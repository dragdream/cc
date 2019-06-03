package com.beidasoft.zfjd.department.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.department.bean.OrganizationOrgsys;
import com.beidasoft.zfjd.department.model.OrganizationOrgsysModel;
import com.beidasoft.zfjd.department.service.OrganizationOrgsysService;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * 部门和所属领域关系表CONTROLLER类
 */
@Controller
@RequestMapping("organizationOrgsysCtrl")
public class OrganizationOrgsysController {

    @Autowired
    private OrganizationOrgsysService organizationOrgsysService;

    /**
     * 保存部门和所属领域关系表数据
     *
     * @param model
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/saveDocInfo")
    public TeeJson saveDocInfo(OrganizationOrgsysModel model, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        OrganizationOrgsys beanInfo = new OrganizationOrgsys();

        //model-->实体类传值
        BeanUtils.copyProperties(model, beanInfo);







        json.setRtData(beanInfo);
        json.setRtState(true);
        return json;
    }
    /**
     * 获取部门和所属领域关系表数据
     *
     * @param id
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/getDocInfo")
    public TeeJson getDocInfo(String id, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        OrganizationOrgsysModel model = new OrganizationOrgsysModel();

        OrganizationOrgsys beanInfo = organizationOrgsysService.getById(id);
        //实体类-->model传值
        BeanUtils.copyProperties(beanInfo, model);







        json.setRtData(model);
        json.setRtState(true);
        return json;
    }
}
