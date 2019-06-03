package com.beidasoft.zfjd.subject.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.subject.bean.SubjectSubpower;
import com.beidasoft.zfjd.subject.model.SubjectSubpowerModel;
import com.beidasoft.zfjd.subject.service.SubjectSubpowerService;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * 主体职权类别关系表CONTROLLER类
 */
@Controller
@RequestMapping("subjectSubpowerCtrl")
public class SubjectSubpowerController {

    @Autowired
    private SubjectSubpowerService subjectSubpowerService;

    /**
     * 保存主体职权类别关系表数据
     *
     * @param model
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/saveDocInfo")
    public TeeJson saveDocInfo(SubjectSubpowerModel model, HttpServletRequest request) {

        TeeJson json = new TeeJson();
//        TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        SubjectSubpower beanInfo = new SubjectSubpower();

        //model-->实体类传值
        BeanUtils.copyProperties(model, beanInfo);







        json.setRtData(beanInfo);
        json.setRtState(true);
        return json;
    }
    /**
     * 获取主体职权类别关系表数据
     *
     * @param id
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/getDocInfo")
    public TeeJson getDocInfo(String id, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        SubjectSubpowerModel model = new SubjectSubpowerModel();

        SubjectSubpower beanInfo = subjectSubpowerService.getById(id);
        //实体类-->model传值
        BeanUtils.copyProperties(beanInfo, model);







        json.setRtData(model);
        json.setRtState(true);
        return json;
    }
}
