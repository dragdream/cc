package com.tianee.oa.core.workflow.flowmanage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowSortDao;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowTypeDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowSortModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeFlowSortService extends TeeBaseService implements TeeFlowSortServiceInterface{
	@Autowired
	private TeeFlowSortDao flowSortDao;
	
	@Autowired
	private TeeFlowTypeDao flowTypeDao;
	
	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceItf#save(com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort)
	 */
	@Override
	public void save(TeeFlowSort flowSort){
		int maxOrder = flowSortDao.getMaxOrderNo();
		flowSort.setOrderNo(maxOrder+1);
		flowSortDao.save(flowSort);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceItf#get(int)
	 */
	@Override
	public TeeFlowSort get(int flowSortId){
		return flowSortDao.get(flowSortId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceItf#getModel(int)
	 */
	@Override
	public TeeFlowSortModel getModel(int flowSortId){
		TeeFlowSort flowSort = flowSortDao.get(flowSortId);
		TeeFlowSortModel m = new TeeFlowSortModel();
		if(flowSort!=null){
			BeanUtils.copyProperties(flowSort, m);
		}else{
			m.setSortName("默认分类");
		}
		return m;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceItf#getModelList()
	 */
	@Override
	public List<TeeFlowSortModel> getModelList(){
		List<TeeFlowSortModel> list = new ArrayList<TeeFlowSortModel>();
		List<TeeFlowSort> flowSortList = flowSortDao.list();
		
		for(TeeFlowSort flowSort:flowSortList){
			TeeFlowSortModel m = new TeeFlowSortModel();
			BeanUtils.copyProperties(flowSort, m);
			list.add(m);
		}
		
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceItf#update(com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort)
	 */
	@Override
	public void update(TeeFlowSort flowSort){
		flowSortDao.update(flowSort);
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceItf#delete(int)
	 */
	@Override
	public void delete(int sortId){
		//最后删除该流程分类
		simpleDaoSupport.executeUpdate("update TeeFlowType ft set ft.flowSort=null where ft.flowSort.sid="+sortId, null);
		flowSortDao.delete(sortId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceItf#doSortOrder(int, int)
	 */
	@Override
	public void doSortOrder(int type,int flowSortId){
		TeeFlowSort tmpFlowSort = null;
		TeeFlowSort currFlowSort = flowSortDao.get(flowSortId);
		int tmpOrder = 0;
		if(type==1){
			tmpFlowSort = flowSortDao.getPrevFlowSort(flowSortId);
		}else if(type==2){
			tmpFlowSort = flowSortDao.getNextFlowSort(flowSortId);
		}
		
		if(tmpFlowSort!=null){
			tmpOrder = tmpFlowSort.getOrderNo();
			tmpFlowSort.setOrderNo(currFlowSort.getOrderNo());
			currFlowSort.setOrderNo(tmpOrder);
			update(tmpFlowSort);
			update(currFlowSort);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceItf#getMaxOrderNo()
	 */
	@Override
	public int getMaxOrderNo(){
		return flowSortDao.getMaxOrderNo();
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceItf#list()
	 */
	@Override
	public List<TeeFlowSort> list(){
		return flowSortDao.list();
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceItf#datagrid(com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm){
		TeeEasyuiDataGridJson easyuiDataGridJson = new TeeEasyuiDataGridJson();
		
		List<TeeFlowSortModel> flowSortModelList = new ArrayList<TeeFlowSortModel>();
		List<TeeFlowSort> flowSortList = flowSortDao.list((dm.getPage()-1)*dm.getRows(), dm.getRows());
		
		for(TeeFlowSort flowSort:flowSortList){
			TeeFlowSortModel m = new TeeFlowSortModel();
			BeanUtils.copyProperties(flowSort, m);
			flowSortModelList.add(m);
		}
		
		easyuiDataGridJson.setTotal(flowSortDao.listCount());
		easyuiDataGridJson.setRows(flowSortModelList);
		
		return easyuiDataGridJson;
	}
	

	
}
