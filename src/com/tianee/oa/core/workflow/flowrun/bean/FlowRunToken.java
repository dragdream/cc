package com.tianee.oa.core.workflow.flowrun.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.util.global.TeeBeanFactory;
import com.tianee.webframe.util.secure.Base64Private;
import com.tianee.webframe.util.str.TeeStringUtil;

public class FlowRunToken {
	private int runId;   // 用户调用ID
	private int flowId;  // 系统定义ID
	private int frpSid;  // 下一环节自增ID
	private String runName;
	private String prcsName;
	private int prcsId;  // 步骤ID
	private int flowPrcs;
	private int backFlag;//回退标记
	private int goBack = 0;// 回退类型 0：不允许 1：回退上一步骤 2：回退之前步骤 3：回退到指定步骤
	private int flag = 0;// 办理状态，1=未接收 2=已接受未办理 3=已办结但下一步未接收 4=已办结且下一步已接收
	private boolean isPrcsUser;//当前节点的办理人是否为当前登陆人
	private boolean isFinished;//当前节点是否办理结束
	private Map<String,String> vars = new HashMap();
	
	public FlowRunToken(HttpServletRequest request){
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String token = request.getParameter("token");
		token = token.replace(" ", "+");
		String jsonString = new String(Base64Private.decode(token.getBytes()));
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		
		this.runId = jsonObject.getInt("runId");
		this.flowId = jsonObject.getInt("flowId");
		this.frpSid = jsonObject.getInt("frpSid");
		this.prcsId = jsonObject.getInt("prcsId");
		this.flowPrcs = TeeStringUtil.getInteger(jsonObject.getString("flowPrcs"), 0);
		this.runName = jsonObject.getString("runName");
		this.prcsName = jsonObject.getString("prcsName");
		this.backFlag = jsonObject.getInt("backFlag");

		
		if(flowPrcs!=0){
			TeeFlowProcessServiceInterface flowProcessService = (TeeFlowProcessServiceInterface) TeeBeanFactory.getBean("teeFlowProcessService");
			TeeFlowProcess flowProcess = flowProcessService.get(flowPrcs);
			if(flowProcess!=null){
				this.goBack = flowProcess.getGoBack();
			}
		}
		
		int prcsUser = jsonObject.getInt("prcsUser");
		if(person.getUuid()==prcsUser){
			isPrcsUser = true;
		}else{
			isPrcsUser = false;
		}
		
		int flag = jsonObject.getInt("flag");
		this.flag = flag;
		if(flag==1 || flag==0 || flag==2){
			isFinished = false;
		}else{
			isFinished = true;
		}
		
		jsonObject = jsonObject.getJSONObject("vars");
		Set<String> keys = jsonObject.keySet();
		for(String key:keys){
			vars.put(key, jsonObject.getString(key));
		}
		
	}

	public int getRunId() {
		return runId;
	}

	public int getFlowId() {
		return flowId;
	}

	public int getFrpSid() {
		return frpSid;
	}

	public String getRunName() {
		return runName;
	}

	public String getPrcsName() {
		return prcsName;
	}

	public int getPrcsId() {
		return prcsId;
	}

	public int getFlowPrcs() {
		return flowPrcs;
	}

	/**
	 * 当前登陆人是否为该节点的办理人
	 * @return
	 */
	public boolean isPrcsUser() {
		return isPrcsUser;
	}

	/**
	 * 当前节点是否已办理完
	 * @return
	 */
	public boolean isFinished() {
		return isFinished;
	}

	/**
	 * 获取流程变量
	 * @return
	 */
	public Map<String, String> getVars() {
		return vars;
	}

	public int getGoBack() {
		return goBack;
	}

	public int getBackFlag() {
		return backFlag;
	}

	public void setBackFlag(int backFlag) {
		this.backFlag = backFlag;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
}
