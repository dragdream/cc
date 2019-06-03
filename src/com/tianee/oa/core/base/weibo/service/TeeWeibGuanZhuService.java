package com.tianee.oa.core.base.weibo.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.weibo.bean.TeeWeibGuanZhu;
import com.tianee.oa.core.base.weibo.bean.TeeWeibGzTopic;
import com.tianee.oa.core.base.weibo.bean.TeeWeibTopic;
import com.tianee.oa.core.base.weibo.dao.TeeWeibGuanZhuDao;
import com.tianee.oa.core.base.weibo.dao.TeeWeibGzTopicDao;
import com.tianee.oa.core.base.weibo.dao.TeeWeibPublishDao;
import com.tianee.oa.core.base.weibo.dao.TeeWeibTopicDao;
import com.tianee.oa.core.base.weibo.model.TeeWeibGuanZhuModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeWeibGuanZhuService extends TeeBaseService {

	@Autowired
	private TeeWeibGuanZhuDao teeWeibGuanZhuDao;
	
	@Autowired
	private TeeWeibPublishDao teeWeibPublishDao;
	
	@Autowired
	private TeePersonDao teePersonDao;
	
	@Autowired
	private TeeWeibGzTopicDao teeWeibGzTopicDao;
	
	@Autowired
	private TeeWeibTopicDao  teeWeibTopicDao;

	/**
	 * 关注
	 * */
	public TeeJson addGuanZhu(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        int userId = loginPerson.getUuid();//当前登录人（关注人）
        String personId = request.getParameter("personId");//被关注人
        TeeWeibGuanZhu gz=new TeeWeibGuanZhu();
        Date date=new Date();
        gz.setCreTime(date);
        gz.setUserId(userId);
        if(personId!=null && !"".equals(personId)){
        	gz.setPersonId(Integer.parseInt(personId));
        }
        Serializable save = teeWeibGuanZhuDao.save(gz);
        int integer = TeeStringUtil.getInteger(save, 0);
        if(integer>0){
        	json.setRtMsg("关注成功");
        	json.setRtState(true);
        }else{
        	json.setRtMsg("关注失败");
        	json.setRtState(false);
        }
		return json;
	}

	/**
	 * 取消关注
	 * */
	public TeeJson deleteGuanZhu(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        int userId = loginPerson.getUuid();//当前登录人（关注人）
        String personId = request.getParameter("sid");//被关注人
        int query = teeWeibGuanZhuDao.deleteOrUpdateByQuery("delete from TeeWeibGuanZhu where personId=? and userId=?", new Object[]{Integer.parseInt(personId),userId});
        if(query>0){
        	json.setRtMsg("取消关注");
        	json.setRtState(true);
        }else{
        	json.setRtState(false);
        }
		return json;
	}

	/**
	 * 查看当前登录人是否关注了此人
	 * */
	public TeeJson findGuanZhu(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        int userId = loginPerson.getUuid();//当前登录人（关注人）
        String personId = request.getParameter("sid");//被关注人
        List<TeeWeibGuanZhu> find = teeWeibGuanZhuDao.find("from TeeWeibGuanZhu where personId=? and userId=?", new Object[]{Integer.parseInt(personId),userId});
        TeeWeibGuanZhuModel model=new TeeWeibGuanZhuModel();
        if(find!=null && find.size()>0){
        	model.setGuanZhu(true);
        }
        json.setRtData(model);
        json.setRtState(true);
		return json;
	}

	/**
	 * 统计当前登录人关注的人数，统计被关注的人数（关注当前登录人的人数），统计当前登录人发布的微博数量
	 * */
	public TeeJson countPerson(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        int userId = loginPerson.getUuid();//当前登录人的id
        Long countPerson = teeWeibGuanZhuDao.count("select count(*) from TeeWeibGuanZhu where userId=?", new Object[]{userId});//关注人数
        Long count = teeWeibGzTopicDao.count("select count(*) from TeeWeibGzTopic where userId=?", new Object[]{userId});
        Long countByPerson=teeWeibGuanZhuDao.count("select count(*) from TeeWeibGuanZhu where personId=?", new Object[]{userId});//被关注人数
        Long countPublish=teeWeibPublishDao.count("select count(*) from TeeWeibPublish where userId=?", new Object[]{userId});//当前登录人发布的微博数
        TeeWeibGuanZhuModel model=new TeeWeibGuanZhuModel();
        model.setCountByPerson(TeeStringUtil.getInteger(countByPerson,0));//粉丝数量
        model.setCountPeson(TeeStringUtil.getInteger(countPerson,0)+TeeStringUtil.getInteger(count,0));//关注人数
        model.setCountPublish(TeeStringUtil.getInteger(countPublish,0));//微博数量
        model.setUserId(userId);
        model.setUserName(loginPerson.getUserName());//当前登录人姓名
        model.setDeptName(loginPerson.getDept().getDeptName());//当前登录人部门
        json.setRtData(model);
        json.setRtState(true);
        return json;
	}

	/**
	 * 查询未被当前登录人关注的人员信息（随机5条）
	 * */
	public TeeJson findByPerson(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        int userId = loginPerson.getUuid();//当前登录人的id
//        List<TeeWeibGuanZhu> find = teeWeibGuanZhuDao.find("from TeeWeibGuanZhu where userId=?", new Object[]{userId});
//        String str="";
//        if(find!=null && find.size()>0){
//        	for(TeeWeibGuanZhu gz:find){
//        		str+=gz.getPersonId()+",";
//        	}
//        }
//        str+=userId;
//        str="("+str+")";
        List<TeePersonModel> modelList=new ArrayList<TeePersonModel>();
        String hql="from TeePerson where uuid not in (select personId from TeeWeibGuanZhu where userId="+userId+") and uuid !="+userId;
        List<TeePerson> find2 = teePersonDao.find(hql, null);
        if(find2!=null && find2.size()>0){
        	int size = find2.size();
        	if(find2.size()>5){
        		while(modelList.size()<5){
            		Random rd=new Random();
            		int b=(int)(Math.random()*(size-1));
            		TeePersonModel model=new TeePersonModel();
            	    TeePerson p = find2.get(b);
            	    model.setUuid(p.getUuid());
            	    model.setAvatar(p.getAvatar());
            		model.setUserName(p.getUserName());
            		model.setDeptIdName(p.getDept().getDeptName());
            		if(!modelList.contains(model)){
            			modelList.add(model);
            		}
            	}
        	}else{
        		for(TeePerson p:find2){
        			TeePersonModel model=new TeePersonModel();
            	    model.setUuid(p.getUuid());
            	    model.setAvatar(p.getAvatar());
            		model.setUserName(p.getUserName());
            		model.setDeptIdName(p.getDept().getDeptName());
            		modelList.add(model);
        		}
        		
        	}
        	
        }
        json.setRtData(modelList);
        json.setRtState(true);
		return json;
	}

	/**
	 * 关注（部门）
	 * */
	public TeeJson addGuanZhuDept(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//当前登录人的id
		int userId = loginPerson.getUuid();
		//关注部门的id
		String deptId = request.getParameter("deptId");
		//获取本部门中所有的人员
		List<TeePerson> find = teePersonDao.find("from TeePerson where dept.uuid=?", new Object[]{Integer.parseInt(deptId)});
		if(find!=null && find.size()>0){
			for(TeePerson p:find){
				List<TeeWeibGuanZhu> find2 = teeWeibGuanZhuDao.find("from TeeWeibGuanZhu where userId=? and personId=?", new Object[]{userId,p.getUuid()});
				if((find2==null || find2.size()==0) && p.getUuid()!=userId){
					TeeWeibGuanZhu gz=new TeeWeibGuanZhu();
					Date date=new Date();
					gz.setCreTime(date);//关注时间
					gz.setPersonId(p.getUuid());//被关注人
					gz.setUserId(userId);//关注人
					teeWeibGuanZhuDao.save(gz);//添加关注
				}
			}
			json.setRtState(true);
			json.setRtMsg("关注成功");
		}else{
			json.setRtState(false);
			json.setRtMsg("关注失败");
		}
		return json;
	}

	/**
	 * 取消关注（部门）
	 * */
	public TeeJson deleteGuanZhuDept(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//当前登录人的id
		int userId = loginPerson.getUuid();
		//关注部门的id
		String deptId = request.getParameter("deptId");
		//获取本部门所有的人员
		List<TeePerson> find = teePersonDao.find("from TeePerson where dept.uuid=?", new Object[]{Integer.parseInt(deptId)});
		if(find!=null && find.size()>0){
			for(TeePerson p:find){
				List<TeeWeibGuanZhu> find2 = teeWeibGuanZhuDao.find("from TeeWeibGuanZhu where userId=? and personId=?", new Object[]{userId,p.getUuid()});
				if(find2!=null && find2.size()>0){
					TeeWeibGuanZhu zhu = find2.get(0);
					teeWeibGuanZhuDao.delete(zhu.getSid());//取消关注
				}
			}
			json.setRtState(true);
			json.setRtMsg("取消关注");
		}else{
			json.setRtState(false);
			json.setRtMsg("取消失败");
		}
		return json;
	}

	/**
	 * 关注话题
	 * */
	public TeeJson addGzTopic(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int userId = loginPerson.getUuid();//当前登录人（关注人）
		String topicName = request.getParameter("topicName");
		int topicId=0;
		List<TeeWeibTopic> find = teeWeibTopicDao.find("from TeeWeibTopic where topic='"+topicName+"'", null);
	    if(find!=null && find.size()>0){
	    	TeeWeibTopic topic = find.get(0);
	    	topicId = topic.getSid();
	    }
	    TeeWeibGzTopic gz=new TeeWeibGzTopic();
	    Date date=new Date();
	    gz.setCreTime(date);
	    gz.setUserId(userId);//关注人
	    gz.setTopicId(topicId);//关注的话题
	    Serializable save = teeWeibGzTopicDao.save(gz);
	    int integer = TeeStringUtil.getInteger(save, 0);
	    if(integer>0){
	       json.setRtMsg("关注成功");
	       json.setRtState(true);
	     }else{
	       json.setRtMsg("关注失败");
	       json.setRtState(false);
	     }
		return json;
	}
	
	/**
	 * 取消关注（话题）
	 * */
	public TeeJson deleteGzTopic(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        int userId = loginPerson.getUuid();//当前登录人（关注人）
        int topicId=0;
        String topicName = request.getParameter("topicName");
		List<TeeWeibTopic> find = teeWeibTopicDao.find("from TeeWeibTopic where topic='"+topicName+"'", null);
	    if(find!=null && find.size()>0){
	    	TeeWeibTopic topic = find.get(0);
	    	topicId = topic.getSid();
	    }
        int query = teeWeibGzTopicDao.deleteOrUpdateByQuery("delete from TeeWeibGzTopic where topicId=? and userId=?", new Object[]{topicId,userId});
        if(query>0){
        	json.setRtMsg("取消关注");
        	json.setRtState(true);
        }else{
        	json.setRtState(false);
        }
		return json;
	}

	/**
	 * 判断当前登录人是否关注此话题
	 * */
	public TeeJson loginUserGzTopic(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        int userId = loginPerson.getUuid();//当前登录的id
        String topicName = request.getParameter("topicName");
        List<TeeWeibTopic> find = teeWeibTopicDao.find("from TeeWeibTopic where topic='"+topicName+"'", null);
        if(find!=null && find.size()>0){
        	TeeWeibTopic topic = find.get(0);
            int sid = topic.getSid();
            List<TeeWeibGzTopic> find2 = teeWeibGzTopicDao.find("from TeeWeibGzTopic where topicId=? and userId=?", new Object[]{sid,userId});
            if(find2!=null && find2.size()>0){
            	json.setRtData(true);
            }else{
            	json.setRtData(false);
            }
        }
		return json;
	}

	/**
	 * 话题关注（数量）
	 * */
	public TeeEasyuiDataGridJson countTopic(String topicName) {
		TeeEasyuiDataGridJson easyjson =new TeeEasyuiDataGridJson();
		 List<TeeWeibTopic> find = teeWeibTopicDao.find("from TeeWeibTopic where topic='"+topicName+"'", null);
		 List<TeeWeibGuanZhuModel> model=new ArrayList();
		 Long count=0L;
		 if(find!=null && find.size()>0){
			 TeeWeibTopic topic = find.get(0);
			 int sid = topic.getSid();
			  count= teeWeibGzTopicDao.count("select count(*) from TeeWeibGzTopic where topicId=?", new Object[]{sid});
			  List<TeeWeibGzTopic> find2 = teeWeibGzTopicDao.find("from TeeWeibGzTopic where topicId=?", new Object[]{sid});
			  if(find2!=null && find2.size()>0){
				  for(TeeWeibGzTopic t:find2){
					  TeeWeibGuanZhuModel m=new TeeWeibGuanZhuModel();
					  m.setUserId(t.getUserId());
					  TeePerson person = teePersonDao.get(t.getUserId());
					  m.setUserName(person.getUserName());
					  m.setAvatar(TeeStringUtil.getInteger(person.getAvatar(),0));
					  if(model.size()<=3){
						  model.add(m);
					  }
				  }
			  }
		 }
		 easyjson.setRows(model);
		 easyjson.setTotal(count);
		return easyjson;
	}

	/**
	 * 获取当前登录人所有的关注人信息
	 * */
	public TeeEasyuiDataGridJson findGzPersonAll(HttpServletRequest request,
			TeeDataGridModel model) {
		TeeEasyuiDataGridJson easyJson=new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeePersonModel> modelList=new ArrayList<TeePersonModel>();
		String hql="from TeePerson where uuid in (select personId from TeeWeibGuanZhu where userId="+loginPerson.getUuid()+")";
		List<TeePerson> find = teePersonDao.find(hql, null);
		Long count = teePersonDao.count("select count(*) "+hql, null);
		if(find!=null && find.size()>0){
			for(TeePerson p:find){
				TeePersonModel m=new TeePersonModel();
				m.setUuid(p.getUuid());
				m.setUserName(p.getUserName());
				m.setDeptIdName(p.getDept().getDeptName());
				m.setUserRoleStrName(p.getUserRole().getRoleName());
				m.setAvatar(p.getAvatar());
				List<TeeWeibGuanZhu> find2 = teeWeibGuanZhuDao.find("from TeeWeibGuanZhu where userId=? and personId=?", new Object[]{p.getUuid(),loginPerson.getUuid()});
				if(find2!=null && find2.size()>0){
					m.setFans(true);//粉丝 （互粉）
				}else{
					m.setFans(false);
				}
				modelList.add(m);
			}
		}
		easyJson.setRows(modelList);
		easyJson.setTotal(count);
		return easyJson;
	}

	/**
	 * 查询当前登录人所有粉丝信息
	 * */
	public TeeEasyuiDataGridJson findFansAll(HttpServletRequest request,TeeDataGridModel model) {
		TeeEasyuiDataGridJson easyJson=new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeePersonModel> modelList=new ArrayList<TeePersonModel>();
		String hql="from TeePerson where uuid in (select userId from TeeWeibGuanZhu where personId="+loginPerson.getUuid()+")";
		List<TeePerson> find = teePersonDao.find(hql, null);
        Long count = teePersonDao.count("select count(*) "+hql, null);
		if(find!=null && find.size()>0){
			for(TeePerson p:find){
				TeePersonModel m=new TeePersonModel();
				m.setUuid(p.getUuid());
				m.setUserName(p.getUserName());
				m.setDeptIdName(p.getDept().getDeptName());
				m.setUserRoleStrName(p.getUserRole().getRoleName());
				m.setAvatar(p.getAvatar());
				List<TeeWeibGuanZhu> find2 = teeWeibGuanZhuDao.find("from TeeWeibGuanZhu where userId=? and personId=?", new Object[]{loginPerson.getUuid(),p.getUuid()});
				if(find2!=null && find2.size()>0){
					m.setFowllows(true);//）
				}else{
					m.setFowllows(false);
				}
				modelList.add(m);
			}
		}
		easyJson.setRows(modelList);
		easyJson.setTotal(count);
		return easyJson;
	}

	/**
	 * 删除粉丝
	 * */
	public TeeJson deleteFans(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        int userId = loginPerson.getUuid();//当前登录人（被关注人）
        String personId = request.getParameter("sid");//关注人
        int query = teeWeibGuanZhuDao.deleteOrUpdateByQuery("delete from TeeWeibGuanZhu where personId=? and userId=?", new Object[]{userId,Integer.parseInt(personId)});
        if(query>0){
        	json.setRtMsg("取消关注");
        	json.setRtState(true);
        }else{
        	json.setRtState(false);
        }
		return json;
	}
	
}
