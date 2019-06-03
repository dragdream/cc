package com.beidasoft.xzzf.queries.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.queries.bean.LawBase;
import com.beidasoft.xzzf.queries.model.LawBaseModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class LawBaseDao extends TeeBaseDao<LawBase> {
	
//	/**
//	 * 根据分页查找用户信息
//	 * 
//	 * @param firstResult
//	 * @param rows
//	 * @return
//	 */
//	public List<LawBase> listByPage(int firstResult, int rows,
//			LawBaseModel queryModel) {
//		String hql = "from LawBase where 1=1 ";
//
//		if (!TeeUtility.isNullorEmpty(queryModel.getLawNum())) {
//			hql += " and lawNum = '" + queryModel.getLawNum() + "'";
//
//		}
//		if (!TeeUtility.isNullorEmpty(queryModel.getName())) {
//			hql += " and name like '%" + queryModel.getName() + "%'";
//
//		}
//		if (!TeeUtility.isNullorEmpty(queryModel.getSubmitlawLevel())) {
//			hql += " and submitlawLevel = '" + queryModel.getSubmitlawLevel() + "'";
//		}
//		if (!TeeUtility.isNullorEmpty(queryModel.getWord())) {
//			hql += " and word like '%" + queryModel.getWord() + "%'";
//
//		}
//		if (!TeeUtility.isNullorEmpty(queryModel.getLawDetailCode())) {
//			hql += " and lawDetailCode = '" + queryModel.getLawDetailCode() + "'";
//
//		}
//		if (!TeeUtility.isNullorEmpty(queryModel.getLaw_strip())) {
//			hql += " and law_strip = '" + queryModel.getLaw_strip() + "'";
//
//		}
//		if (!TeeUtility.isNullorEmpty(queryModel.getLawDetailCode())) {
//			hql += " and fund = '" + queryModel.getFund() + "'";
//
//		}
//		if (!TeeUtility.isNullorEmpty(queryModel.getItem())) {
//			hql += " and item = '" + queryModel.getItem() + "'";
//
//		}
//		if (!TeeUtility.isNullorEmpty(queryModel.getContent())) {
//			hql += " and content like '%" + queryModel.getContent() + "%'";
//
//		}
//		return super.pageFind(hql, firstResult, rows, null);
//	}
//
//
//
//	/**
//	 * 重载
//	 * @return
//	 */
//	public long getTotal(LawBaseModel queryModel) {
//		String hql = "select count(id) from LawBase where 1=1";
//
//		if (!TeeUtility.isNullorEmpty(queryModel.getLawNum())) {
//			System.out.println(queryModel.getLawNum());
//			hql += " and lawNum = '" + queryModel.getLawNum() + "'";
//
//		}
//		if (!TeeUtility.isNullorEmpty(queryModel.getName())) {
//			hql += " and name like '%" + queryModel.getName() + "%'";
//
//		}
//		if (!TeeUtility.isNullorEmpty(queryModel.getSubmitlawLevel())) {
//			hql += " and submitlawLevel = '" + queryModel.getSubmitlawLevel() + "'";
//		}
//		if (!TeeUtility.isNullorEmpty(queryModel.getWord())) {
//			hql += " and word like '%" + queryModel.getWord() + "%'";
//
//		}
//		if (!TeeUtility.isNullorEmpty(queryModel.getLawDetailCode())) {
//			hql += " and lawDetailCode = '" + queryModel.getLawDetailCode() + "'";
//
//		}
//		if (!TeeUtility.isNullorEmpty(queryModel.getLaw_strip())) {
//			hql += " and law_strip = '" + queryModel.getLaw_strip() + "'";
//
//		}
//		if (!TeeUtility.isNullorEmpty(queryModel.getLawDetailCode())) {
//			hql += " and fund = '" + queryModel.getFund() + "'";
//
//		}
//		if (!TeeUtility.isNullorEmpty(queryModel.getItem())) {
//			hql += " and item = '" + queryModel.getItem() + "'";
//
//		}
//		if (!TeeUtility.isNullorEmpty(queryModel.getContent())) {
//			hql += " and content like '%" + queryModel.getContent() + "%'";
//
//		}
//		System.out.println(hql);
//		
//		return super.count(hql, null);
//	}
}