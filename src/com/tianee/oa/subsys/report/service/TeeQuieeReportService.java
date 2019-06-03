package com.tianee.oa.subsys.report.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.module.SimpleAbstractTypeResolver;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.model.TeeFileNetdiskModel;
import com.tianee.oa.core.base.fileNetdisk.service.TeeApacheZipUtil;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.org.service.TeeUserRoleService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.report.bean.TeeQuieeReport;
import com.tianee.oa.subsys.report.dao.TeeQuieeReportDao;
import com.tianee.oa.subsys.report.model.TeeQuieeReportModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeQuieeReportService extends TeeBaseService{

	@Autowired
	private TeeQuieeReportDao quieeReportDao;
	@Autowired
	private TeePersonService personService;
	@Autowired
	private TeeDeptService deptService;
	@Autowired
	private TeeUserRoleService roleService;
	/**
	 * 获取报表目录树
	 * @param requestMap
	 * @param model
	 * @return
	 */
	public TeeJson getReportFolderTree(Map requestMap, TeeQuieeReportModel model) {
		List<TeeZTreeModel> reportFolderTree = new ArrayList<TeeZTreeModel>();
		int reportId = TeeStringUtil.getInteger(requestMap.get("id"), 0);
		TeePerson person = (TeePerson) requestMap.get(TeeConst.LOGIN_USER);

		List<TeeQuieeReport> reports = new ArrayList<TeeQuieeReport>();
		if(reportId==0){//取出第一级（parent=null）有权限的文件夹
			//获取所有有权限的文件夹,一次性加载，包含有权限的父级文件夹
			reports = simpleDaoSupport
					.executeQuery("from TeeQuieeReport r where (r.parent.sid=0 or r.parent is null)  and r.reportType=1 "
							+ " and (exists (select 1 from r.userPrivManage userPrivManage where userPrivManage.uuid ="+person.getUuid()+")"
							+ " or exists (select 1 from r.deptPrivManage deptPrivManage where deptPrivManage.uuid ="+person.getDept().getUuid()+")"
							+ " or exists (select 1 from r.rolePrivManage rolePrivManage where rolePrivManage.uuid ="+person.getUserRole().getUuid()+")"
							+ " or exists (select 1 from r.userPrivView userPrivView where userPrivView.uuid ="+person.getUuid()+")"
							+ " or exists (select 1 from r.deptPrivView deptPrivView where deptPrivView.uuid ="+person.getDept().getUuid()+")"
							+ " or exists (select 1 from r.rolePrivView rolePrivView where rolePrivView.uuid ="+person.getUserRole().getUuid()+"))", null);
		    //系统管理员    无权限控制
			if (TeePersonService.checkIsSuperAdmin(person, null)) {
				reports = simpleDaoSupport
						.executeQuery("from TeeQuieeReport r where (r.parent.sid=0 or r.parent is null) and r.reportType=1 ", null);
			}
		}else{//取出parent.sid=reportId的所有有权限的文件夹
			reports = simpleDaoSupport
					.executeQuery("from TeeQuieeReport r where r.parent.sid="+reportId+" and r.reportType=1 "
							+ " and (exists (select 1 from r.userPrivManage userPrivManage where userPrivManage.uuid ="+person.getUuid()+")"
							+ " or exists (select 1 from r.deptPrivManage deptPrivManage where deptPrivManage.uuid ="+person.getDept().getUuid()+")"
							+ " or exists (select 1 from r.rolePrivManage rolePrivManage where rolePrivManage.uuid ="+person.getUserRole().getUuid()+")"
							+ " or exists (select 1 from r.userPrivView userPrivView where userPrivView.uuid ="+person.getUuid()+")"
							+ " or exists (select 1 from r.deptPrivView deptPrivView where deptPrivView.uuid ="+person.getDept().getUuid()+")"
							+ " or exists (select 1 from r.rolePrivView rolePrivView where rolePrivView.uuid ="+person.getUserRole().getUuid()+"))",null);
		
			//系统管理员    无权限控制
			if (TeePersonService.checkIsSuperAdmin(person, null)) {
				reports = simpleDaoSupport
						.executeQuery("from TeeQuieeReport r where r.parent.sid="+reportId+" and r.reportType=1 ",null);
			    }
		}

		for (TeeQuieeReport report : reports) {
			TeeZTreeModel ztree = new TeeZTreeModel();
			ztree.setId(String.valueOf(report.getSid()));
			ztree.setName(report.getReportName());
			if (report.getParent()!= null) {
				ztree.setpId(report.getParent().getSid() + "");
			} else {
				ztree.setpId("0");
			}
			ztree.setParent(true);
			ztree.setIconSkin(TeeZTreeModel.FILE_FOLDER);
			reportFolderTree.add(ztree);
		}
		
		TeeJson json = new TeeJson();
		json.setRtData(reportFolderTree);
		json.setRtState(true);
		json.setRtMsg("报表目录获取成功!");
		return json;
	}
	
	
	
	/**
	 * 获取当前登录的用户  对文件夹是管理权限   还是查看权限
	 * @param folderSid
	 * @param person
	 * @return
	 */
	public TeeJson getPriv(int folderSid, TeePerson person) {
		TeeJson  json=new TeeJson();
		int priv=0;//权限  1：管理权限     2.查看权限
		List<TeeQuieeReport> reports = new ArrayList<TeeQuieeReport>();
		//系统管理员    无权限控制
		if (TeePersonService.checkIsSuperAdmin(person, null)) {
			reports = simpleDaoSupport
					.executeQuery("from TeeQuieeReport r where r.sid="+folderSid,null);
		}else{
			reports=simpleDaoSupport.executeQuery("from TeeQuieeReport r where r.sid="+folderSid
					+ " and (exists (select 1 from r.userPrivManage userPrivManage where userPrivManage.uuid ="+person.getUuid()+")"
					+ " or exists (select 1 from r.deptPrivManage deptPrivManage where deptPrivManage.uuid ="+person.getDept().getUuid()+")"
					+ " or exists (select 1 from r.rolePrivManage rolePrivManage where rolePrivManage.uuid ="+person.getUserRole().getUuid()+"))", null);
			
		}
			
	    if(reports.size()>0){//管理权限
	    	priv=1;
	    }else{//查看权限
	    	priv=2;
	    }
	   
	    json.setRtData(priv);
        json.setRtState(true);
	    return json;
	}


    /**
     * 新建文件夹
     * @param requestMap
     * @return
     */
	public TeeJson addFolder(Map requestMap) {
		TeeJson json=new TeeJson();
		TeeQuieeReport report=new TeeQuieeReport();
		//获取父文件夹的主键
		int parentId=TeeStringUtil.getInteger(requestMap.get("parentId"), 0);
		int sortNo=TeeStringUtil.getInteger(requestMap.get("sortNo"), 0);
		String reportName=TeeStringUtil.getString(requestMap.get("reportName"));
		//获取人员管理权限
		String userManageIds=TeeStringUtil.getString(requestMap.get("userManageIds"));
		//获取部门管理权限
		String deptManageIds=TeeStringUtil.getString(requestMap.get("deptManageIds"));
		//获取角色管理权限
		String roleManageIds=TeeStringUtil.getString(requestMap.get("roleManageIds"));
		//获取人员查看权限
		String userViewIds=TeeStringUtil.getString(requestMap.get("userViewIds"));
		//获取部门查看权限
		String deptViewIds=TeeStringUtil.getString(requestMap.get("deptViewIds"));
		//获取角色查看权限
		String roleViewIds=TeeStringUtil.getString(requestMap.get("roleViewIds"));
		
		TeeQuieeReport parent=(TeeQuieeReport) simpleDaoSupport.get(TeeQuieeReport.class,parentId);
		List<TeePerson> userPrivManage=personService.getPersonByUuids(userManageIds);
		Set<TeePerson> userPrivManageSet = new HashSet(userPrivManage);//人员管理权限集合
		List<TeePerson> userPrivView=personService.getPersonByUuids(userViewIds);
		Set<TeePerson> userPrivViewSet = new HashSet(userPrivView);//人员查看权限集合
		
		List<TeeDepartment> deptPrivManage=deptService.getDeptByUuids(deptManageIds);
		Set<TeeDepartment> deptPrivManageSet = new HashSet(deptPrivManage);//人员管理权限集合
		List<TeeDepartment> deptPrivView=deptService.getDeptByUuids(deptViewIds);
		Set<TeeDepartment> deptPrivViewSet = new HashSet(deptPrivView);//人员查看权限集合
		
		List<TeeUserRole> rolePrivManage=roleService.getUserRoleByUuids(roleManageIds);
		Set<TeeUserRole> rolePrivManageSet = new HashSet(rolePrivManage);//人员管理权限集合
		List<TeeUserRole> rolePrivView=roleService.getUserRoleByUuids(roleViewIds);
		Set<TeeUserRole> rolePrivViewSet = new HashSet(rolePrivView);//人员查看权限集合
		
		report.setParent(parent);
		report.setReportName(reportName);
		report.setReportType(1);
		report.setCrTime(Calendar.getInstance());
		report.setSortNo(sortNo);
		report.setDeptPrivManage(deptPrivManageSet);
		report.setDeptPrivView(deptPrivViewSet);
		report.setUserPrivManage(userPrivManageSet);
		report.setUserPrivView(userPrivViewSet);
		report.setRolePrivManage(rolePrivManageSet);
		report.setRolePrivView(rolePrivViewSet);
		simpleDaoSupport.save(report);
		json.setRtState(true);
		return json;
	}


	 //获取父文件夹下的有权限的  文件夹 和文件列表
	public TeeEasyuiDataGridJson getReportPage(TeeDataGridModel dm, HttpServletRequest request) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		int folderSid = TeeStringUtil.getInteger(request.getParameter("folderSid"), 0);//父文件夹主键 
        //获取总记录数
		dataGridJson.setTotal(quieeReportDao.getCountByFolderSid(folderSid,
				person));
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置

		List<TeeQuieeReport> reportList = quieeReportDao.getReportPage(
				firstIndex, dm.getRows(), dm,folderSid, person);

		List<TeeQuieeReportModel> models = new ArrayList<TeeQuieeReportModel>();

		if (reportList != null && reportList.size() > 0) {
			for (TeeQuieeReport report : reportList) {
				TeeQuieeReportModel model=parseToModel(report);
				models.add(model);
			}	
		}
		dataGridJson.setRows(models);
		return dataGridJson;
	}
	
	/**
	 * 将model转换成report
	 * @param model
	 * @return
	 */
	public TeeQuieeReport parseToReport(TeeQuieeReportModel model,TeeQuieeReport report){
		BeanUtils.copyProperties(model, report);
		//父文件夹
		TeeQuieeReport parent=(TeeQuieeReport) simpleDaoSupport.get(TeeQuieeReport.class,model.getParentSid());
		if(parent!=null){
			report.setParent(parent);
		}
		
		//获取人员管理权限
		String userManageIds=TeeStringUtil.getString(model.getUserManageIds());
		//获取部门管理权限
		String deptManageIds=TeeStringUtil.getString(model.getDeptManageIds());
		//获取角色管理权限
		String roleManageIds=TeeStringUtil.getString(model.getRoleManageIds());
		//获取人员查看权限
		String userViewIds=TeeStringUtil.getString(model.getUserViewIds());
		//获取部门查看权限
		String deptViewIds=TeeStringUtil.getString(model.getDeptViewIds());
		//获取角色查看权限
		String roleViewIds=TeeStringUtil.getString(model.getRoleViewIds());
		
		List<TeePerson> userPrivManage=personService.getPersonByUuids(userManageIds);
		Set<TeePerson> userPrivManageSet = new HashSet(userPrivManage);//人员管理权限集合
		List<TeePerson> userPrivView=personService.getPersonByUuids(userViewIds);
		Set<TeePerson> userPrivViewSet = new HashSet(userPrivView);//人员查看权限集合
		
		List<TeeDepartment> deptPrivManage=deptService.getDeptByUuids(deptManageIds);
		Set<TeeDepartment> deptPrivManageSet = new HashSet(deptPrivManage);//人员管理权限集合
		List<TeeDepartment> deptPrivView=deptService.getDeptByUuids(deptViewIds);
		Set<TeeDepartment> deptPrivViewSet = new HashSet(deptPrivView);//人员查看权限集合
		
		List<TeeUserRole> rolePrivManage=roleService.getUserRoleByUuids(roleManageIds);
		Set<TeeUserRole> rolePrivManageSet = new HashSet(rolePrivManage);//人员管理权限集合
		List<TeeUserRole> rolePrivView=roleService.getUserRoleByUuids(roleViewIds);
		Set<TeeUserRole> rolePrivViewSet = new HashSet(rolePrivView);//人员查看权限集合
		
		report.setDeptPrivManage(deptPrivManageSet);
		report.setDeptPrivView(deptPrivViewSet);
		report.setUserPrivManage(userPrivManageSet);
		report.setUserPrivView(userPrivViewSet);
		report.setRolePrivManage(rolePrivManageSet);
		report.setRolePrivView(rolePrivViewSet);
		
		return report;
	}
	/**
	 * 将report类转换成model
	 * @param report
	 * @return
	 */
    public TeeQuieeReportModel  parseToModel(TeeQuieeReport report){
    	TeeQuieeReportModel model=new TeeQuieeReportModel();
    	BeanUtils.copyProperties(report, model);
    	
    	//创建时间
    	Calendar date=report.getCrTime();
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String crTimeStr=sdf.format(date.getTime());
    	model.setCrTimeStr(crTimeStr);
    	
    	//是否存在报表文件
    	if(report.getRaqFile()!=null&&report.getRaqFile().length>0){
    		model.setIsExistRaqFile(1);
    	}else{
    		model.setIsExistRaqFile(0);
    	}
    	//是否存在参数文件
    	if(report.getRaqParamFile()!=null&&report.getRaqParamFile().length>0){
    		model.setIsExistRaqParamFile(1);
    	}else{
    		model.setIsExistRaqParamFile(0);
    	}
    	//用户管理权限
    	Set<TeePerson> userPrivManage=report.getUserPrivManage();
    	List<TeePerson>userPrivManageList=new  ArrayList<TeePerson>(userPrivManage);
    	String userManageIds="";
    	String userManageNames="";
    	if(userPrivManageList.size()>0){
    		for (TeePerson teePerson : userPrivManageList) {
        		userManageIds+=teePerson.getUuid()+",";
        		userManageNames+=teePerson.getUserName()+",";
    		}	
    	}
    	if(userManageIds.endsWith(",")){
    		userManageIds=userManageIds.substring(0,userManageIds.length()-1);
    	}
    	if(userManageNames.endsWith(",")){
    		userManageNames=userManageNames.substring(0,userManageNames.length()-1);
    	}
    	model.setUserManageIds(userManageIds);
    	model.setUserManageNames(userManageNames);
    	//用户查看权限
    	Set<TeePerson> userPrivView=report.getUserPrivView();
    	List<TeePerson>userPrivViewList=new  ArrayList<TeePerson>(userPrivView);
    	String userViewIds="";
    	String userViewNames="";
    	if(userPrivViewList.size()>0){
        	for (TeePerson teePerson : userPrivViewList) {
        		userViewIds+=teePerson.getUuid()+",";
        		userViewNames+=teePerson.getUserName()+",";
    		}
    	}
    	if(userViewIds.endsWith(",")){
    		userViewIds=userViewIds.substring(0,userViewIds.length()-1);
    	}
    	if(userViewNames.endsWith(",")){
    		userViewNames=userViewNames.substring(0,userViewNames.length()-1);
    	}
    	model.setUserViewIds(userViewIds);
    	model.setUserViewNames(userViewNames);
    	//部门管理权限
    	Set<TeeDepartment> deptPrivManage=report.getDeptPrivManage();
    	List<TeeDepartment>deptPrivManageList=new  ArrayList<TeeDepartment>(deptPrivManage);
    	String deptManageIds="";
    	String deptManageNames="";
    	if(deptPrivManageList.size()>0){
        	for (TeeDepartment teeDepartment : deptPrivManageList) {
    			deptManageIds+=teeDepartment.getUuid()+",";
    			deptManageNames+=teeDepartment.getDeptName()+",";
    		}
    	}
    	if(deptManageIds.endsWith(",")){
    		deptManageIds=deptManageIds.substring(0,deptManageIds.length()-1);
    	}
    	if(deptManageNames.endsWith(",")){
    		deptManageNames=deptManageNames.substring(0,deptManageNames.length()-1);
    	}
    	model.setDeptManageIds(deptManageIds);
    	model.setDeptManageNames(deptManageNames);
    	//部门查看权限
    	Set<TeeDepartment> deptPrivView=report.getDeptPrivView();
    	List<TeeDepartment>deptPrivViewList=new  ArrayList<TeeDepartment>(deptPrivView);
    	String deptViewIds="";
    	String deptViewNames="";
    	if(deptPrivViewList.size()>0){
        	for (TeeDepartment teeDepartment : deptPrivViewList) {
        		deptViewIds+=teeDepartment.getUuid()+",";
        		deptViewNames+=teeDepartment.getDeptName()+",";
    		}
    	}
    	if(deptViewIds.endsWith(",")){
    		deptViewIds=deptViewIds.substring(0,deptViewIds.length()-1);
    	}
    	if(deptViewNames.endsWith(",")){
    		deptViewNames=deptViewNames.substring(0,deptViewNames.length()-1);
    	}
    	model.setDeptViewIds(deptViewIds);
    	model.setDeptViewNames(deptViewNames);
    	//角色管理权限
    	Set<TeeUserRole> rolePrivManage=report.getRolePrivManage();
    	List<TeeUserRole>rolePrivManageList=new  ArrayList<TeeUserRole>(rolePrivManage);
    	String roleManageIds="";
    	String roleManageNames="";
    	if(rolePrivManageList.size()>0){
    		for (TeeUserRole teeUserRole : rolePrivManageList) {
        		roleManageIds+=teeUserRole.getUuid()+",";
        		roleManageNames+=teeUserRole.getRoleName()+",";
    		}
    	}  	
    	if(roleManageIds.endsWith(",")){
    		roleManageIds=roleManageIds.substring(0,roleManageIds.length()-1);
    	}
    	if(roleManageNames.endsWith(",")){
    		roleManageNames=roleManageNames.substring(0,roleManageNames.length()-1);
    	}
    	model.setRoleManageIds(roleManageIds);
    	model.setRoleManageNames(roleManageNames);
    	//角色查看权限
    	Set<TeeUserRole> rolePrivView=report.getRolePrivView();
    	List<TeeUserRole>rolePrivViewList=new  ArrayList<TeeUserRole>(rolePrivView);
    	String roleViewIds="";
    	String roleViewNames="";
    	if(rolePrivViewList.size()>0){
        	for (TeeUserRole teeUserRole : rolePrivViewList) {
        		roleViewIds+=teeUserRole.getUuid()+",";
        		roleViewNames+=teeUserRole.getRoleName()+",";		
    		}
    	}
    	if(roleViewIds.endsWith(",")){
    		roleViewIds=roleViewIds.substring(0,roleViewIds.length()-1);
    	}
    	if(roleViewNames.endsWith(",")){
    		roleViewNames=roleViewNames.substring(0,roleViewNames.length()-1);
    	}
    	model.setRoleViewIds(roleViewIds);
    	model.setRoleViewNames(roleViewNames);

        return model;
    }


    /**
     * 批量删除  文件和文件夹
     * @param request
     * @return
     */
	public TeeJson deleteReportsBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String sids=request.getParameter("sids");
		String[] sidArray=sids.split(",");
		int sid=0;
		TeeQuieeReport report;
		for (String str : sidArray) {
			sid=Integer.parseInt(str);
			//根据主键  获取report对象
			report=(TeeQuieeReport) simpleDaoSupport.get(TeeQuieeReport.class,sid);
		    /*if(report.getReportType()==1){//文件夹
		    	//先删除子文件夹和文件
		    	simpleDaoSupport.executeUpdate("delete from TeeQuieeReport r where r.parent.sid=?", new Object[]{report.getSid()});
		    	//删除父文件夹
		    	simpleDaoSupport.deleteByObj(report);
		    }else{//文件
		    	simpleDaoSupport.deleteByObj(report);
		    }*/
			deleteFolder(report);//递归删除文件和文件夹
		}
		json.setRtState(true);
		return json;
	}

	/**
	 * 递归删除文件夹
	 */
    public void deleteFolder(TeeQuieeReport report){
    	
    	if(report.getReportType()==1){//文件夹
    		//获取父文件下的所有文件 和文件夹的集合
    		List<TeeQuieeReport> list=simpleDaoSupport.executeQuery("from TeeQuieeReport r where r.parent.sid=?", new Object[]{report.getSid()});
	    	for (TeeQuieeReport teeQuieeReport : list) {
	    		deleteFolder(teeQuieeReport);  		
			}	
	    	//删除父文件夹	
	    	simpleDaoSupport.deleteByObj(report);
	    }else{//文件
	    	simpleDaoSupport.deleteByObj(report);
	    }
    	
    }
    /**
     * 根据主键   获取详情
     * @param request
     * @return
     */
	public TeeJson getBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		//根据主键  获取报表对象
		TeeQuieeReport report=(TeeQuieeReport) simpleDaoSupport.get(TeeQuieeReport.class,sid);
		TeeQuieeReportModel model=parseToModel(report);
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}


    /**
     * 编辑文件夹
     * @param requestMap
     * @return
     */
	public TeeJson editFolder(TeeQuieeReportModel model) {
		TeeJson json=new TeeJson();
		TeeQuieeReport report=(TeeQuieeReport) simpleDaoSupport.get(TeeQuieeReport.class,model.getSid());
		parseToReport(model,report);
		simpleDaoSupport.update(report);
		json.setRtState(true);
		return json;
	}


    /**
     * 上传报表
     * @param multiRequest
     * @return
     * @throws IOException 
     */
	public TeeJson addReport(MultipartHttpServletRequest multiRequest) throws IOException {
		TeeJson json=new TeeJson();
		TeeQuieeReport report=new TeeQuieeReport();
		//获取符文件夹的主键
		int parentId=TeeStringUtil.getInteger(multiRequest.getParameter("parentId"), 0);
		TeeQuieeReport parent=(TeeQuieeReport) simpleDaoSupport.get(TeeQuieeReport.class,parentId);
		//报表名称
		String reportName=multiRequest.getParameter("reportName");
		//排序号
		int sortNo=TeeStringUtil.getInteger(multiRequest.getParameter("sortNo"), 0);

		byte[]raqFile=null;
		byte[]raqParamFile=null;
		if(multiRequest.getFile("raqFile")!=null){
			raqFile=multiRequest.getFile("raqFile").getBytes();
		}
		if(multiRequest.getFile("paraFile")!=null){
			raqParamFile=multiRequest.getFile("paraFile").getBytes();
		}
		
		report.setCrTime(Calendar.getInstance());
		report.setSortNo(sortNo);
		report.setReportName(reportName);
		report.setParent(parent);
		report.setRaqFile(raqFile);
		report.setRaqParamFile(raqParamFile);
		report.setReportName(reportName);
		report.setReportType(2);
		
		simpleDaoSupport.save(report);
		json.setRtState(true);
		return json;
	}


    /**
     * 编辑报表
     * @param multiRequest
     * @return
     * @throws IOException 
     */
	public TeeJson editReport(MultipartHttpServletRequest multiRequest) throws IOException {
		TeeJson json=new TeeJson();
		//获取当前文件的主键
		 int sid=TeeStringUtil.getInteger(multiRequest.getParameter("sid"), 0);
		 TeeQuieeReport report=(TeeQuieeReport) simpleDaoSupport.get(TeeQuieeReport.class,sid);
		 
		 String reportName=multiRequest.getParameter("reportName");
		 int sortNo=TeeStringUtil.getInteger(multiRequest.getParameter("sortNo"), 0);
		 
		 byte[]raqFile=null;
	     byte[]raqParamFile=null;
		 if(multiRequest.getFile("raqFile")!=null){
			raqFile=multiRequest.getFile("raqFile").getBytes();
		 }
		 if(multiRequest.getFile("paraFile")!=null){
			raqParamFile=multiRequest.getFile("paraFile").getBytes();
			}
		 report.setReportName(reportName);
		 report.setSortNo(sortNo);
		 if(raqFile!=null){
			report.setRaqFile(raqFile); 
		 }
		 if(raqParamFile!=null){
			report.setRaqParamFile(raqParamFile); 
		 }
		 simpleDaoSupport.update(report);
		 json.setRtState(true);
		 return json;
	}


    /**
     * 获取一级目录列表
     * @param dm
     * @param request
     * @return
     */
	public TeeEasyuiDataGridJson getFolderList(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
        //获取总记录数
		dataGridJson.setTotal(quieeReportDao.getFirstFolderCount());
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置

		List<TeeQuieeReport> reportList = quieeReportDao.getFirstFolderList(
				firstIndex, dm.getRows(), dm);

		List<TeeQuieeReportModel> models = new ArrayList<TeeQuieeReportModel>();

		if (reportList != null && reportList.size() > 0) {
			for (TeeQuieeReport report : reportList) {
				TeeQuieeReportModel model=parseToModel(report);
				models.add(model);
			}	
		}
		dataGridJson.setRows(models);
		return dataGridJson;
	}
}
