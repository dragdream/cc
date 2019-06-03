package com.beidasoft.xzzf.punish.document.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.beidasoft.xzzf.punish.document.bean.DocEvidencedescriptionDetail;
import com.beidasoft.xzzf.punish.document.util.DocCommonUtils;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;


public class EvidencedescriptionModel {

	/**
	 * 取证情况及证据说明Model类
	 */
	
	private String id;

    // 当事人姓名
    private String clientName;

    // 取证内容（证据一）
    private String evidence1;

    // 取证方法及过程证据（证据一）
    private String method1;

    // 证明对象证据（证据一）
    private String certifier1;

    // 页数证据（证据一）
    private String page1;

    // 取证内容证据（证据二）
    private String evidence2;

    // 取证方法及过程证据（证据二）
    private String method2;

    // 证明对象证据（证据二）
    private String certifier2;

    // 页数证据（证据二）
    private String page2;

    // 取证内容(证据三)
    private String evidence3;

    // 取证方法及过程(证据三)
    private String method3;

    // 证明对象(证据三)
    private String certifier3;

    // 页数(证据三)
    private String page3;

    // 取证内容（证据四）
    private String evidence4;

    // 取证方法及过程（证据四）
    private String method4;

    // 证明对象（证据四）
    private String certifier4;

    // 页数（证据四）
    private String page4;

    // 取证内容（证据五）
    private String evidence5;

    // 取证方法及过程（证据五）
    private String method5;

    // 证明对象（证据五）
    private String certifier5;

    // 页数（证据五）
    private String page5;

    // 取证时间(开始)
    private String evidenceStartTimeStr;

    // 取证时间(结束)
    private String evidenceEndTimeStr;

    //执法人盖章时间
    private String enforcementStampDataStr;
    
    //当事人盖章时间
    private String clientStampDataStr;
    
    // 取证地点
    private String evidenceAddr;

    // 取证人
    private String evidencePerson;

    // 当事人现场负责人或受托人意见
    private String clientIdea;
    
    // 当事人现场负责人或受托人意见图
    private String clientIdeaSignatureBase64;
    
    // 当事人现场负责人或受托人意见值
    private String clientIdeaSignatureValue;
    
    // 当事人现场负责人或受托人意见位置
    private String clientIdeaSignaturePlace;
    
    // 当事人现场负责人或受托人签名
    private String clientPersonSignatureBase64;

    // 当事人现场负责人或受托人签名值
    private String clientPersonSignatureValue;
    
    // 当事人现场负责人或受托人签名位置
    private String clientPersonSignaturePlace;
    
    //是否备注
    private String isRemarks;
    
    //备注
    private String remarks;
    
    // 当事人签名图片
    private String clientSignatureBase64;

    // 当事人签名值
    private String clientSignatureValue;

    // 当事人签名位置
    private String clientSignaturePlace;

    // 主办人签名图片
    private String majorSignatureBase64;

    // 主办人签名值
    private String majorSignatureValue;

    // 主办人签名位置
    private String majorSignaturePlace;

    // 协办人签名图片
    private String minorSignatureBase64;

    // 协办人签名值
    private String minorSignatureValue;

    // 协办人签名位置
    private String minorSignaturePlace;

    // 附件地址
    private String prosessUnit;

    // 删除标记
    private String delFlg;

    // 执法办案唯一编号
    private String baseId;

    // 执法环节ID
    private String lawLinkId;

    // 变更人ID
    private String updateUserId;

    // 变更人姓名
    private String updateUserName;

    // 变更时间
    private String updateTimeStr;

    //证据字符串
    private String attaches;
    
    private List<DocEvidencedescriptionDetail> detailList;
    
    List<List<TeeAttachmentModel>> attachModelList; 
    
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getClientName() {
		return clientName;
	}


	public void setClientName(String clientName) {
		this.clientName = clientName;
	}


	public String getEvidence1() {
		return evidence1;
	}


