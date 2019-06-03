package com.beidasoft.xzzf.discretionaryPower.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beidasoft.xzzf.discretionaryPower.bean.discretionaryPower;
import com.beidasoft.xzzf.discretionaryPower.dao.discretionaryPowerDao;

@Service
@Transactional
public class discretionaryService {

	@Autowired
	private discretionaryPowerDao powerDao;
	/**
	 * 根据分页进行查询总数
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<discretionaryPower> listByPage(int firstResult,int rows){
	    return	powerDao.listByPage(firstResult, rows);
	}
	/**
	 * 返回总记录数
	 * @return
	 */
	public long getTotal(){
	    return	powerDao.getTotal();
	}
	
	//模糊查询 start
	/**
	 * 模糊查询分页数
	 * @param firstResult
	 * @param rows
	 * @param power
	 * @return
	 */
	public List<discretionaryPower> listByPage(int firstResult,int rows,discretionaryPower power){
	    return	powerDao.listByPage(firstResult, rows, power);
	}
	
	
	/**
	 * 模糊查询总数
	 * @param queryModel
	 * @return
	 */
	public long getTotal(discretionaryPower power){
	    return	powerDao.getTotal(power);
	}
	//模糊查询 end
	
	
	/**
	 *通过主键查询用户信息
	 * @param userInfo
	 */
	public discretionaryPower getById(int id){
		return powerDao.get(id);
	}

	/**
	 * 修改自由裁量权信息
	 * @param userInfo
	 */
	public void update(discretionaryPower userInfo){
		powerDao.update(userInfo);
	}
	/**
	 * 根据id删除自由裁量权信息
	 * @param id
	 */
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		powerDao.delete(id);
	}

}
