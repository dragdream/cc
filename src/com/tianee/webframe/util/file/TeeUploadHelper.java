package com.tianee.webframe.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.springframework.util.FileCopyUtils;

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.util.global.TeeSysProps;
/**
 * 
 * @author zhp
 * @createTime 2013-10-2
 * @desc: 这个主要负责文件作 文件加密解密 也在这里
 */
public class TeeUploadHelper {
	/**
	 * 保存上传文件
	 * 
	 * @param fileItem
	 *            文件项目
	 * @param savePath
	 *            保存文件的全路径
	 * @throws IOException
	 */
	public static void saveFile(InputStream inputStream, String savePath)
			throws IOException {
		if (savePath == null) {
			return;
		}
		File outFile = new File(savePath);
		File outPath = outFile.getParentFile();
		if (!outPath.exists()) {
			outPath.mkdirs();
		}
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(inputStream);
			out = new BufferedOutputStream(new FileOutputStream(outFile));
			byte[] buff = new byte[TeeConst.M];
			int length = 0;
			while ((length = in.read(buff)) > 0) {
				out.write(buff, 0, length);
				out.flush();
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception ex) {
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception ex) {
			}
		}
	}
	/**
	 * 
	 * @author zhp
	 * @createTime 2013-10-2
	 * @editTime 下午06:18:09
	 * @desc
	 */
	public static void saveFile(byte[] bytes,String savePath) throws IOException{
		 File outFile = new File(savePath);
		FileCopyUtils.copy(bytes, outFile);
	}
	
	/**
	 * 处理加密保存文件
	 * @param inputStream
	 * @param encryIns
	 * @param savePath
	 * @throws IOException
	 */
	public static void saveFile(InputStream inputStream,InputStream encryIns, int count,String savePath)
			throws IOException {
		if (savePath == null) {
			return;
		}
		File outFile = new File(savePath);
		File outPath = outFile.getParentFile();
		if (!outPath.exists()) {
			outPath.mkdirs();
		}
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		InputStream in = null;
		OutputStream out = null;
		DataOutputStream  dos = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(outFile));
			dos = new DataOutputStream(out);
			if(encryIns!=null){
				//1.记录加密内容大小
				int encryCount = 0 ;
				while(encryCount==0){
					encryCount = encryIns.available();
				}
				dos.writeInt(encryCount);
				dos.flush();
				//2.将加密后的内容写入outputStream
				in = new BufferedInputStream(encryIns);
				byte[] buff = new byte[TeeConst.M];
				int length = 0;
				while ((length = in.read(buff)) > 0) {
					dos.write(buff, 0, length);
					dos.flush();
				}
			}
			//3.将没有加密的内容写入outputStream
			in = new BufferedInputStream(inputStream);
			byte[] buff = new byte[TeeConst.M];
			int length = 0;
			//in.skip(count);
			while ((length = in.read(buff)) > 0) {
				dos.write(buff, 0, length);
				dos.flush();
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception ex) {
			}
			try {
				if (out != null) {
					out.close();
				}
				if(dos!=null){
					dos.close();
				}
			} catch (Exception ex) {
			}
		}
	}

}
