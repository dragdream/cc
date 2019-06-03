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
import com.beidasoft.zfjd.supervise.bean.Supervise;
import com.beidasoft.zfjd.supervise.dao.SuperviseDao;
import com.beidasoft.zfjd.supervise.model.SuperviseModel;
import com.beidasoft.zfjd.system.bean.DepartmentUser;
import com.beidasoft.zfjd.system.bean.SysBusinessRelation;
import com.beidasoft.zfjd.system.dao.DepartmentUserDao;
import com.beidasoft.zfjd.system.dao.SysBusinessRelationDao;
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
import com.tianee.webframe.util.secure.TeePassEncryptMD5;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class SuperviseService extends TeeBaseService {

	@Autowired
	private SuperviseDao superviseDao;
	@Autowired
	private TeeDeptDao deptDao;
	@Autowired
	private SysBusinessRelationDao sysBusinessRelationDao;
	@Autowired
	private TeePersonDao personDao;
	@Autowired
	private TeeUserRoleDao userRoleDao;
	@Autowired
	private DepartmentUserDao departmentUserDao;
	
	/*
	 * 保存用户信息
	 */
	public void save(Supervise supervise) {
		superviseDao.save(supervise);
	}

	/*
	 * 更新用户信息
	 */
	public void update(Supervise supervise) {
		superviseDao.update(supervise);
	}

	/*
	 * 通过对象删除用户信息
	 */
	public void deleteByObject(Supervise supervise) {
		superviseDao.deleteByObj(supervise);
	}

	/*
	 * 通过主键删除用户信息
	 */
	public void deleteById(String id) {

		superviseDao.delete(id);
	}

	/*
	 * 通过主键查询用户信息
	 */
	public Supervise getById(String id) {
		return superviseDao.get(id);
	}
	/**
	 * 多删
	 * @param id
	 */
	public void deletes(String id ) {

		String ids [] = id.split(",");
		
		superviseDao.deletes(ids);
    }


	/*
	 * 返回所有用户信息
	 */
	public List<Supervise> getAllUsers() {
		return superviseDao.findUsers();
	}

	/*
	 * 根据分页进行查询
	 */
	public List<Supervise> listByPage(int firstResult, int rows) {
		return superviseDao.listByPage(firstResult, rows, null);
	}

	public List<Supervise> listByPage(int firstResult, int rows, SuperviseModel queryModel) {
		return superviseDao.listByPage(firstResult, rows, queryModel);
	}
	
	public List<Supervise> findListByPageSearch(TeeDataGridModel tModel, SuperviseModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return superviseDao.findListByPageSearch(tModel.getFirstResult(), tModel.getRows(), cbModel, orgCtrl);
    }

	public long listSearchCount(SuperviseModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return superviseDao.listSearchCount(cbModel, orgCtrl);
    }
	
	public List<Supervise> findListByPage(TeeDataGridModel tModel, SuperviseModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return superviseDao.findListByPage(tModel.getFirstResult(), tModel.getRows(), cbModel, orgCtrl);
    }

	public long listCount(SuperviseModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return superviseDao.listCount(cbModel, orgCtrl);
    }
	
	public List<Supervise> listByDe(int firstResult, int rows, SuperviseModel queryModel,OrgCtrlInfoModel orgCtrl) {
		return superviseDao.listByDe(firstResult, rows, queryModel,orgCtrl);
	}
	
	public List<Supervise> listByDeAll(int firstResult, int rows, SuperviseModel queryModel) {
		return superviseDao.listByDeAll(firstResult, rows, queryModel);
	}
	
	public long getTotal() {
		return superviseDao.getTotal();
	}

	public long getTotal(SuperviseModel queryModel) {
		return superviseDao.getTotal(queryModel);
	}
	
	/**
    * 查看界面获取信息
    */
    public SuperviseModel getSupDeptById(String id){
        Supervise supervise = superviseDao.get(id);
        SuperviseModel superviseModel = new SuperviseModel();
        superviseModel = copyAllProperties(supervise);
        return superviseModel;
    }
    /**
     * 代码表中获取值
     */
    public SuperviseModel copyAllProperties(Supervise supervise){
    	SuperviseModel superviseModel = new SuperviseModel();
        BeanUtils.copyProperties(supervise, superviseModel);
        //代码表获取部门地区
        if(!TeeUtility.isNullorEmpty(supervise.getAdministrativeDivision())){
        	superviseModel.setAdministrativeDivision(TeeSysCodeManager.getChildSysCodeNameCodeNo("ADMINISTRAIVE_DIVISION", supervise.getAdministrativeDivision()));
        }
      //代码表获取部门层级
        if(!TeeUtility.isNullorEmpty(supervise.getDeptLevel())){
        	superviseModel.setDeptLevel(TeeSysCodeManager.getChildSysCodeNameCodeNo("DEPT_LEVEL", supervise.getDeptLevel()));
        }
        return superviseModel;
    }
    
    /**
     * 审核
     * @param id
     * @param orgCtrl
     * @return
     * @date:2019年3月15日下午3:35:25
     * @author:yxy
     */
    public TeeJson examine(String id,OrgCtrlInfoModel orgCtrl){
    	//获取当前登录人
    	TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
    	TeeJson json = new TeeJson();
    	//更新部门表审核状态
    	simpleDaoSupport.executeNativeUpdate("update tbl_base_supervise set is_examine = 1 , examine_time =? ,examine_person_id = ? where id = ?",
                new Object[] { new Date(),user.getUserId(),id });
    	//系统部门表插入数据
    	TeeDepartment parentDept = new TeeDepartment();
    	Supervise supervise = getById(id);
    	//获取上级政府关联系统部门 
    	Map<String, Object> deptUuid = superviseDao.GovUuid(supervise.getAdministrativeDivision());
		String deptUuids = deptUuid.toString();
		String parentDeptId = deptUuids.substring(4, deptUuids.length()-1);
		
		SysBusinessRelation parentRelation = sysBusinessRelationDao.getRelationByBussDeptId(parentDeptId);
        parentDept = parentRelation.getDeptRelation();
    	
    	
    		
        TeeDepartment dept = new TeeDepartment();
        dept.setGuid(TeePassEncryptMD5.getRandomGUID());
    	dept.setDeptName(supervise.getName());
        dept.setDeptFullName(parentDept.getDeptFullName() + "/" + dept.getDeptName());
        dept.setDeptParentLevel(parentDept.getDeptParentLevel() + parentDept.getGuid());
        dept.setDeptParent(parentDept);
        dept.setDeptNo(11);
        dept.setWeixinDeptId(0);
        dept.setDeptType(1);
    	String returnId = deptDao.save(dept).toString();
        dept.setDeptFullId(parentDept.getDeptFullId() + "/" + returnId);
        deptDao.saveOrUpdate(dept);
    	//监督业务管理表 插入数据
        SysBusinessRelation newRelation = new SysBusinessRelation();
        newRelation.setId(UUID.randomUUID().toString());
        newRelation.setDeptRelation(dept);
        newRelation.setSysOrgName(dept.getDeptName());
        newRelation.setBusinessSupDeptId(supervise.getId());
        newRelation.setBusinessSupDeptName(supervise.getName());
        newRelation.setOrgType("20");
        sysBusinessRelationDao.save(newRelation);
    	return json;
    }
    
    /**
     * 分配账号
     * @param superviseModel
     * @param orgCtrl
     * @return
     * @throws NoSuchAlgorithmException
     * @date:2019年3月19日下午2:33:30
     * @author:yxy
     */
	public TeeJson saveUser(SuperviseModel superviseModel,OrgCtrlInfoModel orgCtrl) throws NoSuchAlgorithmException{
		TeeJson json = new TeeJson();
		TeePerson person = new TeePerson();
		TeeDepartment department = new TeeDepartment();
		TeeUserRole userRole = new TeeUserRole();
		//获取当前登录人信息
		TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
		//账号
		person.setUserId(superviseModel.getUserName());
		//用户姓名
		person.setUserName(superviseModel.getName());
		//密码
		person.setPassword(TeePassEncryptMD5.cryptDynamic("zfjd123456"));
		//所属部门
		Map<String, Object> deptUuid = superviseDao.deptUuid(superviseModel.getId());
		String deptUuids = deptUuid.toString();
		Integer sysDeptId = Integer.parseInt(deptUuids.substring(4, deptUuids.length()-1));
		department = deptDao.get(sysDeptId);
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
		//关联表 department_user插入数据
		if(!TeeUtility.isNullorEmpty(superviseModel.getId())){
			DepartmentUser departmentUser = new DepartmentUser();
			departmentUser.setId(UUID.randomUUID().toString());
			departmentUser.setSuperviseId(superviseModel.getId());
			departmentUser.setIsDelete(0);
			departmentUser.setUserUuid(uuiddInteger);
			departmentUser.setCreatorTime(new Date());
			departmentUser.setCreatorId(user.getUserId());
			departmentUserDao.save(departmentUser);
		}
		//关联表PERSON_MENU_GROUP（用户-权限组）
		//查询监督部门的uuid
		Map<String, Object> deptId = superviseDao.deptId();
		String deptIdString  = deptId.toString();
		Integer deptIdInteger = Integer.parseInt(deptIdString.substring(4, deptIdString.length()-1));
		simpleDaoSupport.executeNativeUpdate("insert into PERSON_MENU_GROUP values (?,?)",
				new Object[]{uuiddInteger,deptIdInteger});
		//tbl_base_supervise表中插入用户密码
		try {
			simpleDaoSupport.executeNativeUpdate("update tbl_base_supervise set username =? , password =?  where id =?",
					new Object[]{superviseModel.getUserName(),TeePassEncryptMD5.cryptDynamic("zfjd123456"),superviseModel.getId()});
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return json;
	}
	/**
	 * 重置密码
	 * @param id
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @date:2019年3月19日下午2:33:08
	 * @author:yxy
	 */
	 public TeeJson resetPassword(String id) throws NoSuchAlgorithmException{
		TeeJson json = new TeeJson();
		//根据ID从关系表中获取数据
		Map<String, Object> uuid = superviseDao.getUuid(id);
		String a = uuid.toString();
		Integer b = Integer.parseInt(a.substring(4, a.length()-1));
		//默认新密码为"zfjd123456"
		String newPaw =TeePassEncryptMD5.cryptDynamic("zfjd123456");
		//person表中更新新密码
		simpleDaoSupport.executeNativeUpdate("update person set PWD =? where uuid = ?",
				new Object[]{newPaw,b});
		//tbl_base_supervise监督部门表中更新新密码
		simpleDaoSupport.executeNativeUpdate("update tbl_base_supervise set password =? where id =?",
				new Object[]{newPaw,id});
		return json;
	 }
	 
	 /**
	  * 回收账号
	  * @param id
	  * @return
	  * @date:2019年3月19日下午2:32:57
	  * @author:yxy
	  */
	 public TeeJson resetUser(String id){
			TeeJson json = new TeeJson();
			//根据ID从关系表中获取数据
			Map<String, Object> uuid = superviseDao.getUuid(id);
			String a = uuid.toString();
			Integer b = Integer.parseInt(a.substring(4, a.length()-1));
			//删除person表中用户账号信息
			simpleDaoSupport.executeNativeUpdate("delete from person where uuid = ?",
					new Object[]{b});
			//删除关系表
			Date date = new Date();
			simpleDaoSupport.executeNativeUpdate("update tbl_sys_department_user set is_delete = 1,delete_time =? where supervise_id = ?",
					new Object[]{date,id});
			//更新执法部门表信息
			simpleDaoSupport.executeNativeUpdate("update tbl_base_supervise set username = '' , password = ''  where id =?",
					new Object[]{id});
			return json;
		 }
	 /**
	  * 判断person用户名是否重复
	  * @param name
	  * @return
	  * @date:2019年3月21日上午10:55:53
	  * @author:yxy
	  */
	 public TeeJson doubleUser(String name){
			TeeJson json = new TeeJson();
			Integer a =simpleDaoSupport.executeNativeUpdate("select uuid from person where ? in user_id", new Object[]{name});
			json.setRtData(a);
			return json;
		 }
}
