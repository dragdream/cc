package com.tianee.oa.core.base.hr.training.record.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.base.hr.training.plan.bean.TeeTrainingPlan;
import com.tianee.oa.core.base.hr.training.plan.dao.TeeTrainingPlanDao;
import com.tianee.oa.core.base.hr.training.record.bean.TeeTrainingRecord;
import com.tianee.oa.core.base.hr.training.record.dao.TeeTrainingRecordDao;
import com.tianee.oa.core.base.hr.training.record.model.TeeTrainingRecordModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeTrainingRecordService extends TeeBaseService {

	@Autowired
	private TeeTrainingRecordDao trainingRecordDao;

	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeTrainingPlanDao planDao;

	
	public TeeJson addOrUpdateService(HttpServletRequest request, TeeTrainingRecordModel model) {

		TeeJson json = new TeeJson();
		int planId = TeeStringUtil.getInteger(model.getPlanId(), 0);
		TeeTrainingPlan plan = planDao.get(planId);
		if(model.getSid() > 0){
			TeeTrainingRecord record = trainingRecordDao.get(model.getSid());
			if(record != null){
				BeanUtils.copyProperties(model, record);
				record.setPlan(plan);
				List<TeePerson> personList = personDao.getPersonByUuids(model.getRecordUserId());//获取人员
				record.setRecordUser(personList.get(0));
				trainingRecordDao.update(record);
			}else{
				json.setRtState(false);
				json.setRtMsg("该培训记录已被删除！");
				return json;
			}
		}else{
			if(!TeeUtility.isNullorEmpty(model.getRecordUserId())){
				List<TeePerson> personList = personDao.getPersonByUuids(model.getRecordUserId());//获取人员
				for (int i = 0; i <personList.size(); i++) {
					TeeTrainingRecord record = new TeeTrainingRecord();
					BeanUtils.copyProperties(model, record);
					record.setPlan(plan);
					record.setCreateTime(new Date());
					record.setRecordUser(personList.get(i));
					trainingRecordDao.save(record);
				}
			}
		}
		json.setRtState(true);
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
	public TeeEasyuiDataGridJson getRecordList(TeeDataGridModel dm, HttpServletRequest request, TeeTrainingRecordModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		Map requestDatas = TeeServletUtility.getParamMap(request);
	

		String hql = "from TeeTrainingRecord record where 1=1";
		List param = new ArrayList();
		if (!TeeUtility.isNullorEmpty(model.getPlanId())) {//培训计划
			hql += " and record.plan.sid =?";
			param.add(TeeStringUtil.getInteger(model.getPlanId(), 0));
		}
		
		if (!TeeUtility.isNullorEmpty(model.getRecordUserId())) {//培训人
			hql += " and record.recordUser.uuid = ?";
			param.add(TeeStringUtil.getInteger(model.getRecordUserId(), 0));
		}

		if (!TeeUtility.isNullorEmpty(model.getRecordInstitution())) {//培训机构
			hql += " and record.recordInstitution like ?";
			param.add("%" +  model.getRecordInstitution()+ "%");
		}
		if (!TeeUtility.isNullorEmpty((String)requestDatas.get("recordCost"))) {//培训费用
			hql += " and record.recordCost = ?";
			param.add(TeeStringUtil.getDouble(model.getRecordCost() , 0));
		}

	
		// j.setTotal(trainingPlanDao.getQueryCount(loginPerson ,model));//
		// 设置总记录数
		j.setTotal(trainingRecordDao.countByList("select count(*) " + hql, param));// 设置总记录数

		hql += " order by record.createTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		// List<TeeTrainingPlan> list =
		// trainingPlanDao.getMeetPageFind(firstIndex, dm.getRows(), dm,
		// model);// 查
		List<TeeTrainingRecord> list = trainingRecordDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeTrainingRecordModel> modelList = new ArrayList<TeeTrainingRecordModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeTrainingRecordModel modeltemp = parseModel(list.get(i) , true);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	/**
	 * 转模型
	 * @author syl
	 * @date 2014-6-21
	 * @param record
	 * @return
	 */
	public TeeTrainingRecordModel parseModel(TeeTrainingRecord record , boolean isSimple){
		TeeTrainingRecordModel model = new TeeTrainingRecordModel();
		if(record != null){
			BeanUtils.copyProperties(record, model);
			if(record.getPlan() != null){
				model.setPlanId(record.getPlan().getSid() + "");
				model.setPlanName(record.getPlan().getPlanName());
			}
			if(record.getRecordUser() != null){
				model.setRecordUserId(record.getRecordUser().getUuid() + "");
				model.setRecordUserName(record.getRecordUser().getUserName() );
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			model.setCreateTimeDesc(TeeUtility.getDateTimeStr(record.getCreateTime(), dateFormat));
			
		}
		return model;
	}
	
	/**
	 * 获取信息
	 * @author syl
	 * @date 2014-6-21
	 * @param request
	 * @return
	 */
	public TeeJson getById( HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeTrainingRecordModel model = new TeeTrainingRecordModel();
		TeeTrainingRecord  record = trainingRecordDao.get(sid);
		if(record != null){
			model = parseModel(record, false);
		}
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	/**
	 * 单个删除
	 * @author syl
	 * @date 2014-6-21
	 * @param request
	 * @return
	 */
	public TeeJson deleteById( HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		trainingRecordDao.delete(sid);
		json.setRtState(true);
		return json;
	}
	/**
	 * byIds  多个删除
	 * @author syl
	 * @date 2014-6-21
	 * @param request
	 * @return
	 */
	public TeeJson deleteByIds( HttpServletRequest request){
		TeeJson json = new TeeJson();
		String sids = TeeStringUtil.getString(request.getParameter("sid"));
		trainingRecordDao.delByIds(sids);
		json.setRtState(true);
		return json;
	}
	
	
}
