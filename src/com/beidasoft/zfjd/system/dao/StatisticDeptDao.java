package com.beidasoft.zfjd.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.system.bean.StatisticDept;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 部门信息数量统计表DAO类
 */
@Repository
public class StatisticDeptDao extends TeeBaseDao<StatisticDept> {
	
	public List<StatisticDept> getByDeptId(String id){
    	String sql = "from StatisticDept where isDelete = 0 and deptId = '"+id+"'";
		return   super.pageFind(sql, 0, 1, null);
    }
}
