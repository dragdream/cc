package com.beidasoft.xzzf.queries.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.queries.bean.LawBase;
import com.beidasoft.xzzf.queries.bean.LawBaseDetail;
import com.beidasoft.xzzf.queries.dao.LawBaseDao;
import com.beidasoft.xzzf.queries.dao.LawBaseDetailDao;
import com.beidasoft.xzzf.queries.model.LawBaseModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class LawBaseService extends TeeBaseService {

	@Autowired
	private LawBaseDao LawBaseDao;
	@Autowired
	private LawBaseDetailDao DetailDao;
	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;

	/**
	 * 通过主键查询用户信息
	 * @param id
	 * @return
	 */
	public LawBase getById(String id) {
		return LawBaseDao.get(id);
	}

//	/**
//	 * 分页进行查询
//	 * @param firstResult
//	 * @param rows
//	 * @return
//	 */
//	public List<LawBaseDetail> listByPage(int firstResult, int rows,
//			LawBaseModel queryModel) {
//		return LawBaseDao.listByPage(firstResult, rows, queryModel);
//	}

	/**
	 * 返回总记录数
	 * @return
	 */
	public long getTotal(LawBaseModel queryModel) {
		String sql = "select count(*) from FX_BASE_LAW p,FX_BASE_LAW_DETAIL q where p.ID = q.LAW_ID ";
		if (!TeeUtility.isNullorEmpty(queryModel.getLawNum())) {
			sql += " and LAW_NUM = '" + queryModel.getLawNum() + "'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getName())) {
			sql += " and NAME like '%" + queryModel.getName() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getSubmitlawLevel())) {
			sql += " and SUBMITLAW_LEVEL = '" + queryModel.getSubmitlawLevel() + "'";
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getWord())) {
			sql += " and WORD like '%" + queryModel.getWord() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getLawDetailCode())) {
			sql += " and LAW_DETAIL_CODE = '" + queryModel.getLawDetailCode() + "'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getLaw_strip())) {
			sql += " and LAW_STRIP = '" + queryModel.getLaw_strip() + "'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getLawDetailCode())) {
			sql += " and FUND = '" + queryModel.getFund() + "'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getItem())) {
			sql += " and ITEM = '" + queryModel.getItem() + "'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getContent())) {
			sql += " and CONTENT like '%" + queryModel.getContent() + "%'";

		}
		
		long total =simpleDaoSupport.countSQLByList(sql, null);
		return total;
	}
	
	/**
	 * 获取多条记录带分页
	 * @param firstResult
	 * @param rows
	 * @param queryModel
	 * @return
	 */
	public List<Map> test(int firstResult, int rows, LawBaseModel queryModel){
		String sql = "select p.ID,p.NAME,p.LAW_NUM,p.SUBMITLAW_LEVEL,p.WORD,q.LAW_NAME,q.LAW_STRIP,q.FUND,q.ITEM,q.CONTENT ,q.LAW_DETAIL_CODE from FX_BASE_LAW p,FX_BASE_LAW_DETAIL q where p.ID = q.LAW_ID ";
		if (!TeeUtility.isNullorEmpty(queryModel.getLawNum())) {
			sql += " and LAW_NUM = '" + queryModel.getLawNum() + "'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getName())) {
			sql += " and NAME like '%" + queryModel.getName() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getSubmitlawLevel())) {
			sql += " and SUBMITLAW_LEVEL = '" + queryModel.getSubmitlawLevel() + "'";
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getWord())) {
			sql += " and WORD like '%" + queryModel.getWord() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getLawDetailCode())) {
			sql += " and LAW_DETAIL_CODE = '" + queryModel.getLawDetailCode() + "'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getLaw_strip())) {
			sql += " and LAW_STRIP = '" + queryModel.getLaw_strip() + "'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getLawDetailCode())) {
			sql += " and FUND = '" + queryModel.getFund() + "'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getItem())) {
			sql += " and ITEM = '" + queryModel.getItem() + "'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getContent())) {
			sql += " and CONTENT like '%" + queryModel.getContent() + "%'";

		}
		sql+=" order by LAW_STRIP,LAW_NUM,FUND,ITEM";
		List<Map> dataList0 =simpleDaoSupport.executeNativeQuery(sql, null, firstResult, rows);
		return dataList0;
			}
	
}
