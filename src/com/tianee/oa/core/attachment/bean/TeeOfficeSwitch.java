package com.tianee.oa.core.attachment.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "office_switch")
public class TeeOfficeSwitch {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="office_switch_seq_gen")
	@SequenceGenerator(name="office_switch_seq_gen", sequenceName="office_switch_seq")
	private int sid; //主键
	
	@Column(name="flag")
	private int flag;//0：未转换  1：转换中 2：已转换  -1：转换失败
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="att_id")
	private TeeAttachment  attachment;//原附件
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="html_att_id")
	private TeeAttachment  htmlAtt;//html附件

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public TeeAttachment getAttachment() {
		return attachment;
	}

	public void setAttachment(TeeAttachment attachment) {
		this.attachment = attachment;
	}

	public TeeAttachment getHtmlAtt() {
		return htmlAtt;
	}

	public void setHtmlAtt(TeeAttachment htmlAtt) {
		this.htmlAtt = htmlAtt;
	}
	
}
