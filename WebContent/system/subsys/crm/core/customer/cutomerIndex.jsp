<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

 <%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
    
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>客户信息</title>
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
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript">

function doInit(){
	getCrmCodeByParentCodeNo("CUSTOMER_TYPE","customerType");
	getCrmCodeByParentCodeNo("CUSTOMER_SOURCE","customerSource");
	getCrmCodeByParentCodeNo("INDUSTRY_TYPE","industry");
	renderCostomFileds();
}

//新建客户
function add(){
	var url = contextPath+"/system/subsys/crm/core/customer/addOrEditCustomer.jsp";
	location.href=url;
}

//重置form表单
function  resetForm(){
	document.getElementById("form1").reset(); 
	$("#customerId").val(" ");
	$("#customerName").val(" ");
	$("#managePersonName").val(" ");
	$("#managePersonId").val(" ");
}

function fresh(){
	var params = tools.formToJson($("#form1"));
	$("#tab-content").find("iframe")[0].contentWindow.query(params);
	$(".searchCancel").click();
}


//渲染自定义字段 查询字段
function renderCostomFileds(){
		var url=contextPath+"/TeeCrmCustomerController/getQueryFieldListById.action";
		var json=tools.requestJsonRs(url,null);
		if(json.rtState){
			var data=json.rtData;
			
			//先移除之前渲染的自定义字段的数据
			$(".customTr").remove();
			
			
			var html = ["<tr class='customTr'>"];	
//			$("#searchTable").append("<tr class='customTr'>");
			for(var i=1;i<=data.length;i++){	
				
				var name="EXTRA_"+data[i-1].sid;
				if(i%3==0){
					html.push("</tr><tr class='customTr'>");
				}
				
				if(data[i-1].filedType=="单行输入框"){
					html.push("<td  width=\"10%\" >"+data[i-1].extendFiledName+"：</td>"
							   +"<td  width=\"40%\" >"
							   +"<input type=\"text\" name='"+name+"' id='"+name+"' style=\"height: 23px;width: 300px\" />"
							   +"</td>");
				}else if(data[i-1].filedType=="多行输入框"){
					html.push("<td  width=\"10%\" >"+data[i-1].extendFiledName+"：</td>"
							   +"<td  width=\"40%\">"
							   +"<input type=\"text\" name='"+name+"' id='"+name+"' style=\"height: 23px;width: 300px\" />"
							   +"</td>");
				}else if(data[i-1].filedType=="下拉列表"){
			/* 		var fieldCtrModel=data[i-1].fieldCtrModel;
					var j=tools.strToJson(fieldCtrModel); */
					if(data[i-1].codeType=="CRM系统编码"){
						html.push("<td   width=\"10%\" >"+data[i-1].extendFiledName+"：</td>"
								   +"<td   width=\"40%\">"
								   +    "<select  name='"+name+"' id='"+name+"' style=\"height:23px;width:150px\" >");
						
						
						html.push("<option value=''>全部</option>");
						var url1 =   contextPath + "/crmCode/getSysCodeByParentCodeNo.action";
						var para = {codeNo:data[i-1].sysCode};
						var jsonObj = tools.requestJsonRs(url1 ,para);
						if(jsonObj.rtState){
							var prcs = jsonObj.rtData;
							for ( var n = 0; n < prcs.length; n++) {
								html.push("<option value='"+prcs[n].codeNo+"'>" + prcs[n].codeName + "</option>");
							}				
						}
						
						html.push("</select></td>");
						/* getSysCodeByParentCodeNo(j.value,name); */
					}else if(data[i-1].codeType=="自定义选项"){
						var values=data[i-1].sysCode;
						var optionNames=data[i-1].optionName.split(",");
						var optionValues=data[i-1].optionValue.split(",");
						html.push("<td width=\"10%\" >"+data[i-1].extendFiledName+"：</td>"
								   +"<td   width=\"40%\">"
								   +    "<select  name='"+name+"' id='"+name+"' style=\"height:23px;width:150px\" >");
						html.push("<option value=''>全部</option>");
						for(var j=0;j<optionNames.length;j++){
							html.push("<option value="+optionValues[j]+">"+optionNames[j]+"</option>");
						
						}	
						html.push("</select></td>");	
					}	
				}
			}
			html.push("</tr>");
			$("#searchTable").append(html.join(""));
		}
	
}
</script>
</head>
<body onload="doInit();"  style="overflow:hidden;padding-left:10px;padding-right:10px;">

 <div class="titlebar clearfix" >
		<img class = 'tit_img' style="margin-right: 10px;" src="<%=contextPath%>/system/subsys/crm/img/icon_khxx.png">
		<p class="title">客户信息</p>
		<ul id = 'tab' class = 'tab clearfix' style='display:inline-block;'>
		</ul>
	<div class="right fr clearfix" style="padding-top: -1px;">
      <input type="button" value="新建客户" class="btn-win-white" onclick="add();"/>
      <button type="button" onclick="" class="advancedSearch btn-win-white">高级查询</button>
   </div>
		
		<span class="basic_border_grey fl"></span>
 </div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right: 10px;"></div>
	
	
	 <form id="form1" class='ad_sea_Content'>
       <table class="TableBlock" width="100%"  style="font-size:12px" id="searchTable">
          <tr>
             <td width="10%">
				客户名称：
			</td>
			<td width="40%">
				<input style="width: 300px;" id='customerName' name='customerName' type='text'/>
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
              <td>状态：</td>
             <td>
                <select style="width: 300px;" id="customerStatus" name="customerStatus" onchange="renderCostomFileds()">
                   <option value="0">全部</option>
                   <option value="1">未分配</option>
                   <option value="2">已分配</option>
                   <option value="3">已作废</option>
                </select>
             </td>
              <td>客户级别：</td>
             <td>
                 <select class="BigSelect" id='customerType' name='customerType' style="width:180px;">
					<option value='0'>全部</option>
				</select>
             </td>
          </tr>
          <tr>
             <td width="10%">
				来源：
			</td>
			<td width="40%">
				<select class="BigSelect" id='customerSource' name='customerSource' style="width:180px;">
				   <option value='0'>全部</option>
			    </select>
			</td>
             <td width="10%">行业：</td>
             <td width="40%">
                <select class="BigSelect" id='industry' name='industry' style="width:180px;">
				     <option value='0'>全部</option>
			    </select>
             </td>
          </tr>
          <tr>
             <td width="10%">
				客户编号：
			</td>
			<td width="40%">
				<input style="width: 300px;" id='customerNum' name='customerNum' type='text'/>
			</td>
             <td width="10%">公司地址：</td>
             <td width="40%">
                <input  style="width: 300px;" type="text" name="companyAddress" id="companyAddress"/>
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
 $.addTab("tab","tab-content",[{title:"全部",url:contextPath+"/system/subsys/crm/core/customer/customerList.jsp?type=1"},
                              {title:"我负责的",url:contextPath+"/system/subsys/crm/core/customer/customerList.jsp?type=2"},
                              {title:"我下属负责的",url:contextPath+"/system/subsys/crm/core/customer/customerList.jsp?type=3"},
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
