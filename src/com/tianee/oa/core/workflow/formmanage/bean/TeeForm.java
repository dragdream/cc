package com.tianee.oa.core.workflow.formmanage.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.tianee.oa.core.org.bean.TeeDepartment;

@Entity
@Table(name="FORM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TeeForm implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FORM_seq_gen")
	@SequenceGenerator(name="FORM_seq_gen", sequenceName="FORM_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="FORM_NAME")
	private String formName;//表单名称
	
	@Lob
	@Column(name="PRINT_MODEL")
	private String printModel="";//打印模板
	
	@Lob
	@Column(name="PRINT_MODEL_SHORT")
	private String printModelShort="";//快速模板
	
	@Column(name="SCRIPT")
	@Lob
	private String script="";//javascript脚本
	
	@Column(name="CSS")
	@Lob
	private String css="";//css样式
	
	@Column(name="ITEM_MAX")
	private int itemMax;//当前最大控件数
	
	@Column(name="VERSION_NO")
	private int versionNo;//版本号
	
	@Column(name="VERSION_TIME")
	private Calendar versionTime;//生成版本时间
	
	@Column(name="LOCKED")
	private int locked;//表单修订锁，用于生成版本之后，未接受修订前不允许发起新流程

	@Column(name="FORM_GROUP")
	private int formGroup;//原始表单id
	
	@ManyToOne()
	@Index(name="IDX718309d35021460bbcbbf344e8a")
	@JoinColumn(name="FORM_SORT_ID")
	private TeeFormSort formSort;//表单分类
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.ALL},orphanRemoval=true,mappedBy="form")
	private List<TeeFormItem> formItems = new ArrayList<TeeFormItem>(0);
	
	@ManyToOne()
	@JoinColumn(name="DEPT_ID")
	private TeeDepartment dept;//所属部门
	
	
	public TeeDepartment getDept() {
		return dept;
	}

	public void setDept(TeeDepartment dept) {
		this.dept = dept;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getPrintModel() {
		return printModel;
	}

	public void setPrintModel(String printModel) {
		this.printModel = printModel;
	}

	public String getPrintModelShort() {
		return printModelShort;
	}

	public void setPrintModelShort(String printModelShort) {
		this.printModelShort = printModelShort;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public int getItemMax() {
		return itemMax;
	}

	public void setItemMax(int itemMax) {
		this.itemMax = itemMax;
	}

	public int getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}

	public Calendar getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(Calendar versionTime) {
		this.versionTime = versionTime;
	}

	
	public int getFormGroup() {
		return formGroup;
	}

	public void setFormGroup(int formGroup) {
		this.formGroup = formGroup;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public int getLocked() {
		return locked;
	}

	public void setFormSort(TeeFormSort formSort) {
		this.formSort = formSort;
	}

	public TeeFormSort getFormSort() {
		return formSort;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getSid() {
		return sid;
	}

	public void setFormItems(List<TeeFormItem> formItems) {
		this.formItems = formItems;
	}

	public List<TeeFormItem> getFormItems() {
		return formItems;
	}
	
	
}
