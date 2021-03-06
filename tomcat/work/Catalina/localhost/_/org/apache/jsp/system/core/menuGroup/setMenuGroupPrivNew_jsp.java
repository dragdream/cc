package org.apache.jsp.system.core.menuGroup;

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

public final class setMenuGroupPrivNew_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(request.getContextPath());
      out.write("/common/font-awesome/4.7.0/css/font-awesome.min.css\">\r\n");
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
      out.write("\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("<style>\r\n");
      out.write("body{\r\n");
      out.write("\tfont-size:12px;\r\n");
      out.write("}\r\n");
      out.write(".c{\r\n");
      out.write("\twidth:150px;\r\n");
      out.write("\tfloat:left;\r\n");
      out.write("\tmargin-left:5px;\r\n");
      out.write("\tmargin-right:5px;\r\n");
      out.write("\tpadding:5px;\r\n");
      out.write("}\r\n");
      out.write(".cc{\r\n");
      out.write("\tmargin-left:10px;\r\n");
      out.write("\tpadding:2px;\r\n");
      out.write("}\r\n");
      out.write("td{\r\n");
      out.write("\tborder:1px solid #66ccff;\r\n");
      out.write("\tbackground:#f0f0f0;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".zTreeBackground{\r\n");
      out.write("\twidth:225px;\r\n");
      out.write("\theight:462px;\r\n");
      out.write("\tfloat:left;\r\n");
      out.write("\tmargin-left:5px;\r\n");
      out.write("\tmargin-right:5px;\r\n");
      out.write("    margin-bottom: 10px;\t\r\n");
      out.write("\tpadding:5px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("ul.ztree {\r\n");
      out.write("\tmargin-top: 10px 0 0 0;\r\n");
      out.write("\tborder: 1px solid #617775;\r\n");
      out.write("\tbackground: #f0f6e4;\r\n");
      out.write("\twidth:220px;\r\n");
      out.write("\theight:460px;\r\n");
      out.write("\toverflow-y:scroll;\r\n");
      out.write("\toverflow-x:auto;\r\n");
      out.write("    padding: 5px;\r\n");
      out.write("    color: #333;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("/* 字体图标 */\r\n");
      out.write(".ztree li a span.button {font-family: FontAwesome;background-image: none;margin: 0 3px 0;line-height: 16px;}\r\n");
      out.write(".ztree li span.sys_ico_open:before, .ztree li span.sys_ico_close:before, .ztree li span.sys_ico_docu:before {content: \"\\f108\";font-size:120%;}\r\n");
      out.write(".ztree li span.fld_ico_open:before, .ztree li span.fld_ico_close:before, .ztree li span.fld_ico_docu:before {content: \"\\f114\";font-size:120%;}\r\n");
      out.write(".ztree li span.pag_ico_open:before, .ztree li span.pag_ico_close:before, .ztree li span.pag_ico_docu:before {content: \"\\f1c9\";font-size:120%;}\r\n");
      out.write(".ztree li span.btn_ico_open:before, .ztree li span.btn_ico_close:before, .ztree li span.btn_ico_docu:before {content: \"\\f0a7\";font-size:120%;}\r\n");
      out.write("\r\n");
      out.write("</style>\r\n");
      out.write("<script>\r\n");
      out.write("var treeIds = new Array();\r\n");
      out.write("var uuid = \"");
      out.print(request.getParameter("uuid") == null ? "" : request.getParameter("uuid"));
      out.write("\";\r\n");
      out.write("function doInit(){\r\n");
      out.write("\t$.MsgBox.Loading();\r\n");
      out.write("\tvar width = 0;\r\n");
      out.write("\t\r\n");
      out.write("\tvar url = contextPath +  \"/teeMenuGroup/getSysMenuBtnPriv.action\";\r\n");
      out.write("\tvar para = {groupId:uuid};\r\n");
      out.write("\tvar jsonRs = tools.requestJsonRs(url,para);\r\n");
      out.write("\t\r\n");
      out.write("\tvar setting = {\r\n");
      out.write("\t\t\tcheck: {\r\n");
      out.write("\t\t\t\tenable: true\r\n");
      out.write("\t\t\t},\t\t\t\r\n");
      out.write("\t\t\tdata: {\r\n");
      out.write("\t\t\t\tsimpleData: {\r\n");
      out.write("\t\t\t\t\tenable: true\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t};\r\n");
      out.write("\t\r\n");
      out.write("\tvar zNodesList = jsonRs.rtData;\r\n");
      out.write("\tfor(var i=0;i<zNodesList.length;i++){\r\n");
      out.write("\t\ttreeIds[i] = \"tree\" + i;\r\n");
      out.write("\t\t$(\"<div class='zTreeBackground'><ul id='tree\" + i + \"' class='ztree'></ul></div>\").appendTo($(\"#mainDiv\"));\r\n");
      out.write("\t\t$.fn.zTree.init($(\"#tree\" + i), setting, zNodesList[i]);\r\n");
      out.write("\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t$.MsgBox.CloseLoading();\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * 保存\r\n");
      out.write(" */\r\n");
      out.write("function doSubmit(){\r\n");
      out.write("\t\r\n");
      out.write("\tvar checkedIdArr = new Array();\r\n");
      out.write("\t\r\n");
      out.write("\tfor(var i=0; i< treeIds.length;i++){\r\n");
      out.write("\t\tvar treeId = treeIds[i];\r\n");
      out.write("\t\tvar zTreeObj = $.fn.zTree.getZTreeObj(treeId);  \r\n");
      out.write("\t\tvar checkedNodes = zTreeObj.getCheckedNodes();\r\n");
      out.write("\t\tfor(var j=0 ; j < checkedNodes.length ; j++){\r\n");
      out.write("\t\t\tcheckedIdArr.push(checkedNodes[j].id);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\tvar checkedIds = checkedIdArr.toString();\r\n");
      out.write("\t\r\n");
      out.write("\tvar url = contextPath+\"/teeMenuGroup/saveSysMenuBtnPriv.action\";\r\n");
      out.write("\tvar jsonObj = tools.requestJsonRs(url,{groupId:uuid,checkedIds:checkedIdArr.join(\",\")});\r\n");
      out.write("\tif(jsonObj.rtState){\r\n");
      out.write("\t\tlocation='manageGroup.jsp';\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\t$.MsgBox.Alert_auto(jsonObj.rtMsg);\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"doInit()\" style=\"overflow:hidden\">\r\n");
      out.write("<div id=\"toolbar\" class=\"topbar clearfix\" style=\"position:absolute;top:0px;left:0px;right:0px;height:20px\">\r\n");
      out.write("   <div class=\"fl\">\r\n");
      out.write("   \t&nbsp;&nbsp;<button type=\"button\" class=\"btn-win-white\"  onclick=\"doSubmit();\">保存</button>&nbsp;&nbsp;<button type=\"button\" class=\"btn-win-white\" onclick=\"location='manageGroup.jsp'\">返回</button>\r\n");
      out.write("   \t&nbsp;&nbsp;&nbsp;&nbsp;\r\n");
      out.write("   </div>\r\n");
      out.write("</div>\r\n");
      out.write("<div id=\"mainDiv\" style=\"position:absolute;top:60px;left:0px;right:0px;bottom:0px;overflow:auto\">\r\n");
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
