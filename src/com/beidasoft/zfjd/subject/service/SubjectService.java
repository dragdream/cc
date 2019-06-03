package com.beidasoft.zfjd.subject.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.department.dao.TblDepartmentDao;
import com.beidasoft.zfjd.department.model.OrganizationSubjectModel;
import com.beidasoft.zfjd.department.service.OrganizationSubjectService;
import com.beidasoft.zfjd.subject.bean.Subject;
import com.beidasoft.zfjd.subject.bean.SubjectSubpower;
import com.beidasoft.zfjd.subject.dao.SubjectDao;
import com.beidasoft.zfjd.subject.dao.SubjectSubpowerDao;
import com.beidasoft.zfjd.subject.model.SubjectModel;
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

@Service("subjectService")
public class SubjectService extends TeeBaseService{

    @Autowired
    private SubjectDao subjectDao;
    @Autowired
	private OrganizationSubjectService organizationSubjectService;
    @Autowired
    private TblDepartmentDao departmentDao;
    @Autowired
    private SysBusinessRelationDao sysBusinessRelationDao;
    @Autowired
    private TeeDeptDao deptDao;
    @Autowired
	private TeeUserRoleDao userRoleDao;
    @Autowired
	private TeePersonDao personDao;
    @Autowired
	private DepartmentUserDao departmentUserDao;
    @Autowired
	private SubjectSubpowerDao subjectSubpowerDao;
    
    
    /**
     * 
    * @Function: SubjectService.java
    * @Description: 分页查询主体列表
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenq
    * @date: 2019年1月2日 下午8:28:19 
    *
     */
    public List<Subject> listByPage(TeeDataGridModel dm, SubjectModel subjectModel) {
        return subjectDao.listByPage(dm.getFirstResult(), dm.getRows(), subjectModel);
    }
    public List<Subject> listByPageOrg(TeeDataGridModel dm, SubjectModel subjectModel) {
        return subjectDao.listByPageOrg(dm.getFirstResult(), dm.getRows(), subjectModel);
    }
    
    public List<Subject> findListByPageSearch(TeeDataGridModel tModel, SubjectModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return subjectDao.findListByPageSearch(tModel.getFirstResult(), tModel.getRows(), cbModel, orgCtrl);
    }
    
	public long listSearchCount(SubjectModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return subjectDao.listSearchCount(cbModel, orgCtrl);
    }
	
	public List<Subject> findListByPageManOrg(TeeDataGridModel tModel, SubjectModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return subjectDao.findListByPageManOrg(tModel.getFirstResult(), tModel.getRows(), cbModel, orgCtrl);
    }

	public long listManOrgCount(SubjectModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return subjectDao.listManOrgCount(cbModel, orgCtrl);
    }
    
    //根据id返回name
    public Subject getNameById(String parentId) {
        return subjectDao.getNameById(parentId);
    }

    public List<Subject> listByDe(int firstResult, int rows, SubjectModel queryModel) {
		return subjectDao.listByDe(firstResult, rows, queryModel);
	}
    
    public List<Subject> listByOrgSub(int firstResult, int rows, SubjectModel queryModel,OrgCtrlInfoModel orgCtrl) {
		return subjectDao.listByOrgSub(firstResult, rows, queryModel,orgCtrl);
	}
    
    public List<Subject> listByDeSys(int firstResult, int rows, String id) {
		return subjectDao.listByDeSys(firstResult, rows, id);
	}
	
    public List<Subject> listByDe2(int firstResult, int rows, SubjectModel queryModel,OrgCtrlInfoModel orgCtrl) {
		return subjectDao.listByDe2(firstResult, rows, queryModel,orgCtrl);
	}
	
    public List<Subject> generalListByPageSubSearch(TeeDataGridModel tModel, SubjectModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return subjectDao.generalListByPageSubSearch(tModel.getFirstResult(), tModel.getRows(), cbModel, orgCtrl);
    }

	public long generallistSearchSubCount(SubjectModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return subjectDao.generallistSearchSubCount(cbModel, orgCtrl);
    }
    
