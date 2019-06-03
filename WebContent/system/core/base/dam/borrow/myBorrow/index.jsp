<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>我的借阅</title>
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
		url:contextPath + "/TeeFileBorrowController/getMyBorrow.action",
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
			/* {field:'sid',checkbox:true,title:'ID',width:200}, */
			{field:'fileTitle',title:'文件标题',width:200},
			{field:'fileNumber',title:'文件编号',width:200},
			{field:'fileUnit',title:'发/来文单位',width:250},
			{field:'fileRt',title:'保管期限',width:200},
			{field:'fileMj',title:'密级',width:200},
			{field:'fileHj',title:'紧急程度',width:200},
			{field:'viewTimeStr',title:'借阅时间',width:250},
			{field:'state',title:'借阅状态',width:250,formatter:function(value,rowData,rowIndex){
				var approveFlag=rowData.approveFlag;
				var returnFlag=rowData.returnFlag;
				if(approveFlag==0){
					return "待审批";
				}else if(approveFlag==1){//已批准
					if(returnFlag==0){
						return "已批准";
					}else if(returnFlag==1){
						return "归还中";
					}else if(returnFlag==2){
						return "已归还";
					}
				}else if(approveFlag==2){//未批准
					return "未批准";
				}
			}},
			{field:'opt_',title:'操作',width:200,formatter:function(value,rowData,rowIndex){
				var opt="";
				var approveFlag=rowData.approveFlag;
				var returnFlag=rowData.returnFlag;
				if(approveFlag==0){//待审批
					opt="<a  href='#' onclick='basicDetail("+rowData.fileId+")'>查看</a>&nbsp;&nbsp;&nbsp;";
					opt+="<a  href='#' onclick='del("+rowData.sid+")'>删除</a>&nbsp;&nbsp;&nbsp;";
				}else if(approveFlag==1){//已批准
					if(returnFlag==0){//已批准未归还
						opt="<a  href='#' onclick='detail("+rowData.fileId+")'>查看</a>&nbsp;&nbsp;&nbsp;";
						opt+="<a  href='#' onclick='back("+rowData.sid+")'>归还</a>&nbsp;&nbsp;&nbsp;";
					}else if(returnFlag==1){//归还中
						
					}else if(returnFlag==2){//已归还
						
					}
				}else if(approveFlag==2){//未批准
					opt="<a  href='#' onclick='del("+rowData.sid+")'>删除</a>&nbsp;&nbsp;&nbsp;";
				}
				
			    return opt;
			}}
		]]
	});	
	
	//隐藏高级查询form表单
	$(".searchCancel").click();
}


//查看档案的简单详情
function  basicDetail(sid){
	var  url=contextPath+"/system/core/base/dam/borrow/basicDetail.jsp?sid="+sid;
    openFullWindow(url);
}

//查看档案的全部详情
function  detail(sid){
	var url=contextPath+"/system/core/base/dam/fileManagement/file/detail.jsp?sid="+sid;
    openFullWindow(url);
}


//删除
function del(sid){
	 $.MsgBox.Confirm ("提示", "是否确认删除该借阅记录？", function(){
		  var url = contextPath + "/TeeFileBorrowController/delBySid.action";
			var para = {sid:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){					
				$.MsgBox.Alert_auto("删除成功！");
				datagrid.datagrid('reload');
			}   
	  });
}

//归还
function back(sid){
	$.MsgBox.Confirm ("提示", "是否确认归还该档案文件？", function(){
		  var url = contextPath + "/TeeFileBorrowController/giveBack.action";
			var para = {sid:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){					
				$.MsgBox.Alert_auto("归还申请成功！");
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
           <td>借阅状态：</td>
           <td>
               <select id="status" name="status">
	                <option value="0">请选择</option>
	                <option value="1">待审批</option>
	                <option value="2">已批准</option>
	                <option value="3">归还中</option>
	                <option value="4">已归还</option>
	                <option value="5">未批准</option>
               </select>
           </td>
           <td>备注：</td>
           <td>
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