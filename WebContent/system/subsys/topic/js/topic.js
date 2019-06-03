/* 查看详情 */
function getInfoById(id){
	var prc ="";
	var url = contextPath + "/TeeTopicSectionController/getInfoById.action";
	var para = {uuid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		prc = jsonObj.rtData;
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
	return prc;
}