package com.tianee.thirdparty.itf.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.itf.service.TeeItfService;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("itfController")
public class TeeItfController {

	@Autowired
	private TeeItfService teeItfService;
	
	@Autowired
	TeeSimpleDaoSupport simpleDaoSupport;
	
	/**
	 * 添加人员
	 * @throws Exception 
	 * */
	@ResponseBody
	@RequestMapping("/addPerson")
	public String addPerson(HttpServletRequest request) throws Exception{
		return teeItfService.addPerson(request);
	}
	/**
	 *修改人员
	 * @throws Exception 
	 * */
	@ResponseBody
	@RequestMapping("/updatePerson")
	public String updatePerson(HttpServletRequest request) throws Exception{
		return teeItfService.updatePerson(request);
	}
	/**
	 * 删除人员
	 * */
	@ResponseBody
	@RequestMapping("/deletePerson")
	public String deletePerson(String userId){
		return teeItfService.deletePerson(userId);
	}
	/**
	 * 添加部门
	 * @throws Exception 
	 * */
	@ResponseBody
	@RequestMapping("/addDept")
	public String addDept(HttpServletRequest request) throws Exception{
		return teeItfService.addDept(request);
	}
	/**
	 * 修改部门
	 * @throws Exception 
	 * */
	@ResponseBody
	@RequestMapping("/updateDept")
	public String updateDept(HttpServletRequest request) throws Exception{
		return teeItfService.updateDept(request);
	}
	/**
	 * 删除部门
	 * @throws Exception 
	 * */
	@ResponseBody
	@RequestMapping("/deleteDept")
	public String deleteDept(HttpServletRequest request) throws Exception{
		return teeItfService.deleteDept(request);
	}
	
	/**
	 * 结束流程
	 * */
	@ResponseBody
	@RequestMapping("/endFlowRun")
	public String endFlowRun(HttpServletRequest request) throws Exception{
		return teeItfService.endFlowRun(request);
	}
	
	/**
	 * 请假类
	 * */
	@ResponseBody
	@RequestMapping("/fingQingJiaNian")
	public TeeJson fingQingJiaNian(HttpServletRequest request){
		TeeJson tJson=new TeeJson();
		TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String bedate=request.getParameter("bedate");
		String endate=request.getParameter("endate");
		String betime=request.getParameter("betime");
		String endtime=request.getParameter("endtime");
		int userSid=TeeStringUtil.getInteger(request.getParameter("userSid"), 0);
		JSONObject  json = new JSONObject();
		if(userSid==0){
			json.put("userId", person.getUserId()); //账户
		}else{
			TeePerson p = (TeePerson)simpleDaoSupport.get(TeePerson.class, userSid);
			json.put("userId", p.getUserId()); //账户
		}
		json.put("bedate", bedate);//开始时间
		json.put("endate", endate);//结束时间
		json.put("betime", betime.replace("时", ""));	//开始
		json.put("endtime", endtime.replace("时", ""));//结束
		String jsonStr=isAllow(json.toString());
		//bcsjqjts
		//bcsjqjts  bcsjqjts
		//String jsonStr="{\"success\":1,\"msg\":\"允许休上年年假,上年可休年假5天,已休0天,还剩5天,其中,去年休年假0天,本年休去年年假0天,可休今年年假5天,已休今年年假0天,剩余5.0天，本次申请年假4.5天\",\"data\":{\"nfsysnj\":1,\"sndzts\":5,\"sndyxts\":0,\"sndyqts\":0,\"bndysnts\":0,\"sndsyts\":2,\"bnzts\":5,\"bnyxts\":0,\"bnsyts\":2,\"bcsjqjts\":4.5}}";
		//String jsonStr="{\"success\":1,\"msg\":\"上年度不能休年假,今年可休年假5天,已休0天,剩余年假5.0天,本次申请年假6天\",\"datas\":{\"nfsysnj\":1,\"sndsyts\":2,\"bnsyts\":3,\"bcsjqjts\":3}}";                                                                                                                     
		tJson.setRtData(jsonStr);                                                                            
		return tJson;
	}
	
	
	/**
	 * 请假类(除年休假外其他假期)
	 * */
	@ResponseBody
	@RequestMapping("/fingQingJiaQiTa")
	public TeeJson fingQingJiaQiTa(HttpServletRequest request){
		TeeJson tJson=new TeeJson();
		//TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String bedate=request.getParameter("bedate");
		String endate=request.getParameter("endate");
		String betime=request.getParameter("betime");
		String endtime=request.getParameter("endtime");
		String nJia=request.getParameter("nJia");
		if("9时".equals(betime)){
			betime="0"+betime;
		}
		JSONObject object=new JSONObject();
		String includeHoliday="0";
		if("产假".equals(nJia) || "探亲假".equals(nJia)){
			includeHoliday="1";
		}
		//object.put(key, value);
		String b=bedate+" "+betime.replace("时", "")+":00:00";
		String e=endate+" "+endtime.replace("时", "")+":00:00";
		//"{'beginTime':'2017-06-19 09:00:00','endTime':'2018-06-27 17:00:00'}"
		String jsonStr="{'beginTime':'"+b+"','endTime':'"+e+"','includeHoliday':'"+includeHoliday+"'}";
		String allow = isAllow2(jsonStr);
		//{"msg":"","status":"","num",""}status:0不可以请  1可以请
		//String allow="2.0";
		tJson.setRtData(allow);
		return tJson;
	}
	
	
	
