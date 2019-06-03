package com.tianee.oa.subsys.cms.core.directive;

import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;

import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.oa.subsys.cms.model.SiteTemplateModel;
import com.tianee.oa.subsys.cms.service.CmsChannelService;
import com.tianee.oa.subsys.cms.service.CmsDocumentService;
import com.tianee.oa.subsys.cms.service.CmsSiteTemplateService;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDataSource;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class CmsNavigatorDirective implements TemplateDirectiveModel{
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String print = TeeStringUtil.getString(params.get("print"));//自定义打印输出的内容
		int chnlId = TeeStringUtil.getInteger(params.get("chnlId"),0);
		
		Map loopObj = (Map) env.getCustomAttribute("loopObj");//上层循环变量
		
		int curChannelId = TeeStringUtil.getInteger(env.getConfiguration().getCustomAttribute("CHANNEL_ID"), 0);
		
		if(curChannelId==0 || chnlId==0){
			return;
		}
		
		CmsChannelService channelService = (CmsChannelService) env.getCustomAttribute("channelService");
		ChannelInfo curChannel = channelService.getChannelInfo(curChannelId);
		Writer writer = env.getOut();
		String sp[] = curChannel.getPath().split("/");
		for(int i=0;i<sp.length;i++){
			if(sp[i].equals(chnlId+".ch")){
				writer.write(print);
				break;
			}
		}
		
		
		
	}

}
