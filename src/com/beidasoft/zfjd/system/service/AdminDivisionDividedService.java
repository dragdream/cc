package com.beidasoft.zfjd.system.service;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.department.dao.TblDepartmentDao;
import com.beidasoft.zfjd.department.model.TblDepartmentModel;
import com.beidasoft.zfjd.system.bean.AdminDivisionDivided;
import com.beidasoft.zfjd.system.bean.SysBusinessRelation;
import com.beidasoft.zfjd.system.bean.SysManagerUser;
import com.beidasoft.zfjd.system.dao.AdminDivisionDividedDao;
import com.beidasoft.zfjd.system.dao.SysBusinessRelationDao;
import com.beidasoft.zfjd.system.dao.SysManagerUserDao;
import com.beidasoft.zfjd.system.model.AdminDivisionDividedModel;
import com.tianee.oa.core.general.bean.TeeSysCode;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.general.dao.TeeSysCodeDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.sync.config.bean.TeeOutSystemConfig;
import com.tianee.oa.sync.log.bean.TeeOutSystemSyncLog;
import com.tianee.oa.sync.log.dao.TeeSyncLogDao;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.secure.TeePassEncryptMD5;
import com.tianee.webframe.util.servlet.HttpClientUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 行政区划管理信息SERVICE类
 */
@Service
public class AdminDivisionDividedService extends TeeBaseService {

    // 获取日志记录器Logger，名字为本类类名
    private static Logger logger = LogManager.getLogger(AdminDivisionDividedService.class);

    @Autowired
    private AdminDivisionDividedDao adminDivisionDividedDao;

    @Autowired
    private TblDepartmentDao tblDepartmentDao;

    @Autowired
    private SysBusinessRelationDao sysBusinessRelationDao;

    @Autowired
    private TeeDeptDao teeDeptDao;

    @Autowired
    private TeeUserRoleDao teeUserRoleDao;

    @Autowired
    private TeePersonDao teePersonDao;

    @Autowired
    private TeeSysCodeDao teeSysCodeDao;

    @Autowired
    private SysManagerUserDao sysManagerUserDao;

    @Autowired
    private TeeSyncLogDao logDao;

    /**
     * 分页获取符合查询条件的行政区划信息
     *
     * @param dm
     * @param adminDivisionDividedModel
     * @return adminDivisionList
     * @author hoax
     */
    public List<AdminDivisionDivided> findBaseListByPage(TeeDataGridModel dm,
            AdminDivisionDividedModel adminDivisionDividedModel) {
        return adminDivisionDividedDao.selectBaseListByPage(dm.getFirstResult(), dm.getRows(),
                adminDivisionDividedModel);
    }

    @SuppressWarnings("rawtypes")
	public List<Map> listAreaSearch(OrgCtrlInfoModel orgCtrl) {
        return adminDivisionDividedDao.listAreaSearch(orgCtrl);
    }
    
    @SuppressWarnings("rawtypes")
	public List<Map> AreaToLevel(String id) {
        return adminDivisionDividedDao.AreaToLevel(id);
    }
    /**
     * 获取符合查询条件的行政区划数量
     *
     * @param adminDivisionDividedModel
     * @return adminDivisionList
     * @author hoax
     */
    public long findBaseListCount(AdminDivisionDividedModel adminDivisionDividedModel) {
        return adminDivisionDividedDao.selectBaseListCount(adminDivisionDividedModel);
    }

    /**
     * 获取符合查询条件的行政区划数量
     *
     * @param adminDivisionCode
     * @return AdminDivisionDivided
     * @author hoax
     */
    public AdminDivisionDivided getByAdminCode(String adminDivisionCode) {
        return adminDivisionDividedDao.getByAdminCode(adminDivisionCode);
    }

    /**
     * 根据区划代码获取所有下去区划信息
     *
     * @param adminDivisionDividedModel
     * @return adminDivisionList
     * @author hoax
     */
    public List<AdminDivisionDivided> findChildAdminDivisionListByCode(
            AdminDivisionDividedModel adminDivisionDividedModel) {
        AdminDivisionDivided baseAdminDivision = adminDivisionDividedDao
                .getByAdminCode(adminDivisionDividedModel.getBaseAdminDivisionCode());
        adminDivisionDividedModel.setBaseLevelCode(baseAdminDivision.getLevelCode());
        return adminDivisionDividedDao.findChildAdminDivisionListByCode(adminDivisionDividedModel);
    }

