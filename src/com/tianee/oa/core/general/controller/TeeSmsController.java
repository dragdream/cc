package com.tianee.oa.core.general.controller;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.general.service.TeeSmsService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.cache.CacheConst;
import com.tianee.webframe.util.cache.RedisClient;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("sms")
public class TeeSmsController {
	@Autowired
	TeeSmsService smsService;
	
	@Autowired
	TeeSmsSender smsSender;
	
	/**
	 * 从session中检查是否有需要提醒的短信
	 */
	@RequestMapping("/checkSessionSmsList.action")
	@ResponseBody
	public int checkSessionSmsList(HttpServletRequest request){
		int result = TeeStringUtil.getInteger(request.getSession().getAttribute(TeeConst.SMS_FLAG), 0);
		return result;
	}
	/**
	 * 发送短信
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addSms.action")
	@ResponseBody
	public TeeJson addSms(HttpServletRequest request){
		
		TeeJson json = new TeeJson();
		
		String userListIds = request.getParameter("userListIds");
		String content = request.getParameter("content");
		String sendTime = request.getParameter("sendTime");
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int fromId = person.getUuid();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;

		try {
			if(!TeeUtility.isNullorEmpty(sendTime)){
				date = sdf.parse(sendTime);
			}else{
				date = new Date();
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map requestData = new HashMap();
		requestData.put("userListIds",userListIds);
		requestData.put("content",content);
		requestData.put("sendTime",sendTime);
		
		smsSender.sendSms(requestData, person);
		
		json.setRtMsg("发送成功");
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取发送短信列表
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSendSmsList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getSendSmsList(TeeDataGridModel dm,HttpServletRequest request){
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int fromId = person.getUuid();
		return smsService.getSendSmsList(dm,String.valueOf(fromId));
	}
	
	@RequestMapping("/getSmsBoxDatas.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getSmsBoxDatas(TeeDataGridModel dm,HttpServletRequest request){
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//设置短信标记不接收
		request.getSession().setAttribute(TeeConst.SMS_FLAG, 0);
		int toId = person.getUuid();
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put("toId", toId);
		return smsService.smsDatas(dm, requestData);
	}
	
	/**
	 * 获取收到短信列表
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getReceiveSmsList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getReceiveSmsList(TeeDataGridModel dm,HttpServletRequest request){
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int fromId = person.getUuid();
		return smsService.receiveDatagrid(dm,String.valueOf(fromId));
	}
	
//	/**
//	 * 桌面收到短信列表
//	 * @param request
//	 * @param para
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/getPortletReceiveSmsList.action")
//	@ResponseBody
//	public List<TeeSmsModel> getPortletReceiveSmsList(TeeDataGridModel dm,HttpServletRequest request){
//		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
//		int fromId = person.getUuid();
//		TeeJsonUtil ju = new TeeJsonUtil();
//		List<TeeSmsModel> list = smsService.portletReceiveDatagrid(String.valueOf(fromId));
//		//System.out.println(ju.toJson(list));
//		return list;
//	}
	
	  /**
	   * 标记为读
	   * @param request
	   * @param response
	   * @return
	   * @throws Exception
	   */
	@RequestMapping("/resetFlag.action")
	@ResponseBody
	public TeeJson resetFlag(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    int toId = person.getUuid();
	    String smsIds = request.getParameter("seqId");
	    //System.out.println(toId+","+smsIds);
	    smsService.resetFlag(toId+"",smsIds);
	    json.setRtMsg("更新成功");
		json.setRtState(true);
		
		return json;
	}
	
	  /**
	   * 标记为已读
	   * @param request
	   * @param response
	   * @return
	   * @throws Exception
	   */
	@RequestMapping("/updateReadFlag.action")
	@ResponseBody
	public TeeJson updateReadFlag(HttpServletRequest request){
		TeeJson json = new TeeJson();
	    String ids = request.getParameter("ids");
	    if(!TeeUtility.isNullorEmpty(ids)){
	    	smsService.updateReadFlag("'"+ids+"'");
	    }
	    json.setRtMsg("更新成功");
		json.setRtState(true);
		
		return json;
	}
	  /**
	   * 获取信息
	   * @param request
	   * @param response
	   * @return
	   * @throws Exception
	   */
	@RequestMapping("/remindCheck.action")
	@ResponseBody
	  public TeeJson remindCheck(HttpServletRequest request) throws Exception{
	      TeeJson json = new TeeJson(); 
//	      long data = 0;
//
	      TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
//	      if(person != null){
//	        int personUuid = person.getUuid();
//	        data = smsService.remindCheck(String.valueOf(personUuid));
//	      }
	      Map data = new HashMap();
    	  
    	  data.put("smsFlag", TeeStringUtil.getInteger(RedisClient.getInstance().get(CacheConst.USER_SMS_FLAG_PREFIX+person.getUserId().hashCode()), 0));
    	  data.put("msgFlag", TeeStringUtil.getInteger(RedisClient.getInstance().get(CacheConst.USER_MSG_FLAG_PREFIX+person.getUserId().hashCode()), 0));
    	  data.put("grpMsgFlag", TeeStringUtil.getInteger(RedisClient.getInstance().get(CacheConst.GRP_MSG_FLAG_PREFIX+person.getUserId().hashCode()), 0));
    	  RedisClient.getInstance().set(CacheConst.USER_SMS_FLAG_PREFIX+person.getUserId().hashCode(),"0");
    	  RedisClient.getInstance().set(CacheConst.USER_MSG_FLAG_PREFIX+person.getUserId().hashCode(),"0");
    	  RedisClient.getInstance().set(CacheConst.GRP_MSG_FLAG_PREFIX+person.getUserId().hashCode(),"0");
    	  
	      json.setRtData(data);
	      
	      
	      json.setRtMsg("查询成功");
	      json.setRtState(true);
	      return json;
	  }
	
