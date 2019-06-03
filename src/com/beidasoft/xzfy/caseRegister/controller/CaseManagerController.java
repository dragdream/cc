package com.beidasoft.xzfy.caseRegister.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzfy.base.controller.FyBaseController;
import com.beidasoft.xzfy.caseRegister.bean.FyCaseHandling;
import com.beidasoft.xzfy.caseRegister.bean.FyLetter;
import com.beidasoft.xzfy.caseRegister.bean.FyReception;
import com.beidasoft.xzfy.caseRegister.bean.FyThirdParty;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.ClientInfo;
import com.beidasoft.xzfy.caseRegister.model.caseManager.request.ApplicantReq;
import com.beidasoft.xzfy.caseRegister.model.caseManager.request.FyLetterReq;
import com.beidasoft.xzfy.caseRegister.model.caseManager.request.GetCaseInfoReq;
import com.beidasoft.xzfy.caseRegister.model.caseManager.request.MaterialReq;
import com.beidasoft.xzfy.caseRegister.model.caseManager.request.ReceptionReq;
import com.beidasoft.xzfy.caseRegister.model.caseManager.request.RespondentReq;
import com.beidasoft.xzfy.caseRegister.model.caseManager.request.ReviewMattersReq;
import com.beidasoft.xzfy.caseRegister.model.caseManager.request.ThirdPartyReq;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.AddCaseMaterialResp;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.AddFyApplicantResp;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.AddFyLetterResp;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.AddFyReceptioResp;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.AddFyRespondentResp;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.AddFyThirdPartyResp;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.AddReviewMattersResp;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.GetFyApplicantResp;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.GetFyLetterResp;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.GetFyReceptionResp;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.GetFyRespondentResp;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.GetFyThirdPartyResp;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.GetReviewMattersResp;
import com.beidasoft.xzfy.caseRegister.service.CaseExtractService;
import com.beidasoft.xzfy.caseRegister.service.CaseFyApplicantService;
import com.beidasoft.xzfy.caseRegister.service.CaseFyLetterService;
import com.beidasoft.xzfy.caseRegister.service.CaseFyReceptionService;
import com.beidasoft.xzfy.caseRegister.service.CaseFyRespondentService;
import com.beidasoft.xzfy.caseRegister.service.CaseFyThirdPartyService;
import com.beidasoft.xzfy.caseRegister.service.CaseMaterialService;
import com.beidasoft.xzfy.caseRegister.service.CaseReviewMattersService;
import com.tianee.webframe.httpmodel.TeeJson;
import common.Logger;

/**
 * 案件登记
 * 
 * @author chumc
 */
@Controller
@RequestMapping("/xzfy/caseManager")
public class CaseManagerController extends FyBaseController {

    public Logger log = Logger.getLogger(CaseManagerController.class);

    private static final long serialVersionUID = 1L;

    @Autowired
    private CaseFyLetterService caseFyLetterService;

    @Autowired
    private CaseFyReceptionService caseFyReceptionService;

    @Autowired
    private CaseFyApplicantService caseFyApplicantService;

    @Autowired
    private CaseFyRespondentService caseFyRespondentService;

    @Autowired
    private CaseFyThirdPartyService caseFyThirdPartyService;

    @Autowired
    private CaseMaterialService caseMaterialService;

    @Autowired
    private CaseReviewMattersService caseReviewMattersService;

    @Autowired
    private CaseExtractService caseExtractService;

    /**
     * 来件信息-登记/修改
     * 
     * @param fyLetterReq
     * @return
     */
    @ResponseBody
    @RequestMapping("/addOrUpdateFyLetter")
    public TeeJson addOrUpdateFyLetter(@RequestBody FyLetterReq fyLetterReq) {
        log.info("[xzfy - LetterReceptionController - addOrUpdateFyLetter] enter controller.");
        TeeJson returnData = new TeeJson();
        AddFyLetterResp resp = new AddFyLetterResp();
        try {
            // 校验入参
            fyLetterReq.validate();
            // 来件信息登记
            resp = caseFyLetterService.addOrUpdateFyLetter(fyLetterReq.getLetter(), getRequest());

            returnData.setRtData(resp);
            returnData.setRtState(true);
            returnData.setRtMsg("来件信息登记成功");
        } catch (Exception e) {
            log.info("[xzfy - LetterReceptionController - addOrUpdateFyLetter] error=" + e);
            returnData.setRtData(e);
            returnData.setRtState(false);
            returnData.setRtMsg("来件信息登记失败");
        }
        return returnData;
    }

