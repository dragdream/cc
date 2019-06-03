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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="SENIOR_REPORT_TPL")
public class TeeSeniorReportTemplate {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "varchar(32)",name="UUID")
	private String uuid;//uuid
	
	@Column(name="TPL_NAME")
	private String tplName;
	
	@Lob
	@Column(name="HEADER")
	private String header;
	
	@Lob
	@Column(name="BODY")
	private String body;
	
	@Lob
	@Column(name="FOOTER")
	private String footer;
	
	@Column(name="DATA_IDENTITY")
	private String dataIdentity;
	
	@Column(name="PAGE_SIZE")
	private int pageSize;
	
	@Column(name="GROUP_")
	private String group;
	
	@Column(name="CHART_TYPE")
	private String chartType;//图形
	
	@Column(name="REVERSE_")
	private int reverse = 0;//是否行列转置
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<TeePerson> userPriv = new HashSet<TeePerson>();
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<TeeDepartment> deptPriv = new HashSet<TeeDepartment>();
	
	@ManyToOne
	@Index(name="IDX7e98addfbb764f9f8a2bbae069e")
	@JoinColumn(name="CAT_ID")
	private TeeSeniorReportCat reportCat;
	
	@JoinColumn(name="PARAMS")
	private String params;
	
	@Column(name="STATUS",nullable=true)
	private int status;
	
	@Column(name="CT_TYPE",nullable=true)
	private int ctType;
	
	@Column(name="CT_VIEW",nullable=true)
	private String ctView;
	
	@Column(name="CT_REPORT",nullable=true)
	private String ctReport;
	
	@Column(name="CT_PARAMS",nullable=true)
	private String ctParams;
	
	@Lob
	@Column(name="MODEL_",nullable=true)
	private String model;//报表模型，用于对设计视图进行管控
	
	@ManyToOne
	@Index(name="IDX_GEZ_RES_ID")
	@JoinColumn(name="GEZ_RES_ID")
	private TeeGenzReport genzReport;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTplName() {
		return tplName;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getDataIdentity() {
		return dataIdentity;
	}

	public void setDataIdentity(String dataIdentity) {
		this.dataIdentity = dataIdentity;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
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

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public int getReverse() {
		return reverse;
	}

	public void setReverse(int reverse) {
		this.reverse = reverse;
	}

	public TeeSeniorReportCat getReportCat() {
		return reportCat;
	}

	public void setReportCat(TeeSeniorReportCat reportCat) {
		this.reportCat = reportCat;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getCtType() {
		return ctType;
	}

	public void setCtType(int ctType) {
		this.ctType = ctType;
	}

	public String getCtView() {
		return ctView;
	}

	public void setCtView(String ctView) {
		this.ctView = ctView;
	}

	public String getCtReport() {
		return ctReport;
	}

	public void setCtReport(String ctReport) {
		this.ctReport = ctReport;
	}

	public String getCtParams() {
		return ctParams;
	}

	public void setCtParams(String ctParams) {
		this.ctParams = ctParams;
	}

	public TeeGenzReport getGenzReport() {
		return genzReport;
	}

	public void setGenzReport(TeeGenzReport genzReport) {
		this.genzReport = genzReport;
	}
	
}
