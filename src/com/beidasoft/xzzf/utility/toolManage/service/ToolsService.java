package com.beidasoft.xzzf.utility.toolManage.service;

import java.util.ArrayList;
import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.utility.toolManage.bean.Tools;
import com.beidasoft.xzzf.utility.toolManage.dao.ToolsDao;
import com.beidasoft.xzzf.utility.toolManage.model.ToolsModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class ToolsService extends TeeBaseService {
	@Autowired
	private ToolsDao toolsDao;
	@Autowired
	TeeAttachmentService attachService;
	
	
	/**
	 * 分页查询
	 * @param dm
	 * @param request
	 * @param baseId
	 * @return
	 */
	public TeeEasyuiDataGridJson pageList(TeeDataGridModel dm, Tools tools) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		//查询条件list
		List param = new ArrayList();
		//基础hql
		String hql = " from Tools  where 1=1  ";
		long total = simpleDaoSupport.count("select count(id) " + hql, param.toArray());
		datagrid.setTotal(total);
		List<Tools> list = simpleDaoSupport.pageFind(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param.toArray());
		List<ToolsModel> models = new ArrayList<ToolsModel>();
		ToolsModel toolsModel = null;
		for(Tools row : list) {
			toolsModel = new ToolsModel();
			toolsModel = transferModel(row);
			models.add(toolsModel);
		}
		datagrid.setRows(models);
		
		return datagrid;
	}
	
	//bean转model
	public ToolsModel transferModel(Tools tools){
		ToolsModel model = new ToolsModel();
		BeanUtils.copyProperties(tools, model);
		//工具附件id
		model.setToolsAttachId(TeeStringUtil.getInteger(tools.getToolsAttach().getSid(), 0));
		//教程附件id
		model.setTutorialAttachId(TeeStringUtil.getInteger(tools.getTutorialAttach().getSid(), 0));
		//工具附件名称
		if(!TeeUtility.isNullorEmpty(tools.getToolsAttach().getFileName())){
			model.setToolsAttachName(tools.getToolsAttach().getFileName());
		}
		//教程附件名称
		if(!TeeUtility.isNullorEmpty(tools.getTutorialAttach().getFileName())){
			model.setTutorialAttachName(tools.getTutorialAttach().getFileName());
		}
		
		//转换上传时间
		if(!TeeUtility.isNullorEmpty(tools.getUploadDate())){
			model.setUploadDateStr(TeeDateUtil.format(tools.getUploadDate(), "yyyy-MM-dd"));
		}
		//转换更新时间
		if(!TeeUtility.isNullorEmpty(tools.getUpdateDate())){
			model.setUpdateDateStr(TeeDateUtil.format(tools.getUpdateDate(), "yyyy-MM-dd"));
		}
		
		return model;
	}
	
	/**
	 * 根据id查询
	 */
	public TeeJson loadById(String id) {
		TeeJson json = new TeeJson();
		Tools tools = toolsDao.loadById(id);
		ToolsModel model = new ToolsModel();
		model = transferModel(tools);
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 保存
	 * @param tools
	 */
	public void save(ToolsModel toolsModel,HttpServletRequest request){
		Tools tools = new Tools();
		BeanUtils.copyProperties(toolsModel, tools);
		tools.setToolsAttach(attachService.getById(toolsModel.getToolsAttachId()));
		tools.setTutorialAttach(attachService.getById(toolsModel.getTutorialAttachId()));
		tools.setToolsSize(TeeFileUtility.getFileSizeDesc(tools.getToolsAttach().getSize()));
		toolsDao.saveTools(tools, request);
	}
	
	/**
	 * 修改
	 * @param tools
	 */
	public void update(ToolsModel toolsModel, HttpServletRequest request){
		Tools tools = new Tools();
		tools = toolsDao.loadById(toolsModel.getId());
		//如果附件不一致，删除原有的附件
		if(tools.getToolsAttach().getSid()!=toolsModel.getToolsAttachId()){
			attachService.deleteAttach(tools.getToolsAttach().getSid());
		}
		if(tools.getTutorialAttach().getSid()!=toolsModel.getTutorialAttachId()){
			attachService.deleteAttach(tools.getTutorialAttach().getSid());
		}
		tools.setToolsName(toolsModel.getToolsName());
		tools.setToolsVersion(toolsModel.getToolsVersion());
		tools.setToolsIntroduction(toolsModel.getToolsIntroduction());
		tools.setTutorialName(toolsModel.getTutorialName());
		tools.setToolsAttach(attachService.getById(toolsModel.getToolsAttachId()));
		tools.setTutorialAttach(attachService.getById(toolsModel.getTutorialAttachId()));
		tools.setToolsSize(TeeFileUtility.getFileSizeDesc(tools.getToolsAttach().getSize()));
		toolsDao.updateTools(tools, request);
	}
	
	/**
	 * 删除
	 * @param tools
	 */
	public void delete(String id){
		toolsDao.deleteTools(id);
	}
	
}
