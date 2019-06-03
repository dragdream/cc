/**
 * 默认加载方法
 * @returns
 */
function doInitEnd(){
    
}

/**
 * 获取撤销立案form表单
 * @returns
 */
function initEndForm(){
    var params = null;
    if($("#common_case_add_end_form").form('enableValidation').form('validate')){
        params = tools.formToJson($("#common_case_add_end_form"));
    }
    return params;
}