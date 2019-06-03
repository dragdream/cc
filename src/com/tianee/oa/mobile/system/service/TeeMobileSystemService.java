package com.tianee.oa.mobile.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.misc.BASE64Encoder;

import com.tianee.oa.core.base.email.dao.TeeMailDao;
import com.tianee.oa.core.base.news.dao.TeeNewsDao;
import com.tianee.oa.core.base.notify.dao.TeeNotifyDao;
import com.tianee.oa.core.general.bean.TeeInterface;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.priv.bean.TeeMenuGroup;
import com.tianee.oa.core.priv.bean.TeeSysMenu;
import com.tianee.oa.core.priv.dao.TeeMenuDao;
import com.tianee.oa.core.priv.service.TeeSysMenuPrivService;
import com.tianee.oa.core.system.adapter.TeeImValidator;
import com.tianee.oa.core.system.adapter.TeeLoginAdapter;
import com.tianee.oa.core.system.adapter.TeeRegistAuthValidator;
import com.tianee.oa.core.system.filters.TeeExistUserValidator;
import com.tianee.oa.core.system.filters.TeeForbidLoginValidator;
import com.tianee.oa.core.system.filters.TeeMobileDeviceLoginValidator;
import com.tianee.oa.core.system.filters.TeePasswordValidator;
import com.tianee.oa.core.system.service.TeeSystemServiceInterface;
import com.tianee.oa.mobile.global.TeeMobileConst;
import com.tianee.oa.mobile.manage.bean.TeeMobileModule;
import com.tianee.oa.mobile.manage.service.TeeMobileModuleService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.bisengin.service.BisViewService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.dynamic.TeeClassRunner;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;


@Service
public class TeeMobileSystemService  extends TeeBaseService implements TeeMobileSystemServiceInterface{
	
	@Autowired
	TeePersonDao personDao;
	
	@Autowired
	TeeSystemServiceInterface systemService;
	
	@Autowired
	private TeeMenuDao sysMenuDao;

	@Autowired
	private TeeSysMenuPrivService menuPrivService;
	
	@Autowired
	private TeeMailDao mailDao;
	
	@Autowired
	private TeeNotifyDao notifyDao;
	
	@Autowired
	private TeeNewsDao newsDao;
	
	@Autowired
	private BisViewService bisViewService;
	
