package com.beidasoft.zfjd.system.controller;

import java.util.ArrayList;
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
import com.beidasoft.zfjd.system.bean.Department;
import com.beidasoft.zfjd.system.model.DepartmentModel;
import com.beidasoft.zfjd.system.service.DepartmentService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * 系统部门表CONTROLLER类
 */
@Controller
@RequestMapping("departmentCtrl")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CommonService commonservivse;
    
    /**
	 * 保存
	 * @param departmentModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("save")
	public TeeJson save(DepartmentModel departmentModel){
		TeeJson json = new TeeJson();
		try {
			//创建实例化实体类对象
			Department department = new Department();
			BeanUtils.copyProperties(departmentModel, department);
			
			//department.setId(UUID.randomUUID().toString());
			departmentService.save(department);

			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("DepartmentController.save"+e.getMessage());
		}
		return json;
	}

	@ResponseBody
	@RequestMapping("update")
	public TeeJson update(HttpServletRequest request,DepartmentModel departmentModel){
		TeeJson json = new TeeJson();
		try {
			json = departmentService.update(request,departmentModel);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("DepartmentController.update"+e.getMessage());
		}
		return json;
	}
    
	@ResponseBody
	@RequestMapping("delete")
	public TeeJson delete(HttpServletRequest request,DepartmentModel departmentModel){
		TeeJson json = new TeeJson();
		try {
			json = departmentService.delete(request,departmentModel);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("DepartmentController.delete"+e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("get")
	public TeeJson get(String id){
		TeeJson json = new TeeJson();
		try {
			Integer uuid =Integer.parseInt(id);
			Department department = departmentService.getById(uuid);
			
			DepartmentModel departmentModel = new DepartmentModel();
			BeanUtils.copyProperties(department, departmentModel);
			
			json.setRtData(departmentModel);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("DepartmentController.get"+e.getMessage());
		}
		return json;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
    @RequestMapping("listByPage")
    public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,DepartmentModel queryModel,HttpServletRequest request){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		try {
			OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
//			long total = departmentService.getTotal(queryModel);
			long total = departmentService.listSearchCount(queryModel, orgCtrl);
			List<DepartmentModel> modelList = new ArrayList();
			List<Map> departmentInfos = departmentService.listByPage2(
					dataGridModel.getFirstResult(), dataGridModel.getRows(),
					queryModel);
			for (Map map : departmentInfos) {
				DepartmentModel departmentModel = new DepartmentModel();
				departmentModel.setUuid(Integer.parseInt(map.get("UUID").toString()));
				departmentModel.setDeptName(String.valueOf(map.get("DEPTNAME")));
				departmentModel.setBusinessDeptName(String.valueOf(map.get("BUSINESSDEPTNAME")));
				departmentModel.setBusinessSubjectName(String.valueOf(map.get("BUSINESSSUBJECTNAME")));
				departmentModel.setBusinessSupDeptName(String.valueOf(map.get("BUSINESSSUPDEPTNAME")));
				modelList.add(departmentModel);
			}
			dataGridJson.setTotal(total);
			dataGridJson.setRows(modelList);
		} catch (Exception e) {
			// TODO: handle exception
            System.out.println("DepartmentController.listByPage() " + e.getMessage());
		}
        
		return dataGridJson;
	}
	
	
	@ResponseBody
    @RequestMapping("findSearchListBypage")
	public TeeEasyuiDataGridJson findSearchListBypage(TeeDataGridModel tModel, DepartmentModel cbModel, HttpServletRequest request) {
			//返回前台json 
	        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson(); 
	        // 接收查询集合 cList
	        List<Department> cList = null; 
	        //bean对应的model cModels
	        List<DepartmentModel> cModels = new ArrayList<DepartmentModel>(); 
	        //查询集合总数
	        Long count = null;
	        try {
	            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
	            cList = departmentService.findSearchListBypage(tModel, cbModel, orgCtrl);
	            count = departmentService.listSearchCount(cbModel, orgCtrl);
	            //定义model
	            DepartmentModel cModel = null;
	            if (cList != null && cList.size() > 0) {
	                for (int i = 0; i < cList.size(); i++) {
	                    cModel = new DepartmentModel();
	                    Department tempBasic = cList.get(i);
	                    //将tempBasic赋值给cModel
	                    BeanUtils.copyProperties(tempBasic, cModel);
	                    
	                    cModels.add(cModel);
	                }
	            }
	            
	            json.setRows(cModels);
	            json.setTotal(count);
	        } catch (Exception e) {
	            System.out.println("DepartmentController.findListBypage() " + e.getMessage());
	        }
	        return json;
	    }
}
