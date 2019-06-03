package com.tianee.oa.core.workflow.formmanage.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.tianee.oa.core.attachment.bean.TeeAttachment;

@Entity
@Table(name="FORM_ITEM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TeeFormItem  implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FORM_ITEM_seq_gen")
	@SequenceGenerator(name="FORM_ITEM_seq_gen", sequenceName="FORM_ITEM_seq")
	@Column(name="SID")
	private int sid;//表单控件自增ID
	
	private int itemId;//项目ID
	
	@ManyToOne()
	@Index(name="IDX8078a157c0b84f2f80fe105c074")
	@JoinColumn(name="FORM_ID")
	private TeeForm form;//表单
	
	@Column(name="TAG")
	private String tag;//控件标签名称
	
	@Column(name="SORT_NO")
	private int sortNo;//排序字段
	
	@Column(name="TITLE")
	private String title;//控件标题
	
	@Lob
	@Column(name="CONTENT")
	private String content;//控件整体内容
	
	@Lob
	@Column(name="MODEL")
	private String model="";//数据模型
	
	@Column(name="NAME")
	private String name;//控件名称
	
	@Column(name="XTYPE")
	private String xtype;//控件类型
	
	@Column(name="COLUMN_TYPE")
	private int columnType;//列类型
	
	@Column(name="DEFAULT_VALUE")
	private String defaultValue;//默认值
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ITEM_GROUP")
	private TeeFormItemGroup itemGroup;//组容器
	
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setForm(TeeForm form) {
		this.form = form;
	}

	public TeeForm getForm() {
		return form;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getXtype() {
		return xtype;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}

	public int getColumnType() {
		return columnType;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getSid() {
		return sid;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}
	
	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public TeeFormItemGroup getItemGroup() {
		return itemGroup;
	}

	public void setItemGroup(TeeFormItemGroup itemGroup) {
		this.itemGroup = itemGroup;
	}

	/**
	 * 根据字段title返回表单字段
	 * @param items
	 * @return
	 */
	public static TeeFormItem getItemByTitle(List<TeeFormItem> items,String title){
		for(TeeFormItem it:items){
			if(it.getTitle().equals(title)){
				return it;
			}
		}
		return null;
	}
	
}
