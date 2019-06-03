package com.tianee.oa.core.attachment.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件过滤器
 * @author kakalion
 *
 */
public interface TeeUploadFileFilter {
	/**
	 * 
	 * @param fileName 实际文件名
	 * @param fileKeyName 表单域中的<input type=file>的控件名称
	 * @return
	 */
	public boolean accept(MultipartFile file,String fileKeyName);
}
