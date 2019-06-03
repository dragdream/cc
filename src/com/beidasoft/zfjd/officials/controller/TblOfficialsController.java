package com.beidasoft.zfjd.officials.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.common.service.CommonService;
import com.beidasoft.zfjd.department.service.TblDepartmentService;
import com.beidasoft.zfjd.officials.bean.TblOfficials;
import com.beidasoft.zfjd.officials.model.TblOfficialsModel;
import com.beidasoft.zfjd.officials.service.TblOfficialsService;
import com.beidasoft.zfjd.subject.controller.SubjectController;
import com.beidasoft.zfjd.system.bean.SysBusinessRelation;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.general.service.TeeSysCodeService;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

/**
 * 执法人员表CONTROLLER类
 */
@Controller
@RequestMapping("OfficialsCtrl")
public class TblOfficialsController {

    @Autowired
    private TblOfficialsService OfficialsService;
    @Autowired
	private TeeAttachmentService attachmentService;
    @Autowired
	private TblDepartmentService departmentService;
    @Autowired
   	private TeeSysCodeService sysCodeService;
    @Autowired
   	private SubjectController subjectController;
    @Autowired
    private CommonService commonservivse;
    
    //所属主体
    String busName = "";
    /**
     * 根据当前登录人，获取主体名称
     * @return
     */
    @ResponseBody
   	@RequestMapping("getOrgSystemByCurrentPerson.action")
   	public TeeJson getOrgSystemByCurrentPerson(){
		TeeJson json = new TeeJson();
		try {
			TblOfficialsModel officialsModel = new TblOfficialsModel();
	   		//获取当前登录人
			TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
			//当前登录人所在的部门
			TeeDepartment department = user.getDept();
			//部门ID
			int deptId = department.getUuid();
			
			if(!TeeUtility.isNullorEmpty(OfficialsService.getSysBusinessRelationByDeptId(deptId))){
				//通过部门ID，查询到SysBusinessRelation对象（）
				SysBusinessRelation sysBusinessRelation = OfficialsService.getSysBusinessRelationByDeptId(deptId);
				
				if(!TeeUtility.isNullorEmpty(sysBusinessRelation.getBusinessDeptId())){
					String businessDeptId = sysBusinessRelation.getBusinessDeptId();
					String businessDeptName = sysBusinessRelation.getBusinessDeptName();
					officialsModel.setDeptName(businessDeptName);
					officialsModel.setDeptId(businessDeptId);
					//获得部门信息主体信息
					if(!TeeUtility.isNullorEmpty(sysBusinessRelation.getBusinessSubjectId())){
						String businessSubId = sysBusinessRelation.getBusinessSubjectId();
						busName = sysBusinessRelation.getBusinessSubjectName();
						officialsModel.setBusinessSubName(busName);
						officialsModel.setBusinessSubId(businessSubId);
					}else{
						officialsModel.setBusinessSubName("1");
						officialsModel.setBusinessSubId("1");
					}
				}else{
					officialsModel.setDeptName("1");
					officialsModel.setDeptId("1");
				}
			}
			json.setRtData(officialsModel);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("tblOfficialsController.getOrgSystemByCurrentPerson"+e.getMessage());
		}
		
   		return json;
   	}

    
    /**
	 * 保存
	 * @param lawModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("save")
	public TeeJson save(TblOfficialsModel officialsModel){
		TeeJson json = new TeeJson();
		try {
			json = OfficialsService.save(officialsModel);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("tblOfficialsController.save"+e.getMessage());
		}
		return json;
	}
	/**
	 * 更新
	 * @param lawModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("update")
	public TeeJson update(TblOfficialsModel officialsModel){
		TeeJson json = new TeeJson();
		try {
			json = OfficialsService.updateUser(officialsModel);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("tblOfficialsController.update"+e.getMessage());
		}
		
		return json;
	}
	
	/**
	 * 审核
	 * @param lawModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("examine")
	public TeeJson examine(String id){
		TeeJson json = new TeeJson();
		try {
			//获取当前登录人
			TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
			//创建实例化实体类对象
			TblOfficials officials = new TblOfficials();
			officials = OfficialsService.getById(id);
			
			if(!TeeUtility.isNullorEmpty(officials.getExamine())){
				if(officials.getExamine()==1){
					officials.setExamine(0);
					officials.setDisExamineTime(new Date());
				}else{
					officials.setExamine(1);
					officials.setExamineTime(new Date());
					officials.setExamineId(user.getUserId());
				}
			}
			
			officials.setExamineTime(new Date());
			OfficialsService.update(officials);	
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("tblOfficialsController.examine"+e.getMessage());
		}
		return json;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public TeeJson dalete(String id){
		TeeJson json = new TeeJson();
		try {
			//创建实例化实体类对象
			TblOfficials officials = new TblOfficials();
			officials = OfficialsService.getById(id);
			
			officials.setIsDelete(1);
			officials.setDeleteTime(new Date());
			OfficialsService.update(officials);	
			
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("tblOfficialsController.delete"+e.getMessage());
		}
		return json;
	}
	/**
	 * 获取数据
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@ResponseBody
	@RequestMapping("get")
	public TeeJson get(String id){
			TeeJson json = new TeeJson();
			try {
				TblOfficials officials = OfficialsService.getById(id);
				
				TblOfficialsModel officialsModel = new TblOfficialsModel();
				BeanUtils.copyProperties(officials, officialsModel);
				
				//单独处理日期
				if(!TeeUtility.isNullorEmpty(officials.getBirth())){
					officialsModel.setBirthStr(TeeDateUtil.format(officials.getBirth(), "yyyy-MM-dd"));
				}
				if(!TeeUtility.isNullorEmpty(officials.getDepartmentGettime())){
					officialsModel.setDepartmentGettimeStr(TeeDateUtil.format(officials.getDepartmentGettime(), "yyyy-MM-dd"));
				}
				if(!TeeUtility.isNullorEmpty(officials.getCityGettime())){
					officialsModel.setCityGettimeStr(TeeDateUtil.format(officials.getCityGettime(), "yyyy-MM-dd"));
				}
				if(!TeeUtility.isNullorEmpty(officials.getEffectiveTime())){
					officialsModel.setEffectiveTimeStr(TeeDateUtil.format(officials.getEffectiveTime(), "yyyy-MM-dd"));
				}
				if(!TeeUtility.isNullorEmpty(officials.getDeptEffectiveTime())){
					officialsModel.setDeptEffectiveTimeStr(TeeDateUtil.format(officials.getDeptEffectiveTime(), "yyyy-MM-dd"));
				}
				
				//获取所属主体
				List<Map> subName = OfficialsService.listByBus(0, 10, officials.getId());
				for (Map map : subName) {
					String a =(String) subName.get(0).get("ID");
					String b =(String) subName.get(0).get("SUB_NAME");

					officialsModel.setBusinessSubId(a);
					officialsModel.setBusinessSubName(b);
				}

				json.setRtData(officialsModel);
				json.setRtState(true);
			} catch (Exception e) {
				// TODO: handle exception
				json.setRtState(false);
				System.out.println("tblOfficialsController.save"+e.getMessage());
			}
			
			return json;
		}
	
	@ResponseBody
	@RequestMapping("findAllUsers")
	public TeeJson findAllUsers(){
		return null;
	}
	/**
	 * 分页
	 * @param dataGridModel
	 * @param queryModel
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@ResponseBody
    @RequestMapping("listByPage")
    public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,TblOfficialsModel queryModel){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		//获取当前登录人
		TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
		if(!"admin".equals(user.getUserId())){
			//当前登录人所在的部门
			TeeDepartment department = user.getDept();
			//部门ID
			int deptId = department.getUuid();
			//通过部门ID，查询到SysBusinessRelation对象（）
			SysBusinessRelation sysBusinessRelation = OfficialsService.getSysBusinessRelationByDeptId(deptId);
			if(!TeeUtility.isNullorEmpty(sysBusinessRelation)){
				//获得部门Id
				String DeptId = sysBusinessRelation.getBusinessDeptId();
				//获取所在部门下所有人员ID
				List<Map> deptIdMap = OfficialsService.getDeptId(0,99999999,DeptId);
				if(deptIdMap.size()!=0){
					String idsList = "";
					for (int i = 0; i < deptIdMap.size(); i++) {
						String a = (String) deptIdMap.get(i).get("PERSON_ID");
						idsList +="'"+a+"'"+",";
					}
					idsList = idsList.substring(0,idsList.length()-1);
					queryModel.setDeptId(idsList);
				}
			}
		}
		long total = OfficialsService.getTotal(queryModel);
		List<TblOfficialsModel> modelList = new ArrayList();
		List<TblOfficials> lawInfos = OfficialsService.listByPage(
				dataGridModel.getFirstResult(), dataGridModel.getRows(),
				queryModel);
		for (TblOfficials officials : lawInfos) {
			TblOfficialsModel officialsModel = new TblOfficialsModel();
			BeanUtils.copyProperties(officials,officialsModel);
			
			if(!TeeUtility.isNullorEmpty(officials.getBirth())){
				Calendar cal = Calendar.getInstance();
				Calendar bir = Calendar.getInstance();
				bir.setTime(officials.getBirth());
				int yearNow = cal.get(Calendar.YEAR);
				/*取出出生年月日*/
				int yearBirth = bir.get(Calendar.YEAR);
				/*大概年龄是当前年减去出生年*/
				int age = yearNow - yearBirth + 1;
				officialsModel.setBirthStr(Integer.toString(age));
			}
			
			//获取所属主体
			List<Map> subName = OfficialsService.listByBus(0, 10, officials.getId());
			for (Map map : subName) {
				String a =(String) subName.get(0).get("SUB_NAME");
				officialsModel.setBusinessSubName(a);
			}
			
			modelList.add(officialsModel);

		}

		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);

		return dataGridJson;

	}
	/**
	 * 查看界面获取数据
	 */
	@ResponseBody
	@RequestMapping("getPersonById")
	public TeeJson getPersonById(String id,HttpServletRequest request){
		TeeJson json = new TeeJson();
        try {
            TblOfficialsModel officialsModel = OfficialsService.getPersonById(id);
            json.setRtData(officialsModel);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	json.setRtState(false);
            e.printStackTrace();
        }
        return json;
	}
	/**
	 * 多删
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deletes")
	public TeeJson deletes(HttpServletRequest request){

    	String id = request.getParameter("ids");
    	TeeJson json = new TeeJson();
    	OfficialsService.deletes(id);
    	json.setRtState(true);
		return null;
	}
	
	/**
	 * 执法人员管理 分页 权限控制
	 * @param tModel
	 * @param cbModel
	 * @param request
	 * @return
	 * @date:2019年3月6日下午3:47:01
	 * @author:yxy
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@ResponseBody
    @RequestMapping("findSearchListBypage")
	public TeeEasyuiDataGridJson findSearchListBypage(TeeDataGridModel tModel, TblOfficialsModel cbModel, HttpServletRequest request) {
			//返回前台json 
	        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson(); 

	        // 接收查询集合 cList
	        List<TblOfficials> cList = null; 
	        //bean对应的model cModels
	        List<TblOfficialsModel> cModels = new ArrayList<TblOfficialsModel>(); 
	        //查询集合总数
	        Long count = null;
	        try {
	            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
	            cList = OfficialsService.findListByPageSearch(tModel, cbModel, orgCtrl);
	            count = OfficialsService.listSearchCount(cbModel, orgCtrl);
	            //定义model
	            TblOfficialsModel cModel = null;
	            if (cList != null && cList.size() > 0) {
	                for (int i = 0; i < cList.size(); i++) {
	                    cModel = new TblOfficialsModel();
	                    TblOfficials tempBasic = cList.get(i);
	                    //将tempBasic赋值给cModel
	                    BeanUtils.copyProperties(tempBasic, cModel);
	                    if(!TeeUtility.isNullorEmpty(tempBasic.getBirth())){
	        				Calendar cal = Calendar.getInstance();
	        				Calendar bir = Calendar.getInstance();
	        				bir.setTime(tempBasic.getBirth());
	        				int yearNow = cal.get(Calendar.YEAR);
	        				/*取出出生年月日*/
	        				int yearBirth = bir.get(Calendar.YEAR);
	        				/*大概年龄是当前年减去出生年*/
	        				int age = yearNow - yearBirth + 1;
	        				cModel.setBirthStr(Integer.toString(age));
	        			}
	        			
	        			//获取所属主体
	        			List<Map> subName = OfficialsService.listByBus(0, 10, tempBasic.getId());
	        			for (Map map : subName) {
	        				String a =(String) subName.get(0).get("SUB_NAME");
	        				cModel.setBusinessSubName(a);
	        			}
	                    cModels.add(cModel);
	                }
	            }
	            
	            json.setRows(cModels);
	            json.setTotal(count);
	        } catch (Exception e) {
	            System.out.println("TblOfficialsController.findSearchListBypage() " + e.getMessage());
	        }
	        return json;
	    }
	
	/**
	 * 执法人员查询 分页 权限控制
	 * @param tModel
	 * @param cbModel
	 * @param request
	 * @return
	 * @date:2019年3月6日下午5:27:55
	 * @author:yxy
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@ResponseBody
    @RequestMapping("findSearchListBypageQuery")
	public TeeEasyuiDataGridJson findSearchListBypageQuery(TeeDataGridModel tModel, TblOfficialsModel cbModel, HttpServletRequest request) {
			//返回前台json 
	        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson(); 

	        // 接收查询集合 cList
	        List<TblOfficials> cList = null; 
	        //bean对应的model cModels
	        List<TblOfficialsModel> cModels = new ArrayList<TblOfficialsModel>(); 
	        //查询集合总数
	        Long count = null;
	        try {
	            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
	            cList = OfficialsService.findListByPageSearchQuery(tModel, cbModel, orgCtrl);
	            count = OfficialsService.listSearchCountQuery(cbModel, orgCtrl);
	            //定义model
	            TblOfficialsModel cModel = null;
	            if (cList != null && cList.size() > 0) {
	                for (int i = 0; i < cList.size(); i++) {
	                    cModel = new TblOfficialsModel();
	                    TblOfficials tempBasic = cList.get(i);
	                    //将tempBasic赋值给cModel
	                    BeanUtils.copyProperties(tempBasic, cModel);
	                    if(!TeeUtility.isNullorEmpty(tempBasic.getBirth())){
	        				Calendar cal = Calendar.getInstance();
	        				Calendar bir = Calendar.getInstance();
	        				bir.setTime(tempBasic.getBirth());
	        				int yearNow = cal.get(Calendar.YEAR);
	        				/*取出出生年月日*/
	        				int yearBirth = bir.get(Calendar.YEAR);
	        				/*大概年龄是当前年减去出生年*/
	        				int age = yearNow - yearBirth + 1;
	        				cModel.setBirthStr(Integer.toString(age));
	        			}
	        			
	        			//获取所属主体
	        			List<Map> subName = OfficialsService.listByBus(0, 10, tempBasic.getId());
	        			for (Map map : subName) {
	        				String a =(String) subName.get(0).get("SUB_NAME");
	        				cModel.setBusinessSubName(a);
	        			}
	                    cModels.add(cModel);
	                }
	            }
	            
	            json.setRows(cModels);
	            json.setTotal(count);
	        } catch (Exception e) {
	            System.out.println("TblOfficialsController.findSearchListBypageQuery() " + e.getMessage());
	        }
	        return json;
	    }
	
	/**
	 * 分配账号
	 * @param officialsModel
	 * @return
	 * @date:2019年3月9日下午4:25:38
	 * @author:yxy
	 */
	@ResponseBody
	@RequestMapping("saveUser")
	public TeeJson saveUser(TblOfficialsModel officialsModel,HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
	        OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
			json = OfficialsService.saveUser(officialsModel,orgCtrl);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("tblOfficialsController.saveUser"+e.getMessage());
		}
		return json;
	}
	
	/**
	 * 密码重置
	 * @param officialsModel
	 * @param request
	 * @return
	 * @date:2019年3月12日下午3:22:11
	 * @author:yxy
	 */
	@ResponseBody
	@RequestMapping("resetPassword")
	public TeeJson resetPassword(String id,HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
			json = OfficialsService.resetPassword(id);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("tblOfficialsController.resetPassword"+e.getMessage());
		}
		return json;
	}
	
	/**
	 * 回收账号
	 * @param id
	 * @param request
	 * @return
	 * @date:2019年3月12日下午4:10:11
	 * @author:yxy
	 */
	@ResponseBody
	@RequestMapping("resetUser")
	public TeeJson resetUser(String id,HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
			json = OfficialsService.resetUser(id);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("tblOfficialsController.resetUser"+e.getMessage());
		}
		return json;
	}
}
