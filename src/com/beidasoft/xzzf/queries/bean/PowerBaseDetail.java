package com.beidasoft.xzzf.queries.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 职权层级表实体类
 */
@Entity
@Table(name="FX_BASE_POWER_DETAIL")
public class PowerBaseDetail {
	    @Id
	    @Column(name = "ID")
	    private String id; // 主键

	    @Column(name = "POWER_ID")
	    private String powerId; // 职权ID

	    @Column(name = "NAME")
	    private String name; // 职权分类名称

	    @Column(name = "CODE")
	    private String code; // 职权种类代码名称

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getPowerId() {
			return powerId;
		}

		public void setPowerId(String powerId) {
			this.powerId = powerId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
}
