package com.tianee.oa.core.base.job.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.job.bean.TeeJob;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.quartz.util.TeeQuartzUtils;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.job.TeeJobManager;
import com.tianee.webframe.util.str.TeeJsonUtil;

@Service
public class TeeJobService extends TeeBaseService{
	
	
	public void changeStatus(String id,int status){
		TeeJob job = (TeeJob) simpleDaoSupport.get(TeeJob.class, id);
		job.setStatus(status);
		if(status==0){
			TeeJobManager.stop(id);
		}else{
			TeeJobManager.stop(id);
			TeeJobManager.start(job);
		}
	}
	
	public void delete(String id){
		TeeJobManager.stop(id);
		simpleDaoSupport.delete(TeeJob.class, id);
	}
	
	
	public void addOrUpdate(TeeJob job){
		
		Map<String,String> model = TeeJsonUtil.JsonStr2Map(job.getExpModel());
		String expType = model.get("type");
		if("1".equals(expType)){//间隔
			job.setExp("0/"+model.get("a")+" * * * * ?");
		}else if("2".equals(expType)){//天
			String sp [] = model.get("a").split(":");
			
			job.setExp(TeeQuartzUtils.getDayQuartzExpression(
					Integer.parseInt(sp[0]), 
					Integer.parseInt(sp[1]), 
					Integer.parseInt(sp[2]), 
					null));
			
		}else if("3".equals(expType)){//周
			String sp [] = model.get("b").split(":");
			job.setExp(Integer.parseInt(sp[2])+" "+Integer.parseInt(sp[1])+" "+Integer.parseInt(sp[0])+" ? * "+model.get("a"));
		}else if("4".equals(expType)){//月
			String sp [] = model.get("b").split(":");
			job.setExp(Integer.parseInt(sp[2])+" "+Integer.parseInt(sp[1])+" "+Integer.parseInt(sp[0])+" "+model.get("a")+" * ?");
		}else if("5".equals(expType)){//年
			String sp [] = model.get("c").split(":");
			job.setExp(Integer.parseInt(sp[2])+" "+Integer.parseInt(sp[1])+" "+Integer.parseInt(sp[0])+" "+model.get("b")+" "+model.get("a")+" ?");
		}else if("6".equals(expType)){//间隔
			job.setExp("0 0/"+model.get("a")+" * * * ?");
		}else if("7".equals(expType)){//间隔
			job.setExp("0 0 0/"+model.get("a")+" * * ?");
		}
		
		simpleDaoSupport.saveOrUpdate(job);
		
		
		changeStatus(job.getId(),job.getStatus());
	}
	
	public TeeJob getById(String id){
		return (TeeJob) simpleDaoSupport.get(TeeJob.class, id);
	}
	
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		String hql = " from TeeJob ";
		
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		dataGridJson.setTotal(total);
		dataGridJson.setRows(simpleDaoSupport.pageFind(hql+" order by type asc", dm.getFirstResult(), dm.getRows(), null));
		
		
		return dataGridJson;
	}
	
}
