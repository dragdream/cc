package com.tianee.oa.subsys.doc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.doc.service.TeeDocService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/doc")
public class TeeDocController {
	@Autowired
	private TeeDocService docService;

	// 公文分发
	/**
	 * @param request
	 * @return
	 */
	@RequestMapping("/sendDoc")
	@ResponseBody
	public TeeJson sendDoc(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		docService.sendDoc(requestData);
		json.setRtState(true);
		return json;
	}

	// 公文传阅
	@RequestMapping("/viewDoc")
	@ResponseBody
	public TeeJson viewDoc(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		docService.viewDoc(requestData);
		json.setRtState(true);
		return json;
	}

	@RequestMapping("/DaiYue")
	@ResponseBody
	public TeeEasyuiDataGridJson DaiYue(HttpServletRequest request, TeeDataGridModel dm) {
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return docService.DaiYue(requestData, loginUser, dm);
	}

	@RequestMapping("/DaiShou")
	@ResponseBody
	public TeeEasyuiDataGridJson DaiShou(HttpServletRequest request, TeeDataGridModel dm) {
		Map requestData = TeeServletUtility.getParamMap(request);
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return docService.DaiShou(requestData, loginUser, dm);
	}

	@RequestMapping("/YiFaSong")
	@ResponseBody
	public TeeEasyuiDataGridJson YiFaSong(HttpServletRequest request, TeeDataGridModel dm) {
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return null;
	}

	@RequestMapping("/getReceivedWorks")
	@ResponseBody
	public TeeEasyuiDataGridJson getReceivedWorks(HttpServletRequest request, TeeDataGridModel dataGridModel) {
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return docService.getReceivedWorks(TeeServletUtility.getParamMap(request), loginUser, dataGridModel);
	}

	@RequestMapping("/getHandledWorks")
	@ResponseBody
	public TeeEasyuiDataGridJson getHandledWorks(HttpServletRequest request, TeeDataGridModel dataGridModel) {
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return docService.getHandledWorks(TeeServletUtility.getParamMap(request), loginUser, dataGridModel);
	}

	/**
	 * 签收与拒签收,还有删除
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateRecFlag")
	@ResponseBody
	public TeeJson updateRecFlag(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		docService.updateRecFlag(requestData);
		json.setRtState(true);
		return json;
	}

	/**
	 * 获取分发信息主要用于待阅查看
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDeliveryInfo")
	@ResponseBody
	public TeeJson getDeliveryInfo(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		json.setRtData(docService.getDeliveryInfo(requestData));
		json.setRtState(true);
		return json;
	}

	/**
	 * 转公文，用于获取发起流程时需要的映射参数，将正文和附件进行处理，并根据字段映射反向设置表单数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/createFlow")
	@ResponseBody
	public TeeJson createFlow(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		json.setRtData(docService.createFlow(requestData));
		json.setRtState(true);
		return json;
	}

	/**
	 * 获取传阅视图信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getViewInfo")
	@ResponseBody
	public TeeJson getViewInfo(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		docService.updateViewFlag(requestData);
		json.setRtData(docService.getViewInfo(requestData));
		json.setRtState(true);
		return json;
	}

	/**
	 * 更新查阅标识
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateViewFlag")
	@ResponseBody
	public TeeJson updateViewFlag(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		docService.updateViewFlag(requestData);
		json.setRtState(true);
		return json;
	}

	/**
	 * 转公文，用于获取发起流程时需要的映射参数，将正文和附件进行处理，并根据字段映射反向设置表单数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/createViewFlow")
	@ResponseBody
	public TeeJson createViewFlow(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		json.setRtData(docService.createViewFlow(requestData));
		json.setRtState(true);
		return json;
	}

	/**
	 * 我下发的公文
	 * 
	 * @param request
	 * @param dataGridModel
	 * @return
	 */
	@RequestMapping("/myDocSend")
	@ResponseBody
	public TeeEasyuiDataGridJson myDocSend(HttpServletRequest request, TeeDataGridModel dataGridModel) {
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return docService.myDocSend(TeeServletUtility.getParamMap(request), loginUser, dataGridModel);
	}

	/**
	 * 我传阅的文件
	 * 
	 * @param request
	 * @param dataGridModel
	 * @return
	 */
	@RequestMapping("/myDocView")
	@ResponseBody
	public TeeEasyuiDataGridJson myDocView(HttpServletRequest request, TeeDataGridModel dataGridModel) {
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return docService.myDocView(TeeServletUtility.getParamMap(request), loginUser, dataGridModel);
	}

	@RequestMapping("/getDocSendListByRunId")
	@ResponseBody
	public TeeEasyuiDataGridJson getDocSendListByRunId(HttpServletRequest request, TeeDataGridModel dataGridModel) {
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return docService.getDocSendListByRunId(TeeServletUtility.getParamMap(request), loginUser, dataGridModel);
	}

	@RequestMapping("/getDocViewListByRunId")
	@ResponseBody
	public TeeEasyuiDataGridJson getDocViewListByRunId(HttpServletRequest request, TeeDataGridModel dataGridModel) {
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return docService.getDocViewListByRunId(TeeServletUtility.getParamMap(request), loginUser, dataGridModel);
	}

	/**
	 * 删除 公文分发
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年8月13日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/deleteObjById")
	@ResponseBody
	public TeeJson deleteObjById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sidStr = request.getParameter("sids");
		json = docService.deleteObjById(sidStr);
		return json;
	}

	/**
	 * 收回 公文分发
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年8月13日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/takeBackObjById")
	@ResponseBody
	public TeeJson takeBackObjById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sidStr = request.getParameter("sids");
		json = docService.takeBackObjById(sidStr);
		return json;
	}

	/**
	 * 重发 公文分发
	 * 
	 * @date 2014-3-8
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/reSendObjById")
	@ResponseBody
	public TeeJson reSendObjById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Map requestMap = TeeServletUtility.getParamMap(request);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json = docService.reSendObjById(requestMap, loginPerson);
		return json;
	}

	/**
	 * 删除传阅反馈
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年8月13日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/deleteDocViewById")
	@ResponseBody
	public TeeJson deleteDocViewById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sidStr = request.getParameter("sids");
		json = docService.deleteDocViewById(sidStr);
		return json;
	}

	/**
	 * 收回传阅反馈
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年8月13日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/takeBackDocViewById")
	@ResponseBody
	public TeeJson takeBackDocViewById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sidStr = request.getParameter("sids");
		json = docService.takeBackDocViewById(sidStr);
		return json;
	}
	
	
	
	/**
	 * 重发 传阅反馈
	 * 
	 * @date 2014-3-8
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/reSendDocViewById")
	@ResponseBody
	public TeeJson reSendDocView(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Map requestMap = TeeServletUtility.getParamMap(request);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json = docService.reSendDocViewById(requestMap, loginPerson);
		return json;
	}
	

    /**
     * 待收件   修改打印份数
     * @param request
     * @return
     */
	@RequestMapping("/updateDeliveryPrintedNum")
	@ResponseBody
	public TeeJson updateDeliveryPrintedNum(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		//待收件  修改打印份数
		docService.updateDeliveryPrintedNum(requestData);
		json.setRtState(true);
		return json;
	}
	
	
	  /**
     * 待签阅   修改打印份数
     * @param request
     * @return
     */
	@RequestMapping("/updateViewPrintedNum")
	@ResponseBody
	public TeeJson updateViewPrintedNum(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		//待收件  修改打印份数
		docService.updateViewPrintedNum(requestData);
		json.setRtState(true);
		return json;
	}
}
