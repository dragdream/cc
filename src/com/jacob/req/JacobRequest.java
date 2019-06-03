package com.jacob.req;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * Jacob请求
 * @author liteng
 *
 */
public class JacobRequest {
	private String address;
	
	public JacobRequest(String addr){
		this.address = addr;
	}
	
	/**
	 * 上传文件
	 * @param filePath
	 * @return
	 */
	public JacobResponse uploadFile(String filePath){
		String result = HttpClientUtil.uploadFile(null, null, filePath, address+"/DocProcess?op=upload", 1000*1000);
		return JSONObject.parseObject(result, JacobResponse.class);
	}
	
	/**
	 * 替换word书签
	 * @param fileId
	 * @param replaceData
	 * @return
	 */
	public JacobResponse replaceBookmarks(String fileId,Map<String,String> replaceData){
		Map params = new HashMap();
		JSONObject jsonObject = (JSONObject)JSONObject.toJSON(replaceData);
		params.put("payload", jsonObject.toJSONString());
		String result = HttpClientUtil.request(null, params, address+"/DocProcess?op=bookmarks&fileId="+fileId);
		return JSONObject.parseObject(result, JacobResponse.class);
	}
	
	/**
	 * 文件（word、PPT、excel）转pdf
	 * @param fileId
	 * @return
	 */
	public JacobResponse file2pdf(String fileId){
		String result = HttpClientUtil.request(null, null, address+"/DocProcess?op=file2pdf&fileId="+fileId);
		return JSONObject.parseObject(result, JacobResponse.class);
	}
	
	/**
	 * 使用freemarker标记语言替换文件内容
	 * @param fileId
	 * @param replaceData
	 * @return
	 */
	public JacobResponse freemarker(String fileId,Map<String,Object> replaceData){
		Map params = new HashMap();
		JSONObject jsonObject = (JSONObject)JSONObject.toJSON(replaceData);
		params.put("payload", jsonObject.toJSONString());
		String result = HttpClientUtil.request(null, params, address+"/DocProcess?op=freemarker&fileId="+fileId);
		return JSONObject.parseObject(result, JacobResponse.class);
	}
	
	/**
	 * 将html文件转换为pdf文件
	 * @param fileId
	 * @return
	 */
	public JacobResponse html2pdf(String fileId){
		String result = HttpClientUtil.request(null, null, address+"/DocProcess?op=html2pdf&fileId="+fileId);
		return JSONObject.parseObject(result, JacobResponse.class);
	}
	
	/**
	 * 根据文件ID下载文件
	 * @param fileId
	 * @return
	 */
	public void download(String fileId,String filePath){
		HttpClientUtil.download(null, null, address+"/DocProcess?op=download&fileId="+fileId, filePath);
	}
	
	/**
	 * 将网页格式转化为图片并下载
	 * @param webUrl
	 * @param ext
	 * @param downloadFilePath
	 * @return
	 */
	public void html2ImageAndDownload(String webUrl,String ext,String screenSize,String downloadFilePath){
		Map<String,String> params = new HashMap();
		params.put("op", "html2image");
		params.put("ext", ext);
		params.put("weburl", webUrl);
		params.put("screensize", screenSize);
		HttpClientUtil.download(null, params, address+"/DocProcess", downloadFilePath);
	}
	
	/**
	 * 执行jacob脚本
	 * @param script
	 * @param fileId
	 * @return
	 */
	public JacobResponse scriptRun(String script,String fileId){
		Map<String,String> params = new HashMap();
		params.put("script", script);
		String result = HttpClientUtil.request(null, params, address+"/DocProcess?op=scriptrun&fileId="+fileId);
		return JSONObject.parseObject(result, JacobResponse.class);
	}
	
