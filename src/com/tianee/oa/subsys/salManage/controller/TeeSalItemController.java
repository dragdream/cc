package com.tianee.oa.subsys.salManage.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.salManage.bean.TeeSalAccount;
import com.tianee.oa.subsys.salManage.bean.TeeSalItem;
import com.tianee.oa.subsys.salManage.service.TeeSalItemService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/teeSalItemController")
public class TeeSalItemController {
	@Autowired
	private TeeSalItemService itemService;
	
	/**
	 * 新建或者更新
	 * @param request
	 * @param salItem
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeSalItem salItem) throws Exception {
		TeeJson json = itemService.addOrUpdate(salItem);
		return json;
	}
	/**
	 * @author CXT
	 * 新增工资项
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/addSalItem")
	@ResponseBody
	public TeeJson addSalItem(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try{
			String itemName = request.getParameter("itemName");
			String formula = request.getParameter("formula");
			String formulaName = request.getParameter("formulaName");
			String itemType = request.getParameter("itemType");
			String itemFlag = TeeStringUtil.getString(request.getParameter("itemFlag"),"0" );
			TeeSalItem salItem = new TeeSalItem();
			salItem.setItemName(itemName);
			salItem.setFormula(formula);
			salItem.setFormulaName(formulaName);
			salItem.setItemFlag(itemType);
			String itemColumn = itemService.getUseColumn(null, salItem);
			salItem.setItemColumn(itemColumn);
			salItem.setItemFlag(itemFlag);
			json = itemService.saveSalItem(salItem);
		}catch(Exception ex){
			json.setRtMsg("保存失败");
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * @author CXT
	 * 修改工资项
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/updateSalItem")
	@ResponseBody
	public TeeJson updateSalItem(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try{
			String sid = request.getParameter("sid");
			String itemName = request.getParameter("itemName");
			String formula = request.getParameter("formula");
			String formulaName = request.getParameter("formulaName");
			String itemType = request.getParameter("itemType");
			String itemFlag = TeeStringUtil.getString(request.getParameter("itemFlag"),"0" );
			TeeSalItem salItem = itemService.getSalItem(sid);
			//TeeSalItem salItem = new TeeSalItem();
			salItem.setItemName(itemName);
			salItem.setFormula(formula);
			salItem.setFormulaName(formulaName);
			salItem.setItemFlag(itemFlag);
			itemService.updateSalItem(salItem);
			json.setRtMsg("保存成功");
			json.setRtState(true);
		}catch(Exception ex){
			json.setRtMsg("保存失败");
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * @author CXT
	 * 删除工资项
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/deleteSalItem")
	@ResponseBody
	public TeeJson deleteSalItem(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try{
			String sid = request.getParameter("sid");
			itemService.deleteSalItem(sid);
			json.setRtMsg("删除成功");
			json.setRtState(true);
		}catch(Exception ex){
			json.setRtMsg("删除失败");
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * @author CXT
	 * 根据id获取工资项
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/getSalItem")
	@ResponseBody
	public TeeJson getSalItem(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try{
			String sid = request.getParameter("sid");
			TeeSalItem item = itemService.getSalItem(sid);
			json.setRtData(item);
			json.setRtMsg("获取成功");
			json.setRtState(true);
		}catch(Exception ex){
			json.setRtMsg("获取失败");
			json.setRtState(false);
		}
		return json;
	}
	/**
	 * @author nieyi
	 * 查询员工工资
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/querySalary")
	@ResponseBody
	public TeeJson querySalary(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try{
						
			//获取当前登录人工资账套
			TeePerson  person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
			Map map = TeeServletUtility.getParamMap(request);
			map.put(TeeConst.LOGIN_USER, person);
			List<Map> salayList = itemService.querySalary(map);
			json.setRtData(salayList);
			json.setRtMsg("获取成功");
			json.setRtState(true);
		}catch(Exception ex){
			json.setRtMsg("获取失败");
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * 个人薪资详情
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/querySalaryDetail")
	@ResponseBody
	public TeeJson querySalaryDetail(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try{
						
			//获取当前登录人工资账套
			TeePerson  person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
			Map map = TeeServletUtility.getParamMap(request);
			map.put(TeeConst.LOGIN_USER, person);
			json.setRtData(itemService.querySalaryDetail(map));
			json.setRtMsg("获取成功");
			json.setRtState(true);
		}catch(Exception ex){
			json.setRtMsg("获取失败");
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * @author kakalion
	 * 获取薪资基数
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/querySalaryBase")
	@ResponseBody
	public TeeJson querySalaryBase(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try{
						
			//获取当前登录人工资账套
			TeePerson  person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
			Map map = TeeServletUtility.getParamMap(request);
			map.put(TeeConst.LOGIN_USER, person);
			json.setRtData(itemService.querySalaryBase(map));
			json.setRtMsg("获取成功");
			json.setRtState(true);
		}catch(Exception ex){
			json.setRtMsg("获取失败");
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * @author kakalion
	 * 更新薪资基数
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/updateSalaryBase")
	@ResponseBody
	public TeeJson updateSalaryBase(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try{
						
			//获取当前登录人工资账套
			TeePerson  person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			Map map = TeeServletUtility.getParamMap(request);
			map.put(TeeConst.LOGIN_USER, person);
			itemService.updateSalaryBase(map);
		}catch(Exception ex){
			json.setRtState(false);
		}
		return json;
	}
	
	 /**
	  * spring mvc 返回mav 用el表达式获取值
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/sal_item_index.action")
	 public ModelAndView sal_item_index(HttpServletRequest request) {
		  ModelAndView mav = new ModelAndView("/system/subsys/salary/insurance_para/sal_item/salaryItemEdit.jsp");
		  TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		  List<TeeSalItem> salItemList = itemService.salItemList(0);
		  mav.addObject("salItemList", salItemList);
		  return mav;
	 }
	 
	/**
	 * @author nieyi
	 * 删除人员工资
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/delSalaryInfo")
	@ResponseBody
	public TeeJson delSalaryInfo(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try{
			String sids =TeeStringUtil.getString(request.getParameter("sids"), "0");
			itemService.delSalaryInfo(sids);
			json.setRtMsg("删除成功");
			json.setRtState(true);
		}catch(Exception ex){
			json.setRtMsg("删除失败");
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * @author nieyi
	 * 查询员工工资
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/queryPersonalSalary")
	@ResponseBody
	public TeeJson queryPersonalSalary(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try{
			Map requestDatas = TeeServletUtility.getParamMap(request);
			requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
			json.setRtData(itemService.querySalaryByCondition(requestDatas));
			json.setRtMsg("获取成功");
			json.setRtState(true);
		}catch(Exception ex){
			json.setRtMsg("获取失败");
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * @author nieyi
	 * 查询员工工资
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/getSalaryTitle")
	@ResponseBody
	public TeeJson getSalaryTitle(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try{
			List<Map> titleList = itemService.getSalaryTitle();
			json.setRtData(titleList);
			json.setRtMsg("获取成功");
			json.setRtState(true);
		}catch(Exception ex){
			json.setRtMsg("获取失败");
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * 获取工资项目  by 账套Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/getItemListByAccountId")
	@ResponseBody
	public TeeJson getItemListByAccountId(HttpServletRequest request ,  TeeSalItem item) {
		//int accountId = TeeStringUtil.getInteger(request.getParameter("accountId") , 0);
		List<TeeSalItem> itemList = itemService.salItemList(item.getAccountId());
		TeeJson json  = new TeeJson();
		json.setRtState(true);
		json.setRtData(itemList);
		return json;
	}
	
	/**
	 * 根据账套Id 获取 账套工资项，并获取保险参数字段 -- 计算项
	 * @param request
	 * @param item
	 * @return
	 */
	@RequestMapping("/getFormulaItemByAccountId")
	@ResponseBody
	public TeeJson getFormulaItemByAccountId(HttpServletRequest request ,  TeeSalItem item) {
		List<TeeSalItem> itemList = itemService.salItemList(item.getAccountId());
		TeeJson json  = new TeeJson();
		String itemTemp [] =  new String[]{"allBase:SYS_基本工资","sbBase:SYS_社保基数","gjjBase:SYS_公积金基数","pensionU:SYS_养老（单位）" , "pensionP:SYS_养老（个人）"
						,"medicalU:SYS_医疗（单位）" , "medicalP:SYS_医疗（个人）"
						,"fertilityU:SYS_生育（单位）" , "fertilityP:SYS_生育（个人）"
						,"unemploymentU:SYS_失业（单位）" , "unemploymentP:SYS_失业（个人）"
						,"injuriesU:SYS_工伤（单位）","injuriesP:SYS_工伤（个人）"
						,"housingU:SYS_公积金（单位）" , "housingP:SYS_公积金（个人）"
				};
		for (int i = 0; i < itemTemp.length; i++) {
			String tt = itemTemp[i];
			TeeSalItem tempItemp = new TeeSalItem();
			String ttTemp[] = tt.split(":");
			tempItemp.setItemColumn(ttTemp[0]);
			tempItemp.setItemName(ttTemp[1]);
			itemList.add(tempItemp);
		}
		json.setRtState(true);
		json.setRtData(itemList);
		return json;
	}
}