	/**
	 * 流程结束之后返回数据给人事系统
	 * */
	@ResponseBody
	@RequestMapping("/toPersonSystem")
	public void toPersonSystem(HttpServletRequest request){
		TeeJson tJson=new TeeJson();
		
		//String allow = testRecordAdd(jsonStr);
		//tJson.setRtData(allow);
	}
	
	
	/**
	 * 人员层级关系
	 * */
	@ResponseBody
	@RequestMapping("/fingPersonCengJi")
	public TeeJson fingPersonCengJi(HttpServletRequest request){
		TeeJson json=new TeeJson();
		TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//String userId=request.getParameter("userId");
		String regist = regist(person.getUserId());
		JSONObject jsonObject = JSONObject.fromObject(regist);//返回的是数值
		String str = jsonObject.getString("ryzj");
		//String regist="";
		json.setRtData(str);
		return json;
	}

	/**
	 * 年休假 请假天数、备注等信息
	 * */
	public String isAllow(String data) {

		String xml = data.toString();
		// 声明返回值
		String result = "";
		// 省院调用高检的url
		// 声明接口地址
		String personAddress = TeeSysProps.getProps().getProperty("PERSON_ADDRESS");
		String ENDPOINT = "http://"+personAddress+"/NxjsqService/IsCanLast?wsdl";
		// 声明方法名
		String method = "IsCanLast";
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
	return result;
	}
	/**
	 * 除年休假之外的所有假期的请假天数
	 * */
	public String isAllow2(String data) {

		String xml = data.toString();
		// 声明返回值
		String result = "";
		// 省院调用高检的url
		// 声明接口地址
		String personAddress = TeeSysProps.getProps().getProperty("PERSON_ADDRESS");
		String ENDPOINT = "http://"+personAddress+"/QjxxLenService/getQJlen?wsdl";
		// 声明方法名
		String method = "getQJlen";
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
	  return result;
	}
	
	/**
	 * 流程结束请假时间回传人事系统(流程结束之后)
	 * */
	public void testRecordAdd(String data) {
		String xml = data.toString();
		// 声明返回值
		String result = "";
		// 省院调用高检的url
		// 声明接口地址
		String personAddress = TeeSysProps.getProps().getProperty("PERSON_ADDRESS");
		String ENDPOINT = "http://"+personAddress+"QjxxLenService/addRecordQJInfo?wsdl";
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
//		JSONObject vacaJson = new JSONObject();
//		vacaJson.put("KQ1002", "年休假");
//		String jr = getHolidayId(vacaJson.toString());
//		System.out.println("jr :" + jr);
//		vacaJson = JSONObject.fromObject(jr).getJSONObject("data");
//		String KQ1001 = "9c171511-2aa5-4c33-a783-1159911f6ed3";// vacaJson.getString("KQ1001");
//		JSONObject json = new JSONObject();
//		json.put("A00", "A00ID");
//		json.put("KQID", UUID.randomUUID().toString()); // "2c5dfaeb-5271-4348-9dd7-b650efb8279e"
//		json.put("KQ0401", "2018-10-17 10:00:00");
//		json.put("KQ0403", "2018-10-19 10:00:00");
//		json.put("KQ0405", KQ1001);
//		json.put("KQ0406", "3"); // 请假天数
//		json.put("KQ0407", "0");
//		json.put("KQ0410", "年休假");
//		json.put("KQ0411", "2");
//		json.put("NODEID", "e8b8d5a1-a719-4238-9c63-069a53d5963a");
//		json.put("KQ0415", "guoxiaodi 年休假  2018-10-10 到2018-10-19 共9天");
//		json.put("KQ0416", "0");
//		json.put("KQ0418", "7");
//		json.put("KQ0419", "1");
//		json.put("KQ0422", "2018-11-06");
//		json.put("KQ0499", "请假申请");
//		JSONArray k17Array = new JSONArray();
//
//		JSONObject kq171 = new JSONObject();
//		// kq171.put("KQ1701",value); //请假主键
//		kq171.put("KQ1702", "审批人"); // 审批人
//		kq171.put("KQ1703", "同意"); // 审批结果
//		kq171.put("KQ1704", "审批意见"); // 审批意见
//		kq171.put("KQ1705", "2018-12-27"); // 审批时间
//		kq171.put("KQ1706", "部门领导"); // 审批人级别
//
//		k17Array.add(kq171);
//		json.put("KQ17", k17Array);
//
//		String rs = addRecordInfo(json.toString());
//		System.out.println("fdsfdsfdsf:fff " + rs.toString());
//		System.out.println(rs);

	}

