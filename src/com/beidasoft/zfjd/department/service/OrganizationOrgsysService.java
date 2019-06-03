package com.beidasoft.zfjd.department.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.department.bean.OrganizationOrgsys;
import com.beidasoft.zfjd.department.dao.OrganizationOrgsysDao;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 部门和所属领域关系表SERVICE类
 */
@Service
public class OrganizationOrgsysService extends TeeBaseService {

    @Autowired
    private OrganizationOrgsysDao organizationOrgsysDao;

    /**
     * 保存部门和所属领域关系表数据
     *
     * @param beanInfo
     */
    public void save(OrganizationOrgsys beanInfo) {

        organizationOrgsysDao.saveOrUpdate(beanInfo);
    }

    /**
     * 获取部门和所属领域关系表数据
     *
     * @param id
     * @return     */
    public OrganizationOrgsys getById(String id) {

        return organizationOrgsysDao.get(id);
    }
}
