/*----------------------------------导航菜单开始-----------------------------------------*/
// 显示页签的标识符
var pageFlag = 0;

function doInit() {
	renderingTable();
	showpage(pageFlag);
	route();
}

function renderingTable() {
	$("#recp-table").append(juicer(recp_tpl, null));
	$("#material-table").append(juicer(material_tpl1, null));
}


function route() {
	switch (pageFlag) {
	case 0:
		receptionInit();
		break;
	case 1:
		clientInit();
		break;
	case 2:
		reviewMattersInit();
		break;
	case 3:

		break;

	default:
		break;
	}
}

function operation() {
	switch (pageFlag) {
	case 1:
		applyType = $('#applyType option:selected').val();// 选中的值
		if (applyType == 1) {
			// 来件信息登记
			saveLetter();
		} else {
			// 接待信息登记
			saveReception();
		}
		break;
	case 2:
		// 当事人信息登记/修改
		saveOrUpdataClientInfo();
		break;
	case 3:
		// 复议事项登记/修改
		saveOrUpdataReviewMatters();
		break;

	default:
		break;
	}
}

$(document).ready(function() {
	$('.rp-btn-left').hide();
	$('#copy').hide();
});

// 下一步
function back() {
	$('#forward').attr("disabled", false);
	$('#forward').css("color", '#fff');
	pageFlag--;
	if (pageFlag <= 0) {
		$('#back').attr("disabled", true);
		$("#back").css('color', '#A9A9A9');
		showpage(0);
	} else {
		$('#back').attr("disabled", false);
		$("#forward").css('color', '#3379b7');
		showpage(pageFlag);
	}
	route();
}

function forward() {
	$('#back').attr("disabled", false);
	$('#back').css("color", '#fff');
	pageFlag++;
	if (pageFlag >= 3) {
		$('#forward').attr("disabled", true);
		$("#forward").css('color', '#A9A9A9');
		showpage(3);
	} else {
		$('#forward').attr("disabled", false);
		$("#forward").css('color', '#3379b7');
		showpage(pageFlag);
	}
	operation();
	route();
}

function save() {

}

function showpage(index) {
	pageFlag = index;
	if (pageFlag == 0) {
		$('#back').attr("disabled", true);
		$('#forward').attr("disabled", false);
		$("#back").css('color', '#A9A9A9');
		$('#forward').css("color", '#fff');
	} else if (pageFlag == 3) {
		$('#back').attr("disabled", false);
		$('#forward').attr("disabled", true);
		$('#back').css("color", '#fff');
		$("#forward").css('color', '#A9A9A9');
		$('#copy').show();
	} else {
		$('#back').attr("disabled", false);
		$('#forward').attr("disabled", false);
		$('#back').css("color", '#fff');
		$('#forward').css("color", '#fff');
		$('#copy').hide();
	}
	if (pageFlag == 0 || pageFlag == 1) {
		$('.rp-btn-left').hide();
	}
	if (pageFlag == 2 || pageFlag == 3) {
		$('.rp-btn-left').show();
	}
	$(".content").hide();
	$(".content").eq(pageFlag).show();
}
/*-------------------------------------导航菜单结束--------------------------------------------*/

/* ---------------------------------------------------------------------------------- */
// 数据部分
var recp_listdata;

var material_listdata;

