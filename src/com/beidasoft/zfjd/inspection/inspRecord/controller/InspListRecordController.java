package com.beidasoft.zfjd.inspection.inspRecord.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.beidasoft.zfjd.inspection.inspList.model.InspListBaseModel;
import com.beidasoft.zfjd.inspection.inspRecord.bean.InspRecordDetail;
import com.beidasoft.zfjd.inspection.inspRecord.bean.InspRecordMain;
import com.beidasoft.zfjd.inspection.inspRecord.model.InspRecordMainModel;
import com.beidasoft.zfjd.inspection.inspRecord.service.InspListRecordService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 执法检查CONTROLLER类
 */
@Controller
@RequestMapping("inspListRecordCtrl")
public class InspListRecordController {

//    @Autowired
//    private InspListBaseService inspListBaseService;

    @Autowired
    private InspListRecordService inspListRecordService;

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
    @RequestMapping("/inspListRecordByPage.action")
    public TeeEasyuiDataGridJson inspListRecordByPage(TeeDataGridModel dm, InspRecordMainModel inspRecordMainModel,
            HttpServletRequest request) throws Exception {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        try {
            List<InspRecordMainModel> rtModels = new ArrayList<InspRecordMainModel>();
            // 获取检查单列表
            List<InspRecordMain> inspLists = inspListRecordService.listByPage(dm, inspRecordMainModel);
            Long total = inspListRecordService.listCount(inspRecordMainModel);
            // 返回数据model处理
            if (inspLists != null) {
                for (int i = 0; i < inspLists.size(); i++) {
                    InspRecordMainModel rtModel = new InspRecordMainModel();
                    InspRecordMain temp = inspLists.get(i);
                    BeanUtils.copyProperties(temp, rtModel);
                    // //设置检查模块名称
                    // Inspection module =
                    // inspectionService.getById(temp.getModuleId());
                    // rtModel.setModuleName(module.getModuleName());
                    // 设置检查日期
                    if (temp.getInspectionDate() != null) {
                        rtModel.setInspectionDateStr(TeeDateUtil.format(temp.getInspectionDate(), "yyyy-MM-dd"));
                    } else {
                        rtModel.setInspectionDateStr("");
                    }
                    rtModels.add(rtModel);

                }
            }
            gridJson.setRows(rtModels);
            gridJson.setTotal(total);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return gridJson;
    }

    /**
     * 打开检查项编辑页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/listRecordInputPage")
    public void listRecordInputPage(InspRecordMainModel inspRecordMainModel, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            if (inspRecordMainModel.getId() != null && !"".equals(inspRecordMainModel.getId())) {
                InspRecordMain recordMain = inspListRecordService.getById(inspRecordMainModel.getId());
                InspRecordMainModel rtModel = new InspRecordMainModel();
                BeanUtils.copyProperties(recordMain, rtModel);
                if (recordMain.getInspectionDate() != null) {
                    rtModel.setInspectionDateStr(TeeDateUtil.format(recordMain.getInspectionDate(), "yyyy-MM-dd"));
                } else {
                    rtModel.setInspectionDateStr("");
                }
                request.setAttribute("inspRecordInfo", rtModel);
            }
            request.setAttribute("basicInfo", inspRecordMainModel);
            request.getRequestDispatcher("/supervise/inspection/inspRecord/inspectionRecord_input.jsp").forward(request,
                    response);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }
    /**
     * @author lrn
     * @description 通过id获取详情
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getById")
    public TeeJson getById(String id){
        TeeJson json = new TeeJson();
        InspRecordMainModel recordMainModel = null;
        try {
            recordMainModel = inspListRecordService.getByRecordId(id);
            json.setRtState(true);
        } catch (Exception e) {
        }
        json.setRtData(recordMainModel);
        return json;
    }
    
                            
    /**
     * 选择本次检查使用的检查项
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/chocieInspItemPage")
    public void chocieInspItemPage(InspListBaseModel inspListBaseModel, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            request.setAttribute("basicInfo", inspListBaseModel);

            request.getRequestDispatcher("/supervise/inspection/inspRecord/choice_item.jsp").forward(request, response);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    /**
     * 保存检查记录信息
     *
     * @param model
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("saveRecordInfo")
    public TeeJson saveRecordInfo(InspRecordMainModel inspRecordMainModel) {
        TeeJson json = new TeeJson();
        // 创建实例化实体类对象
        InspRecordMain record = new InspRecordMain();
        Date time = new Date();
        if (inspRecordMainModel.getId() != null && !"".equals(inspRecordMainModel.getId())) {
            // 已存在main数据，进行更新
            record.setUpdateDate(new Date());
            record = inspListRecordService.getById(inspRecordMainModel.getId());
            record.setInspectionNumber(inspRecordMainModel.getInspectionNumber());
            String inspectionDateStr = inspRecordMainModel.getInspectionDateStr();
            if (inspectionDateStr != null && !"".equals(inspectionDateStr)) {
                record.setInspectionDate(TeeDateUtil.format(inspectionDateStr, "yyyy-MM-dd"));
            } else {
                record.setInspectionDate(null);
            }
            record.setInspectionAddr(inspRecordMainModel.getInspectionAddr());
            record.setInspectionListId(inspRecordMainModel.getInspectionListId());
            record.setIsInspectionPass(inspRecordMainModel.getIsInspectionPass());
            record.setResultDiscribe(inspRecordMainModel.getResultDiscribe());
            record.setUpdateDate(time);
        } else {
            // 新main数据，需要新增
            BeanUtils.copyProperties(inspRecordMainModel, record);
            record.setId(UUID.randomUUID().toString());
            record.setCreateDate(time);
            record.setSubjectId(inspRecordMainModel.getSubjectId());
            record.setDepartmentId(inspRecordMainModel.getDepartmentId());
            record.setIsDelete(0);
            String inspectionDateStr = inspRecordMainModel.getInspectionDateStr();
            if (inspectionDateStr != null && !"".equals(inspectionDateStr)) {
                record.setInspectionDate(TeeDateUtil.format(inspectionDateStr, "yyyy-MM-dd"));
            } else {
                record.setInspectionDate(null);
            }
        }
        if (record != null) {
            // 保存inspMain信息
            inspListRecordService.save(record);
            // 处理inspDetail数据
            List<InspRecordDetail> details = new ArrayList<InspRecordDetail>();
            if (inspRecordMainModel.getInspItems() != null && inspRecordMainModel.getInspItems().size() > 0) {
                for (Map<?, ?> temp : inspRecordMainModel.getInspItems()) {
                    InspRecordDetail inspItem = new InspRecordDetail();
                    inspItem.setId(UUID.randomUUID().toString());
                    inspItem.setItemSrcType(1);
                    inspItem.setRecordMainId(record.getId());
                    if (temp.get("id") != null) {
                        inspItem.setInspItemId(temp.get("id").toString());
                    }
                    if (!TeeUtility.isNullorEmpty(temp.get("isInspectionPass"))){
                        inspItem.setIsInspectionPass(Integer.parseInt(temp.get("isInspectionPass").toString()));
                    }
                    details.add(inspItem);
                }
            }
            // 保存inspDetail信息
            inspListRecordService.saveInspModuleAndItem(inspRecordMainModel, details);
        }
        json.setRtState(true);
        json.setRtData(record.getId());
        return json;
    }
    /**
     * @author lrn
     * @description 删除检查单(修改isDelete状态）
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/inspRecordDel")
    public TeeJson inspRecordDel(HttpServletRequest request){
        TeeJson json = new TeeJson();
        String id = TeeStringUtil.getString(request.getParameter("id"));
        Integer isDelete = TeeStringUtil.getInteger(request.getParameter("isdetele"), 1);
        try {
            inspListRecordService.inspRecordDel(id,isDelete);
            json.setRtState(true);
        } catch (Exception e) {
        }
        return json;
    }
}
