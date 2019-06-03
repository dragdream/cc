package com.beidasoft.zfjd.system.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.system.bean.PersonUser;
import com.beidasoft.zfjd.system.dao.PersonUserDao;
import com.beidasoft.zfjd.system.model.PersonUserModel;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 人员和登录账号关系表SERVICE类
 */
@Service
public class PersonUserService extends TeeBaseService {

    @Autowired
    private PersonUserDao personUserDao;

    /**
     * 保存人员和登录账号关系表数据
     *
     * @param beanInfo
     */
    public void save(PersonUserModel personUserModel) {
    	PersonUser personUser = new PersonUser();
    	BeanUtils.copyProperties(personUserModel,personUser);
        personUserDao.save(personUser);
    }
    
    /**
     * 获取人员和登录账号关系表数据
     *
     * @param id
     * @return     */
    public PersonUser getById(String id) {

        return personUserDao.get(id);
    }
}
