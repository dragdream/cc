package com.tianee.oa.subsys.vmeeting.synchronoususer;
import java.io.DataInputStream;
import java.io.IOException;  
import java.net.HttpURLConnection;
import java.net.URL;   
import com.tianee.oa.core.org.bean.TeePerson;
public class ReserverConf {  
		public static void reserConf(TeePerson tp)throws IOException{
        /**  
         * 此文件为同步用户的demo程序。
         */    
	    //需要以json格式来组织；
	    /*String par = "" +
	    		"{\"type\":\"conf\",\"confid\":\"\",\"now\":\"\",\"topic\":" +
	    		"\"大家好\",\"attendnum\":\"7\",\"maxvideo\":\"7\",\"maxpersonspeak\":" +
	    		"\"7\",\"begintime\":\"2014-01-01 10:50\",\"endtime\":" +
	    		"\"2014-03-28 10:50\",\"chairmanpwd\":\"yyyyyy\",\"confuserpwd\":" +
	    		"\"yyyyyy\",\"content\":\"eee\",\"username\":\"admin\",\"userpd\":" +
	    		"\"admin\",\"h323_usernum\":\"0\"," +
	    		"\"videotype\":\"320*240\",\"bandwidth\":\"256\",\"videocode\":\"2\",\"h323_videotype\":" +
	    		"\"1\",\"h323_bandwidth\":\"384\",\"h323_videocode\":" +
	    		"\"3\",\"driftnum\":\"0\",\"multinum\":\"0\",\"sip_usernum\":\"0\"," +
	    		"\"pstn_usernum\":\"0\",\"confshow\":\"0\",\"confusertype\":" +
	    		"\"0\",\"voip_usernum\":\"0\",\"audiotype\":\"5\",\"encrypt\":\"\"}";
        */
	  
	  String par =""+"{\"type\":\"user\",\"deptid\":\""+tp.getDept().getGuid()+"\",\"deptname\":\""+tp.getDept().getDeptName()+"\",\"userid\":"+
	 "\""+tp.getUuid()+"\",\"new\":\"yes\",\"level\":\"\",\"roleid\":\"3\",\"username\":\""+tp.getByname()+" \",\"sex\":\""+tp.getSex()+" \",\"realname\":"
	 + "\""+tp.getUserName()+" \",\"userpass\":\""+tp.getPassword()+"  \",\"email\":\""+tp.getEmail()+"\",\"mobile\":\""+tp.getMobilNo()+"\",\"telephone\":\""+tp.getTelNoDept()+"\"}";

	    
	    //URL  url = new URL("http://192.168.1.164/web/conferences/confsave.php");
	      URL url = new URL("http://127.0.0.1:80/ web/users/usersave.php");
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
	    System.out.println("code    " + code);
        //如果是200 代表请求成功；
	    if (code == 200) {
               DataInputStream in = new DataInputStream(httpurlconnection.getInputStream());
               int len = in.available();
               byte[] by = new byte[len];
               in.readFully(by);
               String rev = new String(by,"utf-8");
               //res 为服务器返回的字符串，这个前面可能会有一些乱码字符，需要自己吧其屏蔽掉
               System.out.println(rev);
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
    }     
    
   public static void main(String[] args) throws IOException {     
    	reserConf(null);     
    }    
}  