// 模板引擎
/*-------------------------------被接待人信息-----------------------------*/
var recp_tpl = [
		'<tr class="">',
		'<th class="">序号</th>',
		'<th class="">被接待人姓名</th>',
		'<th class="">证件类型</th>',
		'<th class="">证件号</th>',
		'<th class="">性别</th>',
		'<th class="">常住地址</th>',
		'<th class="">邮编</th>',
		'<th class="">联系电话</th>',
		'<th class="">&nbsp</th>',
		'</tr>',
		'{@each isShowColumn as it, index}',
		'<tr class="">',
		'<td class="indexTd"></td>',
		'<td class=""><input id="visitorName" type="text" value="${it.visitorName}"/></td>',
		'<td class=""><select id="cardTypeCode" name="cardTypeCode" value="${it.cardTypeCode}" class=""></select></td>',
		'<td class=""><input id="cardNum" type="text" value="${it.cardNum}"/></td>',
		'<td class=""><select id="sexCode" class=""><option value="${it.sexCode}">男</option><option value="${it.sexCode}">女</option></select></td>',
		'<td class=""><input id="postAddress" type="text" value="${it.postAddress}"/></td>',
		'<td class=""><input id="postCode" type="text" value="${it.postCode}"/></td>',
		'<td class=""><input id="phoneNum" type="text" value="${it.phoneNum}"/></td>',
		'<td class="funcTd"><i id="deleteUL" class="iconfont icon-shanchu" onclick="deleteRow(this);"></i></td>',
		'</tr>','{@/each}'].join('\n');

// 列表添加功能 /* 被接待人信息 */
function recp_insertRow() {
	$("#recp-table")
			.append(
					'<tr class="">'
							+ '<td class="indexTd"></td>'
							+ '<td class=""><input id="visitorName" name="visitorName" type="text"/></td>'
							+ '<td class=""><select id="cardTypeCode" name="cardTypeCode" class="cardTypeCode">'+ createSelectHtml(cardTypeCodeJson, null)+'</select></td>'
							+ '<td class=""><input id="cardNum" type="text"/></td>'
							+ '<td class=""><select id="sexCode" name="sexCode" class="sexCode">'+ createSelectHtml(sexCodeJson, null)+'</select></td>'
							+ '<td class=""><input id="postAddress" name="postAddress" class="postAddress"/></td>'
							+ '<td class=""><input id="postCode" name="postCode" class="postCode" type="text"/></td>'
							+ '<td class=""><input id="phoneNum" name="phoneNum" class="phoneNum" type="text"/></td>'
							+ '<td class="funcTd"><i id="deleteUL" class="iconfont icon-shanchu" onclick="deleteRow(this);"></i></td>'
							+ '</tr>');
	updateIndex();
}

/*----------------------------------接收材料----------------------------------*/
var material_tpl1 = [
		'<tr class="">',
		'<th class="">序号</th>',
		'<th class="">材料名称</th>',
		'<th class="">材料类型</th>',
		'<th class="">份数</th>',
		'<th class="">张数</th>',
		'<th class="">&nbsp</th>',
		'</tr>',
		'{@each isShowColumn as it, index}',
		'<tr class="">',
		'<td class="indexTd"></td>',
		'<td class=""><input id="fileName" name="fileName" type="text" value="${it.fileName}"/></td>',
		'<td class=""><select id="fileTypeCode" name="fileTypeCode" value="${it.fileTypeCode}" class=""></select></td>',
		'<td class=""><input id="copyNum" name="copyNum" type="text" value="${it.copyNum}"/></td>',
		'<td class=""><input id="pageNum" name="pageNum" type="text" value="${it.pageNum}"/></td>',
		'<td class="funcTd"><i id="deleteUL" class="iconfont icon-shanchu" onclick="deleteRow(this);"></i></td>',
		'</tr>', '{@/each}' ].join('\n');

function material_insertRow() {
	$("#material-table")
			.append(
					'<tr class="">'
							+ '<td class="indexTd"></td>'
							+ '<td class=""><input id="fileName" name="fileName" type="text" value=""/></td>'
							+ '<td class=""><select id="fileTypeCode" name="fileTypeCode" class="">'+ createSelectHtml(fileTypeCodeJson, null)+'</select></td>'
							+ '<td class=""><input id="copyNum" name="copyNum" type="text" value=""/></td>'
							+ '<td class=""><input id="pageNum" name="pageNum" type="text" value=""/></td>'
							+ '<td class="funcTd"><i id="deleteUL" class="iconfont icon-shanchu" onclick="deleteRow(this);"></i></td>',
							+ '</tr>');
	updateIndex();
}

