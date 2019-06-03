package com.tianee.oa.core.xt.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.xt.bean.TeeXTReply;
import com.tianee.oa.core.xt.bean.TeeXTRunPrcs;
import com.tianee.oa.core.xt.model.TeeXTReplyModel;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeXTReplyService extends TeeBaseService {
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private TeeSmsManager smsManager;
	/**
	 * 新增回复
	 * @param request
	 * @return
	 */
	public TeeJson add(HttpServletRequest request) {
		TeeJson json=new TeeJson();	
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int prcsId=TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);
		String content=TeeStringUtil.getString(request.getParameter("content"));
		String attachmentSidStr=TeeStringUtil.getString(request.getParameter("attachmentSidStr"));
		TeeXTRunPrcs prcs=(TeeXTRunPrcs) simpleDaoSupport.get(TeeXTRunPrcs.class,prcsId);
		
		TeeXTReply reply=new TeeXTReply();
		reply.setContent(content);
		reply.setCreateTime(Calendar.getInstance());
		reply.setCreateUser(loginUser);
		reply.setXtRunPrcs(prcs);
		
		simpleDaoSupport.save(reply);
		
		//处理附件
        List<TeeAttachment> attList=attachmentService.getAttachmentsByIds(attachmentSidStr);
        if(attList!=null&&attList.size()>0){
        	for (TeeAttachment teeAttachment : attList) {
        		teeAttachment.setModelId(reply.getSid()+"");
        		simpleDaoSupport.update(teeAttachment);
			}
        }
        
        //发送系统消息
    	Map requestData1 = new HashMap();
    	requestData1.put("content", loginUser.getUserName()+"回复了您的协同意见：《"+prcs.getXtRun().getSubject()+"》，回复内容："+content);
    	requestData1.put("userListIds", prcs.getPrcsUser().getUuid());
    	requestData1.put("moduleNo", "100");
    	requestData1.put("remindUrl","/system/core/xt/detail.jsp?sid="+prcs.getXtRun().getSid());
    	smsManager.sendSms(requestData1, loginUser);
		
		json.setRtState(true);
		return json;
	}

	
	/**
	 * 获取回复列表
	 * @param request
	 * @return
	 */
	public TeeJson getReplyListByPrcsId(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int prcsId=TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);
		List<TeeXTReply> replyList=simpleDaoSupport.executeQuery(" from TeeXTReply where xtRunPrcs.sid=?  ", new Object[]{prcsId});
		List<TeeXTReplyModel>modelList=new ArrayList<TeeXTReplyModel>();
		TeeXTReplyModel model=null;
		if(replyList!=null&&replyList.size()>0){
			for (TeeXTReply reply : replyList) {
				model=parseToModel(reply);
				modelList.add(model);
			}
		}
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}


	
	/**
	 * 实体类转换成model
	 * @param reply
	 * @return
	 */
	private TeeXTReplyModel parseToModel(TeeXTReply reply) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeXTReplyModel model=new TeeXTReplyModel();
		BeanUtils.copyProperties(reply, model);
		if(reply.getCreateTime()!=null){
			model.setCreateTimeStr(sdf.format(reply.getCreateTime().getTime()));
		}
		
		if(reply.getCreateUser()!=null){
		   model.setCreateUserId(reply.getCreateUser().getUuid());	
		   model.setCreateUserName(reply.getCreateUser().getUserName());
		}
		
		//处理附件
		List<TeeAttachmentModel> attList=attachmentService.getAttacheModels(TeeAttachmentModelKeys.XT_REPLY_ATTACH, reply.getSid()+"");
		model.setAttList(attList);
		return model;
	}

}
