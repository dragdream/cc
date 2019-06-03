package com.beidasoft.xzzf.punish.document.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.service.CommonService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocInspectionRecord;
import com.beidasoft.xzzf.punish.document.bean.DocPutonrecord;
import com.beidasoft.xzzf.punish.document.bean.DocSiteSanction;
import com.beidasoft.xzzf.punish.document.model.InspectionRecordModel;
import com.beidasoft.xzzf.punish.document.model.PutonrecordModel;
import com.beidasoft.xzzf.punish.document.model.SiteSanctionModel;
import com.beidasoft.xzzf.punish.document.service.InspectionRecordService;
import com.beidasoft.xzzf.punish.document.service.SiteSanctionService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/siteSanctionCtrl")
public class SiteSanctionController {
	
	@Autowired
	private SiteSanctionService siteSanctionService;
	
	@Autowired
	private CommonService commonService;

	@Autowired
	private InspectionRecordService inspectionRecordService;
	
	@Autowired
	private TeeWenShuService wenShuService;
	
	@Autowired
	private PunishFlowService flowService;
	
	/**
	 * 保存当场处罚信息
	 * @param siteSanctionModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson saveDocInfo(SiteSanctionModel siteSanctionModel, HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();

		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		// 实例化实体类对象
		DocSiteSanction docSiteSanction = new DocSiteSanction();
		
		// 属性值传递
		BeanUtils.copyProperties(siteSanctionModel, docSiteSanction);

		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(siteSanctionModel.getLawUnitDateStr())) {
			docSiteSanction.setLawUnitDate(TeeDateUtil.format(siteSanctionModel.getLawUnitDateStr(),
					"yyyy年MM月dd日"));
		}
		
		// 设置创建人相关信息
		if (StringUtils.isBlank(siteSanctionModel.getId())) {
			docSiteSanction.setId(UUID.randomUUID().toString());
			docSiteSanction.setCreateUserName(user.getUserName());
			docSiteSanction.setCreateUserId(user.getUuid()+"");
			docSiteSanction.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建行政处罚决定书（当场处罚）");
			docSiteSanction.setDelFlg("0");
		} else {
			//设置修改人相关信息
			docSiteSanction.setUpdateUserId(user.getUserId());
			docSiteSanction.setUpdateUserName(user.getUserName());
			docSiteSanction.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改行政处罚决定书（当场处罚）");
		}
		
		// 保存 行政处罚决定书（当场处罚）记录表
		siteSanctionService.saveDocInfo(docSiteSanction,request);
		
		json.setRtData(docSiteSanction);
		json.setRtState(true);

		return json;
	}
	/**
	 * 查询当场处罚信息（根据ID）
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		DocSiteSanction docSiteSanction = siteSanctionService.getDocInfo(id);
		SiteSanctionModel siteSanctionModel = new SiteSanctionModel();
		BeanUtils.copyProperties(docSiteSanction, siteSanctionModel);
		// 单独处理时间类型转换
		if (docSiteSanction.getLawUnitDate() != null) {
			siteSanctionModel.setLawUnitDateStr(TeeDateUtil.format(docSiteSanction.getLawUnitDate(),
					"yyyy年MM月dd日"));
		}
		// 返回 行政处罚决定书（当场处罚）记录表 json 对象
		json.setRtData(siteSanctionModel);
		json.setRtState(true);

		return json;
	}

	/**
	 * 当场处罚信息预览
	 * @param siteSanctionModel
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/viewDocInfo")
	@ResponseBody
	public TeeJson viewDocInfo(SiteSanctionModel siteSanctionModel, HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = siteSanctionModel.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId, content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
	/**
	 * 行政处罚决定书（当场处罚）取现场勘验笔录的信息
	 * @param baseId
	 * @param lawLinkId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/getDocFromInsep")
	public TeeJson getDocFromInsep(String baseId, String lawLinkId) {
		TeeJson json = new TeeJson();
		InspectionRecordModel model = new InspectionRecordModel();
		List<DocInspectionRecord> list=  (List<DocInspectionRecord>)commonService.getDocByBaseId("DocInspectionRecord",baseId,lawLinkId);
		List<InspectionRecordModel> modelList = new ArrayList<InspectionRecordModel>();
		for (DocInspectionRecord docInspectionRecord : list) {
			BeanUtils.copyProperties(docInspectionRecord, model);
			
			model.setInspectionTimeStartStr(TeeDateUtil.format(docInspectionRecord.getInspectionTimeStart(),"yyyy年MM月dd日"));
			model.setInspectionTimeEndStr(TeeDateUtil.format(docInspectionRecord.getInspectionTimeEnd(),"yyyy年MM月dd日"));
			modelList.add(model);
		}
		json.setRtData(modelList);
		return json;
	}
	
	/**
	 * 行政处罚决定书（当场处罚）取立案审批表的信息
	 * @param baseId
	 * @param lawLinkId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/getDocFromPuton")
	public TeeJson getDocFromPuton(String baseId) {
		TeeJson json = new TeeJson();
		// 因为立案审批表与行政处罚决定书 不是同一个环节  所以需要通过baseId从流程表（punishFlow） 取得当前 案件的 立案审批流程信息 以获取 立案审批的环节ID
		List<PunishFLow> list = flowService.getFlowcase(baseId, "立案审批", "");//通过baseId 和 流程名  查流程 信息（立案审批一般只有一个）
		PunishFLow fir = list.get(0);
		String lawLinkId = fir.getTacheId();
		PutonrecordModel model = new PutonrecordModel();
		List<DocPutonrecord> putoList= (List<DocPutonrecord>)commonService.getDocByBaseId("DocPutonrecord", baseId, lawLinkId);
		List<PutonrecordModel> modelList = new ArrayList<PutonrecordModel>();
		for (DocPutonrecord docPutonrecord : putoList) {
			BeanUtils.copyProperties(docPutonrecord, model);
			modelList.add(model);
		}
		json.setRtData(modelList);
		return json;
	}
}





