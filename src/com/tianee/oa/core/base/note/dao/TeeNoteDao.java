package com.tianee.oa.core.base.note.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.note.bean.TeeNote;
import com.tianee.oa.core.base.note.model.TeeNoteModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author syl
 *
 */
@Repository("noteDao")
public class TeeNoteDao  extends TeeBaseDao<TeeNote> {
	/**
	 * @author syl
	 * 增加
	 * @param TeeNote
	 */
	public void addNote(TeeNote note) {
		save(note);
	}
	
	/**
	 * @author syl
	 * 更新
	 * @param TeeNote
	 */
	public void updateNote(TeeNote note) {
		update(note);
	}
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeNote loadById(int id) {
		TeeNote intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeNote getById(int id) {
		TeeNote intf = get(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 删除
	 * @param 
	 */
	public void delById(int id) {
		delete(id);
	}
	
	/**
	 * 删除 by Ids
	 * 
	 * @author syl
	 * @date 2014-1-6
	 * @param ids
	 */
	public void delByIds(String ids){
		
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids= ids.substring(0, ids.length() -1 );
			}
			String hql = "delete from TeeNote where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * @author syl
	 * 查询个人便签
	 * @param 
	 */
	public  List<TeeNote> selectPersonalNote(TeePerson person , TeeNoteModel model) {
		Object[] values = {person.getUuid() };
		String hql = "from TeeNote where user.uuid = ? order by  sid desc";
		List<TeeNote> list = (List<TeeNote>) executeQuery(hql,values);
		return list;
	}	
	

	
}