	public void setEvidence1(String evidence1) {
		this.evidence1 = evidence1;
	}


	public String getMethod1() {
		return method1;
	}


	public void setMethod1(String method1) {
		this.method1 = method1;
	}


	public String getCertifier1() {
		return certifier1;
	}


	public void setCertifier1(String certifier1) {
		this.certifier1 = certifier1;
	}


	public String getPage1() {
		return page1;
	}


	public void setPage1(String page1) {
		this.page1 = page1;
	}


	public String getEvidence2() {
		return evidence2;
	}


	public void setEvidence2(String evidence2) {
		this.evidence2 = evidence2;
	}


	public String getMethod2() {
		return method2;
	}


	public void setMethod2(String method2) {
		this.method2 = method2;
	}


	public String getCertifier2() {
		return certifier2;
	}


	public void setCertifier2(String certifier2) {
		this.certifier2 = certifier2;
	}


	public String getPage2() {
		return page2;
	}


	public void setPage2(String page2) {
		this.page2 = page2;
	}


	public String getEvidence3() {
		return evidence3;
	}


	public void setEvidence3(String evidence3) {
		this.evidence3 = evidence3;
	}


	public String getMethod3() {
		return method3;
	}


	public void setMethod3(String method3) {
		this.method3 = method3;
	}


	public String getCertifier3() {
		return certifier3;
	}


	public void setCertifier3(String certifier3) {
		this.certifier3 = certifier3;
	}


	public String getPage3() {
		return page3;
	}


	public void setPage3(String page3) {
		this.page3 = page3;
	}


	public String getEvidence4() {
		return evidence4;
	}


	public void setEvidence4(String evidence4) {
		this.evidence4 = evidence4;
	}


	public String getMethod4() {
		return method4;
	}


	public void setMethod4(String method4) {
		this.method4 = method4;
	}


	public String getCertifier4() {
		return certifier4;
	}


	public void setCertifier4(String certifier4) {
		this.certifier4 = certifier4;
	}


	public String getPage4() {
		return page4;
	}


	public void setPage4(String page4) {
		this.page4 = page4;
	}


	public String getEvidence5() {
		return evidence5;
	}


	public void setEvidence5(String evidence5) {
		this.evidence5 = evidence5;
	}


	public String getMethod5() {
		return method5;
	}


	public void setMethod5(String method5) {
		this.method5 = method5;
	}


	public String getCertifier5() {
		return certifier5;
	}


	public void setCertifier5(String certifier5) {
		this.certifier5 = certifier5;
	}


	public String getPage5() {
		return page5;
	}


	public void setPage5(String page5) {
		this.page5 = page5;
	}


	public String getEvidenceStartTimeStr() {
		return evidenceStartTimeStr;
	}


	public void setEvidenceStartTimeStr(String evidenceStartTimeStr) {
		this.evidenceStartTimeStr = evidenceStartTimeStr;
	}


	public String getEvidenceEndTimeStr() {
		return evidenceEndTimeStr;
	}


	public void setEvidenceEndTimeStr(String evidenceEndTimeStr) {
		this.evidenceEndTimeStr = evidenceEndTimeStr;
	}


	public String getEnforcementStampDataStr() {
		return enforcementStampDataStr;
	}


	public void setEnforcementStampDataStr(String enforcementStampDataStr) {
		this.enforcementStampDataStr = enforcementStampDataStr;
	}


	public String getClientStampDataStr() {
		return clientStampDataStr;
	}


	public void setClientStampDataStr(String clientStampDataStr) {
		this.clientStampDataStr = clientStampDataStr;
	}


	public String getEvidenceAddr() {
		return evidenceAddr;
	}


	public void setEvidenceAddr(String evidenceAddr) {
		this.evidenceAddr = evidenceAddr;
	}


	public String getEvidencePerson() {
		return evidencePerson;
	}


