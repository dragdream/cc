package org.apache.jsp.xzfy.jsp.organ;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.tianee.webframe.util.str.TeeUtility;
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

public final class addupdate_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n");
      out.write("\r\n");

	String puuid = request.getParameter("puuid") == null ? "" : request.getParameter("puuid");
	String uuid = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");
	String type = request.getParameter("type") == null ? "" : request.getParameter("type");
	String deptParent = request.getParameter("deptParent") == null? "" : request.getParameter("deptParent");

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
      out.write("\t<meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\"/>\r\n");
      out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n");
      out.write("\t<title>部门管理</title>\r\n");
      out.write("\t<link href=\"");
      out.print(cssPath);
      out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("\t<link href=\"");
      out.print(contextPath);
      out.write("/common/jquery/ztree/css/demo.css\" type=\"text/css\" rel=\"stylesheet\"/>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/xzfy/js/common/common.js\"></script>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body onload=\"doInit()\" style=\"font-family: MicroSoft YaHei;font-size: 12px;\">\r\n");
      out.write("\t<form method=\"post\" name=\"form1\" id=\"form1\" class=\"tableForm\" style=\"padding-bottom:5px;\">\r\n");
      out.write("\t\t<table class=\"TableBlock_page\" width=\"100%\" align=\"center\">\r\n");
      out.write("            <tr>\r\n");
      out.write("               <td colspan=\"2\" style='vertical-align: middle;font-family: MicroSoft YaHei;font-size: 14px;line-height: 30px;'><img src=\"");
      out.print(contextPath );
      out.write("/common/zt_webframe/imgs/common_img/icon_yhcx.png\" align=\"absMiddle\">&nbsp;&nbsp; \r\n");
      out.write("\t\t\t\t   <span id=\"title\"></span> \r\n");
      out.write("               </td>\r\n");
      out.write("            </tr>\r\n");
      out.write("           \r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t    <td nowrap class=\"TableData\" style=\"text-indent: 10px\">机关名称：</td>\r\n");
      out.write("\t\t\t\t<td nowrap class=\"TableData\">\r\n");
      out.write("\t\t\t\t\t<input id=\"deptId\" name=\"deptId\"  type=\"hidden\"/>\r\n");
      out.write("\t\t\t\t\t<input id=\"orgId\" name=\"orgId\"  type=\"hidden\"/>\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" id=\"orgName\" name=\"orgName\" style=\"font-family:MicroSoft YaHei;\" \r\n");
      out.write("\t\t\t\t\t    class=\"easyui-validatebox BigInput\" \r\n");
      out.write("\t\t\t\t\t    required=\"true\" readonly=\"readonly\"/>\r\n");
      out.write("\t\t\t \t\t\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td nowrap class=\"TableData\" style=\"text-indent: 10px\">机关层级：</td>\r\n");
      out.write("\t\t\t\t<td nowrap class=\"TableData\">\r\n");
      out.write("\t\t\t\t\t<select class=\"easyui-validatebox BigSelect\" name=\"orgLevelCode\" id=\"orgLevelCode\" \r\n");
      out.write("\t\t\t\t\t    required=\"true\" >\r\n");
      out.write("\t\t\t\t\t\t<option value=\"\">--请选择--</option>\r\n");
      out.write("\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("                <td nowrap class=\"TableData\" style=\"text-indent: 10px\">机关编码：</td>\r\n");
      out.write("                <td nowrap class=\"TableData\">\r\n");
      out.write("                \t<input type=\"text\" name=\"orgCode\" id=\"orgCode\" \r\n");
      out.write("                \t    class=\"easyui-validatebox BigInput\" \r\n");
      out.write("\t\t\t\t\t    required=\"true\" validType=\"orgCodeCheck\" />\r\n");
      out.write("                </td>\r\n");
      out.write("            </tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td nowrap class=\"TableData\" style=\"text-indent: 10px\">法人：</td>\r\n");
      out.write("\t\t\t\t<td nowrap class=\"TableData\">\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" name=\"legalRepresentative\" id=\"legalRepresentative\" \r\n");
      out.write("\t\t\t\t\t    class=\"easyui-validatebox BigInput\" size=\"25\" maxlength=\"25\" \r\n");
      out.write("\t\t\t\t\t\trequired=\"true\" validType=\"legalRepresentativeCheck\"/>&nbsp;\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td nowrap class=\"TableData\" style=\"text-indent: 10px\">编制人数：</td>\r\n");
      out.write("\t\t\t\t<td nowrap class=\"TableData\">\r\n");
      out.write("\t\t\t\t    <input type=\"text\" name=\"compilersNum\" id=\"compilersNum\" \r\n");
      out.write("\t\t\t\t    class=\"easyui-validatebox BigInput\" size=\"25\" maxlength=\"25\" \r\n");
      out.write("\t\t\t\t\trequired=\"true\" validType=\"compilersNumCheck\" />&nbsp;\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td nowrap class=\"TableData\" style=\"text-indent: 10px\">联系人：</td>\r\n");
      out.write("\t\t\t\t<td nowrap class=\"TableData\">\r\n");
      out.write("\t\t\t\t    <input type=\"text\" id=\"contacts\" name=\"contacts\" \r\n");
      out.write("\t\t\t\t    class=\"easyui-validatebox BigInput\" size=\"40\" maxlength=\"40\" \r\n");
      out.write("\t\t\t\t\trequired=\"true\" validType=\"contactsCheck\" />&nbsp;\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td nowrap class=\"TableData\" style=\"text-indent: 10px\">联系人电话：</td>\r\n");
      out.write("\t\t\t\t<td class=\"TableData\">\r\n");
      out.write("\t\t\t\t<input type=\"text\" name=\"contactsPhone\" id=\"contactsPhone\" \r\n");
      out.write("\t\t\t\t    class=\"easyui-validatebox BigInput\" maxlength=\"11\"\r\n");
      out.write("\t\t\t\t    required=\"true\" validType=\"contactsPhoneCheck\" />&nbsp;\r\n");
      out.write("               </td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("\t\t\t\t<td nowrap class=\"TableData\" style=\"text-indent: 10px\">传真：</td>\r\n");
      out.write("\t\t\t\t<td class=\"TableData\">\r\n");
      out.write("\t\t\t\t<input type=\"text\" name=\"fax\" id=\"fax\" \r\n");
      out.write("\t\t\t\t    class=\"easyui-validatebox BigInput\" />&nbsp;\r\n");
      out.write("               </td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td nowrap class=\"TableData\" style=\"text-indent: 10px\">邮政编码：</td>\r\n");
      out.write("\t\t\t\t<td class=\"TableData\">\r\n");
      out.write("\t\t\t\t<input type=\"text\" name=\"areaCode\" id=\"areaCode\" \r\n");
      out.write("\t\t\t\t    class=\"easyui-validatebox BigInput\" \r\n");
      out.write("\t\t\t\t    />&nbsp;\r\n");
      out.write("               </td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td nowrap class=\"TableData\" style=\"text-indent: 10px\">备注：</td>\r\n");
      out.write("\t\t\t\t<td class=\"TableData\">\r\n");
      out.write("\t\t\t\t<textarea cols=\"45\" name=\"remark\" id=\"remark\" rows=\"4\" style=\"overflow-y: auto;\" \r\n");
      out.write("\t\t\t\t\tclass=\"easyui-validatebox SmallStatic BigTextarea\" wrap=\"yes\" \r\n");
      out.write("\t\t\t\t\t>\r\n");
      out.write("\t\t\t\t</textarea>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("               </td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td colspan=\"2\">\r\n");
      out.write("\t\t\t\t <div align=\"center\">\r\n");
      out.write("\t\t\t\t\t<input id=\"saveBtn\"  type=\"button\" value=\"保存\" \r\n");
      out.write("\t\t\t\t\t    class=\"btn-win-white\" onclick=\"doSave();\" />&nbsp;&nbsp;  \r\n");
      out.write("\t\t\t\t\t<input type=\"text\" id=\"uuid\" name=\"uuid\" value=\"0\" style=\"display: none;\" />\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t  </div>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t</form>\r\n");
      out.write("\t\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/xzfy/js/organ/addupdate.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"/xzfy/js/organ/check.js\"></script>\r\n");
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
