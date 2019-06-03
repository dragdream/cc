package com.tianee.oa.core.attachment.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.bean.TeeOfficeSwitch;
import com.tianee.oa.core.attachment.model.TeeOfficeSwitchModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeOfficeSwitchService extends TeeBaseService{

	
	/**
	 * 根据原附件id获取中间表信息
	 * @param request
	 * @return
	 */
	public TeeJson getTaskByAttachId(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取原附件id
		int attId=TeeStringUtil.getInteger(request.getParameter("attId"), 0);
		List<TeeOfficeSwitch> list=simpleDaoSupport.executeQuery("from TeeOfficeSwitch where attachment.sid=? ",new Object[]{attId});
		if(list!=null&&list.size()>0){
			TeeOfficeSwitch officeSwitch=list.get(0);
			TeeOfficeSwitchModel model=parseToModel(officeSwitch);
			json.setRtData(model);
		}
		json.setRtState(true);
		return json;
	}

	/**
	 * 实体类转换成model类型的 
	 * @param officeSwitch
	 * @return
	 */
	private TeeOfficeSwitchModel parseToModel(TeeOfficeSwitch officeSwitch) {
		TeeOfficeSwitchModel model=new TeeOfficeSwitchModel();
		BeanUtils.copyProperties(officeSwitch, model);
		if(officeSwitch.getAttachment()!=null){
			model.setAttachmentId(officeSwitch.getAttachment().getSid());
		    model.setAttachmentName(officeSwitch.getAttachment().getAttachmentName());
		}
		
		if(officeSwitch.getHtmlAtt()!=null){
			model.setHtmlAttId(officeSwitch.getHtmlAtt().getSid());
			model.setHtmlAttName(officeSwitch.getHtmlAtt().getAttachmentName());
		}
		return model;
	}

	
	
	/**
	 * 转换html
	 * @param request
	 * @return
	 */
	public TeeJson insertSwitchTask(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取原附件id
		int attId=TeeStringUtil.getInteger(request.getParameter("attId"), 0);
		TeeAttachment att=(TeeAttachment) simpleDaoSupport.get(TeeAttachment.class,attId);
		//判断是不是第一次转换
		List<TeeOfficeSwitch> list=simpleDaoSupport.executeQuery("from TeeOfficeSwitch where attachment.sid=? ",new Object[]{attId});
		if(list==null||list.size()==0){
			TeeOfficeSwitch officeSwitch=new TeeOfficeSwitch();
			officeSwitch.setAttachment(att);
			officeSwitch.setFlag(0);
			simpleDaoSupport.save(officeSwitch);
		}
		json.setRtState(true);
		return json;
	}
    
	
}
