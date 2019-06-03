/**
 * 行政区划管理填报页面管理方法
 */

function doInit() {
    id = $('#id').val();
}

/*
 * 新增
 */
function save() {
    var params = tools.formToJson($('#adminDivisionEdit_form'));

    if ($('#form1').form('enableValidation').form('validate')) {
        var param = tools.formToJson($("#form1"));
        param.id = id;
        param.isDelete = 0;
        console.log(param);
        var json = tools.requestJsonRs(
                "/adminDivisionManageCtrl/saveAccount.action", param);
        if (json.rtState) {
            if(json.rtData == 0){
                $.MsgBox.Alert_auto("分配帐号成功！");
                return true;
            }else if (json.rtData == -1){
                $.MsgBox.Alert_auto("该行政区划下综合管理政府机构不存在，分配帐号失败！请联系管理员！");
                return false;
            }else if (json.rtData == -2){
                $.MsgBox.Alert_auto("该行政区划下综合管理政府机构未与系统机构建立关联关系，分配帐号失败！请联系管理员！");
                return false;
            }else if (json.rtData == -3){
                $.MsgBox.Alert_auto("该账号已存在！");
                return false;
            }
        } else {
            $.MsgBox.Alert_auto("分配帐号失败！");
            return false;
        }
    } else {
        return false;
    }
}