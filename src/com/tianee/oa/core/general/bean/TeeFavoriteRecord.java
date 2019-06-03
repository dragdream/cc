package com.tianee.oa.core.general.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 收藏夹
 * @author kakalion
 *
 */
@Entity
@Table(name="FAVORITE_RECORD")
public class TeeFavoriteRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="FAVORITE_RECORD_seq_gen")
	@SequenceGenerator(name="FAVORITE_RECORD_seq_gen", sequenceName="FAVORITE_RECORD_seq")
	private int sid;
	
	@Column(name="TITLE")
	private String title;//名称
	
	@Column(name="URL")
	@Index(name="FRECORD_URL_IDX")
	private String url;//路径
	
	@ManyToOne
	@Index(name="IDX5cf252201cbd4f85afbc25ef02e")
	@JoinColumn(name="USER_ID")
	private TeePerson user;//操作人
	
	@Column(name="OPT_TIME")  
	private long optTime;//操作时间

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public TeePerson getUser() {
		return user;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}

	public long getOptTime() {
		return optTime;
	}

	public void setOptTime(long optTime) {
		this.optTime = optTime;
	}

	
	
}
