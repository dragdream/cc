package com.tianee.oa.core.base.dam.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.base.dam.bean.TeeFileAttch;
import com.tianee.oa.core.base.dam.bean.TeeFiles;
import com.tianee.oa.core.base.dam.model.TeeFileAttchModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeFileAttchService extends TeeBaseService{

	@Autowired
	TeeAttachmentService attachmentService;
	/**
	 * 新建/编辑
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeFileAttchModel model) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TeeJson  json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeFileAttch fileAttch=null;
		if(sid>0){//编辑
			fileAttch=(TeeFileAttch) simpleDaoSupport.get(TeeFileAttch.class,sid);
			BeanUtils.copyProperties(model, fileAttch);
			if(model.getAttchId()!=0){
				TeeAttachment att=(TeeAttachment) simpleDaoSupport.get(TeeAttachment.class,model.getAttchId());
			    fileAttch.setAttch(att);
			}
			if(model.getFileId()!=0){
				TeeFiles file=(TeeFiles) simpleDaoSupport.get(TeeFiles.class,model.getFileId());
			    fileAttch.setFile(file);
			}
			if(model.getPubTimeStr()!=null&&!("").equals(model.getPubTimeStr())){
				Date d=null;
				try {
					d = sdf.parse(model.getPubTimeStr());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Calendar pubTime=Calendar.getInstance();
				pubTime.setTime(d);
				fileAttch.setPubTime(pubTime);
			}
			simpleDaoSupport.update(fileAttch);
		}else{//新建
			fileAttch=new TeeFileAttch();
			BeanUtils.copyProperties(model, fileAttch);
			if(model.getAttchId()!=0){
				TeeAttachment att=(TeeAttachment) simpleDaoSupport.get(TeeAttachment.class,model.getAttchId());
			    fileAttch.setAttch(att);
			}
			if(model.getFileId()!=0){
				TeeFiles file=(TeeFiles) simpleDaoSupport.get(TeeFiles.class,model.getFileId());
			    fileAttch.setFile(file);
			}
			if(model.getPubTimeStr()!=null&&!("").equals(model.getPubTimeStr())){
				Date d=null;
				try {
					d = sdf.parse(model.getPubTimeStr());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Calendar pubTime=Calendar.getInstance();
				pubTime.setTime(d);
				fileAttch.setPubTime(pubTime);
			}
			simpleDaoSupport.save(fileAttch);
		}
		json.setRtState(true);
		return json;
	}

	
	
	/**
	 * 根据档案id   获取与该档案相关的档案附件信息
	 * @param request
	 * @return
	 */
	public TeeJson getFileAttchListByFileId(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int fileId=TeeStringUtil.getInteger(request.getParameter("fileId"),0);
		List<TeeFileAttch> list=simpleDaoSupport.find(" from TeeFileAttch where file.sid=? order by sortNo asc", new Object[]{fileId});
		List<TeeFileAttchModel> modelList=new ArrayList<TeeFileAttchModel>();
		if(list!=null&&list.size()>0){
			TeeFileAttchModel model=null;
			for (TeeFileAttch fileAttch : list) {
				model=parseToModel(fileAttch);
				modelList.add(model);
			}	
		}
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}



	/**
	 * 实体类转换成model
	 * @param fileAttch
	 * @return
	 */
	private TeeFileAttchModel parseToModel(TeeFileAttch fileAttch) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TeeFileAttchModel model=new TeeFileAttchModel();
		BeanUtils.copyProperties(fileAttch, model);
		if(fileAttch.getPubTime()!=null){
			model.setPubTimeStr(sdf.format(fileAttch.getPubTime().getTime()));
		}else{
			model.setPubTimeStr("");
		}
		if(fileAttch.getAttch()!=null){
			model.setAttchId(fileAttch.getAttch().getSid());
			model.setAttchName(fileAttch.getAttch().getFileName());
			
			TeeAttachmentModel attModel=attachmentService.getModelById(fileAttch.getAttch().getSid());
		    model.setAttModel(attModel);
		}
		if(fileAttch.getFile()!=null){
			model.setFileId(fileAttch.getFile().getSid());
		}
		return model;
	}



	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeFileAttch fileAtt=(TeeFileAttch) simpleDaoSupport.get(TeeFileAttch.class,sid);
		    if(fileAtt!=null){
		    	TeeFileAttchModel model=parseToModel(fileAtt);
		    	json.setRtData(model);
		    	json.setRtState(true);
		    }else{
		    	json.setRtState(false);
				json.setRtMsg("数据获取失败！");
		    }
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}



	/**
	 * 根据主键删除档案附件信息
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			TeeFileAttch fileAtt=(TeeFileAttch) simpleDaoSupport.get(TeeFileAttch.class,sid);
		    if(fileAtt!=null){
		    	simpleDaoSupport.deleteByObj(fileAtt);
		    	json.setRtState(true);
		    	json.setRtMsg("删除成功！");
		    }else{
		    	json.setRtState(false);
				json.setRtMsg("数据获取失败！");
		    }
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}

}
