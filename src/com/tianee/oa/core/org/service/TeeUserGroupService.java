package com.tianee.oa.core.org.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserGroup;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserGroupDao;
import com.tianee.oa.core.org.model.TeeUserGroupModel;
import com.tianee.oa.core.workFlowFrame.dataloader.TeeFixedFlowDataLoaderInterface;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class TeeUserGroupService extends TeeBaseService{
    @Autowired
    TeeUserGroupDao userGroupDao;
    @Autowired
    TeePersonDao personDao;
    
    @Autowired
	private TeePersonService personService;
    
    @Autowired
	private TeeFixedFlowDataLoaderInterface fixedFlowDataLoader;
    /**
     * 新增或者更新
     * @param para
     * @return
     */
    public void addUpdateUserGroup(TeeUserGroupModel userGroupModel){
    	TeeUserGroup userGroup = new TeeUserGroup();
    	if(TeeUtility.isNullorEmpty(userGroupModel.getUuid())){
    		BeanUtils.copyProperties(userGroupModel, userGroup);
    		if(!TeeUtility.isNullorEmpty(userGroupModel.getUserListIds())&&!TeeUtility.isNullorEmpty(userGroupModel.getUserListNames())){
    			List<TeePerson> userList = new ArrayList<TeePerson>();
    			userList =personDao.getPersonByUuids(userGroupModel.getUserListIds());
    			userGroup.setUserList(userList);
    		}
    		userGroupDao.save(userGroup);
    	}else{
    		BeanUtils.copyProperties(userGroupModel, userGroup);
    		userGroup.setSid(Integer.parseInt(userGroupModel.getUuid()));
    		if(!TeeUtility.isNullorEmpty(userGroupModel.getUserListIds())&&!TeeUtility.isNullorEmpty(userGroupModel.getUserListNames())){
    			List<TeePerson> userList = new ArrayList<TeePerson>();
    			userList =personDao.getPersonByUuids(userGroupModel.getUserListIds());
    			userGroup.setUserList(userList);
    		}
    		userGroupDao.update(userGroup);
    	}
    }
    
	public void setUserGroupDao(TeeUserGroupDao userGroupDao) {
		this.userGroupDao = userGroupDao;
	}
	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}
	
    /**
     * 获取分组
     * @param paraName
     * @return
     */
    public List<TeeUserGroup> getUserGroup(){

    	List<TeeUserGroup> list = userGroupDao.getUserGroup();

    	return list;
    }
    
    

    /**
     * 获取个人用户分组
     * @param paraName
     * @return
     */
    public List<TeeUserGroup> getUserGroupByPersonId(int personId){
    	List<TeeUserGroup> list = userGroupDao.getUserGroupByPersonUuid(personId);
    	return list;
    }
    
    /**
     * 获取个人用户分组
     * @param paraName
     * @return
     */
    public List<TeeUserGroup> getPublicUserGroupAndPerson(int personId){
    	List<TeeUserGroup> list = userGroupDao.getPublicUserGroupAndPerson(personId);
    	return list;
    }
    
    
    
    
	/**
	 * 查询 byId
	 * @param TeeUserGroupDao
	 */
	public TeeUserGroup selectUserGroupByUuid(int uuid) {
		TeeUserGroup userGroup = (TeeUserGroup) userGroupDao.selectUserGroupById(uuid);
		return userGroup;
	}
	
	
	/**
	 * 删除
	 * @param TeeUserGroup
	 */
	public void delUserGroup(TeeUserGroup userGroup) {

		TeeUserGroup fUserGroup  = userGroupDao.get(userGroup.getSid());
		fUserGroup.setUserList(null);
		userGroupDao.deleteByObj(fUserGroup);
		
	}

	
	
	
	public TeeJson getPublicAndPersonalUserGroupWorkFlow(
			HttpServletRequest request) {
		TeeJson json = new TeeJson();
	    TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    loginPerson = personService.selectByUuid(loginPerson.getUuid());
	    
	    int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);//步骤ID
	    int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);//frpSid
	
	    //根据步骤id获取当前步骤的有条件的人员ids
	    
	    TeeFlowProcess flowProcess = (TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class, prcsId);
	    TeeFlowRunPrcs flowRunPrcs = (TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class, frpSid);
	    
	    Map data = fixedFlowDataLoader.getFreeFlowFilterSelectPersonInfo(flowProcess, flowRunPrcs, loginPerson);
	    
	    String userFilter=TeeStringUtil.getString(data.get("filterPersonIds"));

	    List<TeeUserGroupModel> userGroupModelList = new ArrayList<TeeUserGroupModel>();
    	List<TeeUserGroup> userGroup = simpleDaoSupport.executeQuery("  from TeeUserGroup  g where exists(select 1 from g.userList user where ("+TeeDbUtility.IN("user.uuid",userFilter)+") ) ", null);
    	for(int i = 0;i<userGroup.size();i++){
    		TeeUserGroupModel model = new TeeUserGroupModel();
    		TeeUserGroup group = userGroup.get(i);
    		//model = userGroupModelList.get(i);
    		BeanUtils.copyProperties(group, model);
    		model.setUuid(group.getSid()+"");
    		userGroupModelList.add(model);
    	}

    	json.setRtData(userGroupModelList);
    	json.setRtState(true);
		json.setRtMsg("查询成功！");
		return json;
	}
   

    
}
