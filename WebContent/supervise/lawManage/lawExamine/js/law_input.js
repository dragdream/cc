function upload() {
    $("#form1").ajaxSubmit({
        url : "<%=contextPath %>/detailController/importLaw.action",
        iframe : true,
        data : {},
        dataType : 'json'
    });
    alert("文件导入成功！");
    Window.close();
}

function save(paramWindow) {
//     var json = tools.requestJsonRs("/detailController/importLaw.action",{id: $('#dataId').val()});
//     if(json.rtState){
//     $.MsgBox.Alert_auto("导入成功！");
//     return true;
//     }else{
//     $.MsgBox.Alert_auto("导入失败！");
//     return false;
//     }
	var isState = true;
    var myajax = $("#form1").ajaxSubmit({
        url : "/detailController/importLaw.action",
        iframe : true,
        async: false,
        data : {
            id : $('#dataId').val()
        },
        dataType : 'json',
        complete : function(state) {
        	var json = JSON.parse(state.responseText);
        	if(json.rtState){
        		$.MsgBox.Alert_auto("导入成功");
                paramWindow.initDatagriad();
                isState = json.rtState;
        	}else{
        		$.MsgBox.Alert_auto("导入失败，请检查Excel格式是否正确");
                paramWindow.initDatagriad();
                isState = json.rtState;        	
            }
        }
    });
    $.when(myajax).done(function () {
        //要执行的操作
        console.log(isState);
        console.log(isState);
        return isState;
      });


}