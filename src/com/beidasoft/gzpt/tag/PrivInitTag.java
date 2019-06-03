package com.beidasoft.gzpt.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.util.Assert;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;

public class PrivInitTag extends SimpleTagSupport {
	private static SessionFactory sessionFactory;

	private String menuId;

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public void doTag() throws JspException, IOException {
		HttpServletRequest request = (HttpServletRequest) ((PageContext) this.getJspContext()).getRequest();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int personId = person.getUuid();
		String menuId = request.getParameter("menuId");
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		sessionFactory = (SessionFactory) webApplicationContext.getBean("sessionFactory");
		Assert.notNull(sessionFactory, "sessionFactory 对象为空!");
		Session session = sessionFactory.openSession();

		String sql = "select t1.*,NVL2( t2.BUTTON_ID, 'true', 'false' ) IS_PRIV " + " from "
				+ "  (select * from menu_button mb where mb.MENU_ID = ? ) t1  " + " LEFT OUTER JOIN "
				+ "  (select distinct mgmb.BUTTON_ID " + "     from person p, " + "          person_menu_group pmg, "
				+ "          menu_group_menu_button mgmb " + "    where p.uuid = pmg.PERSON_UUID "
				+ "      and pmg.MENU_GROUP_UUID = mgmb.GROUP_UUID " + "      and p.uuid = ?) t2 "
				+ "       ON t1.ID = t2.BUTTON_ID ";
		Object[] param = { menuId, personId };
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		query.setParameter(0, menuId);
		query.setParameter(1, personId);
		List<Map<String,String>> result = query.list();
		Map<String,String> btnPriv = new HashMap<String, String>();
		for(Map<String,String> map : result) {
			btnPriv.put(map.get("BUTTON_NO"), map.get("IS_PRIV"));
		}
		
		request.setAttribute("loginUser", person.getUserName());
		request.setAttribute("personId", personId);
		request.setAttribute("privMap", result);
		request.setAttribute("btnPriv", btnPriv);
		session.close();
		super.doTag();
	}

}
