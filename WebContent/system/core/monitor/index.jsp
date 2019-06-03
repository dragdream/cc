<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>下属监控</title>
<style>
	.modal-test{
		width: 500px;
		height: 230px;
		position: absolute;
		display: none;
		z-index: 999;
	}
	.modal-test .modal-header{
		width: 100%;
		height: 50px;
		background-color:#8ab0e6;
	}
	.modal-test .modal-header .modal-title{
		color: #fff;
		font-size: 16px;
		line-height:50px;
		margin-left:20px;
		float: left;
	}
	.modal-test .modal-header .modal-win-close{
		color:#fff;
		font-size: 16px;
		line-height:50px;
		margin-right:20px;
		float: right;
		cursor: pointer;
	}
	.modal-test .modal-body{
		width: 100%;
		height: 90px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-body ul{
		overflow: hidden;
		clear:both;
	}
	.modal-test .modal-body ul li{
		width: 510px;
		height: 30px;
		line-height: 30px;
		margin-top: 25px;
		margin-left: 20px;
	}
	.modal-test .modal-body ul li span{
		display: inline-block;
		float:left;
		vertical-align: middle;
	}
	.modal-test .modal-body ul li input{
		display: inline-block;
		/* float: left; */
		width: 400px;
		height: 25px;
		
	}
	.modal-test .modal-footer{
		width: 100%;
		height: 60px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-footer input{
		margin-top:12px;
		float: right;
		margin-right:20px;
	}
</style>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	$(function(){
		query();
	});
	var datagrid;
	function query(){
		datagrid = $('#datagrid').datagrid({
			url : contextPath+'/monitor/monitorList.action',
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			toolbar : '#toolbar',
			title : '',
			singleSelect:true,
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			/* pageList : [ 20, 30, 40, 50, 60, 70, 80, 90, 100 ], */
			fit : true,
			fitColumns : false,
			nowrap : true,
			border : false,
			idField : 'id',
			sortOrder: 'desc',
			striped: true,
			remoteSort: false,
			columns : [ [ {
				field : 'userName',
				title : '人员',
				width:200
			}, {
				field : 'deptName',
				title : '所属部门',
				width:200
			}, {
				field : 'scheduleCount',
				title : '计划',
				width:130,
				formatter:function(data,rowData){
					if(data==0){
						return data;
					}
					return "<a href=\"javascript:shaoDetail(" +rowData.userId +",1)\">"+data+"</a>";
				}
			} ,{
				field : 'taskCount',
				title : '任务',
				width:130,
				formatter:function(data,rowData){
					if(data==0){
						return data;
					}
					return "<a href=\"javascript:shaoDetail(" +rowData.userId +",2)\">"+data+"</a>";
				}
			},{
				field : 'customerCount',
				title : '客户',
				width:130,
				formatter:function(data,rowData){
					if(data==0){
						return data;
					}
					return "<a href=\"javascript:shaoDetail(" +rowData.userId +",3)\">"+data+"</a>";
				}
			},{
				field : 'runCount',
				title : '工作',
				width:130,
				formatter:function(data,rowData){
					if(data==0){
						return data;
					}
					return "<a href=\"javascript:shaoDetail(" +rowData.userId +",4)\">"+data+"</a>";
				}
			},{
				field : 'diaryCount',
				title : '日志',
				width:130,
				formatter:function(data,rowData){
					if(data==0){
						return data;
					}
					return "<a href=\"javascript:shaoDetail(" +rowData.userId +",5)\">"+data+"</a>";
				}
			},{
				field : 'mailCount',
				title : '邮件',
				width:130,
				formatter:function(data,rowData){
					if(data==0){
						return data;
					}
					return "<a href=\"javascript:shaoDetail(" +rowData.userId +",6)\">"+data+"</a>";
				}
			}
			] ],
			onLoadSuccess:function(){
				$("#datagrid").datagrid("unselectAll");
			}
		});
	}
	
	function shaoDetail(id,flag){
		var createTimeStrMin = $("#createTimeStrMin").val(); 
		var createTimeStrMax = $("#createTimeStrMax").val(); 
		var dateStr  = "&createTimeStrMin=" + createTimeStrMin + "&createTimeStrMax=" + createTimeStrMax;
		var url = "";
		if(flag == 1){
			url = "scheduleList.jsp?userId=" + id + dateStr;
		}else if(flag == 2){
			url = "taskList.jsp?userId=" + id + dateStr;
		}else if(flag == 3){
			url = "customerList.jsp?userId=" + id + dateStr;  
		}else if(flag == 4){
			url = "runList.jsp?userId=" + id + dateStr;
		}else if(flag == 5){
			url = "diaryList.jsp?userId=" + id + dateStr;
		}else if(flag == 6){
			url = "mailList.jsp?userId=" + id + dateStr;
		}
		if(url){
			location.href = url;
		}
	}
	
	//验证必填
	function check(){
		var createTimeStrMin=$("#createTimeStrMin").val();
		var createTimeStrMax=$("#createTimeStrMax").val();
		if(createTimeStrMin==""||createTimeStrMin==null){
			 $.MsgBox.Alert_auto("请填写开始日期！",function(){
				 return false;
			 });  
			return false;
		}
		if(createTimeStrMax==""||createTimeStrMax==null){
			$.MsgBox.Alert_auto("请填写结束日期！",function(){
				return false;
			}); 
			return false;
		}
		return true;
	}
	
	//根据条件查询
	function doSearch(){
		if(check()){
			var queryParams=tools.formToJson($("#form1"));
			datagrid.datagrid('options').queryParams=queryParams; 
			datagrid.datagrid("reload");
			$(".modal-win-close").click();	
		}
		
		/* $('#searchDiv').modal('hide'); */
	}

	</script>
</head>
<style>
	.t_btns>button{
		padding:5px 8px;
		padding-left:22px;
		text-align:right;
		background-repeat:no-repeat;
		background-position:6px center;
		background-size:17px 17px;
		border-radius:5px;
		background-color:#e6f3fc;
		border:none;
		color:#000;
		outline:none;
		font-size: 12px;
		border: #abd6ea solid 1px ;
	}
</style>
<body  style="padding-left: 10px;padding-right: 10px">
<table id="datagrid"></table>

<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gztj/icon_gongzuotongji.png">
		<span class="title">工作统计</span>
	</div>
	<div class = "right fr clearfix t_btns">
	    <button type="button" onclick="$(this).modal();" value="高级检索" class="modal-menu-test"
		style="background-image:url(<%=contextPath %>/common/zt_webframe/imgs/grbg/gztj/icon_gaojijiansuo.png)"
		>&nbsp;高级检索</button>
		<%-- <input  type="button" style="background-color:transparent;width:102px;height:32px;background-image:url(<%=contextPath %>/common/zt_webframe/imgs/grbg/gztj/icon_gaojijiansuo.png);background-size:100% 100%;color:#fff;border:none;padding-left:15px;" class="btn-win-white fl modal-menu-test" onclick="$(this).modal()" value="高级检索"/> --%>
    </div>
</div>


<!-- 模态框Modal -->
<form id="form1" name="form1">
<div class="modal-test">
	<div class="modal-header">
		<p class="modal-title">
			查询条件
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body" id="mailBoxForm">
		<ul>
			<li class="clearfix">
				<span>创建日期:</span>
				<input type="text" id="createTimeStrMin" name="createTimeStrMin"
				 style="width: 130px;margin-left: 20px;"
				 class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'createTimeStrMax\')}'})" required value="" />
				 &nbsp;至 &nbsp;
				 <input type="text" id="createTimeStrMax" name="createTimeStrMax" 
				  style="width: 130px;"
				 class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'createTimeStrMin\')}'})" required value=""/>
			</li>
		</ul>
	</div>
	<div class="modal-footer clearfix">
		<input class = "btn-alert-gray" type="reset" value='重置' onclick=""/>
		<input class = "modal-save btn-alert-blue" type="button" onclick="doSearch();" value = '查询'/>
	</div>
</div>
</form>
</body>
</html>