package com.beidasoft.xzzf.transferred.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.lawCheck.bean.BaseLawCheck;
import com.beidasoft.xzzf.lawCheck.bean.LawCheckItem;
import com.beidasoft.xzzf.lawCheck.dao.LawCheckDao;
import com.beidasoft.xzzf.lawCheck.dao.LawCheckItemDao;
import com.beidasoft.xzzf.lawCheck.model.LawCheckModel;
import com.beidasoft.xzzf.transferred.bean.DocManagements;
import com.beidasoft.xzzf.transferred.bean.DocTransferred;
import com.beidasoft.xzzf.transferred.dao.ManagementsDao;
import com.beidasoft.xzzf.transferred.dao.TransferredDao;
import com.beidasoft.xzzf.transferred.model.ManagementsModel;
import com.beidasoft.xzzf.transferred.model.TransferredModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TransferredService extends TeeBaseService{
	
	@Autowired //自动注入
	private TransferredDao transferredDao;
	@Autowired //自动注入
	private ManagementsDao managementsDao;
	/*
     * 保存
     * */
	public void save(DocTransferred docTransferred) {
		// TODO Auto-generated method stub
		transferredDao.saveOrUpdate(docTransferred);
	}
	
	/*
	 * 更新 
	 * */
	public void update(DocTransferred docTransferred) {
		// TODO Auto-generated method stub
		transferredDao.saveOrUpdate(docTransferred);
	}

	public DocTransferred getById(String id) {
		// TODO Auto-generated method stub
		return transferredDao.get(id);
	}

	public void deleteById(String id) {
		// TODO Auto-generated method stub
		transferredDao.delete(id);
	}

	public List<LawCheckItem> getBylawListById(String id,
			TeeDataGridModel dataGridModel) {
		// TODO Auto-generated method stub
		return managementsDao.getBylawList(id, dataGridModel);
	}

	public long getTotal(TransferredModel transferredModel) {
		// TODO Auto-generated method stub
		return transferredDao.getTotal(transferredModel);
	}

	public List<DocTransferred> listByPage(int firstResult, int rows,TransferredModel transferredModel) {
		return transferredDao.listByPage(firstResult, rows, transferredModel);
	}

	public long getTotal(String id) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
