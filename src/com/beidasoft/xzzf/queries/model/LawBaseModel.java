package com.beidasoft.xzzf.queries.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.beidasoft.xzzf.queries.bean.LawBaseDetail;

public class LawBaseModel {
	    private String id;

	    private String name; // 名称

	    private String timeliness; // 时效性

	    private String submitlawLevel; // 法律法规类别

	    private String word; // 发文字号

	    private String organ; // 发布机关

	    private Date promulgation; // 颁布日期
 
	    private Date implementation; // 实施日期

	    private String remark; // 备注

	    private String examine; // 审核状态

	    private String isDelete; // 是否删除

	    private String createId; // 创建者

	    private String createTime; // 创建时间
 
	    private String lawPath; // 法律原文文件路径

	    private String lawNum; // 法律编号

	    private Date annulTime; // 废止日期
	    
	    private List<LawBaseDetail> lawDetail; //法律法规条目集合
	    
		private String lawDetailCode; // 法律条文编号
		
		private String law_strip; // 条

		private String fund; // 款

		private String item; // 项

		private String content; // 内容
		
		private List<Map> ss;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTimeliness() {
			return timeliness;
		}

		public void setTimeliness(String timeliness) {
			this.timeliness = timeliness;
		}

		public String getSubmitlawLevel() {
			return submitlawLevel;
		}

		public void setSubmitlawLevel(String submitlawLevel) {
			this.submitlawLevel = submitlawLevel;
		}

		public String getWord() {
			return word;
		}

		public void setWord(String word) {
			this.word = word;
		}

		public String getOrgan() {
			return organ;
		}

		public void setOrgan(String organ) {
			this.organ = organ;
		}

		public Date getPromulgation() {
			return promulgation;
		}

		public void setPromulgation(Date promulgation) {
			this.promulgation = promulgation;
		}

		public Date getImplementation() {
			return implementation;
		}

		public void setImplementation(Date implementation) {
			this.implementation = implementation;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getExamine() {
			return examine;
		}

		public void setExamine(String examine) {
			this.examine = examine;
		}

		public String getIsDelete() {
			return isDelete;
		}

		public void setIsDelete(String isDelete) {
			this.isDelete = isDelete;
		}

		public String getCreateId() {
			return createId;
		}

		public void setCreateId(String createId) {
			this.createId = createId;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getLawPath() {
			return lawPath;
		}

		public void setLawPath(String lawPath) {
			this.lawPath = lawPath;
		}

		
		public String getLawNum() {
			return lawNum;
		}

		public void setLawNum(String lawNum) {
			this.lawNum = lawNum;
		}

		public Date getAnnulTime() {
			return annulTime;
		}

		public void setAnnulTime(Date annulTime) {
			this.annulTime = annulTime;
		}

		public List<LawBaseDetail> getLawDetail() {
			return lawDetail;
		}

		public void setLawDetail(List<LawBaseDetail> lawDetail) {
			this.lawDetail = lawDetail;
		}

		public String getLawDetailCode() {
			return lawDetailCode;
		}

		public void setLawDetailCode(String lawDetailCode) {
			this.lawDetailCode = lawDetailCode;
		}

		public String getLaw_strip() {
			return law_strip;
		}

		public void setLaw_strip(String law_strip) {
			this.law_strip = law_strip;
		}

		public String getFund() {
			return fund;
		}

		public void setFund(String fund) {
			this.fund = fund;
		}

		public String getItem() {
			return item;
		}

		public void setItem(String item) {
			this.item = item;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public List<Map> getSs() {
			return ss;
		}

		public void setSs(List<Map> ss) {
			this.ss = ss;
		}
}
