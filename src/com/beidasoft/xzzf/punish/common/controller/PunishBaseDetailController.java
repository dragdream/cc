package com.beidasoft.xzzf.punish.common.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.beidasoft.xzzf.punish.common.bean.PunishBaseDetail;
import com.beidasoft.xzzf.punish.common.model.PunishBaseDetailModel;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDetailService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * 案件办理标识表子表CONTROLLER类
 */
@Controller
@RequestMapping("punishBaseDetailCtrl")
public class PunishBaseDetailController {

    @Autowired
    private PunishBaseDetailService punishBaseDetailService;

    /**
     * 保存案件办理标识表子表数据
     *
     * @param model
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/saveDocInfo")
    public TeeJson saveDocInfo(PunishBaseDetailModel model, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        PunishBaseDetail beanInfo = new PunishBaseDetail();

        //model-->实体类传值
        BeanUtils.copyProperties(model, beanInfo);

        json.setRtData(beanInfo);
        json.setRtState(true);
        return json;
    }
    /**
     * 获取案件办理标识表子表数据
     *
     * @param id
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/getDocInfo")
    public TeeJson getDocInfo(String id, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        PunishBaseDetailModel model = new PunishBaseDetailModel();

        PunishBaseDetail beanInfo = punishBaseDetailService.getById(id);
        //实体类-->model传值
        BeanUtils.copyProperties(beanInfo, model);

        json.setRtData(model);
        json.setRtState(true);
        return json;
    }
}
