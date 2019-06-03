package com.tianee.webframe.util.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.tianee.webframe.data.TeeDataRecord;


public class TeeAOPExcleUtil {
	/**
	   * 写excel
	   * @param ops   
	   * @param dataArray  数据列表
	   * @return
	   * @throws Exception
	   */
	  public static OutputStream writeExc(OutputStream ops,ArrayList<TeeDataRecord> dataArray ) throws Exception {
	    HSSFWorkbook wwb = null;
	    HSSFSheet ws = null;
	    ArrayList<Integer> counSize = new ArrayList<Integer>();
	    try {
	      wwb = new HSSFWorkbook();
	    // 创建Excel工作表
	      //表头，创建sheet
	      ws = wwb.createSheet();
	     // wwb.setSheetName(0,"Tee导出",HSSFWorkbook.ENCODING_UTF_16); 
	      HSSFRow titlerow = ws.createRow(0);//创建第一行---title行
	      for (int i = 0; i < dataArray.size() ; i++) {
	        TeeDataRecord dbRec = dataArray.get(i);
	        HSSFRow row = ws.createRow(i+1);
	        for (int j = 0; j < dbRec.getFieldCnt(); j++) {//循环一行中存在多个记录数（列）
	          //System.out.println(dbRec.getNameByIndex(j));
	          
	          if(i == 0){//第一行，title行
	            HSSFCell titleCell = titlerow.createCell(j);//第一行（title行）创建列
	            titleCell.setCellValue(dbRec.getNameByIndex(j));//列赋值
	            titleCell.setCellStyle(getTiltleStyle(wwb));//列设置样式
	            counSize.add(dbRec.getNameByIndex(j).length()+20);//设置每列的字数
	          }
	          String value = "";
	          if(dbRec.getValueByIndex(j) != null){//根据索引获取值
	            value = dbRec.getValueByIndex(j).toString();
	          }
	          HSSFCell dataCell = row.createCell(j);//从第二行创建列
	          dataCell.setCellValue(value);
	        }
	      }
	      for (int i = 0; i < counSize.size(); i++) {
	        ws.setColumnWidth(i, (counSize.get(i) + 10)*150);//设置每列的宽度
	      }
	      wwb.write(ops);
	      ops.flush();
	    } catch (Exception e1) {
	      throw e1;
	    } finally{
	     
	    }
	    return ops;
	  }
	  /**
	   * 读execl文件
	   * @param ins
	   * @param hasTitle 是否存在表头
	   * @return
	   * @throws IOException
	   */
	  public static ArrayList<TeeDataRecord> readExc(InputStream ins,boolean hasTitle) throws Exception {
	    ArrayList<TeeDataRecord> dataArray = new ArrayList<TeeDataRecord>();
	    HSSFWorkbook wb = new HSSFWorkbook(ins);//读取excle流，并获取
	    HSSFSheet s = wb.getSheetAt(0);//第1个sheet
	    HSSFCell c = null;
	    HSSFRow titleRow = s.getRow(0);//获取第一行
	    int rowTotle = s.getLastRowNum();//总行数
	    int col = s.getRow(0).getLastCellNum();//总列数
	    int i = 0;
	    if(hasTitle){//存在表头，数据从第二行取
	      i = 1;
	    }
	    for(;i <= rowTotle;i++){//循环行
	      HSSFRow row = s.getRow(i);
	      if(row == null){
	    	  continue;
	      }
	      TeeDataRecord dbr = new TeeDataRecord();
	      for(int j = 0;j < col;j++){//循环列
	        String title = "";
	        if(hasTitle){//如果存在则去表头值作为键，否则为“cell_” + j
	          HSSFCell titleC = titleRow.getCell(j);//获取列对象
	          title = titleC.getStringCellValue();//获取列值字符串
	        }else{
	          title = "cell_" + j;
	        }
	        c = row.getCell(j);
	        if (c == null) {
	          continue;
	        }
	        Object value = null;
	        if(c.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){//数字类型
	          value = c.getNumericCellValue();
	        }else if( c.getCellType() == HSSFCell.CELL_TYPE_STRING){//String类型
	          value = c.getStringCellValue();
	        }else if( c.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN){//boolean值类型
	          value = c.getBooleanCellValue();
	        }else{//还有其他类型，待处理
	        	
	        }
	        dbr.addField(title, value);
	       }
	      dataArray.add(dbr);
	    }   
	    return dataArray;
	  }
	 
	  
	  /**
	   * 获取列样式
	   * @param hssfwb  --- excle工作对象
	   * @return
	   */
	  public static HSSFCellStyle getTiltleStyle(HSSFWorkbook hssfwb){
	    HSSFCellStyle cellStyle = hssfwb.createCellStyle();//创建样式
	    HSSFFont font = getTitleFont(hssfwb);
	    cellStyle.setFont(font);
	    return cellStyle;
	  }
	  /**
	   * 设置字体格式
	   * @param hssfwb
	   * @return
	   */
	  public static HSSFFont getTitleFont(HSSFWorkbook hssfwb){
		    HSSFFont fontStyle = hssfwb.createFont();//创建格式
		    fontStyle.setFontName("宋体");//字体
		    fontStyle.setFontHeightInPoints((short)14);//字体大小
		    fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//是否加粗
		    return fontStyle;
	 }
	  
	  /**
	   * 写excel
	   * @param ops
	   * @param dataArray
	   * @return
	   * @throws Exception
	   */
	  public static OutputStream writeWord(OutputStream ops,byte[] bytes ) throws Exception {
	    try{
	      POIFSFileSystem fs = new POIFSFileSystem();
	      ByteArrayInputStream bs = new ByteArrayInputStream(bytes);
	     // DirectoryEntry directory = fs.getRoot();
	     // DocumentEntry de = directory.createDocument("WordDocument", bs);
	      fs.writeFilesystem(ops);
	      bs.close();
	      ops.flush();
	      ops.close();
	    } catch (Exception e1) {
	      throw e1;
	    } finally{
	     
	    }
	    return ops;
	  }
}
