package com.tianee.oa.core.base.officeProducts.bean;
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

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 用品分类
 * @author kakalion
 *
 */
@Entity
@Table(name="OFFICE_CATEGORY")
public class TeeOfficeCategory{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="OFFICE_CATEGORY_seq_gen")
	@SequenceGenerator(name="OFFICE_CATEGORY_seq_gen", sequenceName="OFFICE_CATEGORY_seq")
	private int sid;
	
	@Column(name="CAT_NAME")
	private String catName;//类别名称
	
	@ManyToOne()
	@Index(name="IDXd13b11f1c58347698647107e671")
	@JoinColumn(name="depository_ID")
	private TeeOfficeDepository officeDepository;//用品库
	
	@ManyToOne()
	@Index(name="IDXcde0ed5176204ba8a382cec5635")
	@JoinColumn(name="CREATE_USER_ID")
	private TeePerson createUser;//创建人

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public TeeOfficeDepository getOfficeDepository() {
		return officeDepository;
	}

	public void setOfficeDepository(TeeOfficeDepository officeDepository) {
		this.officeDepository = officeDepository;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getSid() {
		return sid;
	}
	
}
