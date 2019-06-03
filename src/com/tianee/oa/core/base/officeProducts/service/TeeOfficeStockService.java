package com.tianee.oa.core.base.officeProducts.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeProduct;
import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeRecord;
import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeStock;
import com.tianee.oa.core.base.officeProducts.model.TeeOfficeStockModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;

@Service
public class TeeOfficeStockService extends TeeBaseService{
	@Autowired
	private TeeSmsManager smsManager;
	
	public void saveStockModel(TeeOfficeStockModel officeStockModel){
		TeeOfficeStock officeStock = new TeeOfficeStock();
		BeanUtils.copyProperties(officeStockModel, officeStock);
		TeeOfficeProduct op = (TeeOfficeProduct) simpleDaoSupport.get(TeeOfficeProduct.class, officeStockModel.getProductId());
		//原来库存
		long original = simpleDaoSupport.count("select sum(os.regCount) from TeeOfficeStock os where os.product.sid="+op.getSid(), null);
		officeStock.setProduct(op);
		int regType = officeStockModel.getRegType();
		int regCount = officeStockModel.getRegCount();
		int recordType = 0;
		String actionDesc = null;
		if(regType==4){//入库
			regCount = regCount;
			recordType = 1;
			actionDesc = "采购入库";
		}else if(regType==5 || regType==6){//维护和报废
			regCount = 0-regCount;
			if(regType==5){
				recordType = 6;
				actionDesc = "维护";
			}else{
				recordType = 2;
				actionDesc = "报废";
			}
		}
		officeStock.setRegCount(regCount);
		officeStock.setCreateTime(Calendar.getInstance());
		
		//登记记录
		TeeOfficeRecord record = new TeeOfficeRecord();
		record.setActionDesc(actionDesc);
		record.setActionTime(Calendar.getInstance());
		record.setCategoryName(op.getCategory().getCatName());
		record.setDepositoryName(op.getCategory().getOfficeDepository().getDeposName());
		record.setOriginStock(original+"");
		record.setRegCount(Math.abs(regCount)+"");
		record.setProCode(op.getProCode());
		record.setProName(op.getProName());
		record.setRecordType(recordType);
		record.setRegType(regType);
		record.setRegUserId(officeStockModel.getRegUserId());
		record.setRegUserName(officeStockModel.getRegUserName());
		record.setActionTimeDesc(TeeDateUtil.format(Calendar.getInstance()));
		simpleDaoSupport.save(record);
		
		simpleDaoSupport.save(officeStock);
		
		checkStockLimit(op.getSid());
	}
	
	/**
	 * 判断申请量是否超出库存量
	 * @param user
	 * @param billId
	 */
	public boolean checkOutOfStockCount(int productId,int regCount){
		long count = Integer.parseInt(""+simpleDaoSupport.count("select sum(os.regCount) from TeeOfficeStock os where os.product.sid="+productId, null));
		if(regCount>count){
			return false;
		}
		return true;
	}
	
	/**
	 * 检查相关产品的库存限制
	 */
	public void checkStockLimit(int productId){
		//获取当前产品
		TeeOfficeProduct product = (TeeOfficeProduct) simpleDaoSupport.get(TeeOfficeProduct.class,productId);
		//获取最低限制
		int min = product.getMinStock();
		int max = product.getMaxStock();
		//当前库存量
		long count = Integer.parseInt(""+simpleDaoSupport.count("select sum(os.regCount) from TeeOfficeStock os where os.product.sid="+productId, null));
		Map requestData = new HashMap();
		requestData.put("moduleNo", "028");
		
		requestData.put("remindUrl", "/system/core/base/officeProducts/manage/products.jsp");
		boolean alerm = false;
		if(count<=min){
			requestData.put("content", "用品["+product.getProName()+"]已达到最低库存预警，请进行处理。");
			alerm = true;
		}else if(count>=max){
			requestData.put("content", "用品["+product.getProName()+"]已达到最高库存预警，请进行处理。");
			alerm = true;
		}
		
		if(alerm){
			//发送给库存管理员
			Set<TeePerson> personSets = product.getCategory().getOfficeDepository().getAdmins();
			List list = new ArrayList();
			for(TeePerson p:personSets){
				list.add(p);
			}
			requestData.put("userList", list);
			smsManager.sendSms(requestData, null);
		}
		
	}
}
