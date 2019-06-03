<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.partthree.util.TeePartThreeUtil" %>

<%
	String uuid = request.getParameter("uuid") == null ? "0" : request.getParameter("uuid");
	String deptId = request.getParameter("deptId") == null? "": request.getParameter("deptId");
	String deptName = request.getParameter("deptName") == null ? "" : request.getParameter("deptName");

	TeePerson  loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
    boolean adminPriv=TeePartThreeUtil.checkHasPriv(loginUser, "SYS_USER_MANAGE");
    boolean saferPriv=TeePartThreeUtil.checkHasPriv(loginUser, "SYS_USER_PRIV");
    
    if(!adminPriv&&!saferPriv){
    	response.sendRedirect("/system/core/system/partthree/error.jsp");
    }

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/upload.jsp" %>
<%@ include file="/header/userheader.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<title>人员管理</title>

<style type="text/css">
.imgMiddle{
	float:left;
	margin-top:5px;
}
.imgMiddleSpan{
	float:left;
	margin-top:4px;
}

</style>
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>


<script type="text/javascript">

var deptId = '<%=deptId%>';
var uuid = '<%=uuid%>';	
var deptName = '<%=deptName%>';
var keySN="";//绑定ukey的code
var oldUserId="";
var swfUploadObj;
function doInit(){
	//单附件简单上传组件
	new TeeSingleUpload({
		uploadBtn:"uploadBtn",
		callback:function(json){//回调函数，json.rtData
			var list=json.rtData;
		    if(list!=null && list.length>0){
		    	var data=list[0];
		    	//$("#attachName").val(data.fileName);
		    	$("#attachId").val(data.sid);
				var html="<img src='<%=contextPath %>/attachmentController/downFile.action?id="+data.sid+"' />";
		    	$("#uploadDiv").html(html);
		    }
		},
		post_params:{model:"workFlow"}//后台传入值，model为模块标志
	});

		//添加例子一
		//获取角色
		getAllAttendConfig();
		getPortalTemplate();
		var isSuperAdmin = false;
		var userRoleStrName = "";
		var userRoleStr = "";
		if(uuid != "" && uuid != '0'){
			var url = "<%=contextPath%>/personManager/getPersonById.action";
			var para = {uuid:uuid};
			var jsonObj = tools.requestJsonRs(url,para);
			if(jsonObj.rtState){
				var json = jsonObj.rtData;
				keySN=json.keySN;//记录绑定设备   
				oldUserId=json.userId;//记录原来的用户名
				if(json && json.uuid){
					if(json.userId == 'admin'){
					   isSuperAdmin = true;
					    $("#userRoleStr").append("<option value='" + json.userRoleStr + "'>系统管理员<option>");
					   userRoleStrName = json.userRoleStrName;
					}else{
						selectUserPrivList('userRoleStr','1');
						$("#userRoleStr").val(userRoleStr);
					}
					userRoleStr = json.userRoleStr;
				   bindJsonObj2Cntrl(json);
				   if(json.attachId>0){
				    var html="<img src='<%=contextPath %>/attachmentController/downFile.action?id="+json.attachId+"' />";
			    	$("#uploadDiv").html(html);
				   }
				   deptId = json.deptId;
				   deptName = json.deptName;
		           document.getElementById("userRoleStr").value = json.userRoleStr;
		           if(json.userRoleOtherId != ""){//辅助角色
		        	   $("#priv").show();
		           }
		           
		           if(json.deptIdOtherStr != ""){//辅助部门
		        	   $("#dept_other").show();
		           }
		           var postPriv = json.postPriv;
		          // var postDeptStr = json.postDeptStr;
		           //if(postPriv == '2' &&  postDeptStr != ""){
		        	if(postPriv == '2'){   
		       			$("#post_dept").show();
		       		}
		  
					//$("#deptIdName").val(json.deptIdName);
		        	selectViewDept();
				}
			}else{
				$.MsgBox.Alert_auto(jsonObj.rtMsrg);
			}
		}else{
			
			selectUserPrivList('userRoleStr','1');
			$("#deptId").val(deptId);
			$("#deptIdName").val(deptName);
		}
			
		if(isSuperAdmin){
			$("#isNotSuperAdmin").hide();
			$("#isNotSuperAdminRole").append(userRoleStrName);//给角色
			$("#userRoleStr").hide();//隐藏角色
			
		}
}

//清空
function qingkou(){
	$("#attachId").val("0");
	$("#uploadDiv").html("");
}
/**
 * 保存
 */
