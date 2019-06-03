package com.beidasoft.zfjd.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.system.bean.StatisticDept;
import com.beidasoft.zfjd.system.model.StatisticDeptModel;
import com.beidasoft.zfjd.system.service.StatisticDeptService;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * 部门信息数量统计表CONTROLLER类
 */
@Controller
@RequestMapping("statisticDeptCtrl")
public class StatisticDeptController {

    @Autowired
    private StatisticDeptService statisticDeptService;

    /**
     * 保存部门信息数量统计表数据
     *
     * @param model
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/saveDocInfo")
    public TeeJson saveDocInfo(StatisticDeptModel model, HttpServletRequest request) {

        TeeJson json = new TeeJson();
//        TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        StatisticDept beanInfo = new StatisticDept();

        //model-->实体类传值
        BeanUtils.copyProperties(model, beanInfo);







        json.setRtData(beanInfo);
        json.setRtState(true);
        return json;
    }
    /**
     * 获取部门信息数量统计表数据
     *
     * @param id
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/getDocInfo")
    public TeeJson getDocInfo(String id, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        StatisticDeptModel model = new StatisticDeptModel();

        StatisticDept beanInfo = statisticDeptService.getById(id);
        //实体类-->model传值
        BeanUtils.copyProperties(beanInfo, model);







        json.setRtData(model);
        json.setRtState(true);
        return json;
    }
}
