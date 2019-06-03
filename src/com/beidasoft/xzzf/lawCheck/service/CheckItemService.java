package com.beidasoft.xzzf.lawCheck.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.lawCheck.bean.LawCheckItem;
import com.beidasoft.xzzf.lawCheck.dao.LawCheckItemDao;
import com.beidasoft.xzzf.lawCheck.model.CheckItemModel;
import com.tianee.webframe.service.TeeBaseService;
@Service
public class CheckItemService extends TeeBaseService{
	@Autowired //自动注入
	private LawCheckItemDao itemkDao;
	
	/*
     * 保存用户信息
     * */         	    
	public void  save(LawCheckItem item){
		itemkDao.save(item);
		}
	/*
	 * 更新用户信息
	 * */	 
	public void  update(LawCheckItem item){
		itemkDao.update(item);
	}
	
	/*
	 * 通过主键查询用户信息
	 * */	 
	public LawCheckItem getById(String id){
		return itemkDao.get(id);

	}
	
	public long getTotal(CheckItemModel queryModel) {
		return itemkDao.getTotal(queryModel);
	}
	
	public List<LawCheckItem> listByPage(int firstResult, int rows,
			CheckItemModel queryModel) {
		return itemkDao.listByPage(firstResult,rows,queryModel);
	}
	public void del(String id) {
		itemkDao.delete(id);
		
	}
	public List<LawCheckItem> getListById(String sid) {
		return itemkDao.getListById(sid);
	}
}