    public TeeSysCode buildAdminDivisionCode(AdminDivisionDivided adminDivisionDivided) {
        // 保存：插入字典表，行政区划信息
        String hql = "from TeeSysCode where codeNo = ?";
        Object[] params = { "ADMINISTRAIVE_DIVISION" };
        List<TeeSysCode> parentCodes = teeSysCodeDao.executeQuery(hql, params);
        TeeSysCode newCode = new TeeSysCode();
        newCode.setParentId(parentCodes.get(0).getSid());
        newCode.setCodeName(adminDivisionDivided.getAdminDivisionName());
        newCode.setCodeNo(adminDivisionDivided.getAdminDivisionCode());
        newCode.setCodeOrder(9);
        // teeSysCodeDao.save(newCode);
        return newCode;
    }

    /**
     * 保存行政区划信息
     *
     * @param beanInfo
     */
    public Integer save(AdminDivisionDivided adminDivisionDivided) {
        try {
            // 保存行政区划信息
            adminDivisionDividedDao.saveOrUpdate(adminDivisionDivided);
            // 新增地方政府信息
            TblDepartmentInfo tblDepartmentInfo = new TblDepartmentInfo();
            tblDepartmentInfo.setId(UUID.randomUUID().toString());
            tblDepartmentInfo.setName(adminDivisionDivided.getFullName() + "人民政府");
            tblDepartmentInfo.setDeptLevel(adminDivisionDivided.getLevelCode());
            tblDepartmentInfo.setAdministrativeDivision(adminDivisionDivided.getAdminDivisionCode());
            tblDepartmentInfo.setCreateTime(new Date());
            tblDepartmentInfo.setIsDelete(0);
            tblDepartmentInfo.setIsExamine(1);
            tblDepartmentInfo.setIsRelease(0);
            tblDepartmentInfo.setIsStop(0);
            tblDepartmentInfo.setIsManubrium(0);
            tblDepartmentInfo.setOrgSys("01");
            tblDepartmentInfo.setSimpleName(adminDivisionDivided.getAdminDivisionName() + "政府");
            tblDepartmentInfo.setIsGovernment(1);
            // 保存：插入业务系统地方政府信息
            tblDepartmentDao.save(tblDepartmentInfo);
            // 获取上级政府信息
            TblDepartmentInfo parentGov = null;
            TblDepartmentModel queryModel = new TblDepartmentModel();
            switch (adminDivisionDivided.getLevelCode()) {
            case ("0200"): {
                queryModel.setAdministrativeDivision(adminDivisionDivided.getCountryCode());
                break;
            }
            case ("0300"): {
                queryModel.setAdministrativeDivision(adminDivisionDivided.getProvincialCode());
                break;
            }
            case ("0400"): {
                queryModel.setAdministrativeDivision(adminDivisionDivided.getCityCode());
                break;
            }
            case ("0500"): {
                queryModel.setAdministrativeDivision(adminDivisionDivided.getDistrictCode());
                break;
            }
            }
            List<TblDepartmentInfo> parentGovs = tblDepartmentDao.findOrderLvGovByAdminDivisionCode(queryModel);
            if (parentGovs != null && parentGovs.size() > 0) {
                parentGov = parentGovs.get(0);
            } else {
                System.out.println("上级人民政府不存在，获取关联失败！");
                return -1;
            }
            // 获取上级政府关联系统部门
            TeeDepartment parentDept = new TeeDepartment();
            if (parentGov != null) {
                SysBusinessRelation parentRelation = sysBusinessRelationDao.getRelationByBussDeptId(parentGov.getId());
                parentDept = parentRelation.getDeptRelation();
            }

            // 创建系统部门
            if (parentDept != null && parentDept.getGuid() != null) {
                TeeDepartment dept = new TeeDepartment();
                // 从数据库获取uuid
                // Map<String, Object> uuids =
                // adminDivisionDividedDao.getNextSysDeptUuid();
                // String uuidStr = uuids.toString();
                // Integer uuidNum = Integer.parseInt(uuidStr.substring(4,
                // uuidStr.length()-1));
                dept.setGuid(TeePassEncryptMD5.getRandomGUID());
                dept.setDeptName(tblDepartmentInfo.getSimpleName());
                dept.setDeptFullName(parentDept.getDeptFullName() + "/" + dept.getDeptName());
                dept.setDeptNo(11);
                dept.setDeptParentLevel(parentDept.getDeptParentLevel() + parentDept.getGuid());
                dept.setWeixinDeptId(0);
                dept.setDeptParent(parentDept);
                dept.setDeptType(1);
                // 保存：插入系统部门地方政府信息
                String returnId = teeDeptDao.save(dept).toString();
                dept.setDeptFullId(parentDept.getDeptFullId() + "/" + returnId);
                teeDeptDao.saveOrUpdate(dept);
                // 建立新的关联关系
                SysBusinessRelation newRelation = new SysBusinessRelation();
                newRelation.setId(UUID.randomUUID().toString());
                newRelation.setDeptRelation(dept);
                newRelation.setSysOrgName(dept.getDeptName());
                newRelation.setBusinessDeptId(tblDepartmentInfo.getId());
                newRelation.setBusinessDeptName(tblDepartmentInfo.getName());
                newRelation.setOrgType("10");
                // 保存：插入业务系统部门与系统管理部门关联关系
                sysBusinessRelationDao.save(newRelation);
            }
        } catch (Exception e) {
            System.out.println("法律法规条调整申请保存失败！");
            return -9;
        }
        return 0;
    }

