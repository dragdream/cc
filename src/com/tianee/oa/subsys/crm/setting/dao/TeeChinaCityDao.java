package com.tianee.oa.subsys.crm.setting.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.crm.setting.bean.TeeChinaCity;
import com.tianee.oa.subsys.crm.setting.model.TeeChinaCityModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("chinaCityDao")
public class TeeChinaCityDao extends TeeBaseDao<TeeChinaCity> {

	/**
	 * @function: 根据城市代码统计个数
	 * @author: 
	 * @data: 2014年8月24日
	 * @param model
	 * @return long
	 */
	public long getQueryCount(TeeChinaCityModel model) {
		Object[] param ={model.getCityCode()};
		String queryStr = "";
		if(model.getSid()>0){
			queryStr = " and sid<>" + model.getSid();
		}
		String hql = "select count(sid) from TeeChinaCity  where  cityCode=? " + queryStr;
		long count = count(hql, param);
		return count;
	}

	/**
	 * 分页查询
	 * 
	 * @date 2014-3-17
	 * @author
	 * @param firstResult
	 * @param pageSize
	 * @param dm
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	public List<TeeChinaCity> getPageFind(int firstResult, int pageSize, TeeDataGridModel dm, TeeChinaCityModel model) throws ParseException {
		String hql = "from TeeChinaCity  where  1 = 1  ";
		List list = new ArrayList();
		if (TeeUtility.isNullorEmpty(dm.getSort())) {
			dm.setSort("createTime");
			dm.setOrder("desc");
		}
		hql = hql + " order by " + dm.getSort() + " " + dm.getOrder();
		return pageFindByList(hql, firstResult, pageSize, list);
	}

	/**
	 * 多个删除
	 * 
	 * @date 2014-3-8
	 * @author
	 * @param ids
	 */
	public void delByIds(String ids) {
		if (!TeeUtility.isNullorEmpty(ids)) {
			if (ids.endsWith(",")) {
				ids = ids.substring(0, ids.length() - 1);
			}
			String hql = "delete from TeeChinaCity where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}

	/**
	 * @function: 根据sid串返回数据
	 * @author: 
	 * @data: 2014年8月24日
	 * @param sids
	 * @return List<TeeChinaCity>
	 */
	public List<TeeChinaCity> getObjListsById(String sids) {
		if (sids.endsWith(",")) {
			sids = sids.substring(0, sids.length() - 1);
		}
		String hql = " from TeeChinaCity where sid in(" + sids +")";
		List<TeeChinaCity> list = executeQuery(hql, null);
		return list;
	}
	
	/**
	 * @function: 获取市级数据
	 * @author: 
	 * @data: 2014年8月24日
	 * @param cityCode
	 * @return List<TeeChinaCity>
	 */
	public List<TeeChinaCity> getCityListsByCityCode(String cityCode) {
		String hql = " from TeeChinaCity where cityCode like ? and cityCode <> ?";
		Object [] param = {cityCode.substring(0,2)+"__00",cityCode};
		List<TeeChinaCity> list = executeQuery(hql, param);
		return list;
	}
	
	
	/**
	 * @function: 获取县市数据
	 * @author: 
	 * @data: 2014年8月24日
	 * @param cityCode
	 * @return List<TeeChinaCity>
	 */
	public List<TeeChinaCity> getCountyListsByCityCode(String cityCode) {
		String hql = " from TeeChinaCity where cityCode like ? and cityCode <> ?";
		Object [] param = {cityCode.substring(0,4)+"__",cityCode};
		List<TeeChinaCity> list = executeQuery(hql, param);
		return list;
	}
	
	/**
	 * 多个删除
	 * 
	 * @date 2014-3-8
	 * @author
	 * @param ids
	 */
	public void delCityByCityCode(String cityCode) {
		if (!TeeUtility.isNullorEmpty(cityCode)) {
			
			String hql = " delete from TeeChinaCity where cityCode like ? and cityCode <> ?";
			Object [] param = {cityCode.substring(0,2)+"__00",cityCode};
			deleteOrUpdateByQuery(hql, param);
		}
	}
	
	/**
	 * 获取所有省份
	 * @return
	 */
	public List<TeeChinaCity> getProvince() {
		String hql = " from TeeChinaCity where cityCode like '__0000'";
		List<TeeChinaCity> list = executeQuery(hql, null);
		return list;
	}

	/**
	 * 判断是否有下级城市
	 * @param city
	 * @return
	 */
	public boolean checkExistsChild(TeeChinaCity city) {
		List<TeeChinaCity> list = new ArrayList<TeeChinaCity>();
		if(city.getCityCode().endsWith("0000")){
			String hql = " from TeeChinaCity where cityCode like ? and cityCode <> ?";
			Object [] param = {city.getCityCode().substring(0,2)+"__00",city.getCityCode()};
			 list = executeQuery(hql, param);
		}
	/*	if(city.getCityCode().endsWith("00") && !city.getCityCode().endsWith("0000")){
			String hql = " from TeeChinaCity where cityCode like ? and cityCode <> ?";
			Object [] param = {city.getCityCode().substring(0,4)+"__",city.getCityCode()};
		    list = executeQuery(hql, param);
		}*/
		if(list.size()>0){
			return true;
		}
		return false;
	}

	/**
	 * @author nieyi
	 * 获取下级城市
	 * @param provinceNo
	 * @return
	 */
	public List<TeeChinaCity> getChildCity(String provinceNo) {
		List<TeeChinaCity> list = new ArrayList<TeeChinaCity>();
		if(provinceNo.endsWith("0000")){
			String hql = " from TeeChinaCity where cityCode like ? and cityCode <> ?";
			Object [] param = {provinceNo.substring(0,2)+"__00",provinceNo};
			 list = executeQuery(hql, param);
		}
		/*if(provinceNo.endsWith("00") && !provinceNo.endsWith("0000")){
			String hql = " from TeeChinaCity where cityCode like ? and cityCode <> ?";
			Object [] param = {provinceNo.substring(0,4)+"__",provinceNo};
		    list = executeQuery(hql, param);
		}*/
		return list;
	}
	
	/**
	 * 获取城市名称
	 * @return
	 */
	public String getNameByCityCode(String cityCode) {
		String cityName="";
		String hql = " from TeeChinaCity where cityCode ='"+cityCode+"'";
		List<TeeChinaCity> list = executeQuery(hql, null);
		if(list.size()>0){
			TeeChinaCity city = list.get(0);
			cityName=city.getCityName();
		}
		return cityName;
	}
	
	/**
	 * @function: 根据城市编号获取数据对象
	 * @author: wyw
	 * @data: 2014年9月3日
	 * @param cityCode
	 * @return TeeChinaCity
	 */
	public TeeChinaCity getInfoByCityCode(String cityCode) {
		String hql = " from TeeChinaCity where cityCode ='"+cityCode+"'";
		List<TeeChinaCity> list = executeQuery(hql, null);
		TeeChinaCity city = new TeeChinaCity ();
		if(list.size()>0){
			city = list.get(0);
		}
		return city;
	}
	
}