// 初始化
$(document).ready(function() {
	updateIndex();
	$('.material-div').hide();
});

// 公共列表删除功能
function deleteRow(that) {
	$(that).parents('tr').remove();
	updateIndex();
}

// 公共更新列表序号
function updateIndex() {
	var index = 0;
	$(".indexTd").each(function() {
		index++;
		$(this).text(index);
	});
}

// 按钮显示隐藏表格
function showTable() {
	$('.material-div').show();
}

function hideTable() {
	$('.material-div').hide();
}

// 申请方式切换
function selectPostType() {
	document.getElementById("sel")[2].selected = true;
}

/* --------------------------------当事人信息页------------------------------------------------------ */
// 数据部分
/* 申请人数据 */
var applicant_listdata;
/* 其他申请人数据 */
var other_applicant_listdata;
/* 申请人代理人 */
var applicant_agent_listdata;
/* 被申请人 */
var respondent_listdata;
/* 被申请人代理人 */
var respondent_agent_listdata;
/* 第三人 */
var thirdParty_listdata;
/* 第三人代理人 */
var thirdParty_agent_listdata;

// 模板引擎部分
// 申请人表格
var applicant_tpl = [
		'<tr class="">',
		'<th class="">序号</th>',
		'<th style="display:none" id="id" class="">申请人Id</th>',
		'<th class="">申请人姓名</th>',
		'<th class="">证件类型</th>',
		'<th class="">证件号</th>',
		'<th class="">性别</th>',
		'<th class="">常住地址</th>',
		'<th class="">邮编</th>',
		'<th class="">联系电话</th>',
		'<th class="">&nbsp</th>',
		'</tr>',
		'{@each isShowColumn as it, index}',
		'<tr class="">',
		'<td class="indexTd"></td>',
		'<td style="display:none" id="td_id" class=""><input id="id" type="text" value="${it.id}"/></td>',
		'<td class=""><input id="name" type="text" value="${it.name}"/></td>',
		'<td class=""><select id="certificateTypeCode" class="certificateTypeCode" name="certificateTypeCode" value="${it.certificateTypeCode}"></select></td>',
		'<td class=""><input id="certificateId" type="text" value="${it.certificateId}"/></td>',
		'<td class=""><select id="sexCode" class="sexCode" name="sexCode" value="${it.sexCode}"></select></td>',
		'<td class=""><input id="postAddress" type="text" value="${it.postAddress}"/></td>',
		'<td class=""><input id="postCode" type="text" value="${it.postCode}"/></td>',
		'<td class=""><input id="phoneNum" type="text" value="${it.phoneNum}"/></td>',
		'<td class="funcTd"><i id="deleteUL" class="iconfont icon-shanchu" onclick="deleteRow(this);"></i></td>',
		'</tr>', '{@/each}' ].join('\n');

function operationRow() {
	$("#id").hide();
	$("#td_id").hide();
}

// 申请人列表添加功能
function applicant_insertRow() {
	$("#applicant-table")
			.append(
					'<tr class="">'
							+ '<td class="indexTd"></td>'
							+ '<td style="display:none" id="td_id" class=""><input id="id" type="text" /></td>'
							+ '<td class=""><input id="name" type="text"/></td>'
							+ '<td class=""><select id="certificateTypeCode" class="certificateTypeCode" name="certificateTypeCode">'+ createSelectHtml(certificateTypeJson, null) +'</select></td>'
							+ '<td class=""><input id="certificateId" type="text"/></td>'
							+ '<td class=""><select id="sexCode" class="sexCode" name="sexCode">'+ createSelectHtml(sexJson, null)+'</select></td>'
							+ '<td class=""><input id="postAddress" type="text"/></td>'
							+ '<td class=""><input id="postCode" type="text"/></td>'
							+ '<td class=""><input id="phoneNum" type="text"/></td>'
							+ '<td class="funcTd"><i id="deleteUL" class="iconfont icon-shanchu" onclick="deleteRow(this);"></i></td>',
							+ '</tr>');
	updateIndex();
}

