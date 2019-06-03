package com.tianee.oa.subsys.zhidao.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.project.bean.TeeTask;
import com.tianee.oa.subsys.zhidao.bean.TeeZhiDaoCat;
import com.tianee.oa.subsys.zhidao.bean.TeeZhiDaoQuestion;
import com.tianee.oa.subsys.zhidao.model.TeeZhiDaoCatModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeZhiDaoCatService extends TeeBaseService{

	@Autowired
	private TeePersonService personService;
	
	/**
	 * 新建/编辑分类
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String catName=TeeStringUtil.getString(request.getParameter("catName"));
		int parentCatId=TeeStringUtil.getInteger(request.getParameter("parentCatId"), 0);
		String managerIds=TeeStringUtil.getString(request.getParameter("managerIds"));
		TeeZhiDaoCat parent=null;
		if(parentCatId!=0){
			parent=(TeeZhiDaoCat) simpleDaoSupport.get(TeeZhiDaoCat.class,parentCatId);
		}
		if(sid>0){//编辑
			TeeZhiDaoCat cat=(TeeZhiDaoCat) simpleDaoSupport.get(TeeZhiDaoCat.class,sid);
		    if(cat!=null){
		    	cat.setCatName(catName);
		    	cat.setManagerIds(managerIds);
		    	cat.setParentCat(parent);
		    	simpleDaoSupport.update(cat);
		    	json.setRtState(true);
		    }else{
		    	json.setRtState(false);
		    	json.setRtMsg("该分类已不存在！");
		    }
		}else{//新建
			TeeZhiDaoCat cat=new TeeZhiDaoCat();
			cat.setCatName(catName);
	    	cat.setManagerIds(managerIds);
	    	cat.setParentCat(parent);
	    	simpleDaoSupport.save(cat);
	    	json.setRtState(true);
		}
		return json;
	}

	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeZhiDaoCat cat=(TeeZhiDaoCat) simpleDaoSupport.get(TeeZhiDaoCat.class,sid);
		if(cat!=null){
			TeeZhiDaoCatModel model=parseToModel(cat);
			json.setRtState(true);
			json.setRtData(model);
		}else{
			json.setRtState(false);
			json.setRtMsg("该分类已不存在！");
		}
		
		return json;
	}


	/**
	 * 实体类转换成model
	 * @param cat
	 * @return
	 */
	private TeeZhiDaoCatModel parseToModel(TeeZhiDaoCat cat) {
		TeeZhiDaoCatModel model=new TeeZhiDaoCatModel();
		BeanUtils.copyProperties(cat, model);
		if(cat.getParentCat()!=null){
			model.setParentCatId(cat.getParentCat().getSid());
			model.setParentCatName(cat.getParentCat().getCatName());
		}else{
			model.setParentCatId(0);
			model.setParentCatName("");
		}
		
		if(!TeeUtility.isNullorEmpty(cat.getManagerIds())){
			String [] userInfo=personService.getPersonNameAndUuidByUuids(cat.getManagerIds());
			model.setManagerNames(userInfo[1]);
		}else{
		    model.setManagerNames("");	
		}
		
		return model;
	}


	
	/**
	 * 获取所有的一级分类
	 * @param request
	 * @return
	 */
	public TeeJson getFirstLevelCat(HttpServletRequest request) {
	    TeeJson json=new TeeJson();
	    String hql=" from TeeZhiDaoCat cat where cat.parentCat is null ";
	    List<TeeZhiDaoCat> catList=simpleDaoSupport.executeQuery(hql,null);
		List<TeeZhiDaoCatModel> modelList=new ArrayList<TeeZhiDaoCatModel>();
		TeeZhiDaoCatModel model=null;
		if(catList!=null&&catList.size()>0){
			for (TeeZhiDaoCat cat : catList) {
				model=parseToModel(cat);
				modelList.add(model);
			}
		}
		json.setRtState(true);
		json.setRtData(modelList);
	    return json;
	}


	/**
	 * 获取所有的分类
	 * @param request
	 * @param dm
	 * @return
	 */
	public List getAllCat(HttpServletRequest request, TeeDataGridModel dm) {
		TeePerson loginUser =(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		List<TeeZhiDaoCat> catList = simpleDaoSupport.executeQuery(" from TeeZhiDaoCat  order by sid",new Object[]{});
		List<Map> catMapList = new ArrayList();
		
		//找出所有一级节点
		TeeZhiDaoCatModel model=null;
		for (TeeZhiDaoCat cat : catList) {
			model=parseToModel(cat);
			if(cat.getParentCat()==null){
				Map map=new HashMap();
				map.put("sid", cat.getSid());
				map.put("catName", cat.getCatName());
				map.put("managerNames", model.getManagerNames());
				map.put("children", new ArrayList());
				catMapList.add(map);
			}
		}
		//从一级节点开始往下找
		for(Map data:catMapList){
			setChildInfos(catList,catMapList,data,loginUser);
		}
		return catMapList;
	}
   
	
	private void setChildInfos(List<TeeZhiDaoCat> catList,List<Map> catMapList,Map catMap,TeePerson loginUser){
		//先获取该节点下面的所有子节点
		List<Map> childList = new ArrayList();
		//将taskMap的所有子节点加入到childList中
		TeeZhiDaoCatModel model=null;
		for(TeeZhiDaoCat cat:catList){
			model=parseToModel(cat);
			if(cat.getParentCat()!=null){	
				if(cat.getParentCat().getSid()==Integer.parseInt(catMap.get("sid")+"")){
					Map map=new HashMap();
					map.put("sid", cat.getSid());
					map.put("catName", cat.getCatName());
					map.put("managerNames", model.getManagerNames());	
					map.put("children", new ArrayList());
					childList.add(map);
				}
				
			}
			
		}
		((List)catMap.get("children")).addAll(childList);
		
		for(Map data:childList){
			setChildInfos(catList,catMapList,data,loginUser);
		}

	}


	
	/**
	 * 根据主键删除
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		//判断有没有问题关联该分类
		List<TeeZhiDaoQuestion> qList=simpleDaoSupport.executeQuery(" from TeeZhiDaoQuestion q where q.cat.sid=? ", new Object[]{sid});
		if(qList!=null&&qList.size()>0){
			json.setRtState(false);
			json.setRtMsg("已有问题关联该分类，暂且不可删除！");
		}else{
			//判断该分类下有没有子分类
			List<TeeZhiDaoCat> cList=simpleDaoSupport.executeQuery(" from TeeZhiDaoCat c where c.parentCat.sid=? ", new Object[]{sid});
			if(cList!=null&&cList.size()>0){
				json.setRtState(false);
				json.setRtMsg("该分类下存在子分类，暂且不可删除！");
			}else{
				TeeZhiDaoCat cat=(TeeZhiDaoCat) simpleDaoSupport.get(TeeZhiDaoCat.class,sid);
				if(cat!=null){
					simpleDaoSupport.deleteByObj(cat);
					json.setRtState(true);
				}else{
					json.setRtState(false);
					json.setRtMsg("该分类不存在！");	
				}
			}
			
		}
		
		
		return json;
	}


	/**
	 * 获取所有的分类  (分类最多只有两级)
	 * @param request
	 * @return
	 */
	public TeeJson getAllCat1(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		List<Map> mapList=new  ArrayList<Map>();
		//先取出一级分类
		String hql=" from TeeZhiDaoCat where  parentCat is null ";
		String hql1=" from TeeZhiDaoCat where  parentCat.sid=? ";
		List<TeeZhiDaoCat> list=simpleDaoSupport.executeQuery(hql, null);
		if(list!=null&&list.size()>0){
			Map map=null;
			TeeZhiDaoCatModel model=null;
			List<TeeZhiDaoCat> list1=null;
			
			for (TeeZhiDaoCat teeZhiDaoCat : list) {
				map=new HashMap();
				model=parseToModel(teeZhiDaoCat);
				map.put("parent",model);
				//判断该分类是否存在子分类
				List<TeeZhiDaoCatModel> children=new ArrayList<TeeZhiDaoCatModel>();
				TeeZhiDaoCatModel model1=null;
				list1=simpleDaoSupport.executeQuery(hql1, new Object[]{teeZhiDaoCat.getSid()});
			    if(list1!=null&&list1.size()>0){
			    	for (TeeZhiDaoCat cat : list1) {
			    		model1=parseToModel(cat);
					    children.add(model1);
					}
			    }
			    
			    map.put("children",children);
			    
			    mapList.add(map);
			}
		}
		
		json.setRtState(true);
		json.setRtData(mapList);
		return json;
	}

	
	
    /**
     * 判断当前登陆人是不是当前分类的管理员  0=不是管理员  1=是管理员
     * @param request
     * @return
     */
	public TeeJson isManager(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int catId=TeeStringUtil.getInteger(request.getParameter("catId"),0);
		TeeZhiDaoCat cat=(TeeZhiDaoCat) simpleDaoSupport.get(TeeZhiDaoCat.class,catId);
		if(cat!=null){
			String managerIds=cat.getManagerIds();
			if(!TeeUtility.isNullorEmpty(managerIds)){
				String[]ids=managerIds.split(",");
				for (String userId : ids) {
				    if(TeeStringUtil.getInteger(userId,0)==loginUser.getUuid()){
				    	json.setRtData(1);
				    	break;
				    }else{
				    	json.setRtData(0);
				    }
				}
			}else{
				json.setRtData(0);
			}
		}
		json.setRtState(true);
		return json;
	}
}
