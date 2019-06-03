package com.tianee.oa.subsys.salManage.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;


@SuppressWarnings("serial")
@Entity
@Table(name = "HR_SAL_FLOW")
public class TeeSalFlow {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HR_SAL_FLOW_seq_gen")
	@SequenceGenerator(name="HR_SAL_FLOW_seq_gen", sequenceName="HR_SAL_FLOW_seq")
	private int sid;//工资流程编号
	
	@ManyToOne()
	@Index(name="IDXca8e7568eea64a12bfb6fadc0fc")
	@JoinColumn(name="SAL_CREATER")
	private TeePerson salCreater;//创建人
	
	@Column(name="ACCOUNT_ID")
	private int accountId;//账套ID

	@Column(name="BEGIN_DATE")
	private Date beginDate;//流程起始日期
	
	@Column(name="END_DATE")
	private Date endDate;//流程结束日期
	
	@Lob
	@Column(name="CONTENT")
	private String  content;//备注
	
	@Column(name="SEND_TIME")
	private Date sendTime;//流程发起时间
	
	@Column(name="STYLE")
	private String style;//工资条默认选择字段
	
	@Column(name="ISSEND" ,columnDefinition="INT default 0" ,nullable=false)
	private int issend ;//工资流程的状态(1-已终止,0-执行中)
	
	@Column(name="SAL_YEAR")
	private int salYear;//年份
	
	@Column(name="SAL_MONTH")
	private int salMonth;//月份

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeePerson getSalCreater() {
		return salCreater;
	}

	public void setSalCreater(TeePerson salCreater) {
		this.salCreater = salCreater;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public int getIssend() {
		return issend;
	}

	public void setIssend(int issend) {
		this.issend = issend;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getSalYear() {
		return salYear;
	}

	public void setSalYear(int salYear) {
		this.salYear = salYear;
	}

	public int getSalMonth() {
		return salMonth;
	}

	public void setSalMonth(int salMonth) {
		this.salMonth = salMonth;
	}

	

	
}
