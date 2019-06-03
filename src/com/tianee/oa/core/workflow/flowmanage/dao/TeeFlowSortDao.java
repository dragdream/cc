package com.tianee.oa.core.workflow.flowmanage.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class TeeFlowSortDao extends TeeBaseDao<TeeFlowSort>{
	
	/**
	 * 查询流程分类集合
	 * @return
	 */
	public List<TeeFlowSort> list(){
		return this.find("from TeeFlowSort flowSort order by flowSort.orderNo asc", null);
	}
	
	/**
	 * 查询流程分类集合
	 * @param firstResult
	 * @param pageSize
	 * @return
	 */
	public List<TeeFlowSort> list(int firstResult,int pageSize){
		return this.pageFind("from TeeFlowSort flowSort order by flowSort.orderNo asc", firstResult, pageSize, null);
	}
	
	/**
	 * 查询流程分类集合
	 * @param firstResult
	 * @param pageSize
	 * @return
	 */
	public long listCount(){
		return this.count("select count(*) from TeeFlowSort flowSort ", null);
	}
	
	/**
	 * 获取最大分类排序号
	 * @param flowSortId
	 * @return
	 */
	public int getMaxOrderNo(){
		Session session = this.getSession();
		Query query = session.createQuery("select max(flowSort.orderNo) from TeeFlowSort flowSort");
		Integer max = (Integer) query.uniqueResult();
		return max==null?0:max;
	}
	
	/**
	 * 获取排序号小于当前分类的流程分类集合
	 * @param flowSortId
	 * @return
	 */
	public List<TeeFlowSort> getBeforeFlowSort(int flowSortId){
		TeeFlowSort flowSort = super.get(flowSortId);
		List<TeeFlowSort> prevFlowSortList = super.find("from TeeFlowSort flowSort where flowSort.orderNo<? order by flowSort.orderNo asc"
				, new Object[]{flowSort.getOrderNo()});
		return prevFlowSortList;
	}
	
	/**
	 * 获取排序号大于当前分类的流程分类集合
	 * @param flowSortId
	 * @return
	 */
	public List<TeeFlowSort> getAfterFlowSort(int flowSortId){
		TeeFlowSort flowSort = super.get(flowSortId);
		List<TeeFlowSort> prevFlowSortList = super.find("from TeeFlowSort flowSort where flowSort.orderNo>? order by flowSort.orderNo asc"
				, new Object[]{flowSort.getOrderNo()});
		return prevFlowSortList;
	}
	
	/**
	 * 获取当前分类的前一个分类实体
	 * @param flowSortId
	 * @return
	 */
	public TeeFlowSort getPrevFlowSort(int flowSortId){
		TeeFlowSort flowSort = super.get(flowSortId);
		List<TeeFlowSort> prevFlowSortList = super.pageFind("from TeeFlowSort flowSort where flowSort.orderNo<? order by flowSort.orderNo desc",0,1,
				new Object[]{flowSort.getOrderNo()});
		return prevFlowSortList.size()==0?null:prevFlowSortList.get(0);
	}
	
	/**
	 * 获取当前分类的后一个分类实体
	 * @param flowSortId
	 * @return
	 */
	public TeeFlowSort getNextFlowSort(int flowSortId){
		TeeFlowSort flowSort = super.get(flowSortId);
		List<TeeFlowSort> nextFlowSortList = super.pageFind("from TeeFlowSort flowSort where flowSort.orderNo>? order by flowSort.orderNo asc",0,1,
				new Object[]{flowSort.getOrderNo()});
		return nextFlowSortList.size()==0?null:nextFlowSortList.get(0);
	}
}
