package com.beidasoft.xzzf.lawCheck.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="ZF_INSPECTION_MODULE")
public class BaseLawCheck {
	    @Id
	    @Column(name = "ID")
	    private String id; 
	    
	    @Column(name = "MODULE_NAME")
	    private String moduleName; // 模块名称

	    @Column(name = "MODULE_TYPE")
	    private String moduleType; // 模块类型
	    
	    @Column(name = "IS_DELETE")
	    private String isDelete; // 是否删除

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getModuleName() {
			return moduleName;
		}

		public void setModuleName(String moduleName) {
			this.moduleName = moduleName;
		}

		public String getModuleType() {
			return moduleType;
		}

		public void setModuleType(String moduleType) {
			this.moduleType = moduleType;
		}

		public String getIsDelete() {
			return isDelete;
		}

		public void setIsDelete(String isDelete) {
			this.isDelete = isDelete;
		}

		}

	
