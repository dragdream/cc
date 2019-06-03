package com.beidasoft.zfjd.permission.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.department.bean.OrganizationSubject;
import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.department.model.TblDepartmentModel;
import com.beidasoft.zfjd.department.service.OrganizationSubjectService;
import com.beidasoft.zfjd.department.service.TblDepartmentService;
import com.beidasoft.zfjd.permission.model.PermissionItemGistModel;
import com.beidasoft.zfjd.permission.model.PermissionItemModel;
import com.beidasoft.zfjd.permission.model.PermissionItemPowerModel;
import com.beidasoft.zfjd.permission.service.PermissionItemService;
import com.beidasoft.zfjd.power.bean.Power;
import com.beidasoft.zfjd.power.model.PowerModel;
import com.beidasoft.zfjd.power.service.PowerService;
import com.beidasoft.zfjd.system.bean.SysBusinessRelation;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

import net.sf.json.JSONArray;

/**
 * @ClassName: PermissionItemController.java
 * @Description: 许可事项CONTROLLER层
 *
 * @author: mixue
 * @date: 2019年2月26日 下午8:26:04
 */
@Controller
@RequestMapping("permissionItemCtrl")
public class PermissionItemController {

    @Autowired
    private PermissionItemService permissionItemService;

    @Autowired
    private TblDepartmentService departmentService;

    @Autowired
    private TeeDeptService deptService;

    @Autowired
    private OrganizationSubjectService organizationSubjectService;

    @Autowired
    private PowerService powerService;

