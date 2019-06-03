package com.beidasoft.xzzf.punish.document.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.document.bean.DocEvidencedescriptionDetail;
import com.beidasoft.xzzf.punish.document.dao.EvidencedescriptionDetailDao;

@Service
public class EvidencedescriptionDetailService {
	
	@Autowired
	private EvidencedescriptionDetailDao detailDao;
	
	//保存
	public void save(DocEvidencedescriptionDetail detail) {
		detailDao.save(detail);
	}
	
	//根据mainId 删除所有
	public void delete(String mainId) {
		detailDao.deleteByMainId(mainId);
	}
	
	//根据mainId 查所有
	public List<DocEvidencedescriptionDetail> getDetailListByMainId(String mainId) {
		return detailDao.getListByMainId(mainId);
	}
}
