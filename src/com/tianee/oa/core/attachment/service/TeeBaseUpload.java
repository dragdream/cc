package com.tianee.oa.core.attachment.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.bean.TeeAttachmentSpace;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.util.TeeAttachSpaceGenerator;
import com.tianee.oa.core.attachment.util.TeeFirstDefaultAttachSpaceGenerator;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.system.encryption.TeeFileEncryption;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.file.TeeUploadHelper;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 上传基类
 * 
 * @author zhp
 * @createTime 2013-10-4
 * @desc:简单封装一下 上传类
 */
@Service
public class TeeBaseUpload extends TeeBaseService{

	@Autowired
	@Qualifier("teeAttachmentService")
	private TeeAttachmentService attachmentService;

	@Autowired
	private TeePersonDao personDao;

	/**
	 * 
	 * @author zhp
	 * @createTime 2013-10-4
	 * @editTime 下午12:44:17
	 * @desc multipartRequest spring 封装的request model 模块名字
	 */
	public TeeAttachment singleAttachUpload(
			MultipartHttpServletRequest multipartRequest, String model) throws IOException {
		String disName = multipartRequest.getParameter("disName");
		MultipartFile file = null;
		Map<String, MultipartFile> files = multipartRequest.getFileMap();
		Set<String> keys = files.keySet();
		for (String key : keys) {
			file = files.get(key);
			if (file != null) {
				continue;
			}
		}

		TeePerson loginPerson = (TeePerson) multipartRequest.getSession()
				.getAttribute(TeeConst.LOGIN_USER);
		TeePerson realPerson = personDao.load(loginPerson.getUuid());
		return singleAttachUpload(file, disName, model, realPerson);
	}

	/**
	 * 
	 * @author zhp
	 * @createTime 2013-10-5
	 * @editTime 上午10:06:25
	 * @desc multipartRequest spring 封装的request model 模块名 disNameParaName 文件显示名
	 *       如果为空 则是文件上传时候的真实名字
	 * 
	 */
	public TeeAttachment singleAttachUpload(
			MultipartHttpServletRequest multipartRequest, String model,
			String disNameParaName) throws IOException {
		String disName = multipartRequest.getParameter(disNameParaName);
		MultipartFile file = null;
		Map<String, MultipartFile> files = multipartRequest.getFileMap();
		Set<String> keys = files.keySet();
		for (String key : keys) {
			file = files.get(key);
			if (file != null) {
				continue;
			}
		}

		TeePerson loginPerson = (TeePerson) multipartRequest.getSession()
				.getAttribute(TeeConst.LOGIN_USER);
		TeePerson realPerson = personDao.load(loginPerson.getUuid());

		return singleAttachUpload(file, disName, model, realPerson);
	}

	/**
	 * 多附件上传
	 * 
	 * @author zhp
	 * @createTime 2013-10-5
	 * @editTime 上午10:12:14
	 * @desc
	 * 
	 */
	public List<TeeAttachment> manyAttachUpload(
			MultipartHttpServletRequest multipartRequest, String model)
			throws IOException {
		List<TeeAttachment> list = new ArrayList<TeeAttachment>();
		String modelId = multipartRequest.getParameter("modelId");
		multipartRequest.getFileMap();
		Map<String, MultipartFile> files = multipartRequest.getFileMap();
		Set<String> keys = files.keySet();
		MultipartFile file = null;
		TeePerson loginPerson = (TeePerson) multipartRequest.getSession()
				.getAttribute(TeeConst.LOGIN_USER);
		TeePerson realPerson = personDao.load(loginPerson.getUuid());

		for (String key : keys) {
			file = files.get(key);
			if (file.isEmpty())
				continue;
			TeeAttachment attach = singleAttachUpload(file, "",
						
			model, realPerson);
			attach.setModelId(modelId);
			list.add(attach);
		}
		return list;
	}

