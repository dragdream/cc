<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="org.jdom.input.SAXBuilder"%>
<%@page import="org.jdom.Element"%>
<%@page import="org.jdom.Document"%>
<%@ page import="com.tianee.oa.core.partthree.util.TeePartThreeUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
    int orgId=TeeStringUtil.getInteger(request.getParameter("orgId"), 0);
	String deptId = request.getParameter("deptId") == null ? "" : request.getParameter("deptId");
	String deptName = request.getParameter("deptName") == null ? "" : request.getParameter("deptName");
	if("undefined".equals(deptName)){
		deptName = "";
	}
	
	//读取ukcode
	 File file=new File(TeeSysProps.getRootPath()+"/system/ukey/auth_code.xml");
     FileInputStream  in=new FileInputStream(file);
     SAXBuilder builder = new SAXBuilder();
	 Document doc = null;
	try {
		doc = builder.build(in);
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	Element root = doc.getRootElement();
	String UkCode=root.getChild("authcode").getText();
	
	
	
	TeePerson  loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	//系统管理员权限
	boolean adminPriv=TeePartThreeUtil.checkHasPriv(loginUser, "SYS_USER_MANAGE");
	//系统安全员权限
	boolean saferPriv=TeePartThreeUtil.checkHasPriv(loginUser, "SYS_USER_PRIV");
%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>
	<title>人员管理</title>

	<script type="text/javascript" charset="UTF-8">
	var adminPriv=<%=adminPriv %>;//系统管理员权限
	var saferPriv=<%=saferPriv %>;//系统安全员权限
	var orgId=<%=orgId %>;
	var UkCode="<%=UkCode%>";//UkCode
	var deptId = '<%=deptId%>';
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	var title ="";
	var usbKey = "<%=TeeSysProps.getString("USB_KEY")%>";
	
// 	$(window).resize(function () {
// 	  $('#table-bootstrap').bootstrapTable('resetView', {
// 	    height: getHeight()
// 	  });
// 	});

// 	function getHeight() {
// 	  return $(window).height()-40;
// 	}

	
	
	
	function getIdSelections() {
        return $.map($('#datagrid').datagrid('getSelections'), function (row) {
            return row.uuid;
        });
    }
		
	$(function() {
		
		
		var column=[
		  			{field:'userId',title:'用户名',width:150,	formatter : function(value, rowData, rowIndex) {
						if(rowData.notLogin && rowData.notLogin == '1'){
							return "<font color='gray'> " + value + "</font>";
						}else if(rowData.passwordIsNUll == '1'){
							return "<font color='red'> " + value + "</font>";
						}else{
							return value;
						}
						
						
					}},
		  			
		  			{field:'userName',title:'用户姓名',width:200,formatter : function(value, rowData, rowIndex) {
						if(rowData.notLogin && rowData.notLogin == '1'){
							return "<font color='gray'> " + value + "</font>";
						}else if(rowData.passwordIsNUll == '1'){
							return "<font color='red'> " + value + "</font>";
						}else{
							return value;
						}
					}},
		  			{field:'deptIdName',title:'部门',width:200,formatter : function(value, rowData, rowIndex) {
						value = value==undefined?"":value;
						if(rowData.notLogin && rowData.notLogin == '1'){
							return "<font color='gray'> " + value + "</font>";
						}else if(rowData.passwordIsNUll == '1'){
							return "<font color='red'> " + value + "</font>";
						}else{
							return value;
						}
					}},
					{field : 'userRoleStrName',title : '角色',width : 100,formatter : function(value, rowData, rowIndex) {
							if(rowData.notLogin && rowData.notLogin == '1'){
								return "<font color='gray'> " + value + "</font>";
							}else if(rowData.passwordIsNUll == '1'){
								return "<font color='red'> " + value + "</font>";
							}else{
								return value;
							}
						}},
						 {field : 'sex',title : '性别',width : 40,formatter : function(value, rowData, rowIndex) {
								if(value == '1'){
									value =  "女";
								}else{
									value = "男";
								}
								if(rowData.notLogin && rowData.notLogin == '1'){
									return "<font color='gray'> " + value + "</font>";
								}else if(rowData.passwordIsNUll == '1'){
									return "<font color='red'> " + value + "</font>";
								}else{
									return value;
								}			
							}} , 
							{field : 'postPriv',title : '管理范围',width : 100,formatter : function(value, rowData, rowIndex) {
								if( value == '1'){
									value =  "全体";
								}else if(value == '2'){
									value = "指定部门";
								}else if(value == '3'){
									value = "本部门及下级所有部门";
								}else{
									value = "本部门";
								}				
								if(rowData.notLogin && rowData.notLogin == '1'){
									return "<font color='gray'> " + value + "</font>";
								}else if(rowData.passwordIsNUll == '1'){
									return "<font color='red'> " + value + "</font>";
								}else{
									return value;
								}
							
							}}
							
		  		];
		
		//获取当前登录人员的管理范围
		var json = tools.requestJsonRs(contextPath+"/personManager/getPostDeptIds.action");
		var postIds = ","+json.rtData+",";
		if(postIds==",0," || postIds.indexOf(deptId)!=-1){//有管理权限
			column.push(
			{field : '_optmanage',title : '操作',width : 150,formatter : function(value, rowData, rowIndex) {
				var html="";
				if(adminPriv||saferPriv){//系统管理员  或者  系统安全员
					html="<a href='#' onclick='toAddUpdatePerson(\"" +rowData.uuid + "\");'> 编辑 </a>"; 	
				}
				if(adminPriv){//系统管理员
					if(rowData.keySN!=null&&rowData.keySN!="undefined"&&rowData.keySN!="null"&&usbKey=="1"){//绑定过uk
						html+="&nbsp;&nbsp;&nbsp;<a href='#' onclick='unbund(\"" +rowData.uuid + "\");'>解绑Ukey</a>";
					}else if(usbKey=="1"){
						html+="&nbsp;&nbsp;&nbsp;<a href='#' onclick='bund(\"" +rowData.uuid + "\",\""+rowData.userId+"\");'>绑定Ukey</a>";
					}
				}
				
				return  html;
			}});
		}else{//无管理权限
			$("#toolbar").html("");
		}
		
		
		datagrid = $('#datagrid').datagrid({
			url: contextPath + '/personManager/getPersonList.action?sort=userNo&isAdmin=1&deptId=' + deptId,
		    pagination:true,
		    singleSelect:false,
		    view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		    toolbar:'#toolbar',//工具条对象
	    	checkbox:true,
		    border:false,
		    idField:'sid',//主键列
		    fitColumns:true,//列是否进行自动宽度适应
		    columns:[column]
		  	});
	});
	
//绑定
function bund(uuid,userId){
	var res = AuthIE.Open("<dogscope/>", UkCode);
	if(res==0){
		AuthIE.GetDogID();
		var dogid = AuthIE.DogIdStr;
		//判断dogid是否被其他人占用
		var url=contextPath+"/personManager/checkKeySNIsExist.action";
		var json=tools.requestJsonRs(url,{keySN:dogid});
		if(json.rtState){
			var data=json.rtData;
			if(data==1){//设备被别人占用   不可用
				$.MsgBox.Alert_auto("该设备已被其他用户绑定！");
				return;
			}else{//设备没有被其他人占用    可用
				 //判读即将被绑定的人员是否已经与其他的设备绑定了
				 var url1=contextPath+"/personManager/checkUserIsBound.action";
			     var json1=tools.requestJsonRs(url1,{uuid:uuid});
				 if(json1.rtState){
					 var data1=json1.rtData;
					 if(data1==1){//已经绑定过其他设备  不能再绑定了
						 $.MsgBox.Alert_auto("当前用户已经绑定过其他设备！");
					     return;
					 }else{//没有绑定过其他设备  可以进行绑定
						 //用户注册  将用户登录名写入设备   统一Ukey密码设置为12345678
						 AuthIE.RegisterUser(userId, "12345678");
						 AuthIE.GetUserName();
					     var name = AuthIE.UserNameStr;	 	
					     //alert(name); 
					     //将keySN存到用户数据中
					     var url2=contextPath+"/personManager/boundUkey.action";
					     var json2=tools.requestJsonRs(url2,{uuid:uuid,keySN:dogid});
					     if(json2.rtState){
					    	 $.MsgBox.Alert_auto("绑定成功！");
					    	 datagrid.datagrid("reload");
					     }else{
					    	 $.MsgBox.Alert_auto("绑定出错！");
						     return;
					     }
					 }
				 }
			}
		}else{
			$.MsgBox.Alert_auto("查询出错！");
			return;
		}
	}else{
		$.MsgBox.Alert_auto("无法连接到设备！");
		return;
	}
	AuthIE.Close();
}
//解绑
function unbund(uuid){
	 $.MsgBox.Confirm ("提示", "是否确认解除绑定？", function(){
		 var res = AuthIE.Open("<dogscope/>", UkCode);
		 if(res==0){
			 AuthIE.GetDogID();
			 var dogid = AuthIE.DogIdStr;
			 //判断当前连接到的设备是不是与用户匹配
			 var url=contextPath+"/personManager/checkUserAndUkeyIsMatch.action";
			 var json=tools.requestJsonRs(url,{uuid:uuid,keySN:dogid});
			 if(json.rtState){//匹配
				 //解除绑定
				 //用户注册  将用户名清空       统一Ukey密码设置为12345678
				 AuthIE.RegisterUser(" ", "12345678");
				 AuthIE.GetUserName();
			     var name = AuthIE.UserNameStr;	 	
			     //alert(name); 
			     var url1=contextPath+"/personManager/unBoundUkey.action";
			     var json1=tools.requestJsonRs(url1,{uuid:uuid});
				 if(json1.rtState){
					 $.MsgBox.Alert_auto("解绑成功！");
					 datagrid.datagrid("reload");
				 }else{
					 $.MsgBox.Alert_auto("解绑出错！");
				     return; 
				 }
				 
				 
			 }else{//不匹配
				 $.MsgBox.Alert_auto("该设备与解绑用户不匹配！");
			     return;
			 }
		 }else{
			$.MsgBox.Alert_auto("无法连接到设备！");
		    return; 
		 }
		 
		 AuthIE.Close();
	  });
}




function toAddUpdatePersonList(deptId){
	var deptName = '<%=deptName%>';
	var url = "<%=contextPath %>/gzpt/adminUser/addupdate.jsp?deptId=" + deptId + "&deptName=" + encodeURIComponent(deptName);
	//openWindow(url,"addupdatePerson",870,540);
	window.parent.changePage(url);
}
/**
 * 批量删除人员
 */
function deletePerson(){
	
	var selections = getIdSelections();
	if(selections.length==0){
		$.MsgBox.Alert_auto("至少选择一项！");
		return ;
	}
	$.MsgBox.Confirm ("提示", "确定要将所选中人员更改为离职状态吗？",function(){
		var url = contextPath +  "/personManager/updateDelPersonByUuids.action?uuids=" + selections;
		var para = {};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto("操作成功！",function(){
		    if(deptId==0){//顶层节点
		    	parent.refreshTargetNode(orgId);
		    }else{
		    	parent.refreshTargetNode(deptId+";dept");
		    }
		    
			
			window.location.reload();
				
			});
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	});
}
/**
 * easyUI 清空密码
 * @param uuid
 */
function easyUIClearPassword(){
	var selections = getIdSelections();
	if(selections.length==0){
		$.MsgBox.Alert_auto("至少选择一项！");
		return ;
	}
	$.MsgBox.Confirm ("提示", "确定要清空密码？",function(){
		var url = contextPath +  "/personManager/clearPassword.action?uuids=" + selections;
		var para = {};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto(jsonRs.rtMsg,function(){
			window.location.reload();
				
			});
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	});
}

function toAddUpdatePerson(personUuid){
	var url = contextPath +  "/gzpt/adminUser/addupdate.jsp?uuid=" + personUuid ;
	//openWindow(url,"addupdatePerson",870,540);
	window.parent.changePage(url);
}

	</script>
</head>
<body onload="" style="padding: 10px 10px 10px 10px;">
<%
	if("1".equals(TeeSysProps.getString("USB_KEY"))){
		%>
		<object id="AuthIE" name="AuthIE" width="0px" height="0px"
		   codebase="/system/ukey/DogAuth.CAB"
		   classid="CLSID:05C384B0-F45D-46DB-9055-C72DC76176E3">
		</object>
		<%
	}
%>

	


<table id="datagrid" fit="true"></table>
	  <div id="toolbar" class=" clearfix" style="padding-top: 5px;padding-bottom: 5px;">
	   
	     <div style="padding-top: 5px;padding-bottom: 10px;">
		   <h5 style="font-size: 14px;">部门（<%=deptName %>）－ 用户管理，说明：密码为空用户显示为红色，禁止登录用户显示为灰色</h5>
	     </div>
	    
	    <%  
			   if(adminPriv){//没有权限
				   %>
				   
				   <input type="button" class="btn-win-white"   onclick="toAddUpdatePersonList('<%=deptId %>');" value="添加人员"/>
	   	           <input type="button" class="btn-win-white"  onclick="easyUIClearPassword()" value="清空密码">
	   	           <!-- <input type="button" class="btn-win-white"  onclick="deletePerson()" value="离职">  -->
				   <%
			   }
		   
		%>
	  	
	  </div>


</body>
</html>