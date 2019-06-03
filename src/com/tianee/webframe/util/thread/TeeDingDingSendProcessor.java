package com.tianee.webframe.util.thread;

import com.alibaba.dingtalk.openapi.demo.message.LightAppMessageDelivery;
import com.alibaba.dingtalk.openapi.demo.message.MessageHelper;
import com.tianee.webframe.util.global.TeeSysProps;

/**
 * 钉钉具体发送处理类
 * @author xsy
 *
 */
public class TeeDingDingSendProcessor implements Runnable{

	//钉钉任务的引用实例
	LightAppMessageDelivery delivery = null;
	
	public TeeDingDingSendProcessor(LightAppMessageDelivery delivery){
		this.delivery = delivery;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			MessageHelper.send(TeeSysProps.getString("DING_ACCESS_TOKEN"), delivery);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
