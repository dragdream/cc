package com.tianee.oa.core.ispirit.com;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeUtility;


public class TeeSocketUtil {


	  
	/**
	 * 推送消息的方法 --- UDP
	 * @author syl
	 * @date 2013-11-23
	 * @param receiverIp  服务器IP
	 * @param port 端口号
	 * @param content 消息内容
	 * @param charset  
	 * @throws IOException
	 */
	  public static void push(String receiverIp, int port, String content, String charset) throws IOException {  
	    try {
	      DatagramSocket socket = null;
	      DatagramPacket packet = null;
	      byte[] data = content.getBytes(charset);
	      socket = new DatagramSocket();
	      socket.setBroadcast(true);
	      packet = new DatagramPacket(data, data.length, InetAddress.getByName(receiverIp), port);
	      socket.send(packet);
	    } catch (UnknownHostException e) {
	      throw e;
	    }
	  }
	 /**
	  * 推送消息的方法,从配置中读取im服务器和端口号 --- 
	  * @author syl
	  * @date 2013-11-23
	  * @param content
	  * @throws IOException
	  */
	  public static void pushMessage(String content) throws IOException {  
	    //从配置文件中读取
	    String addr = "";//TeeSysProps.getString("IM_SERVER_ADDR");
	    String portStr = "";//TeeSysProps.getString("IM_SERVER_PORT");
//	    String addr = "192.168.1.1";
//	    String portStr = "";
	    int port = 6688;
	    if (TeeUtility.isNullorEmpty(addr)) {
	    	addr = "127.0.0.1";
	    }
	    if (TeeUtility.isNumber(portStr)) {
	    	port = Integer.parseInt(portStr);
	    }
	    push(addr, port, content, "UTF-8");
	  }
	  /**
	   * 接收UPD服务
	   * @author syl
	   * @date 2013-11-23
	   */
	  public static void getMessage(){
	  try {
			   //这里必须要指定socket的端口，不然我不知道是从哪个端口去接受数据
			   DatagramSocket datagramSocket = new DatagramSocket(6688);
			   byte[] buf = new byte[1024];
			   //这里是用datagrampacket接受发送过来的数据，所以不需要指定IP和port
			   DatagramPacket p = new DatagramPacket(buf, 1024);
			   datagramSocket.receive(p);
			   //这是得到接受过来的字节数组，其实就是上面的buf，也可以直接用上面的buf
			   byte [] b = p.getData();
			   
			   //这是得到接受到的数据的真实长度
			   int len = p.getLength();
			   System.out.println(len);
			   //这是得到发送放的IP
			   String sendIP = p.getAddress().getHostAddress();
			   System.out.println(sendIP);
			   //这是得到发送发的端口号
			   int port = p.getPort();
			   System.out.println(port);
			   //这是打印出接收到的数据
			   System.out.println(new String(b,0, len ));
			  } catch (Exception e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
			  }
	  }
	  
	  public static void main(String[] args) throws IOException {
		  String content = "122";
		  pushMessage(content);
		  
		  getMessage();
	    //pushMyStatus("1", "1111");
	    //pushSms("1,");
	   // pushAvatar("1", "1.gif");
	  }
}
