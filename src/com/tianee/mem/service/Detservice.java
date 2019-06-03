package com.tianee.mem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.mem.bean.Detbean;
import com.tianee.mem.dao.Detdao;
import com.tianee.webframe.service.TeeBaseService;
@Service
public class Detservice extends TeeBaseService{
 @Autowired
 private Detdao detD;

public void save(Detbean memb) {
	// TODO Auto-generated method stub
	
	detD.save(memb);
}

public void del(int id) {
	// TODO Auto-generated method stub
	detD.delete(id);
}

public long gettotal(int id) {
	// TODO Auto-generated method stub
	
	return detD.gettotal(id);
}

public List<Detbean> listByPage(int firstResult, int rows,int id) {
	// TODO Auto-generated method stub
	 return  detD.listByPage(firstResult, rows,id);
}

public Detbean getbyid(int id) {
	// TODO Auto-generated method stub
	return detD.get(id);
}

public void update(Detbean eleinfo) {
	// TODO Auto-generated method stub
	detD.update(eleinfo);
}
}
