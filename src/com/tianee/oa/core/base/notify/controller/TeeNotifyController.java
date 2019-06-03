package com.tianee.oa.core.base.notify.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.notify.bean.TeeNotify;
import com.tianee.oa.core.base.notify.bean.TeeNotifyInfo;
import com.tianee.oa.core.base.notify.model.TeeNotifyInfoModel;
import com.tianee.oa.core.base.notify.model.TeeNotifyModel;
import com.tianee.oa.core.base.notify.service.TeeNotifyService;
import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.core.general.service.TeeSysParaService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.system.initSysData.service.TeeInitSysDataService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeSysParaKeys;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 公告通知 新建 编辑 删除 查询等
 * @author zhp
 * @createTime 2013-12-26
 * @desc
 */
@Controller
@RequestMapping("/teeNotifyController")
public class TeeNotifyController {

	@Autowired
	@Qualifier("teeNotifyService")
	private TeeNotifyService notifyService;

	@Autowired
	private TeeSysParaService sysParaService;
	
	/**
	 * 新增或者更新
	 * @author syl
	 * @date 2014-3-12
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/addUpdateNotify")
	@ResponseBody
	public TeeJson addUpdateNotify(HttpServletRequest request) throws IOException{
		TeeJson json = notifyService.addOrUpdateNotify(request);
		return json;
	}
	
	
	/**
	 * 查看阅读公告人员详情
	 * @author syl
	 * @date 2014-3-14
	 * @param request
	 * @return
	 */
	@RequestMapping("/getNotifyInfo")
	@ResponseBody
	public TeeJson getNotifyInfo(HttpServletRequest request){
		TeeJson json  = notifyService.getNotifyInfo(request);
		return json;
	}
	
	
	/**
	 * 清空阅读公告人员详情
	 * @author syl
	 * @date 2014-3-14
	 * @param request
	 * @return
	 */
	@RequestMapping("/clearNotifyInfo")
	@ResponseBody
	public TeeJson clearNotifyInfo(HttpServletRequest request){
		TeeJson json  = notifyService.clearNotifyInfo(request);
		return json;
	}
	
	
	/**
	 * 获取未读的 公告通知
	 * @author zhp
	 * @createTime 2014-1-4
	 * @editTime 下午03:35:53
	 * @desc
	 */
	@RequestMapping("/getNoReadNotify")
	@ResponseBody
	public TeeEasyuiDataGridJson getNoReadNotify(TeeDataGridModel dm,HttpServletRequest request) {
		Map map = TeeServletUtility.getParamMap(request);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			
		return notifyService.getNotifyList(dm, map,loginPerson,0);
	}
	
	
	/**
	 * 获取已经阅读的公告通知
	 * @author zhp
	 * @createTime 2014-1-17
	 * @editTime 下午12:33:17
	 * @desc
	 */
	@RequestMapping("/getReadedNotify")
	@ResponseBody
	public TeeEasyuiDataGridJson getReadedNotify(TeeDataGridModel dm,HttpServletRequest request) {
		Map map = TeeServletUtility.getParamMap(request);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			
		return notifyService.getNotifyList(dm, map,loginPerson,1);
	}
	
	

	/**
	 * 查看公告   -- 个人查询
	 * @author syl
	 * @date 2014-3-13
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public TeeEasyuiDataGridJson query(TeeDataGridModel dm,HttpServletRequest request) {
		Map map = new HashMap<String, String>();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return notifyService.query(dm, request, loginPerson);
	}
	
	
	/**
	 * 查看公告   -- 管理查询
	 * @author syl
	 * @date 2014-3-13
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/managerQuery")
	@ResponseBody
	public TeeEasyuiDataGridJson managerQuery(TeeDataGridModel dm,HttpServletRequest request) {
		Map map = new HashMap<String, String>();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return notifyService.managerQuery(dm, request, loginPerson);
	}
	
	/**
	 * 删除BYId
	 * @author syl
	 * @date 2014-3-13
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/delNotifyById")
	@ResponseBody
	public TeeJson delNotifyById(HttpServletRequest request) {
		Map map = new HashMap<String, String>();
		return notifyService.delNotifyById( request);
	}
	

	
	
	/**
	 * by Id 
	 * @author syl
	 * @date 2014-3-12
	 * @param request
	 * @return
	 */
	@RequestMapping("/getNotifyById")
	@ResponseBody
	public TeeJson getNotifyById(HttpServletRequest request) {
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int id = TeeStringUtil.getInteger(request.getParameter("id"),0);
		TeeJson json = 	 notifyService.selectById(id);
		return json;
	}
	/**
	 * count 为条数 -1为全部 || state为 状态 0 未读 1已读 -1全部
	 * @author zhp
	 * @createTime 2014-2-13
	 * @editTime 下午02:08:09
	 * @desc
	 */
	@RequestMapping("/getNotifyByState")
	@ResponseBody
	public TeeJson getNotifyByState(HttpServletRequest request) {
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int count = TeeStringUtil.getInteger(request.getParameter("count"),-1);
		int state = TeeStringUtil.getInteger(request.getParameter("state"),-1);
		TeeJson json = new TeeJson();
		String subject = TeeStringUtil.getString(request.getParameter("subject"),"");
		TeeNotifyModel model = new  TeeNotifyModel();
		model.setSubject(subject);
		List list = null;
		try {
			list = notifyService.getAllNotifyListByState(loginPerson,state,count,model);
			json.setRtData(list);
			json.setRtState(true);
			json.setRtMsg("查询公告通知成功!");
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("获取公告通知失败!");
		}
		return json;
		
	}
	
