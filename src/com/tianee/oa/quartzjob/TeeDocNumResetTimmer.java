package com.tianee.oa.quartzjob;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetsInfo;
import com.tianee.oa.core.base.fixedAssets.service.TeeFixedAssetsInfoService;
import com.tianee.oa.core.workflow.flowrun.bean.TeeDocNum;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;

@Service
public class TeeDocNumResetTimmer extends TeeBaseService{
	
	public void doTimmer(){
		if(TeeSysProps.getProps()==null){
			return;
		}
		
		Calendar c = Calendar.getInstance();
		int currYear = c.get(Calendar.YEAR);
		List<TeeDocNum> docNumList = simpleDaoSupport.find("from TeeDocNum where resetStamp!=?", new Object[]{currYear});
		for(TeeDocNum docNum:docNumList){
			if((currYear-docNum.getResetStamp())%docNum.getResetYear()==0){
				docNum.setCurrNum(0);
				docNum.setResetStamp(currYear);
			}
		}
	}
}
