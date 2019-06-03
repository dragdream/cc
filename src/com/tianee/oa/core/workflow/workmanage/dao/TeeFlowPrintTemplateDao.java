package com.tianee.oa.core.workflow.workmanage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowPrintTemplate;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository("printTemplateDao")
public class TeeFlowPrintTemplateDao extends TeeBaseDao<TeeFlowPrintTemplate> {
	/**
	 * 新建
	 * 
	 * @param TeeFlowPriv
	 */
	public void addTpl(TeeFlowPrintTemplate tpl) {
		save(tpl);
	}

	/**
	 * 更新
	 * 
	 * @param TeePerson
	 */
	public void updateTpl(TeeFlowPrintTemplate tpl) {
		update(tpl);
	}

	/**
	 * 查询 byid
	 * 
	 * @param sid  Id
	 */
	public TeeFlowPrintTemplate getById(int sid) {
		TeeFlowPrintTemplate tpl = (TeeFlowPrintTemplate) get(sid);
		return tpl;
	}
	
	
	/**
	 * 查询 byid
	 * 
	 * @param sid  Id
	 */
	public void delById(int sid) {
		 delete(sid);
	}
	
	/**
	 * 根据流程Id 获取所有打印模版
	 * 
	 * @param flowTypeId 工作流程Id
	 */
	public List<TeeFlowPrintTemplate> selectModulByFlowType(int flowTypeId) {
		String hql = "from TeeFlowPrintTemplate where flowType.sid = " + flowTypeId;
		return executeQuery(hql, null);
	}
	

	/**
	 *  获取可打印模版  自由流都可以打印      固定流根据步骤权限设置
	 * @author syl
	 * @date 2013-11-24
	 * @param flowTypeId 流程Id
	 * @param flowType 流程类型 1 -固定流  2 - 自由流
	 * @param prcsId 流程步骤Id
	 * @return
	 */
	public List<TeeFlowPrintTemplate> selectModulByFlowTypeAndPrcsId(int flowTypeId , int flowType, int prcsId) {
		String hql = "from TeeFlowPrintTemplate tpl where tpl.flowType.sid = " + flowTypeId;
		if(flowType == 1){
			hql = "from TeeFlowPrintTemplate tpl where tpl.flowType.sid="+flowTypeId+" and exists (select 1 from tpl.flowPrcs prcs where prcs.sid="+prcsId+")";
		}
		return executeQuery(hql, null);
	}
	
}

	