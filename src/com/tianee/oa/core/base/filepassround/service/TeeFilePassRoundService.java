package com.tianee.oa.core.base.filepassround.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.base.filepassround.bean.TeeFilePassRound;
import com.tianee.oa.core.base.filepassround.bean.TeeFilePassRoundRecord;
import com.tianee.oa.core.base.filepassround.dao.TeeFilePassRoundDao;
import com.tianee.oa.core.base.filepassround.dao.TeeFilePassRoundRecordDao;
import com.tianee.oa.core.base.filepassround.model.TeeFilePassRoundModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeFilePassRoundService extends TeeBaseService{
	
	@Autowired
	private TeeFilePassRoundDao filePassRoundDao;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private TeeFilePassRoundRecordDao filePassRoundRecordDao;
	
	public void save(HttpServletRequest request,TeeFilePassRoundModel filePassRoundModel){		
		TeeFilePassRound filePassRound = new TeeFilePassRound();
		
		TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Integer userId=person.getUuid();
		
		String uuid = UUID.randomUUID().toString();
		
		//保存主表中的内容
		BeanUtils.copyProperties(filePassRoundModel, filePassRound);
		filePassRound.setProId(userId);		
		filePassRound.setSendTime(Calendar.getInstance());
		filePassRound.setId(uuid);
		filePassRoundDao.save(filePassRound);
							
			
		//保存子表中的内容
		String conIdStr = filePassRoundModel.getConIdStr();
		String[] conIdArr = conIdStr.split(",");
		for (int i = 0; i < conIdArr.length; i++) {
			TeeFilePassRoundRecord filePassRoundRecord = new TeeFilePassRoundRecord();
			String uid = UUID.randomUUID().toString();
			filePassRoundRecord.setConId(Integer.parseInt(conIdArr[i]));
			filePassRoundRecord.setState(0);
			filePassRoundRecord.setFilePassRound(filePassRound);
			filePassRoundRecord.setId(uid);
			filePassRoundRecordDao.save(filePassRoundRecord);
		}
		
		//保存文件		
		String attachIds = filePassRoundModel.getAttaches();
		List<TeeAttachment> attachments = attachmentService.getAttachmentsByIds(attachIds);
		if(attachments !=null && attachments.size()>0){}
		for(TeeAttachment attach:attachments){
			attach.setModelId(filePassRound.getId());
			attachmentService.updateAttachment(attach);
		}
	}

	public TeeEasyuiDataGridJson findByProId(HttpServletRequest request, TeeDataGridModel m,TeeFilePassRoundModel queryModel) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int userId=person.getUuid();
		
		List<Calendar> queryList = new ArrayList<Calendar>();
		
		//条件查询sql语句拼接
		String sql = "from TeeFilePassRound where proId="+userId;
		
		if(!TeeUtility.isNullorEmpty(queryModel.getTitle())){
			sql += " and title like '%"+queryModel.getTitle()+"%'";
		}
		
		if (!TeeUtility.isNullorEmpty(queryModel.getStartTime())) {
			Date format = TeeDateUtil.format(queryModel.getStartTime(), "yyyy-MM-dd");
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(format);
			queryList.add(calendar1);
			
			sql += " and sendTime >= ?";
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getEndTime())) {
			Date format = TeeDateUtil.format(queryModel.getEndTime(), "yyyy-MM-dd");
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(format);
			queryList.add(calendar2);
			sql += " and sendTime <= ?";
		}
		sql += " order by sendTime desc";
		

		
		//返回的Model
		List<TeeFilePassRoundModel> filePassRoundModelList = new ArrayList<TeeFilePassRoundModel>();
		Object[] array = queryList.toArray();
		List<TeeFilePassRound> list=filePassRoundDao.pageFind(sql, m.getFirstResult(), m.getRows(), array);
		
		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				TeeFilePassRoundModel filePassRoundModel = new TeeFilePassRoundModel();
				filePassRoundModel.setId(list.get(i).getId());
				filePassRoundModel.setSendTime(TeeDateUtil.format(list.get(i).getSendTime().getTime(),"yyyy-MM-dd HH:mm:ss"));

				
				//文件				
				List<TeeAttachmentModel> attachmentsModels = attachmentService.getAttacheModels("filePassRound", list.get(i).getId());
				filePassRoundModel.setAttachmentsModels(attachmentsModels);
				
				filePassRoundModel.setTitle(list.get(i).getTitle());
				filePassRoundModelList.add(filePassRoundModel);
				
				
			}
			
		}
		
		Long l = filePassRoundDao.count("select count(id) "+sql, array);
		json.setRows(filePassRoundModelList);
		json.setTotal(l);
		return json;
	}
	
	
	
	
	
	
	
}
