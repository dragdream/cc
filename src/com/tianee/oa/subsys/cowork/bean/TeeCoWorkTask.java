package com.tianee.oa.subsys.cowork.bean;
import org.hibernate.annotations.Index;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="CO_WORK_TASK")
public class TeeCoWorkTask {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CO_WORK_TASK_seq_gen")
	@SequenceGenerator(name="CO_WORK_TASK_seq_gen", sequenceName="CO_WORK_TASK_seq")
	private int sid;
	
	@Column(name="TASK_TITLE")
	private String taskTitle;
	
	@Column(name="TASK_TYPE")
	private int taskType;//任务来源   0：默认  1：部门计划   2：上级任务
	
	@Column(name="RANGE_TIMES")
	private String rangeTimes;//评估工时
	
	@Column(name="REL_TIMES")
	private String relTimes;//实际工时
	
	@Column(name="CR_TIME")
	private Calendar createTime;
	
	@Column(name="START_TIME")
	private Calendar startTime;
	
	@Column(name="END_TIME")
	private Calendar endTime;
	
	@Column(name="REL_START_TIME")
	private Calendar relStartTime;
	
	@Column(name="REL_END_TIME")
	private Calendar relEndTime;
	
	@ManyToOne
	@Index(name="IDX568da6da48a04471ae6d3594315")
	@JoinColumn(name="CR_USER_ID")
	private TeePerson createUser;//布置人
	
	@ManyToOne
	@Index(name="IDXf8cc215a631f4dda9d2b45d810f")
	@JoinColumn(name="AUDITOR_ID")
	private TeePerson auditor;//审批人
	
	@ManyToOne
	@Index(name="IDX23aca0309b894ce7b85c4ddf5bc")
	@JoinColumn(name="CHARGER_ID")
	private TeePerson charger;//负责人
	
	@ManyToMany
	private Set<TeePerson> joiners = new HashSet();//参与人
	
	@Column(name="CONTENT")
	@Lob
	private String content;//会议内容
	
	@Column(name="STANDARD")
	@Lob
	private String standard;//奖罚标准
	
	@Column(name="LEADER_REMARK")
	@Lob
	private String leaderRemark;//领导批示
	
	@Column(name="STATUS")
	private int status;//状态，0：等待接收  1：等待审批  2：审批不通过  3：拒绝接收  4：进行中  5：等待审核 6：审核不通过 7：任务撤销  8:已完成 9:任务失败

	@Column(name="PROGRESS")
	private int progress;//进度百分比
	
	@Column(name="SCORE")
	private int score;//任务评分
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX61a288d66dac4530ae9ef2b3653")
	private TeeCoWorkTask parentTask;//父任务
	
	@Column(name="TASK_PATH",columnDefinition="varchar(500)")
	private String taskPath;//任务路径
	
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.REMOVE,mappedBy="task")
	private List<TeeTaskSchedule> taskSchedule = new ArrayList();

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public String getRangeTimes() {
		return rangeTimes;
	}

	public void setRangeTimes(String rangeTimes) {
		this.rangeTimes = rangeTimes;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
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

	public Calendar getRelStartTime() {
		return relStartTime;
	}

	public void setRelStartTime(Calendar relStartTime) {
		this.relStartTime = relStartTime;
	}

	public Calendar getRelEndTime() {
		return relEndTime;
	}

	public void setRelEndTime(Calendar relEndTime) {
		this.relEndTime = relEndTime;
	}

	public TeePerson getAuditor() {
		return auditor;
	}

	public void setAuditor(TeePerson auditor) {
		this.auditor = auditor;
	}

	public TeePerson getCharger() {
		return charger;
	}

	public void setCharger(TeePerson charger) {
		this.charger = charger;
	}

	public Set<TeePerson> getJoiners() {
		return joiners;
	}

	public void setJoiners(Set<TeePerson> joiners) {
		this.joiners = joiners;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getLeaderRemark() {
		return leaderRemark;
	}

	public void setLeaderRemark(String leaderRemark) {
		this.leaderRemark = leaderRemark;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public TeeCoWorkTask getParentTask() {
		return parentTask;
	}

	public void setParentTask(TeeCoWorkTask parentTask) {
		this.parentTask = parentTask;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public String getRelTimes() {
		return relTimes;
	}

	public void setRelTimes(String relTimes) {
		this.relTimes = relTimes;
	}

	public String getTaskPath() {
		return taskPath;
	}

	public void setTaskPath(String taskPath) {
		this.taskPath = taskPath;
	}

	public List<TeeTaskSchedule> getTaskSchedule() {
		return taskSchedule;
	}

	public void setTaskSchedule(List<TeeTaskSchedule> taskSchedule) {
		this.taskSchedule = taskSchedule;
	}
	
}
