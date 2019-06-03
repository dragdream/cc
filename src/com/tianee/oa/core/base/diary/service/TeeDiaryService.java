package com.tianee.oa.core.base.diary.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.base.diary.bean.TeeDiary;
import com.tianee.oa.core.base.diary.bean.TeeDiaryReply;
import com.tianee.oa.core.base.diary.dao.TeeDiaryDao;
import com.tianee.oa.core.base.diary.model.TeeDiaryModel;
import com.tianee.oa.core.base.diary.model.TeeDiaryReplyModel;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.core.general.dao.TeeSysParaDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeDiaryService extends TeeBaseService{
	
	@Autowired
	TeeDiaryDao diaryDao;
	
	@Autowired
	TeeSysParaDao sysParaDao;
	
	@Autowired
	TeeAttachmentService attachmentService;
	
	public void getShareRanges(TeePerson loginPerson){
		//String hql = "from TeeDiary diary where exists (select 1 from diary.shareRanges ranges where ranges.uuid="+loginPerson.getUuid()+")";
	}
	
	public String diaryHtml() throws Exception{
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	   String returnHtml = "<div><table class='diaryTable' width='100%'>";
	   int dd = getMondayPlus();
	   Calendar cd = Calendar.getInstance();  
	   cd.add(Calendar.DAY_OF_WEEK, dd+6);
	   returnHtml +="<tr><td class='weekTd'><div class='weekItem'><div align='center' class='d1'>星期"+getWeek(cd.get(Calendar.DAY_OF_WEEK))+"</div>" +
	   				"<div align='center' class='d2'>"+formatter.format(cd.getTime())+"</div></div></td>" +
	   				"<td class='TableData tdClass' onDblClick='addDiary(\""+formatter.format(cd.getTime()).toString()+"\");'>"+getDiaryByTime(formatter.format(cd.getTime()),"","")+"</td></tr>"; 
	   for(int i=1;i<7;i++){
		   cd.add(Calendar.DAY_OF_YEAR, -1);
		   returnHtml +="<tr><td class='weekTd'><div class='weekItem'><div align='center' class='d1'>星期"+getWeek(cd.get(Calendar.DAY_OF_WEEK))+"</div>" +
		   				"<div align='center' class='d2'>"+formatter.format(cd.getTime())+"</div></div></td>" +
		   				"<td class='TableData tdClass' onDblClick='addDiary(\""+formatter.format(cd.getTime()).toString()+"\")'>"+getDiaryByTime(formatter.format(cd.getTime()),"","")+"</td></tr>"; 
	   }
	   returnHtml+="</table></div>";
	   return returnHtml;
	}
	  
	
	 public  int getMondayPlus() {  
		    Calendar cd = Calendar.getInstance();  
		    // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......  
		    int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);  
		    if (dayOfWeek == 1) {  
		        return -6;  
		    } else {  
		        return 2 - dayOfWeek;  
		    }  
		}
	 
	 
	 public String getWeek(int week){
		 String num="";
		 switch (week){
		 case 1:num="星期日";break;
		 case 2:num="星期一";break;
		 case 3:num="星期二";break;
		 case 4:num="星期三";break;
		 case 5:num="星期四";break;
		 case 6:num="星期五";break;
		 case 7:num="星期六";break;
		 }
		 return num;
	 }
	 
	 public Map returnStartAndEndTime(){
		 Map map = new HashMap();
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		 String startTime="";
		 String endTime="";
//		 int dd = getMondayPlus();
//		 Calendar cd = Calendar.getInstance();  
//		 cd.add(Calendar.DAY_OF_WEEK, dd);
//		 startTime=formatter.format(cd.getTime());//星期一对应的日期
//		 cd.add(Calendar.DAY_OF_YEAR, 6);
//		 endTime = formatter.format(cd.getTime());
		 
		 Calendar c = Calendar.getInstance();
		 map.put("endTime", formatter.format(c.getTime()));
		 
		 //不等于周一的话
		 for(;c.get(Calendar.DAY_OF_WEEK)!=2;c.add(Calendar.DAY_OF_YEAR, -1));
		 map.put("startTime", formatter.format(c.getTime()));
		 
		 return map;
	 }

	public void addOrUpdateDiary(TeeDiaryModel model , TeePerson person) throws ParseException {
		TeeSysLog sysLog = TeeSysLog.newInstance();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
		Calendar cl = Calendar.getInstance();
		String time = formatter2.format(cl.getTime());
		String writeTimeDesc = model.getWriteTimeDesc()+" "+time;
		TeeDiary diary = new TeeDiary();
		
		if(model.getSid()>0){
			diary = (TeeDiary) simpleDaoSupport.get(TeeDiary.class, model.getSid());
		}
		
		BeanUtils.copyProperties(model, diary);	
		cl.setTime(formatter.parse(writeTimeDesc));
		diary.setWriteTime(cl);
		diary.setCreateTime(Calendar.getInstance());
		
		diary.getShareRanges().clear();
		if(diary.getType()==2){
			//设置共享范围
			int ranges[] = TeeStringUtil.parseIntegerArray(model.getShareRangesIds());
			
			for(int uuid:ranges){
				if(uuid==0){
					continue;
				}
				diary.getShareRanges().add((TeePerson)simpleDaoSupport.get(TeePerson.class, uuid));
			}
		}
		
		if(model.getSid()>0){
			diaryDao.update(diary);
			sysLog.setType("018B");
			sysLog.setRemark("更新日志信息 ["+diary.getTitle()+"]");
		}else{
			diary.setCreateUser(person);
			diaryDao.save(diary);
			
			sysLog.setType("018A");
			sysLog.setRemark("添加日志信息 ["+diary.getTitle()+"]");
		}
		
		List attaches = model.getAttacheModels();
		for(int i=0;i<attaches.size();i++){
			TeeAttachment attach = (TeeAttachment) attaches.get(i);
			attach.setModelId(String.valueOf(diary.getSid()));
			attachmentService.updateAttachment(attach);
		}
		
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		
		model.setSid(diary.getSid());
	}
	
	public String getDiaryByTime(String time,String title,String type) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String startTime=time+" 00:00:00";
		String endTime=time+ " 23:59:59";
		Calendar cl=Calendar.getInstance();
		cl.setTime(formatter.parse(startTime));
		Calendar c2=Calendar.getInstance();
		c2.setTime(formatter.parse(endTime));
		String returnStr = "";
		List<TeeDiary> diaryList = new ArrayList<TeeDiary>();
		String hql ="from TeeDiary diary where diary.createTime > ? and diary.createTime<?";
		String query = "";
		
		List values = new ArrayList();
		values.add(cl);
		values.add(c2);
		if(!TeeUtility.isNullorEmpty(title)){
			query+=" and diary.title like ?";
			values.add("%"+title+"%");
		}
		if(!TeeUtility.isNullorEmpty(type) && !type.equals("0")){
			query+=" and diary.type = ?";
			values.add(Integer.parseInt(type));
		}
		hql+=query;
		diaryList = diaryDao.executeQueryByList(hql,values);
		for(TeeDiary diary:diaryList){
			returnStr+="<div sid='"+diary.getSid()+"'><a href='javascript:void(0)'>"+diary.getTitle()+"</a></div>";
		}
		return returnStr;
	}

	public TeeDiaryModel getDiaryById(String sid) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeDiaryModel model = new TeeDiaryModel();
		TeeDiary diary = new TeeDiary();
		diary = diaryDao.get(Integer.parseInt(sid));
		Calendar cl = diary.getCreateTime();
		String createTimeDesc=formatter.format(cl.getTime());
		String writeTimeDesc = formatter.format(diary.getWriteTime().getTime());