    /**
     * 接待信息-登记/修改
     * 
     * @param receptionReq
     * @return
     */
    @ResponseBody
    @RequestMapping("/addOrUpdateFyReception")
    public TeeJson addOrUpdateFyReception(@RequestBody ReceptionReq receptionReq) {
        log.info("[xzfy - LetterReceptionController - addOrUpdateFyReception] enter controller.");
        TeeJson returnData = new TeeJson();
        AddFyReceptioResp resp = new AddFyReceptioResp();
        try {
            // 校验入参
            receptionReq.validate();
            // 来件信息登记
            resp = caseFyReceptionService.addOrUpdateFyReception(receptionReq.getReception(), getRequest());

            returnData.setRtData(resp);
            returnData.setRtState(true);
            returnData.setRtMsg("接待信息登记成功");
        } catch (Exception e) {
            log.info("[xzfy - LetterReceptionController - addOrUpdateFyReception] error=" + e);
            returnData.setRtData(e);
            returnData.setRtState(false);
            returnData.setRtMsg("接待信息登记失败");
        }
        return returnData;
    }

    /**
     * 当事人信息-申请人信息登记/修改
     * 
     * @param clientInfoReq
     * @return
     */
    @ResponseBody
    @RequestMapping("/addOrUpdateFyApplicant")
    public TeeJson addOrUpdateFyApplicant(@RequestBody ApplicantReq applicantReq) {

        log.info("[xzfy - ClientInfoController - addFyApplicant] enter controller.");
        TeeJson returnData = new TeeJson();
        AddFyApplicantResp resp = new AddFyApplicantResp();
        try {
            applicantReq.validate();
            resp = caseFyApplicantService.addOrUpdateFyApplicant(applicantReq, getRequest());
            returnData.setRtData(resp);
            returnData.setRtState(true);
            returnData.setRtMsg("申请人信息登记成功");
        } catch (Exception e) {
            log.info("[xzfy - ClientInfoController - addFyApplicant] error=" + e);
            returnData.setRtData(resp);
            returnData.setRtState(false);
            returnData.setRtMsg("申请人信息登记失败");
        }
        return returnData;
    }

    /**
     * 当事人信息-被申请人信息登记/修改
     * 
     * @param clientInfoReq
     * @return
     */
    @ResponseBody
    @RequestMapping("/addOrUpdateFyRespondent")
    public TeeJson addOrUpdateFyRespondent(@RequestBody RespondentReq respondentReq) {

        log.info("[xzfy - ClientInfoController - addFyRespondent] enter controller.");
        TeeJson returnData = new TeeJson();
        AddFyRespondentResp resp = new AddFyRespondentResp();
        try {
            respondentReq.validate();
            resp = caseFyRespondentService.addOrUpdateFyRespondent(respondentReq, getRequest());
            returnData.setRtData(resp);
            returnData.setRtState(true);
            returnData.setRtMsg("被申请人信息登记成功");
        } catch (Exception e) {
            log.info("[xzfy - ClientInfoController - addFyRespondent] error=" + e);
            returnData.setRtData(resp);
            returnData.setRtState(false);
            returnData.setRtMsg("被申请人信息登记失败");
        }
        return returnData;
    }

    /**
     * 当事人信息-第三人信息登记/修改
     * 
     * @param addFyThirdPartyReq
     * @return
     */
    @ResponseBody
    @RequestMapping("/addOrUpdateFyThirdParty")
    public TeeJson addOrUpdateFyThirdParty(@RequestBody ThirdPartyReq thirdPartyReq) {

        log.info("[xzfy - ClientInfoController - addFyThirdParty] enter controller.");
        TeeJson returnData = new TeeJson();
        AddFyThirdPartyResp resp = new AddFyThirdPartyResp();
        try {
            thirdPartyReq.validate();
            resp = caseFyThirdPartyService.addOrUpdateFyThirdParty(thirdPartyReq, getRequest());
            returnData.setRtData(resp);
            returnData.setRtState(true);
            returnData.setRtMsg("第三人信息登记成功");
        } catch (Exception e) {
            log.info("[xzfy - ClientInfoController - addFyThirdParty] error=" + e);
            returnData.setRtData(resp);
            returnData.setRtState(false);
            returnData.setRtMsg("第三人信息登记失败");
        }
        return returnData;
    }

