package com.tianee.oa.core.workflow.flowrun.bean;
import org.hibernate.annotations.Index;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;

@Entity
@Table(name="DOC_NUM")
public class TeeDocNum {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="DOC_NUM_seq_gen")
	@SequenceGenerator(name="DOC_NUM_seq_gen", sequenceName="DOC_NUM_seq")
	private int sid;
	
	private String docName;//文号名称
	
	private int orderNo;
	
	private String docStyle;//文号样式
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<TeeDepartment> deptPriv = new HashSet<TeeDepartment>();
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<TeeUserRole> rolePriv = new HashSet<TeeUserRole>();
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<TeePerson> userPriv = new HashSet<TeePerson>();
	
	@Column(name="FLOW_IDS")
	private String flowIds = "";//绑定流程id串
	
	@Column(name="RESET_YEAR")
	private int resetYear = 1;//清零年限，表示几年进行清零
	
	@Column(name="RESET_STAMP")
	private int resetStamp;//清零年，主要标记哪年清零过
	
	@Column(name="CURR_NUM")
	private int currNum = 0;//当前文号值，从0开始

	
	@Column(name="COUNT_NUM")
	private int countNum = 0;//文号计数值，从0开始
	
	
	
	
	public int getCountNum() {
		return countNum;
	}

	public void setCountNum(int countNum) {
		this.countNum = countNum;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getDocStyle() {
		return docStyle;
	}

	public void setDocStyle(String docStyle) {
		this.docStyle = docStyle;
	}

	public Set<TeeDepartment> getDeptPriv() {
		return deptPriv;
	}

	public void setDeptPriv(Set<TeeDepartment> deptPriv) {
		this.deptPriv = deptPriv;
	}

	public Set<TeeUserRole> getRolePriv() {
		return rolePriv;
	}

	public void setRolePriv(Set<TeeUserRole> rolePriv) {
		this.rolePriv = rolePriv;
	}

	public Set<TeePerson> getUserPriv() {
		return userPriv;
	}

	public void setUserPriv(Set<TeePerson> userPriv) {
		this.userPriv = userPriv;
	}

	public void setFlowIds(String flowIds) {
		this.flowIds = flowIds;
	}

	public String getFlowIds() {
		return flowIds;
	}

	public int getResetYear() {
		return resetYear;
	}

	public void setResetYear(int resetYear) {
		this.resetYear = resetYear;
	}

	public int getResetStamp() {
		return resetStamp;
	}

	public void setResetStamp(int resetStamp) {
		this.resetStamp = resetStamp;
	}

	public int getCurrNum() {
		return currNum;
	}

	public void setCurrNum(int currNum) {
		this.currNum = currNum;
	}
	
}
