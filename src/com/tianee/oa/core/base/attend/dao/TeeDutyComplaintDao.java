package com.tianee.oa.core.base.attend.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.attend.bean.TeeDutyComplaint;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.date.TeeDateUtil;

@Repository("dutyComplaintDao")
public class TeeDutyComplaintDao extends TeeBaseDao<TeeDutyComplaint>{

	
	/**
	 * 
	 * @param remarkTimeStr签到日期   例如:2018-06-12
	 * @param num 代表第几次签到
	 * @param userId 用户主键
	 * @return cl1 规定打卡的时间  1970-01-01 09:00:00
	 */
	public int getComplaintOperation(String remarkTimeStr,int num,int userId,Calendar cl1){
		List<TeeDutyComplaint> list=executeQuery(" from TeeDutyComplaint where user.uuid=? and registerNum=? and remarkTimeStr=? ", new Object[]{userId,num,remarkTimeStr});
		Calendar curr=Calendar.getInstance();
		Calendar guid=TeeDateUtil.parseCalendar(remarkTimeStr);//规定时间
		guid.set(Calendar.HOUR_OF_DAY, cl1.get(Calendar.HOUR_OF_DAY));
		guid.set(Calendar.MINUTE,cl1.get(Calendar.MINUTE));
		guid.set(Calendar.SECOND,cl1.get(Calendar.SECOND));
		if(guid.after(curr)){
			return 0;//没有申诉权限
		}else{
			if(list!=null&&list.size()>0){//已经申诉过
				return 1;
			}else{//没有申诉过
				return 2;
			}	
		}	
	}
}
