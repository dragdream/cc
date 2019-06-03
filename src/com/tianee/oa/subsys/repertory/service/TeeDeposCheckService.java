package com.tianee.oa.subsys.repertory.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.product.bean.TeeCrmProducts;
import com.tianee.oa.subsys.crm.core.product.bean.TeeCrmProductsType;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.subsys.repertory.bean.TeeDeposCheckItem;
import com.tianee.oa.subsys.repertory.bean.TeeDeposCheckRecord;
import com.tianee.oa.subsys.repertory.bean.TeeRepDepository;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 库存盘点操作类
 * @author kakalion
 *
 */
@Service
public class TeeDeposCheckService extends TeeBaseService{
	
	/**
	 * 添加盘库记录
	 * @return
	 */
	public void addCheckRecord(Map requestData){
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		
		String title = TeeStringUtil.getString(requestData.get("title"));
		int deposId = TeeStringUtil.getInteger(requestData.get("deposId"),0);
		
		TeeDeposCheckRecord checkRecord = new TeeDeposCheckRecord();
		checkRecord.setTitle(title);
		checkRecord.setCheckTime(Calendar.getInstance());
		checkRecord.setCheckUser(loginUser);
		checkRecord.setFlag(1);
		
		if(deposId!=0){
			TeeRepDepository depository = new TeeRepDepository();
			depository.setSid(deposId);
			checkRecord.setDepository(depository);
		}
		
		//获取全部产品
		List<TeeCrmProducts> productList = simpleDaoSupport.find("from TeeCrmProducts", null);
		TeeDeposCheckItem checkItem = null;
		for(TeeCrmProducts product:productList){
			checkItem = new TeeDeposCheckItem();
			checkItem.setCheckRecord(checkRecord);
			checkItem.setProduct(product);
			
			//获取当前库存量
			long inCount = simpleDaoSupport.count("select case when sum(rr.count) is null then 0 else sum(rr.count) end from TeeRepRecord rr where rr.products.sid=? and rr.type=1"+(deposId==0?"":" and rr.depository.sid="+deposId), new Object[]{product.getSid()});
			long outCount = simpleDaoSupport.count("select case when sum(rr.count) is null then 0 else sum(rr.count) end from TeeRepRecord rr where rr.products.sid=? and rr.type=2"+(deposId==0?"":" and rr.depository.sid="+deposId), new Object[]{product.getSid()});
			checkItem.setOriginalCount((int)(inCount-outCount));
			
			checkRecord.getCheckItems().add(checkItem);
		}
		
		simpleDaoSupport.save(checkRecord);
		requestData.put("sid", checkRecord.getSid());
	}
	
	/**
	 * 删除盘库记录
	 * @return
	 */
	public void delCheckRecord(Map requestData){
		int sid = TeeStringUtil.getInteger(requestData.get("sid"),0);
		simpleDaoSupport.delete(TeeDeposCheckRecord.class, sid);
	}
	
	/**
	 * 结束盘库记录，不允许修改
	 * @param request
	 * @return
	 */
	public void finishCheckRecord(Map requestData){
		int sid = TeeStringUtil.getInteger(requestData.get("sid"), 0);
		TeeDeposCheckRecord checkRecord = (TeeDeposCheckRecord) simpleDaoSupport.get(TeeDeposCheckRecord.class, sid);
		if(checkRecord!=null){
			checkRecord.setFlag(0);
		}
	}
	
