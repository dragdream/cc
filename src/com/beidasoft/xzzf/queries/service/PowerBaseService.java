package com.beidasoft.xzzf.queries.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.queries.bean.LawBaseDetail;
import com.beidasoft.xzzf.queries.bean.PowerBase;
import com.beidasoft.xzzf.queries.bean.PowerBaseDetail;
import com.beidasoft.xzzf.queries.bean.PowerBaseGist;
import com.beidasoft.xzzf.queries.dao.PowerBaseDao;
import com.beidasoft.xzzf.queries.dao.PowerDetailDao;
import com.beidasoft.xzzf.queries.dao.PowerGistDao;
import com.beidasoft.xzzf.queries.model.LawBaseModel;
import com.beidasoft.xzzf.queries.model.PowerBaseModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class PowerBaseService extends TeeBaseService {
	@Autowired
	private PowerBaseDao powerDao;
	@Autowired
	private PowerGistDao gistDao;
	@Autowired
	private PowerDetailDao DetailDao;
	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;
	/**
	 * 通过主键查询用户信息
	 * @param id
	 * @return
	 */
	public PowerBase getById(String id) {
		return powerDao.get(id);
	}

	/**
	 * 返回总记录数
	 * @return
	 */
	public long getTotal(PowerBaseModel queryModel) {
		String sql = "select count(*) from FX_BASE_POWER p,FX_BASE_POWER_GIST q where p.ID = q.POWER_ID ";
		if (!TeeUtility.isNullorEmpty(queryModel.getCode())) {
			sql += " and CODE = '" + queryModel.getCode() + "'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getName())) {
			sql += " and NAME like '%" + queryModel.getName() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getPowerType())) {
			sql += " and POWER_TYPE  '%" + queryModel.getPowerType() + "%'";
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getPowerLevel())) {
			sql += " and POWER_LEVEL like '%" + queryModel.getPowerLevel() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getLawName())) {
			sql += " and LAW_NAME like '%" + queryModel.getLawName() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getGistType())) {
			sql += " and GIST_TYPE like '%" + queryModel.getGistType() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getLawStrip())) {
			sql += " and LAW_STRIP = '" + queryModel.getLawStrip() + "'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getFund())) {
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
	public List<Map> test(int firstResult, int rows, PowerBaseModel queryModel){
		String sql = "select p.ID,p.CODE,p.NAME,p.POWER_TYPE,p.POWER_LEVEL,q.LAW_NAME,q.GIST_TYPE,q.LAW_STRIP,q.FUND,q.ITEM,q.CONTENT ,q.GIST_CODE from FX_BASE_POWER p,FX_BASE_POWER_GIST q where p.ID = q.POWER_ID ";
		if (!TeeUtility.isNullorEmpty(queryModel.getCode())) {
			sql += " and CODE = '" + queryModel.getCode() + "'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getName())) {
			sql += " and NAME like '%" + queryModel.getName() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getPowerType())) {
			sql += " and POWER_TYPE  '%" + queryModel.getPowerType() + "%'";
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getPowerLevel())) {
			sql += " and POWER_LEVEL like '%" + queryModel.getPowerLevel() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getLawName())) {
			sql += " and LAW_NAME like '%" + queryModel.getLawName() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getGistType())) {
			sql += " and GIST_TYPE like '%" + queryModel.getGistType() + "%'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getLawStrip())) {
			sql += " and LAW_STRIP = '" + queryModel.getLawStrip() + "'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getFund())) {
			sql += " and FUND = '" + queryModel.getFund() + "'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getItem())) {
			sql += " and ITEM = '" + queryModel.getItem() + "'";

		}
		if (!TeeUtility.isNullorEmpty(queryModel.getContent())) {
			sql += " and CONTENT like '%" + queryModel.getContent() + "%'";

		}
		sql+=" order by CODE";
		
	List<Map> dataList0 =simpleDaoSupport.executeNativeQuery(sql, null, firstResult, rows);
	return dataList0;
}
}
