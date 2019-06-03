package com.tianee.oa.subsys.crm.core.product.bean;
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

/**
 * 产品类型表
 * @author syl
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CRM_PRODUCTS_TYPE")
public class TeeCrmProductsType {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_PRODUCTS_TYPE_seq_gen")
	@SequenceGenerator(name="CRM_PRODUCTS_TYPE_seq_gen", sequenceName="CRM_PRODUCTS_TYPE_seq")
	@Column(name = "SID")
	private int sid;// 自增id
			
	@Column(name = "TYPE_NAME", nullable = true, length = 254)
	private String typeName;//分类名称

	@Column(name = "TYPE_ORDER", columnDefinition="int default 0")
	private int typeOrder ;//排序

		
	@Column(name = "TYPE_FLAG", columnDefinition="int default 0")
	private int typeFlag ;//是否删除
		
	@ManyToOne()
	@Index(name="IDX07a50e3450f64fad95e2f07e94d")
	@JoinColumn(name="PARENT_ID")
	private TeeCrmProductsType parentType;//上级分类ID
	
	@Column(name = "PARENT_PATH" , length =500)
	private String parentPath;//上级节点路径  如1/2/

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getTypeOrder() {
		return typeOrder;
	}

	public void setTypeOrder(int typeOrder) {
		this.typeOrder = typeOrder;
	}

	public int getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(int typeFlag) {
		this.typeFlag = typeFlag;
	}

	public TeeCrmProductsType getParentType() {
		return parentType;
	}

	public void setParentType(TeeCrmProductsType parentType) {
		this.parentType = parentType;
	}

	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

}
