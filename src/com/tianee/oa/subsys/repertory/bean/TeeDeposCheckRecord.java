package com.tianee.oa.subsys.repertory.bean;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 库存盘点记录
 * @author kakalion
 *
 */
@Entity
@Table(name="DEPOS_CHECK_RECORD")
public class TeeDeposCheckRecord {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="DEPOS_CHECK_RECORD_seq_gen")
	@SequenceGenerator(name="DEPOS_CHECK_RECORD_seq_gen", sequenceName="DEPOS_CHECK_RECORD_seq")
	private int sid;
	
	@Column(name="TITLE_")
	private String title;//盘库记录标题
	
	@Column(name="CHECK_TIME")
	private Calendar checkTime;//盘点日期
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CHECK_USER_ID")
	@Index(name="DEPOS_CHECK_RECORD_CUID")
	private TeePerson checkUser;//盘点人
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DEPOS_ID")
	@Index(name="DEPOS_CHECK_RECORD_DEPOS_ID")
	private TeeRepDepository depository;//仓库ID，如果为空，则是盘点所有仓库
	
	@Column(name="FLAG_")
	private int flag;//标记  1：有效（可编辑）    0：锁定
	
	/**
	 * 盘点项
	 */
	@OneToMany(mappedBy="checkRecord",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
	private List<TeeDeposCheckItem> checkItems = new ArrayList(0);

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

	public Calendar getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Calendar checkTime) {
		this.checkTime = checkTime;
	}

	public TeePerson getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(TeePerson checkUser) {
		this.checkUser = checkUser;
	}

	public TeeRepDepository getDepository() {
		return depository;
	}

	public void setDepository(TeeRepDepository depository) {
		this.depository = depository;
	}

	public List<TeeDeposCheckItem> getCheckItems() {
		return checkItems;
	}

	public void setCheckItems(List<TeeDeposCheckItem> checkItems) {
		this.checkItems = checkItems;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
}
