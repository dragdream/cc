package com.tianee.oa.subsys.zhidao.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tianee.lucene.TeeLuceneSoapService;
import com.tianee.lucene.entity.DocumentRecords;
import com.tianee.lucene.entity.SearchModel;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.zhidao.bean.TeeZhiDaoAnswer;
import com.tianee.oa.subsys.zhidao.bean.TeeZhiDaoCat;
import com.tianee.oa.subsys.zhidao.bean.TeeZhiDaoLabel;
import com.tianee.oa.subsys.zhidao.bean.TeeZhiDaoQuestion;
import com.tianee.oa.subsys.zhidao.model.TeeZhiDaoQuestionModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeKeyWordUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeZhiDaoQuestionService extends TeeBaseService{

	@Autowired
	@Qualifier("teeBaseUpload")
	private TeeBaseUpload upload;
	
	
	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	@Autowired
	private TeeZhiDaoLabelService  zhiDaoLabelService;
	
	/**
	 * 新建  编辑问题
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	public TeeJson addOrUpdate(HttpServletRequest request) throws IOException {
		TeeJson json=new TeeJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String attachmentSidStr = TeeStringUtil.getString(request.getParameter("attachmentSidStr"));
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String title=TeeStringUtil.getString(request.getParameter("title"));
		String description=TeeStringUtil.getString(request.getParameter("description"));
		String label=TeeStringUtil.getString(request.getParameter("label")).trim();//去掉首尾的空格
		int catId=TeeStringUtil.getInteger(request.getParameter("catId"),0);
		
		TeeZhiDaoCat cat=(TeeZhiDaoCat) simpleDaoSupport.get(TeeZhiDaoCat.class,catId);
		List<TeeAttachment> attachments = attachmentDao.getAttachmentsByIds(attachmentSidStr);//新上传附件
		
		//处理标签
		String newLabel="";
		if(!TeeUtility.isNullorEmpty(label)){
			String[] labelArr=label.split(" ");
			if(labelArr!=null&&labelArr.length>0){
				for (int i = 0; i < labelArr.length; i++) {
					if(!TeeUtility.isNullorEmpty(labelArr[i].trim())){
						//判断标签库中该标签是否存在  存在则点击数加1  不存在则加入
						TeeZhiDaoLabel zhiDaoLabel=zhiDaoLabelService.isExists(labelArr[i].trim());
						if(zhiDaoLabel!=null){//存在
							zhiDaoLabel.setClick(zhiDaoLabel.getClick()+1);
							simpleDaoSupport.update(zhiDaoLabel);
						}else{//不存在
							zhiDaoLabel=new TeeZhiDaoLabel();
							zhiDaoLabel.setClick(1);
							zhiDaoLabel.setLabelName(labelArr[i].trim());
							simpleDaoSupport.save(zhiDaoLabel);
						}
						newLabel+=labelArr[i].trim()+" ";
					}
				}
			}
		}
		if(newLabel.endsWith(" ")){
			newLabel=newLabel.substring(0,newLabel.length()-1);
		}
		
		//lucene服务
		TeeLuceneSoapService luceneSoapService = new TeeLuceneSoapService();
		String luceneTable = "zhidao";
		
		if(sid>0){//编辑
			TeeZhiDaoQuestion q=(TeeZhiDaoQuestion) simpleDaoSupport.get(TeeZhiDaoQuestion.class,sid);
			q.setCat(cat);
			/*q.setClick(0);
			q.setCreateTime(new Date());
			q.setCreateUser(loginUser);*/
			q.setDescription(description);
			q.setLabel(label);
			q.setStatus(0);
			q.setTitle(title);
			
			simpleDaoSupport.update(q);
			
			
			SearchModel model = new SearchModel();
			model.setSpace(luceneTable);
			model.setTerm("sid|int:["+q.getSid()+" TO "+q.getSid()+"]");
			luceneSoapService.deleteDocuments(model);
			
			Map record = new HashMap();
			record.put("title", title);
			record.put("description", description);
			record.put("sid", q.getSid());
			record.put("createTime", q.getCreateTime().getTime());
			record.put("catId", catId);
			record.put("status", 0);
			
			//添加lucene索引
			luceneSoapService.addRecord(luceneTable, record);

			//添加新附件
			if(attachments!=null&&attachments.size()>0){
				for(TeeAttachment attach:attachments){
					attach.setModelId(String.valueOf(q.getSid()));
					attachmentDao.update(attach);
				}
			}
			
		}else{//新建
			TeeZhiDaoQuestion q=new TeeZhiDaoQuestion();
			q.setCat(cat);
			q.setClickCount(0);
			q.setCreateTime(new Date());
			q.setCreateUser(loginUser);
			q.setDescription(description);
			q.setLabel(label);
			q.setStatus(0);
			q.setTitle(title);
			
			simpleDaoSupport.save(q);
			
			Map record = new HashMap();
			record.put("title", title);
			record.put("description", description);
			record.put("sid", q.getSid());
			record.put("createTime", new Date().getTime());
			record.put("catId", catId);
			record.put("status", 0);
			
			
			//添加lucene索引
			luceneSoapService.addRecord(luceneTable, record);
			
			
			//添加新附件
			if(attachments!=null&&attachments.size()>0){
				for(TeeAttachment attach:attachments){
					attach.setModelId(String.valueOf(q.getSid()));
					attachmentDao.update(attach);
				}
			}
		}
		json.setRtState(true);
		return json;
	}

	
	/**
	 * 获取所有相关问题的总页数
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	public TeeJson getRelationsTotalPages(HttpServletRequest request) throws IOException {
		TeeJson json=new TeeJson();
		Map returnData=new HashMap();
		List<String>param=new ArrayList<String>();
		String hql=" from TeeZhiDaoQuestion q where q.status=1 ";
		//获取页面上传来的title
		String title=TeeStringUtil.getString(request.getParameter("title"));
		String [] keywords = TeeKeyWordUtil.getKeyWords(title); 
		String keys="";
		if(keywords!=null&&keywords.length>0){
			hql+=" and (";
			for(int i=0; i<keywords.length; i++){
				keys+=keywords[i];
		    	hql+=" (q.title like ? ) ";
		    	if(i!=(keywords.length-1)){
		    		hql+=" or "; 
		    		keys+=" ";
		    	}
		    	param.add("%"+keywords[i]+"%");
		    } 
			hql+=")";
		}else{
			returnData.put("allPage", 0);
			returnData.put("keyWords", "");
			json.setRtData(returnData);
			json.setRtState(true);
			return json;
		}
	    
		double allPage=0;
		List<TeeZhiDaoQuestion> list=simpleDaoSupport.executeQuery(hql, param.toArray());
		if(list!=null&&list.size()>0){
			allPage=Math.ceil((double)(list.size()/5.00));
		}else{
			allPage=0;
		}
		returnData.put("allPage",(int)allPage);
		returnData.put("keyWords",keys );
		json.setRtData(returnData);
		json.setRtState(true);
		return json;
	}


	/**
	 * 根据页码和标题获取相关内容
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	public TeeJson getRelationsByTitle(HttpServletRequest request) throws IOException {
		TeeJson json=new TeeJson();
		int page=TeeStringUtil.getInteger(request.getParameter("page"),0);
		int pageSize=TeeStringUtil.getInteger(request.getParameter("pageSize"), 0);
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		List<String>param=new ArrayList<String>();
		String hql=" from TeeZhiDaoQuestion q where q.status=1 ";
		if(sid!=0){
			hql+=" and q.sid!="+sid+" ";
		}
		//获取页面上传来的title
		String title=TeeStringUtil.getString(request.getParameter("title"));
		String [] keywords = TeeKeyWordUtil.getKeyWords(title); 
		if(keywords!=null&&keywords.length>0){
			hql+=" and (";
			for(int i=0; i<keywords.length; i++){
		    	hql+=" (q.title like ? ) ";
		    	if(i!=(keywords.length-1)){
		    		hql+=" or "; 
		    	}
		    	param.add("%"+keywords[i]+"%");
		    } 
			hql+=")";
		}else{
			json.setRtState(true);
			json.setRtData(new ArrayList());
			return json;
		}
		List<TeeZhiDaoQuestion> list=
				simpleDaoSupport.pageFind(hql+" order by q.clickCount desc", (page-1)*pageSize, pageSize, param.toArray());
		List<TeeZhiDaoQuestionModel> modelList=new ArrayList<TeeZhiDaoQuestionModel>();
		TeeZhiDaoQuestionModel model=null;
		if(list!=null&&list.size()>0){
			for (TeeZhiDaoQuestion q : list) {
				model=parseToModel(q);
				modelList.add(model);
			}
		}
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}


	/**
	 * 实体类转换成model类型
	 * @param q
	 * @return
	 */
	private TeeZhiDaoQuestionModel parseToModel(TeeZhiDaoQuestion q) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeZhiDaoQuestionModel model=new TeeZhiDaoQuestionModel();
		BeanUtils.copyProperties(q, model);
		if(q.getCat()!=null){
			model.setCatId(q.getCat().getSid());
			model.setCatName(q.getCat().getCatName());
		}
		if(q.getCreateTime()!=null){
			model.setCreateTimeStr(sdf.format(q.getCreateTime()));
		}
		if(q.getCreateUser()!=null){
			model.setCreateUserId(q.getCreateUser().getUuid());
			model.setCreateUserName(q.getCreateUser().getUserName());
			if(q.getCreateUser().getDept()!=null){
				model.setCreateUserInfo(q.getCreateUser().getUserName()+"["+q.getCreateUser().getDept().getDeptName()+"]");
			}else{
				model.setCreateUserInfo(q.getCreateUser().getUserName());
			}
		}
		if(q.getHandleTime()!=null){
			model.setHandleTimeStr(sdf.format(q.getHandleTime()));
		}
		
		List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
		List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.zhiDaoQuestion, String.valueOf(q.getSid()));
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
	 * 获取已解决和待解决的问题数量
	 * @param request
	 * @return
	 */
	public TeeJson getHandledAndNoHandledCount(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		Map<String,Integer> map=new HashMap<String,Integer>();
		String hql1=" select count(q.sid) from TeeZhiDaoQuestion q where q.status=1 ";
		String hql2=" select count(q.sid) from TeeZhiDaoQuestion q where q.status=0 ";
		long handledCount=simpleDaoSupport.count(hql1, null);
		long noHandledCount=simpleDaoSupport.count(hql2, null);
		
		map.put("handledCount", (int)handledCount);
		map.put("noHandledCount",(int) noHandledCount);
		json.setRtState(true);
		json.setRtData(map);
		return json;
	}


	/**
	 * 获取所有我创建的问题
	 * @param request
	 * @param dm
	 * @return
	 * @throws IOException 
	 */
	public TeeEasyuiDataGridJson getMyQuestion(HttpServletRequest request,
			TeeDataGridModel dm) throws IOException {
		String title=TeeStringUtil.getString(request.getParameter("title"));
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List param=new ArrayList();
		String hql=" from TeeZhiDaoQuestion q where createUser.uuid=? ";
		param.add(loginUser.getUuid());
		if(!TeeUtility.isNullorEmpty(title)){
			String [] keywords = TeeKeyWordUtil.getKeyWords(title); 
			if(keywords!=null&&keywords.length>0){
				hql+=" and (";
				for(int i=0; i<keywords.length; i++){
			    	hql+=" (q.title like ? ) ";
			    	if(i!=(keywords.length-1)){
			    		hql+=" or "; 
			    	}
			    	param.add("%"+keywords[i]+"%");
			    } 
				hql+=")";
			}else{
				json.setTotal(0L);
				json.setRows(new ArrayList());
				return json;
			}
		}
		
		
		
		
		long total=simpleDaoSupport.count(" select count(q.sid) "+hql,param.toArray());
		json.setTotal(total);
		List<TeeZhiDaoQuestion> list=simpleDaoSupport.pageFind(hql+" order by q.createTime desc ", (dm.getPage() - 1) * dm.getRows(), dm.getRows(),param.toArray());
		List<TeeZhiDaoQuestionModel> modelList=new ArrayList<TeeZhiDaoQuestionModel>();
		TeeZhiDaoQuestionModel model=null;
		if(list!=null&&list.size()>0){
	    	for (TeeZhiDaoQuestion q : list) {
				model=parseToModel(q);
				modelList.add(model);
			}
	    }
		json.setRows(modelList);
		return json;
	}


	
	/**
	 * 获取所有待解决的问题
	 * @param request
	 * @param dm
	 * @return
	 * @throws IOException 
	 */
	public TeeEasyuiDataGridJson getNoHandledQuestion(
			HttpServletRequest request, TeeDataGridModel dm) throws IOException {
        String title=TeeStringUtil.getString(request.getParameter("title"));
		int catId=TeeStringUtil.getInteger(request.getParameter("catId"),0);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		List param=new ArrayList();
		String hql=" from TeeZhiDaoQuestion q where status=0  ";
		
		if(catId!=0){
			hql+=" and q.cat.sid=? ";
			param.add(catId);
		}
		
		if(!TeeUtility.isNullorEmpty(title)){
			String [] keywords = TeeKeyWordUtil.getKeyWords(title); 
			if(keywords!=null&&keywords.length>0){
				hql+=" and (";
				for(int i=0; i<keywords.length; i++){
			    	hql+=" (q.title like ? ) ";
			    	if(i!=(keywords.length-1)){
			    		hql+=" or "; 
			    	}
			    	param.add("%"+keywords[i]+"%");
			    } 
				hql+=")";
			}else{
				json.setTotal(0l);
				json.setRows(new ArrayList());
				return json;
			}
			
		}
		
		
		
		long total=simpleDaoSupport.count(" select count(q.sid) "+hql,param.toArray());
		json.setTotal(total);
		List<TeeZhiDaoQuestion> list=simpleDaoSupport.pageFind(hql+" order by q.createTime desc ", (dm.getPage() - 1) * dm.getRows(), dm.getRows(),param.toArray());
		List<TeeZhiDaoQuestionModel> modelList=new ArrayList<TeeZhiDaoQuestionModel>();
		TeeZhiDaoQuestionModel model=null;
		if(list!=null&&list.size()>0){
	    	for (TeeZhiDaoQuestion q : list) {
				model=parseToModel(q);
				modelList.add(model);
			}
	    }
		json.setRows(modelList);
		return json;
	}


	
	/**
	 * 获取最近已解决的问题
	 * @param request
	 * @param dm
	 * @return
	 * @throws IOException 
	 */
	public TeeEasyuiDataGridJson getHandledQuestion(HttpServletRequest request,
			TeeDataGridModel dm) throws IOException {
		    String title=TeeStringUtil.getString(request.getParameter("title"));
		    int catId=TeeStringUtil.getInteger(request.getParameter("catId"),0);
		    
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
			List param=new ArrayList();
			String hql=" from TeeZhiDaoQuestion q where status=1  ";
			
			
			if(catId!=0){
				hql+=" and q.cat.sid=? ";
				param.add(catId);
			}
			
			if(!TeeUtility.isNullorEmpty(title)){
				String [] keywords = TeeKeyWordUtil.getKeyWords(title); 
				if(keywords!=null&&keywords.length>0){
					hql+=" and (";
					for(int i=0; i<keywords.length; i++){
				    	hql+=" (q.title like ? ) ";
				    	if(i!=(keywords.length-1)){
				    		hql+=" or "; 
				    	}
				    	param.add("%"+keywords[i]+"%");
				    } 
					hql+=")";
				}else{
					json.setTotal(0l);
					json.setRows(new ArrayList());
					return json;
				}
			}
			
			
			
			
			long total=simpleDaoSupport.count(" select count(q.sid) "+hql,param.toArray());
			json.setTotal(total);
			List<TeeZhiDaoQuestion> list=simpleDaoSupport.pageFind(hql+" order by q.handleTime desc ", (dm.getPage() - 1) * dm.getRows(), dm.getRows(),param.toArray());
			List<TeeZhiDaoQuestionModel> modelList=new ArrayList<TeeZhiDaoQuestionModel>();
			TeeZhiDaoQuestionModel model=null;
			if(list!=null&&list.size()>0){
		    	for (TeeZhiDaoQuestion q : list) {
					model=parseToModel(q);
					modelList.add(model);
				}
		    }
			json.setRows(modelList);
			return json;
	}


	
	/**
	 * 根据主键删除
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson json=new  TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeZhiDaoQuestion q=(TeeZhiDaoQuestion) simpleDaoSupport.get(TeeZhiDaoQuestion.class,sid);
		if(q!=null){
			//删除相关的回复信息
			simpleDaoSupport.executeUpdate(" delete from TeeZhiDaoAnswer  where question.sid=? ", new Object[]{sid});
			simpleDaoSupport.deleteByObj(q);
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("该问题已不存在！");
		}
		return json;
	}


	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeZhiDaoQuestion q=(TeeZhiDaoQuestion) simpleDaoSupport.get(TeeZhiDaoQuestion.class,sid);
		TeeZhiDaoQuestionModel model=new TeeZhiDaoQuestionModel();
		if(q!=null){
			model=parseToModel(q);
			json.setRtData(model);
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("信息获取失败！");
		}
	    return json;
	}


	/**
	 * 采纳正确答案
	 * @param request
	 * @return
	 */
	public TeeJson adopt(HttpServletRequest request) {
		TeeLuceneSoapService luceneSoapService = new TeeLuceneSoapService();
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		int answerSid=TeeStringUtil.getInteger(request.getParameter("answerSid"), 0);
		TeeZhiDaoQuestion q=(TeeZhiDaoQuestion) simpleDaoSupport.get(TeeZhiDaoQuestion.class,sid);
		TeeZhiDaoAnswer a=(TeeZhiDaoAnswer) simpleDaoSupport.get(TeeZhiDaoAnswer.class,answerSid);
		if(q!=null&&a!=null){
			q.setHandleTime(new Date());
			q.setStatus(1);
			
			a.setIsBest(1);
			simpleDaoSupport.update(q);
			simpleDaoSupport.update(a);
			
			SearchModel model = new SearchModel();
			model.setSpace("zhidao");
			model.setTerm("sid|int:["+q.getSid()+" TO "+q.getSid()+"]");
			luceneSoapService.deleteDocuments(model);
			
			Map record = new HashMap();
			record.put("title", q.getTitle());
			record.put("description", q.getDescription());
			record.put("sid", q.getSid());
			record.put("createTime", q.getCreateTime().getTime());
			record.put("catId", q.getCat().getSid());
			record.put("status", q.getStatus());
			
			//添加lucene索引
			luceneSoapService.addRecord("zhidao", record);
			
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}


	/**
	 * 改变问题的点击量
	 * @param request
	 * @return
	 */
	public TeeJson updateClick(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		simpleDaoSupport.executeUpdate("update TeeZhiDaoQuestion set clickCount=clickCount+1 where sid="+sid, null);
		return json;
		
	}

	
	

	/**
	 * 获取我参与的问题
	 * @param request
	 * @param dm
	 * @return
	 * @throws IOException 
	 */
	public TeeEasyuiDataGridJson getMyParticipate(HttpServletRequest request,
			TeeDataGridModel dm) throws IOException {
        String title=TeeStringUtil.getString(request.getParameter("title"));
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List param=new ArrayList();
		String hql=" from TeeZhiDaoAnswer a,TeeZhiDaoQuestion q where  a.question.sid=q.sid   and a.createUser.uuid=? ";
		param.add(loginUser.getUuid());
		if(!TeeUtility.isNullorEmpty(title)){
			String [] keywords = TeeKeyWordUtil.getKeyWords(title); 
			if(keywords!=null&&keywords.length>0){
				hql+=" and (";
				for(int i=0; i<keywords.length; i++){
			    	hql+=" (q.title like ? ) ";
			    	if(i!=(keywords.length-1)){
			    		hql+=" or "; 
			    	}
			    	param.add("%"+keywords[i]+"%");
			    } 
				hql+=")";
			}else{
				json.setTotal(0L);
				json.setRows(new ArrayList());
				return json;
			}
		}
		
		
		
		
		long total=simpleDaoSupport.count(" select q.sid "+hql,param.toArray());
		json.setTotal(total);
		List<TeeZhiDaoQuestion> list=simpleDaoSupport.pageFind(" select q "+hql+" order by q.createTime desc ", (dm.getPage() - 1) * dm.getRows(), dm.getRows(),param.toArray());
		List<TeeZhiDaoQuestionModel> modelList=new ArrayList<TeeZhiDaoQuestionModel>();
		TeeZhiDaoQuestionModel model=null;
		if(list!=null&&list.size()>0){
	    	for (TeeZhiDaoQuestion q : list) {
				model=parseToModel(q);
				modelList.add(model);
			}
	    }
		json.setRows(modelList);
		return json;
	}
	
	public static void main(String[] args) {
		
		TeeSysProps.setProps(new Properties());
		TeeSysProps.getProps().setProperty("LUCENE_SPACE", "D:\\oaop\\lucene");
		
		TeeLuceneSoapService luceneSoapService = new TeeLuceneSoapService();
		
		
		SearchModel model = new SearchModel();
		model.setSpace("zhidao");
		model.setDefaultSearchField(new String[]{"title","description"});
		model.setReturnField(new String[]{"title","description","sid","status","catId"});
		model.setLightedField(new String[]{"description","title"});
		String term="((title:OA) OR (description:OA)) AND (status|int:[1 TO 1])";
		
		model.setTerm(term);
//		model.setCurPage(page);
//		model.setPageSize(pageSize);
		
		DocumentRecords results = luceneSoapService.queryParserSearch(model);
		System.out.println(results.getRecordList());
	}


	
	/**
	 * 获取检索到的总记录数目
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getSearchResult(HttpServletRequest request) {
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		int page=TeeStringUtil.getInteger(request.getParameter("page"),0);
		int pageSize=TeeStringUtil.getInteger(request.getParameter("pageSize"),0);
		int catId=TeeStringUtil.getInteger(request.getParameter("catId"),0);
		int status=TeeStringUtil.getInteger(request.getParameter("status"),0);
		String keyWord=TeeStringUtil.getString(request.getParameter("keyWord"));
		TeeLuceneSoapService luceneSoapService = new TeeLuceneSoapService();
		
		SearchModel model = new SearchModel();
		model.setSpace("zhidao");
		model.setReturnFieldWordCount(new int[]{50,300});
		model.setDefaultSearchField(new String[]{"title","description"});
		model.setReturnField(new String[]{"title","description","sid","status","catId"});
		model.setLightedField(new String[]{"description","title"});
		String term="(status|int:["+status+" TO "+status+"])";
		if(!TeeUtility.isNullorEmpty(keyWord)){
			term+=" AND ((title:"+keyWord+") OR (description:"+keyWord+")) ";
		}
		if(catId!=0){
			term+=" AND (catId|int:["+catId+" TO "+catId+"])";
		}
		model.setTerm(term);
		model.setCurPage(page);
		model.setPageSize(pageSize);
		
		DocumentRecords results = luceneSoapService.queryParserSearch(model);
		json.setTotal(TeeStringUtil.getLong(results.getTotalHits(),0));
		
		List<TeeZhiDaoQuestionModel> modelList=new ArrayList<TeeZhiDaoQuestionModel>();
		TeeZhiDaoQuestion question=null;
		TeeZhiDaoQuestionModel questionModel=null;
		if(results.getRecordList()!=null&&results.getRecordList().size()>0){
			for (Map m : results.getRecordList()) {
				question=(TeeZhiDaoQuestion) simpleDaoSupport.get(TeeZhiDaoQuestion.class,TeeStringUtil.getInteger(m.get("sid"), 0));
			    if(question!=null){
			    	questionModel=parseToModel(question);
			    	questionModel.setTitle(m.get("title")+"");
			    	questionModel.setDescription(m.get("description")+"");
			    	modelList.add(questionModel);
			    }
				
			}
		}
		json.setRows(modelList);
		return json;
	}
}
