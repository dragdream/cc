package com.beidasoft.zfjd.permission.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.common.service.CommonService;
import com.beidasoft.zfjd.permission.model.PermissionItemModel;
import com.beidasoft.zfjd.permission.service.PermissionItemService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * @ClassName: PermissionItemSearchController.java
 * @Description: 许可事项查询CONTROLLER层
 *
 * @author: mixue
 * @date: 2019年3月2日 上午11:27:36
 */
@Controller
@RequestMapping("permissionItemSearchCtrl")
public class PermissionItemSearchController {
    
    @Autowired
    private PermissionItemService permissionItemService;
    
    @Autowired
    private CommonService commonService;
    
    
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
    public TeeEasyuiDataGridJson findListByPage(TeeDataGridModel dataGridModel, PermissionItemModel permissionItemModel, HttpServletRequest request) {
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
     * @Description: 许可事项分页查询（数据权限）
     *
     * @param dataGridModel
     * @param permissionItemModel
     * @param request
     * @return
     *
     * @author: mixue
     * @date: 2019年3月2日 下午6:35:53
     */
    @ResponseBody
    @RequestMapping("findListBypageRoles.action")
    public TeeEasyuiDataGridJson findListByPageRoles(TeeDataGridModel dataGridModel, PermissionItemModel permissionItemModel, HttpServletRequest request) {
        // 返回前台json 
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        List<PermissionItemModel> permissionItemModels = new ArrayList<>();
        Long count = null;
        try {
            OrgCtrlInfoModel orgCtrlInfoModel = commonService.getOrgCtrlInfo(request);
            permissionItemModels = permissionItemService.findListByPageRoles(dataGridModel, permissionItemModel, orgCtrlInfoModel);
            count = permissionItemService.findListCountByPageRoles(permissionItemModel, orgCtrlInfoModel);
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
            e.printStackTrace();
        }
        return json;
    }
    
}