function doSave(){
	if (check()){
		var url = "<%=contextPath%>/personManager/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			//alert();
			$.MsgBox.Alert_auto(jsonRs.rtMsg,function(){
			window.parent.changePage("<%=contextPath%>/system/core/person/personList.jsp?deptId=" + deptId + "&deptName=" + encodeURIComponent(deptName));
			parent.refreshTargetNode($("#deptId").val()+";dept");
				
			});
			}else{
				 $.MsgBox.Alert_auto(jsonRs.rtMsg);
			}
			//window.opener.location.reload();
	       // window.location.reload();
		}else{
			return false;
		}
}

function check() {
	var checkStatus =  $("#form1").valid();
	if(checkStatus == false ){
		return checkStatus;
	}
	var deptId = $("#deptId");
	if(!deptId.val()){
		$.MsgBox.Alert_auto("部门是必填项！");
		return false ;
	}
	var userRoleUuid = $("#userRoleStr");
	if(!userRoleUuid.val()){
		//$("#userRoleStr").addClass("easyui-combogrid combogrid-f combo-f");
		$.MsgBox.Alert_auto("用户角色是必填项！");
		return false ;
	}
	
	if(uuid>0){//编辑用户 的時候  判断用户是不是绑定了ukey 
		if(keySN!="undefined"&&keySN!="null"&&keySN!=null){//绑定了ukey设备
		     var newUserId=$("#userId").val();
              if(newUserId!=oldUserId){
            	  $.MsgBox.Alert_auto("该用户绑定了Ukey设备，不能更改用户名！");
            	  return false;
              }
		}
	}
	return true;
	
}



function getAllAttendConfig(){
	var url = contextPath+"/TeeAttendConfigController/getConfig.action";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data=json.rtData;
		var html = "<option value=\"0\">请选择</option>";
		for(var i=0;i<data.length;i++){
			html+="<option value=\""+data[i].sid+"\">"+data[i].dutyName+"</option>";
		}
		$("#dutyType").html(html);
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
}

/**
 * 获取有权限的桌面模块
 */
function getPortalTemplate(){
	var url = contextPath+"/teePortalTemplateController/getTemplateList.action?uuid="+uuid+"&deptId="+deptId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data=json.rtData;
		var html="";
		if(data.length>0){
			for(var i=0;i<data.length;i++){
				html+="<option value=\""+data[i].sid+"\">"+data[i].templateName+"</option>";
			}
		}else{
			html = "<option value=\"0\">无</option>";
		}
		$("#desktop").html(html);
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
}

/**
 * 隐藏显示
 */
function setOption(id){
	$('#' + id).toggle();
}

function selectViewDept(){
	if($("#viewPriv").val()=="2"){//指定部门
		$("#view_dept").show();
	}else{
		$("#view_dept").hide();
	}
}
</script>

</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;">
<div id="toolbar" class = "setHeight clearfix" style="margin-bottom: 10px;margin-top: 10px;">
<div class=" fl ">
               <%
					if (TeeUtility.isNullorEmpty(uuid)||uuid=="0") {
				%> 
   <h4 style="font-size: 16px;font-family:MicroSoft YaHei;">
				新增人员
				</h4>
				<%
 					} else {
 				%> 
 				 <h4 style="font-size: 16px;font-family:MicroSoft YaHei;">
				编辑人员
				</h4>
 				<%
 					}
 				%>
</div>
 	<span class="basic_border" style="margin-top: 10px;"></span>
</div>
	<form  method="post" name="form1" id="form1" >
		<input type="hidden" name="isAdmin" value="1"/>
