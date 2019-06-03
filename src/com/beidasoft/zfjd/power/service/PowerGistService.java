package com.beidasoft.zfjd.power.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.power.bean.PowerGist;
import com.beidasoft.zfjd.power.bean.PowerGistTemp;
import com.beidasoft.zfjd.power.dao.PowerGistDao;
import com.beidasoft.zfjd.power.model.PowerGistModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;

@Service("powerGistService")
public class PowerGistService {

    @Autowired
    private PowerGistDao powerGistDao;
    
    public List<PowerGist> listByPage(TeeDataGridModel dm, PowerGistModel powerGistModel) {
        return powerGistDao.listByPage(dm.getFirstResult(), dm.getRows(), powerGistModel);
    }
    
    public long listCount(PowerGistModel powerGistModel) {
        return powerGistDao.listCount(powerGistModel);
    }
    
    public List<PowerGist> findGistsByPowerId(TeeDataGridModel dm, String powerId) {
        return powerGistDao.findGistsByPowerId(dm.getFirstResult(), dm.getRows(), powerId);
    }
    
    public long findGistsCountByPowerId(String powerId) {
        return powerGistDao.findGistsCountByPowerId(powerId);
    }
    
    /**
     * 
    * @Function: findGistsByPowerIds()
    * @Description: 通过职权ID，依据类型查询依据
    *
    * @param: dm datagrid表格参数
    * @param: pgModel 查询参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月15日 下午5:10:24 
    *
     */
    public List<PowerGist> findGistsByPowerIds(TeeDataGridModel dm, PowerGistModel pgModel) {
        return powerGistDao.findGistsByPowerIds(dm.getFirstResult(),dm.getRows(), pgModel);
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public PowerGist getById(String id){
    	return powerGistDao.get(id);
    }
}