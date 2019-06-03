<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String sid = request.getParameter("sid");//bodyId
	String subject=request.getParameter("subject");//邮件主题
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/email/js/email.js"></script>
<script>
var sid='<%=sid%>';
var subject='<%=subject%>';
var datagrid;
function doInit(){
	//邮件主题赋值
	$("#subject").html(subject);
	
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/emailController/getSignDetail.action?sid="+sid,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID'},
			{field:'toUserName',title:'收件人' ,width:100,formatter:function(e,rowData){
				var toUserName =rowData.toUserName; 
				return toUserName;
			}},
			{field:'deptName',title:'所属部门',width:100,formatter:function(e,rowData){
				var deptName =rowData.deptName; 
				return deptName;
			}},
			{field:'roleName',title:'所属角色',width:100,formatter:function(e,rowData){
				var roleName =rowData.roleName; 
				return roleName;
			}}, 
			{field:'status',title:'阅读状态',width:100,formatter:function(e,rowData){
				var deleteStatus =rowData.deleteFlag;
				var readStatus=rowData.readFlag;
				var readTime=rowData.readTimeStr;
				var timeDesc="";
				if(readTime==null||readTime=="null"||readTime==""){
					timeDesc="";
				}else{
					timeDesc="&nbsp;&nbsp;("+readTime+")";
				}
				if(deleteStatus==1){
					return "已删除";		
				}else{
					if(readStatus==0){
						return "<span style='color:red;'>未读<span>";		
					}else{
						return "<span style='color:green'>已读"+timeDesc+"</span>";		
					}
	
				}
			}} 
		]],onLoadSuccess: function(data){//加载完毕后获取所有的checkbox遍历
            if (data.rows.length > 0) {
                //循环判断操作为新增的不能选择
                for (var i = 0; i < data.rows.length; i++) {
                    //根据operate让某些行不可选
                    if (data.rows[i].readFlag == 1 || data.rows[i].deleteFlag == 1) {
                        $("input[type='checkbox']")[i + 1].style.display = "none";
                    }
                }
            }
        },onClickRow: function(rowIndex, rowData){
            //加载完毕后获取所有的checkbox遍历
            $("input[type='checkbox']").each(function(index, el){
                //如果当前的复选框不可选，则不让其选中
                if (el.disabled == true) {
                    $("#datagrid").datagrid('unselectRow', index - 1);
                }
            });
        }
	});
}


//收回邮件
function back(){
	//获取选中的项
	var ids = getSelectItem();
	if(ids.length==0){
		//$.jBox.tip("未选中任何项！", "info", {timeout: 1800});
		$.MsgBox.Alert_auto("未选中任何项！");
		return ;
	}else{
		var url =  "<%=contextPath %>/emailController/back.action";
		var jsonObj = tools.requestJsonRs(url,{ids:ids});
		if(jsonObj.rtState){
			//$.jBox.tip("成功收回邮件！", "info", {timeout: 1800});
			$.MsgBox.Alert_auto("成功收回邮件！");
			$("#datagrid").datagrid("reload");
			
		} 
	}
	
	
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<span  id="subject" class="title" style="display: none;" ></span><span class="title">阅读详情</span>
	</div>
	<div class = "right fr clearfix">
		<input type="button" class="btn-win-white fl" onclick="back();" value="收回邮件"/>
    </div>
</div>
<iframe id="exportIframe" style="display:none"></iframe>
</body>
</html>