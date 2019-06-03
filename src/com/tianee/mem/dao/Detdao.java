package com.tianee.mem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.mem.bean.Detbean;
import com.tianee.mem.bean.Membean;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class Detdao  extends TeeBaseDao<Detbean>{

	public long gettotal(int sid) {
		// TODO Auto-generated method stub
		return super.count("select count(id) from Detbean where sid="+sid, null);
	}

	public List<Detbean> listByPage(int firstResult, int rows,int sid) {
		String hql = "from Detbean where sid="+sid;
		
		return super.pageFind(hql, firstResult, rows, null);
	}

}
