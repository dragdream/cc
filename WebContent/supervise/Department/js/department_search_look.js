function doInit(){
	var url = contextPath + "/departmentSearchController/getDepartmentById.action";
    var json = tools.requestJsonRs(url,{id:$("#id").val()});
    if(json.rtState){
    	bindJsonObj2Cntrl(json.rtData);
    	var isManubrium = json.rtData.isManubrium;
    	if(isManubrium == 0){
    		document.getElementById('isManubrium').innerText = "否";
    	}else if(isManubrium == 1){
    		document.getElementById('isManubrium').innerText = "是";
        	document.getElementById("chuiguan1").style.display="";
    	}
    }
}