<table class="TableBlock_page" width="90%" align="center">
	<tbody style="<%=adminPriv?"":"display:none"%>">
		   <tr>
		    <td colspan="2" style='vertical-align: middle;font-family: MicroSoft YaHei;font-size: 14px;line-height: 30px;'><img src="<%=contextPath %>/common/zt_webframe/imgs/common_img/icon_yhcx.png" align="absMiddle">&nbsp;&nbsp; <span>用户基本信息</span></td>
		   </tr>
		   <tr>
		    <td style="text-indent: 10px;" nowrap class="TableData" width="120">用户名：<span style=""></span></td>
		    <td nowrap class="TableData">
		        <input style="height: 25px;font-family: MicroSoft YaHei;font-size: 12px;" type="text" id="userId" name="userId" class="BigInput"  required  size="15" newMaxLength="20" onBlur="check_user(this.value)"/>&nbsp;<span id="user_id_msg"></span> 
		    </td>
		   </tr>
		   <tr>
		    <td style="text-indent: 10px;" nowrap class="TableData">用户姓名：</td>
		    <td nowrap class="TableData">
		    <input style="height: 25px;font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="userName" id="userName" size="15" newMaxLength="20" class="easyui-validatebox BigInput"  required/>
		         </td>
		   </tr>
		   <tr style="display:none">
		    <td style="text-indent: 10px;" nowrap class="TableData">执法证号：</td>
		    <td nowrap class="TableData">
		    <input style="height: 25px;font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="performCode" id="performCode" size="15" newMaxLength="20" class="easyui-validatebox BigInput"/>
		    </td>
		   </tr>
		   <tr>
		    <td style="text-indent: 10px;" nowrap class="TableData">部门：</td>
		    <td nowrap class="TableData">
				<input id="deptId" name="deptId"  type="text" style="display:none;" value="<%=deptId %>"/>		
				<input style="height: 25px;font-family: MicroSoft YaHei;font-size: 12px;" id="deptIdName" name="deptIdName"  type="text" class="BigInput" readonly/>	
				<span class='addSpan'>
				    <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectSingleDept(['deptId','deptIdName'],'0')" value="选择"/>
				</span>
				<!-- &nbsp;&nbsp;<a href="javascript:selectDeptOther()">指定其它所属部门</a>  -->
		    </td>
		   </tr>
		
		   <tr id="dept_other" style="display:none;">
		      <td style="text-indent: 10px;" nowrap class="TableData">所属部门：</td>
		      <td class="TableData">
		      
		     <!-- <select multiple="multiple" size="10" id="deptIdOtherStr" style="width:150px;">
		     </select> -->
		       <input type="hidden" name="deptIdOtherStr" id="deptIdOtherStr" value=""/>
		        <textarea cols=30 name="deptIdOtherStrName" id="deptIdOtherStrName" style="font-family: MicroSoft YaHei;font-size: 12px;"  rows=2 class="SmallStatic  BigTextarea" wrap="yes" readonly ></textarea>
		        <span class='addSpan'>
				         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectDept(['deptIdOtherStr','deptIdOtherStrName'],'1')" value="选择"/>
					        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('deptIdOtherStr','deptIdOtherStrName')" value="清空"/>
				 </span>
		      </td>
		   </tr>
		   <tr style="display:none">
		    <td style="text-indent: 10px;" nowrap class="TableData">主角色：</td>
		    <td nowrap class="TableData">
		        <select style="height: 25px;font-family: MicroSoft YaHei;font-size: 12px;" name="userRoleStr"  id="userRoleStr"  class="BigSelect"  >
		     	</select>
		     	<span id="isNotSuperAdminRole" ></span>
		        <span>&nbsp;&nbsp;<a href="javascript:select_priv();">指定辅助角色</a></span>
		      <br>
		    </td>
		   </tr>
		   <tr id="priv" style="display:none;">
		      <td style="text-indent: 10px;" nowrap class="TableData">辅助角色：</td>
		 
		      <td class="TableData">
		      <!-- <select multiple="multiple" size="10" id="privIdStr" style="width:150px;">
		     </select> -->
		    	<input type="hidden" name="userRoleOtherId"  id="userRoleOtherId" value=""/> 
		        <textarea style="font-family: MicroSoft YaHei;font-size: 12px;" cols=30 id="userRoleOtherName" name="userRoleOtherName" rows=2 class="SmallStatic  BigTextarea" wrap="yes" readonly></textarea>
		        <span class='addSpan'>
				         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectRole(['userRoleOtherId','userRoleOtherName'],'','','2')" value="选择"/>
					        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('userRoleOtherId', 'userRoleOtherName')" value="清空"/>
				 </span>
		        <br >（辅助角色仅用于扩展主角色）      </td>
		   </tr>
		    <tr>
		    <td style="text-indent: 10px;" nowrap class="TableData">用户排序号：</td>
		    <td nowrap class="TableData">
		        <input style="height: 25px;font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="userNo" class=" BigInput" size="5"  newMaxLength="4"  value="" required positive_integer="true">&nbsp;
		        <span>（用于同角色用户的排序 ）</span>   </td>
		   </tr>
		   <tr style="display:none">
		    <td style="text-indent: 10px;" nowrap class="TableData">直属上级：</td>
		    <td  class="TableData">
		    	<input id="leaderIds" name="leaderIds" type="hidden"/>
		    	<input type="text" style="height: 25px;font-family: MicroSoft YaHei;font-size: 12px;"  id="leaderNames" class="BigInput" readonly />
		    	 <span class='addSpan'>
				         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectSingleUser(['leaderIds','leaderNames'])" value="选择"/>
					        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('leaderIds','leaderNames')" value="清空"/>
				 </span>
		    </td>
		   </tr>
		    <tr style="display:none">
		    <td style="text-indent: 10px;" nowrap class="TableData">直属下级：</td>
		    <td  class="TableData">
		    	<input id="underlingIds" name="underlingIds" type="hidden"/>
		    	<textarea style="font-family: MicroSoft YaHei;font-size: 12px;" rows="2" cols="30" id="underlingNames" class="BigTextarea readonly" readonly></textarea>
		    	 <span class='addSpan'>
				         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectUser(['underlingIds','underlingNames'])" value="选择"/>
					        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('underlingIds','underlingNames')" value="清空"/>
				 </span>
		    	
		   	</td>
		   </tr>
	</tbody>
	
   
		 <tr <%="4".equals(uuid)?"style='display:none'":"" %>  style="<%=saferPriv?"":"display:none"%>" >
		   <td  colspan="2" style='vertical-align: middle;font-family: MicroSoft YaHei;font-size: 14px;line-height: 30px;'><img src="<%=contextPath %>/common/zt_webframe/imgs/common_img/icon_yhcx.png">&nbsp;&nbsp; <span>用户权限信息</span></td>
		  </tr>
		  
		<tbody id="isNotSuperAdmin" style="<%=saferPriv?"":"display:none"%>">   
		   <tr >  
		   <td style="text-indent: 10px;" nowrap class="TableData" width="120">可见范围：</td>
		   <td nowrap class="TableData">
		       <select style="height: 25px;font-family: MicroSoft YaHei;font-size: 12px;" name="viewPriv"  id="viewPriv" class="BigSelect" OnChange="selectViewDept()">
		       	<option value="1">本单位</option>		       	
		       	<!-- 
		       	<option value="0">请选择</option>
		       	<option value="1">本机构</option>
		       	<option value="3">本部门</option>
		       	<option value="4">本部门及以下部门</option>
		       	<option value="2">指定部门</option>
		       	 -->
		       </select>
		       <br/>
		       （可见范围是指用户在选人、选部门、查看组织机构等信息时，可查看到的指定部门及机构信息。）
		   </td>
		  </tr>
		  <tr id="view_dept" style="display:none;">
		     <td style="text-indent: 10px;" nowrap class="TableData">可见范围（部门/机构）：</td>
		
		     <td class="TableData">
		 	 <input type="hidden" name="viewDeptIds" id="viewDeptIds" value="">
		       <textarea cols=30 name="viewDeptNames" id="viewDeptNames" rows=2 class="SmallStatic   BigTextarea" wrap="yes" readonly ></textarea>
		       <a href="javascript:void(0);" class="orgAdd" onClick="selectDept(['viewDeptIds','viewDeptNames'],'1')">选择</a>
		       <a href="javascript:void(0);" class="orgClear" onClick="clearData('viewDeptIds','viewDeptNames')">清空</a>
		     </td>
		   </tr>
		   
		   
		    <tr >
		  
		   <td style="text-indent: 10px;" nowrap class="TableData" width="120">管理范围：</td>
		   <td nowrap class="TableData">
		       <select style="height: 25px;font-family: MicroSoft YaHei;font-size: 12px;" name="postPriv"  id="postPriv" class="BigSelect" OnChange="selectPostDept()">
		         <option value="4">本单位</option>		         
		         <!-- 
		         <option value="-1">请选择</option>
		         <option value="0">本部门</option>
		         <option value="3">本部门以及下级所有部门</option>
		         <option value="4">本机构</option>
		         <option value="1">全体</option>
		         <option value="2">指定部门</option>
		          -->
		       </select>
		    	
		    	<!-- 	
		    	&nbsp;&nbsp;<a href="javascript:module_priv();">按模块设置管理范围</a>
		      		（ 在管理型模块中起约束作用 ）
		         --> 
		       
		         </td>
		  </tr>
		
		  <tr id="post_dept" style="display:none;">
		     <td style="text-indent: 10px;" nowrap class="TableData">管理范围（部门）：</td>
		
		     <td class="TableData">
		 	 <input type="hidden" name="postDeptStr" id="postDeptStr" value="">
		       <textarea style="height: 25px;" cols=30 name="postDeptStrName" id="postDeptStrName" rows=2 class="SmallStatic" wrap="yes" readonly ></textarea>
		       <a href="javascript:void(0);" class="orgAdd" onClick="selectDept(['postDeptStr','postDeptStrName'],'1')">选择</a>
		       <a href="javascript:void(0);" class="orgClear" onClick="clearData('postDeptStr','postDeptStrName')">清空</a>
		  
		     </td>
		   </tr>
		   
		   
		  
		  <tr style="display:none;">
		   <td style="text-indent: 10px;" nowrap class="TableData">访问控制：</td>
		   <td nowrap class="TableData">
		       <input type="checkbox" name="notLogin" id="notLogin" value="1"><label for="notLogin" style="cursor: hand">禁止登录OA系统</label>&nbsp;
		   	<input type="checkbox" name="notViewUser" id="notViewUser" value="1" ><label for="notViewUser" style="cursor: hand">禁止查看用户列表</label>&nbsp;
		   	<input type="checkbox" name="notWebLogin" id="notWebLogin" value="1" ><label for="notWebLogin" style="cursor: hand">禁止WEB登陆</label>&nbsp;
		   	<input type="checkbox" name="notMobileLogin" id="notMobileLogin" value="1" ><label for="notMobileLogin" style="cursor: hand">禁止移动端登陆</label>&nbsp;
		   	<input type="checkbox" name="notPcLogin" id="notPcLogin" value="1" ><label for="notPcLogin" style="cursor: hand">禁止PC端登陆</label>&nbsp;
		   	<!-- <input type="checkbox" name="notViewTable" id="notViewTable" value="1" ><label for="notViewTable" style="cursor: hand">禁止显示桌面</label>-->
		       <!--<input type="checkbox" name="notSearch" id="notSearch" value="1" ><label for="notSearch" style="cursor: hand">禁止OA搜索</label> -->
		     </td>
		   </tr>
		</tbody>
		  <tr <%="4".equals(uuid)?"style='display:none'":"" %>  style="<%=saferPriv?"":"display:none"%>">
		     <td style="text-indent: 10px;" nowrap class="TableData">菜单组（菜单权限设置）：</td>
		     <td class="TableData">
		    
		      <input type="hidden" name="menuGroupsStr" id="menuGroupsStr" value="">
		       <textarea style="font-family: MicroSoft YaHei;font-size: 12px;" cols=30 name="menuGroupsStrName" id="menuGroupsStrName" rows=2 class="BigTextarea" wrap="yes" readonly ></textarea>
		        <span class='addSpan'>
			         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectMenuGroup(['menuGroupsStr','menuGroupsStrName'],'1')" value="选择"/>
				        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('menuGroupsStr','menuGroupsStrName')" value="清空"/>
			 </span>
		       
		     </td>
		  </tr>
  

   <tr onClick="setOption('option1')"  style="display:none" title="点击展开/收缩选项" >
    <td colspan="2" style='vertical-align: middle;font-family: MicroSoft YaHei;font-size: 14px;line-height: 30px;'><img src="<%=contextPath %>/common/zt_webframe/imgs/common_img/icon_yhcx.png">&nbsp;&nbsp; <span>其它选项</span></td>
   </tr>
   <tbody id="option1" style="display:none">
   <tr style="display: none">
    <td style="text-indent: 10px;" nowrap class="TableData">考勤排班类型：</td>
    <td nowrap class="TableData">
        <select style="height: 25px;font-family: MicroSoft YaHei;font-size: 12px;" name="dutyType" id="dutyType" class="BigSelect">
        </select>
    </td>
   </tr>
   <tr style="display:none;">
    <td style="text-indent: 10px;" nowrap class="TableData">考勤签到方式：</td>
    <td nowrap class="TableData">
        <select style="height: 25px;width:150px; font-family: MicroSoft YaHei;font-size: 12px;" name="signWay" id="signWay" class="BigSelect">
             <option value="1">组合签到</option>
             <option value="2">移动端签到</option>
             <option value="3">网页签到</option>
        </select>
    </td>
   </tr>
   <tr style="display:none;">
    <td style="text-indent: 10px;" nowrap class="TableData">默认桌面模块：</td>
    <td nowrap class="TableData">
        <select style="height: 25px;font-family: MicroSoft YaHei;font-size: 12px;" name="desktop" id="desktop" class="BigSelect">
        </select>
    </td>
   </tr>
   <tr style="display:none;">
    <td style="text-indent: 10px;" nowrap class="TableData">内部邮箱容量：</td>
    <td nowrap class="TableData">
        <input style="height: 25px;font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="emailCapacity"   class="easyui-validatebox BigInput" validType ='integeZero[]' size="5" newMaxLength="11" value="500">&nbsp;MB
        （0为空则表示不限制大小）    </td>
   </tr>
   <tr style="display:none;">
    <td style="text-indent: 10px;" nowrap class="TableData">个人网盘容量：</td>
    <td nowrap class="TableData">
        <input style="height: 25px;font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="folderCapacity"  class=" BigInput" validType ='integeZero[]' size="5" newMaxLength="11" value="500">&nbsp;MB
        （0为空则表示不限制大小 ）   </td>
   </tr>
     <tr style="display:none;">
    <td style="text-indent: 10px;" nowrap class="TableData">是否启用POP3功能：</td>
    <td nowrap class="TableData">
       <input type="checkbox" name="usePops" id="usePops" value="1" ><label for="usePops" style="cursor: hand">是</label>
    </td>
   </tr>
   
 
   <tr id="internet1" style="display:none;">
    <td style="text-indent: 10px;" nowrap class="TableData">Internet邮箱数量：</td>
    <td nowrap class="TableData">
        <input type="text" name="webmailNum"  class="BigInput" validType =''  size="5" newMaxLength="11" value="">&nbsp;个        为空则表示不限制数量    </td>
   </tr>
   <tr id="internet2" style="display:none;">
    <td style="text-indent: 10px;" nowrap class="TableData">每个Internet邮箱容量：</td>
    <td nowrap class="TableData">
        <input type="text" name="webmailCapacity" class="BigInput" validType =''  size="5" newMaxLength="11" value="">&nbsp;MB
        为空则表示不限制大小    </td>
   </tr>
 
   <tr>
    <td style="text-indent: 10px;" nowrap class="TableData">绑定IP地址：</td>
    <td nowrap class="TableData">
      <textarea style="font-family: MicroSoft YaHei;font-size: 12px;" name="bindIp" cols="60" rows="2" id='bindIp' class="BigTextarea" validType='ipValid[]'></textarea><br>
        （为空则该用户不绑定固定的IP地址，绑定多个IP地址用英文逗号(,)隔开<br>也可以绑定IP段，如“192.168.0.60,192.168.0.100-192.168.0.200”<br>表示192.168.0.60或192.168.0.100到192.168.0.200这个范围内都可以登录 ）
    </td>
   </tr>
   <tr>
    <td style="text-indent: 10px;" nowrap class="TableData">绑定MAC地址：</td>
    <td  class="TableData"> 
      <textarea style="font-family: MicroSoft YaHei;font-size: 12px;" name="bindMac" cols="60" rows="3" id='bindMac' class="easyui-validatebox BigTextarea" validType='macValid[]'></textarea><br>
     （ 为空则该用户不绑定固定的MAC地址，绑定多个MAC地址用英文逗号(,)隔开，<br/>比如：A4:DB:30:5B:ED:40,A4:DB:30:5B:ED:41）
    </td>
   </tr>
   <tr>
    <td style="text-indent: 10px;" nowrap class="TableData">移动设备ID：</td>
    <td nowrap class="TableData">
      <input style="font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="deviceId"  class="BigInput" />
      <br/>
      （绑定移动设备ID，仅允许指定移动终端进行登录。为空则不限制）
    </td>
   </tr>
   <tr>
    <td style="text-indent: 10px;" nowrap class="TableData">IMEI码：</td>
    <td nowrap class="TableData">
      <input style="font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="secureKeySn"  class="BigInput" />
      <br/>
      （设备的IMEI码，用于点聚AIP版式文件授权）
    </td>
   </tr>
   <tr>
    <td style="text-indent: 10px;" nowrap class="TableData">点聚AIP授权码：</td>
    <td nowrap class="TableData">
      <input style="font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="icq_no"  class="BigInput" />
    </td>
   </tr>
   <tr>
    <td style="text-indent: 10px;" nowrap class="TableData">统计图地址：</td>
    <td nowrap class="TableData">
    	<input style="font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="bkground" id="bkground" class="BigInput" />
    </td>
   </tr>
   </tbody>
   
   <tr onClick="setOption('option2')" style="<%=adminPriv?"cursor: pointer;":"display:none;cursor: pointer;"%>" title="点击展开/收缩选项">
    <td colspan="2" style='vertical-align: middle;font-family: MicroSoft YaHei;font-size: 14px;line-height: 30px;'><img src="<%=contextPath %>/common/zt_webframe/imgs/common_img/icon_yhcx.png">&nbsp;&nbsp; <span>扩展信息</span></td>
   </tr>
   <tbody id="option2" style="<%=adminPriv?"":"display:none;"%>">
   <tr>
    <td style="text-indent: 10px;" nowrap class="TableData" width="120">别名：</td>
    <td nowrap class="TableData">
        <input style="height:25px;font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="byname" class="BigInput" size="10" newMaxLength="20">
    </td>
   </tr>
   <%
					if (TeeUtility.isNullorEmpty(uuid)) {
				%>    <tr>
    <td style="text-indent: 10px;" nowrap class="TableData">密码：</td>
    <td nowrap class="TableData">
        <input style="height:25px; font-family: MicroSoft YaHei;font-size: 12px;" type="password" name="password" class="BigInput" size="20" newMaxLength="20"> 8-20位，必须同时包含字母和数字    </td>
   </tr> <%
 					} 
 				%> 

   <tr >
    <td style="text-indent: 10px;" nowrap class="TableData">性别：</td>
    <td nowrap class="TableData">
        <select style="height:25px; font-family: MicroSoft YaHei;font-size: 12px;" name="sex" class="BigSelect">
        <option value="0">男</option>
        <option value="1">女</option>
        </select>
    </td>
   </tr style="display:none;">
   <tr>
    <td style="text-indent: 10px;" nowrap class="TableData">生日：</td>
    <td nowrap class="TableData">
        <input style="height:25px; font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="birthdayStr" size="10" newMaxLength="10" value="" class="BigInput" onClick="WdatePicker()"  class="easyui-validatebox" validType ='date[]'/>&nbsp;&nbsp;
       <!--  <input type="checkbox" name="isLunar" id="isLunar"><label for="isLunar">是农历生日</label>      -->
    </td>
   </tr>
   <tr style="display:none;">
      <td style="text-indent: 10px;" nowrap class="TableData">界面主题：</td>
      <td class="TableData">
        <select style="height:25px; font-family: MicroSoft YaHei;font-size: 12px;" name="theme" class="BigSelect">
	  		<option value="4" >现代门户</option>
	  		<option value="8" >智慧政务</option>
	  		<option value="classic" selected>2017版经典</option>
        </select>
      </td>
    </tr>
   <tr>
      <td style="text-indent: 10px;" nowrap class="TableData"> 手机：</td>
      <td class="TableData">
        <input style="height:25px; font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="mobilNo" size="16" newMaxLength="23" class="BigInput" value="">
        <input type="checkbox" name="mobilNoHidden" id="mobilNoHidden" value='1'><label for="mobilNoHidden" style="cursor: hand;">手机号码不公开</label><br>
        （填写后可接收OA系统发送的手机短信，手机号码不公开仍可接收短信）      </td>
    </tr>
    <tr>
      <td style="text-indent: 10px;" nowrap class="TableData"> 电子邮件：</td>
      <td class="TableData">
        <input style="height:25px; font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="email" size="25" newMaxLength="50" class="BigInput" value="">
      </td>
    </tr>
     <tr>
      <td style="text-indent: 10px;" nowrap class="TableData"> QQ：</td>
      <td class="TableData">
        <input style="height:25px; font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="oicqNo" size="25" newMaxLength="20" class="BigInput" value="">
      </td>
    </tr>
    
    
    <tr>
      <td style="text-indent: 10px;" nowrap class="TableData"> 微信号：</td>
      <td class="TableData">
        <input style="height:25px; font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="weixinNo" size="16" newMaxLength="23" class="BigInput" value="">
      </td>
    </tr>
   <tr>
      <td style="text-indent: 10px;" nowrap class="TableData"> 工作电话：</td>
      <td class="TableData">
        <input style="height:25px; font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="telNoDept" size="16" newMaxLength="23" class="BigInput" value="">
      </td>
    </tr>

    <tr>
      <td style="text-indent: 10px;" nowrap class="TableData"> 工作传真：</td>
      <td class="TableData">
        <input style="height:25px; font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="faxNoDept" size="16" newMaxLength="23" class="BigInput" value="">
      </td>
    </tr>
    
    
    <tr>
      <td style="text-indent: 10px;" nowrap class="TableData"> 家庭住址：</td>
      <td class="TableData">
        <input style="height:25px; font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="addHome" size="16" newMaxLength="23" class="BigInput" value="">
      </td>
    </tr>
     
   <tr>
      <td style="text-indent: 10px;" nowrap class="TableData"> 家庭邮编：</td>
      <td class="TableData">
        <input style="height:25px; font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="postNoHome" size="16" newMaxLength="23" class="BigInput" value="">
      </td>
    </tr>
    <tr>
      <td style="text-indent: 10px;" nowrap class="TableData"> 家庭电话：</td>
      <td class="TableData">
        <input style="height:25px; font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="telNoHome" size="16" newMaxLength="23" class="BigInput" value="">
      </td>
    </tr>
     <tr>
      <td style="text-indent: 10px;" nowrap class="TableData"> 身份证号：</td>
      <td class="TableData">
        <input style="height:25px; font-family: MicroSoft YaHei;font-size: 12px;" type="text" name="certUniqueId" size="16" newMaxLength="23" class="BigInput" value="">
      </td>
    </tr>
    <tr style="display:none;">
      <td style="text-indent: 10px;" nowrap class="TableData"> 个人签名图片：</td>
      <td class="TableData">
        <input type="hidden" id="attachId" name="attachId"></input>
        <div id="uploadDiv" ></div>
        <div style="margin-top:6px;">
        <input class="btn-win-white" id="uploadBtn" type="button" value="上传"/>
        <input class="btn-win-white"  type="button" value="清空" onclick="qingkou();"/>
        </div>
      </td>
    </tr>
    
   </tbody>
    	
   
   <tr>
    <td  nowrap style="text-align: center;" colspan="2" align="center">
        <input type="hidden" id="uuid" name="uuid"  value="<%=uuid %>">
        <input style="width:45px;height: 25px;" type="button" value="保存" class="btn-win-white" title="保存用户" onclick="doSave()" >&nbsp;&nbsp;
        <input style="width:45px;height: 25px;" type="button" value="返回" class="btn-win-white" title="返回人员列表" onClick="history.go(-1)">
    </td>
	</tr>
	
