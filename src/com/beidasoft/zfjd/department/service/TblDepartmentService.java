package com.beidasoft.zfjd.department.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.department.bean.OrganizationOrgsys;
import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.department.dao.OrganizationOrgsysDao;
import com.beidasoft.zfjd.department.dao.TblDepartmentDao;
import com.beidasoft.zfjd.department.model.TblDepartmentModel;
import com.beidasoft.zfjd.supervise.bean.Supervise;
import com.beidasoft.zfjd.supervise.dao.SuperviseDao;
import com.beidasoft.zfjd.supervise.model.SupOrganizationModel;
import com.beidasoft.zfjd.supervise.model.SuperviseModel;
import com.beidasoft.zfjd.supervise.service.SupOrganizationService;
import com.beidasoft.zfjd.system.bean.DepartmentUser;
import com.beidasoft.zfjd.system.bean.StatisticDept;
import com.beidasoft.zfjd.system.bean.SysBusinessRelation;
import com.beidasoft.zfjd.system.dao.DepartmentUserDao;
import com.beidasoft.zfjd.system.dao.SysBusinessRelationDao;
import com.beidasoft.zfjd.system.service.StatisticDeptService;
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

@Service
public class TblDepartmentService extends TeeBaseService {

    @Autowired
    private TblDepartmentDao departmentDao;
    @Autowired
    private SupOrganizationService supOrganizationService;
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
	@Autowired
	private OrganizationOrgsysDao organizationOrgsysDao;
	@Autowired
	private StatisticDeptService statisticDeptService;
    /*
     * 保存
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public TeeJson save(TblDepartmentModel departmentModel) {
        TeeJson json = new TeeJson();
        // 获取当前登录人
        TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
        // 创建实例化实体类对象
        TblDepartmentInfo department = new TblDepartmentInfo();
        department.setId(UUID.randomUUID().toString());
        departmentModel.setId(department.getId());
        BeanUtils.copyProperties(departmentModel, department);
        // 执法机关和监督部门关联表插入数据
        if (!TeeUtility.isNullorEmpty(departmentModel.getSuperviceDepartmentId())) {
            SupOrganizationModel supOrganizationModel = new SupOrganizationModel();
            supOrganizationModel.setId(UUID.randomUUID().toString());
            supOrganizationModel.setOrganizationId(departmentModel.getId());
            supOrganizationModel.setSuperviseId(departmentModel.getSuperviceDepartmentId());
            ;
            supOrganizationService.save(supOrganizationModel);
        }
        //执法机关和所属领域关联表插入输入数据
        if(!TeeUtility.isNullorEmpty(departmentModel.getOrgSys())){
        	String orgSysSplited [] = departmentModel.getOrgSys().split(",");
			for(String code:orgSysSplited){
				Map codeMap = new HashMap();
				codeMap.put("id", code);
				codeMap.put("name", TeeSysCodeManager.getChildSysCodeNameCodeNo("LAW_ENFORCEMENT_FIELD", code));
				OrganizationOrgsys organizationOrgsys = new OrganizationOrgsys();
	        	organizationOrgsys.setId(UUID.randomUUID().toString());
	        	organizationOrgsys.setDeptId(departmentModel.getId());
	        	organizationOrgsys.setFieldId(codeMap.get("id").toString());
	        	organizationOrgsys.setFieldName(codeMap.get("name").toString());
	        	organizationOrgsysDao.save(organizationOrgsys);
			}
        }
        department.setCreateTime(new Date());
        department.setUpdateTime(new Date());
        department.setCreatorId(user.getUserId());
        department.setCreatorName(user.getUserName());
        departmentDao.save(department);
        json.setRtState(true);
        return json;
    }

    /*
     * 更新
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public TeeJson updateUser(TblDepartmentModel departmentModel) {
        // 获取当前登录人
        TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);

        TeeJson json = new TeeJson();

        TblDepartmentInfo departmentInfo = getById(departmentModel.getId());
        // 处理创建日期
        if (!TeeUtility.isNullorEmpty(departmentInfo.getCreateTime())) {
            departmentModel.setCreateTime(departmentInfo.getCreateTime());
        }
        // 处理审核日期
        if (!TeeUtility.isNullorEmpty(departmentInfo.getExamineTime())) {
            departmentModel.setExamineTime(departmentInfo.getExamineTime());
        }
        if (!TeeUtility.isNullorEmpty(departmentInfo.getDisExamineTime())) {
            departmentModel.setDisExamineTime(departmentInfo.getDisExamineTime());
        }
        // 处理删除日期
        if (!TeeUtility.isNullorEmpty(departmentInfo.getDeleteTime())) {
            departmentModel.setDeleteTime(departmentInfo.getDeleteTime());
        }
        // 处理创建人
        if (!TeeUtility.isNullorEmpty(departmentInfo.getCreatorId())) {
            departmentModel.setCreatorId(departmentInfo.getCreatorId());
        }
        if (!TeeUtility.isNullorEmpty(departmentInfo.getCreatorName())) {
            departmentModel.setCreatorName(departmentInfo.getCreatorName());
        }
        BeanUtils.copyProperties(departmentModel, departmentInfo);

        // 关联表插入数据
        SupOrganizationModel supOrganizationModel = new SupOrganizationModel();
        supOrganizationModel.setId(UUID.randomUUID().toString());
        supOrganizationModel.setOrganizationId(departmentModel.getId());
        supOrganizationModel.setSuperviseId(departmentModel.getSuperviceDepartmentId());
        simpleDaoSupport.executeNativeUpdate("delete from tbl_base_sup_organization where organization_id = ?",
                new Object[] { departmentModel.getId() });
        supOrganizationService.save(supOrganizationModel);
        
        //执法机关和所属领域关联表插入输入数据
        if(!TeeUtility.isNullorEmpty(departmentModel.getOrgSys())){
        	String orgSysSplited [] = departmentModel.getOrgSys().split(",");
        	simpleDaoSupport.executeNativeUpdate("delete from tbl_base_organization_orgsys where dept_id = ?",
                    new Object[] { departmentModel.getId() });
			for(String code:orgSysSplited){
				Map codeMap = new HashMap();
				codeMap.put("id", code);
				codeMap.put("name", TeeSysCodeManager.getChildSysCodeNameCodeNo("LAW_ENFORCEMENT_FIELD", code));
				OrganizationOrgsys organizationOrgsys = new OrganizationOrgsys();
	        	organizationOrgsys.setId(UUID.randomUUID().toString());
	        	organizationOrgsys.setDeptId(departmentModel.getId());
	        	organizationOrgsys.setFieldId(codeMap.get("id").toString());
	        	organizationOrgsys.setFieldName(codeMap.get("name").toString());
	        	organizationOrgsysDao.save(organizationOrgsys);
			}
        }
        
        // 日期处理
        departmentInfo.setCreateTime(departmentModel.getCreateTime());
        departmentInfo.setExamineTime(departmentModel.getExamineTime());
        departmentInfo.setDisExamineTime(departmentModel.getDisExamineTime());
        departmentInfo.setDeleteTime(departmentModel.getDeleteTime());
        departmentInfo.setCreatorId(departmentModel.getCreatorId());
        departmentInfo.setCreatorName(departmentModel.getCreatorName());

        departmentInfo.setUpdateTime(new Date());
        departmentInfo.setUpdatePersonId(user.getUserId());
        departmentDao.update(departmentInfo);
        json.setRtState(true);

        return json;
    }
    
    /**
     * 审核
     * @param id
     * @param orgCtrl
     * @return
     * @date:2019年3月14日下午4:24:05
     * @author:yxy
     */
    public TeeJson addDepartment(String id,OrgCtrlInfoModel orgCtrl){
    	//获取当前登录人
    	TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
    	TeeJson json = new TeeJson();
    	//更新部门表审核状态
    	simpleDaoSupport.executeNativeUpdate("update tbl_base_organization set is_examine = 1 , examine_time =? ,examine_person_id = ? where id = ?",
                new Object[] { new Date(),user.getUserId(),id });
    	//系统部门表插入数据
    	TeeDepartment parentDept = new TeeDepartment();
    	TblDepartmentInfo departmentInfo = getById(id);
    	//获取上级政府关联系统部门 有垂管部门则为垂管部门，无则为归属政府
    	if(departmentInfo.getIsManubrium()==1){
    		SysBusinessRelation parentRelation = sysBusinessRelationDao.getRelationByBussDeptId(departmentInfo.getDroopId());
            parentDept = parentRelation.getDeptRelation();
    	}else{
    		SysBusinessRelation parentRelation = sysBusinessRelationDao.getRelationByBussDeptId(departmentInfo.getParentId());
            parentDept = parentRelation.getDeptRelation();
    	}
    		
        TeeDepartment dept = new TeeDepartment();
        dept.setGuid(TeePassEncryptMD5.getRandomGUID());
    	dept.setDeptName(departmentInfo.getName());
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
        newRelation.setBusinessDeptId(departmentInfo.getId());
        newRelation.setBusinessDeptName(departmentInfo.getName());
        newRelation.setOrgType("10");
        sysBusinessRelationDao.save(newRelation);
    	return json;
    }
    
