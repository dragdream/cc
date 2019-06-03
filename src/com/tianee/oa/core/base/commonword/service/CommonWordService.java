package com.tianee.oa.core.base.commonword.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.commonword.bean.CommonWord;
import com.tianee.oa.core.base.commonword.model.CommonWordModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class CommonWordService extends TeeBaseService{

	@SuppressWarnings("unchecked")
	public void addCm(CommonWordModel cwm, int uuid) {
		 CommonWord cw = new CommonWord();
		 BeanUtils.copyProperties(cwm, cw);
		 cwm.setPensonId(uuid);
		 if(cwm.getPensonId()!=0){
			 TeePerson tp=new TeePerson();
			 tp.setUuid(cwm.getPensonId());
			 cw.setPerson(tp);
		 }
		 simpleDaoSupport.save(cw);
		
	}

	/*public TeeEasyuiDataGridJson testDatagrid(TeeDataGridModel dm, Object object) {
TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		String hql = "from CommonWord cw ";
		@SuppressWarnings("unchecked")
		List<CommonWord> list =simpleDaoSupport.pageFind(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		
		List<CommonWordModel> modelList = new ArrayList();
		for(CommonWord commonWord:list){
			CommonWordModel model = new CommonWordModel();
			BeanUtils.copyProperties(commonWord, model);
			if(commonWord.getPerson()!=null){
				model.setPensonId(commonWord.getPerson().getUuid());
				model.setPensonName(commonWord.getPerson().getUserName());
			}
			modelList.add(model);
		}
		
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}*/
//先得到sid
	@SuppressWarnings("unchecked")
	public CommonWord deteleCm(String sid) {
		CommonWord cw= getCommonWord(sid);
		if(cw.getSid()!=""||cw.getSid()!=null){
		simpleDaoSupport.deleteByObj(cw);
		}
		return cw;
	}

	private CommonWord getCommonWord(String sid) {
		CommonWord cw = (CommonWord) simpleDaoSupport.get(CommonWord.class, sid);
		return cw;
	}

	

	//获得id
			public CommonWordModel getCommonWords(String sid) {
				CommonWord tu = (CommonWord) simpleDaoSupport.get(CommonWord.class, sid);
				CommonWordModel model = new CommonWordModel();
				BeanUtils.copyProperties(tu, model);
				model.setPensonId(tu.getPerson().getUuid());
				model.setPensonName(tu.getPerson().getUserName());
				return model;
			}

			//修改
			@SuppressWarnings("unchecked")
			public void updateCommonWord(CommonWordModel commonWordModel) {
			 //先查实体类
				CommonWord commonWord = (CommonWord)simpleDaoSupport.get(CommonWord.class, commonWordModel.getSid());
				//再往实体类中set对应的属性值
				commonWord.setCyy(commonWordModel.getCyy());
				commonWord.setCis(commonWordModel.getCis());
				
				//先判断是否选择了部门
						if(commonWordModel.getPensonId()!=0){
							TeePerson person = new TeePerson();
							person.setUuid(commonWordModel.getPensonId());
							commonWord.setPerson(person);
						}
						
				//执行更新
				simpleDaoSupport.update(commonWord);
			}
			
			/**
			 * 增加使用频次
			 * @param sid
			 */
			public void wordCountPlus(String sid) {
				simpleDaoSupport.executeUpdate("update CommonWord set cis=cis+1 where sid=?", new Object[]{sid});
			}

			public TeeEasyuiDataGridJson sel(TeePerson person,TeeDataGridModel dm) {
			
				TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
				String hql ="from CommonWord where person.uuid="+person.getUuid();
				List<CommonWord> list =	simpleDaoSupport.pageFind(hql+" order by cis desc", dm.getFirstResult(), dm.getRows(), null);
				long total = simpleDaoSupport.count("select count(sid) "+hql, null);
				List<CommonWordModel> modelList = new ArrayList();
				for(CommonWord commonWord:list){
					CommonWordModel commonWordModel=new CommonWordModel();
					BeanUtils.copyProperties(commonWord, commonWordModel);
					if(commonWord.getPerson()!=null){
						commonWordModel.setPensonId(commonWord.getPerson().getUuid());
						commonWordModel.setPensonName(commonWord.getPerson().getUserName());
					}
					modelList.add(commonWordModel);
				}
				
				dataGridJson.setRows(modelList);
				dataGridJson.setTotal(total);
				
				return dataGridJson;
		
				
			}

		
}
