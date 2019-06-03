package com.tianee.oa.subsys.cms.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.cms.core.CmsPublisher;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/cmsPub")
public class CmsPublishController {
	@Autowired
	private CmsPublisher publisher;
	
	@RequestMapping("/portal")
	public void pub(HttpServletRequest request,HttpServletResponse response){
		int siteId = TeeStringUtil.getInteger(request.getParameter("siteId"), 0);
		int channelId = TeeStringUtil.getInteger(request.getParameter("channelId"), 0);
		int docId = TeeStringUtil.getInteger(request.getParameter("docId"), 0);
		Map params = TeeServletUtility.getParamMap(request);//额外请求参数
		response.setContentType("text/html");
		try {
			PrintWriter writer = response.getWriter();
			
			if(siteId!=0 && channelId==0 && docId==0){//渲染首页模板
				writer.write(publisher.renderSiteIndexPage(siteId,params, true));
			}else if(siteId!=0 && channelId!=0 && docId==0){//渲染栏目首页模板
				writer.write(publisher.renderChannelIndexPage(channelId,params, true));
			}else if(siteId!=0 && channelId!=0 && docId!=0){//渲染栏目文档模板
				writer.write(publisher.renderDocPage(docId,params, true));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
