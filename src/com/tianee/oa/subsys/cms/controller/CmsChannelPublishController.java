package com.tianee.oa.subsys.cms.controller;

import java.io.PrintWriter;
import java.util.List;

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
@RequestMapping("/cmsChannelPub")
public class CmsChannelPublishController {
	@Autowired
	private CmsChannelPublishService channelPublishService;
	@Autowired
	private CmsSitePublishService cmsSitePublishService;
	@Autowired
	private CmsDocumentPublishService cmsDocumentPublishService;
	
	@Autowired
	private CmsChannelService cmsChannelService;
	
	@Autowired
	private CmsDocumentService cmsDocumentService;
	
	@RequestMapping("/toPub")
	public void toPub(HttpServletRequest request,HttpServletResponse response){
		String channelIds = TeeStringUtil.getString(request.getParameter("channelIds"), "");
		//发布类型  1：栏目首页  2：仅当前栏目下文档  3、全栏目发布（包含子栏目）
		int pub = TeeStringUtil.getInteger(request.getParameter("pub"), 1);
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		
		try {
			PrintWriter pw = response.getWriter();
			pw.write("发布栏目…<br/><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			pw.flush();
			
			if(channelIds.endsWith(",")){
				channelIds = channelIds.substring(0,channelIds.length()-1);
			}
			String[] chIds = channelIds.split(",");
			
			if(pub==1){//栏目首页
				for(String id:chIds){
					channelPublishService.toPub(Integer.parseInt(id));
				}
			}else if(pub==2){//当前栏目下文档
				List<DocumentInfo> docs = null;
				for(String id:chIds){
					channelPublishService.toPub(Integer.parseInt(id));
					
					pw.write("发布栏目:"+id+"<br/><script>window.scrollTo(0,document.body.scrollHeight);</script>");
					pw.flush();
					
					docs = cmsDocumentService.getDocumentByChnlSimple(Integer.parseInt(id));
					int total = docs.size();
					int i = 1 ;
					for(DocumentInfo doc:docs){
						cmsDocumentPublishService.toPub(doc.getSid());
						if((i++)%20==0){
							pw.write("发布文档进度："+(i++)+"/"+total+"<br/><script>window.scrollTo(0,document.body.scrollHeight);</script>");
							pw.flush();
						}
					}
				}
				
			}else if(pub==3){//全栏目发布
				List<DocumentInfo> docs = null;
				List<ChannelInfo> chnls = null;
				for(String id:chIds){
					channelPublishService.toPub(Integer.parseInt(id));
					
					pw.write("发布栏目:"+id+"<br/><script>window.scrollTo(0,document.body.scrollHeight);</script>");
					pw.flush();
					
					chnls = cmsChannelService.getChildChannelsByChnlIdSimples(Integer.parseInt(id));
					for(ChannelInfo ch:chnls){
						channelPublishService.toPub(ch.getSid());
						docs = cmsDocumentService.getDocumentByChnlSimple(ch.getSid());
						int total = docs.size();
						int i = 1 ;
						for(DocumentInfo doc:docs){
							cmsDocumentPublishService.toPub(ch.getSid());
							if((i++)%20==0){
								pw.write("发布文档进度："+(i++)+"/"+total+"<br/><script>window.scrollTo(0,document.body.scrollHeight);</script>");
								pw.flush();
							}
						}
					}
					
					docs = cmsDocumentService.getDocumentByChnlSimple(Integer.parseInt(id));
					int total = docs.size();
					int i = 1 ;
					for(DocumentInfo doc:docs){
						cmsDocumentPublishService.toPub(doc.getSid());
						if((i++)%20==0){
							pw.write("发布文档进度："+(i++)+"/"+total+"<br/><script>window.scrollTo(0,document.body.scrollHeight);</script>");
							pw.flush();
						}
					}
				}
				
				
			}
			
			
			pw.write("发布完成！<br/><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