	/**
	 * 审批  批准/不批准
	 * @author syl
	 * @date 2014-3-14
	 * @param request
	 * @return
	 */
	@RequestMapping("/audNotify")
	@ResponseBody
	public  TeeJson audNotify(HttpServletRequest request) {
		TeeJson json = notifyService.audNotify(request);
		return json;
	}
	
	/**
	 * 获取 要管理的 公告 自己创建的
	 * @author zhp
	 * @createTime 2014-1-20
	 * @editTime 下午08:52:56
	 * @desc
	 */
	@RequestMapping("/getManageNotify")
	@ResponseBody
	public TeeEasyuiDataGridJson getManageNotify(TeeDataGridModel dm,HttpServletRequest request) {
		Map map = new HashMap<String, String>();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			
		return notifyService.getManageNotify(request , dm, map,loginPerson,0);
	}
	
	/**
	 * 获取待审批 的公告通知
	 * @author zhp
	 * @createTime 2014-1-22
	 * @editTime 上午06:48:49
	 * @desc
	 */
	@RequestMapping("/getAudNotify")
	@ResponseBody
	public TeeEasyuiDataGridJson getAudNotify(TeeDataGridModel dm,HttpServletRequest request) {
		Map map = new HashMap<String, String>();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			
		return notifyService.getAudNotify(dm, map,loginPerson);
	}
	
	@RequestMapping("/getAuditedNotify")
	@ResponseBody
	public TeeEasyuiDataGridJson getAuditedNotify(TeeDataGridModel dm,HttpServletRequest request) {
		Map map = new HashMap<String, String>();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			
		return notifyService.getAuditedNotify(dm, map,loginPerson);
	}
	
	
	/**
	 * 查看信息
	 * @author syl
	 * @date 2014-3-13
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getNotify")
	@ResponseBody
	public TeeJson getNotify(HttpServletRequest request) throws Exception{
		TeeJson json = notifyService.setNotifyInfo(request);
		return json;
	}
	
	/**
	 * 更新公告人员 阅读状态
	 * @author zhp
	 * @createTime 2014-1-17
	 * @editTime 上午11:17:39
	 * @desc
	 */
	@RequestMapping("/updateNotifyState")
	@ResponseBody
	public TeeJson updateNotifyState(TeeDataGridModel dm,HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("id"),0);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		try {
			notifyService.updateNotifyState(sid,loginPerson);
		     json.setRtMsg("更新公告通知状态成功!");
		     json.setRtState(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setRtState(false);
		    json.setRtMsg("更新公告通知状态失败!");
		}
		return json;
	}
	
