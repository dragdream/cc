package com.beidasoft.xzzf.evi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.evi.bean.ElvEvidenceChild;
import com.beidasoft.xzzf.evi.elvdao.ElvEvidenceChildDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
@Service
public class ElvEvidenceChildService extends TeeBaseService{
	@Autowired
	private ElvEvidenceChildDao childDao;
	public TeeJson save(ElvEvidenceChild child){
		TeeJson json = new TeeJson();
		childDao.save(child);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据主表id查询列表
	 * @param eleBaseId
	 * @return
	 */
	public List<ElvEvidenceChild> getListByEleBaseId(String eleBaseId){
		List<ElvEvidenceChild> list = childDao.getListByEleBaseId(eleBaseId);
		return list;
	}
	/**
	 * 修改
	 * @param child
	 */
	public void update(ElvEvidenceChild child){
		childDao.update(child);
	}
}
