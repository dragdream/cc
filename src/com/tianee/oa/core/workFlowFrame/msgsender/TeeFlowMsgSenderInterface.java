package com.tianee.oa.core.workFlowFrame.msgsender;

import java.util.List;
import java.util.Map;

import com.tianee.oa.core.base.email.bean.TeeMail;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;

public interface TeeFlowMsgSenderInterface {

	/**
	 * 固定流程发送消息
	 * @param curFrp
	 * @param frpList
	 * @param requestData
	 * @param loginUser
	 */
	public void sendFixedFlowSms(TeeFlowRunPrcs curFrp,
			List<TeeFlowRunPrcs> frpList, Map requestData, TeePerson loginUser,
			int curTurnType);

	/**
	 * 自由流程 发送短消息
	 * @author zhp
	 * @createTime 2013-11-17
	 * @editTime 下午09:03:32
	 * @desc
	 */
	public void sendFreeFlowSms(String msg, TeeFlowRunPrcs curFrp,
			List<TeeFlowRunPrcs> frpList, TeePerson loginUser, int reminds[]);

	/**
	 * 自由流程发送短消息
	 * @author zhp
	 * @createTime 2013-11-17
	 * @editTime 下午09:33:45
	 * @desc
	 */
	public void sendFreeFlowSms(String msg, TeeFlowRunPrcs tmp,
			TeePerson loginUser, int reminds[]);

	/**
	 * 发送结束短信
	 * @param frp
	 * @param requestData
	 */
	public void sendFixedFlowEndSms(TeeFlowRunPrcs frp, Map requestData);

	/**
	 * 发送委托代理短信提醒
	 * @param curFrp
	 * @param frpList
	 * @param requestData
	 * @param loginUser
	 */
	public void sendFixedFlowDeligateSms(TeeFlowRunPrcs curFrp,
			List<TeeFlowRunPrcs> frpList, Map requestData, TeePerson loginUser);

	/**
	 * 发送关注短信，当流程进行某种状态时，发送不同的提醒
	 * @param fr
	 * @param type 发送类型，1：转交 2：结束
	 */
	public void sendConcernSms(TeeFlowRun fr, int type);

	/**
	 * 发送查阅相关短消息
	 * @param userIds 用户id串
	 * @param flowRun
	 */
	public void sendViewSms(String userIds, TeeFlowRun flowRun);

	/**
	 * 发送邮件提醒相关短消息
	 * @param userIds 用户id串
	 * @param flowRun
	 */
	public void sendMailSms(List<TeeMail> mails);

}