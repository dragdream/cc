
//案件详情接口类型
var type ="6";

//状态
var status ="";
var index = 0;
//记录列表
var progressList = "";

//案件信息-div
function doInit(){
	//案件ID
	var caseId = getQueryString("caseId");
  	//请求参数
//    var param = {
//      	caseId:caseId,
//      	type:type
//    };
//  	var json = tools.requestJsonRs(CASEINFO_URL,param);
//  	if( json.rtState == true ){
//  		//获取案件信息返回数据
//  		var data = json.rtData;
//  	}
//  	else{
//  		$.MsgBox.Alert_auto("查询失败,请联系管理员！");
//  	}
	init(null);
}

//初始化进度
function init(data){
	status = "03";
	//登记中,已登记
	if( status== "00" || status =="01"){
		$("#caseRegister").removeClass("undo").addClass("doing");
		index = 1;
	}
	//受理
	else if( status =="02"){
		$("#caseRegister").removeClass("undo").addClass("finish");
		$("#caseAccept").removeClass("undo").addClass("doing");
		index = 2;
	}
	//审理
	else if( status =="03"){
		$("#caseRegister").removeClass("undo").addClass("finish");
		$("#caseAccept").removeClass("undo").addClass("finish");
		$("#caseTrial").removeClass("undo").addClass("doing");
		index = 3;
	}
	//结案
	else if( status =="04"){
		$("#caseRegister").removeClass("undo").addClass("finish");
		$("#caseAccept").removeClass("undo").addClass("finish");
		$("#caseTrial").removeClass("undo").addClass("finish");
		$("#caseClose").removeClass("undo").addClass("doing");
		index = 4;
	}
	//归档
	else if( status =="05"){
		$("#caseRegister").removeClass("undo").addClass("finish");
		$("#caseAccept").removeClass("undo").addClass("finish");
		$("#caseTrial").removeClass("undo").addClass("finish");
		$("#caseClose").removeClass("undo").addClass("finish");
		$("#caseAricive").removeClass("undo").addClass("doing");
		index = 5;
	}
	//归档完成
	else if( status =="06"){
		$("#caseRegister").removeClass("undo").addClass("finish");
		$("#caseAccept").removeClass("undo").addClass("finish");
		$("#caseTrial").removeClass("undo").addClass("finish");
		$("#caseClose").removeClass("undo").addClass("finish");
		$("#caseAricive").removeClass("undo").addClass("finish");
		index = 5;
	}
	change(index);
}

//切换
function change(val){
	if(val<=index){
		var str = createHtml(val);
		$("#content").html(str);

		//js 动态效果
		$("#stepcover").children(".tabline").removeClass("active");
		$("#stepcover").children(".tabline").html("");
		$("#stepcover").children(".tabline:eq("+(val-1)+")").addClass("active");
		$("#stepcover").children(".tabline:eq("+(val-1)+")").html("<img src='/xzfy/imgs/active.png' alt=''/>");
	}
	else{
		return false;
	}
}

function createHtml(val){
	var html = "";
	//var list = progressList[val];
	var length = 1+parseInt(val);
	for(var i=0;i<length;i++){
		html = html + "<div class='stepdetail ";
	    if(i==0){
	    	html = html + "firstdetail"
	    }
		html = html +" '>"+
            "<span class='steptime'>2019-10-11 15:00</span>"+
            "<span class='circle iconfont'>&#xe62f;</span>"+
            "<span class='stepstatus'>由 XXX 进行了 选择审理人 操作，案件状态为 审理中</span>"+
            "</div>"+
            "<div class='steproom'>"+
            "<span class='steptime'></span>"+
            "<span class='line'></span>"+
            "<span class='stepstatus'></span>"+
            "</div>";
	}
	return html;
}
