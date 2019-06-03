/**
 * 检查单模版管理控制js
 */
var listId = '';


function doInit(){
    listId = $('#listId').val();
    var json = tools.requestJsonRs("/inspListBaseCtrl/getById.action",{id:listId});   
    if(json.rtState){
        bindJsonObj2Cntrl(json.rtData);

        if(json.rtData.currentState!=null){
            if(json.rtData.currentState==0){
                $('#currentState').text('未提交');
            } else if(json.rtData.currentState==1){
                $('#currentState').text('已提交');
            } else if((json.rtData.currentState==2)){
                $('#currentState').text('已停用');
            }
        }
    }
}

