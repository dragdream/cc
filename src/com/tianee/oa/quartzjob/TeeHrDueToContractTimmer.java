package com.tianee.oa.quartzjob;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.pm.bean.TeeHumanContract;
import com.tianee.oa.core.base.pm.dao.TeeHumanContractDao;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeHrDueToContractTimmer extends TeeBaseService{
	@Autowired
	TeeHumanContractDao contractDao;
	
	@Autowired
	private TeeSmsManager smsManager;
	
	public void doTimmer(){
		try {
			if(TeeSysProps.getProps()==null){
				return;
			}
			
			String hrIds = TeeSysProps.getString("HR_USER");
			List<TeeHumanContract> models = contractDao.queryDueToContract();
			if(null!=models && models.size()>0){
				for(TeeHumanContract m :models){
					if(m.getRenewDate()!=null){
						continue;
					}else{
						Map requestData = new HashMap();
						requestData.put("content", m.getConCode()+"合同即将到期，请及时处理！");
						requestData.put("userListIds",hrIds);
						requestData.put("moduleNo", "033" );
						requestData.put("remindUrl", "/system/core/base/pm/archivesManage/ht_detail.jsp?sid="+m.getSid());
						Calendar cl= Calendar.getInstance();
						smsManager.sendSms(requestData, null);
						m.setLastRemindDate(cl);
						contractDao.update(m);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
