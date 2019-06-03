package com.tianee.oa.subsys.supervise.bean;

import java.util.Date;

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

import com.tianee.oa.core.org.bean.TeePerson;

//暂停 办结  回复申请
@Entity
@Table(name = "supervision_apply")
public class TeeSupervisionApply {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="supervision_apply_seq_gen")
	@SequenceGenerator(name="supervision_apply_seq_gen", sequenceName="supervision_apply_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@Column(name="status")
	private int  status ;//0待审批   1 同意  2 拒绝 
	
	@Column(name="content")
	private String  content ;//内容
	
	@Column(name="type")
	private int  type ;// 1暂停   2回复   3办结
	
	@Column(name="create_time")
	private Date  createTime ;//创建时间
	
	@Column(name="reason")
	private String   reason;//理由
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="creater_id")
	private TeePerson creater;//创建人
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="sup_id")
	private TeeSupervision sup;//关联的 任务
	
	
	
	public TeeSupervision getSup() {
		return sup;
	}

	public void setSup(TeeSupervision sup) {
		this.sup = sup;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public TeePerson getCreater() {
		return creater;
	}

	public void setCreater(TeePerson creater) {
		this.creater = creater;
	}

	
}
