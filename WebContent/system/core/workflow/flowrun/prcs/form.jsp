<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.tianee.oa.oaconst.TeeConst" %>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ contextPath + "/";
	//获取主题的索引号
	int styleIndex = 1;
	Integer styleInSession = (Integer) request.getSession().getAttribute("STYLE_TYPE_INDEX");
	
	TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
	
	
	if (styleInSession != null) {
		styleIndex = styleInSession;
	}
	String stylePath = contextPath + "/common/styles";
	String imgPath = stylePath + "/style" + styleIndex + "/img";
	String cssPath = stylePath + "/style" + styleIndex + "/css";
	String systemImagePath = contextPath+"/common/images";
	
	
	String loginOutText = (String) request.getSession().getAttribute("LOGIN_OUT_TEXT") == null ? "" : (String) request.getSession().getAttribute("LOGIN_OUT_TEXT");//退出提示语
	String ieTitle = (String) request.getSession().getAttribute("IE_TITLE") == null ? "" : (String) request.getSession().getAttribute("IE_TITLE");//主界面IEtitle
	String secUserMem = (String) request.getSession().getAttribute("SEC_USER_MEM") == null ? "" : (String) request.getSession().getAttribute("SEC_USER_MEM");//是否记忆用户名
%>
<!-- jQuery库 -->
<script src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script src="<%=contextPath %>/common/js/grayscale.js"></script>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script src="<%=contextPath %>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>

<script type="text/javascript" >
window.UEDITOR_HOME_URL = "<%=contextPath%>/common/ueditor/";
$.browser = {};
$.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());
$.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
$.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
$.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());
</script>


<!-- 其他工具库类 -->
<script src="<%=contextPath%>/common/js/md5.js"></script>
<script src="<%=contextPath%>/common/js/tools.js?v=1"></script>
<script src="<%=contextPath%>/common/js/sys.js?v=2018050301"></script>
<script src="<%=contextPath%>/common/js/src/orgselect.js?v=3"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>


<!-- jQuery 布局器 -->
<script src="<%=contextPath%>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath%>/common/ueditor/ueditor.all.min.js"></script>
<!-- 区域联动js -->
<script type="text/javascript" src="/common/js/address_cascade.js"></script>
<script type="text/javascript">

/** 常量定义 **/
var TDJSCONST = {
  YES: 1,
  NO: 0
};
/** 变量定义 **/
var contextPath = "<%=contextPath%>";
var imgPath = "<%=imgPath%>";
var cssPath = "<%=cssPath%>";
var stylePath = "<%=stylePath%>";
var loginOutText = "<%=loginOutText%>";
var uploadFlashUrl = "<%=contextPath%>/common/swfupload/swfupload.swf";
var commonUploadUrl = "<%=contextPath%>/attachmentController/commonUpload.action";
var systemImagePath = "<%=systemImagePath%>";
var rand = new Date().getTime()*parseInt((Math.random()*10000+"").split("\\.")[0]);
var websignUrl = "http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=contextPath%>/sealManage/stampPriv.action?sid=";
var signatures = [];//签章数组
var loadFirst = true;
var xparent;
if(window.dialogArguments){
	xparent = window.dialogArguments;
}else if(window.opener){
	xparent = opener;
}else{
	xparent = window;
}
function parseNumber(value, defValue) {
  if (isNaN(value)) {
    return defValue;
  }
  return value * 1;
}
</script>


	<%@ include file="/header/upload.jsp" %>
	<%@ include file="/header/userheader.jsp" %>
	<%
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"),
				0);
		int frpSid = TeeStringUtil.getInteger(request
				.getParameter("frpSid"), 0);
		int flowId = TeeStringUtil.getInteger(request
				.getParameter("flowId"), 0);
		String isNew = TeeStringUtil.getString(request
				.getParameter("isNew"), "");
	%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>流程办理</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/images/workflow/index.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/ckeditor/contents.css">
	<script type="text/javascript" src="<%=contextPath%>/common/js/workflowUtils.js?v=2018072501"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script>
	<script src="<%=contextPath %>/system/core/workflow/flowrun/prcs/js/form.js?v=2018071802"></script>
	<%@ include file="/header/kinggrid.jsp" %>
