package com.beidasoft.zfjd.inspection.inspList.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.inspection.inspItem.bean.InspectItem;
import com.beidasoft.zfjd.inspection.inspItem.dao.InspectItemDao;
import com.beidasoft.zfjd.inspection.inspList.bean.InspListBase;
import com.beidasoft.zfjd.inspection.inspList.bean.InspListItem;
import com.beidasoft.zfjd.inspection.inspList.bean.InspListModule;
import com.beidasoft.zfjd.inspection.inspList.dao.InspListBaseDao;
import com.beidasoft.zfjd.inspection.inspList.dao.InspListItemDao;
import com.beidasoft.zfjd.inspection.inspList.dao.InspListModuleDao;
import com.beidasoft.zfjd.inspection.inspList.model.InspListBaseModel;
import com.beidasoft.zfjd.inspection.inspModule.bean.InspModule;
import com.beidasoft.zfjd.inspection.inspModule.dao.InspModuleDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 执法检查SERVICE类
 */
@Service
public class InspListBaseService extends TeeBaseService {

    @Autowired
    private InspListBaseDao inspListBaseDao;

    @Autowired
    private InspListModuleDao inspListModuleDao;
    
    @Autowired
    private InspListItemDao inspListItemDao;
    
    @Autowired
    private InspectItemDao inspectitemDao;
    
    @Autowired
    private InspModuleDao inspModuleDao;
    /**
     * 保存执法检查数据
     *
     * @param beanInfo
     */
    public void save(InspListBase beanInfo) {

        inspListBaseDao.saveOrUpdate(beanInfo);
    }
    
    /**
     * 保存执法检查数据
     *
     * @param beanInfo
     */
    public void saveInspModuleAndItem(InspListBase beanInfo, String[] moduleIds) {
        //删除旧有module
        if(!TeeUtility.isNullorEmpty(beanInfo)&&!TeeUtility.isNullorEmpty(beanInfo.getId())){
            inspListModuleDao.deleteRelateByListId(beanInfo.getId());
        }
        //删除旧有item
        inspListItemDao.deleteRelateByListId(beanInfo, moduleIds);
        //保存新module
        inspListModuleDao.saveListRelate(beanInfo, moduleIds);
        //保存新item
        List<InspectItem> itemList = inspectitemDao.listValidByModuleIds(moduleIds);
        inspListItemDao.saveListRelate(beanInfo, itemList);
    }

    /**
     * 获取执法检查数据
     *
     * @param id
     * @return     */
    public InspListBase getById(String id) {

        return inspListBaseDao.get(id);
        
    }
    
    /**
     * 分页获取符合条件的检查单列表
     * @param id
     * @return     */
    public List<InspListBase> listByPage(TeeDataGridModel dm, InspListBaseModel inspListBaseModel) {
        return inspListBaseDao.listByPage(dm.getFirstResult(), dm.getRows(), inspListBaseModel);
    }
    
    /**
     * 获取符合条件的检查单列表
     * @param id
     * @return     */
    public List<InspListBase> getValidInspLists(InspListBaseModel inspListBaseModel) {
        return inspListBaseDao.getValidInspLists(inspListBaseModel);
    }
    
    /**
     * 获取符合条件的检查单总数
     * @param id
     * @return     */
    public long listCount(InspListBaseModel inspListBaseModel) {
        return inspListBaseDao.listCount(inspListBaseModel);
    }
    
    /**
     * 获取检查单关联的检查项
     * @param id
     * @return     */
    public List<InspListItem> getRelatedItemsByListId(InspListBaseModel inspListBaseModel){
        
        return inspListItemDao.getRelatedItemsByListId(inspListBaseModel);
    }

    /**
     * 通过id获取检查单模块内容
     * @param id
     * @return
     */
    public InspListBaseModel getListById(String id) {
    	InspListBase inspList = inspListBaseDao.get(id);
    	List<InspListModule> listModeuleList = inspListModuleDao.getByListId(id);
    	List<String> moduleIds = new ArrayList<>();
    	String moduleIdsStr = null;
    	for(InspListModule ListModule :listModeuleList){
    		String moduleId = ListModule.getInspModuleId();
    		moduleIds.add(moduleId);
    		InspModule inspModule = inspModuleDao.get(moduleId);
    		if(!TeeUtility.isNullorEmpty(inspModule)){
    		    moduleIdsStr = moduleIdsStr==null?inspModule.getModuleName():moduleIdsStr + ','+inspModule.getModuleName();
    		}
    	}
    	InspListBaseModel inspListModel = new InspListBaseModel();
    	BeanUtils.copyProperties(inspList,inspListModel);
    	inspListModel.setModuleIdList(moduleIds);
    	inspListModel.setModuleIdsStr(moduleIdsStr);
        return inspListModel;
    }
    /**
     * @author lrn
     * @description 修改检查单模版状态
     * @param id
     * @param currentState
     * @return
     */
	public Boolean updateListState(String id, Integer currentState) {
		String [] ids = id.split(",");
		Boolean flag = inspListBaseDao.updateListState(ids,currentState);
		return flag;
	}
	
	/**
	 * @author lrn
	 * @description 未提交检查单模版数据库删除
	 * @param id
	 * @return
	 */
    public Boolean dataBaseListDelete(String id) {
        Boolean flag = false;
        //删除TBL_INSP_LIST_BASE表中检查单模版
        try {
            inspListModuleDao.deleteRelateByListId(id);
            inspListBaseDao.delete(id);
            flag = true;
        } catch (Exception e) {
            
        }
        
        return flag;
    }
    /**
     * @author lrn
     * @description 批量删除检查单模版
     * @param id
     * @param currentState
     * @return
     */
    public Boolean inspListDel(String ids, Integer isDelete) {
        Boolean flag = false;
        String[] idArray = ids.split(",");
        //删除已提交检查单模版
        try {
            //对已提交的检查单模版修改isDelete状态为1
            inspListBaseDao.inspListDel(idArray, isDelete);
            //获取未删除检查单模版的id
            List<String> id = inspListBaseDao.inspListDelete(idArray);
            //删除未提交检查单模版
            if(id.size()>0){
                //删除未提交检查单模版关联模块
                String[] string = new String[id.size()];
                inspListModuleDao.deleteRelateByListId(id.toArray(string));
            }
            flag = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return flag;
    }
    /**
     * @author lrn
     * @description 对选中的id进行权限控制
     * @param inspListBaseModel
     * @return
     */
    public Boolean idsCtrl(InspListBaseModel inspListBaseModel) {
        Boolean flag = false;
        List<?> ids = inspListBaseDao.idsCtrl(inspListBaseModel);
        if(ids.size()== 0 || ids == null){
            flag = true;
        }
        return flag;
    }
}
