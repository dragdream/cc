package com.tianee.oa.core.workflow.formmanage.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.workflow.formmanage.bean.TeeFormSort;
import com.tianee.oa.core.workflow.formmanage.dao.TeeFormDao;
import com.tianee.oa.core.workflow.formmanage.dao.TeeFormSortDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFormSortModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeFormSortService  extends TeeBaseService implements TeeFormSortServiceInterface{
	@Autowired
	private TeeFormSortDao formSortDao;
	
	@Autowired
	private TeeFormDao formDao;
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFormSortServiceInterface#save(com.tianee.oa.core.workflow.formmanage.bean.TeeFormSort)
	 */
	@Override
	public void save(TeeFormSort formSort){
		int maxOrder = formSortDao.getMaxOrderNo();
		formSort.setOrderNo(maxOrder+1);
		formSortDao.save(formSort);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFormSortServiceInterface#get(int)
	 */
	@Override
	public TeeFormSort get(int formSortId){
		return formSortDao.get(formSortId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFormSortServiceInterface#getModel(int)
	 */
	@Override
	public TeeFormSortModel getModel(int formSortId){
		TeeFormSort formSort = formSortDao.get(formSortId);
		TeeFormSortModel m = new TeeFormSortModel();
		if(formSort!=null){
			BeanUtils.copyProperties(formSort, m);
		}else{
			m.setSortName("默认分类");
		}
		
		return m;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFormSortServiceInterface#getModelList()
	 */
	@Override
	public List<TeeFormSortModel> getModelList(){
		List<TeeFormSortModel> list = new ArrayList<TeeFormSortModel>();
		
		List<TeeFormSort> formSortList = formSortDao.list();
		for(TeeFormSort formSort:formSortList){
			TeeFormSortModel formSortModel = new TeeFormSortModel();
			BeanUtils.copyProperties(formSort, formSortModel);
			list.add(formSortModel);
		}
		
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFormSortServiceInterface#update(com.tianee.oa.core.workflow.formmanage.bean.TeeFormSort)
	 */
	@Override
	public void update(TeeFormSort formSort){
		formSortDao.update(formSort);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFormSortServiceInterface#delete(com.tianee.oa.core.workflow.formmanage.bean.TeeFormSort)
	 */
	@Override
	public void delete(TeeFormSort formSort){
		delete(formSort.getSid());
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFormSortServiceInterface#delete(int)
	 */
	@Override
	public void delete(int formSordId){
		//把该分类的表单都移至默认分类下
		simpleDaoSupport.executeUpdate("update TeeForm set formSort=null where formSort.sid="+formSordId, null);
		//最后删除该流程分类
		formSortDao.delete(formSordId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFormSortServiceInterface#doSortOrder(int, int)
	 */
	@Override
	public void doSortOrder(int type,int formSortId){
		TeeFormSort tmpFormSort = null;
		TeeFormSort currFormSort = formSortDao.get(formSortId);
		int tmpOrder = 0;
		if(type==1){
			tmpFormSort = formSortDao.getPrevFormSort(formSortId);
		}else if(type==2){
			tmpFormSort = formSortDao.getNextFormSort(formSortId);
		}
		
		if(tmpFormSort!=null){
			tmpOrder = tmpFormSort.getOrderNo();
			tmpFormSort.setOrderNo(currFormSort.getOrderNo());
			currFormSort.setOrderNo(tmpOrder);
			update(currFormSort);
			update(tmpFormSort);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFormSortServiceInterface#getMaxOrderNo()
	 */
	@Override
	public int getMaxOrderNo(){
		return formSortDao.getMaxOrderNo();
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFormSortServiceInterface#list()
	 */
	@Override
	public List<TeeFormSort> list(){
		return formSortDao.list();
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFormSortServiceInterface#datagrid(com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm){
		TeeEasyuiDataGridJson easyuiDataGridJson = new TeeEasyuiDataGridJson();
		
		List<TeeFormSortModel> formSortModelList = new ArrayList<TeeFormSortModel>();
		List<TeeFormSort> formSortList = formSortDao.list((dm.getPage()-1)*dm.getRows(), dm.getRows());
		
		for(TeeFormSort formSort:formSortList){
			TeeFormSortModel m = new TeeFormSortModel();
			BeanUtils.copyProperties(formSort, m);
			formSortModelList.add(m);
		}
		
		easyuiDataGridJson.setTotal(formSortDao.listCount());
		easyuiDataGridJson.setRows(formSortModelList);
		
		return easyuiDataGridJson;
	}
	

	
}
