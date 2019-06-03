package com.beidasoft.xzfy.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzfy.common.bean.DictionaryInfo;
import com.beidasoft.xzfy.common.dao.FyCommonDao;
import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;

@Service
public class FyCommonService {

	@Autowired
	private FyCommonDao comdao;
	
	@Autowired
	private TeeSimpleDaoSupport simplDaoSupport;
	/**
	 * 查询字典表类型(当类型为空，查询所有类型)
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DictionaryInfo> getDictByType(String type){
		List<Object[]> list = (List<Object[]>)comdao.getDictByType(type);
		
		DictionaryInfo model = new DictionaryInfo();
		List<DictionaryInfo> models = new ArrayList<>();
		for(int i=0;i<list.size();i++)
		{
			model = (DictionaryInfo) StringUtils.arrayToObject(list.get(i),DictionaryInfo.class);
			models.add(model);
		}
		return models;
	}
	
	/**
	 * Description:查询受理人的列表
	 * @author zhangchengkun
	 * @version 0.1 2019年5月27日
	 * @param deptId
	 * @return  List<TeePerson>
	 */
	@SuppressWarnings("unchecked")
	public List<TeePerson> getAllPerson(int deptId){
	
		return simplDaoSupport.executeQuery("from TeePerson where dept.uuid="+deptId, null);
	}
	
}
