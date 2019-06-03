package com.beidasoft.zfjd.inspection.inspItem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.inspection.inspItem.bean.InspectItem;
import com.beidasoft.zfjd.inspection.inspItem.dao.InspectItemDao;
import com.beidasoft.zfjd.inspection.inspItem.model.InspectItemModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 职权基础信息表SERVICE类
 */
@Service
public class InspectItemService extends TeeBaseService {

    @Autowired
    private InspectItemDao InspectitemDao;

    /**
     * 保存检查项信息
     *
     * @param beanInfo
     */
    public void save(InspectItem beanInfo) {

        InspectitemDao.saveOrUpdate(beanInfo);
    }

    /**
     * 获取检查项信息表数据
     *
     * @param id
     * @return     */
    public InspectItem getById(String id) {
    	InspectItem inspItem = InspectitemDao.get(id);
        return inspItem;
    }
    
    /**
     * 分页获取符合条件的检查项列表
     *
     * @param id
     * @return     */
    public List<InspectItem> listByPage(TeeDataGridModel dm, InspectItemModel InspectitemModel) {
    	List <InspectItem> inspectItem = InspectitemDao.listByPage(dm.getFirstResult(), dm.getRows(), InspectitemModel);
        return inspectItem;
    }
    
    /**
     * 获取符合条件的检查项总数
     *
     * @param id
     * @return     */
    public long listCount(InspectItemModel InspectitemModel) {
        return InspectitemDao.listCount(InspectitemModel);
    }
    
    /**
     * @author lrn
     * @description 通过id删除执法检查项
     * @param ids
     * @return
     */
    public Boolean inspListDel(InspectItemModel inspItemModel) {
        Boolean flag = false;
        try {
            InspectitemDao.inspListDel(inspItemModel);
            flag = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return flag;
    }
    
    public Boolean idsCtrl(InspectItemModel inspectItemModel) {
        Boolean flag = false;
        List<?> ids = InspectitemDao.idsCtrl(inspectItemModel);
        if(ids.size()== 0 || ids == null){
            flag = true;
        }
        return flag;
    }
}
