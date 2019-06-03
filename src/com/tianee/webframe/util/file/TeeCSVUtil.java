package com.tianee.webframe.util.file;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.tianee.webframe.data.TeeDataRecord;

public class TeeCSVUtil {
	/**
	 * 写入CVS文件
	 * @param printWriter
	 * @param dataArray
	 * @throws Exception
	 */
	public static void CVSWrite(PrintWriter printWriter,
			ArrayList<TeeDataRecord> dataArray) throws Exception {
		TeeCVSWriter csvw = new TeeCVSWriter(printWriter);
		csvw.writeAll(dataArray);
		csvw.flush();
		csvw.close();
	}
	/**
	 * 读CSV文件
	 * @param ips
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<TeeDataRecord> CVSReader(InputStream ips)
			throws Exception {
		return CVSReader(ips, "GBK");
	}
	/**
	 * 读CSV文件
	 * @param ips
	 * @param charset  
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<TeeDataRecord> CVSReader(InputStream ips,
			String charset) throws Exception {
		TeeCSVReader csvReader = null;
		String[] header = null;
		ArrayList<TeeDataRecord> result = new ArrayList<TeeDataRecord>();
		try {
			csvReader = new TeeCSVReader(new InputStreamReader(ips, charset),',');// importFile为要导入的文本格式逗号分隔的csv文件，提供getXX/setXX方法
			if (csvReader != null) {
				header = csvReader.readNext();
				String[] csvRow = null;// row

				while ((csvRow = csvReader.readNext()) != null) {
					TeeDataRecord dbRecord = new TeeDataRecord();
					for (int i = 0; i < csvRow.length; i++) {
						String temp = csvRow[i];
						if ("".equals(temp)) {
							temp = null;
						}
						if (header.length > i) {
							dbRecord.addField(header[i], temp);
						}
					}
					result.add(dbRecord);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
}
