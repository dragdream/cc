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

/**
 * 技能管理
 * @author kakalion
 *
 */
@Entity
@Table(name="Human_Skill")
public class TeeHumanSkill {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="Human_Skill_seq_gen")
	@SequenceGenerator(name="Human_Skill_seq_gen", sequenceName="Human_Skill_seq")
	private int sid;
	
	private String skillName;//技能名称
	
	/**
	 * 是
	 * 否
	 */
	private String skillSpecial;//特种技能
	
	private String skillLevel;//技能级别
	
	/**
	 * 有
	 * 无
	 */
	private String skillCert;//技能证
	
	private Calendar startTime;//发放日期
	
	private Calendar endTime;//结束日期
	
	private String sendCompany;//发送单位
	@Lob()
	private String remark;//备注
	
	@ManyToOne
	@Index(name="IDX7c712bff949a4823ba2df529aa2")
	private TeeHumanDoc humanDoc;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getSkillSpecial() {
		return skillSpecial;
	}

	public void setSkillSpecial(String skillSpecial) {
		this.skillSpecial = skillSpecial;
	}

	public String getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(String skillLevel) {
		this.skillLevel = skillLevel;
	}

	public String getSkillCert() {
		return skillCert;
	}

	public void setSkillCert(String skillCert) {
		this.skillCert = skillCert;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public String getSendCompany() {
		return sendCompany;
	}

	public void setSendCompany(String sendCompany) {
		this.sendCompany = sendCompany;
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
