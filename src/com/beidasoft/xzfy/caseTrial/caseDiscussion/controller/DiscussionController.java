package com.beidasoft.xzfy.caseTrial.caseDiscussion.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzfy.caseRegister.bean.FyCaseHandling;
import com.beidasoft.xzfy.caseRegister.service.CaseReviewMattersService;
import com.beidasoft.xzfy.caseTrial.caseDiscussion.bean.Discussion;
import com.beidasoft.xzfy.caseTrial.caseDiscussion.model.DiscussionModel;
import com.beidasoft.xzfy.caseTrial.caseDiscussion.service.DiscussionService;
import com.beidasoft.xzfy.utils.Const;
import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

/**   
 * Description:审理-集体讨论会
 * @author zhangchengkun
 * @version 0.1 2019年4月23日
 */
@Controller
@RequestMapping("/discussionController")
public class DiscussionController {
	
	@Autowired
	private CaseReviewMattersService caseReviewMattersService;
	
	@Autowired
	private DiscussionService discussionService;

	/**
	 * Description:集体讨论会保存或修改
	 * @author ZCK
	 * @version 0.1 2019年4月23日
	 * @param request
	 * @return
	 * TeeJson
	 */
	@RequestMapping("/save")
	@ResponseBody
	public TeeJson saveOrUpdate(HttpServletRequest request) {
		TeeJson json= new TeeJson();
		try {
			//调用保存或修改方法
			Discussion discussion = discussionService.getById(request);
			if(discussion!=null) {
				discussionService.update(request,json);
			}else {
				discussionService.save(request,json);
			}
			json.setRtState(true);
		} catch (Exception e) {
			json.setRtMsg("案件异常!");
			json.setRtState(false);
		}
	 	return json;
	}
	
	/**
	 * Description:根据案件id查询集体讨论会
	 * @author ZCK
	 * @version 0.1 2019年4月23日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/getListByCaseId")
	@ResponseBody
	public TeeEasyuiDataGridJson getListByCaseId(TeeDataGridModel dm, HttpServletRequest request) {
		DiscussionModel model = new  DiscussionModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return discussionService.getListByCaseId(dm,request,model);
	}
	
	/**
	 * Description:删除
	 * @author zhangchengkun
	 * @version 0.1 2019年5月7日
	 * @param request
	 * @return  TeeJson
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public TeeJson delete(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		try {
			discussionService.delete(request);
			json.setRtState(true);
		} catch (Exception e) {
			json.setRtMsg("案件异常!");
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * Description:获取单个对象信息
	 * @author zhangchengkun
	 * @version 0.1 2019年5月7日
	 * @param request
	 * @return  TeeJson
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Discussion discussion = null;
		try {
			discussion = discussionService.getById(request);
			json.setRtData(discussion);
			json.setRtState(true);
		} catch (Exception e) {
			json.setRtMsg("案件异常!");
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * Description:获取案件受理两个承办人回显到会议管理页面
	 * @author zhangchengkun
	 * @version 0.1 2019年5月22日
	 * @param caseId
	 * @return  TeeJson
	 */
	@RequestMapping("getCbr")
	@ResponseBody
	public TeeJson getCbr(String caseId) {
		TeeJson json = new TeeJson();
		try {
			String cbr = discussionService.getCbr(caseId);
			json.setRtState(true);
			json.setRtData(cbr);
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("请求失败,请联系管理员！");
		}
		return json;
	}
	
