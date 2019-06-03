package com.beidasoft.xzzf.power.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="FX_BASE_DISCRETION")
public class BaseDiscretion {
	 	@Id
	    @Column(name = "ID")
	 	private String id;// 主键

	    @Column(name = "POWER_ID")
	    private String powerId;   // 职权ID

	    @Column(name = "POWER_CODE")
	    private String powerCode; // 自由裁量编号

	    @Column(name = "BREAKLOW")
	    private String breaklow; // 违法行为
	    
	    @Column(name = "LEGALBASIS")
	    private String legalbasis ; // 法律依据

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

		
		public String getPowerCode() {
			return powerCode;
		}

		public void setPowerCode(String powerCode) {
			this.powerCode = powerCode;
		}

		public String getBreaklow() {
			return breaklow;
		}

		public void setBreaklow(String breaklow) {
			this.breaklow = breaklow;
		}

		public String getLegalbasis() {
			return legalbasis;
		}

		public void setLegalbasis(String legalbasis) {
			this.legalbasis = legalbasis;
		} // 法律依据

	}
