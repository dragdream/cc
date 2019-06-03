<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>列表</title>
	<script type="text/javascript" src="/xzfy/js/common/common.js"></script>
	<script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
	<link rel="stylesheet" type="text/css" href="/xzfy/css/accept.css" />

</head>

<body style="padding-left: 10px;padding-right: 10px" onload="doInit();">

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
					<span class='establish-stage' onclick="change(this,'1')">我的待办</span>
					<span class='establish-stage active-estab' onclick="change(this,'0')">我的已办</span>
				</div>
			</form>
		</div>
   	</div>

    <table id="datagrid" fit="true" ></table> 
    
    <iframe id="exportIframe" style="display:none"></iframe>

    <script type="text/javascript" src="/xzfy/js/caseRegister/caseRegisterlist.js"></script>
    <script type="text/javascript" src="/xzfy/js/caseRegister/register.js"></script>
    
</body>
</html>