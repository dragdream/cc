package com.tianee.oa.core.base.weibo.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.weibo.bean.TeeWeibDianZai;
import com.tianee.oa.core.base.weibo.bean.TeeWeibReply;
import com.tianee.oa.core.base.weibo.dao.TeeWeibDianZaiDao;
import com.tianee.oa.core.base.weibo.dao.TeeWeibReplyDao;
import com.tianee.oa.core.base.weibo.model.TeeWeibReplyModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeWeibReplyService extends TeeBaseService {

	@Autowired
	private TeeWeibReplyDao teeWeibReplyDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeWeibDianZaiDao teeWeibDianZaiDao;

	/**
	 * 添加回复
	 * */
	public TeeJson addReply(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        int userId = loginPerson.getUuid();//当前登录人的id
        TeeWeibReply re=new TeeWeibReply();
        String plIdStr = request.getParameter("infoId");
        String content = request.getParameter("content");
        String personId = request.getParameter("personId");
        content=workContent(content);
        re.setContent(content);//回复内容
        Date date=new Date();
        re.setCreTime(date);//回复时间
        re.setPersonId(Integer.parseInt(personId));
        re.setPlId(Integer.parseInt(plIdStr));//评论id
        re.setUserId(userId);//回复人
        Serializable save = teeWeibReplyDao.save(re);
        int integer = TeeStringUtil.getInteger(save, 0);
        if(integer>0){
        	json.setRtState(true);
        	json.setRtMsg("回复成功");
        }else{
        	json.setRtState(false);
        	json.setRtMsg("回复失败");
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
	 * 获取某评论下所有的回复
	 * */
	public TeeJson findReplyAll(int plId,HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeWeibReplyModel> modelList=new ArrayList<TeeWeibReplyModel>();
		if(plId>0){
			List<TeeWeibReply> find = teeWeibReplyDao.find("from TeeWeibReply where plId=?",new Object[]{plId});
			if(find!=null && find.size()>0){
				for(TeeWeibReply r:find){
					TeeWeibReplyModel model=new TeeWeibReplyModel();
					model.setContent(r.getContent());
					model.setCreTime(TeeStringUtil.getString(r.getCreTime(), "yyyy-MM-dd HH:mm"));
					//评论回复点赞次数
		    		List<TeeWeibDianZai> findList = teeWeibDianZaiDao.find("from TeeWeibDianZai where huiFuId=?", new Object[]{r.getSid()});
		    		if(findList!=null && findList.size()>0){
		    			model.setCountReply(findList.size());//评论回复点赞次数
		    		}
		    		//评断是否给评论回复点赞
		    		List<TeeWeibDianZai> findList2 = teeWeibDianZaiDao.find("from TeeWeibDianZai where huiFuId=? and userId=?", new Object[]{r.getSid(),loginPerson.getUuid()});
		    		if(findList2!=null && findList2.size()>0){
		    			model.setDianZanReply(true);
		    		}
					//model.setDianZanReply(dianZanReply);
					model.setPersonId(r.getPersonId());
					if(r.getPersonId()>0){
						TeePerson teePerson = personDao.get(r.getPersonId());
						model.setPersonName(teePerson.getUserName());
					}
					model.setPlId(r.getPlId());
					model.setSid(r.getSid());
					model.setUserId(r.getUserId());
					TeePerson teePerson2 = personDao.get(r.getUserId());
					model.setUserName(teePerson2.getUserName());
					modelList.add(model);
				}
			}
		}
		json.setRtData(modelList);
		return json;
	}

	/**
	 * 删除某条回复
	 * */
	public TeeJson deleteReply(int sid) {
		TeeJson json=new TeeJson();
		if(sid>0){
			teeWeibReplyDao.delete(sid);
			json.setRtMsg("删除成功");
			json.setRtState(true);
		}else{
			json.setRtMsg("删除失败");
			json.setRtState(false);
		}
		return json;
	}
}
