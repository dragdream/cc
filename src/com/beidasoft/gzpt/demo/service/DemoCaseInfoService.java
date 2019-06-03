package com.beidasoft.gzpt.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.gzpt.demo.bean.DemoCaseInfo;
import com.beidasoft.gzpt.demo.dao.DemoCaseInfoDao;
import com.beidasoft.gzpt.demo.model.DemoCaseInfoModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class DemoCaseInfoService extends TeeBaseService {
	@Autowired
	DemoCaseInfoDao demoCaseInfoDao;

	@Autowired
	TeeSimpleDaoSupport simpleDaoSupport;

	@SuppressWarnings("unchecked")
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, Map<String, String> map) throws ParseException {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String hql = "from DemoCaseInfo dci ";
		List<DemoCaseInfo> list = simpleDaoSupport.pageFind(hql + "order by dci.id asc",
				dm.getRows() * (dm.getPage() - 1), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) " + hql, null);

		List<DemoCaseInfoModel> modelList = new ArrayList<DemoCaseInfoModel>();
		for (int i = 0; i < list.size(); i++) {
			DemoCaseInfoModel model = new DemoCaseInfoModel();
			BeanUtils.copyProperties(list.get(i), model);
			String d = format.format(model.getAddtime());
			Date date = format.parse(d);
			model.setAddtime(date);
			modelList.add(model);
		}

		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}

}
