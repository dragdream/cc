package com.tianee.oa.core.base.weibo.model;

public class TeeWeibGuanZhuModel {

    private int sid;//关注主键
	
	private int userId;//关注人id
	
	private String userName;//关注人姓名
	
	private String deptName;//关注人部门名称
	
	private int personId;//被关注人id
	
	private String creTime;//评论时间
	
	private boolean guanZhu=false;//是否关注
	
	private int countPeson;//关注人数
	
	private int countByPerson;//被关注的人数
	
	private int countPublish;//当前登录人发布的微博数
	
	private int avatar;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getCreTime() {
		return creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}

	public boolean isGuanZhu() {
		return guanZhu;
	}

	public void setGuanZhu(boolean guanZhu) {
		this.guanZhu = guanZhu;
	}

	public int getCountPeson() {
		return countPeson;
	}

	public void setCountPeson(int countPeson) {
		this.countPeson = countPeson;
	}

	public int getCountByPerson() {
		return countByPerson;
	}

	public void setCountByPerson(int countByPerson) {
		this.countByPerson = countByPerson;
	}

	public int getCountPublish() {
		return countPublish;
	}

	public void setCountPublish(int countPublish) {
		this.countPublish = countPublish;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}
	
	
}
