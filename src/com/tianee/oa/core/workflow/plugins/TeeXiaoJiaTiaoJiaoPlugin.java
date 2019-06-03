package com.tianee.oa.core.workflow.plugins;

import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeBeanFactory;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 销假转交条件判断
 * */
public class TeeXiaoJiaTiaoJiaoPlugin extends TeeWfPlugin {
	@Override
	public TeeJsonProxy beforeTurnnext() {
		return null;
	}

	@Override
	public void afterTurnnext() {
//		JSONObject json=new JSONObject();
//		int runId = flowRunProxy.getRunId(); //获取流程ID
//		String qjType = this.flowRunDatas.get(formItemIdentities.get("请假类别"));
//		String bTimeJ = this.flowRunDatas.get(formItemIdentities.get("实际开始时间"));
//		String bTimeJ2 = this.flowRunDatas.get(formItemIdentities.get("实际开时间"));
//		String eTimeJ = this.flowRunDatas.get(formItemIdentities.get("实际结束时间"));
//		String eTimeJ2 = this.flowRunDatas.get(formItemIdentities.get("实际结时间"));
//		bTimeJ2=bTimeJ2.replace("时", "");
//		eTimeJ2=eTimeJ2.replace("时", "");
//		json.put("KQ0401", bTimeJ+" "+bTimeJ2+":00:00");  //开始日期
//		json.put("KQ0401", eTimeJ+" "+eTimeJ2+":00:00");  //结束日期
//		json.put("KQID", qjType);		//假期类别
//		json.put("KQ0434", runId);		//oa流程ID
//		//调接口
//		//返回值
//		//"success":0,"msg":"",data:{
////    			"KQ0433":"2019-01-11 09:00:00",//实际开始时间
////    			"KQ0417":"2019-01-13 18:00:00",//实际结束时间 
////    			"KQ0430":"0",				//是否使用上年时间
////    			"KQ0431":"3",				//使用本年年假时间
////    			"KQ0432":"0",				//本年使用上年年假时间
////    			"KQ0434":"123123123123"		//OA考勤记录ID
////    	}
//		String joStr="";
//		JSONObject jsonObject = JSONObject.fromObject(joStr);
//		int success = TeeStringUtil.getInteger(jsonObject.get("success"),0);
//		if(success==0){
//			JSONObject obj = JSONObject.fromObject(jsonObject.get("data"));
//            int lastNum=TeeStringUtil.getInteger(obj.get("KQ0432"), 0);
//            int bNum=TeeStringUtil.getInteger(obj.get("KQ0431"), 0);
//            int rId=TeeStringUtil.getInteger(obj.get("KQ0434"), 0);
//			String qUser = this.flowRunDatas.get(formItemIdentities.get("申请人"));
//			TeePersonDao personDao = 
//		    		(TeePersonDao) TeeBeanFactory.getBean("personDao");
//		    int beginUserId = this.flowRunProxy.getBeginUserUuid();//流程发起人id
//			TeePerson person = personDao.get(beginUserId);
//			TeeSimpleDaoSupport simpleDaoSupport = 
//					(TeeSimpleDaoSupport) TeeBeanFactory.getBean("simpleDaoSupport");
//			if(qUser.equals(person.getUserName())){//请假
//				simpleDaoSupport.executeNativeUpdate("update tee_f_r_d_1032 set DATA_51=?,DATA_52=? where RUN_ID=?", new Object[]{lastNum,bNum,rId});
//			}else{//代请假
//				simpleDaoSupport.executeNativeUpdate("update tee_f_r_d_1051 set DATA_54=?,DATA_55=? where RUN_ID=?", new Object[]{lastNum,bNum,rId});
//			}
//		}
	}

