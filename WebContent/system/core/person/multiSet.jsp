<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.partthree.util.TeePartThreeUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp"%>
<title>人员批量设置</title>
<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>
<%
	TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
    
	boolean adminPriv=TeePartThreeUtil.checkHasPriv(loginUser, "SYS_USER_MANAGE");
	boolean saferPriv=TeePartThreeUtil.checkHasPriv(loginUser, "SYS_USER_PRIV");
	if(!adminPriv&&!saferPriv){//没有权限(既不是系统安全员  也不是系统管理员 )
		   response.sendRedirect("/system/core/system/partthree/error.jsp");
	}


%>
<script Language="JavaScript">
var flag = 1;  //标记能否添加部门  1可以，0不可以/**
 * 获取判断密码校验
 */

function doInit(){
	//获取角色
	selectUserPrivList('userRoleStr');
	
	getDeptParent();
	
	//判断密码校验
	//getCheckPassPara('pass1','pass2');
	
	getSysLog();
	getAllAttendConfig();//获取排班类型
	getPortalTemplate();
}

 

function commit(){
	if(CheckForm()){
		var url = "<%=contextPath %>/personManager/mulitSetPersonByTerm.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto("批量设置用户成功！",function(){
			window.location.reload();
			});
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}

	return ;

}

/* function changePass(pass1,pass2){
	if($('#' + pass1).val() || $('#' + pass2).val()){
		checkPass(pass1,pass2,true);
	}else{
		checkPass(pass1,pass2,false);
	}
}
 */
/* function checkPass(pass1,pass2,checkFlag){
	if(checkFlag){
		$('#' + pass1).validatebox({ 
			required:true 
		}); 
		$('#' + pass2).validatebox({ 
			required:true
		}); 
	}else{
		$('#' + pass1).validatebox({ 
			required:false
		}); 
		$('#' + pass2).validatebox({ 
			required:false
		});
	}
} */

function CheckForm(){
	if($("#deptIdOtherStr").val() == "" 
			&& $("#userRoleOtherId").val() == ""
			&& $("#userId").val() == ""){
		
		$.MsgBox.Alert_auto("请选择批量设置范围！");
		return false;
	}
	/* if($("#pass1").val() || $("#pass2").val()){
		checkPass("pass1","pass2",true);
	}else{
		checkPass("pass1","pass2",false);
	} */
	var isCheck =  $("#form1").valid(); 
	if(isCheck == false){
		return false;
	}
	
    return true;
}



/**
 * 获取部门
 */
function getDeptParent(){
	//var url =  "<%=contextPath %>/deptManager/getDeptTreeAll.action";
// 	var config = {
// 			zTreeId:"deptIdZTree",
// 			requestURL:url,
// 	        onClickFunc:onclickDept,
// 			async:false,
// 			onAsyncSuccess:setDeptParentSelct
// 		};
// 		zTreeObj = ZTreeTool.config(config);
	
} 
/**
 * 初始化后选中节点,上级部门
 */
function setDeptParentSelct(){
	ZTreeObj = $.fn.zTree.getZTreeObj(ZTreeTool.zTreeId);
    if(ZTreeObj == null){
    	setTimeout('setDeptParentSelct()',500);
    }else{
    	//ZTreeObj.expandAll(true);
    	 var node = ZTreeObj.getNodeByParam("id",$("#deptId").val(),null);
    	 if(node){
    	    ZTreeObj.selectNode(node);
    	 }
    }  
    ZTreeTool.inputBindZtree(ZTreeTool.zTreeId,'deptId','');
}

//点击树执行事件
function onclickDept (event, treeId, treeNode) {
	$("#deptIdName").val(treeNode.name);
	$("#deptId").val(treeNode.id);
	ZTreeTool.hideZtreeMenu();
}



/**
 * 获取更新密码列表
 */
