package com.tianee.oa.core.pending.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileNetdiskDao;
import com.tianee.oa.core.base.fileNetdisk.service.TeeFileNetdiskService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.topic.bean.TeeTopic;
import com.tianee.oa.subsys.topic.bean.TeeTopicReply;
import com.tianee.oa.subsys.topic.bean.TeeTopicSection;
import com.tianee.oa.subsys.topic.model.TeeTopicModel;
import com.tianee.oa.subsys.topic.service.TeeTopicReplyService;
import com.tianee.oa.subsys.topic.service.TeeTopicService;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller 
@RequestMapping("/dataMigrationController")
public class TeeDataMigrationController {
	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;
	@Autowired
	private TeeTopicService service;
	@Autowired
	private TeePersonService pservice;
	
	@Autowired
	private TeeTopicReplyService trservice;
	@Autowired
	private TeeFileNetdiskService fndService;

	@ResponseBody
	@RequestMapping("/topic")
	public JSONObject Topic(String parm,HttpServletRequest request) throws ParseException{
		
		//对传过来的json数据进行处理	
		String json=request.getParameter("json");
		JSONObject js = JSONObject.fromObject(json);
		String subject = String.valueOf(js.get("subject"));
		String content = String.valueOf(js.get("content"));
		int anonymous =TeeStringUtil.getInteger(js.get("anonymous"), 0);
		String crTime = String.valueOf(js.get("crTime"));
		String crUser = String.valueOf(js.get("crUser"));
		String section = String.valueOf(js.get("section"));
		String lastReplyUser = String.valueOf(js.get("lastReplyUser"));
		String lastReplyTime = String.valueOf(js.get("lastReplyTime"));
		int replyAmount =TeeStringUtil.getInteger(js.get("replyAmount"), 0);
		int isTop =TeeStringUtil.getInteger(js.get("isTop"), 0);
		int clickAccount =TeeStringUtil.getInteger(js.get("clickAccount"), 0);
		int flag =TeeStringUtil.getInteger(js.get("flag"), 0);
		
		String replys=String.valueOf(js.get("replys"));
		
		TeeTopic t=new TeeTopic();
		t.setAnonymous(anonymous);
		t.setClickAccount(clickAccount);
		t.setContent(content);
		Calendar crTime1= TeeDateUtil.parseCalendar(crTime);
		t.setCrTime(crTime1);
		TeePerson pCrUser=pservice.getPersonByUserId(crUser);
		t.setCrUser(pCrUser);
		t.setFlag(flag);
		t.setIsTop(isTop);
		Calendar lastReplyTime1= TeeDateUtil.parseCalendar(lastReplyTime);
		t.setLastReplyTime(lastReplyTime1);
		TeePerson plastReplyUser=pservice.getPersonByUserId(lastReplyUser);
		t.setLastReplyUser(plastReplyUser);
		t.setReplyAmount(replyAmount);
		TeeTopicSection section1=new TeeTopicSection();
		section1.setUuid(section);
		t.setSection(section1);
		t.setSubject(subject);		
		service.addTopic(t);
		
		
		
		JSONArray jsonArray=JSONArray.fromObject(replys);
		//Object o=jsonArray.get(0);
		for(int i=0;i<jsonArray.size();i++){
		    JSONObject jsonObject2=JSONObject.fromObject(jsonArray.get(i));
			String hcontent = String.valueOf(jsonObject2.get("hcontent"));
			String hcrUser = String.valueOf(jsonObject2.get("hcrUser"));
			String hcrTime = String.valueOf(jsonObject2.get("hcrTime"));
			int hanonymous =TeeStringUtil.getInteger(jsonObject2.get("hanonymous"), 0);
			
			TeeTopicReply tr=new TeeTopicReply();
			tr.setAnonymous(hanonymous);
			tr.setContent(hcontent);
			Calendar hcrTime1= TeeDateUtil.parseCalendar(hcrTime);
			tr.setCrTime(hcrTime1);
			TeePerson hcrUser1=pservice.getPersonByUserId(hcrUser);
			tr.setCrUser(hcrUser1);
			tr.setTopic(t);
			tr.setSection(section1);
			trservice.addTopicReply(tr);
			
		}
		
		
		//对传过来的数据进行处理	
		
		/*String content=request.getParameter("content");
		int anonymous=TeeStringUtil.getInteger(request.getParameter("anonymous"), 0);
		String crUser=request.getParameter("crUser");
		String crTime=request.getParameter("crTime");
		String section=request.getParameter("section");
		String lastReplyUser=request.getParameter("lastReplyUser");
		String lastReplyTime=request.getParameter("lastReplyTime");
		int replyAmount=TeeStringUtil.getInteger(request.getParameter("replyAmount"),0);
		int isTop=TeeStringUtil.getInteger(request.getParameter("isTop"),0);
		int flag=TeeStringUtil.getInteger(request.getParameter("flag"),0);
		int clickAccount=TeeStringUtil.getInteger(request.getParameter("clickAccount"),0);
		String hcontent=request.getParameter("hcontent");
		String hcrUser=request.getParameter("hcrUser");
		String hcrTime=request.getParameter("hcrTime");
		int hanonymous=TeeStringUtil.getInteger(request.getParameter("hanonymous"),0);*/
		
		
		
		String result ="{\"status\":\"添加成功\"}";

		//System.out.println(json.getRtData());
		return JSONObject.fromObject(result);
		
	}
	
	@RequestMapping("/addFileNetdisk")
	@ResponseBody
	public JSONObject addFileNetdisk(String parm,HttpServletRequest request) throws ParseException{
		TeeJson json=new TeeJson();
		//对传过来的json数据进行处理	
		/*JSONObject js = JSONObject.fromObject(parm);
		String fileName=String.valueOf(js.get("fileName"));
		int fileNo=TeeStringUtil.getInteger(js.get("fileNo"), 0);*/
		//对传过来的数据进行处理	
		String fileName=request.getParameter("fileName");
		int fileNo=TeeStringUtil.getInteger(request.getParameter("fileNo"), 0);
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			
		TeeFileNetdisk fileNetdisk = new TeeFileNetdisk();
		fileNetdisk.setFileNo(fileNo);
		fileNetdisk.setFileName(fileName);
		fileNetdisk.setCreater(person);
		fileNetdisk.setCreateTime(new Date());
		
		fndService.addFndService(person, fileNetdisk);
		fileNetdisk.setFileFullPath("/" + fileNetdisk.getSid() + "/");
		String result ="{\"status\":\"添加成功\"}";
		return JSONObject.fromObject(result);
	
	
	}

}
