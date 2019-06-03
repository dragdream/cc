package com.tianee.oa.core.general.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.meeting.bean.TeeMeeting;
import com.tianee.oa.core.base.meeting.dao.TeeMeetingDao;
import com.tianee.oa.core.base.meeting.model.TeeMeetingModel;
import com.tianee.oa.core.base.notify.bean.TeeNotify;
import com.tianee.oa.core.base.notify.dao.TeeNotifyDao;
import com.tianee.oa.core.base.notify.model.TeeNotifyModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonManagerI;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.xt.bean.TeeXTRun;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeModelIdConst;
import com.tianee.oa.subsys.topic.bean.TeeTopic;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeManagerPostPersonDataPrivModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeQuickSearchService extends TeeBaseService{

	@Autowired
	private TeePersonManagerI personManagerI;
	
	@Autowired
	private TeeNotifyDao notifyDao;
	
	@Autowired
	private TeeMeetingDao meetingDao;
	
	public List<Map> quickSearchUser(TeePerson loginUser,Map requestDatas){
		String word = TeeStringUtil.getString(requestDatas.get("word"));
		
		//查询相关用户信息
		String hql = "select "
				+ "p.userName as userName,"
				+ "p.uuid as uuid,"
				+ "p.userId as userId,"
				+ "p.dept.deptName as deptName"
				+ " from TeePerson p where"
				+ " p.deleteStatus <> '1' and ("
				+ " p.userId like '%"+TeeDbUtility.formatString(word)+"%'"
				+ " or p.userName like '%"+TeeDbUtility.formatString(word)+"%'"
				+ " or p.dept.deptName like '%"+TeeDbUtility.formatString(word)+"%'"
				+ " or p.userRole.roleName like '%"+TeeDbUtility.formatString(word)+"%' ) order by p.userNo asc";
		
		List<Map> userList = null;
		if(!word.trim().equals("")){
			userList = simpleDaoSupport.getMaps(hql, null);
		}else{
			userList = new ArrayList();
		}
		return userList;
	}
	
	public List<Map> quickSearchWork(TeePerson loginUser,Map requestDatas){
		String word = TeeStringUtil.getString(requestDatas.get("word"));
		//查询相关工作流
		String hql = "select "
				+ "fr.runName as runName,"
				+ "fr.runId as runId "
				+ " from TeeFlowRun fr where ("
				+ " fr.runName like '%"+TeeDbUtility.formatString(word)+"%'"
				+ " or fr.runId="+TeeStringUtil.getInteger(word, 0)+" "
				+ " ) and exists (select 1 from TeeFlowRunPrcs frp where frp.flowRun=fr and frp.prcsUser = "+loginUser.getUuid()+") order by fr.runId desc";
		
		List<Map> workList = simpleDaoSupport.getMaps(hql, null);
		if(!word.trim().equals("")){
			workList = simpleDaoSupport.getMaps(hql, null);
		}else{
			workList = new ArrayList();
		}
		return workList;
	}
	
	public Map quickSearchCount(String word,TeePerson loginUser){
		Map map = new HashMap();
		
		
		//任务
		boolean isAdmin = TeePersonService.checkIsAdminPriv(loginUser);//判断你是否是管理员
		String hql = "select count(sid) from TeeCoWorkTask task where 1=1 ";
		if (!isAdmin) {
			hql += " and task.createUser.uuid="+loginUser.getUuid();
		}

		if(!"".equals(word) && word!=null){
			hql+=" and task.taskTitle like '%"+TeeDbUtility.formatString(word)+"%'";
		}else{
			hql+=" and 1!=1";
		}
		
		hql += " and ((task.auditor.uuid="+loginUser.getUuid()+" and task.status=1) "
		+ " or (task.charger.uuid="+loginUser.getUuid()+" and task.status !=1) "
		+ " or (exists (select 1 from task.joiners j where j.uuid in ("+loginUser.getUuid()+"))  and task.status !=1))) ";
		
		map.put("task", simpleDaoSupport.count(hql, null));
		
		//公告
		hql = "select count(sid) from TeeNotify where 1=1 ";
		
		if (!isAdmin) {
			hql += " and fromPerson.uuid="+loginUser.getUuid();
		}
		if(!"".equals(word) && word!=null){//根据关键字条件查询
			hql+=" and subject like '%"+TeeDbUtility.formatString(word)+"%'";
		}else{
			hql+=" and 1!=1";
		}
		map.put("notify", simpleDaoSupport.count(hql, null));
		
		//文档
		hql = "select count(sid) from TeeFileNetdisk where 1=1 ";
		if (!isAdmin) {
			hql += " and creater.uuid="+loginUser.getUuid();
		}
		if(!"".equals(word) && word!=null){
			hql+=" and fileName like '%"+TeeDbUtility.formatString(word)+"%'";
		}else{
			hql+=" and 1!=1";
		}
		map.put("document", simpleDaoSupport.count(hql, null));
		
		//会议
		hql = "select count(sid) from TeeMeeting m where user.uuid= "+loginUser.getUuid();

		if(!"".equals(word) && word!=null){
			hql+=" and meetName like '%"+TeeDbUtility.formatString(word)+"%'";
		}else{
			hql+=" and 1!=1";
		}
		map.put("meeting", simpleDaoSupport.count(hql, null));
		
		//流程
		hql = "select count(runId) from TeeFlowRun where 1=1 ";
		if (!isAdmin) {
			hql += " beginPerson.uuid="+loginUser.getUuid();
		}
		if(!"".equals(word) && word!=null){
			hql+=" and runName like '%"+TeeDbUtility.formatString(word)+"%'";
		}else{
			hql+=" and 1!=1";
		}
		map.put("flowRun", simpleDaoSupport.count(hql, null));
		
		
		//用户
		hql = "select count(uuid) from TeePerson where deleteStatus <> '1' ";
		if(!"".equals(word) && word!=null){
			hql+=" and (userName like '%"+TeeDbUtility.formatString(word)+"%' or userId like '%"+TeeDbUtility.formatString(word)+"%')";
		}else{
			hql+=" and 1!=1";
		}
		map.put("user", simpleDaoSupport.count(hql, null));
		
		//讨论
		hql = "select count(uuid) from TeeTopic t where 1 = 1 ";
		if (!isAdmin) {
			hql += " and crUser.uuid="+loginUser.getUuid(); 
		}
		if(!"".equals(word) && word!=null){
			hql+=" and subject like '%"+TeeDbUtility.formatString(word)+"%'";
		}else{
			hql+=" and 1!=1";
		}
		map.put("discussion", simpleDaoSupport.count(hql, null));
		
		//协同
		hql="select count(sid) from TeeXTRun where 1=1 ";
		if (!isAdmin) {
			hql += "and createUser.uuid="+loginUser.getUuid(); 
		}
		if(!"".equals(word) && word!=null){
			hql+=" and subject like '%"+TeeDbUtility.formatString(word)+"%'";
		}else{
			hql+=" and 1!=1";
		}
		map.put("cowork", simpleDaoSupport.count(hql, null));
		
		return map;
		
	}
	
	public TeeEasyuiDataGridJson quickSearchList(String word,int type,TeeDataGridModel dataGridModel,TeePerson loginUser){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List<Map> list = null;
		if(type==1){//任务
			boolean isAdmin = TeePersonService.checkIsAdminPriv(loginUser);//判断你是否是管理员
			String hql = "from TeeCoWorkTask task where 1=1 ";
			if (!isAdmin) {
				hql += " and task.createUser.uuid="+loginUser.getUuid();
			}
			if(!"".equals(word) && word!=null){
				hql+=" and task.taskTitle like '%"+TeeDbUtility.formatString(word)+"%'";
			}else{
				hql+=" and 1!=1";
			}
			hql += " and ((task.auditor.uuid="+loginUser.getUuid()+" and task.status=1) "
			+ " or (task.charger.uuid="+loginUser.getUuid()+" and task.status !=1) "
			+ " or (exists (select 1 from task.joiners j where j.uuid in ("+loginUser.getUuid()+")) and task.status !=1))) ";
			
			dataGridJson.setTotal(simpleDaoSupport.count("select count(sid) "+hql, null));
			hql += " order by task.sid desc";
			
			list = simpleDaoSupport.getMaps("select task.taskTitle as taskTitle,task.sid as sid,task.status as status,task.progress as progress "+hql, null, dataGridModel.getFirstResult(), dataGridModel.getRows());
 		}else if(type==2){//公告
			boolean isAdmin = TeePersonService.checkIsAdminPriv(loginUser);//判断你是否是管理员
			String hql = "from TeeNotify n where n.publish='"+1+"'";
			
			if (!isAdmin) {
				hql += " and n.fromPerson.uuid="+loginUser.getUuid();
			}
			if(!"".equals(word) && word!=null){//根据关键字条件查询
				hql+=" and subject like '%"+TeeDbUtility.formatString(word)+"%'";
			}else{
				hql+=" and 1!=1";
			}
			
			String totalHql = " select count(*) " + hql;
			dataGridJson.setTotal(notifyDao.countByList(totalHql, null));// 设置总记录数
			hql += " order by n.top desc,n.createDate desc" ;
			list = simpleDaoSupport.getMaps("select n.sid as sid,n.subject as subject,n.createDate as createDate,n.sendTime as sendTime,n.publish as publish "+hql, null, dataGridModel.getFirstResult(), dataGridModel.getRows());
			
		}else if(type==3){//文档
			String hql = " from TeeFileNetdisk where 1=1 ";
			boolean isAdmin = TeePersonService.checkIsAdminPriv(loginUser);//判断你是否是管理员
			if (!isAdmin) {
				hql += " and creater.uuid="+loginUser.getUuid();
			}
			if(!"".equals(word) && word!=null){
				hql+=" and fileName like '%"+TeeDbUtility.formatString(word)+"%'";
			}else{
				hql+=" and 1!=1";
			}
			
			dataGridJson.setTotal(simpleDaoSupport.count("select count(sid) "+hql, null));
			hql += " order by createTime desc";
			List<TeeFileNetdisk> disks = simpleDaoSupport.pageFind(hql, dataGridModel.getFirstResult(), dataGridModel.getRows(), null);
			list = new ArrayList();
			for(TeeFileNetdisk disk:disks){
				Map data = new HashMap();
				data.put("fileName",disk.getFileName());
				if(disk.getAttachemnt()!=null){
					data.put("attachId",disk.getAttachemnt().getSid());
					data.put("ext",disk.getAttachemnt().getExt());
				}
				list.add(data);
			}
			//			list = simpleDaoSupport.getMaps("select fileName as fileName,attachemnt.sid as attachId, "+hql, null, );
			
		}else if(type==4){//会议
			String hql = "from TeeMeeting m where 1=1 ";
			boolean isAdmin = TeePersonService.checkIsAdminPriv(loginUser);//判断你是否是管理员
			if (!isAdmin) {
				hql += " and user.uuid= "+loginUser.getUuid() ;
				hql += " or (exists (select 1 from m.attendeeList a where a.uuid in ("+loginUser.getUuid()+")))";
			}
			if(!"".equals(word) && word!=null){
				hql+=" and meetName like '%"+TeeDbUtility.formatString(word)+"%'";
			}else{
				hql+=" and 1!=1";
			}
			dataGridJson.setTotal(simpleDaoSupport.count("select count(sid) "+hql, null));
			
			list = simpleDaoSupport.getMaps("select m.sid as sid,m.meetName as meetName,m.startTime as startTime,m.endTime as endTime,m.status as status "+hql, null, dataGridModel.getFirstResult(), dataGridModel.getRows());
		}else if(type==5){//流程
			String hql = "from TeeFlowRun where 1=1 ";
			boolean isAdmin = TeePersonService.checkIsAdminPriv(loginUser);//判断你是否是管理员
			if (!isAdmin) {
				hql += " beginPerson.uuid="+loginUser.getUuid();
			}
			if(!"".equals(word) && word!=null){
				hql+=" and runName like '%"+TeeDbUtility.formatString(word)+"%'";
			}else{
				hql+=" and 1!=1";
			}
			
			dataGridJson.setTotal(simpleDaoSupport.count("select count(runId) "+hql, null));
			hql += " order by runId desc";
			list = simpleDaoSupport.getMaps("select runId as runId,runName as runName "+hql, null, dataGridModel.getFirstResult(), dataGridModel.getRows());
			
		}else if(type==6){//用户
			String hql = " from TeePerson where deleteStatus <> '1' ";
			Map<String, Object> map = new HashMap<>();
			if(!"".equals(word) && word!=null){
				hql+=" and (userName like '%"+TeeDbUtility.formatString(word)+"%' or userId like '%"+TeeDbUtility.formatString(word)+"%')";
			}else{
				hql+=" and 1!=1";
			}
			
			dataGridJson.setTotal(simpleDaoSupport.count("select count(uuid) "+hql, null));
			hql += " order by uuid desc";
			list = simpleDaoSupport.getMaps("select uuid as uuid,userName as userName "+hql, null, dataGridModel.getFirstResult(), dataGridModel.getRows());
			if (list != null && list.size() > 0) {
				for (Map personMap : list) {
					int uuid = TeeStringUtil.getInteger(personMap.get("uuid"), 0);
					TeePerson person = (TeePerson) simpleDaoSupport.get(TeePerson.class, uuid);
					if (person != null) {
						personMap.put("deptName", person.getDept().getDeptName());
						personMap.put("telephone", person.getTelNoDept());
						personMap.put("email", person.getEmail());
					}
				}
			}
		}else if(type==7){//讨论
			String hql = " from TeeTopic t where 1 = 1 ";
			boolean isAdmin = TeePersonService.checkIsAdminPriv(loginUser);//判断你是否是管理员
			if (!isAdmin) {
				hql += " and crUser.uuid="+loginUser.getUuid(); 
			}
			Map map = new HashMap<>();
			if(!"".equals(word) && word!=null){
				hql+=" and subject like '%"+TeeDbUtility.formatString(word)+"%'";
			}else{
				hql+=" and 1!=1";
			}
			
			dataGridJson.setTotal(simpleDaoSupport.count("select count(uuid) "+hql, null));
			hql += " order by uuid desc";
			list = simpleDaoSupport.getMaps("select uuid as uuid,subject as subject "+hql, null, dataGridModel.getFirstResult(), dataGridModel.getRows());
			if (list != null && list.size() > 0) {
				for (Map topicMap : list) {
					String topicId = (String) topicMap.get("uuid");
					TeeTopic topic  = (TeeTopic) simpleDaoSupport.get(TeeTopic.class,topicId);
					if (topic != null) {
						topicMap.put("crUser", topic.getCrUser().getUserName());
						topicMap.put("reply", topic.getReplyAmount());
						topicMap.put("click", topic.getClickAccount());
						topicMap.put("crTime", topic.getCrTime());
						topicMap.put("sectionId", topic.getSection().getUuid());
					}
				}
			}
		}else if(type==8){//协同
			String hql=" from TeeXTRun where 1=1 ";
			boolean isAdmin = TeePersonService.checkIsAdminPriv(loginUser);//判断你是否是管理员
			if (!isAdmin) {
				hql += "and createUser.uuid="+loginUser.getUuid(); 
			}
			if(!"".equals(word) && word!=null){
				hql+=" and subject like '%"+TeeDbUtility.formatString(word)+"%'";
			}else{
				hql+=" and 1!=1";
			}
			
			dataGridJson.setTotal(simpleDaoSupport.count("select count(sid) "+hql, null));
			hql += " order by sid desc";
			list = simpleDaoSupport.getMaps("select sid as sid,subject as subject "+hql, null, dataGridModel.getFirstResult(), dataGridModel.getRows());
			
			if (list != null && list.size() > 0) {
				for (Map xtMap : list) {
					int sid = (int) xtMap.get("sid");
					TeeXTRun cowork = (TeeXTRun) simpleDaoSupport.get(TeeXTRun.class, sid);
					xtMap.put("createUser", cowork.getCreateUser().getUserName());
					xtMap.put("createTime", cowork.getCreateTime());
					xtMap.put("level", cowork.getImportantLevel());
					xtMap.put("status", cowork.getStatus());
				}
			}
		}
		dataGridJson.setRows(list);
		return dataGridJson;
	}
}
