package com.beidasoft.xzzf.punish.document.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.document.bean.DocDiscussionPersonSignature;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeJson;

@Repository
public class DiscussionPersonSignatureDao extends TeeBaseDao<DocDiscussionPersonSignature>{

	/**
	 * 遍历子表数据
	 * @param id
	 * @return
	 */
	public List<DocDiscussionPersonSignature> getByGroupId(String groupId){
		Session session = this.getSession();
		 String hql = "from DocDiscussionPersonSignature where groupId =:groupId order by personNo+0 ";
		@SuppressWarnings("unchecked")
		List<DocDiscussionPersonSignature> listDocDiscussionPersonSignature = session.createQuery(hql).setString("groupId", groupId).list();
		return listDocDiscussionPersonSignature;
	}
	
	/**
	 * 删除子表数据
	 * @param groupId
	 */
	public void del(String groupId){
		 Session session = this.getSession();
		 String hql = "Delete DocDiscussionPersonSignature Where groupId =:groupId ";
		 session.createQuery(hql).setString("groupId", groupId).executeUpdate();
	}
	
	/**
	 * 通过UUID 查 子表实体类
	 * 
	 * @param personUUID
	 * @return
	 */
	public DocDiscussionPersonSignature getPersonSignByUUID(String groupId, String personUUID){
		DocDiscussionPersonSignature signature = new DocDiscussionPersonSignature();

		Session session = this.getSession();
		String hql = "from DocDiscussionPersonSignature where personUUID =:personUUID and groupId =:groupId ";
		signature = (DocDiscussionPersonSignature) session.createQuery(hql)
				.setString("personUUID", personUUID)
				.setString("groupId", groupId).uniqueResult();

		return signature;
	}
	
	/**
	 * 保存或更新子表数据
	 * @param groupId
	 */
	public void saveOrUpdate(DocDiscussionPersonSignature o){
		super.saveOrUpdate(o);;
	}
}
