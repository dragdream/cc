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
 * 调动管理
 * @author kakalion
 *
 */
@Entity
@Table(name="Human_Shift")
public class TeeHumanShift {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="Human_Shift_seq_gen")
	@SequenceGenerator(name="Human_Shift_seq_gen", sequenceName="Human_Shift_seq")
	private int sid;
	
	/**
	 * 晋升
	 * 平调
	 * 降级
	 * 其他
	 */
	private String sType;//调动类型
	
	private String sCause;//调动原因
	
	private Calendar sTime1;//调动日期
	
	private Calendar sTime2;//调动生效日期
	
	private String sFirstCompany;//调动前单位
	
	private String sLastCompany;//调动后单位
	
	private String sFirstPost;//调动前职位
	
	private String sLastPost;//调动后职位
	
	private String sFirstDept;//调动前部门
	
	private String sLastDept;//调动后部门
	
	
	@Lob()
	private String detail;//调动手续办理
	
	@Lob()
	private String remark;//备注
	
	@ManyToOne
	@Index(name="IDXad1aeda1459c4795945aa1e58ba")
	private TeeHumanDoc humanDoc;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSType() {
		return sType;
	}

	public void setSType(String type) {
		sType = type;
	}

	public String getSCause() {
		return sCause;
	}

	public void setSCause(String cause) {
		sCause = cause;
	}

	public Calendar getSTime1() {
		return sTime1;
	}

	public void setSTime1(Calendar time1) {
		sTime1 = time1;
	}

	public Calendar getSTime2() {
		return sTime2;
	}

	public void setSTime2(Calendar time2) {
		sTime2 = time2;
	}

	public String getSFirstCompany() {
		return sFirstCompany;
	}

	public void setSFirstCompany(String firstCompany) {
		sFirstCompany = firstCompany;
	}

	public String getSLastCompany() {
		return sLastCompany;
	}

	public void setSLastCompany(String lastCompany) {
		sLastCompany = lastCompany;
	}

	public String getSFirstPost() {
		return sFirstPost;
	}

	public void setSFirstPost(String firstPost) {
		sFirstPost = firstPost;
	}

	public String getSLastPost() {
		return sLastPost;
	}

	public void setSLastPost(String lastPost) {
		sLastPost = lastPost;
	}

	public String getSFirstDept() {
		return sFirstDept;
	}

	public void setSFirstDept(String firstDept) {
		sFirstDept = firstDept;
	}

	public String getSLastDept() {
		return sLastDept;
	}

	public void setSLastDept(String lastDept) {
		sLastDept = lastDept;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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
