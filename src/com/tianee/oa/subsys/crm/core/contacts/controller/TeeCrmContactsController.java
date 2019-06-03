package com.tianee.oa.subsys.crm.core.contacts.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.contacts.model.TeeCrmContactsModel;
import com.tianee.oa.subsys.crm.core.contacts.service.TeeCrmContactsService;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmCustomerModel;
import com.tianee.oa.subsys.crm.core.customer.service.TeeCrmCustomerService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;


@Controller
@RequestMapping("/TeeCrmContactsController")
public class TeeCrmContactsController extends BaseController{
	@Autowired
	private TeeCrmContactsService contactsService;
	
	@Autowired
	private TeeCrmCustomerService customerService;
	
	/**
	 * 判断此客户下的该联系人是否已存在
	 * @param request
	 * @return
	 */
	@RequestMapping("/getContactsByName")
	@ResponseBody
	public TeeJson getContactsByName(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = contactsService.getContactsByName(request);
		return json;
	}
	
	/**
	 * 添加联系人
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request,TeeCrmContactsModel model) throws ParseException, IOException{
		TeeJson json = new TeeJson();
		json =contactsService.addOrUpdate(request,model);
		return json;
	}
	
	/**
	 * 查询联系人列表
	 * @param dm
	 * @param requestDatas
	 * @return
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, HttpServletRequest request) {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return contactsService.datagird(dm, requestDatas);
	}
	
	/**
	 * 获取联系人详情
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getContactsInfoBySid")
	public TeeJson getContactsInfoBySid(HttpServletRequest request,TeeCrmContactsModel model){
		TeeJson json = new TeeJson();
		json = contactsService.getContactsInfoBySid(request,model);
		return json;
	}
	
	/**
	 * 更换联系人所属负责人
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/changeManage")
	public TeeJson changeManage(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = contactsService.changeManage(request);
		return json;
	}
	
	/**
	 * 作废当前联系人-更改联系人状态为作废
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/cancel")
	public TeeJson cancel(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json=contactsService.cancel(request);
		return json;
		
	}
	
	/**
	 * 恢复联系人状态到撤销之前
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/recovery")
	public TeeJson recovery(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json=contactsService.recovery(request);
	    return json;
	}
	
	/**
	 * 详情页面删除此联系人
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delContacts")
	public TeeJson delContacts(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = contactsService.delContacts(request);
		return json;
	}
	
	/**
	 * 获取全部联系人数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectAllContacts")
	@ResponseBody
	public TeeJson selectAllContacts(HttpServletRequest request){
		TeeJson json = contactsService.selectAllContacts();
		return json;
	}
	
	/**
	 * 获取某客户下的联系人数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectReceiver")
	@ResponseBody
	public TeeJson selectReceiver(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List<TeeCrmContactsModel> list = contactsService.selectReceiver(request);
		if(list.size()>0){
			json.setRtData(list);
			json.setRtMsg("查询成功！");
			json.setRtState(true);
		}else{
			json.setRtMsg("查询失败！");
			json.setRtState(false);
		}
		
		return json;
	}
	
	
	
	/**
	 * 名片识别
	 * @param request
	 * @return
	 */
	@RequestMapping("/ScanCard")
	@ResponseBody
	public TeeJson ScanCard(HttpServletRequest request){
		return contactsService.ScanCard(request);
	}
}