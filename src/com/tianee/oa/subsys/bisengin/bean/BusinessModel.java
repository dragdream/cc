package com.tianee.oa.subsys.bisengin.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;

//业务建模
@Entity
@Table(name="Business_model")
public class BusinessModel {

	@Id
	private String bisKey;
	
	
	/**
	 * 业务名称
	 */
	@Column(name="business_NAME")
	private String businessName;
	
	/**
	 * 业务标题
	 */
	@Column(name="business_title")
	private String businessTitle;
	
	/**
	 * 业务操作
	 */
	@Column(name="business_operation")
	private String businessOperation;
	
	/**
	 * 列表表头字段
	 */
	@Column(name="header_fields")
	private String headerFields;
	
	/**
	 * 查询条件字段
	 */
	@Column(name="query_fields")
	private String queryFields;
	
	/**
	 * 列表排序字段
	 */
	@Column(name="order_field")
	private String orderField;
	
	/**
	 * 列表分组字段
	 */
	@Column(name="group_field")
	private String groupField;
	
	
	/**
	 * 列表显示条件
	 */
	@Column(name="display_condition")
	private String displayCondition;
	
	
	/**
	 * 业务表单
	 */
	@ManyToOne
	@JoinColumn(name="BIS_FORM_ID")
	private BisForm bisForm;
	
	
	/**
	 * 业务类别
	 */
	@ManyToOne
	@JoinColumn(name="business_cat_ID")
	private BusinessCat businessCat;
	
	
	/**
	 * 创建时间
	 */
	@Column(name="create_Time")
	private Date createTime;
	
	/**
	 * 绑定流程
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FLOW_ID")
	private TeeFlowType flowTye;
	
	/**
	 * 触发步骤
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FLOW_STEP")
	private TeeFlowProcess flowProcess;

	/**
	 * 映射关系
	 */
	@Column(name="MAPPING")
	private String mapping;
	
	
	
	
	
	public TeeFlowType getFlowTye() {
		return flowTye;
	}


	public TeeFlowProcess getFlowProcess() {
		return flowProcess;
	}


	public String getMapping() {
		return mapping;
	}


	public void setFlowTye(TeeFlowType flowTye) {
		this.flowTye = flowTye;
	}


	public void setFlowProcess(TeeFlowProcess flowProcess) {
		this.flowProcess = flowProcess;
	}


	public void setMapping(String mapping) {
		this.mapping = mapping;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getBisKey() {
		return bisKey;
	}


	public void setBisKey(String bisKey) {
		this.bisKey = bisKey;
	}


	public String getBusinessName() {
		return businessName;
	}


	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}


	public String getBusinessTitle() {
		return businessTitle;
	}


	public void setBusinessTitle(String businessTitle) {
		this.businessTitle = businessTitle;
	}


	public String getBusinessOperation() {
		return businessOperation;
	}


	public void setBusinessOperation(String businessOperation) {
		this.businessOperation = businessOperation;
	}


	public String getHeaderFields() {
		return headerFields;
	}


	public void setHeaderFields(String headerFields) {
		this.headerFields = headerFields;
	}


	public String getQueryFields() {
		return queryFields;
	}


	public void setQueryFields(String queryFields) {
		this.queryFields = queryFields;
	}


	public String getOrderField() {
		return orderField;
	}


	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}


	public String getGroupField() {
		return groupField;
	}


	public void setGroupField(String groupField) {
		this.groupField = groupField;
	}


	public String getDisplayCondition() {
		return displayCondition;
	}


	public void setDisplayCondition(String displayCondition) {
		this.displayCondition = displayCondition;
	}


	public BisForm getBisForm() {
		return bisForm;
	}


	public void setBisForm(BisForm bisForm) {
		this.bisForm = bisForm;
	}


	public BusinessCat getBusinessCat() {
		return businessCat;
	}


	public void setBusinessCat(BusinessCat businessCat) {
		this.businessCat = businessCat;
	}


	
}
