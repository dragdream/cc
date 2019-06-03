package com.beidasoft.xzzf.evi.model;

import java.util.List;


import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class ElvModel {

	private String id;
	
	private String baseId;
	
	private int create_person_id;
	
	private String create_timeStr;
	
	private int file_type;
	
	private String src_type;
	
	private String name;
	
	private String commeents;
	
	private String get_time_str;
	
	private String get_person;
	
	private String get_way_type;
	
	private String get_address;
	
	private String file_name;
	
	private String file_real_name;
	
	private int is_valid;
	
	private int is_delete;
	
	private int md_keys;
	
	List<TeeAttachmentModel> attachments;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public int getCreate_person_id() {
		return create_person_id;
	}

	public void setCreate_person_id(int create_person_id) {
		this.create_person_id = create_person_id;
	}

	public String getCreate_timeStr() {
		return create_timeStr;
	}

	public void setCreate_timeStr(String create_timeStr) {
		this.create_timeStr = create_timeStr;
	}

	public int getFile_type() {
		return file_type;
	}

	public void setFile_type(int file_type) {
		this.file_type = file_type;
	}

	public String getSrc_type() {
		return src_type;
	}

	public void setSrc_type(String src_type) {
		this.src_type = src_type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommeents() {
		return commeents;
	}

	public void setCommeents(String commeents) {
		this.commeents = commeents;
	}

	public String getGet_time_str() {
		return get_time_str;
	}

	public void setGet_time_str(String get_time_str) {
		this.get_time_str = get_time_str;
	}

	public String getGet_person() {
		return get_person;
	}

	public void setGet_person(String get_person) {
		this.get_person = get_person;
	}

	public String getGet_way_type() {
		return get_way_type;
	}

	public void setGet_way_type(String get_way_type) {
		this.get_way_type = get_way_type;
	}

	public String getGet_address() {
		return get_address;
	}

	public void setGet_address(String get_address) {
		this.get_address = get_address;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_real_name() {
		return file_real_name;
	}

	public void setFile_real_name(String file_real_name) {
		this.file_real_name = file_real_name;
	}

	public int getIs_valid() {
		return is_valid;
	}

	public void setIs_valid(int is_valid) {
		this.is_valid = is_valid;
	}

	public int getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}

	public int getMd_keys() {
		return md_keys;
	}

	public void setMd_keys(int md_keys) {
		this.md_keys = md_keys;
	}

	public List<TeeAttachmentModel> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<TeeAttachmentModel> attachments) {
		this.attachments = attachments;
	}

}