//		System.out.println(writeTimeDesc);
		int type=diary.getType();
		String typeDesc="个人日志";
		if(type==2){
			typeDesc="工作日志";
		}
		BeanUtils.copyProperties(diary,model);
		//处理附件
		List<TeeAttachment> attaches = attachmentService.getAttaches(TeeAttachmentModelKeys.diary, String.valueOf(diary.getSid()));
		List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
		for(TeeAttachment attach:attaches){
			TeeAttachmentModel m = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, m);
			m.setUserId(attach.getUser().getUuid()+"");
			m.setUserName(attach.getUser().getUserName());
			m.setPriv(1+2+4+8+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
			attachmodels.add(m);
		}
		model.setReplyModels(getDiaryReplies(Integer.parseInt(sid)));
		
		//获取共享范围
		String ids = "";
		String names = "";
		Set<TeePerson> ranges = diary.getShareRanges();
		for(TeePerson p:ranges){
			ids+=p.getUuid()+",";
			names+=p.getUserName()+",";
		}
		
		model.setShareRangesIds(ids);
		model.setShareRangesNames(names);
		
		model.setAttacheModels(attachmodels);
		model.setCreateTimeDesc(createTimeDesc);
		model.setWriteTimeDesc(writeTimeDesc);
		model.setTypeDesc(typeDesc);
		model.setCreateUserName(diary.getCreateUser().getUserName());
		model.setCreateUserId(diary.getCreateUser().getUuid());
		return model; 
	}

	@TeeLoggingAnt(template="删除日志信息 [{#.title}]",type="018C")
	public TeeDiary delDiaryService(String sid) {
		TeeDiary diary = diaryDao.get(Integer.parseInt(sid));
		List<TeeAttachment> attaches = attachmentService.getAttaches(TeeAttachmentModelKeys.diary, String.valueOf(diary.getSid()));
		for(TeeAttachment atta:attaches){
			attachmentService.deleteAttach(atta);
		}
		//删除留言回复
		simpleDaoSupport.executeUpdate("delete from TeeDiaryReply where diary.sid=?", new Object[]{Integer.parseInt(sid)});
		diaryDao.deleteByObj(diary);
		return diary;
	}

	/**
	 * 日志通用查询接口（我的日志）
	 * @param requestDatas
	 * @return Map --> {dateModels:[{week:'星期四',date:'2013-12-31'}],dataModels:[{title:'xxx',content:'xx',attaches:[],date:'2013-12-31',replies:[{crTime:'',replyUserName:'',replyUserId:'',content:''}]}]}
	 * @throws Exception
	 */
	public Map doSearch(Map requestDatas) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("MM月dd日");
		
		String startTime = TeeStringUtil.getString(requestDatas.get("startTime"));
		String endTime = TeeStringUtil.getString(requestDatas.get("endTime"));
		String title = TeeStringUtil.getString(requestDatas.get("title"));
		String type = TeeStringUtil.getString(requestDatas.get("type"));
		
		TeePerson loginUser = (TeePerson) requestDatas.get(TeeConst.LOGIN_USER);
		
		//返回对象数据
		Map returnedData = new HashMap();
		//渲染日期模型
		List dateModels = new ArrayList();
		Calendar start = Calendar.getInstance();
		start.setTime(format1.parse(startTime));
		Calendar end = Calendar.getInstance();
		end.setTime(format1.parse(endTime));
		
		for(;start.before(end) || start.equals(end);start.add(Calendar.DATE, 1)){
			Map dateModel = new HashMap();
			dateModel.put("week", getWeek(start.get(Calendar.DAY_OF_WEEK)));
			dateModel.put("date", format1.format(start.getTime()));
			dateModel.put("dateDesc", format2.format(start.getTime()));
			dateModels.add(dateModel);
		}
		
		String hql="from TeeDiary diary where 1=1";
		String query = "";
		List values = new ArrayList();
		if(!TeeUtility.isNullorEmpty(title)){
			query+=" and diary.title like ?";
			values.add("%"+title+"%");
		}
		if(!TeeUtility.isNullorEmpty(type) && !type.equals("0")){
			query+=" and diary.type = ?";
			values.add(Integer.parseInt(type));
		}
		if(!TeeUtility.isNullorEmpty(startTime) && !TeeUtility.isNullorEmpty(endTime)){
			Calendar cs = Calendar.getInstance();
			Calendar es = Calendar.getInstance();
			es.setTime(format.parse(endTime+" 23:59"));
			cs.setTime(format.parse(startTime+" 00:00"));
			query+=" and diary.writeTime >= ? and diary.writeTime <=?";
			values.add(cs);
			values.add(es);
		}
		hql+=query+" and diary.createUser.uuid="+loginUser.getUuid()+" order by diary.createTime desc";
		List<TeeDiary> diaryList = new ArrayList<TeeDiary>();
		diaryList = diaryDao.executeQueryByList(hql, values);
		
		List dataModels = new ArrayList();
		for(TeeDiary diary:diaryList){
			Map dataModel = new HashMap();
			dataModel.put("title", diary.getTitle());
			dataModel.put("content", diary.getContent());
			dataModel.put("sid", diary.getSid());
			dataModel.put("createUserName", diary.getCreateUser().getUserName());
			dataModel.put("createTimeDesc", format.format(diary.getCreateTime().getTime()));
			dataModel.put("writeTimeDesc", format1.format(diary.getWriteTime().getTime()));
			
			//处理附件
			List<TeeAttachment> attaches = attachmentService.getAttaches(TeeAttachmentModelKeys.diary, String.valueOf(diary.getSid()));
			List models = new ArrayList();
			for(TeeAttachment atta:attaches){
				TeeAttachmentModel m = new TeeAttachmentModel();
				BeanUtils.copyProperties(atta, m);
				if(isLocked(format.format(diary.getWriteTime().getTime()))){
					m.setPriv(1+2);
				}else{
					m.setPriv(1+2+4+8+16+32);
				}
				models.add(m);
			}
			dataModel.put("attacheModels", models);
			
			//处理回复
			dataModel.put("replyModels", getDiaryReplies(diary.getSid()));
			
			dataModels.add(dataModel);
		}
		
		returnedData.put("dateModels", dateModels);
		returnedData.put("dataModels", dataModels);
		
		return returnedData;
	}
	
	
	/**
	 * 日志通用查询接口（共享日志）
	 * @param requestDatas
	 * @return Map --> {dateModels:[{week:'星期四',date:'2013-12-31'}],dataModels:[{title:'xxx',content:'xx',attaches:[],date:'2013-12-31'}]}
	 * @throws Exception
	 */
	public Map doSearchShare(Map requestDatas) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("MM月dd日");
		
		String startTime = TeeStringUtil.getString(requestDatas.get("startTime"));
		String endTime = TeeStringUtil.getString(requestDatas.get("endTime"));
		String title = TeeStringUtil.getString(requestDatas.get("title"));
		String type = TeeStringUtil.getString(requestDatas.get("type"));
		
		TeePerson loginUser = (TeePerson) requestDatas.get(TeeConst.LOGIN_USER);
		
		//返回对象数据
		Map returnedData = new HashMap();
		//渲染日期模型
		List dateModels = new ArrayList();
		Calendar start = Calendar.getInstance();
		start.setTime(format1.parse(startTime));
		Calendar end = Calendar.getInstance();
		end.setTime(format1.parse(endTime));
		
		for(;start.before(end) || start.equals(end);start.add(Calendar.DATE, 1)){
			Map dateModel = new HashMap();
			dateModel.put("week", getWeek(start.get(Calendar.DAY_OF_WEEK)));
			dateModel.put("date", format1.format(start.getTime()));
			dateModel.put("dateDesc", format2.format(start.getTime()));
			dateModels.add(dateModel);
		}
		
		String hql="from TeeDiary diary where 1=1";
		String query = "";
		List values = new ArrayList();
		if(!TeeUtility.isNullorEmpty(title)){
			query+=" and diary.title like ?";
			values.add("%"+title+"%");
		}
		if(!TeeUtility.isNullorEmpty(type) && !type.equals("0")){
			query+=" and diary.type = ?";
			values.add(Integer.parseInt(type));
		}
		if(!TeeUtility.isNullorEmpty(startTime) && !TeeUtility.isNullorEmpty(endTime)){
			Calendar cs = Calendar.getInstance();
			Calendar es = Calendar.getInstance();
			es.setTime(format.parse(endTime+" 23:59"));
			cs.setTime(format.parse(startTime+" 00:00"));
			query+=" and diary.writeTime > ? and diary.writeTime <?";
			values.add(cs);
			values.add(es);
		}
		hql+=query+" and exists (select 1 from diary.shareRanges shareRanges where shareRanges.uuid = "+loginUser.getUuid()+") order by diary.createTime desc";
		List<TeeDiary> diaryList = new ArrayList<TeeDiary>();
		diaryList = diaryDao.executeQueryByList(hql, values);
		
		List dataModels = new ArrayList();
		for(TeeDiary diary:diaryList){
			Map dataModel = new HashMap();
			dataModel.put("title", diary.getTitle());
			dataModel.put("content", diary.getContent());
			dataModel.put("sid", diary.getSid());
			dataModel.put("createUserName", diary.getCreateUser().getUserName());
			dataModel.put("createTimeDesc", format.format(diary.getCreateTime().getTime()));
			dataModel.put("writeTimeDesc", format1.format(diary.getWriteTime().getTime()));
			
			//处理附件
			List<TeeAttachment> attaches = attachmentService.getAttaches(TeeAttachmentModelKeys.diary, String.valueOf(diary.getSid()));
			List models = new ArrayList();
			for(TeeAttachment atta:attaches){
				TeeAttachmentModel m = new TeeAttachmentModel();
				BeanUtils.copyProperties(atta, m);
				m.setPriv(1+2);
				models.add(m);
			}
			dataModel.put("attacheModels", models);
			
			//处理回复
			dataModel.put("replyModels", getDiaryReplies(diary.getSid()));
			
			dataModels.add(dataModel);
		}
		
		returnedData.put("dateModels", dateModels);
		returnedData.put("dataModels", dataModels);
		
		return returnedData;
	}
	
	
	/**
	 * 日志通用查询接口（下属日志）
	 * @param requestDatas
	 * @return Map --> {dateModels:[{week:'星期四',date:'2013-12-31'}],dataModels:[{title:'xxx',content:'xx',attaches:[],date:'2013-12-31'}]}
	 * @throws Exception
	 */
	public Map doSearchBranch(Map requestDatas) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("MM月dd日");
		
		String startTime = TeeStringUtil.getString(requestDatas.get("startTime"));
		String endTime = TeeStringUtil.getString(requestDatas.get("endTime"));
		String title = TeeStringUtil.getString(requestDatas.get("title"));
		String type = TeeStringUtil.getString(requestDatas.get("type"));
		int userId =  TeeStringUtil.getInteger(requestDatas.get("userId"), 0);
		
		TeePerson loginUser = (TeePerson) requestDatas.get(TeeConst.LOGIN_USER);
		
		//返回对象数据
		Map returnedData = new HashMap();
		//渲染日期模型
		List dateModels = new ArrayList();
		Calendar start = Calendar.getInstance();
		start.setTime(format1.parse(startTime));
		Calendar end = Calendar.getInstance();
		end.setTime(format1.parse(endTime));
		
		for(;start.before(end) || start.equals(end);start.add(Calendar.DATE, 1)){
			Map dateModel = new HashMap();
			dateModel.put("week", getWeek(start.get(Calendar.DAY_OF_WEEK)));
			dateModel.put("date", format1.format(start.getTime()));
			dateModel.put("dateDesc", format2.format(start.getTime()));
			dateModels.add(dateModel);
		}
		
		String hql="from TeeDiary diary where 1=1 and diary.type=2 ";
		String query = "";
		List values = new ArrayList();
		if(!TeeUtility.isNullorEmpty(title)){
			query+=" and diary.title like ?";
			values.add("%"+title+"%");
		}
		//只查询工作日志，不查个人日志
	/*	if(!TeeUtility.isNullorEmpty(type) && !type.equals("0")){
			query+=" and diary.type = ?";
			values.add(Integer.parseInt(type));
		}*/
		if(!TeeUtility.isNullorEmpty(startTime) && !TeeUtility.isNullorEmpty(endTime)){
			Calendar cs = Calendar.getInstance();
			Calendar es = Calendar.getInstance();
			es.setTime(format.parse(endTime+" 23:59"));
			cs.setTime(format.parse(startTime+" 00:00"));
			query+=" and diary.writeTime > ? and diary.writeTime <?";
			values.add(cs);
			values.add(es);
		}
		hql+=query+" and diary.createUser.uuid="+userId+" order by diary.createTime desc";
		List<TeeDiary> diaryList = new ArrayList<TeeDiary>();
		diaryList = diaryDao.executeQueryByList(hql, values);
		
		List dataModels = new ArrayList();
		for(TeeDiary diary:diaryList){
			Map dataModel = new HashMap();
			dataModel.put("title", diary.getTitle());
			dataModel.put("content", diary.getContent());
			dataModel.put("sid", diary.getSid());
			dataModel.put("createUserName", diary.getCreateUser().getUserName());
			dataModel.put("createTimeDesc", format.format(diary.getCreateTime().getTime()));
			dataModel.put("writeTimeDesc", format1.format(diary.getWriteTime().getTime()));
			
			//处理附件
			List<TeeAttachment> attaches = attachmentService.getAttaches(TeeAttachmentModelKeys.diary, String.valueOf(diary.getSid()));
			List models = new ArrayList();
			for(TeeAttachment atta:attaches){
				TeeAttachmentModel m = new TeeAttachmentModel();
				BeanUtils.copyProperties(atta, m);
				m.setPriv(1+2);
				models.add(m);
			}
			dataModel.put("attacheModels", models);
			
			//处理回复
			dataModel.put("replyModels", getDiaryReplies(diary.getSid()));
			
			dataModels.add(dataModel);
		}
		
		returnedData.put("dateModels", dateModels);
		returnedData.put("dataModels", dataModels);
		
		return returnedData;
	}

	/**
	 * 保存日志的配置参数
	 * @param requestDatas
	 */
	public void saveDiarySetting(Map requestDatas){
		String startTimeDesc=TeeStringUtil.getString(requestDatas.get("startTimeDesc"),"");
		String endTimeDesc=TeeStringUtil.getString(requestDatas.get("endTimeDesc"),"");
		String beforeDays=TeeStringUtil.getString(requestDatas.get("beforeDays"),"");
		TeeSysPara sysPara1 = new TeeSysPara();
		if(sysParaDao.getSysPara("DIARY_START_TIME")==null){
			sysPara1.setParaName("DIARY_START_TIME");
			sysPara1.setParaValue(startTimeDesc);
			sysParaDao.save(sysPara1);
		}else{
			sysPara1=sysParaDao.getSysPara("DIARY_START_TIME");
			sysPara1.setParaValue(startTimeDesc);
			sysParaDao.update(sysPara1);
		}
		TeeSysPara sysPara2 = new TeeSysPara();
		if(sysParaDao.getSysPara("DIARY_END_TIME")==null){
			sysPara2.setParaName("DIARY_END_TIME");
			sysPara2.setParaValue(endTimeDesc);
			sysParaDao.save(sysPara2);
		}else{
			sysPara2=sysParaDao.getSysPara("DIARY_END_TIME");
			sysPara2.setParaValue(endTimeDesc);
			sysParaDao.update(sysPara2);
		}
		TeeSysPara sysPara = new TeeSysPara();
		if(sysParaDao.getSysPara("DIARY_BEFOREDAYS")==null){
			sysPara.setParaName("DIARY_BEFOREDAYS");
			sysPara.setParaValue(beforeDays);
			sysParaDao.save(sysPara);
		}else{
			sysPara=sysParaDao.getSysPara("DIARY_BEFOREDAYS");
			sysPara.setParaValue(beforeDays);
			sysParaDao.update(sysPara);
		}
	
	}
	
	/**
	 * 获取日志的配置参数
	 * @return
	 */
	public Map getDiarySetting() {
		Map returnedData = new HashMap();
		String startTimeDesc="";
		String endTimeDesc="";
		String beforeDays="";
		if(sysParaDao.getSysPara("DIARY_START_TIME")!=null){
			 startTimeDesc=TeeStringUtil.getString(sysParaDao.getSysPara("DIARY_START_TIME").getParaValue(),"");
		}
		if(sysParaDao.getSysPara("DIARY_END_TIME")!=null){
			 endTimeDesc=TeeStringUtil.getString(sysParaDao.getSysPara("DIARY_END_TIME").getParaValue(),"");
		}
		if(sysParaDao.getSysPara("DIARY_BEFOREDAYS")!=null){
			 beforeDays=TeeStringUtil.getString(sysParaDao.getSysPara("DIARY_BEFOREDAYS").getParaValue(),"");
		}
		returnedData.put("startTimeDesc", startTimeDesc);
		returnedData.put("endTimeDesc", endTimeDesc);
		returnedData.put("beforeDays", beforeDays);
		return returnedData;
	}

	public boolean isLocked(String addTime) throws Exception {
		String startTimeDesc="";
		String endTimeDesc="";
		int beforeDays=0;
		int realDiffDays=0;
		boolean flag=false;
		Calendar cl = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
		if(sysParaDao.getSysPara("DIARY_START_TIME")!=null && sysParaDao.getSysPara("DIARY_END_TIME")!=null){
			 startTimeDesc=TeeStringUtil.getString(sysParaDao.getSysPara("DIARY_START_TIME").getParaValue(),"");
			 endTimeDesc=TeeStringUtil.getString(sysParaDao.getSysPara("DIARY_END_TIME").getParaValue(),"");
			 Calendar sl = Calendar.getInstance();
			 if(!TeeUtility.isNullorEmpty(startTimeDesc)){
				 sl.setTime(formatter.parse(startTimeDesc));
			 }
			 Calendar el= Calendar.getInstance();
			 if(!TeeUtility.isNullorEmpty(endTimeDesc)){
				 el.setTime(formatter.parse(endTimeDesc));
			 }
			 Calendar al = Calendar.getInstance();
			 al.setTime(formatter.parse(addTime.substring(0, 10)));
			 if(al.after(sl) && al.before(el) || (al.compareTo(sl)==0 || al.compareTo(el)==0)){
				 flag=true;
			 }
		}
		if(sysParaDao.getSysPara("DIARY_BEFOREDAYS")!=null){
			 beforeDays=TeeStringUtil.getInteger(sysParaDao.getSysPara("DIARY_BEFOREDAYS").getParaValue(),0);
			 Calendar al = Calendar.getInstance();
			 SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm");
			 al.setTime(format.parse(addTime));
			 realDiffDays = Integer.parseInt(String.valueOf((cl.getTimeInMillis()-al.getTimeInMillis())/(1000*3600*24)));
			 if(beforeDays>0 && realDiffDays>=beforeDays ){
				 flag=true;
			 }
			 
		}
		return flag;
	}
	
	public void replyEntityToModel(TeeDiaryReply reply,TeeDiaryReplyModel model){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		model.setContent(reply.getContent());
		model.setCreateTimeDesc(format.format(reply.getCreateTime().getTime()));
		model.setDiaryId(reply.getDiary().getSid());
		model.setReplyUserId(reply.getReplyUser().getUuid());
		model.setReplyUserName(reply.getReplyUser().getUserName());
		model.setSid(reply.getSid());
	}
	
	/**
	 * 添加回复
	 * @param diaryReplyModel
	 */
	public void addReply(TeeDiaryReplyModel diaryReplyModel){
		TeeDiary diary = (TeeDiary) simpleDaoSupport.get(TeeDiary.class, diaryReplyModel.getDiaryId());
		TeePerson person = (TeePerson) simpleDaoSupport.get(TeePerson.class, diaryReplyModel.getReplyUserId());
		TeeDiaryReply diaryReply = new TeeDiaryReply();
		diaryReply.setContent(diaryReplyModel.getContent());
		diaryReply.setDiary(diary);
		diaryReply.setReplyUser(person);
		
		simpleDaoSupport.save(diaryReply);
	}
	
	/**
	 * 编辑回复
	 * @param diaryReplyModel
	 */
	public void editReply(TeeDiaryReplyModel diaryReplyModel){
		TeeDiaryReply reply = (TeeDiaryReply) simpleDaoSupport.get(TeeDiaryReply.class, diaryReplyModel.getSid());
		reply.setContent(diaryReplyModel.getContent());
		simpleDaoSupport.update(reply);
	}
	
	/**
	 * 删除回复
	 * @param replyId
	 */
	public void deleteReply(int replyId){
		simpleDaoSupport.delete(TeeDiaryReply.class, replyId);
	}
	
	/**
	 * 删除回复
	 * @param replyId
	 */
	public List<TeeDiaryReplyModel> getDiaryReplies(int diaryId){
		//处理回复
		List<TeeDiaryReply> replies = simpleDaoSupport.find("from TeeDiaryReply dr where dr.diary.sid="+diaryId+" order by dr.createTime desc", null);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<TeeDiaryReplyModel> repliesModels = new ArrayList<TeeDiaryReplyModel>();
		for(TeeDiaryReply reply:replies){
			TeeDiaryReplyModel replyModel = new TeeDiaryReplyModel();
			replyEntityToModel(reply, replyModel);
			repliesModels.add(replyModel);
			//replyModel.setCreateTimeDesc(replyModel.getCreateTimeDesc());
		}
		
		return repliesModels;
	}

	
	
	/**
	 * 获取直属下级
	 * @param request
	 * @return
	 */
	public TeeJson getXsTree(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取当前登录的用户
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取页面上传来的选中的人员的主键
		int checkedUserId=TeeStringUtil.getInteger(request.getParameter("id"), 0);
		
		List<TeeZTreeModel>list=new ArrayList<TeeZTreeModel>();
		List<TeePerson> personList=null;
		if(checkedUserId==0){//获取当前登陆人的直属下级
			personList=simpleDaoSupport.executeQuery(" from TeePerson where leader.uuid=? ", new Object[]{loginUser.getUuid()});
		}else{
			personList=simpleDaoSupport.executeQuery(" from TeePerson where leader.uuid=? ", new Object[]{checkedUserId});
		}
		
		TeeZTreeModel m=null;
		List<TeePerson> list2=null;
		if(personList!=null&&personList.size()>0){
			for (TeePerson p : personList) {
				m = new TeeZTreeModel();
				m.setId(p.getUuid()+"");
				m.setName(p.getUserName());
				//判读当前用户有没有直属下级
				list2=simpleDaoSupport.executeQuery(" from TeePerson where leader.uuid=? ",new Object[]{p.getUuid()});
				if(list2!=null&&list2.size()>0){//有下属
					m.setParent(true);
				}else{
					m.setParent(false);
				}
				
			/*	m.getParams().put("level", 1);//直属下级  第一级
*/				m.setIconSkin("person_online_node");
				list.add(m);
			}
		}
			
 	  json.setRtState(TeeConst.RETURN_OK);
	  json.setRtData(list);	
	  return json;
	 }

	/**
	 * 获取某天的所有日志
	 * */
	public TeeJson findWorkLogAll(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String writeTimeDesc=request.getParameter("writeTimeDesc");
		String time1=writeTimeDesc+" 00:00:00";
		String time2=writeTimeDesc+" 23:59:59";
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1=null;
		Date date2=null;
		try {
			date1 = sdf.parse(time1);
			date2 = sdf.parse(time2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1); 
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String hql="from TeeDiary where  createUser.uuid=? and createTime>=? and createTime<=?";
		List<TeeDiary> find = diaryDao.find(hql, new Object[]{person.getUuid(),calendar1,calendar2});
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		if(find!=null && find.size()>0){
			for(TeeDiary d:find){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("sid", d.getSid());
				map.put("title", d.getTitle());
				//map.put("content", d.getContent());
				listMap.add(map);
			}
		}
		json.setRtData(listMap);
		json.setRtState(true);
		return json;
	}

	/**
	 * 获取某个日志
	 * */
	public TeeJson findWorkLogById(HttpServletRequest request) {
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeJson json=new TeeJson();
		if(sid>0){
			TeeDiary diary = diaryDao.get(sid);
			json.setRtData(diary.getContent());
			json.setRtState(true);
		}
		return json;
	}

	/**
	 * 添加日志
	 * */
	public TeeJson workLogSave(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	    String content=request.getParameter("content");
	    String writeTimeDesc=request.getParameter("writeTimeDesc");
	    SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
	    if(sid>0){//修改
	    	TeeDiary diary = diaryDao.get(sid);
	    	diary.setContent(content);
	    	Date date=null;
			try {
				date = sdf.parse(writeTimeDesc);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	Calendar calendar = Calendar.getInstance();
	    	calendar.setTime(date);
	        diary.setWriteTime(calendar);
	    	diaryDao.update(diary);
	    }else{//添加
	       TeeDiary d=new TeeDiary();
	       Date date=null;
			try {
				date = sdf.parse(writeTimeDesc);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       Calendar calendar = Calendar.getInstance();
	       calendar.setTime(date);
	       d.setContent(content);
	       d.setCreateTime(calendar);
	       d.setWriteTime(calendar);
	       d.setCreateUser(person);
	       d.setType(2);
	       d.setTitle("日志");
	       diaryDao.save(d);
	    }
	    json.setRtState(true);
		return json;
	}
	
}
