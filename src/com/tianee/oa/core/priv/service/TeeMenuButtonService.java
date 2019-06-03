package com.tianee.oa.core.priv.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.priv.bean.TeeMenuButton;
import com.tianee.oa.core.priv.dao.TeeMenuButtonDao;
import com.tianee.oa.core.priv.model.TeeMenuButtonModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;


@Service
public class TeeMenuButtonService extends TeeBaseService {

	@Autowired
	private TeeMenuButtonDao menuButtonDao;
	
	public TeeEasyuiDataGridJson getButtonListByMenuId(int menuId){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		Object[] values = {menuId};
		String hql = "from TeeMenuButton where menuId = ?";
		long total = simpleDaoSupport.count("select count(*) "+hql, values);
		dataGridJson.setTotal(total);
		List<TeeMenuButton> buttonList = simpleDaoSupport.find(hql+" order by sortNo asc", values);
		List<TeeMenuButtonModel> buttonModelList = new ArrayList<TeeMenuButtonModel>();
		for(TeeMenuButton button:buttonList) {
			TeeMenuButtonModel buttonModel = new TeeMenuButtonModel();
			BeanUtils.copyProperties(button, buttonModel);	
			buttonModelList.add(buttonModel);
		}
		dataGridJson.setRows(buttonModelList);
		return dataGridJson;
	}

	public TeeMenuButton getButtonById(int id) {
		return menuButtonDao.get(id);
	}
	
	public void save(TeeMenuButton menuButton ) {
		menuButtonDao.save(menuButton);
	}

	public void update(TeeMenuButton menuButton ) {
		menuButtonDao.update(menuButton);
	}
	
	public void deleteById(int id) {
		menuButtonDao.delete(id);
	}
	
	public List<TeeMenuButton> getAll() {
		return menuButtonDao.getAll();
	}
	
	public List<TeeMenuButton> selectButton(String hql, Object[] values) {
		List<TeeMenuButton> list = menuButtonDao.executeQuery(hql, values);
		return list;
	}
	
	public List<Map> getBtnPrivByMenuUuid(int personId,int menuId){		
		return menuButtonDao.getBtnPrivByMenuUuid(personId, menuId);
	}

}