    /*
     * 更新状态
     */
    public void update(TblDepartmentInfo department) {
        departmentDao.update(department);
    }

    /*
     * 通过对象删除
     */
    public void deleteByObject(TblDepartmentInfo department) {
        departmentDao.deleteByObj(department);
    }

    /*
     * 通过主键删除
     */
    public void deleteById(String id) {

        departmentDao.delete(id);
    }

    public List<TblDepartmentInfo> findListByPageSearch(TeeDataGridModel tModel, TblDepartmentModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return departmentDao.findListByPageSearch(tModel.getFirstResult(), tModel.getRows(), cbModel, orgCtrl);
    }

	public long listSearchCount(TblDepartmentModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return departmentDao.listSearchCount(cbModel, orgCtrl);
    }
    
    public List<Supervise> findListByPageSearch(TeeDataGridModel tModel, SuperviseModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return superviseDao.findListByPageSearch(tModel.getFirstResult(), tModel.getRows(), cbModel, orgCtrl);
    }

	public long listSearchCount(SuperviseModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return superviseDao.listSearchCount(cbModel, orgCtrl);
    }
    
    /*
     * 通过主键查询
     */
    public TblDepartmentInfo getById(String id) {
        return departmentDao.get(id);
    }

    /**
     * 多删
     * 
     * @param id
     */
    public void deletes(String id) {

        String ids[] = id.split(",");

        departmentDao.deletes(ids);
    }

