package com.tianee.oa.core.workflow.plugins;

import java.util.List;
import java.util.Map;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;
import com.tianee.webframe.util.global.TeeBeanFactory;
import com.tianee.webframe.util.str.TeeStringUtil;


public class TeeDuBanPlugin extends TeeWfPlugin {
	@Override
	public TeeJsonProxy beforeTurnnext() {
		TeeJsonProxy jsonProxy = new TeeJsonProxy();
		jsonProxy.setRtState(true);
		return jsonProxy;
	}

	@Override
	public void afterTurnnext() {
		
	}

	@Override
	public TeeJsonProxy beforeSave() {
		TeeJsonProxy jsonProxy = new TeeJsonProxy();
		String deptId = this.getFlowRunDatas().get("EXTRA_7");
		int id=TeeStringUtil.getInteger(deptId, 0);
		TeePersonDao personDao = 
				(TeePersonDao) TeeBeanFactory.getBean("personDao");
		List<TeePerson> find = personDao.find("from TeePerson where dept.uuid=? and userRole.roleName=?", new Object[]{id,"部门负责人"});
		if(find!=null && find.size()>0){
			String manager = "";
			for(TeePerson p:find){
				manager=p.getUuid()+",";
			}
			if(!"".equals(manager)){
				manager=manager.substring(0, manager.length()-1);
			}
			this.flowRunDatas.put("EXTRA_33", manager);
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
//		System.out.println(preTurnNextData);
//		List<Map<String,String>> prcsNodeInfos = (List<Map<String, String>>) preTurnNextData.get("prcsNodeInfos");
//		Map<String,String> prcsInfo = prcsNodeInfos.get(0);
//		
//		String deptId = this.getFlowRunDatas().get("EXTRA_7");
//		System.out.println(deptId);
//		
//		prcsInfo.put("prcsUser", "4,23");
//		prcsInfo.put("prcsUserDesc", "系统管理员,王小明");
//		System.out.println(prcsInfo);
		
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
