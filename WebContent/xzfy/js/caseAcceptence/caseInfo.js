
/*******************************************************/

//申请人--代理人选择
function chooseApplicant(that){
	var html = "";
	var length = $("#applicant-table")[0].rows.length-1;
	var object = $("#applicant-table").children("tbody");
	var name = "";
	//拼接HTML
	for(var i=0;i<length;i++){
		name = object.children("tr:eq("+(i+1)+")").children("td:eq(1)").find("input").val();
		html = html + "<option value='"+name+"'>"+name+"</option>"
	}
	//获取当前是第几行
	var index = $(that).parent().siblings('.indexTd').text();
	//获取已选中的值
	var val = $("#applicantAgent-table").children("tbody").children("tr:eq("+index+")").children("td:eq(7)").find("option:selected").text();
	$(that).empty();
	$(that).html(html);
	$(that).val(val);
}
//被申请人--代理人选择
function chooseRespondent(that){
	var html = "";
	var length = $("#respondent-table")[0].rows.length-1;
	var object = $("#respondent-table").children("tbody");
	var name = "";
	for(var i=0;i<length;i++){
		name = object.children("tr:eq("+(i+1)+")").children("td:eq(2)").find("input").val();
		html = html + "<option value='"+name+"'>"+name+"</option>"
	}
	//获取当前是第几行
	var index = $(that).parent().siblings('.indexTd').text();
	//获取已选中的值
	var val = $("#respondentAgent-table").children("tbody").children("tr:eq("+index+")").children("td:eq(7)").find("option:selected").text();
	$(that).empty();
	$(that).html(html);
	$(that).val(val);
}
//第三人--代理人选择
function choosethird(that){
	var html = "";
	var length = $("#third-table")[0].rows.length-1;
	var object = $("#third-table").children("tbody");
	var name = "";
	for(var i=0;i<length;i++){
		name = object.children("tr:eq("+(i+1)+")").children("td:eq(1)").find("input").val();
		html = html + "<option value='"+name+"'>"+name+"</option>"
	}
	//$(".thirdAgentchoose").html(html);
	//获取当前是第几行
	var index = $(that).parent().siblings('.indexTd').text();
	//获取已选中的值
	var val = $("#thirdAgent-table").children("tbody").children("tr:eq("+index+")").children("td:eq(7)").find("option:selected").text();
	$(that).empty();
	$(that).html(html);
	$(that).val(val);
}



//申请人代理人radio事件
function agent(type){
	if( type ==1){
		$("#applicantAgent").show();
	}
	else{
		$("#applicantAgent").hide();
	}
}
//被申请人代理人radio事件
function respondentA(type){
	if( type ==1){
		$("#respondentAgent").show();
	}
	else{
		$("#respondentAgent").hide();
	}
}
// 第三人
function third(type){
	if( type ==1){
		$("#third").show();
		var v = $("input[name='isThirdAgent']:checked").val();
		if(v == 1){
			$("#thirdAgent").show();
		}else{
			$("#thirdAgent").hide();
		}
	}
	else {
		$("#third").hide();
		$("#thirdAgent").hide();
	}
}
// 第三人代理人
function thirdAgent(type){
	if( type ==1){
		$("#thirdAgent").show();
	}
	else {
		$("#thirdAgent").hide();
	}
}

//申请对规范性文件审查
function docCheck(type){
	if( type ==1){
		$("#documentReviewName").removeAttr("disabled");
	}
	else {
		$("#documentReviewName").attr("disabled", "disabled");
	}
}

//申请赔偿
function payCheck(type){
	if( type ==1){
		$("#compensationTypeCode").removeAttr("disabled");
		$("#compensationAmount").removeAttr("disabled");
	}
	else {
		$("#compensationTypeCode").attr("disabled", "disabled");
		$("#compensationAmount").attr("disabled", "disabled");
		
		$("#compensationTypeCode").val(""); 
		$("#compensationAmount").val(""); 
	}
}

