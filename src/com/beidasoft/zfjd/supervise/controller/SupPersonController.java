package com.beidasoft.zfjd.supervise.controller;

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
import com.beidasoft.zfjd.supervise.bean.SupPerson;
import com.beidasoft.zfjd.supervise.model.SupPersonModel;
import com.beidasoft.zfjd.supervise.service.SupPersonService;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.general.service.TeeSysCodeService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

/**
 * 监督人员表CONTROLLER类
 */
@Controller
@RequestMapping("SupPersonController")
public class SupPersonController {

    @Autowired
    private SupPersonService supPersonService;
    @Autowired
	private TeeAttachmentService attachmentService;
    @Autowired
	private TblDepartmentService departmentService;
    @Autowired
   	private TeeSysCodeService sysCodeService;
    @Autowired
    private CommonService commonservivse;
    
    
    /**
	 * 保存
	 * @param lawModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("save")
	public TeeJson save(SupPersonModel supPersonModel){
		TeeJson json = new TeeJson();
		try {
			json = supPersonService.save(supPersonModel);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SupPersonController.save"+e.getMessage());
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
	public TeeJson update(SupPersonModel supPersonModel){
		TeeJson json = new TeeJson();
		try {
			json = supPersonService.updateUser(supPersonModel);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SupPersonController.update"+e.getMessage());
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
			SupPerson supPerson = new SupPerson();
			supPerson = supPersonService.getById(id);
			
			if(!TeeUtility.isNullorEmpty(supPerson.getExamine())){
				if(supPerson.getExamine()==1){
					supPerson.setExamine(0);
					supPerson.setDisExamineTime(new Date());
				}else{
					supPerson.setExamine(1);
					supPerson.setExamineTime(new Date());
					supPerson.setExamineId(user.getUserId());
				}
			}
			supPersonService.update(supPerson);	
			
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SupPersonController.examine"+e.getMessage());
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
			SupPerson supPerson = new SupPerson();
			supPerson = supPersonService.getById(id);
			
			supPerson.setIsDelete(1);
			supPerson.setDeleteTime(new Date());
			supPersonService.update(supPerson);	
			
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SupPersonController.delete"+e.getMessage());
		}
		return json;
	}
	/**
	 * 获取数据
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@ResponseBody
	@RequestMapping("get")
	public TeeJson get(String id){
			TeeJson json = new TeeJson();
			try {
				SupPerson supPerson = supPersonService.getById(id);
				
				SupPersonModel supPersonModel = new SupPersonModel();
				BeanUtils.copyProperties(supPerson, supPersonModel);
				
				//单独处理日期
				supPersonModel.setBirthStr(TeeDateUtil.format(supPerson.getBirth(), "yyyy-MM-dd"));

				//获取所属监督部门
				List<Map> deptName = supPersonService.listByBus(0, 10, supPerson.getId());
				for (Map map : deptName) {
					String a =(String) deptName.get(0).get("NAME");
					String b =(String) deptName.get(0).get("ID");
					
					supPersonModel.setDepartmentName(a);
					supPersonModel.setDepartmentId(b);
				}
				
				json.setRtData(supPersonModel);
				json.setRtState(true);
			} catch (Exception e) {
				// TODO: handle exception
				json.setRtState(false);
				System.out.println("SupPersonController.get"+e.getMessage());
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
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@ResponseBody
    @RequestMapping("listByPage")
    public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,SupPersonModel querModel){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();

		long total = supPersonService.getTotal(querModel);
		List<SupPersonModel> modelList = new ArrayList();
		List<SupPerson> supPersons = supPersonService.listByPage(
				dataGridModel.getFirstResult(), dataGridModel.getRows(),
				querModel);
		for (SupPerson supPerson : supPersons) {
			SupPersonModel supPersonModel = new SupPersonModel();
			BeanUtils.copyProperties(supPerson,supPersonModel);
			//获取所属部门
			List<Map> deptName = supPersonService.listByBus(0, 10, supPerson.getId());
			for (Map map : deptName) {
				String a =(String) deptName.get(0).get("NAME");
				supPersonModel.setDepartmentName(a);
			}
			//生日转年龄
			if(!TeeUtility.isNullorEmpty(supPerson.getBirth())){
				Calendar cal = Calendar.getInstance();
				Calendar bir = Calendar.getInstance();
				bir.setTime(supPerson.getBirth());
				int yearNow = cal.get(Calendar.YEAR);
				int yearBirth = bir.get(Calendar.YEAR);
				int age = yearNow - yearBirth + 1;
				supPersonModel.setBirthStr(Integer.toString(age));
				
			}
			modelList.add(supPersonModel);

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
            SupPersonModel supPersonModel = supPersonService.getPersonById(id);
            json.setRtData(supPersonModel);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
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
    	supPersonService.deletes(id);
    	json.setRtState(true);
		return null;
	}
	/**
	 * 监督人员管理 权限控制
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@ResponseBody
    @RequestMapping("findListBypage")
	public TeeEasyuiDataGridJson findListBypage(TeeDataGridModel tModel, SupPersonModel cbModel, HttpServletRequest request) {
			//返回前台json 
	        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson(); 
	        // 接收查询集合 cList
	        List<SupPerson> cList = null; 
	        //bean对应的model cModels
	        List<SupPersonModel> cModels = new ArrayList<SupPersonModel>(); 
	        //查询集合总数
	        Long count = null;
	        try {
	            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
	            cList = supPersonService.findListByPage(tModel, cbModel, orgCtrl);
	            count = supPersonService.listCount(cbModel, orgCtrl);
	            //定义model
	            SupPersonModel cModel = null;
	            if (cList != null && cList.size() > 0) {
	                for (int i = 0; i < cList.size(); i++) {
	                    cModel = new SupPersonModel();
	                    SupPerson tempBasic = cList.get(i);
	                    //将tempBasic赋值给cModel
	                    BeanUtils.copyProperties(tempBasic, cModel);
	                    //职级
	                    cModel.setJobClass(TeeSysCodeManager.getChildSysCodeNameCodeNo("JOB_CLASS", cModel.getJobClass()));
	                    //获取所属部门
	        			List<Map> deptName = supPersonService.listByBus(0, 10, tempBasic.getId());
	        			for (Map map : deptName) {
	        				String a =(String) deptName.get(0).get("NAME");
	        				cModel.setDepartmentName(a);
	        			}
	        			//生日转年龄
	        			if(!TeeUtility.isNullorEmpty(tempBasic.getBirth())){
	        				Calendar cal = Calendar.getInstance();
	        				Calendar bir = Calendar.getInstance();
	        				bir.setTime(tempBasic.getBirth());
	        				int yearNow = cal.get(Calendar.YEAR);
	        				int yearBirth = bir.get(Calendar.YEAR);
	        				int age = yearNow - yearBirth + 1;
	        				cModel.setBirthStr(Integer.toString(age));
	        				
	        			}
	                    cModels.add(cModel);
	                }
	            }
	            
	            json.setRows(cModels);
	            json.setTotal(count);
	        } catch (Exception e) {
	            System.out.println("SupPersonController.findListBypage() " + e.getMessage());
	        }
	        return json;
	    }
	/**
	 * 监督人员查询 权限控制 分页
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@ResponseBody
    @RequestMapping("findSearchListBypage")
	public TeeEasyuiDataGridJson findSearchListBypage(TeeDataGridModel tModel, SupPersonModel cbModel, HttpServletRequest request) {
			//返回前台json 
	        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson(); 
	        // 接收查询集合 cList
	        List<SupPerson> cList = null; 
	        //bean对应的model cModels
	        List<SupPersonModel> cModels = new ArrayList<SupPersonModel>(); 
	        //查询集合总数
	        Long count = null;
	        try {
	            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
	            cList = supPersonService.findSearchListBypage(tModel, cbModel, orgCtrl);
	            count = supPersonService.listSearchCount(cbModel, orgCtrl);
	            //定义model
	            SupPersonModel cModel = null;
	            if (cList != null && cList.size() > 0) {
	                for (int i = 0; i < cList.size(); i++) {
	                    cModel = new SupPersonModel();
	                    SupPerson tempBasic = cList.get(i);
	                    //将tempBasic赋值给cModel
	                    BeanUtils.copyProperties(tempBasic, cModel);
	                    //职级
	                    cModel.setJobClass(TeeSysCodeManager.getChildSysCodeNameCodeNo("JOB_CLASS", cModel.getJobClass()));
	                    //获取所属部门
	        			List<Map> deptName = supPersonService.listByBus(0, 10, tempBasic.getId());
	        			for (Map map : deptName) {
	        				String a =(String) deptName.get(0).get("NAME");
	        				cModel.setDepartmentName(a);
	        			}
	        			//生日转年龄
	        			if(!TeeUtility.isNullorEmpty(tempBasic.getBirth())){
	        				Calendar cal = Calendar.getInstance();
	        				Calendar bir = Calendar.getInstance();
	        				bir.setTime(tempBasic.getBirth());
	        				int yearNow = cal.get(Calendar.YEAR);
	        				int yearBirth = bir.get(Calendar.YEAR);
	        				int age = yearNow - yearBirth + 1;
	        				cModel.setBirthStr(Integer.toString(age));
	        				
	        			}
	                    cModels.add(cModel);
	                }
	            }
	            
	            json.setRows(cModels);
	            json.setTotal(count);
	        } catch (Exception e) {
	            System.out.println("SupPersonController.findListBypage() " + e.getMessage());
	        }
	        return json;
	    }
	
	/**
	 * 分配账号
	 * @param supPersonModel
	 * @param request
	 * @return
	 * @date:2019年3月12日下午5:29:07
	 * @author:yxy
	 */
	@ResponseBody
	@RequestMapping("saveUser")
	public TeeJson saveUser(SupPersonModel supPersonModel,HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
	        OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
			json = supPersonService.saveUser(supPersonModel,orgCtrl);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SuppersonController.saveUser"+e.getMessage());
		}
		return json;
	}
	
	/**
	 * 密码重置
	 * @param id
	 * @param request
	 * @return
	 * @date:2019年3月12日下午5:29:24
	 * @author:yxy
	 */
	@ResponseBody
	@RequestMapping("resetPassword")
	public TeeJson resetPassword(String id,HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
			json = supPersonService.resetPassword(id);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SuppersonController.resetPassword"+e.getMessage());
		}
		return json;
	}
	
	/**
	 * 回收账号
	 * @param id
	 * @param request
	 * @return
	 * @date:2019年3月12日下午5:29:34
	 * @author:yxy
	 */
	@ResponseBody
	@RequestMapping("resetUser")
	public TeeJson resetUser(String id,HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
			json = supPersonService.resetUser(id);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SuppersonController.resetUser"+e.getMessage());
		}
		return json;
	}
}
