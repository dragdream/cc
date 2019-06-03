package com.beidasoft.gzpt.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;

public class PrivAButtonTag extends SimpleTagSupport {
	private boolean priv;
	private String id;
	private String name;
	private String cls;
	private String style;
	private String title;
	private String value;
	private String code;
	private String url;
	private String href;
	private String onclick;

	public void setPriv(boolean priv) {
		this.priv = priv;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	@Override
	public void doTag() throws IOException, JspException {
		if (priv) {
			HttpServletRequest request = (HttpServletRequest) ((PageContext) this.getJspContext()).getRequest();
			TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			System.out.println(basePath);
			System.out.println(person.toString());
			if (code != null) {

			}

			if (url != null) {

			}

			StringBuffer sBuffer = new StringBuffer();
			sBuffer.append("<a ");
			if (id != null) {
				sBuffer.append(" id=\"");
				sBuffer.append(id);
				sBuffer.append("\"");
			}

			if (name != null) {
				sBuffer.append(" name=\"");
				sBuffer.append(name);
				sBuffer.append("\"");
			}

			if (cls != null) {
				sBuffer.append(" class=\"");
				sBuffer.append(cls);
				sBuffer.append("\"");
			} else {
				sBuffer.append(" class=\"\"");
			}

			if (style != null) {
				sBuffer.append(" style=\"");
				sBuffer.append(style);
				sBuffer.append("\"");
			}

			if (title != null) {
				sBuffer.append(" title=\"");
				sBuffer.append(title);
				sBuffer.append("\"");
			}

			if (href != null) {
				sBuffer.append(" href=\"");
				sBuffer.append(href);
				sBuffer.append("\"");
			} else {
				sBuffer.append(" href=\"javascript:void(0);\"");
			}

			if (onclick != null) {
				sBuffer.append(" onclick=\"");
				sBuffer.append(onclick);
				sBuffer.append("\"");
			} else {
				sBuffer.append(" onclick=\"javascript:void(0);\"");
			}
			sBuffer.append(">" + value + "</a>");
			JspWriter out = getJspContext().getOut();
			out.write(sBuffer.toString());
		}
	}
}
