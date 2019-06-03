package com.tianee.oa.core.pending.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.pending.bean.TeeYiBanEventView;
import com.tianee.oa.core.pending.dao.TeeYiBanEventViewDao;
import com.tianee.oa.core.pending.model.TeeDaiBanEventModel;

@Service 
public class TeeYiBanEventViewService {
	@Autowired
	private TeeYiBanEventViewDao  ybeViewDao;
	
	
	/*
	 * 分页查询
	 */
	public List<TeeYiBanEventView> lists(int firstResult,int rows,TeeDaiBanEventModel querymodel,TeePerson loginUser){
		
		return ybeViewDao.list(firstResult, rows, querymodel,loginUser);
		
	}
	/*
	 * 返回总数
	 */
	public long  getTotal(TeeDaiBanEventModel querymodel,TeePerson loginUser){
		return ybeViewDao.getTotal(querymodel,loginUser);
	}
}
