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
import com.beidasoft.zfjd.permission.model.PermissionListModel;
import com.beidasoft.zfjd.permission.service.PermissionListService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

/**
 * @ClassName: PermissionListSearchController.java
 * @Description: 许可清单查询CONTROLLER层
 *
 * @author: mixue
 * @date: 2019年3月2日 上午11:27:50
 */
@Controller
@RequestMapping("permissionListSearchCtrl")
public class PermissionListSearchController {

    @Autowired
    private PermissionListService permissionListService;
    
    @Autowired
    private CommonService commonService;
    
    /**
     * 
     * @Function: findListByPageRoles
     * @Description: 许可清单分页查询（数据权限）
     *
     * @param dataGridModel
     * @param permissionListModel
     * @param request
     * @return
     *
     * @author: mixue
     * @date: 2019年3月4日 上午11:40:37
     */
    @ResponseBody
    @RequestMapping("findListBypageRoles.action")
    public TeeEasyuiDataGridJson findListByPageRoles(TeeDataGridModel dataGridModel, PermissionListModel permissionListModel, HttpServletRequest request) {
        // 返回前台json
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        List<PermissionListModel> permissionListModels = new ArrayList<>();
        Long count = null;
        try {
            OrgCtrlInfoModel orgCtrlInfoModel = commonService.getOrgCtrlInfo(request);
            permissionListModels = permissionListService.findListByPageRoles(dataGridModel, permissionListModel, orgCtrlInfoModel);
            count = permissionListService.findListCountByPageRoles(permissionListModel, orgCtrlInfoModel);
            json.setRows(permissionListModels);
            json.setTotal(count);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }
}
