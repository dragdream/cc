package com.tianee.oa.core.workflow.formmanage.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.workflow.formmanage.bean.TeeFormSort;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class TeeFormSortDao extends TeeBaseDao<TeeFormSort>{
	
	/**
	 * 查询表单分类集合
	 * @return
	 */
	public List<TeeFormSort> list(){
		return this.find("from TeeFormSort formSort order by formSort.orderNo asc", null);
	}
	
	/**
	 * 查询表单分类集合
	 * @param firstResult
	 * @param pageSize
	 * @return
	 */
	public List<TeeFormSort> list(int firstResult,int pageSize){
		return this.pageFind("from TeeFormSort formSort order by formSort.orderNo asc", firstResult, pageSize, null);
	}
	
	/**
	 * 查询表单分类集合
	 * @param firstResult
	 * @param pageSize
	 * @return
	 */
	public long listCount(){
		return this.count("select count(*) from TeeFormSort formSort ", null);
	}
	
	/**
	 * 获取最大分类排序号
	 * @param flowSortId
	 * @return
	 */
	public int getMaxOrderNo(){
		Session session = this.getSession();
		Query query = session.createQuery("select max(formSort.orderNo) from TeeFormSort formSort");
		Integer max = (Integer) query.uniqueResult();
		return max==null?0:max;
	}
	
	/**
	 * 获取排序号小于当前分类的表单分类集合
	 * @param flowSortId
	 * @return
	 */
	public List<TeeFormSort> getBeforeFormSort(int formSortId){
		TeeFormSort formSort = super.get(formSortId);
		List<TeeFormSort> prevFormSortList = super.find("from TeeFormSort formSort where formSort.orderNo<? order by formSort.orderNo asc"
				, new Object[]{formSort.getOrderNo()});
		return prevFormSortList;
	}
	
	/**
	 * 获取排序号大于当前分类的流程分类集合
	 * @param flowSortId
	 * @return
	 */
	public List<TeeFormSort> getAfterFormSort(int formSortId){
		TeeFormSort formSort = super.get(formSortId);
		List<TeeFormSort> prevFormSortList = super.find("from TeeFormSort formSort where formSort.orderNo>? order by formSort.orderNo asc"
				, new Object[]{formSort.getOrderNo()});
		return prevFormSortList;
	}
	
	/**
	 * 获取当前分类的前一个分类实体
	 * @param flowSortId
	 * @return
	 */
	public TeeFormSort getPrevFormSort(int formSortId){
		TeeFormSort formSort = super.get(formSortId);
		List<TeeFormSort> prevFormSortList = super.pageFind("from TeeFormSort formSort where formSort.orderNo<? order by formSort.orderNo desc",0,1,
				new Object[]{formSort.getOrderNo()});
		return prevFormSortList.size()==0?null:prevFormSortList.get(0);
	}
	
	/**
	 * 获取当前分类的后一个分类实体
	 * @param flowSortId
	 * @return
	 */
	public TeeFormSort getNextFormSort(int formSortId){
		TeeFormSort formSort = super.get(formSortId);
		List<TeeFormSort> nextFormSortList = super.pageFind("from TeeFormSort formSort where formSort.orderNo>? order by formSort.orderNo asc",0,1,
				new Object[]{formSort.getOrderNo()});
		return nextFormSortList.size()==0?null:nextFormSortList.get(0);
	}
}
