package com.tianee.oa.core.priv.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.priv.bean.TeeSysMenu;
import com.tianee.oa.core.priv.bean.TeeSysMenuPriv;
import com.tianee.oa.core.priv.dao.TeeMenuDao;
import com.tianee.oa.core.priv.dao.TeeSysMenuPrivDao;
import com.tianee.oa.core.priv.model.TeeSysMenuModel;
import com.tianee.oa.core.priv.model.TeeSysMenuPrivModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * @author syl
 *
 */
@Service
public class TeeSysMenuPrivService extends TeeBaseService{

	@Autowired
	private TeeSysMenuPrivDao menuPrivDao;
	
	@Autowired
	private TeeMenuDao menuDao;
	
    /**
     * 新增
     * @author syl
     * @date 2014-2-25
     * @param TeeSysMenuPriv
     */
	public TeeJson addOrUpdate(TeeSysMenuPrivModel model , HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeSysMenuPriv priv = new TeeSysMenuPriv();
		if(!TeeUtility.isNullorEmpty(model.getSysMenuIds())){
			List<TeeSysMenu> menuList = menuDao.getSysMenuListByUuids(model.getSysMenuIds());
			priv.setMenuPriv(menuList);
		}
		if(model.getSid() > 0){
			TeeSysMenuPriv oldPriv = menuPrivDao.selectById(model.getSid());
			if(oldPriv != null){
				BeanUtils.copyProperties(model, oldPriv);
				oldPriv.setMenuPriv(priv.getMenuPriv());
				menuPrivDao.updateMenuPriv(oldPriv);
			}else{
				json.setRtState(false);
				json.setRtMsg("该菜单权限已被删除！");
				return json;
			}
			
		}else{
			BeanUtils.copyProperties(model, priv);
			menuPrivDao.addMenuPriv(priv);
			
		}
		json.setRtState(true);
		return json;
	}

	
	/**
	 * 查询 byId
	 * @param TeeSysMenuPriv
	 */
	public TeeJson selectById(int id) {
		TeeJson json = new TeeJson();
		TeeSysMenuPriv priv  = menuPrivDao.selectById(id);
		if(priv != null){
			TeeSysMenuPrivModel model = parseModel(priv);
			json.setRtData(model);
			json.setRtState(true);
		}else{
			json.setRtMsg("该模块权限已被删除！");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * 删除 byId
	 * @param TeeSysMenuPriv
	 */
	public void deleteById(int id) {
		menuPrivDao.deleteById(id);
	}
	
	
	/**
	 * 删除 byIds
	 * @param TeeSysMenuPriv
	 */
	public void deleteByIds(HttpServletRequest request) {
		String sid = TeeStringUtil.getString(request.getParameter("sids"));
		menuPrivDao.deleteByIds(sid);
	}
	

	/**
	 * 查询 所有记录
	 * @param 
	 */
	public void deleteAll() {
		menuPrivDao.deleteAll();
	}
	
	/**
	 * 查询 所有记录
	 * @param 
	 */
	public List<TeeSysMenuPriv> selectAll() {
		return menuPrivDao.selectAll();
	}
	
	/**
	 * 根据有效性 查询
	 * @param 
	 */
	public List<TeeSysMenuPriv> selectByPrivFlag(TeeSysMenuPriv priv) {
		return menuPrivDao.selectByPrivFlag(priv);
	}
	
	/**
	 * 条件查询
	 * @param 
	 */
	public List<TeeSysMenuPrivModel> selectByTerm(TeeSysMenuPrivModel model , String menuIds) {
		List<TeeSysMenuPrivModel> modelList = new  ArrayList<TeeSysMenuPrivModel>();
		List<TeeSysMenuPriv> list = menuPrivDao.selectByTerm(model, menuIds);
		for (int i = 0; i < list.size(); i++) {
			TeeSysMenuPrivModel m  = parseModel(list.get(i));
			modelList.add(m);
		}
		return modelList ;
	}
	/**
	 * 转对象
	 * @author syl
	 * @date 2014-2-25
	 * @param priv
	 * @return
	 */
	public TeeSysMenuPrivModel parseModel(TeeSysMenuPriv priv){
		TeeSysMenuPrivModel model = new TeeSysMenuPrivModel();
		if(priv != null){
			BeanUtils.copyProperties(priv, model);
			String sysMenuIds = "";
			String sysMenuNames = "";
			List<TeeSysMenu> menuList = priv.getMenuPriv();
			if(menuList != null && menuList.size() >0){
				for(TeeSysMenu menu : menuList){
					sysMenuIds = sysMenuIds + menu.getUuid() + ",";
					sysMenuNames = sysMenuNames + menu.getMenuName() + ",";
				}
				
			}
			model.setSysMenuIds(sysMenuIds);
			model.setSysMenuNames(sysMenuNames);
		}
		return model;
	}
	
	/**
	 * 检查时候有校验路径
	 * @param 
	 */
	public boolean checkSysMenuPriv(HttpServletRequest request ) {
		String URL = TeeStringUtil.getString(request.getParameter("URL"));
		TeeSysMenuPrivModel model = new TeeSysMenuPrivModel();
		model.setPrivUrl(URL);
		long count =  menuPrivDao.checkExistUrl(model);
		if(count <= 0 ){
			TeeSysMenuPriv priv = new TeeSysMenuPriv();
			priv.setPrivUrl(model.getPrivUrl());
			
		}
		return true ;
	}
	
	
	
	/**
	 * 根据有权限的菜单 获取所有权限路径  获取权限路径和 action/URL  ----  
	 * @author syl
	 * @date 2014-2-26
	 * @param model
	 * @param menuIds  菜单Id字符串  以逗号分隔
	 * @return
	 */
	public List<TeeSysMenuPriv> selectPrivByMenuIds(TeeSysMenuPrivModel model , String menuIds) {
		List<TeeSysMenuPriv> list = menuPrivDao.selectByPriv(model, menuIds);
		return list ;
	}
}
