package com.tianee.oa.core.base.news.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tianee.oa.core.base.news.bean.TeeNewsComment;
import com.tianee.oa.core.base.news.service.TeeNewsCommentService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/teeNewsCommentController")
public class TeeNewsCommentController {

	@Autowired
	private TeeNewsCommentService commentService;
	
	/**
	 * 添加新闻评论
	 * @author zhp
	 * @createTime 2014-2-25
	 * @editTime 下午10:20:16
	 * @desc
	 */
	@RequestMapping("/addNewsComment")
	@ResponseBody
	public TeeJson addNewsComment(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int newsId = TeeStringUtil.getInteger(request.getParameter("newsId"),0);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String content = TeeStringUtil.getString(request.getParameter("content"),"");
		String nickName = TeeStringUtil.getString(request.getParameter("nickName"),"");
		System.out.println(nickName);
		TeeNewsComment comment = new TeeNewsComment();
		comment.setContent(content);
		comment.setNewsId(newsId);
		comment.setNickName(nickName);
		comment.setReTime(new Date());
		comment.setParentId(0);
		comment.setUserId(String.valueOf(loginPerson.getUuid()));
		try {
			commentService.addNewsComment(comment);
			json.setRtState(true);
			json.setRtMsg("发表评论成功!");
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("发表评论失败!");
		}
		return json;
	}
	
	 
	@RequestMapping("/deleteNewsComment")
	@ResponseBody
	public TeeJson deleteNewsComment(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int cid = TeeStringUtil.getInteger(request.getParameter("id"),0);
		try {
			commentService.deleteNewsComment(cid);
			json.setRtState(true);
			json.setRtMsg("删除评论成功!");
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("删除评论失败!");
		}
		return json;
	 }
	/**
	 * 获取所有新闻评论
	 * @author zhp
	 * @createTime 2014-2-25
	 * @editTime 下午10:20:32
	 * @desc
	 */
	@RequestMapping("/getNewsAllComment")
	@ResponseBody
	public TeeJson getNewsAllComment(HttpServletRequest request){
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int newsId = TeeStringUtil.getInteger(request.getParameter("id"),0);
		int count = TeeStringUtil.getInteger(request.getParameter("count"),-1);//
		int state = TeeStringUtil.getInteger(request.getParameter("state"),-1);// -1 ：全部 0： 自己  1：非自己
		TeeJson json = new TeeJson();
		List list = null;
		try {
			list = commentService.getNewsAllComments(newsId,count,state,loginPerson.getUuid());
			json.setRtData(list);
			json.setRtState(true);
			json.setRtMsg("获取新闻评论成功!");
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("获取新闻评论失败!");
		}
		return json;
	}
	
	@RequestMapping("/deleteNewsAllComment")
	@ResponseBody
	public TeeJson deleteNewsAllComment(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int newsId = TeeStringUtil.getInteger(request.getParameter("id"),0);
		try {
			commentService.deleteNewsAllComment(newsId);
			json.setRtState(true);
			json.setRtMsg("删除评论成功!");
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("删除评论失败!");
		}
		return json;
	 }
	
	
	public TeeNewsCommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(TeeNewsCommentService commentService) {
		this.commentService = commentService;
	}
	
	
}
