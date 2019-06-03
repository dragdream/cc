package com.tianee.oa.core.base.vote.service;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import com.tianee.webframe.data.TeeDataRecord;

public class TeeExportExcel {

	/**
	 * 写excel
	 * 
	 * @param ops
	 * @param dataArray
	 *            数据列表
	 * @return
	 * @throws Exception
	 */
	public static OutputStream writeExc(OutputStream ops, ArrayList<TeeDataRecord> dataArray) throws Exception {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();// 创建Excel工作表对象
			HSSFSheet sheet = workbook.createSheet("投票结果");// 创建一个Sheet1工作表
			ArrayList<Integer> countSizeList = new ArrayList<Integer>();
			Map<Object, Object> map = new HashMap<Object, Object>();
			HSSFRow firstRow = sheet.createRow(0); // 第一行：创建一大标题行
			HSSFRow secondRow = sheet.createRow(1); // 第二行： 创建一小标题行
			HSSFRow threeRow = sheet.createRow(2); // 第三行： 创建一小标题行
			HSSFRow fourthRow = sheet.createRow(3); // 第四行： 创建一小标题行

			firstRow.setHeight((short) 0x250);
			secondRow.setHeight((short) 0x190);
			threeRow.setHeight((short) 0x190);
			fourthRow.setHeight((short) 0x190);

			for (int i = 0; i < dataArray.size(); i++) {// 遍历每一行
				TeeDataRecord dbRecord = dataArray.get(i);
				HSSFRow contentRow = sheet.createRow(i + 1);// 第3行开始是数据
				contentRow.setHeight((short) 0x150);

				for (int j = 0; j < dbRecord.getFieldCnt(); j++) {// 遍历每一行的列
					if (i == 0) {// 循环第一次时设置标题
						map.put("fontName", "宋体");
						map.put("fontSize", (short) 16);
						map.put("isBoldWeight", true);
						map.put("isAlignCenter", true);
						map.put("isWrapText", false);
						// 创建第一行标题
						HSSFCell firstRowCell = firstRow.createCell(j);// 为第一行创建单元格
						firstRow.setHeight((short) 0x349);
						sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) (dbRecord.getFieldCnt() - 1)));// 合并：从第0行第0列开始到
																													// 第0行第dbRec.getFieldCnt()
																													// -1列结束

						firstRowCell.setCellValue(dbRecord.getValueByIndex(j).toString());

						HSSFCellStyle firstRowCellStyle = getTitleStyle(workbook, map);
						firstRowCellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
						firstRowCell.setCellStyle(firstRowCellStyle);

						if (j == 0) {
							countSizeList.add(dbRecord.getNameByIndex(j).length() + 80);// 设置每列的字数
						} else if (j == dbRecord.getFieldCnt() - 1) {
							countSizeList.add(dbRecord.getNameByIndex(j).length() + 100);// 设置每列的字数
						} else {
							countSizeList.add(dbRecord.getNameByIndex(j).length() + 20);// 设置每列的字数
						}
					} else if (i == 1) {
						// 创建第二标题
						map.put("fontName", "宋体");
						map.put("fontSize", (short) 12);
						map.put("isBoldWeight", false);
						map.put("isAlignCenter", true);
						map.put("isWrapText", true);
						HSSFCell secondRowCell = secondRow.createCell(j);// 为第二行创建单元格

						HSSFCellStyle secondRowCellStyle = getTitleStyle(workbook, map);
						secondRowCellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
						secondRowCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
						secondRowCell.setCellStyle(secondRowCellStyle);
						Region region = new Region(1, (short) 0, 1, (short) (dbRecord.getFieldCnt() - 1));// 合并：从第2行第1列开始到
																											// 第2行结束
						sheet.addMergedRegion(region);

						secondRowCell.setCellValue("投票描述：" + dbRecord.getValueByIndex(j).toString());
					} else if (i == 2) {
						// System.out.print(dbRecord.getValueByIndex(j).toString());
						// 第三行
						map.put("fontName", "宋体");
						map.put("fontSize", (short) 12);
						map.put("isBoldWeight", true);
						map.put("isAlignCenter", true);
						map.put("isWrapText", true);
						HSSFCell threeRowCell = threeRow.createCell(j);
						HSSFCellStyle threeRowCellStyle = getTitleStyle(workbook, map);
						threeRowCellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
						threeRowCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
						threeRowCell.setCellStyle(threeRowCellStyle);
						threeRowCell.setCellValue(dbRecord.getNameByIndex(j).toString());

						// 第四行,创建表头
						map.put("fontName", "宋体");
						map.put("fontSize", (short) 12);
						map.put("isBoldWeight", true);
						map.put("isAlignCenter", false);
						map.put("isWrapText", true);
						HSSFCell fourthRowCell = fourthRow.createCell(j);
						HSSFCellStyle fourthRowCellStyle = getTitleStyle(workbook, map);
						fourthRowCellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
						fourthRowCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
						fourthRowCell.setCellStyle(fourthRowCellStyle);

						String columnName = dbRecord.getValueByIndex(j).toString();
						if (columnName.indexOf("`~") > 0) {
							columnName = columnName.substring(0, columnName.indexOf("`~"));
						}
						Region region = new Region(3, (short) 0, 3, (short) (dbRecord.getFieldCnt() - 1));// 合并：从第4行第1列开始到
																											// 第3行结束
						sheet.addMergedRegion(region);
						fourthRowCell.setCellValue(columnName);
					} else {
						// 打印他行行的值
						boolean isBoldWeight = false;
						String value = "";
						if (dbRecord.getValueByIndex(j) != null) {
							value = dbRecord.getValueByIndex(j).toString();
						}
						if (value.indexOf("`~") > 0) {
							value = value.substring(0, value.indexOf("`~"));
							Region region = new Region(i + 1, (short) 0, i + 1, (short) (dbRecord.getFieldCnt() - 1));// 合并：从第4行第1列开始到
																														// 第3行结束
							sheet.addMergedRegion(region);
							isBoldWeight = true;
						}
						map.put("fontName", "宋体");
						map.put("fontSize", (short) 12);
						map.put("isBoldWeight", isBoldWeight);
						map.put("isAlignCenter", false);
						map.put("isWrapText", true);

						HSSFCell dataCell = contentRow.createCell(j);
						dataCell.setCellValue(value);
						dataCell.setCellStyle(getTitleStyle(workbook, map));
					}
				}
			}
			for (int i = 0; i < countSizeList.size(); i++) {
				sheet.setColumnWidth(i, (countSizeList.get(i)) * 150);
			}
			workbook.write(ops);
			ops.flush();
		} catch (Exception e1) {
			throw e1;
		} finally {

		}
		return ops;
	}

	/**
	 * 设置样式 2011-4-1
	 * 
	 * @param hssfwb
	 * @param fontName
	 *            字体名称
	 * @param fontSize
	 *            字体大小
	 * @param boldWeight
	 *            黑体加粗
	 * @return
	 */
	public static HSSFCellStyle getTitleStyle(HSSFWorkbook hssfwb, Map<Object, Object> map) {
		String fontName = (String) map.get("fontName");
		short fontSize = (Short) map.get("fontSize");
		boolean isBoldWeight = (Boolean) map.get("isBoldWeight");// 字体家粗
		boolean isAlignCenter = (Boolean) map.get("isAlignCenter");// 居中
		boolean isWrapText = (Boolean) map.get("isWrapText"); // 自动换行

		short boldWeight = 0;
		short alignCenter = 0;
		if (isBoldWeight) {
			boldWeight = HSSFFont.BOLDWEIGHT_BOLD;
		}
		if (isAlignCenter) {
			alignCenter = HSSFCellStyle.ALIGN_CENTER;
		} else {
			alignCenter = HSSFCellStyle.ALIGN_LEFT;
		}
		HSSFCellStyle cellStyle = hssfwb.createCellStyle();// 样式对象
		HSSFFont font = getTitleFont(hssfwb, fontName, fontSize, boldWeight);
		cellStyle.setFont(font);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
		cellStyle.setAlignment(alignCenter);
		cellStyle.setBorderBottom((short) 1); // 设置边框线大小

		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setWrapText(isWrapText);
		return cellStyle;
	}

	/**
	 * 设置样式
	 * 
	 * @date 2012-2-21
	 * @author
	 * @param hssfwb
	 * @param fontName
	 * @param fontSize
	 * @param boldWeight
	 * @return
	 */
	public static HSSFFont getTitleFont(HSSFWorkbook hssfwb, String fontName, short fontSize, short boldWeight) {
		if (fontName == null || "".equals(fontName)) {
			fontName = "";
		}
		HSSFFont fontStyle = hssfwb.createFont();
		fontStyle.setFontName(fontName);
		fontStyle.setFontHeightInPoints(fontSize);
		fontStyle.setBoldweight(boldWeight); // 黑体加粗
		return fontStyle;
	}

}
