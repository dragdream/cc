package com.tianee.oa.core.base.filepassround.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;











import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incors.plaf.alloy.o;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.base.filepassround.bean.TeeFilePassRoundRecord;
import com.tianee.oa.core.base.filepassround.dao.TeeFilePassRoundRecordDao;
import com.tianee.oa.core.base.filepassround.model.TeeFilePassRoundModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeFilePassRoundRecordService extends TeeBaseService{
	@Autowired
	private TeeFilePassRoundRecordDao filePassRoundRecordDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	
	public void save(TeeFilePassRoundRecord filePassRoundRecord){
		filePassRoundRecordDao.save(filePassRoundRecord);
	}
	
	public TeeEasyuiDataGridJson findByPassroundId(String id,TeeDataGridModel m) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		List<TeeFilePassRoundRecord> list = filePassRoundRecordDao.pageFind("from TeeFilePassRoundRecord where filePassRound.id ='"+id+"'", m.getFirstResult(),m.getRows(), null);
		List<TeeFilePassRoundModel> filePassRoundModelList = new ArrayList<TeeFilePassRoundModel>();	
		
		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				TeeFilePassRoundModel model = new TeeFilePassRoundModel();
				
				model.setId(list.get(i).getId());
				model.setConId(list.get(i).getConId());
				model.setState(list.get(i).getState());
				if (list.get(i).getReadTime() != null) {
					model.setReadTime(TeeDateUtil.format(list.get(i).getReadTime().getTime(),"yyyy-MM-dd HH:mm:ss"));
				}

				List<TeePerson> persons = personDao.find("from TeePerson where uuid="+list.get(i).getConId(), null);
				if (persons != null) {
					model.setConName(persons.get(0).getUserName());
				}
				
				filePassRoundModelList.add(model);
				
			}
			
		}
		Long l = filePassRoundRecordDao.count("select count(id) from TeeFilePassRoundRecord where filePassRound.id ='"+id+"'", null);
		json.setRows(filePassRoundModelList);
		json.setTotal(l);
		
		return json;
		
		
	}
	
	@Transactional(readOnly=true)
	public TeeEasyuiDataGridJson findByConId(Integer id,TeeDataGridModel m,TeeFilePassRoundModel queryModel) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		//条件查询
		String sql = "from TeeFilePassRoundRecord where conId="+id;
		List<Calendar> queryList = new ArrayList<Calendar>();
		
		if(!TeeUtility.isNullorEmpty(queryModel.getTitle())){
			sql += "and filePassRound.id in(select id from TeeFilePassRound where title like '%"+queryModel.getTitle()+"%')";
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getStartTime())) {
			Date format = TeeDateUtil.format(queryModel.getStartTime(), "yyyy-MM-dd");
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(format);
			queryList.add(calendar1);
			sql += " and readTime >= ?";
		}
		if (!TeeUtility.isNullorEmpty(queryModel.getEndTime())) {
			Date format = TeeDateUtil.format(queryModel.getEndTime(), "yyyy-MM-dd");
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(format);
			queryList.add(calendar2);
			sql += " and readTime <= ?";
		}
		
		sql += " order by readTime desc";
		
		
		//查询输出
		Object[] array = queryList.toArray();
		List<TeeFilePassRoundRecord> list = filePassRoundRecordDao.pageFind(sql, m.getFirstResult(),m.getRows(), array);
		
		List<TeeFilePassRoundModel> filePassRoundModelList = new ArrayList<TeeFilePassRoundModel>();		
		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				TeeFilePassRoundModel model = new TeeFilePassRoundModel();
				
				//从表
				model.setId(list.get(i).getId());
				model.setState(list.get(i).getState());
				if (list.get(i).getReadTime() != null) {
					model.setReadTime(TeeDateUtil.format(list.get(i).getReadTime().getTime(),"yyyy-MM-dd HH:mm:ss"));
				}
				
				//主表
				model.setPassroundId(list.get(i).getFilePassRound().getId());
				model.setProId(list.get(i).getFilePassRound().getProId());
				model.setDepId(list.get(i).getFilePassRound().getDepId());
				model.setTitle(list.get(i).getFilePassRound().getTitle());
				model.setSendTime(TeeDateUtil.format(list.get(i).getFilePassRound().getSendTime().getTime(),"yyyy-MM-dd HH:mm:ss"));
				
				//其他信息
				List<TeePerson> persons = personDao.find("from TeePerson where uuid="+list.get(i).getFilePassRound().getProId(), null);
				if (persons != null) {
					model.setProName(persons.get(0).getUserName());
				}
				
				List<TeeAttachmentModel> attachmentsModels = attachmentService.getAttacheModels("filePassRound", list.get(i).getFilePassRound().getId());
				model.setAttachmentsModels(attachmentsModels);
				filePassRoundModelList.add(model);
				
				
			}
			
		}
		
		Long l = filePassRoundRecordDao.count("select count(id) "+sql, array);
		json.setRows(filePassRoundModelList);
		json.setTotal(l);
		
		
		return json;
		
		
	}
	
	public void updateReadState(String id){
		List<TeeFilePassRoundRecord> filePassRoundRecords = filePassRoundRecordDao.find("from TeeFilePassRoundRecord where id='"+id+"'", null);
		
		TeeFilePassRoundRecord filePassRoundRecord = new TeeFilePassRoundRecord();
		if (filePassRoundRecords != null) {
			filePassRoundRecord = filePassRoundRecords.get(0);
		}
		
		filePassRoundRecord.setState(1);
		filePassRoundRecord.setReadTime(Calendar.getInstance());

		filePassRoundRecordDao.update(filePassRoundRecord);
		
	}
	
	
	
	
	
}