	@Autowired
	private TeeMobileModuleService mobileModuleService;
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.mobile.system.service.TeeMobileSystemServiceInterface#doLoginInService(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	@TeeLoggingAnt(template="{request.userName}手机登录系统{#.rtMsg}",type="003E")
    public TeeJson doLoginInService(HttpServletRequest request , HttpServletResponse response){
		TeeJson json = new TeeJson();
		Map map = new HashMap();

	    try {
		   String url = new String(request.getRequestURL());
		   String userName = TeeStringUtil.getString(request.getParameter("userName"));
		   String pwd = TeeStringUtil.getString(request.getParameter("pwd"));
		   
		   //用户名和密码解密
//		   userName = new String(Base64Private.decode(userName.getBytes()));
//		   pwd = new String(Base64Private.decode(pwd.getBytes()));
		   
		   String qDevice = TeeStringUtil.getString(request.getParameter("qDevice") , "1");//登录方式  ？？ 1-android  2-IOS  3-IM  4 - ipad
		   String pVer = request.getParameter("P_VER");
		   String client = request.getParameter("CLIENT");
		   String deviceNo = request.getParameter("deviceNo");
	       TeePerson person = null;
	       if (TeeUtility.isNullorEmpty(userName)) {
	           userName = "";
	       }else {
	    	   person = personDao.getPersonByUserId(userName);
	    	   if(person!=null){
	    		   person.getPostDept().size();
	    		   person.getUserRoleOther().size();
	    		   if(person.getDept()!=null){
	    			   person.getDept().getDeptName();
	    		   }
	    		   if(person.getUserRole()!=null){
	    			   person.getUserRole().getRoleName();
	    		   }
	    	   }
	      }

	       
	      //登录验证
	      TeeLoginAdapter loginAdapter = new TeeLoginAdapter(request, person,json , null);
	      //验证软件是否过期
	      if(!loginAdapter.validate(new TeeRegistAuthValidator())){
	    	  return json;
	      }
	      //验证用户是否存在
	      if (!loginAdapter.validate(new TeeExistUserValidator())){
	    	   json.setRtMsg(TeeMobileConst.LOGIN_NOTEXIST_USER);
	    	   json.setRtData(null);
	    	   return json;
	      }
	      
	      //验证移动端设备ID绑定
	      if (!loginAdapter.validate(new TeeMobileDeviceLoginValidator())){
	    	   return json;
	      }
	      
	      TeeRequestInfoContext.getRequestInfo().setUserName(person.getUserName());
	      TeeRequestInfoContext.getRequestInfo().setUserId(person.getUuid() + "");
	      //验证用户是否禁止登陆
	      if (!loginAdapter.validate(new TeeForbidLoginValidator())){
	    	  json.setRtMsg(TeeMobileConst.LOGIN_FORBID_LOGIN);
	    	  json.setRtData(null);
	          return  json;
	      }
	      //密码检验
         if (!loginAdapter.validate(new TeePasswordValidator(pwd))){
        	 json.setRtMsg(TeeMobileConst.LOGIN_PASSWORD_ERROR);
        	 json.setRtData(null);
        	 return json;
         }
	        
	      //调用登陆成功的处理
         String loginType = TeeStringUtil.getString(request.getParameter("CLIENT"), "1");
	      if("3".equals(loginType) || "4".equals(loginType)){//移动终端通讯登陆人数判断
		      if (!loginAdapter.validate(new TeeImValidator())){
		    	  return json;
		      }
	      }
	      
	      systemService.loginSuccess( person, request  ,  response,loginType);
	      map.put("sid", person.getUuid());
	      map.put("UUID", person.getUuid());
	      map.put("userName", person.getUserName());
	      map.put("deptId", person.getDept().getUuid());
	      map.put("luokuan",  person.getDept().getSubordinateUnits());
	      map.put("userId", person.getUserId());
	      map.put("JSESSIONID", request.getSession().getId());
	      map.put("PATH", request.getContextPath());
	      map.put("theme", person.getTheme());
	      map.put("sex", person.getSex());
	      map.put("photoId", person.getAvatar());
	      map.put("aipCode", person.getIcq_no());//aip授权码
	      

           TeeInterface itfc = (TeeInterface) simpleDaoSupport.get(TeeInterface.class,1);
           String title =  itfc.getIeTitle();
           if(title==null || "".equals(title)){
        	   title = "移动办公平台";
           }
	    
            map.put("welcomePic", TeeStringUtil.getString(itfc.getWelcomePic()));
            map.put("appIndex", TeeStringUtil.getString(itfc.getAppIndex()));
	        map.put("loginFuncStr","USER,ADDRESS,ATTENDANCE,EMAIL,NEWS,NOTIFY,CALENDAR,DIARY,WORK_FLOW,PERSONAL_NETDISK,PUBLIC_NETDISK,REPORTSHOP,DOCREC,DOCVIEW");
	        map.put("loginFunc",getMenuFuncs(person,1));//oa  app权限
	        map.put("loginZfFunc",getMenuFuncs(person,2));//执法  app权限
	        map.put("appTitle",title);
	        map.put("webVersion",TeeSysProps.getString("VERSION"));//系统web版本
	        map.put("webVersionNo",TeeSysProps.getString("VERSION_NO"));//系统web版本号
	        map.put("photo",person.getAvatar());
	        
	        //动态获取对应服务器，并将该用户写入目标服务器路由
	        map.put("mLogo",itfc.getmLogo());
	        map.put("mPic",itfc.getmPic());
	        map.put("imPic",TeeStringUtil.getString(itfc.getImPic()));
	        
	        map.put("tcpPort",TeeSysProps.getString("TCP_SOCKET_PORT"));//TCP端口
	        map.put("oaUrl","");
	        map.put("isNewVersion","");
	        map.put("currVersion4Android",TeeMobileConfigService.getAndroidCurrVersion());//当前版本
	        map.put("downloadUpdateUrl4Android","/appupdate/android/Android_Setup.apk");//下载更新地址
	        map.put("currVersion4Ios",TeeMobileConfigService.getIosCurrVersion());//当前版本
	        map.put("downloadUpdateUrl4Ios","/mobileSystemAction/downIOSFile.action");//下载更新地址
	        map.put("currVersion4PC", TeeSysProps.getString("PC_CURR_VERSION"));
	        map.put("watermark", TeeSysProps.getInt("WATER_MARK"));
	        
	        map.put("appTopBg",itfc.getAppTopBg());
	        map.put("appTopSignShow",TeeStringUtil.getString(itfc.getAppTopSignShow()));
	        
			BASE64Encoder base64Encoder = new BASE64Encoder();
			Cookie token = new Cookie("TOKEN", base64Encoder.encode(person.getUserId().getBytes("UTF-8")).replace("=", "%3D").replace("/", "%2F"));
			token.setPath("/");
			token.setMaxAge(60*60*24*365);
			response.addCookie(token);
	        
	    } catch (Exception ex) {
	        String retData = "{\"code\":9999,\"msg\":\""+ex.getMessage()+"\"}";
	        ex.printStackTrace();
	        TeeJsonUtil jsonUtil = new TeeJsonUtil();
	        JSONObject jsonObj = jsonUtil.jsonString2Json(retData);
	    	json.setRtMsg(ex.getMessage());
	    	json.setRtState(false);
	    	json.setRtData(jsonObj);
	    	return json;
	    	//System.out.println(ex.getMessage());
	    }
     	json.setRtState(true);
     	json.setRtData(map);
     	json.setRtMsg("登录成功");
	    return json ;
    }
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.mobile.system.service.TeeMobileSystemServiceInterface#getMenuFuncs(com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public List getMenuFuncs(TeePerson person,int type){
//		StringBuffer  menuFuncs = new StringBuffer("USER,ADDRESS,ATTENDANCE");
//		Map map = TeeMobileConfigService.getMobileConfig();
//		Set set  = getMenuList( person);
//		Iterator it = map.entrySet().iterator();  
//
//    	while (it.hasNext()) {
//    		Map.Entry mapObj = (Map.Entry)it.next();
//    		String key = (String) mapObj.getKey();
//    		String value = (String)mapObj.getValue();
//			if(set.contains(value)){
//				menuFuncs.append("," + key);
//			}
//    	}
//		simpleDaoSupport.find("select ", param);
		String hql = "select sid as sid,appName as appName,pic as pic,url as url from TeeMobileModule where  type="+type+" and ";
		hql+="(userPriv like '%/"+person.getUuid()+".u%'";
		if(person.getDept()!=null){
			hql+=" or deptPriv like '%/"+person.getDept().getUuid()+".d%'";
		}
		if(person.getUserRole()!=null){
			hql+=" or rolePriv like '%/"+person.getUserRole().getUuid()+".r%'";
		}
		hql+=") order by sort asc";
		List<Map> list = simpleDaoSupport.getMaps(hql, null);
		
