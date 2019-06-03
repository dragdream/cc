package com.tianee.oa.subsys.evaluation.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.evaluation.bean.TeeEvalTemplate;
import com.tianee.oa.subsys.evaluation.bean.TeeEvalTemplateItem;
import com.tianee.oa.subsys.evaluation.dao.TeeEvalTemplateDao;
import com.tianee.oa.subsys.evaluation.dao.TeeEvalTemplateItemDao;
import com.tianee.oa.subsys.evaluation.model.TeeEvalTemplateItemModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;


@Service
public class TeeEvalTemplateItemService extends TeeBaseService{
	@Autowired
	private TeeEvalTemplateItemDao templateItemDao;
	
	@Autowired
	private TeeEvalTemplateDao templateDao;
	
	/**
	 * 保存或更新
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeEvalTemplateItemModel model) {
		TeeJson json = new TeeJson();
		TeeEvalTemplateItem templateItem = new TeeEvalTemplateItem();
		TeeEvalTemplateItem parentItem =null;
		int parentId = TeeStringUtil.getInteger(request.getParameter("parentId"), 0);
		if(parentId>0){
			parentItem = templateItemDao.getById(parentId);
		}
		if(model.getSid() > 0){
		    templateItem  = templateItemDao.getById(model.getSid());
			if(templateItem != null){
				BeanUtils.copyProperties(model, templateItem);
				templateItemDao.updateTemplateItem(templateItem);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关信息！");
				return json;
			}
			
		}else{
			BeanUtils.copyProperties(model, templateItem);
			if(model.getEvalTemplateId()>0){
				TeeEvalTemplate evalTemplate = templateDao.get(model.getEvalTemplateId());
				templateItem.setEvalTemplate(evalTemplate);
			}
			if(null!=parentItem){
				if(null!=parentItem.getLevel() && !TeeUtility.isNullorEmpty(parentItem.getLevel())){
					templateItem.setLevel(parentItem.getLevel()+parentId+"/");
				}else{
					templateItem.setLevel("/"+parentId+"/");
				}
			}
			templateItemDao.addTemplateItem(templateItem);
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * 对象转换
	 * @author nieyi
	 * @param templateItem
	 * @return
	 */
	public TeeEvalTemplateItemModel parseModel(TeeEvalTemplateItem templateItem){
		TeeEvalTemplateItemModel model = new TeeEvalTemplateItemModel();
		if(templateItem == null){
			return null;
		}
		BeanUtils.copyProperties(templateItem, model);
		if(null!=templateItem.getEvalTemplate()){
			model.setEvalTemplateId(templateItem.getEvalTemplate().getSid());
			model.setEvalTemplateSubject(templateItem.getEvalTemplate().getSubject());
		}
		return model;
	}
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson deleteByIdService(HttpServletRequest request, String sids) {
		TeeJson json = new TeeJson();
		templateItemDao.delByIds(sids);
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request, TeeEvalTemplateItemModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeEvalTemplateItem templateItem = templateItemDao.getById(model.getSid());
			if(templateItem !=null){
				model = parseModel(templateItem);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关客户记录！");
		return json;
	}
	

	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		return templateItemDao.datagird(dm, requestDatas);
	}

	
	public TeeJson getTemplateItemTree(int evalTemplateId) {
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> itemTree = new ArrayList<TeeZTreeModel>();
		int parentSid = 0;
		if(evalTemplateId>0){
			List<TeeEvalTemplateItem> templateItemRoot = templateItemDao.getTemplateItemRoot(evalTemplateId);
			if (templateItemRoot != null && templateItemRoot.size() > 0) {
				parentSid = templateItemRoot.get(0).getSid();
				for (TeeEvalTemplateItem parentItem : templateItemRoot) {
					List<TeeEvalTemplateItem> items = templateItemDao.getItemListByParent(parentItem.getSid()+"/",parentItem.getSid(),evalTemplateId);
					if (items != null && items.size() > 0) {
						for (TeeEvalTemplateItem item : items) {
							String iconSkin = TeeZTreeModel.FILE_FOLDER;
							String parentId = "0";
							if (item.getLevel() != null) {
								String level = item.getLevel().substring(0, item.getLevel().length()-1);
								int index = level.lastIndexOf("/");
								TeeEvalTemplateItem templateItem = null;
								if(index!=-1){
									templateItem =templateItemDao.getById(Integer.parseInt(level.substring(index+1,level.length())));
								}else{
									templateItem =templateItemDao.getById(Integer.parseInt(level));
								}
								parentId = templateItem.getSid() + "";
							}
							TeeZTreeModel ztree = new TeeZTreeModel();
							ztree.setId(String.valueOf(item.getSid()));
							ztree.setName(item.getName());
							ztree.setOpen(true);
							ztree.setpId(parentId);
							ztree.setIconSkin(iconSkin);
							itemTree.add(ztree);
						}
					}
				}
			} else {
				TeeEvalTemplate template = templateDao.get(evalTemplateId);
				TeeEvalTemplateItem templateItem = new TeeEvalTemplateItem();
				templateItem.setEvalTemplate(template);
				templateItem.setName("考核项目");
				templateItemDao.save(templateItem);
				
				parentSid = templateItem.getSid();
				
				TeeZTreeModel rooTztree = new TeeZTreeModel();
				rooTztree.setId(String.valueOf(templateItem.getSid()));
				rooTztree.setName(templateItem.getName());
				rooTztree.setOpen(true);
				rooTztree.setpId("0");
				rooTztree.setIconSkin(TeeZTreeModel.FILE_FOLDER);
				itemTree.add(rooTztree);
			}
		}
		Map map = new HashMap();
		map.put("ztreeData", itemTree);
		map.put("parentSid", parentSid);
		json.setRtData(map);
		json.setRtState(true);
		json.setRtMsg("考核项目获取成功!");
		return json;
	}

	public TeeJson getEvalTemplateItem(HttpServletRequest request, String level) {
		TeeJson json = new TeeJson();
		List<TeeEvalTemplateItemModel> itemList = templateItemDao.getEvalTemplateItem(level);
		if(null!=itemList && itemList.size()>0){
			json.setRtState(true);
			json.setRtData(itemList);
			json.setRtMsg("数据获取成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("没有找到相关数据！");
		}
		return json;
	}
}