    /**
     * 保存法律法规调整数据
     *
     * @param beanInfo
     */
    public void saveNewGovInfo(AdminDivisionDivided adminDivisionDivided) {
        try {

        } catch (Exception e) {
            System.out.println("发生错误异常，保存失败！");
        }
    }

    /**
     * 保存法律法规调整数据
     *
     * @param beanInfo
     */
    public Integer saveUserAcount(AdminDivisionDividedModel adminDivisionDividedModel) {
        try {
            // 获取对应区划人民政府信息
            TblDepartmentInfo selfGov = null;
            TblDepartmentModel queryModel = new TblDepartmentModel();
            queryModel.setAdministrativeDivision(adminDivisionDividedModel.getAdminDivisionCode());
            List<TblDepartmentInfo> resultGovs = tblDepartmentDao.findOrderLvGovByAdminDivisionCode(queryModel);
            if (resultGovs != null && resultGovs.size() > 0) {
                // 保障程序执行，如果查询结果为多条，默认选取第一条
                selfGov = resultGovs.get(0);
            } else {
                System.out.println("本地区人民政府不存在，获取关联失败！");
                // 运行发生错误标记，指代该行政区划下无综合管理政府机构信息
                return -1;
            }
            // 获取本人民政府关联的系统部门
            TeeDepartment selfSysDept = new TeeDepartment();
            if (selfGov != null) {
                SysBusinessRelation selfRelation = sysBusinessRelationDao.getRelationByBussDeptId(selfGov.getId());
                if (selfRelation != null) {
                    selfSysDept = selfRelation.getDeptRelation();
                } else {
                    // 运行发生错误标记，指代该行政区划下综合管理政府机构未与系统机构建立关联关系
                    System.out.println("本地区行政区划下综合管理政府机构未与系统机构建立关联关系，获取关联失败！");
                    return -2;
                }

            }
            //尝试获取相同用户名的用户
            TeePerson person = teePersonDao.getPersonByUserId(adminDivisionDividedModel.getUserAccount());
            if(person != null 
            		&& person.getUserId().equals(adminDivisionDividedModel.getUserAccount())){
            	//该账号已存在，中止创建
            	return -3;
            }
            // 拼组人员帐号信息，并进行保存
            person = new TeePerson();
            TeeUserRole userRole = new TeeUserRole();
            // 账号
            person.setUserId(adminDivisionDividedModel.getUserAccount());
            // 用户姓名
            person.setUserName(adminDivisionDividedModel.getUserName());
            // 密码
            person.setPassword(TeePassEncryptMD5.cryptDynamic("zfjd123456"));
            // 所属部门
            person.setDept(selfSysDept);
            // 精灵在线状况 0 - 不在线 1-在线
            person.setIspiritOnlineStatus(0);
            // 主开关在线状况 0 - 不在线 1-在线
            person.setMainOnlineStatus(0);
            // 禁止WEB登录
            person.setNotWebLogin("0");
            // 考勤签到方式
            person.setSignWay("1");
            // 角色编号2员工
            userRole = teeUserRoleDao.selectByUUId(2);
            person.setUserRole(userRole);
            //
            person.setUsePops("0");
            // 界面主题
            person.setTheme("classic");
            // 管理范围
            person.setPostPriv(-1);
            // 禁止查看用户列表
            person.setNotViewUser("0");
            // 禁止登录OA系统
            person.setNotLogin("0");
            // 手机号码是否公开
            person.setMobilNoHidden("0");
            // 内部邮箱容量
            person.setEmailCapacity(500);
            // 个人文件柜容量
            person.setFolderCapacity(500);
            // 考勤排班类型
            person.setDutyType("0");
            // 禁止移动端登录
            person.setNotMobileLogin("0");
            // 禁止PC端登录
            person.setNotPcLogin("0");
            // 删除状态
            person.setDeleteStatus("0");
            // 保存: 插入人员信息，并获取人员id
            String personId = teePersonDao.save(person).toString();
            // 保存：关联表PERSON_MENU_GROUP（用户-权限组）
            simpleDaoSupport.executeNativeUpdate("insert into PERSON_MENU_GROUP values (?,?)",
                    new Object[] { personId, 4009 });
            // 关联表 TBL_SYS_MANAGER_USER插入数据
            // 记录帐号历史
            SysManagerUser sysManagerUser = new SysManagerUser();
            sysManagerUser.setId(UUID.randomUUID().toString());
            sysManagerUser.setAdminDivisionCode(adminDivisionDividedModel.getAdminDivisionCode());
            // 地区分级管理员默认分配给人民政府
            sysManagerUser.setDeptId(selfGov.getId());
            // 地区人民政府部门性质为执法部门
            sysManagerUser.setOrgType(1);
            // 标记本帐号所属部门为人民政府
            sysManagerUser.setIsGovernment(1);
            // 标记帐号为分级管理员
            sysManagerUser.setAccountType(1);
            sysManagerUser.setUserUuid(Integer.parseInt(personId));
            sysManagerUser.setUserAccount(person.getUserId());
            // 初始化数据，默认未删除状态
            sysManagerUser.setIsDelete(0);
            sysManagerUser.setCreateDate(new Date());
            // 插入或更新帐号记录信息，通常为插入
            sysManagerUserDao.saveOrUpdate(sysManagerUser);
            if (adminDivisionDividedModel.getId() != null) {
                // 将分配的管分级管理员帐号更新至TBL_ADMIN_DIVISION_DIVIDED表,便于查询
                AdminDivisionDivided baseAdminDivision = adminDivisionDividedDao.get(adminDivisionDividedModel.getId());
                baseAdminDivision.setUserAccount(adminDivisionDividedModel.getUserAccount());
                adminDivisionDividedDao.update(baseAdminDivision);
            }
        } catch (Exception e) {
            System.out.println("发生错误异常，保存失败！");
            logger.info(e.getMessage());
            return -1;
        }
        return 0;
    }

