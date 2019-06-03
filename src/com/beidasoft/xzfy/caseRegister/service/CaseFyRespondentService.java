package com.beidasoft.xzfy.caseRegister.service;

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
import com.beidasoft.xzfy.caseRegister.bean.FyCaseHandling;
import com.beidasoft.xzfy.caseRegister.bean.FyRespondent;
import com.beidasoft.xzfy.caseRegister.common.NumEnum;
import com.beidasoft.xzfy.caseRegister.dao.CaseFyAgentDao;
import com.beidasoft.xzfy.caseRegister.dao.CaseFyRespondentDao;
import com.beidasoft.xzfy.caseRegister.dao.CaseReviewMattersDao;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Agent;
import com.beidasoft.xzfy.caseRegister.model.caseManager.request.RespondentReq;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.AddFyRespondentResp;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.GetFyRespondentResp;
import com.beidasoft.xzfy.utils.Const;
import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;

@Service
public class CaseFyRespondentService {

    @Autowired
    private CaseFyRespondentDao caseFyRespondentDao;

    @Autowired
    private CaseFyAgentDao caseFyAgentDao;

    @Autowired
    private CaseInfoService caseInfoService;

    @Autowired
    private CaseReviewMattersDao caseReviewMattersDao;

    /**
     * 被申请人信息登记
     * 
     * @param addFyRespondentReq
     * @param httpServletRequest
     * @return
     */
    @Transactional
    public AddFyRespondentResp addOrUpdateFyRespondent(RespondentReq respondentReq, HttpServletRequest request)
        throws Exception {
        AddFyRespondentResp resp = new AddFyRespondentResp();
        String caseId = respondentReq.getCaseId();
        String operationType = respondentReq.getOperationType(); // 操作类型
                                                                 // "01"为登记/"02"为填报

        List<FyRespondent> respondentList = respondentReq.getRespondentList();// 被申请人
        List<Agent> agentList = respondentReq.getAgentList(); // 被申请人代理人
        // 获取当前用户
        TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);

        // 获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 设置日期格式
        String time = df.format(new Date()).toString();

        /*
         * 判断caseId是否存在 ,若不存在初始化案件信息 ,若存在就对案件信息进行修改
         */
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
        List<FyRespondent> fyRespondentList = new ArrayList<>();
        for (FyRespondent fyRespondent : respondentList) {
            if ("".equals(fyRespondent.getId()) || null == fyRespondent.getId()) {
                fyRespondent.setId(StringUtils.getUUId());
            }
            StringUtils.setAddInfo(fyRespondent, loginUser);
            fyRespondent.setCreatedTime(time);
            fyRespondentList.add(fyRespondent);
        }
        // 删除被申请人信息
        caseFyRespondentDao.updateFyRespondentByCaseId(caseId);
        caseFyRespondentDao.addFyRespondent(fyRespondentList);
        List<FyAgent> fyAgentList = new ArrayList<>();
        List<FyAgentRelation> fyAgentRelationList = new ArrayList<>();// 代理人
        for (Agent agent : agentList) {
            FyAgent fyAgent = new FyAgent();
            FyAgentRelation fyAgentRelation = new FyAgentRelation();
            fyAgentRelation.setCaseId(caseId);
            fyAgentRelation.setAgentId(agent.getId());
            fyAgentRelation.setRelationId(agent.getAgentParentId());
            BeanUtils.copyProperties(fyAgent, agent);
            if ("".equals(fyAgent.getId()) || null == fyAgent.getId()) {
                fyAgent.setId(StringUtils.getUUId());
            }
            fyAgent.setType(NumEnum.NUM.getTwo());
            fyAgentList.add(fyAgent);
            fyAgentRelationList.add(fyAgentRelation);
        }
        // 代理人信息删除
        String type = NumEnum.NUM.getTwo();
        caseFyAgentDao.updateFyAgent(caseId, type);
        // 代理人信息登记/修改
        caseFyAgentDao.addFyAgent(fyAgentList);
        caseFyAgentDao.addFyAgentRelationList(fyAgentRelationList);
        resp.setCaseId(caseId);
        return resp;
    }

    /**
     * 根据caseId查询被申请人信息
     * 
     * @param caseId
     * @return
     */
    public GetFyRespondentResp getFyRespondent(String caseId) {
        GetFyRespondentResp resp = new GetFyRespondentResp();
        List<FyRespondent> listFyRespondent = caseFyRespondentDao.findFyRespondentByCaseId(caseId);
        List<FyAgent> agentList = caseFyAgentDao.findFyAgentByCaseId(caseId, null);
        resp.setFyRespondentList(listFyRespondent);
        resp.setFyAgentList(agentList);
        return resp;
    }
}
