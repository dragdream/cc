<%@page import="com.tianee.webframe.util.str.TeeJsonUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="com.tianee.webframe.util.auth.TeeAuthUtil"%>
<%@ page import="com.tianee.webframe.util.db.*"%>
<%@ page import="com.tianee.webframe.util.str.TeeJsonUtil"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.net.HttpURLConnection"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp"%>
<title><%=ieTitle%></title>
<script>
function updateSeries(){
	var pwd = prompt("请输入系统管理员密码:","");
	if(pwd!=null){
		var json = tools.requestJsonRs(contextPath+"/systemAction/updateSeries.action",{pwd:pwd,series:$("#series").val()});
		if(json.rtState){
			$.MsgBox.Alert_auto("更新成功");
			window.location.reload();
		}else{
			$.MsgBox.Alert_auto("密码输入有误");
		}
	}
}

function updateUnitName(){
	var pwd = prompt("请输入系统管理员密码:","");
	if(pwd!=null){
		var json = tools.requestJsonRs(contextPath+"/systemAction/updateUnitName.action",{pwd:pwd,unitName:$("#unitName").val()});
		if(json.rtState){
			$.MsgBox.Alert_auto("更新成功");
			window.location.reload();
		}else{
			$.MsgBox.Alert_auto("密码输入有误");
		}
	}
}

function updateAuthInfo(){
	var json = tools.requestJsonRs(contextPath+"/systemAction/updateUnitName.action",{pwd:pwd,unitName:$("#unitName").val()});
	if(json.rtState){
		$.MsgBox.Alert_auto("更新成功");
		window.location.reload();
	}else{
		$.MsgBox.Alert_auto("密码输入有误");
	}
}

function reloadAuthInfo(){
	var pwd = prompt("请输入系统管理员密码:","");
	if(pwd!=null){
		var json = tools.requestJsonRs(contextPath+"/systemAction/reloadAuthInfo.action",{pwd:pwd});
		if(json.rtState){
			$.MsgBox.Alert_auto("更新成功");
			window.location.reload();
		}else{
			$.MsgBox.Alert_auto("密码输入有误");
		}
	}
}
</script>
</head>
<% 
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
	
	
	
