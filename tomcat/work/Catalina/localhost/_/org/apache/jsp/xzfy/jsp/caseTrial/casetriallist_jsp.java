package org.apache.jsp.xzfy.jsp.caseTrial;

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

public final class casetriallist_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("/common/js/src/orgselect.js\"></script>\r\n");
      out.write("<!--\r\n");
      out.write("\r\n");
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
      out.write("\t<title>审理列表</title>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/xzfy/js/common/common.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/common/My97DatePicker/WdatePicker.js\"></script>\r\n");
      out.write("\t<style type=\"text/css\">\r\n");
      out.write("\t.tab-container{\r\n");
      out.write("   \t    width: 100%;\r\n");
      out.write("\t    height: 30px;\r\n");
      out.write("\t    border-bottom: 1px solid blue;\r\n");
      out.write("\t    position: relative;\r\n");
      out.write("   \t}\r\n");
      out.write("   \t\r\n");
      out.write("   \t.case-tab{\r\n");
      out.write("\t   \tfloat:left;\r\n");
      out.write("\t   \tmargin-right:5px;\r\n");
      out.write("   \t    /* display: inline-block; */\r\n");
      out.write("\t    text-align: center;\r\n");
      out.write("\t    line-height: 30px;\r\n");
      out.write("\t    width: 55px;\r\n");
      out.write("\t    height: 30px;\r\n");
      out.write("\t    border: 1px solid blue;\r\n");
      out.write("\t    border-radius: 3px 3px 0 0;\r\n");
      out.write("   \t}\r\n");
      out.write("   \t.actived-tables{\r\n");
      out.write("   \t\tfloat:left;\r\n");
      out.write("\t   \tmargin-right:5px;\r\n");
      out.write("   \t   /*  display: inline-block; */\r\n");
      out.write("\t    text-align: center;\r\n");
      out.write("\t    line-height: 30px;\r\n");
      out.write("\t    width: 55px;\r\n");
      out.write("\t    height: 30px;\r\n");
      out.write("\t    border: 1px solid blue;\r\n");
      out.write("\t    border-radius: 3px 3px 0 0;\r\n");
      out.write("\t    border-bottom: none;\r\n");
      out.write("\t    position: relative;\r\n");
      out.write("\t   /*  bottom: -2px; */\r\n");
      out.write("\t    z-index: 3;\r\n");
      out.write("\t    background: white;\r\n");
      out.write("\t    border-bottom-radius: 0px;\r\n");
      out.write("   \t}\r\n");
      out.write("\t</style>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body style=\"height:94%\">\r\n");
      out.write("   <!--  \t\r\n");
      out.write("   \t<div class='tab-container' id=\"container\">\r\n");
      out.write("\t   \t<span id=\"needDeal\" class='actived-tables' onclick=\"change(this,'0')\">待办</span>\r\n");
      out.write("\t   \t<span id=\"alreadyDeal\" class='case-tab' onclick=\"change(this,'1')\">已办</span>\r\n");
      out.write("   \t</div>\r\n");
      out.write("    -->\r\n");
      out.write("    <iframe id=\"exportIframe\" style=\"width:100%;height:100%\" src=\"/xzfy/jsp/caseTrial/casehandle/agency.jsp\"></iframe>\r\n");
      out.write("\r\n");
      out.write("    <script type=\"text/javascript\">\r\n");
      out.write("\t    //tab页切换\r\n");
      out.write("\t\tfunction change(that,type){\r\n");
      out.write("\t    \t$(that).siblings().removeClass().addClass(\"case-tab\");\r\n");
      out.write("\t\t\t$(that).removeClass().addClass(\"actived-tables\");\r\n");
      out.write("\t\t\t//赋值\r\n");
      out.write("\t\t\tif(type==0){\r\n");
      out.write("\t\t\t\t$(\"#exportIframe\").attr(\"src\",\"/xzfy/jsp/caseTrial/casehandle/agency.jsp\");\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tif(type==1){\r\n");
      out.write("\t\t\t\t$(\"#exportIframe\").attr(\"src\",\"/xzfy/jsp/caseTrial/casehandle/done.jsp\");\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t$(\"#exportIframe\").show();\r\n");
      out.write("\t\t}\r\n");
      out.write("     </script>\r\n");
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
