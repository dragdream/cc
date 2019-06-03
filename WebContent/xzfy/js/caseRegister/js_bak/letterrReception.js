// 来件信息登记/修改
var LETTER_URL = "/xzfy/caseManager/addOrUpdateFyLetter.action";

// 接待信息登记/修改
var RECEPTION_URL = "/xzfy/caseManager/addOrUpdateFyReception.action";

// 根据caseId查询来件信息
var GET_LETTER_URL = "/xzfy/caseManager/getFyLetter.action";

// 根据caseId查询接待信息
var GET_RECEPTION_URL = "/xzfy/caseManager/getFyReception.action";

// 案件提取
var GET_CASE_EXTRACT_URL = "/xzfy/caseExtract/getCaseExtract.action";

// var caseId = $('#caseId').val();
var caseId = "10be0f867b10407e90009295889ac860";

// 加载初始化数据
function receptionInit() {
	searchReception();
	/*
	 * alert($('#postType').val()); var postType = $('#postType').val(); if
	 * (postType == '1') { searchLetter(caseId); } else if (postType == '2') {
	 * searchReception(caseId); }
	 */
}

// 查询来件信息
function searchLetter() {
	$.ajax({
		type : 'GET',
		dataType : "json",
		url : GET_LETTER_URL,
		data : {
			caseId : caseId
		},
		success : function(res) {
			alert("加载数据");
			if (res.code == "0") {
				$("#edit_cust_id").val(data.cust_id);
				$("#cust_name").val(data.cust_name);
				$("#edit_customerFrom").val(data.cust_source);
				$("#edit_custIndustry").val(data.cust_industry);
				$("#edit_custLevel").val(data.cust_level);
				$("#edit_linkMan").val(data.cust_linkman);
				$("#edit_phone").val(data.cust_phone);
				$("#edit_mobile").val(data.cust_mobile);
				$("#edit_zipcode").val(data.cust_zipcode);
				$("#edit_address").val(data.cust_address);
			} else {

			}
		},
		error : function() {
			// $("#test").html("发生错误:"+jqXHR.status);
			alert("发生错误");
		}
	});

}

// 查询接待信息
function searchReception() {
	$.ajax({
		type : 'GET',
		dataType : "json",
		url : GET_RECEPTION_URL,
		data : {
			caseId : caseId
		},
		success : function(res) {
			var json = res.rtData.fyReceptionVo;
			var visitor = JSON.parse(json.visitor);
			recp_listdata = {
				isShowColumn : visitor
			}
			$("#recp-table").append(juicer(recp_tpl, recp_listdata));
			bindJsonObj2Cntrl(json);
		},
		error : function() {
			alert("发生错误");
		}
	});

}

// 来件信息修改/保存
function saveLetter() {
	var caseId = $('#caseId').val(); // 案件ID
	var operationType = $('#operationType').val(); // 操作类型 "01"为登记/"02"为填报
	var applicationTypeCode = $('#applicationTypeCode').val(); // 复议申请方式代码
	var applicationType = $('#applicationType').val(); // 复议申请方式
	var letterTypeCode = $('#letterTypeCode').val(); // 收件类型代码
	var receiveDate = $('#receiveDate').val(); // 接收日期
	var senderName = $('#senderName').val(); // 来信人姓名
	var senderPhone = $('#senderPhone').val(); // 来信人电话
	var senderPostCode = $('#senderPostCode').val(); // 来信人邮编
	var senderAddress = $('#senderAddress').val(); // 来信人通信地址
	$.ajax({
		type : 'POST',
		dataType : "json",
		url : LETTER_URL,
		data : {
			reception : {
				caseId : caseId,
				operationType : operationType,
				applicationTypeCode : applicationTypeCode,
				applicationType : applicationType,
				letterTypeCode : letterTypeCode,
				receiveDate : receiveDate,
				senderName : senderName,
				senderPhone : senderPhone,
				senderPostCode : senderPostCode,
				senderAddress : senderAddress
			}
		},
		success : function(res) {
			alert("加载数据");
			var data = res.rtData.fyReceptionVo;
			bindJsonObj2Cntrl(data);
		},
		error : function() {
			// $("#test").html("发生错误:"+jqXHR.status);
			alert("发生错误");
		}
	});
}

