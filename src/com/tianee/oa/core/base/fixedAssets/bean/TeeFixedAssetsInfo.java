package com.tianee.oa.core.base.fixedAssets.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 固定资产信息
 * @author kakalion
 *
 */
@Entity
@Table(name="FIXED_ASSETS_INFO")
public class TeeFixedAssetsInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="FIXED_ASSETS_INFO_seq_gen")
	@SequenceGenerator(name="FIXED_ASSETS_INFO_seq_gen", sequenceName="FIXED_ASSETS_INFO_seq")
	private int sid;
	
	@Column(name="ASSET_CODE")
	private String assetCode;//资产编号
	
	@Column(name="ASSET_NAME")
	private String assetName;//资产名称
	
	@ManyToOne
	@Index(name="IDX42dd6895623542af8b2523c2a3b")
	private TeeFixedAssetsCategory category;//资产类别
	
	@ManyToOne
	@Index(name="IDX9a36af7558bf4526aec40986c6e")
	private TeeDepartment dept;//所属部门
	
	@Column(name="ASSET_KIND")
	private int assetKind;//资产性质
	
	/**
	 * 1、购入不需安装的固定资产
	 * 2、购入需安装已完工的固定资产
	 * 3、其他单位转入的固定资产(新设备)
	 * 4、其他单位转入的固定资产(旧设备)
	 * 5、捐赠的固定资产
	 * 6、融资租赁的固定资产
	 * 7、固定资产盘盈
	 */
	@Column(name="ADD_KIND")
	private int addKind;//增加类型
	
	@Column(name="ASSETVAL")
	private double assetVal;//资产原值
	
	@Column(name="ASSETBAL")
	private double assetBal;//残值
	
	@Column(name="ASSETBAL_RATE")
	private double assetBalRate;//残值率
	
	@Column(name="ASSETYEAR")
	private double assetYear;//折旧年限
	
	@Column(name="DEPRECIATION")
	private int depreciation;//折旧方式
	
	@Column(name="VALIDE_TIME")
	private Calendar valideTime;//启用日期
	
	@ManyToOne
	@Index(name="IDX31a006fcbcc4439bb0caa0d456d")
	private TeePerson keeper;//保管人
	
	@Column(name="ASSETS_VERSION")
	private String assetsVersion;//规则型号
	
	@Column(name="MADEIN")
	private String madein ;//制造商
	
	@Column(name="dealer")
	private String dealer ;//经销商
	
	@Column(name="RECEIPT_DATE")
	@Temporal(TemporalType.DATE)
	private Date receiptDate ;//发票日期

	
	@Column(name="PHYSICAL_LOCATION")
	private String physicalLocation ;//物理位置
	
	@Column(name="USE_YEARS")
	private String useYears ;//使用年限
	
	@ManyToOne
	@Index(name="IDXcb25e2afa7834da19dd07daca63")
	@JoinColumn(name="USE_USER")
	private TeePerson useUser;//领用人
	
	@Column(name="UES_STATE" , columnDefinition = ("char(1) default 0"))
	private String useState ;//USE_STATE 固定资产状  0-正常 1-在用 2-维修中  3-已报废
	
	@Column(name="REMARK")
	private String remark;//备注
	
	@Column(name="ASSET_NUM")
	private int assetNum ;//库存数量
	
	@Column(name="USE_NUM")
	private int useNum;//领用数量
	
	@Column(name="SCRAP_NUM")
	private int scrapNum;//报废数量
	@Column(name="AUSE_NUM")
	private  int auseNum;//可用数量
	@Column(name="LAST_DPT")
	private Calendar lastDepreciation;//最后一次折旧日期
	public int getAuseNum() {
		return auseNum;
	}

	public void setAuseNum(int auseNum) {
		this.auseNum = auseNum;
	}

	public int getScrapNum() {
		return scrapNum;
	}

	public void setScrapNum(int scrapNum) {
		this.scrapNum = scrapNum;
	}
	public int getAssetNum() {
		return assetNum;
	}

	public void setAssetNum(int assetNum) {
		this.assetNum = assetNum;
	}

	public int getUseNum() {
		return useNum;
	}

	public void setUseNum(int useNum) {
		this.useNum = useNum;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getAssetCode() {
		return assetCode;
	}
	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}
	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public TeeFixedAssetsCategory getCategory() {
		return category;
	}

	public void setCategory(TeeFixedAssetsCategory category) {
		this.category = category;
	}

	public TeeDepartment getDept() {
		return dept;
	}

	public void setDept(TeeDepartment dept) {
		this.dept = dept;
	}

	public int getAssetKind() {
		return assetKind;
	}

	public void setAssetKind(int assetKind) {
		this.assetKind = assetKind;
	}

	public int getAddKind() {
		return addKind;
	}

	public void setAddKind(int addKind) {
		this.addKind = addKind;
	}

	public int getDepreciation() {
		return depreciation;
	}

	public void setDepreciation(int depreciation) {
		this.depreciation = depreciation;
	}

	public Calendar getValideTime() {
		return valideTime;
	}

	public void setValideTime(Calendar valideTime) {
		this.valideTime = valideTime;
	}

	public TeePerson getKeeper() {
		return keeper;
	}

	public void setKeeper(TeePerson keeper) {
		this.keeper = keeper;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAssetsVersion() {
		return assetsVersion;
	}

	public void setAssetsVersion(String assetsVersion) {
		this.assetsVersion = assetsVersion;
	}

	public String getMadein() {
		return madein;
	}

	public void setMadein(String madein) {
		this.madein = madein;
	}

	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getPhysicalLocation() {
		return physicalLocation;
	}

	public void setPhysicalLocation(String physicalLocation) {
		this.physicalLocation = physicalLocation;
	}

	public TeePerson getUseUser() {
		return useUser;
	}

	public void setUseUser(TeePerson useUser) {
		this.useUser = useUser;
	}

	public String getUseState() {
		return useState;
	}

	public void setUseState(String useState) {
		this.useState = useState;
	}

	public String getUseYears() {
		return useYears;
	}

	public void setUseYears(String useYears) {
		this.useYears = useYears;
	}

	public double getAssetVal() {
		return assetVal;
	}

	public void setAssetVal(double assetVal) {
		this.assetVal = assetVal;
	}

	public double getAssetBal() {
		return assetBal;
	}

	public void setAssetBal(double assetBal) {
		this.assetBal = assetBal;
	}

	public double getAssetYear() {
		return assetYear;
	}

	public void setAssetYear(double assetYear) {
		this.assetYear = assetYear;
	}

	public Calendar getLastDepreciation() {
		return lastDepreciation;
	}

	public void setLastDepreciation(Calendar lastDepreciation) {
		this.lastDepreciation = lastDepreciation;
	}

	public double getAssetBalRate() {
		return assetBalRate;
	}

	public void setAssetBalRate(double assetBalRate) {
		this.assetBalRate = assetBalRate;
	}
	
}
