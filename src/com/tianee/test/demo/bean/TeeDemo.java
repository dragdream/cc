package com.tianee.test.demo.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeeUserRole;

/**
 * 实体类
 * @author liteng
 *
 */
@Entity
@Table(name="DEMO")
public class TeeDemo {
	@Id
	@Column(name="SID")
	@GeneratedValue(generator="DEMO_seq_gen",strategy=GenerationType.AUTO)
	@SequenceGenerator(name="DEMO_seq_gen",sequenceName="DEMO_seq")
	private int sid;//主键自增ID
	
	@Column(name="USERNAME")
	private String userName;//用户名
	
	@Column(name="PASSWORD")
	private String passWord;//密码
	
	@Column(name="AGE")
	private int age;//年龄
	
	@Column(name="GENDER")
	private String gender;//性别
	
	@ManyToOne
	@JoinColumn(name="DEPT_ID")
	private TeeDepartment dept;//所属部门

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

	public TeeDepartment getDept() {
		return dept;
	}

	public void setDept(TeeDepartment dept) {
		this.dept = dept;
	}
	
	
	
}
