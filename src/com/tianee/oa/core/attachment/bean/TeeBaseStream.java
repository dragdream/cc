package com.tianee.oa.core.attachment.bean;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.tianee.oa.core.system.encryption.TeeDealInputStream;
import com.tianee.oa.core.system.encryption.TeeFileDecryption;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author zhp
 * desc：主要是封装一下 将TeeAttachment 与文件流等操作 提供一些常用的方法
 *       这样方便操作 如： 
 *       获取文件大小  
 *       获取文件头信息
 *       获取文件后缀
 *       获取文件
 *  遵循开闭原则 设计此类
 */
public class TeeBaseStream {

	/**
	 * 附件文件bean
	 */
	private TeeAttachment attach;
	/**
	 * 文件大小
	 */
	private Long fileSize = 0l;
	/**
	 * 文件流
	 */
	private  InputStream fio = null;
	/**
	 * 获取文件名称
	 */
	private String fileName;
	/**
	 * 文件id
	 */
	private int fileId;
	/**
	 * 文件存储路径
	 */
	private String filePath;
	
	private String fileExt;
	
	private File file = null;
	/**
	 * @author:zhp
	 * 构造方法需要传入TeeAttachment对象
	 * @throws FileNotFoundException 
	 */
	public TeeBaseStream(TeeAttachment attach) throws FileNotFoundException{
		if(attach != null){
			this.attach = attach;
			fileName = attach.getFileName();
			filePath = attach.getAttachSpace().getSpacePath()+File.separator+attach.getModel()+File.separator+attach.getAttachmentPath()+File.separator+attach.getAttachmentName();
			fileId = attach.getSid();
			fileExt = attach.getExt();
		    file = new File(filePath);
		    if(file.exists()){
				//fileSize = file.length();
				fio = new FileInputStream(filePath);
				/**
				 * 读取附件加密系统参数
				 */
				String encryAlgo = attach.getEncryAlgo();//加密算法
				
				if(!TeeUtility.isNullorEmpty(encryAlgo)){
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
						fileSize = Long.parseLong(String.valueOf(destByte.length));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					fileSize = file.length();
				}
			}
		}
		
	}
	
	public void close(){
		if(fio!=null){
			try{
				fio.close();
			}catch(Exception e){
				
			}
		}
	}
	
	public TeeAttachment getAttach() {
		return attach;
	}
	public void setAttach(TeeAttachment attach) {
		this.attach = attach;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public InputStream getFileInputStream() {
		return fio;
	}
	public void setInputStream(InputStream fio) {
		this.fio = fio;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	public File getFile() {
		return file;
	}

	
}
