package com.beidasoft.xzzf.evi.model;


public class ResModel {
	private String id; // 数据唯一标识
	
	private String baseId; // 案件唯一标识
	
	private int create_person_id;// 数据创建人员ID
	
	private String create_timeStr;// 数据创建日期
	
	private int file_type;// 电子证据类型-10-图片，20-视频，30-音频，40-其他
	
	private String src_type;// 证据来源10-投诉举报，20-现场检查，30-其他
	
	private String name;// 证据名称
	
	private String commeents;// 备注，证据额外描述
	
	private String get_time_str;// 取证时间
	
	private String get_person;// 取证人姓名
	
	private String get_way_type;// 取证方式
	
	private String get_address;// 取证地点
	
	private String file_name;// 文件名称（原名称）
	
	private String file_real_name;// 文件名称（真实保存名称）
	
	private int is_valid;// 是否有效
	
	private int is_delete;// 删除标识
	
	private String res_location;// 物品位置

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

	public String getRes_location() {
		return res_location;
	}

	public void setRes_location(String res_location) {
		this.res_location = res_location;
	}

	
	
}
