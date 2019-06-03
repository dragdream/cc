package com.beidasoft.xzfy.caseTrial.template.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.beidasoft.xzfy.caseTrial.template.bean.Template;
import com.beidasoft.xzfy.caseTrial.template.service.TemplateService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.global.TeeSysProps;

@Controller
@RequestMapping("/templateController") 
public class TemplateController {
	@Autowired
	private TemplateService templateService; //模版service
	/**
	 * Description:保存
	 * @author ZCK
	 * @param request
	 * @param template
	 * @return
	 * @throws Exception
	 * TeeJson
	 */
	@RequestMapping("/saveTemplate")
	@ResponseBody
	public TeeJson save(HttpServletRequest request,Template template) throws Exception {
		MultipartHttpServletRequest mr = (MultipartHttpServletRequest) request;
		// 获得前台上传的 全部文件
		MultipartFile file = mr.getFile("file");
		//文件上传成功返回文件id
		return wjscTemplate(request, file);
	}
	/**
	 * Description:获取模板列表
	 * @author ZCK
	 * @param request
	 * @param gm
	 * @return
	 * @throws Exception
	 * TeeEasyuiDataGridJson
	 */
	@RequestMapping("/getAll")
	@ResponseBody
	public TeeEasyuiDataGridJson getAll(HttpServletRequest request,TeeDataGridModel gm) throws Exception {
		TeeEasyuiDataGridJson json = templateService.getAll(request, gm);
		return json;
	}
	
	/**
	 * Description:删除
	 * @author ZCK
	 * @param request
	 * void
	 */
	@RequestMapping("/del")
	@ResponseBody
	public void del(HttpServletRequest request) {
		String id = request.getParameter("id");
		templateService.del(id);
	}
	
	/**
	 * Description:文书上传
	 * @author zhangchengkun
	 * @version 0.1 2019年5月24日
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception  TeeJson
	 */
	public TeeJson wjscTemplate(HttpServletRequest request,MultipartFile file) throws Exception{
		TeeJson teeJson = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		String organId = StringUtils.EMPTY;
		//文书类型
		String documentNo = request.getParameter("documentNo");
		//文书类型名称
		String documentName = request.getParameter("documentName");//口头申请行政复议笔录
		//文件原名称
		String mbmc = file.getOriginalFilename();
		//模板类型(共性/个性)CODE
		String typeCode = request.getParameter("typesOfCode");//01:共性   02:个性
		//模板类型(共性/个性)
		String type = request.getParameter("typesOf");
		//个性模板就要获取机构id
		if("02".equals(typeCode)) {
			organId= request.getParameter("fyOrganId");
		}
		//根据不满名称和模板类型code查询是否已经上传过该模板
		List<Template> list = templateService.getByMcAndCodeAndOrganId(documentName,typeCode,organId);
		if(list.size()>0&&list.get(0)!=null) {
			teeJson.setRtState(false);
			teeJson.setRtMsg("该类型文件已上传,请勿多次上传!");
		}else {
			Template template = new Template();
			//机构Id
			template.setFyOrganId(organId);
			//文件真实名称
			template.setMbmc(mbmc);
			//文书编号(01 申请书 02 被申请人答复书...等类型)
			template.setDocumentNo(documentNo);
			//文书标准名称
			template.setDocumentName(documentName);
			//类型代码(01 个性 02 共性)
			template.setTypesOfCode(typeCode);
			//个性模板还是共性模板
			template.setTypesOf(type);
			//创建时间
			template.setCreatedTime(com.beidasoft.xzfy.utils.StringUtils.getCurrentTime());
			//创建人
			template.setCreatedUser(person.getUserName());
			//创建人id
			template.setCreatedUserId(person.getUserId());
			//删除标志
			template.setIsdelete("0");
			// 文件服务器上路径的取得  D:\\xzfy\\
			String nasPath = TeeSysProps.getProps().getProperty("FY_LEGALDOC_PATH");
			// 获取文件存储全路径
			try {
				String filePath = writeFile(file,nasPath);
				//完善对象
				template.setStoragePath(filePath);
				//信息入库
				templateService.saveTemplate(template);
				teeJson.setRtState(true);
			} catch (Exception e) {
				teeJson.setRtState(false);
				teeJson.setRtMsg("文件上传失败,请联系管理员重新上传!");
			}
		}
		return teeJson;
	}
	
	public String writeFile(MultipartFile file,String filePath) throws IOException {
		File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 文件后缀
		String fileExt = TeeFileUtility.getFileExtName(file.getOriginalFilename());
		filePath +=com.beidasoft.xzfy.utils.StringUtils.getUUId()+"."+fileExt;
		InputStream in = file.getInputStream();
		OutputStream out = new FileOutputStream(filePath);
		byte[] bytes = new byte[2048];
		//接受读取的内容(n就代表的相关数据，只不过是数字的形式)
        int n = -1;
        //循环取出数据
        while ((n = in.read(bytes,0,bytes.length)) != -1) {
            //转换成字符串
            //String str = new String(bytes,0,n,"GBK"); //这里可以实现字节到字符串的转换，比较实用
            //写入相关文件
            out.write(bytes, 0, n);
        }
        //关闭流
        in.close();
        out.close();
        //返回文件大小
        return filePath;
	}
	
	@RequestMapping("downFile")
	@ResponseBody
	public void downFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String fileurl = request.getParameter("fileurl");
    	String fileName = request.getParameter("fileName");
    	File file = null;
    	try {
	    	file = new File(fileurl);
	    	InputStream in;
	    	in = new FileInputStream(file);
	        OutputStream out;
	    	response.setHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode(fileName, "UTF-8"));
	    	out = response.getOutputStream(); 
	        //写文件  
	    	byte[] b = new byte[1024];
	        int len = 0;
	        while ((len = in.read(b)) != -1) {
	            out.write(b, 0, len);
	            out.flush();
	        }
	        in.close();  
	        in = null;
	        out.close(); 
	        out = null;
    	} catch (Exception e) {
			e.printStackTrace();
		} 
    }
}
