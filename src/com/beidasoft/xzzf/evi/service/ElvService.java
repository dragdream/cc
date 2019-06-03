package com.beidasoft.xzzf.evi.service;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.evi.bean.EleEvidenceBase;
import com.beidasoft.xzzf.evi.bean.ElvEvidenceChild;
import com.beidasoft.xzzf.evi.elvdao.ElvDao;
import com.beidasoft.xzzf.evi.model.ElvModel;
import com.incors.plaf.alloy.ch;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
@Service
public class ElvService extends TeeBaseService{
	@Autowired
	private ElvDao elvD;
	@Autowired
	private TeeAttachmentService attachmentService;//注入附件
	@Autowired
	private ElvEvidenceChildService childService;
    
	public List<EleEvidenceBase> listByPage(int firstResult,int rows){
		return elvD.listByPage(firstResult, rows);
	}
	/**
	 * dateGraid控件的展示，以及检索
	 * @param firstResult
	 * @param rows
	 * @param searchModel
	 * @return
	 */
	public List<EleEvidenceBase> listByPage(int firstResult,int rows,ElvModel searchModel){
		return elvD.listByPage(firstResult, rows,searchModel);
	}
	/**
	 * 返回总记录数
	 * @return
	 */
	public long gettotal(){
		return elvD.gettotal();
	}
	
	public long gettotal(ElvModel searchModel){
		return elvD.gettotal(searchModel);
		
	}
	/**
	 * 回显操作	
     * 参数为证据模型的主键id
	 * @param id
	 * @return
	 */
	public EleEvidenceBase getbyId(String id){
		return elvD.get(id);
	}
	
	/**
	 * 添加数据，参数为EleEvidenceBase，由前台model模型转换而来
	 * @param eeb
	 * @throws IOException 
	 */
	public TeeJson save(ElvModel elvModel,String attaches){
		EleEvidenceBase eeb=new EleEvidenceBase();
		TeeJson json = new TeeJson(); 	
		BeanUtils.copyProperties(elvModel, eeb);
		eeb.setGet_time(TeeDateUtil.format(elvModel.getGet_time_str(),"yyyy-MM-dd"));
		eeb.setCreate_time(Calendar.getInstance());
		eeb.setId(UUID.randomUUID().toString());
		elvD.save(eeb);
		//处理随带的附件
		if(!attaches.equals("") &&attaches!=null){
			String sp[] =attaches.split(",");
			for(String attachId:sp){
				TeeAttachment attachment=attachmentService.getById(Integer.parseInt(attachId));//要么改方法  要么改参数 2种
				attachment.setModelId(eeb.getId()+"");
				attachmentService.updateAttachment(attachment);	
				File file = new File(attachment.getFilePath());
				if (!file.exists()) {
					json.setRtMsg("文件上传失败！");
					json.setRtState(false);
					return json;
				}
				//将上传的文件记录到子表中
				ElvEvidenceChild child = new ElvEvidenceChild();
				child.setId(UUID.randomUUID().toString());
				child.setFileRealName(attachment.getFileName());
				child.setFileName(attachment.getAttachmentName());
				child.setEvidenceBaseId(eeb.getId());
				child.setFilePath(attachment.getFilePath());
				System.out.println(attachment.getFilePath());
				childService.save(child);
			}
		}
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 修改操作，参数为后台传入的bean
	 * @param eleinfo
	 */
	public TeeJson update(ElvModel elvmodel,String attaches){
		TeeJson json = new TeeJson();
		EleEvidenceBase eleinfo = elvD.get(elvmodel.getId());
		BeanUtils.copyProperties(elvmodel,eleinfo);// 两个对象类型有些字段类型不一样，不能复制
		eleinfo.setGet_time(TeeDateUtil.format(elvmodel.getGet_time_str(),"yyyy-MM-dd"));	
		elvD.update(eleinfo);
		//处理随带的附件
		if(!attaches.equals("") &&attaches!=null){
			String sp[] =attaches.split(",");
			for(String attachId:sp){
				TeeAttachment attachment=attachmentService.getById(Integer.parseInt(attachId));//要么改方法  要么改参数 2种
				attachment.setModelId(eleinfo.getId()+"");
				attachmentService.updateAttachment(attachment);	
			}
		}
		json.setRtState(true);
		return json;
	}
	
	public void del(String id){
		elvD.delete(id);
	}
	
	/**
	 * 证据固化
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	public TeeJson fileCuring(String id) throws IOException{
		TeeJson json = new TeeJson();
		List<ElvEvidenceChild> childList = childService.getListByEleBaseId(id);
		for (int i = 0; i < childList.size(); i++) {
			File file = new File(childList.get(i).getFilePath());
			if (!file.exists()) {
				json.setRtMsg("文件不存在！");
				json.setRtState(false);
				return json;
			}
			//生成固化标识
			childList.get(i).setMdKeys(FileCuring.getFileMD5String(file));
			childService.update(childList.get(i));
			json.setRtState(true);
		}
		if (json.isRtState()) {
			//修改主表固化状态
			EleEvidenceBase base = elvD.get(id);
			base.setMd_keys(1);
			elvD.update(base);
		}
		json.setRtState(true);
		return json;
	}
	/**
	 * 逻辑删除
	 * @return
	 */
	public TeeJson updateDelete(String id){
		TeeJson json = new TeeJson();
		EleEvidenceBase eleEvidenceBase = elvD.get(id);
		eleEvidenceBase.setIsDelete(1);
		elvD.update(eleEvidenceBase);
		json.setRtState(true);
		return json;
	}

}
