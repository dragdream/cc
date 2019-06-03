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
import com.tianee.oa.subsys.project.bean.TeeProjectCommunication;
import com.tianee.oa.subsys.project.model.TeeProjectCommunicationModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeProjectCommunicationService extends TeeBaseService{

	@Autowired
	private TeeSmsManager smsManager;
	
	/**
	 * 新增或者编辑交流
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String content=TeeStringUtil.getString(request.getParameter("content"));
		String uuid=TeeStringUtil.getString(request.getParameter("projectId"));//项目id
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){//编辑
			TeeProjectCommunication  communication=(TeeProjectCommunication) simpleDaoSupport.get(TeeProjectCommunication.class,sid);
			communication.setContent(content);
			communication.setCreateUser(loginUser);
			communication.setProjectId(uuid);
			simpleDaoSupport.update(communication);
			json.setRtState(true);
			json.setRtMsg("编辑成功！");
				
		}else{//新增
			TeeProjectCommunication  communication=new TeeProjectCommunication();
			communication.setContent(content);
			communication.setCreateTime(new Date());
			communication.setCreateUser(loginUser);
			communication.setProjectId(uuid);
			simpleDaoSupport.save(communication);
			
			
			//获取项目的基本信息
			Map map=simpleDaoSupport.executeNativeUnique(" select * from project where uuid=? ", new Object[]{uuid});
			String projectName=TeeStringUtil.getString(map.get("PROJECT_NAME"));
			int managerId=TeeStringUtil.getInteger(map.get("PROJECT_MANAGER_ID"), 0);
			int createrId=TeeStringUtil.getInteger(map.get("PROJECT_CREATER_ID"), 0);
			
			Set set=new HashSet();
			set.add(createrId);
			set.add(managerId);
			//获取项成员
	    	List<Map> memberList=simpleDaoSupport.executeNativeQuery(" select * from project_member where project_id=?", new Object[]{uuid}, 0, Integer.MAX_VALUE);
	    	for (Map m : memberList){
	    		set.add(TeeStringUtil.getInteger(m.get("member_id"), 0));
			}
	    	//获取项目观察者
	    	List<Map> viewList=simpleDaoSupport.executeNativeQuery(" select * from project_view where project_id=?", new Object[]{uuid}, 0, Integer.MAX_VALUE);
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
			
			
			// 发送消息     创建人、负责人、观察者、成员
			Map requestData1 = new HashMap();
			requestData1.put("content", loginUser.getUserName()+"在项目“"+projectName+"”中创建了一条交流信息，请注意查看。");
			requestData1.put("userListIds", userListIds);
			requestData1.put("moduleNo", "060");
			requestData1.put("remindUrl","/system/subsys/project/myproject/communication.jsp?uuid="+uuid);
			smsManager.sendSms(requestData1, loginUser);
			
			
			
			json.setRtState(true);
			json.setRtMsg("添加成功！");
		}

		return json;
	}

	
	
	/**
	 * 根据项目主键  获取对应的项目交流
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getCommunicationPage(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//获取项目主键
		String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
		String hql = " from TeeProjectCommunication c where c.projectId=? ";
		List param = new ArrayList();
		param.add(projectId);

		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, param));// 设置总记录数

		hql += " order by c.createTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeProjectCommunication> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		List<TeeProjectCommunicationModel> modelList = new ArrayList<TeeProjectCommunicationModel>();
		if (list != null) {
			TeePerson crUser=null;
			TeeProjectCommunicationModel model=null;
			for (int i = 0; i < list.size(); i++) {
				model=new TeeProjectCommunicationModel();
				BeanUtils.copyProperties(list.get(i), model);
				crUser=list.get(i).getCreateUser();
				if(crUser!=null){
					model.setCreateUserName(crUser.getUserName());
					model.setCreateUserUuid(crUser.getUuid());
				}
				Date crDate=list.get(i).getCreateTime();
				String crTimeStr=sdf.format(crDate);
				model.setCreateTimeStr(crTimeStr);
				
				modelList.add(model);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}


	
    /**
     * 删除项目交流
     * @param request
     * @return
     */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson  json=new TeeJson();
	    //获取主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			TeeProjectCommunication c=(TeeProjectCommunication) simpleDaoSupport.get(TeeProjectCommunication.class,sid);
		    simpleDaoSupport.deleteByObj(c);
		    json.setRtState(true);
		    json.setRtMsg("删除成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("该交流不存在！");
		}
		return json;
	}



	/**
	 * 根据主键获取项目交流的详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeJson json=new  TeeJson();
		//获取主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeProjectCommunication c=(TeeProjectCommunication) simpleDaoSupport.get(TeeProjectCommunication.class,sid);
			TeeProjectCommunicationModel model=new TeeProjectCommunicationModel();
			BeanUtils.copyProperties(c, model);
			TeePerson crUser=c.getCreateUser();
			if(crUser!=null){
				model.setCreateUserName(crUser.getUserName());
				model.setCreateUserUuid(crUser.getUuid());
			}
			Date crDate=c.getCreateTime();
			String crTimeStr=sdf.format(crDate);
			model.setCreateTimeStr(crTimeStr);
			
			json.setRtData(model);
			json.setRtState(true);
			json.setRtMsg("数据获取成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}

}
