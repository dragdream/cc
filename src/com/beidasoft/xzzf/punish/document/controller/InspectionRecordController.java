package com.beidasoft.xzzf.punish.document.controller;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.service.CommonService;
import com.beidasoft.xzzf.punish.document.bean.DocInspectionRecord;
import com.beidasoft.xzzf.punish.document.model.InspectionRecordModel;
import com.beidasoft.xzzf.punish.document.service.InspectionRecordService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/inspectionRecordCtrl")
public class InspectionRecordController {

	@Autowired
	private InspectionRecordService inspectionRecordService;
	
	@Autowired
	private TeeWenShuService wenShuService;
	
	@Autowired
	private CommonService commonService;

	/**
	 * 保存现场检查（勘验）记录单
	 * 
	 * @param inspectionRecordModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson saveDocInfo(InspectionRecordModel inspectionRecordModel, HttpServletRequest request) throws Exception {

		TeeJson json = new TeeJson();

		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeDepartment department = user.getDept();
		
		// 实例化实体类对象
		DocInspectionRecord inspectionRecord = new DocInspectionRecord();

		// 属性值传递
		BeanUtils.copyProperties(inspectionRecordModel, inspectionRecord);

		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(inspectionRecordModel.getInspectionTimeStartStr())) {
			inspectionRecord.setInspectionTimeStart(TeeDateUtil.format(inspectionRecordModel.getInspectionTimeStartStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(inspectionRecordModel.getInspectionTimeEndStr())) {
			inspectionRecord.setInspectionTimeEnd(TeeDateUtil.format(inspectionRecordModel.getInspectionTimeEndStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(inspectionRecordModel.getSiteLeaderDateStr())) {
			inspectionRecord.setSiteLeaderDate(TeeDateUtil.format(inspectionRecordModel.getSiteLeaderDateStr(), 
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(inspectionRecordModel.getLawUnitDateStr())) {
			inspectionRecord.setLawUnitDate(TeeDateUtil.format(inspectionRecordModel.getLawUnitDateStr(), 
					"yyyy年MM月dd日"));
		} 

		// 设置创建人相关信息
		if (StringUtils.isBlank(inspectionRecordModel.getId())) {
			inspectionRecord.setLawUnitId(department.getSubordinateUnitsCode());
			inspectionRecord.setLawUnitName(department.getSubordinateUnits());
			inspectionRecord.setCreateUserId(user.getUserId());
			inspectionRecord.setCreateUserName(user.getUserName());
			inspectionRecord.setId(UUID.randomUUID().toString());
			inspectionRecord.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建现场检查（勘验）笔录");
			inspectionRecord.setDelFlg("0");
		} else {
			//设置创建人相关信息
			inspectionRecord.setUpdateUserId(user.getUserId());
			inspectionRecord.setUpdateUserName(user.getUserName());
			inspectionRecord.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改现场检查（勘验）笔录");
		}
		// 保存 现场检查（勘验）记录表
		inspectionRecordService.save(inspectionRecord, request);

		json.setRtData(inspectionRecord);
		json.setRtState(true);

		return json;
	}
	
	/**
	 * 获取单个现场检查（勘验）信息（通过主键ID）
	 * @param id
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id, HttpServletRequest request) {

		TeeJson json = new TeeJson();
		
		DocInspectionRecord inspectionRecord = inspectionRecordService.getById(id);
		InspectionRecordModel inspectionRecordModel = new InspectionRecordModel();

		BeanUtils.copyProperties(inspectionRecord, inspectionRecordModel);

		// 单独处理时间类型转换
		if (inspectionRecord.getInspectionTimeStart() != null) {
			inspectionRecordModel.setInspectionTimeStartStr(TeeDateUtil.format(inspectionRecord.getInspectionTimeStart(), 
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (inspectionRecord.getInspectionTimeEnd() != null) {
			inspectionRecordModel.setInspectionTimeEndStr(TeeDateUtil.format(inspectionRecord.getInspectionTimeEnd(), 
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (inspectionRecord.getSiteLeaderDate() != null) {
			inspectionRecordModel.setSiteLeaderDateStr(TeeDateUtil.format(inspectionRecord.getSiteLeaderDate(), 
					"yyyy年MM月dd日"));
		}
		if (inspectionRecord.getLawUnitDate() != null) {
			inspectionRecordModel.setLawUnitDateStr(TeeDateUtil.format(inspectionRecord.getLawUnitDate(), 
					"yyyy年MM月dd日"));
		}

		// 返回 现场检查（勘验）记录表 json 对象
		json.setRtData(inspectionRecordModel);
		json.setRtState(true);

		return json;
	}
	
	
	/**
	 * 更新现场检查（勘验）记录单
	 * @param inspectionRecordModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public TeeJson update(InspectionRecordModel inspectionRecordModel, HttpServletRequest request) {

		TeeJson json = new TeeJson();

		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		DocInspectionRecord inspectionRecord = inspectionRecordService.getById(inspectionRecordModel.getId());

		// 属性值传递
		BeanUtils.copyProperties(inspectionRecordModel, inspectionRecord);

		// 单独处理时间类型转换
		if (!StringUtils.isBlank(inspectionRecordModel.getInspectionTimeStartStr())) {
			inspectionRecord.setInspectionTimeStart(TeeDateUtil.format(inspectionRecordModel.getInspectionTimeStartStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (!StringUtils.isBlank(inspectionRecordModel.getInspectionTimeEndStr())) {
			inspectionRecord.setInspectionTimeEnd(TeeDateUtil.format(inspectionRecordModel.getInspectionTimeEndStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (!StringUtils.isBlank(inspectionRecordModel.getSiteLeaderDateStr())) {
			inspectionRecord.setSiteLeaderDate(TeeDateUtil.format(inspectionRecordModel.getSiteLeaderDateStr(), 
					"yyyy年MM月dd日"));
		}
		if (!StringUtils.isBlank(inspectionRecordModel.getLawUnitDateStr())) {
			inspectionRecord.setLawUnitDate(TeeDateUtil.format(inspectionRecordModel.getLawUnitDateStr(), 
					"yyyy年MM月dd日"));
		}
		
		//设置创建人相关信息
		inspectionRecord.setUpdateUserId(user.getUserId());
		inspectionRecord.setUpdateUserName(user.getUserName());
		inspectionRecord.setUpdateTime(Calendar.getInstance().getTime());

		// 更新 现场检查（勘验）记录表
		inspectionRecordService.update(inspectionRecord);

		json.setRtData(inspectionRecord);
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
	public TeeJson viewDocInfo(InspectionRecordModel inspectionRecordModel, HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = inspectionRecordModel.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId, content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
}
