<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<title>邮件箱设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/system/core/email/js/email.js"></script>
<style>
	.modal-test{
		width: 564px;
		height: 230px;
		position: absolute;
		display: none;
		z-index: 999;
	}
	.modal-test .modal-header{
		width: 100%;
		height: 50px;
		background-color:#8ab0e6;
	}
	.modal-test .modal-header .modal-title{
		color: #fff;
		font-size: 16px;
		line-height:50px;
		margin-left:20px;
		float: left;
	}
	.modal-test .modal-header .modal-win-close{
		color:#fff;
		font-size: 16px;
		line-height:50px;
		margin-right:20px;
		float: right;
		cursor: pointer;
	}
	.modal-test .modal-body{
		width: 100%;
		height: 120px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-body ul{
		overflow: hidden;
		clear:both;
	}
	.modal-test .modal-body ul li{
		width: 510px;
		height: 30px;
		line-height: 30px;
		margin-top: 25px;
		margin-left: 20px;
	}
	.modal-test .modal-body ul li span{
		display: inline-block;
		float:left;
		vertical-align: middle;
	}
	.modal-test .modal-body ul li input{
		display: inline-block;
		float: right;
		width: 400px;
		height: 25px;
	}
	.modal-test .modal-footer{
		width: 100%;
		height: 60px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-footer input{
		margin-top:12px;
		float: right;
		margin-right:20px;
	}
	
	
   table{
	border-collapse: collapse;
    border: 1px solid #f2f2f2;
    width:100%;
    }
   table tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
   }
  table tr td:first-child{
	text-indent:10px;
  }
  table tr:first-child td{
	font-weight:bold;
  }
  table tr:first-child{
	border-bottom:2px solid #b0deff!important;
	background-color: #f8f8f8;
  }
</style>
<script>
function doInit(){
	getEmailBox();
	
}


/**
 * 获取各种状态总数
 */
function getEmailBox(){
	var url =  "<%=contextPath %>/emailController/getBoxListService.action";
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var mailBoxList = jsonObj.rtData;
		var tbodyDesc = "";
		if(mailBoxList.length>0){
			jQuery.each(mailBoxList,function(i,prcs){
				//alert(prcs.boxName);
				var boxName = prcs.boxName;
				var mailCount = prcs.mailCount;
				var mailBoxSId = prcs.sid;
				tbodyDesc = tbodyDesc + "<tr>"
				+ "<td class='TableData'>"+ prcs.boxNo + "</td>"
				+ "<td  class='TableData'>"+  prcs.boxName+ "</td>"
				+ "<td  class='TableData'>"  
					+ "<a href='javascript:void(0);' class='modal-menu-test' onclick='$(this).modal();updateFolderFunc(" + prcs.sid + ");'>编辑</a>&nbsp;&nbsp;"
					+ "<a href='javascript:void(0);' onclick='deleteEmailBox(" + prcs.sid + ")'>删除</a>"
				+ "</td></tr>";
			    
			});
			$("#mailBoxList").append(tbodyDesc);
			
		}else{
			messageMsg("暂无符合条件的数据！", "container" ,'info' ,300);
		}
		
		
		
	}else{
		//alert(jsonObj.rtMsg);
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}	
}



</script>

</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_youxiang.png">
		<span class="title">管理邮件箱</span>
	</div>
	<div class = "right fr clearfix">
	    <input type="button"  class="btn-win-white modal-menu-test" onclick='$(this).modal();'  value="新建邮件箱"/>
	</div>
</div>

<div>
<table>
		
		<tbody id="mailBoxList">
			<tr class="TableHeader" >
			<td width="100px">序号</td>
			<td width="100px">名称</td>
			<td width="100px">操作</td>
		    </tr>
		</tbody>
</table>

  <div id="container" style="margin-top: 30px;"></div>
</div>



<!-- Modal -->
   <div class="modal-test">
	<div class="modal-header">
		<p class="modal-title">
			新建邮件箱
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body" id="mailBoxForm">
		<ul>
			<li class="clearfix">
				<span>邮件箱名称:</span>
				<input type="text" id="boxInput" name="boxInput" />
			</li>
			<li class="clearfix">
				<span>序号:</span>
				<input type="text"  id="boxNo" name="boxNo"/>
			</li>
		</ul>
	</div>
	<div class="modal-footer clearfix">
		<input class = "modal-btn-close btn-alert-gray" type="button" value = '关闭'/>
		<input class = "modal-save btn-alert-blue" type="button" onclick="submitNewFolder();" value = '保存'/>
	</div>
</div>
</body>
</html>