function getSysLog(){
	var url = "<%=request.getContextPath() %>/sysLogManage/getLogByLoginPerson.action";
	var para =  {count:10 , type :'003H'} ;
	var jsonRs = tools.requestJsonRs(url,para);
	//alert(jsonRs);
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		if(data.length >0){
			var tableStr = "<table class='TableBlock_page' width='90%' align='center' >"
		      + " <tbody id='tbody'>"
		      + "<tr class='TableData' style='line-height:25px;border-bottom:1px solid #f2f2f2;border-bottom:2px solid #b0deff!important;background-color: #f8f8f8;'>"
		      + "<td  align='center' >操作用户 </td>"
		      + "<td > 修改时间</td>"
		     // + "<td > 所属部门</td>"
		      + "<td > IP地址</td>"
		      + "<td > 备注</td>"
		      +"</tr>";
			for(var i = 0;i<data.length ; i++){
				var obj = data[i];
				var id = obj.sid;
				var time = obj.time + "";
				var timeStr = getFormatDateStr(time , 'yyyy-MM-dd HH:mm:ss');
				tableStr = tableStr + "<tr  align='center'>"
				+"<td style='line-height:25px;' width='120px;'>" + obj.userName + "</td>"
				+"<td width='160px;'>" + timeStr+ "</td>"
				//+"<td>" + seal.deptName + "</td>"
				+"<td width='120px;'>" + obj.ip + "</td>"
				+"<td width='' >" + obj.remark + "</td>"
			
				+ "</tr>";		
			}
		    tableStr = tableStr + " </tbody></table>";
			$("#sysLog").append(tableStr);
		}else{
			messageMsg("没有相关数据", "sysLog" ,'info');
		}
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
	
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
	var url = contextPath+"/teePortalTemplateController/getTemplateList.action?uuid=&deptId=";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data=json.rtData;
		var html="";
		html = "<option value=\"\">请选择</option>";
		if(data.length>0){
			for(var i=0;i<data.length;i++){
				html+="<option value=\""+data[i].sid+"\">"+data[i].templateName+"</option>";
			}
		}
		$("#desktop").html(html);
	}else{
		parent.$.MsgBox.Alert_auto(json.rtMsg);
	}
}
/* var postPriv = json.postPriv;
// var postDeptStr = json.postDeptStr;
 //if(postPriv == '2' &&  postDeptStr != ""){
	if(postPriv == '2'){   
			$("#post_dept").show();
		} 
 */


 function selectViewDept(){
	if($("#viewPriv").val()=="2"){//指定部门
		$("#view_dept").show();
	}else{
		$("#view_dept").hide();
	}
}
</script>
</head>
<body style="padding: 10px;margin-left: 5px;margin-right: 5px;overflow-x:hidden;" onload="doInit()">
<div id="toolbar" class = "setHeight clearfix">
<div class=" fl ">
   <h4 style="font-size: 16px;font-family:MicroSoft YaHei;">
				批量用户个性设置
   </h4>
</div>
 	<span class="basic_border" style="margin-top: 10px;"></span>
