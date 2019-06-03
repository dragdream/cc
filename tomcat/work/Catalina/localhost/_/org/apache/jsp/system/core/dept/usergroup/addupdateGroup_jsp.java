package org.apache.jsp.system.core.dept.usergroup;

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

public final class addupdateGroup_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

  	String uuid = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
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
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\"/>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n");
      out.write("<title>自定义分组</title>\r\n");
      out.write("<link href=\"");
      out.print(cssPath );
      out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("<link  rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(cssPath );
      out.write("/stylebootstrap.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(contextPath);
      out.write("/common/easyui/themes/gray/easyui.css\"/>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath );
      out.write("/common/jquery/jquery-min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath );
      out.write("/common/js/tools.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/common/easyui/jquery.easyui.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/common/easyui/locale/easyui-lang-zh_CN.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/common/js/src/teeValidagteBox.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath );
      out.write("/common/js/src/orgselect.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/common/js/sys2.0.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("var uuid = '");
      out.print(uuid);
      out.write("';\r\n");
      out.write("\r\n");
      out.write("function doInit(){\r\n");
      out.write("\t//添加例子一\r\n");
      out.write("\tif(uuid != \"\"){\r\n");
      out.write("\t\tvar url = \"");
      out.print(contextPath );
      out.write("/userGroup/getUserGroupById.action\";\r\n");
      out.write("\t\tvar para = {uuid:uuid};\r\n");
      out.write("\t\t//alert(uuid);\r\n");
      out.write("\t\tvar jsonObj = tools.requestJsonRs(url,para);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(jsonObj){\r\n");
      out.write("\t\t\tvar json = jsonObj.rtData;\r\n");
      out.write("\t\t\t//alert(json.uuid);\r\n");
      out.write("\t\t\tif(json.uuid){\r\n");
      out.write("\t\t\t\tbindJsonObj2Cntrl(json);\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$.MsgBox.Alert_auto(jsonObj.rtMsg);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * 保存\r\n");
      out.write(" */\r\n");
      out.write("function doSave(){\r\n");
      out.write("\tif(check()){\r\n");
      out.write("\t\tvar url = \"");
      out.print(contextPath );
      out.write("/userGroup/addUpdateUserGroup.action\";\r\n");
      out.write("\t\tvar para =  tools.formToJson($(\"#form1\"));\r\n");
      out.write("\t\tvar jsonRs = tools.requestJsonRs(url,para);\r\n");
      out.write("\t\t//alert(jsonRs);\r\n");
      out.write("\t\tif(jsonRs.rtState){\r\n");
      out.write("\t\t\tparent.$.MsgBox.Alert_auto(jsonRs.rtMsg);\r\n");
      out.write("\t\t\twindow.location = \"");
      out.print(contextPath);
      out.write("/system/core/dept/usergroup/personGroup.jsp\";\r\n");
      out.write("\t\t    //window.location.reload();\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tparent.$.MsgBox.Alert_auto(jsonRs.rtMsg);;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function checkStr(str){ \r\n");
      out.write("    var re=/[\\\\\"']/; \r\n");
      out.write("    return str.match(re); \r\n");
      out.write("  }\r\n");
      out.write(" \r\n");
      out.write("function check() {\r\n");
      out.write("\treturn $(\"#form1\").form('validate'); \r\n");
      out.write("\t\r\n");
      out.write("  }\r\n");
      out.write("  \r\n");
      out.write("function doReturn() {\r\n");
      out.write("\twindow.location.href = \"");
      out.print(contextPath);
      out.write("/system/core/dept/usergroup/personGroup.jsp\";\r\n");
      out.write("\t\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"doInit()\">\r\n");
      out.write(" <div style=\"margin-top: 5px;margin-bottom: 5px;margin-left: 5px;\">\r\n");
      out.write("   <img src=\"");
      out.print(contextPath );
      out.write("/common/zt_webframe/imgs/common_img/icon_yhcx.png\" align=\"absMiddle\">&nbsp;&nbsp; \r\n");
      out.write("   <span>\r\n");
      out.write("      \r\n");
      out.write("        ");

         if(TeeUtility.isNullorEmpty(uuid)){
        	
        	  
      out.write("\r\n");
      out.write("        \t  新建分组\r\n");
      out.write("        \t  \r\n");
      out.write("        \t  ");

         }else{
        	 
      out.write("\t \r\n");
      out.write("        \t 编辑分组\r\n");
      out.write("        \t \r\n");
      out.write("         ");
}
        
      out.write("</span>\r\n");
      out.write("    </div>\r\n");
      out.write("  <form   method=\"post\" name=\"form1\" id=\"form1\">\r\n");
      out.write("<table class=\"TableBlock_page\" width=\"80%\" align=\"center\">\r\n");
      out.write("\t\t\t<tr class=\"TableLine2\">\r\n");
      out.write("\t\t\t\t<td nowrap style=\"text-indent:15px;\">分组名称：</td>\r\n");
      out.write("\t\t\t\t<td nowrap><input type=\"text\" name=\"groupName\" id=\"groupName\" class=\"easyui-validatebox BigInput\" required=\"true\" >&nbsp;</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t<tr class=\"TableLine1\">\r\n");
      out.write("\t\t\t\t<td nowrap style=\"text-indent:15px;\">排序号：</td>\r\n");
      out.write("\t\t\t\t<td nowrap><INPUT type=text name=\"orderNo\" id=\"orderNo\" class=\"easyui-validatebox BigInput\" required=\"true\" size=\"10\" validType ='intege[]'></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td nowrap class=\"TableData\" style=\"text-indent:15px;\">分配用户：</td>\r\n");
      out.write("\t\t\t\t<td nowrap class=\"TableData\"><input type=\"hidden\"\r\n");
      out.write("\t\t\t\t\tname=\"userListIds\" id=\"userListIds\" value=\"\"> <textarea cols=\"60\"\r\n");
      out.write("\t\t\t\t\t\tname=\"userListNames\" id=\"userListNames\" rows=\"6\"\r\n");
      out.write("\t\t\t\t\t\tstyle=\"overflow-y: auto;\" class=\"BigTextarea\" wrap=\"yes\" readonly></textarea>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<span class='addSpan'>\r\n");
      out.write("\t\t\t\t\t   <img src=\"");
      out.print(contextPath );
      out.write("/common/zt_webframe/imgs/grbg/gryj/icon_select.png\" onClick=\"selectUser(['userListIds', 'userListNames'])\" value=\"选择\"/>\r\n");
      out.write("\t\t\t\t\t   &nbsp;&nbsp;\r\n");
      out.write("\t\t\t\t\t   <img src=\"");
      out.print(contextPath );
      out.write("/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png\" onClick=\"clearData('userListIds', 'userListNames')\" value=\"清空\"/>\r\n");
      out.write("\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t<tr class=\"TableControl\"> \r\n");
      out.write("\t\t\t\t<td colspan=\"2\" align=\"center\">\r\n");
      out.write("\t\t\t\t   <div align=\"center\">\r\n");
      out.write("\t\t\t\t    <input type=\"button\" value=\"保存\"\r\n");
      out.write("\t\t\t\t\tclass=\"btn-win-white\" onclick=\"doSave();\">&nbsp;&nbsp;\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<input type=\"button\" value=\"返回\"\r\n");
      out.write("\t\t\t\t\tclass=\"btn-win-white\" onclick=\"doReturn();\">&nbsp;&nbsp;\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" id=\"uuid\" name=\"uuid\" style=\"display:none;\"/>\t\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" id=\"userId\" name=\"userId\" value=\"0\"/>\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" id=\"userUuid\" name=\"userUuid\" style=\"display:none;\"/>\t\t\t\t\r\n");
      out.write("\t\t\t\t  </div>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("   </form>\r\n");
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
