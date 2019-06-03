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
import com.beidasoft.xzzf.punish.document.bean.DocTransferlist;
import com.beidasoft.xzzf.punish.document.model.TransferlistModel;
import com.beidasoft.xzzf.punish.document.service.TransferlistService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/transferlistCtrl")
public class TransferlistController {

	@Autowired
	private TransferlistService transferlistService;

	@Autowired
	private TeeWenShuService wenShuService;
	
	@Autowired
	private CommonService commonService;

	/**
	 * 保存证据材料移送清单
	 * @param transferlistModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson saveDocInfo(TransferlistModel transferlistModel, HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();

		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		// 实例化实体对象
		DocTransferlist docTransferlist = new DocTransferlist();

		// 属性值传递
		BeanUtils.copyProperties(transferlistModel, docTransferlist);

		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(transferlistModel.getMoveLawUnitDateStr())) {
			docTransferlist.setMoveLawUnitDate(TeeDateUtil.format(transferlistModel.getMoveLawUnitDateStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(transferlistModel.getReciveLawUnitDateStr())) {
			docTransferlist.setReciveLawUnitDate(TeeDateUtil.format(transferlistModel.getReciveLawUnitDateStr(),
					"yyyy年MM月dd日"));
		}
		// 设置创建人相关信息
		if (StringUtils.isBlank(transferlistModel.getId())) {
			docTransferlist.setId(UUID.randomUUID().toString());
			docTransferlist.setCreateUserName(user.getUserName());
			docTransferlist.setCreateUserId(user.getUuid()+"");
			docTransferlist.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建证据材料移送清单");
			docTransferlist.setDelFlg("0");
		} else {
			//设置修改人相关信息
			docTransferlist.setUpdateUserId(user.getUserId());
			docTransferlist.setUpdateUserName(user.getUserName());
			docTransferlist.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改证据材料移送清单");
		}
		// 保存 证据材料移送清单 记录表
		transferlistService.saveDocInfo(docTransferlist , request);
		
		json.setRtData(docTransferlist);
		json.setRtState(true);
		
		return json;
	}

	/**
	 * 查询证据材料移送清单
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		DocTransferlist docTransferlist = transferlistService.getDocInfo(id);
		TransferlistModel transferlistModel = new TransferlistModel();
		BeanUtils.copyProperties(docTransferlist, transferlistModel);
		// 单独处理时间类型转换
		if (docTransferlist.getMoveLawUnitDate() != null) {
			transferlistModel.setMoveLawUnitDateStr(TeeDateUtil.format(docTransferlist.getMoveLawUnitDate(),
					"yyyy年MM月dd日"));
		}
		if (docTransferlist.getReciveLawUnitDate() != null) {
			transferlistModel.setReciveLawUnitDateStr(TeeDateUtil.format(docTransferlist.getReciveLawUnitDate(),
					"yyyy年MM月dd日"));
		}
		// 返回 证据材料移送清单 记录表 json 对象
		json.setRtData(transferlistModel);
		json.setRtState(true);
		return json;
	}

	/**
	 * 预览案证据材料移送清单
	 * @param transferlistModel
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/viewDocInfo")
	public TeeJson viewDocInfo(TransferlistModel transferlistModel, HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = transferlistModel.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId, content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
}
