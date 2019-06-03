package com.tianee.oa.subsys.budget.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.subsys.budget.bean.TeeBudgetReg;
import com.tianee.oa.subsys.budget.dao.TeeBudgetRegDao;
import com.tianee.oa.subsys.budget.dao.TeeDeptBudgetDao;
import com.tianee.oa.subsys.budget.dao.TeeUserBudgetDao;
import com.tianee.oa.subsys.budget.model.TeeBudgetRegModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeBudgetRegService extends TeeBaseService {
	@Autowired
	private TeeBudgetRegDao dao;

	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeDeptDao deptDao;
	
	@Autowired
	private TeeUserBudgetDao userBudgetDao;
	
	@Autowired
	private TeeDeptBudgetDao deptBudgetDao;

	/**
	 * @function: 新建数据
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 * @throws ParseException
	 */
	public TeeJson addObj(Map requestMap, TeePerson loginPerson, TeeBudgetRegModel model) throws ParseException {
		TeeJson json = new TeeJson();
		
		TeeBudgetReg obj = new TeeBudgetReg();
		
		boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);
		if(isAdmin){
			TeePerson personObj = personDao.get(model.getOpUserId());
			obj.setOpUser(personObj);
			TeeDepartment deptObj = deptDao.get(model.getOpDeptId());
			obj.setOpDept(deptObj);
		}else{
			obj.setOpUser(loginPerson);
			TeeDepartment deptObj = deptDao.get(model.getOpDeptId());
			obj.setOpDept(deptObj);
		}
		
		obj.setCrTime(new Date());
		obj.setAmount(model.getAmount());
		obj.setReason(model.getReason());
		obj.setRegType(model.getRegType());
		obj.setRemark(model.getRemark());
		obj.setType(model.getType());
		dao.save(obj);
		json.setRtState(true);
		json.setRtMsg("保存成功！");
		return json;
	}
	/**
	 * @function: 编辑数据
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 * @throws ParseException
	 */
	public TeeJson updateObj(Map requestMap, TeePerson loginPerson, TeeBudgetRegModel model) throws ParseException {
		TeeJson json = new TeeJson();
		
		if(!TeeUtility.isNullorEmpty(model.getUuid())){
			TeeBudgetReg obj = dao.get(model.getUuid());
			
			boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);
			if(isAdmin){
				TeePerson personObj = personDao.get(model.getOpUserId());
				obj.setOpUser(personObj);
				TeeDepartment deptObj = deptDao.get(model.getOpDeptId());
				obj.setOpDept(deptObj);
			}else{
				obj.setOpUser(loginPerson);
				TeeDepartment deptObj = deptDao.get(model.getOpDeptId());
				obj.setOpDept(deptObj);
			}
			
			obj.setAmount(model.getAmount());
			obj.setReason(model.getReason());
			obj.setRegType(model.getRegType());
			obj.setRemark(model.getRemark());
			obj.setType(model.getType());
			dao.update(obj);
			
		}
		json.setRtState(true);
		json.setRtMsg("保存成功！");
		return json;
	}
	
	/**
	 * @function: 管理列表
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param dm
	 * @param requestDatas
	 * @param model
	 * @return
	 * @throws ParseException
	 *             TeeEasyuiDataGridJson
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getManageInfoList(TeeDataGridModel dm, Map requestDatas,TeePerson loginPerson, TeeBudgetRegModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();

		boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);
		
		String regType = (String) requestDatas.get("regType");
		String type = (String) requestDatas.get("type");
		String opUserId = (String) requestDatas.get("opUserId");
		String opDeptId = (String) requestDatas.get("opDeptId");
		String amountMax = (String) requestDatas.get("amountMax");
		String amountMin = (String) requestDatas.get("amountMin");
		if("1".equals(regType)){
			opDeptId = "";
		}else{
			opUserId = "";
		}
		
		String queryStr = " 1=1";
		if(!isAdmin){
			queryStr = " opUser.uuid=" + loginPerson.getUuid();
		}
		String hql = "from TeeBudgetReg where " + queryStr;
		List param = new ArrayList();
		if (!TeeUtility.isNullorEmpty(regType)) {
			hql += " and regType = ?";
			param.add(Integer.parseInt(regType));
		}
		if (!TeeUtility.isNullorEmpty(type)) {
			hql += " and type = ?";
			param.add(Integer.parseInt(type));
		}
		if (!TeeUtility.isNullorEmpty(opUserId)) {
			hql += " and opUser.uuid = ?";
			param.add(Integer.parseInt(opUserId));
		}
		if (!TeeUtility.isNullorEmpty(opDeptId)) {
			hql += " and opDept.uuid = ?";
			param.add(Integer.parseInt(opDeptId));
		}
		if (!TeeUtility.isNullorEmpty(amountMax)) {
			hql += " and amount >?";
			param.add(TeeUtility.parseDouble(amountMax));
		}
		if (!TeeUtility.isNullorEmpty(amountMin)) {
			hql += " and amount <?";
			param.add(TeeUtility.parseDouble(amountMin));
		}

		
		j.setTotal(dao.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by crTime desc";
		List<TeeBudgetReg> list = dao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeBudgetRegModel> modelList = new ArrayList<TeeBudgetRegModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeBudgetRegModel modeltemp = parseReturnModel(list.get(i));
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	
	
	/**
	 * @function: 转换成返回对象
	 * @author: wyw
	 * @data: 2014年8月29日
	 * @param obj
	 * @return TeeCrmProductsModel
	 */
	public TeeBudgetRegModel parseReturnModel(TeeBudgetReg obj) {
		TeeBudgetRegModel model = new TeeBudgetRegModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);
		
		model.setAmount(obj.getAmount());
		model.setReason(obj.getReason());
		model.setReasonDesc(TeeSysCodeManager.getChildSysCodeNameCodeNo("BUDGET_REG_REASON", obj.getReason()));
		model.setRegType(obj.getRegType());
		model.setRemark(obj.getRemark());
		model.setType(obj.getType());
		
		if (!TeeUtility.isNullorEmpty(obj.getCrTime())) {
			model.setCrTimeDesc(TeeUtility.getDateStrByFormat(obj.getCrTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
		}

		if (obj.getOpUser() != null) {
			model.setOpUserId(obj.getOpUser().getUuid());
			model.setOpUserName(obj.getOpUser().getUserName());
		}
		if (obj.getOpDept() != null) {
			model.setOpDeptId(obj.getOpDept().getUuid());
			model.setOpDeptName(obj.getOpDept().getDeptName());
		}
		return model;
	}
	
	
	/**
	 * @function: 根据sid查看详情
	 * @author: wyw
	 * @data: 2014年8月29日
	 * @param request
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson getInfoById(Map requestMap, TeePerson loginPerson, TeeBudgetRegModel model) {
		TeeJson json = new TeeJson();
		if (!TeeUtility.isNullorEmpty(model.getUuid())) {
			TeeBudgetReg obj = dao.get(model.getUuid());
			if (obj != null) {
				model = parseReturnModel(obj);
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
	 * @function: 删除
	 * @author: wyw
	 * @data: 2014年8月30日
	 * @param sids
	 * @return TeeJson
	 */
	public TeeJson deleteObjById(String sids) {
		TeeJson json = new TeeJson();

		dao.delByIds(sids);
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	public TeeJson getCurRegUserInfoAbout(int userId) {
		TeeJson json = new TeeJson();
		Map data = new HashMap();
		
		TeePerson person = personDao.get(userId);
		List<TeeDepartment> list = new ArrayList();
		list.add(person.getDept());
		list.addAll(person.getDeptIdOther());
		
		Calendar c = Calendar.getInstance();
		
		//个人当月预算
		double used = userBudgetDao.getRegBudgetCost(userId, c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1);
		double total = userBudgetDao.getUserBudgetCost(userId, c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1);
		data.put("personalRemain", total-used);
		
		//部门当月预算
		for(TeeDepartment dept:list){
			used = deptBudgetDao.getRegBudgetCost(dept.getUuid(),c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1);
			total= deptBudgetDao.getDeptBudgetCost(dept.getUuid(), c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1);
			data.put(dept.getDeptName(), total-used);
		}
		
		json.setRtData(data);
		json.setRtState(true);
		return json;
	}
}
