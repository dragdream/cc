package com.beidasoft.xzzf.informationEntry.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.informationEntry.bean.InforEntryBase;
import com.beidasoft.xzzf.informationEntry.bean.InforEntryItem;
import com.beidasoft.xzzf.informationEntry.dao.InforEntryItemDao;
import com.beidasoft.xzzf.informationEntry.model.InforEntryBaseModel;
import com.beidasoft.xzzf.informationEntry.model.InforEntryDecisionModel;
import com.beidasoft.xzzf.informationEntry.model.InforEntryItemModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 子表物品清单SERVICE类
 */
@Service
public class InforEntryItemService extends TeeBaseService {

    @Autowired
    private InforEntryItemDao inforEntryItemDao;

    /**
     * 保存子表物品清单数据
     *
     * @param beanInfo
     */
    public void save(InforEntryItem beanInfo) {

        inforEntryItemDao.saveOrUpdate(beanInfo);
    }

    /**
     * 获取子表物品清单数据
     *
     * @param id
     * @return     */
    public InforEntryItem getById(String id) {
        return inforEntryItemDao.get(id);
    }
    
    /**
     * 分页查询
     * @param dataGridModel
     * @param entryBase
     * @param request
     * @return
     */
    public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,String caseId,HttpServletRequest request){
    	TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
    	//通过分页获取检查对象信息数据的List集合
		long total = inforEntryItemDao.getTotal(caseId);
		List<InforEntryItem> itemList = inforEntryItemDao.getbyCaseId(caseId, dataGridModel);
		List<InforEntryItemModel> modelList = new ArrayList<InforEntryItemModel>();
		for (InforEntryItem item : itemList) {
			InforEntryItemModel model = new InforEntryItemModel();
			BeanUtils.copyProperties(item, model);
			modelList.add(model);
		}
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		return dataGridJson;
    }
    
    /**
     * 根据id删除物品
     * @param id
     * @return
     */
    public TeeJson deleteById(String id){
    	inforEntryItemDao.delete(id);
    	TeeJson json = new TeeJson();
    	json.setRtState(true);
    	return json;
    }
    
    /**
     * 批量修改数据根据caseId
     * @param decModel
     * @return
     */
    public int updateByCaseId(InforEntryDecisionModel decModel) {
    	return inforEntryItemDao.updateByCaseId(decModel);
    }
    
}
