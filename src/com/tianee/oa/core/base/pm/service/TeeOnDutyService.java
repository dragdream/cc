package com.tianee.oa.core.base.pm.service;

import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.base.pm.bean.TeeOnDuty;
import com.tianee.oa.core.base.pm.dao.TeeOnDutyDao;
import com.tianee.oa.core.base.pm.model.TeeOnDutyModel;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonManagerI;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeModelIdConst;
import com.tianee.oa.webframe.httpModel.TeeManagerPostPersonDataPrivModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;

@Service
public class TeeOnDutyService extends TeeBaseService{

	@Autowired
	private TeeOnDutyDao dao;

	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	TeePersonManagerI personManagerI;

	/**
	 * 转换成返回对象
	 * 
	 * @param obj
	 * @param isEdit
	 * @return
	 */
	public TeeOnDutyModel parseReturnModel(TeeOnDuty obj, boolean isEdit) {
		TeeOnDutyModel model = new TeeOnDutyModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);

		if (obj.getUser() != null && obj.getUser().getUuid() != 0) {
			model.setUserName(obj.getUser().getUserName());
			model.setUserUuid(obj.getUser().getUuid());
		}

		if (obj.getUser().getDept() != null
				&& obj.getUser().getDept().getUuid() != 0) {
			model.setDeptId(obj.getUser().getDept().getUuid());
			model.setDeptName(obj.getUser().getDept().getDeptName());
		}
		String beginTime = TeeDateUtil.format(obj.getBeginTime(),
				"yyyy-MM-dd HH:mm:ss");
		model.setBeginTimeStr(beginTime);

