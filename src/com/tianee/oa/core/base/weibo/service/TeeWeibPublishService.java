package com.tianee.oa.core.base.weibo.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.weibo.bean.TeeWeibComment;
import com.tianee.oa.core.base.weibo.bean.TeeWeibConllect;
import com.tianee.oa.core.base.weibo.bean.TeeWeibDianZai;
import com.tianee.oa.core.base.weibo.bean.TeeWeibGuanZhu;
import com.tianee.oa.core.base.weibo.bean.TeeWeibPublish;
import com.tianee.oa.core.base.weibo.bean.TeeWeibReply;
import com.tianee.oa.core.base.weibo.bean.TeeWeibTopic;
import com.tianee.oa.core.base.weibo.dao.TeeWeibCommentDao;
import com.tianee.oa.core.base.weibo.dao.TeeWeibConllectDao;
import com.tianee.oa.core.base.weibo.dao.TeeWeibDianZaiDao;
import com.tianee.oa.core.base.weibo.dao.TeeWeibGuanZhuDao;
import com.tianee.oa.core.base.weibo.dao.TeeWeibPublishDao;
import com.tianee.oa.core.base.weibo.dao.TeeWeibRelayDao;
import com.tianee.oa.core.base.weibo.dao.TeeWeibReplyDao;
import com.tianee.oa.core.base.weibo.dao.TeeWeibTopicDao;
import com.tianee.oa.core.base.weibo.model.TeeWeibCommentModel;
import com.tianee.oa.core.base.weibo.model.TeeWeibDianZaiModel;
import com.tianee.oa.core.base.weibo.model.TeeWeibPublishModel;
import com.tianee.oa.core.base.weibo.model.TeeWeibRelayModel;
import com.tianee.oa.core.base.weibo.model.TeeWeibReplyModel;
import com.tianee.oa.core.base.weibo.model.TeeWeibTopicModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.model.TeeDepartmentModel;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeWeibPublishService extends TeeBaseService{

	@Autowired
	private TeeWeibPublishDao teeWeibPublishDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeWeibDianZaiDao teeWeibDianZaiDao;
	
	@Autowired
	private TeeWeibConllectDao teeWeibConllectDao;
	
	@Autowired
	private TeeWeibGuanZhuDao teeWeibGuanZhuDao;
	
	@Autowired
	private TeeWeibCommentDao teeWeibCommentDao;
	
	@Autowired
	private TeeWeibReplyDao teeWeibReplyDao;
	
	@Autowired
	private TeeWeibRelayDao teeWeibRelayDao;
	
	@Autowired
	private TeeDeptDao teeDeptDao;

	@Autowired
	private TeeWeibTopicDao teeWeibTopicDao;
	
	/**
	 * 发布微博信息
	 * */
	public TeeJson addPublish(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String content=request.getParameter("content");
		String img=request.getParameter("img");
		String imgy=request.getParameter("imgy");
		List<String> contentStr=workContent(content);
		TeeWeibPublish publish=new TeeWeibPublish();
		publish.setContent(contentStr.get(0));//发布内容
		Date date=new Date();
		publish.setCreateTime(date);//发布时间
		publish.setImg(img);//缩略图id字符串
		publish.setImgy(imgy);//原图id字符串
		publish.setUserId(loginPerson.getUuid());//发布人员id
		Serializable save = teeWeibPublishDao.save(publish);
	    int pId = TeeStringUtil.getInteger(save, 0);//新发布的信息id
	    String topicStr = contentStr.get(1);//新添加的话题id字符串
	    if(topicStr!=null && !"".equals(topicStr)){
	    	String[] split = topicStr.split(",");
	    	if(split!=null && split.length>0){
	    		for(String sp:split){
	    			TeeWeibTopic topic = teeWeibTopicDao.get(Integer.parseInt(sp));
	    			if(topic!=null){
	    				String infoStr = topic.getInfoStr();
	    				if(infoStr==null || "".equals(infoStr)){
	    					topic.setInfoStr(pId+",");
	    				}else{
	    					String infoStr2 = topic.getInfoStr();
	    					infoStr2+=pId+",";
	    					topic.setInfoStr(infoStr2);
	    				}
	    			}
	    			int count = topic.getCount();
	    			count+=1;
	    			topic.setCount(count);
	    			teeWeibTopicDao.update(topic);
	    		}
	    	}
	    }
		if(pId>0){
			json.setRtMsg("发布成功！！！");
			json.setRtState(true);
		}else{
			json.setRtMsg("发布失败！！！");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * 处理表情
	 * */
	public String workContent2(String content){
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
					contentStr+="<img alt='' style='width:22px;height:22px;' src='dist/arclist/"+indexof+".png'>";
				}
				content=content.substring(indexOf2+1,content.length());
			}
		}
		return contentStr;
	}
	/**
	 * 处理
	 * */
	public List<String> workContent(String content){
		String contentStr=content;
		//处理话题
		 List<String> listS=new ArrayList<String>();
		 String zfInfo="";
		 String topicIdStr="";
		 while(contentStr!=null && !"".equals(contentStr)){
			 String [] zfarr=contentStr.split("#");
			 if(zfarr.length>=2){
				 int index=contentStr.indexOf("#");
				 int indexD=contentStr.indexOf("#", index+1);
				 if(index>=0 && indexD>0){
					 zfInfo+=zfarr[0];
					 if(zfarr[1]!=null && !"".equals(zfarr[1])){
						 String str="<a href='#'>#"+zfarr[1]+"#</a>";
						 zfInfo+=str;
						 contentStr=contentStr.substring(indexD+1, contentStr.length());
						 List<TeeWeibTopic> find = teeWeibTopicDao.find("from TeeWeibTopic where topic ='"+zfarr[1]+"'", null);
						 if(find==null || find.size()==0){
							 TeeWeibTopic topic=new TeeWeibTopic();//话题实体类
							 topic.setTopic(zfarr[1]);//话题内容
							 Serializable save = teeWeibTopicDao.save(topic);
							 int topicId = TeeStringUtil.getInteger(save, 0);
							 topicIdStr+=topicId+",";
						 }else{
							 TeeWeibTopic topic = find.get(0);
							 topicIdStr+=topic.getSid()+",";
						 }
					 }else{
						 zfInfo+="#";
						 contentStr=contentStr.substring(index+1, contentStr.length());
					 }
				 }else{
					 zfInfo+=contentStr;
					 contentStr="";
				 }
			 }else{
				 zfInfo+=contentStr;
				 contentStr="";
			 }
		 }
		 String workContent2 = workContent2(zfInfo);
		 listS.add(workContent2);
		 listS.add(topicIdStr);
	    return listS;
	}
	/**
	 * 获取所有的微博信息（广场）
	 * */
	public TeeEasyuiDataGridJson findAll(TeeDataGridModel dm,HttpServletRequest request) {
		TeeEasyuiDataGridJson easyJson=new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int userId = loginPerson.getUuid();//当前登录人
		List<TeeWeibPublish> find = teeWeibPublishDao.find("from TeeWeibPublish order by sid desc", null);
		if(find!=null && find.size()>0){
			List<TeeWeibPublishModel> listModel=new ArrayList<TeeWeibPublishModel>();
			for(TeeWeibPublish p:find){
				TeeWeibPublishModel model=new TeeWeibPublishModel();
				model.setContent(p.getContent());
				model.setCount(p.getCount());
				model.setCreateTime(TeeStringUtil.getString(p.getCreateTime(), "MM-dd HH:mm"));
				model.setImg(p.getImg());
				model.setNum(p.getNum());
				model.setNumber(p.getNumber());
			    model.setSid(p.getSid());
			    model.setUserId(p.getUserId());
			    TeePerson teePerson = personDao.get(p.getUserId());
			    if(teePerson!=null){
			    	model.setUserName(teePerson.getUserName());
			    }
			    List<TeeWeibDianZai> find2 = teeWeibDianZaiDao.find("from TeeWeibDianZai where infoId=? and userId=?", new Object[]{p.getSid(),userId});
			    if(find2!=null && find2.size()>0){
			    	model.setDianzan(true);
			    }
			    List<TeeWeibConllect> find3 = teeWeibConllectDao.find("from TeeWeibConllect where infoId=? and userId=?", new Object[]{p.getSid(),userId});
			    if(find3!=null && find3.size()>0){
			    	model.setCollect(true);
			    }
			    listModel.add(model);
			}
			easyJson.setRows(listModel);
		}
		return easyJson;
	}

	/**
	 * 获取所有的微博信息（收藏）
	 * */
	public TeeEasyuiDataGridJson findCollectAll(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson easyJson=new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String meter = request.getParameter("meter");
		String page = request.getParameter("page");
		String rows2 = request.getParameter("rows");
		int deptId = Integer.parseInt(request.getParameter("deptId"));
		dm.setPage(Integer.parseInt(page));
		dm.setRows(Integer.parseInt(rows2));
		int userId = loginPerson.getUuid();//当前登录人
		String hql=hql="from TeeWeibPublish pub where 1=1 ";
	   if("1".equals(meter)){//收藏
		 //当前登录人收藏的微博信息
//			List<TeeWeibConllect> find4 = teeWeibConllectDao.find("from TeeWeibConllect where userId=?", new Object[]{userId});
//			String str="(";
//			if(find4!=null && find4.size()>0){
//				for(TeeWeibConllect c:find4){
//					str+=c.getInfoId()+",";
//				}
//				str=str.substring(0, str.length()-1);
//			}
//			str+=")";
//			if(!"()".equals(str)){
//				hql+=" and sid in "+str;
//			}else{
//				hql+=" and sid=0";
//			}
		   hql+=" and pub.sid in (select con.infoId from TeeWeibConllect con where con.userId="+userId+")";
		}else if("0".equals(meter)){//关注的人
//	         List<TeeWeibGuanZhu> find = teeWeibGuanZhuDao.find("from TeeWeibGuanZhu where userId=?", new Object[]{userId});//关注人数
//	         String str="(";
//	         if(find!=null && find.size()>0){
//	        	 for(TeeWeibGuanZhu gz:find){
//	        		 str+=gz.getPersonId()+",";
//	        	 }
//	         }
//	         str+=userId+")";
//	         hql+=" and userId in "+str;
			hql+=" and pub.userId in (select gz.personId from TeeWeibGuanZhu gz where gz.userId ="+userId+") or pub.userId ="+userId;
		}else if("3".equals(meter)){//我的部门
			int uuid = loginPerson.getDept().getUuid();//部门id
//			List<TeePerson> find = personDao.find("from TeePerson t where t.dept.uuid=?", new Object[]{uuid});
//			String str="(";
//			if(find!=null && find.size()>0){
//				for(TeePerson p:find){
//					str+=p.getUuid()+",";
//				}
//				str=str.substring(0, str.length()-1);
//			}
//			str+=")";
//			hql+=" and userId in "+str;
			hql+=" and pub.userId in (select p.uuid from TeePerson p where p.dept.uuid="+uuid+")";
		}else if("4".equals(meter) && deptId>0){
//			List<TeePerson> find = personDao.find("from TeePerson t where t.dept.uuid=?", new Object[]{deptId});
//			String str="(";
//			if(find!=null && find.size()>0){
//				for(TeePerson p:find){
//					str+=p.getUuid()+",";
//				}
//				str=str.substring(0, str.length()-1);
//			}
//			str+=")";
//			hql+=" and userId in "+str;
			hql+=" and pub.userId in (select p2.uuid from TeePerson p2 where p2.dept.uuid="+deptId+")";
		}else{
			
		}
		List<TeeWeibPublish> find = teeWeibPublishDao.pageFind(hql+" order by pub.sid desc", dm.getFirstResult(), dm.getRows(), null);
		Long count = teeWeibPublishDao.count("select count(*) "+hql, null);
		if(find!=null && find.size()>0){
			List<TeeWeibPublishModel> listModel=new ArrayList<TeeWeibPublishModel>();
			for(TeeWeibPublish p:find){
				TeeWeibPublishModel model=new TeeWeibPublishModel();
				model.setContent(p.getContent());
				model.setCount(p.getCount());
				String string = TeeStringUtil.getString(p.getCreateTime(), "yyyy-MM-dd");
				model.setCreateTime(string.substring(0, string.length()-2));
				model.setImg(p.getImg());
				model.setImgy(p.getImgy());
				model.setNum(p.getNum());
				model.setNumber(p.getNumber());
			    model.setSid(p.getSid());
			    model.setImg(p.getImg());
			    model.setUserId(p.getUserId());
			    //转发的内容
			    String str=findZf(p.getZfId());
			    if(!"".equals(str)){
			    	str=str.substring(0, str.length()-2);
			    }
//			    List<TeeWeibRelay> find8 = teeWeibRelayDao.find("from TeeWeibRelay where infoId=?", new Object[]{p.getSid()});
//			    String str="";
//			    if(find8!=null && find8.size()>0){
//			    	for(TeeWeibRelay rl:find8){
//			    		str+=rl.getRelayId()+"//";
//			    	}
//			    	str=str.substring(0, str.length()-2);
//			    }
			    model.setZfCotent(str);//转发内容
			    TeePerson teePerson = personDao.get(p.getUserId());
			    if(teePerson!=null){
			    	model.setUserName(teePerson.getUserName());
			    	String avatar = teePerson.getAvatar();
			    	model.setAvatar(TeeStringUtil.getInteger(avatar, 0));
			    }
			    List<TeeWeibDianZai> find2 = teeWeibDianZaiDao.find("from TeeWeibDianZai where infoId=? and userId=?", new Object[]{p.getSid(),userId});
			    if(find2!=null && find2.size()>0){
			    	model.setDianzan(true);
			    }
			    List<TeeWeibConllect> find3 = teeWeibConllectDao.find("from TeeWeibConllect where infoId=? and userId=?", new Object[]{p.getSid(),userId});
			    if(find3!=null && find3.size()>0){
			    	model.setCollect(true);
			    }
			    int sid = p.getSid();
			     // 查询微博信息的的全部评论
			    List<TeeWeibComment> find4 = teeWeibCommentDao.find("from TeeWeibComment where infoId=?", new Object[]{p.getSid()});
			    List<TeeWeibCommentModel> ctList = model.getCtList();
			    if(find4!=null && find4.size()>0){
			    	for(TeeWeibComment ct:find4){
			    		TeeWeibCommentModel cm=new TeeWeibCommentModel();
			    		cm.setContent(ct.getContent());//评论内容
			    		cm.setCreTime(TeeStringUtil.getString(ct.getCreTime(),"yyyy-MM-dd HH:ss"));//评论时间
			    		cm.setInfoId(ct.getInfoId());//微博信息id
			    		cm.setSid(ct.getSid());//评论id
			    		//评论点赞次数
			    		List<TeeWeibDianZai> find5 = teeWeibDianZaiDao.find("from TeeWeibDianZai where replyId=?", new Object[]{ct.getSid()});
			    		if(find5!=null && find5.size()>0){
			    			cm.setPlNum(find5.size());//评论点赞次数
			    		}
			    		//评断是否给评论点赞
			    		List<TeeWeibDianZai> find7 = teeWeibDianZaiDao.find("from TeeWeibDianZai where replyId=? and userId=?", new Object[]{ct.getSid(),userId});
			    		if(find7!=null && find7.size()>0){
			    			cm.setpDianzan(true);
			    		}
			    		//回复次数
			    		List<TeeWeibReplyModel> replyList=cm.getReplyList();
			    		List<TeeWeibReply> find6 = teeWeibReplyDao.find("from TeeWeibReply where plId=?", new Object[]{ct.getSid()});
			    		if(find6!=null && find6.size()>0){
			    			TeeWeibReply teeWeibReply = find6.get(0);
			    			TeePerson teePerson2 = personDao.get(teeWeibReply.getUserId());
			    			cm.setHfName(teePerson2.getUserName());//回复评论中的一个人的姓名
			    			cm.setHfNum(find6.size());//评论回复次数
			    			for(TeeWeibReply r:find6){
			    				TeeWeibReplyModel rm=new TeeWeibReplyModel();
			    				rm.setContent(r.getContent());
			    				rm.setCreTime(TeeStringUtil.getString(r.getCreTime(), "yyyy-MM-dd HH:mm"));
			    				//rm.setDianZanReply(dianZanReply);
			    				rm.setPlId(r.getPlId());
			    				rm.setSid(r.getSid());
			    				TeePerson teePerson3 = personDao.get(r.getUserId());
			    				rm.setUserId(r.getUserId());
			    				rm.setUserName(teePerson3.getUserName());
			    				rm.setPersonId(r.getPersonId());
			    				if(r.getPersonId()>0){
			    					TeePerson teePerson4 = personDao.get(r.getPersonId());
			    					rm.setPersonName(teePerson4.getUserName());
			    				}
			    				//评论回复点赞次数
					    		List<TeeWeibDianZai> findList = teeWeibDianZaiDao.find("from TeeWeibDianZai where huiFuId=?", new Object[]{r.getSid()});
					    		if(findList!=null && findList.size()>0){
					    			rm.setCountReply(findList.size());//评论回复点赞次数
					    		}
					    		//评断是否给评论回复点赞
					    		List<TeeWeibDianZai> findList2 = teeWeibDianZaiDao.find("from TeeWeibDianZai where huiFuId=? and userId=?", new Object[]{r.getSid(),userId});
					    		if(findList2!=null && findList2.size()>0){
					    			rm.setDianZanReply(true);
					    		}
			    				if(replyList.size()<2){
			    					replyList.add(rm);
			    				}
			    			}
			    			cm.setReplyList(replyList);
			    		}
			    		cm.setUserId(ct.getUserId());//评论人
			    		TeePerson person = personDao.get(ct.getUserId());
			    		if(person!=null){
			    			cm.setUserName(person.getUserName());
			    			String avatar = person.getAvatar();
			    			cm.setAvatar(TeeStringUtil.getInteger(avatar, 0));
			    		}
			    		ctList.add(cm);
			    	}
			    	
			    }
			    model.setCtList(ctList);
			    listModel.add(model);
			}
			int rows = dm.getRows();
			//Long pageS=count%rows>0 ? count/rows+1 :count/rows;
			easyJson.setTotal(count);
			easyJson.setRows(listModel);
		}
		return easyJson;
	}

	/**
	 * 查转发
	 * */
	public String findZf(int zfId){
		String str="";
		if(zfId>0){
			TeeWeibPublish publish = teeWeibPublishDao.get(zfId);
			if(publish!=null){
				str+=publish.getContent()+"//";
				str+=findZf(publish.getZfId());
			}
		}
		return str;
	}
	/**
	 * 获取所有部门
	 * */
	public TeeJson allDept(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeDepartment> list=new ArrayList<TeeDepartment>();
		List<TeeDepartment> find = teeDeptDao.find("from TeeDepartment", null);
		if(find!=null && find.size()>0){
			String html="";
         for(int i=0;i<find.size();i++){
        	 TeeDepartment d = find.get(i);
        	 TeeDepartment parent = d.getDeptParent();//上级部门
        	 List<TeeDepartment> children = d.getChildren();//下级部门
        	// boolean gzDept = gzDept(d.getUuid(),loginPerson.getUuid());
     		if(parent==null && children==null){
				html+="<li class='allDepartItem extend'>";
				html+="<span class='allDepartName mouse' onclick='query(4,"+d.getUuid()+")'>"+d.getDeptName()+"</span>";
//				if(gzDept){
//					html+="<span class='followAll' onclick='deleteguanzhuDept("+d.getUuid()+");'><span class='add'>-</span>已关注</span>";
//				}else{
//					html+="<span class='followAll' onclick='addguanzhuDept("+d.getUuid()+");'><span class='add'>+</span>关注</span>";
//				}
				//html+="<span class='followAll hide'>已关注</span>";
				html+="<ul class=''></ul></li>";
			}else if(parent==null && children!=null){
				html+="<li class='allDepartItem collapse'>";
				html+="<span class='allDepartName mouse' onclick='query(4,"+d.getUuid()+")'>"+d.getDeptName()+"</span>";
				/*if(gzDept){
					html+="<span class='followAll' onclick='deleteguanzhuDept("+d.getUuid()+");'><span class='add'>-</span>已关注</span>";
				}else{
					html+="<span class='followAll' onclick='addguanzhuDept("+d.getUuid()+");'><span class='add'>+</span>关注</span>";
				}*/
				//html+="<span class='followAll hide'>已关注</span>";
				html+="<ul class='subDepList'>";
				String html2=nextDept(children,loginPerson.getUuid());
				html+=html2;
				html+="</ul></li>";
			}
			}
         json.setRtData(html);
         json.setRtState(true);
		}
		
		return json;
	}
