package com.tianee.mem.model;


public class Memmodel {
	private int id;
	private String evidence_name;
	private int src_type;
	private String get_timestr;
	private String get_person;
	private String collecting_unit;
	private String collection_address;
	private String seize_timestr;
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
	public String getGet_timestr() {
		return get_timestr;
	}
	public void setGet_timestr(String get_timestr) {
		this.get_timestr = get_timestr;
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
	public String getSeize_timestr() {
		return seize_timestr;
	}
	public void setSeize_timestr(String seize_timestr) {
		this.seize_timestr = seize_timestr;
	}
	@Override
	public String toString() {
		return "Memmodel [id=" + id + ", evidence_name=" + evidence_name
				+ ", src_type=" + src_type + ", get_timestr=" + get_timestr
				+ ", get_person=" + get_person + ", collecting_unit="
				+ collecting_unit + ", collection_address="
				+ collection_address + ", seize_timestr=" + seize_timestr + "]";
	}
	
	
}
