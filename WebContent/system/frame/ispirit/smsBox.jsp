<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.general.model.TeeSmsModel" %>
<%@ page import="com.tianee.webframe.util.str.TeeJsonUtil" %>
<html>
<head>
<%@ include file="/header/smsHeader.jsp" %>
<%
	List<TeeSmsModel> list1 = new ArrayList<TeeSmsModel>();
	String ids = "";
	if(request.getSession().getAttribute(person.getUserId())!=null){
		list1 = (List<TeeSmsModel>)request.getSession().getAttribute(person.getUserId());
		
		for(TeeSmsModel model : list1){
			//ids+=model.getSms_id()+",";
		}
		if(ids.endsWith(",")){
			ids=ids.substring(0, ids.length()-1);
		}
	}
	String smsList = TeeJsonUtil.toJson(list1);
%>
<title>消息盒子</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./style/smsBox.css" />
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="./js/smsBox.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>

<script type="text/javascript">
var bSmsPriv = true;
var smsList = <%=smsList%>;
var loginUser = {seqId:"<%=person.getUuid()%>", userName:"<%=person.getUserName()%>"};
try {
	if(window.external && typeof(window.external.OA_SMS) != 'undefined') {
	  window.external.OA_SMS(485, 412, "SET_SIZE");
	  window.external.OA_SMS(document.title, "", "NAV_TITLE");
	}
} catch (e) {
  
} finally {
	//markRead(seqId);
}

function doInit() {
	var url = contextPath + "/sms/updateFlag.action?ids=<%=ids%>";
	var para =  {};
	var jsonRs = tools.requestJsonRs(url,para);
	
}
function markRead(seqId){

  jQuery.ajax({
    async: false,
    type: 'POST',
    url: "<%=contextPath %>/sms/resetFlag.action?",
    data: 'seqId= ' + seqId,
    success: function(data){
    }
 });
  
}

</script>
<title>短信箱</title>
</head>
<body onload="doInit()">
<div class="center">
 <div class="center-left">
 <div class="center-group">
 <a href="javascript:void(0);" id="group_by_name" class="active" hidefocus="hidefocus">按姓名分组</a>
 <a href="javascript:void(0);" id="group_by_type" class="" hidefocus="hidefocus">按类型分组</a>
 </div>
 <div id="smsbox_scroll_up"></div>
 <div id="smsbox_list">
 <div id="smsbox_list_container" class="list-container"></div>
 </div>
 <div id="smsbox_scroll_down"></div>
 <div id="smsbox_op_all">
 <a href="javascript:void(0);" id="smsbox_read_all" hidefocus="hidefocus">全部已阅</a>
 <a href="javascript:void(0);" id="smsbox_detail_all" hidefocus="hidefocus">全部详情</a>
 <a href="javascript:void(0);" id="smsbox_delete_all" hidefocus="hidefocus">全部删除</a>
 </div>
 </div>
 <div class="center-right">
 <div class="center-toolbar">
 <a href="javascript:void(0);" id="smsbox_toolbar_read" hidefocus="hidefocus" title="已阅以下短信">已阅</a>
 <a href="javascript:void(0);" id="smsbox_toolbar_detail" hidefocus="hidefocus" title="查看以下短信详情">详情</a>
 <a href="javascript:void(0);" id="smsbox_toolbar_delete" hidefocus="hidefocus" title="删除以下短信">删除</a>
 </div>
 <div id="smsbox_msg_container" class="center-chat"></div>
 <div class="center-reply" id="reply">
 <textarea id="smsbox_textarea"></textarea>
 <a id="smsbox_send_msg" href="javascript:void(0);" hidefocus="hidefocus">发送</a>
 </div>
 </div>
 <div id="smsbox_tips" class="center-tips"></div>
 <div id="no_sms">
 <div class="no-msg">
 <div class="close-tips">本窗口 <span id="smsbox_close_countdown">5</span> 秒后自动关闭</div>
 <a class="btn-white-big" href="javascript:void(0);" onclick="send_sms('', '');" hidefocus="hidefocus">写短信</a>&nbsp;&nbsp;
 <a class="btn-white-big" href="javascript:void(0);" onclick="close_window();" hidefocus="hidefocus">关闭</a>
 </div>
</div>
</div>
</body> 
</html>