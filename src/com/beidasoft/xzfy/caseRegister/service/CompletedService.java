package com.beidasoft.xzfy.caseRegister.service;

import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzfy.caseRegister.bean.FyApplicant;
import com.beidasoft.xzfy.caseRegister.bean.FyCaseHandling;
import com.beidasoft.xzfy.caseRegister.bean.FyCaseRegisterOriginal;
import com.beidasoft.xzfy.caseRegister.bean.FyLetter;
import com.beidasoft.xzfy.caseRegister.bean.FyMaterial;
import com.beidasoft.xzfy.caseRegister.bean.FyReception;
import com.beidasoft.xzfy.caseRegister.bean.FyRespondent;
import com.beidasoft.xzfy.caseRegister.bean.FyThirdParty;
import com.beidasoft.xzfy.caseRegister.dao.CaseFyApplicantDao;
import com.beidasoft.xzfy.caseRegister.dao.CaseFyLetterDao;
import com.beidasoft.xzfy.caseRegister.dao.CaseFyReceptionDao;
import com.beidasoft.xzfy.caseRegister.dao.CaseFyRespondentDao;
import com.beidasoft.xzfy.caseRegister.dao.CaseFyThirdPartyDao;
import com.beidasoft.xzfy.caseRegister.dao.CaseReviewMattersDao;
import com.beidasoft.xzfy.caseRegister.dao.CompletedDao;
import com.beidasoft.xzfy.caseRegister.model.registrationCompleted.request.CaseRegisteredReq;
import com.beidasoft.xzfy.caseRegister.model.registrationCompleted.response.CaseRegisteredResp;
import com.beidasoft.xzfy.utils.StringUtils;

@Service
public class CompletedService {

	@Autowired
	private CompletedDao completedDao;

	@Autowired
	private CaseFyLetterDao caseFyLetterDao;

	@Autowired
	private CaseFyApplicantDao caseFyApplicantDao;

	@Autowired
	private CaseFyReceptionDao caseFyReceptionDao;

	@Autowired
	private CaseFyRespondentDao caseFyRespondentDao;

	@Autowired
	private CaseFyThirdPartyDao caseFyThirdPartyDao;

	@Autowired
	private CaseReviewMattersDao caseReviewMattersDao;

	/**
	 * 登记完成
	 * 
	 * @param caseRegisteredReq
	 * @return
	 */
	public CaseRegisteredResp caseRegistered(CaseRegisteredReq caseRegisteredReq) throws Exception {

		CaseRegisteredResp resp = new CaseRegisteredResp();
		FyCaseRegisterOriginal fyCaseRegisterOriginal = new FyCaseRegisterOriginal();
		String caseId = caseRegisteredReq.getCaseId();

		// 来件信息
		FyLetter fyLetterBean = caseFyLetterDao.getFyLetterByCaseId(caseId);
		// 接待信息
		FyReception fyReceptionBean = caseFyReceptionDao
				.getFyReceptionByCaseId(caseId);
		// 申请人信息
		List<FyApplicant> listFyApplicant = caseFyApplicantDao
				.findFyApplicantByCaseId(caseId);
		// 被申请人信息
		List<FyRespondent> listFyRespondent = caseFyRespondentDao
				.findFyRespondentByCaseId(caseId);
		// 第三人信息
		List<FyThirdParty> listFyThirdParty = caseFyThirdPartyDao
				.findFyThirdPartyByCaseId(caseId);
		// 复议事项
		FyCaseHandling FyCaseHandlingBean = caseReviewMattersDao
				.findReviewMattersByCaseId(caseId);
		// 案件材料 
		FyMaterial FyMaterialBean = null;
		
		String jsonFyLetter = JSONArray.fromObject(fyLetterBean).toString();
		String jsonFyReception = JSONArray.fromObject(fyReceptionBean).toString();
		String jsonFyApplicant = JSONArray.fromObject(listFyApplicant).toString();
		String jsonFyRespondent = JSONArray.fromObject(listFyRespondent).toString();
		String jsonFyThirdParty = JSONArray.fromObject(listFyThirdParty).toString();
		String jsonFyCaseHandling = JSONArray.fromObject(FyCaseHandlingBean).toString();
		String jsonFyMaterial = JSONArray.fromObject(FyMaterialBean).toString();
		
		fyCaseRegisterOriginal.setId(StringUtils.getUUId());
		fyCaseRegisterOriginal.setCaseId(caseId);
		fyCaseRegisterOriginal.setLetterInfo(jsonFyLetter);
		fyCaseRegisterOriginal.setReceptionInfo(jsonFyReception);
		fyCaseRegisterOriginal.setApplicantInfo(jsonFyApplicant);
		fyCaseRegisterOriginal.setRespondentInfo(jsonFyRespondent);
		fyCaseRegisterOriginal.setThirdPartyInfo(jsonFyThirdParty);
		fyCaseRegisterOriginal.setCaseHandlingInfo(jsonFyCaseHandling);
		fyCaseRegisterOriginal.setCaseHandlingInfo(jsonFyCaseHandling);
		fyCaseRegisterOriginal.setMaterialInfo(jsonFyMaterial);
//		fyCaseRegisterOriginal.setCreatedUserId(createdUserId);
//		fyCaseRegisterOriginal.setCreatedUser(createdUser);
//		fyCaseRegisterOriginal.setCreateTime(createTime);
//		fyCaseRegisterOriginal.setModifiedUserId(modifiedUserId);
//		fyCaseRegisterOriginal.setModifiedUser(modifiedUser);
//		fyCaseRegisterOriginal.setModifiedTime(modifiedTime);
		completedDao.addOrUpdateCaseRegistered(fyCaseRegisterOriginal);
		resp.setCaseId(caseId);
		return resp;
	}
}
