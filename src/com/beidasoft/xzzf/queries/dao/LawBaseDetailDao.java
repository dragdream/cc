package com.beidasoft.xzzf.queries.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.queries.bean.LawBase;
import com.beidasoft.xzzf.queries.bean.LawBaseDetail;
import com.beidasoft.xzzf.queries.model.LawBaseModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;
@Repository
public class LawBaseDetailDao extends TeeBaseDao<LawBaseDetail> {
//	/**
//	 * 根据分页查找用户信息
//	 * @param firstResult
//	 * @param rows
//	 * @return
//	 */
//	public List<LawBaseDetail> listByPage(int firstResult, int rows,
//			LawBaseModel queryModel) {
//		String hql = "from LawBaseDetail where 1=1 ";
//		//String hql = "select count(p.ID) from FX_BASE_LAW p,FX_BASE_LAW_DETAIL q where p.ID = q.LAW_ID ";
//		if (!TeeUtility.isNullorEmpty(queryModel.getContent())) {
//			hql += " and content like '%" + queryModel.getContent() + "%'";
//
//		}
////		System.out.println(hql);
//		return super.pageFind(hql, firstResult, rows, null);
//	}
//
//	/**
//	 * 返回总记录数
//	 * @return
//	 */
//	public long getTotal() {
//		return super.count("select count(id) from LawBaseDetail", null);
//	}
//
//	/**
//	 * 重载
//	 * @return
//	 */
//	public long getTotal(LawBaseModel queryModel) {
//		String hql = "select count(id) from LawBaseDetail where 1=1";
//		//String hql = "select count(p.ID) from FX_BASE_LAW p,FX_BASE_LAW_DETAIL q where p.ID = q.LAW_ID ";
//		if (!TeeUtility.isNullorEmpty(queryModel.getContent())) {
//			hql += " and content like '%" + queryModel.getContent() + "%'";
//
//		}
//		System.out.println(hql);
//		return super.count(hql, null);
//	}
//
//	
//	


}
