package com.tianee.oa.core.base.dam.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.tools.ant.filters.StringInputStream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.dam.bean.TeeDamBox;
import com.tianee.oa.core.base.dam.bean.TeeFileAttch;
import com.tianee.oa.core.base.dam.bean.TeeFiles;
import com.tianee.oa.core.base.dam.bean.TeeOperationRecords;
import com.tianee.oa.core.base.dam.bean.TeePreArchiveType;
import com.tianee.oa.core.base.dam.bean.TeeStoreHouse;
import com.tianee.oa.core.base.dam.dao.TeeDamFilesDao;
import com.tianee.oa.core.base.dam.model.TeeFilesModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.workFlowFrame.dataloader.TeeSimpleDataLoaderInterface;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunDoc;
import com.tianee.oa.core.workflow.flowrun.model.TeeFlowRunAipTemplateModel;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunAipTemplateServiceInterface;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.util.workflow.TeeWorkflowHelperInterface;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeDamFilesService extends TeeBaseService{
	@Autowired
	TeeDamFilesDao fileDao;
	@Autowired
	TeePersonDao personDao;
	@Autowired
	TeeAttachmentService attachmentService;
	@Autowired
	private TeeSmsManager smsManager;
	
	@Autowired
	private TeeAttachmentService attachService;
	
	@Autowired
	private TeeBaseUpload baseUpload;
	
	@Autowired
	private TeeSimpleDataLoaderInterface simpleDataLoader;
	
	@Autowired
	private TeeFlowFormServiceInterface flowFormService;
	
	@Autowired
	private TeeWorkflowHelperInterface helper;
	
	
	@Autowired
	private TeeFlowRunAipTemplateServiceInterface flowRunAipTemplateService;
	/**
	 * 新建/编辑
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request,TeeFilesModel model) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		//获取sid
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeFiles file=null;
		if(sid>0){//编辑
			file=(TeeFiles) simpleDaoSupport.get(TeeFiles.class,sid);
		    file.setOrgCode(model.getOrgCode());
		    file.setQzh(model.getQzh());
		    file.setYear(model.getYear());
		    file.setRetentionPeriod(model.getRetentionPeriod());
		    file.setTitle(model.getTitle());
		    file.setUnit(model.getUnit());
		    file.setNumber(model.getNumber());
		    file.setMj(model.getMj());
		    file.setHj(model.getHj());
		    file.setSubject(model.getSubject());
		    file.setRemark(model.getRemark());
		    file.setJh(model.getJh());
		    if(file.getOpFlag()==3){//已加入卷盒  管理员整理   就得顺便修改档案号
		    	String rtStr=TeeSysCodeManager.getChildSysCodeNameCodeNo("DAM_RT", model.getRetentionPeriod());
		    	file.setDah(model.getOrgCode()+"-"+model.getQzh()+"-"+model.getYear()+"-"+rtStr+"-"+file.getBox().getBoxNo()+"-"+model.getJh());
		    } 
			simpleDaoSupport.update(file);
			
			//记录档案操作日志
			TeeOperationRecords  record=new  TeeOperationRecords();
			record.setFile(file);
			record.setOperTime(Calendar.getInstance());
			record.setOperUser(loginUser);
			record.setContent(loginUser.getUserName()+"整理了档案！");
			simpleDaoSupport.save(record);
			
			
			
			json.setRtData(file.getSid());
			json.setRtMsg("编辑成功");
			json.setRtState(true);	
		}else{//新增
			file=new TeeFiles();
		    BeanUtils.copyProperties(model, file);
		    file.setCreateTime(Calendar.getInstance());
		    file.setCreateUser(loginUser);
			file.setDelFlag(0);
			file.setOpFlag(1);
			file.setViewFlag(0);
			file.setViewTotal(0);
			simpleDaoSupport.save(file);
			
			//记录档案操作日志
			TeeOperationRecords  record=new  TeeOperationRecords();
			record.setFile(file);
			record.setOperTime(file.getCreateTime());
			record.setOperUser(loginUser);
			record.setContent(loginUser.getUserName()+"创建了档案！");
			simpleDaoSupport.save(record);
			
			
			json.setRtData(file.getSid());
			json.setRtMsg("新建成功");
			json.setRtState(true);
		}
		return json;
	}

	
	
	/**
	 * 获取预归档文件列表
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getPreFileList(TeeDataGridModel dm,
			HttpServletRequest request) {
		String orgCode=TeeStringUtil.getString(request.getParameter("orgCode"));//组织机构代码
		String qzh=TeeStringUtil.getString(request.getParameter("qzh"));//全宗号
		String year=TeeStringUtil.getString(request.getParameter("year"));//年份
		String retentionPeriod=TeeStringUtil.getString(request.getParameter("retentionPeriod"));//保管期限
		String title=TeeStringUtil.getString(request.getParameter("title"));//标题
		String unit=TeeStringUtil.getString(request.getParameter("unit"));//单位
		String number=TeeStringUtil.getString(request.getParameter("number"));//文件编号
		String subject=TeeStringUtil.getString(request.getParameter("subject"));//主题词
		String mj=TeeStringUtil.getString(request.getParameter("mj"));//密级
		String hj=TeeStringUtil.getString(request.getParameter("hj"));//缓急
		String remark=TeeStringUtil.getString(request.getParameter("remark"));//备注
		
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql = " from TeeFiles where createUser.uuid="+loginUser.getUuid()+" and opFlag=1 ";
		
		if(orgCode!=null&&!("").equals(orgCode)){
			hql+=" and orgCode like '"+"%"+orgCode+"%"+"'";
		}
		if(qzh!=null&&!("").equals(qzh)){
			hql+=" and qzh like '"+ "%"+qzh+"%"+"'";
		}
		if(year!=null&&!("").equals(year)){
			hql+=" and year like '"+ "%"+year+"%"+"'";
		}
		if(retentionPeriod!=null&&!("").equals(retentionPeriod)){
			hql+=" and retentionPeriod='"+retentionPeriod+"'";
		}
		if(title!=null&&!("").equals(title)){
			hql+=" and title like '"+"%"+title+"%"+"'";
		}
		if(unit!=null&&!("").equals(unit)){
			hql+=" and unit like '"+"%"+unit+"%"+"'";
		}
		if(number!=null&&!("").equals(number)){
			hql+=" and number like '"+ "%"+number+"%"+"'";
		}
		if(subject!=null&&!("").equals(subject)){
			hql+=" and subject like'" +"%"+subject+"%"+"'";
		}
		if(mj!=null&&!(" ").equals(mj)){
			hql+=" and mj='"+mj+"'";
		}
		if(hj!=null&&!(" ").equals(hj)){
			hql+=" and hj='"+hj+"'";
		}
		if(remark!=null&&!("").equals(remark)){
			hql+=" and remark like '"+ "%"+remark+"%"+"'";
		}
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, null));// 设置总记录数
		hql += " order by createTime asc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeFiles> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查
		List<TeeFilesModel>	modelList=new ArrayList<TeeFilesModel>();
		if(list!=null&&list.size()>0){
			TeeFilesModel model=null;
			for (TeeFiles file : list) {
				model=parseToModel(file);
				modelList.add(model);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}



	/**
	 * 实体类转换成model
	 * @param file
	 * @return
	 */
	private TeeFilesModel parseToModel(TeeFiles file) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TeeFilesModel model=new TeeFilesModel();
		BeanUtils.copyProperties(file, model);
		//处理系统编码   保管期限
		if(file.getRetentionPeriod()!=null&&!("").equals(file.getRetentionPeriod())){
		    model.setRetentionPeriodStr(TeeSysCodeManager.getChildSysCodeNameCodeNo("DAM_RT",file.getRetentionPeriod()));
		}
		//处理创建人
		if(file.getCreateUser()!=null){
		    model.setCreateUserId(file.getCreateUser().getUuid());
		    model.setCreateUserName(file.getCreateUser().getUserName());
		}
		//处理创建时间
		if(file.getCreateTime()!=null){
			model.setCreateTimeStr(sdf.format(file.getCreateTime().getTime()));
		}
		//处理预归档分类
		if(file.getType()!=null){
			model.setTypeId(file.getType().getSid());
			model.setTypeName(file.getType().getTypeName());
		}
		//处理所属卷盒
		if(file.getBox()!=null){
			model.setBoxId(file.getBox().getSid());
			model.setBoxNo(file.getBox().getBoxNo());
		}
		//处理所属卷库
		if(file.getStoreHouse()!=null){
			model.setStoreHouseId(file.getStoreHouse().getSid());
			model.setStoreHouseName(file.getStoreHouse().getRoomName());
		}
		//处理归档人员
		if(file.getArchiveUser()!=null){
			model.setArchiveUserId(file.getArchiveUser().getUuid());
			model.setArchiveUserName(file.getArchiveUser().getUserName());
		}
		//处理归档时间
		if(file.getArchiveTime()!=null){
			model.setArchiveTimeStr(sdf.format(file.getArchiveTime().getTime()));
		}
		return model;
	}


    /**
     * 根据主键获取详情
     * @param request
     * @param model
     * @return
     */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			TeeFiles file=(TeeFiles) simpleDaoSupport.get(TeeFiles.class,sid);
			if(file!=null){
				TeeFilesModel model=parseToModel(file);
				json.setRtData(model);
				json.setRtState(true);
			}else{
				json.setRtMsg("数据获取失败！");
				json.setRtState(false);
			}
		}else{
			json.setRtMsg("数据获取失败！");
			json.setRtState(false);
		}
		return json;
	}



	/**
	 * 删除预归档的文件
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeFiles file=(TeeFiles) simpleDaoSupport.get(TeeFiles.class,sid);
		    if(file!=null){
		    	//删除该档案关联的档案附件信息表
				simpleDaoSupport.deleteOrUpdateByQuery("delete TeeFileAttch where file.sid=? ", new Object[]{file.getSid()});
				//删除该档案关联的日志信息
				simpleDaoSupport.deleteOrUpdateByQuery("delete TeeOperationRecords where file.sid=? ", new Object[]{file.getSid()});
				//删除档案
				simpleDaoSupport.deleteByObj(file);
				json.setRtMsg("删除成功！");
				json.setRtState(true);
		    }	
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		
		return json;
	}



	/**
	 * 移交档案
	 * @param request
	 * @return
	 */
	public TeeJson turnOver(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		String opt=TeeStringUtil.getString(request.getParameter("opt"));
		String ids=TeeStringUtil.getString(request.getParameter("ids"));
		int typeId=TeeStringUtil.getInteger(request.getParameter("typeId"), 0);
		//获取预归档分类
		TeePreArchiveType type=null;
		if(typeId!=0){
			type=(TeePreArchiveType) simpleDaoSupport.get(TeePreArchiveType.class,typeId);
		}
		if(type!=null){
			String []idArray=null;
			if(ids!=null&&!("").equals(ids)){
				idArray=ids.split(",");
			}
			if(idArray!=null&&idArray.length>0){
				TeeFiles file=null;
				for (String str : idArray) {
					int id=TeeStringUtil.getInteger(str, 0);
					if(id>0){
						file=(TeeFiles) simpleDaoSupport.get(TeeFiles.class,id);
					    if(type!=null){
					    	file.setArchiveUser(type.getManager());
					    	file.setOpFlag(2);
					    	file.setType(type);
					    }
					    simpleDaoSupport.update(file);
					    
					    
					    //如果是单个移交   发消息
					    if(("single").equals(opt)){//单个移交
					    	if(type.getManager()!=null){
					    		Map requestData1 = new HashMap();
						    	requestData1.put("content", loginUser.getUserName()+"将《"+file.getTitle()+"》档案移交给了您，请及时处理！");
						    	requestData1.put("userListIds", type.getManager().getUuid());
						    	requestData1.put("moduleNo", "036");
						    	requestData1.put("remindUrl","/system/core/base/dam/fileManagement/index.jsp");
						    	smsManager.sendSms(requestData1, loginUser);
					    	}
						}
					}
				}
			}
			//发送消息
			if(("batch").equals(opt)){//批量移交
				if(type.getManager()!=null){
					Map requestData2 = new HashMap();
			    	requestData2.put("content", loginUser.getUserName()+"将"+idArray.length+"个档案移交给了您，请及时处理！");
			    	requestData2.put("userListIds", type.getManager().getUuid());
			    	requestData2.put("moduleNo", "036");
			    	requestData2.put("remindUrl","/system/core/base/dam/fileManagement/index.jsp");
			    	smsManager.sendSms(requestData2, loginUser);
				}
				
			}
			
			json.setRtState(true);
			json.setRtMsg("移交成功");
		}else{
			json.setRtState(false);
			json.setRtMsg("预归档分类获取失败！");
		}
		return json;
	}



	/**
	 * 获取档案整理  文件管理的列表
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getManageFileList(TeeDataGridModel dm,
			HttpServletRequest request) {
		
		//获取所属的预归档分类
		int typeId=TeeStringUtil.getInteger(request.getParameter("typeId"),0);
		
		
		String orgCode=TeeStringUtil.getString(request.getParameter("orgCode"));//组织机构代码
		String qzh=TeeStringUtil.getString(request.getParameter("qzh"));//全宗号
		String year=TeeStringUtil.getString(request.getParameter("year"));//年份
		String retentionPeriod=TeeStringUtil.getString(request.getParameter("retentionPeriod"));//保管期限
		String title=TeeStringUtil.getString(request.getParameter("title"));//标题
		String unit=TeeStringUtil.getString(request.getParameter("unit"));//单位
		String number=TeeStringUtil.getString(request.getParameter("number"));//文件编号
		String subject=TeeStringUtil.getString(request.getParameter("subject"));//主题词
		String mj=TeeStringUtil.getString(request.getParameter("mj"));//密级
		String hj=TeeStringUtil.getString(request.getParameter("hj"));//缓急
		String remark=TeeStringUtil.getString(request.getParameter("remark"));//备注
		
		
		
		
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql = " from TeeFiles where archiveUser.uuid="+loginUser.getUuid()+" and opFlag=2 ";
		
		if(typeId!=0){
			hql+=" and type.sid="+typeId+" ";
		}
		
		if(orgCode!=null&&!("").equals(orgCode)){
			hql+=" and orgCode like '"+"%"+orgCode+"%"+"'";
		}
		if(qzh!=null&&!("").equals(qzh)){
			hql+=" and qzh like '"+ "%"+qzh+"%"+"'";
		}
		if(year!=null&&!("").equals(year)){
			hql+=" and year like '"+ "%"+year+"%"+"'";
		}
		if(retentionPeriod!=null&&!("").equals(retentionPeriod)){
			hql+=" and retentionPeriod='"+retentionPeriod+"'";
		}
		if(title!=null&&!("").equals(title)){
			hql+=" and title like '"+"%"+title+"%"+"'";
		}
		if(unit!=null&&!("").equals(unit)){
			hql+=" and unit like '"+"%"+unit+"%"+"'";
		}
		if(number!=null&&!("").equals(number)){
			hql+=" and number like '"+ "%"+number+"%"+"'";
		}
		if(subject!=null&&!("").equals(subject)){
			hql+=" and subject like'" +"%"+subject+"%"+"'";
		}
		if(mj!=null&&!(" ").equals(mj)){
			hql+=" and mj='"+mj+"'";
		}
		if(hj!=null&&!(" ").equals(hj)){
			hql+=" and hj='"+hj+"'";
		}
		if(remark!=null&&!("").equals(remark)){
			hql+=" and remark like '"+ "%"+remark+"%"+"'";
		}
		

		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, null));// 设置总记录数
		hql += " order by createTime asc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeFiles> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查
		List<TeeFilesModel>	modelList=new ArrayList<TeeFilesModel>();
		if(list!=null&&list.size()>0){
			TeeFilesModel model=null;
			for (TeeFiles file : list) {
				model=parseToModel(file);
				modelList.add(model);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}


	/**
	 * 档案退回
	 * @param request
	 * @return
	 */
	public TeeJson drawBack(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		String ids=TeeStringUtil.getString(request.getParameter("ids"));
		String []idArray=null;
		if(ids!=null&&!("").equals(ids)){
			idArray=ids.split(",");
		}
		if(idArray!=null&&idArray.length>0){
			TeeFiles file=null;
			for (String str : idArray) {
				int id=TeeStringUtil.getInteger(str, 0);
				if(id>0){
					file=(TeeFiles) simpleDaoSupport.get(TeeFiles.class,id);
				    file.setType(null);
				    file.setArchiveUser(null);
				    file.setOpFlag(1);
				    simpleDaoSupport.update(file);
				    
				    if(file.getCreateUser()!=null){
				    	//发送消息
					    Map requestData1 = new HashMap();
				    	requestData1.put("content", loginUser.getUserName()+"将《"+file.getTitle()+"》档案退回给了您，请重新整理！");
				    	requestData1.put("userListIds", file.getCreateUser().getUuid());
				    	requestData1.put("moduleNo", "036");
				    	requestData1.put("remindUrl","/system/core/base/dam/preArchive/manage/index.jsp");
				    	smsManager.sendSms(requestData1, loginUser);
				    }    
				    
				}
			}
		}
		
		json.setRtState(true);
		json.setRtMsg("退回成功");
		return json;
	}



	/**
	 * 分配卷盒
	 * @param request
	 * @return
	 */
	public TeeJson distributeBox(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String ids=TeeStringUtil.getString(request.getParameter("ids"));
		int boxId=TeeStringUtil.getInteger(request.getParameter("boxId"), 0);
	    TeeDamBox box=null;
		if(boxId!=0){
			box=(TeeDamBox) simpleDaoSupport.get(TeeDamBox.class,boxId);
		}
		if(box!=null){
			String []idArray=null;
			if(ids!=null&&!("").equals(ids)){
				idArray=ids.split(",");
			}
			if(idArray!=null&&idArray.length>0){
				TeeFiles file=null;
				String retentionPeriodStr="";
				for (String str : idArray) {
					int id=TeeStringUtil.getInteger(str, 0);
					if(id>0){
						file=(TeeFiles) simpleDaoSupport.get(TeeFiles.class,id);
						retentionPeriodStr=TeeSysCodeManager.getChildSysCodeNameCodeNo("DAM_RT", file.getRetentionPeriod());
						file.setBox(box);
					    file.setOpFlag(3);//已加入卷盒
					    file.setDah(file.getOrgCode()+"-"+file.getQzh()+"-"+file.getYear()+"-"+retentionPeriodStr+"-"+box.getBoxNo()+"-"+file.getJh());
					    simpleDaoSupport.update(file);
					}
				}
				
				//修改卷盒文件数
				box.setFileNum(box.getFileNum()+idArray.length);
				simpleDaoSupport.update(box);
			}
			
			json.setRtState(true);
			json.setRtMsg("分配成功");
		}else{
			json.setRtState(false);
			json.setRtMsg("卷盒信息获取失败！");
		}
		
		return json;
	}



	/**
	 * 查看还未归档的卷盒下的文件列表
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getFileListByBoxId(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		
		int boxId=TeeStringUtil.getInteger(request.getParameter("boxId"),0);
		String hql = " from TeeFiles where box.sid="+boxId+" and opFlag=3 ";
		
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, null));// 设置总记录数
		hql += " order by createTime asc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeFiles> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查
		List<TeeFilesModel>	modelList=new ArrayList<TeeFilesModel>();
		if(list!=null&&list.size()>0){
			TeeFilesModel model=null;
			for (TeeFiles file : list) {
				model=parseToModel(file);
				modelList.add(model);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}



	/**
	 * 根据卷库主键  或者  卷盒主键  获取档案文件列表
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getFileListByBoxOrHouse(TeeDataGridModel dm,
			HttpServletRequest request) {
		
		String title=TeeStringUtil.getString(request.getParameter("title"));//标题
		String unit=TeeStringUtil.getString(request.getParameter("unit"));//单位
		String number=TeeStringUtil.getString(request.getParameter("number"));//文件编号
		String hj=TeeStringUtil.getString(request.getParameter("hj"));//缓急
		
		
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String id=TeeStringUtil.getString(request.getParameter("id"));
		String [] idArray=null;
		if(id!=null&&!("").equals(id)){
			idArray=id.split(";");
		}
		String hql = "";
		if(idArray!=null&&idArray.length>0){
			int sid=TeeStringUtil.getInteger(idArray[0], 0);
			if("room".equals(idArray[1])){//卷库
				hql=" from TeeFiles where opFlag=4 and delFlag=0 and storeHouse.sid="+sid;
			}else{//卷盒
				hql=" from TeeFiles where opFlag=4 and delFlag=0 and box.sid="+sid;
			}
			
			
			if(title!=null&&!("").equals(title)){
				hql+=" and title like '"+"%"+title+"%"+"'";
			}
			if(unit!=null&&!("").equals(unit)){
				hql+=" and unit like '"+"%"+unit+"%"+"'";
			}
			if(number!=null&&!("").equals(number)){
				hql+=" and number like '"+ "%"+number+"%"+"'";
			}
			
			if(hj!=null&&!(" ").equals(hj)){
				hql+=" and hj='"+hj+"'";
			}
				
			
			// 设置总记录数
			j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, null));// 设置总记录数
			hql += " order by createTime asc";
			int firstIndex = 0;
			firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
			List<TeeFiles> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查
			List<TeeFilesModel>	modelList=new ArrayList<TeeFilesModel>();
			if(list!=null&&list.size()>0){
				TeeFilesModel model=null;
				for (TeeFiles file : list) {
					model=parseToModel(file);
					modelList.add(model);
				}
			}
			j.setRows(modelList);// 设置返回的行	
		}
		return j;	
	}



	
    /**
     * 逻辑删除
     * @param request
     * @return
     */
	public TeeJson logicDel(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeFiles file=(TeeFiles) simpleDaoSupport.get(TeeFiles.class,sid);
			if(file!=null){
				file.setDelFlag(1);
				simpleDaoSupport.update(file);
				
				//写日志
				TeeOperationRecords  record=new TeeOperationRecords();
				record.setContent(loginUser.getUserName()+"删除了档案！");
				record.setFile(file);
				record.setOperTime(Calendar.getInstance());
				record.setOperUser(loginUser);
                simpleDaoSupport.save(record);
				
				
				
				json.setRtState(true);
				json.setRtMsg("删除成功！");
			}else{
				json.setRtState(false);
				json.setRtMsg("档案数据获取失败！");
			}
		}else{
			json.setRtState(false);
			json.setRtMsg("档案数据获取失败！");
		}
		return json;
	}



	/**
	 * 获取档案文件销毁列表
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getArchivedAndDeledFileList(
			TeeDataGridModel dm, HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql = "from TeeFiles f left outer join f.storeHouse h where f.opFlag=4 and f.delFlag=1 and (h.createUser.uuid=? or (exists (select 1 from h.userPriv userPriv where userPriv.uuid = ?)) or (exists (select 1 from h.deptPriv deptPriv where deptPriv.uuid = ?)) or (exists (select 1 from h.rolePriv rolePriv where rolePriv.uuid = ?)) ) ";
		List<Object> param=new ArrayList<Object>();
		param.add(loginUser.getUuid());
		param.add(loginUser.getUuid());
		param.add(loginUser.getDept().getUuid());
		param.add(loginUser.getUserRole().getUuid());
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by f.createTime asc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeFiles> list = simpleDaoSupport.pageFindByList("select f "+hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		List<TeeFilesModel>	modelList=new ArrayList<TeeFilesModel>();
		if(list!=null&&list.size()>0){
			TeeFilesModel model=null;
			for (TeeFiles file : list) {
				model=parseToModel(file);
				modelList.add(model);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}



	/**
	 * 销毁档案
	 * @param request
	 * @return
	 */
	public TeeJson destory(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String ids=TeeStringUtil.getString(request.getParameter("ids"));
		String[] idArray=null;
		TeeFiles file=null;
		if(ids!=null&&!("").equals(ids)){
			idArray=ids.split(",");
			if(idArray!=null&&idArray.length>0){
				for (String str : idArray) {
					 int sid=TeeStringUtil.getInteger(str,0);
					 if(sid!=0){
						 file=(TeeFiles) simpleDaoSupport.get(TeeFiles.class,sid);
					     if(file!=null){
					    	//删除该档案关联的档案附件信息表
							simpleDaoSupport.deleteOrUpdateByQuery("delete TeeFileAttch where file.sid=? ", new Object[]{file.getSid()});
					    	//删除该档案关联的日志信息
							simpleDaoSupport.deleteOrUpdateByQuery("delete TeeOperationRecords  where file.sid=? ", new Object[]{file.getSid()});
							simpleDaoSupport.deleteByObj(file);
					     }
					 }
				}
				json.setRtState(true);
				json.setRtMsg("销毁成功！");
			}	
		}
		return json;
	}



	/**
	 * 档案还原
	 * @param request
	 * @return
	 */
	public TeeJson restore(HttpServletRequest request) {
		TeeJson  json=new TeeJson();
		String ids=TeeStringUtil.getString(request.getParameter("ids"));
		String[] idArray=null;
		TeeFiles file=null;
		if(ids!=null&&!("").equals(ids)){
			idArray=ids.split(",");
			if(idArray!=null&&idArray.length>0){
				for (String str : idArray) {
					 int sid=TeeStringUtil.getInteger(str,0);
					 if(sid!=0){
						 file=(TeeFiles) simpleDaoSupport.get(TeeFiles.class,sid);
					     if(file!=null){
					    	file.setDelFlag(0);
					    	simpleDaoSupport.update(file);
					     }
					 }
				}
				json.setRtState(true);
				json.setRtMsg("还原成功！");
			}	
		}
		return json;
	}



	
	/**
	 * 查询所有已经归档的并且未被删除的档案
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson queryAllArchivedFiles(TeeDataGridModel dm,
			HttpServletRequest request) {
		String orgCode=TeeStringUtil.getString(request.getParameter("orgCode"));//组织机构代码
		String qzh=TeeStringUtil.getString(request.getParameter("qzh"));//全宗号
		String year=TeeStringUtil.getString(request.getParameter("year"));//年份
		String retentionPeriod=TeeStringUtil.getString(request.getParameter("retentionPeriod"));//保管期限
		String title=TeeStringUtil.getString(request.getParameter("title"));//标题
		String unit=TeeStringUtil.getString(request.getParameter("unit"));//单位
		String number=TeeStringUtil.getString(request.getParameter("number"));//文件编号
		String subject=TeeStringUtil.getString(request.getParameter("subject"));//主题词
		String mj=TeeStringUtil.getString(request.getParameter("mj"));//密级
		String hj=TeeStringUtil.getString(request.getParameter("hj"));//缓急
		String remark=TeeStringUtil.getString(request.getParameter("remark"));//备注
		
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql = " from TeeFiles where  opFlag=4  and delFlag=0 ";
		
		if(orgCode!=null&&!("").equals(orgCode)){
			hql+=" and orgCode like '"+"%"+orgCode+"%"+"'";
		}
		if(qzh!=null&&!("").equals(qzh)){
			hql+=" and qzh like '"+ "%"+qzh+"%"+"'";
		}
		if(year!=null&&!("").equals(year)){
			hql+=" and year like '"+ "%"+year+"%"+"'";
		}
		if(retentionPeriod!=null&&!("").equals(retentionPeriod)){
			hql+=" and retentionPeriod='"+retentionPeriod+"'";
		}
		if(title!=null&&!("").equals(title)){
			hql+=" and title like '"+"%"+title+"%"+"'";
		}
		if(unit!=null&&!("").equals(unit)){
			hql+=" and unit like '"+"%"+unit+"%"+"'";
		}
		if(number!=null&&!("").equals(number)){
			hql+=" and number like '"+ "%"+number+"%"+"'";
		}
		if(subject!=null&&!("").equals(subject)){
			hql+=" and subject like'" +"%"+subject+"%"+"'";
		}
		if(mj!=null&&!(" ").equals(mj)){
			hql+=" and mj='"+mj+"'";
		}
		if(hj!=null&&!(" ").equals(hj)){
			hql+=" and hj='"+hj+"'";
		}
		if(remark!=null&&!("").equals(remark)){
			hql+=" and remark like '"+ "%"+remark+"%"+"'";
		}
		
		
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, null));// 设置总记录数
		hql += " order by createTime asc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeFiles> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查
		List<TeeFilesModel>	modelList=new ArrayList<TeeFilesModel>();
		if(list!=null&&list.size()>0){
			TeeFilesModel model=null;
			for (TeeFiles file : list) {
				model=parseToModel(file);
				modelList.add(model);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}



	/**
	 * 流程归档
	 * @param request
	 * @return
	 */
	public TeeJson workFlowArchive(Map requestData1) {
		TeePerson loginUser=(TeePerson) requestData1.get("loginUser");
		
		TeeJson json=new TeeJson();
		int runId=TeeStringUtil.getInteger(requestData1.get("runId"),0);
		//卷库
		int storeId=TeeStringUtil.getInteger(requestData1.get("storeId"),0);
		
		TeeStoreHouse store=(TeeStoreHouse) simpleDaoSupport.get(TeeStoreHouse.class,storeId);
		if(runId!=0){
			TeeFlowRun  flowRun=(TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class,runId);
			if(flowRun!=null){
				
				//保存档案基本信息
				TeeFiles file=new TeeFiles();
				file.setCreateTime(Calendar.getInstance());
				file.setCreateUser(loginUser);
				file.setDelFlag(0);
				file.setOpFlag(4);//已归档
			    file.setTitle(flowRun.getRunName());
			    file.setViewFlag(0);
			    file.setViewTotal(0);
			    file.setRunId(runId);
			    file.setStoreHouse(store);
			    
			    //处理映射关系
			    if(flowRun.getFlowType()!=null){
			    	Map<String, String> flowRunData = helper.getFlowRunData(runId,
			    			flowRun.getFlowType().getSid());

			    	
			    	String archiveMapping=flowRun.getFlowType().getArchiveMapping();
			    	Map<String,String> fieldMapping=TeeJsonUtil.JsonStr2Map(archiveMapping);
			    	Set<String> keys = fieldMapping.keySet();
			    	
			    	TeeFormItem tmp = null;
			    	List<TeeFormItem> formItems = flowFormService
							.getLatestFormItemsByOriginForm(flowRun.getFlowType().getForm());
			    	// 查找对应的字段，并映射到预归档实体中
					for (String key : keys) {
						tmp = TeeFormItem.getItemByTitle(formItems, key);
						if (tmp != null) {
							if ("组织机构代码".equals(fieldMapping.get(key).toString())) {
								file.setOrgCode(flowRunData.get(tmp.getName()));
								
							} else if ("全宗号".equals(fieldMapping.get(key).toString())) {
								file.setQzh(flowRunData.get(tmp.getName()));
							} else if ("件号".equals(fieldMapping.get(key).toString())) {
								file.setJh(flowRunData.get(tmp.getName()));
							} else if ("年份".equals(fieldMapping.get(key).toString())) {
								file.setYear(flowRunData.get(tmp.getName()));
							} else if ("文件标题".equals(fieldMapping.get(key).toString())) {
								file.setTitle(flowRunData.get(tmp.getName()));
							} else if ("发/来文单位".equals(fieldMapping.get(key).toString())) {
								file.setUnit(flowRunData.get(tmp.getName()));
							} else if ("文件编号".equals(fieldMapping.get(key).toString())) {
								file.setNumber(flowRunData.get(tmp.getName()));
							} else if ("密级".equals(fieldMapping.get(key).toString())) {
								file.setMj(flowRunData.get(tmp.getName()));
							} else if ("缓急".equals(fieldMapping.get(key).toString())) {
								file.setHj(flowRunData.get(tmp.getName()));
							} else if ("主题词".equals(fieldMapping.get(key).toString())) {
								file.setSubject(flowRunData.get(tmp.getName()));
							} else if ("备注".equals(fieldMapping.get(key).toString())) {
								file.setRemark(flowRunData.get(tmp.getName()));
							}
						}
					}
			    	
			    	
			    	
			    	
			    	
			    }
			    
			    
			    simpleDaoSupport.save(file);
			    
			    //记录档案操作日志
				TeeOperationRecords  record=new  TeeOperationRecords();
				record.setFile(file);
				record.setOperTime(Calendar.getInstance());
				record.setOperUser(loginUser);
				record.setContent(loginUser.getUserName()+"创建了档案！");
				simpleDaoSupport.save(record);
			    
				
			    json.setRtState(true);
				json.setRtMsg("预归档成功!");	
			}else{
				json.setRtState(false);
				json.setRtMsg("流程数据获取失败！");		
			}	
		}else{//流程北部存在
			json.setRtState(false);
			json.setRtMsg("流程数据获取失败！");		
		}
		return json;
	}
	

	
}
