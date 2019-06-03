package com.tianee.oa.core.attachment.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.bean.TeeAttachmentSpace;
import com.tianee.oa.core.attachment.bean.TeeBaseStream;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseDownloadService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.attachment.service.TeePicThumbnailsService;
import com.tianee.oa.core.attachment.util.TeeAttachSpaceGenerator;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

import org.apache.commons.codec.binary.Base64;
/**
 * 附件操作 主要有 新建附件 上传附件等
 * 适合 不和表单同时提交的异步 附件上传
 * @author zhp
 * @createTime 2013-10-13
 * @desc
 */
@Controller
@RequestMapping("/attachmentController")
public class TeeAttachmentController {

	@Autowired
	private TeeBaseUpload baseUpload;
	
	@Autowired
	private TeeBaseDownloadService baseDownloadService;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private TeePicThumbnailsService picThumbnailsService;
	
	@RequestMapping("/commonUpload")
	@ResponseBody
	public TeeJson commonUpload(HttpServletRequest request) throws IOException{
		TeeJson json = new TeeJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String model = TeeStringUtil.getString(multipartRequest.getParameter("model"));
		
		List<TeeAttachmentModel> list = new ArrayList<TeeAttachmentModel>();
		List<TeeAttachment> attachs = baseUpload.manyAttachUpload(multipartRequest, model);
		
		for(TeeAttachment attach:attachs){
			TeeAttachmentModel am = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, am);
			am.setCreateTimeDesc(TeeDateUtil.format(attach.getCreateTime()));
			am.setSizeDesc(TeeFileUtility.getFileSizeDesc(attach.getSize()));
			
			//新上传的附件设置为所有权限
			am.setPriv(1|2|4|8|16|32|64|128);
			list.add(am);
		}
		
		json.setRtMsg("上传成功");
		json.setRtState(true);
		json.setRtData(list);
		
