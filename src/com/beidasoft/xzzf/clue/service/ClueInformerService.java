package com.beidasoft.xzzf.clue.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.clue.bean.ClueInformer;
import com.beidasoft.xzzf.clue.dao.ClueInformerDao;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class ClueInformerService extends TeeBaseService{
	
	@Autowired
	private ClueInformerDao clueperinformerDao;
	
	/**
	 * 通过clueId 获取所有举报人信息
	 * 
	 * @param clueId
	 * @return
	 */
	public List<ClueInformer> getByClueId(String clueId){
		return clueperinformerDao.getByClueId(clueId);
	}
	
	/**
	 * 保存 举报人和联系人信息
	 * @param informer
	 */
	public void save(ClueInformer informer) {
		clueperinformerDao.save(informer);
	}
	
	/**
	 * 修改 举报人和联系人信息
	 * @param informer
	 */
	public void update(ClueInformer informer) {
		clueperinformerDao.update(informer);
	}
	
	public void deleteByClueId(String clueId) {
		clueperinformerDao.deleteByClueId(clueId);
	}
	
	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	public ClueInformer getById(String id){
		return clueperinformerDao.get(id);
	}
	
	/**
	 * 获取所有举报人信息
	 * 
	 * @return
	 */
	public List<ClueInformer> getAllClueInformers(){
		return clueperinformerDao.findClueInformers();
	}
	
}
