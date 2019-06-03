package com.tianee.lucene;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xslf.XSLFSlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraph;
import org.openxmlformats.schemas.presentationml.x2006.main.CTGroupShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTSlide;

public class TeeFileExtractUtil {

	
	/**
	 * 提取TXT文档
	 * 
	 * @param 路径
	 * @param 编码
	 * @return
	 * @throws IOException
	 */
	public static String FileReaderAll(String fileName, String charSet)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileName), charSet));
		String line = new String();
		String temp = new String();
		while ((line = reader.readLine()) != null) {
			temp += line;
		}
		reader.close();
		return temp;
	}

	/**
	 * 提取word文档doc格式
	 * 
	 * @param fileName
	 *            路径
	 * @return
	 * @throws IOException
	 */
	public static String WordFileReader(String fileName) throws IOException {
		String bodyText = null;
		FileInputStream is = new FileInputStream(fileName);
		bodyText = new WordExtractor(is).getText();
		return bodyText;
	}

	/**
	 * 提取word文档docx格式
	 * 
	 * @param fileName
	 *            路径
	 * @return
	 * @throws IOException
	 */
	public static String WordDocxFileReader(String fileName) throws IOException {
		String bodyText = null;
		FileInputStream is = new FileInputStream(fileName);
		XWPFDocument doc = new XWPFDocument(is);
	    XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
		bodyText = extractor.getText();
		//System.out.println(bodyText);

		return bodyText;
	}

	/**
	 * Excel表格提取数据 xls
	 * 
	 * @param fileName
	 *            路径
	 * @return
	 * @throws IOException
	 */
	public static String ExcelFileReader(String fileName) throws IOException {
		InputStream path = new FileInputStream(fileName);
		String content = null;
		HSSFWorkbook wb = new HSSFWorkbook(path);
		ExcelExtractor extractor = new ExcelExtractor(wb);
		extractor.setFormulasNotResults(true);
		extractor.setIncludeSheetNames(false);
		content = extractor.getText();
		return content;
	}

	/**
	 * Excel表格提取数据 xlsx
	 * 
	 * @param fileName
	 *            路径
	 * @return
	 * @throws IOException
	 */
	public static String xlsxFileReader(String fileName) throws IOException {
		InputStream path = new FileInputStream(fileName);
		String content = null;
		XSSFWorkbook wb = new XSSFWorkbook(path);
		XSSFExcelExtractor extractor = new XSSFExcelExtractor(wb);
		//XSSFExcelExtractor extractor = null;
		extractor.setFormulasNotResults(true);
		extractor.setIncludeSheetNames(false);
		content = extractor.getText();
		return content;
	}

	/**
	 * 提取PDF文件
	 * 
	 * @param fileName路径
	 * @return
	 * @throws Exception 
	 */
	public static String PdfboxFileReader(String fileName) throws Exception {
		StringBuffer content = new StringBuffer("");// 文档内容
		FileInputStream fis = new FileInputStream(fileName);
		PDFParser p = new PDFParser(fis);
		p.parse();
		PDFTextStripper ts = new PDFTextStripper();
		content.append(ts.getText(p.getPDDocument()));
		fis.close();

		return content.toString().trim();
	}

	/**
	 * ppt创建索引
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String PptFileReader(String fileName) throws Exception {
		// 创建输入流读取ppt文件
		FileInputStream is = new FileInputStream(fileName);
		SlideShow ss = new SlideShow(new HSLFSlideShow(is));// is
		// 为文件的InputStream，建立SlideShow
		Slide[] slides = ss.getSlides();// 获得每一张幻灯片

		String content = new String();
		for (int i = 0; i < slides.length; i++) {
			TextRun[] t = slides[i].getTextRuns();// 为了取得幻灯片的文字内容，建立TextRun
			for (int j = 0; j < t.length; j++) {
				content += t[j].getText();
			}
		}
		return content;
	}
	
	
	/**
	 * pptx创建索引
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String PptxFileReader(String fileName) {
//		        XSLFSlideShow slideShow;
//		        String reusltString=null;
//		        try {
//		            slideShow = new XSLFSlideShow(fileName);
//		            XMLSlideShow xmlSlideShow = new XMLSlideShow(slideShow);
//		            XSLFSlide[] slides = xmlSlideShow.getSlides();
//		            StringBuilder sb = new StringBuilder();
//		            for (XSLFSlide slide : slides) {
//		                CTSlide rawSlide = slide._getCTSlide();
//		                CTGroupShape gs = rawSlide.getCSld().getSpTree();
//		                CTShape[] shapes = gs.getSpArray();
//		                for (CTShape shape : shapes) {
//		                    CTTextBody tb = shape.getTxBody();
//		                    if (null == tb)
//		                        continue;
//		                    CTTextParagraph[] paras = tb.getPArray();
//		                    for (CTTextParagraph textParagraph : paras) {
//		                        CTRegularTextRun[] textRuns = textParagraph.getRArray();
//		                        for (CTRegularTextRun textRun : textRuns) {
//		                            sb.append(textRun.getT());
//		                        }
//		                    }
//		                }
//		            }
//		        reusltString=sb.toString();
//		        } catch (Exception e) {
//		            // TODO Auto-generated catch block
//		            e.printStackTrace();
//		        }
//                System.out.println(reusltString);
//		        return reusltString;
		return "";
		    }

	
	/**
	 * 提取html或者htm文件
	 * @param canonicalPath
	 * @return
	 */
	public static String htmlFileReader(String fileName) {
		    BufferedReader br=null;   
	        StringBuffer sb = new  StringBuffer();   
	        try {   
	            br=new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));            
	            String temp=null;          
	            while((temp=br.readLine())!=null){   
	                sb.append(temp);   
	            }              
	        } catch (FileNotFoundException e) {   
	            e.printStackTrace();   
	        } catch (IOException e) {   
	            e.printStackTrace();   
	        }  
	        return sb.toString();   
	}
}
