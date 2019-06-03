package com.tianee.oa.core.base.meeting.bean;
import org.hibernate.annotations.Index;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 
 * @author syl
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "MEETING_ROOM")
public class TeeMeetingRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="MEETING_ROOM_seq_gen")
	@SequenceGenerator(name="MEETING_ROOM_seq_gen", sequenceName="MEETING_ROOM_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@Column(name="MR_NAME")
	private String mrName;//MR_NAME	VARCHAR(200)	会议室名称
	@Column(name="MR_CAPACITY")
	private String mrCapacity;//MR_CAPACITY	VARCHAR(200)	会议容量人数
	
	@Lob
	@Column(name="MR_DEVICE")
	private String mrDevice;//MR_DEVICE	CLOB	设备情况
	@Lob
	@Column(name="MR_DESC")
	private String mrdesc;//MR_DESC	CLOB	会议室描述
	
	@Column(name="MR_PLACE")
	private String mrPlace;//MR_PLACE	VARCHAR(200)	地址
	
	private String managerIds;//	OPERATOR	CLOB	会议管理员
	
	
	
	@Column(name="TYPE")
	private int type;//会议室类型  2=询问室   1=会议室内
	
	
	
	@ManyToMany(targetEntity=TeeDepartment.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="MEET_ROOM_DEPT",        
			joinColumns={@JoinColumn(name="ROOM_ID")},       
			inverseJoinColumns={@JoinColumn(name="DEPT_UUID")}  ) 	
	@Index(name="MEET_ROOM_DEPT_INDEX")
	private List<TeeDepartment> postDept;//申请权限 -部门
	
	
	@ManyToMany(targetEntity=TeePerson.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="MEET_ROOM_PERSON",        
			joinColumns={@JoinColumn(name="ROOM_ID")},       
			inverseJoinColumns={@JoinColumn(name="USER_UUID")}  ) 	
	@Index(name="MEET_ROOM_USER_INDEX")
	private List<TeePerson> postUser;//申请权限--人员
	
	@Column(name="EQUIPMENT_IDS" )
	private String equipmentIds;
	
	@Column(name="EQUIPMENT_NAMES" )
	private String equipmentNames;

	@Column(name="SB_STATUS")
	private int sbStatus;//设备是否异常 0：正常 1：异常
	
	
	
	
	
	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public String getMrName() {
		return mrName;
	}


	public void setMrName(String mrName) {
		this.mrName = mrName;
	}


	public String getMrCapacity() {
		return mrCapacity;
	}


	public void setMrCapacity(String mrCapacity) {
		this.mrCapacity = mrCapacity;
	}


	public String getMrDevice() {
		return mrDevice;
	}


	public void setMrDevice(String mrDevice) {
		this.mrDevice = mrDevice;
	}


	public String getMrdesc() {
		return mrdesc;
	}


	public void setMrdesc(String mrdesc) {
		this.mrdesc = mrdesc;
	}


	public String getMrPlace() {
		return mrPlace;
	}


	public void setMrPlace(String mrPlace) {
		this.mrPlace = mrPlace;
	}


	public String getManagerIds() {
		return managerIds;
	}


	public void setManagerIds(String managerIds) {
		this.managerIds = managerIds;
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


	public String getEquipmentIds() {
		return equipmentIds;
	}


	public void setEquipmentIds(String equipmentIds) {
		this.equipmentIds = equipmentIds;
	}


	public String getEquipmentNames() {
		return equipmentNames;
	}


	public void setEquipmentNames(String equipmentNames) {
		this.equipmentNames = equipmentNames;
	}


	public int getSbStatus() {
		return sbStatus;
	}


	public void setSbStatus(int sbStatus) {
		this.sbStatus = sbStatus;
	}


}

