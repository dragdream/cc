package com.tianee.oa.core.office2html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @author：罗大锤
 * @date: 2017年9月5日 上午11:06:58
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class FileUtils {
	private static final String ENCODING = "UTF-8";// UTF-8

	public static String GetFileExt(String path) {
		String ext = null;
		int i = path.lastIndexOf('.');
		if (i > 0 && i < path.length() - 1) {
			ext = path.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	public static void createHtmlDir(String path) {
		int i = path.lastIndexOf('/');
		String dirPath = "";
		if (i > 0 && i < path.length() - 1) {
			dirPath = path.substring(0, i).toLowerCase();
		}
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

	}

	public static void createDir(String dirPath) {
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

	}

	public static void writeFile(String content, String path) {
		createHtmlDir(path);
		OutputStream os = null;
		BufferedWriter bw = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(os, ENCODING));
			bw.write(content);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (os != null)
					os.close();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}

}
