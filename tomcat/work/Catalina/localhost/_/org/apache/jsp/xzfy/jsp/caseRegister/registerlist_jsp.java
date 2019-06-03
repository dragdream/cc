package org.apache.jsp.xzfy.jsp.caseRegister;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.tianee.webframe.util.servlet.TeeCookieUtils;
import java.util.*;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeModelIdConst;
import com.tianee.webframe.util.servlet.TeeCookieUtils;

public final class registerlist_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(2);
    _jspx_dependants.add("/header/header2.0.jsp");
    _jspx_dependants.add("/header/easyui2.0.jsp");
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

String contextPath = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ contextPath + "/";
//获取主题的索引号
int styleIndex = 1;
Integer styleInSession = (Integer) request.getSession().getAttribute("STYLE_TYPE_INDEX");
if (styleInSession != null) {
	styleIndex = styleInSession;
}
String stylePath = contextPath + "/common/styles";
String imgPath = stylePath + "/style" + styleIndex + "/img";
String cssPath = stylePath + "/style" + styleIndex + "/css";
String systemImagePath = contextPath+"/common/images";

//第二套风格
int STYLE_TYPE_INDEX_2 = TeeStringUtil.getInteger( request.getSession().getAttribute("STYLE_TYPE_INDEX_2"), 1);
String cssPathSecond = contextPath + "/system/frame/2/styles/style" + STYLE_TYPE_INDEX_2 + "/css";
String imgPathSecond = contextPath + "/system/frame/2/styles/style" + STYLE_TYPE_INDEX_2 + "/img";


String loginOutText = TeeSysProps.getString("LOGIN_OUT_TEXT");
String ieTitle = TeeSysProps.getString("IE_TITLE");
String secUserMem = TeeSysProps.getString("SEC_USER_MEM");

