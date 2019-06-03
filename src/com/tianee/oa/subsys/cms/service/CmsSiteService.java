package com.tianee.oa.subsys.cms.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.cms.bean.SiteInfo;
import com.tianee.oa.subsys.cms.model.SiteInfoModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.thread.TeeRequestInfo;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class CmsSiteService extends TeeBaseService{
	
	public void addSiteInfo(SiteInfo siteInfo){
		TeeRequestInfo requestInfo = TeeRequestInfoContext.getRequestInfo();
		
		siteInfo.setCreateUser(requestInfo.getUserSid());
		siteInfo.setCreateUserName(requestInfo.getUserId());
		siteInfo.setCrTime(Calendar.getInstance());
		
		simpleDaoSupport.save(siteInfo);
	}
	
	public SiteInfoModel getSiteInfoModelByIdentity(String id){
		SiteInfo siteInfo = (SiteInfo) simpleDaoSupport.unique("from SiteInfo si where si.siteIdentity=?", new Object[]{id});
		if(siteInfo==null){
			return null;
		}
		SiteInfoModel infoModel = new SiteInfoModel();
		BeanUtils.copyProperties(siteInfo, infoModel);
		infoModel.setCrTimeDesc(TeeDateUtil.format(siteInfo.getCrTime()));
		return infoModel;
	}
	
	public void updateSiteInfo(SiteInfo siteInfo){
		SiteInfo entity = (SiteInfo) simpleDaoSupport.get(SiteInfo.class, siteInfo.getSid());
		entity.setDetailTpl(siteInfo.getDetailTpl());
		entity.setFolder(siteInfo.getFolder());
		entity.setIndexTpl(siteInfo.getIndexTpl());
		entity.setPubStatus(siteInfo.getPubStatus());
		entity.setSiteName(siteInfo.getSiteName());
		entity.setSortNo(siteInfo.getSortNo());
		entity.setSiteIdentity(siteInfo.getSiteIdentity());
		entity.setPubFileExt(siteInfo.getPubFileExt());
		entity.setContextPath(siteInfo.getContextPath());
		
		simpleDaoSupport.update(entity);
	}
	
	public void delSiteInfo(SiteInfo siteInfo){
		simpleDaoSupport.deleteByObj(siteInfo);
	}
	
	/**
	 * 获取站点信息
	 * @param siteId
	 * @return
	 */
	public SiteInfoModel getSiteInfo(int siteId){
		SiteInfo siteInfo = (SiteInfo) simpleDaoSupport.get(SiteInfo.class, siteId);
		SiteInfoModel infoModel = new SiteInfoModel();
		BeanUtils.copyProperties(siteInfo, infoModel);
		infoModel.setCrTimeDesc(TeeDateUtil.format(siteInfo.getCrTime()));
		return infoModel;
	}
	
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,SiteInfo siteInfo){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from SiteInfo s order by s.sortNo asc";
		List<SiteInfo> list = simpleDaoSupport.pageFind(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		dataGridJson.setRows(list);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
}
