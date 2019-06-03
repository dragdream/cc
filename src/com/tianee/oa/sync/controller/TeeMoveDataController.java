package com.tianee.oa.sync.controller;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.notify.bean.TeeNotify;
import com.tianee.oa.core.base.notify.bean.TeeNotifyInfo;
import com.tianee.oa.core.base.notify.dao.TeeNotifyDao;
import com.tianee.oa.core.base.notify.dao.TeeNotifyInfoDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workFlowFrame.creator.TeeFlowCreatorInterface;
import com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.controller.TeeWorkFlowRunController;
import com.tianee.oa.core.workflow.flowrun.model.TeeBisRunModel;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsService;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.sync.service.TeeMoveDataService;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/moveData")
public class TeeMoveDataController {

	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;

	@Autowired
	private TeePersonService personService;

	@Autowired
	private TeeWorkFlowServiceContextInterface flowServiceContext;
	
	@Autowired
	private TeeFlowCreatorInterface flowCreator;
	
	@Autowired
	private TeeMoveDataService moveDataService;
	
	@Autowired
	private TeeWorkflowHandlerInterface workflowHandler;
	
	@Autowired
	private TeeFlowRunPrcsService flowRunPrcsService;
	
	@Autowired
	private TeeFlowRunServiceInterface flowRunServiceInterface;
	
	@RequestMapping("/notify")
	@ResponseBody
	public JSONObject syncNotify(HttpServletRequest request)
			throws ParseException, Exception {
		Map map = request.getParameterMap();
		Map paramMap = transToMAP(map);
		
		String deptName = (String) paramMap.get("fromDept").toString();
		String userId = (String) paramMap.get("fromPerson");
		String subject = (String) paramMap.get("subject");
		String content = (String) paramMap.get("content");
		String shortContent = (String) paramMap.get("shortContent");
		String sendTimeStr = (String) paramMap.get("sendTime");
		String beginDateStr = (String) paramMap.get("beginDate");
		String endDateStr = (String) paramMap.get("endDate");
		String top = (String) paramMap.get("top");
		String typeId = (String) paramMap.get("typeId");
		String publish = (String) paramMap.get("publish");
		String allPriv = (String) paramMap.get("allPriv");
		String createDateStr = (String) paramMap.get("createDate");
		String contentStyle = (String) paramMap.get("contentStyle");
		String sendUsers = (String) paramMap.get("sendUsers");

		TeeNotify notify = new TeeNotify();
		boolean status = false;
		// 处理时间类型
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date sendTime = simpleDateFormat.parse(sendTimeStr);
		Date beginDate = simpleDateFormat.parse(beginDateStr);
		Date endDate = simpleDateFormat.parse(endDateStr);
		Date createDate = simpleDateFormat.parse(createDateStr);

		notify.setCreateDate(createDate);
		notify.setBeginDate(beginDate);
		notify.setEndDate(endDate);
		notify.setSendTime(sendTime);

		notify.setContent(content);// 公告内容html格式
		notify.setShortContent(shortContent);// 公告内容无html格式
		notify.setTypeId(typeId);// 公告类型系统编码
		notify.setSubject(subject);// 标题
		notify.setContentStyle(contentStyle);// 内容填写方式 0为html 1为电子文档（即word格式）
		notify.setTop(top);// "是否置顶  0-不置顶  1-置顶"
		notify.setPublish(publish);// "是否发布  0-未发布  1-已发布"
		notify.setDocContentSid(0);

		notify.setAllPriv(TeeStringUtil.getInteger(allPriv, 1));// 是否全范围发布
																// 0-范围发布
																// 1-全机构发布"
		// 设置发布部门
		Map deptMap = simpleDaoSupport.getMap(
				"select uuid as uuid from TeeDepartment where deptName = '"
						+ deptName + "'", null);
		int deptId = TeeStringUtil.getInteger(deptMap.get("uuid"), 0);
		notify.setFromDept(deptId);

		// 设置发布人员
		TeePerson fromPerson = personService.getPersonByUserId(userId);
		notify.setFromPerson(fromPerson);

		// 保存
		moveDataService.syncNotify(notify);

		// 设置阅读人员
		JSONArray reqJsonArray = JSONArray.fromObject(sendUsers);
		Serializable save=0;
		if (reqJsonArray != null && reqJsonArray.size() > 0) {
			for (int i = 0; i < reqJsonArray.size(); i++) {
				TeeNotifyInfo notifyInfo = new TeeNotifyInfo();
				// 解析json
				JSONObject js = reqJsonArray.getJSONObject(i);
				String userIdStr = (String) js.get("userId");// 用户id
				String readTime = (String) js.get("readTime");// 阅读时间
				int isRead = TeeStringUtil.getInteger(js.get("isRead"), 0);// "是否阅读，0-未读，1-已读"
				// 日期转换
				Date readTimeDate = simpleDateFormat.parse(readTime);
				// 获取用户信息
				TeePerson toUser = personService.getPersonByUserId(userIdStr);

				notifyInfo.setIsRead(isRead);
				notifyInfo.setReadTime(readTimeDate);
				notifyInfo.setToUser(toUser);
				notifyInfo.setNotify(notify);
				
				moveDataService.syncRecord(notifyInfo);
			}
		}
		status = true;
		String respJson = "{\"status\":" + status + "}";
		JSONObject respObject = JSONObject.fromObject(respJson);
		return respObject;
	}

