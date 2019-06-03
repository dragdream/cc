package com.tianee.oa.core.general.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

@Entity
@Table(name="MOBILE_SEAL")
public class TeeMobileSeal {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@Column(name="USER_ID")
	@Index(name="MOBILE_SEAL_USER_ID")
	private int userId;//使用人
	
	@Column(name="DEVICE_NO")
	private String deviceNo = "";//设备号
	
	@Column(name="SEAL_NAME")
	private String sealName;//印章名称
	
	@Column(name="PWD")
	private String pwd = "";//印章密码
	
	@Lob
	@Column(name="SEAL_DATA")
	private String sealData;//签章数据 base64格式
	
	@Column(name="CR_TIME")
	private Calendar crTime;//创建时间
	
	@Column(name="CR_USER_ID")
	private int crUserId;//创建人ID
	
	@Column(name="FLAG_")
	private int flag;//0：申请中  1：已绑定

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getSealData() {
		return sealData;
	}

	public void setSealData(String sealData) {
		this.sealData = sealData;
	}

	public Calendar getCrTime() {
		return crTime;
	}

	public void setCrTime(Calendar crTime) {
		this.crTime = crTime;
	}

	public int getCrUserId() {
		return crUserId;
	}

	public void setCrUserId(int crUserId) {
		this.crUserId = crUserId;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getSealName() {
		return sealName;
	}

	public void setSealName(String sealName) {
		this.sealName = sealName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
