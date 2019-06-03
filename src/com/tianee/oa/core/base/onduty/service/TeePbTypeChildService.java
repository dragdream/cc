package com.tianee.oa.core.base.onduty.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.onduty.bean.TeePbTypeChild;
import com.tianee.oa.core.base.onduty.dao.TeePbTypeChildDao;
import com.tianee.oa.core.base.onduty.model.TeePbTypeChildModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeePbTypeChildService extends TeeBaseService {

	@Autowired
	private TeePbTypeChildDao teePbTypeChildDao;

	/**
	 * 字段列表
	 * */
	public TeeEasyuiDataGridJson findFieldAll(HttpServletRequest request,TeeDataGridModel m) {
		TeeEasyuiDataGridJson easyJson=new TeeEasyuiDataGridJson();
		int typeId = TeeStringUtil.getInteger(request.getParameter("typeId"),0);
		List<TeePbTypeChildModel> listModel=new ArrayList<TeePbTypeChildModel>();
		String hql="from TeePbTypeChild where typeId =?";
		List<TeePbTypeChild> pageFind = teePbTypeChildDao.pageFind(hql, m.getFirstResult(), m.getRows(), new Object[]{typeId});
		Long count = teePbTypeChildDao.count("select count(*) "+hql, new Object[]{typeId});
		if(pageFind!=null && pageFind.size()>0){
			for(TeePbTypeChild pc:pageFind){
				TeePbTypeChildModel model=new TeePbTypeChildModel();
				model.setName(pc.getName());
				model.setSease(pc.getSease());
				model.setSid(pc.getSid());
				model.setTypeId(typeId);
				listModel.add(model);
			}
		}
		easyJson.setRows(listModel);
		easyJson.setTotal(count);
		return easyJson;
	}

	/**
	 * 添加或 修改字段
	 * */
	public TeeJson addOrUpdateField(TeePbTypeChildModel model) {
		TeeJson json=new TeeJson();
		if(model.getSid()>0){//修改
			TeePbTypeChild child = teePbTypeChildDao.get(model.getSid());
			child.setName(model.getName());
			child.setSease(model.getSease());
			teePbTypeChildDao.update(child);
		}else{//添加
			TeePbTypeChild pt=new TeePbTypeChild();
			pt.setName(model.getName());
			pt.setSease(model.getSease());
			pt.setTypeId(model.getTypeId());
			teePbTypeChildDao.save(pt);
		}
		json.setRtState(true);
		return json;
	}

	/**
	 * 删除字段
	 * @return 
	 * */
	public TeeJson deleteField(int sid) {
		TeeJson json=new TeeJson();
		if(sid>0){
			teePbTypeChildDao.delete(sid);
			json.setRtState(true);
			json.setRtMsg("删除成功");
		}else{
			json.setRtState(false);
			json.setRtMsg("删除失败");
		}
		return json;
	}
	
	
	
}