//是否行政不作为
function nonfeasanceCheck(type){
	if( type ==1){
		$("#isNonfeasanceAtra").show();
		$("#isNonfeasanceAtrb").show();
		$("#isNonfeasanceBtr").hide();
	}
	else {
		$("#isNonfeasanceAtra").hide();
		$("#isNonfeasanceAtrb").hide();
		$("#isNonfeasanceBtr").show();
	}
}
/***********************************************************/


//申请人--添加
function addApplicantRow() {
	var length = $("#applicant-table")[0].rows.length-1;
	if(length >= 5){
		$.MsgBox.Alert_auto("申请人最多选择5人！");
		return false;
	}
    $("#applicant-table").append('<tr class="edit-table-tr">' +
        '<td class="indexTd"></td>' +
        '<td class=""><input type="text" name="applicant.name" value=""/></td>' +
        '<td class=""><select class="certificateType" name="applicant.certificateTypeCode">' + createSelectHtml(certificateTypeJson,null) + '</select></td>' +
        '<td class=""><input type="text" name="applicant.ceritificateId" value=""/></td>' +
        '<td class=""><select class="sex" name="applicant.sexCode">'+ createSelectHtml(sexJson,null) +'</select></td>' +
        '<td class=""><select class="nation" name="applicant.nationCode">'+ createSelectHtml(nationJson,null) +'</select></td>' +
        '<td class=""><input type="text" name="applicant.postAddress" value=""/></td>' +
        '<td class=""><input type="text" name="applicant.postCode" value=""/></td>' +
        '<td class=""><input type="text" name="applicant.phoneNum" value=""/></td>' +
        '<td class="funcTd"><i class="iconfont icon-shanchu" onclick="deleteApplicantRow(this);"></i></td>' +
        '</tr>');
    updateApplicantIndex();
}
//申请人--删除功能
function deleteApplicantRow(that) {
	var length = $("#applicant-table")[0].rows.length-1;
	if(length <= 1){
		$.MsgBox.Alert_auto("申请人最少选择1人！");
		return false;
	}
    $(that).parents('.edit-table-tr').remove();
    updateApplicantIndex();
}
//申请人--更新列表序号
function updateApplicantIndex() {
    $('#applicant-table').each(function() {
        var index = 0;
        var that = this;
        $(that).find(".indexTd").each(function() {
            index++;
            $(this).text(index);
        });
        //申请人和其他申请人总和
        var length = $("#otherApplicant-table")[0].rows.length-1;
        $("#totalApplicant").html((index+length));
    });
}


//其他申请人--添加
function addOtherRow() {
    $("#otherApplicant-table").append('<tr class="edit-table-tr">' +
        '<td class="indexTd"></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class=""><select class="sex">'+ createSelectHtml(sexJson,null) +'</select></td>' +
        '<td class=""><select class="certificateType">' + createSelectHtml(certificateTypeJson,null)  + '</select></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class="funcTd"><i class="iconfont icon-shanchu" onclick="deleteOtherRow(this);"></i></td>' +
        '</tr>');
    updateOtherIndex();
}
//其他申请人--删除功能
function deleteOtherRow(that) {
    $(that).parents('.edit-table-tr').remove();
    updateOtherIndex();
}
//其他申请人--更新列表序号
function updateOtherIndex() {
    $('#otherApplicant-table').each(function() {
        var index = 0;
        var that = this;
        $(that).find(".indexTd").each(function() {
            index++;
            $(this).text(index);
        });
        
        //申请人和其他申请人总和
        var length = $("#applicant-table")[0].rows.length-1;
        $("#totalApplicant").html((index+length));
    });
}


