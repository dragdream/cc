package com.tianee.oa.core.org.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.priv.bean.TeeMenuGroup;
import com.tianee.oa.core.priv.bean.TeeModulePriv;

@Entity
@Table(name="PERSON")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class TeePerson implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="PERSON_seq_gen")
	@SequenceGenerator(name="PERSON_seq_gen", sequenceName="PERSON_seq")
	private int uuid;
	
	@Column(name="USER_ID",length=30)
	private String userId;//用户名英文缩写 -- 系统登录唯一标示
	
	@Column(name="UNIQUE_ID",length=100)
	private String uniqueId;//唯一标示，做系统对接用，例如CA认证、移动手机号唯一标示预留字段

	@Column(name="USER_NAME",length=200)
	private String userName;//用户姓名
	
	@Column(name="PERFORM_CODE",length=20)
	private String performCode;//执法证号，后来加的字段

	@Column(name="BYNAME",length=20)
	private String byname;//别名

	@Column(name="USEING_KEY",length=20,columnDefinition="char(2)")
	private String useingKey;//使用USB KEY登录
	
	@Column(name="pwd",length=50)
	private String password;//用户密码
	
	@Column(name="GSB_USER_ID",length=30)
	private String gsbUserId;//钉钉用户名
	
	@Column(name="GSB_PWD",length=50)
	private String gsbPassword;//微信用户名

	@Column(name="KEY_SN",length=100)
	private String keySN;//KEY码

	@Column(name="SECURE_KEY_SN",length=20)
	private String secureKeySn;//安全KEY码
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX4549336b9abf4a06a7a8b331e27")
	@JoinColumn(name="USER_ROLE")
	private TeeUserRole userRole;//角色编号
	
	
	@ManyToMany(targetEntity=TeeUserRole.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable(       
			joinColumns={@JoinColumn(name="PERSON_UUID")},       
			inverseJoinColumns={@JoinColumn(name="USER_ROLE_UUID")}  ) 	
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	private List<TeeUserRole> userRoleOther;//辅助角色
	
	@Column(name="POST_PRIV")
	private Integer postPriv;//管理范围



	@ManyToMany(targetEntity=TeeDepartment.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="PERSON_DEPARTMENT_POST",        
			joinColumns={@JoinColumn(name="PERSON_UUID")},       
			inverseJoinColumns={@JoinColumn(name="DEPT_UUID")}  )  	
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)	
	private List<TeeDepartment> postDept;//管理范围
	
	@ManyToMany(targetEntity=TeeDepartment.class,   //多对多     
			fetch = FetchType.EAGER  ) 
			@JoinTable(       
			joinColumns={@JoinColumn(name="PERSON_UUID")},       
			inverseJoinColumns={@JoinColumn(name="DEPT_UUID")}  ) 	 	
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	private List<TeeDepartment> deptIdOther;//辅助部门
	

	@Column(name="SEX")
	private String sex;
	
	@Temporal(TemporalType.DATE)
	private Date birthday;//生日

	@Column(name="TEL_NO_DEPT",length=50)
	private String telNoDept;//工作电话
	
	@Column(name="FAX_NO_DEPT",length=50)
	private String faxNoDept;//工作传真
	
	@Column(name="ADD_HOME",length=200)
	private String addHome;//家庭住址
	
	@Column(name="POST_NO_HOME",length=50)
	private String postNoHome;//家庭邮编
	
	@Column(name="TEL_NO_HOME",length=50)
	private String telNoHome;//家庭电话

	@Column(name="MOBIL_NO",length=50)
	private String mobilNo;//手机号
	
	@Column(name="EMAIL",length=50)
	private String email;//电子邮件地址
	
	@Column(name="OICQ_NO",length=50)
	private String oicqNo;//QQ号码

	
	@Column(name="ICQ_NO",length=50)
	private String icq_no;//ICQ号码
	
	@Column(name="WEIXIN_NO",length=50)
	private String weixinNo;//微信号码

	@Column(name="MSN",length=200)
	private String MSN;//MSN号码
	
	@Column(name="NICK_NAME",length=50)
	private String nickName;//昵称

	
	@Column(name="AVATAR",length=50)
	private String avatar;//自定义头像

	
	@Column(name="CALL_SOUND",columnDefinition="char(2)")
	private String callSound;//短信提示音

	@Column(name="DUTY_TYPE")
	private String dutyType;//考勤排班类型
	
	@Column(name="SIGN_WAY")
	private String signWay;//考勤签到方式

	@Column(name="SMS_ON",columnDefinition="char(1)")
	private String smsOn;//短信提醒窗口弹出方式

	@Column(name="MENU_TYPE",columnDefinition="char(1)")
	private String menuType;//登录模式

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_PASS_TIME")
	private Date lastPassTime;//上次修改密码的时间

	@Column(name="THEME",length=10)
	private String theme;//界面主题

	@Column(name="SHORTCUT")
	private String shortcut;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX2a8c44bb3d424d53846bf3a66fd")
	@JoinColumn(name="DEPT_ID")
	private TeeDepartment dept;
	
	@Column(name="PANEL",columnDefinition="char(1)")
	private String  panel;//登录后显示的左侧面板

	@Column(name="ON_STATUS",columnDefinition="char(1)")
	private String onStatus;
	
	@Column(name="MAIN_ONLINE_STATUS",columnDefinition="INT default 0" ,nullable=false)
	private int mainOnlineStatus;//主开关在线状况 0 - 不在线 1-在线
	
	@Column(name="ISPIRIT_ONLINE_STATUS",columnDefinition="INT default 0" ,nullable=false)
	private int ispiritOnlineStatus;//精灵在线状况 0 - 不在线 1-在线

	@Column(name="MOBIL_NO_HIDDEN",columnDefinition="char(1)")
	private String mobilNoHidden;//手机号码是否公开

	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="DYNAMIC_INFO_ID")
	private TeePersonDynamicInfo dynamicInfo;
	
	@Column(name="PORTLET_COL")
	private int portletCol;

	@Column(name="PORTLET_LEFT_WIDTH")
	private int portletLeftWidth;//左边
	

	@Column(name="PORTLET_CENTER_WIDTH")
	private int portletCenterWidth;//中间
	
	@Column(name="PORTLET_RIGHT_WIDTH")
	private int portletRightWidth;//右边
	

	@Column(name="EMAIL_CAPACITY")
	private Integer emailCapacity;//内部邮箱容量

	@Column(name="FOLDER_CAPACITY")
	private Integer folderCapacity;//个人文件柜容量


	@Column(name="USER_NO")
	private Integer userNo;//用户排序号

	@Column(name="NOT_LOGIN",length=20)
	private String notLogin;//禁止登录OA系统
	
	@Column(name="NOT_WEB_LOGIN",length=20)
	private String notWebLogin;//禁止WEB登录
	
	@Column(name="NOT_MOBILE_LOGIN",length=20)
	private String notMobileLogin;//禁止移动端登录
	
	@Column(name="NOT_PC_LOGIN",length=20)
	private String notPcLogin;//禁止PC端登录
	
	@Column(name="DELETE_STATUS",columnDefinition="char(1) default '0'")
	private String deleteStatus;//删除状态

	
	@Column(name="NOT_VIEW_USER",length=20)
	private String notViewUser;//禁止查看用户列表
	
	@Column(name="NOT_VIEW_TABLE",length=20)
	private String notViewTable;//禁止显示界面
	
	@Column(name="NOT_SEARCH",length=20)
	private String notSearch;//禁止oa查询

	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="BKGROUND")
	private String bkground;//统计图地址

	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="BIND_IP")
	private String bindIp;//绑定IP地址
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="BIND_MAC")
	private String bindMac;//绑定Mac地址

	@Column(name="MENU_IMAGE",length=20)
	private String menuImage;//菜单图标

	
	@Column(name="SHOW_WEATHER",length=10)
	private String showWeather;//是否显示天气
	
	@Column(name="WEATHER_CITY",length=20)
	private String weatherCity;//天气预报城市（默认城市)
	
	@Column(name="SHOW_RSS",columnDefinition="char(1)")
	private String showRss;//是否显示今日资讯

	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="REMARK")
	private String remark;//备注

	@Column(name="MENU_EXPAND",columnDefinition="char(20)")
	private String menuExpand;//默认展开菜单

	@Column(name="WEBMAIL_CAPACITY")
	private Integer webmailCapacity;//每个Internet邮箱容量

	@Column(name="WEBMAIL_NUM")
	private Integer webmailNum; //Internet邮箱数量 为空表示不限制个数

	@Column(name="MY_STATUS",length=20)
	private String myStatus;//
	
	@Column(name="USE_POPS",length=20)
	private String usePops;//
	
	@Column(name="IS_ADMIN",length=1)
	private String isAdmin;	
	
	@ManyToMany(targetEntity=TeeMenuGroup.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable(name="PERSON_MENU_GROUP",
			joinColumns={@JoinColumn(name="PERSON_UUID")},       
			inverseJoinColumns={@JoinColumn(name="MENU_GROUP_UUID")}  )  	
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)	
	private List<TeeMenuGroup> menuGroups;//菜单组
	
	
	@OneToMany(mappedBy="userId",fetch=FetchType.LAZY)//这是双向的 要写JoinColumn 对面要写 mappedBy  ,在对方的 	
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	private List<TeeModulePriv> modulePriv;// 按模块设置权限
	
	@Lob
	@Column(name="MENU_PARAM_SET")
	private String menuParamSet;
	
	@Lob
	@Column(name="DESKTOP")
	private String desktop;//默认桌面模板
	
	@Column(name="AUTO_POP_SMS" ,columnDefinition="INT default 1" )
	private int autoPopSms;//自动弹出事务提醒
	
