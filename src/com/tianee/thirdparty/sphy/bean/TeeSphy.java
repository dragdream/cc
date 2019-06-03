package com.tianee.thirdparty.sphy.bean;

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
import com.tianee.oa.core.org.bean.TeePerson;
/**
 * 视频会议
 * */
@Entity
@Table(name="SPHY")
public class TeeSphy {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SPHY_seq_gen")
	@SequenceGenerator(name="SPHY_seq_gen", sequenceName="SPHY_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	//会议名 
	@Column(name="ROOM_NAME")
	private String roomName;
	
	//应用类型  1：远程教学 2：视频会议
	@Column(name="APP_TYPE")
	private int appType;
	
	//主题
	@Column(name="REMARK_")
	private String remark;
	
	//会议密码
	@Column(name="PWD_NORMAL")
	private String pwdNormal;
	
	//是否公开  0：不公开 1：公开
	@Column(name="IS_PUBLIC")
	private int isPublic;
	
	//带宽 64、128
	@Column(name="BAND_WIDTH")
	private int bandwidth;
	
	//最大用户数
	@Column(name="MAX_USER")
	private int maxUser;
	
	//是否允许匿名 0:不允许 1：允许
	@Column(name="ANONYMOUS")
	private int anonymous;
	
	//是否MCU合屏 0：分屏  1：合屏
	@Column(name="ROOM_TYPE")
	private int roomType;
	
	//开始时间yyyy-mm-dd HH:mm:ss
	@Column(name="START_TIME")
	private Date startTime;
	
	//结束时间yyyy-mm-dd HH:mm:ss
	@Column(name="END_TIME")
	private Date endTime;
	
	//会议议程
	@Column(name="AGENDA_CONTENT")
	private String agendaContent;
	
	//创建人
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CREATE_USER")
	private TeePerson createUser;
	
	//主持人
	@Column(name="HOUTS")
	private String hosts;
	
	//助理
	@Column(name="ASSISTANT")
	private String assistant;
	
	//普通用户
	@Column(name="PU_USER")
	private String puUser;
	
	@Column(name="SPHY_ID")
	private int sphyId;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getAppType() {
		return appType;
	}

	public void setAppType(int appType) {
		this.appType = appType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPwdNormal() {
		return pwdNormal;
	}

	public void setPwdNormal(String pwdNormal) {
		this.pwdNormal = pwdNormal;
	}

	public int getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(int isPublic) {
		this.isPublic = isPublic;
	}

	public int getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(int bandwidth) {
		this.bandwidth = bandwidth;
	}

	public int getMaxUser() {
		return maxUser;
	}

	public void setMaxUser(int maxUser) {
		this.maxUser = maxUser;
	}

	public int getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(int anonymous) {
		this.anonymous = anonymous;
	}

	

	public int getRoomType() {
		return roomType;
	}

	public void setRoomType(int roomType) {
		this.roomType = roomType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getAgendaContent() {
		return agendaContent;
	}

	public void setAgendaContent(String agendaContent) {
		this.agendaContent = agendaContent;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public String getHosts() {
		return hosts;
	}

	public void setHosts(String hosts) {
		this.hosts = hosts;
	}

	public String getAssistant() {
		return assistant;
	}

	public void setAssistant(String assistant) {
		this.assistant = assistant;
	}

	public String getPuUser() {
		return puUser;
	}

	public void setPuUser(String puUser) {
		this.puUser = puUser;
	}

	public int getSphyId() {
		return sphyId;
	}

	public void setSphyId(int sphyId) {
		this.sphyId = sphyId;
	}
	
	
	
}
