package com.beidasoft.xzfy.common.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.alibaba.druid.util.StringUtils;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class FyCommonDao extends TeeBaseDao<Object>{

	/**
	 * 查询字典表类型(当类型为空，查询所有类型)
	 * @param type
	 * @return
	 */
	public List<?> getDictByType(String type){
		
		StringBuffer str = new StringBuffer();
		str.append("select column_name as type,code,CODE_DESC as codeDesc ");
		str.append("from FY_DICT where is_delete = 0 ");
		if( !StringUtils.isEmpty(type))
		{
			str.append(" and column_name = '");
			str.append(type);
			str.append("'");
		}
		Session session = this.getSession();
		List<?> result = null;
		Query query = session.createSQLQuery(str.toString());
		result = query.list();
		return result;
	}
}
