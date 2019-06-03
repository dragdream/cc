package org.apache.jsp.xzfy.jsp.caseAcceptence;

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

public final class caseAccept_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\r');
      out.write('\n');
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
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\"/>\r\n");
      out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n");
      out.write("\t\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"/xzfy/css/init1.css\" />\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"/xzfy/css/common.css\" />\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"/xzfy/css/index.css\" />\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"/xzfy/css/accept.css\" />\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"/xzfy/imgs/caseAccept_icon/iconfont.css\" />\r\n");
      out.write("    <script type=\"text/javascript\" src=\"/xzfy/js/common/common.js\"></script>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"/xzfy/js/base/juicer-min.js\"></script>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"/common/My97DatePicker/WdatePicker.js\"></script>\r\n");
      out.write("    <title>立案受理</title>\r\n");
      out.write("    <style type=\"text/css\">\r\n");
      out.write("    html, body{\r\n");
      out.write("\tmargin:0;\r\n");
      out.write("\tpadding:0;\r\n");
      out.write("\tborder:0;\r\n");
      out.write("\toutline:0;\r\n");
      out.write("\tfont-weight:inherit;\r\n");
      out.write("\tfont-style:inherit;\r\n");
      out.write("\tfont-size:100%;\r\n");
      out.write("\tfont-family:inherit;\r\n");
      out.write("\tvertical-align:baseline;\r\n");
      out.write("\t-webkit-text-size-adjust:90%;\r\n");
      out.write("    overflow: auto;\r\n");
      out.write("\t}\r\n");
      out.write("    </style>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body onload=\"doInit()\"> \r\n");
      out.write("    <div id='filling-content'>\r\n");
      out.write("        <div class='case-buttons'>\r\n");
      out.write("            <div class='btn-left'>\r\n");
      out.write("                <span class='btn generate-accept'><span class='iconfont ddd'>&nbsp;&#xe6cb;&nbsp;</span>生成受理通知书</span>\r\n");
      out.write("                <span class='btn generate-reply'><span class='iconfont ddd'>&nbsp;&#xe6cb;&nbsp;</span>生成答复通知书</span>\r\n");
      out.write("                <span class='btn upload-accept'><span class='iconfont ddd'>&nbsp;&#xe62e;&nbsp;</span>上传受理通知书</span>\r\n");
      out.write("                <span class='btn upload-reply'><span class='iconfont ddd'>&nbsp;&#xe62e;&nbsp;</span>上传答复通知书</span>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class='btn-right'>\r\n");
      out.write("                <span class='btn case-similar'>相似案例</span>\r\n");
      out.write("                <span class='btn case-rules'>法律法规</span>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class='case-fill'>\r\n");
      out.write("            <div class='fill-table'>\r\n");
      out.write("                <span class='fill-title'>申请人信息：</span>\r\n");
      out.write("                <span class='fill-input'>\r\n");
      out.write("                    <input type=\"text\" id=\"applicantList\" readonly=\"readonly\"/>\r\n");
      out.write("                </span>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class='fill-table'>\r\n");
      out.write("                <span class='fill-title'>被申请人信息：</span>\r\n");
      out.write("                <span class='fill-input'>\r\n");
      out.write("                    <input type=\"text\" id=\"respondentList\" readonly=\"readonly\"/>\r\n");
      out.write("                </span>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class='fill-table'>\r\n");
      out.write("                <span class='fill-title'>行政复议请求：</span>\r\n");
      out.write("                <span class='fill-input'>\r\n");
      out.write("                    <input type=\"text\" id=\"requestForReconsideration\" readonly=\"readonly\"/>\r\n");
      out.write("                </span>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class='fill-table'>\r\n");
      out.write("                <span class='fill-title'>事实和理由：</span>\r\n");
      out.write("                <span class='fill-input'>\r\n");
      out.write("                    <input type=\"text\" id=\"factsAndReasons\" readonly=\"readonly\"/>\r\n");
      out.write("                </span>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class='module-middle'>\r\n");
      out.write("            <div class='stage-title'>\r\n");
      out.write("                <span class='establish-stage' onclick='changeFormShow(this,\"1\")'>登记阶段材料</span>\r\n");
      out.write("                <span class='establish-stage active-estab' onclick='changeFormShow(this,\"2\")'>立案阶段材料</span>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class='form-info'>\r\n");
      out.write("                <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id='apply-table' style=\"display: none;\">\r\n");
      out.write("                    <tr>\r\n");
      out.write("                        <th>序号</th>\r\n");
      out.write("                        <th>文件类型</th>\r\n");
      out.write("                        <th>文件名称</th>\r\n");
      out.write("                        <th>操作</th>\r\n");
      out.write("                    </tr>\r\n");
      out.write("                </table>\r\n");
      out.write("                <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id='accept-table' style=\"display: none;\">\r\n");
      out.write("                    <tr>\r\n");
      out.write("                        <th>序号</th>\r\n");
      out.write("                        <th>文件类型</th>\r\n");
      out.write("                        <th>文件名称</th>\r\n");
      out.write("                        <th>操作</th>\r\n");
      out.write("                    </tr>\r\n");
      out.write("                </table>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class='establish-calendar'>\r\n");
      out.write("                <span id=\"acceptTimeTitle\">立案时间：</span>\r\n");
      out.write("                <input type=\"text\" id=\"acceptTime\" readonly onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\" class=\"Wdate BigInput\" style=\"width:172px;height: 20px;border: 1px solid #E2E2E2 !important;\"  />\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class='establish-advices'>\r\n");
      out.write("                <span>立案意见：</span>\r\n");
      out.write("                <span>\r\n");
      out.write("                    <input type=\"text\" id=\"reason\" class='estab-input' placeholder=\"请输入理由\" />\r\n");
      out.write("                </span>\r\n");
      out.write("            </div>\r\n");
      out.write("           \r\n");
      out.write("           \r\n");
      out.write("        </div>\r\n");
      out.write("        <div class='module-bottom'>\r\n");
      out.write("            <div class='operate-container'>\r\n");
      out.write("                <span class='btn case-save' onclick=\"submit()\">保存</span>\r\n");
      out.write("                <span class='btn case-approval' onclick=\"approval()\">立案审批</span>\r\n");
      out.write("                <span class='btn case-hearing-person' onclick=\"chooseNext()\">选择审理人</span>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>         \r\n");
      out.write("   \r\n");
      out.write("    <script type=\"text/javascript\" src=\"/xzfy/js/caseAcceptence/acceptcommon.js\"></script>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"/xzfy/js/caseAcceptence/caseAccept.js\"></script>\r\n");
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
