package com.tianee.oa.core.base.fixedAssets.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetDeprecRecord;
import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetsInfo;
import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetsRecord;
import com.tianee.oa.core.base.fixedAssets.dao.TeeFixedAssetsInfoDao;
import com.tianee.oa.core.base.fixedAssets.dao.TeeFixedAssetsRecordDao;
import com.tianee.oa.core.base.fixedAssets.model.TeeFixedAssetDeprecRecordModel;
import com.tianee.oa.core.base.fixedAssets.model.TeeFixedAssetsInfoModel;
import com.tianee.oa.core.base.fixedAssets.model.TeeFixedAssetsRecordModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeFixedAssetsRecordService  extends TeeBaseService{

	@Autowired
	private TeeFixedAssetsRecordDao assetsRecordDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeDeptDao deptDao;
	
	@Autowired
	private TeeFixedAssetsInfoDao assetsInfoDao;
	
	@Autowired
	private TeeBaseUpload upload;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	/**
	 * 新增或者更新
	 * @author syl
	 * @date 2014-6-7
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeFixedAssetsRecordModel model) throws IOException {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		
		TeeFixedAssetsRecord record = new TeeFixedAssetsRecord();
/*		MultipartHttpServletRequest multipartRequest;
		List<TeeAttachment> attachments = new ArrayList();
		try {
			multipartRequest = (MultipartHttpServletRequest) request;
			attachments = upload.manyAttachUpload(multipartRequest, TeeAttachmentModelKeys.assetsRecord);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		BeanUtils.copyProperties(model, record);
		int gixedAssetsId = TeeStringUtil.getInteger(model.getFixedAssetsId()  ,0);
		TeeFixedAssetsInfo fixedAssets  = assetsInfoDao.getById(gixedAssetsId);
		record.setFixedAssets(fixedAssets);
		int deptId  =  TeeStringUtil.getInteger(model.getDeptId(), 0);//申请部门
		int userId  =  TeeStringUtil.getInteger(model.getUserId(), 0);//申请人
		if(deptId > 0){
			TeeDepartment dept = deptDao.selectDeptById(deptId);
			record.setDept(dept);
		}
		if(userId > 0){
			TeePerson user = personDao.selectPersonById(userId);
			record.setUser(user);
		}
		record.setUser(person);
		if(model.getSid() > 0){
			TeeFixedAssetsRecord oldRecord  = assetsRecordDao.getById(model.getSid());
			if(oldRecord != null){
			/*	for(TeeAttachment atta:attachments){
					atta.setModelId(String.valueOf(oldRecord.getSid()));
					simpleDaoSupport.update(atta);
				}*/
				BeanUtils.copyProperties(record, oldRecord);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关领用信息！");
				return json;
			}
		}else{
			record.setOptDate(new Date());
			assetsRecordDao.add(record);
	/*		for(TeeAttachment atta:attachments){
				atta.setModelId(String.valueOf(record.getSid()));
				simpleDaoSupport.update(atta);
			}*/
		}
		
		if(model.getOptType().equals("0")){//领用
			fixedAssets.setUseUser(record.getUser());
			fixedAssets.setUseState("1");
			assetsInfoDao.edit(fixedAssets);
		}else if(model.getOptType().equals("1")){//返库
			fixedAssets.setUseUser(null);
			fixedAssets.setUseState("0");
			assetsInfoDao.edit(fixedAssets);
		}else if(model.getOptType().equals("2")){//报修
			fixedAssets.setUseState("2");
			assetsInfoDao.edit(fixedAssets);
		}else if(model.getOptType().equals("3")){//报修返库
			fixedAssets.setUseState("0");
			assetsInfoDao.edit(fixedAssets);
		}else if(model.getOptType().equals("4")){//报废
			fixedAssets.setUseState("4");
			assetsInfoDao.edit(fixedAssets);
		}
		
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
	
		return json;
	}
	

	/**
	 * 根据Id 
	 * @author syl
	 * @date 2014-6-7
	 * @param request
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeFixedAssetsRecord record  = assetsRecordDao.getById(sid);
		TeeFixedAssetsRecordModel model  = parseModel(record);
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据固定资产查询领用或者返库记录列表
	 * @author syl
	 * @date 2014-6-7
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson selectByAssetsId(HttpServletRequest request , TeeFixedAssetsRecordModel model) {
		TeeJson json = new TeeJson();
		int assetsId = TeeStringUtil.getInteger(model.getFixedAssetsId(), 0);
		List<TeeFixedAssetsRecord> recordList  = assetsRecordDao.selectByAssetsId(assetsId , model.getOptType());
		List<TeeFixedAssetsRecordModel> modelList = new ArrayList<TeeFixedAssetsRecordModel>();
		for (int i = 0; i < recordList.size(); i++) {
			TeeFixedAssetsRecordModel model2 =  parseModel(recordList.get(i));
			modelList.add(model2);
		}
		
		json.setRtData(modelList);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取固定资产折旧记录
	 * @param assetId
	 * @return
	 */
	public List<TeeFixedAssetDeprecRecordModel> selectDeprecRecordsByAssetsId(int assetId) {
		List<TeeFixedAssetDeprecRecord> list = simpleDaoSupport.find("from TeeFixedAssetDeprecRecord where asset.sid="+assetId+" order by sid asc", null);
		List<TeeFixedAssetDeprecRecordModel> modelList = new ArrayList<TeeFixedAssetDeprecRecordModel>();
		
		for(TeeFixedAssetDeprecRecord record:list){
			TeeFixedAssetDeprecRecordModel m = new TeeFixedAssetDeprecRecordModel();
			BeanUtils.copyProperties(record, m);
			if(record.getOpUser()==null){
				m.setUserName("");
			}else{
				m.setUserName(record.getOpUser().getUserName());
			}
			modelList.add(m);
		}
		
		return modelList;
	}
	
	/**
	 * 维修确认
	 * @param assetId
	 * @return
	 */
	public void repairConfirm(int recordId) {
		TeeFixedAssetsRecord record = 
				(TeeFixedAssetsRecord) simpleDaoSupport.get(TeeFixedAssetsRecord.class, recordId);
		
		record.setRepairConfirm(1);
		record.getFixedAssets().setUseState("0");
	}
	
	/**
	 * 转换模型
	 * @author syl
	 * @date 2014-6-7
	 * @param record
	 * @return
	 */
	public TeeFixedAssetsRecordModel parseModel(TeeFixedAssetsRecord record){
		TeeFixedAssetsRecordModel model = new TeeFixedAssetsRecordModel();
		if(record != null){
			BeanUtils.copyProperties(record, model);
			String deptId = "";
			String deptName = "";
			String userId = "";
			String userName = "";
			if(record.getDept() != null){
				deptId = record.getDept().getUuid() + "";
				deptName = record.getDept().getDeptName();
			}
			
			if(record.getUser() != null){
				userId = record.getUser().getUuid() + "";
				userName = record.getUser().getUserName();
			}
			
			model.setDeptId(deptId);
			model.setDeptName(deptName);
			model.setUserId(userId);
			model.setUserName(userName);
			model.setFixedAssetsId(record.getFixedAssets().getSid() + "");		
			model.setFixedAssetsName(record.getFixedAssets().getAssetName());	
			model.setAssetCode(record.getFixedAssets().getAssetCode());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			model.setOptDateStr(TeeUtility.getDateTimeStr(record.getOptDate(), formatter));
			
			String optTypeDesc = "";
			String optType = TeeStringUtil.getString(record.getOptType(),"0");//操作类型  0 - 领用   1- 返库 2-报修  3-报修返库  - 4 报废
			if(optType.equals("0")){
				optTypeDesc = "领用";
			}else if(optType.equals("1")){
				optTypeDesc = "返库";
			}else if(optType.equals("2")){
				optTypeDesc = "报修";
			}else if(optType.equals("3")){
				optTypeDesc = "维修确认";
			}else if(optType.equals("4")){
				optTypeDesc = "报废";
			}
			model.setOptTypeDesc(optTypeDesc);
			List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.assetsRecord, String.valueOf(record.getSid()));
			List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
			for(TeeAttachment attach:attaches){
				TeeAttachmentModel m = new TeeAttachmentModel();
				BeanUtils.copyProperties(attach, m);
				m.setUserId(attach.getUser().getUuid()+"");
				m.setUserName(attach.getUser().getUserName());
				m.setPriv(1+2+8+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
				attachmodels.add(m);
			}
			model.setAttacheModels(attachmodels);
		}
		return model;
	}
	
	
	
	
	/**
	 * 获取固定资产 -- 领用、返库、维护、报废
	 * @author syl
	 * @date 2014-6-8
	 * @param request
	 * @param dm
	 * @param model
	 * @return
	 */
	public TeeEasyuiDataGridJson assetsRecordService(HttpServletRequest request, TeeDataGridModel dm,TeeFixedAssetsInfoModel model){
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String assetsCode =TeeStringUtil.getString(model.getAssetCode(),"");
		String assetsName = TeeStringUtil.getString(model.getAssetName(),"");
		String optType = TeeStringUtil.getString(request.getParameter("optType"),""); //获取类型 0-领用的  1-返库的  空为全部
		int userId = TeeStringUtil.getInteger(request.getParameter("userId"),0); 
		int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"),0); 
		int priv =  TeeStringUtil.getInteger(request.getParameter("priv"),0); 
		String time1 = TeeStringUtil.getString(request.getParameter("time1"));
		String time2 = TeeStringUtil.getString(request.getParameter("time2"));
		
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		String hql = "from TeeFixedAssetsRecord temp where 1=1 ";
		List param = new ArrayList();
		
		if(priv==1){//进行权限校验
			if(!TeePersonService.checkIsSuperAdmin(person, "")  ){//不是admin超级管理员
				hql+=" and temp.user.uuid = ?";
				param.add(person.getUuid());
			}
		}
		
		if(optType.equals("0")){//领用
			hql+=" and temp.optType = '0'";//领用
		}else if(optType.equals("1")){//返库
			hql+=" and temp.optType = '1' ";
		}else if(optType.equals("2")){//报修的
			hql+=" and (temp.optType = '2')";
		}else if(optType.equals("3")){//报修返库
			hql+=" and temp.optType = '3' ";
		}else if(optType.equals("4")){//报废
			hql+=" and temp.optType = '4' ";
		}else if(optType.equals("5")){//维修待确认
			hql+=" and temp.optType = '2' and temp.repairConfirm=0";
		}
		if(!TeeUtility.isNullorEmpty(assetsCode)){
			hql+=" and temp.fixedAssets.assetCode like ?";
			param.add("%"+assetsCode+"%");
		}
		if(!TeeUtility.isNullorEmpty(assetsName)){
			hql+=" and temp.fixedAssets.assetName like ?";
			param.add("%"+assetsName+"%");
		}
		if(deptId>0){
			hql+=" and temp.dept.uuid="+ deptId;
		}
		if(userId>0){
			hql+=" and temp.user.uuid= "+ userId ;
		}
		
		if(!"".equals(time1)){
			hql+=" and temp.optDate >= ?" ;
			param.add(TeeDateUtil.parseCalendar("yyyy-MM-dd HH:mm", time1+" 00:00").getTime());
		}
		
		if(!"".equals(time2)){
			hql+=" and temp.optDate <= ?" ;
			param.add(TeeDateUtil.parseCalendar("yyyy-MM-dd HH:mm", time2+" 23:59").getTime());
		}
		
		
		long total = simpleDaoSupport.countByList("select count(*) "+hql, param);
		
		hql+=" order by temp.sid desc";
		
		
		List rows = new ArrayList();
		List<TeeFixedAssetsRecord> list = simpleDaoSupport.pageFindByList(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), param);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for(TeeFixedAssetsRecord record:list){
			Map map = getMap(record);
			rows.add(map);
		}
		
		datagird.setRows(rows);
		datagird.setTotal(total);
		
		return datagird;
	}
	/**
	 * 转Map
	 * @author syl
	 * @date 2014-6-8
	 * @param assetsInfo
	 * @return
	 */
	public Map getMap(TeeFixedAssetsRecord record){
		Map map = new HashMap();
		map.put("runId", record.getRunId());
		map.put("sid", record.getSid());
		String deptId = "";
		String deptName = "";
		String userId = "";
		String userName = "";
		if(record.getDept() != null){
			deptId = record.getDept().getUuid() + "";
			deptName = record.getDept().getDeptName();
		}
		
		if(record.getUser() != null){
			userId = record.getUser().getUuid() + "";
			userName = record.getUser().getUserName();
		}
		map.put("deptId",deptId);
		map.put("deptName",deptName);
		map.put("userId",userId);
		map.put("userName",userName);
		if(record.getFixedAssets()!=null){
			map.put("fixedAssetsId",record.getFixedAssets().getSid());
			map.put("fixedAssetsName",record.getFixedAssets().getAssetName());
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		map.put("optDateStr",TeeUtility.getDateTimeStr(record.getOptDate(), formatter));
		String optTypeDesc = "";
		String optType = TeeStringUtil.getString(record.getOptType(),"0");//操作类型  0 - 领用   1- 返库 2-报修  3-报修返库  - 4 报废
		if(optType.equals("0")){
			optTypeDesc = "领用";
		}else if(optType.equals("1")){
			optTypeDesc = "返库";
		}else if(optType.equals("2")){
			optTypeDesc = "报修";
		}else if(optType.equals("3")){
			optTypeDesc = "维修确认";
		}else if(optType.equals("4")){
			optTypeDesc = "报废";
		}
		map.put("optTypeDesc",optTypeDesc);
		if(record.getFixedAssets()!=null){
			map.put("assetCode",record.getFixedAssets().getAssetCode());
			map.put("assetName",record.getFixedAssets().getAssetName());
		}
		String typeName = "";
		if(record.getFixedAssets()!=null){
			if(record.getFixedAssets().getCategory()!=null){
				typeName = record.getFixedAssets().getCategory().getName();
			}
		}
		
		map.put("typeName",typeName);
		map.put("optReason",record.getOptReason());
		map.put("optDateDesc",formatter.format(record.getOptDate()));
		map.put("runId",record.getRunId());
		map.put("repairUser",record.getRepairUser());
		map.put("repairCost",record.getRepairCost());
		return map;
	}
	
}
