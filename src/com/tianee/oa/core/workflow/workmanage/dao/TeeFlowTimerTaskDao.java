package com.tianee.oa.core.workflow.workmanage.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowRule;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowTimerTask;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class TeeFlowTimerTaskDao extends TeeBaseDao<TeeFlowTimerTask>{
	
	public List<TeeFlowTimerTask> list(){
		return super.find("from TeeFlowTimerTask ft order by ft.sid asc", null);
	}
	
	/**
	 * 获取可执行的任务
	 * @return
	 */
	public List<TeeFlowTimerTask> listExecutableTask(){
		return super.find("from TeeFlowTimerTask ftt where (ftt.type=1 and ftt.remindStamp<="+new Date().getTime()+" and ftt.lastTime is null) " +
				"or (ftt.type in (2,3,4,5) and ftt.remindStamp<="+new Date().getTime()+")", null);
	}
	
}
