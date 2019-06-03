package com.tianee.webframe.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PublishTools {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String baseDir = "D:/TeePublic/WebContent";
		ValideFileFilter(new File(args[0]));
	}
	
	public static void ValideFileFilter(File file){
		//如果是目录，则递归下级目录
		if(file.isDirectory()){
			File files[] = file.listFiles();
			for(File f:files){
				ValideFileFilter(f);
			}
		}else{//如果是文件，则进行筛选
			if(!file.getName().endsWith(".jsp")){
				return;
			}
			FileInputStream in = null;
			boolean isZatpFile = false;
			try {
				in = new FileInputStream(file);
				byte buf[] = new byte[4];
				in.read(buf);
				if(new String(buf).equals("ZATP")){//删除掉所有内容为ZATP的文件
					isZatpFile = true;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(in!=null){
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
					}
				}
				try{
					if(isZatpFile){
						file.delete();//删除文件
					}
				}catch(Exception ex1){}
			}
		}
	}

}
