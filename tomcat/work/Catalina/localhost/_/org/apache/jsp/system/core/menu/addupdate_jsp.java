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

public final class addupdate_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(2);
    _jspx_dependants.add("/header/header.jsp");
    _jspx_dependants.add("/header/easyui.jsp");
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
      out.write("\r\n");
      out.write("<title>菜单功能</title>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("var uuid = '");
      out.print(uuid);
      out.write("';\r\n");
      out.write("\r\n");
      out.write("function doInit(){\r\n");
      out.write("\trenderSysList();\r\n");
      out.write("\t//添加例子一\r\n");
      out.write("\tif(uuid != \"\"){\r\n");
      out.write("\t\tvar url = \"");
      out.print(contextPath );
      out.write("/sysMenu/getSysMenuById.action\";\r\n");
      out.write("\t\tvar para = {uuid:uuid};\r\n");
      out.write("\t\tvar jsonObj = tools.requestJsonRs(url,para);\r\n");
      out.write("\t\tif(jsonObj){\r\n");
      out.write("\t\t\tvar json = jsonObj.rtData;\r\n");
      out.write("\t\t\tif(json.uuid){\r\n");
      out.write("\t\t\t\t//alert(json.uuid +\":\"+ json.menuId + \":\" + json.menuName);\r\n");
      out.write("\t\t\t\t/* $(\"#menuId\").val(json.menuId);\r\n");
      out.write("\t\t\t\t$(\"#menuName\").val(json.menuName);\r\n");
      out.write("\t\t\t\t$(\"#menuCode\").val(json.menuCode);\r\n");
      out.write("\t\t\t\t$(\"#icon\").val(json.icon);\r\n");
      out.write("\t\t\t\t$(\"#uuid\").val(json.uuid); */\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tbindJsonObj2Cntrl(json);\r\n");
      out.write("\t\t\t\t$(\"#oldMenuId\").val(json.menuId);\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\talert(jsonObj.rtMsg);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//渲染应用系统列表\r\n");
      out.write("function renderSysList(){\r\n");
      out.write("\tvar html=[];\r\n");
      out.write("\tvar url=contextPath+\"/ApplicationSystemMaintainController/getAll.action\";\r\n");
      out.write("\tvar json=tools.requestJsonRs(url);\r\n");
      out.write("\tif(json.rtState){\r\n");
      out.write("\t\tvar data=json.rtData;\r\n");
      out.write("\t\tif(data!=null&&data.length>0){\r\n");
      out.write("\t\t\tfor(var i=0;i<data.length;i++){\r\n");
      out.write("\t\t\t\thtml.push(\"<option value=\"+data[i].sid+\">\"+data[i].systemName+\"</option>\");\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t$(\"#sysId\").append(html.join(\"\"));\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * 保存\r\n");
      out.write(" */\r\n");
      out.write("function doSave(){\r\n");
      out.write("\tif (check()){\r\n");
      out.write("\t\tvar url = \"");
      out.print(contextPath );
      out.write("/sysMenu/addOrUpdateMenu.action\";\r\n");
      out.write("\t\tvar para =  tools.formToJson($(\"#form1\")) ;\r\n");
      out.write("\t\tvar jsonRs = tools.requestJsonRs(url,para);\r\n");
      out.write("\t\tif(jsonRs.rtState){\r\n");
      out.write("\t\t\tvar data = jsonRs.rtData;\r\n");
      out.write("\t\t\tif(data.isExistCode == \"0\"){\r\n");
      out.write("\t\t\t\ttop.$.jBox.tip(\"保存成功！\",\"info\",{timeout:1500});\r\n");
      out.write("\t\t\t\tvar parent = window.parent;\r\n");
      out.write("\t\t\t\tparent.location.reload();\r\n");
      out.write("\t\t        window.location.reload();\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\talert(\"菜单编号已存在！\");\r\n");
      out.write("\t\t\t\tvar menuId = document.getElementById(\"menuId\");\r\n");
      out.write("\t\t\t  \tmenuId.focus();\r\n");
      out.write("\t\t\t    menuId.select();\r\n");
      out.write("\t\t\t\treturn;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\talert(jsonRs.rtMsg);\r\n");
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
      out.write("\t var status =  $(\"#form1\").form('validate'); \r\n");
      out.write("\t if(status == false){\r\n");
      out.write("\t\t return false;\r\n");
      out.write("\t }\r\n");
      out.write("  \tvar cntrl = document.getElementById(\"menuName\"); \r\n");
      out.write("    if(checkStr(cntrl.value)){\r\n");
      out.write("      alert(\"您输入的菜单名称含有'双引号'、'单引号 '或者 '\\\\' 请从新填写\");\r\n");
      out.write("      $('#menuName').focus();\r\n");
      out.write("      $('#menuName').select();\r\n");
      out.write("      return false;\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    return true;\r\n");
      out.write("  }\r\n");
      out.write("  \r\n");
      out.write("  /**选择菜单样式**/\r\n");
      out.write("  function selectIcon(){\r\n");
      out.write("\t\t$.jBox('iframe:' + contextPath+ '/system/core/menu/bsicon.jsp', \r\n");
      out.write("\t\t\t\t { bottomText: '',\r\n");
      out.write("\t\t\t \ttitle:'选择菜单样式',\r\n");
      out.write("\t\t\t\twidth: 540,\r\n");
      out.write("\t\t\t    height: 450\r\n");
      out.write("\t\t});\r\n");
      out.write("\r\n");
      out.write("\t// top.$.jBox.info(info);\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"doInit()\" style=\"padding-top:10px;\">\r\n");
      out.write("   <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"3\" class=\"small\" align=\"center\">\r\n");
      out.write("    <tr>\r\n");
      out.write("      <td class=\"Big\">\r\n");
      out.write("      \r\n");
      out.write("        ");

         if(TeeUtility.isNullorEmpty(uuid)){
        	
        	  
      out.write("\r\n");
      out.write("        \t<span class=\"big3\">\r\n");
      out.write("        \t  主菜单新增\r\n");
      out.write("        \t  </span>\r\n");
      out.write("        \t  ");

         }else{
        	 
      out.write("\t \r\n");
      out.write("        <span class=\"big3\">\r\n");
      out.write("        \t 主菜单编辑\r\n");
      out.write("        \t  </span>\r\n");
      out.write("        \t \r\n");
      out.write("         ");
}
        
      out.write("</td>\r\n");
      out.write("    </tr>\r\n");
      out.write("  </table><br>\r\n");
      out.write("  <form   method=\"post\" name=\"form1\" id=\"form1\">\r\n");
      out.write("<table class=\"TableBlock\" width=\"80%\" align=\"center\">\r\n");
      out.write("           <tr class=\"TableLine1\">\r\n");
      out.write("\t\t\t\t<td nowrap>所属应用系统：</td>\r\n");
      out.write("\t\t\t\t<td nowrap>\r\n");
      out.write("\t\t\t\t     <select class=\"BigSelect\" id=\"sysId\" name=\"sysId\">\r\n");
      out.write("\t\t\t\t         <option value=\"0\">公用</option>\r\n");
      out.write("\t\t\t\t     </select>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr class=\"TableLine1\">\r\n");
      out.write("\t\t\t\t<td nowrap>菜单编号：</td>\r\n");
      out.write("\t\t\t\t<td nowrap><INPUT type=text name=\"menuId\" id=\"menuId\" size=\"10\" maxlength=\"3\" class=\"easyui-validatebox BigInput\" required=\"true\" validType =\"numberstrLength[3]\"> &nbsp;3位数字</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr class=\"TableLine2\">\r\n");
      out.write("\t\t\t\t<td nowrap>菜单名称：：</td>\r\n");
      out.write("\t\t\t\t<td nowrap>\r\n");
      out.write("\t\t\t\t<input type=\"text\" name=\"menuName\" id=\"menuName\" class=\"easyui-validatebox BigInput\" style=\"width:200px;\" required=\"true\">&nbsp;</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr class=\"TableLine2\">\r\n");
      out.write("\t\t\t\t<td nowrap>图片：</td>\r\n");
      out.write("\t\t\t\t<td nowrap><input type=\"text\" id=\"icon\" name=\"icon\"  class=\"easyui-validatebox BigInput\" style=\"width:200px;\"  id=\"icon\">\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t      \r\n");
      out.write("<!-- \t\t\t\t<input type=\"button\" value=\"选择图片\" class=\"btn\" onclick=\"selectIcon();\">&nbsp;&nbsp; -->\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr class=\"TableLine1\">\r\n");
      out.write("\t\t\t\t<td nowrap>路径:</td>\r\n");
      out.write("\t\t\t\t<td nowrap><input type=\"text\" name=\"menuCode\" id=\"menuCode\" size=\"60\" maxlength=\"100\" class=\"BigInput\" value=\"\">\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr class=\"\">\r\n");
      out.write("\t\t\t\t<td colspan=\"2\" align=\"center\"><input type=\"button\" value=\"保存\"\r\n");
      out.write("\t\t\t\t\tclass=\"btn btn-primary\" onclick=\"doSave();\">&nbsp;&nbsp;\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" id=\"uuid\" name=\"uuid\" style=\"display:none;\" value='0'/>\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" id=\"oldMenuId\" name=\"oldMenuId\" style=\"display:none;\"/>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("   </form>\r\n");
      out.write("   \r\n");
      out.write("   \r\n");
      out.write("   \r\n");
      out.write("   \r\n");
      out.write("\r\n");
      out.write("<!-- Modal -->\r\n");
      out.write("<div class=\"modal fade\" id=\"myModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">\r\n");
      out.write("  <div class=\"modal-dialog\">\r\n");
      out.write("    <div class=\"modal-content\">\r\n");
      out.write("      <div class=\"modal-header\">\r\n");
      out.write("        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>\r\n");
      out.write("        <h4 class=\"modal-title\" id=\"myModalLabel\">Modal title</h4>\r\n");
      out.write("      </div>\r\n");
      out.write("      <div class=\"modal-body\">\r\n");
      out.write("        ...\r\n");
      out.write("      </div>\r\n");
      out.write("      <div class=\"modal-footer\">\r\n");
      out.write("        <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Close</button>\r\n");
      out.write("        <button type=\"button\" class=\"btn btn-primary\">Save changes</button>\r\n");
      out.write("      </div>\r\n");
      out.write("    </div><!-- /.modal-content -->\r\n");
      out.write("  </div><!-- /.modal-dialog -->\r\n");
      out.write("</div><!-- /.modal -->\r\n");
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