    /**
     * 案件材料-新增
     * 
     * @param reviewMattersReq
     * @return
     */
    @ResponseBody
    @RequestMapping("/addCaseMaterial")
    public TeeJson addCaseMaterial(MaterialReq materialReq) {

        log.info("[xzfy - CaseRegisterController - reviewMatters] enter controller.");
        TeeJson returnData = new TeeJson();
        AddCaseMaterialResp resp = new AddCaseMaterialResp();
        try {
            materialReq.validate();
            caseMaterialService.addCaseMaterial(materialReq);
            resp.setCaseId(materialReq.getCaseId());
            returnData.setRtData(resp);
            returnData.setRtState(true);
            returnData.setRtMsg("案件材料信息登记成功");
        } catch (Exception e) {
            log.info("[xzfy - CaseRegisterController - reviewMatters] error=" + e);
            returnData.setRtData(e);
            returnData.setRtState(false);
            returnData.setRtMsg("案件材料信息登记失败");
        }
        return returnData;
    }

    /**
     * 复议事项-新增
     * 
     * @param reviewMattersReqcaseReviewMattersService
     * @return
     */
    @ResponseBody
    @RequestMapping("/addReviewMatters")
    public TeeJson addReviewMatters(@RequestBody ReviewMattersReq reviewMattersReq) {

        log.info("[xzfy - CaseRegisterController - reviewMatters] enter controller.");
        TeeJson returnData = new TeeJson();
        AddReviewMattersResp resp = new AddReviewMattersResp();
        try {
            resp = caseReviewMattersService.addReviewMatters(reviewMattersReq.getCaseHandling(), getRequest());
            returnData.setRtData(resp);
            returnData.setRtState(true);
            returnData.setRtMsg("复议事项登记成功");
        } catch (Exception e) {
            log.info("[xzfy - CaseRegisterController - reviewMatters] error=" + e);
            returnData.setRtData(e);
            returnData.setRtState(false);
            returnData.setRtMsg("复议事项登记失败");
        }
        return returnData;
    }

    /**
     * 来件信息-根据caseId查询
     * 
     * @param caseId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getFyLetter")
    public TeeJson getFyLetter(GetCaseInfoReq getCaseInfoReq) {
        log.info("[xzfy - LetterReceptionController - getFyLetter] enter controller.");
        TeeJson returnData = new TeeJson();
        GetFyLetterResp resp = new GetFyLetterResp();
        try {
            // 入参校验
            getCaseInfoReq.validate();
            // 根据caseId查询来件信息
            FyLetter fyLetterVo = caseFyLetterService.getFyLetterByCaseId(getCaseInfoReq.getCaseId());
            resp.setFyLetterVo(fyLetterVo);
            returnData.setRtData(resp);
            returnData.setRtState(true);
            returnData.setRtMsg("根据caseId查询来件信息成功");
        } catch (Exception e) {
            log.info("[xzfy - LetterReceptionController - getFyLetter] error=" + e);
            returnData.setRtData(e);
            returnData.setRtState(true);
            returnData.setRtMsg("根据caseId查询来件信息失败");
        }
        return returnData;
    }

    /**
     * 接待信息-根据caseId查询
     * 
     * @param caseId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getFyReception")
    public TeeJson getFyReception(GetCaseInfoReq getCaseInfoReq) {
        log.info("[xzfy - LetterReceptionController - getFyReception] enter controller.");
        TeeJson returnData = new TeeJson();
        GetFyReceptionResp resp = new GetFyReceptionResp();
        try {
            // 入参校验
            getCaseInfoReq.validate();
            // 根据caseId查询接待信息
            FyReception fyReceptionVo = caseFyReceptionService.getFyReceptionByCaseId(getCaseInfoReq.getCaseId());
            resp.setFyReceptionVo(fyReceptionVo);
            returnData.setRtData(resp);
            returnData.setRtState(true);
            returnData.setRtMsg("根据caseId查询接待信息成功");
        } catch (Exception e) {
            returnData.setRtData(e);
            returnData.setRtState(false);
            returnData.setRtMsg("根据caseId查询接待信息失败");
        }
        return returnData;
    }

    /**
     * 当事人信息-根据caseId查询当事人信息
     * 
     * @param getCaseInfoReq
     * @return
     */
    @ResponseBody
    @RequestMapping("/clientInfo")
    public TeeJson getClientInfo(GetCaseInfoReq getCaseInfoReq) {
        log.info("[xzfy - ClientInfoController - getFyApplicant] enter controller.");
        TeeJson returnData = new TeeJson();
        ClientInfo clientInfo = new ClientInfo();
        try {
            // 入参校验
            getCaseInfoReq.validate();
            // 根据caseId查询来件信息
            clientInfo = caseExtractService.getClientInfoByCaseId(getCaseInfoReq.getCaseId());
            returnData.setRtData(clientInfo);
            returnData.setRtState(true);
            returnData.setRtMsg("申请人信息查询成功");
        } catch (Exception e) {
            log.error("[xzfy - ClientInfoController - getFyApplicant] error=" + e);
            returnData.setRtData(e);
            returnData.setRtState(false);
            returnData.setRtMsg("申请人信息查询失败");
        }
        return returnData;
    }

