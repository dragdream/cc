package com.tianee.oa.subsys.project.service;

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

import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.project.bean.TeeProject;
import com.tianee.oa.subsys.project.bean.TeeProjectCopy;
import com.tianee.oa.subsys.project.model.TeeProjectCopyModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeProjectCopyService extends TeeBaseService{

	@Autowired
	private TeeSmsManager smsManager;
	
	/**
	 * 抄送
	 * @param request
	 * @return
	 */
	public TeeJson copy(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
		String userIds=TeeStringUtil.getString(request.getParameter("userIds"));
		String[] userIdsArray=userIds.split(",");
		TeePerson user=null;
		TeeProjectCopy copy=null;
		for (String str : userIdsArray) {
			copy=new TeeProjectCopy();
			int id=TeeStringUtil.getInteger(str, 0);
			user=(TeePerson) simpleDaoSupport.get(TeePerson.class,id);
			if(user!=null){
				copy.setUser(user);
			}
			copy.setProjectId(projectId);
			copy.setReadFlag(0);
			copy.setCreateTime(new Date());
			copy.setFromUser(loginUser);
			simpleDaoSupport.save(copy);
		}
		
		TeeProject project=(TeeProject) simpleDaoSupport.get(TeeProject.class,projectId);
		//发送消息
		Map requestData1 = new HashMap();
		requestData1.put("content", loginUser.getUserName()+"抄送给您一个项目，项目名称："+project.getProjectName()+"，请注意查收。");
		requestData1.put("userListIds", userIds);
		requestData1.put("moduleNo", "060");
		requestData1.put("remindUrl","/system/subsys/project/mylookup/index.jsp");
		smsManager.sendSms(requestData1, loginUser);
		
		
		
		
		json.setRtState(true);
		json.setRtMsg("抄送成功！");
		return json;
	}

	
	/**
	 * 获取我的查阅列表  根据已阅   待阅
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getMyLookUpByReadFlag(
			HttpServletRequest request, TeeDataGridModel dm) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
        //获取当前登陆人
        TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        //获取页面上传来的阅读状态
        int readFlag=TeeStringUtil.getInteger(request.getParameter("readFlag"), 0);
		//获取项目主键
		String hql = " from TeeProjectCopy c where c.readFlag=? and c.user=? ";
		List param = new ArrayList();
		param.add(readFlag);
		param.add(loginUser);

		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, param));// 设置总记录数

		hql += " order by c.createTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeProjectCopy> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		List<TeeProjectCopyModel> modelList = new ArrayList<TeeProjectCopyModel>();
		if (list != null) {
			TeeProjectCopyModel model=null;
			TeePerson manager=null;
			TeePerson fromUser=null;
			List<Map> pMemberList=null;
			for (int i = 0; i < list.size(); i++) {
				model=new TeeProjectCopyModel();
				BeanUtils.copyProperties(list.get(i), model);
				//获取抄送时间
				model.setCreateTimeStr(sdf.format(list.get(i).getCreateTime()));
				//抄送人
				fromUser=list.get(i).getFromUser();
				if(fromUser!=null){
					model.setFromUserId(fromUser.getUuid());
					model.setFromUserName(fromUser.getUserName());
				}
				
				//获取项目主键
				String projectId=list.get(i).getProjectId();
				if(!("").equals(projectId)){
					Map map=simpleDaoSupport.executeNativeUnique(" select  * from project where uuid=? ", new Object[]{projectId});
					model.setProjectId(projectId);
					
					model.setProjectName(TeeStringUtil.getString(map.get("PROJECT_NAME")));
					model.setProjectNum(TeeStringUtil.getString(map.get("PROJECT_NUM")));
					
					model.setProjectBeginTimeStr(sdf1.format(map.get("BEGIN_TIME")));
					model.setProjectEndTimeStr(sdf1.format(map.get("END_TIME")));
					
					int managerId=(Integer) map.get("PROJECT_MANAGER_ID");
					manager=(TeePerson) simpleDaoSupport.get(TeePerson.class,managerId);
					model.setProjectManagerName(manager.getUserName());
					
					//获取项目成员
					pMemberList=simpleDaoSupport.executeNativeQuery("select * from project_member where project_id=? ", new Object[]{projectId}, 0, Integer.MAX_VALUE);
					String projectMemberNames="";
					
					for (Map m1 : pMemberList) {
						TeePerson p=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger(m1.get("member_id"), 0));
						projectMemberNames+=p.getUserName()+",";
					}
					if(projectMemberNames.endsWith(",")){
						projectMemberNames=projectMemberNames.substring(0,projectMemberNames.length()-1);
					}
					model.setProjectMemberNames(projectMemberNames);
					
				}
				
				modelList.add(model);
				
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}


	/**
	 * 改变阅读状态
	 * @param request
	 * @return
	 */
	public TeeJson changeReadFlag(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeProjectCopy c=(TeeProjectCopy) simpleDaoSupport.get(TeeProjectCopy.class,sid);
			c.setReadFlag(1);
			simpleDaoSupport.update(c);
			json.setRtState(true);
			json.setRtMsg("已阅！");
		}else{
			json.setRtState(false);
			json.setRtMsg("数据不存在！");
		}
		return json;
	}

	
	
}
