package com.tianee.oa.core.base.news.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.news.bean.TeeNews;
import com.tianee.oa.core.base.news.bean.TeeNewsInfo;
import com.tianee.oa.core.base.news.dao.TeeNewsDao;
import com.tianee.oa.core.base.news.dao.TeeNewsInfoDao;
import com.tianee.oa.core.base.news.model.TeeNewsInfoModel;
import com.tianee.oa.core.base.news.model.TeeNewsModel;
import com.tianee.oa.core.base.notify.bean.TeeNotify;
import com.tianee.oa.core.base.notify.bean.TeeNotifyInfo;
import com.tianee.oa.core.base.notify.model.TeeNotifyInfoModel;
import com.tianee.oa.core.base.notify.model.TeeNotifyModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.core.general.dao.TeeSysParaDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.core.org.service.TeePersonManagerI;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.phoneSms.service.TeeSmsSendPhoneService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeModelIdConst;
import com.tianee.oa.subsys.weixin.msg.util.SMessage;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeManagerPostPersonDataPrivModel;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.service.TeeSimpleService;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUrlToFile;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.str.expReplace.TeeHTMLImgTag;
import com.tianee.webframe.util.thread.TeeRequestInfo;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;
/**
 * 新闻管理
 * @author zhp
 * @createTime 2013-12-26
 * @desc
 */
@Service
public class TeeNewsService extends TeeBaseService  {

	@Autowired
	private TeeNewsDao newsDao;
	
	@Autowired
	public  TeeNewsInfoDao newsInfoDao;
	
	@Autowired
	@Qualifier("teeBaseUpload")
	private TeeBaseUpload upload;
	
	@Autowired
	private TeeSmsManager smsManager;
	
	@Autowired
	private TeeSimpleService simpleService;
	
	@Autowired
	TeeAttachmentService attachmentService;
	
	@Autowired
	TeeBaseUpload baseUpload;
	
	@Autowired
	TeePersonManagerI personManagerI;
	
	
	@Autowired
	private TeeDeptDao deptDao;
	
	@Autowired
	private TeeUserRoleDao userRoleDao;
	
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeSmsSendPhoneService smsSendPhoneService;
	
	@Autowired
	TeeSysParaDao sysParaDao;
	
