//package com.tianee.test.syl;
//
//
//import java.util.Hashtable;
//
//import  org.apache.axiom.om.OMAbstractFactory; 
//import  org.apache.axiom.om.OMElement; 
//import  org.apache.axiom.om.OMFactory; 
//import  org.apache.axiom.om.OMNamespace; 
//import  org.apache.axis2.AxisFault; 
//import  org.apache.axis2.addressing.EndpointReference; 
//import  org.apache.axis2.client.Options; 
//import  org.apache.axis2.rpc.client.RPCServiceClient; 
//
//
//
//import   javax.xml.namespace.QName;    
//
//public  class   TestMain {    
//	static   Hashtable<String, String> deptExtInfo2 = new Hashtable<String, String>() ;
//	static{
//		   deptExtInfo2.put("telePhoneNumber", "010-2121212");
//           deptExtInfo2.put("mobile", "15010098993");
//           deptExtInfo2.put("mail", "2323@sina.com");
//	 }
//	public final static String WEBSERVICE_URL = "http://localhost:88/oaop/services/TeeOrgSync?wsdl";
//    public final static String WEB_COM_URL = "http://org.websevice.censoft.thirdparty.tianee.com";
//	public  static  void   main(String args[]){    
//
//         //    使用 RPC 方式调用 WebService            
//
//         // getRPCServiceClient2();  
//    	// addDept();
//    	updateDept();
//    	//deleteDept();
//    	 
//    	// addUser();
//    	// updateUser();
//    	 //deleteUser();
//    }  
//     /**
//      * 新建部门    
//      */
//     public static  void  addDept(){
//    	  RPCServiceClient serviceClient;  
//          try  { 
//             serviceClient =  new  RPCServiceClient(); 
//             Options options = serviceClient.getOptions();    
//               //    指定调用 WebService 的 URL    
////           是浏览器中的访问地址
//             EndpointReference targetEPR =  new   EndpointReference(WEBSERVICE_URL);    
//             options.setTo(targetEPR);    
//                //    指定 sum 方法的参数值    
//             String containerCode = "123456789";
//             String deptName = "测试部门2";
//             String parentContainerCode = "12345678";
//            
//   
//             String deptExtJsonInfo = "{deptNo:9}";
//             Object[] opAddEntryArgs =  new   Object[] {containerCode, deptName , parentContainerCode , deptExtJsonInfo};    
//               //    指定 sum 方法返回值的数据类型的 Class 对象    
//             Class[] classes =  new  Class[] { Boolean.class   };    
//               //    指定要调用的 sum 方法及 WSDL 文件的命名空间 
//             //  第一个参数浏览器中看到 targetNamespace 的值 targetNamespace="http://service.axis.lcb.com"    第二个参数是方法名 
//             QName opAddEntry =  new  QName( WEB_COM_URL , "addDept"  );    
//               //    调用 sum 方法并输出该方法的返回值    
//             System.out.println(serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0]);   //  输出 3 
//         }  catch  (AxisFault e) { 
//              // TODO  Auto-generated catch block 
//             e.printStackTrace();  
//         }  
//     }
//     /**
//      * 更新部门    
//      */
//     public static  void  updateDept(){
//   	  RPCServiceClient serviceClient;  
//         try  { 
//            serviceClient =  new  RPCServiceClient(); 
//            Options options = serviceClient.getOptions();    
//              //    指定调用 WebService 的 URL    
////          是浏览器中的访问地址
//            EndpointReference targetEPR =  new   EndpointReference(WEBSERVICE_URL);    
//            options.setTo(targetEPR);    
//               //    指定 sum 方法的参数值    
//            String containerCode = "1234567829";
//            String deptName = "测试部门22233222";
//            String parentContainerCode = "1234567822";
//            String deptExtJsonInfo = "{deptNo:1}";
//            Object[] opAddEntryArgs =  new   Object[] {containerCode, deptName , parentContainerCode , deptExtJsonInfo};    
//              //    指定 sum 方法返回值的数据类型的 Class 对象    
//            Class[] classes =  new  Class[] { Boolean.class   };    
//              //    指定要调用的 sum 方法及 WSDL 文件的命名空间 
//            //  第一个参数浏览器中看到 targetNamespace 的值 targetNamespace="http://service.axis.lcb.com"    第二个参数是方法名 
//            QName opAddEntry =  new  QName( WEB_COM_URL , "updateDept"  );    
//              //    调用 sum 方法并输出该方法的返回值    
//            System.out.println(serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0]);   //  输出 3 
//        }  catch  (AxisFault e) { 
//             // TODO  Auto-generated catch block 
//            e.printStackTrace();  
//        }  
//    }
//      
//     /**
//      * 删除部门    
//      */
//     public static  void  deleteDept(){
//   	  RPCServiceClient serviceClient;  
//         try  { 
//            serviceClient =  new  RPCServiceClient(); 
//            Options options = serviceClient.getOptions();    
//              //    指定调用 WebService 的 URL    
////          是浏览器中的访问地址
//            EndpointReference targetEPR =  new   EndpointReference(WEBSERVICE_URL);    
//            options.setTo(targetEPR);    
//               //    指定 sum 方法的参数值    
//            String containerCode = "12345678";
//     
//            Object[] opAddEntryArgs =  new   Object[] {containerCode};    
//              //    指定 sum 方法返回值的数据类型的 Class 对象    
//            Class[] classes =  new  Class[] { Boolean.class   };    
//              //    指定要调用的 sum 方法及 WSDL 文件的命名空间 
//            //  第一个参数浏览器中看到 targetNamespace 的值 targetNamespace="http://service.axis.lcb.com"    第二个参数是方法名 
//            QName opAddEntry =  new  QName( WEB_COM_URL , "deleteDept"  );    
//              //    调用 sum 方法并输出该方法的返回值    
//            System.out.println(serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0]);   //  输出 3 
//        }  catch  (AxisFault e) { 
//             // TODO  Auto-generated catch block 
//            e.printStackTrace();  
//        }  
//    }
//     
//     
//     
//     
//     
//     
//     
//     
//     
//     /**
//      * 新建部门    
//      */
//     public static  void  addUser(){
//    	  RPCServiceClient serviceClient;  
//          try  { 
//             serviceClient =  new  RPCServiceClient(); 
//             Options options = serviceClient.getOptions();    
//               //    指定调用 WebService 的 URL    
////           是浏览器中的访问地址
//             EndpointReference targetEPR =  new   EndpointReference(WEBSERVICE_URL);    
//             options.setTo(targetEPR);    
//                //    指定 sum 方法的参数值    
//             String userId = "zhangsan";
//             String userName = "张三";
//             String userContainerCode = "123456";
//             String userPassword = "";
//             Hashtable deptExtInfo = new Hashtable() ;
//             deptExtInfo.put("telePhoneNumber", "010-2121212");
//             deptExtInfo.put("mobile", "15010098993");
//             deptExtInfo.put("mail", "2323@sina.com");
//             Object[] opAddEntryArgs =  new   Object[] {userId, userName , userContainerCode , userPassword , deptExtInfo};    
//               //    指定 sum 方法返回值的数据类型的 Class 对象    
//             Class[] classes =  new  Class[] { Boolean.class   };    
//               //    指定要调用的 sum 方法及 WSDL 文件的命名空间 
//             //  第一个参数浏览器中看到 targetNamespace 的值 targetNamespace="http://service.axis.lcb.com"    第二个参数是方法名 
//             QName opAddEntry =  new  QName( WEB_COM_URL , "addUser"  );    
//               //    调用 sum 方法并输出该方法的返回值    
//             System.out.println(serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0]);   //  输出 3 
//         }  catch  (AxisFault e) { 
//              // TODO  Auto-generated catch block 
//             e.printStackTrace();  
//         }  
//     }
//     /**
//      * 更新部门    
//      */
//     public static  void  updateUser(){
//   	  RPCServiceClient serviceClient;  
//         try  { 
//            serviceClient =  new  RPCServiceClient(); 
//            Options options = serviceClient.getOptions();    
//              //    指定调用 WebService 的 URL    
////          是浏览器中的访问地址
//            EndpointReference targetEPR =  new   EndpointReference(WEBSERVICE_URL);    
//            options.setTo(targetEPR);    
//               //    指定 sum 方法的参数值    
//            String userId = "zhangsan";
//            String userName = "张三sd";
//            String userContainerCode = "123456";
//            String userPassword = "";
//            
//         
//            System.out.println(deptExtInfo2);
//            Object[] opAddEntryArgs =  new   Object[] {userId, userName , userContainerCode , userPassword , deptExtInfo2};    
//              //    指定 sum 方法返回值的数据类型的 Class 对象    
//            Class[] classes =  new  Class[] { Boolean.class   };    
//              //    指定要调用的 sum 方法及 WSDL 文件的命名空间 
//            //  第一个参数浏览器中看到 targetNamespace 的值 targetNamespace="http://service.axis.lcb.com"    第二个参数是方法名 
//            QName opAddEntry =  new  QName( WEB_COM_URL , "updateUser"  );    
//              //    调用 sum 方法并输出该方法的返回值    
//            System.out.println(serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0]);   //  输出 3 
//        }  catch  (AxisFault e) { 
//             // TODO  Auto-generated catch block 
//            e.printStackTrace();  
//        }  
//    }
//      
//     /**
//      * 删除部门    
//      */
//     public static  void  deleteUser(){
//   	  RPCServiceClient serviceClient;  
//         try  { 
//            serviceClient =  new  RPCServiceClient(); 
//            Options options = serviceClient.getOptions();    
//              //    指定调用 WebService 的 URL    
////          是浏览器中的访问地址
//            EndpointReference targetEPR =  new   EndpointReference(WEBSERVICE_URL);    
//            options.setTo(targetEPR);    
//               //    指定 sum 方法的参数值    
//            String userId = "zhangsan";
//     
//            Object[] opAddEntryArgs =  new   Object[] {userId};    
//              //    指定 sum 方法返回值的数据类型的 Class 对象    
//            Class[] classes =  new  Class[] { Boolean.class   };    
//              //    指定要调用的 sum 方法及 WSDL 文件的命名空间 
//            //  第一个参数浏览器中看到 targetNamespace 的值 targetNamespace="http://service.axis.lcb.com"    第二个参数是方法名 
//            QName opAddEntry =  new  QName( WEB_COM_URL , "deleteUser"  );    
//              //    调用 sum 方法并输出该方法的返回值    
//            System.out.println(serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0]);   //  输出 3 
//        }  catch  (AxisFault e) { 
//             // TODO  Auto-generated catch block 
//            e.printStackTrace();  
//        }  
//    }
//     
//     
//     
//  
//}
//
//
//
// 