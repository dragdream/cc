package com.tianee.oa.core.general.bean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;
@SuppressWarnings("serial")
@Entity
@Table(name = "SYS_INTERFACE")
public class TeeInterface  extends TeeBaseModel  {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SYS_INTERFACE_seq_gen")
	@SequenceGenerator(name="SYS_INTERFACE_seq_gen", sequenceName="SYS_INTERFACE_seq")
	private int sid;//自增id

	@Column(name="IE_TITLE",length=100)
	private String ieTitle;//IE_TITLE IE浏览器窗口标题
	
	@Lob 
	@Column(name="BOTTOM_STATUS_TEXT", nullable=true)
	private String bottomStatusText ;//BOTTOM_STATUS_TEXT主界面-底部状态栏置中文字

	@Column(name="TOP_BANNER_TEXT",length=200)//顶部大标题文字
	private String topBannerText;//TOP_BANNER_TEXT
	
	@Column(name="TOP_BANNER_FONT",length=200)//顶部大标题文字样式
	private String topBannerFont ;//rTOP_BANNER_FONT ,
	
	@Column(name="TOP_ATTACHMENT_ID",length=200)//
	private String topAttachmentId;//TOP_ATTACHMENT_ID  顶部图片Id  TeeAttament.sid

	@Column(name="ATTACHMENT_ID",length=200)
	private String attachmentId;//
	
	@Column(name="ATTACHMENT_NAME",length=200)
	private String attachmentName;//
	
	
	@Column(name="TOP_IMG_WIDTH")
	private int topImgWidth;//TOP_IMG_WIDTH
	
	@Column(name="TOP_IMG_HeIGHT")
	private int topImgHeight;//TOP_IMG_WIDTH
	
	@Column(name="AVATAR_UPLOAD")
	private int avatarUpload;//AVATAR_UPLOAD 允许用户上传头像
	
	@Column(name="AVATAR_WIDTH")
	private int avatarWidth;//AVATAR_WIDTH 用户上传头像最大宽度
	
	@Column(name="AVATAR_HEIGHT")
	private int avatarHeight;//AVATAR_HEIGHT 用户上传头像最大高度
	
	@Column(name="LOGIN_INTERFACE",length=10)
	private String loginInterface;//LOGIN_INTERFACE  选择界面布局  1-允许  0-不允许
	
	@Column(name="UI",length=10)
	private String ui;//UI 默认界面布局
	
	@Column(name="THEME_SELECT",length=100)
	private String themeSelect;//THEME_SELECT 是否允许用户选择界面主题  1-允许  0-不允许
	
	
	@Column(name="THEME",length=100)
	private String theme;//THEME 默认界面主题
	
	@Column(name="TEMPLATE",length=100)
	private String temPlate;//TEMPLATE 登录界面模板
	
	@Column(name="WEATHER_CITY",length=100)
	private String weatherCity;//WEATHER_CITY 双重含义：1、是否允许用户使用天气预报功能  2、天气预报的默认城市代码
	
	
	@Column(name="SHOW_RSS",length=100)
	private String showRss;//SHOW_RSS 允许用户使用今日资讯  0-不允许  1-允许
	
	@Column(name="IM_PIC",length=100)
	private String imPic;//即时通讯客户端图标
	
	@Column(name="M_LOGO",length=100)
	private String mLogo;//即时通讯客户端图标
	
	@Column(name="M_PIC",length=100)
	private String mPic;//即时通讯客户端图标
	
	@Column(name="WELCOME_PIC",length=100)
	private String welcomePic;//移动端欢迎图片
	
	@Column(name="APP_TOP_BG",length=100)
	private String appTopBg;//移动端签到区背景图
	
	@Column(name="APP_TOP_SIGN_SHOW")
	private int appTopSignShow;//移动端签到区是否显示
	
	@Column(name="APP_INDEX",length=100)
	private String appIndex;//应用首页

