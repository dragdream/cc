package com.tianee.oa.core.attachment.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.bean.TeeAttachmentSpace;
import com.tianee.oa.core.attachment.bean.TeeBaseStream;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.util.TeeAttachSpaceGenerator;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.file.TeeUploadHelper;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.EncodingDetect;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
/**
 * 附件增删改查
 * @author zhp
 * @createTime 2013-10-4
 * @desc
 */
@Service
public class TeeAttachmentService extends TeeBaseService{

	@Autowired
	private TeeAttachmentDao attachmentDao;

	public TeeAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}

	public void setAttachmentDao(TeeAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}
	
	@Autowired
	private TeeBaseDownloadService baseDownloadService;
	
	/**
	 * 添加附件
	 * @author zhp
	 * @createTime 2013-10-6
	 * @editTime 下午01:46:53
	 * @desc
	 */
	public void addAttachment(TeeAttachment attach){
		attachmentDao.save(attach);
	}
	
	public void updateAttachment(TeeAttachment attach){ 
		attachmentDao.update(attach);
	}
	
	/**
	 * 克隆附件
	 */
	public TeeAttachment clone(TeeAttachment attach,String model,TeePerson operator){
		TeeAttachment attachment = new TeeAttachment();
		String originFilePath = attach.getAttachSpace().getSpacePath() + File.separator +attach.getModel()+ File.separator + attach.getAttachmentPath()+File.separator+attach.getAttachmentName();
		BeanUtils.copyProperties(attach, attachment);
		attachment.setSid(0);
		attachment.setModel(model);
		attachment.setUser(operator);
		attachment.setCreateTime(Calendar.getInstance());
		attachment.setModelId(null);
		
		//通过默认运算获取附件指定附件空间
		TeeAttachSpaceGenerator attachSpaceGenerator = TeeAttachSpaceGenerator.getInstance(simpleDaoSupport);
		//附件空间
		TeeAttachmentSpace space = attachSpaceGenerator.generate();
		
		attachment.setAttachSpace(space);
		
		/**
		 * 修改后的文件名
		 */
		String fileName2 = UUID.randomUUID().toString();
		fileName2 = fileName2.replaceAll("-", "");
		fileName2 = fileName2 + "." + attach.getExt();// 这是修改后的名字
		attachment.setAttachmentName(fileName2);
		
		File f1 = new File(originFilePath);
		if (!f1.exists()) {
			f1.mkdir();
		}
		Calendar cld = Calendar.getInstance();
		int year = cld.get(Calendar.YEAR) % 100;
		int month = cld.get(Calendar.MONTH) + 1;
		String mon = month >= 10 ? month + "" : "0" + month;
		String hard = year + mon;

		File f2 = new File(space.getSpacePath()+File.separator+ model + File.separator + hard);
		if (!f2.exists()) {
			f2.mkdir();
		}
		String tmp = space.getSpacePath()+File.separator+ model + File.separator + hard+File.separator+fileName2;
		
		try {
			TeeUploadHelper.saveFile(new FileInputStream(f1), tmp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		attachment.setAttachmentPath("/"+hard+"/");
		
		simpleDaoSupport.save(attachment);
		return attachment;
	}
	
	/**
	 * 创建文件
	 * @param attach
	 * @param model
	 * @param operator
	 * @return
	 */
	public TeeAttachment createFile(String fileName,InputStream input,String model,TeePerson operator){
		if (TeeUtility.isNullorEmpty(model)) {
			model = TeeSysProps.getDefaultAttachModelPath();
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
		 * 修改后的文件名
		 */
		String fileName2 = UUID.randomUUID().toString();
		fileName2 = fileName2.replaceAll("-", "");
		fileName2 = fileName2 + "." + fileName.split("\\.")[1];// 这是修改后的名字

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
		attach.setExt(fileName.split("\\.")[1]);
		attach.setAttachSpace(space);
		attach.setFileName(fileName);
		attach.setAttachmentName(fileName2);
		attach.setModel(model);
		attach.setPriority(0);
		attach.setUser(operator);

		FileOutputStream output = null;
		try {
			output = new FileOutputStream(tmp);
			byte b[] = new byte[1024];
			int length = 0;
			while((length = input.read(b))!=-1){
				output.write(b, 0, length);
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}finally{
			if(output!=null){
				try {
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		/**
		 * 保存附件到数据库
		 */
		addAttachment(attach);
		return attach;
	}
	
	/**
	 * 查询附件
	 * @author zhp
	 * @createTime 2013-10-6
	 * @editTime 下午01:56:59
	 * @desc
	 */
	public List<TeeAttachment> getAttachmentsByIds(String attachIds){
		return attachmentDao.getAttachmentsByIds(attachIds);
	}
	
	/**
	 * 获取附件
	 * @param model
	 * @param modelId
	 * @return
	 */
	public List<TeeAttachment> getAttaches(String model,String modelId){
		return attachmentDao.getAttaches(model, modelId);
	}
	
	public long getAttachesCount(String model,String modelId){
		return attachmentDao.getAttachesCount(model, modelId);
	}
	
	public List<TeeAttachmentModel> getAttacheModels(String model,String modelId){
		List<TeeAttachment> list = getAttaches(model,modelId);
		List<TeeAttachmentModel> list0 = new ArrayList<TeeAttachmentModel>();
		for(TeeAttachment attach:list){
			TeeAttachmentModel modelObj = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, modelObj);
			modelObj.setCreateTimeDesc(TeeDateUtil.format(attach.getCreateTime()));
			modelObj.setSizeDesc(TeeFileUtility.getFileSizeDesc(attach.getSize()));
			if(attach.getUser()!=null){
				modelObj.setUserId(attach.getUser().getUuid()+"");
				modelObj.setUserName(attach.getUser().getUserName());
			}else{
				modelObj.setUserId("");
				modelObj.setUserName("");
			}
			
			list0.add(modelObj);
		}
		return list0;
	}
	
	/**
	 * 查询附件，返回附件模型
	 * @param attachIds
	 * @return
	 */
	public List<TeeAttachmentModel> getAttachmentModelsByIds(String attachIds){
		List<TeeAttachmentModel> attachModels = new ArrayList<TeeAttachmentModel>();
		List<TeeAttachment> list = attachmentDao.getAttachmentsByIds(attachIds);
		for(TeeAttachment attach:list){
			TeeAttachmentModel model = new TeeAttachmentModel();
			entityToModel(attach, model);
			attachModels.add(model);
		}
		return attachModels;
	}
	
	public void deleteAttach(int attachId){
		TeeAttachment attach = (TeeAttachment) simpleDaoSupport.get(TeeAttachment.class, attachId);
		deleteAttach(attach);
	}
	
	public void deleteAttach(TeeAttachment attach){
		if(attach==null){
			return ;
		}
		String filePath = attach.getAttachSpace().getSpacePath()+File.separator+attach.getModel()+attach.getAttachmentPath();
	    File file = new File(filePath+attach.getAttachmentName());
	    file.delete();
		attachmentDao.deleteByObj(attach);
	}
	
	public TeeAttachment getById(int attachId){
		return attachmentDao.get(attachId);
	}
	
	public String getFileNameById(int attachId){
		return (String)simpleDaoSupport.unique("select fileName from TeeAttachment where sid="+attachId,null );
	}
	
	/**
	 * 根据id获取model
	 * @param attachId
	 * @return
	 */
	public TeeAttachmentModel getModelById(int attachId){
		TeeAttachmentModel model = new TeeAttachmentModel();
		TeeAttachment attach = attachmentDao.get(attachId);
		if(attach==null){
			return null;
		}
		entityToModel(attach, model);
		return model;
	}
	
	public void entityToModel(TeeAttachment attach,TeeAttachmentModel model){
		BeanUtils.copyProperties(attach, model);
		model.setUserId(attach.getUser().getUuid()+"");
		model.setCreateTimeDesc(TeeDateUtil.format(attach.getCreateTime()));
		model.setSizeDesc(TeeFileUtility.getFileSizeDesc(attach.getSize()));
	}
	
	/**
	 * 获取限制信息
	 * {emailUse:xxx,emailRemain:xx,emailMax:xx}
	 * {fileDiskUse:xx,fileDiskRemain:xxx,fileDiskMax:xx}
	 * @param loginUser
	 * @return
	 */
	public Map getLimitedSpaceInfo(String model,TeePerson loginUser){
		Map data = new HashMap();
		long max = 0;
		loginUser = (TeePerson) simpleDaoSupport.get(TeePerson.class, loginUser.getUuid());
		if(model.equals(TeeAttachmentModelKeys.EMAIL) && loginUser.getEmailCapacity()!=null){
			max = loginUser.getEmailCapacity()*1024*1024;
		}else if(model.equals(TeeAttachmentModelKeys.FILE_NET_DISK_PERSON) && loginUser.getFolderCapacity()!=null){
			max = loginUser.getFolderCapacity()*1024*1024;
		}
		
		max=max==0?Long.MAX_VALUE:max;
		long used = simpleDaoSupport.count("select sum(atta.size) from TeeAttachment atta where atta.user.uuid="+loginUser.getUuid()+" and atta.model='"+model+"'", null);
		long remain = max-used;
		data.put("max", max);
		data.put("used", used);
		data.put("remain", remain);
		data.put("maxDesc", TeeFileUtility.getFileSizeDesc(max));
		data.put("usedDesc", TeeFileUtility.getFileSizeDesc(used));
		data.put("remainDesc", TeeFileUtility.getFileSizeDesc(remain));
		return data;
	}
	
	/**
	 * 校验存储空间信息
	 * @return
	 */
	public boolean isValidSpaceInfo(String model,long fileSize,TeePerson loginUser){
		//已用空间
		long use = simpleDaoSupport.count("select sum(atta.size) from TeeAttachment atta where atta.user.uuid="+loginUser.getUuid()+" and atta.model='"+model+"'", null);
		//最大空间
		long max = 0;
		if(TeeAttachmentModelKeys.EMAIL.equals(model)){
			if(loginUser.getEmailCapacity() != null  && loginUser.getEmailCapacity() != 0){
				max = loginUser.getEmailCapacity();
			}
			
		}else if(TeeAttachmentModelKeys.FILE_NET_DISK_PERSON.equals(model)){
			if(loginUser.getFolderCapacity() != null  && loginUser.getFolderCapacity() != 0){
				max = loginUser.getFolderCapacity();
			}
		}else{
			//max=1024*10;
			max=0;
		}
		max = max*1024*1024;
		if(max == 0){//为0 不做控制
			return true;
		}
		//可用空间
		long remain = max-use;
		if(fileSize>remain){
			return false;
		}
		return true;
	}

	
	
	/**
	 * 文本预览
	 * @param request
	 * @param response
	 * @throws FileNotFoundException 
	 */
	public void textPreview(HttpServletRequest request,
			HttpServletResponse response){
		String ext=TeeStringUtil.getString(request.getParameter("ext"));
		int attachmentId=TeeStringUtil.getInteger(request.getParameter("attachmentId"), 0);
		TeeBaseStream baseStream = null;
		InputStream is = null;
        
		try {
			baseStream = baseDownloadService.getTeeBaseStream(attachmentId);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		is = baseStream.getFileInputStream();
		
		String encoding = EncodingDetect.getJavaEncode(baseStream.getFilePath());
		
		if("UTF-8".equals(encoding)){
			response.setCharacterEncoding("UTF-8");
			if("txt".equals(ext)){//txt文件
				response.setContentType("text/plain;charset=utf-8");  
			}else{
				response.setContentType("text/html;charset=utf-8"); 
			}
		}else{
			response.setCharacterEncoding("GBK");
			if("txt".equals(ext)){//txt文件
				response.setContentType("text/plain;charset=gbk");  
			}else{
				response.setContentType("text/html;charset=gbk"); 
			}
		}
		
		try{
			OutputStream ops = response.getOutputStream();
			ops.flush();
			if (is != null) {
				byte[] buff = new byte[1024*50];
				int byteread = 0;
				while ((byteread = is.read(buff)) != -1) {
					ops.write(buff, 0, byteread);
					ops.flush();
				}
			}
			ops.close();
		}catch(Exception ex){
			
		}finally{
			try {
				is.close();
				baseStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * 根据pdf附件的主键获取pdf文件的页数
	 * @param attachId
	 * @return
	 */
	public int getPDFPageNumById(int attachId) {
		// 根据主键获取附件对象
		TeeAttachment att = (TeeAttachment) simpleDaoSupport.get(TeeAttachment.class, attachId);
		int pageNum = 0;
		if (att != null) {
			PDDocument pdfReader = null;
			try {
				pdfReader = PDDocument.load(att.getFilePath());
				pageNum = pdfReader.getNumberOfPages();
			} catch (IOException e) {
				e.printStackTrace();
				pageNum = -1;
			}
		}
		return pageNum;
	}
	
}
