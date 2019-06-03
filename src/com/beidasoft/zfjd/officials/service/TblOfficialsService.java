package com.beidasoft.zfjd.officials.service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.department.model.OrganizationPersonModel;
import com.beidasoft.zfjd.department.service.OrganizationPersonService;
import com.beidasoft.zfjd.officials.bean.TblOfficials;
import com.beidasoft.zfjd.officials.dao.TblOfficialsDao;
import com.beidasoft.zfjd.officials.model.TblOfficialsModel;
import com.beidasoft.zfjd.subject.dao.SubjectDao;
import com.beidasoft.zfjd.subjectPerson.model.TblSubjectPersonModel;
import com.beidasoft.zfjd.subjectPerson.service.TblSubjectPersonService;
import com.beidasoft.zfjd.system.bean.SysBusinessRelation;
import com.beidasoft.zfjd.system.model.PersonUserModel;
import com.beidasoft.zfjd.system.service.PersonUserService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.secure.TeePassEncryptMD5;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

/**
 * 执法人员表SERVICE类
 */
@Service
public class TblOfficialsService extends TeeBaseService {

	@Autowired
	private TblOfficialsDao officialsDao;
	@Autowired
	private OrganizationPersonService organizationPersonService;
	@Autowired
	private TblSubjectPersonService tblSubjectPersonService;
	@Autowired
	private SubjectDao subjectDao;
	@Autowired
	private TeePersonDao personDao;
	@Autowired
	private TeeDeptDao departmentDao;
	@Autowired
	private TeeUserRoleDao userRoleDao;
	@Autowired
	private PersonUserService personUserService;
	
