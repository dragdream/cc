package com.tianee.oa.mobile.meeting.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.meeting.bean.TeeMeetingAttendConfirm;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.mobile.meeting.dao.TeeMobileMeetingAttendConfirmDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class TeeMobileMeetingAttendConfirmService extends TeeBaseService{

	@Autowired
	private TeeMobileMeetingAttendConfirmDao mobileMeetingAttendConfirmDao;
	
	/**
	 * 根据状态获取参会情况
	 * @param requestMap
	 * @param person
	 * @return
	 */
	public TeeJson showMeetingAttendInfoByStatus(Map requestMap,
			TeePerson person) {
		List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
		int meetingId = TeeStringUtil.getInteger(requestMap.get("meetingId"), 0);
		int attendFlag=TeeStringUtil.getInteger(requestMap.get("status"), 0);
		
		List<TeeMeetingAttendConfirm> list = mobileMeetingAttendConfirmDao.getObjList(meetingId,attendFlag) ;
		if(list != null && list.size()>0){
			for(TeeMeetingAttendConfirm obj:list){
				Map<String,String> map = new HashMap<String, String>();
				String confirmTime = "";
				if(!TeeUtility.isNullorEmpty(obj.getConfirmTime())){
					confirmTime = TeeUtility.getDateTimeStr(obj.getConfirmTime());
				}
				map.put("userName", obj.getCreateUser().getUserName());
				map.put("deptName", obj.getCreateUser().getDept().getDeptName());
				map.put("roleName", obj.getCreateUser().getUserRole().getRoleName());
				map.put("attendFlag", String.valueOf(obj.getAttendFlag()));
				map.put("confirmTime", confirmTime);
				map.put("remark", TeeUtility.null2Empty(obj.getRemark()));
				mapList.add(map);
			}
		}
		TeeJson json = new TeeJson();
		json.setRtData(mapList);
		json.setRtState(true);
		json.setRtMsg("数据获取成功!");
		return json;
	}

}