<style>
p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol
{
	font-size:14px;
	margin:0px;
	padding:0px;
}
table{
border-collapse:collapse;
font-size:12px;
}
.remarkShadowDivInnerDiv:hover{
	background:red;
}
#aips{
	position:fixed;
	top:0px;
	left:60px;
	padding:5px;
	border-left:1px solid #cdcdcd;
	border-bottom:1px solid #cdcdcd;
	border-right:1px solid #cdcdcd;
	background:white;
}
#aips a{
font-size:12px;
margin-right:10px;
}
</style>
<script>

var contextPath = "<%=contextPath%>";
var runId = <%=runId%>;
var flowId = <%=flowId%>;
var frpSid = <%=frpSid%>;
var isNew = '<%=isNew%>';
var imgPath = '<%=imgPath%>';
var fileImgPath = imgPath +'/dll.gif';
var fileDelImgPath = imgPath +'/remove.png';
var delfeedbackPath = imgPath +'/delete.gif';
var lookfeedbackPath = imgPath +'/focus.gif';
var runName;
var sealForm = 2;
var userName = "<%=person.getUserName()%>";
var userIdDesc = "<%=person.getUserId()%>";
var feedback = 1;
var attachPriv = 1;
var attachOtherPriv=0;
var feedbackPriv=1;
var flowPrcs;
var userId = <%=loginUser.getUuid()%>;
var deptId = <%=loginUser.getDept().getUuid()%>;
var deptName = "<%=loginUser.getDept().getDeptName()%>";
var deptFullName = "<%=loginUser.getDept().getDeptFullName()%>";
var roleId = <%=loginUser.getUserRole().getUuid()%>;
var roleName = "<%=loginUser.getUserRole().getRoleName()%>";
//#######################重要的签章 参数 zhp 20131114 解决表单印章 盖章 验章 参数################################//
var signJson = {};//需要验章的 字段
var signArray = new Array();//盖了章的 data
var noSignArray =  new Array();//没有盖章的 data

var mobileSignJson = {};//需要验章的 字段
var mobileSignArray = new Array();//盖了章的 data

var ctrlSignArray = [];//控件签章data数组
var ctrlRandArray = [];//控件随机数数组

var mobileHwStores = [];//移动签批数据数组
var mobileHwArray = [];//移动签批数据数组

var h5HwStores = [];//h5手写签批数组
var h5HwArray = [];//h5手写签批数组

//#######################重要的签章 参数 结束##################################################################//
var officePriv = 1+2+4+8+16+32+64+128+256;//office文档权限

var pubAttachUploader;//公共附件上传者
var feedbackAttachUploader;//会签附件上传者
var flowType;
var frpModel;
var editor;

var systemImagePath = "<%=systemImagePath%>";

//地理位置
function selectPosition(data,extra,positionType){
	if(positionType=="1"||positionType==1){
		alert("PC端暂不支持自动获取当前位置！");
	}else{
		var url=contextPath+"/system/core/workflow/flowrun/prcs/map.jsp?data="+data+"&extra="+extra+"&positionType="+positionType;
		openFullWindow(url);
	}
}


//地图预览
function  previewPosition(spanId){
		//获取坐标点
		var points=document.getElementById(spanId).nextSibling.nextSibling.value;
		var lng=points.split(",")[0];
		var lat=points.split(",")[1];
		
		var url=contextPath+"/system/core/workflow/flowrun/prcs/mapPreview.jsp?lng="+lng+"&lat="+lat;
		
		top.bsWindow(url ,"地图预览",{width:"600",height:"300",buttons:
			[
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="保存"){
			
			}else if(v=="关闭"){
				return true;
			}
		}});
	}
	
	
	
	//双击会签控件
	function dbClickFbCtrl(itemId){
		var url=contextPath+"/system/core/workflow/flowrun/prcs/selectZdld.jsp?itemId="+itemId;
		dialog0(url,200,250);
	}
