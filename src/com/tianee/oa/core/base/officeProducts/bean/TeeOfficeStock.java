package com.tianee.oa.core.base.officeProducts.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="Office_Stock")
public class TeeOfficeStock{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="Office_Stock_seq_gen")
	@SequenceGenerator(name="Office_Stock_seq_gen", sequenceName="Office_Stock_seq")
	private int sid;
	
	@ManyToOne()
	@Index(name="IDXcc6beae1ae7c4ceea30e261396e")
	@JoinColumn(name="PRODUCT_ID")
	private TeeOfficeProduct product;
	
	/**
	 * 登记类型：1、领用 2、借用 3、归还 4、入库 5、维护 6、报废
	 */
	@Column(name="REG_TYPE")
	private int regType;
	
	@Column(name="REG_COUNT")
	private int regCount;//登记数量
	
	@Column(name="CREATE_TIME")
	private Calendar createTime;//创建时间

	public TeeOfficeProduct getProduct() {
		return product;
	}

	public void setProduct(TeeOfficeProduct product) {
		this.product = product;
	}

	public int getRegType() {
		return regType;
	}

	public void setRegType(int regType) {
		this.regType = regType;
	}

	public int getRegCount() {
		return regCount;
	}

	public void setRegCount(int regCount) {
		this.regCount = regCount;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 登记类型：1、领用 2、借用 3、归还 4、入库 5、维护 6、报废
	 */
	public static String getTypeDesc(int regType){
		switch(regType){
		case 1:return "领用";
		case 2:return "借用";
		case 3:return "归还";
		case 4:return "入库";
		case 5:return "维护";
		case 6:return "报废";
		}
		return "";
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getSid() {
		return sid;
	}
	
}
