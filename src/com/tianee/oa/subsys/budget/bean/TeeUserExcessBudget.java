package com.tianee.oa.subsys.budget.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;




import com.tianee.oa.core.org.bean.TeePerson;
/**
 * 个人超额表
 * @author 
 *
 */
@Entity
@Table(name="BUDGET_EXCESS_USER")
public class TeeUserExcessBudget implements Serializable {

		/**
	 * 
	 */
	private static final long serialVersionUID = -3872224324404374473L;


	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "UUID")//主键id
    private String uuid;
		@ManyToOne
		@JoinColumn(name="USER_ID")
		private TeePerson user;
		
		@Column(name="YEAR")
		private String year;//年度   例如  2014
		
		@Column(name="MONTH")
		private String month;//月份  例如 01 02 03 11 12 等
		
		@Column(name="EXCESS_AMOUNT")
		private double excessAmount;
		
		@ManyToOne
		@JoinColumn(name="CR_USER_ID")
		private TeePerson crUser;

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

		public TeePerson getUser() {
			return user;
		}

		public void setUser(TeePerson user) {
			this.user = user;
		}

		public String getYear() {
			return year;
		}

		public void setYear(String year) {
			this.year = year;
		}

		public String getMonth() {
			return month;
		}

		public void setMonth(String month) {
			this.month = month;
		}

	
		public double getExcessAmount() {
			return excessAmount;
		}

		public void setExcessAmount(double excessAmount) {
			this.excessAmount = excessAmount;
		}

		public TeePerson getCrUser() {
			return crUser;
		}

		public void setCrUser(TeePerson crUser) {
			this.crUser = crUser;
		}

		
}
