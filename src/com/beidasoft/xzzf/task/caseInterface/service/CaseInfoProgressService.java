package com.beidasoft.xzzf.task.caseInterface.service;

import java.io.Serializable;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.beidasoft.xzzf.task.caseInterface.httpmodel.CaseJson;
import com.beidasoft.xzzf.task.taskAppointed.bean.CaseAppointedInfo;
import com.beidasoft.xzzf.task.taskAppointed.dao.CaseAppointedInfoDao;
import com.tianee.webframe.service.TeeBaseService;
 
@Service
public class CaseInfoProgressService extends TeeBaseService{

	
	@Autowired
	private CaseAppointedInfoDao caseAppointedInfoDao;
	
	
	
	
	
	
	/**
	 * 
	 * @param caseAppointed
	 * @return
	 */
	public CaseJson NewCaseApprove(CaseAppointedInfo caseAppointed){
		CaseJson cJson = new CaseJson();
		//判断添加的任务是否已经存在
		CaseAppointedInfo caseAppointedInfo = caseAppointedInfoDao.getByTaskRecId(caseAppointed.getTaskRecId());
		if(null== caseAppointedInfo ||("").equals(caseAppointedInfo)){
//			//Organization organization = OrganizationDao.getByOrganizationId(organizationId);
//			String name = "";// =organization.getName();
//			if(name.equals("")||(!name.equals(caseAppointed.getTaskSendPersonName()))){
//				return msg+="送审人唯一编号与姓名错误，请检查";
//			}
			
			// 2. 10案件办理-立案任务，20执法检查-检查任务
			if(caseAppointed.getTaskType()!=10 ||caseAppointed.getTaskType()!=20){  
				cJson.setRtMsg("案件类型错误，请检查。");
			}
			 
//			// 3.校验案件来源编号与名称是否匹配
//			TaskRecBasicInfo listBask = taskRecBasicDao.getByRec(caseAppointed.getTaskRec());
//			if(listBask==null||(listBask.equals(""))||(!listBask.getTaskRecName().equals(caseAppointed.getTaskName()))){
//				cJson.setRtMsg("任务来源错误");
//			}
			caseAppointed.setCreateTime(Calendar.getInstance());
			Serializable saveResult = caseAppointedInfoDao.save(caseAppointed);
			int flag = (Integer) saveResult;
			if(flag>0){
				cJson.setRtMsg("任务保存成功");
				cJson.setRtState(true);
			}else{
				cJson.setRtMsg("任务保存失败");
			}
		}else{
			cJson.setRtMsg("保存的任务已经存在");
		}
		return cJson;
	}
	
}
