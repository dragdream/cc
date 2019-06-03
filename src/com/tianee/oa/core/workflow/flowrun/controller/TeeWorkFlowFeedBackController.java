package com.tianee.oa.core.workflow.flowrun.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import sun.misc.BASE64Encoder;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFeedBack;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeWorkFlowFeedBackService;
import com.tianee.oa.core.workflow.flowrun.service.TeeWorkFlowFeedBackServiceInterface;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/feedBack")
public class TeeWorkFlowFeedBackController {

	@Autowired
	private TeeWorkFlowFeedBackServiceInterface flowFeedBackServic;

	@Autowired
	private TeeBaseUpload upload;
	
	@Autowired
	private TeeFlowRunPrcsServiceInterface flowRunPrcsService;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private TeePersonService personService;
	
	@RequestMapping("/addFeedBack")
	public void addFeedBack(HttpServletRequest request,HttpServletResponse response) throws IOException {
		TeeJson json = new TeeJson();
		
		 MultipartHttpServletRequest multipartRequest = null;
		 if(request instanceof MultipartHttpServletRequest){//如果request是MultipartHttpServletRequest的实例，则转换
			 multipartRequest = (MultipartHttpServletRequest) request;
		 }
		 
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		TeeFlowRunPrcs frp = flowRunPrcsService.get(frpSid);
		
		int replyId=TeeStringUtil.getInteger(request.getParameter("replyId"),0);
		
		String content = TeeStringUtil.getString(request.getParameter("content"),"");
		
		String signData = TeeStringUtil.getString(request.getParameter("FEEDBACK_SIGN_DATA"),"");
		String voiceId = TeeStringUtil.getString(request.getParameter("voiceId"),"");
		
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		List list = null;
		if(multipartRequest!=null){
			list = upload.manyAttachUpload(multipartRequest, TeeAttachmentModelKeys.workFlowFeedBack);
		}
		
		PrintWriter pw = response.getWriter();
		
		//如果啥数据也没有，则过滤掉
		if("".equals(content) && list.size()==0 && "".equals(signData) && "".equals(voiceId)){
			json.setRtState(true);
			pw.write("<script>try{parent.parentCallback();}catch(e){}</script>");
			return ;
		}
		
		
		TeeFeedBack fb = new TeeFeedBack();
		fb.setContent(content);
		fb.setEditTime(Calendar.getInstance());
		fb.setFeedFlag(0);
		fb.setPrcsId(frp.getPrcsId());
		fb.setFlowRun(frp.getFlowRun());
		fb.setFlowPrcs(frp.getFlowPrcs()==null?null:frp.getFlowPrcs());
		fb.setUserPerson(person);
		fb.setSignData(signData);
		fb.setVoiceId(voiceId);
		fb.setReplayId(replyId);
		fb.setUserName(person.getUserName());
		if(person.getDept()!=null){
			fb.setDeptName(person.getDept().getDeptName());
			fb.setDeptFullPath(person.getDept().getDeptFullName());
		}
		if(person.getUserRole()!=null){
			fb.setRoleName(person.getUserRole().getRoleName());
		}
		//fb.setFlowPrcs(flowPrcs);
		TeePerson teePerson = personService.get(person.getUuid());
		String base64 = getBase64(teePerson);
		fb.setSignature(base64);
		try {
			flowFeedBackServic.addFeedBack(fb);
			for(int i=0;list!=null && i<list.size();i++){
				TeeAttachment att = (TeeAttachment)list.get(i);
				att.setModelId(String.valueOf(fb.getSid()));
				attachmentService.updateAttachment(att);
			}
			
			json.setRtMsg("添加会签意见成功!");
			json.setRtState(true);
			pw.write("<script>parent.parentCallback();</script>");
		} catch (Exception e) {
			json.setRtMsg("添加会签意见出错!");
			json.setRtState(false);
			pw.write("<script>alert('添加会签意见出错!');</script>");
		}
	}

