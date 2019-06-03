package com.tianee.oa.core.org.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeeOrganization;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeeOrgDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.model.TeeDepartmentModel;
import com.tianee.oa.core.priv.bean.TeeModulePriv;
import com.tianee.oa.core.priv.service.TeeModulePrivService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.sync.config.bean.TeeOutSystemConfig;
import com.tianee.oa.sync.log.bean.TeeOutSystemSyncLog;
import com.tianee.oa.sync.log.dao.TeeSyncLogDao;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.file.TeeCSVUtil;
import com.tianee.webframe.util.secure.TeePassEncryptMD5;
import com.tianee.webframe.util.servlet.HttpClientUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

import net.sf.json.JSONObject;
@Service
public class TeeDeptService extends TeeBaseService  {

	@Autowired
	@Qualifier("deptDao")
	private TeeDeptDao deptDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeOrgDao orgDao;
	
	@Autowired
	private TeeModulePrivService modulePrivServ;
	
	@Autowired
	private TeeSyncLogDao logDao;
	/**
	 * 新增
	 * @param TeeDepartment
	 * @throws UnsupportedEncodingException 
	 */
	@TeeLoggingAnt(template="增加部门信息[{$1.deptName}]",type="002A")
	public TeeJson addDeptService(TeeDepartmentModel model,HttpServletRequest request) throws UnsupportedEncodingException{
		TeeJson  json = new TeeJson();
		int uuid = model.getUuid() ;	
		int deptParent = model.getDeptParentId();
		TeeDepartment dept = new TeeDepartment();
		
		//判断部门名称是否重复
//		boolean isExistDeptName = deptDao.checkExistDeptName(model.getDeptName(), model.getUuid(), deptParent);
//		if(isExistDeptName){
//			json.setRtState(false);
//			json.setRtMsg("部门名称已存在！");
//			return json;
//		}
		if(deptParent!=0){ //处理上级部门和部门级别
			TeeDepartment deptParentObj = deptDao.selectDeptByUuid(deptParent);//获取上级部门对象
			if(deptParentObj != null  ){
				String deptParentLevel =  deptParentObj.getDeptParentLevel();//上级部门级别字符串
				if(TeeUtility.isNullorEmpty(deptParentLevel)){
					deptParentLevel = "";
				}
				dept.setDeptParentLevel(deptParentLevel + deptParentObj.getGuid() );//级别,以逗号分隔
			}
			dept.setDeptParent(deptParentObj);
			
		}else{
			dept.setDeptParentLevel("");
		}			
		BeanUtils.copyProperties(model, dept);
		dept.setGuid(TeePassEncryptMD5.getRandomGUID());
		
		//处理信息上报人员
		if(TeeStringUtil.getInteger(model.getInfoReportUserId(), 0)!=0){
			TeePerson infoReportUser=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger(model.getInfoReportUserId(), 0));
		    dept.setInfoReportUser(infoReportUser);
		}
		deptDao.addDept(dept);
		
		//同步外部系统 新增部门 operation为3
		syncSystem(dept,"3",request);
		
		//处理部门路径
		List<TeeDepartment> path = getDeptEntityPath(dept);
		StringBuffer fullName = new StringBuffer();
		StringBuffer fullId = new StringBuffer();
		
		for(int i=path.size()-1;i>=0;i--){
			fullName.append("/"+path.get(i).getDeptName());
			fullId.append("/"+path.get(i).getUuid());
		}
		
