package com.beidasoft.zfjd.department.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.department.bean.OrganizationSubject;
import com.beidasoft.zfjd.department.model.OrganizationSubjectModel;
import com.beidasoft.zfjd.department.service.OrganizationSubjectService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * 部门主体关联表表CONTROLLER类
 */
@Controller
@RequestMapping("organizationSubjectCtrl")
public class OrganizationSubjectController {

    @Autowired
    private OrganizationSubjectService organizationSubjectService;

    /**
     * 部门主体关联表数据
     *
     * @param model
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/saveDocInfo")
    public TeeJson saveDocInfo(OrganizationSubjectModel model, HttpServletRequest request) {
        TeeJson json = new TeeJson();
        try {
        	TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
            OrganizationSubject beanInfo = new OrganizationSubject();
            //model-->实体类传值
            BeanUtils.copyProperties(model, beanInfo);
            json.setRtData(beanInfo);
            json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
		}
        return json;
    }
    /**
     * 部门主体关联表数据
     *
     * @param id
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/getDocInfo")
    public TeeJson getDocInfo(String id, HttpServletRequest request) {
        TeeJson json = new TeeJson();
        try {
        	OrganizationSubjectModel model = new OrganizationSubjectModel();
            OrganizationSubject beanInfo = organizationSubjectService.getById(id);
            //实体类-->model传值
            BeanUtils.copyProperties(beanInfo, model);
            json.setRtData(model);
            json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
            json.setRtState(false);
		}
        return json;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
    @RequestMapping("listByPage")
    public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,OrganizationSubjectModel queryModel){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		try {
			long total = organizationSubjectService.getTotal(queryModel);
			List<OrganizationSubjectModel> modelList = new ArrayList();
			List<OrganizationSubject> departmentInfos = organizationSubjectService.listByPage(
					dataGridModel.getFirstResult(), dataGridModel.getRows(),
					queryModel);
			for (OrganizationSubject departmentInfo : departmentInfos) {
				OrganizationSubjectModel infoModel = new OrganizationSubjectModel();
				BeanUtils.copyProperties(departmentInfo,infoModel);
				

				modelList.add(infoModel);
			}
			dataGridJson.setTotal(total);
			dataGridJson.setRows(modelList);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("OrganizationSubjectController.listBypage"+e.getMessage());
		}
		
		return dataGridJson;

	}
}
