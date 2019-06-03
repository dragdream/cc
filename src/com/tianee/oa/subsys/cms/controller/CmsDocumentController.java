package com.tianee.oa.subsys.cms.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.lucene.TeeLuceneSearcher;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.cms.bean.DocumentInfo;
import com.tianee.oa.subsys.cms.model.DocumentInfoModel;
import com.tianee.oa.subsys.cms.service.CmsDocumentService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.cache.RedisClient;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/cmsDocument")
public class CmsDocumentController {
	
	@Autowired
	private CmsDocumentService documentService;

	
	@ResponseBody
	@RequestMapping("/addDocument")
	public TeeJson addDocument(HttpServletRequest request){
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		DocumentInfoModel documentInfoModel = (DocumentInfoModel) TeeServletUtility.request2Object(request, DocumentInfoModel.class);
		DocumentInfo doc =documentService.addDocument(documentInfoModel,loginUser); 
		json.setRtData(doc);
		String attachmentSidStr = request.getParameter("attachmentSidStr");
		List<TeeAttachment> attachments = documentService.getAttachmentDao().getAttachmentsByIds(attachmentSidStr);
		if(attachments!= null && attachments.size()>0){
			for(TeeAttachment attach:attachments){
				attach.setModelId(String.valueOf(doc.getSid()));
				documentService.getSimpleDaoSupport().update(attach);
			}
		}
		
		RedisClient.getInstance().del("SITE");
		RedisClient.getInstance().del("CHNL");
		RedisClient.getInstance().del("DOC");
		
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/updateDocument")
	public TeeJson updateDocument(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String attachmentSidStr = request.getParameter("attachmentSidStr");
		int sid = TeeStringUtil.getInteger(request.getParameter("docId"), 0);
		if(sid>0){
			List<TeeAttachment> attachments = documentService.getAttachmentDao().getAttachmentsByIds(attachmentSidStr);
			if(attachments!= null && attachments.size()>0){
				for(TeeAttachment attach:attachments){
					attach.setModelId(String.valueOf(sid));
					documentService.getSimpleDaoSupport().update(attach);
				}
			}
			DocumentInfoModel documentInfoModel = (DocumentInfoModel) TeeServletUtility.request2Object(request, DocumentInfoModel.class);
			documentService.updateDocument(documentInfoModel,loginUser);
			json.setRtState(true);
		}
		
		RedisClient.getInstance().del("SITE");
		RedisClient.getInstance().del("CHNL");
		RedisClient.getInstance().del("DOC");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/deleteDocumentByDocId")
	public TeeJson deleteDocumentByDocId(HttpServletRequest request){
		TeeJson json = new TeeJson();
		DocumentInfoModel documentInfoModel = (DocumentInfoModel) TeeServletUtility.request2Object(request, DocumentInfoModel.class);
		documentService.deleteDocumentByDocId(documentInfoModel.getDocId());
		json.setRtState(true);
		
		RedisClient.getInstance().del("SITE");
		RedisClient.getInstance().del("CHNL");
		RedisClient.getInstance().del("DOC");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getDocument")
	public TeeJson getDocument(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int docId = TeeStringUtil.getInteger(request.getParameter("docId"), 0);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		DocumentInfoModel model = new DocumentInfoModel();
		DocumentInfo docInfo = documentService.getDocumentByDocId(docId);
		BeanUtils.copyProperties(docInfo, model);
		model.setDocId(docId);
		if(null!=docInfo.getWriteTime()){
			model.setWriteTimeDesc(sf.format(docInfo.getWriteTime().getTime()));
		}
		List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
		List<TeeAttachment> attaches =documentService.getAttachmentDao().getAttaches(TeeAttachmentModelKeys.cms, String.valueOf(docId));
		for (TeeAttachment attach : attaches) {
			TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, attachmentModel);
			attachmentModel.setUserId(attach.getUser().getUuid() + "");
			attachmentModel.setUserName(attach.getUser().getUserName());
			attachmentModel.setPriv(1 + 2+4+8+16+32);// 一共五个权限好像
											// 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
			attachmodels.add(attachmentModel);
		}
		model.setAttachMentModel(attachmodels);
		
		if (docInfo.getDocAttachmentId() > 0) {
			TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
			TeeAttachment teeAttachment = documentService.getAttachmentDao().get(docInfo.getDocAttachmentId());
			BeanUtils.copyProperties(teeAttachment, attachmentModel);
			attachmentModel.setUserId(teeAttachment.getUser().getUuid() + "");
			attachmentModel.setUserName(teeAttachment.getUser().getUserName());
			attachmentModel.setPriv(1+2+4+8+16+32);
			model.setDocAttachmentModel(attachmentModel);
		}
		
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping("/moveToTrash")
	public TeeJson moveToTrash(HttpServletRequest request){
		TeeJson json = new TeeJson();
		DocumentInfoModel documentInfoModel = (DocumentInfoModel) TeeServletUtility.request2Object(request, DocumentInfoModel.class);
		documentService.moveToTrash(documentInfoModel);
		json.setRtState(true);
		
		RedisClient.getInstance().del("SITE");
		RedisClient.getInstance().del("CHNL");
		RedisClient.getInstance().del("DOC");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/clearTrash")
	public TeeJson clearTrash(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int channelId = TeeStringUtil.getInteger(request.getParameter("channelId"), 0);
		documentService.clearTrash(channelId);
		json.setRtState(true);
		
		RedisClient.getInstance().del("SITE");
		RedisClient.getInstance().del("CHNL");
		RedisClient.getInstance().del("DOC");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/recycle")
	public TeeJson recycle(HttpServletRequest request){
		int docId=TeeStringUtil.getInteger(request.getParameter("docId"),0);
		RedisClient.getInstance().del("SITE");
		RedisClient.getInstance().del("CHNL");
		RedisClient.getInstance().del("DOC");
		return documentService.recycle(request);
	}
	
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request){
		Map requestData = TeeServletUtility.getParamMap(request);
		return documentService.datagrid(dm, requestData);
	}
	
	@ResponseBody
	@RequestMapping("/datagridTrash")
	public TeeEasyuiDataGridJson datagridTrash(TeeDataGridModel dm,HttpServletRequest request){
		Map requestData = TeeServletUtility.getParamMap(request);
		return documentService.datagridTrash(dm, requestData);
	}
	
	
	
	@ResponseBody
	@RequestMapping("/search")
	public TeeJson search(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String  keyword = TeeStringUtil.getString(request.getParameter("keyword"), "");
		int  pageSize = TeeStringUtil.getInteger(request.getParameter("pageSize"), 10);
		int  curPage = TeeStringUtil.getInteger(request.getParameter("curPage"), 1);
		TeeLuceneSearcher searcher = new TeeLuceneSearcher();
		String cmsIndexPath = TeeSysProps.getProps().getProperty("CMS_CONTENT_INDEX_PATH");
		String result="";
		try {
			result = searcher.search(cmsIndexPath, keyword,pageSize,curPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		json.setRtData(TeeJsonUtil.jsonString2Json(result));
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 获取待审批的文档
	 * @param dm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCheckDocs")
	public TeeEasyuiDataGridJson getCheckDocs(TeeDataGridModel dm,HttpServletRequest request){
		Map requestData = TeeServletUtility.getParamMap(request);
		return documentService.getCheckDocs(dm, requestData,request);
	}
	
	
	/**
	 * 审核文档
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/approveDoc")
	public TeeJson approveDoc(HttpServletRequest request){
		RedisClient.getInstance().del("SITE");
		RedisClient.getInstance().del("CHNL");
		RedisClient.getInstance().del("DOC");
		return documentService.approveDoc(request);
	}
	
	
	/**
	 * 累积阅读量
	 * @param request
	 * @return
	 */
	@RequestMapping("/readCount")
	public void readCount(HttpServletRequest request){
		documentService.readCount(request);
	}
}