// 其他申请人表格
var other_applicant_tpl = [
		'<tr class="">',
		'<th class="">序号</th>',
		'<th style="display:none" id="id" class="">其他申请人Id</th>',
		'<th class="">申请人姓名</th>',
		'<th class="">性别</th>',
		'<th class="">证件类型</th>',
		'<th class="">证件号</th>',
		'<th class="">&nbsp</th>',
		'</tr>',
		'{@each isShowColumn as it, index}',
		'<tr class="">',
		'<td class="indexTd"></td>',
		'<td style="display:none" id="td_id" class=""><input id="id" type="text" value="${it.id}"/></td>',
		'<td class=""><input id="name" type="text" value="${it.name}"/></td>',
		'<td class=""><select id="other_sexCode" class="sexCode"></select></td>',
		'<td class=""><select id="other_certificateTypeCode" class="certificateTypeCode" name="certificateTypeCode" value="${it.certificateTypeCode}"></select></td>',
		'<td class=""><input id="ceritificateId" type="text" value="${it.ceritificateId}"/></td>',
		'<td class="funcTd"><i id="deleteUL" class="iconfont icon-shanchu" onclick="deleteRow(this);"></i></td>',
		'</tr>', '{@/each}' ].join('\n');

/* 申请人其他申请人列表 */
function applicant_otherApplicant_insertRow() {
	$("#applicant-otherApplicant-table")
			.append(
					'<tr class="">'
							+ '<td class="indexTd"></td>'
							+ '<td style="display:none" id="td_id" class=""><input id="id" type="text" value="${it.id}"/></td>'
							+ '<td class=""><input id="name" type="text" value=""/></td>'
							+ '<td class=""><select id="other_sexCode" class="">'+ createSelectHtml(sexJson, null) +'</select></td>'
							+ '<td class=""><select id="other_certificateTypeCode" class="">'+ createSelectHtml(certificateTypeJson, null) +'</select></td>'
							+ '<td class=""><input id="ceritificateId" type="text" value=""/></td>'
							+ '<td class="funcTd"><i id="deleteUL" class="iconfont icon-shanchu" onclick="deleteRow(this);"></i></td>',
							+ '</tr>');
	updateIndex();
}

// 申请人中添加其他申请人
function add_otherApplicant() {
	if ($('.applicant-otherApplicant-div').is(':hidden')) {
		$('.applicant-otherApplicant-div').show();
	} else {
		applicant_otherApplicant_insertRow();
	}
}

// 申请人中代理人表格
var applicant_agent_tpl = [
		'<tr class="">',
		'<th class="">序号</th>',
		'<th id="id" class="">申请人中代理人Id</th>',
		'<th class="">代理人姓名</th>',
		'<th class="">代理人类型</th>',
		'<th class="">电话</th>',
		'<th class="">是否有授权书</th>',
		'<th class="">被代理人</th>',
		'<th class="">&nbsp</th>',
		'</tr>',
		'{@each isShowColumn as it, index}',
		'<tr class="">',
		'<td class="indexTd"></td>',
		'<td id="td_id" class=""><input id="id" name="id" type="text" value="${it.id}"/></td>',
		'<td class=""><input type="text" id="agentName" name="agentName" value="${it.agentName}"/></td>',
		'<td class=""><select id="applicant_agentTypeCode" class="agentTypeCode" name="agentTypeCode">'+createSelectHtml(agentJson,$("#applicant_agentTypeCode").val())+'</select></td>',
		'<td class=""><input id="phone" name="phone" type="text" value="${it.phone}"/></td>',
		'<td class=""><input type="text" id="isAuthorization" name="isAuthorization" value="${it.isAuthorization}"/></td>',
		'<td class=""><input type="text" id="agentParent" name="agentParent" value="${it.agentParent}"/></td>',
		'<td class="funcTd"><i id="deleteUL" class="iconfont icon-shanchu" onclick="deleteRow(this);"></i></td>',
		'</tr>', '{@/each}'

].join('\n');

