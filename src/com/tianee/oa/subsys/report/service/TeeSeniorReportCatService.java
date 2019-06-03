package com.tianee.oa.subsys.report.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.report.bean.TeeSeniorReportCat;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeSeniorReportCatService extends TeeBaseService{
	
	public void add(TeeSeniorReportCat seniorReportCat){
		simpleDaoSupport.save(seniorReportCat);
	}
	
	public void update(TeeSeniorReportCat seniorReportCat){
		simpleDaoSupport.update(seniorReportCat);
	}
	
	public void delete(int sid){
		simpleDaoSupport.executeUpdate("delete TeeSeniorReportTemplate where reportCat.sid=?", new Object[]{sid});
		simpleDaoSupport.delete(TeeSeniorReportCat.class, sid);
	}
	
	public TeeSeniorReportCat get(int sid){
		return (TeeSeniorReportCat) simpleDaoSupport.get(TeeSeniorReportCat.class, sid);
	}
	
	public TeeEasyuiDataGridJson datagrid(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from TeeSeniorReportCat ";
		List<TeeSeniorReportCat> list = simpleDaoSupport.pageFind(hql+"order by sortNo asc", dm.getFirstResult(), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		dataGridJson.setTotal(total);
		dataGridJson.setRows(list);
		return dataGridJson;
	}

	
	/**
	 * 获取所有的报表分类   不分页
	 * @param request
	 * @return
	 */
	public TeeJson getAllCat(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String hql = "from TeeSeniorReportCat ";
		List<TeeSeniorReportCat> list = simpleDaoSupport.executeQuery(hql, null);
		json.setRtData(list);
		json.setRtState(true);
		return json;
	}
}
