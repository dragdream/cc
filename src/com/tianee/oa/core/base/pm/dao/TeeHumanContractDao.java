package com.tianee.oa.core.base.pm.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.hr.TeeHrCodeManager;
import com.tianee.oa.core.base.pm.bean.TeeHumanContract;
import com.tianee.oa.core.base.pm.model.TeeHumanContractModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class TeeHumanContractDao extends TeeBaseDao<TeeHumanContract>{
	
	public List<TeeHumanContract> queryDueToContract(){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String hql = "from TeeHumanContract contract where 1=1 ";
		List param = new ArrayList();
		Calendar cl = Calendar.getInstance();
		String dateStr = sdf.format(cl.getTime());
		try {
			cl.setTime(formatter.parse(dateStr+" 00:00:00"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hql+=" and contract.endTime>=?";
		param.add(cl);
		
		Calendar cl2 = Calendar.getInstance();
		cl2.add(Calendar.DAY_OF_YEAR, 10);
		hql+=" and contract.endTime<=?";
		param.add(cl2);
		List<TeeHumanContract> humanContracts =executeQueryByList(hql, param);
		return humanContracts;
	}
}
