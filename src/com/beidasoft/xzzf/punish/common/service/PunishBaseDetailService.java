package com.beidasoft.xzzf.punish.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishBaseDetail;
import com.beidasoft.xzzf.punish.common.dao.PunishBaseDetailDao;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 案件办理标识表子表SERVICE类
 */
@Service
public class PunishBaseDetailService extends TeeBaseService {

    @Autowired
    private PunishBaseDetailDao punishBaseDetailDao;

    /**
     * 保存案件办理标识表子表数据
     *
     * @param beanInfo
     */
    public void save(PunishBaseDetail beanInfo) {

        punishBaseDetailDao.saveOrUpdate(beanInfo);
    }

    /**
     * 获取案件办理标识表子表数据
     *
     * @param id
     * @return     */
    public PunishBaseDetail getById(String id) {

        return punishBaseDetailDao.get(id);
    }
}