/* 申请人代理人列表 */
function applicant_agent_insertRow() {
	$("#applicant-agent-table")
			.append(
					'<tr class="">'
							+ '<td class="indexTd"></td>'
							+ '<td id="td_id" class=""><input id="id" type="text" value=""/></td>'
							+ '<td class=""><input type="text" value=""/></td>'
							+ '<td class=""><select id="applicant_agentTypeCode" name="agentTypeCode" class="agentTypeCode">'+ createSelectHtml(agentJson, null) +'</select></td>'
							+ '<td class=""><input id="phone" type="text" value=""/></td>'
							+ '<td class=""><input id="isAuthorization" type="text" value=""/></td>'
							+ '<td class=""><input id="agentParent" type="text" value=""/></td>'
							+ '<td class="funcTd"><i id="deleteUL" class="iconfont icon-shanchu" onclick="deleteRow(this);"></i></td>',
							+ '</tr>');
	updateIndex();
}

// 申请人中添加申请人代理人
function add_agent() {
	if ($('.applicant-agent-div').is(':hidden')) {
		$('.applicant-agent-div').show();
	} else {
		applicant_agent_insertRow();
	}
}

/* ####################################################################### */
// 被申请人表格
var respondent_tpl = [
		'<tr class="">',
		'<th class="">序号</th>',
		'<th id="id" class="">被申请人Id</th>',
		'<th class="">被申请人种类</th>',
		'<th class="">被申请人名称</th>',
		'<th class="">&nbsp</th>',
		'</tr>',
		'{@each isShowColumn as it, index}',
		'<tr class="">',
		'<td class="indexTd"></td>',
		'<td id="td_id" class=""><input id="id" type="text" value="${it.id}"/></td>',
		'<td class=""><input id="respondentType" type="text" value="${it.respondentType}"/></td>',
		'<td class=""><input id="respondentName" type="text" value="${it.respondentName}"/></td>',
		'<td class="funcTd"><i id="deleteUL" class="iconfont icon-shanchu" onclick="deleteRow(this);"></i></td>',
		'</tr>', '{@/each}'

].join('\n');

// 被申请人列表添加功能
function respondent_insertRow() {
	$("#respondent-table")
			.append(
					'<tr class="">'
							+ '<td class="indexTd"></td>'
							+ '<td id="td_id" class=""><input id="id" type="text" value=""/></td>'
							+ '<td class=""><input id="respondentType" type="text" value=""/></td>'
							+ '<td class=""><input id="respondentName" type="text" value=""/></td>'
							+ '<td class="funcTd"><i id="deleteUL" class="iconfont icon-shanchu" onclick="deleteRow(this);"></i></td>',
							+ '</tr>');
	updateIndex();
}

// 被申请人代理人表格
var respondent_agent_tpl = [
		'<tr class="">',
		'<th class="">序号</th>',
		'<th id="id" class="">被申请人代理人Id</th>',
		'<th class="">代理人姓名</th>',
		'<th class="">代理人类型</th>',
		'<th class="">电话</th>',
		'<th class="">是否有授权书</th>',
		'<th class="">被代理人</th>',
		'<th class="">&nbsp</th>',
		'</tr>',
		'{@each isShowColumn as it, index}',
		'<tr class="">',
		'<td class="indexTd"></td>',
		'<td id="td_id" class=""><input id="id" type="text" value="${it.id}"/></td>',
		'<td class=""><input id="agentName" type="text" value="${it.agentName}"/></td>',
		'<td class=""><select id="respondent_agentTypeCode" class="agentTypeCode" name="agentTypeCode"></select></td>',
		'<td class=""><input id="phone" type="text" value="${it.phone}"/></td>',
		'<td class=""><input id="isAuthorization" type="text" value="${it.isAuthorization}"/></td>',
		'<td class=""><input id="agentParent" type="text" value="${it.agentParent}"/></td>',
		'<td class="funcTd"><i id="deleteUL" class="iconfont icon-shanchu" onclick="deleteRow(this);"></i></td>',
		'</tr>', '{@/each}'

].join('\n');

