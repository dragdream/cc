package com.beidasoft.xzzf.punish.classicCase.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;





import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.classicCase.bean.ClassicCase;
import com.beidasoft.xzzf.punish.classicCase.dao.ClassicCaseDao;
import com.beidasoft.xzzf.punish.classicCase.model.ClassicCaseModel;
import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.service.PunishBaseService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class ClassicCaseService extends TeeBaseService {
	@Autowired
	private ClassicCaseDao classicCaseDao;
	@Autowired
	private PunishBaseService punishBaseService;
	@Autowired
	TeeAttachmentService attachService;
	
	
	/**
	 * 分页查询
	 * @param dm
	 * @param request
	 * @param baseId
	 * @return
	 */
	public TeeEasyuiDataGridJson pageList(TeeDataGridModel dm, ClassicCase classicCase) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		//查询条件list
		List param = new ArrayList();
		//基础hql
		String hql = " from ClassicCase  where 1=1 and delFlag=0 ";
		//title查询条件
		if(!TeeUtility.isNullorEmpty(classicCase.getBaseTitle())){
			String title = TeeDbUtility.formatString(classicCase.getBaseTitle());
			hql += " and baseTitle like '%"+title+"%' ";
		}
		//sourceType查询条件
		if(!TeeUtility.isNullorEmpty(classicCase.getSourceType())){
			hql += " and sourceType="+classicCase.getSourceType();
		}
		long total = simpleDaoSupport.count("select count(id) " + hql, param.toArray());
		datagrid.setTotal(total);
		List<ClassicCase> list = simpleDaoSupport.pageFind(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param.toArray());
		List<ClassicCaseModel> models = new ArrayList<ClassicCaseModel>();
		ClassicCaseModel classicCaseModel = null;
		for(ClassicCase row : list) {
			classicCaseModel = new ClassicCaseModel();
			classicCaseModel = transferModel(row);
			models.add(classicCaseModel);
		}
		datagrid.setRows(models);
		
		return datagrid;
	}
	
	//bean转model
	public ClassicCaseModel transferModel(ClassicCase classicCase){
		ClassicCaseModel model = new ClassicCaseModel();
		BeanUtils.copyProperties(classicCase, model);
		//转换登记接案时间
		if(!TeeUtility.isNullorEmpty(classicCase.getFilingDate())){
			model.setFilingDateStr(TeeDateUtil.format(classicCase.getFilingDate(), "yyyy-MM-dd"));
		}
		//转换立案受理时间
		if(!TeeUtility.isNullorEmpty(classicCase.getClosedDate())){
			model.setClosedDateStr(TeeDateUtil.format(classicCase.getClosedDate(), "yyyy-MM-dd"));
		}
		
		return model;
	}
	
	/**
	 * 关联基础案例保存
	 */
	public TeeJson saveByBaseCase(ClassicCaseModel model) {
		TeeJson json = new TeeJson();
		PunishBase punishBase = new PunishBase();
		punishBase = punishBaseService.getbyid(model.getId());
		ClassicCase classicCase = new ClassicCase();
		BeanUtils.copyProperties(punishBase, classicCase);
		classicCase.setDelFlag("0");
		classicCase.setOrigin(model.getOrigin());
		classicCase.setId(punishBase.getBaseId());
		classicCaseDao.saveClassicCase(classicCase);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 上传附件方式保存
	 */
	public TeeJson saveByUpload(ClassicCaseModel model) {
		TeeJson json = new TeeJson();
		ClassicCase classicCase = new ClassicCase();
		BeanUtils.copyProperties(model, classicCase);
		classicCase.setId(UUID.randomUUID().toString());
		classicCase.setFilingDate(TeeDateUtil.format(model.getFilingDateStr(), "yyyy-MM-dd"));
		classicCase.setClosedDate(TeeDateUtil.format(model.getClosedDateStr(), "yyyy-MM-dd"));
		classicCase.setDelFlag("0");
		classicCase.setOrigin(model.getOrigin());
		
		//附件处理
		List<TeeAttachment> attachList = attachService.getAttachmentsByIds(model.getCaseAttachment());
		for (TeeAttachment attach : attachList) {
			attach.setModelId(classicCase.getId());
			attachService.updateAttachment(attach);
		}
		
		classicCaseDao.saveClassicCase(classicCase);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 逻辑删除
	 * @param ids
	 * @param num
	 * @param param
	 * @return TeeJson
	 */
	public TeeJson delete(String ids, String num, String param){
		TeeJson json = new TeeJson();
		int count = classicCaseDao.updateStatusByUuids(ids, num, param);
		json.setRtData(count);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据id查询
	 */
	public TeeJson loadById(String id) {
		TeeJson json = new TeeJson();
		ClassicCase classicCase = classicCaseDao.loadById(id);
		ClassicCaseModel model = new ClassicCaseModel();
		BeanUtils.copyProperties(classicCase, model);
		model.setFilingDateStr(TeeDateUtil.format(classicCase.getFilingDate(), "yyyy-MM-dd"));
		model.setClosedDateStr(TeeDateUtil.format(classicCase.getClosedDate(), "yyyy-MM-dd"));
		//附件
		List<TeeAttachmentModel> attachModels = attachService.getAttacheModels("workFlow", classicCase.getId());
		model.setAttachModels(attachModels);
		String attachNames = "";
		for (int i = 0; i < attachModels.size(); i++) {
			attachNames += attachModels.get(i).getSid();
			if(i < attachModels.size()-1){
				attachNames += ",";
			}
		}
		model.setCaseAttachment(attachNames);
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
}
