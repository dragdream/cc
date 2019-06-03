package com.tianee.oa.core.base.email.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.email.bean.TeeMailBox;
import com.tianee.oa.core.base.email.dao.TeeMailBoxDao;
import com.tianee.oa.core.base.email.dao.TeeMailDao;
import com.tianee.oa.core.base.email.model.TeeMailBoxModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeMailBoxService extends TeeBaseService{
	@Autowired
	private TeeMailBoxDao mailBoxDao;
	@Autowired
	private TeeMailDao mailDao;
	

	/**
	 * 获取自定义邮箱信息 ,根据mailBox 的sid
	 * @date 2014年6月24日
	 * @author 
	 * @param request
	 * @return
	 */
	public TeeJson getEmailBoxInfoByIdService(HttpServletRequest request) {
		int sid = 0;
		String sidStr = request.getParameter("sid");
		if (TeeUtility.isInteger(sidStr)) {
			sid = Integer.parseInt(sidStr);
		}
		TeeJson json = new TeeJson();
		TeeMailBoxModel model = new TeeMailBoxModel();
		String boxName = "";
		if (sid > 0) {
			TeeMailBox obj= mailBoxDao.get(sid);
			if (obj != null) {
				boxName = obj.getBoxName();
				Map map  = new HashMap();
				map.put("sid", obj.getSid());
				map.put("boxNo", obj.getBoxNo());
				map.put("boxName", boxName);
				
				json.setRtData(map);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("查询成功!");
		return json;
	}

	/**
	 * 删除自定义组
	 * @author syl
	 * @date 2014-6-28
	 * @param request
	 * @return
	 */
	public TeeJson deleEmailBoxById(HttpServletRequest request) {
		int sid = TeeStringUtil.getInteger(request.getParameter("sid") , 0);
		TeeJson json = new TeeJson();
		
		String hql = "update TeeMail mail set mail.mailBox=null,mail.deleteFlag = 0 where mail.mailBox.sid ="+ sid;
		
		//移动到收信箱
		mailDao.executeUpdate(hql, null);
		//先将自定义组的邮箱转到收件箱中
		mailBoxDao.delete(sid);
		json.setRtState(true);
		return json;
	}
	
	
	
	
	
}
