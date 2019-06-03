package com.tianee.oa.subsys.cms.core.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.oa.subsys.cms.model.SiteInfoModel;
import com.tianee.oa.subsys.cms.service.CmsChannelService;
import com.tianee.oa.subsys.cms.service.CmsSiteService;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class CmsSiteDirective implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// TODO Auto-generated method stub
		String siteIdentity = TeeStringUtil.getString(params.get("id"));
		int siteId = TeeStringUtil.getInteger(env.getCustomAttribute("SITE_ID"), 0);
		int curChannelId = TeeStringUtil.getInteger(env.getCustomAttribute("CHANNEL_ID"), 0);
		Writer writer = env.getOut();
		
		//根据siteIdentity查询出站点信息
		CmsSiteService siteService = (CmsSiteService) env.getCustomAttribute("siteService");
		CmsChannelService channelService = (CmsChannelService) env.getCustomAttribute("channelService");
		Object loopObj = env.getCustomAttribute("loopObj");//上层循环变量
		boolean preview = (Boolean) env.getCustomAttribute("preview");
		
		SiteInfoModel siteInfo = null;
		if(!"".equals(siteIdentity)){
			siteInfo = siteService.getSiteInfoModelByIdentity(siteIdentity);
		}else{
			siteInfo = siteService.getSiteInfo(siteId);
		}
		ChannelInfo channelInfo=channelService.getChannelInfo(curChannelId);
		Map map = new HashMap();
		map.put("siteId",siteInfo.getSid());
		map.put("siteIdentity",siteInfo.getSiteIdentity());
		map.put("siteName",siteInfo.getSiteName());
		map.put("sortNo",siteInfo.getSortNo());
		map.put("folder",siteInfo.getFolder());
		map.put("pubStatus",siteInfo.getPubStatus());
		map.put("ABS_PATH",siteInfo.getFolder()+"/index."+siteInfo.getPubFileExt());
		if(null!=channelInfo){
			String channelPath = channelService.getChannelRootPath(curChannelId);
			map.put("REL_PATH",channelPath+"index."+siteInfo.getPubFileExt());
		}else{
			map.put("REL_PATH","./index."+siteInfo.getPubFileExt());
		}
		if(preview){
			map.put("ABS_PATH",TeeSysProps.getString("contextPath")+"/cmsPub/portal.action?siteId="+siteInfo.getSid());
			map.put("REL_PATH",TeeSysProps.getString("contextPath")+"/cmsPub/portal.action?siteId="+siteInfo.getSid());
		}
		
		env.setCustomAttribute("loopObj", map);
		
		if(body!=null){
			body.render(writer);
		}
		
		env.setCustomAttribute("loopObj", loopObj);
	}

}
