package com.tianee.oa.core.base.pm.service;

import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.base.hr.TeeHrCodeManager;
import com.tianee.oa.core.base.pm.bean.TeeHumanDoc;
import com.tianee.oa.core.base.pm.bean.TeePmCustomerField;
import com.tianee.oa.core.base.pm.dao.TeeHumanDocDao;
import com.tianee.oa.core.base.pm.model.TeeHumanDocModel;
import com.tianee.oa.core.base.pm.model.TeePmCustomerFieldModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.core.org.service.TeePersonManagerI;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeModelIdConst;
import com.tianee.oa.subsys.salManage.bean.TeeHrInsurancePara;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeManagerPostPersonDataPrivModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeHumanDocService extends TeeBaseService{
	
	@Autowired
	TeeHumanDocDao humanDao;
	@Autowired
	TeePersonDao personDao;
	@Autowired
	TeeDeptDao deptDao;
	@Autowired
	TeeUserRoleDao roleDao;
	@Autowired
	TeePersonService personService;
	
	
	@Autowired
	TeePersonManagerI personManagerI;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;

	public void setAttachmentDao(TeeAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	public TeeAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}
	
	public TeeHumanDoc addHumanDoc(HttpServletRequest request,TeeHumanDocModel humanDocModel) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanDoc humanDoc =new TeeHumanDoc();
		String birthdayDesc=humanDocModel.getBirthdayDesc();
		String graduateDateDesc = humanDocModel.getGraduateDateDesc();
		String joinDateDesc = humanDocModel.getJoinDateDesc();
		String joinPartyDateDesc = humanDocModel.getJoinPartyDateDesc();
		String insuranceName = humanDocModel.getInsuranceName();
		Map<String, String> map1 = humanDocModel.getMap();//自定义字段
		List<TeePmCustomerFieldModel> list1 = humanDocModel.getList();
	
		if(!TeeUtility.isNullorEmpty(insuranceName)){
			List<TeeHrInsurancePara> find = simpleDaoSupport.find("from TeeHrInsurancePara where insuranceName='"+insuranceName+"'", null);
            if(find!=null && find.size()>0){
            	TeeHrInsurancePara para = find.get(0);
            	humanDoc.setHrInsurancePara(para);
            }
		}
		if(!TeeUtility.isNullorEmpty(birthdayDesc)){
			Calendar cl = Calendar.getInstance();
			cl.setTime(formatter.parse(birthdayDesc));
			humanDoc.setBirthday(cl);
		}
		if(!TeeUtility.isNullorEmpty(graduateDateDesc)){
			Calendar cl1 = Calendar.getInstance();
			cl1.setTime(formatter.parse(graduateDateDesc));
			humanDoc.setGraduateDate(cl1);
		}
		if(!TeeUtility.isNullorEmpty(joinDateDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(joinDateDesc));
			humanDoc.setJoinDate(cl2);
		}
		if(!TeeUtility.isNullorEmpty(joinPartyDateDesc)){
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(formatter.parse(joinPartyDateDesc));
			humanDoc.setJoinPartyDate(cl3);
		}
		BeanUtils.copyProperties(humanDocModel, humanDoc);	
		TeePerson person = new TeePerson();
		if(humanDocModel.getUserId()!=0){
			person = (TeePerson)personDao.get(humanDocModel.getUserId());
			humanDoc.setOaUser(person);
		}
		TeeDepartment dept = new TeeDepartment();
		if(humanDocModel.getDeptId()!=0){
			dept = (TeeDepartment)deptDao.get(humanDocModel.getDeptId());
			humanDoc.setDept(dept);
		}
		TeeUserRole role = new TeeUserRole();
		if(humanDocModel.getRoleId()!=0){
			role = (TeeUserRole)roleDao.get(humanDocModel.getRoleId());
			humanDoc.setRole(role);
		}
		TeeHrInsurancePara hrInsurancePara = new TeeHrInsurancePara();
		if(humanDocModel.getInsuranceId()!=0){
			hrInsurancePara.setSid(humanDocModel.getInsuranceId());
			humanDoc.setHrInsurancePara(hrInsurancePara);
		}
		
		//是oa用户，如果未选择，则自动创建用户信息
		if("true".equals(humanDocModel.getIsOaUser()) && humanDocModel.getUserId()==0){
			
			TeePerson personModel = new TeePerson();
			HanyuPinyinOutputFormat pyFormat = new HanyuPinyinOutputFormat();
	        pyFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);    //设置样式
	        pyFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	        pyFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
	        personModel.setUserId(PinyinHelper.toHanyuPinyinString(humanDocModel.getPersonName(), pyFormat, "")+new Random().nextInt(100000));
	        personModel.setUserName(humanDocModel.getPersonName());
	        TeeDepartment deptModel = new TeeDepartment();
	        deptModel.setUuid(humanDocModel.getDeptId());
	        personModel.setDept(deptModel);
	        TeeUserRole roleModel = new TeeUserRole();
	        roleModel.setUuid(humanDocModel.getRoleId());
	        personModel.setUserRole(roleModel);
	        if("男".equals(humanDocModel.getGender())){
	        	personModel.setSex("0");
	        }else{
	        	personModel.setSex("1");
	        }
	        personModel.setBirthday(TeeDateUtil.parseDate(humanDocModel.getBirthdayDesc()));
	        personModel.setTheme(TeeConst.PERSON_DEFAULT_THEME);
	        personModel.setMobilNo(humanDocModel.getMobileNo());
	        personModel.setEmail(humanDocModel.getEmail());
	        personModel.setPassword(TeeConst.EMPTY_PWD);
	        personModel.setNotViewUser("0");
	        personModel.setDeleteStatus("0");
	        
	        simpleDaoSupport.save(personModel);
	        
	        humanDoc.setOaUser(personModel);
		}else if("true".equals(humanDocModel.getIsOaUser()) && humanDocModel.getUserId()!=0){
			TeePerson targetPerson = personService.selectByUuid(humanDocModel.getUserId());
			targetPerson.setUserName(humanDocModel.getPersonName());
			if("男".equals(humanDocModel.getGender())){
				targetPerson.setSex("0");
	        }else{
	        	targetPerson.setSex("1");
	        }
			if(targetPerson.getUserRole().getUuid()!=humanDocModel.getRoleId()){
				throw new TeeOperationException("所关联的OA用户角色为["+targetPerson.getUserRole().getRoleName()+"],与您选择的角色不符合!");
			}
			
			targetPerson.setBirthday(TeeDateUtil.parseDate(humanDocModel.getBirthdayDesc()));
			targetPerson.setMobilNo(humanDocModel.getMobileNo());
			targetPerson.setEmail(humanDocModel.getEmail());
		}
		
		Serializable save = humanDao.save(humanDoc);
		int humId = TeeStringUtil.getInteger(save, 0);
		if(list1!=null && list1.size()>0){
			String sql1="";
			for(TeePmCustomerFieldModel pm:list1){
				String str = map1.get("EXTRA_"+pm.getSid());
				sql1+="EXTRA_"+pm.getSid()+"='"+str+"',";
			}
			sql1=sql1.substring(0, sql1.length()-1);
			sql1="update PERSONNEL_MANAGEMENT set "+sql1+" where sid="+humId;
			simpleDaoSupport.executeNativeUpdate(sql1,null);
		}
		//为自定义字段赋值
		int sid = humanDoc.getSid();
		//处理自定义字段
		Map map = TeeServletUtility.getParamMap(request);
		Map<String,String> mapExtra = new HashMap<String,String>();
		Set<String> set = map.keySet();
		for (String key : set) {
			if(key.startsWith("EXTRA_")){
				mapExtra.put(key,(String) map.get(key));
			}
		}
		List  list=new ArrayList();
		if(mapExtra.keySet().size()>0){
			String sql= "update PERSONNEL_MANAGEMENT set";
			int count = 0;
			for (String key2 : mapExtra.keySet()) {
				count++;
				if(count==mapExtra.keySet().size()){
					sql += " "+ key2+" = ?";
				}else{
					sql +=" "+ key2+" = ? ,";
				}
				list.add(mapExtra.get(key2));
			}
			sql += " "+"where sid = "+sid;
			simpleDaoSupport.executeNativeUpdate(sql,list.toArray());
		}
		
		return humanDoc;
	}
	
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		//String searchType = (String)requestDatas.get("searchType");//查询类型   1：查询在职人员  2：离职或退休人员
		
		String personName = (String)requestDatas.get("personName");
		String idCard = (String)requestDatas.get("idCard");
		String nativePlace = (String)requestDatas.get("nativePlace");
		String codeNumber = (String)requestDatas.get("codeNumber");
		String roleId = (String)requestDatas.get("roleId");
		String userId = (String)requestDatas.get("userId");
		String deptId = (String)requestDatas.get("deptId");
		String workNumber = (String)requestDatas.get("workNumber");
		String statusType = (String)requestDatas.get("statusType");
		String employeeType = (String)requestDatas.get("employeeType");
		String englishName = (String)requestDatas.get("englishName");
		String gender = (String)requestDatas.get("gender");
		String birthdayDesc1 = (String)requestDatas.get("birthdayDesc1");
		String birthdayDesc2 = (String)requestDatas.get("birthdayDesc2");
		String ethnicity = (String)requestDatas.get("ethnicity");
		String defaultAnnualLeaveDays = (String)requestDatas.get("defaultAnnualLeaveDays");
		String postState = (String)requestDatas.get("postState");
		String marriage = (String)requestDatas.get("marriage");
		String graduateSchool = (String)requestDatas.get("graduateSchool");
		String household = (String)requestDatas.get("household");
		String health = (String)requestDatas.get("health");
		String householdPlace = (String)requestDatas.get("householdPlace");
		String joinDateDesc1 = (String)requestDatas.get("joinDateDesc1");
		String joinDateDesc2 = (String)requestDatas.get("joinDateDesc2");
		String politics = (String)requestDatas.get("politics");
		String joinPartyDateDesc1 = (String)requestDatas.get("joinPartyDateDesc1");
		String joinPartyDateDesc2 = (String)requestDatas.get("joinPartyDateDesc2");
		String major = (String)requestDatas.get("major");
		String graduateDateDesc1 = (String)requestDatas.get("graduateDateDesc1");
		String graduateDateDesc2 = (String)requestDatas.get("graduateDateDesc2");
		String educationDegree = (String)requestDatas.get("educationDegree");
		String degree = (String)requestDatas.get("degree");
		String mobileNo = (String)requestDatas.get("mobileNo");
		String telNo = (String)requestDatas.get("telNo");
		String email = (String)requestDatas.get("email");
		String qqNo = (String)requestDatas.get("qqNo");
		String msn = (String)requestDatas.get("msn");
		String address = (String)requestDatas.get("address");
		String otherAddress = (String)requestDatas.get("otherAddress");
		
		
		String hql = "from TeeHumanDoc human where 1=1 ";
		
	/*	if("1".equals(searchType)){//在职
			hql+=" and human.statusType = '在职'";
		}else if("2".equals(searchType)){//离职
			hql+=" and human.statusType != '在职'";
		}
		*/
		List param = new ArrayList();
		//姓名查询
		if(!TeeUtility.isNullorEmpty(personName)){
			hql+=" and human.personName like '%"+TeeDbUtility.formatString(personName)+"%'";
		}
		//身份证查询
		if(!TeeUtility.isNullorEmpty(idCard)){
			hql+=" and human.idCard like '%"+TeeDbUtility.formatString(personName)+"%'";
		}
		//籍贯查询
		if(!TeeUtility.isNullorEmpty(nativePlace)){
			hql+=" and human.nativePlace like '%"+TeeDbUtility.formatString(nativePlace)+"%'";
		}
		//档案编号查询
		if(!TeeUtility.isNullorEmpty(codeNumber)){
			hql+=" and human.codeNumber like '%"+TeeDbUtility.formatString(codeNumber)+"%'";
		}
		//角色查询
		if(!TeeUtility.isNullorEmpty(roleId)){
			hql+=" and human.role.uuid = "+roleId;
		}
		//用户查询
		if(!TeeUtility.isNullorEmpty(userId)){
			hql+=" and human.oaUser.uuid = "+userId;
		}
		//部门查询
		if(!TeeUtility.isNullorEmpty(deptId)){
			TeeDepartment dept = (TeeDepartment) simpleDaoSupport.get(TeeDepartment.class, Integer.parseInt(deptId));
			String fullDeptId = dept.getDeptFullId();
		
			String hql2 = "from TeeDepartment where 1=1 and deptFullId like ?";
			List<TeeDepartment> listDept = simpleDaoSupport.executeQuery(hql2, new Object[]{"%"+fullDeptId+"/"+"%"});
			String ids = deptId;
			if(!TeeUtility.isNullorEmpty(listDept)){
				for (TeeDepartment teeDepartment : listDept) {
					ids += ","+ teeDepartment.getUuid();
				}
				if(!TeeUtility.isNullorEmpty(ids)){
		    		if(ids.endsWith(",")){
		    			ids=ids.substring(0, ids.length()-1);
		    		}
		    		hql+=" and human.dept.uuid in ("+ ids+")";
		    		
		    	}else{
		    		hql+=" and human.dept.uuid = "+deptId;
			    }
				
			}else{
				hql+=" and and human.dept.uuid = "+deptId;
			}
			
		}
		//工号查询
		if(!TeeUtility.isNullorEmpty(workNumber)){
			hql+=" and human.workNumber like '%"+TeeDbUtility.formatString(workNumber)+"%'";
		}
		//员工状态查询
		if(!TeeUtility.isNullorEmpty(statusType)){
			if(!"全部".equals(statusType)){
				hql+=" and human.statusType like '%"+TeeDbUtility.formatString(statusType)+"%'";
			}else{
				hql +=" ";
			}
		}
		
		//员工类型查询
		if(!TeeUtility.isNullorEmpty(employeeType)){
			if(!"全部".equals(employeeType)){
				hql+=" and human.employeeType like '%"+TeeDbUtility.formatString(employeeType)+"%'";
			}else{
				hql +=" ";
			}
		
		}
		//英文名查询
		if(!TeeUtility.isNullorEmpty(englishName)){
			hql+=" and human.englishName like '%"+TeeDbUtility.formatString(englishName)+"%'";
		}
		//性别查询
		if(!TeeUtility.isNullorEmpty(gender)){
			if(!"全部".equals(gender)){
				hql+=" and human.gender like '%"+TeeDbUtility.formatString(gender)+"%'";
			}else{
				hql+=" ";
			}
		}
		//出生日期查询
		if(!TeeUtility.isNullorEmpty(birthdayDesc1)){
			hql+=" and human.birthday >= ?";
			param.add(TeeDateUtil.parseCalendar(birthdayDesc1+" 00:00:00"));
		}
		if(!TeeUtility.isNullorEmpty(birthdayDesc2)){
			hql+=" and human.birthday <= ?";
			param.add(TeeDateUtil.parseCalendar(birthdayDesc2+" 23:59:59"));
		}
		//默认年假天数查询
		if(!TeeUtility.isNullorEmpty(defaultAnnualLeaveDays)){
			hql+=" and human.defaultAnnualLeaveDays = "+defaultAnnualLeaveDays;
		}
		//职务查询
		if(!TeeUtility.isNullorEmpty(postState)){
			hql+=" and human.postState like '%"+TeeDbUtility.formatString(postState)+"%'";
		}
		//婚姻状况查询
		if(!TeeUtility.isNullorEmpty(marriage)){
			hql+=" and human.marriage like '%"+TeeDbUtility.formatString(marriage)+"%'";
		}
		//毕业院校查询
		if(!TeeUtility.isNullorEmpty(graduateSchool)){
			hql+=" and human.graduateSchool like '%"+TeeDbUtility.formatString(graduateSchool)+"%'";
		}
		//户口类型查询
		if(!TeeUtility.isNullorEmpty(household)){
			hql+=" and human.household like '%"+TeeDbUtility.formatString(household)+"%'";
		}
		//健康状况查询
		if(!TeeUtility.isNullorEmpty(health)){
			hql+=" and human.health like '%"+TeeDbUtility.formatString(health)+"%'";
		}
		//户口所在地查询
		if(!TeeUtility.isNullorEmpty(householdPlace)){
			hql+=" and human.householdPlace like '%"+TeeDbUtility.formatString(householdPlace)+"%'";
		}
		//入职时间查询
		if(!TeeUtility.isNullorEmpty(joinDateDesc1)){
			hql+=" and human.joinDate >= ?";
			param.add(TeeDateUtil.parseCalendar(joinDateDesc1+" 00:00:00"));
		}
		if(!TeeUtility.isNullorEmpty(joinDateDesc2)){
			hql+=" and human.joinDate <= ?";
			param.add(TeeDateUtil.parseCalendar(joinDateDesc2+" 23:59:59"));
		}
		//政治面貌查询
		if(!TeeUtility.isNullorEmpty(politics)){
			if(!"全部".equals(politics)){
				hql+=" and human.politics like '%"+TeeDbUtility.formatString(politics)+"%'";
			}else{
				hql+=" ";
			}
		}
		//入党（团）时间查询
		if(!TeeUtility.isNullorEmpty(joinPartyDateDesc1)){
			hql+=" and human.joinPartyDate >= ?";
			param.add(TeeDateUtil.parseCalendar(joinPartyDateDesc1+" 00:00:00"));
		}
		if(!TeeUtility.isNullorEmpty(joinPartyDateDesc2)){
			hql+=" and human.joinPartyDate <= ?";
			param.add(TeeDateUtil.parseCalendar(joinPartyDateDesc2+" 23:59:59"));
		}
		//专业查询
		if(!TeeUtility.isNullorEmpty(major)){
			hql+=" and human.major like '%"+TeeDbUtility.formatString(major)+"%'";
		}
		//毕业时间查询
		if(!TeeUtility.isNullorEmpty(graduateDateDesc1)){
			hql+=" and human.graduateDate >= ?";
			param.add(TeeDateUtil.parseCalendar(graduateDateDesc1+" 00:00:00"));
		}
		if(!TeeUtility.isNullorEmpty(graduateDateDesc2)){
			hql+=" and human.graduateDate <= ?";
			param.add(TeeDateUtil.parseCalendar(graduateDateDesc2+" 23:59:59"));
		}
		//学历查询
		if(!TeeUtility.isNullorEmpty(educationDegree)){
			if(!"全部".equals(educationDegree)){
				hql+=" and human.educationDegree like '%"+TeeDbUtility.formatString(educationDegree)+"%'";
			}else{
				hql+=" ";
			}
		}
		//学位查询
		if(!TeeUtility.isNullorEmpty(degree)){
			if(!"全部".equals(degree)){
				hql+=" and human.degree like '%"+TeeDbUtility.formatString(degree)+"%'";
			}else{
				hql+=" ";
			}
		}
		//手机号码查询
		if(!TeeUtility.isNullorEmpty(mobileNo)){
			hql+=" and human.mobileNo like '%"+TeeDbUtility.formatString(mobileNo)+"%'";
		}
		//电话号码查询
		if(!TeeUtility.isNullorEmpty(telNo)){
			hql+=" and human.telNo like '%"+TeeDbUtility.formatString(telNo)+"%'";
		}
		//电子邮件查询
		if(!TeeUtility.isNullorEmpty(email)){
			hql+=" and human.email like '%"+TeeDbUtility.formatString(email)+"%'";
		}
		//QQ号码查询
		if(!TeeUtility.isNullorEmpty(qqNo)){
			hql+=" and human.qqNo like '%"+TeeDbUtility.formatString(qqNo)+"%'";
		}
		//家庭地址查询
		if(!TeeUtility.isNullorEmpty(address)){
			hql+=" and human.address like '%"+TeeDbUtility.formatString(address)+"%'";
		}
		//其他联系地址查询
		if(!TeeUtility.isNullorEmpty(otherAddress)){
			hql+=" and human.otherAddress like '%"+TeeDbUtility.formatString(otherAddress)+"%'";
		}
		
		List<TeeHumanDocModel> models = new ArrayList<TeeHumanDocModel>();
		
		//数据管理权限
		TeeManagerPostPersonDataPrivModel dataPrivModel = personManagerI.getManagerPostPersonIdsPriv(requestDatas, TeeModelIdConst.HR_PERSONAL_RECORDS_POST_PRIV, "0");
		
		TeePerson loginUser = (TeePerson) requestDatas.get(TeeConst.LOGIN_USER);
		
		if(!TeePersonService.checkIsAdminPriv(loginUser)){
			if(dataPrivModel.getPrivType().equals("0")){//只能看到自己创建的
				dataGridJson.setTotal(0L);
				dataGridJson.setRows(models);
				return dataGridJson;
			}else if(dataPrivModel.getPrivType().equals("ALL")){//所有
				
			}else{
				List<Integer> pIdList = dataPrivModel.getPersonIds();//获取权限
				
				if(pIdList.size() > 0){
					String personIdsSql = TeeDbUtility.IN("human.oaUser.uuid", pIdList);
					hql+=" and "+personIdsSql;
				}else{
					dataGridJson.setTotal(0L);
					dataGridJson.setRows(models);
					return dataGridJson;
				}
			}
		}
		
		
		
		List<TeeHumanDoc> humanDocs = humanDao.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		
		long total = humanDao.countByList("select count(*) "+hql, param);
		for(TeeHumanDoc humanDoc:humanDocs){
			TeeHumanDocModel m = new TeeHumanDocModel();
			if(humanDoc.getBirthday()!=null){
				m.setBirthdayDesc(formatter.format(humanDoc.getBirthday().getTime()));
			}
			if(humanDoc.getGraduateDate()!=null){
				m.setGraduateDateDesc(formatter.format(humanDoc.getGraduateDate().getTime()));
			}
			if(humanDoc.getJoinDate()!=null){
				m.setJoinDateDesc(formatter.format(humanDoc.getJoinDate().getTime()));
			}
			if(humanDoc.getJoinPartyDate()!=null){
				m.setJoinPartyDateDesc(formatter.format(humanDoc.getJoinPartyDate().getTime()));
			}
			BeanUtils.copyProperties(humanDoc, m);	
			if(humanDoc.getOaUser()!=null){
				m.setUserId(humanDoc.getOaUser().getUuid());
				m.setUserName(humanDoc.getOaUser().getUserName());
			}
			if(humanDoc.getDept()!=null){
				m.setDeptId(humanDoc.getDept().getUuid());
				m.setDeptIdName(humanDoc.getDept().getDeptName());
			}
			String statusTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_STATUS_TYPE", humanDoc.getStatusType());
			String employeeTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_EMPLOYEE_TYPE", humanDoc.getEmployeeType());
			String marriageDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_MARRIAGE", humanDoc.getMarriage());
			String householdDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_HOUSEHOLD", humanDoc.getHousehold());
			String politicsDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_POLITICS", humanDoc.getPolitics());
			String educationDegreeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_EDUCATIONDEGREE", humanDoc.getEducationDegree());
			String degreeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_DEGREE", humanDoc.getDegree());
			m.setStatusTypeDesc(statusTypeDesc);
			m.setEmployeeTypeDesc(employeeTypeDesc);
			m.setMarriageDesc(marriageDesc);
			m.setHouseholdDesc(householdDesc);
			m.setPoliticsDesc(politicsDesc);
			m.setEducationDegreeDesc(educationDegreeDesc);
			m.setDegreeDesc(degreeDesc);
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	public TeeEasyuiDataGridJson datagrid2(TeeDataGridModel dm,Map requestDatas){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String deptId = (String)requestDatas.get("deptId");
		//String searchType = (String)requestDatas.get("searchType");//查询类型   1：查询在职人员  2：离职或退休人员
		
		String personName = (String)requestDatas.get("personName");
		String idCard = (String)requestDatas.get("idCard");
		String nativePlace = (String)requestDatas.get("nativePlace");
		String codeNumber = (String)requestDatas.get("codeNumber");
		String roleId = (String)requestDatas.get("roleId");
		String userId = (String)requestDatas.get("userId");
		String workNumber = (String)requestDatas.get("workNumber");
		String statusType = (String)requestDatas.get("statusType");
		String employeeType = (String)requestDatas.get("employeeType");
		String englishName = (String)requestDatas.get("englishName");
		String gender = (String)requestDatas.get("gender");
		String birthdayDesc1 = (String)requestDatas.get("birthdayDesc1");
		String birthdayDesc2 = (String)requestDatas.get("birthdayDesc2");
		String ethnicity = (String)requestDatas.get("ethnicity");
		String defaultAnnualLeaveDays = (String)requestDatas.get("defaultAnnualLeaveDays");
		String postState = (String)requestDatas.get("postState");
		String marriage = (String)requestDatas.get("marriage");
		String graduateSchool = (String)requestDatas.get("graduateSchool");
		String household = (String)requestDatas.get("household");
		String health = (String)requestDatas.get("health");
		String householdPlace = (String)requestDatas.get("householdPlace");
		String joinDateDesc1 = (String)requestDatas.get("joinDateDesc1");
		String joinDateDesc2 = (String)requestDatas.get("joinDateDesc2");
		String politics = (String)requestDatas.get("politics");
		String joinPartyDateDesc1 = (String)requestDatas.get("joinPartyDateDesc1");
		String joinPartyDateDesc2 = (String)requestDatas.get("joinPartyDateDesc2");
		String major = (String)requestDatas.get("major");
		String graduateDateDesc1 = (String)requestDatas.get("graduateDateDesc1");
		String graduateDateDesc2 = (String)requestDatas.get("graduateDateDesc2");
		String educationDegree = (String)requestDatas.get("educationDegree");
		String degree = (String)requestDatas.get("degree");
		String mobileNo = (String)requestDatas.get("mobileNo");
		String telNo = (String)requestDatas.get("telNo");
		String email = (String)requestDatas.get("email");
		String qqNo = (String)requestDatas.get("qqNo");
		String msn = (String)requestDatas.get("msn");
		String address = (String)requestDatas.get("address");
		String otherAddress = (String)requestDatas.get("otherAddress");
		
		String sql = "";
		String sql2 = "select * from PERSONNEL_MANAGEMENT PM where 1=1 ";
		List param = new ArrayList();
		
		//姓名查询
		if(!TeeUtility.isNullorEmpty(personName)){
			sql += " and PM.PERSON_NAME like ?";
			param.add("%"+TeeDbUtility.formatString(personName)+"%");
	    }
		//身份证查询
		if(!TeeUtility.isNullorEmpty(idCard)){
			sql+=" and PM.IDCARD like ?";
			param.add("%"+TeeDbUtility.formatString(idCard)+"%");
		}
		//籍贯查询
		if(!TeeUtility.isNullorEmpty(nativePlace)){
			sql+=" and PM.NATIVE_PLACE like ?";
			param.add("%"+TeeDbUtility.formatString(nativePlace)+"%");
		}
		//档案编号查询
		if(!TeeUtility.isNullorEmpty(codeNumber)){
			sql+=" and PM.CODE_NUMBER like ?";
			param.add("%"+TeeDbUtility.formatString(codeNumber)+"%");
		}
		//角色查询
		if(!TeeUtility.isNullorEmpty(roleId)){
			sql+=" and PM.ROLE = ?";
			param.add(roleId);
		}
		//用户查询
		if(!TeeUtility.isNullorEmpty(userId)){
			sql+=" and PM.OA_USER = ?";
			param.add(userId);
		}
		//部门查询
		if(!TeeUtility.isNullorEmpty(deptId)){
			TeeDepartment dept = (TeeDepartment) simpleDaoSupport.get(TeeDepartment.class, Integer.parseInt(deptId));
			String fullDeptId = dept.getDeptFullId();
		
			String hql2 = "from TeeDepartment where 1=1 and deptFullId like ?";
			List<TeeDepartment> listDept = simpleDaoSupport.executeQuery(hql2, new Object[]{"%"+fullDeptId+"/"+"%"});
			String ids = deptId;
			if(!TeeUtility.isNullorEmpty(listDept)){
				for (TeeDepartment teeDepartment : listDept) {
					ids += ","+ teeDepartment.getUuid();
				}
				if(!TeeUtility.isNullorEmpty(ids)){
		    		if(ids.endsWith(",")){
		    			ids=ids.substring(0, ids.length()-1);
		    		}
		    		sql+=" and PM.DEPT in ("+ ids+")";
		    		
		    	}else{
		    		sql+=" and PM.DEPT = ?";
		    		param.add(deptId);
			    }
				
			}else{
				sql+=" and PM.DEPT = ?";
				param.add(deptId);
			}
			
		}
		//工号查询
		if(!TeeUtility.isNullorEmpty(workNumber)){
			sql+=" and PM.WORK_NUMBER like ?";
			param.add("%"+TeeDbUtility.formatString(workNumber)+"%");
		}
		//员工状态查询
		if(!TeeUtility.isNullorEmpty(statusType)){
			if(!"全部".equals(statusType)){
				sql+=" and PM.STATUS_TYPE like ?";
				param.add("%"+TeeDbUtility.formatString(statusType)+"%");
			}else{
				sql +=" ";
			}
		}
		//员工类型查询
		if(!TeeUtility.isNullorEmpty(employeeType)){
			if(!"全部".equals(employeeType)){
			    sql+=" and PM.EMPLOYEE_TYPE like ?";
			    param.add("%"+TeeDbUtility.formatString(employeeType)+"%");
			}else{
				sql +=" ";
			}
		}
		//英文名查询
		if(!TeeUtility.isNullorEmpty(englishName)){
			 sql+=" and PM.ENGLISH_NAME like ?";
			 param.add("%"+TeeDbUtility.formatString(englishName)+"%");
		}
		//性别查询
		if(!TeeUtility.isNullorEmpty(gender)){
			if(!"全部".equals(gender)){
			    sql+=" and PM.GENDER like ?";
			    param.add("%"+TeeDbUtility.formatString(gender)+"%");
			}else{
				sql+=" ";
			}
		}
		//出生日期查询
		if(!TeeUtility.isNullorEmpty(birthdayDesc1)){
			sql+=" and PM.birthday >= ?";
			param.add(TeeDateUtil.parseCalendar(birthdayDesc1+" 00:00:00"));
		}
		if(!TeeUtility.isNullorEmpty(birthdayDesc2)){
			sql+=" and PM.birthday <= ?";
			param.add(TeeDateUtil.parseCalendar(birthdayDesc2+" 23:59:59"));
		}
		//默认年假天数查询
		if(!TeeUtility.isNullorEmpty(defaultAnnualLeaveDays)){
			sql+=" and PM.defaultAnnualLeaveDays = ?";
			param.add(defaultAnnualLeaveDays);
		}
		//职务查询
		if(!TeeUtility.isNullorEmpty(postState)){
			sql+=" and PM.postState like ?";
			param.add("%"+TeeDbUtility.formatString(postState)+"%");
		}
		//婚姻状况查询
		if(!TeeUtility.isNullorEmpty(marriage)){
			if(!"全部".equals(marriage)){
			    sql+=" and PM.marriage like ?";
			    param.add("%"+TeeDbUtility.formatString(marriage)+"%");
			}else{
				sql+=" ";
			}
		}
		//毕业院校查询
		if(!TeeUtility.isNullorEmpty(graduateSchool)){
			sql+=" and PM.graduateSchool like ?";
			param.add("%"+TeeDbUtility.formatString(graduateSchool)+"%");
		}
		//户口类型查询
		if(!TeeUtility.isNullorEmpty(household)){
			if(!"全部".equals(household)){
			    sql+=" and PM.household like ?";
			    param.add("%"+TeeDbUtility.formatString(household)+"%");
			}else{
				sql+=" ";
			}
		}
		//健康状况查询
		if(!TeeUtility.isNullorEmpty(health)){
			sql+=" and PM.health like ?";
			param.add("%"+TeeDbUtility.formatString(health)+"%");
	    }
		//户口所在地查询
		if(!TeeUtility.isNullorEmpty(householdPlace)){
			sql+=" and PM.householdPlace like ?";
			param.add("%"+TeeDbUtility.formatString(householdPlace)+"%");
		}
		//入职时间查询
	    if(!TeeUtility.isNullorEmpty(joinDateDesc1)){
			sql+=" and PM.joinDate >= ?";
			param.add(TeeDateUtil.parseCalendar(joinDateDesc1+" 00:00:00"));
		}
		if(!TeeUtility.isNullorEmpty(joinDateDesc2)){
			sql+=" and PM.joinDate <= ?";
			param.add(TeeDateUtil.parseCalendar(joinDateDesc2+" 23:59:59"));
		}
		//政治面貌查询
		if(!TeeUtility.isNullorEmpty(politics)){
			if(!"全部".equals(politics)){
			    sql+=" and PM.politics like ?";
			    param.add("%"+TeeDbUtility.formatString(politics)+"%");
			}else{
				sql+=" ";
			}
		}
		//入党（团）时间查询
		if(!TeeUtility.isNullorEmpty(joinPartyDateDesc1)){
			sql+=" and PM.joinPartyDate >= ?";
			param.add(TeeDateUtil.parseCalendar(joinPartyDateDesc1+" 00:00:00"));
		}
		if(!TeeUtility.isNullorEmpty(joinPartyDateDesc2)){
			sql+=" and PM.joinPartyDate <= ?";
			param.add(TeeDateUtil.parseCalendar(joinPartyDateDesc2+" 23:59:59"));
		}
		//专业查询
		if(!TeeUtility.isNullorEmpty(major)){
			sql+=" and PM.major like ?";
			param.add("%"+TeeDbUtility.formatString(major)+"%");
		}
		//毕业时间查询
		if(!TeeUtility.isNullorEmpty(graduateDateDesc1)){
			sql+=" and PM.graduateDate >= ?";
			param.add(TeeDateUtil.parseCalendar(graduateDateDesc1+" 00:00:00"));
		}
		if(!TeeUtility.isNullorEmpty(graduateDateDesc2)){
			sql+=" and PM.graduateDate <= ?";
			param.add(TeeDateUtil.parseCalendar(graduateDateDesc2+" 23:59:59"));
		}
		//学历查询
		if(!TeeUtility.isNullorEmpty(educationDegree)){
			if(!"全部".equals(educationDegree)){
		    	sql+=" and PM.educationDegree like ?";
		    	param.add("%"+TeeDbUtility.formatString(educationDegree)+"%");
			}else{
				sql+=" ";
			}
		}
		//学位查询
		if(!TeeUtility.isNullorEmpty(degree)){
			if(!"全部".equals(degree)){
			    sql+=" and PM.degree like ?";
			    param.add("%"+TeeDbUtility.formatString(degree)+"%");
			}else{
				sql+=" ";
			}
		}
		//手机号码查询
		if(!TeeUtility.isNullorEmpty(mobileNo)){
			 sql+=" and PM.mobileNo like ?";
			 param.add("%"+TeeDbUtility.formatString(mobileNo)+"%");
		}
		//电话号码查询
		if(!TeeUtility.isNullorEmpty(telNo)){
			sql+=" and PM.telNo like ?";
			param.add("%"+TeeDbUtility.formatString(telNo)+"%");
		}
		//电子邮件查询
		if(!TeeUtility.isNullorEmpty(email)){
			sql+=" and PM.email like ?";
			param.add("%"+TeeDbUtility.formatString(email)+"%");
		}
		//QQ号码查询
		if(!TeeUtility.isNullorEmpty(qqNo)){
			sql+=" and PM.qqNo like ?";
			param.add("%"+TeeDbUtility.formatString(qqNo)+"%");
		}
		//家庭地址查询
		if(!TeeUtility.isNullorEmpty(address)){
			sql+=" and PM.address like ?";
			param.add("%"+TeeDbUtility.formatString(address)+"%");
		}
		//其他联系地址查询
		if(!TeeUtility.isNullorEmpty(otherAddress)){
			sql+=" and PM.otherAddress like ?";
			param.add("%"+TeeDbUtility.formatString(otherAddress)+"%");
		}
		
		//获取自定义 字段查询
		Map customMap=requestDatas;
		for (Object obj : customMap.keySet()) {
			String str=TeeStringUtil.getString(obj);
			if(str.startsWith("EXTRA_")){
				int filedId=TeeStringUtil.getInteger(str.substring(6,str.length()), 0);
				if(filedId>0){
					TeePmCustomerField field=(TeePmCustomerField) simpleDaoSupport.get(TeePmCustomerField.class,filedId);
					
					if(("单行输入框").equals(field.getFiledType())||("多行输入框").equals(field.getFiledType())){
						String value=(String) customMap.get(str);
						if(!("").equals(value)){
							sql+=" and PM."+str+" like ? ";
							param.add("%"+value+"%");
						}
					}else if(("下拉列表").equals(field.getFiledType())){
						String value=(String) customMap.get(str);
						if(!("").equals(value)){
							sql+=" and PM."+str+" = ? ";
							param.add(value);
						}
					}	
				}	
			}
		}
		
	    List<TeeHumanDocModel> models = new ArrayList<TeeHumanDocModel>();
		
		//数据管理权限
		TeeManagerPostPersonDataPrivModel dataPrivModel = personManagerI.getManagerPostPersonIdsPriv(requestDatas, TeeModelIdConst.HR_PERSONAL_RECORDS_POST_PRIV, "0");
		
		TeePerson loginUser = (TeePerson) requestDatas.get(TeeConst.LOGIN_USER);
		
		if(!TeePersonService.checkIsAdminPriv(loginUser)){
			if(dataPrivModel.getPrivType().equals("0")){//只能看到自己创建的
				dataGridJson.setTotal(0L);
				dataGridJson.setRows(models);
				return dataGridJson;
			}else if(dataPrivModel.getPrivType().equals("ALL")){//所有
				
			}else{
				List<Integer> pIdList = dataPrivModel.getPersonIds();//获取权限
				
				if(pIdList.size() > 0){
					String personIdsSql = TeeDbUtility.IN("PM.OA_USER", pIdList);
					sql+=" and "+personIdsSql;
				}else{
					dataGridJson.setTotal(0L);
					dataGridJson.setRows(models);
					return dataGridJson;
				}
			}
		}
		
		long total = simpleDaoSupport.countSQLByList("select count(*) from PERSONNEL_MANAGEMENT PM where 1=1"+ sql, param);
		dataGridJson.setTotal(total);
		List<Map> resultList = simpleDaoSupport.executeNativeQuery(sql2+sql, param.toArray(), (dm.getPage() - 1) * dm.getRows(),  dm.getRows());
		List<Map> rows=new ArrayList<Map>();
		Map map=null;
		if(resultList!=null){
			List<TeePmCustomerField> list1=null;
			for (Map m: resultList) {
				map=new HashMap();
		    	list1=simpleDaoSupport.executeQuery(" from TeePmCustomerField where 1=1 ", null);
		    	if(list1!=null){
		    		for (TeePmCustomerField field : list1) {
						if(("单行输入框").equals(field.getFiledType())||("多行输入框").equals(field.getFiledType())){
							map.put("EXTRA_"+field.getSid(),m.get("EXTRA_"+field.getSid()));
						}else if(("下拉列表").equals(field.getFiledType())){
							if(("HR系统编码").equals(field.getCodeType())){
								 String value =field.getSysCode();
								 String name=TeeHrCodeManager.getChildSysCodeNameCodeNo(value,(String)(m.get("EXTRA_"+field.getSid())));
								 map.put("EXTRA_"+field.getSid(),name);
							}else if(("自定义选项").equals(field.getCodeType())){
								 String[] optionNames = field.getOptionName().split(",");
								 String[] optionValues = field.getOptionValue().split(",");
								 for (int i=0;i<optionValues.length;i++) {
									 if(optionValues[i].equals(m.get("EXTRA_"+field.getSid()))){
										 map.put("EXTRA_"+field.getSid(),optionNames[i]);
										 break;
									 }
								 }
							}
						}
					}
		    	}
		    	
		    	String sid=TeeStringUtil.getString(m.get("SID"));
		    	map.put("sid", sid);
		    	map.put("personName", m.get("PERSON_NAME"));//个人名称
				map.put("gender", m.get("GENDER"));//性别
				map.put("nativePlace", m.get("NATIVE_PLACE"));//籍贯
				map.put("ethnicity", m.get("ETHNICITY"));//民族
				//是否为OA用户
				map.put("isOaUser", m.get("ISOAUSER"));//是否是OA用户
				//是OA用户才能关联角色和用户
				if("true".equals(m.get("ISOAUSER"))){
					//String OaUser = (Integer) m.get("OA_USER")+"";
					String OaUser=TeeStringUtil.getString(m.get("OA_USER"));
					if(!"null".equals(OaUser)&&!TeeUtility.isNullorEmpty(OaUser)){
						TeePerson oaUser = (TeePerson) simpleDaoSupport.get(TeePerson.class, Integer.parseInt(OaUser));
						if(oaUser!=null){
							map.put("userId", oaUser.getUuid());
						    map.put("userName", oaUser.getUserName());
						}	
					}else{
						map.put("userName", null);
					}
					//所属角色
					//String role = (Integer) m.get("ROLE")+"";
					String role=TeeStringUtil.getString(m.get("ROLE"));
					if(!"null".equals(role)&&!TeeUtility.isNullorEmpty(role)){
						TeeUserRole userRole = (TeeUserRole) simpleDaoSupport.get(TeeUserRole.class, Integer.parseInt(role));
					    if(userRole!=null){
					    	map.put("roleId", userRole.getUuid());
						    map.put("roleName", userRole.getRoleName());
					    }
						
					}else{
						 map.put("roleName", null);
					 }
				}else{
					map.put("userName", null);
					map.put("roleName", null);
				}
			
				//所在部门
				//String dept = (Integer) m.get("DEPT")+"";
				String dept=TeeStringUtil.getString(m.get("DEPT"));
				if(!"null".equals(dept)&&!TeeUtility.isNullorEmpty(dept)){
					TeeDepartment department = (TeeDepartment) simpleDaoSupport.get(TeeDepartment.class, Integer.parseInt(dept));
				    if(department!=null){
				    	map.put("deptId", department.getUuid());
					    map.put("deptName", department.getDeptName());
				    }
					
				}else{
					 map.put("deptName", null);
				 }
			
				map.put("codeNumber", m.get("CODE_NUMBER"));//编号
				map.put("workNumber", m.get("WORK_NUMBER"));//工号
				map.put("idCard", m.get("IDCARD"));//身份证号
				map.put("englishName", m.get("ENGLISH_NAME"));//身份证号
				if(!TeeUtility.isNullorEmpty(m.get("BIRTHDAY"))){
					map.put("birthdayDesc", formatter.format((Date)m.get("BIRTHDAY")));//出生日期
				}else{
					map.put("birthdayDesc",null);
				}
				map.put("health", m.get("HEALTH"));//健康状况
				map.put("householdPlace", m.get("HOUSEHOLDPLACE"));//户口所在地
				if(!TeeUtility.isNullorEmpty(m.get("JOINPARTYDATE"))){
					map.put("joinPartyDateDesc", formatter.format((Date)m.get("JOINPARTYDATE")));//入党时间
				}else{
					map.put("joinPartyDateDesc", null);
				}
				if(!TeeUtility.isNullorEmpty(m.get("GRADUATEDATE"))){
					map.put("graduateDateDesc", formatter.format((Date)m.get("GRADUATEDATE")));//毕业时间
				}else{
					map.put("graduateDateDesc", null);
				}
				map.put("major", m.get("MAJOR"));//主修
				
				map.put("graduateSchool", m.get("GRADUATESCHOOL"));//毕业院校
				map.put("mobileNo", m.get("MOBILENO"));//手机号
				map.put("telNo", m.get("TELNO"));//电话
				map.put("email", m.get("EMAIL"));//电子邮件
				map.put("qqNo", m.get("QQNO"));//QQ号
				map.put("msn", m.get("MSN"));//MSN
				map.put("address", m.get("ADDRESS"));//住址
				map.put("otherAddress", m.get("OTHERADDRESS"));//其他联系地址
				map.put("postState", m.get("POSTSTATE"));//职务
				if(!TeeUtility.isNullorEmpty(m.get("JOINDATE"))){
					map.put("joinDateDesc", formatter.format((Date)m.get("JOINDATE")));//入职时间
				}else{
					map.put("joinDateDesc",null);
				}
				
				map.put("workYears", m.get("WORKYEARS"));//工龄
				map.put("totalYears", m.get("TOTALYEARS"));//总工龄
				map.put("computerLevel", m.get("COMPUTERLEVEL"));//计算机水平
				map.put("language1", m.get("LANGUAGE1"));//语种1
				map.put("language2", m.get("LANGUAGE2"));//语种2
				map.put("language3", m.get("LANGUAGE3"));//语种3
				map.put("skill", m.get("SKILL"));//特长
				map.put("postDesc", m.get("POSTDESC"));//职务情况
				map.put("shebaoDesc", m.get("SHEBAODESC"));//社保情况
				map.put("healthDesc", m.get("HEALTHDESC"));//体检情况
				map.put("remark", m.get("REMARK"));//备注
				map.put("defaultAnnualLeaveDays", m.get("DEFAULTANNUALLEAVEDAYS"));//默认年假天数
				//保险设置
				//String paraId = (Integer) m.get("INSUANCE_PARA_ID")+"";
				String paraId=TeeStringUtil.getString(m.get("INSUANCE_PARA_ID"));
				if(!"null".equals(paraId)&&!TeeUtility.isNullorEmpty(paraId)){
					TeeHrInsurancePara insurancePara = (TeeHrInsurancePara) simpleDaoSupport.get(TeeHrInsurancePara.class, Integer.parseInt(paraId));
				    if(insurancePara!=null){
				    	map.put("insuranceId", insurancePara.getSid());
					    map.put("insuranceName", insurancePara.getInsuranceName());
				    }
					
				}else{
					 map.put("insuranceName", null);
					 map.put("insuranceId", 0);
				 }
				map.put("salaryLevel", m.get("SALARYLEVEL"));//职工薪资等级
				
				//在职状态
			    String statusTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_STATUS_TYPE",(String) m.get("STATUS_TYPE"));
			    map.put("statusTypeDesc", statusTypeDesc);
			    //员工类型
			    String employeeTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_EMPLOYEE_TYPE",(String) m.get("EMPLOYEE_TYPE"));
			    map.put("employeeTypeDesc", employeeTypeDesc);
			    //婚姻类型
			    String marriageDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_MARRIAGE",(String) m.get("MARRIAGE"));
			    map.put("marriageDesc", marriageDesc);
			    //户口类型
			    String householdDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_HOUSEHOLD",(String) m.get("HOUSEHOLD"));
			    map.put("householdDesc", householdDesc);
			   //政治面貌
			    String politicsDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_POLITICS",(String) m.get("POLITICS"));
			    map.put("politicsDesc", politicsDesc);
			   //学历
			    String educationDegreeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_EDUCATIONDEGREE",(String) m.get("EDUCATIONDEGREE"));
			    map.put("educationDegreeDesc", educationDegreeDesc);
			   //学位
			    String degreeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_DEGREE",(String) m.get("DEGREE"));
			    map.put("degreeDesc", degreeDesc);
		    	
		    	rows.add(map);
			}
		}
		
		dataGridJson.setRows(rows);
		return dataGridJson;
		
	}
	
	
	public TeeHumanDocModel getModelById(int sid, String type){
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDao.get(sid);
		TeeHumanDocModel model = new TeeHumanDocModel();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(humanDoc.getBirthday()!=null){
			model.setBirthdayDesc(formatter.format(humanDoc.getBirthday().getTime()));
		}else{
			model.setBirthdayDesc("");
		}
		if(humanDoc.getGraduateDate()!=null){
			model.setGraduateDateDesc(formatter.format(humanDoc.getGraduateDate().getTime()));
		}else{
			model.setGraduateDateDesc("");
		}
		if(humanDoc.getJoinDate()!=null){
			model.setJoinDateDesc(formatter.format(humanDoc.getJoinDate().getTime()));
		}else{
			model.setJoinDateDesc("");
		}
		if(humanDoc.getJoinPartyDate()!=null){
			model.setJoinPartyDateDesc(formatter.format(humanDoc.getJoinPartyDate().getTime()));
		}else{
			model.setJoinPartyDateDesc("");
		}
		if(humanDoc.getOaUser()!=null){
			model.setUserId(humanDoc.getOaUser().getUuid());
			model.setUserName(humanDoc.getOaUser().getUserName());
		}else{
			model.setUserName("");
		}
		if(humanDoc.getDept()!=null){
			model.setDeptId(humanDoc.getDept().getUuid());
			model.setDeptIdName(humanDoc.getDept().getDeptName());
		}else{
			model.setDeptIdName("");
		}
		if(humanDoc.getRole()!=null){
			model.setRoleId(humanDoc.getRole().getUuid());
			model.setRoleName(humanDoc.getRole().getRoleName());
		}else{
			model.setRoleName("");
		}
		if(humanDoc.getHrInsurancePara()!=null){
			model.setInsuranceId(humanDoc.getHrInsurancePara().getSid());
			model.setInsuranceName(humanDoc.getHrInsurancePara().getInsuranceName());
		}else{
			model.setInsuranceName("");
		}
		if(humanDoc.getRole()!=null){
			model.setSalaryLevelModel(humanDoc.getRole().getSalaryLevelModel());
		}
		
		BeanUtils.copyProperties(humanDoc,model);
		String statusTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_STATUS_TYPE", humanDoc.getStatusType());
		String employeeTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_EMPLOYEE_TYPE", humanDoc.getEmployeeType());
		String marriageDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_MARRIAGE", humanDoc.getMarriage());
		String householdDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_HOUSEHOLD", humanDoc.getHousehold());
		String politicsDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_POLITICS", humanDoc.getPolitics());
		String educationDegreeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_EDUCATIONDEGREE", humanDoc.getEducationDegree());
		String degreeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_DEGREE", humanDoc.getDegree());
		model.setStatusTypeDesc(statusTypeDesc);
		model.setEmployeeTypeDesc(employeeTypeDesc);
		model.setMarriageDesc(marriageDesc);
		model.setHouseholdDesc(householdDesc);
		model.setPoliticsDesc(politicsDesc);
		model.setEducationDegreeDesc(educationDegreeDesc);
		model.setDegreeDesc(degreeDesc);
		
		List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
		List<TeeAttachment> attaches =this.getAttachmentDao().getAttaches(TeeAttachmentModelKeys.humanDoc, String.valueOf(sid));
		for (TeeAttachment attach : attaches) {
			TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, attachmentModel);
			attachmentModel.setUserId(attach.getUser().getUuid() + "");
			attachmentModel.setUserName(attach.getUser().getUserName());
			if(type.equals("1")){
				attachmentModel.setPriv(1+2);// 一共五个权限好像
			}else{
				attachmentModel.setPriv(1 + 2+4+8+16+32);// 一共五个权限好像
			}
											// 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
			attachmodels.add(attachmentModel);
		}
		model.setAttachMentModel(attachmodels);
		return model;
	}
	
	/**
	 * 根据档案id获取档案详情
	 * @param request
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeJson json = new TeeJson();
		List<Map> list = new ArrayList();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String type = TeeStringUtil.getString(request.getParameter("type"), "0");
		if(!("").equals(sid)){
			Map map=new HashMap();
			String sql = "select * from PERSONNEL_MANAGEMENT PM where PM.sid = ? ";
			Map data =simpleDaoSupport.executeNativeUnique(sql, new Object[]{sid});
			map.put("personName", data.get("PERSON_NAME"));//个人名称
			map.put("gender", data.get("GENDER"));//性别
			map.put("nativePlace", data.get("NATIVE_PLACE"));//籍贯
			map.put("ethnicity", data.get("ETHNICITY"));//民族
			//是否为OA用户
			map.put("isOaUser", data.get("ISOAUSER"));//是否是OA用户
			//是OA用户才能关联角色和用户
			if("true".equals(data.get("ISOAUSER"))){
				//String OaUser =  (Integer) data.get("OA_USER")+"";
				String OaUser=TeeStringUtil.getString(data.get("OA_USER"));
				if(!"null".equals(OaUser)&&!TeeUtility.isNullorEmpty(OaUser)){
					TeePerson oaUser = (TeePerson) simpleDaoSupport.get(TeePerson.class, Integer.parseInt(OaUser));
					map.put("userId", oaUser.getUuid());
				    map.put("userName", oaUser.getUserName());
				}else{
					map.put("userName", null);
				}
				//所属角色
				//String role = (Integer) data.get("ROLE")+"";
				String role=TeeStringUtil.getString(data.get("ROLE"));
				if(!"null".equals(role)&&!TeeUtility.isNullorEmpty(role)){
					TeeUserRole userRole = (TeeUserRole) simpleDaoSupport.get(TeeUserRole.class, Integer.parseInt(role));
				    map.put("roleId", userRole.getUuid());
				    map.put("roleName", userRole.getRoleName());
				}else{
					 map.put("roleName", null);
				 }
			}else{
				map.put("userName", null);
				map.put("roleName", null);
			}
		
			//所在部门
			//int dept = (Integer) data.get("DEPT");
			int dept=TeeStringUtil.getInteger(data.get("DEPT"), 0);
			if(!TeeUtility.isNullorEmpty(dept)){
				TeeDepartment department = (TeeDepartment) simpleDaoSupport.get(TeeDepartment.class, dept);
			    map.put("deptId", department.getUuid());
			    map.put("deptName", department.getDeptName());
			}else{
				 map.put("deptName", null);
			 }
		
			map.put("codeNumber", data.get("CODE_NUMBER"));//编号
			map.put("workNumber", data.get("WORK_NUMBER"));//工号
			map.put("idCard", data.get("IDCARD"));//身份证号
			map.put("englishName", data.get("ENGLISH_NAME"));//身份证号
			if(!TeeUtility.isNullorEmpty(data.get("BIRTHDAY"))){
				map.put("birthdayDesc", sdf.format((Date)data.get("BIRTHDAY")));//出生日期
			}else{
				map.put("birthdayDesc",null);
			}
			map.put("health", data.get("HEALTH"));//健康状况
			map.put("householdPlace", data.get("HOUSEHOLDPLACE"));//户口所在地
			if(!TeeUtility.isNullorEmpty(data.get("JOINPARTYDATE"))){
				map.put("joinPartyDateDesc", sdf.format((Date)data.get("JOINPARTYDATE")));//入党时间
			}else{
				map.put("joinPartyDateDesc", null);
			}
			if(!TeeUtility.isNullorEmpty(data.get("GRADUATEDATE"))){
				map.put("graduateDateDesc", sdf.format((Date)data.get("GRADUATEDATE")));//毕业时间
			}else{
				map.put("graduateDateDesc", null);
			}
			map.put("major", data.get("MAJOR"));//主修
			
			
			map.put("graduateSchool", data.get("GRADUATESCHOOL"));//毕业院校
			map.put("mobileNo", data.get("MOBILENO"));//手机号
			map.put("telNo", data.get("TELNO"));//电话
			map.put("email", data.get("EMAIL"));//电子邮件
			map.put("qqNo", data.get("QQNO"));//QQ号
			map.put("msn", data.get("MSN"));//MSN
			map.put("address", data.get("ADDRESS"));//住址
			map.put("otherAddress", data.get("OTHERADDRESS"));//其他联系地址
			map.put("postState", data.get("POSTSTATE"));//职务
			if(!TeeUtility.isNullorEmpty(data.get("JOINDATE"))){
				map.put("joinDateDesc", sdf.format((Date)data.get("JOINDATE")));//入职时间
			}else{
				map.put("joinDateDesc",null);
			}
			
			map.put("workYears", data.get("WORKYEARS"));//工龄
			map.put("totalYears", data.get("TOTALYEARS"));//总工龄
			map.put("computerLevel", data.get("COMPUTERLEVEL"));//计算机水平
			map.put("language1", data.get("LANGUAGE1"));//语种1
			map.put("language2", data.get("LANGUAGE2"));//语种2
			map.put("language3", data.get("LANGUAGE3"));//语种3
			map.put("skill", data.get("SKILL"));//特长
			map.put("postDesc", data.get("POSTDESC"));//职务情况
			map.put("shebaoDesc", data.get("SHEBAODESC"));//社保情况
			map.put("healthDesc", data.get("HEALTHDESC"));//体检情况
			map.put("remark", data.get("REMARK"));//备注
			map.put("defaultAnnualLeaveDays", data.get("DEFAULTANNUALLEAVEDAYS"));//默认年假天数
			//保险设置
			//String paraId = (Integer) data.get("INSUANCE_PARA_ID")+"";
			String paraId=TeeStringUtil.getString(data.get("INSUANCE_PARA_ID"));
			if(!"null".equals(paraId)&&!TeeUtility.isNullorEmpty(paraId)){
				TeeHrInsurancePara insurancePara = (TeeHrInsurancePara) simpleDaoSupport.get(TeeHrInsurancePara.class, Integer.parseInt(paraId));
			    map.put("insuranceId", insurancePara.getSid());
			    map.put("insuranceName", insurancePara.getInsuranceName());
			}else{
				 map.put("insuranceName", null);
				 map.put("insuranceId", 0);
			 }
			map.put("salaryLevel", data.get("SALARYLEVEL"));//职工薪资等级
			
			//在职状态
			map.put("statusType", data.get("STATUS_TYPE"));
		    //员工类型
		    map.put("employeeType", data.get("EMPLOYEE_TYPE"));
		    //婚姻类型
		    map.put("marriage", data.get("MARRIAGE"));
		    //户口类型
		    map.put("household", data.get("HOUSEHOLD"));
		   //政治面貌
		    map.put("politics", data.get("POLITICS"));
		   //学历
		    map.put("educationDegree", data.get("EDUCATIONDEGREE"));
		   //学位
		    map.put("degree", data.get("DEGREE"));
		    
			//获取自定义字段的值
	    	for(Object obj:data.keySet()){
	    		String name=(String)obj;
	    		if(name.startsWith("EXTRA_")){
	    			map.put(name,data.get(name));
	    		}
	    	}
	    	
	    	//获取附件的值		
	    	List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
			List<TeeAttachment> attaches =this.getAttachmentDao().getAttaches(TeeAttachmentModelKeys.humanDoc, String.valueOf(sid));
			for (TeeAttachment attach : attaches) {
				TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
				BeanUtils.copyProperties(attach, attachmentModel);
				attachmentModel.setUserId(attach.getUser().getUuid() + "");
				attachmentModel.setUserName(attach.getUser().getUserName());
				if(type.equals("1")){
					attachmentModel.setPriv(1+2);// 一共五个权限好像
				}else{
					attachmentModel.setPriv(1 + 2+4+8+16+32);// 一共五个权限好像
				}
												// 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
				attachmodels.add(attachmentModel);
			}
			map.put("attachmodels", attachmodels);
			
		    json.setRtData(map);
		    json.setRtState(true);
			json.setRtMsg("查询成功！");
		}
		return json;
	}
	
	/**
	 * 获取个人档案详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoById(HttpServletRequest request) {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		TeeJson json = new TeeJson();
		List<Map> list = new ArrayList();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String type = TeeStringUtil.getString(request.getParameter("type"), "0");
		if(!("").equals(sid)){
			Map map=new HashMap();
			String sql = "select * from PERSONNEL_MANAGEMENT PM where PM.sid = ? ";
			Map data =simpleDaoSupport.executeNativeUnique(sql, new Object[]{sid});
			map.put("personName", data.get("PERSON_NAME"));//个人名称
			map.put("gender", data.get("GENDER"));//性别
			map.put("nativePlace", data.get("NATIVE_PLACE"));//籍贯
			map.put("ethnicity", data.get("ETHNICITY"));//民族
			//是否为OA用户
			map.put("isOaUser", data.get("ISOAUSER"));//是否是OA用户
			//是OA用户才能关联角色和用户
			if("true".equals(data.get("ISOAUSER"))){
				//String OaUser =  (Integer) data.get("OA_USER")+"";
				String OaUser=TeeStringUtil.getString(data.get("OA_USER"));
				if(!"null".equals(OaUser)&&!TeeUtility.isNullorEmpty(OaUser)){
					TeePerson oaUser = (TeePerson) simpleDaoSupport.get(TeePerson.class, Integer.parseInt(OaUser));
					map.put("userId", oaUser.getUuid());
				    map.put("userName", oaUser.getUserName());
				}else{
					map.put("userName", null);
				}
				//所属角色
				//String role = (Integer) data.get("ROLE")+"";
				String role=TeeStringUtil.getString(data.get("ROLE"));
				if(!"null".equals(role)&&!TeeUtility.isNullorEmpty(role)){
					TeeUserRole userRole = (TeeUserRole) simpleDaoSupport.get(TeeUserRole.class, Integer.parseInt(role));
				    map.put("roleId", userRole.getUuid());
				    map.put("roleName", userRole.getRoleName());
				}else{
					 map.put("roleName", null);
				 }
			}else{
				map.put("userName", null);
				map.put("roleName", null);
			}
		
			//所在部门
			//int dept = (Integer) data.get("DEPT");
			int dept=TeeStringUtil.getInteger(data.get("DEPT"), 0);
			if(!TeeUtility.isNullorEmpty(dept)){
				TeeDepartment department = (TeeDepartment) simpleDaoSupport.get(TeeDepartment.class, dept);
			    map.put("deptId", department.getUuid());
			    map.put("deptName", department.getDeptName());
			}else{
				 map.put("deptName", null);
			 }
		
			map.put("codeNumber", data.get("CODE_NUMBER"));//编号
			map.put("workNumber", data.get("WORK_NUMBER"));//工号
			map.put("idCard", data.get("IDCARD"));//身份证号
			map.put("englishName", data.get("ENGLISH_NAME"));//身份证号
			if(!TeeUtility.isNullorEmpty(data.get("BIRTHDAY"))){
				map.put("birthdayDesc", formatter.format((Date)data.get("BIRTHDAY")));//出生日期
			}else{
				map.put("birthdayDesc",null);
			}
			map.put("health", data.get("HEALTH"));//健康状况
			map.put("householdPlace", data.get("HOUSEHOLDPLACE"));//户口所在地
			if(!TeeUtility.isNullorEmpty(data.get("JOINPARTYDATE"))){
				map.put("joinPartyDateDesc", formatter.format((Date)data.get("JOINPARTYDATE")));//入党时间
			}else{
				map.put("joinPartyDateDesc", null);
			}
			if(!TeeUtility.isNullorEmpty(data.get("GRADUATEDATE"))){
				map.put("graduateDateDesc", formatter.format((Date)data.get("GRADUATEDATE")));//毕业时间
			}else{
				map.put("graduateDateDesc", null);
			}
			map.put("major", data.get("MAJOR"));//主修
			
			
			map.put("graduateSchool", data.get("GRADUATESCHOOL"));//毕业院校
			map.put("mobileNo", data.get("MOBILENO"));//手机号
			map.put("telNo", data.get("TELNO"));//电话
			map.put("email", data.get("EMAIL"));//电子邮件
			map.put("qqNo", data.get("QQNO"));//QQ号
			map.put("msn", data.get("MSN"));//MSN
			map.put("address", data.get("ADDRESS"));//住址
			map.put("otherAddress", data.get("OTHERADDRESS"));//其他联系地址
			map.put("postState", data.get("POSTSTATE"));//职务
			if(!TeeUtility.isNullorEmpty(data.get("JOINDATE"))){
				map.put("joinDateDesc", formatter.format((Date)data.get("JOINDATE")));//入职时间
			}else{
				map.put("joinDateDesc",null);
			}
			
			map.put("workYears", data.get("WORKYEARS"));//工龄
			map.put("totalYears", data.get("TOTALYEARS"));//总工龄
			map.put("computerLevel", data.get("COMPUTERLEVEL"));//计算机水平
			map.put("language1", data.get("LANGUAGE1"));//语种1
			map.put("language2", data.get("LANGUAGE2"));//语种2
			map.put("language3", data.get("LANGUAGE3"));//语种3
			map.put("skill", data.get("SKILL"));//特长
			map.put("postDesc", data.get("POSTDESC"));//职务情况
			map.put("shebaoDesc", data.get("SHEBAODESC"));//社保情况
			map.put("healthDesc", data.get("HEALTHDESC"));//体检情况
			map.put("remark", data.get("REMARK"));//备注
			map.put("defaultAnnualLeaveDays", data.get("DEFAULTANNUALLEAVEDAYS"));//默认年假天数
			//保险设置
			//String paraId = (Integer) data.get("INSUANCE_PARA_ID")+"";
			String paraId=TeeStringUtil.getString(data.get("INSUANCE_PARA_ID"));
			if(!"null".equals(paraId)&&!TeeUtility.isNullorEmpty(paraId)){
				TeeHrInsurancePara insurancePara = (TeeHrInsurancePara) simpleDaoSupport.get(TeeHrInsurancePara.class, Integer.parseInt(paraId));
			    if(insurancePara!=null){
			    	map.put("insuranceId", insurancePara.getSid());
			    	map.put("insuranceName", insurancePara.getInsuranceName());
			    }
			}else{
				 map.put("insuranceName", null);
				 map.put("insuranceId", 0);
			 }
			map.put("salaryLevel", data.get("SALARYLEVEL"));//职工薪资等级
			
			//在职状态
		    String statusType = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_STATUS_TYPE",(String) data.get("STATUS_TYPE"));
		    map.put("statusType", statusType);
		    //员工类型
		    String employeeType = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_EMPLOYEE_TYPE",(String) data.get("EMPLOYEE_TYPE"));
		    map.put("employeeType", employeeType);
		    //婚姻类型
		    String marriage = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_MARRIAGE",(String) data.get("MARRIAGE"));
		    map.put("marriage", marriage);
		    //户口类型
		    String household = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_HOUSEHOLD",(String) data.get("HOUSEHOLD"));
		    map.put("household", household);
		   //政治面貌
		    String politics = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_POLITICS",(String) data.get("POLITICS"));
		    map.put("politics", politics);
		   //学历
		    String educationDegree = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_EDUCATIONDEGREE",(String) data.get("EDUCATIONDEGREE"));
		    map.put("educationDegree", educationDegree);
		   //学位
		    String degree = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_DEGREE",(String) data.get("DEGREE"));
		    map.put("degree", degree);
		    
		    // 获取自定义字段的值
		    List<TeePmCustomerField> extendList=simpleDaoSupport.executeQuery(" from TeePmCustomerField where 1=1 ", null);
			 if(extendList!=null){
				 for (TeePmCustomerField field : extendList) {
					 if(("单行输入框").equals(field.getFiledType())||("多行输入框").equals(field.getFiledType())){
						 map.put("EXTRA_"+field.getSid(),data.get("EXTRA_"+field.getSid()));
					 }else if(("下拉列表").equals(field.getFiledType())){
						 if(("HR系统编码").equals(field.getCodeType())){
							 String value =field.getSysCode();
							 String name=TeeHrCodeManager.getChildSysCodeNameCodeNo(value,(String)(data.get("EXTRA_"+field.getSid())));
							 map.put("EXTRA_"+field.getSid(),name);
						 }else if(("自定义选项").equals(field.getCodeType())){
							 String[] optionNames = field.getOptionName().split(",");
							 String[] optionValues = field.getOptionValue().split(",");
							 for (int i=0;i<optionValues.length;i++) {
								 if(optionValues[i].equals(data.get("EXTRA_"+field.getSid()))){
									 map.put("EXTRA_"+field.getSid(),optionNames[i]);
									 break;
								 }else{
									 map.put("EXTRA_"+field.getSid(),""); 
								 }
							 }
						 }
					 }
					 
				 }
			 }
	    	
	    	//获取附件的值		
	    	List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
			List<TeeAttachment> attaches =this.getAttachmentDao().getAttaches(TeeAttachmentModelKeys.humanDoc, String.valueOf(sid));
			for (TeeAttachment attach : attaches) {
				TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
				BeanUtils.copyProperties(attach, attachmentModel);
				attachmentModel.setUserId(attach.getUser().getUuid() + "");
				attachmentModel.setUserName(attach.getUser().getUserName());
				if(type.equals("1")){
					attachmentModel.setPriv(1+2);// 一共五个权限好像
				}else{
					attachmentModel.setPriv(1 + 2+4+8+16+32);// 一共五个权限好像
				}
												// 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
				attachmodels.add(attachmentModel);
			}
			map.put("attachmodels", attachmodels);
			
		    json.setRtData(map);
		    json.setRtState(true);
			json.setRtMsg("查询成功！");
		}
		return json;
	}
	
	/**
	 * 
	 * @param request
	 * @param humanDocModel
	 * @throws ParseException
	 */
	public void updateHumanDoc(HttpServletRequest request,TeeHumanDocModel humanDocModel) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanDoc humanDoc = new TeeHumanDoc();
		String birthdayDesc=humanDocModel.getBirthdayDesc();
		String graduateDateDesc = humanDocModel.getGraduateDateDesc();
		String joinDateDesc = humanDocModel.getJoinDateDesc();
		String joinPartyDateDesc = humanDocModel.getJoinPartyDateDesc();
		if(!TeeUtility.isNullorEmpty(birthdayDesc)){
			Calendar cl = Calendar.getInstance();
			cl.setTime(formatter.parse(birthdayDesc));
			humanDoc.setBirthday(cl);
		}
		if(!TeeUtility.isNullorEmpty(graduateDateDesc)){
			Calendar cl1 = Calendar.getInstance();
			cl1.setTime(formatter.parse(graduateDateDesc));
			humanDoc.setGraduateDate(cl1);
		}
		if(!TeeUtility.isNullorEmpty(joinDateDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(joinDateDesc));
			humanDoc.setJoinDate(cl2);
		}
		if(!TeeUtility.isNullorEmpty(joinPartyDateDesc)){
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(formatter.parse(joinPartyDateDesc));
			humanDoc.setJoinPartyDate(cl3);
		}
		BeanUtils.copyProperties(humanDocModel,humanDoc);
		TeePerson person = new TeePerson();
		if(humanDocModel.getUserId()!=0){
			person = (TeePerson)personDao.get(humanDocModel.getUserId());
			humanDoc.setOaUser(person);
		}
		TeeDepartment dept = new TeeDepartment();
		if(humanDocModel.getDeptId()!=0){
			dept = (TeeDepartment)deptDao.get(humanDocModel.getDeptId());
			humanDoc.setDept(dept);
		}
		TeeUserRole role = new TeeUserRole();
		if(humanDocModel.getRoleId()!=0){
			role = (TeeUserRole)roleDao.get(humanDocModel.getRoleId());
			humanDoc.setRole(role);
		}
		TeeHrInsurancePara hrInsurancePara = new TeeHrInsurancePara();
		if(humanDocModel.getInsuranceId()!=0){
			hrInsurancePara.setSid(humanDocModel.getInsuranceId());
			humanDoc.setHrInsurancePara(hrInsurancePara);
		}
		
		//是oa用户，如果未选择，则自动创建用户信息
		if("true".equals(humanDocModel.getIsOaUser()) && humanDocModel.getUserId()!=0){
			TeePerson targetPerson = personService.selectByUuid(humanDocModel.getUserId());
			targetPerson.setUserName(humanDocModel.getPersonName());
			if("男".equals(humanDocModel.getGender())){
				targetPerson.setSex("0");
	        }else{
	        	targetPerson.setSex("1");
	        }
			if(targetPerson.getUserRole().getUuid()!=humanDocModel.getRoleId()){
				throw new TeeOperationException("所关联的OA用户角色为["+targetPerson.getUserRole().getRoleName()+"],与您选择的角色不符合!");
			}
			TeeDepartment department = new TeeDepartment();
			department.setUuid(humanDocModel.getDeptId());
			targetPerson.setDept(department);
			targetPerson.setBirthday(TeeDateUtil.parseDate(humanDocModel.getBirthdayDesc()));
			targetPerson.setMobilNo(humanDocModel.getMobileNo());
			targetPerson.setEmail(humanDocModel.getEmail());
		}
		
		humanDao.update(humanDoc);
		
		//为自定义字段赋值
				int sid = humanDoc.getSid();
				//处理自定义字段
				Map map = TeeServletUtility.getParamMap(request);
				Map<String,String> mapExtra = new HashMap<String,String>();
				Set<String> set = map.keySet();
				for (String key : set) {
					if(key.startsWith("EXTRA_")){
						mapExtra.put(key,(String) map.get(key));
					}
				}
				List  list=new ArrayList();
				if(mapExtra.keySet().size()>0){
					String sql= "update PERSONNEL_MANAGEMENT set";
					int count = 0;
					for (String key2 : mapExtra.keySet()) {
						count++;
						if(count==mapExtra.keySet().size()){
							sql += " "+ key2+" = ?";
						}else{
							sql +=" "+ key2+" = ? ,";
						}
						list.add(mapExtra.get(key2));
					}
					sql += " "+"where sid = "+sid;
					simpleDaoSupport.executeNativeUpdate(sql,list.toArray());
				}
	}
	
	public void delHumanDoc(int sid){
		//删除与人事档案有关的所有数据
		simpleDaoSupport.executeUpdate("delete from TeeHumanCert where humanDoc.sid=?", new Object[]{sid});
		simpleDaoSupport.executeUpdate("delete from TeeHumanContract where humanDoc.sid=?", new Object[]{sid});
		simpleDaoSupport.executeUpdate("delete from TeeHumanEducation where humanDoc.sid=?", new Object[]{sid});
		simpleDaoSupport.executeUpdate("delete from TeeHumanExperience where humanDoc.sid=?", new Object[]{sid});
		simpleDaoSupport.executeUpdate("delete from TeeHumanLeave where humanDoc.sid=?", new Object[]{sid});
		simpleDaoSupport.executeUpdate("delete from TeeHumanRehab where humanDoc.sid=?", new Object[]{sid});
		simpleDaoSupport.executeUpdate("delete from TeeHumanSanction where humanDoc.sid=?", new Object[]{sid});
		simpleDaoSupport.executeUpdate("delete from TeeHumanShift where humanDoc.sid=?", new Object[]{sid});
		simpleDaoSupport.executeUpdate("delete from TeeHumanSkill where humanDoc.sid=?", new Object[]{sid});
		simpleDaoSupport.executeUpdate("delete from TeeHumanSocial where humanDoc.sid=?", new Object[]{sid});
		simpleDaoSupport.executeUpdate("delete from TeeHumanTrain where humanDoc.sid=?", new Object[]{sid});
		humanDao.delete(sid);
	}

	
	public TeeJson findExtraInfoByIdCard(HttpServletRequest request) {
		String idCard = TeeStringUtil.getString(request.getParameter("idCard"));
		if(idCard.length()==18){
			String provinceCode = idCard.substring(0,3);
			simpleDaoSupport.find("", null);
			String province = "";
		}
		return null;
	}
	

	public TeeJson importPersonInfo(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		InputStream ins=null;
		TeeJson json = new TeeJson();
		Workbook wb = null;  
		try{
			MultipartFile  file = multipartRequest.getFile("excelFile");
			ins = file.getInputStream();
			wb = Workbook.getWorkbook(ins);
			if(wb==null){
				json.setRtState(false);
				json.setRtMsg("上传文件错误！");
			}
			Sheet[] sheet = wb.getSheets();  
	        if (sheet != null && sheet.length > 0) {  
	            // 对每个工作表进行循环  
	            for (int i = 0; i < sheet.length; i++) {  
	                // 得到当前工作表的行数  
	                int rowNum = sheet[i].getRows(); 
                    if(sheet[i].getColumns()!=43){
                    	json.setRtState(false);
                    	json.setRtMsg("你导入的文件不对，请下载模板，按模板填写内容");
                    	break;
                    }
	                for (int j = 1; j < rowNum; j++) {  
	                	TeeHumanDocModel model = new TeeHumanDocModel();
	                    // 得到当前行的所有单元格  
	                    Cell[] cells = sheet[i].getRow(j); 
	                    if (cells != null && cells.length > 0) {  
	                        // 对每个单元格进行循环  
	                        for (int k = 0; k < cells.length; k++) {  
	                            String cellValue = cells[k].getContents();  
	                            switch(k){
	                            	case 0:
	                            	model.setPersonName(cellValue);
	                            	break;
	                            	case 1:
	                            	model.setGender(cellValue);
	                            	break;
	                            	case 2:
		                            model.setNativePlace(cellValue);
		                            break;
	                            	case 3:
		                            model.setEthnicity(cellValue);
		                            break;
	                            	case 4:
		                            model.setDeptIdName(cellValue);
		                            break;
	                            	case 5:
		                            model.setStatusType(cellValue);
		                            break;
	                            	case 6:
		                            model.setCodeNumber(cellValue);
		                            break;
	                            	case 7:
		                            model.setWorkNumber(cellValue);
		                            break;
	                            	case 8:
		                            model.setEnglishName(cellValue);
		                            break;
	                            	case 9:
		                            model.setIdCard(cellValue);
		                            break;
	                            	case 10:
		                            model.setBirthdayDesc(cellValue);
			                        break;
	                             	case 11:
		                            model.setMarriage(cellValue);
	                            	break;
	                            	case 12:
		                            model.setHealth(cellValue);
		                            break;
	                            	case 13:
		                            model.setPolitics(cellValue);
		                            break;
	                            	case 14:
		                            model.setJoinPartyDateDesc(cellValue);
		                            break;
	                            	case 15:
		                            model.setHousehold(cellValue);
		                            break;
	                            	case 16:
		                            model.setHouseholdPlace(cellValue);
		                            break;
	                            	case 17:
		                            model.setEmployeeType(cellValue);
		                            break;
	                            	case 18:
		                            model.setPostState(cellValue);
		                            break;
	                            	case 19:
		                            model.setJoinDateDesc(cellValue);
		                            break;
	                            	case 20:
		                            model.setWorkYears(Integer.parseInt(cellValue));
			                        break;
	                             	case 21:
			                        model.setTotalYears(Integer.parseInt(cellValue)); 	
	                            	break;
	                            	case 22:
		                            model.setMobileNo(cellValue);	
		                            break;
	                            	case 23:
		                            model.setTelNo(cellValue);	
		                            break;
	                            	case 24:
		                            model.setEmail(cellValue);
		                            break;
	                            	case 25:
		                            model.setQqNo(cellValue);
		                            break;
	                            	case 26:
		                            model.setMsn(cellValue);	
		                            break;
	                            	case 27:
		                            model.setAddress(cellValue);
		                            break;
	                            	case 28:
		                            model.setOtherAddress(cellValue);
		                            break;
	                            	case 29:
		                            model.setEducationDegree(cellValue);
		                            break;
	                            	case 30:
		                            model.setDegree(cellValue);	
			                        break;
	                             	case 31:
				                    model.setGraduateSchool(cellValue);      	
	                            	break;
	                            	case 32:
		                            model.setGraduateDateDesc(cellValue);	
		                            break;
	                            	case 33:
		                            model.setMajor(cellValue);
		                            break;
	                            	case 34:
		                            model.setComputerLevel(cellValue);
		                            break;
	                            	case 35:
		                            model.setLanguage1(cellValue);	
		                            break;
	                            	case 36:
		                            model.setLanguage2(cellValue);
		                            break;
	                            	case 37:
		                            model.setLanguage3(cellValue);	
		                            break;
	                            	case 38:
		                            model.setSkill(cellValue);	
		                            break;
	                            	case 39:
		                            model.setPostDesc(cellValue);
		                            break;
	                            	case 40:
		                            model.setShebaoDesc(cellValue);
			                        break;
	                             	case 41:
					                model.setHealthDesc(cellValue);      	
	                            	break;
	                            	case 42:
		                            model.setRemark(cellValue);
		                            break;
	                            }
	                        }  
	                    }  
	                    //保存对象 
	                    addHumanDoc(request,model);
	                } 
	                json.setRtState(true);
	                json.setRtMsg("导入成功");
	            }
	        }
	        wb.close();  
		}catch(Exception ex){
			json.setRtMsg("文件格式不对，请按模板进行内容填写");
			json.setRtState(false);
		}
		return json;
	}


	public List<TeeHumanDocModel> getDocListByConditon(Map requestDatas) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String personName = (String)requestDatas.get("personName");
		String gender = (String)requestDatas.get("gender");
		String deptId = (String)requestDatas.get("deptId");
		String statusType = (String)requestDatas.get("statusType");
		String hql = "from TeeHumanDoc human where 1=1 ";
		List param = new ArrayList();
		if(!TeeUtility.isNullorEmpty(personName)){
			hql+=" and human.personName like ?";
			param.add("%"+personName+"%");
		}
		if(!TeeUtility.isNullorEmpty(deptId)){
			hql+=" and human.dept.uuid='"+deptId+"'";
		}
		if(!TeeUtility.isNullorEmpty(gender) && !"全部".equals(gender)){
			hql+=" and human.gender='"+gender+"'";
		}
		if(!TeeUtility.isNullorEmpty(statusType) && !"全部".equals(statusType)){
			hql+=" and human.statusType='"+statusType+"'";
		}
		List<TeeHumanDoc> humanDocs = humanDao.executeQueryByList(hql, param);
		List<TeeHumanDocModel> docList = new ArrayList<TeeHumanDocModel>();
		for(TeeHumanDoc doc:humanDocs){
			TeeHumanDocModel m = new TeeHumanDocModel();
			if(doc.getBirthday()!=null){
				m.setBirthdayDesc(formatter.format(doc.getBirthday().getTime()));
			}
			if(doc.getGraduateDate()!=null){
				m.setGraduateDateDesc(formatter.format(doc.getGraduateDate().getTime()));
			}
			if(doc.getJoinDate()!=null){
				m.setJoinDateDesc(formatter.format(doc.getJoinDate().getTime()));
			}
			if(doc.getJoinPartyDate()!=null){
				m.setJoinPartyDateDesc(formatter.format(doc.getJoinPartyDate().getTime()));
			}
			BeanUtils.copyProperties(doc, m);	
			if(doc.getOaUser()!=null){
				m.setUserId(doc.getOaUser().getUuid());
				m.setUserName(doc.getOaUser().getUserName());
			}
			if(doc.getDept()!=null){
				m.setDeptId(doc.getDept().getUuid());
				m.setDeptIdName(doc.getDept().getDeptName());
			}
			String statusTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_STATUS_TYPE", doc.getStatusType());
			String employeeTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_EMPLOYEE_TYPE", doc.getEmployeeType());
			String marriageDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_MARRIAGE", doc.getMarriage());
			String householdDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_HOUSEHOLD", doc.getHousehold());
			String politicsDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_POLITICS", doc.getPolitics());
			String educationDegreeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_EDUCATIONDEGREE", doc.getEducationDegree());
			String degreeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_DEGREE", doc.getDegree());
			
			m.setStatusTypeDesc(statusTypeDesc);
			m.setEmployeeTypeDesc(employeeTypeDesc);
			m.setMarriageDesc(marriageDesc);
			m.setHouseholdDesc(householdDesc);
			m.setPoliticsDesc(politicsDesc);
			m.setEducationDegreeDesc(educationDegreeDesc);
			m.setDegreeDesc(degreeDesc);
			
			docList.add(m);
		}
		return docList;
	}
	
	/**
	 * 导入人事档案 
	 * @param request
	 * @return
	 */
	public TeeJson importHumanDocInfo(HttpServletRequest request,List<TeePmCustomerFieldModel> listPmCustomer) {
		StringBuffer sb = new StringBuffer("[");
		String infoStr= "";
		
		int importCount=0;//成功导入的记录数
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		InputStream ins=null;
		TeeJson json = new TeeJson();
		Workbook wb = null;  
		try{
			MultipartFile  file = multipartRequest.getFile("excelFile");
			ins = file.getInputStream();
			wb = Workbook.getWorkbook(ins);
			if(wb==null){
				json.setRtState(false);
				json.setRtMsg("上传文件错误！");
			}
			Sheet[] sheet = wb.getSheets();  
	        if (sheet != null && sheet.length > 0) {  
	            // 对每个工作表进行循环  
	            for (int i = 0; i < sheet.length; i++) {  
	                // 得到当前工作表的行数  
	                int rowNum = sheet[i].getRows(); 
	                int rowNum2=listPmCustomer.size()+35;
                    if(sheet[i].getColumns()!=rowNum2){
                    	json.setRtState(false);
                    	json.setRtMsg("你导入的文件不正确，请下载模板，按模板填写内容");
                    	break;
                    }
	                for (int j = 1; j < rowNum; j++) {
	                	int m=34;
	                	int n=0;
	                	TeeHumanDocModel model = new TeeHumanDocModel();
	                	Map<String,String> map=new HashMap<String,String>();
	                    // 得到当前行的所有单元格  
	                    Cell[] cells = sheet[i].getRow(j); 
	                    if (cells != null && cells.length > 0) {  
	                        // 对每个单元格进行循环  
	                        for (int k = 0; k < cells.length; k++) {  
	                            String cellValue = cells[k].getContents();  
	                            switch(k){
	                            	case 0:
	                            	model.setPersonName(cellValue);
	                            	break;
	                            	case 1:
	                            	model.setUserName(cellValue);
	                            	break;
	                            	case 2:
		                            model.setDeptIdName(cellValue);
		                            break;
	                            	case 3:
		                            model.setRoleName(cellValue);
		                            break;
	                            	case 4:
		                            model.setIdCard(cellValue);
		                            break;
	                            	case 5:
		                            model.setCodeNumber(cellValue);
		                            break;
	                            	case 6:
		                            model.setNativePlace(cellValue);
		                            break;
	                            	case 7:
		                            model.setWorkNumber(cellValue);
		                            break;
	                            	case 8:
		                            model.setStatusType(cellValue);
		                            break;
	                            	case 9:
		                            model.setEmployeeType(cellValue);
		                            break;
	                            	case 10:
		                            model.setEnglishName(cellValue);
			                        break;
	                             	case 11:
		                            model.setGender(cellValue);
	                            	break;
	                            	case 12:
		                            model.setBirthdayDesc(cellValue);
		                            break;
	                            	case 13:
		                            model.setEthnicity(cellValue);
		                            break;
	                            	case 14:
			                        model.setPostState(cellValue);
			                        break;
	                            	case 15:
		                            model.setMarriage(cellValue);
		                            break;
	                            	case 16:
		                            model.setGraduateSchool(cellValue);
		                            break;
	                            	case 17:
		                            model.setHousehold(cellValue);
		                            break;
	                            	case 18:
		                            model.setHealth(cellValue);
		                            break;
	                            	case 19:
		                            model.setHouseholdPlace(cellValue);
		                            break;
	                            	case 20:
		                            model.setJoinDateDesc(cellValue);
		                            break;
	                            	case 21:
		                            model.setPolitics(cellValue);
			                        break;
	                             	case 22:
			                        model.setJoinPartyDateDesc(cellValue); 	
	                            	break;
	                            	case 23:
		                            model.setMajor(cellValue);	
		                            break;
	                            	case 24:
		                            model.setGraduateDateDesc(cellValue);	
		                            break;
	                            	case 25:
		                            model.setEducationDegree(cellValue);
		                            break;
	                            	case 26:
		                            model.setDegree(cellValue);
		                            break;
	                            	case 27:
		                            model.setMobileNo(cellValue);	
		                            break;
	                            	case 28:
		                            model.setTelNo(cellValue);
		                            break;
	                            	case 29:
		                            model.setEmail(cellValue);
		                            break;
	                            	case 30:
		                            model.setQqNo(cellValue);
		                            break;
	                            	case 31:
		                            model.setMsn(cellValue);	
			                        break;
	                             	case 32:
				                    model.setAddress(cellValue);      	
	                            	break;
	                            	case 33:
		                            model.setOtherAddress(cellValue);	
		                            break;
	                            	case 34:
			                        model.setInsuranceName(cellValue);	
			                        break;
	                            }
	                            if(k>34){
	                            		m++;
	                            		if(m==k){
	                            			TeePmCustomerFieldModel model2 = listPmCustomer.get(n);
	                            			int sid = model2.getSid();
	                            			map.put("EXTRA_"+sid, cellValue);
	                            			n++;
	                            	}		
	                            }
	                        } 
	                        model.setMap(map);
	                        model.setList(listPmCustomer);
	                        
	                    } 
	                    String color = "red";//显示导入信息的返回信息的 颜色
	                    //封装角色id
	                    TeeUserRole role=roleDao.getUserRoleByRoleName(model.getRoleName());
	                    if(role==null){
	                    	infoStr = "导入失败,角色不存在！";
							getPersonInfoStr(sb, model, infoStr, color);
					        continue;
	                    	
	                    }else{
	                    	model.setRoleId(role.getUuid());	
	                    }
	                    
	                    
	                    //封装部门id               
	                    String name1=model.getDeptIdName();;           
	                    TeeDepartment dept=deptDao.getDeptByFullName(name1);
                        if(dept==null){
                        	infoStr = "导入失败,部门不存在！";
							getPersonInfoStr(sb, model, infoStr, color);
					        continue;
	                    }else{
	                    	model.setDeptId(dept.getUuid());	                    	
	                    }
	                    
                        //判断在职状态
                        if(model.getStatusType()!=null&&!("").equals(model.getStatusType())){
                        	if(!("在职").equals(model.getStatusType())&&!("离职").equals(model.getStatusType())){
                        		infoStr = "导入失败,员工状态填写错误！";
    							getPersonInfoStr(sb, model, infoStr, color);
    					        continue; 	
                        	}	
                        }else{
                        	infoStr = "导入失败,员工状态不能为空！";
							getPersonInfoStr(sb, model, infoStr, color);
					        continue;  	
                        }
                        
                        
                        
	                    if(model.getUserName()!=""){
	                    	//判断是否是OA用户
		                    TeePerson p=(TeePerson) personDao.getPersonByUserId(model.getUserName());	   
		                    if(p!=null&&p.getUuid()!=0){
		                    	 TeeHumanDoc td=humanDao.getHumanDocInfo(p);
		                        if(!(model.getRoleName()).equals(p.getUserRole().getRoleName())){
		                        	infoStr = "导入失败,所关联的OA用户角色与您选择的角色不符合！";
									getPersonInfoStr(sb, model, infoStr, color);
							        continue;
		                        	
		                        }else if(td!=null&&td.getSid()>0){//判断该用户在人事档案中是否已经存在
		                        	model.setSid(td.getSid());
		                        	model.setIsOaUser("true");
		                        	//覆盖
		                        	updateHDoc(model);
		                        	importCount++;
			 	                    infoStr="更新成功";
			 	                    color="black";
			 	                    getPersonInfoStr(sb, model, infoStr, color);
		                        	
		                        	
		                        }else{
		                    	 model.setIsOaUser("true");
		                    	 model.setUserId(p.getUuid());
		                    	 
		                    	//保存对象  
		                    	 addHumanDoc(request,model);
		 	                    importCount++;
		 	                    infoStr="导入成功";
		 	                    color="black";
		 	                    getPersonInfoStr(sb, model, infoStr, color);
		                        }
		                    }else{
		                    	infoStr = "导入失败,用户名不存在！";
								getPersonInfoStr(sb, model, infoStr, color);
						        continue;
		                    } 
		                    
	                    }else{//用户名是空
	                    	  model.setIsOaUser("false");
	                    	//保存对象  
	                    	  addHumanDoc(request,model);
	  	                    importCount++;
	  	                    infoStr="导入成功";
	  	                    color="black";
	  	                    getPersonInfoStr(sb, model, infoStr, color);
	                    }
//	                    
	                   /* //保存对象  
	                    addHumanDoc(model);
	                    importCount++;
	                    infoStr="导入成功";
	                    color="black";
	                    getPersonInfoStr(sb, model, infoStr, color);*/
	                }
	              
	                if(!sb.equals("[")){
	                	//System.out.println("进来了");
	        			sb.deleteCharAt(sb.length() -1);
	        		}
	        		sb.append("]");
	        		String data = sb.toString();
	        		List<Map<String, String>> list =  TeeJsonUtil.JsonStr2MapList(data);
	        		json.setRtData(list);
	        		
	                json.setRtState(true);
	                json.setRtMsg(importCount+"");
	            }
	        }
	        wb.close();  
		}catch(Exception ex){
			ex.printStackTrace();
			json.setRtMsg("文件格式不对，请按模板进行内容填写");
			json.setRtState(false);
			
		}
		return json;
	}
	
	/**
	 * 导入人事档案返回导入详细信息
	 * @author 
	 * @date 
	 * @param sb 返回所有字符串信息
	 * @param model
	 * @param infoStr  说明
	 * @param color 颜色
	 * @return
	 */
	public StringBuffer getPersonInfoStr(StringBuffer sb ,TeeHumanDocModel model ,String infoStr ,  String color){
		sb.append("{");
        sb.append("deptName:\"" + model.getDeptIdName() + "\"");
        sb.append(",userName:\"" + model.getUserName() + "\"");
        sb.append(",personName:\"" + model.getPersonName() + "\"");
        sb.append(",roleName:\"" + model.getRoleName()+ "\"");
        sb.append(",color:\"" + color + "\"");
        sb.append(",info:\"" + infoStr + "\"");
        sb.append("},");
        return sb;
	}
	
	public void updateHDoc(TeeHumanDocModel humanDocModel) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanDoc humanDoc = humanDao.get(humanDocModel.getSid());
		String birthdayDesc=humanDocModel.getBirthdayDesc();
		String graduateDateDesc = humanDocModel.getGraduateDateDesc();
		String joinDateDesc = humanDocModel.getJoinDateDesc();
		String joinPartyDateDesc = humanDocModel.getJoinPartyDateDesc();
		String insuranceName = humanDocModel.getInsuranceName();
		Map<String, String> map = humanDocModel.getMap();//自定义字段
		List<TeePmCustomerFieldModel> list = humanDocModel.getList();
		if(list!=null && list.size()>0){
			String sql="";
			for(TeePmCustomerFieldModel pm:list){
				String str = map.get("EXTRA_"+pm.getSid());
				sql+="EXTRA_"+pm.getSid()+"='"+str+"',";
			}
			sql=sql.substring(0, sql.length()-1);
			sql="update PERSONNEL_MANAGEMENT set "+sql+" where sid="+humanDocModel.getSid();
			simpleDaoSupport.executeNativeUpdate(sql,null);
		}
		if(!TeeUtility.isNullorEmpty(insuranceName)){
			List<TeeHrInsurancePara> find = simpleDaoSupport.find("from TeeHrInsurancePara where insuranceName='"+insuranceName+"'", null);
            if(find!=null && find.size()>0){
            	TeeHrInsurancePara para = find.get(0);
            	humanDoc.setHrInsurancePara(para);
            }
		}
		if(!TeeUtility.isNullorEmpty(birthdayDesc)){
			Calendar cl = Calendar.getInstance();
			cl.setTime(formatter.parse(birthdayDesc));
			humanDoc.setBirthday(cl);
		}
		if(!TeeUtility.isNullorEmpty(graduateDateDesc)){
			Calendar cl1 = Calendar.getInstance();
			cl1.setTime(formatter.parse(graduateDateDesc));
			humanDoc.setGraduateDate(cl1);
		}
		if(!TeeUtility.isNullorEmpty(joinDateDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(joinDateDesc));
			humanDoc.setJoinDate(cl2);
		}
		if(!TeeUtility.isNullorEmpty(joinPartyDateDesc)){
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(formatter.parse(joinPartyDateDesc));
			humanDoc.setJoinPartyDate(cl3);
		}
		BeanUtils.copyProperties(humanDocModel,humanDoc);
		TeePerson person = new TeePerson();
		if(humanDocModel.getUserId()!=0){
			person = (TeePerson)personDao.get(humanDocModel.getUserId());
			humanDoc.setOaUser(person);
		}
		TeeDepartment dept = new TeeDepartment();
		if(humanDocModel.getDeptId()!=0){
			dept = (TeeDepartment)deptDao.get(humanDocModel.getDeptId());
			humanDoc.setDept(dept);
		}
		TeeUserRole role = new TeeUserRole();
		if(humanDocModel.getRoleId()!=0){
			role = (TeeUserRole)roleDao.get(humanDocModel.getRoleId());
			humanDoc.setRole(role);
		}
		TeeHrInsurancePara hrInsurancePara = new TeeHrInsurancePara();
		if(humanDocModel.getInsuranceId()!=0){
			hrInsurancePara.setSid(humanDocModel.getInsuranceId());
			humanDoc.setHrInsurancePara(hrInsurancePara);
		}
		humanDao.update(humanDoc);
		
	}

	public List<Map<String,String>> findHmanAll(HttpServletRequest request,List<TeePmCustomerFieldModel> pmList) {
		List<Map<String,String>> modelList=new ArrayList<Map<String,String>>();
		String ids = request.getParameter("ids");
		String sql="";
		if(ids!=null && !"".equals(ids)){
			sql="from TeeHumanDoc where sid in ("+ids+")";
		}else{
			sql="from TeeHumanDoc ";
		}
		 List<TeeHumanDoc> find = simpleDaoSupport.find(sql, null);
		   if(find!=null && find.size()>0){
			   for(TeeHumanDoc d:find){
				   TeeHumanDocModel model=new TeeHumanDocModel();
				   Map<String,String> map=new HashMap<String,String>();
				   map.put("sid", d.getSid()+"");
				   map.put("userName", d.getPersonName());//姓名
				   if(d.getDept()!=null){
					   map.put("deptIdName", d.getDept().getDeptName());//部门
				   }else{
					   map.put("deptIdName","");
				   }
				   if(d.getRole()!=null){
					   map.put("roleName", d.getRole().getRoleName());//角色
				   }else{
					   map.put("roleName","");
				   }
				   map.put("idCard", d.getIdCard());//身份证号码
				   map.put("codeNumber", d.getCodeNumber());//档案编号
				   map.put("nativePlace", d.getNativePlace());//籍贯
				   map.put("workNumber", d.getWorkNumber());//工号
				   map.put("graduateSchool", d.getGraduateSchool());//毕业院校
				   map.put("mobileNo", d.getMobileNo());//手机号码
				   map.put("telNo", d.getTelNo());//电话号码
				   map.put("email", d.getEmail());//电子邮件
				   map.put("qqNo", d.getQqNo());//QQ号码
				   map.put("address", d.getAddress());//家庭地址
				   map.put("otherAddress", d.getOtherAddress());//其他联系地址
				   map.put("postState", d.getPostState());//职务
				   if(d.getHrInsurancePara()!=null){
					   map.put("insuranceName", d.getHrInsurancePara().getInsuranceName());//保险系数
				   }else{
					   map.put("insuranceName","");
				   }
				   map.put("gender", d.getGender());//性别
				   map.put("health", d.getHealth());//健康状况
				   map.put("householdPlace", d.getHouseholdPlace());//户口所在地
				   map.put("joinPartyDateDesc", TeeDateUtil.format(d.getJoinPartyDate()));//入党（团）时间
				   map.put("graduateDateDesc", TeeDateUtil.format(d.getGraduateDate()));//毕业时间
				   map.put("birthdayDesc", TeeDateUtil.format(d.getBirthday()));//出生日期
				   map.put("ethnicity", d.getEthnicity());//民族
				   map.put("joinDateDesc", TeeDateUtil.format(d.getJoinDate()));//入职时间
				   map.put("msn", d.getMsn());//MSN
				   map.put("major", d.getMajor());//专业
				   map.put("englishName", d.getEnglishName());//英文名
					//在职状态
				    String statusTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_STATUS_TYPE",d.getStatusType());
				   // model.setStatusType(statusTypeDesc);	//员工状态
				    map.put("statusTypeDesc", statusTypeDesc);
				    //员工类型
				    String employeeTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_EMPLOYEE_TYPE",d.getEmployeeType());
				   // model.setEmployeeType(employeeTypeDesc);//员工类型
				    map.put("employeeTypeDesc", employeeTypeDesc);
				    //婚姻类型
				    String marriageDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_MARRIAGE",d.getMarriage());
				   // model.setMarriage(marriageDesc);//婚姻状况
				    map.put("marriageDesc", marriageDesc);
				    //户口类型
				    String householdDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_HOUSEHOLD",d.getHousehold());
				    //model.setHousehold(householdDesc);//户口类型
				    map.put("householdDesc", householdDesc);
				   //政治面貌
				    String politicsDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_POLITICS",d.getPolitics());
				    //model.setPolitics(politicsDesc);	//政治面貌
				    map.put("politicsDesc", politicsDesc);
				   //学历
				    String educationDegreeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_EDUCATIONDEGREE",d.getEducationDegree());
				    //model.setEducationDegree(educationDegreeDesc);//学历
				    map.put("educationDegreeDesc", educationDegreeDesc);
				   //学位
				    String degreeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_DEGREE",d.getDegree());
				    //model.setDegree(degreeDesc);//学位
				    map.put("degreeDesc", degreeDesc);
				    Map map2 = simpleDaoSupport.executeNativeUnique("select * from  PERSONNEL_MANAGEMENT where sid=?",new Object[]{d.getSid()});
				    if(pmList!=null && pmList.size()>0){
				    	for(TeePmCustomerFieldModel m:pmList){
				    		map.put("EXTRA_"+m.getSid(), TeeStringUtil.getString(map2.get("EXTRA_"+m.getSid())));
				    	}
				    }
				    modelList.add(map);
			   }
		   }
		
		
		
		return modelList;
	}

	/**
	 * 批量设置保险账套
	 * */
	public TeeJson getInsuranceAll(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String sids = request.getParameter("ids");
		int insuranceId=TeeStringUtil.getInteger(request.getParameter("insuranceId"),0);
		String sql="update TeeHumanDoc set hrInsurancePara="+insuranceId+" where 1=1 ";
		if(sids!=null && !"".equals(sids)){
			sql+=" and sid in ("+sids+")";
		}
		int query = simpleDaoSupport.deleteOrUpdateByQuery(sql,null);
		if(query>0){
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}
	
	
	

	
}