	/**
	 * Description:通过案件Id获取案件信息
	 * @author zhangchengkun
	 * @version 0.1 2019年5月23日
	 * @param caseId
	 * @return  TeeJson
	 */
	@RequestMapping("/getCaseInfoById")
	@ResponseBody
	public TeeJson getCaseInfoById(String caseId) {
		TeeJson json = new TeeJson();
		FyCaseHandling caseInfo = null;
		try {
			caseInfo =  caseReviewMattersService.get(caseId);
			json.setRtState(true);
			json.setRtData(caseInfo);
		} catch (Exception e) {
			json.setRtMsg("请求失败,请联系管理员");
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * Description:中止操作
	 * @author zhangchengkun
	 * @version 0.1 2019年5月6日
	 * @return TeeJson
	 */
	@RequestMapping("/suspension")
	@ResponseBody
	public TeeJson suspension(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		//获取案件Id
		String caseId = request.getParameter("caseId");
		//获取该条案件
		FyCaseHandling caseInfo = (FyCaseHandling) caseReviewMattersService.get(caseId);
		if(caseInfo!=null) {
			//中止时间
			String caseSubBreakTime = request.getParameter("caseSubBreakTime");
			//中止原因
			String caseSubBreakReason = request.getParameter("caseSubBreakReason");
			//修改案件状态
			caseInfo.setCaseSubStatusCode("");//子状态code-中止
			caseInfo.setCaseSubStatus("");//子状态-中止
			caseInfo.setCaseSubBreakReason(caseSubBreakReason);//中止理由
			caseInfo.setCaseSubBreakTime(caseSubBreakTime);//中止时间
			try {
				caseReviewMattersService.update(caseInfo);
				json.setRtState(true);
			} catch (Exception e) {
				json.setRtState(false);
				json.setRtMsg("操作失败,请联系管理员!");
			}
		}else {
			json.setRtState(false);
			json.setRtMsg("未查询到该案件!");
		}
		return json;
	}
	
	/**
	 * Description:案件恢复
	 * @author zhangchengkun
	 * @version 0.1 2019年5月6日
	 * @return TeeJson
	 */
	@RequestMapping("/recovery")
	@ResponseBody
	public TeeJson recovery(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		//获取案件Id
		String caseId = request.getParameter("caseId");
		//获取该条案件
		FyCaseHandling caseInfo = (FyCaseHandling) caseReviewMattersService.get(caseId);
		if(caseInfo!=null) {
			//修改案件状态
			caseInfo.setCaseSubStatusCode("");//子状态中止code赋值为空
			caseInfo.setCaseSubStatus("");//子状态中止值赋值为空
			caseInfo.setCaseSubRestoreTime(request.getParameter("caseSubRestoreTime"));//恢复时间
			caseInfo.setCaseSubRestoreReason(request.getParameter("caseSubRestoreReason"));//恢复理由
			try {
				caseReviewMattersService.update(caseInfo);
				json.setRtState(true);
			} catch (Exception e) {
				json.setRtState(false);
				json.setRtMsg("操作失败,请联系管理员!");
			}
		}else {
			json.setRtState(false);
			json.setRtMsg("未查询到该案件!");
		}
		return json;
	}
	
	/**
	 * Description:延期
	 * @author zhangchengkun
	 * @version 0.1 2019年5月6日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/delay")
	@ResponseBody
	public TeeJson delay(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		//获取案件Id
		String caseId = request.getParameter("caseId");
		//获取该条案件
		FyCaseHandling caseInfo = (FyCaseHandling) caseReviewMattersService.get(caseId);
		if(caseInfo!=null) {
			//是否延期
			caseInfo.setIsDelay(1);
			//延期开始日期
			caseInfo.setCaseSubRestoreStartTime(request.getParameter("caseSubRestoreStartTime"));
			//延期结束日期
			caseInfo.setCaseSubRestoreEndTime(request.getParameter("caseSubRestoreEndTime"));
			//延期天数
			caseInfo.setDelayDays(Integer.parseInt(request.getParameter("delayDays")));
			//延期理由
			caseInfo.setCaseSubExtensionReason(request.getParameter("caseSubExtensionReason"));
			try {
				caseReviewMattersService.update(caseInfo);
				json.setRtState(true);
			} catch (Exception e) {
				json.setRtState(false);
				json.setRtMsg("操作失败,请联系管理员!");
			}
		}else {
			json.setRtState(false);
			json.setRtMsg("未查询到该案件!");
		}
		
		return json;
	}
	/**
	 * Description:辅助操作：建议书
	 * @author zhangchengkun
	 * @version 0.1 2019年5月23日
	 * @param request
	 * @return  TeeJson
	 */
	@RequestMapping("/commend")
	@ResponseBody
	public TeeJson commend(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		//获取案件Id
		String caseId = request.getParameter("caseId");
		//获取该条案件
		FyCaseHandling caseInfo = (FyCaseHandling) caseReviewMattersService.get(caseId);
		if(caseInfo!=null) {
			//发现的问题
			caseInfo.setDiscoveredProblems(request.getParameter("discoveredProblems"));
			//建议
			caseInfo.setProposalAdvise(request.getParameter("proposalAdvise"));
			try {
				caseReviewMattersService.update(caseInfo);
				json.setRtState(true);
			} catch (Exception e) {
				json.setRtState(false);
				json.setRtMsg("操作失败,请联系管理员!");
			}
		}else {
			json.setRtState(false);
			json.setRtMsg("未查询到该案件!");
		}
		
		return json;
	}
	
	
	/**
	 * Description:结案
	 * @author zhangchengkun
	 * @version 0.1 2019年5月6日
	 * @return TeeJson
	 */
	@RequestMapping("/settle")
	@ResponseBody
	public TeeJson settle(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		//获取案件Id
		String caseId = request.getParameter("caseId");
		//结案类型code
		String settleTypeCode = request.getParameter("settleTypeCode");
		//结案类型
		String settleType = request.getParameter("settleType");
		//处理理由
		String caseSubEndReason = request.getParameter("caseSubEndReason");
		//获取该条案件
		FyCaseHandling caseInfo = (FyCaseHandling) caseReviewMattersService.get(caseId);
		if(caseInfo!=null) {
			//归档中案件状态code
			caseInfo.setCaseStatusCode(Const.CASESTATUS.CASE_ARCHIVE_CODE);
			//案件状态为归档中
			caseInfo.setCaseStatus(Const.CASESTATUS.CASE_ARCHIVE_NAME);
			//结案类型code
			caseInfo.setSettleTypeCode(settleTypeCode);
			//结案类型
			caseInfo.setSettleType(settleType);
			//结案时间
			caseInfo.setSettleDate(StringUtils.getCurrentTime());
			//处理理由
			caseInfo.setCaseSubEndReason(caseSubEndReason);
			try {
				caseReviewMattersService.update(caseInfo);
			} catch (Exception e) {
				json.setRtState(false);
				json.setRtMsg("案件异常!");
			}
		}else {
			json.setRtState(false);
			json.setRtMsg("未查询到该案件!");
		}
		return json;
	}
}
