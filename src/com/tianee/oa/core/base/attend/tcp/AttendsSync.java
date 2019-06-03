package com.tianee.oa.core.base.attend.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
//考勤同步

import com.tianee.webframe.util.global.TeeSysProps;

public class AttendsSync {

	 public static Socket socket;
	 private static int port =TeeSysProps.getInt("ATTEND_TCP_PORT");// 默认服务器端口  
	  
	 public AttendsSync() {  
	 }  
	  
	 // 创建指定端口的服务器  
	 public AttendsSync(int port) {  
	     this.port = port;  
	 }  
	  
	 // 提供服务  
	 public static void service(){  
	    	ServerSocket server=null;
			try {
				server = new ServerSocket(port);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
	    	while(true){
	            try {  
		            // 等待客户连接  
		            socket = server.accept();
		            socket.setSoTimeout(1000*5);
	            }catch(Exception e){
	            	//e.printStackTrace();
	            }finally {// 建立连接失败的话不会执行socket.close();  
	            	
	            } 
	    	} 
	    }  
	  
}