	/**
	 * 列出库存盘点记录（有权限）
	 * @return
	 */
	public TeeEasyuiDataGridJson listCheckRecord(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		
		StringBuffer hql = new StringBuffer("from TeeDeposCheckRecord ");
		List params = new ArrayList();
		if(!TeePersonService.checkIsAdminPriv(loginUser)){//非管理员，只允许查看自己有权限的盘点记录
			
			hql.append(" where checkUser.uuid=? or depository.manager.uuid=? ");
			params.add(loginUser.getUuid());
			params.add(loginUser.getUuid());
		}
		
		
		List<TeeDeposCheckRecord> list = simpleDaoSupport.pageFind(hql.toString()+" order by sid desc"
		, dm.getFirstResult(), dm.getRows(), params.toArray());
		long total = simpleDaoSupport.count("select count(*) "+hql.toString(), params.toArray());
		List<Map> dataList = new ArrayList();
		Map data = null;
		for(TeeDeposCheckRecord record:list){
			data = new HashMap();
			data.put("sid", record.getSid());
			data.put("title", record.getTitle());
			data.put("checkTime", TeeDateUtil.format(record.getCheckTime()));
			data.put("checkUser", record.getCheckUser().getUserName());
			data.put("checkUserId", record.getCheckUser().getUuid());
			data.put("flag", record.getFlag());
			if(record.getDepository()!=null){
				data.put("deposName", record.getDepository().getName());
			}
			dataList.add(data);
		}
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(dataList);
		return dataGridJson;
	}
	
	
	/**
	 * 列出库存盘点项
	 * @return
	 */
	public TeeEasyuiDataGridJson listCheckItem(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		
		int recordSid = TeeStringUtil.getInteger(requestData.get("recordSid"), 0);
		String words = TeeStringUtil.getString(requestData.get("words"));
		
		StringBuffer hql = new StringBuffer("from TeeDeposCheckItem c");
		List params = new ArrayList();
		
		hql.append(" where c.checkRecord.sid=? ");
		if(!"".equals(words)){
			hql.append(" and (");
			hql.append(" c.product.productsName like '%"+TeeDbUtility.formatString(words)+"%' ");
			hql.append(" or c.product.productsNo like '%"+TeeDbUtility.formatString(words)+"%' ");
			hql.append(" or exists (select 1 from TeeCrmProductsType t where t.sid=c.product.productsType and t.typeName like '%"+TeeDbUtility.formatString(words)+"%') ");
			hql.append(")");
		}
		params.add(recordSid);
		
		
		List<TeeDeposCheckItem> list = simpleDaoSupport.pageFind(hql.toString()+" order by originalCount desc"
		, dm.getFirstResult(), dm.getRows(), params.toArray());
		long total = simpleDaoSupport.count("select count(*) "+hql.toString(), params.toArray());
		List<Map> dataList = new ArrayList();
		Map data = null;
		TeeCrmProducts products = null;
		TeeCrmProductsType productsType = null;
		for(TeeDeposCheckItem item:list){
			data = new HashMap();
			products = item.getProduct();
			data.put("sid", item.getSid());
			if(products!=null){
				data.put("proName", products.getProductsName());//名称
				data.put("proNo", products.getProductsNo());//名称
				data.put("proModel", products.getProductsModel());//规格
				productsType = (TeeCrmProductsType) simpleDaoSupport.get(TeeCrmProductsType.class, item.getProduct().getProductsType());
				if(productsType!=null){
					data.put("type", productsType.getTypeName());
				}
				data.put("units", TeeCrmCodeManager.getChildSysCodeNameCodeNo("PRODUCTS_UNITS_TYPE", products.getUnits()));
			}
			data.put("originalCount", item.getOriginalCount());
			data.put("manualCount", item.getManualCount());
			dataList.add(data);
		}
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(dataList);
		return dataGridJson;
	}
	
	/**
	 * 更新盘点项
	 * @return
	 */
	public void updateCheckItems(Map requestData){
		String jsonData = TeeStringUtil.getString(requestData.get("jsonData"));
		List<Map<String, String>> list = TeeJsonUtil.JsonStr2MapList(jsonData);
		TeeDeposCheckItem checkItem = null;
		for(Map<String, String> data:list){
			checkItem = (TeeDeposCheckItem) simpleDaoSupport.get(TeeDeposCheckItem.class, TeeStringUtil.getInteger(data.get("sid"), 0));
			checkItem.setManualCount(TeeStringUtil.getInteger(data.get("manualCount"), 0));
		}
	}
}
