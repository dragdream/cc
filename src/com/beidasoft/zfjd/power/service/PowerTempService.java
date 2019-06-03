package com.beidasoft.zfjd.power.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.power.bean.PowerAdjust;
import com.beidasoft.zfjd.power.bean.PowerAdjustAuthority;
import com.beidasoft.zfjd.power.bean.PowerAdjustTache;
import com.beidasoft.zfjd.power.bean.PowerOperation;
import com.beidasoft.zfjd.power.bean.PowerTemp;
import com.beidasoft.zfjd.power.dao.PowerAdjustAuthorityDao;
import com.beidasoft.zfjd.power.dao.PowerAdjustDao;
import com.beidasoft.zfjd.power.dao.PowerAdjustTacheDao;
import com.beidasoft.zfjd.power.dao.PowerDetailTempDao;
import com.beidasoft.zfjd.power.dao.PowerFlowsheetTempDao;
import com.beidasoft.zfjd.power.dao.PowerGistTempDao;
import com.beidasoft.zfjd.power.dao.PowerLevelTempDao;
import com.beidasoft.zfjd.power.dao.PowerOperationDao;
import com.beidasoft.zfjd.power.dao.PowerSubjectTempDao;
import com.beidasoft.zfjd.power.dao.PowerTempDao;
import com.beidasoft.zfjd.power.model.PowerTempModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;



@Service
public class PowerTempService extends TeeBaseService{
    @Autowired
    private PowerTempDao powerTempDao;
    
    @Autowired
    private PowerGistTempDao gistTempDao;
    
    @Autowired
    private PowerDetailTempDao detailTempDao;
    
    @Autowired
    private PowerLevelTempDao levelTempDao;
    
    @Autowired
    private PowerFlowsheetTempDao flowsheetTempDao;
    
    @Autowired
    private PowerOperationDao operationDao;
    
    @Autowired
    private PowerSubjectTempDao subjectTempDao;
    
    @Autowired
    private PowerAdjustDao adjustDao;
    
    @Autowired
    private PowerAdjustAuthorityDao adjustAuthorityDao;
    
    @Autowired
    private PowerAdjustTacheDao adjustTacheDao;
    
    @Autowired
    private PowerService powerService;
    
    
    public List<PowerTemp> listByPage(TeeDataGridModel dm, PowerTempModel powerModel) {
        return powerTempDao.listByPage(dm.getFirstResult(), dm.getRows(), powerModel);
    }
    
    public long listCount(PowerTempModel powerModel) {
        return powerTempDao.listCount(powerModel);
    }
    
    public PowerTemp get(String id) {
        return powerTempDao.get(id);
    }
    
    public void savePower(PowerTemp power) throws Exception{
        
        // 重新写入职权分类子表
        if(power.getDetails() != null) {
            detailTempDao.deleteByPowerId(power);
            detailTempDao.batchSave(power.getDetails());
        }
        
        // 重新写入职权流程图子表
        if(power.getFlows() != null) {
            flowsheetTempDao.deleteByPowerId(power);
            flowsheetTempDao.batchSave(power.getFlows());
        }
        
        // 重新写入职权层级子表
        if(power.getLevels() != null) {
            levelTempDao.deleteByPowerId(power);
            levelTempDao.batchSave(power.getLevels());
        }
        
        if(power.getGists() != null) {
            // 重新写入职权依据子表
            gistTempDao.deleteByPowerId(power);
            gistTempDao.batchSave(power.getGists());
        }
        
        // 重新写入职权实施主体表
        if(power.getSubjects() != null) {
            subjectTempDao.deleteByPowerId(power);
            subjectTempDao.batchSave(power.getSubjects());
        }
        
        // 写入职权调整信息
        operationDao.batchSave(power.getOperationTemps());
        
        //更新或保存职权信息
        powerTempDao.saveOrUpdate(power);
    }
    
    public List<PowerTemp> getPowerByIds(TeeDataGridModel dm, PowerTempModel powerModel) {
        return powerTempDao.getPowerByIds(dm.getFirstResult(), dm.getRows(), powerModel);
    }
    
    public long getCountByIds(PowerTempModel powerModel) {
        return powerTempDao.getCountByIds(powerModel);
    }
    
    public List<PowerTemp> getPowerTemps(String ids) {
        return powerTempDao.getPowerTemps(ids);
    }
    
    public void saveAdjust(PowerAdjust powerAdjust, PowerAdjustTache adjustTache, List<PowerAdjustAuthority> adjustAuthorities, String ids) {
        // 保存职权变更单信息
        adjustDao.saveOrUpdate(powerAdjust);
        // 保存职权提交环节信息
        adjustTacheDao.saveOrUpdate(adjustTache);
        // 批量保存职权变更单包含职权
        adjustAuthorityDao.batchSave(adjustAuthorities);
        // 更新临时职权状态为 20 提交
        powerTempDao.updatePowerTempStateByIds(ids);
    }
    
