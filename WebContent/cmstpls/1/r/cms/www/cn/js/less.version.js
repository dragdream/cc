(function() {
	var ua = navigator.userAgent;
	if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/MSIE (6|7)./i)){
		var html = '<div style="background-color:#F9F1A2;">'
					+ '<div style="width:1000px;margin:0 auto;font-size:12px;color:black;line-height:40px;">'
					+ '<a href="javascript:;" onclick="this.parentNode.parentNode.removeChild(this.parentNode)" style="float:right;font-size:16px;display:inline-block;padding:0 5px;">×</a>'
					+ '<span>抱歉，您的浏览器版本过低，请升级后访问本站.</span>'
					+ '</div>'
					+ '</div>';
		document.write(html);
	}
})();
