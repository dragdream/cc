package org.apache.jsp.system.core.system.applicationsystem;

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

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<title>应用系统维护</title>\r\n");
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
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("var datagrid;\r\n");
      out.write("function doInit(){\r\n");
      out.write("\tdatagrid = $('#datagrid').datagrid({\r\n");
      out.write("\t\turl:contextPath + \"/ApplicationSystemMaintainController/getList.action\",\r\n");
      out.write("\t\tview:window.EASYUI_DATAGRID_NONE_DATA_TIP,\r\n");
      out.write("\t\tpagination:true,\r\n");
      out.write("\t\tsingleSelect:false,\r\n");
      out.write("\t\ttoolbar:'#toolbar',//工具条对象\r\n");
      out.write("\t\tcheckbox:true,\r\n");
      out.write("\t\tborder:false,\r\n");
      out.write("\t\tidField:'sid',//主键列\r\n");
      out.write("\t\tstriped : true,\r\n");
      out.write("\t\tfitColumns:true,//列是否进行自动宽度适应\r\n");
      out.write("\t\tcolumns:[[\r\n");
      out.write("            {field:'sid',checkbox:true,width:100},\r\n");
      out.write("\t\t\t{field:'id',title:'ID',width:100,formatter:function(value,rowData,rowIndex){\r\n");
      out.write("                return rowData.sid;\r\n");
      out.write("\t\t\t}},\r\n");
      out.write("\t\t\t{field:'systemName',title:'应用系统名称',width:200},\r\n");
      out.write("\t\t\t{field:'url',title:'访问地址',width:200},\r\n");
      out.write("\t\t\t{field:'userNames',title:'人员权限',width:200},\r\n");
      out.write("\t\t\t{field:'deptNames',title:'部门权限',width:200},\r\n");
      out.write("\t\t\t{field:'roleNames',title:'角色权限',width:200},\r\n");
      out.write("\t\t\t{field:'opt_',title:'操作',width:200,formatter:function(value,rowData,rowIndex){\r\n");
      out.write("                var  opt= \"<a href=\\\"#\\\" onclick=\\\"addOrUpdate(\"+rowData.sid+\")\\\">编辑</a>&nbsp;&nbsp;\" \r\n");
      out.write("                        + \"<a href=\\\"#\\\" onclick=\\\"querySysMenu(\"+rowData.sid+\")\\\">菜单查询</a>\";\r\n");
      out.write("                return  opt;\r\n");
      out.write("\t\t\t}},\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t]]\r\n");
      out.write("\t})\r\n");
      out.write("\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//新建\r\n");
      out.write("function addOrUpdate(sid){\r\n");
      out.write("    var title=\"\";\r\n");
      out.write("    var mess=\"\";\r\n");
      out.write("    if(sid>0){\r\n");
      out.write("    \ttitle=\"编辑应用系统信息\";\r\n");
      out.write("    \tmess=\"编辑成功！\";\r\n");
      out.write("    }else{\r\n");
      out.write("    \ttitle=\"新增应用系统信息\";\r\n");
      out.write("    \tmess=\"新增成功！\";\r\n");
      out.write("    }\r\n");
      out.write("\tvar url=contextPath+\"/system/core/system/applicationsystem/addOrUpdate.jsp?sid=\"+sid;\r\n");
      out.write("\tbsWindow(url ,title,{width:\"600\",height:\"320\",buttons:\r\n");
      out.write("\t\t[\r\n");
      out.write("\t\t {name:\"保存\",classStyle:\"btn-alert-blue\"},\r\n");
      out.write("\t \t {name:\"关闭\",classStyle:\"btn-alert-gray\"}\r\n");
      out.write("\t\t ]\r\n");
      out.write("\t\t,submit:function(v,h){\r\n");
      out.write("\t\tvar cw = h[0].contentWindow;\r\n");
      out.write("\t\tif(v==\"保存\"){\r\n");
      out.write("\t\t    var json=cw.doSave();\r\n");
      out.write("\t\t    if(json.rtState){\r\n");
      out.write("\t\t    \t$.MsgBox.Alert_auto(mess,function(){\r\n");
      out.write("\t\t    \t\tdatagrid.datagrid('reload');\r\n");
      out.write("\t\t    \t});\r\n");
      out.write("\t\t    \treturn true;\r\n");
      out.write("\t\t    }\r\n");
      out.write("\t\t}else if(v==\"关闭\"){\r\n");
      out.write("\t\t\treturn true;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}}); \r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//删除   批量删除\r\n");
      out.write("function delByIds(){\r\n");
      out.write("\tvar selections = $('#datagrid').datagrid('getSelections');\r\n");
      out.write("\tvar ids = \"\";\r\n");
      out.write("\tfor(var i=0;i<selections.length;i++){\r\n");
      out.write("\t\tids+=selections[i].sid;\r\n");
      out.write("\t\tif(i!=selections.length-1){\r\n");
      out.write("\t\t\tids+=\",\";\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tif(ids.length==0){\r\n");
      out.write("\t\t$.MsgBox.Alert_auto(\"未选中任何记录！\");\r\n");
      out.write("\t\treturn ;\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\t$.MsgBox.Confirm (\"提示\", \"是否确认删除选中的系统信息？\", function(){\r\n");
      out.write("\t\t\tvar url=contextPath+\"/ApplicationSystemMaintainController/delByIds.action\";\r\n");
      out.write("\t\t\tvar para = {ids:ids};\r\n");
      out.write("\t\t\tvar json = tools.requestJsonRs(url,para);\r\n");
      out.write("\t\t\tif(json.rtState){\t\t\t\t\t\r\n");
      out.write("\t\t\t\t$.MsgBox.Alert_auto(\"删除成功！\",function(){\r\n");
      out.write("\t\t\t\t\tdatagrid.datagrid('reload');\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t});\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function querySysMenu(id){\r\n");
      out.write("\t window.location.href = \"");
      out.print(contextPath );
      out.write("/system/core/system/applicationsystem/querySysMenu.jsp?sysId=\" + id;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body style=\"padding-left: 10px;padding-right: 10px\" onload=\"doInit();\">\r\n");
      out.write("   <div id=\"toolbar\" class = \"topbar clearfix\">\r\n");
      out.write("\t\t<div class=\"fl\" style=\"position:static;\">\r\n");
      out.write("\t\t\t<img id=\"img1\" class = 'title_img' src=\"");
      out.print(contextPath );
      out.write("/system/core/system/applicationsystem/image/icon_yyxtwh.png\">\r\n");
      out.write("\t\t\t<span class=\"title\">应用系统维护</span>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class = \"right fr clearfix\">\r\n");
      out.write("\t\t    <input type=\"button\" class=\"btn-win-white\" onclick=\"addOrUpdate();\" value=\"新建\"/>\r\n");
      out.write("\t\t\t<input type=\"button\" class=\"btn-del-red fl\" onclick=\"delByIds()\" value=\"删除\"/>\r\n");
      out.write("\t    </div>\r\n");
      out.write("   </div>\r\n");
      out.write("   <table id=\"datagrid\" fit=\"true\"></table> \r\n");
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
