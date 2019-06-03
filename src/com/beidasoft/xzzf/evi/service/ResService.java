package com.beidasoft.xzzf.evi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.evi.bean.ResEvidence;
import com.beidasoft.xzzf.evi.elvdao.ResDao;
import com.beidasoft.xzzf.evi.model.ResModel;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class ResService extends TeeBaseService{
	@Autowired //自动注入
	private  ResDao resDao;
	
	/**
	 * 保存用户信息
	 * @param resInfo
	 */
	public void  save(ResEvidence resInfo){
		resDao.save(resInfo);
		}
	/**
	 * 更新用户信息
	 * @param resInfo
	 */
	public void  update(ResEvidence resInfo){
		resDao.update(resInfo);

	}
	/**
	 * 通过对象删除用户信息
	 * @param resInfo
	 */
	public void  deleteByObjact(ResEvidence resInfo){
		resDao.deleteByObj(resInfo);
	}
	/**
	 * 通过主键删除用户信息
	 * @param id
	 */
	public void  deleteById(String id){
		resDao.delete(id);
	}
	/**
	 * 通过主键查询用户信息
	 * @param id
	 * @return
	 */
	public ResEvidence getById(String id){
		return resDao.get(id);
	}
	/**
	 * 分页进行查询
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<ResEvidence> listByPage(int firstResult,int rows, ResModel queryModel) {
		return resDao.listByPage(firstResult,rows,queryModel);
	}
	
	/**
	 * 返回总记录数
	 * @return
	 */
	public long getTotal(){
		return resDao.getTotal();
	}
	
	public long getTotal(ResModel queryModel){
		return resDao.getTotal(queryModel);
	}
	
}
