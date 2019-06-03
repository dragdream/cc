package com.beidasoft.xzfy.caseFile.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.beidasoft.xzfy.caseFile.bean.CaseFile;
import com.beidasoft.xzfy.caseFile.dao.CaseFileDao;
import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.global.TeeSysProps;

/**   
 * Description:材料信息service
 * @title CaseFileService.java
 * @package com.beidasoft.xzfy.caseFile.service 
 * @author zhangchengkun
 * @version 0.1 2019年5月7日
 */
@Service("caseFileService")
public class CaseFileService extends TeeBaseService{
	@Autowired
	CaseFileDao caseFileDao;

	/**
	 * Description:文件上传
	 * @author zhangchengkun
	 * @version 0.1 2019年5月7日
	 * @param wjxxBean
	 * @param file
	 * @return
	 * @throws Exception  String
	 */
	@SuppressWarnings("null")
	public List<CaseFile> wjsc(HttpServletRequest request,List<MultipartFile> listFile) throws Exception{
		List<CaseFile> list = new ArrayList<CaseFile>();
		CaseFile caseFile = null;
		for(MultipartFile file : listFile) {
			if(file!=null) {
				caseFile = uploadFileAndSaveInfo(request,file);
				list.add(caseFile);
			}
		}
		return list;
	}
	
	public CaseFile uploadFileAndSaveInfo(HttpServletRequest request,MultipartFile file) throws Exception{
		String uuid = StringUtils.EMPTY;
		//文件上传至服务器
		CaseFile wjxxBean = null;
		if(file!=null){
			wjxxBean = new CaseFile();
			TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			// 文件服务器上路径的取得
			String filePath = TeeSysProps.getProps().getProperty("FY_MATERIAL_PATH");
			// 写入文件返回文件大小KB
			filePath = writeFile(file,filePath);
			int size = (int)file.getSize();
			//文件信息入库
			if(size>0) {
				wjxxBean.setSize(size);
			}
			//唯一标识
			uuid = StringUtils.getUUId();
			wjxxBean.setId(uuid);
			// 案件ID
			String id = request.getParameter("id");
			if(!StringUtils.isEmptyOrBlank(id)) {
				wjxxBean.setCaseId(id);
			}
			// 文件类型:申请书、被申请人答复书等
			String fileType = request.getParameter("fileType");
			if(!StringUtils.isEmptyOrBlank(fileType)) {
				wjxxBean.setFileType(fileType);
			}
			// 文件类型code
			String fileTypeCode = request.getParameter("fileTypeCode");
			if(!StringUtils.isEmptyOrBlank(fileTypeCode)) {
				wjxxBean.setFileTypeCode(fileTypeCode);
			}
			// 保存的文件名的处理
			String fileName = file.getOriginalFilename();
			if(!StringUtils.isEmptyOrBlank(fileName)) {
				wjxxBean.setFileName(fileName);
			}
			//文件页数
			String page = request.getParameter("pageNum");
			if(!StringUtils.isEmptyOrBlank(page)) {
				int pageNum = Integer.parseInt(page);
				wjxxBean.setPageNum(pageNum);
			}
			//份数
			String copy = request.getParameter("copyNum");
			if(!StringUtils.isEmptyOrBlank(page)) {
				int copyNum = Integer.parseInt(copy);
				wjxxBean.setCopyNum(copyNum);
			}
			//存储路径
			if(!StringUtils.isEmptyOrBlank(filePath)) {
				wjxxBean.setStoragePath(filePath);
			}
			//产生材料所在环节
			String link = request.getParameter("link");
			if(!StringUtils.isEmptyOrBlank(link)) {
				wjxxBean.setLink(link);
			}
			//7个共通信息
			StringUtils.setAddInfo(wjxxBean,loginUser);
			//不再直接保存,将对象以list形式给到前端
			//caseFileDao.save(wjxxBean);
		}
		return wjxxBean;
	}
	
	public String writeFile(MultipartFile file,String filePath) throws IOException {
		File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 文件后缀
		String fileExt = TeeFileUtility.getFileExtName(file.getOriginalFilename());
		filePath +=StringUtils.getUUId()+"."+fileExt;
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
	
	
	/**
	 * Description:根据文件Id删除文件
	 * @author zhangchengkun
	 * @version 0.1 2019年5月7日
	 * @param fileId  void
	 */
	public void deleteById(String fileId){
		CaseFile file = caseFileDao.get(fileId);
		file.setIsDelete(1);
		caseFileDao.update(file);
	}
}
