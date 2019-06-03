package com.tianee.oa.subsys.cms.core;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.oa.subsys.cms.bean.SiteInfo;
import com.tianee.oa.subsys.cms.bean.SiteTemplate;
import com.tianee.oa.subsys.cms.model.SiteInfoModel;
import com.tianee.oa.subsys.cms.model.SiteTemplateModel;
import com.tianee.oa.subsys.cms.service.CmsChannelService;
import com.tianee.oa.subsys.cms.service.CmsDocumentService;
import com.tianee.oa.subsys.cms.service.CmsSiteService;
import com.tianee.oa.subsys.cms.service.CmsSiteTemplateService;
import com.tianee.webframe.util.cache.RedisClient;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * @author kakalion
 *
 */
@Repository
public class CmsPublisher{
	
	@Autowired
	private CmsSiteService siteService;
	
	@Autowired
	private CmsSiteTemplateService siteTemplateService;
	
	@Autowired
	private CmsChannelService channelService;
	
	@Autowired
	private CmsDocumentService documentService;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	private static Object siteLock = new Object();
	private static Object chnlLock = new Object();
	private static Object docLock = new Object();
	
	public void pubSite(int siteId){
		SiteInfoModel siteInfo = siteService.getSiteInfo(siteId);
		//首页模板
		SiteTemplateModel indexPage = siteTemplateService.getSiteTemplate(siteInfo.getIndexTpl());
		String renderContent = renderSiteIndexPage(siteId,null,false);
		
	}
	
	public void pubSiteIndex(int siteId){
		
	}
	
	public void pubChannel(int channelId){
		
	}
	
	public void pubChannelIndex(int channelId){
		
	}
	
	public void pubDoc(int chnlDocId){
		
	}
	
	/**
	 *渲染站点索引页面
	 * @param siteId
	 * @return
	 */
	public String renderSiteIndexPage(int siteId,Map params,boolean preview){
		String pageContent = null;
		if(pageContent==null){
			synchronized (siteLock) {
				pageContent = null;
				if(pageContent==null){
					//获取站点信息
					SiteInfoModel siteModel = siteService.getSiteInfo(siteId);
					//获取站点模板
					SiteTemplateModel indexTpl = siteTemplateService.getSiteTemplate(siteModel.getIndexTpl());
					if(indexTpl!=null){//如果存在首页模板，则进行渲染
						String path = TeeSysProps.getSiteTemplatePath()+File.separator+indexTpl.getSiteId();
						Configuration config=new Configuration();
						ByteArrayOutputStream baos = null;
						Writer out = null;
						try {
							config.setDirectoryForTemplateLoading(new File(path));
							
							//注入服务
							config.setCustomAttribute("siteService", siteService);
							config.setCustomAttribute("siteTemplateService", siteTemplateService);
							config.setCustomAttribute("channelService", channelService);
							config.setCustomAttribute("documentService", documentService);
							config.setCustomAttribute("attachmentService", attachmentService);
							
							//附加参数
							config.setCustomAttribute("ROOT_PATH", siteModel.getContextPath());
							config.setCustomAttribute("SITE_ID", siteModel.getSid());
							
							config.setCustomAttribute("preview", preview);
							
							Template template=config.getTemplate(indexTpl.getTplFileName(),"UTF-8");
							baos = new ByteArrayOutputStream();
						    out = new OutputStreamWriter(baos,"UTF-8");
						    
						    //输出参数
						    template.process(CmsTemplateUtil.getInstance(), out);
						    out.flush();
						    out.close();

						    pageContent = baos.toString("UTF-8");
						    RedisClient.getInstance().hset("SITE","CMS_SITE_"+siteId,pageContent);
						    return pageContent;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							if(baos!=null){
								try {
									baos.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
								}
							}
							if(out!=null){
								try {
									out.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
								}
							}
						}
					}
				}
			}
		}
		
		return pageContent;
	}
	
