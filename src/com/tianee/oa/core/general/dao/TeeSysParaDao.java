package com.tianee.oa.core.general.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.core.priv.dao.TeeMenuGroupDao;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
@Repository("sysParaDao")
public class TeeSysParaDao extends TeeBaseDao<TeeSysPara> {
	@Autowired
	private TeeMenuGroupDao menuDao;
	
	/**
	 * 增加菜单
	 * @param TeeSysPara
	 */
	public void addSysPara(TeeSysPara sysPara) {
		save(sysPara);	
	}
	
	/**
	 * 更新
	 * @param TeeSysMenu
	 */
	public void updateSysPara(TeeSysPara sysPara) {
		update(sysPara);	
	}
	   
	/**
	 * 查询所有记录
	 * @param 
	 */
	public List<TeeSysPara> selectSysPara() {
		Object[] values = null;
		String hql = "from TeeSysPara";
		List<TeeSysPara> list = (List<TeeSysPara>) executeQuery(hql,values);
		return list;
	}
	
	/**
	 * 删除  by uuid
	 * @param TeeSysPara
	 */
	public void delSysParaById(String uuid) {
		delete(uuid);
	}
	/**
	 * 删除  by paraName
	 * @param TeeSysPara
	 */
	public void delSysPara(String paraName) {
		String hql = "delete from TeeSysPara where paraName = ?";
		Object[] values = {paraName};
		deleteOrUpdateByQuery(hql, values);
	}
	
	/**
	 * get  by paraName
	 * @param TeeSysPara
	 */
	public String getSysParaValue(String paraName) {
		String hql = "from TeeSysPara where paraName = ?";
		Object[] values = {paraName};
		List<TeeSysPara> list =  executeQuery(hql, values);
		if(list.size() > 0){
			return list.get(0).getParaValue();
		}
		return "";
	}
	
	/**
	 * 获取对象  by paraName
	 * @param TeeSysPara
	 */
	public TeeSysPara getSysPara(String paraName) {
		String hql = "from TeeSysPara where paraName = ?";
		Object[] values = {paraName};
		List<TeeSysPara> list =  executeQuery(hql, values);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	
	/**
	 * 获取对象  by paraNames  以逗号分隔
	 * @param TeeSysPara
	 */
	public List<TeeSysPara> getSysParaByNames(String paraNames) {
		paraNames = TeeStringUtil.getSqlStringParse(paraNames);
		String hql = "from TeeSysPara where paraName in (" + paraNames + ")";
		Object[] values = {};
		return executeQuery(hql, values);
	}
	

	/***
	 * 获取系统参数表，系统登录
	 * @return
	 */
	public Map getParaListToLogin(){
		String sql = " select para_name , para_value from SYS_PARA" +
		          " where para_name in" +
		          //密码过期时间
		          " ('SEC_PASS_TIME'" +
		          
		          //密码是否过期
		          ",'SEC_PASS_FLAG'" +
		          
		          //是否限制错误登陆
		          ",'SEC_RETRY_BAN'" +
		          
		          //重复登陆次数
		          ",'SEC_RETRY_TIMES'" +
		          
		          //多少分钟内禁止再次登陆

		          ",'SEC_BAN_TIME'" +
		          
		          //密码最小长度

		          ",'SEC_PASS_MIN'" +
		          
		          //密码最大长度

		          ",'SEC_PASS_MAX'" +
		          
		          //密码是否需要同时包含字母和数字
		          ",'SEC_PASS_SAFE'" +
		          
		          //登陆时记忆用户名
		          ",'SEC_USER_MEM'" +
		          
		          //是否将用户在线状态记录到PERSON表中
		          ",'SEC_ON_STATUS'" +
		          
		          //Ip规则不限制的用户
		          ",'IP_UNLIMITED_USER'" +
		          
		          //是否启用USBKey
		          ",'LOGIN_KEY'" +
		          
		          //启用USBKey是否需要输入用户名
		          ",'SEC_KEY_USER'" +
		          
		          //是否修改初始的密码
		          ",'SEC_INIT_PASS'" +
		          //是否使用验证码
		          ",'VERIFICATION_CODE'" +
		          //是否是高速波云办公平台 1是 其他不是
		          ",'GAO_SU_BO_VERSION'" +
		          //缺省界面风格(webos/经典界面)
		          ",'DEFAULT_INTERFACE_STYLE')";
		List list =   getBySql(sql, null);
		Map map = new HashMap();
		Iterator it = list.iterator();
	    while (it.hasNext()){
	    	Object[] array = (Object[])it.next();
	        String paraName = (String)array[0];
	        String paraValue = (String)  TeeStringUtil.clob2String(array[1]);
	        map.put(paraName, paraValue);
	     }
		return map;
	}
}
