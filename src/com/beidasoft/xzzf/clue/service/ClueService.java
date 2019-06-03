package com.beidasoft.xzzf.clue.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.clue.bean.Clue;
import com.beidasoft.xzzf.clue.bean.ClueInformer;
import com.beidasoft.xzzf.clue.bean.ClueLeaderOpinion;
import com.beidasoft.xzzf.clue.dao.ClueDao;
import com.beidasoft.xzzf.clue.model.ClueLeaderOpinionModel;
import com.beidasoft.xzzf.clue.model.ClueModel;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.beidasoft.xzzf.common.service.ClueRegionService;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.model.TeeDepartmentModel;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeBeanFactory;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.servlet.HttpClientUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class ClueService extends TeeBaseService{

	@Autowired
	private ClueDao clueDao;
	
	@Autowired
	private ClueInformerService informerService;
	
	@Autowired
	private TeeAttachmentService attachmentService;

	@Autowired
	private ClueCodeDetailService codeDetailService;
	
	@Autowired
	private ClueRegionService regionService;
	
	@Autowired
	private ClueLeaderOpinionService opinionService;

	@Autowired
	private TeePersonDao personDao;
	/**
	 * 保存线索信息
	 * 
	 * @param clueModel
	 * @param request
	 * @return
	 */
	public Clue save(ClueModel clueModel, HttpServletRequest request){// 实例化实体类对象

		TeePerson user = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Clue clue = new Clue();
		ClueInformer informer = new ClueInformer();

		// 属性值传递
		BeanUtils.copyProperties(clueModel, clue);

		// 单独处理时间类型转换
		clue.setDocumentDate(TeeDateUtil.format(clueModel.getDocumentDateStr(),"yyyy年MM月dd日"));
		clue.setReportDate(TeeDateUtil.format(clueModel.getReportDateStr(),"yyyy年MM月dd日"));
		clue.setCreateTime(TeeDateUtil.format(clueModel.getCreateTimeStr(),"yyyy年MM月dd日"));

		//保存线索创建人信息
		clue.setCreateUserId(user.getUuid());
		clue.setCreateUserName(user.getUserName());
		//自动生成主键ID
		clue.setId(UUID.randomUUID().toString());
		//线索阶段的BaseId 未空  因为还未被转换为案件
		clue.setBaseId("");
		clueDao.save(clue);
		
		// 前台传来的举报人信息
		JSONArray informerArray = JSONArray.fromObject(clueModel.getInformerArray()); 
		JSONArray contactArray = JSONArray.fromObject(clueModel.getContactArray());
		for (int i = 0; i < informerArray.size(); i++) {
			JSONObject object = informerArray.getJSONObject(i);

			informer = new ClueInformer();

			// 自动生成举报人信息表的主键ID
			informer.setId(UUID.randomUUID().toString());
			// 取得举报人相关信息
			informer.setClueId(clue.getId());
			String type = object.getString("pTypeValue");
			informer.setInformerType(type);
			String tel = "";
			String mail = object.getString("pEmailValue");
			if (type.equals("1")) {// 举报人类型为公民
				tel += object.getString("pTelValue");
				// 公民相关属性
				informer.setPersonalName(object.getString("pNameValue"));
				informer.setPersonalAddress(object.getString("pAddressValue"));
				informer.setPersonalTel(tel);
				informer.setPersonalMail(mail);
				informer.setPersonalCode(object.getString("pCodeValue"));
				informer.setPersonalRemark(object.getString("pRemarkValue"));
			} else if (type.equals("2")) {// 举报人类型为组织机构
				// 组织机构相关属性
				tel += object.getString("orgaPersonTelValue");
				informer.setOrganizationAddress(object.getString("orgaAddressValue"));
				informer.setOrganizationCode(object.getString("orgaCodeValue"));
				informer.setOrganizationName(object.getString("orgaNameValue"));
				informer.setOrganInformerPersonName(object.getString("orgaPersonNameValue"));
				informer.setOrganInformerPersonTel(tel);
			}

			//是否多次举报
			informer.setIsInformers(object.getString("isInformersValue"));

			String iscontacts = object.getString("isContactsValue");// 该举报人是否为联系人
			if (iscontacts.equals("1")) {// 如果字段 “是否为联系人” 的值已知（是联系人，即点击了界面的同步联系人按钮）
				informer.setIsContacts("1");
				for (int j = 0; j < contactArray.size(); j++) {// 遍历联系人数组
					JSONObject obj = contactArray.getJSONObject(j);
					if (tel.equals(obj.getString("cTelValue"))) {// 通过判断电话号码来确认是否为同一人
						// 联系人信息赋值
						informer.setContactsName(obj.getString("cNameValue"));
						informer.setContactsTel(obj.getString("cTelValue"));
						informer.setContactsMail(obj.getString("cMailValue"));
						informer.setContactsAddress(obj.getString("cAddressValue"));

						// 保存联系人信息
						informerService.save(informer);
						break;
					}
				}
			} else { // 如果字段 “是否为联系人” 的值未知（未点击界面的同步联系人按钮）
				for (int j = 0; j < contactArray.size(); j++) { // 遍历联系人信息
					JSONObject obj = contactArray.getJSONObject(j);
					// 判断下是否有和举报人信息相同的联系人，如果有，则设置字段 “是否为联系人 ” 的值 并且进行联系人信息的相关赋值
					if (tel.equals(obj.getString("cTelValue"))
							&& mail.equals(obj.getString("cMailValue"))) {
						informer.setIsContacts("1");
						informer.setContactsName(obj.getString("cNameValue"));
						informer.setContactsTel(obj.getString("cTelValue"));
						informer.setContactsMail(obj.getString("cMailValue"));
						informer.setContactsAddress(obj
								.getString("cAddressValue"));
						informerService.save(informer);
						break;

					} else {// 如果没有和举报人信息相同的联系人，则设置字段 “是否为联系人” 的值 并且直接保存
							// 不需要遍历联系人数组
						informer.setIsContacts("0");
						informerService.save(informer);
					}
				}
			}
		}
		return clue;
	}
	
	
	/**
	 * 更新线索信息（例如修改状态之类）
	 * @param clue
	 */
	public void update(Clue clue) {
		clueDao.update(clue);
	}
	
	
	/**
	 * 更新线索信息
	 * 
	 * @param clueModel
	 * @param request
	 * @return
	 */
	public Clue update(ClueModel clueModel, HttpServletRequest request){
		TeePerson user = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		//更新前查固定创建人字段
		Clue befClue = clueDao.get(clueModel.getId());
		ClueInformer informer = new ClueInformer();
		int createUserId = befClue.getCreateUserId();
		String createUserName = befClue.getCreateUserName();
		Date createTime = befClue.getCreateTime();
		
		// 属性值传递
		BeanUtils.copyProperties(clueModel, befClue);

		// 单独处理时间类型转换
		befClue.setDocumentDate(TeeDateUtil.format(clueModel.getDocumentDateStr(),"yyyy年MM月dd日"));
		befClue.setReportDate(TeeDateUtil.format(clueModel.getReportDateStr(),"yyyy年MM月dd日"));
		befClue.setCreateTime(TeeDateUtil.format(clueModel.getCreateTimeStr(),"yyyy年MM月dd日"));

		//线索阶段的BaseId 未空  因为还未被转换为案件
		befClue.setBaseId("");
		//设置创建人信息
		befClue.setCreateTime(createTime);
		befClue.setCreateUserId(createUserId);
		befClue.setCreateUserName(createUserName);
		//设置更新时间
		befClue.setUpdateUserId(user.getUuid());
		befClue.setUpdateUserName(user.getUserName());
		befClue.setUpdateTime(Calendar.getInstance().getTime());
		clueDao.update(befClue);
		
		//先删除该线索下的举报人信息
		informerService.deleteByClueId(befClue.getId());
		// 前台传来的举报人信息
		JSONArray informerArray = JSONArray.fromObject(clueModel.getInformerArray()); 
		JSONArray contactArray = JSONArray.fromObject(clueModel.getContactArray());
		for (int i = 0; i < informerArray.size(); i++) {
			JSONObject object = informerArray.getJSONObject(i);

			informer = new ClueInformer();

			// 自动生成举报人信息表的主键ID
			informer.setId(UUID.randomUUID().toString());
			// 取得举报人相关信息
			informer.setClueId(befClue.getId());
			String type = object.getString("pTypeValue");
			informer.setInformerType(type);
			String tel = "";
			String mail = object.getString("pEmailValue");
			if (type.equals("1")) {// 举报人类型为公民
				tel += object.getString("pTelValue");
				// 公民相关属性
				informer.setPersonalName(object.getString("pNameValue"));
				informer.setPersonalAddress(object.getString("pAddressValue"));
				informer.setPersonalTel(tel);
				informer.setPersonalMail(mail);
				informer.setPersonalCode(object.getString("pCodeValue"));
				informer.setPersonalRemark(object.getString("pRemarkValue"));
			} else if (type.equals("2")) {// 举报人类型为组织机构
				// 组织机构相关属性
				tel += object.getString("orgaPersonTelValue");
				informer.setOrganizationAddress(object.getString("orgaAddressValue"));
				informer.setOrganizationCode(object.getString("orgaCodeValue"));
				informer.setOrganizationName(object.getString("orgaNameValue"));
				informer.setOrganInformerPersonName(object.getString("orgaPersonNameValue"));
				informer.setOrganInformerPersonTel(tel);
			}

			//是否多次举报
			informer.setIsInformers(object.getString("isInformersValue"));

			String iscontacts = object.getString("isContactsValue");// 该举报人是否为联系人
			if (iscontacts.equals("1")) {// 如果字段 “是否为联系人” 的值已知（是联系人，即点击了界面的同步联系人按钮）
				informer.setIsContacts("1");
				for (int j = 0; j < contactArray.size(); j++) {// 遍历联系人数组
					JSONObject obj = contactArray.getJSONObject(j);
					if (tel.equals(obj.getString("cTelValue"))) {// 通过判断电话号码来确认是否为同一人
						// 联系人信息赋值
						informer.setContactsName(obj.getString("cNameValue"));
						informer.setContactsTel(obj.getString("cTelValue"));
						informer.setContactsMail(obj.getString("cMailValue"));
						informer.setContactsAddress(obj.getString("cAddressValue"));

						// 保存联系人信息
						informerService.save(informer);
						break;
					}
				}
			} else { // 如果字段 “是否为联系人” 的值未知（未点击界面的同步联系人按钮）
				for (int j = 0; j < contactArray.size(); j++) { // 遍历联系人信息
					JSONObject obj = contactArray.getJSONObject(j);
					// 判断下是否有和举报人信息相同的联系人，如果有，则设置字段 “是否为联系人 ” 的值 并且进行联系人信息的相关赋值
					if (tel.equals(obj.getString("cTelValue"))
							&& mail.equals(obj.getString("cMailValue"))) {
						informer.setIsContacts("1");
						informer.setContactsName(obj.getString("cNameValue"));
						informer.setContactsTel(obj.getString("cTelValue"));
						informer.setContactsMail(obj.getString("cMailValue"));
						informer.setContactsAddress(obj
								.getString("cAddressValue"));
						informerService.save(informer);
						break;

					} else {// 如果没有和举报人信息相同的联系人，则设置字段 “是否为联系人” 的值 并且直接保存
							// 不需要遍历联系人数组
						informer.setIsContacts("0");
						informerService.save(informer);
					}
				}
			}
		}
		return befClue;
	}
	
	/**
	 * 继承的get 方法
	 * 
	 * @param id
	 * @return
	 */
	public Clue get(String id) {
		return clueDao.get(id);
	}
	
	
	/**
	 * 通过ID获取一个线索对象
	 * @param sid
	 * @return
	 */
	public ClueModel beanToModel(Clue clue){
		//字段copy  bean---> model
		String id = clue.getId();

		ClueModel clueModel = new ClueModel();
		BeanUtils.copyProperties(clue, clueModel);

		// 单独处理时间类型
		if (clue.getCreateTime() != null) {
			clueModel.setCreateTimeStr(TeeDateUtil.format(clue.getCreateTime(), "yyyy年MM月dd日"));
		}
		if (clue.getReportDate() != null) {
			clueModel.setReportDateStr(TeeDateUtil.format(clue.getReportDate(), "yyyy年MM月dd日"));
		}
		if (clue.getDocumentDate() != null) {
			clueModel.setDocumentDateStr(TeeDateUtil.format(clue.getDocumentDate(), "yyyy年MM月dd日"));
		}
		if (clue.getAcceptTime() != null) {
			clueModel.setAcceptTimeStr(TeeDateUtil.format(clue.getAcceptTime(), "yyyy年MM月dd日"));
		}
		if (clue.getLeaderTime() != null) {
			clueModel.setLeaderTimeStr(TeeDateUtil.format(clue.getLeaderTime(), "yyyy年MM月dd日"));
		}

		// 附带的用于 附件的List
		List<TeeAttachmentModel> attachments = attachmentService.getAttacheModels("clue", id + "");
		clueModel.setAttachModel(attachments);

		// 附带的用于 举报人的List
		List<ClueInformer> informerList = informerService.getByClueId(id);
		clueModel.setClueInformer(informerList);

//		// 附带的用于 举报类型的List
//		List<ClueCodeDetail> TypeList = codeDetailService.findTypeList();
//		clueModel.setTypeList(TypeList);
//
//		// 附带的用于 举报来源的List
//		List<ClueCodeDetail> FormList = codeDetailService.findFormList();
//		clueModel.setFormList(FormList);

//		List<ClueCodeDetail> departList = codeDetailService.findDepartList();
		//获取拟办理单位
		List <TeeDepartmentModel> models = getDepartList();
		clueModel.setDepartList(models);

//		// 如果举报来源是其他省市转办 则获取省市的List
//		if (clue.getReportForm().equals("04")) {
//			List<Region> proviceList = regionService.findProviceList();
//			clueModel.setProvinceList(proviceList);
//			List<Region> cityList = regionService.findCityList(clue.getAnotherProvince());
//			clueModel.setCityList(cityList);
//		} else {
//			// 否则只获取正常的举报来源List
//			List<ClueCodeDetail> SourceList = codeDetailService.findSourceList(clueModel.getReportForm());
//			clueModel.setSourceList(SourceList);
//		}

		// 如果状态为 线索确认以后 则获取领导意见的list集合
		if (clue.getStaus() >= 20) {
			List<ClueLeaderOpinion> opinionlist = opinionService.getListByClueId(id);
			if (opinionlist.size() > 0) {

				List<ClueLeaderOpinionModel> opinionModelList = new ArrayList<ClueLeaderOpinionModel>();
				for (ClueLeaderOpinion c : opinionlist) {
					ClueLeaderOpinionModel model = new ClueLeaderOpinionModel();

					BeanUtils.copyProperties(c, model);
					model.setCreateTimeStr(TeeDateUtil.format(
							c.getCreateTime(), "yyyy年MM月dd日"));
					opinionModelList.add(model);
				}

				clueModel.setOpinionList(opinionModelList);
			}
		}	
		return clueModel;
	}
	
	/**
	 * 根据分页进行查询
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<Clue> listByPage(int firstResult,int rows,ClueModel queryModel, TeePerson user){
		return clueDao.listByPage(firstResult, rows, queryModel, user);
	}
	
	/**
	 * 返回所有线索信息(线索受理)
	 * @param Clue
	 */
	public List<Clue> getAllWaitClues(int firstResult,int rows,ClueModel queryModel){
		return clueDao.findWaitClues(firstResult,rows,queryModel);
	}
	
	/**
	 * 返回所有线索信息(线索确认)
	 * @param Clue
	 */
	public List<Clue> getAllAdmitClues(int firstResult,int rows,ClueModel queryModel){
		return clueDao.findAdmitClues(firstResult,rows,queryModel);
	}
	
	/**
	 * 返回总记录数
	 * @return
	 */
	public long getTotal(ClueModel queryModel, TeePerson user){
		return clueDao.getTotal(queryModel,user);
	}
	
	/**
	 * 返回总记录数(线索受理)
	 * @return
	 */
	public long getWaitTotals(ClueModel queryModel){
		return clueDao.getWaitTotals(queryModel);
	}
	
	/**
	 * 返回总记录数(线索确认)
	 * @return
	 */
	public long getAdmitTotals(ClueModel queryModel){
		return clueDao.getAdmitTotals(queryModel);
	}
	
	/**
	 * 分页查询回复列表
	 * @param firstResult
	 * @param rows
	 * @param queryModel
	 * @param user
	 * @return
	 */
	public List<Clue> replylistByPage(TeeDataGridModel dm, ClueModel queryModel, TeePerson user) {
		return clueDao.replyListByPage(dm.getFirstResult(), dm.getRows(), queryModel, user);
	}
	
	/**
	 * 查询回复列表总数
	 * @param queryModel
	 * @param user
	 * @return
	 */
	public long replylistCount(ClueModel queryModel, TeePerson user) {
		return clueDao.replylistCount(queryModel, user);
	}
	
	/**
	 * 签收总队机关即为举报信访移交处理登记表数据(保存到线索)
	 * @param request
	 * @return
	 */
	public TeeJson saveTurnSend(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int logUuid = user.getUuid();
		String userName = user.getUserName();
		String reportIds = request.getParameter("idStr");
		String signReplyStatic = TeeStringUtil.getString( request.getParameter("signReplyStatic"), "");
		String url = TeeSysProps.getString("XF_URL");
		boolean executeFlg = true;
		String[] splitId = reportIds.split(",");
		for (int i = 0; i < splitId.length; i++) {
			String id = splitId[i];
			String respStr = HttpClientUtil.requestGet(url+"/module/petletter/xjzx/viewInfo.json?reportId="+id);
			JSONObject object = JSONObject.fromObject(respStr);
			Object resultData = object.get("data");
			Object resultMsg = object.get("message");
			Object resultFlg = object.get("success");
			JSONObject fromObject = null;
			fromObject = JSONObject.fromObject(resultData);
			JSONObject jsonInfo = null;
			Clue clue = new Clue();
			ClueInformer informer = new ClueInformer();
			clue.setId(fromObject.get("reportId").toString());
			clue.setDocumentCode(fromObject.get("reportNum").toString());
			clue.setDocumentTitle(fromObject.get("letterTitle").toString());
			clue.setStaus(10);
			long reportData =TeeStringUtil.getLong(fromObject.get("reportTime"), 0) ;
			//下载附件
			String fileGroupID = TeeStringUtil.getString(fromObject.get("fileGroupID"), "");
			String requestFileList = HttpClientUtil.requestGet(url+"/module/components/gtcmsblob/findInfoList.json?fileGroupID="+fileGroupID);
			JSONObject fileList = null;
			fileList = JSONObject.fromObject(requestFileList);
			if ("1".equals(signReplyStatic)) {
				if ("true".equals(fileList.getString("success"))) {
					JSONObject data = JSONObject.fromObject(fileList.getString("data"));
					String substring = data.getString("resultList");
					JSONArray dataArray = JSONArray.fromObject(substring);
					for (int j = 0; j < dataArray.size(); j++) {
						JSONObject fromObject2 = JSONObject.fromObject(dataArray.get(1));
						String fileName = TeeStringUtil.getString(fromObject2.get("fileName"));
						String blobId = TeeStringUtil.getString(fromObject2.get("blobId"));
						HttpServletResponse response = null;
						try {
							this.downloadNet(request, response, blobId, fileName);
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					}
				}
			}		
			Calendar c=Calendar.getInstance();
			c.setTimeInMillis(reportData);
			clue.setReportDate(c.getTime());
			String signReplyStatic1 = fromObject.get("signReplyStatic").toString();
			if (!"1".equals(signReplyStatic1)) {
				clue.setCreateUserId(logUuid);
				clue.setCreateUserName(userName);
				clue.setCreateTime(Calendar.getInstance().getTime());
			} else {
				//状态不是为接受则删除联系人
				informerService.deleteByClueId(TeeStringUtil.getString(fromObject.get("reportId"), ""));
			}
			clue.setUpdateTime(Calendar.getInstance().getTime());
			clue.setUpdateUserId(logUuid);
			clue.setUpdateUserName(userName);
			informer.setId(UUID.randomUUID().toString());
			informer.setPersonalName(TeeStringUtil.getString(fromObject.get("reporter"), ""));
			informer.setPersonalTel(TeeStringUtil.getString(fromObject.get("telNum"), ""));
			informer.setContactsName(TeeStringUtil.getString(fromObject.get("reporter"), ""));
			informer.setContactsTel(TeeStringUtil.getString(fromObject.get("telNum"), ""));
			informer.setClueId(TeeStringUtil.getString(fromObject.get("reportId"), ""));
			informer.setInformerType("1");
			clueDao.saveOrUpdate(clue);
			informerService.save(informer);
			
		}
		
		String parm = "";
		Map params=new HashMap();
		if ("1".equals(signReplyStatic)) {
			//1为签收
			int receiverId = logUuid;
			String receiver = userName;
			params.put("signReplyStatic",signReplyStatic);
			params.put("receiverId",receiverId);
			params.put("receiver",receiver);
			params.put("reportIds",reportIds);
		} else if ("2".equals(signReplyStatic)) {
			//2为回复
			int fuPeopleId = logUuid;
			String fuPeople = userName;
			params.put("signReplyStatic",signReplyStatic);
			params.put("reportIds",reportIds);
			params.put("fuPeopleId",fuPeopleId);
			params.put("fuPeople",fuPeople);
			params.put("processState",1);
		}
		
		
		String updUrl = url+"/module/petletter/xjzx/updateInfo.json";
		String respStr1 =HttpClientUtil.requestPost(params, updUrl);
		JSONObject js = JSONObject.fromObject(respStr1);
		String status = String.valueOf(js.get("status"));
		String success = String.valueOf(js.get("success"));
		
			
		if (!"true".equals(success.toString())) {
			if("1".equals(signReplyStatic)) {
				json.setRtMsg("数据接收失败，请联系管理员！");
			} else {
				json.setRtMsg("数据回复失败，请联系管理员！");
			}
			executeFlg = false;
		} 
			
		if (!executeFlg) {
			// 操作失败时，进行回滚
			throw new TeeOperationException(json.getRtMsg());
		}
		json.setRtState(true);
		
		return json;
	 }
	
	/**
	 * 将附件从机关纪委服务器下载后在上传到本服务器
	 * @param request
	 * @param response
	 * @param blobId
	 * @param fileName
	 * @throws MalformedURLException
	 */
     public void downloadNet(HttpServletRequest request, HttpServletResponse response, String blobId, String fileName) throws MalformedURLException {
        // 下载网络文件
        URL url = new URL("http://39.106.62.234:8000/module/components/gtcmsblob/download?blobId="+blobId);
        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
    		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
    		TeePerson realPerson = personDao.load(loginPerson.getUuid());
	        TeeBaseUpload baseUpload = (TeeBaseUpload) TeeBeanFactory.getBean("teeBaseUpload");
	        TeeAttachment attach = baseUpload.singleAttachUpload(inStream, inStream.available(), fileName, fileName, "clue", "clue_idx", realPerson);
			if ("".equals(attach)) {
				throw new TeeOperationException("附件上传失败");
			}
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
 	/**
 	 * 根据领域查询举报受理量
 	 * @return
 	 */
 	public List getCountBydomainType(){
 		Calendar cal = Calendar.getInstance();
 		//拿到本年yyyy日期
 		int thisYearStart = cal.get(Calendar.YEAR);
 		
 		StringBuffer sql = new StringBuffer("");

 		sql.append("SELECT  REPORT_TYPE namea,");
 		sql.append("      COUNT (ID) AS num");
 		sql.append("         FROM ZF_CLUE_BASE");
 		sql.append(" WHERE to_date(to_char(CREATE_TIME, 'yyyy'),'yyyy') = to_date('");
 		sql.append(thisYearStart);
 		sql.append("','yyyy')");
 		sql.append("		GROUP BY REPORT_TYPE ORDER BY num DESC");
 		List result = simpleDaoSupport.executeNativeQuery(sql.toString(), null,0, 10000);
 		return result;
 	}
 	
 	/**
 	 * 根据部门查询举报半结量
 	 * @return
 	 */
 	public List getCountByDept(){
 		Calendar cal = Calendar.getInstance();
 		//拿到本年yyyy日期
 		int thisYearStart = cal.get(Calendar.YEAR);
 		
 		StringBuffer sql = new StringBuffer("");

 		sql.append("SELECT  DEAL_DEPART namea,");
 		sql.append("      COUNT (ID) AS num");
 		sql.append("         FROM ZF_CLUE_BASE");
 		sql.append(" WHERE STAUS = 99 AND to_date(to_char(CREATE_TIME, 'yyyy'),'yyyy') = to_date('");
 		sql.append(thisYearStart);
 		sql.append("','yyyy')");
 		sql.append("		GROUP BY DEAL_DEPART ORDER BY num DESC");
 		List result = simpleDaoSupport.executeNativeQuery(sql.toString(), null,0, 10000);
 		return result;
 	}
 	
 	/**
 	 * 查询举报办结量根据领域
 	 * @return
 	 */
 	public List getCountByReport(){
 		Calendar cal = Calendar.getInstance();
 		//拿到本年yyyy日期
 		int thisYearStart = cal.get(Calendar.YEAR);
 		
 		StringBuffer sql = new StringBuffer("");

 		sql.append("SELECT  REPORT_TYPE namea,");
 		sql.append("      COUNT (ID) AS num");
 		sql.append("         FROM ZF_CLUE_BASE");
 		sql.append(" WHERE STAUS = 99 AND to_date(to_char(CREATE_TIME, 'yyyy'),'yyyy') = to_date('");
 		sql.append(thisYearStart);
 		sql.append("','yyyy')");
 		sql.append("		GROUP BY REPORT_TYPE ORDER BY num DESC");
 		List result = simpleDaoSupport.executeNativeQuery(sql.toString(), null,0, 10000);
 		return result;
 	}
 	
 	/**
 	 * 查询已办结的方法
 	 * @return
 	 */
 	public List getCountByStaus(){
 		Calendar cal = Calendar.getInstance();
 		//拿到本年yyyy日期
 		int thisYearStart = cal.get(Calendar.YEAR);
 		
 		StringBuffer sql = new StringBuffer("");

 		sql.append("      SELECT COUNT (ID) AS num");
 		sql.append("         FROM ZF_CLUE_BASE");
 		sql.append(" WHERE STAUS = 99 AND to_date(to_char(CREATE_TIME, 'yyyy'),'yyyy') = to_date('");
 		sql.append(thisYearStart);
 		sql.append("','yyyy')");
 		List result = simpleDaoSupport.executeNativeQuery(sql.toString(), null,0, 10000);
 		return result;
 	}
 	
 	/**
 	 * 查询未办结的方法
 	 * @return
 	 */
 	public List getCountByStausW(){
 		Calendar cal = Calendar.getInstance();
 		//拿到本年yyyy日期
 		int thisYearStart = cal.get(Calendar.YEAR);
 		
 		StringBuffer sql = new StringBuffer("");

 		sql.append("      SELECT COUNT (ID) AS num");
 		sql.append("         FROM ZF_CLUE_BASE");
 		sql.append(" WHERE STAUS < 99 AND to_date(to_char(CREATE_TIME, 'yyyy'),'yyyy') = to_date('");
 		sql.append(thisYearStart);
 		sql.append("','yyyy')");
 		List result = simpleDaoSupport.executeNativeQuery(sql.toString(), null,0, 10000);
 		return result;
 	}
 	
 	/**
 	 * 查询今天的新增的举报办理
 	 * @return
 	 */
 	public List getCountByDay(){
 		//拿到今天的日期
 		Date date = new Date();
 		String dateStr = TeeDateUtil.format(date);
 		
 		StringBuffer sql = new StringBuffer("");

 		sql.append("      SELECT COUNT (ID) AS num");
 		sql.append("         FROM ZF_CLUE_BASE");
 		sql.append(" WHERE to_date(to_char(CREATE_TIME, 'yyyy-MM-dd'),'yyyy-MM-dd') = to_date('");
		sql.append(dateStr);
 		sql.append("','yyyy-MM-dd')");
 		List result = simpleDaoSupport.executeNativeQuery(sql.toString(), null,0, 10000);
 		return result;
 	}
 	
 	/**
 	 * 查询领导页面案件数
 	 * @param  dayOrYear  今日或年度
 	 * @param  deptId  部门ID
 	 * @param  status  0所有，1未办结，2已办结
 	 * @return  long
 	 */
 	public long getLeaderCount(String dayOrYear, int deptId, int status){
 		//拿到今天的日期
 		Date date = new Date();
 		String dateStr = TeeDateUtil.format(date);
 		
 		//拿到本年yyyy日期
 		Calendar cal = Calendar.getInstance();
 		int thisYear = cal.get(Calendar.YEAR);
 		
 		StringBuffer hql = new StringBuffer("");
 		hql.append("      SELECT COUNT (id)");
 		hql.append("         FROM Clue where 1=1 ");
 		if("day".equals(dayOrYear)){
 			hql.append(" and to_date(to_char(createTime, 'yyyy-MM-dd'),'yyyy-MM-dd') = to_date('");
 	 		hql.append(dateStr);
 	 		hql.append("','yyyy-MM-dd')");
 		}else if("year".equals(dayOrYear)){
 			hql.append(" and to_date(to_char(createTime, 'yyyy'),'yyyy') = to_date('");
 			hql.append(thisYear);
 			hql.append("','yyyy')");
 		}
 		if(status==1){
 			hql.append(" and staus<99");
 		}else if(status==2){
 			hql.append(" and staus>=99");
 		}
 		if(deptId>0){
 			hql.append(" and dealDepart="+deptId);
 		}
 		long result = simpleDaoSupport.count(hql.toString(), null);
 		return result;
 	}

	/**
	 * 获取拟办理单位
	 * 
	 * @return
	 */
	public List<TeeDepartmentModel> getDepartList() {
		List <TeeDepartment> depList = simpleDaoSupport.executeQuery("from TeeDepartment where deptNo > 8 and deptNo < 35 "
				+ "and deptNo not in (10 ,17 , 19) order by deptNo " , null);
		List <TeeDepartmentModel> models = new ArrayList<TeeDepartmentModel>();
		for (TeeDepartment teeDepartment : depList) {
			TeeDepartmentModel model = new TeeDepartmentModel();
			BeanUtils.copyProperties(teeDepartment, model);
			models.add(model);
		}
		return models;
	}
}
