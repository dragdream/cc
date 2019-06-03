package com.jacob.req;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;


public class HttpClientUtil {
	
	public static long sessionCreateTime = System.currentTimeMillis();
	
	/**
	 * 
	 * @param cookie cookie信息
	 * @param params 请求参数
	 * @param url 请求地址
	 * @return
	 */
	public static String request(Map<String,String> cookie,Map<String,String> params,String url,int timeout){
		String content = null;
		InputStream in = null;
		try {
			HttpParams params0 = new BasicHttpParams();
			List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 
			//写入请求
			if(params!=null){
				Set<String> keys = params.keySet();
				for(String key : keys){
					formparams.add(new BasicNameValuePair(key, String.valueOf(params.get(key))));
				}
			}
			DefaultHttpClient client = new DefaultHttpClient(params0);
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout); 
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
			
			//写入cookie
			CookieStore cookies = client.getCookieStore();
//			BasicClientCookie cookie_ = new BasicClientCookie("JSESSIONID", Main.JSESSIONID);
//			cookie_.setPath("/");
//			cookie_.setDomain(Main.address.split(":")[0]);
//	        cookies.addCookie(cookie_);
			client.setCookieStore(cookies);
			
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			HttpPost post = new HttpPost(url);
			post.setEntity(uefEntity);
			HttpResponse response = client.execute(post);
			BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(response.getEntity());
			in = bufferedHttpEntity.getContent();
			byte b[] = new byte[in.available()];
			int total = in.read(b);
			if(total==-1){
				content = "";
			}else{
				content = new String(b,0,total,"utf-8");
			}
			if("{\"timeout\":1}".equals(content)){//如果已失效，则重新进行登陆，然后继续请求源地址

				in.close();
//				cookies = client.getCookieStore();
//				cookie_ = new BasicClientCookie("JSESSIONID", Main.JSESSIONID);
//				cookie_.setPath("/");
//				cookie_.setDomain(Main.address.split(":")[0]);
//		        cookies.addCookie(cookie_);
				client.setCookieStore(cookies);
				
				uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
				post = new HttpPost(url);
				post.setEntity(uefEntity);
				response = client.execute(post);
				bufferedHttpEntity = new BufferedHttpEntity(response.getEntity());
				in = bufferedHttpEntity.getContent();
				b = new byte[in.available()];
				total = in.read(b);
				if(total==-1){
					content = "";
				}else{
					content = new String(b,0,total,"utf-8");
				}
				in.close();
			}
			return content;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			content = "{\"rtState\":false,\"rtMsg\":\""+e.getMessage()+",网络未连接\",\"rtData\":{}}";
			content = null;
			return content;
		}
	}
	
	public static String request(Map<String,String> cookie,Map<String,String> params,String url){
		return request(cookie,params,url,10000);
	}
	
	public static void download(Map<String,String> cookie,Map<String,String> params,String url,String filePath){
		try {
			HttpParams params0 = new BasicHttpParams();
			List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 
			//写入请求
			if(params!=null){
				Set<String> keys = params.keySet();
				for(String key : keys){
					formparams.add(new BasicNameValuePair(key, String.valueOf(params.get(key))));
				}
			}
			DefaultHttpClient client = new DefaultHttpClient(params0);
			
			//写入cookie
//			CookieStore cookies = client.getCookieStore();
//			BasicClientCookie cookie_ = new BasicClientCookie("JSESSIONID", Main.JSESSIONID);
//			cookie_.setPath("/");
//			cookie_.setDomain(Main.address.split(":")[0]);
//	        cookies.addCookie(cookie_);
//			client.setCookieStore(cookies);
			
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			HttpPost post = new HttpPost(url);
			post.setEntity(uefEntity);
			HttpResponse response = client.execute(post);
			BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(response.getEntity());
			InputStream in = bufferedHttpEntity.getContent();
			
			if(in.available()==13){//尝试判断一下失效
				byte smallByte[] = new byte[13];
				in.read(smallByte);
				in.close();
				String result = new String(smallByte);
				if("{\"timeout\":1}".equals(result)){//失效
				}
				
//				cookies = client.getCookieStore();
//				cookie_ = new BasicClientCookie("JSESSIONID", Main.JSESSIONID);
//				cookie_.setPath("/");
//				cookie_.setDomain(Main.address.split(":")[0]);
//		        cookies.addCookie(cookie_);
//				client.setCookieStore(cookies);
				
				uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
				post = new HttpPost(url);
				post.setEntity(uefEntity);
				response = client.execute(post);
				bufferedHttpEntity = new BufferedHttpEntity(response.getEntity());
				in = bufferedHttpEntity.getContent();
			}
			
			byte b[] = new byte[4096];
			int length = 0;
			File file = new File(filePath);
			if(!file.exists()){
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			OutputStream output = new FileOutputStream(file);
			while((length=in.read(b))!=-1){
				output.write(b,0,length);
			}
			output.close();
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取文件二进制流  syl
	 * @param cookie
	 * @param params
	 * @param url
	 * @return
	 */
	public static byte[] downloadFile(Map<String,String> cookie,Map<String,String> params,String url){
		try {
			HttpParams params0 = new BasicHttpParams();
			List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 
			//写入请求
			if(params!=null){
				Set<String> keys = params.keySet();
				for(String key : keys){
					formparams.add(new BasicNameValuePair(key, String.valueOf(params.get(key))));
				}
			}
			DefaultHttpClient client = new DefaultHttpClient(params0);
//			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000*60);
//			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000*60);
			
			//写入cookie
//			CookieStore cookies = client.getCookieStore();
//			BasicClientCookie cookie_ = new BasicClientCookie("JSESSIONID", Main.JSESSIONID);
//			cookie_.setPath("/");
//			cookie_.setDomain(Main.address.split(":")[0]);
//	        cookies.addCookie(cookie_);
//			client.setCookieStore(cookies);
			
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			HttpPost post = new HttpPost(url);
			post.setEntity(uefEntity);
			HttpResponse response = client.execute(post);
			BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(response.getEntity());
			InputStream in = bufferedHttpEntity.getContent();
			if(in.available()==13){//尝试判断一下失效
				byte smallByte[] = new byte[13];
				in.read(smallByte);
				in.close();
				String result = new String(smallByte);
				


//				cookies = client.getCookieStore();
//				cookie_ = new BasicClientCookie("JSESSIONID", Main.JSESSIONID);
//				cookie_.setPath("/");
//				cookie_.setDomain(Main.address.split(":")[0]);
//		        cookies.addCookie(cookie_);
//				client.setCookieStore(cookies);
				
				uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
				post = new HttpPost(url);
				post.setEntity(uefEntity);
				response = client.execute(post);
				bufferedHttpEntity = new BufferedHttpEntity(response.getEntity());
				in = bufferedHttpEntity.getContent();
			}
		      ByteArrayOutputStream bos = new ByteArrayOutputStream();
		      byte[] buffer = new byte[1024];
		      int len = 0;
		      while ((len = in.read(buffer)) != -1) {
		          bos.write(buffer, 0, len);
		      }
		      byte[] dataByte = bos.toByteArray();
		      bos.close();
				in.close();
		      return dataByte;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param cookie cookie信息
	 * @param params 请求参数
	 * @param url 请求地址
	 * @param fileList 文件list
	 * @return
	 */
	public static String requestPostForm(Map<String,String> cookie,Map<String,String> params , List<Map<String,Object>> fileList,String url,int timeout){
		String content = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			//写入cookie
//			if(cookie!=null){
//				Set<String> keys = cookie.keySet();
//				CookieStore cookies = client.getCookieStore();
//				for(String key : keys){
//					BasicClientCookie cookie_ = new BasicClientCookie("JSESSIONID",Main.JSESSIONID);
//					cookie_.setPath("/");
//					cookie_.setDomain(Main.address.split(":")[0]);
//			        cookies.addCookie(cookie_);
//				}
//				client.setCookieStore(cookies);
//			}
			MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, 
		               null, Charset.forName("UTF-8"));  
			//参数
			Set<String> keys = params.keySet();
			for(String key : keys){
				reqEntity.addPart(key, 
						new StringBody(String.valueOf(params.get(key)), Charset.forName("UTF-8")));
			}
			//参数
			for (int i = 0; i < fileList.size(); i++) {
				String path = (String) fileList.get(i).get("filePath");
				String fileName = (String) fileList.get(i).get("fileName");
				File file = new File(path);
				// 添加文件参数
				if (file != null && file.exists()) {
					reqEntity.addPart("file_" + i, new FileBody(file));
				}
			}
		
			HttpPost post = new HttpPost(url);
			
		    //设置请求  
			post.setEntity(reqEntity);  
			HttpResponse response = client.execute(post);
			BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(response.getEntity());
			InputStream in = bufferedHttpEntity.getContent();
			byte b[] = new byte[in.available()];
			int total = in.read(b);
			content = new String(b,0,total,"utf-8");
			if("{\"timeout\":1}".equals(content)){//如果已失效，则重新进行登陆，然后继续请求源地址
				
				in.close();
//				cookies = client.getCookieStore();
//				cookie_ = new BasicClientCookie("JSESSIONID", Main.JSESSIONID);
//				cookie_.setPath("/");
//				cookie_.setDomain(Main.address.split(":")[0]);
//		        cookies.addCookie(cookie_);
//				client.setCookieStore(cookies);
				
				reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, 
			               null, Charset.forName("UTF-8"));  
				post = new HttpPost(url);
				post.setEntity(reqEntity);
				response = client.execute(post);
				bufferedHttpEntity = new BufferedHttpEntity(response.getEntity());
				in = bufferedHttpEntity.getContent();
				b = new byte[in.available()];
				total = in.read(b);
				if(total==-1){
					content = "";
				}else{
					content = new String(b,0,total,"utf-8");
				}
				in.close();
			}
			return content;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			content = "";
			return content;
		}
	}
	
	/**
	 * 提交版式文件
	 * @param cookie
	 * @param params
	 * @param filePath
	 * @param url
	 * @param timeout
	 * @return
	 */
	public static String uploadFile(Map<String,String> cookie,Map<String,String> params , String filePath,String url,int timeout){
		String content = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			//写入cookie
//			if(cookie!=null){
//				Set<String> keys = cookie.keySet();
//				CookieStore cookies = client.getCookieStore();
//				for(String key : keys){
//					BasicClientCookie cookie_ = new BasicClientCookie("JSESSIONID",Main.JSESSIONID);
//					cookie_.setPath("/");
//					cookie_.setDomain(Main.address.split(":")[0]);
//			        cookies.addCookie(cookie_);
//				}
//				client.setCookieStore(cookies);
//			}
			MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, 
		               null, Charset.forName("UTF-8"));  
			//参数
			if(params!=null){
				Set<String> keys = params.keySet();
				for(String key : keys){
					reqEntity.addPart(key, 
							new StringBody(String.valueOf(params.get(key)), Charset.forName("UTF-8")));
				}
			}
			
			
			File file = new File(filePath);
			reqEntity.addPart("file", new FileBody(file));
		
			HttpPost post = new HttpPost(url);
			
		    //设置请求  
			post.setEntity(reqEntity);  
			HttpResponse response = client.execute(post);
			BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(response.getEntity());
			InputStream in = bufferedHttpEntity.getContent();
			byte b[] = new byte[in.available()];
			int total = in.read(b);
			content = new String(b,0,total,"utf-8");
			if("{\"timeout\":1}".equals(content)){//如果已失效，则重新进行登陆，然后继续请求源地址
				
				in.close();
//				cookies = client.getCookieStore();
//				cookie_ = new BasicClientCookie("JSESSIONID", Main.JSESSIONID);
//				cookie_.setPath("/");
//				cookie_.setDomain(Main.address.split(":")[0]);
//		        cookies.addCookie(cookie_);
//				client.setCookieStore(cookies);
				
				reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, 
			               null, Charset.forName("UTF-8"));  
				post = new HttpPost(url);
				post.setEntity(reqEntity);
				response = client.execute(post);
				bufferedHttpEntity = new BufferedHttpEntity(response.getEntity());
				in = bufferedHttpEntity.getContent();
				b = new byte[in.available()];
				total = in.read(b);
				if(total==-1){
					content = "";
				}else{
					content = new String(b,0,total,"utf-8");
				}
				in.close();
			}
			return content;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			content = "";
			return content;
		}
	}
	
}
