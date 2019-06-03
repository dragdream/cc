package com.tianee.oa.core.base.hr.recruit.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.hr.TeeHrCodeManager;
import com.tianee.oa.core.base.hr.recruit.bean.TeeHrFilter;
import com.tianee.oa.core.base.hr.recruit.bean.TeeHrFilterItem;
import com.tianee.oa.core.base.hr.recruit.dao.TeeHrFilterDao;
import com.tianee.oa.core.base.hr.recruit.dao.TeeHrFilterItemDao;
import com.tianee.oa.core.base.hr.recruit.model.TeeHrFilterItemModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class TeeHrFilterItemService  extends TeeBaseService{
	@Autowired
	private TeeHrFilterDao hrFilterDao;
	@Autowired
	private TeeHrFilterItemDao filterItemDao;
	
	@Autowired
	private TeePersonDao personDao;

	public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public TeeJson addOrUpdate(HttpServletRequest request,
			TeeHrFilterItemModel model) throws IOException {
		TeeJson json = new TeeJson();
		try {
			TeePerson person = (TeePerson) request.getSession().getAttribute(
					TeeConst.LOGIN_USER);
			
			int filterId = TeeStringUtil.getInteger(model.getFilterId(), 0);//筛选记录
			TeeHrFilter  filter = null ;
			if(filterId > 0){
				filter = hrFilterDao.get(filterId);
			}
			int tranUserId = TeeStringUtil.getInteger(model.getNextTransactorStepId(), 0);//下一个筛选人
			TeePerson nextTransactorStep  = null;
			if(tranUserId > 0){
				nextTransactorStep = personDao.get(tranUserId);
			}
			
			String type = TeeStringUtil.getString(request.getParameter("type"), "0");//type 0-下一步 1- 结束筛选
			if (model.getSid() > 0) {
				TeeHrFilterItem obj = filterItemDao.get(model.getSid());
				BeanUtils.copyProperties(model, obj);
				obj.setFilter(filter);
				obj.setTransactorStep(person);
				obj.setNextTransactorStep(nextTransactorStep);
				filterItemDao.update(obj);	
			} else {
				TeeHrFilterItem obj = new TeeHrFilterItem();
				BeanUtils.copyProperties(model, obj);
				obj.setFilter(filter);
				obj.setTransactorStep(person);
				obj.setNextTransactorStep(nextTransactorStep);
				if(type.equals("0")){
					
				}else{//结束
					filter.setFilterState(model.getFilterState());
					hrFilterDao.update(filter);
				}
				filterItemDao.save(obj);	
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 封装对象
	 * 
	 * @date 2014-3-17
	 * @author
	 * @param obj
	 * @return
	 */
	public static TeeHrFilterItemModel parseModel(TeeHrFilterItem obj ,boolean isSimple) {
		TeeHrFilterItemModel model = new TeeHrFilterItemModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);
		
		
        if(!isSimple){

        }
        String nextTransactorStepId = "";
        String nextTransactorStepName = "";
        TeePerson nextPerson = obj.getNextTransactorStep();
        if(nextPerson != null){
        	nextTransactorStepId = nextPerson.getUuid() + "";
        	nextTransactorStepName = nextPerson.getUserName();
        }
        model.setNextTransactorStepId(nextTransactorStepId);
        model.setNextTransactorStepName(nextTransactorStepName);
        model.setTransactorStepId(obj.getTransactorStep().getUuid() + "");
        model.setTransactorStepName(obj.getTransactorStep().getUserName());
        
        String hrMethodDesc = "";//筛选方式
        if(!TeeUtility.isNullorEmpty(obj.getFilterMethod())){
        	hrMethodDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("HR_RECRUIT_FILTER", obj.getFilterMethod());
		}
        model.setFilterMethodDesc(hrMethodDesc);
		return model;
	}
	
}
