package com.tianee.oa.subsys.cms.core.directive;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.oa.subsys.cms.model.SiteInfoModel;
import com.tianee.oa.subsys.cms.service.CmsChannelService;
import com.tianee.oa.subsys.cms.service.CmsSiteService;
import com.tianee.webframe.util.file.TeeUploadHelper;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class CmsLocationDirective implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String chnlIdentity = TeeStringUtil.getString(params.get("id"));
		
		Writer writer = env.getOut();
		//根据siteIdentity查询出站点信息
		CmsChannelService channelService = (CmsChannelService) env.getCustomAttribute("channelService");
		boolean preview = (Boolean) env.getCustomAttribute("preview");
		Object channelId = env.getCustomAttribute("CHANNEL_ID");
		
		ChannelInfo chnl = channelService.getChannelByIdentity(chnlIdentity);
		if(chnl==null){
			if(null!=channelId){
				chnl = channelService.getChannelInfo((Integer)channelId);
			}else{
				return;
			}
		}
		String channelPath = channelService.getChannelRootPath(chnl.getSid());
		
		CmsSiteService siteService = (CmsSiteService) env.getCustomAttribute("siteService");
		SiteInfoModel siteInfoModel = siteService.getSiteInfo(chnl.getSiteId());
		
		String firstIndexUrl="";
		if(null!=chnl){
			firstIndexUrl=channelPath+"index."+siteInfoModel.getPubFileExt();
		}else{
			firstIndexUrl = "./index."+siteInfoModel.getPubFileExt();
		}
		if(preview){
			firstIndexUrl= TeeSysProps.getString("contextPath")+"/cmsPub/portal.action?siteId="+chnl.getSiteId();
		}
		String firstIndexPath="";
		Map data = new HashMap();
		data.put("url",firstIndexUrl);
		data.put("name","首页");
		env.setCustomAttribute("loopObj", data);
		if(body!=null){
			body.render(writer);
		}
		env.setCustomAttribute("loopObj", null);
		String path = chnl.getPath();
		String[] paths = path.split("/");
		String chalPath="";
		String chalName="";
		
		for (int i=1;i<paths.length;i++) {
			if(i==paths.length-1){
				chalPath="index."+siteInfoModel.getPubFileExt();
				chalName=chnl.getChnlName();
				if(preview){
					chalPath= TeeSysProps.getString("contextPath")+"/cmsPub/portal.action?siteId="+chnl.getSiteId()+"&channelId="+chnl.getSid();
				}
			}else{
				String [] ids = (paths[i]).split("\\.");
				ChannelInfo info = channelService.getChannelInfo(Integer.parseInt(ids[0]));
				String realPath = channelService.getChannelRootPath(info.getSid());
				chalPath=realPath+"index."+siteInfoModel.getPubFileExt();
				chalName=info.getChnlName();
				if(preview){
					chalPath= TeeSysProps.getString("contextPath")+"/cmsPub/portal.action?siteId="+chnl.getSiteId()+"&channelId="+info.getSid();
				}
			}
			
			data.put("url",chalPath);
			data.put("name",chalName);
			env.setCustomAttribute("loopObj", data);
			if(body!=null){
				body.render(writer);
			}
			env.setCustomAttribute("loopObj", null);
		}
	}

}
