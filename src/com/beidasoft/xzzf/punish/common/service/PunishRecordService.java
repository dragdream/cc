package com.beidasoft.xzzf.punish.common.service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishRecord;
import com.beidasoft.xzzf.punish.common.dao.PunishRecordDao;
import com.beidasoft.xzzf.punish.common.model.PunishRecordModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class PunishRecordService extends TeeBaseService {
	@Autowired
	private PunishRecordDao punishRecordDao;
	
	
	/**
	 * 保存案件操作记录表(日志)
	 * @param punishRecordModel
	 * @param request
	 */
	public void save(PunishRecordModel punishRecordModel, HttpServletRequest request) {
		
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		PunishRecord punishRecord = new PunishRecord();
		BeanUtils.copyProperties(punishRecordModel, punishRecord);
		//处理操作人信息
		punishRecord.setPunishRecordId(UUID.randomUUID().toString());
		punishRecord.setOpeartionPerson(user.getUuid());
		punishRecord.setOpeartionPersonName(user.getUserName());
		punishRecord.setOpeartionTime(Calendar.getInstance().getTime());
		
		punishRecordDao.save(punishRecord);
	}
	
	/**
	 * 分页查询符合条件的记录（根据id和环节名）
	 * @param PunishRecordModel
	 * @param dataGridModel
	 * @return
	 */
	public List<PunishRecord> getPunishRecordsById(PunishRecordModel punishRecordModel, 
			TeeDataGridModel dataGridModel) {
		
		return punishRecordDao.getPunishRecordsById(punishRecordModel, dataGridModel);
	}
	
	/**
	 * 获得符合条件的总记录数（根据Id）
	 * @param PunishRecordModel
	 * @return
	 */
	public long getTotalById(PunishRecordModel punishRecordModel) {
		return punishRecordDao.getTotalById(punishRecordModel);
	}
}
