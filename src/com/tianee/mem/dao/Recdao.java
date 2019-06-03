package com.tianee.mem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.mem.bean.Membean;
import com.tianee.mem.bean.Recbean;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class Recdao extends TeeBaseDao<Recbean>{

	public List<Recbean> listByPage(int firstResult, int rows, int sid) {
		
         String hql = "from Recbean where sid="+sid;
		return super.pageFind(hql, firstResult, rows, null);
	}

	public long gettotal(int sid) {
		// TODO Auto-generated method stub
		return super.count("select count(id) from Recbean where sid="+sid, null);
	}

}