	/**
	 * 弹出消息修改sms状态
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/popup.action")
	@ResponseBody
	  public TeeJson popup(HttpServletRequest request) throws Exception{
	      TeeJson json = new TeeJson(); 
	      TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	      if(person != null){
	        int personUuid = person.getUuid();
	        smsService.popup(String.valueOf(personUuid));
	      }
	      return json;
	  }
	
	/**
	   * 
	   * @param request
	   * @param response
	   * @return
	   * @throws Exception
	   */
	@RequestMapping("/getNewsSmsForBox.action")
	@ResponseBody
	  public String getNewsSmsForBox(HttpServletRequest request,
	      HttpServletResponse response) throws Exception{
		  TeeJson json = new TeeJson();
	      TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	      int toId = person.getUuid();
	      String data = smsService.getRemindInBox(String.valueOf(toId));
	      PrintWriter pw = response.getWriter();
	      pw.write(data);
	      pw.flush();
	      pw.close();
	    return null;
	  }
	
		/**
	   * 查看详情
	   * @param request
	   * @param response
	   * @return
	   * @throws Exception
	   */
	@RequestMapping("/viewDetails.action")
	@ResponseBody
	  public TeeJson viewDetails(HttpServletRequest request,
	      HttpServletResponse response) throws Exception{
	    String smsIds = request.getParameter("smsIds");
	    TeeJson json = new TeeJson();
	    TeeJsonUtil ju = new TeeJsonUtil();
	    try {
	      TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	      int toId = person.getUuid();
	      List<Map<String, String>> data = smsService.viewDetails(smsIds,toId);
	      String ss = ju.toJson(data);
	      //System.out.println(data);
	      json.setRtData(data);
	      json.setRtMsg("获取数据成功");
	      json.setRtState(true);
	    } catch (Exception e) {
	      throw e;
	    }
	    return json;
	  }
	
	 /**
	   * 收信人逻辑删除
	   * @param request
	   * @param response
	   * @return
	   * @throws Exception
	   */
	@RequestMapping("/delSms.action")
	@ResponseBody
	  public TeeJson delSms(HttpServletRequest request) throws Exception{
	      TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		  TeeJson json = new TeeJson();
	      String smsIdsStr = TeeStringUtil.getString(request.getParameter("smsIds"));
	      String smsIds[] = TeeStringUtil.parseStringArray(smsIdsStr);
	      
	      for(String smsSid:smsIds){
	    	  smsService.delSms(smsSid);
	      }
	      
	      json.setRtMsg("删除成功");
	      json.setRtState(true);
	    return json;
	  }
	
	/**
	 * 发信人删除
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delSmsBody.action")
	@ResponseBody
	  public TeeJson delSmsBody(HttpServletRequest request) throws Exception{
	      TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	      TeeJson json = new TeeJson();
	      String smsBodyIdsStr = TeeStringUtil.getString(request.getParameter("smsBodyIds"));
	      String smsBodyIds[] = TeeStringUtil.parseStringArray(smsBodyIdsStr);
	      
	      for(String smsBodySid:smsBodyIds){
	    	  smsService.delSmsBody(smsBodySid);
	      }
	      
	      json.setRtMsg("删除成功");
	      json.setRtState(true);
	    return json;
	  }
	
	  /**
	   * 标记为读
	   * @param request
	   * @param response
	   * @return
	   * @throws Exception
	   */
	@RequestMapping("/delSmsList.action")
	@ResponseBody
	public TeeJson delSmsList(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String id = request.getParameter("id");
	    String deleteFlag = request.getParameter("deleteFlag");
	    smsService.delSmsList(Integer.parseInt(id),Integer.parseInt(deleteFlag));
	    json.setRtMsg("删除成功");
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 全部已阅
	 * @param userId
	 */
	@RequestMapping("/viewAll.action")
	@ResponseBody
	public TeeJson viewAll(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = 
				(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		smsService.viewAll(loginPerson.getUuid());
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取当前待办的消息分组
	 * @param userId
	 */
	@RequestMapping("/getUnreadSmsGroup.action")
	@ResponseBody
	public List<Map> getUnreadSmsGroup(HttpServletRequest request) {
		TeePerson loginPerson = 
				(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		return smsService.getUnreadSmsGroup(loginPerson.getUuid());
	}
}