//申请人代理人--添加
function addApplicantAgentRow() {

    $("#applicantAgent-table").append('<tr class="edit-table-tr">' +
        '<td class="indexTd"></td>' +
        '<td class=""><select class="agent">' + createSelectHtml(agentJson,null) + '</select></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class=""><select class="certificateType">' + createSelectHtml(certificateTypeJson,null) + '</select></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class=""><select class=""><option value="1">是</option>' +
	        '<option value="0">否</option></select></td>' +
	    '<td class="">' +
	        '<select class="applicantchoose" onclick="chooseApplicant(this);"></select></td>' +
        '<td class="funcTd"><i class="iconfont icon-shanchu" onclick="deleteApplicantAgentRow(this);"></i></td>' +
        '</tr>');
    updateApplicantAgentIndex();
    
}
//申请人代理人--删除功能
function deleteApplicantAgentRow(that) {
    $(that).parents('.edit-table-tr').remove();
    updateApplicantAgentIndex();
}
//申请人代理人--更新列表序号
function updateApplicantAgentIndex() {
    $('#applicantAgent-table').each(function() {
        var index = 0;
        var that = this;
        $(that).find(".indexTd").each(function() {
            index++;
            $(this).text(index);
        });
    });
}



//被申请人--添加
function addRespondentRow() {
	var length = $("#respondent-table")[0].rows.length-1;
	if(length >= 1){
		$.MsgBox.Alert_auto("申请人最多选择1人！");
		return false;
	}
    $("#respondent-table").append('<tr class="edit-table-tr">' +
	    '<td class="indexTd"></td>' +
	    '<td class=""><select class="respondentType">' + createSelectHtml(respondentTypeJson,null) + '</select></td>' +
	    '<td class=""><input type="text" value=""/></td>' +
	    '<td class="funcTd"><i class="iconfont icon-shanchu" onclick="deleteRespondentRow(this);"></i></td>' +
	    '</tr>');
	updateRespondentIndex();
}
//被申请人代理人--删除功能
function deleteRespondentRow(that) {
	var length = $("#respondent-table")[0].rows.length-1;
	if(length <= 1){
		$.MsgBox.Alert_auto("申请人最少选择1人！");
		return false;
	}
	$(that).parents('.edit-table-tr').remove();
	updateRespondentIndex();
}
//被申请人代理人--更新列表序号
function updateRespondentIndex() {
	$('#respondent-table').each(function() {
	    var index = 0;
	    var that = this;
	    $(that).find(".indexTd").each(function() {
	        index++;
	        $(this).text(index);
	    });
	});
}


//被申请人代理人--添加
function addRespondentAgentRow() {
    $("#respondentAgent-table").append('<tr class="edit-table-tr">' +
	    '<td class="indexTd"></td>' +
	    '<td class=""><select class="agent">' + createSelectHtml(agentJson,null) + '</select></td>' +
	    '<td class=""><input type="text" value=""/></td>' +
	    '<td class=""><select class="certificateType">' + createSelectHtml(certificateTypeJson,null) + '</select></td>' +
	    '<td class=""><input type="text" value=""/></td>' +
	    '<td class=""><input type="text" value=""/></td>' +
	    '<td class=""><select class=""><option value="1">是</option>' +
		      '<option value="0">否</option></select></td>' +
		  '<td class="">' +
		      '<select class="respondentchoose" onclick="chooseRespondent(this);"><option value="">--请选择--</option></select></td>' +
	    '<td class="funcTd"><i class="iconfont icon-shanchu" onclick="deleteRespondentAgentRow(this);"></i></td>' +
	    '</tr>');
	updateRespondentAgentIndex();
}
//被申请人代理人--删除功能
function deleteRespondentAgentRow(that) {
	$(that).parents('.edit-table-tr').remove();
	updateRespondentAgentIndex();
}
//被申请人代理人--更新列表序号
function updateRespondentAgentIndex() {
	$('#respondentAgent-table').each(function() {
	    var index = 0;
	    var that = this;
	    $(that).find(".indexTd").each(function() {
	        index++;
	        $(this).text(index);
	    });
	});
}