// 被申请人代理人列表添加功能
function respondent_agent_insertRow() {
	$("#respondent-agent-table")
			.append(
					'<tr class="">'
							+ '<td class="indexTd"></td>'
							+ '<td id="td_id" class=""><input id="id" type="text" value=""/></td>'
							+ '<td class=""><input id="agentName" type="text" value=""/></td>'
							+ '<td class=""><select id="respondent_agentTypeCode" class="">'+ createSelectHtml(agentJson, null) +'</select></td>'
							+ '<td class=""><input id="phone" type="text" value=""/></td>'
							+ '<td class=""><input id="isAuthorization" type="text" value=""/></td>'
							+ '<td class=""><input id="agentParent" type="text" value=""/></td>'
							+ '<td class="funcTd"><i id="deleteUL" class="iconfont icon-shanchu" onclick="deleteRow(this);"></i></td>',
							+ '</tr>');
	updateIndex();
}

// 被申请人中添加申请人代理人
function add_respondent_agent() {
	if ($('.respondent-agent-div').is(':hidden')) {
		$('.respondent-agent-div').show();
	} else {
		respondent_agent_insertRow();
	}
}

/* ########################################################################### */
// 第三人表格
var thirdParty_tpl = [
		'<tr class="">',
		'<th class="">序号</th>',
		'<th id="id" class="">第三人Id</th>',
		'<th class="">第三人姓名</th>',
		'<th class="">证件类型</th>',
		'<th class="">证件号码</th>',
		'<th class="">性别</th>',
		'<th class="">名族</th>',
		'<th class="">通信地址</th>',
		'<th class="">邮编</th>',
		'<th class="">&nbsp</th>',
		'</tr>',
		'{@each isShowColumn as it, index}',
		'<tr class="">',
		'<td class="indexTd"></td>',
		'<td id="td_id" class=""><input id="id" type="text" value="${it.id}"/></td>',
		'<td class=""><input id="thirdPartyName" type="text" value="${it.thirdPartyName}"/></td>',
		'<td class=""><select id="thirdParty_certificateTypeCode" class="certificateTypeCode" name="certificateTypeCode" value="${it.certificateTypeCode}"></select></td>',
		'<td class=""><input id="certificateId" type="text" value="${it.certificateId}"/></td>',
		'<td class=""><select id="thirdParty_sexCode" class="sexCode" name="sexCode" value="${it.sexCode}"></select></td>',
		'<td class=""><input id="nationCode" type="text" value="${it.nationCode}"/></td>',
		'<td class=""><input id="postAddress" type="text" value="${it.postAddress}"/></td>',
		'<td class=""><input id="postcode" type="text" value="${it.postcode}"/></td>',
		'<td class="funcTd"><i id="deleteUL" class="iconfont icon-shanchu" onclick="deleteRow(this);"></i></td>',
		'</tr>', '{@/each}'

].join('\n');

