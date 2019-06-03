package com.beidasoft.zfjd.inspection.inspList.controller;

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

import com.beidasoft.zfjd.inspection.inspList.bean.InspListBase;
import com.beidasoft.zfjd.inspection.inspList.bean.InspListItem;
import com.beidasoft.zfjd.inspection.inspList.model.InspListBaseModel;
import com.beidasoft.zfjd.inspection.inspList.service.InspListBaseService;
import com.beidasoft.zfjd.inspection.inspModule.bean.InspModule;
import com.beidasoft.zfjd.inspection.inspModule.service.InspModuleService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 执法检查CONTROLLER类
 */
@Controller
@RequestMapping("inspListBaseCtrl")
public class InspListBaseController {

    @Autowired
    private InspListBaseService inspListBaseService;
    
    @Autowired
    private InspModuleService inspModuleService;
    /**
     * 保存执法检查数据
     *
     * @param model
     * @param request
     * @return
     */

    @ResponseBody
    @RequestMapping("/saveDocInfo")
    public TeeJson saveDocInfo(InspListBaseModel model, HttpServletRequest request) {

        TeeJson json = new TeeJson();
//        TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        InspListBase beanInfo = new InspListBase();

        // model-->实体类传值
        BeanUtils.copyProperties(model, beanInfo);
        json.setRtData(beanInfo);
        json.setRtState(true);
        return json;
    }

    /**
     * 获取执法检查单数据
     *
     * @param inspectitemModel
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/inspListByPage.action")
    public TeeEasyuiDataGridJson inspListByPage(TeeDataGridModel dm, InspListBaseModel inspListBaseModel,
            HttpServletRequest request) throws Exception {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        try {
            List<InspListBaseModel> inspListModels = new ArrayList<InspListBaseModel>();
            // 获取检查项列表
            List<InspListBase> inspLists = inspListBaseService.listByPage(dm, inspListBaseModel);
            Long total = inspListBaseService.listCount(inspListBaseModel);

            // 返回数据model处理
            InspListBaseModel rtModel = null;
            if (inspLists != null) {
                for (int i = 0; i < inspLists.size(); i++) {
                    rtModel = new InspListBaseModel();
                    InspListBase temp = inspLists.get(i);
                    BeanUtils.copyProperties(temp, rtModel);
                    //设置所属领域
                    String orgSysName = TeeSysCodeManager.getChildSysCodeNameCodeNo("LAW_ENFORCEMENT_FIELD",
                            temp.getOrgSys());
                    rtModel.setOrgSysName(orgSysName);
                    // 设置检查单层级名称
                    String applyHierarchy = TeeSysCodeManager.getChildSysCodeNameCodeNo("INSP_LIST_LEVEL",
                            temp.getApplyHierarchy());
                    rtModel.setApplyHierarchyStr(applyHierarchy);
                    // 设置检查单层级名称
                    String listClassify = TeeSysCodeManager.getChildSysCodeNameCodeNo("INSP_LIST_TYPE",
                            temp.getListClassify());
                    rtModel.setListClassifyStr(listClassify);
                    inspListModels.add(rtModel);
                }
            }
            gridJson.setRows(inspListModels);
            gridJson.setTotal(total);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return gridJson;
    }

    /**
     * 获取执法检查单数据
     *
     * @param inspectitemModel
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getValidInspLists.action")
    public TeeJson getValidInspLists(InspListBaseModel inspListBaseModel, HttpServletRequest request) throws Exception {
//        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        TeeJson json = new TeeJson();
        try {
            // 获取检查项列表
            List<InspListBase> inspLists = inspListBaseService.getValidInspLists(inspListBaseModel);

            json.setRtState(true);
            json.setRtData(inspLists);
        } catch (Exception e) {
            // TODO: handle exception
            json.setRtState(false);
            System.out.println(e.getMessage());
        }
        return json;
    }

    /**
     * 打开检查项编辑页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/listInputPage")
    public void listInputPage(InspListBaseModel inspListBaseModel, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            // 获取所属执法系统
            List<Map<String, Object>> orgSysList = new ArrayList<Map<String, Object>>();
            if (inspListBaseModel.getOrgSys() != null) {
                String[] orgSysBuff = inspListBaseModel.getOrgSys().split(",");
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
            request.setAttribute("basicInfo", inspListBaseModel);
            request.getRequestDispatcher("/supervise/inspection/inspList/inspectionList_input.jsp").forward(request,
                    response);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping("save")
    public TeeJson save(InspListBaseModel inspListBaseModel) {
        TeeJson json = new TeeJson();

        // 创建实例化实体类对象
        InspListBase list = new InspListBase();
        if (inspListBaseModel.getId() != null && !"".equals(inspListBaseModel.getId())&& !"0".equals(inspListBaseModel.getId())) {
            // 已存在数据，进行更新
            list = inspListBaseService.getById(inspListBaseModel.getId());
            if (list != null) {
                list.setOrgSys(inspListBaseModel.getOrgSys());
                // list.setModuleId(inspListBaseModel.getModuleId());
                list.setListName(inspListBaseModel.getListName());
                list.setCreateType(inspListBaseModel.getCtrlType());
                inspListBaseService.save(list);
                // 保存检查单关联的检查模块与检查项信息
                String[] moduleIds = inspListBaseModel.getModuleIdsStr().split(",");
                inspListBaseService.saveInspModuleAndItem(list, moduleIds);
            }
        } else {
            // 新数据，需要新增
            BeanUtils.copyProperties(inspListBaseModel, list);
            list.setId(UUID.randomUUID().toString());
            list.setCreateOrganizationId(inspListBaseModel.getLoginDeptId());
            list.setCreateSubjectId(inspListBaseModel.getLoginSubId());
            list.setListName(inspListBaseModel.getListName());
            list.setIsDelete(0);
            list.setCurrentState(0);
            list.setCreateDate(new Date());
            list.setCreateType(inspListBaseModel.getCtrlType());
            inspListBaseService.save(list);
            // 保存检查单关联的检查模块与检查项信息
            String[] moduleIds = inspListBaseModel.getModuleIdsStr().split(",");
            inspListBaseService.saveInspModuleAndItem(list, moduleIds);
        }

        json.setRtState(true);
        return json;
    }

    /**
     * 根据执法检查单模版id获取所有执法检查项信息
     *
     * @param id
     * @param request
     * @return
     */

