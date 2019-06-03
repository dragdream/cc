package com.tianee.oa.subsys.bisengin.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbutils.DbUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.bisengin.bean.BisDataSource;
import com.tianee.oa.subsys.bisengin.bean.BisView;
import com.tianee.oa.subsys.bisengin.bean.BisViewListItem;
import com.tianee.oa.subsys.bisengin.model.BisViewListItemModel;
import com.tianee.oa.subsys.bisengin.model.BisViewModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDataSource;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.str.expReplace.TeeExpFetcher;
import com.tianee.webframe.util.str.expReplace.TeeRegexpAnalyser;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class BisViewService extends TeeBaseService {

	public void createBisView(BisViewModel bisViewModel) {
		BisView bisView = new BisView();
		BeanUtils.copyProperties(bisViewModel, bisView);
		BisDataSource dataSource = new BisDataSource();
		dataSource.setSid(1);
		bisView.setDataSource(dataSource);
		simpleDaoSupport.save(bisView);
	}

	public BisViewModel getBisView(String identity) {
		BisView bisView = (BisView) simpleDaoSupport.get(BisView.class, identity);
		BisViewModel bisViewModel = BisViewEntity2Model(bisView);
		return bisViewModel;
	}

	public TeeEasyuiDataGridJson listBisView(TeeDataGridModel dm, Map requestMap) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String identity = (String) requestMap.get("identity1");
		String viewName = (String) requestMap.get("viewName2");
		String type = (String) requestMap.get("type1");

		String hql = "from BisView where 1=1 ";
		List param = new ArrayList();
		if (!TeeUtility.isNullorEmpty(identity)) {
			hql += " and identity like ?";
			param.add("%" + identity + "%");
		}

		if (!TeeUtility.isNullorEmpty(viewName)) {
			hql += " and name like ?";
			param.add("%" + viewName + "%");
		}
		if (!TeeUtility.isNullorEmpty(type)) {
			hql += " and type = ?";
			param.add(Integer.parseInt(type));
		}

		long total = simpleDaoSupport.countByList("select count(*) " + hql, param);
		if (TeeUtility.isNullorEmpty(dm.getSort())) {
			dm.setSort("name");
			dm.setOrder("asc");
		}
		hql += " order by " + dm.getSort() + " " + dm.getOrder();
		List<BisView> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);
		List<BisViewModel> modelList = new ArrayList();
		BisViewModel bisViewModel = null;
		for (BisView bisView : list) {
			bisViewModel = BisViewEntity2Model(bisView);
			modelList.add(bisViewModel);
		}
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}

	public void delBisView(String identity) {
		simpleDaoSupport.delete(BisView.class, identity);
	}

	public void updateBisView(BisViewModel bisViewModel) {
		BisView bisView = (BisView) simpleDaoSupport.get(BisView.class, bisViewModel.getIdentity());
		bisView.setName(bisViewModel.getName());
		bisView.setSql(bisViewModel.getSql());
		bisView.setCountSql(bisViewModel.getCountSql());
		bisView.setDesignModel(bisViewModel.getDesignModel());
		bisView.setType(bisViewModel.getType());
		bisView.setFromSql(bisViewModel.getFromSql());
		bisView.setSelectSql(bisViewModel.getSelectSql());
		bisView.setWhereSql(bisViewModel.getWhereSql());

		BisDataSource dataSource = new BisDataSource();
		if (bisViewModel.getBisDataSourceId() != 0) {
			dataSource.setSid(bisViewModel.getBisDataSourceId());
			bisView.setDataSource(dataSource);
		}

		simpleDaoSupport.update(bisView);
	}

	public void addBisViewListItem(BisViewListItemModel bisViewListItemModel) {
		BisViewListItem bisViewListItem = new BisViewListItem();
		BeanUtils.copyProperties(bisViewListItemModel, bisViewListItem);
		BisView bisView = new BisView();
		bisView.setIdentity(bisViewListItemModel.getBisViewId());
		bisViewListItem.setBisView(bisView);
		simpleDaoSupport.save(bisViewListItem);
	}

	public void updateBisViewListItem(BisViewListItemModel bisViewListItemModel) {
		BisViewListItem bisViewListItem = (BisViewListItem) simpleDaoSupport.get(BisViewListItem.class, bisViewListItemModel.getSid());
		BeanUtils.copyProperties(bisViewListItemModel, bisViewListItem);
		simpleDaoSupport.update(bisViewListItem);
	}

	public void delBisViewListItem(int sid) {
		simpleDaoSupport.delete(BisViewListItem.class, sid);
	}

	public BisViewListItemModel getBisViewListItem(int sid) {
		BisViewListItem bisViewListItem = (BisViewListItem) simpleDaoSupport.get(BisViewListItem.class, sid);
		return BisViewListItemEntity2Model(bisViewListItem);
	}

	public TeeEasyuiDataGridJson listBisViewListItem(String identity) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from BisViewListItem where bisView.identity='" + identity + "' ";
		long total = simpleDaoSupport.count("select count(*) " + hql, null);
		List<BisViewListItem> list = simpleDaoSupport.find(hql + " order by orderNo asc", null);
		List<BisViewListItemModel> modelList = new ArrayList();
		BisViewListItemModel bisViewListItemModel = null;
		for (BisViewListItem bisViewListItem : list) {
			bisViewListItemModel = BisViewListItemEntity2Model(bisViewListItem);
			modelList.add(bisViewListItemModel);
		}
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}

	public static BisViewModel BisViewEntity2Model(BisView bisView) {
		BisViewModel bisViewModel = new BisViewModel();
		BeanUtils.copyProperties(bisView, bisViewModel);
		bisViewModel.setBisDataSourceId(bisView.getDataSource().getSid());
		return bisViewModel;
	}

	public static BisViewListItemModel BisViewListItemEntity2Model(BisViewListItem bisViewListItem) {
		BisViewListItemModel bisViewListItemModel = new BisViewListItemModel();
		BeanUtils.copyProperties(bisViewListItem, bisViewListItemModel);
		BisView bisView = bisViewListItem.getBisView();
		if (bisView != null) {
			bisViewListItemModel.setBisViewId(bisView.getIdentity());
			bisViewListItemModel.setBisViewName(bisView.getName());
		}
		return bisViewListItemModel;
	}

	/**
	 * 格式化sql语句
	 * 
	 * @param sql
	 * @return
	 */
	public static String formatSql(String sql, TeeDataGridModel dm, final Map requestData, final BisView bisView, TeePerson loginUser) {
		if(sql==null){
			return "";
		}
		// 处理sql动态变量
		sql = sql.replace("\r\n", " ").replace("$userId", loginUser.getUserId()).replace("$userSid", loginUser.getUuid() + "").replace("$userName", loginUser.getUserName())
				.replace("$deptId", loginUser.getDept().getUuid() + "").replace("$roleId", loginUser.getUserRole().getUuid() + "");

		// 替换动态变量
		TeeRegexpAnalyser analyser = new TeeRegexpAnalyser(sql);
		sql = analyser.replace(new String[] { "@[a-zA-z0-9]*" }, new TeeExpFetcher() {

			@Override
			public String parse(String pattern) {
				// TODO Auto-generated method stub
				return TeeStringUtil.getString(requestData.get(pattern.substring(1)));
			}
		});

		// 拼接查询条件
		// analyser = new TeeRegexpAnalyser(sql);
		// sql = analyser.replace(new String[] { "\\[SEARCH[^\\]]*\\]" },
		// new TeeExpFetcher() {
		//
		// @Override
		// public String parse(String pattern) {
		// // TODO Auto-generated method stub
		// pattern = pattern.substring(1, pattern.length() - 1);
		// String sp[] = pattern.split(":");
		//
		// Set<String> keys = requestData.keySet();
		// String sp0[] = null;
		// boolean hasExists = false;
		// for (String key : keys) {
		// if (key.endsWith("_SEARCH")
		// && !"".equals(requestData.get(key)
		// .toString())) {
		// sp0 = key.toString().split("_");
		// if (sp[1].equals(sp0[0])) {
		// hasExists = true;
		// if ("TEXT".equals(sp0[1])) {
		// pattern = sp[2] + " like '%"
		// + requestData.get(key)
		// + "%'";
		// } else if ("NUMBER".equals(sp0[1])) {
		// pattern = sp[2] + " = "
		// + requestData.get(key) + "";
		// }
		// break;
		// }
		// }
		// }
		//
		// return hasExists ? pattern : "1=1";
		// }
		// });

		// 查询块儿替换
		StringBuffer where = new StringBuffer();
		Set<String> keys = requestData.keySet();
		List params = new ArrayList();
		
		for (String key : keys) {
			if (key.endsWith("_SEARCH") && !"".equals(requestData.get(key).toString())) {
				String sp[] = null;
				String dateType ="";
				if(key.endsWith("_TEXT_SEARCH")){//文本类型
					dateType = key.substring(key.indexOf("_TEXT_SEARCH")+1, key.indexOf("_SEARCH"));
					sp = key.toString().split("_TEXT_SEARCH");
				}else if(key.endsWith("_NUMBER_SEARCH")){//数值类型
					dateType = key.substring(key.indexOf("_NUMBER_SEARCH")+1, key.indexOf("_SEARCH"));
					sp = key.toString().split("_NUMBER_SEARCH");
				}else if(key.endsWith("_DATE_SEARCH")){//日期类型
					
				}
				if(!TeeUtility.isNullorEmpty(dateType.trim())){
					where.append(" and " + sp[0]);
					if ("TEXT".equals(dateType)) {
						where.append(" like '%" + requestData.get(key) + "%'");
					} else if ("NUMBER".equals(dateType)) {
						where.append(" = " + requestData.get(key) + "");
					}
				}
			}
		}
		
		if(TeeUtility.isNullorEmpty(where)){
			if(sql.contains("[SEARCH_BLOCK]")){
				sql = sql.replace("[SEARCH_BLOCK]", " 1=1 ");
			}
		}else{
			if(sql.contains("[SEARCH_BLOCK]")){
				sql = sql.replace("[SEARCH_BLOCK]", " 1=1 "+where);
			}else{
				sql+=where;
			}
			
		}
		

		// 过滤SQL
		analyser = new TeeRegexpAnalyser(sql);
		sql = analyser.replace(new String[] { "\\[[A-Za-z0-9\\|\\._,'%/]*\\]" }, new TeeExpFetcher() {

			@Override
			public String parse(String pattern) {
				// TODO Auto-generated method stub
				// System.out.println(pattern);
				pattern = pattern.substring(1, pattern.length() - 1);
				String sp[] = pattern.split("\\|");
				if (sp[0].indexOf("DATE2CHAR_Y_M_D") != -1) {
					return TeeDbUtility.DATE2CHAR_Y_M_D(bisView.getDataSource().getDbType(), sp[1]);
				} else if (sp[0].indexOf("DATE2CHAR_Y_M") != -1) {
					return TeeDbUtility.DATE2CHAR_Y_M(bisView.getDataSource().getDbType(), sp[1]);
				} else if (sp[0].indexOf("GET_DAY_OF_MONTH_STR") != -1) {
					Calendar c = Calendar.getInstance();
					String pp[] = TeeStringUtil.parseStringArray(sp[0]);
					if (pp.length == 1) {
						return TeeDbUtility.GET_DAY_OF_MONTH_STR(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
					} else {
						return TeeDbUtility.GET_DAY_OF_MONTH_STR(Integer.parseInt(pp[1]), Integer.parseInt(pp[2]));
					}
				} else if (sp[0].indexOf("GET_MONTH_OF_YEAR_STR") != -1) {
					Calendar c = Calendar.getInstance();
					String pp[] = TeeStringUtil.parseStringArray(sp[0]);
					if (pp.length == 1) {
						return TeeDbUtility.GET_MONTH_OF_YEAR_STR(c.get(Calendar.YEAR));
					} else {
						return TeeDbUtility.GET_MONTH_OF_YEAR_STR(Integer.parseInt(pp[1]));
					}
				} else if (sp[0].indexOf("DATE2CHAR_Y") != -1) {
					return TeeDbUtility.DATE2CHAR_Y(bisView.getDataSource().getDbType(), sp[1]);
				} else if (sp[0].indexOf("DATE2CHAR_M") != -1) {
					return TeeDbUtility.DATE2CHAR_M(bisView.getDataSource().getDbType(), sp[1]);
				} else if (sp[0].indexOf("GET_LONG_MONTH_BETWEEN_STR") != -1) {
					Calendar c = Calendar.getInstance();
					return TeeDbUtility.GET_LONG_MONTH_BETWEEN_STR(sp[1], c.get(Calendar.YEAR), Integer.parseInt(sp[2]));
				} else if (sp[0].indexOf("GET_LONG_MONTH_BETWEEN_STR") != -1) {
					Calendar c = Calendar.getInstance();
					return TeeDbUtility.GET_LONG_MONTH_BETWEEN_STR(sp[1], c.get(Calendar.YEAR), Integer.parseInt(sp[2]));
				} else if (sp[0].indexOf("GET_LONG_MONTH_FIRST_STR") != -1) {
					Calendar c = Calendar.getInstance();
					return TeeDbUtility.GET_LONG_MONTH_FIRST_STR(c.get(Calendar.YEAR), Integer.parseInt(sp[1]));
				} else if (sp[0].indexOf("GET_LONG_MONTH_LAST_STR") != -1) {
					Calendar c = Calendar.getInstance();
					return TeeDbUtility.GET_LONG_MONTH_LAST_STR(c.get(Calendar.YEAR), Integer.parseInt(sp[1]));
				} else if (sp[0].indexOf("CONCAT_BEFORE") != -1) {
					String pp[] = TeeStringUtil.parseStringArray(sp[1]);
					return TeeDbUtility.CONCAT_BEFORE(bisView.getDataSource().getDbType(), pp[0], pp[1]);
				} else if (sp[0].indexOf("CONCAT_AFTER") != -1) {
					String pp[] = TeeStringUtil.parseStringArray(sp[1]);
					return TeeDbUtility.CONCAT_AFTER(bisView.getDataSource().getDbType(), pp[0], pp[1]);
				}
				return null;
			}
		});

		return sql;
	}

	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson dflist(TeeDataGridModel dm, final Map requestData) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String dfid = TeeStringUtil.getString(requestData.get("dfid"));// 获取数据标识
		Exception e1=null;
		BisView bisView = (BisView) simpleDaoSupport.get(BisView.class, dfid);
		if (bisView == null) {
			throw new TeeOperationException("视图不存在");
		}

		Connection conn = null;
		final BisDataSource bisDataSource = bisView.getDataSource();
		if (bisDataSource.getDataSource() == 1) {// 内部数据源
			conn = TeeDbUtility.getConnection(null);
		} else {// 外部数据源
			TeeDataSource dataSource = new TeeDataSource();
			dataSource.setDriverClass(bisDataSource.getDriverClass());
			dataSource.setPassWord(bisDataSource.getDriverPwd());
			dataSource.setUrl(bisDataSource.getDriverUrl());
			dataSource.setUserName(bisDataSource.getDriverUsername());
			dataSource.setConfigModel(bisDataSource.getConfigModel());
			conn = TeeDbUtility.getConnection(dataSource);
		}

		TeePerson loginUser = (TeePerson) simpleDaoSupport.get(TeePerson.class, TeeRequestInfoContext.getRequestInfo().getUserSid());

		try {
			String sql = bisView.getSql();
			String countSql = bisView.getCountSql();
			DbUtils dbUtils = new DbUtils(conn);

			sql = formatSql(sql, dm, requestData, bisView, loginUser);
			if (countSql != null) {
				countSql = formatSql(countSql, dm, requestData, bisView, loginUser);
				int index = countSql.indexOf("order by");
				if (index > 0) {
					countSql = countSql.substring(0, index);
				}
			}
//			System.out.println(sql);

			// 如果不存在where条件，则替换sql中的条件区块
//			if (where.toString().equals("")) {
//				sql = sql.replace("[SEARCH_BLOCK]", "1=1 ");
//			}else{
//				sql = sql.replace("[SEARCH_BLOCK]", where.toString());
//			}
			
			List<Map> list = dbUtils.queryToMapList(sql, null, (dm.getPage() - 1) * dm.getRows(), dm.getRows());
			if (list == null) {
				list = new ArrayList();
			}
			
			for(Map data:list){
				Set<String> keys0 = data.keySet();
				Map hashMap = new HashMap();
				for(String key:keys0){
					if(data.get(key) instanceof Calendar){
						hashMap.put(key, TeeDateUtil.format(((Calendar)data.get(key)).getTime(), "yyyy-MM-dd"));
					}
				}
				data.putAll(hashMap);
			}

			long total = 0;
			if (countSql == null || "".equals(countSql)) {
				total = list.size();
			} else {
				Map totalMap = dbUtils.queryToMap(countSql);
				Set<String> keys2 = totalMap.keySet();
				for (String key : keys2) {
					total = TeeStringUtil.getLong(totalMap.get(key), 0);
				}
			}
			dataGridJson.setRows(list == null ? new ArrayList() : list);
			dataGridJson.setTotal(total);
		} catch (Exception e) {
			e1=e;
			e.printStackTrace();
		} finally {
			TeeDbUtility.closeConn(conn);
		}
		
		if(e1!=null){
			throw new TeeOperationException(e1.toString());
		}
		
		return dataGridJson;
	}
	
	/**
	 * 通过视图获取数据库连接
	 * @param viewId
	 * @return
	 */
	@Transactional(readOnly = true)
	public Connection getConnectionByView(String viewId) {
		Connection conn = null;
		BisView bisView = (BisView) simpleDaoSupport.get(BisView.class, viewId);
		
		BisDataSource bisDataSource = bisView.getDataSource();
		if (bisDataSource.getDataSource() == 1) {// 内部数据源
			conn = TeeDbUtility.getConnection(null);
		} else {// 外部数据源
			TeeDataSource dataSource = new TeeDataSource();
			dataSource.setDriverClass(bisDataSource.getDriverClass());
			dataSource.setPassWord(bisDataSource.getDriverPwd());
			dataSource.setUrl(bisDataSource.getDriverUrl());
			dataSource.setUserName(bisDataSource.getDriverUsername());
			dataSource.setConfigModel(bisDataSource.getConfigModel());
			conn = TeeDbUtility.getConnection(dataSource);
		}
		
		return conn;
	}
	
	/**
	 * 通过视图获取SQL
	 * @param viewId
	 * @return
	 */
	@Transactional(readOnly = true)
	public String getSqlByView(String viewId) {
		
		BisView bisView = (BisView) simpleDaoSupport.get(BisView.class, viewId);

		TeePerson loginUser = (TeePerson) simpleDaoSupport.get(TeePerson.class, TeeRequestInfoContext.getRequestInfo().getUserSid());
		String sql = bisView.getSql();
		sql = formatSql(sql, null, new HashMap(), bisView, loginUser);
		return sql;
		
	}
	
	/**
	 * 通过视图获取SQLCount
	 * @param viewId
	 * @return
	 */
	@Transactional(readOnly = true)
	public String getCountSqlByView(String viewId) {
		
		BisView bisView = (BisView) simpleDaoSupport.get(BisView.class, viewId);

		TeePerson loginUser = (TeePerson) simpleDaoSupport.get(TeePerson.class, TeeRequestInfoContext.getRequestInfo().getUserSid());
		String sql = bisView.getCountSql();
		sql = formatSql(sql, null, null, bisView, loginUser);
		return sql;
		
	}
	
	
	
	public String exportXml(String identity){
		BisView bisView = (BisView) simpleDaoSupport.get(BisView.class,identity );
		Element root;   
		root=new Element("BisView");
		
		root.addContent(new Element("identity").setText(TeeStringUtil.getString(bisView.getIdentity())));
		root.addContent(new Element("name").setText(TeeStringUtil.getString(bisView.getName())));
		root.addContent(new Element("sql").setText(TeeStringUtil.getString(bisView.getSql())));
		root.addContent(new Element("countSql").setText(TeeStringUtil.getString(bisView.getCountSql())));
		root.addContent(new Element("selectSql").setText(TeeStringUtil.getString(bisView.getSelectSql())));
		root.addContent(new Element("fromSql").setText(TeeStringUtil.getString(bisView.getFromSql())));
		root.addContent(new Element("whereSql").setText(TeeStringUtil.getString(bisView.getWhereSql())));
		root.addContent(new Element("orderBySql").setText(TeeStringUtil.getString(bisView.getOrderBySql())));
		root.addContent(new Element("dataSource").setText("1"));
		root.addContent(new Element("type").setText(TeeStringUtil.getString(bisView.getType())));
		root.addContent(new Element("designModel").setText(TeeStringUtil.getString(bisView.getDesignModel())));
		
		Element items = new Element("Items");
		List<BisViewListItem> list = bisView.getViewListItems();
		for(BisViewListItem viewListItem:list){
			Element item = new Element("Item");
			item.addContent(new Element("sid").setText(TeeStringUtil.getString(viewListItem.getSid())));
			item.addContent(new Element("fieldType").setText(TeeStringUtil.getString(viewListItem.getFieldType())));
			item.addContent(new Element("title").setText(TeeStringUtil.getString(viewListItem.getTitle())));
			item.addContent(new Element("field").setText(TeeStringUtil.getString(viewListItem.getField())));
			item.addContent(new Element("searchField").setText(TeeStringUtil.getString(viewListItem.getSearchField())));
			item.addContent(new Element("searchFieldWrap").setText(TeeStringUtil.getString(viewListItem.getSearchFieldWrap())));
			item.addContent(new Element("width").setText(TeeStringUtil.getString(viewListItem.getWidth())));
			item.addContent(new Element("formatterScript").setText(TeeStringUtil.getString(viewListItem.getFormatterScript())));
			item.addContent(new Element("isSearch").setText(TeeStringUtil.getString(viewListItem.getIsSearch())));
			item.addContent(new Element("orderNo").setText(TeeStringUtil.getString(viewListItem.getOrderNo())));
			item.addContent(new Element("model").setText(TeeStringUtil.getString(viewListItem.getModel())));
			
			items.addContent(item);
		}
		
		root.addContent(items);
		
		
		Document doc = new Document(root);   
        XMLOutputter out = new XMLOutputter();   
        
        String str = out.outputString(doc);
        
		return str;
	}
	
	
	
	public void importXml(InputStream in) throws JDOMException{
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build(in);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Element root = doc.getRootElement();
		
		String identity = root.getChildText("identity");
		String name = root.getChildText("name");
		String sql = root.getChildText("sql");
		String countSql = root.getChildText("countSql");
		String selectSql = root.getChildText("selectSql");
		String fromSql = root.getChildText("fromSql");
		String whereSql = root.getChildText("whereSql");
		String orderBySql = root.getChildText("orderBySql");
		int dataSource = TeeStringUtil.getInteger(root.getChildText("dataSource"), 0);
		int type = TeeStringUtil.getInteger(root.getChildText("type"), 0);
		String designModel = root.getChildText("designModel");
		
		BisView bisView = new BisView();
		bisView.setIdentity(identity);
		bisView.setName(name);
		bisView.setSql(sql);
		bisView.setCountSql(countSql);
		bisView.setSelectSql(selectSql);
		bisView.setFromSql(fromSql);
		bisView.setWhereSql(whereSql);
		bisView.setOrderBySql(orderBySql);
		
		BisDataSource bisDataSource = new BisDataSource();
		bisDataSource.setSid(dataSource);
		bisView.setDataSource(bisDataSource);
		bisView.setType(type);
		bisView.setDesignModel(designModel);
		
		simpleDaoSupport.save(bisView);
		
		Element ItemsElement = root.getChild("Items");
		List<Element> items = ItemsElement.getChildren();
		for(Element item:items){
			BisViewListItem bisViewListItem = new  BisViewListItem();
			bisViewListItem.setFieldType(item.getChild("fieldType").getText());
			bisViewListItem.setTitle(item.getChild("title").getText());
			bisViewListItem.setField(item.getChild("field").getText());
			bisViewListItem.setSearchField(item.getChild("searchField").getText());
			bisViewListItem.setSearchFieldWrap(item.getChild("searchFieldWrap").getText());
			bisViewListItem.setWidth(item.getChild("width").getText());
			bisViewListItem.setFormatterScript(item.getChild("formatterScript").getText());
			bisViewListItem.setIsSearch(TeeStringUtil.getInteger(item.getChild("isSearch").getText(), 0));
			bisViewListItem.setOrderNo(TeeStringUtil.getInteger(item.getChild("orderNo").getText(), 0));
			bisViewListItem.setModel(item.getChild("model").getText());
			bisViewListItem.setBisView(bisView);
			simpleDaoSupport.save(bisViewListItem);
		}
		
		
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	/**
	 * 获取所有的视图  不分页
	 * @param request
	 * @return
	 */
	public TeeJson getAllBisViewList(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String hql = "from BisView where 1=1 order by identity asc ";
		List<BisView> list = simpleDaoSupport.executeQuery(hql, null);
		List<BisViewModel> modelList = new ArrayList();
		BisViewModel bisViewModel = null;
		for (BisView bisView : list) {
			bisViewModel = BisViewEntity2Model(bisView);
			modelList.add(bisViewModel);
		}
		//System.out.println(modelList.size());
		json.setRtData(modelList);
		json.setRtState(true);
		return json;
	}

	
	/**
	 * 根据视图标识  获取item集合
	 * @param identity
	 * @return
	 */
	public TeeJson getBisViewListItemList(String identity) {
		TeeJson json=new TeeJson();
		String hql = "from BisViewListItem where bisView.identity='" + identity + "' ";
		List<BisViewListItem> list = simpleDaoSupport.find(hql + " order by orderNo asc", null);
		List<BisViewListItemModel> modelList = new ArrayList();
		BisViewListItemModel bisViewListItemModel = null;
		for (BisViewListItem bisViewListItem : list) {
			bisViewListItemModel = BisViewListItemEntity2Model(bisViewListItem);
			modelList.add(bisViewListItemModel);
		}
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}

}
