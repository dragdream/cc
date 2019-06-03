<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>档案查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>
<script type="text/javascript">
var datagrid;
function  doInit(){
	//初始化保管期限
	getSysCodeByParentCodeNo("DAM_RT" , "retentionPeriod");
	
	query();
}
/**
 *查询管理
 */
function query(){
	var params = tools.formToJson($("#form1"));
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeDamFilesController/queryAllArchivedFiles.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		queryParams:params,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:200}, */
			{field:'title',title:'文件标题',width:200},
			{field:'number',title:'编号',width:200},
			{field:'unit',title:'发/来文单位',width:250},
			{field:'retentionPeriodStr',title:'保管期限',width:200},
			{field:'mj',title:'密级',width:200},
			{field:'hj',title:'紧急程度',width:200},
			{field:'createTimeStr',title:'创建日期',width:250},
			{field:'opt_',title:'操作',width:200,formatter:function(value,rowData,rowIndex){
				var opt="<a  href='#' onclick='detail("+rowData.sid+")'>查看</a>&nbsp;&nbsp;&nbsp;";
				if(rowData.viewFlag==0){//未借阅
					opt+="<a  href='#' onclick='borrow("+rowData.sid+")'>借阅</a>&nbsp;&nbsp;&nbsp;";
				}
			    return opt;
			}}
		]]
	});	
	
	//隐藏高级查询form表单
	$(".searchCancel").click();
}


//查看档案的简单详情
function  detail(sid){
	var  url=contextPath+"/system/core/base/dam/borrow/basicDetail.jsp?sid="+sid;
    openFullWindow(url);
}


//借阅
function borrow(sid){
	 $.MsgBox.Confirm ("提示", "是否确认借阅该档案？", function(){
		  var url = contextPath + "/TeeFileBorrowController/borrow.action";
			var para = {fileId:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){					
				$.MsgBox.Alert_auto("申请成功！");
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
		$(".ad_sea_Content").css('border-bottom','1px solid #0d93f6');
	}else{
		$(".ad_sea_Content").addClass("searchOpen");//显示时
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
<body class="" onload="doInit();">
 
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
<input type='button' class='btn-win-white' value='查询' onclick="query()">&nbsp;&nbsp;&nbsp;
<input type='button' class='btn-win-white' value='重置' onclick='resetForm()'>&nbsp;&nbsp;&nbsp;
<input type='button' class='btn-win-white searchCancel' value='取消'>
</div>
  </form>
</body>

</html>