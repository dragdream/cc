package com.tianee.oa.core.general.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.general.bean.TeeSeal;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.general.dao.TeeSealDao;
import com.tianee.oa.core.general.dao.TeeSealLogDao;
import com.tianee.oa.core.general.model.TeeSealModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeSealLogConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.dynamic.TeeClassRunner;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeSealService extends TeeBaseService{

	@Autowired
	TeeSealDao sealDao;
	
	@Autowired
	TeeSealLogDao sealLogDao;
	
	@Autowired
	TeeDeptDao deptDao;
	
	@Autowired
	TeePersonDao personDao;
	
	public void addOrUpdate(HttpServletRequest request,TeeSealModel model ){
		TeeSysLog sysLog = TeeSysLog.newInstance();
		 TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		if(model.getSid() > 0){//只修改印章数据
			TeeSeal seal =  sealDao.getById(model.getSid() );
			if(seal != null ){
				//BeanUtils.copyProperties(model, seal);\
				seal.setSealData(model.getSealData());
				sealDao.updateSeal(seal);
			}
			
			sysLog.setType("012B");
			sysLog.setRemark("修改印章["+seal.getSealId()+"  "+seal.getSealName()+"]");
			
		}else{
			TeeSeal seal  = new TeeSeal();
			if(model.getDeptId() > 0){
				TeeDepartment dept = deptDao.load(model.getDeptId());
				seal.setDept(dept);
			}
			BeanUtils.copyProperties(model, seal);
			seal.setCreateTime(Calendar.getInstance());
			seal.setIsFlag(0);
			
			int sealLimit = 0;
			long sealCount = 0;
			//做手机签章控制限制
			try {
				sealLimit = (Integer) TeeClassRunner.exec("com.tianee.webframe.util.auth.TeeAuthUtil", "getSealLimit", null);
				sealCount = simpleDaoSupport.count("select count(sid) from TeeSeal", null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(sealCount>=sealLimit){
				throw new TeeOperationException("签章组件数量超过系统限制最大值"+sealLimit);
			}
			
			sealDao.save(seal);
			
			sysLog.setType("012A");
			sysLog.setRemark("添加印章["+seal.getSealId()+"  "+seal.getSealName()+"]");
			
			//制作印章日志
			sealLogDao.addSealLog(person, seal.getSid(),seal.getSealName(), TeeStringUtil.getInteger(TeeSealLogConst.SEAL_LOG_TYPE[0],1), request.getRemoteAddr(), "", "制作印章成功！");
			
		}
		
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		
	}

	/**
	 * byId  查询
	 * @param 
	 */
	public TeeSeal loadById(int sid) {
		TeeSeal seal = sealDao.loadById(sid);
		return seal;
	}
	
	/**
	 * byId  查询
	 * @param 
	 */
	public TeeSeal getById(int sid) {
		TeeSeal seal = sealDao.getById(sid);
		return seal;
	}
	
	/**
	 * byId  del
	 * @param 
	 */
	@TeeLoggingAnt(template="删除印章 [{#.sealId}  {#.sealName}]",type="012C")
	public TeeSeal delByIdService(int sid) {
		TeeSeal seal = sealDao.getById(sid);
		sealDao.deleteByObj(seal);
		return seal;
	}
	
	/**
	 * 查询最大sealId
	 * @param 
	 */
	public String selectMaxSealid(String sealIdPre) {
		String maxSealId= sealDao.getNextSealId(sealIdPre);
		return maxSealId;
	}
	
	/**
	 * 通用列表
	 * @param dm
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request, TeeSealModel model) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		j.setTotal(sealDao.selectAllCount());// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage()-1) * dm.getRows() ;//获取开始索引位置
		Object parm[] = {};
	
		List<TeeSeal> roles = sealDao.getSealPageFind(firstIndex , dm.getRows(),dm ,model ,loginPerson.getDept().getUuid(),false);// 查询
		List<TeeSealModel> rolemodel = new ArrayList<TeeSealModel>();
		if (roles != null && roles.size() > 0) {
			for (TeeSeal seal : roles) {
				TeeSealModel um = new TeeSealModel();
				BeanUtils.copyProperties(seal, um);
				if(seal.getDept() != null){
					um.setDeptId(seal.getDept().getUuid());
					um.setDeptName(seal.getDept().getDeptName());
				}
				Calendar createTime = seal.getCreateTime();
				um.setCreateTimeDesc(format.format(createTime==null?null:createTime.getTime()));
				um.setCreateTime(createTime);
				rolemodel.add(um);
			}
		}
		j.setRows(rolemodel);// 设置返回的行
		return j;
	}
	
	
	

	/**
	 * 启用或者停止
	 * @param sids : 印章ID字符串
	 * @param  isflag 启用状态 0 - 启用 1-停用
	 */
	public int openOrstopSeal(String sids , String isflag ,HttpServletRequest request) {
		
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int count = sealDao.openOrstopSeal(sids , isflag);
		//制作印章日志
		int sealLogType = TeeSealLogConst.SEAL_LOG_TYPE[4];
		String sealLogTypeDesc = TeeSealLogConst.SEAL_LOG_TYPE_DESC[4];
		if(isflag.equals("1")){
			sealLogType = TeeSealLogConst.SEAL_LOG_TYPE[5];
			 sealLogTypeDesc = TeeSealLogConst.SEAL_LOG_TYPE_DESC[5];
		}
		
		List list =  sealDao.selectSealName(sids);
		sealLogDao.addSealLog(person, 0,list.toString(), sealLogType, request.getRemoteAddr(), "", sealLogTypeDesc + "成功");
		
		return count;
	}
	
	
	
	/**
	 * 启用或者停止
	 * @param sid : 印章ID
	 * @param userStr 人员id字符串
	 */
	public int setSealPriv(String sid , String userStr , HttpServletRequest request ) {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		String userStrDesc = request.getParameter("userStrDesc");
		String sealName = request.getParameter("sealName");
		int count = sealDao.setSealPriv( sid , userStr);
		
		//制作印章日志
		sealLogDao.addSealLog(person, TeeStringUtil.getInteger(sid,0),sealName, TeeStringUtil.getInteger(TeeSealLogConst.SEAL_LOG_TYPE[1],2), request.getRemoteAddr(), "", "将印章授权给" + userStrDesc);
	
		
		return count;
	}
	
	
	
	
	
	/**
	 * byId  sid字符串 以逗号分隔  删除
	 * @param 
	 */
	public void delBySids(String sids , HttpServletRequest request) {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List list =  sealDao.selectSealName(sids);
		sealDao.delBySids(sids);
		
		
		sealLogDao.addSealLog(person, 0,list.toString(), TeeStringUtil.getInteger(TeeSealLogConst.SEAL_LOG_TYPE[3],4), request.getRemoteAddr(), "",  "删除成功！");
		
	}
	
	
	/**
	 * 获取有权限的印章记录 --- 不带印章和使用权限人人员信息，用于流程手写签批和盖章
	 * @param 
	 */
	public TeeJson getHavePrivSeal(TeePerson person) {
		TeeJson json = new TeeJson();
		List<TeeSeal> list =  sealDao.getHavePrivSeal(person.getUuid());
		List<TeeSealModel> listNew = new ArrayList<TeeSealModel>();
		for (int i = 0; i < list.size(); i++) {
			TeeSeal seal = list.get(i);
			TeeSealModel model = new TeeSealModel();
			BeanUtils.copyProperties(seal, model);
			
			model.setSealData("");
			listNew.add(model);
		}
		json.setRtData(listNew);
		json.setRtState(true);
		return json;
		
	}
	
	
	
	/**
	 * 获取有权限的印章记录   信息全面
	 * @param 
	 */
	public TeeEasyuiDataGridJson getHavePrivSealInfo(TeePerson person,TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List l = new ArrayList();
		Object[] values = {"%," + person.getUuid() + ",%"};
		l.add("%," + person.getUuid() + ",%");
		String hql = "from TeeSeal  where concat(',' , userStr) || ','  like ? ";
		json.setTotal(simpleDaoSupport.count("select count(*) "+hql, values));
		List<TeeSealModel> listNew = new ArrayList<TeeSealModel>();
		List<TeeSeal> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), l);
		for (int i = 0; i < list.size(); i++) {
			TeeSeal seal = list.get(i);
			TeeSealModel model = new TeeSealModel();
			BeanUtils.copyProperties(seal, model);
			if(seal.getDept() != null){
				model.setDeptId(seal.getDept().getUuid());
				model.setDeptName(seal.getDept().getDeptName());
			}
			Calendar createTime = seal.getCreateTime();
			model.setCreateTimeDesc(format.format(createTime==null?null:createTime.getTime()));
			if(!TeeUtility.isNullorEmpty(model.getUserStr()));{//获取使用人员信息
				String personInfoArra[] = personDao.getPersonNameAndUuidByUuids(model.getUserStr());
				model.setUserStr(personInfoArra[0]);
				model.setUserStrDesc(personInfoArra[1]);
			}
			model.setSealData("");
			listNew.add(model);
		}
		json.setRows(listNew);
		return json;
		
	}
	
	public void setSealDao(TeeSealDao sealDao) {
		this.sealDao = sealDao;
	}

	public void setSealLogDao(TeeSealLogDao sealLogDao) {
		this.sealLogDao = sealLogDao;
	}

	public void setDeptDao(TeeDeptDao deptDao) {
		this.deptDao = deptDao;
	}

	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}
	
	
}


