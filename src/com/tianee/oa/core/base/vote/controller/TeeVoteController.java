package com.tianee.oa.core.base.vote.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianee.oa.core.base.vote.bean.TeeVote;
import com.tianee.oa.core.base.vote.bean.TeeVoteItem;
import com.tianee.oa.core.base.vote.model.TeeSaveVoteItemModel;
import com.tianee.oa.core.base.vote.model.TeeSaveVoteItemSubjectModel;
import com.tianee.oa.core.base.vote.model.TeeSaveVoteModel;
import com.tianee.oa.core.base.vote.model.TeeVoteItemModel;
import com.tianee.oa.core.base.vote.model.TeeVoteItemPersonModel;
import com.tianee.oa.core.base.vote.model.TeeVoteModel;
import com.tianee.oa.core.base.vote.service.TeeExportExcel;
import com.tianee.oa.core.base.vote.service.TeeVoteItemService;
import com.tianee.oa.core.base.vote.service.TeeVoteService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 
 * @author syl
 *
 */
@Controller
@RequestMapping("/voteManage")
public class TeeVoteController {
	@Autowired
	private TeeVoteService voteService;
	@Autowired
	private TeeVoteItemService itemService;
	

	/**
	 * @author syl
	 * 新增或者更新
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeeVoteModel model = new TeeVoteModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = voteService.addOrUpdate(request , model);
		return json;
	}
	

	/**
	 * 获取 权限获取投票          -- 管理  模块---   
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/selectPostVoteManager")
	@ResponseBody
	public TeeEasyuiDataGridJson selectPostVoteManager(TeeDataGridModel dm  , HttpServletRequest request ) throws ParseException {

		TeeVoteModel model = new TeeVoteModel();
	
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return voteService.datagrid(dm , request , model);
	}
	
	
	/**
	 * 删除  byId
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		TeeVoteModel model = new TeeVoteModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = voteService.deleteByIdService(request , model);
		return json;
	}
	
	
	/**
	 * 删除  所有会议室
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteAll")
	@ResponseBody
	public TeeJson deleteAll(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		TeeVoteModel model = new TeeVoteModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = voteService.deleteAll(request , model);
		return json;
	}
	
	
	

	/**
	 * 查询 byId
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		TeeVoteModel model = new TeeVoteModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = voteService.getById(request , model);
		return json;
	}
	
	/**
	 * 查询 byId
	 * @author CXT
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getVoteBySid")
	@ResponseBody
	public TeeJson getVoteBySid(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		TeeVoteModel model = new TeeVoteModel();
		String sid = request.getParameter("voteId");
		try{
			model = voteService.getVoteBySid(sid);
			json.setRtData(model);
			json.setRtState(true);
		}catch(Exception ex){
			json.setRtState(false);
			ex.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 根据id查找子项
	 * @author CXT
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getVoteListBySid")
	@ResponseBody
	public TeeJson getVoteListBySid(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		String sid = request.getParameter("voteId");
		try{
			List<TeeVoteModel> list = voteService.getVoteListBySid(sid);
			json.setRtData(list);
			json.setRtState(true);
		}catch(Exception ex){
			json.setRtState(false);
			ex.printStackTrace();
		}
		return json;
	}
	
	
	/**
	 * @author CXT
	 * 保存
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/saveVote")
	@ResponseBody
	public TeeJson saveVote(HttpServletRequest request) throws Exception {
		TeeJson json1 = new TeeJson();
		String jsonStr = request.getParameter("json");
		//System.out.println(jsonStr);
		//接收前台json结构，转换为json对象
		jsonStr = jsonStr.replaceAll("\n", "<br>").replaceAll("\\\n", "<br>");
		JSONObject json = JSONObject.fromObject(jsonStr);
		TeeSaveVoteModel model = (TeeSaveVoteModel) JSONObject.toBean(json, TeeSaveVoteModel.class);
		int itemNum =  model.getItemNum();
		int voteId = model.getVoteId();
		TeeVote vote = voteService.getVoteById(voteId);
		//清空相关子项、子选项
		voteService.formatVote(vote);
		
		vote.setItemNum(itemNum);
		//更新vote对象
		voteService.saveObj(vote);
		JSONArray jsonArray = model.getVote();
		//解析json层内结构，转为jsonlist
		List<TeeSaveVoteItemModel> list = (List<TeeSaveVoteItemModel>) JSONArray.toCollection(jsonArray, TeeSaveVoteItemModel.class);
		for(TeeSaveVoteItemModel itemModel : list){
			TeeVote voteChildren = new TeeVote();
			voteChildren.setParentVote(vote);
			voteChildren.setSubject(itemModel.getVoteSubject());
			voteChildren.setContent(itemModel.getContent());
			voteChildren.setMaxNum(itemModel.getMaxNum());
			voteChildren.setMinNum(itemModel.getMinNum());
			voteChildren.setVoteType(itemModel.getType()+"");
			voteChildren.setRequired(itemModel.getRequired());
			voteChildren.setIfContent(itemModel.getIfContent());
			voteChildren.setTextHtml(itemModel.getTextHtml());
			voteChildren.setVoteNo(itemModel.getVoteNo());
			String htmlForPreview = itemModel.getTextHtml().replaceAll("glyphicon glyphicon-remove", "");
			htmlForPreview = htmlForPreview.replaceAll("<span id='textareaSpan_"+itemModel.getVoteNo()+"'><font color='red'>说明：</font><br><textarea cols='50' rows='5' id='textarea_"+itemModel.getVoteNo()+"' name='textarea_"+itemModel.getVoteNo()+"'></textarea></span>", "");
			if(itemModel.getType()==4){
				htmlForPreview = htmlForPreview.replaceAll("</td><td>","");
				htmlForPreview = htmlForPreview.replaceAll("<table><tbody><tr><td>","");
				htmlForPreview = htmlForPreview.replaceAll("</td></tr></tbody></table>","");
				htmlForPreview = htmlForPreview.replaceAll("<div","<option><div");
				htmlForPreview = htmlForPreview.replaceAll("</div>","</div></option>");
				htmlForPreview = "<select id='select_"+itemModel.getVoteNo()+"'> " + htmlForPreview + "</select>";
			}
			//System.out.println(htmlForPreview);
			voteChildren.setHtmlForPreview(htmlForPreview);
			voteService.saveObj(voteChildren);
			//获取子项中的各个选项
			List<TeeSaveVoteItemSubjectModel> subjectList = (List<TeeSaveVoteItemSubjectModel>) JSONArray.toCollection(itemModel.getItem(), TeeSaveVoteItemSubjectModel.class);
			for(TeeSaveVoteItemSubjectModel subject : subjectList){
				TeeVoteItem itemSubject = new TeeVoteItem();
				itemSubject.setItemName(subject.getItemSubject());
				itemSubject.setVote(voteChildren);
				itemSubject.setVoteNo(subject.getVoteNo());
				voteService.saveVoteItem(itemSubject);
			}
		}
		json1.setRtMsg("保存成功");
		return json1;
	}
	
	 /**
	  * spring mvc 返回mav 用el表达式获取值
	  * 接收json数据
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/votePreview/{json}")
	 public ModelAndView mailList(HttpServletRequest request,@PathVariable("json") String json) {
		  ModelAndView mav = new ModelAndView("/system/core/base/vote/item/preview.jsp");
		  TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		  //String json = request.getParameter("json");
		  //System.out.println(json);
		  //mav.addObject("color", mailColor);
		  return mav;
	  }
	 
	 
	/**
	 * 获取投票权限          
	 * @author CXT
	 * @date 2014-3-25
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/getPostVote")
	@ResponseBody
	public TeeEasyuiDataGridJson getPostVote(TeeDataGridModel dm  , HttpServletRequest request ) throws ParseException {

		TeeVoteModel model = new TeeVoteModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return voteService.voteDatagrid(dm , request , model);
	}
	
	/**
	 * 更新状态、删除
	 * @author CXT
	 * @date 2014-3-27
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAddUpdatePerson.action")
	@ResponseBody
	public TeeJson toAddUpdatePerson(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		String sid = request.getParameter("sid");
		String status = request.getParameter("status");
		try{
			voteService.toAddUpdatePerson(sid,status);
			json.setRtMsg("操作成功");
			json.setRtState(true);
		}catch(Exception ex){
			ex.printStackTrace();
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * 根据id查找子项
	 * @author CXT
	 * @date 2014-3-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getVoteItemListBySid")
	@ResponseBody
	public TeeJson getVoteItemListBySid(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		String sid = request.getParameter("voteId");
		try{
			List<TeeVoteItemModel> list = voteService.getVoteItemListBySid(sid);
			json.setRtData(list);
			json.setRtState(true);
		}catch(Exception ex){
			json.setRtState(false);
			ex.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 保存个人投票记录
	 * @author CXT
	 * @date 2014-3-30
	 * @param request
	 * @param model
	 * @return
	 */
	/**
	 * @param request
	 * @return
	 */
	@RequestMapping("/savePersonalVote")
	@ResponseBody
	public TeeJson savePersonalVote(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		try{
			TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			int voteId = Integer.parseInt(request.getParameter("voteId"));
			
			int checkFlag = 0;
			boolean privFlag = voteService.getVotesPriv(voteId, loginUser);
			if(!privFlag){//无权限
				checkFlag = 1;
			}else {
				boolean isVoteFlag = voteService.checkVote(voteId,loginUser);
				if(isVoteFlag){
					checkFlag = 2;//已投票
				}
			}
			
			if(checkFlag ==0){
				String anonymity = request.getParameter("anonymity");
				
				List<TeeVoteItemPersonModel> models = new ArrayList();
				Enumeration<String> enumer = request.getParameterNames();
				String key = null;
				while(enumer.hasMoreElements()){
					key = enumer.nextElement();
					if(key.indexOf("item")!=-1){
						TeeVoteItemPersonModel m = new TeeVoteItemPersonModel();
						m.setVoteData(request.getParameter(key));
						m.setVoteItemId(Integer.valueOf(key.replace("item", "")));
						models.add(m);
					}
				}
				voteService.savePersonalVote(voteId, anonymity, models, loginUser);
			}
			
			Map map = new HashMap();
			map.put("checkFlag", checkFlag);
			json.setRtData(map);
			json.setRtMsg("保存成功");
			json.setRtState(true);
		}catch(Exception ex){
			json.setRtState(false);
			json.setRtMsg("保存失败");
			ex.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 获得文本内容
	 * @author CXT
	 * @date 2014-3-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getVoteData.action")
	@ResponseBody
	public TeeJson getVoteData(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		try{
			TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			String id = request.getParameter("id");
			String voteId = request.getParameter("voteId");
			List<TeeVoteItemPersonModel> list  = voteService.getVoteData(Integer.parseInt(id),Integer.parseInt(voteId));
			json.setRtData(list);
			json.setRtState(true);
		}catch(Exception ex){
			json.setRtState(false);
			ex.printStackTrace();
		}
		return json;
	}
	/**
	 * @author CXT
	 *  格式化投票项
	 * @date 2014-3-14
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/clearResult.action")
	@ResponseBody
	public TeeJson clearResult(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		try{
			String voteId = request.getParameter("voteId");
			TeeVote vote = voteService.getVoteById(Integer.parseInt(voteId));
			voteService.clearResult(vote);
			json.setRtState(true);
			json.setRtMsg("结果已清空！");
		}catch(Exception ex){
			json.setRtState(false);
			json.setRtMsg("结果清空失败！");
			ex.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 查询投票项是否已经有选项
	 * @author CXT
	 * @date 2014-7-16
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/checkVoteItemNum")
	@ResponseBody
	public TeeJson checkVoteItemNum(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		String sid = request.getParameter("sid");
		try{
			TeeVote vote = voteService.getVoteById(Integer.parseInt(sid));
			json.setRtData(vote.getItemNum());
			json.setRtState(true);
		}catch(Exception ex){
			json.setRtState(false);
			ex.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 获取投票结果
	 * @param request
	 * @return
	 */
	@RequestMapping("/getVoteResult")
	@ResponseBody
	public TeeJson getVoteResult(HttpServletRequest request ) {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtState(true);
		json.setRtData(voteService.getVoteResult(sid,person));
		return json;
	}
	
	/**
	 * 获取投票
	 * @param request
	 * @return
	 */
	@RequestMapping("/getVotes")
	@ResponseBody
	public TeeJson getVotes(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtState(true);
		json.setRtData(voteService.getVotes(sid));
		return json;
	}
	
	/**
	 * js全局替换
	 * @author CXT
	 * @date 2014-7-19
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/replaceAll")
	@ResponseBody
	public TeeJson replaceAll(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		String html = request.getParameter("html");
		String str1 = request.getParameter("str1");
		String str2 = request.getParameter("str2");
		//System.out.println(html);
		try{
	        html = html.replaceAll(","+str1+"\\)",","+str2+"\\)");
	        html = html.replaceAll("_"+str1+"\"","_"+str2+"\"");
	        html = html.replaceAll("\\("+str1+"\\)","\\("+str2+"\\)");
			//System.out.println(html);
			json.setRtData(html);
			json.setRtState(true);
		}catch(Exception ex){
			json.setRtState(false);
			ex.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 获取我的投票数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/getMyVoteItemsData")
	@ResponseBody
	public TeeJson getMyVoteItemsData(HttpServletRequest request ) {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtState(true);
		json.setRtData(voteService.getMyVoteItemsData(sid,person));
		return json;
	}
	
	/**
	 * 检测是否已经投票过
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/checkVote")
	@ResponseBody
	public TeeJson checkVote(HttpServletRequest request ) throws ParseException {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int voteId = TeeStringUtil.getInteger(request.getParameter("voteId"), 0);
		TeeJson json = new TeeJson();
		int checkFlag = 0; //0-可以投票；1-无权限投票；2-已投票
		
		boolean privFlag = voteService.getVotesPriv(voteId, person);
		if(!privFlag){//无权限
			checkFlag = 1;
		}else {
			boolean isVoteFlag = voteService.checkVote(voteId,person);
			if(isVoteFlag){
				checkFlag = 2;//已投票
			}
		}
		
		Map map = new HashMap();
		map.put("checkFlag", checkFlag);
		json.setRtState(true);
		json.setRtData(map);
		return json;
	}
	
	
	/**
	 * @function: 删除对象
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/deleteObjById")
	@ResponseBody
	public TeeJson deleteObjById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		// 将request中的对应字段映值射到目标对象的属性中
		Map requestMap = TeeServletUtility.getParamMap(request);
		String sids = TeeStringUtil.getString(requestMap.get("sids"), "0");
		json = voteService.deleteObjById(sids);
		return json;
	}
	
	/**
	 * @function: 更新发布状态
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/updatePublish")
	@ResponseBody
	public TeeJson updatePublish(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		// 将request中的对应字段映值射到目标对象的属性中
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = voteService.updatePublish(requestMap);
		return json;
	}
	
	
	/**
	 * @function: 查看投票结果权限
	 * @author: wyw
	 * @data: 2015年6月26日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/isVotePrivById")
	@ResponseBody
	public TeeJson isVotePrivById(HttpServletRequest request ) {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		// 将request中的对应字段映值射到目标对象的属性中
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = voteService.isVotePrivById(requestMap,person);
		return json;
	}
	/**
	 * @function: 清空投票数据
	 * @author: wyw
	 * @data: 2015年6月26日
	 * @param request
	 * @return TeeJson
	 * @throws ParseException 
	 */
	@RequestMapping("/clearVoteById")
	@ResponseBody
	public TeeJson clearVoteById(HttpServletRequest request ) throws ParseException {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		// 将request中的对应字段映值射到目标对象的属性中
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = voteService.clearVoteById(requestMap,person);
		return json;
	}
	
	/**
	 * @function: 获取未投票/已投票人员数据
	 * @author: wyw
	 * @data: 2015年6月26日
	 * @param request
	 * @return TeeJson
	 * @throws ParseException 
	 */
	@RequestMapping("/getVotePersonCount")
	@ResponseBody
	public TeeJson getVotePersonCount(HttpServletRequest request ) throws ParseException {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		// 将request中的对应字段映值射到目标对象的属性中
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = voteService.getVotePersonCount(requestMap,person);
		return json;
	}
	
	
	
	/**
	 * 管理员获取投票结果
	 * @function: 
	 * @author: wyw
	 * @data: 2015年6月27日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/getManageVoteResult")
	@ResponseBody
	public TeeJson getManageVoteResult(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		try {
			TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
			json.setRtState(true);
			json.setRtData(voteService.getManageVoteResult(sid,person));
			
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("获取数据失败！");
		}
		return json;
	}
	
	
	/**
	 * @function: 获取投票情况
	 * @author: wyw
	 * @data: 2015年6月26日
	 * @param request
	 * @return TeeJson
	 * @throws ParseException 
	 */
	@RequestMapping("/showVoteDetail")
	@ResponseBody
	public TeeJson showVoteDetail(HttpServletRequest request ) throws ParseException {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		// 将request中的对应字段映值射到目标对象的属性中
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = voteService.showVoteDetail(requestMap,person);
		return json;
	}
	
	/**
	 * @function: 提醒所有未投票人
	 * @author: wyw
	 * @data: 2015年6月26日
	 * @param request
	 * @return TeeJson
	 * @throws ParseException 
	 */
	@RequestMapping("/remindVoteById")
	@ResponseBody
	public TeeJson remindVoteById(HttpServletRequest request ) throws ParseException {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		// 将request中的对应字段映值射到目标对象的属性中
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = voteService.remindVoteById(requestMap,person);
		return json;
	}
	
	/**
	 * 导出Excle文件
	 * @function: 
	 * @author: wyw
	 * @data: 2015年7月16日
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception String
	 */
	@RequestMapping("/exportExcle")
	public String export(HttpServletRequest request,HttpServletResponse response) throws Exception {
		OutputStream ops = null;
		try {
			TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			// 将request中的对应字段映值射到目标对象的属性中
			Map requestMap = TeeServletUtility.getParamMap(request);
			requestMap.put(TeeConst.LOGIN_USER, person);
			
			String dateStr = TeeDateUtil.getCurDateTimeStr().replace(":", "_");
			String fileName = URLEncoder.encode("投票结果 _" + dateStr+ ".xls", "UTF-8");
			fileName = fileName.replaceAll("\\+", "%20");
			response.setHeader("Cache-control", "private");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
			ops = response.getOutputStream();
			
			ArrayList<TeeDataRecord> dbL = voteService.getDbRecord(requestMap);
			TeeExportExcel.writeExc(ops, dbL);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			ops.close();
		}
		return null;
	}
	
	
}
