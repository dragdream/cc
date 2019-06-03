package com.tianee.mem.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name="MATERIAL_EVIDENCE_MANAGEMENT")
public class Membean {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="MATERIAL_E_M_seq_gen")
	@SequenceGenerator(name="MATERIAL_E_M_seq_gen", sequenceName="MATERIAL_E_M_SEQ")
	@Column(name="ID")
	private int id;	
	@Column(name="EVIDENCE_NAME")
	private String evidence_name;
	@Column(name="SRC_TYPE")
	private int src_type;
	@Column(name="GET_TIME")
	private Date get_time;
	@Column(name="GET_PERSON")
	private String get_person;
	@Column(name="COLLECTING_UNIT")
	private String collecting_unit;
	@Column(name="COLLECTION_ADDRESS")
	private String collection_address;
	@Column(name="SEIZE_TIME")
	private Date seize_time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEvidence_name() {
		return evidence_name;
	}
	public void setEvidence_name(String evidence_name) {
		this.evidence_name = evidence_name;
	}
	public int getSrc_type() {
		return src_type;
	}
	public void setSrc_type(int src_type) {
		this.src_type = src_type;
	}
	public Date getGet_time() {
		return get_time;
	}
	public void setGet_time(Date get_time) {
		this.get_time = get_time;
	}
	public String getGet_person() {
		return get_person;
	}
	public void setGet_person(String get_person) {
		this.get_person = get_person;
	}
	public String getCollecting_unit() {
		return collecting_unit;
	}
	public void setCollecting_unit(String collecting_unit) {
		this.collecting_unit = collecting_unit;
	}
	public String getCollection_address() {
		return collection_address;
	}
	public void setCollection_address(String collection_address) {
		this.collection_address = collection_address;
	}
	public Date getSeize_time() {
		return seize_time;
	}
	public void setSeize_time(Date seize_time) {
		this.seize_time = seize_time;
	}
	
	@Override
	public String toString() {
		return "Membean [id=" + id + ",  evidence_name="
				+ evidence_name + ", src_type=" + src_type + ", get_time="
				+ get_time + ", get_person=" + get_person
				+ ", collecting_unit=" + collecting_unit
				+ ", collection_address=" + collection_address
				+ ", seize_time=" + seize_time + "]";
	}
}
