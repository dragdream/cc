
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp"%>
<title>会议申请管理</title>
<script type="text/javascript">
function  doOnload(){
	query();
}
/**
 *查询待审批记录
 */
function query(){
	var url =   "<%=contextPath %>/sysMenuPrivManager/queryByTerm.action";
	var para =  tools.formToJson($("#form1")) ;
	var jsonObj = tools.requestJsonRs(url , para);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		$("#listDiv").empty();
		if(prcs.length > 0){
			var tableStr = "<table class='TableList' width='90%' align='center'><tbody id='listTbody'>";
		    	 tableStr = tableStr + "<tr class='TableHeader'>"
		    		 + "<td width='40px'  nowrap align='center'>选择</td>"
			      	 + "<td width='20%'  align='center'>权限名称</td>"
			     	 +"<td nowrap align='center'>页面路径</td>"
			     	 +"<td nowrap align='center'>菜单名称</td>"
			     	 +"<td nowrap  width='10%'  align='center'>是否校验</td>"
			     	 //+"<td nowrap  width='10%'  align='center'>开始时间</td>"
		
			      	 +"<td nowrap  width='80' align='center'>操作</td>"
			         +"</tr>";
			for(var i = 0;i<prcs.length ; i++){
				var prc = prcs[i];
				var id = prc.sid;
				var privFlag = prc.privFlag;
				var  fontStr = "";
				var privFlagDesc = "不校验";  
				if(privFlag == "1"){
					privFlagDesc = "校验";
				}
				var sysMenuNames = prc.sysMenuNames;
				
				var opts =  "&nbsp;&nbsp;<a href='javascript:void(0);'  onclick='toAddEdit(" + id + ");'>修改</a>"
					+  "&nbsp;&nbsp;<a href='javascript:void(0);'  onclick='deleteById(" + id + ");'>删除</a>";
			
				tableStr = tableStr +"<tr class=''>"
						 +"<td nowrap align='center' ><font color='" + fontStr + "'><input type='checkbox' name='privId' value='"+id+"'/> </font></td>"
						 +"<td nowrap align='center' ><font color='" + fontStr + "'>" + prc.privName + "</font></td>"
						 +"<td nowrap align='center' ><font color='" + fontStr + "'>" + prc.privUrl + "</font></td>"
						 +"<td nowrap align='center' ><font color='" + fontStr + "'>" + sysMenuNames + "</font></td>"
						 +"<td nowrap   align='center'><font color='" + fontStr + "'>"+ privFlagDesc +"</font></td>"
				      	// +"<td nowrap align='center'  ><font color='" + fontStr + "'>"  + prc.endTimeStr + "</font></td>"
				    	   +"<td nowrap align='center'  >"
					     +opts
					     +"</td>"
				         +"</tr>";
			}
			
			var tr = "<tr class='TableFooter'>"
				 + "<td width='40px'  nowrap align='center'>"
				 + "<input type='checkbox' onclick='checkAll(this)' id='allPriv'/></td>"
		      	 + "<td   class='' colspan='5'>"
				 + "<label for='allPriv'>全部</label>"
				 + "&nbsp;&nbsp;<a href='javascript:deleteByIds()'>批量删除</a>"
				 +"</td>"
				 + "</tr>";
			tableStr = tableStr + tr + "</tbody></table>";	
			$("#listDiv").append(tableStr);	
		}else{
			messageMsg("暂无相关菜单权限记录", "listDiv" ,'' ,380);
		}
	}else{
		alert(jsonObj.rtMsg);
	}
}



/*
 * 新增模块权限
 */
function toAddEdit(id){
	var title = "新增模块权限";
	if(id > 0){
		 title = "编辑模块权限";
	}
	var url = "addOrUpdate.jsp?sid=" + id   ;
	bsWindow(url ,title,{width:"520",height:"280",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			cw.doSave(function (){
				 query();
				 $.jBox.tip("保存成功！", 'info',{timeout:1500});
				 BSWINDOW.modal("hide");
			});
			
		}else if(v=="关闭"){
			return true;
		}
	}});
}
/**
 * 删除
 */
