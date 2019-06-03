package com.beidasoft.zfjd.supervise.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.supervise.bean.SupOrganization;
import com.beidasoft.zfjd.supervise.dao.SupOrganizationDao;
import com.beidasoft.zfjd.supervise.model.SupOrganizationModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 执法部门监督部门关系表SERVICE类
 */

@Service
public class SupOrganizationService extends TeeBaseService {

    @Autowired
    private SupOrganizationDao supOrganizationDao;

    /**
     * 保存执法部门监督部门关系表数据
     *
     * @param beanInfo
     * @return 
     */
	public TeeJson save(SupOrganizationModel supOrganizationModel){
		TeeJson json = new TeeJson();
			SupOrganization supOrganization = new SupOrganization();
			BeanUtils.copyProperties(supOrganizationModel, supOrganization);
			
			supOrganizationDao.save(supOrganization);
		json.setRtState(true);
		return json ;
	}

    /**
     * 获取执法部门监督部门关系表数据
     *
     * @param id
     * @return     */
    public SupOrganization getById(String id) {

        return supOrganizationDao.get(id);
    }
}
