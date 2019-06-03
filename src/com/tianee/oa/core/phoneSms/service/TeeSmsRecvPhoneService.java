package com.tianee.oa.core.phoneSms.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.phoneSms.bean.TeeSmsRecvPhone;
import com.tianee.oa.core.phoneSms.dao.TeeSmsRecvPhoneDao;
import com.tianee.oa.core.phoneSms.model.TeeSmsRecvPhoneModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeSmsRecvPhoneService extends TeeBaseService{
	@Autowired
	private TeeSmsRecvPhoneDao recvPhoneDao;
	/**
	 * 保存或更新
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeSmsRecvPhoneModel model) {
		TeeJson json = new TeeJson();
		TeeSmsRecvPhone recvPhone = new TeeSmsRecvPhone();
		if(model.getSid() > 0){
		    recvPhone  = recvPhoneDao.getById(model.getSid());
			if(recvPhone != null){
				BeanUtils.copyProperties(model, recvPhone);
				recvPhoneDao.updateRecvPhoneInfo(recvPhone);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关外出信息！");
				return json;
			}
		}else{
			BeanUtils.copyProperties(model, recvPhone);
			recvPhoneDao.addRecvPhoneInfo(recvPhone);
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * 对象转换
	 * @author nieyi
	 * @param recvPhone
	 * @return
	 */
	public TeeSmsRecvPhoneModel parseModel(TeeSmsRecvPhone recvPhone){
		TeeSmsRecvPhoneModel model = new TeeSmsRecvPhoneModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(recvPhone == null){
			return null;
		}
		BeanUtils.copyProperties(recvPhone, model);
		return model;
	}
	
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson deleteByIdService(HttpServletRequest request,String sids) {
		TeeJson json = new TeeJson();
		recvPhoneDao.delByIds(sids);
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request, TeeSmsRecvPhoneModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeSmsRecvPhone recvPhone = recvPhoneDao.getById(model.getSid());
			if(recvPhone !=null){
				model = parseModel(recvPhone);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关记录！");
		return json;
	}

	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		return recvPhoneDao.datagird(dm, requestDatas);
	}


	public List<TeeSmsRecvPhoneModel> getTotalByConditon(Map requestDatas) {
		return recvPhoneDao.getTotalByConditon(requestDatas);
	}
	
}