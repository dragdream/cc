package com.tianee.oa.subsys.cms.core.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class CmsOutDirective implements TemplateDirectiveModel{

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// TODO Auto-generated method stub
		
		String name = TeeStringUtil.getString(params.get("name"));
		String dateFormat = TeeStringUtil.getString(params.get("dateFormat"));
		Map loopObj = (Map)env.getCustomAttribute("loopObj");
		Writer writer = env.getOut();
		if(loopObj!=null){
			if(!"".equals(dateFormat)){
				Object obj = loopObj.get(name);
				if(obj instanceof Date){
					writer.write(TeeDateUtil.format((Date)obj, dateFormat));
				}else if(obj instanceof Calendar){
					writer.write(TeeDateUtil.format(((Calendar)obj).getTime(), dateFormat));
				}
			}else{
				writer.write(TeeStringUtil.getString(loopObj.get(name)));
			}
		}
		if(body!=null){
			body.render(writer);
		}
		
	}

}
