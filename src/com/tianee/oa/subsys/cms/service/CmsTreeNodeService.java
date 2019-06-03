package com.tianee.oa.subsys.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.oa.subsys.cms.bean.SiteInfo;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class CmsTreeNodeService extends TeeBaseService{
	
	public List<TeeZTreeModel> treeNode(String id,String siteIds,String chnls,TeePerson loginUser){
		List<TeeZTreeModel> list = new ArrayList<TeeZTreeModel>();
		long childCount = 0;
		if(id==null && siteIds!=null){
			List<SiteInfo> siteList = simpleDaoSupport.find("from SiteInfo where sid in ("+siteIds+") order by sortNo asc", null);
			for(SiteInfo si:siteList){
				TeeZTreeModel model = new TeeZTreeModel();
				model.setOpen(true);
				model.setName(si.getSiteName());
				model.setTitle(si.getSiteName());
				model.setIconSkin("site");
				model.setId(si.getSid()+";site");
				model.setExtend1(si.getSid()+"");
				//检测该站点下有无栏目
				childCount = simpleDaoSupport.count("select count(*) from ChannelInfo where siteId="+si.getSid()+" and parentChnl=0", null);
				if(childCount!=0){
					model.setParent(true);
				}else{
					model.setParent(false);
				}
				list.add(model);
			}
		}else if(id==null){//展开站点信息
			List<SiteInfo> siteList = simpleDaoSupport.find("from SiteInfo order by sortNo asc", null);
			for(SiteInfo si:siteList){
				TeeZTreeModel model = new TeeZTreeModel();
				model.setOpen(true);
				model.setParent(true);
				model.setName(si.getSiteName());
				model.setTitle(si.getSiteName());
				model.setIconSkin("site");
				model.setId(si.getSid()+";site");
				model.setExtend1(si.getSid()+"");
				//检测该站点下有无栏目
				childCount = simpleDaoSupport.count("select count(*) from ChannelInfo where siteId="+si.getSid()+" and parentChnl=0", null);
				if(childCount!=0){
					model.setParent(true);
				}else{
					model.setParent(false);
				}
				list.add(model);
			}
		}else{
			String sp [] = id.split(";");
			String sid = sp[0];
			String type = sp[1];
			List<ChannelInfo> chnlList = null;
			
			if(type.equals("site")){//展开该站点下的栏目
			
				if(TeePersonService.checkIsAdminPriv(loginUser)){
					chnlList = simpleDaoSupport.find("from ChannelInfo c1 where c1.siteId="+sid+" and c1.delFlag=0 and c1.parentChnl=0 order by c1.sortNo asc", null);
				}else{
					chnlList = simpleDaoSupport.find("from ChannelInfo c1 where c1.siteId="+sid+" and c1.delFlag=0 and c1.parentChnl=0 and (c1.privUserIds like '"+"%,"+loginUser.getUuid()+",%"+"' or exists (select 1 from ChannelInfo c2 where  c2.delFlag=0 and  c2.path like concat(c1.path,'%') and c2.privUserIds like '%,"+loginUser.getUuid()+",%')) order by sortNo asc", null);
				}
		    }else if(type.equals("chnl")){//展开该栏目下的子栏目
				if(TeePersonService.checkIsAdminPriv(loginUser)){
					chnlList = simpleDaoSupport.find("from ChannelInfo where parentChnl="+sid+"  and delFlag=0 order by sortNo asc", null);
				}else{
					chnlList = simpleDaoSupport.find("from ChannelInfo c1 where c1.parentChnl="+sid+"  and c1.delFlag=0 and (c1.privUserIds like '"+"%,"+loginUser.getUuid()+",%"+"'or exists (select 1 from ChannelInfo c2 where c2.delFlag=0 and  (c2.parentChnl=c1.sid or  c2.path like concat(c1.path,'%') ) and c2.privUserIds like '%,"+loginUser.getUuid()+",%')) order by sortNo asc", null);
				}
		     }
			
			//获取当前登陆人有管理权限的部门
			List<ChannelInfo>privList=simpleDaoSupport.find("from ChannelInfo c1 where  c1.delFlag=0 and c1.privUserIds like '"+"%,"+loginUser.getUuid()+",%"+"'",null);
			
			
			for(ChannelInfo ch:chnlList){
				TeeZTreeModel model = new TeeZTreeModel();
				model.setOpen(true);
				model.setParent(true);
				model.setName(ch.getChnlName());
				model.setTitle(ch.getChnlName());
				model.setIconSkin("channel");
				model.setId(ch.getSid()+";chnl");
				model.setExtend1(ch.getSiteId()+"");
				//检测该站点下有无栏目
				childCount = simpleDaoSupport.count("select count(*) from ChannelInfo where siteId="+ch.getSiteId()+" and delFlag=0 and parentChnl="+ch.getSid(), null);
				if(childCount!=0){
					model.setParent(true);
				}else{
					model.setParent(false);
				}
				
				if(TeePersonService.checkIsAdminPriv(loginUser)){
					model.setExtend2(1+"");	
				}else{
					if(privList.contains(ch)){
	                	model.setExtend2(1+"");
	                }else{
	                	model.setExtend2(0+"");
	                }
				}
				list.add(model);
			}
			
//			if(!type.equals("rootSite")){
//				
//			}else{
//				List<SiteInfo> siteList = simpleDaoSupport.find("from SiteInfo where sid="+sid+" order by sortNo asc", null);
//				for(SiteInfo si:siteList){
//					TeeZTreeModel model = new TeeZTreeModel();
//					model.setOpen(true);
//					model.setParent(true);
//					model.setName(si.getSiteName());
//					model.setTitle(si.getSiteName());
//					model.setIconSkin("site");
//					model.setId(si.getSid()+";site");
//					model.setExtend1(si.getSid()+"");
//					list.add(model);
//				}
//			}
			
		}
		return list;
	}
	
	
	
	public Object checkTreeNode(String id, String siteIds, String chnls,
			TeePerson loginUser) {
		List<TeeZTreeModel> list = new ArrayList<TeeZTreeModel>();
		long childCount = 0;
		if(id==null && siteIds!=null){
			List<SiteInfo> siteList = simpleDaoSupport.find("from SiteInfo where sid in ("+siteIds+") order by sortNo asc", null);
			for(SiteInfo si:siteList){
				TeeZTreeModel model = new TeeZTreeModel();
				model.setOpen(true);
				model.setName(si.getSiteName());
				model.setTitle(si.getSiteName());
				model.setIconSkin("site");
				model.setId(si.getSid()+";site");
				model.setExtend1(si.getSid()+"");
				//检测该站点下有无栏目
				childCount = simpleDaoSupport.count("select count(*) from ChannelInfo where siteId="+si.getSid()+" and parentChnl=0", null);
				if(childCount!=0){
					model.setParent(true);
				}else{
					model.setParent(false);
				}
				list.add(model);
			}
		}else if(id==null){//展开站点信息
			List<SiteInfo> siteList = simpleDaoSupport.find("from SiteInfo order by sortNo asc", null);
			for(SiteInfo si:siteList){
				TeeZTreeModel model = new TeeZTreeModel();
				model.setOpen(true);
				model.setParent(true);
				model.setName(si.getSiteName());
				model.setTitle(si.getSiteName());
				model.setIconSkin("site");
				model.setId(si.getSid()+";site");
				model.setExtend1(si.getSid()+"");
				//检测该站点下有无栏目
				childCount = simpleDaoSupport.count("select count(*) from ChannelInfo where siteId="+si.getSid()+" and parentChnl=0", null);
				if(childCount!=0){
					model.setParent(true);
				}else{
					model.setParent(false);
				}
				list.add(model);
			}
		}else{
			String sp [] = id.split(";");
			String sid = sp[0];
			String type = sp[1];
			List<ChannelInfo> chnlList = null;
			if(type.equals("site")){//展开该站点下的栏目
				chnlList = simpleDaoSupport.find("from ChannelInfo c1 where c1.siteId="+sid+" and c1.delFlag=0 and c1.parentChnl=0 "+(chnls!=null?"and c1.sid not in ("+chnls+")":"")+" and (c1.checkUserId="+loginUser.getUuid()+" or exists (select 1 from ChannelInfo c2 where  c2.delFlag=0 and  c2.path like concat(c1.path,'%') and c2.checkUserId ="+loginUser.getUuid()+"))  order by c1.sortNo asc", null);
			}else if(type.equals("chnl")){//展开该栏目下的子栏目
				chnlList = simpleDaoSupport.find("from ChannelInfo c1 where c1.parentChnl="+sid+"  and c1.delFlag=0 "+(chnls!=null?"and c1.sid not in ("+chnls+")":"")+" and (c1.checkUserId="+loginUser.getUuid()+" or exists (select 1 from ChannelInfo c2 where c2.delFlag=0 and  (c2.parentChnl=c1.sid or  c2.path like concat(c1.path,'%') ) and c2.checkUserId= "+loginUser.getUuid()+")) order by c1.sortNo asc", null);
			}
			
			
			//获取当前登陆人有审核权限的部门
			List<ChannelInfo>privList=simpleDaoSupport.find("from ChannelInfo c1 where  c1.delFlag=0 and c1.checkUserId="+loginUser.getUuid(),null);
			
			for(ChannelInfo ch:chnlList){
				TeeZTreeModel model = new TeeZTreeModel();
				model.setOpen(true);
				model.setParent(true);
				model.setName(ch.getChnlName());
				model.setTitle(ch.getChnlName());
				model.setIconSkin("channel");
				model.setId(ch.getSid()+";chnl");
				model.setExtend1(ch.getSiteId()+"");
				//检测该站点下有无栏目
				childCount = simpleDaoSupport.count("select count(*) from ChannelInfo where siteId="+ch.getSiteId()+" and delFlag=0 and parentChnl="+ch.getSid(), null);
				if(childCount!=0){
					model.setParent(true);
				}else{
					model.setParent(false);
				}
				
				if(TeePersonService.checkIsAdminPriv(loginUser)){
					model.setExtend2(1+"");	
				}else{
					if(privList.contains(ch)){
	                	model.setExtend2(1+"");
	                }else{
	                	model.setExtend2(0+"");
	                }
				}
				list.add(model);
			}
			
//			if(!type.equals("rootSite")){
//				
//			}else{
//				List<SiteInfo> siteList = simpleDaoSupport.find("from SiteInfo where sid="+sid+" order by sortNo asc", null);
//				for(SiteInfo si:siteList){
//					TeeZTreeModel model = new TeeZTreeModel();
//					model.setOpen(true);
//					model.setParent(true);
//					model.setName(si.getSiteName());
//					model.setTitle(si.getSiteName());
//					model.setIconSkin("site");
//					model.setId(si.getSid()+";site");
//					model.setExtend1(si.getSid()+"");
//					list.add(model);
//				}
//			}
			
		}
		return list;
	}
}
