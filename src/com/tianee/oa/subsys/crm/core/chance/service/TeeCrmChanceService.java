package com.tianee.oa.subsys.crm.core.chance.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.base.bean.TeeCrmAfterSaleServ;
import com.tianee.oa.subsys.crm.core.base.model.TeeCrmAfterSaleServModel;
import com.tianee.oa.subsys.crm.core.chance.bean.TeeCrmChance;
import com.tianee.oa.subsys.crm.core.chance.dao.TeeCrmChanceDao;
import com.tianee.oa.subsys.crm.core.chance.model.TeeCrmChanceModel;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomerInfo;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeCrmChanceService extends TeeBaseService {
	@Autowired
	private TeeCrmChanceDao dao;

	/**
	 * 获取客户机会列表
	 * 
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeEasyuiDataGridJson getChanceList(TeeDataGridModel dm,
			HttpServletRequest request, TeeCrmChanceModel model) {
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		// hql查询语句
		String hql = "from TeeCrmChance c where c.crUser.uuid="
				+ loginPerson.getUuid();
		// 设置总记录数
		json.setTotal(dao.countByList("select count(*) " + hql, null));

		int firstIndex = 0;
		// 获取开始索引位置
		firstIndex = (dm.getPage() - 1) * dm.getRows();
		List<TeeCrmChance> list = dao.pageFindByList(hql, (dm.getPage() - 1)
				* dm.getRows(), dm.getRows(), null);

		List<TeeCrmChanceModel> modelList = new ArrayList<TeeCrmChanceModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeCrmChanceModel modeltemp = parseReturnModel(list.get(i),
						false);
				modelList.add(modeltemp);
			}
		}
		json.setRows(modelList);// 设置返回的行

		return json;
	}

	/**
	 * 转换成返回对象
	 * 
	 * @param obj
	 * @param isEdit
	 * @return
	 */
	public TeeCrmChanceModel parseReturnModel(TeeCrmChance obj, boolean isEdit) {

		TeeCrmChanceModel model = new TeeCrmChanceModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);

		if (obj.getCrUser() != null && obj.getCrUser().getUuid() != 0) {
			model.setCrUserId(obj.getCrUser().getUuid());
			model.setCrUserName(obj.getCrUser().getUserName());
		}

		if (obj.getCustomer() != null && obj.getCustomer().getSid() != 0) {
			model.setCustomerId(obj.getCustomer().getSid());
			model.setCustomerName(obj.getCustomer().getCustomerName());
		}

		String createTime = TeeDateUtil.format(obj.getCreateTime().getTime(),
				"yyyy-MM-dd");
		model.setCreateTimeStr(createTime);

		String forcastTime = TeeDateUtil.format(obj.getForcastTime().getTime(),
				"yyyy-MM-dd");
		model.setForcastTimeStr(forcastTime);

		return model;
	}

	/**
	 * 删除单个
	 * 
	 * @param sid
	 * @return
	 */
	public TeeJson deleteChanceById(int sid) {
		TeeJson json = new TeeJson();
		TeeCrmChance chance = new TeeCrmChance();
		chance.setSid(sid);
		dao.deleteByObj(chance);
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}

	/**
	 * 根据id获取详情
	 * 
	 * @param sid
	 * @return
	 */
	public TeeJson getChanceById(int sid) {

		TeeJson json = new TeeJson();
		TeeCrmChance chance = dao.get(sid);

		TeeCrmChanceModel model = parseReturnModel(chance, false);
		json.setRtData(model);
		json.setRtMsg(null);
		json.setRtState(true);
		return json;
	}

	/**
	 * 批量删除
	 * 
	 * @param sidStr
	 * @return
	 */
	public TeeJson deleteChance(String sidStr) {
		if (TeeUtility.isNullorEmpty(sidStr)) {
			sidStr = "0";
		}
		if (sidStr.endsWith(",")) {
			sidStr = sidStr.substring(0, sidStr.length() - 1);
		}
		TeeJson json = new TeeJson();

		Object values[] = {};
		String hql = " from TeeCrmChance where sid in (" + sidStr + ")";
		List<TeeCrmChance> list = dao.executeQuery(hql, values);
		if (list != null && list.size() > 0) {
			for (TeeCrmChance obj : list) {
				dao.deleteByObj(obj);
			}
		}
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}

	/**
	 * 增加或者编辑
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdateService(HttpServletRequest request,
			TeeCrmChanceModel model) {

		TeeJson json = new TeeJson();
		try {
			TeePerson person = (TeePerson) request.getSession().getAttribute(
					TeeConst.LOGIN_USER);
			if (model.getSid() > 0) {

				TeeCrmChance obj = dao.get(model.getSid());
				if (obj != null) {
					this.parseObj(model, obj);
					obj.setCrUser(person);
					Calendar c = Calendar.getInstance();
					obj.setCreateTime(c);
					dao.update(obj);
				}
			} else {
				TeeCrmChance obj = new TeeCrmChance();

				this.parseObj(model, obj);

				obj.setCrUser(person);

				Calendar c = Calendar.getInstance();
				obj.setCreateTime(c);

				dao.save(obj);

			}

			json.setRtState(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 转换成保存对象
	 * 
	 * @param model
	 * @param obj
	 * @return
	 */
	public TeeCrmChance parseObj(TeeCrmChanceModel model, TeeCrmChance obj) {
		if (model == null) {
			return obj;
		}
		try {
			BeanUtils.copyProperties(model, obj);

			obj.setCreateTime(TeeDateUtil.parseCalendar(model
					.getCreateTimeStr()));
			obj.setForcastTime(TeeDateUtil.parseCalendar(model
					.getForcastTimeStr()));

			TeeCrmCustomerInfo customer = new TeeCrmCustomerInfo();
			customer.setSid(model.getCustomerId());

			obj.setCustomer(customer);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 获取共享机会列表
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeEasyuiDataGridJson getShareChanceList(TeeDataGridModel dm,
			HttpServletRequest request, TeeCrmChanceModel model) {
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		// hql查询语句
		//String hql = " from TeeCrmChance c where 1 =1 and crUser.uuid <> " + loginPerson.getUuid() +  " and exists(select 1 from c.customer.sharePerson sharePerson where sharePerson.uuid="+loginPerson.getUuid()+")";
		String hql = " from TeeCrmChance c,TeeCrmCustomerInfo customer where c.customer=customer and exists (select 1 from customer.sharePerson sharePerson where sharePerson.uuid="+loginPerson.getUuid()+")";
		// 设置总记录数
		json.setTotal(dao.countByList("select count(*) " + hql, null));

		List<TeeCrmChance> list = dao.pageFindByList("select c "+hql, (dm.getPage() - 1)
				* dm.getRows(), dm.getRows(), null);

		List<TeeCrmChanceModel> modelList = new ArrayList<TeeCrmChanceModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeCrmChanceModel modeltemp = parseReturnModel(list.get(i),
						false);
				modelList.add(modeltemp);
			}
		}
		json.setRows(modelList);// 设置返回的行

		return json;
	}

}
