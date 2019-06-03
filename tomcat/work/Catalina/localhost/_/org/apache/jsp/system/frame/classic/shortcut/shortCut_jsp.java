package org.apache.jsp.system.frame.classic.shortcut;

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

public final class shortCut_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/header/header2.0.jsp");
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

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/reset.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" href=\"css/pto.css?v=5\">\r\n");
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
      out.write("\r\n");
      out.write("<title>快捷菜单页面</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"doInit();\">\r\n");
      out.write("\r\n");
      out.write("<div class=\"a\">\r\n");
      out.write("\r\n");
      out.write("\t<ul class=\"b\" id=\"menuUl\">\r\n");
      out.write("\t</ul>\r\n");
      out.write("</div>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("    //初始化方法\r\n");
      out.write("    function doInit(){\r\n");
      out.write("    \t//获取当前登陆人所有的快捷菜单\r\n");
      out.write("    \tvar url=contextPath+\"/personManager/getQuickMenus.action\";\r\n");
      out.write("    \tvar json=tools.requestJsonRs(url);\r\n");
      out.write("    \tif(json.rtState){\r\n");
      out.write("    \t\tvar data=json.rtData;\r\n");
      out.write("    \t\tvar html=[];\r\n");
      out.write("    \t\tif(data!=null&&data.length>0){\r\n");
      out.write("    \t\t\tfor(var i=0;i<data.length;i++){\r\n");
      out.write("    \t\t\t\tif(data[i].icon==\"\" || data[i].icon==undefined){\r\n");
      out.write("    \t\t\t\t\tdata[i].icon = \"default.png\";\r\n");
      out.write("    \t\t\t\t}\r\n");
      out.write("    \t\t\t\thtml.push(\"<li onclick=\\\"clickMenu('\"+data[i].menuCode+\"','\"+data[i].menuName+\"');\\\" class=\\\"c\\\" style=\\\"background: url('img/\"+data[i].icon+\"')\\\"><span class=\\\"d\\\">\"+data[i].menuName+\"</span></li>\");\r\n");
      out.write("    \t\t\t}\r\n");
      out.write("    \t\t}\r\n");
      out.write("    \t\t$(\"#menuUl\").append(html.join(\"\"));\r\n");
      out.write("    \t}\r\n");
      out.write("    \t\r\n");
      out.write("    \tresizeFunc();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    //点击菜单调用的方法\r\n");
      out.write("    function clickMenu(menuCode,menuName){\r\n");
      out.write("    \tparent.parent.NewPageINfo(menuName,menuCode);\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    \r\n");
      out.write("\tvar clickMore = false;\r\n");
      out.write("\tfunction resizeFunc(){\r\n");
      out.write("\t\t//清空父元素中的“更多”的li\r\n");
      out.write("\t\t$(\".a li.more\").remove();\r\n");
      out.write("\t\t$(\".a\").css(\"overflow\",\"hidden\");\r\n");
      out.write("\t\tvar list = getClassNames('c' , 'li');\r\n");
      out.write("\t\tfunction getClassNames(classStr,tagName){\r\n");
      out.write("\t\t\tif (document.getElementsByClassName) {\r\n");
      out.write("\t\t\t\treturn document.getElementsByClassName(classStr)\r\n");
      out.write("\t\t\t}else {\r\n");
      out.write("\t\t\t\tvar nodes = document.getElementsByTagName(tagName),ret = [];\r\n");
      out.write("\t\t\t\tfor(i = 0; i < nodes.length; i++) {\r\n");
      out.write("\t\t\t\t\tif(hasClass(nodes[i],classStr)){\r\n");
      out.write("\t\t\t\t\t\tret.push(nodes[i])\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\treturn ret;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfunction hasClass(tagStr,classStr){\r\n");
      out.write("\t\t\tvar arr=tagStr.className.split(/\\s+/ );  //这个正则表达式是因为class可以有多个,判断是否包含\r\n");
      out.write("\t\t\tfor (var i=0;i<arr.length;i++){\r\n");
      out.write("\t\t\t\tif (arr[i]==classStr){\r\n");
      out.write("\t\t\t\t\treturn true ;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\treturn false ;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tvar heights='';\r\n");
      out.write("\t\tvar heights2='';\r\n");
      out.write("\t\tvar ccc=0;\r\n");
      out.write("\t\tvar hasOverflow = false;\r\n");
      out.write("\t\tfor(var i = 0; i < list.length; i++) {\r\n");
      out.write("\t\t\theights2=$('.c').eq(i).index();\r\n");
      out.write("\t\t\theights=$('.c').eq(i).height() + $('.c').eq(i).offset().top;\r\n");
      out.write("\t\t\tlist[i].index = i;\r\n");
      out.write("\t\t\t//console.log( list[i].index + 1 + \" \" +heights);\r\n");
      out.write("\t\t\tx =  $('.a').height();\r\n");
      out.write("\t\t\t//console.log(xx);\r\n");
      out.write("\t\t\tif( heights>x){\r\n");
      out.write("\t\t\t\tccc++;\r\n");
      out.write("\t\t\t\thasOverflow = true;\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t$(\".a\").css(\"overflow\",\"hidden\");\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t};\r\n");
      out.write("\t\t//console.log(ccc);\r\n");
      out.write("\t\t//console.log(heights2 - ccc);\r\n");
      out.write("\r\n");
      out.write("\t\t//如果不存在更多按钮的话\r\n");
      out.write("\t\tif($(\".a li.more\").length==0 && hasOverflow){\r\n");
      out.write("\t\t\t//var obj = $(\"<li class='more c'><span class='d'>更多</span></li>\");\r\n");
      out.write("\t\t\tvar obj = $(\"<li class='more c' style='background: url(css/gengduo.png)'><span class='d'>更多</span></li>\");\r\n");
      out.write("\t\t\tobj.click(function(){\r\n");
      out.write("\t\t\t\t$(\".a\").css(\"overflow-y\",\"scroll\");\r\n");
      out.write("\t\t\t\tclickMore = true;\r\n");
      out.write("\t\t\t\t$(this).remove();\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\t$(\".a li\").eq(heights2 - ccc).before(obj);\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t$(window).resize(function(){\r\n");
      out.write("\t\tif(!clickMore){\r\n");
      out.write("\t\t\tresizeFunc();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t});\r\n");
      out.write("\tresizeFunc();\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
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
