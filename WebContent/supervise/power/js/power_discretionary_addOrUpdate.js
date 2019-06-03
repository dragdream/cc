
function doInit() {}

function doSave() {
    var params = tools.formToJson($('#discretionaryForm'));
    
    if(params.illegalFact == null || params.illegalFact == "" ) {
        alert("请输入违法事实！");
        return false;
    }
    if(params.punishStandard == null || params.punishStandard == "") {
        alert("请输入处罚标准！");
        return false;
    }
    
    var resultInfo = tools.requestJsonRs("/discretionaryCtrl/saveOrUpdate.action", params);
    return resultInfo;
}