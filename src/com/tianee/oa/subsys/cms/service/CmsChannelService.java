package com.tianee.oa.subsys.cms.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.oa.subsys.cms.bean.ChannelInfoExt;
import com.tianee.oa.subsys.cms.bean.SiteInfo;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.thread.TeeRequestInfo;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class CmsChannelService extends TeeBaseService{
	@Autowired
	private TeeAttachmentDao attachmentDao;

	public void setAttachmentDao(TeeAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	public TeeAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}
	/**
	 * 添加栏目
	 * @param channelInfo
	 */
	public void addChannelInfo(ChannelInfo channelInfo){
		TeeRequestInfo info = TeeRequestInfoContext.getRequestInfo();
		channelInfo.setCreateTime(Calendar.getInstance());
		channelInfo.setCreateUserId(info.getUserSid());
		channelInfo.setCreateUserName(info.getUserId());
		
		//判断是否存在重复标识
		ChannelInfo existObj = getChannelByIdentity(channelInfo.getChnlIdentity());
		if(existObj!=null){
			throw new TeeOperationException("已存在标识为("+channelInfo.getChnlIdentity()+")的栏目，请更换其他标识");
		}
		
		//获取父级栏目信息
		ChannelInfo parentChannel = (ChannelInfo) simpleDaoSupport.unique("from ChannelInfo ci where ci.sid="+channelInfo.getParentChnl(), null);
		simpleDaoSupport.save(channelInfo);
		
		//栏目路径
		if(parentChannel==null){
			channelInfo.setPath("/"+channelInfo.getSid()+".ch");
		}else{
			channelInfo.setPath(parentChannel.getPath()+"/"+channelInfo.getSid()+".ch");
		}
		simpleDaoSupport.update(channelInfo);
	}
	
	/**
	 * 获取栏目信息
	 * @param sid
	 * @return
	 */
	public ChannelInfo getChannelInfo(int channelId){
		return (ChannelInfo) simpleDaoSupport.get(ChannelInfo.class, channelId);
	}
	
	/**
	 * 获取顶级栏目
	 * @param siteId
	 * @param top
	 * @return
	 */
	public List<ChannelInfo> getTopChannels(int siteId,int top){
		return simpleDaoSupport.pageFind("from ChannelInfo ci where ci.siteId="+siteId+" and ci.parentChnl=0 and ci.delFlag=0 order by ci.sortNo asc", 0, top, null);
	}
	
	public List<ChannelInfo> getChannelsBySite(int siteId){
		return simpleDaoSupport.pageFind("from ChannelInfo ci where ci.siteId="+siteId+" and ci.delFlag=0 order by ci.sortNo asc", 0, Integer.MAX_VALUE, null);
	}
	
	public List<ChannelInfo> getChannelsBySiteSimples(int siteId){
		return simpleDaoSupport.pageFind("select new ChannelInfo(sid,chnlName) from ChannelInfo ci where ci.siteId="+siteId+" and ci.delFlag=0 order by ci.sortNo asc", 0, Integer.MAX_VALUE, null);
	}
	
	public List<ChannelInfo> getChildChannelsByChnlIdSimples(int chnlId){
		ChannelInfo channelInfo = (ChannelInfo) simpleDaoSupport.get(ChannelInfo.class, chnlId);
		return simpleDaoSupport.pageFind("select new ChannelInfo(sid,chnlName) from ChannelInfo ci where ci.path like '"+channelInfo.getPath()+"%' and sid!="+chnlId+" and ci.delFlag=0 order by ci.sortNo asc", 0, Integer.MAX_VALUE, null);
	}
	
	/**
	 * 获取某个栏目的子栏目
	 * @param channelId
	 * @param top
	 * @return
	 */
	public List<ChannelInfo> getChildChannels(int channelId,int top){
		return simpleDaoSupport.pageFind("from ChannelInfo ci where ci.parentChnl="+channelId+" and ci.delFlag=0 order by ci.sortNo asc", 0, top, null);
	}
	
	/**
	 * 获取某几个栏目
	 * @param channelId
	 * @param top
	 * @return
	 */
	public List<ChannelInfo> getChannelsByIds(String ids,int top){
		String newids = "";
		String sp[] = ids.split(",");
		for(String tmp:sp){
			newids+="'"+tmp+"',";
		}
		newids = newids.substring(0,newids.length()-1);
		return simpleDaoSupport.pageFind("from ChannelInfo ci where ci.chnlIdentity in ("+newids+") and ci.delFlag=0 order by ci.sortNo asc", 0, top, null);
	}
	
	/**
	 * 获取栏目绝对路径
	 * @param channelId
	 * @return
	 */
	public String getChannelAbsolutePath(int channelId){
		String path = "";
		ChannelInfo channelInfo = getChannelInfo(channelId);
		SiteInfo siteInfo = (SiteInfo) simpleDaoSupport.get(SiteInfo.class, channelInfo.getSiteId());
		path+="/"+siteInfo.getFolder();
		
		String tmps[] = channelInfo.getPath().split("/");
		for(String tmp:tmps){
			if("".equals(tmp)){
				continue;
			}
			ChannelInfo ci = getChannelInfo(Integer.parseInt(tmp.split("\\.")[0]));
			path+="/"+ci.getFolder();
		}
		
		return path;
	}
	
	public String getChannelRootPath(int channelId){
		String path = "";
		ChannelInfo channelInfo = getChannelInfo(channelId);
		if(channelInfo==null){
			return "./";
		}
		String tmps[] = channelInfo.getPath().split("/");
		for(String tmp:tmps){
			if("".equals(tmp)){
				continue;
			}
			path+="../";
		}
		
		return path;
	}
	
	/**
	 * 更新栏目基础信息
	 * @param channelInfo
	 */
	public void updateChannelInfo(ChannelInfo channelInfo){
		ChannelInfo entity = (ChannelInfo) simpleDaoSupport.get(ChannelInfo.class, channelInfo.getSid());
		
		//判断是否存在重复标识
		ChannelInfo existObj = getChannelByIdentity(channelInfo.getChnlIdentity());
		if(existObj!=null && !channelInfo.getChnlIdentity().equals(entity.getChnlIdentity())){
			throw new TeeOperationException("已存在标识为("+channelInfo.getChnlIdentity()+")的栏目，请更换其他标识");
		}
		
		BeanUtils.copyProperties(channelInfo, entity, new String[]{"path","createUserId","createUserName","createTime","parentChnl"});
		simpleDaoSupport.update(entity);
	}
	
	/**
	 * 实际删除栏目，级联删除子栏目
	 * @param channelInfo
	 */
	public void delChannelInfo(ChannelInfo channelInfo){
		ChannelInfo entity = (ChannelInfo) simpleDaoSupport.get(ChannelInfo.class, channelInfo.getSid());
		simpleDaoSupport.executeUpdate("delete from ChannelInfo ci where ci.path like '"+entity.getPath()+"%'", null);
	}
	
	/**
	 * 移至回收站
	 * @param channelInfo
	 */
	public void moveToTrash(ChannelInfo channelInfo){
		ChannelInfo entity = (ChannelInfo) simpleDaoSupport.get(ChannelInfo.class, channelInfo.getSid());
		entity.setDelFlag(1);
	}
	
	/**
	 * 从回收站还原
	 * @param channelInfo
	 */
	public void recycle(ChannelInfo channelInfo){
		ChannelInfo entity = (ChannelInfo) simpleDaoSupport.get(ChannelInfo.class, channelInfo.getSid());
		entity.setDelFlag(0);
	}
	
	/**
	 * 移动栏目
	 * @param channelInfo
	 */
	public void moveChannel(ChannelInfo channelInfo){
		ChannelInfo entity = (ChannelInfo) simpleDaoSupport.get(ChannelInfo.class, channelInfo.getSid());
		boolean changedChnl = false;//是否改变了栏目层级结构
		if(channelInfo.getParentChnl()!=entity.getParentChnl()){
			changedChnl = true;
		}
		
		if(changedChnl){//更新该栏目及下属栏目的path路径
			ChannelInfo parentChannel = (ChannelInfo) simpleDaoSupport.unique("from ChannelInfo ci where ci.sid="+channelInfo.getParentChnl(), null);
			
			List<ChannelInfo> chnlList = simpleDaoSupport.find("from ChannelInfo ci where ci.path like '"+entity.getPath()+"/%'", null);
			//新栏目的path路径
			String newPath = null;
			if(parentChannel==null){
				newPath = "/"+entity.getSid()+".ch";
			}else{
				newPath = parentChannel.getPath()+"/"+entity.getSid()+".ch";
			}
			//批量更新下属栏目的path
			for(ChannelInfo tmp:chnlList){
				tmp.setPath(tmp.getPath().replace(entity.getPath(), newPath));
				simpleDaoSupport.update(tmp);
			}
			//然后更新当前栏目的path和parentChnl
			entity.setParentChnl(channelInfo.getParentChnl());
			entity.setPath(newPath);
			simpleDaoSupport.update(entity);
		}
	}
	
	public void clearTrash(int siteId,int channelId){
		simpleDaoSupport.executeUpdate("delete from ChannelInfo where siteId=? and parentChnl=? and delFlag=1", new Object[]{siteId,channelId});
	}
	
	public Map listChannels(int siteId,int channelId,TeePerson loginUser){
		Map data = new HashMap();
		List<ChannelInfo> chnls = null;
		
		if(TeePersonService.checkIsAdminPriv(loginUser)){
			chnls=simpleDaoSupport.find(
					"from ChannelInfo ci where ci.siteId=" + siteId
					+ " and ci.parentChnl=" + channelId
					+ " and ci.delFlag=0  order by ci.sortNo asc", null);		
		}else{
		
			chnls=simpleDaoSupport.find(
				"from ChannelInfo ci where ci.siteId=" + siteId
						+ " and ci.parentChnl=" + channelId
						+ " and ci.delFlag=0 and ci.privUserIds like '"+"%,"+loginUser.getUuid()+",%"+"'order by ci.sortNo asc", null);		
		}
		
		
		
		data.put("list", chnls);
		//获取路径
		String path = "";
		String idPath = "";
		
		SiteInfo site = (SiteInfo) simpleDaoSupport.get(SiteInfo.class, siteId);
		
		path = "/"+site.getSiteName();
		idPath = "/"+site.getSid()+".st";
		
		if(channelId!=0){//只取出站点的路径来
			ChannelInfo chnl = (ChannelInfo) simpleDaoSupport.get(ChannelInfo.class, channelId);
			idPath += chnl.getPath();
			
			//拼接名称路径
			String sp[] = chnl.getPath().split("/");
			for(String id:sp){
				if(!"".equals(id)){
					chnl = (ChannelInfo) simpleDaoSupport.get(ChannelInfo.class, TeeStringUtil.getInteger(id.split("\\.")[0], 0));
					path+="/"+chnl.getChnlName();
				}
			}
		}
		
		data.put("path", path);
		data.put("idPath", idPath);
		
		return data;
	}
	
	public Map listTrashChannels(int siteId,int channelId){
		Map data = new HashMap();
		List<ChannelInfo> chnls = simpleDaoSupport.find("from ChannelInfo ci where ci.siteId="+siteId+" and ci.parentChnl="+channelId+" and ci.delFlag=1 order by ci.sortNo asc", null);
		data.put("list", chnls);
		//获取路径
		String path = "";
		String idPath = "";
		
		SiteInfo site = (SiteInfo) simpleDaoSupport.get(SiteInfo.class, siteId);
		
		path = "/"+site.getSiteName();
		idPath = "/"+site.getSid()+".st";
		
		if(channelId!=0){//只取出站点的路径来
			ChannelInfo chnl = (ChannelInfo) simpleDaoSupport.get(ChannelInfo.class, channelId);
			idPath += chnl.getPath();
			
			//拼接名称路径
			String sp[] = chnl.getPath().split("/");
			for(String id:sp){
				if(!"".equals(id)){
					chnl = (ChannelInfo) simpleDaoSupport.get(ChannelInfo.class, TeeStringUtil.getInteger(id.split("\\.")[0], 0));
					path+="/"+chnl.getChnlName();
				}
			}
		}
		
		data.put("path", path);
		data.put("idPath", idPath);
		
		return data;
	}
	
	public ChannelInfo getChannelByIdentity(String identity){
		return (ChannelInfo) simpleDaoSupport.unique("from ChannelInfo where chnlIdentity = ?", new Object[]{identity});
	}

	/**
	 * 复制栏目
	 * @param channelInfo
	 */
	public void copyChannel(ChannelInfo channelInfo) {
		ChannelInfo entity = (ChannelInfo) simpleDaoSupport.get(ChannelInfo.class, channelInfo.getSid());
		boolean changedChnl = false;//是否改变了栏目层级结构
		if(channelInfo.getParentChnl()!=entity.getParentChnl()){
			changedChnl = true;
		}
		
		if(changedChnl){//更新该栏目及下属栏目的path路径
			ChannelInfo parentChannel = (ChannelInfo) simpleDaoSupport.unique("from ChannelInfo ci where ci.sid="+channelInfo.getParentChnl(), null);
			
			List<ChannelInfo> chnlList = simpleDaoSupport.find("from ChannelInfo ci where ci.path like '"+entity.getPath()+"/%'", null);
			
			ChannelInfo newChannel = new ChannelInfo();
			BeanUtils.copyProperties(entity, newChannel);
			//复制栏目
			simpleDaoSupport.save(newChannel);
			
			
			//新栏目的path路径
			String newPath = null;
			if(parentChannel==null){
				newPath = "/"+newChannel.getSid()+".ch";
			}else{
				newPath = parentChannel.getPath()+"/"+newChannel.getSid()+".ch";
			}
			
		/*	//批量更新下属栏目的path
			for(ChannelInfo tmp:chnlList){
				ChannelInfo newTmp = new ChannelInfo();
				BeanUtils.copyProperties(tmp, newTmp);
				newTmp.setPath(tmp.getPath().replace(entity.getPath(), newPath));
				simpleDaoSupport.save(newTmp);
				
				String oldPath = newTmp.getPath();
				String[] oldPaths = oldPath.split("/");
				String oldSid = oldPaths[oldPaths.length-1];
				newTmp.setPath(newTmp.getPath().replace(oldSid, newTmp.getSid()+".ch"));
				String parentPath = oldPaths[oldPaths.length-2];
				int pid = Integer.parseInt(parentPath.split("\\.")[0]);
				newTmp.setParentChnl(pid);
				simpleDaoSupport.update(newTmp);
			}*/
			dealChildChannel(entity.getSid(),newPath);
			
			newChannel.setParentChnl(channelInfo.getParentChnl());
			newChannel.setPath(newPath);
			newChannel.setChnlIdentity(entity.getChnlIdentity()+(int)((Math.random()*9+1)*100000));
			simpleDaoSupport.update(newChannel);
		}
		
	}
	
	public void dealChildChannel(int channelId,String newPath){
		ChannelInfo entity = (ChannelInfo) simpleDaoSupport.get(ChannelInfo.class,channelId);
		List<ChannelInfo> childList = this.getChildChannels(channelId, Integer.MAX_VALUE);
		for(ChannelInfo tmp:childList){
			ChannelInfo newTmp = new ChannelInfo();
			BeanUtils.copyProperties(tmp, newTmp);
			newTmp.setPath(tmp.getPath().replace(entity.getPath(), newPath));
			newTmp.setChnlIdentity(tmp.getChnlIdentity()+(int)((Math.random()*9+1)*100000));
			simpleDaoSupport.save(newTmp);
			String oldPath = newTmp.getPath();
			String[] oldPaths = oldPath.split("/");
			String oldSid = oldPaths[oldPaths.length-1];
			newTmp.setPath(newTmp.getPath().replace(oldSid, newTmp.getSid()+".ch"));
			String parentPath = oldPaths[oldPaths.length-2];
			int pid = Integer.parseInt(parentPath.split("\\.")[0]);
			newTmp.setParentChnl(pid);
			simpleDaoSupport.update(newTmp);
			//递归处理下级栏目
			String path = newTmp.getPath();
			dealChildChannel(tmp.getSid(),path);
			
		}
		
	}

	public TeeEasyuiDataGridJson listChannelDataGrid(TeeDataGridModel dm,
			int siteId, int channelId, TeePerson loginUser) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql="";
		if(TeePersonService.checkIsAdminPriv(loginUser)){
			hql="from ChannelInfo ci where ci.siteId=" + siteId
					+ " and ci.parentChnl=" + channelId
					+ " and ci.delFlag=0  order by ci.sortNo asc";		
		}else{
		    hql="from ChannelInfo ci where ci.siteId=" + siteId
						+ " and ci.parentChnl=" + channelId
						+ " and ci.delFlag=0 and ci.privUserIds like '"+"%,"+loginUser.getUuid()+",%"+"'order by ci.sortNo asc";		
		}
		
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql,null));// 设置总记录数
		
		
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<ChannelInfo> chnls = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查
		
		j.setRows(chnls);

		List<String> footer=new ArrayList<String>();
		//获取路径
		String path = "";
		String idPath = "";
		
		SiteInfo site = (SiteInfo) simpleDaoSupport.get(SiteInfo.class, siteId);
		
		path = "/"+site.getSiteName();
		idPath = "/"+site.getSid()+".st";
		
		if(channelId!=0){//只取出站点的路径来
			ChannelInfo chnl = (ChannelInfo) simpleDaoSupport.get(ChannelInfo.class, channelId);
			idPath += chnl.getPath();
			
			//拼接名称路径
			String sp[] = chnl.getPath().split("/");
			for(String id:sp){
				if(!"".equals(id)){
					chnl = (ChannelInfo) simpleDaoSupport.get(ChannelInfo.class, TeeStringUtil.getInteger(id.split("\\.")[0], 0));
					path+="/"+chnl.getChnlName();
				}
			}
		}
		
		footer.add(path);
		footer.add(idPath);
		
		j.setFooter(footer);
		return j;
	}

	public TeeEasyuiDataGridJson listTrashChannelsDatagrid(TeeDataGridModel dm,int siteId,
			int channelId) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql="from ChannelInfo ci where ci.siteId="+siteId+" and ci.parentChnl="+channelId+" and ci.delFlag=1 order by ci.sortNo asc";
		
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql,null));// 设置总记录数
		
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<ChannelInfo> chnls = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查
		
		j.setRows(chnls);

		return j;
	}
	
	public void updateExtFields(Map<String,String> params,int chnlId) {
		String sql = "update cms_channel_info set ";
		List pms = new ArrayList();
		Set<String> keys = params.keySet();
		for(String key : keys){
			sql+=" "+key+" = ?," ;
			pms.add(params.get(key));
		}
		if(sql.endsWith(",")){
			sql = sql.substring(0, sql.length()-1);
		}
		sql+=" where sid="+chnlId;
		
		if(keys.size()!=0){
			simpleDaoSupport.executeNativeUpdate(sql, pms.toArray());
		}
	}
	
	
	
	public Map getExtFields(int chnlId) {
		List<ChannelInfoExt> list = simpleDaoSupport.find("from ChannelInfoExt order by sid asc", null);
		if(list.size()==0){
			return new HashMap();
		}
		String sql = "select ";
		for(ChannelInfoExt ext:list){
			sql+=" "+ext.getFieldName().toUpperCase()+",";
		}
		if(sql.endsWith(",")){
			sql = sql.substring(0,sql.length()-1);
		}
		sql+=" from cms_channel_info where sid="+chnlId;
		Map values = simpleDaoSupport.executeNativeUnique(sql, null);
		return values;
	}
	
	
	public void updateChannelHtmlContent(int chnlId,String htmlContent) {
		simpleDaoSupport.executeUpdate("update ChannelInfo set htmlContent=? where sid="+chnlId, new Object[]{htmlContent});
	}
	
}
