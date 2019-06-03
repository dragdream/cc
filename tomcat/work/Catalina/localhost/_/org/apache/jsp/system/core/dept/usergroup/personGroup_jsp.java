package org.apache.jsp.system.core.dept.usergroup;

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

public final class personGroup_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n");

	String paraValue = request.getParameter("paraValue") == null ? "" : request.getParameter("paraValue");
	String Puuid = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");
	String paraName = request.getParameter("paraName") == null ? "" : request.getParameter("paraName");
	paraName = paraName.replace("\'", "\\\'");

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\"/>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n");
      out.write("<title>自定义分组</title>\r\n");
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
      out.write("\r\n");
      out.write("<link href=\"");
      out.print(cssPath );
      out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<style>\r\n");
      out.write("\r\n");
      out.write("table{\r\n");
      out.write("\tborder-collapse: collapse;\r\n");
      out.write("    border: 1px solid #f2f2f2;\r\n");
      out.write("    width:100%;\r\n");
      out.write("}\r\n");
      out.write("table tr{\r\n");
      out.write("\tline-height:35px;\r\n");
      out.write("\tborder-bottom:1px solid #f2f2f2;\r\n");
      out.write("}\r\n");
      out.write("table tr td:first-child{\r\n");
      out.write("\ttext-indent:10px;\r\n");
      out.write("}\r\n");
      out.write("table tr:first-child td{\r\n");
      out.write("\tfont-weight:bold;\r\n");
      out.write("}\r\n");
      out.write("table tr:first-child{\r\n");
      out.write("\tborder-bottom:2px solid #b0deff!important;\r\n");
      out.write("\tbackground-color: #f8f8f8; \r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("var paraValue = '");
      out.print(paraValue);
      out.write("';\r\n");
      out.write("var puuid = \"");
      out.print(Puuid);
      out.write("\";\r\n");
      out.write("var paraName = \"");
      out.print(paraName);
      out.write("\";\r\n");
      out.write("function doInit(){\r\n");
      out.write("\t//alert(paraValue);\r\n");
      out.write("\tvar url = \"");
      out.print(contextPath );
      out.write("/userGroup/getUserGroupList.action\";\r\n");
      out.write("\tvar jsonObj = tools.requestJsonRs(url);\r\n");
      out.write("\t//alert(jsonObj.rtState);\r\n");
      out.write("\tif(jsonObj.rtState){\r\n");
      out.write("\t\tvar json = jsonObj.rtData;\r\n");
      out.write("\t\tjQuery.each(json, function(i, sysPara) {\r\n");
      out.write("\t\t\t//alert(sysPara.paraName);\r\n");
      out.write("\t\t\t//var codeValue = sysPara.codeValue;\r\n");
      out.write("\t\t \t$(\"#tbody\").append(\"<tr>\"\r\n");
      out.write("\t\t\t\t\t+\"<td nowrap align='center' style='width:10%'>\" + sysPara.orderNo+ \"</td>\"\r\n");
      out.write("\t\t\t\t\t+\"<td nowrap align='center' style='width:40%'>\" + sysPara.groupName + \"</td>\"\r\n");
      out.write("\t\t\t\t\t+\"<td nowrap align='center' style='width:30%'>\"\r\n");
      out.write("\t\t\t\t\t +\"<a href='#' id='sysPara-child-a-\" + sysPara.uuid + \"'>修改</a>\"\r\n");
      out.write("\t\t\t\t\t +\"&nbsp;&nbsp;<a href='javascript:deleteUserGroup(\\\"\" + sysPara.uuid+ \"\\\")'>删除</a>\"\r\n");
      out.write("\t\t\t\t\t +\"</td>\"\r\n");
      out.write("\t\t  \t+ \"</tr>\"); \r\n");
      out.write("\t\t \t//alert(123);\r\n");
      out.write("\t\t \t$(\"#sysPara-child-a-\" + sysPara.uuid).bind(\"click\",function(){\r\n");
      out.write("\t\t \t\ttoMenu(sysPara.uuid);\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t});\r\n");
      out.write("\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\t$.MsgBox.Alert_auto(jsonObj.rtMsg);\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function deleteUserGroup(uuid){\r\n");
      out.write("\t $.MsgBox.Confirm (\"提示\", \"确认要删除此编码？\", function(){\r\n");
      out.write("\t\t var url = \"");
      out.print(contextPath );
      out.write("/userGroup/delUserGroup.action\";\r\n");
      out.write("\t\t\tvar jsonRs = tools.requestJsonRs(url,{uuid:uuid});\r\n");
      out.write("\t\t\tif(jsonRs.rtState){\r\n");
      out.write("\t\t\t\t$.MsgBox.Alert_auto(\"删除成功！\");\r\n");
      out.write("\t\t\t\twindow.location.reload();\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\t$.MsgBox.Alert_auto(jsonRs.rtMsg);\r\n");
      out.write("\t\t\t} \r\n");
      out.write("\t  });\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function toMenu(uuid){\r\n");
      out.write("\t//alert(uuid);\r\n");
      out.write("\twindow.location = \"");
      out.print(contextPath);
      out.write("/system/core/dept/usergroup/addupdateGroup.jsp?uuid=\"+uuid;\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function toAddGroup(){\r\n");
      out.write("\t\r\n");
      out.write("\t//alert(123);\r\n");
      out.write("\t//var parent = window.parent.contentFrame;\r\n");
      out.write("\t\r\n");
      out.write("\twindow.location.href = \"");
      out.print(contextPath);
      out.write("/system/core/dept/usergroup/addupdateGroup.jsp\";\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"doInit()\">\r\n");
      out.write("   <div style=\"margin-left:3px;margin-right: 10px;margin-bottom: 5px;margin-top: 5px\">\r\n");
      out.write("      <img src=\"");
      out.print(contextPath );
      out.write("/common/zt_webframe/imgs/common_img/icon_yhcx.png\" align=\"absMiddle\">&nbsp;&nbsp; <span>分组管理</span>\r\n");
      out.write("      &nbsp;&nbsp;\r\n");
      out.write("      <input onclick=\"javascript:toAddGroup();\"  class=\"btn-win-white\"  type = \"button\" value = \"新增分组\"/>\r\n");
      out.write("   </div>\r\n");
      out.write("\r\n");
      out.write(" \r\n");
      out.write("   <table width=\"80%\" id=\"tbody\" >\r\n");
      out.write("        <tr class=\"TableHeader\">\r\n");
      out.write("      \t\t<td nowrap align=\"center\" style='width:10%'>排序号</td>\r\n");
      out.write("     \t    <td nowrap align=\"center\" style='width:40%'>用户组名称</td>\r\n");
      out.write("      \t\t<td nowrap align=\"center\" style='width:30%'>操作</td>\r\n");
      out.write("       </tr>\r\n");
      out.write("   </table>\r\n");
      out.write("\r\n");
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
