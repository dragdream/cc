package com.tianee.oa.core.base.hr.settings.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.hr.settings.bean.TeeAnnualLeave;
import com.tianee.oa.core.base.hr.settings.dao.TeeAnnualLeaveDao;
import com.tianee.oa.core.base.hr.settings.model.TeeAnnualLeaveModel;
import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.core.general.dao.TeeSysParaDao;
import com.tianee.oa.core.general.service.TeeSysParaService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeAnnualLeaveService extends TeeBaseService {
	@Autowired
	private TeeAnnualLeaveDao dao;
	@Autowired
	private TeeSysParaDao sysParaDao;
	
	@Autowired
	TeeSysParaService sysParaServ;

	/**
	 * @function: 新建或编辑
	 * @author:
	 * @data: 2014年8月29日
	 * @param request
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson addOrUpdateService(HttpServletRequest request, TeeAnnualLeaveModel model) {
		TeeJson json = new TeeJson();
		try {
			TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			String delIdStr = request.getParameter("delIdStr");
			String annualLeaveListStr = request.getParameter("annualLeaveList");
			
			String hrAnnualLeaveRuleOpen = request.getParameter("hrAnnualLeaveRuleOpen");
			String hrAnnualLeaveType = request.getParameter("hrAnnualLeaveType");
			
			if(!TeeUtility.isNullorEmpty(hrAnnualLeaveRuleOpen)){
				TeeSysPara para = new TeeSysPara ();
				para.setParaName("HR_ANNUAL_LEAVE_RULE_OPEN");
				para.setParaValue(hrAnnualLeaveRuleOpen);
				sysParaServ.addUpdatePara(para);
			}
			if(!TeeUtility.isNullorEmpty(hrAnnualLeaveType)){
				TeeSysPara para = new TeeSysPara ();
				para.setParaName("HR_ANNUAL_LEAVE_TYPE");
				para.setParaValue(hrAnnualLeaveType);
				sysParaServ.addUpdatePara(para);
			}
			
			//先清除所有之前的信息
			simpleDaoSupport.executeUpdate("delete from TeeAnnualLeave", null);
			
			if (!TeeUtility.isNullorEmpty(annualLeaveListStr)) {
				List<TeeAnnualLeave> annualLeaveList = (List<TeeAnnualLeave>) TeeJsonUtil.JsonStr2ObjectList(annualLeaveListStr, TeeAnnualLeave.class);
				if (annualLeaveList != null && annualLeaveList.size() > 0) {
					for (TeeAnnualLeave annualLeave : annualLeaveList) {
						annualLeave.setCreateTime(new Date());
						annualLeave.setCreateUser(person);
						dao.save(annualLeave);
					}
				}
			}

			json.setRtState(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * @function: 返回全部数据集合
	 * @author: wyw
	 * @data: 2014年9月4日
	 * @return TeeJson
	 */
	public TeeJson getManageList() {
		TeeJson json = new TeeJson();
		List<TeeAnnualLeave> list = dao.executeQuery("from TeeAnnualLeave order by yearCount asc", null);
		List<TeeAnnualLeaveModel> modelList = new ArrayList<TeeAnnualLeaveModel>();
		if (list != null && list.size()>0) {
			for(TeeAnnualLeave annualLeave:list){
				TeeAnnualLeaveModel model = parseReturnModel(annualLeave);
				model.setDefaultFlag("0");
				modelList.add(model);
			}
		}
		json.setRtData(modelList);
		json.setRtState(true);
		json.setRtMsg("查询成功!");
		return json;
	}

	/**
	 * @function: 转换成返回对象
	 * @author: wyw
	 * @data: 2014年8月29日
	 * @param obj
	 * @return TeeAnnualLeaveModel
	 */
	public TeeAnnualLeaveModel parseReturnModel(TeeAnnualLeave obj) {
		TeeAnnualLeaveModel model = new TeeAnnualLeaveModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);

		if (!TeeUtility.isNullorEmpty(obj.getCreateTime())) {
			model.setCreateTimeStr(TeeUtility.getDateStrByFormat(obj.getCreateTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
		}
		if (obj.getCreateUser() != null) {
			model.setCreateUserId(obj.getCreateUser().getUuid());
			model.setCreateUserName(obj.getCreateUser().getUserName());
		}
		model.setVacationDays(obj.getVacationDays());
		model.setYearCount(obj.getYearCount());
		model.setDefaultFlag(obj.getDefaultFlag());
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
	public TeeJson getInfoById(HttpServletRequest request, TeeAnnualLeaveModel model) {
		TeeJson json = new TeeJson();
		String isEdit = request.getParameter("isEdit");
		boolean editFlag = false;
		if ("1".equals(isEdit)) {
			editFlag = true;
		}
//		if ("".equals(arg0)model.getUuid()) {
//			TeeAnnualLeave obj = dao.get(model.getSid());
//			if (obj != null) {
//				model = parseReturnModel(obj);
//				json.setRtData(model);
//				json.setRtState(true);
//				json.setRtMsg("查询成功!");
//				return json;
//			}
//		}
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

}
