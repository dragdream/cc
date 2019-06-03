package com.beidasoft.zfjd.common.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.department.service.TblDepartmentService;
import com.beidasoft.zfjd.subject.bean.Subject;
import com.beidasoft.zfjd.subject.service.SubjectService;
import com.beidasoft.zfjd.supervise.bean.Supervise;
import com.beidasoft.zfjd.supervise.service.SuperviseService;
import com.beidasoft.zfjd.system.bean.SysBusinessRelation;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.priv.bean.TeeMenuGroup;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class CommonService extends TeeBaseService{

    @Autowired
    private TeeDeptService deptService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private TblDepartmentService tblDepartmentService;
    @Autowired
    private SuperviseService superviseService;
    @Autowired
    private TeePersonService personService;
    
    public void updateFlowRunVars(String primaryId, int runId, String key) {
        simpleDaoSupport.executeUpdate("update TeeFlowRunVars set varValue = '" + primaryId + "' where flowRun.runId="+ runId + " and varKey= '" + key + "'", null);
    }
    
    /**
     * 
    * @Function: getOrgCtrlInfo()
    * @Description: 传入HttpServletRequest作为参数，获取业务系统用户数据控制相关数据
    * 
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年2月27日 上午11:20:53 
    *
     */
    public OrgCtrlInfoModel getOrgCtrlInfo(HttpServletRequest request) {
        //权限控制类
        OrgCtrlInfoModel orgCtrl = new OrgCtrlInfoModel();
        //登录用户session
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        TeeDepartment department = deptService.selectDeptByUuid(TeeStringUtil.getString(person.getDept().getUuid(), ""));
        
        //菜单权限组
        TeePerson personMenu = personService.selectByUuid(person.getUuid());
        String menuNames = "";
        List<TeeMenuGroup> menuGroupList = personMenu.getMenuGroups();
        if(menuGroupList != null  && menuGroupList.size() > 0){//辅助权限
            for (int i = 0; i < menuGroupList.size(); i++) {
                menuNames = menuNames + menuGroupList.get(i).getMenuGroupName() +",";
            }
            menuNames = menuNames.substring(0, menuNames.length() - 1);
        }
        //菜单权限组
        orgCtrl.setMenuNames(menuNames);
        //确认是否是分级管理员（1 是，2否）
        if (orgCtrl.getMenuNames().contains("分级管理员权限组")) {
            orgCtrl.setGradeAdministrator(true);
        }else {
            orgCtrl.setGradeAdministrator(false);
        }
        //确认是否是执法人员（1 是，2否）
        if (orgCtrl.getMenuNames().contains("执法人员zfjd")) {
            orgCtrl.setIsLawPerson(true);
        }else {
            orgCtrl.setIsLawPerson(false);
        }
        //登录系统部门ID
        orgCtrl.setSysDeptId(person.getDept().getUuid());
        SysBusinessRelation relation = null;
        if (department.getRelations() != null && department.getRelations().size() > 0) {
            relation = department.getRelations().get(0);
        }else{
            return orgCtrl;
        }
        String orgSys = "";
        if (relation != null) {
            
            if (relation.getBusinessSubjectId() != null && !"".equals(relation.getBusinessSubjectId())) {
                // 该账号为执法主体账号
                Subject subejct = subjectService.getById(relation.getBusinessSubjectId());
                if (subejct != null) {
                    //执法主体名称
                    orgCtrl.setSubjectName(subejct.getSubName());
                    //执法主体ID
                    orgCtrl.setSubjectId(subejct.getId());
                    //执法主体所属系统编号 
                    if (!TeeUtility.isNullorEmpty(subejct.getOrgSys())) {
                        orgSys = subejct.getOrgSys();
                    }
                    //表示执法主体标识
                    orgCtrl.setOrgType(3);
                    
                    //执法主体行政区划（）
                    if (!TeeUtility.isNullorEmpty(subejct.getArea())) {
                        orgCtrl.setAdminDivisionCode(subejct.getArea());
                    }
                    //执法主体层级
                    if (!TeeUtility.isNullorEmpty(subejct.getSubLevel())) {
                        orgCtrl.setLevelCode(subejct.getSubLevel());
                    }
                }
            } else {
                if (relation.getBusinessDeptId() != null && !"".equals(relation.getBusinessDeptId())) {
                    // 该账号为执法部门账号
                    TblDepartmentInfo departmentInfo = tblDepartmentService.getById(relation.getBusinessDeptId());
                    if (departmentInfo != null) {
                        //执法部门名称
                        orgCtrl.setDepartName(departmentInfo.getName());
                        //执法部门ID
                        orgCtrl.setDepartId(departmentInfo.getId());
                        //执法部门所属系统编号
                        if (!TeeUtility.isNullorEmpty(departmentInfo.getOrgSys())) {
                            orgSys = departmentInfo.getOrgSys();
                        }
                        //表示执法部门标识
                        orgCtrl.setOrgType(2);
                        
                        //执法部门行政区划
                        if (!TeeUtility.isNullorEmpty(departmentInfo.getAdministrativeDivision())) {
                            orgCtrl.setAdminDivisionCode(departmentInfo.getAdministrativeDivision());
                        }
                        //执法部门层级
                        if (!TeeUtility.isNullorEmpty(departmentInfo.getDeptLevel())) {
                            orgCtrl.setLevelCode(departmentInfo.getDeptLevel());
                        }
                    }
                }else {
                    //该账号为监督部门账号
                    if (relation.getBusinessSupDeptId() != null && !"".equals(relation.getBusinessSupDeptId())) {
                        Supervise supervise = superviseService.getById(relation.getBusinessSupDeptId());
                        if (supervise != null) {
                            //监督部门名称
                            orgCtrl.setSupDeptName(supervise.getName());
                            //监督部门ID
                            orgCtrl.setSupDeptId(supervise.getId());
                            //监督部门所属系统
                            if (!TeeUtility.isNullorEmpty(supervise.getOrgSys())) {
                                orgSys = supervise.getOrgSys();
                            }
                            //表示监督部门标识
                            orgCtrl.setOrgType(1);
                            //监督部门行政区划
                            if (!TeeUtility.isNullorEmpty(supervise.getAdministrativeDivision())) {
                                orgCtrl.setAdminDivisionCode(supervise.getAdministrativeDivision());
                            }
                            //监督部门层级
                            if (!TeeUtility.isNullorEmpty(supervise.getDeptLevel())) {
                                orgCtrl.setLevelCode(supervise.getDeptLevel());
                            }
                        }
                    }
                }
            }
        }
        //系统编号
        orgCtrl.setOrgSysCode(orgSys);
        return orgCtrl;
    }
}