	public static void main(String[] args) {
		//创建jacob请求对象，并设置服务的ip地址
		JacobRequest jacobRequest = new JacobRequest("http://47.94.170.175:19239");
		
		//将本地一个word文件上传，获取到服务器远程的文件id
		JacobResponse jacobResponse = jacobRequest.uploadFile("C:\\Users\\liteng\\Desktop\\客户资料\\文化执法\\文书样本整理\\调查询问笔录1.docx");
		String wordId = jacobResponse.fileId;//获取到上传后的word文件的ID
		
		//替换word书签的值
		Map<String,String> replaceData = new HashMap();
		replaceData.put("询问地点", "北京市丰台区西三环南路1号（市文化执法总队）1210室           ");
		replaceData.put("询问时间年1", "2017");
		replaceData.put("询问时间月1", "08");
		replaceData.put("询问时间日1", "19");
		replaceData.put("询问时间时1", "9");
		replaceData.put("询问时间分1", "50");
		replaceData.put("询问时间时2", "10");
		replaceData.put("询问时间分2", "30");
		replaceData.put("被询问人姓名", "王晓明");
		replaceData.put("被询问人性别", "男");
		replaceData.put("被询问人出生年月", "1979年12月");
		replaceData.put("被询问人联系电话", "15120059230");
		replaceData.put("被询问人证件名称", "居民身份证");
		replaceData.put("被询问人证件号码", "110108999993023322");
		replaceData.put("被询问人工作单位", "北京天涯海格娱乐城有限公司");
		replaceData.put("被询问人住址", "黑龙江省讷河市孔国乡德宝存3组（现住址：北京市海淀区学院南路33号北京艺海世纪商务酒店东区101号）");
		
		//内容记录
		String content = "问：你好的我们是北京市文化市场行政执法总队的行政执法人员的这是我们的执法证件，你看清楚了吗？\n";
		content += "答：我看清楚看了。\n";
		content += "问：根据《中华人民共和国行政处罚法》的有关规定，现就北京天涯海格娱乐城有限公司涉嫌未设置能够覆盖全部营业区域的中英文应急广播的相关事宜对你进行调查询问。在场进行调查询问的执法人员，你认为有需要回避的，请申请回避；在接受调查询问的过程中，你有陈述、申辩的权利，同时你也有如实回答询问并提供有关资料、证据的义务，你清楚吗？\n";
		content += "答：我清楚了。不申请回避。\n";
		content += "请你介绍一下你公司的基本情况？\n";
		content += "答：我叫李金平，是北京天涯海格娱乐城有限公司的总经理，我公司法定代表人刘希朱，刘希朱出差在外地，受他委托我来总队接收调查处理此事。我公司于2008年03月27日成立，并已取得当地文委颁发的《娱乐经营许可证》。我们场所现有39个包间，从业人员31人，包间最低消费80元。\n";
		content += "问：按执法人员的通知要求提供公司的企业法人营业执照、娱乐场所经营许可证及公司法定代表人的有效身份证件等相关资料？\n";
		content += "答：好的，我已将上述资料的复印件都带来了，请查验。\n";
		content += "问：你单位两年之内有没有受到过文化执法部门的处罚？\n";
		content += "答：没有。\n";
		content += "问：2017年08月15日18时45分，执法人员检查你单位时，你是否在现场？\n";
		content += "答：我在现场，你们的检查情况我都清楚。\n";
		content += "问：执法人员现场检查时发现你单位存在什么问题？\n";
		content += "答：执法人员检查我单位经营场所时，发现应急广播不能覆盖到卫生间、操作间。\n";
		content += "问：为什么会出现这样的情况？\n";
		content += "答：主要是我们认识不到造成的，是我们的责任，我们立即改正。\n";
		content += "问：你单位提供的《娱乐经营许可证》是2016年03月23日签发的，已经过了一年多了，你们有新的吗？\n";
		content += "答：今年年初我们主动同海淀区文委联系过此事，他们说两年后再年审。\n";
		content += "问：你单位没有设置能够覆盖全部营业区的应急广播要收到行政处罚你认可吗？\n";
		content += "答：我们认可。\n";
		content += "问：你还有需要补充的吗？\n";
		content += "答：没有。\n";
		content += "问：请你看一下以上笔录，如无异议，请签字。\n";
		content += "答：好的。";
		
		String newContent = "";
		String sp[] = content.split("\n");
		int lineCount = sp.length;//获取原始行数（未折行的）
		for(int i=0;i<sp.length-1;i++){
			String line = sp[i];
			int c = line.length();//获取字符数量（全部当做中文）
			char cr[] = line.toCharArray();
			int enCount = 0;//英文字符数量
			for(char crtmp:cr){
				if((crtmp>='A' && crtmp<='Z') || (crtmp>='a' && crtmp<='z') || (crtmp>='0' && crtmp<='9')){
					enCount++;
				}
			}
			
			c = c-(enCount/2+enCount%2);//获取除去英文字符的字符数量
			double yu = c/36.00*1.00;
			int yuInt = c/36;
			if(yuInt!=0 && (yu-yuInt)>0){
				lineCount = lineCount+yuInt;
			}
			newContent+=line+"                                                                                              \n";
			
		}
		lineCount--;
		if(lineCount<=9){//如果行数小于等于9，说明只有第一页，然后补全剩下的行数
			for(int i=0;i<(9-lineCount);i++){
				newContent+="                                                                                               ";
				if(i!=(9-lineCount)-1){
					newContent+="\n";
				}
			}
		}else{//如果行数大于9，说明已经超出一页纸
			int yuLine = lineCount-9;//获取余下的行数
			int pageMaxLine = 21;//新的一页纸最大行数是21
			int lastPageLineCount = yuLine%pageMaxLine;//获取最后一页的行数
			for(int i=0;i<(pageMaxLine-lastPageLineCount);i++){
				newContent+="                                                                                               ";
				if(i!=(pageMaxLine-lastPageLineCount)-1){
					newContent+="\n";
				}
			}
		}
		
//		replaceData.put("内容记录", newContent);
		
		
		jacobResponse = jacobRequest.replaceBookmarks(wordId, replaceData);
		String replacedWordId = jacobResponse.fileId;//获取替换书签后的word文件id
		
		
		//将替换书签后的word文件下载到本地
		jacobRequest.download(replacedWordId,"d:\\1.doc");
		
		if(true){
			return;
		}
		
		//将word转换为pdf文件
		jacobResponse = jacobRequest.file2pdf(replacedWordId);
		String pdfFileId = jacobResponse.fileId;
		
		//将pdf文件下载到本地
		jacobRequest.download(pdfFileId,"d:\\1.pdf");
		
		
		
		//上传一个xml格式文件
		jacobResponse = jacobRequest.uploadFile("C:\\Users\\liteng\\Desktop\\test.xml");
		String xmlId = jacobResponse.fileId;//获取到上传后的xml文件的ID
		
		//使用freemarker进行标签替换
		Map<String,Object> replacedData = new HashMap();
		List<Map> list = new ArrayList();
		
		Map m1 = new HashMap();
		m1.put("content", "内容1");
		m1.put("name", "名字1");
		
		Map m2 = new HashMap();
		m2.put("content", "内容2");
		m2.put("name", "名字2");
		
		list.add(m1);
		list.add(m2);
		
		replacedData.put("itemList", list);
		replacedData.put("title", "我是大标题");
		
		
		jacobResponse = jacobRequest.freemarker(xmlId, replacedData);
		String replacedXmlId = jacobResponse.fileId;//获取通过freemarker替换后的xml文件id
		
		//下载替换后的xml文件
		jacobRequest.download(replacedXmlId,"d:\\1.xml");
		
		//将某个网页转换成图片格式并下载
		String ext = "jpg";
//		jacobRequest.html2ImageAndDownload("http://127.0.0.1/imAttachment/printRunHtml.action?runId=307", ext,"1024*768", "d:\\html2image."+ext);
		jacobRequest.html2ImageAndDownload("http://www.126.com", ext,"1024*768", "d:\\html2image."+ext);
		
//		String script = "Dispatch tables = Dispatch.get(document, \"Tables\").toDispatch();";
//		script+="Dispatch table = Dispatch.call(tables,\"Item\",new Object[]{new Variant(1)}).toDispatch();";
//		script+="Dispatch cell = Dispatch.call(table, \"Cell\", new Object[]{new Variant(1),new Variant(1)}).toDispatch();";
//		script+="Dispatch.call(cell, \"Select\");";
//		script+="Dispatch selection = Dispatch.get(app, \"Selection\").toDispatch();";
//		script+="Dispatch.put(selection, \"Text\", \"我们是共产主义接班人\");";
//		
//		JacobResponse jacobResponse = jacobRequest.scriptRun(script, "20180604d7bb18d7a4754fb4b5344eb622407433");
//		jacobRequest.download(jacobResponse.fileId, "C:\\Users\\liteng\\Desktop\\new.docx");
	}
}
