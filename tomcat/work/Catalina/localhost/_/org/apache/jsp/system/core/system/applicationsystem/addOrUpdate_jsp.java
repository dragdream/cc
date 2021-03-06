package org.apache.jsp.system.core.system.applicationsystem;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.tianee.webframe.util.str.TeeStringUtil;
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

public final class addOrUpdate_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n");

    int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<title>新建/编辑业务系统信息</title>\r\n");
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
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("var sid=");
      out.print(sid );
      out.write(";\r\n");
      out.write("//初始化\r\n");
      out.write("function doInit(){\r\n");
      out.write("\tif(sid>0){\r\n");
      out.write("\t\tgetInfoBySid();\r\n");
      out.write("\t}\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//根据主键获取详情\r\n");
      out.write("function getInfoBySid(){\r\n");
      out.write("\tvar url=contextPath+\"/ApplicationSystemMaintainController/getInfoBySid.action\";\r\n");
      out.write("\tvar json=tools.requestJsonRs(url,{sid:sid});\r\n");
      out.write("\tif(json.rtState){\r\n");
      out.write("\t\tvar data=json.rtData;\r\n");
      out.write("\t\tbindJsonObj2Cntrl(data);\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//保存操作\r\n");
      out.write("function doSave(){\r\n");
      out.write("\tif(check()){\r\n");
      out.write("\t\tvar url=contextPath+\"/ApplicationSystemMaintainController/addOrUpdate.action\";\r\n");
      out.write("\t\tvar param=tools.formToJson($(\"#form1\"));\r\n");
      out.write("\t\tvar json=tools.requestJsonRs(url,param);\r\n");
      out.write("\t\treturn json;\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("//验证   \r\n");
      out.write("function check(){\r\n");
      out.write("\tvar systemName=$(\"#systemName\").val();\r\n");
      out.write("\tif(systemName==null||systemName==\"\"){\r\n");
      out.write("\t\t$.MsgBox.Alert_auto(\"请填写系统名称！\");\r\n");
      out.write("\t\treturn false;\r\n");
      out.write("\t}\r\n");
      out.write("\treturn true;\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"doInit()\" style=\"background-color: #f2f2f2\">\r\n");
      out.write(" <form id=\"form1\">\r\n");
      out.write("   <input type=\"hidden\" name=\"sid\" id=\"sid\" value=\"");
      out.print(sid );
      out.write("\" />\r\n");
      out.write("   <table  class=\"TableBlock\" width=\"100%\" align=\"center\" >\r\n");
      out.write("        <tr>\r\n");
      out.write("\t\t\t<td nowrap class=\"TableData\" width=\"100\" style=\"text-indent: 10px\">系统名称：</td>\r\n");
      out.write("\t\t\t<td class=\"TableData\">\r\n");
      out.write("\t\t\t\t<input type=\"text\" class=\"BigInput\" name=\"systemName\" id=\"systemName\" style=\"height: 23px;width: 400px\"/>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td nowrap class=\"TableData\" width=\"100\" style=\"text-indent: 10px\">访问地址：</td>\r\n");
      out.write("\t\t\t<td class=\"TableData\">\r\n");
      out.write("\t\t\t\t<input type=\"text\" class=\"BigInput\" name=\"url\" id=\"url\" style=\"height: 23px;width: 400px\"/>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td nowrap class=\"TableData\" width=\"100\" style=\"text-indent: 10px\">人员权限：</td>\r\n");
      out.write("\t\t\t<td class=\"TableData\">\r\n");
      out.write("\t\t\t    <input type=\"hidden\" name=\"userIds\" id=\"userIds\"/>\r\n");
      out.write("\t\t\t\t<textarea rows=\"5\" cols=\"40\" name=\"userNames\" id=\"userNames\"></textarea>\r\n");
      out.write("\t\t\t    <span class='addSpan'>\r\n");
      out.write("\t\t\t\t\t   <img src=\"");
      out.print(contextPath );
      out.write("/common/zt_webframe/imgs/grbg/gryj/icon_select.png\" onclick=\"selectUser(['userIds','userNames'],'14')\" value=\"选择\"/>\r\n");
      out.write("\t\t\t\t\t   &nbsp;&nbsp;\r\n");
      out.write("\t\t\t\t\t   <img src=\"");
      out.print(contextPath );
      out.write("/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png\" onclick=\"clearData('userIds','userNames')\" value=\"清空\"/>\r\n");
      out.write("\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td nowrap class=\"TableData\" width=\"100\" style=\"text-indent: 10px\">部门权限：</td>\r\n");
      out.write("\t\t\t<td class=\"TableData\">\r\n");
      out.write("\t\t\t    <input type=\"hidden\" name=\"deptIds\" id=\"deptIds\"/>\r\n");
      out.write("\t\t\t\t<textarea rows=\"5\" cols=\"40\" name=\"deptNames\" id=\"deptNames\"></textarea>\r\n");
      out.write("\t\t\t    <span class='addSpan'>\r\n");
      out.write("\t\t\t\t\t   <img src=\"");
      out.print(contextPath );
      out.write("/common/zt_webframe/imgs/grbg/gryj/icon_select.png\" onclick=\"selectDept(['deptIds','deptNames'],'14')\" value=\"选择\"/>\r\n");
      out.write("\t\t\t\t\t   &nbsp;&nbsp;\r\n");
      out.write("\t\t\t\t\t   <img src=\"");
      out.print(contextPath );
      out.write("/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png\" onclick=\"clearData('deptIds','deptNames')\" value=\"清空\"/>\r\n");
      out.write("\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td nowrap class=\"TableData\" width=\"100\" style=\"text-indent: 10px\">角色权限：</td>\r\n");
      out.write("\t\t\t<td class=\"TableData\">\r\n");
      out.write("\t\t\t    <input type=\"hidden\" name=\"roleIds\" id=\"roleIds\"/>\r\n");
      out.write("\t\t\t\t<textarea rows=\"5\" cols=\"40\" name=\"roleNames\" id=\"roleNames\"></textarea>\r\n");
      out.write("\t\t\t     <span class='addSpan'>\r\n");
      out.write("\t\t\t\t\t   <img src=\"");
      out.print(contextPath );
      out.write("/common/zt_webframe/imgs/grbg/gryj/icon_select.png\" onclick=\"selectRole(['roleIds','roleNames'],'14')\" value=\"选择\"/>\r\n");
      out.write("\t\t\t\t\t   &nbsp;&nbsp;\r\n");
      out.write("\t\t\t\t\t   <img src=\"");
      out.print(contextPath );
      out.write("/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png\" onclick=\"clearData('roleIds','roleNames')\" value=\"清空\"/>\r\n");
      out.write("\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t\r\n");
      out.write("   \r\n");
      out.write("   </table>\r\n");
      out.write(" </form>\r\n");
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
