package com.tianee.mem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.mem.bean.Recbean;
import com.tianee.mem.dao.Recdao;
import com.tianee.webframe.service.TeeBaseService;
@Service
public class Recservice extends TeeBaseService{
	@Autowired
	private Recdao recD;

	public void save(Recbean recbean) {
		// TODO Auto-generated method stub
		recD.save(recbean);
	}

	public List<Recbean> listByPage(int firstResult, int rows, int sid) {
		// TODO Auto-generated method stub
		 return  recD.listByPage(firstResult, rows,sid);
	}

	public long gettotal(int sid) {
		// TODO Auto-generated method stub
		return recD.gettotal(sid);
	}

	public Recbean getbyid(int id) {
		return recD.get(id);
	}

	public void update(Recbean eleinfo) {
		// TODO Auto-generated method stub
		recD.update(eleinfo);
	}

}
