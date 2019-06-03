package com.tianee.test.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;


/**
 * DTO传输模型，用户前后台交互
 * @author liteng
 *
 */
public class TeeDemoModel {
	private int sid;//主键自增ID
	private String userName;//用户名
	private String passWord;//密码
	private int age;//年龄
	private String gender;//性别
	private int deptId;//所属部门ID
	private String deptName;//所属部门名称
	
	//附件集合，用于向前台展示业务数据的附件
	private List<TeeAttachmentModel> attachs = new ArrayList();
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public List<TeeAttachmentModel> getAttachs() {
		return attachs;
	}
	public void setAttachs(List<TeeAttachmentModel> attachs) {
		this.attachs = attachs;
	}
	
	
	
}
