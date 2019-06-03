//案件ID
var caseId = "";
//案件详情接口类型
var type ="2";
//初始化
function doInit() {
	
	caseId = getQueryString("caseId");
	caseNum = unescape(getQueryString("caseNum"));
	time = unescape(getQueryString("time"));
	$("#caseNum").html(caseNum);
	$("#time").html(time);
	
	//请求参数
    var param = {
    	caseId:caseId,
    	type:type
    };
	var json = tools.requestJsonRs(CASEINFO_URL,param);
	if( json.rtState == true ){
		//获取登记信息返回数据
		var data = json.rtData;
		//案件受理子状态
		var index = 0;
		var status = data.caseSubStatusCode;
		if( status!=null &&status!=""){
			if( status=="01"){
				index = 2;
			}
			else if(status == "02"){
				index = 3;
			}
			else if(status == "03"){
				index = 4;
			}
			else if(status == "04"){
				index = 5;
			}
			else if(status == "05"){
				index = 6;
			}
			else if(status == "06"){
				index = 7;
			}
			//展示第index页
			showpage(index);
			//去掉点击事件
//			for(var i=2;i<8;i++){
//				if(i==index){
//					continue;
//				}
//				else{
//					$(".lanky-tab").eq(i).unbind("click");
//					$(".lanky-tab").eq(i).addClass("disabled-grey");
//				}
//			}
			//添加active样式
			$('.lanky-tab').eq(index).addClass("actived");
		}
		else{
			//展示第2页
			index = 2;
			showpage(index);
			$('.lanky-tab').eq(index).addClass("actived");
		}
	}
	else{
		$.MsgBox.Alert_auto("查询失败,请联系管理员！");
	}
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
		$("#myframe").attr("src","/xzfy/jsp/caseAcceptence/caseRegister.jsp?caseId="+caseId);
	}
	//案件信息
	else if(index ==1){
		$("#myframe").attr("src","/xzfy/jsp/caseAcceptence/caseInfo.jsp?caseId="+caseId);
	}
	//立案受理
	else if(index ==2){
		$("#myframe").attr("src","/xzfy/jsp/caseAcceptence/caseAccept.jsp?caseId="+caseId);
	}
	//不予受理
	else if(index ==3){
		$("#myframe").attr("src","/xzfy/jsp/caseAcceptence/caseUnAccept.jsp?caseId="+caseId);
	}
	//补正
	else if(index ==4){
		$("#myframe").attr("src","/xzfy/jsp/caseAcceptence/caseCorrection.jsp?caseId="+caseId);
	}
	//告知
	else if(index ==5){
		$("#myframe").attr("src","/xzfy/jsp/caseAcceptence/caseInform.jsp?caseId="+caseId);
	}
	//转送
	else if(index ==6){
		$("#myframe").attr("src","/xzfy/jsp/caseAcceptence/caseForword.jsp?caseId="+caseId);
	}
	//其他
	else if(index ==7){
		$("#myframe").attr("src","/xzfy/jsp/caseAcceptence/caseOther.jsp?caseId="+caseId);
	}
	//案卷管理
	else if(index ==8){
		$("#myframe").attr("src","/xzfy/jsp/caseClose/caseClose.jsp?caseId="+caseId);
	}
	//归档管理
	else if(index ==9){
		$("#myframe").attr("src","/xzfy/jsp/caseArchive/caseArchive.jsp?caseId="+caseId);
	}
	//办案进度
	else if(index ==10){
		$("#myframe").attr("src","/xzfy/jsp/caseAcceptence/caseProgress.jsp?caseId="+caseId);
	}
	
}

