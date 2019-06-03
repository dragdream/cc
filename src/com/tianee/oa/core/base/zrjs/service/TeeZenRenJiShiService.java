package com.tianee.oa.core.base.zrjs.service;

import java.io.OutputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.base.zrjs.bean.TeeZenRenJiShi;
import com.tianee.oa.core.base.zrjs.dao.TeeZenRenJiShiDao;
import com.tianee.oa.core.base.zrjs.model.TeeZenRenJiShiModel;
import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeZenRenJiShiService extends TeeBaseService{

	@Autowired
	private TeeZenRenJiShiDao teeZenRenJiShiDao;
	
	@Autowired
	private TeeSimpleDaoSupport teeSimpleDaoSupport;
	
	@Autowired
	private TeeSmsSender smsSender;

	/**
	 * 添加修改责任纪实
	 * */
	public TeeJson addOrUpdate(HttpServletRequest request,
			TeeZenRenJiShiModel model) {
		TeeJson json=new TeeJson();
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		if(model.getSid()>0){//修改
			TeeZenRenJiShi jh = teeZenRenJiShiDao.get(model.getSid());
		    jh.setAddress(model.getAddress());//地址
		    //jh.setAttachIds(model.getAttachIds());//附件id字符串
		    jh.setContent(model.getContent());//内容
		    jh.setDaclContent(model.getDaclContent());//材料内容
		    TeeDepartment dept = (TeeDepartment)teeSimpleDaoSupport.get(TeeDepartment.class, model.getDeptId());
		    jh.setDept(dept);//部门
		    jh.setJhEndTime(TeeDateUtil.format(model.getJhEndTime(),"yyyy-MM-dd HH:mm"));//计划结束时间
		    jh.setLeaderQm(model.getLeaderQm());//领导签名
		    jh.setRease(model.getRease());//摘要
		    jh.setSjEndTime(TeeDateUtil.format(model.getSjEndTime(),"yyyy-MM-dd HH:mm"));//时间结束时间
		    jh.setStatus(model.getStatus());//是否已完成
		    TeePerson person=(TeePerson) simpleDaoSupport.get(TeePerson.class, model.getUserId());
		    jh.setUser(person);//当事人
		    jh.setZrSxName(model.getZrSxName());//责任事项
		    jh.setCreateUser(loginUser);
		    teeZenRenJiShiDao.update(jh);//修改
		    String attachIds = model.getAttachIds();
			if(attachIds!=null && !"".equals(attachIds)){
				List<TeeAttachment> find = simpleDaoSupport.find("from TeeAttachment where sid in ("+attachIds+")", null);
				if(find!=null && find.size()>0){
					for(TeeAttachment att:find){
						att.setModel(TeeAttachmentModelKeys.zenRenJishi);
						att.setModelId(model.getSid()+"");
						simpleDaoSupport.update(att);
					}
				}
			}
		}else{//添加
			TeeZenRenJiShi jh = new TeeZenRenJiShi();
			jh.setAddress(model.getAddress());//地址
			//jh.setAttachIds(model.getAttachIds());//附件id字符串
			
			jh.setContent(model.getContent());//内容
			jh.setDaclContent(model.getDaclContent());//材料内容
			TeeDepartment dept = (TeeDepartment)teeSimpleDaoSupport.get(TeeDepartment.class, model.getDeptId());
			jh.setDept(dept);//部门
			jh.setJhEndTime(TeeDateUtil.format(model.getJhEndTime(),"yyyy-MM-dd HH:mm"));//计划结束时间
			jh.setLeaderQm(model.getLeaderQm());//领导签名
			jh.setRease(model.getRease());//摘要
			jh.setSjEndTime(TeeDateUtil.format(model.getSjEndTime(),"yyyy-MM-dd HH:mm"));//时间结束时间
			jh.setStatus(model.getStatus());//是否已完成
			TeePerson person=(TeePerson) simpleDaoSupport.get(TeePerson.class, model.getUserId());
			jh.setUser(person);//当事人
		    jh.setZrSxName(model.getZrSxName());//责任事项
		    jh.setCreateUser(loginUser);
		    Serializable save = teeZenRenJiShiDao.save(jh);
		    String jhId = TeeStringUtil.getString(save);
		    String attachIds = model.getAttachIds();
			if(attachIds!=null && !"".equals(attachIds)){
				List<TeeAttachment> find = simpleDaoSupport.find("from TeeAttachment where sid in ("+attachIds+")", null);
				if(find!=null && find.size()>0){
					for(TeeAttachment att:find){
						att.setModel(TeeAttachmentModelKeys.zenRenJishi);
						att.setModelId(jhId);
						simpleDaoSupport.update(att);
					}
				}
			}
		}
		json.setRtState(true);
		return json;
	}

	/**
	 * 查询责任纪实
	 * */
	public TeeJson findZenRenJiShi(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String sidStr = request.getParameter("sid");
		int sid=TeeStringUtil.getInteger(sidStr,0);
		TeeZenRenJiShi jh = teeZenRenJiShiDao.get(sid);
		TeeZenRenJiShiModel model=new TeeZenRenJiShiModel();
		if(jh!=null){
			model.setAddress(jh.getAddress());//地址
			//model.setAttachIds(jh.getAttachIds());//附件id字符串
			List<TeeAttachmentModel> attach = new ArrayList<TeeAttachmentModel>();
			List<TeeAttachment> find = simpleDaoSupport.find("from TeeAttachment where model=? and modelId=?",new Object[]{TeeAttachmentModelKeys.zenRenJishi,sidStr});
			if(find!=null && find.size()>0){
				for(TeeAttachment att:find){
					TeeAttachmentModel m=new TeeAttachmentModel();
					m.setSid(att.getSid());
					m.setExt(att.getExt());
					m.setFileName(att.getFileName());
					m.setPriv(3);
					attach.add(m);
				}
			}
			model.setContent(jh.getContent());//内容
			model.setDaclContent(jh.getDaclContent());//材料内容
			model.setDeptId(jh.getDept().getUuid());
			model.setDeptName(jh.getDept().getDeptName());
			model.setJhEndTime(TeeDateUtil.format(jh.getJhEndTime(), "yyyy-MM-dd HH:mm"));//计划结束时间
			model.setLeaderQm(jh.getLeaderQm());//领导签名
			model.setRease(jh.getRease());//摘要
			model.setSjEndTime(TeeDateUtil.format(jh.getSjEndTime(),"yyyy-MM-dd HH:mm"));//时间结束时间
			model.setStatus(jh.getStatus());//是否已完成
			model.setUserId(jh.getUser().getUuid());
			model.setUserName(jh.getUser().getUserName());
			model.setZrSxName(jh.getZrSxName());//责任事项
			model.setSid(jh.getSid());
			model.setAttach(attach);
			TeeDepartment dept = jh.getCreateUser().getDept();
			if(dept!=null){
				model.setLeaderId(dept.getManager2());
			}
		}
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}

	/**
	 * 查看责任纪实 0：查看所有的责任纪实，1：查看分管领导所能查看的责任纪实，2：查看当前登陆人所创建的责任纪实
	 * */
	public TeeEasyuiDataGridJson ZenREnJiShiList(HttpServletRequest request,
			TeeDataGridModel m) {
		TeeEasyuiDataGridJson easyJson=new TeeEasyuiDataGridJson();
		List<TeeZenRenJiShiModel> listModel=new ArrayList<TeeZenRenJiShiModel>();
		TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int state=TeeStringUtil.getInteger(request.getParameter("state"),0);
		String content = request.getParameter("content");//内容主题
		String rease = request.getParameter("rease");//摘要
		String userId=request.getParameter("userId");//责任人
		String deptId=request.getParameter("deptId");//部门
		String status=request.getParameter("status");//是否已完成
		String zrSxName = request.getParameter("zrSxName");//责任事项
		String hql="from TeeZenRenJiShi where 1=1 ";
		List<Object> obj=new ArrayList<Object>();
		if(state==1){//查看分管领导所能查看的责任纪实
			List<TeeUserRole> userRoleOther = person.getUserRoleOther();
			List<String> list=new ArrayList<String>();
			if(userRoleOther!=null && userRoleOther.size()>0){
				for(TeeUserRole r:userRoleOther){
					list.add(r.getRoleName());
				}
			}
			if(list.contains("责任总队分管领导")){
				hql+="and dept.deptName=? and createUser.uuid!=? ";
				obj.add(person.getDept().getDeptName());
				obj.add(person.getUuid());
			}else{
				hql+="and createUser.dept.manager2=? ";
				obj.add(person.getUuid()+"");
			}
		}else if(state==2){//查看当前登陆人所创建的责任纪实
			hql+="and createUser.uuid=? ";
			obj.add(person.getUuid());
		}else{//所有的
			
		}
		if(content!=null && !"".equals(content)){//内容主题
			hql+="and content like '%"+content+"%' ";
			//obj.add(content);
		}
		if(rease!=null && !"".equals(rease)){//摘要
			hql+="and rease like '%"+rease+"%' ";
			//obj.add();
		}
		if(userId!=null && !"".equals(userId)){//责任人
			hql+="and user.uuid=? ";
			obj.add(TeeStringUtil.getInteger(userId, 0));
		}
		if(deptId!=null && !"".equals(deptId)){//部门
			hql+="and dept.uuid=? ";
			obj.add(TeeStringUtil.getInteger(deptId, 0));
		}
		if(status!=null && !"".equals(status)){//是否已完成，0否1是
			hql+="and status=? ";
			obj.add(TeeStringUtil.getInteger(status, 0));
		}
		if(zrSxName!=null && !"".equals(zrSxName)){
			hql+="and zrSxName=? ";
			obj.add(zrSxName);
		}
		String hql2="order by sid desc";
		List<TeeZenRenJiShi> list = teeZenRenJiShiDao.pageFindByList(hql+hql2, m.getFirstResult(), m.getRows(), obj);
		Long count = teeZenRenJiShiDao.countByList("select count(*) "+hql, obj);
		if(list!=null && list.size()>0){
			for(TeeZenRenJiShi jh:list){
				TeeZenRenJiShiModel model=new TeeZenRenJiShiModel();
				model.setAddress(jh.getAddress());//地址
				model.setAttachIds(jh.getAttachIds());//附件id字符串
				model.setContent(jh.getContent());//内容
				model.setDaclContent(jh.getDaclContent());//材料内容
				model.setDeptId(jh.getDept().getUuid());
				model.setDeptName(jh.getDept().getDeptName());
				model.setJhEndTime(TeeDateUtil.format(jh.getJhEndTime(), "yyyy-MM-dd HH:mm"));//计划结束时间
				model.setLeaderQm(jh.getLeaderQm());//领导签名
				model.setRease(jh.getRease());//摘要
				model.setSjEndTime(TeeDateUtil.format(jh.getSjEndTime(),"yyyy-MM-dd HH:mm"));//时间结束时间
				model.setStatus(jh.getStatus());//是否已完成
				model.setUserId(jh.getUser().getUuid());
				model.setUserName(jh.getUser().getUserName());
				model.setZrSxName(jh.getZrSxName());//责任事项
				model.setSid(jh.getSid());
				listModel.add(model);
			}
		}
		easyJson.setRows(listModel);
		easyJson.setTotal(count);
		return easyJson;
	}

	
	/**
	 * 删除责任纪实
	 * */
	public TeeJson deleteZenRenJiShi(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			teeZenRenJiShiDao.delete(sid);
			json.setRtMsg("删除成功!");
			json.setRtState(true);
		}else{
			json.setRtMsg("删除失败!");
			json.setRtState(false);
		}
		
		return json;
	}

	public TeeJson deletePicZenRenJiShi(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String sids = request.getParameter("sids");
		if(sids!=null && !"".equals(sids)){
			teeZenRenJiShiDao.deleteOrUpdateByQuery("delete from TeeZenRenJiShi where sid in ("+sids+")", null);
			json.setRtMsg("删除成功!");
			json.setRtState(true);
		}else{
			json.setRtMsg("删除失败!");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * 签字
	 * */
	public TeeJson qianZiBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String leaderQm=request.getParameter("leaderQm");
		TeeZenRenJiShi jh = teeZenRenJiShiDao.get(sid);
		if(jh!=null){
			jh.setLeaderQm(leaderQm);
			teeZenRenJiShiDao.update(jh);
		}
		json.setRtState(true);
		return json;
	}

	/**
	 * 导出
	 * */
	public void exportExcel(HttpServletRequest request,HttpServletResponse response) {
		List<Map<String,Object>> listJh=getAllZenRenJiShi(request);
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("责任纪实列表信息");
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
			
			cell.setCellValue("责任事项");
			cell.setCellStyle(style);
			cell = row.createCell((short) (1));
					
			cell.setCellValue("计划完成日期");
			cell.setCellStyle(style);
			cell = row.createCell((short) (2));
					
					
			cell.setCellValue("地点");
			cell.setCellStyle(style);
			cell = row.createCell((short) (3));
					
			cell.setCellValue("实际完成日期");
			cell.setCellStyle(style);
			cell = row.createCell((short) (4));
			
			cell.setCellValue("内容主题");
			cell.setCellStyle(style);
			cell = row.createCell((short) (5));
			
			cell.setCellValue("摘要");
			cell.setCellStyle(style);
			cell = row.createCell((short) (6));
			
			cell.setCellValue("是否完成");
			cell.setCellStyle(style);
			cell = row.createCell((short) (7));
			// 设置内容
			if(listJh!=null && listJh.size()>0){
				for(int i=0;i<listJh.size();i++){
					Map<String, Object> map = listJh.get(i);
					HSSFRow tRow = sheet.createRow(i + 1); // 第4行是数据
					HSSFCell cell0 = tRow.createCell(0);
					cell0.setCellValue(TeeStringUtil.getString(map.get("zrSxName")));
					cell0.setCellStyle(style1);
					
					HSSFCell cell1 = tRow.createCell(1);
					cell1.setCellValue(TeeStringUtil.getString(map.get("jhEndTime")));
					cell1.setCellStyle(style1);
					
					HSSFCell cell2 = tRow.createCell(2);
					cell2.setCellValue(TeeStringUtil.getString(map.get("address")));
					cell2.setCellStyle(style1);
					
					HSSFCell cell3 = tRow.createCell(3);
					cell3.setCellValue(TeeStringUtil.getString(map.get("sjEndTime")));
					cell3.setCellStyle(style1);
					
					HSSFCell cell4 = tRow.createCell(4);
					cell4.setCellValue(TeeStringUtil.getString(map.get("content")));
					cell4.setCellStyle(style1);
					
					HSSFCell cell5 = tRow.createCell(5);
					cell5.setCellValue(TeeStringUtil.getString(map.get("sease")));
					cell5.setCellStyle(style1);
					
					HSSFCell cell6 = tRow.createCell(6);
					cell6.setCellValue(TeeStringUtil.getString(map.get("status")));
					cell6.setCellStyle(style1);
					
				}
			}

			// 设置每一列的宽度
			sheet.setDefaultColumnWidth(20);
			String fileName = "责任纪实列表信息.xls";
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

	private List<Map<String,Object>> getAllZenRenJiShi(HttpServletRequest request) {
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String para=request.getParameter("params");
		Map params = TeeJsonUtil.JsonStr2Map(para);
		String sids=request.getParameter("sids");
		String hql="from TeeZenRenJiShi where 1=1 ";
		List<Object> obj=new ArrayList<Object>();
		
		if(sids!=null && !"".equals(sids)){
			hql+="and sid in ("+sids+")";
		}else{
			int state=TeeStringUtil.getInteger(params.get("state"),0);
			String content = TeeStringUtil.getString(params.get("content"));//内容主题
			String rease = TeeStringUtil.getString(params.get("rease"));//摘要
			String userId=TeeStringUtil.getString(params.get("userId"));//责任人
			String deptId=TeeStringUtil.getString(params.get("deptId"));//部门
			String status=TeeStringUtil.getString(params.get("status"));//是否已完成
			String zrSxName = TeeStringUtil.getString(params.get("zrSxName"));//责任事项
			if(state==1){//查看分管领导所能查看的责任纪实
				List<TeeUserRole> userRoleOther = person.getUserRoleOther();
				List<String> list=new ArrayList<String>();
				if(userRoleOther!=null && userRoleOther.size()>0){
					for(TeeUserRole r:userRoleOther){
						list.add(r.getRoleName());
					}
				}
				if(list.contains("责任总队分管领导")){
					hql+="and dept.deptName=? and createUser.uuid!=? ";
					obj.add(person.getDept().getDeptName());
					obj.add(person.getUuid());
				}else{
					hql+="and createUser.dept.manager2=? ";
					obj.add(person.getUuid()+"");
				}
			}else if(state==2){//查看当前登陆人所创建的责任纪实
				hql+="and createUser.uuid=? ";
				obj.add(person.getUuid());
			}else{//所有的
				
			}
			if(content!=null && !"".equals(content)){//内容主题
				hql+="and content like '%"+content+"%' ";
				//obj.add(content);
			}
			if(rease!=null && !"".equals(rease)){//摘要
				hql+="and rease like '%"+rease+"%' ";
				//obj.add();
			}
			if(userId!=null && !"".equals(userId)){//责任人
				hql+="and user.uuid=? ";
				obj.add(TeeStringUtil.getInteger(userId, 0));
			}
			if(deptId!=null && !"".equals(deptId)){//部门
				hql+="and dept.uuid=? ";
				obj.add(TeeStringUtil.getInteger(deptId, 0));
			}
			if(status!=null && !"".equals(status)){//是否已完成，0否1是
				hql+="and status=? ";
				obj.add(TeeStringUtil.getInteger(status, 0));
			}
			if(zrSxName!=null && !"".equals(zrSxName)){
				hql+="and zrSxName=? ";
				obj.add(zrSxName);
			}
		}
		  String hql2="order by sid desc";
		List<TeeZenRenJiShi> list = teeZenRenJiShiDao.find(hql+hql2, obj.toArray());
		if(list!=null && list.size()>0){
			for(TeeZenRenJiShi jh:list){
				Map<String,Object> m=new HashMap<String,Object>();
				m.put("address", jh.getAddress());//地址
				m.put("content", jh.getContent());//内容
				m.put("deptName", jh.getDept().getDeptName());
				//计划结束时间
				m.put("jhEndTime", TeeDateUtil.format(jh.getJhEndTime(), "yyyy-MM-dd HH:mm"));
				//摘要
				m.put("sease", jh.getRease());
				//时间结束时间
				m.put("sjEndTime", TeeDateUtil.format(jh.getSjEndTime(),"yyyy-MM-dd HH:mm"));
				//是否已完成
				if(jh.getStatus()==0){
					m.put("status", "否");
				}else{
					m.put("status", "是");
				}
				m.put("userName", jh.getUser().getUserName());
				//责任事项
				m.put("zrSxName", jh.getZrSxName());
				listMap.add(m);
			}
		}
		return listMap;
	}
	
	public TeeEasyuiDataGridJson getZenRenJiShiAll(HttpServletRequest request,TeeDataGridModel m){
		TeeEasyuiDataGridJson easyJson =new TeeEasyuiDataGridJson();
		TeePerson person=null;
		String userId = request.getParameter("userId");
		List<TeePerson> find = teeSimpleDaoSupport.find("from TeePerson  where userId = ? and deleteStatus <> '1' ", new Object[]{userId});
        if(find!=null && find.size()>0){
        	person=find.get(0);
        }
        String hql2="order by sid desc";
		//1分管2当前登陆人0其他人
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		List<String> list=new ArrayList<String>();//辅助角色名称集合
		Long count=0L;
		List<TeeZenRenJiShi> pageFind=null;
	  if(person!=null){
		TeeUserRole userRole = person.getUserRole();//主教色
		List<TeeUserRole> userRoleOther = person.getUserRoleOther();//辅助角色
		if(userRoleOther!=null && userRoleOther.size()>0){
			for(TeeUserRole r:userRoleOther){
				list.add(r.getRoleName());
			}
		}
		if(list.contains("责任纪实最大权限")){//可以查看所有的
			pageFind= teeZenRenJiShiDao.pageFind("from TeeZenRenJiShi "+hql2, m.getFirstResult(), m.getRows(), null);
			count=teeZenRenJiShiDao.count("select count(*) from TeeZenRenJiShi", null);
		}else{
			if(userRole!=null){
				if("总队领导".equals(userRole.getRoleName())){//查看所分管的和自己所创建的
					pageFind= teeZenRenJiShiDao.pageFind("from TeeZenRenJiShi where createUser.uuid=? or createUser.dept.manager2=? "+hql2, m.getFirstResult(), m.getRows(), new Object[]{person.getUuid(),person.getUuid()+""});
					count=teeZenRenJiShiDao.count("select count(*) from TeeZenRenJiShi where createUser.uuid=? or createUser.dept.manager2=?", new Object[]{person.getUuid(),person.getUuid()+""});
				}else if("部门负责人".equals(userRole.getRoleName())){//查看自己的
					pageFind= teeZenRenJiShiDao.pageFind("from TeeZenRenJiShi where createUser.uuid=? "+hql2, m.getFirstResult(), m.getRows(), new Object[]{person.getUuid()});
				    count=teeZenRenJiShiDao.count("select count(*) from TeeZenRenJiShi where createUser.uuid=?", new Object[]{person.getUuid()});
				}
			}
		}
		if(pageFind!=null){
			for(TeeZenRenJiShi jh:pageFind){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("sid", jh.getSid());
				map.put("address", jh.getAddress());//地址
				map.put("content", jh.getContent());//内容
				//map.put("deptId", jh.getDept().getUuid());
				map.put("deptName", jh.getDept().getDeptName());
				//计划结束时间
				map.put("jhEndTime", TeeDateUtil.format(jh.getJhEndTime(), "yyyy-MM-dd HH:mm"));
				//摘要
				map.put("sease", jh.getRease());
				//时间结束时间
				map.put("sjEndTime", TeeDateUtil.format(jh.getSjEndTime(),"yyyy-MM-dd HH:mm"));
				//是否已完成
				if(jh.getStatus()==0){
					map.put("status", "否");
				}else{
					map.put("status", "是");
				}
				map.put("userName", jh.getUser().getUserName());
				//map.put("userId", jh.getUser().getUuid());
				//责任事项
				map.put("zrSxName", jh.getZrSxName());
				map.put("daclContent", jh.getDaclContent());
				if(person.getUuid()==jh.getCreateUser().getUuid()){//当前登陆人创建的
					map.put("flag", "2");
				}else if(person.getUuid()==TeeStringUtil.getInteger(jh.getCreateUser().getDept().getManager2(), 0)){//当前登陆分管部门下的
					map.put("flag", "1");
				}else if(list.contains("责任总队分管领导") && "总队领导".equals(jh.getCreateUser().getDept().getDeptName())){
					map.put("flag", "1");
				}else{
					map.put("flag", "0");
				}
				listMap.add(map);
			}
		}
		}
		easyJson.setRows(listMap);
		easyJson.setTotal(count);
        return easyJson;
	}

	/**
	 * 调用发布内部信息
	 * */
	public TeeJson sendSmsSender(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String userId = request.getParameter("userId");
		String content = request.getParameter("content");
		List<TeePerson> find = teeSimpleDaoSupport.find("from TeePerson  where userId = ? and deleteStatus <> '1' ", new Object[]{userId});
        if(find!=null && find.size()>0){
        	TeePerson person=find.get(0);
    		Map requestData = new HashMap();
			requestData.put("moduleNo", "093");
			requestData.put("userListIds", person.getUuid());
			requestData.put("content", content);
			requestData.put("remindUrl", "");
			smsSender.sendSms(requestData, null);
			json.setRtMsg("发送成功");
			json.setRtState(true);
        }else{
        	json.setRtMsg("发送失败");
			json.setRtState(false);
        }
		return json;
	}
	
}
