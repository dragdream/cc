package com.tianee.oa.subsys.timeline.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.subsys.timeline.bean.TeeTimeline;
import com.tianee.oa.subsys.timeline.model.TeeTimelineModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeUtility;


@Repository("TeeTimelineDao")
public class TeeTimelineDao extends TeeBaseDao<TeeTimeline>{
	/**
	 * @author nieyi
	 * @param timeline
	 */
	public void addTimeline(TeeTimeline timeline) {
		save(timeline);
	}
	
	/**
	 * @author nieyi
	 * @param timeline
	 */
	public void updateTimeline(TeeTimeline timeline) {
		update(timeline);
	}
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeTimeline loadById(int id) {
		TeeTimeline intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeTimeline getById(String id) {
		TeeTimeline intf = get(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 */
	public void delById(int id) {
		delete(id);
	}
	
	
	/**
	 * @author nieyi
	 * @param ids
	 */
	public void delByIds(String ids){
		
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids= ids.substring(0, ids.length() -1 );
			}
			String hql = "delete from TeeTimeline where uuid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeePerson person = (TeePerson)requestDatas.get("LOGIN_USER");
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String title=(String)requestDatas.get("title");
		String tag=(String)requestDatas.get("tag");
		String type=(String)requestDatas.get("type");
		List param = new ArrayList();
		String hql = "from TeeTimeline timeLine where 1=1";
		//判断查看权限
		hql+="and (timeLine.crUser.uuid="+person.getUuid()+" or timeLine.updateUser.uuid="+person.getUuid()+" or ";
		hql+=" exists(select 1 from timeLine.viewUser user where user.uuid = "+person.getUuid()+" ) or exists(select 1 from timeLine.viewDept dept where dept.uuid="+person.getDept().getUuid()+")";
		hql+=" or exists(select 1 from timeLine.viewRole role where role.uuid = "+person.getUserRole().getUuid()+")";
		//判断管理权限
		hql+="or exists(select 1 from timeLine.postUser u where u.uuid = "+person.getUuid()+" ) or exists(select 1 from timeLine.postDept d where d.uuid="+person.getDept().getUuid()+")";
		hql+=" or exists(select 1 from timeLine.postRole r where r.uuid = "+person.getUserRole().getUuid()+"))";
		if(!TeeUtility.isNullorEmpty(title)){
			hql+=" and timeLine.title like '%"+TeeDbUtility.formatString(title)+"%'";
		}
		if(!TeeUtility.isNullorEmpty(tag)){
			hql+=" and timeLine.tag like '%/"+TeeDbUtility.formatString(tag)+"/%'";
		}
		if(!TeeUtility.isNullorEmpty(type) && !"0".equals(type)){
			hql+=" and timeLine.type = ?";
			param.add(type);
		}
		
		List<TeeTimeline> infos = super.pageFindByList(hql+" order by timeLine.startTime desc", (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = countByList("select count(*) "+hql, param);
		List<TeeTimelineModel> models = new ArrayList<TeeTimelineModel>();
		for(TeeTimeline timeline:infos){
			TeeTimelineModel m = new TeeTimelineModel();
			m=parseModel(timeline);	
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	
	/**
	 * 对象转换
	 * @author nieyi
	 * @param timeline
	 * @return
	 */
	public TeeTimelineModel parseModel(TeeTimeline timeline){
		TeeTimelineModel model = new TeeTimelineModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(timeline == null){
			return null;
		}
		BeanUtils.copyProperties(timeline, model);
		if(!TeeUtility.isNullorEmpty(timeline.getViewUser())){
			String viewUserIds="";
			String viewUserNames="";
			for(TeePerson person:timeline.getViewUser()){
				viewUserIds += person.getUuid()+",";
				viewUserNames+=person.getUserName()+",";
			}
			model.setViewUserIds(viewUserIds);
			model.setViewUserNames(viewUserNames);
		}
		if(!TeeUtility.isNullorEmpty(timeline.getViewDept())){
			String viewDeptIds="";
			String viewDeptNames="";
			for(TeeDepartment dept:timeline.getViewDept()){
				viewDeptIds += dept.getUuid()+",";
				viewDeptNames+=dept.getDeptName()+",";
			}
			model.setViewDeptIds(viewDeptIds);
			model.setViewDeptNames(viewDeptNames);
		}
		if(!TeeUtility.isNullorEmpty(timeline.getViewRole())){
			String viewRoleIds="";
			String viewRoleNames="";
			for(TeeUserRole role:timeline.getViewRole()){
				viewRoleIds += role.getUuid()+",";
				viewRoleNames+=role.getRoleName()+",";
			}
			model.setViewRoleIds(viewRoleIds);
			model.setViewRoleNames(viewRoleNames);
		}
		if(!TeeUtility.isNullorEmpty(timeline.getPostUser())){
			String postUserIds="";
			String postUserNames="";
			for(TeePerson person:timeline.getViewUser()){
				postUserIds += person.getUuid()+",";
				postUserNames+=person.getUserName()+",";
			}
			model.setPostUserIds(postUserIds);
			model.setPostUserNames(postUserNames);
		}
		if(!TeeUtility.isNullorEmpty(timeline.getPostDept())){
			String postDeptIds="";
			String postDeptNames="";
			for(TeeDepartment dept:timeline.getPostDept()){
				postDeptIds += dept.getUuid()+",";
				postDeptNames+=dept.getDeptName()+",";
			}
			model.setPostDeptIds(postDeptIds);
			model.setPostDeptNames(postDeptNames);
		}
		if(!TeeUtility.isNullorEmpty(timeline.getPostRole())){
			String postRoleIds="";
			String postRoleNames="";
			for(TeeUserRole role:timeline.getPostRole()){
				postRoleIds += role.getUuid()+",";
				postRoleNames +=role.getRoleName()+",";
			}
			model.setPostRoleIds(postRoleIds);
			model.setPostRoleNames(postRoleNames);
		}
		if(!TeeUtility.isNullorEmpty(timeline.getStartTime())){
			model.setStartTimeDesc(sf.format(timeline.getStartTime().getTime()));
		}
		if(!TeeUtility.isNullorEmpty(timeline.getEndTime())){
			model.setEndTimeDesc(sf.format(timeline.getEndTime().getTime()));
		}
		if(!TeeUtility.isNullorEmpty(timeline.getCrTime())){
			model.setCrTimeDesc(sf.format(timeline.getCrTime().getTime()));
		}
		if(!TeeUtility.isNullorEmpty(timeline.getUpdateTime())){
			model.setUpdateTimeDesc(sf.format(timeline.getUpdateTime().getTime()));
		}
		if(!TeeUtility.isNullorEmpty(timeline.getCrUser())){
			model.setCrUserId(timeline.getCrUser().getUuid());
			model.setCrUserName(timeline.getCrUser().getUserName());
		}
		if(!TeeUtility.isNullorEmpty(timeline.getUpdateUser())){
			model.setUpdateUserId(timeline.getUpdateUser().getUuid());
			model.setUpdateUserName(timeline.getUpdateUser().getUserName());
		}
		if(!TeeUtility.isNullorEmpty(model.getType())){
			String typeDesc = TeeSysCodeManager.getChildSysCodeNameCodeNo("TIMELINE_TYPE",model.getType());
			model.setTypeDesc(typeDesc);
		}
		return model;
	}

	/**
	 * 根据条件查出所有数据
	 * @param requestDatas
	 * @return
	 */
	public List<TeeTimelineModel> getTotalByConditon(Map requestDatas) {
		TeePerson person = (TeePerson)requestDatas.get("LOGIN_USER");
		String title = (String)requestDatas.get("title");
		String tag=(String)requestDatas.get("tag");
		String type=(String)requestDatas.get("type");//type=2 代表所有的客户，type =1 共享客户  没有type代表我的客户
		List param = new ArrayList();
		String hql = "from TeeTimeline customer where 1=1 ";
		
		if(title!=null && !"".equals(title)){
			hql+=" and ";
		}
		
		List<TeeTimeline> infos = super.executeQueryByList(hql, param);
		List<TeeTimelineModel> models = new ArrayList<TeeTimelineModel>();
		for(TeeTimeline timeline:infos){
			TeeTimelineModel m = new TeeTimelineModel();
			m=parseModel(timeline);	
			models.add(m);
		}
		return models;
	}
}