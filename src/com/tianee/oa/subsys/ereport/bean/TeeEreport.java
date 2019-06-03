package com.tianee.oa.subsys.ereport.bean;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.subsys.bisengin.bean.BisView;
import com.tianee.oa.subsys.report.bean.TeeSeniorReportCat;

@Entity
@Table(name="E_REPORT")
public class TeeEreport {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="E_REPORT_seq_gen")
	@SequenceGenerator(name="E_REPORT_seq_gen", sequenceName="E_REPORT_seq")
	private int sid;
	
	@Column(name="TITLE")
	private String title;//报表标题
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CAT_ID")
	private TeeSeniorReportCat cat;//报表分类
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CR_USER_ID")
	private TeePerson crUser;//创建人
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BIS_VIEW")
	private BisView bisView ;//所属视图
	
	
	@Column(name="CHART_TYPE")
	private int chartType;//图标类型
	
	
	@Column(name="DIMENSION")
	private String dimension;//维度
	
	
	@Column(name="SPINDLE")
	private String spindle;//主轴数值
	
	
	@Column(name="SHAFT")
	private String shaft;//次轴数值
	
	
	@Column(name="ORDER_STR")
	private String orderStr;//排序
	
	
	@Column(name="CR_TIME")
	private Calendar crTime;//创建时间
	
	
	@Column(name="CONDITION_EXP")
	private String conditionExp;//条件表达式
	
	
	@Column(name="CONDITION_ITEMS")
	private String conditionItems;//条件项
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="e_report_role",
			joinColumns={@JoinColumn(name="e_report_id")},
			inverseJoinColumns={@JoinColumn(name="role_id")})
	private Set<TeeUserRole> roles;//角色权限
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="e_report_user",
			joinColumns={@JoinColumn(name="e_report_id")},
			inverseJoinColumns={@JoinColumn(name="user_id")})
	private Set<TeePerson> users;//人员权限
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="e_report_dept",
			joinColumns={@JoinColumn(name="e_report_id")},
			inverseJoinColumns={@JoinColumn(name="dept_id")})
	private Set<TeeDepartment> depts;//部门权限

	
	
	

	public String getConditionExp() {
		return conditionExp;
	}


	public String getConditionItems() {
		return conditionItems;
	}


	public void setConditionExp(String conditionExp) {
		this.conditionExp = conditionExp;
	}


	public void setConditionItems(String conditionItems) {
		this.conditionItems = conditionItems;
	}


	public int getSid() {
		return sid;
	}


	public String getTitle() {
		return title;
	}


	public TeeSeniorReportCat getCat() {
		return cat;
	}


	public TeePerson getCrUser() {
		return crUser;
	}


	public BisView getBisView() {
		return bisView;
	}


	public int getChartType() {
		return chartType;
	}


	public String getDimension() {
		return dimension;
	}


	public String getSpindle() {
		return spindle;
	}


	public String getShaft() {
		return shaft;
	}


	public String getOrderStr() {
		return orderStr;
	}


	public Calendar getCrTime() {
		return crTime;
	}


	public Set<TeeUserRole> getRoles() {
		return roles;
	}


	public Set<TeePerson> getUsers() {
		return users;
	}


	public Set<TeeDepartment> getDepts() {
		return depts;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public void setCat(TeeSeniorReportCat cat) {
		this.cat = cat;
	}


	public void setCrUser(TeePerson crUser) {
		this.crUser = crUser;
	}


	public void setBisView(BisView bisView) {
		this.bisView = bisView;
	}


	public void setChartType(int chartType) {
		this.chartType = chartType;
	}


	public void setDimension(String dimension) {
		this.dimension = dimension;
	}


	public void setSpindle(String spindle) {
		this.spindle = spindle;
	}


	public void setShaft(String shaft) {
		this.shaft = shaft;
	}


	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}


	public void setCrTime(Calendar crTime) {
		this.crTime = crTime;
	}


	public void setRoles(Set<TeeUserRole> roles) {
		this.roles = roles;
	}


	public void setUsers(Set<TeePerson> users) {
		this.users = users;
	}


	public void setDepts(Set<TeeDepartment> depts) {
		this.depts = depts;
	}
	
	
	
	
	
	
}
