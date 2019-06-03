package com.tianee.oa.subsys.crm.setting.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.setting.bean.TeeChinaCity;
import com.tianee.oa.subsys.crm.setting.dao.TeeChinaCityDao;
import com.tianee.oa.subsys.crm.setting.model.TeeChinaCityModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeChinaCityService extends TeeBaseService {
	@Autowired
	private TeeChinaCityDao chinaCityDao;

	/**
	 * @function: 新建或编辑
	 * @author:
	 * @data: 2014年8月24日
	 * @param request
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson addOrUpdateService(HttpServletRequest request, TeeChinaCityModel model) {
		TeeJson json = new TeeJson();
		try {
			if (model.getSid() > 0) {
				TeeChinaCity obj = chinaCityDao.get(model.getSid());
				if (obj != null) {
					obj.setCityCode(model.getCityCode());
					obj.setCityName(model.getCityName());
					chinaCityDao.update(obj);
				}
			} else {
				TeeChinaCity obj = new TeeChinaCity();
				BeanUtils.copyProperties(model, obj);
				obj.setCityCode(model.getCityCode());
				obj.setCityName(model.getCityName());
				obj.setCreateTime(new Date());
				chinaCityDao.save(obj);
			}

			json.setRtState(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * @function: 省-通用列表
	 * @author:
	 * @data: 2014年8月25日
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 *             TeeEasyuiDataGridJson
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getChinaCityList(TeeDataGridModel dm, HttpServletRequest request, TeeChinaCityModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();

		Map requestDatas = TeeServletUtility.getParamMap(request);
		String cityName = (String) requestDatas.get("cityName");
		String cityCode = (String) requestDatas.get("cityCode");

		String startDateStr = (String) requestDatas.get("startDateStr");
		String endDateStr = (String) requestDatas.get("endDateStr");

		String queryStr = " 1=1";
		String hql = "from TeeChinaCity require where cityCode like '__0000'";
		List param = new ArrayList();
		if (!TeeUtility.isNullorEmpty(cityName)) {
			hql += " and require.cityName like ?";
			param.add("%" + cityName + "%");
		}
		if (!TeeUtility.isNullorEmpty(cityCode)) {
			hql += " and require.cityCode like ?";
			param.add("%" + cityCode + "%");
		}

		if (!TeeUtility.isNullorEmpty(startDateStr)) {
			hql += " and require.createTime >= ?";
			param.add(TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", startDateStr + " 00:00:00"));
		}
		if (!TeeUtility.isNullorEmpty(endDateStr)) {
			hql += " and require.createTime <= ?";
			param.add(TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", endDateStr + " 23:59:59"));
		}

		// j.setTotal(chinaCityDao.getQueryCount(loginPerson ,model));//
		// 设置总记录数
		j.setTotal(chinaCityDao.countByList("select count(*) " + hql, param));// 设置总记录数

		hql += " order by require.cityCode asc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeChinaCity> list = chinaCityDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		List<TeeChinaCityModel> modelList = new ArrayList<TeeChinaCityModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeChinaCityModel modeltemp = parseModel(list.get(i));
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * 封装对象
	 * 
	 * @date 2014-3-17
	 * @author
	 * @param obj
	 * @return
	 */
	public TeeChinaCityModel parseModel(TeeChinaCity obj) {
		TeeChinaCityModel model = new TeeChinaCityModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);
		if (!TeeUtility.isNullorEmpty(obj.getCreateTime())) {
			model.setCreateTimeStr(TeeUtility.getDateStrByFormat(obj.getCreateTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
		} else {
			model.setCreateTimeStr("");
		}
		model.setCityCode(obj.getCityCode());
		model.setCityName(obj.getCityName());
		return model;
	}

	/**
	 * 根据sid查看详情
	 * 
	 * @date 2014-3-8
	 * @author
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getInfoByIdService(HttpServletRequest request, TeeChinaCityModel model) {
		TeeJson json = new TeeJson();
		if (model.getSid() > 0) {
			TeeChinaCity out = chinaCityDao.get(model.getSid());
			if (out != null) {
				model = parseModel(out);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		return json;
	}

	/**
	 * 删除省信息（级联市、县）
	 * 
	 * @date 2014年5月27日
	 * @author
	 * @param sids
	 * @return
	 */
	public TeeJson deleteObjByIdService(String sids) {
		TeeJson json = new TeeJson();
		List<TeeChinaCity> list = chinaCityDao.getObjListsById(sids);
		for (TeeChinaCity obj : list) {
			String cityCode = obj.getCityCode();
			List<TeeChinaCity> cityList = chinaCityDao.getCityListsByCityCode(cityCode);
			if (cityList != null && cityList.size() > 0) {
				for (TeeChinaCity city : cityList) {
					String cityCodeStr = city.getCityCode();
					List<TeeChinaCity> countyList = chinaCityDao.getCountyListsByCityCode(cityCodeStr);
					if (countyList != null && countyList.size() > 0) {
						for (TeeChinaCity county : countyList) {
							chinaCityDao.deleteByObj(county);
						}
					}
					chinaCityDao.deleteByObj(city);
				}
			}
		}
		chinaCityDao.delByIds(sids);
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}

	/**
	 * 删除市（级联县）
	 * 
	 * @date 2014年5月27日
	 * @author
	 * @param sids
	 * @return
	 */
	public TeeJson deleteCityObjById(String sids) {
		TeeJson json = new TeeJson();
		List<TeeChinaCity> list = chinaCityDao.getObjListsById(sids);
		for (TeeChinaCity obj : list) {
			String cityCode = obj.getCityCode();
			List<TeeChinaCity> cityList = chinaCityDao.getCountyListsByCityCode(cityCode);
			if (cityList != null && cityList.size() > 0) {
				for (TeeChinaCity city : cityList) {
					chinaCityDao.deleteByObj(city);
				}
			}
		}
		chinaCityDao.delByIds(sids);
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}

	/**
	 * 删除县
	 * 
	 * @date 2014年5月27日
	 * @author
	 * @param sids
	 * @return
	 */
	public TeeJson deleteCountyObjById(String sids) {
		TeeJson json = new TeeJson();
		chinaCityDao.delByIds(sids);
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}

	/**
	 * 通用列表
	 * 
	 * @param dm
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getPlanApprovalListService(TeeDataGridModel dm, HttpServletRequest request, TeeChinaCityModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		Map requestDatas = TeeServletUtility.getParamMap(request);
		int planStatus = TeeStringUtil.getInteger(requestDatas.get("planStatus"), 0);

		String hql = "from TeeChinaCity require where planStatus = ? ";
		List param = new ArrayList();
		param.add(planStatus);
		if (!TeePersonService.checkIsSuperAdmin(loginPerson, "")) {
			hql = hql + " and approvePerson.uuid = ? ";
			param.add(loginPerson.getUuid());
		}

		// 设置总记录数
		j.setTotal(chinaCityDao.countByList("select count(*) " + hql, param));// 设置总记录数

		hql += " order by require.createTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeChinaCity> list = chinaCityDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeChinaCityModel> modelList = new ArrayList<TeeChinaCityModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeChinaCityModel modeltemp = parseModel(list.get(i));
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * @function: 获取所有的省和直辖市
	 * @author:
	 * @data: 2014年8月24日
	 * @return TeeJson
	 */
	public TeeJson getProvinceList() {
		TeeJson json = new TeeJson();
//		String hql = " from TeeChinaCity where cityCode like '__0000'";
		List<TeeChinaCity> list = chinaCityDao.getProvince();
		List<TeeChinaCityModel> modelList = new ArrayList<TeeChinaCityModel>();
		if (list != null && list.size() > 0) {
			for (TeeChinaCity obj : list) {
				modelList.add(parseModel(obj));
			}
		}
		json.setRtData(modelList);
		json.setRtState(true);
		json.setRtMsg("查询成功！ ");
		return json;
	}

	/**
	 * @function: 返回该省所有的地级市
	 * @author:
	 * @data: 2014年8月24日
	 * @param cityCode
	 *            省或者直辖市的cityCode
	 * @return TeeJson
	 */
	public TeeJson getCityListByCode(String cityCode) {
		if (cityCode == null) {
			cityCode = "";
		}
		TeeJson json = new TeeJson();
		List<TeeChinaCity> list = chinaCityDao.getCityListsByCityCode(cityCode);
		List<TeeChinaCityModel> modelList = new ArrayList<TeeChinaCityModel>();
		if (list != null && list.size() > 0) {
			for (TeeChinaCity obj : list) {
				modelList.add(parseModel(obj));
			}
		}
		json.setRtData(modelList);
		json.setRtState(true);
		json.setRtMsg("查询成功！ ");
		return json;
	}

	/**
	 * @function: 获取所有县级地区
	 * @author:
	 * @data: 2014年8月24日
	 * @param cityCode
	 *            省或者直辖市的cityCode
	 * @return TeeJson
	 */
	public TeeJson getCountyListByCode(String cityCode) {
		if (cityCode == null) {
			cityCode = "";
		}
		TeeJson json = new TeeJson();
		String hql = " from TeeChinaCity where cityCode like ? and cityCode <> ?";
		Object[] param = { cityCode.substring(0, 4) + "__", cityCode };
		List<TeeChinaCity> list = chinaCityDao.executeQuery(hql, param);
		List<TeeChinaCityModel> modelList = new ArrayList<TeeChinaCityModel>();
		if (list != null && list.size() > 0) {
			for (TeeChinaCity obj : list) {
				modelList.add(parseModel(obj));
			}
		}
		json.setRtData(modelList);
		json.setRtState(true);
		json.setRtMsg("查询成功！ ");
		return json;
	}

	/**
	 * @function: 校验城市编号
	 * @author:
	 * @data: 2014年8月24日
	 * @param cityCode
	 *            省或者直辖市的cityCode
	 * @return TeeJson
	 */
	public TeeJson checkCityCode(TeeChinaCityModel model) {
		TeeJson json = new TeeJson();
		long count = chinaCityDao.getQueryCount(model);
		int countFlag = 0;
		if (count > 0) {
			countFlag = 1;
		}
		Map map = new HashMap();
		map.put("countFlag", countFlag);

		json.setRtData(map);
		json.setRtState(true);
		json.setRtMsg("查询成功！ ");
		return json;
	}

	/**
	 * @function: 市级-通用列表
	 * @author:
	 * @data: 2014年8月25日
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 *             TeeEasyuiDataGridJson
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getCityListByCode(TeeDataGridModel dm, HttpServletRequest request, TeeChinaCityModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();

		Map requestDatas = TeeServletUtility.getParamMap(request);
		String provinceCode = (String) requestDatas.get("provinceCode");

		String cityName = (String) requestDatas.get("cityName");
		String cityCode = (String) requestDatas.get("cityCode");

		String startDateStr = (String) requestDatas.get("startDateStr");
		String endDateStr = (String) requestDatas.get("endDateStr");

		String hql = "from TeeChinaCity require where cityCode like ? and cityCode <> ?";
		List param = new ArrayList();
		param.add(provinceCode.substring(0, 2) + "__00");
		param.add(provinceCode);
		if (!TeeUtility.isNullorEmpty(cityName)) {
			hql += " and require.cityName like ?";
			param.add("%" + cityName + "%");
		}
		if (!TeeUtility.isNullorEmpty(cityCode)) {
			hql += " and require.cityCode like ?";
			param.add("%" + cityCode + "%");
		}

		if (!TeeUtility.isNullorEmpty(startDateStr)) {
			hql += " and require.createTime >= ?";
			param.add(TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", startDateStr + " 00:00:00"));
		}
		if (!TeeUtility.isNullorEmpty(endDateStr)) {
			hql += " and require.createTime <= ?";
			param.add(TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", endDateStr + " 23:59:59"));
		}

		// j.setTotal(chinaCityDao.getQueryCount(loginPerson ,model));//
		// 设置总记录数
		j.setTotal(chinaCityDao.countByList("select count(*) " + hql, param));// 设置总记录数

		hql += " order by require.cityCode asc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeChinaCity> list = chinaCityDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		List<TeeChinaCityModel> modelList = new ArrayList<TeeChinaCityModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeChinaCityModel modeltemp = parseModel(list.get(i));
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * @function: 县级-通用列表
	 * @author:
	 * @data: 2014年8月25日
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 *             TeeEasyuiDataGridJson
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getCountyByCityCode(TeeDataGridModel dm, HttpServletRequest request, TeeChinaCityModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();

		Map requestDatas = TeeServletUtility.getParamMap(request);
		String cityCodeStr = (String) requestDatas.get("cityCodeStr");

		String cityName = (String) requestDatas.get("cityName");
		String cityCode = (String) requestDatas.get("cityCode");

		String startDateStr = (String) requestDatas.get("startDateStr");
		String endDateStr = (String) requestDatas.get("endDateStr");

		String hql = "from TeeChinaCity require where cityCode like ? and cityCode <> ?";
		List param = new ArrayList();
		param.add(cityCodeStr.substring(0, 4) + "__");
		param.add(cityCodeStr);
		if (!TeeUtility.isNullorEmpty(cityName)) {
			hql += " and require.cityName like ?";
			param.add("%" + cityName + "%");
		}
		if (!TeeUtility.isNullorEmpty(cityCode)) {
			hql += " and require.cityCode like ?";
			param.add("%" + cityCode + "%");
		}

		if (!TeeUtility.isNullorEmpty(startDateStr)) {
			hql += " and require.createTime >= ?";
			param.add(TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", startDateStr + " 00:00:00"));
		}
		if (!TeeUtility.isNullorEmpty(endDateStr)) {
			hql += " and require.createTime <= ?";
			param.add(TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", endDateStr + " 23:59:59"));
		}

		// j.setTotal(chinaCityDao.getQueryCount(loginPerson ,model));//
		// 设置总记录数
		j.setTotal(chinaCityDao.countByList("select count(*) " + hql, param));// 设置总记录数

		hql += " order by require.cityCode asc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeChinaCity> list = chinaCityDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		List<TeeChinaCityModel> modelList = new ArrayList<TeeChinaCityModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeChinaCityModel modeltemp = parseModel(list.get(i));
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * @author nieyi 获取省份树
	 * @param provinceNo
	 * @return
	 */
	public TeeJson getProvinceTree(String provinceNo) {
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> cityList = new ArrayList<TeeZTreeModel>();
		if (TeeUtility.isNullorEmpty(provinceNo)) {
			List<TeeChinaCity> list = chinaCityDao.getProvince();// 第一级
			for (int i = 0; i < list.size(); i++) {
				TeeChinaCity city = list.get(i);
				TeeZTreeModel ztree = new TeeZTreeModel();
				ztree.setId(city.getCityCode() + "");
				ztree.setName(city.getCityName());
				ztree.setParent(false);
				if (chinaCityDao.checkExistsChild(city)) {
					ztree.setParent(true);
				}
				ztree.setpId(provinceNo);
				// ztree.setIconSkin("deptNode");
				cityList.add(ztree);
			}
			json.setRtData(list);
		} else {
			List<TeeChinaCity> list = chinaCityDao.getChildCity(provinceNo);
			for (int i = 0; i < list.size(); i++) {
				TeeChinaCity city = list.get(i);
				TeeZTreeModel ztree = new TeeZTreeModel();
				ztree.setId(city.getCityCode() + "");
				ztree.setName(city.getCityName());
				ztree.setParent(false);
				if (chinaCityDao.checkExistsChild(city)) {
					ztree.setParent(true);
				}
				ztree.setpId(provinceNo);
				// ztree.setIconSkin("deptNode");
				cityList.add(ztree);
			}
		}
		json.setRtData(cityList);
		json.setRtState(true);
		return json;
	}

	/**
	 * @function: 根据城市编号获取数据对象
	 * @author: wyw
	 * @data: 2014年9月3日
	 * @param request
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson getInfoByCityCode(HttpServletRequest request, TeeChinaCityModel model) {
		TeeJson json = new TeeJson();
		if (!TeeUtility.isNullorEmpty(model.getCityCode())) {
			TeeChinaCity out = chinaCityDao.getInfoByCityCode(model.getCityCode());
			if (out != null) {
				model = parseModel(out);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		return json;
	}

	/**
	 * @function: 根据城市编号自动获取下一个
	 * @author: wyw
	 * @data: 2014年9月20日
	 * @param requestMap
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson getAutoNumberByCityCode(Map requestMap, TeeChinaCityModel model) {
		TeeJson json = new TeeJson();
		String cityCode = (String) requestMap.get("cityCode");
		String cityFlag = (String) requestMap.get("cityFlag");//城市标识;1-省；2-市；3-县
		String autoNumber= "01";
		
		if (!TeeUtility.isNullorEmpty(cityCode) && !TeeUtility.isNullorEmpty(cityFlag)) {
			List<TeeChinaCity> list = new ArrayList<TeeChinaCity>();
			if("1".equals(cityFlag)){
				list = chinaCityDao.getProvince();
			}else if("2".equals(cityFlag)){
				list = chinaCityDao.getCityListsByCityCode(cityCode);
			}else if("3".equals(cityFlag)){
				list = chinaCityDao.getCountyListsByCityCode(cityCode);
			}
			
			Collections.sort(list, new Comparator<TeeChinaCity>() {
				@Override
				public int compare(TeeChinaCity o1, TeeChinaCity o2) {
					String cityCode1 = o1.getCityCode();
					String cityCode2 = o2.getCityCode();
					if (cityCode1.compareTo(cityCode2) > 0) {
						return -1;
					} else if (cityCode1.compareTo(cityCode2) == 0) {
						return 0;
					} else {
						return 1;
					}
				}
			});
			
			if (list != null && list.size() > 0) {
				TeeChinaCity obj = list.get(0);
				if (obj != null) {
					String cityCodeStr = TeeStringUtil.getString(obj.getCityCode(), "000000");
					String numberStr = "";
					if("1".equals(cityFlag)){
						numberStr = cityCodeStr.substring(0,2);
					}else if("2".equals(cityFlag)){
						numberStr = cityCodeStr.substring(2,4);
					}else if("3".equals(cityFlag)){
						numberStr = cityCodeStr.substring(4,6);
					}
					
					int number = TeeStringUtil.getInteger(numberStr, 0);
					number ++;
					if(number<10){
						autoNumber = "0" + number;
					}else{
						autoNumber = "" + number;
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("autoNumber", autoNumber);
		
		json.setRtData(map);
		json.setRtState(true);
		json.setRtMsg("查询成功!");
		return json;
	}

}