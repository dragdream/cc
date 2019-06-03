package com.tianee.oa.core.workflow.pkgen;

import java.util.Date;

public class MyPKGenerator implements PKGenrator{

	@Override
	public Object generate() {
		// TODO Auto-generated method stub
		
		return new Date().toString();
	}
	
}
