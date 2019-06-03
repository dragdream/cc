package com.tianee.oa.core.base.fileNetdisk.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileComment;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileCommentDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileNetdiskDao;
import com.tianee.oa.core.base.fileNetdisk.model.TeeFileCommentModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeFileCommentService extends TeeBaseService {

	@Autowired
	private TeeFileCommentDao teeFileCommentDao;
	
	@Autowired
	@Qualifier("fileNetdiskDao")
	private TeeFileNetdiskDao fileNetdiskDao;

	/**
	 * 添加评论
	 * */
	public TeeJson addFileComment(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int fileId = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		String content = request.getParameter("content");
		TeeFileComment comment=new TeeFileComment();
		comment.setContent(content);//评论内容
		Date date=new Date();
		comment.setCreateTime(date);//评论时间
		comment.setFileId(fileId);//文件id
		comment.setUser(loginPerson);
		Serializable save = teeFileCommentDao.save(comment);
		int integer = TeeStringUtil.getInteger(save, 0);
		TeeFileNetdisk netdisk = fileNetdiskDao.get(fileId);
		if(netdisk!=null){
			int huiFuCount = netdisk.getHuiFuCount();
			huiFuCount=huiFuCount+1;
			netdisk.setHuiFuCount(huiFuCount);
			fileNetdiskDao.update(netdisk);
		}
		if(integer>0){
			json.setRtState(true);
			json.setRtMsg("提交成功");
		}else{
			json.setRtState(false);
			json.setRtMsg("提交失败");
		}
		return json;
	}

	/**
	 * 删除评论
	 * */
	public TeeJson deleteFileComment(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			teeFileCommentDao.delete(sid);
			json.setRtState(true);
			json.setRtMsg("删除成功");
		}else{
			json.setRtState(false);
			json.setRtMsg("删除失败");
		}
		return json;
	}

	/**
	 * 查找出此文件的所有的评论
	 * */
	public TeeEasyuiDataGridJson getFileComment(HttpServletRequest request,TeeDataGridModel dm) {
		TeeEasyuiDataGridJson easyJson=new TeeEasyuiDataGridJson();
		List<TeeFileCommentModel> modelList=new ArrayList<TeeFileCommentModel>();
		int fileId = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		String sql="from TeeFileComment where fileId=?";
		List<TeeFileComment> pageFind = teeFileCommentDao.pageFind(sql+" order by sid desc", dm.getFirstResult(), dm.getRows(), new Object[]{fileId});
		Long count = teeFileCommentDao.count("select count(*) "+sql, new Object[]{fileId});
		if(pageFind!=null && pageFind.size()>0){
			for(TeeFileComment comm:pageFind){
				TeeFileCommentModel model=new TeeFileCommentModel();
				model.setContent(comm.getContent());
				model.setCreateTime(TeeDateUtil.format(comm.getCreateTime(), "yyyy-MM-dd HH:mm"));
				model.setFileId(comm.getFileId());
				model.setSid(comm.getSid());
				model.setUserId(comm.getUser().getUuid());
				model.setUserName(comm.getUser().getUserName());
				model.setAvatar(TeeStringUtil.getInteger(comm.getUser().getAvatar(),0));
				modelList.add(model);
			}
		}
		easyJson.setRows(modelList);
		easyJson.setTotal(count);
		return easyJson;
	}
	
	
}
