package org.apache.jsp.system.core.person;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.io.FileInputStream;
import java.io.File;
import org.jdom.input.SAXBuilder;
import org.jdom.Element;
import org.jdom.Document;
import com.tianee.oa.core.partthree.util.TeePartThreeUtil;
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
import com.tianee.webframe.util.servlet.TeeCookieUtils;

public final class personList_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(2);
    _jspx_dependants.add("/header/header2.0.jsp");
    _jspx_dependants.add("/header/easyui2.0.jsp");
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

    int orgId=TeeStringUtil.getInteger(request.getParameter("orgId"), 0);
	String deptId = request.getParameter("deptId") == null ? "" : request.getParameter("deptId");
	String deptName = request.getParameter("deptName") == null ? "" : request.getParameter("deptName");
	if("undefined".equals(deptName)){
		deptName = "";
	}
	
	//读取ukcode
	 File file=new File(TeeSysProps.getRootPath()+"/system/ukey/auth_code.xml");
     FileInputStream  in=new FileInputStream(file);
     SAXBuilder builder = new SAXBuilder();
	 Document doc = null;
	try {
		doc = builder.build(in);
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	Element root = doc.getRootElement();
	String UkCode=root.getChild("authcode").getText();
	
	
	
	TeePerson  loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	//系统管理员权限
	boolean adminPriv=TeePartThreeUtil.checkHasPriv(loginUser, "SYS_USER_MANAGE");
	//系统安全员权限
	boolean saferPriv=TeePartThreeUtil.checkHasPriv(loginUser, "SYS_USER_PRIV");

      out.write("\r\n");
      out.write("<!DOCTYPE html >\r\n");
      out.write("<html>\r\n");
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
      out.write("\r\n");
      out.write("<!-- zt_webframe框架引入 核心库 -->\r\n");
      out.write("<script type=\"text/javascript\" src = '");
      out.print(request.getContextPath() );
      out.write("/common/jquery-easyui-1.6.11/jquery.easyui.min.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src = '");
      out.print(request.getContextPath() );
      out.write("/common/zt_webframe/js/jquery.datagrid.extend.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src = '");
      out.print(request.getContextPath() );
      out.write("/common/jquery-easyui-1.6.11/locale/easyui-lang-zh_CN.js'></script>\r\n");
      out.write("\r\n");

Cookie __cookie = TeeCookieUtils.getCookie(request, "skin_new");
String __skinNew = "1";
if(__cookie!=null){
	__skinNew = __cookie.getValue();
}

      out.write("\r\n");
      out.write("<!-- zt_webframe框架引入 css样式 -->\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(request.getContextPath());
      out.write("/common/jquery-easyui-1.6.11/themes/metro/easyui.css\">\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath );
      out.write("/system/core/person/js/person.js\"></script>\r\n");
      out.write("\t<title>人员管理</title>\r\n");
      out.write("\r\n");
      out.write("\t<script type=\"text/javascript\" charset=\"UTF-8\">\r\n");
      out.write("\tvar adminPriv=");
      out.print(adminPriv );
      out.write(";//系统管理员权限\r\n");
      out.write("\tvar saferPriv=");
      out.print(saferPriv );
      out.write(";//系统安全员权限\r\n");
      out.write("\tvar orgId=");
      out.print(orgId );
      out.write(";\r\n");
      out.write("\tvar UkCode=\"");
      out.print(UkCode);
      out.write("\";//UkCode\r\n");
      out.write("\tvar deptId = '");
      out.print(deptId);
      out.write("';\r\n");
      out.write("\tvar datagrid;\r\n");
      out.write("\tvar userDialog;\r\n");
      out.write("\tvar userForm;\r\n");
      out.write("\tvar passwordInput;\r\n");
      out.write("\tvar userRoleDialog;\r\n");
      out.write("\tvar userRoleForm;\r\n");
      out.write("\tvar title =\"\";\r\n");
      out.write("\tvar usbKey = \"");
      out.print(TeeSysProps.getString("USB_KEY"));
      out.write("\";\r\n");
      out.write("\t\r\n");
      out.write("// \t$(window).resize(function () {\r\n");
      out.write("// \t  $('#table-bootstrap').bootstrapTable('resetView', {\r\n");
      out.write("// \t    height: getHeight()\r\n");
      out.write("// \t  });\r\n");
      out.write("// \t});\r\n");
      out.write("\r\n");
      out.write("// \tfunction getHeight() {\r\n");
      out.write("// \t  return $(window).height()-40;\r\n");
      out.write("// \t}\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\tfunction getIdSelections() {\r\n");
      out.write("        return $.map($('#datagrid').datagrid('getSelections'), function (row) {\r\n");
      out.write("            return row.uuid;\r\n");
      out.write("        });\r\n");
      out.write("    }\r\n");
      out.write("\t\t\r\n");
      out.write("\t$(function() {\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar column=[\r\n");
      out.write("\t\t  \t\t\t{field:'userId',title:'用户名',width:150,\tformatter : function(value, rowData, rowIndex) {\r\n");
      out.write("\t\t\t\t\t\tif(rowData.notLogin && rowData.notLogin == '1'){\r\n");
      out.write("\t\t\t\t\t\t\treturn \"<font color='gray'> \" + value + \"</font>\";\r\n");
      out.write("\t\t\t\t\t\t}else if(rowData.passwordIsNUll == '1'){\r\n");
      out.write("\t\t\t\t\t\t\treturn \"<font color='red'> \" + value + \"</font>\";\r\n");
      out.write("\t\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\t\treturn value;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t}},\r\n");
      out.write("\t\t  \t\t\t\r\n");
      out.write("\t\t  \t\t\t{field:'userName',title:'用户姓名',width:200,formatter : function(value, rowData, rowIndex) {\r\n");
      out.write("\t\t\t\t\t\tif(rowData.notLogin && rowData.notLogin == '1'){\r\n");
      out.write("\t\t\t\t\t\t\treturn \"<font color='gray'> \" + value + \"</font>\";\r\n");
      out.write("\t\t\t\t\t\t}else if(rowData.passwordIsNUll == '1'){\r\n");
      out.write("\t\t\t\t\t\t\treturn \"<font color='red'> \" + value + \"</font>\";\r\n");
      out.write("\t\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\t\treturn value;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}},\r\n");
      out.write("\t\t  \t\t\t{field:'deptIdName',title:'部门',width:200,formatter : function(value, rowData, rowIndex) {\r\n");
      out.write("\t\t\t\t\t\tvalue = value==undefined?\"\":value;\r\n");
      out.write("\t\t\t\t\t\tif(rowData.notLogin && rowData.notLogin == '1'){\r\n");
      out.write("\t\t\t\t\t\t\treturn \"<font color='gray'> \" + value + \"</font>\";\r\n");
      out.write("\t\t\t\t\t\t}else if(rowData.passwordIsNUll == '1'){\r\n");
      out.write("\t\t\t\t\t\t\treturn \"<font color='red'> \" + value + \"</font>\";\r\n");
      out.write("\t\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\t\treturn value;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}},\r\n");
      out.write("\t\t\t\t\t{field : 'userRoleStrName',title : '角色',width : 100,formatter : function(value, rowData, rowIndex) {\r\n");
      out.write("\t\t\t\t\t\t\tif(rowData.notLogin && rowData.notLogin == '1'){\r\n");
      out.write("\t\t\t\t\t\t\t\treturn \"<font color='gray'> \" + value + \"</font>\";\r\n");
      out.write("\t\t\t\t\t\t\t}else if(rowData.passwordIsNUll == '1'){\r\n");
      out.write("\t\t\t\t\t\t\t\treturn \"<font color='red'> \" + value + \"</font>\";\r\n");
      out.write("\t\t\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\t\t\treturn value;\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t}},\r\n");
      out.write("\t\t\t\t\t\t {field : 'sex',title : '性别',width : 40,formatter : function(value, rowData, rowIndex) {\r\n");
      out.write("\t\t\t\t\t\t\t\tif(value == '1'){\r\n");
      out.write("\t\t\t\t\t\t\t\t\tvalue =  \"女\";\r\n");
      out.write("\t\t\t\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\t\t\t\tvalue = \"男\";\r\n");
      out.write("\t\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t\tif(rowData.notLogin && rowData.notLogin == '1'){\r\n");
      out.write("\t\t\t\t\t\t\t\t\treturn \"<font color='gray'> \" + value + \"</font>\";\r\n");
      out.write("\t\t\t\t\t\t\t\t}else if(rowData.passwordIsNUll == '1'){\r\n");
      out.write("\t\t\t\t\t\t\t\t\treturn \"<font color='red'> \" + value + \"</font>\";\r\n");
      out.write("\t\t\t\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\t\t\t\treturn value;\r\n");
      out.write("\t\t\t\t\t\t\t\t}\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t}} , \r\n");
      out.write("\t\t\t\t\t\t\t{field : 'postPriv',title : '管理范围',width : 100,formatter : function(value, rowData, rowIndex) {\r\n");
      out.write("\t\t\t\t\t\t\t\tif( value == '1'){\r\n");
      out.write("\t\t\t\t\t\t\t\t\tvalue =  \"全体\";\r\n");
      out.write("\t\t\t\t\t\t\t\t}else if(value == '2'){\r\n");
      out.write("\t\t\t\t\t\t\t\t\tvalue = \"指定部门\";\r\n");
      out.write("\t\t\t\t\t\t\t\t}else if(value == '3'){\r\n");
      out.write("\t\t\t\t\t\t\t\t\tvalue = \"本部门及下级所有部门\";\r\n");
      out.write("\t\t\t\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\t\t\t\tvalue = \"本部门\";\r\n");
      out.write("\t\t\t\t\t\t\t\t}\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\tif(rowData.notLogin && rowData.notLogin == '1'){\r\n");
      out.write("\t\t\t\t\t\t\t\t\treturn \"<font color='gray'> \" + value + \"</font>\";\r\n");
      out.write("\t\t\t\t\t\t\t\t}else if(rowData.passwordIsNUll == '1'){\r\n");
      out.write("\t\t\t\t\t\t\t\t\treturn \"<font color='red'> \" + value + \"</font>\";\r\n");
      out.write("\t\t\t\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\t\t\t\treturn value;\r\n");
      out.write("\t\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t}}\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t  \t\t];\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t//获取当前登录人员的管理范围\r\n");
      out.write("\t\tvar json = tools.requestJsonRs(contextPath+\"/personManager/getPostDeptIds.action\");\r\n");
      out.write("\t\tvar postIds = \",\"+json.rtData+\",\";\r\n");
      out.write("\t\tif(postIds==\",0,\" || postIds.indexOf(deptId)!=-1){//有管理权限\r\n");
      out.write("\t\t\tcolumn.push(\r\n");
      out.write("\t\t\t{field : '_optmanage',title : '操作',width : 150,formatter : function(value, rowData, rowIndex) {\r\n");
      out.write("\t\t\t\tvar html=\"\";\r\n");
      out.write("\t\t\t\tif(adminPriv||saferPriv){//系统管理员  或者  系统安全员\r\n");
      out.write("\t\t\t\t\thtml=\"<a href='#' onclick='toAddUpdatePerson(\\\"\" +rowData.uuid + \"\\\");'> 编辑 </a>\"; \t\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tif(adminPriv){//系统管理员\r\n");
      out.write("\t\t\t\t\tif(rowData.keySN!=null&&rowData.keySN!=\"undefined\"&&rowData.keySN!=\"null\"&&usbKey==\"1\"){//绑定过uk\r\n");
      out.write("\t\t\t\t\t\thtml+=\"&nbsp;&nbsp;&nbsp;<a href='#' onclick='unbund(\\\"\" +rowData.uuid + \"\\\");'>解绑Ukey</a>\";\r\n");
      out.write("\t\t\t\t\t}else if(usbKey==\"1\"){\r\n");
      out.write("\t\t\t\t\t\thtml+=\"&nbsp;&nbsp;&nbsp;<a href='#' onclick='bund(\\\"\" +rowData.uuid + \"\\\",\\\"\"+rowData.userId+\"\\\");'>绑定Ukey</a>\";\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\treturn  html;\r\n");
      out.write("\t\t\t}});\r\n");
      out.write("\t\t}else{//无管理权限\r\n");
      out.write("\t\t\t$(\"#toolbar\").html(\"\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tdatagrid = $('#datagrid').datagrid({\r\n");
      out.write("\t\t\turl: contextPath + '/personManager/getPersonList.action?sort=userNo&deptId=' + deptId,\r\n");
      out.write("\t\t    pagination:true,\r\n");
      out.write("\t\t    singleSelect:false,\r\n");
      out.write("\t\t    view:window.EASYUI_DATAGRID_NONE_DATA_TIP,\r\n");
      out.write("\t\t    toolbar:'#toolbar',//工具条对象\r\n");
      out.write("\t    \tcheckbox:true,\r\n");
      out.write("\t\t    border:false,\r\n");
      out.write("\t\t    idField:'sid',//主键列\r\n");
      out.write("\t\t    fitColumns:true,//列是否进行自动宽度适应\r\n");
      out.write("\t\t    columns:[column]\r\n");
      out.write("\t\t  \t});\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("//绑定\r\n");
      out.write("function bund(uuid,userId){\r\n");
      out.write("\tvar res = AuthIE.Open(\"<dogscope/>\", UkCode);\r\n");
      out.write("\tif(res==0){\r\n");
      out.write("\t\tAuthIE.GetDogID();\r\n");
      out.write("\t\tvar dogid = AuthIE.DogIdStr;\r\n");
      out.write("\t\t//判断dogid是否被其他人占用\r\n");
      out.write("\t\tvar url=contextPath+\"/personManager/checkKeySNIsExist.action\";\r\n");
      out.write("\t\tvar json=tools.requestJsonRs(url,{keySN:dogid});\r\n");
      out.write("\t\tif(json.rtState){\r\n");
      out.write("\t\t\tvar data=json.rtData;\r\n");
      out.write("\t\t\tif(data==1){//设备被别人占用   不可用\r\n");
      out.write("\t\t\t\t$.MsgBox.Alert_auto(\"该设备已被其他用户绑定！\");\r\n");
      out.write("\t\t\t\treturn;\r\n");
      out.write("\t\t\t}else{//设备没有被其他人占用    可用\r\n");
      out.write("\t\t\t\t //判读即将被绑定的人员是否已经与其他的设备绑定了\r\n");
      out.write("\t\t\t\t var url1=contextPath+\"/personManager/checkUserIsBound.action\";\r\n");
      out.write("\t\t\t     var json1=tools.requestJsonRs(url1,{uuid:uuid});\r\n");
      out.write("\t\t\t\t if(json1.rtState){\r\n");
      out.write("\t\t\t\t\t var data1=json1.rtData;\r\n");
      out.write("\t\t\t\t\t if(data1==1){//已经绑定过其他设备  不能再绑定了\r\n");
      out.write("\t\t\t\t\t\t $.MsgBox.Alert_auto(\"当前用户已经绑定过其他设备！\");\r\n");
      out.write("\t\t\t\t\t     return;\r\n");
      out.write("\t\t\t\t\t }else{//没有绑定过其他设备  可以进行绑定\r\n");
      out.write("\t\t\t\t\t\t //用户注册  将用户登录名写入设备   统一Ukey密码设置为12345678\r\n");
      out.write("\t\t\t\t\t\t AuthIE.RegisterUser(userId, \"12345678\");\r\n");
      out.write("\t\t\t\t\t\t AuthIE.GetUserName();\r\n");
      out.write("\t\t\t\t\t     var name = AuthIE.UserNameStr;\t \t\r\n");
      out.write("\t\t\t\t\t     //alert(name); \r\n");
      out.write("\t\t\t\t\t     //将keySN存到用户数据中\r\n");
      out.write("\t\t\t\t\t     var url2=contextPath+\"/personManager/boundUkey.action\";\r\n");
      out.write("\t\t\t\t\t     var json2=tools.requestJsonRs(url2,{uuid:uuid,keySN:dogid});\r\n");
      out.write("\t\t\t\t\t     if(json2.rtState){\r\n");
      out.write("\t\t\t\t\t    \t $.MsgBox.Alert_auto(\"绑定成功！\");\r\n");
      out.write("\t\t\t\t\t    \t datagrid.datagrid(\"reload\");\r\n");
      out.write("\t\t\t\t\t     }else{\r\n");
      out.write("\t\t\t\t\t    \t $.MsgBox.Alert_auto(\"绑定出错！\");\r\n");
      out.write("\t\t\t\t\t\t     return;\r\n");
      out.write("\t\t\t\t\t     }\r\n");
      out.write("\t\t\t\t\t }\r\n");
      out.write("\t\t\t\t }\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$.MsgBox.Alert_auto(\"查询出错！\");\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\t$.MsgBox.Alert_auto(\"无法连接到设备！\");\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("\tAuthIE.Close();\r\n");
      out.write("}\r\n");
      out.write("//解绑\r\n");
      out.write("function unbund(uuid){\r\n");
      out.write("\t $.MsgBox.Confirm (\"提示\", \"是否确认解除绑定？\", function(){\r\n");
      out.write("\t\t var res = AuthIE.Open(\"<dogscope/>\", UkCode);\r\n");
      out.write("\t\t if(res==0){\r\n");
      out.write("\t\t\t AuthIE.GetDogID();\r\n");
      out.write("\t\t\t var dogid = AuthIE.DogIdStr;\r\n");
      out.write("\t\t\t //判断当前连接到的设备是不是与用户匹配\r\n");
      out.write("\t\t\t var url=contextPath+\"/personManager/checkUserAndUkeyIsMatch.action\";\r\n");
      out.write("\t\t\t var json=tools.requestJsonRs(url,{uuid:uuid,keySN:dogid});\r\n");
      out.write("\t\t\t if(json.rtState){//匹配\r\n");
      out.write("\t\t\t\t //解除绑定\r\n");
      out.write("\t\t\t\t //用户注册  将用户名清空       统一Ukey密码设置为12345678\r\n");
      out.write("\t\t\t\t AuthIE.RegisterUser(\" \", \"12345678\");\r\n");
      out.write("\t\t\t\t AuthIE.GetUserName();\r\n");
      out.write("\t\t\t     var name = AuthIE.UserNameStr;\t \t\r\n");
      out.write("\t\t\t     //alert(name); \r\n");
      out.write("\t\t\t     var url1=contextPath+\"/personManager/unBoundUkey.action\";\r\n");
      out.write("\t\t\t     var json1=tools.requestJsonRs(url1,{uuid:uuid});\r\n");
      out.write("\t\t\t\t if(json1.rtState){\r\n");
      out.write("\t\t\t\t\t $.MsgBox.Alert_auto(\"解绑成功！\");\r\n");
      out.write("\t\t\t\t\t datagrid.datagrid(\"reload\");\r\n");
      out.write("\t\t\t\t }else{\r\n");
      out.write("\t\t\t\t\t $.MsgBox.Alert_auto(\"解绑出错！\");\r\n");
      out.write("\t\t\t\t     return; \r\n");
      out.write("\t\t\t\t }\r\n");
      out.write("\t\t\t\t \r\n");
      out.write("\t\t\t\t \r\n");
      out.write("\t\t\t }else{//不匹配\r\n");
      out.write("\t\t\t\t $.MsgBox.Alert_auto(\"该设备与解绑用户不匹配！\");\r\n");
      out.write("\t\t\t     return;\r\n");
      out.write("\t\t\t }\r\n");
      out.write("\t\t }else{\r\n");
      out.write("\t\t\t$.MsgBox.Alert_auto(\"无法连接到设备！\");\r\n");
      out.write("\t\t    return; \r\n");
      out.write("\t\t }\r\n");
      out.write("\t\t \r\n");
      out.write("\t\t AuthIE.Close();\r\n");
      out.write("\t  });\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function toAddUpdatePersonList(deptId){\r\n");
      out.write("\tvar deptName = '");
      out.print(deptName);
      out.write("';\r\n");
      out.write("\tvar url = \"");
      out.print(contextPath );
      out.write("/system/core/person/addupdate.jsp?deptId=\" + deptId + \"&deptName=\" + encodeURIComponent(deptName);\r\n");
      out.write("\t//openWindow(url,\"addupdatePerson\",870,540);\r\n");
      out.write("\twindow.parent.changePage(url);\r\n");
      out.write("}\r\n");
      out.write("/**\r\n");
      out.write(" * 批量删除人员\r\n");
      out.write(" */\r\n");
      out.write("function deletePerson(){\r\n");
      out.write("\t\r\n");
      out.write("\tvar selections = getIdSelections();\r\n");
      out.write("\tif(selections.length==0){\r\n");
      out.write("\t\t$.MsgBox.Alert_auto(\"至少选择一项！\");\r\n");
      out.write("\t\treturn ;\r\n");
      out.write("\t}\r\n");
      out.write("\t$.MsgBox.Confirm (\"提示\", \"确定要将所选中人员更改为离职状态吗？\",function(){\r\n");
      out.write("\t\tvar url = contextPath +  \"/personManager/updateDelPersonByUuids.action?uuids=\" + selections;\r\n");
      out.write("\t\tvar para = {};\r\n");
      out.write("\t\tvar jsonRs = tools.requestJsonRs(url,para);\r\n");
      out.write("\t\tif(jsonRs.rtState){\r\n");
      out.write("\t\t\t$.MsgBox.Alert_auto(\"操作成功！\",function(){\r\n");
      out.write("\t\t    if(deptId==0){//顶层节点\r\n");
      out.write("\t\t    \tparent.refreshTargetNode(orgId);\r\n");
      out.write("\t\t    }else{\r\n");
      out.write("\t\t    \tparent.refreshTargetNode(deptId+\";dept\");\r\n");
      out.write("\t\t    }\r\n");
      out.write("\t\t    \r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\twindow.location.reload();\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$.MsgBox.Alert_auto(jsonRs.rtMsg);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("}\r\n");
      out.write("/**\r\n");
      out.write(" * easyUI 清空密码\r\n");
      out.write(" * @param uuid\r\n");
      out.write(" */\r\n");
      out.write("function easyUIClearPassword(){\r\n");
      out.write("\tvar selections = getIdSelections();\r\n");
      out.write("\tif(selections.length==0){\r\n");
      out.write("\t\t$.MsgBox.Alert_auto(\"至少选择一项！\");\r\n");
      out.write("\t\treturn ;\r\n");
      out.write("\t}\r\n");
      out.write("\t$.MsgBox.Confirm (\"提示\", \"确定要清空密码？\",function(){\r\n");
      out.write("\t\tvar url = contextPath +  \"/personManager/clearPassword.action?uuids=\" + selections;\r\n");
      out.write("\t\tvar para = {};\r\n");
      out.write("\t\tvar jsonRs = tools.requestJsonRs(url,para);\r\n");
      out.write("\t\tif(jsonRs.rtState){\r\n");
      out.write("\t\t\t$.MsgBox.Alert_auto(jsonRs.rtMsg,function(){\r\n");
      out.write("\t\t\twindow.location.reload();\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$.MsgBox.Alert_auto(jsonRs.rtMsg);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"\" style=\"padding: 10px 10px 10px 10px;\">\r\n");

	if("1".equals(TeeSysProps.getString("USB_KEY"))){
		
      out.write("\r\n");
      out.write("\t\t<object id=\"AuthIE\" name=\"AuthIE\" width=\"0px\" height=\"0px\"\r\n");
      out.write("\t\t   codebase=\"/system/ukey/DogAuth.CAB\"\r\n");
      out.write("\t\t   classid=\"CLSID:05C384B0-F45D-46DB-9055-C72DC76176E3\">\r\n");
      out.write("\t\t</object>\r\n");
      out.write("\t\t");

	}

      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<table id=\"datagrid\" fit=\"true\"></table>\r\n");
      out.write("\t  <div id=\"toolbar\" class=\" clearfix\" style=\"padding-top: 5px;padding-bottom: 5px;\">\r\n");
      out.write("\t   \r\n");
      out.write("\t     <div style=\"padding-top: 5px;padding-bottom: 10px;\">\r\n");
      out.write("\t\t   <h5 style=\"font-size: 14px;\">部门（");
      out.print(deptName );
      out.write("）－ 用户管理，说明：密码为空用户显示为红色，禁止登录用户显示为灰色</h5>\r\n");
      out.write("\t     </div>\r\n");
      out.write("\t    \r\n");
      out.write("\t    ");
  
			   if(adminPriv){//没有权限
				   
      out.write("\r\n");
      out.write("\t\t\t\t   \r\n");
      out.write("\t\t\t\t   <input type=\"button\" class=\"btn-win-white\"   onclick=\"toAddUpdatePersonList('");
      out.print(deptId );
      out.write("');\" value=\"添加人员\"/>\r\n");
      out.write("\t   \t           <input type=\"button\" class=\"btn-win-white\"  onclick=\"easyUIClearPassword()\" value=\"清空密码\">\r\n");
      out.write("\t   \t           <input type=\"button\" class=\"btn-win-white\"  onclick=\"deletePerson()\" value=\"离职\">\r\n");
      out.write("\t\t\t\t   ");

			   }
		   
		
      out.write("\r\n");
      out.write("\t  \t\r\n");
      out.write("\t  </div>\r\n");
      out.write("\r\n");
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
