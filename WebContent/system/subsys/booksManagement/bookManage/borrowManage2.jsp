<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<title>借书确认</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>




<script type="text/javascript">
var datagrid;


function doSave(){
    if (checkForm()){
        var url = "<%=contextPath%>/sms/addSms.action";
        var para =  tools.formToJson($("#form1")) ;
        var jsonRs = tools.requestJsonRs(url,para);
        if(jsonRs.rtState){
            top.$.jBox.tip("发送成功!","info");
            window.location.reload();
        }else{
            top.$.jBox.tip(jsonRs.rtMsg,"error");
        }
    }
}

function checkForm(){
    
    var userListNames = document.getElementById("userListNames");
        if (!userListNames.value) {
          top.$.jBox.tip("收信人不能为空！","error");
          userListNames.focus();
          userListNames.select();
          return false;
        }
    return $("#form1").form('validate'); 
}

function backIndex(){

    window.location.href = "<%=contextPath %>/system/core/org/role/manageRole.jsp";
}


function doInit(){
	booList();
	query();
}
function query(){
	var para =  tools.formToJson($("#form"));
    datagrid = $('#datagrid').datagrid({
        url : '<%=request.getContextPath() %>/bookManage/borrowManage2.action',
        toolbar : '#toolbar',
        queryParams : para,
        title : '',
        iconCls : 'icon-save',
        pagination : true,
        pageSize : 10,
        pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
        fit : true,
        fitColumns : true,
        nowrap : true,
        border : false,
        idField : 'sid',
        sortOrder: 'desc',
        striped: true,
        remoteSort: false,
        columns : [ [ 
         {
             field:'sid',
             checkbox:false,
             hidden:true
        },{
            field : 'buserName',
            title : '借书人',
            width : 100
        },{
            field : 'bookName',
            title : '书名',
            width : 200
        },{
            field : 'bookNo',
            title : '编号',
            width : 100
        },{
            field : 'borrowDateStr',
            title : '借书日期',
            width : 100
        },{
            field : 'returnDateStr',
            title : '还书日期',
            width : 100
        },{
            field : 'ruserName',
            title : '登记人',
            width : 100
        },{
            field : 'borrowRemark',
            title : '备注',
            width :100
        },{field:'_manage',title:'操作',width : 100,formatter:function(value,rowData,rowIndex){
            var text = "";
        
             text += "<a href='#' onclick=\"allManage('"+rowData.sid+"','"+rowData.bookNo+"',0)\">还书</a>&nbsp;";
            return text;
        }}
        ] ]
    });
}
function allManage(sid,bookNo,flag){
    var msg = "确定要还该书么";
    if (window.confirm(msg)){
        var url = "<%=contextPath%>/bookManage/allManage.action";
        var para = {sid:sid,bookNo:bookNo,flag:flag};
        var jsonRs = tools.requestJsonRs(url,para);
        if(jsonRs.rtState){
            alert("操作成功");
            window.location.reload();
        }
    }
}

function detail(sid){
    var url = "<%=contextPath %>/bookManage/getBookDetail.action?sid="+sid;
    var myleft=(screen.availWidth-500)/2;
    window.open(url,"read_notify","height=400,width=500,status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,top=150,left="+myleft+",resizable=yes");
}

function toReturn(){
    window.location.reload();
}

function borrowSign(){
	var url = contextPath + "/system/subsys/booksManagement/bookManage/borrowSign2.jsp";
	bsWindow(url,"借书登记",{width:"460", height:"260",buttons:[{name:"保存",classStyle:"btn-alert-gray"},{name:"关闭",classStyle:"btn-alert-gray"}],
		submit:function(v,h){
	    var cw = h[0].contentWindow;
	    if(v=="保存"){
		   var a=cw.doSave();
	       if(a){
		    $.MsgBox.Alert_auto("登记成功");
		    window.location.reload();
		    return true;
	      }
	     }
	    else if(v=="关闭"){
	    	return true;
	    }
	  }}); 
}
function booList(){
	var url = "<%=contextPath%>/bookManage/booList.action";
	var jsonRs = tools.requestJsonRs(url);
	var html="<option value='0'>全部</option>";
	if(jsonRs.rtState){
		var data=jsonRs.rtData;
		console.info(data);
		for(var i=0;i<data.length;i++){
			html+="<option value='"+data[i].bookNo+"'>"+data[i].bookNo+"("+data[i].bookName+")</option>";
		}
		$("#bookNo").html(html);
	}
}

</script>

</head>
<body onload="doInit()" fit="true">
<table id="datagrid" fit="true"></table>
<div id="toolbar" style="float:right;width: 100%;">
<form id="form" style="padding:5px">
		<table class="none_table" style="width:100%">
		<tr>
            <td nowrap class="TableData TableBG">借书人：</td>
            <td class="TableData" colspan="3">
                <input type="hidden" name="postUserIds" id="postUserIds" value="" calss="BigInput">
                <input name="postUserNames" id="postUserNames" style="width: 100px;height:20px" class="BigInput" wrap="yes" readonly>
                <a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['postUserIds', 'postUserNames']);">添加</a>
                <a href="javascript:void(0);" class="orgClear" onClick="$('#postUserIds').val('');$('#postUserNames').val('');">清空</a>
           </td>
           <td nowrap class="TableData">借书日期：</td>
           <td nowrap class="TableData">
                                      从<input type="text" name="borrowDateBegin" style="width:100px;" id="borrowDateBegin" class="Wdate BigInput"  onClick="WdatePicker()">
                                     至 <input type="text" name="borrowDateEnd" style="width:100px;" id="borrowDateEnd" class="Wdate BigInput" onClick="WdatePicker()">
    
          </td> 
           <td nowrap class="TableData TableBG">图书编号：</td>
           <td class="TableData">
               <select id="bookNo" name="bookNo" class="BigSelect">
                 
               </select>
          </td> 
         <!-- <td nowrap class="TableData">状态：</td>
         <td nowrap class="TableData">
            <select id="status" name = "status" class="BigSelect">
               <option value = "all">全部</option>
               <option value = "1">已还</option>
              <option value = "0">未还</option>
           </select>
        </td>  -->
         
        
		<td>
			<button type="button"  onclick="borrowSign()" class="btn-win-white fr">借书登记</button></td><td>
			<button type="button"  onclick="query()" class="btn-win-white fr">查询</button>
		</td>
		</tr>
		</table>
</form>
</div>
</body>
</html>
 