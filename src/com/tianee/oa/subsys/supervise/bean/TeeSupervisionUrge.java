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

//任务催办
@Entity
@Table(name = "sup_urge")
public class TeeSupervisionUrge {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="sup_urge_seq_gen")
	@SequenceGenerator(name="sup_urge_seq_gen", sequenceName="sup_urge_seq")
	@Column(name="SID")
	private int sid;//自增id
	

	@Column(name="content")
	private String content ;//内容
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="creater_id")
	private TeePerson creater;//创建人
	
	
	@Column(name="create_time")
	private Date createTime;//创建时间
	
	
	@Column(name="is_include_children")
	private int isIncludeChildren;//是否包含下级任务
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="sup_id")
	private TeeSupervision sup;//任务对象
	
	
	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
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




	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public int getIsIncludeChildren() {
		return isIncludeChildren;
	}


	public void setIsIncludeChildren(int isIncludeChildren) {
		this.isIncludeChildren = isIncludeChildren;
	}


	public TeeSupervision getSup() {
		return sup;
	}


	public void setSup(TeeSupervision sup) {
		this.sup = sup;
	}


	
	
	
}
