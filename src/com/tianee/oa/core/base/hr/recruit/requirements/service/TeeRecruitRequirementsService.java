package com.tianee.oa.core.base.hr.recruit.requirements.service;

import java.text.ParseException;
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
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.base.hr.recruit.bean.TeeHrFilter;
import com.tianee.oa.core.base.hr.recruit.bean.TeeHrPool;
import com.tianee.oa.core.base.hr.recruit.dao.TeeHrFilterDao;
import com.tianee.oa.core.base.hr.recruit.dao.TeeHrPoolDao;
import com.tianee.oa.core.base.hr.recruit.model.TeeHrPoolModel;
import com.tianee.oa.core.base.hr.recruit.plan.bean.TeeRecruitPlan;
import com.tianee.oa.core.base.hr.recruit.plan.dao.TeeRecruitPlanDao;
import com.tianee.oa.core.base.hr.recruit.requirements.bean.TeeRecruitRequirements;
import com.tianee.oa.core.base.hr.recruit.requirements.dao.TeeRecruitRequirementsDao;
import com.tianee.oa.core.base.hr.recruit.requirements.model.TeeRecruitRequirementsModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.general.dao.TeeSysParaDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeRecruitRequirementsService extends TeeBaseService {
	@Autowired
	private TeeRecruitRequirementsDao recruitRequirementsDao;
	@Autowired
    private TeeDeptDao deptDao;
	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	@Autowired
	private TeeHrPoolDao hrPoolDao;
	@Autowired
	private TeeSmsManager smsManager;
    @Autowired
    TeeSysParaDao sysParaDao;
    @Autowired
    TeeHrFilterDao hrFilterDao;
    
	
	@Autowired
	private TeeAttachmentService attachmentService;
	public TeeJson addOrUpdateService(HttpServletRequest request, TeeRecruitRequirementsModel model){
		List<TeeAttachment> listAttachments  = new ArrayList<TeeAttachment>();
		if(!TeeUtility.isNullorEmpty(model.getAttacheIds())){
			listAttachments = attachmentService.getAttachmentsByIds(model.getAttacheIds());
		}
		String isSelectPoolsIds  = TeeStringUtil.getString(request.getParameter("isSelectPoolsIds"));//从人才库选中的id字符串
		String addHrPoolsItem  = TeeStringUtil.getString(request.getParameter("addHrPoolsItem"));//新添加的人员数组
		int optType  = TeeStringUtil.getInteger(request.getParameter("optType") , 0);//操作类型 0-保存 2-需求确认
		
		TeeJson json = new TeeJson();
		try {
			TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			TeeRecruitRequirements recruitRequirements;
			if (model.getSid() > 0) {
				recruitRequirements = recruitRequirementsDao.get(model.getSid());
				if(recruitRequirements != null){
					recruitRequirements.setRequNo(model.getRequNo());
					recruitRequirements.setRequJob(model.getRequJob());
					recruitRequirements.setRequDept(model.getRequDeptStr());
					recruitRequirements.setRequNum(model.getRequNum());
					recruitRequirements.setRemark(model.getRemark());
					recruitRequirements.setRequRequires(model.getRequRequires());
					if (!TeeUtility.isNullorEmpty(model.getRequTimeStr())) {
						recruitRequirements.setRequTime(TeeUtility.parseDate(model.getRequTimeStr()));
					}
					for (int i = 0; i < listAttachments.size(); i++) {
						TeeAttachment attach = listAttachments.get(i);
						attach.setModelId(recruitRequirements.getSid()+"");
						simpleDaoSupport.update(attach);
					}
					if(optType == 2 || optType == 1){
						recruitRequirements.setRequStatus(optType);
					}
					recruitRequirementsDao.update(recruitRequirements);
				}
			}else{
				recruitRequirements = new TeeRecruitRequirements();
				BeanUtils.copyProperties(model, recruitRequirements);
				recruitRequirements.setCreateUser(person);
				recruitRequirements.setCreateTime(new Date());
				recruitRequirements.setCreateDept(person.getDept());
				recruitRequirements.setRequDept(model.getRequDeptStr());
				recruitRequirements.setRequRequires(model.getRequRequires());
				
				if (!TeeUtility.isNullorEmpty(model.getRequTimeStr())) {
					recruitRequirements.setRequTime(TeeUtility.parseDate(model.getRequTimeStr()));
				}
				if(optType == 2 || optType == 1){
					recruitRequirements.setRequStatus(optType);
				}
				recruitRequirementsDao.save(recruitRequirements);
				
				for (int i = 0; i < listAttachments.size(); i++) {
					TeeAttachment attach = listAttachments.get(i);
					attach.setModelId(recruitRequirements.getSid()+"");
					simpleDaoSupport.update(attach);
				}
			}
			
			//处理添加人员
			List<TeeHrPool> list = new ArrayList<TeeHrPool>();
			if(!TeeUtility.isNullorEmpty(addHrPoolsItem)){
				List<TeeHrPool>  hrPoolList= (List<TeeHrPool>)TeeJsonUtil.JsonStr2ObjectList4Protogen(addHrPoolsItem , TeeHrPool.class);
				for (int i = 0; i < hrPoolList.size(); i++) {
					TeeHrPool item = hrPoolList.get(i);
					//employeeBirth
					item.setCreateTime(new Date());
					hrPoolDao.save(item);
					list.add(item);
					
				}
			}
			//处理已经存在人才库
			if(!TeeUtility.isNullorEmpty(isSelectPoolsIds)){
				List<TeeHrPool> listTemp = hrPoolDao.selectByIds(isSelectPoolsIds);
				list.addAll(listTemp);
			}
			recruitRequirements.setHrPools(list);
			recruitRequirementsDao.update(recruitRequirements);	//再次更新
			
			//短信
			Map requestData = new HashMap();
			String paraValue = "";
			if(optType == 1){//人才推荐
				if(recruitRequirements.getCreateUser() != null){
					paraValue = recruitRequirements.getCreateUser().getUuid() + "";
				}
			}else{
				 paraValue = sysParaDao.getSysParaValue("RECRUIT_PERSONS");//人事专员
			}
			
			if(!TeeUtility.isNullorEmpty(paraValue)){
				//获取系统参数 --招聘专员
				String desc= "[" + person.getUserName() + "]" + " 提交招聘需求，编号：" + model.getRequNo() + ",人数："+ model.getRequNum() +"，请查看。";
				if(optType == 2){
					desc= "[" + person.getUserName() + "]" + " 您推荐的招聘需求已确认，编号：" + model.getRequNo() + ",人数："+ model.getRequNum() +"，请查看。";
				}else if(optType == 1){
					desc= "[" + person.getUserName() + "]" + " 您的需求已受理，并推荐相关人才信息，编号：" + model.getRequNo() + ",人数："+ model.getRequNum() +"，请查看。";
				}
				requestData.put("content", desc);
				requestData.put("moduleNo","052");
				requestData.put("userListIds",paraValue);
				requestData.put("remindUrl","/system/core/base/hr/recruit/requirements/index.jsp");
				smsManager.sendSms(requestData, person);
			}
			json.setRtState(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
     * 通用列表
     * @param dm
     * @return
     * @throws ParseException 
     */
    @Transactional(readOnly = true)
    public TeeEasyuiDataGridJson getRecruitList(TeeDataGridModel dm,HttpServletRequest request , TeeRecruitRequirementsModel model) throws ParseException {
        TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
        TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
        
        Map requestDatas = TeeServletUtility.getParamMap(request);
        String requNo = (String)requestDatas.get("requNo");
        String requJob = (String)requestDatas.get("requJob");
        String requNum = (String)requestDatas.get("requNum");
        String requDeptStr = (String)requestDatas.get("requDeptStr");
        String requTimeStrMin = (String)requestDatas.get("requTimeStrMin");
        String requTimeStrMax = (String)requestDatas.get("requTimeStrMax");
        String isQueryType = TeeStringUtil.getString(requestDatas.get("isQueryType") , "0");//是否是计划关联需求，1-是 0-不是
        String queryStr = " 1=1";
        String recruirementsPriv = "0";
    	//不是人事专员
    	String paraValue = "," +sysParaDao.getSysParaValue("RECRUIT_PERSONS") + ",";//人事专员
        if(!paraValue.contains("," + loginPerson.getUuid() +",")){
        	 if(!TeePersonService.checkIsSuperAdmin(loginPerson,"")){
        			queryStr = " require.createUser.uuid= " + loginPerson.getUuid();
             }
    	}else{
    		recruirementsPriv = "1";
    	}
    		
        String hql = "from TeeRecruitRequirements require where " + queryStr;
		List param = new ArrayList();
		if(!TeeUtility.isNullorEmpty(requNo)){
			hql+=" and require.requNo like ?";
			param.add("%"+requNo+"%");
		}
		if(!TeeUtility.isNullorEmpty(requJob)){
			hql+=" and require.requJob like ?";
			param.add("%"+requJob+"%");
		}
		if(!TeeUtility.isNullorEmpty(requNum)){
			hql+=" and require.requNum=?";
			param.add(requNum);
		}
		
		if(!TeeUtility.isNullorEmpty(requTimeStrMin)){
			hql += " and require.requTime >= ?";
			param.add(TeeDateUtil.parseDate("yyyy-MM-dd hh:mm:ss", requTimeStrMin+" 00:00:00"));
		}
		if(!TeeUtility.isNullorEmpty(requTimeStrMax)){
			hql += " and require.requTime <= ?";
			param.add(TeeDateUtil.parseDate("yyyy-MM-dd hh:mm:ss", requTimeStrMax+" 23:59:59"));
		}
		if(isQueryType.equals("1")){//不需要已和计划关联的需求
			hql+=" and require.plan.sid is null";
		}
//        j.setTotal(recruitRequirementsDao.getQueryCount(loginPerson ,model));// 设置总记录数
		j.setTotal(recruitRequirementsDao.countByList("select count(*) "+hql, param));// 设置总记录数
        
		hql+=" order by require.createTime desc";
        
        int firstIndex = 0;
        firstIndex = (dm.getPage()-1) * dm.getRows() ;//获取开始索引位置
//        List<TeeRecruitRequirements> list = recruitRequirementsDao.getMeetPageFind(firstIndex, dm.getRows(), dm, model);// 查
        List<TeeRecruitRequirements> list = recruitRequirementsDao.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);// 查
        List<TeeRecruitRequirementsModel> modelList = new ArrayList<TeeRecruitRequirementsModel>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
            	TeeRecruitRequirementsModel modeltemp = parseModel(list.get(i) , true);
            	modeltemp.setRecruirementsPriv(recruirementsPriv);
                modelList.add(modeltemp);
            }
        }
        j.setRows(modelList);// 设置返回的行
        return j;
    }
	
    /**
     * 封装对象
     * @date 2014-3-17
     * @author 
     * @param obj
     * @return
     */
    public TeeRecruitRequirementsModel parseModel(TeeRecruitRequirements obj , boolean isSimple) {
    	TeeRecruitRequirementsModel model = new TeeRecruitRequirementsModel();
        if (obj == null) {
            return model;
        }
        BeanUtils.copyProperties(obj, model);
        if (!TeeUtility.isNullorEmpty(obj.getCreateTime())) {
            model.setCreateTimeStr(TeeUtility.getDateStrByFormat(obj.getCreateTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
        }
        if (!TeeUtility.isNullorEmpty(obj.getRequTime())) {
            model.setRequTimeStr(TeeUtility.getDateStrByFormat(obj.getRequTime(), new SimpleDateFormat("yyyy-MM-dd")));
        }else{
        	model.setRequTimeStr("");
        }
        
        if (obj.getCreateUser() != null) {
            model.setCreateUserId(obj.getCreateUser().getUuid());
            model.setCreateUserName(obj.getCreateUser().getUserName());
        }
        if(!TeeUtility.isNullorEmpty(obj.getRequDept())){
        	String requDeptName = "";
        	List<TeeDepartment> list = deptDao.getDeptListByUuids(obj.getRequDept());
        	if(list != null && list.size()>0){
        		for(TeeDepartment deptObj:list){
        			if(!TeeUtility.isNullorEmpty(requDeptName)){
        				requDeptName +=",";
        			}
        			requDeptName += deptObj.getDeptName();
        		}
        	}
        	model.setRequDeptStrName(requDeptName);
        	model.setRequDeptStr(obj.getRequDept());
        }else{
        	model.setRequDeptStrName("");
        }
        List<TeeAttachmentModel> attacheModels = new ArrayList<TeeAttachmentModel>();
        String attacheIds = "";
        List<TeeHrPoolModel> hpMapList = new ArrayList<TeeHrPoolModel>();
        if(!isSimple){
        	  List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.RECRUIT_REQUIREMENTS, obj.getSid()+"");
        	  for(TeeAttachment attach:attaches){
      			TeeAttachmentModel m = new TeeAttachmentModel();
      			BeanUtils.copyProperties(attach, m);
      			m.setUserId(attach.getUser().getUuid()+"");
      			m.setUserName(attach.getUser().getUserName());
      			m.setPriv(1+2+4);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
      			attacheModels.add(m);
      			attacheIds = attacheIds + attach.getSid() + ",";
      		}
              if(attacheIds.endsWith(",")){
              	attacheIds = attacheIds.substring(0, attacheIds.length() - 1);
              }  
              ///关联人才库
              List<TeeHrPool> hpList =  obj.getHrPools();//
              if(hpList != null && hpList.size() > 0){
              	for (int i = 0; i < hpList.size(); i++) {
              		TeeHrPool hp = hpList.get(i);
              		TeeHrPoolModel hrPoolModel = new TeeHrPoolModel();
              		hrPoolModel.setSid( hp.getSid());
              		hrPoolModel.setEmployeeEmail( hp.getEmployeeEmail());
              		hrPoolModel.setEmployeeName(hp.getEmployeeName());
              		hrPoolModel.setEmployeeMajor( hp.getEmployeeMajor());
              		hrPoolModel.setEmployeePhone(hp.getEmployeePhone());
              		hrPoolModel.setEmployeeHighestSchool(hp.getEmployeeHighestSchool());
              		hrPoolModel.setExpectedSalary( hp.getExpectedSalary());
              		Date date = hp.getEmployeeBirth();
              		String dateStr = "";
              		if(date != null){
              			dateStr = TeeDateUtil.format(date, "yyyy-MM-dd");
              		}
              		hrPoolModel.setEmployeeBirthStr( dateStr);
              		hpMapList.add(hrPoolModel);
      			}
              }
        }
        
        model.setAttacheModels(attacheModels);
        model.setAttacheIds("");
        model.setHrPoolsModel(hpMapList);
        return model;
    }
	
    /**
     * 根据sid查看详情
     * @date 2014-3-8
     * @author 
     * @param request
     * @param model
     * @return
     */
    public TeeJson getInfoByIdService(HttpServletRequest request, TeeRecruitRequirementsModel model) {
        TeeJson json = new TeeJson();
        if (model.getSid() > 0) {
        	TeeRecruitRequirements out = recruitRequirementsDao.get(model.getSid());
            if (out != null) {
                model = parseModel(out , false);
                json.setRtData(model);
                json.setRtState(true);
                json.setRtMsg("查询成功!");
                return json;
            }
        }
        json.setRtState(false);
        return json;
    }
	
    /**
     * 删除信息
     * @date 2014年5月27日
     * @author 
     * @param sids
     * @return
     */
    public TeeJson deleteObjByIdService(String sids){
    	TeeJson json = new TeeJson();
    	
    	recruitRequirementsDao.delByIds(sids);
    	json.setRtState(true);
        json.setRtMsg("删除成功!");
    	return json;
    }
    
    /**
     * @date 2014年8月10日
     * syl
     * @return
     */
    public TeeJson batchSendInterviewInfo(HttpServletRequest request){
    	TeeJson json = new TeeJson();
    	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
    	String batchSendInterviewInfo = request.getParameter("batchSendInterviewInfo");//添加面试信息
    	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//需求Id
    	int planId = TeeStringUtil.getInteger(request.getParameter("planId"), 0);//计划Id
    	TeeRecruitRequirements requirement = recruitRequirementsDao.get(sid);
    	if(!TeeUtility.isNullorEmpty(batchSendInterviewInfo) && requirement != null){
        	List<Map<String, String>> list = TeeJsonUtil.JsonStr2MapList(batchSendInterviewInfo);
        	
        	for (int i = 0; i < list.size(); i++) {
				Map<String , String> map = list.get(i);
				int poolSid = TeeStringUtil.getInteger(map.get("sid") , 0);
				String interviewTime = map.get("interviewTime");//发送时间
				String employeeMajorDesc = map.get("employeeMajorDesc");//专业
				String employeePhone = map.get("employeePhone");//联系电话
				String positionDesc = map.get("positionDesc");//岗位
				TeeHrPool hrPool = hrPoolDao.load(poolSid);
				if(hrPool != null && !TeeUtility.isNullorEmpty(interviewTime)){
					TeeHrFilter filter = new TeeHrFilter();
					filter.setCreateTime(new Date());
					filter.setHrPool(hrPool);
					filter.setEmployeeMajor(employeeMajorDesc);
					filter.setPosition(positionDesc);
					filter.setEmployeePhone(employeePhone);
					filter.setNextTransactor(requirement.getCreateUser());
					filter.setPlan(requirement.getPlan());
					try {
						filter.setNextDatetime(TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", interviewTime));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					filter.setPlan(requirement.getPlan());
					filter.setFilterState("0");
					filter.setSendPerson(person);
					hrFilterDao.save(filter);
				}
			}
    	} 
        json.setRtState(true);
    	return json ;
    }
}
