package com.beidasoft.xzfy.caseRegister.service;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beidasoft.xzfy.caseRegister.bean.FyCaseHandling;
import com.beidasoft.xzfy.caseRegister.dao.CaseReviewMattersDao;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.AddReviewMattersResp;
import com.beidasoft.xzfy.caseRegister.model.entity.CaseHandReq;
import com.beidasoft.xzfy.utils.Const;
import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;

@Service
public class CaseReviewMattersService extends TeeBaseService {

    @Autowired
    private CaseReviewMattersDao caseReviewMattersDao;

    /**
     * 复议事项登记
     * 
     * @param addReviewMattersReq
     * @param request
     * @param caseId
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Transactional
    public AddReviewMattersResp addReviewMatters(CaseHandReq caseHandling, HttpServletRequest request) throws Exception {

        AddReviewMattersResp resp = new AddReviewMattersResp();
        FyCaseHandling fyCaseHandlingBean = new FyCaseHandling();
        String caseId = caseHandling.getCaseId();
        String operationType = caseHandling.getOperationType(); // 操作类型
                                                                // "01"为登记/"02"为填报
        // 获取当前用户
        TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);

        // 获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 设置日期格式
        String time = df.format(new Date()).toString();
        BeanUtils.copyProperties(fyCaseHandlingBean, caseHandling);
        StringUtils.setAddInfo(fyCaseHandlingBean, loginUser);

        if ("".equals(caseId) || null == caseId) {
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
            fyCaseHandlingBean.setCaseStatusCode(caseStatusCode);
            fyCaseHandlingBean.setCaseStatus(caseStatus);
        }
        fyCaseHandlingBean.setCreateTime(time);
        fyCaseHandlingBean.setCaseNum(TeeSysProps.getString("CASE_TEXT").replace("{1}", StringUtils.getUUId()));
        caseId = caseReviewMattersDao.addOrUpdateReviewMatters(fyCaseHandlingBean, caseHandling.getCaseId());
        resp.setCaseId(caseId);
        return resp;
    }

    /**
     * 根据caseId查询复议事项
     * 
     * @param caseId
     * @return
     */
    public FyCaseHandling getReviewMatters(String caseId) throws Exception {
        FyCaseHandling FyCaseHandlingVo = caseReviewMattersDao.findReviewMattersByCaseId(caseId);
        return FyCaseHandlingVo;
    }

    public void update(FyCaseHandling fyCaseHandling) {
        caseReviewMattersDao.update(fyCaseHandling);
    }

    public FyCaseHandling get(String id) {
        return caseReviewMattersDao.get(id);
    }
}
