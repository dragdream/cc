package com.beidasoft.xzzf.punish.document.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 物品清单子表实体类
 */
@Entity
@Table(name="ZF_DOC_ARTICLES_DETAIL")
public class DocArticlesDetail {
    // 物品清单子表主键ID
    @Id
    @Column(name = "ID")
    private String id;

    // 物品清单子表关联主表ID
    @Column(name = "MAIN_ID")
    private String mainId;

    // 序号
    @Column(name = "GOODS_CODE")
    private int goodsCode;

    // 物品名称
    @Column(name = "GOODS_NAME")
    private String goodsName;

    // 计量单位（规格）
    @Column(name = "GOODS_UNIT")
    private String goodsUnit;

    // 数量
    @Column(name = "GOODS_SUM")
    private int goodsSum;

    // 备注
    @Column(name = "GOODS_REMARK")
    private String goodsRemark;
    
    //是否送鉴定
    @Column(name = "GOODS_IS_APPRAISAL")
    private int goodsIsAppraisal;
    
    //鉴定数量
    @Column(name = "GOODS_APPRAISAL_SUM")
    private int goodsAppraisalSum;
    
    //剩余物品位置
    @Column(name = "GOODS_ADDRESS")
    private String goodsAddress;
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

	public int getGoodsIsAppraisal() {
		return goodsIsAppraisal;
	}

	public void setGoodsIsAppraisal(int goodsIsAppraisal) {
		this.goodsIsAppraisal = goodsIsAppraisal;
	}

	public int getGoodsAppraisalSum() {
		return goodsAppraisalSum;
	}

	public void setGoodsAppraisalSum(int goodsAppraisalSum) {
		this.goodsAppraisalSum = goodsAppraisalSum;
	}

	public String getGoodsAddress() {
		return goodsAddress;
	}

	public void setGoodsAddress(String goodsAddress) {
		this.goodsAddress = goodsAddress;
	}

}
