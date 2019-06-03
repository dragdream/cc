package com.tianee.oa.subsys.project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.project.bean.TeeNeiBuTiXing;
import com.tianee.oa.subsys.project.model.TeeNeiBuTiXingModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeNeiBuTiXingService extends TeeBaseService{

	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;
	
	/**
	 * 获取所有提示信息 status 0:所有提示信息  1:未过期的提示信息  2:已过期的提示信息
	 * */
	public TeeEasyuiDataGridJson getNeiBuTiXingList(TeeDataGridModel m,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson easyJson =new TeeEasyuiDataGridJson();
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
		Date date=new Date();
		String format = TeeDateUtil.format(date, "yyyy-MM-dd");
		String hql="from TeeNeiBuTiXing where 1=1 ";
		//List<Object> object=new ArrayList<Object>();
		if(status==1){//未过期的提示信息
			hql+=" and creTime>="+format;
			//object.add(date);
		}else if(status==2){//过期的提示信息
			hql+=" and creTime<"+format;
			//object.add(date);
		}else{//所有的提示信息
			
		}
		//根据状态获取提示信息的数据
		List<TeeNeiBuTiXing> list = simpleDaoSupport.pageFindByList(hql, m.getFirstResult(), m.getRows(), null);
		//根据状态获取提示信息的总数
		Long countByList = simpleDaoSupport.countByList("select count(*) "+hql, null);
		if(list!=null && list.size()>0){
			for(TeeNeiBuTiXing nb:list){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("sid", nb.getSid());//ID
				map.put("content", nb.getContent());//提示内容
				String userIds=nb.getUserIds();
				map.put("userIds", userIds);//提示人员
				if(userIds!=null && !"".equals(userIds)){
					String userNames="";
					//获取提示人员的姓名
					List<TeePerson> find = simpleDaoSupport.find("from TeePerson where uuid in ("+userIds+")", null);
					if(find!=null && find.size()>0){
						for(TeePerson p:find){
							userNames+=p.getUserName()+",";
						}
					}
					if(!"".equals(userNames)){
						userNames=userNames.substring(0, userNames.length()-1);
					}
					map.put("userNames", userNames);
				}
				map.put("creTime", TeeDateUtil.format(nb.getCreTime(), "yyyy-MM-dd"));//提示时间
			    listMap.add(map);
			}
		}
		easyJson.setRows(listMap);
		easyJson.setTotal(countByList);
		return easyJson;
	}

	/**
	 * 添加或修改提示信息
	 * */
	public TeeJson addOrUpdate(TeeNeiBuTiXingModel model,
			HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		if(model.getSid()>0){//修改提示信息
			TeeNeiBuTiXing nb=new TeeNeiBuTiXing();
			nb.setContent(model.getContent());//提示信息
			nb.setCreateUser(person);//创建人
			nb.setCreTime(TeeDateUtil.format(model.getCreTime(), "yyyy-MM-dd"));//提示时间
			nb.setSid(model.getSid());//修改ID
			nb.setUserIds(model.getUserIds());//提示人员
			simpleDaoSupport.update(nb);
		}else{//添加提示信息
			TeeNeiBuTiXing nb=new TeeNeiBuTiXing();
			nb.setContent(model.getContent());//提示信息
			nb.setCreateUser(person);//创建人
			nb.setCreTime(TeeDateUtil.format(model.getCreTime(), "yyyy-MM-dd"));//提示时间
			nb.setUserIds(model.getUserIds());//提示人员
			simpleDaoSupport.save(nb);
		}
		json.setRtMsg("保存成功！");
		json.setRtState(true);
		return json;
	}

	/**
	 * 删除提示信息
	 * */
	public TeeJson deleteTiXing(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//提示信息ID
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			simpleDaoSupport.deleteOrUpdateByQuery("delete from TeeNeiBuTiXing where sid=?", new Object[]{sid});
		    json.setRtMsg("删除成功");
		    json.setRtState(true);
		}else{
			json.setRtMsg("删除失败");
		    json.setRtState(false);
		}
		return json;
	}

	/**
	 * 根据提示信息ID查找提示信息数据
	 * */
	public TeeJson getTiXingById(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeeNeiBuTiXingModel model=new TeeNeiBuTiXingModel();
		//提示信息ID
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			TeeNeiBuTiXing nb = (TeeNeiBuTiXing)simpleDaoSupport.get(TeeNeiBuTiXing.class, sid);
			model.setSid(nb.getSid());//提示信息ID
			model.setCreateUserId(nb.getCreateUser().getUuid());//创建人ID
			model.setCreTime(TeeDateUtil.format(nb.getCreTime(), "yyyy-MM-dd"));//提示时间
			String userIds=nb.getUserIds();
			model.setContent(nb.getContent());
			model.setUserIds(userIds);//提示人员ID
			if(userIds!=null && !"".equals(userIds)){
				String userNames="";
				//获取提示人员的姓名
				List<TeePerson> find = simpleDaoSupport.find("from TeePerson where uuid in ("+userIds+")", null);
				if(find!=null && find.size()>0){
					for(TeePerson p:find){
						userNames+=p.getUserName()+",";
					}
				}
				if(!"".equals(userNames)){
					userNames=userNames.substring(0, userNames.length()-1);
				}
				model.setUserNames(userNames);//提示人员姓名
			}
			
		}
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}

}
