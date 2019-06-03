package com.tianee.oa.core.workflow.workmanage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowNtkoPrintTemplate;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeFlowNtkoPrintTemplateService extends TeeBaseService implements TeeFlowNtkoPrintTemplateServiceInterface{
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowNtkoPrintTemplateServiceInterface#addOrUpdate(java.util.Map)
	 */
	@Override
	public void addOrUpdate(Map requestData){
		TeeAttachment attach = (TeeAttachment) requestData.get("attach");
		String modelName = TeeStringUtil.getString(requestData.get("modelName"));
		int flowId = TeeStringUtil.getInteger(requestData.get("flowId"), 0);
		
		TeeFlowType flowType = new TeeFlowType();
		flowType.setSid(flowId);
		
		TeeFlowNtkoPrintTemplate flowNtkoPrintTemplate = new TeeFlowNtkoPrintTemplate();
		flowNtkoPrintTemplate.setAttach(attach);
		flowNtkoPrintTemplate.setFlowType(flowType);
		flowNtkoPrintTemplate.setModulName(modelName);
		
		
		simpleDaoSupport.save(flowNtkoPrintTemplate);
		
		attach.setModelId(String.valueOf(flowNtkoPrintTemplate.getSid()));
		simpleDaoSupport.update(attach);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowNtkoPrintTemplateServiceInterface#delete(int)
	 */
	@Override
	public void delete(int sid){
		TeeFlowNtkoPrintTemplate flowNtkoPrintTemplate = 
				(TeeFlowNtkoPrintTemplate) simpleDaoSupport.get(TeeFlowNtkoPrintTemplate.class, sid);
		
		TeeAttachment attach = flowNtkoPrintTemplate.getAttach();
		
		simpleDaoSupport.deleteByObj(flowNtkoPrintTemplate);
		
		attachmentService.deleteAttach(attach);
		
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowNtkoPrintTemplateServiceInterface#list(int, int)
	 */
	@Override
	public List<Map> list(int flowId,int runId){
		List<TeeFlowNtkoPrintTemplate> list = null;
		if(flowId!=0){
			list = simpleDaoSupport.find("from TeeFlowNtkoPrintTemplate where flowType.sid="+flowId, null);
		}else{
			Map data = simpleDaoSupport.getMap("select flowType.sid as FLOW_ID from TeeFlowRun where runId="+runId, null);
			flowId = TeeStringUtil.getInteger(data.get("FLOW_ID"), 0);
			list = simpleDaoSupport.find("from TeeFlowNtkoPrintTemplate where flowType.sid="+flowId, null);
		}
		
		List<Map> mapList = new ArrayList();
		for(TeeFlowNtkoPrintTemplate flowNtkoPrintTemplate:list){
			Map data = new HashMap();
			data.put("modelName", flowNtkoPrintTemplate.getModulName());
			data.put("sid", flowNtkoPrintTemplate.getSid());
			
			TeeAttachmentModel attachModel = new TeeAttachmentModel();
			TeeAttachment attach = flowNtkoPrintTemplate.getAttach();
			attachModel.setFileName(attach.getFileName());
			attachModel.setSid(attach.getSid());
			attachModel.setExt(attach.getExt());
			
			data.put("attach", attachModel);
			
			mapList.add(data);
		}
		
		return mapList;
	}
	
	
	
}
