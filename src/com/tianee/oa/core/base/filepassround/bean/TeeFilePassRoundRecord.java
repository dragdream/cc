package com.tianee.oa.core.base.filepassround.bean;

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
@Table(name="file_pass_round_record")
public class TeeFilePassRoundRecord {
	
	@Id
	@Column(name="ID")
	private String id;		//主键
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FILE_PASS_ROUND_ID")
	private TeeFilePassRound filePassRound; 	//传阅id
	
	@Column(name="CON_ID")
	private Integer conId;
	
	
	
	@Column(name="STATE")
	private Integer state;	//签约标志（1为已签阅 0为未签阅）
	
	@Column(name="READ_TIME")
	private Calendar readTime;	//签约时间

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setConId(Integer conId) {
		this.conId = conId;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public TeeFilePassRound getFilePassRound() {
		return filePassRound;
	}

	public void setFilePassRound(TeeFilePassRound filePassRound) {
		this.filePassRound = filePassRound;
	}

	public Integer getConId() {
		return conId;
	}

	public void setConId(int conId) {
		this.conId = conId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Calendar getReadTime() {
		return readTime;
	}

	public void setReadTime(Calendar readTime) {
		this.readTime = readTime;
	}

	@Override
	public String toString() {
		return "TeeFilePassRoundRecord [id=" + id + ", filePassRound="
				+ filePassRound + ", conId=" + conId + ", state=" + state
				+ ", readTime=" + readTime + "]";
	}

	
	
	
	
	
	

	
	
	
}
