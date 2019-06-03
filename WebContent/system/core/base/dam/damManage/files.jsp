<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<%
   String id=TeeStringUtil.getString(request.getParameter("id"));
%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>文件管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
var id="<%=id %>";

var datagrid;
function  doInit(){
	//初始化保管期限
	getSysCodeByParentCodeNo("DAM_RT" , "retentionPeriod");
	
	getList();
}
/**
 *查询管理
 */
function getList(){
	var params=tools.formToJson($("#form1"));
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeDamFilesController/getFileListByBoxOrHouse.action?id="+id,
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
			{field:'number',title:'文件编号',width:200},
			{field:'viewTotal',title:'借阅次数',width:200},
			{field:'opt_',title:'操作',width:300,formatter:function(value,rowData,rowIndex){
				var opt="<a  href='#' onclick='detail("+rowData.sid+")'>查看</a>&nbsp;&nbsp;&nbsp;";
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
	var url=contextPath+"/system/core/base/dam/fileManagement/file/detail.jsp?sid="+sid;
    openFullWindow(url);
}


//删除
function del(sid){
	 $.MsgBox.Confirm ("提示", "确定要删除该档案吗？", function(){
		  var url = contextPath + "/TeeDamFilesController/logicDel.action";
			var para = {sid:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){					
				$.MsgBox.Alert_auto("删除成功！");
				datagrid.datagrid('reload');
			}   
	  });
}


//重置form表单
function  resetForm(){
	document.getElementById("form1").reset();
}


//显示高级搜索面板
function showSearchPanel(){
	$(".ad_sea_Content").css('top',0);//给面板设置top=0，贴到最上岩
	
	$(".ad_sea_Content").slideToggle(200);//切换显示
	
	if($(".ad_sea_Content").hasClass("searchOpen")){//显示前
		$(".serch_zhezhao").remove();
		$(".ad_sea_Content").removeClass("searchOpen");
// 		$(".ad_sea_Content").css({"border":"1px solid #0d93f6",});
		$(".ad_sea_Content").css('border-bottom','1px solid #0d93f6');
	}else{
		$(".ad_sea_Content").addClass("searchOpen");//显示时
// 		$(".ad_sea_Content").css({"border":"1px solid #dadada",'border-bottom':'1px solid #fff'});
		$(".ad_sea_Content").css('border-bottom','1px solid #0d93f6');
		$('body').append('<div class="serch_zhezhao"></div>');
	}

	$(".searchCancel").click(function(){
		$(".ad_sea_Content").removeClass("searchOpen");
		$("#form1").slideUp(200);
		$(".serch_zhezhao").remove();
		
	});
}

</script>
</head>
<body onload="doInit();" style="margin:0px;padding:0px;overflow: auto">
  <table id="datagrid" fit="true"></table>
  
<form id="form1" class='ad_sea_Content'>
    <table class="TableBlock" width="100%"  style="font-size:12px" id="searchTable">
        <tr>
           <td width="15%">组织机构代码：</td>
           <td width="35%">
              <input type="text" name="orgCode" id="orgCode"/>
           </td>
           <td width="15%">全宗号：</td>
           <td width="35%">
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
</body>

</html>