    public List<Subject> listBySubject(int firstResult, int rows, SubjectModel queryModel) {
		return subjectDao.listBySubject(firstResult, rows, queryModel);

	}
    /**
     * 查询主体总数
    * @Function: SubjectService.java
    * @Description: 该函数的功能描述
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenq
    * @date: 2019年1月2日 下午8:28:42 
    *
     */
    public long listCount(SubjectModel subjectModel) {
        return subjectDao.listCount(subjectModel);
    }
    public long listCountOrg(SubjectModel subjectModel) {
        return subjectDao.listCountOrg(subjectModel);
    }
    /**
     * 
    * @Function: SubjectService.java
    * @Description: 通过主体ID查询主体
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenq
    * @date: 2019年1月8日 下午7:47:41 
    *
     */
    public List<Subject> getSubjectByIds(String ids) {
        return subjectDao.getSubjectByIds(ids);
    }
    
    /*
	 * 保存
	 */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public TeeJson save(SubjectModel subjectModel) {
		//获取当前登录人
		TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		//创建实例化实体类对象
		Subject subject = new Subject();
		subject.setId(UUID.randomUUID().toString());
		subjectModel.setId(subject.getId());
		BeanUtils.copyProperties(subjectModel, subject);
		//关联表插入数据
		if(!TeeUtility.isNullorEmpty(subjectModel.getDepartmentCode())){
			OrganizationSubjectModel organizationSubjectModel = new OrganizationSubjectModel();
			organizationSubjectModel.setId(UUID.randomUUID().toString());
			organizationSubjectModel.setOrganizationId(subjectModel.getDepartmentCode());
			organizationSubjectModel.setSubjectId(subjectModel.getId());
			organizationSubjectService.save(organizationSubjectModel);
		}
		//主体职权类型关系表
		if(subjectModel.getIsDepute() == 0 ){
			subjectModel.setSubjectPower(subjectModel.getSubjectPower().replace("\"", ""));
			subjectModel.setSubjectPower(subjectModel.getSubjectPower().replace("[", ""));
			subjectModel.setSubjectPower(subjectModel.getSubjectPower().replace("]", ""));
			String orgSysSplited [] = subjectModel.getSubjectPower().split(",");
			for(String code:orgSysSplited){
				Map codeMap = new HashMap();
				codeMap.put("id", code);
				codeMap.put("name", TeeSysCodeManager.getChildSysCodeNameCodeNo("POWER_TYPE", code));
				SubjectSubpower subjectSubpower = new SubjectSubpower();
				subjectSubpower.setId(UUID.randomUUID().toString());
				subjectSubpower.setSubjectId(subjectModel.getId());
				subjectSubpower.setSubjectPowerId(codeMap.get("id").toString());
				subjectSubpower.setSubjectPowerName(codeMap.get("name").toString());
				subjectSubpowerDao.save(subjectSubpower);
			}
		}
		subject.setCreateTime(new Date());
		subject.setCreateId(user.getUserId());;
		subjectDao.save(subject);
		json.setRtState(true);
		return json;
	}
	