    /**
     * 重置地区分级管理员帐号的密码
     *
     * @param adminDivisionDividedModel
     */
    public Integer reSetUserPassword(AdminDivisionDividedModel adminDivisionDividedModel) {
        try {
            AdminDivisionDivided adminDivisionDivided = null;
            if (adminDivisionDividedModel.getId() != null) {
                // 校验参数完整性
                // 根据数据id获取完整数据信息
                adminDivisionDivided = adminDivisionDividedDao.get(adminDivisionDividedModel.getId());
            } else {
                // 标记错误类型为1,未找到匹配的行政区划信息
                return 1;
            }
            if (adminDivisionDivided != null && adminDivisionDivided.getUserAccount() != null) {
                // 校验数据是否匹配获取
                // 校验该行政区划下是否已分配分级管理员帐号
                // 具备管理员帐号，则获取其管理员帐号完整信息
                TeePerson person = teePersonDao.getPersonByUserId(adminDivisionDivided.getUserAccount());
                if (person != null) {
                    // 重置用户密码为zfjd123456
                    person.setPassword(TeePassEncryptMD5.cryptDynamic("zfjd123456"));
                    teePersonDao.update(person);
                } else {
                    // 标记错误类型为1
                    return 3;
                }
            } else {
                // 标记错误类型为2,对应对区尚未分配分级管理员帐号
                return 2;
            }
        } catch (Exception e) {
            System.out.println("发生错误异常，保存失败！");
            logger.info(e.getMessage());
            return -9;
        }
        return 0;
    }

