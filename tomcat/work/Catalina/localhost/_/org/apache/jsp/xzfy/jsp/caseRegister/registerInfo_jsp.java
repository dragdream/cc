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

public final class registerInfo_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(3);
    _jspx_dependants.add("/header/header2.0.jsp");
    _jspx_dependants.add("/header/easyui.jsp");
    _jspx_dependants.add("/header/ztree.jsp");
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
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/common/easyui/jquery.easyui.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/common/easyui/locale/easyui-lang-zh_CN.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath() );
      out.write("/common/js/src/teeValidagteBox.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/common/easyui/datagrid-groupview.js\"></script>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(request.getContextPath());
      out.write("/common/easyui/themes/gray/easyui.css\">");
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("<!-- zTree库 -->\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(request.getContextPath() );
      out.write("/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css\" type=\"text/css\"/>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath() );
      out.write("/common/jquery/ztree/js/jquery.ztree.core-3.5.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath() );
      out.write("/common/jquery/ztree/js/jquery.ztree.excheck-3.5.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath() );
      out.write("/common/jquery/ztree/js/jquery.ztree.exedit-3.5.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath() );
      out.write("/common/js/ZTreeSync.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\"/>\r\n");
      out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n");
      out.write("    <link rel=\"stylesheet\" href=\"/xzfy/css/init1.css\" />\r\n");
      out.write("    <script type=\"text/javascript\" src=\"/xzfy/js/common/common.js\"></script>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"/xzfy/js/base/juicer-min.js\"></script>\r\n");
      out.write("    \r\n");
      out.write("    <title>登记信息</title>\r\n");
      out.write("    <style rel=\"stylesheet\" type=\"text/css\">\r\n");
      out.write("    .main-left {\r\n");
      out.write("        width: 100px;\r\n");
      out.write("        display: inline-block;\r\n");
      out.write("    }\r\n");
      out.write("    .main-right {\r\n");
      out.write("        width: 90%;\r\n");
      out.write("        height: 95%;\r\n");
      out.write("        display: inline-block;\r\n");
      out.write("        vertical-align: top;\r\n");
      out.write("    }\r\n");
      out.write("    .lanky-tab {\r\n");
      out.write("        width: 73px;\r\n");
      out.write("        height: 35px;\r\n");
      out.write("        margin-top: 14px;\r\n");
      out.write("        border: solid 1px burlywood;\r\n");
      out.write("    }\r\n");
      out.write("    .head {\r\n");
      out.write("        width: 100%;\r\n");
      out.write("        height: 51px;\r\n");
      out.write("        line-height: 51px;\r\n");
      out.write("        font-size: 18px;\r\n");
      out.write("        background-color: #F7F7F7;\r\n");
      out.write("    }\r\n");
      out.write("    .content {\r\n");
      out.write("        width: 100%;\r\n");
      out.write("    }\r\n");
      out.write("    .lanky-star {\r\n");
      out.write("        color: red;\r\n");
      out.write("        font-weight: bold;\r\n");
      out.write("    }\r\n");
      out.write("    .lanky-tr {\r\n");
      out.write("        height: 35px;\r\n");
      out.write("        font-size: 18px;\r\n");
      out.write("        color: #343434;\r\n");
      out.write("        background-color: #F7F7F7;\r\n");
      out.write("    }\r\n");
      out.write("    .lanky-th {\r\n");
      out.write("        line-height: 35px;\r\n");
      out.write("        font-size: 18px;\r\n");
      out.write("        color: #3376C3;\r\n");
      out.write("    }\r\n");
      out.write("    .lanky-item {\r\n");
      out.write("        width: 10%;\r\n");
      out.write("        margin: 0 auto;\r\n");
      out.write("        text-indent: 10px;\r\n");
      out.write("        vertical-align: middle;\r\n");
      out.write("        text-align: right;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    .lanky-td {\r\n");
      out.write("        width: 15%;\r\n");
      out.write("        text-align: left;\r\n");
      out.write("    }\r\n");
      out.write("    .lanky-info {\r\n");
      out.write("        border: 1px solid #cfcfcf;\r\n");
      out.write("        background-color: #F7F7F7;\r\n");
      out.write("    }\r\n");
      out.write("    .lanky-p {\r\n");
      out.write("        height: 70px;\r\n");
      out.write("    }\r\n");
      out.write("    table {\r\n");
      out.write("        width: 100%;\r\n");
      out.write("    }\r\n");
      out.write("    table td,\r\n");
      out.write("    table th {\r\n");
      out.write("        border: none;\r\n");
      out.write("    }\r\n");
      out.write("    table {\r\n");
      out.write("        /* border-top: 1px solid #cfcfcf;\r\n");
      out.write("        border-left: 1px solid #cfcfcf; */\r\n");
      out.write("        border: 1px solid #cfcfcf;\r\n");
      out.write("        border-collapse: separate;\r\n");
      out.write("        border-spacing: 0px 10px;\r\n");
      out.write("    }\r\n");
      out.write("    table td {\r\n");
      out.write("        height: 30px;\r\n");
      out.write("        line-height: 30px;\r\n");
      out.write("        font-size: 15px;\r\n");
      out.write("        text-indent: 5px;\r\n");
      out.write("    }\r\n");
      out.write("    p {\r\n");
      out.write("        word-wrap: break-word;\r\n");
      out.write("        overflow: auto\r\n");
      out.write("    }\r\n");
      out.write("    input {\r\n");
      out.write("        vertical-align: inherit;\r\n");
      out.write("    }\r\n");
      out.write("    .lanky-input {\r\n");
      out.write("        height: 99%;\r\n");
      out.write("        width: 96%;\r\n");
      out.write("        vertical-align: inherit;\r\n");
      out.write("    }\r\n");
      out.write("    </style>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body onload=\"doInit()\">\r\n");
      out.write("    <div class=\"main-left\">\r\n");
      out.write("        <ul>\r\n");
      out.write("            <li class=\"lanky-tab\">登记信息</li>\r\n");
      out.write("            <li class=\"lanky-tab\">案件信息</li>\r\n");
      out.write("            <li class=\"lanky-tab\">立案受理</li>\r\n");
      out.write("            <li class=\"lanky-tab\">不予受理</li>\r\n");
      out.write("            <li class=\"lanky-tab\">补正</li>\r\n");
      out.write("            <li class=\"lanky-tab\">告知</li>\r\n");
      out.write("            <li class=\"lanky-tab\">转送</li>\r\n");
      out.write("            <li class=\"lanky-tab\">其他</li>\r\n");
      out.write("            <li class=\"lanky-tab\">案卷管理</li>\r\n");
      out.write("            <li class=\"lanky-tab\">归档管理</li>\r\n");
      out.write("            <li class=\"lanky-tab\">办理进度</li>\r\n");
      out.write("        </ul>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div class=\"main-right\">\r\n");
      out.write("        <div class=\"head\">\r\n");
      out.write("            <span>案件编号:</span>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"content reg-info\" id=\"register-div\"></div>\r\n");
      out.write("        <div class=\"content\" id=\"case-div\"></div>\r\n");
      out.write("        <div class=\"content\" id=\"accept-div\"></div>\r\n");
      out.write("        <div class=\"content\" id=\"refuse-div\"></div>\r\n");
      out.write("        <div class=\"content\" id=\"correction-div\"></div>\r\n");
      out.write("        <div class=\"content\" id=\"inform-div\"></div>\r\n");
      out.write("        <div class=\"content\" id=\"forward-div\"></div>\r\n");
      out.write("        <div class=\"content\" id=\"other-div\"></div>\r\n");
      out.write("        <div class=\"content\" id=\"file-div\"></div>\r\n");
      out.write("        <div class=\"content\" id=\"arichive-div\"></div>\r\n");
      out.write("        <div class=\"content\" id=\"flow-div\"></div>\r\n");
      out.write("    </div>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"/xzfy/js/caseRegister/caseTpl.js\"></script>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"/xzfy/js/caseRegister/registerInfo.js\"></script>\r\n");
      out.write("    \r\n");
      out.write("</body>\r\n");
      out.write("\r\n");
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
