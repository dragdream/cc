package com.beidasoft.xzzf.evi.bean;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_ELECTRONIC_EVIDENCE_BASE")
public class EleEvidenceBase {
	@Id
	@Column(name = "ID")
	private String id; // 数据唯一标识
	
	@Column(name = "BASE_ID")
	private String baseId; // 案件唯一标识
	
	@Column(name = "CREATE_PERSON_ID")
	private int create_person_id;// 数据创建人员ID
	
	@Column(name = "CREATE_TIME")
	private Calendar create_time;// 数据创建日期
	
	@Column(name = "FILE_TYPE")
	private int file_type;// 电子证据类型-10-图片，20-视频，30-音频，40-其他
	
	@Column(name = "SRC_TYPE")
	private String src_type;// 证据来源10-投诉举报，20-现场检查，30-其他
	
	@Column(name = "NAME")
	private String name;// 证据名称
	
	@Column(name = "COMMEENTS")
	private String commeents;// 备注，证据额外描述
	
	@Column(name = "GET_TIME")
	private Date get_time;// 取证时间
	
	@Column(name = "GET_PERSON")
	private String get_person;// 取证人姓名
	
	@Column(name = "GET_WAY_TYPE")
	private String get_way_type;// 取证方式
	
	@Column(name = "GET_ADDRESS")
	private String get_address;// 取证地点
	
	@Column(name = "FILE_NAME")
	private String file_name;// 文件名称（原名称）
	
	@Column(name = "FILE_REAL_NAME")
	private String file_real_name;// 文件名称（真实保存名称）
	
	@Column(name = "IS_VALID")
	private int is_valid;// 是否有效
	
	@Column(name = "IS_DELETE")
	private int isDelete;// 删除标识
	
	@Column(name = "MD_KEYS")
	private int md_keys;// 证据固化标识码

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

	public Calendar getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Calendar create_time) {
		this.create_time = create_time;
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

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public int getMd_keys() {
		return md_keys;
	}

	public void setMd_keys(int md_keys) {
		this.md_keys = md_keys;
	}

}
