package com.tianee.oa.core.base.vote.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.vote.bean.TeeVote;
import com.tianee.oa.core.base.vote.bean.TeeVoteItem;
import com.tianee.oa.core.base.vote.bean.TeeVoteItemPerson;
import com.tianee.oa.core.base.vote.bean.TeeVotePerson;
import com.tianee.oa.core.base.vote.dao.TeeVoteDao;
import com.tianee.oa.core.base.vote.dao.TeeVoteItemDao;
import com.tianee.oa.core.base.vote.dao.TeeVoteItemPersonDao;
import com.tianee.oa.core.base.vote.dao.TeeVotePersonDao;
import com.tianee.oa.core.base.vote.model.TeeVoteItemModel;
import com.tianee.oa.core.base.vote.model.TeeVoteItemPersonModel;
import com.tianee.oa.core.base.vote.model.TeeVoteModel;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.core.org.service.TeePersonManagerI;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeModelIdConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeManagerPostPersonDataPrivModel;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class TeeVoteService extends TeeBaseService {
	@Autowired
	private TeeVoteDao voteDao;

	@Autowired
	private TeeVoteItemDao voteItemDao;

	@Autowired
	private TeeVoteItemPersonDao itemPersonDao;

	@Autowired
	private TeeVotePersonDao votePersonDao;

	@Autowired
	private TeePersonDao personDao;

	@Autowired
	private TeeDeptDao deptDao;

	@Autowired
	private TeeAttachmentDao attachmentDao;

	@Autowired
	private TeeUserRoleDao userRoleDao;

	@Autowired
	private TeeBaseUpload upload;
	@Autowired
	private TeePersonManagerI personManagerI;
	@Autowired
	private TeeSmsSender smsSender;

	/**
	 * @author syl 新增 或者 更新
	 * @param message
	 * @param person
	 *            系统当前登录人
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeVoteModel model) throws IOException, ParseException {
		/* 附件处理 */
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<TeeAttachment> attachments = upload.manyAttachUpload(multipartRequest, TeeAttachmentModelKeys.VOTE);

		// 记录日志
		TeeSysLog sysLog = TeeSysLog.newInstance();

		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();

		TeeVote vote = new TeeVote();

		BeanUtils.copyProperties(model, vote);
		if (!TeeUtility.isNullorEmpty(model.getPostDeptIds())) {// 发布权限 ---部门
			List<TeeDepartment> listDept = deptDao.getDeptListByUuids(model.getPostDeptIds());
			vote.setPostDept(listDept);
		}
		if (!TeeUtility.isNullorEmpty(model.getPostUserIds())) {// 申发布权限- 人员
			List<TeePerson> listDept = personDao.getPersonByUuids(model.getPostUserIds());
			vote.setPostUser(listDept);
		}

		if (!TeeUtility.isNullorEmpty(model.getPostUserRoleIds())) {// fa发布权限 --
																	// 角色
			List<TeeUserRole> listRole = userRoleDao.getPrivListByUuids(model.getPostUserRoleIds());
			vote.setPostUserRole(listRole);
		}

		if (!TeeUtility.isNullorEmpty(model.getBeginDateStr())) {
			Date date = TeeUtility.parseDate("yyyy-MM-dd", model.getBeginDateStr());
			vote.setBeginDate(date);
		}
		if (!TeeUtility.isNullorEmpty(model.getEndDateStr())) {
			Date date = TeeUtility.parseDate("yyyy-MM-dd", model.getEndDateStr());
			vote.setEndDate(date);
		}

		if (model.getSid() > 0) {
			TeeVote oldVote = voteDao.getById(model.getSid());
			if (oldVote != null) {
				for (int i = 0; i < attachments.size(); i++) {
					TeeAttachment attach = (TeeAttachment) attachments.get(i);
					attach.setModelId(String.valueOf(oldVote.getSid()));
					simpleDaoSupport.update(attach);
				}
				String publish = oldVote.getPublish();
				BeanUtils.copyProperties(model, oldVote);
				oldVote.setPublish(publish);
				oldVote.setBeginDate(vote.getBeginDate());
				oldVote.setEndDate(vote.getEndDate());
				oldVote.setPostDept(vote.getPostDept());
				oldVote.setPostUser(vote.getPostUser());
				oldVote.setPostUserRole(vote.getPostUserRole());

				voteDao.updateObj(oldVote);
				updateVoteLog();
				json.setRtState(true);
				json.setRtMsg("保存成功！");
			} else {
				json.setRtState(false);
				json.setRtMsg("该投票已被删除！");
			}
			sysLog.setType("029B");
		} else {
			Calendar cal = Calendar.getInstance();
			vote.setCreateDate(cal);
			vote.setCreateUser(person);
			voteDao.add(vote);

			for (int i = 0; i < attachments.size(); i++) {
				TeeAttachment attach = (TeeAttachment) attachments.get(i);
				attach.setModelId(String.valueOf(vote.getSid()));
				simpleDaoSupport.update(attach);
			}

			sysLog.setType("029A");
			json.setRtState(true);
			json.setRtMsg("保存成功！");
		}
		// 创建日志
		sysLog.setRemark(getSysLogInfo(model));
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		json.setRtData(model);

		return json;
	}

	@TeeLoggingAnt(template = "新增投票项", type = "029A")
	public void addVoteLog() {

	}

	@TeeLoggingAnt(template = "修改投票项", type = "029B")
	public void updateVoteLog() {

	}

	@TeeLoggingAnt(template = "删除投票项", type = "029C")
	public void deleteVoteLog() {

	}

	@TeeLoggingAnt(template = "发起投票", type = "029D")
	public void doVoteLog() {

	}

	/**
	 * 通用列表 ---- 投票管理
	 * 
	 * @param dm
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, HttpServletRequest request, TeeVoteModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestDatas = TeeServletUtility.getParamMap(request);
		boolean isSuperAdmin = TeePersonService.checkIsSuperAdmin(loginPerson, "");
		Map map = new HashMap();
		map.put(TeeConst.LOGIN_USER, loginPerson);
		// 数据管理权限
		TeeManagerPostPersonDataPrivModel dataPrivModel = personManagerI.getManagerPostPersonIdsPriv(map, TeeModelIdConst.VOTE_POST_PRIV, "0");
		j.setTotal(voteDao.getVoteManagerCount(loginPerson,requestDatas, model, isSuperAdmin, dataPrivModel));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		Object parm[] = {};
		List<TeeVote> list = voteDao.getVoteManager(loginPerson,requestDatas, isSuperAdmin, firstIndex, dm.getRows(), dm, model, dataPrivModel);// 查
		List<TeeVoteModel> modelList = new ArrayList<TeeVoteModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeVoteModel modeltemp = parseModel(list.get(i), false);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * 获取有权限的
	 * 
	 * @author syl
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson selectPostVote(HttpServletRequest request, TeeVoteModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		boolean isSuperAdmin = TeePersonService.checkIsSuperAdmin(person, "");
		List<TeeVote> list = voteDao.getPersonalVote(person, model, isSuperAdmin);
		List<TeeVoteModel> listModel = new ArrayList<TeeVoteModel>();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i), true));
		}
		json.setRtData(listModel);
		json.setRtState(true);
		return json;
	}

	/**
	 * 对象转换
	 * 
	 * @author syl
	 * @date 2014-1-29
	 * @param out
	 * @return
	 */
	public TeeVoteModel parseModel(TeeVote vote, boolean isSimple) {
		TeeVoteModel model = new TeeVoteModel();
		if (vote == null) {
			return model;
		}
		BeanUtils.copyProperties(vote, model);
		// 权限范围----部门、人员、角色
		List<TeeDepartment> listDept = vote.getPostDept();
		List<TeePerson> userList = vote.getPostUser();
		List<TeeUserRole> userRoleList = vote.getPostUserRole();

		String postDeptIds = "";
		String postDeptNames = "";
		String postUserIds = "";
		String postUserNames = "";

		String postUserRoleIds = "";
		String postUserRoleNames = "";
		if (listDept != null) {
			for (int i = 0; i < listDept.size(); i++) {
				postDeptIds = postDeptIds + listDept.get(i).getUuid() + ",";
				postDeptNames = postDeptNames + listDept.get(i).getDeptName() + ",";
			}
		}

		if (userList != null) {
			for (int i = 0; i < userList.size(); i++) {
				postUserIds = postUserIds + userList.get(i).getUuid() + ",";
				postUserNames = postUserNames + userList.get(i).getUserName() + ",";
			}
		}

		if (userRoleList != null) {
			for (int i = 0; i < userRoleList.size(); i++) {
				postUserRoleIds = postUserRoleIds + userRoleList.get(i).getUuid() + ",";
				postUserRoleNames = postUserRoleNames + userRoleList.get(i).getRoleName() + ",";
			}
		}
		model.setPostDeptIds(postDeptIds);
		model.setPostDeptNames(postDeptNames);
		model.setPostUserIds(postUserIds);
		model.setPostUserNames(postUserNames);
		model.setPostUserRoleIds(postUserRoleIds);
		model.setPostUserRoleNames(postUserRoleNames);

		SimpleDateFormat simpDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String createDate = "";
		if (vote.getCreateDate() != null) {
			//createDate = TeeUtility.getDateTimeStr(vote.getCreateDate().getTime());
			createDate = simpDateFormat1.format(vote.getCreateDate().getTime());

		}
		model.setCreateDateStr(createDate);

		model.setCreateUserId(vote.getCreateUser().getUuid());
		model.setCreateUserName(vote.getCreateUser().getUserName());

		String beginDateStr = "";// 开始时间
		if (vote.getBeginDate() != null) {
			beginDateStr = TeeUtility.getDateStrByFormat(vote.getBeginDate(), simpDateFormat);

		}
		model.setBeginDateStr(beginDateStr);

		String endDateStr = "";// 结束时间
		if (vote.getEndDate() != null) {
			endDateStr = TeeUtility.getDateStrByFormat(vote.getEndDate(), simpDateFormat);
		}
		model.setEndDateStr(endDateStr);

		// 附件
		if (!isSimple) {
			List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.VOTE, String.valueOf(vote.getSid()));
			List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
			for (TeeAttachment attach : attaches) {
				TeeAttachmentModel m = new TeeAttachmentModel();
				BeanUtils.copyProperties(attach, m);
				m.setUserId(attach.getUser().getUuid() + "");
				m.setUserName(attach.getUser().getUserName());
				m.setPriv(1 + 2 + 4 + 8 + 16 + 32);// 一共五个权限好像
													// 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
				attachmodels.add(m);
			}
			model.setAttacheModels(attachmodels);
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

	@TeeLoggingAnt(template = "删除投票记录,  {logModel.remark}", type = "029C")
	public TeeJson deleteByIdService(HttpServletRequest request, TeeVoteModel model) {
		TeeJson json = new TeeJson();
		String remark = "";
		if (model.getSid() > 0) {
			TeeVote vote = voteDao.getById(model.getSid());
			if (vote == null) {
				remark = getSysLogInfo(null);
			} else {
				model = parseModel(vote, true);
				remark = getSysLogInfo(model);
				voteDao.deleteByObj(vote);
				json.setRtState(true);
				json.setRtMsg("删除成功!");
			}
		}
		TeeRequestInfoContext.getRequestInfo().getLogModel().put("remark", remark);// 添加其他参数
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
	public TeeJson deleteAll(HttpServletRequest request, TeeVoteModel model) {
		TeeJson json = new TeeJson();
		voteDao.delAll();
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}

	/**
	 * 
	 * @author syl 查询 ById
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request, TeeVoteModel model) {
		TeeJson json = new TeeJson();
		if (model.getSid() > 0) {
			TeeVote out = voteDao.getById(model.getSid());
			if (out != null) {
				model = parseModel(out, false);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("该投票可能已被删除！");
		return json;
	}

	/**
	 * 
	 * @author CXT 查询 ById
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeVoteModel getVoteBySid(String voteId) {
		TeeVoteModel model = new TeeVoteModel();
		if (!TeeUtility.isNullorEmpty(voteId)) {
			TeeVote vote = voteDao.get(Integer.parseInt(voteId));
			if (vote == null) {
				return null;
			}
			BeanUtils.copyProperties(vote, model);
		}
		return model;
	}

	/**
	 * 
	 * @author CXT 查询 ById
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public List<TeeVoteModel> getVoteListBySid(String voteId) {
		List<TeeVoteModel> list = new ArrayList<TeeVoteModel>();
		if (!TeeUtility.isNullorEmpty(voteId)) {
			List<TeeVote> voteList = voteDao.getVoteListBySid(Integer.parseInt(voteId));
			for (TeeVote vote : voteList) {
				TeeVoteModel model = new TeeVoteModel();
				BeanUtils.copyProperties(vote, model);
				list.add(model);
			}

		}
		return list;
	}

	/**
	 * 获取投票结果
	 * 
	 * @param voteId
	 * @return [{subject:
	 *         '',type:'1(4,0)',voteItems:[{name:'item1',total:'',itemId:''},{name:'item2',total:'',itemId:''}]}
	 *         , {subject:
	 *         '',type:'2(3)',voteItem:{name:'',datas:[{user:'',data:'',itemId:''}],itemId:''}}
	 *         ]
	 */
	public List<Map> getVoteResult(int voteId, TeePerson loginUser) {
		List<Map> list = new ArrayList();
		/*
		 * TeeVote topVote = voteDao.get(voteId);
		 * //判断查看结果类型，如果当前投票是自己的投票，则允许任何时候查看
		 * if(topVote.getCreateUser().getUuid()!=loginUser.getUuid()){
		 * if("0".equals(topVote.getViewPriv())){//投票后允许查看 //判断该人是否投过票 long
		 * count = simpleDaoSupport.count(
		 * "select count(*) from TeeVotePerson where vote.sid="
		 * +voteId+" and user.uuid="+loginUser.getUuid(), null); if(count==0){
		 * throw new TeeOperationException("投票后允许查看结果"); } }else
		 * if("2".equals(topVote.getViewPriv())){//不允许查看 throw new
		 * TeeOperationException("该投票不允许查看结果"); } }
		 */

		// 获取一级投票项目
		List<TeeVote> voteList = voteDao.getVoteListBySid(voteId);
		for (TeeVote vote : voteList) {
			Map voteMap = new HashMap();
			List<Map> items = new ArrayList();
			Map item = new HashMap();
			voteMap.put("subject", vote.getSubject());
			voteMap.put("type", vote.getVoteType());
			voteMap.put("voteItems", items);
			voteMap.put("voteItem", item);
			voteMap.put("required", vote.getRequired());

			// 投票二级项目
			List<TeeVoteItem> voteItems = voteItemDao.getItemListByVoteId(vote.getSid());

			if ("2".equals(vote.getVoteType()) || "3".equals(vote.getVoteType())) {// 单行文本、多行文本，列出投票项目即可，不用汇总
				TeeVoteItem voteItem = voteItems.get(0);
				List<TeeVoteItemPerson> voteItemPersons = simpleDaoSupport.find("select vote from TeeVoteItemPerson vote where vote.voteData<>'' and vote.voteItem.sid =" + voteItem.getSid() + " order by vote.sid asc",
						null);
				Map d1 = new HashMap();
				List<Map> dlist = new ArrayList<Map>();
				item.put("name", "");
				item.put("datas", dlist);
				item.put("itemId", voteItem.getSid());
				for (TeeVoteItemPerson personItemData : voteItemPersons) {
					int userId =0;
					String voteUserName = "";
					if(!TeeUtility.isNullorEmpty(personItemData.getUser())){
						voteUserName = personItemData.getUser().getUserName();
						userId = personItemData.getUser().getUuid();
					}
					
					
					Map d2 = new HashMap();
					d2.put("user", voteUserName);
					d2.put("data", personItemData.getVoteData());
					d2.put("itemId", personItemData.getVoteItem().getSid());
					d2.put("userId", userId);
					dlist.add(d2);
				}
			} else {// 需要汇总
				for (TeeVoteItem voteItem : voteItems) {
					Map d2 = new LinkedHashMap();
					d2.put("name", voteItem.getItemName());
					d2.put("total", simpleDaoSupport.count("select count(*) from TeeVoteItemPerson where voteItem.sid=" + voteItem.getSid(), null));
					d2.put("itemId", voteItem.getSid());
					items.add(d2);
				}
			}

			list.add(voteMap);
		}

		return list;
	}

	/**
	 * 获取投票与其投票项
	 * 
	 * @param voteId
	 * @return [{subject:
	 *         '',type:'1(4,0)',voteItems:[{name:'item1',itemId:''},{name:'item2',itemId:''}]}
	 *         , {subject:'',type:'2(3)',voteItem:{name:'',itemId:''}}]
	 */
	public List<Map> getVotes(int voteId) {
		List<Map> list = new ArrayList();
		// 获取一级投票项目
		List<TeeVote> voteList = voteDao.getVoteListBySid(voteId);
		for (TeeVote vote : voteList) {
			Map voteMap = new HashMap();
			List<Map> items = new ArrayList();
			Map item = new HashMap();
			voteMap.put("subject", vote.getSubject());
			voteMap.put("max", vote.getMaxNum());
			voteMap.put("min", vote.getMinNum());
			voteMap.put("required", vote.getRequired());
			voteMap.put("content", vote.getContent());
			voteMap.put("type", vote.getVoteType());
			voteMap.put("voteId", vote.getSid());
			voteMap.put("voteItems", items);
			voteMap.put("voteItem", item);
			voteMap.put("ifContent", vote.getIfContent());

			// 投票二级项目
			List<TeeVoteItem> voteItems = voteItemDao.getItemListByVoteId(vote.getSid());

			if ("2".equals(vote.getVoteType()) || "3".equals(vote.getVoteType())) {// 列出投票项目即可，不用汇总
				TeeVoteItem voteItem = voteItems.get(0);
				Map d1 = new HashMap();
				List<Map> dlist = new ArrayList<Map>();
				item.put("name", "");
				item.put("itemId", voteItem.getSid());
			} else {// 需要汇总
				for (TeeVoteItem voteItem : voteItems) {
					Map d2 = new LinkedHashMap();
					d2.put("name", voteItem.getItemName());
					d2.put("itemId", voteItem.getSid());
					items.add(d2);
				}
			}

			list.add(voteMap);
		}

		return list;
	}

	/**
	 * 获取我的投票项
	 * 
	 * @param voteId
	 * @return
	 */
	public List<Map> getMyVoteItemsData(int voteId, TeePerson loginUser) {
		List<Map> list = new ArrayList();
		List<TeeVoteItemPerson> datas = simpleDaoSupport.find("from TeeVoteItemPerson where voteItem.vote.parentVote.sid=" + voteId + " and user.uuid=" + loginUser.getUuid(), null);
		for (TeeVoteItemPerson data : datas) {
			Map d = new HashMap();
			d.put("itemId", data.getVoteItem().getSid());
			d.put("userId", data.getUser().getUuid());
			list.add(d);
		}
		return list;
	}

	/**
	 * 
	 * @author CXT 查询 ById
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeVote getVoteById(int voteId) {
		TeeVote vote = new TeeVote();
		if (!TeeUtility.isNullorEmpty(voteId)) {
			vote = voteDao.get(voteId);
		}
		return vote;
	}

	/**
	 * 
	 * @author CXT 查询 ById
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public void saveObj(TeeVote vote) {
		if (!TeeUtility.isNullorEmpty(vote)) {
			voteDao.add(vote);
		}
	}

	/**
	 * 
	 * @author CXT 查询 ById
	 * @date 2014-3-14
	 * @param request
	 * @param model
	 * @return
	 */
	public void saveVoteItem(TeeVoteItem vote) {
		if (!TeeUtility.isNullorEmpty(vote)) {
			voteItemDao.saveObj(vote);
		}
	}

	/**
	 * 
	 * @author CXT 格式化投票项
	 * @date 2014-3-14
	 * @param request
	 * @param model
	 * @return
	 */
	public void formatVote(TeeVote vote) {
		if (!TeeUtility.isNullorEmpty(vote)) {
			// 获取所有vote
			List<TeeVote> votes = simpleDaoSupport.find("from TeeVote vote where vote.parentVote.sid =" + vote.getSid(), null);
			for (TeeVote v : votes) {
				simpleDaoSupport.executeUpdate("delete from TeeVoteItem where vote.sid =" + v.getSid(), null);
				simpleDaoSupport.deleteByObj(v);
			}
			simpleDaoSupport.executeUpdate("delete from TeeVoteItemPerson where vote.sid =" + vote.getSid(), null);
			simpleDaoSupport.executeUpdate("delete from TeeVotePerson votePerson where votePerson.vote.sid =" + vote.getSid(), null);
		}
	}

	/**
	 * 
	 * @author CXT 清空投票结果
	 * @date 2014-3-14
	 * @param request
	 * @param model
	 * @return
	 */
	public void clearResult(TeeVote vote) {
		if (!TeeUtility.isNullorEmpty(vote)) {
			List<TeeVote> voteList = voteDao.getVoteListBySid(vote.getSid());
			for (TeeVote v : voteList) {
				simpleDaoSupport.executeUpdate("update TeeVoteItem vote set vote.voteCount = 0  where vote.vote.sid =" + v.getSid(), null);
				List<TeeVoteItem> itemList = voteItemDao.getVoteItemListBySid(v.getSid());
				for (TeeVoteItem i : itemList) {
					simpleDaoSupport.executeUpdate("delete from TeeVoteItemPerson person where person.voteItem.sid =" + i.getSid(), null);
				}
			}
			simpleDaoSupport.executeUpdate("delete from TeeVotePerson votePerson where votePerson.vote.sid =" + vote.getSid(), null);
		}
	}

	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson voteDatagrid(TeeDataGridModel dm, HttpServletRequest request, TeeVoteModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestDatas = TeeServletUtility.getParamMap(request);
		boolean isSuperAdmin = TeePersonService.checkIsSuperAdmin(loginPerson, "");
		
		
		j.setTotal(voteDao.getVoteManagerCount(loginPerson,requestDatas, model, isSuperAdmin));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		Object parm[] = {};
		List<TeeVote> list = new ArrayList<TeeVote>();

		list = voteDao.getVotes(loginPerson,requestDatas, isSuperAdmin, firstIndex, dm.getRows(), dm, model);// 查
		List<TeeVoteModel> modelList = new ArrayList<TeeVoteModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				int voteStatus = 1;
				TeeVoteModel modeltemp = parseModel(list.get(i), false);
				long begin = 0;
				if (!TeeUtility.isNullorEmpty(list.get(i).getBeginDate())) {
					begin = list.get(i).getBeginDate().getTime();
				}
				long end = 0;
				// System.out.println("getBeginDate:"+list.get(i).getBeginDate()+",getEndDate:"+list.get(i).getEndDate());
				if (!TeeUtility.isNullorEmpty(list.get(i).getEndDate())) {
					// end = list.get(i).getEndDate().getTime();
					Calendar endCalendar = Calendar.getInstance();
					endCalendar.setTime(list.get(i).getEndDate());
					endCalendar.set(Calendar.HOUR_OF_DAY, 23);
					endCalendar.set(Calendar.MINUTE, 59);
					endCalendar.set(Calendar.SECOND, 59);
					end = endCalendar.getTimeInMillis();
				}
				long now = new Date().getTime();
				if ((now <= begin) && (begin != 0)) {// 未开始； 投票状态：0 未开始 ;1-进行中 ;2-已结束 ;3-已投票
					voteStatus = 0;
				} else if ((now > end) && (end != 0)) {//2-已结束
					voteStatus = 2;
				} else if ((begin < now || begin == 0) && (now < end || end == 0)) {//1-进行中
					voteStatus = 1;
				}

				// System.out.println(TeeUtility.getDateTimeStr(new Date(now)) +
				// ">>>" + TeeUtility.getDateTimeStr(new Date(begin)) + ">>>" +
				// TeeUtility.getDateTimeStr(new Date(end)));

				List<TeeVotePerson> l = votePersonDao.getVotePersonList(loginPerson, modeltemp.getSid());
				if (l.size() > 0) {// 已投票
					voteStatus = 3;
				}
				modeltemp.setVoteStatus(voteStatus);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * 更新状态、删除
	 * 
	 * @author CXT
	 * @date 2014-3-27
	 * @param request
	 * @param model
	 * @return
	 */
	public void toAddUpdatePerson(String sid, String status) {
		if (!TeeUtility.isNullorEmpty(sid) && !TeeUtility.isNullorEmpty(status)) {
			int id = Integer.parseInt(sid);
			// 记录日志
			TeeSysLog sysLog = TeeSysLog.newInstance();
			if (status.equals("2")) {
				List<TeeVote> votes = simpleDaoSupport.find("from TeeVote vote where vote.parentVote.sid =" + id, null);
				for (TeeVote v : votes) {
					simpleDaoSupport.executeUpdate("delete from TeeVoteItem where vote.sid =" + v.getSid(), null);
					simpleDaoSupport.deleteByObj(v);
				}
				simpleDaoSupport.executeUpdate("delete from TeeVoteItemPerson where vote.sid =" + id, null);
				simpleDaoSupport.executeUpdate("delete from TeeVotePerson votePerson where votePerson.vote.sid =" + id, null);
				TeeVote vote = getVoteById(id);
				simpleDaoSupport.deleteByObj(vote);

				sysLog.setType("029C");
				sysLog.setRemark("删除投票项id:" + id);
				TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
			} else {
				if (status.equals("1")) {// 给相关人员发送短信
					TeeVote vote = voteDao.get(id);
					List<TeePerson> p = vote.getPostUser();
					String uuids = "";
					for (TeePerson person : p) {
						uuids += person.getUuid() + ",";
					}
					List<TeeDepartment> d = vote.getPostDept();
					String deptIds = "";
					for (TeeDepartment dept : d) {
						deptIds += dept.getUuid() + ",";
					}
					List<TeeUserRole> r = vote.getPostUserRole();
					String roleIds = "";
					for (TeeUserRole role : r) {
						roleIds += role.getUuid() + ",";
					}
					List<TeePerson> list = personManagerI.getPersonByUuidsOrDeptIdsOrRoleIds(null, uuids, deptIds, roleIds);
					String userIds = "";
					for (TeePerson t : list) {
						userIds += t.getUuid() + ",";
					}
					Map requestData = new HashMap();
					requestData.put("userListIds", userIds);
					requestData.put("moduleNo", "029");
					requestData.put("content", "您有一条投票信息[" + vote.getSubject() + "],请查看。");
					requestData.put("remindUrl", "/system/core/base/vote/personal/vote.jsp?voteId=" + vote.getSid());
					smsSender.sendSms(requestData, null);
					// 发起投票项日志
					sysLog.setType("029D");
					sysLog.setRemark("发起投票id:" + id);
					TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
				}
				simpleDaoSupport.executeUpdate("update TeeVote vote set vote.publish = '" + status + "' where vote.sid =" + id, null);
			}
		}
	}

	/**
	 * 
	 * @author CXT 查询 ById
	 * @date 2014-3-30
	 * @param request
	 * @param model
	 * @return
	 */
	public List<TeeVoteItemModel> getVoteItemListBySid(String voteId) {
		List<TeeVoteItemModel> list = new ArrayList<TeeVoteItemModel>();
		if (!TeeUtility.isNullorEmpty(voteId)) {
			List<TeeVoteItem> voteList = voteItemDao.getVoteItemListBySid(Integer.parseInt(voteId));
			for (TeeVoteItem item : voteList) {
				TeeVoteItemModel model = new TeeVoteItemModel();
				BeanUtils.copyProperties(item, model);
				list.add(model);
			}

		}
		return list;
	}

	/**
	 * 
	 * @author CXT 查询 ById
	 * @date 2014-3-30
	 * @param request
	 * @param model
	 * @return
	 */
	public List<TeeVoteItem> voteItemListBySid(String voteId) {
		List<TeeVoteItem> voteList = null;
		if (!TeeUtility.isNullorEmpty(voteId)) {
			voteList = voteItemDao.voteItemListBySid(Integer.parseInt(voteId));
		}
		return voteList;
	}

	public void saveVotePerson(TeeVotePerson t) {
		votePersonDao.save(t);
	}

	/**
	 * 保存个人投票
	 * 
	 * @param voteId
	 * @param anonymity
	 * @param votesItemModels
	 */
	public void savePersonalVote(int voteId, String anonymity, List<TeeVoteItemPersonModel> votesItemModels, TeePerson loginUser) {
		// 保存投票关系
		TeeVote vote = getVoteById(voteId);
		if(vote != null){
			TeeVotePerson votePerson = new TeeVotePerson();
			votePerson.setCreateDate(new Date());
			votePerson.setUser(loginUser);
			votePerson.setVote(vote);
			votePerson.setAnonymity(anonymity);
			saveVotePerson(votePerson);

			for (TeeVoteItemPersonModel model : votesItemModels) {
				TeeVoteItemPerson vip = new TeeVoteItemPerson();
				vip.setCreateDate(new Date());
				if(!"1".equals(vote.getAnonymity())){//匿名投票，不保存用户
					vip.setUser(loginUser);
				}
				vip.setVoteData(model.getVoteData());
				vip.setVote(vote);
				TeeVoteItem vi = new TeeVoteItem();
				vi.setSid(model.getVoteItemId());
				vip.setVoteItem(vi);
				simpleDaoSupport.save(vip);
				simpleDaoSupport.executeUpdate("update TeeVoteItem vi set vi.voteCount=vi.voteCount+1 where vi.sid=" + model.getVoteItemId(), null);
			}
		}
		
	}

	/**
	 * 获取文本内容
	 * 
	 * @author CXT 查询 ById
	 * @date 2014-3-29
	 * @param request
	 * @param model
	 * @return
	 */
	public List<TeeVoteItemPersonModel> getVoteData(int voteItemId, int voteId) {
		List<TeeVoteItemPersonModel> list = new ArrayList<TeeVoteItemPersonModel>();
		if (!TeeUtility.isNullorEmpty(voteItemId)) {
			List<TeeVoteItemPerson> voteList = itemPersonDao.getVoteData(voteItemId);
			for (TeeVoteItemPerson item : voteList) {
				TeeVoteItemPersonModel model = new TeeVoteItemPersonModel();
				BeanUtils.copyProperties(item, model);
				List<TeeVotePerson> p = votePersonDao.getVotePersonList(item.getUser(), voteId);
				String userName = "匿名";
				if (p.get(0).getAnonymity().equals("0")) {
					userName = item.getUser().getUserName();
				}
				model.setUserId(item.getUser().getUuid());
				model.setUserName(userName);
				list.add(model);
			}
		}
		return list;
	}

	/**
	 * 获取记录日志详细信息
	 * 
	 * @author syl
	 * @date 2014-4-23
	 * @param vote
	 * @return
	 */
	public String getSysLogInfo(TeeVoteModel vote) {
		String remark = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (vote == null) {
			remark = "该投票管理已被删除！";
		} else {
			remark = "【主题:" + vote.getSubject() + ",有效时间:'" + vote.getBeginDateStr() + " 至 " + vote.getEndDateStr() + "'" + ",发布范围:{部门:'" + vote.getPostDeptNames() + "',角色:'"
					+ vote.getPostUserRoleNames() + "',人员:'" + vote.getPostUserNames() + "'}" + "】";
		}
		return remark;
	}

	/**
	 * 检测是否已经投票过
	 * 
	 * @param voteId
	 * @param loginUser
	 * @return
	 */
	public boolean checkVote(int voteId, TeePerson loginUser) {
		long count = simpleDaoSupport.count("select count(*) from TeeVotePerson where vote.sid=" + voteId + " and user.uuid=" + loginUser.getUuid(), null);
		return count != 0;
	}

	/**
	 * @function: 删除
	 * @author: wyw
	 * @data: 2015年6月25日
	 * @param sids
	 * @return TeeJson
	 */
	public TeeJson deleteObjById(String sids) {
		TeeJson json = new TeeJson();
		if (sids.endsWith(",")) {
			sids = sids.substring(0, sids.length() - 1);
		}
		TeeSysLog sysLog = TeeSysLog.newInstance();
		List<TeeVote> votes = simpleDaoSupport.find("from TeeVote vote where vote.parentVote.sid in(" + sids + ")", null);
		for (TeeVote v : votes) {
			simpleDaoSupport.executeUpdate("delete from TeeVoteItem where vote.sid =" + v.getSid(), null);
			simpleDaoSupport.deleteByObj(v);
		}
		simpleDaoSupport.executeUpdate("delete from TeeVoteItemPerson where vote.sid in(" + sids + ")", null);
		simpleDaoSupport.executeUpdate("delete from TeeVotePerson votePerson where votePerson.vote.sid in(" + sids + ")", null);
		simpleDaoSupport.executeUpdate("delete from TeeVote vote where vote.sid in(" + sids + ")", null);
		sysLog.setType("029C");
		sysLog.setRemark("删除投票项id:" + sids);
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);

		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}

	/**
	 * @function: 更新发布状态
	 * @author: wyw
	 * @data: 2015年6月25日
	 * @param sids
	 * @return TeeJson
	 */
	public TeeJson updatePublish(Map requestMap) {
		TeeJson json = new TeeJson();
		String optValue = TeeStringUtil.getString(requestMap.get("optValue"), "0");
		int id = TeeStringUtil.getInteger(requestMap.get("sid"), 0);

		TeeVote vote = voteDao.get(id);
		if (vote != null) {
			TeeSysLog sysLog = TeeSysLog.newInstance();
			if ("1".equals(optValue)) {// 给相关人员发送短信 1-生效
			// TeeVote vote = voteDao.get(id);
				List<TeePerson> p = vote.getPostUser();
				String uuids = "";
				for (TeePerson person : p) {
					uuids += person.getUuid() + ",";
				}
				List<TeeDepartment> d = vote.getPostDept();
				String deptIds = "";
				for (TeeDepartment dept : d) {
					deptIds += dept.getUuid() + ",";
				}
				List<TeeUserRole> r = vote.getPostUserRole();
				String roleIds = "";
				for (TeeUserRole role : r) {
					roleIds += role.getUuid() + ",";
				}
				List<TeePerson> list = personManagerI.getPersonByUuidsOrDeptIdsOrRoleIds(null, uuids, deptIds, roleIds);
				String userIds = "";
				for (TeePerson t : list) {
					userIds += t.getUuid() + ",";
				}
				Map requestData = new HashMap();
				requestData.put("userListIds", userIds);
				requestData.put("moduleNo", "029");
				requestData.put("content", "您有一条投票信息[" + vote.getSubject() + "],投票时间段为："+vote.getBeginDate()+"到"+vote.getEndDate()+",请及时查看。");
				requestData.put("remindUrl", "/system/core/base/vote/personal/vote.jsp?voteId=" + vote.getSid());
				smsSender.sendSms(requestData, null);
				// 发起投票项日志
				sysLog.setType("029D");
				sysLog.setRemark("发起投票id:" + id);
				TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);

			} else if ("2".equals(optValue)) {// 终止

			}
			simpleDaoSupport.executeUpdate("update TeeVote vote set vote.publish = '" + optValue + "' where vote.sid =" + id, null);

		}

		json.setRtState(true);
		json.setRtMsg("更新成功!");
		return json;
	}

	/**
	 * 查看投票结果权限
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年6月26日
	 * @param requestMap
	 * @param person
	 * @return TeeJson
	 */
	public TeeJson isVotePrivById(Map requestMap, TeePerson person) {
		int voteId = TeeStringUtil.getInteger(requestMap.get("sid"), 0);
		int privFlag = 0; // 0-可查投票结果；1-投票后允许查看结果;2-投票前允许查看;3-该投票不允许查看结果

		TeeVote topVote = voteDao.get(voteId);
		// 判断查看结果类型，如果当前投票是自己的投票，则允许任何时候查看
		if (topVote.getCreateUser().getUuid() != person.getUuid()) {
			if ("0".equals(topVote.getViewPriv())) {// 投票后允许查看
				// 判断该人是否投过票
				long count = simpleDaoSupport.count("select count(*) from TeeVotePerson where vote.sid=" + voteId + " and user.uuid=" + person.getUuid(), null);
				if (count == 0) {
					privFlag = 1;
				}
			} else if ("1".equals(topVote.getViewPriv())) {// 投票前允许查看
				privFlag = 2;
			} else if ("2".equals(topVote.getViewPriv())) {// 不允许查看
				privFlag = 3;
			}
		}
		Map map = new HashMap();
		map.put("privFlag", privFlag);
		TeeJson json = new TeeJson();
		json.setRtData(map);
		json.setRtState(true);
		json.setRtMsg("数据获取成功!");
		return json;

	}

	/**
	 * 检测是否有权限投票
	 * 
	 * @param voteId
	 * @param loginUser
	 * @return true - 有权限；false-无权限
	 * @throws ParseException
	 */
	public boolean getVotesPriv(int voteId, TeePerson loginUser) throws ParseException {
		List<TeeVote> list = voteDao.getVotesPriv(loginUser, voteId);
		boolean flag = false;
		if (list != null && list.size() > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 清空投票数据
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年6月26日
	 * @param requestMap
	 * @param person
	 * @return
	 * @throws ParseException
	 *             boolean
	 */
	public TeeJson clearVoteById(Map requestMap, TeePerson person) throws ParseException {
		String sids = TeeStringUtil.getString(requestMap.get("sid"), "0");
		TeeJson json = new TeeJson();
		if (sids.endsWith(",")) {
			sids = sids.substring(0, sids.length() - 1);
		}
		simpleDaoSupport.executeUpdate("delete from TeeVotePerson where vote.sid in(" + sids + ")", null);
		simpleDaoSupport.executeUpdate("delete from TeeVoteItemPerson where vote.sid in(" + sids + ")", null);

		List<TeeVote> parentVoteList = simpleDaoSupport.find("from TeeVote vote where vote.parentVote.sid in(" + sids + ")", null);
		for (TeeVote vote : parentVoteList) {
			simpleDaoSupport.executeUpdate("update TeeVoteItem set voteCount=0 where vote.sid =" + vote.getSid(), null);
		}
		json.setRtState(true);
		json.setRtMsg("更新成功!");
		return json;
	}

	/**
	 * 获取未投票/已投票人员数
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年6月26日
	 * @param requestMap
	 * @param person
	 * @return
	 * @throws ParseException
	 *             boolean
	 */
	public TeeJson getVotePersonCount(Map requestMap, TeePerson person) throws ParseException {
		int sid = TeeStringUtil.getInteger(requestMap.get("sid"), 0);

		TeeVote vote = voteDao.get(sid);
		int voteCount = 0;
		int notVoteCount = 0;
		if (vote != null) {
			// 权限范围----部门、人员、角色
			List<TeeDepartment> listDept = vote.getPostDept();
			List<TeePerson> userList = vote.getPostUser();
			List<TeeUserRole> userRoleList = vote.getPostUserRole();

			StringBuffer deptBuffer = new StringBuffer();
			StringBuffer userBuffer = new StringBuffer();
			StringBuffer userRoleBuffer = new StringBuffer();

			if (listDept != null && listDept.size() > 0) {
				for (TeeDepartment obj : listDept) {
					if (deptBuffer.length() > 0) {
						deptBuffer.append(",");
					}
					deptBuffer.append(obj.getUuid());
				}
			}
			if (userList != null && userList.size() > 0) {
				for (TeePerson obj : userList) {
					if (userBuffer.length() > 0) {
						userBuffer.append(",");
					}
					userBuffer.append(obj.getUuid());
				}
			}

			if (userRoleList != null && userRoleList.size() > 0) {
				for (TeeUserRole obj : userRoleList) {
					if (userRoleBuffer.length() > 0) {
						userRoleBuffer.append(",");
					}
					userRoleBuffer.append(obj.getUuid());
				}
			}

			//获取权限范围----部门、人员、角色人员
			List<TeePerson> personList = personDao.getPersonsByUuidsOrDeptIdsOrRoleIds(userBuffer.toString(), deptBuffer.toString(), userRoleBuffer.toString());

			//根据vote Sid获取已投票数据
			List<TeeVotePerson> votePersons = votePersonDao.getVotePersonListByVoteId(sid);

			List<Integer> personTempList = new ArrayList<Integer>();
			if (personList != null && personList.size() > 0) {
				for (TeePerson obj : personList) {
					personTempList.add(obj.getUuid());
				}
			}

			//过滤投票人员（唯一性）
			Set<Integer> set = new HashSet<Integer>();
			if (votePersons != null && votePersons.size() > 0) {
				for (TeeVotePerson obj : votePersons) {
					set.add(obj.getUser().getUuid());
				}
			}
			for (Integer obj : set) {
				if (personTempList.contains(obj)) {
					voteCount++;
				}
			}
			notVoteCount = personTempList.size() - voteCount;
		}

		Map map = new HashMap();
		map.put("voteCount", voteCount);
		map.put("notVoteCount", notVoteCount);
		
		TeeJson json = new TeeJson();
		json.setRtData(map);
		json.setRtState(true);
		json.setRtMsg("数据获取成功!");
		return json;
	}
	
	
	
	
	/**
	 * 管理员获取投票结果
	 * 
	 * @param voteId
	 * @return [非文本框数据  {subject:'',type:'1(4,0)',voteItems:[{name:'item1',total:'',itemId:''},{name:'item2',total:'',itemId:''}]},
	 *          文本框数据  {subject:'',type:'2(3)',voteItem:{name:'',datas:[{user:'',data:'',itemId:''}],itemId:''}} 
	 *         ]
	 */
	public List<Map> getManageVoteResult(int voteId, TeePerson loginUser) {
		List<Map> list = new ArrayList();
		
		// 获取一级投票项目
		List<TeeVote> voteList = voteDao.getVoteListBySid(voteId);
		if(voteList != null && voteList.size()>0){
			TeeVote voteObj = voteDao.get(voteId);

			//根据已投票的voteId获取投票记录项 
			List<TeeVoteItemPerson> voteItemPersonList = simpleDaoSupport.find(" select vip from TeeVoteItemPerson vip,TeeVote v where v.sid=vip.vote.sid  and v.anonymity<>'1' and vip.user.uuid<>null and vip.vote.sid=" + voteId + " order by vip.voteItem.sid asc",null);
			
			Map<Integer,String> voteItemPersonMap = new HashMap();
			StringBuffer voteItemPersonBuffer = new StringBuffer();
			if(voteItemPersonList != null && voteItemPersonList.size()>0){
				for(TeeVoteItemPerson obj:voteItemPersonList){
					if(voteItemPersonMap.containsKey(obj.getVoteItem().getSid())){
						String temStr = voteItemPersonMap.get(obj.getVoteItem().getSid());
						if(!TeeUtility.isNullorEmpty(temStr)){
							temStr += "," + obj.getUser().getUserName();
						}
						voteItemPersonMap.put(obj.getVoteItem().getSid(), temStr );
					}else{
						voteItemPersonMap.put(obj.getVoteItem().getSid(), obj.getUser().getUserName());
					}
				}
			}
			
			for (TeeVote vote : voteList) {
				Map voteMap = new HashMap();
				List<Map> items = new ArrayList();
				Map item = new HashMap();
				voteMap.put("subject", vote.getSubject());
				voteMap.put("type", vote.getVoteType());
				voteMap.put("voteItems", items);//非文本框数据
				voteMap.put("voteItem", item);//文本框数据
				voteMap.put("required", vote.getRequired()); //是否必填

				// 投票二级项目，投票项
				List<TeeVoteItem> voteItems = voteItemDao.getItemListByVoteId(vote.getSid());

				if ("2".equals(vote.getVoteType()) || "3".equals(vote.getVoteType())) {// 单行文本、多行文本，列出投票项目即可，不用汇总
					TeeVoteItem voteItem = voteItems.get(0);
					List<TeeVoteItemPerson> voteItemPersons = simpleDaoSupport.find("select vote from TeeVoteItemPerson vote where vote.voteData<>'' and vote.user.uuid<>null and vote.voteItem.sid =" + voteItem.getSid() + " order by vote.sid asc",
							null);
					Map d1 = new HashMap();
					List<Map> dlist = new ArrayList<Map>();
					item.put("name", "");
					item.put("datas", dlist);
					item.put("itemId", voteItem.getSid());
					for (TeeVoteItemPerson personItemData : voteItemPersons) {
						String voteUserName = "";
						if(!"1".equals(voteObj.getAnonymity())){
							voteUserName = personItemData.getUser().getUserName();
						}
						
						Map d2 = new HashMap();
						d2.put("user", personItemData.getUser().getUserName());
						d2.put("data", personItemData.getVoteData());
						d2.put("itemId", personItemData.getVoteItem().getSid());
						d2.put("userId", personItemData.getUser().getUuid());
						d2.put("voteUserName", voteUserName);
						dlist.add(d2);
					}
				} else {// 需要汇总
							
					for (TeeVoteItem voteItem : voteItems) {
						String voteUserName = "";
						if(!"1".equals(voteObj.getAnonymity()) && !TeeUtility.isNullorEmpty(voteItemPersonMap.get(voteItem.getSid()))){
							voteUserName = TeeUtility.null2Empty(voteItemPersonMap.get(voteItem.getSid()));
						}
						Map d2 = new LinkedHashMap();
						d2.put("name", voteItem.getItemName());
						d2.put("total", simpleDaoSupport.count("select count(*) from TeeVoteItemPerson where voteItem.sid=" + voteItem.getSid(), null));
						d2.put("itemId", voteItem.getSid());
						d2.put("voteUserName", voteUserName);
						//根据sid获取
						
						items.add(d2);
					}
				}
				list.add(voteMap);
			}
		}
		
		return list;
	}
	
	
	/**
	 * 获取投票情况
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年6月26日
	 * @param requestMap
	 * @param person
	 * @return
	 * @throws ParseException
	 *             boolean
	 */
	public TeeJson showVoteDetail(Map requestMap, TeePerson person) throws ParseException {
		List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
		int sid = TeeStringUtil.getInteger(requestMap.get("sid"), 0);

		TeeVote vote = voteDao.get(sid);
		int voteCount = 0;
		int notVoteCount = 0;
		if (vote != null) {
			// 权限范围----部门、人员、角色
			List<TeeDepartment> listDept = vote.getPostDept();
			List<TeePerson> userList = vote.getPostUser();
			List<TeeUserRole> userRoleList = vote.getPostUserRole();

			StringBuffer deptBuffer = new StringBuffer();
			StringBuffer userBuffer = new StringBuffer();
			StringBuffer userRoleBuffer = new StringBuffer();

			if (listDept != null && listDept.size() > 0) {
				for (TeeDepartment obj : listDept) {
					if (deptBuffer.length() > 0) {
						deptBuffer.append(",");
					}
					deptBuffer.append(obj.getUuid());
				}
			}
			if (userList != null && userList.size() > 0) {
				for (TeePerson obj : userList) {
					if (userBuffer.length() > 0) {
						userBuffer.append(",");
					}
					userBuffer.append(obj.getUuid());
				}
			}

			if (userRoleList != null && userRoleList.size() > 0) {
				for (TeeUserRole obj : userRoleList) {
					if (userRoleBuffer.length() > 0) {
						userRoleBuffer.append(",");
					}
					userRoleBuffer.append(obj.getUuid());
				}
			}

			//获取权限范围----部门、人员、角色人员
			List<TeePerson> personList = personDao.getPersonsByUuidsOrDeptIdsOrRoleIds(userBuffer.toString(), deptBuffer.toString(), userRoleBuffer.toString());
			
			//根据vote Sid获取已投票数据
			List<TeeVotePerson> votePersons = votePersonDao.getVotePersonListByVoteId(sid);

			Map<Integer, TeeVotePerson> votePersonMap = new HashMap<Integer, TeeVotePerson>();
			if (votePersons != null && votePersons.size() > 0) {
				for (TeeVotePerson obj : votePersons) {
					votePersonMap.put(obj.getUser().getUuid(), obj);
				}
			}
			
			if (personList != null && personList.size() > 0){
				for (TeePerson obj : personList) {
					//if(!"1".equals(obj.getDeleteStatus())){
						String userName = "";
						String deptName = "";
						String roleName = "";
						String voteState = "";
						String voteTime = "";
						
						TeeVotePerson votePerson = votePersonMap.get(obj.getUuid());
						if(votePerson != null){
							userName = votePerson.getUser().getUserName();
							deptName = votePerson.getUser().getDept().getDeptName();
							roleName = votePerson.getUser().getUserRole().getRoleName();
							voteState = "1";
							voteTime = TeeUtility.getDateTimeStr(votePerson.getCreateDate());
						}else{
							userName = obj.getUserName();
							deptName = obj.getDept().getDeptName();
							roleName = obj.getUserRole().getRoleName();
							voteState = "0";
							voteTime = "";
						}
						Map<String,String> map = new HashMap<String, String>();
						map.put("userName", userName);
						map.put("deptName", deptName);
						map.put("roleName", roleName);
						map.put("voteState", voteState);
						map.put("voteTime", voteTime);
						mapList.add(map);
					//}
					
				}
			}
		}
		
		TeeJson json = new TeeJson();
		json.setRtData(mapList);
		json.setRtState(true);
		json.setRtMsg("数据获取成功!");
		return json;
	}
	
	

	/**
	 * 提醒所有未投票人
	 * @function:
	 * @author: wyw
	 * @data: 2015年6月26日
	 * @param requestMap
	 * @param person
	 * @return
	 * @throws ParseException
	 *             boolean
	 */
	public TeeJson remindVoteById(Map requestMap, TeePerson person) throws ParseException {
		List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
		int sid = TeeStringUtil.getInteger(requestMap.get("sid"), 0);

		TeeVote vote = voteDao.get(sid);
		int voteCount = 0;
		int notVoteCount = 0;
		if (vote != null) {
			// 权限范围----部门、人员、角色
			List<TeeDepartment> listDept = vote.getPostDept();
			List<TeePerson> userList = vote.getPostUser();
			List<TeeUserRole> userRoleList = vote.getPostUserRole();

			StringBuffer deptBuffer = new StringBuffer();
			StringBuffer userBuffer = new StringBuffer();
			StringBuffer userRoleBuffer = new StringBuffer();

			if (listDept != null && listDept.size() > 0) {
				for (TeeDepartment obj : listDept) {
					if (deptBuffer.length() > 0) {
						deptBuffer.append(",");
					}
					deptBuffer.append(obj.getUuid());
				}
			}
			if (userList != null && userList.size() > 0) {
				for (TeePerson obj : userList) {
					if (userBuffer.length() > 0) {
						userBuffer.append(",");
					}
					userBuffer.append(obj.getUuid());
				}
			}

			if (userRoleList != null && userRoleList.size() > 0) {
				for (TeeUserRole obj : userRoleList) {
					if (userRoleBuffer.length() > 0) {
						userRoleBuffer.append(",");
					}
					userRoleBuffer.append(obj.getUuid());
				}
			}

			//获取权限范围----部门、人员、角色人员
			List<TeePerson> personList = personDao.getPersonsByUuidsOrDeptIdsOrRoleIds(userBuffer.toString(), deptBuffer.toString(), userRoleBuffer.toString());
			
			//根据vote Sid获取已投票数据
			List<TeeVotePerson> votePersons = votePersonDao.getVotePersonListByVoteId(sid);

			Map<Integer, TeeVotePerson> votePersonMap = new HashMap<Integer, TeeVotePerson>();
			if (votePersons != null && votePersons.size() > 0) {
				for (TeeVotePerson obj : votePersons) {
					votePersonMap.put(obj.getUser().getUuid(), obj);
				}
			}
			
			StringBuffer userIdBuffer = new StringBuffer();
			if (personList != null && personList.size() > 0){
				for (TeePerson obj : personList) {
					TeeVotePerson votePerson = votePersonMap.get(obj.getUuid());
					if(votePerson != null){//未投票
						continue;
					}
					
					if(userIdBuffer.length()>0){
						userIdBuffer.append(",");
					}
					userIdBuffer.append(obj.getUuid());
					
				}
			}
			
			Map requestData = new HashMap();
			requestData.put("userListIds", userIdBuffer.toString());
			requestData.put("moduleNo", "029");
			requestData.put("content", "您有一条投票信息[" + vote.getSubject() + "],请查看。");
			requestData.put("remindUrl", "/system/core/base/vote/personal/vote.jsp?voteId=" + vote.getSid());
			smsSender.sendSms(requestData, null);
		}
		
		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtMsg("数据获取成功!");
		return json;
	}
	
	
	/**
	 * 封装导出数据
	 * @function: 
	 * @author: wyw
	 * @data: 2015年7月16日
	 * @param requestMap
	 * @return List<TeeDataRecord>
	 */
	public ArrayList<TeeDataRecord> getDbRecord(Map requestMap){
		ArrayList<TeeDataRecord > dateRecords = new ArrayList<TeeDataRecord>();
		
		TeePerson loginPerson = (TeePerson) requestMap.get(TeeConst.LOGIN_USER);
		int voteId = TeeStringUtil.getInteger(requestMap.get("sid"), 0);
		TeeVote voteObj = voteDao.get(voteId);
		if(voteObj != null ){
			//标题
			TeeDataRecord titleRecord = new TeeDataRecord();
			titleRecord.addField("选项", voteObj.getSubject());
			titleRecord.addField("票数","");
			titleRecord.addField("投票人", "");
			dateRecords.add(titleRecord);
			//描述
			TeeDataRecord contentRecord = new TeeDataRecord();
			contentRecord.addField("选项", voteObj.getContent());
			contentRecord.addField("票数","");
			contentRecord.addField("投票人", "");
			dateRecords.add(contentRecord);
			
			
			int counter = 0;
			List<Map> list = this.getManageVoteResult(voteId, loginPerson);
			if(list != null && list.size()>0){
				for(Map map:list){//题目
					List<Map> items = new ArrayList();
					Map item = new HashMap();
					
					String subject = (String) map.get("subject");
					String type = (String) map.get("type");
					List<Map> voteItems = (List<Map>) map.get("voteItems");
					Map voteItem = (Map) map.get("voteItem");
					int required = (Integer) map.get("required");
					
					
					
					if("2".equals(type) || "3".equals(type)){//文本框
						
					}else{
						counter++;
						//投票项目
						TeeDataRecord subjectRecord = new TeeDataRecord();
						subjectRecord.addField("选项", counter + "、" + subject+ "`~");
						subjectRecord.addField("票数", "");
						subjectRecord.addField("投票人", "");
						dateRecords.add(subjectRecord);
						
						int itemCounter = 0;
						if(voteItems != null && voteItems.size()>0){//选项
							for(Map itemMap:voteItems){
								itemCounter++;
								//投票项
								TeeDataRecord record = new TeeDataRecord();
								record.addField("选项", "(" + itemCounter + ")" + itemMap.get("name"));
								record.addField("票数", itemMap.get("total"));
								record.addField("投票人", itemMap.get("voteUserName"));
								dateRecords.add(record);
							}
						}
					}
					
				}
				
			}
			
		}
		return dateRecords;
		
	}
	

	

}
