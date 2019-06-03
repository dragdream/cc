package com.beidasoft.zfjd.law.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.law.service.TblLawService;

@Controller
@RequestMapping("importController")
public class ImportCourseExcel {
	
	@Autowired
	private TblLawService lawService;
	
	/**
	 * 判断Excel文件类型
	 * @param input
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("importUser")
	 public static List<List<Object>> readExcel(InputStream input,String fileName) throws IOException {
	        String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
	                .substring(fileName.lastIndexOf(".") + 1);
	        if ("xls".equals(extension)) {
	            return read2003Excel(input);
	        } else if ("xlsx".equals(extension)) {
	            return read2007Excel(input);
	        } else {
	            throw new IOException("不支持的文件类型");
	        }
	    }

	    /**
	     * 读取 office 2003 excel
	     * 
	     * @throws IOException
	     * @throws FileNotFoundException
	     */
	    private  static List<List<Object>> read2003Excel(InputStream input) throws IOException {
	        List<List<Object>> list = new LinkedList<List<Object>>();
	        HSSFWorkbook hwb = new HSSFWorkbook(input);
	        HSSFSheet sheet = hwb.getSheetAt(0);
	        Object value = null;
	        HSSFRow row = null;
	        HSSFCell cell = null;
	        int counter = 0;
	        for (int i = sheet.getFirstRowNum(); counter < sheet
	                .getPhysicalNumberOfRows(); i++) {
	            row = sheet.getRow(i);
	            if (row == null) {
	                continue;
	            } else {
	                counter++;
	            }
	            List<Object> linked = new LinkedList<Object>();
	            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
	                cell = row.getCell(j);
	                if (cell == null) {
	                    continue;
	                }
	                DecimalFormat df = new DecimalFormat("0");// 格式化 number String
	                                                            // 字符
	                SimpleDateFormat sdf = new SimpleDateFormat(
	                        "yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
	                DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
	                switch (cell.getCellType()) {
	                case XSSFCell.CELL_TYPE_STRING:
	                    value = cell.getStringCellValue();
	                    break;
	                case XSSFCell.CELL_TYPE_NUMERIC:
	                    if ("@".equals(cell.getCellStyle().getDataFormatString())) {
	                        value = df.format(cell.getNumericCellValue());
	                    } else if ("General".equals(cell.getCellStyle()
	                            .getDataFormatString())) {
	                        value = df.format(cell.getNumericCellValue());
	                    } else {
	                        value = sdf.format(HSSFDateUtil.getJavaDate(cell
	                                .getNumericCellValue()));
	                    }
	                    break;
	                case XSSFCell.CELL_TYPE_BOOLEAN:
	                    value = cell.getBooleanCellValue();
	                    break;
	                case XSSFCell.CELL_TYPE_BLANK:
	                    value = "";
	                    break;
	                default:
	                    value = cell.toString();
	                }
	                /*if (value == null || "".equals(value)) {
	                    continue;
	                }*/
	                linked.add(value);
	            }
	            list.add(linked);
	        }
//	        for (List<Object> str : list) {   
//	            System.out.println(str);   
//	        
//	        } 
	        return list;
	    }

	    /**
	     * 读取Office 2007 excel
	     * */
	    private   static  List<List<Object>> read2007Excel(InputStream input) throws IOException {
	        List<List<Object>> list = new LinkedList<List<Object>>();
	        // 构造 XSSFWorkbook 对象，strPath 传入文件路径
	        XSSFWorkbook xwb = new XSSFWorkbook(input);
	        // 读取第一章表格内容
	        XSSFSheet sheet = xwb.getSheetAt(0);
	        Object value = null;
	        XSSFRow row = null;
	        XSSFCell cell = null;
	        int counter = 0;
	        for (int i = sheet.getFirstRowNum(); counter < sheet
	                .getPhysicalNumberOfRows(); i++) {
	            row = sheet.getRow(i);
	            if (row == null) {
	                continue;
	            } else {
	                counter++;
	            }
	            List<Object> linked = new LinkedList<Object>();
	            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
	                cell = row.getCell(j);
	                if (cell == null) {
	                    continue;
	                }
	                DecimalFormat df = new DecimalFormat("0");// 格式化 number String                                          // 字符
	                SimpleDateFormat sdf = new SimpleDateFormat(
	                        "yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
	                switch (cell.getCellType()) {
	                case XSSFCell.CELL_TYPE_STRING:
	                    value = cell.getStringCellValue();
	                    break;
	                case XSSFCell.CELL_TYPE_NUMERIC:
	                    if ("@".equals(cell.getCellStyle().getDataFormatString())) {
	                        value = df.format(cell.getNumericCellValue());
	                    } else if ("General".equals(cell.getCellStyle()
	                            .getDataFormatString())) {
	                        value = df.format(cell.getNumericCellValue());
	                    } else {
	                        value = sdf.format(HSSFDateUtil.getJavaDate(cell
	                                .getNumericCellValue()));
	                    }
	                    break;
	                case XSSFCell.CELL_TYPE_BOOLEAN:
	                    value = cell.getBooleanCellValue();
	                    break;
	                case XSSFCell.CELL_TYPE_BLANK:
	                    value = "";
	                    break;
	                default:
	                    value = cell.toString();
	                }
	                /*if (value == null || "".equals(value)) {
	                    continue;
	                }*/
	                linked.add(value);
	            }
	            list.add(linked);
	        }
	        return list;
	    }
	    //插入数据 法律名称10，章17，条18，款19，项110，目111，内容16
	   // public static final String INSERT_STUDENT_SQL = "insert into tbl_base_law_detail(
	    //law_name, detail_chapter, detail_strip, detail_fund,detail_item,detail_catalog,content) values(?, ?, ?, ?,?,?,?)";
	    
	    
	    
//	    public static void main(String[] args) {
//	        try {
//	            List<List<Object>> excleDataList=readExcel(new File("D:\\test.xls"));
//	            for(int i=2;i<excleDataList.size();i++){
//	            	for (int j = 0; j <= excleDataList.size(); j++) {
//	            		if (j==0) {
//							//System.out.print("{law_name:\""+excleDataList.get(i).get(0).toString()+"\",");
//							String json = "{\"law_name\":\""+excleDataList.get(i).get(0).toString()+"\",";
//							System.out.print(json);
//	            		}
//	            		if (j==6) {
//							System.out.print("\"content\":\""+excleDataList.get(i).get(6).toString()+"\",");
//						}
//	            		if (j==7) {
//							System.out.print("\"detail_chapter\":\""+excleDataList.get(i).get(7).toString()+"\",");
//						}
//	            		if (j==8) {
//							System.out.print("\"detail_strip\":\""+excleDataList.get(i).get(8).toString()+"\",");
//						}
//	            		if (j==9) {
//							System.out.print("\"detail_fund\":\""+excleDataList.get(i).get(9).toString()+"\",");
//						}
//	            		if (j==10) {
//							System.out.print("\"detail_item\":\""+excleDataList.get(i).get(10).toString()+"\",");
//						}
//	            		if (j==11) {
//							System.out.print("\"detail_catalog\":\""+excleDataList.get(i).get(11).toString()+"\"}");
//						}
//	            		
//					}
//	            	System.out.println();
//	            }
//	            for (List<Object> str : excleDataList) {   
//		            System.out.println(str);   
//		        
//		        }  
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	    }
	}
