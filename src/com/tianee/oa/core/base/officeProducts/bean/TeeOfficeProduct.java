package com.tianee.oa.core.base.officeProducts.bean;
import org.hibernate.annotations.Index;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 办公用品
 * @author kakalion
 *
 */
@Entity
@Table(name="OFFICE_PRODUCT")
public class TeeOfficeProduct{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="OFFICE_PRODUCT_seq_gen")
	@SequenceGenerator(name="OFFICE_PRODUCT_seq_gen", sequenceName="OFFICE_PRODUCT_seq")
	private int sid;
	
	@Column(name="PRO_NAME")
	private String proName;//用品名称
	
	@Column(name="PRO_CODE")
	private String proCode;//用品编号
	
	@Column(name="PRO_UNIT")
	private String proUnit;//计量单位
	
	@Column(name="NORMS")
	private String norms;//规格
	
	@Column(name="PRO_SUPPLY")
	private String proSupplier;//供应商
	
	@Column(name="PRICE")
	private BigDecimal price = new BigDecimal(0);//单价
	
	@Column(name="MAX_STOCK")
	private int maxStock;//最高库存警戒
	
	@Column(name="MIN_STOCK")
	private int minStock;//最低库存警戒
	
	/**
	 * 1、领用
	 * 2、借用
	 * 3、归还
	 */
	@Column(name="REG_TYPE")
	private int regType;//申请类型
	
	@ManyToOne()
	@Index(name="IDX46a182a31972493896ed29e1046")
	@JoinColumn(name="CATEGORY")
	private TeeOfficeCategory category;//所属类别
	
	@Column(name="PRO_DESC")
	@Lob
	private String proDesc;//用品描述
	
	@ManyToMany(fetch=FetchType.LAZY,cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name="officeProduct_auditors")
	private Set<TeePerson> auditors = new HashSet<TeePerson>(0);//审批员
	
	@ManyToMany(fetch=FetchType.LAZY,cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name="officeProduct_regUsers")
	private Set<TeePerson> regUsers = new HashSet<TeePerson>(0);//登记用户权限
	
	@ManyToMany(fetch=FetchType.LAZY,cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name="officeProduct_regDepts")
	private Set<TeeDepartment> regDepts = new HashSet<TeeDepartment>(0);//登记部门权限

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getProUnit() {
		return proUnit;
	}

	public void setProUnit(String proUnit) {
		this.proUnit = proUnit;
	}

	public String getNorms() {
		return norms;
	}

	public void setNorms(String norms) {
		this.norms = norms;
	}

	public String getProSupplier() {
		return proSupplier;
	}

	public void setProSupplier(String proSupplier) {
		this.proSupplier = proSupplier;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getMaxStock() {
		return maxStock;
	}

	public void setMaxStock(int maxStock) {
		this.maxStock = maxStock;
	}

	public int getMinStock() {
		return minStock;
	}

	public void setMinStock(int minStock) {
		this.minStock = minStock;
	}

	public TeeOfficeCategory getCategory() {
		return category;
	}

	public void setCategory(TeeOfficeCategory category) {
		this.category = category;
	}

	public String getProDesc() {
		return proDesc;
	}

	public void setProDesc(String proDesc) {
		this.proDesc = proDesc;
	}

	public Set<TeePerson> getAuditors() {
		return auditors;
	}

	public void setAuditors(Set<TeePerson> auditors) {
		this.auditors = auditors;
	}

	public Set<TeePerson> getRegUsers() {
		return regUsers;
	}

	public void setRegUsers(Set<TeePerson> regUsers) {
		this.regUsers = regUsers;
	}

	public Set<TeeDepartment> getRegDepts() {
		return regDepts;
	}

	public void setRegDepts(Set<TeeDepartment> regDepts) {
		this.regDepts = regDepts;
	}

	public void setRegType(int regType) {
		this.regType = regType;
	}

	public int getRegType() {
		return regType;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getSid() {
		return sid;
	}
	
	
}
