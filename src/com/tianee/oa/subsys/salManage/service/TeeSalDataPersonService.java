package com.tianee.oa.subsys.salManage.service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.mvel2.MVEL;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.base.pm.bean.TeeHumanDoc;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.salManage.bean.TeeHrInsurancePara;
import com.tianee.oa.subsys.salManage.bean.TeeHrSalData;
import com.tianee.oa.subsys.salManage.bean.TeeSalAccount;
import com.tianee.oa.subsys.salManage.bean.TeeSalAccountPerson;
import com.tianee.oa.subsys.salManage.bean.TeeSalDataPerson;
import com.tianee.oa.subsys.salManage.bean.TeeSalFlow;
import com.tianee.oa.subsys.salManage.bean.TeeSalItem;
import com.tianee.oa.subsys.salManage.dao.TeeHrInsuranceParaDao;
import com.tianee.oa.subsys.salManage.dao.TeeSalAccountDao;
import com.tianee.oa.subsys.salManage.dao.TeeSalDataPersonDao;
import com.tianee.oa.subsys.salManage.dao.TeeSalItemDao;
import com.tianee.oa.subsys.salManage.model.TeeSalDataPersonModel;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.file.TeeAOPExcleUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service

public class TeeSalDataPersonService  extends TeeBaseService{
	@Autowired
	TeeSalDataPersonDao dataPersonDao;
	
	@Autowired
	TeeHrInsuranceParaDao hrInsuranceParaDao;
	
	@Autowired
	TeeSalItemDao salItemDao;
	
	@Autowired
	TeePersonDao personDao;
	
	@Autowired
	TeeSalAccountDao accountDao;//账套
	
	@Autowired
	TeeSmsManager smsManager;
	
