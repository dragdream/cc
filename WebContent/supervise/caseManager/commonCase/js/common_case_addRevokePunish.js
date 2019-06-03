var editFlag = $('#common_case_add_revokePunish_editFlag').val();
/**
 * 默认加载方法
 * @returns
 */
function doInitRevokePunish(){
    initCodeListInput('COMMON_REVOKE_PUNISH_TYPE','revokePunishType');// 初始化撤销原处罚决定类型
    $('#revokePunishType').combobox({required:true, novalidate:true, missingMessage:'请选择撤销原处罚<br />决定类型'});
    $('#revokePunishmentDateStr').datebox({required:true, novalidate:true, validType:'date', missingMessage:'请选择撤销原处罚<br />决定日期'});
    $('#approvePerson').textbox({required:true, novalidate:true, validType:'length[0,20]', missingMessage:'请选择撤销原处罚<br />决定批准人'});
    $('#approveDateStr').datebox({required:true, novalidate:true, validType:'date', missingMessage:'请选择撤销原处罚<br />决定批准日期'});
    $('#revokePunishmentReason').textbox({multiline:true, required:true, validType:'length[0,100]', novalidate:true, missingMessage:'请填写撤销原处罚<br />决定原因'});
    if('2' == editFlag || '3' == editFlag){
        var revokePunishType = $('#common_case_add_revokePunish_revokePunishType').val();
        if(revokePunishType != null && revokePunishType != ''){
            $('#revokePunishType').combobox('setValue',revokePunishType);
        }
    }
}

/**
 * 获取撤销立案form表单
 * @returns
 */
function initReovkePunishForm(){
    var params = null;
    if($("#common_case_add_revokePunish_form").form('enableValidation').form('validate')){
        params = tools.formToJson($("#common_case_add_revokePunish_form"));
    }
    return params;
}