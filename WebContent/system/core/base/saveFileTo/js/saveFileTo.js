/**
 * 根据文件id获取文件信息
 */
function getAttachmentInfo(attachId,inputObjId){
	var url = contextPath + "/saveFileToPersonController/getAttachmentInfo.action";
	var para =  {attachId:attachId};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var fileName = jsonRs.rtData.fileName;
		$("#" + inputObjId).html(fileName);
	}else{
		alert(jsonRs.rtMsg);
	 }
}







