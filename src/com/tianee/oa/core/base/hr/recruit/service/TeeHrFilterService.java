package com.tianee.oa.core.base.hr.recruit.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.base.hr.recruit.bean.TeeHrFilter;
import com.tianee.oa.core.base.hr.recruit.bean.TeeHrFilterItem;
import com.tianee.oa.core.base.hr.recruit.bean.TeeHrPool;
import com.tianee.oa.core.base.hr.recruit.dao.TeeHrFilterDao;
import com.tianee.oa.core.base.hr.recruit.dao.TeeHrPoolDao;
import com.tianee.oa.core.base.hr.recruit.model.TeeHrFilterItemModel;
import com.tianee.oa.core.base.hr.recruit.model.TeeHrFilterModel;
import com.tianee.oa.core.base.hr.recruit.plan.bean.TeeRecruitPlan;
import com.tianee.oa.core.base.hr.recruit.plan.dao.TeeRecruitPlanDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserGroup;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeHrFilterService extends TeeBaseService {
	@Autowired
	private TeeHrFilterDao hrFilterDao;

	@Autowired
	private TeeDeptDao deptDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeRecruitPlanDao recruitPlanDao;
	
	@Autowired
	private TeeHrPoolDao hrPoolDao;
	

	public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public TeeJson addOrUpdate(HttpServletRequest request,
			TeeHrFilterModel model) throws IOException {
		TeeJson json = new TeeJson();
		try {
			TeePerson person = (TeePerson) request.getSession().getAttribute(
					TeeConst.LOGIN_USER);
			
			int planId = TeeStringUtil.getInteger(model.getPlanId(), 0);//招聘计划
			TeeRecruitPlan plan = null ;
			if(planId > 0){
				plan= recruitPlanDao.get(planId);
			}
			int poolId = TeeStringUtil.getInteger(model.getHrPoolId(), 0);//人才库
			TeeHrPool pool= null;
			if(poolId > 0){
				pool = hrPoolDao.get(poolId);
			}
			int tranUserId = TeeStringUtil.getInteger(model.getNextTransactorId(), 0);//下一个筛选人
			TeePerson nextTransactor  = null;
			if(tranUserId > 0){
				nextTransactor = personDao.get(tranUserId);
			}
			if (model.getSid() > 0) {
				TeeHrFilter obj = hrFilterDao.get(model.getSid());
				BeanUtils.copyProperties(model, obj);
				obj.setHrPool(pool);
				obj.setPlan(plan);
				obj.setSendPerson(person);
				obj.setFilterState("0");
				obj.setNextTransactor(nextTransactor);
				hrFilterDao.update(obj);	
			} else {
				TeeHrFilter obj = new TeeHrFilter();
				BeanUtils.copyProperties(model, obj);
				obj.setCreateTime(new Date());
				obj.setHrPool(pool);
				obj.setPlan(plan);
				obj.setSendPerson(person);
				obj.setFilterState("0");
				obj.setNextTransactor(nextTransactor);
				hrFilterDao.save(obj);	
			}
			json.setRtState(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

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
	public TeeEasyuiDataGridJson getHrFilterList(TeeDataGridModel dm,
			HttpServletRequest request, TeeHrFilterModel model) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		String hql = "from TeeHrFilter filter where 1 = 1";
		List param = new ArrayList();
		if(!TeePersonService.checkIsSuperAdmin(loginPerson, "")){//不是超级管理员
			hql = hql + " and (filter.sendPerson.uuid = ? or" +
					" exists (select 1 from filter.filterItem  item where item.nextTransactorStep.uuid = ?) " +
					" or filter.nextTransactor.uuid = ?) " ;
			param.add(loginPerson.getUuid());	
			param.add(loginPerson.getUuid());	
			param.add(loginPerson.getUuid());	
			
		}
		// 设置总记录数
		j.setTotal(hrFilterDao.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by filter.createTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		// List<TeeHrFilter> list =
		// hrPoolDao.getMeetPageFind(firstIndex, dm.getRows(), dm,
		// model);// 查
		List<TeeHrFilter> list = hrFilterDao.pageFindByList(hql,
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeHrFilterModel> modelList = new ArrayList<TeeHrFilterModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeHrFilterModel modeltemp = parseModel(list.get(i) , true);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	/**
	 * 通用列表  查询
	 * 
	 * @param dm
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson queryHrFilterList(TeeDataGridModel dm,
			HttpServletRequest request, TeeHrFilterModel model) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		String hql = "from TeeHrFilter filter where 1 = 1";
		List param = new ArrayList();
		if(!TeePersonService.checkIsSuperAdmin(loginPerson, "")){//不是超级管理员
			//自己发起的，需要筛选的， 自己筛选过的
			hql = hql + " and (filter.sendPerson.uuid = ? or" +
					" exists (select 1 from filter.filterItem  item where item.nextTransactorStep.uuid = ?) "
					+" or filter.nextTransactor.uuid = ?) " ;
			param.add(loginPerson.getUuid());	
			param.add(loginPerson.getUuid());	
			param.add(loginPerson.getUuid());		
		}
		if(!TeeUtility.isNullorEmpty(model.getHrPoolName())){
			hql = hql + " and filter.hrPool.employeeName like ?" ;
			param.add("%" + model.getHrPoolName() + "%");
		}
		int planId = TeeStringUtil.getInteger(model.getPlanId() , 0);
		if(planId > 0){
			hql = hql + " and filter.plan.sid = ?" ;
			param.add(planId);
		}
		int sendPerson = TeeStringUtil.getInteger(model.getSendPersonId() , 0);
		if(sendPerson > 0){
			hql = hql + " and filter.sendPerson.uuid = ?" ;
			param.add(sendPerson);
		}
		
		if(!TeeUtility.isNullorEmpty(model.getEmployeeMajor())){
			hql = hql + " and filter.employeeMajor = ?" ;
			param.add("%" + model.getEmployeeMajor() + "%");
		}
		
		if(!TeeUtility.isNullorEmpty(model.getEmployeePhone())){
			hql = hql + " and filter.employeePhone = ?" ;
			param.add("%" + model.getEmployeePhone() + "%");
		}
		if(!TeeUtility.isNullorEmpty(model.getFilterState())){
			hql = hql + " and filter.filterState = ?" ;
			param.add(model.getFilterState());
		}
		
		// 设置总记录数
		j.setTotal(hrFilterDao.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by filter.createTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		// List<TeeHrFilter> list =
		// hrPoolDao.getMeetPageFind(firstIndex, dm.getRows(), dm,
		// model);// 查
		List<TeeHrFilter> list = hrFilterDao.pageFindByList(hql,
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeHrFilterModel> modelList = new ArrayList<TeeHrFilterModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeHrFilterModel modeltemp = parseModel(list.get(i) , true);
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
	public TeeHrFilterModel parseModel(TeeHrFilter obj ,boolean isSimple) {
		TeeHrFilterModel model = new TeeHrFilterModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);
        if(!isSimple){

        }
        //招聘计划
        TeeRecruitPlan plan =  obj.getPlan();
        if(plan != null){
        	model.setPlanId(plan.getSid() + "");
        	model.setPlanName(plan.getPlanName());
        }
        
        //人才库
        TeeHrPool pool = obj.getHrPool();
        String poolId = "";
        String poolName = "";
        if(pool != null){
        	poolId = pool.getSid() + "";
        	poolName = pool.getEmployeeName();
        }
        model.setHrPoolId(poolId);
        model.setHrPoolName(poolName);
        model.setSendPersonId(obj.getSendPerson().getUuid() + "");
        model.setSendPersonName(obj.getSendPerson().getUserName());
        model.setNextTransactorId(obj.getNextTransactor().getUuid() + "");
        model.setNextTransactorName(obj.getNextTransactor().getUserName());
        
        if(obj.getNextDatetime() != null){
        	model.setNextDatetimeStr(TeeDateUtil.format(obj.getNextDatetime(), "yyyy-MM-dd HH:mm:ss"));
        }
        
        List<TeeHrFilterItemModel> itemModeList = new ArrayList<TeeHrFilterItemModel>(); 
        if(!isSimple){//筛选记录
        	List<TeeHrFilterItem> listItem = obj.getFilterItem();
        	for (int i = 0; i < listItem.size(); i++) {
        	 	TeeHrFilterItemModel itemModel = TeeHrFilterItemService.parseModel(listItem.get(i), isSimple);
        	 	itemModeList.add(itemModel);
        	}
        }
        model.setItemModelList(itemModeList);
		return model;
	}
	/**
	 * 根据Id 查询详情
	 * @author syl
	 * @date 2014-6-27
	 * @param model
	 * @return
	 */
	public TeeJson getByIdInfo(TeeHrFilterModel model){
		TeeJson json = new TeeJson();
		TeeHrFilter filter = hrFilterDao.get(model.getSid());
		model = parseModel(filter, false);
		json.setRtState(true);
		json.setRtData(model);
		return json;
	}
	
	
	/**
	 * 删除信息
	 * 
	 * @date 2014年5月27日
	 * @author
	 * @param sids
	 * @return
	 */
	public TeeJson deleteObjById(String sids) {
		TeeJson json = new TeeJson();
		//处理筛选项目
		String ids [] = TeeStringUtil.parseStringArray(sids);
		TeeHrFilter filter = null;
		for(String id:ids){
			if("".equals(id)){
				continue;
			}
			filter = (TeeHrFilter) simpleDaoSupport.get(TeeHrFilter.class, Integer.parseInt(id));
			simpleDaoSupport.deleteByObj(filter);
		}
//		hrFilterDao.deleteByIds(sids);
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}


}
