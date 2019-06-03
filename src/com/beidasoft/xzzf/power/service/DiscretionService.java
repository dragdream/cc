package com.beidasoft.xzzf.power.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.power.bean.BaseDiscretion;
import com.beidasoft.xzzf.power.dao.DiscretionDao;
import com.beidasoft.xzzf.power.model.DiscretionModel;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class DiscretionService extends TeeBaseService {

	@Autowired
	// 自动注入
	private DiscretionDao cretionDao;

	/*
	 * 保存用户信息
	 */
	public void save(BaseDiscretion discretion) {
		cretionDao.save(discretion);
	}

	/*
	 * 更新用户信息
	 */
	public void update(BaseDiscretion discretion) {
		cretionDao.update(discretion);

	}

	/*
	 * 通过对象删除用户信息
	 */
	public void deleteByObjact(BaseDiscretion discretion) {
		cretionDao.deleteByObj(discretion);

	}

	/*
	 * 通过主键删除用户信息
	 */
	public void deleteById(String id) {
		cretionDao.delete(id);

	}

	/*
	 * 通过主键查询用户信息
	 */
	public BaseDiscretion getById(String id) {
		return cretionDao.get(id);

	}


	/**
	 * 分页进行查询
	 * 
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<BaseDiscretion> listByPage(int firstResult, int rows,
			DiscretionModel queryModel) {
		return cretionDao.listByPage(firstResult, rows, queryModel);

	}

	/**
	 * 返回总记录数
	 * 
	 * @return
	 */
	public long getTotal(DiscretionModel queryModel) {
		return cretionDao.getTotal(queryModel);
	}
}
