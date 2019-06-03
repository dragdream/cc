package com.beidasoft.zfjd.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.system.bean.DepartmentUser;
import com.beidasoft.zfjd.system.model.DepartmentUserModel;
import com.beidasoft.zfjd.system.service.DepartmentUserService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * 部门主体和登录账号关系表CONTROLLER类
 */
@Controller
@RequestMapping("departmentUserCtrl")
public class DepartmentUserController {

    @Autowired
    private DepartmentUserService departmentUserService;

    /**
     * 保存部门主体和登录账号关系表数据
     *
     * @param model
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/saveDocInfo")
    public TeeJson saveDocInfo(DepartmentUserModel model, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        DepartmentUser beanInfo = new DepartmentUser();

        //model-->实体类传值
        BeanUtils.copyProperties(model, beanInfo);







        json.setRtData(beanInfo);
        json.setRtState(true);
        return json;
    }
    /**
     * 获取部门主体和登录账号关系表数据
     *
     * @param id
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/getDocInfo")
    public TeeJson getDocInfo(String id, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        DepartmentUserModel model = new DepartmentUserModel();

        DepartmentUser beanInfo = departmentUserService.getById(id);
        //实体类-->model传值
        BeanUtils.copyProperties(beanInfo, model);







        json.setRtData(model);
        json.setRtState(true);
        return json;
    }
}
