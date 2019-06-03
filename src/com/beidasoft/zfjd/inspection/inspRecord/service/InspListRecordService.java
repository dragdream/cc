package com.beidasoft.zfjd.inspection.inspRecord.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.inspection.inspList.bean.InspListItem;
import com.beidasoft.zfjd.inspection.inspList.dao.InspListItemDao;
import com.beidasoft.zfjd.inspection.inspRecord.bean.InspRecordDetail;
import com.beidasoft.zfjd.inspection.inspRecord.bean.InspRecordMain;
import com.beidasoft.zfjd.inspection.inspRecord.dao.InspRecordDetailDao;
import com.beidasoft.zfjd.inspection.inspRecord.dao.InspRecordMainDao;
import com.beidasoft.zfjd.inspection.inspRecord.model.InspRecordMainModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 执法检查SERVICE类
 */
@Service
public class InspListRecordService extends TeeBaseService {

    @Autowired
    private InspRecordMainDao inspRecordMainDao;

    @Autowired
    private InspRecordDetailDao inspRecordDetailDao;
    
    @Autowired
    private InspListItemDao inspListItemDao;

    /**
     * 保存执法检查数据
     *
     * @param beanInfo
     */
    public void saveInspModuleAndItem(InspRecordMainModel beanInfo, List<InspRecordDetail> details) {
        // 删除旧有item
        inspRecordDetailDao.deleteDetailsByMainId(beanInfo);
        // 保存新item
        if (details != null && details.size() > 0) {
            inspRecordDetailDao.saveDetails(beanInfo, details);
        }
    }

    /**
     * 获取执法检查数据
     *
     * @param id
     * @return
     */
    public InspRecordMain getById(String id) {
        return inspRecordMainDao.get(id);
    }
    /**
     * @author lrn
     * @description 通过检查单id获取检查数据详情
     * @param id
     * @return
     */
    public InspRecordMainModel getByRecordId(String id) {
        InspRecordMainModel recordMainModel = new InspRecordMainModel();
        InspRecordMain recordMain = inspRecordMainDao.get(id);
        List<InspRecordDetail> recordDetailList = inspRecordDetailDao.getById(id);
        BeanUtils.copyProperties(recordMain, recordMainModel);
        if (!TeeUtility.isNullorEmpty( recordMain.getInspectionDate())) {
            recordMainModel.setInspectionDateStr(TeeDateUtil.format(recordMain.getInspectionDate(), "yyyy-MM-dd"));
        }
        if(recordDetailList!=null&&recordDetailList.size()>0){
            List<Map<?,?>> inspItems = new ArrayList<>();
            for(InspRecordDetail inspRecord:recordDetailList){
                InspListItem inspListItem = inspListItemDao.getById(inspRecord.getInspItemId());
                Map<String,Object> map = new HashMap<>();
                map.put("id", inspRecord.getInspItemId());
                map.put("isInspectionPass", inspRecord.getIsInspectionPass());
                if(inspListItem!=null){
                    map.put("inspItemName", inspListItem.getInspItemName());
                }
                inspItems.add(map);
            }
            recordMainModel.setInspItems(inspItems);
        }
        return recordMainModel;
    }

    /**
     * 保存执法检查数据
     *
     * @param beanInfo
     */
    public void save(InspRecordMain beanInfo) {
        inspRecordMainDao.saveOrUpdate(beanInfo);
    }

    /**
     * 分页获取符合条件的检查单列表
     * 
     * @param id
     * @return
     */
    public List<InspRecordMain> listByPage(TeeDataGridModel dm, InspRecordMainModel inspRecordMainModel) {
        return inspRecordMainDao.listByPage(dm.getFirstResult(), dm.getRows(), inspRecordMainModel);
    }

    /**
     * 获取符合条件的检查单总数
     * 
     * @param id
     * @return
     */
    public long listCount(InspRecordMainModel inspRecordMainModel) {
        return inspRecordMainDao.listCount(inspRecordMainModel);
    }
    /**
     * @author lrn
     * @description 删除检查单（修改isDelete状态）
     * @param id
     * @param isDelete
     */
    public void inspRecordDel(String id, Integer isDelete) {
        String[] idArray = id.split(",");
        inspRecordMainDao.inspRecordDel(idArray,isDelete);
    }

    //
    // /**
    // * 分页获取符合条件的检查单列表
    // * @param id
    // * @return */
    // public List<InspListBase> getValidInspLists(InspListBaseModel
    // inspListBaseModel) {
    // return inspListBaseDao.getValidInspLists(inspListBaseModel);
    // }
    //
    // /**
    // * 获取检查单关联的检查项
    // * @param id
    // * @return */
    // public List<InspListItem> getRelatedItemsByListId(InspListBaseModel
    // inspListBaseModel){
    //
    // return inspListItemDao.getRelatedItemsByListId(inspListBaseModel);
    // }
}
