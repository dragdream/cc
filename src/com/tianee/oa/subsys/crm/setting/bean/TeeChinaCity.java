package com.tianee.oa.subsys.crm.setting.bean;
import org.hibernate.annotations.Index;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 中国省、城市、县
 * @author wyw
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CHINA_CITY")
public class TeeChinaCity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CHINA_CITY_seq_gen")
	@SequenceGenerator(name="CHINA_CITY_seq_gen", sequenceName="CHINA_CITY_seq")
	@Column(name = "SID")
	private int sid;// 自增id

	// 城市名称
	@Column(name = "CITY_NAME", nullable = true, length = 254)
	private String cityName;

	// 城市编号
	@Column(name = "CITY_CODE", nullable = true, length = 254)
	private String cityCode;


	// 创建时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
