package com.beidasoft.xzzf.discretionaryPower.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FX_DISCRETIONARY_POWER")
public class discretionaryPower {

	@Id
	@Column(name = "ID") 
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "FX_DISCRETIONARY_POWER_seq_gen")
	@SequenceGenerator(name = "FX_DISCRETIONARY_POWER_seq_gen", sequenceName = "FX_DISCRETIONARY_POWER_seq")
	private int id; //

	@Column(name = "POWER")
	private String power; //职权编号

	@Column(name = "BREAKLOW")
	private String breaklow; //违法行为
	
	@Column(name = "LEGALBASIS")
	private String legalbasis; //法律依据
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
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
	}

}
