
//导出
function exportExcel(voteId){
	var url =  contextPath + "/voteManage/exportExcle.action?sid=" + voteId;
	location.href=url;
}