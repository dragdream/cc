package com.beidasoft.zfjd.supervise.service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.officials.dao.TblOfficialsDao;
import com.beidasoft.zfjd.supervise.bean.SupPerson;
import com.beidasoft.zfjd.supervise.dao.SupPersonDao;
import com.beidasoft.zfjd.supervise.model.SupPersonModel;
import com.beidasoft.zfjd.supervise.model.SuperviseSuppersonModel;
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
 * 监督人员表SERVICE类
 */
@Service
public class SupPersonService extends TeeBaseService {

	@Autowired
	private SupPersonDao supPersonDao;
	@Autowired
	private SuperviseSuppersonService superviseSuppersonService;
	@Autowired
	private TeePersonDao personDao;
	@Autowired
	private TeeDeptDao departmentDao;
	@Autowired
	private TeeUserRoleDao userRoleDao;
	@Autowired
	private PersonUserService personUserService;
	@Autowired
	private TblOfficialsDao officialsDao;
	
	/*
	 * 保存用户信息
	 */
	public TeeJson save(SupPersonModel supPersonModel){
		//获取当前登录人
		TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		//创建实例化实体类对象
		SupPerson supPerson = new SupPerson();
		supPerson.setId(UUID.randomUUID().toString());
		supPersonModel.setId(supPerson.getId());
		BeanUtils.copyProperties(supPersonModel, supPerson);
		//关联表插入数据
		if(!TeeUtility.isNullorEmpty(supPersonModel.getDepartmentName())){
			SuperviseSuppersonModel superviseSuppersonModel = new SuperviseSuppersonModel();
			superviseSuppersonModel.setId(UUID.randomUUID().toString());
			superviseSuppersonModel.setSuppersonId(supPersonModel.getId());
			superviseSuppersonModel.setSuperviseId(supPersonModel.getDepartmentName());;
			superviseSuppersonService.save(superviseSuppersonModel);
		}
		if(!TeeUtility.isNullorEmpty(supPersonModel.getBirthStr())){
		supPerson.setBirth(TeeDateUtil.format(supPersonModel.getBirthStr(), "yyyy-MM-dd"));
		}
		supPerson.setCreateTime(new Date());
		supPerson.setCreateId(user.getUserId());
		supPersonDao.save(supPerson);
		json.setRtState(true);
		return json;
	}
	/*
	 * 更新用户信息
	 */
	public TeeJson updateUser(SupPersonModel supPersonModel){
		//获取当前登录人
		TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		
		SupPerson supPerson = getById(supPersonModel.getId());
		
		//处理创建日期审核日期删除日期
		if(!TeeUtility.isNullorEmpty(supPerson.getCreateTime())){
			supPersonModel.setCreateTime(supPerson.getCreateTime());
		}
		if(!TeeUtility.isNullorEmpty(supPerson.getExamineTime())){
			supPersonModel.setExamineTime(supPerson.getExamineTime());
		}
		if(!TeeUtility.isNullorEmpty(supPerson.getDisExamineTime())){
			supPersonModel.setDisExamineTime(supPerson.getDisExamineTime());
		}
		if(!TeeUtility.isNullorEmpty(supPerson.getDeleteTime())){
			supPersonModel.setDeleteTime(supPerson.getDeleteTime());
		}
		if(!TeeUtility.isNullorEmpty(supPerson.getCreateId())){
			supPersonModel.setCreateId(supPerson.getCreateId());
		}
		BeanUtils.copyProperties(supPersonModel, supPerson);
		//关联表更新数据 判断是否存在该数据 有则更新 无则新增
			SuperviseSuppersonModel superviseSuppersonModel = new SuperviseSuppersonModel();
			superviseSuppersonModel.setId(UUID.randomUUID().toString());
			System.out.println(supPersonModel.getDepartmentId());
			if(!TeeUtility.isNullorEmpty(supPersonModel.getDepartmentId())){
				superviseSuppersonModel.setSuperviseId(supPersonModel.getDepartmentId());
			}else{
				superviseSuppersonModel.setSuperviseId(supPersonModel.getDepartmentName());
			}
			
			superviseSuppersonModel.setSuppersonId(supPersonModel.getId());
			simpleDaoSupport.executeNativeUpdate("delete from tbl_base_supervise_supperson where supperson_id =?", new Object[]{supPersonModel.getId()});
			superviseSuppersonService.save(superviseSuppersonModel);

		//单独处理生日
		if(!TeeUtility.isNullorEmpty(supPersonModel.getBirthStr())){
			supPerson.setBirth(TeeDateUtil.format(supPersonModel.getBirthStr(), "yyyy-MM-dd"));
		}
		
		supPerson.setCreateTime(supPersonModel.getCreateTime());
		supPerson.setExamineTime(supPersonModel.getExamineTime());
		supPerson.setDisExamineTime(supPersonModel.getDisExamineTime());
		supPerson.setDeleteTime(supPersonModel.getDeleteTime());
		supPerson.setCreateId(supPersonModel.getCreateId());
		supPerson.setUpdateTime(new Date());
		supPerson.setUpdateId(user.getUserId());
		json.setRtState(true);
		
		supPersonDao.update(supPerson);
		return json;
	}
	
