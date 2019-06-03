package com.tianee.oa.core.base.meeting.bean;
import org.hibernate.annotations.Index;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 
 * @author syl
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "MEETING_EQUUIPMENT")
public class TeeMeetingEquipment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="MEETING_EQUUIPMENT_seq_gen")
	@SequenceGenerator(name="MEETING_EQUUIPMENT_seq_gen", sequenceName="MEETING_EQUUIPMENT_seq")
	@Column(name="SID")
	private int sid;//自增id
	
//	@Column(name="EQUIPMENT_NO" , length = 200)
//	private String equipmentNo ;//MEET_NAME	VARCHAR(200)	设备编号
	
	@Column(name="EQUIPMENT_NAME" , length = 200)
	private String equipmentName;//SUBJECT	VARCHAR(200)	设备名称
//	@Column(name="GROUP_NO" , length = 200)
//	private String groupNo;//GROUP_NO	CLOB	同类设备名称

	@Lob
	@Column(name="REMARK" )
	private String remark;//REMARK	CLOB	设备描述

//	@Column(name="STATUS" ,columnDefinition="INT default 0" ,nullable=false)
//	private int statue;//STATUS	VARCHAR(20)	设备状态  0-正常 1-不可用

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

//	public String getEquipmentNo() {
//		return equipmentNo;
//	}
//
//	public void setEquipmentNo(String equipmentNo) {
//		this.equipmentNo = equipmentNo;
//	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

//	public String getGroupNo() {
//		return groupNo;
//	}
//
//	public void setGroupNo(String groupNo) {
//		this.groupNo = groupNo;
//	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

//	public int getStatue() {
//		return statue;
//	}
//
//	public void setStatue(int statue) {
//		this.statue = statue;
//	}

	

}


	

	
