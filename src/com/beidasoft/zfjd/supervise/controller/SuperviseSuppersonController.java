package com.beidasoft.zfjd.supervise.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.supervise.bean.SuperviseSupperson;
import com.beidasoft.zfjd.supervise.model.SuperviseSuppersonModel;
import com.beidasoft.zfjd.supervise.service.SuperviseSuppersonService;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * 监督部门人员关系表CONTROLLER类
 */
@Controller
@RequestMapping("superviseSuppersonCtrl")
public class SuperviseSuppersonController {

    @Autowired
    private SuperviseSuppersonService superviseSuppersonService;

    /**
     * 保存监督部门人员关系表数据
     *
     * @param model
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/save")
    public TeeJson save(SuperviseSuppersonModel model, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        SuperviseSupperson beanInfo = new SuperviseSupperson();

        //model-->实体类传值
        BeanUtils.copyProperties(model, beanInfo);

        json.setRtData(beanInfo);
        json.setRtState(true);
        return json;
    }
    /**
     * 获取监督部门人员关系表数据
     *
     * @param id
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/get")
    public TeeJson get(String id, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        SuperviseSuppersonModel model = new SuperviseSuppersonModel();

        SuperviseSupperson beanInfo = superviseSuppersonService.getById(id);
        //实体类-->model传值
        BeanUtils.copyProperties(beanInfo, model);

        json.setRtData(model);
        json.setRtState(true);
        return json;
    }
}
