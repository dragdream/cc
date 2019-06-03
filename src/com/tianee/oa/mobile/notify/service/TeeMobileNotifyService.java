package com.tianee.oa.mobile.notify.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.base.news.bean.TeeNews;
import com.tianee.oa.core.base.news.bean.TeeNewsInfo;
import com.tianee.oa.core.base.news.dao.TeeNewsDao;
import com.tianee.oa.core.base.news.model.TeeNewsModel;
import com.tianee.oa.core.base.news.service.TeeNewsService;
import com.tianee.oa.core.base.notify.bean.TeeNotify;
import com.tianee.oa.core.base.notify.bean.TeeNotifyInfo;
import com.tianee.oa.core.base.notify.dao.TeeNotifyDao;
import com.tianee.oa.core.base.notify.dao.TeeNotifyInfoDao;
import com.tianee.oa.core.base.notify.model.TeeNotifyModel;
import com.tianee.oa.core.base.notify.service.TeeNotifyService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.mobile.global.TeeMobileConst;
import com.tianee.oa.mobile.news.dao.TeeMobileNewsDao;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeMobileNotifyService  extends TeeBaseService{
	@Autowired
	TeeNotifyService notifyService ;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	TeeNotifyDao notifyDao;
	
	@Autowired
	TeeNotifyInfoDao  notifyInfoDao;

	
	/**
	 * 查看公告    --- 查询
	 * @author syl
	 * @date 2014-3-13
	 * @param dm
	 * @param paraMap
	 * @param person
	 * @param isRed
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson query(TeeDataGridModel dm,HttpServletRequest request,TeePerson person) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeeNotifyModel model = new TeeNotifyModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map map = notifyDao.getPersonalQuery(person, dm, model, "0",request);
		long count = (Long)map.get("notifyCount");
		j.setTotal(count);// 设置总记录数
		
		
		List<TeeNotifyModel> notifysmodel = new ArrayList<TeeNotifyModel>();
		map = notifyDao.getPersonalQuery(person, dm, model, "1",request);
		List<TeeNotify> notifys = (List<TeeNotify>)map.get("notifyList");
		if (notifys != null && notifys.size() > 0) {
			for (TeeNotify n : notifys) {
				TeeNotifyModel m = parseNotifyModel(n, true , person);
				m.setContent("");
				m.setFromPersonName(n.getFromPerson().getUserName());
				notifysmodel.add(m);
			}
		}
		j.setRows(notifysmodel);// 设置返回的行
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
	public TeeNotifyModel parseNotifyModel(TeeNotify n , boolean isSimple , TeePerson person ){
		List<TeeAttachment> sorceAttachs = null;
		List<Map> attachs = new ArrayList<Map>();
		TeeNotifyModel m = new TeeNotifyModel();
		BeanUtils.copyProperties(n, m);
		m.setFromPersonName(n.getFromPerson().getUserName());
		m.setFromDeptName(n.getFromPerson().getDept().getDeptName());
		m.setSendTimeDesc(TeeDateUtil.format(n.getSendTime()));
		String typeDesc = TeeSysCodeManager.getChildSysCodeNameCodeNo("NOTIFY_TYPE", m.getTypeId());
		typeDesc = "".equals(typeDesc)?"默认":typeDesc;
		m.setTypeDesc(typeDesc);
		if(!isSimple){
			sorceAttachs = attachmentService.getAttaches(TeeAttachmentModelKeys.NOTIFY, String.valueOf(n.getSid()));
			if(sorceAttachs!= null && sorceAttachs.size() > 0){
				 for(int i=0;i<sorceAttachs.size();i++){
					 TeeAttachment f = (TeeAttachment)sorceAttachs.get(i);
					 
					 Map map = new HashMap<String, String>();
					 map.put("sid", f.getSid());
					 map.put("priv", 31);
					 map.put("ext", f.getExt());
					 map.put("fileName", f.getFileName());
					 map.put("attachmentName", f.getAttachmentName());
					 map.put("sizeDesc",TeeFileUtility.getFileSizeDesc(f.getSize()));
					 attachs.add(map);
				 }
			}
			m.setAttachmentsMode(attachs);
		}else{
			long count  = attachmentService.getAttachesCount(TeeAttachmentModelKeys.NOTIFY, String.valueOf(n.getSid()));
			m.setAttachmentCount(count);	
			
			List<TeeNotifyInfo> infoList = n.getInfos();
			for (int i = 0; i < infoList.size(); i++) {
				TeeNotifyInfo info = infoList.get(i);
				TeePerson p = info.getToUser();
				if(p != null && p.getUuid() == person.getUuid() && info.getIsRead() == 1){//当前用户已经阅读
					m.setReadType(1);
					break;
				}
			}
			
			int spanDay = TeeUtility.getDaySpan(n.getSendTime() , new Date());//取得间隔数
			SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd");
			if(spanDay > 7){
				m.setSendTimeDesc(TeeUtility.getDateTimeStr(n.getSendTime() ,format));
			}else if(spanDay < 1){
				m.setSendTimeDesc("今天");
			}else{
				m.setSendTimeDesc(spanDay + "天前");
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
    public TeeJson getNotifyById(HttpServletRequest request , HttpServletResponse response){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		int isLooked = TeeStringUtil.getInteger(request.getParameter("isLooked"),0);
		if(sid > 0){
			TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			TeeNotify notify = notifyDao.getById(sid);
//			if(isLooked != 1){
				boolean exists = notifyInfoDao.checkExistsInfo(person, notify);
				if(!exists){//不存在
					TeeNotifyInfo info = new TeeNotifyInfo();
					info.setIsRead(1);
					info.setReadTime(new Date());
					info.setToUser(person);
					info.setNotify(notify);
					notifyInfoDao.addNotify(info);
				}
//			 }
			 TeeNotifyModel m = parseNotifyModel(notify, false, person);
			 json.setRtState(true);
			 json.setRtData(m);
		}else{
			json.setRtState(false);
		    json.setRtMsg("无公告");
		}
	    return json ;
    }
	
    

}


