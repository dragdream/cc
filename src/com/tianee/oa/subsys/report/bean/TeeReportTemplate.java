package com.tianee.oa.subsys.report.bean;
import org.hibernate.annotations.Index;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;

@Entity
@Table(name="REPORT_TPL")
public class TeeReportTemplate {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy=GenerationType.AUTO,generator="REPORT_TPL_seq_gen")
	@SequenceGenerator(name="REPORT_TPL_seq_gen", sequenceName="REPORT_TPL_seq")
	private int sid;
	
	@Column(name="TPL_NAME")
	private String tplName;
	
	@JoinColumn(name="CONDITION_")
	@ManyToOne
	@Index(name="IDX9a96e50e80344464adf89c9ae1b")
	private TeeReportCondition condition;
	
	@Column(name="GROUP_BY")
	private String groupBy;
	
	@Column(name="GRPBY_ORDER")
	private String groupByOrder;
	
	@Column(name="SORT_BY")
	private String sortBy;
	
	@Column(name="SORT_BY_ORDER")
	private String sortByOrder;
	
	@Column(name="PAGE_SIZE")
	private int pageSize;
	
	@Column(name="OPER_PRIV")
	private int operPriv;
	
	@Column(name="MERGE_GROUP")
	private int mergeGroup;//分组合并
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<TeePerson> userPriv = new HashSet();
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<TeeDepartment> deptPriv = new HashSet();
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX857fbe5ad9d040c98be25ea5ddf")
	@JoinColumn(name="FLOW_ID")
	private TeeFlowType flowType;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTplName() {
		return tplName;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	public TeeReportCondition getCondition() {
		return condition;
	}

	public void setCondition(TeeReportCondition condition) {
		this.condition = condition;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public String getGroupByOrder() {
		return groupByOrder;
	}

	public void setGroupByOrder(String groupByOrder) {
		this.groupByOrder = groupByOrder;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortByOrder() {
		return sortByOrder;
	}

	public void setSortByOrder(String sortByOrder) {
		this.sortByOrder = sortByOrder;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getOperPriv() {
		return operPriv;
	}

	public void setOperPriv(int operPriv) {
		this.operPriv = operPriv;
	}

	public Set<TeePerson> getUserPriv() {
		return userPriv;
	}

	public void setUserPriv(Set<TeePerson> userPriv) {
		this.userPriv = userPriv;
	}

	public Set<TeeDepartment> getDeptPriv() {
		return deptPriv;
	}

	public void setDeptPriv(Set<TeeDepartment> deptPriv) {
		this.deptPriv = deptPriv;
	}

	public TeeFlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(TeeFlowType flowType) {
		this.flowType = flowType;
	}

	public int getMergeGroup() {
		return mergeGroup;
	}

	public void setMergeGroup(int mergeGroup) {
		this.mergeGroup = mergeGroup;
	}
	
}
