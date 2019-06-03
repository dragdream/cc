package org.apache.jsp.system.frame._3;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.oa.core.general.model.TeeSmsModel;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeeUserRole;
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

public final class right_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(3);
    _jspx_dependants.add("/header/smsHeader.jsp");
    _jspx_dependants.add("/header/header.jsp");
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
      out.write("\r\n");
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

	String smsRefresh = TeeSysProps.getString("$SMS_REFRESH_TIME");
	if (smsRefresh == null || "".equals(smsRefresh.trim())) {
	  smsRefresh = "10";
	}
	String smsCallCount = TeeSysProps.getString("$SMS_VIOCE_MAX");
	if (smsCallCount == null || "".equals(smsCallCount.trim())) {
	  smsCallCount = "3";
	}
	String smsVoiceSpace = TeeSysProps.getString("$SMS_VOICE_SPACE");
	if (smsVoiceSpace == null || "".equals(smsVoiceSpace.trim())) {
	  smsVoiceSpace = "3";
	}
	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);

	TeeDepartment dept = person.getDept();
	TeeUserRole userRole = person.getUserRole();
	String deptNameDesc = "";
	String roleNameDesc = "";
	if(dept != null){
		deptNameDesc = dept.getDeptName();
	}
	if(userRole != null){
		roleNameDesc = userRole.getRoleName();
	}
	long loginTime = person.getDynamicInfo().getOnline();
	long loginTimeL = loginTime * 1000;
	String onlineTime = TeeDateUtil.getTimeMilisecondDesc(loginTimeL);
	
	String userNameDesc = person.getUserName();
	String userInfo = "姓名: " +  userNameDesc
	+ "\n部门: " + deptNameDesc
	+ "\n角色: " + roleNameDesc
	+ "\n在线时长: " + onlineTime
	;

      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("var bSmsPriv = true;\r\n");
      out.write("var userId = \"");
      out.print(person.getUserId());
      out.write("\";\r\n");
      out.write("var userName = \"");
      out.print(person.getUserName());
      out.write("\";\r\n");
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
      out.write("<title>组织机构</title>\r\n");
      out.write("<link href=\"css/index.css\" type=\"text/css\" rel=\"stylesheet\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(cssPath);
      out.write("/menu.css\">\r\n");
      out.write("<script type=\"text/javascript\"src=\"");
      out.print(contextPath);
      out.write("/system/frame/default/js/index.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/common/js/TeeMenu.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/system/core/org/orgUser/orgUser.js?s=11\"></script>\r\n");
      out.write("<style>\r\n");
      out.write("<!--\r\n");
      out.write(".hd{\r\n");
      out.write("    width: 235px;\r\n");
      out.write("    height: 56px;\r\n");
      out.write("    background: #FFF;\r\n");
      out.write("    padding: 0px;\r\n");
      out.write("    margin: 0px;\r\n");
      out.write("\r\n");
      out.write("}\r\n");
      out.write(".hd li.on {\r\n");
      out.write("    width: 70px;\r\n");
      out.write("    background: #FFF;\r\n");
      out.write("    border-bottom: 1px #FFF solid;\r\n");
      out.write("    border-left: 1px #e9e9e9 solid;\r\n");
      out.write("    border-right: 1px #e9e9e9 solid;\r\n");
      out.write("    position: relative;\r\n");
      out.write("    left: -1px;\r\n");
      out.write("    color: #263543;\r\n");
      out.write("}\r\n");
      out.write(".hd li {\r\n");
      out.write("    width: 70px;\r\n");
      out.write("    float: left;\r\n");
      out.write("    text-align: center;\r\n");
      out.write("    line-height: 40px;\r\n");
      out.write("    font-family: \"Microsoft YaHei\";\r\n");
      out.write("    color: #6e6e6e;\r\n");
      out.write("    border-bottom: 1px #e9e9e9 solid;\r\n");
      out.write("    background: #fafafa;\r\n");
      out.write("    padding: 0px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("-->\r\n");
      out.write("</style>\r\n");
      out.write("\r\n");
      out.write("\t\t\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t\t\tfunction doInit() {\r\n");
      out.write("\t\t\t\t\t//设置高度\r\n");
      out.write("\t\t\t\t\t/* var clientHeight = parent.$(\".c_right fl\").clientHeight;\r\n");
      out.write("\t\t\t\t\t$(\"#orgUserZtree\").css(\"height\",clientHeight-47); */\r\n");
      out.write("\t\t\t\t\tgetORGInfo();\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t</script>\r\n");
      out.write("<style>\r\n");
      out.write(".btn_span{\r\n");
      out.write("width:119px;\r\n");
      out.write("height:43px;\r\n");
      out.write("line-height:43px;\r\n");
      out.write("border-bottom:1px solid #f0f0f0;\r\n");
      out.write("cursor:pointer;\r\n");
      out.write("background:#fafafa;\r\n");
      out.write("font-family:微软雅黑;\r\n");
      out.write("position:absolute;\r\n");
      out.write("}\r\n");
      out.write(".btn_span_hot{\r\n");
      out.write("border-bottom:0px;\r\n");
      out.write("background:none;\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body onload=\"doInit();\" style=\"overflow:hidden;\">\r\n");
      out.write("\r\n");
      out.write("\t<div style=\"text-align: center; position:absolute;left:0px;right:0px;top:0px;height:43px;\" class=\"c_right_tab clearfix\">\r\n");
      out.write("\t\t<span class=\"btn_span btn_span_hot\" item='1' style=\"border-right:1px solid #f0f0f0;left:0px;top:0px;\">在线人员</span>&nbsp;&nbsp;<span class=\"btn_span\" style=\"left:120px\">全部人员</span>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<div layout=\"center\" style=\"position:absolute;left:0px;right:0px;bottom:0px;top:43px;overflow-y:auto;overflow-x:hidden\">\r\n");
      out.write("\t\t<ul id=\"orgUserZtree\" class=\"ztree\"\r\n");
      out.write("\t\t\tstyle=\"border: 0px; width: 95%;; overflow-y: hidden;\"></ul>\r\n");
      out.write("\t\t</a>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<script>\r\n");
      out.write("\t\t$(\".c_right_tab span\").click(function() {\r\n");
      out.write("\t\t\t$(\".c_right_tab span\").removeClass(\"btn_span_hot\");\r\n");
      out.write("\t\t\t$(this).addClass(\"btn_span_hot\");\r\n");
      out.write("\t\t\tvar item = $(this).attr(\"item\");\r\n");
      out.write("\t\t\tif (item == '1') {\r\n");
      out.write("\t\t\t\tonlineUser();\r\n");
      out.write("\t\t\t} else {\r\n");
      out.write("\t\t\t\tallUser();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t//var index = $(this).index();\r\n");
      out.write("\r\n");
      out.write("\t\t\t/* $('.c_right_list').hide();\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t$('.c_right_list').eq(index).show(); */\r\n");
      out.write("\t\t});\r\n");
      out.write("\t</script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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
