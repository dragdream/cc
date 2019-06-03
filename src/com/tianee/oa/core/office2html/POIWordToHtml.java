package com.tianee.oa.core.office2html;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.IImageExtractor;
import org.apache.poi.xwpf.converter.core.IURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.webframe.util.global.TeeBeanFactory;


/**
 * @author：罗大锤
 * @date: 2017年9月1日 下午4:33:02
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class POIWordToHtml {
	private static final String ENCODING = "UTF-8";// UTF-8
	

	public static String wordToHtml(String sourcePath){
		File file=new File(sourcePath);
		String fileName=file.getName();
		String ext = FileUtils.GetFileExt(sourcePath);
		String content = null;
		final TeeBaseUpload baseUpload = (TeeBaseUpload) TeeBeanFactory.getBean("teeBaseUpload");
		try {
			if (ext.equals("doc")) {
				HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(sourcePath));
				WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
						DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
				wordToHtmlConverter.setPicturesManager(new PicturesManager() {
					@Override
					public String savePicture(byte[] content, PictureType pictureType, String suggestedName,
							float widthInches, float heightInches) {
						
						InputStream inputStream = new ByteArrayInputStream(content);
						TeeAttachment att=null;
						try {
							att=baseUpload.singleAttachUpload(inputStream, content.length, suggestedName, null, "quickView", null);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							try {//关闭流
								inputStream.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						return "/attachmentController/downFile.action?id="+att.getSid();//返回img标签的src的路径
					}
				});
				wordToHtmlConverter.processDocument(wordDocument);
				Document htmlDocument = wordToHtmlConverter.getDocument();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				DOMSource domSource = new DOMSource(htmlDocument);
				StreamResult streamResult = new StreamResult(out);

				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer serializer = tf.newTransformer();
				serializer.setOutputProperty(OutputKeys.ENCODING, "GBK");
				serializer.setOutputProperty(OutputKeys.INDENT, "yes");
				serializer.setOutputProperty(OutputKeys.METHOD, "html");
				serializer.transform(domSource, streamResult);
				
				out.close();
				content = out.toString();
				
				byte b[] = content.getBytes("UTF-8");
				//转换成流的形式
				InputStream    inputStream =   new   ByteArrayInputStream(b);   
				
				TeeAttachment htmlAtt=baseUpload.singleAttachUpload(inputStream, b.length,fileName.substring(0,fileName.indexOf("."))+".html", null, "quickView", null);
				//关闭流
				inputStream.close();
				if(htmlAtt!=null){
					return htmlAtt.getSid()+"";
				}else{
					return "0";
				}
				
			} else if (ext.equals("docx")) {
				// 1) 加载word文档生成 XWPFDocument对象
				InputStream in = new FileInputStream(new File(sourcePath));
				XWPFDocument document = new XWPFDocument(in);
				// 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
				XHTMLOptions options = XHTMLOptions.create();
				final Map<String,Integer> map=new HashMap<String,Integer>();
				options.setExtractor(new IImageExtractor() {
					@Override
					public void extract(String arg0, byte[] arg1) throws IOException {
						// TODO Auto-generated method stub
						/*System.out.println("文件名："+arg0);
						System.out.println("文件字节内容长度："+arg1.length);*/
						
						InputStream inputStream = new ByteArrayInputStream(arg1);
						TeeAttachment att=null;
						try {
							att=baseUpload.singleAttachUpload(inputStream, arg1.length, arg0, null, "quickView", null);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							inputStream.close();
						}
						if(att!=null){
							map.put(arg0, att.getSid());
						}
					}
				});
				options.URIResolver(new IURIResolver() {
					
					@Override
					public String resolve(String arg0) {
						// TODO Auto-generated method stub
						//循环map集合  取出对应文件名称  生成的    附件的id
						int attId=0;
						for (String str : map.keySet()) {
							if(str.equals(arg0)){
								attId=map.get(str);
							}
						}
						return "/attachmentController/downFile.action?id="+attId;//返回img标签的src的路径
					}
				});
				// 3) 将 XWPFDocument转换成XHTML
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				XHTMLConverter.getInstance().convert(document, baos, options);
				baos.close();
			    content = baos.toString();
//				
			    
			    byte b[] = content.getBytes("UTF-8");
				//转换成流的形式
				InputStream    inputStream =   new   ByteArrayInputStream(b);   
				
				TeeAttachment htmlAtt=baseUpload.singleAttachUpload(inputStream, b.length,fileName.substring(0,fileName.indexOf("."))+".html", null, "quickView", null);
				
				//关闭流
				inputStream.close();
				
				if(htmlAtt!=null){
					return htmlAtt.getSid()+"";
				}else{
					return "0";
				}
			    
			    
				//System.out.println("*****docx转html 转换结束...*****");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
		return content;
	}
}
