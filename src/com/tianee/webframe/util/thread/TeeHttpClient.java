package com.tianee.webframe.util.thread;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.HttpClientUtil;

public class TeeHttpClient {
	/*public String show(){
	String sequenceId="12";
	String user="李文军";
	String expendNum="123456";
	String destinations="18713340757";
	String message="发送成功！！！！";
    String md5="AFDDA50E67C2CDF0907FD2D777700F95";
    String reportFlag="1";
    String expireTime="2018.09.03 16:35";
	String respStr = HttpClientUtil.requestGet("http://192.168.31.101/ClientTest/deleteDept.action?"
			 +"sequenceId={"+sequenceId+"}&use={"+user+"}&expendNum={"+expendNum+"}&destinations={"+
			destinations+"}&message={"+message+"}&md5={"+md5+"}&reportFlag={"+reportFlag+"}&expireTime={"+expireTime+"}");
	
	
	return respStr;
	
	
	}*/

	public void show1() throws Exception{
		//测试添加状态接口
		/*String pendingTitle="测试113";//待办名称
		String url="http://127.0.0.1/system/pending/index.jsp";//办理地址
		String yiUrl="http://127.0.0.1/system/pending/addOrUpdate.jsp";//办理地址
		String model="HR";//判断系统
		String recUserIds="4"; //办理人id
		String sendUserIds="4";//发布人id
	
		String parm="{\"pendingTitle\":'"+pendingTitle+"',\"pendingContent\":'"+pendingTitle+"',\"url\":'"+url+"',\"model\":'"+model+"',\"recUserIds\":"+recUserIds+",\"sendUserIds\":"+sendUserIds+",\"yiUrl\":'"+yiUrl+"'}";
		System.out.println(parm);
		String respStr = HttpClientUtil.requestGet("http://10.253.6.227/saveInterface/save.action?parm="+ URLEncoder.encode(parm,"UTF-8"));
		System.out.println(respStr);
		
		JSONObject js = JSONObject.fromObject(parm);
		
		
		String status = String.valueOf(js.get("state"));*/
		//测试更改状态接口
		/*String renturn="{uuid:'"+"8a7d866365f5d4bc0165f5d839cb0004"+"',state:"+1+"}";	
		String respStr = HttpClientUtil.requestGet("http://10.253.6.227/saveInterface/update.action?renturn="+ URLEncoder.encode(renturn,"UTF-8"));
		System.out.println(respStr);*/
		
		//测试删除状态接口
		/*String uuid ="8a7d866365c620f70165c700b2310020";
		String respStr = HttpClientUtil.requestGet("http://10.253.6.227/saveInterface/delete.action?uuid="+ URLEncoder.encode(uuid,"UTF-8"));
		System.out.println(respStr);*/
		
		//讨论区测试
		//测试添加状态接口
		String subject="测试数据迁移004";//待办名称
		String content="xxxxxxxxxxxxxxxxxxxxx";//内容
		int anonymous=0;//是否为匿名发布  1：匿名  0：实名",
		String crTime="1998-12-01 12:23:45";//创建时间
		String section="40284b8165e510f00165e5af85900009"; //"所属板块ID
		String crUser="ceshi22";//创建人ID
		String lastReplyUser="ceshi16";//最后回复的用户ID
		String lastReplyTime="1999-01-01 12:23:45";//最后回复的时间
		int replyAmount=200;//回复数量
		int isTop=1;//是否置顶 
		int clickAccount=1;//点击数量
		int flag=1;//帖子状态
		//String replys="xxxxxxxxxxxxx";
		String hcrUser="ceshi2";//回复人
		String hcontent="测试回复";//回复内容
		String hcrTime="1999-01-01 12:23:45";//创建时间
		int hanonymous=1;//是否匿名
		
		String parm="{\"subject\":'"+subject+"',\"content\":'"+content+"',\"anonymous\":"+anonymous+",\"crTime\":'"+crTime+"',\"section\":'"+section+"',\"crUser\":'"+crUser+"',\"lastReplyUser\":'"+lastReplyUser+"',\"lastReplyTime\":'"+lastReplyTime+"',\"replyAmount\":"+replyAmount+",\"isTop\":"+isTop+",\"clickAccount\":"+clickAccount+",\"flag\":"+flag+",\"replys\": [{ \"hcrUser\":'"+hcrUser+"',\"hcontent\":'"+hcontent+"',\"hcrTime\":'"+hcrTime+"',\"hanonymous\":"+hanonymous+"},{ \"hcrUser\":'"+hcrUser+"',\"hcontent\":'"+hcontent+"',\"hcrTime\":'"+hcrTime+"',\"hanonymous\":"+hanonymous+"}] }";
		/*System.out.println(parm);
		String respStr = HttpClientUtil.requestGet("http://10.253.6.227/dataMigrationController/topic.action?parm="+ URLEncoder.encode(parm,"UTF-8"));*/
		
		Map params=new HashMap();
		params.put("json",parm);

		
		String url="http://10.253.6.227/dataMigrationController/topic.action";
		String respStr1 =HttpClientUtil.requestPost(params, url);
		JSONObject js = JSONObject.fromObject(respStr1);
		String status=String.valueOf(js.get("status"));
		System.out.println(status);
		
		
		
		//测试文档中心
		/*String fileName="测试4";//创建时间
		int fileNo=21;//是否匿名
		
		String parm="{\"fileNo\":"+fileNo+",\"fileName\":\""+fileName+"\"}";
		String respStr = HttpClientUtil.requestGet("http://10.253.6.227/dataMigrationController/addFileNetdisk.action?parm="+ URLEncoder.encode(parm,"UTF-8"));
		*/
		/*Map params=new HashMap();
		params.put("fileName","测试5");
		params.put("fileNo","22");
		String url="http://10.253.6.227/dataMigrationController/addFileNetdisk.action";
		String respStr1 =HttpClientUtil.requestPost(params, url);
		JSONObject js = JSONObject.fromObject(respStr1);
		String status=String.valueOf(js.get("status"));
		System.out.println(status);*/
		
		
		}
	
	
	
	
	public static void main(String[] args) throws Exception{
		TeeHttpClient cc=new TeeHttpClient();
		//cc.show1();
		cc.show1();//17130736150346
		}
	}

