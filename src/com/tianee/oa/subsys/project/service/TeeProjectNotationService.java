package com.tianee.oa.subsys.project.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.project.bean.TeeProjectNotation;
import com.tianee.oa.subsys.project.model.TeeProjectNotationModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
@Service
public class TeeProjectNotationService extends TeeBaseService{

	@Autowired
	private TeeSmsManager smsManager;
	
	/**
	 * 新建/编辑批注
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		//获取页面上传来的项目的主键
		String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
		//获取页面上传来的批注的主键
	    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	    //获取页面上传来的批注的内容
	    String content=TeeStringUtil.getString(request.getParameter("content"));
	    if(sid>0){//编辑
	    	TeeProjectNotation n=(TeeProjectNotation) simpleDaoSupport.get(TeeProjectNotation.class,sid);
	    	n.setContent(content);
	    	simpleDaoSupport.update(n);
	    	json.setRtState(true);
	    	json.setRtMsg("编辑成功！");
	    }else{//新建
	    	TeeProjectNotation n=new TeeProjectNotation();
	    	n.setContent(content);
	    	n.setCreater(loginUser);
	    	n.setCreateTime(new Date());
	    	n.setProjectId(projectId);
	    	simpleDaoSupport.save(n);
	    	
	    	
	    	//获取项目的基本信息
			Map map=simpleDaoSupport.executeNativeUnique(" select * from project where uuid=? ", new Object[]{projectId});
			String projectName=TeeStringUtil.getString(map.get("PROJECT_NAME"));
			int managerId=TeeStringUtil.getInteger(map.get("PROJECT_MANAGER_ID"), 0);
			int createrId=TeeStringUtil.getInteger(map.get("PROJECT_CREATER_ID"), 0);
			
			Set set=new HashSet();
			set.add(createrId);
			set.add(managerId);
			//获取项成员
	    	List<Map> memberList=simpleDaoSupport.executeNativeQuery(" select * from project_member where project_id=?", new Object[]{projectId}, 0, Integer.MAX_VALUE);
	    	for (Map m : memberList){
	    		set.add(TeeStringUtil.getInteger(m.get("member_id"), 0));
			}
	    	//获取项目观察者
	    	List<Map> viewList=simpleDaoSupport.executeNativeQuery(" select * from project_view where project_id=?", new Object[]{projectId}, 0, Integer.MAX_VALUE);
	    	for (Map m : viewList){
	     		set.add(TeeStringUtil.getInteger(m.get("view_id"), 0));
	     		  
			}
			String userListIds="";//项目创建人  项目成员  项目观察者的id字符串
			for (Object obj : set) {
			   int id=TeeStringUtil.getInteger(obj, 0);
			   if(id!=0){
				   userListIds+=id+",";
			   }
			}
			if(userListIds.endsWith(",")){
				userListIds=userListIds.substring(0,userListIds.length()-1);
			}
	    	
	    	
	    	
	    	// 发送消息
	    	Map requestData1 = new HashMap();
	    	requestData1.put("content", loginUser.getUserName()+"在项目“"+projectName+"”中提交了一条批注，请注意查看。");
	    	requestData1.put("userListIds", userListIds);
	    	requestData1.put("moduleNo", "060");
	    	requestData1.put("remindUrl","/system/subsys/project/projectdetail/notationList.jsp?uuid="+projectId);
	    	smsManager.sendSms(requestData1, loginUser);

	    	
	    	json.setRtState(true);
	    	json.setRtMsg("新建成功！");
	    }
		return json;
	}

	
	/**
	 * 根据项目主键获取批注列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getNotationListByProjectId(
			HttpServletRequest request, TeeDataGridModel dm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
		
		long total=simpleDaoSupport.count(" select count(*) from TeeProjectNotation n where n.projectId=? ", new Object[]{projectId});
		json.setTotal(total);
		List<TeeProjectNotation> list=simpleDaoSupport.executeQuery(" from TeeProjectNotation n where n.projectId=? ", new Object[]{projectId});
		List<TeeProjectNotationModel> modelList=new ArrayList<TeeProjectNotationModel>();
		TeeProjectNotationModel model=null;
		for (TeeProjectNotation n : list) {
			model=parseToModel(n);
			modelList.add(model);
		}	
		json.setRows(modelList);
		return json;
	}


	/**
	 * 将实体类转换成model
	 * @param n
	 * @return
	 */
	private TeeProjectNotationModel parseToModel(TeeProjectNotation n) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeProjectNotationModel model=new TeeProjectNotationModel();
		BeanUtils.copyProperties(n, model);
		if(n.getCreater()!=null){
			model.setCreaterId(n.getCreater().getUuid());
			model.setCreaterName(n.getCreater().getUserName());
		}
		if(n.getCreateTime()!=null){
			model.setCreateTimeStr(sdf.format(n.getCreateTime()));
		}
		return model;
	}


	/**
	 * 根据主键获取批注详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取批注的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeProjectNotation n=(TeeProjectNotation) simpleDaoSupport.get(TeeProjectNotation.class,sid);
		TeeProjectNotationModel model=parseToModel(n);
		json.setRtData(model);
		json.setRtState(true);
		json.setRtMsg("数据获取成功！");
		return json;
	}


	/**
	 * 根据主键删除项目批注
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的批注的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeProjectNotation n=(TeeProjectNotation) simpleDaoSupport.get(TeeProjectNotation.class,sid);
		simpleDaoSupport.deleteByObj(n);
		json.setRtState(true);
		json.setRtMsg("删除成功 ！");
		return json;
	}


	/**
	 * 根据项目主键  获取批注列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeJson getNotationsByProjectId(HttpServletRequest request,
			TeeDataGridModel dm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeJson json=new TeeJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
		
		List<TeeProjectNotation> list=simpleDaoSupport.executeQuery(" from TeeProjectNotation n where n.projectId=? ", new Object[]{projectId});
		List<TeeProjectNotationModel> modelList=new ArrayList<TeeProjectNotationModel>();
		TeeProjectNotationModel model=null;
		for (TeeProjectNotation n : list) {
			model=parseToModel(n);
			modelList.add(model);
		}	
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}
}
