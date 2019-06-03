package com.tianee.thirdparty.sphy;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class TeeSphyTest {

	public static void main(String[] args) {
		//添加人员
		//addPerson();
		//修改人员
		//updatePerson();
		//删除人员
		deletePerson();
		//添加会议
		//addMeeting();
		//修改会议
		//updateMeeting();
        //删除会议
		//deleteMeeting();
		//会议权限
		//addQXMeeting();
		//findPerson();
		//addQXMeetingRole();
	}
	/**
	 * 添加人员
	 * */
	public static void addPerson(){
		try {  
		    String endpoint = "http://10.253.6.230:8080/acenter/services/iactiveService?wsdl";  
		    // 直接引用远程的wsdl文件  
		    // 以下都是套路  
		    Service service = new Service();  
		    Call call = (Call) service.createCall();  
		    call.setTargetEndpointAddress(endpoint);  
		    QName qn = new QName("http://service.iactive.com.cn", "userOperate");
		    call.setOperationName(qn);// WSDL里面描述的接口名称  
		   
		    call.addParameter("deptXmlInfo",  
		            org.apache.axis.encoding.XMLType.XSD_STRING,  
		            javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_UID",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_PASS",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    

		    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_INT);// 设置返回类型  
		    
		    String deptXmlInfo="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		    deptXmlInfo+="<USER>";
		    deptXmlInfo+="<OPT_METHOD>CREATE</OPT_METHOD>";
		    deptXmlInfo+="<IA_USERNAME>lisi</IA_USERNAME>";
		    deptXmlInfo+="<IA_USER_SERIAL>lisi</IA_USER_SERIAL>";
		    deptXmlInfo+="<IA_U_TRUENAME>lisi</IA_U_TRUENAME>";
		    deptXmlInfo+="<IA_U_PASSWORD>111111</IA_U_PASSWORD>";
		    deptXmlInfo+="<IA_DEPT_SERIAL>1</IA_DEPT_SERIAL>";
		    deptXmlInfo+="</USER>";
		    String AUTH_UID = "admin"; 
		    String AUTH_PASS="iactive";
		    //String deptXmlInfo,String AUTH_UID,String AUTH_PASS
		    int result = (int) call.invoke(new Object[] {deptXmlInfo,AUTH_UID,AUTH_PASS});  
		    // 给方法传递参数，并且调用方法  
		     System.out.println("result is " + result);  
		 } catch (Exception e) {  
		      System.err.println(e.toString());  
		 }
	}
	/**
	 * 修改人员
	 * */
	public static void updatePerson(){
		try {  
		    String endpoint = "http://10.253.6.230:8080/acenter/services/iactiveService?wsdl";  
		    // 直接引用远程的wsdl文件  
		    // 以下都是套路  
		    Service service = new Service();  
		    Call call = (Call) service.createCall();  
		    call.setTargetEndpointAddress(endpoint);  
		    QName qn = new QName("http://service.iactive.com.cn", "userOperate");
		    call.setOperationName(qn);// WSDL里面描述的接口名称  
		   
		    call.addParameter("deptXmlInfo",  
		            org.apache.axis.encoding.XMLType.XSD_STRING,  
		            javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_UID",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_PASS",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    

		    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_INT);// 设置返回类型  
		    
		    String deptXmlInfo="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		    deptXmlInfo+="<USER>";
		    deptXmlInfo+="<OPT_METHOD>UPDATE</OPT_METHOD>";
		    deptXmlInfo+="<IA_USER_SERIAL>test2</IA_USER_SERIAL>";
		    deptXmlInfo+="<IA_U_TRUENAME>张三</IA_U_TRUENAME>";
		    deptXmlInfo+="<IA_U_PASSWORD>666666</IA_U_PASSWORD>";
		    deptXmlInfo+="<IA_DEPT_SERIAL>1</IA_DEPT_SERIAL>";
		    deptXmlInfo+="</USER>";
		    String AUTH_UID = "admin"; 
		    String AUTH_PASS="iactive";
		    //String deptXmlInfo,String AUTH_UID,String AUTH_PASS
		    int result = (int) call.invoke(new Object[] {deptXmlInfo,AUTH_UID,AUTH_PASS});  
		    // 给方法传递参数，并且调用方法  
		     System.out.println("result is " + result);  
		 } catch (Exception e) {  
		      System.err.println(e.toString());  
		 }
	}
	
	/**
	 * 删除人员
	 * */
	public static void deletePerson(){
		try {  
		    String endpoint = "http://10.253.6.230:8080/acenter/services/iactiveService?wsdl";  
		    // 直接引用远程的wsdl文件  
		    // 以下都是套路  
		    Service service = new Service();  
		    Call call = (Call) service.createCall();  
		    call.setTargetEndpointAddress(endpoint);  
		    QName qn = new QName("http://service.iactive.com.cn", "userOperate");
		    call.setOperationName(qn);// WSDL里面描述的接口名称  
		   
		    call.addParameter("deptXmlInfo",  
		            org.apache.axis.encoding.XMLType.XSD_STRING,  
		            javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_UID",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_PASS",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    

		    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_INT);// 设置返回类型  
		    
		    String deptXmlInfo="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		    deptXmlInfo+="<USER>";
		    deptXmlInfo+="<OPT_METHOD>DELETE</OPT_METHOD>";
		    deptXmlInfo+="<IA_USER_SERIAL>ceshi002</IA_USER_SERIAL>";
		    deptXmlInfo+="</USER>";
		    String AUTH_UID = "admin"; 
		    String AUTH_PASS="iactive";
		    //String deptXmlInfo,String AUTH_UID,String AUTH_PASS
		    int result = (int) call.invoke(new Object[] {deptXmlInfo,AUTH_UID,AUTH_PASS});  
		    // 给方法传递参数，并且调用方法  
		     System.out.println("result is " + result);  
		 } catch (Exception e) {  
		      System.err.println(e.toString());  
		 }
	}
	/**
	 * 添加会议
	 * */
	public static void addMeeting(){
		try {  
		    String endpoint = "http://10.253.6.230:8080/acenter/services/iactiveMeetingService?wsdl";  
		    // 直接引用远程的wsdl文件  
		    // 以下都是套路  
		    Service service = new Service();  
		    Call call = (Call) service.createCall();  
		    call.setTargetEndpointAddress(endpoint);  
		    QName qn = new QName("http://service.meeting.acenter.iactive.com.cn", "roomOperate");
		    call.setOperationName(qn);// WSDL里面描述的接口名称  
		   
		    call.addParameter("deptXmlInfo",  
		            org.apache.axis.encoding.XMLType.XSD_STRING,  
		            javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_UID",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_PASS",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    

		    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_INT);// 设置返回类型  
		    
		    String deptXmlInfo="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		    deptXmlInfo+="<ROOM>";
		    deptXmlInfo+="<OPT_METHOD>CREATE</OPT_METHOD>";
		    deptXmlInfo+="<IA_ROOMNAME>关于XXX讨论会议</IA_ROOMNAME>";
		    deptXmlInfo+="<IA_R_APP>2</IA_R_APP>";
		    deptXmlInfo+="</ROOM>";
		    String AUTH_UID = "admin"; 
		    String AUTH_PASS="iactive";
		    //String deptXmlInfo,String AUTH_UID,String AUTH_PASS
		    int result = (int) call.invoke(new Object[] {deptXmlInfo,AUTH_UID,AUTH_PASS});  
		    // 给方法传递参数，并且调用方法  
		     System.out.println("result is " + result);  
		 } catch (Exception e) {  
		      System.err.println(e.toString());  
		 }
	}
	/**
	 * 修改会议
	 * */
	public static void updateMeeting(){
		try {  
		    String endpoint = "http://10.253.6.230:8080/acenter/services/iactiveMeetingService?wsdl";  
		    // 直接引用远程的wsdl文件  
		    // 以下都是套路  
		    Service service = new Service();  
		    Call call = (Call) service.createCall();  
		    call.setTargetEndpointAddress(endpoint);  
		    QName qn = new QName("http://service.meeting.acenter.iactive.com.cn", "roomOperate");
		    call.setOperationName(qn);// WSDL里面描述的接口名称  
		   
		    call.addParameter("deptXmlInfo",  
		            org.apache.axis.encoding.XMLType.XSD_STRING,  
		            javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_UID",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_PASS",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    

		    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_INT);// 设置返回类型  
		    
		    String deptXmlInfo="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		    deptXmlInfo+="<ROOM>";
		    deptXmlInfo+="<OPT_METHOD>UPDATE</OPT_METHOD>";
		    deptXmlInfo+="<IA_ROOMID>6</IA_ROOMID>";
		    deptXmlInfo+="<IA_ROOMNAME>2018年底关于XXX会议名2222</IA_ROOMNAME>";
		    deptXmlInfo+="<IA_R_APP>2</IA_R_APP>";
		    deptXmlInfo+="</ROOM>";
		    String AUTH_UID = "admin"; 
		    String AUTH_PASS="iactive";
		    //String deptXmlInfo,String AUTH_UID,String AUTH_PASS
		    int result = (int) call.invoke(new Object[] {deptXmlInfo,AUTH_UID,AUTH_PASS});  
		    // 给方法传递参数，并且调用方法  
		     System.out.println("result is " + result);  
		 } catch (Exception e) {  
		      System.err.println(e.toString());  
		 }
	}
	/**
	 * 删除会议
	 * */
	public static void deleteMeeting(){
		try {  
		    String endpoint = "http://10.253.6.230:8080/acenter/services/iactiveMeetingService?wsdl";  
		    // 直接引用远程的wsdl文件  
		    // 以下都是套路  
		    Service service = new Service();  
		    Call call = (Call) service.createCall();  
		    call.setTargetEndpointAddress(endpoint);  
		    QName qn = new QName("http://service.meeting.acenter.iactive.com.cn", "roomOperate");
		    call.setOperationName(qn);// WSDL里面描述的接口名称  
		   
		    call.addParameter("deptXmlInfo",  
		            org.apache.axis.encoding.XMLType.XSD_STRING,  
		            javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_UID",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_PASS",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    

		    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_INT);// 设置返回类型  
		    
		    String deptXmlInfo="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		    deptXmlInfo+="<ROOM>";
		    deptXmlInfo+="<OPT_METHOD>DELETE</OPT_METHOD>";
		    deptXmlInfo+="<IA_ROOMID>4</IA_ROOMID>";
		    deptXmlInfo+="</ROOM>";
		    String AUTH_UID = "admin"; 
		    String AUTH_PASS="iactive";
		    //String deptXmlInfo,String AUTH_UID,String AUTH_PASS
		    int result = (int) call.invoke(new Object[] {deptXmlInfo,AUTH_UID,AUTH_PASS});  
		    // 给方法传递参数，并且调用方法  
		     System.out.println("result is " + result);  
		 } catch (Exception e) {  
		      System.err.println(e.toString());  
		 }
	}
	/**
	 * 授权会议
	 * */
	public static void addQXMeeting(){
		try {  
		    String endpoint = "http://10.253.6.230:8080/acenter/services/iactiveMeetingService?wsdl";  
		    // 直接引用远程的wsdl文件  
		    // 以下都是套路  
		    Service service = new Service();  
		    Call call = (Call) service.createCall();  
		    call.setTargetEndpointAddress(endpoint);  
		    QName qn = new QName("http://service.meeting.acenter.iactive.com.cn", "roomPermOperate");
		    call.setOperationName(qn);// WSDL里面描述的接口名称  
		   
		    call.addParameter("roomPermXmlInfo",  
		            org.apache.axis.encoding.XMLType.XSD_STRING,  
		            javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_UID",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_PASS",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    

		    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_INT);// 设置返回类型  
		    
		    String deptXmlInfo="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		    deptXmlInfo+="<ROOM_PERM>";
		    deptXmlInfo+="<OPT_METHOD>CREATE</OPT_METHOD>";
		    deptXmlInfo+="<PERM_TYPE>1</PERM_TYPE>";
		    deptXmlInfo+="<PERM_OBJ_TYPE>1</PERM_OBJ_TYPE>";
		    deptXmlInfo+="<IA_SRC_INCLUDE_CHILD>0<IA_SRC_INCLUDE_CHILD>";
		    deptXmlInfo+="<PERM_SRC>5</PERM_SRC>";
		    deptXmlInfo+="<PERM_OBJ OBJ=\"44\" ROLE=\"1\"></PERM_OBJ>";
		    deptXmlInfo+="</ROOM_PERM>";
		    String AUTH_UID = "admin"; 
		    String AUTH_PASS="iactive";
		    //String deptXmlInfo,String AUTH_UID,String AUTH_PASS
		    int result = (int) call.invoke(new Object[] {deptXmlInfo,AUTH_UID,AUTH_PASS});  
		    // 给方法传递参数，并且调用方法  
		     System.out.println("result is " + result);  
		 } catch (Exception e) {  
		      System.err.println(e.toString());  
		 }
	}
	
	/**
	 * 查找用户
	 * */
	public static void findPerson(){
		try {  
		    String endpoint = "http://10.253.6.230:8080/acenter/services/iactiveService?wsdl";  
		    // 直接引用远程的wsdl文件  
		    // 以下都是套路  
		    Service service = new Service();  
		    Call call = (Call) service.createCall();  
		    call.setTargetEndpointAddress(endpoint);  
		    QName qn = new QName("http://service.iactive.com.cn", "getUserInfo");
		    call.setOperationName(qn);// WSDL里面描述的接口名称  
		   
		    call.addParameter("userXmlInfo",  
		            org.apache.axis.encoding.XMLType.XSD_STRING,  
		            javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_UID",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_PASS",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    

		    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型  
		    
		    String deptXmlInfo="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		    deptXmlInfo+="<USER>";
		    deptXmlInfo+="<IA_USER_SERIAL>test</IA_USER_SERIAL>";
		    deptXmlInfo+="</USER>";
		    String AUTH_UID = "admin"; 
		    String AUTH_PASS="iactive";
		    //String deptXmlInfo,String AUTH_UID,String AUTH_PASS
		    String result = (String) call.invoke(new Object[] {deptXmlInfo,AUTH_UID,AUTH_PASS});  
		    // 给方法传递参数，并且调用方法  
		     System.out.println("result is " + result);  
		 } catch (Exception e) {  
		      System.err.println(e.toString());  
		 }
	}
	/**
	 * 授权会议角色
	 * */
	public static void addQXMeetingRole(){
		try {  
		    String endpoint = "http://10.253.6.230:8080/acenter/services/iactiveMeetingService?wsdl";  
		    // 直接引用远程的wsdl文件  
		    // 以下都是套路  
		    Service service = new Service();  
		    Call call = (Call) service.createCall();  
		    call.setTargetEndpointAddress(endpoint);  
		    QName qn = new QName("http://service.meeting.acenter.iactive.com.cn", "setMeetingRole");
		    call.setOperationName(qn);// WSDL里面描述的接口名称  
		   
		    call.addParameter("roomId",  
		            org.apache.axis.encoding.XMLType.XSD_INT,  
		            javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("userId",  
				    org.apache.axis.encoding.XMLType.XSD_LONG,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("userSerial",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("userType",  
				    org.apache.axis.encoding.XMLType.XSD_INT,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_UID",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_PASS",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  

		    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_INT);// 设置返回类型  
		    
		    String AUTH_UID = "admin"; 
		    String AUTH_PASS="iactive";
		    //String deptXmlInfo,String AUTH_UID,String AUTH_PASS
		    int result = (int) call.invoke(new Object[] {5,0,"lisi",2,AUTH_UID,AUTH_PASS});  
		    // 给方法传递参数，并且调用方法  
		     System.out.println("result is " + result);  
		 } catch (Exception e) {  
		      System.err.println(e.toString());  
		 }
	}
}
