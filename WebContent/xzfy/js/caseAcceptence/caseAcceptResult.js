//案件ID
var caseId = "";
//案件受理类型
var acceptType = "";
//案件详情接口类型
var type ="2";
function doInit(){
	//案件ID
	caseId = getQueryString("caseId");
	
	//请求参数
    var param = {
    	caseId:caseId,
    	type:type
    };
	var json = tools.requestJsonRs(CASEINFO_URL,param);
	if( json.rtState == true ){
		//获取登记信息返回数据
		var data = json.rtData;
		//数据封装
		acceptData(data);
		//受理类型
		acceptType = "1";
		showAcceptType(acceptType);
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

//结果类型
function showAcceptType(acceptypes,data){
	
	//立案受理
	if( acceptypes ==1){
		$("#accept").show();
		$("#unaccept").hide();
		$("#correction").hide();
		$("#inform").hide();
		$("#forword").hide();
		$("#other").hide();
		
		$("#timeTitle").html("立案时间：");
		$("#reasonTitle").html("立案意见：");
		$("#remarkdiv").hide();

	}
	//不予受理
	else if( acceptypes ==2){
		$("#accept").hide();
		$("#unaccept").show();
		$("#correction").hide();
		$("#inform").hide();
		$("#forword").hide();
		$("#other").hide();
		
		$("#timeTitle").html("不予受理时间：");
		$("#reasonTitle").html("不予受理理由：");
		$("#remarkdiv").hide();
	}
	//补正
	else if( acceptypes ==3){
		$("#accept").hide();
		$("#unaccept").hide();
		$("#correction").show();
		$("#inform").hide();
		$("#forword").hide();
		$("#other").hide();
		
		$("#timeTitle").html("补正时间：");
		$("#reasonTitle").html("补正理由：");
		$("#remarkdiv").show();
	}
	//告知
	else if( acceptypes ==4){
		$("#accept").hide();
		$("#unaccept").hide();
		$("#correction").hide();
		$("#inform").show();
		$("#forword").hide();
		$("#other").hide();
		
		$("#timeTitle").html("告知时间：");
		$("#reasonTitle").html("有权管辖的行政复议机关：");
		$("#remarkdiv").hide();
	}
	//转送
	else if( acceptypes ==5){
		$("#accept").hide();
		$("#unaccept").hide();
		$("#correction").hide();
		$("#inform").hide();
		$("#forword").show();
		$("#other").hide();
		
		$("#timeTitle").html("转送时间：");
		$("#reasonTitle").html("接收转送的行政复议机关：");
		$("#remarkdiv").hide();
	}
	//其他
	else if( acceptypes ==6){
		$("#accept").hide();
		$("#unaccept").hide();
		$("#correction").hide();
		$("#inform").hide();
		$("#forword").hide();
		$("#other").show();
		
		$("#timeTitle").html("处理时间：");
		$("#reasonTitle").html("处理理由：");
		$("#remarkdiv").hide();
	}
}

