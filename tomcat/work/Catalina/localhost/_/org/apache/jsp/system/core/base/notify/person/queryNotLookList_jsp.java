package org.apache.jsp.system.core.base.notify.person;

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
import com.tianee.webframe.util.servlet.TeeCookieUtils;

public final class queryNotLookList_jsp extends org.apache.jasper.runtime.HttpJspBase
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

		String groupId = request.getParameter("groupId");
		String seqIds = request.getParameter("seqIds");
		if(groupId == null || "".equals(groupId)){
			groupId = "0";
		}
		String typeId = request.getParameter("typeId");
		typeId=typeId==null?"":typeId;


      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
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
      out.write("\t<title>未读公告</title>\r\n");
      out.write("\r\n");
      out.write("\t<script type=\"text/javascript\" charset=\"UTF-8\">\r\n");
      out.write("\tvar contextPath = '");
      out.print(contextPath);
      out.write("';\r\n");
      out.write("\tvar datagrid;\r\n");
      out.write("\tvar userDialog;\r\n");
      out.write("\tvar userForm;\r\n");
      out.write("\tvar passwordInput;\r\n");
      out.write("\tvar userRoleDialog;\r\n");
      out.write("\tvar userRoleForm;\r\n");
      out.write("\tvar seqIds = '");
      out.print(seqIds);
      out.write("';\r\n");
      out.write("\tvar groupId = '");
      out.print(groupId);
      out.write("';\r\n");
      out.write("\tvar typeId = '");
      out.print(typeId);
      out.write("';\r\n");
      out.write("\tvar para = {};\r\n");
      out.write("\tpara['groupId'] = groupId;\r\n");
      out.write("\tpara['seqIds'] = seqIds;\r\n");
      out.write("\tpara['typeId'] = typeId;\r\n");
      out.write("\t$(function() {\r\n");
      out.write("\t\tdatagrid = $('#datagrid').datagrid({\r\n");
      out.write("\t\t\turl : contextPath + '/teeNotifyController/getNoReadNotify.action',\r\n");
      out.write("\t\t\tview:window.EASYUI_DATAGRID_NONE_DATA_TIP,\r\n");
      out.write("\t\t//\ttoolbar : '#toolbar',\r\n");
      out.write("\t\t\ttitle : '',\r\n");
      out.write("\t\t\ticonCls : 'icon-save',\r\n");
      out.write("\t\t\tpagination : true,\r\n");
      out.write("\t\t\tpageSize : 10,\r\n");
      out.write("\t\t\t/* pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ], */\r\n");
      out.write("\t\t\tfit : true,\r\n");
      out.write("\t\t\tqueryParams:para,\r\n");
      out.write("\t\t\tfitColumns : true,\r\n");
      out.write("\t\t\tnowrap : true,\r\n");
      out.write("\t\t\tborder : false,\r\n");
      out.write("\t\t\tidField : 'sid',\r\n");
      out.write("\t\t\tsortOrder: 'desc',\r\n");
      out.write("\t\t\tstriped: true,\r\n");
      out.write("\t\t\tsingleSelect:true,\r\n");
      out.write("\t\t\tremoteSort: true,\r\n");
      out.write("\t\t\ttoolbar: '#toolbar',\r\n");
      out.write("\t\t\tcolumns : [ [ \r\n");
      out.write("\t\t\t/*  {field:'ck',checkbox:true},{\r\n");
      out.write("\t\t\t\ttitle : 'id',\r\n");
      out.write("\t\t\t\tfield : 'sid',\r\n");
      out.write("\t\t\t\twidth : 200,\r\n");
      out.write("\t\t\t\thidden:true\r\n");
      out.write("\t\t\t\t//sortable:true\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t}, */ \r\n");
      out.write("\t\t\t{field:'count',\r\n");
      out.write("\t\t\t\ttitle:'序号',\r\n");
      out.write("\t\t\t    sortable:false,\r\n");
      out.write("\t\t\t    width:30,\r\n");
      out.write("\t\t\t    formatter:function(value,rowData,rowIndex){\r\n");
      out.write("\t\t\t        return rowIndex + 1;\r\n");
      out.write("\t\t\t    }\r\n");
      out.write("\t\t\t},\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tfield : 'fromPersonName',\r\n");
      out.write("\t\t\t\ttitle : '发布部门',\r\n");
      out.write("\t\t\t\twidth : 80,\r\n");
      out.write("\t\t\t\tsortable : true,\r\n");
      out.write("\t\t\t\tformatter:function(value,rowData,rowIndex){\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\treturn \"<B>\"+rowData.fromDeptName+\"</B>\";\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t},{\r\n");
      out.write("\t\t\t\tfield : 'typeId',\r\n");
      out.write("\t\t\t\ttitle : '类型',\r\n");
      out.write("\t\t\t\twidth : 100,\r\n");
      out.write("\t\t\t\tsortable : true,\r\n");
      out.write("\t\t\t\tformatter:function(value,rowData,rowIndex){\r\n");
      out.write("\t\t\t\t\treturn rowData.typeDesc;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t,{\r\n");
      out.write("\t\t\t\tfield : 'typeDesc',\r\n");
      out.write("\t\t\t\ttitle : '类型描述',\r\n");
      out.write("\t\t\t\twidth : 100,\r\n");
      out.write("\t\t\t\thidden : true\r\n");
      out.write("\t\t\t},{\r\n");
      out.write("\t\t\t\tfield : 'top',\r\n");
      out.write("\t\t\t\ttitle : '置顶',\r\n");
      out.write("\t\t\t\twidth : 100,\r\n");
      out.write("\t\t\t\thidden : true\r\n");
      out.write("\t\t\t},{\r\n");
      out.write("\t\t\t\tfield : 'subject',\r\n");
      out.write("\t\t\t\ttitle : '标题',\r\n");
      out.write("\t\t\t\twidth : 300,\r\n");
      out.write("\t\t\t\tsortable : true,\r\n");
      out.write("\t\t\t\tformatter:function(value,rowData,rowIndex){\r\n");
      out.write("\t\t\t\t\tvar top = rowData.top;\r\n");
      out.write("\t\t\t\t\tvar topDesc = \"\";\r\n");
      out.write("\t\t\t\t\tif(top == '1'){\r\n");
      out.write("\t\t\t\t\t\ttopDesc = \"red\";\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\treturn \"<B>标题：</B>&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick=loadDetail('\"+rowData.sid+\"') style='color:\" + topDesc + \"'>\"+rowData.subject+\"</a>\";\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t},{\r\n");
      out.write("\t\t\t\tfield : 'userId',\r\n");
      out.write("\t\t\t\ttitle : '发布范围',\r\n");
      out.write("\t\t\t\twidth : 220,\r\n");
      out.write("\t\t\t\tformatter:function(value,rowData,rowIndex){\r\n");
      out.write("\t\t\t\tvar deptNames = rowData.toDeptNames;\r\n");
      out.write("\t\t\t\tvar personNames = rowData.toUserNames;\r\n");
      out.write("\t\t\t\tvar toRolesNames = rowData.toRolesNames;\r\n");
      out.write("\t\t\t\tvar renderHtml = \"\";\r\n");
      out.write("\t\t\t\tif(deptNames && deptNames != \"\"){\r\n");
      out.write("\t\t\t\t\trenderHtml = renderHtml + \"<div title=\"+deptNames+\"><B>部门：</B>&nbsp;&nbsp;\"+deptNames+\"\"+\"</div>\";\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tif(personNames && personNames != \"\"){\r\n");
      out.write("\t\t\t\t\trenderHtml = renderHtml + \"<div title=\"+personNames+\"><B>人员：</B>&nbsp;&nbsp;\"+personNames+\"\"+\"</div>\";\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tif(toRolesNames && toRolesNames != \"\"){\r\n");
      out.write("\t\t\t\t\trenderHtml = renderHtml + \"<div title=\"+toRolesNames+\"><B>角色：</B>&nbsp;&nbsp;\"+toRolesNames+\"\"+\"</div>\";\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tif(rowData.allPriv==1){\r\n");
      out.write("\t\t\t\t\trenderHtml=\"<div>全体人员</div>\";\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\treturn \"<div>\"+renderHtml+\"</div>\";\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t},{\r\n");
      out.write("\t\t\t\tfield : 'sendTime',\r\n");
      out.write("\t\t\t\ttitle : '发布时间',\r\n");
      out.write("\t\t\t\twidth : 120,\r\n");
      out.write("\t\t\t\tsortable : true,\r\n");
      out.write("\t\t\t\tformatter:function(value,rowData,rowIndex){\r\n");
      out.write("\t\t\t\t    return getFormatDateStr(rowData.sendTime , 'yyyy-MM-dd HH:mm');\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t] ]\r\n");
      out.write("\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t//$(\"[title]\").tooltips();\r\n");
      out.write("\t});\r\n");
      out.write("\r\n");
      out.write("    function deleteAdd(id){\r\n");
      out.write("\t\tvar para = {};\r\n");
      out.write("\t\tpara['sid'] = id;\r\n");
      out.write("    \tvar url = \"");
      out.print(contextPath );
      out.write("/teeAddressController/delAddress.action\";\r\n");
      out.write("\t\tvar jsonRs = tools.requestJsonRs(url,para);\r\n");
      out.write("\t\tif(jsonRs.rtState){\r\n");
      out.write("\t\t$.MsgBox.Alert_auto(\"删除成功！\");\r\n");
      out.write("\t\tdatagrid.datagrid('reload');\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t$.MsgBox.Alert_auto(jsonRs.rtMsg);\r\n");
      out.write("\t\t}\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    /**\r\n");
      out.write("    *查看公告\r\n");
      out.write("    */\r\n");
      out.write("    function loadDetail(seqId){\r\n");
      out.write("        var url = contextPath + \"/system/core/base/notify/person/readNotify.jsp?id=\"+seqId+\"&isLooked=0\";\r\n");
      out.write("        openFullWindow(url);\r\n");
      out.write("      \r\n");
      out.write("// \t     top.bsWindow(url,\"公告详情\",{width:\"900px\", height:\"400px\" , buttons:[{name:\"关闭\"}],submit:function(v,h){\r\n");
      out.write("// \t        if(v==\"关闭\"){\r\n");
      out.write("// \t        \t\r\n");
      out.write("// \t\t\t\treturn true;\r\n");
      out.write("// \t        }\r\n");
      out.write("// \t    }});\r\n");
      out.write("     }\r\n");
      out.write("    \r\n");
      out.write("    \r\n");
      out.write("    \r\n");
      out.write("    //全部已阅\r\n");
      out.write("    function readAll(){\r\n");
      out.write("    \tvar url = \"");
      out.print(contextPath );
      out.write("/teeNotifyController/readAll.action\";\r\n");
      out.write("\t\tvar json = tools.requestJsonRs(url,{groupId:groupId,seqIds:seqIds,typeId:typeId});\r\n");
      out.write("    \tif(json.rtState){\t\r\n");
      out.write("    \t\t$.MsgBox.Alert_auto(\"操作成功！\");\r\n");
      out.write("    \t\tdatagrid.datagrid('reload');\r\n");
      out.write("    \t}\r\n");
      out.write("    \t\r\n");
      out.write("    }\r\n");
      out.write("\t</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body style=\"margin:0px;overflow:auto;\" onload=\"\">\r\n");
      out.write("\r\n");
      out.write("  <div id=\"toolbar\" class = \"clearfix\">\r\n");
      out.write("  <form  method=\"post\" name=\"form1\" id=\"form1\" >\r\n");
      out.write("     <div class=\"left fl setHeight\">\r\n");
      out.write("\t    <input type=\"button\" value=\"全部已阅\" class=\"btn-win-white\" title=\"返回\" onClick=\"readAll();\">\r\n");
      out.write("     </div>\r\n");
      out.write("     </form> \r\n");
      out.write("  </div>\r\n");
      out.write(" \r\n");
      out.write("  <table id=\"datagrid\" fit=\"true\"></table>\r\n");
      out.write("\r\n");
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
