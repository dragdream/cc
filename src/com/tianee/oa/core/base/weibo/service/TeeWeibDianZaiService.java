package com.tianee.oa.core.base.weibo.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.weibo.bean.TeeWeibDianZai;
import com.tianee.oa.core.base.weibo.bean.TeeWeibPublish;
import com.tianee.oa.core.base.weibo.dao.TeeWeibDianZaiDao;
import com.tianee.oa.core.base.weibo.dao.TeeWeibPublishDao;
import com.tianee.oa.core.base.weibo.model.TeeWeibDianZaiModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeWeibDianZaiService extends TeeBaseService {

	@Autowired
	private TeeWeibDianZaiDao teeWeibDianZaiDao;
	
	@Autowired
	private TeeWeibPublishDao teeWeibPublishDao;

	/**
	 * 查询当前用户是否给某个微博信息点赞（微博）
	 * */
	public TeeJson findDianZan(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String infoStrId = request.getParameter("sid");//发布信息的id
		int infoId = Integer.parseInt(infoStrId);//发布信息id
		int userId =loginPerson.getUuid();//当前登录用户的id
		List<TeeWeibDianZai> find = teeWeibDianZaiDao.find("from TeeWeibDianZai where infoId=? and userId=?", new Object[]{infoId,userId});
		TeeWeibDianZaiModel model=new TeeWeibDianZaiModel();
		if(find!=null && find.size()>0){
			TeeWeibDianZai zai = find.get(0);
			model.setDianzai(true);//已经点赞
		}
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}

	/**
	 * 点赞（微博）
	 * */
	public TeeJson addDianZan(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String infoStrId = request.getParameter("sid");//发布信息的id
		int infoId = Integer.parseInt(infoStrId);//发布信息id
		int userId =loginPerson.getUuid();//当前登录用户的id
		TeeWeibDianZai zai=new TeeWeibDianZai();
		Date date=new Date();
		zai.setCreTime(date);
		zai.setInfoId(infoId);
		zai.setUserId(userId);
		List<TeeWeibDianZai> find = teeWeibDianZaiDao.find("from TeeWeibDianZai where infoId=? and userId=?", new Object[]{infoId,userId});
		if(find.size()==0){
			Serializable save = teeWeibDianZaiDao.save(zai);
			TeeWeibPublish publish = teeWeibPublishDao.get(infoId);
			if(publish!=null){
				int count = publish.getCount();
				count+=1;
				publish.setCount(count);
				teeWeibPublishDao.update(publish);//修改点赞次数
			}
			int integer = TeeStringUtil.getInteger(save, 0);
			if(integer>0){
				json.setRtState(true);
			}
		}
		return json;
	}

	/**
	 * 取消点赞（微博）
	 * */
	public TeeJson deleteDianZan(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String infoStrId = request.getParameter("sid");//发布信息的id
		int infoId = Integer.parseInt(infoStrId);//发布信息id
		int userId =loginPerson.getUuid();//当前登录用户的id
		List<TeeWeibDianZai> find = teeWeibDianZaiDao.find("from TeeWeibDianZai where infoId=? and userId=?", new Object[]{infoId,userId});
		if(find!=null && find.size()>0){
			int query = teeWeibDianZaiDao.deleteOrUpdateByQuery("delete from TeeWeibDianZai where infoId=? and userId=?", new Object[]{infoId,userId});
			TeeWeibPublish publish = teeWeibPublishDao.get(infoId);
			if(publish!=null){
				int count = publish.getCount();
				count=count-1;
				publish.setCount(count);
				teeWeibPublishDao.update(publish);//修改点赞次数
			}
			if(query>0){
				json.setRtState(true);
			 }
	 }
		return json;
	}
	
	
	

	/**
	 * 查询当前用户是否给某个微博信息点赞(评论)
	 * */
	public TeeJson findDianZan2(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String infoStrId = request.getParameter("sid");//发布信息评论的id
		int infoId = Integer.parseInt(infoStrId);//发布信息评论id
		int userId =loginPerson.getUuid();//当前登录用户的id
		List<TeeWeibDianZai> find = teeWeibDianZaiDao.find("from TeeWeibDianZai where replyId=? and userId=?", new Object[]{infoId,userId});
		TeeWeibDianZaiModel model=new TeeWeibDianZaiModel();
		if(find!=null && find.size()>0){
			TeeWeibDianZai zai = find.get(0);
			model.setDianzai(true);//已经点赞
		}
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}

	/**
	 * 点赞（评论）
	 * */
	public TeeJson addDianZan2(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String infoStrId = request.getParameter("sid");//发布信息评论的id
		int infoId = Integer.parseInt(infoStrId);//发布信息评论id
		int userId =loginPerson.getUuid();//当前登录用户的id
		TeeWeibDianZai zai=new TeeWeibDianZai();
		Date date=new Date();
		zai.setCreTime(date);
		zai.setReplyId(infoId);
		zai.setUserId(userId);
		Serializable save = teeWeibDianZaiDao.save(zai);
		//TeeWeibPublish publish = teeWeibPublishDao.get(infoId);
//		if(publish!=null){
//			int count = publish.getCount();
//			count+=1;
//			publish.setCount(count);
//			teeWeibPublishDao.update(publish);//修改点赞次数
//		}
		int integer = TeeStringUtil.getInteger(save, 0);
		if(integer>0){
			json.setRtState(true);
		}
		return json;
	}

	/**
	 * 取消点赞（评论）
	 * */
	public TeeJson deleteDianZan2(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String infoStrId = request.getParameter("sid");//发布信息评论的id
		int infoId = Integer.parseInt(infoStrId);//发布信息评论id
		int userId =loginPerson.getUuid();//当前登录用户的id
		int query = teeWeibDianZaiDao.deleteOrUpdateByQuery("delete from TeeWeibDianZai where replyId=? and userId=?", new Object[]{infoId,userId});
//		TeeWeibPublish publish = teeWeibPublishDao.get(infoId);
//		if(publish!=null){
//			int count = publish.getCount();
//			count=count-1;
//			publish.setCount(count);
//			teeWeibPublishDao.update(publish);//修改点赞次数
//		}
		if(query>0){
			json.setRtState(true);
		}
		return json;
	}
	
	
	
	

	/**
	 * 查询当前用户是否给某个微博评论回复信息点赞（回复）
	 * */
	public TeeJson findDianZan3(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String infoStrId = request.getParameter("sid");//发布信息评论回复的id
		int infoId = Integer.parseInt(infoStrId);//发布信息评论回复id
		int userId =loginPerson.getUuid();//当前登录用户的id
		List<TeeWeibDianZai> find = teeWeibDianZaiDao.find("from TeeWeibDianZai where huiFuId=? and userId=?", new Object[]{infoId,userId});
		TeeWeibDianZaiModel model=new TeeWeibDianZaiModel();
		if(find!=null && find.size()>0){
			TeeWeibDianZai zai = find.get(0);
			model.setDianzai(true);//已经点赞
		}
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}

	/**
	 * 点赞（回复）
	 * */
	public TeeJson addDianZan3(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String infoStrId = request.getParameter("sid");//发布信息评论回复的id
		int infoId = Integer.parseInt(infoStrId);//发布信息评论回复id
		int userId =loginPerson.getUuid();//当前登录用户的id
		TeeWeibDianZai zai=new TeeWeibDianZai();
		Date date=new Date();
		zai.setCreTime(date);
		zai.setHuiFuId(infoId);
		zai.setUserId(userId);
		Serializable save = teeWeibDianZaiDao.save(zai);
		int integer = TeeStringUtil.getInteger(save, 0);
		if(integer>0){
			json.setRtState(true);
		}
		return json;
	}

	/**
	 * 取消点赞（回复）
	 * */
	public TeeJson deleteDianZan3(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String infoStrId = request.getParameter("sid");//发布信息评论回复的id
		int infoId = Integer.parseInt(infoStrId);//发布信息评论回复id
		int userId =loginPerson.getUuid();//当前登录用户的id
		int query = teeWeibDianZaiDao.deleteOrUpdateByQuery("delete from TeeWeibDianZai where huiFuId=? and userId=?", new Object[]{infoId,userId});
		if(query>0){
			json.setRtState(true);
		}
		return json;
	}
}