    /**
     * 当事人信息-根据caseId查询申请人信息
     * 
     * @param caseId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getFyApplicant")
    public TeeJson getFyApplicant(GetCaseInfoReq getCaseInfoReq) {
        log.info("[xzfy - ClientInfoController - getFyApplicant] enter controller.");
        TeeJson returnData = new TeeJson();
        GetFyApplicantResp resp = new GetFyApplicantResp();
        try {
            // 入参校验
            getCaseInfoReq.validate();
            // 根据caseId查询来件信息
            resp = caseFyApplicantService.getFyApplicant(getCaseInfoReq.getCaseId());
            returnData.setRtData(resp);
            returnData.setRtState(true);
            returnData.setRtMsg("申请人信息查询成功");
        } catch (Exception e) {
            log.info("[xzfy - ClientInfoController - getFyApplicant] error=" + e);
            returnData.setRtData(resp);
            returnData.setRtState(false);
            returnData.setRtMsg("申请人信息查询失败");
        }
        return returnData;
    }

    /**
     * 当事人信息-根据caseId查询被申请人信息
     * 
     * @param caseId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getFyRespondent")
    public TeeJson getFyRespondent(GetCaseInfoReq getCaseInfoReq) {
        log.info("[xzfy - ClientInfoController - getFyRespondent] enter controller.");
        TeeJson returnData = new TeeJson();
        GetFyRespondentResp resp = new GetFyRespondentResp();
        try {
            // 入参校验
            getCaseInfoReq.validate();
            // 根据caseId查询被申请人信息
            resp = caseFyRespondentService.getFyRespondent(getCaseInfoReq.getCaseId());

            returnData.setRtData(resp);
            returnData.setRtState(true);
            returnData.setRtMsg("被申请人信息查询成功");
        } catch (Exception e) {
            log.error("[xzfy - ClientInfoController - getFyRespondent] error=" + e);
            returnData.setRtData(resp);
            returnData.setRtState(false);
            returnData.setRtMsg("被申请人信息查询失败");
        }
        return returnData;
    }

    /**
     * 当事人信息-根据caseId查询第三人信息
     * 
     * @param caseId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getFyThirdParty")
    public TeeJson getFyThirdParty(GetCaseInfoReq getCaseInfoReq) {
        log.info("[xzfy - ClientInfoController - getFyRespondent] enter controller.");
        TeeJson returnData = new TeeJson();
        GetFyThirdPartyResp resp = new GetFyThirdPartyResp();
        try {
            // 入参校验
            getCaseInfoReq.validate();
            // 根据caseId查询第三人信息
            List<FyThirdParty> fyThirdPartyList = caseFyThirdPartyService.getFyThirdParty(getCaseInfoReq.getCaseId());
            resp.setFyThirdPartyList(fyThirdPartyList);
            returnData.setRtData(resp);
            returnData.setRtState(true);
            returnData.setRtMsg("第三人信息查询成功");
        } catch (Exception e) {
            log.info("[xzfy - ClientInfoController - getFyRespondent] error=" + e);
            returnData.setRtData(resp);
            returnData.setRtState(false);
            returnData.setRtMsg("第三人信息查询失败");
        }
        return returnData;
    }

    /**
     * 复议事项-根据caseId查询复议事项
     * 
     * @param caseId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getReviewMatters")
    public TeeJson getReviewMatters(GetCaseInfoReq getCaseInfoReq) {
        log.info("[xzfy - ClientInfoController - getFyRespondent] enter controller.");
        TeeJson returnData = new TeeJson();
        GetReviewMattersResp resp = new GetReviewMattersResp();
        try {
            // 入参校验
            getCaseInfoReq.validate();
            // 根据caseId查询复议事项
            FyCaseHandling fyCaseHandlingVo = caseReviewMattersService.getReviewMatters(getCaseInfoReq.getCaseId());
            resp.setFyCaseHandling(fyCaseHandlingVo);
            returnData.setRtData(resp);
            returnData.setRtState(true);
            returnData.setRtMsg("根据caseId查询复议事项成功");
        } catch (Exception e) {
            log.error("[xzfy - ClientInfoController - getFyRespondent] error=" + e);
            returnData.setRtState(false);
            returnData.setRtMsg("根据caseId查询复议事项失败");
        }
        return returnData;
    }
}
