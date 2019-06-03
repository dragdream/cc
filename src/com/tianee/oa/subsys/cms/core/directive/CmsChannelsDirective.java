package com.tianee.oa.subsys.cms.core.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.oa.subsys.cms.bean.SiteTemplate;
import com.tianee.oa.subsys.cms.model.SiteInfoModel;
import com.tianee.oa.subsys.cms.model.SiteTemplateModel;
import com.tianee.oa.subsys.cms.service.CmsChannelService;
import com.tianee.oa.subsys.cms.service.CmsSiteService;
import com.tianee.oa.subsys.cms.service.CmsSiteTemplateService;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class CmsChannelsDirective implements TemplateDirectiveModel{

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// TODO Auto-generated method stub
		/**
		 * TOP：站点顶层栏目列表
		 * CHILD：指定栏目ID下的子栏目
		 * IDS：指定[a,b,c,d]的栏目
		 */
		String type = TeeStringUtil.getString(params.get("type"));
		String chnlIdentity = TeeStringUtil.getString(params.get("id"));
		String ids = TeeStringUtil.getString(params.get("ids"));
		int top = TeeStringUtil.getInteger(params.get("top"),0);
		
		int siteId = TeeStringUtil.getInteger(env.getCustomAttribute("SITE_ID"), 0);
		int channelId = TeeStringUtil.getInteger(env.getCustomAttribute("CHANNEL_ID"), 0);
		
		Writer writer = env.getOut();
		boolean preview = (Boolean) env.getCustomAttribute("preview");
		
		CmsChannelService channelService = (CmsChannelService) env.getCustomAttribute("channelService");
		CmsSiteService siteService = (CmsSiteService) env.getCustomAttribute("siteService");
		CmsSiteTemplateService siteTemplateService = (CmsSiteTemplateService) env.getCustomAttribute("siteTemplateService");
		
		SiteInfoModel siteInfo = siteService.getSiteInfo(siteId);
		
		List<ChannelInfo> channels = new ArrayList<ChannelInfo>();
		if("TOP".equals(type)){
			channels = channelService.getTopChannels(siteId, top==0?Integer.MAX_VALUE:top);
		}else if("CHILD".equals(type)){
			
			if("".equals(chnlIdentity)){//如果没有标识，则走当前channelId
				channels = channelService.getChildChannels(channelId, top==0?Integer.MAX_VALUE:top);
			}else{//如果有标识，则走当前的标识
				ChannelInfo ci = channelService.getChannelByIdentity(chnlIdentity);
				if(ci==null){
					return ;
				}
				channels = channelService.getChildChannels(ci.getSid(), top==0?Integer.MAX_VALUE:top);
			}
		}else if("IDS".equals(type)){
			channels = channelService.getChannelsByIds(ids, top==0?Integer.MAX_VALUE:top);
		}
		
		Object loopObj = env.getCustomAttribute("loopObj");//保留上层循环变量
		TemplateModel this0 = env.getVariable("this");
		
		if(channels==null){
			return;
		}
		
		int index0 = 0;
		for(ChannelInfo channel:channels){
			SiteTemplateModel st = siteTemplateService.getSiteTemplate(channel.getIndexTpl());
			
			Map map = new HashMap();
			map.put("channelId", channel.getSid());
			map.put("siteId", channel.getSiteId());
			map.put("chnlIdentity", channel.getChnlIdentity());
			map.put("chnlName", channel.getChnlName());
			map.put("htmlContent", channel.getHtmlContent());
			//获取扩展字段
			Map exts = channelService.getExtFields(channel.getSid());
			map.putAll(exts);
			
			String absPath = channelService.getChannelAbsolutePath(channel.getSid());
			if(st!=null){
				map.put("ABS_PATH", absPath+"/index."+siteInfo.getPubFileExt());//绝对路径
				int index = absPath.indexOf("/", 2);
				absPath = absPath.substring(index+1);
				map.put("REL_PATH", env.getCustomAttribute("ROOT_PATH")+absPath+"/index."+siteInfo.getPubFileExt());//相对路径
			}else{
				map.put("ABS_PATH", absPath);//绝对路径
				int index = absPath.indexOf("/", 2);
				absPath = absPath.substring(index+1);
				map.put("REL_PATH", env.getCustomAttribute("ROOT_PATH")+absPath);//相对路径
			}
			
			//如果是预览中
			if(preview){
				map.put("ABS_PATH", TeeSysProps.getString("contextPath")+"/cmsPub/portal.action?siteId="+channel.getSiteId()+"&channelId="+channel.getSid());//绝对路径
				map.put("REL_PATH", TeeSysProps.getString("contextPath")+"/cmsPub/portal.action?siteId="+channel.getSiteId()+"&channelId="+channel.getSid());//相对路径
			}
			
			//内部循环变量
			Map _this = new HashMap();
			_this.put("instance", map);
			_this.put("channelId", channel.getSid());
			_this.put("index", index0);
			if(index0==channels.size()-1){
				_this.put("isLast", true);
			}else{
				_this.put("isLast", false);
			}
			_this.putAll(exts);
			
			env.setVariable("this", new DefaultObjectWrapper().wrap(_this));
			env.setCustomAttribute("loopObj", map);
			env.setCustomAttribute("CHANNEL_ID",channel.getSid());
			if(body!=null){
				body.render(writer);
			}
			env.setVariable("this", this0);
			env.setCustomAttribute("CHANNEL_ID",channelId);
			env.setCustomAttribute("loopObj", loopObj);
			
			index0++;
		}
		
	}

}
