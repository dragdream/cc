package com.tianee.oa.core.org.model;

import java.util.Date;

import javax.persistence.Column;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;
public class TeePersonModel  extends TeeBaseModel{


	private int uuid;
	private String userId;//用户名英文缩写
	private String uniqueId;//唯一标示，做系统对接用，例如CA认证、移动手机号唯一标示
	private String userName;//用户姓名
	private String performCode;//执法证号，后来加的字段！
	private String byname;//别名
	private String useingKey;//使用USB KEY登录
	private String password;//用户密码
	private String keySN;//KEY码
	private String secureKeySn;//安全KEY码
	private String deptId;
	private String deptIdName;
	private int autoPopSms;//自动弹出事务提醒
	private String leaderIds;
	private String leaderNames;
	private String underlingIds;
	private String underlingNames;
	private String deviceId;//设备号绑定
	private int viewPriv = 1;//可见范围  1：本机构  2：指定部门  3：本部门
	private String viewDeptIds;//可见范围部门ID字符串
	private String viewDeptNames;//可见范围部门ID字符串
	private String userRoleOtherIds;//辅助角色id  
	private String userRoleOtherNames;//辅助角色人员姓名(扩展主角色)
	private String deptIdOtherStrs;//辅助部门id;
	private String deptIdOtherStrNames;//辅助部门名称(扩展部门)
	private String orgName;//所属机构名称
	private boolean fans;//判断是否被关注的人关注了
	
	private boolean fowllows;//判断是否关注了粉丝
	
	private int attachId;
	private String attachName;
	private String certUniqueId;//CA认证唯一标识   身份证号
	private String isAdmin;	//是否是管理员 0不是管理员，1分级管理员 9超级管理员
		
	public String getCertUniqueId() {
		return certUniqueId;
	}

	public void setCertUniqueId(String certUniqueId) {
		this.certUniqueId = certUniqueId;
	}

	public boolean isFowllows() {
		return fowllows;
	}

	public void setFowllows(boolean fowllows) {
		this.fowllows = fowllows;
	}

	public boolean isFans() {
		return fans;
	}

	public void setFans(boolean fans) {
		this.fans = fans;
	}

	public String getUserRoleOtherIds() {
		return userRoleOtherIds;
	}

	public void setUserRoleOtherIds(String userRoleOtherIds) {
		this.userRoleOtherIds = userRoleOtherIds;
	}

	public String getUserRoleOtherNames() {
		return userRoleOtherNames;
	}

	public void setUserRoleOtherNames(String userRoleOtherNames) {
		this.userRoleOtherNames = userRoleOtherNames;
	}

	public String getDeptIdOtherStrs() {
		return deptIdOtherStrs;
	}

	public void setDeptIdOtherStrs(String deptIdOtherStrs) {
		this.deptIdOtherStrs = deptIdOtherStrs;
	}

	public String getDeptIdOtherStrNames() {
		return deptIdOtherStrNames;
	}

