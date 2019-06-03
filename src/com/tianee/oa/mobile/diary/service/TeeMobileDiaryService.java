package com.tianee.oa.mobile.diary.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.diary.bean.TeeDiary;
import com.tianee.oa.core.base.diary.dao.TeeDiaryDao;
import com.tianee.oa.core.base.diary.model.TeeDiaryModel;
import com.tianee.oa.core.base.notify.bean.TeeNotify;
import com.tianee.oa.core.base.notify.bean.TeeNotifyInfo;
import com.tianee.oa.core.base.notify.model.TeeNotifyModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeMobileDiaryService  extends TeeBaseService{

	
	@Autowired
	private TeeAttachmentService attachmentService;

	@Autowired
	private TeeDiaryDao diaryDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	
	@Autowired
	@Qualifier("teeBaseUpload")
	private TeeBaseUpload upload;
	/**
	 *  自己日志
	 * @author syl
	 * @date 2014-3-13
	 * @param dm
	 * @param paraMap
	 * @param person
	 * @param isRed
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson query(TeeDataGridModel dm,HttpServletRequest request,TeePerson loginUser) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeeNotifyModel model = new TeeNotifyModel();
		String hql="from TeeDiary diary where 1=1  and diary.createUser.uuid="+loginUser.getUuid()+" order by diary.createTime desc";
	
		long count = diaryDao.count("select count(*) " + hql, null);
		j.setTotal(count);// 设置总记录数
		List<TeeDiaryModel> modelList = new ArrayList<TeeDiaryModel>();
		List<TeeDiary> list = diaryDao.pageFind(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(),null);// 查询
		if (list != null && list.size() > 0) {
			for (TeeDiary n : list) {
				TeeDiaryModel m = parseModel(n, true , loginUser);
				m.setContent(TeeUtility.cutHtml(m.getContent()));
				modelList.add(m);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	/**
	 *  共享日志
	 * @author syl
	 * @date 2014-3-13
	 * @param dm
	 * @param paraMap
	 * @param person
	 * @param isRed
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson queryShare(TeeDataGridModel dm,HttpServletRequest request,TeePerson loginUser) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeeNotifyModel model = new TeeNotifyModel();
		String hql="from TeeDiary diary where 1=1  and exists (select 1 from diary.shareRanges shareRanges where shareRanges.uuid = "+loginUser.getUuid()+") order by diary.createTime desc";
		
		long count = diaryDao.count("select count(*) " + hql, null);
		j.setTotal(count);// 设置总记录数
		List<TeeDiaryModel> modelList = new ArrayList<TeeDiaryModel>();
		List<TeeDiary> list = diaryDao.pageFind(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(),null);// 查询
		if (list != null && list.size() > 0) {
			for (TeeDiary n : list) {
				TeeDiaryModel m = parseModel(n, true , loginUser);
				m.setContent(TeeUtility.cutHtml(m.getContent()));
				modelList.add(m);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * 转换     通知公告 模版
	 * @author syl
	 * @date 2014-3-12
	 * @param n
	 * @param isSimple 是否简单模式
	 * @return
	 */
	public TeeDiaryModel parseModel(TeeDiary n , boolean isSimple , TeePerson person ){
		List<TeeAttachment> sorceAttachs = null;
		List<TeeAttachmentModel> attachs = new ArrayList<TeeAttachmentModel>();
		TeeDiaryModel m = new TeeDiaryModel();

		BeanUtils.copyProperties(n, m);

		if(n.getCreateUser() != null){
			m.setCreateUserName(n.getCreateUser().getUserName());

		}
		if(!isSimple){
			sorceAttachs = attachmentService.getAttaches(TeeAttachmentModelKeys.diary, String.valueOf(n.getSid()));
			if(sorceAttachs!= null && sorceAttachs.size() > 0){
				 for(int i=0;i<sorceAttachs.size();i++){
					 TeeAttachment f = (TeeAttachment)sorceAttachs.get(i);
					 TeeAttachmentModel attModel = new TeeAttachmentModel();
					 attModel.setSid( f.getSid());
					 attModel.setFileName(f.getFileName());
					 attModel.setAttachmentName(f.getAttachmentName());
					 attModel.setPriv(31);
					 attModel.setExt(f.getExt());
					 attModel.setSizeDesc(TeeFileUtility.getFileSizeDesc(f.getSize()));
					 attachs.add(attModel);
				 }
			}
			m.setAttacheModels(attachs);
			SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd");
			m.setCreateTimeDesc(TeeUtility.getDateTimeStr(n.getCreateTime().getTime()  ,format));
			m.setWriteTimeDesc(TeeUtility.getDateTimeStr(n.getWriteTime().getTime()  ,format));
			
			//共享人处理
			Set<TeePerson> shareRanges = n.getShareRanges();
			StringBuffer ids = new StringBuffer();
			StringBuffer names = new StringBuffer();
			
			int i=0;
			for(TeePerson sharePerson:shareRanges){
				ids.append(sharePerson.getUuid());
				names.append(sharePerson.getUserName());
				if(i!=shareRanges.size()-1){
					ids.append(",");
					names.append(",");
				}
				i++;
			}
			
			m.setShareRangesIds(ids.toString());
			m.setShareRangesNames(names.toString());
			
		}else{
			long count  = attachmentService.getAttachesCount(TeeAttachmentModelKeys.diary, String.valueOf(n.getSid()));
			m.setAttachmentCount(count);	
			int spanDay = TeeUtility.getDaySpan(n.getWriteTime().getTime() , new Date());//取得间隔数
			SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd");
			if(spanDay > 7){
				m.setWriteTimeDesc(TeeUtility.getDateTimeStr(n.getWriteTime().getTime()  ,format));
			}else if(spanDay < 1){
				m.setWriteTimeDesc("今天");
			}else{
				m.setWriteTimeDesc(spanDay + "天前");
			}
			
			spanDay = TeeUtility.getDaySpan(n.getCreateTime().getTime() , new Date());//取得间隔数
			if(spanDay > 7){
				m.setCreateTimeDesc(TeeUtility.getDateTimeStr(n.getCreateTime().getTime()  ,format));
			}else if(spanDay < 1){
				m.setCreateTimeDesc("今天");
			}else{
				m.setCreateTimeDesc(spanDay + "天前");
			}
		}

		return m;
	}
	
    /**
     * 根据Id  获取数据
     * @author syl
     * @date 2014-4-15
     * @param request
     * @param response
     * @return
     */
    public TeeJson getDiaryById(HttpServletRequest request , HttpServletResponse response){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid > 0){
			TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			TeeDiary notify = diaryDao.get(sid);
	
			TeeDiaryModel m =parseModel(notify, false, person);
			m.setContent(TeeUtility.cutHtml(m.getContent()));
			json.setRtState(true);
			json.setRtData(m);
		}else{
			json.setRtState(false);
		    json.setRtMsg("该日志已被删除");
		}
	    return json ;
    }
	
    
    /**
     * 新建或者更新 获取数据
     * @author syl
     * @date 2014-4-15
     * @param request
     * @param response
     * @return
     * @throws IOException 
     */
    public TeeJson addOrUpdate(HttpServletRequest request , HttpServletResponse response) throws IOException{
		TeeJson json = new TeeJson();
	    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List attachments = upload.manyAttachUpload(multipartRequest, TeeAttachmentModelKeys.diary);
		TeeDiaryModel model = new TeeDiaryModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(multipartRequest, model);
		
		int sid = TeeStringUtil.getInteger(multipartRequest.getParameter("sid"),0);
		String attachment_id_old = TeeStringUtil.getString(multipartRequest.getParameter("attachment_id_old"));
		String writeTimeDesc = TeeStringUtil.getString(multipartRequest.getParameter("writeTimeDesc"));
		String delExistattachIds = TeeStringUtil.getString(multipartRequest.getParameter("delExistattachIds"));//编辑的时候，删除已存在的附件Id字符串，以逗号分隔
		
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(!writeTimeDesc.equals("")){
			try {
				cal.setTime(sdf.parse(writeTimeDesc));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeDiary diary;
		if(sid > 0){
			 diary = diaryDao.get(sid);
			if(diary != null){
				diary.setContent(model.getContent());
				diary.setTitle(model.getTitle());
				diary.setType(model.getType());
				diary.setWriteTime(cal);
				
				//设置共享范围
				diary.getShareRanges().clear();
				int ranges[] = TeeStringUtil.parseIntegerArray(model.getShareRangesIds());
				for(int uuid:ranges){
					if(uuid==0){
						continue;
					}
					diary.getShareRanges().add((TeePerson)simpleDaoSupport.get(TeePerson.class, uuid));
				}
				
				/* 删除附件*/
				if(!delExistattachIds.equals("")){
					String delExistattachIdArray[] = delExistattachIds.split(",");
					for (int i = 0; i < delExistattachIdArray.length; i++) {
						int attachId = TeeStringUtil.getInteger(delExistattachIdArray[i] , 0);
						if(attachId > 0){
							attachmentService.deleteAttach(attachId);
						}
					}
				}
				diaryDao.update(diary);
				json.setRtState(true);
			    json.setRtMsg("保存成功");
			}
			
		}else{
			 diary = new TeeDiary();
			BeanUtils.copyProperties(model, diary);
			diary.setCreateUser(person);
			diary.setCreateTime(Calendar.getInstance());
			diary.setWriteTime(cal);
			
			//设置共享范围
			diary.getShareRanges().clear();
			int ranges[] = TeeStringUtil.parseIntegerArray(model.getShareRangesIds());
			for(int uuid:ranges){
				if(uuid==0){
					continue;
				}
				diary.getShareRanges().add((TeePerson)simpleDaoSupport.get(TeePerson.class, uuid));
			}
			
			diaryDao.save(diary);
			json.setRtState(true);
		    json.setRtMsg("保存成功");
		}
		
		/**
		 * 设置附件外键
		 */
		for(int i=0;i<attachments.size();i++){
			TeeAttachment attach = (TeeAttachment) attachments.get(i);
			attach.setModelId(String.valueOf(diary.getSid()));
			attachmentService.updateAttachment(attach);
		}
	    return json ;
    }
    
    /**
     * 获取当前登陆人的直属下级
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public TeeEasyuiDataGridJson getUnderlings(HttpServletRequest request , TeeDataGridModel dataGridModel) throws IOException{
    	TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		TeePerson loginUser = 
				(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		loginUser = personDao.get(loginUser.getUuid());
		List<TeePerson> list = personDao.getUnderlines(loginUser.getUuid());
		List returnList = new ArrayList();
		for(TeePerson p:list){
			Map map = new HashMap();
			map.put("userName", p.getUserName());
			map.put("userSid", p.getUuid());
			map.put("deptName", p.getDept().getDeptName());
			map.put("roleName", p.getUserRole().getRoleName());
			returnList.add(map);
		}
		
		json.setRows(returnList);
		return json;
    }
    
    
    /**
	 * 获取直属下级人员的日志（工作日志）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public TeeEasyuiDataGridJson getUnderlingDiarys(HttpServletRequest request , TeeDataGridModel dataGridModel) throws IOException{
    	TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeeNotifyModel model = new TeeNotifyModel();
		int userSid = TeeStringUtil.getInteger(request.getParameter("userSid"), 0);
		TeePerson person = personDao.get(userSid);
		String hql="from TeeDiary diary where diary.createUser.uuid="+userSid+" and type=2 ";
		
		long count = diaryDao.count("select count(*) " + hql, null);
		j.setTotal(count);// 设置总记录数
		List<TeeDiaryModel> modelList = new ArrayList<TeeDiaryModel>();
		List<TeeDiary> list = diaryDao.pageFind(hql+" order by diary.createTime desc", (dataGridModel.getPage() - 1) * dataGridModel.getRows(), dataGridModel.getRows(),null);// 查询
		if (list != null && list.size() > 0) {
			for (TeeDiary n : list) {
				TeeDiaryModel m = parseModel(n, true , person);
				m.setContent(TeeUtility.cutHtml(m.getContent()));
				modelList.add(m);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
    }
}


