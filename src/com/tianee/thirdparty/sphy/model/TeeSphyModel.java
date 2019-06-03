package com.tianee.thirdparty.sphy.model;

/**
 * 视频会议
 * */

public class TeeSphyModel {
	
	private int sid;//自增id
	
	//会议名 
	private String roomName;
	
	//应用类型  1：远程教学 2：视频会议
	private int appType;
	
	//主题
	private String remark;
	
	//会议密码
	private String pwdNormal;
	
	//是否公开  0：不公开 1：公开
	private int isPublic;
	
	//带宽 64、128
	private int bandwidth;
	
	//最大用户数
	private int maxUser;
	
	//是否允许匿名 0:不允许 1：允许
	private int anonymous;
	
	//是否MCU合屏 0：分屏  1：合屏
	private int roomType;
	
	//开始时间yyyy-mm-dd HH:mm:ss
	private String startTime;
	
	//结束时间yyyy-mm-dd HH:mm:ss
	private String endTime;
	
	//会议议程
	private String agendaContent;
	
	//创建人
	private int createUser;
	private String createUserId;
	private String createUserName;
	//主持人
	private String hosts;
	
	private String hostsName;
	//助理
	private String assistant;
	
	private String assistantName;
	//普通用户
	private String puUser;
	
	private String puUserName;
	
	private int sphyId;//视频会议id
	
	private int hyId;

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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAgendaContent() {
		return agendaContent;
	}

	public void setAgendaContent(String agendaContent) {
		this.agendaContent = agendaContent;
	}

	public int getCreateUser() {
		return createUser;
	}

	public void setCreateUser(int createUser) {
		this.createUser = createUser;
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

	public String getHostsName() {
		return hostsName;
	}

	public void setHostsName(String hostsName) {
		this.hostsName = hostsName;
	}

	public String getAssistantName() {
		return assistantName;
	}

	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}

	public String getPuUserName() {
		return puUserName;
	}

	public void setPuUserName(String puUserName) {
		this.puUserName = puUserName;
	}

	public int getHyId() {
		return hyId;
	}

	public void setHyId(int hyId) {
		this.hyId = hyId;
	}

	
	
}
