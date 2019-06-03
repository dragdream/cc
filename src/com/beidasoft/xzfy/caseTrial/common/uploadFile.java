package com.beidasoft.xzfy.caseTrial.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;

import net.sf.json.JSONArray;

public class uploadFile {
	
	public static void copyFile(int runId,String atthment,TeeAttachmentService attachmentService,TeePerson person,TeeFlowRunPrcs flowRunPrcs){
	//文件属性
	String filename=StringUtils.EMPTY;
	String filepath=StringUtils.EMPTY;
	String attachment_id=StringUtils.EMPTY;
	List<Map> lmap = null;
	//格式化字符串
	JSONArray jsonArrays = JSONArray.fromObject(atthment);
	lmap = jsonArrays.toList(jsonArrays, Map.class);
	//定义流程类型
	//String prefilePath = TeeSysProps.getProps().getProperty("NASPATH");//文件路径
	for(Map att:lmap){
		filename =(String) att.get("filename");
		filepath = (String) att.get("filepath");
		attachment_id = (String) att.get("attachment_id");
		filepath =  filepath.replace("[*]", "\\");
		//TODO 该步骤为从原业务系统将附件拷贝到OA系统路径下 暂时屏蔽
		//boolean b = attachmentService.copyfileLoca(runId,attachment_id,flowRunPrcs.getSid());
		//if(!b){
			InputStream input = null;
			try {
				//input = new FileInputStream(prefilePath+filepath);
				input = new FileInputStream(filepath);
				TeeAttachment createFile = attachmentService.createFile(filename, input, "workFlow", person);
				createFile.setModelId(runId+"");
				createFile.setUser(person);
				attachmentService.updateAttachment(createFile);
				input.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	 //}
}
 
}