    /**
     * 
     * @Function: findListByPage
     * @Description: 许可事项分页查询(×)
     *
     * @param dataGridModel
     * @param permissionItemModel
     * @param request
     * @return
     *
     * @author: mixue
     * @date: 2019年2月26日 下午8:50:52
     */
    @ResponseBody
    @RequestMapping("findListBypage.action")
    public TeeEasyuiDataGridJson findListByPage(TeeDataGridModel dataGridModel, PermissionItemModel permissionItemModel,
            HttpServletRequest request) {
        // 返回前台json
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        List<PermissionItemModel> permissionItemModels = new ArrayList<>();
        Long count = null;
        try {
            permissionItemModels = permissionItemService.findListByPage(dataGridModel, permissionItemModel);
            count = permissionItemService.findListCountByPage(permissionItemModel);
            json.setRows(permissionItemModels);
            json.setTotal(count);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 
     * @Function: findListByPageRoles
     * @Description: 许可事项分页查询（本单位）
     *
     * @param dataGridModel
     * @param permissionItemModel
     * @param request
     * @return
     *
     * @author: mixue
     * @date: 2019年3月2日 下午2:58:19
     */
    @ResponseBody
    @RequestMapping("findListBypageRoles.action")
    public TeeEasyuiDataGridJson findListByPageRoles(TeeDataGridModel dataGridModel,
            PermissionItemModel permissionItemModel, HttpServletRequest request) {
        // 返回前台json
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        List<PermissionItemModel> permissionItemModels = new ArrayList<>();
        Long count = null;
        try {
            // 办理部门查询条件为空时，设置办理部门为本单位
            if (TeeUtility.isNullorEmpty(permissionItemModel.getXkGsdw())) {
                TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
                TeeDepartment department = deptService.selectDeptByUuid(TeeStringUtil.getString(person.getDept().getUuid(), ""));
                if (department.getRelations() != null && department.getRelations().size() > 0) {
                    SysBusinessRelation businessRelation = department.getRelations().get(0);
                    if (!TeeUtility.isNullorEmpty(businessRelation.getBusinessDeptId())) {
                        permissionItemModel.setXkGsdw(businessRelation.getBusinessDeptId());
                    }
                }else{
                    permissionItemModel.setXkGsdw("/");
                }
            }
            permissionItemModels = permissionItemService.findListByPage(dataGridModel, permissionItemModel);
            count = permissionItemService.findListCountByPage(permissionItemModel);
            json.setRows(permissionItemModels);
            json.setTotal(count);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 
     * @Function: getDepartmentList
     * @Description: 获取执法部门ID和名称(×)
     *
     * @param q
     * @param request
     * @return
     *
     * @author: mixue
     * @date: 2019年2月26日 下午8:49:58
     */
    @ResponseBody
    @RequestMapping("/getDepartmentList.action")
    public List<TblDepartmentInfo> getDepartmentList(String q, HttpServletRequest request) {
        TblDepartmentModel departmentModel = new TblDepartmentModel();
        departmentModel.setName(q);
        List<TblDepartmentInfo> codeList = departmentService.findDepartment(q);
        return codeList;
    }

    /**
     * 
     * @Function: getDepartmentListByOneself
     * @Description: 获取本单位ID及NAME
     *
     * @param name
     * @param request
     * @return
     *
     * @author: mixue
     * @date: 2019年3月2日 上午10:42:38
     */
    @ResponseBody
    @RequestMapping("/getDepartmentListByOneself.action")
    public TeeJson getDepartmentListByOneself(String name, HttpServletRequest request) {
        TeeJson json = new TeeJson();
        List<TblDepartmentInfo> codeList = new ArrayList<>();
        TblDepartmentInfo departmentInfo = new TblDepartmentInfo();
        try {
            TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
            TeeDepartment department = deptService.selectDeptByUuid(TeeStringUtil.getString(person.getDept().getUuid(), ""));
            if (department.getRelations() != null && department.getRelations().size() > 0) {
                SysBusinessRelation businessRelation = department.getRelations().get(0);
                if (!TeeUtility.isNullorEmpty(businessRelation.getBusinessDeptId())) {
                    departmentInfo.setId(businessRelation.getBusinessDeptId());
                    departmentInfo.setName(businessRelation.getBusinessDeptName());
                    codeList.add(departmentInfo);
                }
            }
            json.setRtData(codeList);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            json.setRtState(false);
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 
     * @Function: getPermissionItemById
     * @Description: 通过ID获取单个许可事项
     *
     * @param id
     * @return
     *
     * @author: mixue
     * @date: 2019年2月26日 下午8:50:05
     */
    @ResponseBody
    @RequestMapping("getPermissionItemById.action")
    public TeeJson getPermissionItemById(String id) {
        TeeJson json = new TeeJson();
        try {
            PermissionItemModel permissionItemModel = permissionItemService.getPermissionItemById(id);
            json.setRtData(permissionItemModel);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            json.setRtState(false);
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 
     * @Function: updateOrDelete
     * @Description: 许可事项修改/删除
     *
     * @param permissionItemModel
     * @param request
     * @return
     *
     * @author: mixue
     * @date: 2019年2月27日 上午9:57:36
     */
    @ResponseBody
    @RequestMapping("updateOrDelete.action")
    public TeeJson updateOrDelete(PermissionItemModel permissionItemModel, HttpServletRequest request) {
        TeeJson json = new TeeJson();
        try {
            TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
            permissionItemModel.setUpdatePersonId(person.getUuid());
            permissionItemModel.setUpdatePersonName(person.getUserName());
            List<PermissionItemPowerModel> powerList = powerLists(permissionItemModel);
            permissionItemModel.setPowerList(powerList);
            List<PermissionItemGistModel> gistList = gistLists(permissionItemModel);
            permissionItemModel.setGistList(gistList);
            permissionItemService.updateOrDeleteById(permissionItemModel);
            json.setRtState(true);
        } catch (Exception e) {
            json.setRtState(false);
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 
     * @Function: savePermissionItem
     * @Description: 许可事项新增
     *
     * @param permissionItemModel
     * @param request
     * @return
     *
     * @author: mixue
     * @date: 2019年2月27日 上午9:57:57
     */
    @ResponseBody
    @RequestMapping("savePermissionItem.action")
    public TeeJson savePermissionItem(PermissionItemModel permissionItemModel, HttpServletRequest request) {
        TeeJson json = new TeeJson();
        try {
            TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
            permissionItemModel.setCreatePersonId(person.getUuid());
            permissionItemModel.setCreatePersonName(person.getUserName());
            List<PermissionItemPowerModel> powerList = powerLists(permissionItemModel);
            permissionItemModel.setPowerList(powerList);
            List<PermissionItemGistModel> gistList = gistLists(permissionItemModel);
            permissionItemModel.setGistList(gistList);
            json = permissionItemService.savePermissionItem(permissionItemModel);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }

    @SuppressWarnings("unchecked")
    public List<PermissionItemPowerModel> powerLists(PermissionItemModel permissionItemModel) throws Exception {
        List<PermissionItemPowerModel> powerLists = new ArrayList<>();
        try {
            // 许可职权json串转List<..Model>对象
            if (!TeeUtility.isNullorEmpty(permissionItemModel.getPowerJsonStr())) {
                JSONArray powerArray = JSONArray.fromObject(permissionItemModel.getPowerJsonStr());
                List<PermissionItemPowerModel> powerList = (List<PermissionItemPowerModel>) JSONArray
                        .toCollection(powerArray, PermissionItemPowerModel.class);
                for (PermissionItemPowerModel powerModel : powerList) {
                    PermissionItemPowerModel power = new PermissionItemPowerModel();
                    // 将 powerModel 赋值 power
                    BeanUtils.copyProperties(powerModel, power);
                    power.setId(UUID.randomUUID().toString());
                    power.setPermissionItemModel(permissionItemModel);
                    power.setCreateDate(new Date());
                    powerLists.add(power);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return powerLists;
    }

    @SuppressWarnings("unchecked")
    public List<PermissionItemGistModel> gistLists(PermissionItemModel permissionItemModel) throws Exception {
        List<PermissionItemGistModel> gistLists = new ArrayList<>();
        try {
            // 设定依据json串转List<..Model>对象
            if (!TeeUtility.isNullorEmpty(permissionItemModel.getGistJsonStr())) {
                JSONArray gistArray = JSONArray.fromObject(permissionItemModel.getGistJsonStr());
                List<PermissionItemGistModel> gistList = (List<PermissionItemGistModel>) JSONArray
                        .toCollection(gistArray, PermissionItemGistModel.class);
                for (PermissionItemGistModel gistModel : gistList) {
                    PermissionItemGistModel gist = new PermissionItemGistModel();
                    // 将 gistModel 赋值 gist
                    BeanUtils.copyProperties(gistModel, gist);
                    gist.setId(UUID.randomUUID().toString());
                    gist.setPermissionItemModel(permissionItemModel);
                    gist.setCreateDate(new Date());
                    gistLists.add(gist);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gistLists;
    }

    /**
     * 
     * @Function: getActSubject
     * @Description: 根据登录账户获取本单位对应主体
     *
     * @param request
     * @return
     *
     * @author: mixue
     * @date: 2019年3月5日 下午7:34:42
     */
    @ResponseBody
    @RequestMapping("getActSubject.action")
    public TeeJson getActSubject(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        try {
            TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
            TeeDepartment department = deptService.selectDeptByUuid(TeeStringUtil.getString(person.getDept().getUuid(), ""));
            List<OrganizationSubject> organizationSubjects = new ArrayList<>();
            String subjectId = "";
            if (department.getRelations() != null && department.getRelations().size() > 0) {
                SysBusinessRelation businessRelation = department.getRelations().get(0);
                if (!TeeUtility.isNullorEmpty(businessRelation.getBusinessDeptId())
                        && TeeUtility.isNullorEmpty(businessRelation.getBusinessSubjectId())) {
                    StringBuffer subjectIds = new StringBuffer("");
                    organizationSubjects = organizationSubjectService.getSubjects(businessRelation.getBusinessDeptId());
                    if (organizationSubjects != null && organizationSubjects.size() > 0) {
                        for (int i = 0; i < organizationSubjects.size(); i++) {
                            subjectIds.append(organizationSubjects.get(i).getSubjectId() + ",");
                        }
                        subjectId = subjectIds.substring(0, subjectIds.length() - 1);
                    }
                } else if (!TeeUtility.isNullorEmpty(businessRelation.getBusinessDeptId())
                        && !TeeUtility.isNullorEmpty(businessRelation.getBusinessSubjectId())) {
                    subjectId = businessRelation.getBusinessSubjectId();
                }
            }
            json.setRtData(subjectId);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            json.setRtState(false);
            e.printStackTrace();
        }
        return json;
    }

    @ResponseBody
    @RequestMapping("/getPowerByActSubject")
    public TeeEasyuiDataGridJson getPowerByActSubject(TeeDataGridModel dm, HttpServletRequest request,
            PowerModel powerModel) {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();

        List<Power> powers = powerService.getPowerByActSubjects(dm, powerModel);
        Long total = powerService.getPowerCountByActSubjects(powerModel);

        List<PowerModel> powerModels = new ArrayList<PowerModel>();
        List<Map<String, Object>> codeList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_TYPE");

        PowerModel rtModel = null;
        for (int i = 0; i < powers.size(); i++) {
            rtModel = new PowerModel();
            Power temp = powers.get(i);

            BeanUtils.copyProperties(temp, rtModel);
            rtModel.setCreateDateStr(TeeDateUtil.format(temp.getCreateDate(), "yyyy-MM-dd"));
            rtModel.setDeleteDateStr(TeeDateUtil.format(temp.getDeleteDate(), "yyyy-MM-dd"));
            rtModel.setRevokeDateStr(TeeDateUtil.format(temp.getRevokeDate(), "yyyy-MM-dd"));

            for (Map<String, Object> code : codeList) {
                if (rtModel.getPowerType().equals(code.get("codeNo").toString())) {
                    rtModel.setPowerType(code.get("codeName").toString());
                    break;
                }
            }

            String powerDetail = "";
            for (int j = 0; j < temp.getDetails().size(); j++) {
                powerDetail = powerDetail + temp.getDetails().get(j).getName() + ",";
            }
            if (powerDetail.length() > 0) {
                rtModel.setPowerDetail(powerDetail.substring(0, powerDetail.length() - 1));
            }
            powerModels.add(rtModel);
        }

        gridJson.setRows(powerModels);
        gridJson.setTotal(total);

        return gridJson;
    }
}
