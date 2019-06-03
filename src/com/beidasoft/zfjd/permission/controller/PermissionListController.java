package com.beidasoft.zfjd.permission.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.department.model.TblDepartmentModel;
import com.beidasoft.zfjd.department.service.TblDepartmentService;
import com.beidasoft.zfjd.permission.bean.PermissionItem;
import com.beidasoft.zfjd.permission.model.PermissionItemModel;
import com.beidasoft.zfjd.permission.model.PermissionListModel;
import com.beidasoft.zfjd.permission.service.PermissionItemService;
import com.beidasoft.zfjd.permission.service.PermissionListService;
import com.beidasoft.zfjd.system.bean.SysBusinessRelation;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * @ClassName: PermissionListController.java
 * @Description: 许可清单CONTROLLER层
 *
 * @author: mixue
 * @date: 2019年2月21日 下午3:47:33
 */
@Controller
@RequestMapping("permissionListCtrl")
public class PermissionListController {

    @Autowired
    private PermissionListService permissionListService;
    
    @Autowired
    private PermissionItemService permissionItemService;
    
    @Autowired
    private TblDepartmentService departmentService;
    
    @Autowired
    private TeeDeptService deptService;
    
    /**
     * 
     * @Function: findListByPage
     * @Description: 许可清单分页查询
     *
     * @param dataGridModel
     * @param permissionListModel
     * @param request
     * @return
     *
     * @author: mixue
     * @date: 2019年2月21日 下午6:10:51
     */
    @ResponseBody
    @RequestMapping("findListBypage.action")
    public TeeEasyuiDataGridJson findListByPage(TeeDataGridModel dataGridModel, PermissionListModel permissionListModel, HttpServletRequest request) {
        // 返回前台json 
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        List<PermissionListModel> permissionListModels = new ArrayList<>();
        Long count = null;
        try {
            permissionListModels = permissionListService.findListByPage(dataGridModel, permissionListModel);
            count = permissionListService.findListCountByPage(permissionListModel);
            json.setRows(permissionListModels);
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
     * @Description: 许可清单分页查询（本单位）
     *
     * @param dataGridModel
     * @param permissionListModel
     * @param request
     * @return
     *
     * @author: mixue
     * @date: 2019年3月2日 下午6:07:47
     */
    @ResponseBody
    @RequestMapping("findListBypageRoles.action")
    public TeeEasyuiDataGridJson findListByPageRoles(TeeDataGridModel dataGridModel,
            PermissionListModel permissionListModel, HttpServletRequest request) {
        // 返回前台json
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        List<PermissionListModel> permissionListModels = new ArrayList<>();
        Long count = null;
        try {
            // 办理部门查询条件为空时，设置办理部门为本单位
            if (TeeUtility.isNullorEmpty(permissionListModel.getXkXkjg())) {
                TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
                TeeDepartment department = deptService.selectDeptByUuid(TeeStringUtil.getString(person.getDept().getUuid(), ""));
                if (department.getRelations() != null && department.getRelations().size() > 0) {
                    SysBusinessRelation businessRelation = department.getRelations().get(0);
                    if (!TeeUtility.isNullorEmpty(businessRelation.getBusinessDeptId())) {
                        permissionListModel.setXkXkjg(businessRelation.getBusinessDeptId());
                    }
                }else{
                    permissionListModel.setXkXkjg("/");
                }
            }
            permissionListModels = permissionListService.findListByPage(dataGridModel, permissionListModel);
            count = permissionListService.findListCountByPage(permissionListModel);
            json.setRows(permissionListModels);
            json.setTotal(count);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }
    
    /**
     * 
     * @Function: savePermissionList
     * @Description: 许可清单填录
     *
     * @param permissionListModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月22日 下午4:54:32
     */
    @ResponseBody
    @RequestMapping("savePermissionList.action")
    public TeeJson savePermissionList(PermissionListModel permissionListModel,HttpServletRequest request){
        TeeJson json = new TeeJson();
        try {
            TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
            permissionListModel.setCreatePersonId(person.getUuid());
            permissionListModel.setCreatePersonName(person.getUserName());
            json = permissionListService.savePermissionList(permissionListModel);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }
    
    /**
     * 
     * @Function: updateOrDelete
     * @Description: 删除、修改
     *
     * @param permissionListModel
     * @param request
     * @return
     *
     * @author: mixue
     * @date: 2019年2月25日 上午9:11:42
     */
    @ResponseBody
    @RequestMapping("updateOrDelete.action")
    public TeeJson updateOrDelete(PermissionListModel permissionListModel, HttpServletRequest request) {
        TeeJson json = new TeeJson();
        try {
            TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
            permissionListModel.setUpdatePersonId(person.getUuid());
            permissionListModel.setUpdatePersonName(person.getUserName());
            permissionListService.updateOrDeleteById(permissionListModel);
            json.setRtState(true);
        } catch (Exception e) {
            json.setRtState(false);
            e.printStackTrace();
        }
        return json;
    }
    
    /**
     * 
     * @Function: getDepartmentList
     * @Description: 获取执法部门ID和名称
     *
     * @param q
     * @param request
     * @return
     *
     * @author: mixue
     * @date: 2019年2月25日 下午7:04:43
     */
    @ResponseBody
    @RequestMapping("/getDepartmentList.action")
    public List<TblDepartmentInfo> getDepartmentList(String q,HttpServletRequest request){
        TblDepartmentModel departmentModel = new TblDepartmentModel();
        departmentModel.setName(q);
        List<TblDepartmentInfo> codeList = departmentService.findDepartment(q);
        return codeList;
    }
    
    /**
     * 
     * @Function: getPermissionItem
     * @Description: 模糊查询许可事项
     *
     * @param q
     * @param request
     * @return
     *
     * @author: mixue
     * @date: 2019年3月2日 下午3:23:01
     */
    @ResponseBody
    @RequestMapping("/getPermissionItem.action")
    public List<PermissionItem> getPermissionItem(String q,HttpServletRequest request){
        PermissionItemModel permissionItemModel = new PermissionItemModel();
        permissionItemModel.setXkSxmc(q);
        List<PermissionItem> codeList = permissionItemService.findPermissionItem(q);
        return codeList;
    }
    
    /**
     * 
     * @Function: getPermissionItemByOneself
     * @Description: 获取本单位许可事项
     *
     * @param request
     * @return
     *
     * @author: mixue
     * @date: 2019年3月4日 上午11:39:56
     */
    @ResponseBody
    @RequestMapping("getPermissionItemByOneself.action")
    public TeeJson getPermissionItemByOneself(HttpServletRequest request) {
        // 返回前台json
        TeeJson json = new TeeJson();
        List<PermissionItemModel> permissionItemModels = new ArrayList<>();
        try {
            PermissionItemModel permissionItemModel = new PermissionItemModel();
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
            permissionItemModels = permissionItemService.getPermissionItemByOneself(permissionItemModel);
            json.setRtData(permissionItemModels);
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
     * @Function: getPermissionListById
     * @Description: 通过ID获取单个许可清单
     *
     * @param id
     * @return
     *
     * @author: mixue
     * @date: 2019年2月26日 上午1:14:21
     */
    @ResponseBody
    @RequestMapping("getPermissionListById.action")
    public TeeJson getPermissionListById(String id) {
        TeeJson json = new TeeJson();
        try {
            PermissionListModel permissionListModel = permissionListService.getPermissionListById(id);
            json.setRtData(permissionListModel);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }
}
