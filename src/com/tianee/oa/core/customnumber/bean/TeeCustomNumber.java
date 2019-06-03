package com.tianee.oa.core.customnumber.bean;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="custom_number")
public class TeeCustomNumber {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CUSTOM_NUMBER_seq_gen")
	@SequenceGenerator(name="CUSTOM_NUMBER_seq_gen", sequenceName="CUSTOM_NUMBER_seq")
    private int uuid;//主键
		
	@Column(name="TYPES")
	private int additonalType =1;//累加方式  1自动累加  2按年累加  3按月累加  4按日累加
	
	@Column(name="NUMBER_BIT")
	private int numberBit =3;//编号位数
	
	@Column(name="CURRENT_NUMBER")
	private int currentNumber =0;//当前编号
	
	@Column(name="LAST_DATE")
	private Calendar lastDate;//最后一次修改 
	
	@Column(name="SET_STYLE")
	private String userSetStyle= "YYYYMMDD##";//编号样式  YYYYMMDD##
	
	
	@Column(name="CODE_NAME")
	private String codeName;//编号名称
	
	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public int getAdditonalType() {
		return additonalType;
	}

	public void setAdditonalType(int additonalType) {
		this.additonalType = additonalType;
	}

	public int getNumberBit() {
		return numberBit;
	}

	public void setNumberBit(int numberBit) {
		this.numberBit = numberBit;
	}

	public int getCurrentNumber() {
		return currentNumber;
	}

	public void setCurrentNumber(int currentNumber) {
		this.currentNumber = currentNumber;
	}

	public Calendar getLastDate() {
		return lastDate;
	}

	public void setLastDate(Calendar lastDate) {
		this.lastDate = lastDate;
	}

	public String getUserSetStyle() {
		return userSetStyle;
	}

	public void setUserSetStyle(String userSetStyle) {
		this.userSetStyle = userSetStyle;
	}
	
	
	/*public TeePerson getPerson() {
		return person;
	}

	public void setPerson(TeePerson person) {
		this.person = person;
	}*/
	
	

}
