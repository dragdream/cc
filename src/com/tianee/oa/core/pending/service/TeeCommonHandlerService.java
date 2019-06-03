package com.tianee.oa.core.pending.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.pending.bean.TeeCommonHandler;
import com.tianee.oa.core.pending.dao.TeeCommonHandlerDao;
import com.tianee.oa.core.pending.model.TeeCommonHandlerModel;
import com.tianee.webframe.bean.TeeBaseEntity;
import com.tianee.webframe.service.TeeBaseService;
@Service
public class TeeCommonHandlerService extends TeeBaseService{
	@Autowired
	private TeeCommonHandlerDao cHandlerDao;
	//保存
	public void save(TeeCommonHandler chandler){
		cHandlerDao.save(chandler);
	}
	//更新
	public void update(TeeCommonHandler chandler){
		cHandlerDao.update(chandler);
	}
	//通过id查
	public TeeCommonHandler  get(String uuid){
		return cHandlerDao.get(uuid);
	}
	
	//删除
	public void delete(String uuid){
		cHandlerDao.delete(uuid);
	}
	
	/*
	 * 分页查询
	 */
	public List<TeeCommonHandler> lists(int firstResult,int rows,TeeCommonHandlerModel querymodel){
		
		return cHandlerDao.list(firstResult, rows, querymodel);
		
	}
	/*
	 * 返回总数
	 */
	public long  getTotal(TeeCommonHandlerModel querymodel){
		return cHandlerDao.getTotal(querymodel);
	}
}
