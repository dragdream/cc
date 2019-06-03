package com.tianee.oa.core.org.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserOnline;
import com.tianee.oa.core.org.dao.TeeUserOnlineDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeUserOnlineService extends TeeBaseService {
	@Autowired
	TeeUserOnlineDao userOnlineDao; 
	/**
	 * 新建
	 * @author syl
	 * @date 2013-11-19
	 * @param userOnline
	 */
	public void addUserOnline(TeeUserOnline userOnline) {
		userOnlineDao.addUserOnline(userOnline);	
	}
	
	public boolean checkIsOnline(int userId) {
		long count = simpleDaoSupport.count("select count(sid) from TeeUserOnline where userId="+userId, null);
		if(count>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 新建
	 * @author syl
	 * @date 2013-11-19
	 * @param userOnline
	 */
	public void addOrUpdateUserOnline(HttpServletRequest request , TeePerson loginUser , String sessionToken) {
		
//		TeeUserOnline userOnline = userOnlineDao.getUseronlineByUserId(loginUser.getUuid());
		TeeUserOnline userOnline = null;
//		boolean isNew = false;
//		if(userOnline == null ){
			userOnline = new TeeUserOnline();
//			isNew = true;
//		}
		String qDevice = request.getParameter("CLIENT");
		qDevice = qDevice==null?"1":qDevice;
		userOnline.setSessionToken(sessionToken);
		userOnline.setLoginTime(new Date());
		userOnline.setUserId(loginUser.getUuid());
		userOnline.setUserStatus(1);
		userOnline.setClient(qDevice);
		userOnline.setIp(request.getRemoteAddr());
//		if(isNew){
			userOnlineDao.addUserOnline(userOnline);	
//		}else{
//			userOnlineDao.UpdateUserOnline(userOnline);	
//		}
	}
	
	/**
	 * 更新
	 * @author syl
	 * @date 2013-11-19
	 * @param TeeDept
	 */
	public void UpdateUserOnline(TeeUserOnline userOnline) {
		userOnlineDao.UpdateUserOnline(userOnline);	
	}
	
	
	/**
	 * 更新在线状态
	 * @author syl
	 * @date 2013-11-19
	 * @ 
	 * @param sessionToken 
	 */
	public TeeJson updateBySessionToken( Map map) {
		TeeJson json = new TeeJson();
		String sessionToken  = TeeStringUtil.getString(map.get("JSESSIONID"));
		if(TeeUtility.isNullorEmpty(sessionToken)){
			int index = sessionToken.lastIndexOf(".");
			if(index >0){
				sessionToken = sessionToken.substring(0, index);
			}
		}
		int userStatus=  TeeStringUtil.getInteger(map.get("userOnlineStatus") , 1); //人员在线状态 空和0 -不在线，1- 在线 2-忙碌 3-离开
		userOnlineDao.updateBySessionToken(sessionToken, userStatus);
		json.setRtMsg("更新成功！");
		json.setRtState(true);
		return json;
	}
	
	public List<TeeUserOnline> getListByUser(TeePerson user){
		List<TeeUserOnline> list=null;
		if(user!=null){
			 list=simpleDaoSupport.executeQuery("from TeeUserOnline where userId=? ", new Object[]{user.getUuid()});
		}
		return list;
	}
	
	
	/**
	 * 查询byId
	 * @author syl
	 * @date 2013-11-19
	 * @param TeeDept
	 */
	public TeeUserOnline selectById(int id) {
		return userOnlineDao.selectById(id);	
	}
	/**
	 * 删除ById
	 * @author syl
	 * @date 2013-11-21
	 * @param id
	 */
	public void deleteById(int id) {
		userOnlineDao.delete(id);	
	}
	
	
	public void deleteBySessionToken(String sessionToken){
		userOnlineDao.deleteBySessionToken(sessionToken);
	}
	
}
