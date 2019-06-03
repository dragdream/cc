package com.tianee.webframe.util.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.PrivilegedAction;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.system.encryption.TeeDealInputStream;
import com.tianee.oa.core.system.encryption.TeeFileDecryption;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeFileConst;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeUtility;

public class TeeFileUtility {

	/**
	 * 扩展哈希表
	 */
	private static Map extMap = new HashMap();

	/**
	 * 设置扩展
	 * 
	 * @param key
	 * @param value
	 */
	public static void setExt(Object key, Object value) {
		extMap.put(key, value);
	}

	/**
	 * 清除扩展
	 */
	public static void removeExt(Object key) {
		extMap.remove(key);
	}

	/**
	 * 把文件中的内容读入数组，每行作为一个元素
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static void storeString2File(String fileName, String content)
			throws Exception {
		OutputStream outs = null;
		try {
			File file = new File(fileName);
			File outDir = file.getParentFile();
			if (!outDir.exists()) {
				outDir.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			if (!file.canWrite()) {
				file.setWritable(true);
			}
			outs = new FileOutputStream(file);
			storeString2File(outs, content, TeeConst.DEFAULT_CODE);
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				if (outs != null) {
					outs.close();
				}
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * 把文件中的内容读入数组，每行作为一个元素
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static void storeArray2Line(String fileName, List rtList)
			throws Exception {
		storeArray2Line(fileName, rtList, TeeConst.DEFAULT_CODE);
	}

	/**
	 * 把文件中的内容读入数组，每行作为一个元素
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static void storeArray2Line(String fileName, List rtList,
			String charSet) throws Exception {
		OutputStream outs = null;
		try {
			File file = new File(fileName);
			File outDir = file.getParentFile();
			if (!outDir.exists()) {
				outDir.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			if (!file.canWrite()) {
				file.setWritable(true);
			}
			outs = new FileOutputStream(file);
			storeArray2Line(outs, rtList, charSet);
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				if (outs != null) {
					outs.close();
				}
			} catch (Exception ex) {
				// System.out.println(ex.getMessage());
			}
		}
	}

	/**
	 * 从文件输出到流
	 * 
	 * @param file
	 * @param out
	 */
	public static void copyFile(String file, OutputStream out) {
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			byte[] buff = new byte[1024];
			int readLength = 0;
			while ((readLength = in.read(buff)) > 0) {
				out.write(buff, 0, readLength);
			}
			out.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 把字节数组保存到文件
	 * 
	 * @param fileName
	 * @param srcBuf
	 * @throws Exception
	 */
	public static void storBytes2File(String fileName, byte[] srcBuf)
			throws Exception {
		storBytes2File(fileName, srcBuf, 0, srcBuf.length);
	}

	/**
	 * 把字节数组保存到文件
	 * 
	 * @param fileName
	 * @param srcBuf
	 * @throws Exception
	 */
	public static void storBytes2File(String fileName, byte[] srcBuf,
			int offset, int length) throws Exception {
		OutputStream outs = null;
		try {
			File outFile = new File(fileName);
			File outDir = outFile.getParentFile();
			if (!outDir.exists()) {
				outDir.mkdirs();
			}
			if (!outFile.exists()) {
				outFile.createNewFile();
			}
			if (!outFile.canWrite()) {
				outFile.setWritable(true);
			}
			outs = new FileOutputStream(outFile);

			outs.write(srcBuf, offset, length);
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				if (outs != null) {
					outs.close();
				}
			} catch (Exception ex) {
				// System.out.println(ex.getMessage());
			}
		}
	}

	/**
	 * 把文件加载到字节数组
	 * 
	 * @param fileName
	 */
	public static byte[] loadFile2Bytes(String fileName) throws Exception {
		InputStream in = null;
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				return null;
			}
			in = new FileInputStream(fileName);
			int fileLength = (int) file.length();
			byte[] buff = new byte[fileLength];
			in.read(buff, 0, fileLength);