	public void setDeptIdOtherStrNames(String deptIdOtherStrNames) {
		this.deptIdOtherStrNames = deptIdOtherStrNames;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
    public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptIdName() {
		return deptIdName;
	}
	public void setDeptIdName(String deptIdName) {
		this.deptIdName = deptIdName;
	}
	private String userRoleStr;//角色编号
	private String userRoleStrName;//角色编号名称
	public String getUserRoleStr() {
		return userRoleStr;
	}
	public void setUserRoleStr(String userRoleStr) {
		this.userRoleStr = userRoleStr;
	}
	public String getUserRoleStrName() {
		return userRoleStrName;
	}
	public void setUserRoleStrName(String userRoleStrName) {
		this.userRoleStrName = userRoleStrName;
	}
	private Integer postPriv;//管理范围
	private String postDeptStr;//管理范围uuid字符串
	private String postDeptStrName;//管理范围name字符串
	
	public String getPostDeptStr() {
		return postDeptStr;
	}
	public void setPostDeptStr(String postDeptStr) {
		this.postDeptStr = postDeptStr;
	}
	public String getPostDeptStrName() {
		return postDeptStrName;
	}
	public void setPostDeptStrName(String postDeptStrName) {
		this.postDeptStrName = postDeptStrName;
	}
	private String deptIdOtherStr;//辅助部门UUID字符窜
	private String deptIdOtherStrName;//辅助部门名称字符串
	
	private String sex;	
	private Date birthday;//生日
	private String birthdayStr;//
	private String telNoDept;//工作电话
	private String faxNoDept;//工作传	
	private String addHome;//家庭住址	
	private String postNoHome;//家庭邮编
	private String telNoHome;//家庭电话
	private String mobilNo;//手机号
	private String email;//电子邮件地址
	private String oicqNo;//QQ号码
	private String icq_no;//ICQ号码
	private String MSN;//MSN号码
	private String weixinNo;//微信号码
	private String nickName;//昵称
	private String avatar;//自定义头像
	private String callSound;//短信提示音
	private String dutyType;//考勤排班类型
	private String signWay;//考勤签到方式
	private Date lastVisitTime;//上次访问系统的时间
	private String smsOn;//短信提醒窗口弹出方式
	private String menuType;//登录模式
	private Date lastPassTime;//上次修改密码的时间
	private String theme;//界面主题

	private String shortcut;	
	private String  panel;//登录后显示的左侧面板
	private long online; //在线时长
	private String userOnlineStatus = "";//用户在线状态  1-在线 其它不在线
	
	private String deleteStatus;//删除状态
	
	
	public String getSignWay() {
		return signWay;
	}

	public void setSignWay(String signWay) {
		this.signWay = signWay;
	}

	public long getOnline() {
		return online;
	}
	public void setOnline(long online) {
		this.online = online;
	}
	private String onStatus;
	private String mobilNoHidden;//手机号码是否公开
	private int portletCol;
	private int portletLeftWidth;//左边
	private int portletCenterWidth;//中间
	private int portletRightWidth;//右边
	private Integer emailCapacity;//内部邮箱容量
	private Integer folderCapacity;//个人文件柜容量  
	private String userRoleOtherId;//辅助角色uuid串,-->userRoleOther
	private String userRoleOtherName;//
	private String passwordIsNUll;
	
	
	public String getPerformCode() {
		return performCode;
	}

	public void setPerformCode(String performCode) {
		this.performCode = performCode;
	}

	public String getPasswordIsNUll() {
		return passwordIsNUll;
	}
	public void setPasswordIsNUll(String passwordIsNUll) {
		this.passwordIsNUll = passwordIsNUll;
	}
	public Integer getPostPriv() {
		return postPriv;
	}
	public void setPostPriv(Integer postPriv) {
		this.postPriv = postPriv;
	}
	public String getDeptIdOtherStr() {
		return deptIdOtherStr;
	}
	public void setDeptIdOtherStr(String deptIdOtherStr) {
		this.deptIdOtherStr = deptIdOtherStr;
	}
	public String getDeptIdOtherStrName() {
		return deptIdOtherStrName;
	}
	public void setDeptIdOtherStrName(String deptIdOtherStrName) {
		this.deptIdOtherStrName = deptIdOtherStrName;
	}
	public Date getLastVisitTime() {
		return lastVisitTime;
	}
	public void setLastVisitTime(Date lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}
	public Date getLastPassTime() {
		return lastPassTime;
	}
	public void setLastPassTime(Date lastPassTime) {
		this.lastPassTime = lastPassTime;
	}
	public String getUserRoleOtherId() {
		return userRoleOtherId;
	}
	public void setUserRoleOtherId(String userRoleOtherId) {
		this.userRoleOtherId = userRoleOtherId;
	}
	public String getUserRoleOtherName() {
		return userRoleOtherName;
	}
	public void setUserRoleOtherName(String userRoleOtherName) {
		this.userRoleOtherName = userRoleOtherName;
	}
	private Integer userNo;//用户排序号
	
	public Integer getEmailCapacity() {
		return emailCapacity;
	}
	public void setEmailCapacity(Integer emailCapacity) {
		this.emailCapacity = emailCapacity;
	}
	public Integer getFolderCapacity() {
		return folderCapacity;
	}
	public void setFolderCapacity(Integer folderCapacity) {
		this.folderCapacity = folderCapacity;
	}
	public Integer getUserNo() {
		return userNo;
	}
	public void setUserNo(Integer userNo) {
		this.userNo = userNo;
	}
	private String notLogin;//禁止登录OA系统 
	private String notViewUser;//禁止查看用户列表
	private String notViewTable;//禁止显示界面
	private String notSearch;//禁止oa查询
	private String bkground;//桌面背景图片
	private String bindIp;//绑定IP地址
	private String bindMac;//绑定Mac地址
	private String lastVisitIp;//上次访问系统的IP
	private String menuImage;//菜单图标
	private String showWeather;//是否显示天气
	private String weatherCity;//天气预报城市（默认城市)
	private String showRss;//是否显示今日资讯
	private String remark;//备注
	private String menuExpand;//默认展开菜单
	private Integer webmailCapacity;//每个Internet邮箱容量
	private Integer webmailNum; //Internet邮箱数量 为空表示不限制个数
	private String usePops;//是否开始pop
	
	private String desktop;
	
	public String getDesktop() {
		return desktop;
	}

	public void setDesktop(String desktop) {
		this.desktop = desktop;
	}

	public Integer getWebmailCapacity() {
		return webmailCapacity;
	}
	public void setWebmailCapacity(Integer webmailCapacity) {
		this.webmailCapacity = webmailCapacity;
	}
	public Integer getWebmailNum() {
		return webmailNum;
	}
	public void setWebmailNum(Integer webmailNum) {
		this.webmailNum = webmailNum;
	}
	private String myStatus;//
	private String menuGroupsStr;//菜单组uuid字符窜，逗号分隔
	private String menuGroupsStrName;//菜单组nama字符串
	public int getUuid() {
		return uuid;
	}
	public void setUuid(int uuid) {
		this.uuid = uuid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getByname() {
		return byname;
	}
	public void setByname(String byname) {
		this.byname = byname;
	}
	public String getUseingKey() {
		return useingKey;
	}
	public void setUseingKey(String useingKey) {
		this.useingKey = useingKey;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getKeySN() {
		return keySN;
	}
	public void setKeySN(String keySN) {
		this.keySN = keySN;
	}
	public String getSecureKeySn() {
		return secureKeySn;
	}
	public void setSecureKeySn(String secureKeySn) {
		this.secureKeySn = secureKeySn;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getTelNoDept() {
		return telNoDept;
	}
	public void setTelNoDept(String telNoDept) {
		this.telNoDept = telNoDept;
	}
	public String getFaxNoDept() {
		return faxNoDept;
	}
	public void setFaxNoDept(String faxNoDept) {
		this.faxNoDept = faxNoDept;
	}
	public String getAddHome() {
		return addHome;
	}
	public void setAddHome(String addHome) {
		this.addHome = addHome;
	}
	public String getPostNoHome() {
		return postNoHome;
	}
	public void setPostNoHome(String postNoHome) {
		this.postNoHome = postNoHome;
	}
	public String getTelNoHome() {
		return telNoHome;
	}
	public void setTelNoHome(String telNoHome) {
		this.telNoHome = telNoHome;
	}
	public String getMobilNo() {
		return mobilNo;
	}
	public void setMobilNo(String mobilNo) {
		this.mobilNo = mobilNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOicqNo() {
		return oicqNo;
	}
	public void setOicqNo(String oicqNo) {
		this.oicqNo = oicqNo;
	}
	public String getIcq_no() {
		return icq_no;
	}
	public void setIcq_no(String icq_no) {
		this.icq_no = icq_no;
	}
	public String getMSN() {
		return MSN;
	}
	public void setMSN(String mSN) {
		MSN = mSN;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getCallSound() {
		return callSound;
	}
	public void setCallSound(String callSound) {
		this.callSound = callSound;
	}
/*	public String getLastVisitTime() {
		return lastVisitTime;
	}
	public void setLastVisitTime(String lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}*/
	public String getSmsOn() {
		return smsOn;
	}
	public void setSmsOn(String smsOn) {
		this.smsOn = smsOn;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
/*	public String getLastPassTime() {
		return lastPassTime;
	}
	public void setLastPassTime(String lastPassTime) {
		this.lastPassTime = lastPassTime;
	}*/
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getShortcut() {
		return shortcut;
	}
	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}
	public String getPanel() {
		return panel;
	}
	public void setPanel(String panel) {
		this.panel = panel;
	}
	public String getOnStatus() {
		return onStatus;
	}
	public void setOnStatus(String onStatus) {
		this.onStatus = onStatus;
	}
	public String getMobilNoHidden() {
		return mobilNoHidden;
	}
	public void setMobilNoHidden(String mobilNoHidden) {
		this.mobilNoHidden = mobilNoHidden;
	}

	public int getPortletCol() {
		return portletCol;
	}
	public void setPortletCol(int portletCol) {
		this.portletCol = portletCol;
	}
	public int getPortletLeftWidth() {
		return portletLeftWidth;
	}
	public void setPortletLeftWidth(int portletLeftWidth) {
		this.portletLeftWidth = portletLeftWidth;
	}
	public int getPortletCenterWidth() {
		return portletCenterWidth;
	}
	public void setPortletCenterWidth(int portletCenterWidth) {
		this.portletCenterWidth = portletCenterWidth;
	}
	public int getPortletRightWidth() {
		return portletRightWidth;
	}
	public void setPortletRightWidth(int portletRightWidth) {
		this.portletRightWidth = portletRightWidth;
	}
	public String getNotLogin() {
		return notLogin;
	}
	public void setNotLogin(String notLogin) {
		this.notLogin = notLogin;
	}
	public String getNotViewUser() {
		return notViewUser;
	}
	public void setNotViewUser(String notViewUser) {
		this.notViewUser = notViewUser;
	}
	public String getBkground() {
		return bkground;
	}
	public void setBkground(String bkground) {
		this.bkground = bkground;
	}
	public String getBindIp() {
		return bindIp;
	}
	public void setBindIp(String bindIp) {
		this.bindIp = bindIp;
	}
	
	public String getBindMac() {
		return bindMac;
	}

	public void setBindMac(String bindMac) {
		this.bindMac = bindMac;
	}

	public String getLastVisitIp() {
		return lastVisitIp;
	}
	public void setLastVisitIp(String lastVisitIp) {
		this.lastVisitIp = lastVisitIp;
	}
	public String getMenuImage() {
		return menuImage;
	}
	public void setMenuImage(String menuImage) {
		this.menuImage = menuImage;
	}
	public String getWeatherCity() {
		return weatherCity;
	}
	public void setWeatherCity(String weatherCity) {
		this.weatherCity = weatherCity;
	}
	public String getShowRss() {
		return showRss;
	}
	public void setShowRss(String showRss) {
		this.showRss = showRss;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMenuExpand() {
		return menuExpand;
	}
	public void setMenuExpand(String menuExpand) {
		this.menuExpand = menuExpand;
	}
	public String getMyStatus() {
		return myStatus;
	}
	public void setMyStatus(String myStatus) {
		this.myStatus = myStatus;
	}
	public String getMenuGroupsStr() {
		return menuGroupsStr;
	}
	public void setMenuGroupsStr(String menuGroupsStr) {
		this.menuGroupsStr = menuGroupsStr;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDutyType() {
		return dutyType;
	}
	public void setDutyType(String dutyType) {
		this.dutyType = dutyType;
	}
	public String getNotSearch() {
		return notSearch;
	}
	public void setNotSearch(String notSearch) {
		this.notSearch = notSearch;
	}
	public String getNotViewTable() {
		return notViewTable;
	}
	public void setNotViewTable(String notViewTable) {
		this.notViewTable = notViewTable;
	}
	public String getUsePops() {
		return usePops;
	}
	public void setUsePops(String usePops) {
		this.usePops = usePops;
	}
	public String getMenuGroupsStrName() {
		return menuGroupsStrName;
	}
	public void setMenuGroupsStrName(String menuGroupsStrName) {
		this.menuGroupsStrName = menuGroupsStrName;
	}
	public String getShowWeather() {
		return showWeather;
	}
	public void setShowWeather(String showWeather) {
		this.showWeather = showWeather;
	}
	public String getBirthdayStr() {
		return birthdayStr;
	}
	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}
	public String getUserOnlineStatus() {
		return userOnlineStatus;
	}
	public void setUserOnlineStatus(String userOnlineStatus) {
		this.userOnlineStatus = userOnlineStatus;
	}
	public String getDeleteStatus() {
		return deleteStatus;
	}
	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public int getAutoPopSms() {
		return autoPopSms;
	}

	public void setAutoPopSms(int autoPopSms) {
		this.autoPopSms = autoPopSms;
	}

	public String getWeixinNo() {
		return weixinNo;
	}

	public void setWeixinNo(String weixinNo) {
		this.weixinNo = weixinNo;
	}

	public String getLeaderIds() {
		return leaderIds;
	}

	public void setLeaderIds(String leaderIds) {
		this.leaderIds = leaderIds;
	}

	public String getLeaderNames() {
		return leaderNames;
	}

	public void setLeaderNames(String leaderNames) {
		this.leaderNames = leaderNames;
	}

	public String getUnderlingNames() {
		return underlingNames;
	}

	public void setUnderlingNames(String underlingNames) {
		this.underlingNames = underlingNames;
	}

	public String getUnderlingIds() {
		return underlingIds;
	}

	public void setUnderlingIds(String underlingIds) {
		this.underlingIds = underlingIds;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public int getViewPriv() {
		return viewPriv;
	}

	public void setViewPriv(int viewPriv) {
		this.viewPriv = viewPriv;
	}

	public String getViewDeptIds() {
		return viewDeptIds;
	}

	public void setViewDeptIds(String viewDeptIds) {
		this.viewDeptIds = viewDeptIds;
	}

	public String getViewDeptNames() {
		return viewDeptNames;
	}

	public void setViewDeptNames(String viewDeptNames) {
		this.viewDeptNames = viewDeptNames;
	}

	private String notWebLogin;//禁止WEB登录
	private String notMobileLogin;//禁止移动端登录
	private String notPcLogin;//禁止PC端登录
	
	public String getNotWebLogin() {
		return notWebLogin;
	}

	public void setNotWebLogin(String notWebLogin) {
		this.notWebLogin = notWebLogin;
	}

	public String getNotMobileLogin() {
		return notMobileLogin;
	}

	public void setNotMobileLogin(String notMobileLogin) {
		this.notMobileLogin = notMobileLogin;
	}

	public String getNotPcLogin() {
		return notPcLogin;
	}

	public void setNotPcLogin(String notPcLogin) {
		this.notPcLogin = notPcLogin;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public int getAttachId() {
		return attachId;
	}

	public void setAttachId(int attachId) {
		this.attachId = attachId;
	}

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
