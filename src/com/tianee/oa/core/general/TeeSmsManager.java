package com.tianee.oa.core.general;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeSmsManager extends TeeBaseService{

	@Autowired
	private TeeSmsSender smsSender;
	/**
	 * 发送短信
	 * @param Map{required-- content:"内容",userListIds:"接收人uuid串（或者是userList：List<TeePerson>对象），逗号分隔（无需处理最后一个逗号）,"
	 * 			  optional-- sendTime:"发送时间（不填为即时发送）,moduleNo:模块ID（不填默认为工作流）,remindUrl:"链接如（/system/core/workflow/flowrun/prcs/index.jsp?runId=1&prcsId=1&flowPrcs=1&flowId=1&isNew=1）" }
	 * @param loginPerson
	 * @return
	 */
	public void sendSms(Map requestData,TeePerson loginPerson){
		smsSender.sendSms(requestData, loginPerson);
	}
	
}
