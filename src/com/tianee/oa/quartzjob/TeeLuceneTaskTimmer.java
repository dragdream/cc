package com.tianee.oa.quartzjob;



import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tianee.lucene.TeeLuceneSoapService;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.general.bean.TeeLuceneTask;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;


@Service
public class TeeLuceneTaskTimmer extends TeeBaseService{
	
	@SuppressWarnings("unchecked")
	public void doTimmer(){
		if(TeeSysProps.getProps()==null){
			return;
		}
		
	//取出modelNo=024的lucene_task里面的任务
		String hql ="from TeeLuceneTask tlt where tlt.modelNo='024' ";
		try {
			
			List<TeeLuceneTask> tltList=simpleDaoSupport.executeQuery(hql,null);
			@SuppressWarnings("rawtypes")
			List<Map> mapList=new ArrayList<Map>();
			//循环任务集合，然后每次通过modelId，找到对应的记录
			for (TeeLuceneTask tlt:tltList) {
				TeeFileNetdisk tfnd = (TeeFileNetdisk) simpleDaoSupport.get(TeeFileNetdisk.class,Integer.parseInt(tlt.getModelId()));
				if(tfnd.getAttachemnt()!=null){
					
					String ffid="";
					String[] ljStr = tfnd.getFileFullPath().split("/");
					ffid=ljStr[2];
					String filePath=tfnd.getAttachemnt().getAttachSpace().getSpacePath()+"/"+tfnd.getAttachemnt().getModel()+"/"+tfnd.getAttachemnt().getAttachmentPath()+"/"+tfnd.getAttachemnt().getAttachmentName();
					File file=new File(filePath);
					if(file.length()>0){
						@SuppressWarnings("rawtypes")
						Map map=new HashMap();
						map.put("attachSid", tfnd.getAttachemnt().getSid()+"");
						map.put("title", tfnd.getAttachemnt().getFileName());
						map.put("folderSid", ""+ffid+"");
						map.put("fileNetDiskSid", tfnd.getSid()+"");
						map.put("body", file);
						mapList.add(map);
					}			
				}
				
			}
			//删除TeeLuceneTask
			for(TeeLuceneTask teeLuceneTask:tltList){
				simpleDaoSupport.deleteByObj(teeLuceneTask);
			}
			
			
			//对每一条数据进行创建索引的操作
			TeeLuceneSoapService.addRecords("pubnetdisk", mapList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
