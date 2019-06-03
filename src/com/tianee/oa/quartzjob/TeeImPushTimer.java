package com.tianee.oa.quartzjob;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.tianee.oa.oaconst.TeeModuleConst;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.openfire.TeeOpenfireUtil;

@Repository
public class TeeImPushTimer{

	//缓存消息队列
	public static List<Map> msgBuffQueue = new LinkedList<Map>();
	
	//临时消息队列
	List<Map> messageList = new LinkedList<Map>();
			
	/**
	 * 手动控制事务，细粒度
	 * @throws OApiException 
	 */
	public void doTimmer(){
		if(TeeSysProps.getProps()==null){
			return;
		}
		
		synchronized (msgBuffQueue) {
			messageList.addAll(msgBuffQueue);
			msgBuffQueue.clear();
		}
		
		try {
			String t = null;//消息类型
			JSONObject jsonObject = new JSONObject();
			for(Map datagram:messageList){
				t = datagram.get("t").toString();
				jsonObject.clear();
				
				String no = "";
				if("1".equals(t)){//单体消息
					
				}else if("2".equals(t)){//群组消息
					
				}else if("50".equals(t)){//消息事务
					jsonObject.put("t", 50);
					jsonObject.put("c", datagram.get("content"));
					jsonObject.put("url", datagram.get("url"));
					jsonObject.put("url1", datagram.get("url1"));
					no = TeeModuleConst.MODULE_SORT_TYPE.get(datagram.get("no")+"");
					jsonObject.put("no", no);
					
					jsonObject.put("id", datagram.get("from"));
					jsonObject.put("name", datagram.get("userName"));
					
					//发送消息到Openfire中
					TeeOpenfireUtil.sendMessage(jsonObject.toString(), datagram.get("to").toString(),no+"："+datagram.get("content"),"50");
				}
				
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally{
			messageList.clear();
		}
		
	}

}