	/*
	 * 更新
	 */
	public void update(SupPerson supPerson){
		supPersonDao.update(supPerson);
	}
	/*
	 * 更新用户信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void update(Map updateItem,String lawId){
		supPersonDao.update(updateItem, lawId);
	}
	/*
	 * 通过对象删除用户信息
	 */
	public void deleteByObject(SupPerson law){
		supPersonDao.deleteByObj(law);
	}
	/*
	 * 获取所属部门
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> listByBus(int firstResult,int rows,String id){
		return supPersonDao.listByBus( firstResult, rows, id);
	  }
	/*
	 * 通过主键删除用户信息
	 */
	public void deleteById(String id){
		supPersonDao.delete(id);
	}
	/**
	 * 多删
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public void deletes(String id ) {

		String ids [] = id.split(",");
		
		supPersonDao.deletes(ids);
    }
	/*
	 * 通过主键查询用户信息
	 */
	public SupPerson getById(String id){
		return supPersonDao.get(id);
	}
	/*
	 * 返回所有用户信息
	 */
	public List<SupPerson> getAllUsers(){
		return supPersonDao.findUsers();
	}
	/*
	 * 根据分页进行查询
	 */
	public List<SupPerson> listByPage(int firstResult,int rows){
		return supPersonDao.listByPage( firstResult, rows, null);
	  }
	  
   public List<SupPerson> listByPage(int firstResult,int rows,SupPersonModel queryModel){
		 return supPersonDao.listByPage(firstResult, rows, queryModel);
		  
	  }
   
   public List<SupPerson> findListByPage(TeeDataGridModel tModel, SupPersonModel cbModel, OrgCtrlInfoModel orgCtrl) {
       return supPersonDao.findListByPage(tModel.getFirstResult(), tModel.getRows(), cbModel, orgCtrl);
   }

	public long listCount(SupPersonModel cbModel, OrgCtrlInfoModel orgCtrl) {
       return supPersonDao.listCount(cbModel, orgCtrl);
   }
   
   public List<SupPerson> findSearchListBypage(TeeDataGridModel tModel, SupPersonModel cbModel, OrgCtrlInfoModel orgCtrl) {
       return supPersonDao.findSearchListBypage(tModel.getFirstResult(), tModel.getRows(), cbModel, orgCtrl);
   }

	public long listSearchCount(SupPersonModel cbModel, OrgCtrlInfoModel orgCtrl) {
       return supPersonDao.listSearchCount(cbModel, orgCtrl);
   }
   
	  public long getTotal(){
		  return supPersonDao.getTotal();
	  }
   
