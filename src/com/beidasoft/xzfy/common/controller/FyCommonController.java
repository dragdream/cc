package com.beidasoft.xzfy.common.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.beidasoft.xzfy.base.controller.FyBaseController;
import com.beidasoft.xzfy.base.exception.ValidateException;
import com.beidasoft.xzfy.common.bean.DictionaryInfo;
import com.beidasoft.xzfy.common.bean.UploadInfo;
import com.beidasoft.xzfy.common.model.request.DictRequest;
import com.beidasoft.xzfy.common.model.request.UploadRequest;
import com.beidasoft.xzfy.common.service.FyCommonService;
import com.beidasoft.xzfy.utils.Const;
import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.global.TeeSysProps;

import common.Logger;
/**
 * 公共方法
 * @author fyj
 *
 */
@Controller
@RequestMapping("/xzfy/common")
public class FyCommonController extends FyBaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Logger log = Logger.getLogger(FyCommonController.class);
	
	@Autowired
	private FyCommonService comService;
	@Autowired
	private TeeAttachmentService attachmentService;
	
	/**
	 * 查询字典表类型(当类型为空，查询所有类型)
	 * @param req
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping("/getDict")
	@ResponseBody
	public TeeJson getDict(DictRequest req) throws ValidateException{
		
		log.info("[xzfy - CommonController - getDict] enter controller.");
		TeeJson json = new TeeJson();
		try{
			//参数校验
			req.validate();
			
			//获取列表
			List<DictionaryInfo> list = comService.getDictByType(req.getType());
			
			//设置返回参数
			json.setRtState(true);
			json.setRtMsg("请求成功");
			json.setRtData(list);
		}
		catch(ValidateException e)
		{
			log.info("[xzfy - CommonController - getDict] error=" + e);
			json.setRtState(false);
			json.setRtMsg("校验失败");
		}
		catch(Exception e){
			
			log.info("[xzfy - CommonController - getDict] error=" + e);
			json.setRtState(false);
			json.setRtMsg("请求失败");
		}
		finally{
			
			log.info("[xzfy - CommonController - getDict] controller end.");
		}
		return json;
	}

	/**
	 * 文件上传(单个)
	 * @param file
	 * @param req
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public TeeJson upload(@RequestParam("file") MultipartFile[] files,
			UploadRequest req) throws ValidateException{
		
		log.info("[xzfy - CommonController - upload] enter controller.");
		
		TeeJson json = new TeeJson();
		
		long startTime=System.currentTimeMillis();
		
		List<UploadInfo> list = new ArrayList<UploadInfo>();
		try{
			for(MultipartFile file : files){
				
				//上传名字
				String oldName = file.getOriginalFilename();
				//后缀
				String suff = oldName.substring(oldName.lastIndexOf("."));
				
				//从配置文件中获取数据固定方法
				String sysUrl = "";
				//文书
				if(Const.TYPE.ONE.equals(req.getType())){
					sysUrl = TeeSysProps.getProps().getProperty("FY_LEGALDOC_PATH");
				}
			    //材料
				else if(Const.TYPE.TWO.equals(req.getType())){
					sysUrl = TeeSysProps.getProps().getProperty("FY_MATERIAL_PATH");
				}
				
				//保存名字
		    	String saveName = "/" + StringUtils.getUUId() + suff;
		    	//保存时间
		    	String timeUrl = getTimeFile(req.getCaseId());
			    String path = sysUrl + timeUrl + saveName;
			    
			    File newFile = new File(path);
			    long size = 0;
			    if (newFile.exists() && newFile.isFile()){  
			        size = newFile.length();
			    }
		        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
		        file.transferTo(newFile);
		        
		        UploadInfo upload = new UploadInfo();
		        upload.setName(oldName);
		        upload.setUrl(path);
		        upload.setSize(size);
		        
		        list.add(upload);
			}
	        
			//设置返回参数
	        json.setRtState(true);
	        json.setRtMsg("请求成功");
			json.setRtData(list);
		}
		catch(ValidateException e)
		{
			log.info("[xzfy - CommonController - upload] error=" + e);
			json.setRtState(false);
	        json.setRtMsg("校验失败");
		}
		catch(Exception e){
			
			log.info("[xzfy - CommonController - upload] error=" + e);
			json.setRtState(false);
	        json.setRtMsg("请求失败");
		}
		finally{
			
			long endTime=System.currentTimeMillis();
			log.info("[xzfy - CommonController - upload] time=" +
			    String.valueOf(endTime-startTime)+"ms");
			log.info("[xzfy - CommonController - upload] controller end.");
		}
		return json;
	}
	
	
	/**
	 * 下载
	 * @param url
	 * @param response
	 * @throws ValidateException
	 */
	@RequestMapping("/download")
	@ResponseBody
    public void download(@RequestParam("url") String url,
    		HttpServletResponse response) 
		throws ValidateException{
			
		log.info("[xzfy - CommonController - download] enter controller.");

		long startTime=System.currentTimeMillis();
		
		InputStream in = null;
		OutputStream out = null;
		try{
			//文件的路径
	    	String path = url;
	    	
	    	//导出名称
	    	String fileName = url.substring(url.lastIndexOf("/")+1);
	    	//设置返回
	    	response.reset();        
	        response.setCharacterEncoding("UTF-8");
	        response.setContentType("application/octet-stream");
	        //设置content-disposition响应头控制浏览器以下载的形式打开文件
	        response.addHeader("Content-Disposition","attachment;filename=" 
	            + new String(fileName.getBytes("utf-8"),"iso-8859-1"));
	        
	        //获取文件输入流
	        in = new FileInputStream(path);
	        int len = 0;
	        byte[] buffer = new byte[1024];
	        out = response.getOutputStream();
	        while ((len = in.read(buffer)) > 0) {
	            //将缓冲区的数据输出到客户端浏览器
	            out.write(buffer,0,len);
	        }
			
		}
		catch(Exception e){
			
			log.info("[xzfy - CommonController - download] error=" + e);
		}
		finally{
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			long endTime=System.currentTimeMillis();
			log.info("[xzfy - CommonController - download] time=" +
			    String.valueOf(endTime-startTime)+"ms");
			log.info("[xzfy - CommonController - download] controller end.");
		}

	}
    
	/**
	 * 获取时间地址
	 * @param caseId
	 * @return
	 */
	public static String getTimeFile(String caseId){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String dateString = formatter.format(currentTime);
		return dateString+"/"+caseId;
	}
	/**
	 * Description:获取classpath
	 * @author zhangchengkun
	 * @version 0.1 2019年5月8日
	 * @return  String
	 */
	public static String getClassResources(){
		String path =  (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))).replaceAll("file:/", "").replaceAll("%20", " ").trim();	
		if(path.indexOf(":") != 1){
			path = File.separator + path;
		}
		return path;
	}
	
	 /**  
     * 复制文件或者目录,复制前后文件完全一样。  
     * @author zhangchengkun
     * @param resFilePath 源文件路径  
     * @param distFolder    目标文件夹  
	 * @throws IOException 
     * @IOException 当操作发生异常时抛出  
     */   
    public static void copyFile(String resFilePath, String distFolder) throws IOException {   
        File resFile = new File(resFilePath);   
        File distFile = new File(distFolder);   
        if (resFile.isDirectory()) {   
                FileUtils.copyDirectoryToDirectory(resFile, distFile);   
        } else if (resFile.isFile()) {   
                FileUtils.copyFileToDirectory(resFile, distFile, true);   
        }   
    } 
    
    /**
	 * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下
	 * @author zhangchengkun
	 * @param sourceFilePath
	 *            :待压缩的文件路径
	 * @param zipFilePath
	 *            :压缩后存放路径
	 * @param fileName
	 *            :压缩后文件的名称
	 * @return
	 */
	@SuppressWarnings("resource")
	public static TeeJson fileToZip(String sourceFilePath, String zipFilePath, String fileName) {
		TeeJson json = new TeeJson();
		boolean flag = false;
		File sourceFile = new File(sourceFilePath);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		int size = 0;
		if (sourceFile.exists() == false) {
			System.out.println("待压缩的文件目录：" + sourceFilePath + "不存在.");
		} else {
			try {
				File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
				size = (int)zipFile.length();
				if (zipFile.exists()) {
					System.out.println(zipFilePath + "目录下存在名字为:" + fileName + ".zip" + "打包文件.");
				} else {
					File[] sourceFiles = sourceFile.listFiles();
					if (null == sourceFiles || sourceFiles.length < 1) {
						System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
					} else {
						fos = new FileOutputStream(zipFile);
						zos = new ZipOutputStream(new BufferedOutputStream(fos));
						byte[] bufs = new byte[1024 * 10];
						for (int i = 0; i < sourceFiles.length; i++) {
							// 创建ZIP实体，并添加进压缩包
							ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
							zos.putNextEntry(zipEntry);
							// 读取待压缩的文件并写进压缩包里
							fis = new FileInputStream(sourceFiles[i]);
							bis = new BufferedInputStream(fis, 1024 * 10);
							int read = 0;
							while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
								zos.write(bufs, 0, read);
							}
						}
						flag = true;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				// 关闭流
				try {
					if (null != bis)
						bis.close();
					if (null != zos)
						zos.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
		json.setRtState(flag);
		json.setRtData(size);
		return json;
	}
	
	
	@RequestMapping("/selectPersonDept")
	@ResponseBody
	public TeeJson selectPersonDept(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		List<TeePersonModel> personList = new ArrayList<TeePersonModel>();
		
		// 获取当前登录人信息
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int deptId = person.getDept().getUuid();
		
		List<TeePerson> allPerson = comService.getAllPerson(deptId);
		for (TeePerson teePerson : allPerson) {
			TeePersonModel model = new TeePersonModel();
			model.setUserName(teePerson.getUserName());
			model.setUuid(teePerson.getUuid());
			personList.add(model);
		}
		json.setRtState(true);
		json.setRtData(personList);
		return json;
	}
	
	/**
	 * Description:通过model和modelid获取文件信息
	 * @author zhangchengkun
	 * @version 0.1 2019年6月2日
	 * model 文件表中模块标识
	 * modelId 复议中存储文件对应数据id标识位
	 * @return  TeeJson
	 */
	@RequestMapping("/getAttachByModelAndModelId")
	@ResponseBody
	public TeeJson getAttachByModelAndModelId(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String modelId  = request.getParameter("id");
		String model  = request.getParameter("model");
		//文件model
		List<TeeAttachmentModel> attachModels = new ArrayList<TeeAttachmentModel>();
		List<TeeAttachment> attaches = null;
		//获取文件表中文件ids(利用modelid即调查管理id)
		try {
			attaches = attachmentService.getAttaches(model, modelId);
			for(TeeAttachment attach:attaches){
				TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
				entityToModel(attach, attachmentModel);
				attachModels.add(attachmentModel);
			}
			json.setRtState(true);
			json.setRtData(attachModels);
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("未获取到文件信息,请联系管理员!");
		}
		return json;
	}
	public void entityToModel(TeeAttachment attach,TeeAttachmentModel model){
		BeanUtils.copyProperties(attach, model);
		model.setUserId(attach.getUser().getUuid()+"");
		model.setCreateTimeDesc(TeeDateUtil.format(attach.getCreateTime()));
		model.setSizeDesc(TeeFileUtility.getFileSizeDesc(attach.getSize()));
	}
	
	
	public static void main(String[] args){
		String oldName = "1.txt";
		String suff = oldName.substring(oldName.lastIndexOf("."));
		System.out.println(suff);
	}
}
