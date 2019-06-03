<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.tianee.oa.core.org.bean.TeeDepartment" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp"%>
<script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>

<title>高级查询</title>
<style type="text/css">
	input.tableInput,select.tableSelect{
		font-size:12px!important;
		width:90%;
	}
	input[disabled="disabled"]{
		background-color: #ebebe4!important;
	}
	select[disabled="disabled"]{
		background-color: #ebebe4!important;
	}
	input[type="checkbox"]{
		width:15px;
		height:15px;
		margin-top:10px\9;
	}
	ul.tableHead{
		display:inline-block;
		overflow: hidden;
		width:853px;
		border:1px solid #82a4d2;
		border-left:none;
	}
	ul.tableHead li{
		float: left;
		padding:5px;
		height:25px;
		background-color: #c8ddff;
		border-left: 1px solid #82a4d2;
		font-size: 12px;
		text-align: center;
		line-height: 25px;
		font-weight:bold;
	}
	input.tableCheckbox{
		width:15px;
		height:15px;
	}
	div#tableBody{
		height: 150px;
		width:854px;
		display:inline-block;
		background-color: rgb(250,250,250);
		overflow: auto;
	}
	ul.searchItem{
		background-color: rgb(250,250,250);
		overflow: auto;
		border:1px solid #aaa;
		border-left:none;
		border-top: none;
	}
	ul.searchItem li{
		text-align:center;
		padding:5px;
		border-left:1px solid #aaa;
	}
	ul.searchItem li{
		float: left;
		height: 25px;
	}
	li.li_0{
		width:30px;
		text-align: center;
	}
	li.li_1{
		width:90px;
	}
	li.li_2{
		width:175px;
	}
	li.li_3{
		width:90px;
	}
	li.li_4{
		width:275px;
	}
	li.li_5{
		width:109px;
	}
	.searchResult{
		/*width: 100%;*/
	}
	#finalResult{
		width:835px;
		margin:0 auto;
		border:1px dashed #333;
		height:50px;
		overflow:auto;
		padding:5px;
		font-size:14px;
		background-color: #dadada;
	}
