package com.beidasoft.zfjd.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.system.bean.StatisticDept;
import com.beidasoft.zfjd.system.dao.StatisticDeptDao;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 部门信息数量统计表SERVICE类
 */
@Service
public class StatisticDeptService extends TeeBaseService {

    @Autowired
    private StatisticDeptDao statisticDeptDao;

    /**
     * 保存部门信息数量统计表数据
     *
     * @param beanInfo
     */
    public void save(StatisticDept beanInfo) {

        statisticDeptDao.saveOrUpdate(beanInfo);
    }

    /**
     * 获取部门信息数量统计表数据
     *
     * @param id
     * @return     */
    public StatisticDept getById(String id) {

        return statisticDeptDao.get(id);
    }
    public List<StatisticDept> getByDeptId(String id) {
        return statisticDeptDao.getByDeptId(id);
    }
}
