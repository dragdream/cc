package com.beidasoft.xzzf.punish.transact.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.model.PunishBaseModel;
import com.beidasoft.xzzf.punish.transact.bean.TransactBean;
import com.beidasoft.xzzf.punish.transact.dao.TransactDao;
import com.beidasoft.xzzf.punish.transact.model.TransactModel;
import com.beidasoft.xzzf.transferred.bean.DocTransferred;
import com.beidasoft.xzzf.transferred.model.TransferredModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TransactService extends TeeBaseService{
	
	@Autowired
	private TransactDao transactDao;
	
	public TeeEasyuiDataGridJson getTranscatOfPage(TeeDataGridModel dm, HttpServletRequest request) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		
		//立案时间
		String startFilingTime = TeeStringUtil.getString(request.getParameter("startTime"));
		String endFilingTime = TeeStringUtil.getString(request.getParameter("endTime"));
		
		//查询条件list
		List param=new ArrayList();
		//基础hql
		String hql=" from TransactBean  ";
		
		long total = simpleDaoSupport.count("select count(id) " + hql, param.toArray());
		datagrid.setTotal(total);
		List<TransactBean> list = simpleDaoSupport.pageFind(hql + " order by filingDate desc ", (dm.getPage() - 1) * dm.getRows(),dm.getRows(), param.toArray());
		
		List<TransactModel> models = new ArrayList<TransactModel>();
		TransactModel model = null;
		for(TransactBean row : list) {
			model = new TransactModel();
			
			model = transferModel(row);
			
			models.add(model);
			
		}
		
		datagrid.setRows(models);
		
		return datagrid;
	}
	

	
	
	
	/**
	 * 将bean转换为model
	 * @param transactBean
	 * @return
	 */
	private TransactModel transferModel(TransactBean transactBean) {
		TransactModel transactModel = new TransactModel();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		BeanUtils.copyProperties(transactBean, transactModel);
		
		//转换立案日期
		if(transactBean.getFilingDate() != null) {
			transactModel.setFilingDateStr(dateFormat.format(transactBean.getFilingDate().getTime()));
		}

		//转换检查日期
		if(transactBean.getInspectionDate() != null) {
			transactModel.setInspectionDateStr(dateFormat.format(transactBean.getInspectionDate().getTime()));
		}

		//转换处罚决定书日期
		if(transactBean.getPunishmentDate() != null) {
			transactModel.setPunishmentDateStr(dateFormat.format(transactBean.getPunishmentDate().getTime()));
		}

		//转换处罚执行日期
		if(transactBean.getPunishmentExeDate() != null) {
			transactModel.setPunishmentExeDateStr(dateFormat.format(transactBean.getPunishmentExeDate().getTime()));
		}

		//转换结案日期
		if(transactBean.getClosedDate() != null) {
			transactModel.setClosedDateStr(dateFormat.format(transactBean.getClosedDate().getTime()));
		}
		
		return transactModel;
	}
	
	/**
	 * 保存 当事人信息
	 * @param transactInfo
	 */
	public void save(TransactBean transactInfo) {
		transactDao.saveOrUpdate(transactInfo);
	}

		
	
	
}
