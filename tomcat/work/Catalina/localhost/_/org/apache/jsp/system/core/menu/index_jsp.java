package org.apache.jsp.system.core.menu;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.tianee.oa.core.partthree.util.TeePartThreeUtil;
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

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
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

   TeePerson  loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
   boolean hasPriv=TeePartThreeUtil.checkHasPriv(loginUser, "SYS_MENU");
   if(!hasPriv){//没有权限
	   response.sendRedirect("/system/core/system/partthree/error.jsp");
   }
   

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\">\r\n");
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
      out.write("\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write("html{\r\n");
      out.write("\tpadding:5px 0px 0px 5px;\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("<!-- jQuery 布局器 -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath() );
      out.write("/common/jqueryui/jquery.layout-latest.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/common/js/layout/layout.js\"></script>\r\n");
      out.write("<script>\r\n");
      out.write("\r\n");
      out.write("function doInit(){\r\n");
      out.write("\t$(\"#layout\").layout({auto:true});\r\n");
      out.write("\t$(\"#group\").group();\r\n");
      out.write("\tchangePage(1);\r\n");
      out.write("\tgetMenuList();\r\n");
      out.write("}\r\n");
      out.write("function changePage(sel , sid){\r\n");
      out.write("\tif(sel==1){\r\n");
      out.write("\t\t$(\"#frame0\").attr(\"src\",contextPath+\"/system/core/menu/blank.jsp\");//外出\r\n");
      out.write("\t}else if(sel==2){\r\n");
      out.write("\t\t$(\"#frame0\").attr(\"src\",contextPath +\"/system/core/menu/addupdate.jsp?sid=\" + sid);//请假\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("function getMenuList(){\r\n");
      out.write("\t\r\n");
      out.write("\t//添加例子一\r\n");
      out.write("\tvar url = \"");
      out.print(contextPath );
      out.write("/sysMenu/getSysMenu.action\";\r\n");
      out.write("\tvar jsonObj = tools.requestJsonRs(url);\r\n");
      out.write("\tif(jsonObj.rtState){\r\n");
      out.write("\t\tvar json = jsonObj.rtData;\r\n");
      out.write("\t\t//for(var i =0;i<json.length;i++){\r\n");
      out.write("\t\tjQuery.each(json, function(i, sysMenu) {\r\n");
      out.write("\t\t\t//var sysMenu = json[i];\r\n");
      out.write("\t\t \t$(\"#menuHeader\").before(\"<tr class='TableData'>\"\r\n");
      out.write("\t\t\t\t\t//+\"<td nowrap align='center'>\" + sysMenu.uuid + \"</td>\"\r\n");
      out.write("\t\t\t\t\t+\"<td nowrap align='left'><b>\" + sysMenu.menuId + \"&nbsp;&nbsp;\" + sysMenu.menuName  + \"</b></td>\"\r\n");
      out.write("\t\t\t\t\t+\"<td nowrap align='center'>\"\r\n");
      out.write("\t\t\t\t\t +\"<a href='javascript:toMainMenu(\\\"\" + sysMenu.uuid+ \"\\\")'>修改</a>\"\r\n");
      out.write("\t\t\t\t\t +\"&nbsp;&nbsp;<a id='menu-a-\" + sysMenu.uuid+ \"' href='javascript:void(0);'>下一级</a>\"\r\n");
      out.write("\t\t\t\t\t +\"&nbsp;&nbsp;<a href='javascript:deleteSysMenu(\\\"\" + sysMenu.uuid + \"\\\",\\\"\" + sysMenu.menuId+ \"\\\")'>删除</a>\"\r\n");
      out.write("\t\t\t\t\t +\"</td>\"\r\n");
      out.write("\t\t  \t+ \"</tr>\");\r\n");
      out.write("\t\t \t$(\"#menu-a-\" + sysMenu.uuid).bind(\"click\",function(){\r\n");
      out.write("\t\t \t\ttoChild(sysMenu.uuid,sysMenu.menuId,sysMenu.menuName);\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t});\r\n");
      out.write("\r\n");
      out.write("\t//}\r\n");
      out.write("\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\talert(jsonObj.rtMsg);\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("/**\r\n");
      out.write(" * 删除\r\n");
      out.write(" */\r\n");
      out.write("function deleteSysMenu(uuid,menuId){\r\n");
      out.write("\t\r\n");
      out.write("\tif(confirm(\"确认要删除此主菜单！删除后将删除所有下级菜单\")){\r\n");
      out.write("\t\tvar url = \"");
      out.print(contextPath );
      out.write("/sysMenu/delMenu.action\";\r\n");
      out.write("\t\tvar jsonRs = tools.requestJsonRs(url,{uuid:uuid,menuId:menuId});\r\n");
      out.write("\t\tif(jsonRs.rtState){\r\n");
      out.write("\t\t\ttop.$.jBox.tip(\"删除成功！\",'info',{timeout:1000});\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\twindow.location.reload();\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\talert(jsonRs.rtMsg);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("/**\r\n");
      out.write(" * 新增或删除路径\r\n");
      out.write(" */\r\n");
      out.write("function toMainMenu(uuid){\r\n");
      out.write("\tvar url = \"");
      out.print(contextPath);
      out.write("/system/core/menu/addupdate.jsp?uuid=\" + uuid;\r\n");
      out.write("\t$(\"#frame0\").attr(\"src\",url);\t \r\n");
      out.write("}\r\n");
      out.write("/**\r\n");
      out.write(" * 去下级菜单管理\r\n");
      out.write(" */\r\n");
      out.write("function toChild(uuid,menuId,menuName){\r\n");
      out.write("\tvar url = \"");
      out.print(contextPath);
      out.write("/system/core/menu/childMenu.jsp?pMenuId=\" + menuId + \"&uuid=\" + uuid + \"&menuName=\" + encodeURIComponent(menuName);\r\n");
      out.write("\t$(\"#frame0\").attr(\"src\",url);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * 跳转设置菜单页面\r\n");
      out.write(" */\r\n");
      out.write("function toSetMenu(type){\r\n");
      out.write("\tvar url = \"\";\r\n");
      out.write("\tif(type == '1'){\r\n");
      out.write("\t\turl = \"");
      out.print(contextPath);
      out.write("/system/core/menu/menuModulePriv.jsp\";\r\n");
      out.write("\t}else if(type == '2'){\r\n");
      out.write("\t\turl = \"");
      out.print(contextPath);
      out.write("/system/core/menuPriv/index.jsp\";\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\turl = \"");
      out.print(contextPath);
      out.write("/system/core/menu/menupara.jsp\";\r\n");
      out.write("\t}\r\n");
      out.write("\t$(\"#frame0\").attr(\"src\",url);\r\n");
      out.write("\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"doInit()\" style=\"overflow:hidden;font-size:12px;\">\r\n");
      out.write("<div id=\"layout\" >\r\n");
      out.write("\t<div layout=\"west\" width=\"340\" style=\"overflow-y:auto;overflow-x:hidden;\">\r\n");
      out.write("\t\t<br>\r\n");
      out.write("\t\t<div id=\"group\" class=\"list-group\">\r\n");
      out.write("\t\t     <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"3\" class=\"small\" align=\"center\" style=\"margin:10px 0px 0px 10px ;\">\r\n");
      out.write("\t\t\t    <tr>\r\n");
      out.write("\t\t\t      <td class=\"Big\"><span class=\"Big3\">主菜单</span></td>\r\n");
      out.write("\t\t\t    </tr>\r\n");
      out.write("\t\t\t  </table><br>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t \r\n");
      out.write("\t\t\t   <table class=\"TableBlock\" width=\"80%\" align=\"center\" >\r\n");
      out.write("\t\t\t      <tbody id=\"tbody\">\r\n");
      out.write("\t\t\t        <tr  class=\"TableHeader\">\r\n");
      out.write("\t\t\t         <td colspan=\"2\" onclick=\"toMainMenu('')\" style=\"cursor: pointer;\"> 新增主菜单</td>\r\n");
      out.write("\t\t\t        </tr>\r\n");
      out.write("\t\t\t       <!--  <tr style=\"background: none repeat scroll 0 0 #f7f8bd\" >\r\n");
      out.write("\t\t\t      \t\t<td width=\"40\" align=\"center\">UUId</td>\r\n");
      out.write("\t\t\t      \t\t<td nowrap align=\"center\" >菜单编号</td>\r\n");
      out.write("\t\t\t     \t    <td nowrap align=\"center\" >菜单名称</td>\r\n");
      out.write("\t\t\t      \t\t<td nowrap align=\"center\">操作</td>\r\n");
      out.write("\t\t\t       </tr>\r\n");
      out.write("\t\t\t        -->\r\n");
      out.write("\t\t\t       <tr id='menuHeader'  class=\"TableHeader\">\r\n");
      out.write("\t\t\t         <td colspan=\"2\" onclick=\"toSetMenu('')\" style=\"cursor:pointer ;\">菜单设置</td>\r\n");
      out.write("\t\t\t        </tr>\r\n");
      out.write("\t\t\t        <!--  <tr id='menuHeader'  class=\"TableHeader\">\r\n");
      out.write("\t\t\t         <td colspan=\"2\" onclick=\"toSetMenu('1')\" style=\"cursor: pointer;\">菜单模块权限编码设置</td>\r\n");
      out.write("\t\t\t        </tr> -->\r\n");
      out.write("\t\t\t         <tr id='menuHeader'  class=\"TableHeader\">\r\n");
      out.write("\t\t\t         <td colspan=\"2\" onclick=\"toSetMenu('2')\" style=\"cursor: pointer;\">菜单模块权限设置</td>\r\n");
      out.write("\t\t\t        </tr>\r\n");
      out.write("\t\t\t      </tbody>\r\n");
      out.write("\t\t\t   </table>\r\n");
      out.write("\t\t\t<br>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div layout=\"center\" style=\"padding-left:10px\">\r\n");
      out.write("\t\t<iframe id=\"frame0\" frameborder=0 style=\"width:100%;height:100%\"></iframe>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
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
