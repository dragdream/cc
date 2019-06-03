package com.tianee.oa.core.base.vote.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.vote.bean.TeeVoteItem;
import com.tianee.oa.core.base.vote.bean.TeeVoteItemPerson;
import com.tianee.oa.core.base.vote.dao.TeeVoteItemDao;
import com.tianee.oa.core.base.vote.dao.TeeVoteItemPersonDao;
import com.tianee.oa.core.base.vote.model.TeeVoteItemModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeVoteItemService extends TeeBaseService{

	@Autowired
	private TeeVoteItemDao voteItemDao;
	
	@Autowired
	private TeeVoteItemPersonDao itemPersonDao;
	
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
	public TeeJson addOrUpdate(HttpServletRequest request, TeeVoteItemModel model) throws IOException, ParseException {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		TeeVoteItem vote = new TeeVoteItem();

		if(model.getSid() > 0){
			TeeVoteItem oldVote  = voteItemDao.getById(model.getSid());
			if(oldVote != null){
				
				voteItemDao.updateObj(oldVote);
			}else{
				json.setRtState(false);
				json.setRtMsg("该投票已被删除！");
				return json;
			}
		}else{
		
			voteItemDao.add(vote);
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		
		return json;
	}
	
	
	
	
	/**
	 * 获取有权限的 投票管理
	 * @author syl
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson selectPostVote(HttpServletRequest request, TeeVoteItemModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		boolean isSuperAdmin = TeePersonService.checkIsSuperAdmin(person, "");
		List<TeeVoteItem> list = voteItemDao.getVoteItemByVote(person, model );
		List<TeeVoteItemModel> listModel = new ArrayList<TeeVoteItemModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
		json.setRtData(listModel);
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
	public TeeVoteItemModel parseModel(TeeVoteItem vote){
		TeeVoteItemModel model = new TeeVoteItemModel();
		if(vote == null){
			return model;
		}
		BeanUtils.copyProperties(vote, model);
	    
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
	public TeeJson deleteById(HttpServletRequest request, TeeVoteItemModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			voteItemDao.delById(model.getSid());
		}
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	/**
	 * 
	 * @author syl 删除所有会议室
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson deleteAll(HttpServletRequest request, TeeVoteItemModel model) {
		TeeJson json = new TeeJson();
		voteItemDao.delAll();
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	
	/**
	 * 
	 * @author syl 
	 *  查询 ById
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request, TeeVoteItemModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeVoteItem out = voteItemDao.getById(model.getSid());
			if(out !=null){
				model = parseModel(out);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("该投票项可能已被删除！");
		return json;
	}
		
	
	public TeeVoteItem getItemById(int id){
		TeeVoteItem item = voteItemDao.get(id);
		return item;
	}
	
	public void updateItem(TeeVoteItem i){
		voteItemDao.updateItem(i);
	}
	
	public List<TeeVoteItem> getItemListByVoteId(int voteId){
		List<TeeVoteItem> list = voteItemDao.getItemListByVoteId(voteId);
		return list;
	}

	public void saveItemPerson(TeeVoteItemPerson itemPerson){
		itemPersonDao.save(itemPerson);
	}
	
}
