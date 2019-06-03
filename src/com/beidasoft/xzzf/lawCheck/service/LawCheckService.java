package com.beidasoft.xzzf.lawCheck.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.lawCheck.bean.BaseLawCheck;
import com.beidasoft.xzzf.lawCheck.dao.LawCheckDao;
import com.beidasoft.xzzf.lawCheck.dao.LawCheckItemDao;
import com.beidasoft.xzzf.lawCheck.model.LawCheckModel;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class LawCheckService extends TeeBaseService{
	
	@Autowired //自动注入
	private LawCheckDao checkDao;
	@Autowired //自动注入
	private LawCheckItemDao itemkDao;
	
	/*
     * 保存用户信息
     * */         	    
	public void  save(BaseLawCheck check){
		checkDao.save(check);
		}
	/*
	 * 更新用户信息
	 * */	 
	public void  update(BaseLawCheck check){
		checkDao.update(check);

	}
	/*
	 * 通过对象删除用户信息
	 * */	 
	public void  deleteByObjact(BaseLawCheck check){
		checkDao.deleteByObj(check);

	}
	/*
	 * 通过主键删除用户信息
	 * */	 
	public void  deleteById(String id){
		checkDao.delete(id);

	}
	/*
	 * 通过主键查询用户信息
	 * */	 
	public BaseLawCheck getById(String id){
		return checkDao.get(id);

	}
	
	/**
	 * 分页进行查询
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<BaseLawCheck> listByPage(int firstResult,int rows, LawCheckModel queryModel) {
		return checkDao.listByPage(firstResult,rows,queryModel);

	}
	
	/**
	 * 返回总记录数
	 * @return
	 */
	public long getTotal(LawCheckModel queryModel){
		return checkDao.getTotal(queryModel);
	}
//	/**
//	 * 返回总记录数
//	 * @return
//	 */
//	public long getTotal(String id){
//	    return	checkDao.getTotal(id);
//	}
//	
//	public List<LawCheckItem> getBylawListById(String id,
//			TeeDataGridModel dataGridModel) {
//		return itemkDao.getBylawList(id, dataGridModel);
//	}
	
}
