package com.tianee.oa.core.base.weibo.service;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.weibo.bean.TeeWeibComment;
import com.tianee.oa.core.base.weibo.bean.TeeWeibPublish;
import com.tianee.oa.core.base.weibo.dao.TeeWeibCommentDao;
import com.tianee.oa.core.base.weibo.dao.TeeWeibPublishDao;
import com.tianee.oa.core.base.weibo.dao.TeeWeibReplyDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeWeibCommentService extends TeeBaseService{

	@Autowired
	private TeeWeibCommentDao teeWeibCommentDao;
	
	@Autowired
	private TeeWeibPublishDao teeWeibPublishDao;
	
	@Autowired
	private TeeWeibReplyDao teeWeibReplyDao;

	/**
	 * 添加评论
	 * */
	public TeeJson addComment(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        int userId = loginPerson.getUuid();//当前登录人id
        String content=request.getParameter("content");
        String infoId=request.getParameter("infoId");
        TeeWeibComment comm=new TeeWeibComment();
        content=workContent(content);
        comm.setContent(content);//评论内容
        Date date=new Date();
        comm.setCreTime(date);//评论时间
        comm.setInfoId(Integer.parseInt(infoId));//微博id
        comm.setUserId(userId);//评论人
        Serializable save = teeWeibCommentDao.save(comm);
        int integer = TeeStringUtil.getInteger(save, 0);
        if(integer>0){
        	TeeWeibPublish publish = teeWeibPublishDao.get(Integer.parseInt(infoId));
        	int num = publish.getNum();
        	num+=1;
        	publish.setNum(num);//评论次数
        	teeWeibPublishDao.update(publish);
        	json.setRtState(true);
        	json.setRtMsg("评论成功");
        }else{
        	json.setRtMsg("评论失败");
        	json.setRtState(false);
        }
        
		return json;
	}
	
	/**
	 * 处理表情
	 * */
	public String workContent(String content){
		String contentStr="";
		int indexOf = 0;
		int indexOf2 = 0;
		while(content!=null && !"".equals(content)){
			indexOf = content.indexOf("[");
			indexOf2 = content.indexOf("]");
			if(indexOf<0 || indexOf2<0){
				contentStr+=content;
				content="";
			}else{
				contentStr+=content.substring(0,indexOf);//
				String indexof=content.substring(indexOf+1,indexOf2);
				
				if(indexof.matches("emo_[0-9]{1,}")){
					//indexof=indexof.substring(1,indexof.length()-1);
					contentStr+="<img alt='' style='width:22px;height:22px;' src='dist/arclist/"+indexof+".png'>";
				}
				content=content.substring(indexOf2+1,content.length());
			}
		}
		return contentStr;
	}

	/**
	 * 删除评论
	 * */
	public TeeJson deletePingLun(int sid) {
		TeeJson json=new TeeJson();
		if(sid>0){
			TeeWeibComment comment = teeWeibCommentDao.get(sid);
			int infoId = comment.getInfoId();
			TeeWeibPublish publish = teeWeibPublishDao.get(infoId);
			int num = publish.getNum();
			num=num-1;
			publish.setNum(num);
			teeWeibPublishDao.update(publish);//修改微博中评论的次数
			teeWeibCommentDao.delete(sid);//删除微博下的某条评论
			//删除评论下的所有回复
			teeWeibReplyDao.deleteOrUpdateByQuery("delete from TeeWeibReply where plId=?", new Object[]{sid});
			json.setRtState(true);
			json.setRtMsg("删除成功");
		}else{
			json.setRtState(false);
			json.setRtMsg("删除失败");
		}
		return json;
	}
	
	
}
