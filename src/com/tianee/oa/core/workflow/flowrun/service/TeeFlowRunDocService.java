package com.tianee.oa.core.workflow.flowrun.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunDoc;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunDocVersion;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.model.TeeFlowRunDocModel;
import com.tianee.oa.core.workflow.flowrun.model.TeeFlowRunDocVersionModel;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.file.TeeUploadHelper;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeFlowRunDocService extends TeeBaseService implements TeeFlowRunDocServiceInterface{
	
	@Autowired
	private TeeBaseUpload baseUpload;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunDocServiceInterface#getFlowRunDocByDocId(int)
	 */
	@Override
	public TeeFlowRunDoc getFlowRunDocByDocId(int docId){
		TeeFlowRunDoc doc = (TeeFlowRunDoc) simpleDaoSupport.get(TeeFlowRunDoc.class,docId);
		return doc;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunDocServiceInterface#entityToModel(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunDoc, com.tianee.oa.core.workflow.flowrun.model.TeeFlowRunDocModel)
	 */
	@Override
	public void entityToModel(TeeFlowRunDoc doc,TeeFlowRunDocModel model){
		BeanUtils.copyProperties(doc, model,new String[]{"docAttach"});
		model.setCreateTimeDesc(TeeDateUtil.format(doc.getCreateTime()));
		TeePerson lastEditor = (TeePerson) simpleDaoSupport.get(TeePerson.class,doc.getLastEditor());
		model.setLastEditorUsername(lastEditor==null?"":lastEditor.getUserName());
		model.setLastEditTimeDesc(TeeDateUtil.format(doc.getLastEditTime()));
		
		TeeAttachmentModel attachModel = new TeeAttachmentModel();
		BeanUtils.copyProperties(doc.getDocAttach(), attachModel);
		attachModel.setUserId(doc.getDocAttach().getUser().getUuid()+"");
		attachModel.setUserName(doc.getDocAttach().getUser().getUserName());
		
		model.setDocAttach(attachModel);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunDocServiceInterface#getFlowRunDocByRunId(int)
	 */
	@Override
	public TeeFlowRunDoc getFlowRunDocByRunId(int runId){
		String hql = "from TeeFlowRunDoc doc where doc.flowRun.runId="+runId;
		TeeFlowRunDoc doc = (TeeFlowRunDoc) simpleDaoSupport.unique(hql, null);
		return doc;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunDocServiceInterface#getFlowRunDocAipByRunId(int)
	 */
	@Override
	public TeeAttachment getFlowRunDocAipByRunId(int runId){
		List<TeeAttachment> attachs = attachmentDao.getAttaches(TeeAttachmentModelKeys.workFlowDocAip, String.valueOf(runId));
		TeeAttachmentModel doc = new TeeAttachmentModel();
		if(attachs!=null && attachs.size()!=0){
			return attachs.get(0);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunDocServiceInterface#createOrUpdateFlowRunDocAip(int, com.tianee.oa.core.org.bean.TeePerson, org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	public void createOrUpdateFlowRunDocAip(int runId,TeePerson person,MultipartFile file){
		//先查询是否存在指定流程的版式文件
		List<TeeAttachment> attachments = attachmentDao.getAttaches(TeeAttachmentModelKeys.workFlowDocAip, String.valueOf(runId));
		TeeAttachment attachment = null;
		if(attachments.size()==0){//新建
			try {
				attachment = baseUpload.singleAttachUpload(file, "正文", TeeAttachmentModelKeys.workFlowDocAip, person);
				attachment.setModelId(String.valueOf(runId));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{//更新
			attachment = attachments.get(0);
			String attachName  = null;
			String sFilePath = null;
			attachName = attachment.getAttachmentName();
			sFilePath = attachment.getAttachmentPath();
			
			String updateFilepath  = attachment.getAttachSpace().getSpacePath()+File.separator+attachment.getModel()+File.separator+sFilePath+File.separator+ attachName;
			try {
				TeeUploadHelper.saveFile(file.getInputStream(), updateFilepath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			attachment.setSize(file.getSize());
			attachmentDao.update(attachment);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunDocServiceInterface#createNewOffice(int, int, java.lang.String, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public TeeFlowRunDoc createNewOffice(int runId,int frpSid,String docType,TeePerson loginUser) throws IOException{
		TeeFlowRunPrcs frp=(TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class,frpSid);
		TeeAttachment attachment = baseUpload.newAttachment("正文", docType, TeeAttachmentModelKeys.workFlowDoc, loginUser);
		attachment.setModelId("0");
		TeeFlowRunDoc flowRunDoc = new TeeFlowRunDoc();
		flowRunDoc.setDocAttach(attachment);
		TeeFlowRun flowRun = (TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class, runId);
		flowRunDoc.setFlowRun(flowRun);
		flowRunDoc.setLastEditor(loginUser.getUuid());
		flowRunDoc.setLastEditTime(Calendar.getInstance());
		flowRunDoc.setLock(0);
		flowRunDoc.setVersionNo(1);
		simpleDaoSupport.save(flowRunDoc);
		
		
		//同时往正文版本中间表中插入一条数据
		TeeFlowRunDocVersion version=new TeeFlowRunDocVersion();
		version.setAttachId(attachment.getSid());
		version.setCreateTime(Calendar.getInstance());
		version.setCreateUser(loginUser);
		version.setPrcsId(frp.getPrcsId());
		version.setPrcsName(frp.getFlowPrcs().getPrcsName());
		version.setRunId(runId);
		version.setVersionNo(1);
		simpleDaoSupport.save(version);
	
		return flowRunDoc;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunDocServiceInterface#lockDoc(int)
	 */
	@Override
	public void lockDoc(int docId){
		String hql = "update TeeFlowRunDoc doc set doc.lock=1 where doc.sid="+docId;
		simpleDaoSupport.executeUpdate(hql, null);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunDocServiceInterface#unlockDoc(int)
	 */
	@Override
	public void unlockDoc(int docId){
		String hql = "update TeeFlowRunDoc doc set doc.lock=0 where doc.sid="+docId;
		simpleDaoSupport.executeUpdate(hql, null);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunDocServiceInterface#versionNoValidate(int, int)
	 */
	@Override
	public void versionNoValidate(int docId,int versionNo){
		String hql = "select doc.versionNo as VERSION_NO from TeeFlowRunDoc doc where doc.sid="+docId;
		Map vn = simpleDaoSupport.getMap(hql, null);
		int currentVn = TeeStringUtil.getInteger(vn.get("VERSION_NO"), 0);
		if(currentVn>versionNo){
			throw new TeeOperationException("正文版本号已过时，请手动保存好数据，重新打开该页面。");
		}
	} 
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunDocServiceInterface#updateDoc(int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public void updateDoc(int docId,TeePerson loginUser){
		String hql = "update TeeFlowRunDoc doc set doc.versionNo = doc.versionNo+1,doc.lastEditor="+loginUser.getUuid()+",doc.lastEditTime=? where doc.sid="+docId;
		simpleDaoSupport.executeUpdate(hql, new Object[]{Calendar.getInstance()});
	}

	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunDocServiceInterface#hasGenerateVersion(int, int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public TeeJson hasGenerateVersion(int runId, int frpSid, TeePerson loginUser) {
		TeeJson json=new TeeJson();
		TeeFlowRunPrcs frp=(TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class,frpSid);
	    List<TeeFlowRunDocVersion> list=simpleDaoSupport.executeQuery(" from TeeFlowRunDocVersion where runId=? and prcsId=? and createUser.uuid=? ", new Object[]{runId,frp.getPrcsId(),loginUser.getUuid()});
		if(list!=null&&list.size()>0){//已经生成版本
			json.setRtData(1);
		}else{//未生成版本
			json.setRtData(0);
		}
		json.setRtState(true);
		return json;
	}

	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunDocServiceInterface#generateVersion(int, int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public TeeJson generateVersion(int runId, int frpSid, TeePerson loginUser) {
		TeeJson json=new TeeJson();
		TeeFlowRun flowRun=(TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class,runId);
		TeeFlowRunPrcs frp=(TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class,frpSid);
		
		//获取当前正文的最大版本号
		int maxVersionNo=0;
		Map map=simpleDaoSupport.executeNativeUnique(" select max(VERSION_NO) as maxVersionNo from  flow_run_doc_version where RUN_ID=? ",new Object[]{runId});
	    if(map!=null){
	    	maxVersionNo=TeeStringUtil.getInteger(map.get("maxVersionNo"), 0);	
	    }
		
		//获取当前流程关联的正文附件
	    List<TeeFlowRunDoc> docList=simpleDaoSupport.executeQuery(" from TeeFlowRunDoc where flowRun.runId=? ", new Object[]{flowRun.getRunId()});
	    TeeFlowRunDoc doc=null;
	    if(docList!=null && docList.size()>0){
	    	doc=docList.get(0);
	    	TeeAttachment att=doc.getDocAttach();
	    	
	    	TeeAttachment newAtt=attachmentService.clone(att, TeeAttachmentModelKeys.workFlowDocVersion, loginUser);
	    	
	    	TeeFlowRunDocVersion v=new TeeFlowRunDocVersion();
	    	v.setAttachId(newAtt.getSid());
	    	v.setCreateTime(Calendar.getInstance());
	    	v.setCreateUser(loginUser);
	    	v.setPrcsId(frp.getPrcsId());
	    	v.setRunId(runId);
	    	v.setVersionNo(maxVersionNo+1);
	    	v.setPrcsName(frp.getFlowPrcs().getPrcsName());
			
	    	simpleDaoSupport.save(v);
	    	
	    	
	    	doc.setDocAttach(newAtt);
	    	doc.setVersionNo(v.getVersionNo());
	    	simpleDaoSupport.update(doc);
			
	    	json.setRtData(newAtt.getSid());
		}
	    json.setRtState(true);
		return json;
	}

	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunDocServiceInterface#getAllDocVersionByRunId(int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public TeeJson getAllDocVersionByRunId(int runId, TeePerson loginUser) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TeeJson json=new TeeJson();
		
		List<TeeFlowRunDocVersion> list=simpleDaoSupport.executeQuery(" from  TeeFlowRunDocVersion where runId=? ", new Object[]{runId});
		List<TeeFlowRunDocVersionModel> modelList=new ArrayList<TeeFlowRunDocVersionModel>();
		TeeFlowRunDocVersionModel  model=null;	
		if(list!=null&&list.size()>0){
			for (TeeFlowRunDocVersion teeFlowRunDocVersion : list) {
				model=new TeeFlowRunDocVersionModel();
				BeanUtils.copyProperties(teeFlowRunDocVersion, model);
				if(teeFlowRunDocVersion.getCreateUser()!=null){
					model.setCreateUserId(teeFlowRunDocVersion.getCreateUser().getUuid());
				    model.setCreateUserName(teeFlowRunDocVersion.getCreateUser().getUserName());
				}
				
				if(teeFlowRunDocVersion.getCreateTime()!=null){
					model.setCreateTimeStr(sdf.format(teeFlowRunDocVersion.getCreateTime().getTime()));
				}
				
				modelList.add(model);
			}
		}
		
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}
	
}