	/**
	 * 渲染栏目索引页面
	 * @param channelId
	 * @return
	 */
	public String renderChannelIndexPage(int channelId,Map params,boolean preview){
		String pageContent = null;
		if(pageContent==null){
			synchronized (chnlLock) {
				pageContent = null;
				if(pageContent==null){

					//获取站点信息
					ChannelInfo channelInfo = channelService.getChannelInfo(channelId);
					//获取站点信息
					SiteInfoModel siteInfo = siteService.getSiteInfo(channelInfo.getSiteId());
					//获取站点模板
					SiteTemplateModel indexTpl = siteTemplateService.getSiteTemplate(channelInfo.getIndexTpl());
					if(indexTpl!=null){//如果存在首页模板，则进行渲染
						String path = TeeSysProps.getSiteTemplatePath()+File.separator+indexTpl.getSiteId();
						Configuration config=new Configuration();
						ByteArrayOutputStream baos = null;
						Writer out = null;
						try {
							config.setDirectoryForTemplateLoading(new File(path));
							
							int curPage = TeeStringUtil.getInteger(params.get("curPage"), 1);
							
							//注入服务
							config.setCustomAttribute("siteService", siteService);
							config.setCustomAttribute("siteTemplateService", siteTemplateService);
							config.setCustomAttribute("channelService", channelService);
							config.setCustomAttribute("documentService", documentService);
							config.setCustomAttribute("attachmentService", attachmentService);
							
							//附加参数
							config.setCustomAttribute("ROOT_PATH", siteInfo.getContextPath());
							config.setCustomAttribute("SITE_ID", channelInfo.getSiteId());
							config.setCustomAttribute("CHANNEL_ID", channelId);
							config.setSharedVariable("curChannelId", channelId);
							
							
							config.setCustomAttribute("preview", preview);
							config.setCustomAttribute("curPage", curPage);
							
							//获取当前栏目下的可发布的文档数量
							int pageSize = channelInfo.getPageSize();
							long total = documentService.getPublishableDocumentsCountByChnlId(channelId);
							int pageNums = 0;
							if(total!=0 && pageSize!=0){
								pageNums = (int) ((total)%pageSize==0?(total)/pageSize:(total)/pageSize+1);
							}
							
							config.setCustomAttribute("pageNums", pageNums);
							
							Template template=config.getTemplate(indexTpl.getTplFileName(),"UTF-8");
							baos = new ByteArrayOutputStream();
						    out = new OutputStreamWriter(baos,"UTF-8");
						    
						    //输出参数
						    template.process(CmsTemplateUtil.getInstance(), out);
						    out.flush();
						    out.close();


						    pageContent = baos.toString("UTF-8");
						    RedisClient.getInstance().hset("CHNL","CMS_CHNL_"+channelId+params.toString(),pageContent);
						    return pageContent;
						    
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							if(baos!=null){
								try {
									baos.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
								}
							}
							if(out!=null){
								try {
									out.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
								}
							}
						}
					}
				}
			}
		}
		
		return pageContent;
	}
	
	/**
	 * 渲染文档详细页面
	 * @param siteId
	 * @return
	 */
	public String renderDocPage(int docId,Map params,boolean preview){
		
		String pageContent = null;
		if(pageContent==null){
			synchronized (docLock) {
				pageContent = null;
				if(pageContent==null){
					Map cd = documentService.getDocumentMapByDocId(docId);
					ChannelInfo channelInfo = null;
					channelInfo = channelService.getChannelInfo(TeeStringUtil.getInteger(cd.get("chnlId"), 0));
					//获取站点信息
					SiteInfoModel siteInfo = siteService.getSiteInfo(channelInfo.getSiteId());
					//获取站点模板
					SiteTemplateModel detailTpl = siteTemplateService.getSiteTemplate(channelInfo.getDetailTpl());
					if(detailTpl!=null){//如果存在首页模板，则进行渲染
						String path = TeeSysProps.getSiteTemplatePath()+File.separator+detailTpl.getSiteId();
						Configuration config=new Configuration();
						ByteArrayOutputStream baos = null;
						Writer out = null;
						try {
							config.setDirectoryForTemplateLoading(new File(path));
							
							//注入服务
							config.setCustomAttribute("siteService", siteService);
							config.setCustomAttribute("siteTemplateService", siteTemplateService);
							config.setCustomAttribute("channelService", channelService);
							config.setCustomAttribute("documentService", documentService);
							config.setCustomAttribute("attachmentService", attachmentService);
							
							//附加参数
							config.setCustomAttribute("ROOT_PATH", siteInfo.getContextPath());
							config.setCustomAttribute("SITE_ID", channelInfo.getSiteId());
							config.setCustomAttribute("CHANNEL_ID", channelInfo.getSid());
							config.setSharedVariable("curChannelId", channelInfo.getSid());
							config.setSharedVariable("docAttachmentId", TeeStringUtil.getInteger(cd.get("docAttachmentId"), 0));
							
							config.setCustomAttribute("DOCUMENT_ID", cd.get("docId"));
							
							config.setCustomAttribute("preview", preview);
							
							Template template=config.getTemplate(detailTpl.getTplFileName(),"UTF-8");
							baos = new ByteArrayOutputStream();
						    out = new OutputStreamWriter(baos,"UTF-8");
						    
						    //输出参数
						    template.process(CmsTemplateUtil.getInstance(), out);
						    out.flush();
						    out.close();
						    
						    pageContent = baos.toString("UTF-8");
						    RedisClient.getInstance().hset("DOC","CMS_DOC_"+docId,pageContent);
						    return pageContent;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							if(baos!=null){
								try {
									baos.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
								}
							}
							if(out!=null){
								try {
									out.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
								}
							}
						}
					}
				}
			}
		}
		
		return pageContent;
	}
}
