package com.tianee.oa.core.base.fileNetdisk.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileHistory;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileHistoryDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileNetdiskDao;
import com.tianee.oa.core.base.fileNetdisk.model.TeeFileHistoryModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeFileHistoryService extends TeeBaseService {

	@Autowired
	private TeeFileHistoryDao teeFileHistoryDao;
	
	@Autowired
	private TeeAttachmentDao teeAttachmentDao;
	
	@Autowired
	@Qualifier("fileNetdiskDao")
	private TeeFileNetdiskDao fileNetdiskDao;

	/**
	 * 生成版本
	 * */
	public TeeJson addFileHistory(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int attachSid = TeeStringUtil.getInteger(request.getParameter("attachSid"),0);//附件id
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);//文件id
		String rease = request.getParameter("rease");//备注
		if(attachSid>0){
			TeeAttachment attachment = teeAttachmentDao.get(attachSid);
			TeeAttachment attachment2=new TeeAttachment();
			attachment2.setAttachmentName(attachment.getAttachmentName());
			attachment2.setAttachmentPath(attachment.getAttachmentPath());
			attachment2.setAttachSpace(attachment.getAttachSpace());
			Date date=new Date();
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			attachment2.setCreateTime(cal);
			attachment2.setExt(attachment.getExt());
			attachment2.setFileName(attachment.getFileName());
			attachment2.setMd5(attachment.getMd5());
			attachment2.setModel(attachment.getModel());
			attachment2.setModelId(attachment.getModelId());
			attachment2.setPriority(attachment.getPriority());
			attachment2.setSize(attachment.getSize());
			attachment2.setUser(attachment.getUser());
			attachment2.setVersion(attachment.getVersion()+1);
			Serializable save = teeAttachmentDao.save(attachment2);
			int attachId = TeeStringUtil.getInteger(save, 0);//新附件id
			if(sid>0){
				TeeAttachment attach=new TeeAttachment();
				TeeFileNetdisk netdisk = fileNetdiskDao.get(sid);
				attach.setSid(attachId);
				netdisk.setAttachemnt(attach);
				fileNetdiskDao.update(netdisk);
			}
			
		}
		TeeFileHistory fh=new TeeFileHistory();
		fh.setAttachId(attachSid);//附件id
		fh.setFileId(sid);//文件id
		fh.setRease(rease);//备注
		fh.setUser(person);//创建人
		Date date=new Date();
		fh.setCreateTime(date);//创建时间
		List<TeeFileHistory> find = teeFileHistoryDao.find("from TeeFileHistory where fileId=? order by banben desc", new Object[]{sid});
		if(find!=null && find.size()>0){
			TeeFileHistory teeFileHistory = find.get(0);
			fh.setBanben(teeFileHistory.getBanben()+1);
		}else{
			fh.setBanben(1);
		}
		Serializable fhId = teeFileHistoryDao.save(fh);
		int fId = TeeStringUtil.getInteger(fhId, 0);
		if(fId>0){
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}

	//获取所有版本
	public TeeEasyuiDataGridJson getFileHistoryAll(HttpServletRequest request,
			TeeDataGridModel dm) {
		List<TeeFileHistoryModel> modelList=new ArrayList<TeeFileHistoryModel>();
		Long count=0L;
		TeeEasyuiDataGridJson easyJson = new TeeEasyuiDataGridJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			String hql="from TeeFileHistory where fileId=?";
			List<TeeFileHistory> pageFind = teeFileHistoryDao.pageFind(hql+" order by sid desc", dm.getFirstResult(), dm.getRows(), new Object[]{sid});
			count= teeFileHistoryDao.count("select count(*) "+hql, new Object[]{sid});
			if(pageFind!=null && pageFind.size()>0){
				for(TeeFileHistory fh:pageFind){
					TeeFileHistoryModel m=new TeeFileHistoryModel();
					m.setAttachIdOld(fh.getAttachId());
					m.setBanben(fh.getBanben());
					m.setCreateTime(TeeDateUtil.format(fh.getCreateTime(), "yyyy-MM-dd HH:mm"));
					m.setFileId(fh.getFileId());
					m.setSid(fh.getSid());
					m.setUserId(fh.getUser().getUuid());
					m.setUserName(fh.getUser().getUserName());
					m.setSease(fh.getRease());
					modelList.add(m);
				}
			}
		}
		easyJson.setRows(modelList);
		easyJson.setTotal(count);
		return easyJson;
	}
}