		return json;
	}
	
	@RequestMapping("/uploadChunk")
	@ResponseBody
	public TeeJson uploadChunk(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		String fileMd5 = request.getParameter("fileMd5");
		String chunk = request.getParameter("chunk");
	
		List<MultipartFile> files = multipartRequest.getFiles("file");
		
		//通过默认运算获取附件指定附件空间
		TeeAttachSpaceGenerator attachSpaceGenerator = TeeAttachSpaceGenerator.getInstance(attachmentService.getSimpleDaoSupport());
		//附件空间
		TeeAttachmentSpace space = attachSpaceGenerator.generate();
		
		/**
		 * 模块目录
		 */
		String filePath = space.getSpacePath() + File.separator + "chunk_upload";
		
		for(MultipartFile f:files){
			File file = new File(filePath+"/"+fileMd5);
            if(!file.exists()){  
                file.mkdir();  
            }  
            File chunkFile = new File(filePath+"/"+fileMd5+"/"+chunk);  
//            FileUtils.copyInputStreamToFile(f.getInputStream(), chunkFile); 
		}
		
		
		return json;
	}
	
	@RequestMapping("/checkChunk")
	@ResponseBody
	public TeeJson checkChunk(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		String fileMd5 = request.getParameter("fileMd5");
		String chunk = request.getParameter("chunk");
		String chunkSize = request.getParameter("chunkSize");
		
		//通过默认运算获取附件指定附件空间
		TeeAttachSpaceGenerator attachSpaceGenerator = TeeAttachSpaceGenerator.getInstance(attachmentService.getSimpleDaoSupport());
		//附件空间
		TeeAttachmentSpace space = attachSpaceGenerator.generate();
		
		/**
		 * 模块目录
		 */
		String filePath = space.getSpacePath() + File.separator + "chunk_upload";
		
		 File checkFile = new File(filePath+"/"+fileMd5+"/"+chunk);  
         
         //检查文件是否存在，且大小是否一致  
         if(checkFile.exists() && checkFile.length()==Integer.parseInt(chunkSize)){  
             //上传过  
             json.setRtState(true);
         }else{  
             //没有上传过  
             json.setRtState(false);
         }  
		
		return json;
	}
	
	@RequestMapping("/mergeChunks")
	@ResponseBody
	public TeeJson mergeChunks(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		//通过默认运算获取附件指定附件空间
		TeeAttachSpaceGenerator attachSpaceGenerator = TeeAttachSpaceGenerator.getInstance(attachmentService.getSimpleDaoSupport());
		//附件空间
		TeeAttachmentSpace space = attachSpaceGenerator.generate();
		
		/**
		 * 模块目录
		 */
		String filePath = space.getSpacePath() + File.separator + "chunk_upload";
		
		//合并文件  
        //需要合并的文件的目录标记  
        String fileMd5 = request.getParameter("fileMd5");  
        String fileName = request.getParameter("fileName");  
          
        //读取目录里的所有文件  
        File f = new File(filePath+"/"+fileMd5);  
        File[] fileArray = f.listFiles(new FileFilter(){  
            //排除目录只要文件  
            @Override  
            public boolean accept(File pathname) {  
                // TODO Auto-generated method stub  
                if(pathname.isDirectory()){  
                    return false;  
                }  
                return true;  
            }  
        });  
          
        //转成集合，便于排序  
        List<File> fileList = new ArrayList<File>(Arrays.asList(fileArray));  
        Collections.sort(fileList,new Comparator<File>() {  
            @Override  
            public int compare(File o1, File o2) {  
                // TODO Auto-generated method stub  
                if(Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())){  
                    return -1;  
                }  
                return 1;  
            }  
        });
        
        
        String randomName = UUID.randomUUID().toString();
        File outputFile = new File(filePath+"/"+randomName);
        
        //创建文件  
        outputFile.createNewFile();  
        //输出流  
        FileChannel outChnnel = new FileOutputStream(outputFile).getChannel();
        //合并  
        FileChannel inChannel;  
        for(File file : fileList){  
            inChannel = new FileInputStream(file).getChannel();  
            inChannel.transferTo(0, inChannel.size(), outChnnel);  
            inChannel.close();  
            //删除分片  
            file.delete();  
        }  
        outChnnel.close();  
        
        //清除文件夹  
        File tempFile = new File(filePath+"/"+fileMd5);  
        if(tempFile.isDirectory() && tempFile.exists()){  
            tempFile.delete();  
        }
        
        String model = TeeStringUtil.getString(request.getParameter("model"),"tmp");
  		List<TeeAttachmentModel> list = new ArrayList<TeeAttachmentModel>();
  		TeeAttachment attach = baseUpload.singleAttachUpload(new FileInputStream(outputFile), outputFile.length(), fileName, "", model, loginUser);
  		
  		TeeAttachmentModel am = new TeeAttachmentModel();
  		BeanUtils.copyProperties(attach, am);
  		am.setCreateTimeDesc(TeeDateUtil.format(attach.getCreateTime()));
  		am.setSizeDesc(TeeFileUtility.getFileSizeDesc(attach.getSize()));
  		
  		//新上传的附件设置为所有权限
  		am.setPriv(1|2|4|8|16|32|64|128);
  		
  		outputFile.delete();
  		
  		json.setRtData(am);
		return json;
	}
	
	/**
	 * 下载文件
	 * 
	 * @author zhp
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downFile")
	public String downFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sAttachId = request.getParameter("id");
		int attachId = TeeStringUtil.getInteger(sAttachId, 0);
		OutputStream ops = null;
		InputStream is = null;
		TeeBaseStream baseStream = null;
		String fileName = null;
		try {
			
			fileName = attachmentService.getFileNameById(attachId);
			String contentTypeDesc = baseDownloadService.getContentType(fileName);
			if (contentTypeDesc != null) {
				response.setContentType(contentTypeDesc);
			} else {
				response.setContentType("application/octet-stream");
			}
			
			//是否为不可在线编辑的缓存文件，如果不是的话，则走客户端缓存
			boolean isEditable = baseDownloadService.isEditableFile(fileName);
			if(!isEditable){
				String IfNoneMatch = request.getHeader("If-None-Match");
				if(TeeUtility.isNullorEmpty(IfNoneMatch)){//如果请求中没有超时时限，则写入一个超时的实现
					response.setHeader("ETag", Long.toString(new Date().getTime()));
					response.setStatus(HttpServletResponse.SC_OK);
				}else{
					response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
					return null;
				}
			}
			
			
			baseStream = baseDownloadService.getTeeBaseStream(attachId);
			is = baseStream.getFileInputStream();
			fileName = baseStream.getFileName();
			fileName = fileName.replaceAll("\\+", "%20");
			response.setHeader("Cache-control", "private");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setHeader("Accept-Length", String.valueOf(baseStream
					.getFileSize()));
			response.setHeader("Content-Length", String.valueOf(baseStream
					.getFileSize()));
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ URLEncoder.encode(fileName, "UTF-8") + "\"");
			ops = response.getOutputStream();
			ops.flush();
			if (is != null) {
				byte[] buff = new byte[1024*50];
				int byteread = 0;
				while ((byteread = is.read(buff)) != -1) {
					ops.write(buff, 0, byteread);
					ops.flush();
				}
			}
		} catch (Exception ex) {
//			ex.printStackTrace();
//			throw ex;
		} finally {
			if(baseStream!=null){
				baseStream.close();
			}
		}
		return null;
	}
	
	
	/**
	 * 在线输出PDF内容
	 * 
	 * @author zhp
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downFile4Pdf")
	public String downFile4Pdf(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sAttachId = request.getParameter("id");
		int attachId = TeeStringUtil.getInteger(sAttachId, 0);
		OutputStream ops = null;
		InputStream is = null;
		TeeBaseStream baseStream = null;
		try {
			baseStream = baseDownloadService.getTeeBaseStream(attachId);
			is = baseStream.getFileInputStream();
			String fileName = baseStream.getFileName();
			fileName = fileName.replaceAll("\\+", "%20");
			response.setContentType("application/pdf");
			ops = response.getOutputStream();
			ops.flush();
			if (is != null) {
				byte[] buff = new byte[2048];
				int byteread = 0;
				while ((byteread = is.read(buff)) != -1) {
					ops.write(buff, 0, byteread);
					ops.flush();
				}
			}
		} catch (Exception ex) {
//			ex.printStackTrace();
//			throw ex;
		} finally {
			if(baseStream!=null){
				baseStream.close();
			}
		}
		return null;
	}

	@RequestMapping("/deleteFile")
	@ResponseBody
	public TeeJson deleteFile(HttpServletRequest request) throws IOException{
		TeeJson json = new TeeJson();
		String attachIds = TeeStringUtil.getString(request.getParameter("attachIds"));
		
		attachmentService.deleteAttach(Integer.parseInt(attachIds));
		
		json.setRtMsg("删除成功");
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 获取控件限制信息
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getLimitedSpaceInfo")
	@ResponseBody
	public TeeJson getLimitedSpaceInfo(HttpServletRequest request) throws IOException{
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String model = TeeStringUtil.getString(request.getParameter("model"));
		
		json.setRtData(attachmentService.getLimitedSpaceInfo(model,loginPerson));
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 查询附件，返回附件模型
	 * @param attachIds
	 * @return
	 */
	@RequestMapping("/getAttachmentModelsByIds")
	@ResponseBody
	public TeeJson getAttachmentModelsByIds(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String attachIds = TeeStringUtil.getString(request.getParameter("attachIds"));
		json.setRtData(attachmentService.getAttachmentModelsByIds(attachIds));
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getRemoteFolder")
	public TeeJson getRemoteFolder(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String path = TeeStringUtil.getString(request.getParameter("path"));
		path = URLDecoder.decode(path);
		String spPath[] = path.split("/");
		File file = new File(path);
		if(!file.exists() && !"".equals(path)){
			return json;
		}
		
		File files[] = null;
		if("".equals(path)){
			files = file.listRoots();
		}else{
			files = file.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File arg0) {
					// TODO Auto-generated method stub
					if(arg0.isDirectory()){
						return true;
					}
					return false;
				}
			});
		}
		
		
		
		List list = new ArrayList();
		for(File f:files){
			list.add(f.getAbsoluteFile().toString().replace("\\", "/"));
		}
		
		json.setRtData(list);
		
		return json;
	}
	
	
	/**
	 * 文本预览
	 * @param attachIds
	 * @return
	 */
	@RequestMapping("/textPreview")
	public void textPreview(HttpServletRequest request, HttpServletResponse response){	
		attachmentService.textPreview(request,response);
	}
	
	/**
	 * 图片缩略图
	 * */
	@ResponseBody
	@RequestMapping("/picZoom")
	public TeeJson picZoom(int attachId){
		TeeJson json = new TeeJson();
		TeeAttachment attachment = picThumbnailsService.picZoom(attachId);
		if(attachment!=null){
			json.setRtData(attachment.getSid());
		}
		return json;
	}
	
	/**
	 * 通过附件id获取附件信息
	 * @param attachIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAttachInfo")
	public TeeJson getAttachInfo(HttpServletRequest request, HttpServletResponse response){	
		String id = request.getParameter("id");
		TeeAttachment attachment = attachmentService.getById(Integer.parseInt(id));
		
		TeeJson json = new TeeJson();
		TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
		BeanUtils.copyProperties(attachment, attachmentModel);
		json.setRtData(attachmentModel);
		json.setRtState(true);
		
		return json;
	}
	
	
	
	/**
	 * 根据pdf附件的主键  获取pdf的页码
	 * @param attachId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPDFPageNumById")
	public TeeJson getPDFPageNumById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		//获取前台传来的pdf附件主键
		int attachId=TeeStringUtil.getInteger(request.getParameter("attachId"),0);
		int pageNum=attachmentService.getPDFPageNumById(attachId);
		if(pageNum>=0){
			json.setRtState(true);
			json.setRtData(pageNum);
		}else{
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * 图片base64
	 * @throws IOException 
	 * */
	@ResponseBody
	@RequestMapping("/getBase64ToInStream")
	public TeeJson getBase64ToInStream(HttpServletRequest request) throws IOException{
		TeeJson json=new TeeJson();
		TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String model = request.getParameter("model");
		String modelId = request.getParameter("modelId");
		String base64 = request.getParameter("base64");
		String realName = request.getParameter("realName");
		String disName = request.getParameter("disName");
		byte[] bs = Base64.decodeBase64(base64);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bs);		
		TeeAttachment attachment = baseUpload.singleAttachUpload(byteArrayInputStream, bs.length, realName, disName, model, modelId, person);
		List<TeeAttachmentModel> attaches=new ArrayList<TeeAttachmentModel>();
		if(attachment!=null){
			TeeAttachmentModel m=new TeeAttachmentModel();
			BeanUtils.copyProperties(attachment, m);
			attaches.add(m);
			json.setRtData(attaches);
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}
	
	public TeeBaseUpload getBaseUpload() {
		return baseUpload;
	}
	public void setBaseUpload(TeeBaseUpload baseUpload) {
		this.baseUpload = baseUpload;
	}

	public void setBaseDownloadService(TeeBaseDownloadService baseDownloadService) {
		this.baseDownloadService = baseDownloadService;
	}

	public TeeBaseDownloadService getBaseDownloadService() {
		return baseDownloadService;
	}
	
}
  