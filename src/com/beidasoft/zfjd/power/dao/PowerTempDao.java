package com.beidasoft.zfjd.power.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.power.bean.Power;
import com.beidasoft.zfjd.power.bean.PowerTemp;
import com.beidasoft.zfjd.power.model.PowerTempModel;
import com.tianee.webframe.dao.TeeBaseDao;



/**
 * 
* 2018 
* @ClassName: PowerDao.java
* @Description: 该类的功能描述
*
* @author: chenqr
* @date: 2018年12月24日 下午3:19:55 
*
 */
@Repository
public class PowerTempDao extends TeeBaseDao<PowerTemp>{

    /**
     * 
    * @see com.tianee.webframe.dao.TeeBaseDao#saveOrUpdate(java.lang.Object)  
    * @Function: PowerDao.java
    * @Description: 保存或更新power信息
    *
    * @param:Power
    * @return：void
    * @throws：
    *
    * @author: chenqr
    * @date: 2018年12月24日 下午2:28:41 
    *
     */
    public void saveOrUpdate(PowerTemp power) {
        super.saveOrUpdate(power);
    }
    /**
     * 
    * @Function: PowerDao.java
    * @Description: 分页查询职权列表
    *
    * @param: 职权列表信息
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenqr
    * @date: 2018年12月24日 下午3:57:56 
    *
     */
    public List<PowerTemp> listByPage(int start, int length, PowerTempModel powerTempModel) {
        String hql = "from PowerTemp p where isDelete = 0 ";
        if(powerTempModel.getCurrentState() != null && !"-1".equals(powerTempModel.getCurrentState())) {
            hql = hql + " and currentState = '" + powerTempModel.getCurrentState() + "' ";
        }
        if(powerTempModel.getCode() != null && !"".equals(powerTempModel.getCode())) {
            hql = hql + " and code like '%" + powerTempModel.getCode() + "%' ";
        }
        if(powerTempModel.getName() != null && !"".equals(powerTempModel.getName())) {
            hql = hql + " and name like '%" + powerTempModel.getName() + "%' ";
        }
        if(powerTempModel.getPowerType() != null && !"-1".equals(powerTempModel.getPowerType())) {
            hql = hql + " and powerType = '" + powerTempModel.getPowerType() + "' ";
        }
        if(powerTempModel.getPowerDetail() != null && !"".equals(powerTempModel.getPowerDetail())) {
            hql = hql + " and exists (select 1 from PowerDetailTemp pd where p.id = pd.powerDetailTemp.id and code in ('" + powerTempModel.getPowerDetail().replace(",", "','") + "')) ";
        }
        if(powerTempModel.getSubjectId() != null && !"".equals(powerTempModel.getSubjectId())) {
            hql = hql + " and subjectId = '" + powerTempModel.getSubjectId() + "'";
        }
        if(powerTempModel.getSubjectIds() != null && !"".equals(powerTempModel.getSubjectIds())) {
            hql = hql + " and subjectId in ('" + powerTempModel.getSubjectIds().replace(",", "','") + "')"; 
        }
        if(powerTempModel.getOperationType() != null && !"-1".equals(powerTempModel.getOperationType() )) {
            hql = hql + " and exists (select 1 from PowerOperation po where p.id = po.powerOptTemp.id and optType = '" + powerTempModel.getOperationType() + "')";
        }
        return pageFind(hql + " order by code", start, length, null);
    }
    /**
     * 
    * @Function: PowerDao.java
    * @Description: 分页查询总数
    *
    * @param: 分页查询的总数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenq
    * @date: 2018年12月24日 下午3:58:29 
    *
     */
    public long listCount(PowerTempModel powerTempModel) {
        String hql = "select count(*) from PowerTemp p where isDelete = 0 ";
        if(powerTempModel.getCurrentState() != null && !"-1".equals(powerTempModel.getCurrentState())) {
            hql = hql + " and currentState = '" + powerTempModel.getCurrentState() + "' ";
        }
        if(powerTempModel.getCode() != null && !"".equals(powerTempModel.getCode())) {
            hql = hql + " and code like '%" + powerTempModel.getCode() + "%' ";
        }
        if(powerTempModel.getName() != null && !"".equals(powerTempModel.getName())) {
            hql = hql + " and name like '%" + powerTempModel.getName() + "%' ";
        }
        if(powerTempModel.getPowerType() != null && !"-1".equals(powerTempModel.getPowerType())) {
            hql = hql + " and powerType = '" + powerTempModel.getPowerType() + "' ";
        }
        if(powerTempModel.getPowerDetail() != null && !"".equals(powerTempModel.getPowerDetail())) {
            hql = hql + " and exists (select 1 from PowerDetailTemp pd where p.id = pd.powerDetailTemp.id and code in ('" + powerTempModel.getPowerDetail().replace(",", "','") + "')) ";
        }
        if(powerTempModel.getSubjectId() != null && !"".equals(powerTempModel.getSubjectId())) {
            hql = hql + " and subjectId = '" + powerTempModel.getSubjectId() + "'";
        }
        if(powerTempModel.getSubjectIds() != null && !"".equals(powerTempModel.getSubjectIds())) {
            hql = hql + " and subjectId in ('" + powerTempModel.getSubjectIds().replace(",", "','") + "')"; 
        }
        if(powerTempModel.getOperationType() != null && !"-1".equals(powerTempModel.getOperationType() )) {
            hql = hql + " and exists (select 1 from PowerOperation po where p.id = po.powerOptTemp.id and optType = '" + powerTempModel.getOperationType() + "')";
        }
        return count(hql, null);
    }
    /**
     * 
    * @Function: PowerDao.java
    * @Description: 通过ID查询职权详细信息
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenq
    * @date: 2018年12月25日 下午2:49:46 
    *
     */
    public PowerTemp get(String id) {
        return super.get(id);
    }
    