    /*
	 * 更新
	 */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public TeeJson updateUser(SubjectModel subjectModel){
		//获取当前登录人
		TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		Subject subject = getById(subjectModel.getId());
		
		//处理创建日期审核日期删除日期
		if(!TeeUtility.isNullorEmpty(subject.getCreateTime())){
			subjectModel.setCreateTime(subject.getCreateTime());
		}
		if(!TeeUtility.isNullorEmpty(subject.getExamineTime())){
			subjectModel.setExamineTime(subject.getExamineTime());
		}
		if(!TeeUtility.isNullorEmpty(subject.getDisExamineTime())){
			subjectModel.setDisExamineTime(subject.getDisExamineTime());
		}
		if(!TeeUtility.isNullorEmpty(subject.getDeleteTime())){
			subjectModel.setDeleteTime(subject.getDeleteTime());
		}
		if(!TeeUtility.isNullorEmpty(subject.getCreateId())){
			subjectModel.setCreateId(subject.getCreateId());
		}
		
		BeanUtils.copyProperties(subjectModel, subject);
		//关联表更新数据 先删后插
		OrganizationSubjectModel organizationSubjectModel = new OrganizationSubjectModel();
		organizationSubjectModel.setId(UUID.randomUUID().toString());
		organizationSubjectModel.setOrganizationId(subjectModel.getDepartmentCode());
		organizationSubjectModel.setSubjectId(subjectModel.getId());
		simpleDaoSupport.executeNativeUpdate("delete from tbl_base_organization_subject where subject_id = ?", new Object[]{subjectModel.getId()});
		organizationSubjectService.save(organizationSubjectModel);

		//主体职权类型关系表
		subjectModel.setSubjectPower(subjectModel.getSubjectPower().replace("\"", ""));
		subjectModel.setSubjectPower(subjectModel.getSubjectPower().replace("[", ""));
		subjectModel.setSubjectPower(subjectModel.getSubjectPower().replace("]", ""));
		String orgSysSplited [] = subjectModel.getSubjectPower().split(",");
		simpleDaoSupport.executeNativeUpdate("delete from jd_base_subject_subpower where subject_id = ?", new Object[]{subjectModel.getId()});
		for(String code:orgSysSplited){
			Map codeMap = new HashMap();
			codeMap.put("id", code);
			codeMap.put("name", TeeSysCodeManager.getChildSysCodeNameCodeNo("POWER_TYPE", code));
			SubjectSubpower subjectSubpower = new SubjectSubpower();
			subjectSubpower.setId(UUID.randomUUID().toString());
			subjectSubpower.setSubjectId(subjectModel.getId());
			subjectSubpower.setSubjectPowerId(codeMap.get("id").toString());
			subjectSubpower.setSubjectPowerName(codeMap.get("name").toString());
			subjectSubpowerDao.save(subjectSubpower);
		}
		
		
		subject.setCreateTime(subjectModel.getCreateTime());
		subject.setExamineTime(subjectModel.getExamineTime());
		subject.setDisExamineTime(subjectModel.getDisExamineTime());
		subject.setDeleteTime(subjectModel.getDeleteTime());
		subject.setCreateId(subjectModel.getCreateId());
		subject.setUpdateTime(new Date());
		subject.setUpdateId(user.getUserId());
		
		subjectDao.update(subject);
		json.setRtState(true);
		return json;

    }
    
	/*
	 * 更新状态
	 */
	public void update(Subject subject){
		subjectDao.update(subject);
	}
	/*
	 * 通过对象删除
	 */
	public void deleteByObject(Subject subject){
		subjectDao.deleteByObj(subject);
	}
	/*
	 * 通过主键删除
	 */
	public void deleteById(String id){
		subjectDao.delete(id);
	}
	/*
	 * 通过主键查询
	 */
	public Subject getById(String id){
		return subjectDao.get(id);
	}
	/*
	 * 地区查层级
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> areaToLevel(int firstResult, int rows,String id){
		return subjectDao.areaToLevel(firstResult,rows,id);
	}
	/**
	 * 多删
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	

	public void deletes(String id ) {

		String ids [] = id.split(",");
		
		subjectDao.deletes(ids);
    }

	/*
	 * 根据分页进行查询
	 */
	public List<Subject> listByPage(int firstResult,int rows){
		return subjectDao.listByPage( firstResult, rows, null);
	  }
	  
	  public long getTotal(){
		  return subjectDao.getTotal();
	  }
   
	  public long getTotal(SubjectModel subjectModel){
		  return subjectDao.listCount(subjectModel);
	  }
	/**
	 * @param p
	 * @return
	 */
//	public List<Subject> getSysCodeTemp(String p) {
//		  return subjectDao.getSysCodeTemp(p);
//	}
    /**
     * 
    * @Function: subjectList()
    * @Description: 条件查询执法主体
    *
    * @param: subjectModel 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年2月15日 下午4:58:28 
    *
     */
    public List<Subject> subjectList(SubjectModel subjectModel) {
        return subjectDao.subjectList(subjectModel);
    }
    
    /**
     * 
     * @Function: findListByPageOrg
     * @Description: 受委托组织查询-分页查询
     *
     * @param dataGridModel 分页条件
     * @param subjectModel 查询条件
     * @return
     *
     * @author: mixue
     * @date: 2019年2月20日 上午11:33:18
     */
    public List<SubjectModel> findListByPageOrg(TeeDataGridModel dataGridModel, SubjectModel subjectModel) {
        // TODO Auto-generated method stub
        List<SubjectModel> subjectModels = new ArrayList<>();
        List<Subject> subjects = subjectDao.findListByPageOrg(dataGridModel.getFirstResult(), dataGridModel.getRows(), subjectModel);
        for (Subject subject : subjects) {
            SubjectModel sModel = copyAllProperties(subject);
            subjectModels.add(sModel);
        }
        return subjectModels;
    }
    