		//获取当前的版本
		int versionType = 1 ;
		try {
			versionType=(Integer) TeeClassRunner.exec("com.tianee.webframe.util.auth.TeeAuthUtil", "getVersion", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Map data:list){
			String sid = TeeStringUtil.getString(data.get("pic"));
			if(versionType==1){//标准版本进行控制   政务版本和平台版本不用进行控制
				if(sid.equals("8") || sid.equals("18") || sid.equals("20") || sid.equals("25")){
					continue;
				}
			}
			data.put("pic", TeeStringUtil.getString(data.get("pic")));
			data.put("url", TeeStringUtil.getString(data.get("url")));
		}
		return  list;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.mobile.system.service.TeeMobileSystemServiceInterface#getMenuList(com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public Set getMenuList(TeePerson person){
		List<TeeSysMenu>  list = new ArrayList<TeeSysMenu>(); 
		String sysMenuIds = "";//菜单uuid字符串，已逗号分隔，方便查有权限路径（页面和controller）
		List<TeeMenuGroup> groupList = person.getMenuGroups();
		String groupIds = "";
		for (int i = 0; i < groupList.size(); i++) {
			TeeMenuGroup group = groupList.get(i);
			groupIds = groupIds  + group.getUuid() + ",";
		}
		Set set = new HashSet();
		if(TeeUtility.isNullorEmpty(groupIds)){
			return set;
		}
		//根据菜单组获取菜单对象list
		list = sysMenuDao.getSysMenuListByMenuGroupUuids(groupIds);
			
		   
		for (int i = 0; i < list.size(); i++) {
			TeeSysMenu menu = list.get(i);
			
			set.add(String.valueOf(menu.getUuid()));
		
		}
			/*//获取有关联的菜单路径
			if(!TeeUtility.isNullorEmpty(sysMenuIds)){
				//获取缓存对象
				Cache cache = TeeCacheManager.getCache("menuPrivPersonal");
				if(cache != null){
					//先删除个人缓存菜单权限元素
					cache.remove(person.getUuid());
					Map<String , Object> map = new HashMap<String , Object>();
					TeeSysMenuPrivModel model = new TeeSysMenuPrivModel();
					model.setPrivFlag("1");
					List<TeeSysMenuPriv> menuPrivlist  = menuPrivService.selectPrivByMenuIds(model, sysMenuIds);
					for (int i = 0; i < menuPrivlist.size(); i++) {
						TeeSysMenuPriv priv = menuPrivlist.get(i);
						if(!TeeUtility.isNullorEmpty(priv.getPrivUrl())){
							map.put(priv.getPrivUrl(), priv.getSid());
						}
					}
					//存放个人缓存菜单权限元素
					Element element = new Element(person.getUuid() , map);
					//存放到缓存里
					cache.put(element);
					Element element2 =  cache.get(person.getUuid());
					Map o = (Map)element2.getValue();
					System.out.println(o);
				}
				
			}*/
			return set;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.mobile.system.service.TeeMobileSystemServiceInterface#getNewPush(com.tianee.oa.core.org.bean.TeePerson, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public Map getNewPush(TeePerson loginUser,String module){
		Map msg = new HashMap();
	
		long total = 0;
		
		//获取未读邮件
		long count = mailDao.getNotReadingCount(loginUser);
		total+=count;
		msg.put("email", String.valueOf(count));
		msg.put("4", String.valueOf(count));
		
		//获取未读公告;
		count = notifyDao.getPersonalNoReadCount(0, loginUser,"");
		total+=count;
		msg.put("notify", String.valueOf(count));
		msg.put("1", String.valueOf(count));
		
		//获取未读新闻
		String hql = "";
		count = newsDao.getPersonalNoReadCount(0, loginUser);
		total+=count;
		msg.put("news", String.valueOf(count));
		msg.put("2", String.valueOf(count));
		
		//获取待办工作
		//hql = "select count(frp.sid) from TeeFlowRunPrcs frp left outer join frp.flowType as ft where ft is not null and frp.flowRun.delFlag=0 and frp.prcsUser.uuid="+loginUser.getUuid()+" and frp.flag in (1,2) and frp.flowRun.endTime is null and frp.delFlag=0 and frp.suspend=0 ";
		hql="select count(*) from FLOW_RUN_PRCS frp left outer join FLOW_PROCESS fp on fp.sid=frp.FLOW_PRCS ,FLOW_RUN fr,PERSON p,FLOW_TYPE ft where  fr.IS_SAVE=1 and fr.DEL_FLAG=0 and p.uuid=fr.begin_person and ft.SID=fr.FLOW_ID and frp.RUN_ID=fr.RUN_ID  and frp.flag in (1,2) and fr.END_TIME is null and frp.PRCS_USER="+loginUser.getUuid()+" and frp.suspend=0";
		count = simpleDaoSupport.countSQLByList(hql,null);
		total+=count;
		msg.put("workflow", String.valueOf(count));
		msg.put("7", String.valueOf(count));
		
//		if("INIT".equals(module)){
//			//获取未读消息message
//			hql = "select count(*) from TeeMessage where deleteFlag=0 and remindFlag=0 and toId='"+loginUser.getUserId()+"' and body.deleteFlag=0";
//			count = simpleDaoSupport.count(hql, null);
//			msg.put("message", String.valueOf(count));
//		}
		
		long doc = 0;
		//获取待收公文
		hql = "select count(d.uuid) from TeeDocumentDelivery d where d.flag=0 and exists (select 1 from TeeDocumentRecMapping m where m.dept.uuid=d.recDept and exists (select 1 from m.privUsers user where user.uuid="+loginUser.getUuid()+"))";
		count = simpleDaoSupport.count(hql, null);
		total+=count;
		doc+=count;
		msg.put("docrec", String.valueOf(count));
		
		
		//获取待阅公文
		hql = "select count(v.uuid) from TeeDocumentView v where v.flag=0 and v.recUser="+loginUser.getUuid();
		count = simpleDaoSupport.count(hql, null);
		total+=count;
		doc+=count;
		msg.put("docview", String.valueOf(count));
		
		
		// 获取定义的公文
		List<Map> flowPriv = simpleDaoSupport.getMaps(
				"select flowType.sid as FLOWID from TeeDocumentFlowPriv", null);
		StringBuffer flowIds = new StringBuffer();
		for (int i = 0; i < flowPriv.size(); i++) {
			flowIds.append(flowPriv.get(i).get("FLOWID"));
			if (i != flowPriv.size() - 1) {
				flowIds.append(",");
			}
		}
		//获取待办公文
		hql="select count(*) from FLOW_RUN_PRCS frp left outer join FLOW_PROCESS fp on fp.sid=frp.FLOW_PRCS ,FLOW_RUN fr,PERSON p,FLOW_TYPE ft where fr.IS_SAVE=1 and fr.DEL_FLAG=0 and p.uuid=fr.begin_person and ft.SID=fr.FLOW_ID and frp.RUN_ID=fr.RUN_ID  and frp.flag in (1,2) and fr.END_TIME is null and frp.PRCS_USER="+loginUser.getUuid()+" and frp.suspend=0 and ft.sid in "+"("+flowIds+")";
		count = simpleDaoSupport.countSQLByList(hql, null);
		total+=count;
		doc+=count;
		msg.put("docwork", String.valueOf(count));
		
		
		msg.put("8", String.valueOf(doc));
		
		msg.put("TOTAL", String.valueOf(total));
		
		hql = "select count(sms.uuid) from TeeSms sms where sms.remindFlag=0 and sms.toId="+loginUser.getUuid();
		count = simpleDaoSupport.count(hql, null);
		msg.put("SMS_TOTAL", String.valueOf(count));
		
		//获取所有的第三方APP模块
		List<TeeMobileModule> modules = mobileModuleService.list();
		TeeDataGridModel dm = new TeeDataGridModel();
		dm.setPage(1);
		dm.setRows(1);
		Map requestDatas = new HashMap();
		for(TeeMobileModule m:modules){
			if(m.getSid()>=100 && !TeeUtility.isNullorEmpty(m.getViewId())){
				requestDatas.put(TeeConst.LOGIN_USER, loginUser);
				requestDatas.put("dfid", m.getViewId());
				TeeEasyuiDataGridJson dataGridJson = bisViewService.dflist(dm, requestDatas);
				msg.put(String.valueOf(m.getSid()), String.valueOf(dataGridJson.getTotal()));
			}else if(m.getSid()>=100 && TeeUtility.isNullorEmpty(m.getViewId())){
				msg.put(String.valueOf(m.getSid()), String.valueOf(0));
			}
		}
		
		return msg;
	}
	
	
	

	/* (non-Javadoc)
	 * @see com.tianee.oa.mobile.system.service.TeeMobileSystemServiceInterface#bindDeviceId(com.tianee.oa.core.org.bean.TeePerson, java.lang.String, java.lang.String)
	 */
	@Override
	public TeeJson bindDeviceId(TeePerson loginUser,String deviceId,String serialNo){
		TeeJson json = new TeeJson();
		long count = simpleDaoSupport.count("select count(uuid) from TeePerson p where p.deviceId=? and p.uuid !=?", new Object[]{deviceId,loginUser.getUuid()});
		if(count>=1){
			json.setRtData(1);
			json.setRtMsg("该设备已被其他用户绑定！");
			json.setRtState(false);
		}else{
			simpleDaoSupport.executeUpdate("update TeePerson p set p.deviceId=?,p.secureKeySn=? where p.uuid=?", new Object[]{deviceId,serialNo,loginUser.getUuid()});
		    json.setRtData(0);
			json.setRtMsg("绑定成功！");
		    json.setRtState(true);
		}
	    return json;
	}
	
}
