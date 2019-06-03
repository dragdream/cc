//案件ID
var caseId = "";
//案件详情接口类型
var type ="2";
//案件受理状态
var status = "";
//是否审理
var isApproval = "";

function doInit(){
	//案件ID
	if(caseId=="" || caseId == null){
		caseId = getQueryString("caseId");
	}
	//请求参数
    var param = {
    	caseId:caseId,
    	type:type
    };
	var json = tools.requestJsonRs(CASEINFO_URL,param);
	if( json.rtState == true ){
		//获取登记信息返回数据
		var data = json.rtData;
		//数据状态
		status = data.caseSubStatusCode;
		isApproval = data.isApproval;
		//数据封装
		acceptData(data);
	}
	else{
		$.MsgBox.Alert_auto("查询失败,请联系管理员！");
	}
}

//数据封装
function acceptData(data){
	//申请人列表
	var list = data.applicantList;
	var val = "";
	if( list!=null){
		for(var i=0;i<list.length;i++){
			var it = list[i];
			val = val + "姓名："+it.name+",证件类型："+it.certificateTypeCode+",证件号："+it.ceritificateId+",性别："+it.sexCode 
			    + ",民族："+ it.nationCode + ",送达地址："+ it.postAddress+",邮编："+it.postCode+",联系电话："+it.phoneNum+";";
		}
		$("#applicantList").val(val);
	}
	//被申请人
	list = data.respondentList;
	val = "";
	if( list != null){
		for(var i=0;i<list.length;i++){
			var it = list[i];
			val = val + "被申请人类型："+it.respondentType+",被申请人姓名："+it.respondentName+";";
		}
		$("#respondentList").val(val);
	}
	//复议请求
	$("#requestForReconsideration").val(data.requestForReconsideration);
	//事实和理由
	$("#factsAndReasons").val(data.factsAndReasons);
	
	//材料
	$("#apply-table").append(createCaseMaterial(data.applyMaterialList));
	$("#accept-table").append(createCaseMaterial(data.filingMaterialList));
	//材料展示
	$("#apply-table").hide();
	$("#accept-table").show();
}

//拼接案件材料
function createCaseMaterial(data){
	var html = "";
	var length = data.length;
	if( length > 0 ){
		for(var i=0;i<length;i++){
			html = html + "<tr>";
			html = html + "<td>"+(i+1)+"</td>";
			html = html + "<td>"+data.fileType+"</td>";
			html = html + "<td>"+data.fileName+"</td>";
			html = html + "<td><span>预览</span><span>删除</span></td>";
			html = html + "</tr>";
		}
	}
	else{
		html = html + "<tr>";
		html = html + "<td colspan='4'>暂无数据</td>";
		html = html + "</tr>";
	}
	return html;
}
//更改受理材料tab切换
function changeFormShow(that,type){
	//样式切换
	$(that).siblings().removeClass("active-estab");
	$(that).addClass("active-estab");
	if(type==1){
		$("#apply-table").show();
		$("#accept-table").hide();
	}
	else if(type==2){
		$("#apply-table").hide();
		$("#accept-table").show();
	}
}

/**********************************************************/
//保存
function submit(){
	if( status != null && status != ""){
		$.MsgBox.Alert_auto("案件已受理,不能再受理！");
		return false;
	}
	//请求参数
	var param = {
	  	caseId:caseId,
	  	type:2,
	  	acceptTime:$("#acceptTime").val(),
	  	reason:$("#reason").val()
	};
	var json = tools.requestJsonRs(ACCEPT_URL,param);
	if( json.rtState == true ){
		$.MsgBox.Alert_auto("保存成功！");
		doInit();
	}
	else{
		$.MsgBox.Alert_auto("保存失败,请联系管理员！");
	}
}
//立案审批
function approval(){
	if( status == null || status == ""){
		$.MsgBox.Alert_auto("案件未受理,不能审批！");
		return false;
	}
	if(isApproval == 0){
		
	}
	else if(isApproval == 1){
		$.MsgBox.Alert_auto("审批中,不能再审批！");
		return false;
	}
	else{
		$.MsgBox.Alert_auto("审批完成,不能再审批！");
		return false;
	}
}

//选择审理人
function chooseNext(){
	
	if( status == null || status == ""){
		$.MsgBox.Alert_auto("案件未受理,不能选择审理人！");
		return false;
	}
	if(isApproval == 0){
		$.MsgBox.Alert_auto("案件未审批,不能选择审理人！");
		return false;
	}
	else if(isApproval == 1){
		$.MsgBox.Alert_auto("案件审批中,不能选择审理人！");
		return false;
	}

	//弹出框
	bsWindow("/xzfy/jsp/common/chooseAcceptor.jsp?caseId="+caseId, "选择承办人", {
		width : "400",
		height : "250",
		buttons:[
		    {name:"保存",classStyle:"btn btn-primary"},
		 	{name:"关闭",classStyle:"btn btn-primary"}
		],
		submit : function(v, h) {
			var result = h[0].contentWindow;
			if(v == "保存"){
				var result = result.commit();
				if(result){
					location.reload();
					//保存数据
//					var param = {
//				    	caseId:caseId,
//				    	type:1,
//				    	acceptTime:$("$acceptTime").val(),
//				    	reason:$("#reason").val()
//				    };
//					var json = tools.requestJsonRs(ACCEPT_URL,param);
					return true;
				}
			}
			if(v =="关闭"){
				return true;
			}
		}
	});
}

//选择
function getUnAcceptReason(){
	bsWindow("/xzfy/jsp/caseAcceptence/chooseUnAcceptReason.jsp", "选择承办人", {
		width : "750",
		height : "250",
		buttons:
		[
			 {name:"保存",classStyle:"btn btn-primary"},
		 	 {name:"关闭",classStyle:"btn btn-primary"}
		],
		submit : function(v, h, c, b) {
			var result = h[0].contentWindow;
			if(v == "保存"){
				var reason = result.giveFatherReason();
				//将中止理由回显
				$("#chooseReason").val(reason);
				//关闭子页面
				return true; 
			}else{
				return true;  
			}
		}
	});
}