package com.tianee.oa.core.general.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.general.bean.TeeInterface;
import com.tianee.webframe.dao.TeeBaseDao;


@Repository("interfaceDao")
public class TeeInterfaceDao  extends TeeBaseDao<TeeInterface>  {
	
	/**
	 * 增加菜单
	 * @param TeeInterface
	 */
	public void addInterface(TeeInterface intf) {
		save(intf);	
	}
	
	/**
	 * 更新
	 * @param TeeInterface
	 */
	public void updaInterface(TeeInterface intf){
		update(intf);	
	}
	   
	/**
	 * 查询所有记录
	 * @param 
	 */
	public  List<TeeInterface> select() {
		Object[] values = null;
		String hql = "from TeeInterface";
		List<TeeInterface> list = (List<TeeInterface>) executeQuery(hql,values);
		return list;
	}
	
	
	/**
	 * 查询所有记录
	 * @param 
	 */
	public TeeInterface selectById(int id) {
		TeeInterface intf = get(id);
		return intf;
	}
}