</script>
</head>
<body onload="doInit()" style="margin:0px;">
<!-- <div id="aips"> -->
<!-- 	<a>呈批件1</a><a>呈批件2</a><a>呈批件3</a> -->
<!-- </div> -->
<!-- Center -->
<div id="center-container" class="pane ui-layout-center">
	<!-- 表单渲染区域 -->
	<form id="form">
		<div id="formDiv"  class="wf"></div>
		
		<div style="margin-top:20px;display:none" id="SYS_DOC_SEND">
			<center>
				<table style="width:90%" align="center" class="TableBlock">
					<thead>
						<tr>
							<td class="TableHeader" style="font-size:12px">公文发送</td>
						</tr>
					</thead>
					<tbody style="padding:10px;">
						<tr>
							<td style="font-size:12px">
								<b>发送单位：</b>
								<br/>
								<input type="hidden" id="SYS_DOC_DEPT_IDS" name="SYS_DOC_DEPT_IDS"/>
								<textarea id="SYS_DOC_DEPT_NAMES" name="SYS_DOC_DEPT_NAMES" class="BigTextarea" readonly style="width:90%;height:60px;"></textarea>
								<br/>
								<a href="javascript:void(0)" onclick="selectDept(['SYS_DOC_DEPT_IDS','SYS_DOC_DEPT_NAMES'])">选择</a>
								&nbsp;
								<a href="javascript:void(0)" onclick="clearData('SYS_DOC_DEPT_IDS','SYS_DOC_DEPT_NAMES')">清空</a>
							</td>
						</tr>
					</tbody>
				</table>
			</center>
		</div>
	</form>
	
	<!-- 公共附件区开始 zhp 20131020 -->
	<div style="margin-top:20px;" name="pulicAttachmentForm" id="pulicAttachmentForm" method="post" enctype="multipart/form-data">
		<div id="workFlowAttach">
			<table style="width:90%" align="center" class="TableBlock">
				<thead>
					<tr>
						<td id="attaHeader"  style="padding:0px;font-size:12px;text-align:left;border-top:2px solid #1aba58;background:url(images/attach_header_bg.png);height:52px;background-repeat:no-repeat;border-left:1px solid #1aba58;padding-left:40px;font-size:14px;font-weight:bold;color:#1aba58">相关材料</td>
					</tr>
				</thead>
				<tbody>
					<tr height="25">
						<td style="padding:20px;padding-top:0px">
							<table style="border:1px solid #dddad5;width:100%" >
								<tr>
									<td class="TableData"  style="font-size:12px">
										<div id="pulicAttachments" style="font-size:12px"></div>
										<div id="pulicAttachmentsTemp" style="font-size:12px"></div>
									</td>
								</tr>
								<tr height="25" id="newAttachTR">
									<td class="TableData"  style="font-size:12px"><input id="new_type1" name="doc_type"
										onclick="document.all('new_type').value='doc'" checked="checked"
										value="" type="radio"> <label for="new_type1">word文档</label>
									<input id="new_type2" onclick="document.all('new_type').value='xls'"
										name="doc_type" value="" type="radio"> <label
										for="new_type2">excel文档</label> <input id="new_type3"
										onclick="document.all('new_type').value='ppt'" name="doc_type"
										value="" type="radio"> <label for="new_type3">ppt文档</label>&nbsp;&nbsp;
									<b>附件名：</b> <input class="BigInput" name="new_name" id="new_name" value="新建文档"> 
										<button type="button" class="btn btn-success" onclick="newAttach()">在线创建附件</button> <input name="new_type" id="new_type" type="hidden" value="doc" />
									</td>
								</tr>
								<tr id="addAttachTR">
									<td class="TableData"  style="font-size:12px">
									<div id="pubUpfile" class="add_swfupload"  style="font-size:12px">
										<a href="javascript:void(0)"><span style="font-size:12px">添加公共附件</span></a>
									</div>
									<input type="hidden" id="pubUpfileValues" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>	
	<!--公共附件区结束-->
	<!-------------------------------------------会签意见区开始 zhp 20131020------------------------------------------------------------------- -->
		<div id="feedbackTitleDiv"  style="margin-top:20px;">
			<form name="feedBackForm" action="<%=request.getContextPath() %>/feedBack/addFeedBack.action" id="feedBackForm" method="post" enctype="multipart/form-data" target="feedbackIframe">
			<table style="width:90%" align="center" class="TableBlock">
				<thead>
					<tr>
						<td id="fbHeader" colspan="2" style="padding:0px;font-size:12px;text-align:left;border-top:2px solid #176fd1;background:url(images/feedback_header_bg.png);height:52px;background-repeat:no-repeat;border-left:1px solid #176fd1;padding-left:40px;font-size:14px;font-weight:bold;color:#176fd1">
							办理意见
