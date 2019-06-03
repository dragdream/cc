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
import com.beidasoft.xzfy.caseRegister.bean.FyThirdParty;
import com.beidasoft.xzfy.caseRegister.common.NumEnum;
import com.beidasoft.xzfy.caseRegister.dao.CaseFyAgentDao;
import com.beidasoft.xzfy.caseRegister.dao.CaseFyThirdPartyDao;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Agent;
import com.beidasoft.xzfy.caseRegister.model.caseManager.request.ThirdPartyReq;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.AddFyThirdPartyResp;
import com.beidasoft.xzfy.utils.Const;
import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;

@Service
public class CaseFyThirdPartyService {

    @Autowired
    private CaseFyThirdPartyDao caseFyThirdPartyDao;

    @Autowired
    private CaseFyAgentDao caseFyAgentDao;

    @Autowired
    private CaseInfoService caseInfoService;

    /**
     * 第三人信息登记/修改
     * 
     * @param addFyThirdPartyReq
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional
    public AddFyThirdPartyResp addOrUpdateFyThirdParty(ThirdPartyReq thirdPartyReq, HttpServletRequest request)
        throws Exception {
        AddFyThirdPartyResp resp = new AddFyThirdPartyResp();

        String caseId = thirdPartyReq.getCaseId();
        String operationType = thirdPartyReq.getOperationType(); // 操作类型
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
            System.err.println("初始化案件信息---thirdParty");
            // 初始化案件信息
            caseInfoService.initCaseInfo(fyCaseHandlingBean);
        }
        // 第三人信息登记
        List<FyThirdParty> thirdPartyList = thirdPartyReq.getThirdPartyList();// 第三人
        List<FyThirdParty> fyThirdPartyList = new ArrayList<>();
        for (FyThirdParty fyThirdParty : thirdPartyList) {
            if ("".equals(fyThirdParty.getId()) || null == fyThirdParty.getId()) {
                fyThirdParty.setId(StringUtils.getUUId());
            }
            fyThirdParty.setCaseId(caseId);
            StringUtils.setAddInfo(fyThirdParty, loginUser);
            fyThirdParty.setCreatedTime(time);
            fyThirdPartyList.add(fyThirdParty);
        }
        // 删除第三人信息
        caseFyThirdPartyDao.updateFyThirdPartyByCaseId(caseId);
        caseFyThirdPartyDao.addFyThirdParty(fyThirdPartyList);
        // 第三人代理人
        List<FyAgent> agentList = new ArrayList<>();
        List<FyAgentRelation> fyAgentRelationList = new ArrayList<>();// 代理人
        for (Agent agentVo : thirdPartyReq.getAgentList()) {
            FyAgent fyAgent = new FyAgent();
            FyAgentRelation fyAgentRelation = new FyAgentRelation();
            fyAgentRelation.setCaseId(caseId);
            fyAgentRelation.setAgentId(agentVo.getId());
            fyAgentRelation.setRelationId(agentVo.getAgentParentId());
            BeanUtils.copyProperties(fyAgent, agentVo);
            if ("".equals(fyAgent.getId()) || null == fyAgent.getId()) {
                fyAgent.setId(StringUtils.getUUId());
            }
            fyAgent.setType(NumEnum.NUM.getThree());
            agentList.add(fyAgent);
            fyAgentRelationList.add(fyAgentRelation);
        }
        // 代理人信息删除
        String type = NumEnum.NUM.getThree();
        caseFyAgentDao.updateFyAgent(caseId, type);
        // 代理人信息登记/修改
        caseFyAgentDao.addFyAgent(agentList);
        caseFyAgentDao.addFyAgentRelationList(fyAgentRelationList);
        resp.setCaseId(caseId);
        return resp;
    }

    /**
     * 根据caseId查询第三人信息
     * 
     * @param caseId
     * @return
     */
    public List<FyThirdParty> getFyThirdParty(String caseId) {
        List<FyThirdParty> fyThirdPartyList = caseFyThirdPartyDao.findFyThirdPartyByCaseId(caseId);
        return fyThirdPartyList;
    }
}
