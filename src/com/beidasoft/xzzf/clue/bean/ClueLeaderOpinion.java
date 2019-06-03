package com.beidasoft.xzzf.clue.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_CLUE_LEADER_OPINION") //领导意见表    （用于提交审批后 存放所有的领导意见List集合）
public class ClueLeaderOpinion {

	@Id
	@Column(name = "ID")
	private String id; // 线索管理领导意见表主键ID

	@Column(name = "CLUE_ID")
	private String clueId;//对应的线索主键ID

	@Column(name = "LEADERS_OPINION")
	private String leadersOpinion;//领导意见

	@Column(name = "LEADERS_ID")
	private String leadersId;//领导ID

	@Column(name = "LEADERS_NAME")
	private String leadersName;//领导名

	@Column(name = "CREATE_TIME")
	private Date createTime;//创建时间
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClueId() {
		return clueId;
	}

	public void setClueId(String clueId) {
		this.clueId = clueId;
	}

	public String getLeadersOpinion() {
		return leadersOpinion;
	}

	public void setLeadersOpinion(String leadersOpinion) {
		this.leadersOpinion = leadersOpinion;
	}

	public String getLeadersId() {
		return leadersId;
	}

	public void setLeadersId(String leadersId) {
		this.leadersId = leadersId;
	}

	public String getLeadersName() {
		return leadersName;
	}

	public void setLeadersName(String leadersName) {
		this.leadersName = leadersName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
