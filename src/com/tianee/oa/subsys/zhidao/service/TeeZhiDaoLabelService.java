package com.tianee.oa.subsys.zhidao.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.zhidao.bean.TeeZhiDaoLabel;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeZhiDaoLabelService extends TeeBaseService{

	/**
	 * 根据标签名称  判断标签是否存在
	 * @param labelName
	 * @return
	 */
	public TeeZhiDaoLabel isExists(String labelName){
		String hql=" from TeeZhiDaoLabel l where l.labelName=? ";
		List<TeeZhiDaoLabel>list=simpleDaoSupport.executeQuery(hql,new Object[]{labelName});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
