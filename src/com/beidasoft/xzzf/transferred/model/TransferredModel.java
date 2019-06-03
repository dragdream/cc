package com.beidasoft.xzzf.transferred.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
/**
 * @author fanxi 
 * 执法文书管理

 */
public class TransferredModel {
		
		//执法文书管理唯一ID
		private String id;
		
		//案件编号
		private int transId;
		
		//当事人类型
		private String partyType;
		
		//当事人
		private String partyName;
		
		//案件办结日期
		private String transferredTransTimeStr;
		
		//执行单位
		private String performUnit;
		
		//案件唯一编号
		private String baseId;

		// 创建人ID
	    private String createUserId;

	    // 创建人姓名
	    private String createUserName;

	    // 创建时间
	    private Date createTime;

	    // 变更人ID
	    private String updateUserId;

	    // 变更人姓名
	    private String updateUserName;

	    // 变更时间
	    private Date updateTime;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getPartyType() {
			return partyType;
		}

		public void setPartyType(String partyType) {
			this.partyType = partyType;
		}

		public String getPartyName() {
			return partyName;
		}

		public void setPartyName(String partyName) {
			this.partyName = partyName;
		}

		

	

		public String getTransferredTransTimeStr() {
			return transferredTransTimeStr;
		}

		public void setTransferredTransTimeStr(String transferredTransTimeStr) {
			this.transferredTransTimeStr = transferredTransTimeStr;
		}

		public String getPerformUnit() {
			return performUnit;
		}

		public void setPerformUnit(String performUnit) {
			this.performUnit = performUnit;
		}

		public String getBaseId() {
			return baseId;
		}

		public void setBaseId(String baseId) {
			this.baseId = baseId;
		}

		public String getCreateUserId() {
			return createUserId;
		}

		public void setCreateUserId(String createUserId) {
			this.createUserId = createUserId;
		}

		public String getCreateUserName() {
			return createUserName;
		}

		public void setCreateUserName(String createUserName) {
			this.createUserName = createUserName;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public String getUpdateUserId() {
			return updateUserId;
		}

		public void setUpdateUserId(String updateUserId) {
			this.updateUserId = updateUserId;
		}

		public String getUpdateUserName() {
			return updateUserName;
		}

		public void setUpdateUserName(String updateUserName) {
			this.updateUserName = updateUserName;
		}

		public Date getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}

		public int getTransId() {
			return transId;
		}

		public void setTransId(int transId) {
			this.transId = transId;
		}
	    
	    
		
}
