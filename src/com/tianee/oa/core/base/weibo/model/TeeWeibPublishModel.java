package com.tianee.oa.core.base.weibo.model;

import java.util.ArrayList;
import java.util.List;

public class TeeWeibPublishModel {

    private int sid;//发布信息主键
	
	private String content;//发布内容
	
	private String img;//上传图片id字符串
	
	private int count;//点赞次数
	
	private int userId;//发布者id
	
	private String userName;//发布者姓名
	
	private int num;//评论次数
	
	private int number;//转发次数
	
	private String createTime;//发布时间
	
	private boolean dianzan=false;//判断当前登录人是否点赞
	
	private boolean collect=false;//判断当前登录人是否收藏
	
	private String zfCotent;//转发内容
	
	private int avatar;//头像
	
	private String imgy;//原图id字符串
	
	private List<TeeWeibCommentModel> ctList=new ArrayList<TeeWeibCommentModel>();//微博信息的评论集合
	private List<TeeWeibDianZaiModel> dzList=new ArrayList<TeeWeibDianZaiModel>();//微博信息的点赞集合
	private List<TeeWeibRelayModel> rlList=new ArrayList<TeeWeibRelayModel>();//微博信息的转发集合
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isDianzan() {
		return dianzan;
	}

	public void setDianzan(boolean dianzan) {
		this.dianzan = dianzan;
	}

	public boolean isCollect() {
		return collect;
	}

	public void setCollect(boolean collect) {
		this.collect = collect;
	}

	public List<TeeWeibCommentModel> getCtList() {
		return ctList;
	}

	public void setCtList(List<TeeWeibCommentModel> ctList) {
		this.ctList = ctList;
	}

	public String getZfCotent() {
		return zfCotent;
	}

	public void setZfCotent(String zfCotent) {
		this.zfCotent = zfCotent;
	}

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}

	public List<TeeWeibDianZaiModel> getDzList() {
		return dzList;
	}

	public void setDzList(List<TeeWeibDianZaiModel> dzList) {
		this.dzList = dzList;
	}

	public List<TeeWeibRelayModel> getRlList() {
		return rlList;
	}

	public void setRlList(List<TeeWeibRelayModel> rlList) {
		this.rlList = rlList;
	}

	public String getImgy() {
		return imgy;
	}

	public void setImgy(String imgy) {
		this.imgy = imgy;
	}

	


}
