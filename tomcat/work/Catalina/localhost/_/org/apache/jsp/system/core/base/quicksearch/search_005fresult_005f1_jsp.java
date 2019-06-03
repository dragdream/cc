package org.apache.jsp.system.core.base.quicksearch;

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
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.global.TeeSysProps;

public final class search_005fresult_005f1_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
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
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
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


	String loginOutText = (String) request.getSession().getAttribute("LOGIN_OUT_TEXT") == null ? "" : (String) request.getSession().getAttribute("LOGIN_OUT_TEXT");//退出提示语
	String ieTitle = (String) request.getSession().getAttribute("IE_TITLE") == null ? "" : (String) request.getSession().getAttribute("IE_TITLE");//主界面IEtitle
	String secUserMem = (String) request.getSession().getAttribute("SEC_USER_MEM") == null ? "" : (String) request.getSession().getAttribute("SEC_USER_MEM");//是否记忆用户名
    String souSouContent=request.getParameter("souSouContent");



      out.write("\r\n");
      out.write("<!-- jQuery库 -->\r\n");
      out.write("<script src=\"");
      out.print(contextPath );
      out.write("/common/jquery/jquery-1.11.3.min.js\"></script>\r\n");
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
      out.write("<!-- 其他工具库类 -->\r\n");
      out.write("<script src=\"");
      out.print(contextPath );
      out.write("/common/js/tools2.0.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(contextPath );
      out.write("/common/js/sys2.0.js\"></script>\r\n");
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
      out.write("\r\n");
      out.write("<!-- 图片预览器 -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/common/js/TeeMenu.js\"></script>\r\n");
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
      out.write("/attachmentController/commonUpload.action\";\r\n");
      out.write("var systemImagePath = \"");
      out.print(systemImagePath);
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
      out.write("scrollbar-arrow-color: #777777;  /*图6,三角箭头的颜色*/\r\n");
      out.write("scrollbar-face-color: #fff;  /*图5,立体滚动条的颜色*/\r\n");
      out.write("scrollbar-3dlight-color: red;  /*图1,立体滚动条亮边的颜色*/\r\n");
      out.write("scrollbar-highlight-color: #e9e9e9;  /*图2,滚动条空白部分的颜色*/\r\n");
      out.write("scrollbar-shadow-color: #c4c4c4;  /*图3,立体滚动条阴影的颜色*/\r\n");
      out.write("scrollbar-darkshadow-color: #666;  /*图4,立体滚动条强阴影的颜色*/\r\n");
      out.write("scrollbar-track-color: #dfdcdc;  /*图7,立体滚动条背景颜色*/\r\n");
      out.write("scrollbar-base-color:#fff;  /*滚动条的基本颜色*/\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/common/js/lazyloader.js\"></script>\r\n");
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
      out.write("\r\n");
      out.write("<title>查询结果</title>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("var word = \"\";\r\n");
      out.write("var type = 1;\r\n");
      out.write("$(function(){\r\n");
      out.write("\tdoSearchWork();\r\n");
      out.write("})\r\n");
      out.write("function doSearchWork(){\r\n");
      out.write("\tword = $(\"#word\").val();\r\n");
      out.write("\r\n");
      out.write("\ttools.requestJsonRs(contextPath+\"/quickSearch/quickSearchCount.action\",{word:word},true,function(countData){\r\n");
      out.write("\t\t$(\"#task\").html(countData.task);\r\n");
      out.write("\t\t$(\"#schedule\").html(countData.schedule);\r\n");
      out.write("\t\t$(\"#doc\").html(countData.doc);\r\n");
      out.write("\t\t$(\"#customer\").html(countData.customer);\r\n");
      out.write("\t\t$(\"#workflow\").html(countData.workflow);\r\n");
      out.write("\t\t$(\"#user\").html(countData.user);\r\n");
      out.write("\r\n");
      out.write("\t\t$(\"#list-content\").html(\"\");\r\n");
      out.write("\t\tloadLazyLoader();\r\n");
      out.write("\r\n");
      out.write("\t});\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("$(document).ready(function(){\r\n");
      out.write("\t$('#word').bind('keypress',function(event){\r\n");
      out.write("        if(event.keyCode == \"13\")\r\n");
      out.write("        {\r\n");
      out.write("        \tdoSearchWork();\r\n");
      out.write("        }\r\n");
      out.write("    });\r\n");
      out.write("});\r\n");
      out.write("\r\n");
      out.write("function changeTypeSearch(type,obj){\r\n");
      out.write("\t$(\".search_ul li\").removeClass(\"on\");\r\n");
      out.write("\t$(obj).addClass(\"on\");\r\n");
      out.write("\twindow.type = type;\r\n");
      out.write("\t$(\"#list-content\").html(\"\");\r\n");
      out.write("\tloadLazyLoader();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function loadLazyLoader(){\r\n");
      out.write("\twindow.loader = null;\r\n");
      out.write("\twindow.loader = new lazyLoader({\r\n");
      out.write("\t\turl:contextPath+'/quickSearch/quickSearchList.action',\r\n");
      out.write("\t\tplaceHolder:'loadMore',\r\n");
      out.write("\t\tqueryParam:{word:word,type:type},\r\n");
      out.write("\t\tcontentHolder:'list-content',\r\n");
      out.write("\t\tpageSize:30,\r\n");
      out.write("\t\trowRender:function(rowData){\r\n");
      out.write("\t\t\tvar render = [];\r\n");
      out.write("\t\t\tif(window.type==1){//任务\r\n");
      out.write("\t\t\t\trender.push(\"<div class='list-item' onclick=\\\"openFullWindow('");
      out.print(request.getContextPath() );
      out.write("/system/subsys/cowork/detail.jsp?taskId=\"+rowData.sid+\"')\\\">\"+rowData.taskTitle+\"</div>\");\r\n");
      out.write("\t\t\t}else if(window.type==2){//计划\r\n");
      out.write("\t\t\t\trender.push(\"<div class='list-item' onclick=\\\"openFullWindow('");
      out.print(request.getContextPath() );
      out.write("/system/subsys/schedule/manage/detail.jsp?scheduleId=\"+rowData.uuid+\"')\\\">\"+rowData.title+\"</div>\");\r\n");
      out.write("\t\t\t}else if(window.type==3){//文档\r\n");
      out.write("\t\t\t\trender.push(\"<div class='list-item' fileName=\\\"\"+rowData.fileName+\"\\\" ext=\\\"\"+rowData.ext+\"\\\" attachId=\\\"\"+rowData.attachId+\"\\\">\"+rowData.fileName+\"</div>\");\r\n");
      out.write("\t\t\t}else if(window.type==4){//客户\r\n");
      out.write("\t\t\t\trender.push(\"<div class='list-item' onclick=\\\"openFullWindow('");
      out.print(request.getContextPath() );
      out.write("/system/subsys/crm/core/customInfo/detail.jsp?sid=\"+rowData.sid+\"')\\\">\"+rowData.customerName+\"</div>\");\r\n");
      out.write("\t\t\t}else if(window.type==5){//流程\r\n");
      out.write("\t\t\t\trender.push(\"<div class='list-item' onclick=\\\"openFullWindow('");
      out.print(request.getContextPath() );
      out.write("/system/core/workflow/flowrun/print/index.jsp?view=3&runId=\"+rowData.runId+\"')\\\"><span style='color:green'>[\"+rowData.runId+\"]&nbsp;</span>\"+rowData.runName+\"</div>\");\r\n");
      out.write("\t\t\t}else if(window.type==6){//用户\r\n");
      out.write("\t\t\t\trender.push(\"<div class='list-item' onclick=\\\"openFullWindow('");
      out.print(request.getContextPath() );
      out.write("/system/core/person/userinfo.jsp?uuid=\"+rowData.uuid+\"')\\\">\"+rowData.userName+\"</div>\");\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t$(\"#loadMore\").show();\r\n");
      out.write("\t\t\treturn render.join(\"\");\r\n");
      out.write("\t\t},\r\n");
      out.write("\t\tonLoadSuccess:function(){\r\n");
      out.write("\t\t\tif(window.type==3){//文档类型，支持下载和转储\r\n");
      out.write("\t\t\t\t$(\".list-item\").each(function(i,obj){\r\n");
      out.write("\t\t\t\t\tvar att = {priv:1+2,fileName:obj.getAttribute(\"fileName\"),ext:obj.getAttribute(\"ext\"),sid:obj.getAttribute(\"attachId\")};\r\n");
      out.write("\t\t\t\t\tvar attach = tools.getAttachElement(att,{});\r\n");
      out.write("\t\t\t\t\t$(obj).html(\"\").append(attach);\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t},\r\n");
      out.write("\t\tonNoData:function(){\r\n");
      out.write("\t\t\t$(\"#loadMore\").hide();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("<style>\r\n");
      out.write("body{\r\n");
      out.write("\tfont-family:微软雅黑;\r\n");
      out.write("}\r\n");
      out.write(".search_ul{\r\n");
      out.write("\tmargin:0px;\r\n");
      out.write("\tpadding:0px;\r\n");
      out.write("\tlist-style:none;\r\n");
      out.write("\tmargin-top:10px;\r\n");
      out.write("}\r\n");
      out.write(".search_ul li{\r\n");
      out.write("\tfloat:left;\r\n");
      out.write("\tfont-size:12px;\r\n");
      out.write("\tmargin-left:10px;\r\n");
      out.write("\tdisplay:block;\r\n");
      out.write("\tcolor:#888;\r\n");
      out.write("\tmin-width:50px;\r\n");
      out.write("\theight:25px;\r\n");
      out.write("\ttext-align:center;\r\n");
      out.write("\tcursor:pointer;\r\n");
      out.write("}\r\n");
      out.write(".search_ul li:hover{\r\n");
      out.write("\tcolor:#69b1df;\r\n");
      out.write("}\r\n");
      out.write(".search_ul li.on{\r\n");
      out.write("\tcolor:#69b1df;\r\n");
      out.write("\tborder-bottom:2px solid #69b1df;\r\n");
      out.write("}\r\n");
      out.write(".list-content{\r\n");
      out.write("\tclear:both;\r\n");
      out.write("\tposition:absolute;\r\n");
      out.write("\ttop:80px;\r\n");
      out.write("\tleft:10px;\r\n");
      out.write("\tright:10px;\r\n");
      out.write("\tbottom:10px;\r\n");
      out.write("}\r\n");
      out.write(".list-item{\r\n");
      out.write("\tpadding:10px;\r\n");
      out.write("\tborder-bottom:1px solid #efefef;\r\n");
      out.write("\tcolor:#000;\r\n");
      out.write("\tfont-size:12px;\r\n");
      out.write("\tbackground:white;\r\n");
      out.write("\tcursor:pointer;\r\n");
      out.write("}\r\n");
      out.write(".list-item:hover{\r\n");
      out.write("\tbackground:#f9f9f9;\r\n");
      out.write("}\r\n");
      out.write(".search_btn{\r\n");
      out.write("\tbackground-color: #6a9ee8;\r\n");
      out.write("\tborder:none;\r\n");
      out.write("\toutline: none;\r\n");
      out.write("\tcolor: #fff;\r\n");
      out.write("\ttext-align: center;\r\n");
      out.write("\tfont-size: 16px;\r\n");
      out.write("\twidth: 70px;\r\n");
      out.write("\theight: 30px;\r\n");
      out.write("\tline-height: 28px;\r\n");
      out.write("\tcursor: pointer;\r\n");
      out.write("\tfont-family: \"微软雅黑\";\r\n");
      out.write("}\r\n");
      out.write(".input-sm {\r\n");
      out.write("    padding: 5px 10px;\r\n");
      out.write("    font-size: 12px;\r\n");
      out.write("    line-height: 18px;\r\n");
      out.write("}\r\n");
      out.write(".form-control{\r\n");
      out.write("\twidth: 90%;\r\n");
      out.write("    color: #555;\r\n");
      out.write("    background-color: #fff;\r\n");
      out.write("    background-image: none;\r\n");
      out.write("    border: 1px solid #ccc;\r\n");
      out.write("    font-family: \"微软雅黑\";\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("<body style=\"margin:10px;background:transparent\">\r\n");
      out.write("<input type=\"text\" id=\"word\" value=\"");
      out.print(souSouContent );
      out.write("\" class=\"form-control input-sm\" placeholder=\"请输入检索关键字\" onkeyup=\"doSearchWork()\"/>\r\n");
      out.write("<ul class=\"search_ul\">\r\n");
      out.write("\t<li class=\"on\" onclick=\"changeTypeSearch(1,this)\">任务(<span id=\"task\">0</span>)</li>\r\n");
      out.write("\t<li onclick=\"changeTypeSearch(2,this)\">计划(<span id=\"schedule\">0</span>)</li>\r\n");
      out.write("\t<li onclick=\"changeTypeSearch(3,this)\">文档(<span id=\"doc\">0</span>)</li>\r\n");
      out.write("\t<li onclick=\"changeTypeSearch(4,this)\">客户(<span id=\"customer\">0</span>)</li>\r\n");
      out.write("\t<li onclick=\"changeTypeSearch(5,this)\">流程(<span id=\"workflow\">0</span>)</li>\r\n");
      out.write("\t<li onclick=\"changeTypeSearch(6,this)\">用户(<span id=\"user\">0</span>)</li>\r\n");
      out.write("</ul>\r\n");
      out.write("<div class=\"list-content\">\r\n");
      out.write("\t<div id=\"list-content\"></div>\r\n");
      out.write("\t<br/>\r\n");
      out.write("\t<div id=\"loadMore\" style=\"font-size:12px;text-align:center;display:none;cursor:pointer\">点击加载更多</div>\r\n");
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
