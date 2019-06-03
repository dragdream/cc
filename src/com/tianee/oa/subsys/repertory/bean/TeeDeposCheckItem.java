package com.tianee.oa.subsys.repertory.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Index;
import com.tianee.oa.subsys.crm.core.product.bean.TeeCrmProducts;

/**
 * 库存盘点项
 * @author kakalion
 *
 */
@Entity
@Table(name="DEPOS_CHECK_ITEM")
public class TeeDeposCheckItem {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="DEPOS_CHECK_ITEM_seq_gen")
	@SequenceGenerator(name="DEPOS_CHECK_ITEM_seq_gen", sequenceName="DEPOS_CHECK_ITEM_seq")
	private int sid;
	
	@ManyToOne()
	@JoinColumn(name="PRO_ID")
	@Index(name="DEPOS_CHECK_ITEM_PRO_ID")
	private TeeCrmProducts product;//产品
	
	@ManyToOne()
	@JoinColumn(name="REC_ID")
	@Index(name="DEPOS_CHECK_ITEM_REC_ID")
	private TeeDeposCheckRecord checkRecord;//盘库记录
	
	@Column(name="ORIGINAL_COUNT")
	private int originalCount;//原库存量
	
	@Column(name="MANUAL_COUNT")
	private int manualCount;//盘点数量

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeCrmProducts getProduct() {
		return product;
	}

	public void setProduct(TeeCrmProducts product) {
		this.product = product;
	}

	public TeeDeposCheckRecord getCheckRecord() {
		return checkRecord;
	}

	public void setCheckRecord(TeeDeposCheckRecord checkRecord) {
		this.checkRecord = checkRecord;
	}

	public int getOriginalCount() {
		return originalCount;
	}

	public void setOriginalCount(int originalCount) {
		this.originalCount = originalCount;
	}

	public int getManualCount() {
		return manualCount;
	}

	public void setManualCount(int manualCount) {
		this.manualCount = manualCount;
	}
	
	
}