//	@ManyToMany(targetEntity=TeePerson.class,   //多对多     
//			fetch = FetchType.LAZY  ) 
//			@JoinTable(name="PERSON_LEADERS",
//			joinColumns={@JoinColumn(name="PERSON_UUID")},       
//			inverseJoinColumns={@JoinColumn(name="LEADER_UUID")}  ) 	 	
//	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
//	private List<TeePerson> leaders = new ArrayList(0);//直属上级
	
//	@ManyToMany(targetEntity=TeePerson.class,   //多对多     
//			fetch = FetchType.LAZY  ) 
//			@JoinTable(name="PERSON_UNDERLING",
//			joinColumns={@JoinColumn(name="PERSON_UUID")},       
//			inverseJoinColumns={@JoinColumn(name="UNDERLING_UUID")}  ) 	 	
//	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
//	private List<TeePerson> underlings = new ArrayList(0);//直属下级
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDXxxx9336b9abxxx06a7a8b331e27")
	@JoinColumn(name="LEADER_ID")
	private TeePerson leader;//直属上级
	
	@Column(name="DEVICE_ID")
	private String deviceId;//设备号绑定
	
	@Column(name="VIEW_PRIV")
	private int viewPriv = 1;//可见范围  1：本机构  2：指定部门  3:本部门  4：本部门及以下部门

	@Lob
	@Column(name="VIEW_DEPT")
	private String viewDept = "";//可见范围部门ID字符串
	
	@Column(name="CERT_UNIQUE_ID")
	private String certUniqueId;//CA认证唯一标识   身份证号
	
	
	@Column(name="QUICK_MENU_IDS")
	private String quickMenuIds;//快捷菜单id字符串
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ATTACH_ID")
	private TeeAttachment attach;//个人签名图片
	
	
	
	
	
	public String getQuickMenuIds() {
		return quickMenuIds;
	}

	public void setQuickMenuIds(String quickMenuIds) {
		this.quickMenuIds = quickMenuIds;
	}

	public String getPerformCode() {
		return performCode;
	}

	public void setPerformCode(String performCode) {
		this.performCode = performCode;
	}

	public int getViewPriv() {
		return viewPriv;
	}

	public void setViewPriv(int viewPriv) {
		this.viewPriv = viewPriv;
	}

	public String getViewDept() {
		return viewDept;
	}

	public void setViewDept(String viewDept) {
		this.viewDept = viewDept;
	}

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

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
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

	public TeeUserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(TeeUserRole userRole) {
		this.userRole = userRole;
	}

	public Integer getPostPriv() {
		return postPriv;
	}

	public void setPostPriv(Integer postPriv) {
		this.postPriv = postPriv;
	}

	public List<TeeDepartment> getPostDept() {
		return postDept;
	}

	public void setPostDept(List<TeeDepartment> postDept) {
		this.postDept = postDept;
	}

	public List<TeeDepartment> getDeptIdOther() {
		return deptIdOther;
	}

	public void setDeptIdOther(List<TeeDepartment> deptIdOther) {
		this.deptIdOther = deptIdOther;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public void setMSN(String msn) {
		MSN = msn;
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

	public String getDutyType() {
		return dutyType;
	}

	public void setDutyType(String dutyType) {
		this.dutyType = dutyType;
	}

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

	public Date getLastPassTime() {
		return lastPassTime;
	}

	public void setLastPassTime(Date lastPassTime) {
		this.lastPassTime = lastPassTime;
	}

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

	public TeeDepartment getDept() {
		return dept;
	}

	public void setDept(TeeDepartment dept) {
		this.dept = dept;
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


	public void setPortletCol(int portletCol) {
		this.portletCol = portletCol;
	}

	public void setPortletLeftWidth(int portletLeftWidth) {
		this.portletLeftWidth = portletLeftWidth;
	}

	public void setPortletCenterWidth(int portletCenterWidth) {
		this.portletCenterWidth = portletCenterWidth;
	}
	
	

	public int getPortletCol() {
		return portletCol;
	}

	public int getPortletLeftWidth() {
		return portletLeftWidth;
	}

	public int getPortletCenterWidth() {
		return portletCenterWidth;
	}

	public int getPortletRightWidth() {
		return portletRightWidth;
	}

	public void setPortletRightWidth(int portletRightWidth) {
		this.portletRightWidth = portletRightWidth;
	}

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

	public List<TeeUserRole> getUserRoleOther() {
		return userRoleOther;
	}

	public void setUserRoleOther(List<TeeUserRole> userRoleOther) {
		this.userRoleOther = userRoleOther;
	}

	public Integer getUserNo() {
		return userNo;
	}

	public void setUserNo(Integer userNo) {
		this.userNo = userNo;
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

	public String getNotViewTable() {
		return notViewTable;
	}

	public void setNotViewTable(String notViewTable) {
		this.notViewTable = notViewTable;
	}

	public String getNotSearch() {
		return notSearch;
	}

	public void setNotSearch(String notSearch) {
		this.notSearch = notSearch;
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

	public String getMyStatus() {
		return myStatus;
	}

	public void setMyStatus(String myStatus) {
		this.myStatus = myStatus;
	}

	public String getUsePops() {
		return usePops;
	}

	public void setUsePops(String usePops) {
		this.usePops = usePops;
	}

	public List<TeeMenuGroup> getMenuGroups() {
		return menuGroups;
	}

	public void setMenuGroups(List<TeeMenuGroup> menuGroups) {
		this.menuGroups = menuGroups;
	}

	public String getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public String getShowWeather() {
		return showWeather;
	}

	public void setShowWeather(String showWeather) {
		this.showWeather = showWeather;
	}

	public List<TeeModulePriv> getModulePriv() {
		return modulePriv;
	}

	public void setModulePriv(List<TeeModulePriv> modulePriv) {
		this.modulePriv = modulePriv;
	}

	public void setMainOnlineStatus(int mainOnlineStatus) {
		this.mainOnlineStatus = mainOnlineStatus;
	}

	public int getIspiritOnlineStatus() {
		return ispiritOnlineStatus;
	}

	public void setIspiritOnlineStatus(int ispiritOnlineStatus) {
		this.ispiritOnlineStatus = ispiritOnlineStatus;
	}

	public int getMainOnlineStatus() {
		return mainOnlineStatus;
	}

	public String getMenuParamSet() {
		return menuParamSet;
	}

	public void setMenuParamSet(String menuParamSet) {
		this.menuParamSet = menuParamSet;
	}

	public String getDesktop() {
		return desktop;
	}

	public void setDesktop(String desktop) {
		this.desktop = desktop;
	}

	public TeePersonDynamicInfo getDynamicInfo() {
		return dynamicInfo;
	}

	public void setDynamicInfo(TeePersonDynamicInfo dynamicInfo) {
		this.dynamicInfo = dynamicInfo;
	}

	public String getWeixinNo() {
		return weixinNo;
	}

	public void setWeixinNo(String weixinNo) {
		this.weixinNo = weixinNo;
	}

	public String getBindMac() {
		return bindMac;
	}

	public void setBindMac(String bindMac) {
		this.bindMac = bindMac;
	}

	public int getAutoPopSms() {
		return autoPopSms;
	}

	public void setAutoPopSms(int autoPopSms) {
		this.autoPopSms = autoPopSms;
	}

//	public List<TeePerson> getLeaders() {
//		return leaders;
//	}
//
//	public void setLeaders(List<TeePerson> leaders) {
//		this.leaders = leaders;
//	}
//
//	public List<TeePerson> getUnderlings() {
//		return underlings;
//	}
//
//	public void setUnderlings(List<TeePerson> underlings) {
//		this.underlings = underlings;
//	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getGsbUserId() {
		return gsbUserId;
	}

	public void setGsbUserId(String gsbUserId) {
		this.gsbUserId = gsbUserId;
	}

	public String getGsbPassword() {
		return gsbPassword;
	}

	public void setGsbPassword(String gsbPassword) {
		this.gsbPassword = gsbPassword;
	}
	
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

	public TeePerson getLeader() {
		return leader;
	}

	public void setLeader(TeePerson leader) {
		this.leader = leader;
	}

	public String getSignWay() {
		return signWay;
	}

	public void setSignWay(String signWay) {
		this.signWay = signWay;
	}

	public String getCertUniqueId() {
		return certUniqueId;
	}

	public void setCertUniqueId(String certUniqueId) {
		this.certUniqueId = certUniqueId;
	}

	public TeeAttachment getAttach() {
		return attach;
	}

	public void setAttach(TeeAttachment attach) {
		this.attach = attach;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
