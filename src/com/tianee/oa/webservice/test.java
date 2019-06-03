package com.tianee.oa.webservice;

import com.caucho.hessian.client.HessianProxyFactory;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {    
            String url = "http://127.0.0.1/ztService";
            HessianProxyFactory factory = new HessianProxyFactory();
            ZtWebService ws = (ZtWebService) factory.create(    
            		ZtWebService.class, url);
            ws.System_AuthMachineCode("asdzxcasd");
        } catch (Exception e) {
            e.printStackTrace();    
        }    
	}

}
