package org.apache.jsp.system.core.menu;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeModelIdConst;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.servlet.TeeResPrivServlet;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import org.springframework.core.io.ClassPathResource;
import com.tianee.oa.core.org.service.TeePersonService;

public final class childMenu_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/header/header.jsp");
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

	String pMenuId = request.getParameter("pMenuId") == null ? "" : request.getParameter("pMenuId");
	String Puuid = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");
	String menuName = request.getParameter("menuName") == null ? "" : request.getParameter("menuName");
	menuName = menuName.replace("\'", "\\\'");

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\"/>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
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




      out.write("\r\n");
      out.write("<!-- jQuery库 -->\r\n");
      out.write("<script src=\"");
      out.print(contextPath );
      out.write("/common/zt_webframe/js/jquery-1.7.1.min.js\"></script>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(cssPath);
      out.write("/style.css\"/>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" >\r\n");
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
      out.write("<!-- Bootstrap通用UI组件 -->\r\n");
      out.write("<script src=\"");
      out.print(contextPath );
      out.write("/common/bootstrap/js/bootstrap.min.js\"></script>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(contextPath );
      out.write("/common/bootstrap/css/bootstrap.css\"/>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- JBOX通用UI组件 -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/common/jbox-v2.3/jBox/jquery.jBox-2.3.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/common/jbox-v2.3/jBox/i18n/jquery.jBox-zh-CN.js\"></script>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(contextPath);
      out.write("/common/jbox-v2.3/jBox/Skins/Blue/jbox.css\" />\r\n");
      out.write("<script>\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("<!-- 其他工具库类 -->\r\n");
      out.write("<script src=\"");
      out.print(contextPath );
      out.write("/common/js/tools.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(contextPath );
      out.write("/common/js/sys.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(contextPath );
      out.write("/common/js/sysUtil.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(contextPath );
      out.write("/common/js/src/orgselect.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/common/js/easyuiTools.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- jQuery Tooltip -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/common/tooltip/jquery.tooltip.min.js\"></script>\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(contextPath );
      out.write("/common/tooltip/jquery.tooltip.css\" type=\"text/css\"/>\r\n");
      out.write("\r\n");
      out.write("<!-- 图片预览器 -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath() );
      out.write("/common/js/picexplore/jquery.mousewheel.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath() );
      out.write("/common/js/picexplore/picexplore.js\"></script>\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(contextPath );
      out.write("/common/js/picexplore/picexplore.css\" type=\"text/css\"/>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/common/js/TeeMenu.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("/** 常量定义 **/\r\n");
      out.write("var TDJSCONST = {\r\n");
      out.write("  YES: 1,\r\n");
      out.write("  NO: 0\r\n");
      out.write("};\r\n");
      out.write("/** 变量定义 **/\r\n");
      out.write("var contextPath = \"");
      out.print(contextPath );
      out.write("\";\r\n");
      out.write("var imgPath = \"");
      out.print(imgPath );
      out.write("\";\r\n");
      out.write("var cssPath = \"");
      out.print(cssPath);
      out.write("\";\r\n");
      out.write("var stylePath = \"");
      out.print(stylePath);
      out.write("\";\r\n");
      out.write("\r\n");
      out.write("var cssPathSecond = \"");
      out.print(cssPathSecond);
      out.write("\";\r\n");
      out.write("var imgPathSecond = \"");
      out.print(imgPathSecond);
      out.write("\";\r\n");
      out.write("var loginOutText = \"");
      out.print(loginOutText);
      out.write("\";\r\n");
      out.write("var uploadFlashUrl = \"");
      out.print(contextPath );
      out.write("/common/swfupload/swfupload.swf\";\r\n");
      out.write("var commonUploadUrl = \"");
      out.print(contextPath );
      out.write("/attachmentController/commonUpload.action;jsessionid=");
      out.print(session.getId());
      out.write("\";\r\n");
      out.write("var systemImagePath = \"");
      out.print(systemImagePath);
      out.write("\";\r\n");
      out.write("var gezAddr =  \"");
      out.print(TeeSysProps.getString("GENZ_ADDR"));
      out.write("\";\r\n");
      out.write("var xparent;\r\n");
      out.write("if(window.dialogArguments){\r\n");
      out.write("\txparent = window.dialogArguments;\r\n");
      out.write("}else if(window.opener){\r\n");
      out.write("\txparent = opener;\r\n");
      out.write("}else{\r\n");
      out.write("\txparent = window;\r\n");
      out.write("}\r\n");
      out.write("function parseNumber(value, defValue) {\r\n");
      out.write("  if (isNaN(value)) {\r\n");
      out.write("    return defValue;\r\n");
      out.write("  }\r\n");
      out.write("  return value * 1;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("<style>\r\n");
      out.write("body {\r\n");
      out.write("scrollbar-arrow-color: #a3a3a3;  /*图6,三角箭头的颜色*/\r\n");
      out.write("scrollbar-face-color: #bcbcbc;  /*图5,立体滚动条的颜色*/\r\n");
      out.write("scrollbar-3dlight-color: #b2b2b2;  /*图1,立体滚动条亮边的颜色*/\r\n");
      out.write("scrollbar-highlight-color: #e9e9e9;  /*图2,滚动条空白部分的颜色*/\r\n");
      out.write("scrollbar-shadow-color: #b2b2b2;  /*图3,立体滚动条阴影的颜色*/\r\n");
      out.write("scrollbar-darkshadow-color: #666;  /*图4,立体滚动条强阴影的颜色*/\r\n");
      out.write("scrollbar-track-color: #f1f1f1;  /*图7,立体滚动条背景颜色*/\r\n");
      out.write("scrollbar-base-color:#bcbcbc;  /*滚动条的基本颜色*/\r\n");
      out.write("}\r\n");
      out.write("</style>");
      out.write("\r\n");
      out.write("<title>子菜单设置</title>\r\n");
      out.write("<link href=\"");
      out.print(cssPath );
      out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("<link  rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(cssPath );
      out.write("/stylebootstrap.css\" />\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath );
      out.write("/common/jquery/jquery-min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath );
      out.write("/common/js/tools.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("var menuId = '");
      out.print(pMenuId);
      out.write("';\r\n");
      out.write("var uuid = \"");
      out.print(Puuid);
      out.write("\";\r\n");
      out.write("var pMenuName = \"");
      out.print(menuName);
      out.write("\";\r\n");
      out.write("function doInit(){\r\n");
      out.write("\t//添加例子一\r\n");
      out.write("\tvar url = \"");
      out.print(contextPath );
      out.write("/sysMenu/getSysMenuByParent.action?menuId=\" + menuId;\r\n");
      out.write("\tvar jsonObj = tools.requestJsonRs(url);\r\n");
      out.write("\tif(jsonObj.rtState){\r\n");
      out.write("\t\tvar json = jsonObj.rtData;\r\n");
      out.write("\t\t//for(var i =0;i<json.length;i++){\r\n");
      out.write("\t\tjQuery.each(json, function(i, sysMenu) {\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tvar menuId = sysMenu.menuId;\r\n");
      out.write("\t\t\tvar icon = sysMenu.icon;\r\n");
      out.write("\t\t\tvar ICON =  \"menu/default.gif\";\r\n");
      out.write("\t\t\tif(icon &&  icon != \"\" ){\r\n");
      out.write("\t\t\t\tICON = \"menu/\" + icon;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tif(menuId){\r\n");
      out.write("\t\t\t\tvar level = (menuId.length-3)/3;\r\n");
      out.write("\t\t\t\tvar paddingLeft  = 0;\r\n");
      out.write("\t\t\t\tif(level>1){\r\n");
      out.write("\t\t\t\t\tpaddingLeft = (level-1) * 25;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tmenuId =  \"<span style='padding-left:\" +paddingLeft+ \"px;margin-left:5px;'></span>\"\r\n");
      out.write("\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t\t+ menuId.substring(menuId.length - 3,menuId.length);\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t//var sysMenu = json[i];\r\n");
      out.write("\t\t \t$(\"#tbody\").append(\"<tr class='TableData'>\"\r\n");
      out.write("\t\t\t\t\t//+\"<td nowrap align='center'>\" + sysMenu.uuid + \"</td>\"\r\n");
      out.write("\t\t\t\t\t+\"<td nowrap align=''>\" \r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\t\t+ menuId+ \"</td>\"\r\n");
      out.write("\t\t\t\t\t+\"<td nowrap align=''>\" + sysMenu.menuName + \"</td>\"\r\n");
      out.write("\t\t\t\t\t//+\"<td nowrap align='center'>\" + sysMenu.menuCode + \"</td>\"\r\n");
      out.write("\t\t\t\t\t//+\"<td nowrap align='center'>\" + sysMenu.openFlag + \"</td>\"\r\n");
      out.write("\t\t\t\t\t+\"<td nowrap align=''>\"\r\n");
      out.write("\t\t\t\t\t +\"<a href='javascript:void(0);' id='menu-child-a-\" + sysMenu.uuid + \"'>修改</a>\"\r\n");
      out.write("\t\t\t\t\t +\"&nbsp;&nbsp;<a href='javascript:deleteSysMenu(\\\"\" + sysMenu.uuid+ \"\\\",\\\"\" + sysMenu.menuId +  \"\\\")'>删除</a>\"\r\n");
      out.write("\t\t\t\t\t +\"&nbsp;&nbsp;<a href='javascript:addMenuButton(\\\"\" + sysMenu.uuid+ \"\\\",\\\"\" + sysMenu.menuId +  \"\\\")'>按钮编辑</a>\"\r\n");
      out.write("\t\t\t\t\t +\"</td>\"\r\n");
      out.write("\t\t  \t+ \"</tr>\"); \r\n");
      out.write("\t\t \t$(\"#menu-child-a-\" + sysMenu.uuid).bind(\"click\",function(){\r\n");
      out.write("\t\t \t\ttoMenu(sysMenu.uuid,sysMenu.menuId);\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t});\r\n");
      out.write("\r\n");
      out.write("\t//}\r\n");
      out.write("\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\talert(jsonObj.rtMsg);\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function deleteSysMenu(uuid,menuId){\r\n");
      out.write("\tif(confirm(\"确认要删除此主菜单！删除后将删除所有下级菜单\")){\r\n");
      out.write("\t\tvar url = \"");
      out.print(contextPath );
      out.write("/sysMenu/delMenu.action\";\r\n");
      out.write("\t\tvar jsonRs = tools.requestJsonRs(url,{uuid:uuid,menuId:menuId});\r\n");
      out.write("\t\tif(jsonRs.rtState){\r\n");
      out.write("\t\t\talert(\"删除成功！\");\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t//var parent = window.parent;\r\n");
      out.write("\t\t\twindow.location.reload();\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\talert(jsonRs.rtMsg);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function toMenu(uuid,menuId){\r\n");
      out.write("\tvar url = \"");
      out.print(contextPath);
      out.write("/system/core/menu/addupdatechild.jsp?uuid=\" + uuid + \"&menuId=\" + menuId;\r\n");
      out.write("\twindow.parent.$(\"#frame0\").attr(\"src\",url);\t \r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function toAddMenu(){\r\n");
      out.write("\tvar url = \"");
      out.print(contextPath);
      out.write("/system/core/menu/addupdatechild1.jsp?pMenuId=\" + menuId + \"&Puuid=\" + uuid + \"&menuName=\" + encodeURIComponent(pMenuName);;\r\n");
      out.write("\twindow.parent.$(\"#frame0\").attr(\"src\",url);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function addMenuButton(uuid,menuId){\r\n");
      out.write("\tvar url = \"");
      out.print(contextPath);
      out.write("/system/core/menu/menuButton.jsp?uuid=\" + uuid + \"&menuId=\" + menuId;\r\n");
      out.write("\twindow.parent.$(\"#frame0\").attr(\"src\",url);\t \r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"doInit()\">\r\n");
      out.write("   <table border=\"0\" width=\"95%\" cellspacing=\"0\" cellpadding=\"3\" class=\"small\" align=\"center\">\r\n");
      out.write("    <tr>\r\n");
      out.write("      <td class=\"Big\"><span class=\"Big3\">子菜单设置</span></td>\r\n");
      out.write("    </tr>\r\n");
      out.write("  </table><br>\r\n");
      out.write("\r\n");
      out.write(" \r\n");
      out.write("   <table class=\"TableList\" width=\"60%\" align=\"center\">\r\n");
      out.write("      <tbody id=\"tbody\">\r\n");
      out.write("        <tr>\r\n");
      out.write("         <td colspan=\"3\" class=\"TableHeader\" id=\"mainMenuName\">\r\n");
      out.write("         ");
      out.print(menuName );
      out.write("\r\n");
      out.write("        </td>\r\n");
      out.write("        </tr>\r\n");
      out.write("        <tr>\r\n");
      out.write("        <td colspan=\"3\" class=\"\" align=\"center\">\r\n");
      out.write("          <img src=\"");
      out.print(imgPath);
      out.write("/notify_new.gif\" align=\"absmiddle\"><span class=\"big3\">\r\n");
      out.write("         <a href=\"javascript:toAddMenu();\">新增菜单</a></td>\r\n");
      out.write("        </tr>\r\n");
      out.write("      \r\n");
      out.write("        <tr class=\"TableHeader\">\r\n");
      out.write("     <!--  \t\t<td width=\"40\" align=\"center\">UUId</td> -->\r\n");
      out.write("      \t\t<td nowrap align=\"center\">菜单编号</td>\r\n");
      out.write("     \t    <td nowrap align=\"center\">菜单名称</td>\r\n");
      out.write("      \t\t<td nowrap align=\"center\">操作</td>\r\n");
      out.write("       </tr>\r\n");
      out.write("      </tbody>\r\n");
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