//第三人--添加
function addThirdRow() {
	$("#third-table").append('<tr class="edit-table-tr">' +
        '<td class="indexTd"></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class=""><select class="certificateType">' + createSelectHtml(certificateTypeJson,null) + '</select></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class=""><select class="sex">'+ createSelectHtml(sexJson,null) +'</select></td>' +
        '<td class=""><select class="nation">'+ createSelectHtml(nationJson,null) +'</select></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class=""><input type="text" value=""/></td>' +
        '<td class="funcTd"><i class="iconfont icon-shanchu" onclick="deleteThirdRow(this);"></i></td>' +
        '</tr>');
	updateThirdIndex();
}
//第三人--删除功能
function deleteThirdRow(that) {
	$(that).parents('.edit-table-tr').remove();
	updateThirdIndex();
}
//第三人--更新列表序号
function updateThirdIndex() {
	$('#third-table').each(function() {
	    var index = 0;
	    var that = this;
	    $(that).find(".indexTd").each(function() {
	        index++;
	        $(this).text(index);
	    });
	});
}


//第三人代理人--添加
function addThirdAgentRow() {
	$("#thirdAgent-table").append('<tr class="edit-table-tr">' +
	    '<td class="indexTd"></td>' +
	    '<td class=""><select class="agent">' + createSelectHtml(agentJson,null) + '</select></td>' +
	    '<td class=""><input type="text" value=""/></td>' +
	    '<td class=""><select class="certificateType">' + createSelectHtml(certificateTypeJson,null) + '</select></td>' +
	    '<td class=""><input type="text" value=""/></td>' +
	    '<td class=""><input type="text" value=""/></td>' +
	    '<td class=""><select class=""><option value="1">是</option>' +
		      '<option value="0">否</option></select></td>' +
		  '<td class="">' +
		      '<select class="thirdAgentchoose" onclick="choosethird(this);"><option value="">--请选择--</option></select></td>' +
	    '<td class="funcTd"><i class="iconfont icon-shanchu" onclick="deleteThirdAgentRow(this);"></i></td>' +
	    '</tr>');
	updateThirdAgentIndex();
	
}
//第三人代理人--删除功能
function deleteThirdAgentRow(that) {
	$(that).parents('.edit-table-tr').remove();
	updateThirdAgentIndex();
}
//第三人代理人--更新列表序号
function updateThirdAgentIndex() {
	$('#thirdAgent-table').each(function() {
	    var index = 0;
	    var that = this;
	    $(that).find(".indexTd").each(function() {
	        index++;
	        $(this).text(index);
	    });
	    
	});
}

/******************************************************/

//防止重复提交
var isSummit = true;
function submit(){
	//防止重复提交
	if(!isSummit){
		return false;
	}
	isSummit = false;
	//申请人
	applicantSubmit();
	//被申请人
	respondentSubmit();
	//第三人
	thirdSubmit();
	//复议事项
	reviewSubmit();
	
//	if (caseStatus == "03"){
//		//受理
//		acceptSubmit();
//	}
//	else if(caseStatus == "04" || caseStatus == "05" ||caseStatus == "06"){
//		//审理
//		trialSubmit();
//	}
	
	//设置为true
	isSummit = true;
	
	$.MsgBox.Alert_auto("保存成功!");
}


//提交修改(申请人)
function applicantSubmit(){
	
	//申请人列表
	var applicantList = createApplicaent("applicant");
	//其他申请人列表
	var otherApplicantList = createOtherApplicant();
	//代理人列表
	var agentList = createAgent("applicantAgent");
	var param = {
		"operationType":"01",
		"caseId":caseId,
		"applicantList":applicantList,
		"otherApplicantList":otherApplicantList,
		"agentList":agentList
	};
	//同步请求
	$.ajax({
		type : "POST",
	    dataType : "json",
		contentType : 'application/json;charset=utf-8',
		url : CLIENT_APPLiCAENT_URL,
		data : JSON.stringify(param),
		async: false,
		success : function(res) {},
		error : function() {}
	});
}


