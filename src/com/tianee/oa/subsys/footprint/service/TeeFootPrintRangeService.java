package com.tianee.oa.subsys.footprint.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.subsys.footprint.bean.TeeFootPrintRange;
import com.tianee.oa.subsys.footprint.model.TeeFootPrintRangeModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;


@Service
public class TeeFootPrintRangeService extends TeeBaseService{

	@Autowired
	private TeePersonService personService;
	
	/**
	 * 获取所有的电子围栏列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getAllList(HttpServletRequest request,
			TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		String hql=" from TeeFootPrintRange fpr ";
		
		// 设置总记录数
		json.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, null));		
		List<TeeFootPrintRange> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查		
		List<TeeFootPrintRangeModel> listModel = new ArrayList<TeeFootPrintRangeModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
	   json.setRows(listModel);
	   
	   return json;
	}

	
	
	/**
	 * 实体类转换成model
	 * @param teeFootPrintRange
	 * @return
	 */
	private TeeFootPrintRangeModel parseModel(
			TeeFootPrintRange teeFootPrintRange) {
		TeeFootPrintRangeModel model=new TeeFootPrintRangeModel();
		BeanUtils.copyProperties(teeFootPrintRange, model);
        
		List<TeePerson> users=teeFootPrintRange.getUsers();
		if(users!=null&&users.size()>0){
			String userIds="";
			String userNames="";
			
			for (TeePerson teePerson : users) {
				userIds+=teePerson.getUuid()+",";
				userNames+=teePerson.getUserName()+",";
			}
			
			if(userIds.endsWith(",")){
				userIds=userIds.substring(0, userIds.length()-1);
			}
			
			if(userNames.endsWith(",")){
				userNames=userNames.substring(0, userNames.length()-1);
			}
			
			model.setUserIds(userIds);
			model.setUserNames(userNames);
		}else{
			model.setUserIds("");
			model.setUserNames("");
		}
		
		return model;
	}



	
	/**
	 * 新建/编辑
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的参数
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String rangeName=TeeStringUtil.getString(request.getParameter("rangeName"));
		String userIds=TeeStringUtil.getString(request.getParameter("userIds"));
		String userNames=TeeStringUtil.getString(request.getParameter("userNames"));
		List<TeePerson> users=personService.getPersonByUuids(userIds);
		if(sid>0){//编辑
			TeeFootPrintRange range=(TeeFootPrintRange) simpleDaoSupport.get(TeeFootPrintRange.class,sid);
			range.setRangeName(rangeName);
			range.getUsers().clear();
		    range.setUsers(users);
		    simpleDaoSupport.update(range);
		    json.setRtState(true);
		    json.setRtMsg("编辑成功！");
			
		}else{//新建
			TeeFootPrintRange range=new TeeFootPrintRange();
			range.setRangeName(rangeName);
		    range.setUsers(users);
		    simpleDaoSupport.save(range);
		    json.setRtState(true);
		    json.setRtMsg("新建成功！");
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
		//获取前台页面传来的sid
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeFootPrintRange range=(TeeFootPrintRange) simpleDaoSupport.get(TeeFootPrintRange.class,sid);
		if(range!=null){
			TeeFootPrintRangeModel model=parseModel(range);
			json.setRtState(true);
			json.setRtData(model);
		}else{
			json.setRtState(false);
			json.setRtMsg("信息获取失败！");
		}	
		return json;
	}



	/**
	 * 根据主键删除
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取前台传来的sid
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeFootPrintRange range=(TeeFootPrintRange) simpleDaoSupport.get(TeeFootPrintRange.class,sid);
		if(range!=null){
			simpleDaoSupport.deleteByObj(range);
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("该电子围栏已不存在！");
		}
		return json;
	}



	/**
	 * 设计范围
	 * @param request
	 * @return
	 */
	public TeeJson design(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		String rangeStr=TeeStringUtil.getString(request.getParameter("rangeStr"));
		TeeFootPrintRange range=(TeeFootPrintRange) simpleDaoSupport.get(TeeFootPrintRange.class,sid);
		if(range!=null){
			range.setRangeStr(rangeStr);
			simpleDaoSupport.save(range);
			
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("该电子围栏已不存在！");
		}
		return json;
	}

	
	
}
