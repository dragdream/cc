package org.apache.jsp.system.core.system;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.auth.TeeAuthUtil;
import com.tianee.webframe.util.db.*;
import com.tianee.webframe.util.str.TeeJsonUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.sql.*;
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

public final class auth_005finfo_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/header/header2.0.jsp");
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
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
      out.write("\r\n");
      out.write("<title>");
      out.print(ieTitle);
      out.write("</title>\r\n");
      out.write("<script>\r\n");
      out.write("function updateSeries(){\r\n");
      out.write("\tvar pwd = prompt(\"请输入系统管理员密码:\",\"\");\r\n");
      out.write("\tif(pwd!=null){\r\n");
      out.write("\t\tvar json = tools.requestJsonRs(contextPath+\"/systemAction/updateSeries.action\",{pwd:pwd,series:$(\"#series\").val()});\r\n");
      out.write("\t\tif(json.rtState){\r\n");
      out.write("\t\t\t$.MsgBox.Alert_auto(\"更新成功\");\r\n");
      out.write("\t\t\twindow.location.reload();\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$.MsgBox.Alert_auto(\"密码输入有误\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function updateUnitName(){\r\n");
      out.write("\tvar pwd = prompt(\"请输入系统管理员密码:\",\"\");\r\n");
      out.write("\tif(pwd!=null){\r\n");
      out.write("\t\tvar json = tools.requestJsonRs(contextPath+\"/systemAction/updateUnitName.action\",{pwd:pwd,unitName:$(\"#unitName\").val()});\r\n");
      out.write("\t\tif(json.rtState){\r\n");
      out.write("\t\t\t$.MsgBox.Alert_auto(\"更新成功\");\r\n");
      out.write("\t\t\twindow.location.reload();\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$.MsgBox.Alert_auto(\"密码输入有误\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function updateAuthInfo(){\r\n");
      out.write("\tvar json = tools.requestJsonRs(contextPath+\"/systemAction/updateUnitName.action\",{pwd:pwd,unitName:$(\"#unitName\").val()});\r\n");
      out.write("\tif(json.rtState){\r\n");
      out.write("\t\t$.MsgBox.Alert_auto(\"更新成功\");\r\n");
      out.write("\t\twindow.location.reload();\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\t$.MsgBox.Alert_auto(\"密码输入有误\");\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function reloadAuthInfo(){\r\n");
      out.write("\tvar pwd = prompt(\"请输入系统管理员密码:\",\"\");\r\n");
      out.write("\tif(pwd!=null){\r\n");
      out.write("\t\tvar json = tools.requestJsonRs(contextPath+\"/systemAction/reloadAuthInfo.action\",{pwd:pwd});\r\n");
      out.write("\t\tif(json.rtState){\r\n");
      out.write("\t\t\t$.MsgBox.Alert_auto(\"更新成功\");\r\n");
      out.write("\t\t\twindow.location.reload();\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$.MsgBox.Alert_auto(\"密码输入有误\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
 
	Map<String,String> registInfo = TeeAuthUtil.getRegistInfo();
	Map<String,String> delayInfo = TeeAuthUtil.getDelayInfo();
	Map<String,String> versionInfo = new HashMap();
	
	URL url = null;
	HttpURLConnection connection = null;
	BufferedReader in = null;
	String inputline = null;
	
	
	
	try{
		// 创建url对象
		url = new URL("http://www.zatp.com.cn/version_ext.txt");
		// 打开url连接
		connection = (HttpURLConnection) url.openConnection();
		connection.setReadTimeout(1000*5);
		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("GET");
		// 发送
		in = new BufferedReader(new InputStreamReader(url.openStream()));
		// 返回发送结果
		inputline = in.readLine();
		
		versionInfo = TeeJsonUtil.JsonStr2Map(inputline);
		
	}catch(Exception ex){
		
	}finally{
		try{
			connection.disconnect();
		}catch(Exception ex){}
		try{
			in.close();
		}catch(Exception ex){}
	}
	
	
	

      out.write("\r\n");
      out.write("<body style=\"padding-left: 10px;padding-right: 10px;\">\r\n");
      out.write("    <div id=\"toolbar\" class=\"topbar clearfix\">\r\n");
      out.write("\t   <img class=\"title_img\" src=\"/system/core/system/imgs/icon_xtxxsz.png\" alt=\"\">\r\n");
      out.write("\t   &nbsp;<span class=\"title\">系统信息设置</span>\t   \r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\t<table class=\"TableBlock_page\" width=\"100%\" align=\"center\">\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td colspan=\"2\" ><img src=\"/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png\" alt=\"\" style=\"vertical-align:middle;\">\r\n");
      out.write("\t\t    <b style=\"color: #0050aa\">系统信息</b></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t");

			if(!"1".equals(TeeSysProps.getString("NO_LOGO"))){
				
      out.write("\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td style=\"text-indent: 15px;\">软件名称：</td>\r\n");
      out.write("\t\t\t\t\t<td>中腾OA协同办公智能管理平台</td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td style=\"text-indent: 15px;\">版权所有：</td>\r\n");
      out.write("\t\t\t\t\t<td>北京中安腾鹏科技有限公司</td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t");

			}
		
      out.write("\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">系统版本：</td>\r\n");
      out.write("\t\t\t<td>");
      out.print(TeeSysProps.getString("VERSION") );
      out.write("&nbsp;&nbsp;\r\n");
      out.write("\t\t\t");

				if(inputline==null){
					
      out.write("\r\n");
      out.write("\t\t\t\t\t<span style=\"color:gray\">网络错误</span>\r\n");
      out.write("\t\t\t\t\t");

				}else if(TeeSysProps.getString("VERSION").compareTo(versionInfo.get("Server"))<0){
					
      out.write("\r\n");
      out.write("\t\t\t\t\t<span style=\"color:red;cursor:pointer\" onclick=\"window.open('http://www.zatp.com.cn/Update_");
      out.print(versionInfo.get("Server") );
      out.write(".exe');\">有新版本，请更新</span>\r\n");
      out.write("\t\t\t\t\t");

				}else{
					
      out.write("\r\n");
      out.write("\t\t\t\t\t<span style=\"color:green\">无可用更新</span>\r\n");
      out.write("\t\t\t\t\t");

				}
			
      out.write("\r\n");
      out.write("\t\t\t&nbsp;&nbsp;<a href=\"http://www.zatp.com.cn/versiondesc.html?v=");
      out.print(System.currentTimeMillis() );
      out.write("\" target=\"_blank\">历史版本</a>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">版本号：</td>\r\n");
      out.write("\t\t\t<td>");
      out.print(TeeSysProps.getString("VERSION_NO") );
      out.write("</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">操作系统：</td>\r\n");
      out.write("\t\t\t<td>");
      out.print(System.getProperty("os.name") );
      out.write("&nbsp;&nbsp;");
      out.print(System.getProperty("os.arch") );
      out.write("&nbsp;&nbsp;版本：");
      out.print(System.getProperty("os.version") );
      out.write("</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">Tomcat所在目录：</td>\r\n");
      out.write("\t\t\t<td>");
      out.print(System.getProperty("user.dir"));
      out.write("</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">JRE版本：</td>\r\n");
      out.write("\t\t\t<td>");
      out.print(System.getProperty("java.version"));
      out.write("</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">JRE所在目录：</td>\r\n");
      out.write("\t\t\t<td>");
      out.print(System.getProperty("java.home"));
      out.write("</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">组织机构名称：</td>\r\n");
      out.write("\t\t\t<td>\r\n");
      out.write("\t\t\t\t<input style=\"width:280px\" type=\"text\" id=\"unitName\" name=\"unitName\" value=\"");
      out.print(TeeAuthUtil.getUnitName() );
      out.write("\"/>&nbsp;<a href=\"javascript:void(0)\" onclick=\"updateUnitName()\">更新</a>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">机器码：</td>\r\n");
      out.write("\t\t\t<td>");
      out.print(TeeAuthUtil.getMachineCode());
      out.write("</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">软件序列号：</td>\r\n");
      out.write("\t\t\t<td>\r\n");
      out.write("\t\t\t\t<input type=\"text\" id=\"series\" name=\"series\" value=\"");
      out.print(TeeSysProps.getString("SERIAL"));
      out.write("\"/>&nbsp;<a href=\"javascript:void(0)\" onclick=\"updateSeries()\">更新</a>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">用户信息：</td>\r\n");
      out.write("\t\t\t<td>");
      out.print(TeeAuthUtil.getUserInfosDesc() );
      out.write("</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">重新加载授权信息：</td>\r\n");
      out.write("\t\t\t<td>\r\n");
      out.write("\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"reloadAuthInfo()\">刷新</a>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td colspan=\"2\"><img src=\"/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png\" alt=\"\" style=\"vertical-align:middle;\">\r\n");
      out.write("\t\t    <b style=\"color: #0050aa\">正式授权信息</b><span style=\"color: red;\">（注：正式授权信息，需校验组织机构名称、机器码和软件序列号，务必与授权文件中的信息一致）</span></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">授权单位：</td>\r\n");
      out.write("\t\t\t<td>");
      out.print(registInfo==null?"":new String(registInfo.get("company").getBytes(),"UTF-8"));
      out.write("</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">授权机器码：</td>\r\n");
      out.write("\t\t\t<td>");
      out.print(registInfo==null?"":registInfo.get("machineCode"));
      out.write("</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">授权开始日期：</td>\r\n");
      out.write("\t\t\t<td>");
      out.print(registInfo==null?"":registInfo.get("beginTime"));
      out.write("</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">授权结束日期：</td>\r\n");
      out.write("\t\t\t<td>");
      out.print(registInfo==null?"":registInfo.get("endTime"));
      out.write("</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">移动版终端限制：</td>\r\n");
      out.write("\t\t\t<td>");
      out.print(registInfo==null?"30":registInfo.get("imLimit"));
      out.write("</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">PC端终端限制：</td>\r\n");
      out.write("\t\t\t<td>");
      out.print(registInfo==null?"30":registInfo.get("pcLimit"));
      out.write("</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">OA用户数限制：</td>\r\n");
      out.write("\t\t\t<td>");
      out.print(registInfo==null?"30":registInfo.get("personLimit"));
      out.write("</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">上传注册文件：</td>\r\n");
      out.write("\t\t\t<td style=\"text-align:left\">\r\n");
      out.write("\t\t\t\t<form action=\"");
      out.print(contextPath );
      out.write("/registAuth/uploadRegistAuthFile.action\" method=\"post\" enctype=\"multipart/form-data\">\r\n");
      out.write("\t\t\t\t\t<input type=\"file\" name=\"file\" />\r\n");
      out.write("\t\t\t\t\t<input type=\"submit\" value=\"提交\" class=\"btn-win-white\"/>\r\n");
      out.write("\t\t\t\t</form>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td style=\"text-indent: 15px;\">延期授权：</td>\r\n");
      out.write("\t\t\t<td style=\"text-align:left\">\r\n");
      out.write("\t\t\t\t<button class=\"btn-win-white\" onclick=\"window.location = 'auth_info_delay.jsp'\"><b>查看或提交延期授权文件</b></button>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t</table>\r\n");
      out.write("\r\n");
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
