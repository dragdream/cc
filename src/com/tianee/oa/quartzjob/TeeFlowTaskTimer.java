package com.tianee.oa.quartzjob;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.dao.TeeSmsDao;
import com.tianee.oa.core.general.model.TeeSmsModel;
import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowTypeDao;
import com.tianee.oa.core.workflow.workmanage.service.TeeFlowTimerTaskServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeSmsConst;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.servlet.TeeSessionListener;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;


@Service
public class TeeFlowTaskTimer extends TeeBaseService{

	@Autowired
	TeeSimpleDaoSupport simpleDaoSupport;
	@Autowired
	TeeFlowTimerTaskServiceInterface flowTimerTaskService;
	
	public void doTimmer(){
		try{
			if(TeeSysProps.getProps()==null){
				return;
			}
			
			flowTimerTaskService.autoProcessingExecutableTask();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void setSimpleDaoSupport(TeeSimpleDaoSupport simpleDaoSupport) {
		this.simpleDaoSupport = simpleDaoSupport;
	}
	  
}