</style>
<script type="text/javascript">

	/*  window.resizeTo(875, 450);  */
	
	var remainParam = xparent.REMAINPARAM;
	
	var jsonData = xparent.TMP_SENIOR_SEARCH_PARAMS;
	// jsonData = [{"id":"name","name":"姓名","type":"1"},{"id":"age","name":"年龄","type":"2"},{"id":"data","name":"日期","type":"3"},{"id":"data2","name":"日期2","type":"4"}];
	var SQL = "";
	var dialect = "<%=TeeSysProps.getDialect()%>";
	$(function(){
		if(xparent._SQL && xparent._TBODYHTML){
			SQL = xparent._SQL;
			$("#tableBody").html(xparent._TBODYHTML);
			$(".selected").prop("selected",true);
			//$("#finalResult").text(xparent._SHOWEDSTR);
		}else{
			var tr = [];
			tr.push("<ul class='searchItem'>");
			tr.push("<li class='li_0'>");
				tr.push("<input style='margin-top:5px;' type='checkbox' />");
			tr.push("</li>");
			tr.push("<li class='li_1'>");
			tr.push("</li>");
			tr.push("<li class='li_2'>");
			tr.push("<select class='condition tableSelect' onchange='addOper(this)'>");
			tr.push("<option value=''></option>");
			
			for(var i = 0,l = jsonData.length;i<l;i++){
				tr.push("<option type='"+jsonData[i].type+"' opts='"+tools.jsonArray2String(jsonData[i].opts?jsonData[i].opts:"[]")+"'  value='"+jsonData[i].id+"'>"+jsonData[i].name+"</option>");
			}
			tr.push("</select>");
			tr.push("</li>");
			tr.push("</ul>");
			$("#tableBody").append(tr.join(""));
		}
	});
	
	/*
	*添加新搜索项
	*@ type 1-字符串  2-数字  3-日期
	*/
	function addNew(){
		//检查上一条数据是否有未填项
		var $lastLi = $(".searchItem:last-child");
		var condition = $lastLi.find(".condition").val();
		var oper = $lastLi.find(".oper").val();
		if(condition == "" || oper == ""){
			alert("上一条查询条件有未输入项！");
			return;
		}

		var tr = [];
		var length = jsonData.length;
		//判断是否是第一个添加的
		tr.push("<ul class='searchItem'>");
			tr.push("<li class='li_0'>");
				tr.push("<input style='margin-top:5px;' type='checkbox' />");
			tr.push("</li>");

		if(!$("ul.searchItem").length){//第一个添加
			tr.push("<li class='li_1'>");
			tr.push("</li>");
		}else{
			tr.push("<li class='li_1'>");
				tr.push("<select class='tableSelect logic'>");
					tr.push("<option value='and'>并且</option>");
					tr.push("<option value='or'>或者</option>");
				tr.push("</select>");
			tr.push("</li>");
		}
		tr.push("<li class='li_2'>");
		tr.push("<select class='condition tableSelect' onchange='addOper(this)'>");
		tr.push("<option value=''></option>");

		for(var i = 0;i<length;i++){
			tr.push("<option type='"+jsonData[i].type+"' opts='"+tools.jsonArray2String(jsonData[i].opts?jsonData[i].opts:"[]")+"' value='"+jsonData[i].id+"'>"+jsonData[i].name+"</option>");
		}
		tr.push("</select>");
		tr.push("</li>");
		tr.push("</ul>");
		$("#tableBody").append(tr.join(""));
	}
	//添加运算符
	function addOper(dom){
		var $dom = $(dom);

		var type = $dom.find("option:selected").attr("type");
		var opts = $dom.find("option:selected").attr("opts");
		var tr = [];
			tr.push("<li class='li_3'>");
				tr.push("<select class='oper tableSelect'>");
					tr.push("<option value=''></option>");
					if(type == 1 || type==100){//操作类型不是字符串
						tr.push("<option value='"+7+"'>包含</option>");
						tr.push("<option value='"+8+"'>不包含</option>");
						tr.push("<option value='"+9+"'>左包含</option>");
						tr.push("<option value='"+0+"'>右包含</option>");
					}
					if(type==1 || type==2 || type==3){
						tr.push("<option value='"+1+"'>等于</option>");
						tr.push("<option value='"+2+"'>大于</option>");
						tr.push("<option value='"+3+"'>小于</option>");
						tr.push("<option value='"+4+"'>大于等于</option>");
						tr.push("<option value='"+5+"'>小于等于</option>");
						tr.push("<option value='"+6+"'>不等于</option>");
					}
					
					
				tr.push("</select>");
			tr.push("</li>");
			tr.push("<li class='li_4'>");
				if(type == 1 || type == 2 || type == 100){//字符类型和数字
					tr.push("<input class='judgmentText tableInput' type='text' />");
				}
				if(type == 3 || type == 4){//日期类型
					tr.push('<input type="text" id="startTime" name="startTime" onfocus="WdatePicker({dateFmt:\'yyyy-MM-dd\'})" class="Wdate tableInput judgmentText">');
				}
				if(type == 5){//option类型
					tr.push('<select>');
					var opts = eval("("+opts+")");
					for(var k=0;k<opts.length;k++){
						tr.push("<option value='"+opts[k].value+"'>"+opts[k].name+"</option>");
					}
					tr.push('</select>');
				}
			tr.push("</li>");
			tr.push("<li class='li_5'>");
				tr.push("<input type='button' class='btn-del-red addToResult' onclick='delSQL(this)' value='删除'/>");
			tr.push("</li>");

			$dom.closest("ul").find(".li_3,.li_4,.li_5").remove();
			$dom.closest("ul").append(tr.join(""));
	}
	/*添加SQL语句*/
	function addToResult(dom,del){
		var $dom = $(dom);
		var $ul = $dom.closest('ul');

		var logicText,logicVal;
		
		var isFirst =  $ul.index();
		/*判断当前有没有“并且或者”逻辑选项*/
		if($ul.find(".logic option:selected").length == 0){
			logicText = "";
			logicVal = "";
		}else{
			logicText = $ul.find(".logic option:selected").text();
			logicVal = " " + $ul.find(".logic option:selected").val() + " ";
		}


		var conditionText = $ul.find(".condition option:selected").text();
		var operText = $ul.find(".oper option:selected").text();

		var aaa = $ul.find(".condition option:selected").val();
		$ul.find(".condition option:selected").addClass("selected");
		$ul.find(".oper option:selected").addClass("selected");
			
		if(!conditionText){
			var len = $(".searchItem").length;
			if(($ul.index() + 1) == len && ($ul.index() + 1) !=1){//是最后一个且不是第一个
				return true;
			}else{
				alert("有条件项未输入！");
				return false;
			}
		}
		var conditionVal = $ul.find(".condition option:selected").val();
		var operVal = $ul.find(".oper option:selected").val();


		var judgmentText = $ul.find(".judgmentText").val();
		$ul.find(".judgmentText").attr("value",judgmentText);
		var pattern =  new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
		
		if(pattern.test(judgmentText)){
			alert("输入的内容包含非法字符");
			return;
		}
		
		var type = $ul.find(".condition option:selected").attr("type");

		var hideStr;
		/*拼接要返回的隐藏id*/
		switch(parseInt(operVal)){
			//等于
			case 1 : hideStr = judgment(type,logicVal,conditionVal,"=",judgmentText) ? judgment(type,logicVal,conditionVal,"=",judgmentText) : false ; break;

			//大于
			case 2 : hideStr = judgment(type,logicVal,conditionVal,">",judgmentText) ? judgment(type,logicVal,conditionVal,">",judgmentText) : false ; break;
			//小于
			case 3 : hideStr = judgment(type,logicVal,conditionVal,"<",judgmentText) ? judgment(type,logicVal,conditionVal,"<",judgmentText) : false ; break;
			//大于等于
			case 4 : hideStr = judgment(type,logicVal,conditionVal,">=",judgmentText) ? judgment(type,logicVal,conditionVal,">=",judgmentText) : false ; break;
			//小于等于
			case 5 : hideStr = judgment(type,logicVal,conditionVal,"<=",judgmentText) ? judgment(type,logicVal,conditionVal,"<=",judgmentText) : false ; break;
			//不等于
			case 6 : hideStr = judgment(type,logicVal,conditionVal,"!=",judgmentText) ? judgment(type,logicVal,conditionVal,"!=",judgmentText) : false ; break;

			//包含
			case 7 :  judgmentText = "%"+judgmentText+"%";hideStr = judgment(type,logicVal,conditionVal,"like",judgmentText) ? judgment(type,logicVal,conditionVal,"like",judgmentText) : false ; break;
			//不包含
			case 8 :  judgmentText = "%"+judgmentText+"%";hideStr = judgment(type,logicVal,conditionVal,"not like",judgmentText) ? judgment(type,logicVal,conditionVal,"not like",judgmentText) : false ; break;
			//左包含
			case 9 :  judgmentText = ""+judgmentText+"%";hideStr = judgment(type,logicVal,conditionVal,"like",judgmentText) ? judgment(type,logicVal,conditionVal,"like",judgmentText) : false ; break;
			//右包含
			case 0 :  judgmentText = "%"+judgmentText+"";hideStr = judgment(type,logicVal,conditionVal,"like",judgmentText) ? judgment(type,logicVal,conditionVal,"like",judgmentText) : false ; break;
			//默认为空时提示
			default:alert("请选择运算符!");
		}
		/*查询有错误项,未输入项  返回*/
		if(!hideStr){
			return false;
		}
		/*判断是否是真添加还是假添加*/
		if(del){//假添加，用于删除条目，返回要删除的值
			var data = {"hideStr":hideStr,"showStr":" " + logicText + conditionText + "" + operText + judgmentText + " "}
			return data;
		}else{
			SQL += hideStr;
			//$("#hideStr").text(SQL);
			var showStr = " " + logicText + conditionText + "" + operText + judgmentText + " ";
			$("#finalResult").append(showStr);
			xparent._SHOWEDSTR = $("#finalResult").text();
			
			/*设置添加后的不可选*/
			/* $ul.find("input[type='text'],input[type='button'],select").attr("disabled","disabled"); */
		}
	}
	/*生成查询语句*/
	function judgment(type,logicVal,conditionVal,oper,judgmentText){
		if(type == 1){//字符串
			judgmentText = "'"+judgmentText+"'";
			return logicVal +  conditionVal + " " + oper + " " + judgmentText;
		}else if(type == 2){//數字
			//判断为空
			if(judgmentText == ""){
				alert("值不可为空");
				return false;
			}
			//验证数字
			judgmentText = Number(judgmentText);
			if(isNaN(judgmentText)){
				alert("输入的内容不是数字");
				return false;
			}
			return logicVal +  conditionVal + " " + oper + " " + judgmentText;
		}else if(type == 3){//表字段为日期类型  转  字符串  进行比较
			if(judgmentText == ""){
				alert("日期不可为空");
				return false;
			}
			if(dialect=="oracle"){
				return " " + logicVal + "to_char(" + conditionVal +",'YYYY-mm-dd') " + oper + " '" + judgmentText + "'";
			}else if(dialect=="mysql"){
				return " " + logicVal + "DATE_FORMAT(" + conditionVal +", '%Y-%m-%d') " + oper + " '" + judgmentText + "'";
			}else if(dialect=="sqlserver"){
				return " " + logicVal + "convert(char(10)," + conditionVal +",120) " + oper + " '" + judgmentText + "'";
			}
			
		}else if(type == 4){//表字段为字符串 转 日期到字符串
			if(judgmentText == ""){
				alert("日期不可为空");
				return false;
			}
			return " " + logicVal + "to_char(to_date(" + conditionVal +",'YYYY-mm-dd'),'YYYY-mm-dd') " + oper + " '" + judgmentText + "'";
		}else if(type == 100){//工作流会签控件
			judgmentText = "'"+judgmentText+"'";
			return " exists (select 1 from flow_run_ctrl_fb where flow_run_ctrl_fb.run_id=fr.RUN_ID and flow_run_ctrl_fb.item_id="+conditionVal+" and (flow_run_ctrl_fb.content "+oper+" "+judgmentText+" or flow_run_ctrl_fb.user_name "+oper+" "+judgmentText+")) ";
		}
	}
	/*点击删除 删除语句*/
	function delSQL (dom){
		if(dom){
			$(dom).closest('ul.searchItem').remove();
			return false;
		}
		var delItems = $("#tableBody").find('input[type="checkbox"]:checked');
		var len = delItems.length;
		
		for(var i=0;i<len;i++){
			$(delItems[i]).closest('ul.searchItem').remove();
		}
		$("#checkAll").prop("checked",false);

		/* if(showedStr.substr(0,3) == " 并且" || showedStr.substr(0,3) == " 或者" ){
			showedStr = showedStr.substr(3);
		} */
		
		//$("#hideStr").text(SQL);
		//$("#finalResult").text(showedStr);
		//xparent._SHOWEDSTR = showedStr;
		$("#checkAll").prop("checked",false);
	}

	function checkAll (dom){
		var status = $(dom).prop("checked");
			$("#tableBody").find(".li_0").find("input[type='checkbox']").prop("checked",status);
	}

	function returnAdvancedSearchResult(){
		var searchItem = $(".searchItem");
		SQL = "";
		for(var i=0,l=searchItem.length;i<l;i++){
			var returnData = addToResult(searchItem[i]);
			if(returnData == false){
				return;
				break;
			}
		}
		//SQL开头是"or"
		if(SQL.indexOf(" or ") == 0){
			SQL = SQL.substr(3);
		}
		//SQL开头是"and"
		if(SQL.indexOf(" and ") == 0){
			SQL = SQL.substr(4);
		}
		if(SQL){//查询结果并存储数据到原页面上
			xparent.advancedSearchSQL(SQL);
			xparent._TBODYHTML = $("#tableBody").html();
			xparent._SQL = SQL;
			window.close();
		}else{
			alert("无可查询的条件!");
		}
	}
	
	function reset0(){
		xparent.REMAINPARAM = undefined;
		xparent._SQL = "";
		xparent._TBODYHTML = "";
		xparent.advancedSearchSQL("");
		window.close();
	}
