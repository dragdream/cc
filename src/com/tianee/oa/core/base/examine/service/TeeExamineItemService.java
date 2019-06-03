package com.tianee.oa.core.base.examine.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.examine.bean.TeeExamineGroup;
import com.tianee.oa.core.base.examine.bean.TeeExamineItem;
import com.tianee.oa.core.base.examine.dao.TeeExamineGroupDao;
import com.tianee.oa.core.base.examine.dao.TeeExamineItemDao;
import com.tianee.oa.core.base.examine.model.TeeExamineItemModel;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author syl
 */
@Service
public class TeeExamineItemService extends TeeBaseService {
	@Autowired
	private TeeExamineItemDao examineItemDao;
	
	@Autowired
	private TeeExamineGroupDao examineGroupDao;
	
	@Autowired
	private TeePersonDao personDao;



	/**
	 * @author syl
	 * 新增 或者 更新
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeExamineItemModel model) throws IOException, ParseException {
		TeeJson json = new TeeJson();
		TeeExamineItem item = new TeeExamineItem();
		BeanUtils.copyProperties(model, item);
		
		int groupId = TeeStringUtil.getInteger(request.getParameter("groupId"), 0);
		TeeExamineGroup group = examineGroupDao.getById(groupId);
		item.setGroup(group);
		if(model.getSid() > 0){
			TeeExamineItem meetRoom  = examineItemDao.getById(model.getSid());
			if(meetRoom != null){
				int sid = meetRoom.getSid();
				BeanUtils.copyProperties(item, meetRoom);
				examineItemDao.updateObj(meetRoom);
			}else{
				json.setRtState(false);
				json.setRtMsg("该指标集明细已被删除！");
				return json;
			}
		}else{
			examineItemDao.add(item);
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	
	}
	
	/**
	 * 根据指标集获取 指标明细
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @return
	 */
	public TeeJson getAllByGroupId(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int groupId = TeeStringUtil.getInteger(request.getParameter("groupId"),0);
		List<TeeExamineItem> list = examineItemDao.getAllByGroupId(groupId);
		List<TeeExamineItemModel> modelList = new ArrayList<TeeExamineItemModel>();
		for (int i = 0; i < list.size(); i++) {
			modelList.add(parseModel(list.get(i), false));
		}
		json.setRtData(modelList);
		json.setRtState(true);
		return json;
	}

	/**
	 * 对象转换
	 * @author syl
	 * @date 2014-1-29
	 * @param out
	 * @return
	 */
	public static TeeExamineItemModel parseModel(TeeExamineItem room , boolean isSimple){
		TeeExamineItemModel model = new TeeExamineItemModel();
		if(room == null){
			return model;
		}
		BeanUtils.copyProperties(room, model);
		if(TeeUtility.isNullorEmpty(model.getItemDesc())){
			model.setItemDesc("");
		}
		return model;
	}
	
	
	/**
	 * 
	 * @author syl 删除ById
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson deleteById(HttpServletRequest request, TeeExamineItemModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			examineItemDao.delById(model.getSid());
		}
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	/**
	 * 查询 ById  
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson selectById(HttpServletRequest request, TeeExamineItemModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeExamineItem group = examineItemDao.getById(model.getSid());
			if(group != null){
				json.setRtData(parseModel(group, false));
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("该记录已被删除!");
	
		return json;
	}
		
}
