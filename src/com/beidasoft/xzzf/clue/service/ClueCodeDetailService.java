package com.beidasoft.xzzf.clue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.clue.bean.ClueCodeDetail;
import com.beidasoft.xzzf.clue.dao.ClueCodeDetailDao;
import com.tianee.webframe.service.TeeBaseService;


@Service
public class ClueCodeDetailService extends TeeBaseService{
	
	@Autowired
	private ClueCodeDetailDao clueCodeDetailDao;
	
	/**
	 * 获取举报形式List
	 * @return
	 */
	public List<ClueCodeDetail> findFormList(){
		return clueCodeDetailDao.findFormList();
	}
	
	/**
	 * 获取举报来源List
	 * @return
	 */
	public List<ClueCodeDetail> findSourceList(String reportForm){
		return clueCodeDetailDao.findSourceList(reportForm);
	}
	
	/**
	 * 获取举报类型List
	 * @return
	 */
	public List<ClueCodeDetail> findTypeList(){
		return clueCodeDetailDao.findTypeList();
	}

	/**
	 * 获取办理单位List
	 * @return
	 */
	public List<ClueCodeDetail> findDepartList() {
		return clueCodeDetailDao.findDepartList();
	}

}
