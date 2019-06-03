<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>文件管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>
<script type="text/javascript">
var datagrid;
function  doInit(){
	//初始化保管期限
	getSysCodeByParentCodeNo("DAM_RT" , "retentionPeriod");
	
	renderPreArchiveType();
	
	getList();
}


//渲染预归档分类
function renderPreArchiveType(){
	var url=contextPath+"/preArchiveTypeController/getAllPreArchiveType.action";
	var json=tools.requestJsonRs(url,{});
	if(json.rtState){
		var data=json.rtData;
		if(data!=null&&data.length>0){
			 for(var i=0;i<data.length;i++){
				 $("#typeId").append("<option value="+data[i].sid+">"+data[i].typeName+"</option>");		 
			 }
		}
	}
}






/**
 *查询管理
 */
function getList(){
	var params = tools.formToJson($("#form1"));
	params["typeId"]=$("#typeId").val();
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeDamFilesController/getManageFileList.action",
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
				opt+="<a  href='#' onclick='drawBack("+rowData.sid+")'>退回</a>&nbsp;&nbsp;&nbsp;";
				opt+="<a  href='#' onclick='edit("+rowData.sid+")'>整理</a>&nbsp;&nbsp;&nbsp;";
			    return opt;
			}}
		]]
	});
	
	//隐藏高级查询form表单
	$(".searchCancel").click();
}

//退回
function drawBack(sid){
	 $.MsgBox.Confirm ("提示", "是否确认将该档案退回给档案创建人？", function(){
		  var url = contextPath + "/TeeDamFilesController/drawBack.action";
			var para = {ids:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){					
				$.MsgBox.Alert_auto("退回成功！");
				datagrid.datagrid('reload');
			}   
	  });
	
}
//查看 
function detail(sid){
	var url=contextPath+"/system/core/base/dam/fileManagement/file/detail.jsp?sid="+sid;
    openFullWindow(url);
}

//整理
function edit(sid){
	var url=contextPath+"/system/core/base/dam/fileManagement/file/edit.jsp?sid="+sid;
    openFullWindow(url);
}

//批量退回
function drawBackBatch(){
	var selections = $("#datagrid").datagrid("getSelections");
	if(selections.length==0){
		$.MsgBox.Alert_auto("未选中任何数据！");
		return;
	}else{
		var ids = [];
		for(var i=0;i<selections.length;i++){
			ids.push(selections[i].sid);
		}
		 $.MsgBox.Confirm ("提示", "是否确认将所选档案退回给档案创建人？", function(){
			  var url = contextPath + "/TeeDamFilesController/drawBack.action";
				var para = {ids:ids.join(",")};
				var json = tools.requestJsonRs(url,para);
				if(json.rtState){					
					$.MsgBox.Alert_auto("退回成功！");
					datagrid.datagrid('reload');
				}   
		  });
		
	}
}


//分配卷盒
function distributeBox(){
	var selections = $("#datagrid").datagrid("getSelections");
	if(selections.length==0){
		$.MsgBox.Alert_auto("未选中任何数据！");
		return;
	}else{
		var ids = [];
		for(var i=0;i<selections.length;i++){
			ids.push(selections[i].sid);
		}
		var url=contextPath+"/system/core/base/dam/fileManagement/file/distributeBox.jsp?ids="+ids.join(",");
		bsWindow(url ,"分配卷盒",{width:"500",height:"120",buttons:
			[
             {name:"确定",classStyle:"btn-alert-blue"},
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="确定"){
			    var json=cw.distributeBox();
			    if(json.rtState){
			    	$.MsgBox.Alert_auto("卷盒分配成功！",function(){
			    		datagrid.datagrid("reload");
			    	});
			    	return  true;
			    }else{
			    	$.MsgBox.Alert_auto("卷盒分配失败！");
			    	return false;
			    }
			}else if(v=="关闭"){
				return true;
			}
		}}); 
	}
}



//重置form表单
function  resetForm(){
	document.getElementById("form1").reset(); 
}
</script>
</head>
<body class="" onload="doInit();">

  <div id="toolbar" class = "clearfix">
    <div class="left fl setHeight">
	   <input type="button" value="批量退回" class="btn-del-red fr" onclick="drawBackBatch()">
	   &nbsp;
	   <input type="button" value="分配卷盒" class="btn-win-white fr" onclick="distributeBox()"> 
    </div> 
    <div class="fr right setHeight">
       <input style="float:right" type="button" class="btn-win-white advancedSearch"  value="高级查询"/>
       <span style="float:right">             
                     预归档分类：
	   <select id="typeId" name="typeId"  onchange="getList();">
	       <option value="0">所有分类</option>
	   </select>
	   </span>
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
		<input type='button' class='btn-win-white' value='查询' onclick="getList()">&nbsp;&nbsp;&nbsp;
		<input type='button' class='btn-win-white' value='重置' onclick='resetForm()'>&nbsp;&nbsp;&nbsp;
		<input type='button' class='btn-win-white searchCancel' value='取消'>
		</div>
    </form>
    </div> 
    
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