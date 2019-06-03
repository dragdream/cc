package com.tianee.oa.core.base.examine.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "EXAMINE_ITEM")
public class TeeExamineItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="EXAMINE_ITEM_seq_gen")
	@SequenceGenerator(name="EXAMINE_ITEM_seq_gen", sequenceName="EXAMINE_ITEM_seq")
	@Column(name="SID")
	private int sid;//SID	int(11)	自增字段	是	自增
	
	@Column(name="ITEM_NAME")
	private String itemName;//ITEM_NAME	varchar(254)	项目名称		
	
	
	@Column(name="ITEM_DESC")
	private String itemDesc;//ITEM_NAME	varchar(254)	项目描述
	
	
	@Column(name="ITEM_MIN")
	private double itemMin;//ITEM_MIN	Number	最小值	
	
	@Column(name="ITEM_MAX")
	private double itemMax;//ITEM_MAX	Number	最大值	
	
	@ManyToOne()
	@Index(name="IDXc86862f7fda049ffa94401a7e58")
	@JoinColumn(name="GROUP_ID")
	private TeeExamineGroup group;//EXAMINE _ID	TeeScoreGroup	考核指标集Id		

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getItemMin() {
		return itemMin;
	}

	public void setItemMin(double itemMin) {
		this.itemMin = itemMin;
	}

	public double getItemMax() {
		return itemMax;
	}

	public void setItemMax(double itemMax) {
		this.itemMax = itemMax;
	}

	public TeeExamineGroup getGroup() {
		return group;
	}

	public void setGroup(TeeExamineGroup group) {
		this.group = group;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	
}
