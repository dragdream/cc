package com.tianee.oa.core.base.fixedAssets.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetDeprecRecord;
import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetsCategory;
import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetsInfo;
import com.tianee.oa.core.base.fixedAssets.model.TeeFixedAssetsInfoModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeFixedAssetsInfoService extends TeeBaseService{
	
	public TeeFixedAssetsInfo addAssetsInfo(TeeFixedAssetsInfo assetsInfo){
		getSimpleDaoSupport().save(assetsInfo);
		return assetsInfo;
	}
	
	public TeeFixedAssetsInfo addAssetsInfoModel(TeeFixedAssetsInfoModel assetsInfoModel) throws Exception{
		TeeFixedAssetsInfo assetsInfo = new TeeFixedAssetsInfo();
		BeanUtils.copyProperties(assetsInfoModel, assetsInfo);
			TeeDepartment dept = (TeeDepartment)simpleDaoSupport.get(TeeDepartment.class,assetsInfoModel.getDeptId());
			assetsInfo.setDept(dept);
		TeePerson person = (TeePerson)simpleDaoSupport.get(TeePerson.class,assetsInfoModel.getKeeperId());
		TeeFixedAssetsCategory type = (TeeFixedAssetsCategory)simpleDaoSupport.get(TeeFixedAssetsCategory.class,assetsInfoModel.getTypeId());
		
		assetsInfo.setKeeper(person);
		assetsInfo.setCategory(type);
		assetsInfo.setAddKind(1);
		assetsInfo.setAssetKind(1);
		assetsInfo.setAssetVal(1);
		assetsInfo.setAssetBal(0.0);
		assetsInfo.setAssetKind(1);
		assetsInfo.setAssetYear(1);
		
		assetsInfo.setDepreciation(0);
		//assetsInfo.setAssetNum();
		assetsInfo.setScrapNum(0);
		assetsInfo.setAssetBalRate(0.0);
		if(assetsInfo.getAssetBal()==0){
			assetsInfo.setAssetBal(assetsInfo.getAssetVal());
		}
		
		String valideTimeDesc = assetsInfoModel.getValideTimeDesc();
		if(!TeeUtility.isNullorEmpty(valideTimeDesc)){
			Calendar cl = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			cl.setTime(formatter.parse(valideTimeDesc));
			assetsInfo.setValideTime(cl);
		}
		
		if(!TeeUtility.isNullorEmpty(assetsInfoModel.getReceiptDateStr())){//发票日期
			Date receiptDate = TeeUtility.parseDate("yyyy-MM-dd", assetsInfoModel.getReceiptDateStr());
			assetsInfo.setReceiptDate(receiptDate);
		}
		if(assetsInfoModel.getAttacheModels()!=null){
		
			List attaches = assetsInfoModel.getAttacheModels();
			addAssetsInfo(assetsInfo);
			System.out.println(attaches.size());
			
			for(int i=0;i<attaches.size();i++){
				TeeAttachment attach = (TeeAttachment) attaches.get(i);
				attach.setModelId(assetsInfo.getSid()+"");
				simpleDaoSupport.update(attach);
			}
		}
		getSimpleDaoSupport().save(assetsInfo);
		return assetsInfo;
	}
	
	public TeeFixedAssetsInfo deleteAssetsInfo(TeeFixedAssetsInfo assetsInfo){
		//删除与其有关的
		simpleDaoSupport.executeUpdate("delete from TeeFixedAssetDeprecRecord where asset.sid="+assetsInfo.getSid(), null);
		simpleDaoSupport.executeUpdate("delete from TeeFixedAssetsRecord where fixedAssets.sid="+assetsInfo.getSid(), null);
		getSimpleDaoSupport().deleteByObj(assetsInfo);
		return assetsInfo;
	}
	
	public void updateAssetsInfo(TeeFixedAssetsInfo assetsInfo){
		getSimpleDaoSupport().update(assetsInfo);
	}
	
	public void updateAssetsInfoModel(TeeFixedAssetsInfoModel assetsInfoModel) throws Exception{
		TeeFixedAssetsInfo assetsInfo = (TeeFixedAssetsInfo) simpleDaoSupport.get(TeeFixedAssetsInfo.class, assetsInfoModel.getSid());
		BeanUtils.copyProperties(assetsInfoModel, assetsInfo);
		TeeDepartment dept = (TeeDepartment)simpleDaoSupport.get(TeeDepartment.class,assetsInfoModel.getDeptId());
		TeePerson person = (TeePerson)simpleDaoSupport.get(TeePerson.class,assetsInfoModel.getKeeperId());
		TeeFixedAssetsCategory type = (TeeFixedAssetsCategory)simpleDaoSupport.get(TeeFixedAssetsCategory.class,assetsInfoModel.getTypeId());
		assetsInfo.setDept(dept);
		assetsInfo.setKeeper(person);
		assetsInfo.setCategory(type);
		if(assetsInfo.getAssetBal()==0){
			assetsInfo.setAssetBal(assetsInfo.getAssetVal());
		}
		String valideTimeDesc = assetsInfoModel.getValideTimeDesc();
		if(!TeeUtility.isNullorEmpty(valideTimeDesc)){
			Calendar cl = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			cl.setTime(formatter.parse(valideTimeDesc));
			assetsInfo.setValideTime(cl);
		}
		if(!TeeUtility.isNullorEmpty(assetsInfoModel.getReceiptDateStr())){//发票日期
			Date receiptDate = TeeUtility.parseDate("yyyy-MM-dd", assetsInfoModel.getReceiptDateStr());
			assetsInfo.setReceiptDate(receiptDate);
		}
		List attaches = assetsInfoModel.getAttacheModels();
		if(attaches!=null && attaches.size()>0){
			for(int i=0;i<attaches.size();i++){
				TeeAttachment attach = (TeeAttachment) attaches.get(i);
				attach.setModelId(assetsInfo.getSid()+"");
				simpleDaoSupport.update(attach);
			}
		}
		updateAssetsInfo(assetsInfo);
	}
	
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,Map requestDatas){
		String assetsCode =TeeStringUtil.getString(requestDatas.get("assetsCode"),"");
		String assetsName = TeeStringUtil.getString(requestDatas.get("assetsName"),"");
		int typeId = TeeStringUtil.getInteger(requestDatas.get("typeId"),0);
		int deptId = TeeStringUtil.getInteger(requestDatas.get("deptId"), 0);
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		String hql = "from TeeFixedAssetsInfo oc where 1=1 ";
		List param = new ArrayList();
		if(!TeeUtility.isNullorEmpty(assetsCode)){
			hql+=" and oc.assetCode like ?";
			param.add("%"+assetsCode+"%");
		}
		if(!TeeUtility.isNullorEmpty(assetsName)){
			hql+=" and oc.assetName like ?";
			param.add("%"+assetsName+"%");
		}
		if(typeId>0){
			hql+=" and oc.category.sid="+typeId;
		}
		if(deptId>0){
			hql+=" and oc.dept.uuid="+deptId;
		}
		hql+=" order by oc.sid asc";
		
		long total = simpleDaoSupport.countByList("select count(*) "+hql, param);
		
		List rows = new ArrayList();
		List<TeeFixedAssetsInfo> list = simpleDaoSupport.pageFindByList(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), param);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(TeeFixedAssetsInfo assetsInfo:list){
			TeeFixedAssetsInfoModel model = parseModel(assetsInfo);
			rows.add(model);
		}
		
		datagird.setRows(rows);
		datagird.setTotal(total);
		
		return datagird;
	}
	
	public TeeFixedAssetsInfo getById(int sid){
		TeeFixedAssetsInfo assetsInfo = (TeeFixedAssetsInfo) simpleDaoSupport.get(TeeFixedAssetsInfo.class, sid);
		return assetsInfo;
	}
	
	
	/**
	 * 获取固定资产  ---领用、返库
	 * @author syl
	 * @date 2014-6-8
	 * @param request
	 * @param dm
	 * @param model
	 * @return
	 */
	public TeeEasyuiDataGridJson getAssetsDatagrid(HttpServletRequest request, TeeDataGridModel dm,TeeFixedAssetsInfoModel model){
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String assetsCode =TeeStringUtil.getString(model.getAssetCode(),"");
		String assetsName = TeeStringUtil.getString(model.getAssetName(),"");
		String optType = TeeStringUtil.getString(request.getParameter("optType"),""); //获取类型 0-领用的  1-返库的  空为全部
		int typeId = model.getTypeId();
		int deptId = model.getDeptId();
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		String hql = "from TeeFixedAssetsInfo oc where 1=1 ";
		List param = new ArrayList();
		if(!TeePersonService.checkIsSuperAdmin(person, "") && optType.equals("1") ){//不是admin超级管理员
			hql+=" and oc.useUser.uuid = ?";
			param.add(person.getUuid());
		}
		if(optType.equals("0")){//领用
			hql+=" and (oc.useState = '0' or oc.useState is null)";
		}else if(optType.equals("1")){//返库
			hql+=" and oc.useState = '1' or oc.useState = '2' ";
		}else if(optType.equals("2")){//报修的
			hql+=" and oc.useState ='2'";
		}else if(optType.equals("3")){//报修返库
			hql+=" and oc.useState = ?";
			param.add("2");
		}else if(optType.equals("4")){//报废
			hql+=" and oc.useState <> ?";
			param.add("4");
		}
		if(!TeeUtility.isNullorEmpty(assetsCode)){
			hql+=" and oc.assetCode like ?";
			param.add("%"+assetsCode+"%");
		}
		if(!TeeUtility.isNullorEmpty(assetsName)){
			hql+=" and oc.assetName like ?";
			param.add("%"+assetsName+"%");
		}
		if(typeId>0){
			hql+=" and oc.category.sid="+typeId;
		}
		if(deptId>0){
			hql+=" and oc.dept.uuid="+deptId;
		}
		hql+=" order by oc.sid asc";
		
		long total = simpleDaoSupport.countByList("select count(*) "+hql, param);
		
		List rows = new ArrayList();
		List<TeeFixedAssetsInfo> list = simpleDaoSupport.pageFindByList(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), param);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(TeeFixedAssetsInfo assetsInfo:list){
			TeeFixedAssetsInfoModel newModel = parseModel(assetsInfo);
			rows.add(newModel);
		}
		
		datagird.setRows(rows);
		datagird.setTotal(total);
		
		return datagird;
	}
	
	/**
	 * 转化模型
	 * @author syl
	 * @date 2014-6-8
	 * @param assetsInfo
	 * @return
	 */
	public TeeFixedAssetsInfoModel parseModel(TeeFixedAssetsInfo assetsInfo){
		TeeFixedAssetsInfoModel model = new TeeFixedAssetsInfoModel();
		if(assetsInfo != null){
			BeanUtils.copyProperties(assetsInfo, model);
			if(assetsInfo.getDept()!=null){
				model.setDeptName(assetsInfo.getDept().getDeptName());
			}
			if(assetsInfo.getKeeper()!=null){
				model.setKeeperName(assetsInfo.getKeeper().getUserName());
			}
			if(assetsInfo.getCategory()!=null){
				model.setTypeName(assetsInfo.getCategory().getName());
			}
			if(assetsInfo.getValideTime()!=null){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				model.setValideTimeDesc(formatter.format(assetsInfo.getValideTime().getTime()));
			}
			//状态
			String useState = TeeStringUtil.getString(model.getUseState() , "0");
			String statusColor = "green";
			String optTypeDesc = "在库";
			if(useState.equals("0")){
				optTypeDesc = "在库";
				statusColor = "green";
			}else if(useState.equals("1")){
				optTypeDesc = "使用中";
				statusColor = "green";
			}else if(useState.equals("2")){
				optTypeDesc = "维修中";
				statusColor = "orange";
			}else if(useState.equals("4")){
				optTypeDesc = "已报废";
				statusColor = "red";
			}else if(useState.equals("5")){
				optTypeDesc = "已丢失";
				statusColor = "red";
			}
			String optTypeDesc2 = "<span style='padding:3px;background:"+statusColor+";color:#fff;'>"+ optTypeDesc+"</span>";
			model.setUseStateDesc(optTypeDesc2);
			String useUserId = "";//领用人
			String useUserName = "";
			if(assetsInfo.getUseUser() != null){
				useUserId = assetsInfo.getUseUser().getUuid() + "";
				useUserName = assetsInfo.getUseUser().getUserName();
			}
			model.setUseUserId(useUserId);
			model.setUseUserName(useUserName);
		}
		return model;
	}
	
	public void depreciate(TeeFixedAssetsInfo assetsInfo,TeePerson loginUser){
		//获取当月的最后一天
		Calendar lastDate = Calendar.getInstance();
		int lastDay = lastDate.getActualMaximum(Calendar.DAY_OF_MONTH);
		lastDate.set(Calendar.DAY_OF_MONTH, lastDay);
		lastDate.set(Calendar.HOUR_OF_DAY, 23);
		lastDate.set(Calendar.MINUTE, 59);
		lastDate.set(Calendar.SECOND, 59);
		
		//获取当月第一天
		Calendar firstDate = Calendar.getInstance();
		lastDate.set(Calendar.DAY_OF_MONTH, 1);
		lastDate.set(Calendar.HOUR_OF_DAY, 0);
		lastDate.set(Calendar.MINUTE, 0);
		lastDate.set(Calendar.SECOND, 0);
		
		//获取当前时间
		Calendar curDate = Calendar.getInstance();
		
		Calendar lastDeprec = assetsInfo.getLastDepreciation();
		double cz = 0;
		double yz = 0;
		double nzjl = 0;
		double yzjl = 0;
		double yzje = 0;
		
		//如果该月没有进行折旧
		if(lastDeprec==null 
				|| !( lastDeprec.after(firstDate) 
				&& lastDeprec.before(lastDate) )){
			
			Calendar valideTime = assetsInfo.getValideTime();//启用时间
			if(valideTime.before(curDate)){//如果启用时间开始生效的话，则开始折旧操作
				if(lastDeprec==null){
					lastDeprec = (Calendar) valideTime.clone();
				}
				
				nzjl = (1-assetsInfo.getAssetBalRate())/assetsInfo.getAssetYear();
				yzjl = nzjl/12;
				yzje = assetsInfo.getAssetVal()*yzjl;
				cz = assetsInfo.getAssetBal();//残值
				yz = assetsInfo.getAssetVal();
				
				BigDecimal bigDecimal = null;
				//从启用月份开始折旧，直到折旧到本月份
				for(;lastDeprec.compareTo(lastDate)!=1;lastDeprec.add(Calendar.MONTH, 1)){
					cz = cz-yzje;
					TeeFixedAssetDeprecRecord fadr = new TeeFixedAssetDeprecRecord();
					fadr.setStartTime(assetsInfo.getValideTime());
					fadr.setOriginal(assetsInfo.getAssetVal());
					fadr.setDeprecTime((Calendar)lastDeprec.clone());
					bigDecimal = new BigDecimal(yzje);
					fadr.setDeprecValue(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
					bigDecimal = new BigDecimal(cz);
					fadr.setDeprecRemainValue(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
					fadr.setCrTime(Calendar.getInstance());
					fadr.setOpUser(loginUser);
					if(loginUser==null){//自动折旧
						fadr.setFlag(1);
					}else{
						fadr.setFlag(2);
					}
					fadr.setAsset(assetsInfo);
					simpleDaoSupport.save(fadr);
				}
				
				assetsInfo.setAssetBal(cz);
				assetsInfo.setLastDepreciation(Calendar.getInstance());
				simpleDaoSupport.update(assetsInfo);
			}
			
		}
	}
	
	/**
	 * 固定资产折旧
	 * @param assetId
	 */
	public void depreciate(int assetId,TeePerson loginUser){
		TeeFixedAssetsInfo assetsInfo = (TeeFixedAssetsInfo) simpleDaoSupport.get(TeeFixedAssetsInfo.class, assetId);
		depreciate(assetsInfo,loginUser);
	}
	
	/**
	 * 固定资产折旧
	 * @param assetId
	 */
	public void depreciateBatch(TeePerson loginUser){
		List<TeeFixedAssetsInfo> list = simpleDaoSupport.find("from TeeFixedAssetsInfo", null);
		//逐步折旧
		for(TeeFixedAssetsInfo fixedAsset:list){
			depreciate(fixedAsset, null);
		}
	}
	
	/**
	 * 删除折旧记录
	 * @param assetId
	 */
	public void deleteAssetDeprecRecords(int assetId){
		String hql="delete from TeeFixedAssetDeprecRecord r where r.asset.sid="+assetId;
		simpleDaoSupport.executeUpdate(hql, null);
	}
	
	public TeeFixedAssetsInfo add(TeeFixedAssetsInfoModel assetsInfoModel) throws ParseException{
		TeeFixedAssetsInfo assetsInfo = new TeeFixedAssetsInfo();
		BeanUtils.copyProperties(assetsInfoModel, assetsInfo);
			TeeDepartment dept = (TeeDepartment)simpleDaoSupport.get(TeeDepartment.class,assetsInfoModel.getDeptId());
			assetsInfo.setDept(dept);
		TeePerson person = (TeePerson)simpleDaoSupport.get(TeePerson.class,assetsInfoModel.getKeeperId());
		TeeFixedAssetsCategory type = (TeeFixedAssetsCategory)simpleDaoSupport.get(TeeFixedAssetsCategory.class,assetsInfoModel.getTypeId());
		
		assetsInfo.setKeeper(person);
		assetsInfo.setCategory(type);
		
		if(assetsInfo.getAssetBal()==0){
			assetsInfo.setAssetBal(assetsInfo.getAssetVal());
		}
		
		String valideTimeDesc = assetsInfoModel.getValideTimeDesc();
		if(!TeeUtility.isNullorEmpty(valideTimeDesc)){
			Calendar cl = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			cl.setTime(formatter.parse(valideTimeDesc));
			assetsInfo.setValideTime(cl);
		}
		
		if(!TeeUtility.isNullorEmpty(assetsInfoModel.getReceiptDateStr())){//发票日期
			Date receiptDate = TeeUtility.parseDate("yyyy-MM-dd", assetsInfoModel.getReceiptDateStr());
			assetsInfo.setReceiptDate(receiptDate);
		}
		if(assetsInfoModel.getAttacheModels()!=null){
		
			List attaches = assetsInfoModel.getAttacheModels();
			addAssetsInfo(assetsInfo);
			System.out.println(attaches.size());
			
			for(int i=0;i<attaches.size();i++){
				TeeAttachment attach = (TeeAttachment) attaches.get(i);
				attach.setModelId(assetsInfo.getSid()+"");
				simpleDaoSupport.update(attach);
			}
		}
		getSimpleDaoSupport().save(assetsInfo);
		return assetsInfo;
	}
}
