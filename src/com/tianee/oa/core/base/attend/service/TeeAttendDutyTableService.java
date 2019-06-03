package com.tianee.oa.core.base.attend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.attend.bean.TeeAttendDutyTable;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeAttendDutyTableService extends TeeBaseService{
	
	public void deleteById(int id){
		simpleDaoSupport.delete(TeeAttendDutyTable.class, id);
	}
	
	public void addDutyTableRecord(TeeAttendDutyTable attendDutyTable){
		simpleDaoSupport.save(attendDutyTable);
	}
	
	public void updateDutyTableRecord(TeeAttendDutyTable attendDutyTable){
		simpleDaoSupport.update(attendDutyTable);
	}
	
	public List<TeeAttendDutyTable> getDutyTablesByDate(String dateStr){
		return simpleDaoSupport.find("from TeeAttendDutyTable where pbDate='"+dateStr+"'", null);
	}
	
	public TeeAttendDutyTable getDutyTablesByDateAndUser(String dateStr,int userSid){
		return (TeeAttendDutyTable) simpleDaoSupport.unique("from TeeAttendDutyTable where pbDate='"+dateStr+"' and user.uuid="+userSid, null);
	}
	
	public TeeAttendDutyTable getDutyTableBySid(int sid){
		return (TeeAttendDutyTable) simpleDaoSupport.unique("from TeeAttendDutyTable where sid="+sid, null);
	}
}
