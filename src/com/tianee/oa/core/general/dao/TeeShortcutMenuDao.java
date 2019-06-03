package com.tianee.oa.core.general.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.tianee.oa.core.general.bean.TeeShortcutMenu;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository("shortcutMenuDao")
public class TeeShortcutMenuDao  extends TeeBaseDao<TeeShortcutMenu>  {
	/**
	 * 新增
	 * @author syl
	 * @date 2014-3-17
	 * @param intf
	 */
	public void addShortcutMenu(TeeShortcutMenu intf) {
		save(intf);	
	}
	
	/**
	 * 更新
	 * @author syl
	 * @date 2014-3-17
	 * @param intf
	 */
	public void updateShortcutMenu(TeeShortcutMenu intf){
		update(intf);	
	}
	   
	/**
	 * 根据人员查询
	 * @param 
	 */
	public  List<TeeShortcutMenu> selectByPersonId(TeePerson person) {
		Object[] values = null;
		String hql = "from TeeShortcutMenu where user.uuid =" + person.getUuid();
		List<TeeShortcutMenu> list = (List<TeeShortcutMenu>) executeQuery(hql,values);
		return list;
	}
	
	
	/**
	 * by Id
	 * @param 
	 */
	public TeeShortcutMenu selectById(int id) {
		TeeShortcutMenu intf = get(id);
		return intf;
	}
}