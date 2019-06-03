package com.tianee.oa.core.base.fileNetdisk.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.model.TeeFileNetdiskModel;
import com.tianee.oa.core.base.fileNetdisk.service.TeeFileNetdiskService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.system.attachCenter.service.TeeAttachCenterService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("teeFileInterfaceController")
public class TeeFileInterfaceController {
	@Autowired
	private TeeFileNetdiskService fileNetdiskService;//文件
	
	
	@Autowired
	private TeeAttachmentService attachService;//附件
	
	
	
	@Autowired
	private TeePersonService personService;//用户
	
	
	/**
	 * 新建文件网盘
	 * 
	 * @date 2014-1-4
	 * @author
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/addOrUpdateFileNetdisk")
	@ResponseBody
	public JSONObject addOrUpdateFileNetdisk(String params,HttpServletRequest request) throws IOException {
		//对传过来的json数据进行处理	
		String json=request.getParameter("json");
		JSONArray jsonArray=JSONArray.fromObject(json);
			for(int i=0;i<jsonArray.size();i++){
				JSONObject jsonObject2=jsonArray.getJSONObject(i);
				saveFileFolder(jsonObject2,null);
				saveAttachFolder(jsonObject2,null,null,null);
			}
		
		String result ="{\"status\":\"添加成功\"}";
		return JSONObject.fromObject(result);
		
	}
	
	private void saveFileFolder(JSONObject jsonObject,TeeFileNetdisk parentDisk){
		JSONArray childs = new JSONArray();
		if(jsonObject.has("child")){
			childs = jsonObject.getJSONArray("child");//獲取子文件夾數組集合
		}
	
		TeeFileNetdisk fileNetdisks = new TeeFileNetdisk();
		fileNetdisks.setFileName(jsonObject.getString("fileName"));
		//fileNetdisks.setCreateTime(TeeDateUtil.parseDate(jsonObject.getString("createTime")));
		fileNetdisks.setCreateTime(new Date());
		fileNetdisks.setFiletype(0);
		fileNetdisks.setFileNetdiskType(0);
		fileNetdiskService.save(fileNetdisks);
		
		
		if(parentDisk==null){//如果父文件夹不存在，说明该jsonobject是一级节点
			fileNetdisks.setFileFullPath("/"+fileNetdisks.getSid()+"/");
		}else{
			fileNetdisks.setFileParent(fileNetdiskService.getFileNetdiskObjById(parentDisk.getSid()));//获取sid添加到一级文件一下的文件相关字段中，用来区分上下级的显示
			fileNetdisks.setFileFullPath(parentDisk.getFileFullPath()+fileNetdisks.getSid()+"/");
		}
		
		//更新文件夹数据
		fileNetdiskService.update(fileNetdisks);
		
		if(childs!=null && childs.size()!=0){//说明当前要新建的目录是有子目录的
			for(int i=0;i<childs.size();i++){
				saveFileFolder(childs.getJSONObject(i),fileNetdisks);
			}
		}
		
	}
	
	
	/**
	 * 附件的数据迁移
	 * @throws IOException 
	 */
	private void saveAttachFolder(JSONObject jsonObject,TeeFileNetdisk parentDisk,TeeAttachment parentattachments,TeePerson person) throws IOException{
		JSONArray fileInfos = new JSONArray();
		if(jsonObject.has("fileInfo")){
			fileInfos = jsonObject.getJSONArray("fileInfo");//獲取子文件夹數組集合
		}
		TeeFileNetdisk fileNetdisks = new TeeFileNetdisk();
		TeeAttachment attachment = new TeeAttachment();
		TeePerson defaulPerson = personService.selectByUuid(4);
		
		
		attachment.setFileName(fileInfos.getJSONObject(0).getString("attachName"));//附件名称
		attachment.setPath(fileInfos.getJSONObject(0).getString("path"));
		
		FileInputStream fis= null; 
		try {
			File f= new File(fileInfos.getJSONObject(0).getString("path")); //获取文件的大小
			fis= new FileInputStream(f);
			attachment.setSize(fis.available());//文件大小
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String nameSuffix1 = fileInfos.getJSONObject(0).getString("attachName");//文件后缀名
		
		String nameSuffix2=nameSuffix1.substring(nameSuffix1.lastIndexOf(".")+1);
		System.out.println(nameSuffix2);//txt
		attachment.setExt(nameSuffix2);
		
		
		String path = jsonObject.getString("filePath");
		String[] strArray = path.split("/");
		
		TeeFileNetdisk parentFile = null;
		for (int i = 1; i < strArray.length; i++) {
				String fileName=strArray[i];
				parentFile = fileNetdiskService.selectFileName(fileName,parentFile);
			
        }
		
		Calendar crTime1 = TeeDateUtil.parseCalendar(jsonObject.getString("createTime"));//附件创建时间
		attachment.setCreateTime(crTime1);
		attachment.setUser(defaulPerson);
		
		attachService.addAttachment(attachment);
		//attachCenterService.update(attachment);
		

		//fileNetdisks.setAttachemnt();
		fileNetdisks.setFileName(fileInfos.getJSONObject(0).getString("attachName"));//文件的名称
		fileNetdisks.setFileFullPath(jsonObject.getString("filePath"));
		fileNetdisks.setAttachemnt(attachment);
		fileNetdisks.setCreateTime(new Date());//文件的创建时间
		fileNetdisks.setFiletype(1);//文件类型， 0为文件夹 1为文件
		fileNetdisks.setFileNetdiskType(0);//网盘类型，默认为0公共网盘
		fileNetdisks.setCreater(defaulPerson);//创建人id
		
		
		if(parentDisk==null){//
			fileNetdisks.setFileParent(parentFile);
			fileNetdisks.setFileFullPath(parentFile.getFileFullPath()+fileNetdisks.getSid()+"/");
		}else{
			fileNetdisks.setFileParent(parentFile);//对象获取sid
			fileNetdisks.setFileFullPath(parentDisk.getFileFullPath()+fileNetdisks.getSid()+"/");
		}
		
		
		if(fileInfos!=null && fileInfos.size()!=0){//
			for(int i=0;i<fileInfos.size();i++){
				saveAttachFolder(fileInfos.getJSONObject(i),null,parentattachments,null);
			}
		}
		
	}

}
