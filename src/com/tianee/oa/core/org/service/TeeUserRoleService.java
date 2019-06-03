package com.tianee.oa.core.org.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.bean.TeeUserRoleType;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.core.org.dao.TeeUserRoleTypeDao;
import com.tianee.oa.core.org.model.TeeUserRoleModel;
import com.tianee.oa.core.workFlowFrame.dataloader.TeeFixedFlowDataLoaderInterface;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;
@Service
public class TeeUserRoleService extends TeeBaseService {

	@Autowired
	private TeeUserRoleDao userRolelDao;
	
	@Autowired
	private TeeUserRoleTypeDao roleTypeDao;
	
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeFixedFlowDataLoaderInterface fixedFlowDataLoader;
	
	
	/**
	 * 新增角色
	 * @param role1
	 */
	@TeeLoggingAnt(template="新增角色 [{$1.roleName}]",type="004A")
	public void addRoleService(TeeUserRoleModel role1 ) {
		TeeUserRole role = new TeeUserRole();
		BeanUtils.copyProperties(role1, role);
		TeeUserRoleType type = null;
		if(role1.getRoleTypeId() > 0){
			type = roleTypeDao.get(role1.getRoleTypeId());
		}
		if(role1.getDeptId()!=0){
			TeeDepartment dept = new TeeDepartment();
			dept.setUuid(role1.getDeptId());
			role.setDept(dept);
		}
		role.setUserRoleType(type);
		userRolelDao.save(role);
	}
	
	/**
	 * 更新岗位薪资
	 * @param roleModel
	 */
	public void updateRoleSalary(TeeUserRoleModel roleModel) {
		TeeUserRole role = (TeeUserRole)userRolelDao.get(roleModel.getUuid());
		role.setSalary(roleModel.getSalary());
		role.setSalaryLevelModel(roleModel.getSalaryLevelModel());
		userRolelDao.update(role);
	}
	
