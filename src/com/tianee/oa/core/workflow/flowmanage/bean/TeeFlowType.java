package com.tianee.oa.core.workflow.flowmanage.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowPriv;

@Entity
@Table(name="FLOW_TYPE")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class TeeFlowType  implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_TYPE_seq_gen")
	@SequenceGenerator(name="FLOW_TYPE_seq_gen", sequenceName="FLOW_TYPE_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="FLOW_NAME")
	private String flowName;//流程名称
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDXa47b3cd8e63a414cb61cb710c21")
	@JoinColumn(name="FORM_ID")
	private TeeForm form;//表单
	
	@Column(name="FREE_PRESET")
	private int freePreset;//是否预设步骤
	
	@Column(name="HAS_DOC")
	private int hasDoc;//是否拥有正文
	
	@Column(name="TYPE")
	private int type;//流程类型
	
	@Column(name="ORDER_NO")
	private int orderNo;//排序号
	
	@ManyToOne()
	@Index(name="IDX238f09e34c6b4cd5b744281cba6")
	@JoinColumn(name="SORT_ID")
	private TeeFlowSort flowSort;//流程分类
	
	@Column(name="COMMENT_REMARK")
	private String comment;//流程说明
	
	@Column(name="VIEW_PRIV")
	private int viewPriv;//是否允许传阅
	
	@ManyToOne()
	@Index(name="IDX11f5ec10a6b9497ba9cfb7feef7")
	@JoinColumn(name="DEPT_ID")
	private TeeDepartment dept;//所属部门
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="FT_VIEW_PERSONS")
	private Set<TeePerson> viewPersons;//传阅人
	
	/**
	 *   <OPTION selected value=2>允许委托</OPTION> 
	 *   <OPTION value=0>禁止委托</OPTION> 
	 *   <OPTION value=3>按步骤办理权限委托</OPTION>
	 *   <OPTION value=1>委托当前步骤经办人</OPTION>
	 */
	@Column(name="DELEGATE")
	private int delegate;//委托类型
	
	@Column(name="FILE_CODE_EXPRESSION")
	private String fileCodeExpression;//文号表达式
	
	@Column(name="NUMBERING")
	private int numbering;//编号
	
	@Column(name="NUMBERING_LENGTH")
	private int numberingLength;//编号位数
	
	@Column(name="EDIT_TITLE")
	private int editTitle;//编辑标题1-允许修改2-不允许修改3-修改前缀4-修改后缀-5修改前缀和后缀
	
	@Column(name="ATTACH_PRIV")
	private int attachPriv;//附件权限1-允许 0-不允许
	
	@Column(name="FEEDBACK_PRIV")
	private int feedbackPriv;//会签权限1-允许 0-不允许
	
	@OneToMany(fetch=FetchType.LAZY,cascade={javax.persistence.CascadeType.ALL},orphanRemoval=true,mappedBy="flowType")
	private List<TeeFlowProcess> processList;//步骤
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="FT_PRCS_USER",
			joinColumns={@JoinColumn(name="FLOW_ID")},
			inverseJoinColumns={@JoinColumn(name="PRCS_USER")})
	private Set<TeePerson> prcsUsers;//自由流程发起权限
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="FT_PRCS_DEPT",
			joinColumns={@JoinColumn(name="FLOW_ID")},
			inverseJoinColumns={@JoinColumn(name="PRCS_DEPT")})
	private Set<TeeDepartment> prcsDepts;//自由流程发起权限
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="FT_PRCS_ROLE",
			joinColumns={@JoinColumn(name="FLOW_ID")},
			inverseJoinColumns={@JoinColumn(name="PRCS_ROLE")})
	private Set<TeeUserRole> prcsRoles;//自由流程发起权限
	
	
	@OneToMany(mappedBy="flowType",fetch=FetchType.LAZY,cascade={javax.persistence.CascadeType.ALL})
	private Set<TeeFlowPriv> flowPrivs;
	
	@Lob
	@Column(name="QUERY_FIELD_MODEL")
	private String queryFieldModel="[@流水号,@工作名称,@所属流程,@发起人,@步骤与流程图,@时间,@状态,@公共附件,@正文,@操作]";//查询字段模型数组
	
	@Lob
	@Column(name="VARS_MODEL")
	private String flowRunVarsModel="[]";//流程变量模型
	
	@Column(name="RUN_NAME_PRIV")
	private int runNamePriv;//工作名称权限 0：不允许修改  1：允许修改 2：仅允许输入前缀  3：仅允许输入后缀  4：仅允许输入前缀和后缀
	
	//外接模块页面
	@Column(name = "OUTER_PAGE")
	private String outerPage;
	
	
	@Column(name="ARCHIVE_MAPPING")
	private String archiveMapping;//归档映射
	
	
	@Column(name="AUTO_ARCHIVE")
	private int autoArchive;//自动归档
	
	
	@Column(name="AUTO_ARCHIVE_VALUE")
	private int autoArchiveValue;//自动归档值
		
	
	
	
	public int getAutoArchive() {
		return autoArchive;
	}

	public int getAutoArchiveValue() {
		return autoArchiveValue;
	}

	public void setAutoArchive(int autoArchive) {
		this.autoArchive = autoArchive;
	}

	public void setAutoArchiveValue(int autoArchiveValue) {
		this.autoArchiveValue = autoArchiveValue;
	}

	public String getArchiveMapping() {
		return archiveMapping;
	}

	public void setArchiveMapping(String archiveMapping) {
		this.archiveMapping = archiveMapping;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public TeeForm getForm() {
		return form;
	}

	public void setForm(TeeForm form) {
		this.form = form;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public TeeFlowSort getFlowSort() {
		return flowSort;
	}

	public void setFlowSort(TeeFlowSort flowSort) {
		this.flowSort = flowSort;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getViewPriv() {
		return viewPriv;
	}

	public void setViewPriv(int viewPriv) {
		this.viewPriv = viewPriv;
	}
	
	public Set<TeePerson> getViewPersons() {
		return viewPersons;
	}

	public void setViewPersons(Set<TeePerson> viewPersons) {
		this.viewPersons = viewPersons;
	}

	public int getDelegate() {
		return delegate;
	}

	public void setDelegate(int delegate) {
		this.delegate = delegate;
	}

	public String getFileCodeExpression() {
		return fileCodeExpression;
	}

	public void setFileCodeExpression(String fileCodeExpression) {
		this.fileCodeExpression = fileCodeExpression;
	}

	public int getNumbering() {
		return numbering;
	}

	public void setNumbering(int numbering) {
		this.numbering = numbering;
	}

	public int getNumberingLength() {
		return numberingLength;
	}

	public void setNumberingLength(int numberingLength) {
		this.numberingLength = numberingLength;
	}

	public int getEditTitle() {
		return editTitle;
	}

	public void setEditTitle(int editTitle) {
		this.editTitle = editTitle;
	}

	public void setAttachPriv(int attachPriv) {
		this.attachPriv = attachPriv;
	}

	public int getAttachPriv() {
		return attachPriv;
	}

	public void setDept(TeeDepartment dept) {
		this.dept = dept;
	}

	public TeeDepartment getDept() {
		return dept;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getSid() {
		return sid;
	}

	public void setProcessList(List<TeeFlowProcess> processList) {
		this.processList = processList;
	}

	public List<TeeFlowProcess> getProcessList() {
		return processList;
	}

	public void setPrcsUsers(Set<TeePerson> prcsUsers) {
		this.prcsUsers = prcsUsers;
	}

	public Set<TeePerson> getPrcsUsers() {
		return prcsUsers;
	}

	public void setPrcsDepts(Set<TeeDepartment> prcsDepts) {
		this.prcsDepts = prcsDepts;
	}

	public Set<TeeDepartment> getPrcsDepts() {
		return prcsDepts;
	}

	public void setPrcsRoles(Set<TeeUserRole> prcsRoles) {
		this.prcsRoles = prcsRoles;
	}

	public Set<TeeUserRole> getPrcsRoles() {
		return prcsRoles;
	}

	public Set<TeeFlowPriv> getFlowPrivs() {
		return flowPrivs;
	}

	public void setFlowPrivs(Set<TeeFlowPriv> flowPrivs) {
		this.flowPrivs = flowPrivs;
	}

	public void setQueryFieldModel(String queryFieldModel) {
		this.queryFieldModel = queryFieldModel;
	}

	public String getQueryFieldModel() {
		return queryFieldModel;
	}

	public void setHasDoc(int hasDoc) {
		this.hasDoc = hasDoc;
	}

	public int getHasDoc() {
		return hasDoc;
	}

	public void setFlowRunVarsModel(String flowRunVarsModel) {
		this.flowRunVarsModel = flowRunVarsModel;
	}

	public String getFlowRunVarsModel() {
		return flowRunVarsModel;
	}

	public int getFreePreset() {
		return freePreset;
	}

	public void setFreePreset(int freePreset) {
		this.freePreset = freePreset;
	}

	public int getFeedbackPriv() {
		return feedbackPriv;
	}

	public void setFeedbackPriv(int feedbackPriv) {
		this.feedbackPriv = feedbackPriv;
	}

	public int getRunNamePriv() {
		return runNamePriv;
	}

	public void setRunNamePriv(int runNamePriv) {
		this.runNamePriv = runNamePriv;
	}

	public String getOuterPage() {
		return outerPage;
	}

	public void setOuterPage(String outerPage) {
		this.outerPage = outerPage;
	}
	
	
}
