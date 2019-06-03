package com.tianee.oa.subsys.project.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.project.bean.TeeProjectType;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
@Service
public class TeeProjectTypeService extends TeeBaseService{

	
	
	/**
	 * 新增/编辑项目类型
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		int parentId=TeeStringUtil.getInteger(request.getParameter("parentId"), 0);
		String typeName=TeeStringUtil.getString(request.getParameter("typeName"));
		int orderNum=TeeStringUtil.getInteger(request.getParameter("orderNum"), 0);
		if(sid>0){//编辑
			TeeProjectType type=(TeeProjectType) simpleDaoSupport.get(TeeProjectType.class,sid);
		    type.setOrderNum(orderNum);
		    type.setTypeName(typeName);
		    type.setParentId(parentId);
		    simpleDaoSupport.update(type);
		    json.setRtMsg("编辑成功！");
		}else{//新增
			TeeProjectType type=new TeeProjectType();
		    type.setOrderNum(orderNum);
		    type.setTypeName(typeName);
		    type.setParentId(parentId);
		    simpleDaoSupport.save(type);
		    json.setRtMsg("保存成功！");
		}
		json.setRtState(true);
		return json;
	}

	
	/**
	 * 根据主键获取项目类型的详情
	 * @param request
	 * @return
	 */
	public TeeJson getProjectTypeBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeProjectType type=(TeeProjectType) simpleDaoSupport.get(TeeProjectType.class,sid);	
		    json.setRtData(type);
		    json.setRtState(true);
		    json.setRtMsg("数据获取成功 ！");
		}else{
			json.setRtState(false);
			json.setRtMsg("该项目类型不存在！");
		}
		return json;
	}


	/**
	 * 获取项目类型类表
	 * @param request
	 * @return
	 */
	public TeeJson getTypeList(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int psid=TeeStringUtil.getInteger(request.getParameter("psid"), 0);
		List<TeeProjectType> list=simpleDaoSupport.executeQuery(" from TeeProjectType where parentId=? order by orderNum asc", new Object[]{psid});
		json.setRtState(true);
		json.setRtData(list);
		json.setRtMsg("数据获取成功！");
		return json;
	}


	/**
	 * 根据主键删除项目类型
	 * @param request
	 * @return
	 */
	public TeeJson deleteBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeProjectType type=(TeeProjectType) simpleDaoSupport.get(TeeProjectType.class,sid);
			simpleDaoSupport.deleteByObj(type);
			json.setRtData(type);
			json.setRtMsg("删除成功！");
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("该项目类型不存在！");
		}
		return json;
	}

/**
 * 获取某种类型下的所有任务
 * */
	public TeeJson findTaskByType(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		List<TeeProjectType> listMap=new ArrayList<TeeProjectType>();
		int psid = TeeStringUtil.getInteger(request.getParameter("psid"),0);
		getTaskByType(psid,listMap);
		json.setRtData(listMap);
		json.setRtState(true);
		return json;
	}


private List<TeeProjectType> getTaskByType(int psid,List<TeeProjectType> listMap) {
	List<TeeProjectType> pList=new ArrayList<TeeProjectType>();
	pList=simpleDaoSupport.find("from TeeProjectType where parentId=?", new Object[]{psid});
    listMap.addAll(pList);
    if(pList!=null && pList.size()>0){
    	for(TeeProjectType p:pList){
    		getTaskByType(p.getSid(),listMap);
    	}
    }
	return listMap;
}

/**
 * 添加任务模板数据
 * */
