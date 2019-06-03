package com.tianee.webframe.util.str;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import com.tianee.webframe.util.str.expReplace.TeeExpFetcher;
import com.tianee.webframe.util.str.expReplace.TeeHTMLImgTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTag;
import com.tianee.webframe.util.str.expReplace.TeeRegexpAnalyser;


public class TeeUrlToFile {
	  /**多次使用的话不需要重新编译正则表达式了，对于频繁调用能提高效率*/
	  public static   final String patternString1="[^\\s]*((<\\s*[aA]\\s+(href\\s*=[^>]+\\s*)>)(.*)</[aA]>).*";
	  public static   final String patternString2=".*(<\\s*[aA]\\s+(href\\s*=[^>]+\\s*)>(.*)</[aA]>).*";
	  public static   final String patternString3=".*href\\s*=\\s*(\"|'|)http://.*";

	  public static    Pattern pattern1 =Pattern.compile(patternString1,Pattern.DOTALL);
	  public static    Pattern pattern2 =Pattern.compile(patternString2,Pattern.DOTALL);
	  public static    Pattern pattern3 =Pattern.compile(patternString3,Pattern.DOTALL);
	
	  
	  public static   final String patternStringTemp=".*img\\s*src\\s*=(\"|'|)http://.*";
	  public static    Pattern patternTemp =Pattern.compile(patternStringTemp,Pattern.DOTALL);
		
