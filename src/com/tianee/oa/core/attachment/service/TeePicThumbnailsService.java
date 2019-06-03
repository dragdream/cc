package com.tianee.oa.core.attachment.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.coobird.thumbnailator.Thumbnails;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeePicThumbnailsService extends TeeBaseService{
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private TeeBaseUpload baseUpload;
	
	/**
     * 缩放图片
     */
    public TeeAttachment picZoom(int attachId) {
    	TeeAttachment oldAttach = attachmentService.getById(attachId);
    	String ext = oldAttach.getExt();
    	TeeAttachment newAttach = null;
    	if("jpg".equals(ext) || "png".equals(ext) || "gif".equals(ext)){
    		try {
    			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    			
    			
    			Thumbnails.of(oldAttach.getFilePath())
    			.size(80, 80).keepAspectRatio(false).toOutputStream(outputStream);
    			
    			InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
    			newAttach = baseUpload.singleAttachUpload(inputStream, inputStream.available(), oldAttach.getFileName(), "", TeeAttachmentModelKeys.imgupload, "", null);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
        return newAttach;
    }
}
