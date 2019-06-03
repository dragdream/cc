<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
    String pMenuId = request.getParameter("pMenuId") == null ? "" : request.getParameter("pMenuId");
	String uuid = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");
	String Puuid = request.getParameter("Puuid") == null ? "" : request.getParameter("Puuid");
	String menuName = request.getParameter("menuName") == null ? "" : request.getParameter("menuName");
	
	String menuId = request.getParameter("menuId") == null ? "" : request.getParameter("menuId");
	
	String childMenuName = request.getParameter("childMenuName") == null ? "" : request.getParameter("childMenuName");
	String menuURL = request.getParameter("menuURL") == null ? "" : request.getParameter("menuURL");//
	
	menuName = menuName.replace("\'", "\\\'");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>菜单功能</title>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/src/teeValidagteBox.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/sys.js"></script>
<script type="text/javascript">

var uuid = '<%=uuid%>';
var pMenuId = '<%=pMenuId%>';//父级菜单编号
var Puuid = "<%=Puuid%>";//父级UUID

var menuId = "<%=menuId%>";//菜单编号


var childMenuName = "<%=childMenuName%>";//公共文件柜过来创建的菜单
var menuURL = "<%=menuURL%>";
function doInit(){
	renderSysList();
	//添加例子一
	if(uuid != ""){
		var url = "<%=contextPath %>/sysMenu/getSysMenuById.action";
		var para = {uuid:uuid,menuId:menuId};
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
				var childMenuId = json.menuId.substring(json.menuId.length-3, json.menuId.length); 
				$("#menuId").val(childMenuId);
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
		///$("#menuId").val($("#pMenuId").val() + $("#menuId").val());
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			var data = jsonRs.rtData;
			if(data.isExistCode == "0"){
				
				//alert("保存成功！");
				top.$.jBox.tip('保存成功','info',{timeout:1500});
				if(childMenuName != "" && menuURL != ""){
					parent.BSWINDOW.modal("hide");
				}else{
					 history.go(-1);
				}
				//var parent = window.parent.navigateFrame
				//parent.location.reload();
		        //window.location.reload();
		       
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
/****
 * 检查
 */
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


/**
 * 选择菜单
 * @return
 */
var menuArray = null ;
function toSelectTreeMenu(retArray) {
  menuArray = retArray;
  var url =  "<%=contextPath%>/system/core/menu/menuTree.jsp?sid=" + uuid;
  dialogChangesize(url , 300, 450);
  
  //toSelectTreeMenu()
}


function closeBS(){
	parent.BSWINDOW.modal("hide");;
}

</script>

</head>
<body onload="doInit()">
   <table border="0" width="100%" cellspacing="0" cellpadding="3" class="small" align="center">
    <tr>
      <td class="Big"> <%
         if(TeeUtility.isNullorEmpty(uuid)){
        	
        	  %>
        	  <span class="Big3">
        	  子菜单新增
        	  </span>
        	  <%
         }else{
        	 %>	 
        	   <span class="Big3">
        	 子菜单编辑
        	  </span>
        	 
         <%}%></td>
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
			<td nowrap>上级菜单：</td>
			<td>
			<input type="text"  name="pMenuId" id="pMenuId" style="display:none;" value="<%=pMenuId %>"/>
					
				<input type="text"  readonly name="pMenuIdDesc" id="pMenuIdDesc" value="<%=menuName%>" class="easyui-validatebox BigInput" required="true"/>&nbsp;	
					<a href ="javascript:toSelectTreeMenu('<%=menuId %>')">添加</a>
					</td>
			</tr>
			<tr class="TableLine1">


				<td nowrap>子菜单编号：</td>
				<td nowrap><INPUT type=text name="menuId" id="menuId" size="10" maxlength="3" class="easyui-validatebox BigInput" required="true" validType ="numberstrLength[3]" >
				<div></div>说明：此代码为三位，作为排序之用。<br>在同一父菜单下的平级菜单，该代码不能重复 
				</td>
			</tr>
			<tr class="TableLine2">
				<td nowrap>子菜单名称：</td>
				<td nowrap><input type="text" name="menuName" id="menuName" maxlength="100" class="easyui-validatebox BigInput" required="true" value="<%=childMenuName %>" >&nbsp;</td>
			</tr>
        <tr class="TableLine2">
				<td nowrap>图片：</td>
				<td nowrap>
					<input type="text" name="icon" id="icon" size="40" maxlength="100" class="BigInput"> 
					 </td>
			</tr>
			<tr class="TableLine1">
				<td nowrap>路径:</td>
				<td nowrap><input type="text" name="menuCode" id="menuCode" size="60"  class="BigInput" value="<%=menuURL%>">
			</tr>
			
			
			<tr class="">
				<td colspan="2" align="center" ><input type="button" value="保存"
					class="btn btn-primary" onclick="doSave();">&nbsp;&nbsp;
					<%if(childMenuName.equals("") && childMenuName.equals("")){ %>
					<input type="button" value="返回" class="btn btn-primary" onclick="history.go(-1);">&nbsp;&nbsp;
					
					<%}else{ %>
					<input type="button" value="关闭" class="btn btn-primary" onclick="closeBS()">&nbsp;&nbsp;
					
					<%} %>
					
					<input type="text" id="uuid" name="uuid" style="display:none;" value='0'/>
					
						<input type="text" id="oldMenuId" name="oldMenuId" style="display:none;"/>
				
					</td>
			</tr>
		</table>
   </form>
</body>
</html>