	/**
	 * 
	 * @param resPath
	 * @param destPath
	 * @return
	 */
	public static boolean urlToFile(String resPath,String destPath){
		try {
			URL url = new URL(resPath);
			try {
				BufferedInputStream bis = new BufferedInputStream(url.openStream());
				//实例化存储字节数组  
	            byte[] bytes = new byte[100];  
	            //设置写入路径以及图片名称  
	            OutputStream bos = new FileOutputStream(new File(destPath));  
	            int len;  
	            while ((len = bis.read(bytes)) > 0) {  
	                bos.write(bytes, 0, len);  
	            }  
	            bis.close();  
	            bos.flush();  
	            bos.close();  
	            return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;
		}
		
		 
	}
	
	/**
	 * 下载远程图片
	 * @param url
	 * @param destPath
	 */
	public static InputStream download(String url)
    {
        HttpClient client = new HttpClient();
        GetMethod get = new GetMethod(url);
        try {
        client.executeMethod(get);
        InputStream inputStream = get.getResponseBodyAsStream();
        return inputStream;
        } catch (IOException e) {
        	e.printStackTrace();
        	return null;
        }
    }
	
	
	public static String replaceImageUrl(List oldUrls ,String content,List newUrls)
	{
		 String result=null;
		 /**最好不要对参数修改*/
		 result=content;
		 for(int i=0;i<oldUrls.size();i++){
			 String url=(String)oldUrls.get(i);
			 if(url!=null)
		    	{
		    		result=result.replace(url,(String)newUrls.get(i));
		    		
		    	}
		 }
		return result;
	}
	
	public static void getImageUrls(List oldUrls,String content)
	{
		Matcher matcher=null;
		//假设最短的a标签链接为 <a href=http://www.a.cn></a>则计算他的长度为10
		if(content!=null  && content.length()>10)
		{
		     matcher=pattern3.matcher(content);
		     //确定句子里包含有链接
			if(matcher!=null && matcher.matches())
			{
				matcher=pattern1.matcher(content);
				String aString=null;
				String bString=null;
				
				while(matcher!=null && matcher.find())
				{
					if(matcher.groupCount()>3)
					{
					  bString=matcher.group(matcher.groupCount()-3);//这个group包含所有符合正则的字符串
					  aString=matcher.group(matcher.groupCount()-2);//这个group包含url的html标签
					  String url1=matcher.group(matcher.groupCount()-1);//最后一个group就是url
					  oldUrls.add(url1);//将找到的url保存起来
					  bString=bString.replaceAll(aString, "");//去掉已经找到的url的html标签
					}
					
				}
				if(bString!=null)
				{
					getImageUrls(oldUrls,bString);//继续循环提取下一个url
				}
				
			}
		}
		
	}
	public static void main(String[] args) {
		TeeUrlToFile toFile = new TeeUrlToFile(); 
		String resPath="<a href=http://www.google.cn/test.jpg >www.google.cn</a><a href=http://www.baidu.cn>www.google.cn</a><a href=http://www.sina.cn>www.google.cn</a>";
		
		resPath =  resPath + "<div><strong>Zepto</strong> is a minimalist <b>JavaScript library for modern browsers</b> with a largely <b>jQuery-compatible API</b>. <em>If you use jQuery, you already know how to use Zepto.</em>"
		+ "<div id='tour'><img src=http://zeptojs.com/intro.jpg style='transform: translateX(0px);' /></div>"
			+"<p>While 100% jQuery coverage is not a design goal, the <b>APIs provided match their jQuery counterparts</b>. The goal is to have a ~5-10k modular library that <b>downloads and executes fast</b>, with a <b>familiar and versatile API</b>, so you can <b>concentrate on getting stuff done</b>.</p>"
			+"</div>";
		
		resPath = resPath + "<div style=\"text-align: center;\"><img src=\"http://image1.chinanews.com.cn/cnsupload/big/2014/09-27/4-426/fe21f466e6f54100a3bb64bba1c2bc17.jpg\" /></div>"

				+ "<p align=\"left\" class=\"left_pt\">&nbsp;&nbsp;&nbsp;&nbsp;当地时间2";
		String destPath="D:/tupian.jpg";
		List list= new ArrayList();
		/*toFile.getImageUrls(list, resPath);
		for(int i=0;i<list.size();i++){
			System.out.println((String)list.get(i));
		}*/
		list = getImageSrc(resPath);
		for(int i=0;i<list.size();i++){
			System.out.println((String)list.get(i));
		}
	}
	
	
	/** 
	 * 从HTML源码中提取图片路径，最后以一个 String 类型的 List 返回，如果不包含任何图片，则返回一个 size=0　的List 
	 * 需要注意的是，此方法只会提取以下格式的图片：.jpg|.bmp|.eps|.gif|.mif|.miff|.png|.tif|.tiff|.svg|.wmf|.jpe|.jpeg|.dib|.ico|.tga|.cut|.pic 
	 * @param htmlCode HTML源码 
	 * @return <img>标签 src 属性指向的图片地址的List集合 
	 * @author Carl He 
	 */  
	public static List<String> getImageSrc(String htmlCode) {  
	   List<String> imageSrcList = new ArrayList<String>();  
	   Pattern p = Pattern.compile("<img[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);  
	   Pattern p2 = Pattern.compile("<img[^>]*src\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic))[^>]*>", Pattern.CASE_INSENSITIVE);  
	   
	   Matcher m = p.matcher(htmlCode);  
	    String quote = null;  
	    String src = null;  
	    while (m.find()) {  
	        quote = m.group(1);  //判断是否有双引号和单引号
	        src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);  
	        imageSrcList.add(src);  
	    }  
	    return imageSrcList;  
	}  

	/**
	 * 获取图片标签
	 * @param content
	 * @return
	 */
	public static List<TeeHTMLImgTag> getImageTagsFromHtml(String content){
		final List<TeeHTMLImgTag> list = new ArrayList();
		TeeRegexpAnalyser analyser = new TeeRegexpAnalyser();
		analyser.setText(content);
		analyser.replace(new String[]{new TeeHTMLImgTag().getRegExp()}, new TeeExpFetcher() {
			
			@Override
			public String parse(String pattern) {
				// TODO Auto-generated method stub
				TeeHTMLImgTag htmlTag = new TeeHTMLImgTag();
				htmlTag.analyse(pattern);
				list.add(htmlTag);
				return pattern;
			}
		});
		return list;
	}
  
}