package com.beidasoft.xzfy.caseRegister.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzfy.base.controller.FyBaseController;
import com.beidasoft.xzfy.caseAcceptence.service.CaseAcceptService;
import com.beidasoft.xzfy.caseRegister.model.caseExtract.request.GetCaseExtractReq;
import com.beidasoft.xzfy.caseRegister.model.caseExtract.response.GetCaseExtractResp;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.request.CaseInfoRequest;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.CaseAcceptResponse;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.CaseInfoResponse;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.CaseRegisterResponse;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.CaseTrialResponse;
import com.beidasoft.xzfy.caseRegister.service.CaseExtractService;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import common.Logger;

/**
 * 案件登记 案件提取
 * 
 * @author chumc
 */
@Controller
@RequestMapping("/xzfy/caseInfo")
public class CaseInfoController extends FyBaseController {

    public Logger log = Logger.getLogger(CaseInfoController.class);

    private static final long serialVersionUID = 1L;

    @Autowired
    private CaseExtractService caseExtractService;

    @Autowired
    private CaseAcceptService caseAcceptService;

    /**
     * caseAcceptService 案件提取
     * 
     * @param caseExtractReq
     * @return
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping("/getCaseExtract")
    public TeeEasyuiDataGridJson getCaseExtract(GetCaseExtractReq getCaseExtractReq) {
        log.info("[xzfy - caseExtract - getCaseExtract] enter controller.");
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        List<GetCaseExtractResp> resp = new ArrayList<>();
        try {
            // 参数校验
            getCaseExtractReq.validate();
            resp = caseExtractService.getCaseExtract(getCaseExtractReq);
            json.setTotal(new Long(resp.size()));
            json.setRows(resp);
        } catch (Exception e) {
            log.info("[xzfy - caseExtract - getCaseExtract] error=" + e);
        }
        return json;
    }

    /**
     * 案件明细查询
     * 
     * @param getCaseInfoReq
     * @return
     */
    @ResponseBody
    @RequestMapping("/getCaseInfo")
    public TeeJson getCaseInfo(CaseInfoRequest caseInfoRequest) {
        log.info("[xzfy - caseExtract - getCaseInfo] enter controller.");
        TeeJson returnData = new TeeJson();
        try {
            caseInfoRequest.validate();
            switch (caseInfoRequest.getType()) {
                case "0":
                    // 登记信息查询
                    CaseRegisterResponse resp = caseExtractService.getCaseRegisterInfo(caseInfoRequest.getCaseId());
                    returnData.setRtData(resp);
                    break;
                case "1":
                    // 案件信息
                    CaseInfoResponse caseInfoResponse = caseExtractService.getCaseInfo(caseInfoRequest.getCaseId());
                    returnData.setRtData(caseInfoResponse);
                    break;
                case "2":
                    // 立案受理
                    CaseAcceptResponse caseAcceptResponse =
                        caseExtractService.getFilingAcceptance(caseInfoRequest.getCaseId());
                    returnData.setRtData(caseAcceptResponse);
                    break;
                case "3":
                    // 立案审理
                    CaseTrialResponse caseTrialResponse = caseExtractService.findCaseTrial(caseInfoRequest.getCaseId());
                    returnData.setRtData(caseTrialResponse);
                    break;
                default:
                    log.error("请求有误！");
                    break;
            }
            returnData.setRtState(true);
            returnData.setRtMsg("案件明细查询成功");
        } catch (Exception e) {
            log.error("[xzfy - caseExtract - getCaseInfo] error=" + e);
            returnData.setRtData(e);
            returnData.setRtState(true);
            returnData.setRtMsg("案件明细查询成功");
        }
        return returnData;
    }
}
