package org.apache.jsp.system.core.dept;

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

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(2);
    _jspx_dependants.add("/header/header2.0.jsp");
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

  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
  int isAdmin=0;//是否是超级管理员
  if(TeePersonService.checkIsAdminPriv(loginUser)){
	  isAdmin=1;
  }

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\">\r\n");
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
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("<!-- jQuery 布局器 -->\r\n");
      out.write("<script>\r\n");
      out.write("var isAdmin=");
      out.print(isAdmin);
      out.write(";\r\n");
      out.write("function doInit(){\r\n");
      out.write("    //判断当前登陆人是不是系统管理员\r\n");
      out.write("    if(isAdmin==1){\r\n");
      out.write("    \t$(\"#dcBtn\").show();\r\n");
      out.write("    \t$(\"#drBtn\").show();\r\n");
      out.write("    }\r\n");
      out.write("\t$(\"#group\").group();\r\n");
      out.write("\tchangePage('");
      out.print(contextPath);
      out.write("/system/core/dept/addupdate.jsp');\r\n");
      out.write("\tgetDeptTree();\r\n");
      out.write("\t\r\n");
      out.write("\t$(\".dom\").click(function(){\r\n");
      out.write("\t\t$(\".dom\").removeClass(\"li_active\");\r\n");
      out.write("\t\t$(this).addClass(\"li_active\");\r\n");
      out.write("\t});\r\n");
      out.write("\t\t\t\t$(\".panel-heading\").click(function(){\r\n");
      out.write("\t\t\t\t\tif($(this).siblings().find('.panel-body').length==0){\r\n");
      out.write("\t\t\t\t\t\treturn false;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\tvar $span = $(this).find('span');\r\n");
      out.write("\t\t\t\t\tvar isOpen = $span.hasClass(\"caret-down\");\r\n");
      out.write("\t\t\t\t\tif(isOpen){\r\n");
      out.write("\t\t\t\t\t\t//$('.panel-body ul').slideUp();\r\n");
      out.write("\t\t\t\t\t\t$(this).siblings('.collapse').slideUp(200);\r\n");
      out.write("\t\t\t\t\t\t$span.attr(\"class\",\"caret-right\");\r\n");
      out.write("\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\t$(this).siblings('.collapse').slideDown(200);\r\n");
      out.write("\t\t\t\t\t\t$span.attr(\"class\",\"caret-down\");\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("function  changePage(url){\r\n");
      out.write("\t$(\"#frame0\").attr(\"src\", url);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * 马上加载\r\n");
      out.write(" */\r\n");
      out.write(" var zTreeObj ;\r\n");
      out.write(" function getDeptTree(){\r\n");
      out.write("\t\tvar url = \"");
      out.print(contextPath );
      out.write("/orgManager/checkOrg.action\";\r\n");
      out.write("\t\tvar jsonObj = tools.requestJsonRs(url);\r\n");
      out.write("\t\tif(jsonObj.rtState){\r\n");
      out.write("\t\t\tvar json = jsonObj.rtData;\r\n");
      out.write("\t\t\tif(json.sid){\r\n");
      out.write("\t\t\t\tvar url = \"");
      out.print(contextPath );
      out.write("/deptManager/getOrgDeptTree.action\";\r\n");
      out.write("\t\t\t\tvar config = {\r\n");
      out.write("\t\t\t\t\t\tzTreeId:\"orgZtree\",\r\n");
      out.write("\t\t\t\t\t\trequestURL:url,\r\n");
      out.write("\t\t\t\t\t\tparam:{\"para1\":\"111\"},\r\n");
      out.write("\t\t\t\t\t\tonClickFunc:deptOnClick,\r\n");
      out.write("\t\t\t\t\t\tonAsyncSuccess:onDeptAsyncSuccess\r\n");
      out.write("\t\t\t\t\t};\r\n");
      out.write("\t\t\t\tzTreeObj = ZTreeTool.config(config);\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\t//alert(\"单位信息未录入，请您先填写单位信息！\");\r\n");
      out.write("\t\t\t\t$.MsgBox.Alert_auto(\"单位信息未录入，请您先填写单位信息！\");\r\n");
      out.write("\t\t\t\treturn;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write(" }\r\n");
      out.write(" function onDeptAsyncSuccess(event, treeId, treeNode, msg) {//异步执行成功后\r\n");
      out.write("\r\n");
      out.write("\texpandNodes(); \r\n");
      out.write(" }\r\n");
      out.write(" /**\r\n");
      out.write("   *第一级展开部门\r\n");
      out.write("   */\r\n");
      out.write("function expandNodes() {\r\n");
      out.write("\t if(!zTreeObj){\r\n");
      out.write("\t\tzTreeObj = $.fn.zTree.getZTreeObj(\"orgZtree\"); \r\n");
      out.write("\t }\r\n");
      out.write("\tvar nodes = zTreeObj.getNodes();\r\n");
      out.write("\tzTreeObj.expandNode(nodes[0], true, false, false);\r\n");
      out.write("\tif (nodes[0].isParent && nodes[0].zAsync  && nodes[0].id =='0') {//是第一级节点\r\n");
      out.write("\t\texpandNodes(nodes[0].children);\r\n");
      out.write("\t}\r\n");
      out.write("/* \talert(nodes[0].id)\r\n");
      out.write("\tfor (var i=0, l=nodes.length; i<l; i++) {\r\n");
      out.write("\t\tzTreeObj.expandNode(nodes[i], true, false, false);\r\n");
      out.write("\t\tif (nodes[i].isParent && nodes[i].zAsync ) {\r\n");
      out.write("\t\t\texpandNodes(nodes[i].children);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t} */\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * 点击节点\r\n");
      out.write(" */\r\n");
      out.write("function deptOnClick(event, treeId, treeNode) {\r\n");
      out.write("\tvar uuid = treeNode.id;\r\n");
      out.write("\tif(uuid.split(\";\").length == 2){\r\n");
      out.write("\t\tchangePage(\"");
      out.print(contextPath);
      out.write("/system/core/dept/addupdate.jsp?uuid=\" + uuid.split(\";\")[0]) ;\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function userGroup() {\r\n");
      out.write("\tchangePage(\"");
      out.print(contextPath);
      out.write("/system/core/dept/usergroup/personGroup.jsp\");\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * 设置菜单\r\n");
      out.write(" */\r\n");
      out.write("function setMenu(){\r\n");
      out.write("\t$('#teemenulist ul li a').click(function(even){\r\n");
      out.write("\t    if($(this).parent().children(\"div\").is(':hidden')){//上级（LI）下面存在UL标签且为隐藏的 ，都展开，否则反之\r\n");
      out.write("\t\t\t$(this).parent().children(\"div\").show('slow');//显示   \r\n");
      out.write("\t\t\tthis.className = \"aMenuVisited\";\r\n");
      out.write("\t     }else{\r\n");
      out.write("\t    \t$(this) .parent().children(\"div\").hide('slow');//隐藏\t\r\n");
      out.write("\t    \tthis.className = \"aMenulink\";//css({'class':'aMenuVisited'});\r\n");
      out.write("\t      } \r\n");
      out.write("\t   \r\n");
      out.write("\t}).css({'cursor':'pointer'});\r\n");
      out.write("\t isFirst = true;\r\n");
      out.write("\t//$(\"#teemenu ul li div\").hide();//隐藏下级所有节点\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function importDept(){\r\n");
      out.write("\tchangePage('import.jsp');\r\n");
      out.write("}\r\n");
      out.write("/**\r\n");
      out.write(" * 导出\r\n");
      out.write(" */\r\n");
      out.write("function exportDept(){\r\n");
      out.write("\tvar url = \"");
      out.print(contextPath);
      out.write("/orgImportExport/exportDept.action\";\r\n");
      out.write("\twindow.location.href = url;\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("<style type=\"\">\r\n");
      out.write("\t\tbody{\r\n");
      out.write("\t\t\tbackground-color:#eaedf2;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t.panel-heading > span{\r\n");
      out.write("\t\t\tposition:absolute;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t.panel-heading{\r\n");
      out.write("\t\tpadding: 10px 5px;\r\n");
      out.write("\t\tfont-size: 14px;\r\n");
      out.write("\t\ttext-align: left;\r\n");
      out.write("\t\ttext-indent:20px;\r\n");
      out.write("\t\tbox-sizing: border-box;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t.panel-title {\r\n");
      out.write("\t\t\tmargin-left:15px;\r\n");
      out.write("\t\t\tdisplay:inline;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t.panel-title a{\r\n");
      out.write("\t\tcolor:#000;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t.groupContent li{\r\n");
      out.write("\t\theight: 30px;\r\n");
      out.write("\t\tline-height: 30px;\r\n");
      out.write("\t\tfont-size: 12px;\r\n");
      out.write("\t\ttext-align: left;\r\n");
      out.write("\t\ttext-indent:60px;\r\n");
      out.write("\t\tcursor:pointer;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t.groupContent li:hover{\r\n");
      out.write("\t\tbackground-color:#fff;\r\n");
      out.write("\t\tcolor:#fff;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t.groupContent li a{\r\n");
      out.write("\t\tcolor:#000;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t.groupContent li a:hover{\r\n");
      out.write("\t\tcolor:#000;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t.li_active{\r\n");
      out.write("\t\t\tbackground-color:#fff;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t#orgZtree{\r\n");
      out.write("\t\t\tpadding-left:45px!important;\r\n");
      out.write("\t\t}\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"doInit()\" style=\"overflow:hidden;font-size:12px;\">\r\n");
      out.write("<div id=\"layout\" >\r\n");
      out.write("\t<div layout=\"north\" width=\"100%\" height=\"40px\" style=\"overflow:hidden;position:absolute;left:283px;top:0px;height:40px;right: 10px;background-color: white\">\r\n");
      out.write("\t  <div class=\"clearfix fr\" style=\"margin-top: 8px;\">\r\n");
      out.write("\t     <input type=\"button\" value=\"新建部门/成员单位\" class=\"btn-win-white\" onClick=\"changePage('addupdate.jsp');\" title=\"新建部门/成员单位\"/>\r\n");
      out.write("         <input id=\"drBtn\" style=\"display: none;\" type=\"button\" value=\"导入\" class=\"btn-win-white\" onClick=\"importDept();\" title=\"导入部门/成员单位\"/>\r\n");
      out.write("         <input id=\"dcBtn\" style=\"display: none;\" type=\"button\" value=\"导出\" class=\"btn-win-white\" onClick=\"exportDept();\" title=\"导出部门/成员单位\"/>\r\n");
      out.write("\t  </div>\r\n");
      out.write("      \r\n");
      out.write("      <span class=\"basic_border\"></span>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div layout=\"west\" width=\"280px\" style=\"overflow-y:auto;overflow-x:hidden;position:absolute;left:0px;top:0px;bottom:0px;width:280px\">\r\n");
      out.write("\t\t<br>\r\n");
      out.write("\t\t<div id=\"group\" class=\"list-group\">\r\n");
      out.write("\t\t \r\n");
      out.write("\t\t \t<div class=\"panel-group\" class=\"list-group\">\r\n");
      out.write("\t\t\t\t  <div class=\"panel panel-default\">\r\n");
      out.write("\t\t\t\t   <div class=\"panel-heading menuList\">\r\n");
      out.write("\t\t\t\t     <span class='caret-down' ></span>\r\n");
      out.write("\t\t\t\t      <h4 class=\"panel-title\">\r\n");
      out.write("\t\t\t\t        <a data-toggle=\"collapse\" data-toggle=\"collapse\" data-parent=\"#accordion\" href=\"#collapseOne\">\r\n");
      out.write("\t\t\t\t          \t部门列表\r\n");
      out.write("\t\t\t\t        </a>\r\n");
      out.write("\t\t\t\t      </h4>\r\n");
      out.write("\t\t\t\t    </div>\r\n");
      out.write("\t\t\t\t    <div id=\"collapseOne\" class=\"panel-collapse collapse in\">\r\n");
      out.write("\t\t\t\t      \t<div class=\"panel-body\" style=\"padding:0px;\">\r\n");
      out.write("\t\t\t\t       \t\t<ul id=\"orgZtree\" class=\"ztree\" style=\"overflow:auto;border:0px;margin-top:0px;width:100%;height:100%;min-height:330px; padding:2px;\"></ul>\r\n");
      out.write("\t\t\t\t   \t    </div>\r\n");
      out.write("\t\t\t\t    </div>\r\n");
      out.write("\t\t\t\t  </div>\r\n");
      out.write("\t\t\t\t  <div class=\"panel panel-default\">\r\n");
      out.write("\t\t\t\t    <div class=\"panel-heading menuList dom\">\r\n");
      out.write("\t\t\t\t      <h5 class=\"panel-title\">\r\n");
      out.write("\t\t\t\t        <a data-toggle=\"collapse\" data-toggle=\"collapse\" data-parent=\"#accordion\" href=\"#\" onclick=\"userGroup();\">\r\n");
      out.write("\t\t\t\t         \t公共自定义组\r\n");
      out.write("\t\t\t\t        </a>\r\n");
      out.write("\t\t\t\t      </h5>\r\n");
      out.write("\t\t\t\t    </div>\r\n");
      out.write("\t\t\t\t   \r\n");
      out.write("\t\t\t\t  </div>\r\n");
      out.write("\t\t\t\t  \r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t  </div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div layout=\"center\" style=\"padding-left:2px;position:absolute;left:281px;top:41px;bottom:0px;right:0px;\">\r\n");
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