// 第三人列表添加功能
function thirdParty_insertRow() {
	$("#thirdParty-table")
			.append(
					'<tr class="">'
							+ '<td class="indexTd"></td>'
							+ '<td id="td_id" class=""><input id="id" type="text" value=""/></td>'
							+ '<td class=""><input id="thirdPartyName" type="text"/></td>'
							+ '<td class=""><select id="thirdParty_certificateTypeCode" class="certificateTypeCode">'+ createSelectHtml(certificateTypeJson, null) +'</select></td>'
							+ '<td class=""><input id="certificateId" type="text"/></td>'
							+ '<td class=""><select id="thirdParty_sexCode" class="">'+ createSelectHtml(sexJson, null) +'</select></td>'
							+ '<td class=""><input id="nationCode" type="text"/></td>'
							+ '<td class=""><input id="postAddress" type="text"/></td>'
							+ '<td class=""><input id="postcode" type="text"/></td>'
							+ '<td class="funcTd"><i id="deleteUL" class="iconfont icon-shanchu" onclick="deleteRow(this);"></i></td>',
							+ '</tr>');
	updateIndex();
}

// 第三人代理人表格
var thirdParty_agent_tpl = [
		'<tr class="">',
		'<th class="">序号</th>',
		'<th id="id" class="">第三人代理人Id</th>',
		'<th class="">代理人姓名</th>',
		'<th class="">代理人类型</th>',
		'<th class="">电话</th>',
		'<th class="">是否有授权书</th>',
		'<th class="">被代理人</th>',
		'<th class="">&nbsp</th>',
		'</tr>',
		'{@each isShowColumn as it, index}',
		'<tr class="">',
		'<td class="indexTd"></td>',
		'<td id="td_id" class=""><input id="id" type="text" value="${it.id}"/></td>',
		'<td class=""><input id="name" type="text" value="${it.name}"/></td>',
		'<td class=""><select id="thirdParty_agentTypeCode" class=""></select></td>',
		'<td class=""><input id="phone" type="text" value="${it.phone}"/></td>',
		'<td class=""><input id="authorize" type="text" value="${it.authorize}"/></td>',
		'<td class=""><input id="agent" type="text" value="${it.agent}"/></td>',
		'<td class="funcTd"><i id="deleteUL" class="iconfont icon-shanchu" onclick="deleteRow(this);"></i></td>',
		'</tr>', '{@/each}'

].join('\n');

// 第三人代理人列表添加功能
function thirdParty_agent_insertRow() {
	$("#thirdParty-agent-table")
			.append(
					'<tr class="">'
							+ '<td class="indexTd"></td>'
							+ '<td id="td_id" class=""><input id="id" type="text" value=""/></td>'
							+ '<td class=""><input id="name" type="text" value=""/></td>'
							+ '<td class=""><select id="thirdParty_agentTypeCode" class="">'+ createSelectHtml(agentJson,null) +'</select></td>'
							+ '<td class=""><input id="phone" type="text" value=""/></td>'
							+ '<td class=""><input id="authorize" type="text" value=""/></td>'
							+ '<td class=""><input id="agent" type="text" value=""/></td>'
							+ '<td class="funcTd"><i id="deleteUL" class="iconfont icon-shanchu" onclick="deleteRow(this);"></i></td>',
							+ '</tr>');
	updateIndex();
}

// 被申请人中添加申请人代理人
function add_thirdParty_agent() {
	if ($('.thirdParty-agent-div').is(':hidden')) {
		$('.thirdParty-agent-div').show();
	} else {
		thirdParty_agent_insertRow();
	}
}

// 初始化
$(document).ready(function() {
	updateIndex();
	if (applicant_listdata != null) {
		$('.applicant-agent-div').show();
		$('.applicant-otherApplicant-div').show();
	} else {
		$('.applicant-agent-div').hide();
		$('.applicant-otherApplicant-div').hide();
	}
});

// 公共列表删除功能
function deleteRow(that) {
	$(that).parents('tr').remove();
	updateIndex();
}

// 公共更新列表序号
function updateIndex() {
	$('.edit-table').each(function() {
		var index = 0;
		var that = this;
		$(that).find(".indexTd").each(function() {
			index++;
			$(this).text(index);

		});
	});
}

