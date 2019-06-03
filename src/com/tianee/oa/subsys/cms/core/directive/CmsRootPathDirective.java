package com.tianee.oa.subsys.cms.core.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.tianee.oa.subsys.cms.service.CmsChannelService;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class CmsRootPathDirective implements TemplateDirectiveModel{

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// TODO Auto-generated method stub
		
		String src = TeeStringUtil.getString(params.get("src"));
		int siteId = TeeStringUtil.getInteger(env.getCustomAttribute("SITE_ID"),0);
		int curChannelId = TeeStringUtil.getInteger(env.getCustomAttribute("CHANNEL_ID"), 0);
		CmsChannelService channelService = (CmsChannelService) env.getCustomAttribute("channelService");
		boolean preview = (Boolean) env.getCustomAttribute("preview");
		Writer writer = env.getOut();
		if(preview){
			writer.write(TeeSysProps.getString("contextPath")+"/cmstpls/"+siteId+"/"+src);
		}else{
			String channelPath="";
			if(curChannelId>0){
				 channelPath = channelService.getChannelRootPath(curChannelId);
				 writer.write(channelPath+"/"+src);
			}else{
				writer.write(src);
			}
		}
	}

}