	public String getBase64(TeePerson person){
		String encode="";
		if(person!=null){
			TeeAttachment attach = person.getAttach();
			if(attach!=null && attach.getSid()>0){
				InputStream in = null;
				byte[] data1 = null;
				//读取图片字节数组
				try 
				{
					in = new FileInputStream(attach.getFilePath());        
					data1 = new byte[in.available()];
					in.read(data1);
					in.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				//对字节数组Base64编码  
				BASE64Encoder encoder1 = new BASE64Encoder();  
				encode = encoder1.encode(data1);//返回Base64编码过的字节数组字符串  
				encode="data:image/png;base64,"+encode.replaceAll("\r|\n", "");
			}
		}
		return encode;
	}
	
	/**
	 * 添加回复会签意见
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/addReplyFeedBack")
	@ResponseBody
	public TeeJson addReplyFeedBack(HttpServletRequest request,HttpServletResponse response) throws IOException {
		TeeJson json = new TeeJson();
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		TeeFlowRunPrcs frp = flowRunPrcsService.get(frpSid);
		int replyId=TeeStringUtil.getInteger(request.getParameter("replyId"),0);
		String content = TeeStringUtil.getString(request.getParameter("content"),"");
		String signData = TeeStringUtil.getString(request.getParameter("FEEDBACK_SIGN_DATA"),"");
		String voiceId = TeeStringUtil.getString(request.getParameter("voiceId"),"");
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		TeeFeedBack fb = new TeeFeedBack();
		fb.setContent(content);
		fb.setEditTime(Calendar.getInstance());
		fb.setFeedFlag(0);
		fb.setPrcsId(frp.getPrcsId());
		fb.setFlowRun(frp.getFlowRun());
		fb.setFlowPrcs(frp.getFlowPrcs()==null?null:frp.getFlowPrcs());
		fb.setUserPerson(person);
		fb.setSignData(signData);
		fb.setVoiceId(voiceId);
		fb.setReplayId(replyId);
		fb.setUserName(person.getUserName());
		if(person.getDept()!=null){
			fb.setDeptName(person.getDept().getDeptName());
			fb.setDeptFullPath(person.getDept().getDeptFullName());
		}
		if(person.getUserRole()!=null){
			fb.setRoleName(person.getUserRole().getRoleName());
		}
		
		try {
			flowFeedBackServic.addFeedBack(fb);
			
			json.setRtMsg("回复成功!");
			json.setRtState(true);
			return json;
		} catch (Exception e) {
			json.setRtMsg("回复失败！");
			json.setRtState(false);
			return json;
		}
	}
	
	@RequestMapping("/deleteFeedBack")
	@ResponseBody
	public TeeJson deleteFeedBack(HttpServletRequest request) throws IOException {
		TeeJson json = new TeeJson();
		int fid = TeeStringUtil.getInteger(request.getParameter("fid"), 0);
		try {
			flowFeedBackServic.deleteFeedBack(fid);
			json.setRtMsg("删除会签意见成功!");
			json.setRtState(true);
		} catch (Exception e) {
			json.setRtMsg("删除会签意见成功!");
			json.setRtState(true);
		}
		return json;
	}
	
	@RequestMapping("/deleteFeedBackAttach")
	@ResponseBody
	public TeeJson deleteFeedBackAttach(HttpServletRequest request) throws IOException {
		TeeJson json = new TeeJson();
		int aid = TeeStringUtil.getInteger(request.getParameter("aid"), 0);
		int fid = TeeStringUtil.getInteger(request.getParameter("fid"), 0);
		try {
			flowFeedBackServic.deleteFeedBackAttach(fid,aid);
			json.setRtMsg("删除会签附件成功!");
			json.setRtState(true);
		} catch (Exception e) {
			json.setRtMsg("删除会签附件成功!");
			json.setRtState(true);
		}
		return json;
	}
	/**
	 * 获取会签意见
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getFeedBackList")
	@ResponseBody
	public TeeJson getFeedBackList(HttpServletRequest request,HttpServletResponse response) {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		/**
		 * 获取一下单签流程是否为 自由流程 或者 固定流程
		 */
		List fbList = flowFeedBackServic.selectPrcsPrivedFeedBackByRunId(runId,frpSid,loginPerson.getUuid());
		
		json.setRtData(fbList);
		json.setRtState(true);
		json.setRtMsg("获取会签意见成功!");
		return json;
	}
	
	/**
	 * 校验当前人员是否在某步骤进行会签
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/hasFeedback")
	@ResponseBody
	public TeeJson hasFeedback(HttpServletRequest request){
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		TeeFlowRunPrcs frp = flowRunPrcsService.get(frpSid);
		boolean hasExist = flowFeedBackServic.hasFeedback(frp);
		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtData(hasExist);
		
		return json;
	}
	
	/**
	 * 获取会签意见
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getFeedBackSealById")
	@ResponseBody
	public TeeJson getFeedBackSealById(HttpServletRequest request,HttpServletResponse response) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String sealData = "";
		try {
			sealData =  flowFeedBackServic.getFeedBackSealDataById(sid);
			json.setRtData(sealData);
			json.setRtState(true);
			json.setRtMsg("获取会签印章成功!");
		
		} catch (Exception e) {
			json.setRtMsg("查询会签印章失败!");
			json.setRtState(false);
		}
		return json;
	}
	
	
	
	/**
	 * 根据runId  和  itemId  获取会签控件
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getFeedBackByRunIdAndItemId")
	@ResponseBody
	public TeeJson getFeedBackByRunIdAndItemId(HttpServletRequest request,HttpServletResponse response) {
		return 	flowFeedBackServic.getFeedBackByRunIdAndItemId(request);
	}
	
	public TeeWorkFlowFeedBackServiceInterface getFlowFeedBackServic() {
		return flowFeedBackServic;
	}

	public void setFlowFeedBackServic(TeeWorkFlowFeedBackService flowFeedBackServic) {
		this.flowFeedBackServic = flowFeedBackServic;
	}

	public TeeBaseUpload getUpload() {
		return upload;
	}

	public void setUpload(TeeBaseUpload upload) {
		this.upload = upload;
	}

	
	
}
