package com.beidasoft.xzzf.task.caseOrder.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.task.caseOrder.bean.CaseOrder;
import com.beidasoft.xzzf.task.caseOrder.dao.CaseOrderDao;
import com.beidasoft.xzzf.task.caseOrder.model.CaseOrderModel;



@Service
public class CaseOrderService {
	private CaseOrderDao caseOrderDao = new CaseOrderDao();
	/**
	 * 返回总记录结果
	 * @return
	 */
	public long getTotal(CaseOrderModel queryModel){
		return caseOrderDao.getTotal(queryModel);
	}
	public List<CaseOrder> listByPage(int firstResult,int rows,CaseOrderModel queryModel){
		return caseOrderDao.listByPage(firstResult, rows, queryModel);
	}
	public void save(CaseOrder caseOrder) {
		caseOrderDao.save(caseOrder);
		
	}
	/**
	 * 修改用户信息
	 * @param userInfo
	 */
	public void update(CaseOrder caseOrder){
		caseOrderDao.update(caseOrder);
	}
	/**
	 * 通过id删除用户
	 * @param sid
	 */
	public void deleteById(int id){
		caseOrderDao.delete(id);
	}
	/**
	 * 通过id查找用户
	 * @param sid
	 * @return
	 */
	public CaseOrder getById(int id){
		return caseOrderDao.get(id);
	}

}