    public List<PowerTemp> getPowerByIds(int start, int length, PowerTempModel powerTempModel) {
        String hql = "from PowerTemp p where isDelete = 0 ";
        hql = hql + " and currentState = '10' ";
        if(powerTempModel.getIds() != null && !"".equals(powerTempModel.getIds())) {
            hql = hql + " and id in ('" + powerTempModel.getIds().replace(",", "','") + "')";
        }
        return pageFind(hql + " order by code", start, length, null);
    }
    
    public long getCountByIds(PowerTempModel powerTempModel) {
        String hql = "select count(*) from PowerTemp p where isDelete = 0 ";
        hql = hql + " and currentState = '10' ";
        if(powerTempModel.getIds() != null && !"".equals(powerTempModel.getIds())) {
            hql = hql + " and id in ('" + powerTempModel.getIds().replace(",", "','") + "')";
        }
        return count(hql, null);
    }
    
    public List<PowerTemp> getPowerTemps(String ids) {
        String hql = "from PowerTemp p where isDelete = 0 ";
        hql = hql + " and currentState = '10' ";
        if(ids != null && !"".equals(ids)) {
            hql = hql + " and id in ('" + ids.replace(",", "','") + "')";
        }
        return super.find(hql, null);
    }
    
    public void updatePowerTempStateByIds(String ids) {
        String hql = " update PowerTemp set currentState = '20' where id in ('" + ids.replace(",", "','") + "')";
        super.executeUpdate(hql, null);
    }
    
    public void updatePowertempState(String ids, String examineState) {
        String hql = " update PowerTemp set currentState = '" + examineState + "' where id in ('"+ ids.replace(",", "','") + "')";
        super.executeUpdate(hql, null);
    }
    
    public List<PowerTemp> getPowerTempList(String ids) {
        String hql = "from PowerTemp p where isDelete = 0 ";
        if(ids != null && !"".equals(ids)) {
            hql = hql + " and id in ('" + ids.replace(",", "','") + "')";
        }
        return super.find(hql, null);
    }
    
    public List<PowerTemp> getExaminePowerList(int start, int length, PowerTempModel powerTempModel) {
        String hql = "from PowerTemp p where isDelete = 0 ";
        if(powerTempModel.getCurrentState() != null && !"-1".equals(powerTempModel.getCurrentState())) {
            hql = hql + " and currentState = '" + powerTempModel.getCurrentState() + "' ";
        }
        if(powerTempModel.getCode() != null && !"".equals(powerTempModel.getCode())) {
            hql = hql + " and code like '%" + powerTempModel.getCode() + "%' ";
        }
        if(powerTempModel.getName() != null && !"".equals(powerTempModel.getName())) {
            hql = hql + " and name like '%" + powerTempModel.getName() + "%' ";
        }
        if(powerTempModel.getPowerType() != null && !"-1".equals(powerTempModel.getPowerType())) {
            hql = hql + " and powerType = '" + powerTempModel.getPowerType() + "' ";
        }
        if(powerTempModel.getPowerDetail() != null && !"".equals(powerTempModel.getPowerDetail())) {
            hql = hql + " and exists (select 1 from PowerDetailTemp pd where p.id = pd.powerDetailTemp.id and code in ('" + powerTempModel.getPowerDetail().replace(",", "','") + "')) ";
        }
        if(powerTempModel.getDepartmentId() != null && !"".equals(powerTempModel.getDepartmentId())) {
            hql = hql + " and exists (select 1 from SupOrganization supOrg where p.departmentId = supOrg.organizationId and supOrg.superviseId = '" + powerTempModel.getDepartmentId() + "') ";
        }
        
        return super.pageFind(hql, start, length, null);
    }
    
    public Long getExaminePowerCount( PowerTempModel powerTempModel) {
        String hql = "select count(*) from PowerTemp p where isDelete = 0 ";
        if(powerTempModel.getCurrentState() != null && !"-1".equals(powerTempModel.getCurrentState())) {
            hql = hql + " and currentState = '" + powerTempModel.getCurrentState() + "' ";
        }
        if(powerTempModel.getCode() != null && !"".equals(powerTempModel.getCode())) {
            hql = hql + " and code like '%" + powerTempModel.getCode() + "%' ";
        }
        if(powerTempModel.getName() != null && !"".equals(powerTempModel.getName())) {
            hql = hql + " and name like '%" + powerTempModel.getName() + "%' ";
        }
        if(powerTempModel.getPowerType() != null && !"-1".equals(powerTempModel.getPowerType())) {
            hql = hql + " and powerType = '" + powerTempModel.getPowerType() + "' ";
        }
        if(powerTempModel.getPowerDetail() != null && !"".equals(powerTempModel.getPowerDetail())) {
            hql = hql + " and exists (select 1 from PowerDetailTemp pd where p.id = pd.powerDetailTemp.id and code in ('" + powerTempModel.getPowerDetail().replace(",", "','") + "')) ";
        }
        if(powerTempModel.getDepartmentId() != null && !"".equals(powerTempModel.getDepartmentId())) {
            hql = hql + " and exists (select 1 from SupOrganization supOrg where p.departmentId = supOrg.organizationId and supOrg.superviseId = '" + powerTempModel.getDepartmentId() + "') ";
        }
        return super.count(hql, null);
    }
}
