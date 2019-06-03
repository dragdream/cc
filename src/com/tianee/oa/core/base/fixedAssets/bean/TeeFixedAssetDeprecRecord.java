package com.tianee.oa.core.base.fixedAssets.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

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

@Entity
@Table(name="ASSET_DEPREC_RECORD")
public class TeeFixedAssetDeprecRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="ASSET_DEPREC_RECORD_seq_gen")
	@SequenceGenerator(name="ASSET_DEPREC_RECORD_seq_gen", sequenceName="ASSET_DEPREC_RECORD_seq")
	private int sid;
	
	@Column(name="START_TIME")
	private Calendar startTime;//折旧开始时间
	
	@Column(name="ORIGINAL")
	private double original;//资产原值
	
	@Column(name="DEPREC_TIME")
	private Calendar deprecTime;//折旧年月
	
	@Column(name="DEPREC_VALUE")
	private double deprecValue;//本月折旧值
	
	@Column(name="DEPREC_RE_VAL")
	private double deprecRemainValue;//本月残值
	
	@Column(name="CR_TIME")
	private Calendar crTime;//操作日期
	
	@Column(name="FLAG")
	private int flag;//操作类型  1:自动折旧   2：手动折旧
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX4879fd815c914ec1b57027a0709")
	@JoinColumn(name="USER_ID")
	private TeePerson opUser;
	
	@ManyToOne
	@Index(name="IDXa5b822f0050a41db9b4c23bd07c")
	@JoinColumn(name="ASSET_ID")
	private TeeFixedAssetsInfo asset;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public double getOriginal() {
		return original;
	}

	public void setOriginal(double original) {
		this.original = original;
	}

	public Calendar getDeprecTime() {
		return deprecTime;
	}

	public void setDeprecTime(Calendar deprecTime) {
		this.deprecTime = deprecTime;
	}

	public double getDeprecValue() {
		return deprecValue;
	}

	public void setDeprecValue(double deprecValue) {
		this.deprecValue = deprecValue;
	}

	public double getDeprecRemainValue() {
		return deprecRemainValue;
	}

	public void setDeprecRemainValue(double deprecRemainValue) {
		this.deprecRemainValue = deprecRemainValue;
	}

	public Calendar getCrTime() {
		return crTime;
	}

	public void setCrTime(Calendar crTime) {
		this.crTime = crTime;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public TeeFixedAssetsInfo getAsset() {
		return asset;
	}

	public void setAsset(TeeFixedAssetsInfo asset) {
		this.asset = asset;
	}

	public TeePerson getOpUser() {
		return opUser;
	}

	public void setOpUser(TeePerson opUser) {
		this.opUser = opUser;
	}
	
	
}
