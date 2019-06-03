package com.tianee.oa.subsys.crm.core.customer.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;


@Entity
@Table(name="CRM_SALE_FOLLOW")
public class TeeCrmSaleFollow{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_SALE_FOLLOW_seq_gen")
	@SequenceGenerator(name="CRM_SALE_FOLLOW_seq_gen", sequenceName="CRM_SALE_FOLLOW_seq")
	@Column(name="SID")
	private int sid;//Sid	int	11	否	主键
	
	@ManyToOne()
	@Index(name="IDX7dc8883df00b446c8771a830a30")
	@JoinColumn(name="CUSTOMER_ID")
	private TeeCrmCustomerInfo customer;//Custom_id	int	1	否	外键（所属客户id）
	
	@Column(name="CUSTOM_TYPE")
	private String customType;//Custom_type	varchar	255	是	客户类型
	
	@ManyToOne()
	@Index(name="IDXcb179ef0b69d4149aff6db2765f")
	@JoinColumn(name="ADD_USER_ID")
	private TeePerson addPerson;//Add_person_id	Int	11	否	添加人id Add_person_name	Int	11	否	添加人姓名
	
	@Column(name="CUSTOM_LEVEL")
	private String customLevel;//Custom_level	Varchar	255	是	客户程度
	
	@ManyToOne()
	@Index(name="IDX201b4fcb9550403db7f993edde2")
	@JoinColumn(name="CONTANT_USER")
	private TeeCrmContactUser contantUser;//Contants_id	Int	11	否	联系人Id Contants_name	Varchar	255	否	联系人name
	
	@Column(name="FOLLOW_TYPE")
	private String followType;//Follow_type	Varchar	255	否	跟踪方式（电话，qq,书函，面谈，会议）
	
	@Column(name="FOLLOW_CONTENT" ,length=1000)
	private String followContent;//Follow_content	Varchar	2000	否	跟踪内容
	
	@Column(name="FOLLOW_DATE")
	private Calendar followDate;//Follow_date	Calendar		否	跟踪时间
	
	@Column(name="FOLLOW_RESULT")
	@Lob()
	private String followResult;//Follow_result	Varchar	2000	是	跟踪结果
	
	@ManyToOne()
	@Index(name="IDXa2f7d12965ec47d0a017f67d94c")
	@JoinColumn(name="NEXT_FOLLOW_USER_ID")
	private TeeCrmContactUser nextFollowUser;//nextFollowUser
	
	@Column(name="NEXT_FOLLOW_TIME")
	private Calendar nextFollowTime;//Next_follow_time	Calendar		是	下次跟踪时间
	
	@Column(name="IS_REMIND",columnDefinition="char(1)")
	private char isRemind;//Is_remind	Char	1	是	是否提醒
	
	@Column(name="NEXT_FOLLOW_CONTENT" ,length=1000)
	private String nextFollowContent;//下次跟踪内容
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public TeeCrmCustomerInfo getCustomer() {
		return customer;
	}
	public void setCustomer(TeeCrmCustomerInfo customer) {
		this.customer = customer;
	}
	public String getCustomType() {
		return customType;
	}
	public void setCustomType(String customType) {
		this.customType = customType;
	}
	public TeePerson getAddPerson() {
		return addPerson;
	}
	public void setAddPerson(TeePerson addPerson) {
		this.addPerson = addPerson;
	}
	public String getCustomLevel() {
		return customLevel;
	}
	public void setCustomLevel(String customLevel) {
		this.customLevel = customLevel;
	}
	public TeeCrmContactUser getContantUser() {
		return contantUser;
	}
	public void setContantUser(TeeCrmContactUser contantUser) {
		this.contantUser = contantUser;
	}
	public String getFollowType() {
		return followType;
	}
	public void setFollowType(String followType) {
		this.followType = followType;
	}
	public String getFollowContent() {
		return followContent;
	}
	public void setFollowContent(String followContent) {
		this.followContent = followContent;
	}
	public Calendar getFollowDate() {
		return followDate;
	}
	public void setFollowDate(Calendar followDate) {
		this.followDate = followDate;
	}
	public String getFollowResult() {
		return followResult;
	}
	public void setFollowResult(String followResult) {
		this.followResult = followResult;
	}
	public Calendar getNextFollowTime() {
		return nextFollowTime;
	}
	public void setNextFollowTime(Calendar nextFollowTime) {
		this.nextFollowTime = nextFollowTime;
	}
	public char getIsRemind() {
		return isRemind;
	}
	public void setIsRemind(char isRemind) {
		this.isRemind = isRemind;
	}
	public TeeCrmContactUser getNextFollowUser() {
		return nextFollowUser;
	}
	public void setNextFollowUser(TeeCrmContactUser nextFollowUser) {
		this.nextFollowUser = nextFollowUser;
	}
	public String getNextFollowContent() {
		return nextFollowContent;
	}
	public void setNextFollowContent(String nextFollowContent) {
		this.nextFollowContent = nextFollowContent;
	}

	
}