	/**
	 * 编辑角色
	 * @param role1
	 */
	@TeeLoggingAnt(template="修改角色信息[原角色ID={$1.uuid}] [{$1.roleName}]",type="004B")
	public void editRoleService(TeeUserRoleModel role1 ) {
		TeeUserRole role = null;
		role = (TeeUserRole)userRolelDao.get( role1.getUuid());
		BeanUtils.copyProperties(role1, role);
		TeeUserRoleType type = null;
		if(role1.getRoleTypeId() > 0){
			type = roleTypeDao.get(role1.getRoleTypeId());
		}
		if(role1.getDeptId()!=0){
			TeeDepartment dept = new TeeDepartment();
			dept.setUuid(role1.getDeptId());
			role.setDept(dept);
		}else{
			role.setDept(null);
		}
		role.setUserRoleType(type);
		userRolelDao.update(role);
	}
	/**
	 * 加载ById
	 * @param uid
	 * @return
	 */
	public TeeUserRoleModel loadRoleById(int uid) {
		TeeUserRoleModel roleModel = new TeeUserRoleModel();
		TeeUserRole ur = userRolelDao.get(uid);
		if(ur != null){
			BeanUtils.copyProperties(ur, roleModel);
			if(ur.getUserRoleType()!=null){
				roleModel.setRoleTypeId(ur.getUserRoleType().getSid());
			}
			if(ur.getDept()!=null){
				roleModel.setDeptName(ur.getDept().getDeptName());
				roleModel.setDeptId(ur.getDept().getUuid());
			}
		}
		
		return roleModel;
	}
	/**
	 * 通用分页
	 * @param dm
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson datagrid(Map params,TeeDataGridModel dm) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		List<Object> values = new ArrayList<Object>();
		String hql = " from TeeUserRole t where 1=1 ";
		int deptId = TeeStringUtil.getInteger(params.get("deptId"), 0);
		if(deptId==0){
			hql+= " and dept is null ";
		}else{
			hql+= " and dept.uuid = "+deptId;
		}
		
		String totalHql = " select count(*) " + hql;
		j.setTotal(userRolelDao.count(totalHql,null));// 设置总记录数
		hql += " order by t.roleNo asc,t.uuid asc";
		//List<TeeUserRole> roles = userRolelDao.loadList(hql, dm.getPage(),dm.getRows(),values);// 查询
		List<TeeUserRole> roles =simpleDaoSupport.pageFind(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查询
		
		List<TeeUserRoleModel> rolemodel = new ArrayList<TeeUserRoleModel>();
		if (roles != null && roles.size() > 0) {
			for (TeeUserRole role : roles) {
				TeeUserRoleModel um = new TeeUserRoleModel();
				BeanUtils.copyProperties(role, um);
				rolemodel.add(um);
				if(role.getUserRoleType()!=null){
					um.setRoleTypeName(role.getUserRoleType().getTypeName());
				}else{
					um.setRoleTypeName("默认分类");
				}
				//设置   主角色用户数/辅助角色用户数
				um.setStatistics(getStatisticsByRoleId(role.getUuid(),0));
				
			}
		}
		j.setRows(rolemodel);// 设置返回的行
		return j;
	}
	
	/**
	 * 根据角色id 获取   主角色用戶數/辅助角色用户数 
	 * @param uuid
	 * @return
	 */
	private String getStatisticsByRoleId(int roleId,int deptId) {
		String statistics="";
		long zNum=0;
		long fNum=0;
		
		//获取主角色用户数
		if(deptId>0){
			zNum=simpleDaoSupport.count("select count(p.uuid) from TeePerson p where userRole.uuid=?  and p.deleteStatus<>1 and p.dept.uuid=? ", new Object[]{roleId,deptId});
		}else{
			zNum=simpleDaoSupport.count("select count(p.uuid) from TeePerson p where userRole.uuid=?  and p.deleteStatus<>1 ", new Object[]{roleId});
		}
		
		
		//获取辅助角色用户数
		if(deptId>0){
			fNum=simpleDaoSupport.count("select count(p.uuid) from TeePerson p where p.deleteStatus<>1 and exists(select 1 from p.userRoleOther ou where ou.uuid=? ) and p.dept.uuid=? ", new Object[]{roleId,deptId});
		}else{
			fNum=simpleDaoSupport.count("select count(p.uuid) from TeePerson p where p.deleteStatus<>1 and exists(select 1 from p.userRoleOther ou where ou.uuid=? ) ", new Object[]{roleId});
		}
		
		statistics=zNum+"/"+fNum;
		
		return statistics;
	}

	/**
	 * 删除
	 * @param ids
	 */
	@TeeLoggingAnt(template="删除角色信息 [{logModel.userRoleNames}]",type="004C")
	public List<TeeUserRole> deleteService(String ids) {
		List<TeeUserRole> roleList = new ArrayList<TeeUserRole>();
		StringBuffer userRoleNames = new StringBuffer();
		try {
			for (String id : ids.split(",")) {
				TeeUserRole role =  userRolelDao.get(Integer.parseInt(id));
				List list = role.getPersons();
				role.setPersons(null);
				for(int i=0;i<list.size();i++){
					TeePerson p = (TeePerson)list.get(i);
					//p.setUserRoleOther(null);//辅助部门
					p.setUserRole(null);
				}
				//roleList.add(role);
				userRolelDao.deleteByObj(role);
				if(!TeeUtility.isNullorEmpty(userRoleNames.toString())){
					userRoleNames.append( ","+ role.getRoleName() );
				}else{
					userRoleNames.append(role.getRoleName()) ;
				}
				
			}
		} catch (Exception e) {
			roleList.clear();
			e.printStackTrace();
			return roleList;
		}
		
		TeeRequestInfoContext.getRequestInfo().getLogModel().put("userRoleNames",  userRoleNames);//添加其他参数
		
		return roleList;
	}
	/**
	 * 根据对象删除
	 * @author syl
	 * @date 2013-11-17
	 * @param userRole
	 */
	public void deleteByObj(TeeUserRole userRole){
		userRolelDao.deleteByObj(userRole);
	}
	
