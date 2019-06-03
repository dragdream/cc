package com.tianee.oa.core.general.service;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.sanselan.Sanselan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.general.bean.TeeInterface;
import com.tianee.oa.core.general.dao.TeeInterfaceDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.file.TeeFileUtility;

@Service
public class TeeInterfaceService extends TeeBaseService {

	@Autowired
	private TeeInterfaceDao interfacelDao;
	
	@Autowired
	private TeeBaseUpload baseUpload;
	
	
	
	

	public TeeInterface addOrUpdate(TeeInterface intf , HttpServletRequest request) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		try {
			MultipartFile  file = multipartRequest.getFile("topAttachmentFile");
			if(file!=null && !file.isEmpty()){
				InputStream avatarIs = null;//头像
				//获取真实文件名称
				
				String realName = file.getOriginalFilename();
				 /* 获取文件后缀
				 */
				String avatrarNameExt = TeeFileUtility.getFileExtName(realName);// 文件后缀
				avatarIs = file.getInputStream();
				 //
				TeeAttachment attachement = baseUpload.singleAttachUpload(file, avatrarNameExt, TeeAttachmentModelKeys.SYSTEM,person);
				attachement.setModelId("0");
				if(attachement != null ){
				    intf.setTopAttachmentId(attachement.getSid() + "");	
				}
			}
			
			//上传im客户端图标
			file = multipartRequest.getFile("imPicFile");
			if(file!=null && !file.isEmpty()){
				InputStream avatarIs = null;//头像
				//获取真实文件名称
				
				String realName = file.getOriginalFilename();
				 /* 获取文件后缀
				 */
				String avatrarNameExt = TeeFileUtility.getFileExtName(realName);// 文件后缀
				avatarIs = file.getInputStream();
				 //
				TeeAttachment attachement = baseUpload.singleAttachUpload(file, avatrarNameExt, TeeAttachmentModelKeys.SYSTEM,person);
				attachement.setModelId("0");
				if(attachement != null ){
				    intf.setImPic(attachement.getSid() + "");	
				}
				
			}
			
			//上传移动端logo
			file = multipartRequest.getFile("mLogoFile");
			if(file!=null && !file.isEmpty()){
				InputStream avatarIs = null;//头像
				//获取真实文件名称
				
				String realName = file.getOriginalFilename();
				 /* 获取文件后缀
				 */
				String avatrarNameExt = TeeFileUtility.getFileExtName(realName);// 文件后缀
				avatarIs = file.getInputStream();
				 //
				TeeAttachment attachement = baseUpload.singleAttachUpload(file, avatrarNameExt, TeeAttachmentModelKeys.SYSTEM,person);
				attachement.setModelId("0");
				if(attachement != null ){
				    intf.setmLogo(attachement.getSid() + "");	
				}
				
			}
			
			//上传移动端背景图片
			file = multipartRequest.getFile("mPicFile");
			if(file!=null && !file.isEmpty()){
				InputStream avatarIs = null;//头像
				//获取真实文件名称
				
				String realName = file.getOriginalFilename();
				 /* 获取文件后缀
				 */
				String avatrarNameExt = TeeFileUtility.getFileExtName(realName);// 文件后缀
				avatarIs = file.getInputStream();
				 //
				TeeAttachment attachement = baseUpload.singleAttachUpload(file, avatrarNameExt, TeeAttachmentModelKeys.SYSTEM,person);
				attachement.setModelId("0");
				if(attachement != null ){
				    intf.setmPic(attachement.getSid() + "");	
				}
				
			}
			
			//上传移动端背景图片
			file = multipartRequest.getFile("welcomePicFile");
			if(file!=null && !file.isEmpty()){
				InputStream avatarIs = null;//头像
				//获取真实文件名称
				
				String realName = file.getOriginalFilename();
				 /* 获取文件后缀
				 */
				String avatrarNameExt = TeeFileUtility.getFileExtName(realName);// 文件后缀
				avatarIs = file.getInputStream();
				 //
				TeeAttachment attachement = baseUpload.singleAttachUpload(file, avatrarNameExt, TeeAttachmentModelKeys.SYSTEM,person);
				attachement.setModelId("0");
				if(attachement != null ){
				    intf.setWelcomePic(attachement.getSid() + "");
				}
				
			}
			
			//上传移动端签到区背景图片
			file = multipartRequest.getFile("appTopBgFile");
			if(file!=null && !file.isEmpty()){
				InputStream avatarIs = null;//头像
				//获取真实文件名称
				
				String realName = file.getOriginalFilename();
				 /* 获取文件后缀
				 */
				String avatrarNameExt = TeeFileUtility.getFileExtName(realName);// 文件后缀
				avatarIs = file.getInputStream();
				 //
				TeeAttachment attachement = baseUpload.singleAttachUpload(file, avatrarNameExt, TeeAttachmentModelKeys.SYSTEM,person);
				attachement.setModelId("0");
				if(attachement != null ){
				    intf.setAppTopBg(attachement.getSid() + "");
				}
				
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		if(intf.getSid() > 0 ){
			TeeInterface old = interfacelDao.selectById(intf.getSid());
			BeanUtils.copyProperties(intf,  old);
			//old.setLogOutText("dddd");
			interfacelDao.updaInterface(old);
			return old;
			
		}else{
			List<TeeInterface> list = interfacelDao.select();
		    if(list.size() > 0){
		    	TeeInterface old = list.get(0);
				BeanUtils.copyProperties(intf,  old);
				interfacelDao.updaInterface(old);
				return old;
		    }else{
				interfacelDao.addInterface(intf);
				return intf;
		    }
	
		}
	}

	
	public TeeInterface selectById(String sid) {
		TeeInterface old = interfacelDao.selectById(Integer.parseInt(sid));
	
		return old;
	}
	
	
	public TeeInterface select() {
		List<TeeInterface> list = interfacelDao.select();
	    if(list.size() > 0){
	    	return  list.get(0);
	    }
		return null;
	}
	
	public void setInterfacelDao(TeeInterfaceDao interfacelDao) {
		this.interfacelDao = interfacelDao;
	}
	
}