    /*
     * 返回所有用户信息
     */
    public List<TblDepartmentInfo> getAllUsers() {
        return departmentDao.findUsers();
    }
    /*
     * 根据分页进行查询
     */
    public List<TblDepartmentInfo> listByPage(int firstResult, int rows) {
        return departmentDao.listByPage(firstResult, rows, null);
    }

    public List<TblDepartmentInfo> listByPage(int firstResult, int rows, TblDepartmentModel queryModel) {
        return departmentDao.listByPage(firstResult, rows, queryModel);
    }

    public List<TblDepartmentInfo> generalListByPageSearch(TeeDataGridModel tModel, TblDepartmentModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return departmentDao.generalListByPageSearch(tModel.getFirstResult(), tModel.getRows(), cbModel, orgCtrl);
    }

    public List<TblDepartmentInfo> generalListByPage(TblDepartmentModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return departmentDao.generalListByPage(cbModel, orgCtrl);
    }
    
	public long generallistSearchCount(TblDepartmentModel cbModel, OrgCtrlInfoModel orgCtrl) {
        return departmentDao.generallistSearchCount(cbModel, orgCtrl);
    }
    
    public List<TblDepartmentInfo> listByDeQ(int firstResult, int rows, TblDepartmentModel queryModel,OrgCtrlInfoModel orgCtrl) {
        return departmentDao.listByDeQ(firstResult, rows, queryModel,orgCtrl);
    }
    
    public List<TblDepartmentInfo> listByDe(int firstResult, int rows, TblDepartmentModel queryModel) {
        return departmentDao.listByDe(firstResult, rows, queryModel);
    }
    
    public List<TblDepartmentInfo> listByGov(int firstResult, int rows, TblDepartmentModel queryModel,OrgCtrlInfoModel orgCtrl) {
        return departmentDao.listByGov(firstResult, rows, queryModel,orgCtrl);
    }

    @SuppressWarnings("rawtypes")
    public List<Map> listByPI(int firstResult, int rows, String parentId) {
        return departmentDao.listByPI(firstResult, rows, parentId);
    }

    @SuppressWarnings("rawtypes")
    public List<Map> listByOrg(int firstResult, int rows, String id) {
        return departmentDao.listByOrg(firstResult, rows, id);
    }

    @SuppressWarnings("rawtypes")
    public List<Map> listBySysCode(int firstResult, int rows, String q) {
        return departmentDao.listBySysCode(firstResult, rows, q);
    }