	@RequestMapping("/dutyRecord")
	@ResponseBody
	public JSONObject syncRecord(HttpServletRequest request) throws Exception {
		
		boolean status = false;
		TeeFlowTypeServiceInterface flowTypeService = flowServiceContext
				.getFlowTypeService();
		//责任纪实流程类型 1024
		TeeFlowType ft = flowTypeService.get(1024);

		// 处理接收流程发起数据相关
		TeeBisRunModel bisRunModel = new TeeBisRunModel();

		Map<String, String> initDatas = new HashMap();
		Map<String, String> initVars = new HashMap();

		Enumeration _enum = request.getParameterNames();
		String key = null;
		String val = null;
		while (_enum.hasMoreElements()) {
			key = (String) _enum.nextElement();
			if (key.indexOf("DATA_") != -1 || key.indexOf("EXT_") != -1) {//
				initDatas.put(key, request.getParameter(key));
			} else if (key.indexOf("VAR_") != -1) {
				initVars.put(key, request.getParameter(key));
			} else if (key.indexOf("DOC_ID") != -1) {// 正文
				bisRunModel.setDocId(TeeStringUtil.getInteger(
						request.getParameter(key), 0));
			} else if (key.indexOf("ATTACH_IDS") != -1) {// 附件
				bisRunModel.setAttachIds(TeeStringUtil.getString(request
						.getParameter(key)));
			} else if (key.indexOf("DOC_AIP_ID") != -1) {// 版式正文
				bisRunModel.setDocAipId(TeeStringUtil.getInteger(
						request.getParameter(key), 0));
			}
		}
		//设置责任人签名宏控件的uuid
		String userId = (String) initDatas.get("DATA_责任人签名");
		TeePerson zerenPerson = personService.getPersonByUserId(userId);
		initDatas.put("DATA_责任人签名", zerenPerson.getUserName());
		//initDatas.put("EXT_责任人签名", TeeStringUtil.getString(zerenPerson.getUuid()));
		bisRunModel.setRunDatas(initDatas);

		//设置部门选择器控件uuid
		String deptName = (String) initDatas.get("DATA_部门");
		Map deptMap = simpleDaoSupport.getMap("select uuid as uuid from TeeDepartment where deptName = '"+deptName+"'", null);
		String uuid = TeeStringUtil.getString(deptMap.get("uuid"));
		if (deptMap != null) {
			initDatas.put("EXT_部门", uuid);
		}
		
		TeeFlowRunPrcs flowRunPrcs = null;
		
		Date currentDate = new Date();
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(currentDate);
		try {
			//创建流程
			flowRunPrcs = flowCreator.CreateNewWork(ft,zerenPerson , bisRunModel,
					true);
			status = true;
			//已办结流程
			flowRunPrcs.setFlag(4);
			flowRunPrcs.setEndTime(endTime);
			flowRunPrcsService.save(flowRunPrcs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TeeOperationException(e);
		}
		
		JSONObject respObject = null;
		if (flowRunPrcs != null) {
			int runId = flowRunPrcs.getFlowRun().getRunId();
			//更新为已保存状态
			workflowHandler.updateSaveStatus(runId);
			//设置已结束状态
			TeeFlowRun  flowRun=(TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class,runId);
			flowRun.setEndTime(endTime);
			flowRunServiceInterface.update(flowRun);
			//响应信息
			String respJson = "{\"status\":" + status + "}";
			respObject = JSONObject.fromObject(respJson);
		}
		return respObject;
	}
	
	 private Map transToMAP(Map parameterMap){
	      // 返回值Map
	      Map returnMap = new HashMap();
	      Iterator entries = parameterMap.entrySet().iterator();
	      Map.Entry entry;
	      String name = "";
	      String value = "";
	      while (entries.hasNext()) {
	          entry = (Map.Entry) entries.next();
	          name = (String) entry.getKey();
	          Object valueObj = entry.getValue();
	          if(null == valueObj){
	              value = "";
	          }else if(valueObj instanceof String[]){
	              String[] values = (String[])valueObj;
	              for(int i=0;i<values.length;i++){
	                  value = values[i] + ",";
	              }
	              value = value.substring(0, value.length()-1);
	          }else{
	              value = valueObj.toString();
	          }
	          returnMap.put(name, value);
	      }
	      return  returnMap;
	  }
}