    /**
     * 
     * @Function: findListCountByPageOrg
     * @Description: 查询受委托组织数量
     *
     * @param subjectModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月20日 下午4:38:24
     */
    public long findListCountByPageOrg(SubjectModel subjectModel){
        return subjectDao.findListCountByPageOrg(subjectModel);
    }
    
    /**
     * 
     * @Function: getSubjectById
     * @Description: 获取单个执法主体信息
     *
     * @param id 主体ID
     * @return
     *
     * @author: mixue
     * @date: 2019年2月19日 下午7:51:41
     */
    public SubjectModel getSubjectById(String id){
        Subject subject = subjectDao.get(id);
        SubjectModel subjectModel = new SubjectModel();
        subjectModel = copyAllProperties(subject);
        return subjectModel;
    }
    
    /**
     * 
     * @Function: copyAllProperties
     * @Description: 主体信息处理
     *
     * @param subject
     * @return
     *
     * @author: mixue
     * @date: 2019年2月20日 上午11:45:19
     */
    public SubjectModel copyAllProperties(Subject subject){
        SubjectModel subjectModel = new SubjectModel();
        BeanUtils.copyProperties(subject, subjectModel);
        // 从代码表获取主体性质
        if(subject.getNature() != null){
            subjectModel.setNature(TeeSysCodeManager.getChildSysCodeNameCodeNo("SYSTEM_SUBJECT_NATURE", subject.getNature()));
        }
        // 从代码表获取委托组织性质
        if(subject.getEntrustNature() != null){
            subjectModel.setEntrustNature(TeeSysCodeManager.getChildSysCodeNameCodeNo("SYSTEM_SUBJECT_NATURE", subject.getEntrustNature()));
        }
        //创建人
		if(!TeeUtility.isNullorEmpty(subject.getCreateId())){
			Map<String, Object> nameMap = subjectDao.getuserName(subject.getCreateId());
			if(nameMap != null){
			    String a = nameMap.toString();
			    subjectModel.setCreateId(a.substring(6, a.length()-1));
			}else{
			    subjectModel.setCreateId("");
			}
		}
		//创建时间
		if(!TeeUtility.isNullorEmpty(subject.getCreateTime())){
			String creString = subject.getCreateTime().toString();
			String timeString = creString.substring(0, 10);
			subjectModel.setCreateTimeStr(timeString);
		}
        
        // 从代码表获取主体层级
        if(subject.getSubLevel() != null){
            subjectModel.setSubLevel(TeeSysCodeManager.getChildSysCodeNameCodeNo("DEPT_LEVEL", subject.getSubLevel()));
        }
        // 从代码表获取所属地区
        if(subject.getArea() != null){
            subjectModel.setArea(TeeSysCodeManager.getChildSysCodeNameCodeNo("ADMINISTRAIVE_DIVISION", subject.getArea()));
        }
        // 获取执法主体所属部门
        if(subject.getDepartmentCode() != null){
            TblDepartmentInfo departmentInfo = departmentDao.get(subject.getDepartmentCode());
            subjectModel.setDepartmentCode("");
            if(departmentInfo != null)
                subjectModel.setDepartmentCode(departmentInfo.getName());
        }
        // 获取执法主体（受委托组织）上级主体
        if(subject.getParentId() != null){
            Subject subjectInfo = subjectDao.get(subject.getParentId());
            subjectModel.setParentId("");
            if(subjectInfo != null)
                subjectModel.setParentName(subjectInfo.getSubName());
        }
        // 从代码表获取职权类别
        subjectModel.setSubjectPower("");
        if(subject.getSubjectPower() != null && subject.getSubjectPower().length() > 2){
            List<Map<String, Object>> subjectPowerCode = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_TYPE");
            String[] subs = subject.getSubjectPower().substring(2, subject.getSubjectPower().length() - 2).split("\",\"");
            StringBuilder subjectPowers = new StringBuilder("");
            for(Map<String, Object> code : subjectPowerCode) {
                for (int i = 0; i < subs.length; i++) {
                    if(subs[i].equals(code.get("codeNo").toString())) {
                        subjectPowers.append(code.get("codeName").toString() + "，");
                        break;
                    }
                }
            }
            if(subjectPowers.length() > 0){
                subjectModel.setSubjectPower(subjectPowers.toString().substring(0, subjectPowers.length() - 1));
            }
        }
        // 从代码表获取执法系统
        if(subject.getOrgSys() != null){
            List<Map<String, Object>> orgSysCode = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("LAW_ENFORCEMENT_FIELD");
            subjectModel.setOrgSys("");
            String[] orgs = subject.getOrgSys().split(",");
            StringBuilder orgSys = new StringBuilder("");
            for(Map<String, Object> code : orgSysCode) {
                for (int i = 0; i < orgs.length; i++) {
                    if(orgs[i].equals(code.get("codeNo").toString())) {
                        orgSys.append(code.get("codeName").toString() + "，");
                        break;
                    }
                }
            }
            if(orgSys.length() > 0){
                subjectModel.setOrgSys(orgSys.toString().substring(0, orgSys.length() - 1));
            }
        }
        return subjectModel;
    }
    public List<SubjectModel> findListByPageRoles(TeeDataGridModel dataGridModel, SubjectModel subjectModel,
            OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        List<SubjectModel> subjectModels = new ArrayList<>();
        List<Subject> subjects = subjectDao.findListByPageOrg(dataGridModel.getFirstResult(), dataGridModel.getRows(), subjectModel, orgCtrlInfoModel);
        for (Subject subject : subjects) {
            SubjectModel sModel = copyAllProperties(subject);
            subjectModels.add(sModel);
        }
        return subjectModels;
    }
    public Long findListCountByPageRoles(SubjectModel subjectModel, OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        return subjectDao.findListCountByPageOrg(subjectModel, orgCtrlInfoModel);
    }
    
