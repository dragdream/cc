function doInit(){
	var url = contextPath + "/subjectSearchController/getSubjectById.action";
    var json = tools.requestJsonRs(url,{id:$("#id").val()});
    if(json.rtState){
    	bindJsonObj2Cntrl(json.rtData);
    	
    	document.getElementById("div1").style.display="";
		document.getElementById("div2").style.display="";
		$.parser.parse($('#div1'));
		$.parser.parse($('#div2'));
    }
}
