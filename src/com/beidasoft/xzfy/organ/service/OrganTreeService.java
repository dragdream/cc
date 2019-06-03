package com.beidasoft.xzfy.organ.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beidasoft.xzfy.organ.bean.OrganInfo;
import com.beidasoft.xzfy.organ.dao.OrganDao;
import com.beidasoft.xzfy.organ.model.request.OrganListRequest;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeeOrganization;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeeOrgDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class OrganTreeService extends TeeBaseService{

	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	@Qualifier("deptDao")
	private TeeDeptDao deptDao;
	
	@Autowired
	public OrganDao organDao;
	
	@Autowired
	private TeeOrgDao teeDao;
	
	/**
	 * 选择复议组织机构树
	 * @return
	 */
	public List<TeeZTreeModel> selectFyTree(HttpServletRequest request){
		
		//封装请求
		OrganListRequest re = new OrganListRequest();
		re.setPage(1);
		re.setRows(100000);
		//所有的组织机构
		List<OrganInfo> all = organDao.getOrganList(re,"");
		Map<Integer,OrganInfo> checkedDeptMap = new HashMap<Integer,OrganInfo>();
		if(all != null) {
			for(OrganInfo organ :all) {
				checkedDeptMap.put( Integer.parseInt(organ.getDeptId()), organ);
			}
		}
		
		//获取机构范围
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//根据登录人获取信息
		TeePerson person = personDao.selectPersonById( loginPerson.getUuid() );
		TeeDepartment personDept = person.getDept();
		String deptFullId = personDept.getDeptFullId();
		//拼接hql语句
		String hql = " from TeeDepartment t where t.deptFullId like ? order by t.deptFullId ";
		Object[] para = {deptFullId + "%"};
		
		List<TeeDepartment> deptList = deptDao.selectDept(hql, para);
		List<TeeZTreeModel> nodeList = new ArrayList<TeeZTreeModel>();
			
		for(TeeDepartment dept:deptList) {
			
			String pid = null;
			if (dept.getDeptParent() != null) {
				pid = String.valueOf(dept.getDeptParent().getUuid());
			}
			//判决是否选中
			boolean isChecked = false;
			if(checkedDeptMap.get(dept.getUuid())!=null) {
				isChecked = true;
			}

			String iconSkin = "dept";
			if(dept.getDeptType() == 2) {
				iconSkin = "unit";
			}
			//封装树对象
			TeeZTreeModel node = new TeeZTreeModel();
			node.setId(String.valueOf(dept.getUuid()));
			node.setpId(pid);
			node.setName(dept.getDeptName());
			node.setIconSkin(iconSkin);
			node.setNocheck(false);
			node.setChecked(isChecked);
			node.setOpen(true);
			//添加
			nodeList.add(node);
		}
		return nodeList;
	}
	
	
	/**
	 * 获取复议的组织机构树
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<TeeZTreeModel> getFyTree(HttpServletRequest request,String deptId) throws Exception{
		
		//返回树集合
		List<TeeZTreeModel> orgDeptList = new ArrayList<TeeZTreeModel>();
		//登录用户
		TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		//判断部门ID是否为空
		if(TeeUtility.isNullorEmpty(deptId)){
			// 获取单位(顶级)
			List<TeeOrganization> list = teeDao.traversalOrg();
			if (list.size() > 0) {
				TeeOrganization org = list.get(0);
				//部门树对象
				TeeZTreeModel ztree = new TeeZTreeModel(org.getSid() + "",
						org.getUnitName(), true, "0", true, "pIconHome", "");
				orgDeptList.add(ztree);
			}
		}
		else{
			Map<Integer,Object> deptListPriv= new HashMap<Integer,Object>();
			//获取当前登陆人  可见范围的部门 
			List<TeeDepartment> deptList = getDeptsByLoginUser(loginUser);
				
			//获取有可见权限的所有上级部门和自己的集合
			Map<String,Object> deptIdSetPriv= new HashMap<String,Object>();
				
			String[] ids = null;
			String deptFullId = null;
			for (TeeDepartment dept : deptList) {
				deptListPriv.put(dept.getUuid(), "");
				deptFullId=dept.getDeptFullId();
				deptFullId=deptFullId.substring(1,deptFullId.length());
				ids = deptFullId.split("/");
				//顶级的部门主键
				for (String s : ids) {
					if (!deptIdSetPriv.containsKey(s)) {//从map里取出来，不用内部for循环
						deptIdSetPriv.put(s, "");
					}
				}
			}
				
			String[] idArray = deptId.split(";");
			if (idArray.length >= 2){
				Object[] values = { Integer.parseInt(idArray[0]) };
				if (idArray[1].equals("dept")) {// 部门
					List<TeeDepartment> list = deptDao
							.selectDept("from TeeDepartment  where deptParent.uuid = ?  order by deptNo ",
									values);
					for (int i = 0; i < list.size(); i++) {
						TeeDepartment dept = list.get(i);
						if(!deptListPriv.containsKey(dept.getUuid()+"")
								&& !deptIdSetPriv.containsKey(dept.getUuid()+"")){
							continue;
						}
						// 如果存在下级获取用户
						boolean isParent = false;
						if (deptDao.checkExists(dept)) {
							isParent = true;
						}
						TeeZTreeModel ztree = null;
						if (dept.getDeptType() == 2) {//分支机构
							ztree = new TeeZTreeModel(dept.getUuid()
									+ ";dept", dept.getDeptName(),
									isParent, deptId, true, "pIconHome", "");
						} else {//部门/全局部门
							ztree = new TeeZTreeModel(dept.getUuid()
									+ ";dept", dept.getDeptName(),
									isParent, deptId, true, "deptNode", "");
						}
						orgDeptList.add(ztree);
					}// 1-禁止
				} else if (idArray[1].equals("person")) {// 人员不处理

				}
			} else {		
				//获取所有的一级部门
				List<TeeDepartment> list = deptDao.getFisrtDept();
				for (int i = 0; i < list.size(); i++) {
					TeeDepartment dept = list.get(i);
					if(!deptListPriv.containsKey(dept.getUuid()+"")
							&& !deptIdSetPriv.containsKey(dept.getUuid()+"")){
						continue;
					}
					boolean isParent = false;
//					long count = personDao.selectPersonCountByDeptIdandOtherDept(dept
//									.getUuid());
					if (deptDao.checkExists(dept)) {// 如果存在下级部门
						isParent = true;
					}
					TeeZTreeModel ztree = null;
					if (dept.getDeptType() == 2) {//分支机构
						ztree = new TeeZTreeModel(dept.getUuid() + ";dept",
								dept.getDeptName(), isParent, deptId, true,
								"pIconHome", "");
					} else {//部门/全局部门
						ztree = new TeeZTreeModel(dept.getUuid() + ";dept",
								dept.getDeptName(), isParent, deptId, true,
								"deptNode", "");
					}
					orgDeptList.add(ztree);
				}
			}
		} 
		return orgDeptList;
	}
	
	
	
	/**
	 * 获取当前登陆人  可见范围的部门 
	 * @param loginUser
	 * @return
	 */
	@SuppressWarnings(value={"unchecked"})
	@Transactional(readOnly=true)
	public List<TeeDepartment> getDeptsByLoginUser(TeePerson loginUser){
		
		StringBuffer str = new StringBuffer();
		//获取复议的组织机构
		OrganListRequest re = new OrganListRequest();
		re.setPage(1);
		re.setRows(100000);
		//所有的组织机构
		List<OrganInfo> all = organDao.getOrganList(re,"");
		
		//拼接in 语句
		str.append("(");
		if(all != null) {
			for(int i=0;i<all.size()-1;i++) {
				str.append("'");
				str.append(all.get(i).getDeptId());
				str.append("',");
			}
			str.append("'");
			str.append(all.get(all.size()-1).getDeptId());
			str.append("'");
		}
		str.append(")");
		
		
		loginUser = personDao.get(loginUser.getUuid());
		List<TeeDepartment> listDept = new ArrayList<TeeDepartment>();
		
		String hql = "";
		String nhql = "";
		//超级管理员,则获取所有部门
		if(TeePersonService.checkIsAdminPriv(loginUser)){
			hql = "select new TeeDepartment(uuid,deptName,deptFullId,deptParent) from TeeDepartment d "
					+ " where d.uuid in " + str.toString() 
					+ " order by d.deptNo asc";
			listDept = simpleDaoSupport.find(hql, null);
			
			nhql ="select new TeeDepartment(uuid,deptName,deptFullId) from TeeDepartment d "
					+ " where d.deptParent is null "
					+ " and d.uuid in " + str.toString() 
					+ " order by d.deptNo asc";
			listDept.addAll(simpleDaoSupport.find(nhql,null));
			return listDept;
		}
		
		String path = loginUser.getDept().getDeptFullId();
		path = path.substring(1).replace("/", ",");
		
		if(loginUser.getViewPriv()==1){//本机构
			//从下至上递归，查看是否存在上层机构
			String sp [] = path.split(",");
			boolean hasOrg = false;
			TeeDepartment org = null;
			for(int i=sp.length-1;i>=0;i--){
				TeeDepartment tmpD = deptDao.get(Integer.parseInt(sp[i]));
				if(tmpD.getDeptType()==2){//找到机构
					hasOrg = true;
					org = tmpD;
					break;
				}
			}
			
			if(hasOrg){//如果找到了上层机构，则仅查询出该机构及其以下的所有部门
				hql = "select new TeeDepartment(uuid,deptName,deptFullId,deptParent) from TeeDepartment d "
						+ " where (d.deptFullId like '"+org.getDeptFullId()+"/"+"%'  or d.deptFullId='"
						+ org.getDeptFullId()+"') "
						+ " and d.uuid in " + str.toString() 
						+ " order by d.deptNo asc";
				listDept = simpleDaoSupport.find(hql, null);
				
				nhql = "select new TeeDepartment(uuid,deptName,deptFullId) from TeeDepartment d "
						+ " where (d.deptFullId like '"+org.getDeptFullId()+"/"+"%'  or d.deptFullId='"
						+ org.getDeptFullId()+"') "
						+ " and d.deptParent is null "
						+ " and d.uuid in " + str.toString() 
						+ " order by d.deptNo asc";
				listDept.addAll(simpleDaoSupport.find(nhql, null));
				
			}else{//如果没有，则全部查出
				hql = "select new TeeDepartment(uuid,deptName,deptFullId,deptParent) from TeeDepartment d "
						+ " where d.uuid in " + str.toString() 
						+ " order by d.deptNo asc";
				listDept = simpleDaoSupport.find(hql, null);
				
				nhql = "select new TeeDepartment(uuid,deptName,deptFullId) from TeeDepartment d "
						+ " where d.deptParent is null "
						+ " and d.uuid in " + str.toString() 
						+ " order by d.deptNo asc ";
				listDept.addAll(simpleDaoSupport.find(nhql, null));
			}
		}else if(loginUser.getViewPriv()==2){//指定部门
			String viewDept = loginUser.getViewDept();
			if(!"".equals(viewDept)){
				hql = "select new TeeDepartment(uuid,deptName,deptFullId,deptParent) from TeeDepartment d "
						+ " where "+TeeDbUtility.IN("d.uuid", viewDept)+" "
						+ " and d.uuid in " + str.toString() 
						+ " order by d.deptNo asc";
				listDept = simpleDaoSupport.find(hql, null);
				
				nhql = "select new TeeDepartment(uuid,deptName,deptFullId) from TeeDepartment d "
						+ " where "+TeeDbUtility.IN("d.uuid", viewDept)+" "
						+ " and d.deptParent is null "
						+ " and d.uuid in " + str.toString() 
						+ " order by d.deptNo asc ";
				listDept.addAll(simpleDaoSupport.find(nhql, null));
			}
		}else if(loginUser.getViewPriv()==3){//本部门(支持辅助部门)
			listDept.add(loginUser.getDept());
			List<TeeDepartment> tmpList = loginUser.getDeptIdOther();
			for(TeeDepartment tmp:tmpList){
				if(tmp.getUuid()!=loginUser.getDept().getUuid()){
					listDept.add(tmp);
				}
			}
		}else if(loginUser.getViewPriv()==4){//本部门及以下部门(支持辅助部门)
			Set<TeeDepartment> depts = new LinkedHashSet<TeeDepartment>();
			//获取主部门及其子部门
			hql = "select new TeeDepartment(uuid,deptName,deptFullId,deptParent) from TeeDepartment d "
					+ " where (d.deptFullId like '"+loginUser.getDept().getDeptFullId()+"/"+"%' "
					+ " or d.deptFullId='"+loginUser.getDept().getDeptFullId()+"') "
					+ " and d.uuid in " + str.toString() 
					+ " order by d.deptNo asc";
			depts.addAll(simpleDaoSupport.find(hql, null));
			
			nhql = "select new TeeDepartment(uuid,deptName,deptFullId) from TeeDepartment d "
					+ " where (d.deptFullId like '"+loginUser.getDept().getDeptFullId()+"/"+"%' "
					+ " or d.deptFullId='"+loginUser.getDept().getDeptFullId()+"') "
					+ " and d.deptParent is null "
					+ " and d.uuid in " + str.toString() 
					+ " order by d.deptNo asc";
			depts.addAll(simpleDaoSupport.find(nhql, null));
			
			List<TeeDepartment> tmpList = loginUser.getDeptIdOther();
			for(TeeDepartment tmp:tmpList){
				if(tmp.getUuid()!=loginUser.getDept().getUuid()){
					
					hql = "select new TeeDepartment(uuid,deptName,deptFullId,deptParent) from TeeDepartment d "
							+ " where (d.deptFullId like '"+tmp.getDeptFullId()+"/"+"%' "
							+ " or d.deptFullId='"+tmp.getDeptFullId()+"') "
							+ " and d.uuid in " + str.toString() 
							+ " order by d.deptNo asc";
					depts.addAll(simpleDaoSupport.find(hql, null));
					
					nhql = "select new TeeDepartment(uuid,deptName,deptFullId) from TeeDepartment d "
							+ " where (d.deptFullId like '"+tmp.getDeptFullId()+"/"+"%' "
							+ " or d.deptFullId='"+tmp.getDeptFullId()+"') "
							+ " and d.deptParent is null  "
							+ " and d.uuid in " + str.toString() 
							+ " order by d.deptNo asc";
					depts.addAll(simpleDaoSupport.find(nhql, null));
				}
			}
			for(TeeDepartment d:depts){
				listDept.add(d);
			}
		}
		
		return listDept;
	}
}
