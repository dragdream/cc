package com.tianee.oa.subsys.contract.service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.service.TeePersonManagerI;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeModelIdConst;
import com.tianee.oa.subsys.contract.bean.TeeContract;
import com.tianee.oa.subsys.contract.bean.TeeContractCategory;
import com.tianee.oa.subsys.contract.bean.TeeContractSort;
import com.tianee.oa.subsys.contract.model.TeeContractModel;
import com.tianee.oa.subsys.contract.model.TeeContractSortModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeManagerPostPersonDataPrivModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeContractService extends TeeBaseService{
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private TeePersonManagerI personManagerI;
	
	@Autowired
	private TeeContractCategoryService categoryService;
	
	@Autowired
	private TeeContractSortService sortService;
	
	@Autowired
	private TeeDeptDao deptDao;
	
	public void add(TeeContractModel contractModel){
		TeeContract contract = Model2Entity(contractModel);
		simpleDaoSupport.save(contract);
		//设置附件
		String attachIds[] = TeeStringUtil.parseStringArray(contractModel.getAttachIds());
		for(String attachId:attachIds){
			if("".equals(attachId)){
				continue;
			}
			TeeAttachment attachment = attachmentService.getById(Integer.parseInt(attachId));
			if(attachment!=null){
				attachment.setModelId(String.valueOf(contract.getSid()));
				attachment.setModel(TeeAttachmentModelKeys.CONTRACT);
			}
		}
		
	}
	
	public void update(TeeContractModel contractModel){
		TeeContract contract = (TeeContract) simpleDaoSupport.get(TeeContract.class, contractModel.getSid());
		BeanUtils.copyProperties(contractModel, contract, new String[]{"sid","runId","attachIds"});
		
		contract.setCategory((TeeContractCategory)simpleDaoSupport.get(TeeContractCategory.class, contractModel.getCategoryId()));
		contract.setContractSort((TeeContractSort)simpleDaoSupport.get(TeeContractSort.class, contractModel.getContractSortId()));
		contract.setDept((TeeDepartment)simpleDaoSupport.get(TeeDepartment.class, contractModel.getDeptId()));
		contract.setRunId(0);
		
		String oldAttachIds = contract.getAttachIds();
		String newAttachIds = contractModel.getAttachIds();
		
		//更新最新的附件
		boolean hasExists = false;
		String attachIds[] = TeeStringUtil.parseStringArray(newAttachIds);
		for(String attachId:attachIds){
			if("".equals(attachId)){
				continue;
			}
			TeeAttachment attachment = attachmentService.getById(Integer.parseInt(attachId));
			if(attachment!=null){
				hasExists = true;
				attachment.setModelId(String.valueOf(contract.getSid()));
				attachment.setModel(TeeAttachmentModelKeys.CONTRACT);
			}
		}
		
		//合并附件
		if(hasExists){
			if(oldAttachIds==null || "".equals(oldAttachIds)){
				oldAttachIds+=newAttachIds;
			}else{
				oldAttachIds+=","+newAttachIds;
			}
		}
		
		contract.setAttachIds(oldAttachIds);
		
		simpleDaoSupport.update(contract);
	}
	
	public void delete(int sid){
		simpleDaoSupport.delete(TeeContract.class, sid);
	}
	
	public TeeContractModel get(int sid){
		TeeContract contract = (TeeContract) simpleDaoSupport.get(TeeContract.class, sid);
		return Entity2Model(contract);
	}
	
	public TeeEasyuiDataGridJson datagridByView(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		int catId = TeeStringUtil.getInteger(requestData.get("catId"), 0);
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		List params = new ArrayList();
		StringBuffer hql = new StringBuffer("from TeeContract contract,TeeContractCategory category where contract.category=category and exists (select 1 from category.viewPriv viewPriv where viewPriv.uuid=?)");
		params.add(loginUser.getUuid());
		
		if(catId!=0){
			hql.append(" and category.sid=?");
			params.add(catId);
		}
		
		//拼接查询条件
		String contractName = TeeStringUtil.getString(requestData.get("contractName"));
		if(!"".equals(contractName)){
			hql.append(" and contract.contractName like ?");
			params.add("%"+contractName+"%");
		}
		
		String bisUser = TeeStringUtil.getString(requestData.get("bisUser"));
		if(!"".equals(bisUser)){
			hql.append(" and contract.bisUser like ?");
			params.add("%"+bisUser+"%");
		}
		
		String contractCode = TeeStringUtil.getString(requestData.get("contractCode"));
		if(!"".equals(contractCode)){
			hql.append(" and contract.contractCode like ?");
			params.add("%"+contractCode+"%");
		}
		
		int deptId = TeeStringUtil.getInteger(requestData.get("deptId"),0);
		if(deptId!=0){
			hql.append(" and contract.dept.uuid = ?");
			params.add(deptId);
		}
		
		String partyAUnit = TeeStringUtil.getString(requestData.get("partyAUnit"));
		if(!"".equals(partyAUnit)){
			hql.append(" and contract.partyAUnit like ?");
			params.add("%"+partyAUnit+"%");
		}
		
		String partyBUnit = TeeStringUtil.getString(requestData.get("partyBUnit"));
		if(!"".equals(partyBUnit)){
			hql.append(" and contract.partyBUnit like ?");
			params.add("%"+partyBUnit+"%");
		}
		
		String partyACharger = TeeStringUtil.getString(requestData.get("partyACharger"));
		if(!"".equals(partyACharger)){
			hql.append(" and contract.partyACharger like ?");
			params.add("%"+partyACharger+"%");
		}
		
		String partyBCharger = TeeStringUtil.getString(requestData.get("partyBCharger"));
		if(!"".equals(partyBCharger)){
			hql.append(" and contract.partyBCharger like ?");
			params.add("%"+partyBCharger+"%");
		}
		
		String startTime1 = TeeStringUtil.getString(requestData.get("startTime1"));
		String startTime2 = TeeStringUtil.getString(requestData.get("startTime2"));
		if(!"".equals(startTime1) && "".equals(startTime2)){
			hql.append(" and contract.startTime >= ?");
			params.add(TeeDateUtil.parseDateByPattern(startTime1).getTime());
		}else if("".equals(startTime1) && !"".equals(startTime2)){
			hql.append(" and contract.startTime <= ?");
			params.add(TeeDateUtil.parseDateByPattern(startTime2).getTime());
		}else if(!"".equals(startTime1) && !"".equals(startTime2)){
			hql.append(" and (contract.startTime >= ? or contract.startTime<=?)");
			params.add(TeeDateUtil.parseDateByPattern(startTime1).getTime());
			params.add(TeeDateUtil.parseDateByPattern(startTime2).getTime());
		}
		
		String endTime1 = TeeStringUtil.getString(requestData.get("endTime1"));
		String endTime2 = TeeStringUtil.getString(requestData.get("endTime2"));
		if(!"".equals(endTime1) && "".equals(endTime2)){
			hql.append(" and contract.endTime >= ?");
			params.add(TeeDateUtil.parseDateByPattern(endTime1).getTime());
		}else if("".equals(endTime1) && !"".equals(endTime2)){
			hql.append(" and contract.endTime <= ?");
			params.add(TeeDateUtil.parseDateByPattern(endTime2).getTime());
		}else if(!"".equals(endTime1) && !"".equals(endTime2)){
			hql.append(" and (contract.endTime >= ? or contract.endTime<=?)");
			params.add(TeeDateUtil.parseDateByPattern(endTime1).getTime());
			params.add(TeeDateUtil.parseDateByPattern(endTime2).getTime());
		}
		
		String verTime1 = TeeStringUtil.getString(requestData.get("verTime1"));
		String verTime2 = TeeStringUtil.getString(requestData.get("verTime2"));
		if(!"".equals(verTime1) && "".equals(verTime2)){
			hql.append(" and contract.verTime >= ?");
			params.add(TeeDateUtil.parseDateByPattern(verTime1).getTime());
		}else if("".equals(verTime1) && !"".equals(verTime2)){
			hql.append(" and contract.verTime <= ?");
			params.add(TeeDateUtil.parseDateByPattern(verTime2).getTime());
		}else if(!"".equals(verTime1) && !"".equals(verTime2)){
			hql.append(" and (contract.verTime >= ? or contract.verTime<=?)");
			params.add(TeeDateUtil.parseDateByPattern(verTime1).getTime());
			params.add(TeeDateUtil.parseDateByPattern(verTime2).getTime());
		}
		
		List<TeeContractModel> modelList = new ArrayList();
		List<TeeContract> list = simpleDaoSupport.pageFind("select contract "+hql.toString()+"  order by contract.sid desc", dm.getRows()*(dm.getPage()-1), dm.getRows(), params.toArray());
		for(TeeContract contract:list){
			TeeContractModel m = Entity2Model(contract);
			m.setContent("");
			modelList.add(m);
		}
		
		long total = simpleDaoSupport.count("select count(contract) "+hql.toString(), params.toArray());
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		
		return dataGridJson;
	}
	
	public TeeEasyuiDataGridJson datagridByManage(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		int catId = TeeStringUtil.getInteger(requestData.get("catId"), 0);
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		List params = new ArrayList();
		StringBuffer hql = new StringBuffer("from TeeContract contract,TeeContractCategory category where contract.category=category and exists (select 1 from category.managePriv managePriv where managePriv.uuid=?)");
		params.add(loginUser.getUuid());
		
		if(catId!=0){
			hql.append(" and category.sid=?");
			params.add(catId);
		}
		
		//拼接查询条件
		String contractName = TeeStringUtil.getString(requestData.get("contractName"));
		if(!"".equals(contractName)){
			hql.append(" and contract.contractName like ?");
			params.add("%"+contractName+"%");
		}
		
		String bisUser = TeeStringUtil.getString(requestData.get("bisUser"));
		if(!"".equals(bisUser)){
			hql.append(" and contract.bisUser like ?");
			params.add("%"+bisUser+"%");
		}
		
		String contractCode = TeeStringUtil.getString(requestData.get("contractCode"));
		if(!"".equals(contractCode)){
			hql.append(" and contract.contractCode like ?");
			params.add("%"+contractCode+"%");
		}
		
		int deptId = TeeStringUtil.getInteger(requestData.get("deptId"),0);
		if(deptId!=0){
			hql.append(" and contract.dept.uuid = ?");
			params.add(deptId);
		}
		
		String partyAUnit = TeeStringUtil.getString(requestData.get("partyAUnit"));
		if(!"".equals(partyAUnit)){
			hql.append(" and contract.partyAUnit like ?");
			params.add("%"+partyAUnit+"%");
		}
		
		String partyBUnit = TeeStringUtil.getString(requestData.get("partyBUnit"));
		if(!"".equals(partyBUnit)){
			hql.append(" and contract.partyBUnit like ?");
			params.add("%"+partyBUnit+"%");
		}
		
		String partyACharger = TeeStringUtil.getString(requestData.get("partyACharger"));
		if(!"".equals(partyACharger)){
			hql.append(" and contract.partyACharger like ?");
			params.add("%"+partyACharger+"%");
		}
		
		String partyBCharger = TeeStringUtil.getString(requestData.get("partyBCharger"));
		if(!"".equals(partyBCharger)){
			hql.append(" and contract.partyBCharger like ?");
			params.add("%"+partyBCharger+"%");
		}
		
		String startTime1 = TeeStringUtil.getString(requestData.get("startTime1"));
		String startTime2 = TeeStringUtil.getString(requestData.get("startTime2"));
		if(!"".equals(startTime1) && "".equals(startTime2)){
			hql.append(" and contract.startTime >= ?");
			params.add(TeeDateUtil.parseDateByPattern(startTime1).getTime());
		}else if("".equals(startTime1) && !"".equals(startTime2)){
			hql.append(" and contract.startTime <= ?");
			params.add(TeeDateUtil.parseDateByPattern(startTime2).getTime());
		}else if(!"".equals(startTime1) && !"".equals(startTime2)){
			hql.append(" and (contract.startTime >= ? or contract.startTime<=?)");
			params.add(TeeDateUtil.parseDateByPattern(startTime1).getTime());
			params.add(TeeDateUtil.parseDateByPattern(startTime2).getTime());
		}
		
		String endTime1 = TeeStringUtil.getString(requestData.get("endTime1"));
		String endTime2 = TeeStringUtil.getString(requestData.get("endTime2"));
		if(!"".equals(endTime1) && "".equals(endTime2)){
			hql.append(" and contract.endTime >= ?");
			params.add(TeeDateUtil.parseDateByPattern(endTime1).getTime());
		}else if("".equals(endTime1) && !"".equals(endTime2)){
			hql.append(" and contract.endTime <= ?");
			params.add(TeeDateUtil.parseDateByPattern(endTime2).getTime());
		}else if(!"".equals(endTime1) && !"".equals(endTime2)){
			hql.append(" and (contract.endTime >= ? or contract.endTime<=?)");
			params.add(TeeDateUtil.parseDateByPattern(endTime1).getTime());
			params.add(TeeDateUtil.parseDateByPattern(endTime2).getTime());
		}
		
		String verTime1 = TeeStringUtil.getString(requestData.get("verTime1"));
		String verTime2 = TeeStringUtil.getString(requestData.get("verTime2"));
		if(!"".equals(verTime1) && "".equals(verTime2)){
			hql.append(" and contract.verTime >= ?");
			params.add(TeeDateUtil.parseDateByPattern(verTime1).getTime());
		}else if("".equals(verTime1) && !"".equals(verTime2)){
			hql.append(" and contract.verTime <= ?");
			params.add(TeeDateUtil.parseDateByPattern(verTime2).getTime());
		}else if(!"".equals(verTime1) && !"".equals(verTime2)){
			hql.append(" and (contract.verTime >= ? or contract.verTime<=?)");
			params.add(TeeDateUtil.parseDateByPattern(verTime1).getTime());
			params.add(TeeDateUtil.parseDateByPattern(verTime2).getTime());
		}
		
		
		List<TeeContractModel> modelList = new ArrayList();
		
		//数据管理权限
		TeeManagerPostPersonDataPrivModel dataPrivModel = personManagerI.getManagerPostPersonIdsPriv(requestData, TeeModelIdConst.CONTRACT_MANAGER_POST_PRIV, "0");
		if(dataPrivModel.getPrivType().equals("0")){//只能看到自己创建的
			hql.append(" and contract.operUser.uuid=?");
			params.add(loginUser.getUuid());
		}else if(dataPrivModel.getPrivType().equals("ALL")){//所有
			
		}else{
			List<Integer> pIdList = dataPrivModel.getPersonIds();//获取权限
			
			if(pIdList.size() > 0){
				String personIdsSql = TeeDbUtility.IN("contract.operUser.uuid", pIdList);
				hql.append(" and "+personIdsSql);
			}else{
				dataGridJson.setTotal(0L);
				dataGridJson.setRows(modelList);
				return dataGridJson;
			}
		}
		
		List<TeeContract> list = simpleDaoSupport.pageFind("select contract "+hql.toString()+" order by contract.sid desc", dm.getRows()*(dm.getPage()-1), dm.getRows(), params.toArray());
		for(TeeContract contract:list){
			TeeContractModel m = Entity2Model(contract);
			m.setContent("");
			modelList.add(m);
		}
		
		long total = simpleDaoSupport.count("select count(contract) "+hql.toString(), params.toArray());
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		
		return dataGridJson;
	}
	
	public TeeContractModel Entity2Model(TeeContract contract){
		TeeContractModel m = new TeeContractModel();
		BeanUtils.copyProperties(contract, m);
		
		TeeContractCategory category = contract.getCategory();
		if(category!=null){
			m.setCategoryId(category.getSid());
			m.setCategoryName(category.getName());
		}
		
		TeeContractSort sort = contract.getContractSort();
		if(sort!=null){
			m.setContractSortId(sort.getSid());
			m.setContractSortName(sort.getName());
		}
		
		TeeDepartment dept = contract.getDept();
		if(dept!=null){
			m.setDeptId(dept.getUuid());
			m.setDeptName(dept.getDeptName());
		}
		
		TeePerson operUser = contract.getOperUser();
		if(operUser!=null){
			m.setOperUserId(operUser.getUuid());
			m.setOperUserName(operUser.getUserName());
		}
		
		
		
		return m;
	}
	
	public TeeContract Model2Entity(TeeContractModel contractModel){
		TeeContract contract = new TeeContract();
		BeanUtils.copyProperties(contractModel, contract);
		
		TeeContractCategory category = 
				(TeeContractCategory) simpleDaoSupport.get(TeeContractCategory.class, contractModel.getCategoryId());
		
		TeeContractSort contractSort = 
				(TeeContractSort) simpleDaoSupport.get(TeeContractSort.class, contractModel.getContractSortId());
		
		TeeDepartment dept = 
				(TeeDepartment) simpleDaoSupport.get(TeeDepartment.class, contractModel.getDeptId());
		
		TeePerson operUser = 
				(TeePerson) simpleDaoSupport.get(TeePerson.class, contractModel.getOperUserId());
		
		contract.setCategory(category);
		contract.setContractSort(contractSort);
		contract.setDept(dept);
		contract.setOperUser(operUser);
		
		return contract;
	}

	/**
	 * 合同导入
	 * @param request
	 * @return
	 */
	public TeeJson importContract(HttpServletRequest request) {
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取所有的合同分类
		String hql = "from TeeContractCategory category where exists (select 1 from category.viewPriv viewPriv where viewPriv.uuid="+loginUser.getUuid()+") order by category.sid asc";
		List<TeeContractCategory> list = simpleDaoSupport.find(hql, null);
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		// 存放model的List
		List<TeeContractModel> modelList = new ArrayList<TeeContractModel>();
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
					int rowNum = sheet[i].getRows();
					if ((sheet[i].getColumns()-1) != 17) {
						json.setRtState(false);
						json.setRtMsg("你导入的文件不正确，请下载模板，按模板填写内容");
						break;
					}
					for (int j = 1; j < rowNum; j++) {

						TeeContractModel model = new TeeContractModel();
						model.setStartTime(0l);
						model.setEndTime(0l);
						model.setVerTime(0l);
						// 得到当前行的所有单元格
						Cell[] cells = sheet[i].getRow(j);
						if (cells != null && cells.length > 0) {
							// 对每个单元格进行循环
							for (int k = 0; k < cells.length; k++) {
								String cellValue = cells[k].getContents();
								switch (k) {
								case 0:
									model.setContractName(cellValue);//合同名称
									break;
								case 1:
									model.setContractCode(cellValue);//合同编号
									break;
								case 2:
									if(cellValue==""||cellValue==null){
										model.setCategoryName("为空");
										model.setCategoryId(0);
									}else{
										if(!TeeUtility.isNullorEmpty(list)){
											for(TeeContractCategory category:list){
												String categoryName = category.getName();//获取合同分类
												int categoryId = category.getSid();
												if(cellValue.equals(categoryName)){
													model.setCategoryName(categoryName);
													model.setCategoryId(categoryId);
													break;
												}else{
													model.setCategoryName("不存在");
													model.setCategoryId(0);
												}
											}
										}else{
											model.setCategoryName("不存在");
											model.setCategoryId(0);
										}
									}
									break;
								case 3:
									if(cellValue==""||cellValue==null){
										model.setContractSortName("为空");
										model.setContractSortId(0);
									}else if("不存在".equals(model.getCategoryName())||"为空".equals(model.getCategoryName())){
										model.setContractSortName("不存在");
										model.setContractSortId(0);
									}else{
										List<TeeContractSortModel> list2 = sortService.getSortByCatId(model.getCategoryId());
										if(list2.size() <=0){
											model.setContractSortName("不存在");
											model.setContractSortId(0);
										}else{
											for(TeeContractSortModel sort:list2){
												String sortName = sort.getName();//获取合同类型
												int sortId = sort.getSid();
												if(cellValue.equals(sortName)){
													model.setContractSortName(sortName);
													model.setContractSortId(sortId);
													break;
												}else{
													model.setContractSortName("不存在");
													model.setContractSortId(0);
												}
											}
										}
									}
									break;
								case 4:
									if(cellValue==""||cellValue==null){//部门
										model.setDeptName("为空");
										model.setDeptId(0);
									}else{
										model.setDeptName(cellValue);
										TeeDepartment dept = deptDao.getParentDeptByFullName(model.getDeptName());
							            if(TeeUtility.isNullorEmpty(dept)){
							            	model.setDeptName("不存在");
							            	model.setDeptId(0);
							            }else{
							            	model.setDeptName(dept.getDeptName());
							            	model.setDeptId(dept.getUuid());
							            	break;
							            }
									}
									break;
								case 5:
									if(cellValue==""||cellValue==null){//金额
										model.setAmount(0);
									}else{
									    model.setAmount(Double.parseDouble(cellValue));
									    break;
									}
									break;
								case 6:
									if(cellValue==""||cellValue==null){//开始时间
										model.setStartTime(0l);
									}else{
										Date beginTime = TeeDateUtil.parseDateByPattern(cellValue);
										if (beginTime == null) {
											 model.setStartTime(-1l);//开始时间
										}else{
											Date date = sdf.parse(cellValue);
											model.setStartTime(date.getTime());//开始时间
											break;
										}
									}
									break;
								case 7:
									if(cellValue==""||cellValue==null){//结束时间
										model.setEndTime(0l);
									}else{
										Date endTime = TeeDateUtil.parseDateByPattern(cellValue);
										if (endTime == null) {
											 model.setEndTime(-1l);//结束时间
										}else{
											Date date = sdf.parse(cellValue);
											model.setEndTime(date.getTime());//结束时间
											break;
										}
									}
									break;
								case 8:
									if(cellValue==""||cellValue==null){//合同签订时间
										model.setVerTime(0l);
									}else{
										Date verTime = TeeDateUtil.parseDateByPattern(cellValue);
										if (verTime == null) {
											 model.setVerTime(-1l);//合同签订时间
										}else{
											Date date = sdf.parse(cellValue);
											model.setVerTime(date.getTime());//合同签订时间
											break;
										}
									}
									break;
								case 9:
									model.setBisUser(cellValue);//业务员
									break;
								case 10:
									model.setPartyAUnit(cellValue);//甲方单位
									break;
								case 11:
									model.setPartyBUnit(cellValue);//乙方单位
									break;
								case 12:
									model.setPartyACharger(cellValue);//甲方负责人
									break;
								case 13:
									model.setPartyBCharger(cellValue);//甲方负责人
									break;
								case 14:
									model.setPartyAContact(cellValue);//甲方联系方式
									break;
								case 15:
									model.setPartyBContact(cellValue);//乙方联系方式
									break;
								case 16:
									model.setContent(cellValue);//合同主要内容
									break;
								case 17:
									model.setRemark(cellValue);//备注
									break;
								}
							}
						}
						// 判断合同名称是否为空
						if (model.getContractName() != "") {
							//判断合同分类是否存在
							if(!("为空").equals(model.getCategoryName())){
								if("不存在".equals(model.getCategoryName())){
									json.setRtMsg("导入失败，合同分类不存在！");
									json.setRtState(false);
								}else{
									if(!("为空").equals(model.getContractSortName())){
										//判断合同类型是否存在
										if("不存在".equals(model.getContractSortName())){
											json.setRtMsg("导入失败，合同类型不存在！");
											json.setRtState(false);
										}else{
											if(!("为空").equals(model.getDeptName())){
												//判断部门是否存在
												if("不存在".equals(model.getDeptName())){
													json.setRtMsg("导入失败，所在部门不存在！");
													json.setRtState(false);
												}else{
													long b = model.getStartTime();
													if(model.getStartTime()!=0l){
														if(model.getStartTime()==-1l){
															json.setRtMsg("导入失败，合同开始日期格式错误！");
															json.setRtState(false);
														}else{
															if(model.getEndTime()!=0l){
																if(model.getEndTime()==-1l){
																	json.setRtMsg("导入失败，合同结束日期格式错误！");
																	json.setRtState(false);
																}else{
																	if(model.getVerTime()!=0l){
																		if(model.getVerTime()==-1l){
																			json.setRtMsg("导入失败，合同签订日期格式错误！");
																			json.setRtState(false);
																		}else{
																			model.setOperUserId(loginUser.getUuid());
																			model.setOperUserName(loginUser.getUserName());
																			modelList.add(model);
																		}
																	}else{
																		model.setOperUserId(loginUser.getUuid());
																		model.setOperUserName(loginUser.getUserName());
																		modelList.add(model);
																	}
																}
															}else{
																if(model.getVerTime()!=0l){
																	if(model.getVerTime()==-1l){
																		json.setRtMsg("导入失败，合同签订日期格式错误！");
																		json.setRtState(false);
																	}else{
																		model.setOperUserId(loginUser.getUuid());
																		model.setOperUserName(loginUser.getUserName());
																		modelList.add(model);
																	}
																}else{
																	model.setOperUserId(loginUser.getUuid());
																	model.setOperUserName(loginUser.getUserName());
																	modelList.add(model);
																}
															
															}
															
														}
													}else{
														if(model.getEndTime()!=0l){
															if(model.getEndTime()==-1l){
																json.setRtMsg("导入失败，合同结束日期格式错误！");
																json.setRtState(false);
															}else{
																if(model.getVerTime()!=0l){
																	if(model.getVerTime()==-1l){
																		json.setRtMsg("导入失败，合同签订日期格式错误！");
																		json.setRtState(false);
																	}else{
																		model.setOperUserId(loginUser.getUuid());
																		model.setOperUserName(loginUser.getUserName());
																		modelList.add(model);
																	}
																}else{
																	model.setOperUserId(loginUser.getUuid());
																	model.setOperUserName(loginUser.getUserName());
																	modelList.add(model);
																}
															}
														}else{
															if(model.getVerTime()!=0l){
																if(model.getVerTime()==-1l){
																	json.setRtMsg("导入失败，合同签订日期格式错误！");
																	json.setRtState(false);
																}else{
																	model.setOperUserId(loginUser.getUuid());
																	model.setOperUserName(loginUser.getUserName());
																	modelList.add(model);
																}
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														
														}
														
													}
													
												}
											}else{
												if(model.getStartTime()!=0l){
													if(model.getStartTime()==-1l){
														json.setRtMsg("导入失败，合同开始日期格式错误！");
														json.setRtState(false);
													}else{
														if(model.getEndTime()!=0l){
															if(model.getEndTime()==-1l){
																json.setRtMsg("导入失败，合同结束日期格式错误！");
																json.setRtState(false);
															}else{
																if(model.getVerTime()!=0l){
																	if(model.getVerTime()==-1l){
																		json.setRtMsg("导入失败，合同签订日期格式错误！");
																		json.setRtState(false);
																	}else{
																		model.setOperUserId(loginUser.getUuid());
																		model.setOperUserName(loginUser.getUserName());
																		modelList.add(model);
																	}
																}else{
																	model.setOperUserId(loginUser.getUuid());
																	model.setOperUserName(loginUser.getUserName());
																	modelList.add(model);
																}
															}
														}else{
															if(model.getVerTime()!=0l){
																if(model.getVerTime()==-1l){
																	json.setRtMsg("导入失败，合同签订日期格式错误！");
																	json.setRtState(false);
																}else{
																	model.setOperUserId(loginUser.getUuid());
																	model.setOperUserName(loginUser.getUserName());
																	modelList.add(model);
																}
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														
														}
														
													}
												}else{
													if(model.getEndTime()!=0l){
														if(model.getEndTime()==-1l){
															json.setRtMsg("导入失败，合同结束日期格式错误！");
															json.setRtState(false);
														}else{
															if(model.getVerTime()!=0l){
																if(model.getVerTime()==-1l){
																	json.setRtMsg("导入失败，合同签订日期格式错误！");
																	json.setRtState(false);
																}else{
																	model.setOperUserId(loginUser.getUuid());
																	model.setOperUserName(loginUser.getUserName());
																	modelList.add(model);
																}
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														}
													}else{
														if(model.getVerTime()!=0l){
															if(model.getVerTime()==-1l){
																json.setRtMsg("导入失败，合同签订日期格式错误！");
																json.setRtState(false);
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														}else{
															model.setOperUserId(loginUser.getUuid());
															model.setOperUserName(loginUser.getUserName());
															modelList.add(model);
														}
													
													}
													
												}
												
											
											}
										}
									}else{
										if(!("为空").equals(model.getDeptName())){
											//判断部门是否存在
											if("不存在".equals(model.getDeptName())){
												json.setRtMsg("导入失败，所在部门不存在！");
												json.setRtState(false);
											}else{
												if(model.getStartTime()!=0l){
													if(model.getStartTime()==-1l){
														json.setRtMsg("导入失败，合同开始日期格式错误！");
														json.setRtState(false);
													}else{
														if(model.getEndTime()!=0l){
															if(model.getEndTime()==-1l){
																json.setRtMsg("导入失败，合同结束日期格式错误！");
																json.setRtState(false);
															}else{
																if(model.getVerTime()!=0l){
																	if(model.getVerTime()==-1l){
																		json.setRtMsg("导入失败，合同签订日期格式错误！");
																		json.setRtState(false);
																	}else{
																		model.setOperUserId(loginUser.getUuid());
																		model.setOperUserName(loginUser.getUserName());
																		modelList.add(model);
																	}
																}else{
																	model.setOperUserId(loginUser.getUuid());
																	model.setOperUserName(loginUser.getUserName());
																	modelList.add(model);
																}
															}
														}else{
															if(model.getVerTime()!=0l){
																if(model.getVerTime()==-1l){
																	json.setRtMsg("导入失败，合同签订日期格式错误！");
																	json.setRtState(false);
																}else{
																	model.setOperUserId(loginUser.getUuid());
																	model.setOperUserName(loginUser.getUserName());
																	modelList.add(model);
																}
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														
														}
														
													}
												}else{
													if(model.getEndTime()!=0l){
														if(model.getEndTime()==-1l){
															json.setRtMsg("导入失败，合同结束日期格式错误！");
															json.setRtState(false);
														}else{
															if(model.getVerTime()!=0l){
																if(model.getVerTime()==-1l){
																	json.setRtMsg("导入失败，合同签订日期格式错误！");
																	json.setRtState(false);
																}else{
																	model.setOperUserId(loginUser.getUuid());
																	model.setOperUserName(loginUser.getUserName());
																	modelList.add(model);
																}
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														}
													}else{
														if(model.getVerTime()!=0l){
															if(model.getVerTime()==-1l){
																json.setRtMsg("导入失败，合同签订日期格式错误！");
																json.setRtState(false);
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														}else{
															model.setOperUserId(loginUser.getUuid());
															model.setOperUserName(loginUser.getUserName());
															modelList.add(model);
														}
													
													}
													
												}
												
											}
										}else{
											if(model.getStartTime()!=0l){
												if(model.getStartTime()==-1l){
													json.setRtMsg("导入失败，合同开始日期格式错误！");
													json.setRtState(false);
												}else{
													if(model.getEndTime()!=0l){
														if(model.getEndTime()==-1l){
															json.setRtMsg("导入失败，合同结束日期格式错误！");
															json.setRtState(false);
														}else{
															if(model.getVerTime()!=0l){
																if(model.getVerTime()==-1l){
																	json.setRtMsg("导入失败，合同签订日期格式错误！");
																	json.setRtState(false);
																}else{
																	model.setOperUserId(loginUser.getUuid());
																	model.setOperUserName(loginUser.getUserName());
																	modelList.add(model);
																}
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														}
													}else{
														if(model.getVerTime()!=0l){
															if(model.getVerTime()==-1l){
																json.setRtMsg("导入失败，合同签订日期格式错误！");
																json.setRtState(false);
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														}else{
															model.setOperUserId(loginUser.getUuid());
															model.setOperUserName(loginUser.getUserName());
															modelList.add(model);
														}
													
													}
													
												}
											}else{
												if(model.getEndTime()!=0l){
													if(model.getEndTime()==-1l){
														json.setRtMsg("导入失败，合同结束日期格式错误！");
														json.setRtState(false);
													}else{
														if(model.getVerTime()!=0l){
															if(model.getVerTime()==-1l){
																json.setRtMsg("导入失败，合同签订日期格式错误！");
																json.setRtState(false);
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														}else{
															model.setOperUserId(loginUser.getUuid());
															model.setOperUserName(loginUser.getUserName());
															modelList.add(model);
														}
													}
												}else{
													if(model.getVerTime()!=0l){
														if(model.getVerTime()==-1l){
															json.setRtMsg("导入失败，合同签订日期格式错误！");
															json.setRtState(false);
														}else{
															model.setOperUserId(loginUser.getUuid());
															model.setOperUserName(loginUser.getUserName());
															modelList.add(model);
														}
													}else{
														model.setOperUserId(loginUser.getUuid());
														model.setOperUserName(loginUser.getUserName());
														modelList.add(model);
													}
												
												}
												
											}
											
										
										}
									}
								}
							}else{
								if(!("为空").equals(model.getContractSortName())){
									//判断合同类型是否存在
									if("不存在".equals(model.getContractSortName())){
										json.setRtMsg("导入失败，合同类型不存在！");
										json.setRtState(false);
									}else{
										if(!("为空").equals(model.getDeptName())){
											//判断部门是否存在
											if("不存在".equals(model.getDeptName())){
												json.setRtMsg("导入失败，所在部门不存在！");
												json.setRtState(false);
											}else{
												if(model.getStartTime()!=0l){
													if(model.getStartTime()==-1l){
														json.setRtMsg("导入失败，合同开始日期格式错误！");
														json.setRtState(false);
													}else{
														if(model.getEndTime()!=0l){
															if(model.getEndTime()==-1l){
																json.setRtMsg("导入失败，合同结束日期格式错误！");
																json.setRtState(false);
															}else{
																if(model.getVerTime()!=0l){
																	if(model.getVerTime()==-1l){
																		json.setRtMsg("导入失败，合同签订日期格式错误！");
																		json.setRtState(false);
																	}else{
																		model.setOperUserId(loginUser.getUuid());
																		model.setOperUserName(loginUser.getUserName());
																		modelList.add(model);
																	}
																}else{
																	model.setOperUserId(loginUser.getUuid());
																	model.setOperUserName(loginUser.getUserName());
																	modelList.add(model);
																}
															}
														}else{
															if(model.getVerTime()!=0l){
																if(model.getVerTime()==-1l){
																	json.setRtMsg("导入失败，合同签订日期格式错误！");
																	json.setRtState(false);
																}else{
																	model.setOperUserId(loginUser.getUuid());
																	model.setOperUserName(loginUser.getUserName());
																	modelList.add(model);
																}
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														
														}
														
													}
												}else{
													if(model.getEndTime()!=0l){
														if(model.getEndTime()==-1l){
															json.setRtMsg("导入失败，合同结束日期格式错误！");
															json.setRtState(false);
														}else{
															if(model.getVerTime()!=0l){
																if(model.getVerTime()==-1l){
																	json.setRtMsg("导入失败，合同签订日期格式错误！");
																	json.setRtState(false);
																}else{
																	model.setOperUserId(loginUser.getUuid());
																	model.setOperUserName(loginUser.getUserName());
																	modelList.add(model);
																}
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														}
													}else{
														if(model.getVerTime()!=0l){
															if(model.getVerTime()==-1l){
																json.setRtMsg("导入失败，合同签订日期格式错误！");
																json.setRtState(false);
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														}else{
															model.setOperUserId(loginUser.getUuid());
															model.setOperUserName(loginUser.getUserName());
															modelList.add(model);
														}
													
													}
													
												}
												
											}
										}else{
											if(model.getStartTime()!=0l){
												if(model.getStartTime()==-1l){
													json.setRtMsg("导入失败，合同开始日期格式错误！");
													json.setRtState(false);
												}else{
													if(model.getEndTime()!=0l){
														if(model.getEndTime()==-1l){
															json.setRtMsg("导入失败，合同结束日期格式错误！");
															json.setRtState(false);
														}else{
															if(model.getVerTime()!=0l){
																if(model.getVerTime()==-1l){
																	json.setRtMsg("导入失败，合同签订日期格式错误！");
																	json.setRtState(false);
																}else{
																	model.setOperUserId(loginUser.getUuid());
																	model.setOperUserName(loginUser.getUserName());
																	modelList.add(model);
																}
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														}
													}else{
														if(model.getVerTime()!=0l){
															if(model.getVerTime()==-1l){
																json.setRtMsg("导入失败，合同签订日期格式错误！");
																json.setRtState(false);
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														}else{
															model.setOperUserId(loginUser.getUuid());
															model.setOperUserName(loginUser.getUserName());
															modelList.add(model);
														}
													
													}
													
												}
											}else{
												if(model.getEndTime()!=0l){
													if(model.getEndTime()==-1l){
														json.setRtMsg("导入失败，合同结束日期格式错误！");
														json.setRtState(false);
													}else{
														if(model.getVerTime()!=0l){
															if(model.getVerTime()==-1l){
																json.setRtMsg("导入失败，合同签订日期格式错误！");
																json.setRtState(false);
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														}else{
															model.setOperUserId(loginUser.getUuid());
															model.setOperUserName(loginUser.getUserName());
															modelList.add(model);
														}
													}
												}else{
													if(model.getVerTime()!=0l){
														if(model.getVerTime()==-1l){
															json.setRtMsg("导入失败，合同签订日期格式错误！");
															json.setRtState(false);
														}else{
															model.setOperUserId(loginUser.getUuid());
															model.setOperUserName(loginUser.getUserName());
															modelList.add(model);
														}
													}else{
														model.setOperUserId(loginUser.getUuid());
														model.setOperUserName(loginUser.getUserName());
														modelList.add(model);
													}
												
												}
												
											}
											
										
										}
									}
								}else{
									if(!("为空").equals(model.getDeptName())){
										//判断部门是否存在
										if("不存在".equals(model.getDeptName())){
											json.setRtMsg("导入失败，所在部门不存在！");
											json.setRtState(false);
										}else{
											if(model.getStartTime()!=0l){
												if(model.getStartTime()==-1l){
													json.setRtMsg("导入失败，合同开始日期格式错误！");
													json.setRtState(false);
												}else{
													if(model.getEndTime()!=0l){
														if(model.getEndTime()==-1l){
															json.setRtMsg("导入失败，合同结束日期格式错误！");
															json.setRtState(false);
														}else{
															if(model.getVerTime()!=0l){
																if(model.getVerTime()==-1l){
																	json.setRtMsg("导入失败，合同签订日期格式错误！");
																	json.setRtState(false);
																}else{
																	model.setOperUserId(loginUser.getUuid());
																	model.setOperUserName(loginUser.getUserName());
																	modelList.add(model);
																}
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														}
													}else{
														if(model.getVerTime()!=0l){
															if(model.getVerTime()==-1l){
																json.setRtMsg("导入失败，合同签订日期格式错误！");
																json.setRtState(false);
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														}else{
															model.setOperUserId(loginUser.getUuid());
															model.setOperUserName(loginUser.getUserName());
															modelList.add(model);
														}
													
													}
													
												}
											}else{
												if(model.getEndTime()!=0l){
													if(model.getEndTime()==-1l){
														json.setRtMsg("导入失败，合同结束日期格式错误！");
														json.setRtState(false);
													}else{
														if(model.getVerTime()!=0l){
															if(model.getVerTime()==-1l){
																json.setRtMsg("导入失败，合同签订日期格式错误！");
																json.setRtState(false);
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														}else{
															model.setOperUserId(loginUser.getUuid());
															model.setOperUserName(loginUser.getUserName());
															modelList.add(model);
														}
													}
												}else{
													if(model.getVerTime()!=0l){
														if(model.getVerTime()==-1l){
															json.setRtMsg("导入失败，合同签订日期格式错误！");
															json.setRtState(false);
														}else{
															model.setOperUserId(loginUser.getUuid());
															model.setOperUserName(loginUser.getUserName());
															modelList.add(model);
														}
													}else{
														model.setOperUserId(loginUser.getUuid());
														model.setOperUserName(loginUser.getUserName());
														modelList.add(model);
													}
												
												}
												
											}
											
										}
									}else{
										if(model.getStartTime()!=0l){
											if(model.getStartTime()==-1l){
												json.setRtMsg("导入失败，合同开始日期格式错误！");
												json.setRtState(false);
											}else{
												if(model.getEndTime()!=0l){
													if(model.getEndTime()==-1l){
														json.setRtMsg("导入失败，合同结束日期格式错误！");
														json.setRtState(false);
													}else{
														if(model.getVerTime()!=0l){
															if(model.getVerTime()==-1l){
																json.setRtMsg("导入失败，合同签订日期格式错误！");
																json.setRtState(false);
															}else{
																model.setOperUserId(loginUser.getUuid());
																model.setOperUserName(loginUser.getUserName());
																modelList.add(model);
															}
														}else{
															model.setOperUserId(loginUser.getUuid());
															model.setOperUserName(loginUser.getUserName());
															modelList.add(model);
														}
													}
												}else{
													if(model.getVerTime()!=0l){
														if(model.getVerTime()==-1l){
															json.setRtMsg("导入失败，合同签订日期格式错误！");
															json.setRtState(false);
														}else{
															model.setOperUserId(loginUser.getUuid());
															model.setOperUserName(loginUser.getUserName());
															modelList.add(model);
														}
													}else{
														model.setOperUserId(loginUser.getUuid());
														model.setOperUserName(loginUser.getUserName());
														modelList.add(model);
													}
												
												}
												
											}
										}else{
											if(model.getEndTime()!=0l){
												if(model.getEndTime()==-1l){
													json.setRtMsg("导入失败，合同结束日期格式错误！");
													json.setRtState(false);
												}else{
													if(model.getVerTime()!=0l){
														if(model.getVerTime()==-1l){
															json.setRtMsg("导入失败，合同签订日期格式错误！");
															json.setRtState(false);
														}else{
															model.setOperUserId(loginUser.getUuid());
															model.setOperUserName(loginUser.getUserName());
															modelList.add(model);
														}
													}else{
														model.setOperUserId(loginUser.getUuid());
														model.setOperUserName(loginUser.getUserName());
														modelList.add(model);
													}
												}
											}else{
												if(model.getVerTime()!=0l){
													if(model.getVerTime()==-1l){
														json.setRtMsg("导入失败，合同签订日期格式错误！");
														json.setRtState(false);
													}else{
														model.setOperUserId(loginUser.getUuid());
														model.setOperUserName(loginUser.getUserName());
														modelList.add(model);
													}
												}else{
													model.setOperUserId(loginUser.getUuid());
													model.setOperUserName(loginUser.getUserName());
													modelList.add(model);
												}
											
											}
											
										}
										
									
									}
								}
							}
								
					}else{
						json.setRtMsg("导入失败，合同名称不能为空！");
						json.setRtState(false);
					}
				}

					// 全部记录都正确进行批量导入
					if ((rowNum-1) == modelList.size()) {
						for (TeeContractModel models : modelList) {
							TeeContract contract = Model2Entity(models);
							simpleDaoSupport.save(contract);
						}
						json.setRtMsg((rowNum-1)+"");
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
	
}
