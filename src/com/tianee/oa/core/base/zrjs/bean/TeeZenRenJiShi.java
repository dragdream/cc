package com.tianee.oa.core.base.zrjs.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="ZENREN_JISHI")
public class TeeZenRenJiShi {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO, generator="ZENREN_JISHI_seq_gen")
		@SequenceGenerator(name="ZENREN_JISHI_seq_gen", sequenceName="ZENREN_JISHI_seq")
		@Column(name="SID")
		private int sid;
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="DEPT_ID")
		private TeeDepartment dept;//部门
		
		@Column(name="JH_END_TIME")
		private Date jhEndTime;//计划完成时间
		
		@Column(name="SJ_END_TIME")
		private Date sjEndTime;//时间完成时间
		
		@Column(name="ZRSX_NAME")
		private String zrSxName;//责任事项
		
		@Column(name="ADDRESS")
		private String address;//地点
		
		@Column(name="CONTENT")
		private String content;//内容主题
		
		@Column(name="SEASE")
		private String rease;//摘要
		
		@Column(name="DACL_CONTENT")
		private String daclContent;//档案材料
		
		@Column(name="STATUS")
        private int status;//是否已完成  0否 1是
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="USER_ID")
		private TeePerson user;//责任人签名
		
		@Column(name="LEADER_QM")
		private String leaderQm;//领导签名
		
		@Column(name="ATTACH_IDS")
		private String attachIds;//附件字符串
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="CREATE_USER_ID")
		private TeePerson createUser;//创建人

		public int getSid() {
			return sid;
		}

		public TeeDepartment getDept() {
			return dept;
		}

		public Date getJhEndTime() {
			return jhEndTime;
		}

		public Date getSjEndTime() {
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

		public TeePerson getUser() {
			return user;
		}

		public String getLeaderQm() {
			return leaderQm;
		}

		public void setSid(int sid) {
			this.sid = sid;
		}

		public void setDept(TeeDepartment dept) {
			this.dept = dept;
		}

		public void setJhEndTime(Date jhEndTime) {
			this.jhEndTime = jhEndTime;
		}

		public void setSjEndTime(Date sjEndTime) {
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

		public void setUser(TeePerson user) {
			this.user = user;
		}

		public void setLeaderQm(String leaderQm) {
			this.leaderQm = leaderQm;
		}

		public String getAttachIds() {
			return attachIds;
		}

		public void setAttachIds(String attachIds) {
			this.attachIds = attachIds;
		}

		public TeePerson getCreateUser() {
			return createUser;
		}

		public void setCreateUser(TeePerson createUser) {
			this.createUser = createUser;
		}
		
		
}
