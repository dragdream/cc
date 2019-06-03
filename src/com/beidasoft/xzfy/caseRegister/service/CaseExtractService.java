package com.beidasoft.xzfy.caseRegister.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.beidasoft.xzfy.caseAcceptence.bean.CaseProcessLogInfo;
import com.beidasoft.xzfy.caseRegister.bean.FyApplicant;
import com.beidasoft.xzfy.caseRegister.bean.FyCaseHandling;
import com.beidasoft.xzfy.caseRegister.bean.FyLetter;
import com.beidasoft.xzfy.caseRegister.bean.FyMaterial;
import com.beidasoft.xzfy.caseRegister.bean.FyReception;
import com.beidasoft.xzfy.caseRegister.bean.FyRespondent;
import com.beidasoft.xzfy.caseRegister.bean.FyThirdParty;
import com.beidasoft.xzfy.caseRegister.common.NumEnum;
import com.beidasoft.xzfy.caseRegister.dao.CaseExtractDao;
import com.beidasoft.xzfy.caseRegister.dao.CaseFyAgentDao;
import com.beidasoft.xzfy.caseRegister.model.caseExtract.request.GetCaseExtractReq;
import com.beidasoft.xzfy.caseRegister.model.caseExtract.response.GetCaseExtractResp;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.CaseAcceptResponse;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.CaseInfoResponse;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.CaseRegisterResponse;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.CaseTrialResponse;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.AcceptInfo;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Agent;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Applicant;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.CaseHandling;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.ClientInfo;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Letter;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Reception;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Respondent;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.ThirdParty;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.TrialInfo;
import com.beidasoft.xzfy.caseRegister.model.entity.Material;
import com.beidasoft.xzfy.caseRegister.model.entity.Visitor;

/**
 * 案件查询
 * 
 * @author cc
 * @date 2019年5月22日
 */
@Service
public class CaseExtractService {

    @Autowired
    private CaseExtractDao caseExtractDao;

    @Autowired
    private CaseFyAgentDao caseFyAgentDao;

    /**
     * 案件提取
     * 
     * @param getCaseExtractReq
     * @return
     */
    public List<GetCaseExtractResp> getCaseExtract(GetCaseExtractReq getCaseExtractReq) {
        List<GetCaseExtractResp> resp = new ArrayList<>();
        resp = caseExtractDao.getCaseExtract(getCaseExtractReq);
        return resp;
    }

