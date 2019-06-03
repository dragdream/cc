package com.tianee.oa.core.base.notify.bean;
import org.hibernate.annotations.Index;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;
@Entity
@Table(name = "NotifyInfo")
public class TeeNotifyInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="NotifyInfo_seq_gen")
	@SequenceGenerator(name="NotifyInfo_seq_gen", sequenceName="NotifyInfo_seq")
	@Column(name = "SID")
	private int sid ; // 
	
	@OneToOne
	private TeePerson toUser;
	
	@Column(name = "IS_READ" , columnDefinition="int default 0")
	private int isRead = 0;//是否阅读 0 未读 1 已读

	// 阅读
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "read_Time", nullable = true)
	private Date readTime;
	
	/*@ManyToMany(mappedBy="infos")
	private List<TeeNotify> notifys = new ArrayList<TeeNotify>();// 
*/	
	@ManyToOne()
	@Index(name="IDX41562262e9364e14ae03fb64b4d")
	@JoinColumn(name="NOTIFY_ID")
	private TeeNotify notify;//主办人
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeePerson getToUser() {
		return toUser;
	}

	public void setToUser(TeePerson toUser) {
		this.toUser = toUser;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}


	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public TeeNotify getNotify() {
		return notify;
	}

	public void setNotify(TeeNotify notify) {
		this.notify = notify;
	}
	
	
}
