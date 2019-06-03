package com.tianee.oa.core.base.address.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.address.bean.TeeAddressGroup;
import com.tianee.oa.core.base.address.dao.TeeAddressGroupDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class TeeAddressGroupService extends TeeBaseService {

	@Autowired
	private TeeAddressGroupDao addressGroupDao;

	/**
	 * 添加公共通讯簿组
	 * @author zhp
	 * @createTime 2014-1-4
	 * @editTime 上午08:16:38
	 * @desc
	 */
	@TeeLoggingAnt(template="新建公共通讯薄分组，【名称：{request.groupName},编号:{request.orderNo},发布范围部门:{request.toName}，发布范围人员:{request.userName} , 发布范围角色：{request.privName} 】",type="027A")
	public void addPublicAddressGroupService(HttpServletRequest request , TeeAddressGroup group){
		addressGroupDao.save(group);
	}
	
	/**
	 * 添加个人通讯簿组
	 * @author zhp
	 * @createTime 2014-1-4
	 * @editTime 上午08:16:38
	 * @desc
	 */
	public void addAddressGroupService(HttpServletRequest request , TeeAddressGroup group){
		addressGroupDao.save(group);
	}
	
	/**
	 * 获取 通讯簿
	 * @author zhp
	 * @createTime 2014-1-3
	 * @editTime 下午01:26:03
	 * @desc
	 */
	public TeeAddressGroup getAddressGroupById(int aid){
		String hql = "from TeeAddressGroup a where a.seqId = "+aid;
		 return addressGroupDao.loadSingleObject(hql, null);
	}
	
	/**
	 * 删除 公共通讯薄组 以及 组下的记录
	 * @author zhp
	 * @createTime 2014-1-9
	 * @editTime 上午11:03:07
	 * @desc
	 */
	@TeeLoggingAnt(template="删除公共通讯薄分组，{logModel.remark}",type="027D")
	public void delAddressGroupService(int groupId){
		TeeAddressGroup group = addressGroupDao.get(groupId);
		String remark = "";
		if(group != null){
			remark = "【名称：" + group.getGroupName() + "编号:" + group.getOrderNo()+ " 】";
			String hql = "delete from TeeAddressGroup ag where ag.seqId = "+groupId+"";
			String hql1 = "delete from TeeAddress a where a.groupId = "+groupId+"";
			addressGroupDao.deleteOrUpdateByQuery(hql, null);
			addressGroupDao.deleteOrUpdateByQuery(hql1, null);
		}
		TeeRequestInfoContext.getRequestInfo().getLogModel().put("remark", remark);//添加其他参数
		
	}
	
	/**
	 * 删除个人 通讯薄组 以及 组下的记录
	 * @author zhp
	 * @createTime 2014-1-9
	 * @editTime 上午11:03:07
	 * @desc
	 */
	public void delAddress(int groupId){
		String hql = "delete from TeeAddressGroup ag where ag.seqId = "+groupId+"";
		String hql1 = "delete from TeeAddress a where a.groupId = "+groupId+"";
		addressGroupDao.deleteOrUpdateByQuery(hql, null);
		addressGroupDao.deleteOrUpdateByQuery(hql1, null);
	}
	 
	
	/**
	 * 清空公共通讯簿组 的通讯簿
	 * @author zhp
	 * @createTime 2014-2-12
	 * @editTime 下午11:27:46
	 * @desc
	 */
	@TeeLoggingAnt(template="清空公共通讯薄分组人员，{logModel.remark}",type="027B")
	 public void emptyPupublicAddressGroupsService(boolean isPub,int groupId){
		TeeAddressGroup group = addressGroupDao.get(groupId);
		String remark = "";
		if(group != null){
			remark = "【名称：" + group.getGroupName() + "编号:" + group.getOrderNo()+ " 】";
			String hql = "delete from TeeAddress a where a.groupId = "+groupId+"";
			if(isPub){
				hql = hql + " and a.userId is null";
			}
			addressGroupDao.deleteOrUpdateByQuery(hql, null);
		}else{//公共默认分组
			remark = "【名称：" + "默认" + "编号:" +"0"+ " 】";
			String hql = "delete from TeeAddress a where a.groupId = "+groupId+"";
			if(isPub){
				hql = hql + " and a.userId is null";
			}
			addressGroupDao.deleteOrUpdateByQuery(hql, null);
		}
		TeeRequestInfoContext.getRequestInfo().getLogModel().put("remark", remark);//添加其他参数
	}
	/**
	 * 清空通讯簿组 的通讯簿
	 * @author zhp
	 * @createTime 2014-2-12
	 * @editTime 下午11:27:46
	 * @desc
	 */
	 public void emptyAddressGroups(boolean isPub,int groupId,TeePerson loginUser){
		 
			String hql = "delete from TeeAddress a where a.groupId = "+groupId+"";
			if(isPub){
				hql = hql + " and a.userId is null";
			}
			
			//判断 个人默认分组清空  只能清空属于个人的
			if(!isPub&&groupId==0){
				hql = hql + " and a.userId="+loginUser.getUuid();
			}
			addressGroupDao.deleteOrUpdateByQuery(hql, null);
	}
	
	/**
	 * 删除关联通讯簿组的通讯薄
	 * @author zhp
	 * @createTime 2014-1-4
	 * @editTime 上午01:01:13
	 * @desc
	 */
	public void delAddressByGroupId(int groupId){
		String hql = "delete from TeeAddress a where a.groupId = "+groupId+"";
		addressGroupDao.deleteOrUpdateByQuery(hql, null);
	}
	
	/**
	 * 更新公共通讯薄分组
	 * @author syl
	 * @date 2014-4-26
	 * @param group
	 */
	@TeeLoggingAnt(template="更新公共通讯薄分组，【名称：{request.groupName},编号:{request.orderNo},发布范围部门:{request.toName}，发布范围人员:{request.userName} , 发布范围角色：{request.privName} 】",type="027C")
	public void updatePublicAddressGroupService(HttpServletRequest request, TeeAddressGroup group){
		addressGroupDao.update(group);
	}
	
	public void updateAddressGroup(TeeAddressGroup group){
		addressGroupDao.update(group);
	}
	
	/**
	 * 获取个人通讯簿组  以及个人有权限的共同通讯薄组
	 * @author zhp
	 * @createTime 2014-1-4
	 * @editTime 上午07:54:05
	 * @desc
	 */
	public List getAddressGroups(TeePerson person){
		String userId = "%,"+person.getUuid()+",%";
		String toDeptIds =  "%,"+person.getDept().getUuid()+",%";
		String toRolesIds =  "%,"+person.getUserRole().getUuid()+",%";
		String hql = "from TeeAddressGroup ag  where (ag.userId is null and  (ag.toDeptIds like '"+toDeptIds+"' or ag.toRolesIds like '"+toRolesIds+"' or ag.toUserIds like '"+userId+"' ) ) or ag.userId = "+person.getUuid() ;
		hql = hql + " order by orderNo";
		return addressGroupDao.executeQuery(hql, new Object[]{});
	}
	
	public List getPublicAddressGroups(TeePerson person){
		String userId = "%,"+person.getUuid()+",%";
		String toDeptIds =  "%,"+person.getDept().getUuid()+",%";
		String toRolesIds =  "%,"+person.getUserRole().getUuid()+",%";
		//String hql = "from TeeAddressGroup ag  where ag.userId is null and  (ag.toDeptIds like ? or ag.toRolesIds like ? or ag.toUserIds like ? )" ;
		String hql = "from TeeAddressGroup ag  where ag.userId is null and  (ag.toDeptIds like '"+toDeptIds+"' or ag.toRolesIds like '" + toRolesIds + "' or ag.toUserIds like '"+userId+"' )";
		
		return addressGroupDao.executeQuery(hql, new Object[]{});
	}
	
	/**
	 * 获取 邮件组id拼成的串
	 * @author zhp
	 * @createTime 2014-2-10
	 * @editTime 下午11:09:16
	 * @desc
	 */
	public String getAddressGroups2String(TeePerson person){
		List list = null;
		list = getPublicAddressGroups(person);
		String rsult = "";
		for(int i=0;list != null&&i<list.size();i++){
			TeeAddressGroup group = (TeeAddressGroup)list.get(i);
			rsult = rsult +group.getSeqId()+ ",";
		}
		if(rsult.endsWith(",")){
			rsult  = rsult.substring(0,rsult.length()-1);
		}
		return rsult;
	}
	/**
	 * 获取个人有权限的公共通讯薄组 以及 个人通讯薄组
	 * @author zhp
	 * @createTime 2014-2-12
	 * @editTime 下午09:40:40
	 * @desc
	 */
	public String getPublicAndPrivateAddressGroups2String(TeePerson person){
		List list = null;
		list = getAddressGroups(person);
		String rsult = "";
		for(int i=0;list != null&&i<list.size();i++){
			TeeAddressGroup group = (TeeAddressGroup)list.get(i);
			rsult = rsult +group.getSeqId()+ ",";
		}
		if(rsult.endsWith(",")){
			rsult  = rsult.substring(0,rsult.length()-1);
		}
		return rsult;
	}
	/**
	 * 获取公共通讯薄组
	 * @author zhp
	 * @createTime 2014-1-4
	 * @editTime 上午07:53:50
	 * @desc
	 */
	public List getPublicAddressGroups(){
		String hql = "from TeeAddressGroup  ag  where ag.userId is null order by orderNo ";
		return addressGroupDao.executeQuery(hql,null);
	}
	
	/**
	 * 获取公共通讯薄组 (当前登录人有权限的)
	 * @author zhp
	 * @createTime 2014-1-4
	 * @editTime 上午07:53:50
	 * @desc
	 */
	public List getPublicAddressGroups2Priv(TeePerson person){
		String userId = "%,"+person.getUuid()+",%";
		String toDeptIds =  "%,"+person.getDept().getUuid()+",%";
		String toRolesIds =  "%,"+person.getUserRole().getUuid()+",%";
		String hql = "from TeeAddressGroup ag  where ag.userId is null and  (ag.toDeptIds like '"+toDeptIds+"' or ag.toRolesIds like '" + toRolesIds + "' or ag.toUserIds like '"+userId+"' )";
		return addressGroupDao.executeQuery(hql, new Object[]{});
	}
	
	/**
	 * 获取有权限的公共分组
	 * @param loginPerson
	 * @return
	 */
	public List getPubAddressGroups(TeePerson person) {
		String userId = "%,"+person.getUuid()+",%";
		String toDeptIds =  "%,"+person.getDept().getUuid()+",%";
		String toRolesIds =  "%,"+person.getUserRole().getUuid()+",%";
		String hql = "from TeeAddressGroup ag  where ag.userId is null and  (ag.toDeptIds like '"+toDeptIds+"' or ag.toRolesIds like '"+toRolesIds+"' or ag.toUserIds like '"+userId+"' ) ";
		hql = hql + " order by orderNo";
		return addressGroupDao.executeQuery(hql, new Object[]{});
	}
	
	
	
	public TeeAddressGroupDao getAddressGroupDao() {
		return addressGroupDao;
	}

	public void setAddressGroupDao(TeeAddressGroupDao addressGroupDao) {
		this.addressGroupDao = addressGroupDao;
	}

	
	
	
	
	
	
	
}
