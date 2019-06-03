package com.tianee.thirdparty.sphy.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.base.attend.bean.TeeAttendConfig;
import com.tianee.oa.core.base.attend.bean.TeeAttendDuty;
import com.tianee.oa.core.base.attend.bean.TeeAttendHoliday;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.thirdparty.sphy.bean.TeeSphy;
import com.tianee.thirdparty.sphy.bean.TeeSphyBody;
import com.tianee.thirdparty.sphy.dao.TeeSphyBodyDao;
import com.tianee.thirdparty.sphy.dao.TeeSphyDao;
import com.tianee.thirdparty.sphy.model.TeeSphyModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeSphyService extends TeeBaseService{
	
	@Autowired
	private TeeSphyDao teeSphyDao;
	
	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;
	
	@Autowired
	private TeePersonDao personDao;

	@Autowired
	private TeeSphyBodyDao teeSphyBodyDao;
	
	@Autowired
	private TeeSmsManager smsManager;
	/**
	 * 添加会议
	 * */
	public TeeJson addSphy(TeeSphyModel model, TeePerson person) {
		TeeJson json=new TeeJson();
		TeeSphy sp=new TeeSphy();
		int save=0;
		sp.setAgendaContent(model.getAgendaContent());//会议议程
		sp.setAnonymous(model.getAnonymous());//是否允许匿名
		sp.setAppType(model.getAppType());//应用类型
		sp.setAssistant(model.getAssistant());//助理
		sp.setBandwidth(model.getBandwidth());//带宽
		sp.setCreateUser(person);//创建人
		sp.setEndTime(TeeDateUtil.format(model.getEndTime(), "yyyy-MM-dd HH:mm:ss"));//结束时间
		sp.setHosts(model.getHosts());//主持人
		sp.setIsPublic(model.getIsPublic());//是否允许公开
		sp.setMaxUser(model.getMaxUser());//允许最大用户数
		sp.setPuUser(model.getPuUser());//普通用户
		sp.setPwdNormal(model.getPwdNormal());//密码
		sp.setRemark(model.getRemark());//主题
		sp.setRoomName(model.getRoomName());//会议名称
		sp.setRoomType(model.getRoomType());//是否MCU合屏 
		sp.setSphyId(model.getSphyId());//会议id
		sp.setStartTime(TeeDateUtil.format(model.getStartTime(), "yyyy-MM-dd HH:mm:ss"));//开始时间
		if(model.getSid()>0){//修改
			sp.setSid(model.getSid());
			teeSphyDao.update(sp);
			save=model.getSid();
		}else{//添加
			save=TeeStringUtil.getInteger(teeSphyDao.save(sp),0);
		}
		json.setRtState(true);
		json.setRtData(save);
		json.setRtMsg("保持成功");
		return json;
	}

	/**
	 * 获取该用户的登录名
	 * */
	public List<TeePerson> findUserIdByUuids(String housts) {
		List<TeePerson> list=null;
		if(housts!=null && !"".equals(housts)){
			list = personDao.find("from TeePerson where uuid in ("+housts+")", null);
		}
		return list;
	}

	public TeeJson deleteSphy(int sid) {
		TeeJson json=new TeeJson();
		if(sid>0){
			teeSphyDao.delete(sid);
			json.setRtState(true);
			json.setRtMsg("删除成功");
		}else{
			json.setRtState(false);
			json.setRtMsg("删除失败");
		}
		return json;
	}

	/**
	 * 添加会议记录
	 * */
	public int addSphyBody(int sphyId, TeePerson person, int statius) {
		TeeSphyBody body=new TeeSphyBody();
		TeeSphy teeSphy = teeSphyDao.get(sphyId);
		body.setSphy(teeSphy);
		body.setCjUser(person);
		body.setStatus(statius);
		body.setCjStatus(0);
		Serializable save = teeSphyBodyDao.save(body);
		
		// 内部短信
		Map requestData = new HashMap();
		requestData.put("content","您有一个名为【"+teeSphy.getRoomName()+"】的视频会议待加入,请查看!");
		requestData.put("userListIds", person.getUuid());
		requestData.put("moduleNo", "101");
		requestData.put("remindUrl","/system/sphy/mySphyList.jsp");
		smsManager.sendSms(requestData, person);
		return TeeStringUtil.getInteger(save, 0);
	}

	/**
	 * 删除会议记录
	 * */
	public void deleteSphyBody(int sphyId) {
		teeSphyBodyDao.deleteOrUpdateByQuery("delete from TeeSphyBody where sphy.sid=?", new Object[]{sphyId});
	}

	/**
	 * 获取所有的会议记录
	 * */
	public TeeEasyuiDataGridJson myCreateSphyList(TeeDataGridModel m,HttpServletRequest request) {
		TeeEasyuiDataGridJson easyJson=new TeeEasyuiDataGridJson();
		TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeSphyModel> listModel=new ArrayList<TeeSphyModel>();
		String hql="from TeeSphy where createUser.uuid=?";
		List<TeeSphy> find = teeSphyDao.pageFind(hql, m.getFirstResult(), m.getRows(), new Object[]{person.getUuid()});
		Long count = teeSphyDao.count("select count(*) "+hql, new Object[]{person.getUuid()});
		if(find!=null && find.size()>0){
			for(TeeSphy sp:find){
				TeeSphyModel model=new TeeSphyModel();
				model.setSid(sp.getSid());
				model.setAgendaContent(sp.getAgendaContent());//会议议程
				model.setAnonymous(sp.getAnonymous());//是否允许匿名
				model.setAppType(sp.getAppType());//应用类型
				model.setAssistant(sp.getAssistant());//助理
				String assistantName="";
				if(!"".equals(sp.getAssistant())){
					List<TeePerson> find2 = personDao.find("from TeePerson where uuid in ("+sp.getAssistant()+")", null);
					if(find2!=null && find2.size()>0){
						for(TeePerson p:find2){
							assistantName+=p.getUserName()+",";
						}
						if(!"".equals(assistantName)){
							assistantName=assistantName.substring(0, assistantName.length()-1);
						}
					}
				}
				
				model.setAssistantName(assistantName);
				model.setBandwidth(sp.getBandwidth());//带宽
				model.setCreateUser(person.getUuid());//创建人
				model.setCreateUserId(person.getUserId());
				model.setCreateUserName(person.getUserName());
				model.setEndTime(TeeDateUtil.format(sp.getEndTime(), "yyyy-MM-dd HH:mm:ss"));//结束时间
				model.setStartTime(TeeDateUtil.format(sp.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
				model.setHosts(sp.getHosts());//主持人
				String hostsName="";
				if(!"".equals(sp.getHosts())){
					List<TeePerson> find3 = personDao.find("from TeePerson where uuid in ("+sp.getHosts()+")", null);
					if(find3!=null && find3.size()>0){
						for(TeePerson p:find3){
							hostsName+=p.getUserName()+",";
						}
						if(!"".equals(hostsName)){
							hostsName=hostsName.substring(0, hostsName.length()-1);
						}
					}
				}
				
				model.setHostsName(hostsName);
				model.setIsPublic(sp.getIsPublic());//是否允许公开
				model.setMaxUser(sp.getMaxUser());//允许最大用户数
				model.setPuUser(sp.getPuUser());//普通用户
				String puUserName="";
				if(!"".equals(sp.getPuUser())){
					List<TeePerson> find4 = personDao.find("from TeePerson where uuid in ("+sp.getPuUser()+")", null);
					if(find4!=null && find4.size()>0){
						for(TeePerson p:find4){
							puUserName+=p.getUserName()+",";
						}
						if(!"".equals(puUserName)){
							puUserName=puUserName.substring(0, puUserName.length()-1);
						}
					}
				}
			
				model.setPuUserName(puUserName);
				model.setPwdNormal(sp.getPwdNormal());//密码
				model.setRemark(sp.getRemark());//主题
				model.setRoomName(sp.getRoomName());//会议名称
				model.setRoomType(sp.getRoomType());//是否MCU合屏 
				model.setSphyId(sp.getSphyId());//会议id
				listModel.add(model);
			}
		}
		easyJson.setRows(listModel);
		easyJson.setTotal(count);
		return easyJson;
	}

	/**
	 * 获取会议详情
	 * */
	public TeeJson findSphyById(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeSphyModel model=new TeeSphyModel();
		if(sid>0){
			TeeSphy sp = teeSphyDao.get(sid);
			model.setSid(sp.getSid());
			model.setAgendaContent(sp.getAgendaContent());//会议议程
			model.setAnonymous(sp.getAnonymous());//是否允许匿名
			model.setAppType(sp.getAppType());//应用类型
			model.setAssistant(sp.getAssistant());//助理
			String assistantName="";
			if(!"".equals(sp.getAssistant())){
				List<TeePerson> find2 = personDao.find("from TeePerson where uuid in ("+sp.getAssistant()+")", null);
				if(find2!=null && find2.size()>0){
					for(TeePerson p:find2){
						assistantName+=p.getUserName()+",";
					}
					if(!"".equals(assistantName)){
						assistantName=assistantName.substring(0, assistantName.length()-1);
					}
				}
			}
			
			model.setAssistantName(assistantName);
			model.setBandwidth(sp.getBandwidth());//带宽
			model.setEndTime(TeeDateUtil.format(sp.getEndTime(), "yyyy-MM-dd HH:mm:ss"));//结束时间
			model.setStartTime(TeeDateUtil.format(sp.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
			model.setHosts(sp.getHosts());//主持人
			String hostsName="";
			if(!"".equals(sp.getHosts())){
				List<TeePerson> find3 = personDao.find("from TeePerson where uuid in ("+sp.getHosts()+")", null);
				if(find3!=null && find3.size()>0){
					for(TeePerson p:find3){
						hostsName+=p.getUserName()+",";
					}
					if(!"".equals(hostsName)){
						hostsName=hostsName.substring(0, hostsName.length()-1);
					}
				}
			}
			
			model.setHostsName(hostsName);
			model.setIsPublic(sp.getIsPublic());//是否允许公开
			model.setMaxUser(sp.getMaxUser());//允许最大用户数
			model.setPuUser(sp.getPuUser());//普通用户
			String puUserName="";
			if(!"".equals(sp.getPuUser())){
				List<TeePerson> find4 = personDao.find("from TeePerson where uuid in ("+sp.getPuUser()+")", null);
				if(find4!=null && find4.size()>0){
					for(TeePerson p:find4){
						puUserName+=p.getUserName()+",";
					}
					if(!"".equals(puUserName)){
						puUserName=puUserName.substring(0, puUserName.length()-1);
					}
				}
			}
		
			model.setPuUserName(puUserName);
			model.setPwdNormal(sp.getPwdNormal());//密码
			model.setRemark(sp.getRemark());//主题
			model.setRoomName(sp.getRoomName());//会议名称
			model.setRoomType(sp.getRoomType());//是否MCU合屏 
			model.setSphyId(sp.getSphyId());//会议id
		}
		json.setRtState(true);
		json.setRtData(model);
		return json;
	}

	/**
	 * 获取当前登陆人所要参加的会议
	 * */
	public TeeEasyuiDataGridJson mySphyList(TeeDataGridModel m,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson easyJson=new TeeEasyuiDataGridJson();
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>(); 
		TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Date date=new Date();
		String hql="from TeeSphyBody where sphy.endTime>? and cjUser.uuid=?";
		List<TeeSphyBody> find = teeSphyBodyDao.pageFind(hql, m.getFirstResult(), m.getRows(), new Object[]{date,person.getUuid()});
		Long count = teeSphyBodyDao.count("select count(*)"+hql, new Object[]{date,person.getUuid()});
		if(find!=null && find.size()>0){
			for(TeeSphyBody body:find){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("roomName", body.getSphy().getRoomName());
				map.put("appType", body.getSphy().getAppType());
				map.put("remark", body.getSphy().getRemark());
				map.put("startTime", TeeDateUtil.format(body.getSphy().getStartTime(), "yyyy-MM-dd HH:mm:ss"));
				map.put("endTime", TeeDateUtil.format(body.getSphy().getEndTime(), "yyyy-MM-dd HH:mm:ss"));
				if(body.getStatus()==1){
					map.put("myZhiwei", "主持人");
				}else if(body.getStatus()==2){
					map.put("myZhiwei", "普通用户");
				}else if(body.getStatus()==3){
					map.put("myZhiwei", "助理");
				}
				map.put("jkHyId", body.getSphy().getSphyId());//接口会议id
				map.put("hyId", body.getSphy().getSid());//会议id
				map.put("sid", body.getSid());
				listMap.add(map);
			}
		}
		easyJson.setRows(listMap);
		easyJson.setTotal(count);
		return easyJson;
	}

	/**
	 * 获取参会记录
	 * */
	public TeeJson cjSphyList(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		int hyId=TeeStringUtil.getInteger(request.getParameter("hyId"),0);
		List<TeeSphyBody> find = teeSphyBodyDao.find("from TeeSphyBody where sphy.sid=?", new Object[]{hyId});
		if(find!=null && find.size()>0){
			for(TeeSphyBody body:find){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("cjUseName", body.getCjUser().getUserName());
				if(body.getCjStatus()==0){
					map.put("cjStatus", "未参加");
				}else{
					map.put("cjStatus", "已参加");
				}
				if(body.getStatus()==1){
					map.put("cjUserSf", "主持人");
				}else if(body.getStatus()==2){
					map.put("cjUserSf", "普通用户");
				}else if(body.getStatus()==3){
					map.put("cjUserSf", "助理");
				}
				if(body.getCjTime()!=null){
					map.put("cjTime", TeeDateUtil.format(body.getCjTime(), "yyyy-MM-dd HH:mm:ss"));
				}else{
					map.put("cjTime", "");
				}
				listMap.add(map);
			}
		}
		json.setRtState(true);
		json.setRtData(listMap);
		return json;
	}

	/**
	 * 修改参会状态
	 * */
	public TeeJson updateCjHyStatus(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int hyId=TeeStringUtil.getInteger(request.getParameter("hyId"),0);
		TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int query = teeSphyBodyDao.deleteOrUpdateByQuery("update TeeSphyBody set cjStatus=1,cjTime=? where sphy.sid=? and cjUser.uuid=? and cjStatus=0", new Object[]{new Date(),hyId,person.getUuid()});
		if(query>0){
			json.setRtState(true);
			json.setRtMsg("修改成功");
		}
		return json;
	}

	/**
	 * 同步数据
	 * @throws Exception 
	 * */
	public List<Map> tongBuNumber(){
		List<Map> listMap=new ArrayList<Map>();
		List<TeePerson> find = personDao.find("from TeePerson where deleteStatus=0 and userId!=?", new Object[]{"admin"});
		if(find!=null && find.size()>0){
			for(TeePerson p:find){
				Map map=new HashMap();
				map.put("sid", p.getUuid());
				map.put("userName",p.getUserName());//用户姓名
			    map.put("userId",p.getUserId());//用户登录名
			    map.put("email",p.getEmail());//用户电子邮件
			    map.put("phone",p.getMobilNo());//用户电话
			    listMap.add(map);
			}
		}
		return listMap;
	}

	public TeeSphy getSphId(int sid) {
		TeeSphy teeSphy = teeSphyDao.get(sid);
		return teeSphy;
	}

	
}
