package com.tianee.oa.core.general.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.general.bean.TeeSealLog;
import com.tianee.oa.core.general.dao.TeeSealLogDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeSealLogConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeSealLogService extends TeeBaseService{
	
	@Autowired
	TeeSealLogDao sealLogDao;
	
	public void add(TeeSealLog sealLog){
		sealLogDao.Add(sealLog);	
	}
	
	
	
	/**
	 * 通用列表
	 * @param dm
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		j.setTotal(sealLogDao.selectAllCount(request));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage()-1) * dm.getRows() ;//获取开始索引位置
		Object parm[] = {};
		List logList = sealLogDao.getLogPageFind(firstIndex , dm.getRows(),dm ,request );// 查
		List<Map> list = new ArrayList<Map>();
		if (logList != null) {
			for (int i = 0; i < logList.size(); i++) {
				Object[] log = (Object[])logList.get(i);
				
				// log.sid , log.LOG_TYPE , log.SEAL_ID , se.SEAL_NAME , log.LOG_TIME , log.IP_ADD , log.MAC_ADD , log.USER_ID , log.USER_NAME
				Map map = new HashMap();
				map.put("sid", log[0]);
				map.put("sealId", log[2]);
				String sealName = (String)log[3];
				if(TeeUtility.isNullorEmpty(sealName)){
					sealName = (String)log[10];
				}
				map.put("sealName", sealName);
				Object obj = log[4];
				String logTimeDesc = "";
				if(obj != null ){
					Timestamp ts = (Timestamp)obj;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");   
				    logTimeDesc = sdf.format(ts);   
				}else{
					logTimeDesc = TeeDateUtil.format(new Date());
				}
				map.put("logTimeDesc",logTimeDesc);
				map.put("ipAdd", log[5]);
				map.put("macAdd", log[6]);
				map.put("logType", log[1]);
				int ss =  TeeStringUtil.getInteger(log[1],1) -1;
				map.put("logTypeDesc", TeeSealLogConst.SEAL_LOG_TYPE_DESC[ss]);
				map.put("userName", log[8]);
				map.put("result", log[9]);
			
			    	
				/*um.setCreateTimeDesc(TeeDateUtil.format(createTime==null?null:createTime.getTime()));
				um.setCreateTime(createTime);*/
				list.add(map);
			}
			/*for (TeeSealLog log : roles) {
				
				list.add(map);
			}*/
		}
		j.setRows(list);// 设置返回的行
		return j;
	}



	public void setSealLogDao(TeeSealLogDao sealLogDao) {
		this.sealLogDao = sealLogDao;
	}	
	
	
	
}


