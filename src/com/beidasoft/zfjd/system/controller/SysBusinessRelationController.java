package com.beidasoft.zfjd.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.system.bean.SysBusinessRelation;
import com.beidasoft.zfjd.system.model.SysBusinessRelationModel;
import com.beidasoft.zfjd.system.service.SysBusinessRelationService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * 监督业务管理表CONTROLLER类
 */
@Controller
@RequestMapping("sysBussinessRelationCtrl")
public class SysBusinessRelationController {

    @Autowired
    private SysBusinessRelationService sysBusinessRelationService;

    /**
     * 保存系统部门表数据
     *
     * @param model
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/saveDocInfo")
    public TeeJson saveDocInfo(SysBusinessRelationModel model, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        SysBusinessRelation beanInfo = new SysBusinessRelation();

        //model-->实体类传值
        BeanUtils.copyProperties(model, beanInfo);
        json.setRtData(beanInfo);
        json.setRtState(true);
        return json;
    }
    /**
     * 获取系统部门表数据
     *
     * @param id
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/getDocInfo")
    public TeeJson getDocInfo(String id, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        SysBusinessRelationModel model = new SysBusinessRelationModel();

        SysBusinessRelation beanInfo = sysBusinessRelationService.getById(id);
        //实体类-->model传值
        BeanUtils.copyProperties(beanInfo, model);

        json.setRtData(model);
        json.setRtState(true);
        return json;
    }
}
