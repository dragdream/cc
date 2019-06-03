package com.tianee.oa.core.workflow.plugins;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetsCategory;
import com.tianee.oa.core.base.fixedAssets.model.TeeFixedAssetsInfoModel;
import com.tianee.oa.core.base.fixedAssets.service.TeeFixedAssetsInfoService;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunCtrlFeedback;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunCtrlFeedBackDao;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunPrcsDao;
import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeBeanFactory;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;


public class TeeQingJiaRolePlugin extends TeeWfPlugin {
	@Override
	public TeeJsonProxy beforeTurnnext() {
		return null;
	}

	@Override
	public void afterTurnnext() {
	    JSONObject object=new JSONObject();
	    int runId = flowRunProxy.getRunId(); //获取流程ID
		TeePersonDao personDao = 
				(TeePersonDao) TeeBeanFactory.getBean("personDao");
		int userId = TeeStringUtil.getInteger(this.getFlowRunDatas().get("EXTRA_48"),0);
		TeePerson person2 = personDao.get(userId);//请假人
		TeeFlowRunPrcsDao teeFlowRunPrcsDao = 
				(TeeFlowRunPrcsDao) TeeBeanFactory.getBean("teeFlowRunPrcsDao");
		List<TeeFlowRunPrcs> find = teeFlowRunPrcsDao.find("from TeeFlowRunPrcs where flowRun.runId=? order by sid desc", new Object[]{runId});
		String qjType = this.flowRunDatas.get(formItemIdentities.get("请假类别"));
		String zg = this.flowRunDatas.get(formItemIdentities.get("共计"));
		object.put("flag", "0");
		object.put("userId", person2.getUserId());//请假人用户名
		object.put("KQ0434", runId);//流程id
		String beginTime = this.flowRunDatas.get(formItemIdentities.get("开始时间"));
		String endTime = this.flowRunDatas.get(formItemIdentities.get("结束时间"));
		String bTime = this.flowRunDatas.get(formItemIdentities.get("开时间"));
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
		int beginUserId = this.flowRunProxy.getBeginUserUuid();//流程发起人id
		TeePerson person = personDao.get(beginUserId);
		object.put("KQ0413", person.getUserId());//发起人
		object.put("KQ0417", "");//实际结束时间
		//实际请假天数共计
        object.put("KQ0418", "");
        String sqTime = this.flowRunDatas.get(formItemIdentities.get("申请日期"));
		object.put("KQ0422", sqTime+" 00:00:00");//申请日期
		TeeFlowRunPrcs runPrcs2 = find.get(find.size()-1);
		object.put("KQ0423", TeeDateUtil.format(runPrcs2.getEndTime()));//真正提交时间
		String address = this.flowRunDatas.get(formItemIdentities.get("境外地址"));
		object.put("KQ0424", address);//具体地址
		String dept = this.flowRunDatas.get(formItemIdentities.get("申请部门"));
		object.put("KQ0425", dept);//申请部门
		String lastN = this.flowRunDatas.get(formItemIdentities.get("上年度的年假"));
        if(TeeStringUtil.getDouble(lastN, 0.0)>0){
        	object.put("KQ0430", 1);//是否使用了上年年假
        }else{
        	object.put("KQ0430", 0);//是否使用了上年年假
        }
        //用本年度请假天数
		String bNian = this.flowRunDatas.get(formItemIdentities.get("本年度的年假"));
		object.put("KQ0431", bNian);//用本年度请假天数
		object.put("KQ0432", lastN);//用上年度请假天数）
		object.put("KQ0433", "");//实际开始时间
		object.put("KQ0499", "");//备注
		object.put("KQ0435", "");//真正的销假时间
		object.put("KQ0436", "");//销假人
		//审批人
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		//审批人
//		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
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
			// 将返回值返回
		} catch (Exception e) {
		}
	}

	@Override
	public TeeJsonProxy beforeSave() {
		TeeJsonProxy jsonProxy=new TeeJsonProxy();
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
