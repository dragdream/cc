package com.tianee.oa.subsys.crm.core.contract.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.crm.core.contract.bean.TeeCrmContract;
import com.tianee.oa.subsys.crm.core.contract.bean.TeeCrmContractProductItem;
import com.tianee.oa.subsys.crm.core.contract.dao.TeeCrmContractProductItemDao;
import com.tianee.oa.subsys.crm.core.contract.model.TeeCrmContractProductItemModel;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeCrmContractProductItemService  extends TeeBaseService{
	@Autowired
	TeeCrmContractProductItemDao contractProductItemDao;

	/**
	 * 新建或者更新
	 * @param request 
	 * @param object 模型
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeCrmContractProductItemModel object){
		TeeJson json = new TeeJson();
		TeeCrmContract contract = new TeeCrmContract();
		if(object.getSid() > 0){
			
			
		}else{
			BeanUtils.copyProperties(object, contract);

		}
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据合同Id  获取产品列表模型
	 * @param contractId
	 * @return
	 */
	public List<TeeCrmContractProductItemModel> getProductItemModelList(int contractId){
		List<TeeCrmContractProductItemModel> modelList = new ArrayList<TeeCrmContractProductItemModel>();
		List<TeeCrmContractProductItem> list = contractProductItemDao.getCrmProductsItemByContractId(contractId);
		for (int i = 0; i < list.size(); i++) {
			TeeCrmContractProductItemModel model = parseModel(list.get(i));
			modelList.add(model);
		}
		return modelList;
	}
	
	/**
	 * 转模型
	 * @param item
	 * @return
	 */
	public TeeCrmContractProductItemModel parseModel (TeeCrmContractProductItem item){
		TeeCrmContractProductItemModel model = new TeeCrmContractProductItemModel();
		if(item == null){
			return model;
		}
		BeanUtils.copyProperties(item, model);
		String unitsDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("PRODUCTS_UNITS_TYPE",item.getUnits());//计量单位（包/袋等）
		model.setUnitsDesc(unitsDesc);
		return model;
	}
}
