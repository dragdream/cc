package com.tianee.oa.core.weekPlan.service;

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

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.tpl.bean.TeePubTemplate;
import com.tianee.oa.core.weekPlan.bean.TeeWeekPlan;
import com.tianee.oa.core.weekPlan.dao.TeeWeekPlanDao;
import com.tianee.oa.core.weekPlan.model.TeeWeekPlanModel;
import com.tianee.oa.core.xt.bean.TeeXTRun;
import com.tianee.oa.core.xt.model.TeeXTRunModel;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeWeekPlanService  extends TeeBaseService{

	@Autowired
	private TeeWeekPlanDao weekPlanDao;
	
	@Autowired
	private TeeDeptDao deptDao;
	
	@Autowired
	private TeePersonDao personDao;

	
	/**
	 * 新建/编辑
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request,TeeWeekPlanModel model) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    TeeJson json=new TeeJson();
	    //获取当前登陆人
	    TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    //获取页面上传来的主键
	    int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
	    TeeWeekPlan weekPlan=null;
	    if(sid>0){//编辑
	    	weekPlan=(TeeWeekPlan) weekPlanDao.get(TeeWeekPlan.class,sid);
	    	
	    	weekPlan.setCreateUserId(loginUser);
	    	weekPlan.setContent(model.getContent());
	    	weekPlan.setTittle(model.getTittle());
	    	weekPlan.setDepartmentNo(loginUser.getDept().getDeptNo());
	    	weekPlan.setTittle(model.getTittle());
	    	weekPlan.setSubmitTime(model.getSubmitTime());
	    /*	//处理发布后 进行时间
			if(weekPlan.getPublishTime()!=null){
				model.setPublishTime(sdf.format(weekPlan.getPublishTime()));
			}*/
			weekPlan.setPostilContent(model.getPostilContent());
	    	
            
            //更新数据库
	    	weekPlanDao.update(weekPlan);
	    	
	    }else{//保存
	    	weekPlan= new TeeWeekPlan();
	    	weekPlan.setCreateUserId(loginUser);
	    	weekPlan.setContent(model.getContent());
	    	weekPlan.setDepartmentNo(loginUser.getDept().getDeptNo());
	    	weekPlan.setTittle(model.getTittle());
	    	// 提交
	    	weekPlan.setStatus(0);
	    	weekPlan.setSubmitTime(model.getSubmitTime());
	    	//添加到数据库
	    	weekPlanDao.save(weekPlan);
	    }
	    	json.setRtState(true);
			return json;
	}
	
	/**
	 * 删除计划
	 * @param request
	 * @return
	 */
	public TeeJson delete(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeWeekPlan weekPlan=(TeeWeekPlan) weekPlanDao.get(TeeWeekPlan.class,sid);;
		    if(weekPlan!=null){
		    	weekPlanDao.delete(sid);
		    }
		json.setRtState(true);
		json.setRtMsg("删除成功！");
		return json;
	}
	
	/**
	 * 发布周计划
	 * @param request
	 * @param model
	 */
	public TeeJson publishWeekPlan(HttpServletRequest request,TeeWeekPlanModel model){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    TeeJson json=new TeeJson();
	    int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		
		TeeWeekPlan weekPlan=(TeeWeekPlan) weekPlanDao.get(TeeWeekPlan.class,sid);
		    if(weekPlan!=null){
		    	Date date = new Date();
		    	weekPlan.setPublishTime(date);
		    	weekPlan.setStatus(1);
		    	weekPlanDao.update(weekPlan);
		    
		}
		json.setRtState(true);
		json.setRtMsg("发布成功！");
		return json;
	
	}
	
	/**
	 * 根据状态以及用户获取提交计划列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getTiJiaoList(HttpServletRequest request,
			TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		/*int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);//0=待发布
*/	
		String startTime=TeeStringUtil.getString(request.getParameter("startTime")); // 开始时间
		String endTime=TeeStringUtil.getString(request.getParameter("endTime"));  // 结束时间
		String tittle=TeeStringUtil.getString(request.getParameter("tittle"));//标题
		
		// 根据登录用户 获取部门以及部门编号
		TeeDepartment department = loginUser.getDept();
		int createUserId = loginUser.getUuid();
		int departmentNo = department.getDeptNo();
		
		
			List param=new ArrayList();
			String hql=" from TeeWeekPlan where departmentNo=? and createUserId = ? and status=0 ";
			param.add(departmentNo);
			param.add(createUserId);
			
			if(!TeeUtility.isNullorEmpty(tittle)){
				hql+=" and tittle like ? ";
				param.add("%"+tittle+"%");
			}
		
			
			if(!TeeUtility.isNullorEmpty(startTime)){
				startTime=startTime+" 00:00:00";
				/*Date s=TeeDateUtil.parseDate(startTime);*/
				hql+=" and submitTime>=? ";
				param.add(startTime);	
			}
			
			if(!TeeUtility.isNullorEmpty(endTime)){
				endTime=endTime+" 23:59:59";
				/*Date e=TeeDateUtil.parseDate(endTime);*/
				hql+=" and submitTime<=? ";
				param.add(endTime);	
			}
			
			long total=weekPlanDao.count("select count(sid) "+hql, param.toArray());
			
			json.setTotal(total);
			List<TeeWeekPlan> list=weekPlanDao.pageFind(hql+" order by submitTime desc ",(dm.getPage() - 1) * dm.getRows(),dm.getRows(), param.toArray());
			
			List<TeeWeekPlanModel> rows=new ArrayList<TeeWeekPlanModel>();
			
			if(list!=null&&list.size()>0){
				for (TeeWeekPlan weekPlan: list) {
					TeeWeekPlanModel model=new TeeWeekPlanModel();
					model=parseToModel(weekPlan);
					rows.add(model);
				}
			}
		
			json.setRows(rows);
			return json;
		}
	
	/**
	 * 实体类转换成model类型
	 * @param xtRun
	 * @return
	 */
	private TeeWeekPlanModel parseToModel(TeeWeekPlan weekPlan) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeWeekPlanModel model=new TeeWeekPlanModel();
		BeanUtils.copyProperties(weekPlan, model);
	
		//处理发布时间
		if(weekPlan.getPublishTime()!=null){
			model.setPublishTime(sdf.format(weekPlan.getPublishTime()));
		}
	
	
		return model;
	}
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeWeekPlan weekPlan=(TeeWeekPlan) weekPlanDao.get(TeeWeekPlan.class,sid);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(weekPlan!=null){
			/*TeeWeekPlanModel model=parseToModel(weekPlan);*/
			TeeWeekPlanModel model = new TeeWeekPlanModel();
			model.setContent(weekPlan.getContent());
			model.setCreateUserId(weekPlan.getCreateUserId().getUuid());
			model.setDepartmentNo(weekPlan.getDepartmentNo());
			model.setPostilContent(weekPlan.getPostilContent());
			model.setSubmitTime(weekPlan.getSubmitTime());
			if(weekPlan.getPublishTime() != null){
				model.setPublishTime(sdf.format(weekPlan.getPublishTime()));
			}
			
			model.setSid(weekPlan.getSid());
			model.setStatus(1);
			model.setTittle(weekPlan.getTittle());
			json.setRtData(model);
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("信息获取失败！");
		}
		return json;
	}
	
	 /**
     * 根据状态以及用户获取发布列表
     * @param request
     * @param dm
     * @return
	 * @throws ParseException 
     */
	public TeeEasyuiDataGridJson getFaBuList(HttpServletRequest request,
			TeeDataGridModel dm) throws ParseException {
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String startTime=TeeStringUtil.getString(request.getParameter("startTime")); // 开始时间
		String endTime=TeeStringUtil.getString(request.getParameter("endTime"));  // 结束时间
		String tittle=TeeStringUtil.getString(request.getParameter("tittle"));//标题
		// 获取用户id
		int createUserId = loginUser.getUuid();
		// 根据登录用户 获取部门以及部门编号
		TeeDepartment department = loginUser.getDept();
		int departmentNo = department.getDeptNo();
		// 查看是否是部门领导
		String manager2 = loginUser.getDept().getManager2();
		List param=new ArrayList();
		if(manager2 != null && manager2 != "" && manager2 != "0"){
			String hql=" from TeeWeekPlan where  status = 1  ";
			param.add(departmentNo);
			if(!TeeUtility.isNullorEmpty(tittle)){
				hql+=" and tittle like ? ";
				param.add("%"+tittle+"%");
			}
		
			
			if(!TeeUtility.isNullorEmpty(startTime)){
				startTime=startTime+" 00:00:00";
				Date s=TeeDateUtil.parseDate(startTime);
				
				hql+=" and publishTime>=? ";
				param.add(s);	
			}
			
			if(!TeeUtility.isNullorEmpty(endTime)){
				endTime=endTime+" 23:59:59";
				Date e=TeeDateUtil.parseDate(endTime);
				
				hql+=" and publishTime<=? ";
				param.add(e);	
			}
			long total=weekPlanDao.count("select count(sid) "+hql, param.toArray());
			json.setTotal(total);
			List<TeeWeekPlan> list=weekPlanDao.pageFind(hql+" order by publishTime desc ",(dm.getPage() - 1) * dm.getRows(),dm.getRows(), param.toArray());
			
			List<TeeWeekPlanModel> rows=new ArrayList<TeeWeekPlanModel>();
			
			if(list!=null&&list.size()>0){
				for (TeeWeekPlan weekPlan : list) {
					TeeWeekPlanModel model=new TeeWeekPlanModel();
					model.setContent(weekPlan.getContent());
					model.setCreateUserId(weekPlan.getCreateUserId().getUuid());
					model.setDepartmentNo(departmentNo);
					model.setPublishTime(sdf.format(weekPlan.getPublishTime()));
					model.setSid(weekPlan.getSid());
					model.setStatus(1);
					model.setTittle(weekPlan.getTittle());
					rows.add(model);
				}
			}
			json.setRows(rows);
		}else{
			// 只能查询到 自己 提交并且以及发布的计划
			String hql=" from TeeWeekPlan where createUserId=? and  status = 1  ";
			param.add(createUserId);
			if(!TeeUtility.isNullorEmpty(tittle)){
				hql+=" and tittle like ? ";
				param.add("%"+tittle+"%");
			}
		
			
			if(!TeeUtility.isNullorEmpty(startTime)){
				startTime=startTime+" 00:00:00";
				Date s=TeeDateUtil.parseDate(startTime);
				
				hql+=" and publishTime>=? ";
				param.add(s);	
			}
			
			if(!TeeUtility.isNullorEmpty(endTime)){
				endTime=endTime+" 23:59:59";
				Date e=TeeDateUtil.parseDate(endTime);
				
				hql+=" and publishTime<=? ";
				param.add(e);	
			}
			long total=weekPlanDao.count("select count(sid) "+hql, param.toArray());
			json.setTotal(total);
			List<TeeWeekPlan> list=weekPlanDao.pageFind(hql+" order by publishTime desc ",(dm.getPage() - 1) * dm.getRows(),dm.getRows(), param.toArray());
			
			List<TeeWeekPlanModel> rows=new ArrayList<TeeWeekPlanModel>();
			
			if(list!=null&&list.size()>0){
				for (TeeWeekPlan weekPlan : list) {
					TeeWeekPlanModel model=new TeeWeekPlanModel();
					model.setContent(weekPlan.getContent());
					model.setCreateUserId(weekPlan.getCreateUserId().getUuid());
					model.setDepartmentNo(departmentNo);
					model.setPublishTime(sdf.format(weekPlan.getPublishTime()));
					model.setSid(weekPlan.getSid());
					model.setStatus(1);
					model.setTittle(weekPlan.getTittle());
					rows.add(model);
				}
			}
			json.setRows(rows);
			
		}

		return json;
	
	
		
	}
	
	
	/**
	 * 领导批注
	 * @return
	 */
	public TeeJson addPiZhu(HttpServletRequest request,TeeWeekPlanModel model){
		 TeeJson json=new TeeJson();
		//获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String manager2 = loginUser.getDept().getManager2();
		if(manager2 != null && manager2 != "" && manager2 != "0"){
			//获取页面上传来的主键
		    int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		    TeeWeekPlan weekPlan=(TeeWeekPlan) weekPlanDao.get(TeeWeekPlan.class,sid);
		    weekPlan.setPostilContent(model.getPostilContent());
		    weekPlanDao.update(weekPlan);
		    json.setRtState(true);
			
		}else{
			 json.setRtState(false);
			 json.setRtMsg("没有批注权限");
		}
		return json;
	    
	}
	
	/**
	 * 查询所有以发布内容
	 * @param dm
	 * @return TeeEasyuiDataGridJson
	 */
	public TeeEasyuiDataGridJson findAll(HttpServletRequest request,TeeDataGridModel dm){
	
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		String startTime=TeeStringUtil.getString(request.getParameter("startTime")); // 开始时间
		String endTime=TeeStringUtil.getString(request.getParameter("endTime"));  // 结束时间
		String tittle=TeeStringUtil.getString(request.getParameter("tittle"));//标题
		String deptNo=TeeStringUtil.getString(request.getParameter("deptNo"));//标题
		
		
		
		
		
		List param=new ArrayList();
		String hql=" from TeeWeekPlan where  status = 1  ";
		
		if(!TeeUtility.isNullorEmpty(tittle)){
			hql+=" and tittle like ? ";
			param.add("%"+tittle+"%");
		}if(request.getParameter("deptNo") != null && request.getParameter("deptNo") != ""){
			 
			Integer departmentNo = Integer.parseInt(request.getParameter("deptNo")); //部门id
			hql+=" and departmentNo = ? ";
			param.add(departmentNo);
		}
	
		
		if(!TeeUtility.isNullorEmpty(startTime)){
			startTime=startTime+" 00:00:00";
			Date s=TeeDateUtil.parseDate(startTime);
			
			hql+=" and publishTime>=? ";
			param.add(s);	
		}
		
		if(!TeeUtility.isNullorEmpty(endTime)){
			endTime=endTime+" 23:59:59";
			Date e=TeeDateUtil.parseDate(endTime);
			
			hql+=" and publishTime<=? ";
			param.add(e);	
		}
		long total=simpleDaoSupport.count("select count(sid) "+hql, param.toArray());
		json.setTotal(total);
		List<TeeWeekPlan> list=weekPlanDao.pageFind(hql+" order by publishTime desc ",(dm.getPage() - 1) * dm.getRows(),dm.getRows(), param.toArray());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<TeeWeekPlanModel> rows=new ArrayList<TeeWeekPlanModel>();
		
		if(list!=null&&list.size()>0){
			for (TeeWeekPlan weekPlan : list) {
				TeeWeekPlanModel model=new TeeWeekPlanModel();
				model.setContent(weekPlan.getContent());
				model.setCreateUserId(weekPlan.getCreateUserId().getUuid());
				model.setDepartmentNo(weekPlan.getDepartmentNo());
				model.setPublishTime(sdf.format(weekPlan.getPublishTime()));
				model.setSid(weekPlan.getSid());
				model.setStatus(1);
				model.setTittle(weekPlan.getTittle());
				rows.add(model);
			}
		}
	
		json.setRows(rows);
		return json;
		
	}
	
	/**
	 * 查询所有部门
	 * @return
	 */
	public TeeJson findAllDept() {
		
		TeeJson json=new TeeJson();
		
		List<TeeDepartment> lists = deptDao.find("from TeeDepartment", null);
		List<Map<String, Object>> listMaps = new ArrayList<Map<String, Object>>();
		if(lists != null && lists.size() > 0){
			for(TeeDepartment department: lists){
				Map<String, Object> map =  new HashMap();
				map.put("sid", department.getDeptNo());
				map.put("deptName", department.getDeptName());
				listMaps.add(map);
			}
		}
		json.setRtData(listMaps);
		json.setRtState(true);
		return json;
		
	}
	/**
	 * 根据状态以及用户获取提交计划列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getTiJiaoListAll(HttpServletRequest request,
			TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int uuid=loginUser.getUuid();
		String startTime=TeeStringUtil.getString(request.getParameter("startTime")); // 开始时间
		String endTime=TeeStringUtil.getString(request.getParameter("endTime"));  // 结束时间
		String tittle=TeeStringUtil.getString(request.getParameter("tittle"));//标题
		int type=TeeStringUtil.getInteger(request.getParameter("type"), 0);
		int deptId=TeeStringUtil.getInteger(request.getParameter("deptNo"), 0);
		// 根据登录用户 获取部门以及部门编号
		//TeeDepartment department = loginUser.getDept();
		int createUserId = loginUser.getUuid();
		
		String hql="from TeeWeekPlan where 1=1 ";
		if(type==0){//查看所有
			hql+=" and status = 1 ";
		}else if(type==1){//分管领导
			hql+=" and createUserId.dept.manager2='"+TeeStringUtil.getString(uuid)+"'";
			hql+=" and status = 1 ";
		}else{//查看自己创建的
			hql+=" and createUserId.uuid="+uuid;
			//hql+=" order by sid desc, status asc";
			
		}
		List param=new ArrayList();
		
		if(!TeeUtility.isNullorEmpty(tittle)){
			hql+=" and tittle like ? ";
			param.add("%"+tittle+"%");
		}
		
		if(type==2){
			if(!TeeUtility.isNullorEmpty(startTime)){
				startTime=startTime+" 00:00:00";
				/*Date s=TeeDateUtil.parseDate(startTime);*/
				hql+=" and submitTime>=? ";
				param.add(startTime);	
			}
			
			if(!TeeUtility.isNullorEmpty(endTime)){
				endTime=endTime+" 23:59:59";
				/*Date e=TeeDateUtil.parseDate(endTime);*/
				hql+=" and submitTime<=? ";
				param.add(endTime);	
			}
		}else{
			if(!TeeUtility.isNullorEmpty(startTime)){
				startTime=startTime+" 00:00:00";
				/*Date s=TeeDateUtil.parseDate(startTime);*/
				hql+=" and publishTime>=? ";
				param.add(startTime);	
			}
			
			if(!TeeUtility.isNullorEmpty(endTime)){
				endTime=endTime+" 23:59:59";
				/*Date e=TeeDateUtil.parseDate(endTime);*/
				hql+=" and publishTime<=? ";
				param.add(endTime);	
			}
		}
		
		
		
		if(deptId>0){
			hql+=" and departmentNo = ? ";
			param.add(deptId);
			
		}
		List<TeeWeekPlanModel> rows=new ArrayList<TeeWeekPlanModel>();
		List<TeeWeekPlanModel> listModels=new ArrayList<TeeWeekPlanModel>();
		List<TeeWeekPlan> pageFind = weekPlanDao.pageFindByList(hql+" order by status asc,sid desc", dm.getFirstResult(), dm.getRows(), param);
		Long count = weekPlanDao.countByList("select count(*) "+hql, param);
		if(pageFind!=null && pageFind.size()>0){
			for (TeeWeekPlan p:pageFind) {
				TeeWeekPlanModel model=new TeeWeekPlanModel();
				model.setSid(p.getSid());
				model.setContent(p.getContent());
				model.setUserName(p.getCreateUserId().getUserName());
				model.setDeptName(p.getCreateUserId().getDept().getDeptName());
				model.setTittle(p.getTittle());
				model.setSubmitTime(p.getSubmitTime());
				model.setPublishTime(TeeDateUtil.format(p.getPublishTime(), "yyyy-MM-dd HH:mm:ss"));
				model.setStatus(p.getStatus());
				listModels.add(model);
			}
		}
		json.setRows(listModels);
		json.setTotal(count);
			
			
			return json;
		}
	
}
