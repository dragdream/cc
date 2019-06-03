package com.tianee.oa.subsys.informationReport.service;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.informationReport.bean.TeeTaskPubRecord;
import com.tianee.oa.subsys.informationReport.bean.TeeTaskTemplate;
import com.tianee.oa.subsys.informationReport.dao.TeeTaskPubRecordDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeTaskPubRecordService extends TeeBaseService{
	@Autowired
	private TeeTaskPubRecordDao taskPubRecordDao;

	public TeeEasyuiDataGridJson getRecordListByTaskTemplateId(
			TeeDataGridModel dm, HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取任务模板主键
		int taskTemplateId=TeeStringUtil.getInteger(request.getParameter("taskTemplateId"), 0);
		TeeTaskTemplate template=(TeeTaskTemplate) simpleDaoSupport.get(TeeTaskTemplate.class,taskTemplateId);
		SimpleDateFormat sdf=null;
		if(template!=null){
			if(template.getTaskType()==1){//1=日报           
				sdf=new SimpleDateFormat("yyyy年MM月dd日");
			}else if(template.getTaskType()==3){//3=月报
				sdf=new SimpleDateFormat("yyyy年MM月");
			}else if(template.getTaskType()==5){// 5=年报
				sdf=new SimpleDateFormat("yyyy年");
			}else if(template.getTaskType()==6){//  6=一次性
				sdf=new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");
			}else if(template.getTaskType()==2){//  2=周报  
				sdf=new SimpleDateFormat("yyyy年MM月");
			}else if(template.getTaskType()==4){//  4=季报       
				sdf=new SimpleDateFormat("yyyy年");
			}
		}
		
		
		String hql = " from TeeTaskPubRecord  t where t.taskTemplate.sid=? ";
		List param = new ArrayList();
		param.add(taskTemplateId);
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by t.createTime desc";

		List<TeeTaskPubRecord> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List <Map> returnList=new ArrayList<Map>();
		if (list != null) {
			Map map=null;
			String reportTime="";//上报时间
			for (int i = 0; i < list.size(); i++) {
				map=new HashMap();
				if(template!=null){
					if(template.getTaskType()==1||template.getTaskType()==3||template.getTaskType()==5||template.getTaskType()==6){
						reportTime=sdf.format(list.get(i).getCreateTime().getTime());
					}else if(template.getTaskType()==2){//周报
						reportTime=sdf.format(list.get(i).getCreateTime().getTime())+"第"+list.get(i).getCreateTime().get(Calendar.WEEK_OF_MONTH)+"周";	
					}else if(template.getTaskType()==4){//季报
						reportTime=sdf.format(list.get(i).getCreateTime().getTime());
						int month=list.get(i).getCreateTime().get(Calendar.MONTH)+1;
						if(month==1||month==2||month==3){
							reportTime+="第一季度";
						}else if(month==4||month==5||month==6){
							reportTime+="第二季度";
						}else if(month==7||month==8||month==9){
							reportTime+="第三季度";
						}else{
							reportTime+="第四季度";
						}
					}
				}
				
				//应报数量   已报数量    未报数量
				long sumNum=simpleDaoSupport.count("select count(t) from TeeTaskPubRecordItem t where t.taskPubRecord.sid=? ", new Object[]{list.get(i).getSid()});
				long ybNum=simpleDaoSupport.count("select count(t) from TeeTaskPubRecordItem t where t.taskPubRecord.sid=? and t.flag=1 ", new Object[]{list.get(i).getSid()});
				long wbNum=simpleDaoSupport.count("select count(t) from TeeTaskPubRecordItem t where t.taskPubRecord.sid=? and t.flag=0 ", new Object[]{list.get(i).getSid()});
				//上报率
				// 创建一个数值格式化对象  
                NumberFormat numberFormat = NumberFormat.getInstance();  
		        // 设置精确到小数点后0位  
		        numberFormat.setMaximumFractionDigits(2);  
		        String result="";
		        if(sumNum!=0){
		        	result = numberFormat.format((float) ybNum / (float) sumNum * 100)+"%";  
		        }
		        
				
				map.put("reportTime", reportTime);
				map.put("sumNum", sumNum);
				map.put("ybNum", ybNum);
				map.put("wbNum", wbNum);
				map.put("rate", result);
				
				map.put("recordId", list.get(i).getSid());
				
				returnList.add(map);
				
			}
		}
		j.setRows(returnList);// 设置返回的行
		return j;
	}
}
