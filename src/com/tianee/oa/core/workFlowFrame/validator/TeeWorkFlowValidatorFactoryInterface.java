package com.tianee.oa.core.workFlowFrame.validator;

import java.util.Map;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;

public interface TeeWorkFlowValidatorFactoryInterface {

	/**
	 * 自由流程和固定流发起权限校验
	 * @param person
	 * @param flowtype
	 * @return
	 */
	public abstract boolean flowCreatePrivValida(TeePerson person,
			TeeFlowType flowType);

	/**
	 * 流程办理界面控制
	 * @param person
	 * @param flowRunPrcs
	 * @return
	 */
	public abstract Map flowHandViewPrivValida(TeePerson person,
			TeeFlowRunPrcs flowRunPrcs);

	/**
	 * 流程预转交界面控制
	 * @param person  当前登录人
	 * @param flowRunPrcs  流程实例步骤对象
	 * @return 
	 *     flowType 流程类型 1 - 固定流 2 - 自由流程
	 *     handPrivMsg  校验权限（删除、已办理、判断是否有办理办理权限）
	 *     isLastHand 无主办会签，是否最后一步办理
	 *     isAdvanceOver 对自由流程   是否为预设计步骤
	 */
	public abstract Map flowBeforeTurnValida(TeePerson person,
			TeeFlowRunPrcs flowRunPrcs);

	/**
	 * 流程转交权限界面控制
	 * @param person
	 * @param flowRunPrcs
	 * @return  flowType:流程类型      handPrivMsg:权限校验 为空正常
	 */
	public abstract Map flowTurnViewValida(TeePerson person,
			TeeFlowRunPrcs flowRunPrcs);

	/**
	 * 获取自由流和固定流基本权限  例如附件、传阅、预设等
	 * @param person
	 * @param flowRunPrcs
	 * @return
	 */
	public abstract Map getFlowBasicPriv(TeeFlowRunPrcs flowRunPrcs);

	/**
	 * 获取表单字段设置权限  隐藏、必填、隐藏
	 * @param flowRunPrcs
	 * @return
	 */
	public abstract Map getFormItemInfo(TeeFlowRunPrcs flowRunPrcs);

	/*	*//**
			 * 获取流程 转交信息  == 固定流程
			 * @param flowRunPrcs
			 * @param loginPerson
			 * @return
			 */
	/*
	public Map getFixedFlowTurnInfo(TeeFlowType flowType , TeeFlowRunPrcs flowRunPrcs , TeePerson loginPerson){
	Map map = new HashMap();
	if(flowType.getType() == 1){
		  map =  basicInfoValidatorService.getFixedFlowTurnInfo( flowType ,  flowRunPrcs ,  loginPerson);
	}
	return map;
	}*/

	public abstract void setFlowPrivValideService(
			TeeWorkFlowPrivValidatorServiceInterface flowPrivValideService);

	public abstract void setBasicInfoValidatorService(
			TeeWorkFlowBasicInfoValidatorServiceInterface basicInfoValidatorService);

	public abstract void setFlowFormValidatorService(
			TeeWorkFlowFormValidatorServiceInterface flowFormValidatorService);

}