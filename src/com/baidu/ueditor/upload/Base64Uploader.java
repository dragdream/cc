package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.webframe.util.global.TeeBeanFactory;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

public final class Base64Uploader
{
  public static State save(String content, Map<String, Object> conf)
  {
    byte[] data = decode(content);

    long maxSize = ((Long)conf.get("maxSize")).longValue();

    if (!validSize(data, maxSize)) {
      return new BaseState(false, 1);
    }

    String suffix = FileType.getSuffix("JPG");

    String savePath = PathFormat.parse((String)conf.get("savePath"), 
      (String)conf.get("filename"));

    savePath = savePath + suffix;

    State storageState = new BaseState(true);
    
   
    try {
    	 //获取上传类
        TeeBaseUpload baseUpload = (TeeBaseUpload) TeeBeanFactory.getBean("teeBaseUpload");
        ByteArrayInputStream input = new ByteArrayInputStream(data);
        TeeAttachment attachment = baseUpload.singleAttachUpload(input, maxSize, "涂鸦"+"."+suffix, "", TeeAttachmentModelKeys.imgupload, null);
		input.close();
		storageState.putInfo("url", "/attachmentController/downFile.action?id="+attachment.getSid());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      storageState.putInfo("type", suffix);
      storageState.putInfo("original", "");

    return storageState;
  }
  
  private static byte[] decode(String content) {
    return Base64.decodeBase64(content);
  }

  private static boolean validSize(byte[] data, long length) {
    return data.length <= length;
  }
}