    @SuppressWarnings("rawtypes")
    public List<Map> listBySysCode2(int firstResult, int rows, String q, String id,OrgCtrlInfoModel orgCtrl) {
        return departmentDao.listBySysCode2(firstResult, rows, q, id,orgCtrl);
    }

    public long getTotal() {
        return departmentDao.getTotal();
    }

    public long getTotal(TblDepartmentModel queryModel) {
        return departmentDao.getTotal(queryModel);
    }

    /*
     * 取消审核
     */
    public void RemoveAuditing() {

    }

    /**
     * @Function: findDepartment()
     * @Description: 查询部门ID，名称
     *
     * @param:q 查询参数
     * @return：返回结果描述
     * @throws：异常描述
     *
     * @author: songff
     * @date: 2019年1月13日 下午8:00:40
     *
     */
    public List<TblDepartmentInfo> findDepartment(String q) {
        List<TblDepartmentInfo> deptList = null;
        deptList = departmentDao.findDepartment(q);
        return deptList;
    }

    /**
     * 
     * @Function: findDepartmentList()
     * @Description: 查询所有部门
     *
     * @param: model
     *             参数
     * @return：返回结果描述
     * @throws：异常描述
     *
     * @author: songff
     * @date: 2019年2月15日 下午6:26:38
     *
     */
    public List<TblDepartmentInfo> findDepartmentList(TblDepartmentModel model) {
        return departmentDao.findDepartmentList(model);
    }

    /**
     * 
     * @Function: getDepartmentById
     * @Description: 获取单个执法部门信息
     *
     * @param id
     * @return
     *
     * @author: mixue
     * @date: 2019年2月19日 下午2:58:20
     */
    public TblDepartmentModel getDepartmentById(String id) {
        TblDepartmentInfo department = departmentDao.get(id);
        TblDepartmentModel departmentModel = new TblDepartmentModel();
        if (department != null)
            departmentModel = copyAllProperties(department);
        return departmentModel;
    }

    /**
     * 
     * @Function: findListByPage
     * @Description: 执法部门分页查询
     *
     * @param dataGridModel
     * @param departmentModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月20日 下午6:35:31
     */
    public List<TblDepartmentModel> findListByPage(TeeDataGridModel dataGridModel, TblDepartmentModel departmentModel) {
        // TODO Auto-generated method stub
        List<TblDepartmentModel> departmentModels = new ArrayList<>();
        List<TblDepartmentInfo> departments = departmentDao.findListByPage(dataGridModel.getFirstResult(),
                dataGridModel.getRows(), departmentModel);
        for (TblDepartmentInfo department : departments) {
            TblDepartmentModel sModel = copyAllProperties(department);
            departmentModels.add(sModel);
        }
        return departmentModels;
    }

    /**
     * 
     * @Function: findListCountByPage
     * @Description: 执法部门查询条数
     *
     * @param departmentModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月20日 下午6:35:50
     */
    public Long findListCountByPage(TblDepartmentModel departmentModel) {
        // TODO Auto-generated method stub
        return departmentDao.findListCountByPage(departmentModel);
    }