function deleteById(id){
	var submit = function (v, h, f) {
	    if (v == 'ok'){
	    	var url =   "<%=contextPath %>/sysMenuPrivManager/delById.action";
	    	var para =  {sid:id} ;
	    	var jsonObj = tools.requestJsonRs(url , para);
	    	if(jsonObj.rtState){
	    		$.jBox.tip("删除成功！", 'info',{timeout:1500});
	    		query();
	    	}else{
	    		alert(jsonObj.rtMsg);
	    	}
	 		
	    }
	    isClose =  true; //close
	};
	$.jBox.confirm("确定要删除吗？", "提示", submit);
}

/**
 * 全部删除
 */
function deleteAll(){
	var submit = function (v, h, f) {
	    if (v == 'ok'){
	    	var url =   "<%=contextPath %>/sysMenuPrivManager/deleteAll.action";
	    	var para =  {sid:id} ;
	    	var jsonObj = tools.requestJsonRs(url , para);
	    	if(jsonObj.rtState){
	    		$.jBox.tip("删除成功！", 'info',{timeout:1500});
	    		query();
	    	}else{
	    		alert(jsonObj.rtMsg);
	    	}
	 		
	    }
	    isClose =  true; //close
	};
	$.jBox.confirm("确定要全部删除吗,删除后不可恢复？", "提示", submit);
}


/**
 * 删除 byIds
 */
function deleteByIds(){
	var submit = function (v, h, f) {
	    if (v == 'ok'){
	    	var ids = "";
	    	$("input[name='privId']").each(function(i){
	 		   if(this.checked){
	 			  ids = ids + this.value + ",";
	 		   }
	 		});
	    	if(ids == ""){
	    		$.jBox.tip("至少选择一条数据！", 'info',{timeout:1500});
	    		return true;
	    	}
	    	var url =   "<%=contextPath %>/sysMenuPrivManager/deleteByIds.action";
	    	var para =  {sids:ids} ;
	    	var jsonObj = tools.requestJsonRs(url , para);
	    	if(jsonObj.rtState){
	    		$.jBox.tip("删除成功！", 'info',{timeout:1500});
	    		query();
	    	}else{
	    		alert(jsonObj.rtMsg);
	    	}
	 		
	    }
	    isClose =  true; //close
	};
	$.jBox.confirm("确定要删除所选记录吗,删除后不可恢复？", "提示", submit);
}

function checkAll(obj){
	$("input[name='privId']").each(function(i){
		   if(obj.checked){
			   this.checked = true; 
		   }else{
			  this.checked = false; 
		   }
	});
}
</script>
</head>
<body class="" topmargin="5" onload="doOnload();">


	<table width="95%">
		<tr>
			<td  class="Big3">
				模块权限管理
			</td>
		</tr>
		<tr align="center">
			<td   align="center">
				<input type="button"  class="btn btn-primary" value="新建模块权限" onclick="toAddEdit(0)"/>
				&nbsp;&nbsp;<input type="button"  class="btn btn-danger" value="全部删除" onclick="deleteAll()"/>
			</td>
		</tr>
	</table>
	
	 <form id="form1" name="form1">
		<table class="TableBlock" width="90%" align="center" style="margin-top:6px;margin-bottom:10px;" >
   			<tr>
   				<td nowrap class="TableData" >模块权限名称:</td>
   				<td nowrap class="TableData" >
					
					<input type="text" name="privName" id="privName" value="" size="30" class="BigInput"  maxlength="100" > 
       			</td>
				<td nowrap class="TableData" >URl路径:</td>
   				<td nowrap class="TableData" >
       				<input type="text" name="privUrl" id="privUrl" size="30" maxlength="200" class="BigInput" value=""  >
         		</td>
			
  			</tr>
  			<tr>
   				<td nowrap class="TableData" >是否校验:</td>
   				<td nowrap class="TableData" colspan="3" >
					<select name="privFlag" class="BigSelect">
						<option value=''>全部</option>
						<option value='0'>不校验</option>
						<option value='1'>校验</option>
					</select>		
				</td>
				
  			</tr>
  			<tr>
   				<td nowrap class="TableData"  colspan="4" align="center">
  					<input type="button"  class="btn btn-primary" value="查询" onclick="query()"/>	
  					&nbsp;&nbsp;<input type="reset"  class="btn btn-primary" value="重置"/>			
   				</td>	
  			</tr>
  		</table>
  	</form>

	<div id='listDiv'>
		

	
	</div>
		

</body>

</html>