package com.tianee.oa.subsys.report.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.subsys.report.bean.TeeQuieeReport;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("quieeReportDao")
public class TeeQuieeReportDao extends TeeBaseDao<TeeQuieeReport>{
    /**
     * 获取记录数
     * @param folderSid
     * @param person
     * @return
     */
	public Long getCountByFolderSid(int folderSid, TeePerson person) {
		String hql = "select count(r.sid)  from TeeQuieeReport r where r.parent.sid="+folderSid
				+ " and ( r.reportType=2 or (r.reportType=1 and (exists (select 1 from r.userPrivManage userPrivManage where userPrivManage.uuid ="+person.getUuid()+")"
		        + " or exists (select 1 from r.deptPrivManage deptPrivManage where deptPrivManage.uuid ="+person.getDept().getUuid()+")"
		        + " or exists (select 1 from r.rolePrivManage rolePrivManage where rolePrivManage.uuid ="+person.getUserRole().getUuid()+")"
		        + " or exists (select 1 from r.userPrivView userPrivView where userPrivView.uuid ="+person.getUuid()+")"
		        + " or exists (select 1 from r.deptPrivView deptPrivView where deptPrivView.uuid ="+person.getDept().getUuid()+")"
		        + " or exists (select 1 from r.rolePrivView rolePrivView where rolePrivView.uuid ="+person.getUserRole().getUuid()+"))))";
		if (TeePersonService.checkIsSuperAdmin(person, null)) {
			hql = " select count(r.sid)  from TeeQuieeReport r  where r.parent.sid="+folderSid;
		}
		
		
		return count(hql, null);
	}

	/**
	 * 获取记录列表
	 * @param firstIndex
	 * @param rows
	 * @param dm
	 * @param folderSid
	 * @param person
	 * @return
	 */
	public List<TeeQuieeReport> getReportPage(int firstIndex, int rows,
			TeeDataGridModel dm, int folderSid, TeePerson person) {
		String hql ="select r  from TeeQuieeReport r where r.parent.sid="+folderSid
				+ " and ( r.reportType=2 or (r.reportType=1 and (exists (select 1 from r.userPrivManage userPrivManage where userPrivManage.uuid ="+person.getUuid()+")"
				+ " or exists (select 1 from r.deptPrivManage deptPrivManage where deptPrivManage.uuid ="+person.getDept().getUuid()+")"
				+ " or exists (select 1 from r.rolePrivManage rolePrivManage where rolePrivManage.uuid ="+person.getUserRole().getUuid()+")"
				+ " or exists (select 1 from r.userPrivView userPrivView where userPrivView.uuid ="+person.getUuid()+")"
				+ " or exists (select 1 from r.deptPrivView deptPrivView where deptPrivView.uuid ="+person.getDept().getUuid()+")"
				+ " or exists (select 1 from r.rolePrivView rolePrivView where rolePrivView.uuid ="+person.getUserRole().getUuid()+"))))";
		if (TeePersonService.checkIsSuperAdmin(person, null)) {
			
			hql = "select r  from TeeQuieeReport r where r.parent.sid="+folderSid;
		}
				
		hql += "  order by r.reportType asc,r.sortNo asc";
		return pageFind(hql, firstIndex, rows, null);
	}

	
	/**
	 * 获取一级目录数量
	 * @return
	 */
	public Long getFirstFolderCount() {
		String hql = "select count(r.sid)  from TeeQuieeReport r where (r.parent.sid=0 or r.parent is null) and r.reportType=1";	
		return count(hql, null);
	}

	/**
	 * 获取一级目录列表
	 * @param firstIndex
	 * @param rows
	 * @param dm
	 * @return
	 */
	public List<TeeQuieeReport> getFirstFolderList(int firstIndex, int rows,
			TeeDataGridModel dm) {
		String hql ="select r  from TeeQuieeReport r where (r.parent.sid=0 or r.parent is null) and r.reportType=1 ";	
		hql += "  order by r.sortNo asc";
		return pageFind(hql, firstIndex, rows, null);
	}

	

}
