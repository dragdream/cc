package com.tianee.oa.core.base.examine.bean;
import org.hibernate.annotations.Index;

import java.util.Date;
import java.util.List;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

@SuppressWarnings("serial")
@Entity
@Table(name = "EXAMINE_TASK")
public class TeeExamineTask {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="EXAMINE_TASK_seq_gen")
	@SequenceGenerator(name="EXAMINE_TASK_seq_gen", sequenceName="EXAMINE_TASK_seq")
	@Column(name="SID")
	private int sid;//SID	int(11)	自增字段	是	自增
	
	@Column(name="TASK_TITLE")
	private String taskTitle;//TASK_TITLE	varchar(254)	任务名称	
	
	@ManyToMany(targetEntity=TeePerson.class,   //考核人	     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="EXA_TASK_RANK_P",        
			joinColumns={@JoinColumn(name="EXAMINE_TASK_ID")},       
			inverseJoinColumns={@JoinColumn(name="USER_UUID")}  ) 	
	@Index(name="EXAMINE_TASK_RANKMAN_INDEX")
	private List<TeePerson> rankman;//
	
	@ManyToMany(targetEntity=TeePerson.class,   //被考核人		   
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="EXA_TASK_PARTI_P",        
			joinColumns={@JoinColumn(name="EXAMINE_TASK_ID")},       
			inverseJoinColumns={@JoinColumn(name="USER_UUID")}  ) 	
	@Index(name="EXAMINE_TASK_PARTICIPANT_PERSON_INDEX")
	private List<TeePerson> participant;//

	@Column(name="IS_SELF_ASSESSMENT" ,columnDefinition="char(1) default '0'")
	private String isSelfAssessment;//IS_SELF_ASSESSMENT	Char（1）	是否需要自评		0：不需要自评1：需要
	
	@Column(name="TASK_FLAG" ,columnDefinition="char(1) default '0'")
	private String taskFlag;//TASK_FLAG	Char（1）	按照管理范围		0：不按1：按
	
	@Column(name="ANONYMITY" ,columnDefinition="char(1) default '0'")
	private String anonymity;//ANONYMITY	Char（1）	是否匿名		0：实名1：匿名
	
	
	@Column(name="TASK_BEGIN")
	@Temporal(TemporalType.DATE)
	private Date taskBegin;//TASK_BEGIN	DATETIME	开始时间	
	
	@Column(name="TASK_END")
	@Temporal(TemporalType.DATE)
	private Date taskEnd;//TASK_TIME	DATETIME	结束时间		
	
	
	@Column(name="SEND_TIME")
	private long sendTime;///SEND_TIME	DATETIME	发布时间
	
	@Lob 
	@Column(name="TASK_DESC")
	private String taskDesc;//TASK_DESC	TEXT	任务描述		
	
	
	@ManyToOne()
	@Index(name="IDX7ac2612c719347fb954b65cba94")
	@JoinColumn(name="GROUP_ID")
	private TeeExamineGroup group;//EXAMINE_ID	TeeScoreGroup	考核指标集Id	
	
	
	@ManyToOne()
	@Index(name="IDXde28cb29bfc2485fb8c522c38fc")
	@JoinColumn(name="CREATER")
	private TeePerson creater;//EXAMINE_ID	TeePerson 创建人员
	
	
	
	@OneToMany(mappedBy="task",fetch=FetchType.LAZY,cascade=CascadeType.ALL)//因为这里是双向的 一对多 所以要写mappedBy
	private List<TeeExamineSelfData> selfData;//自评数据
	
	
	@OneToMany(mappedBy="task",fetch=FetchType.LAZY,cascade=CascadeType.ALL)//因为这里是双向的 一对多 所以要写mappedBy
	private List<TeeExamineData> examineData;//考核数据

	@Lob
	@Column(name="WEIGHT_")
	private String weight;//权重
	
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


	public List<TeePerson> getRankman() {
		return rankman;
	}


	public void setRankman(List<TeePerson> rankman) {
		this.rankman = rankman;
	}


	public List<TeePerson> getParticipant() {
		return participant;
	}


	public void setParticipant(List<TeePerson> participant) {
		this.participant = participant;
	}


	public String getIsSelfAssessment() {
		return isSelfAssessment;
	}


	public void setIsSelfAssessment(String isSelfAssessment) {
		this.isSelfAssessment = isSelfAssessment;
	}


	public String getTaskFlag() {
		return taskFlag;
	}


	public void setTaskFlag(String taskFlag) {
		this.taskFlag = taskFlag;
	}


	public String getAnonymity() {
		return anonymity;
	}


	public void setAnonymity(String anonymity) {
		this.anonymity = anonymity;
	}


	public Date getTaskBegin() {
		return taskBegin;
	}


	public void setTaskBegin(Date taskBegin) {
		this.taskBegin = taskBegin;
	}

	public Date getTaskEnd() {
		return taskEnd;
	}


	public void setTaskEnd(Date taskEnd) {
		this.taskEnd = taskEnd;
	}


	public long getSendTime() {
		return sendTime;
	}


	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}


	public String getTaskDesc() {
		return taskDesc;
	}


	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}


	public TeeExamineGroup getGroup() {
		return group;
	}


	public void setGroup(TeeExamineGroup group) {
		this.group = group;
	}


	public TeePerson getCreater() {
		return creater;
	}


	public void setCreater(TeePerson creater) {
		this.creater = creater;
	}


	public List<TeeExamineSelfData> getSelfData() {
		return selfData;
	}


	public void setSelfData(List<TeeExamineSelfData> selfData) {
		this.selfData = selfData;
	}


	public List<TeeExamineData> getExamineData() {
		return examineData;
	}


	public void setExamineData(List<TeeExamineData> examineData) {
		this.examineData = examineData;
	}


	public String getWeight() {
		return weight;
	}


	public void setWeight(String weight) {
		this.weight = weight;
	}

	
}
