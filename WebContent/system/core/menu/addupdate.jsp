<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
  String uuid = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<title>菜单功能</title>
<script type="text/javascript">

var uuid = '<%=uuid%>';

function doInit(){
	renderSysList();
	//添加例子一
	if(uuid != ""){
		var url = "<%=contextPath %>/sysMenu/getSysMenuById.action";
		var para = {uuid:uuid};
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj){
			var json = jsonObj.rtData;
			if(json.uuid){
				//alert(json.uuid +":"+ json.menuId + ":" + json.menuName);
				/* $("#menuId").val(json.menuId);
				$("#menuName").val(json.menuName);
				$("#menuCode").val(json.menuCode);
				$("#icon").val(json.icon);
				$("#uuid").val(json.uuid); */
				
				bindJsonObj2Cntrl(json);
				$("#oldMenuId").val(json.menuId);
				
			}
			
		}else{
			alert(jsonObj.rtMsg);
		}
	}
	
}


//渲染应用系统列表
function renderSysList(){
	var html=[];
	var url=contextPath+"/ApplicationSystemMaintainController/getAll.action";
	var json=tools.requestJsonRs(url);
	if(json.rtState){
		var data=json.rtData;
		if(data!=null&&data.length>0){
			for(var i=0;i<data.length;i++){
				html.push("<option value="+data[i].sid+">"+data[i].systemName+"</option>");
			}
		}
		$("#sysId").append(html.join(""));
	}
}


/**
 * 保存
 */
function doSave(){
	if (check()){
		var url = "<%=contextPath %>/sysMenu/addOrUpdateMenu.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			var data = jsonRs.rtData;
			if(data.isExistCode == "0"){
				top.$.jBox.tip("保存成功！","info",{timeout:1500});
				var parent = window.parent;
				parent.location.reload();
		        window.location.reload();
			}else{
				alert("菜单编号已存在！");
				var menuId = document.getElementById("menuId");
			  	menuId.focus();
			    menuId.select();
				return;
			}

			
		}else{
			alert(jsonRs.rtMsg);
		}
	}
	
}


function checkStr(str){ 
    var re=/[\\"']/; 
    return str.match(re); 
  }
 
function check() {
	 var status =  $("#form1").form('validate'); 
	 if(status == false){
		 return false;
	 }
  	var cntrl = document.getElementById("menuName"); 
    if(checkStr(cntrl.value)){
      alert("您输入的菜单名称含有'双引号'、'单引号 '或者 '\\' 请从新填写");
      $('#menuName').focus();
      $('#menuName').select();
      return false;
    }
    
    return true;
  }
  
  /**选择菜单样式**/
  function selectIcon(){
		$.jBox('iframe:' + contextPath+ '/system/core/menu/bsicon.jsp', 
				 { bottomText: '',
			 	title:'选择菜单样式',
				width: 540,
			    height: 450
		});

	// top.$.jBox.info(info);
  }

</script>

</head>
<body onload="doInit()" style="padding-top:10px;">
   <table border="0" width="100%" cellspacing="0" cellpadding="3" class="small" align="center">
    <tr>
      <td class="Big">
      
        <%
         if(TeeUtility.isNullorEmpty(uuid)){
        	
        	  %>
        	<span class="big3">
        	  主菜单新增
        	  </span>
        	  <%
         }else{
        	 %>	 
        <span class="big3">
        	 主菜单编辑
        	  </span>
        	 
         <%}
        %></td>
    </tr>
  </table><br>
  <form   method="post" name="form1" id="form1">
<table class="TableBlock" width="80%" align="center">
           <tr class="TableLine1">
				<td nowrap>所属应用系统：</td>
				<td nowrap>
				     <select class="BigSelect" id="sysId" name="sysId">
				         <option value="0">公用</option>
				     </select>
				</td>
			</tr>
			<tr class="TableLine1">
				<td nowrap>菜单编号：</td>
				<td nowrap><INPUT type=text name="menuId" id="menuId" size="10" maxlength="3" class="easyui-validatebox BigInput" required="true" validType ="numberstrLength[3]"> &nbsp;3位数字</td>
			</tr>
			<tr class="TableLine2">
				<td nowrap>菜单名称：：</td>
				<td nowrap>
				<input type="text" name="menuName" id="menuName" class="easyui-validatebox BigInput" style="width:200px;" required="true">&nbsp;</td>
			</tr>
			<tr class="TableLine2">
				<td nowrap>图片：</td>
				<td nowrap><input type="text" id="icon" name="icon"  class="easyui-validatebox BigInput" style="width:200px;"  id="icon">
			
			      
<!-- 				<input type="button" value="选择图片" class="btn" onclick="selectIcon();">&nbsp;&nbsp; -->
				</td>
			</tr>
			<tr class="TableLine1">
				<td nowrap>路径:</td>
				<td nowrap><input type="text" name="menuCode" id="menuCode" size="60" maxlength="100" class="BigInput" value="">
			</tr>
			
			<tr class="">
				<td colspan="2" align="center"><input type="button" value="保存"
					class="btn btn-primary" onclick="doSave();">&nbsp;&nbsp;
					
					<input type="text" id="uuid" name="uuid" style="display:none;" value='0'/>
					<input type="text" id="oldMenuId" name="oldMenuId" style="display:none;"/>
					
					</td>
			</tr>
		</table>
   </form>
   
   
   
   

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
      </div>
      <div class="modal-body">
        ...
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>