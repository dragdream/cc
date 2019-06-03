package com.beidasoft.zfjd.caseManager.simpleCase.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.caseManager.simpleCase.service.CaseSimpleStaffService;
import com.beidasoft.zfjd.officials.model.TblOfficialsModel;
import com.beidasoft.zfjd.subjectPerson.model.TblSubjectPersonModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

@Controller
@RequestMapping("caseSimpleStaffCtrl")
public class CaseSimpleStaffController {
    @Autowired
    private CaseSimpleStaffService caseSimpleStaffService;

    /**
     ** 分页查询
     * 
     * @param tModel
     * @param cbModel
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/findListBypage.action")
    public TeeEasyuiDataGridJson findListBypage(TeeDataGridModel tModel, TblOfficialsModel cbModel,
            HttpServletRequest request) {
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        if (cbModel.getIds() != null && !"".equals(cbModel.getIds())) {
            cbModel.setIds("'" + cbModel.getIds().replace(",", "','") + "'");
        } else {
            return json;
        }
        json = caseSimpleStaffService.findListBypage(tModel, cbModel, request);
        return json;
    }

    /**
     ** 根据主体查询执法人员
     * 
     * @param tModel
     * @param cbModel
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/findListByZhuTi.action")
    public TeeEasyuiDataGridJson findListByZhuTi(TeeDataGridModel tModel, TblSubjectPersonModel subPersonModel,
            HttpServletRequest request) {
        TeeEasyuiDataGridJson json = caseSimpleStaffService.findListByZhuTi(tModel, subPersonModel, request);
        return json;
    }
}
