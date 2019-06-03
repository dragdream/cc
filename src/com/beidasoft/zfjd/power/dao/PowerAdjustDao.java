package com.beidasoft.zfjd.power.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.power.bean.PowerAdjust;
import com.beidasoft.zfjd.power.model.PowerAdjustModel;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PowerAdjustDao extends TeeBaseDao<PowerAdjust>{
    
    public List<PowerAdjust> listByPage(int firstResult, int rows, PowerAdjustModel adjustModel) {
        String hql = " from PowerAdjust pa where 1=1";
        
        if(adjustModel.getBatchCode() != null && !"".equals(adjustModel.getBatchCode())) {
            hql = hql + " and batchCode like '%" + adjustModel.getBatchCode() + "%'";
        }
        if(adjustModel.getCurrentStatus() != null && !"-1".equals(adjustModel.getCurrentStatus())) {
            hql = hql + " and currentStatus = '" + adjustModel.getCurrentStatus() + "'";
        }
        
        if(adjustModel.getDepartmentId() != null && !"".equals(adjustModel.getDepartmentId())) {
            hql = hql +  " and submitDepartment.id = '" + adjustModel.getDepartmentId() + "' ";
        }
        
        if(adjustModel.getSupDeptId() != null && !"".equals(adjustModel.getSupDeptId())) {
        	hql = hql + " and exists (select 1 from SupOrganization supOrg where pa.submitDepartment.id = supOrg.organizationId and supOrg.superviseId = '" + adjustModel.getSupDeptId() + "') ";
        }
        return super.pageFind(hql, firstResult, rows, null);
    }
    
    public Long listCount(PowerAdjustModel adjustModel) {
        String hql = "select count(*) from PowerAdjust where 1=1 ";

        if(adjustModel.getBatchCode() != null && !"".equals(adjustModel.getBatchCode())) {
            hql = hql + " and batchCode like '%" + adjustModel.getBatchCode() + "%'";
        }
        if(adjustModel.getCurrentStatus() != null && !"-1".equals(adjustModel.getCurrentStatus())) {
            hql = hql + " and currentStatus = '" + adjustModel.getCurrentStatus() + "'";
        }
        
        if(adjustModel.getDepartmentId() != null && !"".equals(adjustModel.getDepartmentId())) {
            hql = hql +  " and submitDepartment.id = '" + adjustModel.getDepartmentId() + "' ";
        }
        if(adjustModel.getSupDeptId() != null && !"".equals(adjustModel.getSupDeptId())) {
        	hql = hql + " and exists (select 1 from SupOrganization supOrg where pa.submitDepartment.id = supOrg.organizationId and supOrg.superviseId = '" + adjustModel.getSupDeptId() + "') ";
        }
        return super.count(hql, null);
    }
    
    public void saveOrUpdate(PowerAdjust powerAdjust) {
        super.saveOrUpdate(powerAdjust);
    }
    
    public PowerAdjust getById(String id) {
        return super.get(id);
    }

    public void bindRunId(String id, int runId) {
        String hql = " update PowerAdjust set runId = " + runId + " where id = '" + id + "'";
        deleteOrUpdateByQuery(hql, null);
    }
    
    public void closedAdjust(PowerAdjust powerAdjust) {
        String hql = " update PowerAdjust set currentStatus = '" + powerAdjust.getCurrentStatus() + "' where id = '" + powerAdjust.getId() + "'";
        deleteOrUpdateByQuery(hql, null);
    }
}
