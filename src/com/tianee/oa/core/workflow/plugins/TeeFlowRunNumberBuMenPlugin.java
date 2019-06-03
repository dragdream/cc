package com.tianee.oa.core.workflow.plugins;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.springframework.beans.factory.annotation.Autowired;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFeedBack;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunCtrlFeedback;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunCtrlFeedBackDao;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunDao;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunPrcsDao;
import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeBeanFactory;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 如果部门意见可以直接结束，就把数据推给人事系统
 * */
public class TeeFlowRunNumberBuMenPlugin extends TeeWfPlugin {
	
	@Override
	public TeeJsonProxy beforeTurnnext() {
		return null;
	}

	/**
	 * 转交后推数据
	 * */
	@Override
	public void afterTurnnext() {
		String qjType = this.flowRunDatas.get(formItemIdentities.get("请假类别"));
		String zg = this.flowRunDatas.get(formItemIdentities.get("共计"));
		double double1 = TeeStringUtil.getDouble(zg, 0.0);
		String prcsName = flowRunProxy.getPrcsName();//当前步骤名称
		if("请假申请".equals(prcsName) || ("部门意见".equals(prcsName) && ("事假".equals(qjType) || "病假".equals(qjType)) && double1<3)){
			JSONObject object=new JSONObject();
		    int runId = flowRunProxy.getRunId(); //获取流程ID 5372
		    TeeSimpleDaoSupport simpleDaoSupport = 
					(TeeSimpleDaoSupport) TeeBeanFactory.getBean("teeSimpleDaoSupport");
		    TeePersonDao personDao = 
		    		(TeePersonDao) TeeBeanFactory.getBean("personDao");
		    int beginUserId = this.flowRunProxy.getBeginUserUuid();//流程发起人id
			TeePerson person = personDao.get(beginUserId);
			TeeFlowRunPrcsDao teeFlowRunPrcsDao = 
					(TeeFlowRunPrcsDao) TeeBeanFactory.getBean("teeFlowRunPrcsDao");
			TeeFlowRunCtrlFeedBackDao teeFlowRunCtrlFeedBackDao = 
					(TeeFlowRunCtrlFeedBackDao) TeeBeanFactory.getBean("teeFlowRunCtrlFeedBackDao");
			List<TeeFlowRunPrcs> find = teeFlowRunPrcsDao.find("from TeeFlowRunPrcs where flowRun.runId=? order by sid desc", new Object[]{runId});
			if("请假申请".equals(prcsName)){
				object.put("flag", "0");
			}else{
				object.put("flag", "2");
			}
			object.put("userId", person.getUserId());//请假人用户名
			object.put("KQ0434", runId);//流程id
			String beginTime = this.flowRunDatas.get(formItemIdentities.get("开始时间"));
			String bTime = this.flowRunDatas.get(formItemIdentities.get("开时间"));
			String endTime = this.flowRunDatas.get(formItemIdentities.get("结束时间"));
			String eTime = this.flowRunDatas.get(formItemIdentities.get("结时间"));
			if("9时".equals(bTime)){
				bTime="0"+bTime;
			}
			object.put("KQ0401", beginTime+" "+bTime.replace("时", "")+":00:00");//开始时间
			object.put("KQ0403", endTime+" "+eTime.replace("时", "")+":00:00");//结束时间
			object.put("KQ0405", qjType);//请假类别
			object.put("KQ0406", zg);//共计
			String qwd = this.flowRunDatas.get(formItemIdentities.get("前往地"));
			if("境外".equals(qwd)){
				object.put("KQ0408", 1);//前往地
			}else{
				object.put("KQ0408", 0);//前往地
			}
			String lJiaQi = this.flowRunDatas.get(formItemIdentities.get("路程假"));
			object.put("KQ0409", lJiaQi);//路程假
			String sease = this.flowRunDatas.get(formItemIdentities.get("请假事由"));
			object.put("KQ0410", sease);//请假原因
			object.put("KQ0413", person.getUserId());//代请假人
			object.put("KQ0433", "");//实际开始时间
			//实际请假天数共计
			String sjGj = this.flowRunDatas.get(formItemIdentities.get("实际请假天数共计"));
            object.put("KQ0418", sjGj);
            String sqTime = this.flowRunDatas.get(formItemIdentities.get("申请日期"));
    		object.put("KQ0422", sqTime+" 00:00:00");//申请日期
    		TeeFlowRunPrcs runPrcs2 = find.get(find.size()-1);
    		object.put("KQ0423", TeeDateUtil.format(runPrcs2.getEndTime()));//真正提交时间
    		String address = this.flowRunDatas.get(formItemIdentities.get("境外地址"));
			object.put("KQ0424", address);//具体地址
    		String dept = this.flowRunDatas.get(formItemIdentities.get("申请部门"));
			object.put("KQ0425", dept);
			String lastN = this.flowRunDatas.get(formItemIdentities.get("用上年度请假天数"));
			//是否使用上年年假
			if(TeeStringUtil.getDouble(lastN, 0.0)>0){
				object.put("KQ0430", "1");
			}else{
				object.put("KQ0430", "0");
			}
			//用本年度请假天数
			String bNian = this.flowRunDatas.get(formItemIdentities.get("用本年度请假天数"));
			object.put("KQ0431", TeeStringUtil.getDouble(bNian, 0.0));//用本年度请假天数
			object.put("KQ0432", TeeStringUtil.getDouble(lastN, 0.0));//用上年度请假天数）
			object.put("KQ0417", "");//实际结束时间
			//销假备注
			object.put("KQ0499", "");//备注
			object.put("KQ0435", "");//真正的销假时间
			object.put("KQ0436", "");//销假人
			//审批人
			List<TeeFlowRunCtrlFeedback> find2 = teeFlowRunCtrlFeedBackDao.find("from TeeFlowRunCtrlFeedback where flowRun.runId=?", new Object[]{runId});
			List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
			if(find2!=null && find2.size()>0){
				for(TeeFlowRunCtrlFeedback fb:find2){
					Map<String,Object> obj=new HashMap<String,Object>();
					if(fb.getItemId()==20){//部门意见
						obj.put("KQ1706", "部门意见");
					}
					obj.put("KQ1702", fb.getCreateUser().getUserName());//审批人
					obj.put("KQ1704", fb.getContent());//审批意见
					obj.put("KQ1705", TeeDateUtil.format(fb.getCreateTime()));//审批时间
					List<TeeFlowRunPrcs> find3 = teeFlowRunPrcsDao.find("from TeeFlowRunPrcs where flowRun.runId=? and prcsId=?", new Object[]{runId,fb.getPrcsId()+1});
					if(find3!=null && find3.size()>0){
						TeeFlowRunPrcs prcs = find3.get(0);
						if(prcs.getBackFlag()==0){
							obj.put("KQ1703", 1);//审批状态
						}else{
							obj.put("KQ1703", 0);//审批状态
						}
						List<TeeFeedBack> find4 = simpleDaoSupport.find("from TeeFeedBack where flowRun.runId=? and prcsId=? and backFlag=1", new Object[]{runId,fb.getPrcsId()});
						if(find4!=null && find4.size()>0){
							TeeFeedBack back= find4.get(0);
							obj.put("KQ1707", back.getContent());
						}else{
							obj.put("KQ1707", "");
						}
					}else{
						obj.put("KQ1707", "");//回退意见
						obj.put("KQ1703", "1");//审批状态
					}
					listMap.add(obj);
				}
			}
			object.put("KQ17", listMap);
			String xml = object.toString();
			System.out.println(xml);
			// 声明返回值
			String result = "";
			// 省院调用高检的url
			// 声明接口地址
			String personAddress = TeeSysProps.getProps().getProperty("PERSON_ADDRESS");
			String ENDPOINT = "http://"+personAddress+"/QjxxService/addRecordQJInfo?wsdl";
			// 声明方法名
			String method = "addRecordQJInfo";
			// 声明命名空间
			String SOAPACTION = "http://whzf.inf.web.bop.com/";
			// 2.进行接口调用
			// 声明调用对象
			Call call;
			try {
				// 获得调用对象
				call = (Call) new Service().createCall();
				// 设置超时时间
				call.setTimeout(new Integer(6000000));
				// 设置调用地址
				call.setTargetEndpointAddress(new URL(ENDPOINT));
				// 设置命名空间与调用方法
				call.setOperationName(new QName(SOAPACTION, method));
				// 设置输入参数类型
				call.addParameter(new QName(SOAPACTION, "arg0"),
						XMLType.XSD_STRING, ParameterMode.IN);
				// 设置返回值类型
				call.setReturnType(XMLType.XSD_STRING);
				// 声明参数数组并封装xml数据
				Object[] argArr = new Object[] { xml };
				// 调用接口并获得返回值
				// result = (String) call.invoke(argArr);
				// 将返回值进行解析
				
				result = (String) call.invoke(argArr);
				System.out.println(result);
				// 将返回值返回
			} catch (Exception e) {
			}
		}
	}

	@Override
	public TeeJsonProxy beforeSave() {
		return null;
	/*	TeeJsonProxy jsonProxy=new TeeJsonProxy();
		int userId = TeeStringUtil.getInteger(this.getFlowRunDatas().get("EXTRA_48"),0);
		if(userId>0){
			TeePersonDao personDao = 
					(TeePersonDao) TeeBeanFactory.getBean("personDao");
			TeePerson person = personDao.get(userId);
			TeeUserRole userRole = person.getUserRole();
			if(userRole!=null){
				this.getFlowRunDatas().put("DATA_49", userRole.getRoleName());
				this.getFlowRunDatas().put("EXTRA_49", userRole.getUuid()+"");
			}
		}
		jsonProxy.setRtState(true);
		return jsonProxy;*/
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
