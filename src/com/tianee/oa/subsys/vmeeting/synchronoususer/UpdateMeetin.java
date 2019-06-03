package com.tianee.oa.subsys.vmeeting.synchronoususer;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.tianee.oa.subsys.vmeeting.bean.Meeting;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeJsonUtil;
public class  UpdateMeetin {  
public static Map<String,String> upd(Meeting mt) throws IOException { 	Map<String,String> map=new HashMap<String, String>();
        /**   
         * 此文件为预定会议的demo程序。
         */    
	    //预定会议字符串 ，需要以json格式来组织；4
	            String s=TeeDateUtil.format(mt.getStartTime());
	            String d=TeeDateUtil.format(mt.getEndTime());
	         //   int start=Integer.parseInt(s);
	         //   System.out.println(start);
	            
	    String par = "" +
	    		"{\"type\":\"conf\",\"confid\":\""+mt.getMeetingNumber()+"\",\"now\":\"YES\",\"topic\":" +
	    		"\""+mt.getMeetingName()+"\",\"attendnum\":\""+mt.getAttendNum()+"\",\"maxvideo\":\""+mt.getMaxvideo()+"\",\"maxpersonspeak\":" +
	    		"\""+mt.getMaxPersonspeak()+"\",\"begintime\":\""+s+"\",\"endtime\":" +
	    		"\""+d+"\",\"chairmanpwd\":\""+mt.getChairmanPwd()+"\",\"confuserpwd\":" +
	    		"\""+mt.getConfuserPwd()+"\",\"content\":\""+mt.getContent()+"\",\"username\":\""+TeeSysProps.getString("VMT_ADMIN_ID")+"\",\"userpd\":" +
	    		"\""+TeeSysProps.getString("VMT_ADMIN_PWD")+"\",\"h323_usernum\":\"0\"," +
	    		"\"videotype\":\"320*240\",\"bandwidth\":\"256\",\"videocode\":\"2\",\"h323_videotype\":" +
	    		"\"1\",\"h323_bandwidth\":\"384\",\"h323_videocode\":" +
	    		"\"3\",\"driftnum\":\"0\",\"multinum\":\"0\",\"sip_usernum\":\"0\"," +
	    		"\"pstn_usernum\":\"0\",\"confshow\":\"0\",\"confusertype\":" +
	    		"\"0\",\"voip_usernum\":\"0\",\"audiotype\":\"5\",\"encrypt\":\"\"}";
        
	    
	    
	    
	    URL  url = new URL("http://"+TeeSysProps.getString("VMT_IP")+":"+TeeSysProps.getString("VMT_PORT")+"/web/conferences/confsave.php");
	   
	    HttpURLConnection httpurlconnection = null;
	    try
	    {
	    httpurlconnection = (HttpURLConnection) url.openConnection();
	    httpurlconnection.setDoInput(true);
	    httpurlconnection.setDoOutput(true);

	    httpurlconnection.setRequestMethod("POST");
	    httpurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //发送到服务器的信息
	    String sendinfo = "txt_json="+par;
	    httpurlconnection.getOutputStream().write(sendinfo.getBytes("utf-8"));
	    httpurlconnection.getOutputStream().flush();
	    httpurlconnection.getOutputStream().close();
	    int code = httpurlconnection.getResponseCode();
	   // System.out.println("code    " + code);
        //如果是200 代表请求成功；
	   
	    if (code == 200) {
               DataInputStream in = new DataInputStream(httpurlconnection.getInputStream());
               int len = in.available();
               byte[] by = new byte[len];
               in.readFully(by);
               String  rev = new String(by,"utf-8");
               //res 为服务器返回的字符串，这个前面可能会有一些乱码字符，需要自己吧其屏蔽掉
              String ss=rev.substring(5,rev.length()-2);
              ss=ss.replaceAll("\"", "\'");//重点*********
          //   System.out.println(ss);
             map=TeeJsonUtil.JsonStr2Map(ss);
               in.close();
            
	    }
	    
	    
	    if  (code==404)
	    {
	    	//网络异常
	    }
	   } catch (Exception e) {
	    e.printStackTrace();
	   } finally {
	    if (httpurlconnection != null) {
	     httpurlconnection.disconnect();
	    }
	   } 
	    return map  ;
    }     
    
    public static void main(String[] args) throws IOException {     
    	upd(null);     
    }     
}  