    public TblDepartmentModel copyAllProperties(TblDepartmentInfo department) {
        TblDepartmentModel departmentModel = new TblDepartmentModel();
        try {
            BeanUtils.copyProperties(department, departmentModel);
            // 从代码表获取部门性质
            if (department.getNature() != null) {
                List<Map<String, Object>> natureCode = TeeSysCodeManager
                        .getChildSysCodeListByParentCodeNo("DEPT_NATURE");
                departmentModel.setNature("");
                for (Map<String, Object> code : natureCode) {
                    if (department.getNature().equals(code.get("codeNo").toString())) {
                        departmentModel.setNature(code.get("codeName").toString());
                        break;
                    }
                }
            }
            // 从代码表获取执法系统
            if (department.getOrgSys() != null) {
                List<Map<String, Object>> orgSysCode = TeeSysCodeManager
                        .getChildSysCodeListByParentCodeNo("LAW_ENFORCEMENT_FIELD");
                departmentModel.setOrgSys("");
                String[] orgs = department.getOrgSys().split(",");
                StringBuilder orgSys = new StringBuilder("");
                for (Map<String, Object> code : orgSysCode) {
                    for (int i = 0; i < orgs.length; i++) {
                        if (orgs[i].equals(code.get("codeNo").toString())) {
                            orgSys.append(code.get("codeName").toString() + "，");
                            break;
                        }
                    }
                }
                if (orgSys.length() > 0) {
                    departmentModel.setOrgSys(orgSys.toString().substring(0, orgSys.length() - 1));
                }
            }
            // 从代码表获取部门地区
            if (department.getAdministrativeDivision() != null) {
                List<Map<String, Object>> areaCode = TeeSysCodeManager
                        .getChildSysCodeListByParentCodeNo("ADMINISTRAIVE_DIVISION");
                departmentModel.setAdministrativeDivision("");
                for (Map<String, Object> code : areaCode) {
                    if (department.getAdministrativeDivision().equals(code.get("codeNo").toString())) {
                        departmentModel.setAdministrativeDivision(code.get("codeName").toString());
                        break;
                    }
                }
            }
            // 从代码表获取部门层级
            if (department.getDeptLevel() != null) {
                List<Map<String, Object>> levelCode = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("DEPT_LEVEL");
                departmentModel.setDeptLevel("");
                for (Map<String, Object> code : levelCode) {
                    if (department.getDeptLevel().equals(code.get("codeNo").toString())) {
                        departmentModel.setDeptLevel(code.get("codeName").toString());
                        break;
                    }
                }
            }
            // 获取执法部门对应监督部门名称
            if (department.getSuperviceDepartmentId() != null) {
                Supervise supervise = superviseDao.get(department.getSuperviceDepartmentId());
                departmentModel.setSuperviceDepartmentId("");
                if (supervise != null)
                    departmentModel.setSuperviceDepartmentId(supervise.getName());
            }
            // 获取执法部门对应垂管部门
            if (department.getIsManubrium() != null && department.getIsManubrium() == 1
                    && department.getDroopId() != null) {
                TblDepartmentInfo departmentInfo = departmentDao.get(department.getDroopId());
                departmentModel.setDroopId("");
                if (departmentInfo != null)
                    departmentModel.setDroopId(departmentInfo.getName());
            }
            // 获取执法部门归属人民政府
            if (/*
                 * department.getIsGovernment() != null &&
                 * department.getIsGovernment() == 0 &&
                 */ department.getParentId() != null) {
                TblDepartmentInfo departmentInfo = departmentDao.get(department.getParentId());
                departmentModel.setParentId("");
                if (departmentInfo != null)
                    departmentModel.setParentId(departmentInfo.getName());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return departmentModel;
    }

    /**
     * 
     * @Function: findListByPageRoles
     * @Description: 执法部门分页查询（带数据权限）
     *
     * @param dataGridModel
     * @param departmentModel
     * @param orgCtrlInfoModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月28日 下午8:58:23
     */
    public List<TblDepartmentModel> findListByPageRoles(TeeDataGridModel dataGridModel,
            TblDepartmentModel departmentModel, OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        List<TblDepartmentModel> departmentModels = new ArrayList<>();
        List<TblDepartmentInfo> departments = departmentDao.findListByPageRoles(dataGridModel.getFirstResult(),
                dataGridModel.getRows(), departmentModel, orgCtrlInfoModel);
        for (TblDepartmentInfo department : departments) {
            TblDepartmentModel sModel = copyAllProperties(department);
            departmentModels.add(sModel);
        }
        return departmentModels;
    }

    /**
     * 
     * @Function: findListCountByPageRoles
     * @Description: 执法部门查询总条数（带数据权限）
     *
     * @param departmentModel
     * @param orgCtrlInfoModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月28日 下午8:58:30
     */
    public Long findListCountByPageRoles(TblDepartmentModel departmentModel, OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        return departmentDao.findListCountByPageRoles(departmentModel, orgCtrlInfoModel);
    }

    /**
     * 
     * @Function: getDepartmentRoles
     * @Description: 分权限获取本级及下级/本单位及下级单位执法部门
     *
     * @param orgCtrlInfoModel
     * @return
     *
     * @author: mixue
     * @date: 2019年3月2日 下午3:11:10
     */
    public List<TblDepartmentModel> getDepartmentRoles(OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        List<TblDepartmentModel> departmentModels = new ArrayList<>();
        List<TblDepartmentInfo> departments = departmentDao.getDepartmentRoles(orgCtrlInfoModel);
        for (TblDepartmentInfo department : departments) {
            TblDepartmentModel sModel = copyAllProperties(department);
            departmentModels.add(sModel);
        }
        return departmentModels;
    }
    
    @SuppressWarnings("rawtypes")
	public List<Map> parentIdToLevel(int firstResult, int rows,OrgCtrlInfoModel orgCtrl){
		return departmentDao.parentIdToLevel(firstResult,rows,orgCtrl);
	}
    
    public List<TblDepartmentInfo> findGovDeptsNextLevel(TblDepartmentModel tblDepartmentModel){
        return departmentDao.findDeptsNextLevel(tblDepartmentModel);
    } 
    
    /*
	 * 分配账号
	 */
	public TeeJson saveUser(TblDepartmentModel departmentModel,OrgCtrlInfoModel orgCtrl) throws NoSuchAlgorithmException{
		TeeJson json = new TeeJson();
		TeePerson person = new TeePerson();
		TeeDepartment department = new TeeDepartment();
		TeeUserRole userRole = new TeeUserRole();
		//获取当前登录人信息
		TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
		//账号
		person.setUserId(departmentModel.getUserName());
		//用户姓名
		person.setUserName(departmentModel.getName());
		//密码
		person.setPassword(TeePassEncryptMD5.cryptDynamic("zfjd123456"));
		//所属部门
		Map<String, Object> deptUuid = departmentDao.deptUuid(departmentModel.getId());
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
		if(!TeeUtility.isNullorEmpty(departmentModel.getId())){
			DepartmentUser departmentUser = new DepartmentUser();
			departmentUser.setId(UUID.randomUUID().toString());
			departmentUser.setDepartmentId(departmentModel.getId());
			departmentUser.setIsDelete(0);
			departmentUser.setUserUuid(uuiddInteger);
			departmentUser.setCreatorTime(new Date());
			departmentUser.setCreatorId(user.getUserId());
			departmentUserDao.save(departmentUser);
		}
		//关联表PERSON_MENU_GROUP（用户-权限组）
		//查询执法部门的uuid
		Map<String, Object> deptId = departmentDao.deptId();
		String deptIdString  = deptId.toString();
		Integer deptIdInteger = Integer.parseInt(deptIdString.substring(4, deptIdString.length()-1));
		simpleDaoSupport.executeNativeUpdate("insert into PERSON_MENU_GROUP values (?,?)",
				new Object[]{uuiddInteger,deptIdInteger});
		//tbl_base_organization表中插入用户密码
		try {
			simpleDaoSupport.executeNativeUpdate("update tbl_base_organization set username =? , password =?  where id =?",
					new Object[]{departmentModel.getUserName(),TeePassEncryptMD5.cryptDynamic("zfjd123456"),departmentModel.getId()});
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
		Map<String, Object> uuid = departmentDao.getUuid(id);
		String a = uuid.toString();
		Integer b = Integer.parseInt(a.substring(4, a.length()-1));
		//默认新密码为"zfjd123456"
		String newPaw =TeePassEncryptMD5.cryptDynamic("zfjd123456");
		//person表中更新新密码
		simpleDaoSupport.executeNativeUpdate("update person set PWD =? where uuid = ?",
				new Object[]{newPaw,b});
		//tbl_base_organization执法人员表中更新新密码
		simpleDaoSupport.executeNativeUpdate("update tbl_base_organization set password =? where id =?",
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
			Map<String, Object> uuid = departmentDao.getUuid(id);
			String a = uuid.toString();
			Integer b = Integer.parseInt(a.substring(4, a.length()-1));
			//删除person表中用户账号信息
			simpleDaoSupport.executeNativeUpdate("delete from person where uuid = ?",
					new Object[]{b});
			//删除关系表
			Date date = new Date();
			simpleDaoSupport.executeNativeUpdate("update tbl_sys_department_user set is_delete = 1,delete_time =? where department_id = ?",
					new Object[]{date,id});
			//更新执法部门表信息
			simpleDaoSupport.executeNativeUpdate("update tbl_base_organization set username = '' , password = ''  where id =?",
					new Object[]{id});
			return json;
		 }
    
    public List<TblDepartmentInfo> findEnforceDeptsSameLevel(TblDepartmentModel tblDepartmentModel){
        return departmentDao.findDeptsSameLevel(tblDepartmentModel);
    } 
    
    public List<TblDepartmentInfo> findManuDeptsByParentIds(TblDepartmentModel tblDepartmentModel){
        return departmentDao.findManuDeptsByParentIds(tblDepartmentModel);
    } 
    
    
    
    /**
     * 导出Excel
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @date:2019年3月29日下午3:42:44
     * @author:yxy
     */
	@SuppressWarnings({ "resource" })
	public void exportExcel(String term,List<TblDepartmentInfo> lists,HttpServletRequest request,HttpServletResponse response) throws RowsExceededException, WriteException, IOException{
    	// 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("XXX表");
        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 4.创建单元格，设置值表头，设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
   
        // 设置表头18
        String terms[] = term.split(",");
        //列数
        String rowcell[] = new String[terms.length];
//        String listVal [] = new String[terms.length];
        for (int i = 0; i < terms.length; i++) {
			if(terms[i].equals("deptLevel")){
				rowcell[i] = "部门层级";
//				listVal[i] = "list.getDeptLevel()";
			}
			if(terms[i].equals("administrativeDivision")){
				rowcell[i] = "部门地区";
//				listVal[i] = "list.getAdministrativeDivision()";
			}
			if(terms[i].equals("orgSys")){
				rowcell[i] = "所属领域";
//				listVal[i] = "list.getOrgSys()";
			}
			if(terms[i].equals("isManubrium")){
				rowcell[i] = "是否垂管";
//				listVal[i] = "list.getIsManubrium()";
			}
			if(terms[i].equals("subName")){
				rowcell[i] = "执法主体名称";
//				listVal[i] = "list.getSubName()";
			}
			if(terms[i].equals("orgName")){
				rowcell[i] = "委托组织名称";
//				listVal[i] = "list.getSubName()";
			}
			if(terms[i].equals("powerName")){
				rowcell[i] = "职权名称";
//				listVal[i] = "list.getPowerName()";
			}
			if(terms[i].equals("personNo")){
				rowcell[i] = "执法人员数量";
//				listVal[i] = "list.getPersonNo()";
			}
			if(terms[i].equals("orgNo")){
				rowcell[i] = "委托组织个数";
//				listVal[i] = "list.getOrgNo()";
			}
			if(terms[i].equals("powerNo")){
				rowcell[i] = "职权个数";
//				listVal[i] = "list.getPowerNo()";
			}
			if(terms[i].equals("subNo")){
				rowcell[i] = "执法主体个数";
//				listVal[i] = "list.getSubNo()";
			}
			if(terms[i].equals("postCode")){
				rowcell[i] = "邮编";
//				listVal[i] = "list.getPostCode()";
			}
			if(terms[i].equals("address")){
				rowcell[i] = "地址";
//				listVal[i] = "list.getAddress()";
			}
			if(terms[i].equals("departmentCode")){
				rowcell[i] = "统一社会信用代码";
//				listVal[i] = "list.getAddress()";
			}
			if(terms[i].equals("nature")){
				rowcell[i] = "部门性质";
//				listVal[i] = "list.getNature()";
			}
			if(terms[i].equals("representative")){
				rowcell[i] = "法定代表人";
//				listVal[i] = "list.getRepresentative()";
			}
			if(terms[i].equals("userName")){
				rowcell[i] = "账号";
//				listVal[i] = "list.getUserName()";
			}
			if(terms[i].equals("isExamine")){
				rowcell[i] = "审核状态";
//				listVal[i] = "list.getIsExamine()";
			}
		}
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("部门名称");
        cell.setCellStyle(style);
        
        for (int j = 0; j < rowcell.length; j++) {
        	 cell = row.createCell(j+1);
             cell.setCellValue(rowcell[j]);
             cell.setCellStyle(style);
		}
        List<StatisticDept> cNumber = null;
        StatisticDept stDept = new StatisticDept();
        for (int i = 0; i < lists.size(); i++) {
            row = sheet.createRow((int) i + 1);
            TblDepartmentInfo list= lists.get(i);
            //获取部门信息数量
            cNumber = statisticDeptService.getByDeptId(list.getId());
            if(!TeeUtility.isNullorEmpty(cNumber)){
            	for (int n = 0; n < cNumber.size(); n++) {
					stDept.setOrgNo(cNumber.get(n).getOrgNo());
					stDept.setSubNo(cNumber.get(n).getSubNo());
					stDept.setPersonNo(cNumber.get(n).getPersonNo());
					stDept.setPowerNo(cNumber.get(n).getPowerNo());
				}
            }
            // 创建单元格，设置值
            row.createCell(0).setCellValue(list.getName());
            for (int j = 0; j < rowcell.length; j++) {
//            	row.createCell(j+1).setCellValue(listVal[j].toString());
            	if (terms[j].equals("deptLevel")) {
                    String deptLevel = TeeSysCodeManager.getChildSysCodeNameCodeNo("DEPT_LEVEL", list.getDeptLevel());
            		row.createCell(j+1).setCellValue(deptLevel);
				}
            	if (terms[j].equals("administrativeDivision")) {
                    String area = TeeSysCodeManager.getChildSysCodeNameCodeNo("ADMINISTRAIVE_DIVISION", list.getAdministrativeDivision());
            		row.createCell(j+1).setCellValue(area);
				}
            	if (terms[j].equals("orgSys")) {
            		List<Map<String, Object>> orgSysCode = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("LAW_ENFORCEMENT_FIELD");
                    String[] orgs = list.getOrgSys().split(",");
                    StringBuilder orgSys = new StringBuilder("");
                    for (Map<String, Object> code : orgSysCode) {
                        for (int m = 0; m < orgs.length; m++) {
                            if (orgs[m].equals(code.get("codeNo").toString())) {
                                orgSys.append(code.get("codeName").toString() + ",");
                                break;
                            }
                        }
                    }
                    if (orgSys.length() > 0) {
                    	String aString =(orgSys.toString().substring(0, orgSys.length() - 1));
                		row.createCell(j+1).setCellValue(aString);
                    }
				}
            	if (terms[j].equals("isManubrium")) {
            		if(list.getIsManubrium()==1){
            			row.createCell(j+1).setCellValue("是");
            		}
            		row.createCell(j+1).setCellValue("否");
				}
            	if (terms[j].equals("subName")) {
            		row.createCell(j+1).setCellValue("");
				}
            	if (terms[j].equals("orgName")) {
            		row.createCell(j+1).setCellValue("");
				}
            	if (terms[j].equals("powerName")) {
            		row.createCell(j+1).setCellValue("");
				}
            	if (terms[j].equals("personNo")) {
            		row.createCell(j+1).setCellValue(stDept.getPersonNo());
				}
            	if (terms[j].equals("orgNo")) {
            		row.createCell(j+1).setCellValue(stDept.getOrgNo());
				}
            	if (terms[j].equals("powerNo")) {
            		row.createCell(j+1).setCellValue(stDept.getPowerNo());
				}
            	if (terms[j].equals("subNo")) {
            		row.createCell(j+1).setCellValue(stDept.getSubNo());
				}
            	if (terms[j].equals("postCode")) {
            		row.createCell(j+1).setCellValue(list.getPostCode());
				}
            	if (terms[j].equals("address")) {
            		row.createCell(j+1).setCellValue(list.getAddress());
				}
            	if (terms[j].equals("departmentCode")) {
            		row.createCell(j+1).setCellValue(list.getDepartmentCode());
				}
            	if (terms[j].equals("nature")) {
                    String deptNature = TeeSysCodeManager.getChildSysCodeNameCodeNo("DEPT_NATURE", list.getNature());
            		row.createCell(j+1).setCellValue(deptNature);
				}
            	if (terms[j].equals("representative")) {
            		row.createCell(j+1).setCellValue(list.getRepresentative());
				}
            	if (terms[j].equals("userName")) {
            		row.createCell(j+1).setCellValue(list.getUserName());
				}
            	if (terms[j].equals("isExamine")) {
            		if(list.getIsExamine()==1){
                		row.createCell(j+1).setCellValue("已审核");
            		}else{
                		row.createCell(j+1).setCellValue("未审核");
            		}
				}
			}
          }
		String data = TeeDateUtil.format(new Date(),"yyyyMMdd");
        String fileName = "执法机关综合查询表"+data+"";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="
            + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
   
        try {
          bis = new BufferedInputStream(is);
          bos = new BufferedOutputStream(out);
          byte[] buff = new byte[2048];
          int bytesRead;
          // Simple read/write loop.
          while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
          }
        } catch (Exception e) {
          // TODO: handle exception
          e.printStackTrace();
        } finally {
          if (bis != null)
            bis.close();
          if (bos != null)
            bos.close();
        }
    }
}
