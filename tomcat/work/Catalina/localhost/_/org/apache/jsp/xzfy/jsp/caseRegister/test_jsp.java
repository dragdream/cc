package org.apache.jsp.xzfy.jsp.caseRegister;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.text.SimpleDateFormat;
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

public final class test_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\"/>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n");

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
      out.write("/common/js/upload.js?v=9\"></script>\r\n");
      out.write("\r\n");
      out.write("<script>\r\n");
      out.write("var UPLOAD_ATTACH_LIMIT_GLOBAL = \"");
      out.print(TeeSysProps.getString("UPLOAD_ATTACH_LIMIT"));
      out.write("\";\r\n");
      out.write("var GLOBAL_ATTACH_TYPE=\"");
      out.print(TeeStringUtil.getString(TeeSysProps.getString("GLOBAL_ATTACH_TYPE")) );
      out.write("\";\r\n");
      out.write("</script>");
      out.write("<link href=\"css/bootstrap.min.css\" rel=\"stylesheet\"> \r\n");
      out.write("<link href=\"https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css\" rel=\"stylesheet\"> \r\n");
      out.write("\r\n");
      out.write("<style>\r\n");
      out.write("body{\r\n");
      out.write("\tbackground:#fff;\r\n");
      out.write("\tpadding:5px 10px 0 10px; \r\n");
      out.write("}\r\n");
      out.write(".fyform .row .col-md-4,.col-md-6,.col-md-8,.col-md-2,.col-md-10{padding:0;margin:0 0 10px 0;}\r\n");
      out.write(".fyleft, .fyright ,.fycenter, .fybottom{padding:0}\r\n");
      out.write(".inputlable{text-align:right;height:30px;line-height:30px;font-weight: normal;}\r\n");
      out.write(".form-control{height:30px;border-radius:0px;}\r\n");
      out.write(".radio-group{text-align:left;}\r\n");
      out.write(".radio-group span{    \r\n");
      out.write("\tdisplay: inline-block;\r\n");
      out.write("    height: 28px;\r\n");
      out.write("    line-height: 28px;\r\n");
      out.write("    padding: 0 10px;\r\n");
      out.write("\tborder: 1px solid;}\r\n");
      out.write(".inputlable:after{content:\"*\";color:red;}\r\n");
      out.write("\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("<div class=\"container-fluid fyform\">\r\n");
      out.write("\t<div class=\"row\">\r\n");
      out.write("\t     <div class=\"col-md-6 fyleft\">\r\n");
      out.write("\t         <label class=\"inputlable col-md-4 control-label\">案件编号：</label>\r\n");
      out.write("\t\t     <div class=\"col-md-8\">\r\n");
      out.write("\t\t         <select class=\"form-control\">\r\n");
      out.write("\t\t\t\t <option>1</option>\r\n");
      out.write("\t\t\t\t <option>2</option>\r\n");
      out.write("\t\t\t\t <option>3</option>\r\n");
      out.write("\t\t\t\t <option>4</option>\r\n");
      out.write("\t\t\t\t <option>5</option>\r\n");
      out.write("\t\t\t\t </select>\r\n");
      out.write("\t\t     </div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t <label class=\"inputlable col-md-4 control-label\">申请日期：</label>\r\n");
      out.write("\t\t     <div class=\"col-md-8\">\r\n");
      out.write("\t\t\t\t <div class='input-group date' id='datetime1'>\r\n");
      out.write("                 <input type='text' class=\"form-control\" />\r\n");
      out.write("                 <span class=\"input-group-addon\">\r\n");
      out.write("                    <span class=\"glyphicon glyphicon-calendar\"></span>\r\n");
      out.write("                 </span>\r\n");
      out.write("                 </div>\r\n");
      out.write("\t\t     </div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t <label class=\"inputlable col-md-4 control-label\">申请事项：</label>\r\n");
      out.write("\t\t     <div class=\"col-md-8\">\r\n");
      out.write("\t\t         <input type=\"text\" class=\"form-control\" id=\"\" placeholder=\"\" />\r\n");
      out.write("\t\t     </div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t <label class=\"inputlable col-md-4 control-label\">具体行政行为文号：</label>\r\n");
      out.write("\t\t     <div class=\"col-md-8\">\r\n");
      out.write("\t\t         <select class=\"form-control\">\r\n");
      out.write("\t\t\t\t <option>1</option>\r\n");
      out.write("\t\t\t\t <option>2</option>\r\n");
      out.write("\t\t\t\t <option>3</option>\r\n");
      out.write("\t\t\t\t <option>4</option>\r\n");
      out.write("\t\t\t\t <option>5</option>\r\n");
      out.write("\t\t\t\t </select>\r\n");
      out.write("\t\t     </div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t <label class=\"inputlable col-md-4 control-label\">收到处罚决定书日期：</label>\r\n");
      out.write("\t\t     <div class=\"col-md-8\">\r\n");
      out.write("\t\t\t\t <div class='input-group date' id='datetime2'>\r\n");
      out.write("                 <input type='text' class=\"form-control\" />\r\n");
      out.write("                 <span class=\"input-group-addon\">\r\n");
      out.write("                    <span class=\"glyphicon glyphicon-calendar\"></span>\r\n");
      out.write("                 </span>\r\n");
      out.write("                 </div>\r\n");
      out.write("\t\t     </div>\r\n");
      out.write("\r\n");
      out.write("\t     </div>\r\n");
      out.write("\t     <div class=\"col-md-6 fyright\">\r\n");
      out.write("\t         <label class=\"inputlable col-md-4 control-label\">行政类别管理：</label>\r\n");
      out.write("\t\t     <div class=\"col-md-8\">\r\n");
      out.write("\t\t         <input type=\"text\" class=\"form-control\" id=\"\" placeholder=\"\" />\r\n");
      out.write("\t\t     </div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t <label class=\"inputlable col-md-4 control-label\">具体行政行为名称：</label>\r\n");
      out.write("\t\t     <div class=\"col-md-8\">\r\n");
      out.write("\t\t         <select class=\"form-control\">\r\n");
      out.write("\t\t\t\t <option>1</option>\r\n");
      out.write("\t\t\t\t <option>2</option>\r\n");
      out.write("\t\t\t\t <option>3</option>\r\n");
      out.write("\t\t\t\t <option>4</option>\r\n");
      out.write("\t\t\t\t <option>5</option>\r\n");
      out.write("\t\t\t\t </select>\r\n");
      out.write("\t\t     </div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t <label class=\"inputlable col-md-4 control-label\">具体行政行为日期：</label>\r\n");
      out.write("\t\t     <div class=\"col-md-8\">\r\n");
      out.write("\t\t         <select class=\"form-control\">\r\n");
      out.write("\t\t\t\t <option>1</option>\r\n");
      out.write("\t\t\t\t <option>2</option>\r\n");
      out.write("\t\t\t\t <option>3</option>\r\n");
      out.write("\t\t\t\t <option>4</option>\r\n");
      out.write("\t\t\t\t <option>5</option>\r\n");
      out.write("\t\t\t\t </select>\r\n");
      out.write("\t\t     </div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t <label class=\"inputlable col-md-4 control-label\">得知该具体行为途径：</label>\r\n");
      out.write("\t\t     <div class=\"col-md-8\">\r\n");
      out.write("\t\t         <input type=\"text\" class=\"form-control\" id=\"\" placeholder=\"\" />\r\n");
      out.write("\t\t     </div>\r\n");
      out.write("\t     </div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\t<div class=\"row\">\r\n");
      out.write("\t     <div class=\"col-md-12 fycenter\">\r\n");
      out.write("\t         <label class=\"inputlable col-md-2 control-label\">具体行政行为：</label>\r\n");
      out.write("\t\t     <div class=\"col-md-10\">\r\n");
      out.write("\t\t         <textarea class=\"form-control\" rows=\"3\"></textarea>\r\n");
      out.write("\t\t\t </div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t <label class=\"inputlable col-md-2 control-label\">行政复议请求：</label>\r\n");
      out.write("\t\t     <div class=\"col-md-10\">\r\n");
      out.write("\t\t         <textarea class=\"form-control\" rows=\"3\"></textarea>\r\n");
      out.write("\t\t\t </div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t <label class=\"inputlable col-md-2 control-label\"></label>\r\n");
      out.write("\t\t     <div class=\"col-md-10 radio-group\">\r\n");
      out.write("\t\t         <span><input type=\"radio\" name=\"optradio\" />   是否行政复议前置</span>\r\n");
      out.write("\t\t\t\t <span><input type=\"radio\" name=\"optradio\" />   是否行政不作为</span>\r\n");
      out.write("\t\t\t\t <span><input type=\"radio\" name=\"optradio\" />   是否申请赔偿</span>\r\n");
      out.write("\t\t\t\t <span><input type=\"radio\" name=\"optradio\" />   是否申请举行听证会</span>\r\n");
      out.write("\t\t\t\t <span><input type=\"radio\" name=\"optradio\" />   是否申请附带对规范性文件的审查</span>\r\n");
      out.write("\t\t\t </div>\r\n");
      out.write("\t\t </div>\t \r\n");
      out.write("    </div>\r\n");
      out.write("\r\n");
      out.write("\t<div class=\"row\">\r\n");
      out.write("\t     <div class=\"col-md-12 fybottom\">\r\n");
      out.write("\t\t\t <div class=\"col-md-6\">\r\n");
      out.write("\t\t\t <label class=\"inputlable col-md-4 control-label\">赔偿请求类型：</label>\r\n");
      out.write("\t\t     <div class=\"col-md-8\">\r\n");
      out.write("\t\t         <input type=\"text\" class=\"form-control\" id=\"\" placeholder=\"\">\r\n");
      out.write("\t\t     </div>\r\n");
      out.write("\t\t\t </div>\r\n");
      out.write("\t\t\t <div class=\"col-md-6 bottomright\">\r\n");
      out.write("\t\t\t <label class=\"inputlable col-md-4 control-label\">规范性文件名称：</label>\r\n");
      out.write("\t\t     <div class=\"col-md-8\">\r\n");
      out.write("\t\t         <input type=\"text\" class=\"form-control\" id=\"\" placeholder=\"\">\r\n");
      out.write("\t\t     </div>\r\n");
      out.write("\t\t\t </div>\r\n");
      out.write("\t\t </div>\t \r\n");
      out.write("    </div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script>\r\n");
      out.write("        $(function () {\r\n");
      out.write("            $(\"#datetime1\").datetimepicker({\r\n");
      out.write("                format: 'YYYY-MM-DD',\r\n");
      out.write("                locale: moment.locale('zh-cn')\r\n");
      out.write("            });\r\n");
      out.write("\t\t\t$(\"#datetime1\").children(\".dropdown-menu\").css(\"z-index\",\"9999\");\r\n");
      out.write("\t\t\t $(\"#datetime2\").datetimepicker({\r\n");
      out.write("                format: 'YYYY-MM-DD',\r\n");
      out.write("                maxView:5,\r\n");
      out.write("                locale: moment.locale('zh-cn')\r\n");
      out.write("            });\r\n");
      out.write("        });\r\n");
      out.write("\r\n");
      out.write("\t\t\r\n");
      out.write("    </script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("      <script src=\"../../js/base/jquery.1.11.3.min.js\"></script>\r\n");
      out.write("      <script src=\"../../js/base/bootstrap.min.js\"></script>\r\n");
      out.write("\t  <script src=\"https://cdn.bootcss.com/moment.js/2.18.1/moment-with-locales.min.js\"></script>  \r\n");
      out.write("\t  <script src=\"https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js\"></script>\r\n");
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
