package com.tianee.oa.subsys.supervise.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.supervise.bean.TeeSupFeedBack;
import com.tianee.oa.subsys.supervise.bean.TeeSupFeedBackReply;
import com.tianee.oa.subsys.supervise.bean.TeeSupervision;
import com.tianee.oa.subsys.supervise.model.TeeSupFeedBackReplyModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeSupFeedBackReplyService extends  TeeBaseService{

	@Autowired
	private TeeSmsManager smsManager;
	/**
	 * 新建、编辑
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		//获取页面上传来的参数
		String content=TeeStringUtil.getString(request.getParameter("content"));
		int fbId=TeeStringUtil.getInteger(request.getParameter("feedBackId"), 0);
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		
		TeeSupFeedBack fb=(TeeSupFeedBack) simpleDaoSupport.get(TeeSupFeedBack.class,fbId);
		if(sid>0){//编辑
			TeeSupFeedBackReply reply=(TeeSupFeedBackReply) simpleDaoSupport.get(TeeSupFeedBackReply.class,sid);
		    reply.setContent(content);
		    simpleDaoSupport.update(reply);
		    json.setRtState(true);
		    json.setRtMsg("编辑成功！");
		}else{//新建
			TeeSupFeedBackReply reply=new TeeSupFeedBackReply();
			reply.setContent(content);
			reply.setCreater(loginUser);
			reply.setCreateTime(new Date());	
			reply.setFb(fb);
			
			if(fb!=null){
				reply.setSup(fb.getSup());
			}
			
			simpleDaoSupport.save(reply);
			
			
			if(fb!=null&&fb.getSup()!=null){
				TeeSupervision sup=fb.getSup();
				//获取除了自己其他人员
	 	    	Set<TeePerson> users=sup.getAssists();
	 	    	if(sup.getLeader()!=null){
	 	    		users.add(sup.getLeader());
	 	    	}
	 	    	if(sup.getManager()!=null){
	 	    		users.add(sup.getManager());
	 	    	}
	 	    	String Ids="";
	 	    	for (TeePerson teePerson : users) {
					if(teePerson.getUuid()!=loginUser.getUuid()){
						Ids+=teePerson.getUuid()+",";
					}
				}
	 	    	
	 	    	if(Ids.endsWith(",")){
	 	    		Ids=Ids.substring(0,Ids.length()-1);
	 	    	}
	 	    	
				
				
				// 发送消息
				Map requestData1 = new HashMap();
				requestData1.put("content", "“"+loginUser.getUserName()+"”回复了督办反馈内容，请查看。");
				requestData1.put("userListIds", Ids);
				requestData1.put("moduleNo", "061");
				requestData1.put("remindUrl","/system/subsys/supervise/sms/feedBackDetail.jsp?fbId="+fb.getSid());
				smsManager.sendSms(requestData1, loginUser);
			}
			

			
			
		    json.setRtState(true);
		    json.setRtMsg("回复成功！");
		}
		return json;
	}

	
	/**
	 * 根据反馈主键  获取回复列表
	 * @param request
	 * @return
	 */
	public TeeJson getReplyListByFbId(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的反馈主键
		int fbId=TeeStringUtil.getInteger(request.getParameter("fbId"),0);
		List<TeeSupFeedBackReply> list=simpleDaoSupport.executeQuery(" from TeeSupFeedBackReply reply where reply.fb.sid=?  ", new Object[]{fbId});
		List<TeeSupFeedBackReplyModel> modelList=new ArrayList<TeeSupFeedBackReplyModel>();
		TeeSupFeedBackReplyModel model=null;
		for (TeeSupFeedBackReply teeSupFeedBackReply : list) {
			model=parseToModel(teeSupFeedBackReply);
			modelList.add(model);	
		}
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}


	/**
	 * 将实体类 转换成model
	 * @param teeSupFeedBackReply
	 * @return
	 */
	private TeeSupFeedBackReplyModel parseToModel(
			TeeSupFeedBackReply teeSupFeedBackReply) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeSupFeedBackReplyModel model=new TeeSupFeedBackReplyModel();
		BeanUtils.copyProperties(teeSupFeedBackReply, model);
		if(teeSupFeedBackReply.getCreater()!=null){
			model.setCreaterId(teeSupFeedBackReply.getCreater().getUuid());
			model.setCreaterName(teeSupFeedBackReply.getCreater().getUserName());
		}
		
		if(teeSupFeedBackReply.getCreateTime()!=null){
			model.setCreateTimeStr(sdf.format(teeSupFeedBackReply.getCreateTime()));
		}
		
		if(teeSupFeedBackReply.getFb()!=null){
			model.setFbId(teeSupFeedBackReply.getFb().getSid());
			model.setFbTitle(teeSupFeedBackReply.getFb().getTitle());
		}
		return model;
	}


	/**
	 * 根据主键  删除回复
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
	    TeeSupFeedBackReply reply=(TeeSupFeedBackReply) simpleDaoSupport.get(TeeSupFeedBackReply.class,sid);
	    simpleDaoSupport.deleteByObj(reply);
	    json.setRtState(true);
	    json.setRtMsg("删除成功！");
		return json;
	}


	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeSupFeedBackReply reply=(TeeSupFeedBackReply) simpleDaoSupport.get(TeeSupFeedBackReply.class,sid);
		    TeeSupFeedBackReplyModel model=parseToModel(reply);
		    json.setRtData(model);
		    json.setRtState(true);
		}else{
			json.setRtMsg("数据获取失败！");
			json.setRtState(false);
		}
		return json;
	}

}
