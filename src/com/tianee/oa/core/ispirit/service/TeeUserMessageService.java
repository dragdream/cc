package com.tianee.oa.core.ispirit.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.ispirit.com.TeeSocketUtil;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeUserMessageService  extends TeeBaseService{

	public static String PREFIX = "S";
	public static String SEPARATOR = "^";
	public static String MSG_SMS = "a";
	public static String MSG_AVATAR = "b";
	public static String MSG_MYSTATUS = "c";
	public static String MSG_GROUP = "d";
	
	@Autowired
	public TeePersonService personService;

	/**
	 * 登录后给精灵发送消息---修改在线
	 * 
	 * @author syl
	 * @date 2013-11-23
	 * @param userId
	 * @param status
	 *            0 - 退出登录 1-在线 2-忙碌 3-离开
	 * @throws IOException
	 */
	public void pushLoginUser(int userId, String status) throws IOException {
		if (userId <= 0) {
			return;
		}
		String messageContent = "1" + SEPARATOR + userId + SEPARATOR + status;// 1^111^1
																				// --->第一个:发送登录消息
																				// 第二个:人员I的
																				// 第三个为:状态
		TeeSocketUtil.pushMessage(messageContent);
	}

	/**
	 * 修改头像消息
	 * 
	 * @author syl
	 * @date 2013-11-23
	 * @param id
	 *            人员id
	 * @param path
	 *            图象路径
	 * @throws IOException
	 */
	public static boolean pushAvatar(int userId, String path)
			throws IOException {
		if (userId <= 0) {
			return false;
		}
		TeeSocketUtil.pushMessage(userId + SEPARATOR + path);
		return true;
	}
	
	
	
	/*TeneeOA 新增方法*/
	

	/**
	 * 根据用户名获取 对象内容
	 * @param datMap
	 * @return
	 */
	public TeeJson getUserInfoByUserId(Map dataMap){
		TeeJson json = new TeeJson();
		String userId = TeeStringUtil.getString(dataMap.get("userId"));
		TeePerson person = personService.getPersonByUserId(userId);
		TeePersonModel model = personService.parseModel(person, false);
		model.setPassword("");
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}

}
