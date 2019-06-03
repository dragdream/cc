package com.beidasoft.zfjd.caseManager.commonCase.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonDataModel;
import com.beidasoft.zfjd.caseManager.commonCase.service.CaseCommonDataService;
import com.beidasoft.zfjd.common.controller.CommonController;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * @ClassName: CaseCommonDataController.java
 * @Description: 
 *
 * @author: mixue
 * @date: 2019年3月20日 上午11:28:27
 */
@Controller
@RequestMapping("/caseCommonDataCtrl")
public class CaseCommonDataController extends CommonController {

    @Autowired
    private CaseCommonDataService caseCommonDataService;
    
    @ResponseBody
    @RequestMapping("/findListBypage.action")
    public TeeJson findListByPage(CaseCommonDataModel caseCommonDataModel,
            HttpServletRequest request) {
        // 返回前台json
        TeeJson json = new TeeJson();
        List<CaseCommonDataModel> caseCommonDataModels = new ArrayList<>();
        try {
            caseCommonDataModels = caseCommonDataService.findListByPage(caseCommonDataModel);
            json.setRtData(caseCommonDataModels);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            json.setRtState(false);
            e.printStackTrace();
        }
        return json;
    }
    
    @ResponseBody
    @RequestMapping("getCaseCommonDataById.action")
    public TeeJson getCaseCommonDataById(String id) {
        TeeJson json = new TeeJson();
        try {
            CaseCommonDataModel caseCommonDataModel = caseCommonDataService.getCaseCommonDataById(id);
            json.setRtData(caseCommonDataModel);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            json.setRtState(false);
            e.printStackTrace();
        }
        return json;
    }
}
