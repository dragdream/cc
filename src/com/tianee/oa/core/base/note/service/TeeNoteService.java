package com.tianee.oa.core.base.note.service;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.note.bean.TeeNote;
import com.tianee.oa.core.base.note.dao.TeeNoteDao;
import com.tianee.oa.core.base.note.model.TeeNoteModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author syl
 *
 */
@Service
public class TeeNoteService  extends TeeBaseService{
	@Autowired
	private TeeNoteDao noteDao;
	
	
	/**
	 * @author syl
	 * 新增 或者 更新
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeNoteModel model) {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		TeeNote note  = new TeeNote();
		Date date = new Date();
		int sid = 0;
		sid = model.getSid();
		if(model.getSid() > 0){//编辑
			TeeNote oldNote = noteDao.getById(model.getSid());
			if(oldNote != null){
				oldNote.setContent(model.getContent());
				oldNote.setTop(model.getTop());
				oldNote.setLeft(model.getLeft());
				oldNote.setColor(model.getColor());
				noteDao.updateNote(oldNote);
			}else{
				json.setRtState(false);
				json.setRtMsg("未找到相关便签");
				return json;
			}
		}else{//新建
			note.setContent(model.getContent());
			note.setTop(model.getTop());
			note.setLeft(model.getLeft());
			note.setUser(person);
			note.setCreateTime(date);
			note.setColor(model.getColor());
			note.setHeight(264);
			note.setWidth(318);
			note.setOpenFlag(1);
			Random rand = new Random();
			note.setX(rand.nextInt(500));
			note.setY(rand.nextInt(500));
			noteDao.addNote(note);
			sid = note.getSid();
		}
		json.setRtState(true);
		json.setRtData(sid);
		return json;
	}
	
	/**
	 * 更新便签的位置和大小
	 * @param sid
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void updatePosAndSize(int sid,int x,int y,int width,int height,String color) {
		simpleDaoSupport.executeUpdate("update TeeNote note set note.x=?,note.y=?,note.width=?,note.height=?,note.color=? where note.sid=?",
				new Object[]{x,y,width,height,color,sid});
	}
	
	/**
	 * byId查找
	 * @author syl
	 * @date 2014-2-13
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request, TeeNoteModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeNote note = noteDao.getById(model.getSid());
			model = parseModel(note);
		}
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 转模型
	 * @author syl
	 * @date 2014-2-13
	 * @param note
	 * @return
	 */
	public TeeNoteModel parseModel(TeeNote note){
		TeeNoteModel model = new TeeNoteModel();
		if(note == null){
			return model;
		}
		model.setSid(note.getSid());
		model.setContent(note.getContent());
		model.setUserId(note.getUser().getUuid() + "");
		model.setUserName(note.getUser().getUserName());
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		model.setCreateTimeStr(TeeUtility.getDateStrByFormat(note.getCreateTime(), format));
		model.setOpenFlag(note.getOpenFlag());
		model.setColor(note.getColor());
		model.setY(note.getY());
		model.setX(note.getX());
		model.setWidth(note.getWidth());
		model.setHeight(note.getHeight());
		model.setTop(note.getTop());
		model.setLeft(note.getLeft());
		return model;
	}
	
	/**
	 * 个人桌面 便签
	 * @author syl
	 * @date 2014-2-13
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson selectPersonalNote(HttpServletRequest request, TeeNoteModel model) {
		
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		List<TeeNote> list = noteDao.selectPersonalNote(person, model);
		List<TeeNoteModel> listModel = new ArrayList<TeeNoteModel>();
		for (int i = 0; i < list.size(); i++) {
			TeeNote note = list.get(i);
			TeeNoteModel noteModel = parseModel(note);
			listModel.add(noteModel);
		}
		json.setRtData(listModel);
		json.setRtState(true);
		return json;
	
	}
	
	/**
	 * 删除指定便签
	 * @param sid
	 */
	public void del(int sid) {
		simpleDaoSupport.delete(TeeNote.class, sid);
	}

	
	/**
	 * 更新位置
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson updateLoc(HttpServletRequest request, TeeNoteModel model) {
	    TeeJson json=new  TeeJson();
	    TeeNote note=(TeeNote) simpleDaoSupport.get(TeeNote.class,model.getSid());
	    note.setTop(model.getTop());
	    note.setLeft(model.getLeft());
		return json;
	}
}

