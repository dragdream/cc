function doInit() {
    // getSysCodeByParentCodeNo("LAW_ENFORCEMENT_FIELD","orgSys");

    // getSysCodeByParentCodeNo("ADMINISTRAIVE_DIVISION","administrativeDivision");
    // getSysCodeByParentCodeNo("DEPT_NATURE","nature");
    // getSysCodeByParentCodeNo("LAW_ENFORCEMENT_FIELD","gender");
    // getSysCodeByParentCodeNo("DEPT_LEVEL","departmentallevel");
    $('#orgSys').combobox(
            {
                prompt : '请选择',
                mode : 'remote',
                url : contextPath + '/inspectionCtrl/getOrgSystemByCurrentPerson.action',
                valueField : 'id',
                textField : 'name',
                multiple : false,
                editable : false,
                panelHeight : "100px",
                onLoadSuccess : function() {
                    var orgSys = $('#insp_orgSys').val();
                    $('#orgSys').combobox('setValue', orgSys);
                },
            });
}
function save() {
    if($('#form1').form('enableValidation').form('validate')){
        var param = tools.formToJson($("#form1"));
        param.id=$('#insp_orgSys').val();
        if(param.id == null || param.id == ''){
            var json = tools.requestJsonRs("/inspectionCtrl/save.action", param);
            return json.rtState;
        }else{
            var json = tools.requestJsonRs("/inspectionCtrl/update.action",param);
            return json.rtState;
        }
    }
    
}

/*
 * function show(){ var val=$('input:radio[name="r"]:checked').val();
 * if(val==1){ document.getElementById("div1").style.display="block";
 * document.getElementById("div2").style.display="none"; }else{
 * document.getElementById("div1").style.display="none";
 * document.getElementById("div2").style.display="block"; }
 *  }
 */