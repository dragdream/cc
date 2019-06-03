package com.tianee.oa.subsys.zhidao.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.zhidao.bean.TeeZhiDaoAnswer;
import com.tianee.oa.subsys.zhidao.bean.TeeZhiDaoQuestion;
import com.tianee.oa.subsys.zhidao.model.TeeZhiDaoAnswerModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeZhiDaoAnswerService extends TeeBaseService{

	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	/**
	 * 获取问题的最佳答案
	 * @param request
	 * @return
	 */
	public TeeJson getBestAnswer(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int questionSid=TeeStringUtil.getInteger(request.getParameter("questionSid"), 0);
		String hql=" from TeeZhiDaoAnswer where question.sid=? and isBest=1 ";
		List<TeeZhiDaoAnswer> list=simpleDaoSupport.executeQuery(hql, new Object[]{questionSid});
		TeeZhiDaoAnswerModel model=null;
		if(list!=null&&list.size()>0){
			model=parseToModel(list.get(0));
			json.setRtState(true);
			json.setRtData(model);
		}else{
			json.setRtState(false);
			json.setRtMsg("暂无最佳回答！");
		}
		return json;
	}

	
	/**
	 * 实体类 转换成model
	 * @param teeZhiDaoAnswer
	 * @return
	 */
	private TeeZhiDaoAnswerModel parseToModel(TeeZhiDaoAnswer teeZhiDaoAnswer) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		TeeZhiDaoAnswerModel model=new TeeZhiDaoAnswerModel();
		BeanUtils.copyProperties(teeZhiDaoAnswer, model);
		if(teeZhiDaoAnswer.getCreateUser()!=null){
		 	model.setCreateUserId(teeZhiDaoAnswer.getCreateUser().getUuid());
		    model.setCreateUserName(teeZhiDaoAnswer.getCreateUser().getUserName());
		    model.setAvatar(teeZhiDaoAnswer.getCreateUser().getAvatar());
		    if(teeZhiDaoAnswer.getCreateUser().getDept()!=null){
		    	model.setCreateUserInfo(teeZhiDaoAnswer.getCreateUser().getUserName()+"["+teeZhiDaoAnswer.getCreateUser().getDept().getDeptName()+"]");
		    }else{
		    	model.setCreateUserInfo(teeZhiDaoAnswer.getCreateUser().getUserName());
		    }
		}
		if(teeZhiDaoAnswer.getCreateTime()!=null){
			model.setCreateTimeStr(sdf.format(teeZhiDaoAnswer.getCreateTime().getTime()));
		}
		
		if(teeZhiDaoAnswer.getQuestion()!=null){
			model.setQuestionId(teeZhiDaoAnswer.getQuestion().getSid());
			model.setQuestionName(teeZhiDaoAnswer.getQuestion().getTitle());
		}
		
		List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
		List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.zhiDaoAnswer, String.valueOf(teeZhiDaoAnswer.getSid()));
		for (TeeAttachment attach : attaches) {
			TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, attachmentModel);
			attachmentModel.setUserId(attach.getUser().getUuid() + "");
			attachmentModel.setUserName(attach.getUser().getUserName());
			attachmentModel.setPriv(1 + 2 + 4);// 一共五个权限好像
											// 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
			attachmodels.add(attachmentModel);
		}
		model.setAttachMentModel(attachmodels);
		
		return model;
	}


	/**
	 * 获取其他回到
	 * @param request
	 * @return
	 */
	public TeeJson getOtherAnswer(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int questionSid=TeeStringUtil.getInteger(request.getParameter("questionSid"), 0);
		String hql=" from TeeZhiDaoAnswer where question.sid=? and isBest=0 order by createTime desc";
		List<TeeZhiDaoAnswer> list=simpleDaoSupport.executeQuery(hql, new Object[]{questionSid});
		TeeZhiDaoAnswerModel model=null;
		List<TeeZhiDaoAnswerModel> modelList=new ArrayList<TeeZhiDaoAnswerModel>();
		if(list!=null&&list.size()>0){
			for (TeeZhiDaoAnswer teeZhiDaoAnswer : list) {
				model=parseToModel(teeZhiDaoAnswer);
				modelList.add(model);
			}
		}
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}


	
	/**
	 * 我来回答
	 * @param request
	 * @return
	 */
	public TeeJson add(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		String attachmentSidStr = TeeStringUtil.getString(request.getParameter("attachmentSidStr"));
		List<TeeAttachment> attachments = attachmentDao.getAttachmentsByIds(attachmentSidStr);//新上传附件
		
		int questionSid=TeeStringUtil.getInteger(request.getParameter("questionSid"),0);
		String content=TeeStringUtil.getString(request.getParameter("content"));
		TeeZhiDaoAnswer answer=new TeeZhiDaoAnswer();
		TeeZhiDaoQuestion q=(TeeZhiDaoQuestion) simpleDaoSupport.get(TeeZhiDaoQuestion.class,questionSid);
		answer.setContent(content);
		answer.setCreateTime(Calendar.getInstance());
		answer.setCreateUser(loginUser);
		answer.setIsBest(0);
		answer.setQuestion(q);
		
		simpleDaoSupport.save(answer);
		//添加新附件
		if(attachments!=null&&attachments.size()>0){
			for(TeeAttachment attach:attachments){
				attach.setModelId(String.valueOf(answer.getSid()));
				attachmentDao.update(attach);
			}
		}
		
		json.setRtState(true);
		return json;
	}


	
	/**
	 * 删除回答
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeZhiDaoAnswer a=(TeeZhiDaoAnswer) simpleDaoSupport.get(TeeZhiDaoAnswer.class,sid);
		if(a!=null){
			simpleDaoSupport.deleteByObj(a);
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("该回答已不存在！");
		}
		return json;
	}

}
