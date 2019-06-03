package com.beidasoft.zfjd.power.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
* @ClassName: PowerDetail.java
* @Description: 职权分类表实体类
*
* @author: chenqr
* @date: 2018年12月24日 下午3:20:13 
*
 */
@Entity
@Table(name="TBL_POWER_DETAIL_TEMP")
public class PowerDetailTemp {
    // 主键
    @Id
    @Column(name = "ID")
    private String id;

    // 职权ID
    @ManyToOne
    @JoinColumn(name = "POWER_ID") // 这是双向的 要写JoinColumn 对面要写 mappedBy
    private PowerTemp powerDetailTemp;

    // 分类名称
    @Column(name = "NAME")
    private String name;

    // 分类代码
    @Column(name = "CODE")
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public PowerTemp getPowerDetailTemp() {
        return powerDetailTemp;
    }

    public void setPowerDetailTemp(PowerTemp powerDetailTemp) {
        this.powerDetailTemp = powerDetailTemp;
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
