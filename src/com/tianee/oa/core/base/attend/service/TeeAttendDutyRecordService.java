package com.tianee.oa.core.base.attend.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.attend.bean.TeeAttendDutyRecord;
import com.tianee.oa.core.base.attend.bean.TeeAttendDutyRecordModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeAttendDutyRecordService extends TeeBaseService{

	
	/**
	 * 考勤统计
	 * @param request
	 * @return
	 */
	public TeeJson getRegisterRecordInfo(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取前台页面传来的部门ids
		//int deptId=TeeStringUtil.getInteger(request.getParameter("deptId"),0);
		String deptIds=TeeStringUtil.getString(request.getParameter("deptIds"));
		//获取前台页面传来的月份
		String month=TeeStringUtil.getString(request.getParameter("countMonth"));
		
		List<TeeAttendDutyRecord> recordList=simpleDaoSupport.executeQuery(" from TeeAttendDutyRecord where dept.uuid in ("+deptIds+") and month=? order by dept.deptNo asc",new Object[]{month});
		List<TeeAttendDutyRecordModel> modelList=new ArrayList<TeeAttendDutyRecordModel>();
		TeeAttendDutyRecordModel model=null;
		if(recordList!=null&&recordList.size()>0){
			for (TeeAttendDutyRecord r : recordList) {
				model=parseToModel(r);
				modelList.add(model);
			}
		}

		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}

	private TeeAttendDutyRecordModel parseToModel(TeeAttendDutyRecord r) {
		TeeAttendDutyRecordModel model=new TeeAttendDutyRecordModel();
		BeanUtils.copyProperties(r,model);
		if(r.getUser()!=null){
			model.setUserId(r.getUser().getUuid());
			model.setUserName(r.getUser().getUserName());
		}
		
		if(r.getDept()!=null){
			model.setDeptId(r.getDept().getUuid());
			model.setDeptName(r.getDept().getDeptName());
		}
		return model;
	}
	
	
}
