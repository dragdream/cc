package com.tianee.test;

import java.io.File;
import java.io.FileOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import sun.misc.BASE64Decoder;

/**
 * 点聚签章 ,doc 转 aip 、pdf
 * 
 * @author zcy
 * 
 */
public class GetSeal2Dj {

	public void sendData() {
		// url 地址
		try {
			String result = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
					+ "<SealDocRequest>"
					+ "		<BASE_DATA>"
					+ "		        <!--应用系统id写死-->"
					+ "			<SYS_ID>sysId</SYS_ID>"
					+ "		        <!--用户id写死-->"
					+ "			<USER_ID>admin</USER_ID>"
					// + "		        <!--用户密码写死-->"
					// + "			<USER_PSD>admin</USER_PSD>"
					+ "		</BASE_DATA>"
					+ "		<META_DATA>"
					+ "		     <!--是否模板合并写死-->"
					+ "		    <IS_MERGER>false</IS_MERGER>"
					+ "		   </META_DATA>"
					+ "		<FILE_LIST>"
					+ "		   <TREE_NODE>"
					+ "		        <!--文档名称-->"
					+ "		        <FILE_NO>testNo5.pdf</FILE_NO>"
					+ "		        <!--是否加二维码-->"
					+ "		        <IS_CODEBAR>false</IS_CODEBAR>"
					+ "		        <!--固定写死空-->"
					+ "		        <CERT_CODEBAR></CERT_CODEBAR>"
					+ "		        <!--二维码类型1:p417,0:QR-->"
					+ "		        <CODEBAR_TYPE>0</CODEBAR_TYPE>"
					+ "		        <!--二维码编辑信息-->"
					+ "			<CODEBAR_DATA>000000000000000000</CODEBAR_DATA>"
					+ "		        <!--二维码左右偏移-->"
					+ "		        <X_COORDINATE>30000</X_COORDINATE>"
					+ "		        <!--二维码上下偏移-->"
					+ "			<Y_COORDINATE>5000</Y_COORDINATE>"
					+ "		        <!--二维码大小-->"
					+ "		        <CODEBAR_SIZE>100</CODEBAR_SIZE>"
					+ "		        <!--二维码加盖页码1,2,3,-1是最后一页0是所有页-->"
					+ "		        <CODEBAR_PAGE>0</CODEBAR_PAGE>"
					+ "		        <SEAL_TYPE>0</SEAL_TYPE>"
					+ "		        <RULE_TYPE>0</RULE_TYPE>"
					+ "		        <!--规则号，多个规则用逗号隔开-->"
					+ "		        <RULE_NO>1</RULE_NO>"
					+ "		        <!--应用场景data是模板数据合成，file是读取FILEPATH文件写死-->"
					+ "		        <CJ_TYPE>file</CJ_TYPE>"
					+ "		        <!--固定写死空-->"
					+ "		        <REQUEST_TYPE>0</REQUEST_TYPE>"
					+ "		        <!--读取文件路径-->"
					+ "		        <FILE_PATH>http://127.0.0.1:9239/Seal/testNo5.pdf</FILE_PATH>"
					+ "		        <!--模板名称-->"
					+ "		        <MODEL_NAME>模板1</MODEL_NAME>"
					+ "		        <AREA_SEAL>false</AREA_SEAL>"
					+ "		        <!--模板数据-->" + "		        <APP_DATA>"
					+ "		        <Info>" + "		         <name>李磊</name>"
					+ "		        </Info>" + "		       </APP_DATA>"
					+ "		    </TREE_NODE>" + "		</FILE_LIST>"
					+ "		</SealDocRequest>";
			String wsdlUrl = "http://127.0.0.1:9239/Seal/services/SealService?wsdl";
			String nameSpaceUri = "http://serv";
			Service service = new Service();
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(wsdlUrl));
			call.setOperationName(new QName(nameSpaceUri, "sealAutoPdf"));
			String s = (String) call.invoke(new Object[] { result });
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void operastionSeal(String wsdlUrl, String functionName, String xml) {
		// url 地址
		// wsdlUrl = "http://127.0.0.1:9239/Seal/services/SealService?wsdl";
		wsdlUrl = "http://www.dianju.cn:9236/Seal/services/SealService?wsdl";
		Call call;
		try {
			xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
					+ "<SealDocRequest>"
					+ "<FILE_LIST>"
					+ "   <TREE_NODE>"
					+ "        <!--要转换的文档名称-->"
					+ "        <FILE_NO>testNo5.doc</FILE_NO>"
					+ "        <!--读取文件路径-->"
					+ "        <FILE_PATH>http://www.dianju.cn:9236/Seal/testNo5.doc</FILE_PATH>"
					+ "    </TREE_NODE>"
					/*
					 * +"   <TREE_NODE>" +"        <!--要转换的文档名称-->"
					 * +"        <FILE_NO>testNo4.doc</FILE_NO>"
					 * +"        <!--读取文件路径-->" +
					 * "        <FILE_PATH>http://www.dianju.cn:9236/Seal/testNo4.doc</FILE_PATH>"
					 * +"    </TREE_NODE>"
					 */
					+ "</FILE_LIST>" + "</SealDocRequest>";
			String nameSpaceUri = "http://serv";
			Service service = new Service();
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(wsdlUrl));
			call.setOperationName(new QName(nameSpaceUri, functionName));
			String s = (String) call.invoke(new Object[] { xml });
			s = s.substring(s.indexOf("<FILE_MSG>") + 10,
					s.lastIndexOf("</FILE_MSG>"));
			System.out.println(s);
			FileOutputStream a = new FileOutputStream(new File("D:/a.aip"));
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] decodeBuffer = decoder.decodeBuffer(s);
			a.write(decodeBuffer);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SocketException {
		// GetSeal2Dj g = new GetSeal2Dj();
		// g.operastionSeal("","docAutoOfd","");
		// g.operastionSeal("","docAutoaip","");
		// g.sendData();

	}
}
