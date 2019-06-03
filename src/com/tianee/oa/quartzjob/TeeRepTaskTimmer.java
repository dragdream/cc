package com.tianee.oa.quartzjob;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raq.dm.Record;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.subsys.informationReport.bean.TeeTaskPubRecord;
import com.tianee.oa.subsys.informationReport.bean.TeeTaskPubRecordItem;
import com.tianee.oa.subsys.informationReport.bean.TeeTaskTemplate;
import com.tianee.oa.subsys.informationReport.service.TeeTaskTemplateService;
import com.tianee.webframe.service.TeeBaseService;
/**
 * 信息上报  任务发布定时器
 * @author xsy
 *
 */
@Service
public class TeeRepTaskTimmer extends TeeBaseService{
	
	@Autowired
	private TeeTaskTemplateService taskTemplateService;
	@Autowired
	private TeePersonService personService;
	@Autowired
	private TeeDeptService deptService;
	
	
	@Autowired
	private TeeSmsManager smsManager;
	
	
	/**
	 * 定时任务执行的方法
	 */
	public void doTimmer(){
		//System.out.println("进入定时任务！");
	    //获取当前时间
		Calendar nowTime=Calendar.getInstance();
		//获取所有已经发布的定时任务
		List<TeeTaskTemplate> list=simpleDaoSupport.executeQuery(" from TeeTaskTemplate t where t.pubStatus=1 and t.preTime <= ? ", new Object[]{nowTime});
		//遍历
		if(list!=null&&list.size()>0){
			Calendar nextPreTime=null;//下次预估时间
			TeeTaskPubRecord pubRecord=null;//任务发布记录
			int pubType=0;//任务发布类型
			String  pubUserIds="";//发布人员
			List<TeePerson> personList=null;
			String pubDeptIds="";//发布部门
			List<TeeDepartment> deptList=null;//发布部门
			TeeTaskPubRecordItem recordItem=null;//任务发布记录项
			Map requestData =null;
			String typeDesc="";
			//当前时间大于预估时间则  增加一条任务发布记录  (除一次性)
			for (TeeTaskTemplate teeTaskTemplate : list) {
				if(teeTaskTemplate.getPreTime()!=null){
			        //  1=日报   2=周报    3=月报     4=季报     5=年报   6=一次性
					
					if(teeTaskTemplate.getTaskType()==1){
						typeDesc="日报";
					}else if(teeTaskTemplate.getTaskType()==2){
						typeDesc="周报";
					}else if(teeTaskTemplate.getTaskType()==3){
						typeDesc="月报";
					}else if(teeTaskTemplate.getTaskType()==4){
						typeDesc="季报 ";
					}else if(teeTaskTemplate.getTaskType()==5){
						typeDesc="年报 ";
					}else{
						typeDesc="一次性信息 ";
					}
					
				    //新建一条任务发布记录
					pubRecord=new TeeTaskPubRecord();
					pubRecord.setCreateTime(nowTime);
					pubRecord.setTaskTemplate(teeTaskTemplate);
					simpleDaoSupport.save(pubRecord);
					
					//新建发布记录项
					//获取发布类型  1=按人员   2=按部门
					pubType=teeTaskTemplate.getPubType();
					if(pubType==1){//按人员
						pubUserIds=teeTaskTemplate.getPubUserIds();
						personList=personService.getPersonByUuids(pubUserIds);
						if(personList!=null&&personList.size()>0){
							for (TeePerson teePerson : personList) {
								recordItem=new TeeTaskPubRecordItem();
								recordItem.setCreateTime(nowTime);
								recordItem.setFlag(0);//上报状态  0=未上报   1=已上报
								recordItem.setTaskPubRecord(pubRecord);
								recordItem.setTaskTemplate(teeTaskTemplate);
								recordItem.setUser(teePerson);
								
								simpleDaoSupport.save(recordItem);
								
								
								//发送消息提醒	
								requestData = new HashMap();
								requestData.put("content", "您有一个"+typeDesc+"["+teeTaskTemplate.getTaskName()+"]需要进行上报，请及时处理。");
								requestData.put("userListIds",teePerson.getUuid());
								requestData.put("moduleNo", "092");
								requestData.put("remindUrl", "/system/subsys/informationReport/myReport/report.jsp?pubRecordItemId="+recordItem.getSid()+"&pubRecordId="+pubRecord.getSid()+"&taskTemplateId="+teeTaskTemplate.getSid()+"&taskTemplateName="+teeTaskTemplate.getTaskName()+"&type=1 ");
								smsManager.sendSms(requestData, null);
							}
						}
						
						
						
					}else{//按部门
						pubDeptIds=teeTaskTemplate.getPubDeptIds();
						deptList=deptService.getDeptByUuids(pubDeptIds);
						if(deptList!=null&&deptList.size()>0){
							for (TeeDepartment teeDepartment : deptList) {
								recordItem=new TeeTaskPubRecordItem();
								recordItem.setCreateTime(nowTime);
								recordItem.setFlag(0);//上报状态  0=未上报   1=已上报
								recordItem.setTaskPubRecord(pubRecord);
								recordItem.setTaskTemplate(teeTaskTemplate);
								recordItem.setDept(teeDepartment);
								
								simpleDaoSupport.save(recordItem);
								
								
								
								//发送消息提醒	
								if(teeDepartment.getInfoReportUser()!=null){
									requestData = new HashMap();
									requestData.put("content", "您有一个"+typeDesc+"["+teeTaskTemplate.getTaskName()+"]需要进行上报，请及时处理。");
									requestData.put("userListIds",teeDepartment.getInfoReportUser().getUuid());
									requestData.put("moduleNo", "092");
									requestData.put("remindUrl", "/system/subsys/informationReport/myReport/report.jsp?pubRecordItemId="+recordItem.getSid()+"&pubRecordId="+pubRecord.getSid()+"&taskTemplateId="+teeTaskTemplate.getSid()+"&taskTemplateName="+teeTaskTemplate.getTaskName()+"&type=1 ");
									smsManager.sendSms(requestData, null);
								}
								
							}
						}	
					}
					
					//修改任务模板的预估时间
					nextPreTime = taskTemplateService.getPreTime(teeTaskTemplate);
					if(teeTaskTemplate.getTaskType()==6){//一次性
						teeTaskTemplate.setPreTime(null);
					}else{
						teeTaskTemplate.setPreTime(nextPreTime);
					}	
					simpleDaoSupport.update(teeTaskTemplate);
							
				}	
			}
		}	
	}
	
	
	
	
}