//被申请人提交
function respondentSubmit(){
	
	//被申请人列表
	var respondentList = respondent();
	//被申请人代理列表
	var respondentAgentList = createAgent("respondentAgent");
	
	var param = {
		"operationType":"01",
		"caseId":caseId,
		"respondentList":respondentList,
		"agentList":respondentAgentList
	};
	//同步请求
	$.ajax({
		type : "POST",
	    dataType : "json",
		contentType : 'application/json;charset=utf-8',
		url : CLIENT_RESPONDENT_URL,
		data : JSON.stringify(param),
		async: false,
		success : function(res) {},
		error : function() {}
	});
}


//第三人提交
function thirdSubmit(){
	
	//第三人列表
	var thirdList = createThird("third");
	//第三人代理列表
	var thirdAgentList = createAgent("thirdAgent");
	
	var param = {
		"operationType":"01",
		"caseId":caseId,
		"thirdPartyList":thirdList,
		"agentList":thirdAgentList
	};
	//同步请求
	$.ajax({
		type : "POST",
	    dataType : "json",
		contentType : 'application/json;charset=utf-8',
		url : CLIENT_THIRD_URL,
		data : JSON.stringify(param),
		async: false,
		success : function(res) {},
		error : function() {}
	});
}

//复议事项
function reviewSubmit(){
	//封装复议事项
	var caseHandling = createReview();
	var param = {
		"caseHandling":caseHandling
	};
	//同步请求
	$.ajax({
		type : "POST",
	    dataType : "json",
		contentType : 'application/json;charset=utf-8',
		url : REVIEW_URL,
		data : JSON.stringify(param),
		async: false,
		success : function(res) {},
		error : function() {}
	});
}

//受理提交
function acceptSubmit(){
	
}

//审理提交
function trialSubmit(){
	
}


/*****************************************************/

//封装申请人,第三人信息
function createApplicaent(name){
	var arry = new Array()
	var length = $("#"+name+"-table")[0].rows.length-1;
	var object = $("#"+name+"-table").children("tbody");
	var json = {};
	for(var i=0;i<length;i++){
		json["id"] = object.children("tr:eq("+(i+1)+")").children("td:eq(0)").attr("data-value");
		json["caseId"]=caseId;
		json["name"] = object.children("tr:eq("+(i+1)+")").children("td:eq(1)").find("input").val();
		json["certificateTypeCode"] = object.children("tr:eq("+(i+1)+")").children("td:eq(2)").find("select").val();
		json["certificateType"] = object.children("tr:eq("+(i+1)+")").children("td:eq(2)").find("option:selected").text();
		
		json["certificateId"] = object.children("tr:eq("+(i+1)+")").children("td:eq(3)").find("input").val();
		
		json["sexCode"] = object.children("tr:eq("+(i+1)+")").children("td:eq(4)").find("select").val();
		json["sex"] = object.children("tr:eq("+(i+1)+")").children("td:eq(4)").find("option:selected").text();
		
		json["nationCode"] = object.children("tr:eq("+(i+1)+")").children("td:eq(5)").find("select").val();
		json["nation"] = object.children("tr:eq("+(i+1)+")").children("td:eq(5)").find("option:selected").text();
		
		json["postAddress"] = object.children("tr:eq("+(i+1)+")").children("td:eq(6)").find("input").val();
		json["postCode"] = object.children("tr:eq("+(i+1)+")").children("td:eq(7)").find("input").val();
		json["phoneNum"] = object.children("tr:eq("+(i+1)+")").children("td:eq(8)").find("input").val();
		//json["applicantTypeCode"] = $("#applicantType option:selected").val();
		//json["applicantType"] = $("#applicantType option:selected").text();
		arry.push(json);
	}
	return arry;
}