    /**
     * 审核
     * @param id
     * @param orgCtrl
     * @return
     * @date:2019年3月15日上午10:38:56
     * @author:yxy
     */
    public TeeJson examine(String id,OrgCtrlInfoModel orgCtrl){
    	//获取当前登录人
    	TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
    	TeeJson json = new TeeJson();
    	//更新部门表审核状态
    	simpleDaoSupport.executeNativeUpdate("update tbl_base_subject set examine = 1, examine_time =?, examine_id =? where id = ?",
                new Object[] { new Date(),user.getUserId(),id });
    	//系统部门表插入数据
    	TeeDepartment parentDept = new TeeDepartment();
    	Subject subject = getById(id);
    	//获取上级政府关联系统部门
    	if(!TeeUtility.isNullorEmpty(subject.getDepartmentCode())){
    		SysBusinessRelation parentRelation = sysBusinessRelationDao.getRelationByBussDeptId(subject.getDepartmentCode());
            parentDept = parentRelation.getDeptRelation();
    	}
        TeeDepartment dept = new TeeDepartment();
        dept.setGuid(TeePassEncryptMD5.getRandomGUID());
    	dept.setDeptName(subject.getSubName());
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
        newRelation.setBusinessDeptId(subject.getDepartmentCode());
        //获取所属部门名称
        Map<String, Object> deptUuid = subjectDao.deptName(subject.getDepartmentCode());
		String deptUuids = deptUuid.toString();
		String SubDeptName = deptUuids.substring(4, deptUuids.length()-1);
        newRelation.setBusinessDeptName(SubDeptName);
        newRelation.setBusinessSubjectId(subject.getId());
        newRelation.setBusinessSubjectName(subject.getSubName());
        newRelation.setOrgType("10");
        sysBusinessRelationDao.save(newRelation);
    	return json;
    }
    
