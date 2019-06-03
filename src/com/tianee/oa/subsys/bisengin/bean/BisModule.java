package com.tianee.oa.subsys.bisengin.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;

/**
 * 业务模块定义
 * @author kakalion
 *
 */
@Entity
@Table(name="BIS_MODULE")
public class BisModule {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@Column(name="MODULE_NAME")
	private String moduleName;//模块名称
	
	@ManyToOne()
	@JoinColumn(name="BIS_TABLE_ID")
	private BisTable bisTable;//关联业务表
	
	@ManyToOne()
	@JoinColumn(name="BIS_VIEW_ID")
	private BisView bisView;//关联视图
	
	@ManyToOne()
	@JoinColumn(name="FORM_ID")
	private TeeForm form;//关联表单
	
	@Column(name="PAGE_SIZE")
	private int pageSize;//分页大小
	
	/**
	 * [{FII:'表单项ITEM_ID',FIT:'表单title',TFI:'业务表ID',TFT:'业务表TITLE',TYPE:'xxxx'}]
	 * TYPE:
	 * 文本输入
	 * 整型输入
	 * 下拉选择
	 * 双精度浮点
	 * 单选人员
	 * 多选人员
	 * 单选角色
	 * 多选角色
	 * 单选部门
	 * 多选部门
	 * 日期选择
	 * 日期时间选择
	 */
	@Lob
	@Column(name="MAPPING_")
	private String mapping;//业务表单映射
	
	@Column(name="CR_TIME")
	private Calendar crTime;//创建时间
	
	@Lob
	@Column(name="CREATE_PRIV")
	private String createPriv;//创建权限
	
	@Lob
	@Column(name="EDIT_PRIV")
	private String editPriv;//修改权限
	
	@Lob
	@Column(name="DEL_PRIV")
	private String delPriv;//删除权限
	
	@Column(name="POST_PRIV")
	private int postPriv;//管理权限

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public BisTable getBisTable() {
		return bisTable;
	}

	public void setBisTable(BisTable bisTable) {
		this.bisTable = bisTable;
	}

	public BisView getBisView() {
		return bisView;
	}

	public void setBisView(BisView bisView) {
		this.bisView = bisView;
	}

	public TeeForm getForm() {
		return form;
	}

	public void setForm(TeeForm form) {
		this.form = form;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public Calendar getCrTime() {
		return crTime;
	}

	public void setCrTime(Calendar crTime) {
		this.crTime = crTime;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getCreatePriv() {
		return createPriv;
	}

	public void setCreatePriv(String createPriv) {
		this.createPriv = createPriv;
	}

	public String getEditPriv() {
		return editPriv;
	}

	public void setEditPriv(String editPriv) {
		this.editPriv = editPriv;
	}

	public String getDelPriv() {
		return delPriv;
	}

	public void setDelPriv(String delPriv) {
		this.delPriv = delPriv;
	}

	public int getPostPriv() {
		return postPriv;
	}

	public void setPostPriv(int postPriv) {
		this.postPriv = postPriv;
	}
}