public TeeJson addTaskMb(HttpServletRequest request) {
	TeeJson json=new TeeJson();
	String strNodes=request.getParameter("strNodes");//json字符串
	int typeId=TeeStringUtil.getInteger(request.getParameter("typeId"),0);//类别id
	List<TeeProjectType> find = simpleDaoSupport.find("from TeeProjectType where parentId=?", new Object[]{typeId});
	if(find!=null && find.size()>0){
		for(TeeProjectType p:find){
			deleteTaskNextAll(p.getSid());
		}
		simpleDaoSupport.deleteOrUpdateByQuery("delete from TeeProjectType where parentId=?", new Object[]{typeId});
	}
	
	JSONArray array = JSONArray.fromObject(strNodes);//
	List<Map<String,Object>> mapListJson = (List)array;//json转list
	if(mapListJson!=null && mapListJson.size()>0){
		for(int i=0;i<mapListJson.size();i++){
			Map<String, Object> map = mapListJson.get(i);
			String name = TeeStringUtil.getString(map.get("name"));//名称
			int id = TeeStringUtil.getInteger(map.get("id"), 0);//id 
			int pId = TeeStringUtil.getInteger(map.get("pId"), 0);//pid
			int pNo = TeeStringUtil.getInteger(map.get("pNo"), 0);//pid
			if(pId==0){
				TeeProjectType type=new TeeProjectType();
			    type.setOrderNum(pNo);
			    type.setTypeName(name);
			    type.setParentId(typeId);
			    type.setMbId(id);
				Serializable save=simpleDaoSupport.save(type);
				mapListJson.remove(i);
				saveNextTask(TeeStringUtil.getInteger(save,0),id,mapListJson);
			}
		}
		json.setRtState(true);
		json.setRtMsg("保存成功");
	}else{
		json.setRtState(false);
		json.setRtMsg("保存失败");
	}
	return json;
}

	private void deleteTaskNextAll(int sid) {
		List<TeeProjectType> find = simpleDaoSupport.find("from TeeProjectType where parentId=?", new Object[]{sid});
		if(find!=null && find.size()>0){
			for(TeeProjectType p:find){
				deleteTaskNextAll(p.getSid());
			}
			simpleDaoSupport.deleteOrUpdateByQuery("delete from TeeProjectType where parentId=?", new Object[]{sid});
		}
	
  }


	public void saveNextTask(int saveId,int mbPId,List<Map<String,Object>> listMap){
		if(listMap!=null && listMap.size()>0){
			for(int i=0;i<listMap.size();i++){
				Map<String, Object> map = listMap.get(i);
				String name = TeeStringUtil.getString(map.get("name"));//名称
				int id = TeeStringUtil.getInteger(map.get("id"), 0);//id 
				int pId = TeeStringUtil.getInteger(map.get("pId"), 0);//pid
				int pNo = TeeStringUtil.getInteger(map.get("pNo"), 0);//pid
				if(mbPId==pId){
					TeeProjectType type=new TeeProjectType();
				    type.setOrderNum(pNo);
				    type.setTypeName(name);
				    type.setParentId(saveId);
				    type.setMbId(id);
					Serializable save=simpleDaoSupport.save(type);
					saveNextTask(TeeStringUtil.getInteger(save,0),id,listMap);
					//listMap.remove(i);
				}
			}
		}
	}

    /**
     * 获取某个类别下的所有任务
     * */
	public TeeJson getTaskAllByLei(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int typeId=TeeStringUtil.getInteger(request.getParameter("typeId"),0);
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		List<Integer> listI=new ArrayList<Integer>();
		List<TeeProjectType> find = simpleDaoSupport.find("from TeeProjectType where parentId=?", new Object[]{typeId});
		if(find!=null && find.size()>0){
			for(TeeProjectType type:find){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("id", type.getSid());
				map.put("name", type.getTypeName());
				map.put("pId", 0);
				List<TeeProjectType> find2 = simpleDaoSupport.find("from TeeProjectType where parentId=?", new Object[]{type.getSid()});
                if(find2!=null && find2.size()>0){
                	map.put("open", true);
                	listMap.add(map);
                	listI.add(type.getMbId());
                	//mbIds+=type.getMbId()+",";
                	getTaskNexttByLei(type.getSid(),find2,listI,listMap);
                }else{
                	listMap.add(map);
                	listI.add(type.getMbId());
                	//mbIds+=type.getMbId()+",";
                }
			}
		}
//		if(!"".equals(mbIds)){
//			mbIds=mbIds.substring(0, mbIds.length()-1);
//		}
		String str="";
		if(listI!=null && listI.size()>0){
			 str= listI.toString();
			 str=str.substring(1, str.length()-1);
		}
		json.setRtData(listMap);
		json.setRtMsg(str);
		return json;
	}


	private void getTaskNexttByLei(int pId,List<TeeProjectType> list, List<Integer> listI,
			List<Map<String, Object>> listMap) {
			for(TeeProjectType type:list){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("id", type.getSid());
				map.put("name", type.getTypeName());
				map.put("pId", pId);
				List<TeeProjectType> find2 = simpleDaoSupport.find("from TeeProjectType where parentId=?", new Object[]{type.getSid()});
                if(find2!=null && find2.size()>0){
                	map.put("open", true);
                	listMap.add(map);
                	listI.add(type.getMbId());
                	//mbIds+=type.getMbId()+",";
                	getTaskNexttByLei(type.getSid(),find2,listI,listMap);
                }else{
                	listMap.add(map);
                	listI.add(type.getMbId());
                	//mbIds+=type.getMbId()+",";
                }
			}
		
	}
	/**
     * 获取某个类别下的所有任务
     * */
	public TeeJson getTaskIdByLei(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int typeId=TeeStringUtil.getInteger(request.getParameter("typeId"),0);
		List<Integer> listI=new ArrayList<Integer>();
		List<TeeProjectType> find = simpleDaoSupport.find("from TeeProjectType where parentId=?", new Object[]{typeId});
		if(find!=null && find.size()>0){
			for(TeeProjectType type:find){
				List<TeeProjectType> find2 = simpleDaoSupport.find("from TeeProjectType where parentId=?", new Object[]{type.getSid()});
                if(find2!=null && find2.size()>0){
                	listI.add(type.getMbId());
                	getTaskIdNexttByLei(find2,listI);
                }else{
                	listI.add(type.getMbId());
                }
			}
		}
		String str="";
		if(listI!=null && listI.size()>0){
			 str= listI.toString();
			 str=str.substring(1, str.length()-1);
		}
		json.setRtData(str);
		return json;
	}
	private void getTaskIdNexttByLei(List<TeeProjectType> list, List<Integer> listI) {
			for(TeeProjectType type:list){
				List<TeeProjectType> find2 = simpleDaoSupport.find("from TeeProjectType where parentId=?", new Object[]{type.getSid()});
                if(find2!=null && find2.size()>0){
                	listI.add(type.getMbId());
                	getTaskIdNexttByLei(find2,listI);
                }else{
                	listI.add(type.getMbId());
                }
			}
		
	}
}
