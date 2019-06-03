function selectSingleUser(a,b){
	var iframe;
	if($("#selectUserIframe").length){
		iframe = $("#selectUserIframe");
	}else{
		iframe = $("<iframe id='selectUserIframe' src='' frameborder=0 ></iframe>");
		$("body").append(iframe);
	}
	
	$("#selectUserIframe").css({
		position: "fixed",
		left:0,
		top:0,
		right:0,
		bottom:0,
		width: '100%',
		height :'100%',
		zIndex:100000000000000
	}).show();
	var url = '/system/mobile/userselect/selectPage.jsp?inputName='+b+'&hiddenUserId='+a+'&single=true';
	$("#selectUserIframe").attr("src",url);
}

function selectUser(a,b,c){
	var iframe;
	if($("#selectUserIframe").length){
		iframe = $("#selectUserIframe");
	}else{
		iframe = $("<iframe id='selectUserIframe' src='' frameborder=0 ></iframe>");
		$("body").append(iframe);
	}
	
	$("#selectUserIframe").css({
		position: "fixed",
		left:0,
		top:0,
		right:0,
		bottom:0,
		width: '100%',
		height :'100%',
		zIndex:100000000000000
	}).show();
	var url = '/system/mobile/userselect/selectPage.jsp?inputName='+b+'&hiddenUserId='+a+'&single=false&call_back_name='+c;
	$("#selectUserIframe").attr("src",url);
}


function selectDept(a,b,c){
	var iframe;
	if($("#selectDepartIframe").length){
		iframe = $("#selectDepartIframe");
	}else{
		iframe = $("<iframe id='selectDepartIframe' src='' frameborder=0 ></iframe>");
		$("body").append(iframe);
	}
	$("body").append(iframe);
	$("#selectDepartIframe").css({
		position: "fixed",
		left:0,
		top:0,
		right:0,
		bottom:0,
		width: '100%',
		height :'100%',
		zIndex:100000000000000
	}).show();
	var url = '/system/mobile/userselect/selectDepart.jsp?inputName='+b+'&hiddenUserId='+a+'&single=false&callback='+c;
	$("#selectDepartIframe").attr("src",url);
}

function selectSingleDept(a,b,c){
	var iframe;
	if($("#selectDepartIframe").length){
		iframe = $("#selectDepartIframe");
	}else{
		iframe = $("<iframe id='selectDepartIframe' src='' frameborder=0 ></iframe>");
		$("body").append(iframe);
	}
	$("body").append(iframe);
	$("#selectDepartIframe").css({
		position: "fixed",
		left:0,
		top:0,
		right:0,
		bottom:0,
		width: '100%',
		height :'100%',
		zIndex:100000000000000
	}).show();
	var url = '/system/mobile/userselect/selectDepart.jsp?inputName='+b+'&hiddenUserId='+a+'&single=true&callback='+c;
	$("#selectDepartIframe").attr("src",url);
}

function selectRole(a,b){
	var iframe;
	if($("#selectRoleIframe").length){
		iframe = $("#selectRoleIframe");
	}else{
		iframe = $("<iframe id='selectRoleIframe' src='' frameborder=0 ></iframe>");
		$("body").append(iframe);
	}
	$("body").append(iframe);
	$("#selectRoleIframe").css({
		position: "fixed",
		left:0,
		top:0,
		right:0,
		bottom:0,
		width: '100%',
		height :'100%',
		zIndex:100000000000000
	}).show();
	var url = '/system/mobile/userselect/selectRole.jsp?inputName='+b+'&hiddenUserId='+a+'&single=false&callback=';
	$("#selectRoleIframe").attr("src",url);
}

function selectSingleRole(a,b){
	var iframe;
	if($("#selectRoleIframe").length){
		iframe = $("#selectRoleIframe");
	}else{
		iframe = $("<iframe id='selectRoleIframe' src='' frameborder=0 ></iframe>");
		$("body").append(iframe);
	}
	$("body").append(iframe);
	$("#selectRoleIframe").css({
		position: "fixed",
		left:0,
		top:0,
		right:0,
		bottom:0,
		width: '100%',
		height :'100%',
		zIndex:100000000000000
	}).show();
	var url = '/system/mobile/userselect/selectRole.jsp?inputName='+b+'&hiddenUserId='+a+'&single=true&callback=';
	$("#selectRoleIframe").attr("src",url);
}