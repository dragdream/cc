<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<title>招聘计划管理</title>
<script type="text/javascript">

function doInit(){
	getInfoList();
}

var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/recruitPlanController/getRecruitList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'planNo',title:'计划编号',width:60},
			{field:'planName',title:'计划名称',width:120},
			{field:'planRecrNum',title:'招聘人数',width:40,formatter:function(value,rowData,rowIndex){
				if(value){
					value += "（人）";
				}
				return value;
			}},
			{field:'approvePersonName',title:'审批人',width:60},
			{field:'planCost',title:'费用预算',width:60},
			{field:'startDateStr',title:'开始日期',width:70},
			{field:'planStatus',title:'计划状态',width:40,formatter:function(value, rowData, rowIndex){
				return planStatusFunc(value);
			}},
			{field:'2',title:'操作',width:70,formatter:function(value, rowData, rowIndex){
				var planStatus = rowData.planStatus
				var str = "<a href='javascript:void(0)' onclick='showInfoFunc("+rowData.sid+")'>详细信息 </a>&nbsp;&nbsp;";
				if(planStatus ==0 || planStatus ==2){
					str += "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='editFunc("+rowData.sid+" )'>修改</a>";
					str += "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='deleteSingleFunc("+rowData.sid+")'>删除</a>";
				}else if(planStatus ==1 ){
					str += "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='editFunc2("+rowData.sid+",1)'>申请初试</a>";
				}
				
				return str;
			}}
		]]
	});
}




/**
 * 添加维护信息
 */
function addInfo(){
  var title = "新建招聘计划信息";
  var url = contextPath + "/system/core/base/hr/recruit/plan/newRecruitPlan.jsp";
  bsWindow(url ,title,{width:"850",height:"320",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			cw.doSaveOrUpdate(function(){
			  $.MsgBox.Alert_auto("保存成功！",function(){
				  datagrid.datagrid('reload');
				  BSWINDOW.modal("hide");
			  });
			  
			});
		}else if(v=="关闭"){
			return true;
		}
	}});
}

/**
 * 新建编辑信息
 */
function editFunc(sid ){
	var  url = contextPath + "/system/core/base/hr/recruit/plan/editRecruitPlan.jsp?sid=" + sid ;
	window.location.href = url;
}

/**
 * 新建编辑信息
 optType: 操作类型 0-保存 1-修改 2-申请初试
 */
