<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>综合查询列表</title>
	<link rel="stylesheet" type="text/css" href="/xzfy/css/caseQuery.css" />
	<script type="text/javascript" src="/xzfy/js/common/common.js"></script>
	<script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
</head>

<body style="padding-left: 10px;padding-right: 10px" onload="doInit();">

   <div id="toolbar" class = " clearfix" style="margin-top: 5px">
	    <div class="fl" style="position:static">
		    <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/jhrw/icon_任务中心.png">
		    <span class="title" id="title"></span>
	    </div>
	    <div class = "right fr clearfix" id="register" style="display: none;">
	    	<input type="button" class="btn-win-white" onclick="register();" value="登记"/>&nbsp;
	        <input type="button" class="btn-win-white" onclick="selectNext()" value="选择受理人"/>&nbsp;
	    </div>
	    <div class = "right fr clearfix" id="accept" style="display: none;">
	        <input type="button" class="btn-win-white" onclick="mergeCase();" value="并案"/>&nbsp;
			<input type="button" class="btn-win-white" onclick="acceptBace()" value="受理撤回"/>&nbsp;
			<input type="button" class="btn-win-white" onclick="selectNext()" value="选择审理人"/>&nbsp;
   			<input type="button" class="btn-win-white" onclick="changeNext()" value="变更承办人"/>&nbsp;
	    </div>
	    <div class = "right fr clearfix" id="trial" style="display: none;">
	        <input type="button" class="btn-win-white" onclick="changeNext()" value="变更承办人"/>&nbsp;
	    </div>
	    <span class="basic_border_grey" style="margin-top: 10px"></span>
	    
	    <div class="setHeight">
	        <form id="form1" style="">
	            <input type="hidden" id="orgId" value="" >
				<button id="accSerach" class="btn-win-white" type="button" onclick="accSearch()">精确查询</button>
	            <table class="none_table" width="100%" height="65">
	            	<tr id="keysearchTr">
	            		<td>关键字：
	 					    <input class="BigInput" type="text" name="caseNum" id="caseNum"
	 					        onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5a-zA-Z0-9\w]/g,'')" />
	 					</td>
	 					<td class="TableData">
	 						<button class="btn-win-white" type="button" onclick="search()">查询</button>
	 					</td>

	            	</tr>
	 				<tr id="accsearchTr1" style="display: none;">
	 					<td>案件编号：
	 					    <input class="BigInput" type="text" name="caseNum" id="caseNum"
	 					        onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5a-zA-Z0-9\w]/g,'')" />
	 					</td>
	 					<td >案件状态：
	 						<select class="BigSelect" id="caseStatus" name="caseStatus">
							    <option>---请选择---</option>
							</select>
	 					</td>
	 					<td>复议机关：
	 					    <input class="BigInput" type="text" name="name" id="name"
	 					        onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5a-zA-Z0-9\w]/g,'')" />
	 					</td>
	 					<td >申请方式：
	 						<select class="BigSelect" id="postType" name="postType" style="width: 172px;">
							    <option>---请选择---</option>
							</select>
	 					</td>
	 				</tr>	
	 				<tr id="accsearchTr2" style="display: none;">	
	 					<td>开始时间：
	 					
	 					 	<input type="text" name="beginTime" id="beginTime" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
	 					 		class="Wdate BigInput" style="width:172px;height: 20px"/>
						</td>
						
						<td>结束时间：

	 						<input type="text" name="endTime" id="endTime" readonly onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\',{d:0});}',dateFmt:'yyyy-MM-dd'})" 
							    class="Wdate BigInput" style="width:172px;height: 20px"/>
	 					</td>
	 					
	 					<td></td>
	 					
 						<td class="TableData"><button class="btn-win-white" type="button" onclick="search()">查询</button></td>
	 				</tr>
				</table>
			</form>
		</div>
		<span class="basic_border"></span>
		<div class="" style="padding-top: 5px;display:inline-block;">
            <span style="float:left;height: 26px;line-height: 26px;margin:4px;">&nbsp;已选条件：</span>
            <span id="condition"></span>
        </div>
        <div id="optionCom" >
            <span class="isshow"><i class="optional fa fa-bars fa-lg"></i>可选列  </span> 
            <span class="tip">可根据需要"隐藏/显示"所选列</span> 
            <ul class="panList"></ul>
        </div>
   	</div>
    <table id="datagrid" fit="true"></table> 
    <iframe id="exportIframe" style="display:none"></iframe>
    <!--  
    <script type="text/javascript" src="/xzfy/js/caseRegister/register.js"></script>
    -->
    <script type="text/javascript" src="/xzfy/js/base/juicer-min.js"></script>
    <script type="text/javascript" src="/xzfy/js/caseList/caselist.js"></script>
   <!--  
    <script type="text/javascript" src="/xzfy/js/caseTrial/trial.js"></script>
    -->
    
</body>
</html>