    /**
     * 回收分级管理员帐号
     *
     * @param adminDivisionDividedModel
     */
    public Integer releaseAccount(AdminDivisionDividedModel adminDivisionDividedModel, HttpServletRequest request) {
        try {
            AdminDivisionDivided adminDivisionDivided = null;
            if (adminDivisionDividedModel.getId() != null) {
                // 校验参数完整性
                // 根据数据id获取完整数据信息
                adminDivisionDivided = adminDivisionDividedDao.get(adminDivisionDividedModel.getId());
            } else {
                // 标记错误类型为1,未找到匹配的行政区划信息
                return 1;
            }
            if (adminDivisionDivided != null && adminDivisionDivided.getUserAccount() != null) {
                // 校验数据是否匹配获取
                // 校验该行政区划下是否已分配分级管理员帐号
                // 具备管理员帐号，则获取其管理员帐号完整信息
                TeePerson person = teePersonDao.getPersonByUserId(adminDivisionDivided.getUserAccount());
                if (person != null) {
                    // First table operation: TBL_ADMIN_DIVISION_DIVIDED
                    // 行政区划表，清除帐号信息
                    adminDivisionDivided.setUserAccount("");
                    adminDivisionDividedDao.update(adminDivisionDivided);
                    // Second table operation: TBL_SYS_MANAGER_USER
                    // 管理员帐号权限记录表，将该地区人民政府分级管理员记录标记为删除
                    String hql = "update SysManagerUser set (isDelete,deleteDate) = (1,?) where adminDivisionCode = ? and isGovernment = 1 and accountType = 1 and isDelete = 0 ";
                    Object[] params = { new Date(), adminDivisionDivided.getAdminDivisionCode() };
                    sysManagerUserDao.executeUpdate(hql, params);
                    // Third table operation: PERSON
                    // 用户账号更新为删除状态
                    person.setDeleteStatus("1");
                    teePersonDao.update(person);
                    TeeSysLog sysLog = TeeSysLog.newInstance();
                    // Other operations(参考框架用户管理-用户离职删除)
                    // 系统记录：人员删除操作
                    sysLog.setType("003C");
                    sysLog.setRemark("批量删除人员：【用户名：" + person.getUserId() + ";姓名：" + person.getUserName() + "】");
                    TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
                    // 同步离职人员
                    List<TeeOutSystemConfig> configList = simpleDaoSupport.executeQuery("from TeeOutSystemConfig", null);
                    if (configList != null && configList.size() > 0) {
                        for (TeeOutSystemConfig config : configList) {
                            // 拼接json
                            String param = "{\"uuids\":\"" + person.getUuid() + "\",\"userId\":\"" + person.getUserId()
                                    + "\",\"operation\":\"2\"}";
                            String respJson = HttpClientUtil
                                    .requestGet(config.getSystemUrl() + "?json=" + URLEncoder.encode(param, "UTF-8"));
                            if (!TeeUtility.isNullorEmpty(respJson)) {
                                boolean status = (boolean) JSONObject.fromObject(respJson).get("status");
                                if (!status) {
                                    // 同步失败 记录日志
                                    addLog("2", request, config, param, null);
                                }
                            }
                        }
                    }
                } else {
                    // 标记错误类型为1
                    return 3;
                }
            } else {
                // 标记错误类型为2,对应对区尚未分配分级管理员帐号
                return 2;
            }
        } catch (Exception e) {
            System.out.println("发生错误异常，保存失败！");
            logger.info(e.getMessage());
            return -9;
        }
        return 0;
    }

    /**
     * 同步失败 记录日志
     * 
     * @param operation
     * @param request
     * @param config
     * @param parm
     */
    private void addLog(String operation, HttpServletRequest request, TeeOutSystemConfig config, String param,
            TeePerson person) {
        TeeOutSystemSyncLog log = new TeeOutSystemSyncLog();
        TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        log.setRequestJson(param);
        if (person != null) {
            log.setUuid(person.getUuid() + "");
        }
        // 设置为未同步状态
        log.setSyncFlag("0");
        log.setOperation(operation);
        log.setConfigId(config.getSid());
        log.setSubmitUserId(loginUser.getUuid());
        log.setSubmitUserName(loginUser.getUserName());
        Calendar crTime = Calendar.getInstance();
        crTime.setTime(new Date());
        log.setCrTime(crTime);
        logDao.addLog(log);
    }
}
