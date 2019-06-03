package com.beidasoft.xzzf.punish.common.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.service.CommonService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller("zf_commonController")
@RequestMapping("/commonController")
public class CommonController {
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private PunishFlowService flowService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private TeePersonDao personDao;
	
	@RequestMapping("/varValue")
	@ResponseBody
	public TeeJson varValue(HttpServletRequest request) {
		TeeJson result = new TeeJson();
		int runId = TeeStringUtil.getInteger(request.getParameter("RUN_ID"), 0);
		String primaryId = TeeStringUtil.getString(request.getParameter("PRIMARY_ID"));
		String key = TeeStringUtil.getString(request.getParameter("KEY"));
		//根据key和runId更新流程变量
		commonService.updateFlowRunVars(primaryId, runId, key);
		result.setRtState(true);
		return result;
	}
	/**
	 * 获取附件列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/attachmentList")
	@ResponseBody
	public TeeJson getAttachmentList(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		//String baseId = TeeStringUtil.getString(request.getParameter(""	+ ""), "");
		String baseId = request.getParameter("baseId");
		List<TeeAttachmentModel> attachAllList = new ArrayList<TeeAttachmentModel>();
		// 获取案件附件列表
		List<TeeAttachmentModel> caseList = attachmentService.getAttacheModels("CaseAttachment", baseId);
		if (caseList != null && !caseList.isEmpty()) {
			attachAllList.addAll(caseList);
		}
		// 获取文书RUNID信息
		List<PunishFLow> flowInfo = flowService.getFlowcase(baseId, null, "");
		for (PunishFLow flowObj : flowInfo) {
			// 获取文书附件列表
			List<TeeAttachmentModel> tmpList = attachmentService.getAttacheModels("DocAttachment", String.valueOf(flowObj.getPunishRunId()));
			if (tmpList != null && !tmpList.isEmpty()) {
				attachAllList.addAll(tmpList);
			}
		}
		json.setRtData(attachAllList);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 通过部门ID 获取部门中的所有人员List
	 * 
	 * @param deptId
	 * @return
	 */
	@RequestMapping("/personListByDeptId")
	@ResponseBody
	public TeeJson  getPersonsByDeptId(String deptId) {
		TeeJson json = new TeeJson();
		List<TeePerson> personList = personDao.selectPersonByDeptId(deptId);
		List<TeePersonModel> personModelList = new ArrayList<TeePersonModel>();
		for (TeePerson teePerson : personList) {
			TeePersonModel personModel = new TeePersonModel();
			BeanUtils.copyProperties(teePerson, personModel);
			personModelList.add(personModel);
		}
		if (personModelList.size() >0) {
			json.setRtData(personModelList);
		} else {
			personList = null;
		}
		return json;
	}
}
