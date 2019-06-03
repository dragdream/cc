package org.apache.jsp.xzfy.jsp.caseRegister;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.tianee.webframe.util.global.TeeSysProps;
import java.text.*;
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
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.global.TeeSysProps;

public final class caseRegister_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/header/header2.0.jsp");
    _jspx_dependants.add("/header/easyui2.0.jsp");
    _jspx_dependants.add("/header/validator2.0.jsp");
    _jspx_dependants.add("/header/upload.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
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

      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"UTF-8\">\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\r\n");
      out.write("<title>登记页面</title>\r\n");

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

      out.write("<!-- zt_webframe框架引入 css样式 -->\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(request.getContextPath());
      out.write("/common/jquery-easyui-1.6.11/themes/metro/easyui.css\">\r\n");
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
      out.write("<link rel=\"stylesheet\" href=\"../../css/common.css\">\r\n");
      out.write("<script src=\"../../js/base/jquery-1.9.1.min.js\"></script>\r\n");
      out.write("<script src=\"../../js/base/juicer-min.js\"></script>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../../css/easyui.css\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../../css/icon.css\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../../css/demo.css\">\r\n");
      out.write("<script type=\"text/javascript\" src=\"../../js/base/jquery.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"../../js/base/jquery.easyui.min.js\"></script>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/caseRegister.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../css/common.css\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/init1.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/icon.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/easyui.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/demo.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/case.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/iconfont/iconfont.css\" />\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"/common/My97DatePicker/WdatePicker.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<style>\r\n");
      out.write("#d1 {\r\n");
      out.write("\tmargin-bottom: 40px;\r\n");
      out.write("\tpadding: 10px;\r\n");
      out.write("\tbackground-color: #fff;\r\n");
      out.write("\tmargin-top: 10px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".tab {\r\n");
      out.write("\twidth: 100%;\r\n");
      out.write("\theight: 40px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".tab ul li {\r\n");
      out.write("\twidth: 25%;\r\n");
      out.write("\tline-height: 40px;\r\n");
      out.write("\ttext-align: center;\r\n");
      out.write("\tfloat: left;\r\n");
      out.write("\tcursor: pointer;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("#rp_btn {\r\n");
      out.write("\tposition: fixed;\r\n");
      out.write("\tbottom: 0px;\r\n");
      out.write("\tz-index: 999;\r\n");
      out.write("\tbackground: white;\r\n");
      out.write("\twidth: 100%;\r\n");
      out.write("\theight: 40px;\r\n");
      out.write("\ttext-align: right;\r\n");
      out.write("\tpadding: 6px 30px 6px;\r\n");
      out.write("\tborder-top: 1px #e6e9ed solid;\r\n");
      out.write("\t/* margin-right: 10px; */\r\n");
      out.write("\t/* margin: 0 auto; */\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("#rp_btn input {\r\n");
      out.write("\twidth: 80px;\r\n");
      out.write("\theight: 28px;\r\n");
      out.write("\tmargin-left: 10px;\r\n");
      out.write("\tborder-radius: 5px;\r\n");
      out.write("\tfont-size: 14px;\r\n");
      out.write("\tcolor: #fff;\r\n");
      out.write("\t/* background-color: #3379b7; */\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".rp-btn-left {\r\n");
      out.write("\tfloat: left;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".td-col4-title {\r\n");
      out.write("\ttext-align: right;\r\n");
      out.write("\twidth: 17%;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".td-col4-content {\r\n");
      out.write("\twidth: 33%;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".div-col4-leftext {\r\n");
      out.write("\ttext-align: left;\r\n");
      out.write("\ttext-indent: 9px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".leftextlable {\r\n");
      out.write("\ttext-align: left;\r\n");
      out.write("\ttext-indent: 9px;\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body onload=\"doInit();\"style=\"overflow: auto; padding-left: 10px; padding-right: 10px;\">\r\n");
      out.write("\t<input type=\"hidden\" id=\"caseId\" name=\"caseId\" value=\"\">\r\n");
      out.write("\t<div class=\"tab\">\r\n");
      out.write("\t\t<ul>\r\n");
      out.write("\t\t\t<li onclick=\"showpage(0);\"><span\r\n");
      out.write("\t\t\t\tclass=\"case-tab case-tab-active\">来件\\接待信息</span></li>\r\n");
      out.write("\t\t\t<li onclick=\"showpage(1);\"><span class=\"case-tab\">当事人信息</span></li>\r\n");
      out.write("\t\t\t<li onclick=\"showpage(2);\"><span class=\"case-tab\">复议事项</span></li>\r\n");
      out.write("\t\t\t<li onclick=\"showpage(3);\"><span class=\"case-tab\">案件材料</span></li>\r\n");
      out.write("\t\t</ul>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\t<!-- 来件接待信息  -->\r\n");
      out.write("\t<!-- 当面接待开始 -->\r\n");
      out.write("\t<div class=\"content\" id=\"d1\">\r\n");
      out.write("\t\t<button class=\"getcase\" onclick=\"caseExtraction();\">案件提取</button>\r\n");
      out.write("\t\t<div class=\"fyform\">\r\n");
      out.write("\t\t\t<div class=\"div-col\">\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-title\">\r\n");
      out.write("\t\t\t\t\t<label class=\"inputlable apply-lable\">申请方式：</label>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-content\">\r\n");
      out.write("\t\t\t\t\t<select id=\"applyType\" class=\"fyselect apply-select\">\r\n");
      out.write("\t\t\t\t\t\t<option value=\"\">请选择</option>\r\n");
      out.write("\t\t\t\t\t\t<option value=\"2\">当面接待</option>\r\n");
      out.write("\t\t\t\t\t\t<option value=\"1\">书面来件</option>\r\n");
      out.write("\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t<div class=\"fyform select-one\">\r\n");
      out.write("\t\t\t<div class=\"div-col\">\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-title\">\r\n");
      out.write("\t\t\t\t\t<label class=\"inputlable\">接待地点：</label>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-content\">\r\n");
      out.write("\t\t\t\t\t<input id=\"place\" name=\"place\" value=\"\" type=\"text\" class=\"fyinput\"\r\n");
      out.write("\t\t\t\t\t\tid=\"\" placeholder=\"\" />\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-title\">\r\n");
      out.write("\t\t\t\t\t<label class=\"inputlable\">接待日期：</label>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-content\">\r\n");
      out.write("\t\t\t\t<input type=\"text\" id=\"receptionDate\" name=\"receptionDate\" value=\"\" readonly\r\n");
      out.write("\t\t\t\t\t\t\tonclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\"\r\n");
      out.write("\t\t\t\t\t\t\tclass=\"Wdate BigInput\" style=\"width: 100%; height: 100%\" />\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"div-col\">\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-title\">\r\n");
      out.write("\t\t\t\t\t<label class=\"inputlable\">接待人：</label>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-content\">\r\n");
      out.write("\t\t\t\t\t<input id=\"dealMan1Id\" name=\"dealMan1Id\" value=\"\" type=\"text\"\r\n");
      out.write("\t\t\t\t\t\tclass=\"fyinput\" placeholder=\"\" />\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-title\">\r\n");
      out.write("\t\t\t\t\t<label class=\"inputlable\">第二接待人：</label>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-content\">\r\n");
      out.write("\t\t\t\t\t<input id=\"dealMan2Id\" name=\"dealMan2Id\" value=\"\" type=\"text\"\r\n");
      out.write("\t\t\t\t\t\tclass=\"fyinput\" placeholder=\"\" />\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"div-col\">\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-title\">\r\n");
      out.write("\t\t\t\t\t<label class=\"inputlable\">处理结果：</label>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-content\">\r\n");
      out.write("\t\t\t\t\t<select id=\"dealResultCode\" name=\"dealResultCode\" value=\"\"\r\n");
      out.write("\t\t\t\t\t\tclass=\"fyselect\">\r\n");
      out.write("\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-title\">\r\n");
      out.write("\t\t\t\t\t<label class=\"inputlable  control-label\">复议请求类型：</label>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-content\">\r\n");
      out.write("\t\t\t\t\t<select id=\"recertionTypeCode\" name=\"recertionTypeCode\" value=\"\"\r\n");
      out.write("\t\t\t\t\t\tclass=\"fyselect\">\r\n");
      out.write("\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t<div class=\"recpinfo\">\r\n");
      out.write("\t\t\t\t<p class=\"case-head-title\">被接待人信息</p>\r\n");
      out.write("\t\t\t\t<div class=\"edit-table-div\">\r\n");
      out.write("\t\t\t\t\t<table id=\"recp-table\" class=\"edit-table\"></table>\r\n");
      out.write("\t\t\t\t\t<p class=\"edit-table-add\" onclick=\"recp_insertRow();\">添加</p>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t<div class=\"div-col\">\r\n");
      out.write("\t\t\t\t<div class=\"div-col2 div-col2-title\">\r\n");
      out.write("\t\t\t\t\t<label class=\"inputlable leftextlable\">接待情况：</label>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col2 div-col2-content\">\r\n");
      out.write("\t\t\t\t\t<textarea id=\"receptionDetail\" name=\"receptionDetail\" value=\"\"\r\n");
      out.write("\t\t\t\t\t\tclass=\"rowinput\" rows=\"3\"></textarea>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t<div class=\"other-one\">\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-longtitle div-col4-leftext\">\r\n");
      out.write("\t\t\t\t\t<label class=\"leftextlable\">其他复议机关\\法院受理同一复议申请：</label>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<span><td><input type=\"radio\"\r\n");
      out.write("\t\t\t\t\t\tname=\"isReconsiderTogether\" value=\"1\"\r\n");
      out.write("\t\t\t\t\t\t");
      if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
        return;
      out.write(" />是</span>\r\n");
      out.write("\t\t\t\t<span><td><input type=\"radio\"\r\n");
      out.write("\t\t\t\t\t\tname=\"isReconsiderTogether\" value=\"0\"\r\n");
      out.write("\t\t\t\t\t\t");
      if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
        return;
      out.write(" />\r\n");
      out.write("\t\t\t\t\t\t否</span>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-longtitle\">\r\n");
      out.write("\t\t\t\t\t<label>复议机关名称：</label>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<select id=\"reconsiderOrganCode\" value=\"\"\r\n");
      out.write("\t\t\t\t\tclass=\"reconsiderOrganCode\">\r\n");
      out.write("\t\t\t\t</select>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"other-two\">\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-longtitle div-col4-leftext\"\r\n");
      out.write("\t\t\t\t\t<label class=\"leftextlable\">接收材料：</label></div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<span><td><input type=\"radio\" onclick=\"showTable();\"\r\n");
      out.write("\t\t\t\t\t\tname=\"isReceiveMaterial\" value=\"1\"\r\n");
      out.write("\t\t\t\t\t\t");
      if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
        return;
      out.write(" />是</td></span>\r\n");
      out.write("\t\t\t\t<span><td><input type=\"radio\" onclick=\"hideTable();\"\r\n");
      out.write("\t\t\t\t\t\tname=\"isReceiveMaterial\" value=\"0\"\r\n");
      out.write("\t\t\t\t\t\t");
      if (_jspx_meth_c_005fif_005f3(_jspx_page_context))
        return;
      out.write(" />\r\n");
      out.write("\t\t\t\t\t\t否</td></span>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"material-div\">\r\n");
      out.write("\t\t\t\t<div class=\"edit-table-div \">\r\n");
      out.write("\t\t\t\t\t<table id=\"material-table\" class=\"edit-table\"></table>\r\n");
      out.write("\t\t\t\t\t<p class=\"edit-table-add\" onclick=\"material_insertRow();\">添加</p>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<!-- 当面接待结束 -->\r\n");
      out.write("\r\n");
      out.write("\t\t<!-- 书面来件 -->\r\n");
      out.write("\t\t<div class=\"fyform select-two\">\r\n");
      out.write("\t\t\t<div class=\"div-col\">\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-title\">\r\n");
      out.write("\t\t\t\t\t<label class=\"inputlable\">来信人姓名：</label>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-content\">\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" class=\"fyinput\" id=\"senderName\" name=\"senderName\"\r\n");
      out.write("\t\t\t\t\t\tvalue=\"\" placeholder=\"\" />\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-title\">\r\n");
      out.write("\t\t\t\t\t<label class=\"inputlable\">来信人电话：</label>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-content\">\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" class=\"fyinput\" id=\"senderPhone\" name=\"senderPhone\"\r\n");
      out.write("\t\t\t\t\t\tvalue=\"\" placeholder=\"\" />\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t<div class=\"div-col\">\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-title\">\r\n");
      out.write("\t\t\t\t\t<label class=\"inputlable\">申请日期：</label>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-content\">\r\n");
      out.write("\t\t\t\t\t<div class=\"fyinput\">\r\n");
      out.write("\t\t\t\t\t\t<input type=\"text\" id=\"receiveDate\" name=\"receiveDate\" value=\"\" readonly\r\n");
      out.write("\t\t\t\t\t\t\tonclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\"\r\n");
      out.write("\t\t\t\t\t\t\tclass=\"Wdate BigInput\" style=\"width: 100%; height: 100%\" />\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-title\">\r\n");
      out.write("\t\t\t\t\t<label class=\"inputlable\">收件类型：</label>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-content\">\r\n");
      out.write("\t\t\t\t\t<select id=\"letterTypeCode\" name=\"letterTypeCode\" class=\"fyselect\">\r\n");
      out.write("\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t<div class=\"div-col\">\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-title\">\r\n");
      out.write("\t\t\t\t\t<label class=\"inputlable\">来信人通信地址：</label>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-content\">\r\n");
      out.write("\t\t\t\t\t<input id=\"senderAddress\" name=\"senderAddress\" value=\"\" type=\"text\"\r\n");
      out.write("\t\t\t\t\t\tclass=\"fyinput\" placeholder=\"\" />\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-title\">\r\n");
      out.write("\t\t\t\t\t<label class=\"inputlable\">来信人邮编：</label>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-content\">\r\n");
      out.write("\t\t\t\t\t<input id=\"senderPostCode\" name=\"senderPostCode\" value=\"\" type=\"text\"\r\n");
      out.write("\t\t\t\t\t\tclass=\"fyinput\" placeholder=\"\" />\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t<div class=\"div-col\">\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-title\">\r\n");
      out.write("\t\t\t\t\t<label class=\"inputlable\">来文编号：</label>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-content\">\r\n");
      out.write("\t\t\t\t\t<input id=\"letterNum\" name=\"letterNum\" value=\"\" type=\"text\" class=\"fyinput\"\r\n");
      out.write("\t\t\t\t\t\tplaceholder=\"\" />\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-title\"></div>\r\n");
      out.write("\t\t\t\t<div class=\"div-col4 div-col4-content\"></div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<!-- 来件接待信息结束 -->\r\n");
      out.write("\r\n");
      out.write("\t<!-- 当事人信息  -->\r\n");
      out.write("\t<div class=\"content\" id=\"d1\">\r\n");
      out.write("\t\t<div class=\"partyDiv\">\r\n");
      out.write("\t\t\t<p class=\"case-head-title\">申请人</p>\r\n");
      out.write("\t\t\t<div class=\"party-body\">\r\n");
      out.write("\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t<label>申请人类别：</label> <span><input type=\"radio\"\r\n");
      out.write("\t\t\t\t\t\tname=\"optradio\" /> 公民</span> <span><input type=\"radio\"\r\n");
      out.write("\t\t\t\t\t\tname=\"optradio\" /> 法人或其他组织</span> <span><input type=\"radio\"\r\n");
      out.write("\t\t\t\t\t\tname=\"optradio\" /> 个体工商户</span>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<div class=\"edit-table-div applicant-div\">\r\n");
      out.write("\t\t\t\t\t<table id=\"applicant-table\" class=\"edit-table\"></table>\r\n");
      out.write("\t\t\t\t\t<p class=\"edit-table-add\" onclick=\"applicant_insertRow();\">添加</p>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<hr />\r\n");
      out.write("\t\t\t\t<!-- 用button控制添加的表格 -->\r\n");
      out.write("\t\t\t\t<button class=\"fy-btn blue-btn party-btn\"\r\n");
      out.write("\t\t\t\t\tonclick=\"add_otherApplicant();\">添加其他申请人</button>\r\n");
      out.write("\t\t\t\t<div class=\"edit-table-div applicant-otherApplicant-div\">\r\n");
      out.write("\t\t\t\t\t<table id=\"applicant-otherApplicant-table\" class=\"edit-table\"></table>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<button class=\"fy-btn blue-btn party-btn\" onclick=\"add_agent();\">添加申请人代理人</button>\r\n");
      out.write("\t\t\t\t<div class=\"edit-table-div applicant-agent-div\">\r\n");
      out.write("\t\t\t\t\t<table id=\"applicant-agent-table\" class=\"edit-table\"></table>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t<p class=\"case-head-title\">被申请人</p>\r\n");
      out.write("\t\t\t<div class=\"party-body\">\r\n");
      out.write("\t\t\t\t<div class=\"edit-table-div applicant-div\">\r\n");
      out.write("\t\t\t\t\t<table id=\"respondent-table\" class=\"edit-table\"></table>\r\n");
      out.write("\t\t\t\t\t<p class=\"edit-table-add\" onclick=\"respondent_insertRow();\">添加</p>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<hr />\r\n");
      out.write("\t\t\t\t<!--  用button控制添加的表格 -->\r\n");
      out.write("\t\t\t\t<button class=\"fy-btn blue-btn party-btn\"\r\n");
      out.write("\t\t\t\t\tonclick=\"add_respondent_agent();\">添加被申请人代理人</button>\r\n");
      out.write("\t\t\t\t<div class=\"edit-table-div respondent-agent-div\">\r\n");
      out.write("\t\t\t\t\t<table id=\"respondent-agent-table\" class=\"edit-table\"></table>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t<p class=\"case-head-title\">第三人</p>\r\n");
      out.write("\t\t\t<div class=\"party-body\">\r\n");
      out.write("\t\t\t\t<div class=\"edit-table-div applicant-div\">\r\n");
      out.write("\t\t\t\t\t<table id=\"thirdParty-table\" class=\"edit-table\"></table>\r\n");
      out.write("\t\t\t\t\t<p class=\"edit-table-add\" onclick=\"thirdParty_insertRow();\">添加</p>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<hr />\r\n");
      out.write("\t\t\t\t<!-- 用button控制添加的表格 -->\r\n");
      out.write("\t\t\t\t<button class=\"fy-btn blue-btn party-btn\"\r\n");
      out.write("\t\t\t\t\tonclick=\"add_thirdParty_agent();\">添加第三人代理人</button>\r\n");
      out.write("\t\t\t\t<div class=\"edit-table-div thirdParty-agent-div\">\r\n");
      out.write("\t\t\t\t\t<table id=\"thirdParty-agent-table\" class=\"edit-table\"></table>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\t<!-- 复议事项  -->\r\n");
      out.write("\t<div class=\"content\" id=\"d1\">\r\n");
      out.write("\t\t<div class=\"fyform\">\r\n");
      out.write("\t\t\t<table class=\"table-col4\">\r\n");
      out.write("\t\t\t\t<tr class=\"tr-col4\">\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-title\"><label class=\"inputlable\">案件编号：</label></td>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-content\">\r\n");
      out.write("\t\t\t\t\t\t<!-- 案件编号暂时改成输入框 --> \r\n");
      out.write("\t\t\t\t\t\t<input id=\"caseNum\" name=\"caseNum\" value=\"\" type=\"text\" class=\"fyinput\" placeholder=\"\" />\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-title\"><label class=\"inputlable\">申请日期：</label></td>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-content\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"fyinput\">\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"text\" id=\"applicationDate\" name=\"applicationDate\"\r\n");
      out.write("\t\t\t\t\t\t\t\tvalue=\"\" readonly onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\"\r\n");
      out.write("\t\t\t\t\t\t\t\tclass=\"Wdate BigInput\" style=\"width: 100%; height: 100%\" />\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<tr class=\"tr-col4\">\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-title\"><label class=\"inputlable\">申请事项：</label></td>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-content\"><select id=\"applicationItemCode\"\r\n");
      out.write("\t\t\t\t\t\tname=\"applicationItemCode\" value=\"\" class=\"fyselect\">\r\n");
      out.write("\t\t\t\t\t</select></td>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-title\"><label class=\"inputlable\">行政类别管理：</label></td>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-content\">\r\n");
      out.write("\t\t\t\t\t<select id=\"categoryCode\"\r\n");
      out.write("\t\t\t\t\t\tname=\"categoryCode\" value=\"\" class=\"fyselect\">\r\n");
      out.write("\t\t\t\t\t</select></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<tr class=\"tr-col4\">\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-title\"><label class=\"inputlable\">行政不作为：</label></td>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-content\"><input type=\"radio\"\r\n");
      out.write("\t\t\t\t\t\tname=\"isNonfeasance\" value=\"1\" onclick=\"showNotPoliticalDo(this)\" />是\r\n");
      out.write("\t\t\t\t\t\t<input type=\"radio\" name=\"isNonfeasance\" value=\"0\"\r\n");
      out.write("\t\t\t\t\t\tonclick=\"showPoliticalDo(this)\" />否</td>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-title notpoliticaldo\"><label\r\n");
      out.write("\t\t\t\t\t\tclass=\"inputlable\">不作为事项：</label></td>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-content notpoliticaldo\"><select\r\n");
      out.write("\t\t\t\t\t\tid=\"nonfeasanceItemCode\" name=\"nonfeasanceItemCode\" value=\"\"\r\n");
      out.write("\t\t\t\t\t\tclass=\"fyselect\">\r\n");
      out.write("\t\t\t\t\t</select></td>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-title politicaldo\"><label\r\n");
      out.write("\t\t\t\t\t\tclass=\"inputlable\">具体行政行为名称：</label></td>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-content politicaldo\">\r\n");
      out.write("\t\t\t\t\t<input id=\"specificAdministrativeName\" name=\"specificAdministrativeName\" value=\"\" type=\"text\" class=\"fyinput\" placeholder=\"\" /></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<!-- 行政不作为选项为是 -->\r\n");
      out.write("\t\t\t\t<tr class=\"tr-col4 notpoliticaldo\">\r\n");
      out.write("\t\t\t\t\t<td colspan=\"3\" class=\"td-col4-title\"><label\r\n");
      out.write("\t\t\t\t\t\tclass=\"inputlable\">申请人要求被申请人履行该项法定职责日期：</label></td>\r\n");
      out.write("\t\t\t\t\t<td colspan=\"1\" class=\"td-col4-content\"><input type=\"text\"\r\n");
      out.write("\t\t\t\t\t\tid=\"nonfeasanceDate\" name=\"nonfeasanceDate\" value=\"\" readonly\r\n");
      out.write("\t\t\t\t\t\tonclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\"\r\n");
      out.write("\t\t\t\t\t\tclass=\"Wdate BigInput\" style=\"width: 100%; height: 100%;\" /></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<!-- 行政不作为选项为否 -->\r\n");
      out.write("\t\t\t\t<tr class=\"tr-col4 politicaldo\">\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-title\"><label class=\"inputlable\">具体行政行为文号：</label></td>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-content\"><input type=\"text\" class=\"fyinput\"\r\n");
      out.write("\t\t\t\t\t\tid=\"specificAdministrativeNo\" name=\"specificAdministrativeNo\"\r\n");
      out.write("\t\t\t\t\t\tvalue=\"\" placeholder=\"\" /></td>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-title\"><label class=\"inputlable\">具体行政行为日期：</label></td>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-content\"><input type=\"text\"\r\n");
      out.write("\t\t\t\t\t\tid=\"applicationDate\" name=\"applicationDate\" value=\"\" readonly\r\n");
      out.write("\t\t\t\t\t\tonclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\"\r\n");
      out.write("\t\t\t\t\t\tclass=\"Wdate BigInput\" style=\"width: 100%; height: 100%\" /></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<tr class=\"tr-col4 politicaldo\">\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-title\"><label class=\"inputlable\">收到处罚决定书日期：</label></td>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-content\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"fyinput\">\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"text\" id=\"receivedPunishDate\"\r\n");
      out.write("\t\t\t\t\t\t\t\tname=\"receivedPunishDate\" value=\"\" readonly\r\n");
      out.write("\t\t\t\t\t\t\t\tonclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\"\r\n");
      out.write("\t\t\t\t\t\t\t\tclass=\"Wdate BigInput\" style=\"width: 100%; height: 100%\" />\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-title\"><label class=\"inputlable\">得知该具体行为途径：</label></td>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-content\"><input id=\"receivedSpecificWay\"\r\n");
      out.write("\t\t\t\t\t\tvalue=\"receivedSpecificWay\" type=\"text\" class=\"fyinput\" id=\"\"\r\n");
      out.write("\t\t\t\t\t\tplaceholder=\"\" /></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-title\"><label class=\"inputlable\">具体行政行为：</label></td>\r\n");
      out.write("\t\t\t\t\t<td colspan=\"3\"><textarea id=\"specificAdministrativeDetail\"\r\n");
      out.write("\t\t\t\t\t\t\tvalue=\"specificAdministrativeDetail\" class=\"rowinput\" rows=\"3\"></textarea></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-title\"><label class=\"inputlable\">行政复议请求：</label></td>\r\n");
      out.write("\t\t\t\t\t<td colspan=\"3\"><textarea id=\"requestForReconsideration\"\r\n");
      out.write("\t\t\t\t\t\t\tvalue=\"requestForReconsideration\" class=\"rowinput\" rows=\"3\"></textarea></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<!-- 按钮组 -->\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-title\"><label class=\"inputlable\">行政复议前置：</label></td>\r\n");
      out.write("\t\t\t\t\t<td><span class=\"radio-group\"> <input type=\"radio\"\r\n");
      out.write("\t\t\t\t\t\t\tname=\"isReview\" value=\"1\"\r\n");
      out.write("\t\t\t\t\t\t\t");
      if (_jspx_meth_c_005fif_005f4(_jspx_page_context))
        return;
      out.write(" /><label>是</label>\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"radio\" name=\"isReview\" value=\"0\"\r\n");
      out.write("\t\t\t\t\t\t\t");
      if (_jspx_meth_c_005fif_005f5(_jspx_page_context))
        return;
      out.write(" /><label>否</label>\r\n");
      out.write("\t\t\t\t\t</span></td>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-title\"><label class=\"inputlable\">申请举行听证会：</label></td>\r\n");
      out.write("\t\t\t\t\t<td><span class=\"radio-group\"> <input type=\"radio\"\r\n");
      out.write("\t\t\t\t\t\t\tname=\"isHoldHearing\" value=\"1\"\r\n");
      out.write("\t\t\t\t\t\t\t");
      if (_jspx_meth_c_005fif_005f6(_jspx_page_context))
        return;
      out.write(" /><label>是</label>\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"radio\" name=\"isHoldHearing\" value=\"0\"\r\n");
      out.write("\t\t\t\t\t\t\t");
      if (_jspx_meth_c_005fif_005f7(_jspx_page_context))
        return;
      out.write(" /><label>否</label>\r\n");
      out.write("\t\t\t\t\t</span></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-title\"><label class=\"inputlable\">申请赔偿：</label></td>\r\n");
      out.write("\t\t\t\t\t<td colspan=\"3\"><span class=\"radio-group\"> <input\r\n");
      out.write("\t\t\t\t\t\t\ttype=\"radio\" name=\"isCompensation\" onclick=\"showInput(this);\"\r\n");
      out.write("\t\t\t\t\t\t\tvalue=\"1\"\r\n");
      out.write("\t\t\t\t\t\t\t");
      if (_jspx_meth_c_005fif_005f8(_jspx_page_context))
        return;
      out.write(" /><label>是</label>\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"radio\" name=\"isCompensation\"\r\n");
      out.write("\t\t\t\t\t\t\tonclick=\"hideInput(this);\" value=\"0\"\r\n");
      out.write("\t\t\t\t\t\t\t");
      if (_jspx_meth_c_005fif_005f9(_jspx_page_context))
        return;
      out.write(" /><label>否</label>\r\n");
      out.write("\t\t\t\t\t</span> <span class=\"li-input\"> <label class=\"lilable\">赔偿请求类型：</label>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<select id=\"compensationTypeCode\" name=\"compensationTypeCode\"\r\n");
      out.write("\t\t\t\t\t\t\tvalue=\"\" class=\"fyselect\">\r\n");
      out.write("\t\t\t\t\t\t</select> <label class=\"lilable\">赔偿请求金额：</label> <input type=\"text\"\r\n");
      out.write("\t\t\t\t\t\t\tclass=\"form-control\" id=\"compensationAmount\"\r\n");
      out.write("\t\t\t\t\t\t\tname=\"compensationAmount\" placeholder=\"\"></span>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td class=\"td-col4-title\"><label class=\"inputlable\">申请附带对规范性文件的审查：</label></td>\r\n");
      out.write("\t\t\t\t\t<td colspan=\"3\"><span class=\"radio-group\"> <input\r\n");
      out.write("\t\t\t\t\t\t\ttype=\"radio\" name=\"isDocumentReview\" onclick=\"showInput(this);\"\r\n");
      out.write("\t\t\t\t\t\t\tvalue=\"1\"\r\n");
      out.write("\t\t\t\t\t\t\t");
      if (_jspx_meth_c_005fif_005f10(_jspx_page_context))
        return;
      out.write(" /><label>是</label>\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"radio\" name=\"isDocumentReview\"\r\n");
      out.write("\t\t\t\t\t\t\tonclick=\"hideInput(this);\" value=\"0\"\r\n");
      out.write("\t\t\t\t\t\t\t");
      if (_jspx_meth_c_005fif_005f11(_jspx_page_context))
        return;
      out.write(" /><label>否</label>\r\n");
      out.write("\t\t\t\t\t</span> <span class=\"li-input\"> <label class=\"lilable\">规范性文件名称：</label>\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"text\" class=\"form-control\" id=\"documentReviewName\"\r\n");
      out.write("\t\t\t\t\t\t\tname=\"documentReviewName\" placeholder=\"\"></span></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\t<!-- 案件材料  -->\r\n");
      out.write("\t<div class=\"content  materialDiv\" id=\"d1\"></div>\r\n");
      out.write("\r\n");
      out.write("\t<div id=\"rp_btn\" class=\"fr\">\r\n");
      out.write("\t\t<span class=\"rp-btn-left\"> <input id=\"record\"\r\n");
      out.write("\t\t\tclass=\"fy-btn yellow-btn\" type=\"button\" value=\"接待笔录\" title=\"接待笔录\"\r\n");
      out.write("\t\t\tonclick=\"\" /> <input id=\"receipt\" class=\"fy-btn yellow-btn\"\r\n");
      out.write("\t\t\ttype=\"button\" value=\"材料收据\" title=\"材料收据\" onclick=\"\" /> <input\r\n");
      out.write("\t\t\tid=\"sheet\" class=\"fy-btn yellow-btn\" type=\"button\" value=\"地址确认单\"\r\n");
      out.write("\t\t\ttitle=\"地址确认单\" onclick=\"\" />\r\n");
      out.write("\t\t</span> <input id=\"back\" class=\"fy-btn blue-btn\" type=\"button\" value=\"上一步\"\r\n");
      out.write("\t\t\ttitle=\"上一步\" onclick=\"back();\" /> <input id=\"save\"\r\n");
      out.write("\t\t\tclass=\"fy-btn green-btn\" type=\"button\" value=\"暂  存\" title=\"暂存\"\r\n");
      out.write("\t\t\tonclick=\"save();\" /> <input id=\"copy\" class=\"fy-btn yellow-btn\"\r\n");
      out.write("\t\t\ttype=\"button\" value=\"保存并复制\" title=\"保存并复制\" onclick=\"\" /> <input\r\n");
      out.write("\t\t\tid=\"forward\" class=\"fy-btn blue-btn\" type=\"button\" value=\"下一步\"\r\n");
      out.write("\t\t\ttitle=\"下一步\" onclick=\"forward();\" />\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<script src=\"/xzfy/js/common/common.js\"></script>\r\n");
      out.write("\t<script src=\"/xzfy/js/caseRegister/registerCommon.js\"></script>\r\n");
      out.write("\t<script src=\"/xzfy/js/caseRegister/caseRegister.js\"></script>\r\n");
      out.write("\t<script src=\"/xzfy/js/caseRegister/letterrReception.js\"></script>\r\n");
      out.write("\t<script src=\"/xzfy/js/caseRegister/clientInfoRegister.js\"></script>\r\n");
      out.write("\t<script src=\"/xzfy/js/caseRegister/reviewMatters.js\"></script>\r\n");
      out.write("\t<script>\r\n");
      out.write("\t\t$('.li-input').hide();\r\n");
      out.write("\t\t$('.select-two').hide();\r\n");
      out.write("\r\n");
      out.write("\t\t$('.notpoliticaldo').hide();\r\n");
      out.write("\t\t$('.politicaldo').hide();\r\n");
      out.write("\r\n");
      out.write("\t\tfunction showInput(that) {\r\n");
      out.write("\t\t\t$(that).parent().siblings('.li-input').show();\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\tfunction hideInput(that) {\r\n");
      out.write("\t\t\t$(that).parent().siblings('.li-input').hide();\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\tfunction showNotPoliticalDo(that) {\r\n");
      out.write("\t\t\t$(that).parents().siblings('.politicaldo').hide();\r\n");
      out.write("\t\t\t$(that).parents().siblings('.notpoliticaldo').show();\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\tfunction showPoliticalDo(that) {\r\n");
      out.write("\t\t\t$(that).parents().siblings('.politicaldo').show();\r\n");
      out.write("\t\t\t$(that).parents().siblings('.notpoliticaldo').hide();\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t$(\".apply-select\").change(function() {\r\n");
      out.write("\t\t\tvar selected = $(\".apply-select\").val();\r\n");
      out.write("\t\t\treceptionInit();\r\n");
      out.write("\t\t\tif (selected == 1) {\r\n");
      out.write("\t\t\t\t$('.select-one').hide();\r\n");
      out.write("\t\t\t\t$('.select-two').show();\r\n");
      out.write("\t\t\t} else {\r\n");
      out.write("\t\t\t\t$('.select-one').show();\r\n");
      out.write("\t\t\t\t$('.select-two').hide();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t})\r\n");
      out.write("\r\n");
      out.write("\t\t$('.case-tab').click(function() {\r\n");
      out.write("\t\t\t$('.case-tab').removeClass('case-tab-active');\r\n");
      out.write("\t\t\t$(this).addClass('case-tab-active');\r\n");
      out.write("\t\t})\r\n");
      out.write("\t</script>\r\n");
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

  private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f0.setParent(null);
    // /xzfy/jsp/caseRegister/caseRegister.jsp(218,6) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${isReconsiderTogether=='1'}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
    if (_jspx_eval_c_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("checked=\"checked\"");
        int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f1 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f1.setParent(null);
    // /xzfy/jsp/caseRegister/caseRegister.jsp(221,6) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f1.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${isReconsiderTogether=='0'}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
    if (_jspx_eval_c_005fif_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("checked=\"checked\"");
        int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f2 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f2.setParent(null);
    // /xzfy/jsp/caseRegister/caseRegister.jsp(236,6) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f2.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${isReceiveMaterial=='1'}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
    if (_jspx_eval_c_005fif_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("checked=\"checked\"");
        int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f3 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f3.setParent(null);
    // /xzfy/jsp/caseRegister/caseRegister.jsp(239,6) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f3.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${isReceiveMaterial=='0'}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
    if (_jspx_eval_c_005fif_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("checked=\"checked\"");
        int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f4 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f4.setParent(null);
    // /xzfy/jsp/caseRegister/caseRegister.jsp(489,7) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f4.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${isReview=='1'}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
    if (_jspx_eval_c_005fif_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("checked=\"checked\"");
        int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f5 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f5.setParent(null);
    // /xzfy/jsp/caseRegister/caseRegister.jsp(491,7) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f5.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${isReview=='0'}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
    if (_jspx_eval_c_005fif_005f5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("checked=\"checked\"");
        int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f6 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f6.setParent(null);
    // /xzfy/jsp/caseRegister/caseRegister.jsp(497,7) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f6.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${isReview=='1'}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
    if (_jspx_eval_c_005fif_005f6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("checked=\"checked\"");
        int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f7(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f7 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f7.setParent(null);
    // /xzfy/jsp/caseRegister/caseRegister.jsp(499,7) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f7.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${isReview=='0'}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
    if (_jspx_eval_c_005fif_005f7 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("checked=\"checked\"");
        int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f8(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f8 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f8.setParent(null);
    // /xzfy/jsp/caseRegister/caseRegister.jsp(507,7) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f8.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${isCompensation=='1'}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
    if (_jspx_eval_c_005fif_005f8 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("checked=\"checked\"");
        int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f9(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f9 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f9.setParent(null);
    // /xzfy/jsp/caseRegister/caseRegister.jsp(510,7) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f9.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${isCompensation=='0'}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
    if (_jspx_eval_c_005fif_005f9 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("checked=\"checked\"");
        int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f10(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f10 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f10.setParent(null);
    // /xzfy/jsp/caseRegister/caseRegister.jsp(524,7) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f10.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${isDocumentReview=='1'}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
    if (_jspx_eval_c_005fif_005f10 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("checked=\"checked\"");
        int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f11(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f11 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f11.setParent(null);
    // /xzfy/jsp/caseRegister/caseRegister.jsp(527,7) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f11.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${isDocumentReview=='0'}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
    if (_jspx_eval_c_005fif_005f11 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("checked=\"checked\"");
        int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
    return false;
  }
}
