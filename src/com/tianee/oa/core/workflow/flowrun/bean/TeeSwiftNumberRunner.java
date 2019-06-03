package com.tianee.oa.core.workflow.flowrun.bean;

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
 * 动态文号执行
 * @author kakalion
 *
 */
@Entity
@Table(name="SWIFT_NUMBER_RUN")
public class TeeSwiftNumberRunner {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="SWIFT_NUMBER_RUN_seq_gen")
	@SequenceGenerator(name="SWIFT_NUMBER_RUN_seq_gen", sequenceName="SWIFT_NUMBER_RUN_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="GROUP_NAME")
	private String groupName;//实际组名称  
	
	@Column(name="YEAR_")
	private int year;//年
	
	@Column(name="MONTH_")
	private int month;//月
	
	@Column(name="DATE_")
	private int date;//日期
	
	@Column(name="REAL_NUM_STYLE")
	private String realNumberStyle;//实际流水号样式   全名称
	
	@Column(name="NUMBER_")
	private int number;//当前编号
	
	@ManyToOne()
	@JoinColumn(name="USER_ID")
	private TeePerson user;//创建人

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public String getRealNumberStyle() {
		return realNumberStyle;
	}

	public void setRealNumberStyle(String realNumberStyle) {
		this.realNumberStyle = realNumberStyle;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public TeePerson getUser() {
		return user;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}
	
}
