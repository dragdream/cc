package com.tianee.oa.core.base.fixedAssets.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetsCategory;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 固定资产信息
 *
 */
public class TeeFixedAssetsInfoModel {
	private int sid;
	
	private String assetCode;//资产编号
	
	private String assetName;//资产名称
	
	private int typeId;//资产类别
	private String typeName;
	
	private int deptId;//所属部门
	private String deptName;
	
	private int assetKind;//资产性质
	private String assetKindDesc;
	
	private String assetsVersion;//规则型号

	private String madein ;//制造商

	private String dealer ;//经销商
	private String receiptDateStr ;//发票日期

	private String physicalLocation ;//物理位置
	
	private String useYears ;//使用年限
	private double assetBalRate;//残值率
	
	
	private String useState ;//USE_STATE 固定资产状态  0 - 正常(在库)  1-使用中  2-维修中  3-已报废  4-已丢失
	
	private String useStateDesc;//状态
	
	private String useUserId;//领用人
	private String useUserName;//姓名
	
	private String useDeptName;//领用部门
	private String useDeptId;//领用部门Id
	private String lastDepreciationDesc;//最后一次折旧日期描述
	
	
	public String getAssetKindDesc() {
		return assetKindDesc;
	}

	public void setAssetKindDesc(String assetKindDesc) {
		this.assetKindDesc = assetKindDesc;
	}

	/**
	 * 1、购入不需安装的固定资产
	 * 2、购入需安装已完工的固定资产
	 * 3、其他单位转入的固定资产(新设备)
	 * 4、其他单位转入的固定资产(旧设备)
	 * 5、捐赠的固定资产
	 * 6、融资租赁的固定资产
	 * 7、固定资产盘盈
	 */
	private int addKind;//增加类型
	private String addKindDesc;
	
	private double assetVal;//ruku
	
	private double assetBal;//残值
	
	public String getDepreciationDesc() {
		return depreciationDesc;
	}

	public void setDepreciationDesc(String depreciationDesc) {
		this.depreciationDesc = depreciationDesc;
	}

	private double assetYear;//折旧年限
	
	private int depreciation;//折旧方式
	private String depreciationDesc;
	private String valideTimeDesc;//启用日期
	
	private int keeperId;//保管人
	private String keeperName;
	

	private int auseNum ;//可用数量
	
	public int getAuseNum() {
		return auseNum;
	}

	public void setAuseNum(int auseNum) {
		this.auseNum = auseNum;
	}

	private int useNum;//领用数量
	
	private int scrapNum;//报废数量
	private int assetNum;
	
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

	

	public int getScrapNum() {
		return scrapNum;
	}

	public void setScrapNum(int scrapNum) {
		this.scrapNum = scrapNum;
	}

	private List<TeeAttachmentModel> attacheModels;
	
	public List<TeeAttachmentModel> getAttacheModels() {
		return attacheModels;
	}

	public void setAttacheModels(List<TeeAttachmentModel> attacheModels) {
		this.attacheModels = attacheModels;
	}

	private String remark;//备注

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

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
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

	public String getAddKindDesc() {
		return addKindDesc;
	}

	public void setAddKindDesc(String addKindDesc) {
		this.addKindDesc = addKindDesc;
	}

	public int getDepreciation() {
		return depreciation;
	}

	public void setDepreciation(int depreciation) {
		this.depreciation = depreciation;
	}

	public String getValideTimeDesc() {
		return valideTimeDesc;
	}

	public void setValideTimeDesc(String valideTimeDesc) {
		this.valideTimeDesc = valideTimeDesc;
	}

	public int getKeeperId() {
		return keeperId;
	}

	public void setKeeperId(int keeperId) {
		this.keeperId = keeperId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getKeeperName() {
		return keeperName;
	}

	public void setKeeperName(String keeperName) {
		this.keeperName = keeperName;
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

	public String getReceiptDateStr() {
		return receiptDateStr;
	}

	public void setReceiptDateStr(String receiptDateStr) {
		this.receiptDateStr = receiptDateStr;
	}

	public String getPhysicalLocation() {
		return physicalLocation;
	}

	public void setPhysicalLocation(String physicalLocation) {
		this.physicalLocation = physicalLocation;
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

	public String getUseStateDesc() {
		return useStateDesc;
	}

	public void setUseStateDesc(String useStateDesc) {
		this.useStateDesc = useStateDesc;
	}

	public String getUseUserId() {
		return useUserId;
	}

	public void setUseUserId(String useUserId) {
		this.useUserId = useUserId;
	}

	public String getUseUserName() {
		return useUserName;
	}

	public void setUseUserName(String useUserName) {
		this.useUserName = useUserName;
	}

	public String getUseDeptName() {
		return useDeptName;
	}

	public void setUseDeptName(String useDeptName) {
		this.useDeptName = useDeptName;
	}

	public String getUseDeptId() {
		return useDeptId;
	}

	public void setUseDeptId(String useDeptId) {
		this.useDeptId = useDeptId;
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

	public String getLastDepreciationDesc() {
		return lastDepreciationDesc;
	}

	public void setLastDepreciationDesc(String lastDepreciationDesc) {
		this.lastDepreciationDesc = lastDepreciationDesc;
	}

	public double getAssetBalRate() {
		return assetBalRate;
	}

	public void setAssetBalRate(double assetBalRate) {
		this.assetBalRate = assetBalRate;
	}
	

}