		dept.setDeptFullId(fullId.toString());
		dept.setDeptFullName(fullName.toString());
		model.setUuid(dept.getUuid());
		model.setDeptFullId(dept.getDeptFullId());
		model.setUniqueId(dept.getUniqueId());
		//更新单位
		TeeDepartment unit = null;
		if(dept.getDeptType() == 2) {
			unit = dept;
		}else if (dept.getDeptParent() != null){
			unit = dept.getDeptParent().getUnit();
		}
		dept.setUnit(unit);
		deptDao.update(dept);
		Map map = new HashMap();
		map.put("uuid", dept.getUuid());
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}

	/**
	 * 同步外部系统
	 * @param dept
	 * @throws UnsupportedEncodingException
	 */
	private void syncSystem(TeeDepartment dept,String operation,HttpServletRequest request)
			throws UnsupportedEncodingException {
			//新增部门
			List<TeeOutSystemConfig> configList = simpleDaoSupport.executeQuery("from TeeOutSystemConfig", null);
			if (configList != null && configList.size() > 0) {
				for (TeeOutSystemConfig config : configList) {
					//拼接json
					StringBuilder sb  = new StringBuilder();
					Map<String,String> map=new HashMap<String,String>();
					sb.append("{\"deptId\":"+dept.getUuid()+"");
					sb.append(",\"deptName\":\""+dept.getDeptName()+"\"");
					if(dept.getDeptParent()!=null){
						sb.append(",\"deptParentId\":"+dept.getDeptParent().getUuid()+"");
					}else{
						sb.append(",\"deptParentId\":\"\"");
					}

					sb.append(",\"operation\":\""+operation+"\"}");
					String param = sb.toString();
					map.put("json", param);
					String respJson = HttpClientUtil.requestPost(map, config.getSystemUrl());
					//String respJson = HttpClientUtil.requestGet(config.getSystemUrl()+"?json="+URLEncoder.encode(param,"UTF-8"));
					if (!TeeUtility.isNullorEmpty(respJson)) {
						boolean status = (boolean) JSONObject.fromObject(respJson).get("status");
						if (!status) {
							addLog(operation, request, config, param,dept);
						}
					}
			}
		}
	}

	/**
	 * 同步失败 记录日志
	 * @param operation
	 * @param request
	 * @param config
	 * @param parm
	 */
	private void addLog(String operation, HttpServletRequest request,
			TeeOutSystemConfig config, String parm,TeeDepartment dept) {
		TeeOutSystemSyncLog log = new TeeOutSystemSyncLog();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		log.setRequestJson(parm);
		if (dept != null) {
			log.setUuid(dept.getUuid()+"");
		}
		//状态设置为未同步
		log.setSyncFlag("0");
		log.setOperation(operation);
		log.setConfigId(config.getSid());
		log.setSubmitUserId(loginUser.getUuid());
		log.setSubmitUserName(loginUser.getUserName());
		Calendar crTime = Calendar.getInstance();
		crTime.setTime(new Date());
		log.setCrTime(crTime);
		logDao.addLog(log);
	}
	
	//获取部门路径
	public List<TeeDepartment> getDeptEntityPath(TeeDepartment dept){
		List<TeeDepartment> list = new ArrayList();
		TeeDepartment parent = dept.getDeptParent();
		list.add(dept);
		while(parent!=null){
			list.add(parent);
			parent = parent.getDeptParent();
		}
		return list;
	}
	
	/**
	 * 更新
	 * @param TeeDepartment
	 * @throws UnsupportedEncodingException 
	 */
	@TeeLoggingAnt(template="修改部门信息[{$1.deptName}]",type="002B")
	public TeeJson updateDeptService(TeeDepartmentModel model,HttpServletRequest request) throws UnsupportedEncodingException{
		int uuid = model.getUuid() ;
		TeeJson json = new TeeJson();
		int deptParent = model.getDeptParentId();
		TeeDepartment dept = new TeeDepartment();
		//判断部门名称是否重复
//		boolean isExistDeptName = deptDao.checkExistDeptName(model.getDeptName(), model.getUuid(), deptParent);
//		if(isExistDeptName){
//			json.setRtState(false);
//			json.setRtMsg("部门名称已存在！");
//			return json;
//		}
		if(deptParent!=0){ //处理上级部门和部门级别
			TeeDepartment deptParentObj = deptDao.selectDeptByUuid(deptParent);//获取上级部门对象
			if(deptParentObj != null  ){
				String deptParentLevel =  deptParentObj.getDeptParentLevel();//上级部门级别字符串
				if(TeeUtility.isNullorEmpty(deptParentLevel)){
					deptParentLevel = "";
				}
				dept.setDeptParentLevel(deptParentLevel + deptParentObj.getGuid() );//级别,以逗号分隔
			}
			dept.setDeptParent(deptParentObj);
		}else{
			dept.setDeptParentLevel("");
		}			
		BeanUtils.copyProperties(model, dept);
		
		//deptDao.update(dept);
		TeeDepartment deptOld = deptDao.selectDeptByUuid(uuid);//获取对象
		String  getDeptParentLevel = dept.getDeptParentLevel() ==null ?  "" :  dept.getDeptParentLevel();
		String deptParentLevelOld = deptOld.getDeptParentLevel();
		String oldGuid = deptOld.getGuid();
		BeanUtils.copyProperties(dept, deptOld);
		deptOld.setGuid(oldGuid);
		
		//处理信息上报人员
		if(TeeStringUtil.getInteger(model.getInfoReportUserId(), 0)!=0){
			TeePerson infoReportUser=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger(model.getInfoReportUserId(), 0));
			deptOld.setInfoReportUser(infoReportUser);
		}
		
		deptDao.updateDept(deptOld);
		String type = "";
		if(TeeUtility.isNullorEmpty(deptParentLevelOld)){//如果是第一级,就去当前的字符串
			//获取所有
			deptParentLevelOld = oldGuid;
			type = "1";
		}
		
		//更新下级所有部门级别
		deptDao.updateDept(deptParentLevelOld,getDeptParentLevel,uuid ,type);
		
		//处理部门路径
		//获取下级所有子部门
		List<TeeDepartment> childs = getAllChildDeptByLevl(deptOld.getUuid()+"");
		StringBuffer fullName = new StringBuffer();
		StringBuffer fullId = new StringBuffer();
		for(TeeDepartment child:childs){
			List<TeeDepartment> path = getDeptEntityPath(child);
			for(int i=path.size()-1;i>=0;i--){
				fullName.append("/"+path.get(i).getDeptName());
				fullId.append("/"+path.get(i).getUuid());
			}
			child.setDeptFullId(fullId.toString());
			child.setDeptFullName(fullName.toString());
			
			fullId.delete(0, fullId.length());
			fullName.delete(0, fullName.length());
			
			deptDao.update(child);
		}
		
		//更新当前部门
		fullId.delete(0, fullId.length());
		fullName.delete(0, fullName.length());
		List<TeeDepartment> path = getDeptEntityPath(deptOld);
		for(int i=path.size()-1;i>=0;i--){
			fullName.append("/"+path.get(i).getDeptName());
			fullId.append("/"+path.get(i).getUuid());
		}
		deptOld.setDeptFullId(fullId.toString());
		deptOld.setDeptFullName(fullName.toString());
		
		//更新单位
		TeeDepartment unit = null;
		if(deptOld.getDeptType() == 2) {
			unit = deptOld;
		}else if (deptOld.getDeptParent() != null){
			unit = deptOld.getDeptParent().getUnit();
		}
		deptOld.setUnit(unit);
		deptDao.update(deptOld);
		//更新子部门单位
	    deptDao.setChildDeptUnit(deptOld);		
		
		//同步外部系统
		syncSystem(dept, "4", request);
		
		Map map = new HashMap();
		map.put("uuid", deptOld.getUuid());
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 查询 byUuid
	 * @param TeeDepartment
	 */
	public TeeDepartment  selectDeptByUuid(String uuid) {
		if (!TeeUtility.isNullorEmpty(uuid)) {
			TeeDepartment dept = (TeeDepartment) deptDao.selectDeptById(Integer.parseInt(uuid));
			return  dept;
		}
		return null;
	}
	/**
	 * 查询 byUuid
	 * @param TeeDepartment
	 */
	public String  getDeptByUuid(HttpServletRequest request) {
		String uuid = request.getParameter("uuid");
		String isSelectAllColumn = request.getParameter("isSelectAllColumn");
		String data = "";
		if (!TeeUtility.isNullorEmpty(uuid)) {
			TeeDepartment dept = (TeeDepartment) deptDao.selectDeptById(Integer.parseInt(uuid));
			TeeDepartmentModel model = new TeeDepartmentModel();
			BeanUtils.copyProperties(dept, model);
			String pid = "";
			if(dept.getDeptParent()!=null){
				pid = dept.getDeptParent().getUuid() + "";
				model.setpId(pid);
				model.setpName(dept.getDeptParent().getDeptName());
			}
			
			if(  !TeeUtility.isNullorEmpty(isSelectAllColumn) && isSelectAllColumn.equals("1")){//需要获取所有字段，比如上级主管、分管领导等
				if(!TeeUtility.isNullorEmpty(dept.getManager())){//部门主管
					String[] str = personDao.getPersonNameAndUuidByUuids(dept.getManager());
					model.setManager(str[0]);
					model.setManagerName(str[1]);
				}
				
				if(!TeeUtility.isNullorEmpty(dept.getManager2())){//部门分管领导
					String[] str = personDao.getPersonNameAndUuidByUuids(dept.getManager2());
					model.setManager2(str[0]);
					model.setManager2Name(str[1]);
				}
				
				if(!TeeUtility.isNullorEmpty(dept.getLeader1())){//上级主管领导
					String[] str = personDao.getPersonNameAndUuidByUuids(dept.getLeader1());
					model.setLeader1(str[0]);
					model.setLeader1Name(str[1]);
				}
				
				if(!TeeUtility.isNullorEmpty(dept.getLeader2())){//上级分管领导
					String[] str = personDao.getPersonNameAndUuidByUuids(dept.getLeader2());
					model.setLeader2(str[0]);
					model.setLeader2Name(str[1]);
				}
				
				//处理信息上报员
				if(dept.getInfoReportUser()!=null){
					model.setInfoReportUserId(dept.getInfoReportUser().getUuid());
					model.setInfoReportUserName(dept.getInfoReportUser().getUserName());
				}
				
			}
			TeeJsonUtil jsonUtil = new TeeJsonUtil();
			data = jsonUtil.obj2Json(model).toString();
		}

		return data;
	}
	
	
	/**
	 * 删除byUuid
	 * @param TeeDepartment
	 */
	public void delDeptByUuid(TeeDepartment dept) {
		deptDao.delSysDept(dept);	
	}
	
	/**
	 * 查询 条件查询 
	 * @param TeeDepartment
	 */
	public List<TeeDepartment> selectDept(String hql, Object[] values) {
		List<TeeDepartment> list = deptDao.selectDept(hql, values);
		return list;
	}
	
	/**
	    * 判断同级是否存在相同编号的组织
	    * @param status  true-修改 ；false-新增
	    * @param deptCode 新部门编号  
	    *  @param oldDeptcode:老部门编号
	    * @return
	    */
		public boolean isExist(boolean status , TeeDepartment dept) {
			
			String hql = " from TeeDepartment t where t.deptNo = ? and t.deptParent = ? ";
			Object[] values = {dept.getDeptNo(),dept};
			List<TeeDepartment> list = new ArrayList<TeeDepartment>();
			if(status){
				hql = " from TeeDepartment t where t.menuId = ? and t.uuid != ? and t.deptParent = ? ";
			   Object[] values2 = {dept.getDeptNo() ,dept.getUuid(),dept};
			   list = deptDao.selectDept(hql, values2);
			}else{
				 list = deptDao.selectDept(hql, values);
			}
		
			if(list.size() > 0){//存在
				return false;
			}
			return true;
		}
	

		/**
		 * 检查是否有下级部门
		 * @param TeeDepartment
		 */
		
		public boolean checkExistsChild(TeeDepartment dept) {
			return deptDao.checkExists(dept);
		}
		
		/**
		 * 获取下级节点部门  递归
		 * @param sql
		 * @param values
		 * @return
		 * @throws Exception 
		 */
		public List<TeeDepartment> getAllChildDept(String uuid) throws Exception{
			List<TeeDepartment> list = new ArrayList<TeeDepartment>();
			return deptDao.getAllChildDept(Integer.parseInt(uuid), list);
		}
		
		/**
		 * 获取下级节点部门  采用编号规则
		 * @param sql
		 * @param values
		 * @return
		 * @throws Exception 
		 */
		public List<TeeDepartment> getAllChildDeptByLevl(String uuid){
			TeeDepartment localDept = deptDao.selectDeptByUuid(Integer.parseInt(uuid));
			if(!TeeUtility.isNullorEmpty(localDept.getDeptParentLevel())){
				uuid = localDept.getDeptParentLevel() +localDept.getGuid() ;
			}else{
				uuid = localDept.getGuid();
			}
			 return deptDao.getAllChildDeptByLevl(uuid);
		}
		
		
		@Transactional(readOnly=true)
		public List<TeeDepartment> getDeptsByLoginUser(TeePerson loginUser){
			loginUser = personDao.get(loginUser.getUuid());
			List<TeeDepartment> listDept = new ArrayList();
			
			if(TeePersonService.checkIsAdminPriv(loginUser)){//超级管理员,则获取所有部门
				listDept = simpleDaoSupport.find("select new TeeDepartment(uuid,deptName,deptFullId,deptParent) from TeeDepartment d order by d.deptNo asc", null);
				listDept.addAll(simpleDaoSupport.find("select new TeeDepartment(uuid,deptName,deptFullId) from TeeDepartment d where d.deptParent is null order by d.deptNo asc",null));
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
					listDept = simpleDaoSupport.find("select new TeeDepartment(uuid,deptName,deptFullId,deptParent) from TeeDepartment d where d.deptFullId like '"+org.getDeptFullId()+"/"+"%'  or d.deptFullId='"+org.getDeptFullId()+"'  order by d.deptNo asc", null);
					listDept.addAll(simpleDaoSupport.find("select new TeeDepartment(uuid,deptName,deptFullId) from TeeDepartment d where (d.deptFullId like '"+org.getDeptFullId()+"/"+"%'  or d.deptFullId='"+org.getDeptFullId()+"') and d.deptParent is null  order by d.deptNo asc", null));
				}else{//如果没有，则全部查出
					listDept = simpleDaoSupport.find("select new TeeDepartment(uuid,deptName,deptFullId,deptParent) from TeeDepartment d order by d.deptNo asc", null);
					listDept.addAll(simpleDaoSupport.find("select new TeeDepartment(uuid,deptName,deptFullId) from TeeDepartment d where d.deptParent is null order by d.deptNo asc ", null));
				}
			}else if(loginUser.getViewPriv()==2){//指定部门
				String viewDept = loginUser.getViewDept();
				if(!"".equals(viewDept)){
					listDept = simpleDaoSupport.find("select new TeeDepartment(uuid,deptName,deptFullId,deptParent) from TeeDepartment d where "+TeeDbUtility.IN("d.uuid", viewDept)+" order by d.deptNo asc", null);
					listDept.addAll(simpleDaoSupport.find("select new TeeDepartment(uuid,deptName,deptFullId) from TeeDepartment d where "+TeeDbUtility.IN("d.uuid", viewDept)+" and d.deptParent is null order by d.deptNo asc ", null));
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
				Set<TeeDepartment> depts = new LinkedHashSet();
				//获取主部门及其子部门
				depts.addAll(simpleDaoSupport.find("select new TeeDepartment(uuid,deptName,deptFullId,deptParent) from TeeDepartment d where d.deptFullId like '"+loginUser.getDept().getDeptFullId()+"/"+"%' or d.deptFullId='"+loginUser.getDept().getDeptFullId()+"' order by d.deptNo asc", null));
				depts.addAll(simpleDaoSupport.find("select new TeeDepartment(uuid,deptName,deptFullId) from TeeDepartment d where (d.deptFullId like '"+loginUser.getDept().getDeptFullId()+"/"+"%' or d.deptFullId='"+loginUser.getDept().getDeptFullId()+"') and d.deptParent is null order by d.deptNo asc", null));
				List<TeeDepartment> tmpList = loginUser.getDeptIdOther();
				for(TeeDepartment tmp:tmpList){
					if(tmp.getUuid()!=loginUser.getDept().getUuid()){
						depts.addAll(simpleDaoSupport.find("select new TeeDepartment(uuid,deptName,deptFullId,deptParent) from TeeDepartment d where d.deptFullId like '"+tmp.getDeptFullId()+"/"+"%' or d.deptFullId='"+tmp.getDeptFullId()+"'  order by d.deptNo asc", null));
						depts.addAll(simpleDaoSupport.find("select new TeeDepartment(uuid,deptName,deptFullId) from TeeDepartment d where (d.deptFullId like '"+tmp.getDeptFullId()+"/"+"%' or d.deptFullId='"+tmp.getDeptFullId()+"') and d.deptParent is null  order by d.deptNo asc", null));
					}
				}
				for(TeeDepartment d:depts){
					listDept.add(d);
				}
			}
			
			
			return listDept;
		}
		
	    /**
	     * 获取所有部门树
	     * @return
	     */
		public TeeJson getDeptTreeAll(){
			//从上下文中获取登录用户
			TeePerson loginUser = (TeePerson)TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
			String moduleId = (String)TeeRequestInfoContext.getRequestInfo().getRequest().get("moduleId");
			
			TeeModulePriv modulePriv = modulePrivServ.selectPrivEntityByUserId(loginUser.getUuid() + "", moduleId);;
			
			List<TeeDepartment> listDept = new ArrayList();
			if(modulePriv!=null){
				if("0".equals(modulePriv.getDeptPriv())){//本部门
					listDept.add(loginUser.getDept());
				}else if("1".equals(modulePriv.getDeptPriv())){//全体
					//listDept = deptDao.getAllDept();
					listDept=simpleDaoSupport.find("select new TeeDepartment(uuid,deptName,deptFullId,deptParent) from TeeDepartment d order by d.deptNo asc ", null);
					listDept.addAll(simpleDaoSupport.find("select new TeeDepartment(uuid,deptName,deptFullId) from TeeDepartment d where d.deptParent is null order by d.deptNo asc ", null));
				}else if("2".equals(modulePriv.getDeptPriv())){//指定部门
					listDept.addAll(modulePriv.getDeptIds());
				}else if("3".equals(modulePriv.getDeptPriv())){//指定人员
					Set<TeePerson> persons = modulePriv.getUserIds();
					Set<TeeDepartment> depts = new HashSet();
					for(TeePerson p:persons){
						depts.addAll(p.getDeptIdOther());
						depts.add(p.getDept());
					}
					listDept.addAll(depts);
				}
				
			}else{
				listDept = getDeptsByLoginUser(loginUser); //deptDao.getAllDept();//获取所有的部门
			}
			
		    List<TeeZTreeModel> deptTree = new  ArrayList<TeeZTreeModel>();
		    TeeJson json = new TeeJson();
		    String pid = "0";
		    TeeZTreeModel ztree=null;
		    
		    //取出机构
//		    TeeOrganization org = orgDao.selectOrg("from TeeOrganization", null).get(0);
//		    ztree = new TeeZTreeModel();
//	    	ztree.setId(String.valueOf(org.getSid()+";org"));
//	    	ztree.setName(org.getUnitName());
//	    	ztree.setOpen(false);//设置不自动展开
//	    	ztree.setpId("0");
//	    	ztree.setIconSkin("pIconHome");
//	    	deptTree.add(ztree);
		   
		    for(TeeDepartment dept : listDept){
		    	pid = "0";
		    	if(dept.getDeptParent()!=null){
		    		pid = String.valueOf(dept.getDeptParent().getUuid());
		    	}
		    	ztree = new TeeZTreeModel();
		    	ztree.setId(String.valueOf(dept.getUuid()));
		    	ztree.setName(dept.getDeptName());
		    	ztree.setOpen(false);//设置不自动展开
		    	ztree.setpId(pid);
		    	
		    	//ztree.setParent(false);
		    	
		    	
		    	
		    	if(dept.getDeptType()==2){//机构
		    		ztree.setIconSkin("pIconHome");
		    	}else{
		    		ztree.setIconSkin("deptNode");
		    	}
		    	
			    deptTree.add(ztree);
		    }
		    json.setRtData(deptTree);
		    json.setRtState(true);
		    return json;
		}
		
		/**
		 * 一次性加载
		 * 获取部门数
		 * @author syl
		 * @date 2013-11-21
		 * @param request
		 * @return
		 */
		public TeeJson getOrgDeptTree(HttpServletRequest request){
			String deptId = request.getParameter("id") == null ? "" : request.getParameter("id");
			TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);

			TeeJson json = new TeeJson();
			List<TeeZTreeModel> orgDeptList = new ArrayList<TeeZTreeModel>();
			try {
				if (!TeeUtility.isNullorEmpty(deptId)){
					Map  deptListPriv= new HashMap();
					List<TeeDepartment> deptList = getDeptsByLoginUser(loginUser);//获取当前登陆人  可见范围的部门 
					
					//获取有可见权限的所有上级部门和自己的集合
					Map  deptIdSetPriv= new HashMap();
					
					String[] ids = null;
					String deptFullId = null;
					TeeDepartment d = null;
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
//					depts.addAll(simpleDaoSupport.find("select new TeeDepartment(uuid,deptName,deptFullId) from TeeDepartment d  where  "+TeeDbUtility.IN("d.uuid", newIds.toString()), null));
					
					String[] idArray = deptId.split(";");
					if (idArray.length >= 2){
						Object[] values = { Integer.parseInt(idArray[0]) };
						if (idArray[1].equals("dept")) {// 部门
							List<TeeDepartment> list = deptDao
									.selectDept(
											"from TeeDepartment  where deptParent.uuid = ?  order by deptNo ",
											values);
							for (int i = 0; i < list.size(); i++) {
								TeeDepartment dept = list.get(i);
								if(!deptListPriv.containsKey(dept.getUuid()+"")
										&& !deptIdSetPriv.containsKey(dept.getUuid()+"")){
									continue;
								}
								boolean isParent = false;
								if (deptDao.checkExists(dept)) {// 如果存在下级获取用户
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

							}
																		// 1-禁止
						} else if (idArray[1].equals("person")) {// 人员不处理

						}

					} else {		
						List<TeeDepartment> list = deptDao.getFisrtDept();//获取所有的一级部门
						for (int i = 0; i < list.size(); i++) {
							TeeDepartment dept = list.get(i);
							if(!deptListPriv.containsKey(dept.getUuid()+"")
									&& !deptIdSetPriv.containsKey(dept.getUuid()+"")){
								continue;
							}
							boolean isParent = false;
							long count = personDao
									.selectPersonCountByDeptIdandOtherDept(dept
											.getUuid());
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

				} else {// 获取单位
					List<TeeOrganization> list = orgDao.traversalOrg();
					if (list.size() > 0) {
						TeeOrganization org = list.get(0);
						TeeZTreeModel ztree = new TeeZTreeModel(org.getSid() + "",
								org.getUnitName(), true, "0", true, "pIconHome", "");
						orgDeptList.add(ztree);
					}
				}
				json.setRtMsg("获取成功");
				json.setRtData(orgDeptList);
				json.setRtState(true);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				json.setRtMsg(e.getMessage());
				json.setRtState(false);
			}
			
//			TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
//			TeeJson json = new TeeJson();
//			List<TeeZTreeModel> orgDeptList = new ArrayList<TeeZTreeModel>(); 
//			List<TeeOrganization> list = orgDao.traversalOrg();
//			if (list.size() > 0) {
//				TeeOrganization org = list.get(0);
//				TeeZTreeModel ztree = new TeeZTreeModel(org.getSid() + "", org.getUnitName(), true, "0", true, "pIconHome" , "");
//				orgDeptList.add(ztree);
//				
//				List<TeeDepartment> deptList = getDeptsByLoginUser(loginUser);//获取当前登陆人有可见权限的部门
//				//获取有可见权限的所有上级部门和自己的集合
//				List<String>  deptIdSetPriv=new  ArrayList<String>();
//				List<TeeDepartment>  depts=new  ArrayList<TeeDepartment>();
//				for (TeeDepartment dept : deptList) {
//					String deptFullId=dept.getDeptFullId();
//					deptFullId=deptFullId.substring(1,deptFullId.length());
//					String[] ids = deptFullId.split("/");
//					
//						//顶级的部门主键
//						for (String s : ids) {
//							if (!deptIdSetPriv.contains(s)) {
//								deptIdSetPriv.add(s);
//								TeeDepartment d=(TeeDepartment) simpleDaoSupport.get(TeeDepartment.class,TeeStringUtil.getInteger(s, 0));
//								depts.add(d);
//							}
//						}
//					}
//				
//				//排序字段
//				Collections.sort(depts);
//				
//				for (int i = 0; i < depts.size(); i++) {
//					TeeDepartment dept = depts.get(i);
//					TeeDepartment parentDept = dept.getDeptParent();
//					String parentId = org.getSid() + "";
//					if(parentDept !=  null){
//						parentId = parentDept.getUuid() + ";dept";
//					}
//					TeeZTreeModel deptZtree = null;
//					if(dept.getDeptType()==2){//分支机构
//						deptZtree = new TeeZTreeModel(dept.getUuid()+ ";dept",dept.getDeptName(), false, parentId, true, "pIconHome" , "");
//					}else{
//						deptZtree = new TeeZTreeModel(dept.getUuid()+ ";dept",dept.getDeptName(), false, parentId, true, "deptNode" , "");
//					}
//					orgDeptList.add(deptZtree);
//				}
//			}
//			json.setRtData(orgDeptList);
			return json;
		}
		
		
		/**
	     * 获取所有部门树
	     * deptId : 部门UUID
	     * @return
	     */
		public TeeJson getSelectDeptTree(String deptId){
			TeeJson json = new TeeJson();
			List<TeeZTreeModel> orgDeptList = new ArrayList<TeeZTreeModel>(); 
			if(TeeUtility.isNullorEmpty(deptId)){
				List<TeeDepartment> list = deptDao.getFisrtDept();//第一级
				for (int i = 0; i < list.size(); i++) {
	   	   			TeeDepartment dept = list.get(i);
	   	   			TeeZTreeModel ztree = new TeeZTreeModel();
					   ztree.setId(dept.getUuid() + "");
					   ztree.setName(dept.getDeptName());
					   ztree.setParent(false);
					   if(checkExistsChild(dept)){
						   ztree.setParent(true);
					   }
					   ztree.setpId(deptId);
					   ztree.setIconSkin("deptNode");
					   orgDeptList.add(ztree);
			   }
				json.setRtData(list);
			}else{
				List<TeeDepartment> list = deptDao.getChildDept(Integer.parseInt(deptId));
				for (int i = 0; i < list.size(); i++) {
	   	   			TeeDepartment dept = list.get(i);
	   	   			TeeZTreeModel ztree = new TeeZTreeModel();
					   ztree.setId(dept.getUuid() + "");
					   ztree.setName(dept.getDeptName());
					   ztree.setParent(false);
					   if(checkExistsChild(dept)){
						   ztree.setParent(true);
					   }
					   ztree.setpId(deptId);
					   ztree.setIconSkin("deptNode");
					   orgDeptList.add(ztree);
			   }
			}
		    json.setRtData(orgDeptList);
		    json.setRtState(true);
		    return json;
		}
		  /**
	     * 获取本部门且下级部门select 
	     * deptId : 部门UUID
	     * @return
	     */
		public TeeJson getSelectDept(String deptId){
			TeeJson json = new TeeJson();
			List<TeeDepartment>  list = new ArrayList<TeeDepartment>();
			if(TeeUtility.isNullorEmpty(deptId)){
				list = deptDao.getAllDept();
			}else{
				TeeDepartment dept = deptDao.selectDeptById(Integer.parseInt(deptId));
				String levelUuid = dept.getGuid();
				if(!TeeUtility.isNullorEmpty(dept.getDeptParentLevel())){
					levelUuid = dept.getDeptParentLevel();
				}
				list = deptDao.getSelectDept(Integer.parseInt(deptId),levelUuid);
			}
			List<Map<String,String>> l = new ArrayList<Map<String,String>>();
			for (int i = 0; i <list.size(); i++) {
				Map<String,String> map = new HashMap<String,String>();
				String deptName = list.get(i).getDeptName();
				int uuid = list.get(i).getUuid() ;
				TeeDepartment deptParent =  list.get(i).getDeptParent();
				int deptParentId= 0;
				if(deptParent != null){
					deptParentId =  deptParent.getUuid();
				}
				String deptParentLevel = list.get(i).getDeptParentLevel();
				map.put("uuid", uuid + "");
				map.put("deptName", deptName);
				String[] deptStr = getSelectDeptStr( deptName, deptId , deptParentLevel);
				map.put("deptNameStr", deptStr[0]);
				map.put("deptLevel", deptStr[1]);
				map.put("deptParentId", deptParentId + "");
				l.add(map);
			}
		    json.setRtData(l);
		    json.setRtState(true);
		    return json;
		}
	    public String[] getSelectDeptStr(String deptName,String deptId ,String deptParentLevel){
	    	int deptIdLength = 32;
	    	int deptParentLevelLength = 0;
	    	
	    	/*if(!TeeUtility.isNullorEmpty(deptId)){
	    		deptIdLength = deptId.length();
	    	}*/
	    	if(!TeeUtility.isNullorEmpty(deptParentLevel)){
	        	deptParentLevelLength = 32  +  deptParentLevel.length();
	    	}
	
	    	int level = deptParentLevelLength/deptIdLength;
	    	String str = "|-";
	    	for (int i = 1 ; i < level; i++) {
	    		str = "&nbsp;&nbsp;&nbsp;&nbsp;" + str;
			}
	    	String[] deptStr = new String[2];
	    	deptStr[0] =  str  + deptName;
	    	deptStr[1] = level + "";	
	    	return deptStr ;
	    }

	    
	    
	    
		/**
		 *获取所有部门，并转换为导出数据格式
		 * @author syl
		 * @date 2013-11-14
		 * @return
		 */
		public ArrayList<TeeDataRecord> exportAllDeptInfo(){
			ArrayList<TeeDataRecord> list = new ArrayList<TeeDataRecord>();
			List<TeeDepartment> deptList = deptDao.getAllDept();
		    for (int i = 0; i < deptList.size(); i++) {
		    	TeeDepartment dept = deptList.get(i);
		        TeeDataRecord dbrec = new TeeDataRecord();
		        dbrec.addField("部门名称", dept.getDeptName());
		        dbrec.addField("部门排序号", dept.getDeptNo());
		        StringBuffer parentDeptName = new StringBuffer();
		        StringBuffer parentDeptFullName = new StringBuffer();
		        if(dept.getDeptParent() != null){
		        	parentDeptName.append(dept.getDeptParent().getDeptName());
		        	parentDeptFullName.append(dept.getDeptParent().getDeptFullName());
		        }
		        dbrec.addField("上级部门", parentDeptFullName);
		       // dbrec.addField("上级部门(全路径)", parentDeptFullName);
		        dbrec.addField("部门电话", dept.getTelNo());
		        dbrec.addField("部门传真", dept.getFaxNo());
		        dbrec.addField("部门职能", dept.getDeptFunc() );
		        list.add(dbrec);
		      }
			return list;
		}
	    
		/**
		 * 导入部门
		 * @param request
		 * @return
		 * @throws Exception
		 */
		public TeeJson importDept(HttpServletRequest request) throws Exception{
			TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			TeeJson json = new TeeJson();
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			
			StringBuffer sb = new StringBuffer("[");
			String infoStr= "";
			int importSuccessCount = 0;
			try {
				MultipartFile  file = multipartRequest.getFile("importDeptFile");
				if(!file.isEmpty() ){
					//获取真实文件名称
					String realName = file.getOriginalFilename();
					ArrayList<TeeDataRecord> dbL = TeeCSVUtil.CVSReader(file.getInputStream(), "GBK");
					
					List<TeeDepartment> addDeptList = new ArrayList<TeeDepartment>();
					for (int i = 0; i < dbL.size(); i++) {
						TeeDataRecord dr = dbL.get(i);
						String deptName = TeeStringUtil.getString(dr.getValueByName("部门名称"));
						int deptNo = TeeStringUtil.getInteger((String)dr.getValueByName("部门排序号"), 1);
						
						String parentDeptName = TeeStringUtil.getString(dr.getValueByName("上级部门"));
						
						String color = "red";
						if(TeeUtility.isNullorEmpty(deptName)){//部门为空则不建立
						  infoStr = "导入失败,部门名称不能失败！";
						  sb.append("{");
				          sb.append("deptName:\"" + deptName + "\"");
				          sb.append(",deptNo:\"" + deptNo + "\"");
				          sb.append(",deptParent:\"" + parentDeptName + "\"");
				          sb.append(",info:\"" + infoStr+ "\"");
				          sb.append(",color:\"" + color + "\"");
				          sb.append("},");
				          continue;
				        }
						
						Map map =  deptDao.checkExistedDept(deptName, parentDeptName);
						TeeDepartment parentDept = (TeeDepartment)map.get("parentDept");//上级部门
						boolean exist = (Boolean)map.get("exist");//是否存在

						if(exist){
							 infoStr = "导入失败,部门 " + deptName + " 已经存在！";
							 sb.append("{");
					         sb.append("deptName:\"" + deptName + "\"");
					         sb.append(",deptNo:\"" + deptNo + "\"");
					         sb.append(",deptParent:\"" + parentDeptName + "\"");
					         sb.append(",info:\"" + infoStr+ "\"");
					         sb.append(",color:\"" + color + "\"");
					         sb.append("},");
					         continue;
						}else{
							
							if(!parentDeptName.trim().equals("") && parentDept == null){
								infoStr = "导入失败,上级部门 " + parentDeptName + " 不存在存在！";
								 sb.append("{");
						         sb.append("deptName:\"" + deptName + "\"");
						         sb.append(",deptNo:\"" + deptNo + "\"");
						         sb.append(",deptParent:\"" + parentDeptName + "\"");
						         sb.append(",info:\"" + infoStr+ "\"");
						         sb.append(",color:\"" + color + "\"");
						         sb.append("},");
						         continue;
							}
							//isCount++;
					         infoStr = "导入成功";
					  		String telNo = TeeStringUtil.getString(dr.getValueByName("部门电话"));
							String faxNo = TeeStringUtil.getString(dr.getValueByName("部门传真"));
							String deptFunc = TeeStringUtil.getString(dr.getValueByName("部门职能"));
							TeeDepartment dept = new TeeDepartment();
							dept.setDeptName(deptName);
							dept.setDeptNo(deptNo);
							dept.setDeptFunc(deptFunc);
							dept.setFaxNo(faxNo);
							dept.setTelNo(telNo);
							dept.setGuid(TeePassEncryptMD5.getRandomGUID());//guId
							dept.setDeptParentLevel("");
							String  fullId = "";
							String fullName = "";
							if(parentDept != null){//判断是否存在上级部门
								//TeeDepartment parentDept = deptDao.getDeptByName(parentDeptName);//获取上级部门
								dept.setDeptParent(parentDept);
								String deptParentLevel = parentDept.getDeptParentLevel();
								dept.setDeptParentLevel(deptParentLevel + parentDept.getGuid() );//级别,	
								fullId = parentDept.getDeptFullId();
								fullName = parentDept.getDeptFullName();
							}else{
								/*for (int j = 0; j < addDeptList.size(); j++) {//考虑从刚新建的部门查询，由于在当前的事务中
									TeeDepartment addDept = addDeptList.get(j);
									if(addDept.getDeptName().equals(parentDeptName)){
										dept.setDeptParent(addDept);
										String deptParentLevel = addDept.getDeptParentLevel();
										dept.setDeptParentLevel(deptParentLevel + addDept.getGuid() );//级别,	
									}
								}*/
							}
							deptDao.save(dept);//新建
							dept.setDeptFullId(fullId +"/"+ dept.getUuid());
							dept.setDeptFullName(fullName +"/"+ dept.getDeptName());
							deptDao.update(dept);
							addDeptList.add(dept);//将新建的部门存在放到新list
							
							color = "black";
					        sb.append("{");
					        sb.append("deptName:\"" + deptName + "\"");
					        sb.append(",deptNo:\"" + deptNo + "\"");
					        sb.append(",deptParent:\"" + parentDeptName + "\"");
					        sb.append(",info:\"" + infoStr + "\"");
					        sb.append(",color:\"" + color + "\"");
					        sb.append("},");
					        importSuccessCount++;
				        }
				
					
					}
						
				}else{
					json.setRtState(false);
					json.setRtMsg("文件为空！");
					return json;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				throw ex;
			}
			
			if(!sb.equals("[")){
				sb.deleteCharAt(sb.length() -1);
			}
			sb.append("]");
			json.setRtData(sb);
			json.setRtState(true);
			json.setRtMsg(importSuccessCount + "");
			return json;
		}
	    
	    
		/**
		 * 删除部门已经下级部门
		 * @author syl
		 * @date 2013-11-17
		 * @param id
		 * @return
		 * @throws Exception
		 */
		public TeeJson deleteDeptAndSubDept(int id,HttpServletRequest request) throws Exception{
			//声明日志对象
			TeeSysLog sysLog = TeeSysLog.newInstance();
			StringBuffer sb = new StringBuffer();
			
			TeeJson json = new TeeJson();
			TeeDepartment dept = deptDao.get(id);
			if(dept == null){
				return json;
			}
			String guid = dept.getGuid();
			if(!TeeUtility.isNullorEmpty(guid)){
				sb.append("删除部门["+dept.getDeptName()+"] 以及其下属子部门  ");
				
				List<TeeDepartment> subDeptList = deptDao.getAllChildDeptByLevl(guid);
				String deptIds = "";
				sb.append("[");
				for (int i = 0; i < subDeptList.size(); i++) {
					TeeDepartment subDept = subDeptList.get(i);
					subDept.setDeptParent(null);
					subDept.setChildren(null);
					sb.append(subDept.getDeptName());
					if(i!=subDeptList.size()-1){
						sb.append(",");
					}
					List<TeePerson> personList = personDao.selectPersonByDeptId(subDept.getUuid() + "");
					for (int j = 0; j < personList.size(); j++) {
						TeePerson person = personList.get(j);
						person.setDept(null);
						person.setPostDept(null);
					}
					
					deptDao.deleteByObj(subDept);
					//deptIds = deptIds + subDept.getUuid() + ",";
				}
				sb.append("]");
			}
			
			sysLog.setRemark(sb.toString());
			sysLog.setType("002C");
			TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
			
			/**
			 * 将人员的部门和辅助部门设置为空
			 */

			List<TeePerson> personList = personDao.selectPersonByDeptId(id + "");
			for (int j = 0; j < personList.size(); j++) {
				TeePerson person = personList.get(j);
				person.setDept(null);
				person.setPostDept(null);
			}
			deptDao.deleteByObj(dept);

			//同步外部系统
			syncSystem(dept, "5", request);
			
			return json;
		 }
		
	
	    
	    
	    
		public void setDeptDao(TeeDeptDao deptDao) {
			this.deptDao = deptDao;
		}

		public void setPersonDao(TeePersonDao personDao) {
			this.personDao = personDao;
		}

		public void setOrgDao(TeeOrgDao orgDao) {
			this.orgDao = orgDao;
		}

		public TeeDeptDao getDeptDao() {
			return deptDao;
		}

		public TeePersonDao getPersonDao() {
			return personDao;
		}

		public TeeOrgDao getOrgDao() {
			return orgDao;
		}

		/**
		 * 根据部门主键获取部门对象
		 * @param request
		 * @return
		 */
		public TeeJson getDeptByUid(HttpServletRequest request) {
			TeeJson json=new TeeJson();
			int uuid=TeeStringUtil.getInteger(request.getParameter("uuid"), 0);
			TeeDepartment dept=null;
			TeeDepartmentModel model=new TeeDepartmentModel();
			if(uuid!=0){
                dept=(TeeDepartment) simpleDaoSupport.get(TeeDepartment.class,uuid);
			}
			BeanUtils.copyProperties(dept, model);
			
			json.setRtData(model);
			json.setRtState(true);
			
			return json;
		}
		
		/**
		 * 根据部门主键获取部门对象
		 * @param deptId
		 * @return
		 */
		public TeeDepartment get(int deptId){
			TeeDepartment dept=deptDao.get(deptId);
			return dept;		
		}
/**
 * leiqisheng
 * @param model
 */
		public void deletedep(TeeDepartmentModel model) {
			TeeDepartment tdpt = (TeeDepartment)simpleDaoSupport.get(TeeDepartment.class, model.getUuid());
			if(!TeeUtility.isNullorEmpty(tdpt)){
			simpleDaoSupport.deleteByObj(tdpt);
			}
			
		}

         public void upDept(TeeDepartmentModel model) {
	       
        	 TeeDepartment td = (TeeDepartment)simpleDaoSupport.get(TeeDepartment.class,Integer.parseInt(model.getUniqueId()));
        	 if(td!=null){
        		 td.setDeptName(model.getDeptName());
          		simpleDaoSupport.update(td);
        	 }
     		
          }

        /**
         * 根据部门的uuid获取部门集合
         * @param deptManageIds
         * @return
         */
		public List<TeeDepartment> getDeptByUuids(String deptManageIds) {
			// TODO Auto-generated method stub
			return deptDao.getDeptByUuids(deptManageIds);
		}

		
		/**
		 * 获取当前用户的直属机构名称
		 * @param person
		 * @return
		 */
		public String getCurrentPersonOrgName(TeePerson person) {
			// TODO Auto-generated method stub
			person = personDao.get(person.getUuid());
			String deptFullPaths[] = person.getDept().getDeptFullId().split("/");
			for(int i=deptFullPaths.length-1;i>=0;i--){
				if("".equals(deptFullPaths[i])){
					continue;
				}
				TeeDepartment dept = deptDao.get(Integer.parseInt(deptFullPaths[i]));
				if(dept.getDeptType()==2){
					return dept.getDeptName();
				}
			}
			return orgDao.get(4).getUnitName();
		}
		
		
		/**
		 * 获取当前登陆人 也有管理权限的部门集合
		 * @param loginUser
		 * @return
		 */
		public List<TeeDepartment> getPostDeptListByLoginUser(
				TeePerson loginUser) {
			List<TeeDepartment> deptList=null;
			if (TeePersonService.checkIsSuperAdmin(loginUser, "")) {
				deptList=deptDao.getAllDept();
				return deptList;
			}
			if (loginUser.getPostPriv() == 0) {// 本部门
				deptList.add(loginUser.getDept());
				return deptList;
			} else if (loginUser.getPostPriv() == 1) {// 所有
				deptList=deptDao.getAllDept();
				return deptList;
			} else if (loginUser.getPostPriv() == 2) {// 指定部门
				List<TeeDepartment> deptList1 = loginUser.getPostDept();
				return deptList1;
			} else if (loginUser.getPostPriv() == 3) {// 本部以及下级所有部门
				String level = loginUser.getDept().getUuid() + ",";
				if (!TeeUtility
						.isNullorEmpty(loginUser.getDept().getDeptParentLevel())) {// 如果不是第一级部门
					level = loginUser.getDept().getDeptParentLevel();
				} else {// 如果是第一级部门，则把uuID当做级别
					level = loginUser.getDept().getGuid();
				}
				List<TeeDepartment> deptChildList = deptDao
						.getAllChildDeptByLevl(level);
				return  deptChildList;
			}else if (loginUser.getPostPriv() == 4) {// 本机构
				deptList = getDeptsByLoginUser(loginUser);
				return deptList;
			}
			
			return deptList;
		}

		
		
		

		/**
		 * 根据部门ids获取部门树结构(上下级都得有，断部门要把上级部门取出来)
		 * @return
		 */
		public TeeJson getDeptByIds(String deptFilter,
				HttpServletRequest request) {
			TeeJson json=new TeeJson();
			String hql=" from TeeDepartment dept where "+TeeDbUtility.IN("uuid",deptFilter);
			List<TeeDepartment> deptList=simpleDaoSupport.executeQuery(hql, null);
			
			List<TeeDepartment>dList=new ArrayList<TeeDepartment>();	
			if(deptList!=null&&deptList.size()>0){
				//先把数据copy到另外一个集合中
				for (TeeDepartment teeDepartment : deptList) {
					dList.add(teeDepartment);
				}

				String deptFullId="";
				String[] deptArray=null;
				TeeDepartment dept=null;
				for (TeeDepartment teeDepartment : deptList) {
					deptFullId=teeDepartment.getDeptFullId();
					deptFullId=deptFullId.substring(1, deptFullId.length());
					deptArray=deptFullId.split("/");
					for (String id : deptArray) {
						dept=(TeeDepartment) simpleDaoSupport.get(TeeDepartment.class,TeeStringUtil.getInteger(id, 0));
					    if(!dList.contains(dept)){
					    	dList.add(dept);
					    }
					}
				}
			}
			
			
			
		    List<TeeZTreeModel> deptTree = new  ArrayList<TeeZTreeModel>();
		    String pid = "0";
		    TeeZTreeModel ztree=null;

		    for(TeeDepartment dept : dList){
		    	pid = "0";
		    	if(dept.getDeptParent()!=null){
		    		pid = String.valueOf(dept.getDeptParent().getUuid());
		    	}
		    	ztree = new TeeZTreeModel();
		    	ztree.setId(String.valueOf(dept.getUuid()));
		    	ztree.setName(dept.getDeptName());
		    	ztree.setOpen(false);//设置不自动展开
		    	ztree.setpId(pid);
		    	
		    	if(dept.getDeptType()==2){//机构
		    		ztree.setIconSkin("pIconHome");
		    	}else{
		    		ztree.setIconSkin("deptNode");
		    	}
		    	
			    deptTree.add(ztree);
		    }
		    json.setRtData(deptTree);
		    json.setRtState(true);
		    return json;
		}
	    
	
}
