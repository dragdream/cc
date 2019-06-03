var caseId =  $("#aseId").val(data);

// 文件上传
function application_btn() {
	// 直接弹出，无取消按钮
	var title = "文件上传";
	var url = '/xzfy/jsp/caseRegister/file_upload.jsp';
	bsWindow(url, title, {
		width : "600",
		height : "320",
		buttons : [],
		submit : function(v, h) {
			v.getCatename(caseId);
			alert(caseId);
			alert("关闭子窗口")
			if (v == true) {
				
			} else if(v == false) {

			}
		}
	});
}

function getCatename(caseId){
    //
    $("c#aseId").val(data);
    
}    



