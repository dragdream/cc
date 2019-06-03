package com.tianee.oa.subsys.cms.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.oa.subsys.cms.bean.DocumentInfo;
import com.tianee.oa.subsys.cms.dao.ChannelInfoDao;
import com.tianee.oa.subsys.cms.model.DocumentInfoModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUrlToFile;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfo;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class CmsDocumentService extends TeeBaseService{
	
	@Autowired
	private TeeAttachmentDao attachmentDao;
	@Autowired
	private ChannelInfoDao channelInfoDao;
	@Autowired
	private CmsSiteService siteService;
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeSmsManager smsManager; 
	
	@Autowired
	TeeBaseUpload baseUpload;

	public void setAttachmentDao(TeeAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	public TeeAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}
	
	/**
	 * 添加文档
	 * @param documentInfoModel
	 * @return
	 */
	public DocumentInfo addDocument(DocumentInfoModel documentInfoModel,TeePerson loginUser){
		TeeRequestInfo requestInfo = TeeRequestInfoContext.getRequestInfo();
		
		//保存文档实体
		DocumentInfo info = new DocumentInfo();
		BeanUtils.copyProperties(documentInfoModel, info);
		info.setWriteTime(TeeDateUtil.parseCalendar(documentInfoModel.getWriteTimeDesc()));
		info.setCrTime(Calendar.getInstance());
		//info.setStatus(1);//新稿
		info.setCrUserId(requestInfo.getUserSid());
		info.setCrUserName(requestInfo.getUserId());
		//处理部门和初始化阅读量
		int crUserId = requestInfo.getUserSid();
		List<TeePerson> personList = personService.getPersonByUuids(crUserId+"");
		TeePerson crPerson = personList.get(0);
		TeeDepartment dept = crPerson.getDept();
		info.setDeptId(dept.getUuid());
		info.setDeptName(dept.getDeptName());
		info.setReadTime(0);
		
		//判断是否需要审核
		int chnlId=info.getChnlId();
		ChannelInfo channel=(ChannelInfo) simpleDaoSupport.get(ChannelInfo.class,chnlId);
		if(channel!=null){
		  if(channel.getCheckUserId()!=0 && channel.getCheckPub()==1){//有审核人员   并且需要审批
			info.setStatus(2);//审核中
			
			//发送消息
			Map requestData= new HashMap();
	    	requestData.put("content", "您有一个"+channel.getChnlName()+"的文章需要审批，文章名称为："+info.getDocTitle()+"，请及时办理");
	    	requestData.put("userListIds", channel.getCheckUserId());
	    	requestData.put("moduleNo", "091");
	    	requestData.put("remindUrl","/system/subsys/cms/docs.jsp?siteId="+channel.getSiteId()+"&channelId="+channel.getSid());
	    	smsManager.sendSms(requestData, loginUser);
			
			
		  }else{
		  	info.setStatus(3);//待发
		  }
		}
		
		
		
		int thumbnail = 0;//缩略图
		String content=info.getHtmlContent();
		List oldUrls = new ArrayList();
		List newUrls = new ArrayList();
		oldUrls = TeeUrlToFile.getImageSrc(content);
		if(oldUrls!=null && oldUrls.size()>0){
			for(int i=0;i<oldUrls.size();i++){
				InputStream inputStream =TeeUrlToFile.download((String)oldUrls.get(i));
				int lastIndex=((String)oldUrls.get(i)).lastIndexOf("/");
				String realName=((String)oldUrls.get(i)).substring(lastIndex+1,((String)oldUrls.get(i)).length());
				try {
					TeeAttachment attach = baseUpload.singleAttachUpload(inputStream, inputStream.available(), realName, "", TeeAttachmentModelKeys.imgupload, null);
					String urls=TeeSysProps.getString("contextPath")+"/attachmentController/downFile.action?id="+attach.getSid();
					newUrls.add(urls);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String newContent = TeeUrlToFile.replaceImageUrl(oldUrls, content, newUrls);
		info.setHtmlContent(newContent);
		
		
		//提取第一张图片作为缩略图
		if(thumbnail==0){
			String urls=TeeSysProps.getString("contextPath")+"/attachmentController/downFile.action?id=";
			Pattern p = Pattern.compile("<img[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+\\b)[^>]*>", Pattern.CASE_INSENSITIVE);  
		    Matcher m = p.matcher(newContent);  
		    String quote = null;  
		    String src = null;  
		    while (m.find()) {  
		        quote = m.group(1);  //判断是否有双引号和单引号
		        src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);  
		        thumbnail = TeeStringUtil.getInteger(src.replace(urls, ""), 0);
		        break;
		    } 
		}
		info.setThumbnail(thumbnail);
		info.setTop(documentInfoModel.getTop());
		//获取最大sortNo
		long max = getDocMaxSortNo(info.getChnlId());
		info.setSortNo(Integer.parseInt(String.valueOf(max+1)));
		//电子文档内容
		info.setDocAttachmentId(TeeStringUtil.getInteger(documentInfoModel.getDocAttachmentId(), 0));
		simpleDaoSupport.save(info);
		
		//业务处理
		//chnlDoc.getSid();
//	    ChannelInfo channelInfo=channelInfoDao.getChannelInfoById(info.getChnlId());
//	    String[] channelInfos=channelInfo.getPath().split("/");
//	    String str1="/";
//	    for (String str : channelInfos) {
//			int i=str.indexOf(".");
//			if(i==-1){continue;}
//			str=str.substring(0, i);//数字sid
//			int id=Integer.parseInt(str);	
//			channelInfo = channelInfoDao.getChannelInfoById(id);
//			
//				str1=str1+channelInfo.getFolder()+"/";
//			
//		}
	    
//	    SiteInfoModel siteInfoModel = siteService.getSiteInfo(channelInfo.getSiteId());
	    
//	    info.setPath(str1+info.getSid()+"."+siteInfoModel.getPubFileExt());
	    
//	    simpleDaoSupport.save(chnlDoc);
		return info;
	}
	
	/**
	 * 获取指定栏目下文档的最大排序号
	 * @param channelId
	 * @return
	 */
	public long getDocMaxSortNo(int channelId){
		return simpleDaoSupport.count("select max(sortNo) from DocumentInfo cd where chnlId="+channelId, null);
	}
	
	/**
	 * 更新文档
	 * @param documentInfoModel
	 */
	public void updateDocument(DocumentInfoModel documentInfoModel,TeePerson loginUser){
		//获取之前的数据
		DocumentInfo documentInfo = getDocumentByDocId(documentInfoModel.getDocId());
		//documentInfo.setStatus(4);//已发
		documentInfo.setDocTitle(documentInfoModel.getDocTitle());
		documentInfo.setAbstracts(documentInfoModel.getAbstracts());
		documentInfo.setHtmlContent(documentInfoModel.getHtmlContent());
		documentInfo.setContent(documentInfoModel.getContent());
		documentInfo.setMainTitle(documentInfoModel.getMainTitle());
		documentInfo.setSubTitle(documentInfoModel.getSubTitle());
		documentInfo.setKeyWords(documentInfoModel.getKeyWords());
		documentInfo.setSource(documentInfoModel.getSource());
		documentInfo.setAuthor(documentInfoModel.getAuthor());
		documentInfo.setCategory(documentInfoModel.getCategory());
		documentInfo.setWriteTime(TeeDateUtil.parseCalendar(documentInfoModel.getWriteTimeDesc()));
		documentInfo.setDocAttachmentId(TeeStringUtil.getInteger(documentInfoModel.getDocAttachmentId(), 0));
		
		//判断是否需要审核
		int chnlId=documentInfo.getChnlId();
		ChannelInfo channel=(ChannelInfo) simpleDaoSupport.get(ChannelInfo.class,chnlId);
		if(channel!=null){
		  if(channel.getCheckUserId()!=0 && channel.getCheckPub()==1){//有审核人员   并且需要审批
			  documentInfo.setStatus(2);//审核中
			  
			//发送消息
			Map requestData= new HashMap();
		    requestData.put("content", "您有一个"+channel.getChnlName()+"的文章需要审批，文章名称为："+documentInfo.getDocTitle()+"，请及时办理");
		    requestData.put("userListIds", channel.getCheckUserId());
		    requestData.put("moduleNo", "091");
		    requestData.put("remindUrl","/system/subsys/cms/docs.jsp?siteId="+channel.getSiteId()+"&channelId="+channel.getSid());
		    smsManager.sendSms(requestData, loginUser);
			  
		  }else{
			  documentInfo.setStatus(3);//待发
		  }
		}
				
		
		//获取对应chnlDoc
		documentInfo.setTop(documentInfoModel.getTop());
		
		
		int thumbnail = documentInfoModel.getThumbnail();//缩略图
		String content=documentInfo.getHtmlContent();
		List oldUrls = new ArrayList();
		List newUrls = new ArrayList();
		oldUrls = TeeUrlToFile.getImageSrc(content);
		if(oldUrls!=null && oldUrls.size()>0){
			for(int i=0;i<oldUrls.size();i++){
				InputStream inputStream =TeeUrlToFile.download((String)oldUrls.get(i));
				int lastIndex=((String)oldUrls.get(i)).lastIndexOf("/");
				String realName=((String)oldUrls.get(i)).substring(lastIndex+1,((String)oldUrls.get(i)).length());
				try {
					TeeAttachment attach = baseUpload.singleAttachUpload(inputStream, inputStream.available(), realName, "", TeeAttachmentModelKeys.imgupload, null);
					String urls=TeeSysProps.getString("contextPath")+"/attachmentController/downFile.action?id="+attach.getSid();
					newUrls.add(urls);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String newContent = TeeUrlToFile.replaceImageUrl(oldUrls, content, newUrls);
		
		//提取第一张图片作为缩略图
		if(thumbnail==0){
			String urls=TeeSysProps.getString("contextPath")+"/attachmentController/downFile.action?id=";
			Pattern p = Pattern.compile("<img[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+\\b)[^>]*>", Pattern.CASE_INSENSITIVE);  
		    Matcher m = p.matcher(newContent);  
		    String quote = null;  
		    String src = null;  
		    while (m.find()) {  
		        quote = m.group(1);  //判断是否有双引号和单引号
		        src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);  
		        thumbnail = TeeStringUtil.getInteger(src.replace(urls, ""), 0);
		        break;
		    } 
		}
		documentInfo.setThumbnail(thumbnail);
		
		documentInfo.setHtmlContent(newContent);
		
		simpleDaoSupport.update(documentInfo);
		
	}
	
	public DocumentInfo getDocumentByDocId(int docId){
		DocumentInfo documentInfo = (DocumentInfo) simpleDaoSupport.get(DocumentInfo.class, docId);
		return documentInfo;
	}
	
	public Map getDocumentMapByDocId(int docId){
		String hql = "select "
				+ "di.sid as docId,"
				+ "di.docTitle as docTitle,"
				+ "di.crTime as crTime,"
				+ "di.writeTime as writeTime,"
				+ "di.crUserName as crUserName,"
				+ "di.chnlId as chnlId,"
				+ "di.mainTitle as mainTitle,"
				+ "di.subTitle as subTitle,"
				+ "di.keyWords as keyWords,"
				+ "di.abstracts as abstracts,"
				+ "di.source as source,"
				+ "di.thumbnail as thumbnail,"
				+ "di.author as author,"
				+ "di.htmlContent as htmlContent,"
				+ "di.content as content,"
				+ "di.deptName as deptName,"
				+ "di.readTime as readTime,"
				+ "di.docAttachmentId as docAttachmentId"
				+ " from DocumentInfo di where di.sid="+docId;
		
		return simpleDaoSupport.getMap(hql, null);
		
	}
	
	/**
	 * 通过栏目lds获取文档信息
	 * @param chnlDocId
	 * @return
	 */
	public List<Map> getDocumentByChnlIds(String ids,int top,String orderBy,String category){
		String newids = "";
		String sp[] = ids.split(",");
		for(String tmp:sp){
			newids+="'"+tmp+"',";
		}
		newids = newids.substring(0,newids.length()-1);
		
		String hql = "select "
				+ "di.sid as docId,"
				+ "di.docTitle as docTitle,"
				+ "di.crTime as crTime,"
				+ "di.writeTime as writeTime,"
				+ "di.crUserName as crUserName,"
				+ "di.chnlId as chnlId,"
				+ "di.mainTitle as mainTitle,"
				+ "di.subTitle as subTitle,"
				+ "di.keyWords as keyWords,"
				+ "di.abstracts as abstracts,"
				+ "di.source as source,"
				+ "di.thumbnail as thumbnail,"
				+ "di.author as author,"
				+ "di.htmlContent as htmlContent,"
				+ "di.content as content,"
				+ "di.deptName as deptName,"
				+ "di.readTime as readTime,"
				+ "di.docAttachmentId as docAttachmentId"
				+ " from DocumentInfo di,ChannelInfo ci where ci.sid=di.chnlId and di.delFlag=0 and ci.chnlIdentity in ("+newids+") and di.status in (1,3,4) ";
		
		if(!TeeUtility.isNullorEmpty(category)){
			hql += " and bitand(di.category,"+category+")="+category;
		}
		
		if(!TeeUtility.isNullorEmpty(orderBy)){
			hql+=" order by "+orderBy;
		}else{
			hql+=" order by di.sid desc ";
		}
		
		List<Map> data = simpleDaoSupport.getMaps(hql, null,0,top);
		
		
		return data;
	}
	
	/**
	 * 通过站点获取文档信息
	 * @param chnlDocId
	 * @return
	 */
	public List<DocumentInfo> getDocumentBySiteSimple(int siteId){
		return simpleDaoSupport.find("select new DocumentInfo(sid,docTitle) from DocumentInfo where delFlag=0 and  siteId="+siteId, null);
	}
	
	/**
	 * 通过栏目获取文档信息
	 * @param chnlDocId
	 * @return
	 */
	public List<DocumentInfo> getDocumentByChnlSimple(int chnlId){
		return simpleDaoSupport.find("select new DocumentInfo(sid,docTitle) from DocumentInfo where delFlag=0 and chnlId="+chnlId, null);
	}
	
	/**
	 * 获取可发布的文档
	 * @param channelId
	 * @return
	 */
	public List<Map> getPublishableDocumentsByChnlId(int channelId,int from,int to,String orderBy,String category){
		String hql = "select "
				+ "di.sid as docId,"
				+ "di.docTitle as docTitle,"
				+ "di.crTime as crTime,"
				+ "di.writeTime as writeTime,"
				+ "di.crUserName as crUserName,"
				+ "di.chnlId as chnlId,"
				+ "di.mainTitle as mainTitle,"
				+ "di.subTitle as subTitle,"
				+ "di.keyWords as keyWords,"
				+ "di.abstracts as abstracts,"
				+ "di.source as source,"
				+ "di.thumbnail as thumbnail,"
				+ "di.author as author,"
				+ "di.htmlContent as htmlContent,"
				+ "di.content as content,"
				+ "di.deptName as deptName,"
				+ "di.readTime as readTime,"
				+ "di.docAttachmentId as docAttachmentId"
				+ " from DocumentInfo di where di.delFlag=0 and di.status in (1,3,4) and di.chnlId="+channelId+" ";
		
		if(!TeeUtility.isNullorEmpty(category)){
			hql += " and bitand(di.category,"+category+")="+category;
		}
		
		if(!TeeUtility.isNullorEmpty(orderBy)){
			hql += " order by "+orderBy;
		}else{
			hql += " order by di.sid desc";
		}
		
		List<Map> data = simpleDaoSupport.getMaps(hql, null,from,to);
		
		if(data!=null){
			
		}
		
		return data;
	}
	
	
	/**
	 * 获取可发布的文档的数量
	 * @param channelId
	 * @return
	 */
	public long getPublishableDocumentsCountByChnlId(int channelId){
		long count = simpleDaoSupport.count((
				"select count(di.sid)"
				+ " from DocumentInfo di where di.delFlag=0 and di.status in (3,4) and di.chnlId="+channelId+" order by di.sortNo asc"), new Object[]{});
		
		return count;
	}
	
	public void entityToModel(DocumentInfo documentInfo,DocumentInfoModel documentInfoModel){
		BeanUtils.copyProperties(documentInfo, documentInfoModel);
	}
	
	public void moveToTrash(DocumentInfoModel documentInfoModel){
		simpleDaoSupport.executeUpdate("update DocumentInfo di set di.delFlag=1 where di.sid=?", new Object[]{documentInfoModel.getDocId()});
	}
	
	public void clearTrash(int channelId){
		simpleDaoSupport.executeUpdate("delete from DocumentInfo di where di.chnlId=? and di.delFlag=1", new Object[]{channelId});
	}
	
	public TeeJson recycle(HttpServletRequest request){
		TeeJson json=new TeeJson();
		int docId=TeeStringUtil.getInteger(request.getParameter("docId"),0);
		if(docId>0){
			simpleDaoSupport.executeUpdate("update DocumentInfo di set di.delFlag=0 where di.sid=?", new Object[]{docId});
			json.setRtState(true);
			json.setRtMsg("还原成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("该文档不存在！");
		}
        return json;
	}
	
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,Map requestData){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		int channelId = TeeStringUtil.getInteger(requestData.get("channelId"), 0);
		String hqlHeader = "select "
				+ "doc.chnlId as channelId,"
				+ "doc.sid as docId,"
				+ "doc.docTitle as docTitle,"
				+ "doc.crTime as crTime,"
				+ "doc.top as top,"
				+ "doc.crUserId as crUserId,"
				+ "doc.crUserName as crUserName,"
				+ "chnl.chnlName as chnlName,"
				+ "doc.status as status ";
		
		String countHeader = "select count(doc.sid) ";
		
		String hql = "from DocumentInfo doc,ChannelInfo chnl where doc.chnlId=chnl.sid and doc.chnlId="+channelId+" and doc.delFlag=0 order by doc.top desc,doc.crTime desc";
		
		List<Map> list = simpleDaoSupport.getMaps(hqlHeader+hql, null,dm.getRows()*(dm.getPage() - 1), dm.getRows());
		long total = simpleDaoSupport.count(countHeader+hql, null);
		dataGridJson.setTotal(total);
		dataGridJson.setRows(list);
		
		return dataGridJson;
	}
	
	public TeeEasyuiDataGridJson datagridTrash(TeeDataGridModel dm,Map requestData){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		int channelId = TeeStringUtil.getInteger(requestData.get("channelId"), 0);
		String hqlHeader = "select "
				+ "doc.chnlId as channelId,"
				+ "doc.sid as docId,"
				+ "doc.docTitle as docTitle,"
				+ "doc.crTime as crTime,"
				+ "doc.crUserName as crUserName,"
				+ "chnl.chnlName as chnlName,"
				+ "doc.status as status ";
		
		String countHeader = "select count(doc.sid) ";
		
		String hql = "from DocumentInfo doc,ChannelInfo chnl where doc.chnlId=chnl.sid and doc.chnlId="+channelId+" and doc.delFlag=1 ";
		
		List<Map> list = simpleDaoSupport.getMaps(hqlHeader+hql+"order by doc.top desc,doc.sid desc", null);
		long total = simpleDaoSupport.count(countHeader+hql, null);
		dataGridJson.setTotal(total);
		dataGridJson.setRows(list);
		
		return dataGridJson;
	}
	
	
	/**
	 * 获取待审批的文档
	 * @param dm
	 * @param requestData
	 * @return
	 */
	public TeeEasyuiDataGridJson getCheckDocs(TeeDataGridModel dm,
			Map requestData,HttpServletRequest request) {
		//获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		int channelId = TeeStringUtil.getInteger(requestData.get("channelId"), 0);
		String hqlHeader = "select "
				+ "doc.chnlId as channelId,"
				+ "doc.sid as docId,"
				+ "doc.docTitle as docTitle,"
				+ "doc.crTime as crTime,"
				+ "doc.top as top,"
				+ "doc.crUserName as crUserName,"
				+ "chnl.chnlName as chnlName,"
				+ "doc.status as status ";
		
		String countHeader = "select count(doc.sid) ";
		
		String hql = "from DocumentInfo doc,ChannelInfo chnl where doc.chnlId=chnl.sid and doc.chnlId="+channelId+" and doc.delFlag=0  and   doc.status=2 and chnl.checkUserId="+loginUser.getUuid()+"  order by doc.top desc,doc.crTime desc";
		
		List<Map> list = simpleDaoSupport.getMaps(hqlHeader+hql, null,dm.getRows()*(dm.getPage() - 1), dm.getRows());
		long total = simpleDaoSupport.count(countHeader+hql, null);
		dataGridJson.setTotal(total);
		dataGridJson.setRows(list);
		
		return dataGridJson;
	}
	
	
	/**
	 * 审核文档
	 * @param request
	 * @return
	 */
	public TeeJson approveDoc(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		//获取文档的主键
		int docId=TeeStringUtil.getInteger(request.getParameter("docId"),0);
		//获取审核状态
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
		
		
		DocumentInfo docInfo=(DocumentInfo) simpleDaoSupport.get(DocumentInfo.class,docId);
		
		if(docInfo!=null){
			docInfo.setStatus(status);
			json.setRtState(true);
			json.setRtMsg("已审核！");
	
			
			ChannelInfo channel=(ChannelInfo) simpleDaoSupport.get(ChannelInfo.class,docInfo.getChnlId());
			if(channel!=null){
				String mess="";
				if(status==3){
					mess="通过";
				}else if(status==5){
					mess="退回";
				}
				Map requestData1 = new HashMap();
		    	requestData1.put("content", "您有一个"+channel.getChnlName()+"的文章“"+docInfo.getDocTitle()+"”已被"+loginUser.getUserName()+"审核"+mess+"。");
		    	requestData1.put("userListIds", docInfo.getCrUserId());
		    	requestData1.put("moduleNo", "091");
		    	requestData1.put("remindUrl","/system/subsys/cms/documents.jsp?siteId="+channel.getSiteId()+"&channelId="+channel.getSid());
		    	smsManager.sendSms(requestData1, loginUser);
			}
			
			
			
			
		}else{
			json.setRtState(false);
			json.setRtMsg("文档不存在！");
		}
		return json;
	}
	
	public void deleteDocumentByDocId(int docId){
		simpleDaoSupport.executeUpdate("delete from DocumentInfo where sid="+docId, null);
	}

	/**
	 * 累积阅读量
	 * @param request
	 * @return
	 */
	public void readCount(HttpServletRequest request) {
		int docId=TeeStringUtil.getInteger(request.getParameter("id"),0);
		if (docId > 0) {
			DocumentInfo document = getDocumentByDocId(docId);
			int readTime = document.getReadTime();
			readTime = readTime + 1;
			Object []para = {readTime};
			simpleDaoSupport.executeUpdate("update DocumentInfo set readTime=? where sid="+docId, para);
		}
	}
}
