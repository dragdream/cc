package com.beidasoft.xzzf.punish.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishTache;
import com.beidasoft.xzzf.punish.common.dao.PunishTacheDao;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class PunishTacheService extends TeeBaseService {
	@Autowired
	private PunishTacheDao punishTacheDao;
//保存
	public void save(PunishTache o) {
		punishTacheDao.save(o);
	}
//删除
	public void del(int id) {
		punishTacheDao.delete(id);
	}
//更新
	public void update(PunishTache o) {
		punishTacheDao.update(o);
	}
//通过ID查询
	public PunishTache getbyid(String id) {
		return punishTacheDao.get(id);
	}
	
	public List<PunishTache> getbyindex(String baseId, String index) {
		return punishTacheDao.getbyindex(baseId,index);
	}
//查询全部
	public List<PunishTache> getPunishTacheInfo() {
		return null;
	}
}
