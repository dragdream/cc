//显示页签的标识符
var pageFlag = 0;

function doInit() {
	alert(pageFlag);
	showpage(pageFlag);
	route();
}

function route(){
	alert("123456");
	switch (pageFlag) {
	case 0:
		receptionInit();
		break;
	case 1:
		alert("987654321");
		clientInit();
		break;
	case 2:
		
		break;
	case 3:
		
		break;

	default:
		break;
	}
}

// 上一步
function back() {
	$('#forward').attr("disabled", false);
	$('#forward').css("color", '#aaa');
	pageFlag--;
	if (pageFlag <= 0) {
		$('#back').attr("disabled", true);
		$("#back").css('color', '#A9A9A9');
		showpage(0);
	}
	else {
		$('#back').attr("disabled", false);
		$("#forward").css('color', '#3379b7');
		showpage(pageFlag);
	}
	route();
}

// 下一步
function forward() {
	$('#back').attr("disabled", false);
	$('#back').css("color", '#aaa');
	pageFlag++;
	if (pageFlag >= 3) {
		$('#forward').attr("disabled", true);
		$("#forward").css('color', '#A9A9A9');
		showpage(3);
	}
	else {
		$('#forward').attr("disabled", false);
		$("#forward").css('color', '#3379b7');
		showpage(pageFlag);
	}
	alert(pageFlag);
	saveReception();
	route();
//	window.location.href = "/xzfy/jsp/caseRegister/clientInfo.jsp";
}

// 暂存
function save() {

}

// 案件提取
function getCaseInfo_btn() {
	getCaseExtract();
}

function showpage(index) {
	pageFlag = index;
	if (pageFlag == 0) {
		$('#back').attr("disabled", true);
		$('#forward').attr("disabled", false);
		$("#back").css('color', '#A9A9A9');
		$('#forward').css("color", '#aaa');
	} else if (pageFlag == 3) {
		$('#back').attr("disabled", false);
		$('#forward').attr("disabled", true);
		$('#back').css("color", '#aaa');
		$("#forward").css('color', '#A9A9A9');
	} else {
		$('#back').attr("disabled", false);
		$('#forward').attr("disabled", false);
		$('#back').css("color", '#aaa');
		$('#forward').css("color", '#aaa');
	}
	$(".content").hide();
	$(".content").eq(pageFlag).show();
}