    public List<PowerOperation> getPowerOpts(String powerTempId) {
        return operationDao.getPowerOpts(powerTempId);
    }
    /**
     * 
    * @Function: transferPower
    * @Description: 根据职权调整信息修改正式职权
    *
    * @param:powerTempModels
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenqr
    * @date: 2019年1月22日 下午2:26:59 
    *
     */
    public void transferPower(List<PowerTempModel> powerTempModels) throws Exception {
        try {
            List<Object> paramsList = null;
            for(int i = 0;i < powerTempModels.size(); i++) {
                if(!"50".equals(powerTempModels.get(i).getOperationType()) && "90".equals(powerTempModels.get(i).getCurrentState())) {
                    paramsList = new ArrayList<Object>();
                    String sql = "insert into tbl_power_list"
                            + "(ID, NAME, POWER_TYPE, DEPARTMENT_ID, IS_DELETE, "
                            + "CODE, SUBJECT_DESC, SUBJECT_ID, CREATE_DATE, "
                            + "FLOW_PICTURE_TYPE, IS_CRIMINAL, REVOKE_DATE, "
                            + "BATCH_CODE, DELETE_DATE, POWER_MOLD, "
                            + "UPDATE_DATE) ";
                    paramsList.add(new Date());
                    sql = sql + " select ID, NAME, POWER_TYPE, DEPARTMENT_ID, IS_DELETE, '" + powerTempModels.get(i).getCode() + "', SUBJECT_DESC, SUBJECT_ID,?, FLOW_PICTURE_TYPE, IS_CRIMINAL, REVOKE_DATE, ";
                    sql = sql + "'" + powerTempModels.get(i).getBatchCode() + "',";
                    sql = sql + "DELETE_DATE, POWER_MOLD, UPDATE_DATE";
                    sql = sql + " from tbl_power_list_temp where id = '" + powerTempModels.get(i).getId() + "' ";
                    
                    String detailSql = "insert into tbl_power_detail(ID, POWER_ID, NAME, CODE) select ID, POWER_ID, NAME, CODE from tbl_power_detail_temp where power_id = '" + powerTempModels.get(i).getId() + "' ";
                    String flowsheetSql = "insert into tbl_power_flowsheet(ID, POWER_ID, FILE_NAME, FLOWSHEET_TYPE, FILE_ID) select ID, POWER_ID, FILE_NAME, FLOWSHEET_TYPE, FILE_ID from tbl_power_flowsheet_temp where power_id = '" + powerTempModels.get(i).getId() + "' ";
                    String gistSql = "insert into tbl_power_gist(ID, POWER_ID, LAW_NAME, LAW_DETAIL_ID, GIST_TYPE, GIST_SERIES, GIST_CHAPTER, GIST_SECTION, GIST_STRIP, GIST_FUND, GIST_ITEM, CONTENT, IS_DELETE, GIST_CODE, SUBJECT_ID, CREATE_TIME, GIST_CATALOG) select ID, POWER_ID, LAW_NAME, LAW_DETAIL_ID, GIST_TYPE, GIST_SERIES, GIST_CHAPTER, GIST_SECTION, GIST_STRIP, GIST_FUND, GIST_ITEM, CONTENT, IS_DELETE, GIST_CODE, SUBJECT_ID, CREATE_TIME, GIST_CATALOG from tbl_power_gist_temp where power_id = '" + powerTempModels.get(i).getId() + "' ";
                    String levelSql = "insert into tbl_power_level(ID, POWER_ID, POWER_STAGE, REMARK) select ID, POWER_ID, POWER_STAGE, REMARK from tbl_power_level_temp where power_id = '" + powerTempModels.get(i).getId() + "' ";;
                    String subjectSql = "insert into tbl_power_subject(ID, POWER_ID, SUBJECT_ID, CREATE_DATE, IS_DELETE, SUBJECT_NAME, IS_DEPUTE) select ID, POWER_ID, SUBJECT_ID, CREATE_DATE, IS_DELETE, SUBJECT_NAME, IS_DEPUTE from tbl_power_subject_temp where power_id = '" + powerTempModels.get(i).getId() + "' ";
                    
                    
                    simpleDaoSupport.executeNativeUpdate(sql, paramsList.toArray());
                    simpleDaoSupport.executeNativeUpdate(detailSql, null);
                    simpleDaoSupport.executeNativeUpdate(flowsheetSql, null);
                    simpleDaoSupport.executeNativeUpdate(gistSql, null);
                    simpleDaoSupport.executeNativeUpdate(levelSql, null);
                    simpleDaoSupport.executeNativeUpdate(subjectSql, null);
                }
                if(!"10".equals(powerTempModels.get(i).getOperationType()) && "90".equals(powerTempModels.get(i).getCurrentState())) {
                    powerService.revokePower(powerTempModels.get(i).getBatchCode(), powerTempModels.get(i).getPowerFormalIds());
                }

                String powerTempSql = "update tbl_power_list_temp set current_state = '" + powerTempModels.get(i).getCurrentState() + "' where id = '" + powerTempModels.get(i).getId() + "' ";
                simpleDaoSupport.executeNativeUpdate(powerTempSql, null);
            }
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }
    /**
     * 
    * @Function: PowerTempService.java
    * @Description: 修改职权状态
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenq
    * @date: 2019年3月5日 上午11:12:57 
    *
     */
    public void updatePowertempState(String ids, String examineState) {
        powerTempDao.updatePowertempState(ids, examineState);
    }
    
    public List<PowerTemp> getPowerTempList(String ids) {
        return powerTempDao.getPowerTempList(ids);
    }
    /**
     * 
    * @Function: PowerTempService.java
    * @Description: 根据监督部门监督范围分页查询职权
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenq
    * @date: 2019年3月5日 下午4:40:41 
    *
     */
    public List<PowerTemp> getExaminePowerList(TeeDataGridModel dm, PowerTempModel powerTempModel) {
        return powerTempDao.getExaminePowerList(dm.getFirstResult(), dm.getRows(), powerTempModel);
    }
    /**
     * 
    * @Function: PowerTempService.java
    * @Description: 根据监督部门范围查询职权总数
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenq
    * @date: 2019年3月5日 下午4:41:03 
    *
     */
    public Long getExaminePowerCount(PowerTempModel powerTempModel) {
        return powerTempDao.getExaminePowerCount(powerTempModel);
    }
}
