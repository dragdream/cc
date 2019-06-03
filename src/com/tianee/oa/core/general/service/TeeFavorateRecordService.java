package com.tianee.oa.core.general.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.bean.TeeFavoriteRecord;
import com.tianee.oa.core.general.model.TeeFavoriteRecordModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 收藏记录业务实现层
 * @author kakalion
 *
 */
@Service
public class TeeFavorateRecordService extends TeeBaseService{
	
	public void entityToModel(TeeFavoriteRecord favoriteRecord,TeeFavoriteRecordModel favoriteRecordModel){
		BeanUtils.copyProperties(favoriteRecord, favoriteRecordModel);
		favoriteRecordModel.setUserId(favoriteRecord.getUser().getUuid());
	}
	
	public void modelToEntity(TeeFavoriteRecordModel favoriteRecordModel,TeeFavoriteRecord favoriteRecord){
		BeanUtils.copyProperties(favoriteRecordModel,favoriteRecord);
		TeePerson p = (TeePerson) simpleDaoSupport.get(TeePerson.class, favoriteRecordModel.getUserId());
		favoriteRecord.setUser(p);
	}
	
	/**
	 * 添加收藏夹
	 * @param favoriteRecordModel
	 */
	public boolean addFavorateRecord(TeeFavoriteRecordModel favoriteRecordModel){
		TeeFavoriteRecord favoriteRecord = new TeeFavoriteRecord();
		//先获取标题和url与之对应的收藏数据
		String hql = "select count(fr.sid) from TeeFavoriteRecord fr where fr.user.uuid="+favoriteRecordModel.getUserId()+" and fr.url='"+favoriteRecordModel.getUrl()+"'";
		long count = simpleDaoSupport.count(hql, null);
		
		if(count!=0){
			return false;
		}
		
		modelToEntity(favoriteRecordModel,favoriteRecord);
		Date date = new Date();
		favoriteRecord.setOptTime(date.getTime());
		simpleDaoSupport.save(favoriteRecord);
		return true;
	}
	
	/**
	 * 删除收藏夹
	 * @param sid
	 */
	public void deleteFavoriateRecord(int sid){
		simpleDaoSupport.delete(TeeFavoriteRecord.class, sid);
	}
	
	/**
	 * 列出收藏夹Model集合
	 * @param sid
	 */
	public  TeeEasyuiDataGridJson  listModels(int userId,TeeDataGridModel dm){
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		String hql = "from TeeFavoriteRecord fr where fr.user.uuid="+userId+" order by fr.sid asc";
		json.setTotal(simpleDaoSupport.count("select count(*) "+hql, null));
		
		List<TeeFavoriteRecordModel> modelList = new ArrayList<TeeFavoriteRecordModel>();	
		List<TeeFavoriteRecord> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);
		for(TeeFavoriteRecord fr:list){
			TeeFavoriteRecordModel m = new TeeFavoriteRecordModel();
			entityToModel(fr, m);
			modelList.add(m);
		}
		
		json.setRows(modelList);
		
		
		return json;
	}
	
	
}
