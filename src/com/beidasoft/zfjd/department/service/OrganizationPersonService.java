package com.beidasoft.zfjd.department.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.department.bean.OrganizationPerson;
import com.beidasoft.zfjd.department.dao.OrganizationPersonDao;
import com.beidasoft.zfjd.department.model.OrganizationPersonModel;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 执法部门人员关系表SERVICE类
 */
@Service
public class OrganizationPersonService extends TeeBaseService {

    @Autowired
    private OrganizationPersonDao organizationPersonDao;

    /**
     * 保存执法部门人员关系表数据
     *
     * @param beanInfo
     */
    public void save(OrganizationPersonModel organizationPersonModel) {
    	OrganizationPerson organizationPerson = new OrganizationPerson();
    	BeanUtils.copyProperties(organizationPersonModel,organizationPerson);
        organizationPersonDao.save(organizationPerson);
    }

    /**
     * 获取执法部门人员关系表数据
     *
     * @param id
     * @return     */
    public OrganizationPerson getById(String id) {

        return organizationPersonDao.get(id);
    }
}
