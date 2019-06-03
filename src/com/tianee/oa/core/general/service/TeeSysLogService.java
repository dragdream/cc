package com.tianee.oa.core.general.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.exam.bean.TeeExamPaper;
import com.tianee.oa.core.base.exam.model.TeeExamPaperModel;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.general.dao.TeeArchivesDao;
import com.tianee.oa.core.general.dao.TeeSysLogDao;
import com.tianee.oa.core.general.model.TeeSysLogModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeModuleConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeSysLogService extends TeeBaseService{
	@Autowired
	@Qualifier("sysLogDao")
    TeeSysLogDao sysLogDao;
	
	@Autowired
	TeeArchivesDao archivesDao;
	/**
	 * 创建日志
	 * @author syl
	 * @date 2013-11-24
	 * @param desc
	 * @param error
	 * @param request
	 * @param type
	 */
	public void saveLog(String desc,String error,HttpServletRequest request,String type){
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int userUuid = person.getUuid();
		TeeSysLog sysLog = new TeeSysLog();
		Date date=new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		sysLog.setErrorLog(error);
		sysLog.setIp(request.getRemoteAddr());
		sysLog.setPersonId(userUuid );
		sysLog.setRemark(desc);
		sysLog.setTime(cal);
		sysLog.setType(type);
		sysLogDao.save(sysLog);
	}
	/**
	 * 创建日志
	 * @author syl
	 * @date 2013-11-24
	 * @param desc
	 * @param error
	 * @param request
	 * @param type
	 * @param Ip
	 */
	public void saveLog(String desc,String error,TeePerson loginUser , String type ,String Ip){
		int userUuid = loginUser.getUuid();
		TeeSysLog sysLog = new TeeSysLog();
		Date date=new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		sysLog.setErrorLog(error);
		//sysLog.setIp(request.getRemoteAddr());
		sysLog.setIp(Ip);
		sysLog.setPersonId(userUuid );
		sysLog.setRemark(desc);
		sysLog.setTime(cal);
		sysLog.setType(type);
		sysLogDao.save(sysLog);	
	}
	
	public TeeSysLog save(TeeSysLog sysLog){
		if(sysLog==null){
			return null;
		}
		sysLogDao.save(sysLog);
		return sysLog;
	}
	 	
	/**
	 * 作者  syl
	 * 获取当前登录人最后N条记录
	 * @param personId
	 * @param count
	 * @param type 日志类型
	 * @return
	 * @throws  
	 */
	public List getLogByLoginPerson(TeePerson person , int count , String type) {
		List list = new ArrayList();
		return  sysLogDao.getLogByLoginPerson(person, count, type);
		//return list;
		
	}
	
	
	
	public Map getSummaryLogInfo() {
		return  sysLogDao.getSummaryLogInfo();
	}
	
	public List<TeeSysLogModel> getLast10LogInfo() {
		List<TeeSysLog> list =sysLogDao.getLast10LogInfo();
		List<TeeSysLogModel> models = new ArrayList<TeeSysLogModel>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(TeeSysLog log :list){
			TeeSysLogModel model = new TeeSysLogModel();
			//BeanUtils.copyProperties(log,model);
			model.setTimeDesc(sf.format(log.getTime().getTime()));
			if(TeeUtility.isNullorEmpty(log.getUserName())){
				model.setUserName("");
			}else{
				model.setUserName(log.getUserName());
			}
			if(TeeUtility.isNullorEmpty(log.getIp())){
				model.setIp("");
			}else{
				model.setIp(log.getIp());
			}
			if(TeeUtility.isNullorEmpty(log.getType())){
				model.setType("");
			}else{
				model.setType(TeeModuleConst.MODULE_SORT_TYPE_DETAIL.get(log.getType()));
			}
			if(TeeUtility.isNullorEmpty(log.getRemark())){
				model.setRemark("");
			}else{
				model.setRemark(log.getRemark());
			}
			models.add(model);
		}
		return  models;
	}
	
	
	public List getLogInfoByYear(String year) {
		return  sysLogDao.getLogInfoByYear(year);
	}
	
	public List getLogInfoByHour() {
		return  sysLogDao.getLogInfoByHour();
	}
	public List getLogInfoByYearAndMonth(String year,String month) {
		return  sysLogDao.getLogInfoByYearAndMonth(year,month);
	}
	
	public Map getGraphDataByYearAndMonth(String year, String month) {
		return  sysLogDao.getGraphDataByYearAndMonth(year,month);
	}
	public Map getEverydayByYearAndMonth(String year, String month) {
		return  sysLogDao.getEverydayByYearAndMonth(year,month);
	}
	public Map getGraphDataByTimes() {
		return  sysLogDao.getGraphDataByTimes();
	}
	
	/**
	 * 根据条件查询出满足条件的系统日志信息
	 * @param requestDatas
	 * @return
	 * @throws ParseException
	 */
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, Map requestDatas) throws Exception {
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		List<TeeSysLog>  list = sysLogDao.getLogInfoByCondition(dm,requestDatas);
		List rows = new ArrayList();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long total = sysLogDao.getTotalByConditon(requestDatas);
		for(TeeSysLog log:list){
			TeeSysLogModel model = new TeeSysLogModel();
			//BeanUtils.copyProperties(log, model);
			model.setTimeDesc(sf.format(log.getTime().getTime()));
			if(TeeUtility.isNullorEmpty(log.getUuid())){
				model.setUuid("");
			}else{
				model.setUuid(log.getUuid());
			}
			if(TeeUtility.isNullorEmpty(log.getUserName())){
				model.setUserName("");
			}else{
				model.setUserName(log.getUserName());
			}
			if(TeeUtility.isNullorEmpty(log.getIp())){
				model.setIp("");
			}else{
				model.setIp(log.getIp());
			}
			if(TeeUtility.isNullorEmpty(log.getType())){
				model.setType("");
			}else{
				model.setType(TeeModuleConst.MODULE_SORT_TYPE_DETAIL.get(log.getType()));
			}
			if(TeeUtility.isNullorEmpty(log.getRemark())){
				model.setRemark("");
			}else{
				model.setRemark(log.getRemark());
			}
			rows.add(model);
		}
		datagird.setRows(rows);
		datagird.setTotal(total);
		
		return datagird;
	}
	
	public void delLogInfo(String  uuid) {
		TeeSysLog log = sysLogDao.getSysLogByUuid(uuid);
		sysLogDao.delSysLog(log);
		
	}
	
	
	
	
	/**
	 * 根据条件查询出满足条件的系统日志信息
	 * @param requestDatas
	 * @return
	 * @throws ParseException
	 */
	public List<TeeSysLogModel> getTotalByConditon(Map requestDatas) throws Exception {
		List<TeeSysLog>  list = sysLogDao.getLogInfoByCondition(requestDatas);
		List<TeeSysLogModel> models = new ArrayList<TeeSysLogModel>();;
		for(TeeSysLog log:list){
			TeeSysLogModel model = new TeeSysLogModel();
			//BeanUtils.copyProperties(log, model);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			model.setTimeDesc(sf.format(log.getTime().getTime()));
			if(TeeUtility.isNullorEmpty(log.getUuid())){
				model.setUuid("");
			}else{
				model.setUuid(log.getUuid());
			}
			if(TeeUtility.isNullorEmpty(log.getUserName())){
				model.setUserName("");
			}else{
				model.setUserName(log.getUserName());
			}
			if(TeeUtility.isNullorEmpty(log.getIp())){
				model.setIp("");
			}else{
				model.setIp(log.getIp());
			}
			if(TeeUtility.isNullorEmpty(log.getType())){
				model.setType("");
			}else{
				model.setType(log.getType());
			}
			if(TeeUtility.isNullorEmpty(log.getRemark())){
				model.setRemark("");
			}else{
				model.setRemark(log.getRemark());
			}
			models.add(model);
		}
		
		return models;
	}
	/**
	 * 获取所有归档的日志表
	 * @return
	 */
	public List getSysLogTable() {
		return  archivesDao.getSysLogTable();
	}
	
	
	/**
	 * 三员日志管理
	 * @param dm
	 * @param requestDatas
	 * @return
	 * @throws ParseException 
	 */
	public TeeEasyuiDataGridJson getThreePartSysLogs(TeeDataGridModel dm,
			Map requestDatas) throws ParseException {
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		List<TeeSysLog>  list = sysLogDao.getThreePartSysLogs(dm,requestDatas);
		List rows = new ArrayList();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long total = sysLogDao.getThreePartSysLogsTotal(requestDatas);
		for(TeeSysLog log:list){
			TeeSysLogModel model = new TeeSysLogModel();
			//BeanUtils.copyProperties(log, model);
			if(log.getTime()!=null){
				model.setTimeDesc(sf.format(log.getTime().getTime()));
			}else{
				model.setTimeDesc("");
			}
			
			if(TeeUtility.isNullorEmpty(log.getUuid())){
				model.setUuid("");
			}else{
				model.setUuid(log.getUuid());
			}
			if(TeeUtility.isNullorEmpty(log.getUserName())){
				model.setUserName("");
			}else{
				model.setUserName(log.getUserName());
			}
			if(TeeUtility.isNullorEmpty(log.getIp())){
				model.setIp("");
			}else{
				model.setIp(log.getIp());
			}
			if(TeeUtility.isNullorEmpty(log.getType())){
				model.setType("");
			}else{
				model.setType(TeeModuleConst.MODULE_SORT_TYPE_DETAIL.get(log.getType()));
			}
			if(TeeUtility.isNullorEmpty(log.getRemark())){
				model.setRemark("");
			}else{
				model.setRemark(log.getRemark());
			}
			rows.add(model);
		}
		datagird.setRows(rows);
		datagird.setTotal(total);
		
		return datagird;
	}
}