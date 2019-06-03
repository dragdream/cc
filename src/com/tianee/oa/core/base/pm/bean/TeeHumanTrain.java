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
 * 培训管理
 * @author kakalion
 *
 */
@Entity
@Table(name="HUMAN_TRAIN")
public class TeeHumanTrain {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HUMAN_TRAIN_seq_gen")
	@SequenceGenerator(name="HUMAN_TRAIN_seq_gen", sequenceName="HUMAN_TRAIN_seq")
	private int sid;
	
	private String traSubject;//培训主题
	
	private double traPays;//培训费用
	
	private Calendar startTime;//开始日期
	
	private Calendar endTime;//结束日期
	
	private String traType;//培训类型
	
	private String traCert;//培训证书
	
	private String traPosition;//培训地点
	@Lob()
	private String traContent;//培训内容
	@Lob()
	private String remark;//备注
	
	@ManyToOne
	@Index(name="IDX0e62a2a355b74337a96f5315dbb")
	private TeeHumanDoc humanDoc;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTraSubject() {
		return traSubject;
	}

	public void setTraSubject(String traSubject) {
		this.traSubject = traSubject;
	}

	public double getTraPays() {
		return traPays;
	}

	public void setTraPays(double traPays) {
		this.traPays = traPays;
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

	public String getTraType() {
		return traType;
	}

	public void setTraType(String traType) {
		this.traType = traType;
	}

	public String getTraCert() {
		return traCert;
	}

	public void setTraCert(String traCert) {
		this.traCert = traCert;
	}

	public String getTraPosition() {
		return traPosition;
	}

	public void setTraPosition(String traPosition) {
		this.traPosition = traPosition;
	}

	public String getTraContent() {
		return traContent;
	}

	public void setTraContent(String traContent) {
		this.traContent = traContent;
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
