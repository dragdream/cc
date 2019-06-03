package com.tianee.oa.core.workflow.workmanage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowProcessDao;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowTypeDao;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowPrintTemplate;
import com.tianee.oa.core.workflow.workmanage.dao.TeeFlowPrintTemplateDao;
import com.tianee.oa.core.workflow.workmanage.model.TeeFlowPrintTemplateModel;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeFlowPrintTemplateService extends TeeBaseService implements TeeFlowPrintTemplateServiceInterface {
	@Autowired
	private  TeeFlowPrintTemplateDao tplDao;
	
	@Autowired
	private TeeFlowTypeDao flowTypeDao;
	
	@Autowired
	private TeeFlowProcessDao flowProcessDao;
	
	@Autowired
	private TeeSimpleDaoSupport simpleDao;
	

	

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrintTemplateServiceInterface#addOrUpdateTpl(com.tianee.oa.core.workflow.workmanage.model.TeeFlowPrintTemplateModel, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeFlowPrintTemplate addOrUpdateTpl(TeeFlowPrintTemplateModel model , HttpServletRequest request) {
		TeeFlowPrintTemplate tpl = new TeeFlowPrintTemplate();
		if(model.getSid() > 0 ){
			TeeFlowPrintTemplate tplOld = tplDao.getById(model.getSid());//流程
			if(tplOld != null ){
				tplOld.setModulType(model.getModulType());
				tplOld.setModulName(model.getModulName());
				String flowPrcsIds = model.getFlowPrcsIds();
				if(!TeeUtility.isNullorEmpty(flowPrcsIds)){
					List<TeeFlowProcess> prcsList = flowProcessDao.getPrcsByIds(flowPrcsIds);
					Set<TeeFlowProcess> prcsSet = new HashSet<TeeFlowProcess>(prcsList);
					tplOld.setFlowPrcs(prcsSet);
				}
				tplDao.updateTpl(tplOld);
				return tplOld;
			}
			//BeanUtils.copyProperties(model, tplOld);
			
		}else{
			BeanUtils.copyProperties(model, tpl);
			tpl.setFlowType(flowTypeDao.get(model.getFlowTypeId()));//所属流程
			
		
			tplDao.addTpl(tpl);
		}
		
		return tpl;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrintTemplateServiceInterface#updateModulDesigner(com.tianee.oa.core.workflow.workmanage.model.TeeFlowPrintTemplateModel, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeFlowPrintTemplate updateModulDesigner(TeeFlowPrintTemplateModel model , HttpServletRequest request) {
		if(model.getSid() > 0 ){
			TeeFlowPrintTemplate tplOld = tplDao.getById(model.getSid());//流程
			tplOld.setModulContent(model.getModulContent());
			tplOld.setModulField(model.getModulField());
			tplDao.updateTpl(tplOld);
			return tplOld;
		}else{
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrintTemplateServiceInterface#updateModul(com.tianee.oa.core.workflow.workmanage.model.TeeFlowPrintTemplateModel, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeFlowPrintTemplate updateModul(TeeFlowPrintTemplateModel model , HttpServletRequest request) {
		if(model.getSid() > 0 ){
			TeeFlowPrintTemplate tplOld = tplDao.getById(model.getSid());//流程
			tplOld.setModulContent(model.getModulContent());
			tplDao.updateTpl(tplOld);
			return tplOld;
		}else{
			return null;
		}
	}
	
	
	

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrintTemplateServiceInterface#getById(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeFlowPrintTemplateModel getById(HttpServletRequest request) {
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid > 0 ){
			TeeFlowPrintTemplate tpl = tplDao.getById(sid);
			if(tpl != null ){
				TeeFlowPrintTemplateModel model = new TeeFlowPrintTemplateModel();
				BeanUtils.copyProperties(tpl, model);
				return model;
			}
		}
		return null;
	}
	

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrintTemplateServiceInterface#getByIdInfo(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson getByIdInfo(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String flowTypeId = request.getParameter("flowTypeId");
		int flowId = TeeStringUtil.getInteger(flowTypeId, 0);
		Map map = new HashMap();
		if(sid > 0 ){
			TeeFlowPrintTemplate tpl = tplDao.getById(sid);
			if(tpl != null ){
				TeeFlowPrintTemplateModel model = new TeeFlowPrintTemplateModel();
				model.setSid(tpl.getSid());
				model.setModulType(tpl.getModulType());
				model.setModulName(tpl.getModulName());
				//BeanUtils.copyProperties(tpl, model);
				Set<TeeFlowProcess> set = tpl.getFlowPrcs();
				
				List list = new ArrayList();
				List<TeeFlowProcess> prcsList = flowProcessDao.findFlowProcessByFlowType(flowId);
				for (int i = 0; i < prcsList.size(); i++) {
					TeeFlowProcess flowProcess  = prcsList.get(i);
					Map prcsMap = new HashMap();
					prcsMap.put("sid", flowProcess.getSid());
					prcsMap.put("prcsName", flowProcess.getPrcsName());
					boolean  isPriv = false;
					Iterator<TeeFlowProcess> it = set.iterator();	
					while(it.hasNext()){
						TeeFlowProcess prcs = it.next();
						if(prcs.getSid() == flowProcess.getSid()){
							isPriv = true;
							break;
						}
					}
					prcsMap.put("isPriv", isPriv);
					list.add(prcsMap);
				}
				map.put("printModul", model);
				map.put("prcsList", list);
			}
		}
		json.setRtData(map);
		return json;
	}
	
	

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrintTemplateServiceInterface#delById(java.lang.String)
	 */
	@Override
	public void delById(String sid) {
		int id = TeeStringUtil.getInteger(sid, 0);
		if(id > 0 ){
			TeeFlowPrintTemplate tpl = tplDao.getById(id);
			if(tpl != null ){
				tpl.setFlowType(null);
				tpl.setFlowPrcs(null);
			}
			tplDao.delById(id);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrintTemplateServiceInterface#selectModulByFlowType(java.lang.String)
	 */
	@Override
	public List<TeeFlowPrintTemplateModel> selectModulByFlowType(String flowTypeId) {
		int ftId = TeeStringUtil.getInteger(flowTypeId, 0);
		List<TeeFlowPrintTemplateModel>  list  = new ArrayList<TeeFlowPrintTemplateModel>();
		List<TeeFlowPrintTemplate>  tplList  = tplDao.selectModulByFlowType(ftId);
		for (int i = 0; i < tplList.size(); i++) {
			TeeFlowPrintTemplate tpl = tplList.get(i);
			TeeFlowPrintTemplateModel model = new TeeFlowPrintTemplateModel();
			Set<TeeFlowProcess> prcsList = tpl.getFlowPrcs();
			Iterator<TeeFlowProcess> it = prcsList.iterator();
			String flowPrcsDescs = "";
			String flowPrcsIds="";
			while(it.hasNext()){
				TeeFlowProcess prcs = it.next();
				flowPrcsDescs = flowPrcsDescs + prcs.getPrcsName() + ",";
				flowPrcsIds=flowPrcsIds+prcs.getSid()+",";
			}
			model.setFlowPrcsDescs(flowPrcsDescs);
			model.setFlowPrcsIds(flowPrcsIds);
			BeanUtils.copyProperties(tpl, model);
			list.add(model);
		}

		return list;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrintTemplateServiceInterface#selectModulByFlowTypeAndPrcsId(com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType, com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess)
	 */
	@Override
	public List<TeeFlowPrintTemplateModel> selectModulByFlowTypeAndPrcsId(TeeFlowType ft,	TeeFlowProcess fp) {
		List<TeeFlowPrintTemplateModel>  list  = new ArrayList<TeeFlowPrintTemplateModel>();
		int prcsId = 0;
		if(fp != null){
			prcsId = fp.getSid();
		}
		List<TeeFlowPrintTemplate>  tplList  = tplDao.selectModulByFlowTypeAndPrcsId(ft.getSid() , ft.getType() , prcsId);
		for (int i = 0; i < tplList.size(); i++) {
			TeeFlowPrintTemplate tpl = tplList.get(i);
			TeeFlowPrintTemplateModel model = new TeeFlowPrintTemplateModel();
			BeanUtils.copyProperties(tpl, model);
			list.add(model);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrintTemplateServiceInterface#selectModulByFlowTypeAndPrcsId(int, int, int)
	 */
	@Override
	public List<TeeFlowPrintTemplateModel> selectModulByFlowTypeAndPrcsId(int flowId,int type,int flowPrcsSid) {
		List<TeeFlowPrintTemplateModel>  list  = new ArrayList<TeeFlowPrintTemplateModel>();
		List<TeeFlowPrintTemplate>  tplList  = tplDao.selectModulByFlowTypeAndPrcsId(flowId , type , flowPrcsSid);
		for (int i = 0; i < tplList.size(); i++) {
			TeeFlowPrintTemplate tpl = tplList.get(i);
			TeeFlowPrintTemplateModel model = new TeeFlowPrintTemplateModel();
			BeanUtils.copyProperties(tpl, model);
			list.add(model);
		}
		return list;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrintTemplateServiceInterface#selectModulByFlowRunPrcs(int)
	 */
	@Override
	public List<TeeFlowPrintTemplateModel> selectModulByFlowRunPrcs(int frpSid) {
		List<TeeFlowPrintTemplateModel>  list  = new ArrayList<TeeFlowPrintTemplateModel>();
		if(frpSid!=0){
			Map map = simpleDao.getMap("select frp.flowPrcs.sid as FLOWPRCS,frp.flowRun.flowType.sid as FLOWID,frp.flowRun.flowType.type as TYPE from TeeFlowRunPrcs frp where frp.sid="+frpSid, null);
			int flowPrcs = TeeStringUtil.getInteger(map.get("FLOWPRCS"), 0);
			int flowId = TeeStringUtil.getInteger(map.get("FLOWID"), 0);
			int type = TeeStringUtil.getInteger(map.get("TYPE"), 0);
			list = selectModulByFlowTypeAndPrcsId(flowId,type,flowPrcs);
		}
		return list;
	}
	
	

	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrintTemplateServiceInterface#renderTemplate(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson renderTemplate(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		int frpSid=TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
		
		TeeFlowRunPrcs frp=(TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class,frpSid);
		if(frp!=null){
			TeeFlowProcess  fp=frp.getFlowPrcs();
			//获取与当前流程类型关联的模板
			List<TeeFlowPrintTemplateModel> list=selectModulByFlowType(flowId+"");
			Map<String,List> returnMap=new HashMap<String, List>();
			if(frp.getTopFlag()==0){//经办
			   returnMap.put("cy",list);
			}else{//主办
				//查阅
				List<TeeFlowPrintTemplateModel> cyList=new ArrayList<TeeFlowPrintTemplateModel>();
				//签批
				List<TeeFlowPrintTemplateModel> qpList=new ArrayList<TeeFlowPrintTemplateModel>();
				for (TeeFlowPrintTemplateModel teeFlowPrintTemplateModel : list) {
				   String flowPrcsIds=teeFlowPrintTemplateModel.getFlowPrcsIds();
				   if(!TeeUtility.isNullorEmpty(flowPrcsIds)){//所属步骤不是空
					   String[] prcs=flowPrcsIds.split(",");
					   if(TeeStringUtil.existString(prcs, fp.getSid()+"")){//可签批
						   qpList.add(teeFlowPrintTemplateModel);
					   }else{
						   cyList.add(teeFlowPrintTemplateModel);
					   }
				   }else{//所属步骤是空的
					   cyList.add(teeFlowPrintTemplateModel);
				   }
				}
				
				
				returnMap.put("cy",cyList);
				returnMap.put("qp",qpList);
			}
			json.setRtState(true);
			json.setRtData(returnMap);
		}else{
			json.setRtState(false);
		}
		return json;
	}
	
	
	
}
