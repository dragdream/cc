package com.tianee.oa.subsys.informationReport.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incors.plaf.alloy.ca;
import com.tianee.oa.core.base.email.bean.TeeMail;
import com.tianee.oa.core.base.email.model.TeeEmailModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.org.service.TeeUserRoleService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.informationReport.bean.TeeTaskTemplate;
import com.tianee.oa.subsys.informationReport.dao.TeeTaskTemplateDao;
import com.tianee.oa.subsys.informationReport.model.TeeQueryModel;
import com.tianee.oa.subsys.informationReport.model.TeeTaskTemplateModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeTaskTemplateService extends TeeBaseService{

	@Autowired
	private TeeTaskTemplateDao taskTemplateDao;
	
	@Autowired
	private TeePersonService personService;

	@Autowired
	private TeeDeptService deptService;
	
	@Autowired
	private TeeUserRoleService roleService;
	
	@Autowired
	private TeeTaskTemplateDao teDao;
	/**
	 * 根据主键   获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
        //获取主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeTaskTemplate template=(TeeTaskTemplate) simpleDaoSupport.get(TeeTaskTemplate.class,sid);
		if(template!=null){
			TeeTaskTemplateModel model=parseToModel(template);
			json.setRtState(true);
			json.setRtData(model);
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}

	
	
	
	/**
	 * 将实体类转换成model
	 * @param template
	 * @return
	 */
	private TeeTaskTemplateModel parseToModel(TeeTaskTemplate template) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TeeTaskTemplateModel model=new TeeTaskTemplateModel();
		BeanUtils.copyProperties(template, model);
		//预估时间
		if(template.getPreTime()!=null){
			model.setPreTimeStr(sdf.format(template.getPreTime().getTime()));
		}else{
			model.setPreTimeStr("");
		}
		//创建时间
		if(template.getCrTime()!=null){
			model.setCrTimeStr(sdf.format(template.getCrTime()));
		}
		//创建人
		if(template.getCrUser()!=null){
			model.setCrUserId(template.getCrUser().getUuid());
			model.setCrUserName(template.getCrUser().getUserName());
		}
		
		//上报人员
		String reportUserNames="";
		String reportUserIds="";
		if(template.getUsers()!=null&&template.getUsers().size()>0){
			for (TeePerson user :template.getUsers()) {
				reportUserNames+=user.getUserName()+",";
				reportUserIds=+user.getUuid()+",";
			}
			if(reportUserNames.endsWith(",")){
				reportUserNames=reportUserNames.substring(0,reportUserNames.length()-1);
			}
			if(reportUserIds.endsWith(",")){
				reportUserIds=reportUserIds.substring(0,reportUserIds.length()-1);
			}
		}
		
		model.setReportUserIds(reportUserIds);
		model.setReportUserNames(reportUserNames);
		//上报部门
		String reportDeptNames="";
		String reportDeptIds="";
		if(template.getDepts()!=null&&template.getDepts().size()>0){
			for (TeeDepartment dept : template.getDepts()) {
				reportDeptNames+=dept.getDeptName()+",";
				reportDeptIds+=dept.getUuid()+",";
			}
			if(reportDeptNames.endsWith(",")){
				reportDeptNames=reportDeptNames.substring(0,reportDeptNames.length()-1);
			}
			
			if(reportDeptIds.endsWith(",")){
				reportDeptIds=reportDeptIds.substring(0,reportDeptIds.length()-1);
			}
		}
		model.setReportDeptIds(reportDeptIds);
		model.setReportDeptNames(reportDeptNames);
        
		//上报角色
		String reportRoleIds="";
		String reportRoleNames="";
	    if(template.getRoles()!=null&&template.getRoles().size()>0){
	    	for (TeeUserRole role : template.getRoles()) {
	    		reportRoleIds+=role.getUuid()+",";
	    		reportRoleNames+=role.getRoleName()+",";
			}
	    	if(reportRoleIds.endsWith(",")){
	    		reportRoleIds=reportRoleIds.substring(0,reportRoleIds.length()-1);
			}
	    	if(reportRoleNames.endsWith(",")){
	    		reportRoleNames=reportRoleNames.substring(0,reportRoleNames.length()-1);
			}
	    }
	    model.setReportRoleIds(reportRoleIds);
	    model.setReportRoleNames(reportRoleNames);
	    
	    //拼接频次描述
	    String modelDesc="";
	    String taskTypeDesc="";
	    String model_=template.getModel();
	    Map m=TeeJsonUtil.JsonStr2Map(model_);
	    if(template.getTaskType()==1){//日报
	    	modelDesc="每天"+m.get("rbTime")+"开始";
	    	taskTypeDesc="日报";
	    }else if(template.getTaskType()==2){//周报
	    	String weekDesc="";
	    	if(TeeStringUtil.getInteger(m.get("week"), 0)==1){
	    		weekDesc="一";
	    	}else if(TeeStringUtil.getInteger(m.get("week"), 0)==2){
	    		weekDesc="二";
	    	}else if(TeeStringUtil.getInteger(m.get("week"), 0)==3){
	    		weekDesc="三";
	    	}else if(TeeStringUtil.getInteger(m.get("week"), 0)==4){
	    		weekDesc="四";
	    	}else if(TeeStringUtil.getInteger(m.get("week"), 0)==5){
	    		weekDesc="五";
	    	}else if(TeeStringUtil.getInteger(m.get("week"), 0)==6){
	    		weekDesc="六";
	    	}else if(TeeStringUtil.getInteger(m.get("week"), 0)==7){
	    		weekDesc="日";
	    	}
	    	modelDesc="每周"+weekDesc+m.get("zbTime")+"开始";
	    	taskTypeDesc="周报";
	    }else if(template.getTaskType()==3){//月报
	    	modelDesc="每月"+m.get("ybDate")+"号"+m.get("ybTime")+"开始";
	    	taskTypeDesc="月报";
	    }else if(template.getTaskType()==4){//季报
	    	modelDesc="每季度第"+m.get("jbMonth")+"个月"+m.get("jbDate")+"号"+m.get("jbTime")+"开始";
	    	taskTypeDesc="季报";
	    }else if(template.getTaskType()==5){//年报
	    	modelDesc="每年"+m.get("nbMonth")+"月"+m.get("nbDate")+"号"+m.get("nbTime")+"开始";
	    	taskTypeDesc="年报";
	    }else if(template.getTaskType()==6){//一次性
	    	modelDesc=m.get("ycxTime")+"开始 ";
	    	taskTypeDesc="一次性";
	    }
	    model.setModelDesc(modelDesc);
	    model.setTaskTypeDesc(taskTypeDesc);
		return model;
	}




	/**
	 * 新建/编辑
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		//System.out.println(request.getParameter("repType"));
		
		//System.out.println(request.getParameter("taskName"));
		TeeTaskTemplateModel model=new TeeTaskTemplateModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		
		int flag=TeeStringUtil.getInteger(request.getParameter("flag"),0);
		if(model.getSid()>0){//编辑
			TeeTaskTemplate t=(TeeTaskTemplate) simpleDaoSupport.get(TeeTaskTemplate.class,model.getSid());
			model.setPubStatus(t.getPubStatus());
			BeanUtils.copyProperties(model, t);
			//设置上报人员
			t.getUsers().clear();
			List<TeePerson> userList=personService.getPersonByUuids(model.getReportUserIds());
			Set<TeePerson> users=new HashSet<TeePerson>(userList);
			t.setUsers(users);
			//设置上报部门
			t.getDepts().clear();
			List<TeeDepartment> deptList=deptService.getDeptByUuids(model.getReportDeptIds());
			Set<TeeDepartment> depts=new HashSet<TeeDepartment>(deptList);
			t.setDepts(depts);
			//设置上报角色
			t.getRoles().clear();
			List<TeeUserRole>roleList=roleService.getUserRoleByUuids(model.getReportRoleIds());
		    Set<TeeUserRole> roles=new HashSet<TeeUserRole>(roleList);
		    t.setRoles(roles);
		   
		    if(flag==1){
		    	//获取预估时间
			    Calendar preTime=getPreTime(t);
			    t.setPreTime(preTime);
		    }else{
		        t.setPreTime(null);
		    }
		    
		    
		    simpleDaoSupport.update(t);
		}else{//新建 
			TeeTaskTemplate template=new TeeTaskTemplate();
			BeanUtils.copyProperties(model, template);
			template.setPubStatus(0);//未发布状态
			template.setCrTime(new Date());
			//System.out.println("book"+request.getParameter("repType"));
			template.setRepType(Integer.parseInt(request.getParameter("repType")));
			template.setCrUser(loginUser);
			//设置上报人员
			List<TeePerson> userList=personService.getPersonByUuids(model.getReportUserIds());
			Set<TeePerson> users=new HashSet<TeePerson>(userList);
			template.setUsers(users);
			//设置上报部门
			List<TeeDepartment> deptList=deptService.getDeptByUuids(model.getReportDeptIds());
			Set<TeeDepartment> depts=new HashSet<TeeDepartment>(deptList);
			template.setDepts(depts);
			//设置上报角色
			List<TeeUserRole>roleList=roleService.getUserRoleByUuids(model.getReportRoleIds());
		    Set<TeeUserRole> roles=new HashSet<TeeUserRole>(roleList);
		    template.setRoles(roles);
		    
		    
		    //获取预估时间
		    Calendar preTime=getPreTime(template);
		    template.setPreTime(preTime);
		    
		    simpleDaoSupport.save(template);
		    
		    
		    //创建上报数据表（REP_TASK_DATA_模版ID）
		    //获取方言
		    String dialect=TeeSysProps.getDialect();
		    
		    if("mysql".equals(dialect.toLowerCase())){
		    	simpleDaoSupport.executeNativeUpdate("create table REP_TASK_DATA_"+template.getSid()+"  (RECORD_ITEM_ID int(11),REP_TASK_PUB_RECORD_ID int(11),REP_TASK_TEMPLATE_ID int(11),CREATE_TIME datetime,CREATE_USER_ID int(11)) ",null);
		    }else if("oracle".equals(dialect.toLowerCase())){
		    	simpleDaoSupport.executeNativeUpdate("create table REP_TASK_DATA_"+template.getSid()+"  (RECORD_ITEM_ID number(11),REP_TASK_PUB_RECORD_ID number(11),REP_TASK_TEMPLATE_ID number(11),CREATE_TIME date,CREATE_USER_ID number(11)) ",null);
		    }else if("sqlserver".equals(dialect.toLowerCase())){
		    	simpleDaoSupport.executeNativeUpdate("create table REP_TASK_DATA_"+template.getSid()+"  (RECORD_ITEM_ID int,REP_TASK_PUB_RECORD_ID int,REP_TASK_TEMPLATE_ID int,CREATE_TIME datetime,CREATE_USER_ID int) ",null);
		    }else if("kingbase".equals(dialect.toLowerCase())){
		    	simpleDaoSupport.executeNativeUpdate("create table REP_TASK_DATA_"+template.getSid()+"  (RECORD_ITEM_ID number(11),REP_TASK_PUB_RECORD_ID number(11),REP_TASK_TEMPLATE_ID number(11),CREATE_TIME date,CREATE_USER_ID number(11)) ",null);
		    }
		    
		    
		   
		    
		    
		   
	        
		}
		json.setRtState(true);
		return json;
	}




	/**
	 * 获取我创建的任务
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getMyPubTask(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String hql = " from TeeTaskTemplate  t where t.crUser.uuid=? ";
		List param = new ArrayList();
		param.add(loginPerson.getUuid());
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by t.crTime desc";

		List<TeeTaskTemplate> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeTaskTemplateModel> modelList = new ArrayList<TeeTaskTemplateModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeTaskTemplateModel model = parseToModel(list.get(i));
				modelList.add(model);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}




	/**
	 * 任务发布
	 * @param request
	 * @return
	 */
	public TeeJson pubTask(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeTaskTemplate template=(TeeTaskTemplate) simpleDaoSupport.get(TeeTaskTemplate.class,sid);
		if(template!=null){
			template.setPubStatus(1);//设置成发布状态
			simpleDaoSupport.update(template);
			json.setRtState(true);
			json.setRtMsg("发布成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}




	/**
	 * 删除任务
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		//先删除该任务模板下 的模板项   
		simpleDaoSupport.executeUpdate("delete from TeeTaskTemplateItem item where item.taskTemplate.sid=? ", new Object[]{sid});
		//删除该任务模板
		simpleDaoSupport.executeUpdate("delete from TeeTaskTemplate t where t.sid=? ", new Object[]{sid});
		json.setRtState(true);
		json.setRtMsg("删除成功！");
		return  json;
	}




	/**
	 * 取消发布
	 * @param request
	 * @return
	 */
	public TeeJson cancelPub(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeTaskTemplate template=(TeeTaskTemplate) simpleDaoSupport.get(TeeTaskTemplate.class,sid);
		if(template!=null){
			template.setPubStatus(0);//设置成未发布状态
			simpleDaoSupport.update(template);
			json.setRtState(true);
			json.setRtMsg("取消成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}
	
	
	/**
	 * 获取预估时间  
	 * @param t
	 * @return
	 */
	public  Calendar getPreTime(TeeTaskTemplate t){
		//预估时间
		
		//先取出当前时间
		Calendar nowTime = Calendar.getInstance();
		Map<String,String> pcModel = TeeJsonUtil.JsonStr2Map(t.getModel());
		if(t.getTaskType()==1){//如果是日报
			//取出频次模型中的时分秒
			String rbTime = pcModel.get("rbTime");
			//取出当前时间的时分秒
			String nowShiFenMiao = TeeDateUtil.format(nowTime.getTime(), "HH:mm:ss");
			String sp[] = rbTime.split(":");
			
			if(nowShiFenMiao.compareTo(rbTime)>=0){//说明当前时间大于频次模型中的时间
				nowTime.add(Calendar.DATE, 1);
			}
			
			nowTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sp[0]));
			nowTime.set(Calendar.MINUTE, Integer.parseInt(sp[1]));
			nowTime.set(Calendar.SECOND, Integer.parseInt(sp[2]));
		}else if(t.getTaskType()==2){//如果是周报
			//取出频次模型中的周数(1,2,3,4,5,6,7)
			int week=TeeStringUtil.getInteger(pcModel.get("week"), 0);
			//取出频次模型中的时分秒
			String zbTime=pcModel.get("zbTime");
			//取出当前时间的周数
			int nowWeek=nowTime.get(Calendar.DAY_OF_WEEK);
			if(nowWeek==1){
				nowWeek=7;
			}else{
				nowWeek=nowWeek-1;
			}
			//取出当前时间的时分秒
			String nowShiFenMiao = TeeDateUtil.format(nowTime.getTime(), "HH:mm:ss");
			
			String sp[] = zbTime.split(":");
			if(nowWeek < week){//如果当前周数 小  (例如当前是周三  实际是周五)
				nowTime.add(Calendar.DATE,(week-nowWeek));
				nowTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sp[0]));
				nowTime.set(Calendar.MINUTE, Integer.parseInt(sp[1]));
				nowTime.set(Calendar.SECOND, Integer.parseInt(sp[2]));
			}else if(nowWeek==week){//当前周数  等于  设定的周数   则比较时间点
				if(nowShiFenMiao.compareTo(zbTime)>=0){//说明当前时间大于频次模型中的时间
					nowTime.add(Calendar.DATE,7-(nowWeek-week));
					nowTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sp[0]));
					nowTime.set(Calendar.MINUTE, Integer.parseInt(sp[1]));
					nowTime.set(Calendar.SECOND, Integer.parseInt(sp[2]));
				}else{
					nowTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sp[0]));
					nowTime.set(Calendar.MINUTE, Integer.parseInt(sp[1]));
					nowTime.set(Calendar.SECOND, Integer.parseInt(sp[2]));
				}
			}else{//如果当前周数大   (例如  当前周五  实际周三)
				nowTime.add(Calendar.DATE,7-(nowWeek-week));
				nowTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sp[0]));
				nowTime.set(Calendar.MINUTE, Integer.parseInt(sp[1]));
				nowTime.set(Calendar.SECOND, Integer.parseInt(sp[2]));
			}
		}else if(t.getTaskType()==3){//如果是月报
			
			int ybDate=TeeStringUtil.getInteger(pcModel.get("ybDate"), 0);
			//取出频次中的时间点
			String ybTime=pcModel.get("ybTime");
			String sp[] = ybTime.split(":");
			
			//創建一個臨時時間
			Calendar tmpCalendar = Calendar.getInstance();
			int nowMaxDays = tmpCalendar.getActualMaximum(Calendar.DATE);
			int actuallyYbDate =0;
			if(ybDate>=nowMaxDays){
				actuallyYbDate = nowMaxDays;
			}else{
				actuallyYbDate=ybDate;
			}
			tmpCalendar.set(Calendar.DATE, actuallyYbDate);
			tmpCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sp[0]));
			tmpCalendar.set(Calendar.MINUTE, Integer.parseInt(sp[1]));
			tmpCalendar.set(Calendar.SECOND, Integer.parseInt(sp[2]));
			
			if(nowTime.compareTo(tmpCalendar)>=0){//預估時間是下個月
				
				nowTime.set(Calendar.DATE, 1);
				nowTime.add(Calendar.MONTH, 1);//月份加到下個月
				
				int maxNextDays = nowTime.getActualMaximum(Calendar.DATE);
				if(ybDate>=maxNextDays){
					actuallyYbDate = maxNextDays;
				}else{
					actuallyYbDate=ybDate;
				}
				nowTime.set(Calendar.DATE,actuallyYbDate);
				
				nowTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sp[0]));
				nowTime.set(Calendar.MINUTE, Integer.parseInt(sp[1]));
				nowTime.set(Calendar.SECOND, Integer.parseInt(sp[2]));
				
				
			}else{//預估時間為本月
				nowTime = tmpCalendar;
			}

		}else if(t.getTaskType()==4){//季报
			//获取每个季度第几个月
			int jbMonth=TeeStringUtil.getInteger(pcModel.get("jbMonth"),0);
			//获取几号
			int jbDate=TeeStringUtil.getInteger(pcModel.get("jbDate"),0);
			//获取时间点
			String jbTime=pcModel.get("jbTime");
			String sp[]=jbTime.split(":");
			//获取当前时间  是本季度的第几个月
			int nowNum=0;
			//获取当前时间的月份
			int nowMonth=nowTime.get(Calendar.MONTH)+1;
			if(nowMonth==1||nowMonth==4||nowMonth==7||nowMonth==10){
				nowNum=1;
			}else if(nowMonth==2||nowMonth==5||nowMonth==8||nowMonth==11){
				nowNum=2;
			}else{
				nowNum=3;
			}
			//创建一个临时的时间   为本季度的上报时间
			Calendar temp=Calendar.getInstance();
			//设置月份
			temp.add(Calendar.MONTH, (jbMonth-nowNum));
			//获取该月份  最大天数
			int nowMaxDays=temp.getActualMaximum(Calendar.DATE);
			if(jbDate<=nowMaxDays){
				temp.set(Calendar.DATE, jbDate);
			}else{
				temp.set(Calendar.DATE, nowMaxDays);
			}
			temp.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sp[0]));
			temp.set(Calendar.MINUTE, Integer.parseInt(sp[1]));
			temp.set(Calendar.SECOND, Integer.parseInt(sp[2]));
			
		    if(nowTime.compareTo(temp)>=0){//预估是下个季度
		    	temp.set(Calendar.DATE, 1);
		    	temp.add(Calendar.MONTH,3);
		    	//获取当前月的
		    	int nextMaxDays=temp.getActualMaximum(Calendar.DATE);
		    	if(jbDate<=nextMaxDays){
		    		temp.set(Calendar.DATE, jbDate);
		    	}else{
		    		temp.set(Calendar.DATE, nextMaxDays);
		    	}
		    	
		    	nowTime=temp;
		    }else{//本季度
		       nowTime = temp;
		    }
			
		}else if(t.getTaskType()==5){//年报
			//获取月份
			int nbMonth=TeeStringUtil.getInteger(pcModel.get("nbMonth"), 0);
			//获取几号
			int nbDate=TeeStringUtil.getInteger(pcModel.get("nbDate"), 0);
			//获取时间点
			String nbTime=pcModel.get("nbTime");
			String sp[]=nbTime.split(":");
			int actuallyDate=0;
			//创建一个临时的Calendar
			Calendar tem=Calendar.getInstance();
			tem.set(Calendar.MONTH,nbMonth-1);
			//先获取这个月的做大天使
			int maxTemDays=tem.getActualMaximum(Calendar.DATE);
			if(nbDate>=maxTemDays){
				actuallyDate=maxTemDays;
			}else{
				actuallyDate=nbDate;
			}
			tem.set(Calendar.DATE, actuallyDate);
			tem.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sp[0]));
			tem.set(Calendar.MINUTE, Integer.parseInt(sp[1]));
			tem.set(Calendar.SECOND, Integer.parseInt(sp[2]));
			
			//比较当前时间  和 当前年的设定时间
			if(nowTime.compareTo(tem)>=0){//预估时间是下一年
				nowTime.set(Calendar.MONTH, nbMonth-1);
				nowTime.set(Calendar.DATE, 1);
				nowTime.add(Calendar.YEAR, 1);//下一年
				
				int maxNextDays = nowTime.getActualMaximum(Calendar.DATE);
				if(nbDate>=maxNextDays){
					actuallyDate = maxNextDays;
				}else{
					actuallyDate=nbDate;
				}
				nowTime.set(Calendar.DATE,actuallyDate);

				nowTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sp[0]));
				nowTime.set(Calendar.MINUTE, Integer.parseInt(sp[1]));
				nowTime.set(Calendar.SECOND, Integer.parseInt(sp[2]));
				
			}else{//预估时间是本年
				nowTime=tem;
			}
			
		}else{//一次性
			//获取具体的时间点
			String ycxTime=pcModel.get("ycxTime");
			Calendar preTime =TeeDateUtil.parseCalendar(ycxTime);
			return preTime;
		}	
		
		nowTime.set(Calendar.MILLISECOND,0);
		return  nowTime;
	}




	/**
	 * 获取已经发布   并且我可以查看的任务模板
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getMyViewTask(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String hql = " from TeeTaskTemplate  t where t.pubStatus=1 and ( (exists (select 1 from t.roles role where role.uuid=? )) or (exists (select 1 from t.users user where user.uuid=? )) or (exists (select 1 from t.depts dept where dept.uuid=? )) )";
		List param = new ArrayList();
		param.add(loginPerson.getUserRole().getUuid());
		param.add(loginPerson.getUuid());
		param.add(loginPerson.getDept().getUuid());
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(t) " + hql, param));// 设置总记录数
		hql += " order by t.crTime desc";

		List<TeeTaskTemplate> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeTaskTemplateModel> modelList = new ArrayList<TeeTaskTemplateModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeTaskTemplateModel model = parseToModel(list.get(i));
				modelList.add(model);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	/**
	 * 获取已经发布   并且我可以查看的任务模板
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getMyViewTask1(TeeDataGridModel dm,
			HttpServletRequest request,TeeQueryModel models ) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List param = new ArrayList();
		/*String hql = " from TeeTaskTemplate  t where t.pubStatus=1 and ( (exists (select 1 from t.roles role where role.uuid=? )) or (exists (select 1 from t.users user where user.uuid=? )) or (exists (select 1 from t.depts dept where dept.uuid=? )) )";*/
		String hql = " from TeeTaskTemplate  t where t.pubStatus=1 and t.crUser.uuid=? ";
		//param.add(loginPerson.getUserRole().getUuid());
		param.add(loginPerson.getUuid());//
		//param.add(loginPerson.getDept().getUuid());
		
		if(models.getRepType()>0){
			hql+="and repType =?";
			param.add(models.getRepType());
		}
		
		
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(t) " + hql, param));// 设置总记录数
		hql += " order by t.crTime desc";

		List<TeeTaskTemplate> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		//System.out.println(list.size());
		List<TeeTaskTemplateModel> modelList = new ArrayList<TeeTaskTemplateModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeTaskTemplateModel model = parseToModel(list.get(i));
				modelList.add(model);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	/*
	 * 分页查询
	 
	public List<TeeTaskTemplate> lists(int firstResult,int rows,TeeQueryModel querymodel){
		
		return teDao.list(firstResult, rows, querymodel);
		
	}
	
	 * 返回总数
	 
	public long  getTotal(TeeQueryModel querymodel){
		return teDao.getTotal(querymodel);
	}*/
}

