<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

 <%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
    
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>线索</title>
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
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript">


//添加线索
function add(){
	var url = contextPath+"/system/subsys/crm/core/clue/addOrEditClue.jsp";
	location.href=url;
}


//重置form表单
function  resetForm(){
	document.getElementById("form1").reset(); 
	$("#managePersonId").val(" ");
	$("#managePersonName").val(" ");
}

function fresh(){
	var params = tools.formToJson($("#form1"));
	$("#tab-content").find("iframe")[0].contentWindow.query(params);
	$(".searchCancel").click();
}
</script>
</head>
<body onload=""  style="overflow:hidden;padding-left:10px;padding-right:10px;">

 <div class="titlebar clearfix" >
		<img class = 'tit_img' style="margin-right: 10px;" src="<%=contextPath%>/system/subsys/crm/img/icon_xs.png">
		<p class="title">线索</p>
		<ul id = 'tab' class = 'tab clearfix' style='display:inline-block;'>
		</ul>
	<div class="right fr clearfix" style="padding-top: -1px;">
      <input type="button" value="新建线索" class="btn-win-white" onclick="add();"/>
      <button type="button" onclick="" class="advancedSearch btn-win-white">高级查询</button>
   </div>
		
		<span class="basic_border_grey fl"></span>
 </div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right: 10px;"></div>
	
	
	 <form id="form1" class='ad_sea_Content'>
       <table class="TableBlock" width="100%"  style="font-size:12px" id="searchTable">
          <tr>
             <td width="10%">
				姓名：
			</td>
			<td width="40%">
				<input style="width: 300px;" id='name' name='name' type='text'/>
			</td>
             <td width="10%">负责人：</td>
             <td width="40%">
                <input  style="width: 300px;" type="text" name="managePersonName" id="managePersonName"/>
                <input id='managePersonId' name='managePersonId' class="BigInput" type='hidden'/>
				<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['managePersonId','managePersonName'])">选择负责人</a>&nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgClear" onClick="clearData('managePersonId','managePersonName')">清空</a>
             </td>
          </tr>
          <tr>
            <td>公司：</td>
             <td width="40%">
				<input style="width: 300px;" id='companyName' name='companyName' type='text'/>
			</td>
             <td>状态：</td>
             <td>
                <select style="width: 300px;" id="clueStatusDesc" name="clueStatusDesc">
                   <option value="0">全部</option>
                   <option value="1">待处理</option>
                   <option value="2">已处理</option>
                   <option value="3">已转化</option>
                   <option value="4">无效</option>
                </select>
             </td>
          </tr>
          <tr>
             <td>部门：</td>
             <td width="40%">
				<input style="width: 300px;" id='department' name='department' type='text'/>
			</td>
            <td>职务：</td>
             <td width="40%">
				<input style="width: 300px;" id='duties' name='duties' type='text'/>
			</td>
            
          </tr>
          <tr>
             <td>电话：</td>
             <td width="40%">
				<input style="width: 300px;" id='telephone' name='telephone' type='text'/>
			</td>
            <td>手机：</td>
             <td width="40%">
				<input style="width: 300px;" id='mobilePhone' name='mobilePhone' type='text'/>
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
var customerId = "<%=sid%>";
 $.addTab("tab","tab-content",[{title:"全部",url:contextPath+"/system/subsys/crm/core/clue/allClueList.jsp?type=1"},
                              {title:"我负责的",url:contextPath+"/system/subsys/crm/core/clue/allClueList.jsp?type=2"},
                              {title:"我下属负责的",url:contextPath+"/system/subsys/crm/core/clue/allClueList.jsp?type=3"},
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