		String endTime = TeeDateUtil.format(obj.getEndTime(),
				"yyyy-MM-dd HH:mm:ss");
		model.setEndTimeStr(endTime);
		return model;
	}

	/**
	 * 转换成保存对象
	 * 
	 * @param model
	 * @param obj
	 * @return
	 */
	public TeeOnDuty parseObj(TeeOnDutyModel model, TeeOnDuty obj) {
		if (model == null) {
			return obj;
		} else {
			BeanUtils.copyProperties(model, obj);
			if (model.getUserUuid() != 0) {
				TeePerson user = new TeePerson();
				user.setUuid(model.getUserUuid());
				obj.setUser(user);
			}
			obj.setBeginTime(TeeDateUtil.format(model.getBeginTimeStr()));
			obj.setEndTime(TeeDateUtil.format(model.getEndTimeStr()));

			return obj;
		}
	}

	/**
	 * 删除
	 * 
	 * @param sid
	 * @return
	 */
	public TeeJson deleteDutyById(int sid) {
		TeeJson json = new TeeJson();
		TeeOnDuty duty = new TeeOnDuty();
		duty.setUuid(sid);
		dao.deleteByObj(duty);
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}

	 /**
	 * 根据id获取详情
	 *
	 * @param sid
	 * @return
	 */
	 public TeeJson getDutyInfoById(int sid) {
	 TeeJson json = new TeeJson();
	 TeeOnDuty target = dao.get(sid);
	
	 TeeOnDutyModel model = parseReturnModel(target, false);
	 String pbDesc = TeeSysCodeManager.getChildSysCodeNameCodeNo("PBLX",model.getPbType());
	 String zbDesc = TeeSysCodeManager.getChildSysCodeNameCodeNo("ZBLX",model.getZbType());
	 model.setPbTypeDesc(pbDesc);
	 model.setZbTypeDesc(zbDesc);
	 json.setRtData(model);
	 json.setRtMsg(null);
	 json.setRtState(true);
	 return json;
	 }

	/**
	 * 新增或者更新
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeOnDutyModel model) {

		TeeJson json = new TeeJson();
		try {
			TeePerson person = (TeePerson) request.getSession().getAttribute(
					TeeConst.LOGIN_USER);
			if (model.getUuid() > 0) {
				TeeOnDuty obj = dao.get(model.getUuid());
				if (obj != null) {
					this.parseObj(model, obj);
					dao.update(obj);
				}
			} else {
				TeeOnDuty obj = new TeeOnDuty();
				this.parseObj(model, obj);
				dao.save(obj);
			}
			json.setRtState(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * 批量添加
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdateBatch(HttpServletRequest request,
			TeeOnDutyModel model, String userUuidStr) {
		TeeJson json = new TeeJson();
		try {
			TeePerson person = (TeePerson) request.getSession().getAttribute(
					TeeConst.LOGIN_USER);

			String[] uuidStr = userUuidStr.split(",");
			for (String userUuid : uuidStr) {
				int uuid = Integer.parseInt(userUuid);
				TeeOnDuty obj = new TeeOnDuty();
				TeePerson user = new TeePerson();
				user.setUuid(uuid);
				obj.setUser(user);
				this.parseObj(model, obj);
				dao.save(obj);
			}
			json.setRtState(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 导入排班安排
	 * 
	 * @param request
	 * @return
	 */
	public TeeJson importDutyInfos(HttpServletRequest request) {
		// 获取排班类型集合
		List<Map<String, Object>> pbList = TeeSysCodeManager
				.getChildSysCodeListByParentCodeNo("PBLX");
		// 获取值班类型集合
		List<Map<String, Object>> zbList = TeeSysCodeManager
				.getChildSysCodeListByParentCodeNo("ZBLX");
		// 存放model的List
		List<TeeOnDutyModel> modelList = new ArrayList<TeeOnDutyModel>();

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		InputStream ins = null;
		TeeJson json = new TeeJson();
		Workbook wb = null;
		try {
			MultipartFile file = multipartRequest.getFile("excelFile");
			ins = file.getInputStream();
			wb = Workbook.getWorkbook(ins);
			if (wb == null) {
				json.setRtState(false);
				json.setRtMsg("上传文件错误！");
			}
			Sheet[] sheet = wb.getSheets();
			if (sheet != null && sheet.length > 0) {
				// 对每个工作表进行循环
				for (int i = 0; i < sheet.length; i++) {
					// 得到当前工作表的行数
					int rowNum = sheet[i].getRows()-1;
					if ((sheet[i].getColumns()-1) != 7) {
						json.setRtState(false);
						json.setRtMsg("你导入的文件不正确，请下载模板，按模板填写内容");
						break;
					}
					for (int j = 1; j < rowNum; j++) {

						TeeOnDutyModel model = new TeeOnDutyModel();
						// 得到当前行的所有单元格
						Cell[] cells = sheet[i].getRow(j);
						if (cells != null && cells.length > 0) {
							// 对每个单元格进行循环
							for (int k = 0; k < cells.length; k++) {
								String cellValue = cells[k].getContents();
								switch (k) {
								case 0:
									model.setUserName(cellValue);
									break;
								case 1:
									model.setBeginTimeStr(cellValue);
									break;
								case 2:
									model.setEndTimeStr(cellValue);
									break;
								case 3:
									if(cellValue==""||cellValue==null){
										model.setPbType("为空");
									}else{
									for (Map map : pbList) {
										Set<String> nums = map.keySet();
										String num = (String) map.get("codeNo");
										String name = (String) map
												.get("codeName");
										if (cellValue.equals(name)) {
											model.setPbType(num);
											break;
										} else {
											model.setPbType("不存在");
										}
									  }
									}
									// System.out.println(model.getPbType()+"###");
									break;
								case 4:
									if(cellValue==""||cellValue==null){
										model.setZbType("为空");
									}else{
									for (Map map : zbList) {
										Set<String> nums = map.keySet();
										String num = (String) map.get("codeNo");
										String name = (String) map
												.get("codeName");
										if (cellValue.equals(name)) {
											model.setZbType(num);
											break;
										} else {
											model.setZbType("不存在");
										}
								      }
									}

									break;
								case 5:
									model.setDemand(cellValue);
									break;
								case 6:
									model.setRemark(cellValue);
									break;
								}
							}
						}
						// 判断值班人员是否存在
						if (model.getUserName() != "") {
							// 判断是否是OA用户
							TeePerson p = (TeePerson) personDao
									.getPersonByUserId(model.getUserName());
							if (p != null && p.getUuid() != 0) {
								model.setUserUuid(p.getUuid());

								// 判断值班开始时间是否为空
								if (model.getBeginTimeStr() == ""
										|| model.getBeginTimeStr() == null) {
									json.setRtMsg("值班开始时间不能为空！");
									json.setRtState(false);

								} else {
									Date beginTime = TeeDateUtil
											.parseDateByPattern(model
													.getBeginTimeStr());
									// System.out.println(beginTime+"===");
									if (beginTime == null) {
										json.setRtMsg("值班开始时间格式错误！");
										json.setRtState(false);
									} else {
										// 判断值班结束时间是否为空
										if (model.getEndTimeStr() == ""
												|| model.getEndTimeStr() == null) {
											json.setRtMsg("值班结束时间不能为空！");
											json.setRtState(false);
										} else {
											Date endTime = TeeDateUtil
													.parseDateByPattern(model
															.getEndTimeStr());
											if (endTime == null) {
												json.setRtMsg("值班结束时间格式错误！");
												json.setRtState(false);
											} else {
												if (endTime.after(beginTime)) {
													if(model.getPbType().equals("为空")){
														json.setRtMsg("排班类型不能为空！");
														json.setRtState(false);
													}else{
													// 判断排班类型
													if (model.getPbType()
															.equals("不存在")) {
														json.setRtMsg("排班类型不存在！");
														json.setRtState(false);
													} else {
														// 判断值班类型
														if(model.getZbType().equals("为空")){
																json.setRtMsg("值班类型不能为空");
																json.setRtState(false);
															} else {
																if (model
																		.getZbType()
																		.equals("不存在")) {
																	json.setRtMsg("值班类型不存在");
																	json.setRtState(false);
																} else {
																	modelList
																			.add(model);
														}
														}
													}
													}
												} else {
													json.setRtMsg("结束时间不能小于开始时间！");
													json.setRtState(false);
												}
											}
										}
									}
								}
							} else {
								json.setRtMsg("值班人员不存在！");
								json.setRtState(false);
							}
						} else {
							json.setRtMsg("值班人员不能为空！");
							json.setRtState(false);
						}

					}

					// System.out.println(rowNum+"数量row");
					// System.out.println(modelList.size()+"数量list");
					// 全部记录都正确进行批量导入
					if ((rowNum - 1) == modelList.size()) {
						for (TeeOnDutyModel model : modelList) {
							TeeOnDuty obj = new TeeOnDuty();
							this.parseObj(model, obj);
							dao.save(obj);
						}
						json.setRtMsg("导入成功！");
						json.setRtState(true);
					}
				}
			}
			wb.close();
		} catch (Exception ex) {

			json.setRtMsg("文件格式不对，请按模板进行内容填写");
			json.setRtState(false);

		}
		return json;
	}

	/**
	 * 获取值班列表
	 * 
	 * @param request
	 * @param year
	 * @param month
	 * @param deptId
	 * @param pbType
	 * @return
	 * @throws ParseException
	 */
	public TeeJson getDutyList(HttpServletRequest request, int year, int month,
			int deptId, String pbType) throws ParseException {

		TeeJson json = new TeeJson();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);

		int maxDays = calendar.getActualMaximum(Calendar.DATE);

		String date1 = year + "-" + month + "-01 00:00:00";
		String date2 = year + "-" + month + "-" + maxDays + " 23:59:59";
		Date date3 = TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", date1);
		Date date4 = TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", date2);

		// to_date('1990-01-01','YYYY-MM-DD') and
		// to_date('3000-12-30','YYYY-MM-DD')
		String hql = "from TeeOnDuty t  where ((beginTime >= ? and endTime <= ?) or (beginTime<=? and endTime>=? and endTime<=?) or (beginTime >=? and beginTime<=? and endTime >=?) or (beginTime<=? and endTime >=?))";
		if (!("000").equals(pbType)) {
			hql += " and t.pbType='" + pbType + "'";
		}
		if (deptId != 0) {

			hql += " and t.user.dept.uuid=" + deptId;
		}
		
		Map requestDatas = new HashMap();
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		
		//数据管理权限
		TeeManagerPostPersonDataPrivModel dataPrivModel = personManagerI.getSendPostPersonIdsPriv(requestDatas, TeeModelIdConst.PAI_BAN_POST_PRIV, "0");
		
		TeePerson loginUser = (TeePerson) requestDatas.get(TeeConst.LOGIN_USER);
		
		if(!TeePersonService.checkIsAdminPriv(loginUser)){
			if(dataPrivModel.getPrivType().equals("0")){//只能看到自己创建的
				
			}else if(dataPrivModel.getPrivType().equals("ALL")){//所有
				
			}else{
				List<Integer> pIdList = dataPrivModel.getPersonIds();//获取权限
				
				if(pIdList.size() > 0){
					String personIdsSql = TeeDbUtility.IN("t.user.uuid", pIdList);
					hql+=" and "+personIdsSql;
				}else{
					return json;
				}
			}
		}
				
		

		List<TeeOnDuty> list = dao.find(hql, new Object[] { date3, date4,
				date3, date3, date4, date3, date4, date4, date3, date4 });
		List<TeeOnDutyModel> modelList = new ArrayList<TeeOnDutyModel>();

		for (TeeOnDuty obj : list) {
			TeeOnDutyModel model = new TeeOnDutyModel();
			model = parseReturnModel(obj, false);
			modelList.add(model);
		}
		json.setRtData(modelList);
		json.setRtState(true);
		return json;
	}

	/**
	 * 获取当前登录的用户的列表信息
	 * @param request
	 * @param year
	 * @param month
	 * @param pbType
	 * @return
	 * @throws ParseException 
	 */
	public TeeJson getPersonalDutyList(HttpServletRequest request, int year,
			int month, String pbType) throws ParseException {
		//获取当前登录的用户
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		TeeJson json = new TeeJson();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);

		int maxDays = calendar.getActualMaximum(Calendar.DATE);

		String date1 = year + "-" + month + "-01 00:00:00";
		String date2 = year + "-" + month + "-" + maxDays + " 23:59:59";
		Date date3 = TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", date1);
		Date date4 = TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", date2);

		// to_date('1990-01-01','YYYY-MM-DD') and
		// to_date('3000-12-30','YYYY-MM-DD')
		String hql = "from TeeOnDuty t  where ((beginTime >= ? and endTime <= ?) or (beginTime<=? and endTime>=? and endTime<=?) or (beginTime >=? and beginTime<=? and endTime >=?) or (beginTime<=? and endTime >=?)) and user.uuid="+person.getUuid();
		if (!("000").equals(pbType)) {
			hql += " and t.pbType='" + pbType + "'";
		}

		List<TeeOnDuty> list = dao.find(hql, new Object[] { date3, date4,
				date3, date3, date4, date3, date4, date4, date3, date4 });
		List<TeeOnDutyModel> modelList = new ArrayList<TeeOnDutyModel>();

		for (TeeOnDuty obj : list) {
			TeeOnDutyModel model = new TeeOnDutyModel();
			model = parseReturnModel(obj, false);
			modelList.add(model);
		}
		json.setRtData(modelList);
		json.setRtState(true);
		return json;
	}

}
