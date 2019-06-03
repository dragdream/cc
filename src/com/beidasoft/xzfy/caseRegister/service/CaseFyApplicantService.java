package com.beidasoft.xzfy.caseRegister.service;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beidasoft.xzfy.caseRegister.bean.FyAgent;
import com.beidasoft.xzfy.caseRegister.bean.FyAgentRelation;
import com.beidasoft.xzfy.caseRegister.bean.FyApplicant;
import com.beidasoft.xzfy.caseRegister.bean.FyCaseHandling;
import com.beidasoft.xzfy.caseRegister.common.NumEnum;
import com.beidasoft.xzfy.caseRegister.dao.CaseFyAgentDao;
import com.beidasoft.xzfy.caseRegister.dao.CaseFyApplicantDao;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Agent;
import com.beidasoft.xzfy.caseRegister.model.caseManager.request.ApplicantReq;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.AddFyApplicantResp;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.GetFyApplicantResp;
import com.beidasoft.xzfy.utils.Const;
import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;

/**
 * 申请人信息
 * 
 * @author chumc
 * 
 */
@Service
public class CaseFyApplicantService {

    @Autowired
    private CaseFyApplicantDao caseFyApplicantDao;

    @Autowired
    private CaseFyAgentDao caseFyAgentDao;

    @Autowired
    private CaseInfoService caseInfoService;

    /**
     * 申请人信息登记/修改
     * 
     * @param addFyApplicantReq
     * @param request
     * @return
     */
    @Transactional
    public AddFyApplicantResp addOrUpdateFyApplicant(ApplicantReq applicantReq, HttpServletRequest request)
        throws Exception {

        AddFyApplicantResp resp = new AddFyApplicantResp();
        String caseId = applicantReq.getCaseId();
        String operationType = applicantReq.getOperationType(); // 操作类型
                                                                // "01"为登记/"02"为填报
        // 获取当前用户
        TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);

        // 获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 设置日期格式
        String time = df.format(new Date()).toString();

        if ("".equals(caseId) || null == caseId) {
            caseId = StringUtils.getUUId();
            FyCaseHandling fyCaseHandlingBean = new FyCaseHandling();
            String caseStatusCode = null;// 案件状态代码
            String caseStatus = null;// 案件状态
            switch (operationType) {
            // 案件登记
                case "01":
                    caseStatusCode = Const.CASESTATUS.CASE_DRAFT_CODE;
                    caseStatus = Const.CASESTATUS.CASE_DRAFT_NAME; // 登记中
                    break;
                // 案件填报
                case "02":
                    caseStatusCode = Const.CASEFILL.FILL;
                    caseStatus = Const.CASEFILL.FILL_NAME; // 填报中
                    break;
                default:
                    break;
            }
            StringUtils.setAddInfo(fyCaseHandlingBean, loginUser);
            fyCaseHandlingBean.setCreateTime(time);
            fyCaseHandlingBean.setCaseId(caseId);
            fyCaseHandlingBean.setCaseStatusCode(caseStatusCode);
            fyCaseHandlingBean.setCaseStatus(caseStatus);
            // 初始化案件信息
            caseInfoService.initCaseInfo(fyCaseHandlingBean);
        }
        List<FyApplicant> fyApplicantListAll = new ArrayList<>();
        List<FyApplicant> applicantList = applicantReq.getApplicantList();// 申请人
        List<FyApplicant> otherApplicantList = applicantReq.getOtherApplicantList();// 其他申请人

        fyApplicantListAll.addAll(applicantList);
        fyApplicantListAll.addAll(otherApplicantList);

        List<FyApplicant> fyApplicantList = new ArrayList<>();

        for (FyApplicant fyApplicant : fyApplicantListAll) {
            if ("".equals(fyApplicant.getId()) || null == fyApplicant.getId()) {
                fyApplicant.setId(null);
            }
            fyApplicant.setCaseId(caseId);
            StringUtils.setAddInfo(fyApplicant, loginUser);
            fyApplicant.setCreatedTime(time);
            fyApplicant.setIsOtherApplicant(0);
            fyApplicantList.add(fyApplicant);
        }
        for (FyApplicant fyApplicant : otherApplicantList) {
            if ("".equals(fyApplicant.getId()) || null == fyApplicant.getId()) {
                fyApplicant.setId(StringUtils.getUUId());
            }
            fyApplicant.setCaseId(caseId);
            StringUtils.setAddInfo(fyApplicant, loginUser);
            fyApplicant.setCreatedTime(time);
            fyApplicant.setIsOtherApplicant(1);
            fyApplicantList.add(fyApplicant);
        }

        // 删除申请人信息
        caseFyApplicantDao.updateFyApplicantByCaseId(caseId);
        // 申请人信息登记
        caseFyApplicantDao.addFyApplicant(fyApplicantList);

        List<FyAgent> agentList = new ArrayList<>();// 代理人
        List<FyAgentRelation> fyAgentRelationList = new ArrayList<>();// 代理人
        for (Agent agent : applicantReq.getAgentList()) {
            FyAgent fyAgent = new FyAgent();
            FyAgentRelation fyAgentRelation = new FyAgentRelation();
            fyAgentRelation.setCaseId(caseId);
            fyAgentRelation.setAgentId(agent.getId());
            fyAgentRelation.setRelationId(agent.getAgentParentId());
            BeanUtils.copyProperties(fyAgent, agent);
            if ("".equals(fyAgent.getId()) || null == fyAgent.getId()) {
                fyAgent.setId(StringUtils.getUUId());
            }
            fyAgent.setType(NumEnum.NUM.getOne());
            agentList.add(fyAgent);
            fyAgentRelationList.add(fyAgentRelation);
        }
        // 代理人信息删除
        String type = NumEnum.NUM.getOne();
        caseFyAgentDao.updateFyAgent(caseId, type);
        // 代理人信息登记/修改
        caseFyAgentDao.addFyAgent(agentList);
        caseFyAgentDao.addFyAgentRelationList(fyAgentRelationList);
        resp.setCaseId(caseId);
        return resp;
    }

    /**
     * 根据caseId查询申请人信息
     * 
     * @param caseId
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public GetFyApplicantResp getFyApplicant(String caseId) throws IllegalAccessException, InvocationTargetException {
        GetFyApplicantResp resp = new GetFyApplicantResp();
        List<FyApplicant> listFyApplicant = caseFyApplicantDao.findFyApplicantByCaseId(caseId);
        List<FyApplicant> applicantList = new ArrayList<>();
        List<FyApplicant> otherApplicantList = new ArrayList<>();

        for (FyApplicant fyApplicant : listFyApplicant) {
            if (fyApplicant.getIsOtherApplicant() == 0) {
                FyApplicant fyApplicantVo = new FyApplicant();
                BeanUtils.copyProperties(fyApplicantVo, fyApplicant);
                applicantList.add(fyApplicantVo);
            } else {
                FyApplicant fyApplicantVo = new FyApplicant();
                BeanUtils.copyProperties(fyApplicantVo, fyApplicant);
                otherApplicantList.add(fyApplicantVo);
            }
        }
        List<FyAgent> agentList = caseFyAgentDao.findFyAgentByCaseId(caseId, null);
        resp.setApplicantList(applicantList);
        resp.setOtherApplicantList(otherApplicantList);
        resp.setAgentList(agentList);
        return resp;
    }
}
