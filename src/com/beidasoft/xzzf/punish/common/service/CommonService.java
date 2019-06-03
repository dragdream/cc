package com.beidasoft.xzzf.punish.common.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishDocPdf;
import com.beidasoft.xzzf.punish.common.dao.PunishDocPdfDao;
import com.beidasoft.xzzf.punish.common.model.PunishOperationModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service("xzzf_commonService")
public class CommonService extends TeeBaseService{
	@Autowired
	private PunishDocPdfDao pdfDao;
	
	@Autowired
	private PunishFlowService flowService;
	
	@Autowired
	private TeeAttachmentService teeattachService;
	
	@Autowired
	private TeeWenShuService wenShuService;
	
	@Autowired
	private PunishOperationService punishOperationService;
	
//	@Autowired
//	private TeeWenShuService wenshuService;
	
	public void updateFlowRunVars(String primaryId, int runId, String key) {
		simpleDaoSupport.executeUpdate("update TeeFlowRunVars set varValue = '" + primaryId + "' where flowRun.runId="+ runId + " and varKey= '" + key + "'", null);
	}
	
	public void saveOrUpdatePdf( PunishDocPdf pdf) {
		pdfDao.saveOrUpdate(pdf);
	}
	
	public PunishDocPdf getPdfId(String docId) {
		PunishDocPdf rtnInfo = (PunishDocPdf) simpleDaoSupport.get(PunishDocPdf.class, docId);
		return rtnInfo;
	}

	public List getDocByBaseId(String docName, String baseId, String lawLinkId) {
		String hql = " from " + docName + " where baseId ='" +baseId+ "' and lawLinkId ='"+lawLinkId+"' ";
		return simpleDaoSupport.find(hql, null);
	}
	
	/**
	 * 更新操作日志
	 * 
	 * @param request
	 * @param baseId
	 * @param tacheId
	 * @param tacheName
	 * @param runId
	 * @param content
	 */
	public void writeLog(HttpServletRequest request, String baseId,
			String tacheId, String tacheName, String runId, String content) {
		//添加文书操作日志
		PunishOperationModel punishOperationModel = new PunishOperationModel();
		punishOperationModel.setBaseId(baseId);
		punishOperationModel.setConfTacheCode(tacheId);
		punishOperationModel.setConfTacheName(tacheName);
		punishOperationModel.setOperationRunId(TeeStringUtil.getInteger(runId, 0));
		punishOperationModel.setOperationContent(content);
		//保存日志
		punishOperationService.save(punishOperationModel, request);
	}
	
	/**
	 * 更新操作日志
	 * 
	 * @param request
	 * @param baseId
	 * @param tacheId
	 * @param tacheName
	 * @param runId
	 * @param content
	 */
	public void writeLog(HttpServletRequest request, String content) {
		String baseId = TeeStringUtil.getString(request.getParameter("baseId"));
		String tacheId =  TeeStringUtil.getString(request.getParameter("lawLinkId"));
		String tacheName = TeeStringUtil.getString(request.getParameter("confTacheName"));
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		//添加文书操作日志
		PunishOperationModel punishOperationModel = new PunishOperationModel();
		punishOperationModel.setBaseId(baseId);
		punishOperationModel.setConfTacheCode(tacheId);
		punishOperationModel.setConfTacheName(tacheName);
		punishOperationModel.setOperationRunId(runId);
		punishOperationModel.setOperationContent(content);
		//保存日志
		punishOperationService.save(punishOperationModel, request);
	}
}