	@Lob 
	@Column(name="LOG_OUT_TEXT")
	private String logOutText;//
	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public String getIeTitle() {
		return ieTitle;
	}


	public void setIeTitle(String ieTitle) {
		this.ieTitle = ieTitle;
	}


	public String getBottomStatusText() {
		return bottomStatusText;
	}


	public void setBottomStatusText(String bottomStatusText) {
		this.bottomStatusText = bottomStatusText;
	}


	public String getTopBannerText() {
		return topBannerText;
	}


	public void setTopBannerText(String topBannerText) {
		this.topBannerText = topBannerText;
	}


	public String getTopBannerFont() {
		return topBannerFont;
	}


	public void setTopBannerFont(String topBannerFont) {
		this.topBannerFont = topBannerFont;
	}


	public String getTopAttachmentId() {
		return topAttachmentId;
	}


	public void setTopAttachmentId(String topAttachmentId) {
		this.topAttachmentId = topAttachmentId;
	}


	public String getAttachmentId() {
		return attachmentId;
	}


	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}


	public String getAttachmentName() {
		return attachmentName;
	}


	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}


	public int getTopImgWidth() {
		return topImgWidth;
	}


	public void setTopImgWidth(int topImgWidth) {
		this.topImgWidth = topImgWidth;
	}



	public int getAvatarUpload() {
		return avatarUpload;
	}


	public void setAvatarUpload(int avatarUpload) {
		this.avatarUpload = avatarUpload;
	}


	public int getAvatarWidth() {
		return avatarWidth;
	}


	public void setAvatarWidth(int avatarWidth) {
		this.avatarWidth = avatarWidth;
	}




	public int getTopImgHeight() {
		return topImgHeight;
	}


	public void setTopImgHeight(int topImgHeight) {
		this.topImgHeight = topImgHeight;
	}


	public int getAvatarHeight() {
		return avatarHeight;
	}


	public void setAvatarHeight(int avatarHeight) {
		this.avatarHeight = avatarHeight;
	}


	public String getLoginInterface() {
		return loginInterface;
	}


	public void setLoginInterface(String loginInterface) {
		this.loginInterface = loginInterface;
	}


	public String getUi() {
		return ui;
	}


	public void setUi(String ui) {
		this.ui = ui;
	}


	public String getThemeSelect() {
		return themeSelect;
	}


	public void setThemeSelect(String themeSelect) {
		this.themeSelect = themeSelect;
	}


	public String getTheme() {
		return theme;
	}


	public void setTheme(String theme) {
		this.theme = theme;
	}


	public String getTemPlate() {
		return temPlate;
	}


	public void setTemPlate(String temPlate) {
		this.temPlate = temPlate;
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


	public String getLogOutText() {
		return logOutText;
	}


	public void setLogOutText(String logOutText) {
		this.logOutText = logOutText;
	}


	public String getImPic() {
		return imPic;
	}


	public void setImPic(String imPic) {
		this.imPic = imPic;
	}


	public String getmLogo() {
		return mLogo;
	}


	public void setmLogo(String mLogo) {
		this.mLogo = mLogo;
	}


	public String getmPic() {
		return mPic;
	}


	public void setmPic(String mPic) {
		this.mPic = mPic;
	}


	public String getWelcomePic() {
		return welcomePic;
	}


	public void setWelcomePic(String welcomePic) {
		this.welcomePic = welcomePic;
	}


	public String getAppIndex() {
		return appIndex;
	}


	public void setAppIndex(String appIndex) {
		this.appIndex = appIndex;
	}


	public String getAppTopBg() {
		return appTopBg;
	}


	public void setAppTopBg(String appTopBg) {
		this.appTopBg = appTopBg;
	}


	public int getAppTopSignShow() {
		return appTopSignShow;
	}


	public void setAppTopSignShow(int appTopSignShow) {
		this.appTopSignShow = appTopSignShow;
	}
	
	

	
	
}