</script>
</head>
<body >
<div class="fl left">
         <img id="img1" class = 'title_img' src="/common/zt_webframe/imgs/gzl/gzcx/icon_gongzuochaxun.png">
		 <span class="title">高级查询</span>	 
      </div>
      <span class="basic_border"></span>
      <div style="padding:5px;">
		<button	 type="button"  onclick="returnAdvancedSearchResult()" class="btn-win-white">开始查询</button>
		<input type="button"  class="btn-del-red" value="重置" onclick="reset0()"/>
      </div>

	<div class="base_layout_center"  style="left:10px;right:10px;top:41px">
		<div class="searchContent">
			<ul class="tableHead">
<!-- 				<li class="li_0"><input class="tableCheckbox" id="checkAll" type="checkbox" onclick="checkAll(this)"></li> -->
				<li class="li_0"><input type="button" class="btn-win-white" style="font-weight:bold;font-size:14px" value="+" title="添加条件" onclick="addNew()"/></li>
				<li class="li_1">逻辑运算</li>
				<li class="li_2">条件项</li>
				<li class="li_3">运算符</li>
				<li class="li_4">值</li>
				<li class="li_5" style="width:127px;">操作</li>
			</ul>
			<div id="tableBody"></div>
		</div>
		<div class="searchResult">
			
		</div>
	</div>
</body>
</html>