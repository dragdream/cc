package com.tianee.oa.quartzjob;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.nustaq.serialization.annotations.Serialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.attend.bean.TeeAttendDutyRecord;
import com.tianee.oa.core.base.attend.service.TeeAttendDutyService;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeAttendDutyRecordTimmer extends TeeBaseService{
	@Autowired
	private TeeAttendDutyService attendDutyService;

	/**
	 * 定时任务执行的方法
	 * @throws Exception 
	 */
	public void doTimmer() throws Exception{
		//System.out.println("进入定时任务");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
		
		//获取当前时间
		Calendar c=Calendar.getInstance();
		//获取当前月的String字符串   2017-09
		String month=sdf.format(c.getTime());
		
		Calendar startDate=Calendar.getInstance();//当前月第一天
		startDate.set(Calendar.DAY_OF_MONTH,1);
		Calendar endDate=Calendar.getInstance();//当前月最后一天
		endDate.set(Calendar.DAY_OF_MONTH, endDate.getActualMaximum(Calendar.DAY_OF_MONTH));  
		
		String startDateDesc=sdf1.format(startDate.getTime());
		String endDateDesc=sdf1.format(endDate.getTime());
		
		//获取所有的部门
		List<TeeDepartment> deptList=simpleDaoSupport.executeQuery(" from TeeDepartment ", null);
		if(deptList!=null&&deptList.size()>0){
			TeeJson json=null;
			List<Map> dataList=null;
			TeeAttendDutyRecord record=null;
			int userId=0;
			TeePerson user=null;
			for (TeeDepartment teeDepartment : deptList) {
				json=attendDutyService.getRegisterRecordInfo(teeDepartment.getUuid(), startDateDesc, endDateDesc,null);
				dataList=(List<Map>) json.getRtData();
			    if(dataList!=null&&dataList.size()>0){
			    	for (Map map : dataList) {
			    		 userId=TeeStringUtil.getInteger(map.get("userId"), 0);
				    	 user=(TeePerson) simpleDaoSupport.get(TeePerson.class,userId);
			    		
			    		List<TeeAttendDutyRecord> recordList=simpleDaoSupport.executeQuery(" from TeeAttendDutyRecord where month=? and user.uuid=? ", new Object[]{month,userId});
			    		
			    		if(recordList!=null&&recordList.size()>0){
			    			record=recordList.get(0);
			    		}else{
			    			record=new TeeAttendDutyRecord();
			    		}
						
			    		record.setDept(teeDepartment);
			    		record.setEvectionDays(TeeStringUtil.getDouble(map.get("evectionDays"), 0.0));
			    		record.setHours(TeeStringUtil.getString(map.get("hours")));
			    		record.setLateNums(TeeStringUtil.getInteger(map.get("lateNums"), 0));
			    		record.setLeaveDays(TeeStringUtil.getDouble(map.get("leaveDays"), 0.0));
					    record.setLeaveEarlyNums(TeeStringUtil.getInteger(map.get("leaveEarlyNums"),0));
			    	    record.setMonth(month);
			    	    record.setOutDays(TeeStringUtil.getDouble(map.get("outDays"), 0.0));
			    	    record.setOverHours(TeeStringUtil.getDouble(map.get("overHours"), 0.0));
			    	    record.setPerfectCount(TeeStringUtil.getInteger(map.get("perfectCount"),0));
			    	    
			    	    record.setUser(user);
			    	    record.setWorkOnNoRegisters(TeeStringUtil.getInteger(map.get("workOnNoRegisters"), 0));
			    	    record.setWorkOutNoRegisters(TeeStringUtil.getInteger(map.get("workOutNoRegisters"),0));
			    	
			    	    if(recordList!=null&&recordList.size()>0){
			    	    	//更新
			    			simpleDaoSupport.update(record);
			    			//System.out.println("更新");
			    		}else{
			    			//新增
			    			simpleDaoSupport.save(record);
			    			//System.out.println("新增");
			    		}
			    	}
			    }
			
			}
			
			
		}
		
		
		
	}
	
}
