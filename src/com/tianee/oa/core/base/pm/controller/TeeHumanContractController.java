package com.tianee.oa.core.base.pm.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.base.pm.bean.TeeHumanContract;
import com.tianee.oa.core.base.pm.model.TeeHumanContractModel;
import com.tianee.oa.core.base.pm.service.TeeHumanContractService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("TeeHumanContractController")
public class TeeHumanContractController {
	
	@Autowired
	private TeeHumanContractService contractService;
	
	@RequestMapping("/addHumanContract")
	@ResponseBody
	public TeeJson addHumanContract(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanContractModel humanContract = new TeeHumanContractModel();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cl = Calendar.getInstance();
			/*TeeHumanContractModel model =  contractService.getModelById(sid);
			model.setRenewDateDesc(sdf.format(cl.getTime()));
			contractService.updateHumanContract(model);*/
			TeeHumanContract contract = (TeeHumanContract)contractService.getContractById(sid);
			contract.setRenewDate(cl);
			contractService.getSimpleDaoSupport().update(contract);
		}
		
		TeeServletUtility.requestParamsCopyToObject(request, humanContract);
		humanContract.setRenewDateDesc("");
		TeeHumanContract contract = contractService.addHumanContract(humanContract);
		String attachmentSidStr = request.getParameter("attachmentSidStr");
		List<TeeAttachment> attachments = contractService.getAttachmentDao().getAttachmentsByIds(attachmentSidStr);
		if(attachments!= null && attachments.size()>0){
			for(TeeAttachment attach:attachments){
				attach.setModelId(String.valueOf(contract.getSid()));
				contractService.getSimpleDaoSupport().update(attach);
			}
		}
		
		json.setRtMsg("添加成功");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return contractService.datagird(dm, requestDatas);
	}
	
	/**
	 * 查询即将到期的合同
	 * @param dm
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping("/queryDueToContract")
	@ResponseBody
	public TeeEasyuiDataGridJson queryDueToContract(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return contractService.queryDueToContract(dm, requestDatas);
	}
	
	/**
	 * 查询已到期合同
	 * @param dm
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping("/queryExpiredContract")
	@ResponseBody
	public TeeEasyuiDataGridJson queryExpiredContract(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return contractService.queryExpiredContract(dm, requestDatas);
	}
	
	/**
	 * 查询全部合同
	 * @param dm
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping("/queryAllContract")
	@ResponseBody
	public TeeEasyuiDataGridJson queryAllContract(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return contractService.queryAllContract(dm, requestDatas);
	}
	
	
	
	@RequestMapping("/getModelById")
	@ResponseBody
	public TeeJson getModelById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String renew = TeeStringUtil.getString(request.getParameter("renew"), "0");
		String type = TeeStringUtil.getString(request.getParameter("type"), "0");
		TeeHumanContractModel model = contractService.getModelById(sid,type);
		if(renew.equals("1")){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String validateDateDesc = model.getEndTimeDesc();
				model.setValidTimeDesc(validateDateDesc);
				Calendar ecl = Calendar.getInstance();
				ecl.setTime(sdf.parse(validateDateDesc));
				ecl.add(Calendar.YEAR, 1);
				model.setEndTimeDesc(sdf.format(ecl.getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/updateHumanContract")
	@ResponseBody
	public TeeJson updateHumanContract(HttpServletRequest request) throws Exception{
		String attachmentSidStr = request.getParameter("attachmentSidStr");
		TeeJson json = new TeeJson();
		TeeHumanContractModel humanContractModel = new TeeHumanContractModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanContractModel);
		
		List<TeeAttachment> attachments = contractService.getAttachmentDao().getAttachmentsByIds(attachmentSidStr);
		if(attachments!= null && attachments.size()>0){
			for(TeeAttachment attach:attachments){
				attach.setModelId(String.valueOf(humanContractModel.getSid()));
				contractService.getSimpleDaoSupport().update(attach);
			}
		}
		
		contractService.updateHumanContract(humanContractModel);
		json.setRtState(true);
		json.setRtMsg("更新成功");
		return json;
	}
	
	
	@RequestMapping("/delHumanContract")
	@ResponseBody
	public TeeJson delHumanContract(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		contractService.delHumanContract(sid);
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
	}
	
}
