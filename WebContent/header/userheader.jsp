<%@page import="com.tianee.oa.core.org.bean.TeeUserRole"%>
<%@page import="com.tianee.oa.core.org.bean.TeeDepartment"%>
<%@page import="com.tianee.oa.oaconst.TeeConst"%>
<%@page import="com.tianee.webframe.util.date.TeeDateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@page import="com.tianee.oa.core.org.service.TeePersonService"%>
<%@page import="java.util.Date"%>
<%@page import="com.tianee.webframe.util.date.TeeDateUtil"%>
<%@ page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@page import="com.tianee.oa.oaconst.TeeConst"%>
<%
	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	boolean isAdminPriv = TeePersonService.checkIsAdminPriv(person);
	boolean isSuperAdmin = TeePersonService.checkIsSuperAdmin(person,"");
	String isAdmin = person.getIsAdmin();
    
	Date date = person.getLastPassTime();
	String lastPassTime = "";
	if(date != null){
		lastPassTime = TeeDateUtil.getDateTimeStr(date);
	}
	int loginPersonId = person.getUuid();
	
	
	
	
	String SEC_INIT_PASS = (String) request.getSession().getAttribute("SEC_INIT_PASS") == null ? "0" : (String) request.getSession().getAttribute("SEC_INIT_PASS");//初始密码
	String SEC_PASS_FLAG = TeeStringUtil.getString( (String) request.getSession().getAttribute("SEC_PASS_FLAG"), "0");//是否过期
	String SEC_PASS_TIME = TeeStringUtil.getString((String) request.getSession().getAttribute("SEC_PASS_TIME"), "0");//过期时间
	String SEC_SHOW_IP = TeeStringUtil.getString((String) request.getSession().getAttribute("SEC_SHOW_IP"), "0");//是否显示IP
	String SEC_ON_STATUS = TeeStringUtil.getString((String) request.getSession().getAttribute("SEC_ON_STATUS"), "0");//记录在线状态
	
	int AVATAR_UPLOAD = TeeStringUtil.getInteger(request.getSession().getAttribute("AVATAR_UPLOAD"), 0 );//是否允许上传图象
	int AVATAR_WIDTH =  TeeStringUtil.getInteger(request.getSession().getAttribute("AVATAR_WIDTH"), 0 );//图象宽度
	int  AVATAR_HEIGHT = TeeStringUtil.getInteger(request.getSession().getAttribute("AVATAR_HEIGHT"),0);//图象高度
  
	String userName = person.getUserName();
	String userId = person.getUserId();
	int userSid = person.getUuid();
	TeeDepartment dept = person.getDept();
	String currUserDeptName  = "";
	String currUserRoleName = "";
	TeeUserRole role = person.getUserRole();
	if(dept != null){
		currUserDeptName = dept.getDeptName();
	}
	if(role != null){
		currUserRoleName = role.getRoleName();
	}
	String avatar = person.getAvatar();
	//用于判断用户性别
	String avatar_default = "";
	if(("0".equals(person.getSex())) && TeeUtility.isNullorEmpty(person.getAvatar())){
		avatar_default = "0";  //0代表男
	}else if(("1".equals(person.getSex())) && TeeUtility.isNullorEmpty(person.getAvatar())){
		avatar_default = "1";  //1代表女
	}
%>
<script type="text/javascript">

/** 变量定义 **/
var isAdminPriv = '<%=isAdminPriv%>';
var isSuperAdmin = '<%=isSuperAdmin%>';
var loginPersonId = <%=loginPersonId%>;
var lastPassTime = '<%=lastPassTime%>';

var SEC_SHOW_IP =  '<%=SEC_SHOW_IP%>';
var SEC_ON_STATUS =  '<%=SEC_ON_STATUS%>';
var AVATAR_UPLOAD =  '<%=AVATAR_UPLOAD%>';
var AVATAR_WIDTH =  '<%=AVATAR_WIDTH%>';
var AVATAR_HEIGHT =  '<%=AVATAR_HEIGHT%>';
var userId = "<%=userId%>";
var userName = "<%=userName%>";
var avatar = "<%=avatar%>";
var avatar_default = "<%=avatar_default%>";
</script>