package com.tianee.oa.core.pending.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import java.util.UUID;

import net.sf.json.JSONObject;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.pending.bean.TeeCommonHandler;
import com.tianee.oa.core.pending.service.TeeCommonHandlerService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;


@Controller
@RequestMapping("/saveInterface")
public class TeeSaveInterfaceController {
	
	@Autowired
	private TeeCommonHandlerService CHandlerService;
	@Autowired
	private TeePersonService personService;
	@Autowired
	private TeeSmsSender smsService;
	 
	//待办的同步添加数据
		@ResponseBody
		@RequestMapping("/save")
		public TeeJson  save(String param){
			//对传过来的数据进行处理	
			JSONObject js = JSONObject.fromObject(param);
			String uuid = null;
			if(js.containsKey("uuid")){
				uuid = String.valueOf(js.get("uuid"));
			}
			String recUser = String.valueOf(js.get("recUser"));
			String sendUser = String.valueOf(js.get("sendUser"));
			String pendingContent = String.valueOf(js.get("content"));
			String model = String.valueOf(js.get("model"));
			String pendingTitle = String.valueOf(js.get("title"));
			String url = String.valueOf(js.get("url"));
			String yiUrl = String.valueOf(js.get("url1"));
			//进行添加操作			
			TeeCommonHandler chandler=new TeeCommonHandler();
			//处理办理人
			TeePerson person=personService.getPersonByUserId(recUser);
			chandler.setRecUserId(person);
			//处理审批人
			TeePerson persons=personService.getPersonByUserId(sendUser);
			chandler.setSendUserId(persons);
			chandler.setYiUrl(yiUrl);
			//时间处理
			Calendar createTime= Calendar.getInstance();
			chandler.setCreateTime(createTime);
			chandler.setPendingTitle(pendingTitle);
			chandler.setPendingContent(pendingContent);
			chandler.setUrl(url);
			chandler.setState(0);
			chandler.setModel(model);
			if(uuid!=null && !"".equals(uuid)){
				chandler.setUuid(uuid);
			}else{
				String uid=UUID.randomUUID().toString().replace("-", "");//生成主键
				chandler.setUuid(uid);
			}
			
			CHandlerService.save(chandler);
			//发送消息
			
			Map<Object, Object> requestData=  new HashMap<Object, Object>();
			requestData.put("remindUrl",url);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date=df.format(new Date());
			//String date1="2018-09-05 18:30";
			requestData.put("sendTime", date);
			requestData.put("moduleNo", model);
			requestData.put("content", pendingContent);
			requestData.put("userListIds", person.getUuid()+"");
//			TeePerson loginPerson = personService.get(sendUserIds);
			smsService.sendSms(requestData,persons);
			
			TeeJson json = new TeeJson();
			json.setRtData(chandler.getUuid());
			json.setRtState(true);
			
			return json;
		} 
		
		//待办的同步更改数据状态
		@ResponseBody
		@RequestMapping("/update")
		public TeeJson  update(String uuid,int state){
			TeeJson json=new TeeJson();
			TeeCommonHandler chandler=CHandlerService.get(uuid);
			if(chandler!=null){
				chandler.setState(state);
				CHandlerService.update(chandler);
				 json.setRtState(true);
			}else{
				 json.setRtState(false);
			}
			 return json; 
		} 
		
		//待办的同步删除数据
		@ResponseBody
		@RequestMapping("/delete")
		public TeeJson  delete(String uuid){
			TeeJson json=new TeeJson();
			//通过uuid查数据
			TeeCommonHandler chandler=CHandlerService.get(uuid);
			
			if(chandler!=null){
				CHandlerService.delete(uuid);
				json.setRtState(true);
			}else{
				 json.setRtState(false);
			}		
			
		    return json;
		} 
	}
