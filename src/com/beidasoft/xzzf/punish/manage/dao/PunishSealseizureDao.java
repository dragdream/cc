package com.beidasoft.xzzf.punish.manage.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.manage.bean.PunishSealseizure;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("PunishSealseizureDao")
public class PunishSealseizureDao extends TeeBaseDao<PunishSealseizure> {

	@Autowired
	private TeeDeptDao deptDao;
	
	/**
	 * 保存
	 */
	public void savePunishSealseizure(PunishSealseizure punishSealseizure) {
		super.save(punishSealseizure);
	}
	
	/**
	 * 删除
	 */
	public void delete(String id) {
		super.delete(id);
	}
	
	/**
	 * 修改
	 */
	public void update(PunishSealseizure punishSealseizure) {
		super.saveOrUpdate(punishSealseizure);
	}
	
	/**
	 * 根据id查询
	 */
	public PunishSealseizure loadById(String id) {
		PunishSealseizure punishSealseizure = super.get(id);
		return punishSealseizure;
	}
	
}
