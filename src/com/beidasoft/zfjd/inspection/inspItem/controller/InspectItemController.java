package com.beidasoft.zfjd.inspection.inspItem.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.inspection.inspItem.bean.InspectItem;
import com.beidasoft.zfjd.inspection.inspItem.model.InspectItemModel;
import com.beidasoft.zfjd.inspection.inspItem.service.InspectItemService;
import com.beidasoft.zfjd.inspection.inspList.model.InspListBaseModel;
import com.beidasoft.zfjd.inspection.inspModule.bean.InspModule;
import com.beidasoft.zfjd.inspection.inspModule.service.InspModuleService;
import com.beidasoft.zfjd.system.service.DepartmentService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 职权基础信息表CONTROLLER类
 */
@Controller
@RequestMapping("/inspectitemCtrl")
public class InspectItemController {

    @Autowired
    private InspectItemService inspectitemService;

    @Autowired
    private InspModuleService inspectionService;

    @Autowired
    DepartmentService departmentService;

    /**
     * 检查项列表生成
     *
     * @param model
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("caseListByPage")
    public TeeEasyuiDataGridJson caseListByPage(TeeDataGridModel dm, InspectItemModel inspectitemModel,
            HttpServletRequest request) throws Exception {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        try {
            List<InspectItemModel> inspItemModels = new ArrayList<InspectItemModel>();
            // 获取当前用户数据权限组
            // TeePerson personTemp = (TeePerson)
            // request.getSession().getAttribute(TeeConst.LOGIN_USER);
            // TeePerson person =
            // personService.selectByUuid(personTemp.getUuid());
            //
            // List<TeeMenuGroup> menuGroupList = person.getMenuGroups();
            // String ctrlType = "";
            // boolean isMain = false;
            // boolean isSub = false;
            // for (TeeMenuGroup menuGroup : menuGroupList) {
            // if ("执法检查主管理".equals(menuGroup.getMenuGroupName())) {
            // isMain = true;
            // }
            // if ("执法检查子管理".equals(menuGroup.getMenuGroupName())) {
            // isSub = true;
            // }
            // }
            // if (isMain) {
            // ctrlType = "10";
            // } else {
            // if (isSub) {
            // ctrlType = "02";
            // }
            // }
            // inspectitemModel.setCreateType(ctrlType);
            // // 根据businessDeptId，找到机构对象
            // TblDepartmentInfo departmentInfo =
            // departmentService.getById(inspectitemModel.getLoginDeptId());
            //
            // // 获取执法系统code，逗号分隔形式01,02,03
            // String orgSys = departmentInfo.getOrgSys();
            // 获取检查项列表
            List<InspectItem> inspItems = inspectitemService.listByPage(dm, inspectitemModel);
            Long total = inspectitemService.listCount(inspectitemModel);

            // 返回数据model处理
            InspectItemModel rtModel = null;
            if (inspItems != null) {
                for (int i = 0; i < inspItems.size(); i++) {
                    rtModel = new InspectItemModel();
                    InspectItem temp = inspItems.get(i);
                    BeanUtils.copyProperties(temp, rtModel);
                    //设置检查模块名称
                    InspModule module = inspectionService.getById(temp.getModuleId());
                    rtModel.setModuleName(module.getModuleName());
                    //设置执法系统名称
                    String orgSysName = TeeSysCodeManager.getChildSysCodeNameCodeNo("LAW_ENFORCEMENT_FIELD",
                            temp.getOrgSys());
                    rtModel.setOrgSysName(orgSysName);
                    inspItemModels.add(rtModel);
                }
            }
            gridJson.setRows(inspItemModels);
            gridJson.setTotal(total);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return gridJson;
    }
    /**
     * 通过id获取检查项
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/itemById")
    public TeeJson itemById(String id){
    	TeeJson json = new TeeJson();
        // 创建实例化实体类对象
        InspectItem item = new InspectItem();
        
    	item = inspectitemService.getById(id);
    	json.setRtData(item);
    	json.setRtState(true);
    	System.out.println(json);
        return json;
    }
    
    /**
     * 打开检查项编辑页面(基本没什么用了)
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/itemInputPage")
    public void itemInputPage(InspectItemModel inspectitemModel, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            // 获取所属执法系统
            List<Map<String, Object>> orgSysList = new ArrayList<Map<String, Object>>();
            if (inspectitemModel.getOrgSys() != null) {
                String[] orgSysBuff = inspectitemModel.getOrgSys().split(",");
                if (orgSysBuff != null && orgSysBuff.length > 0) {
                    for (String orgSysCode : orgSysBuff) {
                        String orgSysName = TeeSysCodeManager.getChildSysCodeNameCodeNo("LAW_ENFORCEMENT_FIELD",
                                orgSysCode);
                        Map<String, Object> code = new HashMap<String, Object>();
                        code.put("codeNo", orgSysCode);
                        code.put("codeName", orgSysName);
                        orgSysList.add(code);
                    }
                }
            }
            request.setAttribute("orgSysList", orgSysList);
            request.setAttribute("basicInfo", inspectitemModel);
            request.getRequestDispatcher("/supervise/inspection/inspItem/inspection_item_input.jsp").forward(request,
                    response);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    /**
     * 保存检查项信息
     *
     * @param model
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("save")
    public TeeJson save(InspectItemModel itemModel) {
        TeeJson json = new TeeJson();

        // 创建实例化实体类对象
        InspectItem item = new InspectItem();
        if (!"0".equals(itemModel.getId()) && !"".equals(itemModel.getId())&&itemModel.getId() != null) {
            // 已存在数据，进行更新
            item = inspectitemService.getById(itemModel.getId());
            if (item != null) {
                item.setOrgSys(itemModel.getOrgSys());
                item.setModuleId(itemModel.getModuleId());
                item.setItemName(itemModel.getItemName());
                item.setCreateType(itemModel.getCtrlType());
                inspectitemService.save(item);
            }
        } else {
            // 新数据，需要新增
            BeanUtils.copyProperties(itemModel, item);
            item.setId(UUID.randomUUID().toString());
            item.setCreateOrganizationId(itemModel.getLoginDeptId());
            item.setCreateSubjectId(itemModel.getLoginSubId());
            item.setIsDelete(0);
            item.setCreateTime(new Date());
            item.setCreateType(itemModel.getCtrlType());
            inspectitemService.save(item);
        }
        
        json.setRtState(true);
        return json;
    }
    
    /**
     * @author lrn
     * @description 通过id删除执法检查项
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("inspItemDel.action")
    public TeeJson inspListDel(InspectItemModel inspItemModel,HttpServletRequest request) {
        TeeJson rtInfo = new TeeJson();
        Boolean flag = inspectitemService.inspListDel(inspItemModel);
        rtInfo.setRtState(flag);
        return rtInfo;
    }
    /**
     * @author lrn
     * @description 通过id控制权限
     * @param inspectItemModel
     * @return
     */
    @ResponseBody
    @RequestMapping("idsCtrl.action")
    public TeeJson idsCtrl(InspectItemModel inspectItemModel){
        TeeJson rtInfo = new TeeJson();
        Boolean flag = inspectitemService.idsCtrl(inspectItemModel);
        rtInfo.setRtState(flag);
        return rtInfo;
    }
}
