<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>预归档管理</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>

var datagrid ;
function doInit(){
	//初始化保管期限
	getSysCodeByParentCodeNo("DAM_RT" , "retentionPeriod");
	query();
}

function query(){
	var params = tools.formToJson($("#form1"));
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeDamFilesController/getPreFileList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		queryParams:params,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:200},
			{field:'title',title:'文件标题',width:200},
			{field:'number',title:'编号',width:200},
			{field:'unit',title:'发/来文单位',width:200},
			{field:'retentionPeriodStr',title:'保管期限',width:200},
			{field:'mj',title:'密级',width:200},
			{field:'hj',title:'紧急程度',width:200},
			{field:'createTimeStr',title:'创建日期',width:250},
			{field:'opt_',title:'操作',width:300,formatter:function(value,rowData,rowIndex){
				var opt="<a  href='#' onclick='detail("+rowData.sid+")'>查看</a>&nbsp;&nbsp;&nbsp;";
				opt+="<a  href='#' onclick='turnOver("+rowData.sid+")'>移交</a>&nbsp;&nbsp;&nbsp;";
				opt+="<a  href='#' onclick='edit("+rowData.sid+")'>整理</a>&nbsp;&nbsp;&nbsp;";
				opt+="<a  href='#' onclick='del("+rowData.sid+")'>删除</a>&nbsp;&nbsp;&nbsp;";
			    return opt;
			}}
		]]
	});
	
	//隐藏高级查询form表单
	$(".searchCancel").click();
}

//查看
function detail(sid){
	var url=contextPath+"/system/core/base/dam/preArchive/manage/detail.jsp?sid="+sid;
    window.location.href=url;
}


//批量移交
function turnOverBatch(){
	var selections = $("#datagrid").datagrid("getSelections");
	if(selections.length==0){
		$.MsgBox.Alert_auto("未选中任何数据！");
		return;
	}else{
		var ids = [];
		for(var i=0;i<selections.length;i++){
			ids.push(selections[i].sid);
		}
	  var url=contextPath+"/system/core/base/dam/preArchive/manage/turnOver.jsp?ids="+ids.join(",")+"&&opt="+"batch";
	  bsWindow(url ,"档案移交",{width:"500",height:"100",buttons:
		[
         {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){
		    var json=cw.turnOver();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("移交成功！",function(){
		    		datagrid.datagrid("reload");
		    	});
		    	return true;
		    }else{
		    	$.MsgBox.Alert_auto("移交失败！");
		    	return false;
		    }
		}else if(v=="关闭"){
			return true;
		}
	}});
  }
}
//移交
function turnOver(sid){
	var url=contextPath+"/system/core/base/dam/preArchive/manage/turnOver.jsp?ids="+sid+"&&opt="+"single";
	bsWindow(url ,"档案移交",{width:"500",height:"100",buttons:
		[
         {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){
		    var json=cw.turnOver();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("移交成功！",function(){
		    		datagrid.datagrid("reload");
		    	});
		    	return true;
		    }else{
		    	$.MsgBox.Alert_auto("移交失败！");
		    	return false;
		    }
		}else if(v=="关闭"){
			return true;
		}
	}});
}
//新建分类
function add(){
	var url=contextPath+"/system/core/base/dam/preArchive/manage/add.jsp";
    window.location.href=url;
}



//编辑分类
function edit(sid){
	var url=contextPath+"/system/core/base/dam/preArchive/manage/edit.jsp?sid="+sid;
    window.location.href=url;
}


//删除分类
function del(sid){
	$.MsgBox.Confirm ("提示", "是否确认删除该档案？", function(){
		  var url = contextPath + "/TeeDamFilesController/delBySid.action";
			var para = {sid:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){					
				$.MsgBox.Alert_auto("删除成功！");
				datagrid.datagrid('reload');
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}   
	  });
	
}



//重置form表单
function  resetForm(){
	document.getElementById("form1").reset(); 
	$(".customTr").remove();
}



</script>
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">

<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/dam/imgs/ygdgl.png">
		<span class="title">预归档管理</span>
	</div>
	<div class = "right fr clearfix">
		<input type="button" class="btn-win-white fl" onclick="add();" value="新建档案"/>
		<input type="button" class="btn-win-white fl" onclick="turnOverBatch()" value="批量移交"/>
		<input type="button" class="btn-win-white fl advancedSearch"  value="高级查询"/>
     </div>
     
     <form id="form1" class='ad_sea_Content'>
       <table class="TableBlock" width="100%"  style="font-size:12px" id="searchTable">
          <tr>
             <td width="10%">组织机构代码：</td>
             <td width="40%">
                <input type="text" name="orgCode" id="orgCode"/>
             </td>
             <td width="10%">全宗号：</td>
             <td width="40%">
                <input type="text" name="qzh" id="qzh"/>
             </td>
          </tr>
          <tr>
             <td>年份：</td>
             <td>
                <input type="text" name="year" id="year"/>
             </td>
             <td>保管期限：</td>
             <td>
                <select id="retentionPeriod" name="retentionPeriod">
                  <option value="">请选择</option>
                </select>
             </td>
          </tr>
          <tr>
             <td>文件标题：</td>
             <td>
                <input type="text" id='title' name='title'/>
             </td>
             <td>发/来文单位：</td>
             <td>
               <input type="text" id='unit' name='unit'/>
             </td>
          </tr>
          <tr>
             <td>文件编号：</td>
             <td>
                <input type="text" name="number" id="number"/>
             </td>
             <td>主题词：</td>
             <td>
                <input type="text" id="subject" name="subject"/>
             </td>
          </tr>
          <tr>
             <td>密级：</td>
             <td>
               <select id="mj" name="mj">
                  <option value=" ">请选择</option>
                  <option value="">空</option>
                  <option value="内部">内部</option>
                  <option value="秘密">秘密</option>
                  <option value="机密">机密</option>
                  <option value="绝密">绝密</option>
               </select>
             </td>
             <td>缓急：</td>
             <td>
                 <select id="hj" name="hj">
                 <option value=" ">请选择</option>
                  <option value="">空</option>
                  <option value="普通">普通</option>
                  <option value="急">急</option>
                  <option value="加急">加急</option>
                  <option value="特急">特急</option>
                  <option value="特提">特提</option>
                  <option value="平急">平急</option>
               </select>
             </td>
          </tr>
          <tr>
             <td>备注：</td>
             <td colspan="3">
                 <textarea rows="3" cols="50" id="remark" name="remark"></textarea>
             </td>
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