    /**
     * 当事人信息
     * 
     * @param caseId
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public ClientInfo getClientInfoByCaseId(String caseId) throws IllegalAccessException, InvocationTargetException {
        // 当事人信息
        ClientInfo clientInfo = new ClientInfo();

        List<FyApplicant> fyApplicantList = caseExtractDao.findFyApplicantByCaseId(caseId);
        List<FyRespondent> fyRespondentList = caseExtractDao.findFyRespondentByCaseId(caseId);
        List<FyThirdParty> fyThirdPartyList = caseExtractDao.findFyThirdPartyByCaseId(caseId);

        // 申请人代理人信息
        List<Agent> applicantAgent = caseFyAgentDao.findAgentByTypeCode(caseId, NumEnum.NUM.getOne());
        // 被申请人代理人信息
        List<Agent> respondentAgent = caseFyAgentDao.findAgentByTypeCode(caseId, NumEnum.NUM.getTwo());
        // 第三人代理人信息
        List<Agent> thirdPartyAgent = caseFyAgentDao.findAgentByTypeCode(caseId, NumEnum.NUM.getThree());

        if (fyApplicantList.size() > 0) {
            clientInfo.setApplicantTypeCode(fyApplicantList.get(0).getApplicantTypeCode());
            if (applicantAgent.size() == 0) {
                clientInfo.setIsAgent("0");
            } else {
                clientInfo.setIsAgent("1");
            }
        }
        if (fyRespondentList.size() > 0) {
            if (applicantAgent.size() == 0) {
                clientInfo.setIsRespondentAgent("0");
            } else {
                clientInfo.setIsRespondentAgent("1");
            }
        }
        if (fyThirdPartyList.size() > 0) {

            if (fyThirdPartyList.size() == 0) {
                clientInfo.setIsThirdParty("0");
            } else {
                clientInfo.setIsThirdParty("1");
            }
            if (thirdPartyAgent.size() == 0) {
                clientInfo.setIsThirdPartyAgent("0");
            } else {
                clientInfo.setIsThirdPartyAgent("1");
            }
        }

        // 申请人 和 其他申请人 和申请人代理人
        List<Applicant> applicantList = new ArrayList<>();
        List<Applicant> otherApplicantList = new ArrayList<>();
        for (FyApplicant fyApplicantVo : fyApplicantList) {
            Applicant applicantVo = new Applicant();
            if (fyApplicantVo.getIsOtherApplicant() == 0) {
                BeanUtils.copyProperties(applicantVo, fyApplicantVo);
                applicantList.add(applicantVo);
            } else if (fyApplicantVo.getIsOtherApplicant() == 1) {
                BeanUtils.copyProperties(applicantVo, fyApplicantVo);
                otherApplicantList.add(applicantVo);
            }
        }
        clientInfo.setApplicant(applicantList);
        clientInfo.setOtherApplicant(otherApplicantList);
        clientInfo.setApplicantAgent(applicantAgent);

        // 被申请人信息 和 被申请人代理人
        List<Respondent> respondent = new ArrayList<>();
        for (FyRespondent fyRespondentVo : fyRespondentList) {
            Respondent respondentVo = new Respondent();
            BeanUtils.copyProperties(respondentVo, fyRespondentVo);
            respondent.add(respondentVo);
        }
        clientInfo.setRespondent(respondent);
        clientInfo.setRespondentAgent(respondentAgent);

        // 第三人信息 和 第三人代理人
        List<ThirdParty> thirdParty = new ArrayList<>();
        for (FyThirdParty fyThirdPartyVo : fyThirdPartyList) {
            ThirdParty thirdPartyVo = new ThirdParty();
            BeanUtils.copyProperties(thirdPartyVo, fyThirdPartyVo);
            thirdParty.add(thirdPartyVo);
        }
        clientInfo.setThirdParty(thirdParty);
        clientInfo.setThirdPartyAgent(thirdPartyAgent);
        return clientInfo;

    }

    /**
     * 案件审理
     * 
     * @param caseId
     * @param caseProcessLogInfo
     * @param fyCaseHandling
     * @return
     */
    private TrialInfo
        findTrialInfo(String caseId, FyCaseHandling fyCaseHandling, CaseProcessLogInfo caseProcessLogInfo) {
        TrialInfo trialInfo = new TrialInfo();
        return trialInfo;
    }

    /**
     * 立案审查
     * 
     * @param caseId
     * @param caseProcessLogInfo
     * @param fyCaseHandlingList
     * @return
     */
    private AcceptInfo findAcceptInfo(String caseId, FyCaseHandling fyCaseHandling,
        CaseProcessLogInfo caseProcessLogInfo) {
        AcceptInfo acceptInfo = new AcceptInfo();
        // 案件Id
        acceptInfo.setCaseId(caseId);
        // 申请行政复议日期
        acceptInfo.setApplicationTime(fyCaseHandling.getCreateTime());
        // 是否经过补正
        acceptInfo.setIsCorrection(null);
        // 补正是否过期
        acceptInfo.setIsCorrectionOut(null);
        // 通知补正时间
        acceptInfo.setNoticeCorrectionTime(null);
        // 补正时间
        acceptInfo.setCorrectionTime(null);
        // // 处理意见
        // private String opinions;
        // // 立案时间
        // private String filingTime;
        // // 案件号
        // private String caseNum;
        // // 审查人
        // private String acceptPeople;
        // // 是否延期审理
        // private String isDelay;
        return acceptInfo;
    }

