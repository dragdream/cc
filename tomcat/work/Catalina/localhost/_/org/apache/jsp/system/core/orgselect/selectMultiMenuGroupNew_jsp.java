package org.apache.jsp.system.core.orgselect;

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

public final class selectMultiMenuGroupNew_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/header/header.jsp");
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

String moduleId = request.getParameter("moduleId");
if (moduleId == null) {
  moduleId = "";
}
String privNoFlag = request.getParameter("privNoFlag");
if (privNoFlag == null || "".equals(privNoFlag)) {
  privNoFlag = "0";
}
String privOp = request.getParameter("privOp");
if (privOp == null) {
  privOp = "";
}
String objSelectType = request.getParameter("objSelectType");
if (objSelectType == null) {
	objSelectType = "";
}

String deptUuid = request.getParameter("deptUuid");
if (deptUuid == null) {
	deptUuid = "";
}

//回调函数参数
String callBackPara = request.getParameter("callBackPara") == null ? "" : request.getParameter("callBackPara")  ;
callBackPara = callBackPara.replace("\"", "\\\"");

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\"/>\r\n");
      out.write("<meta http-equiv=\"cache-control\" content=\"no-cache, must-revalidate\"/>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\r\n");
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

	TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);

      out.write("\r\n");
      out.write("<title>选择菜单组</title>\r\n");
      out.write("<link rel=\"stylesheet\" href =\"");
      out.print(cssPath );
      out.write("/style.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(cssPath);
      out.write("/bootstrap.css\"/>\r\n");
      out.write("<link href=\"");
      out.print(cssPath);
      out.write("/selectControls.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("<link rel=\"stylesheet\" href =\"");
      out.print(cssPath );
      out.write("/org_select.css\"/>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/common/easyui/jquery.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/common/js/tools.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/system/core/orgselect/orgselect.js\"></script>\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write("/*** 定义全部添加和全部删除的样式  **/\r\n");
      out.write("li.list-group-item:hover{\r\n");
      out.write("\tbackground-color: #f5f5f5;\r\n");
      out.write("\tcursor: pointer;\r\n");
      out.write("}\r\n");
      out.write("li.list-group-item.active, li.list-group-item.active:hover, li.list-group-item.active:focus {\r\n");
      out.write("\tbackground-color: #fff;\r\n");
      out.write("\tcursor: pointer;\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("var selectedColor = \"rgb(0, 51, 255)\";\r\n");
      out.write("var RoleId,RoleName;\r\n");
      out.write("var moduleId = \"");
      out.print(moduleId);
      out.write("\";\r\n");
      out.write("var isSingle = false;//是否是单选择\r\n");
      out.write("var uuid = \"");
      out.print(loginUser.getUuid());
      out.write("\";\r\n");
      out.write("var userId = \"");
      out.print(loginUser.getUserId());
      out.write("\";\r\n");
      out.write("var deptUuid = \"");
      out.print(deptUuid);
      out.write("\";\r\n");
      out.write("objSelectType = '");
      out.print(objSelectType);
      out.write("';\r\n");
      out.write("\r\n");
      out.write("var callBackPara = \"");
      out.print(callBackPara);
      out.write("\";\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("var parentWindowObj = xparent;\r\n");
      out.write("  \r\n");
      out.write("var to_id_field ;\r\n");
      out.write("var to_name_field ;\r\n");
      out.write("\r\n");
      out.write("var single_select = false;\r\n");
      out.write("\r\n");
      out.write("function doInit(){\r\n");
      out.write("\tvar roleRetNameArray = parentWindowObj[\"roleRetNameArray\"];\r\n");
      out.write("\tif (roleRetNameArray && (roleRetNameArray.length == 2 ||  roleRetNameArray.length == 3) ) {\r\n");
      out.write("\t    var roleCntrl = roleRetNameArray[0];\r\n");
      out.write("\t    var roleDescCntrl = roleRetNameArray[1];\r\n");
      out.write("\t    RoleId = parentWindowObj.document.getElementById(roleCntrl);\r\n");
      out.write("\t    RoleName = parentWindowObj.document.getElementById(roleDescCntrl);\r\n");
      out.write("\t  }else {\r\n");
      out.write("\t    RoleId = parentWindowObj.document.getElementById(\"menuGroup\");\r\n");
      out.write("\t    RoleName = parentWindowObj.document.getElementById(\"menuGroupDesc\");\r\n");
      out.write("\t  }\r\n");
      out.write("\t to_id_field = RoleId;\r\n");
      out.write("\t to_name_field = RoleName;\r\n");
      out.write("\t  \r\n");
      out.write("\tvar url = contextPath +  \"/teeMenuGroup/getMenuGroupByDeptUuid.action\";\r\n");
      out.write("\tvar para = {deptUuid:deptUuid};\r\n");
      out.write("\tvar jsonRs = tools.requestJsonRs(url,para);\r\n");
      out.write("\tif(jsonRs.rtState){\r\n");
      out.write("\t\tvar dataList = jsonRs.rtData;\r\n");
      out.write("\t    for(var i = 0; i < dataList.length; i++){\r\n");
      out.write("\t\t\tvar roleId = dataList[i].uuid;\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tvar menuGroupTypeName = \"其他\"\r\n");
      out.write("\t\t\tif(dataList[i].menuGroupType == '01' ){\r\n");
      out.write("\t\t\t\tmenuGroupTypeName = \"执法\";\r\n");
      out.write("\t\t\t}else if(dataList[i].menuGroupType == '02'){\r\n");
      out.write("\t\t\t\tmenuGroupTypeName = \"监督\";\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tvar roleName = dataList[i].menuGroupName + \" 【\" + menuGroupTypeName + \"】\" ;\r\n");
      out.write("\t\t\tif(userId!=\"admin\" && roleId==1){\r\n");
      out.write("\t\t\t\tcontinue;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t    $(\"#dept_item_0\").append(\"<a class='list-group-item active' item_id='\"+roleId+ \"' item_name='\"+roleName+\"'><h6 class='list-group-item-heading'>\"+ roleName+\"</h6></a>\");\r\n");
      out.write("\t\t} \r\n");
      out.write("\t\tif(dataList.length < 0){\r\n");
      out.write("\t\t\t $(\"#dept_item_0\").append(\"<div align='center' class='emptyClass' style='padding-top:5px;'><h6>无相关菜单组！</h6></div>\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t \tload_init();  \r\n");
      out.write("\t \tload_init_item();\r\n");
      out.write("\t    //默认加载角色选中状态\r\n");
      out.write("\t    init_item('dept');\r\n");
      out.write("\t    \r\n");
      out.write("\t\tto_id_field_value = to_id_field.value;\r\n");
      out.write("\t\tto_name_field_value = to_name_field.value;\r\n");
      out.write("\t\tvar dataList = new Array();\r\n");
      out.write("\t\tvar dataNameList  = new Array();\r\n");
      out.write("\t\tif(to_id_field_value != \"\"){\r\n");
      out.write("\t\t\tdataList = to_id_field_value.split(\",\");\r\n");
      out.write("\t\t\tdataNameList = to_name_field_value.split(\",\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t//转数组对象\r\n");
      out.write("\t\tfor(var i =0 ; i <dataList.length ; i++){\r\n");
      out.write("\t\t\tif(dataList[i] && dataList[i] != \"\" ){\r\n");
      out.write("\t\t\t\tvar item = {id:dataList[i] , name:dataNameList[i]};\r\n");
      out.write("\t\t\t\tid_field_array.push(item);\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tdoInitMenuGroup(); \r\n");
      out.write("\t}else{\r\n");
      out.write("\t\talert(jsonRs.rtMsg);\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function doInitMenuGroup(){\r\n");
      out.write("\t for(var i = 0; i < id_field_array.length; i++){\r\n");
      out.write("\t\tvar item = id_field_array[i];\r\n");
      out.write("\t\tvar uuid = item.id;\r\n");
      out.write("\t\tvar deptNameStr = item.name;\r\n");
      out.write("\t\tadd_item(uuid, deptNameStr);\r\n");
      out.write("\t}\r\n");
      out.write("\tjQuery('#dept_item_0 .dept-item').live('click', function(){\r\n");
      out.write("\t\t remove_item(this.getAttribute(\"item_id\"), this.getAttribute(\"item_name\"));\r\n");
      out.write("\t\t jQuery(this).remove();\r\n");
      out.write("\t\t \r\n");
      out.write("\t}); \r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"doInit()\" >\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<div class=\"main-block\" id=\"block_dept\" style=\"display:block;\">\r\n");
      out.write("   <div class=\"right single\" align=\"center\" style=\"margin:0px 10px 10px 10px;left:5px;\" id=\"dept_item\">\r\n");
      out.write("\t\t<div class=\"block-right\" id=\"dept_item_0\">\r\n");
      out.write("\t\t\t<a href='javascript:void(0);' class='list-group-item-header'>模块权限组</a>\r\n");
      out.write("\t\t\t<li href='javascript:void(0);' id=\"addAll\" class=\"list-group-item\" style=\"text-align:center;\">全部添加</li>\r\n");
      out.write("\t\t\t<li href='javascript:void(0);' id=\"removeAll\" class=\"list-group-item\" style=\"text-align:center;\">全部删除</li>\r\n");
      out.write("\t\t</div> \r\n");
      out.write("\t\r\n");
      out.write("    <div id=\"\" align=\"center\" style=\"margin-top:20px;height:40px;\">\r\n");
      out.write("  \t \t<input type=\"button\" class=\"btn btn-info\" value=\"确定\" onclick=\"close_window();\">&nbsp;&nbsp;\r\n");
      out.write("\t</div>\r\n");
      out.write(" </div>\r\n");
      out.write("</div>\r\n");
      out.write("</body></html>");
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
