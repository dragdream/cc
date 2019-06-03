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
import com.beidasoft.xzzf.punish.document.bean.DocInorout;
import com.beidasoft.xzzf.punish.document.model.InoroutModel;
import com.beidasoft.xzzf.punish.document.service.InoroutService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/inoroutCtrl")
public class InoroutController {
	@Autowired
	private InoroutService inoroutService;

	@Autowired
	private TeeWenShuService wenShuService;

	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private CommonService commonService;

	/**
	 * 保存入库出库单信息
	 * 
	 * @param inoroutModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson saveDocInfo(InoroutModel inoroutModel,
			HttpServletRequest request) throws Exception {

		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 实例化实体类对象
		DocInorout inorout = new DocInorout();
		// 属性值传递
		BeanUtils.copyProperties(inoroutModel, inorout);
		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(inoroutModel.getMoveDateStr())) {
			inorout.setMoveDate(TeeDateUtil.format(
					inoroutModel.getMoveDateStr(), "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(inoroutModel.getReciveDateStr())) {
			inorout.setReciveDate(TeeDateUtil.format(
					inoroutModel.getReciveDateStr(), "yyyy年MM月dd日"));
		}
		// 设置创建人相关信息
		if (StringUtils.isBlank(inoroutModel.getId())) {
			inorout.setId(UUID.randomUUID().toString());
			inorout.setCreateUserId(user.getUserId());
			inorout.setCreateUserName(user.getUserName());
			inorout.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建入库出库单");
			// 防止出现空
			inorout.setDelFlg("0");
		} else {
			// 设置创建人相关信息
			inorout.setUpdateUserId(user.getUserId());
			inorout.setUpdateUserName(user.getUserName());
			inorout.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改入库出库单");
		}
		// 保存 入库出库单
		inoroutService.save(inorout , request);
		json.setRtData(inorout);
		json.setRtState(true);
		return json;
	}

	/**
	 * 获取入库出库单信息
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id, HttpServletRequest request) {
		
		TeeJson json = new TeeJson();
		DocInorout inorout = inoroutService.getById(id);
		InoroutModel inoroutModel = new InoroutModel();
		BeanUtils.copyProperties(inorout, inoroutModel);
		// 单独处理时间类型转换
		if (inorout.getMoveDate() != null) {
			inoroutModel.setMoveDateStr(TeeDateUtil.format(
					inorout.getMoveDate(), "yyyy年MM月dd日"));
		}
		if (inorout.getReciveDate() != null) {
			inoroutModel.setReciveDateStr(TeeDateUtil.format(
					inorout.getReciveDate(), "yyyy年MM月dd日"));
		}
		// 返回 入库出库单json 对象
		json.setRtData(inoroutModel);
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
	public TeeJson viewDocInfo(InoroutModel inoroutModel,
			HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(
				request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(
				request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = inoroutModel.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId,
				content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
}
