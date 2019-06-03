package com.tianee.oa.core.base.vehicle.bean;
import org.hibernate.annotations.Index;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 
 * @author syl
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "VEHICLE_MAINTENANCE")
public class TeeVehicleMaintenance {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="VEHICLE_MAINTENANCE_seq_gen")
	@SequenceGenerator(name="VEHICLE_MAINTENANCE_seq_gen", sequenceName="VEHICLE_MAINTENANCE_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne()
	@Index(name="IDX106c910c1627463b902bcc7e920")
	@JoinColumn(name="VEHICLE_ID")
	private TeeVehicle vehicle;//车辆对象

	@ManyToOne()
	@Index(name="IDXe11ae07df39d4c52ad03e90adda")
	@JoinColumn(name="VM_PERSON")
	private TeePerson vmPerson;//经办人
	
	
	@Column(name="VM_REQUEST_DATE")
	private Date vmRequestDate;//维护日期
	
	@Column(name="VM_TYPE" ,columnDefinition="INT default 0" ,nullable=false)
	private int vmType;//维护类型，本状态与车辆使用申请无关	0:维  1：加油 2:洗车  3：年检 4：其他
	
	@Lob
	@Column(name="VM_REASON" )
	private String vmReason;//CLOB	维护原因	
	
	@Lob
	@Column(name="VM_REMARK" )
	private String vmRemark;//CLOB	备注	
	
	@Column(name="VM_FEE" )
	private Double vmFee;//维护费用	
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_TIME")
    private Date createTime;//创建时间

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeVehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(TeeVehicle vehicle) {
		this.vehicle = vehicle;
	}

	public TeePerson getVmPerson() {
		return vmPerson;
	}

	public void setVmPerson(TeePerson vmPerson) {
		this.vmPerson = vmPerson;
	}

	public Date getVmRequestDate() {
		return vmRequestDate;
	}

	public void setVmRequestDate(Date vmRequestDate) {
		this.vmRequestDate = vmRequestDate;
	}

	public int getVmType() {
		return vmType;
	}

	public void setVmType(int vmType) {
		this.vmType = vmType;
	}

	public String getVmReason() {
		return vmReason;
	}

	public void setVmReason(String vmReason) {
		this.vmReason = vmReason;
	}

	public String getVmRemark() {
		return vmRemark;
	}

	public void setVmRemark(String vmRemark) {
		this.vmRemark = vmRemark;
	}

	public Double getVmFee() {
		return vmFee;
	}

	public void setVmFee(Double vmFee) {
		this.vmFee = vmFee;
	}

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
				



}

