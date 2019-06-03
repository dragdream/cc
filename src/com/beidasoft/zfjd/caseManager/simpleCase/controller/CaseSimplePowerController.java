package com.beidasoft.zfjd.caseManager.simpleCase.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.caseManager.simpleCase.service.CaseSimplePowerService;
import com.beidasoft.zfjd.power.model.PowerModel;
import com.beidasoft.zfjd.power.model.PowerSubjectModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

@Controller
@RequestMapping("caseSimplePowerCtrl")
public class CaseSimplePowerController {
    @Autowired
    private CaseSimplePowerService powerService;
    
    /**
     * 分页查询
     * @param tModel            
     * @param cbModel
     * @param request
     * @return
     */    
    @ResponseBody
    @RequestMapping("/findListBypage.action")
    public TeeEasyuiDataGridJson findListBypage(TeeDataGridModel tModel, PowerModel powerModel, HttpServletRequest request) {
        TeeEasyuiDataGridJson json = powerService.findListBypage(tModel, powerModel, request);
        return json;
    }
    
    /**
     * 根据主体查询执法人员
     * @param tModel
     * @param cbModel
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/findListByZhuTi.action")
    public TeeEasyuiDataGridJson findListByZhuTi(TeeDataGridModel tModel, PowerSubjectModel powerSubjectModel, HttpServletRequest request){
        TeeEasyuiDataGridJson json = powerService.findListByZhuTi(tModel, powerSubjectModel, request);
        return json;
    }
}
