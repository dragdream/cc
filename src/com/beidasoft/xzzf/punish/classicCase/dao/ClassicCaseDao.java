package com.beidasoft.xzzf.punish.classicCase.dao;


import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.classicCase.bean.ClassicCase;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 经典案例表DAO类
 */
@Repository
public class ClassicCaseDao extends TeeBaseDao<ClassicCase> {
	   
	/**
	 * 保存
	 */
	public void saveClassicCase(ClassicCase classicCase) {
		super.saveOrUpdate(classicCase);
	}
	
	
	/**
	 * 根据id查询
	 */
	public ClassicCase loadById(String id) {
		ClassicCase classicCase = super.get(id);
		return classicCase;
	}
	
	/**
	 * 批量，单个修改状态
	 * @param ids id或id串
	 * @param num 要改的数字
	 * @param param	要改的参数
	 * @return
	 */
	public int updateStatusByUuids(String  ids, String num, String param) {
		String[] str = ids.split(",");
		StringBuffer strIds = new StringBuffer("");
		for (int i = 0; i < str.length; i++) {
			strIds.append("'");
			strIds.append(str[i]);
			strIds.append("'");
			if(i < str.length - 1){
				strIds.append(",");
			}
		}
		String sql = "";
		//逻辑删除
		if("delFlag".equals(param)){
			sql = "update ClassicCase set "+param+" = ? where id in(" + strIds + ")";
		}
		
		Object[] values = {num};
		int count = deleteOrUpdateByQuery(sql, values);
		return count;
	}
	
		
}
