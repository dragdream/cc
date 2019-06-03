package com.tianee.oa.core.workflow.flowrun.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunAipTemplate;
import com.tianee.oa.core.workflow.flowrun.model.TeeFlowRunAipTemplateModel;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowPrintTemplate;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeFlowRunAipTemplateService extends TeeBaseService implements TeeFlowRunAipTemplateServiceInterface{
	
	@Autowired
	private TeeBaseUpload baseUpload;

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunAipTemplateServiceInterface#isExist(javax.servlet.http.HttpServletRequest)
	 */
	public TeeJson isExist(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		int runId=TeeStringUtil.getInteger(request.getParameter("runId"),0);
		int templateId=TeeStringUtil.getInteger(request.getParameter("templateId"),0);
		
		TeeFlowRun flowRun=(TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class,runId);
		TeeFlowPrintTemplate tt=(TeeFlowPrintTemplate) simpleDaoSupport.get(TeeFlowPrintTemplate.class,templateId);
		
		TeeFlowRunAipTemplate t=(TeeFlowRunAipTemplate) simpleDaoSupport.unique(" from TeeFlowRunAipTemplate where flowRun.runId=? and template.sid=? ", new Object[]{runId,templateId});
		if(t!=null){
			TeeFlowRunAipTemplateModel model=parseToModel(t);
			json.setRtData(model);
		}else{//不存在  需要新建
			
			InputStream in=null;
			InputStream in1 = null;
			TeeAttachment att=null;
			try {
				in = new ByteArrayInputStream(tt.getModulContent().getBytes());
				BASE64Decoder decoder = new BASE64Decoder();
				byte b[] = decoder.decodeBuffer(in);
				in1 = new ByteArrayInputStream(b);
				att=baseUpload.singleAttachUpload(in1,tt.getModulContent().getBytes().length, tt.getModulName()+".aip", null, "workFlowAip", loginUser);
				
				TeeFlowRunAipTemplate frat=new TeeFlowRunAipTemplate();
				frat.setAttachment(att);
				frat.setFlowRun(flowRun);
				frat.setTemplate(tt);
				simpleDaoSupport.save(frat);
				
				TeeFlowRunAipTemplateModel model=parseToModel(frat);
				json.setRtData(model);
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(in!=null){
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}
		
		json.setRtState(true);
		return json;
	}

	
	/**
	 * 将实体类转换成model
	 * @param t
	 * @return
	 */
	private TeeFlowRunAipTemplateModel parseToModel(TeeFlowRunAipTemplate t) {
		TeeFlowRunAipTemplateModel model=new TeeFlowRunAipTemplateModel();
		BeanUtils.copyProperties(t, model);
		if(t.getFlowRun()!=null){
			model.setRunId(t.getFlowRun().getRunId());
			model.setRunName(t.getFlowRun().getRunName());
		}
		
		if(t.getTemplate()!=null){
			model.setTemplateId(t.getTemplate().getSid());
			model.setTemplateName(t.getTemplate().getModulName());
		}
		
		if(t.getAttachment()!=null){
			model.setAttachExt(t.getAttachment().getExt());
			model.setAttachId(t.getAttachment().getSid());
			model.setAttachName(t.getAttachment().getAttachmentName());
		}
		return model;
	}


	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunAipTemplateServiceInterface#getListByRunId(javax.servlet.http.HttpServletRequest)
	 */
	public TeeJson getListByRunId(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取流程id
		int runId=TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		List<TeeFlowRunAipTemplate> list=simpleDaoSupport.executeQuery(" from TeeFlowRunAipTemplate where flowRun.runId=? ", new Object[]{runId});
		List<TeeFlowRunAipTemplateModel> modelList=new ArrayList<TeeFlowRunAipTemplateModel>();
		if(list!=null&&list.size()>0){
			for (TeeFlowRunAipTemplate teeFlowRunAipTemplate: list) {
				modelList.add(parseToModel(teeFlowRunAipTemplate));
			}
		}
		
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}

	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunAipTemplateServiceInterface#getListByRunId(int)
	 */
	public TeeJson getListByRunId(int  runId) {
		TeeJson json=new TeeJson();
		
		List<TeeFlowRunAipTemplate> list=simpleDaoSupport.executeQuery(" from TeeFlowRunAipTemplate where flowRun.runId=? ", new Object[]{runId});
		List<TeeFlowRunAipTemplateModel> modelList=new ArrayList<TeeFlowRunAipTemplateModel>();
		if(list!=null&&list.size()>0){
			for (TeeFlowRunAipTemplate teeFlowRunAipTemplate: list) {
				modelList.add(parseToModel(teeFlowRunAipTemplate));
			}
		}
		
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}
}
