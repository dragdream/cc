package com.beidasoft.xzzf.law.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.law.bean.BaseLaw;
import com.beidasoft.xzzf.law.bean.BaseLawDetail;
import com.beidasoft.xzzf.law.dao.BaseLawDao;
import com.beidasoft.xzzf.law.dao.LawDetailDao;
import com.beidasoft.xzzf.law.model.BaseLawModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class BaseLawService extends TeeBaseService {

	@Autowired
	private BaseLawDao LawDao;
	@Autowired
	private LawDetailDao DetailDao;

	/**
	 * 通过主键查询用户信息
	 * @param id
	 * @return
	 */
	public BaseLaw getById(String id) {
		return LawDao.get(id);
	}

	/**
	 * 分页进行查询
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<BaseLaw> listByPage(int firstResult, int rows,
			BaseLawModel queryModel) {
		return LawDao.listByPage(firstResult, rows, queryModel);
	}

	/**
	 * 返回总记录数
	 * 
	 * @return
	 */
	public long getTotal(BaseLawModel queryModel) {
		return LawDao.getTotal(queryModel);
	}

	public List<BaseLawDetail> getLawListById(String id,
			TeeDataGridModel dataGridModel) {
		return DetailDao.getLawListById(id, dataGridModel);
	}

	/**
	 * 返回总记录数
	 * @return
	 */
	public long getTotal(String id) {
		return LawDao.getTotal(id);
	}

}
