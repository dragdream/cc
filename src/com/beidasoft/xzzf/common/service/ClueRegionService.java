package com.beidasoft.xzzf.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.common.bean.Region;
import com.beidasoft.xzzf.common.dao.ClueRegionDao;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class ClueRegionService extends TeeBaseService{

	@Autowired
	private ClueRegionDao regionDao;
	/**
	 * 其他省市转办获取省List
	 * @return
	 */
	public List<Region> findProviceList() {
		return regionDao.findProviceList();
	}
	
	/**
	 * 其他省市转办获取市List
	 * @return
	 */
	public List<Region> findCityList(int provinceId) {
		return regionDao.findCityList(provinceId);
	}

	/**
	 * 其他省市转办获取区List
	 * @return
	 */
	public List<Region> findDistrictList(int cityId) {
		return regionDao.findDistrictList(cityId);
	}
}
