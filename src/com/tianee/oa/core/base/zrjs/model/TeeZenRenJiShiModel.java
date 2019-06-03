package com.tianee.oa.core.base.zrjs.model;

import java.util.ArrayList;
import java.util.List;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class TeeZenRenJiShiModel {
		private int sid;
		
		private int deptId;//部门id 
		
		private String deptName;//部门名称
		
		private String jhEndTime;//计划完成时间
		
		private String sjEndTime;//时间完成时间
		
		private String zrSxName;//责任事项
		
		private String address;//地点
		
		private String content;//内容主题
		
		private String rease;//摘要
		
		private String daclContent;//档案材料
		
        private int status;//是否已完成  0否 1是
		
		private int userId;//责任人签名ID
		
		private String userName;//责任人姓名
		
		private String leaderQm;//领导签名
		
		private String attachIds;//附件ID
		
		List<TeeAttachmentModel> attach=new ArrayList<TeeAttachmentModel>();
		
		private String leaderId;//领导id

		public int getSid() {
			return sid;
		}

		public int getDeptId() {
			return deptId;
		}

		public String getDeptName() {
			return deptName;
		}

		public String getJhEndTime() {
			return jhEndTime;
		}

		public String getSjEndTime() {
			return sjEndTime;
		}

		public String getZrSxName() {
			return zrSxName;
		}

		public String getAddress() {
			return address;
		}

		public String getContent() {
			return content;
		}

		public String getRease() {
			return rease;
		}

		public String getDaclContent() {
			return daclContent;
		}

		public int getStatus() {
			return status;
		}

		public int getUserId() {
			return userId;
		}

		public String getUserName() {
			return userName;
		}

		public String getLeaderQm() {
			return leaderQm;
		}

		public String getAttachIds() {
			return attachIds;
		}

		public void setSid(int sid) {
			this.sid = sid;
		}

		public void setDeptId(int deptId) {
			this.deptId = deptId;
		}

		public void setDeptName(String deptName) {
			this.deptName = deptName;
		}

		public void setJhEndTime(String jhEndTime) {
			this.jhEndTime = jhEndTime;
		}

		public void setSjEndTime(String sjEndTime) {
			this.sjEndTime = sjEndTime;
		}

		public void setZrSxName(String zrSxName) {
			this.zrSxName = zrSxName;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public void setRease(String rease) {
			this.rease = rease;
		}

		public void setDaclContent(String daclContent) {
			this.daclContent = daclContent;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public void setLeaderQm(String leaderQm) {
			this.leaderQm = leaderQm;
		}

		public void setAttachIds(String attachIds) {
			this.attachIds = attachIds;
		}

		public String getLeaderId() {
			return leaderId;
		}

		public void setLeaderId(String leaderId) {
			this.leaderId = leaderId;
		}

		public List<TeeAttachmentModel> getAttach() {
			return attach;
		}

		public void setAttach(List<TeeAttachmentModel> attach) {
			this.attach = attach;
		}



		
}