	/*
	 * 保存用户信息
	 */
	public TeeJson save(TblOfficialsModel officialsModel){
		TeeJson json = new TeeJson();
		//获取当前登录人
		TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
		//当前登录人所在的部门
		TeeDepartment department = user.getDept();
		//部门ID
		int departmentId = department.getUuid();
		String deptId = "" ;
		String subjectId = "";
		if(!TeeUtility.isNullorEmpty(getSysBusinessRelationByDeptId(departmentId))){
			//通过部门ID，查询到SysBusinessRelation对象（）
			SysBusinessRelation sysBusinessRelation = getSysBusinessRelationByDeptId(departmentId);
			//获得部门id
			deptId = sysBusinessRelation.getBusinessDeptId();
			//获得主体id
			subjectId = sysBusinessRelation.getBusinessSubjectId();
		}
		//创建实例化实体类对象
		TblOfficials officials = new TblOfficials();
		officialsModel.setId(UUID.randomUUID().toString());
		BeanUtils.copyProperties(officialsModel, officials);
		//单独处理日期
		if(!TeeUtility.isNullorEmpty(officialsModel.getBirthStr())){
			officials.setBirth(TeeDateUtil.format(officialsModel.getBirthStr(), "yyyy-MM-dd"));
		}
		if(!TeeUtility.isNullorEmpty(officialsModel.getDepartmentGettimeStr())){
			officials.setDepartmentGettime(TeeDateUtil.format(officialsModel.getDepartmentGettimeStr(), "yyyy-MM-dd"));
		}
		if(!TeeUtility.isNullorEmpty(officialsModel.getCityGettimeStr())){
			officials.setCityGettime(TeeDateUtil.format(officialsModel.getCityGettimeStr(), "yyyy-MM-dd"));
		}
		if(!TeeUtility.isNullorEmpty(officialsModel.getEffectiveTimeStr())){
			officials.setEffectiveTime(TeeDateUtil.format(officialsModel.getEffectiveTimeStr(), "yyyy-MM-dd"));
		}
		if(!TeeUtility.isNullorEmpty(officialsModel.getDeptEffectiveTimeStr())){
			officials.setDeptEffectiveTime(TeeDateUtil.format(officialsModel.getDeptEffectiveTimeStr(), "yyyy-MM-dd"));
		}
		//部门人员关联表插入数据
		if(!TeeUtility.isNullorEmpty(deptId)){
			OrganizationPersonModel organizationPersonModel = new OrganizationPersonModel();
			organizationPersonModel.setId(UUID.randomUUID().toString());
			organizationPersonModel.setDeptId(deptId);
			organizationPersonModel.setSubjectId(subjectId);
			organizationPersonModel.setPersonId(officialsModel.getId());
			organizationPersonService.save(organizationPersonModel);
		}
		//主体人员关联表插入数据
		if(!TeeUtility.isNullorEmpty(officials.getId())){
			TblSubjectPersonModel tblSubjectPersonModel = new TblSubjectPersonModel();
			tblSubjectPersonModel.setId(UUID.randomUUID().toString());
			tblSubjectPersonModel.setPersonId(officials.getId());
			tblSubjectPersonModel.setSubjectId(officialsModel.getBusinessSubName());
			if(!TeeUtility.isNullorEmpty(officialsModel.getEntrustId())){
				tblSubjectPersonModel.setOrganizationId(officialsModel.getEntrustId());
			}
			tblSubjectPersonService.save(tblSubjectPersonModel);
		}
		officials.setCreateTime(new Date());
		officials.setCreateId(user.getUserId());
		json.setRtState(true);
		officialsDao.save(officials);
		return json;
	}
	/*
	 * 分配账号
	 */
	public TeeJson saveUser(TblOfficialsModel officialsModel,OrgCtrlInfoModel orgCtrl) throws NoSuchAlgorithmException{
		TeeJson json = new TeeJson();
		TeePerson person = new TeePerson();
		TeeDepartment department = new TeeDepartment();
		TeeUserRole userRole = new TeeUserRole();
		//获取当前登录人信息
		TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
		//UUID
//		Map<String, Object> uuids = officialsDao.nextUuid();
//		String a = uuids.toString();
//		Integer b = Integer.parseInt(a.substring(4, a.length()-1));
//		person.setUuid(b);
		//账号
		person.setUserId(officialsModel.getName());
		//用户姓名
		person.setUserName(officialsModel.getUsername());
		//密码
		person.setPassword(TeePassEncryptMD5.cryptDynamic("zfjd123456"));
		//所属部门
		Map<String, Object> deptUuid = officialsDao.deptUuid(officialsModel.getId());
		String deptUuids = deptUuid.toString();
		Integer sysDeptId = Integer.parseInt(deptUuids.substring(4, deptUuids.length()-1));
		department = departmentDao.get(sysDeptId);
		person.setDept(department);
		//精灵在线状况 0 - 不在线 1-在线
		person.setIspiritOnlineStatus(0);
		//主开关在线状况 0 - 不在线 1-在线
		person.setMainOnlineStatus(0);
		//禁止WEB登录
		person.setNotWebLogin("0");
		//考勤签到方式
		person.setSignWay("1");
		//角色编号2员工
		userRole =userRoleDao.selectByUUId(2);
		person.setUserRole(userRole);
		//
		person.setUsePops("0");
		//界面主题
		person.setTheme("classic");
		//管理范围
		person.setPostPriv(-1);
		//禁止查看用户列表
		person.setNotViewUser("0");
		//禁止登录OA系统
		person.setNotLogin("0");
		//手机号码是否公开
		person.setMobilNoHidden("0");
		//内部邮箱容量
		person.setEmailCapacity(500);
		//个人文件柜容量
		person.setFolderCapacity(500);
		//考勤排班类型
		person.setDutyType("0");
		//禁止移动端登录
		person.setNotMobileLogin("0");;
		//禁止PC端登录
		person.setNotPcLogin("0");
		//删除状态
		person.setDeleteStatus("0");
		//UUID
		String uuidString = personDao.save(person).toString();
		Integer uuiddInteger = Integer.parseInt(uuidString);
//		person.setUuid(uuiddInteger);
		personDao.addPerson(person);
		//关联表 person_user插入数据
		if(!TeeUtility.isNullorEmpty(officialsModel.getId())){
			PersonUserModel personUserModel = new PersonUserModel();
			personUserModel.setId(UUID.randomUUID().toString());
			personUserModel.setPersonId(officialsModel.getId());
			personUserModel.setIsDelete(0);
			personUserModel.setUserUuid(uuiddInteger);
			personUserModel.setCreatorTime(new Date());
			personUserModel.setCreatorId(user.getUserId());
			personUserModel.setCreatorName(user.getUserName());
			personUserService.save(personUserModel);
		}
		//关联表PERSON_MENU_GROUP（用户-权限组）
		//查询执法人员zfjd的uuid
		Map<String, Object> zfjdId = officialsDao.zfjdId();
		String zfjdIdString  = zfjdId.toString();
		Integer zfjdInteger = Integer.parseInt(zfjdIdString.substring(4, zfjdIdString.length()-1));
		simpleDaoSupport.executeNativeUpdate("insert into PERSON_MENU_GROUP values (?,?)",
				new Object[]{uuiddInteger,zfjdInteger});
		//tbl_base_person表中插入用户密码
		try {
			simpleDaoSupport.executeNativeUpdate("update tbl_base_person set username =? , password =?  where id =?",
					new Object[]{officialsModel.getName(),TeePassEncryptMD5.cryptDynamic("zfjd123456"),officialsModel.getId()});
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return json;
	}
	/**
	 * 重置密码
	 * @param id
	 * @param orgCtrl
	 * @return
	 * @date:2019年3月12日下午3:27:46
	 * @author:yxy
	 * @throws NoSuchAlgorithmException 
	 */
	 public TeeJson resetPassword(String id) throws NoSuchAlgorithmException{
		TeeJson json = new TeeJson();
		//根据ID从关系表中获取数据
		Map<String, Object> uuid = officialsDao.getUuid(id);
		String a = uuid.toString();
		Integer b = Integer.parseInt(a.substring(4, a.length()-1));
		//默认新密码为"zfjd123456"
		String newPaw =TeePassEncryptMD5.cryptDynamic("zfjd123456");
		//person表中更新新密码
		simpleDaoSupport.executeNativeUpdate("update person set PWD =? where uuid = ?",
				new Object[]{newPaw,b});
		//tbl_base_person执法人员表中更新新密码
		simpleDaoSupport.executeNativeUpdate("update tbl_base_person set password =? where id =?",
				new Object[]{newPaw,id});
		return json;
	 }
	 
	 /**
	  * 回收账号
	  * @param id
	  * @return
	  * @throws NoSuchAlgorithmException
	  * @date:2019年3月12日下午4:12:52
	  * @author:yxy
	  */
	 public TeeJson resetUser(String id){
			TeeJson json = new TeeJson();
			//根据ID从关系表中获取数据
			Map<String, Object> uuid = officialsDao.getUuid(id);
			String a = uuid.toString();
			Integer b = Integer.parseInt(a.substring(4, a.length()-1));
			//删除person表中用户账号信息
			simpleDaoSupport.executeNativeUpdate("delete from person where uuid = ?",
					new Object[]{b});
			//删除关系表
			Date date = new Date();
			simpleDaoSupport.executeNativeUpdate("update tbl_sys_person_user set is_delete = 1,delete_time =? where person_id = ?",
					new Object[]{date,id});
			//更新执法人员表信息
			simpleDaoSupport.executeNativeUpdate("update tbl_base_person set username = '' , password = ''  where id =?",
					new Object[]{id});
			return json;
		 }
	/*
	 * 更新用户信息
	 */
	public TeeJson updateUser(TblOfficialsModel officialsModel){
		//获取当前登录人
		TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		
		TblOfficials officials = getById(officialsModel.getId());
		//处理创建审核删除日期
		if(!TeeUtility.isNullorEmpty(officials.getCreateTime())){
			officialsModel.setCreateTime(officials.getCreateTime());
		}
		if(!TeeUtility.isNullorEmpty(officials.getExamineTime())){
			officialsModel.setExamineTime(officials.getExamineTime());
		}
		if(!TeeUtility.isNullorEmpty(officials.getDisExamineTime())){
			officialsModel.setDisExamineTime(officials.getDisExamineTime());
		}
		if(!TeeUtility.isNullorEmpty(officials.getDeleteTime())){
			officialsModel.setDeleteTime(officials.getDeleteTime());
		}
		if(!TeeUtility.isNullorEmpty(officials.getCreateId())){
			officialsModel.setCreateId(officials.getCreateId());
		}
		
		BeanUtils.copyProperties(officialsModel, officials);
		//单独处理日期
		if(!TeeUtility.isNullorEmpty(officialsModel.getBirthStr())){
			officials.setBirth(TeeDateUtil.format(officialsModel.getBirthStr(), "yyyy-MM-dd"));
		}
		if(!TeeUtility.isNullorEmpty(officialsModel.getDepartmentGettimeStr())){
			officials.setDepartmentGettime(TeeDateUtil.format(officialsModel.getDepartmentGettimeStr(), "yyyy-MM-dd"));
		}
		if(!TeeUtility.isNullorEmpty(officialsModel.getCityGettimeStr())){
			officials.setCityGettime(TeeDateUtil.format(officialsModel.getCityGettimeStr(), "yyyy-MM-dd"));
		}
		if(!TeeUtility.isNullorEmpty(officialsModel.getEffectiveTimeStr())){
			officials.setEffectiveTime(TeeDateUtil.format(officialsModel.getEffectiveTimeStr(), "yyyy-MM-dd"));
		}
		if(!TeeUtility.isNullorEmpty(officialsModel.getDeptEffectiveTimeStr())){
			officials.setDeptEffectiveTime(TeeDateUtil.format(officialsModel.getDeptEffectiveTimeStr(), "yyyy-MM-dd"));
		}
		//更新关系表  直接更新 主体businessSubName 组织entrustId
		if(!TeeUtility.isNullorEmpty(officialsModel.getBusinessSubName())){
			simpleDaoSupport.executeNativeUpdate("delete from tbl_base_subject_person where person_id =? ", new Object[]{officialsModel.getId()});
			TblSubjectPersonModel tblSubjectPersonModel = new TblSubjectPersonModel();
			tblSubjectPersonModel.setId(UUID.randomUUID().toString());
			tblSubjectPersonModel.setPersonId(officials.getId());
			if(!TeeUtility.isNullorEmpty(officialsModel.getBusinessSubId())){
				tblSubjectPersonModel.setSubjectId(officialsModel.getBusinessSubId());
			}else{
				tblSubjectPersonModel.setSubjectId(officialsModel.getBusinessSubName());
			}
			if(!TeeUtility.isNullorEmpty(officialsModel.getEntrustId())){
				tblSubjectPersonModel.setOrganizationId(officialsModel.getEntrustId());
			}
			tblSubjectPersonService.save(tblSubjectPersonModel);
		}
		
		officials.setCreateTime(officialsModel.getCreateTime());
		officials.setExamineTime(officialsModel.getExamineTime());
		officials.setDisExamineTime(officialsModel.getDisExamineTime());
		officials.setDeleteTime(officialsModel.getDeleteTime());
		officials.setCreateId(officialsModel.getCreateId());
		officials.setUpdateTime(new Date());
		officials.setUpdateId(user.getUserId());
		officialsDao.update(officials);
		
		json.setRtState(true);
		
		return json;
	}
	/*
	 * 更新状态
	 */
	public void update(TblOfficials officials){
		officialsDao.update(officials);
	}
	
	/*
	 * 更新用户信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void update(Map updateItem,String lawId){
		officialsDao.update(updateItem, lawId);
	}
	/*
	 * 通过对象删除用户信息
	 */
	public void deleteByObject(TblOfficials law){
		officialsDao.deleteByObj(law);
	}
	/*
	 * 通过主键删除用户信息
	 */
	public void deleteById(String id){
		officialsDao.delete(id);
	}
	/**
	 * 多删
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public void deletes(String id ) {

		String ids [] = id.split(",");
		
		officialsDao.deletes(ids);
    }
	/*
	 * 通过主键查询用户信息
	 */
	public TblOfficials getById(String id){
		return officialsDao.get(id);
	}
	/*
	 * 返回所有用户信息
	 */
	public List<TblOfficials> getAllUsers(){
		return officialsDao.findUsers();
	}
	/*
	 * 根据分页进行查询
	 */
	public List<TblOfficials> listByPage(int firstResult,int rows){
		return officialsDao.listByPage( firstResult, rows, null);
	  }
	
	public List<TblOfficials> findListByPageSearch(TeeDataGridModel tModel, TblOfficialsModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return officialsDao.findListByPageSearch(tModel.getFirstResult(), tModel.getRows(), cbModel, orgCtrl);
    }

	public long listSearchCount(TblOfficialsModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return officialsDao.listSearchCount(cbModel, orgCtrl);
    }
	
    public List<TblOfficials> findListByPageSearchQuery(TeeDataGridModel tModel, TblOfficialsModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return officialsDao.findListByPageSearchQuery(tModel.getFirstResult(), tModel.getRows(), cbModel, orgCtrl);
    }

	public long listSearchCountQuery(TblOfficialsModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return officialsDao.listSearchCountQuery(cbModel, orgCtrl);
    }
	  //查询主体
	  @SuppressWarnings("rawtypes")
	public List<Map> listByBus(int firstResult,int rows,String id){
			return officialsDao.listByBus( firstResult, rows, id);
		  }
   public List<TblOfficials> listByPage(int firstResult,int rows,TblOfficialsModel queryModel){
		 return officialsDao.listByPage(firstResult, rows, queryModel);
		  
	  }
   
	  public long getTotal(){
		  return officialsDao.getTotal();
	  }
	  @SuppressWarnings("rawtypes")
	public List<Map> getDeptId(int firstResult, int rows,String DeptId){
		  return officialsDao.getDeptId(firstResult,rows,DeptId);
	  }
	  public long getTotal(TblOfficialsModel queryModel){
		  return officialsDao.getTotal(queryModel);
	  }
	  	/**
	     * 根据指定部门ID，查询SysBusinessRelation关系对象
	     *
	     * @param beanInfo
	     */
	    public SysBusinessRelation getSysBusinessRelationByDeptId(int deptId) {
	        return (SysBusinessRelation)simpleDaoSupport.unique("from SysBusinessRelation where deptRelation.uuid="+deptId, null);
	    }
	   /**
	    * 查看界面获取人员信息
	    */
	    public TblOfficialsModel getPersonById(String id){
	        TblOfficials officials = officialsDao.get(id);
	        TblOfficialsModel officialsModel = new TblOfficialsModel();
	        officialsModel = copyAllProperties(officials);
	        return officialsModel;
	    }
	    /**
	     * 代码表中获取值
	     */
	    @SuppressWarnings("rawtypes")
		public TblOfficialsModel copyAllProperties(TblOfficials officials){
	        TblOfficialsModel officialsModel = new TblOfficialsModel();
	        BeanUtils.copyProperties(officials, officialsModel);
	        //处理生日
	        if(!TeeUtility.isNullorEmpty(officials.getBirth())){
				officialsModel.setBirthStr(TeeDateUtil.format(officials.getBirth(), "yyyy-MM-dd"));
			}
	        //处理有效期
	        if(!TeeUtility.isNullorEmpty(officials.getDepartmentGettime())){
				officialsModel.setDepartmentGettimeStr(TeeDateUtil.format(officials.getDepartmentGettime(), "yyyy-MM-dd"));
			}
			if(!TeeUtility.isNullorEmpty(officials.getCityGettime())){
				officialsModel.setCityGettimeStr(TeeDateUtil.format(officials.getCityGettime(), "yyyy-MM-dd"));
			}
			if(!TeeUtility.isNullorEmpty(officials.getEffectiveTime())){
				officialsModel.setEffectiveTimeStr(TeeDateUtil.format(officials.getEffectiveTime(), "yyyy-MM-dd"));
			}
			if(!TeeUtility.isNullorEmpty(officials.getDeptEffectiveTime())){
				officialsModel.setDeptEffectiveTimeStr(TeeDateUtil.format(officials.getDeptEffectiveTime(), "yyyy-MM-dd"));
			}
	        //代码表获取政治面貌
	        if(!TeeUtility.isNullorEmpty(officials.getPolitive())){
	        	officialsModel.setPolitive(TeeSysCodeManager.getChildSysCodeNameCodeNo("SYSTEM_CODE_POLITIVE", officials.getPolitive()));
	        }
	        //代码表获取民族nation
	        if(!TeeUtility.isNullorEmpty(officials.getNation())){
	        	officialsModel.setNation(TeeSysCodeManager.getChildSysCodeNameCodeNo("ENROLMENT_CODE_NATION", officials.getPolitive()));
	        }
	        //代码表获最高学历education
	        if(!TeeUtility.isNullorEmpty(officials.getEducation())){
	        	officialsModel.setEducation(TeeSysCodeManager.getChildSysCodeNameCodeNo("SYSTEM_CODE_EDUCATION", officials.getPolitive()));
	        }
	        //代码表获取职级jobClass
	        if(!TeeUtility.isNullorEmpty(officials.getJobClass())){
	        	officialsModel.setJobClass(TeeSysCodeManager.getChildSysCodeNameCodeNo("JOB_CLASS", officials.getPolitive()));
	        }
	        //获取所属主体
	        if(!TeeUtility.isNullorEmpty(officials.getId())){
	        	List<Map> maps = officialsDao.listOrgById(0,10, officials.getId());
	        	if(maps.size() != 0){
	        		String name = (String) maps.get(0).get("SUB_NAME");
		        	officialsModel.setBusinessSubName(name);
	        	}
	        }
	        //获取委托组织
	        if(!TeeUtility.isNullorEmpty(officials.getEntrustId())){
	        	List<Map> maps = subjectDao.listOrgById(0,10, officials.getEntrustId());
	        	if(maps.size() != 0){
	        		String name = (String) maps.get(0).get("SUB_NAME");
		        	officialsModel.setEntrustId(name);
	        	}
	        }
	        return officialsModel;
	    }
}
