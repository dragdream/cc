package com.beidasoft.xzzf.lawCheck.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="ZF_INSPECTION_MODULE_ITEM")
public class LawCheckItem {
	@Id
    @Column(name = "ID")
    private String id; 
    
    @Column(name = "MODULE_ID")
    private String moduleId; // 模块ID

    @Column(name = "ITEM_NAME")
    private String itemName; // 事项名称
    
    @Column(name = "IS_DELETE")
    private String isDelete;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

}