function editFunc2(sid , optType){
	
	 var title = "申请初试";
	 var url = contextPath + "/system/core/base/hr/recruit/plan/recruitPlanDetail.jsp?sid=" + sid + "&optType=" + optType;
	  bsWindow(url ,title,{width:"950",height:"380",buttons:
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
 * 编辑信息
 */
function editFunc1(sid){
	var title = "新建招聘计划信息";
	if(sid > 0){
		  title = "编辑招聘计划信息";
	}
  
    var url = contextPath + "/system/core/base/hr/recruit/plan/editRecruitPlan.jsp?sid=" + sid;
    bsWindow(url ,title,{width:"850",height:"320",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			cw.doSaveOrUpdate(function(){
				$.MsgBox.Alert_auto("保存成功！",function(){
					datagrid.datagrid('reload');
					BSWINDOW.modal("hide");
				});
				
			});
		}else if(v=="关闭"){
			return true;
		}
	}});
}


/**
 * 详情信息
 */
function showInfoFunc(sid){
  var title = "招聘计划详细信息";
  var url = contextPath + "/system/core/base/hr/recruit/plan/recruitPlanDetail.jsp?sid=" + sid;
  bsWindow(url ,title,{width:"950",height:"380",buttons:
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
		var url = contextPath + "/recruitPlanController/deleteObjById.action";
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
function doSearchFunc(){
	$('#searchDiv').toggle();
	datagrid.datagrid("reload");
}

function planStatusFunc(ids){
	var str = "";
	if(ids == "0"){
		str = "待审批";
	}else if(ids == "1"){
		str = "<font color='green'>已批准</font>";
	}else if(ids == "2"){
		str = "<font color='red'>未批准</font>";
	}
	return str;
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
			<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/hr/imgs/icon_zpjhgl.png">
			<span class="title">招聘计划管理</span>
		</div>
		<div class = "right fr clearfix">
		    <input type="button" class="btn-win-white fl" onclick="editFunc(0);" value="添加招聘计划"/>
			<input type="button" class="btn-del-red fl" onclick="batchDeleteFunc();" value="批量删除"/>
		    <input type="button" class="btn-win-white fl advancedSearch"  value="高级检索"/>
		    
		    <form id="form1" class='ad_sea_Content'>
		       <table class="TableBlock" width="100%"  style="font-size:12px" id="searchTable">
		          <tr>
		             <td width="10%">计划编号：</td>
		             <td width="40%">
		                <input class="BigInput" type="text" id = "planNo" name='planNo' size="20"/>
		             </td>
		             <td width="10%">计划名称：</td>
		             <td width="40%">
		                <input class="BigInput" type="text" id = "planName" name='planName' size="20"/>
		             </td>
		          </tr>
		          <tr>
		             <td width="10%">计划状态：</td>
		             <td width="40%">
		                <select id="planStatus" name="planStatus" class="BigSelect">
							<option value="">请选择</option>
							<option value="0">待审批</option>
							<option value="1">已批准</option>
							<option value="2">未批准</option>
						</select>
		             </td>
		             <td width="10%">审批人：</td>
		             <td width="40%">
		                <input type=hidden name="approvePersonId" id="approvePersonId" value="">
						<input type="text" name="approvePersonName" id="approvePersonName" class="BigStatic BigInput" size="10"  readonly value=""></input>
						<span class='addSpan'>
						   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onClick="selectSingleUser(['approvePersonId', 'approvePersonName']);" value="选择"/>
						   &nbsp;&nbsp;
						   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onClick="$('#approvePersonId').val('');$('#approvePersonName').val('');" value="清空"/>
						</span>
						
		             </td>
		          </tr>
		          <tr>
		             <td width="10%">招聘说明：</td>
		             <td width="40%">
		                <input class="BigInput" type="text" id = "recruitDescription" name='recruitDescription' size="20"/>
		             </td>
		             <td width="10%">招聘备注：</td>
		             <td width="40%">
		                <input class="BigInput" type="text" id = "recruitRemark" name='recruitRemark' size="20"/>
		             </td>
		          </tr>
		          <tr>
					<td>招聘开始日期：</td>
					<td colspan="3">
						<input  type="text" id = "startDateStrMin" name='startDateStrMin' size="20" class="BigInput Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value="" />
						至
						<input  type="text" id = "startDateStrMax" name='startDateStrMax' size="20" class="BigInput Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value=""/>
					</td>
				</tr>
				<tr>
					<td>招聘结束日期：</td>
					<td colspan="3">
						<input  type="text" id = "endDateStrMin" name='endDateStrMin' size="20" class="BigInput Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value="" />
						至
						<input  type="text" id = "endDateStrMax" name='endDateStrMax' size="20" class="BigInput  Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value=""/>
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

		<!-- <form id="form1" name="form1" method="post">
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
									<td class="TableData">计划编号：</td>
									<td class="TableData">
										<input class="BigInput" type="text" id = "planNo" name='planNo' size="20"/>
									</td>
									<td class="TableData">名称：</td>
									<td class="TableData">
										<input class="BigInput" type="text" id = "planName" name='planName' size="20"/>
									</td>
								</tr>
								<tr>
									<td class="TableData">计划状态：</td>
									<td class="TableData">
										<select id="planStatus" name="planStatus" class="BigSelect">
											<option value="">请选择</option>
											<option value="0">待审批</option>
											<option value="1">已批准</option>
											<option value="2">未批准</option>
										</select>
									</td>
									<td class="TableData">审批人：</td>
									<td class="TableData">
										<input type=hidden name="approvePersonId" id="approvePersonId" value="">
										<input  name="approvePersonName" id="approvePersonName" class="BigStatic BigInput" size="10"  readonly value=""></input>
										<span>
											<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['approvePersonId', 'approvePersonName']);">添加</a>
											<a href="javascript:void(0);" class="orgClear" onClick="$('#approvePersonId').val('');$('#approvePersonName').val('');">清空</a>
										</span>
									</td>
								</tr>
								<tr>
									<td class="TableData">招聘说明：</td>
									<td class="TableData">
										<input class="BigInput" type="text" id = "recruitDescription" name='recruitDescription' size="20"/>
									</td>
									<td class="TableData">招聘备注：</td>
									<td class="TableData">
										<input class="BigInput" type="text" id = "recruitRemark" name='recruitRemark' size="20"/>
									</td>
								</tr>
								<tr>
									<td class="TableData">招聘开始日期：</td>
									<td colspan="3" class="TableData">
										<input class="BigInput" type="text" id = "startDateStrMin" name='startDateStrMin' size="20" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value="" />
										至
										<input class="BigInput" type="text" id = "startDateStrMax" name='startDateStrMax' size="20" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value=""/>
									</td>
								</tr>
								<tr>
									<td class="TableData">招聘结束日期：</td>
									<td colspan="3" class="TableData">
										<input class="BigInput" type="text" id = "endDateStrMin" name='endDateStrMin' size="20" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value="" />
										至
										<input class="BigInput" type="text" id = "endDateStrMax" name='endDateStrMax' size="20" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value=""/>
									</td>
								</tr>
								<tr align="center">
									<td class="TableData" colspan="4">
										<input type="button" class="btn btn-primary" onclick="doSearch();" value="查询">
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