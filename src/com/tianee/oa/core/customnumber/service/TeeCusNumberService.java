package com.tianee.oa.core.customnumber.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sun.swing.internal.plaf.synth.resources.synth;
import com.tianee.oa.core.customnumber.bean.TeeCustomNumber;
import com.tianee.oa.core.customnumber.bean.TeeCustomNumberRecord;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeCusNumberService extends TeeBaseService {

	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, Object object) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from TeeCustomNumber ";
		List<TeeCustomNumber> list = simpleDaoSupport.pageFind(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		
		dataGridJson.setRows(list);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}

	public void addCusNumber(TeeCustomNumber customNumber) {
		Calendar date = Calendar.getInstance();
		customNumber.setLastDate(date);
		simpleDaoSupport.save(customNumber);
		
	}
	
	public void deleteUser(int uuid) {
		simpleDaoSupport.delete(TeeCustomNumber.class, uuid);
	}
	
	
	public void updateNumber(TeeCustomNumber customNumber, int uuid) {
		TeeCustomNumber cn=(TeeCustomNumber) simpleDaoSupport.get(TeeCustomNumber.class, uuid);
		BeanUtils.copyProperties(customNumber, cn);
		Calendar date = Calendar.getInstance();
		cn.setLastDate(date);
		simpleDaoSupport.update(cn);
		
	}

	public TeeCustomNumber getCustomNumber(int uuid) {
		TeeCustomNumber customNumber = (TeeCustomNumber) simpleDaoSupport.get(TeeCustomNumber.class, uuid);
		return customNumber;
	}
	
	/**
	 * 生成编号
	 * @param uuid 编号主键
	 * @param model 模块编码  详情见module_sort表
	 * @param modelId 模块ID  业务表的主键
	 * @return
	 */
	public synchronized String generateCustomNumber(int uuid,String model,String modelId) {
		//获取对应model和modelId的记录，如果存在，则直接返回
		List<TeeCustomNumberRecord> records = simpleDaoSupport.find("from TeeCustomNumberRecord where model='"+model+"' and modelId='"+modelId+"' and customNumberId="+uuid, null);
		if(records.size()!=0){
			return records.get(0).getNumberStyle();
		}
		
		TeeCustomNumber customNumber = getCustomNumber(uuid); 
		if(customNumber==null){
			return "";
		}
		String userSetStyle=customNumber.getUserSetStyle();
		String showStr="";
		int type=customNumber.getAdditonalType();
		int numBit=customNumber.getNumberBit();
		int curNUmber=customNumber.getCurrentNumber()+1;
		int curNumBit =(curNUmber+"").length();
		Calendar date = customNumber.getLastDate();//最后一次修改的日期
		Calendar now= Calendar.getInstance();//当前系统日期
		if(type==1){             //自动累加
			/*if(year < 1900) year += 1900;*/
		    //将字符串中所有的YYYY替换成当前年
		    if (userSetStyle.indexOf("YYYY") > -1) {
		    	userSetStyle = userSetStyle.replaceAll("YYYY", now.get(Calendar.YEAR)+"");
		    }
		    if (userSetStyle.indexOf("YY") > -1) {
		    	userSetStyle = userSetStyle.replaceAll("YY", (now.get(Calendar.YEAR)+"").substring(2));
		    }
		    //将字符串中所有的MM替换成当前月
		    if (userSetStyle.indexOf("MM") > -1) {
		        int month = now.get(Calendar.MONTH)+1;
		        String monthStr = "";
		        if (month < 10) monthStr = "0";
		        monthStr += month;
		        userSetStyle = userSetStyle.replaceAll("MM", monthStr);
		    }
		    
		    //将字符串中所有的DD替换成当前日
		    if (userSetStyle.indexOf("DD") > -1) {
		        int day = now.get(Calendar.DATE);
				String dayStr = "";
				if (day < 10) dayStr = "0";
				dayStr += day;
				userSetStyle = userSetStyle.replaceAll("DD", dayStr);
		    }
		    //将字符串中所有的##替换成编号
		    if (userSetStyle.indexOf("##") > -1) {
		        String num = "";
			    for (int i = 0; i < numBit - curNumBit; i++) {
			        num += "0";
			    }
		        	num +=curNUmber;
		        	userSetStyle =userSetStyle.replaceAll("##", num);
		        	curNUmber=Integer.parseInt(num);
		    }
		    showStr=userSetStyle;
		    
		}
		
		if(type==2){           //按年累加
			int year=date.get(Calendar.YEAR);//最后一次修改日期的年份
			int nowYear=now.get(Calendar.YEAR);//当前年份
			if(year==nowYear){    //当年份不变时
				 //将字符串中所有的YYYY替换成当前年
			    if (userSetStyle.indexOf("YYYY") > -1) {
			    	userSetStyle = userSetStyle.replaceAll("YYYY", nowYear+"");
			    }
			    if (userSetStyle.indexOf("YY") > -1) {
			    	userSetStyle = userSetStyle.replaceAll("YY", (nowYear+"").substring(2));
			    }
			    //将字符串中所有的MM替换成当前月
			    if (userSetStyle.indexOf("MM") > -1) {
			        int month = now.get(Calendar.MONTH)+1;
			        String monthStr = "";
			        if (month < 10) monthStr = "0";
			        monthStr += month;
			        userSetStyle = userSetStyle.replaceAll("MM", monthStr);
			    }
			    
			    //将字符串中所有的DD替换成当前日
			    if (userSetStyle.indexOf("DD") > -1) {
			        int day = now.get(Calendar.DATE);
					String dayStr = "";
					if (day < 10) dayStr = "0";
					dayStr += day;
					userSetStyle = userSetStyle.replaceAll("DD", dayStr);
			    }
			    //将字符串中所有的##替换成编号
			    if (userSetStyle.indexOf("##") > -1) {
			        String num = "";
				    for (int i = 0; i < numBit - curNumBit; i++) {
				        num += "0";
				    }
			        	num +=curNUmber;
			        	userSetStyle =userSetStyle.replaceAll("##", num);
			        	curNUmber=Integer.parseInt(num);
			    }
			    showStr=userSetStyle;
			}
			else{  //当年份改变时，编号置1累加
				 //将字符串中所有的YYYY替换成当前年
			    if (userSetStyle.indexOf("YYYY") > -1) {
			    	userSetStyle = userSetStyle.replaceAll("YYYY", nowYear+"");
			    }
			    if (userSetStyle.indexOf("YY") > -1) {
			    	userSetStyle = userSetStyle.replaceAll("YY", (nowYear+"").substring(2));
			    }
			    //将字符串中所有的MM替换成当前月
			    if (userSetStyle.indexOf("MM") > -1) {
			        int month = now.get(Calendar.MONTH)+1;
			        String monthStr = "";
			        if (month < 10) monthStr = "0";
			        monthStr += month;
			        userSetStyle = userSetStyle.replaceAll("MM", monthStr);
			    }
			    
			    //将字符串中所有的DD替换成当前日
			    if (userSetStyle.indexOf("DD") > -1) {
			        int day = now.get(Calendar.DATE);
					String dayStr = "";
					if (day < 10) dayStr = "0";
					dayStr += day;
					userSetStyle = userSetStyle.replaceAll("DD", dayStr);
			    }
			    //将字符串中所有的##替换成编号
			    if (userSetStyle.indexOf("##") > -1) {
			        String num = "";
			        curNUmber=1;
			        curNumBit =(curNUmber+"").length();
				    for (int i = 0; i < numBit - curNumBit; i++) {
				        num += "0";
				    }
			        	num +=curNUmber;
			        	userSetStyle =userSetStyle.replaceAll("##", num);
			        	curNUmber=Integer.parseInt(num);
			    }
			    showStr=userSetStyle;
			}
		}
		if(type==3){  //按月累加
			int year=date.get(Calendar.YEAR);//最后一次修改日期年份
			int nowYear=now.get(Calendar.YEAR);//当前系统年份
			int months=date.get(Calendar.MONTH);//最后一次修改时间月
			int nowMonth=now.get(Calendar.MONTH);//当前系统月份
			if(year==nowYear&&months==nowMonth){   //年份和月份都不变的时候
					 //将字符串中所有的YYYY替换成当前年
				    if (userSetStyle.indexOf("YYYY") > -1) {
				    	userSetStyle = userSetStyle.replaceAll("YYYY", nowYear+"");
				    }
				    if (userSetStyle.indexOf("YY") > -1) {
				    	userSetStyle = userSetStyle.replaceAll("YY", (nowYear+"").substring(2));
				    }
				    //将字符串中所有的MM替换成当前月
				    if (userSetStyle.indexOf("MM") > -1) {
				        int month = now.get(Calendar.MONTH)+1;
				        String monthStr = "";
				        if (month < 10) monthStr = "0";
				        monthStr += month;
				        userSetStyle = userSetStyle.replaceAll("MM", monthStr);
				    }
				    
				    //将字符串中所有的DD替换成当前日
				    if (userSetStyle.indexOf("DD") > -1) {
				        int day = now.get(Calendar.DATE);
						String dayStr = "";
						if (day < 10) dayStr = "0";
						dayStr += day;
						userSetStyle = userSetStyle.replaceAll("DD", dayStr);
				    }
				    //将字符串中所有的##替换成编号
				    if (userSetStyle.indexOf("##") > -1) {
				        String num = "";
					    for (int i = 0; i < numBit - curNumBit; i++) {
					        num += "0";
					    }
				        	num +=curNUmber;
				        	userSetStyle =userSetStyle.replaceAll("##", num);
				        	curNUmber=Integer.parseInt(num);
				    }
				    showStr=userSetStyle;
				
			}else{
				if(year!=nowYear||months!=nowMonth){
				 //将字符串中所有的YYYY替换成当前年
			    if (userSetStyle.indexOf("YYYY") > -1) {
			    	userSetStyle = userSetStyle.replaceAll("YYYY", nowYear+"");
			    }
			    if (userSetStyle.indexOf("YY") > -1) {
			    	userSetStyle = userSetStyle.replaceAll("YY", (nowYear+"").substring(2));
			    }
			    //将字符串中所有的MM替换成当前月
			    if (userSetStyle.indexOf("MM") > -1) {
			        int month = now.get(Calendar.MONTH)+1;
			        String monthStr = "";
			        if (month < 10) monthStr = "0";
			        monthStr += month;
			        userSetStyle = userSetStyle.replaceAll("MM", monthStr);
			    }
			    
			    //将字符串中所有的DD替换成当前日
			    if (userSetStyle.indexOf("DD") > -1) {
			        int day = now.get(Calendar.DATE);
					String dayStr = "";
					if (day < 10) dayStr = "0";
					dayStr += day;
					userSetStyle = userSetStyle.replaceAll("DD", dayStr);
			    }
			    //将字符串中所有的##替换成编号
			    if (userSetStyle.indexOf("##") > -1) {
			        String num = "";
			        curNUmber=1;
			        curNumBit =(curNUmber+"").length();
				    for (int i = 0; i < numBit - curNumBit; i++) {
				        num += "0";
				    }
			        	num +=curNUmber;
			        	userSetStyle =userSetStyle.replaceAll("##", num);
			        	curNUmber=Integer.parseInt(num);
			    }
			    showStr=userSetStyle;
				}
			}
		}
		
		if(type==4){
			int year=date.get(Calendar.YEAR);
			int nowYear=now.get(Calendar.YEAR);
			int months=date.get(Calendar.MONTH);
			int nowMonth=now.get(Calendar.MONTH);
			int days=date.get(Calendar.DATE);
			int nowDay = now.get(Calendar.DATE);
			if(year==nowYear&&months==nowMonth&&days==nowDay){
				  //将字符串中所有的YYYY替换成当前年
			    if (userSetStyle.indexOf("YYYY") > -1) {
			    	userSetStyle = userSetStyle.replaceAll("YYYY", now.get(Calendar.YEAR)+"");
			    }
			    if (userSetStyle.indexOf("YY") > -1) {
			    	userSetStyle = userSetStyle.replaceAll("YY", (now.get(Calendar.YEAR)+"").substring(2));
			    }
			    //将字符串中所有的MM替换成当前月
			    if (userSetStyle.indexOf("MM") > -1) {
			        int month = now.get(Calendar.MONTH)+1;
			        String monthStr = "";
			        if (month < 10) monthStr = "0";
			        monthStr += month;
			        userSetStyle = userSetStyle.replaceAll("MM", monthStr);
			    }
			    
			    //将字符串中所有的DD替换成当前日
			    if (userSetStyle.indexOf("DD") > -1) {
			        int day = now.get(Calendar.DATE);
					String dayStr = "";
					if (day < 10) dayStr = "0";
					dayStr += day;
					userSetStyle = userSetStyle.replaceAll("DD", dayStr);
			    }
			    //将字符串中所有的##替换成编号
			    if (userSetStyle.indexOf("##") > -1) {
			        String num = "";
				    for (int i = 0; i < numBit - curNumBit; i++) {
				        num += "0";
				    }
			        	num +=curNUmber;
			        	userSetStyle =userSetStyle.replaceAll("##", num);
			        	curNUmber=Integer.parseInt(num);
			    }
			    showStr=userSetStyle;
			    
			}else{
				if(year!=nowYear||months!=nowMonth||days!=nowDay){
					 //将字符串中所有的YYYY替换成当前年
				    if (userSetStyle.indexOf("YYYY") > -1) {
				    	userSetStyle = userSetStyle.replaceAll("YYYY", nowYear+"");
				    }
				    if (userSetStyle.indexOf("YY") > -1) {
				    	userSetStyle = userSetStyle.replaceAll("YY", (nowYear+"").substring(2));
				    }
				    //将字符串中所有的MM替换成当前月
				    if (userSetStyle.indexOf("MM") > -1) {
				        int month = now.get(Calendar.MONTH)+1;
				        String monthStr = "";
				        if (month < 10) monthStr = "0";
				        monthStr += month;
				        userSetStyle = userSetStyle.replaceAll("MM", monthStr);
				    }
				    
				    //将字符串中所有的DD替换成当前日
				    if (userSetStyle.indexOf("DD") > -1) {
				        int day = now.get(Calendar.DATE);
						String dayStr = "";
						if (day < 10) dayStr = "0";
						dayStr += day;
						userSetStyle = userSetStyle.replaceAll("DD", dayStr);
				    }
				    //将字符串中所有的##替换成编号
				    if (userSetStyle.indexOf("##") > -1) {
				        String num = "";
				        curNUmber=1;
				        curNumBit =(curNUmber+"").length();
					    for (int i = 0; i < numBit - curNumBit; i++) {
					        num += "0";
					    }
				        	num +=curNUmber;
				        	userSetStyle =userSetStyle.replaceAll("##", num);
				        	curNUmber=Integer.parseInt(num);
				    }
				    showStr=userSetStyle;
				}
			}
		}
		customNumber.setCurrentNumber(curNUmber);
		customNumber.setLastDate(now);
		
		//保存编号记录
		TeeCustomNumberRecord customNumberRecord = new TeeCustomNumberRecord();
		customNumberRecord.setCrTime(Calendar.getInstance());
		customNumberRecord.setCustomNumberId(customNumber.getUuid());
		customNumberRecord.setNumberValue(curNUmber);
		customNumberRecord.setNumberStyle(showStr);
		customNumberRecord.setModel(model);
		customNumberRecord.setModelId(modelId);
		simpleDaoSupport.save(customNumberRecord);
		
		updateNumber(customNumber, uuid);
		return showStr;
	}
}