</table>
  </form>
</body>
<script>
	$("#form1").validate();
	
	
	
	/**
	 * 选择部门
	 * @return
	 */
	function selectDept(retArray , moduleId,privNoFlag , noAllDept,callBackFunc) {
	  deptRetNameArray = retArray;
	  objSelectType  = retArray[2] || "";
	  var url = contextPath + "/system/core/orgselect/selectMultiDeptPost.jsp?objSelectType=" + objSelectType;
	  var has = false;
	  if (moduleId) {
	    url += "&moduleId=" + moduleId ;
	  }
	  if (privNoFlag) {
	    url += "&privNoFlag=" + privNoFlag ;
	  }
	  if (noAllDept) {
	    url += "&noAllDept=" + noAllDept ;
	  }
	  if(callBackFunc){
		  url += "&callBackPara=" + callBackFunc ;
	  }
	  var IM_OA;
	try{
	    IM_OA = window.external.IM_OA;
	}catch(e){}

	if(window.showModelDialog || IM_OA){
		  dialogChangesize(url, 560, 400);
	  }else{
		  openWindow(url,"选择人员", 560, 400);
	  }
	  
	}


	/**
	 * 选择单个部门
	 * @return
	 */
	function selectSingleDept(retArray , moduleId,privNoFlag , noAllDept) {
	  deptRetNameArray = retArray;
	  objSelectType  = retArray[2] || "";
	  var url = contextPath + "/system/core/orgselect/selectMultiDeptPost.jsp?objSelectType=" + objSelectType + "&isSingle=1";
	  var has = false;
	  if (moduleId) {
	    url += "&moduleId=" + moduleId ;
	  }
	  if (privNoFlag) {
	    url += "&privNoFlag=" + privNoFlag ;
	  }
	  if (noAllDept) {
	    url += "&noAllDept=" + noAllDept ;
	  }
	  var IM_OA;
	try{
	    IM_OA = window.external.IM_OA;
	}catch(e){}

	if(window.showModelDialog || IM_OA){
		  dialogChangesize(url, 560, 400);
	  }else{
		  openWindow(url,"选择人员", 560, 400);
	  }
	  
	}
</script>
</html>