/* 案件提取 */
function caseExtraction() {
	alert("案件提取");
	var title = "案件提取";
	var url = '/xzfy/jsp/caseRegister/caseExtraction.jsp?caseId=' + caseId;
	bsWindow(url, title, {
		width : "1000",
		height : "400",
		buttons : [],
		submit : function(v, h) {
		}
	});
}

/* ---------------------------------------------------------------------------------- */
var material_listdata = {
	list : [ {
		type : '申请人材料',
		content : [ {
			title : '申请书',
			file : [ {
				fileinfo : 'xzfy.doc,sdsdas'
			}, ]
		}, {
			title : '收件登记材料',
			file : [ {
				fileinfo : '1.doc,sdsdas'
			}, ]
		}, {
			title : '申请人提交的其他材料',
			file : [ {
				fileinfo : '1.doc,sdsdas'
			}, ]
		}, {
			title : '阅卷笔录、阅卷意见及材料',
			file : [ {
				fileinfo : '1.doc,sdsdas'
			}, ]
		} ]
	}, {
		type : '被申请人材料',
		content : [ {
			title : '阅卷笔录、阅卷意见及材料',
			file : [ {
				fileinfo : 'xzfy.doc,sdsdas'
			}, ]
		}, {
			title : '被申请人答复书',
			file : [ {
				fileinfo : '1.doc,sdsdas'
			}, ]
		}, {
			title : '被申请人证据材料',
			file : [ {
				fileinfo : '1.doc,sdsdas'
			}, ]
		} ]
	}, {
		type : '第三材料',
		content : [ {
			title : '被申请人证据材料',
			file : [ {
				fileinfo : 'xzfy.doc,sdsdas'
			}, ]
		} ]
	}, {
		type : '复议机关材料',
		content : [ {
			title : '登记文书',
			file : null
		}, {
			title : '立案文书',
			file : [ {
				fileinfo : '1.doc,sdsdas'
			}, ]
		}, {
			title : '审理文书',
			file : [ {
				fileinfo : '1.doc,sdsdas'
			}, ]
		}, {
			title : '结案文书',
			file : [ {
				fileinfo : '1.doc,sdsdas'
			}, ]
		}, {
			title : '审批表',
			file : [ {
				fileinfo : '1.doc,sdsdas'
			}, ]
		}, {
			title : '其他',
			file : [ {
				fileinfo : '1.doc,sdsdas'
			}, ]
		} ]
	} ]
}

var material_tpl = [
		'{@each list as it1}',
		'<table class="material">',
		'<th class="">${it1.type}</th>',
		'<th class=""></th>',
		'<th class=""></th>',
		'{@each it1.content as it2}',
		'<tr>',
		'<td class="material-title"><span class="">${it2.title}</span></td>',
		'<td class="material-content">',
		'<ul class="material-ul">',
		'{@if it2.file === null }',
		'<li><span class="nofile">未上传</span></li>',
		'{@else}',
		'{@each it2.file as it3}',
		'<li><span>${it3.fileinfo}</span><span class="li-func"><a>预览</a><a onclick="deletefile(this);">删除</a></span></li>',
		'{@/each}',
		'{@/if}',
		'</ul>',
		'</td>',
		'<td class="material-func">',
		'<input class="fy-btn uplode-btn" type="button" value="上传" title="上传" onclick="uplodeMtr(this);" />',
		'</td>', '</tr>', '{@/each}', '</table>', '{@/each}' ].join('\n');
$(".materialDiv").append(juicer(material_tpl, material_listdata));

// 删除二级
function deleteMtr(that) {
	if ($(that).parent().parent().siblings().length == 1) {
		$(that).parent().parent().parent().remove();
	}
	$(that).parent().parent().remove();
}

// 删除材料文件
function deletefile(that) {
	if ($(that).parent().parent().siblings('li').length == 0) {
		$(that).parent().parent().parent().css('color', 'red');
		$(that).parent().parent().parent().text('未上传');
	}
	$(that).parent().parent('li').remove();
}