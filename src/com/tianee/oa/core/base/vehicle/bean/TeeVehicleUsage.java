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

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 
 * @author syl
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "VEHICLE_USAGE")
public class TeeVehicleUsage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="VEHICLE_USAGE_seq_gen")
	@SequenceGenerator(name="VEHICLE_USAGE_seq_gen", sequenceName="VEHICLE_USAGE_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne()
	@Index(name="IDX7630cc5f5c3c44eab28fc90507c")
	@JoinColumn(name="VEHICLE_ID")
	private TeeVehicle vehicle;//车辆信息表对象

	@ManyToOne()
	@Index(name="IDXd5756b1f6cc2446095fa6b5fde1")
	@JoinColumn(name="VU_PROPOSER")
	private TeePerson vuProposer;//申请人
	
	@ManyToOne()
	@Index(name="IDXff29a26b32fd47ac8881df1d635")
	@JoinColumn(name="VU_DEPT")
	private TeeDepartment vuDept;//用车部门的DEPT_ID
	
//	TeeDepartemnt	用车部门 	
	@ManyToOne()
	@Index(name="IDX545d3a80971c4363b884958ba3e")
	@JoinColumn(name="VU_USER")
	private TeePerson vuUser;//	用车人		

	@Column(name="VU_REQUEST_DATE")
	private Date VuReqestDate;//使用日期
	
	@Column(name="STATUS" ,columnDefinition="INT default 0" ,nullable=false)
	private int status;//STATUS	VARCHAR(20)0-	申请待批1-	申请已批准          2-	使用中      3-	审批未通过           4-	归还
	
	@Lob
	@Column(name="VU_REASON" )
	private String vuReason;// 事由		
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="VU_START")
	private Date vuStart;//开始时间
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="VU_END")
	private Date vuEnd;//结束时间
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_TIME")
    private Date createTime;//创建时间

	@Column(name="VU_MILEAGE")
	private double vuMileage;//申请公里数
	
	
	@Lob
	@Column(name="VU_REMARK" )
	private String vuRemark;// 备注			
	
	@Column(name="VU_DESTINATION" )
	private String vuDestination;//varchar (200)	目的地		
	
	
	@ManyToOne()
	@Index(name="IDXd549488fcc6a4699b1b1e477151")
	@JoinColumn(name="VU_OPERATOR")
	private TeePerson vuOperator;//	TeePerson	调度员		
	
	@Column(name="VU_DRIVER" )
	private String vuDriver;//VU_DRIVER	varchar(20)	司机		
	
	
	@Column(name="SMS_REMIND" ,columnDefinition="char(1) default 0") 
	private String smsRemind;//SMS_REMIND	varchar(20)	内部提醒申请人			0否、1是
	
	@Column(name="SMS2_REMIND" ,columnDefinition="char(1) default 0") 
	private String sms2Remind;//SMS2_REMIND	varchar(20)	手机短信申请人			0否、1是
	
	@ManyToOne()
	@Index(name="IDX9bd0594752d64baba90afb7ecfd")
	@JoinColumn(name="DEPT_MANAGER")
	private TeePerson deptManager;//	DEPT_MANAGER	TeePerson	部门审批人			部门审批人ID
	
	@Column(name="DMER_STATUS" ,columnDefinition="INT default 0" ,nullable=false)
	private int dmerStatus ;//DMER_STATUS	int	部门审批状态			0-部门申请待批 1-部门审批通过  3-未批准
	
	@Column(name="DEPT_REASON" ,length=500)
	private String deptReason;//DEPT_REASON	CLOB	部门审批人意见			申请没通过时审批人的意见
	
	@Column(name="OPERATOR_REASON" ,length=500)
	private String operatorReason;//OPERATOR_REASON	CLOB	调度员意见			申请没通过时调度员的意见

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

	public TeePerson getVuProposer() {
		return vuProposer;
	}

	public void setVuProposer(TeePerson vuProposer) {
		this.vuProposer = vuProposer;
	}

	public TeePerson getVuUser() {
		return vuUser;
	}

	public void setVuUser(TeePerson vuUser) {
		this.vuUser = vuUser;
	}

	public Date getVuReqestDate() {
		return VuReqestDate;
	}

	public void setVuReqestDate(Date vuReqestDate) {
		VuReqestDate = vuReqestDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getVuReason() {
		return vuReason;
	}

	public void setVuReason(String vuReason) {
		this.vuReason = vuReason;
	}


	public double getVuMileage() {
		return vuMileage;
	}

	public void setVuMileage(double vuMileage) {
		this.vuMileage = vuMileage;
	}

	public String getVuRemark() {
		return vuRemark;
	}

	public void setVuRemark(String vuRemark) {
		this.vuRemark = vuRemark;
	}

	public String getVuDestination() {
		return vuDestination;
	}

	public void setVuDestination(String vuDestination) {
		this.vuDestination = vuDestination;
	}

	public TeePerson getVuOperator() {
		return vuOperator;
	}

	public void setVuOperator(TeePerson vuOperator) {
		this.vuOperator = vuOperator;
	}

	public String getVuDriver() {
		return vuDriver;
	}

	public void setVuDriver(String vuDriver) {
		this.vuDriver = vuDriver;
	}

	public String getSmsRemind() {
		return smsRemind;
	}

	public void setSmsRemind(String smsRemind) {
		this.smsRemind = smsRemind;
	}

	public String getSms2Remind() {
		return sms2Remind;
	}

	public void setSms2Remind(String sms2Remind) {
		this.sms2Remind = sms2Remind;
	}

	public TeePerson getDeptManager() {
		return deptManager;
	}

	public void setDeptManager(TeePerson deptManager) {
		this.deptManager = deptManager;
	}

	public int getDmerStatus() {
		return dmerStatus;
	}

	public void setDmerStatus(int dmerStatus) {
		this.dmerStatus = dmerStatus;
	}

	public String getDeptReason() {
		return deptReason;
	}

	public void setDeptReason(String deptReason) {
		this.deptReason = deptReason;
	}

	public String getOperatorReason() {
		return operatorReason;
	}

	public void setOperatorReason(String operatorReason) {
		this.operatorReason = operatorReason;
	}

    public TeeDepartment getVuDept() {
        return vuDept;
    }

    public void setVuDept(TeeDepartment vuDept) {
        this.vuDept = vuDept;
    }

    public Date getVuStart() {
        return vuStart;
    }

    public void setVuStart(Date vuStart) {
        this.vuStart = vuStart;
    }

    public Date getVuEnd() {
        return vuEnd;
    }

    public void setVuEnd(Date vuEnd) {
        this.vuEnd = vuEnd;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	
}

