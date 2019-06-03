package com.tianee.oa.core.base.fileNetdisk.test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.tianee.webframe.util.servlet.HttpClientUtil;

public class TeeHttpClient {
	//测试
	public static void main(String[] args) throws Exception {

	
	
		//测试文档中心文件夹数据
			String fileName="文件夹31";//文件夹名称
			String createTime="2018-09-27 17:15";
			String fileName1="文件夹32";//文件夹名称
			String createTime1="2018-09-27 17:15";
			String filePath = "/文件夹31/文件夹32";
			String attachName="测试附件.docx";//附件名称
			String path="D:/oaop/lucene/pubnetdisk/测试附件.docx";//附件路径
			
			
			
			String parm="[{\"fileName\":\""+fileName+"\",\"createTime\":\""+createTime+"\",\"filePath\":\""+filePath+"\","
					+ "\"fileInfo\":[{ \"attachName\":\""+attachName+"\",\"path\":\""+path+"\"}],"
					+ "\"child\":[{ \"fileName\":\""+fileName1+"\",\"createTime\":\""+createTime1+"\","
							+ "\"child\":[{\"fileName\":\""+fileName1+"\",\"createTime\":\""+createTime1+"\"}]},"
									+ "{ \"fileName\":\""+fileName1+"\",\"createTime\":\""+createTime1+"\"}] }]";
			
			System.out.println(parm);
			Map params=new HashMap();
			params.put("json",parm);
			//String respStr = HttpClientUtil.requestGet("http://192.168.199.201/teeFileInterfaceController/addOrUpdateFileNetdisk.action?parm="+ URLEncoder.encode(parm,"UTF-8"));
			String url="http://192.168.199.201/teeFileInterfaceController/addOrUpdateFileNetdisk.action";
			
			String respStr1 =HttpClientUtil.requestPost(params, url);
			
			JSONObject js = JSONObject.fromObject(respStr1);
			String status=String.valueOf(js.get("status"));
			System.out.println(status);
		
			//测试文档中心文件数据

			/*String attachmentName="54ce339c554943d5b904fd10a37bbe60.docx";//附件名称
			String attachmentPath ="1809";//目录年月
			String createTime ="2018-09-28 17:32";//文件创建时间
			String ext = "docx";//文件扩展名称
			String fileName="测试文档.docx";//文件显示名称
			int attachSize = 1365;//文件大小
			String attachmentFilePath = "xxxxxxxxxxxxxx";//文件路径
			
			int userId = 4;//上传者id  先默认系统管理员
			String fileFullPath="/文件夹31/文件夹32/cc/";//文件目录全路径
			
			
			
			
			String parm="{\"attachmentName\":\""+attachmentName+"\",\"attachmentPath\":\""+attachmentPath+"\",\"createTime\":\""+createTime+"\","
					+ "\"ext\":\""+ext+"\",\"fileName\":\""+fileName+"\",\"attachSize\":\""+attachSize+"\",\"attachmentFilePath\":\""+attachmentFilePath+"\","
							+ "\"userId\":\""+userId+"\",\"fileFullPath\":\""+fileFullPath+"\"}";
			
			System.out.println(parm);
			//Map params=new HashMap();
			//params.put("json",parm);
			String respStr = HttpClientUtil.requestGet("http://192.168.199.201/teeFileInterfaceController/uploadNetdiskFile.action?parm="+ URLEncoder.encode(parm,"UTF-8"));
			//String url="http://192.168.199.201/teeFileInterfaceController/uploadNetdiskFile.action";
			System.out.println(respStr);
			//String respStr1 =HttpClientUtil.requestPost(params, url);
			
			//JSONObject js = JSONObject.fromObject(respStr1);
			JSONObject js = JSONObject.fromObject(parm);
			String status=String.valueOf(js.get("status"));
			System.out.println(status);*/
			
	 
	}

}
