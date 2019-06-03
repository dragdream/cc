<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String itemId = request.getParameter("itemId");
%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<script type="text/javascript" charset="UTF-8">
	var itemId = '<%=itemId%>';
	
	var datagrid;
	
	function doInit(){
		datagrid = $('#datagrid').datagrid({
	        url: contextPath + '/CommonWord/testDatagrid.action',
	        pagination:true,
			singleSelect:false,
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			toolbar:'#toolbar',//工具条对象
			checkbox:true,
			singleSelect:true,
			border:false,
			fitColumns:true,//列是否进行自动宽度适应
	        columns: [[
	            {field : 'cyy',	title : '常用语',width:200,formatter:function(value, rowData, rowIndex){
					return "<span id='span_cyy_"+rowIndex+"'>"+value+"</span><input id='input_cyy_"+rowIndex+"' value='"+value+"' style='display:none'/>";
	            }},
			    {field : 'cis',title : '使用频次',width:100,formatter:function(value, rowData, rowIndex){
					return "<span id='span_cis_"+rowIndex+"'>"+value+"</span><input id='input_cis_"+rowIndex+"' value='"+value+"' style='display:none'/>";
		    	
			    
			    }},
			    {field : '_optmanage',title : '操作',width : 300,
				formatter : function(value, rowData, rowIndex) {
				     return "<a href='javascript:void(0)' onclick=\"edit("+rowIndex+",this)\">编辑</a>&nbsp;<a href='javascript:void(0)' onclick=\"del('"+rowData.sid+"')\">删除</a>";
				}},
			 ]]
	      });
	}
	
	function del(sid){
		$.MsgBox.Confirm ("提示", "确认要删除该常用语吗？", function(){
			var url = contextPath + "/CommonWord/deleteCm.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			  datagrid.datagrid("reload");
		});
	}
	
	function edit(index,obj){
		var data = $('#datagrid').datagrid("getData");
		if(!data.isEdit){
			$("#span_cyy_"+index).hide();
			$("#span_cis_"+index).hide();
			$("#input_cyy_"+index).show();
			$("#input_cis_"+index).show();
			$(obj).html("保存");
			data.isEdit = true;
		}else{
			var cyy = $("#input_cyy_"+index).val();
			var cis = $("#input_cis_"+index).val();
			tools.requestJsonRs(contextPath+"/CommonWord/updateCommonWord.action",{sid:data.sid,cyy:cyy,cis:cis});
			data.isEdit = false;
			$("#span_cyy_"+index).show().html(cyy);
			$("#span_cis_"+index).show().html(cis);
			$("#input_cyy_"+index).hide().val(cyy);
			$("#input_cis_"+index).hide().val(cis);
			$(obj).html("编辑");
		}
	}
	
	function checkForm(){
	    var check= $("#form1").valid(); 
	    if(!check){
	    	return false;
	    }
	    return true;
	}

	function commit(){
		if($("#cyy").val()==""){
			$.MsgBox.Alert_auto("请输入常用语文字！");
			return;
		}
	
		if(checkForm()){

		var url = "";
		var para = tools.formToJson($("#form1"));
		url = contextPath+"/CommonWord/addCm.action";
		var jsonRs = tools.requestJsonRs(url,para);
		parent.$.MsgBox.Alert_auto("添加成功！");
		  datagrid.datagrid("reload");
		}
	}
	</script>
	<style>
		input.error,label.error{
			vertical-align:middle!important;
		}
	</style>
</head>
<body onload="doInit()" style="overflow:hidden;padding-left: 10px;padding-right: 10px">
<table id="datagrid" fit="true"></table>
     <div id="toolbar" class="topbar clearfix">
         <div class="fl">
         <img class="title_img" src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_cyywh.png" alt="" />
         &nbsp;<span class="title">常用语维护</span>
         </div>
         <div align="center">
		  	<form name="form1" id="form1" style="margin: 0 auto;width:650px;">
			  <div class="fl" style="margin-right: 10px;">
			    <label for="cyy">常用语：</label>
			    <input type="text" style="line-height: 25px;font-family: MicroSoft YaHei;font-size: 12px;" id="cyy" name="cyy" placeholder="请输入要添加的常用语">
			  </div>
			  <div class="fl" style="margin-right: 20px;">
			    <label for="cis">使用频次：</label>
			    <input type="text" style="line-height: 25px;font-family: MicroSoft YaHei;font-size: 12px;" id="cis" name="cis" placeholder="请输入整数" positive_integer="true" />
			  </div>
			  <input type="button" class="btn-win-white" style="line-height: 20px; width:50px;"  value="创建" onclick="commit()"/>
			</form>
		</div>
     </div>

</body>
</html>