	  public long getTotal(SupPersonModel queryModel){
		  return supPersonDao.getTotal(queryModel);
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
	    public SupPersonModel getPersonById(String id){
	        SupPerson supPerson = supPersonDao.get(id);
	        SupPersonModel supPersonModel = new SupPersonModel();
	        supPersonModel = copyAllProperties(supPerson);
	        return supPersonModel;
	    }
	    /**
	     * 代码表中获取值
	     */
	    @SuppressWarnings("rawtypes")
		public SupPersonModel copyAllProperties(SupPerson supPerson){
	    	SupPersonModel supPersonModel = new SupPersonModel();
	        BeanUtils.copyProperties(supPerson, supPersonModel);
	        //处理生日
	        if(!TeeUtility.isNullorEmpty(supPerson.getBirth())){
	        	supPersonModel.setBirthStr(TeeDateUtil.format(supPerson.getBirth(), "yyyy-MM-dd"));
			}
	        //代码表获取政治面貌
	        if(!TeeUtility.isNullorEmpty(supPerson.getPolitive())){
	        	supPersonModel.setPolitive(TeeSysCodeManager.getChildSysCodeNameCodeNo("SYSTEM_CODE_POLITIVE", supPerson.getPolitive()));
	        }
	        //代码表获取民族nation
	        if(!TeeUtility.isNullorEmpty(supPerson.getNation())){
	        	supPersonModel.setNation(TeeSysCodeManager.getChildSysCodeNameCodeNo("ENROLMENT_CODE_NATION", supPerson.getPolitive()));
	        }
	        //代码表获最高学历education
	        if(!TeeUtility.isNullorEmpty(supPerson.getEducation())){
	        	supPersonModel.setEducation(TeeSysCodeManager.getChildSysCodeNameCodeNo("SYSTEM_CODE_EDUCATION", supPerson.getPolitive()));
	        }
	        //代码表获取职级jobClass
	        if(!TeeUtility.isNullorEmpty(supPerson.getJobClass())){
	        	supPersonModel.setJobClass(TeeSysCodeManager.getChildSysCodeNameCodeNo("JOB_CLASS", supPerson.getPolitive()));
	        }
	        //获取所属部门
	        if(!TeeUtility.isNullorEmpty(supPerson.getId())){
	        	List<Map> maps = supPersonDao.listByBus(0,10, supPerson.getId());
	        	if(maps.size() != 0){
	        		String name = (String) maps.get(0).get("NAME");
		        	supPersonModel.setDepartmentName(name);
	        	}
	        }
	        
	        return supPersonModel;
	    }
	    
    /**
     * 分配账号
     * @param supPersonModel
     * @param orgCtrl
     * @return
     * @throws NoSuchAlgorithmException
     * @date:2019年3月12日下午5:36:00
     * @author:yxy
     */
	public TeeJson saveUser(SupPersonModel supPersonModel,OrgCtrlInfoModel orgCtrl) throws NoSuchAlgorithmException{
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
		person.setUserId(supPersonModel.getName());
		//用户姓名
		person.setUserName(supPersonModel.getUsername());
		//密码
		person.setPassword(TeePassEncryptMD5.cryptDynamic("zfjd123456"));
		//所属部门
		Map<String, Object> deptUuid = supPersonDao.deptUuid(supPersonModel.getId());
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
		if(!TeeUtility.isNullorEmpty(supPersonModel.getId())){
			PersonUserModel personUserModel = new PersonUserModel();
			personUserModel.setId(UUID.randomUUID().toString());
			personUserModel.setSuppersonId(supPersonModel.getId());
			personUserModel.setIsDelete(0);
			personUserModel.setUserUuid(uuiddInteger);
			personUserModel.setCreatorTime(new Date());
			personUserModel.setCreatorId(user.getUserId());
			personUserModel.setCreatorName(user.getUserName());
			personUserService.save(personUserModel);
		}
		//关联表PERSON_MENU_GROUP（用户-权限组）
		//获取监督人员权限组ID
		Map<String, Object> zfjdId = supPersonDao.zfjdId();
		String zfjdIdString  = zfjdId.toString();
		Integer zfjdInteger = Integer.parseInt(zfjdIdString.substring(4, zfjdIdString.length()-1));
		simpleDaoSupport.executeNativeUpdate("insert into PERSON_MENU_GROUP values (?,?)",
				new Object[]{uuiddInteger,zfjdInteger});
		//tbl_base_person表中插入用户密码
			simpleDaoSupport.executeNativeUpdate("update tbl_base_supperson set username =? , password =?  where id =?",
					new Object[]{supPersonModel.getName(),TeePassEncryptMD5.cryptDynamic("zfjd123456"),supPersonModel.getId()});
		return json;
	}
	/**
	 * 重置密码
	 * @param id
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @date:2019年3月12日下午5:36:18
	 * @author:yxy
	 */
	 public TeeJson resetPassword(String id) throws NoSuchAlgorithmException{
		TeeJson json = new TeeJson();
		//根据ID从关系表中获取数据
		Map<String, Object> uuid = supPersonDao.getUuid(id);
		String a = uuid.toString();
		Integer b = Integer.parseInt(a.substring(4, a.length()-1));
		//默认新密码为"zfjd123456"
		String newPaw =TeePassEncryptMD5.cryptDynamic("zfjd123456");
		//person表中更新新密码
		simpleDaoSupport.executeNativeUpdate("update person set PWD =? where uuid = ?",
				new Object[]{newPaw,b});
		//tbl_base_person执法人员表中更新新密码
		simpleDaoSupport.executeNativeUpdate("update tbl_base_supperson set password =? where id =?",
				new Object[]{newPaw,id});
		return json;
	 }
	 
	 /**
	  * 回收账号
	  * @param id
	  * @return
	  * @date:2019年3月12日下午5:36:26
	  * @author:yxy
	  */
	 public TeeJson resetUser(String id){
			TeeJson json = new TeeJson();
			//根据ID从关系表中获取数据
			Map<String, Object> uuid = supPersonDao.getUuid(id);
			String a = uuid.toString();
			Integer b = Integer.parseInt(a.substring(4, a.length()-1));
			//删除person表中用户账号信息
			simpleDaoSupport.executeNativeUpdate("delete from person where uuid = ?",
					new Object[]{b});
			//删除关系表
			Date date = new Date();
			simpleDaoSupport.executeNativeUpdate("update tbl_sys_person_user set is_delete = 1,delete_time =? where supperson_id = ?",
					new Object[]{date,id});
			//更新执法人员表信息
			simpleDaoSupport.executeNativeUpdate("update tbl_base_supperson set username = '' , password = ''  where id =?",
					new Object[]{id});
			return json;
		 }
}
