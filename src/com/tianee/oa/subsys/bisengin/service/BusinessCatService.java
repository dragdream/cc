package com.tianee.oa.subsys.bisengin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.bisengin.bean.BisTableField;
import com.tianee.oa.subsys.bisengin.bean.BusinessCat;
import com.tianee.oa.subsys.bisengin.dao.BusinessCatDao;
import com.tianee.oa.subsys.bisengin.model.BisTableFieldModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
@Service
public class BusinessCatService extends TeeBaseService{

	@Autowired
	private BusinessCatDao businessCatDao;
	
	/**
	 * 获取所有业务分类的列表
	 * @param cat
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson datagrid(BusinessCat cat,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = businessCatDao.datagrid(cat, dm);
		List<BusinessCat> list = dataGridJson.getRows();
		dataGridJson.setRows(list);
		return dataGridJson;
	}
	
	

	/**
	 * 删除业务分类
	 * @param cat
	 */
	public void deleteBusinessCatById(BusinessCat cat) {
		businessCatDao.deleteByObj(cat);
	}


    /**
     * 根据主键获取业务类别的详情
     * @param sid
     * @return
     */
	public BusinessCat getBusinessCatById(int sid) {
		BusinessCat cat=businessCatDao.get(sid);
		return cat;
	}


	
    /**
     * 新建或者更新业务类别
     * @param cat
     * @return
     */
	public TeeJson addOrUpdate(BusinessCat cat) {
		TeeJson json=new TeeJson();
		int  sid=cat.getSid();
		if(sid>0){//更新操作
			businessCatDao.update(cat);
			json.setRtMsg("更新成功");
		}else{//新建操作
			businessCatDao.save(cat);
			json.setRtMsg("新建成功");
		}
		json.setRtState(true);
		return json;
	}

 
    /**
     * 获取业务分类的列表
     * @return
     */
	public List<BusinessCat> getBusinessCatList() {
		String hql="from BusinessCat cat order by cat.sortNo asc";
	    List<BusinessCat>list= businessCatDao.executeQuery(hql,null);
	    return list;
	}
}
