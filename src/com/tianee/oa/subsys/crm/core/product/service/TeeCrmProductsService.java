package com.tianee.oa.subsys.crm.core.product.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.product.bean.TeeCrmProducts;
import com.tianee.oa.subsys.crm.core.product.bean.TeeCrmProductsType;
import com.tianee.oa.subsys.crm.core.product.dao.TeeCrmProductsDao;
import com.tianee.oa.subsys.crm.core.product.dao.TeeCrmProductsTypeDao;
import com.tianee.oa.subsys.crm.core.product.model.TeeCrmProductsModel;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeCrmProductsService extends TeeBaseService {
	@Autowired
	private TeeCrmProductsDao dao;
	@Autowired
	private TeeCrmProductsTypeDao productsTypeDao;

	@Autowired
	private TeePersonDao personDao;

	@Autowired
	private TeeAttachmentDao attachmentDao;

	@Autowired
	private TeeAttachmentService attachmentService;

	/**
	 * @function: 新建或编辑
	 * @author:
	 * @data: 2014年8月29日
	 * @param request
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson addOrUpdateService(HttpServletRequest request, TeeCrmProductsModel model) {
		TeeSysLog sysLog = TeeSysLog.newInstance();
		StringBuffer buffer = new StringBuffer();
		TeeJson json = new TeeJson();
		try {
			TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			List<TeeAttachment> attachmentsList = new ArrayList<TeeAttachment>();
			if (!TeeUtility.isNullorEmpty(model.getAttacheIds())) {
				attachmentsList = attachmentService.getAttachmentsByIds(model.getAttacheIds());
			}
			if (model.getSid() > 0) {
				TeeCrmProducts obj = dao.get(model.getSid());
				if (obj != null) {
					obj.setProductsNo(model.getProductsNo());
					obj.setProductsName(model.getProductsName());
					obj.setProductsType(model.getProductsType());
					obj.setProductsModel(model.getProductsModel());
					obj.setUnits(model.getUnits());
					obj.setProductsWide(model.getProductsWide());
					obj.setManufacturer(model.getManufacturer());
					obj.setManufacturerAdress(model.getManufacturerAdress());
					obj.setPrice(model.getPrice());
					obj.setSalePrice(model.getSalePrice());
					obj.setLastModifyUser(person);
					obj.setLastModifyTime(new Date());
					obj.setRemark(model.getRemark());
					obj.setStatus(model.getStatus());
					obj.setMinStock(model.getMinStock());
					obj.setMaxStock(model.getMaxStock());
					dao.update(obj);
					if (attachmentsList != null && attachmentsList.size() > 0) {
						for (TeeAttachment attach : attachmentsList) {
							attach.setModelId(String.valueOf(obj.getSid()));
							simpleDaoSupport.update(attach);
						}
					}
					sysLog.setType("046B");
					buffer.append("编辑成功,{产品编号 ：" + obj.getProductsNo() + ",产品名称：" + obj.getProductsName() + "}");
				}
			} else {
				TeeCrmProducts obj = new TeeCrmProducts();
				BeanUtils.copyProperties(model, obj);
				obj.setProductsNo(model.getProductsNo());
				obj.setProductsName(model.getProductsName());
				obj.setProductsType(model.getProductsType());
				obj.setProductsModel(model.getProductsModel());
				obj.setUnits(model.getUnits());
				obj.setProductsWide(model.getProductsWide());
				obj.setManufacturer(model.getManufacturer());
				obj.setManufacturerAdress(model.getManufacturerAdress());
				obj.setPrice(model.getPrice());
				obj.setSalePrice(model.getSalePrice());
				obj.setRemark(model.getRemark());
				obj.setCreateUser(person);
				obj.setStatus(model.getStatus());
				obj.setCreateTime(new Date());
				obj.setMinStock(model.getMinStock());
				obj.setMaxStock(model.getMaxStock());
				dao.save(obj);
				if (attachmentsList != null && attachmentsList.size() > 0) {
					for (TeeAttachment attach : attachmentsList) {
						attach.setModelId(String.valueOf(obj.getSid()));
						simpleDaoSupport.update(attach);
					}
				}
				sysLog.setType("046A");
				buffer.append("新建成功,{产品编号 ：" + obj.getProductsNo() + ",产品名称：" + obj.getProductsName() + "}");
			}
			sysLog.setRemark(buffer.toString());
			TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
			json.setRtState(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * @function: 产品管理列表
	 * @author: wyw
	 * @data: 2014年8月29日
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 *             TeeEasyuiDataGridJson
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getManageInfoList(TeeDataGridModel dm, HttpServletRequest request, TeeCrmProductsModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		Map requestDatas = TeeServletUtility.getParamMap(request);
		String productsNo = (String) requestDatas.get("productsNo");
		String productsName = (String) requestDatas.get("productsName");
		String manufacturer = (String) requestDatas.get("manufacturer");
		String manufacturerAdress = (String) requestDatas.get("manufacturerAdress");
		String createTimeStrMin = (String) requestDatas.get("createTimeStrMin");
		String createTimeStrMax = (String) requestDatas.get("createTimeStrMax");

		String queryStr = " 1=1";
		/*
		if (!TeePersonService.checkIsSuperAdmin(loginPerson, "")) {
			queryStr = " require.createUser.uuid= " + loginPerson.getUuid();
		}
		*/

		String hql = "from TeeCrmProducts require where " + queryStr;
		List param = new ArrayList();
		if (!TeeUtility.isNullorEmpty(productsNo)) {
			hql += " and require.productsNo like ?";
			param.add("%" + productsNo + "%");
		}
		if (!TeeUtility.isNullorEmpty(productsName)) {
			hql += " and require.productsName like ?";
			param.add("%" + productsName + "%");
		}
		if (!TeeUtility.isNullorEmpty(manufacturer)) {
			hql += " and require.manufacturer like ?";
			param.add("%" + manufacturer + "%");
		}
		if (!TeeUtility.isNullorEmpty(manufacturerAdress)) {
			hql += " and require.manufacturerAdress like ?";
			param.add("%" + manufacturerAdress + "%");
		}
		if (!TeeUtility.isNullorEmpty(createTimeStrMin)) {
			hql += " and require.createTime >= ?";
			param.add(TeeDateUtil.parseDate("yyyy-MM-dd hh:mm:ss", createTimeStrMin + " 00:00:00"));
		}
		if (!TeeUtility.isNullorEmpty(createTimeStrMax)) {
			hql += " and require.createTime <= ?";
			param.add(TeeDateUtil.parseDate("yyyy-MM-dd hh:mm:ss", createTimeStrMax + " 23:59:59"));
		}

		j.setTotal(dao.countByList("select count(*) " + hql, param));// 设置总记录数

		hql += " order by require.createTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeCrmProducts> list = dao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeCrmProductsModel> modelList = new ArrayList<TeeCrmProductsModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeCrmProductsModel modeltemp = parseReturnModel(list.get(i),false);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * @function: 转换成返回对象
	 * @author: wyw
	 * @data: 2014年8月29日
	 * @param obj
	 * @return TeeCrmProductsModel
	 */
	public TeeCrmProductsModel parseReturnModel(TeeCrmProducts obj,boolean isEdit) {
		TeeCrmProductsModel model = new TeeCrmProductsModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);
		
		if(obj.getProductsType()>0){
			TeeCrmProductsType productsType = productsTypeDao.get(obj.getProductsType());
			if(productsType != null){
				model.setProductsTypeName(productsType.getTypeName());
			}
		}
		model.setUnitsName(TeeCrmCodeManager.getChildSysCodeNameCodeNo("PRODUCTS_UNITS_TYPE", obj.getUnits()));
		
		if (!TeeUtility.isNullorEmpty(obj.getCreateTime())) {
			model.setCreateTimeStr(TeeUtility.getDateStrByFormat(obj.getCreateTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
		}
		if (!TeeUtility.isNullorEmpty(obj.getLastModifyTime())) {
			model.setLastModifyTimeStr(TeeUtility.getDateStrByFormat(obj.getLastModifyTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
		} else {
			model.setLastModifyTimeStr("");
		}

		if (obj.getCreateUser() != null) {
			model.setCreateUserId(obj.getCreateUser().getUuid());
			model.setCreateUserName(obj.getCreateUser().getUserName());
		}
		if (obj.getLastModifyUser() != null) {
			model.setLastModifyUserId(obj.getLastModifyUser().getUuid());
			model.setLastModifyUserName(obj.getLastModifyUser().getUserName());
		}

		List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.CRM_PRODUCTS, String.valueOf(obj.getSid()));
		List<TeeAttachmentModel> attacheModels = new ArrayList<TeeAttachmentModel>();
		StringBuffer attacheIdBuffer = new StringBuffer();
		for (TeeAttachment attach : attaches) {
			TeeAttachmentModel m = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, m);
			m.setUserId(attach.getUser().getUuid() + "");
			m.setUserName(attach.getUser().getUserName());
			int priv = 1+2;
			if(isEdit){
				priv = 1+2+4;
			}
			m.setPriv(priv);// 一共五个权限好像
									// 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachmentConst
			attacheModels.add(m);
			if (attacheIdBuffer.length() > 1) {
				attacheIdBuffer.append(",");
			}
			attacheIdBuffer.append(attach.getSid());
		}
		model.setAttacheModels(attacheModels);
		model.setAttacheIds(attacheIdBuffer.toString());
		return model;
	}

	/**
	 * @function: 根据sid查看详情
	 * @author: wyw
	 * @data: 2014年8月29日
	 * @param request
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson getInfoById(HttpServletRequest request, TeeCrmProductsModel model) {
		TeeJson json = new TeeJson();
		String isEdit = request.getParameter("isEdit");
		boolean editFlag = false;
		if("1".equals(isEdit)){
			editFlag = true;
		}
		if (model.getSid() > 0) {
			TeeCrmProducts obj = dao.get(model.getSid());
			if (obj != null) {
				model = parseReturnModel(obj,editFlag);
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
	 * @function: 删除
	 * @author: wyw
	 * @data: 2014年8月30日
	 * @param sids
	 * @return TeeJson
	 */
	public TeeJson deleteObjById(String sids) {
		TeeJson json = new TeeJson();
		if(TeeUtility.isNullorEmpty(sids)){
			sids = "0";
		}
		if(sids.endsWith(",")){
			sids = sids.substring(0,sids.length()-1);
		}
		TeeSysLog sysLog = TeeSysLog.newInstance();
		sysLog.setType("046C");
		StringBuffer buffer = new StringBuffer();
		
		Object values[]={};
		String hql = " from TeeCrmProducts where sid in (" + sids + ")";
		List<TeeCrmProducts> list = dao.executeQuery(hql, values);
		if(list!= null && list.size()>0){
			for(TeeCrmProducts obj:list){
				dao.deleteByObj(obj);
				if(buffer.length()>0){
					buffer.append(",");
				}
				buffer.append("{产品编号：" + obj.getProductsNo() + ",产品名称：" + obj.getProductsName() + "}");
			}
		}
		sysLog.setRemark("删除文件:[" +buffer.toString() + "]");
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}

}
