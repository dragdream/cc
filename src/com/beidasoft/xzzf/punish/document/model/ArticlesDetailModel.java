package com.beidasoft.xzzf.punish.document.model;


public class ArticlesDetailModel {

	// 物品清单子表主键ID
    private String id;

    // 物品清单子表关联主表ID
    private String mainId;

    // 序号
    private int goodsCode;

    // 物品名称
    private String goodsName;

    // 计量单位（规格）
    private String goodsUnit;

    // 数量
    private int goodsSum;

    // 备注
    private String goodsRemark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public int getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(int goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public int getGoodsSum() {
		return goodsSum;
	}

	public void setGoodsSum(int goodsSum) {
		this.goodsSum = goodsSum;
	}

	public String getGoodsRemark() {
		return goodsRemark;
	}

	public void setGoodsRemark(String goodsRemark) {
		this.goodsRemark = goodsRemark;
	}
    
    
}
