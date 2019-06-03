package com.beidasoft.xzzf.punish.common.service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishOperation;
import com.beidasoft.xzzf.punish.common.dao.PunishOperationDao;
import com.beidasoft.xzzf.punish.common.model.PunishOperationModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class PunishOperationService extends TeeBaseService {
	
	@Autowired
	private PunishOperationDao punishOperationDao;
	
	
	/**
	 * 保存案件操作记录表(日志)
	 * @param punishOperationModel
	 * @param request
	 */
	public void save(PunishOperationModel punishOperationModel, HttpServletRequest request) {
		
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		PunishOperation punishOperation = new PunishOperation();
		BeanUtils.copyProperties(punishOperationModel, punishOperation);
		//处理操作人信息
		punishOperation.setPunishOperationId(UUID.randomUUID().toString());
		punishOperation.setOpeartionPerson(user.getUuid());
		punishOperation.setOpeartionPersonName(user.getUserName());
		punishOperation.setOpeartionTime(Calendar.getInstance().getTime());
		
		punishOperationDao.save(punishOperation);
	}
	
	/**
	 * 分页查询符合条件的记录
	 * @param punishOperationModel
	 * @param dataGridModel
	 * @return
	 */
	public List<PunishOperation> getPunishOperations(PunishOperationModel punishOperationModel, 
			TeeDataGridModel dataGridModel) {
		
		return punishOperationDao.getPunishOperations(punishOperationModel, dataGridModel);
	}
	
	
	
	/**
	 * 获得总记录数
	 * @return
	 */
	public long getTotal() {
		return punishOperationDao.getTotal();
	}
	
	/**
	 * 获得符合条件的总记录数
	 * @param punishOperationModel
	 * @return
	 */
	public long getTotal(PunishOperationModel punishOperationModel) {
		return punishOperationDao.getTotal(punishOperationModel);
	}
	
	
}