	/**
	 * 在线创建附件
	 * 
	 * @disName 显示名 不要带后缀
	 * @fileExt 后缀名 不带点
	 * @author zhp
	 * @createTime 2013-10-13
	 * @editTime 下午04:48:39
	 * @desc
	 */
	public TeeAttachment newAttachment(String disName, String fileExt,
			String model, TeePerson person) throws IOException {

		if (TeeUtility.isNullorEmpty(model)) {
			model = TeeSysProps.getDefaultAttachModelPath();
		}
		if (TeeUtility.isNullorEmpty(fileExt)) {
			throw new TeeOperationException("新建附件后缀为空!");
		}
		
		//通过默认运算获取附件指定附件空间
		TeeAttachSpaceGenerator attachSpaceGenerator = TeeAttachSpaceGenerator.getInstance(simpleDaoSupport);
		//附件空间
		TeeAttachmentSpace space = attachSpaceGenerator.generate();
		
		/**
		 * 模块目录
		 */
		String filePath = space.getSpacePath() + File.separator + model;
		String sysAttachPath = TeeSysProps.getSystemDefaultAttachPath();
		/**
		 * 要从服务器断获取的 文件路径
		 */
		String serverFilePath = "";

		/**
		 * 根据后缀 判断应该 新建什么样的附件 fileExt
		 */
		if (!TeeUtility.isNullorEmpty(fileExt)) {
			serverFilePath = sysAttachPath + File.separator + "copy." + fileExt;
		}
		/**
		 * 显示名
		 */
		disName = disName + "." + fileExt;
		/**
		 * 修改后的文件名
		 */
		String fileName2 = UUID.randomUUID().toString();
		fileName2 = fileName2.replaceAll("-", "");
		fileName2 = fileName2 + "." + fileExt;// 这是修改后的名字

		File f1 = new File(filePath);
		if (!f1.exists()) {
			f1.mkdir();
		}
		Calendar cld = Calendar.getInstance();
		int year = cld.get(Calendar.YEAR) % 100;
		int month = cld.get(Calendar.MONTH) + 1;
		String mon = month >= 10 ? month + "" : "0" + month;
		String hard = year + mon;

		File f2 = new File(filePath + File.separator + hard);
		if (!f2.exists()) {
			f2.mkdir();
		}

		String tmp = filePath + File.separator + hard + File.separator
				+ fileName2;

		new File(filePath + File.separator + hard + File.separator).mkdir();
	
		TeeAttachment attach = new TeeAttachment();
		attach.setCreateTime(Calendar.getInstance());
		attach.setAttachmentPath(hard);
		attach.setExt(fileExt);
		attach.setAttachSpace(space);
		attach.setFileName(disName);
		attach.setAttachmentName(fileName2);
		attach.setModel(model);
		attach.setPriority(0);
		attach.setUser(person);

		try {
			FileCopyUtils.copy(new File(serverFilePath), new File(tmp));
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		/**
		 * 保存附件到数据库
		 */
		attachmentService.addAttachment(attach);
		return attach;
	}

	/**
	 * 
	 * @author zhp
	 * @createTime 2013-10-5
	 * @editTime 上午10:05:32
	 * @desc
	 */
	public TeeAttachment singleAttachUpload(MultipartFile file, String disName,
			String model, TeePerson realPerson) throws IOException {

		if (file == null) {
			return null;
		}
		if (TeeUtility.isNullorEmpty(model)) {
			model = TeeSysProps.getDefaultAttachModelPath();
		}
		
		//验证当前上传空间是否满足要求
		boolean valid = attachmentService.isValidSpaceInfo(model, file.getSize(), realPerson);
		if(!valid){
			throw new TeeOperationException("文件存储超出可用空间，上传失败！");
		}
		
		return singleAttachUpload(file.getInputStream(),file.getSize(),file.getOriginalFilename(),disName,model,realPerson);
	}
	
	public TeeAttachment singleAttachUpload(InputStream inputStream,long fileSize,String realName , String disName,
			String model, TeePerson realPerson) throws IOException {
		return singleAttachUpload(inputStream,fileSize,realName,disName,model,"",realPerson);
	}
	
	/**
	 * 按照规则保存文件
	 * @param inputStream 输入流
	 * @param fileSize 文件大小
	 * @param realName 文件真实名称
	 * @param disName 文件显示名称（可为空）
	 * @param model 文件所属模块
	 * @param realPerson 文件上传人
	 * @return
	 * @throws IOException
	 */
	public TeeAttachment singleAttachUpload(InputStream inputStream,long fileSize,String realName , String disName,
			String model,String modelId, TeePerson realPerson) throws IOException {
		//通过默认运算获取附件指定附件空间
		TeeAttachSpaceGenerator attachSpaceGenerator = TeeAttachSpaceGenerator.getInstance(simpleDaoSupport);
		//附件空间
		TeeAttachmentSpace space = attachSpaceGenerator.generate();
		
		/**
		 * 模块目录
		 */
		String filePath = space.getSpacePath() + File.separator + model;

		/**
		 * 获取文件后缀
		 */
		String fileExt = TeeFileUtility.getFileExtName(realName);// 文件后缀
		/**
		 * 显示名
		 */
		// String disName = multipartRequest.getParameter("disName");
		if (TeeUtility.isNullorEmpty(disName)) {
			disName = realName;
		} else {
			disName = disName + "." + fileExt;
		}
		/**
		 * 修改后的文件名
		 */
		String fileName2 = UUID.randomUUID().toString();
		fileName2 = fileName2.replaceAll("-", "");
		fileName2 = fileName2 + "." + fileExt;// 这是修改后的名字

		File f1 = new File(filePath);
		if (!f1.exists()) {
			f1.mkdir();
		}
		Calendar cld = Calendar.getInstance();
		int year = cld.get(Calendar.YEAR) % 100;
		int month = cld.get(Calendar.MONTH) + 1;
		String mon = month >= 10 ? month + "" : "0" + month;
		String hard = year + mon;

		File f2 = new File(filePath + File.separator + hard);
		if (!f2.exists()) {
			f2.mkdir();
		}

		String tmp = filePath + File.separator + hard + File.separator
				+ fileName2;

		TeeAttachment attach = new TeeAttachment();
		attach.setCreateTime(Calendar.getInstance());
		attach.setAttachmentPath(hard);
		attach.setExt(fileExt);
		attach.setFileName(disName);
		attach.setAttachSpace(space);
		attach.setAttachmentName(fileName2);
		attach.setModel(model);
		attach.setModelId(modelId);
		attach.setPriority(0);
		attach.setSize(fileSize);
		attach.setUser(realPerson);
		
		
		/**
		 * 读取附件加密系统参数
		 */
		String attachEncry = TeeSysProps.getProps().getProperty("ATTACH_ENCRY");//是否加密
		
		String encryAlgo = TeeSysProps.getProps().getProperty("ENCRY_ALGO");//加密算法
		
		String encryModules = TeeSysProps.getProps().getProperty("ENCRY_MODULES");//加密模块
		
		String encryFiles = TeeSysProps.getProps().getProperty("ENCRY_FILES");//加密文件类型
		
		String encryType = TeeSysProps.getProps().getProperty("ENCRY_TYPE");//加密文件类型
		
		String encrySize = TeeSysProps.getProps().getProperty("ENCRY_SIZE");//加密文件大小
		InputStream ins = null;
		int count=0;
		if(null!=attachEncry && attachEncry.equals("1") && null!=encryAlgo && !TeeUtility.isNullorEmpty(encryAlgo)){
			if(encryFiles.indexOf(fileExt)!=-1 && encryModules.indexOf(model)!=-1){
				TeeFileEncryption encry = new TeeFileEncryption(encryAlgo);
				byte[] data = null;
				if(!TeeUtility.isNullorEmpty(encryType) && encryType.equals("1")){//部分加密
					count=512;
					if(fileSize<512){
						count = inputStream.available();
					}
					int readCount = 0; // 已经成功读取的字节的个数
					data = new byte[count];
					while (readCount < count) {
						readCount += inputStream.read(data, readCount, count - readCount);
					}
				}else{//全部加密
					if(encrySize.equals("0")){
						while (count == 0) {
							count = inputStream.available();
						}
					}else{
						if(fileSize<Long.parseLong(encrySize)*1024){
							count = inputStream.available();
						}else{
							
							count = Integer.parseInt(encrySize)*1024;
						}
					}
					data = new byte[count];
					inputStream.read(data);
				}
				ins = encry.encrypt(data);
				attach.setEncryAlgo(encryAlgo);
				try {
					TeeUploadHelper.saveFile(inputStream,ins,count, tmp);
				} catch (Exception e) {
					System.out.println("文件上传错误" + tmp);
					return null;
				}
			}else{
				try {
					TeeUploadHelper.saveFile(inputStream,tmp);
				} catch (Exception e) {
					System.out.println("拷贝文件出错！文件:" + tmp);
					return null;
				}
			}
		}else{
			try {
				TeeUploadHelper.saveFile(inputStream,tmp);
			} catch (Exception e) {
				System.out.println("拷贝文件出错！文件:" + tmp);
				return null;
			}
		}
		/**
		 * 保存附件到数据库
		 */
		attach.setMd5(TeeFileUtility.computeFileMd5(tmp));
		attachmentService.addAttachment(attach);
		return attach;
	}

	public TeeAttachmentService getAttachmentService() {
		return attachmentService;
	}

	public void setAttachmentService(TeeAttachmentService attachmentService)

	{
		this.attachmentService = attachmentService;
	}

	public TeePersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}

}
