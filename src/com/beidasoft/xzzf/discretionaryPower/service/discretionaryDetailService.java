package com.beidasoft.xzzf.discretionaryPower.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beidasoft.xzzf.discretionaryPower.bean.discretionaryDetail;
import com.beidasoft.xzzf.discretionaryPower.dao.discretionaryDetailDao;

@Service
@Transactional
public class discretionaryDetailService {
	@Autowired
	private discretionaryDetailDao detailDao;
	/**
	 * 根据分页进行查询总数
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<discretionaryDetail> listByPage(int firstResult,int rows){
	    return	detailDao.listByPage(firstResult, rows);
	}
	/**
	 * 返回总记录数
	 * @return
	 */
	public long getTotal(){
	    return	detailDao.getTotal();
	}
	
	//模糊查询 start
	/**
	 * 模糊查询分页数
	 * @param firstResult
	 * @param rows
	 * @param power
	 * @return
	 */
	public List<discretionaryDetail> listByPage(int firstResult,int rows,discretionaryDetail detail){
	    return	detailDao.listByPage(firstResult, rows, detail);
	}
	
	
	/**
	 * 模糊查询总数
	 * @param queryModel
	 * @return
	 */
	public long getTotal(discretionaryDetail power){
	    return	detailDao.getTotal(power);
	}
	//模糊查询 end
	
	
	/**
	 *通过主键查询用户信息
	 * @param userInfo
	 */
	public discretionaryDetail getById(int id){
		return detailDao.get(id);
	}

	/**
	 * 修改自由裁量权信息
	 * @param userInfo
	 */
	public void update(discretionaryDetail userInfo){
		detailDao.update(userInfo);
	}
	/**
	 * 根据id删除自由裁量权信息
	 * @param id
	 */
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		detailDao.delete(id);
		
	}

	/**
	 * 存自由裁量权详细信息
	 */
	public void save(discretionaryDetail userInfo) {
		// TODO Auto-generated method stub
		detailDao.save(userInfo);
		
	}

	
}
