package com.tianee.oa.core.org.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.pm.bean.TeeHumanDoc;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.core.general.service.TeeSysParaService;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeeOrganization;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeePersonDynamicInfo;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeeOrgDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.core.priv.bean.TeeMenuGroup;
import com.tianee.oa.core.priv.bean.TeeSysMenu;
import com.tianee.oa.core.priv.dao.TeeMenuGroupDao;
import com.tianee.oa.core.priv.dao.TeeModulePrivDao;
import com.tianee.oa.core.priv.model.TeeSysMenuModel;
import com.tianee.oa.core.priv.service.TeeMenuGroupService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.sync.config.bean.TeeOutSystemConfig;
import com.tianee.oa.sync.log.bean.TeeOutSystemSyncLog;
import com.tianee.oa.sync.log.dao.TeeSyncLogDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.dynamic.TeeClassRunner;
import com.tianee.webframe.util.file.TeeCSVUtil;
import com.tianee.webframe.util.openfire.TeeOpenfireUtil;
import com.tianee.webframe.util.secure.TeePassEncryptMD5;
import com.tianee.webframe.util.servlet.HttpClientUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

@Service
public class TeePersonService extends TeeBaseService {

	@Autowired
	private  TeePersonDao personDao;

	@Autowired
	private TeeDeptDao deptDao;

	@Autowired
	private TeeOrgDao orgDao;

	@Autowired
	private TeeUserRoleDao roleDao;

	@Autowired
	private TeeMenuGroupDao menuGroupDao;

	@Autowired
	private TeeDeptService deptService;

	@Autowired
	private TeeModulePrivDao modulPrivDao;

	@Autowired
	private TeeBaseUpload baseUpload;

	@Autowired
	private TeeSysParaService sysParaService;

	
	@Autowired
	private TeeAttachmentDao teeAttachmentDao;

	
	@Autowired
	private TeeSyncLogDao logDao;
	
	@Autowired
	TeeMenuGroupService  menuGroupServ;

