package com.tianee.oa.subsys.cms.core.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.oa.subsys.cms.model.SiteInfoModel;
import com.tianee.oa.subsys.cms.service.CmsChannelService;
import com.tianee.oa.subsys.cms.service.CmsSiteService;
import com.tianee.webframe.util.str.TeeStringUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class CmsPagerDirective implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String chnlIdentity = TeeStringUtil.getString(params.get("id"));
		
		Writer writer = env.getOut();
		int curPage = TeeStringUtil.getInteger(env.getCustomAttribute("curPage"), 1);
		int pageNums = TeeStringUtil.getInteger(env.getCustomAttribute("pageNums"), 0);
		
		boolean preview = (Boolean) env.getCustomAttribute("preview");
		
		String currentClass = TeeStringUtil.getString(params.get("currentClass"),"");
		String disabledClass = TeeStringUtil.getString(params.get("disabledClass"),"");
		//根据siteIdentity查询出站点信息
		CmsChannelService channelService = (CmsChannelService) env.getCustomAttribute("channelService");
		Object siteId = env.getCustomAttribute("SITE_ID");
		Object channelId = env.getCustomAttribute("CHANNEL_ID");
		
		CmsSiteService siteService = (CmsSiteService) env.getCustomAttribute("siteService");
		SiteInfoModel siteInfoModel = siteService.getSiteInfo(TeeStringUtil.getInteger(siteId, 0));
		
		ChannelInfo chnl = channelService.getChannelByIdentity(chnlIdentity);
		if(chnl==null){
			if(null!=channelId){
				chnl = channelService.getChannelInfo((Integer)channelId);
			}else{
				return;
			}
		}
		String pre = curPage-2+"";
		if("0".equals(pre)|| "-1".equals(pre)){
			pre="";
		}
		if(curPage==0){
			return;
		}
		
		if(preview){
			int prepage =(curPage<=1?1:curPage-1);
			
			String pager="<div class='digg'><a href='portal.action?siteId="+siteId+"&channelId="+channelId+"&curPage="+prepage+"'>&lt;</a>";
			for(int i=0;i<pageNums;i++){
				String url = "";
				if(i==0){
					url="portal.action?siteId="+siteId+"&channelId="+channelId;
				}else{
					url="portal.action?siteId="+siteId+"&channelId="+channelId+"&curPage="+(i+1);
				}
				if(curPage==(i+1)){
					pager+="<span class='"+currentClass+"'>"+(i+1)+"</span>";
					
				}else{
					pager+="<a href='"+url+"'>"+(i+1)+"</a>";
				}
				if(i>10){
					pager+="...";
					url="portal.action?siteId="+siteId+"&channelId="+channelId+"&curPage="+(pageNums-1);
					
					if(curPage==pageNums){
						pager+="<span class='"+currentClass+"'>"+(pageNums)+"</a>";
					}else{
						pager+="<a href='"+url+"'>"+(pageNums)+"</a>";

					}
					break;
				}
				
			}
			int next =(curPage==pageNums?curPage-1:curPage)+1;
			if(next==0){
				next=1;
			}
			pager+="<a href='portal.action?siteId="+siteId+"&channelId="+channelId+"&curPage="+next+"'>&gt;</a></div>";
			writer.write(pager);
		}else{
			int prepage = curPage-2;
			String pager="<div class='digg'><a href='index"+(prepage<=0?"":prepage)+"."+siteInfoModel.getPubFileExt()+"'>&lt;</a>";
			for(int i=0;i<pageNums;i++){
				String url = "";
				if(i==0){
					url="index."+siteInfoModel.getPubFileExt();
				}else{
					url="index"+i+"."+siteInfoModel.getPubFileExt();
				}
				if(curPage==(i+1)){
					pager+="<span class='"+currentClass+"'>"+(i+1)+"</span>";
					
				}else{
					pager+="<a href='"+url+"'>"+(i+1)+"</a>";
				}
				if(i>10){
					pager+="...";
					url="index"+(pageNums-1)+"."+siteInfoModel.getPubFileExt();
					
					if(curPage==pageNums){
						pager+="<span class='"+currentClass+"'>"+(pageNums)+"</a>";
					}else{
						pager+="<a href='"+url+"'>"+(pageNums)+"</a>";

					}
					break;
				}
				
			}
			String next =(curPage==pageNums?curPage-1:curPage)+"";
			if(next.equals("0")){
				next="";
			}
			pager+="<a href='index"+next+"."+siteInfoModel.getPubFileExt()+"'>&gt;</a></div>";
			writer.write(pager);
		}
		
	}

}
