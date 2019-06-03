package com.beidasoft.zfjd.lawManage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.common.service.CommonService;
import com.beidasoft.zfjd.law.bean.TblLawInfo;
import com.beidasoft.zfjd.law.model.TblLawModel;
import com.beidasoft.zfjd.law.service.TblLawService;
import com.beidasoft.zfjd.lawManage.bean.LawAdjustReport;
import com.beidasoft.zfjd.lawManage.model.LawAdjustReportModel;
import com.beidasoft.zfjd.lawManage.service.LawAdjustReportService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("lawAdjustReportCtrl")
public class LawAdjustReportController {

	@Autowired
	private LawAdjustReportService lawAdjustReportService;
	
	@Autowired
	private CommonService commonservice;
	
	@Autowired
	private TblLawService tblLawService;
	
	@Autowired
	private TeeAttachmentService teeAttachmentService;
	/**
	 * 分页
	 * @param dataGridModel
	 * @param queryModel
	 * @return
	 */
	@ResponseBody
    @RequestMapping("listByPage")
    public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,LawAdjustReportModel lawAdjustReportModel,
    		HttpServletRequest request){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		//获取用户信息数据控制
		OrgCtrlInfoModel orgCtrlInfoModel = commonservice.getOrgCtrlInfo(request);
		List<LawAdjustReportModel> lawModelList = new ArrayList<LawAdjustReportModel>();
		long total = lawAdjustReportService.getTotal(lawAdjustReportModel, orgCtrlInfoModel);
		List<LawAdjustReport> lawInfos = lawAdjustReportService.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),
				lawAdjustReportModel, orgCtrlInfoModel);
		for (LawAdjustReport lawInfo : lawInfos) {
			LawAdjustReportModel infoModel = new LawAdjustReportModel();
			BeanUtils.copyProperties(lawInfo, infoModel);
			infoModel.setSubmitlawLevel(TeeSysCodeManager.getChildSysCodeNameCodeNo("LAW_TYPE", lawInfo.getSubmitlawLevel()));
//			//infoModel.setExamineStr(Integer.toString(userInfo.getExamine()));
			lawModelList.add(infoModel);
//
		}

		dataGridJson.setTotal(total);
		dataGridJson.setRows(lawModelList);
		
		return dataGridJson;

	}
	
	/**
	 * 根据名称获取法律列表
	 * @param dataGridModel
	 * @param queryModel
	 * @return
	 */
	@ResponseBody
    @RequestMapping("getLawsByName")
    public List<TblLawInfo> getLawsByName(String q, Integer initLawFlag, String lawId, HttpServletRequest request){
		List<TblLawInfo> lawLists = new ArrayList<>();
		boolean initFlag = false;
		//判断是否初始化加载
		if(initLawFlag != null && initLawFlag == 1){
			initFlag = true;
		}
		if(initFlag){
			//获取指定id的法律法规
			TblLawInfo lawInfo = tblLawService.getById(lawId);
			lawLists.add(lawInfo);
		}else{
		//获取名称匹配的法律法规
			TblLawModel lawBaseModel = new TblLawModel();
			lawBaseModel.setName(q);
			lawLists = tblLawService.listByPage(0, 10, lawBaseModel);
		}
		return lawLists;

	}
	
	/**
	 * 保存法律法规调整信息
	 * @param lawModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("save")
	public TeeJson save(LawAdjustReportModel lawAdjustReportModel,String attaches, HttpServletRequest request){
		TeeJson json = new TeeJson();
		//获取用户信息数据控制
		OrgCtrlInfoModel orgCtrlInfoModel = commonservice.getOrgCtrlInfo(request);
		//创建实例化实体类对象
		LawAdjustReport lawAdjustReport = new LawAdjustReport();
		BeanUtils.copyProperties(lawAdjustReportModel, lawAdjustReport);
		//单独处理日期
		if(lawAdjustReportModel.getPromulgationStr() != null){
			lawAdjustReport.setPromulgation(TeeDateUtil.format(lawAdjustReportModel.getPromulgationStr(), "yyyy-MM-dd"));
		}
		if(lawAdjustReportModel.getImplementationStr() != null){
			lawAdjustReport.setImplementation(TeeDateUtil.format(lawAdjustReportModel.getImplementationStr(), "yyyy-MM-dd"));
		}
		if(lawAdjustReportModel.getId() != null && !"".equals(lawAdjustReportModel.getId())){
			lawAdjustReportService.updateReportInfo(lawAdjustReport);
		}else{
			//设置数据标识lawAdjustReport
			lawAdjustReport.setId(UUID.randomUUID().toString());
			lawAdjustReport.setIsDelete(0);
			lawAdjustReport.setExamine(1);
			lawAdjustReport.setIsBack(0);
			if(orgCtrlInfoModel.getDepartId() != null){
				lawAdjustReport.setCreateDeptId(orgCtrlInfoModel.getDepartId());
			}
			if(orgCtrlInfoModel.getSubjectId() != null){
				lawAdjustReport.setCreateSubjectId(orgCtrlInfoModel.getSubjectId());
			}
			if(orgCtrlInfoModel.getSupDeptId() != null){
				lawAdjustReport.setCreateSupDeptId(orgCtrlInfoModel.getSupDeptId());
			}
			lawAdjustReport.setCreateDate(new Date());
			lawAdjustReportService.save(lawAdjustReport);
		}
		if(!TeeUtility.isNullorEmpty(attaches)){
			//处理随带的附件
			String sp[] = attaches.split(",");
			for(String attachId:sp){
				TeeAttachment attachment = teeAttachmentService.getById(Integer.parseInt(attachId));
				attachment.setModelId(lawAdjustReport.getId()+"");
				teeAttachmentService.updateAttachment(attachment);
			}
		}
		json.setRtState(true);
		return json;
	}

	/**
	 * 保存法律法规调整信息
	 * @param lawModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("submitReport")
	public TeeJson submitReport(LawAdjustReportModel lawAdjustReportModel, HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
			String submitIds = lawAdjustReportModel.getSubmitIds();
			if(submitIds != null && !"".equals(submitIds)){
				String [] idGroup = submitIds.split(",");
				lawAdjustReportService.updateToSubmitByGroup(idGroup);
			}
			json.setRtState(true);
			return json;
		}catch(Exception e){
			json.setRtState(false);
			return json;
		}
	}
	
	   /**
     * 保存法律法规调整信息
     * @param lawModel
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteAdjust")
    public TeeJson deleteAdjust(LawAdjustReport lawAdjustReport, HttpServletRequest request){
        TeeJson json = new TeeJson();
        try {
            if(lawAdjustReport.getId() != null && !"".equals(lawAdjustReport.getId())){
                lawAdjustReportService.deleteById(lawAdjustReport);
            }
            json.setRtState(true);
            return json;
        }catch(Exception e){
            json.setRtState(false);
            return json;
        }
    }
    
	/**
	 * 打开法律法规调整申请页面
	 * @param lawModel
	 * @return
	 */
    @RequestMapping("openLawAdjustInput")
    public void measuresInput(LawAdjustReportModel lawAdjustReportModel, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
        	String dataId = lawAdjustReportModel.getId();
        	LawAdjustReportModel lawAdjsutModel = new LawAdjustReportModel();
            if (dataId !=null && !"".equals(dataId)) {
                // 案件来源为其他来源，则获取强制案件基础表id
                LawAdjustReport lawAdjust = lawAdjustReportService.getById(dataId);
                BeanUtils.copyProperties(lawAdjust, lawAdjsutModel);
                //
                if(lawAdjust.getImplementation() != null){
                	lawAdjsutModel.setImplementationStr(TeeDateUtil.format(lawAdjust.getImplementation(), "yyyy-MM-dd"));
                }
                //
                if(lawAdjust.getPromulgation() != null){
                	lawAdjsutModel.setPromulgationStr(TeeDateUtil.format(lawAdjust.getPromulgation(), "yyyy-MM-dd"));
                }
            }
            request.setAttribute("lawAdjust", lawAdjsutModel);
            request.getRequestDispatcher("/supervise/lawManage/lawAdjustReport/lawAdjustReport_input.jsp").forward(request,
                    response);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }
    
	/**
	 * 获取文件列表
	 * @param lawModel
	 * @return
	 */
	@ResponseBody
    @RequestMapping("getFilelistById")
    public TeeJson getFilelistById(String id) throws Exception {
    	TeeJson json = new TeeJson();
    	try{
    		List<TeeAttachmentModel> attachModels = teeAttachmentService.getAttacheModels("lawInfo", id+"");
			json.setRtState(true);
			json.setRtData(attachModels);
			return json;
    	} catch (Exception e) {
                // TODO: handle exception
    			json.setRtState(false);
    			 System.out.println(e.getMessage());
    			return json;
            }
        }
}
