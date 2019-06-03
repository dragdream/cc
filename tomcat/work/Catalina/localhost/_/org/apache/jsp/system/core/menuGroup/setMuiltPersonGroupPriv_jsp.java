package org.apache.jsp.system.core.menuGroup;

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

public final class setMuiltPersonGroupPriv_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(3);
    _jspx_dependants.add("/header/header.jsp");
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

	String uuid = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");

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
      out.write("<title>批量设置权限</title>\r\n");
      out.write("\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write("div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}\r\n");
      out.write("div#rMenu ul li{\r\n");
      out.write("\tmargin: 1px 0;\r\n");
      out.write("\tpadding: 0 5px;\r\n");
      out.write("\tcursor: pointer;\r\n");
      out.write("\tlist-style: none outside none;\r\n");
      out.write("\t/*background-color: #DFDFDF;*/\r\n");
      out.write("}\r\n");
      out.write("\t</style>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("var zTreeObj ;\r\n");
      out.write("var uuid = \"");
      out.print(uuid);
      out.write("\";\r\n");
      out.write("function doInit(){\r\n");
      out.write("\tgetMenuGroupAll();\r\n");
      out.write("\t\r\n");
      out.write("\tmessageMsg(\"为所选人员添加/删除权限组\", \"menugroupPrivHelp\" ,'help' ,380);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * 保存\r\n");
      out.write(" */\r\n");
      out.write("function doSubmit(){\r\n");
      out.write("    var menuGroupOptType = \"0\";//0-添加  ； 1-删除\r\n");
      out.write("    if(document.getElementById(\"menuGroupOptType1\").checked == true){\r\n");
      out.write("    \tmenuGroupOptType = \"1\";\r\n");
      out.write("    }\r\n");
      out.write("\tvar menuGroupStr = getMenuGroupA();\r\n");
      out.write("\tif(menuGroupStr == ''){\r\n");
      out.write("\t\talert(\"请至少选择一个权限组！\");\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tif($(\"#personIds\").val() == ''){\r\n");
      out.write("\t\talert(\"您未选择人员！\");\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("\tvar url = \"");
      out.print(contextPath );
      out.write("/teeMenuGroup/setMuiltPersonMenuGroupPriv.action\";\r\n");
      out.write("\tvar jsonObj = tools.requestJsonRs(url,{uuids:menuGroupStr,personIds:$(\"#personIds\").val(),opt:menuGroupOptType});\r\n");
      out.write("\tif(jsonObj.rtState){\r\n");
      out.write("\t\t/* $.messager.show({\r\n");
      out.write("\t\t\tmsg : '保存成功！！',\r\n");
      out.write("\t\t\ttitle : '提示'\r\n");
      out.write("\t\t}); */\r\n");
      out.write("\t\t$.jBox.tip(\"保存成功！\",'info',{timeout:1500});\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\talert(jsonObj.rtMsg);\r\n");
      out.write("\t} \r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * 获取菜单组\r\n");
      out.write(" */\r\n");
      out.write("function getMenuGroupAll(){\r\n");
      out.write("\tvar url = \"");
      out.print(contextPath );
      out.write("/teeMenuGroup/getMenuGroupAll.action\";\r\n");
      out.write("\tvar jsonObj = tools.requestJsonRs(url);\r\n");
      out.write("\tif(jsonObj.rtState){\r\n");
      out.write("\t\tvar data = jsonObj.rtData;\r\n");
      out.write("\t\tvar dataJson = eval('(' + data + ')');\r\n");
      out.write("\t\tvar menuGroups = document.getElementById(\"menuGroups\");\r\n");
      out.write("\t\r\n");
      out.write("\t\tfor(var i = 0;i<dataJson.length ;i++){\r\n");
      out.write("\t\t\tvar menuGroup = dataJson[i];\r\n");
      out.write("\t\t\tvar input = document.createElement(\"input\");\r\n");
      out.write("\t\t\tinput.setAttribute(\"type\", \"checkbox\");\r\n");
      out.write("\t\t\tinput.setAttribute(\"id\", \"menuGroup\" + menuGroup.uuid);\r\n");
      out.write("\t\t\tinput.setAttribute(\"name\", \"menuGroup\");\r\n");
      out.write("\t\t    input.setAttribute(\"value\", menuGroup.uuid);\r\n");
      out.write("\t\t\t  \r\n");
      out.write("\t\t\tvar label = document.createElement(\"label\");\r\n");
      out.write("\t\t\tlabel.setAttribute(\"for\", \"menuGroup\" + menuGroup.uuid);\r\n");
      out.write("\t\t\tlabel.innerHTML = menuGroup.menuGroupName + \"&nbsp;&nbsp;\";\r\n");
      out.write("\t\t    //input.appendChild(label); \r\n");
      out.write("\t\t\tmenuGroups.appendChild(input);\r\n");
      out.write("\t\t\tmenuGroups.appendChild(label );\r\n");
      out.write("\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" *获取所有选中菜单权组\r\n");
      out.write(" */\r\n");
      out.write("function getMenuGroupA(){\r\n");
      out.write("\tvar menuGroups = $(\"#menuGroups\").children(\":input\");\r\n");
      out.write("\tvar menuGroupStr = \"\";\r\n");
      out.write("    for(var i = 0;i<menuGroups.length;i++){\r\n");
      out.write("    \tif(menuGroups[i].checked){\r\n");
      out.write("    \t\tmenuGroupStr = menuGroupStr + menuGroups[i].value   + \",\" ;\r\n");
      out.write("    \t}\r\n");
      out.write("    }\r\n");
      out.write("\tif(menuGroupStr != \"\"){\r\n");
      out.write("\t\tmenuGroupStr = menuGroupStr.substring(0,menuGroupStr.length-1);\r\n");
      out.write("\t}\r\n");
      out.write("\treturn menuGroupStr;\r\n");
      out.write("}\r\n");
      out.write("/**\r\n");
      out.write(" * 全部选中或者取消\r\n");
      out.write(" */\r\n");
      out.write("var check_all_var = true;\r\n");
      out.write("function select_all(){\r\n");
      out.write("\t var cb  = $(\"#menuGroups\").children(\":input\");\r\n");
      out.write("\t  if(!cb || cb.length == 0){\r\n");
      out.write("\t    return;\r\n");
      out.write("\t  }\r\n");
      out.write("\t  for(var i = 0;i < cb.length; i++){\r\n");
      out.write("\t    if(check_all_var){\r\n");
      out.write("\t      cb[i].checked = true;\r\n");
      out.write("\t      \r\n");
      out.write("\t    }else{\r\n");
      out.write("\t      cb[i].checked = false;\r\n");
      out.write("\t\r\n");
      out.write("\t    }\r\n");
      out.write("\t  }\r\n");
      out.write("\t  check_all_var = !check_all_var;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</SCRIPT>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</HEAD>\r\n");
      out.write("\r\n");
      out.write("<BODY onload=\"doInit()\">\r\n");
      out.write("\r\n");
      out.write("<div  style=\"padding-top:10px; padding-left:10px;\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<table >\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td>\r\n");
      out.write("  <span class=\"Big3\"> 添加/删除权限组</span>&nbsp;&nbsp;\r\n");
      out.write("    </td>\r\n");
      out.write("  \r\n");
      out.write("  </tr>\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<table class=\"TableBlock\" width=\"60%\" align=\"center\">\r\n");
      out.write("  <tr class=\"TableData\">\r\n");
      out.write("    <td width=\"100px;\">\r\n");
      out.write("     <b>操作：</b>\r\n");
      out.write("    </td>\r\n");
      out.write("    <td >\r\n");
      out.write("\t\t<input type=\"radio\" name=\"menuGroupOptType\" id=\"menuGroupOptType0\" value=\"0\" checked/> 添加权限组\r\n");
      out.write("    \t &nbsp;&nbsp;\r\n");
      out.write("     \t<input type=\"radio\" name=\"menuGroupOptType\" id=\"menuGroupOptType1\" value=\"1\"/> 删除权限组\r\n");
      out.write("    \r\n");
      out.write("    </td>\r\n");
      out.write("    </tr>\r\n");
      out.write("    <tr class=\"TableData\">\r\n");
      out.write("    <td>\r\n");
      out.write("\t\t  <b>  人员</b>\r\n");
      out.write("    </td>\r\n");
      out.write("      <td>\r\n");
      out.write(" \r\n");
      out.write("   \t   <input  type=\"hidden\" id=\"personIds\" name=\"personIds\" style=\"width:100%\"/>\r\n");
      out.write("\t    <textarea cols=\"45\" name=\"personNames\" id=\"personNames\" rows=\"4\"\r\n");
      out.write("\t \t\tstyle=\"overflow-y: auto;\" class=\"SmallStatic BigTextarea\" wrap=\"yes\" readonly></textarea>\r\n");
      out.write("\t\t\t<a href=\"javascript:void(0);\" class=\"orgAdd\" onClick=\"selectUser(['personIds', 'personNames'])\">添加</a>\r\n");
      out.write("\t\t\t <a href=\"javascript:void(0);\" class=\"orgClear\" onClick=\"clearData('personIds', 'personNames')\">清空</a>\r\n");
      out.write("     </td>\r\n");
      out.write("  </tr>\r\n");
      out.write("    <tr class=\"TableData\">\r\n");
      out.write("    <td>\r\n");
      out.write("   \t <b> 权限</b>&nbsp;&nbsp;<a href=\"javascript:select_all();\"><u>全选</u></a>: \r\n");
      out.write("    </td>\r\n");
      out.write("    <td id=\"menuGroups\">\r\n");
      out.write("\r\n");
      out.write("    \r\n");
      out.write("    </td>\r\n");
      out.write("  </tr>\r\n");
      out.write("  \r\n");
      out.write("  <tr align=\"center\">\r\n");
      out.write("  \t<td colspan=\"2\">\r\n");
      out.write("\t\t<input type=\"button\" value=\"保存\" class=\"btn btn-primary\" title=\"保存\" name=\"button\" onclick=\"doSubmit();\">&nbsp;&nbsp;\r\n");
      out.write("  \t</td>\r\n");
      out.write("  </tr>\r\n");
      out.write("\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
      out.write("<div id=\"menugroupPrivHelp\">\r\n");
      out.write(" \r\n");
      out.write("</div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</BODY>\r\n");
      out.write("</HTML>");
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
