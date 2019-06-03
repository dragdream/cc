package com.beidasoft.zfjd.law.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.law.bean.TblLawInfo;
import com.beidasoft.zfjd.law.dao.TblLawDao;
import com.beidasoft.zfjd.law.model.TblLawModel;
import com.tianee.webframe.service.TeeBaseService;


@Service
public class TblLawService extends TeeBaseService{

	
	@Autowired
	private TblLawDao lawDao;
	
	/*
	 * 保存用户信息
	 */
	public void save(TblLawInfo law){
		lawDao.save(law);
	}
	/*
	 * 更新用户信息
	 */
	public void update(TblLawInfo law){
		lawDao.update(law);
	}
	
	/*
	 * 更新用户信息
	 */
	public void update(Map updateItem,String lawId){
		lawDao.update(updateItem, lawId);
	}
	/*
	 * 通过对象删除用户信息
	 */
	public void deleteByObject(TblLawInfo law){
		lawDao.deleteByObj(law);
	}
	/*
	 * 通过主键删除用户信息
	 */
	public void deleteById(String id){
		lawDao.delete(id);
	}
	/*
	 * 通过主键查询用户信息
	 */
	public TblLawInfo getById(String id){
		return lawDao.get(id);
	}
	/*
	 * 返回所有用户信息
	 */
	public List<TblLawInfo> getAllUsers(){
		return lawDao.findUsers();
	}
	/*
	 * 根据分页进行查询
	 */
	public List<TblLawInfo> listByPage(int firstResult,int rows){
		return lawDao.listByPage( firstResult, rows, null);
	  }
	  
   public List<TblLawInfo> listByPage(int firstResult,int rows,TblLawModel queryModel){
		 return lawDao.listByPage(firstResult, rows, queryModel);
		  
	  }
   
	  public long getTotal(){
		  return lawDao.getTotal();
	  }
   
	  public long getTotal(TblLawModel queryModel){
		  return lawDao.getTotal(queryModel);
	  }
	
	 
	
}
