package com.tianee.oa.quartzjob;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.phoneSms.bean.TeeSmsSendPhone;
import com.tianee.oa.core.phoneSms.dao.TeeSmsSendPhoneDao;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeWorkDateTimer extends TeeBaseService{
	
	@Autowired
	private  TeePersonDao personDao;
	
	@Autowired
	private TeeSmsSender smsSender;
	
	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;
	
	@Autowired
	private TeeSmsSendPhoneDao sendPhoneDao;
	
	public void doTimmer(){

	}
	/**
	 * 提醒请假人
	 * */
	public void qingJiaPerson(){
	    Map requestData = new HashMap();
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		String dataStr=TeeDateUtil.format(cl.getTime(), "yyyy-MM-dd");
		//请假
		List<Map<String,Object>> bySql = simpleDaoSupport.getBySql("select RUN_ID,DATA_54,EXTRA_8 from tee_f_r_d_1032 where DATA_54=? and DATA_35=''", new Object[]{dataStr});
		if(bySql!=null && bySql.size()>0){
			for(int i=0;i<bySql.size();i++){
				Map<String,Object> m=bySql.get(i);
			    //String workDate=TeeStringUtil.getString(m.get("DATA_54"));
			    int userId = TeeStringUtil.getInteger(m.get("EXTRA_8"), 0);
			    int runId = TeeStringUtil.getInteger(m.get("RUN_ID"), 0);
			    Map map = simpleDaoSupport.executeNativeUnique("select max(SID) as sid from FLOW_RUN_PRCS where RUN_ID=?", new Object[]{runId});
			    TeePerson person = personDao.get(userId);
			    requestData.put("moduleNo", "006");
				requestData.put("userListIds", userId);
				String str=person.getUserName()+"同志,您有一个请假流程需要您及时销假！";
				requestData.put("content", str);
				requestData.put("remindUrl", "/workflow/toFlowRun.action?runId="
						+ runId + "&frpSid=" + TeeStringUtil.getInteger(map.get("sid"), 0));
				smsSender.sendSms(requestData, null);
				addSmsPhone(person,str,cl);
			}
		}
		//代请假
		List<Map<String,Object>> bySql2 = simpleDaoSupport.getBySql("select RUN_ID,DATA_57,EXTRA_48 from tee_f_r_d_1051 where DATA_54=? and DATA_35=''", new Object[]{dataStr});
		if(bySql2!=null && bySql2.size()>0){
			for(int i=0;i<bySql2.size();i++){
				Map<String,Object> m=bySql2.get(i);
			    //String workDate=TeeStringUtil.getString(m.get("DATA_57"));
			    int userId = TeeStringUtil.getInteger(m.get("EXTRA_48"), 0);
			    int runId = TeeStringUtil.getInteger(m.get("RUN_ID"), 0);
			    Map map = simpleDaoSupport.executeNativeUnique("select max(SID) as sid from FLOW_RUN_PRCS where RUN_ID=?", new Object[]{runId});
			    TeePerson person = personDao.get(userId);
			    requestData.put("moduleNo", "006");
				requestData.put("userListIds", userId);
				String str=person.getUserName()+"同志,您有一个请假流程需要您及时销假！";
				requestData.put("content", str);
				requestData.put("remindUrl", "/workflow/toFlowRun.action?runId="
						+ runId + "&frpSid=" + TeeStringUtil.getInteger(map.get("sid"), 0));
				smsSender.sendSms(requestData, null);
				addSmsPhone(person,str,cl);
			}
		}
	}
	/**
	 * 给请假人的部门领导发信息
	 * */
	public void personDeptLd(){
	    Map requestData = new HashMap();
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		cl.add(Calendar.DATE, -1);//当前时间的前一天
		String dataStr=TeeDateUtil.format(cl.getTime(), "yyyy-MM-dd");
		//请假
		List<Map<String,Object>> bySql = simpleDaoSupport.getBySql("select RUN_ID,DATA_54,EXTRA_8 from tee_f_r_d_1032 where DATA_54=? and DATA_35=''", new Object[]{dataStr});
		if(bySql!=null && bySql.size()>0){
			for(int i=0;i<bySql.size();i++){
				Map<String,Object> m=bySql.get(i);
			    int userId = TeeStringUtil.getInteger(m.get("EXTRA_8"), 0);
			    TeePerson person = personDao.get(userId);
				List<TeePerson> find = simpleDaoSupport.find("from TeePerson where userRole.roleName=? and dept.uuid=?", new Object[]{"部门负责人",person.getDept().getUuid()});
                if(find!=null && find.size()>0){
                	TeePerson person2 = find.get(0);
                	requestData.put("moduleNo", "006");
    				requestData.put("userListIds", person2.getUuid());
    				String str="您部门中的"+person.getUserName()+"同志，昨天有一个请假流程需要销假，请及时销假！";
    				requestData.put("content", str);
    				requestData.put("remindUrl", "");
    				smsSender.sendSms(requestData, null);
    				addSmsPhone(person2,str,cl);
                }
			}
		}
		//代请假
		List<Map<String,Object>> bySql2 = simpleDaoSupport.getBySql("select RUN_ID,DATA_57,EXTRA_48 from tee_f_r_d_1051 where DATA_54=? and DATA_35=''", new Object[]{dataStr});
		if(bySql2!=null && bySql2.size()>0){
			for(int i=0;i<bySql2.size();i++){
				Map<String,Object> m=bySql2.get(i);
			    //String workDate=TeeStringUtil.getString(m.get("DATA_57"));
			    int userId = TeeStringUtil.getInteger(m.get("EXTRA_48"), 0);
			    int runId = TeeStringUtil.getInteger(m.get("RUN_ID"), 0);
			    TeePerson person = personDao.get(userId);
			    List<TeePerson> find = simpleDaoSupport.find("from TeePerson where userRole.roleName=? and dept.uuid=?", new Object[]{"部门负责人",person.getDept().getUuid()});
                if(find!=null && find.size()>0){
                	TeePerson person2 = find.get(0);
                	requestData.put("moduleNo", "006");
    				requestData.put("userListIds", person2.getUuid());
    				String str="您部门中的"+person.getUserName()+"同志，昨天有一个请假流程需要销假，请及时销假！";
    				requestData.put("content", str);
    				requestData.put("remindUrl", "");
    				smsSender.sendSms(requestData, null);
    				addSmsPhone(person2,str,cl);
                }
			}
		}
	}

	public void addSmsPhone(TeePerson user,String desc,Calendar cl){
		String userPhone = null;
		userPhone=user.getMobilNo();
		if(!TeeUtility.isNullorEmpty(userPhone)){
			//保存手机发送记录（本地）
			TeeSmsSendPhone sendPhone = new TeeSmsSendPhone();
			try {
				sendPhone.setFromId(0);
				sendPhone.setFromName("销假提醒");
				sendPhone.setSendTime(cl);
				sendPhone.setSendFlag(0);
				sendPhone.setContent(desc);
				sendPhone.setToId(user.getUuid());
				sendPhone.setToName(user.getUserName());
				sendPhone.setPhone(userPhone);
				sendPhoneDao.addSendPhoneInfo(sendPhone);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
}
