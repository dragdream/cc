var flowInfo;

function doInit() {
    var tokenInfo = $('#power_flow_token').val();
    
    if(tokenInfo != "") {
        flowInfo = tools.string2JsonObj(tokenInfo);
        $('#power_flow_frame').attr("src", flowInfo.flowPath);
    } else {
        $('#power_flow_frame').attr("src", "/supervise/power/power_input.jsp");
    }
    
}