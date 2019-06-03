package com.beidasoft.zfjd.law.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.law.bean.TblLawDetail;
import com.beidasoft.zfjd.law.dao.TblLawDetailDao;
import com.beidasoft.zfjd.law.model.TblLawDetailModel;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 职权基础信息表SERVICE类
 */
@Service
public class TblLawDetailService extends TeeBaseService {

	@Autowired
	private TblLawDetailDao detailDao;
	
	/*
	 * 保存用户信息  6号楼
	 */
	public void save(TblLawDetail detail){
		detailDao.save(detail);
	}
	/*
	 * 更新用户信息
	 */
	public void update(TblLawDetail detail){
		detailDao.update(detail);
	}
	/*
	 * 通过对象删除用户信息
	 */
	public void deleteByObject(TblLawDetail detail){
		detailDao.deleteByObj(detail);
	}
	/*
	 * 通过主键删除用户信息
	 */
	public void deleteById(String id){
		detailDao.delete(id);
	}
	/*
	 * 通过主键查询用户信息
	 */
	public TblLawDetail getById(String id){
		return detailDao.get(id);
	}
	/*
	 * 返回所有用户信息
	 */
	public List<TblLawDetail> getAllUsers(){
		return detailDao.findUsers();
	}
	/*
	 * 根据分页进行查询
	 */
	public List<TblLawDetail> listByPage(int firstResult,int rows){
		return detailDao.listByPage( firstResult, rows, null);
	  }
	  
   public List<TblLawDetail> listByPage(int firstResult,int rows,TblLawDetailModel queryModel){
		 return detailDao.listByPage(firstResult, rows, queryModel);
		  
	  }
   
	  public long getTotal(){
		  return detailDao.getTotal();
	  }
   
	  public long getTotal(TblLawDetailModel queryModel){
		  return detailDao.getTotal(queryModel);
	  }
	  
	  public List<TblLawDetail> getLawDetailByIds(TblLawDetailModel lawDetailModel) {
	      return detailDao.getLawDetailByIds(lawDetailModel);
	  }

	  
}
