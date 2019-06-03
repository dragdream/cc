<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<%
TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
int uuid = loginUser.getUuid();
String uname = loginUser.getUserName();
%>
<script type="text/javascript">
var uuid='<%=uuid%>';
var datagrid;
var contextPath = '<%=contextPath%>';
var uname='<%=uname%>';
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/vmeeting/getMyMeetings.action?uuid='+uuid,
		pagination:true,
		singleSelect:true,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		idField:'meetingId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
		          {field:'meetingNumber',title:'会议编号',width:100},
			{field:'meetingName',title:'会议名称',width:100},
			{field:'attendNum',title:'与会人数',width:100,formatter:function(e,rowData){
				return e+"人";
			}},
			{field:'userName',title:'主持人',width:100},
			{field:'startT',title:'开始时间',width:100},
			{field:'endT',title:'结束时间',width:100},
			{field:'_opt',title:'操作',formatter:function(e,rowData){
				
				var opts = [];
				//opts.push("<img title='查看' style='cursor:pointer' onclick=\"view('"+rowData.meetNo+"')\" src='view.gif' />");
				//if(rowData.status==0){//可进入状态
					if(rowData.timeState==0){
						//opts.push("<button title='进入会议' style='cursor:pointer '   onclick=\"execute('"+rowData.meetingId+"')\" >进入会议</button>");
						opts.push("<span style='color:red'>已超时</span>");
					}else{
					opts.push("<a title='进入会议' style='cursor:pointer' onclick=\"execute('"+rowData.meetingId+"')\" >进入会议</a>");
					}
				//}
				return opts.join("&nbsp;&nbsp;");
			}}
		]],
	});
}

function execute(meetingId){
    var url=contextPath+'/vmeeting/getMeetingById.action';
    var json = tools.requestJsonRs(url,{meetingId:meetingId});

         var entermeetxml ="<getserver>server/serverorder.php?machineid=1</getserver>"  //路由地址
		+"<account>guest</account>"  //登陆服务器的账号n
		+"<key>guest</key>"
		+"<confid>"+json.rtData.meetingNumber+"</confid>"  //会议号
		+"<confkey>"+json.rtData.confuserPwd+"</confkey>"  //会议密码
		+"<roomtype>0</roomtype>"  //会议是类型  默认0
		+"<ssl>0</ssl>"           //是否加密 默认0
		+"<ctrltype>0</ctrltype>"    //控制类型  默认0
		+"<url>http://<%=TeeSysProps.getString("VMT_IP")%>:<%=TeeSysProps.getString("VMT_PORT")%></url>"       //服务器地址
		+"<updateurl>/autodownload/autoupdate/autoupdate.xml</updateurl>"  //智能升级地址
		+"<ContextPath>/web/</ContextPath>"  //固定web
		+"<MasterServer><%=TeeSysProps.getString("VMT_IP")%></MasterServer>"  //主服务器地址
		+"<MasterPort><%=TeeSysProps.getString("VMT_PORT")%></MasterPort>"    //主服务器断开
		+"<host><%=TeeSysProps.getString("VMT_IP")%></host>"	 	 //host地址 只有在走代理的时候才使用
     +"<urlinfo>http://www.baidu.com</urlinfo>"//传入需要url（专为东师理想定制）
     +"<conftype>0</conftype>";  //专为东师理想定制会议界面参数：0会议模式 1专家指导 2教学研讨 */
	 entermeetxml = tools.encode64(entermeetxml)+'['+uname+']';
 	//alert(entermeetxml);
   document.location="conf://"+entermeetxml;   
}
function login(url){
	window.open(url);
}

function view(meetNo){
	window.location = "detail.jsp?meetNo="+meetNo;
}

//根据条件查询
function doSearch(){
  var queryParams=tools.formToJson($("#form1"));
	  datagrid.datagrid('options').queryParams=queryParams; 
	  datagrid.datagrid("reload");
	  //$('#datagrid').modal('hide'); 
	 
	  
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class="clearfix" style="padding: 5px;">
	<form  method="post" name="form1" id="form1">
	 <table>
	  <tr>
	    <td class="TableData TableBG">会议编号：</td>
	    <td nowrap class="TableData" style="width: 180px;">
	     <input type="text" id="meetingNumber" name="meetingNumber" style="height:25px;"/>
	    </td>
	     <td class="TableData TableBG">会议名称：</td>
	    <td nowrap class="TableData" style="width: 180px;">
	     <input type="text" id="meetingName" name="meetingName" style="height:25px;"/>
		</td>
	    <td>
	     <input style="width: 45px;height: 25px;" type="button" class="btn-win-white" onclick="doSearch()" value="查询" />
	    </td>
	</tr>
	</table>
 </form>
	</div>
</body>
</html>