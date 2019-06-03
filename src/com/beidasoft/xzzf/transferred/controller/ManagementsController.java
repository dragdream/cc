package com.beidasoft.xzzf.transferred.controller;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.service.CommonService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.transferred.bean.DocManagements;
import com.beidasoft.xzzf.transferred.model.ManagementsModel;
import com.beidasoft.xzzf.transferred.service.ManagementsService;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/managementsCtrl")
public class ManagementsController {

	@Autowired
	private ManagementsService managementService;

	@Autowired
	private TeeWenShuService wenShuService;
	
	@Autowired
	private PunishFlowService flowService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private TeeAttachmentService teeAttachmentService;
	
	/**
	 * @param managementModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson save(ManagementsModel managementModel,HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		PunishFLow punishFLow = new PunishFLow();
		
		String manaAdd = managementModel.getManaAdd();
		String manaYear = managementModel.getManaYear();
		String manaNum = managementModel.getManaNum();
		String baseId = managementModel.getBaseId();
		
		//目录界面所有的信息   
		JSONArray manaList = JSONArray.fromObject(managementModel.getManaListStr());
		if (manaList.size() != 0) {
			for (int i = 0; i < manaList.size(); i++) {
				JSONObject object = manaList.getJSONObject(i);
				DocManagements docManagement = new DocManagements();
				
				docManagement.setBaseId(baseId);
				docManagement.setManaAdd(manaAdd);
				docManagement.setManaNum(manaNum);
				docManagement.setManaYear(manaYear);
				
				// 设置创建人相关信息   
				if (StringUtils.isBlank(object.getString("id"))) {
					docManagement.setId(UUID.randomUUID().toString());
					docManagement.setCreateUserId(user.getUserId());
					docManagement.setCreateUserName(user.getUserName());
					docManagement.setCreateTime(Calendar.getInstance().getTime());
				}
				// 设置创建人相关信息
				docManagement.setUpdateUserId(user.getUserId());
				docManagement.setUpdateUserName(user.getUserName());
				docManagement.setUpdateTime(Calendar.getInstance().getTime());
				
				//对象赋值
				String manageId = object.getString("manageId");
				docManagement.setManageId(manageId);
				String  titanicNumber = object.getString("titanicNumber");
				docManagement.setTitanicNumber(titanicNumber);
				String responsible = object.getString("responsible");
				docManagement.setResponsible(responsible);
				String manageTitle = object.getString("manageTitle");
				docManagement.setManageTitle(manageTitle);
				String transferredTime = object.getString("transferredTime");
				docManagement.setTransferredTime(transferredTime);
				String pageNumber = "";
				if(!"{}".equals(object.getString("pageNumber"))) {
					pageNumber = object.getString("pageNumber");
				} 
				docManagement.setPageNumber(pageNumber);
				String manageNote = object.getString("manageNote");
				docManagement.setManageNote(manageNote);
				
				String manaFilePath = object.getString("manaFilePath");
				docManagement.setManaFilePath(manaFilePath);
				
				String partyCaseName = object.getString("partyCaseName");
				docManagement.setPartyCaseName(partyCaseName);
				
				if("{}".equals(object.getString("id"))) {
					//自动生成目录信息表的主键ID
					docManagement.setId(UUID.randomUUID().toString());
				} else {
					docManagement.setId(object.getString("id"));
				}
				
				managementService.saveOrUpdate(docManagement);  
				json.setRtState(true);
			}
			
		}
		
		return json;
	}
	
	
	
	/**
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String baseId, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		List<DocManagements> manaGetList = managementService.getByBaseId(baseId);
		if (manaGetList.size() != 0) {
			json.setRtData(manaGetList);
		}
			
		return json;
		
	}
	
	/**
	 * 页次
	 * @param attachId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPDFPageNumById")     
	public TeeJson getPDFPageNumById(int attachId, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int manaGetList = teeAttachmentService.getPDFPageNumById(attachId);
		if (manaGetList != 0) {
			json.setRtData(manaGetList);
		}
		return json;
	}
	
	/**
	 * 获取案件名
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCaseName")     
	public TeeJson getCaseName(String baseId,HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String partyCaseName = managementService.getCaseName(baseId);
		json.setRtData(partyCaseName);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 初始化页面信息
	 * @param punishBase
	 * @param request
	 * @return
	 */
	@RequestMapping("/initCaseMenu")
	@ResponseBody
	public TeeJson initCaseMenu(PunishBase punishBase, HttpServletRequest request) {
		TeeJson result = new TeeJson();
		String baseId = request.getParameter("baseId");
		//办结人员
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<DocManagements> docManageList = managementService.initCaseMenu (baseId, person);
		result.setRtData(docManageList);
		result.setRtState(true);
		return result;
	}
	/**
	 * 预览文书
	 * @param punishBase
	 * @param request
	 * @return
	 */
	@RequestMapping("/lookCasePDF")
	@ResponseBody
	public TeeJson lookCasePDF(PunishBase punishBase, HttpServletRequest request) {
		TeeJson result = new TeeJson();
		String baseId = request.getParameter("baseId");
		//办结人员
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<DocManagements> docManageList = managementService.lookCasePDF (baseId, person);
		result.setRtData(docManageList);
		result.setRtState(true);
		return result;
	}
	
	/**
	 * 加载责任人信息
	 * @return
	 */
	@RequestMapping("/initCaseRespon")
	@ResponseBody
	public TeeJson initCaseRespon(PunishBase punishBase, HttpServletRequest request) {
		TeeJson result = new TeeJson();
		String baseId = request.getParameter("baseId");
		//办结人员
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String partyName = managementService.initCaseRespon (baseId);
		
		result.setRtData(partyName);
		result.setRtState(true);
		return result;
	}
	
//	/**
//	 * 加载文号信息
//	 * @return
//	 */
//	@RequestMapping("/initCaseMess")
//	@ResponseBody
//	public TeeJson initCaseMess(PunishBase punishBase, HttpServletRequest request) {
//		TeeJson result = new TeeJson();
//		String baseId = request.getParameter("baseId");
//		String messNum = managementService.initCaseMess (baseId);
//		result.setRtData(messNum);
//		result.setRtState(true);
//		return result;
//	}
}
