package com.tianee.oa.subsys.repertory.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonManagerI;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.subsys.repertory.bean.TeeRepDepository;
import com.tianee.oa.subsys.repertory.model.TeeRepDepositoryModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeRepDepositoryService extends TeeBaseService{
	
	public void save(TeeRepDepositoryModel depositoryModel){
		TeeRepDepository depository = Model2Entity(depositoryModel);
		simpleDaoSupport.save(depository);
	}
	
	public void update(TeeRepDepositoryModel depositoryModel){
		TeeRepDepository depository = Model2Entity(depositoryModel);
		simpleDaoSupport.update(depository);
	}
	
	public void delete(int sid){
		simpleDaoSupport.delete(TeeRepDepository.class, sid);
	}
	
	public TeeRepDepositoryModel get(int sid){
		TeeRepDepository depository = 
				(TeeRepDepository) simpleDaoSupport.get(TeeRepDepository.class, sid);
		return Entity2Model(depository);
	}
	
	public TeeEasyuiDataGridJson depositoryList(){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List<TeeRepDepository> list = simpleDaoSupport.find("from TeeRepDepository order by sid asc", null);
		List<TeeRepDepositoryModel> modelList = new ArrayList();
		for(TeeRepDepository depository:list){
			modelList.add(Entity2Model(depository));
		}
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(Long.parseLong(modelList.size()+""));
		return dataGridJson;
	}
	
	public TeeEasyuiDataGridJson list(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		int deposId = TeeStringUtil.getInteger(requestData.get("deposId"), 0);
		
		String hql1 = "";
		
		if(TeePersonService.checkIsAdminPriv(loginUser)){//管理员权限
			
			if(deposId==0){//所有仓库
				hql1 = "(select case when sum(rr.count) is null then 0 else sum(rr.count) end from TeeRepRecord rr where rr.products.sid=product.sid and rr.type=1) as inCount,"
						+ "(select case when sum(rr.count) is null then 0 else sum(rr.count) end from TeeRepRecord rr where rr.products.sid=product.sid and rr.type=2) as outCount,"
						+ "(select case when sum(rr.sum) is null then 0 else sum(rr.sum) end from TeeRepRecord rr where rr.products.sid=product.sid and rr.type=1)-(select case when sum(rr.sum) is null then 0 else sum(rr.sum) end from TeeRepRecord rr where rr.products.sid=product.sid and rr.type=2) as amount,";
			}else{//指定仓库
				hql1 = "(select case when sum(rr.count) is null then 0 else sum(rr.count) end from TeeRepRecord rr where rr.products.sid=product.sid and rr.type=1 and rr.depository.sid="+deposId+") as inCount,"
						+ "(select case when sum(rr.count) is null then 0 else sum(rr.count) end from TeeRepRecord rr where rr.products.sid=product.sid and rr.type=2 and rr.depository.sid="+deposId+") as outCount,"
						+ "(select case when sum(rr.sum) is null then 0 else sum(rr.sum) end from TeeRepRecord rr where rr.products.sid=product.sid and rr.type=1 and rr.depository.sid="+deposId+")-(select case when sum(rr.sum) is null then 0 else sum(rr.sum) end from TeeRepRecord rr where rr.products.sid=product.sid and rr.type=2 and rr.depository.sid="+deposId+") as amount,";
			}
			
		}else{//库管员权限
			
			//获取所有有权限的仓库
			List<TeeRepDepository> depositories = simpleDaoSupport.find("from TeeRepDepository where manager.uuid="+loginUser.getUuid(), null);
			StringBuffer repIds = new StringBuffer();
			for(int i=0;i<depositories.size();i++){
				repIds.append(depositories.get(i).getSid());
				if(i!=depositories.size()-1){
					repIds.append(",");
				}	
			}
			
			if(repIds.length()==0){
				repIds.append("0");
			}
			
			if(deposId==0){//所有仓库
				hql1 = "(select case when sum(rr.count) is null then 0 else sum(rr.count) end from TeeRepRecord rr where rr.products.sid=product.sid and rr.type=1 and rr.depository.sid in ("+repIds.toString()+")) as inCount,"
						+ "(select case when sum(rr.count) is null then 0 else sum(rr.count) end from TeeRepRecord rr where rr.products.sid=product.sid and rr.type=2 and rr.depository.sid in ("+repIds.toString()+")) as outCount,"
						+ "(select case when sum(rr.sum) is null then 0 else sum(rr.sum) end from TeeRepRecord rr where rr.products.sid=product.sid and rr.type=1 and rr.depository.sid in ("+repIds.toString()+"))-(select case when sum(rr.sum) is null then 0 else sum(rr.sum) end from TeeRepRecord rr where rr.products.sid=product.sid and rr.type=2 and rr.depository.sid in ("+repIds.toString()+")) as amount,";
			}else{//指定仓库
				hql1 = "(select case when sum(rr.count) is null then 0 else sum(rr.count) end from TeeRepRecord rr where rr.products.sid=product.sid and rr.type=1 and rr.depository.sid="+deposId+") as inCount,"
						+ "(select case when sum(rr.count) is null then 0 else sum(rr.count) end from TeeRepRecord rr where rr.products.sid=product.sid and rr.type=2 and rr.depository.sid="+deposId+") as outCount,"
						+ "(select case when sum(rr.sum) is null then 0 else sum(rr.sum) end from TeeRepRecord rr where rr.products.sid=product.sid and rr.type=1 and rr.depository.sid="+deposId+")-(select case when sum(rr.sum) is null then 0 else sum(rr.sum) end from TeeRepRecord rr where rr.products.sid=product.sid and rr.type=2 and rr.depository.sid="+deposId+") as amount,";
			}
			
		}
		
		StringBuffer sb = new StringBuffer("select "
				+ hql1
				+ "product.productsName as proName,"
				+ "product.productsNo as proCode,"
				+ "product.productsModel as proModel,"
				+ "product.units as unit,"
				+ "product.price as price,"
				+ "product.sid as proId,"
				+ "product.minStock as minStock,"
				+ "product.maxStock as maxStock,"
				+ "product.salePrice as salePrice "
				+ " from TeeCrmProducts product ");
		
		sb.append(" order by product.sid asc");
		
		List<Map> modelList = null;
		modelList = simpleDaoSupport.getMaps(sb.toString(), null);
		
		for(Map data : modelList){
			data.put("unit", TeeCrmCodeManager.getChildSysCodeNameCodeNo("PRODUCTS_UNITS_TYPE", String.valueOf(data.get("unit"))));
		}
		
		dataGridJson.setRows(modelList);
		
		return dataGridJson;
	}
	
	public TeeEasyuiDataGridJson detailList(Map requestData,TeeDataGridModel dm){
		int proId = TeeStringUtil.getInteger(requestData.get("proId"), 0);
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		int deposId = TeeStringUtil.getInteger(requestData.get("deposId"), 0);
		Calendar time1 = TeeDateUtil.parseCalendar(TeeStringUtil.getString(requestData.get("time1")));
		Calendar time2 = TeeDateUtil.parseCalendar(TeeStringUtil.getString(requestData.get("time2")));
		
		if(time1!=null){
			time1.set(Calendar.HOUR, 0);
			time1.set(Calendar.MINUTE, 0);
			time1.set(Calendar.SECOND, 0);
			time1.set(Calendar.MILLISECOND, 0);
		}else{
			time1 = Calendar.getInstance();
			time1.set(Calendar.YEAR, 1900);
			time1.set(Calendar.HOUR, 0);
			time1.set(Calendar.MINUTE, 0);
			time1.set(Calendar.SECOND, 0);
			time1.set(Calendar.MILLISECOND, 0);
		}
		
		if(time2!=null){
			time2.set(Calendar.HOUR, 23);
			time2.set(Calendar.MINUTE, 59);
			time2.set(Calendar.SECOND, 59);
			time2.set(Calendar.MILLISECOND,999);
		}else{
			time2 = Calendar.getInstance();
			time2.set(Calendar.YEAR, 2099);
			time2.set(Calendar.HOUR, 23);
			time2.set(Calendar.MINUTE, 59);
			time2.set(Calendar.SECOND, 59);
			time2.set(Calendar.MILLISECOND,999);
		}
		
		
		StringBuffer sb = new StringBuffer("select "
				+ "rr.billNo as billNo,"
				+ "rr.type as type,"
				+ "rr.crTime as time,"
				+ "rr.billNo as billNo,"
				+ "rr.count as count,"
				+ "rr.price as price,"
				+ "rr.products.productsName as proName,"
				+ "rr.runId as runId,"
				+ "rr.sum as sum"
				+ " from TeeRepRecord rr where rr.crTime>=? and rr.crTime<=? and rr.products.sid=? ");
		
		if(TeePersonService.checkIsAdminPriv(loginUser)){//管理员权限
			
			if(deposId==0){//所有仓库
				
			}else{//指定仓库
				sb.append(" and rr.depository.sid="+deposId);
			}
			
		}else{//库管员权限
			
			//获取所有有权限的仓库
			List<TeeRepDepository> depositories = simpleDaoSupport.find("from TeeRepDepository where manager.uuid="+loginUser.getUuid(), null);
			StringBuffer repIds = new StringBuffer();
			for(int i=0;i<depositories.size();i++){
				repIds.append(depositories.get(i).getSid());
				if(i!=depositories.size()-1){
					repIds.append(",");
				}
			}
			
			if(repIds.length()==0){
				repIds.append("0");
			}
			
			if(deposId==0){//所有仓库
				sb.append(" and rr.depository.sid in ("+repIds.toString()+")");
			}else{//指定仓库
				sb.append(" and rr.depository.sid="+deposId);
			}
			
		}
		
		sb.append(" order by rr.sid desc");
		
		List<Map> modelList = null;
		modelList = simpleDaoSupport.getMaps(sb.toString(), new Object[]{time1,time2,proId});
		
		dataGridJson.setRows(modelList);
		
		return dataGridJson;
	}
	
	/**
	 * 清空库存记录
	 * @param requestData
	 */
	public void clear(Map requestData){
		int sid = TeeStringUtil.getInteger(requestData.get("sid"), 0);
		simpleDaoSupport.executeUpdate("delete from TeeRepRecord where depository.sid=?", new Object[]{sid});
	}
	
	public static TeeRepDepository Model2Entity(TeeRepDepositoryModel depositoryModel){
		TeeRepDepository repDepository = new TeeRepDepository();
		BeanUtils.copyProperties(depositoryModel, repDepository);
		if(depositoryModel.getManagerId()!=0){
			TeePerson manager = new TeePerson();
			manager.setUuid(depositoryModel.getManagerId());
			repDepository.setManager(manager);
		}
		return repDepository;
	}
	
	public static TeeRepDepositoryModel Entity2Model(TeeRepDepository depository){
		TeeRepDepositoryModel depositoryModel = new TeeRepDepositoryModel();
		BeanUtils.copyProperties(depository, depositoryModel);
		if(depository.getManager()!=null){
			depositoryModel.setManagerId(depository.getManager().getUuid());
			depositoryModel.setManagerName(depository.getManager().getUserName());
		}
		return depositoryModel;
	}
}