    @ResponseBody
    @RequestMapping("/getRelatedItems.action")
    public TeeJson getRelatedItems(InspListBaseModel inspListBaseModel, HttpServletRequest request) {
        TeeJson json = new TeeJson();
        try {
            List<InspListItem> itemList = inspListBaseService.getRelatedItemsByListId(inspListBaseModel);
            // list转jsonArray
            // JsonConfig jsonConfig = new JsonConfig();
            // JSONArray moduleJSONArray = JSONArray.fromObject(itemList,
            // jsonConfig);
            json.setRtData(itemList);
            json.setRtState(true);
            
        } catch (Exception e) {
            // TODO: handle exception
            json.setRtState(false);
            System.out.println(e.getMessage());
        }
        return json;
    }
    /**
     * @author lrn
     * @description 通过id获取详情
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getById.action")
    public TeeJson getById(String id){
    	TeeJson json = new TeeJson();
    	try {
    		InspListBaseModel list = inspListBaseService.getListById(id);
    		String orgSysName = TeeSysCodeManager.getChildSysCodeNameCodeNo("LAW_ENFORCEMENT_FIELD",
                    list.getOrgSys());
    		list.setOrgSysName(orgSysName);
    		String applyHierarchyStr = TeeSysCodeManager.getChildSysCodeNameCodeNo("INSP_LIST_LEVEL",
                    list.getApplyHierarchy());
    		list.setApplyHierarchyStr(applyHierarchyStr);
    		String listClassifyStr = TeeSysCodeManager.getChildSysCodeNameCodeNo("INSP_LIST_TYPE",
                    list.getApplyHierarchy());
    		list.setListClassifyStr(listClassifyStr);
            json.setRtData(list);
            json.setRtState(true);
            
        } catch (Exception e) {
            // TODO: handle exception
            json.setRtState(false);
            System.out.println(e.getMessage());
        }
    	return json;
    }
    /**
     * @author lrn
     * @description 数据库彻底删除检查单模版
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("dataBaseDelete.action")
    public TeeJson dataBaseListDelete(String id){
        TeeJson rtInfo = new TeeJson();
        //删除未提交的检查单模版
        Boolean flag = inspListBaseService.dataBaseListDelete(id);
        rtInfo.setRtState(flag);
        return rtInfo;
    }
    /**
     * @author lrn
     * @description 修改检查单模版状态
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("updateListState.action")
    public TeeJson updateListState(HttpServletRequest request) {
        TeeJson rtInfo = new TeeJson();
        String id = TeeStringUtil.getString(request.getParameter("id"), "");
        Integer currentState = TeeStringUtil.getInteger(request.getParameter("currentState"), 1);
        Boolean flag = inspListBaseService.updateListState(id, currentState);
        rtInfo.setRtState(flag);
        return rtInfo;
    }
    /**
     * @author lrn
     * @description 批量删除检查单模版
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("inspListDel.action")
    public TeeJson inspListDel(HttpServletRequest request) {
        TeeJson rtInfo = new TeeJson();
        String ids = TeeStringUtil.getString(request.getParameter("id"), "");
        Integer isDelete = TeeStringUtil.getInteger(request.getParameter("isDelete"), 1);
        Boolean flag = inspListBaseService.inspListDel(ids, isDelete);
        rtInfo.setRtState(flag);
        return rtInfo;
    }
    /**
     * @author lrn
     * @description 对选中id进行权限控制
     * @param inspListBaseModel
     * @return
     */
    @ResponseBody
    @RequestMapping("idsCtrl.action")
    public TeeJson idsCtrl(InspListBaseModel inspListBaseModel){
        TeeJson rtInfo = new TeeJson();
        Boolean flag = inspListBaseService.idsCtrl(inspListBaseModel);
        rtInfo.setRtState(flag);
        return rtInfo;
    }
}
