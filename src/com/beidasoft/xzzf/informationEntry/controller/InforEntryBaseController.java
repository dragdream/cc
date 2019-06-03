package com.beidasoft.xzzf.informationEntry.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.informationEntry.service.InforEntryBaseService;
import com.beidasoft.xzzf.informationEntry.service.InforEntryStaffService;
import com.beidasoft.xzzf.informationEntry.model.InforEntryBaseModel;
import com.beidasoft.xzzf.informationEntry.bean.InforEntryBase;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;



/**
 * 历史信息录入主表CONTROLLER类
 */
@Controller
@RequestMapping("inforEntryBaseCtrl")
public class InforEntryBaseController {

    @Autowired
    private InforEntryBaseService inforEntryBaseService;
    @Autowired
    private InforEntryStaffService inforEntryStaffService;

    /**
     * 保存历史信息录入主表数据
     *
     * @param model
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/saveDocInfo")
    public TeeJson saveDocInfo(InforEntryBaseModel model, HttpServletRequest request) {
    	TeeJson json = inforEntryBaseService.save(model, request);
        return json;
    }
    /**
     * 获取历史信息录入主表数据
     *
     * @param id
     * @param request
     * @return 
     */
    @ResponseBody
    @RequestMapping("/getDocInfo")
    public TeeJson getDocInfo(String id, HttpServletRequest request) {
        TeeJson json = inforEntryBaseService.getDocInfo(id, request);
        return json;
    }
    
    /**
     * 分页查询
     * @param dataGridModel
     * @param entryBase
     * @return
     */
    @ResponseBody
    @RequestMapping("/listByPage")
    public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,InforEntryBaseModel baseModel,HttpServletRequest request){
    	TeeEasyuiDataGridJson json = inforEntryBaseService.listByPage(dataGridModel, baseModel, request);
    	return json;
    }
    
    /**
     * 加载子页面
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/inforEntryAddGrading")
    public void inforEntryAddGrading(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            //一般案件model
        	InforEntryBaseModel baseModel = new InforEntryBaseModel();
            String caseId = TeeStringUtil.getString(request.getParameter("caseId"), "");
            if(caseId != null && !"".equals(caseId)) {
                request.setAttribute("caseId", caseId);
            }
            baseModel.setId(caseId);
            
            InforEntryBase cBase = new InforEntryBase();
            //点击保存事件标志
            String saveEvent = TeeStringUtil.getString(request.getParameter("saveEvent"), "");
            int editFlag = TeeStringUtil.getInteger(request.getParameter("editFlag"), 0);
            request.setAttribute("editFlag", editFlag);
            if ("01".equals(saveEvent) || editFlag == 2 || editFlag == 3) {
                //当点击保存时，修改时，查询案件
                //通过ID查询一般案件信息
                //cBase = caseCommonBaseService.findCaseCommonBaseById(cbModel);
            }
            //案件阶段
            String grading = TeeStringUtil.getString(request.getParameter("grading"), "");
            if (grading != null && !"".equals(grading) && cBase.getId() != null && !"".equals(cBase.getId())) {
//                cbModel.setGrading(grading);
//                cbModel = commonModel(cBase, cbModel);
            }
            //跳转路径
            String pageUrl = TeeStringUtil.getString(request.getParameter("pageUrl"), "");
            if (pageUrl == null || "".equals(pageUrl)) {
                //跳转到错误页面
                pageUrl = "";
            }
            
//            if (cbModel.getIsNext() != null) {
//                request.setAttribute("isNext", cbModel.getIsNext());
//            }else {
//                request.setAttribute("isNext", 0);  
//            }
//            request.setAttribute("cbModel", cbModel);
            request.getRequestDispatcher(pageUrl).forward(request, response);
        }catch (Exception e) {
        }
    }
    
    /**
     * 跳转新增页面
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/inforEntryBaseAdd")
    public void inforEntryBaseAdd(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
        	InforEntryBaseModel baseModel = new InforEntryBaseModel();
            if(request.getParameter("id") == null || "".equals(request.getParameter("id"))) {
                String id = UUID.randomUUID().toString();
                request.setAttribute("id", id);
            }else {
                request.setAttribute("id", request.getParameter("id"));
                baseModel.setId(request.getParameter("id"));
                InforEntryBase base = inforEntryBaseService.getById(request.getParameter("id"));
                if (base.getIsNext() != null) {
                    request.setAttribute("isNext", base.getIsNext());
                }else {
                    request.setAttribute("isNext", 0);  
                }
            }
            request.setAttribute("editFlag", TeeStringUtil.getInteger(request.getParameter("editFlag"), 0));
            request.getRequestDispatcher("/platform/informationEntry/case/information_entry_add.jsp").forward(request, response);
        }catch (Exception e) {
        }
    }
    
    /**
     * 逻辑删除
     * @param dataGridModel
     * @param entryBase
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateDelete")
    public TeeJson updateDelete(String caseId,HttpServletRequest request){
    	TeeJson json = inforEntryBaseService.updateDelete(caseId, request);
    	return json;
    }
    
}
