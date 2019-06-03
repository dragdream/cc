<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
	<title>群组管理</title>
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
	<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
	
	
<script type="text/javascript" >
	
function doInit(){
	var url = "<%=contextPath %>/teeAddressGroupController/getPublicAddressGroups.action";
	//var para =  tools.formToJson($("#form1")) ;
	var jsonRs = tools.requestJsonRs(url);
	if(jsonRs.rtState){
		var rtData = jsonRs.rtData;
		var targetDom = $("#tbody");
		var trTem = '<tr>'+
			'<td nowrap class="TableData">{groupName}</td>'+
			'<td nowrap class="TableData">{toDeptNames}</td>'+
			'<td nowrap class="TableData">{toRolesNames}</td>'+
			'<td nowrap class="TableData">{toUserNames}</td>'+
			'<td nowrap class="TableData">'+
			'<a  onclick="toEdit({seqId})" href="javascript:void(0)">编辑</a> '+
			'<a onclick="deleteById({seqId})" href="javascript:void(0)">删除</a> '+
			'<a onclick="emptyById({seqId})">清空</a> '+
			'<a onclick="exportAddress({seqId})">导出Foxmail格式</a> '+
			'<a onclick="exportAddress({seqId})">导出OutLook</a> '+
			'</td>'+
		'</tr>';
		var trTem0 = '<tr>'+
		'<td nowrap class="TableData">{groupName}</td>'+
		'<td nowrap class="TableData">{toDeptNames}</td>'+
		'<td nowrap class="TableData">{toRolesNames}</td>'+
		'<td nowrap class="TableData">{toUserNames}</td>'+
		'<td nowrap class="TableData">'+
		'<a onclick="emptyById({seqId})">清空</a> '+
		'<a onclick="exportAddress({seqId})">导出Foxmail格式</a> '+
		'<a onclick="exportAddress({seqId})">导出OutLook</a> '+
		'</td>'+
	'</tr>';
		var liArray = new Array();
		if(rtData){
			$.each(rtData,function(key, val){
				if(key !=0){
					var str = FormatModel(trTem,val);
					liArray.push(str);
				}else{
					var str = FormatModel(trTem0,val);
					liArray.push(str);
				}
			});
		}
		targetDom.html(liArray.join(''));
		
		//$("#tbody").append(groupStr);
	}else{
		alert(jsonRs.rtMsg);
	}
}

/**
 * 跳转去新增页面
 */
function toAddUpdate()
{
	window.location.href = "<%=contextPath%>/system/core/base/address/public/group/addGroup.jsp";
}
/**
 * zhp 20130108 删除通讯组
 */
function deleteById(id) {
    var submit = function (v, h, f) {
        if (v == true)
        	delSigle(id);
        return true;
    };
  var delSigle = function ()
    {
    		var url = "<%=contextPath %>/teeAddressGroupController/delAddressGroups.action?isPub=1";
    		var jsonRs = tools.requestJsonRs(url,{"groupId":id});
    		if(jsonRs.rtState){
    			 top.$.jBox.tip("删除成功！");
    			window.location.reload();
    		}else{
    			alert(jsonRs.rtMsg);
    		}
    };
    jBox.confirm("确定删除所选记录,删除后将不可恢复！", "确认删除？", submit, { id:'hahaha', showScrolling: false, buttons: { '确定': true, '取消': false } });
}


/**
* zhp 20130108 清空通讯组
*/
function emptyById(id) {
   var submit = function (v, h, f) {
       if (v == true)
    	   emptySigle(id);
       return true;
   };
 var emptySigle = function ()
   {
   		var url = "<%=contextPath %>/teeAddressGroupController/emptyAddressGroups.action?isPub=1";
   		var jsonRs = tools.requestJsonRs(url,{"groupId":id});
   		if(jsonRs.rtState){
   			 top.$.jBox.tip("清空成功！");
   		}else{
   			alert("清空失败");
   		}
   };
   jBox.confirm("确定清空当前通讯簿组,清空后将不可恢复！", "确认删除？", submit, { id:'hahaha12', showScrolling: false, buttons: { '确定': true, '取消': false } });
}

/**
* 导出通讯薄
*/
function exportAddress(id){
	var url = "<%=contextPath%>/teeAddressController/exportAddressCsv.action?isPub=1&&groupId="+id;
	window.location.href = url;
	
}

/**
 * 跳转去编辑页面
 */
function toEdit(id)
{
	window.location.href = "<%=contextPath%>/system/core/base/address/public/group/editGroup.jsp?id=" + id;
}
</script>
</head>

<body onload="doInit()" style="padding-top:10px;">
<div class="moduleHeader">
	<b> 管理分组</b>
</div>
  
  <div align='center'>
     <input type='button' value='新增分组' class='btn btn-primary' onclick='toAddUpdate();'/>

</div>
  <br>
   <table border="0" width="60%" cellspacing="0" cellpadding="3"  align="">
    <tr>
      <td class="Big">
        <span class="Big3">
        	  群组列表
         </span>
       </td>
       
    </tr>
  </table>
  
  <div style="padding-top:10px;">
     
  <table  class="TableBlock" width="90%" align="center" >
  	 <thead>
  	   <tr  class="TableHeader" >
         	<td >分组名称</td>
         	<td >开放部门</td>
         	<td >开放角色</td>
         	<td >开放人员</td>
            <td > 操作</td>
        </tr>
  	 </thead>
      <tbody id="tbody">
      
   
      
      </tbody>
   </table>
     

  </div>


</body>
</html>