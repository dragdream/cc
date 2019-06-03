package com.tianee.oa.core.base.email.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.base.email.model.TeeEmailBodyModel;
import com.tianee.oa.core.base.email.model.TeeEmailModel;
import com.tianee.oa.core.base.email.model.TeeWebEmailModel;
import com.tianee.oa.core.base.email.service.TeeEmailService;
import com.tianee.oa.core.base.email.service.TeeMailService;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/emailController")
public class TeeEmailController {
	@Autowired
	TeeEmailService emailService;
	@Autowired
	TeePersonService personService;
	@Autowired
	TeeMailService mailService;
	@Autowired
	private TeeSmsManager smsManager;

	/**
	 * 新增或者更新邮件
	 * 
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdateMail.action")
	@ResponseBody
	public TeeJson addOrUpdateMail(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		try {
			json = emailService.addOrUpdateMail(request);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 获取各种状态总数
	 * 
	 * @date 2014-3-9
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/getEmailCount")
	@ResponseBody
	public TeeJson getEmailCount(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<Map<String,String>> mailBoxList = emailService.getBoxListService(request);
		
		long receiveCount = mailService.getListCount(0, person);
		long saveCount = mailService.getListCount(1, person);
		long sendCount = mailService.getListCount(2, person);
		long deleteCount = mailService.getListCount(3, person);

		Map map = new HashMap();
		map.put("receiveCount", receiveCount);
		map.put("saveCount", saveCount);
		map.put("sendCount", sendCount);
		map.put("deleteCount", deleteCount);
		map.put("mailBoxList", mailBoxList);
		
		
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}

	/**
	 *  邮件列表（收件箱）
	 * @date 2014年6月10日
	 * @author 
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getEmailListByType")
	@ResponseBody
	public TeeEasyuiDataGridJson getEmailListByType(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeEmailModel model = new TeeEmailModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return emailService.getEmailListByTypeService(dm, request, model);
	}
	
	

	/**
	 *  邮件列表（已删除邮件箱）
	 * @date 2014年6月10日
	 * @author 
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getDelEmailList")
	@ResponseBody
	public TeeEasyuiDataGridJson getDelEmailList(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeEmailModel model = new TeeEmailModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return emailService.getDelEmailListService(dm, request, model);
	}
	
	
	
	
	
	/**
	 * 获取邮件详情，根据mail表的sid（收件箱）
	 * 
	 * @date 2014-3-9
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/getEmailDetailById")
	@ResponseBody
	public TeeJson getEmailDetailById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeEmailModel model = new TeeEmailModel();
        // 将request中的对应字段映值射到目标对象的属性中
        TeeServletUtility.requestParamsCopyToObject(request, model);
		json = emailService.getEmailDetailByIdService(request);
		return json;
	}
	
	
	/**
	 * 获取邮件详情，根据MailBody表的sid（草稿箱）
	 * 
	 * @date 2014-3-9
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/getEmailDetailByMailBodyId")
	@ResponseBody
	public TeeJson getEmailDetailByMailBodyId(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeEmailModel model = new TeeEmailModel();
        // 将request中的对应字段映值射到目标对象的属性中
        TeeServletUtility.requestParamsCopyToObject(request, model);
		json = emailService.getEmailDetailByMailBodyIdService(request);
		return json;
	}
	
	

	/**
	 * 阅读邮件详情（收件箱）
	 * 
	 * @date 2014-3-9
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/readEmailDetailById")
	@ResponseBody
	public TeeJson readEmailDetailById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeEmailModel model = new TeeEmailModel();
        // 将request中的对应字段映值射到目标对象的属性中
        TeeServletUtility.requestParamsCopyToObject(request, model);
		json = emailService.readEmailDetailByIdService(request);
		return json;
	}
	
	
	
	

	/**
	 *  邮件列表（已发送邮件箱）
	 * @date 2014年6月10日
	 * @author 
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getSendEmailList")
	@ResponseBody
	public TeeEasyuiDataGridJson getSendEmailList(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeEmailBodyModel model = new TeeEmailBodyModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return emailService.getSendEmailListService(dm, request, model);
	}
	
	
	/**
	 *  邮件列表（草稿邮件箱）
	 * @date 2014年6月10日
	 * @author 
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getEmailDraftBoxList")
	@ResponseBody
	public TeeEasyuiDataGridJson getEmailDraftBoxList(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeEmailBodyModel model = new TeeEmailBodyModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return emailService.getEmailDraftBoxListService(dm, request, model);
	}
	
	
	/**
	 *  邮件查询
	 * @date 2014年6月10日
	 * @author 
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/mailSearch")
	@ResponseBody
	public TeeEasyuiDataGridJson mailSearch(TeeDataGridModel dm, HttpServletRequest request){
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return emailService.mailSearch(dm, requestData);
	}
	
	

	/**
	 *  邮件列表（自定义邮箱）
	 * @date 2014年6月10日
	 * @author 
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getEmailBoxListById")
	@ResponseBody
	public TeeEasyuiDataGridJson getEmailBoxListById(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		return emailService.getEmailBoxListByIdService(dm, request);
	}
	
	
	
	
	
	
	
	/**
	 * 删除邮件（假删除）
	 * 
	 * @date 2014-3-9
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/delEmailById")
	@ResponseBody
	public TeeJson delEmailById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sidStr = request.getParameter("sidStr");
		mailService.delMail(sidStr,"");
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
	}
	
	
	/**
	 * 设置邮件阅读状态(收件箱/收邮件)
	 * @date 2014年6月22日
	 * @author 
	 * @param request
	 * @return
	 */
	@RequestMapping("/setEmailReadFlagById")
	@ResponseBody
	public TeeJson setEmailReadFlagById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		json = emailService.setEmailReadFlagByIdService(request);
		return json;
	}
	
	
	/**
	 * 设置全部邮件为已阅读状态（自定义邮箱）
	 * @date 2014年6月22日
	 * @author 
	 * @param request
	 * @return
	 */
	@RequestMapping("/setAllEmailReadFlagCustom")
	@ResponseBody
	public TeeJson setAllEmailReadFlagCustom(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		json = emailService.setAllEmailReadFlagCustom(request);
		return json;
	}
	
	
	/**
	 * 设置全部邮件为已阅读状态
	 * @date 2014年6月22日
	 * @author 
	 * @param request
	 * @return
	 */
	@RequestMapping("/setAllEmailReadFlag")
	@ResponseBody
	public TeeJson setAllEmailReadFlag(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		json = emailService.setAllEmailReadFlagService(request);
		return json;
	}
	
	
	
	/**
	 * 获取文件夹分类
	 * 
	 * @date 2014-3-9
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/getEmailBoxList")
	@ResponseBody
	public TeeJson getEmailBoxList(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		List<Map<String,String>> mailBoxList = emailService.getBoxListService(request);

		Map map = new HashMap();
		map.put("mailBoxList", mailBoxList);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	
	

	/**
	 * 获取系统登录人未阅读邮件总数
	 * @date 2014年6月25日
	 * @author 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getNotReadingCount")
	@ResponseBody
	public TeeJson getNOtReadingCount(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		long counter = emailService.getNotReadingCountService(request);
		Map map = new HashMap();
		map.put("notReadingCount", counter);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	

	/**
	 * 外部邮箱列表
	 * @date 2014年6月26日
	 * @author 
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getWebEmailList")
	@ResponseBody
	public TeeEasyuiDataGridJson getWebEmailList(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeWebEmailModel model = new TeeWebEmailModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return emailService.getWebEmailList(dm, request, model);
	}
	
	
	
	  /**
     * 删除
     * @date 2014年5月27日
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/deleteWebMailById")
    @ResponseBody
    public TeeJson deleteWebMailById(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        String sidStr = request.getParameter("sids");
        json = emailService.deleteWebMailById(sidStr);
        return json;
    }
	
    /**
     * 获取个人自定义分类
     * @author syl
     * @date 2014-6-28
     * @param request
     * @return
     */
    @RequestMapping("/getBoxListService")
    @ResponseBody
    public TeeJson getBoxListService(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        List<Map<String, String>> map = emailService.getBoxListService(request);
        json.setRtData(map);
        json.setRtState(true);
        return json;
    }
    
 
    /**
     * 获取邮件签阅详情
     * @param request
     * @return
     */
    @RequestMapping("/getSignDetail")
    @ResponseBody
    public TeeEasyuiDataGridJson getSignDetail(HttpServletRequest request,TeeDataGridModel dm) {
        int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
        return emailService.getSignDetail(sid,dm);
    }
    
  /**
   * 收回邮件
   * @param request
   * @return
   */
    @RequestMapping("/back")
    @ResponseBody
    public TeeJson back(HttpServletRequest request) {
       String ids=TeeStringUtil.getString(request.getParameter("ids"));
       return emailService.back(ids,request);
    	
    }

}
