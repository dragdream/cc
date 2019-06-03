<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<title>界面主题</title>
<script type="text/javascript">

function doInit(){
	
	var url = "<%=contextPath %>/shortcutMenuController/getMenuInfoByPerson.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		//alert(jsonRs);
		if(jsonRs.rtState){
			var data = jsonRs.rtData;
			var selectSysMenuModelList = data.selectSysMenuModelList;
			var notSelectSysMenuModelList = data.notSelectSysMenuModelList;
			var selectShortcutMenuDesc = "";
			var noSelectShortcutMenuDesc = "";
			for(var i =0;i<selectSysMenuModelList.length ; i++){
				selectShortcutMenuDesc = selectShortcutMenuDesc + "<option value='"+selectSysMenuModelList[i].uuid+"'>" + selectSysMenuModelList[i].menuName + "</option>";
			}
			
			for(var i =0;i<notSelectSysMenuModelList.length ; i++){
				noSelectShortcutMenuDesc = noSelectShortcutMenuDesc + "<option value='"+notSelectSysMenuModelList[i].uuid+"'>" + notSelectSysMenuModelList[i].menuName + "</option>";
			}
			$("#noSelectShortcutMenu").html(noSelectShortcutMenuDesc);
			$("#selectShortcutMenu").html(selectShortcutMenuDesc);
		}else{
			alert("查询错误");
			return;
		}
}



/**
 * 保存
 */

 function doSaveMemu(){
	
	var noSelectShortcutMenuDesc = "";
	var selectShortcutMenuDesc ="";
    /* for (var i=0; i<document.form1.noSelectShortcutMenu.options.length; i++){
		noSelectShortcutMenuDesc = noSelectShortcutMenuDesc + document.form1.noSelectShortcutMenu.options[i].value + ",";
    } */
	
    for (var i=0; i<document.form1.selectShortcutMenu.options.length; i++){
    	selectShortcutMenuDesc = selectShortcutMenuDesc + document.form1.selectShortcutMenu.options[i].value + ",";
	}
	var para =  {noSelectShortcutMenuDesc: noSelectShortcutMenuDesc , selectShortcutMenuDesc:selectShortcutMenuDesc} ;
	
	var url = "<%=contextPath %>/shortcutMenuController/addOrUpdate.action";
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		$.jBox.tip("保存成功！" , 'info',{timeout:1500});
	}else{
		alert(jsonRs.rtMsg);
	}	
 }


/**
 * 设置选中的
 */
function select_all_select(type){
	var check = true;
	if(type == '1'){
		check = false;
	}
    for (var i=0; i<document.form1.selectShortcutMenu.options.length; i++){
        document.form1.selectShortcutMenu.options[i].selected=check;
    }
}
/**
 * 设置未选中的
 */
function select_all_noSelect(type){
	var check = true;
	if(type == '1'){
		check = false;
	}
    for (var i=0; i<document.form1.noSelectShortcutMenu.options.length; i++){
        document.form1.noSelectShortcutMenu.options[i].selected= check;
    }
}

/**
 * 添加
 */
function func_insert(){
	var noSelectShortcutMenu =  $("#noSelectShortcutMenu option:selected");
	if(noSelectShortcutMenu.length == 0){
		$.jBox.tip("至少选择一项",'info' , {timeout:1500});
		return;
	}
	
	//移动
	$("#selectShortcutMenu").append(noSelectShortcutMenu);
}

/**
 * 删除
 */
function func_delete(){
	var selectShortcutMenu =  $("#selectShortcutMenu option:selected");
	if(selectShortcutMenu.length == 0){
		$.jBox.tip("至少选择一项",'info' , {timeout:1500});
		return;
	}
	//移动
	$("#noSelectShortcutMenu").append(selectShortcutMenu);
}
</script>

</head>
<body onload="doInit()">


<table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small" align="center">
		<tr>
			<td class="Big3">
				菜单常用组定义
			</td>
		</tr>
	</table>
	<br>
	<form  method="post" name="form1" id="form1" >
		<table class="TableBlock" width="60%" align="center">
	
		   <tr>
		   		 <td nowrap class="TableHeader" colspan="" style='vertical-align: middle;'>选中</td>
		   		 <td nowrap class="TableHeader" colspan="" style='vertical-align: middle;' width="60px;">选择</td>
		   		 <td nowrap class="TableHeader" colspan="" style='vertical-align: middle;' >备选</td>
		   </tr>
		   <tr align="center" >
		    	<td nowrap class="TableData" >
		    		<select  name='selectShortcutMenu' id = 'selectShortcutMenu' style='width:180px; height:200px' ondblclick='func_delete();' multiple class='form-control' >
		    		
		    		</select> 
		    		<div style="padding-top:10px;padding-bottom:10px;">
			    		<input type='button' value='全选' onClick='select_all_select(0);' class='btn btn-success btn-xs'>&nbsp;
	            	    <input type='button' value='取消选择' onClick='select_all_select(1);' class='btn btn-warning btn-xs'>
		    		</div>
		    	</td>
		    	<td nowrap class="TableData" >
		    	
		    		<input type='button' class='btn btn-info btn-xs' value=' ← ' style="width:40px;" onClick='func_insert();'>
              			<br><br>
              		<input type='button' class='btn btn-info btn-xs' value=' → '  style="width:40px;" onClick='func_delete();'>
		    	
		     	 </td>
		     	 <td nowrap class="TableData" align="center">
		     	 	<select  name='noSelectShortcutMenu' id = 'noSelectShortcutMenu' style='width:180px; height:200px' ondblclick='func_insert();' multiple class='form-control' >
		    		
		    		</select> 
		    		<div style="padding-top:10px;padding-bottom:10px;">
		    			<input type='button' value='全选' onClick='select_all_noSelect(0);' class='btn btn-success btn-xs'>&nbsp;
            	    	<input type='button' value='取消选择' onClick='select_all_noSelect(1);' class='btn btn-warning btn-xs'>
		     	 	</div>
		     	 </td>
		   </tr>
		      <tr>
		    	<td nowrap class="TableData" colspan="3" align="center" >
		    		<div>点击条目时，可以组合CTRL或SHIFT键进行多选</div>
		    		<button type="button" class="btn btn-primary" onclick="doSaveMemu()" value="">保存应用</button>
		    	</td>
		    	
		   </tr>
	</table>
  </form>
</body>
</html>