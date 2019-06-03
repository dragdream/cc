package com.beidasoft.xzzf.common.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.common.bean.Region;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class ClueRegionDao extends TeeBaseDao<Region>{

	
	/**
	 * 获取省List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Region> findProviceList() {
		Session session = this.getSession();
		List<Region> proviceList = null;
		Query query = session.createQuery("from Region where parentId = 0 order by id asc");
		proviceList = query.list();
		return proviceList;
	}
	
	/**
	 * 获取市List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Region> findCityList(int provinceId) {
		Session session = this.getSession();
		List<Region> cityList = null;
		Query query = session.createQuery("from Region where parentId = "+provinceId+" order by id asc");
		cityList = query.list();
		return cityList;
	}

	/**
	 * 获取区List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Region> findDistrictList(int cityId) {
		Session session = this.getSession();
		List<Region> districtList = null;
		Query query = session.createQuery("from Region where parentId = "+cityId+" order by id asc");
		districtList = query.list();
		return districtList;
	}
}
