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
 * 奖惩管理
 * @author kakalion
 *
 */
@Entity
@Table(name="HUMAN_SANCTION")
public class TeeHumanSanction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HUMAN_SANCTION_seq_gen")
	@SequenceGenerator(name="HUMAN_SANCTION_seq_gen", sequenceName="HUMAN_SANCTION_seq")
	private int sid;
	
	/**
	 * 积极参加工作
	 * 不迟到不早退
	 * 违规操作
	 * 经常迟到早退
	 */
	private String sanType;//奖惩项目
	
	private Calendar sanDate;//奖惩日期
	
	private Calendar validDate;//生效日期
	
	private int pays;//奖罚金额
	
	@Lob()
	private String content;//说明
	
	@Lob()
	private String remark;//备注
	
	@ManyToOne
	@Index(name="IDXc8418131e44449d2b3c21a6c317")
	private TeeHumanDoc humanDoc;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public Calendar getSanDate() {
		return sanDate;
	}

	public void setSanDate(Calendar sanDate) {
		this.sanDate = sanDate;
	}

	public Calendar getValidDate() {
		return validDate;
	}

	public void setValidDate(Calendar validDate) {
		this.validDate = validDate;
	}

	public int getPays() {
		return pays;
	}

	public void setPays(int pays) {
		this.pays = pays;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getSanType() {
		return sanType;
	}

	public void setSanType(String sanType) {
		this.sanType = sanType;
	}
	
	
}
