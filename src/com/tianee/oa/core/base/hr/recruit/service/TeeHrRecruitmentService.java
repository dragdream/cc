package com.tianee.oa.core.base.hr.recruit.service;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.base.hr.TeeHrCodeManager;
import com.tianee.oa.core.base.hr.recruit.bean.TeeHrPool;
import com.tianee.oa.core.base.hr.recruit.bean.TeeHrRecruitment;
import com.tianee.oa.core.base.hr.recruit.dao.TeeHrPoolDao;
import com.tianee.oa.core.base.hr.recruit.dao.TeeHrRecruitmentDao;
import com.tianee.oa.core.base.hr.recruit.model.TeeHrRecruitmentModel;
import com.tianee.oa.core.base.hr.recruit.plan.bean.TeeRecruitPlan;
import com.tianee.oa.core.base.hr.recruit.plan.dao.TeeRecruitPlanDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunConcern;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.str.expReplace.TeeExpFetcher;
import com.tianee.webframe.util.str.expReplace.TeeRegexpAnalyser;

@Service
public class TeeHrRecruitmentService extends TeeBaseService {
	@Autowired
	private TeeHrRecruitmentDao thisObjDao;
	@Autowired
	private TeeRecruitPlanDao planDao;
	@Autowired
	private TeeHrPoolDao poolDao;
	@Autowired
	private TeePersonDao personDao;
	@Autowired
    private TeeDeptDao deptDao;
	