	@Override
	public TeeJsonProxy beforeSave() {
		TeeJsonProxy jsonProxy=new TeeJsonProxy();
//		this.getFlowRunDatas().put("DATA_49", userRole.getRoleName());
		//计划时间
//		String bTime = this.flowRunDatas.get(formItemIdentities.get("开始时间"));
//		String bTime2 = this.flowRunDatas.get(formItemIdentities.get("开时间"));
//		String eTime = this.flowRunDatas.get(formItemIdentities.get("结束时间"));
//		String eTime2 = this.flowRunDatas.get(formItemIdentities.get("结时间"));
		String bTime = "";
		String bTime2 = "";
		String eTime = "";
		String eTime2 = "";
		String workTime="";
		String qUser="";
		int runId = flowRunProxy.getRunId(); //获取流程ID
		TeeSimpleDaoSupport simpleDaoSupport = 
				(TeeSimpleDaoSupport) TeeBeanFactory.getBean("teeSimpleDaoSupport");
		TeeFlowRun fr = (TeeFlowRun)simpleDaoSupport.get(TeeFlowRun.class,runId);
		int sid = fr.getFlowType().getSid();
		
		if(sid==1032){
			Map map = simpleDaoSupport.executeNativeUnique("select to_char(DATA_48) as DATA_48,to_char(DATA_8) as DATA_8,to_char(DATA_9) as DATA_9,to_char(DATA_10) as DATA_10,to_char(DATA_41) as DATA_41,to_char(DATA_42) as DATA_42,to_char(DATA_54) as DATA_54,to_char(DATA_57) as DATA_57 from tee_f_r_d_1032 where RUN_ID=?", new Object[]{runId});
			bTime=TeeStringUtil.getString(map.get("DATA_9"));
			bTime2=TeeStringUtil.getString(map.get("DATA_41"));
			eTime=TeeStringUtil.getString(map.get("DATA_10"));
			eTime2=TeeStringUtil.getString(map.get("DATA_42"));
			workTime=TeeStringUtil.getString(map.get("DATA_54"));
			qUser=TeeStringUtil.getString(map.get("DATA_8"));
		}else{
			Map map = simpleDaoSupport.executeNativeUnique("select to_char(DATA_48) as DATA_48,to_char(DATA_9) as DATA_9,to_char(DATA_10) as DATA_10,to_char(DATA_41) as DATA_41,to_char(DATA_42) as DATA_42,to_char(DATA_54) as DATA_54,to_char(DATA_57) as DATA_57 from tee_f_r_d_1051 where RUN_ID=?", new Object[]{runId});
			bTime=TeeStringUtil.getString(map.get("DATA_9"));
			bTime2=TeeStringUtil.getString(map.get("DATA_41"));
			eTime=TeeStringUtil.getString(map.get("DATA_10"));
			eTime2=TeeStringUtil.getString(map.get("DATA_42"));
			workTime=TeeStringUtil.getString(map.get("DATA_57"));
			qUser=TeeStringUtil.getString(map.get("DATA_48"));
		}
		
		//System.out.println(TeeStringUtil.getString(map.get("DATA_9"), "yyyy-MM-dd"));
		//实际时间
		String bTimeJ = this.flowRunDatas.get(formItemIdentities.get("实际开始时间"));
		String bTimeJ2 = this.flowRunDatas.get(formItemIdentities.get("实际开时间"));
		String eTimeJ = this.flowRunDatas.get(formItemIdentities.get("实际结束时间"));
		String eTimeJ2 = this.flowRunDatas.get(formItemIdentities.get("实际结时间"));
		
		//String workTime = this.flowRunDatas.get(formItemIdentities.get("工作日"));
        Date date=new Date();
        String workTime2 = TeeDateUtil.format(date, "yyyy-MM-dd");//当前日期
		//String qUser = this.flowRunDatas.get(formItemIdentities.get("申请人"));
		TeePersonDao personDao = 
	    		(TeePersonDao) TeeBeanFactory.getBean("personDao");
	    int beginUserId = this.flowRunProxy.getBeginUserUuid();//流程发起人id
		TeePerson person = personDao.get(beginUserId);
        if(bTime.equals(bTimeJ) && bTime2.equals(bTimeJ2) && eTime.equals(eTimeJ) && eTime2.equals(eTimeJ2) && workTime.equals(workTime2)){
        	if(qUser.equals(person.getUserName())){//请假
            	this.getFlowRunDatas().put("DATA_58", "true");
        	}else{//代请假
            	this.getFlowRunDatas().put("DATA_61", "true");
        	}
        }else{
        	if(qUser.equals(person.getUserName())){//请假
            	this.getFlowRunDatas().put("DATA_58", "false");
        	}else{//代请假
            	this.getFlowRunDatas().put("DATA_61", "false");
        	}
        }
		jsonProxy.setRtState(true);
		return jsonProxy;
	}

	@Override
	public void afterSave() {
		
		
	}

	@Override
	public void preTurnNextFilter(Map preTurnNextData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String afterRendered() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void goBack(String prcsName, int prcsId, String content) {
		// TODO Auto-generated method stub
		
	}

	

}
