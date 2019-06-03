package com.beidasoft.zfjd.department.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.common.service.CommonService;
import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.department.model.TblDepartmentModel;
import com.beidasoft.zfjd.department.service.TblDepartmentService;
import com.beidasoft.zfjd.system.bean.StatisticDept;
import com.beidasoft.zfjd.system.service.StatisticDeptService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeUtility;


/**
 * @ClassName: DepartmentSearchController.java
 * @Description: 执法部门查询
 *
 * @author: mixue
 * @date: 2019年2月19日 上午9:22:25
 */
@Controller
@RequestMapping("departmentSearchController")
public class DepartmentSearchController {

    @Autowired
    private TblDepartmentService departmentService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private StatisticDeptService statisticDeptService;
    
    public TblDepartmentModel pubModel;
    
    /**
     * 
     * @Function: findListByPage
     * @Description: 执法部门列表查询
     *
     * @param dataGridModel
     * @param queryModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月19日 下午6:05:08
     */
    @ResponseBody
    @RequestMapping("findListByPage.action")
    public TeeEasyuiDataGridJson findListByPage(TeeDataGridModel dataGridModel, TblDepartmentModel departmentModel, HttpServletRequest request) {
        // 返回前台json 
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        List<TblDepartmentModel> departmentModels = new ArrayList<>();
        Long count = null;
        try {
            departmentModels = departmentService.findListByPage(dataGridModel, departmentModel);
            count = departmentService.findListCountByPage(departmentModel);
            json.setRows(departmentModels);
            json.setTotal(count);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }
    
    /**
     * 
     * @Function: findListByPageRoles
     * @Description: 执法部门分页查询（带数据权限）
     *
     * @param dataGridModel
     * @param departmentModel
     * @param request
     * @return
     *
     * @author: mixue
     * @date: 2019年2月28日 下午8:57:45
     */
    @ResponseBody
    @RequestMapping("findListByPageRoles.action")
    public TeeEasyuiDataGridJson findListByPageRoles(TeeDataGridModel dataGridModel, TblDepartmentModel departmentModel, HttpServletRequest request) {
        // 返回前台json 
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        List<TblDepartmentModel> departmentModels = new ArrayList<>();
        Long count = null;
        try {
            OrgCtrlInfoModel orgCtrlInfoModel = commonService.getOrgCtrlInfo(request);
            departmentModels = departmentService.findListByPageRoles(dataGridModel, departmentModel, orgCtrlInfoModel);
            count = departmentService.findListCountByPageRoles(departmentModel, orgCtrlInfoModel);
            json.setRows(departmentModels);
            json.setTotal(count);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 
     * @Function: getDepartmentById
     * @Description: 获取单个执法部门信息
     *
     * @param id 部门ID
     * @return
     *
     * @author: mixue
     * @date: 2019年2月19日 下午3:00:32
     */
    @ResponseBody
    @RequestMapping("getDepartmentById.action")
    public TeeJson getDepartmentById(String id) {
        TeeJson json = new TeeJson();
        try {
            TblDepartmentModel departmentModel = departmentService.getDepartmentById(id);
            json.setRtData(departmentModel);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }
    
    /**
     * 
     * @Function: getDepartmentRoles
     * @Description: 分权限获取本级及下级/本单位及下级单位执法部门
     *
     * @param request
     * @return
     *
     * @author: mixue
     * @date: 2019年3月2日 下午3:18:45
     */
    @ResponseBody
    @RequestMapping("getDepartmentRoles.action")
    public TeeJson getDepartmentRoles(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        List<TblDepartmentModel> departmentModels = new ArrayList<>();
        try {
            OrgCtrlInfoModel orgCtrlInfoModel = commonService.getOrgCtrlInfo(request);
            departmentModels = departmentService.getDepartmentRoles(orgCtrlInfoModel);
            json.setRtData(departmentModels);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            json.setRtState(false);
            e.printStackTrace();
        }
        return json;
    }
    
    /**
     * 执法机关综合查询 分页 权限控制
     * @param dataGridModel
     * @param departmentModel
     * @param request
     * @return
     * @date:2019年3月22日下午3:54:01
     * @author:yxy
     */
    @SuppressWarnings({ "unused", "rawtypes" })
	@ResponseBody
    @RequestMapping("generalListByPageRoles.action")
    public TeeEasyuiDataGridJson generalListByPageRoles(TeeDataGridModel tModel, TblDepartmentModel cbModel, HttpServletRequest request) {
    		//返回前台json 
	        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson(); 
	        // 接收查询集合 cList
            pubModel = cbModel;
            List<StatisticDept> cNumber = null;
	        List<TblDepartmentInfo> cList = null; 
	        List<Map> cLists = null;
	        //bean对应的model cModels
	        List<TblDepartmentModel> cModels = new ArrayList<TblDepartmentModel>(); 
	        //查询集合总数
	        Long count = null;
	        try {
	            OrgCtrlInfoModel orgCtrl = commonService.getOrgCtrlInfo(request);
	            //获取数据
	            cList = departmentService.generalListByPageSearch(tModel, cbModel, orgCtrl);
//	            cList = departmentService.generalListByPage(cbModel, orgCtrl);
	            //统计个数
	            count = departmentService.generallistSearchCount(cbModel, orgCtrl);
	            //定义model
	            TblDepartmentModel cModel = null;
	            if (cList != null && cList.size() > 0) {
	                for (int i = 0; i < cList.size(); i++) {
	                    cModel = new TblDepartmentModel();
	                    TblDepartmentInfo tempBasic = cList.get(i);
	                    //将tempBasic赋值给cModel
	                    BeanUtils.copyProperties(tempBasic, cModel);
	                    //获取部门信息数量
	    	            cNumber = statisticDeptService.getByDeptId(cList.get(i).getId());
	                    //部门层级
	                    cModel.setDeptLevel(TeeSysCodeManager.getChildSysCodeNameCodeNo("DEPT_LEVEL", cModel.getDeptLevel()));
	                    //部门性质
	                    cModel.setNature(TeeSysCodeManager.getChildSysCodeNameCodeNo("DEPT_NATURE", cModel.getNature()));
	                    //部门地区
	                    cModel.setAdministrativeDivision(TeeSysCodeManager.getChildSysCodeNameCodeNo("ADMINISTRAIVE_DIVISION", cModel.getAdministrativeDivision()));
	                    //数量统计
	                    if(!TeeUtility.isNullorEmpty(cNumber)){
	                    	for (int j = 0; j < cNumber.size(); j++) {
	                    		cModel.setPersonNo(cNumber.get(j).getPersonNo());
			                    cModel.setOrgNo(cNumber.get(j).getOrgNo());
			                    cModel.setSubNo(cNumber.get(j).getSubNo());
			                    cModel.setPowerNo(cNumber.get(j).getPowerNo());
							}
	                    }
	                    //所属领域
	                    if (tempBasic.getOrgSys() != null) {
	                        List<Map<String, Object>> orgSysCode = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("LAW_ENFORCEMENT_FIELD");
	                        cModel.setOrgSys("");
	                        String[] orgs = tempBasic.getOrgSys().split(",");
	                        StringBuilder orgSys = new StringBuilder("");
	                        for (Map<String, Object> code : orgSysCode) {
	                            for (int j = 0; j < orgs.length; j++) {
	                                if (orgs[j].equals(code.get("codeNo").toString())) {
	                                    orgSys.append(code.get("codeName").toString() + ",");
	                                    break;
	                                }
	                            }
	                        }
	                        if (orgSys.length() > 0) {
	                        	cModel.setOrgSys(orgSys.toString().substring(0, orgSys.length() - 1));
	                        }
	                    }
	                    cModels.add(cModel);
	                }
	            }
	            json.setRows(cModels);
	            json.setTotal(count);
	        } catch (Exception e) {
	            System.out.println("DepartmentSearchController.generalListByPageRoles() " + e.getMessage());
	        }
	        return json;
	    }
	    
    /**
     * 导出Excel
     * @param shows
     * @param request
     * @return
     * 限制条数 1000 
     * @date:2019年3月29日上午11:07:42
     * @author:yxy
     */
	@ResponseBody
    @RequestMapping("exportDept.action")
    public TeeJson exportDept (String isTrue, String term,HttpServletRequest request,HttpServletResponse response){
    	TeeJson json = new TeeJson();
    	TeeDataGridModel tModel = new TeeDataGridModel();
    	tModel.setRows(9999);
		tModel.setPage(1);
    	List<TblDepartmentInfo> cList = null; 
        Long count = null;
        OrgCtrlInfoModel orgCtrl = commonService.getOrgCtrlInfo(request);
    	try {
    		if(TeeUtility.isNullorEmpty(isTrue)){
                count = departmentService.generallistSearchCount(pubModel, orgCtrl);
                if(count > 1000L){
                	json.setRtData(count);
                	json.setRtState(false);
                	return json;
                }
                json.setRtData(count);
                json.setRtState(true);
    		}else{
                cList = departmentService.generalListByPageSearch(tModel, pubModel, orgCtrl);
                departmentService.exportExcel(term,cList,request, response);
    		}
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("DepartmentSearchController.exportDept"+e.getMessage());
		}
    	return json;
    }
	
}