<!-- 							<span id="feedbackSealBtn"> -->
<!-- 							&nbsp;&nbsp; -->
<!-- 							<input  value="0" type='checkbox' id="startHandWrite" /> -->
<!-- 								<label for="startHandWrite"><b>启用会签手写签章功能</b></label>  -->
<!-- 							</span> -->
						</td>
					</tr>
				</thead>
				<TBODY>
					<TR>
						<TD style="padding:20px;padding-top:0px" colspan="2">
							<DIV class="contest" id="feedBackContest"></DIV> 
						</TD>
					</TR>
					<TR class="TableData"  id="feedbackAttachDiv">
						<TD style="border:1px solid #dddad5;width:80px;font-size:12px;color:#000" >
							办理附件
						</TD>
						<TD  style="border:1px solid #dddad5">
							<div id="upfile"></div>
							<a id="upFileHolder" href="javascript:void(0)"  style="position:relative;font-size:12px" >点击添加办理附件</a>
<!-- 							&nbsp;&nbsp;<img style="cursor:pointer" title="点击录制语音" onclick="RecordVoice('FEEDBACK_VOICE')" src="/common/images/workflow/voice.png"/> -->
<!-- 							<input type="hidden" id="FEEDBACK_VOICE" name="voiceId" value=""/> -->
						</TD>
					</TR>
					<TR class="TableControl" id="signTR" style="display:none"  id="feedbackSealDiv">
						<TD colspan="2">
							<DIV align="left" style="font-size:12px">
								<button type="button" class="" onclick="WebSignHandWritePop()">手写</button>
								<button type="button" class="" onclick="WebSignAddSeal()">盖章</button>
							</DIV>
						</TD>
					</TR>
					<TR class="TableContent"  id="feedbackDiv">
						<TD id="SIGN_INFO_FEEDBACK" colspan="2">
							<div id="remarkShadowDiv" name="remarkShadowDiv" style="display:inline-block;width:100%;background:#f0f0f0">
                            	<div id="remarkShadowDivInnerDiv" class="remarkShadowDivInnerDiv" style="margin: 10px; height: 30px; border: 1px solid rgb(214, 214, 214); background: rgb(255, 255, 255); color: rgb(162, 162, 162); cursor: pointer;" onclick="editor.focus();$('#remarkShadowDiv').hide();$('#uEditorDiv').show();" title="会签意见">
                             	<span style="margin:0 5px;"><img style="vertical-align:middle;" src="images/sign_wev8.png"></span>
                             	<span style="line-height:30px;font-size:12px">
                             		请输入办理意见 
                             	</span>
                            	</div>
                       		</div>
							<DIV id="uEditorDiv" style="display:none">
								<textarea style="" id="content" name="content" ></textarea>
								 <script type="text/javascript">
    							</script>
								<input type="hidden" id="FEEDBACK_SIGN_DATA" name="FEEDBACK_SIGN_DATA" />
							</DIV>
						</TD>
					</TR>
				</TBODY>
			</table>
			<input type="hidden" name="frpSid" value="<%=frpSid%>"></input>
			<input type="hidden" name="runId" value="<%=runId%>"></input>
			</form>
		</div>
		<iframe id="feedbackIframe" name="feedbackIframe" style="display:none"></iframe>
	<!-------------------------------------------会签意见区结束------------------------------------------------------------------- -->
</div>
<br/><br/><br/><br/>
<iframe id="xListDataUploadIframe" name="xListDataUploadIframe" style="display:none"></iframe>
<script src="js/websign.js?v=<%=System.currentTimeMillis()%>"></script>
<script src="js/BJCAWebSignCom.js?v=<%=System.currentTimeMillis()%>"></script>
<div id="attachDiv" style="overflow: hidden"></div>
<iframe style="display:none" id="frame007"></iframe>
</body>
</html>