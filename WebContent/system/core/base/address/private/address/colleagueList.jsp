<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
		String groupId = request.getParameter("groupId");
		String seqIds = request.getParameter("seqIds");
		if(groupId == null || "".equals(groupId)){
			groupId = "-1";
		}
		if(seqIds == null || "".equals(seqIds)){
			seqIds = "";
		}
		
		String userId = TeeStringUtil.getString(request.getParameter("userId"));//创建人，为空是公共

%>
<%
response.addHeader("X-UA-Compatible", "IE=EmulateIE9");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>个人通讯簿</title>
	<%@ include file="/header/header2.0.jsp"%>
	<%@ include file="/header/easyui2.0.jsp"%>
	<script type="text/javascript" charset="UTF-8" src="<%=contextPath%>/system/core/base/address/public/js/address.js"></script>
	<script type="text/javascript" charset="UTF-8" src="<%=contextPath%>/system/core/base/address/public/js/datagrid-filter.js"></script>
	<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	var seqIds = '<%=seqIds%>';
	var groupId = '<%=groupId%>';
	var para = {};
	para['groupId'] = groupId;
	para['seqIds'] = seqIds;
	var tjValue;
	
	function doInit(){
		query();
			  
	}

	/* 新增 */
  /*   function addNewAddress(){
    	window.location.href = contextPath + "/system/core/base/address/private/address/addAddress.jsp?groupId="+groupId;
    }
 */
function xq(sid){
          
	 var url = contextPath + "/system/core/base/address/private/address/xq.jsp?sid="+sid;
	 bsWindow(url,"查看详情",{width:"800", height:"550",buttons:[{name:"关闭",classStyle:"btn-alert-gray"}],submit:function(v,h){
	    if(v=="关闭"){
				return true;
	        }
	  }}); 
 }
 
//高级查詢
function query(){
	var para = tools.formToJson($("#form1"));
	 datagrid = $('#datagrid').datagrid({
	        view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
	        pagination:true,
	        pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
	        queryParams:para,
		    url:contextPath +'/teeAddressController/getColleagueList.action',
	        toolbar:"#toolbar",
			border:false,
			fitColumns:true,//列是否进行自动宽度适应
			enableFilter:true,
	        columns : [ [ 
	        			 
	        			{
	        				/* field : 'psnName',
	        				title : '姓名', */
	        				field : 'userName',
	        				title : '姓名',
							width:80,
	        			},{
	        				field : 'userRoleStrName',
	        				title : '角色',
							width:100,
	        			},{
	        				field : 'sex',
	        				title : '性别',
							width:50,
	        				formatter:function(value,rowData,rowIndex){
	        				
	        				if(rowData.sex == 0)
	        					return "男";
	        				else
	        					return "女";
	        				}
	        			},{
	        				field : 'deptIdName',
	        				title : '部门名称',
							width:150,
	        			},{
	        				field : 'telNoDept',
	        				title : '工作电话',
							width:100,
	        			},{
	        				field : 'mobilNo',
	        				title : '手机',
							width:100,formatter:function(value,rowData,rowIndex){
				        		//判断手机号码是否可以公开mobilNoHidden
		        				var mobilNoHidden=rowData.mobilNoHidden;
				        		var mobilNo=rowData.mobilNo;
				        		if(mobilNoHidden!=1){
				        			return mobilNo;
				        		}else{
				        			return "";
				        		}
		        			}
	        			},{
	        				field : 'email',
	        				title : '邮箱',
							width:150,
	        			},{field:'_manage',title:'操作',width:100,formatter:function(value,rowData,rowIndex){
	        		
	        				var opts ='<a href="javascript:void(0);" onclick="xq('+rowData.uuid+')"> <span style="color:#2285c6">详情</span></a>';
	        				return opts;
	        				
	        			}}
	        			] ],
	      });
	 
	//隐藏高级查询form表单
		$(".searchCancel").click();
}
//重置form表单
 function  resetForm(){
 	document.getElementById("form1").reset(); 
 	$("#roleId").val("");
 	$("#deptId").val("");
 }
	</script>
	
<style>
hr
{ 
height：10px;
border-top:2px solid #185598;
} 
	</style>
	
</head>
<body style="margin:0px;overflow:hidden;padding-top:5px;padding-right: 10px" onload="doInit();">

<div id="toolbar" class = "topbar clearfix"> 
    <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/grbg/grtxb/ts.png">
		<span class="title">我的同事</span>
	</div>
    <div class="fr right" style="margin-top: 5px">
	   <button type="button" onclick="" class="advancedSearch btn-win-white">高级查询</button>
    </div>
    
     <!-- 高级查询检索面板 -->
     <form id="form1" class='ad_sea_Content'>
       <table class="TableBlock" width="100%"  style="font-size:12px" id="searchTable">
          <tr>
             <td width="10%">姓名：</td>
             <td width="40%">
                <input type="text" name="userName" id="userName"/>
             </td>
             <td width="10%">部门：</td>
             <td width="40%">
                <input name="deptId" id="deptId" type="hidden"/>
			    <input class="BigInput readonly" type="text" id="deptName" name="deptName"   readonly/>
			    <span class='addSpan'>
			      <img src="<%=contextPath %>/system/subsys/project/img/icon_select.png" onclick="selectSingleDept(['deptId','deptName'],'14')" value="选择"/>
				    &nbsp;&nbsp;
				  <img src="<%=contextPath %>/system/subsys/project/img/icon_cancel.png" onclick="clearData('deptId','deptName')" value="清空"/>
			    </span>
             </td>
          </tr>
          <tr>
             <td>角色：</td>
             <td>
                <input name="roleId" id="roleId" type="hidden"/>
			    <input class="BigInput readonly" type="text" id="roleName" name="roleName"   readonly/>
			    <span class='addSpan'>
			      <img src="<%=contextPath %>/system/subsys/project/img/icon_select.png" onclick="selectSingleRole(['roleId','roleName'],'14')" value="选择"/>
				    &nbsp;&nbsp;
				  <img src="<%=contextPath %>/system/subsys/project/img/icon_cancel.png" onclick="clearData('roleId','roleName')" value="清空"/>
			    </span>
             </td>
             <td>电话：</td>
             <td>
                <input name="tel" id="tel" type="text"/> 
             </td>
          </tr>
          <tr>
             <td>邮箱：</td>
             <td>
                <input type="text" id='email' name='email' />
             </td>
             <td></td>
             <td></td>
          </tr>
         
          
       </table>
       <div class='btn_search'>
		<input type='button' class='btn-win-white' value='查询' onclick="query()">&nbsp;&nbsp;&nbsp;
		<input type='button' class='btn-win-white' value='重置' onclick='resetForm()'>&nbsp;&nbsp;&nbsp;
		<input type='button' class='btn-win-white searchCancel' value='取消'>
		</div>
    </form>
    
    
    
    
</div>
<table id="datagrid" fit="true"></table>


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