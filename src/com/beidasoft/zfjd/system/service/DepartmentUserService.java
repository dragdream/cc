package com.beidasoft.zfjd.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.system.bean.DepartmentUser;
import com.beidasoft.zfjd.system.dao.DepartmentUserDao;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 部门主体和登录账号关系表SERVICE类
 */
@Service
public class DepartmentUserService extends TeeBaseService {

    @Autowired
    private DepartmentUserDao departmentUserDao;

    /**
     * 保存部门主体和登录账号关系表数据
     *
     * @param beanInfo
     */
    public void save(DepartmentUser beanInfo) {

        departmentUserDao.saveOrUpdate(beanInfo);
    }

    /**
     * 获取部门主体和登录账号关系表数据
     *
     * @param id
     * @return     */
    public DepartmentUser getById(String id) {

        return departmentUserDao.get(id);
    }
}
