package org.apache.jsp.system.core.menu;

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

public final class addupdatechild_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(2);
    _jspx_dependants.add("/header/header2.0.jsp");
    _jspx_dependants.add("/header/validator2.0.jsp");
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
	String uuid = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");
	String Puuid = request.getParameter("Puuid") == null ? "" : request.getParameter("Puuid");
	String menuName = request.getParameter("menuName") == null ? "" : request.getParameter("menuName");
	
	String menuId = request.getParameter("menuId") == null ? "" : request.getParameter("menuId");
	
	String childMenuName = request.getParameter("childMenuName") == null ? "" : request.getParameter("childMenuName");
	String menuURL = request.getParameter("menuURL") == null ? "" : request.getParameter("menuURL");//
	
	menuName = menuName.replace("\'", "\\\'");

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\" style=\"background-color:#f2f2f2;\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\"/>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n");
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
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- zt_webframe框架引入 核心库 -->\r\n");
      out.write("<script type=\"text/javascript\" src = \"");
      out.print(request.getContextPath());
      out.write("/common/zt_webframe/js/jquery.validate.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src = \"");
      out.print(request.getContextPath());
      out.write("/common/zt_webframe/js/additional-methods.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src = \"");
      out.print(request.getContextPath());
      out.write("/common/zt_webframe/js/messages_zh.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src = \"");
      out.print(request.getContextPath());
      out.write("/common/zt_webframe/js/jquery.validate.extend.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<title>菜单功能</title>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/common/easyui/locale/easyui-lang-zh_CN.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath );
      out.write("/common/jquery/ztree/js/jquery.ztree.core-3.5.js\"></script>\r\n");
      out.write("<style>\r\n");
      out.write(".BigInput{\r\n");
      out.write("  height:20px;\r\n");
      out.write("  width: 200px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("</style>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("var uuid = '");
      out.print(uuid);
      out.write("';\r\n");
      out.write("var pMenuId = '");
      out.print(pMenuId);
      out.write("';//父级菜单编号\r\n");
      out.write("var Puuid = \"");
      out.print(Puuid);
      out.write("\";//父级UUID\r\n");
      out.write("\r\n");
      out.write("var menuId = \"");
      out.print(menuId);
      out.write("\";//菜单编号\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("var childMenuName = \"");
      out.print(childMenuName);
      out.write("\";//公共文件柜过来创建的菜单\r\n");
      out.write("var menuURL = \"");
      out.print(menuURL);
      out.write("\";\r\n");
      out.write("function doInit(){\r\n");
      out.write("\trenderSysList();\r\n");
      out.write("\t//添加例子一\r\n");
      out.write("\tif(uuid != \"\"){\r\n");
      out.write("\t\tvar url = \"");
      out.print(contextPath );
      out.write("/sysMenu/getSysMenuById.action\";\r\n");
      out.write("\t\tvar para = {uuid:uuid,menuId:menuId};\r\n");
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
      out.write("\t\t\t\tvar childMenuId = json.menuId.substring(json.menuId.length-3, json.menuId.length); \r\n");
      out.write("\t\t\t\t$(\"#menuId\").val(childMenuId);\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$.MsgBox.Alert_auto(jsonObj.rtMsg);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
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
      out.write("\t\t///$(\"#menuId\").val($(\"#pMenuId\").val() + $(\"#menuId\").val());\r\n");
      out.write("\t\tvar para =  tools.formToJson($(\"#form1\")) ;\r\n");
      out.write("\t\tvar jsonRs = tools.requestJsonRs(url,para);\r\n");
      out.write("\t\tif(jsonRs.rtState){\r\n");
      out.write("\t\t\tvar data = jsonRs.rtData;\r\n");
      out.write("\t\t\tif(data.isExistCode == \"0\"){\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t//alert(\"保存成功！\");\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$.MsgBox.Alert_auto('保存成功');\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\tif(childMenuName != \"\" && menuURL != \"\"){\r\n");
      out.write("\t\t\t\t\t\t//关闭bsWindow\r\n");
      out.write("\t\t\t\t\t\tparent.$(\"#win_ico\").click();\r\n");
      out.write("\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\t \r\n");
      out.write("\t\t\t\t\t\t history.go(-1);\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t//top.$.jBox.tip('保存成功','info',{timeout:1500});\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t//var parent = window.parent.navigateFrame\r\n");
      out.write("\t\t\t\t//parent.location.reload();\r\n");
      out.write("\t\t        //window.location.reload();\r\n");
      out.write("\t\t       \r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\t$.MsgBox.Alert_auto(\"菜单编号已存在！\");\r\n");
      out.write("\t\t\t\tvar menuId = document.getElementById(\"menuId\");\r\n");
      out.write("\t\t\t  \tmenuId.focus();\r\n");
      out.write("\t\t\t    menuId.select();\r\n");
      out.write("\t\t\t\treturn;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$.MsgBox.Alert_auto(jsonRs.rtMsg);\r\n");
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
      out.write("/****\r\n");
      out.write(" * 检查\r\n");
      out.write(" */\r\n");
      out.write("function check() {\r\n");
      out.write("\t var status =  $(\"#form1\").valid(); \r\n");
      out.write("\t if(status == false){\r\n");
      out.write("\t\t return false;\r\n");
      out.write("\t }\r\n");
      out.write("  \tvar cntrl = document.getElementById(\"menuName\"); \r\n");
      out.write("    if(checkStr(cntrl.value)){\r\n");
      out.write("      $.MsgBox.Alert_auto(\"您输入的菜单名称含有'双引号'、'单引号 '或者 '\\\\' 请从新填写\");\r\n");
      out.write("      $('#menuName').focus();\r\n");
      out.write("      $('#menuName').select();\r\n");
      out.write("      return false;\r\n");
      out.write("    }\r\n");
      out.write("    return true;\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * 选择菜单\r\n");
      out.write(" * @return\r\n");
      out.write(" */\r\n");
      out.write("var menuArray = null ;\r\n");
      out.write("function toSelectTreeMenu(retArray) {\r\n");
      out.write("  menuArray = retArray;\r\n");
      out.write("  var url =  \"");
      out.print(contextPath);
      out.write("/system/core/menu/menuTree.jsp?sid=\" + uuid;\r\n");
      out.write("  dialogChangesize(url , 300, 450);\r\n");
      out.write("  \r\n");
      out.write("  //toSelectTreeMenu()\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function closeBS(){\r\n");
      out.write("\tparent.BSWINDOW.modal(\"hide\");;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"doInit()\" style=\"background-color:#f2f2f2;overflow-y:hidden; \">\r\n");
      out.write("\r\n");
      out.write("   <div  style=\"margin-top: 10px\">\r\n");
      out.write("        ");

         if(TeeUtility.isNullorEmpty(uuid)){
        	
        	  
      out.write("\r\n");
      out.write("        \t  <span class=\"\" style=\"font-size: 12px;font-weight: bold;\">\r\n");
      out.write("        \t  子菜单新增\r\n");
      out.write("        \t  </span>\r\n");
      out.write("        \t  ");

         }else{
        	 
      out.write("\t \r\n");
      out.write("        \t   <span class=\"\" style=\"font-size: 12px;font-weight: bold;\">\r\n");
      out.write("        \t 子菜单编辑\r\n");
      out.write("        \t  </span>\r\n");
      out.write("        \t \r\n");
      out.write("         ");
}
      out.write("\r\n");
      out.write("         <span class=\"basic_border\"></span>\r\n");
      out.write("   </div>\r\n");
      out.write("<form   method=\"post\" name=\"form1\" id=\"form1\">\r\n");
      out.write("<table class=\"TableBlock\" width=\"100%\" align=\"center\">\r\n");
      out.write("          <tr class=\"TableLine1\">\r\n");
      out.write("\t\t\t\t<td nowrap>所属应用系统：</td>\r\n");
      out.write("\t\t\t\t<td nowrap>\r\n");
      out.write("\t\t\t\t     <select class=\"BigSelect\" id=\"sysId\" name=\"sysId\">\r\n");
      out.write("\t\t\t\t         <option value=\"0\">公用</option>\r\n");
      out.write("\t\t\t\t     </select>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("            <tr class=\"TableLine1\">\r\n");
      out.write("\t\t\t<td nowrap style=\"text-indent: 10px\">上级菜单：</td>\r\n");
      out.write("\t\t\t<td>\r\n");
      out.write("\t\t\t<input type=\"text\" class=\"BigInput\" name=\"pMenuId\" id=\"pMenuId\" style=\"display:none;\" value=\"");
      out.print(pMenuId );
      out.write("\"/>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t<input type=\"text\" class=\"BigInput\" readonly name=\"pMenuIdDesc\" id=\"pMenuIdDesc\" value=\"");
      out.print(menuName);
      out.write("\" class=\"BigInput\" required/>&nbsp;\t\r\n");
      out.write("\t\t\t\t<a href =\"javascript:toSelectTreeMenu('");
      out.print(menuId );
      out.write("')\">添加</a>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr class=\"TableLine1\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<td nowrap style=\"text-indent: 10px\">子菜单编号：</td>\r\n");
      out.write("\t\t\t\t<td nowrap><INPUT type=text name=\"menuId\" id=\"menuId\"  class=\"BigInput\" required  onlyNumLength=\"3\" />\r\n");
      out.write("\t\t\t\t <br>说明：此代码为三位，作为排序之用。在同一父菜单下的平级菜单，该代码不能重复 \r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr class=\"TableLine2\">\r\n");
      out.write("\t\t\t\t<td nowrap style=\"text-indent: 10px\">子菜单名称：</td>\r\n");
      out.write("\t\t\t\t<td nowrap><input type=\"text\" name=\"menuName\" id=\"menuName\"  class=\"BigInput\" required value=\"");
      out.print(childMenuName );
      out.write("\" >&nbsp;</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("        <tr class=\"TableLine2\">\r\n");
      out.write("\t\t\t\t<td nowrap style=\"text-indent: 10px\">图片：</td>\r\n");
      out.write("\t\t\t\t<td nowrap>\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" name=\"icon\" id=\"icon\"  class=\"BigInput\" style=\"width:400px\"> \r\n");
      out.write("\t\t\t\t\t </td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr class=\"TableLine1\">\r\n");
      out.write("\t\t\t\t<td nowrap style=\"text-indent: 10px\">路径:</td>\r\n");
      out.write("\t\t\t\t<td nowrap><input type=\"text\" name=\"menuCode\" id=\"menuCode\"   class=\"BigInput\" value=\"");
      out.print(menuURL);
      out.write("\" style=\"width: 400px\"/>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t<div class=\"fr\" style=\"margin-top: 25px\">\r\n");
      out.write("\t\t   <input type=\"button\" value=\"保存\"\r\n");
      out.write("\t\t\tclass=\"btn-alert-blue\" onclick=\"doSave();\">&nbsp;&nbsp;\r\n");
      out.write("\t\t\t");
if(childMenuName.equals("") && childMenuName.equals("")){ 
      out.write("\r\n");
      out.write("\t\t\t<input type=\"button\" value=\"返回\" class=\"btn-alert-blue\" onclick=\"history.go(-1);\">&nbsp;&nbsp;\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t");
}else{ 
      out.write("\r\n");
      out.write("\t\t\t<input type=\"button\" value=\"关闭\" class=\"btn-alert-gray\" onclick=\"parent.$('#win_ico').click();\">&nbsp;&nbsp;\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t<input type=\"text\" id=\"uuid\" name=\"uuid\" style=\"display:none;\" value='0'/>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t<input type=\"text\" id=\"oldMenuId\" name=\"oldMenuId\" style=\"display:none;\"/>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("</form>\r\n");
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
