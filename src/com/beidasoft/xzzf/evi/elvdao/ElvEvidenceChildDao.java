package com.beidasoft.xzzf.evi.elvdao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.evi.bean.ElvEvidenceChild;
import com.tianee.webframe.dao.TeeBaseDao;
@Repository
public class ElvEvidenceChildDao extends TeeBaseDao<ElvEvidenceChild>{
	/**
	 * 根据主表id查询列表
	 * @param eleBaseId
	 * @return
	 */
	public List<ElvEvidenceChild> getListByEleBaseId(String eleBaseId){
		List<ElvEvidenceChild> list = super.find("from ElvEvidenceChild where evidenceBaseId = '"+eleBaseId+"'", null);
		return list;
	}
}
