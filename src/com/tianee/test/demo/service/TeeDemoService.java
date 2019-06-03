package com.tianee.test.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.test.demo.bean.TeeDemo;
import com.tianee.test.demo.dao.TeeDemoDao;
import com.tianee.test.demo.model.TeeDemoModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeDemoService extends TeeBaseService{
	
	@Autowired
	private TeeDemoDao demoDao;
	
	/**
	 * 按条件和分页查询
	 * @param demoModel 
	 * @param dataGridModel
	 * @return
	 */
	@Transactional(readOnly=true)//这里加入readonly，因为不需要进行事务的提交和回滚
	public TeeEasyuiDataGridJson query(TeeDemoModel demoModel,TeeDataGridModel dataGridModel){
		//创建easyui列表返回格式对象
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		//获取demoList
		List<TeeDemo> demoList = demoDao.query(demoModel, dataGridModel);
		
		//将demoList转换成模型集合
		List<TeeDemoModel> demoModelList = new ArrayList();
		
		//循环查询出来的实体集合
		for(TeeDemo demo:demoList){
			TeeDemoModel dm = new TeeDemoModel();
			
			//复制基础类型字段
			BeanUtils.copyProperties(demo, dm);
			
			//单独处理特殊字段类型
			dm.setDeptId(demo.getDept().getUuid());
			dm.setDeptName(demo.getDept().getDeptName());
			
			demoModelList.add(dm);
		}
		
		//根据条件获取数据的总数
		long total = demoDao.queryCount(demoModel);
		
		//将模型集合存入easyui对象中的rows中
		dataGridJson.setRows(demoModelList);
		//将查询总数放入total中
		dataGridJson.setTotal(total);
		
		
		return dataGridJson;
	}
	
	/**
	 * 保存
	 * @param demo
	 */
	public void save(TeeDemo demo){
		demoDao.save(demo);
	}
	
	/**
	 * 更新
	 * @param demo
	 */
	public void update(TeeDemo demo){
		demoDao.update(demo);
	}
	
	/**
	 * 通过id获取对象
	 * @param demo
	 */
	public TeeDemo getById(int sid){
		TeeDemo demo = demoDao.get(sid);
		return demo;
	}
	
	/**
	 * 通过对象删除
	 * @param demo
	 */
	public void deleteByObj(TeeDemo demo){
		demoDao.deleteByObj(demo);
	}
	
}
