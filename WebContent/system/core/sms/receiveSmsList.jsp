<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>

	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>消息盒子</title>
	
	<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	$(function(){
		query();
	});

	function query(){
		var param = {};
		param["moduleNo"] = $("#type").val();
		param["remindFlag"] = $("#remindFlag").val();
		datagrid = $('#datagrid').datagrid({
			url : contextPath+'/sms/getSmsBoxDatas.action',
			toolbar : '#toolbar',
			title : '',
			queryParams:param,
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			singleSelect:false,
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			//pageList : [30,60,90,120],
			fit : true,
			fitColumns : true,
			nowrap : true,
			border : false,
			idField : 'smsSid',
			sortOrder: 'desc',
			striped: true,
			remoteSort: false,
			onLoadSuccess:function(){
				//$("[title]").tooltips();
			},
			columns : [ [ {
				field : 'smsSid',
				title : '',
				width : 50,
				checkbox:true
			},{
				field : 'moduleNoDesc',
				title : '类型',
				width : 30
			}, {
				field : 'content',
				title : '消息内容',
				width : 150,
				formatter : function(value, rowData, rowIndex) {
					return "<a href='javascript:void(0)' onclick='view("+rowIndex+")' title='"+rowData.content+"'>"+rowData.content+"</a>";
				}
			} ,{
				field : 'sendTimeDesc',
				title : '发送时间',
				width :50
				//formatter : function(value, rowData, rowIndex) {
				//	return rowData;
				//}
			},{
				field : 'fromUser',
				title : '发送人',
				width : 30
			},{field:'_manage',title:'操作',width : 40,formatter:function(value,rowData,rowIndex){
			//	alert(rowData.uuid);
				return "<a href='#' onclick=\"deleteSmsList('"+rowData.smsSid+"')\">删除</a>&nbsp;";
			}}
			] ]
		});
	}

	function deleteSmsList(id){
		$.MsgBox.Confirm("提示","确定要删除所选中消息？",function(){
			var url = "<%=contextPath %>/sms/delSms.action";
			var jsonRs = tools.requestJsonRs(url,{'smsIds':id});
			if(jsonRs.rtState){
				parent.$.MsgBox.Alert_auto("删除成功！");
					datagrid.datagrid('unselectAll');
					datagrid.datagrid('reload');
				
			}else{
				$.MsgBox.Alert_auto(jsonRs.rtMsg);
			}
    	
		});
		}
			

    function view(rowIndex){
		var rows = $('#datagrid').datagrid('getRows');
		var url = rows[rowIndex].remindUrl;
		var sid = rows[rowIndex].smsSid;
		tools.requestJsonRs(contextPath+"/sms/updateReadFlag.action?ids="+sid);
		$('#datagrid').datagrid('reload');
		if(url==undefined || url==null || url==""){
			return;
		}
		window.openFullWindow(url,"");
	}
    
    function deleteBatch(){
    	var selections = $('#datagrid').datagrid('getSelections');
    	if(selections.length==0){
    		$.MsgBox.Alert_auto("至少选择一项!");
    		return ;
    	}
    	var ids = "";
		for(var i=0;i<selections.length;i++){
			ids+=selections[i].smsSid;
			if(i!=selections.length-1){
				ids+=",";
			}
		}
		deleteSmsList(ids);

    }
	</script>
</head>
<body>
<table id="datagrid" fit="true"></table>
<div id="toolbar" class="clearfix">
	<div style="position:static;" class="setHeight">
		<table class="">
			<tr>
				<td>消息类型：</td>
				<td>
					<select class="" id="type" onchange="query();">
						<option value="0">全部</option>
						<option value="000">系统</option>
						<option value="006">工作流</option>
						<option value="018">日志</option>
						<option value="019">邮件</option>
						<option value="020">新闻</option>
						<option value="021">公告</option>
						<option value="022">日程</option>
						<option value="023">考勤</option>
						<option value="024">公共网盘</option>
						<option value="025">个人网盘</option>
						<option value="026">个人通讯簿</option>
						<option value="027">公共通讯簿</option>
						<option value="028">办公用品</option>
						<option value="029">投票管理</option>
						<option value="030">固定资产</option>
						<option value="031">会议管理</option>
						<option value="032">车辆管理</option>
						<option value="050">讨论区</option>
					</select>
				</td>
				<td class="">消息状态：</td>
				<td>
					<select class="" id="remindFlag" onchange="query();">
						<option value="-1">所有</option>
						<option value="0" selected>未阅读</option>
						<option value="1">已阅读</option>
					</select>
				</td>
				<td>
				      <input style="top:5px;right:10px;position: absolute;" class="btn-del-red" type="button" onclick="deleteBatch()" value="批量删除"/>			
				</td>
			</tr>
		</table>
	</div>
</div>
</body>
</html>