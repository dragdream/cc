package com.tianee.oa.core.base.onduty.service;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.base.onduty.bean.TeePbOnDuty;
import com.tianee.oa.core.base.onduty.bean.TeePbTypeChild;
import com.tianee.oa.core.base.onduty.dao.TeePbOnDutyDao;
import com.tianee.oa.core.base.onduty.model.TeePbOnDutyModel;
import com.tianee.oa.core.base.onduty.model.TeePbTypeChildModel;
import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.phoneSms.bean.TeeSmsSendPhone;
import com.tianee.oa.core.phoneSms.dao.TeeSmsSendPhoneDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.file.TeeCSVUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeePbOnDutyService extends TeeBaseService{

	@Autowired
	private TeePbOnDutyDao teePbOnDutyDao;
	
	@Autowired
	private  TeePersonDao personDao;
	
	@Autowired
	private TeeSmsSender smsSender;
	
	@Autowired
	private TeeSmsSendPhoneDao sendPhoneDao;

	/**
	 * 添加
	 * */
	public TeeJson addOrUpdateDuty(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int bzUserId = TeeStringUtil.getInteger(request.getParameter("bzUserId"),0);
		int bdUserId = TeeStringUtil.getInteger(request.getParameter("bdUserId"),0);
		int yzUserId = TeeStringUtil.getInteger(request.getParameter("yzUserId"),0);
		int ydUserId = TeeStringUtil.getInteger(request.getParameter("ydUserId"),0);
		int zdUserId = TeeStringUtil.getInteger(request.getParameter("zdUserId"),0);
		String mobileNo = request.getParameter("mobileNo");
		String curDateStr = request.getParameter("curDateStr");//日期
		
		//String childStr = request.getParameter("childStr");
		if(curDateStr!=null && !"".equals(curDateStr)){
			String[] split = curDateStr.split(",");
			for(int i=0;i<split.length;i++){
				String userIds = "";
				TeePbOnDuty duty=new TeePbOnDuty();
				if(bzUserId>0){
					TeePerson bzUser=new TeePerson();
					bzUser.setUuid(bzUserId);
					duty.setBzUser(bzUser);
					userIds+=bzUserId+",";
				}
				if(bdUserId>0){
					TeePerson bdUser=new TeePerson();
					bdUser.setUuid(bdUserId);
					duty.setBdUser(bdUser);
					userIds+=bdUserId+",";
				}
				if(yzUserId>0){
					TeePerson yzUser=new TeePerson();
					yzUser.setUuid(yzUserId);
					duty.setYzUser(yzUser);
					userIds+=yzUserId+",";
				}
				if(ydUserId>0){
					TeePerson ydUser=new TeePerson();
					ydUser.setUuid(ydUserId);
					duty.setYdUser(ydUser);
					userIds+=ydUserId+",";
				}
				if(zdUserId>0){
					TeePerson zdUser=new TeePerson();
					zdUser.setUuid(zdUserId);
					duty.setZdUser(zdUser);
					userIds+=zdUserId+",";
				}
				
				duty.setMobileNo(mobileNo);
				Date date=TeeStringUtil.getDate(split[i], "yyyy-MM-dd");
				duty.setCreTime(date);//值班时间
				duty.setAddUser(person);
				List<TeePbOnDuty> find = teePbOnDutyDao.find("from TeePbOnDuty where creTime=?", new Object[]{date});
				if(find!=null && find.size()>0){
					teePbOnDutyDao.deleteOrUpdateByQuery("delete from TeePbOnDuty where creTime=?", new Object[]{date});//删除原有的值班记录				
				}
				Serializable save = teePbOnDutyDao.save(duty);
				int integer = TeeStringUtil.getInteger(save, 0);
				
				//发送消息提醒
				String Str=person.getUserName()+"给您安排了["+TeeDateUtil.format(date, "yyyy-MM-dd")+"]的值班";
				Map requestData = new HashMap();
				if(userIds.endsWith(",")){
					userIds = userIds.substring(0,userIds.length()-1);
		    	}
				if(userIds!=null && !"".equals(userIds)){//值班人员
					String[] split2 = userIds.split(",");
					List<String> split3 = new ArrayList<String>();
					for(int j=0;j<split2.length;j++){
						boolean flag = true;
						for(int k=j+1;k<split2.length;k++){
							if(split2[j].equals(split2[k])){
								flag = false;
								break;
							}
						}
						if(flag){
							split3.add(split2[j]);
						}
					}
					for(int j=0;j<split3.size();j++){
						int userId = TeeStringUtil.getInteger(split3.get(j), 0);
						requestData.put("moduleNo", "093");
						requestData.put("userListIds", userId);
						requestData.put("content", Str);
						requestData.put("remindUrl", "/system/core/base/onduty/myManager.jsp");
						smsSender.sendSms(requestData, person);
					}
				}
			}
			
			json.setRtState(true);
			json.setRtMsg("添加成功");
		}else{
			json.setRtState(false);
			json.setRtMsg("添加失败");
		}
		return json;
	}

	/**
	 * 获取值班信息
	 * */
	public TeeJson findDutyByDate(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String flag = request.getParameter("flag");
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//totalOfMonthDays
		int totalOfMonthDays = TeeStringUtil.getInteger(request.getParameter("totalOfMonthDays"),0);
		String str1=year+"-"+month+"-01";
		String str2=year+"-"+month+"-"+totalOfMonthDays;
		String hql="from TeePbOnDuty where creTime>=? and creTime<=? ";
		List<TeePbOnDuty> find = null;
		if(flag!=null && flag.equals("1")){
			hql+=" and (bzUser=? or bdUser=? or yzUser=? or ydUser=? or zdUser=?) ";
			find = teePbOnDutyDao.find(hql, new Object[]{TeeStringUtil.getDate(str1, "yyyy-MM-dd"),TeeStringUtil.getDate(str2, "yyyy-MM-dd"),person,person,person,person,person});
		}else{
			find = teePbOnDutyDao.find(hql, new Object[]{TeeStringUtil.getDate(str1, "yyyy-MM-dd"),TeeStringUtil.getDate(str2, "yyyy-MM-dd")});
		}
		List<TeePbOnDutyModel> modelList=new ArrayList<TeePbOnDutyModel>();
		if(find!=null && find.size()>0){
			for(TeePbOnDuty duty:find){
				TeePbOnDutyModel model=new TeePbOnDutyModel();
				model.setSid(duty.getSid());
				String format = TeeDateUtil.format(duty.getCreTime(), "dd");
				model.setCreTime(format);//值班日期
				if(duty.getBzUser()!=null){
					String bzUserName=duty.getBzUser().getUserName();
					if("王辉".equals(bzUserName)){
						if("执法二队".equals(duty.getBzUser().getDept().getDeptName())){
							bzUserName=bzUserName+"(二队)";
						}else{
							bzUserName=bzUserName+"(法制)";
						}
					}
					model.setBzUserId(duty.getBzUser().getUuid());
					model.setBzUserName(bzUserName);
					if(duty.getBzUser().getUuid()==242){
						model.setBzUserName("<span style='color:red;font-weight:bold;'>"+bzUserName+"</span>");
					}
				}
				if(duty.getBdUser()!=null){
					String bdUserName = duty.getBdUser().getUserName();
					if("王辉".equals(bdUserName)){
						if("执法二队".equals(duty.getBdUser().getDept().getDeptName())){
							bdUserName=bdUserName+"(二队)";
						}else{
							bdUserName=bdUserName+"(法制)";
						}
					}
					model.setBdUserId(duty.getBdUser().getUuid());
					model.setBdUserName(bdUserName);
					if(duty.getBdUser().getUuid()==242){
						model.setBdUserName("<span style='color:red;font-weight:bold;'>"+bdUserName+"</span>");
					}
				}
				if(duty.getYzUser()!=null){
					String yzUserName=duty.getYzUser().getUserName();
					if("王辉".equals(yzUserName)){
						if("执法二队".equals(duty.getYzUser().getDept().getDeptName())){
							yzUserName=yzUserName+"(二队)";
						}else{
							yzUserName=yzUserName+"(法制)";
						}
					}
					model.setYzUserId(duty.getYzUser().getUuid());
					model.setYzUserName(yzUserName);
					if(duty.getYzUser().getUuid()==242){
						model.setYzUserName("<span style='color:red;font-weight:bold;'>"+yzUserName+"</span>");
					}
				}
				if(duty.getYdUser()!=null){
					String ydUserName=duty.getYdUser().getUserName();
					if("王辉".equals(ydUserName)){
						if("执法二队".equals(duty.getYdUser().getDept().getDeptName())){
							ydUserName=ydUserName+"(二队)";
						}else{
							ydUserName=ydUserName+"(法制)";
						}
					}
					model.setYdUserId(duty.getYdUser().getUuid());
					model.setYdUserName(ydUserName);
					if(duty.getYdUser().getUuid()==242){
						model.setYdUserName("<span style='color:red;font-weight:bold;'>"+ydUserName+"</span>");
					}
				}
				if(duty.getZdUser()!=null){
					model.setZdUserId(duty.getZdUser().getUuid());
					model.setZdUserName(duty.getZdUser().getUserName());
					if(duty.getZdUser().getUuid()==242){
						model.setZdUserName("<span style='color:red;font-weight:bold;font-size:30px'>"+duty.getZdUser().getUserName()+"</span>");
					}
				}
				if(duty.getMobileNo()!=null){
					model.setMobileNo(duty.getMobileNo());
				}
				modelList.add(model);
			}
		}
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}
	
	/**
	 * 个人值班信息
	 * */
	public TeeJson findDutyByDate2(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//totalOfMonthDays
		int totalOfMonthDays = TeeStringUtil.getInteger(request.getParameter("totalOfMonthDays"),0);
		String str1=year+"-"+month+"-01";
		String str2=year+"-"+month+"-"+totalOfMonthDays;
		String hql="from TeePbOnDuty where creTime>=? and creTime<=?";
		List<TeePbOnDuty> find = teePbOnDutyDao.find(hql, new Object[]{TeeStringUtil.getDate(str1, "yyyy-MM-dd"),TeeStringUtil.getDate(str2, "yyyy-MM-dd")});
		List<TeePbOnDutyModel> modelList=new ArrayList<TeePbOnDutyModel>();
		if(find!=null && find.size()>0){
			for(TeePbOnDuty duty:find){
				TeePbOnDutyModel model=new TeePbOnDutyModel();
				model.setSid(duty.getSid());
				String format = TeeDateUtil.format(duty.getCreTime(), "dd");
				model.setCreTime(format);//值班日期
				if(duty.getBzUser()!=null){
					model.setBzUserId(duty.getBzUser().getUuid());
					model.setBzUserName(duty.getBzUser().getUserName());
				}
				if(duty.getBdUser()!=null){
					model.setBdUserId(duty.getBdUser().getUuid());
					model.setBdUserName(duty.getBdUser().getUserName());
				}
				if(duty.getYzUser()!=null){
					model.setYzUserId(duty.getYzUser().getUuid());
					model.setYzUserName(duty.getYzUser().getUserName());
				}
				if(duty.getYdUser()!=null){
					model.setYdUserId(duty.getYdUser().getUuid());
					model.setYdUserName(duty.getYdUser().getUserName());
				}
				if(duty.getZdUser()!=null){
					model.setZdUserId(duty.getZdUser().getUuid());
					model.setZdUserName(duty.getZdUser().getUserName());
				}
				if(duty.getMobileNo()!=null){
					model.setMobileNo(duty.getMobileNo());
				}
				modelList.add(model);
			}
		}
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}

	/**
	 * 根据时间获取值班信息
	 * */
	public TeeJson findDutyByDate3(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String curDateStr = request.getParameter("curDateStr");

		String hql="from TeePbOnDuty where creTime=? ";
		List<TeePbOnDuty> find = teePbOnDutyDao.find(hql, new Object[]{TeeStringUtil.getDate(curDateStr, "yyyy-MM-dd")});
		
		TeePbOnDutyModel model = null;
		if(find!=null && find.size()>0){
			model=new TeePbOnDutyModel();
			TeePbOnDuty duty = find.get(0);
			model.setSid(duty.getSid());
			String format = TeeDateUtil.format(duty.getCreTime(), "dd");
			model.setCreTime(format);//值班日期
			if(duty.getBzUser()!=null){
				model.setBzUserId(duty.getBzUser().getUuid());
				model.setBzUserName(duty.getBzUser().getUserName());
			}
			if(duty.getBdUser()!=null){
				model.setBdUserId(duty.getBdUser().getUuid());
				model.setBdUserName(duty.getBdUser().getUserName());
			}
			if(duty.getYzUser()!=null){
				model.setYzUserId(duty.getYzUser().getUuid());
				model.setYzUserName(duty.getYzUser().getUserName());
			}
			if(duty.getYdUser()!=null){
				model.setYdUserId(duty.getYdUser().getUuid());
				model.setYdUserName(duty.getYdUser().getUserName());
			}
			if(duty.getZdUser()!=null){
				model.setZdUserId(duty.getZdUser().getUuid());
				model.setZdUserName(duty.getZdUser().getUserName());
			}
			if(duty.getMobileNo()!=null){
				model.setMobileNo(duty.getMobileNo());
			}
		}
		json.setRtState(true);
		json.setRtData(model);
		return json;
	}
	
	/**
	 * 根据id获取值班信息
	 * */
	public TeeJson findDutyById(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeePbOnDuty duty = teePbOnDutyDao.get(sid);
		TeePbOnDutyModel model=new TeePbOnDutyModel();
		if(duty!=null){
			model.setSid(duty.getSid());
			String format = TeeDateUtil.format(duty.getCreTime(), "dd");
			model.setCreTime(format);//值班日期
			if(duty.getBzUser()!=null){
				model.setBzUserId(duty.getBzUser().getUuid());
				model.setBzUserName(duty.getBzUser().getUserName());
			}
			if(duty.getBdUser()!=null){
				model.setBdUserId(duty.getBdUser().getUuid());
				model.setBdUserName(duty.getBdUser().getUserName());
			}
			if(duty.getYzUser()!=null){
				model.setYzUserId(duty.getYzUser().getUuid());
				model.setYzUserName(duty.getYzUser().getUserName());
			}
			if(duty.getYdUser()!=null){
				model.setYdUserId(duty.getYdUser().getUuid());
				model.setYdUserName(duty.getYdUser().getUserName());
			}
			if(duty.getZdUser()!=null){
				model.setZdUserId(duty.getZdUser().getUuid());
				model.setZdUserName(duty.getZdUser().getUserName());
			}
			if(duty.getMobileNo()!=null){
				model.setMobileNo(duty.getMobileNo());
			}
		}
		json.setRtState(true);
		json.setRtData(model);
		return json;
	}

	/**
	 * 删除值班信息
	 * */
	public TeeJson deleteDutyById(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			teePbOnDutyDao.delete(sid);
		    json.setRtState(true);
		    json.setRtMsg("删除成功");
		}else{
			json.setRtState(false);
			json.setRtMsg("删除失败");
		}
		return json;
	}

	/**
	 * 批量删除
	 * */
	public TeeJson batchDelete(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String orgDate = request.getParameter("org");
		if(orgDate!=null && !"".equals(orgDate)){
			String[] split = orgDate.split(",");
			for(int i=0;i<split.length;i++){
				Date date1 = TeeDateUtil.format(split[i]+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
				Date date2 = TeeDateUtil.format(split[i]+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
				teePbOnDutyDao.deleteOrUpdateByQuery("delete from TeePbOnDuty where creTime>=? and creTime<=?", new Object[]{date1,date2});
			}
			json.setRtState(true);
			json.setRtMsg("删除成功");
		}else{
			json.setRtState(false);
			json.setRtMsg("删除失败");
		}
		return json;
	}
	
	/**
	 * 导入值班信息
	 * @param request
	 * @return
	 */
	public TeeJson importHumanDocInfo(HttpServletRequest request) {
		TeePerson person =(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		StringBuffer sb = new StringBuffer("[");
		String infoStr= "";
		int importCount=0;//成功导入的记录数
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		InputStream ins=null;
		TeeJson json = new TeeJson();
		Workbook wb = null;  
		try{
			MultipartFile  file = multipartRequest.getFile("excelFile");
			ins = file.getInputStream();
			wb = Workbook.getWorkbook(ins);
			if(wb==null){
				json.setRtState(false);
				json.setRtMsg("上传文件错误！");
			}
			Sheet[] sheet = wb.getSheets();  
	        if (sheet != null && sheet.length > 0) {  
	            // 对每个工作表进行循环  
	            for (int i = 0; i < sheet.length; i++) {  
	                // 得到当前工作表的行数  
	                int rowNum = sheet[i].getRows(); 
//                    if(sheet[i].getColumns()!=8){
//                    	json.setRtState(false);
//                    	json.setRtMsg("你导入的文件不正确，请下载模板，按模板填写内容");
//                    	break;
//                    }
	                for (int j = 2; j < rowNum; j++) {
	                	String uesrName = "";
	                	int m=3;
	                	int n=0;
	                	TeePbOnDutyModel model = new TeePbOnDutyModel();
	                	Map<String,String> map=new HashMap<String,String>();
	                    // 得到当前行的所有单元格  
	                    Cell[] cells = sheet[i].getRow(j); 
	                    if (cells != null && cells.length > 0) { 
	                        // 对每个单元格进行循环  
	                        for (int k = 0; k < cells.length; k++) {
	                            String cellValue = cells[k].getContents(); 
	                            if(cellValue!=null && cellValue!=""){
	                            	cellValue = cellValue.replace(" ", "");
		                            switch(k){
		                            	case 0:
		                            		cellValue = cellValue.replace("\"", "");
		                            	String time = TeeDateUtil.format(TeeDateUtil.format(cellValue, "yyyy年MM月dd日"), "yyyy-MM-dd");
		                            	model.setCreTime(time);
		                            	break;
		                            	case 1:
		                            	break;
		                            	case 2:
		                            	model.setBzUserName(cellValue);
		                            	uesrName+=cellValue+",";
		                            	break;
		                            	case 3:
			                            model.setBdUserName(cellValue);
			                            uesrName+=cellValue+",";
			                            break;
		                            	case 4:
				                        model.setYzUserName(cellValue);
				                        uesrName+=cellValue+",";
				                        break;
		                            	case 5:
					                    model.setYdUserName(cellValue);
					                    uesrName+=cellValue+",";
					                    break;
		                            	case 6:
					                    model.setZdUserName(cellValue);
					                    uesrName+=cellValue+",";
					                    break;
//		                            	case 7:
//					                    model.setMobileNo(cellValue);
//					                    break;
		                            }
	                            }
	                        } 
	                    } 
	                    String color = "red";//显示导入信息的返回信息的 颜色
	                    boolean flag = true;
	                    if(uesrName.endsWith(",")){
	                    	uesrName = uesrName.substring(0,uesrName.length()-1);
	        	    	}
	                    String[] split3 = uesrName.split(",");
	                   for(int l=0;l<split3.length;l++){
	                    	split3[l] = split3[l].replace(" ", "");
	                    	List<TeePerson> find2 = null;
	                    	if(split3[l].contains("二队")){
	                    		find2 = personDao.find("from TeePerson where userName=? and dept.uuid=12", new Object[]{"王辉"}); 
	                    	}else if(split3[l].contains("法制")){
	                    		find2 = personDao.find("from TeePerson where userName=? and dept.uuid=6", new Object[]{"王辉"}); 
	                    	}else{
	                    		find2 = personDao.find("from TeePerson where userName=?", new Object[]{split3[l]});
	                    	}
	                        if(find2==null || find2.size()==0){
		                    	//判断是否是OA用户
	                        	infoStr = "导入失败,用户不存在！";
	                        	getPersonInfoStr(sb, model, infoStr, color);
	                        	//flag = true;
	                        	continue;
		                    }else{
		                    	flag = false;
		                    	if(find2.size()!=1){
		                    		find2.set(0, personDao.selectPersonById(242));//设置一个账号为“人名重复”的账号
		                    	}
		                    	//用户名是空
		                    	if(model.getBzUserName().equals(split3[l])){
		                    		model.setBzUserId(find2.get(0).getUuid());
		                    	}
		                    	if(model.getBdUserName().equals(split3[l])){
		                    		model.setBdUserId(find2.get(0).getUuid());
		                    	}
		                    	if(model.getYzUserName().equals(split3[l])){
		                    		model.setYzUserId(find2.get(0).getUuid());
		                    	}
		                    	if(model.getYdUserName().equals(split3[l])){
		                    		model.setYdUserId(find2.get(0).getUuid());
		                    	}
		                    	if(model.getZdUserName().equals(split3[l])){
		                    		model.setZdUserId(find2.get(0).getUuid());
		                    	}
		                    }
	                    }
	                    if(flag){
	                    	continue;
	                    }
                        
                        //保存对象  e
	                    addDuty(person,model);
	                    importCount++;
	                }
	              
	                if(!sb.equals("[")){
	                	//System.out.println("进来了");
	        			sb.deleteCharAt(sb.length() -1);
	        		}
	        		sb.append("]");
	        		//String data = sb.toString();
	        		//List<Map<String, String>> list =  TeeJsonUtil.JsonStr2MapList(data);
	        		//json.setRtData(list);
	                json.setRtState(true);
	                json.setRtMsg(importCount+"");
	            }
	        }
	        wb.close();  
		}catch(Exception ex){
			ex.printStackTrace();
			json.setRtMsg("文件格式不对，请按模板进行内容填写");
			json.setRtState(false);
			
		}
		return json;
	}
	
	/**
	 * 添加值班排班
	 * @param model 
	 * */
	private String addDuty(TeePerson person,TeePbOnDutyModel model) {
		
		int bzUserId = model.getBzUserId();
		int bdUserId = model.getBdUserId();
		int yzUserId = model.getYzUserId();
		int ydUserId = model.getYdUserId();
		int zdUserId = model.getZdUserId();
		String mobileNo = model.getMobileNo();
		String creTime = model.getCreTime();
		String userIds = "";
		if(creTime==""){
			return "";
		}
		TeePbOnDuty d=new TeePbOnDuty();
		d.setCreTime(TeeDateUtil.format(creTime, "yyyy-MM-dd"));//日期
		
		if(bzUserId>0){
			TeePerson bzUser=new TeePerson();
			bzUser.setUuid(bzUserId);
			d.setBzUser(bzUser);
			userIds+=bzUserId+",";
		}else{
			d.setBzUser(null);
		}
		if(bdUserId>0){
			TeePerson bdUser=new TeePerson();
			bdUser.setUuid(bdUserId);
			d.setBdUser(bdUser);
			userIds+=bdUserId+",";
		}else{
			d.setBdUser(null);
		}
		if(yzUserId>0){
			TeePerson yzUser=new TeePerson();
			yzUser.setUuid(yzUserId);
			d.setYzUser(yzUser);
			userIds+=yzUserId+",";
		}else{
			d.setYzUser(null);
		}
		if(ydUserId>0){
			TeePerson ydUser=new TeePerson();
			ydUser.setUuid(ydUserId);
			d.setYdUser(ydUser);
			userIds+=ydUserId+",";
		}else{
			d.setYdUser(null);
		}
		if(zdUserId>0){
			TeePerson zdUser=new TeePerson();
			zdUser.setUuid(zdUserId);
			d.setZdUser(zdUser);
			//userIds+=zdUserId+",";
		}else{
			d.setZdUser(null);
		}
		
		d.setMobileNo(mobileNo);
		d.setAddUser(person);
		List<TeePbOnDuty> find2 = teePbOnDutyDao.find("from TeePbOnDuty where creTime=?", new Object[]{TeeDateUtil.format(creTime, "yyyy-MM-dd")});
		if(find2!=null && find2.size()>0){
			TeePbOnDuty duty = find2.get(0);
			teePbOnDutyDao.delete(duty.getSid());
		}
		Serializable save = teePbOnDutyDao.save(d);
		sendPhoneSms2(person,model);
		//发送消息提醒
//		Date format = TeeDateUtil.format(creTime, "yyyy-MM-dd");
//		String time=TeeDateUtil.format(format,"yyyy年MM月");
//		String Str=person.getUserName()+"给您安排了["+time+"]值班带班安排计划,请查看！";
//		Map requestData = new HashMap();
//		if(userIds.endsWith(",")){
//			userIds = userIds.substring(0,userIds.length()-1);
//    	}
//		if(userIds!=null && !"".equals(userIds)){//值班人员
//			String[] split1 = userIds.split(",");
//			List<String> split2 = new ArrayList<String>();
//			for(int i=0;i<split1.length;i++){
//				boolean flag = true;
//				for(int j=i+1;j<split1.length;j++){
//					if(split1[i].equals(split1[j])){
//						flag = false;
//						break;
//					}
//				}
//				if(flag){
//					split2.add(split1[i]);
//				}
//			}
//			for(int j=0;j<split2.size();j++){
//				int userId = TeeStringUtil.getInteger(split2.get(j), 0);
//				requestData.put("moduleNo", "093");
//				requestData.put("userListIds", userId);
//				requestData.put("content", Str);
//				requestData.put("remindUrl", "/system/core/base/onduty/myManager.jsp");
//				smsSender.sendSms(requestData, person);
//			}
//			sendPhoneSms(split2,person,creTime);
//			sendPhoneSms2(person,model);
//		}
		return "";
	}

	//发短信
	public void sendPhoneSms(List<String> list,TeePerson person,String creTime){
		Date format = TeeDateUtil.format(creTime, "yyyy-MM-dd");
		String time=TeeDateUtil.format(format,"yyyy年MM月");
		if(list.size() > 0){
			TeePerson user = null;
			String userPhone = null;
			for(int i=0;i<list.size();i++){
				user=(TeePerson)personDao.load(Integer.parseInt(list.get(i)));
				userPhone=user.getMobilNo();
				if(!TeeUtility.isNullorEmpty(userPhone)){
					//保存手机发送记录（本地）
					TeeSmsSendPhone sendPhone = new TeeSmsSendPhone();
					Calendar cl = Calendar.getInstance();
					try {
					    cl.setTime(new Date());
						sendPhone.setFromId(person.getUuid());
						sendPhone.setFromName(person.getUserName());
						sendPhone.setSendTime(cl);
						sendPhone.setSendFlag(0);
						String content=person.getUserName()+"给您安排了["+time+"]值班带班安排计划,请在OA系统上查看！";						sendPhone.setContent(content);
						sendPhone.setToId(user.getUuid());
						sendPhone.setToName(user.getUserName());
						sendPhone.setPhone(userPhone);
						sendPhoneDao.addSendPhoneInfo(sendPhone);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	//发短信
	public void sendPhoneSms2(TeePerson person,TeePbOnDutyModel model){
		Map requestData = new HashMap();
		Calendar cl = Calendar.getInstance();
	    cl.setTime(TeeDateUtil.format(model.getCreTime()+" 08:00:00"));
		cl.add(Calendar.DAY_OF_MONTH, -1);
		//白班值
		int bzUserId = model.getBzUserId();
		if(bzUserId>0){
			addSmsPhone(person,bzUserId,"白班值",model.getCreTime());
		}
		//白班带
		int bdUserId = model.getBdUserId();
		if(bdUserId>0){
			addSmsPhone(person,bdUserId,"白班带",model.getCreTime());
		}
		//夜班值
		int yzUserId = model.getYzUserId();
		if(yzUserId>0){
			addSmsPhone(person,yzUserId,"夜班值",model.getCreTime());
		}
		//夜班带
		int ydUserId = model.getYdUserId();
		if(ydUserId>0){
			addSmsPhone(person,ydUserId,"夜班带",model.getCreTime());
		}
	}
	
	public void addSmsPhone(TeePerson person,int userId,String desc,String creTime){
			TeePerson user = null;
			String userPhone = null;
			user=(TeePerson)personDao.load(userId);
			userPhone=user.getMobilNo();
			if(!TeeUtility.isNullorEmpty(userPhone)){
				//保存手机发送记录（本地）
				TeeSmsSendPhone sendPhone = new TeeSmsSendPhone();
				Calendar cl = Calendar.getInstance();
				try {
					cl.setTime(TeeDateUtil.format(creTime+" 08:00:00"));
					cl.add(Calendar.DAY_OF_MONTH, -1);
					//Date date = cl.getTime();
					//cl.setTime(new Date());
					sendPhone.setFromId(person.getUuid());
					sendPhone.setFromName(person.getUserName());
					sendPhone.setSendTime(cl);
					sendPhone.setSendFlag(0);
					String content="您明天("+creTime+")有一个'"+desc+"'班安排,请确认。";						
					sendPhone.setContent(content);
					sendPhone.setToId(user.getUuid());
					sendPhone.setToName(user.getUserName());
					sendPhone.setPhone(userPhone);
					sendPhoneDao.addSendPhoneInfo(sendPhone);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}
	
	private StringBuffer getPersonInfoStr(StringBuffer sb, TeePbOnDutyModel model,
			String infoStr, String color) {
		
		sb.append("{");
        sb.append("creTime:\"" + model.getCreTime() + "\"");
        sb.append(",bzUserName:\"" + model.getBzUserName() + "\"");
        sb.append(",bdUserName:\"" + model.getBdUserName() + "\"");
        sb.append(",yzUserName:\"" + model.getYzUserName() + "\"");
        sb.append(",ydUserName:\"" + model.getYdUserName() + "\"");
        sb.append(",zdUserName:\"" + model.getZdUserName() + "\"");
        sb.append(",mobileNo:\"" + model.getMobileNo() + "\"");
        sb.append(",color:\"" + color + "\"");
        sb.append(",info:\"" + infoStr + "\"");
        sb.append("},");
        return sb;
	}
	
	/**
	 * 导出值班信息
	 * @param request
	 * @return
	 */
	public List<TeePbOnDuty> exportHumanDocInfo(HttpServletRequest request,HttpServletResponse response) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		int totalOfMonthDays = TeeStringUtil.getInteger(request.getParameter("totalOfMonthDays"),0);
		String str1=year+"-"+month+"-01";
		String str2=year+"-"+month+"-"+totalOfMonthDays;
		String hql="from TeePbOnDuty where creTime>=? and creTime<=? ";
		
		List<TeePbOnDuty> find = teePbOnDutyDao.find(hql, new Object[]{TeeStringUtil.getDate(str1, "yyyy-MM-dd"),TeeStringUtil.getDate(str2, "yyyy-MM-dd")});
		
		return find;
	}

	/**
	 * 导出值班信息
	 * @param request
	 * @return
	 */
	/*public ArrayList<TeeDataRecord> exportHumanDocInfo(HttpServletRequest request,HttpServletResponse response) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		int totalOfMonthDays = TeeStringUtil.getInteger(request.getParameter("totalOfMonthDays"),0);
		String str1=year+"-"+month+"-01";
		String str2=year+"-"+month+"-"+totalOfMonthDays;
		String hql="from TeePbOnDuty where creTime>=? and creTime<=? ";
		
		List<TeePbOnDuty> find = teePbOnDutyDao.find(hql, new Object[]{TeeStringUtil.getDate(str1, "yyyy-MM-dd"),TeeStringUtil.getDate(str2, "yyyy-MM-dd")});
		
		ArrayList<TeeDataRecord> list = new ArrayList<TeeDataRecord>();
		
	    for (int i = 0; i < find.size(); i++) {
	    	TeePbOnDuty duty = find.get(i);
	        TeeDataRecord dbrec = new TeeDataRecord();
	        dbrec.addField("日期", sdf.format(duty.getCreTime()));
	        if(duty.getBzUser()!=null){
	        	dbrec.addField("白班值", duty.getBzUser().getUserId());
	        }else{
	        	dbrec.addField("白班值", "");
	        }
	        if(duty.getBdUser()!=null){
	        	dbrec.addField("白班带", duty.getBdUser().getUserId());
	        }else{
	        	dbrec.addField("白班带", "");
	        }
	        if(duty.getYzUser()!=null){
	        	dbrec.addField("夜班值", duty.getYzUser().getUserId());
	        }else{
	        	dbrec.addField("夜班值", "");
	        }
	        if(duty.getYdUser()!=null){
	        	dbrec.addField("夜班带", duty.getYdUser().getUserId());
	        }else{
	        	dbrec.addField("夜班带", "");
	        }
	        if(duty.getZdUser()!=null){
	        	dbrec.addField("带班总队领导", duty.getZdUser().getUserId());
	        }else{
	        	dbrec.addField("带班总队领导", "");
	        }
	        if(duty.getMobileNo()!=null){
	        	dbrec.addField("电话", duty.getMobileNo());
	        }else{
	        	dbrec.addField("电话", "");
	        }
	        list.add(dbrec);
	      }
		return list;
	}*/

}