	/**
	 * 添加新闻
	 * @author zhp
	 * @createTime 2014-1-5
	 * @editTime 上午10:18:25
	 * @desc
	 */
	@TeeLoggingAnt(template="新建新闻 [{$1.subject}]",type="020A")
	public void addNewsService(TeeNews news){
		
		String content=news.getContent();
		List oldUrls = new ArrayList();
		List newUrls = new ArrayList();
		oldUrls = TeeUrlToFile.getImageSrc(content);
		if(oldUrls!=null && oldUrls.size()>0){
			for(int i=0;i<oldUrls.size();i++){
				InputStream inputStream =TeeUrlToFile.download((String)oldUrls.get(i));
				int lastIndex=((String)oldUrls.get(i)).lastIndexOf("/");
				String realName=((String)oldUrls.get(i)).substring(lastIndex,((String)oldUrls.get(i)).length());
				try {
					TeeAttachment attach = baseUpload.singleAttachUpload(inputStream, inputStream.available(), realName, "", TeeAttachmentModelKeys.imgupload, news.getProvider());
					String urls=TeeSysProps.getString("contextPath")+"/attachmentController/downFile.action?id="+attach.getSid();
					newUrls.add(urls);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String newContent = TeeUrlToFile.replaceImageUrl(oldUrls, content, newUrls);
		List<TeeHTMLImgTag> imgTags = TeeUrlToFile.getImageTagsFromHtml(newContent);
		if(imgTags.size()!=0){//获取第一个图片作为默认缩略图
			news.setThumbnail(imgTags.get(0).getAttributes().get("src"));
		}
		news.setContent(newContent);
		news.setAbstracts(news.getAbstracts());
		news.setShortContent(TeeUtility.cutHtml(newContent).replace("&nbsp;", ""));
		newsDao.save(news);
	}
	
	/**
	 * 更新新闻
	 * @author zhp
	 * @createTime 2014-2-25
	 * @editTime 上午12:57:10
	 * @desc
	 */
	@TeeLoggingAnt(template="更新新闻信息 [{$1.subject}]",type="020B")
	public void updateNewsService(TeeNews news){
		String content=news.getContent();
		List oldUrls = new ArrayList();
		List newUrls = new ArrayList();
		oldUrls = TeeUrlToFile.getImageSrc(content);
		if(oldUrls!=null && oldUrls.size()>0){
			for(int i=0;i<oldUrls.size();i++){
				InputStream inputStream =TeeUrlToFile.download((String)oldUrls.get(i));
				int lastIndex=((String)oldUrls.get(i)).lastIndexOf("/");
				String realName=((String)oldUrls.get(i)).substring(lastIndex+1,((String)oldUrls.get(i)).length());
				try {
					TeeAttachment attach = baseUpload.singleAttachUpload(inputStream, inputStream.available(), realName, "", TeeAttachmentModelKeys.imgupload, news.getProvider());
					String urls=TeeSysProps.getString("contextPath")+"/attachmentController/downFile.action?id="+attach.getSid();
					newUrls.add(urls);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String newContent = TeeUrlToFile.replaceImageUrl(oldUrls, content, newUrls);
		List<TeeHTMLImgTag> imgTags = TeeUrlToFile.getImageTagsFromHtml(newContent);
		if(imgTags.size()!=0){//获取第一个图片作为默认缩略图
			news.setThumbnail(imgTags.get(0).getAttributes().get("src"));
		}
		news.setContent(newContent);
		news.setAbstracts(news.getAbstracts());
		news.setShortContent(TeeUtility.cutHtml(newContent).replace("&nbsp;", ""));
		newsDao.update(news);
	}


	/**
	 * 获取未读新闻
	 * @author zhp
	 * @createTime 2014-1-5
	 * @editTime 上午11:07:06
	 * @desc 
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getReadNews(TeeDataGridModel dm,Map paraMap,int state,TeePerson person) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String typeId =TeeStringUtil.getString(paraMap.get("typeId"),"") ;
		String subject=TeeStringUtil.getString(paraMap.get("subject"),"") ;
		String content=TeeStringUtil.getString(paraMap.get("content"),"") ;
		String keywords=TeeStringUtil.getString(paraMap.get("keywords"),"") ;
		if(!"".equals(keywords)){
			subject = content = keywords;
		}
		j.setTotal(newsDao.getQueryPersonalNoReadCount(state, person,typeId,subject,content));// 设置总记录数
		List<TeeNewsModel> list = new ArrayList<TeeNewsModel>();
		List<TeeNews> newsList = newsDao.getPersonalNoRead(state, person, dm,typeId,subject,content);
		if (newsList != null && newsList.size() > 0) {
			for (TeeNews n : newsList) {
				TeeNewsModel m = parseModel(n, false ,person);
				m.setContent("");
				m.setAbstracts("");
				m.setShortContent("");
				list.add(m);
			}
		}
		j.setRows(list);// 设置返回的行
		return j;
	}
	
	
	/**
	 * 获取未读新闻
	 * @author zhp
	 * @createTime 2014-1-5
	 * @editTime 上午11:07:06
	 * @desc 
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getReadPicNews(TeeDataGridModel dm,Map paraMap,int state,TeePerson person) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String typeId =TeeStringUtil.getString(paraMap.get("typeId"),"") ;
		j.setTotal(newsDao.getPersonalNoReadCount(state, person,typeId));// 设置总记录数
		List<TeeNewsModel> list = new ArrayList<TeeNewsModel>();
		List<TeeNews> newsList = newsDao.getPersonalNoReadPic(state, person, dm,typeId);
		if (newsList != null && newsList.size() > 0) {
			for (TeeNews n : newsList) {
				TeeNewsModel m = parseModel(n, false ,person);
				m.setShortContent("");
				m.setContent("");
				list.add(m);
			}
		}
		
		
		j.setRows(list);// 设置返回的行
		return j;
	}
	
	
	/**
	 * 管理查询
	 * @param request
	 * @param dm
	 * @param paraMap
	 * @param loginPerson
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getManageNews(HttpServletRequest request ,TeeDataGridModel dm,Map paraMap , TeePerson loginPerson) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String subject =  (String)paraMap.get("subject");
		String content =  (String)paraMap.get("content");
		String typeId =  (String)paraMap.get("typeId");//新闻类型
		String state =  (String)paraMap.get("state");
		List termList = new ArrayList();
		String hql = "from TeeNews n where  1 = 1";
		List<TeeNewsModel> mList = new ArrayList<TeeNewsModel>();
		boolean isSuperAdmin = TeePersonService.checkIsSuperAdmin(loginPerson, "");
		
		Map map = new HashMap();
		map.put(TeeConst.LOGIN_USER, loginPerson);
		//数据管理权限
		TeeManagerPostPersonDataPrivModel dataPrivModel = personManagerI.getManagerPostPersonIdsPriv(map, TeeModelIdConst.NEWS_POST_PRIV, "0");
		if(dataPrivModel.getPrivType().equals("0")){//空
			termList.add(loginPerson.getUuid());
			hql = hql + " and n.provider.uuid = ?";//加上自己创建的
		}else if(dataPrivModel.getPrivType().equals("ALL")){//所有
			// hql = "from TeeNews n where  1 = 1";
		}else{
			List<Integer> pIdList = dataPrivModel.getPersonIds();//获取权限
			pIdList.add(loginPerson.getUuid());
			if(pIdList.size() > 0){
				String personIdsSql =  TeeDbUtility.IN("n.provider.uuid", pIdList);
				hql = hql + " and " + personIdsSql;
			}else{
				j.setTotal(0L);
				j.setRows(mList);// 设置返回的行
				return j;
			}
		}
		
		if(!TeeUtility.isNullorEmpty(subject)){
			termList.add("%" + subject + "%");
			hql = hql + " and n.subject like ?";
		}
		if(!TeeUtility.isNullorEmpty(content)){
			termList.add("%" + content + "%");
			hql = hql + " and n.content like ?";
		}
		
		if(!TeeUtility.isNullorEmpty(typeId)){
			termList.add(typeId);
			hql = hql + " and n.typeId = ?";
		}
		if(!TeeUtility.isNullorEmpty(state) && !"-1".equals(state)){
			
			if(state.equals("1")){//已发布
				hql = hql + " and n.publish = '1'";
			}else{
				hql = hql + " and n.publish != '1'";
			}
		}
		
		String totalHql = " select count(*) " + hql;
		j.setTotal(newsDao.countByList(totalHql ,termList));// 设置总记录数
		if (dm.getSort() != null) {// 设置排序
			if(dm.getSort().equals("provider1")){
				 dm.setSort("provider"); 
			}
			hql += " order by " + dm.getSort() + " " + dm.getOrder();
		}else{
			hql += " order by n.newsTime desc";
		}
		List<TeeNews> newses = newsDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(),termList);// 查询

		if (newses != null && newses.size() > 0) {
			for (TeeNews n : newses) {
				TeeNewsModel m = parseModel(n, false ,loginPerson);
				m.setContent("");
				m.setAbstracts("");
				m.setShortContent("");
				mList.add(m);
			}
		}
		j.setRows(mList);// 设置返回的行
		return j;
	}
	
	
	/**
	 * 转换模型
	 * @param n
	 * @return
	 */
	public TeeNewsModel parseModel(TeeNews n , boolean isSimple , TeePerson person){
		TeeNewsModel m = new TeeNewsModel();
		m.setProvider1(n.getProvider().getUserName());
		BeanUtils.copyProperties(n, m);
		String typeDesc = "";
		if(!TeeUtility.isNullorEmpty(m.getTypeId())){
			typeDesc = TeeSysCodeManager.getChildSysCodeNameCodeNo("NEWS_TYPE", m.getTypeId());
		}
		m.setTypeDesc(typeDesc);
		long count  = attachmentService.getAttachesCount(TeeAttachmentModelKeys.NEWS, String.valueOf(n.getSid()));
		m.setAttachmentCount(count);	
		
		List<TeeNewsInfo> infoList = n.getInfos();
		for (int i = 0; i < infoList.size(); i++) {
			TeeNewsInfo info = infoList.get(i);
			TeePerson p = info.getToUser();
			if(p != null && p.getUuid() == person.getUuid() && info.getIsRead() == 1){//当前用户已经阅读
				m.setReadType(1);
				break;
			}
		}
		
		int spanDay = TeeUtility.getDaySpan(n.getNewsTime() , new Date());//取得间隔数
		SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd");
		if(spanDay > 7){
			m.setNewsTimeStr(TeeUtility.getDateTimeStr(n.getNewsTime() ,format));
		}else if(spanDay < 1){
			m.setNewsTimeStr("今天");
		}else{
			m.setNewsTimeStr(spanDay + "天前");
		}
		
		
		 String toRolesIds = "";//发布部门的id串
		 String toRolesNames = "";//发布部门的名字
		 String toDeptIds = "";//发布部门的id串
		 String toDeptNames = "";//发布部门的id串
		 String toUserNames = "";//发布人的名字
		 String toUserIds = "";//发布人的id串
		 List<TeePerson> pList = n.getPostUser();
		 List<TeeDepartment> dList = n.getPostDept();
		 List<TeeUserRole> rList = n.getPostUserRole();
		 for (int i = 0; i < pList.size(); i++) {
			toUserIds = toUserIds +  (pList.get(i).getUuid() + ",");
			toUserNames = toUserNames + pList.get(i).getUserName() + ",";
		  }
		
		  for (int i = 0; i < dList.size(); i++) {
			toDeptIds = toDeptIds + dList.get(i).getUuid() + ",";
			toDeptNames=  toDeptNames + dList.get(i).getDeptName() + ",";
		  }
		
		 for (int i = 0; i < rList.size(); i++) {
			toRolesIds = toRolesIds + rList.get(i).getUuid() + ",";
			toRolesNames=  toRolesNames + rList.get(i).getRoleName() + ",";
		 }
		 m.setToDeptIds(toDeptIds);
		 m.setToDeptNames(toDeptNames);
		 m.setToUserIds(toUserIds);
		 m.setToUserNames(toUserNames);
		 m.setToRolesIds(toRolesIds);
		 m.setToRolesNames(toRolesNames);
		 
		return m;
	}
	/**
	 * 
	 * @author zhp
	 * @createTime 2014-1-16
	 * @editTime 上午10:49:04
	 * @desc
	 */
	public List<TeeNewsInfo> toNewsInfo(List<TeePerson> toUsers){
		List<TeeNewsInfo> infos = new ArrayList<TeeNewsInfo>();
		for(int i=0;i<toUsers.size();i++){
			TeeNewsInfo info = new TeeNewsInfo();
			TeePerson p =  (TeePerson)toUsers.get(i);
			info.setToUser(p);
			info.setIsRead(0);
			infos.add(info);
		}
		return infos;
	}
	
	/**
	 * 获取 用户 uuid 串
	 * @author zhp
	 * @createTime 2014-3-3
	 * @editTime 上午02:11:50
	 * @desc
	 */
	public String getToUsers(List<TeePerson> toUsers){
		String users = "";
		for(int i=0;i<toUsers.size();i++){
			TeePerson p =  (TeePerson)toUsers.get(i);
			users = users +p.getUuid()+",";
		}
		return users;
	}
	/**
	 * 获取新闻 ount 为条数 -1为全部 || state为 状态 0 未读 1已读 -1全部
	 * @author zhp
	 * @createTime 2014-2-13
	 * @editTime 下午02:45:54
	 * @desc
	 */
	@Transactional(readOnly = true)
	public List getAllNewsListByState(TeePerson person,int state,int count) {
		String hql = "from TeeNews n where  n.publish = '1'  and   exists (select 1 from n.infos info where info.toUser.uuid = "+person.getUuid()+"";
		if(state == -1){
			hql = hql + ")";
		}else{
			hql = hql + "and info.isRead = "+state+" )";
		}
		List<TeeNews> newses = null;
		if(count > 0 ){
			newses = newsDao.pageFind(hql,0,count,null);// 查询
		}else{
			newses = newsDao.executeQuery(hql, null);
		}
		List<TeeNewsModel> mList = new ArrayList<TeeNewsModel>();
		if (newses != null && newses.size() > 0) {
			for (TeeNews n : newses) {
				TeeNewsModel m = new TeeNewsModel();
				BeanUtils.copyProperties(n, m);
				mList.add(m);
			}
		}
		return mList;
	}
	
	/**
	 * 获取新闻 
	 * @author zhp
	 * @createTime 2014-2-17
	 * @editTime 下午09:37:08
	 * @desc
	 */
	public TeeNews getTeeNewsById(int sid){
		return (TeeNews)newsDao.loadSingleObject("from TeeNews n where n.sid = "+sid, null);
	}
	
	/**
	 * 更新 新闻阅读状态
	 * @author zhp
	 * @createTime 2014-2-17
	 * @editTime 下午09:41:12
	 * @desc
	 */
	public void updateNewsState(int sid,TeePerson person ){
		String hql = "update TeeNewsInfo info set info.isRead = 1   where   info.toUser.uuid = ?";
		newsDao.executeUpdate(hql, new Object[]{person.getUuid()} );
		
		
	}
	
	/**
	 * 检查是否存在阅读人员
	 * @author syl
	 * @date 2014-3-13
	 * @param person
	 * @param notify
	 * @return
	 */
	public boolean checkExistsInfo(TeePerson person , TeeNews news){
		Object[] values = {person.getUuid() , news.getSid()};
		String hql  = "select count(sid) from TeeNewsInfo  where toUser.uuid= ? and news.sid = ?";
		long count = newsDao.count(hql, values);
		if(count > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 增加点击次数
	 * @author syl
	 * @date 2014-3-4
	 * @param sid
	 * @param person
	 * @param clickCount
	 */
	public void addCount(int sid , int  clickCount){
		newsDao.addCount(  sid, clickCount);
	}
	
	/**
	 * 更新 新闻状态
	 * @author zhp
	 * @createTime 2014-2-18
	 * @editTime 上午01:20:20
	 * @desc
	 */
	public void updateNewsPublishState(int sid,int state){
		String hql = "update TeeNews n set n.publish = '"+state+"' where  n.sid = "+sid;
		newsDao.executeUpdate(hql, null);
	}
	
	
	/**
	 * 删除或更新
	 * @author syl
	 * @date 2014-3-4
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request){
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String toDeptIds = TeeStringUtil.getString(multipartRequest.getParameter("toDeptIds"),"");
		String toRolesIds = TeeStringUtil.getString(multipartRequest.getParameter("toRolesIds"),"");
		String toUserIds = TeeStringUtil.getString(multipartRequest.getParameter("toUserIds"),"");//  
		List<TeePerson> pList = new ArrayList<TeePerson>();
		
		TeeNews news = new TeeNews();
		String top = TeeStringUtil.getString(multipartRequest.getParameter("top"));
		//评论类型 ...
		String anonymityYn = TeeStringUtil.getString(multipartRequest.getParameter("anonymityYn"),"0");//0 实名 1 匿名 2 进制评论
		int allPriv = 0;
		/*获取发布范围人员*/
		if(TeeUtility.isNullorEmpty(toDeptIds) && TeeUtility.isNullorEmpty(toUserIds) && TeeUtility.isNullorEmpty(toRolesIds)){
			pList = personDao.getAllUserNoDelete();
//			List<TeeDepartment> listDept = deptDao.getAllDept();
//			news.setPostDept(listDept);
			allPriv = 1;
		}else{
			//根据部门的id  获取部门的dept_full_id
			String Ids="";
			String[]dIds=toDeptIds.split(",");	
			for (String str : dIds) {
				if(!TeeUtility.isNullorEmpty(str)){
					int deptId=Integer.parseInt(str);
					TeeDepartment d=deptDao.get(deptId);
					String fullId=d.getDeptFullId();
					List<TeeDepartment> departmentList=deptDao.getAllChildDept(fullId);
					//System.out.println("子部门的个数"+departmentList.size());
					String deptIds="";
					for (TeeDepartment dept : departmentList) {
						deptIds+=dept.getUuid()+",";
					}
					Ids+=deptIds;
					
				}
						
			}
			if(Ids.endsWith(",")){
				Ids=Ids.substring(0,Ids.lastIndexOf(","));
			}	
			//System.out.println(Ids+"=====");
			
			pList = personManagerI.getPersonByUuidsOrDeptIdsOrRoleIds(loginPerson, toUserIds, Ids, toRolesIds);
			if(!TeeUtility.isNullorEmpty(toDeptIds)){//发布权限  ---部门
				List<TeeDepartment> listDept = deptDao.getDeptListByUuids(toDeptIds);
				news.setPostDept(listDept);
			}
			if(!TeeUtility.isNullorEmpty(toUserIds)){//申发布权限- 人员
				List<TeePerson> listDept = personDao.getPersonByUuids(toUserIds);
				news.setPostUser(listDept);
			}
			
			if(!TeeUtility.isNullorEmpty(toRolesIds)){//fa发布权限 -- 角色
				List<TeeUserRole> listRole = userRoleDao.getPrivListByUuids(toRolesIds);
				news.setPostUserRole(listRole);
			}
		}
		
		
		
		String subject = TeeStringUtil.getString(request.getParameter("subject"),"");
		String content = TeeStringUtil.getString(request.getParameter("content"),"");
		String abstracts = TeeStringUtil.getString(request.getParameter("abstracts"),"");
		String typeId = TeeStringUtil.getString(request.getParameter("typeId"),"");
		String format = TeeStringUtil.getString(request.getParameter("format"),"");
		String url = TeeStringUtil.getString(request.getParameter("url"),"");
		Date newsTime = TeeStringUtil.getDate(request.getParameter("newsTime"),"yyyy-MM-dd HH:mm");
		
		String publish = TeeStringUtil.getString(multipartRequest.getParameter("publish"),"0");//发布状态
		List<TeeAttachment> attachments = null;
		TeeJson json = new TeeJson();
		try {
			attachments = upload.manyAttachUpload(multipartRequest, TeeAttachmentModelKeys.NEWS);
			int sid = TeeStringUtil.getInteger(multipartRequest.getParameter("sid"), 0);
			if(sid > 0){
				TeeNews OldNews = getTeeNewsById(sid);
				OldNews.setContent(content);
				OldNews.setAbstracts(abstracts);
				OldNews.setAllPriv(allPriv);
				OldNews.setUrl(url);
				OldNews.setFormat(format);
				for(TeeAttachment attach:attachments){
					attach.setModelId(String.valueOf(sid));
					simpleDaoSupport.update(attach);
				}
				//List<TeeNewsInfo> oldInfo = OldNews.getInfos();//获取之前的人员对象 
				//infos = reSetNewsInfo(OldNews, publish, toUsers);
				/*
				List<TeeNewsInfo> newInfos = new ArrayList<TeeNewsInfo>();//存入新的之前的人员对象 
			
				
				for (TeeNewsInfo inofNew : oldInfo) {
					if(toUsers.contains(inofNew.getToUser())){
						newInfos.add(inofNew);
						toUsers.remove(inofNew.getToUser());//删除
					}else{//不包含，已读的也加上
						if(inofNew.getIsRead() == 1){//已读的
							newInfos.add(inofNew);
						}else{
							//删除未读人员
							newsInfoDao.deleteByInfo(inofNew);
						}
					}
				}

				infos = toNewsInfo(toUsers);
				infos.addAll(newInfos);*/
				OldNews.setSubject(subject);
				//OldNews.setInfos(infos);
				OldNews.setPostDept(news.getPostDept());
				OldNews.setPostUser(news.getPostUser());
				OldNews.setPostUserRole(news.getPostUserRole());
				OldNews.setAnonymityYn(anonymityYn);
				OldNews.setTypeId(typeId);
				OldNews.setTop(top);//置顶
				if(newsTime == null){
					OldNews.setNewsTime(new Date());
				}else{
					OldNews.setNewsTime(newsTime);
				}
				news.setSid(OldNews.getSid());
				String oldPublish  = TeeStringUtil.getString(OldNews.getPublish() , "0");
				if(oldPublish.equals("0")){
					OldNews.setPublish(publish);
				}
				updateNewsService(OldNews);
				
			}else{
				//infos = toNewsInfo(toUsers);
				news.setProvider(loginPerson);
				news.setSubject(subject);
				news.setAllPriv(allPriv);
				if(newsTime == null){
					news.setNewsTime(new Date());
				}else{
					news.setNewsTime(newsTime);
				}
				news.setContent(content);
				news.setAbstracts(abstracts);
				news.setProvider(loginPerson);
				//news.setInfos(infos);
				news.setTypeId(typeId);
				news.setPublish(publish);
				news.setAnonymityYn(anonymityYn);
				news.setTop(top);//置顶
				news.setFormat(format);
				news.setUrl(url);
				addNewsService(news);
				//////////////////////////////////////添加短消息 zhp 20140303////////////////////////////////////////////////////
				
				for(TeeAttachment attach:attachments){
					attach.setModelId(String.valueOf(news.getSid()));
					simpleDaoSupport.update(attach);
				}
				
				json.setRtState(true);
				json.setRtMsg("添加新闻成功!");
			}
			
			
			String mailRemind = TeeStringUtil.getString(request.getParameter("mailRemind"));
			if(mailRemind.equals("1") && "1".equals(publish)){
				Map requestData = new HashMap();
				requestData.put("content", "请查看新闻："+subject);
				requestData.put("userList",pList);
				//requestData.put("sendTime", );
				requestData.put("moduleNo", "020");
				requestData.put("remindUrl", "/system/core/base/news/person/readNews.jsp?id="+news.getSid()+"&isLooked=1&sid="+news.getSid());
				requestData.put("remindUrl1", "/system/mobile/phone/news/newsInfo.jsp?id="+news.getSid()+"&isLooked=1&sid="+news.getSid());
				smsManager.sendSms(requestData, loginPerson);
			}
			
			String phoneSms = TeeStringUtil.getString(request.getParameter("phoneSms"));
			if(!"".equals(phoneSms) && "1".equals(publish)){//发送手机短信
				Map requestData = new HashMap();
				requestData.put("smsContent", "请查看新闻："+subject);
				requestData.put("hasRemindPrivPersonList",pList);
				requestData.put("sendTime","");
				requestData.put(TeeConst.LOGIN_USER,loginPerson);
				smsSendPhoneService.sendPhoneSms(requestData);
			}
			//发送到企业微信号
//			String wechat = TeeStringUtil.getString(request.getParameter("wechat"));
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//			String release_time = sdf.format(new Date());
//			String content_html = "<table width=\"100%\" align=\"center\">"
//            +"<tr><td  width=\"100%\"><center>发布部门：&nbsp;"+news.getProvider().getDept().getDeptName()
//            +"&nbsp;&nbsp;发布人：&nbsp;"+news.getProvider().getUserName()+"&nbsp;&nbsp;发布于：&nbsp;"+release_time+"</center></td></tr>"
//	        +"<tr><td  colspan=\"2\" valign=\"top\"><span>"+content+"</span></td></tr>"
//	        +"</table>";
//			TeeSysPara sysPara1 = sysParaDao.getSysPara("WE_CHAT_CORPID");
//			TeeSysPara sysPara2 = sysParaDao.getSysPara("WE_CHAT_SECRET");
//			if(sysPara1 !=null && sysPara2 != null){
//				if("1".equals(wechat)&&"1".equals(publish)){
//					String result = SMessage.releaseWechat(sysPara1.getParaValue(),sysPara2.getParaValue(),subject,content_html,"0");
//				}
//			}
			
			json.setRtState(true);
		} catch (Exception e) {
			//System.out.println(e.getStackTrace());
			json.setRtState(false);
			json.setRtMsg(e.getMessage());
			//json.setRtMsg("添加新闻失败!");
		}
		
		
		
		return json;
	}
	
	/**
	 * 重新设置人员新闻读取信息
	 * @param newsIs
	 * @return
	 */
	public List<TeeNewsInfo>  reSetNewsInfo(TeeNews news ,String publish , List<TeePerson> newPersonList){
		List<TeeNewsInfo> infos = null;
		List<TeeNewsInfo> oldInfo = news.getInfos();//获取之前的人员对象 
		List<TeeNewsInfo> newInfos = new ArrayList<TeeNewsInfo>();//存入新的之前的人员对象 
		if(publish.equals("1")){//发布
			//删除所有新闻
			newsInfoDao.delectByNewsId(news.getSid());
		}else{
			for (TeeNewsInfo inofNew : oldInfo) {
				if(newPersonList.contains(inofNew.getToUser())){
					newInfos.add(inofNew);
					newPersonList.remove(inofNew.getToUser());//删除
				}else{
					newsInfoDao.deleteByInfo(inofNew);//删除数据库
				}
				
			}
		}
		
		infos = toNewsInfo(newPersonList);
		infos.addAll(newInfos);
		return infos ;
	}
	/**
	 * 删除ById
	 * @author syl
	 * @date 2014-3-4
	 * @param id
	 * @return
	 */
	@TeeLoggingAnt(template="删除新闻 [{logModel.subject}]",type="020C")
	public TeeJson deleteByIdService(int id){
		TeeJson json = new TeeJson();
		TeeNews news = newsDao.getById(id);
		TeeRequestInfo requestInfo = TeeRequestInfoContext.getRequestInfo();
		
		if(news != null){
			requestInfo.getLogModel().put("subject", news.getSubject());
			List<TeeNewsInfo> infos = news.getInfos();
			for (int i = 0; i < infos.size(); i++) {
				TeeNewsInfo info = infos.get(i);
				newsInfoDao.deleteByInfo(info);
			}
			news.setInfos(null);
			newsDao.deleteById(id);
			json.setRtState(true);
			//删除回复
			simpleDaoSupport.executeUpdate("delete from TeeNewsComment where newsId="+id, null);
		}else{
			json.setRtState(false);
			json.setRtMsg("该新闻已被删除！");
     	}
		return json;
	}
	
	/**
	 * 查看新闻 阅读情况
	 * @author syl
	 * @date 2014-3-4
	 * @param id
	 * @return
	 */
	public TeeJson getNewsLookDatail(int id){
		TeeJson json = new TeeJson();
		List resultList = new ArrayList();
		TeeNews news  = newsDao.getById(id);
		List<TeeNewsInfo> infoList = (List<TeeNewsInfo>) simpleDaoSupport.filteredList(news.getInfos(), "order by this.isRead desc", null);
		if (infoList != null && infoList.size() > 0) {
			for (TeeNewsInfo n : infoList) {
				Map map = new HashMap();
				String deptName = "";
				String roleName = "";
				String stateDesc = "";
				if(n.getToUser().getDept() != null){
					deptName = n.getToUser().getDept().getDeptName();
				}
				if(n.getToUser().getUserRole() != null){
					roleName = n.getToUser().getUserRole().getRoleName();
				}
				map.put("deptName", deptName);
				map.put("roleName", roleName);
				map.put("userName", n.getToUser().getUserName());
				map.put("isRead", n.getIsRead());
				map.put("sid", n.getSid());
				
				if(n.getIsRead() == 1){
					stateDesc = "<span style='color:green'>已读</span>";
				}else{
					stateDesc = "<span style='color:red'>未读</span>";
				}
				map.put("stateDesc", stateDesc);
				resultList.add(map);
			}
		}
		json.setRtData(resultList);
		json.setRtState(true);
		return json;
	}
	
    /**
     * 查阅情况
     * @param request
     * @return
     */
	public TeeJson getNewsInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int sid = TeeStringUtil.getInteger(request.getParameter("id"),0);
		List<TeeNewsInfoModel> resultList = new ArrayList<TeeNewsInfoModel>();
		TeeNews news = newsDao.getById(sid);
		if(news == null){
			json.setRtMsg("改新闻已被删除");
			json.setRtState(false);
			return json;
		}
		List<TeePerson> pList = news.getPostUser();
		List<TeeDepartment> dList = news.getPostDept();
		List<TeeUserRole> rList = news.getPostUserRole();
		String uuids = "";//人员Ids
		String deptIds = "";//部门Ids
		String roleIds = "";//角色Ids
		if(pList != null && pList.size() > 0){
			for (int i = 0; i < pList.size(); i++) {
				uuids = uuids + pList.get(i).getUuid() + ",";
			}
		}
		
		
		String Ids="";
		if(dList != null && dList.size() > 0){
			for (int i = 0; i < dList.size(); i++) {
				deptIds = deptIds + dList.get(i).getUuid() + ",";
			}	
			String[]dIds=deptIds.split(",");	
			for (String str : dIds) {
				int deptId=Integer.parseInt(str);
				TeeDepartment d=deptDao.get(deptId);
				String fullId=d.getDeptFullId();
				List<TeeDepartment> departmentList=deptDao.getAllChildDept(fullId);
				//System.out.println("子部门的个数"+departmentList.size());
				String deptIdss="";
				for (TeeDepartment dept : departmentList) {
					deptIdss+=dept.getUuid()+",";
				}
				Ids+=deptIdss;		
			}
			if(Ids.endsWith(",")){
				Ids=Ids.substring(0,Ids.lastIndexOf(","));
			}		
		}
		
		
		if(rList != null && rList.size() > 0){
			for (int i = 0; i < rList.size(); i++) {
				roleIds = roleIds + rList.get(i).getUuid() + ",";
			}
		}
		List<TeePerson> personList = personManagerI.getPersonByUuidsOrDeptIdsOrRoleIds(loginPerson, uuids, Ids, roleIds);
		if(news.getAllPriv()==1){
			personList = personDao.getAllUser();
		}
		List<TeeNewsInfo> infoList = news.getInfos();
		Map map = new HashMap();
		if (infoList != null && infoList.size() > 0) {
			for (TeeNewsInfo n : infoList) {
				TeeNewsInfoModel m = new TeeNewsInfoModel();
				m.setRoleName(n.getToUser().getUserRole().getRoleName());
				m.setDeptName(n.getToUser().getDept().getDeptName());
				m.setIsRead(n.getIsRead());
				m.setSid(n.getSid());
				m.setUserName(n.getToUser().getUserName());
				if(n.getIsRead() == 0){
					m.setStateDesc("未读");
				}else{
					m.setStateDesc("已读");
				}
				if(n.getReadTime() != null){
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String temTime = sf.format(n.getReadTime());
					m.setReadTime1(temTime);
				}
				map.put(n.getToUser().getUuid(), m);
			}
		}
		
		for (int i = 0; i < personList.size(); i++) {
			TeeNewsInfoModel m = new TeeNewsInfoModel();
			TeePerson temp = personList.get(i);
			Object obj = map.get(temp.getUuid());
			if(obj != null){
				TeeNewsInfoModel infoM = (TeeNewsInfoModel)obj;
				resultList.add(infoM);
			}else{
				m.setRoleName(temp.getUserRole().getRoleName());
				m.setDeptName(temp.getDept().getDeptName());
				m.setIsRead(0);
				m.setSid(0);
				m.setUserName(temp.getUserName());
				m.setStateDesc("未读");
				resultList.add(m);
			}
		}
		json.setRtData(resultList);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 清空阅读数据
	 * @author syl
	 * @date 2014-3-4
	 * @param id
	 * @return
	 */
	public TeeJson clearReadInfo(int id){
		TeeJson json = new TeeJson();
		TeeNews news  = newsDao.getById(id);
		//List<TeeNewsInfo> infoList = news.getInfos();
		newsInfoDao.delectByNewsId(id);
	/*	if (infoList != null && infoList.size() > 0) {
			for (TeeNewsInfo n : infoList) {
				newsInfoDao.updateNewsInfo(n);
			}
		}*/
		json.setRtState(true);
		return json;
	}
	
	
    
	public List<TeeNewsModel> getNoReadNewsList(Map paraMap,int state,TeePerson person) {
		String typeId =TeeStringUtil.getString(paraMap.get("typeId"),"") ;
		List<TeeNewsModel> list = new ArrayList<TeeNewsModel>();
		List<TeeNews> newsList = newsDao.getNoReadNewsList(state, person,typeId);
		if (newsList != null && newsList.size() > 0) {
			for (TeeNews n : newsList) {
				TeeNewsModel m = parseModel(n, false ,person);
				m.setContent("");
				m.setAbstracts("");
				m.setShortContent("");
				list.add(m);
			}
		}
		return list;
	}
	
	
	public void addNewInfo(TeeNewsInfo info){
		newsInfoDao.addNewsInfo(info);
	}
	
	public TeeNewsDao getNewsDao() {
		return newsDao;
	}

	public void setNewsDao(TeeNewsDao newsDao) {
		this.newsDao = newsDao;
	}

	/**
	 * 阅读新闻     修改新闻的状态
	 * @param sid
	 * @param i
	 * @param loginUser
	 */
	public TeeJson readNews(int sid, int isLooked, TeePerson person) {
		TeeJson json = new TeeJson();
		if(sid > 0){
			TeeNews news = newsDao.getById(sid);
			if(isLooked != 1){			
				 boolean exists = checkExistsInfo(person, news);
				 if(!exists){//不存在
					TeeNewsInfo info = new TeeNewsInfo();
					info.setIsRead(1);
					info.setReadTime(new Date());
					info.setToUser(person);
					info.setNews(news);
					addNewInfo(info);
					news.getInfos().add(info);
					updateNewsService(news);
				 }
			 }
			 //TeeNewsModel m = parseNewsModelById(news , false);
			 json.setRtState(true);
			 //json.setRtData(m);
		}else{
			json.setRtState(false);
		    json.setRtMsg("无公告");
		}
		return json;
		
	}
	
	
	
	
}
