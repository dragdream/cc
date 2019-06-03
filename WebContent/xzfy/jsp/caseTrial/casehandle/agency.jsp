<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="/xzfy/css/common/supervise.css" />
<!-- 中腾按钮框架 -->
<link rel="stylesheet" type="text/css" href="/common/zt_webframe/css/package1.css" />
<link rel="stylesheet" type="text/css" href="/xzfy/css/accept.css" />

<title>审理待办</title>
<style> 
	tr:nth-child(even){
		 background-color:#F0F0F0;
	}
</style>

</head>
<body style="height:100%" onload="doInit(0);">
		<div id="toolbar" class = " clearfix" style="margin-top: 5px">
				<div class="fl" style="position:static;margin-top: 6px;">
					<span class='iconfont icon-falv' style="font-size:20px;"></span>
					<span class="title" id="title"></span>
				</div>
	    <div class = "right fr clearfix" id="register">
	    	<input type="button" class="btn-win-white" onclick="register();" value="登记"/>&nbsp;
	        <input type="button" class="btn-win-white" onclick="selectNext()" value="选择受理人"/>&nbsp;
	    </div>
	    
	    <span class="basic_border_grey" style="margin-top: 10px"></span>
	    
	    <div class="setHeight">
	        <form id="form1" style="">
				<input type="hidden" id="orgId" value="" >
				<div class='form-container'>
				<div calss='form-row-one' style="text-align:right">
						<span class='form-item'>
								<span class='form-label'>
									 案件编号：
								</span>
								<input class="BigInput" type="text" name="caseNum" id="caseNum" onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5a-zA-Z0-9\w]/g,'')"							/>
							</span>
								<span class='form-item'>
										<span class='form-label'>
												申请人：
										</span>
										<input class="BigInput" type="text" name="caseNum" id="caseNum" onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5a-zA-Z0-9\w]/g,'')"							/>
									</span>

									<span class='form-item'>
											<span class='form-label'>
												 被申请人：
											</span>
											<input class="BigInput" type="text" name="respName" id="respName" onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5a-zA-Z0-9\w]/g,'')"/>
										</span>
				</div>
			</div>
			<div calss='form-row-two' style="text-align:right">
					<span class='form-item'>
							<span class='form-label'>
								申请日期：
							</span>
							<span style="width:70%">
								<input type="text" name="beginTime" id="beginTime" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="date-input"/>&nbsp;至
								<input type="text" name="endTime" id="endTime" readonly onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\',{d:0});}',dateFmt:'yyyy-MM-dd'})"
								class="date-input"  />
							</span>
						</span>
					<span class='form-item'>
							<span class='form-label'>
								申请方式：
							</span>
							<select class="BigInput" id="postType" name="postType">
									<option>---请选择---</option>
								</select>
						</span>
					<span class='form-item'>
							<span class='form-label'>
									案件状态：
							</span>
							<select class="BigInput" id="caseStatus" name="caseStatus">
									<option>---请选择---</option>
								</select>
						</span>

			</div>
			<div class='stage-title'>
					<span class='establish-stage active-estab' onclick="change(this,'0')">我的待办</span>
					<span class='establish-stage ' onclick="change(this,'1')">我的已办</span>
				</div>
			</form>
		</div>
   	</div>


<!-- 这是列表页 -->	
	<table class="TableBlock" style="height:100%" id="datagrid"></table>

<script type="text/javascript" src="<%=contextPath %>/system/core/base/meeting/js/meeting.js"></script>
<script type="text/javascript" src="<%=contextPath%>/xzfy/js/jquery.tips.js"></script>
<script type="text/javascript" src="<%=contextPath %>/xzfy/js/caseTrial/common/casetriallist_agency.js"></script>
<script type="text/javascript">
function change(that,type){
	$(that).siblings().removeClass("active-estab");
	$(that).removeClass("active-estab").addClass("active-estab");
	doInit(type);
}
</script>
</body>
</html>