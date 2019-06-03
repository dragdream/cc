package com.tianee.oa.core.tpl.bean;
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

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;

/**
 * 套红模版
 * @author kakalion
 *
 */
@Entity
@Table(name="WORD_MODEL")
public class TeeWordModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="WORD_MODEL_seq_gen")
	@SequenceGenerator(name="WORD_MODEL_seq_gen", sequenceName="WORD_MODEL_seq")
	private int sid;
	
	@Column(name="MODEL_NAME")
	private String modelName;
	
	@Column(name="MODEL_DESC")
	private String modelDesc;
	
	@ManyToOne()
	@Index(name="IDXec581417a5cc4a76b221e37caf7")
	@JoinColumn(name="ATTACH")
	private TeeAttachment attach;//模版文件
	
	@Column(name="SORT_NO")
	private int sortNo;//排序号
	
	
	@Column(name="word_model_type")
	private String wordModelType;//套红模板类型
	public String getWordModelType() {
		return wordModelType;
	}

	public void setWordModelType(String wordModelType) {
		this.wordModelType = wordModelType;
	}

	/**
	 * 作用范围
	 * 1、全体人员
	 * 2、指定权限
	 */
	@Column(name="PRIV_RANGES")
	private int privRanges;
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<TeePerson> userPriv = new HashSet<TeePerson>(0);
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<TeeDepartment> deptPriv = new HashSet<TeeDepartment>(0);
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<TeeUserRole> rolePriv = new HashSet<TeeUserRole>(0);

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelDesc() {
		return modelDesc;
	}

	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}

	public TeeAttachment getAttach() {
		return attach;
	}

	public void setAttach(TeeAttachment attach) {
		this.attach = attach;
	}

	public int getPrivRanges() {
		return privRanges;
	}

	public void setPrivRanges(int privRanges) {
		this.privRanges = privRanges;
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

	public Set<TeeUserRole> getRolePriv() {
		return rolePriv;
	}

	public void setRolePriv(Set<TeeUserRole> rolePriv) {
		this.rolePriv = rolePriv;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public int getSortNo() {
		return sortNo;
	}
	
}
