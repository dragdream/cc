package com.beidasoft.zfjd.department.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.department.bean.OrganizationSubject;
import com.beidasoft.zfjd.department.model.OrganizationSubjectModel;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 部门主体关联表DAO类
 */
@Repository
public class OrganizationSubjectDao extends TeeBaseDao<OrganizationSubject> {

    public List<OrganizationSubject> listByPage(int firstResult, int rows, OrganizationSubjectModel queryModel) {
        String hql = " from OrganizationSubject where 1=1 and isDelete = 0";

        return super.pageFind(hql, firstResult, rows, null);

    }

    public long getTotal() {
        return super.count("select count(id) from OrganizationSubject where 1=1 and isDelete = 0 ", null);
    }

    public long getTotal(OrganizationSubjectModel queryModel) {
        String hql = "select count(id) from OrganizationSubject where 1=1 and isDelete = 0";

        return super.count(hql, null);
    }

    public List<OrganizationSubject> getSubjects(String organizationId) {
        // TODO Auto-generated method stub
        String hql = "from OrganizationSubject where organizationId = ?";
        List<Object> params = new ArrayList<>();
        params.add(organizationId);
        List<OrganizationSubject> organizationSubjects = pageFind(hql, 0, 9999, params.toArray());
        return organizationSubjects;
    }
}
