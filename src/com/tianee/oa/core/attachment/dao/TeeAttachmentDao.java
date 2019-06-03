package com.tianee.oa.core.attachment.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import sun.java2d.pipe.SpanShapeRenderer.Simple;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;
@Repository
public class TeeAttachmentDao  extends TeeBaseDao<TeeAttachment>{

	/**
	 * 增加附件
	 * @param attach
	 */
	public void addAttac(TeeAttachment attach) {
		save(attach);	
	}
	
	/**
	 * 更新 附件
	 * @param attach
	 */
	public void updateOrg(TeeAttachment attach) {
		update(attach);	
	}
	
	public List<TeeAttachment> getAttaches(String model,String modelId){
		return find("from TeeAttachment attach where attach.model=? and attach.modelId=?", new Object[]{model,modelId});
	}
	
	public long getAttachesCount(String model,String modelId){
		return count("select count(attach.sid) from TeeAttachment attach where attach.model=? and attach.modelId=?", new Object[]{model,modelId});
	}
	/**
	 * 查询 附件
	 * @author zhp
	 * @createTime 2013-10-6
	 * @editTime 下午01:56:19
	 * @desc
	 */
	public List<TeeAttachment> getAttachmentsByIds(String attachIds){
		if(TeeUtility.isNullorEmpty(attachIds)){
			return new ArrayList<TeeAttachment>();
		}
		if(attachIds.endsWith(",")){
			attachIds = attachIds.substring(0,attachIds.length()-1);
		}
		Session session = this.getSession();
		String hql = "from TeeAttachment ta where ta.sid in ("+attachIds+")";
		Query query = session.createQuery(hql);
		return query.list();
		
	}
	/**
	 * 删除附件
	 * @author zhp
	 * @createTime 2013-10-6
	 * @editTime 下午08:06:35
	 * @desc
	 */
	public void deleteAttachs(String ids){
		Session session = this.getSession();
		String hql = "delete from TeeAttachment ta where ta.sid in ("+ids+")";
		Query query = session.createQuery(hql);
		query.executeUpdate();
	}
}
