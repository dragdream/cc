package com.beidasoft.xzzf.punish.video.dao;


import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.classicCase.bean.ClassicCase;
import com.beidasoft.xzzf.punish.video.bean.VideoManagement;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 执法视频表DAO类
 */
@Repository
public class VideoManagementDao extends TeeBaseDao<VideoManagement> {
	   
	/**
	 * 保存
	 */
	public void saveVideoManagement(VideoManagement videoManagement) {
		super.saveOrUpdate(videoManagement);
	}
	
}