	public void setEvidencePerson(String evidencePerson) {
		this.evidencePerson = evidencePerson;
	}


	public String getClientIdea() {
		return clientIdea;
	}


	public void setClientIdea(String clientIdea) {
		this.clientIdea = clientIdea;
	}


	public String getClientSignatureBase64() {
		return clientSignatureBase64;
	}


	public void setClientSignatureBase64(String clientSignatureBase64) {
		this.clientSignatureBase64 = clientSignatureBase64;
	}


	public String getClientSignatureValue() {
		return clientSignatureValue;
	}


	public void setClientSignatureValue(String clientSignatureValue) {
		this.clientSignatureValue = clientSignatureValue;
	}


	public String getClientSignaturePlace() {
		return clientSignaturePlace;
	}


	public void setClientSignaturePlace(String clientSignaturePlace) {
		this.clientSignaturePlace = clientSignaturePlace;
	}


	public String getMajorSignatureBase64() {
		return majorSignatureBase64;
	}


	public void setMajorSignatureBase64(String majorSignatureBase64) {
		this.majorSignatureBase64 = majorSignatureBase64;
	}


	public String getMajorSignatureValue() {
		return majorSignatureValue;
	}


	public void setMajorSignatureValue(String majorSignatureValue) {
		this.majorSignatureValue = majorSignatureValue;
	}


	public String getMajorSignaturePlace() {
		return majorSignaturePlace;
	}


	public void setMajorSignaturePlace(String majorSignaturePlace) {
		this.majorSignaturePlace = majorSignaturePlace;
	}


	public String getMinorSignatureBase64() {
		return minorSignatureBase64;
	}


	public void setMinorSignatureBase64(String minorSignatureBase64) {
		this.minorSignatureBase64 = minorSignatureBase64;
	}


	public String getMinorSignatureValue() {
		return minorSignatureValue;
	}


	public void setMinorSignatureValue(String minorSignatureValue) {
		this.minorSignatureValue = minorSignatureValue;
	}


	public String getMinorSignaturePlace() {
		return minorSignaturePlace;
	}


	public void setMinorSignaturePlace(String minorSignaturePlace) {
		this.minorSignaturePlace = minorSignaturePlace;
	}


	public String getProsessUnit() {
		return prosessUnit;
	}


	public void setProsessUnit(String prosessUnit) {
		this.prosessUnit = prosessUnit;
	}


