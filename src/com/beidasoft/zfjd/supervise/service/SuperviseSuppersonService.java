package com.beidasoft.zfjd.supervise.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.supervise.bean.SuperviseSupperson;
import com.beidasoft.zfjd.supervise.dao.SuperviseSuppersonDao;
import com.beidasoft.zfjd.supervise.model.SuperviseSuppersonModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 监督部门人员关系表SERVICE类
 */
@Service
public class SuperviseSuppersonService extends TeeBaseService {

    @Autowired
    private SuperviseSuppersonDao superviseSuppersonDao;

    /**
     * 保存监督部门人员关系表数据
     *
     * @param beanInfo
     */
    public TeeJson save(SuperviseSuppersonModel superviseSuppersonModel) {
    	TeeJson json = new TeeJson();
		SuperviseSupperson superviseSupperson = new SuperviseSupperson();
		BeanUtils.copyProperties(superviseSuppersonModel, superviseSupperson);
		
		superviseSuppersonDao.save(superviseSupperson);
		json.setRtState(true);
		return json ;
    }

    /*
	 * 通过主键删除用户信息
	 */
	public void deleteById(String id) {

		superviseSuppersonDao.delete(id);
	}
    /**
     * 获取监督部门人员关系表数据
     *
     * @param id
     * @return     */
    public SuperviseSupperson getById(String id) {

        return superviseSuppersonDao.get(id);
    }
}