</div>
<form method="post" name="form1" id="form1">
  <table class="TableBlock_page" width="90%" align="center" style="font-size: 12px;font-family: MicroSoft YaHei;">
  <tr>
    <td colspan="2" style='vertical-align: middle;font-family: MicroSoft YaHei;font-size: 14px;line-height: 30px;'><img src="<%=contextPath %>/common/zt_webframe/imgs/common_img/icon_yhcx.png">&nbsp;&nbsp; <span>人员范围</span></td>
   </tr>
  
  <tr>
      <td style="text-indent: 10px;" nowrap class="TableData" width="180">范围(部门)：</td>
      <td class="TableData">                          
        <input type="hidden" name="deptIdOtherStr" id="deptIdOtherStr" value=""/>
        <textarea style="font-family:MicroSoft YaHei;font-size: 12px;" cols=30 name="deptNameStr" id="deptNameStr" rows=2 class="" wrap="yes" readonly></textarea>
        <span class='addSpan'>
		         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectDept(['deptIdOtherStr','deptNameStr'])" value="选择"/>
			        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('deptIdOtherStr','deptNameStr')" value="清空"/>
		 </span>
      </td>
    </tr>
    <tr <%=loginUser.getUuid()==4?"":"style='display:none'" %>>
      <td style="text-indent: 10px;" nowrap class="TableData">范围(角色)：</td>
      <td class="TableData">
        <input type="hidden" name="userRoleOtherId" id="userRoleOtherId" value="">
        <textarea style="font-family:MicroSoft YaHei;font-size: 12px;" cols=30 name="userRoleOtherName" id="userRoleOtherName" rows=2 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
         <span class='addSpan'>
		         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectRole(['userRoleOtherId','userRoleOtherName'])" value="选择"/>
			        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('userRoleOtherId','userRoleOtherName')" value="清空"/>
		 </span>
      </td>
   </tr>
   <tr>
      <td style="text-indent: 10px;" nowrap class="TableData">范围(人员)：</td>
      <td class="TableData">
        <input type="hidden" name="userId" id="userId" value="">
        <textarea style="font-family:MicroSoft YaHei;font-size: 12px;" cols=30 name="userNameStr" id="userNameStr" rows=2 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
        <span class='addSpan'>
		         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectUser(['userId', 'userNameStr'])" value="选择"/>
			        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('userId','userNameStr')" value="清空"/>
		 </span>
       
      </td>
   </tr>
   
  <tr>
    <td colspan="2" style='vertical-align: middle;font-family: MicroSoft YaHei;font-size: 14px;line-height: 30px;'><img src="<%=contextPath %>/common/zt_webframe/imgs/common_img/icon_yhcx.png">&nbsp;&nbsp; <span>设置属性</span></td>
   </tr>
  <!--   <tr>
      <td nowrap class="TableData">桌面模块(左侧)：</td>
      <td class="TableData">
        <input type="hidden" name="myTableLeft" id="myTableLeft" value="">
        <textarea cols=30 name="myTableLeftDesc" id="myTableLeftDesc" rows=2 class="BigStatic" wrap="yes" readonly></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="leftMytable()">选择</a>
        <a href="javascript:void(0);" class="orgClear" onClick="$('myTableLeft').value='';$('myTableLeftDesc').value='';">清空</a>
      </td>
   </tr>
    <tr>
      <td nowrap class="TableData">桌面模块(右侧)：</td>
      <td class="TableData">
        <input type="hidden" name="myTableRight" id="myTableRight" value="">
        <textarea cols=30 name="myTableRightDesc" id="myTableRightDesc" rows=2 class="BigStatic" wrap="yes" readonly></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="rightMytable()">选择</a>
        <a href="javascript:void(0);" class="orgClear" onClick="$('myTableRight').value='';$('myTableRightDesc').value='';">清空</a>
      </td>
   </tr> -->
   
  <!--  <tr style="display:none;">
      <td nowrap class="TableData">通讯范围：</td>
      <td class="TableData">
        <input type="hidden" name="privId1" id="privId1" value="">
        <textarea  cols=30 name="privId1Desc" id="privId1Desc" rows=2 class="BigStatic" wrap="yes" readonly></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="selectRole(['privId1','privId1Desc']);">添加</a>
        <a href="javascript:void(0);" class="orgClear" onClick="$('privId1').value='';$('privId1Desc').value='';">清空</a>
        <br>所选角色的人员可以给上边选择范围内的用户发送邮件和短消息
      </td>
   </tr> -->
   
   
  <%
     if(saferPriv){//系统安全员
    %>
	    <tr>
	      <td style="text-indent: 10px;" nowrap class="TableData">菜单组：</td>
	      <td class="TableData">
	        <input type="hidden" name="menuGroupsStr"  class="BigInput" id="menuGroupsStr" value="">
	        <textarea style="font-family:MicroSoft YaHei;font-size: 12px;"  cols=30 name="menuGroupsStrName" id="menuGroupsStrName" rows=2 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
	         <span class='addSpan'>
			         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectMenuGroup(['menuGroupsStr','menuGroupsStrName'],'1')" value="选择"/>
				        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('menuGroupsStr','menuGroupsStrName')" value="清空"/>
			 </span>
	       
	      </td>
	   </tr>
	     <tr >
	   
	    <td style="text-indent: 10px;" nowrap class="TableData" width="120">可见范围：</td>
	    <td nowrap class="TableData">
	        <select style="font-family:MicroSoft YaHei;font-size: 12px;height: 25px;" name="viewPriv"  id="viewPriv" class="BigSelect" OnChange="selectViewDept()">
	            <option value="0">请选择</option>
	        	<option value="1">本机构</option>
	        	<option value="3">本部门</option>
	        	<option value="4">本部门及以下部门</option>
	        	<option value="2">指定部门</option>
	        </select>
	        <br/>
	        （可见范围是指用户在选人、选部门、查看组织机构等信息时，可查看到的指定部门及机构信息。）
	    </td>
	   </tr>
	   <tr id="view_dept" style="display:none;">
	      <td style="text-indent: 10px;" nowrap class="TableData">可见范围（部门/机构）：</td>
	 
	      <td class="TableData">
	  	 <input type="hidden" name="viewDeptIds" id="viewDeptIds" value="">
	        <textarea style="font-family:MicroSoft YaHei;font-size: 12px;" cols=30 name="viewDeptNames" id="viewDeptNames" rows=2 class="SmallStatic   BigTextarea" wrap="yes" readonly ></textarea>
	       <span class='addSpan'>
			         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectDept(['viewDeptIds','viewDeptNames'],'1')" value="选择"/>
				        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('viewDeptIds','viewDeptNames')" value="清空"/>
			 </span>
	      </td>
	    </tr>
	      <tr >
	    <td style="text-indent: 10px;" nowrap class="TableData" width="120">管理范围：</td>
	    <td nowrap class="TableData">
	        <select style="height: 25px;font-family: MicroSoft YaHei;font-size: 12px;" name="postPriv"  id="postPriv" class="BigSelect" OnChange="selectPostDept()">
	          <option value="-1">请选择</option>
	          <option value="0">本部门</option>
	          <option value="3">本部门以及下级所有部门</option>
	          <option value="4">本机构</option>
	          <option value="1">全体</option>
	          <option value="2">指定部门</option>
	        </select>
	        <br/>
	        （在管理型模块中起约束作用）
	    </td>
	   </tr>
	   <tr id="post_dept" style="display:none;">
	      <td style="text-indent: 10px;" nowrap class="TableData">管理范围（部门）：</td>
	 
	      <td class="TableData">
			  	 <input type="hidden" name="postDeptStr" id="postDeptStr" value="">
			  	 <textarea style="height: 25px;" cols=30 name="postDeptStrName" id="postDeptStrName" rows=2 class="SmallStatic" wrap="yes" readonly ></textarea>
			  	 <span class='addSpan'>
					         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectDept(['postDeptStr','postDeptStrName'],'1')" value="选择"/>
						        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('postDeptStr','postDeptStrName')" value="清空"/>
				</span>
	      </td>
	    </tr>
    	 
    <% 
     }
  %>
   
    
    <%
       if(adminPriv){//系统管理员
    %>
	      <tr>
	    <td style="text-indent: 10px;" nowrap class="TableData">主角色：</td>
	    <td nowrap class="TableData">
	        <select style="font-family:MicroSoft YaHei;font-size: 12px;height: 25px;" name="userRoleStr" id="userRoleStr" class="BigSelect">
	         <option value=''>请选择</option>
	      </select>
	      	 </select>    
	     	  
	     <span>
	     &nbsp;&nbsp;
	      <a href="javascript:select_priv();">指定辅助角色</a>
	     </span>
	      <br>
	    </td>
	
	   </tr>
	     <tr id="priv" style="display:none;">
	      <td style="text-indent: 10px;" nowrap class="TableData">辅助角色：</td>
	 
	      <td class="TableData">
	      <!-- <select multiple="multiple" size="10" id="privIdStr" style="width:150px;">
	     </select> -->
	    	<input type="hidden" name="userRoleOtherIds"  id="userRoleOtherIds" value=""/> 
	       
	        <textarea style="font-family:MicroSoft YaHei;font-size: 12px;" cols=30 id="userRoleOtherNames" name="userRoleOtherNames" rows=2 class="SmallStatic  BigTextarea" wrap="yes" readonly></textarea>
	        <span class='addSpan'>
			         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectRole(['userRoleOtherIds','userRoleOtherNames'],'','','2')" value="选择"/>
				        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('userRoleOtherIds', 'userRoleOtherNames')" value="清空"/>
			 </span>
	        <br>（辅助角色仅用于扩展主角色 ）    </td>
	   </tr>
	   <tr>
	   <td style="text-indent: 10px;" nowrap class="TableData">部门：</td>
	    <td nowrap class="TableData">
	       <!--  <select name="deptId" id="deptId" class="BigSelect">
	        <option value=""></option>
	        </select> -->
	      <input id="deptId" name="deptId"  type="text" style="display:none;"/>
		  <input style="font-family:MicroSoft YaHei;font-size: 12px;" id="deptIdName" name="deptIdName"  type="text" class="BigInput readonly" readonly/>
	<!-- 		<ul id="deptIdZTree" class="ztree" style="margin-top:0; width:247px; display:none;"></ul> -->
		   <span class='addSpan'>
			         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectSingleDept(['deptId', 'deptIdName'])" value="选择"/>
				        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('deptId', 'deptIdName')" value="清空"/>
			 </span>
			  &nbsp;&nbsp;<a href="javascript:selectDeptOther()">指定其它所属部门</a>
	    </td>
	  </tr>
	  <tr id="dept_other" style="display:none;">
	      <td style="text-indent: 10px;" nowrap class="TableData">所属部门：</td>
	      <td class="TableData">
	      
	     <!-- <select multiple="multiple" size="10" id="deptIdOtherStr" style="width:150px;">
	     </select> -->
	       <input type="hidden" name="deptIdOtherStrs" id="deptIdOtherStrs" value=""/>
	        <textarea style="font-family:MicroSoft YaHei;font-size: 12px;" cols=30 name="deptIdOtherStrNames" id="deptIdOtherStrNames" rows=2 class="SmallStatic  BigTextarea" wrap="yes" readonly ></textarea>
	         <span class='addSpan'>
			         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectDept(['deptIdOtherStrs','deptIdOtherStrNames'],'1')" value="选择"/>
				        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('deptIdOtherStrs','deptIdOtherStrNames')" value="清空"/>
			 </span>
	      </td>
	   </tr>
			<%
				String GAO_SU_BO_VERSION = TeeStringUtil.getString(TeeSysProps.getString("GAO_SU_BO_VERSION"));
			%>
	    <tr <%=GAO_SU_BO_VERSION.equals("1")?"style='display:none'":"" %>>
	      <td style="text-indent: 10px;" nowrap class="TableData">界面主题：</td>
	      <td class="TableData">
	        <select style="font-family:MicroSoft YaHei;font-size: 12px;height: 25px;" name="theme" id="theme" class="BigSelect" onChange="">
		  		<option value="4" >现代门户</option>
		  		<option value="8" >智慧政务</option>
		  		<option value="classic" selected>2017版经典</option>
		        </select>&nbsp;需重新登录才能生效
	      </td>
	    </tr>
	    <tr style="display:none">
	      <td style="text-indent: 10px;" nowrap class="TableData">桌面背景图片：</td>
	      <td class="TableData">
	         <select style="font-family:MicroSoft YaHei;font-size: 12px;height: 25px;" name="bkground" id="bkground" class="BigSelect" onchange="bkChange(this.value)">
	            <option value=""></option>
	            <option value="0">开发中...</option>
	         </select>&nbsp;
	      	<a id="bkPreview" href="" target="_blank" style="display:none;">预览</a>
	      </td>
	    </tr>
	
	 <!--    <tr>
	      <td nowrap class="TableData">登录模式：</td>
	      <td class="TableData">
	        <select name="menuType" id="menuType" class="BigSelect">
	          <option value=""></option>
	          <option value="1">在本窗口打开OA</option>
	          <option value="2">在新窗口打开OA，显示工具栏</option>
	          <option value="3">在新窗口打开OA，无工具栏</option>
	        </select> 需重新登录才能生效
	      </td>
	    </tr> -->
	    <tr style="display:;">
	      <td style="text-indent: 10px;" nowrap class="TableData">消息提醒窗口弹出方式：</td>
	      <td class="TableData">
	        <select style="font-family:MicroSoft YaHei;font-size: 12px;height: 25px;" name="autoPopSms" class="BigSelect">
	          <option value="" >请选择</option>
	          <option value="1" >自动</option>
	          <option value="0" >手动</option>
	        </select>
	      </td>
	    </tr>
	    <tr>
	      <td style="text-indent: 10px;" nowrap class="TableData">直属上级：</td>
	      <td class="TableData">
	      	<input type="hidden" name="leaderIds"  class="BigInput" id="leaderIds" value="">
	        <input style="font-family:MicroSoft YaHei;font-size: 12px;height: 25px;" name="leaderNames" id="leaderNames" class="BigInput" wrap="yes" readonly type="text" />
	        <span class='addSpan'>
			         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectSingleUser(['leaderIds','leaderNames'],'1')" value="选择"/>
				        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('leaderIds','leaderNames')" value="清空"/>
			 </span>
	      </td>
	    </tr>
	     <tr style="display:none">
	      <td style="text-indent: 10px;" nowrap class="TableData">直属下级：</td>
	      <td class="TableData">
	        <input type="hidden" name="underlingIds"  class="BigInput" id="underlingIds" value="">
	        <textarea style="font-family:MicroSoft YaHei;font-size: 12px;"  cols=30 name="underlingNames" id="underlingNames" rows=2 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
	        <span class='addSpan'>
			         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectUser(['underlingIds','underlingNames'],'1')" value="选择"/>
				        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('underlingIds','underlingNames')" value="清空"/>
			 </span>
	      </td>
	    </tr>
	    <td style="text-indent: 10px;" nowrap class="TableData">默认桌面模块：</td>
	    <td nowrap class="TableData">
	        <select style="font-family:MicroSoft YaHei;font-size: 12px;height: 25px;width: 100px;" name="desktop" id="desktop" class="BigSelect">
	        </select>
	    </td>
	    <tr style="display: none">
	      <td style="text-indent: 10px;" nowrap class="TableData" >考勤排班类型：</td>
	      <td class="TableData">
	        <select style="font-family:MicroSoft YaHei;font-size: 12px;height: 25px;width: 100px;" name="dutyType" id="dutyType" class="BigSelect">
	        </select>
	      </td>
	    </tr>
	    <tr>
	      <td style="text-indent: 10px;" nowrap class="TableData">设置为离职：</td>
	      <td class="TableData">
	        <select style="font-family:MicroSoft YaHei;font-size: 12px;height: 25px;width: 100px;" name="deleteStatus" id="deleteStatus" class="BigSelect">
	        	<option value="0"></option>
	        	<option value="1">离职</option>
	        </select>
	      </td>
	    </tr>
	   <tr>
	    <td style="text-indent: 10px;" nowrap class="TableData" width="60" >密码：</td>
	    <td nowrap class="TableData">
	        <input style="font-family:MicroSoft YaHei;font-size: 12px;height: 25px;border: 1px solid #dadada;" type="password" name="pass1" id="pass1" size="20" class="BigInput"  newMaxLength='20'> <span id="passMessage"></span>
	    </td>
	   </tr>
	   <tr>
	    <td style="text-indent: 10px;" nowrap class="TableData" width="120">确认密码：</td>
	    <td nowrap class="TableData">
	        <input style="font-family:MicroSoft YaHei;font-size: 12px;height: 25px;border: 1px solid #dadada;" type="password" name="pass2" id="pass2"  size="20"  class="BigInput"  newMaxLength='20' equalTo="#pass1"> <span id="passMessage2"></span>
	    </td>
	   </tr>
	   <tr>
	    <td style="text-indent: 10px;" nowrap class="TableData">内部邮箱容量：</td>
	    <td nowrap class="TableData">
	        <input style="font-family:MicroSoft YaHei;font-size: 12px;height: 25px;" type="text" name="emailCapacity" id="emailCapacity"  class=" BigInput"  validType ='integeZero[]'   size="5" newMaxLength="11" value="" >&nbsp;MB
	        （0表示不限制大小）
	
	    </td>
	   </tr>
	   <tr>
	    <td style="text-indent: 10px;" nowrap class="TableData">个人网盘容量：</td>
	    <td nowrap class="TableData">
	        <input style="font-family:MicroSoft YaHei;font-size: 12px;height: 25px;" type="text" name="folderCapacity" id="folderCapacity"  class=" BigInput"  validType ='integeZero[]'   size="5" newMaxLength="11" >&nbsp;MB
	        （0表示不限制大小）
	
	    </td>
	   </tr>
	   <tr style='display:none;'>
	    <td style="text-indent: 10px;" nowrap class="TableData">桌面模块参照对象：</td>
	    <td nowrap class="TableData">
	        <input style="font-family:MicroSoft YaHei;font-size: 12px;" type="text" readonly name="desktopReferDesc" id="desktopReferDesc"  class="BigInput readonly" />
	        <input type="hidden" name="desktopRefer" id="desktopRefer"  class="BigInput" />
	        <span class='addSpan'>
			         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectSingleUser(['desktopRefer','desktopReferDesc'])" value="选择"/>
				        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('desktopRefer','desktopReferDesc')" value="清空"/>
			 </span>
	        
	    </td>
	   </tr>
	   <tr style="display:none">
	    <td style="text-indent: 10px;" nowrap class="TableData">Internet邮箱数量：</td>
	    <td nowrap class="TableData">
	        <input style="font-family:MicroSoft YaHei;font-size: 12px;height: 25px;" type="text" name="webmailNum" id="webmailNum" class=" BigInput"   validType ='positivIntege[]'  newMaxLength="11" value="">&nbsp;个
	        （0表示不限制数量）
	    </td>
	   </tr>
	   <tr  style="display:none">
	    <td style="text-indent: 10px;" nowrap class="TableData">每个Internet邮箱容量：</td>
	    <td nowrap class="TableData">
	        <input style="font-family:MicroSoft YaHei;font-size: 12px;height: 25px;" type="text" name="webmailCapacity" id="webmailCapacity" class=" BigInput"   validType ='positivIntege[]'  newMaxLength="11">&nbsp;MB
	       （ 0表示不限制大小）
	
	    </td>
	   </tr>
    
    <%
       }
    %>
    
   <tr>
    <td nowrap style="text-align: center;" colspan="2" align="center">
        <input style="height: 25px;" type="button" value="批量设置" class="btn-win-white" onclick="commit();">
    </td>
   </tr>
  </table>
</form>
  <div style='padding-top:10px;'>
    <table  class="TableBlock_page" width="95%" align="center">
   <tr>
         <td colspan="2" style='vertical-align: middle;font-family: MicroSoft YaHei;font-size: 14px;line-height: 30px;'><img src="<%=contextPath %>/common/zt_webframe/imgs/common_img/icon_yhcx.png">&nbsp;&nbsp; <span>最近10次批量个性设置日志</span></td>
   </tr>
	</table>
	<div id="sysLog"> 
	
	</div>
  </div>
  <br>


</body>
</html>