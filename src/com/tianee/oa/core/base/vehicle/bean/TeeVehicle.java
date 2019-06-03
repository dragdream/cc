package com.tianee.oa.core.base.vehicle.bean;
import org.hibernate.annotations.Index;

import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 
 * @author syl
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "VEHICLE")
public class TeeVehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="VEHICLE_seq_gen")
	@SequenceGenerator(name="VEHICLE_seq_gen", sequenceName="VEHICLE_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	/*@ManyToOne()
	@Index(name="IDXd48271c8cddc406f964498546f2")
	@JoinColumn(name="USER_ID")
	private TeePerson user;//
*/	
	@Column(name="BUY_DATE")
	private Date buyDate;//购买日期
	
	@Column(name="STATUS" ,columnDefinition="INT default 0" ,nullable=false)
	private int status;//STATUS	VARCHAR(20)	0:可用（只有可用状态才可以申请使用）1：损坏2：维修中3：报废
	
	@Column(name="V_MODEL" )
	private String vModel;// V_MODEL	VARCHAR(200)	厂牌型号			
	
	@Column(name="V_NUM" )
	private String vNum;//V_NUM	VARCHAR(200)	车牌号		
	
	@Column(name="V_DRIVER" )
	private String vDriver;//V_DRIVER	varchar(500)	司机	
	@Column(name="V_TYPE" )
	private String vType;//V_TYPE	varchar(20)	车辆类型			系统代码的标识ID“CEHICLE_TYPE”

	@Lob
	@Column(name="V_REMARK" )
	private String vRemark;//V_REMARK	CLOB	备注			

	@Column(name="V_ENGINE_NUM" )
	private String vEngineNum;//V_ENG	发动机号			
	
	@Column(name="V_PRICE" )
	private double vPrice;//V_PRICE	varchar(20)	车辆价格	
	
	@OneToOne
	private TeeAttachment attache ;//ATTACHMENT_ID	CLOB	车辆图片ID			TeeAttement
	
	@ManyToMany(targetEntity=TeeDepartment.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="VEHICLE_DEPT_PRIV",        
			joinColumns={@JoinColumn(name="VEHICLE_ID")},       
			inverseJoinColumns={@JoinColumn(name="DEPT_UUID")}  ) 	
	@Index(name="VEHICLE_DEPT_PRIV_INDEX")
	private List<TeeDepartment> postDept;//申请权限 -部门
	
	
	@ManyToMany(targetEntity=TeePerson.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="VEHICLE_PERSON_PRIV",        
			joinColumns={@JoinColumn(name="VEHICLE_ID")},       
			inverseJoinColumns={@JoinColumn(name="USER_UUID")}  ) 	
	@Index(name="VEHICLE_PERSON_PRIV_INDEX")
	private List<TeePerson> postUser;//申请权限--人员
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getvModel() {
		return vModel;
	}

	public void setvModel(String vModel) {
		this.vModel = vModel;
	}

	public String getvNum() {
		return vNum;
	}

	public void setvNum(String vNum) {
		this.vNum = vNum;
	}

	public String getvDriver() {
		return vDriver;
	}

	public void setvDriver(String vDriver) {
		this.vDriver = vDriver;
	}

	public String getvType() {
		return vType;
	}

	public void setvType(String vType) {
		this.vType = vType;
	}

	public String getvRemark() {
		return vRemark;
	}

	public void setvRemark(String vRemark) {
		this.vRemark = vRemark;
	}

	public String getvEngineNum() {
		return vEngineNum;
	}

	public void setvEngineNum(String vEngineNum) {
		this.vEngineNum = vEngineNum;
	}

	public double getvPrice() {
		return vPrice;
	}

	public void setvPrice(double vPrice) {
		this.vPrice = vPrice;
	}

	public TeeAttachment getAttache() {
		return attache;
	}

	public void setAttache(TeeAttachment attache) {
		this.attache = attache;
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

	
	
}
