package com.beidasoft.xzfy.caseRegister.service;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beidasoft.xzfy.caseRegister.bean.FyCaseHandling;
import com.beidasoft.xzfy.caseRegister.bean.FyReception;
import com.beidasoft.xzfy.caseRegister.dao.CaseFyReceptionDao;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Reception;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.AddFyReceptioResp;
import com.beidasoft.xzfy.utils.Const;
import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;

@Service
public class CaseFyReceptionService {

    @Autowired
    private CaseFyReceptionDao caseFyReceptionDao;

    @Autowired
    private CaseInfoService caseInfoService;

    /**
     * 接待信息登记
     * 
     * @param reception
     * @param request
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Transactional
    public AddFyReceptioResp addOrUpdateFyReception(Reception reception, HttpServletRequest request) throws Exception {
        AddFyReceptioResp resp = new AddFyReceptioResp();
        FyReception fyReceptionBean = new FyReception();
        String operationType = reception.getOperationType(); // 操作类型
                                                             // "01"为登记/"02"为填报
        // 获取当前用户
        TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
        // 获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 设置日期格式
        String time = df.format(new Date()).toString();
        String caseId = reception.getCaseId();
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
            fyCaseHandlingBean.setApplicationTypeCode(reception.getApplicationTypeCode());// 复议申请方式代码
            fyCaseHandlingBean.setApplicationType(reception.getApplicationType()); // 复议申请方式
            // 初始化案件信息
            caseInfoService.initCaseInfo(fyCaseHandlingBean);
        }
        BeanUtils.copyProperties(fyReceptionBean, reception);
        StringUtils.setAddInfo(fyReceptionBean, loginUser);
        fyReceptionBean.setCreateTime(time);
        fyReceptionBean.setVisitor(JSONArray.fromObject(reception.getVisitorVo()).toString());
        fyReceptionBean.setReconsiderMaterial(JSONArray.fromObject(reception.getReconsiderMaterialVo()).toString());
        caseFyReceptionDao.addOrUpdateFyReception(fyReceptionBean, caseId);
        resp.setCaseId(caseId);
        return resp;
    }

    /**
     * 根据caseId当面接待信息
     * 
     * @param caseId
     * @return
     */
    public FyReception getFyReceptionByCaseId(String caseId) {
        // 根据caseId查询接待信息
        FyReception fyReception = caseFyReceptionDao.getFyReceptionByCaseId(caseId);
        return fyReception;
    }

}