/**
 * 获取下一级部门
 * */
	public String nextDept(List<TeeDepartment> childDept,int userId){
		String str="";
		for(int i=0;i<childDept.size();i++){
			TeeDepartment department = childDept.get(i);
       	    //boolean gzDept = gzDept(department.getUuid(),userId);
			str+="<li class='subDepItem'>";
			str+="<span class='subMenuName mouse' onclick='query(4,"+department.getUuid()+")'>"+department.getDeptName()+"</span>";
//			if(gzDept){
//				str+="<span class='follow' onclick='deleteguanzhuDept("+department.getUuid()+");'><span class='add'>-</span>已关注</span>";
//			}else{
//				str+="<span class='follow' onclick='addguanzhuDept("+department.getUuid()+");'><span class='add'>+</span>关注</span>";
//			}
			//str+="<span class='follow hide'>已关注</span>";
			List<TeeDepartment> children = department.getChildren();
			if(children!=null && children.size()>0){
				str+="<ul class='subDepList'>";
				String html=nextDept(children,userId);
				str+=html+"</ul>";
			}
			str+="</li>";
		}
		return str;
	}
	
	/**
	 * 判断该部门中的所有人是否都被关注
	 * */
	public boolean gzDept(int deptId,int userId){
		List<TeePerson> find = personDao.find("from TeePerson where dept.uuid=?", new Object[]{deptId});
		if(find!=null && find.size()>0){
			for(TeePerson p:find){
				List<TeeWeibGuanZhu> find2 = teeWeibGuanZhuDao.find("from TeeWeibGuanZhu where userId=? and personId=?", new Object[]{userId,p.getUuid()});
				if((find2==null || find2.size()==0) && userId!=p.getUuid()){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 搜索部门
	 * */
	public TeeJson searchDept(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int userId = loginPerson.getUuid();
		String deptName = request.getParameter("deptName");
		List<TeeDepartmentModel> modelList=new ArrayList<TeeDepartmentModel>();
		List<TeeDepartment> find = teeDeptDao.find("from TeeDepartment where deptName like '%"+deptName+"%'", null);
		if(find!=null && find.size()>0){
			for(TeeDepartment d:find){
				TeeDepartmentModel model=new TeeDepartmentModel();
				model.setUuid(d.getUuid());
				model.setDeptName(d.getDeptName());
				boolean gzDept = gzDept(d.getUuid(),userId);
				model.setGzDept(gzDept);
				modelList.add(model);
			}
		}
		json.setRtData(modelList);
		json.setRtState(true);
		return json;
	}

	/**
	 * 根据微博信息id查找微博信息
	 * */
	public TeeJson findPublish(int sid) {
		TeeJson json=new TeeJson();
		if(sid>0){
			TeeWeibPublish publish = teeWeibPublishDao.get(sid);
			if(publish!=null){
				json.setRtState(true);
				json.setRtData(publish.getContent());
			}
		}
		return json;
	}

	/**
	 * 查询关于此换题的所有的微博信息
	 * */
	public TeeEasyuiDataGridJson findTopicAll(HttpServletRequest request,TeeDataGridModel dm) {
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson easyjson=new TeeEasyuiDataGridJson();
		String content=request.getParameter("content");
		String page = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		dm.setPage(Integer.parseInt(page));
		dm.setRows(Integer.parseInt(pageSize));
		String hql="from TeeWeibTopic where topic = '"+content+"'";
		List<TeeWeibTopic> find = teeWeibTopicDao.find(hql, null);//话题信息
		List<TeeWeibPublishModel> model=new ArrayList<TeeWeibPublishModel>();
		List<TeeWeibPublish> find2=null;
		Long count=0L;
		if(find!=null && find.size()>0){
			TeeWeibTopic topic = find.get(0);
			int getrCount = topic.getrCount();//阅读次数
			getrCount++;
			topic.setrCount(getrCount);
			teeWeibTopicDao.update(topic);//修改阅读次数
			String infoStr = topic.getInfoStr();//微博信息id字符串
			if(infoStr!=null && !"".equals(infoStr)){
				infoStr=infoStr.substring(0, infoStr.length()-1);
				infoStr="("+infoStr+")";
				find2= teeWeibPublishDao.pageFind("from TeeWeibPublish where sid in "+infoStr+" order by sid desc",dm.getFirstResult(),dm.getRows(),null);
				count = teeWeibPublishDao.count("select count(*) from TeeWeibPublish where sid in "+infoStr, null);
				if(find2!=null && find2.size()>0){
					for(TeeWeibPublish p:find2){
						TeeWeibPublishModel m=new TeeWeibPublishModel();
						m.setSid(p.getSid());
						m.setContent(p.getContent());
						m.setUserId(p.getUserId());
						m.setImg(p.getImg());
						m.setImgy(p.getImgy());
						m.setCount(p.getCount());
						m.setNum(p.getNum());
						m.setNumber(p.getNumber());
						TeePerson person = personDao.get(p.getUserId());
						m.setUserName(person.getUserName());
						m.setAvatar(TeeStringUtil.getInteger(person.getAvatar(), 0));
						String string = TeeStringUtil.getString(p.getCreateTime(),"yyyy-MM-dd");
						m.setCreateTime(string.substring(0, string.length()-2));
						//判断当前登录人是否点赞此微博
						List<TeeWeibDianZai> find3 = teeWeibDianZaiDao.find("from TeeWeibDianZai where infoId=? and userId=?", new Object[]{p.getSid(),loginPerson.getUuid()});
						if(find3!=null && find3.size()>0){
							m.setDianzan(true);
						}else{
							m.setDianzan(false);
						}
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
	 * 获取所有的微博信息（收藏）
	 * */
	public TeeEasyuiDataGridJson findCollectAll2(HttpServletRequest request) {
		TeeEasyuiDataGridJson easyJson=new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String meter = request.getParameter("meter");
		int deptId = Integer.parseInt(request.getParameter("deptId"));
		int userId = loginPerson.getUuid();//当前登录人
		String hql="from TeeWeibPublish where 1=1 ";
		if("1".equals(meter)){//收藏
			 //当前登录人收藏的微博信息
			   hql+=" and pub.sid in (select con.infoId from TeeWeibConllect con where con.userId="+userId+")";
			}else if("0".equals(meter)){//关注的人
				hql+=" and pub.userId in (select gz.personId from TeeWeibGuanZhu gz where gz.userId ="+userId+") or pub.userId ="+userId;
			}else if("3".equals(meter)){//我的部门
				int uuid = loginPerson.getDept().getUuid();//部门id
				hql+=" and pub.userId in (select p.uuid from TeePerson p where p.dept.uuid="+uuid+")";
			}else if("4".equals(meter) && deptId>0){
				hql+=" and pub.userId in (select p2.uuid from TeePerson p2 where p2.dept.uuid="+deptId+")";
			}else{
				
			}
		hql+=" order by sid desc";
		List<TeeWeibPublish> find = teeWeibPublishDao.find(hql, null);
		Long count = teeWeibPublishDao.count("select count(*) "+hql, null);
		if(find!=null && find.size()>0){
			List<TeeWeibPublishModel> listModel=new ArrayList<TeeWeibPublishModel>();
			for(TeeWeibPublish p:find){
				TeeWeibPublishModel model=new TeeWeibPublishModel();
				model.setContent(p.getContent());
				model.setCount(p.getCount());
				String string = TeeStringUtil.getString(p.getCreateTime(), "yyyy-MM-dd");
				model.setCreateTime(string.substring(0, string.length()-2));
				model.setImg(p.getImg());
				model.setImgy(p.getImgy());
				model.setNum(p.getNum());
				model.setNumber(p.getNumber());
			    model.setSid(p.getSid());
			    model.setImg(p.getImg());
			    model.setUserId(p.getUserId());
			    //转发的内容
			    String str=findZf(p.getZfId());
			    if(!"".equals(str)){
			    	str=str.substring(0, str.length()-2);
			    }
			    model.setZfCotent(str);//转发内容
			    TeePerson teePerson = personDao.get(p.getUserId());
			    if(teePerson!=null){
			    	model.setUserName(teePerson.getUserName());
			    	String avatar = teePerson.getAvatar();
			    	model.setAvatar(TeeStringUtil.getInteger(avatar, 0));
			    }
			    List<TeeWeibDianZai> find2 = teeWeibDianZaiDao.find("from TeeWeibDianZai where infoId=? and userId=?", new Object[]{p.getSid(),userId});
			    if(find2!=null && find2.size()>0){
			    	model.setDianzan(true);
			    }
			    List<TeeWeibConllect> find3 = teeWeibConllectDao.find("from TeeWeibConllect where infoId=? and userId=?", new Object[]{p.getSid(),userId});
			    if(find3!=null && find3.size()>0){
			    	model.setCollect(true);
			    }
			    int sid = p.getSid();
			     // 查询微博信息的的全部评论
			    List<TeeWeibComment> find4 = teeWeibCommentDao.find("from TeeWeibComment where infoId=?", new Object[]{p.getSid()});
			    List<TeeWeibCommentModel> ctList = model.getCtList();
			    if(find4!=null && find4.size()>0){
			    	for(TeeWeibComment ct:find4){
			    		TeeWeibCommentModel cm=new TeeWeibCommentModel();
			    		cm.setContent(ct.getContent());//评论内容
			    		cm.setCreTime(TeeStringUtil.getString(ct.getCreTime(),"yyyy-MM-dd HH:ss"));//评论时间
			    		cm.setInfoId(ct.getInfoId());//微博信息id
			    		cm.setSid(ct.getSid());//评论id
			    		//评论点赞次数
			    		List<TeeWeibDianZai> find5 = teeWeibDianZaiDao.find("from TeeWeibDianZai where replyId=?", new Object[]{ct.getSid()});
			    		if(find5!=null && find5.size()>0){
			    			cm.setPlNum(find5.size());//评论点赞次数
			    		}
			    		//评断是否给评论点赞
			    		List<TeeWeibDianZai> find7 = teeWeibDianZaiDao.find("from TeeWeibDianZai where replyId=? and userId=?", new Object[]{ct.getSid(),userId});
			    		if(find7!=null && find7.size()>0){
			    			cm.setpDianzan(true);
			    		}
			    		//回复次数
			    		List<TeeWeibReplyModel> replyList=cm.getReplyList();
			    		List<TeeWeibReply> find6 = teeWeibReplyDao.find("from TeeWeibReply where plId=?", new Object[]{ct.getSid()});
			    		if(find6!=null && find6.size()>0){
			    			TeeWeibReply teeWeibReply = find6.get(0);
			    			TeePerson teePerson2 = personDao.get(teeWeibReply.getUserId());
			    			cm.setHfName(teePerson2.getUserName());//回复评论中的一个人的姓名
			    			cm.setHfNum(find6.size());//评论回复次数
			    			for(TeeWeibReply r:find6){
			    				TeeWeibReplyModel rm=new TeeWeibReplyModel();
			    				rm.setContent(r.getContent());
			    				rm.setCreTime(TeeStringUtil.getString(r.getCreTime(), "yyyy-MM-dd HH:mm"));
			    				//rm.setDianZanReply(dianZanReply);
			    				rm.setPlId(r.getPlId());
			    				rm.setSid(r.getSid());
			    				TeePerson teePerson3 = personDao.get(r.getUserId());
			    				rm.setUserId(r.getUserId());
			    				rm.setUserName(teePerson3.getUserName());
			    				rm.setPersonId(r.getPersonId());
			    				if(r.getPersonId()>0){
			    					TeePerson teePerson4 = personDao.get(r.getPersonId());
			    					rm.setPersonName(teePerson4.getUserName());
			    				}
			    				//评论回复点赞次数
					    		List<TeeWeibDianZai> findList = teeWeibDianZaiDao.find("from TeeWeibDianZai where huiFuId=?", new Object[]{r.getSid()});
					    		if(findList!=null && findList.size()>0){
					    			rm.setCountReply(findList.size());//评论回复点赞次数
					    		}
					    		//评断是否给评论回复点赞
					    		List<TeeWeibDianZai> findList2 = teeWeibDianZaiDao.find("from TeeWeibDianZai where huiFuId=? and userId=?", new Object[]{r.getSid(),userId});
					    		if(findList2!=null && findList2.size()>0){
					    			rm.setDianZanReply(true);
					    		}
			    				if(replyList.size()<2){
			    					replyList.add(rm);
			    				}
			    			}
			    			cm.setReplyList(replyList);
			    		}
			    		cm.setUserId(ct.getUserId());//评论人
			    		TeePerson person = personDao.get(ct.getUserId());
			    		if(person!=null){
			    			cm.setUserName(person.getUserName());
			    			String avatar = person.getAvatar();
			    			cm.setAvatar(TeeStringUtil.getInteger(avatar, 0));
			    		}
			    		ctList.add(cm);
			    	}
			    	
			    }
			    model.setCtList(ctList);
			    listModel.add(model);
			}
			easyJson.setTotal(count);
			easyJson.setRows(listModel);
		}
		return easyJson;
	}
	
	/**
	 * 添加话题阅读的次数
	 * */
	public TeeJson readTopicCount(String content) {
		TeeJson json=new TeeJson();
		int count=0;
		String hql="from TeeWeibTopic where topic = '"+content+"'";
		List<TeeWeibTopic> find = teeWeibTopicDao.find(hql, null);//话题信息
		if(find!=null && find.size()>0){
			TeeWeibTopic topic = find.get(0);
			count = topic.getrCount();
		}
		json.setRtData(count);
		return json;
	}

	/**
	 * 删除关于此话题发布的微博信息
	 * */
	public TeeJson deletePublish(String content, int sid) {
		TeeJson json=new TeeJson();
		String hql="from TeeWeibTopic where topic = '"+content+"'";
		List<TeeWeibTopic> find = teeWeibTopicDao.find(hql, null);//话题信息
		if(find!=null && find.size()>0){
			String str2="";
			TeeWeibTopic topic = find.get(0);
			String str = topic.getInfoStr();
		    String[] split = str.split(",");
		    if(split!=null){
		    	for(int i=0;i<split.length;i++){
		    		int pSid=Integer.parseInt(split[i]);
		    		if(sid!=pSid){
		    			str2+=pSid+",";
		    		}
		    	}
		    }
			topic.setInfoStr(str2);
			int count = topic.getCount();//参与讨论的次数
			count-=1;
			topic.setCount(count);
		    teeWeibTopicDao.update(topic);
			teeWeibPublishDao.delete(sid);
			json.setRtMsg("删除成功");
			json.setRtState(true);
		}else{
			json.setRtMsg("删除失败");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * 查询所有的话题
	 * */
	public TeeEasyuiDataGridJson findTopicPage(TeeDataGridModel dm) {
		TeeEasyuiDataGridJson easyJson=new TeeEasyuiDataGridJson();
		dm.setRows(7);
		List<TeeWeibTopicModel> model=new ArrayList<TeeWeibTopicModel>();
		String hql="from TeeWeibTopic t order by t.count desc";
		List<TeeWeibTopic> find = teeWeibTopicDao.pageFind(hql, dm.getFirstResult(), dm.getRows(), null);
		if(find!=null && find.size()>0){
			for(TeeWeibTopic t:find){
				TeeWeibTopicModel m=new TeeWeibTopicModel();
//				String str = t.getInfoStr();
//				String[] split = str.split(",");
//				if(split!=null && split.length>0){
//					m.setpCount(split.length);
//				}else{
//					m.setpCount(0);
//				}
				m.setpCount(t.getCount());
				m.setSid(t.getSid());
				m.setTopicContent(t.getTopic());
				model.add(m);
			}
		}
		easyJson.setRows(model);
		return easyJson;
	}

	/**
	 * 查询话题有多少页
	 * */
	public TeeJson pageCount() {
		TeeJson json=new TeeJson();
		List<TeeWeibTopic> find = teeWeibTopicDao.find("from TeeWeibTopic", null);
		int s=1;
		if(find!=null && find.size()>0){
			int size = find.size();
			s=size%7>0 ? size/7+1 :size/7;
		}
		json.setRtData(s);
		return json;
	}

	/**
	 * 根据微博id删除微博信息
	 * */
	public TeeJson deletePublishById(int sid) {
		TeeJson json=new TeeJson();
		if(sid>0){
			TeeWeibPublish publish = teeWeibPublishDao.get(sid);
			if(publish!=null){
				int zfId = publish.getZfId();
				TeeWeibPublish publish2 = teeWeibPublishDao.get(zfId);
				if(publish2!=null){
					int number = publish2.getNumber();
					number-=1;
					publish2.setNumber(number);
					teeWeibPublishDao.update(publish2);//修改转发次数
				}
			}
			teeWeibPublishDao.delete(sid);//删除微博信息
			List<TeeWeibTopic> find = teeWeibTopicDao.find("from TeeWeibTopic", null);
			List<Integer> list=null;
			if(find!=null && find.size()>0){
				for(TeeWeibTopic t:find){
				   String str = t.getInfoStr();
				   if(str!=null && !"".equals(str)){
					   String str2="";
					   String[] split = str.split(",");
					   for(int i=0;i<split.length;i++){
						  int psid= Integer.parseInt(split[i]);
						   if(psid!=sid){
							   str2+=psid+",";
						   }
					   }
					   t.setInfoStr(str2);
					   teeWeibTopicDao.update(t);
				   }
				}
			}
			json.setRtState(true);
			json.setRtMsg("删除成功");
		}else{
			json.setRtState(false);
			json.setRtMsg("删除失败");
		}
		return json;
	}

	/**
	 * 根据微博id查询微博的具体信息
	 * */
	public TeeJson findWeibInfo(HttpServletRequest request,int sid) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeWeibPublishModel model=new TeeWeibPublishModel();
		if(sid>0){
			TeeWeibPublish publish = teeWeibPublishDao.get(sid);
			if(publish!=null){
				model.setSid(publish.getSid());
				model.setAvatar(TeeStringUtil.getInteger(loginPerson.getAvatar(),0));//头像
				List<TeeWeibConllect> find = teeWeibConllectDao.find("from TeeWeibConllect where infoId=? and userId=?", new Object[]{sid,loginPerson.getUuid()});
				if(find!=null && find.size()>0){
					model.setCollect(true);//已经收藏
				}else{
					model.setCollect(false);
				}
				model.setContent(publish.getContent());//微博内容
				model.setCount(publish.getCount());//点赞次数
				model.setCreateTime(TeeStringUtil.getString(publish.getCreateTime(), "yyyy-MM-dd HH:mm"));
				List<TeeWeibDianZai> find2 = teeWeibDianZaiDao.find("from TeeWeibDianZai where infoId=? and userId=?", new Object[]{sid,loginPerson.getUuid()});
				if(find2!=null && find2.size()>0){
					model.setDianzan(true);//已经点赞
				}else{
					model.setDianzan(false);
				}
				model.setImg(publish.getImg());
				model.setImgy(publish.getImgy());
				model.setNum(publish.getNum());//评论次数
				model.setNumber(publish.getNumber());//转发次数
				model.setUserId(publish.getUserId());
				TeePerson person = personDao.get(publish.getUserId());
				if(person!=null){
					model.setUserName(person.getUserName());
				}
				TeeWeibPublish publish2 = teeWeibPublishDao.get(publish.getZfId());
				if(publish2!=null){
					model.setZfCotent(publish2.getContent());
				}
			
				//评论
			    List<TeeWeibComment> find4 = teeWeibCommentDao.find("from TeeWeibComment where infoId=?", new Object[]{sid});
			    List<TeeWeibCommentModel> ctList = model.getCtList();
			    if(find4!=null && find4.size()>0){
			    	for(TeeWeibComment ct:find4){
			    		TeeWeibCommentModel cm=new TeeWeibCommentModel();
			    		cm.setContent(ct.getContent());//评论内容
			    		cm.setCreTime(TeeStringUtil.getString(ct.getCreTime(),"yyyy-MM-dd HH:ss"));//评论时间
			    		cm.setInfoId(ct.getInfoId());//微博信息id
			    		cm.setSid(ct.getSid());//评论id
			    		//评论点赞次数
			    		List<TeeWeibDianZai> find5 = teeWeibDianZaiDao.find("from TeeWeibDianZai where replyId=?", new Object[]{ct.getSid()});
			    		if(find5!=null && find5.size()>0){
			    			cm.setPlNum(find5.size());//评论点赞次数
			    		}
			    		//评断是否给评论点赞
			    		List<TeeWeibDianZai> find7 = teeWeibDianZaiDao.find("from TeeWeibDianZai where replyId=? and userId=?", new Object[]{ct.getSid(),loginPerson.getUuid()});
			    		if(find7!=null && find7.size()>0){
			    			cm.setpDianzan(true);
			    		}
			    		//回复次数
			    		List<TeeWeibReplyModel> replyList=cm.getReplyList();
			    		List<TeeWeibReply> find6 = teeWeibReplyDao.find("from TeeWeibReply where plId=?", new Object[]{ct.getSid()});
			    		if(find6!=null && find6.size()>0){
			    			TeeWeibReply teeWeibReply = find6.get(0);
			    			TeePerson teePerson2 = personDao.get(teeWeibReply.getUserId());
			    			cm.setHfName(teePerson2.getUserName());//回复评论中的一个人的姓名
			    			cm.setHfNum(find6.size());//评论回复次数
			    			for(TeeWeibReply r:find6){
			    				TeeWeibReplyModel rm=new TeeWeibReplyModel();
			    				rm.setContent(r.getContent());
			    				rm.setCreTime(TeeStringUtil.getString(r.getCreTime(), "yyyy-MM-dd HH:mm"));
			    				//rm.setDianZanReply(dianZanReply);
			    				rm.setPlId(r.getPlId());
			    				rm.setSid(r.getSid());
			    				TeePerson teePerson3 = personDao.get(r.getUserId());
			    				rm.setUserId(r.getUserId());
			    				rm.setUserName(teePerson3.getUserName());
			    				rm.setPersonId(r.getPersonId());
			    				if(r.getPersonId()>0){
			    					TeePerson teePerson4 = personDao.get(r.getPersonId());
			    					rm.setPersonName(teePerson4.getUserName());
			    				}
			    				//评论回复点赞次数
					    		List<TeeWeibDianZai> findList = teeWeibDianZaiDao.find("from TeeWeibDianZai where huiFuId=?", new Object[]{r.getSid()});
					    		if(findList!=null && findList.size()>0){
					    			rm.setCountReply(findList.size());//评论回复点赞次数
					    		}
					    		//评断是否给评论回复点赞
					    		List<TeeWeibDianZai> findList2 = teeWeibDianZaiDao.find("from TeeWeibDianZai where huiFuId=? and userId=?", new Object[]{r.getSid(),loginPerson.getUuid()});
					    		if(findList2!=null && findList2.size()>0){
					    			rm.setDianZanReply(true);
					    		}
			    				if(replyList.size()<2){
			    					replyList.add(rm);
			    				}
			    			}
			    			cm.setReplyList(replyList);
			    		}
			    		cm.setUserId(ct.getUserId());//评论人
			    		TeePerson person2 = personDao.get(ct.getUserId());
			    		if(person2!=null){
			    			cm.setUserName(person2.getUserName());
			    			String avatar = person2.getAvatar();
			    			cm.setAvatar(TeeStringUtil.getInteger(avatar, 0));
			    		}
			    		ctList.add(cm);
			    	}
			    	
			    }
			    model.setCtList(ctList);
				//转发
				List<TeeWeibRelayModel> rlList = model.getRlList();
				List<TeeWeibPublish> find3 = teeWeibPublishDao.find("from TeeWeibPublish where zfId=?", new Object[]{sid});
				if(find3!=null && find3.size()>0){
					for(TeeWeibPublish p:find3){
						TeeWeibRelayModel m=new TeeWeibRelayModel();
						m.setCreTime(TeeStringUtil.getString(p.getCreateTime(), "yyyy-MM-dd HH:mm"));
						m.setUserId(p.getUserId());
						TeePerson teePerson = personDao.get(p.getUserId());
						if(teePerson!=null){
							m.setUserName(teePerson.getUserName());
							m.setAvatar(TeeStringUtil.getInteger(teePerson.getAvatar(),0));
						}
						rlList.add(m);
					}
				}
				model.setRlList(rlList);
				//点赞
				List<TeeWeibDianZaiModel> dzList = model.getDzList();
				List<TeeWeibDianZai> find5 = teeWeibDianZaiDao.find("from TeeWeibDianZai where infoId=?", new Object[]{sid});
				if(find5!=null && find5.size()>0){
					for(TeeWeibDianZai dz:find5){
						TeeWeibDianZaiModel dm=new TeeWeibDianZaiModel();
						dm.setCreTime(TeeStringUtil.getString(dz.getCreTime(), "yyyy-MM-dd HH:mm"));
						dm.setUserId(dz.getUserId());
						TeePerson teePerson = personDao.get(dz.getUserId());
						if(teePerson!=null){
							dm.setUserName(teePerson.getUserName());
							dm.setAvatar(TeeStringUtil.getInteger(teePerson.getAvatar(),0));
						}
						dzList.add(dm);
					}
				}
				model.setDzList(dzList);
			}
			
		}
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 查询关于此换题的所有的微博信息
	 * */
	public TeeEasyuiDataGridJson findTopicAll2(HttpServletRequest request) {
		TeeEasyuiDataGridJson easyjson=new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String content=request.getParameter("content");
		String hql="from TeeWeibTopic where topic = '"+content+"'";
		List<TeeWeibTopic> find = teeWeibTopicDao.find(hql, null);//话题信息
		List<TeeWeibPublishModel> model=new ArrayList<TeeWeibPublishModel>();
		List<TeeWeibPublish> find2=null;
		Long count=0L;
		if(find!=null && find.size()>0){
			TeeWeibTopic topic = find.get(0);
			int getrCount = topic.getrCount();//阅读次数
			getrCount++;
			topic.setrCount(getrCount);
			teeWeibTopicDao.update(topic);//修改阅读次数
			String infoStr = topic.getInfoStr();//微博信息id字符串
			if(infoStr!=null && !"".equals(infoStr)){
				infoStr=infoStr.substring(0, infoStr.length()-1);
				infoStr="("+infoStr+")";
				find2= teeWeibPublishDao.find("from TeeWeibPublish where sid in "+infoStr+" order by sid desc",null);
				count = teeWeibPublishDao.count("select count(*) from TeeWeibPublish where sid in "+infoStr+" order by sid desc", null);
				if(find2!=null && find2.size()>0){
					for(TeeWeibPublish p:find2){
						TeeWeibPublishModel m=new TeeWeibPublishModel();
						m.setSid(p.getSid());
						m.setContent(p.getContent());
						m.setUserId(p.getUserId());
						m.setImg(p.getImg());
						m.setImgy(p.getImgy());
						TeePerson person = personDao.get(p.getUserId());
						m.setUserName(person.getUserName());
						m.setCount(p.getCount());
						m.setNum(p.getNum());
						m.setNumber(p.getNumber());
						m.setAvatar(TeeStringUtil.getInteger(person.getAvatar(), 0));
						String string = TeeStringUtil.getString(p.getCreateTime(),"yyyy-MM-dd");
						m.setCreateTime(string.substring(0, string.length()-2));
						//判断当前登录人是否点赞此微博
						List<TeeWeibDianZai> find3 = teeWeibDianZaiDao.find("from TeeWeibDianZai where infoId=? and userId=?", new Object[]{p.getSid(),loginPerson.getUuid()});
						if(find3!=null && find3.size()>0){
							m.setDianzan(true);
						}else{
							m.setDianzan(false);
						}
					    model.add(m);
					}
				}
			}
		}
		easyjson.setRows(model);
		easyjson.setTotal(count);
		return easyjson;
	}

	
}



