package com.tianee.oa.core.pending.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.pending.bean.TeeDaiBanEventView;
import com.tianee.oa.core.pending.dao.TeeDaiBanEventViewDao;
import com.tianee.oa.core.pending.model.TeeDaiBanEventModel;
import com.tianee.oa.webservice.model.Person;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
@Service
public class TeeDaiBanEventViewService extends TeeBaseService{
	
	@Autowired
	private TeeDaiBanEventViewDao dbeDao;
	
	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;
	
	/*
	 * 分页查询
	 */
	public List<TeeDaiBanEventView> lists(int firstResult,int rows,TeeDaiBanEventModel querymodel,TeePerson loginUser){
		
		return dbeDao.list(firstResult, rows, querymodel,loginUser);
		
	}
	/*
	 * 返回总数
	 */
	public long  getTotal(TeeDaiBanEventModel querymodel,TeePerson loginUser){
		return dbeDao.getTotal(querymodel,loginUser);
	}
	public TeeJson updateRead(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String uuid = request.getParameter("uuid");
		int nativeUpdate=0;
		if(uuid!=null && !"".equals(uuid)){
			if(uuid.indexOf("wf")!=-1 || uuid.indexOf("XT")!=-1){
				
			}else{
				nativeUpdate = simpleDaoSupport.executeNativeUpdate("update COMMON_HANDLER set IS_REND=1 where UUID=?", new Object[]{uuid});
			}
			json.setRtData(nativeUpdate);
			json.setRtState(true);
		}
		return json;
	}
}