%>
<body style="padding-left: 10px;padding-right: 10px;">
    <div id="toolbar" class="topbar clearfix">
	   <img class="title_img" src="/system/core/system/imgs/icon_xtxxsz.png" alt="">
	   &nbsp;<span class="title">系统信息设置</span>	   
	</div>

	<table class="TableBlock_page" width="100%" align="center">
		<tr>
			<td colspan="2" ><img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		    <b style="color: #0050aa">系统信息</b></td>
		</tr>
		<%
			if(!"1".equals(TeeSysProps.getString("NO_LOGO"))){
				%>
				<tr>
					<td style="text-indent: 15px;">软件名称：</td>
					<td>中腾OA协同办公智能管理平台</td>
				</tr>
				<tr>
					<td style="text-indent: 15px;">版权所有：</td>
					<td>北京中安腾鹏科技有限公司</td>
				</tr>
				<%
			}
		%>
		<tr>
			<td style="text-indent: 15px;">系统版本：</td>
			<td><%=TeeSysProps.getString("VERSION") %>&nbsp;&nbsp;
			<%
				if(inputline==null){
					%>
					<span style="color:gray">网络错误</span>
					<%
				}else if(TeeSysProps.getString("VERSION").compareTo(versionInfo.get("Server"))<0){
					%>
					<span style="color:red;cursor:pointer" onclick="window.open('http://www.zatp.com.cn/Update_<%=versionInfo.get("Server") %>.exe');">有新版本，请更新</span>
					<%
				}else{
					%>
					<span style="color:green">无可用更新</span>
					<%
				}
			%>
			&nbsp;&nbsp;<a href="http://www.zatp.com.cn/versiondesc.html?v=<%=System.currentTimeMillis() %>" target="_blank">历史版本</a>
			</td>
		</tr>
		<tr>
			<td style="text-indent: 15px;">版本号：</td>
			<td><%=TeeSysProps.getString("VERSION_NO") %></td>
		</tr>
		<tr>
			<td style="text-indent: 15px;">操作系统：</td>
			<td><%=System.getProperty("os.name") %>&nbsp;&nbsp;<%=System.getProperty("os.arch") %>&nbsp;&nbsp;版本：<%=System.getProperty("os.version") %></td>
		</tr>
		<tr>
			<td style="text-indent: 15px;">Tomcat所在目录：</td>
			<td><%=System.getProperty("user.dir")%></td>
		</tr>
		<tr>
			<td style="text-indent: 15px;">JRE版本：</td>
			<td><%=System.getProperty("java.version")%></td>
		</tr>
		<tr>
			<td style="text-indent: 15px;">JRE所在目录：</td>
			<td><%=System.getProperty("java.home")%></td>
		</tr>
		<tr>
			<td style="text-indent: 15px;">组织机构名称：</td>
			<td>
				<input style="width:280px" type="text" id="unitName" name="unitName" value="<%=TeeAuthUtil.getUnitName() %>"/>&nbsp;<a href="javascript:void(0)" onclick="updateUnitName()">更新</a>
			</td>
		</tr>
		<tr>
			<td style="text-indent: 15px;">机器码：</td>
			<td><%=TeeAuthUtil.getMachineCode()%></td>
		</tr>
		<tr>
			<td style="text-indent: 15px;">软件序列号：</td>
			<td>
				<input type="text" id="series" name="series" value="<%=TeeSysProps.getString("SERIAL")%>"/>&nbsp;<a href="javascript:void(0)" onclick="updateSeries()">更新</a>
			</td>
		</tr>
		<tr>
			<td style="text-indent: 15px;">用户信息：</td>
			<td><%=TeeAuthUtil.getUserInfosDesc() %></td>
		</tr>
		<tr>
			<td style="text-indent: 15px;">重新加载授权信息：</td>
			<td>
				<a href="javascript:void(0)" onclick="reloadAuthInfo()">刷新</a>
			</td>
		</tr>
		<tr>
			<td colspan="2"><img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		    <b style="color: #0050aa">正式授权信息</b><span style="color: red;">（注：正式授权信息，需校验组织机构名称、机器码和软件序列号，务必与授权文件中的信息一致）</span></td>
		</tr>
		<tr>
			<td style="text-indent: 15px;">授权单位：</td>
			<td><%=registInfo==null?"":new String(registInfo.get("company").getBytes(),"UTF-8")%></td>
		</tr>
		<tr>
			<td style="text-indent: 15px;">授权机器码：</td>
			<td><%=registInfo==null?"":registInfo.get("machineCode")%></td>
		</tr>
		<tr>
			<td style="text-indent: 15px;">授权开始日期：</td>
			<td><%=registInfo==null?"":registInfo.get("beginTime")%></td>
		</tr>
		<tr>
			<td style="text-indent: 15px;">授权结束日期：</td>
			<td><%=registInfo==null?"":registInfo.get("endTime")%></td>
		</tr>
		<tr>
			<td style="text-indent: 15px;">移动版终端限制：</td>
			<td><%=registInfo==null?"30":registInfo.get("imLimit")%></td>
		</tr>
		<tr>
			<td style="text-indent: 15px;">PC端终端限制：</td>
			<td><%=registInfo==null?"30":registInfo.get("pcLimit")%></td>
		</tr>
		<tr>
			<td style="text-indent: 15px;">OA用户数限制：</td>
			<td><%=registInfo==null?"30":registInfo.get("personLimit")%></td>
		</tr>
		<tr>
			<td style="text-indent: 15px;">上传注册文件：</td>
			<td style="text-align:left">
				<form action="<%=contextPath %>/registAuth/uploadRegistAuthFile.action" method="post" enctype="multipart/form-data">
					<input type="file" name="file" />
					<input type="submit" value="提交" class="btn-win-white"/>
				</form>
			</td>
		</tr>
		<tr>
			<td style="text-indent: 15px;">延期授权：</td>
			<td style="text-align:left">
				<button class="btn-win-white" onclick="window.location = 'auth_info_delay.jsp'"><b>查看或提交延期授权文件</b></button>
			</td>
		</tr>
	</table>

</body>
</html>