	/**
	 * 新增或者更新
	 * @param map
	 * @param model
	 * @return
	 */
	public TeeJson addUpdate(Map map ,  TeeSalDataPersonModel model){
		TeeJson json = new TeeJson();
		
		return json;
	}
	
	
	/**
	 * 导出工资 模板
	 * @param request
	 * @param model
	 * @return
	 */
	public ArrayList<TeeDataRecord> exportSalModule(HttpServletRequest request ){
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ArrayList<TeeDataRecord> list = new ArrayList<TeeDataRecord>();
		String deptIds = TeeStringUtil.getString(request.getParameter("deptIds"));
		int accountId = TeeStringUtil.getInteger(request.getParameter("accountId"), 0);//账套
		List<TeeHrInsurancePara> paraList  = hrInsuranceParaDao.hrParaList();
//		boolean isShow = false;//是否显示工资基本项
//		if(paraList.size() > 0 ){
//			TeeHrInsurancePara para = paraList.get(0);
//			int yesOther = para.getYesOther();
//			if(yesOther == 1){
//				isShow = true;
//			}
//		}
		
		//自定义项目
		List<TeeSalItem> itemList = salItemDao.salItemListByAccountId(accountId);
		
		if(!"".equals(deptIds)){
			TeeDataRecord dr = new TeeDataRecord();
			List<TeePerson> persons = personDao.getPersonByDeptIds(deptIds);
			for(TeePerson p:persons){
				dr = new TeeDataRecord();
				dr.addField("用户名", p.getUserId());
				dr.addField("姓名", p.getUserName());
				dr.addField("养老保险（个人）", "");
				dr.addField("养老保险（单位）", "");
				dr.addField("医疗保险（个人）", "");
				dr.addField("医疗保险（单位）", "");
				dr.addField("工伤保险（个人）", "");
				dr.addField("工伤保险（单位）", "");
				dr.addField("生育保险（个人）", "");
				dr.addField("生育保险（单位）", "");
				dr.addField("失业保险（个人）", "");
				dr.addField("失业保险（单位）", "");
				dr.addField("公积金（个人）", "");
				dr.addField("公积金（单位）", "");
				dr.addField("个税", "");
				
				
				//自定义项目
				for (int i = 0; i <itemList.size(); i++) {
					TeeSalItem item = itemList.get(i);
					String itemName = item.getItemName();
					dr.addField(itemName, "");
					
				}
				dr.addField("实发金额", "");
				
				list.add(dr);
			}
		}else{
			TeeDataRecord dr = new TeeDataRecord();
			dr.addField("用户名", "");
			dr.addField("姓名", "");
			dr.addField("养老保险（个人）", "");
			dr.addField("养老保险（单位）", "");
			dr.addField("医疗保险（个人）", "");
			dr.addField("医疗保险（单位）", "");
			dr.addField("工伤保险（个人）", "");
			dr.addField("工伤保险（单位）", "");
			dr.addField("生育保险（个人）", "");
			dr.addField("生育保险（单位）", "");
			dr.addField("失业保险（个人）", "");
			dr.addField("失业保险（单位）", "");
			dr.addField("公积金（个人）", "");
			dr.addField("公积金（单位）", "");
			dr.addField("个税", "");
			
			for (int i = 0; i <itemList.size(); i++) {
				TeeSalItem item = itemList.get(i);
				String itemName = item.getItemName();
				dr.addField(itemName, "");
				
			}
			dr.addField("实发金额", "");
			list.add(dr);
		}
		
		return list;
	}
	/**
	 * 导入工资
	 * @param request
	 */
	public TeeJson importSal(HttpServletRequest request)throws Exception{
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		int salYear = TeeStringUtil.getInteger(multipartRequest.getParameter("salYear") , 0);//年份
		int salMonth = TeeStringUtil.getInteger(multipartRequest.getParameter("salMonth") , 0);//月份
		int accountId = TeeStringUtil.getInteger(multipartRequest.getParameter("accountId"), 0);//账套
		
		StringBuffer sb = new StringBuffer("[");
		String infoStr= "";
		int importSuccessCount = 0;
		try {
			MultipartFile  file = multipartRequest.getFile("importFile");
			if(!file.isEmpty() ){
				//获取真实文件名称
				String realName = file.getOriginalFilename();
				ArrayList<TeeDataRecord> dbL = TeeAOPExcleUtil.readExc(file.getInputStream(), true);
				
			
				TeeSalAccount account = accountDao.get(accountId);//获取账套
				if(account == null){
					json.setRtState(false);
					json.setRtMsg("导入的账套不存在！");
					return json;
				}
				List<TeeSalAccountPerson> accountPersonList = account.getAccountPerson();//获取账套人员
				Set set = new HashSet();//存人员的Id
				for (int i = 0; i < accountPersonList.size(); i++) {
					if(accountPersonList.get(i).getUser() != null){
						set.add(accountPersonList.get(i).getUser().getUuid());
					}
					
				}
				Map smsData = new HashMap();
				//自定义项目
				List<TeeSalItem> itemList = salItemDao.salItemListByAccountId(accountId);
				for (int i = 0; i < dbL.size(); i++) {
					TeeDataRecord dr = dbL.get(i);
					String userId = TeeStringUtil.getString(dr.getValueByName("用户名"));
					String userName = TeeStringUtil.getString(dr.getValueByName("姓名"));

					
					String color = "red";
					TeePersonModel personModel  = new TeePersonModel();
					personModel.setUserId(userId);
					personModel.setUserName(userName);
//					if(TeeUtility.isNullorEmpty(userName)){//用户姓名为空则不建立
//						infoStr = "导入失败,姓名不能为空！";
//						getImportInfo(sb, personModel, infoStr, color);
//				        continue;
//				    }
					//根据用户姓名查询人员
					TeePerson listPerson = personDao.getPersonByUserId(userId);
					if(listPerson==null){
						infoStr = "导入失败,不存在用户名为"+userId+"的用户，请检查！";
						getImportInfo(sb, personModel, infoStr, color);
						continue;
					}
//					if(listPerson.size() == 0){
//						infoStr = "导入失败,不存在姓名为"+userName+"的用户，请检查！";
//						getImportInfo(sb, personModel, infoStr, color);
//				        continue;
//					}
//					if(listPerson.size() >= 2){
//						infoStr = "导入失败,存在多个姓名为"+userName+"的用户！";
//						getImportInfo(sb, personModel, infoStr, color);
//				        continue;
//					}
					TeePerson p = listPerson;
					if(dataPersonDao.checkUserSalExist(p.getUuid(), salYear , salMonth)){//判断用户本月工资是否导入过
						 infoStr = "导入失败,"+userName + salYear + "年" + salMonth + "月" +  "工资已经导入过！";
						 getImportInfo(sb, personModel, infoStr, color);
				         continue;
					}
					if(!set.contains(p.getUuid())){
						 infoStr = "导入失败," + userName + "不属于“"+account.getAccountName()+"”工资账套！";
						 getImportInfo(sb, personModel, infoStr, color);
				         continue;
					}
			
					TeeSalDataPerson salData = new TeeSalDataPerson();

					
					//工资自定义项  ---- 所有项目先赋值
					StringBuffer itemStr = new StringBuffer();
					itemStr.append("{");
					for (int j = 0; j <itemList.size(); j++) {
						TeeSalItem item = itemList.get(j);
						String itemName = item.getItemName();
						if(TeeUtility.isNullorEmpty(item.getItemColumn())){
							continue;
						}
						double tempValue = TeeStringUtil.getDouble(dr.getValueByName(itemName) , 0.0);
						
						itemStr.append(item.getItemColumn() +":" + tempValue + ",");
						
					}
					String itemStr2 = itemStr.toString();
					if(itemStr2.endsWith(",")){
						itemStr2 = itemStr2.substring(0, itemStr2.length() - 1);
					}
					itemStr2 = itemStr2 + "}";
					
					
					salData = (TeeSalDataPerson)TeeJsonUtil.toObject(itemStr2, TeeSalDataPerson.class);

					
					salData.setAllBase(TeeStringUtil.getDouble(dr.getValueByName("基本工资") , 0.0));
					salData.setPensionP(TeeStringUtil.getDouble(dr.getValueByName("养老保险（个人）") , 0.0));
					salData.setPensionU(TeeStringUtil.getDouble(dr.getValueByName("养老保险（单位）") , 0.0));
					salData.setMedicalP(TeeStringUtil.getDouble(dr.getValueByName("医疗保险（个人）") , 0.0));
					salData.setMedicalU(TeeStringUtil.getDouble(dr.getValueByName("医疗保险（单位）") , 0.0));
					salData.setUnemploymentP(TeeStringUtil.getDouble(dr.getValueByName("失业保险（个人）") , 0.0));
					salData.setUnemploymentU(TeeStringUtil.getDouble(dr.getValueByName("失业保险（单位）") , 0.0));
					salData.setInjuriesP(TeeStringUtil.getDouble(dr.getValueByName("工伤保险（个人）") , 0.0));
					salData.setInjuriesU(TeeStringUtil.getDouble(dr.getValueByName("工伤保险（单位）") , 0.0));
					salData.setFertilityP(TeeStringUtil.getDouble(dr.getValueByName("生育保险（个人）") , 0.0));
					salData.setFertilityU(TeeStringUtil.getDouble(dr.getValueByName("生育保险（单位）") , 0.0));
					salData.setHousingP(TeeStringUtil.getDouble(dr.getValueByName("公积金（个人）") , 0.0));
					salData.setHousingU(TeeStringUtil.getDouble(dr.getValueByName("公积金（单位）") , 0.0));
					
					
					//对象转MAP
					Map map = TeeJsonUtil.bean2ProtogenesisMap(salData);
					
					Map mapTemp = new HashMap();
					/**
					 * 计算项
					 */
					for (int j = 0; j <itemList.size(); j++) {
						TeeSalItem item = itemList.get(j);
						String itemName = item.getItemName();
						String itemColumn = item.getItemColumn();
						
						mapTemp.put(itemColumn, TeeStringUtil.getDouble(dr.getValueByName(itemName) , 0.0));

					}
					
					salData.setUser(p);
					salData.setSalMonth(salMonth);
					salData.setSalYear(salYear);
					//对计算项重新赋值
					salData = (TeeSalDataPerson)TeeJsonUtil.convertMap2Bean(salData, TeeSalDataPerson.class, mapTemp);
					double finalPayAmount = TeeStringUtil.getDouble(dr.getValueByName("实发金额") , 0.0);
					salData.setFinalPayAmount(finalPayAmount);
					salData.setAccountId(accountId);
					dataPersonDao.save(salData);
					
					
					smsData.put("content", "已下发["+salYear+"年"+salMonth+"月份工资条]，请注意查收。");
					smsData.put("userListIds",salData.getUser().getUuid());
					smsData.put("moduleNo", "042");
					smsData.put("remindUrl", "/system/subsys/salary/manage/detail.jsp?sid="+salData.getSid());
					
					//发送消息
					smsManager.sendSms(smsData, null);
					
					infoStr = "导入成功";
					color = "black";
			        importSuccessCount++;
			        getImportInfo(sb, personModel, infoStr, color);
				}		
			}else{
				json.setRtState(false);
				json.setRtMsg("文件为空！");
				return json;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		String data = sb.toString();
		if(data.endsWith(",")){
			data = data.substring(0, data.length() - 1);
		}
		data = data + "]";
		
		List<Map<String, String>> list =  TeeJsonUtil.JsonStr2MapList(data);
		json.setRtData(list);
		
		TeeSysLog sysLog = TeeSysLog.newInstance();
	    sysLog.setType("042B");
	  	sysLog.setRemark("批量导入人员工资：" +data);
	    TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		json.setRtState(true);
		json.setRtMsg(importSuccessCount + "");
		return json;
	}
	
	/**
	 * 转工资模型
	 * @param data
	 * @return
	 */
	public TeeSalDataPersonModel parseModel(TeeSalDataPerson data){
		TeeSalDataPersonModel model = new TeeSalDataPersonModel();
		if(data != null){
			BeanUtils.copyProperties(data, model);
			model.setUserId(data.getUser().getUuid());
			model.setUserName(data.getUser().getUserName());
		}
		return model;
	}
	
	/**
	 * 导入转字符串提示
	 * @param sb
	 * @param model
	 * @param infoStr
	 * @param color
	 * @return
	 */
	public  StringBuffer getImportInfo(StringBuffer sb,TeePersonModel model, String infoStr, String color){
		sb.append("{");
      /*  sb.append("deptName:\"" + model.getDeptIdName() + "\"");
        sb.append(",userId:\"" + model.getUserId() + "\"");*/
        sb.append("userName:\"" + model.getUserName() + "\"");
     /*   sb.append(",roleName:\"" + model.getUserRoleStrName()+ "\"");
        sb.append(",userNo:\"" + model.getUserNo()+ "\"");*/

        sb.append(",color:\"" + color + "\"");
        sb.append(",info:\"" + infoStr + "\"");
        sb.append("},");
        return sb;
	}

	/**
	 * 根据id获取工资对象
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request,TeeSalDataPersonModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeSalDataPerson salData = dataPersonDao.getById(model.getSid());
			if(salData !=null){
				BeanUtils.copyProperties(salData, model);
				model.setUserName(salData.getUser().getUserName());
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关客户记录！");
		return json;
	}
	
	/**
	 * 生成工资表
	 * @param request
	 * @param model
	 * @return
	 */
	public void generate(int sid) {
		//获取当前工资任务
		TeeSalFlow salFlow = (TeeSalFlow) simpleDaoSupport.get(TeeSalFlow.class, sid);

		//获取工资帐套
		TeeSalAccount account = accountDao.get(salFlow.getAccountId());
		
		//获取当前工资帐套下的所有人员
		List<TeeSalAccountPerson> accountPersonList = account.getAccountPerson();//获取账套人员
		
		//清空该账帐套下的所有数据
		simpleDaoSupport.executeUpdate("delete from TeeSalDataPerson where accountId="+salFlow.getAccountId()+" and salYear="+salFlow.getSalYear()+" and salMonth="+salFlow.getSalMonth(), null);
		
		//遍历帐套下的人员，依次创建工资信息
		TeeSalDataPerson salData = null;
		
		List<TeeSalItem> salItems = salItemDao.salAllItemListByAccountId(salFlow.getAccountId());
		//查询该帐套是否设置了工资组成
		boolean existsGongZiZuCheng = false;
		for(TeeSalItem salItem:salItems){
			if(salItem.getpIncome()==1){
				existsGongZiZuCheng = true;
				break;
			}
		}
		if(!existsGongZiZuCheng){
			throw new TeeOperationException("该帐套不存在“工资组成”的项目，请到帐套管理-工资项设定中进行设置");
		}
		
		Map smsData = new HashMap();
		for(TeeSalAccountPerson accountPerson:accountPersonList){
			//创建薪资基础数据
			salData = new TeeSalDataPerson();
			salData.setAccountId(salFlow.getAccountId());
			salData.setUser(accountPerson.getUser());
			salData.setSalYear(salFlow.getSalYear());
			salData.setSalMonth(salFlow.getSalMonth());
			salData.setInsuranceDate(new Date());
			
			//进行计算
			salData = calculate(salFlow,accountPerson,salData,salItems);
			
			if(salData!=null){
				dataPersonDao.save(salData);
				
				smsData.put("content", "已下发["+salFlow.getSalYear()+"年"+salFlow.getSalMonth()+"月份工资条]，请注意查收。");
				smsData.put("userListIds",salData.getUser().getUuid() );
				smsData.put("moduleNo", "042");
				smsData.put("remindUrl", "/system/subsys/salary/manage/detail.jsp?sid="+salData.getSid());
				
				//发送消息
				smsManager.sendSms(smsData, null);
			}
			
		}
	}
	
	/**
	 * 提取项
	 * @param params
	 * @param salItem
	 * @return
	 */
	private double Get(Map context,TeeSalItem salItem){
		Calendar dateTime = (Calendar) context.get("datetime");
		
//		if(salItem.getItemType()==1){//当月请假工时
//			
//		}else if(salItem.getItemType()==2){//当月事假工时
//			
//		}else if(salItem.getItemType()==3){//当月病假工时
//			
//		}
		
		return 0;
	}
	
	/**
	 * SQL项
	 * @param params
	 * @param salItem
	 * @return
	 */
	private double Sql(Map context,TeeSalItem salItem){
		return 0;
	}
	
	/**
	 * 计算并生成工资
	 * @param accountPerson
	 */
	public TeeSalDataPerson calculate(TeeSalFlow salFlow,TeeSalAccountPerson accountPerson,TeeSalDataPerson salData,List<TeeSalItem> salItems){
		TeeHrInsurancePara hrInsurancePara = null;
		TeeHumanDoc humanDoc = null;
		TeeHrSalData hrSalData = null;
		
		//获取人员保险套账和保险基数
		humanDoc = (TeeHumanDoc) simpleDaoSupport.unique("from TeeHumanDoc where oaUser.uuid="+accountPerson.getUser().getUuid(), null);
		if(humanDoc==null){
			return null;
		}
		
		hrInsurancePara = humanDoc.getHrInsurancePara();
		if(hrInsurancePara==null){
			return null;
		}
		
		//获取默认个性化基数
		hrSalData = (TeeHrSalData) simpleDaoSupport.unique("from TeeHrSalData where user.uuid="+accountPerson.getUser().getUuid()+" and accountId="+accountPerson.getAccount().getSid(), null);
		if(hrSalData==null){
			return null;
		}
		
		//将所有初始化参数的数据全部复制到当前薪酬数据中
		BeanUtils.copyProperties(hrSalData, salData, new String[]{"user","sid"});
		
		//计算基本工资
		double baseSalary = 0;
		//全额工资（应税工资）
		double allSalary = 0;
		
		//通过薪资定级获取当前职位人员工资
		int salaryLevel = humanDoc.getSalaryLevel();
		String salaryLevelModel = accountPerson.getUser().getUserRole().getSalaryLevelModel();
		baseSalary += accountPerson.getUser().getUserRole().getSalary();
		if(salaryLevelModel!=null && !"".equals(salaryLevelModel) && salaryLevel!=0){
			List<Map<String,String>> sp = TeeJsonUtil.JsonStr2MapList(salaryLevelModel);
			if(salaryLevel<=sp.size()){
				baseSalary+=TeeStringUtil.getDouble(sp.get(salaryLevel-1).get("b"), 0);
			}
		}
		salData.setAllBase(baseSalary);
		
		//走指定的基本工资设定
		if(hrSalData.getAllBase()!=0){
			salData.setAllBase(hrSalData.getAllBase());
		}
		
		
		//获取对象形式的Map
		Map salDataMap = TeeJsonUtil.bean2ProtogenesisMap(salData);
		
		//依次计算
		BigDecimal b = null;
		String itemColumn = null;
		String formula = null;
		
		//上下文
		Map context = new HashMap();
		context.put("year", salFlow.getSalYear());
		context.put("month", salFlow.getSalMonth()<10?"0"+salFlow.getSalMonth():salFlow.getSalMonth());
		context.put("datetime", TeeDateUtil.parseCalendar(context.get("year")+"-"+context.get("month")+"-"+"01"));
		context.put("userSid", salData.getUser().getUuid());
		context.put("userId", salData.getUser().getUserId());
		context.put("roleId", salData.getUser().getUserRole().getUuid());
		context.put("deptId", salData.getUser().getDept().getUuid());
		
		
		//先计算应税工资
		for(TeeSalItem salItem:salItems){
			//处理小数点，不进位
			int np = salItem.getNumberPoint();
			
			formula = salItem.getFormula();//计算公式
			itemColumn = salItem.getItemColumn();
			
			if(salItem.getItemType()==0){//输入项
				b = new BigDecimal(TeeStringUtil.getDouble(salDataMap.get(salItem.getItemColumn()), 0));
				salDataMap.put(salItem.getItemColumn(), b.setScale(np, BigDecimal.ROUND_HALF_UP).doubleValue());
			}else if(salItem.getItemType()==1){//计算项
				
				if(TeeUtility.isNullorEmpty(formula)){
					continue;
				}
				
				//如果当前字段是应税工资，则开始计算五险一金
				if(salItem.getpIncome()==1){
					formula = formula.replace("[", "").replace("]", "");//替换中括号
					String itemColumnTemp = itemColumn + "=" + formula;
					MVEL.eval(itemColumnTemp, salDataMap);//处理表达式
					allSalary += TeeStringUtil.getDouble(salDataMap.get(itemColumn), 0);
					
					
					//优先计算个性化基数
					//公积金，默认走社保参数的基数
					if(hrSalData.getGjjBase()==0){
						if(hrInsurancePara.getGjjBase()==0){//走全额保险
							
							if(allSalary>hrInsurancePara.getGjjMax()){
								salData.setGjjBase(hrInsurancePara.getGjjMax());
								salDataMap.put("gjjBase", hrInsurancePara.getGjjMax());
							}else if(allSalary<hrInsurancePara.getGjjMin()){
								salData.setGjjBase(hrInsurancePara.getGjjMin());
								salDataMap.put("gjjBase", hrInsurancePara.getGjjMin());
							}else{
								salData.setGjjBase(allSalary);
								salDataMap.put("gjjBase", allSalary);
							}
							
						}else{//指定基数
							salData.setGjjBase(hrInsurancePara.getGjjBase());
							salDataMap.put("gjjBase", hrInsurancePara.getGjjBase());
						}
					}else{
						salData.setGjjBase(hrSalData.getGjjBase());
						salDataMap.put("gjjBase", hrSalData.getGjjBase());
					}
					
					
					//社保，默认走社保参数的基数
					if(hrSalData.getSbBase()==0){
						if(hrInsurancePara.getSbBase()==0){//走全额保险
							
							if(allSalary>hrInsurancePara.getSbMax()){
								salData.setSbBase(hrInsurancePara.getSbMax());
								salDataMap.put("sbBase", hrInsurancePara.getSbMax());
							}else if(allSalary<hrInsurancePara.getSbMin()){
								salData.setSbBase(hrInsurancePara.getSbMin());
								salDataMap.put("sbBase", hrInsurancePara.getSbMin());
							}else{
								salData.setSbBase(allSalary);
								salDataMap.put("sbBase", allSalary);
							}
							
						}else{//指定基数
							salData.setSbBase(hrInsurancePara.getSbBase());
							salDataMap.put("sbBase", hrInsurancePara.getSbBase());
						}
					}else{
						salData.setSbBase(hrSalData.getSbBase());
						salDataMap.put("sbBase", hrSalData.getSbBase());
					}
					
					
					//计算养老保险
					salData.setPensionP(salData.getSbBase()*hrInsurancePara.getPensionPPay()/100+hrInsurancePara.getPensionPPayAdd());
					salData.setPensionU(salData.getSbBase()*hrInsurancePara.getPensionUPay()/100+hrInsurancePara.getPensionUPayAdd());
					salDataMap.put("pensionP", salData.getPensionP());
					salDataMap.put("pensionU", salData.getPensionU());
					
					//计算医疗保险
					salData.setMedicalP(salData.getSbBase()*hrInsurancePara.getHealthPPay()/100+hrInsurancePara.getHealthPPayAdd());
					salData.setMedicalU(salData.getSbBase()*hrInsurancePara.getHealthUPay()/100+hrInsurancePara.getHealthUPayAdd());
					salDataMap.put("medicalP", salData.getMedicalP());
					salDataMap.put("medicalU", salData.getMedicalU());
					
					//计算失业保险
					salData.setUnemploymentP(salData.getSbBase()*hrInsurancePara.getUnemploymentPPay()/100+hrInsurancePara.getUnemploymentPPayAdd());
					salData.setUnemploymentU(salData.getSbBase()*hrInsurancePara.getUnemploymentUPay()/100+hrInsurancePara.getUnemploymentUPayAdd());
					salDataMap.put("unemploymentP", salData.getUnemploymentP());
					salDataMap.put("unemploymentU", salData.getUnemploymentU());
					
					//计算工伤保险
					salData.setInjuriesP(salData.getSbBase()*hrInsurancePara.getInjuryPPay()/100+hrInsurancePara.getInjuryPPayAdd());
					salData.setInjuriesU(salData.getSbBase()*hrInsurancePara.getInjuryUPay()/100+hrInsurancePara.getInjuryUPayAdd());
					salDataMap.put("injuriesP", salData.getInjuriesP());
					salDataMap.put("injuriesU", salData.getInjuriesU());
					
					//计算生育保险
					salData.setFertilityP(salData.getSbBase()*hrInsurancePara.getMaternityPPay()/100+hrInsurancePara.getMaternityPPayAdd());
					salData.setFertilityU(salData.getSbBase()*hrInsurancePara.getMaternityUPay()/100+hrInsurancePara.getMaternityUPayAdd());
					salDataMap.put("fertilityP", salData.getFertilityP());
					salDataMap.put("fertilityU", salData.getFertilityU());
					
					//计算公积金
					salData.setHousingP(salData.getGjjBase()*hrInsurancePara.getHousingPPay()/100+hrInsurancePara.getHousingPPayAdd());
					salData.setHousingU(salData.getGjjBase()*hrInsurancePara.getHousingUPay()/100+hrInsurancePara.getHousingUPayAdd());
					salDataMap.put("housingP", salData.getHousingP());
					salDataMap.put("housingU", salData.getHousingU());
					
					
					continue;
				}
				
				//如果当前字段是计算个税，则开始计算个税
				if(formula.startsWith("PFax(")){
					formula = formula.replace("[", "").replace("]", "");//替换中括号
					formula = formula.substring(5,formula.length()-1);
					String itemColumnTemp = itemColumn + "=" + formula;
					MVEL.eval(itemColumnTemp, salDataMap);//处理表达式
					double A = TeeStringUtil.getDouble(salDataMap.get(itemColumn), 0)-3500;
					if(A>0){//缴纳个人所得税
						//获取个人计算税率
						String HR_P_INCOME_MODEL = TeeSysProps.getString("HR_P_INCOME_MODEL");
						List<Map<String,String>> models = TeeJsonUtil.JsonStr2MapList(HR_P_INCOME_MODEL);
						Map vars = new HashMap();
						double minData=0;
						double maxData=0;
						
						for(Map<String,String> model:models){
							
							minData=TeeStringUtil.getDouble(model.get("minData"), 0.0);
							maxData=TeeStringUtil.getDouble(model.get("maxData"), 0.0);
							if(maxData==0){
								maxData=Double.MAX_VALUE;
							}
							
							if(A>minData&&A<=maxData){
								salData.setGeshui(A*Double.parseDouble(model.get("rate"))-Double.parseDouble(model.get("reduce")));
								salDataMap.put("geshui", salData.getGeshui());
								A = salData.getGeshui();
								break;
							}
						}
					}else{
						A = 0;
					}
					salDataMap.put(itemColumn, A);
					b = new BigDecimal(TeeStringUtil.getDouble(salDataMap.get(salItem.getItemColumn()), 0));
					salDataMap.put(salItem.getItemColumn(), b.setScale(np, BigDecimal.ROUND_HALF_UP).doubleValue());
					continue;
				}
				
				formula = formula.replace("[", "").replace("]", "");//替换中括号
				String itemColumnTemp = itemColumn + "=" + formula;
				MVEL.eval(itemColumnTemp, salDataMap);//处理表达式
				b = new BigDecimal(TeeStringUtil.getDouble(salDataMap.get(salItem.getItemColumn()), 0));
				salDataMap.put(salItem.getItemColumn(), b.setScale(np, BigDecimal.ROUND_HALF_UP).doubleValue());
			}else if(salItem.getItemType()==2){//提取项
				
				salDataMap.put(itemColumn, Get(context,salItem));
				b = new BigDecimal(TeeStringUtil.getDouble(salDataMap.get(salItem.getItemColumn()), 0));
				salDataMap.put(salItem.getItemColumn(), b.setScale(np, BigDecimal.ROUND_HALF_UP).doubleValue());
				
			}else if(salItem.getItemType()==3){//SQL项
				
				salDataMap.put(itemColumn, Sql(context,salItem));
				b = new BigDecimal(TeeStringUtil.getDouble(salDataMap.get(salItem.getItemColumn()), 0));
				salDataMap.put(salItem.getItemColumn(), b.setScale(np, BigDecimal.ROUND_HALF_UP).doubleValue());
				
			}
			
		}
		
		
		try {
			salData = (TeeSalDataPerson)TeeJsonUtil.convertMap2Bean(salData, TeeSalDataPerson.class, salDataMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return salData;
	}


	/**
	 * 更新人员薪资
	 * @param request
	 * @param model
	 * @return
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 */
	public TeeJson update(HttpServletRequest request,TeeSalDataPersonModel model) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		TeeJson json = new TeeJson();
		Map map = TeeServletUtility.getParamMap(request);
		TeeSalDataPerson salData = new TeeSalDataPerson();
		if(model.getSid() > 0){
			salData  = dataPersonDao.getById(model.getSid());
			if(salData != null){
				
				//BeanUtils.copyProperties(model, salData);
				salData = (TeeSalDataPerson) TeeJsonUtil.convertMap2Bean(salData, TeeSalDataPerson.class, map);
				dataPersonDao.save(salData);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关信息！");
				return json;
			}
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("更新成功！");
		return json;
	}
}
