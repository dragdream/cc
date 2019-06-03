package com.beidasoft.xzzf.punish.video.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.inspection.plan.bean.InspectionPlanBase;
import com.beidasoft.xzzf.inspection.plan.dao.InspectionPlanBaseDao;
import com.beidasoft.xzzf.inspection.plan.model.InspectionPlanBaseModel;
import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.PunishCalendar;
import com.beidasoft.xzzf.punish.common.dao.PunishBaseDao;
import com.beidasoft.xzzf.punish.video.bean.VideoManagement;
import com.beidasoft.xzzf.punish.video.dao.VideoManagementDao;
import com.beidasoft.xzzf.punish.video.model.VideoManagementModel;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webservice.model.Person;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class VideoManagementService extends TeeBaseService {
	@Autowired
	private VideoManagementDao videoManagementDao;
	@Autowired
	private TeePersonDao personDao;
	@Autowired
	private PunishBaseDao punishBaseDao;
	@Autowired
	private TeeAttachmentService attachService;
	/**
	 * 修改
	 * @param model
	 * @return
	 */
	public TeeJson updateVideoManagement(VideoManagementModel model){
		TeeJson json = new TeeJson();
		VideoManagement videoManagement = videoManagementDao.get(model.getId());
		//更新日期
		videoManagement.setUpdateDate(new Date());
		//追加附件ids
		if(!TeeUtility.isNullorEmpty(model.getAttachIds())){
			videoManagement.setAttachIds(videoManagement.getAttachIds()+","+model.getAttachIds());
		}
		//视频数量
		videoManagement.setAttachSum(videoManagement.getAttachIds().split(",").length);
		videoManagement.setRemark(model.getRemark());
		videoManagement.setVideoName(model.getVideoName());
		json.setRtState(true);
		return json;
	}
	
	
	
	/**
	 * 分页查询
	 * @param dm
	 * @param request
	 * @param baseId
	 * @return
	 */
	public TeeEasyuiDataGridJson getVideoManagementOfPage(TeeDataGridModel dm, String baseId, HttpServletRequest request) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		//查询条件list
		List param = new ArrayList();
		//基础hql
		String hql = " from VideoManagement  where baseId='"+baseId+"'";
		long total = simpleDaoSupport.count("select count(id) " + hql, param.toArray());
		datagrid.setTotal(total);
		List<VideoManagement> list = simpleDaoSupport.pageFind(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param.toArray());
		
		List<VideoManagementModel> models = new ArrayList<VideoManagementModel>();
		VideoManagementModel model = null;
		for(VideoManagement row : list) {
			model = new VideoManagementModel();
			model = transferModel(row);
			models.add(model);
		}
		datagrid.setRows(models);
		
		return datagrid;
	}
	
	/**
	 * 将bean转换为model
	 * @param VideoManagement
	 * @return
	 */
	private VideoManagementModel transferModel(VideoManagement videoManagement) {
		VideoManagementModel videoManagementModel = new VideoManagementModel();
		BeanUtils.copyProperties(videoManagement, videoManagementModel);
		//创建人姓名
		videoManagementModel.setCreatePersonName(personDao.get(videoManagement.getCreatePersonId()).getUserName());
		//转换创建时间
		if(videoManagement.getCreateDate() != null) {
			videoManagementModel.setCreateDateStr(TeeDateUtil.format(videoManagement.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
		}
		
		//转换创建时间
		if(videoManagement.getUpdateDate() != null) {
			videoManagementModel.setUpdateDateStr(TeeDateUtil.format(videoManagement.getUpdateDate(), "yyyy-MM-dd HH:mm:ss"));
		}

		return videoManagementModel;
	}
	
	/**
	 * 保存
	 */
	public TeeJson save(VideoManagementModel videoManagementModel, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		VideoManagement videoManagement = new VideoManagement();
		BeanUtils.copyProperties(videoManagementModel, videoManagement);
		videoManagement.setId(UUID.randomUUID().toString());
		//创建时间
		videoManagement.setCreateDate(new Date());
		
		//创建人id
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		videoManagement.setCreatePersonId(user.getUuid());
		//附件处理
		List<TeeAttachment> attachList = attachService.getAttachmentsByIds(videoManagementModel.getAttachIds());
		for (TeeAttachment attach : attachList) {
			attach.setModelId(videoManagement.getId());
			attach.setModel("lawVideo");
			attachService.updateAttachment(attach);
		}
		//附件数量
		videoManagement.setAttachSum(attachList.size());
		videoManagementDao.save(videoManagement);
		//案件主表修改有无视频字段
		PunishBase punishBase = punishBaseDao.get(videoManagement.getBaseId());
		punishBase.setIsHasVideo(1);
		punishBaseDao.saveOrUpdate(punishBase);
		json.setRtData(videoManagement.getId());
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据id获取
	 */
	public TeeJson get(String id) {
		TeeJson json = new TeeJson();
		VideoManagement videoManagement = videoManagementDao.get(id);
		VideoManagementModel model = new VideoManagementModel();
		BeanUtils.copyProperties(videoManagement, model);
		model.setCreateDateStr(TeeDateUtil.format(videoManagement.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
		model.setUpdateDateStr(TeeDateUtil.format(videoManagement.getUpdateDate(),"yyyy-MM-dd HH:mm:ss"));
		//附件
		List<TeeAttachmentModel> attachModels = attachService.getAttachmentModelsByIds(videoManagement.getAttachIds());
		model.setAttachModels(attachModels);
		String attachNames = "";
		for (int i = 0; i < attachModels.size(); i++) {
			attachNames += attachModels.get(i).getSid();
			if(i < attachModels.size()-1){
				attachNames += ",";
			}
		}
		model.setAttachModels(attachModels);
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
}
