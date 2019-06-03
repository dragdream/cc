package com.tianee.oa.core.phoneSms.sender;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.secure.TeePassEncryptMD5;

public class MeiLianRuanTongSender implements SmsSender{

	@Override
	public String send(String fromUser, String phone, String content) {
		
		String text="";
		try {
			
			
			// 获取调用接口后返回的流
			InputStream is = getSoapInputStream(fromUser, phone, content);
			byte b[] = new byte[1024];
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			int index = 0;
			while((index = is.read(b))!=-1){
				arrayOutputStream.write(b, 0, index);
			}
			is.close();
			String result = new String(arrayOutputStream.toByteArray());
			//System.out.println(result);
			 SAXReader reader = new SAXReader();
			Document document = (Document) reader.read(new ByteArrayInputStream(result.getBytes("utf-8")));//读取xml字符串，注意这里要转成输入流
			Element root = (Element) document.getRootElement();//获取根元素
			Element response = root.element("Body").element("SendSMSResponse");
			text = response.elementText("SendSMSResult");
			
			//System.out.println(text);
		    
		} catch (Exception e) {
				e.printStackTrace();
				
			}
		return text;
		
		/*// TODO Auto-generated method stub
		StringBuffer sb = null;
		URL url = null;
		HttpURLConnection connection = null;
		BufferedReader in = null;
		String inputline = "";
		try {
			// 创建StringBuffer对象用来操作字符串
			sb = new StringBuffer("http://m.5c.com.cn/api/send/?");
			// APIKEY
			sb.append("apikey="+TeeSysProps.getString("SMS_APIKEY"));
			//用户名
			sb.append("&username="+TeeSysProps.getString("SMS_USERNAME"));
			// 向StringBuffer追加密码
			sb.append("&password="+TeeSysProps.getString("SMS_PASSWORD"));
			// 向StringBuffer追加手机号码
			sb.append("&mobile="+phone);
			// 向StringBuffer追加消息内容转URL标准码
			String tpl = TeeSysProps.getString("SMS_TEMPLATE")+"";
			tpl = tpl.replace("${CONTENT}", content);
			tpl = tpl.replace("${DATE}", TeeDateUtil.format(new Date(), "yyyy-MM-dd hh:mm"));
			tpl = tpl.replace("${SENDER}", fromUser);
			sb.append("&content="+URLEncoder.encode(tpl,"GBK"));
			// 创建url对象
			url = new URL(sb.toString());
			// 打开url连接
			connection = (HttpURLConnection) url.openConnection();
			// 设置连接超时时间
			connection.setConnectTimeout(1000*(TeeSysProps.getInt("SMS_PHONE_SEND_TIME_OUT")));
			connection.setReadTimeout(1000*(TeeSysProps.getInt("SMS_PHONE_SEND_TIME_OUT")));
			// 设置url请求方式 ‘get’ 或者 ‘post’
			connection.setRequestMethod("POST");
			//测试连接
			connection.connect();
			// 发送
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			// 返回发送结果
			inputline = in.readLine();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				in.close();
			}catch(Exception ex){}
			try{
				connection.disconnect();
			}catch(Exception ex){}
			
		}
		return inputline;*/
	}
	
	//用户把SOAP请求发送给服务器端，并返回服务器点返回的输入流
		public InputStream getSoapInputStream(String fromUser, String phone, String content)throws Exception {
			try {
				// 获取请求规范
					String soap= getSoapRequest(fromUser, phone, content);
					if(soap == null){
						return null;
					}
				// 调用的webserviceURL
				URL url=new URL("http://172.24.152.139/Service.asmx");
				URLConnection conn=url.openConnection();
				conn.setUseCaches(false);
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setRequestProperty("POST", "/Service.asmx HTTP/1.1");
				conn.setRequestProperty("Host", "172.24.152.139");
				conn.setRequestProperty("Content-Length",Integer.toString(soap.length()));
				conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
				
				// 调用的接口方法是
				conn.setRequestProperty("SOAPAction","http://tempuri.org/SendSMS");
				OutputStream os = conn.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
				osw.write(soap);
				osw.flush();
				osw.close();
				
				// 获取webserivce返回的流
				InputStream is = conn.getInputStream();
				
				return is;
			} catch (Exception e) {
				e.printStackTrace();
				
				return null;
				
			}
		}
			//获取SOAP的请求头，
			
			private String getSoapRequest(String fromUser, String phone, String content) {
				String num="";
				String md51=TeePassEncryptMD5.crypt("bjwhzf"+phone+"BJwhzf20160412");
				
				String md5=md51.toUpperCase();
				StringBuffer sb = new StringBuffer();
				sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");			
			    sb.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
				sb.append("   <soap:Body>");
				sb.append("     <SendSMS xmlns=\"http://tempuri.org/\">");
				sb.append("         <sequenceId>1</sequenceId>");
				sb.append("         <user>bjwhzf</user>");
				sb.append("         <expendNum>6</expendNum>");
				sb.append("         <destinations>"+phone+"</destinations >");
				sb.append("         <message>"+content+"</message>");
				sb.append("         <md5>"+md5+"</md5>");
				sb.append("         <reportFlag>2</reportFlag>");
				sb.append("         <expireTime>3600</expireTime>");
				sb.append("      </SendSMS>");
				sb.append("   </soap:Body>");
				sb.append(" </soap:Envelope>");
				num = sb.toString();
//				System.out.println(num);    
				return num;
		}
	
}
