var id = '';
function doInit(){
    id = $('#id').val();
    orgSys = $('#org').val();
    var json = tools.requestJsonRs("/inspectionCtrl/get.action",{id:id});
    if(json.rtState){
        bindJsonObj2Easyui(json.rtData , "form1");
        initOrgSys(json.rtData.orgSys);
    }
}



//初始化所属领域
function initOrgSys(id){
    var json = tools.requestJsonRs("/inspectionCtrl/getOrgSystemByCurrentPerson.action");
    $('#orgSys').combobox({
        data:json,
        mode:'remote',
        valueField:'id',
        textField:'name',
        panelHeight:'auto',
        multiple:false,
        editable:false,
        onLoadSuccess:function(){
            if(id != null && id != "" && id != '0'){
                $(this).combobox('setValue',id);
            }
        },
        panelHeight:"100px"
    });
}
//保存
function save() {
    if($('#form1').form('enableValidation').form('validate')){
        var param = tools.formToJson($("#form1"));
        if(id == null || id == ''){
            var json = tools.requestJsonRs("/inspectionCtrl/save.action", param);
            return json.rtState;
        }else{
            var json = tools.requestJsonRs("/inspectionCtrl/update.action",param);
            return json.rtState;
        }
    }
}
    

