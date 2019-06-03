package com.tianee.oa.core.base.pm.bean;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.subsys.salManage.bean.TeeHrInsurancePara;

/**
 * 基本档案
 * @author kakalion
 *
 */
@Entity
@Table(name="PERSONNEL_MANAGEMENT")
public class TeeHumanDoc {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="PERSONNEL_MANAGEMENT_seq_gen")
	@SequenceGenerator(name="PERSONNEL_MANAGEMENT_seq_gen", sequenceName="PERSONNEL_MANAGEMENT_seq")
	private int sid;//主键
	
	@Column(name="PERSON_NAME")
	private String personName;//人员姓名
	
	/**
	 * 男
	 * 女
	 */
	@Column(name="GENDER")
	private String gender;//性别
	
	@Column(name="NATIVE_PLACE")
	private String nativePlace;//籍贯
	
	@Column(name="ethnicity")
	private String ethnicity;//民族
	
	private String isOaUser;//是否为OA用户
	

	public String getIsOaUser() {
		return isOaUser;
	}

	public void setIsOaUser(String isOaUser) {
		this.isOaUser = isOaUser;
	}

	/**
	 * 对应oa用户可为空
	 */
	@OneToOne()
	@JoinColumn(name="OA_USER")
	private TeePerson oaUser;//对应OA用户
	
	/**
	 * 所在部门可为空
	 */
	@OneToOne()
	@JoinColumn(name="DEPT")
	private TeeDepartment dept;//所在部门
	
	/**
	 * 所属角色，可为空
	 */
	@OneToOne()
	@JoinColumn(name="ROLE")
	private TeeUserRole role;//所在角色
	
	/**
	 * 离职
	 * 在职
	 * 退休
	 */
	@Column(name="STATUS_TYPE")
	private String statusType;//在职状态
	
	/**
	 * 合同工
	 * 正式员工
	 * 兼职工
	 * 临时工
	 */
	@Column(name="EMPLOYEE_TYPE")
	private String employeeType;//员工类型
	
	@Column(name="CODE_NUMBER")
	private String codeNumber;//编号
	
	@Column(name="WORK_NUMBER")
	private String workNumber;//工号
	
	@Column(name="IDCARD")
	private String idCard;//身份证号
	
	@Column(name="ENGLISH_NAME")
	private String englishName;//英文名
	
	@Column(name="birthday")
	private Calendar birthday;//出生日期
	
	/**
	 * 未婚
	 * 已婚
	 * 离婚
	 */
	@Column(name="marriage")
	private String marriage;//婚姻类型
	
	/**
	 * 本市城镇
	 * 外市农村
	 * 外地城镇
	 * 外地农村
	 * 本市城镇职工
	 * 外埠城镇职工
	 * 本市农民工
	 * 外地农民工
	 * 本市农村劳动力
	 * 外埠农村劳动力
	 */
	@Column(name="household")
	private String household;//户口类型
	
	@Column(name="health")
	private String health;//健康状况
	
	@Column(name="householdPlace")
	private String householdPlace;//户口所在地
	
	/**
	 * 群众
	 * 共青团员
	 * 中共党员
	 * 中共预备党员
	 * 无党派人士
	 */
	@Column(name="politics")
	private String politics;//政治面貌
	
	@Column(name="joinPartyDate")
	private Calendar joinPartyDate;//入党时间
	
	@Column(name="major")
	private String major;//主修
	
	@Column(name="graduateDate")
	private Calendar graduateDate;//毕业时间
	
	/**
	 * 研究生
	 * 本科
	 * 大专
	 * 技校
	 * 高中
	 * 初中
	 * 小学
	 */
	@Column(name="educationDegree")
	private String educationDegree;//学历
	
	/**
	 * 博士后
	 * 博士
	 * MBA
	 * 硕士
	 * 双学位
	 * 学士
	 */
	@Column(name="degree")
	private String degree;//学位
	
	@Column(name="graduateSchool")
	private String graduateSchool;//毕业院校
	
	private String mobileNo;//手机号
	
	private String telNo;//电话
	
	private String email;//电子邮件
	
	private String qqNo;//qq号
	
	private String msn;//msn
	
	private String address;//住址
	
	private String otherAddress;//其他联系地址
	
	private String postState;//职务
	
	private Calendar joinDate;//入职时间
	
	private int workYears;//工龄
	
	private int totalYears;//总工龄
	
	private String computerLevel;//计算机水平
	
	private String language1;//语种1
	
	private String language2;//语种2
	
	private String language3;//语种3
	
	private String skill;//特长
	@Lob()
	private String postDesc;//职务情况
	@Lob()
	private String shebaoDesc;//社保情况
	@Lob()
	private String healthDesc;//体检情况
	@Lob()
	private String remark;//备注

	private double defaultAnnualLeaveDays;//默认年假天数
	
	@ManyToOne()
	@JoinColumn(name="INSUANCE_PARA_ID")
	private TeeHrInsurancePara hrInsurancePara;//保险设置
	
	@Column(name="SAL_LEVEL")
	private int salaryLevel;//职级工资等级
	
	public TeeUserRole getRole() {
		return role;
	}

	public void setRole(TeeUserRole role) {
		this.role = role;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public TeePerson getOaUser() {
		return oaUser;
	}

	public void setOaUser(TeePerson oaUser) {
		this.oaUser = oaUser;
	}

	public TeeDepartment getDept() {
		return dept;
	}

	public void setDept(TeeDepartment dept) {
		this.dept = dept;
	}


	public String getCodeNumber() {
		return codeNumber;
	}

	public void setCodeNumber(String codeNumber) {
		this.codeNumber = codeNumber;
	}

	public String getWorkNumber() {
		return workNumber;
	}

	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public Calendar getBirthday() {
		return birthday;
	}

	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}


	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getHouseholdPlace() {
		return householdPlace;
	}

	public void setHouseholdPlace(String householdPlace) {
		this.householdPlace = householdPlace;
	}

	public Calendar getJoinPartyDate() {
		return joinPartyDate;
	}

	public void setJoinPartyDate(Calendar joinPartyDate) {
		this.joinPartyDate = joinPartyDate;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public Calendar getGraduateDate() {
		return graduateDate;
	}

	public void setGraduateDate(Calendar graduateDate) {
		this.graduateDate = graduateDate;
	}

	public String getGraduateSchool() {
		return graduateSchool;
	}

	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQqNo() {
		return qqNo;
	}

	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOtherAddress() {
		return otherAddress;
	}

	public void setOtherAddress(String otherAddress) {
		this.otherAddress = otherAddress;
	}

	public String getPostState() {
		return postState;
	}

	public void setPostState(String postState) {
		this.postState = postState;
	}

	public Calendar getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Calendar joinDate) {
		this.joinDate = joinDate;
	}

	public int getWorkYears() {
		return workYears;
	}

	public void setWorkYears(int workYears) {
		this.workYears = workYears;
	}

	public int getTotalYears() {
		return totalYears;
	}

	public void setTotalYears(int totalYears) {
		this.totalYears = totalYears;
	}

	public String getComputerLevel() {
		return computerLevel;
	}

	public void setComputerLevel(String computerLevel) {
		this.computerLevel = computerLevel;
	}

	public String getLanguage1() {
		return language1;
	}

	public void setLanguage1(String language1) {
		this.language1 = language1;
	}

	public String getLanguage2() {
		return language2;
	}

	public void setLanguage2(String language2) {
		this.language2 = language2;
	}

	public String getLanguage3() {
		return language3;
	}

	public void setLanguage3(String language3) {
		this.language3 = language3;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getPostDesc() {
		return postDesc;
	}

	public void setPostDesc(String postDesc) {
		this.postDesc = postDesc;
	}

	public String getShebaoDesc() {
		return shebaoDesc;
	}

	public void setShebaoDesc(String shebaoDesc) {
		this.shebaoDesc = shebaoDesc;
	}

	public String getHealthDesc() {
		return healthDesc;
	}

	public void setHealthDesc(String healthDesc) {
		this.healthDesc = healthDesc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getHousehold() {
		return household;
	}

	public void setHousehold(String household) {
		this.household = household;
	}

	public String getPolitics() {
		return politics;
	}

	public void setPolitics(String politics) {
		this.politics = politics;
	}

	public String getEducationDegree() {
		return educationDegree;
	}

	public void setEducationDegree(String educationDegree) {
		this.educationDegree = educationDegree;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public double getDefaultAnnualLeaveDays() {
		return defaultAnnualLeaveDays;
	}

	public void setDefaultAnnualLeaveDays(double defaultAnnualLeaveDays) {
		this.defaultAnnualLeaveDays = defaultAnnualLeaveDays;
	}

	public TeeHrInsurancePara getHrInsurancePara() {
		return hrInsurancePara;
	}

	public void setHrInsurancePara(TeeHrInsurancePara hrInsurancePara) {
		this.hrInsurancePara = hrInsurancePara;
	}

	public int getSalaryLevel() {
		return salaryLevel;
	}

	public void setSalaryLevel(int salaryLevel) {
		this.salaryLevel = salaryLevel;
	}
	
}