// 接待信息修改/保存
function saveReception() {
	var caseId = $('#caseId').val(); // 案件Id
	var operationType = $('#operationType').val(); // 操作类型 "01"为登记/"02"为填报
	var applicationTypeCode = $('#applicationTypeCode').val(); // 复议申请方式代码
	var applicationType = $('#applicationType').val(); // 复议申请方式
	var receptionDate = $('#receptionDate').val(); // 接待日期
	var place = $('#place').val(); // 接待地点
	var dealMan1Id = $('#dealMan1Id').val(); // 第一接待人
	var dealMan2Id = $('#dealMan2Id').val(); // 第二接待人
	var recertionTypeCode = $('#recertionTypeCode').val(); // 复议请求/接待类型代码（01 问卷
	// 02 法律咨询 03
	// 反应问题 04
	// 补充材料 05 催办案件 06 提交申请 99 其他）
	var dealResultCode = $('#dealResultCode').val(); // 处理结果代码

	var visitor; // 被接待人基本信息
	var visitorName = $('#visitorName').val(); // 被接待人姓名
	var cardType = $('#cardType').val(); // 证件类型
	var cardNum = $('#cardNum').val(); // 证件号
	var sexCode = $('#sexCode').val(); // 性别代码
	var postAddress = $('#postAddress').val(); // 通讯地址
	var postCode = $('#postCode').val(); // 邮编
	var phoneNum = $('#phoneNum').val(); // 电话

	var receptionDetail = $('#receptionDetail').val(); // 接待情况描述
	var isReceivingMaterial = $('#isReceivingMaterial').val(); // 是否接收材料Receiving
	// material

	var listFyMaterial; // 案件资料信息
	var fileName = $('#fileName').val(); // 文件名称
	var fileTypeCode = $('#fileTypeCode').val(); // 文件类型代码
	var fileType = $('#fileType').val(); // 文件类型VALUE
	var pageNum = $('#pageNum').val(); // 页数
	var copyNum = $('#copyNum').val(); // 份数

	var isAcceptTogether = $('#isAcceptTogether').val(); // 其他复议机关、法院是否受理同一复议申请
	var reconsiderOrganName = $('#reconsiderOrganName').val(); // 复议机构名称

	$.ajax({
		type : 'POST',
		dataType : "json",
		url : "/xzfy/caseManager/addOrUpdateFyReception.action",
		data : {
			reception : {
				caseId : caseId,
				operationType : operationType,
				applicationTypeCode : applicationTypeCode,
				applicationType : applicationType,
				receptionDate : receptionDate,
				place : place,
				dealMan1Id : dealMan1Id,
				dealMan2Id : dealMan2Id,
				recertionTypeCode : recertionTypeCode,
				dealResultCode : dealResultCode,
				visitor : [ {
					visitorName : visitorName,
					cardType : cardType,
					cardNum : cardNum,
					sexCode : sexCode,
					postAddress : postAddress,
					postCode : postCode,
					phoneNum : phoneNum
				} ],
				receptionDetail : receptionDetail,
				isReceivingMaterial : isReceivingMaterial,
				listFyMaterial : {
					fileName : fileName,
					fileTypeCode : fileTypeCode,
					fileType : fileType,
					pageNum : pageNum,
					copyNum : copyNum
				},
				isAcceptTogether : isAcceptTogether,
				reconsiderOrganName : reconsiderOrganName
			}
		},
		success : function(res) {
			var data = res.rtData;
			bindJsonObj2Cntrl(data);
			alert("数据添加成功");
		},
		error : function() {
			// $("#test").html("发生错误:"+jqXHR.status);
			alert("发生错误");
		}
	});
}

// 案件提取
function getCaseExtract() {
	$.ajax({
		type : 'GET',
		dataType : "json",
		url : GET_CASE_EXTRACT_URL,
		data : {
			caseId : caseId
		},
		success : function(res) {
			console.log(res);
			var data = res.rtData;
			bindJsonObj2Cntrl(data);
		},
		error : function() {
			// $("#test").html("发生错误:"+jqXHR.status);
			alert("发生错误");
		}
	});
}
