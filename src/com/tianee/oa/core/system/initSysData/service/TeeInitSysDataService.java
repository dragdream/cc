package com.tianee.oa.core.system.initSysData.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.oaconst.TeeSysParaKeys;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.service.TeeBaseService;
/**
 * 初始化系统参数
 * @author zhp
 * @createTime 2014-1-17
 * @desc
 */
@Service
public class TeeInitSysDataService extends TeeBaseService{

	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;
	/**
	 * 要更新的 初始化参数...放在 map中
	 */
	private Map initMap;
	
	private boolean isInit = false;
	
	/**
	 * 初始化 数据
	 * @author zhp
	 * @createTime 2014-1-17
	 * @editTime 上午09:37:35
	 * @desc
	 */
	public void init( Map initM){
		initMap = initM;
		if(initMap!= null && initMap.size() > 0){
			isInit = true;
		}
		
	}
	/**
	 * @author zhp
	 * @createTime 2014-1-17
	 * @editTime 上午09:38:38
	 * @desc
	 */
	public void doInitData(){
		if(!isInit){
			return;
		}
		Set<String> key = initMap.keySet();       
		for (Iterator it = key.iterator(); it.hasNext();) {           
			String pName = (String) it.next();
			boolean isExist = paraExist(pName);
			if(!isExist){
				insertPara(pName,(String)initMap.get(pName));
			}
		}
		
	}
	
	/**
	 * @author zhp
	 * @createTime 2014-1-17
	 * @editTime 上午09:38:47
	 * @desc
	 */
	public boolean paraExist(String key){
		boolean isExist = false;
		String hql  ="from TeeSysPara s where s.paraName = '"+key+"'";
		List result = null;
		result = simpleDaoSupport.find(hql, null);
		if(result !=null && result.size() > 0){
			isExist = true;
		}
		return isExist;
	}
	
	/**
	 * @author zhp
	 * @createTime 2014-1-17
	 * @editTime 上午09:38:52
	 * @desc
	 */
	public void insertPara(String key,String value){
		TeeSysPara sPara = new TeeSysPara();
		sPara.setParaName(key);
		sPara.setParaValue(value);
		simpleDaoSupport.save(sPara);
	}
	/**
	 * @author zhp
	 * @createTime 2014-1-17
	 * @editTime 上午09:38:58
	 * @desc
	 */
	public void updatePara(String key,String value){
		String hql  ="update TeeSysPara s set s.paraValue = ? where s.paraName = '"+key+"'";
		simpleDaoSupport.executeUpdate(hql, new Object[]{value});
	}
	
	/**
	 * @author zhp
	 * @createTime 2014-1-17
	 * @editTime 上午09:39:24
	 * @desc
	 */
	public void deletePara(String key,Object[] objects){
		
	}
	/**
	 * @author zhp
	 * @createTime 2014-1-17
	 * @editTime 上午09:39:03
	 * @desc
	 */
	public TeeSimpleDaoSupport getSimpleDaoSupport() {
		return simpleDaoSupport;
	}

	public void setSimpleDaoSupport(TeeSimpleDaoSupport simpleDaoSupport) {
		this.simpleDaoSupport = simpleDaoSupport;
	}
	
}
