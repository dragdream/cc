
<%@page import="com.tianee.webframe.util.str.TeeUtility"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>
<title>离职/外部人员</title>
<script type="text/javascript">

var datagrid;
function doInit(){
	
	query();
}



//查询
function query(){
	var params = tools.formToJson($("#form1"));
	datagrid = $('#datagrid').datagrid({
		url: contextPath+"/personManager/queryDeletePerson.action",
		queryParams:params,
	    pagination:true,
	    singleSelect:false,
	    view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
	    toolbar:'#toolbar',//工具条对象
    	checkbox:true,
	    border:false,
	    idField:'sid',//主键列
	    fitColumns:true,//列是否进行自动宽度适应
	    columns:[[
	  			{field:'deptIdName',title:'部门',width:100},
	  			{field:'userId',title:'用户名',width:200},
	  			{field:'userName',title:'姓名',width:200},
	  			{field:'userRoleStrName',title:'角色',width:200},
	  			{field:'opt_',title:'操作',width:80,formatter:function(value,rowData,rowIndex){
	  				 return "<a href='javascript:void(0)' onclick='toAddUpdatePerson("+rowData.uuid+")'>编辑</a>&nbsp;&nbsp;<a href='javascript:void(0)' onclick='reduction("+rowData.uuid+")'>还原";
				}}
	  		]]
	  	});
	//隐藏高级查询form表单
	$(".searchCancel").click();
}

//还原
function reduction(uuid){
	$.MsgBox.Confirm ("提示", "确认要还原该离职人员吗？",function(){
		 var url = contextPath+"/personManager/reductionPerson.action?uuid="+uuid;
			var jsonObj = tools.requestJsonRs(url );
			if(jsonObj.rtState){
				$.MsgBox.Alert_auto("还原成功！",function(){
				window.location.reload();
				});
			}else{
				$.MsgBox.Alert_auto("还原失败！");
			}
	 });
}
 function delPerson(uuid){
	 $.MsgBox.Confirm ("提示", "确认要删除离职人员吗？",function(){
		 var url = contextPath+"/personManager/delPerson.action?uuid="+uuid;
			var jsonObj = tools.requestJsonRs(url );
			if(jsonObj.rtState){
				$.MsgBox.Alert_auto("删除成功！",function(){
				window.location.reload();
				});
			}else{
				$.MsgBox.Alert_auto("删除失败！");
			}
	 });
 }

 
 
 
//重置form表单
 function  resetForm(){
 	document.getElementById("form1").reset(); 
 }
</script>

</head>
<body onload="doInit()" style="padding:10px;margin-left: 5px;margin-right: 5px;">
<!-- 	<table border="0" width="100%" cellspacing="0" cellpadding="3" -->
<!-- 		class="small" align="center"> -->
<!-- 		<tr> -->
<!-- 			<td class="Big3">&nbsp;&nbsp; -->
<!-- 				离职人员 (红色字体为已离职人员，编辑保存后更改为在职人员，<font color="red">注意：在这删除后不可恢复</font>) -->
<!-- 			</td> -->
<!-- 		</tr> -->
<!-- 	</table> -->
	
<!-- 	<div id="personList"> -->
		
<!-- 	</div> -->
<table id="datagrid" fit="true"></table>
<div id="toolbar" class=" clearfix" style="padding-top: 5px;padding-bottom: 10px;">
    <div class="fl left">
      <img src="<%=contextPath %>/common/zt_webframe/imgs/common_img/icon_yhcx.png" align="absMiddle">&nbsp;&nbsp; <span style="font-family: MicroSoft Yahei;font-size: 14px;">离职人员</span> 
    </div>

    <div class="fr right">
        <input type="button"  value="高级检索" class="btn-win-white advancedSearch"/>
    </div>
 </div>


  <form id="form1" class='ad_sea_Content'>
       <table class="TableBlock" width="100%"  style="font-size:12px" id="searchTable">
          <tr>
             <td width="15%">用户名：</td>
             <td width="35%">
                <input type="text" name="userId" id="userId"/>
             </td>
             <td width="15%">用户姓名：</td>
             <td width="35%">
                <input type="text" name="userName" id="userName"/>
             </td>
          </tr>
          <tr>
             <td>所属部门：</td>
             <td>
                 <input name="deptId" id="deptId" type="hidden"/>
			     <input class="BigInput readonly" type="text" id="deptName" name="deptName"   readonly/>
			     <span class='addSpan'>
			      <img src="<%=contextPath %>/system/subsys/project/img/icon_select.png" onclick="selectSingleDept(['deptId','deptName'],'14')" value="选择"/>
				    &nbsp;&nbsp;
				  <img src="<%=contextPath %>/system/subsys/project/img/icon_cancel.png" onclick="clearData('deptId','deptName')" value="清空"/>
			     </span>
             </td>
             <td>所属角色：</td>
             <td>
                <input name="roleId" id="roleId" type="hidden"/>
			    <input class="BigInput readonly" type="text" id="roleName" name="roleName"   readonly/>
			    <span class='addSpan'>
			      <img src="<%=contextPath %>/system/subsys/project/img/icon_select.png" onclick="selectSingleRole(['roleId','roleName'],'14')" value="选择"/>
				    &nbsp;&nbsp;
				  <img src="<%=contextPath %>/system/subsys/project/img/icon_cancel.png" onclick="clearData('roleId','roleName')" value="清空"/>
			    </span>
             </td>
          </tr>
          <tr>
             <td>手机号：</td>
             <td>
                <input type="text" id='mobilNo' name='mobilNo'  class="BigInput"/>
             </td>
             <td>邮箱：</td>
             <td>
                <input type="text" id='email' name='email'  class="BigInput"/>
             </td>
          </tr>
          <tr>
             <td>QQ：</td>
             <td>
                 <input type="text" name="oicqNo" id="oicqNo"/>
             </td>
             <td>微信号：</td>
             <td>
                 <input type="text" name="weixinNo" id="weixinNo"/>
             </td>
          </tr>
          
       </table>
       <div class='btn_search'>
		<input type='button' class='btn-win-white' value='查询' onclick="query()">&nbsp;&nbsp;&nbsp;
		<input type='button' class='btn-win-white' value='重置' onclick='resetForm()'>&nbsp;&nbsp;&nbsp;
		<input type='button' class='btn-win-white searchCancel' value='取消'>
		</div>
    </form>





<script>

		var btn_top = $(".advancedSearch").offset().top;
		var brn_height = $(".advancedSearch").outerHeight();
		$(".ad_sea_Content").css('top',(btn_top + brn_height));
		$(".advancedSearch").click(function(){
			$(".ad_sea_Content").slideToggle(200);
			if($(this).hasClass("searchOpen")){//显示前
			$(".serch_zhezhao").remove();
			$(this).removeClass("searchOpen");
			$(this).css({"border":"1px solid #0d93f6",});
			$(this).css('border-bottom','1px solid #0d93f6');
			}else{
			$(this).addClass("searchOpen");//显示时
			$(this).css({"border":"1px solid #dadada",'border-bottom':'1px solid #fff'});
			$('body').append('<div class="serch_zhezhao"></div>');
		}
		var _offsetTop = $("#form1").offset().top;
		$(".serch_zhezhao").css("top",_offsetTop)
		});
		$(".searchCancel").click(function(){
			$(".advancedSearch").removeClass("searchOpen");
		$("#form1").slideUp(200);
		$(".serch_zhezhao").remove();
		});

		</script>
</body>
</html>