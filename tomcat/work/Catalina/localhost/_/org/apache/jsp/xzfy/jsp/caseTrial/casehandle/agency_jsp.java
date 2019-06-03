package org.apache.jsp.xzfy.jsp.caseTrial.casehandle;

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
import com.tianee.webframe.util.servlet.TeeCookieUtils;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.global.TeeSysProps;

public final class agency_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(3);
    _jspx_dependants.add("/header/header.jsp");
    _jspx_dependants.add("/header/easyui2.0.jsp");
    _jspx_dependants.add("/header/upload.jsp");
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
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(request.getContextPath());
      out.write("/common/swfupload/css/default.css\" />\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/common/js/jquery.form.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/common/swfupload/swfupload.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/common/swfupload/swfupload.queue.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/common/swfupload/fileprogress.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/common/swfupload/handlers.js?v=1\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/common/js/upload.js?v=4\"></script>\r\n");
      out.write("\r\n");
      out.write("<script>\r\n");
      out.write("var UPLOAD_ATTACH_LIMIT_GLOBAL = \"");
      out.print(TeeSysProps.getString("UPLOAD_ATTACH_LIMIT"));
      out.write("\";\r\n");
      out.write("var GLOBAL_ATTACH_TYPE=\"");
      out.print(TeeStringUtil.getString(TeeSysProps.getString("GLOBAL_ATTACH_TYPE")) );
      out.write("\";\r\n");
      out.write("</script>");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/common/My97DatePicker/WdatePicker.js\"></script>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/xzfy/css/common/supervise.css\" />\r\n");
      out.write("<!-- 中腾按钮框架 -->\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/common/zt_webframe/css/package1.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/xzfy/css/accept.css\" />\r\n");
      out.write("\r\n");
      out.write("<title>审理待办</title>\r\n");
      out.write("<style> \r\n");
      out.write("\ttr:nth-child(even){\r\n");
      out.write("\t\t background-color:#F0F0F0;\r\n");
      out.write("\t}\r\n");
      out.write("</style>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body style=\"height:100%\" onload=\"doInit(0);\">\r\n");
      out.write("\t\t<div id=\"toolbar\" class = \" clearfix\" style=\"margin-top: 5px\">\r\n");
      out.write("\t\t\t\t<div class=\"fl\" style=\"position:static;margin-top: 6px;\">\r\n");
      out.write("\t\t\t\t\t<span class='iconfont icon-falv' style=\"font-size:20px;\"></span>\r\n");
      out.write("\t\t\t\t\t<span class=\"title\" id=\"title\"></span>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t    <div class = \"right fr clearfix\" id=\"register\">\r\n");
      out.write("\t    \t<input type=\"button\" class=\"btn-win-white\" onclick=\"register();\" value=\"登记\"/>&nbsp;\r\n");
      out.write("\t        <input type=\"button\" class=\"btn-win-white\" onclick=\"selectNext()\" value=\"选择受理人\"/>&nbsp;\r\n");
      out.write("\t    </div>\r\n");
      out.write("\t    \r\n");
      out.write("\t    <span class=\"basic_border_grey\" style=\"margin-top: 10px\"></span>\r\n");
      out.write("\t    \r\n");
      out.write("\t    <div class=\"setHeight\">\r\n");
      out.write("\t        <form id=\"form1\" style=\"\">\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" id=\"orgId\" value=\"\" >\r\n");
      out.write("\t\t\t\t<div class='form-container'>\r\n");
      out.write("\t\t\t\t<div calss='form-row-one' style=\"text-align:right\">\r\n");
      out.write("\t\t\t\t\t\t<span class='form-item'>\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class='form-label'>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t 案件编号：\r\n");
      out.write("\t\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input class=\"BigInput\" type=\"text\" name=\"caseNum\" id=\"caseNum\" onkeyup=\"this.value=this.value.replace(/[^\\u4e00-\\u9fa5a-zA-Z0-9\\w]/g,'')\"\t\t\t\t\t\t\t/>\r\n");
      out.write("\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class='form-item'>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<span class='form-label'>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t申请人：\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input class=\"BigInput\" type=\"text\" name=\"caseNum\" id=\"caseNum\" onkeyup=\"this.value=this.value.replace(/[^\\u4e00-\\u9fa5a-zA-Z0-9\\w]/g,'')\"\t\t\t\t\t\t\t/>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</span>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<span class='form-item'>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<span class='form-label'>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t 被申请人：\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<input class=\"BigInput\" type=\"text\" name=\"respName\" id=\"respName\" onkeyup=\"this.value=this.value.replace(/[^\\u4e00-\\u9fa5a-zA-Z0-9\\w]/g,'')\"/>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div calss='form-row-two' style=\"text-align:right\">\r\n");
      out.write("\t\t\t\t\t<span class='form-item'>\r\n");
      out.write("\t\t\t\t\t\t\t<span class='form-label'>\r\n");
      out.write("\t\t\t\t\t\t\t\t申请日期：\r\n");
      out.write("\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t<span style=\"width:70%\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"text\" name=\"beginTime\" id=\"beginTime\" readonly onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\" class=\"date-input\"/>&nbsp;至\r\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"text\" name=\"endTime\" id=\"endTime\" readonly onclick=\"WdatePicker({minDate:'#F{$dp.$D(\\'beginTime\\',{d:0});}',dateFmt:'yyyy-MM-dd'})\"\r\n");
      out.write("\t\t\t\t\t\t\t\tclass=\"date-input\"  />\r\n");
      out.write("\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t<span class='form-item'>\r\n");
      out.write("\t\t\t\t\t\t\t<span class='form-label'>\r\n");
      out.write("\t\t\t\t\t\t\t\t申请方式：\r\n");
      out.write("\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t<select class=\"BigInput\" id=\"postType\" name=\"postType\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option>---请选择---</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t<span class='form-item'>\r\n");
      out.write("\t\t\t\t\t\t\t<span class='form-label'>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t案件状态：\r\n");
      out.write("\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t<select class=\"BigInput\" id=\"caseStatus\" name=\"caseStatus\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option>---请选择---</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t\t</span>\r\n");
      out.write("\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class='stage-title'>\r\n");
      out.write("\t\t\t\t\t<span class='establish-stage active-estab' onclick=\"change(this,'0')\">我的待办</span>\r\n");
      out.write("\t\t\t\t\t<span class='establish-stage ' onclick=\"change(this,'1')\">我的已办</span>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</form>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("   \t</div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- 这是列表页 -->\t\r\n");
      out.write("\t<table class=\"TableBlock\" style=\"height:100%\" id=\"datagrid\"></table>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath );
      out.write("/system/core/base/meeting/js/meeting.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/xzfy/js/jquery.tips.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath );
      out.write("/xzfy/js/caseTrial/common/casetriallist_agency.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("function change(that,type){\r\n");
      out.write("\t$(that).siblings().removeClass(\"active-estab\");\r\n");
      out.write("\t$(that).removeClass(\"active-estab\").addClass(\"active-estab\");\r\n");
      out.write("\tdoInit(type);\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
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
