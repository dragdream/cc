package com.beidasoft.xzzf.evi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.evi.bean.CaseBase;
import com.beidasoft.xzzf.evi.elvdao.BaseCaseDao;
import com.beidasoft.xzzf.evi.model.CaseBaseModel;
import com.tianee.webframe.service.TeeBaseService;
@Service
public class BaseCaseService extends TeeBaseService{
	@Autowired
	private BaseCaseDao BaseDao;
	/**
	 * 通过主键查询用户信息
	 * @param baseId
	 * @return
	 */
	public  CaseBase getById(String baseId){
		return BaseDao.get(baseId);
	}
	/**
	 * 分页进行查询
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<CaseBase> listByPage(int firstResult,int rows, CaseBaseModel queryModel) {
		return BaseDao.listByPage(firstResult,rows,queryModel);
	}
	/**
	 * 返回总记录数
	 * @return
	 */
	public long getTotal(CaseBaseModel queryModel){
		return BaseDao.getTotal(queryModel);
	}

	public long getTotal(String baseId){
	    return	BaseDao.getTotal(baseId);
	}
	

}

