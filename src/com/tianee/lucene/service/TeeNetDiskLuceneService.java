package com.tianee.lucene.service;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.lucene.TeeLuceneSoapService;
import com.tianee.lucene.dao.TeeNetDiskLuceneDao;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.webframe.httpmodel.TeeJson;

@Service
public class TeeNetDiskLuceneService {

	@Autowired
	private TeeNetDiskLuceneDao dao;
	

	/**
	 * 将该目录下所有文件创建索引
	 * @param sid
	 * @return
	 */
	public TeeJson createNetDiskIndex(int sid){
		TeeJson json=new TeeJson();
		String hql=" from TeeFileNetdisk disk where disk.filetype=1 and disk.fileNetdiskType=0 and disk.fileFullPath like ?";
		String param="/"+sid+"/%";
		List<TeeFileNetdisk> diskList=dao.find(hql, param);

		//创建索引之前   删除该文件夹之前已经创建过的索引
		TeeLuceneSoapService.deleteDocuments("pubnetdisk", "folderSid",""+sid+"");
		
		
		List<Map> mapList=new ArrayList<Map>();
		//获取该目录下所有的附件
		for (TeeFileNetdisk disk : diskList) {
			if(disk.getAttachemnt()!=null){
				//attList.add(disk.getAttachemnt());
				String filePath=disk.getAttachemnt().getAttachSpace().getSpacePath()+"/"+disk.getAttachemnt().getModel()+"/"+disk.getAttachemnt().getAttachmentPath()+"/"+disk.getAttachemnt().getAttachmentName();
				File file=new File(filePath);
				if(file.length()>0){
					Map map=new HashMap();
					map.put("attachSid", disk.getAttachemnt().getSid()+"");
					map.put("title", disk.getAttachemnt().getFileName());
					map.put("folderSid", ""+sid+"");
					map.put("fileNetDiskSid", disk.getSid()+"");
					map.put("body", file);
					mapList.add(map);
				}			
			}
		}
		
		//创建索引
		TeeLuceneSoapService.addRecords("pubnetdisk", mapList);
		json.setRtMsg("成功创建索引！");
		json.setRtState(true);
		
		
		return json;
	}

}
