package com.beidasoft.xzzf.informationEntry.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.informationEntry.bean.InforEntryItem;
import com.beidasoft.xzzf.informationEntry.bean.InforEntryStaff;
import com.beidasoft.xzzf.informationEntry.model.InforEntryDecisionModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 子表物品清单DAO类
 */
@Repository
public class InforEntryItemDao extends TeeBaseDao<InforEntryItem> {
	/**
	 * 根据caseId分页查询
	 * @param caseId
	 * @return
	 */
	public List<InforEntryItem> getbyCaseId(String caseId,TeeDataGridModel dataGridModel){
		String hql = "from InforEntryItem where caseId = '"+caseId+"'";
		List<InforEntryItem> inforEntryItem = super.pageFind(hql, dataGridModel.getFirstResult(),dataGridModel.getRows(), null);
		return inforEntryItem;
	}
	
	/**
	 * 返回具备条件记录数
	 * @param InforEntryBaseModel
	 * @return
	 */
	public long getTotal(String caseId) {
		String hql = "select count(id) from InforEntryItem where caseId = '"+caseId+"'";
		return super.count(hql, null);
	}
	
	
	/**
	 * 批量，单个修改状态
	 * @param param	要改的参数
	 * @return
	 */
	public int updateByCaseId(InforEntryDecisionModel decModel) {
		String sql = "update InforEntryItem set itemSymbol = '"+decModel.getItemSymbol()+"' where caseId = '"+decModel.getCaseId()+"'";
		int count = deleteOrUpdateByQuery(sql, null);
		return count;
	}
	
}
