package com.tianee.oa.subsys.salManage.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.NewsAddress;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jspsmart.upload.Request;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.general.service.TeeSysParaService;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.core.org.service.TeePersonManagerI;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.booksManagement.bean.TeeBookManager;
import com.tianee.oa.subsys.booksManagement.bean.TeeBookType;
import com.tianee.oa.subsys.booksManagement.dao.TeeBookDao;
import com.tianee.oa.subsys.booksManagement.dao.TeeBookManagerDao;
import com.tianee.oa.subsys.booksManagement.dao.TeeBookTypeDao;
import com.tianee.oa.subsys.booksManagement.model.TeeBookManagerModel;
import com.tianee.oa.subsys.salManage.bean.TeeHrInsurancePara;
import com.tianee.oa.subsys.salManage.bean.TeeHrSalData;
import com.tianee.oa.subsys.salManage.bean.TeeSalItem;
import com.tianee.oa.subsys.salManage.dao.TeeHrInsuranceParaDao;
import com.tianee.oa.subsys.salManage.dao.TeeHrSalDao;
import com.tianee.oa.subsys.salManage.dao.TeeSalItemDao;
import com.tianee.oa.subsys.salManage.model.TeeHrInsuranceParaModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class TeeSalaryService  extends TeeBaseService {
	@Autowired
	private TeeBookDao bookDao;
	
	@Autowired
	private TeeSalItemDao salItemDao;
	@Autowired
	private TeeHrSalDao hrSalDao;
	@Autowired
	private TeeHrInsuranceParaDao hrParaDao;
	@Autowired
	private TeeBookTypeDao bookTypeDao;
	@Autowired
	private TeeBookManagerDao bookManagerDao;
	@Autowired
	private TeePersonDao personDao;
	@Autowired
	private TeeDeptDao deptDao;
	@Autowired
	private TeeUserRoleDao userRoleDao;
	@Autowired
	private TeeBaseUpload upload;
	@Autowired
	private TeePersonManagerI personManagerI;
	@Autowired
	private TeeSmsSender smsSender;
	
	@Autowired
	private TeeSysParaService sysParaService;

	/**
	 * @author CXT
	 * 新增 或者 更新
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public TeeJson addOrUpdateHrPara(HttpServletRequest request, TeeHrInsuranceParaModel model) throws IOException, ParseException {
		TeeJson json = new TeeJson();
		if(model.getSid()!=0){
			TeeHrInsurancePara para = hrParaDao.get(model.getSid());
			BeanUtils.copyProperties(model, para);
			hrParaDao.update(para);
		}else{
			TeeHrInsurancePara para = new TeeHrInsurancePara();
			BeanUtils.copyProperties(model, para);
			hrParaDao.add(para);
			model.setSid(para.getSid());
		}
		//sysLog.setType("029A");
		json.setRtState(true);
		json.setRtMsg("保存成功！");
		//创建日志
		//sysLog.setRemark(getSysLogInfo(model));
		//TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		json.setRtData(model);
		
		return json;
	}
	
	/**
	 * @author CXT
	 * 新增 或者 更新图书管理员
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public TeeJson addOrUpdateManager(HttpServletRequest request, TeeBookManagerModel model) throws IOException, ParseException {
		//记录日志
		TeeSysLog sysLog = TeeSysLog.newInstance();
		
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		
		TeeBookManager book = new TeeBookManager();
		if(model.getSid()>0){//判断是否是更新
			book = bookManagerDao.get(model.getSid());
		}
		BeanUtils.copyProperties(model,book);
		if(!TeeUtility.isNullorEmpty(model.getPostDeptIds())){//借阅权限  ---部门
			List<TeeDepartment> listDept = deptDao.getDeptListByUuids(model.getPostDeptIds());
			book.setPostDept(listDept);
		}
		if(!TeeUtility.isNullorEmpty(model.getPostUserIds())){//借阅权限- 人员
			List<TeePerson> listDept = personDao.getPersonByUuids(model.getPostUserIds());
			book.setPostUser(listDept);
		}
		if(model.getSid()>0){//判断是否是更新
			bookManagerDao.update(book);
			json.setRtMsg("更新成功！");
		}else{
			bookManagerDao.add(book);
			json.setRtMsg("保存成功！");
		}
		//sysLog.setType("029A");
		json.setRtState(true);
		//创建日志
		//sysLog.setRemark(getSysLogInfo(model));
		//TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		json.setRtData(model);
		
		return json;
	}
	
	/**
	 * 已定义工资项目
	 * @author 兴涛
	 * @return
	 */
	public List<TeeSalItem> salItemList(int accountId){
		List<TeeSalItem> list = new ArrayList<TeeSalItem>();
		list = salItemDao.salItemListByAccountId(accountId);
		return list;
	}
	
	/**
	 * 已定义工资项目
	 * @author 兴涛
	 * @return
	 */
	public List<TeeSalItem> salItemList(int type , int accountId){
		List<TeeSalItem> list = new ArrayList<TeeSalItem>();
		list = salItemDao.salItemListByType(type , accountId);
		return list;
	}
	
	/**
	 * 保险基数
	 * @author 兴涛
	 * @return
	 */
	public List<TeeHrInsurancePara> hrParaList(){
		List<TeeHrInsurancePara> list = new ArrayList<TeeHrInsurancePara>();
		list = hrParaDao.hrParaList();
		return list;
	}
	
	/**
	 * 保险基数
	 * @author 兴涛
	 * @return
	 */
	public TeeHrInsurancePara getHrPara(int sid){
		return (TeeHrInsurancePara) simpleDaoSupport.get(TeeHrInsurancePara.class, sid);
	}
	
	/**
	 * 删除指定基数
	 * @param sid
	 */
	public void delHrPara(int sid){
		simpleDaoSupport.delete(TeeHrInsurancePara.class, sid);
	}
	
	/**
	 * 获取图书管理员列表
	 * @author 兴涛
	 * @return
	 */
	public List<TeeBookManagerModel> bookManagerList(){
		List<TeeBookManager> list = new ArrayList<TeeBookManager>();
		list = bookManagerDao.bookManagerList();
		List<TeeBookManagerModel> modelList = new ArrayList<TeeBookManagerModel>();
		for(TeeBookManager manager : list){
			TeeBookManagerModel model = new TeeBookManagerModel();
			String postDeptIds ="";
			String postDeptNames="";
			String postUserIds="";
			String postUserNames="";
			for(TeeDepartment dept : manager.getPostDept()){
				postDeptIds += dept.getUuid()+",";
				postDeptNames += dept.getDeptName()+",";
			}
			for(TeePerson person : manager.getPostUser()){
				postUserIds += person.getUuid()+",";
				postUserNames += person.getUserName() +",";
			}
			BeanUtils.copyProperties(manager,model);
			model.setPostDeptIds(postDeptIds);
			model.setPostDeptNames(postDeptNames);
			model.setPostUserIds(postUserIds);
			model.setPostUserNames(postUserNames);
			modelList.add(model);
		}
		return modelList;
	}
	

	
	/**
	 * 更新图书分类
	 * @author 兴涛
	 */
	public void updateBookType(TeeBookType bookType){
		bookTypeDao.update(bookType);
	}
	
	/**
	 * 新增工资数据
	 * @author 兴涛
	 */
	public void saveSalData(TeeHrSalData salData){
		hrSalDao.save(salData);
	}
	
	/**
	 * 更新工资数据
	 * @author 兴涛
	 */
	public void updateSalData(TeeHrSalData salData){
		hrSalDao.update(salData);
	}
	

	
	/**
	 * 删除图书管理信息
	 * @author 兴涛
	 */
	public void deleteBookManager(String sid){
		bookManagerDao.delete(Integer.parseInt(sid));
	}
	

	/**
	 * 获取图书管理员信息
	 * @author 兴涛
	 */
	public TeeBookManagerModel getBookManager(String sid){
		
		TeeBookManager manager = bookManagerDao.get(Integer.parseInt(sid));
		TeeBookManagerModel model = new TeeBookManagerModel();
		String postDeptIds ="";
		String postDeptNames="";
		String postUserIds="";
		String postUserNames="";
		for(TeeDepartment dept : manager.getPostDept()){
			postDeptIds += dept.getUuid()+",";
			postDeptNames += dept.getDeptName()+",";
		}
		for(TeePerson person : manager.getPostUser()){
			postUserIds += person.getUuid()+",";
			postUserNames += person.getUserName() +",";
		}
		BeanUtils.copyProperties(manager,model);
		model.setPostDeptIds(postDeptIds);
		model.setPostDeptNames(postDeptNames);
		model.setPostUserIds(postUserIds);
		model.setPostUserNames(postUserNames);
		return model;
	}
	
	/**
	 * 获取工资项
	 * @author 兴涛
	 */
	public List<TeeHrSalData> getSalDataList(String personIds){
		
		 List<TeeHrSalData>  data = hrSalDao.getSalDataList(personIds);
		return data;
	}
	
	/**
	 * 单个获取
	 * @param personId
	 * @return
	 */
	public TeeHrSalData getSalData(String personId){
		
		TeeHrSalData  data = hrSalDao.getSalData(personId);
		return data;
	}

	/**
	 * 获取保险列表
	 * @param dataGridModel
	 * @return
	 */
	public TeeEasyuiDataGridJson datagridInsurances(TeeDataGridModel dataGridModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List<TeeHrInsuranceParaModel> modelList = new ArrayList<TeeHrInsuranceParaModel>();
		List<TeeHrInsurancePara> entityList = simpleDaoSupport.pageFind("from TeeHrInsurancePara order by sid asc", 0, Integer.MAX_VALUE, null);
		for(TeeHrInsurancePara entity:entityList){
			TeeHrInsuranceParaModel model = new TeeHrInsuranceParaModel();
			BeanUtils.copyProperties(entity, model);
			modelList.add(model);
		}
		dataGridJson.setTotal(Long.parseLong(modelList.size()+""));
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}

	
	
	/**
	 * 获取税率设置的值
	 * @return
	 */
	public TeeJson getRate() {
		TeeJson json=new TeeJson();
		String rateStr=TeeSysProps.getString("HR_P_INCOME_MODEL");
		json.setRtState(true);
		json.setRtData(rateStr);	
		return json;
	}

	
	/**
	 * 修改税率
	 * @return
	 */
	public TeeJson updateRate(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String rateStr=TeeStringUtil.getString(request.getParameter("model"));
	
    	TeeSysPara sysPara = new TeeSysPara();
    	sysPara.setParaName("HR_P_INCOME_MODEL");
    	sysPara.setParaValue(rateStr);
    	sysParaService.addUpdatePara(sysPara);
		
		json.setRtState(true);
		json.setRtData(rateStr);	
		return json;
	}

}
