package com.tianee.oa.subsys.weixin.model;

public class TeeWeiXinUserModel {
	private String userid;// 成员UserID。对应管理端的帐号，企业内必须唯一。长度为1~64个字节  
	private String name;// 成员名称。长度为0~64个字节  
	private String deparment;//员所属部门id列表。注意，每个部门的直属成员上限为1000个  
	private String mobile;//手机号
	private String position;//机号码。企业内必须唯一，mobile/weixinid/email三者不能同时为空  
	private String gender;//性别。1表示男性，2表示女性 
	private String email;// 邮箱。长度为0~64个字节。企业内必须唯一  
	private String weixinid;//否  微信号。企业内必须唯一。（注意：是微信号，不是微信的名字）  
	private String enable;///启用/禁用成员。1表示启用成员，0表示禁用成员  
	private String avatar_mediaid;//通过多媒体接口上传图片获得的mediaid  
	private String extattr;//扩展属性。扩展属性需要在WEB管理端创建后才生效，否则忽略未知属性的赋值  
	private String errcode;//错误代码

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDeparment() {
		return deparment;
	}
	public void setDeparment(String deparment) {
		this.deparment = deparment;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWeixinid() {
		return weixinid;
	}
	public void setWeixinid(String weixinid) {
		this.weixinid = weixinid;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getAvatar_mediaid() {
		return avatar_mediaid;
	}
	public void setAvatar_mediaid(String avatar_mediaid) {
		this.avatar_mediaid = avatar_mediaid;
	}
	public String getExtattr() {
		return extattr;
	}
	public void setExtattr(String extattr) {
		this.extattr = extattr;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	
}