Cookie cookie = TeeCookieUtils.getCookie(request, "skin_new");
String skinNew = "1";
if(cookie!=null){
	skinNew = cookie.getValue();
}

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- zt_webframe框架引入 jquery -->\r\n");
      out.write("<script src=\"");
      out.print(contextPath );
      out.write("/common/jquery-easyui-1.6.11/jquery.min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- zt_webframe框架引入 核心库 -->\r\n");
      out.write("<script src=\"");
      out.print(contextPath );
      out.write("/common/zt_webframe/js/package.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(contextPath );
      out.write("/common/js/sys2.0.js?v=2\"></script>\r\n");
      out.write("<script src=\"");
      out.print(contextPath );
      out.write("/common/js/sysUtil.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(contextPath );
      out.write("/common/js/src/orgselect.js\"></script>\n");
      out.write("<!--\n");
      out.write("\n");
      out.write("//-->\r\n");
      out.write("<!-- zt_webframe框架引入 css样式 -->\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(request.getContextPath());
      out.write("/common/zt_webframe/css/init");
      out.print(skinNew );
      out.write(".css\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(request.getContextPath());
      out.write("/common/zt_webframe/css/package");
      out.print(skinNew );
      out.write(".css\">\r\n");
      out.write("\r\n");
      out.write("<script src=\"");
      out.print(contextPath );
      out.write("/common/js/tools2.0.js?v=1\"></script>\r\n");
      out.write("<script src=\"");
      out.print(contextPath );
      out.write("/common/js/TeeMenu.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("/** 变量定义 **/\r\n");
      out.write("var contextPath = \"");
      out.print(contextPath );
      out.write("\";\r\n");
      out.write("var systemImagePath = contextPath+\"/common/images\";\r\n");
      out.write("var uploadFlashUrl = \"");
      out.print(contextPath );
      out.write("/common/swfupload/swfupload.swf\";\r\n");
      out.write("var commonUploadUrl = \"");
      out.print(contextPath );
      out.write("/attachmentController/commonUpload.action;jsessionid=");
      out.print(session.getId());
      out.write("\";\r\n");
      out.write("var xparent;\r\n");
      out.write("var stylePath = \"");
      out.print(stylePath);
      out.write("\";\r\n");
      out.write("if(window.dialogArguments){\r\n");
      out.write("\txparent = window.dialogArguments;\r\n");
      out.write("}else if(window.opener){\r\n");
      out.write("\txparent = opener;\r\n");
      out.write("}else{\r\n");
      out.write("\txparent = window;\r\n");
      out.write("}\r\n");
      out.write("window.UEDITOR_HOME_URL = \"");
      out.print(contextPath);
      out.write("/common/ueditor/\";\r\n");
      out.write("$.browser = {};\r\n");
      out.write("$.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());\r\n");
      out.write("$.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());\r\n");
      out.write("$.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());\r\n");
      out.write("$.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());\r\n");
      out.write("</script>\r\n");
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- zt_webframe框架引入 核心库 -->\r\n");
      out.write("<script type=\"text/javascript\" src = '");
      out.print(request.getContextPath() );
      out.write("/common/jquery-easyui-1.6.11/jquery.easyui.min.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src = '");
      out.print(request.getContextPath() );
      out.write("/common/zt_webframe/js/jquery.datagrid.extend.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src = '");
      out.print(request.getContextPath() );
      out.write("/common/jquery-easyui-1.6.11/locale/easyui-lang-zh_CN.js'></script>\r\n");
      out.write("\r\n");

Cookie __cookie = TeeCookieUtils.getCookie(request, "skin_new");
String __skinNew = "1";
if(__cookie!=null){
	__skinNew = __cookie.getValue();
}

      out.write("\r\n");
      out.write("<!-- zt_webframe框架引入 css样式 -->\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(request.getContextPath());
      out.write("/common/jquery-easyui-1.6.11/themes/metro/easyui.css\">\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("\t<title>登记列表</title>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/xzfy/js/common/common.js\"></script>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body style=\"padding-left: 10px;padding-right: 10px\" onload=\"doInit();\">\r\n");
      out.write("\r\n");
      out.write("   <div id=\"toolbar\" class = \" clearfix\" style=\"margin-top: 5px\">\r\n");
      out.write("\t    <div class=\"fl\" style=\"position:static\">\r\n");
      out.write("\t\t    <img id=\"img1\" class = 'title_img' src=\"");
      out.print(contextPath );
      out.write("/common/zt_webframe/imgs/jhrw/icon_任务中心.png\">\r\n");
      out.write("\t\t    <span class=\"title\">登记列表 </span>\r\n");
      out.write("\t    </div>\r\n");
      out.write("\t    <div class = \"right fr clearfix\">\r\n");
      out.write("\t        <input type=\"button\" class=\"btn-win-white\" onclick=\"addOrUpdate('','0');\" value=\"新建\"/>&nbsp;\r\n");
      out.write("\t\t\t<input type=\"button\" class=\"btn-del-red fl\" onclick=\"delByIds()\" value=\"删除\"/>&nbsp;\r\n");
      out.write("\t\t\t<input type=\"button\" class=\"btn-win-white\" onclick=\"exportExcel()\" value=\"导出\"/>\r\n");
      out.write("\t    \r\n");
      out.write("\t    </div>\r\n");
      out.write("\t    <span class=\"basic_border_grey\" style=\"margin-top: 10px\"></span>\r\n");
      out.write("\t    \r\n");
      out.write("\t    <div class=\"setHeight\">\r\n");
      out.write("\t        <form id=\"form1\" style=\"\">\r\n");
      out.write("\t            <input type=\"hidden\" id=\"orgId\" value=\"\" >\r\n");
      out.write("\t            <table class=\"none_table\" width=\"100%\">\r\n");
      out.write("\t \t\t\t\t<tr>\r\n");
      out.write("\t \t\t\t\t\t<td class=\"TableData TableBG\">案件编号：</td>\r\n");
      out.write("\t \t\t\t\t\t<td class=\"TableData\">\r\n");
      out.write("\t \t\t\t\t\t    <input class=\"BigInput\" type=\"text\" name=\"personName\" id=\"personName\"\r\n");
      out.write("\t \t\t\t\t\t        onkeyup=\"this.value=this.value.replace(/[^\\u4e00-\\u9fa5a-zA-Z0-9\\w]/g,'')\" />\r\n");
      out.write("\t \t\t\t\t\t</td>\r\n");
      out.write("\t \t\t\t\t\t\r\n");
      out.write("\t \t\t\t\t\t<td class=\"TableData TableBG\" >申请方式：</td>\r\n");
      out.write(" \t\t\t\t\t\t<td class=\"TableData\">\r\n");
      out.write("\t \t\t\t\t\t\t<select class=\"BigSelect\" id=\"staffing\" name=\"staffing\">\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t</select>\r\n");
      out.write("\t \t\t\t\t\t</td>\r\n");
      out.write("\t \t\t\t\t\t\r\n");
      out.write("\t \t\t\t\t\t<td class=\"TableData TableBG\">申请人：</td>\r\n");
      out.write("\t \t\t\t\t\t<td class=\"TableData\">\r\n");
      out.write("\t \t\t\t\t\t    <input class=\"BigInput\" type=\"text\" name=\"personName\" id=\"personName\"\r\n");
      out.write("\t \t\t\t\t\t        onkeyup=\"this.value=this.value.replace(/[^\\u4e00-\\u9fa5a-zA-Z0-9\\w]/g,'')\" />\r\n");
      out.write("\t \t\t\t\t\t</td>\r\n");
      out.write("\t \t\t\t\t\t\r\n");
      out.write("\t \t\t\t\t\t\r\n");
      out.write("\t \t\t\t\t\t<td class=\"TableData TableBG\">开始时间：</td>\r\n");
      out.write("\t \t\t\t\t\t<td class=\"TableData\">\r\n");
      out.write("\t \t\t\t\t\t    <input class=\"BigInput\" type=\"text\" name=\"personName\" id=\"personName\"\r\n");
      out.write("\t \t\t\t\t\t        onkeyup=\"this.value=this.value.replace(/[^\\u4e00-\\u9fa5a-zA-Z0-9\\w]/g,'')\" />\r\n");
      out.write("\t \t\t\t\t\t</td>\r\n");
      out.write("\t \t\t\t\t\t\r\n");
      out.write("\t \t\t\t\t\t<td class=\"TableData TableBG\">结束时间：</td>\r\n");
      out.write("\t \t\t\t\t\t<td class=\"TableData\">\r\n");
      out.write("\t \t\t\t\t\t    <input class=\"BigInput\" type=\"text\" name=\"personName\" id=\"personName\"\r\n");
      out.write("\t \t\t\t\t\t        onkeyup=\"this.value=this.value.replace(/[^\\u4e00-\\u9fa5a-zA-Z0-9\\w]/g,'')\" />\r\n");
      out.write("\t \t\t\t\t\t</td>\r\n");
      out.write("\t \t\t\t\t\t\r\n");
      out.write(" \t\t\t\t\t\t<td class=\"TableData\"><button class=\"btn-win-white\" type=\"button\" onclick=\"search()\">查询</button></td>\r\n");
      out.write("\t \t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t</form>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("   \t</div>\r\n");
      out.write("    <table id=\"datagrid\" fit=\"true\"></table> \r\n");
      out.write("    <iframe id=\"exportIframe\" style=\"display:none\"></iframe>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"/xzfy/js/caseRegister/registerlist.js\"></script>\r\n");
      out.write("    \r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else log(t.getMessage(), t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
