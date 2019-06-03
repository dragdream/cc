<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<title>招聘需求管理</title>
<script type="text/javascript">

function doInit(){
	getInfoList();
}

var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/recruitRequirementsController/getRecruitList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'requNo',title:'需求编号',width:50},
			{field:'requJob',title:'需求岗位',width:100},
			{field:'requNum',title:'需求人数',width:20,formatter:function(value,rowData,rowIndex){
				if(value){
					value += "";
				}
				return value;
			}},
			{field:'requDeptStrName',title:'需求部门',width:100},
			{field:'requTimeStr',title:'用工日期',width:40,formatter:function(value, rowData, rowIndex){
				return value;
			}},
			{field:'requStatus',title:'需求状态',width:100,hidden:true},
			{field:'recruirementsPriv',title:'需求权限',width:100,hidden:true},
			
			{field:'2',title:'操作',width:60,formatter:function(value, rowData, rowIndex){
				var str = "<a href='javascript:void(0)' onclick='showInfoFunc("+rowData.sid+")'>详细信息 </a>";
				str += "&nbsp;&nbsp;<a href='#' onclick='toAddOrUpdate("+rowData.sid+"),0'>修改</a>";
				if(rowData.recruirementsPriv == '1'){
					str += "&nbsp;&nbsp;<a href='#' onclick='toAddOrUpdate("+rowData.sid+" ,1)'>人才推荐</a>";
				}
				str += "&nbsp;&nbsp;<a href='javascript:void(0)' onclick='deleteSingleFunc("+rowData.sid+")'>删除</a>";
				return str;
			}}
		]]
	});
}

/**
 * 新建编辑信息
 */
function toAddOrUpdate(sid , optType){
	var  url = contextPath + "/system/core/base/hr/recruit/requirements/addOrUpdateRecruitRequire.jsp?sid=" + sid + "&optType=" + optType;
	window.location.href = url;
}




/**
 * 详情信息
 */
function showInfoFunc(sid){
  var title = "招聘需求详细信息";
  var url = contextPath + "/system/core/base/hr/recruit/requirements/recruitDetail.jsp?sid=" + sid;
  bsWindow(url ,title,{width:"1000",height:"360",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		if(v=="关闭"){
			return true;
		}
	}});
}

/**
 * 单个删除维护信息
 */
function deleteSingleFunc(sid){
	deleteObjFunc(sid);
}
/**
 * 批量删除
 */
function batchDeleteFunc(){
	var ids = getSelectItem();
	if(ids.length==0){
		$.MsgBox.Alert_auto("至少选择一项");
		return ;
	}
	deleteObjFunc(ids);
}
/**
 * 删除维护信息
 */
function deleteObjFunc(ids){
	  $.MsgBox.Confirm ("提示", "确定要删除所选中记录？", function(){
		  var url = contextPath + "/recruitRequirementsController/deleteObjById.action";
			var para = {sids:ids};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！",function(){
					datagrid.datagrid('reload');
				});
				
			}  
	  });

}
/**
 * 获取选中值
 */
function getSelectItem(){
	var selections = $('#datagrid').datagrid('getSelections');
	var ids = "";
	for(var i=0;i<selections.length;i++){
		ids+=selections[i].sid;
		if(i!=selections.length-1){
			ids+=",";
		}
	}
	return ids;
}
//根据条件查询
function doSearch(){
	var queryParams=tools.formToJson($("#form1"));
	datagrid.datagrid('options').queryParams=queryParams; 
	datagrid.datagrid("reload");
	//隐藏高级查询form表单
	$(".searchCancel").click();
}


//重置form表单
function  resetForm(){
	document.getElementById("form1").reset(); 
	$(".customTr").remove();
}
</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true" ></table>
    <div id="toolbar" class = "topbar clearfix">
		<div class="fl" style="position:static;">
			<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/hr/imgs/icon_zpxqgl.png">
			<span class="title">招聘需求管理 </span>
		</div>
		<div class = "right fr clearfix">
		    <input type="button" class="btn-win-white fl" onclick="toAddOrUpdate(0);" value="添加需求信息"/>
			<input type="button" class="btn-del-red fl" onclick="batchDeleteFunc();" value="批量删除"/>
			<input type="button" class="btn-win-white advancedSearch fl"  value="高级检索"/>
	     
	     
			 <form id="form1" class='ad_sea_Content'>
		       <table class="TableBlock" width="100%"  style="font-size:12px" id="searchTable">
		          <tr>
		             <td width="10%">需求编号：</td>
		             <td width="40%">
		                <input class="BigInput" type="text" id = "requNo" name='requNo' size="20"/>
		             </td>
		             <td width="10%">需求岗位：</td>
		             <td width="40%">
		                <input class="BigInput" type="text" id = "requJob" name='requJob' size="20"/>
		             </td>
		          </tr>
		          
		          <tr>
		             <td>用工日期：</td>
		             <td colspan="3">
		                <input  type="text" id = "requTimeStrMin" name='requTimeStrMin' size="20" class="BigInput  Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value="" />
						至
						<input  type="text" id = "requTimeStrMax" name='requTimeStrMax' size="20" class="BigInput  Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value=""/>
		             </td>
		             
		          </tr>
		          
		       </table>
		       <div class='btn_search'>
				<input type='button' class='btn-win-white' value='查询' onclick="doSearch();" >&nbsp;&nbsp;&nbsp;
				<input type='button' class='btn-win-white' value='重置' onclick='resetForm()'>&nbsp;&nbsp;&nbsp;
				<input type='button' class='btn-win-white searchCancel' value='取消'>
				</div>
		    </form>
	     </div>
	 </div>

		
		<!-- <form id="form1" name="form1">
			<div class="modal fade" id="searchDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title" id="myModalLabel">查询条件</h4>
						</div>
						<div class="modal-body">
							<table class="TableBlock"  style="text-align: left;" align="center" >
								<tr>
									<td class="TableData">需求编号：</td>
									<td class="TableData">
										<input class="BigInput" type="text" id = "requNo" name='requNo' size="20"/>
									</td>
									<td class="TableData">需求岗位：</td>
									<td class="TableData">
										<input class="BigInput" type="text" id = "requJob" name='requJob' size="20"/>
									</td>
								</tr>
								<tr>
									<td class="TableData">需求人数：</td>
									<td class="TableData">
										<input class="BigInput" type="text" id = "requNum" name='requNum' size="20"/>
									</td>
									<td class="TableData">需求部门：</td>
									<td class="TableData">
										<input class="BigInput" type="text" id = "requDeptStr" name='requDeptStr' size="20"/>
									</td>
								</tr>
								<tr>
									<td class="TableData">用工日期：</td>
									<td colspan="3" class="TableData">
										<input class="BigInput" type="text" id = "requTimeStrMin" name='requTimeStrMin' size="20" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value="" />
										至
										<input class="BigInput" type="text" id = "requTimeStrMax" name='requTimeStrMax' size="20" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value=""/>
									</td>
								</tr>
								<tr align="center">
									<td class="TableData" colspan="4">
										<input type="button" class="btn btn-primary"  value="查询">
										&nbsp;&nbsp;<input type="reset" value="重置" class="btn btn-primary" >
									</td>
								</tr>	
							</table>
						</div>
					</div>
				</div>
			</div>
		</form> -->

</body>

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
</html>