package com.tianee.oa.subsys.bisengin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.bisengin.bean.BisCategory;
import com.tianee.oa.subsys.bisengin.bean.BisForm;
import com.tianee.oa.subsys.bisengin.bean.BisTable;
import com.tianee.oa.subsys.bisengin.bean.BisTableField;
import com.tianee.oa.subsys.bisengin.dao.BisCatDao;
import com.tianee.oa.subsys.bisengin.dao.BisFormDao;
import com.tianee.oa.subsys.bisengin.dao.BisTableDao;
import com.tianee.oa.subsys.bisengin.dao.BisTableFieldDao;
import com.tianee.oa.subsys.bisengin.model.BisFormModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.expReplace.TeeExpFetcher;
import com.tianee.webframe.util.str.expReplace.TeeHTMLImgTag;
import com.tianee.webframe.util.str.expReplace.TeeRegexpAnalyser;

@Service
public class BisFormService extends TeeBaseService {

	@Autowired
	private BisFormDao bisFormDao;

	@Autowired
	private BisCatDao bisCatDao;

	@Autowired
	private BisTableDao bisTableDao;

	@Autowired
	private BisTableFieldDao bisTableFieldDao;

	/**
	 * 获取所有的表格
	 * 
	 * @return
	 */
	public List<Map> getTableList() {
		List<Map> mapList = new ArrayList<Map>();
		// 获取所有的分类
		List<BisCategory> catList = bisCatDao.executeQuery("from BisCategory",
				null);
		// 先取出默认分类下的表格
		List<BisTable> defTableList = bisTableDao.executeQuery(
				"from BisTable table where table.bisCat is null ", null);
		Map defMap = new HashMap();
		defMap.put("cat", "默认分类");
		defMap.put("tableList", defTableList);
		mapList.add(defMap);

		// 循环遍历分类 分别取出各个分类下的表格
		for (BisCategory cat : catList) {
			Map map = new HashMap();
			List<BisTable> tableList = bisTableDao.executeQuery(
					"from BisTable table where table.bisCat.sid="
							+ cat.getSid(), null);
			map.put("cat", cat.getCatName());
			map.put("tableList", tableList);
			mapList.add(map);
		}

		return mapList;
	}

	/**
	 * 创建表单
	 * 
	 * @param model
	 */
	public void addOrUpdateBisForm(BisFormModel model) {
		if (model.getSid() > 0) {
			BisForm form = bisFormDao.get(model.getSid());
			form.setFormName(model.getFormName());
			form.setSortNo(model.getSortNo());
			bisFormDao.update(form);
		} else {
			BisForm form = new BisForm();
			form = parseObj(model, form);
			bisFormDao.save(form);
		}

	}

	/**
	 * 获取所有bisForm的列表
	 * 
	 * @param model
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson datagrid(BisFormModel model,
			TeeDataGridModel dm) {
		TeeEasyuiDataGridJson dataGridJson = bisFormDao.datagrid(model, dm);
		List<BisForm> list = dataGridJson.getRows();
		List modelList = new ArrayList();
		for (BisForm bisForm : list) {
			BisFormModel m = new BisFormModel();
			m = parseReturnModel(bisForm, false);
			modelList.add(m);
		}
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}

	/**
	 * 转换成返回对象
	 * 
	 * @param obj
	 * @param isEdit
	 * @return
	 */
	public BisFormModel parseReturnModel(BisForm obj, boolean isEdit) {

		BisFormModel model = new BisFormModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);

		if (obj.getBisTable() != null && obj.getBisTable().getSid() != 0) {
			model.setBisTableName(obj.getBisTable().getTableName());
			model.setBisTableId(obj.getBisTable().getSid());
		}
		return model;
	}

	/**
	 * 转换成保存对象
	 * 
	 * @param model
	 * @param obj
	 * @return
	 */
	public BisForm parseObj(BisFormModel model, BisForm obj) {
		if (model == null) {
			return obj;
		}
		try {
			BeanUtils.copyProperties(model, obj);

			if (model.getBisTableId() != 0) {
				BisTable table = new BisTable();
				table.setSid(model.getBisTableId());
				obj.setBisTable(table);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 删除表单
	 * 
	 * @param model
	 */
	public void deleteBisForm(BisFormModel model) {
		BisForm bisForm = bisFormDao.get(model.getSid());
		bisFormDao.deleteByObj(bisForm);
	}

	/**
	 * 根据表单的id 获取表单的详情
	 * 
	 * @param model
	 */
	public BisFormModel getFormInfoById(int sid) {
		BisForm form = bisFormDao.get(sid);
		BisFormModel model = parseReturnModel(form, false);
		return model;
	}

	/**
	 * 获取表格字段名称
	 * 
	 * @param formId
	 * @return
	 */
	public List<BisTableField> getFields(int formId) {
		List<BisTableField> list = new ArrayList<BisTableField>();
		// 先获取表单对象
		BisForm bisForm = bisFormDao.get(formId);
		// 获取表单对应的表格对象
		BisTable bisTable = bisForm.getBisTable();
		String hql = "from BisTableField btf where btf.bisTable.sid="
				+ bisTable.getSid()+" order by sid asc";
		list = bisTableFieldDao.executeQuery(hql, null);

		return list;
	}

	/**
	 * 表单设计器点击保存
	 * 
	 * @param formId
	 * @param content
	 */
	public void updateForm(int formId, String content) {
		BisForm bisForm = bisFormDao.get(formId);
		// 设置表单的content
		bisForm.setContent(content);
		// 抽取表单的short_content
		TeeRegexpAnalyser analyser = new TeeRegexpAnalyser(content);
		String shortContent= analyser.replace(
				new String[] { new TeeHTMLImgTag().getRegExp() },
				new TeeExpFetcher() {
					@Override
					public String parse(String pattern) {
						TeeHTMLImgTag htmlimgTag = new TeeHTMLImgTag();
						htmlimgTag.analyse(pattern);
						Map<String, String> attrs = htmlimgTag
								.getAttributes();
						return "${" + attrs.get("id") + "}";
					}
				});
		//设置表单的short_content
		bisForm.setShortContent(shortContent);
		

	}

	/**
	 * 获取所有的业务表单
	 * @return
	 */
	public List<BisFormModel> getBisFormList() {
		String hql="from BisForm bf order by bf.sortNo ";
		List<BisForm> formList=bisFormDao.executeQuery(hql, null);
		List<BisFormModel> modelList=new ArrayList<BisFormModel>();
		for (BisForm bf : formList) {
			BisFormModel model=parseReturnModel(bf, false);
			modelList.add(model);
		}
		return modelList;
	}
}
