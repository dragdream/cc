<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="<%=request.getContextPath() %>/common/zt_webframe/js/jquery-1.7.1.min.js"></script>
<script>
$.ajax({
    async: false,
    url: "http://ssoapi.pyjiaoyu.com/ProxyAPIService.asmx/user_status",
    dataType: "jsonp",
    jsonpCallback: "callback", //默认callback  
    success: function (data) {
        if (data.Type == "1") {
        	window.location = "singleLogin4jiemi.jsp?userName="+data.UserModel.UserLoginName+"&name="+data.UserModel.UserName;
        }
    },
    error: function (xhr) {
    }
});
</script>