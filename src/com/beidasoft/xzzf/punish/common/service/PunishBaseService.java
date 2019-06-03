package com.beidasoft.xzzf.punish.common.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.AffiliatedPerson;
import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.PunishTache;
import com.beidasoft.xzzf.punish.common.dao.AffiliatedDao;
import com.beidasoft.xzzf.punish.common.dao.PunishBaseDao;
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.dao.PunishTacheDao;
import com.beidasoft.xzzf.punish.common.model.PunishBaseModel;
import com.beidasoft.xzzf.transferred.dao.ManagementsDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class PunishBaseService extends TeeBaseService {
	@Autowired
	private PunishBaseDao punishBaseDao;
	@Autowired
	private PunishFlowDao punishFlowDao;
	@Autowired
	private ManagementsDao managementsDao;
	@Autowired
	private AffiliatedDao affiliatedDao;
	@Autowired
	private PunishTacheDao tacheDao;

	// 保存
	public TeeJson save(PunishBaseModel baseModel, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		String baseId = "";
		if (request.getParameter("tachName").equals("现场检查")) { // 如果是现场检查的保存
			
			if (StringUtils.isBlank(baseModel.getBaseId())) { // 新增（自发检查初次保存）
				PunishBase punishbase = new PunishBase();

				String uuid = UUID.randomUUID().toString();
				// 属性值传递
				BeanUtils.copyProperties(baseModel, punishbase);
				
				// 名称重新设定
				if ("2".equals(baseModel.getLitigantType())) {
					punishbase.setOrganName(baseModel.getOrganName());
				} else {
					punishbase.setPsnName(baseModel.getPsnName());
					if ("3".equals(baseModel.getLitigantType())) {
						punishbase.setPsnShopName(baseModel.getPsnShopName());
					}
				}
				punishbase.setBaseId(uuid);
				// 案件编号
				punishbase.setBaseCode(TeeDateUtil.format(new Date(), "yyyyMMddHHmmsssss"));
				// 状态
				punishbase.setStatus("00");
				// 设置 是否申请立案状态为 未申请立案
				punishbase.setIsRegister(1);
				// 设置 是否立案 为 未立案
				punishbase.setIsApply(1);
				
				// 保存负责人信息
				punishbase.setChargePsnName(user.getUserName());
				punishbase.setChargePsnId(user.getUuid());
				punishbase.setChargeDeptName(user.getDept().getDeptName());
				punishbase.setChargeDeptId(user.getDept().getUuid());
				
				// 保存现场检查案件基本信息
				punishBaseDao.saveOrUpdate(punishbase);
				
				baseId += punishbase.getBaseId();
			} else { //更新
				PunishBase punishbase = getbyid(baseModel.getBaseId()); // 通过baseId查询punishBase对象
				punishbase.setLitigantType(baseModel.getLitigantType());

				if ("2".equals(baseModel.getLitigantType())) {
					punishbase.setOrganName(baseModel.getOrganName());
					punishbase.setOrganAddress(baseModel.getOrganAddress());
					punishbase.setOrganLeadingName(baseModel.getOrganLeadingName());
					punishbase.setOrganLeadingTel(baseModel.getOrganLeadingTel());
					punishbase.setOrganType(baseModel.getOrganType());
					punishbase.setOrganCodeType(baseModel.getOrganCodeType());
				} else {
					punishbase.setPsnName(baseModel.getPsnName());
					punishbase.setPsnSex(baseModel.getPsnSex());
					punishbase.setPsnTel(baseModel.getPsnTel());
					punishbase.setPsnAddress(baseModel.getPsnAddress());
					punishbase.setPsnType(baseModel.getPsnType());
					punishbase.setPsnIdNo(baseModel.getPsnIdNo());

					if ("3".equals(baseModel.getLitigantType())) {
						punishbase.setPsnShopName(baseModel.getPsnShopName());
					}
				}

				// 保存现场检查案件基本信息
				punishBaseDao.saveOrUpdate(punishbase);
				baseId += punishbase.getBaseId();
			}

			if (baseModel.getPsnArr() != "") {
				// 获取前台传来的参与人信息
				JSONArray psnArr = JSONArray.fromObject(baseModel.getPsnArr());
				if (psnArr.size() != 1) {
					// 保存前删除所有与案件有关的参与人信息
					affiliatedDao.deletePersonByBaseId(baseId);
					for (int i = 0; i < psnArr.size(); i++) {
						JSONObject object = psnArr.getJSONObject(i);

						AffiliatedPerson person = new AffiliatedPerson();

						person.setId(UUID.randomUUID().toString());
						person.setBaseId(baseId);
						if (StringUtils.isNotBlank(request.getParameter("lawLinkId"))) {
							person.setLawLinkId(request.getParameter("lawLinkId"));
						}
						person.setDepartmentId(object.getInt("deptId"));
						person.setDepartmentName(object.getString("deptName"));
						person.setPersonId(object.getInt("personId"));
						person.setPersonName(object.getString("personName"));

						person.setPersonNo("0");
						person.setDelFlg("0");

						// 重新保存参与人信息
						affiliatedDao.save(person);
					}
				}
			}

			json.setRtData(baseId);

		} else { // 如果是正常的立案环节开始
			PunishBase punishbase = getbyid(baseModel.getBaseId()); // 通过baseId查询punishBase对象
			punishbase.setLitigantType(baseModel.getLitigantType());

			if ("2".equals(baseModel.getLitigantType())) {
				punishbase.setOrganName(baseModel.getOrganName());
				punishbase.setOrganAddress(baseModel.getOrganAddress());
				punishbase.setOrganLeadingName(baseModel.getOrganLeadingName());
				punishbase.setOrganLeadingTel(baseModel.getOrganLeadingTel());
				punishbase.setOrganType(baseModel.getOrganType());
				punishbase.setOrganCodeType(baseModel.getOrganCodeType());
			} else {
				punishbase.setPsnName(baseModel.getPsnName());
				punishbase.setPsnSex(baseModel.getPsnSex());
				punishbase.setPsnTel(baseModel.getPsnTel());
				punishbase.setPsnAddress(baseModel.getPsnAddress());
				punishbase.setPsnType(baseModel.getPsnType());
				punishbase.setPsnIdNo(baseModel.getPsnIdNo());

				if ("3".equals(baseModel.getLitigantType())) {
					punishbase.setPsnShopName(baseModel.getPsnShopName());
				}
			}

			if (request.getParameter("tachName").equals("调查取证")) { // 如果是调查取证环节
				// 获取前台传来的参与人信息
				JSONArray psnArr = JSONArray.fromObject(baseModel.getPsnArr());
				if (psnArr.size() != 1) {
					// 保存前删除所有与案件有关的参与人信息
					affiliatedDao.deletePersonByBaseId(punishbase.getBaseId());
					for (int i = 0; i < psnArr.size(); i++) {
						JSONObject object = psnArr.getJSONObject(i);

						AffiliatedPerson person = new AffiliatedPerson();

						person.setId(UUID.randomUUID().toString());
						person.setBaseId(punishbase.getBaseId());
						person.setLawLinkId(request.getParameter("lawLinkId"));
						person.setDepartmentId(object.getInt("deptId"));
						person.setDepartmentName(object.getString("deptName"));
						person.setPersonId(object.getInt("personId"));
						person.setPersonName(object.getString("personName"));

						person.setPersonNo("0");
						person.setDelFlg("0");

						// 重新保存参与人信息
						affiliatedDao.save(person);
					}
				}
			}
			punishBaseDao.saveOrUpdate(punishbase);
			json.setRtData(baseModel.getBaseId());
		}
		return json;
	}

	// 删除
	public void del(int id) {
		punishBaseDao.delete(id);
	}

	// 更新
	public void update(PunishBase o) {
		punishBaseDao.update(o);
	}

	// 通过id查询
	public PunishBase getbyid(String baseid) {
		return punishBaseDao.get(baseid);
	}

	// 查询全部
	public void getALL(int id) {
		punishBaseDao.getPunishBaseInfo(id, id, id);
	}

	public TeeEasyuiDataGridJson getPunishBaseOfPage(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();

		// 立案时间
		String startFilingTime = TeeStringUtil.getString(request
				.getParameter("startTime"));
		String endFilingTime = TeeStringUtil.getString(request
				.getParameter("endTime"));

		// 查询条件list
		List param = new ArrayList();
		// 基础hql
		String hql = " from PunishBase ";

		long total = simpleDaoSupport.count("select count(id) " + hql,
				param.toArray());
		datagrid.setTotal(total);
		@SuppressWarnings("unchecked")
		List<PunishBase> list = simpleDaoSupport.pageFind(hql
				+ " order by filingDate desc ",
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(),
				param.toArray());

		List<PunishBaseModel> models = new ArrayList<PunishBaseModel>();
		PunishBaseModel model = null;
		for (PunishBase row : list) {
			model = new PunishBaseModel();

			model = baseferModel(row);

			models.add(model);

		}

		datagrid.setRows(models);

		return datagrid;
	}

	/**
	 * 将bean转换为modelBase
	 * 
	 * @param transactBean
	 * @return
	 */
	public PunishBaseModel baseferModel(PunishBase pubishBase) {
		PunishBaseModel baseModel = new PunishBaseModel();

		BeanUtils.copyProperties(pubishBase, baseModel);

		//违法日期
		if (pubishBase.getIllegalDate() != null) {
			baseModel.setIllegalDateStr(TeeDateUtil.format(pubishBase.getIllegalDate(), "yyyy年MM月dd日"));
		}
		
		//指派日期
		if (pubishBase.getAppointTime() != null) {
			baseModel.setAppointTimeStr(TeeDateUtil.format(pubishBase.getAppointTime()));
		}
		
		//立案日期
		if (pubishBase.getReceiveDate() != null) {
			baseModel.setReceiveDateStr(TeeDateUtil.format(pubishBase.getReceiveDate()));
		}
		// 转换立案日期
		if (pubishBase.getFilingDate() != null) {
			baseModel.setFilingDateStr(TeeDateUtil.format(pubishBase.getFilingDate()));
		}
		// 转换检查日期
		if (pubishBase.getInspectionDate() != null) {
			baseModel.setInspectionDateStr(TeeDateUtil.format(pubishBase.getInspectionDate()));
		}
		// 转换处罚决定书日期
		if (pubishBase.getPunishmentDate() != null) {
			baseModel.setPunishmentDateStr(TeeDateUtil.format(pubishBase.getPunishmentDate()));
		}
		// 转换处罚执行日期
		if (pubishBase.getPunishmentExeDate() != null) {
			baseModel.setPunishmentExeDateStr(TeeDateUtil.format(pubishBase.getPunishmentExeDate()));
		}
		// 转换结案日期
		if (pubishBase.getClosedDate() != null) {
			baseModel.setClosedDateStr(TeeDateUtil.format(pubishBase.getClosedDate()));
		}

		return baseModel;
	}

	/**
	 * 根据baseId查询案件基础表信息
	 * 
	 * @param taskRecId
	 * @return
	 */
	public PunishBase getBytaskRecId(String taskRecId) {
		return punishBaseDao.getBytaskRecId(taskRecId);
	}

	/**
	 * 更新案件结案状态 person存办结人员信息
	 * 
	 * @param punishBase
	 * @param person
	 */
	public void caseClosed(String baseId, TeePerson person) {
		// 1更新案件状态
		PunishBase baseInfo = punishBaseDao.get(baseId);
		// 设置结案状态
		baseInfo.setIsClosed(1);
		// 设置归档状态
		baseInfo.setPunishFlg("0");
		// 设置借还状态
		baseInfo.setBorrowingFlg("0");
		// 更新案件信息
		punishBaseDao.save(baseInfo);
	}

	/**
	 * @param punishBase
	 * @return
	 */
	private PunishBaseModel punishBaseModel(PunishBase punishBase) {
		PunishBaseModel punishBaseModel = new PunishBaseModel();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		BeanUtils.copyProperties(punishBase, punishBaseModel);
		// punishmentDate
		if (punishBase.getPunishmentDate() != null) {
			punishBaseModel.setPunishmentDateStr(dateFormat.format(punishBase
					.getPunishmentDate().getTime()));
		}
		return punishBaseModel;
	}

	/**
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getTransManagePage(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		// 查询条件list
		List param = new ArrayList();
		// 基础hql
		String hql = " from PunishBase  ";
		long total = simpleDaoSupport.count("select count(isClosed) " + hql
				+ "where isClosed = 1", param.toArray());
		datagrid.setTotal(total);
		List<PunishBase> transList = simpleDaoSupport.pageFind(hql
				+ "where isClosed = 1", (dm.getPage() - 1) * dm.getRows(),
				dm.getRows(), param.toArray());
		List<PunishBaseModel> models = new ArrayList<PunishBaseModel>();
		PunishBaseModel model = null;
		for (PunishBase row : transList) {
			model = new PunishBaseModel();
			model = punishBaseModel(row);
			// BeanUtils.copyProperties(row, model);
			models.add(model);
		}
		datagrid.setRows(models);
		return datagrid;
	}

	/**
	 * 修改案件
	 * 
	 * @param caseBase
	 */
	public void updateFile(PunishBase punishBase) {
		punishBaseDao.update(punishBase);
	}

	/**
	 * 通过案源ID 获取 案件List（因为同一个案源可指派多个案件）
	 * 
	 * @return
	 */
	public List<PunishBase> getBaseBySourceId(String sourceId) {
		List<PunishBase> bases = new ArrayList<PunishBase>();
		if (sourceId != "") {
			bases = punishBaseDao.getBaseBySourceId(sourceId);
		}
		return bases;
	}

	/**
	 * 条件分页查询案件归档
	 * 
	 * @param companyModel
	 * @param dataGridModel
	 * @return
	 */
	public List<PunishBase> getPunishBases(PunishBaseModel punishBaseModel,
			TeeDataGridModel dataGridModel) {
		return punishBaseDao.getPunishBases(punishBaseModel, dataGridModel);
	}

	/**
	 * 条件分页查询案件查看
	 * 
	 * @param companyModel
	 * @param dataGridModel
	 * @return
	 */
	public List<PunishBase> getTransPunish(PunishBaseModel punishBaseModel,
			TeeDataGridModel dataGridModel) {
		return punishBaseDao.getTransPunish(punishBaseModel, dataGridModel);
	}

	/**
	 * 返回总记录数
	 * 
	 * @return
	 */
	public long getTotal() {
		return punishBaseDao.getTotal();
	}

	/**
	 * 返回符合条件记录数
	 * 
	 * @return
	 */
	public long getTotal(PunishBaseModel punishBaseModel) {
		return punishBaseDao.getTotal(punishBaseModel);
	}

	/**
	 * 返回符合条件记录数
	 * 
	 * @return
	 */
	public long getTransTotal(PunishBaseModel punishBaseModel) {
		return punishBaseDao.getTransTotal(punishBaseModel);
	}

	/**
	 * 案卷查询（按当前登录人）
	 * 
	 * @param dm
	 * @param model
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getRecordsList(TeeDataGridModel dm,
			PunishBaseModel model, HttpServletRequest request) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();

		// 查询条件list
		List param = new ArrayList();
		// 基础hql
		StringBuffer hql = new StringBuffer(
				"from PunishBase where 1=1 and isClosed=1 ");

		// 拿到登陆人信息
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		// 执法队员的查询权限 可查看自己经手的案件的案卷
		if (loginPerson.getUserRole().getUuid() == 6) {
			hql.append(" and( majorPersonName ='" + loginPerson.getUserName()
					+ "'");
			hql.append(" or minorPersonName ='" + loginPerson.getUserName()
					+ "')");
		} else if (loginPerson.getUserRole().getUuid() == 12) {
			// 执法队长的查询权限 可查看本部门的案件的案卷
			hql.append(" and departmentName ='"
					+ loginPerson.getDept().getUuid() + "'");
		}
		// 法制人员,主管领导：可查看全总队或全区的案件的案卷

		// 查询按钮字段
		// 案卷标题
		if (!TeeUtility.isNullorEmpty(model.getBaseTitle())) {
			String baseTitle = TeeDbUtility.formatString(model.getBaseTitle());
			hql.append(" and baseTitle like '%" + baseTitle + "%' ");
		}
		// 案件来源
		if (!TeeUtility.isNullorEmpty(model.getSourceType())) {
			String sourceType = TeeDbUtility
					.formatString(model.getSourceType());
			hql.append(" and sourceType like '%" + sourceType + "%' ");
		}

		long total = simpleDaoSupport.count("select count(id) " + hql,
				param.toArray());
		datagrid.setTotal(total);
		List<PunishBase> list = simpleDaoSupport.pageFind(hql.toString(),
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(),
				param.toArray());
		List<PunishBaseModel> models = new ArrayList<PunishBaseModel>();
		PunishBaseModel punishBaseModel = null;
		for (PunishBase row : list) {
			punishBaseModel = transferModel(row);
			models.add(punishBaseModel);
		}
		datagrid.setRows(models);

		return datagrid;
	}
	/**
	 * 查询所有已归档案件
	 * @param dm
	 * @param model
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson ListPunishBase(TeeDataGridModel dm,
			PunishBaseModel model, HttpServletRequest request) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		
		// 查询条件list
		List param = new ArrayList();
		// 基础hql
		StringBuffer hql = new StringBuffer("from PunishBase where 1=1 and punishFlg='1' ");
		
		//案卷标题
		if(!TeeUtility.isNullorEmpty(model.getBaseTitle())){
			String baseTitle = TeeDbUtility.formatString(model.getBaseTitle());
			hql.append(" and baseTitle like '%"+baseTitle+"%' ");
		}
		//案件来源
		if(!TeeUtility.isNullorEmpty(model.getSourceType())){
			String sourceType = TeeDbUtility.formatString(model.getSourceType());
			hql.append(" and sourceType like '%"+sourceType+"%' ");
		}
		
		long total = simpleDaoSupport.count("select count(id) " + hql, param.toArray());
		datagrid.setTotal(total);
		List<PunishBase> list = simpleDaoSupport.pageFind(hql.toString(), (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param.toArray());
		List<PunishBaseModel> models = new ArrayList<PunishBaseModel>();
		PunishBaseModel punishBaseModel = null;
		for(PunishBase row : list) {
			punishBaseModel = transferModel(row);
			models.add(punishBaseModel);
		}
		datagrid.setRows(models);
		
		return datagrid;
	}

	// bean转model
	public PunishBaseModel transferModel(PunishBase baseInfo) {
		PunishBaseModel model = new PunishBaseModel();
		BeanUtils.copyProperties(baseInfo, model);

		// 转换立案时间
		if (!TeeUtility.isNullorEmpty(baseInfo.getFilingDate())) {
			model.setFilingDateStr(TeeDateUtil.format(baseInfo.getFilingDate(),
					"yyyy-MM-dd HH时mm分"));
		}
		// 转换结案时间
		if (!TeeUtility.isNullorEmpty(baseInfo.getClosedDate())) {
			model.setClosedDateStr(TeeDateUtil.format(baseInfo.getClosedDate(),
					"yyyy-MM-dd HH时mm分"));
		}
		return model;
	}

	/**
	 * 案卷日常统计周统计
	 * @param request
	 * @return
	 */
	public Map<String,List<Integer>> getListDaily(HttpServletRequest request) {
		// 基础sql
		StringBuffer sql = new StringBuffer("");
		
		sql.append("SELECT  SOURCE_TYPE AS NAME,");
		sql.append("      COUNT (BASE_ID) AS num");
		sql.append("         FROM ZF_PUNISH_BASE");
		sql.append("         WHERE IS_CLOSED=1");

		// 拿到登陆人信息
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		// 执法队员的查询权限 可查看自己经手的案件的案卷
		if (loginPerson.getUserRole().getUuid() == 6) {
			sql.append(" AND( MAJOR_PERSON_NAME ='"
					+ loginPerson.getUserName() + "'");
			sql.append(" OR MINOR_PERSON_NAME ='" + loginPerson.getUserName()
					+ "')");
		} else if (loginPerson.getUserRole().getUuid() == 12) {
			// 执法队长的查询权限 可查看本部门的案件的案卷
			sql.append(" AND DEPARTMENT_ID ='"
					+ loginPerson.getDept().getUuid() + "'");
		}
		// 法制人员,主管领导：可查看全总队或全区的案件的案卷
		
		// 拿到今天的日期
		Date date = new Date();
		
		//案源任务
		List<Integer> listAn = new ArrayList<>();
		//双随机检查
		List<Integer> listS = new ArrayList<>();
		//检查计划
		List<Integer> listJ = new ArrayList<>();
		
		//返回的map
		Map<String,List<Integer>> mapList = new HashMap<String,List<Integer>>();
		
		// 取到今天到周一每天的案卷统计
		for (int i = 0; date.compareTo(getMondayOfThisWeek(i)) >= 0; i++) {
			StringBuffer sqlStr = new StringBuffer(sql);
			String dateStr = TeeDateUtil.format(getMondayOfThisWeek(i),
					"yyyy-MM-dd");
			sqlStr.append(" AND to_date(to_char(CLOSED_DATE, 'yyyy-MM-dd'),'yyyy-MM-dd') = to_date('");
			sqlStr.append(dateStr);
			sqlStr.append("','yyyy-MM-dd')");
			sqlStr.append(" GROUP BY SOURCE_TYPE");		
			List result = simpleDaoSupport.executeNativeQuery(sqlStr.toString(), null,0, 100);
			
			//根据案件来源类型赋值
			List<Map> map = (List<Map>) result;
			listAn.add(0);
			listS.add(0);
			listJ.add(0);
			for (int j = 0; j < map.size(); j++) {
				if("10".equals(map.get(j).get("NAME"))){//来源类型
					listAn.set(listAn.size() - 1, TeeStringUtil.getInteger(map.get(j).get("num"), 0));
				}
				if("20".equals(map.get(j).get("NAME"))){
					listS.set(listS.size() - 1, TeeStringUtil.getInteger(map.get(j).get("num"), 0));
				}
				if("30".equals(map.get(j).get("NAME"))){
					listJ.set(listJ.size() - 1, TeeStringUtil.getInteger(map.get(j).get("num"), 0));
				}
			}
		}
		
		//赋值到map中，方便js中取值
		mapList.put("an", listAn);
		mapList.put("s", listS);
		mapList.put("j", listJ);
		
		return mapList;
	}
	
	/**
	 * 案卷日常统计领域类型统计
	 * @param request
	 * @return
	 */
	public Map<String,Integer> getListDoMainType(HttpServletRequest request) {
		// 基础sql
		StringBuffer sql = new StringBuffer("");
		
		sql.append("SELECT  DOMAIN_TYPE AS NAME,");
		sql.append("      COUNT (BASE_ID) AS num");
		sql.append("         FROM ZF_PUNISH_BASE");
		sql.append("         WHERE IS_CLOSED=1");

		// 拿到登陆人信息
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		// 执法队员的查询权限 可查看自己经手的案件的案卷
		if (loginPerson.getUserRole().getUuid() == 6) {
			sql.append(" AND( MAJOR_PERSON_NAME ='"
					+ loginPerson.getUserName() + "'");
			sql.append(" OR MINOR_PERSON_NAME ='" + loginPerson.getUserName()
					+ "')");
		} else if (loginPerson.getUserRole().getUuid() == 12) {
			// 执法队长的查询权限 可查看本部门的案件的案卷
			sql.append(" AND DEPARTMENT_ID ='"
					+ loginPerson.getDept().getUuid() + "'");
		}
		// 法制人员,主管领导：可查看全总队或全区的案件的案卷
		
		// 拿到今天的日期
		Date date = new Date();
		
		//返回的map
		Map<String,Integer> mapList = new HashMap<String,Integer>();
		
		//七种类型在每周的每天出现的次数
		Integer i1 = 0;//新闻出版
		Integer i2 = 0;//版权
		Integer i3 = 0;//广播电视	
		Integer i4 = 0;//电影
		Integer i5 = 0;//文化
		Integer i6 = 0;//文物
		Integer i7 = 0;//旅游
		
		//查询一周的数据，一天一循环
		for (int i = 0; date.compareTo(getMondayOfThisWeek(i)) >= 0; i++) {
			StringBuffer sqlStr = new StringBuffer(sql);
			String dateStr = TeeDateUtil.format(getMondayOfThisWeek(i),
					"yyyy-MM-dd");
			sqlStr.append(" AND to_date(to_char(CLOSED_DATE, 'yyyy-MM-dd'),'yyyy-MM-dd') = to_date('");
			sqlStr.append(dateStr);
			sqlStr.append("','yyyy-MM-dd')");
			sqlStr.append(" GROUP BY DOMAIN_TYPE");		
			List result = simpleDaoSupport.executeNativeQuery(sqlStr.toString(), null,0, 100);
			
			//拿到根据领域分租的list
			List<Map> map = (List<Map>) result;
			
			//循环拿值  每种类型值相加
			for (int j = 0; j < map.size(); j++) {
				if("10".equals(map.get(j).get("NAME"))){//领域类型
					i1 = i1 + TeeStringUtil.getInteger(map.get(j).get("num"), 0);
				}
				if("20".equals(map.get(j).get("NAME"))){
					i2 = i2 + TeeStringUtil.getInteger(map.get(j).get("num"), 0);
				}
				if("30".equals(map.get(j).get("NAME"))){
					i3 = i3 + TeeStringUtil.getInteger(map.get(j).get("num"), 0);
				}
				if("40".equals(map.get(j).get("NAME"))){
					i4 = i4 + TeeStringUtil.getInteger(map.get(j).get("num"), 0);
				}
				if("50".equals(map.get(j).get("NAME"))){
					i5 = i5 + TeeStringUtil.getInteger(map.get(j).get("num"), 0);
				}
				if("60".equals(map.get(j).get("NAME"))){
					i6 = i6 + TeeStringUtil.getInteger(map.get(j).get("num"), 0);
				}
				if("70".equals(map.get(j).get("NAME"))){
					i7 = i7 + TeeStringUtil.getInteger(map.get(j).get("num"), 0);
				}
			}
		}
		
		//赋值到map中，方便js中取值
		mapList.put("i1", i1);
		mapList.put("i2", i2);
		mapList.put("i3", i3);
		mapList.put("i4", i4);
		mapList.put("i5", i5);
		mapList.put("i6", i6);
		mapList.put("i7", i7);
		
		return mapList;
	}
	
	/**
	 * 案卷日常统计月统计
	 * @param request
	 * @return
	 */
	public Map<String,Integer> getListMonth(HttpServletRequest request) {
		// 基础sql
		StringBuffer sql = new StringBuffer("");
		
		sql.append("SELECT COUNT (BASE_ID) AS num");
		sql.append("         FROM ZF_PUNISH_BASE");
		sql.append("         WHERE IS_CLOSED=1");

		// 拿到登陆人信息
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		// 执法队员的查询权限 可查看自己经手的案件的案卷
		if (loginPerson.getUserRole().getUuid() == 6) {
			sql.append(" AND( MAJOR_PERSON_NAME ='"
					+ loginPerson.getUserName() + "'");
			sql.append(" OR MINOR_PERSON_NAME ='" + loginPerson.getUserName()
					+ "')");
		} else if (loginPerson.getUserRole().getUuid() == 12) {
			// 执法队长的查询权限 可查看本部门的案件的案卷
			sql.append(" AND DEPARTMENT_ID ='"
					+ loginPerson.getDept().getUuid() + "'");
		}
		// 法制人员,主管领导：可查看全总队或全区的案件的案卷
		
		// 拿到今天的月份
		Calendar c = Calendar.getInstance();
		String dateStr = TeeDateUtil.format(c);
		Date date = TeeDateUtil.format(dateStr,"yyyy-MM");//转化为几年几月
		
		//返回的map
		Map<String,Integer> mapList = new HashMap<String,Integer>();
		
		//查询一年的的数据，一月一循环
		for (int i = 0; date.compareTo(getMindayOfMonth(i)) >= 0; i++) {
			StringBuffer sqlStr = new StringBuffer(sql);
			
			String dateStr1 = TeeDateUtil.format(getMindayOfMonth(i));
			
			sqlStr.append(" AND to_date(to_char(CLOSED_DATE, 'yyyy-MM'),'yyyy-MM') = to_date('");
			sqlStr.append(dateStr1);
			sqlStr.append("','yyyy-MM')");
			List result = simpleDaoSupport.executeNativeQuery(sqlStr.toString(), null,0, 100);
			
			//拿到根据领域分租的list
			List<Map> map = (List<Map>) result;
			
			//判断是否为空
			if(map.size() >= 0){
				int integer = TeeStringUtil.getInteger(map.get(0).get("num"), 0);
				mapList.put("month"+(i+1), integer);
			}
		}
		
		//如果为空，赋值为o
		if(mapList.get("month1") == null){
			mapList.put("month1", 0);
		}
		if(mapList.get("month2") == null){
			mapList.put("month2", 0);
		}
		if(mapList.get("month3") == null){
			mapList.put("month3", 0);
		}
		if(mapList.get("month4") == null){
			mapList.put("month4", 0);
		}
		if(mapList.get("month5") == null){
			mapList.put("month5", 0);
		}
		if(mapList.get("month6") == null){
			mapList.put("month6", 0);
		}
		if(mapList.get("month7") == null){
			mapList.put("month7", 0);
		}
		if(mapList.get("month8") == null){
			mapList.put("month8", 0);
		}
		if(mapList.get("month9") == null){
			mapList.put("month9", 0);
		}
		if(mapList.get("month10") == null){
			mapList.put("month10", 0);
		}
		if(mapList.get("month11") == null){
			mapList.put("month11", 0);
		}
		if(mapList.get("month12") == null){
			mapList.put("month12", 0);
		}
		return mapList;
	}

	/**
	 * 得到本周周一的时间并加上天数返回某天的日期
	 * 
	 * @return yyyy-MM-dd
	 */
	public Date getMondayOfThisWeek(int day) {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		c.add(Calendar.DATE, -day_of_week + 1);
		
		// 现在的日期加上天数返回日期
		c.add(Calendar.DATE, day);
		String dateStr = TeeDateUtil.format(c);
		Date date = TeeDateUtil.format(dateStr,"yyyy-MM-dd");
		return date;
	}
	
	//获取本年一月一号日期加上几个月的方法
	public Date getMindayOfMonth(int month){
		Calendar cal = Calendar.getInstance();
		//拿到本年一月一号的日期
		String thisYearStart = cal.get(Calendar.YEAR)+"-01-01";
		
		//转化为Calendar类型
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Date dateStr = null;
		try {
			dateStr = sdf.parse(thisYearStart);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateStr);
		
		calendar.add(Calendar.MONTH,month);//日期加几个月
		
		String dateS = TeeDateUtil.format(calendar);
		Date date = TeeDateUtil.format(dateS,"yyyy-MM");//转化为几年几月
		
		return date;
	}
	
	/**
	 * 工作台页面获取执法队案件办理量（柱形图）
	 * @param request
	 * @return
	 */
	public List getCaseHandle(HttpServletRequest request) {
		StringBuffer sql = new StringBuffer("");

		sql.append("SELECT  DEPARTMENT_NAME namea,");
		sql.append("      COUNT (BASE_ID) AS num");
		sql.append("         FROM ZF_PUNISH_BASE");
		sql.append("         WHERE DEPARTMENT_NAME LIKE '执法%'");
		sql.append("         AND IS_CLOSED = 1");
		sql.append("		GROUP BY DEPARTMENT_NAME ORDER BY num DESC");
		List result = simpleDaoSupport.executeNativeQuery(sql.toString(), null,0, 10000);
		return result;
	}	
	
	/**
	 * 工作台页面获取执法队案件办理量（饼状图）
	 * @param request
	 * @return
	 */
	public List getCasePie(HttpServletRequest request) {
		StringBuffer sql = new StringBuffer("");

		sql.append("SELECT  DEPARTMENT_NAME namea,");
		sql.append("      COUNT (BASE_ID) AS num");
		sql.append("         FROM ZF_PUNISH_BASE");
		sql.append("         WHERE DEPARTMENT_NAME LIKE '执法%'");
		sql.append("         AND IS_CLOSED = 1");
		sql.append("		GROUP BY DEPARTMENT_NAME ORDER BY num DESC");
		List result = simpleDaoSupport.executeNativeQuery(sql.toString(), null,0, 10000);
		return result;
	}
	
	/**
	 * 查询首页待办任务list分页
	 */
	public List<PunishBase> listByPage(int firstResult, int rows,PunishBaseModel queryModel, HttpServletRequest request) {
		return punishBaseDao.listByPage(firstResult, rows, queryModel, request);
	}
	
	/**
 	 * 查询领导页面案件数(办案)
 	 * @param  dayOrYear  今日或年度
 	 * @param  deptId  部门ID 0为全部
 	 * @param  status  0所有，1立案，2处罚，3结案，4办理中
 	 * @return  long
 	 */
 	public long getLeaderCountDeal(String dayOrYear, int deptId, int status){
 		//拿到今天的日期
 		Date date = new Date();
 		String dateStr = TeeDateUtil.format(date);
 		
 		//拿到本年yyyy日期
 		Calendar cal = Calendar.getInstance();
 		int thisYear = cal.get(Calendar.YEAR);
 		
 		StringBuffer hql = new StringBuffer("");
 		hql.append("      SELECT COUNT (id)");
 		hql.append("         FROM PunishBase where isApply=0");
 		if(status==4){
 			hql.append(" and closedDate is null");
 		}else{
 		
	 		hql.append(" and to_date(to_char(");
	 		if("day".equals(dayOrYear)){
	 			if(status==1){
	 				hql.append("filingDate");
	 			}else if(status==2){
	 				hql.append("punishmentDate");
	 			}else if(status==3){
	 				hql.append("closedDate");
	 			}
	 			hql.append(", 'yyyy-MM-dd'),'yyyy-MM-dd') = to_date('");
	 	 		hql.append(dateStr);
	 	 		hql.append("','yyyy-MM-dd')");
	 		}else if("year".equals(dayOrYear)){
	 			hql.append("filingDate, 'yyyy'),'yyyy') = to_date('");
	 			hql.append(thisYear);
	 			hql.append("','yyyy')");
	 		}
 		
 		}
 		if(deptId>0){
 			hql.append(" and departmentId="+deptId);
 		}
 		long result = simpleDaoSupport.count(hql.toString(), null);
 		return result;
 	}
 	
 	/**
 	 * 查询领导页面案件数(检查)
 	 * @param  dayOrYear  今日或年度
 	 * @param  deptId  部门ID  0为全部
 	 * @return  long
 	 */
 	public long getLeaderCountInspection(String dayOrYear, int deptId){
 		//拿到今天的日期
 		Date date = new Date();
 		String dateStr = TeeDateUtil.format(date);
 		
 		//拿到本年yyyy日期
 		Calendar cal = Calendar.getInstance();
 		int thisYear = cal.get(Calendar.YEAR);
 		
 		StringBuffer hql = new StringBuffer("");
 		hql.append("      SELECT COUNT (id)");
 		hql.append("         FROM PunishBase where sourceType in('20','30') ");
		if("day".equals(dayOrYear)){
			hql.append(" and to_date(");
			hql.append("substring(baseCode,0,8)");
			hql.append(", 'yyyyMMdd') = to_date('");
			hql.append(dateStr);
			hql.append("','yyyy-MM-dd')");
		}else if("year".equals(dayOrYear)){
			hql.append(" and to_date(");
			hql.append("substring(baseCode,0,4), 'yyyy') = to_date('");
			hql.append(thisYear);
			hql.append("','yyyy')");
		}
 		if(deptId>0){
 			hql.append(" and departmentId="+deptId);
 		}
 		long result = simpleDaoSupport.count(hql.toString(), null);
 		return result;
 	}
	
	/**
	 * 查询首页待办任务总数
	 * 
	 * @param queryModel
	 * @param request
	 * @return
	 */
	public long count(PunishBaseModel queryModel, HttpServletRequest request) {
		return punishBaseDao.count(queryModel, request);
	}
	
	/**
	 * 首页获取案件办理列表
	 */
	public List<PunishBase> listByHandling(int firstResult, int rows,PunishBaseModel queryModel, HttpServletRequest request) {
		return punishBaseDao.listByHandling(firstResult, rows, queryModel, request);
	}
	
	/**
	 * 首页获取任务接收列表总数
	 */
	public long countByHandling(PunishBaseModel queryModel, HttpServletRequest request) {
		return punishBaseDao.countByHandling(queryModel, request);
	}

 	/**
 	 * 查询领导页面案件数(上报)
 	 * @param  dept  1法制办，2文化部
 	 * @return  long
 	 */
 	public long getLeaderCountReport(int dept){
 		StringBuffer hql = new StringBuffer("");
 		hql.append("      SELECT COUNT (id)");
 		hql.append("         FROM PunishBase where 1=1 ");
 		if(dept == 1){
 			hql.append(" and reportOffice=1");
 		}else if(dept == 2){
 			hql.append(" and reportCulture=1");
 		}
 		long result = simpleDaoSupport.count(hql.toString(), null);
 		return result;
 	}
	/**
	 * 查询案件办结的总数根据部门分组
	 * CLOSED_DATE 结案时间   时间未空未结案
	 * IS_APPLY 是否立案    0 立案     1未立案
	 * @return
	 */
	public List getClosedCountByDept(HttpServletRequest request){
		Calendar cal = Calendar.getInstance();
 		//拿到本年yyyy日期
 		int thisYearStart = cal.get(Calendar.YEAR);
 		
		StringBuffer sql = new StringBuffer("");

		sql.append("SELECT  DEPARTMENT_ID namea,");
		sql.append("      COUNT (BASE_ID) AS num");
		sql.append("         FROM ZF_PUNISH_BASE");
		sql.append("         WHERE CLOSED_DATE IS NOT NULL AND IS_APPLY = 0");
		sql.append("         AND to_date(to_char(CLOSED_DATE, 'yyyy'),'yyyy') = to_date('");
		sql.append(thisYearStart);
 		sql.append("','yyyy')");
		sql.append("		GROUP BY DEPARTMENT_ID ORDER BY num DESC");
		List result = simpleDaoSupport.executeNativeQuery(sql.toString(), null,0, 10000);
		return result;
	}
	
	/**
	 * 查询案件未办结的总数根据部门分组
	 * CLOSED_DATE 结案时间   时间未空未结案
	 * IS_APPLY 是否立案    0 立案     1未立案
	 * @return
	 */
	public List getNotClosedCountByDept(HttpServletRequest request){
		StringBuffer sql = new StringBuffer("");

		sql.append("SELECT  DEPARTMENT_ID namea,");
		sql.append("      COUNT (BASE_ID) AS num");
		sql.append("         FROM ZF_PUNISH_BASE");
		sql.append("         WHERE CLOSED_DATE IS NULL AND IS_APPLY = 0");
		sql.append("		GROUP BY DEPARTMENT_ID ORDER BY num DESC");
		List result = simpleDaoSupport.executeNativeQuery(sql.toString(), null,0, 10000);
		return result;
	}
	
	/**
	 * 查询案件办结的总数根据领域分组
	 * CLOSED_DATE 结案时间   时间未空未结案
	 * IS_APPLY 是否立案    0 立案     1未立案
	 * @return
	 */
	public List getClosedCountByDomain(HttpServletRequest request){
		Calendar cal = Calendar.getInstance();
 		//拿到本年yyyy日期
 		int thisYearStart = cal.get(Calendar.YEAR);
 		
		StringBuffer sql = new StringBuffer("");

		sql.append("SELECT  DOMAIN_TYPE namea,");
		sql.append("      COUNT (BASE_ID) AS num");
		sql.append("         FROM ZF_PUNISH_BASE");
		sql.append("         WHERE CLOSED_DATE IS NOT NULL AND IS_APPLY = 0");
		sql.append("         AND to_date(to_char(CLOSED_DATE, 'yyyy'),'yyyy') = to_date('");
		sql.append(thisYearStart);
 		sql.append("','yyyy')");
		sql.append("		GROUP BY DOMAIN_TYPE ORDER BY num DESC");
		List result = simpleDaoSupport.executeNativeQuery(sql.toString(), null,0, 10000);
		return result;
	}
	
	/**
	 * 根据领域类型查询执法检查量
	 * SOURCE_TYPE 案件来源 20 双随机检查  30 现场检查
	 * @return
	 */
	public List getSourceByDomain(HttpServletRequest request){
		Calendar cal = Calendar.getInstance();
 		//拿到本年yyyy日期
 		int thisYearStart = cal.get(Calendar.YEAR);
 		
		StringBuffer sql = new StringBuffer("");

		sql.append("SELECT  DOMAIN_TYPE namea,");
		sql.append("      COUNT (BASE_ID) AS num");
		sql.append("         FROM ZF_PUNISH_BASE");
		sql.append("         WHERE SOURCE_TYPE = 20 OR SOURCE_TYPE = 30");
		sql.append("         AND SUBSTRING(BASE_CODE,0,4) = '");
		sql.append(thisYearStart);
		sql.append("'		GROUP BY DOMAIN_TYPE ORDER BY num DESC");
		List result = simpleDaoSupport.executeNativeQuery(sql.toString(), null,0, 10000);
		return result;
	}
	
	/**
	 * 根据部门查询执法检查量
	 * SOURCE_TYPE 案件来源 20 双随机检查  30 现场检查
	 * @return
	 */
	public List getSourceByDept(HttpServletRequest request){
		Calendar cal = Calendar.getInstance();
 		//拿到本年yyyy日期
 		int thisYearStart = cal.get(Calendar.YEAR);
 		
		StringBuffer sql = new StringBuffer("");

		sql.append("SELECT  DEPARTMENT_ID namea,");
		sql.append("      COUNT (BASE_ID) AS num");
		sql.append("         FROM ZF_PUNISH_BASE");
		sql.append("         WHERE SOURCE_TYPE = 20 OR SOURCE_TYPE = 30");
		sql.append("         AND SUBSTRING(BASE_CODE,0,4) = '");
		sql.append(thisYearStart);
		sql.append("'		GROUP BY DEPARTMENT_ID ORDER BY num DESC");
		List result = simpleDaoSupport.executeNativeQuery(sql.toString(), null,0, 10000);
		return result;
	}
	
	/**
	 * 获取案件列表（执法视频管理用）
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getListOfVideo(TeeDataGridModel dm, PunishBaseModel punishBaseModel) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		// 查询条件list
		List param = new ArrayList();
		// 基础hql
		StringBuffer hql = new StringBuffer(" from PunishBase where 1=1 ");
		if(TeeStringUtil.getInteger(punishBaseModel.getIsHasVideo(), 3)!=3){
			hql.append("and isHasVideo=" + punishBaseModel.getIsHasVideo());
		}
		if(!TeeUtility.isNullorEmpty(punishBaseModel.getBaseTitle())){
			hql.append("and baseTitle like '%" + punishBaseModel.getBaseTitle() + "%'");
		}
		long total = simpleDaoSupport.count("select count(id) " + hql,
				param.toArray());
		datagrid.setTotal(total);
		List<PunishBase> list = simpleDaoSupport.pageFind(hql.toString(),
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(),
				param.toArray());

		List<PunishBaseModel> models = new ArrayList<PunishBaseModel>();
		PunishBaseModel model = null;
		for (PunishBase row : list) {
			model = new PunishBaseModel();
			model = baseferModel(row);
			models.add(model);
		}
		datagrid.setRows(models);
		return datagrid;
	}
}
