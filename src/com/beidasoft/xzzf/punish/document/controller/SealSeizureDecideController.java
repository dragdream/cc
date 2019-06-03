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
import com.beidasoft.xzzf.punish.document.bean.DocSealSeizureDecide;
import com.beidasoft.xzzf.punish.document.model.SealSeizureDecideModel;
import com.beidasoft.xzzf.punish.document.service.SealSeizureDecideService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 查封扣押处理决定书
 * */
@Controller
@RequestMapping("/sealSeizureDecideCtrl")
public class SealSeizureDecideController {
	
	@Autowired
	private SealSeizureDecideService sealSeizureDecideService;
	@Autowired
	private TeeWenShuService wenShuService;
	@Autowired
	private CommonService commonService;
 
	/**
	 * @param sealSeizureDecideModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson save(SealSeizureDecideModel sealSeizureDecideModel, HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 实例化实体类对象
		DocSealSeizureDecide sealSeizureDecide = new DocSealSeizureDecide();
		// 将model的值传递给bean
		BeanUtils.copyProperties(sealSeizureDecideModel, sealSeizureDecide);
		
//		// RUNID
//		String runId = request.getParameter("runId");
		
		// 单独处理时间类型转换
		// 鉴定时间（开始）
		if (StringUtils.isNotBlank(sealSeizureDecideModel.getCheckupDateStartStr())) {
			sealSeizureDecide.setCheckupDateStart(TeeDateUtil.format(sealSeizureDecideModel.getCheckupDateStartStr(),"yyyy年MM月dd日"));
		}
	    // 鉴定时间（结束）
		if (StringUtils.isNotBlank(sealSeizureDecideModel.getCheckupDateEndStr())) {
			sealSeizureDecide.setCheckupDateEnd(TeeDateUtil.format(sealSeizureDecideModel.getCheckupDateEndStr(),"yyyy年MM月dd日"));
		}
		 // 行政机关落款印章时间
		if (StringUtils.isNotBlank(sealSeizureDecideModel.getLawUnitDateStr())) {
			sealSeizureDecide.setLawUnitDate(TeeDateUtil.format(sealSeizureDecideModel.getLawUnitDateStr(),"yyyy年MM月dd日"));
		}
		// 送达时间
		if (StringUtils.isNotBlank(sealSeizureDecideModel.getDeliverDateStr())) {
			sealSeizureDecide.setDeliverDate(TeeDateUtil.format(sealSeizureDecideModel.getDeliverDateStr(),"yyyy年MM月dd日 HH时mm分"));
		}
		// 受送达人签名或盖章时间
		if (StringUtils.isNotBlank(sealSeizureDecideModel.getReceiverDateStr())) {
			sealSeizureDecide.setReceiverDate(TeeDateUtil.format(sealSeizureDecideModel.getReceiverDateStr(),"yyyy年MM月dd日 HH时mm分"));
		}
		// 延长至
		if (StringUtils.isNotBlank(sealSeizureDecideModel.getExtendDateStr())) {
			sealSeizureDecide.setExtendDate(TeeDateUtil.format(sealSeizureDecideModel.getExtendDateStr(),"yyyy年MM月dd日"));
		}
		
		if (StringUtils.isNotBlank(sealSeizureDecideModel.getCheckupBeforBeforeStr())) {
			sealSeizureDecide.setCheckupBeforBefore(TeeDateUtil.format(sealSeizureDecideModel.getCheckupBeforBeforeStr(),"yyyy年MM月dd日"));
		}
		// 设置创建人相关信息
		if (StringUtils.isBlank(sealSeizureDecideModel.getId())) {
			sealSeizureDecide.setId(UUID.randomUUID().toString());
			sealSeizureDecide.setCreateUserId(user.getUserId());
			sealSeizureDecide.setCreateUserName(user.getUserName());
			sealSeizureDecide.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建查封/扣押 处理决定书");
			sealSeizureDecide.setDelFlg("0");
		} else {
			//设置创建人相关信息
			sealSeizureDecide.setUpdateUserId(user.getUserId());
			sealSeizureDecide.setUpdateUserName(user.getUserName());
			sealSeizureDecide.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改查封/扣押 处理决定书");
		}
		
		sealSeizureDecide.setDelFlg("0");
		// 保存 查封/扣押 处理决定书
		sealSeizureDecideService.save(sealSeizureDecide,request);
		json.setRtData(sealSeizureDecide);
		json.setRtState(true);
		return json;
	}
 
	/**
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id,HttpServletRequest request) {
		TeeJson json = new TeeJson();
		DocSealSeizureDecide sealSeizureDecide = sealSeizureDecideService.getById(id);
		SealSeizureDecideModel sealSeizureDecideModel = new SealSeizureDecideModel();
		BeanUtils.copyProperties(sealSeizureDecide, sealSeizureDecideModel);
		// 单独处理时间类型转换
		// 鉴定时间（结束）
		if (sealSeizureDecide.getCheckupDateEnd() != null) {
			sealSeizureDecideModel.setCheckupDateEndStr(TeeDateUtil.format(sealSeizureDecide.getCheckupDateEnd(), "yyyy年MM月dd日"));
		}
		// 鉴定时间（开始）
		if(sealSeizureDecide.getCheckupDateStart() != null) {
			sealSeizureDecideModel.setCheckupDateStartStr(TeeDateUtil.format(sealSeizureDecide.getCheckupDateStart(),"yyyy年MM月dd日"));
		}
		// 行政机关落款时间
		if(sealSeizureDecide.getLawUnitDate() != null) {
			sealSeizureDecideModel.setLawUnitDateStr(TeeDateUtil.format(sealSeizureDecide.getLawUnitDate(),"yyyy年MM月dd日"));
		}
		// 延长至
		if(sealSeizureDecide.getExtendDate() != null) {
			sealSeizureDecideModel.setExtendDateStr(TeeDateUtil.format(sealSeizureDecide.getExtendDate(),"yyyy年MM月dd日"));
		}
		// 受送达人签名或盖章时间
		if(sealSeizureDecide.getReceiverDate() != null) {
			sealSeizureDecideModel.setReceiverDateStr(TeeDateUtil.format(sealSeizureDecide.getReceiverDate(),"yyyy年MM月dd日 HH时mm分"));
		}
		// 送达时间
		if(sealSeizureDecide.getDeliverDate() != null) {
			sealSeizureDecideModel.setDeliverDateStr(TeeDateUtil.format(sealSeizureDecide.getDeliverDate(),"yyyy年MM月dd日 HH时mm分"));
		}
		
		if(sealSeizureDecide.getCheckupBeforBefore() != null) {
			sealSeizureDecideModel.setCheckupBeforBeforeStr(TeeDateUtil.format(sealSeizureDecide.getCheckupBeforBefore(),"yyyy年MM月dd日"));
		}
		// 返回  抽样取证凭证表 json 对象
		json.setRtData(sealSeizureDecideModel);
		json.setRtState(true);
		return json;
	}
	
 
	/**
	 * 预览文书
	 * @param sealSeizureDecideModel
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/viewDocInfo")
	@ResponseBody
	public TeeJson viewDocInfo(SealSeizureDecideModel sealSeizureDecideModel, HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = sealSeizureDecideModel.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId, content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
}
