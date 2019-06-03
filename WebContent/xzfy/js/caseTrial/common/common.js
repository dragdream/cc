//案件ID
var caseId = $('#caseId').val(); //案件ID
//初始化
function doInit() {
	//展示第2页
	showpage(3);
	caseId = getQueryString("caseId");
}

$(document).ready(function() {
    $(".lanky-tab").click(function() {
    	$(".lanky-tab").removeClass("actived");
    	$(this).addClass("actived");
        showpage($(this).index());
    });
});

//页签切换
function showpage(index) {
	//登记信息
	if(index == 0 ){
		$("#myframe").attr("src","/xzfy/jsp/caseTrial/blendpage/index.jsp?caseId="+caseId);
	}
	//案件信息
	else if(index ==1){
		$("#myframe").attr("src","/xzfy/jsp/caseAcceptence/caseInfo.jsp?caseId="+caseId);
	}
	//立案受理
	else if(index ==2){
		$("#myframe").attr("src","/xzfy/jsp/caseAcceptence/caseAcceptResult.jsp?caseId="+caseId);
	}
	//调查取证
	else if(index ==3){
		$("#myframe").attr("src","/xzfy/jsp/caseTrial/iaecollec/index.jsp?caseId="+caseId);
	}
	//复议决定
	else if(index ==4){
		$("#myframe").attr("src","/xzfy/jsp/caseTrial/decision/index.jsp?caseId="+caseId);
	}
	//辅助操作
	else if(index ==5){
		$("#myframe").attr("src","/xzfy/jsp/caseTrial/auxiliaryOperation/index.jsp?caseId="+caseId);
	}
	//案卷管理
	else if(index ==6){
		$("#myframe").attr("src","/xzfy/jsp/caseClose/caseClose.jsp?caseId="+caseId);
	}
	//归档管理
	else if(index ==7){
		$("#myframe").attr("src","/xzfy/jsp/caseArchive/caseArchive.jsp?caseId="+caseId);
	}
	//办理进度
	else if(index ==8){
		$("#myframe").attr("src","/xzfy/jsp/caseAcceptence/caseProgress.jsp?caseId="+caseId);
	}
	
	
}

