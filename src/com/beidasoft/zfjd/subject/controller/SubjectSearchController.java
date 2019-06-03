package com.beidasoft.zfjd.subject.controller;

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
import com.beidasoft.zfjd.subject.bean.Subject;
import com.beidasoft.zfjd.subject.model.SubjectModel;
import com.beidasoft.zfjd.subject.service.SubjectService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * @ClassName: SubjectSearchController.java
 * @Description: 执法主体查询CONTROLLER层
 *
 * @author: mixue
 * @date: 2019年2月19日 下午7:38:26
 */
@Controller
@RequestMapping("subjectSearchController")
public class SubjectSearchController {

    @Autowired
    private SubjectService subjectService;
    
    @Autowired
    private CommonService commonService;
    
    /**
     * 
     * @Function: findListByPageOrg
     * @Description: 委托组织分页查询
     *
     * @param dataGridModel 分页条件
     * @param subjectModel 查询条件
     * @param request
     * @return
     *
     * @author: mixue
     * @date: 2019年2月20日 下午2:38:06
     */
    @ResponseBody
    @RequestMapping("findListByPageOrg.action")
    public TeeEasyuiDataGridJson findListByPageOrg(TeeDataGridModel dataGridModel, SubjectModel subjectModel, HttpServletRequest request) {
        // 返回前台json 
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        List<SubjectModel> subjectModels = new ArrayList<>();
        Long count = null;
        try {
            subjectModels = subjectService.findListByPageOrg(dataGridModel, subjectModel);
            count = subjectService.findListCountByPageOrg(subjectModel);
            json.setRows(subjectModels);
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
     * @Description: 获取单个执法主体信息
     *
     * @param id
     * @return
     *
     * @author: mixue
     * @date: 2019年2月19日 下午7:51:00
     */
    @ResponseBody
    @RequestMapping("getSubjectById.action")
    public TeeJson getDepartmentById(String id) {
        TeeJson json = new TeeJson();
        try {
            SubjectModel subjectModel = subjectService.getSubjectById(id);
            json.setRtData(subjectModel);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }
    
    @ResponseBody
    @RequestMapping("findListByPageRoles.action")
    public TeeEasyuiDataGridJson findListByPageRoles(TeeDataGridModel dataGridModel, SubjectModel subjectModel, HttpServletRequest request) {
        // 返回前台json 
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        List<SubjectModel> subjectModels = new ArrayList<>();
        Long count = null;
        try {
            OrgCtrlInfoModel orgCtrlInfoModel = commonService.getOrgCtrlInfo(request);
            subjectModels = subjectService.findListByPageRoles(dataGridModel, subjectModel, orgCtrlInfoModel);
            count = subjectService.findListCountByPageRoles(subjectModel, orgCtrlInfoModel);
			
            json.setRows(subjectModels);
            json.setTotal(count);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }
    
    /**
     * 执法主体综合查询  分页 权限控制
     * @param tModel
     * @param cbModel
     * @param request
     * @return
     * @date:2019年4月2日下午1:53:41
     * @author:yxy
     */
    @SuppressWarnings({ "unused", "rawtypes" })
	@ResponseBody
    @RequestMapping("generalListByPageRoles.action")
    public TeeEasyuiDataGridJson generalListByPageRoles(TeeDataGridModel tModel, SubjectModel cbModel, HttpServletRequest request) {
    		//返回前台json 
	        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson(); 
	        // 接收查询集合 cList
	        List<Subject> cList = null; 
	        List<Map> cLists = null;
	        //bean对应的model cModels
	        List<SubjectModel> cModels = new ArrayList<SubjectModel>(); 
	        //查询集合总数
	        Long count = null;
	        try {
	            OrgCtrlInfoModel orgCtrl = commonService.getOrgCtrlInfo(request);
	            //获取数据
	            cList = subjectService.generalListByPageSubSearch(tModel, cbModel, orgCtrl);
	            //统计个数
	            count = subjectService.generallistSearchSubCount(cbModel, orgCtrl);
	            //定义model
	            SubjectModel cModel = null;
	            if (cList != null && cList.size() > 0) {
	                for (int i = 0; i < cList.size(); i++) {
	                    cModel = new SubjectModel();
	                    Subject tempBasic = cList.get(i);
	                    //将tempBasic赋值给cModel
	                    BeanUtils.copyProperties(tempBasic, cModel);
//	                    //获取部门信息数量
//	    	            cNumber = statisticDeptService.getByDeptId(cList.get(i).getId());
	                    //部门层级
	                    cModel.setSubLevel(TeeSysCodeManager.getChildSysCodeNameCodeNo("DEPT_LEVEL", cModel.getSubLevel()));
	                    //部门性质
	                    cModel.setNature(TeeSysCodeManager.getChildSysCodeNameCodeNo("SYSTEM_SUBJECT_NATURE", cModel.getNature()));
	                    //所属地区
	                    cModel.setArea(TeeSysCodeManager.getChildSysCodeNameCodeNo("ADMINISTRAIVE_DIVISION", cModel.getArea()));
	                    //所属领域
	                    cModel.setOrgSys(TeeSysCodeManager.getChildSysCodeNameCodeNo("LAW_ENFORCEMENT_FIELD", cModel.getOrgSys()));
	                    //职权类型
//	                    if (tempBasic.getSubjectPower() != null) {
//	                        List<Map<String, Object>> subPowerCode = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_TYPE");
//	                        cModel.setSubjectPower("");
//	                        String[] orgs = tempBasic.getOrgSys().split(",");
//	                        StringBuilder orgSys = new StringBuilder("");
//	                        for (Map<String, Object> code : orgSysCode) {
//	                            for (int j = 0; j < orgs.length; j++) {
//	                                if (orgs[j].equals(code.get("codeNo").toString())) {
//	                                    orgSys.append(code.get("codeName").toString() + ",");
//	                                    break;
//	                                }
//	                            }
//	                        }
//	                        if (orgSys.length() > 0) {
//	                        	cModel.setOrgSys(orgSys.toString().substring(0, orgSys.length() - 1));
//	                        }
//	                    }
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
    
    @ResponseBody
    @RequestMapping("getSubjectRoles.action")
    public TeeJson getSubjectRoles(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        List<SubjectModel> subjectModels = new ArrayList<>();
        try {
            OrgCtrlInfoModel orgCtrlInfoModel = commonService.getOrgCtrlInfo(request);
            subjectModels = subjectService.getSubjectRoles(orgCtrlInfoModel);
            json.setRtData(subjectModels);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            json.setRtState(false);
            e.printStackTrace();
        }
        return json;
    }
}
