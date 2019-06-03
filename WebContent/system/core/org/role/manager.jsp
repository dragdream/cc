<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>

<style type="text/css">
#userRoleTypeList div a{
	background: url("<%=stylePath %>/imgs/error.gif") no-repeat;
}

.userRole a{
	background: url("<%=cssPath %>/remove.png") no-repeat;
}
</style>
<script type="text/javascript" charset="UTF-8">
function doInit(){

	//获取所有角色类型
	var url = "<%=contextPath %>/teeUserRoleTypeController/getAllType.action";
	var jsonRs = tools.requestJsonRs(url,{});
	if(jsonRs.rtState){
	   var prcs = jsonRs.rtData;
	   $("#userRoleTypeList").empty();
	   $.each(prcs , function (i , prc){
		   var div = $("<div>").attr({"id": "type_"+ prc.sid  }).css({"font-size":"14px"});
		  // div.append("<span class='glyphicon glyphicon-plus-sign'></span>");
		   var  span = $("<span>").css({"cursor":"pointer"}).addClass("glyphicon glyphicon-minus-sign").click(
				      function () {
					    setClick(this);
					  }
					);
		   div.append(span);
		   var  span2 = $("<span>").css({"padding-left":"3px" ,"cursor":"pointer"})
						   .attr({"item_id":prc.sid  , "item_name":prc.typeName })
						   .append( prc.typeName )
						   .click(function(){
							   toAddOrUpdateType(prc.sid ,prc.typeSort, prc.typeName);
						   });
		   div.append(span2);
		 
			 
		   //默认不删除
		   if(prc.sid != 1){
			   var delSpan = $("<a>").css({"padding":"0px 20px" ,"margin":"0px 10px"})
			            .attr({"title":"删除角色类型","href":"javascript:void(0);"})              
			   			.append("&nbsp;")
			   			.click(function(){
			   				deleteRoleType(prc.sid);
			   			});//角色类型删除
			   div.append(delSpan);
		   }
		  
		   $("#userRoleTypeList").append(div );
	   });
	   for(var i = 0; i<prcs.length ; i++){
		  
	   }
	   
	   getAllUserRole();
	}else{
		alert(jsonRs.rtMsg);
	}
	
}
/**
 * 设置点击事件
 */
function setClick(event){
	if($(event).hasClass("glyphicon-plus-sign")){//显示
		$(event).parent().children("div").show();
	    $(event).removeClass("glyphicon-plus-sign");
	    $(event).addClass("glyphicon-minus-sign");
	}else{
		$(event).parent().children("div").hide();
		$(event).removeClass("glyphicon-minus-sign");
		$(event).addClass("glyphicon-plus-sign");
		
	}
}
/**
 * 获取所有角色
 */
function getAllUserRole(){
	
	var url = "<%=contextPath %>/userRoleController/getAllRole.action";
	var jsonRs = tools.requestJsonRs(url,{});
	if(jsonRs.rtState){
	   var prcs = jsonRs.rtData;
	  $.each(prcs , function ( i , prc){
		   var div = $("<div class='userRole'>");
			   
		   var span = $("<span>").append(prc.roleName).attr({"id":prc.uuid,"item_name":prc.roleName})
		       .css({"padding-left":"32px","cursor":"pointer"})
		       .attr({ "title":"点击编辑角色"})
		       .click(function (){
		    	   toAddUpdateRole(prc.uuid );
		       });
		   var delA = $("<a>").css({"padding":"0px 20px" ,"margin":"0px 20px"})
           .attr({"title":"删除角色","href":"javascript:void(0);"})    
  			.append("&nbsp;")
  			.click(function(){
  				deleteRole(prc.uuid);				
  			 });//角色删除
  			//div.append(delSpan);
		   var roleTypeId = prc.roleTypeId;
		   if(roleTypeId == 0){
			   roleTypeId = 1;
		   }
		   div.append(span ).append(delA);
		   $("#type_" + roleTypeId ).append(div ); 
		   
	  });
	}else{
		alert(jsonRs.rtMsg);
	}
}
/*删除角色类型*/
function deleteRoleType(id){
	if(confirm("确定要删除此角色类型吗？删除后将不可恢复！")){
		var url = "<%=contextPath %>/teeUserRoleTypeController/deleteById.action";
		var jsonRs = tools.requestJsonRs(url,{'sid':id});
		if(jsonRs.rtState){
			$.jBox.tip("删除角色类型成功！" , 'info' , {timeout:1500})
			doInit();
			

		}else{
			alert(jsonRs.rtMsg);
		}
	}
}

