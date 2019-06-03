package com.beidasoft.zfjd.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.system.bean.PersonUser;
import com.beidasoft.zfjd.system.model.PersonUserModel;
import com.beidasoft.zfjd.system.service.PersonUserService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * 人员和登录账号关系表CONTROLLER类
 */
@Controller
@RequestMapping("personUserCtrl")
public class PersonUserController {

    @Autowired
    private PersonUserService personUserService;

    /**
     * 保存人员和登录账号关系表数据
     *
     * @param model
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/saveDocInfo")
    public TeeJson saveDocInfo(PersonUserModel model, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        PersonUser beanInfo = new PersonUser();

        //model-->实体类传值
        BeanUtils.copyProperties(model, beanInfo);
        json.setRtData(beanInfo);
        json.setRtState(true);
        return json;
    }
}