    /**
     * 账号分配
     * @param subjectModel
     * @param orgCtrl
     * @return
     * @throws NoSuchAlgorithmException
     * @date:2019年3月19日上午9:02:01
     * @author:yxy
     */
	public TeeJson saveUser(SubjectModel subjectModel,OrgCtrlInfoModel orgCtrl) throws NoSuchAlgorithmException{
		TeeJson json = new TeeJson();
		TeePerson person = new TeePerson();
		TeeDepartment department = new TeeDepartment();
		TeeUserRole userRole = new TeeUserRole();
		//获取当前登录人信息
		TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
		//账号
		person.setUserId(subjectModel.getUserName());
		//用户姓名
		person.setUserName(subjectModel.getSubName());
		//密码
		person.setPassword(TeePassEncryptMD5.cryptDynamic("zfjd123456"));
		//所属部门
		Map<String, Object> deptUuid = subjectDao.deptUuid(subjectModel.getId());
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
		if(!TeeUtility.isNullorEmpty(subjectModel.getId())){
			DepartmentUser departmentUser = new DepartmentUser();
			departmentUser.setId(UUID.randomUUID().toString());
			departmentUser.setSubjectId(subjectModel.getId());
			departmentUser.setIsDelete(0);
			departmentUser.setUserUuid(uuiddInteger);
			departmentUser.setCreatorTime(new Date());
			departmentUser.setCreatorId(user.getUserId());
			departmentUserDao.save(departmentUser);
		}
		//关联表PERSON_MENU_GROUP（用户-权限组）
		//查询执法主体的uuid
		Map<String, Object> deptId = subjectDao.subjectId();
		String deptIdString  = deptId.toString();
		Integer deptIdInteger = Integer.parseInt(deptIdString.substring(4, deptIdString.length()-1));
		simpleDaoSupport.executeNativeUpdate("insert into PERSON_MENU_GROUP values (?,?)",
				new Object[]{uuiddInteger,deptIdInteger});
		//tbl_base_subject表中插入用户密码
		try {
			simpleDaoSupport.executeNativeUpdate("update tbl_base_subject set username =? , password =?  where id =?",
					new Object[]{subjectModel.getUserName(),TeePassEncryptMD5.cryptDynamic("zfjd123456"),subjectModel.getId()});
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return json;
	}
	/**
	 * 密码重置
	 * @param id
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @date:2019年3月19日上午9:01:32
	 * @author:yxy
	 */
	 public TeeJson resetPassword(String id) throws NoSuchAlgorithmException{
		TeeJson json = new TeeJson();
		//根据ID从关系表中获取数据
		Map<String, Object> uuid = subjectDao.getUuid(id);
		String a = uuid.toString();
		Integer b = Integer.parseInt(a.substring(4, a.length()-1));
		//默认新密码为"zfjd123456"
		String newPaw =TeePassEncryptMD5.cryptDynamic("zfjd123456");
		//person表中更新新密码
		simpleDaoSupport.executeNativeUpdate("update person set PWD =? where uuid = ?",
				new Object[]{newPaw,b});
		//tbl_base_subject执法主体表中更新新密码
		simpleDaoSupport.executeNativeUpdate("update tbl_base_subject set password =? where id =?",
				new Object[]{newPaw,id});
		return json;
	 }
	 
	 /**
	  * 回收账号
	  * @param id
	  * @return
	  * @date:2019年3月19日上午9:01:21
	  * @author:yxy
	  */
	 public TeeJson resetUser(String id){
			TeeJson json = new TeeJson();
			//根据ID从关系表中获取数据
			Map<String, Object> uuid = subjectDao.getUuid(id);
			String a = uuid.toString();
			Integer b = Integer.parseInt(a.substring(4, a.length()-1));
			//删除person表中用户账号信息
			simpleDaoSupport.executeNativeUpdate("delete from person where uuid = ?",
					new Object[]{b});
			//删除关系表
			Date date = new Date();
			simpleDaoSupport.executeNativeUpdate("update tbl_sys_department_user set is_delete = 1,delete_time =? where subject_id = ?",
					new Object[]{date,id});
			//更新执法主体表信息
			simpleDaoSupport.executeNativeUpdate("update tbl_base_subject set username = '' , password = ''  where id =?",
					new Object[]{id});
			return json;
		 }

    public List<SubjectModel> getSubjectRoles(OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        List<SubjectModel> subjectModels = new ArrayList<>();
        List<Subject> subjects = subjectDao.getSubjectRoles(orgCtrlInfoModel);
        for (Subject department : subjects) {
            SubjectModel sModel = copyAllProperties(department);
            subjectModels.add(sModel);
        }
        return subjectModels;
    }
}