	public TeeJson addOrUpdateService(HttpServletRequest request, TeeHrRecruitmentModel model){

		TeeJson json = new TeeJson();
		try {
			TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			if (model.getSid() > 0) {
				TeeHrRecruitment obj = thisObjDao.get(model.getSid());
				if(obj != null){
					model.setSid(obj.getSid());
					BeanUtils.copyProperties(model, obj);
					
					
					int hrPlanId = model.getPlanId();
					TeeRecruitPlan planObj = planDao.get(hrPlanId);
					obj.setPlanObj(planObj);
					
					TeeHrPool poolObj = poolDao.get(model.getHrPoolId());
					obj.setPoolObj(poolObj);
					TeePerson recordPerson = personDao.get(model.getRecordPersonId());
					obj.setRecordPerson(recordPerson);
					if(!TeeUtility.isNullorEmpty(model.getRecordTimeStr())){
						obj.setRecordTime(TeeUtility.parseDate(model.getRecordTimeStr()));
					}
					
					int deptId = model.getDeptId();
					TeeDepartment requDept = deptDao.get(deptId);
					obj.setRequDept(requDept);
					if(!TeeUtility.isNullorEmpty(model.getOnBoardingTimeStr())){
						obj.setOnBoardingTime(TeeUtility.parseDate(model.getOnBoardingTimeStr()));
					}
					if(!TeeUtility.isNullorEmpty(model.getStartingSalaryTimeStr())){
						obj.setStartingSalaryTime(TeeUtility.parseDate(model.getStartingSalaryTimeStr()));
					}
					thisObjDao.update(obj);
				}
			}else{
				TeeHrRecruitment obj = new TeeHrRecruitment();
				BeanUtils.copyProperties(model, obj);
				
				int hrPlanId = model.getPlanId();
				TeeRecruitPlan planObj = planDao.get(hrPlanId);
				obj.setPlanObj(planObj);
				
				TeeHrPool poolObj = poolDao.get(model.getHrPoolId());
				obj.setPoolObj(poolObj);
				TeePerson recordPerson = personDao.get(model.getRecordPersonId());
				obj.setRecordPerson(recordPerson);
				if(!TeeUtility.isNullorEmpty(model.getRecordTimeStr())){
					obj.setRecordTime(TeeUtility.parseDate(model.getRecordTimeStr()));
				}
				
				int deptId = model.getDeptId();
				TeeDepartment requDept = deptDao.get(deptId);
				obj.setRequDept(requDept);
				if(!TeeUtility.isNullorEmpty(model.getOnBoardingTimeStr())){
					obj.setOnBoardingTime(TeeUtility.parseDate(model.getOnBoardingTimeStr()));
				}
				if(!TeeUtility.isNullorEmpty(model.getStartingSalaryTimeStr())){
					obj.setStartingSalaryTime(TeeUtility.parseDate(model.getStartingSalaryTimeStr()));
				}
				
				obj.setCreateTime(new Date());
				obj.setCreateUser(person);
				thisObjDao.save(obj);
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
    public TeeEasyuiDataGridJson getRecruitList(TeeDataGridModel dm,HttpServletRequest request , TeeHrRecruitmentModel model) throws ParseException {
        TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
        TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
        
        Map requestDatas = TeeServletUtility.getParamMap(request);
        
        String planName = (String)requestDatas.get("planName");
        String hrPoolName = (String)requestDatas.get("hrPoolName");
        String position = (String)requestDatas.get("position");
        String oaName = (String)requestDatas.get("oaName");
        String recordPersonId = (String)requestDatas.get("recordPersonId");
        
        String deptId = (String)requestDatas.get("deptId");
        String employeeType = (String)requestDatas.get("employeeType");
        String administrationLevel = (String)requestDatas.get("administrationLevel");
        String jobPosition = (String)requestDatas.get("jobPosition");
        String presentPosition = (String)requestDatas.get("presentPosition");
        String remark = (String)requestDatas.get("remark");
        
        
        
        
        
        
        String recordTimeStrMin = (String)requestDatas.get("recordTimeStrMin");
        String recordTimeStrMax = (String)requestDatas.get("recordTimeStrMax");
        
        String queryStr = " 1=1";
        if(!TeePersonService.checkIsSuperAdmin(loginPerson,"")){
        	queryStr = " require.createUser.uuid= " + loginPerson.getUuid();
        }
        
        String hql = "from TeeHrRecruitment require where " + queryStr;
		List param = new ArrayList();
		if(!TeeUtility.isNullorEmpty(planName)){
			hql+=" and require.planObj.planName like ?";
			param.add("%"+planName+"%");
		}
		if(!TeeUtility.isNullorEmpty(hrPoolName)){
			hql+=" and require.poolObj.employeeName like ?";
			param.add("%"+hrPoolName+"%");
		}
		if(!TeeUtility.isNullorEmpty(position)){
			hql+=" and require.position like ?";
			param.add("%"+position+"%");
		}
		if(!TeeUtility.isNullorEmpty(oaName)){
			hql+=" and require.oaName like ?";
			param.add("%"+oaName+"%");
		}
		if(!TeeUtility.isNullorEmpty(recordPersonId)){
			hql+=" and require.recordPerson.uuid = ?";
			param.add(Integer.parseInt(recordPersonId));
		}
		if(!TeeUtility.isNullorEmpty(deptId)){
			hql+=" and require.requDept.uuid = ?";
			param.add(Integer.parseInt(deptId));
		}
		if(!TeeUtility.isNullorEmpty(employeeType)){
			hql+=" and require.employeeType = ?";
			param.add(employeeType);
		}
		if(!TeeUtility.isNullorEmpty(administrationLevel)){
			hql+=" and require.administrationLevel = ?";
			param.add(administrationLevel);
		}
		if(!TeeUtility.isNullorEmpty(jobPosition)){
			hql+=" and require.jobPosition = ?";
			param.add(jobPosition);
		}
		if(!TeeUtility.isNullorEmpty(presentPosition)){
			hql+=" and require.presentPosition = ?";
			param.add(presentPosition);
		}
		if(!TeeUtility.isNullorEmpty(remark)){
			hql+=" and require.remark like ?";
			param.add("%"+remark+"%");
		}
		
		
		
		
		
		if(!TeeUtility.isNullorEmpty(recordTimeStrMin)){
			hql += " and require.recordTime >= ?";
			param.add(TeeDateUtil.parseDate("yyyy-MM-dd hh:mm:ss", recordTimeStrMin+" 00:00:00"));
		}
		if(!TeeUtility.isNullorEmpty(recordTimeStrMax)){
			hql += " and require.recordTime <= ?";
			param.add(TeeDateUtil.parseDate("yyyy-MM-dd hh:mm:ss", recordTimeStrMax+" 23:59:59"));
		}
        
		j.setTotal(thisObjDao.countByList("select count(*) "+hql, param));// 设置总记录数
        
		hql+=" order by require.createTime desc";
        
        int firstIndex = 0;
        firstIndex = (dm.getPage()-1) * dm.getRows() ;//获取开始索引位置
//        List<TeeHrRecruitment> list = thisObjDao.getMeetPageFind(firstIndex, dm.getRows(), dm, model);// 查
        List<TeeHrRecruitment> list = thisObjDao.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);// 查
        
        
        List<TeeHrRecruitmentModel> modelList = new ArrayList<TeeHrRecruitmentModel>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
            	TeeHrRecruitmentModel modeltemp = parseModel(list.get(i));
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
    public TeeHrRecruitmentModel parseModel(TeeHrRecruitment obj) {
    	TeeHrRecruitmentModel model = new TeeHrRecruitmentModel();
        if (obj == null) {
            return model;
        }
        BeanUtils.copyProperties(obj, model);
        if (!TeeUtility.isNullorEmpty(obj.getCreateTime())) {
            model.setCreateTimeStr(TeeUtility.getDateStrByFormat(obj.getCreateTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
        }
        
        if (obj.getCreateUser() != null) {
            model.setCreateUserId(obj.getCreateUser().getUuid());
            model.setCreateUserName(obj.getCreateUser().getUserName());
        }
        if(!TeeUtility.isNullorEmpty(obj.getRequDept())){
        	model.setDeptId(obj.getRequDept().getUuid());
        	model.setDeptName(obj.getRequDept().getDeptName());
        }else{
        	model.setDeptName("");
        }
        
        if(obj.getPlanObj() != null){
        	model.setPlanId(obj.getPlanObj().getSid());
        	model.setPlanName(obj.getPlanObj().getPlanName());
        }
        if(obj.getPoolObj() != null){
        	model.setHrPoolId(obj.getPoolObj().getSid());
        	model.setHrPoolName(obj.getPoolObj().getEmployeeName());
        }
        if(obj.getRecordPerson() != null){
        	model.setRecordPersonId(obj.getRecordPerson().getUuid());
        	model.setRecordPersonName(obj.getRecordPerson().getUserName());
        }
        
        if (!TeeUtility.isNullorEmpty(obj.getRecordTime())) {
            model.setRecordTimeStr(TeeUtility.getDateStrByFormat(obj.getRecordTime(), new SimpleDateFormat("yyyy-MM-dd")));
        }
        if (!TeeUtility.isNullorEmpty(obj.getOnBoardingTime())) {
            model.setOnBoardingTimeStr(TeeUtility.getDateStrByFormat(obj.getOnBoardingTime(), new SimpleDateFormat("yyyy-MM-dd")));
        }
        if (!TeeUtility.isNullorEmpty(obj.getStartingSalaryTime())) {
            model.setStartingSalaryTimeStr(TeeUtility.getDateStrByFormat(obj.getStartingSalaryTime(), new SimpleDateFormat("yyyy-MM-dd")));
        }
        String employeeTypeName = "";
        if(!TeeUtility.isNullorEmpty(obj.getEmployeeType())){// 员工类型
        	employeeTypeName = TeeHrCodeManager.getChildSysCodeNameCodeNo("STAFF_OCCUPATION", obj.getEmployeeType());
        }
        model.setEmployeeTypeName(employeeTypeName);
        String presentPositionName = "";
        if(!TeeUtility.isNullorEmpty(obj.getPresentPosition())){//职称
        	presentPositionName = TeeHrCodeManager.getChildSysCodeNameCodeNo("PRESENT_POSITION", obj.getPresentPosition());
        }
        model.setPresentPositionName(presentPositionName);
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
    public TeeJson getInfoByIdService(HttpServletRequest request, TeeHrRecruitmentModel model) {
        TeeJson json = new TeeJson();
        if (model.getSid() > 0) {
        	TeeHrRecruitment out = thisObjDao.get(model.getSid());
            if (out != null) {
                model = parseModel(out);
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
    	
    	thisObjDao.delByIds(sids);
    	json.setRtState(true);
        json.setRtMsg("删除成功!");
    	return json;
    }


    
    /**
     * 导出Excel表格
     * @param request
     * @param response
     */
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String para=request.getParameter("param");
		Map params = TeeJsonUtil.JsonStr2Map(para);
		
		List l = getResultList(params, loginUser);
		List<String> head=(List<String>) l.get(0);
		List<Map> resultList=(List<Map>) l.get(1);
		//获取当前时间
		Calendar c = Calendar.getInstance(); 
		String time="["+ c.get(Calendar.YEAR)+(c.get(Calendar.MONTH) + 1)+ c.get(Calendar.DAY_OF_MONTH)+c.get(Calendar.HOUR_OF_DAY)+c.get(Calendar.MINUTE)+"]";
		
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("招聘录用查询列表信息");
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow((int) 0);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFFont font = wb.createFont();
			font.setFontHeightInPoints((short) 12); // 字体高度
			font.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
			font.setFontName("宋体"); // 字体
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
			font.setItalic(false); // 是否使用斜体
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			style.setFont(font);
			
			HSSFFont font1 = wb.createFont();
			font1.setFontHeightInPoints((short) 12); // 字体高度
			font1.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
			font1.setFontName("宋体"); // 字体
			font1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); // 宽度
			font1.setItalic(false); // 是否使用斜体
			HSSFCellStyle style1 = wb.createCellStyle();
			style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			style1.setFont(font1);
			
			HSSFCell cell = row.createCell((short) 0);
			// 设置表头
			for (int i=0;i<head.size();i++) {
				cell.setCellValue( TeeStringUtil.getString(head.get(i)));
				cell.setCellStyle(style);
				cell = row.createCell((short) (i + 1));
			}
			// 设置内容
			for (int m = 0; m < resultList.size(); m++) {
				HSSFRow r = sheet.createRow((int) (m + 1));
			   
			    for(int n=0;n<head.size();n++){
			    	cell = r.createCell((short) (n));	
			    	cell.setCellValue(TeeStringUtil.getString(resultList.get(m).get(head.get(n))));
					cell.setCellStyle(style1);	
			    }

			}

			// 设置每一列的宽度
			sheet.setDefaultColumnWidth(10);
			String fileName = "列表信息"+time+".xls";
			response.setHeader("Content-disposition", "attachment;filename="
					+ URLEncoder.encode(fileName, "UTF-8"));
			response.setContentType("application/msexcel;charset=UTF-8");
			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	
	//导出查询
	public List getResultList(Map requestDatas, TeePerson loginPerson) {
		List returnList = new ArrayList();//最终返回的集合
		List<String> head=new ArrayList<String>();//表头集合
		head.add("应聘者姓名");
		head.add("招聘岗位");
		head.add("OA中用户名");
		head.add("录入日期");
		head.add("员工类型");
		head.add("行政等级");
		head.add("招聘部门");
		head.add("职务");
		head.add("职称");
		head.add("正式入职时间");
		head.add("正式起薪时间");
		
	        
        String planName = (String)requestDatas.get("planName");
        String hrPoolName = (String)requestDatas.get("hrPoolName");
        String position = (String)requestDatas.get("position");
        String oaName = (String)requestDatas.get("oaName");
        String recordPersonId = (String)requestDatas.get("recordPersonId");
        
        String deptId = (String)requestDatas.get("deptId");
        String employeeType = (String)requestDatas.get("employeeType");
        String administrationLevel = (String)requestDatas.get("administrationLevel");
        String jobPosition = (String)requestDatas.get("jobPosition");
        String presentPosition = (String)requestDatas.get("presentPosition");
        String remark = (String)requestDatas.get("remark");
        
        
        
        
        
        
        String recordTimeStrMin = (String)requestDatas.get("recordTimeStrMin");
        String recordTimeStrMax = (String)requestDatas.get("recordTimeStrMax");
        
        String queryStr = " 1=1";
        if(!TeePersonService.checkIsSuperAdmin(loginPerson,"")){
        	queryStr = " require.createUser.uuid= " + loginPerson.getUuid();
        }
        
        String hql = "from TeeHrRecruitment require where " + queryStr;
		List param = new ArrayList();
		if(!TeeUtility.isNullorEmpty(planName)){
			hql+=" and require.planObj.planName like ?";
			param.add("%"+planName+"%");
		}
		if(!TeeUtility.isNullorEmpty(hrPoolName)){
			hql+=" and require.poolObj.employeeName like ?";
			param.add("%"+hrPoolName+"%");
		}
		if(!TeeUtility.isNullorEmpty(position)){
			hql+=" and require.position like ?";
			param.add("%"+position+"%");
		}
		if(!TeeUtility.isNullorEmpty(oaName)){
			hql+=" and require.oaName like ?";
			param.add("%"+oaName+"%");
		}
		if(!TeeUtility.isNullorEmpty(recordPersonId)){
			hql+=" and require.recordPerson.uuid = ?";
			param.add(Integer.parseInt(recordPersonId));
		}
		if(!TeeUtility.isNullorEmpty(deptId)){
			hql+=" and require.requDept.uuid = ?";
			param.add(Integer.parseInt(deptId));
		}
		if(!TeeUtility.isNullorEmpty(employeeType)){
			hql+=" and require.employeeType = ?";
			param.add(employeeType);
		}
		if(!TeeUtility.isNullorEmpty(administrationLevel)){
			hql+=" and require.administrationLevel = ?";
			param.add(administrationLevel);
		}
		if(!TeeUtility.isNullorEmpty(jobPosition)){
			hql+=" and require.jobPosition = ?";
			param.add(jobPosition);
		}
		if(!TeeUtility.isNullorEmpty(presentPosition)){
			hql+=" and require.presentPosition = ?";
			param.add(presentPosition);
		}
		if(!TeeUtility.isNullorEmpty(remark)){
			hql+=" and require.remark like ?";
			param.add("%"+remark+"%");
		}
		

		try {
			if(!TeeUtility.isNullorEmpty(recordTimeStrMin)){
				hql += " and require.recordTime >= ?";
				param.add(TeeDateUtil.parseDate("yyyy-MM-dd hh:mm:ss", recordTimeStrMin+" 00:00:00"));
			}
			if(!TeeUtility.isNullorEmpty(recordTimeStrMax)){
				hql += " and require.recordTime <= ?";
				param.add(TeeDateUtil.parseDate("yyyy-MM-dd hh:mm:ss", recordTimeStrMax+" 23:59:59"));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		hql+=" order by require.createTime desc";
        
        List<TeeHrRecruitment> list = thisObjDao.executeQuery(hql, param.toArray());
   
        List<Map> modelList = new ArrayList<Map>();
        Map  map=null;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
            	
            	map=new HashMap();
            	TeeHrRecruitmentModel modeltemp = parseModel(list.get(i));
            	map.put("应聘者姓名", modeltemp.getHrPoolName());
            	map.put("招聘岗位", modeltemp.getPosition());
            	map.put("OA中用户名", modeltemp.getOaName());
            	map.put("录入日期", modeltemp.getRecordTimeStr());
            	
            	map.put("员工类型", modeltemp.getEmployeeTypeName());
            	map.put("行政等级", modeltemp.getAdministrationLevel());
            	map.put("招聘部门", modeltemp.getDeptName());
            	map.put("职务", modeltemp.getJobPosition());
            	map.put("职称", modeltemp.getPresentPositionName());
            	map.put("正式入职时间", modeltemp.getOnBoardingTimeStr());
            	map.put("正式起薪时间", modeltemp.getStartingSalaryTimeStr());
            	
                modelList.add(map);
            }
        }
       
		returnList.add(head);
		returnList.add(modelList);
		
        return returnList;	
	}

}
