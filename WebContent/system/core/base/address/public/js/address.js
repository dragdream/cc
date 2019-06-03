/**
 * 查看通讯录详情
 * @param seqId
 */
function loadDetailAddress(seqId){
    var url = contextPath + "/system/core/base/address/private/address/addressDetail.jsp?id="+seqId;
   bsWindow(url,"查看详情",{width:"600", height:"350",
    	buttons:[{name:"关闭",classStyle:"btn-alert-gray"}],submit:function(v,h){
	    if(v=="关闭"){
				return true;
	        }
	  }});
 }



/**
 * 跳转至编辑界面  ---个人
 * @param seqId
 */
function editeAddressPrivate(seqId){
	window.location.href = contextPath + "/system/core/base/address/private/address/updateAddress.jsp?id="+seqId;
}


/**
 * 删除通讯录 包括批量删除
 * @param id  以逗号分隔
 */
function deleteAdd(id) {

	/*if(confirm("确定删除所选记录,删除后将不可恢复！")){
		var para = {};
		para['sid'] = id;
		var url = contextPath + "/teeAddressController/delAddressByIds.action";
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
//			$('#table-bootstrap').bootstrapTable('refresh',{query:para});
			//alert("删除成功！");
			$.MsgBox.Alert_auto("删除成功！");
			}else{
				$.MsgBox.Alert_auto(jsonRs.rtMsg);
			}
	}*/
	 $.MsgBox.Confirm("提示", "确定删除所选记录,删除后将不可恢复！", function(){
		    var para = {};
			para['sid'] = id;
			var url = contextPath + "/teeAddressController/delAddressByIds.action";
			var jsonRs = tools.requestJsonRs(url,para);
			if(jsonRs.rtState){					
				$.MsgBox.Alert_auto("删除成功！");
				//window.location.reload();
				datagrid.datagrid('reload');
			}else{
				$.MsgBox.Alert_auto(jsonRs.rtMsg);
			}   
	  });

			
		
}



/* 批量删除   个人*/
function deleteAddByIdsToPrivate() {
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		$.MsgBox.Alert_auto("至少选择一项");
	}else{
		var seqIds = "";
		for(var i = 0; i<selections.length;i++){
			 seqIds = seqIds + selections[i].sid + ",";
		}
		deleteAdd(seqIds);
	}
  }




/**
 * 编辑公共
 * @param rowIndex
 */
function editeAddressPublic(seqId){
	window.location.href = contextPath + "/system/core/base/address/public/address/updateAddress.jsp?id="+seqId;
}




/* 批量删除   公共*/
function deleteAddByIdsToPublic() {
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		$.MsgBox.Alert_auto("至少选择一项");
	}else{
		var seqIds = "";
		for(var i = 0; i<selections.length;i++){
			 seqIds = seqIds + selections[i].sid + ",";
		}
		deleteAdd(seqIds);
	}
  }


