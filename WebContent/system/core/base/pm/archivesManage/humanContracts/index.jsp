<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>人事合同管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
html{
  padding: 5px;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
}
</style>
<!-- jQuery 布局器 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript">

function doInit(){
	getHrCodeByParentCodeNo("PM_CONTRACT_TYPE" , "conType");
	getHrCodeByParentCodeNo("PM_CONTRACT_STATUS" , "conStatus");
}

//重置form表单
function  resetForm(){
	document.getElementById("form1").reset(); 
}

function fresh(){
	var params = tools.formToJson($("#form1"));
	$("#tab-content").find("iframe")[0].contentWindow.query(params);
	$(".searchCancel").click();
}


</script>
</head>
<body onload="doInit();"  style="overflow:hidden;">

 <div class="titlebar clearfix" >
		<img class = 'tit_img' style="margin-right: 10px;" src="<%=contextPath%>/system/core/base/pm/img/icon_rsht.png">
		<p class="title">人事合同</p>
		<ul id = 'tab' class = 'tab clearfix' style='display:inline-block;'>
		</ul>
		
   <div class="right fr clearfix" style="padding-top: -1px;">
      <button type="button" onclick="" class="advancedSearch btn-win-white">高级查询</button>
   </div>
		<span class="basic_border_grey fl"></span>
  </div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right: 10px;"></div>
	
 <form id="form1" class='ad_sea_Content'>
			<table class="TableBlock" width="100%"  style="font-size:12px" id="searchTable">
						<tr>
							<td width="10%">合同标题：</td>
							<td width="40%">
								<input style="width: 300px;" class="BigInput" type="text" id = "conTitle" name='conTitle'/>
							</td>
							<td width="10%">合同编号：</td>
							<td width="40%">
								<input class="BigInput" type='text' id='conCode' name='conCode' style="width: 300px;"/>
							</td>
						</tr>
						<tr>
							<td width="10%">合同类型：</td>
							<td width="40%">
								<select class="BigSelect" id='conType' name='conType' style="width:180px;">
									<option value="全部">全部</option>
								</select>
							</td>
							<td width="10%">合同状态：</td>
							<td width="40%">
								<select class="BigSelect" id='conStatus' name='conStatus' style="width:180px;">
									<option value="全部">全部</option>
								</select>
							</td>
						</tr>
			</table>
	<div class='btn_search'>
		<input type='button' class='btn-win-white' value='查询' onclick="fresh()">&nbsp;&nbsp;&nbsp;
		<input type='button' class='btn-win-white' value='重置' onclick='resetForm()'>&nbsp;&nbsp;&nbsp;
		<input type='button' class='btn-win-white searchCancel' value='取消'>
	</div>
</form>
	
	
</body>
<script type="text/javascript">
 $.addTab("tab","tab-content",[{title:"将到期",url:contextPath+"/system/core/base/pm/archivesManage/humanContracts/dueToContract.jsp"},
                              {title:"已到期",url:contextPath+"/system/core/base/pm/archivesManage/humanContracts/expiredContract.jsp"},
                              {title:"全部",url:contextPath+"/system/core/base/pm/archivesManage/humanContracts/allContract.jsp"},
                              ]); 
 
 
 var btn_top = $(".advancedSearch").offset().top;
	var brn_height = $(".advancedSearch").outerHeight();
	$(".ad_sea_Content").css('top',(btn_top + brn_height));
	$(".advancedSearch").click(function(){
		$(".ad_sea_Content").slideToggle(200);
		if($(this).hasClass("searchOpen")){//显示前
		$(".serch_zhezhao").remove();
		$(this).removeClass("searchOpen");
		$(this).css({"border":"1px solid #0d93f6",});
		$(this).css('border-bottom','1px solid #0d93f6');
		}else{
		$(this).addClass("searchOpen");//显示时
		$(this).css({"border":"1px solid #dadada",'border-bottom':'1px solid #fff'});
		$('body').append('<div class="serch_zhezhao"></div>');
	}
	var _offsetTop = $("#form1").offset().top;
	$(".serch_zhezhao").css("top",_offsetTop)
	});
	$(".searchCancel").click(function(){
		$(".advancedSearch").removeClass("searchOpen");
	$("#form1").slideUp(200);
	$(".serch_zhezhao").remove();
	});
 
</script>
</html>
