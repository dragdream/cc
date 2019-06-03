package com.tianee.oa.core.general.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.bean.TeeMysqlBackup;
import com.tianee.oa.core.general.dao.TeeMysqlBackupDao;
import com.tianee.oa.core.general.model.TeeMysqlBackupModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

@Service
public class TeeMysqlBackupService{
	@Autowired
	TeeMysqlBackupDao backupDao;
	
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, Map requestDatas) {
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		List<TeeMysqlBackup>  list = backupDao.getBackupList(dm,requestDatas);
		List rows = new ArrayList();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long total = backupDao.getTotal(requestDatas);
		for(TeeMysqlBackup log:list){
			TeeMysqlBackupModel model = new TeeMysqlBackupModel();
			BeanUtils.copyProperties(log, model);
			model.setTimeDesc(sf.format(log.getTime().getTime()));
			rows.add(model);
		}
		datagird.setRows(rows);
		datagird.setTotal(total);
		return datagird;
	}

	public TeeMysqlBackup getById(int sid) {
		return backupDao.get(sid);
	}
	
}