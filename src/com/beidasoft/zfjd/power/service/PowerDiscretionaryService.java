package com.beidasoft.zfjd.power.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.power.bean.PowerDiscretionary;
import com.beidasoft.zfjd.power.dao.PowerDiscretionaryDao;
import com.beidasoft.zfjd.power.model.PowerDiscretionaryModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class PowerDiscretionaryService extends TeeBaseService{
    
    @Autowired
    private PowerDiscretionaryDao discretionaryDao;
    
    public List<PowerDiscretionary> listByPage(TeeDataGridModel dm, PowerDiscretionaryModel discretionaryModel) {
        return discretionaryDao.listByPage(dm.getFirstResult(), dm.getRows(), discretionaryModel);
    }
    
    public Long listCount(PowerDiscretionaryModel discretionaryModel) {
        return discretionaryDao.listCount(discretionaryModel);
    }
    
    public PowerDiscretionary getById(String id) {
        return discretionaryDao.get(id);
    }
    
    public void saveOrUpdate(PowerDiscretionary discretionary) {
        discretionaryDao.saveOrUpdate(discretionary);
    }
    
    public void deleteById(String id) {
        discretionaryDao.deleteById(id);
    }
    
    /**
     * 
    * @Function: listDiscretionaryByPowerIds()
    * @Description: 通过职权ID查询自由裁量基准
    *
    * @param: discretionaryModel 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月21日 下午6:13:45 
    *
     */
    public List<PowerDiscretionary> listDiscretionaryByPowerIds(TeeDataGridModel dm, PowerDiscretionaryModel discretionaryModel) {
        return discretionaryDao.listDiscretionaryByPowerIds(dm.getFirstResult(), dm.getRows(), discretionaryModel);
    }
}
