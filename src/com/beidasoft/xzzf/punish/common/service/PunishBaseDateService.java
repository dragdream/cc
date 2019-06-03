package com.beidasoft.xzzf.punish.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




import com.beidasoft.xzzf.punish.common.bean.PunishBaseDate;
import com.beidasoft.xzzf.punish.common.dao.PunishBaseDateDao;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 案件办理时间表SERVICE类
 */
@Service
public class PunishBaseDateService extends TeeBaseService {

    @Autowired
    private PunishBaseDateDao punishBaseDateDao;

    /**
     * 保存案件办理时间表数据
     *
     * @param beanInfo
     */
    public void save(PunishBaseDate beanInfo) {

        punishBaseDateDao.saveOrUpdate(beanInfo);
    }

    /**
     * 获取案件办理时间表数据
     *
     * @param id
     * @return     */
    public PunishBaseDate getById(String id) {

        return punishBaseDateDao.get(id);
    }
}
