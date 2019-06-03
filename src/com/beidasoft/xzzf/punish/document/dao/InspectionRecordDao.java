package com.beidasoft.xzzf.punish.document.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.document.bean.DocInspectionRecord;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class InspectionRecordDao extends TeeBaseDao<DocInspectionRecord>{

	String strStart = " '";
	String strFina = "' ";
	String temp = "' and '";
	
	
	public DocInspectionRecord getByBaseId(String baseId, String lawLinkId) {
		String hql = "from DocInspectionRecord where baseId ="+strStart
				+baseId+strFina +"and lawLinkId ="+strStart+baseId+strFina;
		return null;
	}
	
	/**
	 * 通过baseId查询现场勘验笔录集合
	 * @param baseId
	 * @return
	 */
	public List<DocInspectionRecord> getAllByBaseId(String baseId) {
		String hql = "from DocInspectionRecord where baseId = "+strStart+baseId+strFina;
		return find(hql, null);
	}
}
