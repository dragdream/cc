package com.tianee.oa.core.workflow.flowrun.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunDoc;
import com.tianee.oa.core.workflow.flowrun.model.TeeFlowRunDocModel;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeFlowRunDocServiceInterface {

	/**
	 * 获取正文
	 * @param docId
	 * @return
	 */
	public abstract TeeFlowRunDoc getFlowRunDocByDocId(int docId);

	public abstract void entityToModel(TeeFlowRunDoc doc,
			TeeFlowRunDocModel model);

	/**
	 * 获取正文
	 * @param docId
	 * @return
	 */
	public abstract TeeFlowRunDoc getFlowRunDocByRunId(int runId);

	/**
	 * 获取版式正文
	 * @param docId
	 * @return
	 */
	public abstract TeeAttachment getFlowRunDocAipByRunId(int runId);

	/**
	 * 创建或更新版式正文
	 * @param runId
	 * @param loginUser
	 * @return
	 * @throws IOException 
	 */
	public abstract void createOrUpdateFlowRunDocAip(int runId,
			TeePerson person, MultipartFile file);

	/**
	 * 创建新文档
	 * @param runId
	 * @param loginUser
	 * @return
	 * @throws IOException 
	 */
	public abstract TeeFlowRunDoc createNewOffice(int runId, int frpSid,
			String docType, TeePerson loginUser) throws IOException;

	/**
	 * 锁定文档
	 * @param docId
	 */
	public abstract void lockDoc(int docId);

	/**
	 * 解锁文档
	 * @param docId
	 */
	public abstract void unlockDoc(int docId);

	/**
	 * 校验
	 * @param docId
	 */
	public abstract void versionNoValidate(int docId, int versionNo);

	/**
	 * 更新文档
	 */
	public abstract void updateDoc(int docId, TeePerson loginUser);

	/**
	 * 判断当前步骤的正文是否已经生成版本
	 * @param runId
	 * @param frpSid
	 * @param loginUser
	 * @return
	 */
	public abstract TeeJson hasGenerateVersion(int runId, int frpSid,
			TeePerson loginUser);

	/**
	 * 正文生成版本
	 * @param runId
	 * @param frpSid
	 * @param loginUser
	 * @return
	 */
	public abstract TeeJson generateVersion(int runId, int frpSid,
			TeePerson loginUser);

	/**
	 * 根据流程id  获取所有的正文版本
	 * @param runId
	 * @param loginUser
	 * @return
	 */
	public abstract TeeJson getAllDocVersionByRunId(int runId,
			TeePerson loginUser);

}