/*删除角色*/
function deleteRole(id){

	if(confirm("确定要删除此角色吗？删除后将不可恢复！")){
		var url = "<%=contextPath %>/userRoleController.action?del";
		var jsonRs = tools.requestJsonRs(url,{'ids':id});
		if(jsonRs.rtState){
			$.jBox.tip("删除角色成功！" , 'info' , {timeout:1500})
			doInit();
			

		}else{
			alert(jsonRs.rtMsg);
		}
	}
}

/**
 * 创建或者新建类型
 */
function toAddOrUpdateType(sid , typeSort,typeName){
	if(sid > 0){
		$('#productTypeModal').modal('toggle');
		$("#sid").val(sid);
		$("#typeSort").val(typeSort);
		$("#typeName").val(typeName);
	}else{
		//重置
		$("#sid").val(0);
		$("#form1")[0].reset(); 
	}
}

/**
 * 保存角色类型
 */
function doSaveType(){
	var url = "<%=contextPath %>/teeUserRoleTypeController/addOrUpdate.action";
	var para =  tools.formToJson($("#form1")) ;
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		$('#productTypeModal').modal('toggle');
		$.jBox.tip("保存成功" , 'info' , {timeout:1500} );
		doInit();
	}else{
		alert(jsonRs.rtMsg);
	}
}


/**
 * 新增或者更新
 */
function toAddUpdateRole(id){
	var title = "新建角色";
	if(id > 0){
		 title = "编辑角色";
	}
	var  url = contextPath + "/system/core/org/role/editRole.jsp?uuid=" + id;
	bsWindow(url ,title,{width:"500",height:"120",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			cw.doSaveOrUpdate(function(json){
				$.jBox.tip("保存成功" , 'info' ,{timeout:1500});
				doInit();
			});
	
			//return ;
		}else if(v=="关闭"){
			return true;
		}
	}});
}
</script>
</head>
<body onload="doInit();">
	<!-- <div class="moduleHeader">
		<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;角色管理</b>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div> -->
	<div style="padding:10px 10px;">
	
		<input type="button" class="btn btn-primary" data-toggle="modal" data-target="#productTypeModal" value="新增角色分类"  onclick="toAddOrUpdateType(0);">
		&nbsp;&nbsp;
		<input type="button" class="btn btn-primary"  value="新增角色"  onclick="toAddUpdateRole(0);">
		
		
	</div>
	
	<div id="userRoleTypeList" style="padding:10px 10px;">
		
	</div>
	
	 	<form id="form1" name="form1" method="post">
		<div class="modal fade" id="productTypeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog"  style="width:550px;">
		    <div class="modal-content">
		    	 <div class="modal-header">
		        	 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		       		<h4 class="modal-title" id="myModalLabel">新建/编辑角色类型</h4>
		   	
		      	</div> 
		      <div class="modal-body">
		         <table align="center" width="60%" class="TableBlock">
				      <tr>
				          <td nowrap class="TableData"> 角色类型名称：<font style='color:red'>*</font></td>
				          <td class="TableData" colspan="3">
				              <input type="text" name="typeName" id="typeName" size="20" maxlength="300" class="BigInput easyui-validatebox" value="" required="true">
				          </td>
				      </tr>
				  
				    <tr>
					    <td nowrap class="TableData">排序号：</td>
					    <td nowrap class="TableData">
					    	<input type="text" name="typeSort" id="typeSort" class="BigInput easyui-validatebox "  size="8" maxlength="9" required="true" validType ='integeZero[]'></input>
					    </td>
				   </tr>
				   
				   </tr>
				     <tr>
				        <td nowrap class="TableData" colspan="4" align="center">
				            <button type="button" class="btn btn-primary" onclick="doSaveType();">保存</button>
				            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				        	<input type="hidden" name='sid' id='sid' value="0"></input> 
				        </td>
				     </tr>
				    </table>
		      </div>

		    </div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
		</div>
	 </form>
</body>
</html>