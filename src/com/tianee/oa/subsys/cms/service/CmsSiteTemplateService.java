package com.tianee.oa.subsys.cms.service;

import java.io.File;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.tianee.oa.subsys.cms.bean.SiteTemplate;
import com.tianee.oa.subsys.cms.model.SiteTemplateModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.global.TeeSysProps;

@Service
public class CmsSiteTemplateService extends TeeBaseService{
	
	/**
	 * 创建模板
	 * @param siteTemplateModel
	 */
	public void createTemplate(SiteTemplateModel siteTemplateModel){
		SiteTemplate siteTemplate = new SiteTemplate();
		BeanUtils.copyProperties(siteTemplateModel, siteTemplate);
		File file = new File(TeeSysProps.getSiteTemplatePath()+File.separator+siteTemplateModel.getSiteId());
		if(!file.exists()){//如果不存在，则创建文件夹
			file.mkdir();
		}
//		//判断是存在该文件名称
		long count = simpleDaoSupport.count("select count(*) from SiteTemplate st where st.siteId=? and st.tplFileName = ?", 
				new Object[]{siteTemplateModel.getSiteId(),siteTemplateModel.getTplFileName()});
		
		if(count>0){
			throw new TeeOperationException("已存在该文件名["+siteTemplateModel.getTplFileName()+"]");
		}
		
		try {
			TeeFileUtility.storeString2File(TeeSysProps.getSiteTemplatePath()+File.separator+siteTemplateModel.getSiteId()+File.separator+siteTemplateModel.getTplFileName(), siteTemplateModel.getContent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		simpleDaoSupport.save(siteTemplate);
		siteTemplateModel.setSid(siteTemplate.getSid());
		
	}
	
	/**
	 * 更新模板
	 * @param siteTemplateModel
	 */
	public void updateTemplate(SiteTemplateModel siteTemplateModel){
		SiteTemplate siteTemplate = (SiteTemplate) simpleDaoSupport.get(SiteTemplate.class, siteTemplateModel.getSid());
		siteTemplate.setTplDesc(siteTemplateModel.getTplDesc());
		siteTemplate.setTplName(siteTemplateModel.getTplName());
		
		simpleDaoSupport.update(siteTemplate);
		
		try {
			TeeFileUtility.storeString2File(TeeSysProps.getSiteTemplatePath()+File.separator+siteTemplate.getSiteId()+File.separator+siteTemplate.getTplFileName(), siteTemplateModel.getContent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除模板
	 * @param sid
	 */
	public void delTemplate(int templateId){
		SiteTemplate siteTemplate = (SiteTemplate) simpleDaoSupport.get(SiteTemplate.class, templateId);
		//删除原始文件
		File file = new File(TeeSysProps.getSiteTemplatePath()+File.separator+siteTemplate.getSiteId()+File.separator+siteTemplate.getTplFileName());
		if(file.exists()){
			file.delete();
		}
		
		simpleDaoSupport.deleteByObj(siteTemplate);
	}
	
	/**
	 * 删除模板
	 * @param sid
	 */
	public SiteTemplateModel getTemplateModel(int templateId){
		SiteTemplateModel model = new SiteTemplateModel();
		SiteTemplate siteTemplate = (SiteTemplate) simpleDaoSupport.get(SiteTemplate.class, templateId);
		BeanUtils.copyProperties(siteTemplate, model);
		
		try {
			byte b[] = TeeFileUtility.loadFile2Bytes(TeeSysProps.getSiteTemplatePath()+File.separator+siteTemplate.getSiteId()+File.separator+siteTemplate.getTplFileName());
			model.setContent(new String(b,"UTF-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return model;
	}
	
	/**
	 * 获取站点下模板列表
	 * @param sid
	 */
	public TeeEasyuiDataGridJson listTemplates(int siteId){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List list = simpleDaoSupport.find("from SiteTemplate st where st.siteId="+siteId, null);
		long total = simpleDaoSupport.count("select count(st.sid) from SiteTemplate st where st.siteId="+siteId, null);
		dataGridJson.setRows(list);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	/**
	 * 获取站点模板
	 * @param templateId
	 * @return
	 */
	public SiteTemplateModel getSiteTemplate(int templateId){
		SiteTemplateModel model = new SiteTemplateModel();
		SiteTemplate st = (SiteTemplate) simpleDaoSupport.get(SiteTemplate.class, templateId);
		if(st==null){
			return null;
		}
		//获取文件内容
		BeanUtils.copyProperties(st, model);
		
//		File file = new File();
		
		
		return model;
	}
	
	public void upload(int siteId,List<String> tplsNames){
		
	}
}
