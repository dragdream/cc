package com.beidasoft.xzfy.utils;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ImportExcelUtils 
{

	/**
	 * 导入excel解析,读出的数据导入List,1:从第2行开始；0:从第A列开始；0:第0个sheet
	 * @param in 
	 * @param startrow 开始行号
	 * @param startcol 开始列号
	 * @param sheetnum sheet页
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static List<Map<String,Object>> readExcel(FileInputStream in, 
			int startrow, int startcol, int sheetnum) {
		
		//返回列表
		List<Map<String,Object>> varList = new ArrayList<Map<String,Object>>();
		try 
		{
			//读取地址
			//File target = new File(filepath, filename);
			//FileInputStream fi = new FileInputStream(target);
			HSSFWorkbook wb = new HSSFWorkbook(in);
			//sheet 从0开始
			HSSFSheet sheet = wb.getSheetAt(sheetnum); 	
			//取得最后一行的行号
			int rowNum = sheet.getLastRowNum() + 1; 					
			//行循环开始
			for (int i = startrow; i < rowNum; i++) {					
				
				Map<String,Object> varpd = new HashMap<String,Object>();
				//行
				HSSFRow row = sheet.getRow(i); 	
				//每行的最后一个单元格位置
				int cellNum = row.getLastCellNum(); 					
				//列循环开始
				for (int j = startcol; j < cellNum; j++) 
				{				
					HSSFCell cell = row.getCell(Short.parseShort(j + ""));
					String cellValue = null;
					if (null != cell) {
						// 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
						switch (cell.getCellType()) { 					
						case 0:
							//数值型
							cellValue = new BigDecimal(cell.getNumericCellValue()).toString();
							break;
						case 1:
							//字符串
							cellValue = cell.getStringCellValue();
							break;
						case 2:
							//
							cellValue = cell.getNumericCellValue() + "";
							// cellValue = String.valueOf(cell.getDateCellValue());
							break;
						case 3:
							//空值
							cellValue = "";
							break;
						case 4:
							//布尔型
							cellValue = String.valueOf(cell.getBooleanCellValue());
							break;
						case 5:
							//错误异常类型
							cellValue = String.valueOf(cell.getErrorCellValue());
							break;
						}
					} else {
						cellValue = "";
					}
					//存放在map中， 以var开始存放
					varpd.put("var"+j, cellValue);
				}
				varList.add(varpd);
			}
			//关闭
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return varList;
	}
}
