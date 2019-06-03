package com.tianee.oa.core.base.vote.bean;
import java.util.Calendar;
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

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;


/**
 * 投票主表
 * @author syl
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "VOTE")
public class TeeVote {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="VOTE_seq_gen")
	@SequenceGenerator(name="VOTE_seq_gen", sequenceName="VOTE_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne()
	@Index(name="IDX3dcbb53285be49499e603d79385")
	@JoinColumn(name="PARENT_ID")
	private TeeVote parentVote;//投标项目

	@ManyToOne()
	@Index(name="IDX60dd64dee2a7450884f1a0b4af3")
	@JoinColumn(name="CREATE_USER_ID")
	private TeePerson createUser;//CREATE_USER(USER_ID)
	
	
	@ManyToMany(targetEntity=TeeDepartment.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="VOTE_DEPT_PRIV",        
			joinColumns={@JoinColumn(name="VOTE_ID")},       
			inverseJoinColumns={@JoinColumn(name="DEPT_UUID")}  ) 	
	@Index(name="VOTE_DEPT_PRIV_INDEX")
	private List<TeeDepartment> postDept;//发布权限 -部门
	
	
	@ManyToMany(targetEntity=TeePerson.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="VOTE_PERSON_PRIV",        
			joinColumns={@JoinColumn(name="VOTE_ID")},       
			inverseJoinColumns={@JoinColumn(name="USER_UUID")}  ) 	
	@Index(name="VOTE_PERSON_PRIV_INDEX")
	private List<TeePerson> postUser;//发布权限--人员
	
	
	@ManyToMany(targetEntity=TeeUserRole.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="VOTE_USER_ROLE_PRIV",        
			joinColumns={@JoinColumn(name="VOTE_ID")},       
			inverseJoinColumns={@JoinColumn(name="ROLE_UUID")}  ) 	
	@Index(name="VOTE_USER_ROLE_PRIV_INDEX")
	private List<TeeUserRole> postUserRole;//发布权限--角色
	
	@Column(name="SUBJECT",length=500)
	private String  subject;//标题
	
	@Lob
	@Column(name="CONTENT")
	private String  content;//标题CONTENT	CLOB	投票描述
	
	@Lob
	@Column(name="TEXTHTML")
	private String  textHtml;//TEXTHTML	CLOB	子投票项内容
	
	@Lob
	@Column(name="HTMLFORPREVIEW")
	private String  htmlForPreview;//预览内容	子投票项
	
	
	@Column(name="VOTE_TYPE",columnDefinition="char(1) default 0" )
	private String  voteType;//	Varchar（10）	 投票类型0-多选 1-单选 2-文本 3-多行 4-下拉
	
	@Column(name="MAX_NUM" ,columnDefinition="INT default 0" ,nullable=false)
	private int maxNum;//MAX_NUM Int	多选 可选最多选项
	
	@Column(name="MIN_NUM" ,columnDefinition="INT default 0" ,nullable=false)
	private int minNum;//MIN_NUM Int	多选 可选最少选项  0不控制
	

	@Column(name="ANONYMITY",columnDefinition="char(1) default 0" )
	private String  anonymity;//	ANONYMITY	Varchar（10）	是否允许匿名投票0：不允许  1：允许
	
	
	@Column(name="VIEW_PRIV",columnDefinition="char(1) default 0" )
	private String  viewPriv;//	VIEW_PRIV	CLOB	查看投票结果 0：投票后允许查看   1：投票前允许查看  2：不允许查看
	
	
	@Column(name="VOTE_TOP",columnDefinition="char(1) default 0" )
	private String  voteTop;//	VOTE_TOP	Varchar（10）	是否置顶      0-不置顶   1-置顶
	
	
	@Column(name="PUBLISH",columnDefinition="char(1) default 0" )
	private String  publish;//	PUBLISH	CLOB	是否发布  0-未发布；  1-已发布 ；2-终止（20150625添加）
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_DATE")
	private Calendar createDate;//CREATE_DATE	Date	新建时间
	
	@Temporal(TemporalType.DATE)
	@Column(name="ENGIN_DATE")
	private Date beginDate;//BENGIN_DATE	Date	有效开始日期
	
	@Temporal(TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;//END_DATE	Date	有效结束时间
	
	
	@Column(name="VOTE_NO" ,columnDefinition="INT default 0" ,nullable=false)
	private int voteNo;//排序号
	
	@Column(name="ITEM_NUM" ,columnDefinition="INT default 0" ,nullable=false)
	private int itemNum;//所有子项数量，只记录在父投票项

	
	@OneToMany(mappedBy="vote",fetch=FetchType.LAZY,cascade=CascadeType.ALL)//因为这里是双向的 一对多 所以要写mappedBy	
	private List<TeeVotePerson> votePersons;////投票人员

	

	@OneToMany(mappedBy="vote",fetch=FetchType.LAZY,cascade=CascadeType.ALL)//因为这里是双向的 一对多 所以要写mappedBy	
	private List<TeeVoteItem> voteItems;////投票项目
	
	@Column(name="REQUIRED" ,columnDefinition="INT default 0" ,nullable=false)
	private int required;//是否必填
	
	@Column(name="IFCONTENT" ,columnDefinition="INT default 0" ,nullable=false)
	private int ifContent;//是否有说明
	
	
	
	public int getRequired() {
		return required;
	}

	public void setRequired(int required) {
		this.required = required;
	}

	public int getIfContent() {
		return ifContent;
	}

	public void setIfContent(int ifContent) {
		this.ifContent = ifContent;
	}

	public String getTextHtml() {
		return textHtml;
	}

	public void setTextHtml(String textHtml) {
		this.textHtml = textHtml;
	}

	public String getHtmlForPreview() {
		return htmlForPreview;
	}

	public void setHtmlForPreview(String htmlForPreview) {
		this.htmlForPreview = htmlForPreview;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeVote getParentVote() {
		return parentVote;
	}

	public void setParentVote(TeeVote parentVote) {
		this.parentVote = parentVote;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public List<TeeDepartment> getPostDept() {
		return postDept;
	}

	public void setPostDept(List<TeeDepartment> postDept) {
		this.postDept = postDept;
	}

	public List<TeePerson> getPostUser() {
		return postUser;
	}

	public void setPostUser(List<TeePerson> postUser) {
		this.postUser = postUser;
	}

	public List<TeeUserRole> getPostUserRole() {
		return postUserRole;
	}

	public void setPostUserRole(List<TeeUserRole> postUserRole) {
		this.postUserRole = postUserRole;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getVoteType() {
		return voteType;
	}

	public void setVoteType(String voteType) {
		this.voteType = voteType;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public int getMinNum() {
		return minNum;
	}

	public void setMinNum(int minNum) {
		this.minNum = minNum;
	}

	public String getAnonymity() {
		return anonymity;
	}

	public void setAnonymity(String anonymity) {
		this.anonymity = anonymity;
	}

	public String getViewPriv() {
		return viewPriv;
	}

	public void setViewPriv(String viewPriv) {
		this.viewPriv = viewPriv;
	}

	public String getVoteTop() {
		return voteTop;
	}

	public void setVoteTop(String voteTop) {
		this.voteTop = voteTop;
	}

	public String getPublish() {
		return publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public Calendar getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getVoteNo() {
		return voteNo;
	}

	public void setVoteNo(int voteNo) {
		this.voteNo = voteNo;
	}
	public List<TeeVotePerson> getVotePersons() {
		return votePersons;
	}

	public void setVotePersons(List<TeeVotePerson> votePersons) {
		this.votePersons = votePersons;
	}

	public List<TeeVoteItem> getVoteItems() {
		return voteItems;
	}

	public void setVoteItems(List<TeeVoteItem> voteItems) {
		this.voteItems = voteItems;
	}
	
	
	
		
}



