package com.tianee.oa.subsys.schedule.bean;
import org.hibernate.annotations.Index;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.cowork.bean.TeeTaskSchedule;

@Entity
@Table(name="SCHEDULE")
public class TeeSchedule {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@Column(name="TITLE")
	private String title;//标题
	
	@Column(name="TYPE")
	private int type;//类型 1：个人计划  2：部门计划 3：公司计划
	
	@Column(name="RANGE_TYPE")
	private int rangeType;//时间类型  1：日计划 2：周计划  3：月计划  4：季度计划  5：半年计划  6：年计划
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX05917012bea74ac98ae3eb57e5e")
	@JoinColumn(name="USER_ID")
	private TeePerson user;//人员
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX5750e973410546348710452e447")
	@JoinColumn(name="DEPT_ID")
	private TeeDepartment dept;//部门
	
	@Column(name="RANGE_DESC")
	private String rangeDesc;//时间描述
	
	@Column(name="CR_TIME")
	private Calendar crTime;//创建时间
	
	@Column(name="TIME1")
	private Calendar time1;//时间段1
	
	@Column(name="TIME2")
	private Calendar time2;//时间段2
	
	@Lob
	@Column(name="CONTENT")
	private String content;//内容
	
	@Lob
	@Column(name="SUMMARIZE")
	private String summarize;//总结
	
	@Column(name="FLAG")
	private int flag;//0:进行中 1：已完成
	
	@Column(name="FINISH_TIME")
	private Calendar finishTime;//完成时间
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="SCHEDULE_MANAGER_USER_ID_INX")
	@JoinColumn(name="MANAGER_USER_ID")
	private TeePerson managerUser;//计划负责人
	/**
	 * 所汇报的
	 */
	@OneToMany(mappedBy="schedule",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
	List<TeeScheduleReported> reportedRanges = new ArrayList();
	
	/**
	 * 所分享的
	 */
	@OneToMany(mappedBy="schedule",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
	List<TeeScheduleShared> sharedRanges = new ArrayList();
	
	/**
	 * 批注
	 */
	@OneToMany(mappedBy="schedule",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
	List<TeeScheduleAnnotations> annotations = new ArrayList();
	
	/**
	 * 评论
	 */
	@OneToMany(mappedBy="schedule",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
	List<TeeScheduleComment> comments = new ArrayList();

	
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.REMOVE,mappedBy="schedule")
	private List<TeeTaskSchedule> taskSchedule = new ArrayList();
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRangeType() {
		return rangeType;
	}

	public void setRangeType(int rangeType) {
		this.rangeType = rangeType;
	}

	public TeePerson getUser() {
		return user;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}

	public TeeDepartment getDept() {
		return dept;
	}

	public void setDept(TeeDepartment dept) {
		this.dept = dept;
	}

	public String getRangeDesc() {
		return rangeDesc;
	}

	public void setRangeDesc(String rangeDesc) {
		this.rangeDesc = rangeDesc;
	}

	public Calendar getTime1() {
		return time1;
	}

	public void setTime1(Calendar time1) {
		this.time1 = time1;
	}

	public Calendar getTime2() {
		return time2;
	}

	public void setTime2(Calendar time2) {
		this.time2 = time2;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSummarize() {
		return summarize;
	}

	public void setSummarize(String summarize) {
		this.summarize = summarize;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Calendar getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Calendar finishTime) {
		this.finishTime = finishTime;
	}

	public TeePerson getManagerUser() {
		return managerUser;
	}

	public void setManagerUser(TeePerson managerUser) {
		this.managerUser = managerUser;
	}

	public List<TeeScheduleReported> getReportedRanges() {
		return reportedRanges;
	}

	public void setReportedRanges(List<TeeScheduleReported> reportedRanges) {
		this.reportedRanges = reportedRanges;
	}

	public List<TeeScheduleShared> getSharedRanges() {
		return sharedRanges;
	}

	public void setSharedRanges(List<TeeScheduleShared> sharedRanges) {
		this.sharedRanges = sharedRanges;
	}

	public List<TeeScheduleAnnotations> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<TeeScheduleAnnotations> annotations) {
		this.annotations = annotations;
	}

	public List<TeeScheduleComment> getComments() {
		return comments;
	}

	public void setComments(List<TeeScheduleComment> comments) {
		this.comments = comments;
	}

	public Calendar getCrTime() {
		return crTime;
	}

	public void setCrTime(Calendar crTime) {
		this.crTime = crTime;
	}

	public List<TeeTaskSchedule> getTaskSchedule() {
		return taskSchedule;
	}

	public void setTaskSchedule(List<TeeTaskSchedule> taskSchedule) {
		this.taskSchedule = taskSchedule;
	}
	
	
}
