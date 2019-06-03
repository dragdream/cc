package com.tianee.oa.subsys.crm.core.base.bean;
import org.hibernate.annotations.Index;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

/**
 * 竞争对手
 * @author SYL
 *
 */
@Entity
@Table(name="CRM_COMPETITOR")
public class TeeCrmCompetitor {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_COMPETITOR_seq_gen")
	@SequenceGenerator(name="CRM_COMPETITOR_seq_gen", sequenceName="CRM_COMPETITOR_seq")
	@Column(name="SID")
	private int sid;//Sid	int	11	否	主键

	@Column(name = "COMPANY")
	private String company;//公司名称
	
	@Column(name = "REGISTER_CAPITAL" , length = 50)
	private String registerCapital;//注册资本
	
	@Column(name = "CONPANY_ADDRESS")
	private String companyAddress;//公司地址
	
	@Column(name = "EMAIL" , length = 50)
	private String email;//公司邮箱
	

	@Column(name = "WEBSITE" , length = 50)
	private String website;//	公司网址
	
	@Column(name = "TELEPHONE" , length = 50)
	private String telephone;//	联系电话
	
	@Column(name = "COMPANY_NATURE" , length = 50)
	private String companyNature;//公司性质

	
	@Column(name = "COMPANY_SCALE" , length = 50)
	private String companyScale;//	公司规模
	
	@Column(name = "MAIN_PRODUCT" , length = 500)
	private String mainProduct;//	主要产品
	
	@Column(name = "COMPANY_SALES" )
	private double companySales;//	销售额（万元）
	
	@Column(name = "COMPANY_CREATE_DATE" )
	private Date companyCreateDate;//	成立时间
	
	@Column(name = "PROVINCE" , length = 50)
	private String province;//	所属区域（省）
	
	@Column(name = "CITY" , length = 50)
	private String city;//	所属区域（市/区）
	
	@Lob
	@Column(name = "REMARK")
	private String remark;//备注
	
	
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getRegisterCapital() {
		return registerCapital;
	}

	public void setRegisterCapital(String registerCapital) {
		this.registerCapital = registerCapital;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCompanyScale() {
		return companyScale;
	}

	public void setCompanyScale(String companyScale) {
		this.companyScale = companyScale;
	}

	public String getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

	public double getCompanySales() {
		return companySales;
	}

	public void setCompanySales(double companySales) {
		this.companySales = companySales;
	}

	public Date getCompanyCreateDate() {
		return companyCreateDate;
	}

	public void setCompanyCreateDate(Date companyCreateDate) {
		this.companyCreateDate = companyCreateDate;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCompanyNature() {
		return companyNature;
	}

	public void setCompanyNature(String companyNature) {
		this.companyNature = companyNature;
	}


	
}
