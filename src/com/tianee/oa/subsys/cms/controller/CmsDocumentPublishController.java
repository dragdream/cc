package com.tianee.oa.subsys.cms.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.oa.subsys.cms.bean.DocumentInfo;
import com.tianee.oa.subsys.cms.service.CmsChannelPublishService;
import com.tianee.oa.subsys.cms.service.CmsChannelService;
import com.tianee.oa.subsys.cms.service.CmsDocumentPublishService;
import com.tianee.oa.subsys.cms.service.CmsDocumentService;
import com.tianee.oa.subsys.cms.service.CmsSitePublishService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/cmsDocumentPub")
public class CmsDocumentPublishController {
	@Autowired
	private CmsSitePublishService cmsSitePublishService;
	@Autowired
	private CmsDocumentPublishService docPublishService;
	
	@Autowired
	private CmsChannelService cmsChannelService;
	
	@Autowired
	private CmsDocumentService documentService;
	
	@Autowired
	private CmsChannelPublishService channelPublishService;
	
	@RequestMapping("/toPub")
	public void toPub(HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		TeeJson json = new TeeJson();
		String chnlId = TeeStringUtil.getString(request.getParameter("chnlId"), "");
		String docIds = TeeStringUtil.getString(request.getParameter("docIds"), "");
		
		try {
			PrintWriter pw = response.getWriter();
			pw.write("发布文档…<br/><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			pw.flush();
			
			if(docIds.endsWith(",")){
				docIds=docIds.substring(0,docIds.length()-1);
			}
			ChannelInfo channel = cmsChannelService.getChannelInfo(Integer.parseInt(chnlId));
			String[] docIds0 =docIds.split(",");
			
			//发布站点首页
			cmsSitePublishService.toPub(channel.getSiteId());
			//发布上级栏目首页
			String sp[] = channel.getPath().split("/");
			for(String path:sp){
				if(!"".equals(path)){
					channelPublishService.toPub(Integer.parseInt(path.replace(".ch", "")));
				}
			}
			
			for(String id:docIds0){
				if(null!=id && !id.equals("")){
					int docId = Integer.parseInt(id);
					docPublishService.toPub(docId);
				}
			}
			
			
			response.getWriter().write("发布完成！");
			json.setRtState(true);
			json.setRtMsg("文章发布成功！");
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("发布失败！");
			e.printStackTrace();
		}
	}
}