	public String getDelFlg() {
		return delFlg;
	}


	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}


	public String getBaseId() {
		return baseId;
	}


	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}


	public String getLawLinkId() {
		return lawLinkId;
	}


	public void setLawLinkId(String lawLinkId) {
		this.lawLinkId = lawLinkId;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}


	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}


	public String getClientIdeaSignatureBase64() {
		return clientIdeaSignatureBase64;
	}


	public void setClientIdeaSignatureBase64(String clientIdeaSignatureBase64) {
		this.clientIdeaSignatureBase64 = clientIdeaSignatureBase64;
	}


	public String getClientIdeaSignatureValue() {
		return clientIdeaSignatureValue;
	}


	public void setClientIdeaSignatureValue(String clientIdeaSignatureValue) {
		this.clientIdeaSignatureValue = clientIdeaSignatureValue;
	}


	public String getClientIdeaSignaturePlace() {
		return clientIdeaSignaturePlace;
	}


	public void setClientIdeaSignaturePlace(String clientIdeaSignaturePlace) {
		this.clientIdeaSignaturePlace = clientIdeaSignaturePlace;
	}


	public String getClientPersonSignatureBase64() {
		return clientPersonSignatureBase64;
	}


	public void setClientPersonSignatureBase64(String clientPersonSignatureBase64) {
		this.clientPersonSignatureBase64 = clientPersonSignatureBase64;
	}


	public String getClientPersonSignatureValue() {
		return clientPersonSignatureValue;
	}


	public void setClientPersonSignatureValue(String clientPersonSignatureValue) {
		this.clientPersonSignatureValue = clientPersonSignatureValue;
	}


	public String getClientPersonSignaturePlace() {
		return clientPersonSignaturePlace;
	}


	public void setClientPersonSignaturePlace(String clientPersonSignaturePlace) {
		this.clientPersonSignaturePlace = clientPersonSignaturePlace;
	}


	public String getUpdateUserName() {
		return updateUserName;
	}


	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}


	public String getUpdateTimeStr() {
		return updateTimeStr;
	}


	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public String getAttaches() {
		return attaches;
	}

	public void setAttaches(String attaches) {
		this.attaches = attaches;
	}

	public List<DocEvidencedescriptionDetail> getDetailList() {
		return detailList;
	}


	public void setDetailList(List<DocEvidencedescriptionDetail> detailList) {
		this.detailList = detailList;
	}

	public List<List<TeeAttachmentModel>> getAttachModelList() {
		return attachModelList;
	}


	public String getIsRemarks() {
		return isRemarks;
	}


	public void setIsRemarks(String isRemarks) {
		this.isRemarks = isRemarks;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public void setAttachModelList(List<List<TeeAttachmentModel>> attachModelList) {
		this.attachModelList = attachModelList;
	}


			//预览
			public Map<String, String> getDocInfo(String caseCode) {
				// 文书内容
				Map<String, String> content = new HashMap<String, String>();
				// 当事人
				content.put("当事人", clientName);
			    // 证据一取证内容
				content.put("证据一取证内容", evidence1);
				// 证据一取证方法及过程
				content.put("证据一取证方法及过程", method1);
				
				//证据一证明对象
				content.put("证据一证明对象", certifier1);
				
				//证据一页数
				content.put("证据一页数", page1);
				
				//证据二取证内容
				content.put("证据二取证内容", evidence2);
				
				//证据二取证方法及过程
				content.put("证据二取证方法及过程", method2);

				//证据二证明对象
				content.put("证据二证明对象", certifier2);
				
				//证据二页数
				content.put("证据二页数", page2);

				//证据三取证内容
				content.put("证据三取证内容", evidence3);
				
				//证据三取证方法及过程
				content.put("证据三取证方法及过程", method3);
				
				//证据三证明对象
				content.put("证据三证明对象", certifier3);
				
				//证据三页数
				content.put("证据三页数", page3);
				
				
				//证据四取证内容
				content.put("证据四取证内容", evidence4);

				//证据四取证方法及过程
				content.put("证据四取证方法及过程", method4);
				
				//证据四证明对象
				content.put("证据四证明对象", certifier4);

				//证据四页数
				content.put("证据四页数", page4);
				
				//证据五取证内容
				content.put("证据五取证内容", evidence5);
				
				//证据五取证方法及过程
				content.put("证据五取证方法及过程", method5);
				
				//证据五证明对象
				content.put("证据五证明对象", certifier5);
				
				//证据五页数
				content.put("证据五页数", page5);
				
				//取证时间
				content.put("取证时间", evidenceStartTimeStr);
				
				//取证地点
				content.put("取证地点", evidenceAddr);
				
				//取证人
				content.put("取证人", evidencePerson);
				
				//当事人现场负责人或受托人意见
				content.put("当事人现场负责人或受托人意见", clientIdea);
				
				//当事人现场负责人或受托人签名
				content.put("当事人现场负责人或受托人签名", clientSignatureBase64);
				
				// 主办人签名
				content.put("执法人1签名", DocCommonUtils.cut(majorSignatureBase64));
				// 协办人签名
				content.put("执法人2签名",  DocCommonUtils.cut(minorSignatureBase64));

				//执法人签字时间
				content.put("执法人签字时间", enforcementStampDataStr);
				
				//签字时间
				content.put("签字时间", clientStampDataStr);
				
				// 页眉
				content.put("页眉", caseCode);
				
				return content;
			}
    
}
