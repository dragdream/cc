package com.tianee.oa.subsys.cms.core.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.oa.subsys.cms.model.SiteInfoModel;
import com.tianee.oa.subsys.cms.model.SiteTemplateModel;
import com.tianee.oa.subsys.cms.service.CmsChannelService;
import com.tianee.oa.subsys.cms.service.CmsSiteService;
import com.tianee.oa.subsys.cms.service.CmsSiteTemplateService;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class CmsChannelDirective implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// TODO Auto-generated method stub
		String chnlIdentity = TeeStringUtil.getString(params.get("id"));
		
		Writer writer = env.getOut();
		boolean preview = (Boolean) env.getCustomAttribute("preview");
		
		//根据siteIdentity查询出站点信息
		CmsSiteService siteService = (CmsSiteService) env.getCustomAttribute("siteService");
		CmsChannelService channelService = (CmsChannelService) env.getCustomAttribute("channelService");
		CmsSiteTemplateService siteTemplateService = (CmsSiteTemplateService) env.getCustomAttribute("siteTemplateService");
		Object loopObj = env.getCustomAttribute("loopObj");//上层循环变量
		TemplateModel this0 = env.getVariable("this");
		Object channelId = env.getCustomAttribute("CHANNEL_ID");
		
		ChannelInfo chnl = channelService.getChannelByIdentity(chnlIdentity);
		if(chnl==null){
			if(null!=channelId){
				chnl = channelService.getChannelInfo((Integer)channelId);
			}else{
				return;
			}
		}
		
		SiteTemplateModel st = siteTemplateService.getSiteTemplate(chnl.getIndexTpl());
		SiteInfoModel siteInfoModel = siteService.getSiteInfo(chnl.getSiteId());
		
		Map map = new HashMap();
		map.put("channelId", chnl.getSid());
		map.put("siteId", chnl.getSiteId());
		map.put("chnlIdentity", chnl.getChnlIdentity());
		map.put("htmlContent", chnl.getHtmlContent());
		map.put("chnlName", chnl.getChnlName());
		//获取扩展字段
		Map exts = channelService.getExtFields(chnl.getSid());
		map.putAll(exts);
		
		String absPath = channelService.getChannelAbsolutePath(chnl.getSid());
		if(st!=null){
			map.put("ABS_PATH", absPath+"/"+siteInfoModel.getPubFileExt());//绝对路径
			int index = absPath.indexOf("/", 2);
			absPath = absPath.substring(index+1);
			map.put("REL_PATH", env.getCustomAttribute("ROOT_PATH")+absPath+"/"+siteInfoModel.getPubFileExt());//相对路径
		}else{
			map.put("ABS_PATH", absPath);//绝对路径
			int index = absPath.indexOf("/", 2);
			absPath = absPath.substring(index+1);
			map.put("REL_PATH", env.getCustomAttribute("ROOT_PATH")+absPath);//相对路径
		}
		
		//如果是预览中
		if(preview){
			map.put("ABS_PATH", TeeSysProps.getString("contextPath")+"/cmsPub/portal.action?siteId="+chnl.getSiteId()+"&channelId="+chnl.getSid());//绝对路径
			map.put("REL_PATH", TeeSysProps.getString("contextPath")+"/cmsPub/portal.action?siteId="+chnl.getSiteId()+"&channelId="+chnl.getSid());//相对路径
		}
		Map _this = new HashMap();
		_this.put("instance", map);
		_this.put("channelId", chnl.getSid());
		_this.put("chnlIdentity", chnl.getChnlIdentity());
		_this.putAll(exts);
		
		env.setVariable("this", new DefaultObjectWrapper().wrap(_this));
		env.setCustomAttribute("loopObj", map);
		env.setCustomAttribute("CHANNEL_ID",chnl.getSid());
		if(body!=null){
			body.render(writer);
		}
		env.setVariable("this", this0);
		env.setCustomAttribute("CHANNEL_ID",channelId);
		env.setCustomAttribute("loopObj", loopObj);
	}

}
