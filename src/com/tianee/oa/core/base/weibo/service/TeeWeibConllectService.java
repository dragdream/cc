package com.tianee.oa.core.base.weibo.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.weibo.bean.TeeWeibConllect;
import com.tianee.oa.core.base.weibo.dao.TeeWeibConllectDao;
import com.tianee.oa.core.base.weibo.model.TeeWeibConllectModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeWeibConllectService extends TeeBaseService {

	@Autowired
	private TeeWeibConllectDao teeWeibConllectDao;

	/**
	 * 查看当前登录人是否收藏此微博信息
	 * */
	public TeeJson findCollect(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String infoStrId = request.getParameter("sid");//发布微博信息id
		int userId = loginPerson.getUuid();//当前登录人的id
		List<TeeWeibConllect> find = teeWeibConllectDao.find("from TeeWeibConllect where infoId=? and userId=?", new Object[]{Integer.parseInt(infoStrId),userId});
		TeeWeibConllectModel model=new TeeWeibConllectModel();
		if(find!=null && find.size()>0){
			model.setConllect(true);//判断是否收藏微博信息
		}else{
			model.setConllect(false);
		}
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}

	/**
	 * 收藏
	 * */
	public TeeJson addCollect(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        String infoStrId = request.getParameter("sid");
        int userId = loginPerson.getUuid();
        TeeWeibConllect ct=new TeeWeibConllect();
        ct.setInfoId(Integer.parseInt(infoStrId));//收藏微博信息id
        Date date=new Date();
        ct.setInfoTime(date);//收藏时间
        ct.setUserId(userId);//收藏人
        Serializable save = teeWeibConllectDao.save(ct);
        int integer = TeeStringUtil.getInteger(save, 0);
        if(integer>0){
        	json.setRtState(true);
        }
		return json;
	}

	/**
	 * 取消收藏
	 * */
	public TeeJson deleteCollect(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        String infoStrId = request.getParameter("sid");//收藏发布微博信息id
        int userId = loginPerson.getUuid();//收藏人
        int query = teeWeibConllectDao.deleteOrUpdateByQuery("delete from TeeWeibConllect where infoId=? and userId=?", new Object[]{Integer.parseInt(infoStrId),userId});
        if(query>0){
        	json.setRtState(true);
        }
		return json;
	}
	
	
}
