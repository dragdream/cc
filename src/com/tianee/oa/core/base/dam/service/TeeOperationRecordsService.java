package com.tianee.oa.core.base.dam.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.dam.bean.TeeOperationRecords;
import com.tianee.oa.core.base.dam.model.TeeOperationRecordsModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeOperationRecordsService extends TeeBaseService{

	/**
	 * 根据档案主键  获取日志列表
	 * @param request
	 * @return
	 */
	public TeeJson getListByFileId(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int fileId=TeeStringUtil.getInteger(request.getParameter("fileId"),0);
		List<TeeOperationRecords> recordList=simpleDaoSupport.executeQuery(" from TeeOperationRecords  where file.sid=? ", new Object[]{fileId});
		List<TeeOperationRecordsModel> modelList=new ArrayList<TeeOperationRecordsModel>();
		TeeOperationRecordsModel model=null;
		for (TeeOperationRecords r : recordList) {
			model=parseToModel(r);
			modelList.add(model);
		}
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}

	
	/**
	 * 实体类转换成model
	 * @param r
	 * @return
	 */
	private TeeOperationRecordsModel parseToModel(TeeOperationRecords r) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TeeOperationRecordsModel model=new TeeOperationRecordsModel();
		BeanUtils.copyProperties(r, model);
		if(r.getFile()!=null){
			model.setFileId(r.getFile().getSid());
			model.setFileName(r.getFile().getTitle());
		}
		if(r.getOperTime()!=null){
			model.setOperTimeStr(sdf.format(r.getOperTime().getTime()));
		}
		
		if(r.getOperUser()!=null){
			model.setOperUserId(r.getOperUser().getUuid());
			model.setOperUserName(r.getOperUser().getUserName());
		}
		return model;
	}

	
}
