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

import com.beidasoft.xzzf.power.bean.BasePower;
import com.beidasoft.xzzf.power.service.PowerSelectService;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.service.CommonService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocInspectionRecord;
import com.beidasoft.xzzf.punish.document.bean.DocPutonrecord;
import com.beidasoft.xzzf.punish.document.model.InspectionRecordModel;
import com.beidasoft.xzzf.punish.document.model.PutonrecordModel;
import com.beidasoft.xzzf.punish.document.service.PutonrecordService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/putonrecordCtrl")
public class PutonrecordController {

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private PutonrecordService docPutonrecordService;
		
	@Autowired
	private TeeWenShuService wenShuService;
	
	@Autowired
	private PowerSelectService powerService;
	
	@Autowired
	private PunishFlowService flowService;
	
	/**
	 * 保存立案审批表
	 * @param evidenceNoteModel
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson save(PutonrecordModel docPutonrecordModel, HttpServletRequest request) throws Exception {

		TeeJson json = new TeeJson();

		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		// 实例化实体类对象
		DocPutonrecord docPutonrecord = new DocPutonrecord();
		
		// 属性值传递
		BeanUtils.copyProperties(docPutonrecordModel, docPutonrecord);
		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(docPutonrecordModel.getUndertakePersonalTimeStr())) {
			docPutonrecord.setUndertakePersonalTime(TeeDateUtil.format(docPutonrecordModel.getUndertakePersonalTimeStr(), "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(docPutonrecordModel.getUndertakeDepartmentTimeStr())) {
			docPutonrecord.setUndertakeDepartmentTime(TeeDateUtil.format(docPutonrecordModel.getUndertakeDepartmentTimeStr(), "yyyy年MM月dd日"));
		}
		// 设置创建人相关信息
		if (StringUtils.isBlank(docPutonrecordModel.getId())) {
			docPutonrecord.setId(UUID.randomUUID().toString());
			docPutonrecord.setCreateUserId(user.getUserId());
			docPutonrecord.setCreateUserName(user.getUserName());
			docPutonrecord.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建立案审批表");
		} else {
			//设置创建人相关信息
			docPutonrecord.setUpdateUserId(user.getUserId());
			docPutonrecord.setUpdateUserName(user.getUserName());
			docPutonrecord.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改立案审批表");
		}
		docPutonrecord.setDelFlg("0");
		// 保存 立案审批表
		docPutonrecordService.save(docPutonrecord,request);

		json.setRtData(docPutonrecord);
		json.setRtState(true);

		return json;
	}
	
	/**
	 * 获取单个立案审批表信息（通过主键ID）
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson get(String id, HttpServletRequest request) {

		TeeJson json = new TeeJson();
		
		DocPutonrecord docPutonrecord = docPutonrecordService.getById(id);
		PutonrecordModel docPutonrecordModel = new PutonrecordModel();
		
		BeanUtils.copyProperties(docPutonrecord, docPutonrecordModel);

		// 单独处理时间类型转换
		if (docPutonrecord.getUndertakePersonalTime() != null) {
			docPutonrecordModel.setUndertakePersonalTimeStr(TeeDateUtil.format(docPutonrecord.getUndertakePersonalTime(), 
					"yyyy年MM月dd日"));
		}
		if (docPutonrecord.getUndertakeDepartmentTime() != null) {
			docPutonrecordModel.setUndertakeDepartmentTimeStr(TeeDateUtil.format(docPutonrecord.getUndertakeDepartmentTime(), 
					"yyyy年MM月dd日"));
		}

		// 返回 立案审批表 json 对象
		json.setRtData(docPutonrecordModel);
		json.setRtState(true);

		return json;
	}
	
	/**
	 * 预览文书
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/viewDocInfo")
	@ResponseBody
	public TeeJson insptnrcdpsnDoc(PutonrecordModel putonrecordModel, HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = putonrecordModel.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId, content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 立案审批表取现场勘验笔录的信息
	 * @param baseId
	 * @param lawLinkId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/getDocFromInsp")
	public TeeJson getDocFromInsp(String baseId) {
		TeeJson json = new TeeJson();
		// 因为立案审批表与现场勘验笔录 不是同一个环节  所以需要通过baseId从流程表（punishFlow） 取得当前 案件的 现场勘验笔录流程信息 以获取现场勘验笔录的环节ID
		List<PunishFLow> flowlist = flowService.getFlowcase(baseId, "现场检查（勘验）笔录", "");//通过baseId 和 流程名  查流程 信息
		PunishFLow fir = flowlist.get(0);
		String lawLinkId = fir.getTacheId();
		InspectionRecordModel model = new InspectionRecordModel();
		List<DocInspectionRecord> list = (List<DocInspectionRecord>) commonService .getDocByBaseId("DocInspectionRecord", baseId, lawLinkId);
		List<InspectionRecordModel> modelList = new ArrayList<InspectionRecordModel>();
		for (DocInspectionRecord docInspectionRecord : list) {
			BeanUtils.copyProperties(docInspectionRecord, model);
			model.setInspectionTimeStartStr(TeeDateUtil.format(docInspectionRecord.getInspectionTimeStart(), "yyyy年MM月dd日HH时mm分"));
			model.setInspectionTimeEndStr(TeeDateUtil.format(docInspectionRecord.getInspectionTimeEnd(), "yyyy年MM月dd日HH时mm分"));			
			modelList.add(model);
		}
		json.setRtData(modelList);
		return json;
  }
	
	/**
	 * 获取全部职权信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPowerList")
	public TeeJson getPowerList() {
		TeeJson json = new TeeJson();
		List<BasePower> powerList = powerService.getPowerList();
		json.setRtData(powerList);
		json.setRtState(true);
		return json;
	}
}