//第三人信息
function createThird(name){
	var arry = new Array()
	var length = $("#"+name+"-table")[0].rows.length-1;
	var object = $("#"+name+"-table").children("tbody");
	var json = {};
	for(var i=0;i<length;i++){
		json["id"] = object.children("tr:eq("+(i+1)+")").children("td:eq(0)").attr("data-value");
		json["caseId"]=caseId;
		json["thirdPartyName"] = object.children("tr:eq("+(i+1)+")").children("td:eq(1)").find("input").val();
		json["certificateTypeCode"] = object.children("tr:eq("+(i+1)+")").children("td:eq(2)").find("select").val();
		json["certificateType"] = object.children("tr:eq("+(i+1)+")").children("td:eq(2)").find("option:selected").text();
		
		json["certificateId"] = object.children("tr:eq("+(i+1)+")").children("td:eq(3)").find("input").val();
		
		json["sexCode"] = object.children("tr:eq("+(i+1)+")").children("td:eq(4)").find("select").val();
		json["sex"] = object.children("tr:eq("+(i+1)+")").children("td:eq(4)").find("option:selected").text();
		
		json["nationCode"] = object.children("tr:eq("+(i+1)+")").children("td:eq(5)").find("select").val();
		json["nation"] = object.children("tr:eq("+(i+1)+")").children("td:eq(5)").find("option:selected").text();
		
		json["postAddress"] = object.children("tr:eq("+(i+1)+")").children("td:eq(6)").find("input").val();
		json["postcode"] = object.children("tr:eq("+(i+1)+")").children("td:eq(7)").find("input").val();
		json["phoneNum"] = object.children("tr:eq("+(i+1)+")").children("td:eq(8)").find("input").val();
		//json["applicantTypeCode"] = $("#applicantType option:selected").val();
		//json["applicantType"] = $("#applicantType option:selected").text();
		arry.push(json);
	}
	return arry;
}
//封装其他申请人信息
function createOtherApplicant(){
	var arry = new Array()
	var length = $("#otherApplicant-table")[0].rows.length-1;
	var object = $("#otherApplicant-table").children("tbody");
	var json = {};
	for(var i=0;i<length;i++){
		json["caseId"] = caseId;
		json["id"] = object.children("tr:eq("+(i+1)+")").children("td:eq(0)").attr("data-value");
		json["name"] = object.children("tr:eq("+(i+1)+")").children("td:eq(1)").find("input").val();
		json["sexCode"] = object.children("tr:eq("+(i+1)+")").children("td:eq(2)").find("select").val();
		json["sex"] = object.children("tr:eq("+(i+1)+")").children("td:eq(2)").find("option:selected").text();
		
		json["certificateTypeCode"] = object.children("tr:eq("+(i+1)+")").children("td:eq(3)").find("select").val();
		json["certificateType"] = object.children("tr:eq("+(i+1)+")").children("td:eq(3)").find("option:selected").text();
		
		json["certificateId"] = object.children("tr:eq("+(i+1)+")").children("td:eq(4)").find("input").val();
		arry.push(json);
	}
	return arry;
}
//封装被申请人
function respondent(){
	var arry = new Array();
	var length = $("#respondent-table")[0].rows.length-1;
	var object = $("#respondent-table").children("tbody");
	var json = {};
	for(var i=0;i<length;i++){
		json["caseId"] = caseId;
		json["id"] = object.children("tr:eq("+(i+1)+")").children("td:eq(0)").attr("data-value");
		json["respondentTypeCode"] = object.children("tr:eq("+(i+1)+")").children("td:eq(1)").find("option:selected").val();
		json["respondentType"] = object.children("tr:eq("+(i+1)+")").children("td:eq(1)").find("option:selected").text();
		json["respondentName"] = object.children("tr:eq("+(i+1)+")").children("td:eq(2)").find("input").val();
		arry.push(json);
	}
	return arry;
}
//封装申请人代理人,被申请人代理人，第三人代理人信息
function createAgent(name){
	var arry = new Array()
	var length = $("#"+name+"-table")[0].rows.length-1;
	var object = $("#"+name+"-table").children("tbody");
	var json = {};
	for(var i=0;i<length;i++){
		json["caseId"] = caseId;
		json["id"] = object.children("tr:eq("+(i+1)+")").children("td:eq(0)").attr("data-value");
		json["agentTypeCode"] = object.children("tr:eq("+(i+1)+")").children("td:eq(1)").find("select").val();
		json["agentType"] = object.children("tr:eq("+(i+1)+")").children("td:eq(1)").find("option:selected").text();
		
		json["agentName"] = object.children("tr:eq("+(i+1)+")").children("td:eq(2)").find("input").val();
		json["cardTypeCode"] = object.children("tr:eq("+(i+1)+")").children("td:eq(3)").find("select").val();
		json["cardType"] = object.children("tr:eq("+(i+1)+")").children("td:eq(3)").find("option:selected").text();
		
		json["idCard"] = object.children("tr:eq("+(i+1)+")").children("td:eq(4)").find("input").val();
		json["phone"] = object.children("tr:eq("+(i+1)+")").children("td:eq(5)").find("input").val();
		json["isAuthorization"] = object.children("tr:eq("+(i+1)+")").children("td:eq(6)").find("select").val();
		json["agentParent"] = object.children("tr:eq("+(i+1)+")").children("td:eq(7)").find("select").val();
		arry.push(json);
	}
	return arry;
}