	/**
	 * 获取角色，针对用户管理新建
	 * @return
	 */
	public List<TeeUserRole> selectUserPrivList(TeePerson person,String privOp){
		boolean isAdminPriv = TeePersonService.checkIsAdminPriv(person);
		boolean isSuperAdminPriv = TeePersonService.checkIsSuperAdmin(person,"");
		if(!privOp.equals("1")){//不需要控制权限
			isSuperAdminPriv = true;
		}
		TeeUserRole role = person.getUserRole();
		List<TeeUserRole> list = new ArrayList<TeeUserRole>();
		if(role == null ){
			return list;
		}
		list = userRolelDao.selectUserPrivListByPersonInfo(isAdminPriv ,isSuperAdminPriv,personService.getMinRoleNoByLoginPerson(person));
		return list;
	}
	
	/**
	 * 上移
	 * @author syl
	 * @date 2013-12-29
	 * @param person
	 * @param id
	 * @param sortNo 排序号
	 * @return
	 */
	public void toPrev(TeePerson person,String id , String sortNo){
		int sid = TeeStringUtil.getInteger(id, 0);
		if(sid > 0){
			TeeUserRole rolo = userRolelDao.get(sid);
			
			
			if(rolo != null){
				//TeeUserRole rolo = userRolelDao.getPrevOrNext(sid, sortNo, type)
			}
		}
	}
	
	/**
	 * 调整角色排序
	 * @author syl
	 * @date 2014-6-1
	 * @param request
	 * @return
	 */
	public TeeJson updateRoleSort(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sidIds = TeeStringUtil.getString(request.getParameter("sidIds") , "");
		String sorts = TeeStringUtil.getString(request.getParameter("sorts") , "");
		if(!TeeUtility.isNullorEmpty(sidIds) && !TeeUtility.isNullorEmpty(sorts)){
			String[] sidArray = sidIds.split(",");
			String[] sortArray = sorts.split(",");
			for (int i = 0; i < sidArray.length; i++) {
				int sid = TeeStringUtil.getInteger(sidArray[i], 0);
				int sort =   TeeStringUtil.getInteger(sortArray[i], 0);
				if(sid > 0){
					userRolelDao.updateRoleSort(sid, sort);
				}
			}
		}
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取所有角色
	 * @return
	 */
	public TeeJson getAllRole(){
		TeeJson json = new TeeJson();
		List<TeeUserRole> list = userRolelDao.selectUserPrivList();
		List<TeeUserRoleModel> listModel = new ArrayList<TeeUserRoleModel>();
		for (int i = 0; i < list.size(); i++) {
			TeeUserRoleModel model = new TeeUserRoleModel();
			BeanUtils.copyProperties(list.get(i), model);
			TeeUserRoleType type = list.get(i).getUserRoleType();
			int roleTypeId = 0;
			String typeName = "";
			if(type != null){
				roleTypeId = type.getSid();
				typeName = type.getTypeName();
			}
			model.setRoleTypeId(roleTypeId);
			model.setRoleTypeName(typeName);
			
			listModel.add(model);
		}
		json.setRtData(listModel);
		json.setRtState(true);
		return json;
	}
	public TeeUserRoleDao getUserRolelDao() {
		return userRolelDao;
	}

	public void setUserRolelDao(TeeUserRoleDao userRolelDao) {
		this.userRolelDao = userRolelDao;
	}

	/**
	 * 根据角色id串   获取id集合
	 * @param roleViewIds
	 * @return
	 */
	public List<TeeUserRole> getUserRoleByUuids(String roleUuids) {
		// TODO Auto-generated method stub
		return userRolelDao.getUserRoleByUuids(roleUuids);
	}

	
	
	/**
	 * 工作流转交   选人    按角色
	 * @param request
	 * @return
	 */
	public TeeJson selectUserPrivListWorkFlow(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		loginPerson = personService.selectByUuid(loginPerson.getUuid());
		
		int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);//步骤ID
	    int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);//frpSid
	
	    //根据步骤id获取当前步骤的有条件的人员ids
	    
	    TeeFlowProcess flowProcess = (TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class, prcsId);
	    TeeFlowRunPrcs flowRunPrcs = (TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class, frpSid);
	    
	    Map data = fixedFlowDataLoader.getFreeFlowFilterSelectPersonInfo(flowProcess, flowRunPrcs, loginPerson);
		