    /**
     * 登记信息查询
     * 
     * @param caseId
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public CaseRegisterResponse getCaseRegisterInfo(String caseId) throws IllegalAccessException,
        InvocationTargetException {

        CaseRegisterResponse resp = new CaseRegisterResponse();
        // 案件信息表
        List<FyCaseHandling> fyCaseHandlingList = caseExtractDao.findFyCaseHandlingByCaseId(caseId);
        // 来件信息
        List<FyLetter> fyLetterList = caseExtractDao.findFyLetterByCaseId(caseId);
        // 接待信息
        List<FyReception> fyReceptionList = caseExtractDao.findFyReceptionByCaseId(caseId);

        // 来件信息
        List<Letter> letter = new ArrayList<>();
        for (FyLetter fyLetterVo : fyLetterList) {
            Letter letterVo = new Letter();
            BeanUtils.copyProperties(letterVo, fyLetterVo);
            letter.add(letterVo);
        }

        // 接待信息
        List<Reception> reception = new ArrayList<>();
        for (FyReception fyReceptionVo : fyReceptionList) {
            List<Visitor> visitor = JSONArray.parseArray(fyReceptionVo.getVisitor(), Visitor.class);
            Reception receptionVo = new Reception();
            List<Material> reconsiderMaterial =
                JSONArray.parseArray(fyReceptionVo.getReconsiderMaterial(), Material.class);
            BeanUtils.copyProperties(receptionVo, fyReceptionVo);
            receptionVo.setVisitorVo(visitor);
            receptionVo.setReconsiderMaterialVo(reconsiderMaterial);
            reception.add(receptionVo);
        }

        // 当事人信息
        ClientInfo clientInfo = this.getClientInfoByCaseId(caseId);

        // 复议事项
        List<CaseHandling> caseHandling = new ArrayList<>();
        for (FyCaseHandling fyCaseHandlingVo : fyCaseHandlingList) {
            CaseHandling caseHandlingVo = new CaseHandling();
            BeanUtils.copyProperties(caseHandlingVo, fyCaseHandlingVo);
            caseHandling.add(caseHandlingVo);
        }

        // 案件Id
        resp.setCaseId(caseId);
        // 案件状态
        resp.setCaseStatusCode(caseHandling.get(0).getCaseStatusCode());
        resp.setCaseStatus(caseHandling.get(0).getCaseStatus());
        // 复议申请方式
        resp.setApplicationTypeCode(fyCaseHandlingList.get(0).getApplicationTypeCode());
        resp.setApplicationType(fyCaseHandlingList.get(0).getApplicationType());
        // 封装来件
        resp.setLetter(letter.get(0));
        // 封装接待
        resp.setReception(reception.get(0));
        // 封装当事人
        resp.setClientInfo(clientInfo);
        // 封装复议事项
        resp.setCaseHandling(caseHandling.get(0));
        return resp;
    }

    /**
     * 案件信息查询
     * 
     * @param caseId
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public CaseInfoResponse getCaseInfo(String caseId) throws IllegalAccessException, InvocationTargetException {

        CaseInfoResponse caseInfoResponse = new CaseInfoResponse();
        // 案件信息表
        List<FyCaseHandling> fyCaseHandlingList = caseExtractDao.findFyCaseHandlingByCaseId(caseId);

        // 流程表
        List<CaseProcessLogInfo> caseProcessLogInfoList = caseExtractDao.findCaseProcessLogInfoByCaseId(caseId);

        // 当事人信息
        ClientInfo clientInfo = this.getClientInfoByCaseId(caseId);

        // 复议事项
        List<CaseHandling> caseHandling = new ArrayList<>();
        for (FyCaseHandling fyCaseHandlingVo : fyCaseHandlingList) {
            CaseHandling caseHandlingVo = new CaseHandling();
            BeanUtils.copyProperties(caseHandlingVo, fyCaseHandlingVo);
            caseHandling.add(caseHandlingVo);
        }

        // // 立案审查
        // AcceptInfo acceptInfo = this.findAcceptInfo(caseId, fyCaseHandlingList.get(0),
        // caseProcessLogInfoList.get(0));
        //
        // // 案件审理
        // TrialInfo trialInfo = this.findTrialInfo(caseId, fyCaseHandlingList.get(0), caseProcessLogInfoList.get(0));

        caseInfoResponse.setTrialInfo(null);
        caseInfoResponse.setAcceptInfo(null);
        caseInfoResponse.setCaseId(caseId);
        caseInfoResponse.setClientInfo(clientInfo);
        caseInfoResponse.setCaseHandling(caseHandling);
        return caseInfoResponse;
    }

    /**
     * 立案受理
     * 
     * @param caseId
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public CaseAcceptResponse getFilingAcceptance(String caseId) throws IllegalAccessException,
        InvocationTargetException {

        CaseAcceptResponse caseAcceptResponse = new CaseAcceptResponse();
        // 当事人信息
        List<FyApplicant> fyApplicantList = caseExtractDao.findFyApplicantByCaseId(caseId);
        List<FyRespondent> fyRespondentList = caseExtractDao.findFyRespondentByCaseId(caseId);
        // 案件信息表
        List<FyCaseHandling> fyCaseHandlingList = caseExtractDao.findFyCaseHandlingByCaseId(caseId);
        // 案件材料
        List<FyMaterial> fyMaterialList = caseExtractDao.findFyMaterialByCaseId(caseId);

        // 流程表
        List<CaseProcessLogInfo> caseProcessLogInfo = caseExtractDao.findCaseProcessLogInfoByCaseId(caseId);

        List<Applicant> applicantList = new ArrayList<>();
        for (FyApplicant fyApplicant : fyApplicantList) {
            Applicant applicant = new Applicant();
            // 申请人
            if (fyApplicant.getIsOtherApplicant() == 0) {
                BeanUtils.copyProperties(applicant, fyApplicant);
                applicantList.add(applicant);
            }
        }
        List<Respondent> respondentList = new ArrayList<>();
        for (FyRespondent fyRespondent : fyRespondentList) {
            Respondent respondent = new Respondent();
            BeanUtils.copyProperties(respondent, fyRespondent);
            respondentList.add(respondent);
        }
        // 申请材料列表
        List<Material> applyMaterialList = new ArrayList<>();
        // 立案材料列表
        List<Material> filingMaterialList = new ArrayList<>();
        // 审理材料列表
        List<Material> hearingMaterialList = new ArrayList<>();
        for (FyMaterial fyMaterial : fyMaterialList) {
            Material material = new Material();
            if (fyMaterial.getCaseTypeCode() == "0") {
                BeanUtils.copyProperties(material, fyMaterial);
                applyMaterialList.add(material);
            } else if (fyMaterial.getCaseTypeCode() == "1") {
                BeanUtils.copyProperties(material, fyMaterial);
                filingMaterialList.add(material);
            } else {
                BeanUtils.copyProperties(material, fyMaterial);
                hearingMaterialList.add(material);
            }
        }
        caseAcceptResponse.setCaseId(caseId);
        caseAcceptResponse.setApplicantList(applicantList);
        caseAcceptResponse.setRespondentList(respondentList);
        if (fyCaseHandlingList.size() > 0) {
            caseAcceptResponse.setRequestForReconsideration(fyCaseHandlingList.get(0).getRequestForReconsideration());
            caseAcceptResponse.setFactsAndReasons(fyCaseHandlingList.get(0).getFactsAndReasons());
            caseAcceptResponse.setFactsAndReasons(fyCaseHandlingList.get(0).getFactsAndReasons());
            caseAcceptResponse.setCaseSubStatusCode(fyCaseHandlingList.get(0).getCaseSubStatusCode());
            caseAcceptResponse.setCaseSubStatus(fyCaseHandlingList.get(0).getCaseSubStatus());
            caseAcceptResponse.setIsApproval(fyCaseHandlingList.get(0).getIsApproval());
        }
        caseAcceptResponse.setApplyMaterialList(applyMaterialList);
        caseAcceptResponse.setFilingMaterialList(filingMaterialList);
        caseAcceptResponse.setHearingMaterialList(hearingMaterialList);
        if (caseProcessLogInfo.size() > 0) {
            caseAcceptResponse.setAcceptTime(caseProcessLogInfo.get(0).getStartTime());
            caseAcceptResponse.setAcceptResult(caseProcessLogInfo.get(0).getDealResult());
            caseAcceptResponse.setReason(caseProcessLogInfo.get(0).getReason());
            caseAcceptResponse.setRemark(caseProcessLogInfo.get(0).getRemark());
        }
        return caseAcceptResponse;
    }

    /**
     * 立案审理
     * 
     * @param caseId
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public CaseTrialResponse findCaseTrial(String caseId) throws IllegalAccessException, InvocationTargetException {

        CaseTrialResponse caseTrialResponse = new CaseTrialResponse();

        // 当事人信息
        List<FyApplicant> fyApplicantList = caseExtractDao.findFyApplicantByCaseId(caseId);
        List<FyRespondent> fyRespondentList = caseExtractDao.findFyRespondentByCaseId(caseId);
        // 案件信息表
        List<FyCaseHandling> fyCaseHandlingList = caseExtractDao.findFyCaseHandlingByCaseId(caseId);
        // 案件材料
        List<FyMaterial> fyMaterialList = caseExtractDao.findFyMaterialByCaseId(caseId);

        // 流程表
        List<CaseProcessLogInfo> caseProcessLogInfo = caseExtractDao.findCaseProcessLogInfoByCaseId(caseId);

        List<Applicant> applicantList = new ArrayList<>();
        for (FyApplicant fyApplicant : fyApplicantList) {
            Applicant applicant = new Applicant();
            // 申请人
            if (fyApplicant.getIsOtherApplicant() == 0) {
                BeanUtils.copyProperties(applicant, fyApplicant);
                applicantList.add(applicant);
            }
        }

        List<Respondent> respondentList = new ArrayList<>();
        for (FyRespondent fyRespondent : fyRespondentList) {
            Respondent respondent = new Respondent();
            BeanUtils.copyProperties(respondent, fyRespondent);
            respondentList.add(respondent);
        }

        // 申请材料列表
        List<Material> applyMaterialList = new ArrayList<>();
        // 立案材料列表
        List<Material> filingMaterialList = new ArrayList<>();
        // 审理材料列表
        List<Material> hearingMaterialList = new ArrayList<>();
        for (FyMaterial fyMaterial : fyMaterialList) {
            Material material = new Material();
            if (fyMaterial.getCaseTypeCode() == "0") {
                BeanUtils.copyProperties(material, fyMaterial);
                applyMaterialList.add(material);
            } else if (fyMaterial.getCaseTypeCode() == "1") {
                BeanUtils.copyProperties(material, fyMaterial);
                filingMaterialList.add(material);
            } else {
                BeanUtils.copyProperties(material, fyMaterial);
                hearingMaterialList.add(material);
            }
        }
        caseTrialResponse.setCaseId(caseId);
        caseTrialResponse.setApplicantList(applicantList);
        caseTrialResponse.setRespondentList(respondentList);
        if (fyCaseHandlingList.size() > 0) {
            caseTrialResponse.setRequestForReconsideration(fyCaseHandlingList.get(0).getRequestForReconsideration());
            caseTrialResponse.setFactsAndReasons(fyCaseHandlingList.get(0).getFactsAndReasons());
        }
        if (caseProcessLogInfo.size() > 0) {
            caseTrialResponse.setTrialType(caseProcessLogInfo.get(0).getCaseCloseType());
            caseTrialResponse.setReason(caseProcessLogInfo.get(0).getReason());
            caseTrialResponse.setRemark(caseProcessLogInfo.get(0).getRemark());
            caseTrialResponse.setTrialType(caseProcessLogInfo.get(0).getCaseCloseType());
        }
        if (fyCaseHandlingList.size() > 0) {
            caseTrialResponse.setIsPay(fyCaseHandlingList.get(0).getIsCompensation() + "");
            caseTrialResponse.setPayType(fyCaseHandlingList.get(0).getCompensationType());
        }
        return caseTrialResponse;
    }
}
