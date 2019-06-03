/**
 * 默认加载方法
 * @returns
 */
function doInitRevoke(){
    
}

/**
 * 获取撤销立案form表单
 * @returns
 */
function initReovkeForm(){
    var params = null;
    if($("#common_case_add_revoke_form").form('enableValidation').form('validate')){
        params = tools.formToJson($("#common_case_add_revoke_form"));
    }
    return params;
}