	/**
	 * 新增 TeePerson
	 * 
	 * @param
	 */
	public void add(TeePerson person2) {

		TeePerson person = new TeePerson();
		try {
			// 单位
			if (orgDao.traversalOrg().size() <= 0) {// 单位
				TeeOrganization org = new TeeOrganization();
				org.setUnitName("北京天一科技有限公司");
				org.setAddress("北京西城区广安门");
				org.setTelNo("010-88888888");
				orgDao.addOrg(org);
			}
			// 角色
			TeeUserRole role = new TeeUserRole();
			List<TeeUserRole> list = roleDao.selectUserPrivList();
			if (list.size() > 0) {
				// role = list.get(0);
			} else {
				/*
				 * role.setRoleName("系统管理员"); role.setRoleNo(1);
				 * roleDao.save(role);
				 */
			}

			// 部门
			TeeDepartment dept = new TeeDepartment();
			List<TeeDepartment> deptList = deptDao.getAllDept();
			if (deptList.size() > 0) {
				// dept = deptList.get(0);
			} else {
				dept.setDeptName("研发部");
				dept.setDeptNo(1);
				dept.setDeptParentLevel("");
				try {
					dept.setGuid(TeePassEncryptMD5.getRandomGUID());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// deptDao.addDept(dept);
			}

			// 人员

			if (personDao.getPersonByUserId("admin") == null) {
				person.setUserId("admin");
				person.setUserName("系统管理员");
				person.setPassword(TeePassEncryptMD5.cryptDynamic("123456"));
				person.setUserRole(role);
				person.setDept(dept);
				person.setDeleteStatus("0");
				personDao.addPerson(person);

			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 更新
	 * 
	 * @param TeePerson
	 * @throws UnsupportedEncodingException 
	 */
	public TeeJson addOrUpdate(TeePersonModel personModul,HttpServletRequest request) throws UnsupportedEncodingException {
		TeeSysLog sysLog = TeeSysLog.newInstance();
        //获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		TeeJson json = new TeeJson();
		TeePerson person = new TeePerson();
		try {
			// 辅助角色
			if (!TeeUtility.isNullorEmpty(personModul.getUserRoleOtherId())) {
				List<TeeUserRole> roleList = roleDao
						.getPrivListByUuids(personModul.getUserRoleOtherId());
				person.setUserRoleOther(roleList);
			}
			// 辅助部门
			if (!TeeUtility.isNullorEmpty(personModul.getDeptIdOtherStr())) {
				List<TeeDepartment> deptOtherlist = deptDao
						.getDeptListByUuids(personModul.getDeptIdOtherStr());
				person.setDeptIdOther(deptOtherlist);
			}

			// 管理范围
			if (!TeeUtility.isNullorEmpty(personModul.getPostDeptStr())) {
				List<TeeDepartment> postDeptlist = deptDao
						.getDeptListByUuids(personModul.getPostDeptStr());
				person.setPostDept(postDeptlist);
			}

			// 菜单组
			if (!TeeUtility.isNullorEmpty(personModul.getMenuGroupsStr())) {
				List<TeeMenuGroup> menuGrouplist = menuGroupDao
						.getMenuGroupListByUuids(personModul.getMenuGroupsStr());
				person.setMenuGroups(menuGrouplist);
			}

			if (!TeeUtility.isNullorEmpty(personModul.getDeptId())) {
				person.setDept(deptDao.selectDeptById(Integer
						.parseInt(personModul.getDeptId())));
			}

			if (!TeeUtility.isNullorEmpty(personModul.getUserRoleStr())) {
				person.setUserRole(roleDao.selectByUUId(Integer
						.parseInt(personModul.getUserRoleStr())));
			}

			if (!TeeUtility.isNullorEmpty(personModul.getBirthdayStr())) {
				Date birthday;
				try {
					birthday = TeeUtility.parseDate("yyyy-MM-dd",
							personModul.getBirthdayStr());
					person.setBirthday(birthday);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				person.setBirthday(null);
			}

			if (personModul.getUuid() <= 0) {// 新增
				// 判断人数限制
				int personLimit = 0;

				try {
					personLimit = (Integer) TeeClassRunner.exec(
							"com.tianee.webframe.util.auth.TeeAuthUtil",
							"getPersonLimit", null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// 从数据库中获取最大人数
				long personCount = simpleDaoSupport
						.count("select count(*) from TeePerson p where p.deleteStatus='0'",
								null);
				if (personCount >= personLimit) {
					throw new TeeOperationException("单位人数[" + personCount
							+ "]超出授权人数范围[" + personLimit + "]，无法添加新的成员！");
				}
				if (personDao.checkUserExist(0, personModul.getUserId())) {
					json.setRtMsg("用户已存在！");
					json.setRtState(false);
					return json;
				}
				BeanUtils.copyProperties(personModul, person);

				String password = "";
				if (!TeeUtility.isNullorEmpty(personModul.getPassword())) {
					password = personModul.getPassword();
				}
				person.setPassword(TeePassEncryptMD5.cryptDynamic(password));
				person.setDeleteStatus("0");
				TeePersonDynamicInfo dynamicInfo = person.getDynamicInfo();
				if (dynamicInfo == null) {
					dynamicInfo = new TeePersonDynamicInfo();
					person.setDynamicInfo(dynamicInfo);
				}

				// person.setTheme(TeeConst.PERSON_DEFAULT_THEME);//设置默认界面主题
				//person.setDesktop(TeeConst.PERSON_DEFAULT_DESKTOP_NEW);
				person.setDesktop(personModul.getDesktop());// 新建人员personModul.getDesktop()
																		// 默认桌面模块
				TeeAttachment attachment = teeAttachmentDao.get(personModul.getAttachId());
				person.setAttach(attachment);
				person.setMenuParamSet(TeeConst.PERSON_DEFAULT_MENU_PARAM_SET);// 现代风格样式
				person.setDeviceId(personModul.getDeviceId());
				person.setViewPriv(personModul.getViewPriv());
				person.setViewDept(personModul.getViewDeptIds());
//				person.getLeaders().clear();
//				person.getUnderlings().clear();
				// 加入直属上级
				if (!TeeUtility.isNullorEmpty(personModul.getLeaderIds())) {
					String sp[] = personModul.getLeaderIds().split(",");
					for (String id : sp) {
						TeePerson tmp = new TeePerson();
						tmp.setUuid(Integer.parseInt(id));
						person.setLeader(tmp);
					}
				}
//				if (!TeeUtility.isNullorEmpty(personModul.getUnderlingIds())) {
//					String sp[] = personModul.getUnderlingIds().split(",");
//					for (String id : sp) {
//						TeePerson tmp = new TeePerson();
//						tmp.setUuid(Integer.parseInt(id));
//						person.getUnderlings().add(tmp);
//					}
//				}

				//判断是否开启三员管理
				TeeSysPara p=sysParaService.getSysPara("IS_OPEN_PART_THREE");
				if(p!=null){
					int isOpenPartThree=TeeStringUtil.getInteger(p.getParaValue(), 0);
					if(isOpenPartThree==1){//开启了三员管理
						TeeSysPara p1=sysParaService.getSysPara("PART_THREE_DEFAULT_PRIV");
						if(p1!=null){
							String menuGroupId=p1.getParaValue();
							List<TeeMenuGroup> menuGrouplist1 = menuGroupDao
									.getMenuGroupListByUuids(menuGroupId);
							person.setMenuGroups(menuGrouplist1);
						}
					}
				}
				
				
				personDao.addPerson(person);
				
				// 在人事档案中加入一条记录
				TeeHumanDoc humanDoc = new TeeHumanDoc();
				humanDoc.setPersonName(person.getUserName());
				humanDoc.setEmail(person.getEmail());
				humanDoc.setMobileNo(person.getMobilNo());
				humanDoc.setDept(person.getDept());
				humanDoc.setRole(person.getUserRole());
				humanDoc.setIsOaUser("true");
				if ("0".equals(person.getSex())) {
					humanDoc.setGender("男");
				} else {
					humanDoc.setGender("女");
				}
				humanDoc.setOaUser(person);
				simpleDaoSupport.save(humanDoc);

				json.setRtMsg("新增人员成功");

				sysLog.setType("003A");
				sysLog.setRemark("新建人员信息[" + person.getUserId() + " "
						+ person.getUserName() + "]");
				// WebSocketRunner.userCache.put(person.getUserId(),
				// person.getUuid());
				// WebSocketRunner.userReverseCache.put(person.getUuid(),
				// person.getUserId());

				//往OpenFire中推送人员数据
				TeeOpenfireUtil.createUser(person.getUserId(), person.getUserName());
				
				//同步外部系统 新增人员
				syncSystem(person, "0", request);
				
			} else {// 编辑
				TeePerson oldPerson = personDao.selectPersonById(personModul
						.getUuid());
				if (oldPerson == null) {
					json.setRtState(false);
					json.setRtMsg("没有相关人员！");
					return json;
				} else {

					sysLog.setType("003B");
					sysLog.setRemark("修改人员信息[" + oldPerson.getUserId() + " "
							+ oldPerson.getUserName() + "]");

					if (personDao.checkUserExist(personModul.getUuid(),
							personModul.getUserId())) {
						json.setRtMsg("用户已存在！");
						json.setRtState(false);
						return json;
					}
					oldPerson.setUserRoleOther(person.getUserRoleOther());
					oldPerson.setDeptIdOther(person.getDeptIdOther());
					oldPerson.setPostDept(person.getPostDept());
					oldPerson.setMenuGroups(person.getMenuGroups());
					oldPerson.setBirthday(person.getBirthday());
					oldPerson.setDeviceId(personModul.getDeviceId());
					oldPerson.setViewPriv(personModul.getViewPriv());
					oldPerson.setViewDept(personModul.getViewDeptIds());
					if (!TeeUtility.isNullorEmpty(personModul.getDeptId())) {
						oldPerson.setDept(deptDao.selectDeptById(Integer
								.parseInt(personModul.getDeptId())));// 部门
					}

					if (!TeeUtility.isNullorEmpty(personModul.getUserRoleStr())) {
						oldPerson.setUserRole(roleDao.selectByUUId(Integer
								.parseInt(personModul.getUserRoleStr())));
					}
					oldPerson.setIcq_no(personModul.getIcq_no());
					oldPerson.setAvatar(personModul.getAvatar());
					oldPerson.setByname(personModul.getByname());
					oldPerson.setAddHome(personModul.getAddHome());
					oldPerson.setBindIp(personModul.getBindIp());
					oldPerson.setBindMac(personModul.getBindMac());
					oldPerson.setBkground(personModul.getBkground());
					oldPerson.setCallSound(personModul.getCallSound());
					oldPerson.setDutyType(personModul.getDutyType());
					oldPerson.setSignWay(personModul.getSignWay());//修改签到方式
					oldPerson.setEmail(personModul.getEmail());
					oldPerson.setEmailCapacity(personModul.getEmailCapacity());
					oldPerson.setFaxNoDept(personModul.getFaxNoDept());
					oldPerson
							.setFolderCapacity(personModul.getFolderCapacity());
					oldPerson.setCertUniqueId(personModul.getCertUniqueId());
					//oldPerson.setKeySN(personModul.getKeySN());
					// oldPerson.setLastPassTime(personModul.getLastPassTime());
					// oldPerson.setLastVisitIp(personModul.getLastVisitIp());
					// person.setLastVisitTime(personModul.getLastVisitTime());
					oldPerson.setMenuExpand(personModul.getMenuExpand());
					// person.setMenuGroups(menuGroups)
					oldPerson.setWeixinNo(personModul.getWeixinNo());
					oldPerson.setMenuImage(personModul.getMenuImage());
					oldPerson.setMenuType(personModul.getMenuType());
					oldPerson.setMobilNo(personModul.getMobilNo());
					oldPerson.setMobilNoHidden(personModul.getMobilNoHidden());
					oldPerson.setMSN(personModul.getMSN());
					oldPerson.setMyStatus(personModul.getMyStatus());
					oldPerson.setNickName(personModul.getNickName());
					oldPerson.setNotLogin(personModul.getNotLogin());
					oldPerson.setNotWebLogin(personModul.getNotWebLogin());
					oldPerson.setNotMobileLogin(personModul.getNotMobileLogin());
					oldPerson.setNotPcLogin(personModul.getNotPcLogin());
					oldPerson.setNotViewUser(personModul.getNotViewUser());
					oldPerson.setOicqNo(personModul.getOicqNo());
					// oldPerson.setOnline(personModul.getOnline());
					oldPerson.setOnStatus(personModul.getOnStatus());
					oldPerson.setPanel(personModul.getPanel());
					// person.setPassword(personModul.getPassword());
					oldPerson.setPostNoHome(personModul.getPostNoHome());
					person.setPostPriv(personModul.getPostPriv());
					oldPerson.setRemark(personModul.getRemark());
					oldPerson.setSecureKeySn(personModul.getSecureKeySn());
					oldPerson.setSex(personModul.getSex());
					oldPerson.setShortcut(personModul.getShortcut());
					oldPerson.setShowRss(personModul.getShowRss());
					oldPerson.setSmsOn(personModul.getSmsOn());
					oldPerson.setTelNoDept(personModul.getTelNoDept());
					oldPerson.setTelNoHome(personModul.getTelNoHome());
					oldPerson.setTheme(personModul.getTheme());
					oldPerson.setUseingKey(personModul.getUseingKey());
					oldPerson.setUserId(personModul.getUserId());
					oldPerson.setUserName(personModul.getUserName());
					oldPerson.setPerformCode(personModul.getPerformCode());
					oldPerson.setUserNo(personModul.getUserNo());
					oldPerson.setPostPriv(personModul.getPostPriv());
					oldPerson.setWeatherCity(personModul.getWeatherCity());
					oldPerson.setWebmailCapacity(personModul
							.getWebmailCapacity());
					oldPerson.setWebmailNum(personModul.getWebmailNum());
					oldPerson.setNotSearch(personModul.getNotSearch());
					oldPerson.setNotViewTable(personModul.getNotViewTable());
					oldPerson.setWebmailCapacity(personModul
							.getWebmailCapacity());
					oldPerson.setWebmailNum(personModul.getWebmailNum());
					oldPerson.setDesktop(personModul.getDesktop());

					oldPerson.setLeader(null);
//					oldPerson.getUnderlings().clear();
					// 加入直属上级和直属下级
					if (!TeeUtility.isNullorEmpty(personModul.getLeaderIds())) {
						String sp[] = personModul.getLeaderIds().split(",");
						for (String id : sp) {
							TeePerson tmp = new TeePerson();
							tmp.setUuid(Integer.parseInt(id));
							oldPerson.setLeader(tmp);
//							oldPerson.getLeaders().add(tmp);
						}
					}
//					if (!TeeUtility
//							.isNullorEmpty(personModul.getUnderlingIds())) {
//						String sp[] = personModul.getUnderlingIds().split(",");
//						for (String id : sp) {
//							TeePerson tmp = new TeePerson();
//							tmp.setUuid(Integer.parseInt(id));
//							oldPerson.getUnderlings().add(tmp);
//						}
//					}
					TeeAttachment attachment = teeAttachmentDao.get(personModul.getAttachId());
					oldPerson.setAttach(attachment);
					personDao.update(oldPerson);
					
					// 查看是否存在相关的人事档案
					TeeHumanDoc humanDoc = (TeeHumanDoc) simpleDaoSupport
							.unique("from TeeHumanDoc where oaUser.uuid=?",
									new Object[] { oldPerson.getUuid() });
					if (humanDoc != null) {
						humanDoc.setPersonName(oldPerson.getUserName());
						humanDoc.setEmail(oldPerson.getEmail());
						humanDoc.setMobileNo(oldPerson.getMobilNo());
						humanDoc.setDept(oldPerson.getDept());
						humanDoc.setRole(oldPerson.getUserRole());
						humanDoc.setIsOaUser("true");
						humanDoc.setOaUser(oldPerson);
						if (oldPerson.getSex().equals("0")) {
							humanDoc.setGender("男");
						} else {
							humanDoc.setGender("女");
						}
						simpleDaoSupport.update(humanDoc);
					} else {
						// 在人事档案中加入一条记录
						TeeHumanDoc humanDoc0 = new TeeHumanDoc();
						humanDoc0.setPersonName(oldPerson.getUserName());
						humanDoc0.setEmail(oldPerson.getEmail());
						humanDoc0.setMobileNo(oldPerson.getMobilNo());
						humanDoc0.setDept(oldPerson.getDept());
						humanDoc0.setRole(oldPerson.getUserRole());
						humanDoc0.setIsOaUser("true");
						humanDoc0.setOaUser(oldPerson);
						if (oldPerson.getSex().equals("0")) {
							humanDoc0.setGender("男");
						} else {
							humanDoc0.setGender("女");
						}
						simpleDaoSupport.save(humanDoc0);
					}

					json.setRtMsg("修改人员成功");
					
					//往OpenFire中推送人员数据
					TeeOpenfireUtil.createUser(oldPerson.getUserId(), oldPerson.getUserName());
				
					//同步外部系统  修改人员
					syncSystem(oldPerson, "1", request);
				}

			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.setRtState(false);
			json.setRtMsg(e.getMessage());
		}
		json.setRtState(true);

		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		return json;
	}

	public void updatePerson(TeePerson person) {
		personDao.update(person);
	}

	/**
	 * 个人更新人员密码
	 * 
	 * @param person
	 * @throws NoSuchAlgorithmException
	 */
	@TeeLoggingAnt(template = "修改密码: {#.rtMsg}", type = "003G")
	public TeeJson updatePersonPasswordService(HttpServletRequest request)
			throws NoSuchAlgorithmException {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		person = personDao.selectPersonById(person.getUuid());
		String oldpassword = request.getParameter("oldPassword") == null ? ""
				: request.getParameter("oldPassword");
		if (!TeePassEncryptMD5.checkCryptDynamic(oldpassword, person
				.getPassword().trim())) {// 密码不对
			json.setRtState(false);
			json.setRtMsg("原密码不正确！");
			return json;
		}
		String newPassword = request.getParameter("newPassword") == null ? ""
				: request.getParameter("newPassword");
		try {
			String md5Str = TeePassEncryptMD5.cryptDynamic(newPassword);
			person.setPassword(md5Str);
			person.setLastPassTime(new Date());
			// personService.updatePerson(person);
			personDao.update(person);
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg("加密失败！");
			return json;
		}
		TeeRequestInfoContext.getRequestInfo().setUserId(person.getUuid() + "");
		TeeRequestInfoContext.getRequestInfo()
				.setUserName(person.getUserName());
		json.setRtState(true);
		json.setRtMsg("密码修改成功");
		return json;
	}

	/**
	 * 更新高速波账号和密码
	 * 
	 * @param person
	 * @throws NoSuchAlgorithmException
	 */
	@TeeLoggingAnt(template = "修改云办公平台账号和密码: {#.rtMsg}", type = "003G")
	public TeeJson updateGsbUserIdAndPassword(HttpServletRequest request)
			throws NoSuchAlgorithmException {
		TeeJson json = new TeeJson();
		String gsbUserId = request.getParameter("cloudUserId");
		String gsbPwd = request.getParameter("cloudPwd");
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		person = personDao.selectPersonById(person.getUuid());
		Map map = new HashedMap();
		if (personDao.checkGsbUserExist(person.getUuid(), gsbUserId)) {
			json.setRtState(false);
			json.setRtMsg("【" + gsbUserId + "】账号已存在！");
			return json;
		}
		try {
			String md5Str = TeePassEncryptMD5.crypt(gsbPwd);
			person.setGsbPassword(md5Str);
			person.setGsbUserId(gsbUserId);
			personDao.update(person);
			map.put("accountId", gsbUserId);
			map.put("accountPwd", md5Str);
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg("加密失败！");
			return json;
		}
		json.setRtState(true);
		json.setRtData(map);
		json.setRtMsg("账号和密码修改成功!");
		return json;
	}

	/**
	 * 查询 byUuid
	 * 
	 * @param TeePerson
	 */
	public TeePerson selectByUuid(String uuid) {

		TeePerson dept = (TeePerson) personDao.selectPersonById(Integer
				.parseInt(uuid));
		return dept;
	}

	/**
	 * 查询 byUuid
	 * 
	 * @param TeePerson
	 */
	public TeePerson selectByUuid(int uuid) {

		TeePerson dept = (TeePerson) personDao.selectPersonById(uuid);
		return dept;
	}

	/**
	 * 查询 byuserId
	 * 
	 * @param TeePerson
	 */
	public TeePerson getPersonByUserId(String userId) {
		TeePerson dept = personDao.getPersonByUserId(userId);
		return dept;
	}
	
	public TeePerson getPersonByDingUserId(String userId) {
		TeePerson p = (TeePerson) simpleDaoSupport.unique("from TeePerson where gsbUserId=?", new Object[]{userId});
		return p;
	}
	
	public TeePerson getPersonByWXUserId(String userId) {
		TeePerson p = (TeePerson) simpleDaoSupport.unique("from TeePerson where gsbPassword=?", new Object[]{userId});
		return p;
	}

	/**
	 * 组织机构树 --人员---不考虑权限
	 * 
	 * @param deptId
	 *            : 部门Id
	 * @param person
	 *            :系统当前登录人
	 */
	public TeeJson selectOrgTreeAll(String deptId, TeePerson LoginPerson) {
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> orgDeptList = new ArrayList<TeeZTreeModel>();
		try {
			if (!TeeUtility.isNullorEmpty(deptId)) {
				List<TeeDepartment> deptList = deptService.getDeptsByLoginUser(LoginPerson);
				Map deptPrivMap = new HashMap();
				for(TeeDepartment dept:deptList){
					deptPrivMap.put(dept.getUuid(), "");
				}
				
				String[] idArray = deptId.split(";");
				if (idArray.length >= 2) {
					Object[] values = { Integer.parseInt(idArray[0]) };
					if (idArray[1].equals("dept")) {// 部门
						List<TeeDepartment> list = deptDao
								.selectDept(
										"select new TeeDepartment(uuid,deptName,deptFullId,deptParent) from TeeDepartment d where d.deptParent.uuid = ? order by d.deptNo asc",
										values);
						for (int i = 0; i < list.size(); i++) {
							TeeDepartment dept = list.get(i);
							if(!deptPrivMap.containsKey(dept.getUuid())){
								continue;
							}
							boolean isParent = false;
							long pl = personDao
									.selectPersonCountByDeptIdandOtherDept(dept
											.getUuid());
							if (deptDao.checkExists(dept) || pl > 0) {// 如果存在下级获取用户
								isParent = true;
							}
							TeeZTreeModel ztree = new TeeZTreeModel(
									dept.getUuid() + ";dept",
									dept.getDeptName(), isParent, deptId, true,
									"deptNode", "");

							orgDeptList.add(ztree);

						}
						List<TeePerson> personList = personDao
								.selectPersonByDeptIdandOtherDept(TeeStringUtil
										.getInteger(idArray[0], 0));
						// 处理重复人员
						List distictPerson = new ArrayList();
						for (int j = 0; j < personList.size(); j++) {
							TeePerson person = personList.get(j);
							if (distictPerson.contains(person.getUuid())) {
								continue;
							}
							distictPerson.add(person.getUuid());
							TeeZTreeModel ztreePerson = new TeeZTreeModel(
									person.getUuid() + ";personId",
									person.getUserName(), false, deptId, true,
									"person_online_node",
									getPersonZtreeTitle(person), true,
									person.getUserId(), "");
							ztreePerson.getParams().put("userId", person.getUserId());
							ztreePerson.getParams().put("ico", person.getAvatar());
							orgDeptList.add(ztreePerson);
						}
					} else if (idArray[1].equals("person")) {// 人员不处理

					}

				} else {// 上级是单位获取所有是第一级的部门
					List<TeeDepartment> list = deptDao.getFisrtDeptSimple();
					for (int i = 0; i < list.size(); i++) {
						TeeDepartment dept = list.get(i);
						if(!deptPrivMap.containsKey(dept.getUuid())){
							continue;
						}
						boolean isParent = false;
						long pl = personDao
								.selectPersonCountByDeptIdandOtherDept(dept
										.getUuid());
						if (deptDao.checkExists(dept) || pl > 0) {// 如果存在下级部门
							isParent = true;
						}
						TeeZTreeModel ztree = new TeeZTreeModel(dept.getUuid()
								+ ";dept", dept.getDeptName(), isParent,
								deptId, true, "deptNode", "");
						orgDeptList.add(ztree);
					}
					
					//如果取出来的集合为空，则取deptList可见范围中的一级数据
					if(orgDeptList.size()==0){
						int minLength = 10000000;
						for(TeeDepartment dept:deptList){
							if(TeeStringUtil.getString(dept.getDeptParentLevel()).length()<minLength){
								minLength = TeeStringUtil.getString(dept.getDeptParentLevel()).length();
							}
						}
						
						for(TeeDepartment dept:deptList){
							if(TeeStringUtil.getString(dept.getDeptParentLevel()).length()==minLength){
								boolean isParent = false;
								long pl = personDao
										.selectPersonCountByDeptIdandOtherDept(dept
												.getUuid());
								if (deptDao.checkExists(dept) || pl > 0) {// 如果存在下级部门
									isParent = true;
								}
								
								TeeZTreeModel ztree = new TeeZTreeModel(dept.getUuid()
										+ ";dept", dept.getDeptName(), isParent,
										deptId, true, dept.getDeptType()==1?"deptNode":"pIconHome", "");
								orgDeptList.add(ztree);
							}
						}
					}
					
				}
			} else {// 获取单位
				List<TeeOrganization> list = orgDao.traversalOrg();
				if (list.size() > 0) {
					TeeOrganization org = list.get(0);
					TeeZTreeModel ztree = new TeeZTreeModel(org.getSid() + "",
							org.getUnitName(), true, "0", true, "pIconHome", "");
					orgDeptList.add(ztree);
				}
			}
			json.setRtMsg("获取成功");
			json.setRtData(orgDeptList);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			json.setRtMsg(e.getMessage());
			json.setRtState(false);
		}

		return json;
	}
	
	/**
	 * 同步外部系统
	 * @param dept
	 * @throws UnsupportedEncodingException
	 */
	private void syncSystem(TeePerson person,String operation,HttpServletRequest request)
			throws UnsupportedEncodingException {
			List<TeeOutSystemConfig> configList = simpleDaoSupport.executeQuery("from TeeOutSystemConfig", null);
			if (configList != null && configList.size() > 0) {
				for (TeeOutSystemConfig config : configList) {
					//拼接json
					StringBuilder sb  = new StringBuilder();
					Map<String,String> map=new HashMap<String,String>();
					
					sb.append("{\"userName\":\""+person.getUserName()+"\"");
					
					sb.append(",\"userId\":\""+person.getUserId()+"\"");
					
					sb.append(",\"uuid\":\""+person.getUuid()+"\"");
					
					if(person.getDept()!=null){
						sb.append(",\"deptId\":"+person.getDept().getUuid()+"");
					}else{
						sb.append(",\"deptId\":\"\"");
					}
					
					if(person.getUserRole()!=null){
						sb.append(",\"roleId\":"+person.getUserRole().getUuid()+"");
					}else{
						sb.append(",\"roleId\":\"\"");
					}
					sb.append(",\"email\":\""+person.getEmail()+"\"");
					
					sb.append(",\"phone\":\""+person.getMobilNo()+"\"");
					
					sb.append(",\"operation\":\""+operation+"\"}");
					
					String param = sb.toString();
					map.put("json", param);
					//String respJson = HttpClientUtil.requestGet(config.getSystemUrl()+"?json="+URLEncoder.encode(param,"UTF-8"));
					String respJson=HttpClientUtil.requestPost(map, config.getSystemUrl());
					if (!TeeUtility.isNullorEmpty(respJson)) {
						System.out.println(JSONObject.fromObject(respJson).get("status"));
						boolean status = (boolean) JSONObject.fromObject(respJson).get("status");
						if (!status) {
							addLog(operation, request, config, param,person);
						}
					}
			}
		}
	}

	/**
	 * 同步失败 记录日志
	 * @param operation
	 * @param request
	 * @param config
	 * @param parm
	 */
	private void addLog(String operation, HttpServletRequest request,
			TeeOutSystemConfig config, String param,TeePerson person) {
		TeeOutSystemSyncLog log = new TeeOutSystemSyncLog();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		log.setRequestJson(param);
		if (person != null) {
			log.setUuid(person.getUuid()+"");
		}
		//设置为未同步状态
		log.setSyncFlag("0");
		log.setOperation(operation);
		log.setConfigId(config.getSid());
		log.setSubmitUserId(loginUser.getUuid());
		log.setSubmitUserName(loginUser.getUserName());
		Calendar crTime = Calendar.getInstance();
		crTime.setTime(new Date());
		log.setCrTime(crTime);
		logDao.addLog(log);
	}
	
	/**
	 * 组织机构树 --人员---考虑权限范围
	 * 
	 * @param deptId
	 *            : 部门Id
	 * @param person
	 *            :系统当前登录人
	 */
	@Transactional(readOnly = true)
	public TeeJson selectOrgTree(String deptId, TeePerson LoginPerson ,String isAdmin) {
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> orgDeptList = new ArrayList<TeeZTreeModel>();
		try {
			if (!TeeUtility.isNullorEmpty(deptId)) {
				String postDeptIds = getLoginPersonPostDept(LoginPerson);// 获取系统当前登录人管理范围部门Id字符串
				List<TeeDepartment> deptList = deptService.getDeptsByLoginUser(LoginPerson);
				Map deptMap=new HashMap();
				for (TeeDepartment teeDepartment : deptList) {
					deptMap.put(teeDepartment.getUuid(), "");
				}
				
				String[] idArray = deptId.split(";");
				if (idArray.length >= 2) {
					Object[] values = { Integer.parseInt(idArray[0]) };
					if (idArray[1].equals("dept")) {// 部门
						List<TeeDepartment> list = deptDao
								.selectDept(
										"from TeeDepartment  where deptParent.uuid = ?  order by deptNo ",
					             values);
						TeeDepartment dept=null;
						TeeZTreeModel ztree = null;
						for (int i = 0; i < list.size(); i++) {
							dept = list.get(i);
							if(!deptMap.containsKey(dept.getUuid())){
							     continue;
						    }
							boolean isParent = false;
							long count = personDao
									.selectPersonCountByDeptIdandOtherDept(dept
											.getUuid());
							if (deptDao.checkExists(dept) || count > 0) {// 如果存在下级获取用户
								isParent = true;
							}
							
							if (dept.getDeptType() == 2) {
								ztree = new TeeZTreeModel(dept.getUuid()
										+ ";dept", dept.getDeptName(),
										isParent, deptId, true, "pIconHome", "");
							} else {
								ztree = new TeeZTreeModel(dept.getUuid()
										+ ";dept", dept.getDeptName(),
										isParent, deptId, true, "deptNode", "");
							}
							orgDeptList.add(ztree);

						}
						List<TeePerson> personList = new ArrayList<TeePerson>();
						String NOT_VIEW_USER = TeeStringUtil.getString(
								LoginPerson.getNotViewUser(), "0");// 是否禁止查看用户列表
																	// 1-禁止
						if (!NOT_VIEW_USER.equals("1")) {
							personList = personDao
									.selectPersonByDeptIdAndOtherDeptAndPostDept(
											Integer.parseInt(idArray[0]),
											checkIsSuperAdmin(LoginPerson, ""),
											postDeptIds, 
											LoginPerson.getUserRole().getRoleNo(), 
											isAdmin);// 展示所有人员，包括辅助部门
						}
						// 处理重复人员
						List distictPerson = new ArrayList();
						for (int j = 0; j < personList.size(); j++) {
							TeePerson person = personList.get(j);
							if (distictPerson.contains(person.getUuid())) {
								continue;
							}
							distictPerson.add(person.getUuid());
							TeeZTreeModel ztreePerson = new TeeZTreeModel(
									person.getUuid() + ";personId",
									person.getUserName(), false, deptId, true,
									"person_online_node",
									getPersonZtreeTitle(person));
							orgDeptList.add(ztreePerson);
						}
					} else if (idArray[1].equals("person")) {// 人员不处理

					}

				} else {//获取第一级部门
					
					/*//获取有权限的顶级部门
					List<String>  deptIdSetPriv=new  ArrayList<String>();
					for (TeeDepartment dept : deptList) {
						String deptFullId=dept.getDeptFullId();
						deptFullId=deptFullId.substring(1,deptFullId.length());
						String[] ids = deptFullId.split("/");
						
						//顶级的部门主键
						for (String s : ids) {
							if (!deptIdSetPriv.contains(s)) {
								deptIdSetPriv.add(s);
							}
						}
						
					}
					
					for (String str : deptIdSetPriv) {
						int dId=TeeStringUtil.getInteger(str,0);
						TeeDepartment dept =(TeeDepartment) simpleDaoSupport.get(TeeDepartment.class,dId);
						boolean isParent = false;
						long count = personDao
								.selectPersonCountByDeptIdandOtherDept(dept
										.getUuid());
						if (deptDao.checkExists(dept) || count > 0) {// 如果存在下级部门
							isParent = true;
						}
						TeeZTreeModel ztree = null;
						if (dept.getDeptType() == 2) {
							ztree = new TeeZTreeModel(dept.getUuid() + ";dept",
									dept.getDeptName(), isParent, deptId, true,
									"pIconHome", "");
						} else {
							ztree = new TeeZTreeModel(dept.getUuid() + ";dept",
									dept.getDeptName(), isParent, deptId, true,
									"deptNode", "");
						}
						orgDeptList.add(ztree);
					}
					*/
					List<TeeDepartment> list = deptDao.getFisrtDept();
					TeeDepartment dept = null;
					TeeZTreeModel ztree = null;
					for (int i = 0; i < list.size(); i++) {
						dept = list.get(i);
						/*if(!deptList.contains(dept)){
							continue;
						}*/
						if(!deptMap.containsKey(dept.getUuid())){
						     continue;
					    }
						boolean isParent = false;
						long count = personDao
								.selectPersonCountByDeptIdandOtherDept(dept
										.getUuid());
						if (deptDao.checkExists(dept) || count > 0) {// 如果存在下级部门
							isParent = true;
						}
						
						if (dept.getDeptType() == 2) {
							ztree = new TeeZTreeModel(dept.getUuid() + ";dept",
									dept.getDeptName(), isParent, deptId, true,
									"pIconHome", "");
						} else {
							ztree = new TeeZTreeModel(dept.getUuid() + ";dept",
									dept.getDeptName(), isParent, deptId, true,
									"deptNode", "");
						}
						orgDeptList.add(ztree);
					}
					/**
					 * 获取没有部门的人员
					 */
					List<TeePerson> pl = personDao.selectNullDeptPerson();
					for (int i = 0; i < pl.size(); i++) {
						TeePerson person = pl.get(i);
						TeeZTreeModel ztreePerson = new TeeZTreeModel(
								person.getUuid() + ";personId",
								person.getUserName(), false, deptId, true,
								"person_online_node",
								getPersonZtreeTitle(person));
						orgDeptList.add(ztreePerson);
					}
				}

			} else {// 获取单位
				List<TeeOrganization> list = orgDao.traversalOrg();
				if (list.size() > 0) {
					TeeOrganization org = list.get(0);
					TeeZTreeModel ztree = new TeeZTreeModel(org.getSid() + "",
							org.getUnitName(), true, "0", true, "pIconHome", "");
					orgDeptList.add(ztree);
				}
			}
			json.setRtMsg("获取成功");
			json.setRtData(orgDeptList);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.setRtMsg(e.getMessage());
			json.setRtState(false);
		}

		return json;
	}

	/**
	 * 获取人员部门信息作为组织机构数title
	 * 
	 * @param person
	 * @return
	 */
	public static String getPersonZtreeTitle(TeePerson person) {

		String deptName = "";
		String roleName = "";
		String telNoDept = "";// 工作电话
		String email = "";// 邮件
		String QQ = "";// QQ
		String myStatus = "";// 我的状态---心情好
		if (person.getDept() != null
				&& !TeeUtility.isNullorEmpty(person.getDept().getDeptName())) {
			deptName = person.getDept().getDeptName();
		}
		if (person.getUserRole() != null
				&& !TeeUtility
						.isNullorEmpty(person.getUserRole().getRoleName())) {
			roleName = person.getUserRole().getRoleName();
		}

		if (!TeeUtility.isNullorEmpty(person.getTelNoDept())) {
			telNoDept = person.getTelNoDept();
		}
		if (!TeeUtility.isNullorEmpty(person.getEmail())) {
			email = person.getEmail();
		}
		if (!TeeUtility.isNullorEmpty(person.getOicqNo())) {
			QQ = person.getOicqNo();
		}
		if (!TeeUtility.isNullorEmpty(person.getMyStatus())) {
			myStatus = person.getMyStatus();
		}
		return "部门:" + deptName + "\n角色:" + roleName + "\n工作电话:" + telNoDept
				+ "\n邮件:" + email + "\nQQ:" + QQ + "\n心情:" + myStatus;

	}

	/**
	 * 校验用户名是否存在
	 * 
	 * @param uuid
	 *            ：uuid
	 * @param userId
	 *            ： 用户名
	 * @return
	 */
	public boolean checkUserExist(String uuid, String userId) {
		if (TeeUtility.isNullorEmpty(uuid)) {
			uuid = "0";
		}
		return personDao.checkUserExist(Integer.parseInt(uuid), userId);
	}

	/**
	 * 校验昵称是否存在
	 * 
	 * @param uuid
	 *            ：uuid
	 * @param userId
	 *            ： 用户名
	 * @return
	 */
	public boolean checkNickNameExist(int uuid, String nickName) {
		return personDao.checkNickNameExist(uuid, nickName);
	}

	/**
	 * 删除byUuid
	 * 
	 * @param TeeDepartment
	 */
	public void delByUuid(TeePerson dept) {
		if (TeePersonService.checkIsAdminPriv(dept)) {
			throw new TeeOperationException("不允许删除系统管理员账户");
		}
		personDao.delSysDept(dept);
	}

	/**
	 * 通用列表
	 * 
	 * @param dm
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();

		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		boolean isSuperAdmin = checkIsSuperAdmin(loginPerson, "");
		int deptIdUuid = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
		String isAdmin = "0";
		if (!TeeUtility.isNullorEmpty(request.getParameter("isAdmin"))) {
			isAdmin = request.getParameter("isAdmin");
		}
		String NOT_VIEW_USER = TeeStringUtil.getString(
				loginPerson.getNotViewUser(), "0");// 是否禁止查看用户列表 1-禁止

		String postDeptIds = getLoginPersonPostDept(loginPerson);// 获取系统当前登录人管理范围部门Id字符串
		List<TeePerson> roles = new ArrayList<TeePerson>();

		// 判断无部门人员的情况，如果是系统管理员，则显示无部门的人员
		if (deptIdUuid == 0 && TeePersonService.checkIsAdminPriv(loginPerson)) {
			roles = simpleDaoSupport.find("from TeePerson where dept is null and deleteStatus <> '1' and isAdmin = ? ",
					new Object[] {isAdmin});
		} else if (deptIdUuid == 0) {
			return j;
		} else {
			if (!NOT_VIEW_USER.equals("1")) {//
				j.setTotal(personDao.countByDeptId(deptIdUuid + "",
						isSuperAdmin, postDeptIds, getMinRoleNoByLoginPerson(loginPerson),isAdmin));// 设置总记录数
			} else {
				j.setTotal(0l);
			}
			int firstIndex = 0;
			firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
			Object parm[] = {};

			if (!NOT_VIEW_USER.equals("1")) {//
				roles = personDao.getPersonPageFind(firstIndex, dm.getRows(),
						dm, deptIdUuid, isSuperAdmin, postDeptIds,getMinRoleNoByLoginPerson(loginPerson),isAdmin
						);// 查询
			}
		}

		List<TeePersonModel> rolemodel = new ArrayList<TeePersonModel>();
		if (roles != null && roles.size() > 0) {
			for (TeePerson person : roles) {
				TeePersonModel um = new TeePersonModel();
				BeanUtils.copyProperties(person, um);
				if (person.getDept() != null) {
					um.setDeptIdName(person.getDept().getDeptName());
				}
				if (person.getUserRole() != null) {
					um.setUserRoleStrName(person.getUserRole().getRoleName());
					um.setUserRoleStr(person.getUserRole().getUuid() + "");
				}
				String password = person.getPassword();
				um.setPasswordIsNUll("0");
				if (!TeeUtility.isNullorEmpty(password)) {
					if (TeePassEncryptMD5.checkCryptDynamic("", password)) {
						um.setPasswordIsNUll("1");
					}
				}
				// role.setSysMenus(null);
				rolemodel.add(um);
			}
		}
		j.setRows(rolemodel);// 设置返回的行
		return j;
	}

	/**
	 * 查询 by deptId 部门Id 不包含下级部门人员以及辅助部门人员
	 * 
	 * @param TeePerson
	 */
	public List<TeePerson> selectPersonByDeptId(String deptId) {
		return personDao.selectPersonByDeptId(deptId);
	}

	/**
	 * 查询 by roleOd 不包含辅助角色人员
	 * 
	 * @param TeePerson
	 */
	public List<TeePerson> selectPersonByRoleId(String roleId) {
		return personDao.selectPersonByRoleId(Integer.parseInt(roleId));
	}

	/**
	 * 查询 by deptId 本部门以及所有下级部门的人员
	 * 
	 * @param TeePerson
	 */
	public List<TeePerson> selectPersonByDeptAndChildDept(String deptIdStr) {
		int deptId = TeeStringUtil.getInteger(deptIdStr, 0);
		TeeDepartment dept = deptDao.get(deptId);
		List<TeePerson> list = new ArrayList<TeePerson>();
		if (dept != null) {
			String deptLevel = dept.getDeptParentLevel();
			if (TeeUtility.isNullorEmpty(deptLevel)) {// 如果是第一级部门
				deptLevel = dept.getGuid();
			}
			personDao.selectDeptAndChildDeptPerson(deptId, deptLevel);// 级别
		}
		return list;
	}

	public List<TeePerson> selectPersonByDeptAndChildDept(String deptId,
			String deptLevel) {
		return personDao.selectDeptAndChildDeptPerson(Integer.parseInt(deptId),
				deptLevel);
	}

	/**
	 * 条件查询---用户人员查询---方法提出来，方便查询与导入
	 * 
	 * @param model
	 * @return
	 */
	public List<TeePerson> queryPerson(TeePersonModel model,
			TeePerson loginPerson) {
		boolean isSuperAdmin = checkIsSuperAdmin(loginPerson, "");
		String postDeptIds = getLoginPersonPostDept(loginPerson);// 获取系统当前登录人管理范围部门Id字符串
		List<TeePerson> listPerson = personDao.queryPerson(model, isSuperAdmin,
				postDeptIds, TeeStringUtil.getInteger(loginPerson.getUserRole().getRoleNo(), 0));
		return listPerson;
	}

	/**
	 * 条件查询---用户人员查询
	 * 
	 * @param model
	 * @return
	 */
	public TeeJson queryPerson(TeePersonModel model, TeeJson json,
			TeePerson loginPerson) {
		List<TeePerson> listPerson = queryPerson(model, loginPerson);// personDao.queryPerson(model,isSuperAdmin,postDeptIds,loginPerson.getUserRole().getRoleNo());
		List<TeePersonModel> modelList = new ArrayList<TeePersonModel>();
		for (int i = 0; i < listPerson.size(); i++) {
			TeePerson person = listPerson.get(i);
			TeePersonModel newModel = new TeePersonModel();
			BeanUtils.copyProperties(person, newModel);
			String password = person.getPassword();
			newModel.setPasswordIsNUll("0");
			if (!TeeUtility.isNullorEmpty(password)) {
				if (TeePassEncryptMD5.checkCryptDynamic("", password)) {
					newModel.setPasswordIsNUll("1");
				}

			}
			if (person.getDept() != null) {
				newModel.setDeptIdName(person.getDept().getDeptName());
			}
			if (person.getUserRole() != null) {
				newModel.setUserRoleStrName(person.getUserRole().getRoleName());
				newModel.setUserRoleStr(person.getUserRole().getUuid() + "");
			}
			modelList.add(newModel);
		}
		json.setRtData(modelList);
		return json;
	}

	/**
	 * 条件查询---用户人员导出
	 * 
	 * @param model
	 * @return
	 */
	public ArrayList<TeeDataRecord> exportToCsv(TeePersonModel model,
			TeePerson loginPerson) {
		ArrayList<TeeDataRecord> list = new ArrayList<TeeDataRecord>();
		List<TeePerson> listPerson = queryPerson(model, loginPerson);// 查询人员
		for (int i = 0; i < listPerson.size(); i++) {
			TeePerson person = listPerson.get(i);
			if(!person.getUserId().equals("admin")){
				TeeDataRecord dr = new TeeDataRecord();
				dr.addField("用户名", person.getUserId());
				String deptName = "";// 部门名称
				String roleName = "";// 角色名称
				if (person.getDept() != null) {
					deptName = person.getDept().getDeptFullName();
				}
				if (person.getUserRole() != null) {
					roleName = person.getUserRole().getRoleName();
				}
				dr.addField("部门名称", deptName);
				dr.addField("姓名", person.getUserName());
				String sexDesc = "男";
				if (TeeStringUtil.getString(person.getSex()).equals("1")) {
					sexDesc = "女";
				}
				dr.addField("性别", sexDesc);
				
				String birthdayDesc = "";
				if (person.getBirthday() != null) {
					birthdayDesc = TeeDateUtil.format(person.getBirthday(),
							"yyyy年MM月dd日");
				}
				dr.addField("生日", birthdayDesc);
				dr.addField("角色", roleName);
				dr.addField("别名", person.getByname());
				dr.addField("用户排序号", person.getUserNo());
				
				String postPrivDesc = "本部门";
				int postPriv = TeeStringUtil.getInteger(person.getPostPriv(), 0);
				if (postPriv == 0) {
					postPrivDesc = "本部门";
				} else if (postPriv == 1) {
					postPrivDesc = "全体";
				} else if (postPriv == 2) {
					postPrivDesc = "指定部门";
				} else if (postPriv == 3) {
					postPrivDesc = "本部门,以及下级所有部门";
				}
				dr.addField("管理范围", postPrivDesc);
				dr.addField("手机", TeeStringUtil.getString(person.getMobilNo()));
				dr.addField("IP", TeeStringUtil.getString(person.getBindIp()));
				dr.addField("工作电话", TeeStringUtil.getString(person.getTelNoDept()));
				dr.addField("工作传真", TeeStringUtil.getString(person.getFaxNoDept()));
				dr.addField("家庭地址", TeeStringUtil.getString(person.getAddHome()));
				dr.addField("邮编", TeeStringUtil.getString(person.getPostNoHome()));
				dr.addField("家庭电话", TeeStringUtil.getString(person.getTelNoHome()));
				dr.addField("E-mail", TeeStringUtil.getString(person.getEmail()));
				dr.addField("QQ", TeeStringUtil.getString(person.getIcq_no()));
				dr.addField("MSN", TeeStringUtil.getString(person.getMSN()));
				list.add(dr);
			}
		}
		return list;
	}

	/**
	 * 导入人员
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	public synchronized TeeJson importUser(HttpServletRequest request)
			throws Exception {
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		StringBuffer sb = new StringBuffer("[");
		String infoStr = "";
		int importSuccessCount = 0;
		try {
			MultipartFile file = multipartRequest.getFile("importUserFile");
			if (!file.isEmpty()) {
				// 获取真实文件名称
				String realName = file.getOriginalFilename();
				ArrayList<TeeDataRecord> dbL = TeeCSVUtil.CVSReader(
						file.getInputStream(), "GBK");

				List<TeePerson> addDeptList = new ArrayList<TeePerson>();

				// 从数据库中获取最大人数
				long personCount = simpleDaoSupport
						.count("select count(*) from TeePerson p where p.deleteStatus='0'",
								null);
				// 判断人数限制
				int personLimit = 0;

				try {
					personLimit = (Integer) TeeClassRunner.exec(
							"com.tianee.webframe.util.auth.TeeAuthUtil",
							"getPersonLimit", null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (int i = 0; i < dbL.size(); i++) {
					TeeDataRecord dr = dbL.get(i);
					TeePerson person = new TeePerson();
					String userId = TeeStringUtil.getString(dr
							.getValueByName("用户名"));
					String userName = TeeStringUtil.getString(dr
							.getValueByName("姓名"));
					String deptName = TeeStringUtil.getString(dr
							.getValueByName("部门名称"));
					String roleName = TeeStringUtil.getString(dr
							.getValueByName("角色"));
					String sexDesc = TeeStringUtil.getString(dr
							.getValueByName("性别"));

					String postPrivDesc = TeeStringUtil.getString(dr
							.getValueByName("管理范围"));
					int postPriv = 0;
					if (postPrivDesc.equals("本部门")) {
						postPriv = 0;
					} else if (postPrivDesc.equals("全体")) {
						postPriv = 1;
					} else if (postPrivDesc.equals("指定部门")) {
						postPriv = 2;
					} else if (postPrivDesc.equals("本部门,以及下级所有部门")) {
						postPriv = 3;
					}
					int userNo = TeeStringUtil.getInteger(
							dr.getValueByName("用户排序号"), 0);
					String sex = "0";
					if (sexDesc.equals("女")) {
						sex = "1";
					}
					String color = "red";
					TeePersonModel personModel = new TeePersonModel();
					if(!userId.equals("admin")){
					personModel.setUserId(userId);
					personModel.setUserName(userName);
					personModel.setDeptIdName(deptName);
					personModel.setUserRoleStrName(roleName);
					personModel.setUserNo(userNo);
					personModel.setPostDeptStrName(postPrivDesc);// 暂用辅助部门姓名代替
					if (personCount >= personLimit) {
						infoStr = "单位人数[" + personCount + "]超出授权人数范围["
								+ personLimit + "]，无法添加新的成员！！";
						getPersonInfoStr(sb, personModel, infoStr, color);
						continue;
					}

					if (TeeUtility.isNullorEmpty(userId)) {// 用户名为空则不建立
						infoStr = "导入失败,用户名不能为空！";
						getPersonInfoStr(sb, personModel, infoStr, color);
						continue;
					}
					if (TeeUtility.isNullorEmpty(deptName)) {// 部门为空则不建立
						infoStr = "导入失败,部门名称不能为空！";
						getPersonInfoStr(sb, personModel, infoStr, color);
						continue;
					}

					if (TeeUtility.isNullorEmpty(roleName)) {// 角色为空则不建立
						infoStr = "导入失败,角色名称不能为空！";
						getPersonInfoStr(sb, personModel, infoStr, color);
						continue;
					}
					TeeDepartment dept = deptDao
							.getParentDeptByFullName(deptName);
					if (dept == null) {// 判断部门是否存在
						infoStr = "导入失败,部门不存在！";
						getPersonInfoStr(sb, personModel, infoStr, color);
						continue;
					}
					TeeUserRole userRole = roleDao
							.getUserRoleByRoleName(roleName);
					if (userRole == null) {// 判断角色是否存在
						infoStr = "导入失败,角色不存在！";
						getPersonInfoStr(sb, personModel, infoStr, color);
						continue;
					}

					person.setUserId(userId);
					person.setUserName(userName);
					person.setDept(dept);
					person.setUserRole(userRole);

					person.setSex(sex);
					person.setPostPriv(postPriv);
					person.setUserNo(userNo);

					String birthdayDesc = TeeStringUtil.getString(dr
							.getValueByName("生日"));
					if (!birthdayDesc.equals("")) {
						Date date = TeeDateUtil
								.parseDateByPattern(birthdayDesc);
						if (date != null) {
							person.setBirthday(date);
						}
					}
					String byname = TeeStringUtil.getString(dr
							.getValueByName("别名"));
					person.setByname(byname);
					String mobilNo = TeeStringUtil.getString(dr
							.getValueByName("手机"));
					person.setMobilNo(mobilNo);
					String bindIp = TeeStringUtil.getString(dr
							.getValueByName("IP"));
					person.setBindIp(bindIp);
					String telNoDept = TeeStringUtil.getString(dr
							.getValueByName("工作电话"));
					person.setTelNoDept(telNoDept);
					String faxNoDept = TeeStringUtil.getString(dr
							.getValueByName("工作传真"));
					person.setFaxNoDept(faxNoDept);
					String addHome = TeeStringUtil.getString(dr
							.getValueByName("家庭地址"));
					person.setAddHome(addHome);
					String postNoHome = TeeStringUtil.getString(dr
							.getValueByName("邮编"));
					person.setPostNoHome(postNoHome);
					String telNoHome = TeeStringUtil.getString(dr
							.getValueByName("家庭电话"));
					person.setTelNoHome(telNoHome);
					String email = TeeStringUtil.getString(dr
							.getValueByName("E-mail"));
					person.setEmail(email);
					String qq = TeeStringUtil
							.getString(dr.getValueByName("QQ"));
					person.setIcq_no(qq);
					String MSN = TeeStringUtil.getString(dr
							.getValueByName("MSN"));
					person.setMSN(MSN);
					person.setDeleteStatus("0");// 设置不删除
					person.setPassword(TeePassEncryptMD5.cryptDynamic(""));// 密码为空;
					person.setTheme(TeeConst.PERSON_DEFAULT_THEME);// 设置默认界面主题
					TeePersonDynamicInfo dynamicInfo = person.getDynamicInfo();
					if (dynamicInfo == null) {
						dynamicInfo = new TeePersonDynamicInfo();
						person.setDynamicInfo(dynamicInfo);
					}
					// person.setDesktop(TeeConst.PERSON_DEFAULT_DESKTOP);//新建人员
					// 默认桌面模块
					person.setDesktop(TeeConst.PERSON_DEFAULT_DESKTOP_NEW);
					person.setMenuParamSet(TeeConst.PERSON_DEFAULT_MENU_PARAM_SET);// 现代风格样式

					if (personDao.checkUserExist(0, userId)) {// 存在更新
					// infoStr = "导入失败,用户名已存在！";
					// getPersonInfoStr(sb, personModel, infoStr, color);
					// continue;
						TeePerson p = personDao.getPersonByUserId(userId);
						person.setUuid(p.getUuid());
						BeanUtils.copyProperties(person, p);
						
						// 更新
						try{
							personDao.update(p);
							infoStr = "更新成功";
							color = "black";
							importSuccessCount++;
							getPersonInfoStr(sb, personModel, infoStr, color);
							
							
							//判断是不是存在相关联的人事档案
							TeeHumanDoc doc=null;
							List<TeeHumanDoc> docList=simpleDaoSupport.find(" from TeeHumanDoc where oaUser.uuid= ? ", new Object[]{p.getUuid()});
							if(docList!=null&&docList.size()>0){
								doc=docList.get(0);
								//更新数据
								doc.setPersonName(p.getUserName());
								doc.setEmail(p.getEmail());
								doc.setMobileNo(p.getMobilNo());
								doc.setDept(p.getDept());
								doc.setRole(p.getUserRole());
								doc.setIsOaUser("true");
								doc.setOaUser(p);
								if (p.getSex().equals("0")) {
									doc.setGender("男");
								} else {
									doc.setGender("女");
								}
								simpleDaoSupport.update(doc);
							}
							//往OpenFire中推送人员数据
							TeeOpenfireUtil.createUser(p.getUserId(), p.getUserName());
							
						}catch(Exception ex){
							infoStr = "更新失败，失败原因："+ex.getMessage();
							color = "red";
							getPersonInfoStr(sb, personModel, infoStr, color);
							continue;
						}
					} else {// 不存在 添加
						try{
							person.setNotLogin("0");
							personDao.addPerson(person);
							
							//往OpenFire中推送人员数据
							TeeOpenfireUtil.createUser(person.getUserId(), person.getUserName());
						}catch(Exception ex){
							infoStr = "添加失败，失败原因："+ex.getMessage();
							color = "red";
							getPersonInfoStr(sb, personModel, infoStr, color);
							continue;
						}
						

						// 在人事档案中加入一条记录
						TeeHumanDoc humanDoc0 = new TeeHumanDoc();
						humanDoc0.setPersonName(person.getUserName());
						humanDoc0.setEmail(person.getEmail());
						humanDoc0.setMobileNo(person.getMobilNo());
						humanDoc0.setDept(person.getDept());
						humanDoc0.setRole(person.getUserRole());
						humanDoc0.setIsOaUser("true");
						humanDoc0.setOaUser(person);
						if (person.getSex().equals("0")) {
							humanDoc0.setGender("男");
						} else {
							humanDoc0.setGender("女");
						}
						simpleDaoSupport.save(humanDoc0);

						personCount = personCount + 1;// 用户数量加1
						infoStr = "导入成功";
						color = "black";
						importSuccessCount++;
						getPersonInfoStr(sb, personModel, infoStr, color);
					}

				}
			  }
			} else {
				json.setRtState(false);
				json.setRtMsg("文件为空！");
				return json;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

		if (!sb.equals("[")) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		String data = sb.toString();
		List<Map<String, String>> list = TeeJsonUtil.JsonStr2MapList(data);
		json.setRtData(list);

		TeeSysLog sysLog = TeeSysLog.newInstance();
		sysLog.setType("003I");
		sysLog.setRemark("批量导入人员：" + data);
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		json.setRtState(true);
		json.setRtMsg(importSuccessCount + "");
		return json;
	}

	/**
	 * 导入人员返回导入详细信息
	 * 
	 * @author syl
	 * @date 2013-11-15
	 * @param sb
	 *            返回所有字符串信息
	 * @param model
	 * @param infoStr
	 *            说明
	 * @param color
	 *            颜色
	 * @return
	 */
	public StringBuffer getPersonInfoStr(StringBuffer sb, TeePersonModel model,
			String infoStr, String color) {
		sb.append("{");
		sb.append("deptName:\"" + model.getDeptIdName() + "\"");
		sb.append(",userId:\"" + model.getUserId() + "\"");
		sb.append(",userName:\"" + model.getUserName() + "\"");
		sb.append(",roleName:\"" + model.getUserRoleStrName() + "\"");
		sb.append(",userNo:\"" + model.getUserNo() + "\"");
		sb.append(",postPriv:\"" + model.getPostDeptStrName() + "\"");
		sb.append(",color:\"" + color + "\"");
		sb.append(",info:\"" + infoStr + "\"");
		sb.append("},");
		return sb;
	}

	/**
	 * 清空人员密码
	 * 
	 * @param
	 */
	public TeeJson clearPassword(String uuids) {
		TeeSysLog sysLog = TeeSysLog.newInstance();
		sysLog.setType("003D");
		TeeJson json = new TeeJson();
		int count = personDao.clearPassword(uuids);
		json.setRtData(count);
		sysLog.setRemark("清空密码[" + uuids + "]，共清空" + count + "个帐号的密码");
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		return json;
	}

	/**
	 * 修改人员密码 personUuids:人员UUId字符串 password:明文密码
	 * 
	 * @param
	 */
	public void updatePassword(String uuid, String password) {
		int count = personDao.updatePassword(uuid, password);

	}

	/**
	 * 更新删除状态
	 * 
	 * @param personUuids
	 * @param status
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public int updateDelPersonByUuids(String personUuids, String status,HttpServletRequest request) throws UnsupportedEncodingException {

		List<TeePerson> personList = personDao.getPersonByUuids(personUuids);
		String userNames = "";
		String userId = "";
		for (int i = 0; i < personList.size(); i++) {
			if (TeePersonService.checkIsAdminPriv(personList.get(i))) {
				continue;
			}
			userNames = userNames + personList.get(i).getUserName() + ",";
			userId = userId + personList.get(i).getUserId() + ",";
		}
		if(!"".equals(userId)){
			userId=userId.substring(0, userId.length()-1);
			userNames=userNames.substring(0, userNames.length()-1);
		}
		int count = personDao.updateDelPersonByUuids(personUuids, status);

		TeeSysLog sysLog = TeeSysLog.newInstance();
		sysLog.setType("003C");
		sysLog.setRemark("批量删除人员：【用户名：" + userId + ";姓名：" + userNames + "】");
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		syncDelSystem(personUuids,userId, "2", request);
		return count;
	}

	/**
	 * 同步离职人员
	 * @param personList
	 * @param string
	 * @param request
	 * @throws UnsupportedEncodingException 
	 */
	private void syncDelSystem(String uuids,String userId, String operation,
			HttpServletRequest request) throws UnsupportedEncodingException {
		List<TeeOutSystemConfig> configList = simpleDaoSupport.executeQuery("from TeeOutSystemConfig", null);
		if (configList != null && configList.size() > 0) {
			for (TeeOutSystemConfig config : configList) {
				Map<String,String> map=new HashMap<String,String>();
				//拼接json
				String param = "{\"uuids\":\""+uuids+"\",\"userId\":\""+userId+"\",\"operation\":\""+operation+"\"}";
				map.put("json", param);
				String respJson=HttpClientUtil.requestPost(map, config.getSystemUrl());
				//String respJson = HttpClientUtil.requestGet(config.getSystemUrl()+"?json="+URLEncoder.encode(param,"UTF-8"));
				if (!TeeUtility.isNullorEmpty(respJson)) {
					boolean status = (boolean) JSONObject.fromObject(respJson).get("status");
					if (!status) {
						//同步失败 记录日志
						addLog(operation, request, config, param,null);
					}
				}
			}
		}
	}

	/**
	 * 根据部门uuid串获取的所有人员
	 * 
	 * @param deptIds
	 *            : 部门uuid字符串
	 */
	public List<TeePerson> getPersonByDeptIds(String deptIds) {
		return personDao.getPersonByDeptIds(deptIds);
	}

	/**
	 * 批量设置
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@TeeLoggingAnt(template = "批量设置: {logModel.desc}", type = "003H")
	public int mulitSetPersonByTermService(HttpServletRequest request,
			TeePersonModel model) {
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 范围
		String deptStr = model.getDeptIdOtherStr();// 部门
		String roleStr = model.getUserRoleOtherId();// 角色
		String userStr = model.getUserId();// 人员
		TeeRequestInfoContext.getRequestInfo().setUserId(
				loginPerson.getUuid() + "");
		TeeRequestInfoContext.getRequestInfo().setUserName(
				loginPerson.getUserName());

		String desc = "";
		// 根据范围把相关人员全部查出来
		List<TeePerson> list1 = personDao.queryPersonByDeptOrRoleOrUuidAndOther(
				deptStr, roleStr, userStr);
		List<TeePerson> list=new ArrayList<TeePerson>();
		//批量设置   过滤到系统管理员
		if(list1.size()>0){
			for (TeePerson teePerson : list1) {
				if(!TeePersonService.checkIsSuperAdmin(teePerson, teePerson.getUserId())){
					list.add(teePerson)	;
				}
			}
		}
		
		
		// 查询主角色
		String userRoleStr = model.getUserRoleStr();
		TeeUserRole role = null;
		if (!TeeUtility.isNullorEmpty(userRoleStr)) {
			role = roleDao.selectByUUId(Integer.parseInt(userRoleStr));
		}

		// 查询主部门
		String deptIdStr = model.getDeptId();
		TeeDepartment dept = null;
		if (!TeeUtility.isNullorEmpty(deptIdStr)) {
			dept = deptDao.selectDeptByUuid(Integer.parseInt(deptIdStr));
		}

		// 查询菜单组LIst
		List<TeeMenuGroup> groupList = new ArrayList<TeeMenuGroup>();
		if (!TeeUtility.isNullorEmpty(model.getMenuGroupsStr())) {
			groupList = menuGroupDao.getMenuGroupListByUuids(model
					.getMenuGroupsStr());
		}
		// 循环更新人员
		int count = 0;
		String personDesc = "";
		Iterator<TeePerson> it = list.iterator();
		int desktopRefer = TeeStringUtil.getInteger(
				request.getParameter("desktopRefer"), 0);
		String autoPopSms = request.getParameter("autoPopSms");
		TeePerson referPerson = null;
		if (desktopRefer != 0) {
			referPerson = personDao.selectPersonById(desktopRefer);
		}
		while (it.hasNext()) {
			TeePerson person = it.next();
			// 如果为系统管理员，则跳过不进行设置
			if (TeePersonService.checkIsAdminPriv(person)) {
				continue;
			}
			personDesc = personDesc + person.getUserName() + ",";
			if (groupList.size() > 0) {// 菜单组
				person.setMenuGroups(groupList);
			}

			if (!TeeUtility.isNullorEmpty(model.getFolderCapacity())) {// 个人网盘
				person.setFolderCapacity(model.getFolderCapacity());
			}

			if (!TeeUtility.isNullorEmpty(model.getEmailCapacity())) {// 邮件数量
				person.setEmailCapacity(model.getEmailCapacity());
			}
			if (role != null) {// 主角色
				person.setUserRole(role);
			}

			if (model.getViewPriv() != 0) {// 可见范围

				person.setViewPriv(model.getViewPriv());

			}
			if (!TeeUtility.isNullorEmpty(model.getViewDeptIds())) {
				person.setViewDept(model.getViewDeptIds());

			}
			if (TeeStringUtil.getInteger(model.getPostPriv(), 0)!= -1) {// 管理范围

				person.setPostPriv(model.getPostPriv());

			}
			if (!TeeUtility.isNullorEmpty(model.getPostDeptStr())) {
				List<TeeDepartment> postDept=deptService.getDeptByUuids(model.getPostDeptStr());
				person.setPostDept(postDept);

			}
			
			if (dept != null) {// 主部门
				person.setDept(dept);
			}
			String pass1 = request.getParameter("pass1");// 密码
			if (!TeeUtility.isNullorEmpty(pass1)) {// 密码
				try {
					String md5Pass = TeePassEncryptMD5.cryptDynamic(pass1);
					person.setPassword(md5Pass);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (!TeeUtility.isNullorEmpty(model.getTheme())) {// 界面主题
				person.setTheme(model.getTheme());
			}
			if (!TeeUtility.isNullorEmpty(model.getSmsOn())) {// 短信提示方式
				person.setSmsOn(model.getSmsOn());
			}
			if (!TeeUtility.isNullorEmpty(autoPopSms)) {// 消息提醒窗口弹出方式
				person.setAutoPopSms(model.getAutoPopSms());
			}

			if (!TeeUtility.isNullorEmpty(model.getCallSound())) {// 短信提示音
				person.setCallSound(model.getCallSound());
			}

			if (!"0".equals(model.getDutyType())
					&& !TeeUtility.isNullorEmpty(model.getDutyType())) {// 考勤排版
				person.setDutyType(model.getDutyType());
			}
			if (!"0".equals(model.getSignWay())
					&& !TeeUtility.isNullorEmpty(model.getSignWay())) {// 考勤签到方式
				person.setSignWay(model.getSignWay());
			}

			// 设置桌面模块 和 现代风格桌面菜单
			if (!TeeUtility.isNullorEmpty(model.getDesktop())) {
				person.setDesktop(model.getDesktop());// 桌面模块
				// person.setMenuParamSet(referPerson.getMenuParamSet());//现代风格桌面菜单
			}

			// 辅助角色
			List<TeeUserRole> userRoleList = new ArrayList<TeeUserRole>();
			if (!TeeUtility.isNullorEmpty(model.getUserRoleOtherIds())) {
				String[] urIds = model.getUserRoleOtherIds().split(",");// 通过逗号拆分
				for (String fzurid : urIds) {// 遍历每一个ID
					TeeUserRole urole = new TeeUserRole();// 实例化一个参与人对象
					urole.setUuid(Integer.parseInt(fzurid));
					userRoleList.add(urole);
					person.setUserRoleOther(userRoleList);
				}

			}
			// 辅助部门
			List<TeeDepartment> dpList = new ArrayList<TeeDepartment>();
			if (!TeeUtility.isNullorEmpty(model.getDeptIdOtherStrs())) {
				String[] dpIds = model.getDeptIdOtherStrs().split(",");// 通过逗号拆分
				for (String fzdpid : dpIds) {// 遍历每一个ID
					TeeDepartment dp = new TeeDepartment(); // 实例化一个参与人对象
					dp.setUuid(Integer.parseInt(fzdpid));
					dpList.add(dp);
					person.setDeptIdOther(dpList);
				}

			}

			// 加入直属上级和直属下级
			if (!TeeUtility.isNullorEmpty(model.getLeaderIds())) {
//				person.getLeaders().clear();
				String sp[] = model.getLeaderIds().split(",");
				for (String id : sp) {
					TeePerson tmp = new TeePerson();
					tmp.setUuid(Integer.parseInt(id));
					person.setLeader(tmp);
//					person.getLeaders().add(tmp);
				}
			}
//			if (!TeeUtility.isNullorEmpty(model.getUnderlingIds())) {
//				person.getUnderlings().clear();
//				String sp[] = model.getUnderlingIds().split(",");
//				for (String id : sp) {
//					TeePerson tmp = new TeePerson();
//					tmp.setUuid(Integer.parseInt(id));
//					person.getUnderlings().add(tmp);
//				}
//			}
			if ("1".equals(model.getDeleteStatus())) {// 当等于1时，设置为离职
				person.setDeleteStatus("1");
			}

			if (++count % 20 == 0) { // 单次批量操作的数目为20
				// personDao.getSession().flush();
				personDao.getSession().flush();// 清理缓存，执行批量更新20条记录的SQL update语句
				// personDao.getSession().clear();//清空缓存中的Customer对象
			}
		}
		desc = desc + "[人员姓名: " + personDesc + "]";
		TeeRequestInfoContext.getRequestInfo().getLogModel().put("desc", desc);// 添加其他参数
		/*
		 * personDao.mutilUpdatePerson(request, model, rs, menuGroup ,role
		 * ,dept);
		 */
		return 0;
	}

	/**
	 * 判断人员是否为超级管理员
	 * 
	 * @param person
	 * @param userId
	 *            用户userId
	 * @return
	 */
	public static boolean checkIsSuperAdmin(TeePerson person, String userId) {
		if (person != null) {
			if (person.getUserId().equals("admin")) {// 用户userId为admin时为超级管理员
				return true;
			}
		}
		if (!TeeUtility.isNullorEmpty(userId) && userId.equals("admin")) {
			return true;
		}

		return false;
	}

	/**
	 * 判断人员角色是否为管理员角色
	 * 
	 * @param person
	 * @return
	 */
	public static boolean checkIsAdminPriv(TeePerson person) {
		// if(person == null){
		// return false;
		// }
		// if(person.getUserRole() == null){
		// return false;
		// }
		
		/*
		if ("admin".equals(person.getUserId())) {// 用户id等于admin
			return true;
		}
		*/
		
		String isAdmin = person.getIsAdmin();
		if("9".equals(isAdmin)) {
			return true;
		}		
		return false;
	}

	/**
	 * 根据登录人userName返回person
	 * 
	 * @param person
	 * @return
	 */

	public TeePerson getPersonByUserName(String userId) {

		TeePerson person = personDao.getPersonByUserId(userId);
		if(person != null){
	    	  // person.getMenuGroups().size();
  		   person.getPostDept().size();
  		   if(person.getDept() != null){
  			   person.getDept().getDeptName();
  		   }
  		   if(person.getUserRole()!=null){
  			   person.getUserRole().getRoleName();
  		   }
  		   person.getUserRoleOther().size();
  	   }
		return person;
	}

	/**
	 * 根据系统当前登录人获取管理范围(deptId字符串)
	 * 
	 * @param person
	 * @return
	 */
	public String getLoginPersonPostDept(TeePerson person) {
		String deptIds = "";
		if (checkIsSuperAdmin(person, "")) {
			return "0";
		}
		if(person.getPostPriv()!=null){
			if (person.getPostPriv() == 0) {// 本部门
				return person.getDept().getUuid() + "";
			} else if (person.getPostPriv() == 1) {// 所有
				return "0";
			} else if (person.getPostPriv() == 2) {// 指定部门
				List<TeeDepartment> deptList = person.getPostDept();
				for (int i = 0; i < deptList.size(); i++) {
					deptIds = deptIds + deptList.get(i).getUuid() + ",";
				}
			} else if (person.getPostPriv() == 3) {// 本部以及下级所有部门
				deptIds=person.getDept().getUuid()+",";
				String level = person.getDept().getUuid() + ",";
				if (!TeeUtility
						.isNullorEmpty(person.getDept().getDeptParentLevel())) {// 如果不是第一级部门
					level = person.getDept().getDeptParentLevel();
				} else {// 如果是第一级部门，则把uuID当做级别
					level = person.getDept().getGuid();
				}
				List<TeeDepartment> deptChildList = deptDao
						.getAllChildDeptByLevl(level);
				for (int i = 0; i < deptChildList.size(); i++) {
					deptIds = deptIds + deptChildList.get(i).getUuid() + ",";
				}
			}else if (person.getPostPriv() == 4) {// 本机构
				List<TeeDepartment> deptList = deptService.getDeptsByLoginUser(person);
				for (int i = 0; i < deptList.size(); i++) {
					deptIds = deptIds + deptList.get(i).getUuid() + ",";
				}
			}
		}
		
		if (deptIds.endsWith(",")) {
			deptIds = deptIds.substring(0, deptIds.length() - 1);
		}
		return deptIds;
	}

	/**
	 * 根据系统当前登录人获取管理范围 对象
	 * 
	 * @param person
	 * @return
	 */
	public List<TeeDepartment> getLoginPersonPostDeptBean(TeePerson person) {
		List<TeeDepartment> list = new ArrayList<TeeDepartment>();
		if (checkIsSuperAdmin(person, "")) {
			list = deptDao.getAllDept();
			return list;
		}
		if (person.getPostPriv() == 0) {// 本部门
			if (person.getDept() != null) {
				list.add(person.getDept());
			}
		} else if (person.getPostPriv() == 1) {// 所有
			list = deptDao.getAllDept();

		} else if (person.getPostPriv() == 2) {// 指定部门
			list = person.getPostDept();

		} else if (person.getPostPriv() == 3) {// 本部以及下级所有部门
			if (person.getDept() != null) {
				list.add(person.getDept());
				String level = person.getDept().getUuid() + ",";
				if (!TeeUtility.isNullorEmpty(person.getDept()
						.getDeptParentLevel())) {// 如果不是第一级部门
					level = person.getDept().getDeptParentLevel();
				} else {// 如果是第一级部门，则把uuID当做级别
					level = person.getDept().getGuid();
				}
				List<TeeDepartment> deptChildList = deptDao
						.getAllChildDeptByLevl(level);
				list.addAll(deptChildList);
			}
		}

		return list;
	}

	/**
	 * 控制面板 --- 界面主题设置
	 * 
	 * @param personModel
	 * @param request
	 */
	public boolean updateDeskTop(TeePersonModel personModel, TeePerson person) {
		boolean updateStatus = false;

		person = personDao.selectPersonById(person.getUuid());
		if (person != null) {
			person.setTheme(personModel.getTheme());
			person.setBkground(personModel.getBkground());
			person.setMenuExpand(personModel.getMenuExpand());
			person.setMenuType(personModel.getMenuType());
			person.setSmsOn(personModel.getSmsOn());
			person.setCallSound(personModel.getCallSound());
			person.setShowWeather(personModel.getShowWeather());
			person.setWeatherCity(personModel.getWeatherCity());
			person.setShowRss(personModel.getShowRss());
			person.setAutoPopSms(personModel.getAutoPopSms());
			personDao.update(person);
			return true;
		}

		return updateStatus;

	}

	/**
	 * 控制面板 --- 个人信息
	 * 
	 * @param personModel
	 * @param request
	 */
	public boolean updateDeskTopInfo(TeePersonModel personModel,
			TeePerson person) {
		boolean updateStatus = false;

		person = personDao.selectPersonById(person.getUuid());
		if (person != null) {
			person.setSex(personModel.getSex());
			person.setBkground(personModel.getBkground());
			if (!TeeUtility.isNullorEmpty(personModel.getBirthdayStr())) {
				Date birthday;
				try {
					birthday = TeeUtility.parseDate("yyyy-MM-dd",
							personModel.getBirthdayStr());
					person.setBirthday(birthday);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				person.setBirthday(null);
			}

			person.setTelNoDept(personModel.getTelNoDept());
			person.setMobilNoHidden(personModel.getMobilNoHidden());
			person.setMobilNo(personModel.getMobilNo());
			person.setFaxNoDept(personModel.getFaxNoDept());
			person.setOicqNo(personModel.getOicqNo());
			person.setWeixinNo(personModel.getWeixinNo());
			person.setEmail(personModel.getEmail());
			person.setMSN(personModel.getMSN());
			person.setAddHome(personModel.getAddHome());
			person.setPostNoHome(personModel.getPostNoHome());
			person.setTelNoHome(personModel.getTelNoHome());
			personDao.update(person);
			return true;
		}
		return updateStatus;

	}

	/**
	 * 控制面板 --- 昵称
	 * 
	 * @param personModel
	 * @param request
	 */
	public boolean updateDeskTopAvatar(TeePersonModel personModel,
			TeePerson person) {
		boolean updateStatus = false;

		person = personDao.selectPersonById(person.getUuid());
		if (person != null) {

			personDao.update(person);
			return true;
		}
		return updateStatus;

	}

	/**
	 * 控制面板 --- 我的账户
	 * 
	 * @param personModel
	 * @param request
	 */
	public boolean updateDeskTopMypriv(TeePersonModel personModel,
			TeePerson person) {
		boolean updateStatus = false;

		person = personDao.selectPersonById(person.getUuid());
		if (person != null) {
			person.setByname(personModel.getByname());
			personDao.update(person);
			return true;
		}
		return updateStatus;

	}

	/**
	 * 根据人员uuid字符串获取的所有人员
	 * 
	 * @param personUuids
	 *            : 人员uuid字符串
	 */
	public List<TeePerson> getPersonByUuids(String personUuids) {
		return personDao.getPersonByUuids(personUuids);
	}

	/**
	 * 更新人员快捷展示区
	 * 
	 * @param personModel
	 * @param request
	 */
	public int updatePersonPortlet(TeePersonModel personModel, int personId) {
		int count = personDao.updatePersonPortletByUuids(personId, personModel);
		return count;
	}

	/**
	 * 更新个人设置图象和昵称
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public TeeJson updateAvatar(HttpServletRequest request) throws Exception {
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		TeeJson json = new TeeJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String nickName = TeeStringUtil.getString(multipartRequest
				.getParameter("nickName"));
		String avatar = TeeStringUtil.getString(multipartRequest
				.getParameter("avatar"));
		if (!nickName.equals("")) {
			boolean nikeNameExist = personDao.checkNickNameExist(
					person.getUuid(), nickName);
			if (nikeNameExist) {
				json.setRtState(false);
				json.setRtMsg("昵称已被其他人使用！");
				return json;
			}
		}
		TeePersonModel model = new TeePersonModel();
		model.setAvatar(avatar);
		// try {
		// MultipartFile file = multipartRequest.getFile("avatarFile");
		// if(!file.isEmpty() ){
		// //获取真实文件名称
		// String realName = file.getOriginalFilename();
		// /**
		// * 获取文件后缀
		// */
		// avatrarNameExt = TeeFileUtility.getFileExtName(realName);// 文件后缀
		// avatarIs = file.getInputStream();
		//
		// if(avatarIs != null && file != null){//头像大小
		// if(AVATAR_UPLOAD == 0){//不允许
		// json.setRtState(false);
		// json.setRtMsg("系统不允许上传头像，请与系统管理员联系！");
		// return json;
		// }
		// BufferedImage srcImage;
		// try {
		// srcImage = ImageIO.read(avatarIs);
		// } catch (Exception ex) {
		// srcImage = Sanselan.getBufferedImage(avatarIs);
		// }
		// if (srcImage != null) {
		// int imageWidth = srcImage.getWidth(null);
		// int imageHeight = srcImage.getHeight(null);
		// if (AVATAR_WIDTH < imageWidth || AVATAR_HEIGHT < imageHeight) {
		// json.setRtState(false);
		// json.setRtMsg("图片大小超过" + AVATAR_WIDTH + "*" + AVATAR_HEIGHT );
		// return json;
		// }
		// }
		//
		// TeePerson person0 = personDao.load(person.getUuid());
		//
		// //保存人员头像
		// TeeAttachment attachement = baseUpload.singleAttachUpload(file,
		// avatrarNameExt, TeeAttachmentModelKeys.PERSON,person0);
		// if(attachement != null ){
		// attachement.setModelId("0");
		// model.setAvatar(attachement.getSid() + "");
		// }
		// }
		//
		// }
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// throw ex;
		// }
		model.setNickName(nickName);
		personDao.updateAvatar(person.getUuid(), model);
		
		TeeOpenfireUtil.createUser(person.getUserId(), person.getUserName());
		
		json.setRtState(true);
		return json;
	}

	/**
	 * 更新在线时长
	 * 
	 * @author syl
	 * @date 2013-11-24
	 * @param uuid
	 * @param online
	 */
	public void updateOnline(int uuid, int online) {
		personDao.updateOnline(uuid, online);
	}

	/**
	 * 获取在线人员
	 * 
	 * @author syl
	 * @date 2013-12-2
	 * @return
	 */
	public long queryOnlineCount() {
		return personDao.queryOnlineCount();
	}

	/**
	 * 根据UUID获取人员 id 和 姓名
	 * 
	 * @author syl
	 * @date 2014-2-14
	 * @param uuids
	 * @return
	 */
	public String[] getPersonNameAndUuidByUuids(String uuids) {
		return personDao.getPersonNameAndUuidByUuids(uuids);
	}

	/**
	 * 根据UUID获取人员 数组对象
	 * 
	 * @author syl
	 * @date 2014-2-14
	 * @param uuids
	 * @return
	 */
	public List<TeePersonModel> getPersonListByUuids(String uuids) {
		List<TeePersonModel> listModel = new ArrayList<TeePersonModel>();
		List<TeePerson> list = personDao.getPersonByUuids(uuids);
		for (int i = 0; i < list.size(); i++) {
			TeePersonModel model = new TeePersonModel();
			if(list.get(i).getUserRole()!=null){
				model.setUserRoleStrName(list.get(i).getUserRole().getRoleName());
			}
			if(list.get(i).getDept()!=null){
				model.setDeptIdName(list.get(i).getDept().getDeptName());
			}
			model.setOrgName(deptService.getCurrentPersonOrgName(list.get(i)));
			BeanUtils.copyProperties(list.get(i), model);
			listModel.add(model);
		}
		return listModel;
	}

	/**
	 * 更新人员桌面菜单设置 ---第二套风格
	 * 
	 * @author syl
	 * @date 2014-3-20
	 * @param request
	 * @return
	 */
	public TeeJson updatePersonMenuParamSet(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		String menuParamSet = TeeStringUtil.getString(request
				.getParameter("menuParamSet"));
		loginPerson.setMenuParamSet(menuParamSet);
		personDao.updatePersonMenuParamSet(loginPerson);
		json.setRtState(true);
		return json;
	}

	/**
	 * 获取用户路径
	 * 
	 * @param uuid
	 * @return
	 */
	public List<String> getUserPath(int userUuid) {
		List<String> path = new ArrayList();
		TeePerson person = personDao.selectPersonById(userUuid);
		// 获取人员主部门路径
		path.add(person.getDept().getDeptFullName() + "/"
				+ person.getUserName());
		// 获取人员辅助部门路径
		List<TeeDepartment> deptOther = person.getDeptIdOther();
		for (TeeDepartment other : deptOther) {
			path.add(other.getDeptFullName() + "/" + person.getUserName());
		}

		return path;
	}

	/**
	 * 获取用户部门路径
	 * 
	 * @param userUuid
	 * @return
	 */
	public List<String> getDeptPath(int userUuid) {
		List<String> path = new ArrayList();
		TeePerson person = personDao.selectPersonById(userUuid);
		// 获取人员主部门路径
		path.add(person.getDept().getDeptFullName());
		// 获取人员辅助部门路径
		List<TeeDepartment> deptOther = person.getDeptIdOther();
		for (TeeDepartment other : deptOther) {
			path.add(other.getDeptFullName());
		}

		return path;
	}

	/**
	 * 获取已被删除和 外部人员 列表
	 * 
	 * @author syl
	 * @date 2014-6-1
	 * @return
	 */
	public TeeEasyuiDataGridJson queryDeletePerson(HttpServletRequest request,TeeDataGridModel dm) {
		//获取前台页面传来的查询参数
		String userId=TeeStringUtil.getString(request.getParameter("userId"));//用户名
		String userName=TeeStringUtil.getString(request.getParameter("userName"));//用户姓名
		int deptId=TeeStringUtil.getInteger(request.getParameter("deptId"),0);//所属部门
		int roleId=TeeStringUtil.getInteger(request.getParameter("roleId"),0);//所属角色
		String mobilNo=TeeStringUtil.getString(request.getParameter("mobilNo"));//手机号
		String email=TeeStringUtil.getString(request.getParameter("email"));//邮箱
		String oicqNo=TeeStringUtil.getString(request.getParameter("oicqNo"));//qq
		String weixinNo=TeeStringUtil.getString(request.getParameter("weixinNo"));//微信号
		
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql = " from TeePerson p where (p.deleteStatus = '1' or p.dept is null)  ";
		
		if(userId!=null&&!("").equals(userId)){
			hql+=" and p.userId like '"+"%"+userId+"%"+"'";
		}
		if(userName!=null&&!("").equals(userName)){
			hql+=" and p.userName like '"+"%"+userName+"%"+"'";
		}
		if(mobilNo!=null&&!("").equals(mobilNo)){
			hql+=" and p.mobilNo like '"+"%"+mobilNo+"%"+"'";
		}
		if(email!=null&&!("").equals(email)){
			hql+=" and p.email like '"+"%"+email+"%"+"'";
		}
		if(oicqNo!=null&&!("").equals(oicqNo)){
			hql+=" and p.oicqNo like '"+"%"+oicqNo+"%"+"'";
		}
		if(weixinNo!=null&&!("").equals(weixinNo)){
			hql+=" and p.weixinNo like '"+"%"+weixinNo+"%"+"'";
		}
		if(deptId!=0){
			hql+=" and (p.dept.uuid="+deptId+" or (exists(select 1 from p.deptIdOther od where od.uuid="+deptId+"))) " ;
		}
		
		if(roleId!=0){
			hql+=" and (p.userRole.uuid="+roleId+" or (exists(select 1 from p.userRoleOther orole where orole.uuid="+roleId+"))) " ;
		}
		
		//System.out.println(hql);
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, null));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeePerson> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查

		List<TeePersonModel> listModel = new ArrayList<TeePersonModel>();
		for (int i = 0; i < list.size(); i++) {
			TeePersonModel model = parseModel(list.get(i), true);
			listModel.add(model);
		}
		j.setRows(listModel);// 设置返回的行
		return j;
	}

	/**
	 * 转换模型
	 */
	public static TeePersonModel parseModel(TeePerson person, boolean isSimple) {
		TeePersonModel model = new TeePersonModel();
		if (person == null) {
			return model;
		}
		BeanUtils.copyProperties(person, model);
		String deptName = "";
		String userRoleName = "";
		int deptId=0;
		if (person.getDept() != null) {
			deptName = person.getDept().getDeptName();
			deptId= person.getDept().getUuid();
		}
		if (person.getUserRole() != null) {
			userRoleName = person.getUserRole().getRoleName();
		}
		model.setDeptIdName(deptName);
		model.setUserRoleStrName(userRoleName);
		// 辅助部门
		String deptOtherIds = "";
		String deptOtherNames = "";
		List<TeeDepartment> deptOtherList = person.getDeptIdOther();
		if (!isSimple) {
			if (deptOtherList.size() > 0) {// 辅助部门
				for (int i = 0; i < deptOtherList.size(); i++) {
					deptOtherIds = deptOtherIds
							+ deptOtherList.get(i).getUuid() + ",";
					deptOtherNames = deptOtherNames
							+ deptOtherList.get(i).getDeptName() + ",";
				}

			}
		}
		model.setDeptIdOtherStr(deptOtherIds);
		model.setDeptIdOtherStrName(deptOtherNames);
        model.setDeptId(deptId+"");
		return model;
	}

	/**
	 * 物理删除
	 * 
	 * @param uuid
	 */
	public void delPerson(int uuid) {
		if (uuid == 4) {
			throw new TeeOperationException("不允许删除系统管理员账户");
		}
		personDao.delete(uuid);
	}
	
	
	/**
	 * 还原离职人员
	 * @param uuid
	 * @throws Exception 
	 */
	public void reductionPerson(int uuid,HttpServletRequest request) throws Exception {
		personDao.reductionPerson(uuid);
		TeePerson person = personDao.get(uuid);
		syncSystem(person, "0", request);		
	}
	
	

	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}

	public void setDeptDao(TeeDeptDao deptDao) {
		this.deptDao = deptDao;
	}

	public void setOrgDao(TeeOrgDao orgDao) {
		this.orgDao = orgDao;
	}

	public void setRoleDao(TeeUserRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setMenuGroupDao(TeeMenuGroupDao menuGroupDao) {
		this.menuGroupDao = menuGroupDao;
	}

	public void setModulPrivDao(TeeModulePrivDao modulPrivDao) {
		this.modulPrivDao = modulPrivDao;
	}

	public void setBaseUpload(TeeBaseUpload baseUpload) {
		this.baseUpload = baseUpload;
	}
	
	/**
	 * lei
	 * @throws NoSuchAlgorithmException 
	 * 
	 */
	public void adduserss(TeePersonModel personModul) throws NoSuchAlgorithmException{
		TeePerson person = new TeePerson();
		
			try{
				if (!TeeUtility.isNullorEmpty(personModul.getUserRoleOtherId())) {
					List<TeeUserRole> roleList = roleDao
							.getPrivListByUuids(personModul.getUserRoleOtherId());
					person.setUserRoleOther(roleList);
				}
				// 辅助部门
				if (!TeeUtility.isNullorEmpty(personModul.getDeptIdOtherStr())) {
					List<TeeDepartment> deptOtherlist = deptDao
							.getDeptListByUuids(personModul.getDeptIdOtherStr());
					person.setDeptIdOther(deptOtherlist);
				}

				// 管理范围
				if (!TeeUtility.isNullorEmpty(personModul.getPostDeptStr())) {
					List<TeeDepartment> postDeptlist = deptDao
							.getDeptListByUuids(personModul.getPostDeptStr());
					person.setPostDept(postDeptlist);
				}

				// 菜单组
				if (!TeeUtility.isNullorEmpty(personModul.getMenuGroupsStr())) {
					List<TeeMenuGroup> menuGrouplist = menuGroupDao
							.getMenuGroupListByUuids(personModul.getMenuGroupsStr());
					person.setMenuGroups(menuGrouplist);
				}

				if (!TeeUtility.isNullorEmpty(personModul.getDeptId())) {
					person.setDept(deptDao.selectDeptById(Integer
							.parseInt(personModul.getDeptId())));
				}

				if (!TeeUtility.isNullorEmpty(personModul.getUserRoleStr())) {
					person.setUserRole(roleDao.selectByUUId(Integer
							.parseInt(personModul.getUserRoleStr())));
				}

				

					BeanUtils.copyProperties(personModul, person);

				/*	String password = "";
					if (!TeeUtility.isNullorEmpty(personModul.getPassword())) {
						password = personModul.getPassword();
					}*/
					
					person.setPassword("$1$rPpefogg$d41d8cd98f00b204e9800998ecf8427eIuO6");
					person.setDeleteStatus("0");
					person.setUniqueId(personModul.getUniqueId());
					//person.setUserId(personModul.getUserId());
					person.setUserName(personModul.getUserName());
					person.setSex(personModul.getSex());
					person.setMenuImage(personModul.getMenuImage());
					person.setUserId(personModul.getUniqueId());
					if(!TeeUtility.isNullorEmpty(personModul.getDeptId())){
						TeeDepartment td = (TeeDepartment)simpleDaoSupport.get(TeeDepartment.class,Integer.parseInt(personModul.getDeptId()));
						person.setDept(td);
					}
					if(!TeeUtility.isNullorEmpty(personModul.getUserRoleStr())){
						TeeUserRole tur = (TeeUserRole)simpleDaoSupport.get(TeeUserRole.class,Integer.parseInt(personModul.getUserRoleStr()));
					person.setUserRole(tur);
					}
				    person.setDeleteStatus("0");
				    person.setTheme("4");
					personDao.addPerson(person);
			
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
			
	}

	public void updateuserss(TeePersonModel personModul) {
		TeePerson tp = (TeePerson)simpleDaoSupport.get(TeePerson.class,Integer.parseInt(personModul.getUniqueId()));
		tp.setUserName(personModul.getUserName());
		tp.setSex(personModul.getSex());
		simpleDaoSupport.update(tp);
		
	}

	public void deleteuserss(TeePersonModel personModul) {
		
		TeePerson tp = (TeePerson)simpleDaoSupport.get(TeePerson.class, personModul.getUuid());
		if(!TeePersonService.checkIsSuperAdmin(tp, tp.getUserId())){
		simpleDaoSupport.deleteByObj(tp);
		}
	}
    
	/**
	 * 查询统计当前人员是否在线
	 * @param uuid
	 * @return
	 */
	public int selectOnlineByUserId(int uuid) {
		return personDao.selectOnlineByUserId(uuid);
	}

	/**
	 * 获取指定人员的直属下级
	 * @param userId
	 * @return
	 */
	public List<TeePerson> getUnderlines(int userId){
		return personDao.getUnderlines(userId);
	}

	
	/**
	 * 判断当前这个Ukey是否被其他人占用
	 * @param request
	 * @return
	 */
	public TeeJson checkKeySNIsExist(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的keySN
		String keySN=TeeStringUtil.getString(request.getParameter("keySN"));
		String hql=" from TeePerson where keySN=? ";
		List<TeePerson> personList=simpleDaoSupport.executeQuery(hql,new Object[]{keySN});
		if(personList!=null&&personList.size()>0){
			json.setRtData(1);//被人占用，不可用
		}else{
			json.setRtData(0);//没有被人占用，可用
		}
		json.setRtState(true);
		return json;
	}

	
	/**
	 * 判读即将绑定的人员是否已经与其他设备绑定了
	 * @param request
	 * @return
	 */
	public TeeJson checkUserIsBound(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int uuid=TeeStringUtil.getInteger(request.getParameter("uuid"), 0);
		if(uuid>0){
			TeePerson p=(TeePerson) simpleDaoSupport.get(TeePerson.class,uuid);
			if(p!=null){
				String keySN=TeeStringUtil.getString(p.getKeySN());
				if(("").equals(keySN)){//沒有綁定過 可以绑定
					json.setRtState(true);
					json.setRtData(0);
				}else{//已经绑定过其他设备  不可以继续绑定
					json.setRtState(true);
					json.setRtData(1);
				}
			}else{
				json.setRtState(false);
				json.setRtMsg("被绑定人员不存在！");
			}
		}else{
			json.setRtState(false);
			json.setRtMsg("被绑定人员不存在！");
		}
		return json;
	}

	
	/**
	 * 进行UKey绑定
	 * @param request
	 * @return
	 */
	public TeeJson boundUkey(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String keySN=TeeStringUtil.getString(request.getParameter("keySN"));
		int uuid=TeeStringUtil.getInteger(request.getParameter("uuid"),0);
		String hql=" update TeePerson  set keySN=? where uuid=? ";
		simpleDaoSupport.executeUpdate(hql, new Object[]{keySN,uuid});
		json.setRtState(true);
		json.setRtMsg("绑定成功！");		
		return json;
	}

	
	/**
	 * 判断用户和UKEY设备是否匹配
	 * @param request
	 * @return
	 */
	public TeeJson checkUserAndUkeyIsMatch(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int uuid=TeeStringUtil.getInteger(request.getParameter("uuid"), 0);
		String keySN=TeeStringUtil.getString(request.getParameter("keySN"));
		String hql="from TeePerson where uuid=? and keySN=? ";
		List<TeePerson> personList=simpleDaoSupport.executeQuery(hql, new Object[]{uuid,keySN});
		if(personList!=null&&personList.size()>0){//匹配
			json.setRtState(true);
		}else{//不匹配
			json.setRtState(false);
		}
		return json;
	}

	
	//解绑ukey
	public TeeJson unBoundUkey(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int uuid=TeeStringUtil.getInteger(request.getParameter("uuid"),0);
		if(uuid>0){
			String hql=" update TeePerson set keySN=? where uuid=?";
			simpleDaoSupport.executeUpdate(hql, new Object[]{null,uuid});
			json.setRtState(true);
			json.setRtMsg("解绑成功！");
		}else{
		   json.setRtState(false);
		   json.setRtMsg("用户不存在！");
		}
		return json;
	}

	
	/**
	 * 获取部门人员树  根据可见范围
	 * @param id
	 * @param person
	 * @return
	 */
	public TeeJson getOrgTreeByViewPriv(String deptId, TeePerson LoginPerson,String isAdmin) {
			TeeJson json = new TeeJson();
			List<TeeZTreeModel> orgDeptList = new ArrayList<TeeZTreeModel>();
			try {
				if (!TeeUtility.isNullorEmpty(deptId)){
					String postDeptIds = getLoginPersonPostDept(LoginPerson);// 获取系统当前登录人管理范围部门Id字符串
					Map  deptListPriv= new HashMap();
					List<TeeDepartment> deptList = deptService.getDeptsByLoginUser(LoginPerson);//获取当前登陆人  可见范围的部门 
					
					//获取有可见权限的所有上级部门和自己的集合
					Map  deptIdSetPriv= new HashMap();
					
					String[] ids = null;
					String deptFullId = null;
					TeeDepartment d = null;
				
					for (TeeDepartment dept : deptList) {
						deptListPriv.put(dept.getUuid(), "");
						deptFullId=dept.getDeptFullId();
						deptFullId=deptFullId.substring(1,deptFullId.length());
						ids = deptFullId.split("/");
						
						
						//顶级的部门主键
						for (String s : ids) {
							if (!deptIdSetPriv.containsKey(s)) {//从map里取出来，不用内部for循环
								deptIdSetPriv.put(s, "");
								
							}
						}
					}
					
//					depts.addAll(simpleDaoSupport.find("select new TeeDepartment(uuid,deptName,deptFullId) from TeeDepartment d  where  "+TeeDbUtility.IN("d.uuid", newIds.toString()), null));
					
					String[] idArray = deptId.split(";");
					if (idArray.length >= 2){
						Object[] values = { Integer.parseInt(idArray[0]) };
						if (idArray[1].equals("dept")) {// 部门
							List<TeeDepartment> list = deptDao
									.selectDept(
											"from TeeDepartment  where deptParent.uuid = ?  order by deptNo ",
											values);
							for (int i = 0; i < list.size(); i++) {
								TeeDepartment dept = list.get(i);
								if(!deptListPriv.containsKey(dept.getUuid()+"")
										&& !deptIdSetPriv.containsKey(dept.getUuid()+"")){
									continue;
								}
								boolean isParent = false;
								long count = personDao
										.selectPersonCountByDeptIdandOtherDept(dept
												.getUuid());
								if (deptDao.checkExists(dept) || count > 0) {// 如果存在下级获取用户
									isParent = true;
								}
								TeeZTreeModel ztree = null;
								if (dept.getDeptType() == 2) {//分支机构
									ztree = new TeeZTreeModel(dept.getUuid()
											+ ";dept", dept.getDeptName(),
											isParent, deptId, true, "pIconHome", "");
								} else {//部门/全局部门
									ztree = new TeeZTreeModel(dept.getUuid()
											+ ";dept", dept.getDeptName(),
											isParent, deptId, true, "deptNode", "");
								}
								orgDeptList.add(ztree);

							}
							List<TeePerson> personList = new ArrayList<TeePerson>();
							String NOT_VIEW_USER = TeeStringUtil.getString(
									LoginPerson.getNotViewUser(), "0");// 是否禁止查看用户列表
																		// 1-禁止
							if (!NOT_VIEW_USER.equals("1")) {
								personList = personDao
										.selectPersonByDeptIdAndOtherDeptAndPostDept(
												Integer.parseInt(idArray[0]),
												checkIsSuperAdmin(LoginPerson, ""),
												postDeptIds,getMinRoleNoByLoginPerson(LoginPerson),
												isAdmin);// 展示所有人员，包括辅助部门
							}
							// 处理重复人员
							List distictPerson = new ArrayList();
							for (int j = 0; j < personList.size(); j++) {
								TeePerson person = personList.get(j);
								if (distictPerson.contains(person.getUuid())) {
									continue;
								}
								distictPerson.add(person.getUuid());
								TeeZTreeModel ztreePerson = new TeeZTreeModel(
										person.getUuid() + ";personId",
										person.getUserName(), false, deptId, true,
										"person_online_node",
										getPersonZtreeTitle(person));
								orgDeptList.add(ztreePerson);
							}
						} else if (idArray[1].equals("person")) {// 人员不处理

						}

					} else {		
						List<TeeDepartment> list = deptDao.getFisrtDept();//获取所有的一级部门
						for (int i = 0; i < list.size(); i++) {
							TeeDepartment dept = list.get(i);
							
							if(!deptListPriv.containsKey(dept.getUuid()+"")
									&& !deptIdSetPriv.containsKey(dept.getUuid()+"")){
								continue;
							}
							boolean isParent = false;
							long count = personDao
									.selectPersonCountByDeptIdandOtherDept(dept
											.getUuid());
							if (deptDao.checkExists(dept) || count > 0) {// 如果存在下级部门
								isParent = true;
							}
							TeeZTreeModel ztree = null;
							if (dept.getDeptType() == 2) {//分支机构
								ztree = new TeeZTreeModel(dept.getUuid() + ";dept",
										dept.getDeptName(), isParent, deptId, true,
										"pIconHome", "");
							} else {//部门/全局部门
								ztree = new TeeZTreeModel(dept.getUuid() + ";dept",
										dept.getDeptName(), isParent, deptId, true,
										"deptNode", "");
							}
							orgDeptList.add(ztree);
						}
						/**
						 * 获取没有部门的人员
						 */
						if(!"1".equals(isAdmin)) {
							List<TeePerson> pl = personDao.selectNullDeptPerson();
							for (int i = 0; i < pl.size(); i++) {
								TeePerson person = pl.get(i);
								TeeZTreeModel ztreePerson = new TeeZTreeModel(
										person.getUuid() + ";personId",
										person.getUserName(), false, deptId, true,
										"person_online_node",
										getPersonZtreeTitle(person));
								orgDeptList.add(ztreePerson);
							}							
						}
					}

				} else {// 获取单位
					List<TeeOrganization> list = orgDao.traversalOrg();
					if (list.size() > 0) {
						TeeOrganization org = list.get(0);
						TeeZTreeModel ztree = new TeeZTreeModel(org.getSid() + "",
								org.getUnitName(), true, "0", true, "pIconHome", "");
						orgDeptList.add(ztree);
					}
				}
				json.setRtMsg("获取成功");
				json.setRtData(orgDeptList);
				json.setRtState(true);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				json.setRtMsg(e.getMessage());
				json.setRtState(false);
			}

			return json;
	}

	
	/**
	 * 根据用户主键  获取头像id   若没有头像返回的是空字符串
	 * @param request
	 * @return
	 */
	public TeeJson getAvatarId(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String userId=TeeStringUtil.getString(request.getParameter("userId"));
		if(!TeeUtility.isNullorEmpty(userId)){
			TeePerson p=personDao.getPersonByUserId(userId);
			if(p!=null){
				json.setRtData(TeeStringUtil.getString(p.getAvatar()));
			}else{
				json.setRtData("");
			}	
		}else{
			json.setRtData("");
		}
		json.setRtState(true);
		return json;
	}

	
	/**
	 * 根据当前登录人   
	 * @param loginUser
	 * @return
	 */
	public  int getMinRoleNoByLoginPerson(TeePerson loginUser){
		loginUser=(TeePerson) simpleDaoSupport.get(TeePerson.class,loginUser.getUuid());
		int minRoleNo=0;
		if(loginUser.getUserRole()!=null){
			minRoleNo =TeeStringUtil.getInteger(loginUser.getUserRole().getRoleNo(), 0);
		}
		
		
		List<TeeUserRole> roleList=new ArrayList<TeeUserRole>();
		//获取当前登录人的辅助角色集合
		roleList=loginUser.getUserRoleOther();
        if(roleList.size()>0){
        	for (TeeUserRole teeUserRole : roleList) {
				if(teeUserRole.getRoleNo()< minRoleNo){
					minRoleNo=teeUserRole.getRoleNo();
				}
        		
			}	
        }
        return minRoleNo;
		
	}

	/**
	 * 获取辅助角色为领导活动的人员
	 * */
	public TeeJson getHongdongLd() {
		TeeJson json=new TeeJson();
		Session session = personDao.getSession();
		String sql="select p.UUID as uuid,p.USER_NAME userName from PERSON p,USER_ROLE u,PERSON_USER_ROLE ur where p.uuid=ur.PERSON_UUID and u.uuid=ur.USER_ROLE_UUID and u.ROLE_NAME='领导活动安排' order by p.USER_NO";
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		json.setRtData(list);
		return json;
	}

	public List<Map> getHongdongTz() {
		TeeJson json=new TeeJson();
		Session session = personDao.getSession();
		String sql="select p.UUID as uuid,p.USER_NAME userName from PERSON p,USER_ROLE u,PERSON_USER_ROLE ur where p.uuid=ur.PERSON_UUID and u.uuid=ur.USER_ROLE_UUID and u.ROLE_NAME='活动安排通知'";
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		return list;
	}
	//通过uuid查用户
	public TeePerson get(int sendUserIds){
		
		
		return personDao.get(sendUserIds);
		
	}

	
	/**
	 * 根据菜单id判断该菜单是不是当前用户的快捷菜单
	 * @param request
	 * @return
	 */
	public TeeJson isQuickMenu(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取页面上传来的菜单id
		int menuId=TeeStringUtil.getInteger(request.getParameter("menuId"), 0);
		
		//获取当前登录人的快捷菜单ids
		String quickMenuIds=loginUser.getQuickMenuIds();
		
		if(!TeeUtility.isNullorEmpty(quickMenuIds)){
			String[] menuIdsArray=quickMenuIds.split(",");
		    if(menuIdsArray!=null&&menuIdsArray.length>0){
		    	for (String id : menuIdsArray) {
					int mId=TeeStringUtil.getInteger(id, 0);
					if(mId==menuId){//有这个快捷菜单
						json.setRtData(1);
					}else{//没有这个快捷菜单
						json.setRtData(1);
					}
				}
		    }else{
		    	json.setRtData(0);
		    }
		}else{//当前登陆人还没有设置任何快捷菜单
			json.setRtData(0);
		}
		json.setRtState(true);
		return json;
	}

	
	/**
	 * 添加快捷菜单
	 * @param request
	 * @return
	 */
	public TeeJson addQuickMenu(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		loginUser=(TeePerson) simpleDaoSupport.get(TeePerson.class,loginUser.getUuid());
		//获取前台传来的menuId
		int menuId=TeeStringUtil.getInteger(request.getParameter("menuId"), 0);
		TeeSysMenu menu=(TeeSysMenu) simpleDaoSupport.get(TeeSysMenu.class,menuId);
		if(menu!=null){
			String quickMenuIds=loginUser.getQuickMenuIds();
			if(!TeeUtility.isNullorEmpty(quickMenuIds)){
				if(quickMenuIds.endsWith(",")){
					quickMenuIds+=menu.getUuid();
				}else{
					quickMenuIds+=","+menu.getUuid();
				}
			}else{
				quickMenuIds=menu.getUuid()+"";
			}
			loginUser.setQuickMenuIds(quickMenuIds);
			simpleDaoSupport.update(loginUser);
			//System.out.println(quickMenuIds);
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}

	
	/**
	 * 移除快捷菜单
	 * @param request
	 * @return
	 */
	public TeeJson removeQuickMenu(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		loginUser=(TeePerson) simpleDaoSupport.get(TeePerson.class,loginUser.getUuid());
		
		//获取前台传来的menuId
		int menuId=TeeStringUtil.getInteger(request.getParameter("menuId"), 0);
		
		String quickMenuIds=loginUser.getQuickMenuIds();
		if(!TeeUtility.isNullorEmpty(quickMenuIds)){
			String[] qmIdArray=quickMenuIds.split(",");
			
			List<String> list=new ArrayList<String>();
			
			if(qmIdArray!=null){
				//将数组中的数据放到list集合中
				for (int i = 0; i < qmIdArray.length; i++) {
					list.add(qmIdArray[i]);
				}
				
				for (int i=0;i<qmIdArray.length;i++) {
					if(menuId==TeeStringUtil.getInteger(qmIdArray[i], 0)){
						list.remove(i);
					}
				}
			}
			
			
			loginUser.setQuickMenuIds(list.toString().replace("[", "").replace("]","").replace(" ", ""));
			simpleDaoSupport.update(loginUser);
		}
		json.setRtState(true);
		return json;
	}

	
	/**
	 * 获取当前登录人快捷 菜单ids
	 * @param request
	 * @return
	 */
	public TeeJson getQuickMenuIds(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		loginUser=(TeePerson) simpleDaoSupport.get(TeePerson.class,loginUser.getUuid());
		String quickMenuIds=loginUser.getQuickMenuIds();
		json.setRtState(true);
		json.setRtData(quickMenuIds);
		return json;
	}

	
	
	/**
	 * 获取当前登陆人又权限的快捷菜单集合
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public TeeJson getQuickMenus(HttpServletRequest request) throws Exception {
		TeeJson json=new TeeJson();
		List<TeeSysMenuModel> l=new ArrayList<>();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    loginUser=(TeePerson) simpleDaoSupport.get(TeePerson.class,loginUser.getUuid());
		//获取当前登陆者快捷菜单ids
	    String quickMenuIds=loginUser.getQuickMenuIds();
	    //获取当前登陆人有权限的菜单
	    List<String> privIdList=new ArrayList<String>();
	    List<TeeSysMenuModel> privList = menuGroupServ.getPrivSysMenu(loginUser);
		for (TeeSysMenuModel teeSysMenuModul : privList) {
			privIdList.add(teeSysMenuModul.getUuid()+"");
		}
		
		
		if(!TeeUtility.isNullorEmpty(quickMenuIds)){
			String [] ids=quickMenuIds.split(",");
			TeeSysMenu menu=null;
			TeeSysMenuModel model=null;
			BASE64Encoder en=new BASE64Encoder();
			for (String s : ids) {
				if(privIdList.contains(s)){
					//有权限  
					menu=(TeeSysMenu) simpleDaoSupport.get(TeeSysMenu.class,TeeStringUtil.getInteger(s,0));
					model=new TeeSysMenuModel();
					BeanUtils.copyProperties(menu, model);
					//重新设置menuCode
					if(!TeeUtility.isNullorEmpty(menu.getMenuCode())){
						model.setMenuCode("/teeMenuGroup/visitPirv.action?url="+en.encode(menu.getMenuCode().getBytes()).replaceAll("\r\n", "")+"&menuName="+URLEncoder.encode(menu.getMenuName(), "utf-8"));
					}
					l.add(model);
				}
			}
		}
		
		
		json.setRtState(true);
		json.setRtData(l);
		return json;
	}

	
	
	/**
	 * 根据部门id获取员工通讯录
	 * @param dm
	 * @param response
	 * @return
	 */
	public TeeEasyuiDataGridJson getAddressListByDeptId(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
        //获取当前登录人
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		//获取传来的部门id
		int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
		
        String hql = " from TeePerson p where p.deleteStatus<>1 ";
		
		if(deptId!=0){
			hql+=" and p.dept.uuid="+deptId ;
		}
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, null));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeePerson> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查

		List<TeePersonModel> listModel = new ArrayList<TeePersonModel>();
		for (int i = 0; i < list.size(); i++) {
			TeePersonModel model = parseModel(list.get(i), true);
			listModel.add(model);
		}
		j.setRows(listModel);
		return j;
	}

	
	/**
	 * 获取当前登陆人的信息
	 * @param request
	 * @return
	 */
	public TeeJson getLoginUserInfo(HttpServletRequest request) {
		TeeJson json=new  TeeJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		loginUser=(TeePerson) simpleDaoSupport.get(TeePerson.class,loginUser.getUuid());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userName",loginUser.getUserName());
		if(loginUser.getDept()!=null){
			map.put("deptName",loginUser.getDept().getDeptName());
		}else{
			map.put("deptName","");
		}
		if(loginUser.getUserRole()!=null){//角色
			map.put("roleId",loginUser.getUserRole().getUuid());
		}else{
			map.put("roleId",0);
		}
		//获取当前登陆人头像
		if(!TeeUtility.isNullorEmpty(loginUser.getAvatar())){
			TeeAttachment att=(TeeAttachment) simpleDaoSupport.get(TeeAttachment.class,TeeStringUtil.getInteger(loginUser.getAvatar(), 0));
		    if(att!=null){
		    	map.put("tx", att.getSid());
		    }else{
		    	map.put("tx",0);
		    }
		}else{//没有头像
			map.put("tx",0);
		}
		json.setRtState(true);
		json.setRtData(map);
		return json;
	}

	
	/**
	 * 用户登录授权（禁止  启用用户登录权限）
	 * @param request
	 * @return
	 */
	public TeeJson updatePersonLoginPermission(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取将要修改的用户的主键
		int userUuid=TeeStringUtil.getInteger(request.getParameter("userUuid"), 0);
		TeePerson user=(TeePerson) simpleDaoSupport.get(TeePerson.class,userUuid);
		//获取修改状态  "1"=禁止登录     "0"=启用登录
		String notLogin=TeeStringUtil.getString(request.getParameter("notLogin"));
		if(user!=null){
			user.setNotLogin(notLogin);
			simpleDaoSupport.update(user);
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}

	
	
	/**
	 * 根据用户姓名和部门名称模糊查询用户列表
	 * @param dm
	 * @param response
	 * @return
	 */
	public TeeEasyuiDataGridJson getUserListByUserNameAndDeptName(
		TeeDataGridModel dm, HttpServletRequest request) {
		//用户姓名
		String userName=TeeStringUtil.getString(request.getParameter("userName"));//用户姓名
        //部门名称
		String deptName=TeeStringUtil.getString(request.getParameter("deptName"));
		
		
		
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql = " from TeePerson p where p.deleteStatus != '1'  ";
		
		
		if(!TeeUtility.isNullorEmpty(userName)){
			hql+=" and p.userName like '"+"%"+userName+"%"+"'";
		}
		
		if(!TeeUtility.isNullorEmpty(deptName)){
			hql+=" and p.dept.deptName like '"+"%"+deptName+"%"+"'";
		}
		
		//System.out.println(hql);
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, null));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeePerson> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查

		List<TeePersonModel> listModel = new ArrayList<TeePersonModel>();
		for (int i = 0; i < list.size(); i++) {
			TeePersonModel model = parseModel(list.get(i), true);
			listModel.add(model);
		}
		j.setRows(listModel);// 设置返回的行
		return j;
	}

	
	/**
	 * 渲染领导桌面   我的事项  待办工作  已办工作   消息提醒数量
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public TeeJson getLeaderDesktopNum(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取当前登录人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		Map<String,Long> data=new HashMap<String,Long>();
		
		//获取已办工作的总数量
		String hql="select count(title) from TeeYiBanEventView where recUserId = ?";
		data.put("ybNum",simpleDaoSupport.count(hql,new Object[]{loginUser.getUuid()} ));
		
		//获取待办工作数量
		String hql1="select count(title) from TeeDaiBanEventView where recUserId = ?";
		data.put("dbNum",simpleDaoSupport.count(hql1,new Object[]{loginUser.getUuid()} )); 
		
		
		//获取消息提醒的数量
		String hql2 = " select count(uuid) from TeeSms sms where sms.toId =? " 
				+ " and sms.deleteFlag = 0  and sms.remindFlag in (0) and sms.smsBody.sendFlag=1 ";
		data.put("xxNum",simpleDaoSupport.count(hql2,new Object[]{loginUser.getUuid()} )); 
		
		json.setRtData(data);
		json.setRtState(true);
		return json;
	}
	
	
	
	
}
