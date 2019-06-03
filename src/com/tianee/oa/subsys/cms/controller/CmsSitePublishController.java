package com.tianee.oa.subsys.cms.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.oa.subsys.cms.bean.DocumentInfo;
import com.tianee.oa.subsys.cms.core.CmsPublisher;
import com.tianee.oa.subsys.cms.service.CmsChannelPublishService;
import com.tianee.oa.subsys.cms.service.CmsChannelService;
import com.tianee.oa.subsys.cms.service.CmsDocumentPublishService;
import com.tianee.oa.subsys.cms.service.CmsDocumentService;
import com.tianee.oa.subsys.cms.service.CmsSitePublishService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/cmsSitePub")
public class CmsSitePublishController {
	@Autowired
	private CmsSitePublishService sitePublishService;
	
	@Autowired
	private CmsChannelPublishService channelPublishService;
	
	@Autowired
	private CmsDocumentPublishService documentPublishService;
	
	@Autowired
	private CmsChannelService channelService;
	
	@Autowired
	private CmsDocumentService documentService;
	
	/**
	 * 全站点发布
	 * @param request
	 * @param response
	 */
	@RequestMapping("/toPub")
	public void toPub(HttpServletRequest request,HttpServletResponse response){
		TeeJson json = new TeeJson();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		int siteId = TeeStringUtil.getInteger(request.getParameter("siteId"), 0);
		
		//发布类型  0：首页  1：全站
		int pubAll = TeeStringUtil.getInteger(request.getParameter("pubAll"), 0);
		
		try {
			PrintWriter pw = response.getWriter();
			pw.write("发布站点首页…<br/><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			pw.flush();
			if(pubAll==0){//发布首页
				sitePublishService.toPub(siteId);
			}else if(pubAll==1){//全站发布
				sitePublishService.toPub(siteId);
				
				pw.write("复制资源文件…<br/><script>window.scrollTo(0,document.body.scrollHeight);</script>");
				pw.flush();
				//发布对应的资源文件
				sitePublishService.copyResource(siteId);
				
				pw.write("获取栏目进行发布…<br/><script>window.scrollTo(0,document.body.scrollHeight);</script>");
				pw.flush();
				//获取指定站点下的所有栏目进行发布
				List<ChannelInfo> channelInfos = channelService.getChannelsBySiteSimples(siteId);
				for(ChannelInfo channels:channelInfos){
					pw.write("发布栏目："+channels.getChnlName()+"<br/><script>window.scrollTo(0,document.body.scrollHeight);</script>");
					pw.flush();
					channelPublishService.toPub(channels.getSid());
				}
				//获取指定站点下的所有文章进行发布
				pw.write("发布所有文档…<br/><script>window.scrollTo(0,document.body.scrollHeight);</script>");
				pw.flush();
				List<DocumentInfo> docs = documentService.getDocumentBySiteSimple(siteId);
				int total = docs.size();
				int i = 1 ;
				for(DocumentInfo doc:docs){
					documentPublishService.toPub(doc.getSid());
					pw.write("发布文档进度："+((i++)+"/"+total)+"<br/><script>window.scrollTo(0,document.body.scrollHeight);</script>");
					pw.flush();
				}
				
			}
			
			json.setRtState(true);
			response.getWriter().write("发布完成…<br/><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		} catch (Exception e) {
			json.setRtState(false);
			e.printStackTrace();
		}
	}
}
