package com.tianee.oa.subsys.project.service;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.service.TeeFileNetdiskService;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.project.bean.TeeProject;
import com.tianee.oa.subsys.project.bean.TeeProjectCustomField;
import com.tianee.oa.subsys.project.bean.TeeProjectFile;
import com.tianee.oa.subsys.project.bean.TeeProjectMember;
import com.tianee.oa.subsys.project.bean.TeeProjectType;
import com.tianee.oa.subsys.project.bean.TeeTask;
import com.tianee.oa.subsys.project.bean.TeeTaskProjectTypeFile;
import com.tianee.oa.subsys.project.model.TeeProjectFileModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeProjectService extends TeeBaseService{

	
	@Autowired
	private TeeFileNetdiskService  fileNetDiskService;
	
	@Autowired
	private TeeProjectApprovalRuleService projectApprovalRuleService ;
	
	@Autowired
	private TeeProjectFileService projectFileService;
	
	@Autowired
	private TeeSmsManager smsManager;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	@Autowired
	private TeePersonDao personDao;
	/**
	 * 新建或者编辑项目
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    TeeJson json=new TeeJson();
	    String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
	    int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
	    
	    String projectNum=TeeStringUtil.getString(request.getParameter("projectNum"));
    	String projectName=TeeStringUtil.getString(request.getParameter("projectName"));
    	int projectTypeId=TeeStringUtil.getInteger(request.getParameter("projectTypeId"), 0);
    	//项目批复时间
    	String projectPFTime=TeeStringUtil.getString(request.getParameter("projectPFTime"));
    	//项目申报文件
    	String attachSids=TeeStringUtil.getString(request.getParameter("attachSids"));
        //经信委批复金额
    	String jxwPFMoney=TeeStringUtil.getString(request.getParameter("jxwPFMoney"));
    	//经信委批复函
    	String jxwPFH=TeeStringUtil.getString(request.getParameter("jxwPFH"));
    	//财政局批复金额
    	String czjPFMoney=TeeStringUtil.getString(request.getParameter("czjPFMoney"));
    	//财政局批复负责人
    	String czjPFPerson=TeeStringUtil.getString(request.getParameter("czjPFPerson"));
    	//财政局批复附件
    	String czjAttachSids=TeeStringUtil.getString(request.getParameter("czjAttachSids"));
    	//财政局批复内容
    	String czjPFContent=TeeStringUtil.getString(request.getParameter("czjPFContent"));

    	String startTime=TeeStringUtil.getString(request.getParameter("startTime"));
    	String endTime=TeeStringUtil.getString(request.getParameter("endTime"));
    	Date start=null;
		Date end=null;
		Date pfTime=null;
    	try {
			 start=sdf.parse(startTime);
			 end=sdf.parse(endTime);
			 if(!"".equals(projectPFTime)){
				 pfTime=sdf.parse(projectPFTime);
			 }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String province=TeeStringUtil.getString(request.getParameter("province"));
    	String city=TeeStringUtil.getString(request.getParameter("city"));
    	String district=TeeStringUtil.getString(request.getParameter("district"));
    	String addressDesc=TeeStringUtil.getString(request.getParameter("addressDesc"));
    	String coordinate=TeeStringUtil.getString(request.getParameter("coordinate"));
    	String projectLevel=TeeStringUtil.getString(request.getParameter("projectLevel"));
    	String diskIds=TeeStringUtil.getString(request.getParameter("diskIds"));
    	double projectBudget=TeeStringUtil.getDouble(request.getParameter("projectBudget"), 0);
    	int managerId=TeeStringUtil.getInteger(request.getParameter("managerId"), 0);
    	int approverId=TeeStringUtil.getInteger(request.getParameter("approverId"), 0);
    	String projectContent=TeeStringUtil.getString(request.getParameter("projectContent"));
    		
    	//项目成员
    	String projectMemberIds=TeeStringUtil.getString(request.getParameter("projectMemberIds"));
    	//共享人员
    	String projectShareIds=TeeStringUtil.getString(request.getParameter("projectShareIds"));
    	//项目观察者
    	String projectViewIds=TeeStringUtil.getString(request.getParameter("projectViewIds"));
    	Map requestData = TeeServletUtility.getParamMap(request);
    	Map<String,String> customMap=new HashMap<String, String>();//存放自定义字段
        for(Object obj:requestData.keySet()){
        	String str=(String)obj;
        	if(str.startsWith("FIELD_")){
        		customMap.put(str, (String) requestData.get(str));
        	}
        }
        
        int isPhone =TeeStringUtil.getInteger(request.getParameter("isPhone"),0);//判断是不是手机进行项目变更
        
        
    	if(uuid!=null&&!("").equals(uuid)&&!("null").equals(uuid)){//编辑
    		
    		//先根据uuid获取项目对象
    		TeeProject p=(TeeProject) simpleDaoSupport.get(TeeProject.class,uuid);
    		
    		//删除中间表中之前的数据
    		simpleDaoSupport.executeNativeUpdate(" delete from project_member where project_id=? ", new Object[]{uuid});
    		simpleDaoSupport.executeNativeUpdate(" delete from project_share where project_id=? ", new Object[]{uuid});
    		simpleDaoSupport.executeNativeUpdate(" delete from project_view where project_id=? ", new Object[]{uuid});
//    		String sql2="delete from FILE_NETDISK where FILE_FULL_PATH like '/"+p.getProjectFileNetdiskIds()+"/%/'";
//    		simpleDaoSupport.executeNativeUpdate(sql2, null);
//    		simpleDaoSupport.executeNativeUpdate(" delete from task_projectype_file where PROJECT_ID='"+p.getUuid()+"'",null);
 	    	//往中间表中插入数据
 	    	if(projectShareIds!=null&&!("").equals(projectShareIds)){
 	    		String[] shareIdArray=projectShareIds.split(",");
 	    		for (String str : shareIdArray) {
 					int shareId=TeeStringUtil.getInteger(str, 0);
 					simpleDaoSupport.executeNativeUpdate(" insert into project_share(sid,project_id,share_id) values (project_share_seq.nextval,?,?) ", new Object[]{uuid,shareId});
 	    		}
 	    	}
 	    	if(projectMemberIds!=null&&!("").equals(projectMemberIds)){
 	    		String[] memberIdArray=projectMemberIds.split(",");
 	    		for (String str : memberIdArray) {
 					int memberId=TeeStringUtil.getInteger(str, 0);
 					simpleDaoSupport.executeNativeUpdate(" insert into project_member(sid,project_id,member_id) values (project_member_seq.nextval,?,?) ", new Object[]{uuid,memberId});
 				}
 	    	}
 	    	if(projectViewIds!=null&&!("").equals(projectViewIds)){
 	    		String[] viewIdArray=projectViewIds.split(",");
 	    		for (String str : viewIdArray) {
 					int viewId=TeeStringUtil.getInteger(str, 0);
 					simpleDaoSupport.executeNativeUpdate(" insert into project_view(sid,project_id,view_id) values (project_view_seq.nextval,?,?) ", new Object[]{uuid,viewId});
 				}
 	    	}
// 	    	//项目类型实体类
// 	    	TeeProjectType ptype = (TeeProjectType)simpleDaoSupport.get(TeeProjectType.class,projectTypeId);
//	    	int dIds = TeeStringUtil.getInteger(diskIds, 0);
//	    	//创建文件夹
//	    	Integer addFileNetDisk = addFileNetDisk(dIds,projectTypeId,ptype.getTypeName(),loginUser,p.getUuid());
 	    	//向项目表中插入数据
 	    	String sql=" update project set project_num=?,project_type_id=?,project_name=?,begin_time=?,end_time=?,province=?,city=?,district=?,address_desc=?,address_coordinate=?,"
 	    			+ "project_level=?,project_file_netdisk_ids=?,project_budget=?,project_manager_id=?,project_approver_id=?,project_content=?,status=?,project_creater_id=?,file_id=?,"
 	    			+ "PROJECT_PF_TIME=?,ATTACH_SIDS=?,JXW_PF_MONEY=?,JXW_PF_H=?,CZJ_PF_MONEY=?,CZJ_PF_PERSON=?,CZJ_ATTACH_SIDS=?,CZJ_PF_CONTENT=?";
 	    	
 	    	List<Object> param=new ArrayList<Object>(); 	
 	    	param.add(projectNum);
 	    	param.add(projectTypeId);
 	    	param.add(projectName);
 	    	param.add(start);
 	    	param.add(end);
 	    	param.add(province);
 	    	param.add(city);
 	    	param.add(district);
 	    	param.add(addressDesc);
 	    	param.add(coordinate);
 	    	param.add(projectLevel);
 	    	if(isPhone==1){//手机端
 	    		param.add(p.getProjectFileNetdiskIds());
 	    	}else{//电脑端
 	    		param.add(diskIds);
 	    	}
 	    	//param.add(diskIds);
 	    	param.add(projectBudget);
 	    	param.add(managerId);
 	    	param.add(approverId);
 	    	param.add(projectContent);
 	    	
 	    	if(status==1){//保存
 	    		param.add(1);
 	    	}else if(status==2){//提交
 	    		if(approverId==0){//免审批
 	    			param.add(3);
 	    		}else{
 	    			param.add(2);
 	    		}
 	    	}
 	    	
 	    	
 	    	param.add(loginUser.getUuid());
 	    	param.add(0);
 	    	
 	    	//批复时间
 	    	param.add(pfTime);
 	    	//项目申报文件
 	    	param.add(attachSids);
 	        //经信委批复金额
 	    	param.add(jxwPFMoney);
 	    	//经信委批复函
 	    	param.add(jxwPFH);
 	    	//财政局批复金额
 	    	param.add(czjPFMoney);
 	    	//财政局批复负责人
 	    	param.add(czjPFPerson);
 	    	//财政局批复附件
 	    	param.add(czjAttachSids);
 	    	//财政局批复内容
 	    	param.add(czjPFContent);
 	    	
 	    	for(String name:customMap.keySet()){
 	    		sql+=","+name+"=?";
 	    		param.add(customMap.get(name));
 	    	}
 	    	sql+="  where uuid=?"; 
 	    	param.add(uuid);
 	    	simpleDaoSupport.executeNativeUpdate(sql, param.toArray());
 	    	if(attachSids!=null && !"".equals(attachSids)){
 	    		simpleDaoSupport.deleteOrUpdateByQuery("update TeeAttachment set model=?,modelId=? where sid in ("+attachSids+")", new Object[]{TeeAttachmentModelKeys.projectFile,uuid});
 	    	}
 	    	if(czjAttachSids!=null && !"".equals(czjAttachSids)){
 	    		simpleDaoSupport.deleteOrUpdateByQuery("update TeeAttachment set model=?,modelId=? where sid in ("+czjAttachSids+")", new Object[]{TeeAttachmentModelKeys.projectPfFile,uuid});
 	    	}
 	    	json.setRtMsg("编辑成功！");
	    }else{//新增
	    	
	        String uid=UUID.randomUUID().toString().replace("-", "");//生成主键
	    	//往中间表中插入数据
	    	if(projectShareIds!=null&&!("").equals(projectShareIds)){
	    		String[] shareIdArray=projectShareIds.split(",");
	    		for (String str : shareIdArray) {
					int shareId=TeeStringUtil.getInteger(str, 0);
					simpleDaoSupport.executeNativeUpdate(" insert into project_share(sid,project_id,share_id) values (project_share_seq.nextval,?,?) ", new Object[]{uid,shareId});
	    		}
	    	}
	    	if(projectMemberIds!=null&&!("").equals(projectMemberIds)){
	    		String[] memberIdArray=projectMemberIds.split(",");
	    		for (String str : memberIdArray) {
					int memberId=TeeStringUtil.getInteger(str, 0);
					simpleDaoSupport.executeNativeUpdate(" insert into project_member(sid,project_id,member_id) values (project_member_seq.nextval,?,?) ", new Object[]{uid,memberId});
				}
	    	}
	    	if(projectViewIds!=null&&!("").equals(projectViewIds)){
	    		String[] viewIdArray=projectViewIds.split(",");
	    		for (String str : viewIdArray) {
					int viewId=TeeStringUtil.getInteger(str, 0);
					simpleDaoSupport.executeNativeUpdate(" insert into project_view(sid,project_id,view_id) values (project_view_seq.nextval,?,?) ", new Object[]{uid,viewId});
				}
	    	}
	    	
	    	//newFileFolderById  fileNetDiskService 文件夹的id//根目录   文件id projectTypeId
	    	//项目类型实体类
//	    	TeeProjectType ptype = (TeeProjectType)simpleDaoSupport.get(TeeProjectType.class,projectTypeId);
//	    	int dIds = TeeStringUtil.getInteger(diskIds, 0);
//	    	//创建文件夹
//	    	Integer addFileNetDisk = addFileNetDisk(dIds,projectTypeId,ptype.getTypeName(),loginUser,uid);
	    	//向项目表中插入数据
	    	String names=" insert into project(uuid,project_num,project_type_id,project_name,begin_time,end_time,province,city,district,address_desc,address_coordinate,"
	    			+ "project_level,project_file_netdisk_ids,project_budget,project_manager_id,project_approver_id,project_content,status,project_creater_id,create_time,progress,file_id,"
	    			+ "PROJECT_PF_TIME,ATTACH_SIDS,JXW_PF_MONEY,JXW_PF_H,CZJ_PF_MONEY,CZJ_PF_PERSON,CZJ_ATTACH_SIDS,CZJ_PF_CONTENT";
	    	
	    	List<Object> param=new ArrayList<Object>();
	    	String value=") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
	    	param.add(uid);
	    	param.add(projectNum);
	    	param.add(projectTypeId);
	    	param.add(projectName);
	    	param.add(start);
	    	param.add(end);
	    	param.add(province);
	    	param.add(city);
	    	param.add(district);
	    	param.add(addressDesc);
	    	param.add(coordinate);
	    	param.add(projectLevel);
	    	param.add(diskIds);
	    	param.add(projectBudget);
	    	param.add(managerId);
	    	param.add(approverId);
	    	param.add(projectContent);
	    	if(status==1){//保存
	    		param.add(1);
	    	}else if(status==2){//提交
	    		if(approverId==0){//免审批
	    			param.add(3);
	    		}else{
	    			param.add(2);
	    		}
	    	}
	    	
	    	
	    	param.add(loginUser.getUuid());
	    	param.add(new Date());
	    	param.add(0);
	    	param.add(0);
	    	
	    	//批复时间
 	    	param.add(pfTime);
 	    	//项目申报文件
 	    	param.add(attachSids);
 	        //经信委批复金额
 	    	param.add(jxwPFMoney);
 	    	//经信委批复函
 	    	param.add(jxwPFH);
 	    	//财政局批复金额
 	    	param.add(czjPFMoney);
 	    	//财政局批复负责人
 	    	param.add(czjPFPerson);
 	    	//财政局批复附件
 	    	param.add(czjAttachSids);
 	    	//财政局批复内容
 	    	param.add(czjPFContent);
	    	
	    	for(String name:customMap.keySet()){
	    		names+=","+name;
	    		value+=",?";
	    		param.add(customMap.get(name));
	    	}
	    	String sql=names+value+")";
	    	
	    	simpleDaoSupport.executeNativeUpdate(sql, param.toArray());
	    	json.setRtMsg("添加成功！");
	    	
	    	if(attachSids!=null && !"".equals(attachSids)){
 	    		simpleDaoSupport.deleteOrUpdateByQuery("update TeeAttachment set model=?,modelId=? where sid in ("+attachSids+")", new Object[]{TeeAttachmentModelKeys.projectFile,uid});
 	    	}
 	    	if(czjAttachSids!=null && !"".equals(czjAttachSids)){
 	    		simpleDaoSupport.deleteOrUpdateByQuery("update TeeAttachment set model=?,modelId=? where sid in ("+czjAttachSids+")", new Object[]{TeeAttachmentModelKeys.projectPfFile,uid});
 	    	}
	    	
	    	
	    	//发送消息
	    	if(status==2&&approverId!=0){//提交  并且  不是免审批操作
	    		Map requestData1 = new HashMap();
		    	requestData1.put("content", "您有一个项目需要审批，项目名称："+projectName+"，请及时办理");
		    	requestData1.put("userListIds", approverId);
		    	requestData1.put("moduleNo", "060");
		    	requestData1.put("remindUrl","/system/subsys/project/projectApprove/index.jsp");
		    	smsManager.sendSms(requestData1, loginUser);
	    	}
	    	
	    }
	    json.setRtState(true);
		return json;
	}

	//diskIds:根目录的id  projectTypeId:二级目录      folderName:二级目录的名称
	//创建文件夹
	public Integer addFileNetDisk(int diskIds,int projectTypeId,String folderName,TeePerson person,String projectId){
		findFileNetDisk(projectTypeId,diskIds,person,projectId);
		return 0;
	}
	//递归，
	public void findFileNetDisk(int projectTypeId,int fId,TeePerson person,String projectId){
		List<TeeProjectType> find = simpleDaoSupport.find("from TeeProjectType where parentId=? order by orderNum asc", new Object[]{projectTypeId});
		if(find!=null && find.size()>0){
			for(TeeProjectType p:find){
				Serializable fileId = fileNetDiskService.newFileFolderById2(fId, p.getTypeName(), person);
				int integer = TeeStringUtil.getInteger(TeeStringUtil.getString(fileId), 0);
				TeeTaskProjectTypeFile f=new TeeTaskProjectTypeFile();
				f.setFileId(integer);
				f.setProjectId(projectId);
				f.setProjectTypeId(p.getSid());
				simpleDaoSupport.save(f);
				findFileNetDisk(p.getSid(),integer,person,projectId);
			}
		}
	}
	
	/**
	 * 判断项目编号是否已经存在
	 * @param request
	 * @return
	 */
	public TeeJson isExistPNum(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//项目主键
		String uuid=TeeStringUtil.getString(request.getParameter("uuid"),"");
		
	    //项目编号
		String projectNum=TeeStringUtil.getString(request.getParameter("projectNum"));
		List list=null;
		if(uuid!=null&&!("").equals(uuid)){//编辑
			 list=simpleDaoSupport.executeNativeQuery("select * from project where project_num=? and uuid!=?", new Object[]{projectNum,uuid}, 0, Integer.MAX_VALUE);
		}else{//新增
			 list=simpleDaoSupport.executeNativeQuery("select * from project where project_num=? ", new Object[]{projectNum}, 0, Integer.MAX_VALUE);
		}
		
		if(list!=null&&list.size()>0){//存在
			json.setRtData(1);	
		}else{//不存在
			json.setRtData(0);
		}
		json.setRtState(true);
		return json;
	}


	/**
	 *  根据项目状态  获取  立项中  审批中  挂起中 我创建的项目列表
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getProjectListByStatus(HttpServletRequest request,TeeDataGridModel dm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
		
		List list=new ArrayList();
		list.add(loginUser.getUuid());
		list.add(status);
		long total=simpleDaoSupport.countSQLByList(" select count(1) from project where project_creater_id=? and status=? ", list);
		json.setTotal(total);
		List<Map> resultList=simpleDaoSupport.executeNativeQuery(" select * from project where project_creater_id=? and status=? ", new Object[]{loginUser.getUuid(),status}, (dm.getPage() - 1) * dm.getRows(),  dm.getRows());
		List<Map> rows=new ArrayList<Map>();
		Map map=null;
		if(resultList!=null){
			TeePerson manager=null;
			for (Map m: resultList) {
				map=new HashMap();
				String uuid=TeeStringUtil.getString(m.get("UUID"));
				map.put("uuid", uuid);
				map.put("projectName", m.get("PROJECT_NAME"));
				map.put("projectNum", m.get("PROJECT_NUM"));
				map.put("endTime",sdf.format(m.get("END_TIME")));
				map.put("beginTime",sdf.format(m.get("BEGIN_TIME")));
				
				int managerId=(Integer) m.get("PROJECT_MANAGER_ID");
				manager=(TeePerson) simpleDaoSupport.get(TeePerson.class,managerId);
				map.put("managerName", manager.getUserName());
				
				//获取项目成员
				List<Map> pMemberList=simpleDaoSupport.executeNativeQuery("select * from project_member where project_id=? ", new Object[]{uuid}, 0, Integer.MAX_VALUE);
				String projectMemberNames="";
				
				for (Map m1 : pMemberList) {
					TeePerson p=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger(m1.get("member_id"), 0));
					projectMemberNames+=p.getUserName()+",";
				}
				if(projectMemberNames.endsWith(",")){
					projectMemberNames=projectMemberNames.substring(0,projectMemberNames.length()-1);
				}
				map.put("projectMemberNames", projectMemberNames);
				map.put("progress", m.get("PROGRESS"));
//				//批复时间
//	 	    	map.put("projectPFTime", sdf.format(m.get("PROJECT_PF_TIME")));
//	 	    	//项目申报文件
//	 	    	map.put("attachSids", m.get("ATTACH_SIDS"));
//	 	    	map.put("attachSidsName", getAttachMentNames(TeeStringUtil.getString(m.get("ATTACH_SIDS"))));
//	 	        //经信委批复金额
//	 	    	map.put("jxwPFMoney", m.get("JXW_PF_MONEY"));
//	 	    	//经信委批复函
//	 	    	map.put("jxwPFH", m.get("JXW_PF_H"));
//	 	    	//财政局批复金额
//	 	    	map.put("czjPFMoney", m.get("CZJ_PF_MONEY"));
//	 	    	//财政局批复负责人
//	 	    	map.put("czjPFPerson", m.get("CZJ_PF_PERSON"));
//	 	    	TeePerson person=(TeePerson)simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger(m.get("CZJ_PF_PERSON"), 0));
//	 	    	if(person!=null){
//	 	    		map.put("czjPFPersonName", person.getUserName());
//	 	    	}
//	 	    	//财政局批复附件
//	 	    	map.put("czjAttachSids", m.get("CZJ_ATTACH_SIDS"));
//	 	    	map.put("czjAttachSidsName", getAttachMentNames(TeeStringUtil.getString(m.get("CZJ_ATTACH_SIDS"))));
//	 	    	//财政局批复内容
//	 	    	map.put("czjPFContent", m.get("CZJ_PF_CONTENT"));
				rows.add(map);
			}
		}
		
		
		json.setRows(rows);
		return json;
	}
	public List<Map<String,Object>> getAttachMentNames2(String model,String modelId){
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
			List<TeeAttachment> find = simpleDaoSupport.find("from TeeAttachment where model=? and modelId=?", new Object[]{model,modelId});
			if(find!=null && find.size()>0){
				for(TeeAttachment att:find){
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("sid", att.getSid());
					map.put("fileName", att.getFileName());
					map.put("attachmentName", att.getAttachmentName());
					map.put("attachmentPath", att.getAttachmentPath());
					map.put("ext", att.getExt());
					map.put("priv", "3");
					listMap.add(map);
				}
			}
		return listMap;
	}

	public List<Map<String,Object>> getAttachMentNames(String atachIds){
		List<TeeAttachment> attachmentsByIds = attachmentDao.getAttachmentsByIds(atachIds);
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		if(atachIds!=null && !"".equals(atachIds)){
			List<TeeAttachment> find = simpleDaoSupport.find("from TeeAttachment where sid in ("+atachIds+")", null);
			if(find!=null && find.size()>0){
				for(TeeAttachment att:find){
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("sid", att.getSid());
					map.put("fileName", att.getFileName());
					map.put("attachmentName", att.getAttachmentName());
					map.put("attachmentPath", att.getAttachmentPath());
					map.put("ext", att.getExt());
					map.put("priv", "2");
					listMap.add(map);
				}
			}
		}
		return listMap;
	}


	/**
	 * 改变项目的状态
	 * @param request
	 * @return
	 */
	public TeeJson changeStatus(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
		simpleDaoSupport.executeNativeUpdate(" update project set status=? where uuid=? ", new Object[]{status,uuid});
		json.setRtState(true);
		json.setRtMsg("操作成功");
		
		return json;
	}



	/**
	 * 根据项目主键 删除项目
	 * @param request
	 * @return
	 */
	public TeeJson delByUUid(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
		if(!("").equals(uuid)){
			//根据项目主键   获取项目的一些基本信息
			//获取项目的基本信息
			Map map=simpleDaoSupport.executeNativeUnique(" select * from project where uuid=? ", new Object[]{uuid});
			String projectName=TeeStringUtil.getString(map.get("PROJECT_NAME"));
			int managerId=TeeStringUtil.getInteger(map.get("PROJECT_MANAGER_ID"), 0);
			int createrId=TeeStringUtil.getInteger(map.get("PROJECT_CREATER_ID"), 0);
			int status=TeeStringUtil.getInteger(map.get("STATUS"), 0);
			
			
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
			
			
			
			//删除三个中间表中的数据
			simpleDaoSupport.executeNativeUpdate(" delete from project_member where project_id=?", new Object[]{uuid});
			simpleDaoSupport.executeNativeUpdate(" delete from project_share where project_id=?", new Object[]{uuid});
			simpleDaoSupport.executeNativeUpdate(" delete from project_view where project_id=?", new Object[]{uuid});
			//删除项目交流表中的数据
			simpleDaoSupport.executeNativeUpdate(" delete from project_communication where project_id=? ", new Object[]{uuid});
			//删除项目抄送表中的数据
			simpleDaoSupport.executeNativeUpdate(" delete from project_copy where project_id=? ", new Object[]{uuid});
			//删除任务表中的数据
			simpleDaoSupport.executeNativeUpdate(" delete from project_task where project_id=? ", new Object[]{uuid});
			//删除任务汇报表中的数据
			simpleDaoSupport.executeNativeUpdate(" delete from project_task_report where project_id=?", new Object[]{uuid});
            //删除项目问题表中的数据
			simpleDaoSupport.executeNativeUpdate(" delete from project_question where  project_id=? ", new Object[]{uuid});
			//删除项目流程表中的数据
			simpleDaoSupport.executeNativeUpdate(" delete from project_flow where project_id=? ", new Object[]{uuid});
			//删除项目批注表中的数据
			simpleDaoSupport.executeNativeUpdate(" delete from project_notation where project_id=? ", new Object[]{uuid});
			//删除项目文档表中的数据
			simpleDaoSupport.executeNativeUpdate(" delete from project_file where project_id=? ", new Object[]{uuid});
			//删除项目预算表中的数据
			simpleDaoSupport.executeNativeUpdate(" delete from project_cost where project_id=?", new Object[]{uuid});
			//删除项目表中的数据
			simpleDaoSupport.executeNativeUpdate(" delete from project where uuid=?", new Object[]{uuid});
		    
			
			if(status!=1){//不是立项中
				// 发送消息   创建人   负责人   观察者   成员
				Map requestData1 = new HashMap();
				requestData1.put("content", "项目“"+projectName+"”已被"+loginUser.getUserName()+"销毁。");
				requestData1.put("userListIds", userListIds);
				requestData1.put("moduleNo", "060");
				smsManager.sendSms(requestData1, loginUser);
				
			}
			
			
			json.setRtState(true);
			json.setRtMsg("删除成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("数据不存在！");
		}
		
		
		return json;
	}


    /**
     * 根据项目主键 获取详情
     * @param request
     * @return
     */
	public TeeJson getInfoByUuid(HttpServletRequest request) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeJson  json =new  TeeJson();
	    String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
	    if(!("").equals(uuid)){
	    	Map<String,Object> map=new HashMap<String,Object>();
	    	//获取项目表中的数据
	    	Map data=simpleDaoSupport.executeNativeUnique(" select * from project where uuid=? ", new Object[]{uuid});
	    	map.put("projectNum", data.get("PROJECT_NUM"));
	    	map.put("projectTypeId", data.get("PROJECT_TYPE_ID"));
	    	map.put("projectName", data.get("PROJECT_NAME"));
	    	map.put("status", data.get("STATUS"));
	    	if(data.get("PROJECT_PF_TIME")!=null){
	    		//批复时间
	    		map.put("projectPFTime", sdf.format((Date)data.get("PROJECT_PF_TIME")));
	    	}
 	    	//项目申报文件
 	    	//map.put("attachSids", data.get("ATTACH_SIDS"));
 	    	//List<Map<String,Object>> list = getAttachMentNames(TeeStringUtil.getString(data.get("ATTACH_SIDS")));
 	    	List<Map<String,Object>> list = getAttachMentNames2(TeeAttachmentModelKeys.projectFile,TeeStringUtil.getString(data.get("UUID")));
 	    	map.put("attachSidsName", list);
 	        //经信委批复金额
 	    	map.put("jxwPFMoney", data.get("JXW_PF_MONEY"));
 	    	//经信委批复函
 	    	map.put("jxwPFH", data.get("JXW_PF_H"));
 	    	//财政局批复金额
 	    	map.put("czjPFMoney", data.get("CZJ_PF_MONEY"));
 	    	//财政局批复负责人
 	    	map.put("czjPFPerson", data.get("CZJ_PF_PERSON"));
 	    	TeePerson person=(TeePerson)simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger(data.get("CZJ_PF_PERSON"), 0));
 	    	if(person!=null){
 	    		map.put("czjPFPersonName", person.getUserName());
 	    	}
 	    	//财政局批复附件
 	    	//map.put("czjAttachSids", data.get("CZJ_ATTACH_SIDS"));
 	    	
 	    	map.put("czjAttachSidsName", getAttachMentNames2(TeeAttachmentModelKeys.projectPfFile,TeeStringUtil.getString(data.get("UUID"))));
 	    	//财政局批复内容
 	    	map.put("czjPFContent", data.get("CZJ_PF_CONTENT"));
	    	
	    	try {
				map.put("startTime", sdf.format((Date)data.get("BEGIN_TIME")));
				map.put("endTime", sdf.format((Date)data.get("END_TIME")));
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	map.put("province", data.get("PROVINCE"));
	    	map.put("city", data.get("CITY"));
	    	map.put("district", data.get("DISTRICT"));
	    	map.put("addressDesc", data.get("ADDRESS_DESC"));
	    	map.put("coordinate", data.get("ADDRESS_COORDINATE"));
	    	map.put("projectLevel", data.get("PROJECT_LEVEL"));
	    	map.put("diskIds", data.get("PROJECT_FILE_NETDISK_IDS"));
	    	map.put("progress", data.get("PROGRESS"));
	    	
	    	if(!("").equals(TeeStringUtil.getString(data.get("PROJECT_FILE_NETDISK_IDS")))){
	    		//根据文件夹id字符串  获取文件夹名称
		    	String[] diskIdArray=TeeStringUtil.getString(data.get("PROJECT_FILE_NETDISK_IDS")).split(",");
		    	String diskNames="";
		    	TeeFileNetdisk  disk=null;
		    	for (String str : diskIdArray) {
					int sid=TeeStringUtil.getInteger(str, 0);
					disk=(TeeFileNetdisk) simpleDaoSupport.get(TeeFileNetdisk.class,sid);
					if(disk!=null){
						diskNames+=disk.getFileName()+",";
					}
				}
		    	if(diskNames.endsWith(",")){
		    		diskNames=diskNames.substring(0,diskNames.length()-1);
		    	}
		    	map.put("diskNames", diskNames);
	    	}
	    	map.put("projectBudget", data.get("PROJECT_BUDGET"));
	    	map.put("managerId", data.get("PROJECT_MANAGER_ID"));
	        //项目负责人
	    	if(TeeStringUtil.getInteger( data.get("PROJECT_MANAGER_ID"), 0)!=0){
	    		TeePerson  manager=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger( data.get("PROJECT_MANAGER_ID"), 0));
	    	    map.put("managerName", manager.getUserName());
	    	}
	    	//项目审批人
	    	map.put("approverId", data.get("PROJECT_APPROVER_ID"));
	    	if(TeeStringUtil.getInteger( data.get("PROJECT_APPROVER_ID"), 0)!=0){
	    		TeePerson  approver=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger( data.get("PROJECT_APPROVER_ID"), 0));
	    	    map.put("approverName", approver.getUserName());
	    	}
	    	map.put("projectContent", data.get("PROJECT_CONTENT"));
	    	//获取项目成员
	    	String projectMemberIds="";
	    	String projectMemberNames="";
	    	List<Map> memberList=simpleDaoSupport.executeNativeQuery(" select * from project_member where project_id=?", new Object[]{uuid}, 0, Integer.MAX_VALUE);
	    	TeePerson member=null;
	    	for (Map m : memberList){
	    		projectMemberIds+=TeeStringUtil.getInteger(m.get("member_id"), 0)+",";
	    		member=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger(m.get("member_id"), 0));
	    		if(member!=null){
	    			projectMemberNames+=member.getUserName()+",";
	    		}
			}
	    	if(projectMemberIds.endsWith(",")){
	    		projectMemberIds=projectMemberIds.substring(0,projectMemberIds.length()-1);
	    	}
	    	if(projectMemberNames.endsWith(",")){
	    		projectMemberNames=projectMemberNames.substring(0,projectMemberNames.length()-1);
	    	}
	    	map.put("projectMemberIds", projectMemberIds);
	    	map.put("projectMemberNames", projectMemberNames);
	    	//获取共享人员
	    	String projectShareIds="";
	    	String projectShareNames="";
	    	List<Map> shareList=simpleDaoSupport.executeNativeQuery(" select * from project_share where project_id=?", new Object[]{uuid}, 0, Integer.MAX_VALUE);
	    	TeePerson share=null;
	    	for (Map m : shareList){
	    		projectShareIds+=TeeStringUtil.getInteger(m.get("share_id"), 0)+",";
	    		share=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger(m.get("share_id"), 0));
	    		if(share!=null){
	    			projectShareNames+=share.getUserName()+",";
	    		}
			}
	    	if(projectShareIds.endsWith(",")){
	    		projectShareIds=projectShareIds.substring(0,projectShareIds.length()-1);
	    	}
	    	if(projectShareNames.endsWith(",")){
	    		projectShareNames=projectShareNames.substring(0,projectShareNames.length()-1);
	    	}
	    	map.put("projectShareIds", projectShareIds);
	    	map.put("projectShareNames", projectShareNames);
	    	
	    	//获取观察者
	    	String projectViewIds="";
	    	String projectViewNames="";
	    	List<Map> viewList=simpleDaoSupport.executeNativeQuery(" select * from project_view where project_id=?", new Object[]{uuid}, 0, Integer.MAX_VALUE);
	    	TeePerson view=null;
	    	for (Map m : viewList){
	    		projectViewIds+=TeeStringUtil.getInteger(m.get("view_id"), 0)+",";
	    		view=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger(m.get("view_id"), 0));
	    		if(view!=null){
	    			projectViewNames+=view.getUserName()+",";
	    		}
			}
	    	if(projectViewIds.endsWith(",")){
	    		projectViewIds=projectViewIds.substring(0,projectViewIds.length()-1);
	    	}
	    	if(projectViewNames.endsWith(",")){
	    		projectViewNames=projectViewNames.substring(0,projectViewNames.length()-1);
	    	}
	    	map.put("projectViewIds", projectViewIds);
	    	map.put("projectViewNames", projectViewNames);
	    	
	    	//获取自定义字段的值
	    	for(Object obj:data.keySet()){
	    		String name=(String)obj;
	    		if(name.startsWith("FIELD_")){
	    			map.put(name,data.get(name));
	    		}
	    	}
	    	json.setRtState(true);
	    	json.setRtData(map);
	    	json.setRtMsg("数据获取成功！");
	    }else{
	    	json.setRtState(false);
	    	json.setRtMsg("数据获取失败！");
	    }
	    
		return json;
	}



	/**
	 * 提交项目
	 * @param request
	 * @return
	 */
	public TeeJson submitProject(HttpServletRequest request) {
		//获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
		
		//获取项目信息
		Map map=simpleDaoSupport.executeNativeUnique(" select * from project where uuid=? ", new Object[]{uuid});
		String projectName=TeeStringUtil.getString(map.get("PROJECT_NAME"));
		int approverId=TeeStringUtil.getInteger(map.get("PROJECT_APPROVER_ID"), 0);
		
		//判断当前登陆的 用户是否是免审批人员 
		TeeJson j=projectApprovalRuleService.isNoApprove(request);
		int isNoApprove=(Integer)j.getRtData();
		if(isNoApprove==1){//免审批
			simpleDaoSupport.executeNativeUpdate(" update project set status=? where uuid=? ", new Object[]{3,uuid});
		}else{
			simpleDaoSupport.executeNativeUpdate(" update project set status=? where uuid=? ", new Object[]{status,uuid});
		    
			if(approverId!=0){//审批人员存在
				// 发送消息
				Map requestData1 = new HashMap();
				requestData1.put("content", "您有一个项目需要审批，项目名称："+projectName+"，请及时办理");
				requestData1.put("userListIds", approverId);
				requestData1.put("moduleNo", "060");
				requestData1.put("remindUrl","/system/subsys/project/projectApprove/index.jsp");
				smsManager.sendSms(requestData1, loginUser);
			}
		}

		json.setRtState(true);
		json.setRtMsg("操作成功");
		
		return json;
	}


	
	
    /**
     * 获取办理中  、已办结  的 与我相关的项目列表
     * @param request
     * @param dm
     * @return
     */
	public TeeEasyuiDataGridJson getMyProjectListByStatus(
			HttpServletRequest request, TeeDataGridModel dm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
		
		List list=new ArrayList();
		list.add(loginUser.getUuid());
		list.add(loginUser.getUuid());
		list.add(loginUser.getUuid());
		list.add(loginUser.getUuid());
		list.add(status);
		long total=simpleDaoSupport.countSQLByList(" select count(*) from project p where  ( (p.project_creater_id=?) or (p.project_manager_id= ?) or (exists (select * from project_member pm where pm.project_id=p.uuid and pm.member_id=? )) or (exists (select * from project_view pv where pv.project_id=p.uuid and pv.view_id=? ))) and p.status=? ", list);
		json.setTotal(total);
		List<Map> resultList=simpleDaoSupport.executeNativeQuery(" select * from project p  where  ( (p.project_creater_id=?) or (p.project_manager_id= ?) or (exists (select * from project_member pm where pm.project_id=p.uuid and pm.member_id=? )) or (exists (select * from project_view pv where pv.project_id=p.uuid and pv.view_id=? ))) and p.status=?  ", new Object[]{loginUser.getUuid(),loginUser.getUuid(),loginUser.getUuid(),loginUser.getUuid(),status}, (dm.getPage() - 1) * dm.getRows(),  dm.getRows());
		List<Map> rows=new ArrayList<Map>();
		Map map=null;
		if(resultList!=null){
			TeePerson manager=null;
			for (Map m: resultList) {
				map=new HashMap();
				String uuid=TeeStringUtil.getString(m.get("UUID"));
				map.put("uuid", uuid);
				map.put("projectName", m.get("PROJECT_NAME"));
				map.put("projectNum", m.get("PROJECT_NUM"));
				map.put("endTime",sdf.format(m.get("END_TIME")));
				map.put("beginTime",sdf.format(m.get("BEGIN_TIME")));
				
				int managerId=(Integer) m.get("PROJECT_MANAGER_ID");
				manager=(TeePerson) simpleDaoSupport.get(TeePerson.class,managerId);
				map.put("managerName", manager.getUserName());
				
				//获取项目成员
				List<Map> pMemberList=simpleDaoSupport.executeNativeQuery("select * from project_member where project_id=? ", new Object[]{uuid}, 0, Integer.MAX_VALUE);
				String projectMemberNames="";
				
				for (Map m1 : pMemberList) {
					TeePerson p=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger(m1.get("member_id"), 0));
					projectMemberNames+=p.getUserName()+",";
				}
				if(projectMemberNames.endsWith(",")){
					projectMemberNames=projectMemberNames.substring(0,projectMemberNames.length()-1);
				}
				map.put("projectMemberNames", projectMemberNames);
				map.put("progress", m.get("PROGRESS"));
				
				//判断该项目  当前登录人是不是负责人 或者项目创建人
				List l=simpleDaoSupport.executeNativeQuery(" select * from project where uuid=? and (project_manager_id=? or project_creater_id=?)", new Object[]{uuid,loginUser.getUuid(),loginUser.getUuid()},0, Integer.MAX_VALUE);
				if(l!=null&&l.size()>0){
					map.put("isManagerOrCreater", 1);
				}else{
					map.put("isManagerOrCreater", 0);
				}
				
				
				rows.add(map);
			}
		}
		
		
		json.setRows(rows);
		return json;
	}


	
	
    /**
     * 完成项目
     * @param request
     * @return
     */
	public TeeJson finish(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
		if(!("").equals(uuid)&&uuid!=null){
			simpleDaoSupport.executeNativeUpdate(" update project set progress=?,status=? where uuid=? ", new Object[]{100,5,uuid});
		    
			
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
			// 发送消息   创建人   负责人   观察者   成员
			Map requestData1 = new HashMap();
			requestData1.put("content", "项目“"+projectName+"”已办结完毕。");
			requestData1.put("userListIds", userListIds);
			requestData1.put("moduleNo", "060");
			requestData1.put("remindUrl","/system/subsys/project/projectdetail/index.jsp?uuid="+uuid);
			smsManager.sendSms(requestData1, loginUser);
				
			json.setRtState(true);
		    json.setRtMsg("操作成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("该项目不存在");
		}
		return json;
	}



	/**
	 * 根据项目类型的主键 获取项目列表
	 * @param request
	 * @return
	 */
	public TeeJson getProjectListByTypeId(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int typeId=TeeStringUtil.getInteger(request.getParameter("sid"), 0);	
		List list=simpleDaoSupport.executeNativeQuery(" select * from project where project_type_id=?", new Object[]{typeId}, 0, Integer.MAX_VALUE);
		json.setRtData(list);
		json.setRtState(true);
		return json;
	}



	/**
	 * 项目延期
	 * @param request
	 * @return
	 */
	public TeeJson delay(HttpServletRequest request) {
		//获取当前登陆人 
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String endTime=TeeStringUtil.getString(request.getParameter("endTime"));
		String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
		try {
			simpleDaoSupport.executeNativeUpdate(" update project set end_time=? where uuid=?", new Object[]{sdf.parse(endTime),uuid});
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String projectName="";
		TeeProject project=(TeeProject) simpleDaoSupport.get(TeeProject.class,uuid);
		if(project!=null){
			projectName=project.getProjectName();
		}
		String userListIds=getCreaterAndManagerAndViewerAndMemberIds(uuid);
		// 发送消息
		Map requestData1 = new HashMap();
		requestData1.put("content", "项目“"+projectName+"”已延期到："+endTime+"，请查看详情。");
		requestData1.put("userListIds", userListIds);
		requestData1.put("moduleNo", "060");
		requestData1.put("remindUrl","/system/subsys/project/projectdetail/info.jsp?uuid="+uuid);
		smsManager.sendSms(requestData1, loginUser);
		
		
		
		
		json.setRtState(true);
		json.setRtMsg("已延期！");
		return json;
	}


    /**
     * 项目汇报
     * @param request
     * @return
     */
	public TeeJson report(HttpServletRequest request) {
		//获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
		int progress=TeeStringUtil.getInteger(request.getParameter("progress"),0);
		simpleDaoSupport.executeNativeUpdate(" update project set progress=? where uuid=?", new Object[]{progress,uuid});
		
		TeeProject project=(TeeProject) simpleDaoSupport.get(TeeProject.class,uuid);
		String projectName="";
		if(project!=null){
			projectName=project.getProjectName();	
		}
		
		String userListIds=getCreaterAndManagerAndViewerAndMemberIds(uuid);
		// 发送消息
		Map requestData1 = new HashMap();
		requestData1.put("content", loginUser.getUserName()+"在项目“"+projectName+"”中汇报了项目进度："+progress+"%。");
		requestData1.put("userListIds", userListIds);
		requestData1.put("moduleNo", "060");
		requestData1.put("remindUrl","/system/subsys/project/projectdetail/info.jsp?uuid="+uuid);
		smsManager.sendSms(requestData1, loginUser);

		
		json.setRtState(true);
		json.setRtMsg("汇报成功！");
		return json;
	}



	/**
	 * 获取已审批的项目列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getHaveApprovedProjectList(
			HttpServletRequest request, TeeDataGridModel dm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		List list=new ArrayList();
		list.add(loginUser.getUuid());
		long total=simpleDaoSupport.countSQLByList(" select count(*) from project where project_approver_id=? and status in (3,4,5,6) ", list);
		json.setTotal(total);
		List<Map> resultList=simpleDaoSupport.executeNativeQuery(" select * from project where project_approver_id=? and status in (3,4,5,6) ", new Object[]{loginUser.getUuid()}, (dm.getPage() - 1) * dm.getRows(),  dm.getRows());
		List<Map> rows=new ArrayList<Map>();
		Map map=null;
		if(resultList!=null){
			TeePerson manager=null;
			for (Map m: resultList) {
				map=new HashMap();
				String uuid=TeeStringUtil.getString(m.get("UUID"));
				map.put("uuid", uuid);
				map.put("projectName", m.get("PROJECT_NAME"));
				map.put("projectNum", m.get("PROJECT_NUM"));
				map.put("endTime",sdf.format(m.get("END_TIME")));
				map.put("beginTime",sdf.format(m.get("BEGIN_TIME")));
				
				int managerId=(Integer) m.get("PROJECT_MANAGER_ID");
				manager=(TeePerson) simpleDaoSupport.get(TeePerson.class,managerId);
				map.put("managerName", manager.getUserName());
				
				//获取项目成员
				List<Map> pMemberList=simpleDaoSupport.executeNativeQuery("select * from project_member where project_id=? ", new Object[]{uuid}, 0, Integer.MAX_VALUE);
				String projectMemberNames="";
				
				for (Map m1 : pMemberList) {
					TeePerson p=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger(m1.get("member_id"), 0));
					projectMemberNames+=p.getUserName()+",";
				}
				if(projectMemberNames.endsWith(",")){
					projectMemberNames=projectMemberNames.substring(0,projectMemberNames.length()-1);
				}
				map.put("projectMemberNames", projectMemberNames);
				map.put("progress", m.get("PROGRESS"));
				map.put("status", m.get("STATUS"));
				rows.add(map);
			}
		}
		
		
		json.setRows(rows);
		return json;
	}



	
	/**
	 * 获取未审批的项目列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getNoApprovedProjectList(
			HttpServletRequest request, TeeDataGridModel dm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		List list=new ArrayList();
		list.add(loginUser.getUuid());
		long total=simpleDaoSupport.countSQLByList(" select count(*) from project where project_approver_id=? and status=2 ", list);
		json.setTotal(total);
		List<Map> resultList=simpleDaoSupport.executeNativeQuery(" select * from project where project_approver_id=? and status=2 ", new Object[]{loginUser.getUuid()}, (dm.getPage() - 1) * dm.getRows(),  dm.getRows());
		List<Map> rows=new ArrayList<Map>();
		Map map=null;
		if(resultList!=null){
			TeePerson manager=null;
			for (Map m: resultList) {
				map=new HashMap();
				String uuid=TeeStringUtil.getString(m.get("UUID"));
				map.put("uuid", uuid);
				map.put("projectName", m.get("PROJECT_NAME"));
				map.put("projectNum", m.get("PROJECT_NUM"));
				map.put("endTime",sdf.format(m.get("END_TIME")));
				map.put("beginTime",sdf.format(m.get("BEGIN_TIME")));
				map.put("projectTypeId", m.get("PROJECT_TYPE_ID"));
				
				int managerId=(Integer) m.get("PROJECT_MANAGER_ID");
				manager=(TeePerson) simpleDaoSupport.get(TeePerson.class,managerId);
				map.put("managerName", manager.getUserName());
				
				//获取项目成员
				List<Map> pMemberList=simpleDaoSupport.executeNativeQuery("select * from project_member where project_id=? ", new Object[]{uuid}, 0, Integer.MAX_VALUE);
				String projectMemberNames="";
				
				for (Map m1 : pMemberList) {
					TeePerson p=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger(m1.get("member_id"), 0));
					projectMemberNames+=p.getUserName()+",";
				}
				if(projectMemberNames.endsWith(",")){
					projectMemberNames=projectMemberNames.substring(0,projectMemberNames.length()-1);
				}
				map.put("projectMemberNames", projectMemberNames);
				map.put("progress", m.get("PROGRESS"));
				map.put("status", m.get("STATUS"));
				rows.add(map);
			}
		}
		
		
		json.setRows(rows);
		return json;
	}



	/**
	 * 项目查询
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson query(HttpServletRequest request,
			TeeDataGridModel dm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		//获取页面上高级查询的数据
		String projectName=TeeStringUtil.getString(request.getParameter("projectName"));//项目名称
		String projectNum=TeeStringUtil.getString(request.getParameter("projectNum"));//项目编码
		int projectTypeId=TeeStringUtil.getInteger(request.getParameter("projectTypeId"), 0);
		int userId=TeeStringUtil.getInteger(request.getParameter("userId"), 0);//成员id   项目负责人和项目成员
		//起始时间
		String beginTime1=TeeStringUtil.getString(request.getParameter("beginTime1"));
		String beginTime2=TeeStringUtil.getString(request.getParameter("beginTime2"));
		//结束时间
		String endTime1=TeeStringUtil.getString(request.getParameter("endTime1"));
		String endTime2=TeeStringUtil.getString(request.getParameter("endTime2"));
		
		int status=TeeStringUtil.getInteger(request.getParameter("status"),0);
		//项目进度
		int minProgress=TeeStringUtil.getInteger(request.getParameter("minProgress"),0);
		int maxProgress=TeeStringUtil.getInteger(request.getParameter("maxProgress"), 0);
		List list=new ArrayList();
		
		String sql=" from project p where  ( (p.project_creater_id=?) or (p.project_manager_id=?) or (exists (select * from project_member pm where pm.project_id=p.uuid and pm.member_id=? )) or (exists (select * from project_view pv where pv.project_id=p.uuid and pv.view_id=? )))  ";
		list.add(loginUser.getUuid());
		list.add(loginUser.getUuid());
		list.add(loginUser.getUuid());
		list.add(loginUser.getUuid());
		if(!("").equals(projectName)){
			sql+=" and p.project_name like ? ";
			list.add("%"+projectName+"%");
		}
		if(!("").equals(projectNum)){
			sql+=" and p.project_num like ? ";
			list.add("%"+projectNum+"%");
		}
		
		if(projectTypeId!=0){
			sql+=" and p.project_type_id=? ";
			list.add(projectTypeId);		
		}
		
		if(status!=0){
			sql+=" and p.status=? ";
			list.add(status);
		}
		
		if(userId!=0){
			sql+=" and ((p.project_manager_id= ?) or (exists (select * from project_member pm where pm.project_id=p.uuid and pm.member_id=? ))) ";
			list.add(userId);
			list.add(userId);
		}
		if(!("").equals(beginTime1)){
			sql+=" and p.begin_time >= ? ";
			try {
				list.add(sdf.parseObject(beginTime1));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(!("").equals(beginTime2)){
			sql+=" and p.begin_time <= ? ";
			try {
				list.add(sdf.parseObject(beginTime2));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(!("").equals(endTime1)){
			sql+=" and p.end_time >= ? ";
			try {
				list.add(sdf.parseObject(endTime1));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(!("").equals(endTime2)){
			sql+=" and p.end_time <= ? ";
			try {
				list.add(sdf.parseObject(endTime2));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(minProgress!=0){
			sql+=" and p.progress >= ? ";
			list.add(minProgress);
		}
		
		if(maxProgress!=0){
			sql+=" and p.progress <= ? ";
			list.add(maxProgress);
		}
		
		//获取自定义   字段查询
		Map customMap=TeeServletUtility.getParamMap(request);
		for (Object obj : customMap.keySet()) {
			String str=TeeStringUtil.getString(obj);
			if(str.startsWith("FIELD_")){
				int filedId=TeeStringUtil.getInteger(str.substring(6,str.length()), 0);
				if(filedId>0){
					TeeProjectCustomField field=(TeeProjectCustomField) simpleDaoSupport.get(TeeProjectCustomField.class,filedId);
					
					if(("单行输入框").equals(field.getFieldType())||("多行输入框").equals(field.getFieldType())){
						String value=(String) customMap.get(str);
						if(!("").equals(value)){
							sql+=" and p."+str+" like ? ";
							list.add("%"+value+"%");
						}
					}else if(("下拉列表").equals(field.getFieldType())){
						String value=(String) customMap.get(str);
						if(!("").equals(value)){
							sql+=" and p."+str+" = ? ";
							list.add(value);
						}
					}	
				}	
			}
		}

		long total=simpleDaoSupport.countSQLByList(" select count(*) "+ sql, list);
		json.setTotal(total);
		List<Map> resultList=simpleDaoSupport.executeNativeQuery(" select * "+sql, list.toArray(), (dm.getPage() - 1) * dm.getRows(),  dm.getRows());
		List<Map> rows=new ArrayList<Map>();
		Map map=null;
		if(resultList!=null){
			TeePerson manager=null;
			TeePerson creater=null;
			List<TeeProjectCustomField> list1=null;
			for (Map m: resultList) {
				map=new HashMap();
				
				/*//获取自定义字段的值
				for (Object obj : m.keySet()) {
					String key=TeeStringUtil.getString(obj);
					if(key.startsWith("FIELD_")){
						map.put(key,m.get(key));
					}
				}*/
				//根据项目类型主键 获取自定义字段的值
		    	list1=simpleDaoSupport.executeQuery(" from TeeProjectCustomField where projectType.sid=? ", new Object[]{m.get("PROJECT_TYPE_ID")});
		    	if(list!=null){
		    		for (TeeProjectCustomField field : list1) {
						if(("单行输入框").equals(field.getFieldType())||("多行输入框").equals(field.getFieldType())){
							map.put("FIELD_"+field.getSid(),m.get("FIELD_"+field.getSid()));
						}else if(("下拉列表").equals(field.getFieldType())){
							String fieldCtrModel=field.getFieldCtrModel();
							Map m1=TeeJsonUtil.JsonStr2Map(fieldCtrModel);
							if(("系统编码").equals(m1.get("codeType"))){
					            String value=(String) m1.get("value");
					            String name=TeeSysCodeManager.getChildSysCodeNameCodeNo(value,(String)(m.get("FIELD_"+field.getSid())));
					            map.put("FIELD_"+field.getSid(),name);
							}else if(("自定义选项").equals(m1.get("codeType"))){
								String value1=(String)m1.get("value");
								
								JSONArray array = JSONArray.fromObject(value1);

								String[] optionNames=((String)array.get(0)).split(",");
								String[] optionValues=((String)array.get(1)).split(",");
								for (int i=0;i<optionValues.length;i++) {
									
									if((TeeStringUtil.getString((m.get("FIELD_"+field.getSid())))).equals(optionValues[i])){
										map.put("FIELD_"+field.getSid(),optionNames[i]);
										break;
									}
								}
							}
						}
					}
		    	}
				
				
				
				
				
				
				
				
				
				
				
				String uuid=TeeStringUtil.getString(m.get("UUID"));
				map.put("uuid", uuid);
				map.put("projectName", m.get("PROJECT_NAME"));
				map.put("projectNum", m.get("PROJECT_NUM"));
				map.put("endTime",sdf.format(m.get("END_TIME")));
				map.put("beginTime",sdf.format(m.get("BEGIN_TIME")));
				
				int managerId=TeeStringUtil.getInteger(m.get("PROJECT_MANAGER_ID"),0);
				manager=(TeePerson) simpleDaoSupport.get(TeePerson.class,managerId);
				map.put("managerName", manager.getUserName());
				
				int createrId=TeeStringUtil.getInteger(m.get("PROJECT_CREATER_ID"),0);
			    creater=(TeePerson) simpleDaoSupport.get(TeePerson.class,createrId);
			   if(creater!=null){
				   map.put("createrName", creater.getUserName());
			   }
				
				//获取项目成员
				List<Map> pMemberList=simpleDaoSupport.executeNativeQuery("select * from project_member where project_id=? ", new Object[]{uuid}, 0, Integer.MAX_VALUE);
				String projectMemberNames="";
				
				for (Map m1 : pMemberList) {
					TeePerson p=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger(m1.get("member_id"), 0));
					projectMemberNames+=p.getUserName()+",";
				}
				if(projectMemberNames.endsWith(",")){
					projectMemberNames=projectMemberNames.substring(0,projectMemberNames.length()-1);
				}
				map.put("projectMemberNames", projectMemberNames);
				map.put("progress", m.get("PROGRESS"));
				map.put("status", m.get("STATUS"));
				
				//判断该项目  当前登录人是不是负责人 或者项目创建人
				List l=simpleDaoSupport.executeNativeQuery(" select * from project where uuid=? and (project_manager_id=? or project_creater_id=?)", new Object[]{uuid,loginUser.getUuid(),loginUser.getUuid()},0, Integer.MAX_VALUE);
				if(l!=null&&l.size()>0){
					map.put("isManagerOrCreater", 1);
				}else{
					map.put("isManagerOrCreater", 0);
				}
				
				
				rows.add(map);
			}
		}
		
		
		json.setRows(rows);
		return json;
	}



	/**
	 * 判断当前登陆人是不是项目负责人   或者  是项目创建者
	 * @param request
	 * @return
	 */
	public TeeJson isCreaterOrManager(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取页面上传来的项目的主键
		String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
		List list=simpleDaoSupport.executeNativeQuery(" select * from project p where p.uuid=?  and ((p.project_creater_id=?) or (p.project_manager_id=?))", new Object[]{uuid,loginUser.getUuid(),loginUser.getUuid()},0,Integer.MAX_VALUE);
		if(list!=null&&list.size()>0){
			json.setRtData(1);//是项目负责人  或者  项目创建者
		}else{
			json.setRtData(0);//不是项目负责人  或者项目创建者
		}
		json.setRtState(true);	
		return json;
	}



	/**
	 * 判断当前登陆人是不是负责人   创建人  或者 成员
	 * @param request
	 * @return
	 */
	public TeeJson isCreaterOrManagerOrMember(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取项目主键
		String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
		
		List list=simpleDaoSupport.executeNativeQuery(" select * from project p where p.uuid=?  and ((p.project_creater_id=?) or (p.project_manager_id=?) or (exists (select *  from project_member pm where pm.project_id=p.uuid and pm.member_id=? ))) ", new Object[]{uuid,loginUser.getUuid(),loginUser.getUuid(),loginUser.getUuid()},0,Integer.MAX_VALUE);
		
		if(list!=null&&list.size()>0){
			json.setRtData(1);
		}else{
			json.setRtData(0);
		}
		json.setRtState(true);
		return json;
	}



	/**
	 * 根据项目主键  获取项目的基本详情
	 * @param request
	 * @return
	 */
	public TeeJson getBasicInfoBySid(HttpServletRequest request) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeJson  json =new  TeeJson();
	    String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
	    if(!("").equals(uuid)){
	    	Map map=new HashMap();
	    	//获取项目表中的数据
	    	Map data=simpleDaoSupport.executeNativeUnique(" select * from project where uuid=? ", new Object[]{uuid});
	    	map.put("projectNum", data.get("PROJECT_NUM"));
	    	map.put("projectTypeId", data.get("PROJECT_TYPE_ID"));
	    	
	    	int projectTypeId=(Integer)data.get("PROJECT_TYPE_ID");
	    	TeeProjectType type=(TeeProjectType) simpleDaoSupport.get(TeeProjectType.class,projectTypeId);
	    	if(type!=null){
	    		map.put("projectTypeName", type.getTypeName());
	    	}
	    	
	    	int createrId=TeeStringUtil.getInteger(data.get("PROJECT_CREATER_ID"),0);
	    	TeePerson creater=(TeePerson) simpleDaoSupport.get(TeePerson.class,createrId);
	    	if(creater!=null){
	    		map.put("createrName", creater.getUserName());
	    	}
	    	
	    	
	    	if(data.get("PROJECT_PF_TIME")!=null){
	    		//批复时间
	    		map.put("projectPFTime", sdf.format((Date)data.get("PROJECT_PF_TIME")));
	    	}
 	    	//项目申报文件
 	    	//map.put("attachSids", data.get("ATTACH_SIDS"));
 	    	//List<Map<String,Object>> list = getAttachMentNames(TeeStringUtil.getString(data.get("ATTACH_SIDS")));
 	    	map.put("attachSidsName", getAttachMentNames2(TeeAttachmentModelKeys.projectFile,TeeStringUtil.getString(data.get("UUID"))));
 	        //经信委批复金额
 	    	map.put("jxwPFMoney", data.get("JXW_PF_MONEY"));
 	    	//经信委批复函
 	    	map.put("jxwPFH", data.get("JXW_PF_H"));
 	    	//财政局批复金额
 	    	map.put("czjPFMoney", data.get("CZJ_PF_MONEY"));
 	    	//财政局批复负责人
 	    	map.put("czjPFPerson", data.get("CZJ_PF_PERSON"));
 	    	TeePerson person=(TeePerson)simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger(data.get("CZJ_PF_PERSON"), 0));
 	    	if(person!=null){
 	    		map.put("czjPFPersonName", person.getUserName());
 	    	}
 	    	//财政局批复附件
 	    	//map.put("czjAttachSids", data.get("CZJ_ATTACH_SIDS"));
 	    	
 	    	map.put("czjAttachSidsName", getAttachMentNames2(TeeAttachmentModelKeys.projectPfFile,TeeStringUtil.getString(data.get("UUID"))));
 	    	//财政局批复内容
 	    	map.put("czjPFContent", data.get("CZJ_PF_CONTENT"));
	    	map.put("czjPFMoney", data.get("CZJ_PF_MONEY"));
	    	
	    	map.put("projectName", data.get("PROJECT_NAME"));
	    	try {
				map.put("time", sdf.format((Date)data.get("BEGIN_TIME"))+" ~ "+sdf.format((Date)data.get("END_TIME")));
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	map.put("address", TeeStringUtil.getString(data.get("PROVINCE"))+TeeStringUtil.getString(data.get("CITY"))+TeeStringUtil.getString(data.get("DISTRICT")));
	    	map.put("addressDesc", data.get("ADDRESS_DESC"));
	    	map.put("status", data.get("STATUS"));
	    	map.put("coordinate", data.get("ADDRESS_COORDINATE"));
	    	map.put("projectLevel", data.get("PROJECT_LEVEL"));
	    	map.put("diskIds", data.get("PROJECT_FILE_NETDISK_IDS"));
	    	map.put("progress", data.get("PROGRESS")+"%");
	    	map.put("coordinate", data.get("ADDRESS_COORDINATE"));
	    	if(!("").equals(TeeStringUtil.getString(data.get("PROJECT_FILE_NETDISK_IDS")))){
	    		//根据文件夹id字符串  获取文件夹名称
		    	String[] diskIdArray=TeeStringUtil.getString(data.get("PROJECT_FILE_NETDISK_IDS")).split(",");
		    	String diskNames="";
		    	TeeFileNetdisk  disk=null;
		    	for (String str : diskIdArray) {
					int sid=TeeStringUtil.getInteger(str, 0);
					disk=(TeeFileNetdisk) simpleDaoSupport.get(TeeFileNetdisk.class,sid);
					if(disk!=null){
						diskNames+=disk.getFileName()+",";
					}
				}
		    	if(diskNames.endsWith(",")){
		    		diskNames=diskNames.substring(0,diskNames.length()-1);
		    	}
		    	map.put("diskNames", diskNames);
	    	}
	    	map.put("projectBudget", data.get("PROJECT_BUDGET"));
	    	map.put("managerId", data.get("PROJECT_MANAGER_ID"));
	        //项目负责人
	    	if(TeeStringUtil.getInteger( data.get("PROJECT_MANAGER_ID"), 0)!=0){
	    		TeePerson  manager=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger( data.get("PROJECT_MANAGER_ID"), 0));
	    	    map.put("managerName", manager.getUserName());
	    	}
	    	//项目审批人
	    	map.put("approverId", data.get("PROJECT_APPROVER_ID"));
	    	if(TeeStringUtil.getInteger( data.get("PROJECT_APPROVER_ID"), 0)!=0){
	    		TeePerson  approver=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger( data.get("PROJECT_APPROVER_ID"), 0));
	    	    map.put("approverName", approver.getUserName());
	    	}
	    	map.put("projectContent", data.get("PROJECT_CONTENT"));
	    	//获取项目成员
	    	String projectMemberIds="";
	    	String projectMemberNames="";
	    	List<Map> memberList=simpleDaoSupport.executeNativeQuery(" select * from project_member where project_id=?", new Object[]{uuid}, 0, Integer.MAX_VALUE);
	    	TeePerson member=null;
	    	for (Map m : memberList){
	    		projectMemberIds+=TeeStringUtil.getInteger(m.get("member_id"), 0)+",";
	    		member=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger(m.get("member_id"), 0));
	    		if(member!=null){
	    			projectMemberNames+=member.getUserName()+",";
	    		}
			}
	    	if(projectMemberIds.endsWith(",")){
	    		projectMemberIds=projectMemberIds.substring(0,projectMemberIds.length()-1);
	    	}
	    	if(projectMemberNames.endsWith(",")){
	    		projectMemberNames=projectMemberNames.substring(0,projectMemberNames.length()-1);
	    	}
	    	map.put("projectMemberIds", projectMemberIds);
	    	map.put("projectMemberNames", projectMemberNames);
	    	//获取共享人员
	    	String projectShareIds="";
	    	String projectShareNames="";
	    	List<Map> shareList=simpleDaoSupport.executeNativeQuery(" select * from project_share where project_id=?", new Object[]{uuid}, 0, Integer.MAX_VALUE);
	    	TeePerson share=null;
	    	for (Map m : shareList){
	    		projectShareIds+=TeeStringUtil.getInteger(m.get("share_id"), 0)+",";
	    		share=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger(m.get("share_id"), 0));
	    		if(share!=null){
	    			projectShareNames+=share.getUserName()+",";
	    		}
			}
	    	if(projectShareIds.endsWith(",")){
	    		projectShareIds=projectShareIds.substring(0,projectShareIds.length()-1);
	    	}
	    	if(projectShareNames.endsWith(",")){
	    		projectShareNames=projectShareNames.substring(0,projectShareNames.length()-1);
	    	}
	    	map.put("projectShareIds", projectShareIds);
	    	map.put("projectShareNames", projectShareNames);
	    	
	    	//获取观察者
	    	String projectViewIds="";
	    	String projectViewNames="";
	    	List<Map> viewList=simpleDaoSupport.executeNativeQuery(" select * from project_view where project_id=?", new Object[]{uuid}, 0, Integer.MAX_VALUE);
	    	TeePerson view=null;
	    	for (Map m : viewList){
	    		projectViewIds+=TeeStringUtil.getInteger(m.get("view_id"), 0)+",";
	    		view=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger(m.get("view_id"), 0));
	    		if(view!=null){
	    			projectViewNames+=view.getUserName()+",";
	    		}
			}
	    	if(projectViewIds.endsWith(",")){
	    		projectViewIds=projectViewIds.substring(0,projectViewIds.length()-1);
	    	}
	    	if(projectViewNames.endsWith(",")){
	    		projectViewNames=projectViewNames.substring(0,projectViewNames.length()-1);
	    	}
	    	map.put("projectViewIds", projectViewIds);
	    	map.put("projectViewNames", projectViewNames);
	    	
	    	//根据项目类型主键 获取自定义字段的值
	    	List<TeeProjectCustomField> list=simpleDaoSupport.executeQuery(" from TeeProjectCustomField where projectType.sid=? ", new Object[]{data.get("PROJECT_TYPE_ID")});
	    	if(list!=null){
	    		for (TeeProjectCustomField field : list) {
					if(("单行输入框").equals(field.getFieldType())||("多行输入框").equals(field.getFieldType())){
						map.put("FIELD_"+field.getSid(),data.get("FIELD_"+field.getSid()));
					}else if(("下拉列表").equals(field.getFieldType())){
						String fieldCtrModel=field.getFieldCtrModel();
						Map m=TeeJsonUtil.JsonStr2Map(fieldCtrModel);
						if(("系统编码").equals(m.get("codeType"))){
				            String value=(String) m.get("value");
				            String name=TeeSysCodeManager.getChildSysCodeNameCodeNo(value,(String)(data.get("FIELD_"+field.getSid())));
				            map.put("FIELD_"+field.getSid(),name);
						}else if(("自定义选项").equals(m.get("codeType"))){
							String value1=(String)m.get("value");
							
							JSONArray array = JSONArray.fromObject(value1);

							String[] optionNames=((String)array.get(0)).split(",");
							String[] optionValues=((String)array.get(1)).split(",");
							for (int i=0;i<optionValues.length;i++) {
								if(data.get("FIELD_"+field.getSid())!=null){
									if(((String)(data.get("FIELD_"+field.getSid()))).equals(optionValues[i])){
										map.put("FIELD_"+field.getSid(),optionNames[i]);
										break;
									}
								}
								
							}
						}
					}
				}
	    	}
	    	
	    	
	    	json.setRtState(true);
	    	json.setRtData(map);
	    	json.setRtMsg("数据获取成功！");
	    }else{
	    	json.setRtState(false);
	    	json.setRtMsg("数据获取失败！");
	    }
	    
		return json;
	}



	/**
	 * 获取挂起中的项目
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getHangProject(HttpServletRequest request,
			TeeDataGridModel dm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
		
		List list=new ArrayList();
		list.add(loginUser.getUuid());
		list.add(loginUser.getUuid());
		list.add(status);
		long total=simpleDaoSupport.countSQLByList(" select count(*) from project where (project_creater_id=? or project_manager_id=?) and status=? ", list);
		json.setTotal(total);
		List<Map> resultList=simpleDaoSupport.executeNativeQuery(" select * from project where  (project_creater_id=? or project_manager_id=?) and status=? ", new Object[]{loginUser.getUuid(),loginUser.getUuid(),status}, (dm.getPage() - 1) * dm.getRows(),  dm.getRows());
		List<Map> rows=new ArrayList<Map>();
		Map map=null;
		if(resultList!=null){
			TeePerson manager=null;
			for (Map m: resultList) {
				map=new HashMap();
				String uuid=TeeStringUtil.getString(m.get("UUID"));
				map.put("uuid", uuid);
				map.put("projectName", m.get("PROJECT_NAME"));
				map.put("projectNum", m.get("PROJECT_NUM"));
				map.put("endTime",sdf.format(m.get("END_TIME")));
				map.put("beginTime",sdf.format(m.get("BEGIN_TIME")));
				
				int managerId=(Integer) m.get("PROJECT_MANAGER_ID");
				manager=(TeePerson) simpleDaoSupport.get(TeePerson.class,managerId);
				map.put("managerName", manager.getUserName());
				
				//获取项目成员
				List<Map> pMemberList=simpleDaoSupport.executeNativeQuery("select * from project_member where project_id=? ", new Object[]{uuid}, 0, Integer.MAX_VALUE);
				String projectMemberNames="";
				
				for (Map m1 : pMemberList) {
					TeePerson p=(TeePerson) simpleDaoSupport.get(TeePerson.class,TeeStringUtil.getInteger(m1.get("member_id"), 0));
					projectMemberNames+=p.getUserName()+",";
				}
				if(projectMemberNames.endsWith(",")){
					projectMemberNames=projectMemberNames.substring(0,projectMemberNames.length()-1);
				}
				map.put("projectMemberNames", projectMemberNames);
				map.put("progress", m.get("PROGRESS"));
				rows.add(map);
			}
		}
		
		
		json.setRows(rows);
		return json;
	}



	/**
	 * 根据项目的主键   获取项目的负责人和项目成员
	 * @param request
	 * @return
	 */
	public TeeJson getManagerOrMember(HttpServletRequest request) {
		TeeJson  json=new TeeJson();
		List<Map> rows=new  ArrayList<Map>();
		//获取页面上传来的项目的主键
		String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
	    //获取项目的负责人
		Map map=simpleDaoSupport.executeNativeUnique(" select * from project where uuid=? ", new Object[]{uuid});
		int managerId=TeeStringUtil.getInteger(map.get("PROJECT_MANAGER_ID"), 0);
		TeePerson manager=(TeePerson) simpleDaoSupport.get(TeePerson.class,managerId);
		Map managerMap=new HashMap();
		if(manager!=null){
			managerMap.put("uuid", manager.getUuid());
			managerMap.put("userName", manager.getUserName());
			rows.add(managerMap);
		}
		List<TeeProjectMember>list=simpleDaoSupport.executeQuery(" from TeeProjectMember pm where pm.projectId=? ", new Object[]{uuid});
	    TeePerson member=null;
	    Map m=null;
		for (TeeProjectMember pm : list) {
			if(pm.getMemberId()!=managerId){
				m=new HashMap();
				member=(TeePerson) simpleDaoSupport.get(TeePerson.class,pm.getMemberId());
			    m.put("uuid", member.getUuid());
			    m.put("userName", member.getUserName());
				rows.add(m);
			}
		} 
		json.setRtState(true);
		json.setRtData(rows);
        json.setRtMsg("数据获取成功！");
		return json;
	}


    /**
     * 判断当前登陆人是不是项目的观察者
     * @param request
     * @return
     */
	public TeeJson isViewer(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取项目主键
		String uuid=TeeStringUtil.getString(request.getParameter("projectId"));
		
		List list=simpleDaoSupport.executeNativeQuery(" select * from project_view pv where pv.project_id=? and pv.view_id=?", new Object[]{uuid,loginUser.getUuid()},0,Integer.MAX_VALUE);
		
		if(list!=null&&list.size()>0){//是观察者
			json.setRtData(1);
		}else{
			json.setRtData(0);
		}
		json.setRtState(true);
		return json;
	}



	/**
	 * 项目文档   获取项目树
	 * @param request
	 * @return
	 */
	public TeeJson getProjectTree(HttpServletRequest request) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeJson json=new TeeJson();
		//获取当前登录的用户
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取页面上传来的项目的状态
		int status=TeeStringUtil.getInteger(request.getParameter("status"),0);
		//获取项目主键
		String uuid=TeeStringUtil.getString(request.getParameter("id"));
		List<TeeZTreeModel> list = new ArrayList<TeeZTreeModel>();	
		
		if(!("").equals(uuid)){
			Map map=simpleDaoSupport.executeNativeUnique(" select * from project where uuid=? ", new Object[]{uuid});
			String diskIds=TeeStringUtil.getString(map.get("PROJECT_FILE_NETDISK_IDS"));
			if(!("").equals(diskIds)){
				String []diskIdArray=diskIds.split(",");
				TeeFileNetdisk disk=null;
				TeeZTreeModel m=null;
				for (String str : diskIdArray) {
					int sid=TeeStringUtil.getInteger(str, 0);
					disk=(TeeFileNetdisk) simpleDaoSupport.get(TeeFileNetdisk.class,sid);
					m = new TeeZTreeModel();
					m.setId(disk.getSid()+"");
					m.setName(disk.getFileName());
					m.setParent(false);
					m.getParams().put("type", 2);//文件
					m.getParams().put("projectId", uuid);//文件
					m.setIconSkin("wfNode1");
					list.add(m);
				}
			}	
		}else{
			String sql=" select * from project p  where ((p.project_creater_id=?) or (p.project_manager_id=?) or (exists (select * from project_member pm where pm.project_id=p.uuid and pm.member_id=? )) or (exists (select * from project_view pv where pv.project_id=p.uuid and pv.view_id=? )) ) and p.status=? ";
			List<Map>pList=simpleDaoSupport.executeNativeQuery( sql, new Object[]{loginUser.getUuid(),loginUser.getUuid(),loginUser.getUuid(),loginUser.getUuid(),status}, 0, Integer.MAX_VALUE);
			TeeZTreeModel m=null;
			for (Map map : pList) {
				m = new TeeZTreeModel();
				m.setId(TeeStringUtil.getString(map.get("UUID")));
				m.setName(TeeStringUtil.getString(map.get("PROJECT_NAME")));
				m.setParent(true);
				m.getParams().put("type", 1);//网盘文件夹
				
				m.getParams().put("num",TeeStringUtil.getString(map.get("PROJECT_NUM")) );//项目编号
				m.getParams().put("beginTime",sdf.format(TeeStringUtil.getDate(map.get("BEGIN_TIME"))) );//开始时间
				m.getParams().put("endTime",sdf.format(TeeStringUtil.getDate(map.get("END_TIME")) ));//结束时间
				
				m.getParams().put("type", 1);//网盘文件夹
				m.setIconSkin("wfNode2");
				list.add(m);
			}
			
		}

		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(list);	
		return json;
	}



	/**
	 * 判断当前登陆人  是不是项目负责人  或者项目成员
	 * @param request
	 * @return
	 */
	public TeeJson isManagerOrMember(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取项目主键
		String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
		
		List list=simpleDaoSupport.executeNativeQuery(" select * from project p where p.uuid=?  and ( (p.project_manager_id=?) or (exists (select *  from project_member pm where pm.project_id=p.uuid and pm.member_id=? ))) ", new Object[]{uuid,loginUser.getUuid(),loginUser.getUuid()},0,Integer.MAX_VALUE);
		
		if(list!=null&&list.size()>0){
			json.setRtData(1);
		}else{
			json.setRtData(0);
		}
		json.setRtState(true);
		return json;
	}



	
	/**
	 * 项目审批
	 * @param request
	 * @return
	 */
	public TeeJson approveProject(HttpServletRequest request) {
		//获取当前登录人 
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
		//获取拒绝原因
		String refusedReason=TeeStringUtil.getString(request.getParameter("refusedReason"));
		
		simpleDaoSupport.executeNativeUpdate(" update project set status=?,refused_reason=?  where uuid=? ", new Object[]{status,refusedReason,uuid});
		
		//获取项目的基本信息
		Map map=simpleDaoSupport.executeNativeUnique(" select * from project where uuid=? ", new Object[]{uuid});
		String projectName=TeeStringUtil.getString(map.get("PROJECT_NAME"));
		int managerId=TeeStringUtil.getInteger(map.get("PROJECT_MANAGER_ID"), 0);
		int createrId=TeeStringUtil.getInteger(map.get("PROJECT_CREATER_ID"), 0);
		
		
		Set set=new HashSet();
		set.add(createrId);
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
		
		
		
		
		if(status==3){//同意
			
			
			
			
			
			//int projectTypeId,TeeTask t,TeeProject project
			TeeProject project=(TeeProject)simpleDaoSupport.get(TeeProject.class,uuid);
			//newFileFolderById  fileNetDiskService 文件夹的id//根目录   文件id projectTypeId
			//项目类型实体类
			TeeProjectType ptype = (TeeProjectType)simpleDaoSupport.get(TeeProjectType.class,project.getProjectTypeId());
			int dIds = TeeStringUtil.getInteger(project.getProjectFileNetdiskIds(), 0);
			//创建文件夹
			Integer addFileNetDisk = addFileNetDisk(dIds,project.getProjectTypeId(),ptype.getTypeName(),loginUser,project.getUuid());
			//创建任务
			getProjectType(uuid,0,project.getProjectTypeId(),project.getUuid());
			// 发送消息    给项目负责人   
			Map requestData1 = new HashMap();
			requestData1.put("content", "您负责的项目“"+projectName+"”已审批通过，请具体分配相关任务。");
			requestData1.put("userListIds", managerId);
			requestData1.put("moduleNo", "060");
			requestData1.put("remindUrl","/system/subsys/project/projectdetail/index.jsp?uuid="+uuid);
			smsManager.sendSms(requestData1, loginUser);
			
			
			//发送消息  给项目成员   项目观察者  项目创建人   
			Map requestData2 = new HashMap();
			requestData2.put("content", "项目“"+projectName+"”已审批通过。");
			requestData2.put("userListIds",userListIds);
			requestData2.put("moduleNo", "060");
			requestData2.put("remindUrl","/system/subsys/project/projectdetail/index.jsp?uuid="+uuid);
			smsManager.sendSms(requestData2, loginUser);
			
			
		}else if(status==6){//拒绝
			// 发送消息    给项目负责人   
			Map requestData1 = new HashMap();
			requestData1.put("content", "您负责的项目“"+projectName+"”已被拒绝，拒绝原因："+refusedReason+"。");
			requestData1.put("userListIds", managerId);
			requestData1.put("moduleNo", "060");
			smsManager.sendSms(requestData1, loginUser);
		
			//发送消息    项目创建人   
			Map requestData2 = new HashMap();
			requestData2.put("content", "您创建的项目“"+projectName+"”已被拒绝，拒绝原因："+refusedReason+"。");
			requestData2.put("userListIds",createrId);
			requestData2.put("moduleNo", "060");
			smsManager.sendSms(requestData2, loginUser);
		
		}
		
		
		json.setRtState(true);
		json.setRtMsg("操作成功");
	
		return json;
	}

	//创建新任务
	public Integer addTask(String typeName,int orderNum,String proId,int shangJiTask,int qianZhiTask){
		TeeTask task=new TeeTask();
		TeeProject project=(TeeProject)simpleDaoSupport.get(TeeProject.class,proId);
		//任务名称
		task.setTaskName(typeName);
		//任务序号
		task.setTaskNo(orderNum+"");
		//项目
		task.setProject(project);
		//创建人
		task.setCreater(project.getProjectCreateId());
		//状态
		task.setStatus(2);
		//创建时间
		task.setCreateTime(new Date());
		//任务进度
		//上级任务
		TeeTask task2 = (TeeTask)simpleDaoSupport.get(TeeTask.class,shangJiTask);
		task.setHigherTask(task2);
		task.setTaskLevel("次要");
		//前置任务
		TeeTask task3 = (TeeTask)simpleDaoSupport.get(TeeTask.class,qianZhiTask);
		task.setPreTask(task3);
		task.setProgress(0);
		Serializable save=simpleDaoSupport.save(task);
		///task.setSid(TeeStringUtil.getInteger(save, 0));
		//task2.setNextTask(task);
		//simpleDaoSupport.update(task2);
		return TeeStringUtil.getInteger(TeeStringUtil.getString(save), 0);
	}
	
	//递归创建任务
	public void getProjectType(String proId,int shangJiTask,int projectTypeId,String projectId){
		String hql="from TeeProjectType where parentId="+projectTypeId+" order by orderNum asc";
		List<TeeProjectType> find = simpleDaoSupport.find(hql, null);
		if(find!=null && find.size()>0){
			TeeTask addTask=new TeeTask();
			for(TeeProjectType projectType:find){
				int sjId=addTask(projectType.getTypeName(),projectType.getOrderNum(),proId,shangJiTask,0);
				simpleDaoSupport.deleteOrUpdateByQuery("update TeeTaskProjectTypeFile set taskId=? where projectTypeId=? and projectId=?", new Object[]{sjId,projectType.getSid(),projectId});
				getProjectType(proId,sjId,projectType.getSid(),projectId);
			}
		}
	}
	


	/**
	 * 挂起项目
	 * @param request
	 * @return
	 */
	public TeeJson hang(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
		simpleDaoSupport.executeNativeUpdate(" update project set status=? where uuid=? ", new Object[]{status,uuid});
		
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
		// 发送消息   创建人   负责人   观察者   成员
		Map requestData1 = new HashMap();
		requestData1.put("content", "项目“"+projectName+"”已被"+loginUser.getUserName()+"挂起。");
		requestData1.put("userListIds", userListIds);
		requestData1.put("moduleNo", "060");
		requestData1.put("remindUrl","/system/subsys/project/projectdetail/index.jsp?uuid="+uuid);
		smsManager.sendSms(requestData1, loginUser);
		
		
		json.setRtState(true);
		json.setRtMsg("操作成功");
		
		return json;
	}

	
	
    /**
     * 根据项目主键   获取项目创建人  项目负责人    项目观察者的id字符串
     * @param uuid
     * @return
     */
	public String getCreaterAndManagerAndViewerIds(String uuid){
		//获取项目的基本信息
				Map map=simpleDaoSupport.executeNativeUnique(" select * from project where uuid=? ", new Object[]{uuid});
				String projectName=TeeStringUtil.getString(map.get("PROJECT_NAME"));
				int managerId=TeeStringUtil.getInteger(map.get("PROJECT_MANAGER_ID"), 0);
				int createrId=TeeStringUtil.getInteger(map.get("PROJECT_CREATER_ID"), 0);
						
				Set set=new HashSet();
				set.add(createrId);
				set.add(managerId);
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
				return userListIds;
		
		
		
	}
	
	/**
	 * 根据项目主键   获取  项目创建人  负责人  观察者  和  项目成员的id字符串
	 * 
	 * @return
	 */
	public  String  getCreaterAndManagerAndViewerAndMemberIds(String uuid){
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
		return userListIds;
		
	}



	
	/**
	 * 项目变更
	 * @param request
	 * @return
	 */
	public TeeJson projectChange(HttpServletRequest request) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    TeeJson json=new TeeJson();
	    String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
	    int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
	    
	    String projectNum=TeeStringUtil.getString(request.getParameter("projectNum"));
    	String projectName=TeeStringUtil.getString(request.getParameter("projectName"));
    	int projectTypeId=TeeStringUtil.getInteger(request.getParameter("projectTypeId"), 0);
    	String startTime=TeeStringUtil.getString(request.getParameter("startTime"));
    	String endTime=TeeStringUtil.getString(request.getParameter("endTime"));
    	Date start=null;
		Date end=null;
    	try {
			 start=sdf.parse(startTime);
			 end=sdf.parse(endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String province=TeeStringUtil.getString(request.getParameter("province"));
    	String city=TeeStringUtil.getString(request.getParameter("city"));
    	String district=TeeStringUtil.getString(request.getParameter("district"));
    	String addressDesc=TeeStringUtil.getString(request.getParameter("addressDesc"));
    	String coordinate=TeeStringUtil.getString(request.getParameter("coordinate"));
    	String projectLevel=TeeStringUtil.getString(request.getParameter("projectLevel"));
    	String diskIds=TeeStringUtil.getString(request.getParameter("diskIds"));
    	double projectBudget=TeeStringUtil.getDouble(request.getParameter("projectBudget"), 0);
    	int managerId=TeeStringUtil.getInteger(request.getParameter("managerId"), 0);
    	
    	String projectContent=TeeStringUtil.getString(request.getParameter("projectContent"));
    		
    	//项目成员
    	String projectMemberIds=TeeStringUtil.getString(request.getParameter("projectMemberIds"));
    	//共享人员
    	String projectShareIds=TeeStringUtil.getString(request.getParameter("projectShareIds"));
    	//项目观察者
    	String projectViewIds=TeeStringUtil.getString(request.getParameter("projectViewIds"));
    	Map requestData = TeeServletUtility.getParamMap(request);
    	Map<String,String> customMap=new HashMap<String, String>();//存放自定义字段
        for(Object obj:requestData.keySet()){
        	String str=(String)obj;
        	if(str.startsWith("FIELD_")){
        		customMap.put(str, (String) requestData.get(str));
        	}
        }
        
        
    	if(uuid!=null&&!("").equals(uuid)){//编辑
    		TeeProject project=(TeeProject) simpleDaoSupport.get(TeeProject.class,uuid);
    		int approverId=0;
    		if(project!=null){
    			approverId=project.getApproverId();
    		}
    		
    		//删除中间表中之前的数据
    		simpleDaoSupport.executeNativeUpdate(" delete from project_member where project_id=? ", new Object[]{uuid});
    		simpleDaoSupport.executeNativeUpdate(" delete from project_share where project_id=? ", new Object[]{uuid});
    		simpleDaoSupport.executeNativeUpdate(" delete from project_view where project_id=? ", new Object[]{uuid});
    		
 	    	//往中间表中插入数据
 	    	if(projectShareIds!=null&&!("").equals(projectShareIds)){
 	    		String[] shareIdArray=projectShareIds.split(",");
 	    		for (String str : shareIdArray) {
 					int shareId=TeeStringUtil.getInteger(str, 0);
 					simpleDaoSupport.executeNativeUpdate(" insert into project_share(project_id,share_id) values (?,?) ", new Object[]{uuid,shareId});
 	    		}
 	    	}
 	    	if(projectMemberIds!=null&&!("").equals(projectMemberIds)){
 	    		String[] memberIdArray=projectMemberIds.split(",");
 	    		for (String str : memberIdArray) {
 					int memberId=TeeStringUtil.getInteger(str, 0);
 					simpleDaoSupport.executeNativeUpdate(" insert into project_member(project_id,member_id) values (?,?) ", new Object[]{uuid,memberId});
 				}
 	    	}
 	    	if(projectViewIds!=null&&!("").equals(projectViewIds)){
 	    		String[] viewIdArray=projectViewIds.split(",");
 	    		for (String str : viewIdArray) {
 					int viewId=TeeStringUtil.getInteger(str, 0);
 					simpleDaoSupport.executeNativeUpdate(" insert into project_view(project_id,view_id) values (?,?) ", new Object[]{uuid,viewId});
 				}
 	    	}
 	    	//向项目表中插入数据
 	    	String sql=" update project set project_num=?,project_type_id=?,project_name=?,begin_time=?,end_time=?,province=?,city=?,district=?,address_desc=?,address_coordinate=?,"
 	    			+ "project_level=?,project_file_netdisk_ids=?,project_budget=?,project_manager_id=?,project_approver_id=?,project_content=?,status=?,project_creater_id=?";
 	    	
 	    	List<Object> param=new ArrayList<Object>(); 	
 	    	param.add(projectNum);
 	    	param.add(projectTypeId);
 	    	param.add(projectName);
 	    	param.add(start);
 	    	param.add(end);
 	    	param.add(province);
 	    	param.add(city);
 	    	param.add(district);
 	    	param.add(addressDesc);
 	    	param.add(coordinate);
 	    	param.add(projectLevel);
 	    	param.add(diskIds);
 	    	param.add(projectBudget);
 	    	param.add(managerId);
 	    	param.add(approverId);
 	    	param.add(projectContent);
 	    	
 	    	if(status==2){//提交
 	    		if(approverId==0){//免审批
 	    			param.add(3);
 	    		}else{
 	    			param.add(2);
 	    			
 	    		}
 	    	}
 	    	param.add(loginUser.getUuid());
 	    	
 	    	for(String name:customMap.keySet()){
 	    		sql+=","+name+"=?";
 	    		param.add(customMap.get(name));
 	    	}
 	    	sql+="  where uuid=?"; 
 	    	param.add(uuid);
 	    	simpleDaoSupport.executeNativeUpdate(sql, param.toArray());
 	    	json.setRtMsg("编辑成功！");
 	    	
 	    	
 	    	
 	    	//发送消息
 	    	if(approverId!=0){//不是免审批
 	    		//发送消息
 		    	
 		    	Map requestData1 = new HashMap();
 			    requestData1.put("content", "项目“"+projectName+"”已变更，需要重新审批，请及时办理");
 			    requestData1.put("userListIds", approverId);
 			    requestData1.put("moduleNo", "060");
 			    requestData1.put("remindUrl","/system/subsys/project/projectApprove/index.jsp");
 			    smsManager.sendSms(requestData1, loginUser);
 		    	
 	    		
 	    	}
	    }
	    json.setRtState(true);
		return json;
	}



	/**
	 * 恢复挂起
	 * @param request
	 * @return
	 */
	public TeeJson restoreHang(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
		simpleDaoSupport.executeNativeUpdate(" update project set status=? where uuid=? ", new Object[]{status,uuid});
		
		TeeProject project=(TeeProject) simpleDaoSupport.get(TeeProject.class,uuid);
		String projectName="";
		if(project!=null){
			projectName=project.getProjectName();
		}
		//发送消息
		String userListIds=getCreaterAndManagerAndViewerAndMemberIds(uuid);
		// 发送消息
		Map requestData1 = new HashMap();
		requestData1.put("content", "项目“"+projectName+"”已被"+loginUser.getUserName()+"恢复挂起。");
		requestData1.put("userListIds", userListIds);
		requestData1.put("moduleNo", "060");
		requestData1.put("remindUrl","/system/subsys/project/projectdetail/index.jsp?uuid="+uuid);
		smsManager.sendSms(requestData1, loginUser);
		

		json.setRtState(true);
		json.setRtMsg("恢复成功！");
		return json;
	}

	/**
	 * 获取项目在网盘中的文件夹id
	 * */
	public TeeJson getFileNetdisk(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String uuid=request.getParameter("uuid");
		Map map = simpleDaoSupport.executeNativeUnique("select project_file_netdisk_ids as fileId from project where uuid=?", new Object[]{uuid});
		if(map!=null){
			json.setRtData(map.get("fileId"));
		}
		return json;
	}
	
	/**
	 * 获取自定义字段
	 * */
	public TeeJson getZiDingInfoByUuid(HttpServletRequest request) {
		TeeJson  json =new  TeeJson();
	    int taskId=TeeStringUtil.getInteger(request.getParameter("taskId"),0);
	    TeeTask task=(TeeTask)simpleDaoSupport.get(TeeTask.class,taskId);
	    if(task!=null){
	    	Map map=new HashMap();
	    	//获取项目表中的数据
	    	Map data=simpleDaoSupport.executeNativeUnique(" select * from project where uuid=? ", new Object[]{task.getProject().getUuid()});
	    	//获取自定义字段的值
	    	for(Object obj:data.keySet()){
	    		String name=(String)obj;
	    		if(name.startsWith("FIELD_")){
	    			map.put(name,data.get(name));
	    		}
	    	}
	    	json.setRtState(true);
	    	json.setRtData(map);
	    	json.setRtMsg("数据获取成功！");
	    }else{
	    	json.setRtState(false);
	    	json.setRtMsg("数据获取失败！");
	    }
		return json;
	}

	/**
	 * 获取项目下面的所有子任务
	 * */
	public TeeJson getTaskAll(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String sid = request.getParameter("sid");
		String hql="from TeeTask where project.uuid=? and higherTask=null order by taskNo";
		List<TeeTask> find = simpleDaoSupport.find(hql, new Object[]{sid});
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		if(find!=null && find.size()>0){
			for(TeeTask task:find){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("sid", task.getSid());
				map.put("taskName", task.getTaskName());
				if(task.getManager()!=null){
					map.put("managerName", task.getManager().getUserName());
				}
				map.put("progress", task.getProgress());
				map.put("status", task.getStatus());
				map.put("beginTime", TeeDateUtil.format(task.getRealBeginTime(), "yyyy-MM-dd HH:mm"));
				map.put("endTime", TeeDateUtil.format(task.getRealEndTime(), "yyyy-MM-dd HH:mm"));
				List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
						
				getNextTask(task.getSid(),list);
				
				map.put("nextTask", list);
				listMap.add(map);
			}
		}
		TeeProject project = (TeeProject)simpleDaoSupport.get(TeeProject.class,sid);
		if(project!=null){
			json.setRtMsg(project.getProjectName());
		}
		json.setRtData(listMap);
		return json;
	}
	
	/**
	 * 
	 * 递归，查询出所有的下级任务
	 * */
	public List<Map<String,Object>> getNextTask(int taskId,List<Map<String,Object>> list){
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		List<TeeTask> find = simpleDaoSupport.find("from TeeTask where higherTask.sid=? order by taskNo", new Object[]{taskId});
		if(find!=null && find.size()>0){
			for(TeeTask task:find){
                	Map<String,Object> map=new HashMap<String,Object>();
    				map.put("sid", task.getSid());
    				map.put("taskName", task.getTaskName());
    				if(task.getManager()!=null){
    					map.put("managerName", task.getManager().getUserName());
    				}
    				map.put("progress", task.getProgress());
    				map.put("status", task.getStatus());
    				map.put("beginTime", TeeDateUtil.format(task.getRealBeginTime(), "yyyy-MM-dd HH:mm"));
    				map.put("endTime", TeeDateUtil.format(task.getRealEndTime(), "yyyy-MM-dd HH:mm"));
    				List<Map<String,Object>> list2=getNextTask(task.getSid(),list);
    				if(list2!=null && list2.size()>0){
    					
    				}else{
    					list.add(map);
    				}
    				map.put("nextTask", list2);
    				listMap.add(map);
                
				
			}
		}
		return listMap;
	}

	private List<Map<String,Object>> getNetTaskNull(List<Map<String, Object>> list) {
		
		return null;
	}

	/**
	 * 获取当前登录人的主管领导
	 * */
	public TeeJson getZhuGuanLingDao(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeDepartment dept = person.getDept();
		if(dept!=null){
			String manager = dept.getManager();
			if(manager!=null && !"".equals(manager)){
				List<TeePerson> find = personDao.find("from TeePerson where uuid in ("+manager+") order by userNo", null);
				if(find!=null && find.size()>0){
					for(TeePerson p:find){
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("uuid", p.getUuid());
						map.put("userName", p.getUserName());
						listMap.add(map);
					}
				}
				
			}
		}
		json.setRtData(listMap);
		json.setRtState(true);
		return json;
	}

	/**
	 * 获取附件id
	 * */
	public TeeJson getFuJianByTaskId(HttpServletRequest request) {
		TeeJson  json=new TeeJson();
		int taskId=TeeStringUtil.getInteger(request.getParameter("taskId"), 0);
		List<TeeProjectFile> find = simpleDaoSupport.find("from TeeProjectFile where taskId=?", new Object[]{taskId});
		List<Map<String,Object>> listM=new ArrayList<Map<String,Object>>();
		if(find!=null && find.size()>0){
			for(TeeProjectFile p:find){
				Map<String,Object> map=new HashMap<String,Object>();
				TeeAttachment attachemnt = p.getFile().getAttachemnt();
				map.put("attSid",attachemnt.getSid());
				map.put("attName", attachemnt.getFileName());
				listM.add(map);
			}
		}
		json.setRtData(listM);
		json.setRtState(true);
		json.setRtMsg("数据获取成功！");
		return json;
	}
	
	
}
