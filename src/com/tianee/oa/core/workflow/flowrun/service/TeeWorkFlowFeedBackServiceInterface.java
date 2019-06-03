package com.tianee.oa.core.workflow.flowrun.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFeedBack;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFeedBackModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeWorkFlowFeedBackServiceInterface {

	/**
	 * 增加会签意见 并且处理一下附件 
	 * @param fb
	 * @throws IOException 
	 */
	public void addFeedBack(TeeFeedBack fb) throws IOException;
	/**
	 * 删除会签附件
	 * @author zhp
	 * @createTime 2013-10-6
	 * @editTime 下午10:21:58
	 * @desc
	 */
	public void deleteFeedBackAttach(int fid,int aid) throws IOException;
	/**
	 * 删除会签意见
	 * @author zhp
	 * @createTime 2013-10-6
	 * @editTime 下午08:09:47
	 * @desc
	 */
	public void deleteFeedBack(int id) throws IOException;
	/**
	 * 获取印章数据
	 * @author zhp
	 * @createTime 2013-11-10
	 * @editTime 上午12:34:58
	 * @desc
	 */
	public String getFeedBackSealDataById (int sid) ;
	/**
	 * @author zhp
	 * @createTime 2013-11-27
	 * @editTime 下午08:35:47
	 * @desc
	 */
	public List<TeeFeedBackModel> selectTeeFeedBackByRunId (int runId);
	/**
	 * 获取会签意见
	 * @param runId
	 * @return
	 */
	public List<TeeFeedBackModel> selectTeeFeedBackByRunId (int runId,int flowType,TeeFlowProcess flowPrcs);
	
	/**
	 * 获取会签意见，有权限判断
	 * @param runId
	 * @return
	 */
	public List<TeeFeedBackModel> selectPrcsPrivedFeedBackByRunId (int runId,int frpSid,int personId);
	
	/**
	 * 获取附件map
	 * @author zhp
	 * @createTime 2013-10-6
	 * @editTime 下午02:15:39
	 * @desc
	 */
	public List getFeedBackAttachList(List<TeeAttachment> attachs);
	
	
	/**
	 * 判断是否已经会签过
	 * @param runId
	 * @param prcsId
	 * @param flowPrcs
	 * @param personId
	 * @return
	 */
	public boolean hasFeedback(TeeFlowRunPrcs frp);
	
	public TeeBaseUpload getUpload();
	public void setUpload(TeeBaseUpload upload);
	public TeeAttachmentDao getAttachmentDao();
	public void setAttachmentDao(TeeAttachmentDao attachmentDao);
	public void setSimpleDao(TeeSimpleDaoSupport simpleDao);
	public TeeSimpleDaoSupport getSimpleDao() ;
	
	
	
	/**
	 * 根据runId和itemId获取相应的会签意见
	 * @param request
	 * @return
	 */
	public TeeJson getFeedBackByRunIdAndItemId(HttpServletRequest request);
	
	
	/**
	 * /**
	 * 根据runId和  teeFlowProcess的prcsId取出对应的会签数据
	 * @param flowRun
	 * @param flowRunPrcs
	 * @return
	 */
	public List<Map<String, String>> selectFeedBackByRunIdAndFlowProcessPrcsId(
			TeeFlowRun flowRun, String prcsId,int isHasBack);
	
	
	/**
	 * 根据runId和 prcsId取出对应的会签数据
	 * @param flowRun
	 * @param num
	 * @param isHasBack
	 * @return
	 */
	public List<Map<String, String>> selectFeedBackByRunIdAndPrcsId(TeeFlowRun flowRun, String num, int isHasBack);
}