package com.beidasoft.gzpt.demo.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * demo案件表
 */
@Entity
@Table(name="MENU_BUTTON_CASE_INFO")
public class DemoCaseInfo {
    @Id
    @Column(name = "ID")
    private int id; // 法律法规ID

    @Column(name = "NAME")
    private String name; // 名称

    @Column(name = "NUM")
    private String NUM; // 编号

    @Column(name = "STATUS")
    private String status; // 状态

    @Column(name = "MEMO")
    private String memo; // 备注

    @Column(name = "PROP1")
    private String prop1; // 备用1

    @Column(name = "PROP2")
    private String prop2; // 备用2

    @Column(name = "ADDTIME")
    private Date addtime; // 添加时间

    @Column(name = "ADDUSER")
    private String adduser; // 添加人

    @Column(name = "ISDELETE")
    private int isDelete; // 是否删除

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNUM() {
		return NUM;
	}

	public void setNUM(String nUM) {
		NUM = nUM;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getProp1() {
		return prop1;
	}

	public void setProp1(String prop1) {
		this.prop1 = prop1;
	}

	public String getProp2() {
		return prop2;
	}

	public void setProp2(String prop2) {
		this.prop2 = prop2;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAdduser() {
		return adduser;
	}

	public void setAdduser(String adduser) {
		this.adduser = adduser;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
    
}