//封装复议事项
function createReview(){
	var json = {};
	json["operationType"]="01";
	json["caseId"] = caseId; 
	json["applicationDate"] = $("#applicationDate").val(); 
	json["categoryCode"] = $("#category").val(); 
	json["category"] = $("#category").find("option:selected").text();
	
	json["applicationItemCode"] = $("#applicationItem").val(); 
	json["applicationItem"] = $("#applicationItem").find("option:selected").text();
	
	//行政不作为
	var isNonfeasance = $('input:radio[name=isNonfeasance]:checked').val();
	json["isNonfeasance"] = isNonfeasance;
	if( isNonfeasance ==1){
		json["specificAdministrativeName"] = $("#specificAdministrativeName").val(); 
		json["specificAdministrativeNo"] = $("#specificAdministrativeNo").val(); 
		json["specificAdministrativeDate"] = $("#specificAdministrativeDate").val(); 
		json["receivedPunishDate"] = $("#receivedPunishDate").val(); 
		json["receivedSpecificWay"] = $("#receivedSpecificWay").val(); 
	}
	else{
		json["nonfeasanceItemCode"] = $("#nonfeasanceItem").val(); 
		json["nonfeasanceItem"] = $("#nonfeasanceItem").find("option:selected").text();
		json["nonfeasanceDate"] = $("#nonfeasanceDate").val(); 
	}
	
	json["specificAdministrativeDetail"] = $("#specificAdministrativeDetail").val(); 
	json["requestForReconsideration"] = $("#requestForReconsideration").val(); 
	
	json["isReview"] = $('input:radio[name=isReview]:checked').val();
	json["isHoldHearing"] = $('input:radio[name=isHoldHearing]:checked').val();
	json["isDocumentReview"] = $('input:radio[name=isDocumentReview]:checked').val();
	json["documentReviewName"] = $("#documentReviewName").val(); 
	
	json["isCompensation"] = $('input:radio[name=isCompensation]:checked').val();
	json["compensationTypeCode"] = $("#compensationTypeCode").val(); 
	json["compensationType"] = $("#compensationTypeCode").find("option:selected").text();
	json["compensationAmount"] = $("#compensationAmount").val(); 
	return json;
}

//封装案件受理


//封装案件审理

/*******************************************************************/

