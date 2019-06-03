package com.tianee.oa.core.general.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.ExpressionCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.general.bean.TeePortlet;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.bisengin.service.BisViewService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.expReplace.TeeExpFetcher;
import com.tianee.webframe.util.str.expReplace.TeeRegexpAnalyser;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class TeePortletService extends TeeBaseService{
	@Autowired
	private BisViewService bisViewService;
	
	public void addOrUpdatePortlet(TeePortlet portlet){
		simpleDaoSupport.saveOrUpdate(portlet);
	}
	
	public void delete(int sid){
		simpleDaoSupport.delete(TeePortlet.class, sid);
	}
	
	public TeePortlet getPortlet(int sid){
		return (TeePortlet) simpleDaoSupport.get(TeePortlet.class, sid);
	}
	
	public void updateStatus(int sid,int flag){
		simpleDaoSupport.executeUpdate("update TeePortlet set viewType="+flag+" where sid="+sid, null);
	}
	
	public TeeEasyuiDataGridJson datagrid(){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List<TeePortlet> list = simpleDaoSupport.find("from TeePortlet order by sortNo asc", null);
		long total = simpleDaoSupport.count("select count(*) from TeePortlet", null);
		dataGridJson.setRows(list);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	public String getPersonalDesktop(){
		TeePerson loginUser = 
				(TeePerson) simpleDaoSupport.get(TeePerson.class, TeeRequestInfoContext.getRequestInfo().getUserSid());
		Map map = simpleDaoSupport.getMap("select p.desktop as desktop from TeePerson p where p.uuid=?", new Object[]{loginUser.getUuid()});
		return TeeStringUtil.getString(map.get("desktop"));
	}
	
	public void updatePersonDesktop(String desktop){
		simpleDaoSupport.executeUpdate("update TeePerson p set p.desktop=? where p.uuid=?", new Object[]{desktop,TeeRequestInfoContext.getRequestInfo().getUserSid()});
	}
	
	public String getUseablePortlet(){
		List<Map> list = simpleDaoSupport.getMaps("select sid as sid from TeePortlet where viewType=1", null);
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<list.size();i++){
			sb.append(list.get(i).get("sid"));
			if(i!=list.size()-1){
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 渲染模块
	 * @param sid
	 * @return
	 */
	@Transactional(readOnly=true)
	public Map renderPortlet(final Map requestDatas){
		Map data = new HashMap();
		int sid = TeeStringUtil.getInteger(requestDatas.get("sid"), 0);
		final TeePortlet portlet = getPortlet(sid);
		if(portlet==null){
			return null;
		}
		//渲染
		String contentTpl = portlet.getContentTpl();
		final TeePerson loginUser = 
				(TeePerson) simpleDaoSupport.get(TeePerson.class, TeeRequestInfoContext.getRequestInfo().getUserSid());
		
		if(portlet.getContentType()==1){
			TeeRegexpAnalyser analyser = new TeeRegexpAnalyser(contentTpl);
			contentTpl = analyser.replace(new String[]{"(<@[a-zA-z0-9]+>)[^@]*(</@[a-zA-z0-9]+>)"}, new TeeExpFetcher() {
				
				@Override
				public String parse(String pattern) {
					// TODO Auto-generated method stub
					StringBuffer sb = new StringBuffer();
					int index1 = pattern.indexOf(">");
					String tagName = pattern.substring(2,index1);//获取TAGName
					int index2 = pattern.indexOf("</@", index1);
					String content = pattern.substring(index1+1, index2);//获取标签内的内容
					String newContent = content;
					
					TeeDataGridModel dm = new TeeDataGridModel();
					dm.setRows(portlet.getRows());
					dm.setPage(1);
					requestDatas.put("dfid", tagName);
					
					TeeEasyuiDataGridJson easyuiDataGridJson = bisViewService.dflist(dm, requestDatas);
					if(easyuiDataGridJson==null || easyuiDataGridJson.getTotal()==0){
						return "";
					}
					
					List<Map> datas = easyuiDataGridJson.getRows();
					Set<String> keys = null;
					for(Map data:datas){
						newContent = content;
						keys = data.keySet();
						for(String key : keys){
							if(data.get(key) instanceof Date){
								newContent = newContent.replace("${"+key+"}", TeeDateUtil.format(((Date)data.get(key)), "yyyy-MM-dd HH:mm:ss")+"");
							}else{
								newContent = newContent.replace("${"+key+"}", data.get(key)+"");
							}
							
						}
						sb.append(newContent);
					}
					
					return sb.toString();
				}
			});
			data.put("content", contentTpl);
			data.put("autoRefresh", portlet.getAutoRefresh());
			data.put("title", portlet.getModelTitle());
			data.put("rows", portlet.getRows());
			data.put("sid", portlet.getSid());
			data.put("moreUrl", portlet.getMoreUrl());
			data.put("icons", portlet.getModelIcons());
		}else{
			contentTpl = portlet.getContentTpl();
			
			ExpressionCompiler compiler = new ExpressionCompiler(contentTpl);
			ParserContext context = new ParserContext();
//			context.setStrictTypeEnforcement(true);
			context.addImport(List.class);
			context.addImport(ArrayList.class);
			context.addImport(Map.class);
			context.addImport(HashMap.class);
			context.addImport(Date.class);
			context.addImport(Calendar.class);
			context.addImport(TeeStringUtil.class);
			context.addImport(TeeDateUtil.class);
			
			compiler.setPCtx(context);
			
			CompiledExpression exp = compiler.compile(context);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("SYS_RV", "");//模板返回值
			params.put("SYS_DB", simpleDaoSupport);
			MVEL.executeExpression(exp, params);
			
			String SYS_RV = (String) params.get("SYS_RV");
			
			
			data.put("content", SYS_RV);
			data.put("title", portlet.getModelTitle());
			data.put("rows", portlet.getRows());
			data.put("sid", portlet.getSid());
			data.put("moreUrl", portlet.getMoreUrl());
			data.put("icons", portlet.getModelIcons());
			data.put("autoRefresh", portlet.getAutoRefresh());
		}
		
		
		return data;
	}
	
	
	
	public String exportXml(int sid){
		TeePortlet portlet = getPortlet(sid);
		Element root;   
		root=new Element("Portlet");
		
		root.addContent(new Element("modelTitle").setText(TeeStringUtil.getString(portlet.getModelTitle())));
		root.addContent(new Element("modelIcons").setText(TeeStringUtil.getString(portlet.getModelIcons())));
		root.addContent(new Element("contentTpl").setText(TeeStringUtil.getString(portlet.getContentTpl())));
		root.addContent(new Element("contentType").setText(TeeStringUtil.getString(portlet.getContentType()+"")));
		root.addContent(new Element("moreUrl").setText(TeeStringUtil.getString(portlet.getMoreUrl())));
		root.addContent(new Element("sortNo").setText(TeeStringUtil.getString(portlet.getSortNo()+"")));
		root.addContent(new Element("rows").setText(TeeStringUtil.getString(portlet.getRows()+"")));
		root.addContent(new Element("viewType").setText(TeeStringUtil.getString(portlet.getViewType()+"")));
		root.addContent(new Element("remark").setText(TeeStringUtil.getString(portlet.getRemark())));
		
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
		
		String modelTitle = root.getChildText("modelTitle");
		String modelIcons = root.getChildText("modelIcons");
		String contentTpl = root.getChildText("contentTpl");
		int contentType = TeeStringUtil.getInteger(root.getChildText("contentType"), 0);
		String moreUrl = root.getChildText("moreUrl");
		int sortNo = TeeStringUtil.getInteger(root.getChildText("sortNo"),0);
		int rows = TeeStringUtil.getInteger(root.getChildText("rows"),0);
		int viewType = TeeStringUtil.getInteger(root.getChildText("viewType"),0);
		String remark = root.getChildText("remark");
		
		TeePortlet portlet = new TeePortlet();
		portlet.setModelIcons(modelIcons);
		portlet.setModelTitle(modelTitle);
		portlet.setContentTpl(contentTpl);
		portlet.setContentType(contentType);
		portlet.setMoreUrl(moreUrl);
		portlet.setSortNo(sortNo);
		portlet.setRows(rows);
		portlet.setViewType(viewType);
		portlet.setRemark(remark);
		
		simpleDaoSupport.save(portlet);
		
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