	/**
	 * 更新公告人员 阅读状态
	 * @author zhp
	 * @createTime 2014-1-17
	 * @editTime 上午11:17:39
	 * @desc
	 */
	@RequestMapping("/updateNotifyPublish")
	@ResponseBody
	public TeeJson updateNotifyPublish(TeeDataGridModel dm,HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		
		String publish = TeeStringUtil.getString(request.getParameter("publish"),"1");
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		try {
			 notifyService.updateNotifyPublish(sid , publish);
		     json.setRtMsg("更新公告通知状态成功!");
		     json.setRtState(true);
		} catch (Exception e) {
			e.printStackTrace();
			json.setRtState(false);
		    json.setRtMsg("更新公告通知状态失败!");
		}
		return json;
	}
	
	
	/**
	 * 新增或者更新公告参数
	 * @author syl
	 * @date 2014-3-14
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateNotifySysPara")
	@ResponseBody
	public TeeJson updateNotifySysPara(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String NOTIFY_AUDITING_ALL = TeeStringUtil.getString(request.getParameter(TeeSysParaKeys.NOTIFY_AUDITING_ALL),"");
		String NOTIFY_AUDITING_EXCEPTION = TeeStringUtil.getString(request.getParameter(TeeSysParaKeys.NOTIFY_AUDITING_EXCEPTION),"");
		String  NOTIFY_AUDITING_SINGLE = TeeStringUtil.getString(request.getParameter(TeeSysParaKeys.NOTIFY_AUDITING_SINGLE));
		
		TeeSysPara auditingAll = new TeeSysPara();//
		auditingAll.setParaName(TeeSysParaKeys.NOTIFY_AUDITING_ALL);
		auditingAll.setParaValue(NOTIFY_AUDITING_ALL);
		sysParaService.addUpdatePara(auditingAll);
		
		
		TeeSysPara auditingExceptoin = new TeeSysPara();//
		auditingExceptoin.setParaName(TeeSysParaKeys.NOTIFY_AUDITING_EXCEPTION);
		auditingExceptoin.setParaValue(NOTIFY_AUDITING_EXCEPTION);
		sysParaService.addUpdatePara(auditingExceptoin);
		
		
		TeeSysPara auditingSingle = new TeeSysPara();//
		auditingSingle.setParaName(TeeSysParaKeys.NOTIFY_AUDITING_SINGLE);
		auditingSingle.setParaValue(NOTIFY_AUDITING_SINGLE);
		sysParaService.addUpdatePara(auditingSingle);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取公告参数
	 * @author syl
	 * @date 2014-3-13
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/getNotifySysPara")
	@ResponseBody
	public TeeJson getNotifySysPara(TeeDataGridModel dm,HttpServletRequest request) {
		TeeJson json = notifyService.getNotifySysPara();
		return json;
	}
	
	/**
	 * 判断一下 是否需要审批 当前人是否需要审批
	 * @author zhp
	 * @createTime 2014-1-22
	 * @editTime 上午05:53:19
	 * @desc
	 */
	@RequestMapping("/getNotifyAduingPerson")
	@ResponseBody
	public TeeJson getNotifyAduingPerson(TeeDataGridModel dm,HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		boolean isNeedAdu = notifyService.isNeedAduing2Person(loginPerson);
		List list = notifyService.getNotifyAduingPerson();
		Map map = new HashMap<String, Object>();
		map.put("isNeedAdu", isNeedAdu);
		map.put("aduListPerson", list);
		try {
		     json.setRtMsg("获取公告审核权限人员成功!");
		     json.setRtState(true);
		     json.setRtData(map);
		} catch (Exception e) {
			e.printStackTrace();
			json.setRtState(false);
		    json.setRtMsg("获取公告审核权限人员失败!");
		}
		return json;
	}
	
	
	
	
	//全部已阅   公告
	/**
	 * 判断一下 是否需要审批 当前人是否需要审批
	 * @author zhp
	 * @createTime 2014-1-22
	 * @editTime 上午05:53:19
	 * @desc
	 */
	@RequestMapping("/readAll")
	@ResponseBody
	public TeeJson readAll(HttpServletRequest request) {
		Map map = TeeServletUtility.getParamMap(request);
		//获取当前登录的用户
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		//获取当前登录的用户未读公告列表
		List<TeeNotifyModel> list=notifyService.getNoReadNotifyList(map,loginUser,0);
		
		for (TeeNotifyModel teeNotifyModel : list) {
			//改变未读公告的状态
			notifyService.readNotify(teeNotifyModel.getSid(),0,loginUser);
		}
	
		json.setRtState(true);
		return json;
	}
	
	
	public TeeNotifyService getNotifyService() {
		return notifyService;
	}

	public void setNotifyService(TeeNotifyService notifyService) {
		this.notifyService = notifyService;
	}
	


	/**
	 * 批量刪除公告
	 * @param request
	 * @return
	 */
	@RequestMapping("/delNotifyBatch")
	@ResponseBody
	public TeeJson delNotifyBatch(HttpServletRequest request) {
		return notifyService.delNotifyBatch(request);
	}
	
	/**
	 * 删除cms网站下的相同附件
	 * @param request
	 * @return
	 */
	@RequestMapping("/delCmsAttachment")
	@ResponseBody
	public TeeJson delCmsAttachment(HttpServletRequest request) {
		return notifyService.delCmsAttachment(request);
	}
	

	/**
	 * 根据类型获取所有公告信息
	 * @author ly
	 * @createTime 2018-9-13
	 */
	@RequestMapping("/getNotifyByTypeId")
	@ResponseBody
	public TeeEasyuiDataGridJson getNotifyByTypeId(TeeDataGridModel dm,HttpServletRequest request) {
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return notifyService.getNotifyByTypeId(request ,dm,loginUser);
	}
	
}