	    //主角色
	    List<TeeUserRole> list=simpleDaoSupport.executeQuery("select distinct(p.userRole) from TeePerson p where "+TeeDbUtility.IN("p.uuid", TeeStringUtil.getString(data.get("filterPersonIds"))),null );
		//輔助角色
	    List<TeeUserRole> otherList=simpleDaoSupport.executeQuery("select distinct(p.userRoleOther) from TeePerson p where "+TeeDbUtility.IN("p.uuid", TeeStringUtil.getString(data.get("filterPersonIds"))),null );
	    //合二为一
	    if(otherList!=null&&otherList.size()>0){
	    	for (TeeUserRole teeUserRole : otherList) {
				if(!list.contains(teeUserRole)){
					list.add(teeUserRole);
				}
			}
	    }
	    
		List<TeeUserRoleModel> listM = new ArrayList<TeeUserRoleModel>();
		for (int i = 0; i <list.size(); i++) {
			TeeUserRoleModel modul = new TeeUserRoleModel();
			BeanUtils.copyProperties(list.get(i), modul);
			listM.add(modul);
		}
		json.setRtMsg("数据获取成功！");
		json.setRtData(listM);
		json.setRtState(true);
		return json;
	}

	
	/**
	 * 获取主角色用户/辅助角色用户详情
	 * @return
	 */
	public TeeJson getStatisticsDetail(HttpServletRequest request) {
		
		TeeJson json=new TeeJson();
		//获取角色id
		int roleId=TeeStringUtil.getInteger(request.getParameter("roleId"),0);
		List<TeeDepartment> deptList=simpleDaoSupport.executeQuery(" select distinct(p.dept) from TeePerson p where p.userRole.uuid=? or exists(select 1 from p.userRoleOther ou where ou.uuid=? ) ",new Object[]{roleId,roleId});
		List<Map> data=new ArrayList<Map>();
		Map map=null;
		List<Map> zList=null;
		List<Map> fList=null;
		
		if(deptList!=null&&deptList.size()>0){
			String zUserNames="";
			String fUserNames="";
			for (TeeDepartment teeDepartment : deptList) {
				zUserNames = "";
				fUserNames = "";
				map=new HashMap();
				map.put("deptName",teeDepartment.getDeptFullName());
				map.put("statistics",getStatisticsByRoleId(roleId,teeDepartment.getUuid()));
			    zList=simpleDaoSupport.getMaps("select p.userName as userName from TeePerson p where p.dept.uuid=? and p.userRole.uuid=? ",new Object[]{teeDepartment.getUuid(),roleId});
			    if(zList!=null&&zList.size()>0){
			    	for (Map teePerson : zList) {
						zUserNames+=teePerson.get("userName")+",";
					}
			    }
			    if(zUserNames.endsWith(",")){
			    	zUserNames=zUserNames.substring(0, zUserNames.length()-1);
			    }
			    fList=simpleDaoSupport.getMaps("select p.userName as userName from TeePerson p where p.dept.uuid=? and exists(select 1 from p.userRoleOther ou where ou.uuid=? ) ",new Object[]{teeDepartment.getUuid(),roleId});
			    if(fList!=null&&fList.size()>0){
			    	for (Map teePerson : fList) {
						fUserNames+=teePerson.get("userName")+",";
					}
			    }
			    if(fUserNames.endsWith(",")){
			    	fUserNames=fUserNames.substring(0, fUserNames.length()-1);
			    }
			    
			    map.put("zUserNames",zUserNames);
			    map.put("fUserNames", fUserNames);
			    
			    data.add(map);
			}
		}
		
		if(data!=null&&data.size()>0){
			String tj=getStatisticsByRoleId(roleId, 0);
			Map hj=new HashMap();
			hj.put("deptName","<span style=\"font-weight:bold;\">合计</span>");
			hj.put("statistics","<span style=\"font-weight:bold;\">"+tj+"</span>");
			hj.put("zUserNames","<span style=\"font-weight:bold;\">"+tj.split("/")[0]+"</span>" );
			hj.put("fUserNames","<span style=\"font-weight:bold;\">"+tj.split("/")[1]+"</span>" );
			data.add(hj);
		}
		json.setRtState(true);
		json.setRtData(data);
		return json;
	}
	
	
	
}
