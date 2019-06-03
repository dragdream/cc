function doInit(){
	var url = contextPath + "/subjectSearchController/getSubjectById.action";
    var json = tools.requestJsonRs(url,{id:$("#id").val()});
    if(json.rtState){
    	bindJsonObj2Cntrl(json.rtData);
    }
}

