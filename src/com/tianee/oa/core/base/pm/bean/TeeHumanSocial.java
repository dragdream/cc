package com.tianee.oa.core.base.pm.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="HUMAN_SOCIAL")
public class TeeHumanSocial {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HUMAN_SOCIAL_seq_gen")
	@SequenceGenerator(name="HUMAN_SOCIAL_seq_gen", sequenceName="HUMAN_SOCIAL_seq")
	private int sid;
	
	private String memberName;//成员名称
	
	/**
	 * 母子
	 * 父子
	 * 兄妹
	 * 姐妹
	 * 兄弟
	 * 其他
	 */
	private String relation;//与本人关系
	
	private Calendar birthday;//出生日期
	
	/**
	 * 群众
	 * 共青团员
	 * 中共党员
	 * 中共预备党员
	 * 无党派人士
	 */
	private String policy;//政治面貌
	
	private String occupation;//职业
	
	private String post;//职位
	
	private String telNoPersonal;//个人电话
	
	private String telNoCompany;//单位电话
	
	private String workAt;//工作单位
	
	private String workAddress;//单位地址
	
	private String homeAddress;//家庭地址
	@Lob()
	private String remark;//备注
	
	@ManyToOne
	@Index(name="IDXd6e491e4fb37486888358f5a6ad")
	private TeeHumanDoc humanDoc;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public Calendar getBirthday() {
		return birthday;
	}

	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getTelNoPersonal() {
		return telNoPersonal;
	}

	public void setTelNoPersonal(String telNoPersonal) {
		this.telNoPersonal = telNoPersonal;
	}

	public String getTelNoCompany() {
		return telNoCompany;
	}

	public void setTelNoCompany(String telNoCompany) {
		this.telNoCompany = telNoCompany;
	}

	public String getWorkAt() {
		return workAt;
	}

	public void setWorkAt(String workAt) {
		this.workAt = workAt;
	}

	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public TeeHumanDoc getHumanDoc() {
		return humanDoc;
	}

	public void setHumanDoc(TeeHumanDoc humanDoc) {
		this.humanDoc = humanDoc;
	}
	
	
}