	/**
	 * 人员层级
	 * */
	public String regist(String data) {

		String xml = data.toString();
		// 声明返回值
		String result = "";
		// 省院调用高检的url
		// 声明接口地址
		String personAddress = TeeSysProps.getProps().getProperty("PERSON_ADDRESS");
		String ENDPOINT = "http://"+personAddress+"/RyzjService/getRyzjmsg?wsdl";
		// 声明方法名
		String method = "getRyzjmsg";
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
			//System.out.println(result);
			// 将返回值返回
		} catch (Exception e) {
		}
	return result;
	}
	
	/**
	 * 流程回退到第一步和流程发起人收回流程的时候调用
	 * */
	@ResponseBody
	@RequestMapping("/deleteRunIdToRenShi")
	public void deleteRunIdToRenShi(HttpServletRequest request){
		int prcId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int flag = TeeStringUtil.getInteger(request.getParameter("flag"), 0);
		if(flag==1){//收回
			List<TeeFlowRunPrcs> find = simpleDaoSupport.find("from TeeFlowRunPrcs where sid=? order by sid desc", new Object[]{prcId});
		    if(find!=null && find.size()>0){
		    	TeeFlowRunPrcs prcs = find.get(0);
		    	if((prcs.getFlowType().getSid()==1032 || prcs.getFlowType().getSid()==1051) && "请假申请".equals(prcs.getFlowPrcs().getPrcsName())){
		    		//调用流程删除方法
		    		System.out.println("流程收回");
		    		JSONObject data=new JSONObject();
		    		data.put("KQ0434", prcs.getFlowRun().getRunId());
		    		deleteFlowRunByRenShi(data.toString());
		    	}
		    }
		}else{//回退
		   //调用流程删除方法
			System.out.println("流程回退");
			JSONObject data=new JSONObject();
			data.put("KQ0434", prcId);
			deleteFlowRunByRenShi(data.toString());
		}
		
	}
	
	/**
	 * 调用人事删除流程方法
	 * */
	public String deleteFlowRunByRenShi(String data) {
		String xml = data.toString();
		// 声明返回值
		String result = "";
		// 省院调用高检的url
		// 声明接口地址
		String personAddress = TeeSysProps.getProps().getProperty("PERSON_ADDRESS");
		String ENDPOINT = "http://"+personAddress+"/QjxxService_removeQJInfo/removeQJInfo?wsdl";
		// 声明方法名
		String method = "removeQJInfo";
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
			//System.out.println(result);
			// 将返回值返回
		} catch (Exception e) {
		}
	return result;
	}
	
	/**
	 * 获取实际的请假情况
	 * */
	@ResponseBody
	@RequestMapping("/shiJiQingJia")
	public TeeJson shiJiQingJia(HttpServletRequest request){
		TeeJson json =new TeeJson();
		String bDate=request.getParameter("bDate");//实际开始时间
		String eDate=request.getParameter("eDate");//实际结束时间
		String bTime=request.getParameter("bTime");//实际开始时间段
		String eTime=request.getParameter("eTime");//实际结束时间段
		String runId=request.getParameter("runId");//流程id
		String jqType=request.getParameter("jqType");//请假类型
		JSONObject object=new JSONObject();
		object.put("KQ0433", bDate+" "+bTime.replace("时", "")+":00:00");  //开始日期
		//object.put("KQ0417", eDate+" "+eTime.replace("时", "")+":00:00");  //结束日期
		object.put("KQ0405", jqType);		//假期类别KQ0417
		object.put("KQ0434", runId);		//oa流程ID
		object.put("KQ0417", eDate+" "+eTime.replace("时", "")+":00:00");
		//调接口
		String result = qingJiaNum(object.toString());
		//String result="";
		Map<String,Object> map=new HashMap<String,Object>();
		if(!"".equals(result)){
			JSONObject jsonObject = JSONObject.fromObject(result);
			map.put("datas", jsonObject.get("datas"));
			JSONObject fromObject = JSONObject.fromObject(jsonObject.get("datas"));
			map.put("numL", fromObject.get("sndsyts"));//上年度s
			map.put("numB", fromObject.get("bndsyts"));//本年度
			map.put("zongJi", fromObject.get("bcsjqjts"));//实际请假天数
		}
		json.setRtData(map);
		return json;
	}
	
	/**
	 * 实际休假天数
	 * */
	public String qingJiaNum(String data){
		String xml = data.toString();
		// 声明返回值
		String result = "";
		// 省院调用高检的url
		// 声明接口地址
		String personAddress = TeeSysProps.getProps().getProperty("PERSON_ADDRESS");
		String ENDPOINT = "http://"+personAddress+"/IkqxjService/ryxj?wsdl";
		// 声明方法名
		String method = "ryxj";
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
			//System.out.println(result);
			// 将返回值返回
		} catch (Exception e) {
		}
	   return result;
	}
	
}
