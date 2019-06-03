package com.tianee.oa.subsys.supervise.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;
//督办任务
@Entity
@Table(name = "supervision")
public class TeeSupervision {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="supervision_seq_gen")
	@SequenceGenerator(name="supervision_seq_gen", sequenceName="supervision_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@Column(name="sup_name")
	private String supName ;//名称
	
	
	@Column(name="status")
	private int status ;//状态   0未发布   1进行中    2暂停申请中  3暂停中  4恢复申请中   5办结申请中  6已办结     7待接收
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="type_id")
	private TeeSupervisionType type;//类型
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="leader_id")
	private TeePerson leader;//责任领导
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="manager_id")
	private TeePerson manager;//主办人
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="sup_assist",
			joinColumns={@JoinColumn(name="sup_id")},
			inverseJoinColumns={@JoinColumn(name="assist_id")})
	private Set<TeePerson> assists = new HashSet<TeePerson>(0);//协办人
	
	
	@Column(name="begin_time")
	private Date beginTime;//开始时间
	
	@Column(name="end_time")
	private Date endTime;//结束时间
	
	
	@Column(name="real_end_time")
	private Date realEndTime;//实际结束时间
	
	
	

	@Column(name="content")
	private String content;//督办内容
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="creater_id")
	private TeePerson creater;//创建人
	
	@Column(name="creater_time")
	private Date createrTime;//创建时间
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parent_id")
	private TeeSupervision parent;//父任务
	
	public TeeSupervision getParent() {
		return parent;
	}

	public void setParent(TeeSupervision parent) {
		this.parent = parent;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	public Date getRealEndTime() {
		return realEndTime;
	}

	public void setRealEndTime(Date realEndTime) {
		this.realEndTime = realEndTime;
	}
	
	public TeeSupervisionType getType() {
		return type;
	}

	public void setType(TeeSupervisionType type) {
		this.type = type;
	}

	public TeePerson getLeader() {
		return leader;
	}

	public void setLeader(TeePerson leader) {
		this.leader = leader;
	}

	public TeePerson getManager() {
		return manager;
	}

	public void setManager(TeePerson manager) {
		this.manager = manager;
	}

	public Set<TeePerson> getAssists() {
		return assists;
	}

	public void setAssists(Set<TeePerson> assists) {
		this.assists = assists;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TeePerson getCreater() {
		return creater;
	}

	public void setCreater(TeePerson creater) {
		this.creater = creater;
	}

	public Date getCreaterTime() {
		return createrTime;
	}

	public void setCreaterTime(Date createrTime) {
		this.createrTime = createrTime;
	}

	
	
}
