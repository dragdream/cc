package com.tianee.oa.core.base.fileNetdisk.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileOptRecord;
import com.tianee.oa.core.base.fileNetdisk.model.TeeFileOptRecordModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
@Service
public class TeeFileOptRecordService extends TeeBaseService{

	
	/**
	 * 根据网盘id  获取网盘的操作历史记录
	 * @param sid
	 * @return
	 */
	public TeeEasyuiDataGridJson getHistorys(int sid) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql="from TeeFileOptRecord record where record.fileId=?";
	    List<TeeFileOptRecord> recordList=simpleDaoSupport.executeQuery(hql, new Object[]{sid});
	    Long count = simpleDaoSupport.count("select count(*) "+hql, new Object[]{sid});
	    List<TeeFileOptRecordModel> modelList=new ArrayList<TeeFileOptRecordModel>();
	    TeeFileOptRecordModel model=null;
	    for (TeeFileOptRecord record : recordList) {
	    	model=new TeeFileOptRecordModel();
	    	BeanUtils.copyProperties(record, model);
	    	Calendar c=record.getCreateTime();
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateStr = sdf.format(c.getTime());
            model.setCreateTimeStr(dateStr);
            modelList.add(model);
		}
	    dataGridJson.setRows(modelList);
	    dataGridJson.setTotal(count);
	    return dataGridJson;
	}



}
