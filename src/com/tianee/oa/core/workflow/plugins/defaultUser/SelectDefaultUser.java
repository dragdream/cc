package com.tianee.oa.core.workflow.plugins.defaultUser;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;

import com.tianee.oa.core.workflow.plugins.TeeWfPlugin;
import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;

public class SelectDefaultUser extends TeeWfPlugin {

	@Override
	public TeeJsonProxy beforeTurnnext() {
		return null;
	}

	@Override
	public void afterTurnnext() {
		
	}

	@Override
	public TeeJsonProxy beforeSave() {
		return null;
	}

	@Override
	public void afterSave() {
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void preTurnNextFilter(Map preTurnNextData) {
		List<Map> prcsNodeInfos = (List<Map>) preTurnNextData.get("prcsNodeInfos");
		// 获取下一步办理人（暂时只到协办人）
		String info = this.getFlowRunVars().get("CIRCULATION_INFO");
		String minorId = this.getFlowRunVars().get("MINOR_ID");
		String minorName = this.getFlowRunVars().get("MINOR_NAME");
		String prcsName = prcsNodeInfos.get(0).get("prcsName").toString();
		
		if("协办人签字".equals(prcsName)) {
			prcsNodeInfos.get(0).put("prcsUser", minorId);
			prcsNodeInfos.get(0).put("prcsUserDesc", minorName);
		}
		
//		if (StringUtils.isNotBlank(info)) {
//			// {NextUserId:4,NextUserName:'系统管理员'}
//			// 转交人存在的场合
//			JSONObject myJson = JSONObject.fromObject(info);
//			Object nextUserId = myJson.get("NextUserId");
//			Object nextUserName = myJson.get("NextUserName");
//			prcsNodeInfos.get(0).put("prcsUser", nextUserId);
//			prcsNodeInfos.get(0).put("prcsUserDesc", nextUserName);
//		}
	}

	@Override
	public String afterRendered() {
		return null;
	}

	@Override
	public void goBack(String prcsName, int prcsId, String content) {
		// TODO Auto-generated method stub
		
	}

}
