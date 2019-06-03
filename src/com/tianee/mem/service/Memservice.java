package com.tianee.mem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.mem.bean.Membean;
import com.tianee.mem.dao.Memdao;
import com.tianee.mem.model.Memmodel;
import com.tianee.webframe.service.TeeBaseService;
@Service
public class Memservice extends TeeBaseService{
	
@Autowired
private Memdao memD;
public void save(Membean memb){	
	memD.save(memb);	
}

public long gettotal(){
	return memD.gettotal();
}
public long gettotal(Memmodel searchModel){
	return memD.gettotal(searchModel);
	
}


/*
 * graid控件的展示，以及检索
 * 参数int firstResult,int rows,由控件自动提供，
 * 
 * */
public List<Membean> listByPage(int firstResult,int rows,Memmodel searchModel){

	return memD.listByPage(firstResult, rows,searchModel);
	
}

public void del(int id) {
	// TODO Auto-generated method stub
	memD.delete(id);
}

public Membean getbyid(int id) {
	// TODO Auto-generated method stub
	return memD.get(id);
}

public void update(Membean eleinfo) {
	// TODO Auto-generated method stub
	memD.update(eleinfo);
	
}
}
