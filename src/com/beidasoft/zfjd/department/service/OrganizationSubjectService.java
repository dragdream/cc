package com.beidasoft.zfjd.department.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.department.bean.OrganizationSubject;
import com.beidasoft.zfjd.department.dao.OrganizationSubjectDao;
import com.beidasoft.zfjd.department.model.OrganizationSubjectModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 部门主体关联表SERVICE类
 */
@Service
public class OrganizationSubjectService extends TeeBaseService {

    @Autowired
    private OrganizationSubjectDao organizationSubjectDao;

    /**
     * 保存部门主体关联表数据
     *
     * @param beanInfo
     */
    public TeeJson save(OrganizationSubjectModel organizationSubjectModel) {
    	TeeJson json = new TeeJson();
		OrganizationSubject organizationSubject = new OrganizationSubject();
		BeanUtils.copyProperties(organizationSubjectModel, organizationSubject);
		
		organizationSubjectDao.save(organizationSubject);
		json.setRtState(true);
		return json ;
    }

    /**
     * 获取部门主体关联表数据
     *
     * @param id
     * @return     */
    public OrganizationSubject getById(String id) {

        return organizationSubjectDao.get(id);
    }
    
    /*
	 * 根据分页进行查询
	 */
	public List<OrganizationSubject> listByPage(int firstResult, int rows) {
		return organizationSubjectDao.listByPage(firstResult, rows, null);
	}

	public List<OrganizationSubject> listByPage(int firstResult, int rows, OrganizationSubjectModel queryModel) {
		return organizationSubjectDao.listByPage(firstResult, rows, queryModel);

	}
	
	public long getTotal() {
		return organizationSubjectDao.getTotal();
	}

	public long getTotal(OrganizationSubjectModel queryModel) {
		return organizationSubjectDao.getTotal(queryModel);
	}

    public List<OrganizationSubject> getSubjects(String organizationId) {
        // TODO Auto-generated method stub
        return organizationSubjectDao.getSubjects(organizationId);
    }
}
