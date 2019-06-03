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
import com.beidasoft.xzfy.caseRegister.bean.FyLetter;
import com.beidasoft.xzfy.caseRegister.dao.CaseFyLetterDao;
import com.beidasoft.xzfy.caseRegister.model.caseInfo.response.entity.Letter;
import com.beidasoft.xzfy.caseRegister.model.caseManager.response.AddFyLetterResp;
import com.beidasoft.xzfy.utils.Const;
import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;

@Service
public class CaseFyLetterService {

    @Autowired
    private CaseFyLetterDao caseFyLetterDao;

    @Autowired
    private CaseInfoService caseInfoService;

    /**
     * 来件信息登记
     * 
     * @param addFyLetterReq
     * @param request
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Transactional
    public AddFyLetterResp addOrUpdateFyLetter(Letter letter, HttpServletRequest request) throws Exception {

        AddFyLetterResp resp = new AddFyLetterResp();
        FyLetter fyLetterBean = new FyLetter();

        String operationType = letter.getOperationType(); // 操作类型
                                                          // "01"为登记/"02"为填报

        // 获取当前用户
        TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);

        // 获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 设置日期格式
        String time = df.format(new Date()).toString();
        String caseId = letter.getCaseId();
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
            fyCaseHandlingBean.setApplicationTypeCode(letter.getApplicationTypeCode());
            fyCaseHandlingBean.setApplicationType(letter.getApplicationType());

            // 初始化案件信息
            caseInfoService.initCaseInfo(fyCaseHandlingBean);
        }
        BeanUtils.copyProperties(fyLetterBean, letter);
        StringUtils.setAddInfo(fyLetterBean, loginUser);
        fyLetterBean.setCreateTime(time);
        caseFyLetterDao.addOrUpdateFyLetter(fyLetterBean, caseId);
        resp.setCaseId(caseId);
        return resp;
    }

    /**
     * 根据caseId查询来件信息
     * 
     * @param caseId
     * @return
     */
    public FyLetter getFyLetterByCaseId(String caseId) {
        FyLetter fyLetterVo = caseFyLetterDao.getFyLetterByCaseId(caseId);
        return fyLetterVo;
    }

}