			return buff;
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * 把流中的内容读入到字符串缓冲对象中
	 * 
	 * @param in
	 *            数据流
	 * @param startLine
	 *            起始行号
	 * @return
	 * @throws Exception
	 */
	public static StringBuffer loadLine2Buff(InputStream in, int startLine,
			int endLine, String encode) throws Exception {

		StringBuffer rtBuffer = new StringBuffer();

		encode = encode == null ? "UTF-8" : encode;
		LineNumberReader reader = new LineNumberReader(new InputStreamReader(
				in, encode));
		String str = null;
		for (int i = 0; (str = reader.readLine()) != null; i++) {
			if (i < startLine) {
				continue;
			}
			if (i > endLine) {
				break;
			}
			rtBuffer.append(str);
			rtBuffer.append("\n");
		}
		if (rtBuffer.length() > 1) {
			rtBuffer.delete(rtBuffer.length() - 1, rtBuffer.length());
		}

		return rtBuffer;
	}

	/**
	 * 把文件中的内容读入数组，每行作为一个元素
	 * 
	 * @param file
	 * @param startLine
	 *            起始行号
	 * @return
	 * @throws Exception
	 */
	public static StringBuffer loadLine2Buff(String fileName, int startLine,
			int endLine, String encode) throws Exception {

		StringBuffer rtBuffer = null;

		InputStream ins = null;
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				return rtBuffer;
			}
			ins = new FileInputStream(file);
			rtBuffer = loadLine2Buff(ins, startLine, endLine, encode);
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				if (ins != null) {
					ins.close();
				}
			} catch (Exception ex) {
				// System.out.println(ex.getMessage());
			}
		}
		return rtBuffer;
	}

	/**
	 * 把文件中的内容读入数组，每行作为一个元素
	 * 
	 * @param file
	 * @param startLine
	 *            起始行号
	 * @return
	 * @throws Exception
	 */
	public static StringBuffer loadLine2Buff(String fileName, int startLine,
			String encode) throws Exception {

		return loadLine2Buff(fileName, startLine, Integer.MAX_VALUE, encode);
	}

	/**
	 * 把文件中的内容读入数组，每行作为一个元素
	 * 
	 * @param file
	 * @param startLine
	 *            起始行号
	 * @return
	 * @throws Exception
	 */
	public static StringBuffer loadLine2Buff(String fileName, int startLine)
			throws Exception {

		return loadLine2Buff(fileName, startLine, Integer.MAX_VALUE,
				TeeConst.DEFAULT_CODE);
	}

	/**
	 * 把文件中的内容读入数组，每行作为一个元素
	 * 
	 * @param file
	 * @param startLine
	 *            起始行号
	 * @return
	 * @throws Exception
	 */
	public static StringBuffer loadLine2Buff(String fileName, String encode)
			throws Exception {

		return loadLine2Buff(fileName, 0, Integer.MAX_VALUE, encode);
	}

	/**
	 * 把文件中的内容读入数组，每行作为一个元素
	 * 
	 * @param file
	 * @param startLine
	 *            起始行号
	 * @return
	 * @throws Exception
	 */
	public static StringBuffer loadLine2Buff(String fileName) throws Exception {

		return loadLine2Buff(fileName, 0, Integer.MAX_VALUE,
				TeeConst.DEFAULT_CODE);
	}

	/**
	 * 把文件中的内容读入数组，每行作为一个元素
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static void loadLine2Array(String fileName, List rtList)
			throws Exception {
		loadLine2Array(fileName, 0, Integer.MAX_VALUE, rtList,
				TeeConst.DEFAULT_CODE);
	}

	/**
	 * 把文件中的内容读入数组，每行作为一个元素
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static void loadLine2Array(String fileName, List rtList,
			String charSet) throws Exception {
		loadLine2Array(fileName, 0, Integer.MAX_VALUE, rtList, charSet);
	}

	/**
	 * 把文件中的内容读入数组，每行作为一个元素
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static void loadLine2Array(String fileName, int startLine,
			int endLine, List rtList, String charSet) throws Exception {

		InputStream ins = null;
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				return;
			}
			ins = new FileInputStream(file);
			loadLine2Array(ins, startLine, endLine, rtList, charSet);
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				if (ins != null) {
					ins.close();
				}
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * 把流中的内容读入到数组之中，每行作为一个元素
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static void loadLine2Array(InputStream in, int startLine,
			int endLine, List rtList, String charSet) throws Exception {

		LineNumberReader reader = new LineNumberReader(new InputStreamReader(
				in, charSet));
		String str = null;
		for (int i = 0; (str = reader.readLine()) != null; i++) {
			if (i < startLine) {
				continue;
			}
			if (i > endLine) {
				break;
			}
			rtList.add(str);
		}
	}

	/**
	 * 把数组中的内如输出到流中，每个元素作为一行
	 * 
	 * @param out
	 * @param list
	 * @throws Exception
	 */
	public static void storeArray2Line(OutputStream out, List list,
			String charSet) throws Exception {
		if (list == null) {
			return;
		}
		OutputStreamWriter writer = new OutputStreamWriter(out, charSet);
		try {
			for (int i = 0; i < list.size(); i++) {
				writer.write(list.get(i).toString());
				writer.write("\r\n");
				writer.flush();
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * 把数组中的内如输出到流中，每个元素作为一行
	 * 
	 * @param out
	 * @param list
	 * @throws Exception
	 */
	public static void storeString2File(OutputStream out, String str,
			String charSet) throws Exception {
		if (str == null) {
			return;
		}

		OutputStreamWriter writer = null;
		try {
			if (charSet != null) {
				writer = new OutputStreamWriter(out, charSet);
			} else {
				writer = new OutputStreamWriter(out);
			}
			writer.write(str);
			writer.flush();
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * 删除指定文件或者目录
	 * 
	 * @param file
	 */
	public static void deleteAll(String file) {
		deleteAll(new File(file));
	}

	/**
	 * 删除指定文件或者目录
	 * 
	 * @param file
	 */
	public static void deleteAll(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}
		File[] fileList = file.listFiles();
		for (int i = 0; fileList != null && i < fileList.length; i++) {
			deleteAll(fileList[i]);
		}
		file.delete();
	}

	/**
	 * 把Unix形式的文件路径转换成Windows形式的路径
	 * 
	 * @param fileName
	 * @return
	 */
	public static String windows2Unix(String fileName) {
		if (fileName == null) {
			return "";
		}
		return fileName.replace('\\', '/');
	}

	/**
	 * 把Windows形式的文件路径转换成Unix形式的路径
	 * 
	 * @param fileName
	 * @return
	 */
	public static String unix2Windows(String fileName) {
		if (fileName == null) {
			return "";
		}
		return fileName.replace('/', '\\');
	}

	/**
	 * 输出文件名称
	 * 
	 * @param fileName
	 * @param outputFile
	 */
	public static void outPutFileName(String fileName, String outputFile)
			throws Exception {

		ArrayList fileList = new ArrayList();
		outPutFileName(new File(fileName), fileList);

		if (outputFile != null) {
			storeArray2Line(outputFile, fileList);
		} else {
			for (int i = 0; i < fileList.size(); i++) {
				// System.out.println(fileList.get(i).toString());
			}
		}
	}

	/**
	 * 输出文件
	 * 
	 * @param fileName
	 */
	private static void outPutFileName(File file, ArrayList fileList)
			throws Exception {

		if (!file.exists()) {
			return;
		}
		if (file.isFile()) {
			fileList.add(file.getAbsolutePath());
		} else if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				outPutFileName(files[i], fileList);
			}
		}
	}

	/**
	 * 设置文件修改时间
	 * 
	 * @param fileName
	 * @param time
	 */
	public static void setLastModified(String fileName, long time) {
		if (TeeUtility.isNullorEmpty(fileName)) {
			return;
		}
		File file = new File(fileName);
		setLastModified(file, time);
	}

	/**
	 * 设置文件修改时间
	 * 
	 * @param fileName
	 * @param time
	 */
	public static void setLastModified(File file, long time) {
		if (!file.exists()) {
			return;
		}
		if (file.isFile()) {
			file.setLastModified(time);
		} else {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				setLastModified(fileList[i], time);
			}
		}
	}

	/**
	 * 设置文件是否可写-是
	 * 
	 * @param fileName
	 * @param time
	 */
	public static void setWritable(String fileName) {
		if (TeeUtility.isNullorEmpty(fileName)) {
			return;
		}
		File file = new File(fileName);
		setWritable(file, true);
	}

	/**
	 * 设置文件是否可写
	 * 
	 * @param fileName
	 * @param time
	 */
	public static void setWritable(String fileName, boolean writable) {
		if (TeeUtility.isNullorEmpty(fileName)) {
			return;
		}
		File file = new File(fileName);
		setWritable(file, writable);
	}

	/**
	 * 设置文件修改时间
	 * 
	 * @param fileName
	 * @param time
	 */
	public static void setWritable(File file, boolean writable) {
		if (!file.exists()) {
			return;
		}
		if (file.isFile()) {
			file.setWritable(writable);
		} else {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				setWritable(fileList[i], writable);
			}
		}
	}

	/**
	 * 复制文件从${form}到${to}
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public static boolean copy(String from, String to) {
		File fromFile = new File(from);
		if (!fromFile.exists() || !fromFile.isFile()) {
			return false;
		}
		File toFileDirectory = new File(to);

		if (!toFileDirectory.exists()) {
			toFileDirectory.mkdirs();
		}
		
		File targetFile = new File(to + "/"+ fromFile.getName());
		if(targetFile.exists()){
			return true;
		}

		FileInputStream input = null;
		FileOutputStream output = null;
		byte b[] = new byte[1024];
		int i = 0;
		try {
			input = new FileInputStream(fromFile);
			output = new FileOutputStream(new File(to + "/"
					+ fromFile.getName()));
			while ((i = input.read(b)) != -1) {
				output.write(b, 0, i);
			}
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 复制文件夹从${form}到${to}
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public static boolean copyDirs(String from, String to) {
		File fromFile = new File(from);
		if (!fromFile.exists() || !fromFile.isDirectory()) {
			return false;
		}
		File toFileDirectory = new File(to);

		if (!toFileDirectory.exists()) {
			toFileDirectory.mkdirs();
		}

		copyDirs0(from, to);

		return true;
	}

	private static void copyDirs0(String from, String to) {
		File fromFile = new File(from);
		File toFile = new File(to);
		toFile.mkdirs();
		File list[] = fromFile.listFiles();
		for (File f : list) {
			if (f.isDirectory()) {
				copyDirs0(f.getAbsolutePath(), to + "/" + f.getName());
			} else {
				copy(f.getAbsolutePath(), to);
			}
		}
	}

	/**
	 * 取得文件扩展名
	 * 
	 * @return
	 */
	public static String getFileExtName(String filePath) {
		if (filePath == null) {
			return "";
		}
		String rtName = getFileName(filePath);
		int pointIndex = rtName.lastIndexOf(TeeFileConst.PATH_POINT);
		if (pointIndex < 0) {
			return "";
		}
		if (pointIndex == rtName.length() - 1) {
			return "";
		}
		return rtName.substring(pointIndex + 1, rtName.length());
	}

	/**
	 * 取得文件名，带扩展名部分
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		if (filePath == null) {
			return null;
		}
		int startIndex = 0;
		if (filePath.lastIndexOf(TeeFileConst.PATH_SPLIT_FILE_WIN) >= 0) {
			startIndex = filePath.lastIndexOf(TeeFileConst.PATH_SPLIT_FILE_WIN) + 1;
		} else if (filePath.lastIndexOf(TeeFileConst.PATH_SPLIT_URL) >= 0) {
			startIndex = filePath.lastIndexOf(TeeFileConst.PATH_SPLIT_URL) + 1;
		}

		return filePath.substring(startIndex, filePath.length());
	}

	/**
	 * 取得文件不带扩展名部分的名称
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileNameNoExt(String filePath) {
		if (filePath == null) {
			return null;
		}
		String rtName = getFileName(filePath);
		int pointIndex = rtName.lastIndexOf(TeeFileConst.PATH_POINT);
		if (pointIndex < 0) {
			return rtName;
		}
		if (pointIndex == 0) {
			return "";
		}
		return rtName.substring(0, pointIndex);
	}

	/**
	 * 获取文件大小描述
	 * 
	 * @param size
	 * @return
	 */
	public static String getFileSizeDesc(long size) {
		DecimalFormat df = new DecimalFormat("####.##");
        String result = new String();
        if (size >= 0 && size < 1024) {
            result = String.valueOf(size);
            result += "B";
        } else if (size >= 1024 && size < 1024 * 1024) {
            // result = String.valueOf((float)size / 1024);
            result = df.format((double) size / 1024);
            result += "KB";
        } else if (size >= 1024 * 1024 && size < 1024 * 1024 * 1024) {
            // result = String.valueOf(size / 1024*1024);
            result = df.format((double) size / (1024 * 1024));
            result += "M";
        } else if (size >= 1024 * 1024 * 1024) {
            // result = String.valueOf(size / 1024*1024*1024);
            result = df.format((double) size / (1024 * 1024 * 1024));
            result += "G";
        } else {
            result = "errorData";
        }
        return result;
		/*double dbSize = size;
		double mb = dbSize / (1024 * 1024);
		if (mb >= Double.parseDouble("0.01")) {
			return String.valueOf(mb).substring(
					0,
					(String.valueOf(mb).length() >= 4 ? 4 : String.valueOf(mb)
							.length()))
					+ "MB";
		} else {
			mb = dbSize / 1024;
			return String.valueOf(mb).substring(
					0,
					(String.valueOf(mb).length() >= 4 ? 4 : String.valueOf(mb)
							.length()))
					+ "KB";
		}
	}*/
		
	}
	/**
	 * 读取流到文件
	 * @param input
	 */
	public static void readStreamToFile(InputStream input,File file){
		if(input==null){
			return;
		}
		OutputStream output = null;
		try {
			if(!file.exists()){
				//创建文件
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			output = new FileOutputStream(file);
			byte b[] = new byte[1024];
			int length = 0;
			while((length=input.read(b))!=-1){
				output.write(b, 0, length);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static String computeFileMd5(String filePath){
		String value = null;
		FileInputStream in = null;
		FileChannel fc = null;
		try {
			File file = new File(filePath);
	        in = new FileInputStream(file);
	        long offset = 10*1024*1024;
	        if(file.length()<offset){
	        	offset = file.length();
	        }
	        fc = in.getChannel();
	        final MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, offset);
	        MessageDigest md5 = MessageDigest.getInstance("MD5");
	        md5.update(byteBuffer);
	        BigInteger bi = new BigInteger(1, md5.digest());
	        value = bi.toString(16);
	        
	        AccessController.doPrivileged(new PrivilegedAction() {
                public Object run() {
                try {
                   Method getCleanerMethod = byteBuffer.getClass().getMethod("cleaner",new Class[0]);
                   getCleanerMethod.setAccessible(true);
                   sun.misc.Cleaner cleaner =(sun.misc.Cleaner)getCleanerMethod.invoke(byteBuffer,new Object[0]);
                   cleaner.clean();
                } catch(Exception e) {
                   e.printStackTrace();
                }
                   return null;}});
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	
	            if(null != in) {
	                try {
	                in.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	                if(null != fc) {
		                try {
		                fc.close();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }}
	        }
	    }
	    return value;
	}
	
	/**
	 * 复制同时解密文件
	 */
	public static void copyDecryptFile(TeeAttachment attachment,String destPath){
		InputStream fio =null;
		OutputStream outs = null;
		try{
			String encryAlgo = attachment.getEncryAlgo();//加密算法
			String filePath = attachment.getAttachSpace().getSpacePath()+File.separator+attachment.getModel()+File.separator+attachment.getAttachmentPath()+File.separator;
			File destFile = new File(destPath);
			fio = new FileInputStream(filePath+attachment.getAttachmentName());
			DataInputStream dis = new DataInputStream(fio);
			int count =0;
			int encryCount = 0;
			byte[] srcByte = null;
			try {
				encryCount = dis.readInt();
				while (count == 0) {
					count = dis.available();
				}
				srcByte =new byte[count]; 
				dis.read(srcByte);
			} catch (IOException e) {
				e.printStackTrace();
			}
			TeeFileDecryption decry = new TeeFileDecryption(encryAlgo);
			byte[] ins = null;
			byte[] data = new byte[encryCount];
			try {
				System.arraycopy(srcByte, 0, data, 0, encryCount);
				ins = decry.decrypt(data);
				TeeDealInputStream deal = new TeeDealInputStream();
				byte[] destByte = null;
				destByte = deal.dealInputStream(srcByte,ins,encryCount);
				fio = new ByteArrayInputStream(destByte);
				outs = new FileOutputStream(destFile);
				byte[] buff = new byte[TeeConst.K];
	            int readLength = 0;
	            while ((readLength = fio.read(buff)) > 0) {
	                outs.write(buff, 0, readLength);
	                outs.flush();
	            }
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(fio!=null){
				try {
					fio.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(outs!=null){
				try {
					outs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
