package com.beidasoft.xzzf.punish.document.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.document.bean.DocAskbook;
import com.beidasoft.xzzf.punish.document.dao.AskbookDao;

@Service
public class AskbookService {

	@Autowired
	private AskbookDao askbookDao;

	/**
	 * 保存调查询问通知书信息
	 * 
	 * @param docAskbook
	 */
	public void saveDocInfo(DocAskbook docAskbook) {
		askbookDao.save(docAskbook);
	}

	/**
	 * 查询调查询问通知书信息（根据id）
	 * @param id
	 * @return
	 */
	public DocAskbook getDocInfo(String id) {
		return askbookDao.get(id);
	}
	
	/**
	 * 更新调查询问通知书信息
	 * @param docAskbook
	 */
	public void updateDocInfo(DocAskbook docAskbook) {
		askbookDao.save(docAskbook);
	}

}
