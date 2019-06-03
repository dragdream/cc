//显示页签的标识符
var pageFlag = 0;

function doInit() {
	showpage(pageFlag);
}

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
}

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
}

function save() {

}

function showpage(index) {
	console.log(index)
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